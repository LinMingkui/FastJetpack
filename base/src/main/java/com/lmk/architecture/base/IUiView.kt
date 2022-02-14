package com.lmk.architecture.base

import androidx.lifecycle.LifecycleOwner

/**
 * @author 再战科技
 * @date 2022/1/26
 * @description
 */

interface IUiView : LifecycleOwner {

    fun showLoading(msg: String?, cancelable: Boolean)

    fun showLoading(msg: String?) {
        showLoading(msg, true)
    }

    fun showLoading(cancelable: Boolean) {
        showLoading(null, cancelable)
    }

    fun showLoading() {
        showLoading(null, true)
    }

    fun dismissLoading()
}