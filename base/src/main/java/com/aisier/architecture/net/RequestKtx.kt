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
    requestBlock: suspend () -> ApiResponse<T>,
    startCallback: (() -> Unit)? = null,
    completeCallback: (() -> Unit)? = null,
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
    requestBlock: suspend () -> ApiResponse<T>,
    msg: String = "Loading...",
    cancelable: Boolean = true
): Flow<ApiResponse<T>> {
    return launchRequest(
        { requestBlock.invoke() },
        { viewEffectLiveData.value = ViewEffect.ShowLoading(msg, cancelable) },
        { viewEffectLiveData.value = ViewEffect.HideLoading }
    )
}

/**
 * 在IO线程发起请求
 * resultLiveData接收数据
 */
fun <T> BaseViewModel.launchRequestOnIO(
    resultLiveData: MutableLiveData<ApiResponse<T>>,
    requestBlock: suspend () -> ApiResponse<T>,
    startCallback: (() -> Unit)? = null,
    completeCallback: (() -> Unit)? = null
) {
    viewModelScope.launch {
        launchRequest(requestBlock, startCallback, completeCallback)
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
        launchRequest(requestBlock, startCallback, completeCallback)
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
    requestBlock: suspend () -> ApiResponse<T>,
    msg: String = "Loading...",
    cancelable: Boolean = true
) {
    launchRequestOnIO(
        resultLiveData,
        requestBlock,
        { viewEffectLiveData.value = ViewEffect.ShowLoading(msg, cancelable) },
        { viewEffectLiveData.value = ViewEffect.HideLoading }
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
        { viewEffectLiveData.value = ViewEffect.ShowLoading(msg, cancelable) },
        { viewEffectLiveData.value = ViewEffect.HideLoading },
        listenerBuilder
    )
}


/**
 * 发起带loading的请求
 * 直接返回数据
 */
fun <T> IUiView.launchRequestWithLoading(
    requestBlock: suspend () -> ApiResponse<T>,
    msg: String = "Loading...",
    cancelable: Boolean = true
): Flow<ApiResponse<T>> {
    return launchRequest(
        { requestBlock.invoke() },
        { showLoading(msg, cancelable) },
        { dismissLoading() }
    )
}

/**
 * 在IO线程发起请求
 * resultLiveData接收数据
 */
fun <T> IUiView.launchRequestOnIO(
    resultLiveData: MutableLiveData<ApiResponse<T>>,
    requestBlock: suspend () -> ApiResponse<T>,
    startCallback: (() -> Unit)? = null,
    completeCallback: (() -> Unit)? = null
) {
    lifecycleScope.launch {
        launchRequest(requestBlock, startCallback, completeCallback)
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
        launchRequest(requestBlock, startCallback, completeCallback)
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
    requestBlock: suspend () -> ApiResponse<T>,
    msg: String = "Loading...",
    cancelable: Boolean = true
) {
    launchRequestOnIO(
        resultLiveData,
        requestBlock,
        { showLoading(msg, cancelable) },
        { dismissLoading() }
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



