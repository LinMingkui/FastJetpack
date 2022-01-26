package com.aisier.architecture.net

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.aisier.architecture.base.BaseViewModel
import com.aisier.architecture.base.IUiView
import com.aisier.architecture.base.ViewEffect
import com.aisier.architecture.net.entity.ApiCompleteResponse
import com.aisier.architecture.net.entity.ApiResponse
import com.aisier.architecture.net.entity.ApiStartResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

fun <T> launchRequest(
    requestBlock: suspend () -> ApiResponse<T>,
    startCallback: (() -> Unit)? = null,
    completeCallback: (() -> Unit)? = null,
): Flow<ApiResponse<T>> {
    return flow {
        emit(requestBlock())
    }.onStart {
        startCallback?.invoke()
//        emit(ApiStartResponse())
    }.onCompletion {
        completeCallback?.invoke()
//        emit(ApiCompleteResponse())
    }
}

fun <T> BaseViewModel.launchRequestOnIO(
    resultLiveData: MutableLiveData<ApiResponse<T>>,
    requestBlock: suspend () -> ApiResponse<T>,
    startCallback: (() -> Unit)? = null,
    completeCallback: (() -> Unit)? = null
) {
    viewModelScope.launch {
        launchRequest(requestBlock, startCallback, completeCallback)
            .collect {
                resultLiveData.postValue(it)
            }
    }
}

fun <T> BaseViewModel.launchRequestWithLoading(
    requestBlock: suspend () -> ApiResponse<T>,
    msg: String = "Loading...",
    cancelable: Boolean = true
): Flow<ApiResponse<T>> {
    return launchRequest(
        { requestBlock.invoke() },
        { viewEffectLiveData.postValue(ViewEffect.ShowLoading(msg, cancelable)) },
        { viewEffectLiveData.postValue(ViewEffect.HideLoading) }
    )
}

fun <T> BaseViewModel.launchRequestWithLoadingOnIO(
    resultLiveData: MutableLiveData<ApiResponse<T>>,
    requestBlock: suspend () -> ApiResponse<T>,
    msg: String = "Loading...",
    cancelable: Boolean = true
) {
    launchRequestOnIO(
        resultLiveData,
        requestBlock,
        { viewEffectLiveData.postValue(ViewEffect.ShowLoading(msg, cancelable)) },
        { viewEffectLiveData.postValue(ViewEffect.HideLoading) }
    )
}


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

fun <T> IUiView.launchRequestOnIO(
    resultLiveData: MutableLiveData<ApiResponse<T>>,
    requestBlock: suspend () -> ApiResponse<T>,
    startCallback: (() -> Unit)? = null,
    completeCallback: (() -> Unit)? = null
) {
    lifecycleScope.launch {
        launchRequest(requestBlock, startCallback, completeCallback)
            .collect {
                resultLiveData.postValue(it)
            }
    }
}

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


/**
 * 这个方法只是简单的一个封装Loading的普通方法，不返回任何实体类
 */
fun IUiView.launchWithLoading(requestBlock: suspend () -> Unit) {
    lifecycleScope.launch {
        flow {
            emit(requestBlock())
        }.onStart {
            showLoading()
        }.onCompletion {
            dismissLoading()
        }.collect()
    }
}

/**
 * 请求不带Loading&&不需要声明LiveData
 */
fun <T> IUiView.launchAndCollect(
    requestBlock: suspend () -> ApiResponse<T>,
    listenerBuilder: ResultBuilder<T>.() -> Unit
) {
    lifecycleScope.launch {
        launchRequest(requestBlock).collect { response ->
            response.parseData(listenerBuilder)
        }
    }
}

/**
 * 请求带Loading&&不需要声明LiveData
 */
fun <T> IUiView.launchWithLoadingAndCollect(
    requestBlock: suspend () -> ApiResponse<T>,
    listenerBuilder: ResultBuilder<T>.() -> Unit
) {
    lifecycleScope.launch {
        launchRequest(requestBlock, { showLoading() }, { dismissLoading() }).collect { response ->
            response.parseData(listenerBuilder)
        }
    }
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



