package com.aisier.architecture.base

import android.widget.Toast

/**
 * @author 再战科技
 * @date 2022/1/26
 * @description
 */
sealed class ViewEffect {
    data class ShowLoading(val msg: String = "Loading...", val cancelable: Boolean = true) :
        ViewEffect()

    object HideLoading : ViewEffect()
    data class ShowToast(
        val msg: String? = null,
        val msgResId: Int = 0,
        val duration: Int = Toast.LENGTH_SHORT
    ) : ViewEffect()

    data class ShowSingleToast(
        val msg: String? = null,
        val msgResId: Int = 0,
        val duration: Int = Toast.LENGTH_SHORT
    ) : ViewEffect()
}
