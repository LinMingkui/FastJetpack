package com.lmk.architecture.util

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.lmk.architecture.base.BaseApp

private val handler = Handler(Looper.getMainLooper())
private val SINGLE_TOAST_LOCK = Any()
private var singleToast: Toast? = null

fun toast(text: String?) {
    toast(text, Toast.LENGTH_SHORT)
}

fun toast(stringRes: Int) {
    toast(stringRes, Toast.LENGTH_SHORT)
}

fun longToast(text: String?) {
    toast(text, Toast.LENGTH_LONG)
}

fun longToast(stringRes: Int) {
    toast(stringRes, Toast.LENGTH_LONG)
}

fun toast(stringRes: Int, duration: Int) {
    toast(BaseApp.instance.getString(stringRes), duration)
}

fun toast(text: String?, duration: Int) {
    if (!text.isNullOrBlank()) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(BaseApp.instance, text, duration).show()
        } else {
            handler.post {
                Toast.makeText(BaseApp.instance, text, duration).show()
            }
        }
    }
}


fun singleToast(text: String?) {
    singleToast(text, Toast.LENGTH_SHORT)
}

fun singleLongToast(text: String?) {
    singleToast(text, Toast.LENGTH_LONG)
}

fun singleToast(stringRes: Int) {
    singleToast(stringRes, Toast.LENGTH_SHORT)
}

fun singleLongToast(stringRes: Int) {
    singleToast(stringRes, Toast.LENGTH_LONG)
}

fun singleToast(stringRes: Int, duration: Int) {
    singleToast(BaseApp.instance.getString(stringRes), duration)
}

/**
 * 避免一下显示很多
 *
 * @param text
 * @param duration
 * @return
 */
fun singleToast(text: String?, duration: Int) {
    if (!text.isNullOrBlank()) {
        synchronized(SINGLE_TOAST_LOCK) {
            singleToast = singleToast?.let {
                it.cancel()
                null
            }
            if (Looper.myLooper() == Looper.getMainLooper()) {
                singleToast = Toast.makeText(BaseApp.instance, text, duration)
                singleToast?.show()
            } else {
                handler.post {
                    singleToast = Toast.makeText(BaseApp.instance, text, duration)
                    singleToast?.show()
                }
            }
        }
    }
}