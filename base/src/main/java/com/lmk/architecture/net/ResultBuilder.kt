package com.lmk.architecture.net

import com.lmk.architecture.net.entity.*
import com.lmk.architecture.util.singleToast
import com.apkfuns.logutils.LogUtils

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
        errorMsg?.let { singleToast(it) }
    }
    var onError: (e: Throwable) -> Unit = { e ->
        e.message?.let {
            LogUtils.e("onError $it")
            singleToast(it)
        }
    }
    var onComplete: () -> Unit = {}
    var onStart: () -> Unit = {}
}