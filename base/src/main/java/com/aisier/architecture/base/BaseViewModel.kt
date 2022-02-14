package com.aisier.architecture.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * author : wutao
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
abstract class BaseViewModel : ViewModel() {
    val viewEffectLiveData: MutableLiveData<ViewEffect> = MutableLiveData()


    fun showLoading(msg: String = "Loading...", cancelable: Boolean = true) {
        viewEffectLiveData.value = ViewEffect.ShowLoading(msg, cancelable)
    }

    fun hideLoading(msg: String = "Loading...", cancelable: Boolean = true) {
        viewEffectLiveData.value = ViewEffect.HideLoading
    }
}
