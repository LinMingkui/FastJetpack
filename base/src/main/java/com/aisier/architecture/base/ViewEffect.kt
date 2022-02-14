package com.aisier.architecture.base

/**
 * @author
 * @date 2022/1/26
 * @description
 */
sealed class ViewEffect {
    data class ShowLoading(val msg: String = "Loading...", val cancelable: Boolean = true) :
        ViewEffect()

    object HideLoading : ViewEffect()
}
