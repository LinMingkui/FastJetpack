package com.aisier.architecture.net

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.aisier.architecture.base.BaseViewModel
import com.aisier.architecture.base.IUiView
import com.aisier.architecture.base.ViewEffect
import com.aisier.architecture.net.entity.ApiCompleteResponse
import com.aisier.architecture.net.entity.ApiResponse
import com.aisier.architecture.net.entity.ApiStartResponse
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * 发起请求
 * 直接返回数据
 */
fun <T> launchRequest(
    startCallback: (() -> Unit)? = null,
    completeCallback: (() -> Unit)? = null,
    requestBlock: suspend () -> ApiResponse<T>
): Flow<ApiResponse<T>> {
    return flow {
        emit(requestBlock())
    }.onStart {
        startCallback?.invoke()
        emit(ApiStartResponse())
    }.onCompletion {
        completeCallback?.invoke()
        emit(ApiCompleteResponse())
    }
}

/**
 * 发起带loading的请求
 * 直接返回数据
 */
fun <T> BaseViewModel.launchRequestWithLoading(
    msg: String = "Loading...",
    cancelable: Boolean = true,
    requestBlock: suspend () -> ApiResponse<T>
): Flow<ApiResponse<T>> {
    return launchRequest(
        { showLoading(msg, cancelable) },
        { hideLoading() },
        { requestBlock.invoke() }
    )
}

/**
 * 在IO线程发起请求
 * resultLiveData接收数据
 */
fun <T> BaseViewModel.launchRequestOnIO(
    resultLiveData: MutableLiveData<ApiResponse<T>>,
    startCallback: (() -> Unit)? = null,
    completeCallback: (() -> Unit)? = null,
    requestBlock: suspend () -> ApiResponse<T>
) {
    viewModelScope.launch {
        launchRequest(startCallback, completeCallback, requestBlock)
            .collect {
//                if (it is ApiCompleteResponse) {
//                    resultLiveData.postValue(it)
//                } else {
                resultLiveData.value = it
//                }
            }
    }
}

/**
 * 在IO线程发起请求
 * listenerBuilder接收数据
 */
fun <T> BaseViewModel.launchRequestOnIO(
    requestBlock: suspend () -> ApiResponse<T>,
    startCallback: (() -> Unit)? = null,
    completeCallback: (() -> Unit)? = null,
    listenerBuilder: ResultBuilder<T>.() -> Unit
) {
    viewModelScope.launch {
        launchRequest(startCallback, completeCallback, requestBlock)
            .collect {
                it.parseData(listenerBuilder)
            }
    }
}

/**
 * 在IO线程发起带loading的请求
 * resultLiveData接收数据
 */
fun <T> BaseViewModel.launchRequestWithLoadingOnIO(
    resultLiveData: MutableLiveData<ApiResponse<T>>,
    msg: String = "Loading...",
    cancelable: Boolean = true,
    requestBlock: suspend () -> ApiResponse<T>
) {
    launchRequestOnIO(
        resultLiveData,
        { showLoading(msg, cancelable) },
        { hideLoading() },
        requestBlock
    )
}

/**
 * 在IO线程发起带loading的请求
 * listenerBuilder接收数据
 */
fun <T> BaseViewModel.launchRequestWithLoadingOnIO(
    requestBlock: suspend () -> ApiResponse<T>,
    msg: String = "Loading...",
    cancelable: Boolean = true,
    listenerBuilder: ResultBuilder<T>.() -> Unit
) {
    launchRequestOnIO(
        requestBlock,
        { showLoading(msg, cancelable) },
        { hideLoading() },
        listenerBuilder
    )
}


/**
 * 发起带loading的请求
 * 直接返回数据
 */
fun <T> IUiView.launchRequestWithLoading(
    msg: String = "Loading...",
    cancelable: Boolean = true,
    requestBlock: suspend () -> ApiResponse<T>
): Flow<ApiResponse<T>> {
    return launchRequest(
        { showLoading(msg, cancelable) },
        { dismissLoading() },
        { requestBlock.invoke() }
    )
}

/**
 * 在IO线程发起请求
 * resultLiveData接收数据
 */
fun <T> IUiView.launchRequestOnIO(
    resultLiveData: MutableLiveData<ApiResponse<T>>,
    startCallback: (() -> Unit)? = null,
    completeCallback: (() -> Unit)? = null,
    requestBlock: suspend () -> ApiResponse<T>
) {
    lifecycleScope.launch {
        launchRequest(startCallback, completeCallback, requestBlock)
            .collect {
                resultLiveData.value = it
            }
    }
}

/**
 * 在IO线程发起请求
 * listenerBuilder接收数据
 */
fun <T> IUiView.launchRequestOnIO(
    requestBlock: suspend () -> ApiResponse<T>,
    startCallback: (() -> Unit)? = null,
    completeCallback: (() -> Unit)? = null,
    listenerBuilder: ResultBuilder<T>.() -> Unit
) {
    lifecycleScope.launch {
        launchRequest(startCallback, completeCallback, requestBlock)
            .collect {
                it.parseData(listenerBuilder)
            }
    }
}

/**
 * 在IO线程发起带loading的请求
 * resultLiveData接收数据
 */
fun <T> IUiView.launchRequestWithLoadingOnIO(
    resultLiveData: MutableLiveData<ApiResponse<T>>,
    msg: String = "Loading...",
    cancelable: Boolean = true,
    requestBlock: suspend () -> ApiResponse<T>
) {
    launchRequestOnIO(
        resultLiveData,
        { showLoading(msg, cancelable) },
        { dismissLoading() },
        requestBlock
    )
}

/**
 * 在IO线程发起带loading的请求
 * listenerBuilder接收数据
 */
fun <T> IUiView.launchRequestWithLoadingOnIO(
    requestBlock: suspend () -> ApiResponse<T>,
    msg: String = "Loading...",
    cancelable: Boolean = true,
    listenerBuilder: ResultBuilder<T>.() -> Unit
) {
    launchRequestOnIO(
        requestBlock,
        { showLoading(msg, cancelable) },
        { dismissLoading() },
        listenerBuilder
    )
}

fun <T> Flow<ApiResponse<T>>.launchAndCollectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    listenerBuilder: ResultBuilder<T>.() -> Unit,
) {
    if (owner is Fragment) {
        owner.viewLifecycleOwner.lifecycleScope.launch {
            owner.viewLifecycleOwner.repeatOnLifecycle(minActiveState) {
                collect { apiResponse: ApiResponse<T> ->
                    apiResponse.parseData(listenerBuilder)
                }
            }
        }
    } else {
        owner.lifecycleScope.launch {
            owner.repeatOnLifecycle(minActiveState) {
                collect { apiResponse: ApiResponse<T> ->
                    apiResponse.parseData(listenerBuilder)
                }
            }
        }
    }
}



