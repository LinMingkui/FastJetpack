package com.aisier.architecture.util

import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.widget.Toast
import com.aisier.architecture.base.BaseApp

class ToastUtil {

    companion object {
        private val handler = Handler(Looper.getMainLooper())
        private val SINGLE_TOAST_LOCK = Any()
        private var singleToast: Toast? = null

        fun showToast(text: String) {
            showCommonToast(text, Toast.LENGTH_SHORT)
        }

        fun showLongToast(text: String) {
            showCommonToast(text, Toast.LENGTH_LONG)
        }

        fun showToast(stringRes: Int) {
            showCommonToast(BaseApp.instance.getString(stringRes), Toast.LENGTH_SHORT)
        }

        fun showLongToast(stringRes: Int) {
            showCommonToast(BaseApp.instance.getString(stringRes), Toast.LENGTH_LONG)
        }


        fun showSingleToast(text: String?) {
            showSingleToast(text, Toast.LENGTH_SHORT)
        }

        fun showSingleLongToast(text: String?) {
            showSingleToast(text, Toast.LENGTH_LONG)
        }

        fun showSingleToast(stringRes: Int) {
            showSingleToast(BaseApp.instance.getString(stringRes), Toast.LENGTH_SHORT)
        }

        fun showSingleLongToast(stringRes: Int) {
            showSingleToast(BaseApp.instance.getString(stringRes), Toast.LENGTH_LONG)
        }

        fun showCommonToast(text: String, duration: Int) {
            if (!TextUtils.isEmpty(text)) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    Toast.makeText(BaseApp.instance, text, duration).show()
                } else {
                    handler.post {
                        Toast.makeText(BaseApp.instance, text, duration).show()
                    }
                }
            }
        }

        /**
         * 避免一下显示很多
         *
         * @param text
         * @param duration
         * @return
         */
        fun showSingleToast(text: String?, duration: Int) {
            if (!TextUtils.isEmpty(text)) {
                synchronized(SINGLE_TOAST_LOCK) {
                    singleToast?.apply {
                        cancel()
                        singleToast = null
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
    }
}