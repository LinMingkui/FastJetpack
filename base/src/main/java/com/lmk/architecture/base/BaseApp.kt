package com.lmk.architecture.base

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.lmk.architecture.lifecycle.ActivityLifecycle
import com.lmk.architecture.lifecycle.AppLifecycle

/**
 * author : wutao
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
open class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycle())
        registerActivityLifecycleCallbacks(ActivityLifecycle())
    }

    companion object {
        lateinit var instance: BaseApp
            private set
    }
}