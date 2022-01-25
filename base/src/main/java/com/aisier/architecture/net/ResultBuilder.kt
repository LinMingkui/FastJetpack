package com.aisier.architecture.net

import com.aisier.architecture.net.entity.*
import com.aisier.architecture.util.ToastUtil

fun <T> ApiResponse<T>.parseData(listenerBuilder: ResultBuilder<T>.() -> Unit) {
    val listener = ResultBuilder<T>().also(listenerBuilder)
    when (this) {
        is ApiStartResponse -> listener.onStart()
        is ApiSuccessResponse -> listener.onSuccess(this.response)
        is ApiEmptyResponse -> listener.onDataEmpty()
        is ApiFailedResponse -> listener.onFailed(this.errorCode, this.errorMsg)
        is ApiErrorResponse -> listener.onError(this.throwable)
        is ApiCompleteResponse -> listener.onComplete()
    }
}

class ResultBuilder<T> {
    var onSuccess: (data: T?) -> Unit = {}
    var onDataEmpty: () -> Unit = {}
    var onFailed: (errorCode: Int?, errorMsg: String?) -> Unit = { _, errorMsg ->
        errorMsg?.let { ToastUtil.showSingleToast(it) }
    }
    var onError: (e: Throwable) -> Unit = { e ->
        e.message?.let { ToastUtil.showSingleToast(it) }
    }
    var onComplete: () -> Unit = {}
    var onStart: () -> Unit = {}
}