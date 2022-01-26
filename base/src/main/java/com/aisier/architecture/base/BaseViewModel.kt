package com.aisier.architecture.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aisier.architecture.net.entity.ApiCompleteResponse
import com.aisier.architecture.net.entity.ApiResponse
import com.aisier.architecture.net.entity.ApiStartResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * author : wutao
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
abstract class BaseViewModel : ViewModel() {
    val viewEffectLiveData: MutableLiveData<ViewEffect> = MutableLiveData()

//    fun <T> launchRequest(
//        requestBlock: suspend () -> ApiResponse<T>,
//        startCallback: (() -> Unit)? = null,
//        completeCallback: (() -> Unit)? = null,
//    ): Flow<ApiResponse<T>> {
//        return flow {
//            emit(requestBlock())
//        }.onStart {
//            startCallback?.invoke()
//            emit(ApiStartResponse())
//        }.onCompletion {
//            completeCallback?.invoke()
//            emit(ApiCompleteResponse())
//        }
//    }
//
//    fun <T> launchRequestOnIO(
//        resultLiveData: MutableLiveData<ApiResponse<T>>,
//        requestBlock: suspend () -> ApiResponse<T>,
//        startCallback: (() -> Unit)? = null,
//        completeCallback: (() -> Unit)? = null
//    ) {
//        viewModelScope.launch(Dispatchers.IO) {
//            launchRequest(requestBlock, startCallback, completeCallback)
//                .collect {
//                    resultLiveData.postValue(it)
//                }
//        }
//    }
//
//    fun <T> launchRequestWithLoading(
//        requestBlock: suspend () -> ApiResponse<T>,
//        msg: String = "Loading...",
//        cancelable: Boolean = true
//    ): Flow<ApiResponse<T>> {
//        return launchRequest(
//            { requestBlock.invoke() },
//            { viewEffectLiveData.postValue(ViewEffect.ShowLoading(msg, cancelable)) },
//            { viewEffectLiveData.postValue(ViewEffect.HideLoading) }
//        )
//    }
//
//    fun <T> launchRequestWithLoadingOnIO(
//        resultLiveData: MutableLiveData<ApiResponse<T>>,
//        requestBlock: suspend () -> ApiResponse<T>,
//        msg: String = "Loading...",
//        cancelable: Boolean = true
//    ) {
//        launchRequestOnIO(
//            resultLiveData,
//            requestBlock,
//            { viewEffectLiveData.postValue(ViewEffect.ShowLoading(msg, cancelable)) },
//            { viewEffectLiveData.postValue(ViewEffect.HideLoading) }
//        )
//    }
}
