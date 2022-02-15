package com.lmk.architecture.net.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable


@Keep
@JsonClass(generateAdapter = true)
open class ApiResponse<T>(
    @Json(name = "items")
    open val data: T? = null,
    open val errorCode: Int? = null,
    open val errorMsg: String? = null,
    @Json(ignore = true)
    open val error: Throwable? = null,
) : Serializable {
    val isSuccess: Boolean
        get() = errorCode == 0

    override fun toString(): String {
        return "ApiResponse(data=$data, errorCode=$errorCode, errorMsg=$errorMsg, error=$error)"
    }
}

data class ApiSuccessResponse<T>(val response: T) : ApiResponse<T>(data = response)

class ApiEmptyResponse<T> : ApiResponse<T>()

class ApiStartResponse<T> : ApiResponse<T>()

class ApiCompleteResponse<T> : ApiResponse<T>()

data class ApiFailedResponse<T>(override val errorCode: Int?, override val errorMsg: String?) :
    ApiResponse<T>(errorCode = errorCode, errorMsg = errorMsg)

data class ApiErrorResponse<T>(val throwable: Throwable) :
    ApiResponse<T>(error = throwable, errorMsg = throwable.localizedMessage)

