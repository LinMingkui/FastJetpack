package com.aisier.architecture.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.apkfuns.logutils.LogUtils

/**
 * @author 再战科技
 * @date 2022/1/26
 * @description
 */
class AppLifecycle : DefaultLifecycleObserver {
    private val TAG = javaClass.simpleName
    override fun onStart(owner: LifecycleOwner) {
        LogUtils.tag(TAG).v("App start")
    }

    override fun onStop(owner: LifecycleOwner) {
        LogUtils.tag(TAG).v("App stop")
    }
}