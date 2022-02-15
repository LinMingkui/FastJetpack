package com.lmk.architecture.net.base

import androidx.annotation.Keep
import androidx.viewbinding.BuildConfig
import com.lmk.architecture.net.entity.ApiResponse
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


abstract class BaseRetrofitClient {

    companion object CLIENT {
        private const val TIME_OUT = 5
    }

    private val client: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
            .addInterceptor(getHttpLoggingInterceptor())
            .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
        handleBuilder(builder)
        builder.build()
    }

    open fun getHttpLoggingInterceptor(): Interceptor {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.BASIC
        }
        return logging
    }

    open fun getConverterFactory(): Converter.Factory {
        val moshi = Moshi
            .Builder()
            .add(ApiResponseIntermediate.JSON_ADAPTER)
            .add(MoshiDefaultAdapterFactory())
            .build()
        return MoshiConverterFactory.create(moshi)
    }

    open fun handleBuilder(builder: OkHttpClient.Builder) {

    }

    fun <Service> getService(serviceClass: Class<Service>, baseUrl: String): Service {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(getConverterFactory())
            .baseUrl(baseUrl)
            .build()
            .create(serviceClass)
    }
}

@Keep
@JsonClass(generateAdapter = true)
class ApiResponseIntermediate<T>(
    @Json(name = "items")
    val items: T? = null
) : ApiResponse<T>() {

    companion object {
        val JSON_ADAPTER: Any = object : Any() {
            @FromJson
            fun fromJson(indeterminate: ApiResponseIntermediate<in Any>): ApiResponse<in Any> {
                val data: Any? = indeterminate.data?.let {
                    indeterminate.data
                } ?: indeterminate.items?.let {
                    indeterminate.items
                }
                return ApiResponse(
                    data,
                    indeterminate.errorCode,
                    indeterminate.errorMsg,
                    indeterminate.error
                )
            }
        }
    }

//    object  JsonAdapter<T> {
//        @FromJson
//        fun fromJson(indeterminate: ApiResponseIntermediate<T>): ApiResponse<T> {
//            val data: T? = indeterminate.data?.let {
//                indeterminate.data
//            } ?: indeterminate.items?.let {
//                indeterminate.items
//            }
//            return ApiResponse(
//                data,
//                indeterminate.errorCode,
//                indeterminate.errorMsg,
//                indeterminate.error
//            )
//        }
//    }
}

//class JsonAdapter<T> {
//    @FromJson
//    fun fromJson(indeterminate: ApiResponseIntermediate<T>): ApiResponse<T> {
//        val data: T? = indeterminate.data?.let {
//            indeterminate.data
//        } ?: indeterminate.items?.let {
//            indeterminate.items
//        }
//        return ApiResponse(
//            data,
//            indeterminate.errorCode,
//            indeterminate.errorMsg,
//            indeterminate.error
//        )
//    }
//}
