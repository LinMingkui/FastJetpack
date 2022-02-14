package com.lmk.architecture.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.apkfuns.logutils.LogUtils

/**
 * @author 再战科技
 * @date 2022/1/26
 * @description
 */
class ActivityLifecycle : Application.ActivityLifecycleCallbacks {
    private val TAG = javaClass.simpleName
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        LogUtils.tag(TAG).v("onActivityCreated:${activity.javaClass.simpleName}")
    }

    override fun onActivityStarted(activity: Activity) {
        LogUtils.tag(TAG).v("onActivityStarted:${activity.javaClass.simpleName}")
    }

    override fun onActivityResumed(activity: Activity) {
        LogUtils.tag(TAG).v("onActivityResumed:${activity.javaClass.simpleName}")
    }

    override fun onActivityPaused(activity: Activity) {
        LogUtils.tag(TAG).v("onActivityPaused:${activity.javaClass.simpleName}")
    }

    override fun onActivityStopped(activity: Activity) {
        LogUtils.tag(TAG).v("onActivityStopped:${activity.javaClass.simpleName}")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        LogUtils.tag(TAG).v("onActivitySaveInstanceState:${activity.javaClass.simpleName}")
    }

    override fun onActivityDestroyed(activity: Activity) {
        LogUtils.tag(TAG).v("onActivityDestroyed:${activity.javaClass.simpleName}")
    }
}