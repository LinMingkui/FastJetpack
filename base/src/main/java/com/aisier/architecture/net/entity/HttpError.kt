//package com.aisier.architecture.net.entity
//
//import com.aisier.architecture.util.singleToast
//import com.google.gson.JsonParseException
//import retrofit2.HttpException
//import java.net.SocketTimeoutException
//import java.util.concurrent.CancellationException
//
//enum class HttpError(var code: Int, var errorMsg: String) {
//    TOKEN_EXPIRE(3001, "token is expired"),
//    PARAMS_ERROR(4003, "params is error")
//    // ...... more
//}
//
//internal fun handlingApiExceptions(code: Int?, errorMsg: String?) = when (code) {
//    HttpError.TOKEN_EXPIRE.code -> singleToast(HttpError.TOKEN_EXPIRE.errorMsg)
//    HttpError.PARAMS_ERROR.code -> singleToast(HttpError.PARAMS_ERROR.errorMsg)
//    else -> errorMsg?.let { singleToast(it) }
//}
//
//internal fun handlingExceptions(e: Throwable) = when (e) {
//    is HttpException -> singleToast(e.message())
//
//    is CancellationException -> {
//    }
//    is SocketTimeoutException -> {
//    }
//    is JsonParseException -> {
//    }
//    else -> {
//    }
//}