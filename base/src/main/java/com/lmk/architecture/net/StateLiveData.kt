package com.lmk.architecture.net

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lmk.architecture.net.entity.ApiResponse

/**
 * <pre>
 * @author : wutao
 * time   : 2021/10/18
 * desc   :
 * version: 1.1
</pre> *
 */
typealias ResponseLiveData<T> = LiveData<ApiResponse<T>>
typealias ResponseMutableLiveData<T> = MutableLiveData<ApiResponse<T>>

@MainThread
fun <T> ResponseMutableLiveData<T>.observeResult(
    owner: LifecycleOwner,
    listenerBuilder: ResultBuilder<T>.() -> Unit,
) {
    observe(owner) { apiResponse -> apiResponse.parseData(listenerBuilder) }
}

@MainThread
fun <T> LiveData<ApiResponse<T>>.observeResult(
    owner: LifecycleOwner,
    listenerBuilder: ResultBuilder<T>.() -> Unit,
) {
    observe(owner) { apiResponse -> apiResponse.parseData(listenerBuilder) }
}