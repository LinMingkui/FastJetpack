package com.lmk.architecture.net.base

import androidx.viewbinding.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    open fun handleBuilder(builder: OkHttpClient.Builder) {

    }

    open fun <Service> getService(serviceClass: Class<Service>, baseUrl: String): Service {
        val gson = GsonBuilder()
            .registerTypeAdapterFactory(CustomAdapterFactory())
            .create()
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .build()
            .create(serviceClass)
    }
}
