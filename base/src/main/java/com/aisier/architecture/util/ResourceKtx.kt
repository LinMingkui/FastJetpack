package com.aisier.architecture.util

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.aisier.architecture.base.BaseApp

/**
 * @author
 * @date 2022/1/26
 * @description
 */

fun getStringX(@StringRes resId: Int, vararg formatArgs: Any?): String {
    return BaseApp.instance.resources.getString(resId, *formatArgs)
}

fun getColorX(@ColorRes resId: Int) = ContextCompat.getColor(BaseApp.instance, resId)

fun getDrawableX(@ColorRes resId: Int) = ContextCompat.getDrawable(BaseApp.instance, resId)