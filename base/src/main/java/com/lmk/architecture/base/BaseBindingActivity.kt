package com.lmk.architecture.base

import android.app.ProgressDialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseBindingActivity<B : ViewDataBinding>(@LayoutRes val layoutResId: Int) :
    AppCompatActivity(), IUiView {
    val TAG = javaClass.simpleName
    var mActivity: BaseBindingActivity<B>? = null
    var mBinding: B? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
        initBinding()
        init(savedInstanceState)
    }

    private fun initBinding() {
        val binding: B = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this
        val dataBindingArguments = getDataBindingArguments()
        if (dataBindingArguments != null) {
            for ((k, v) in dataBindingArguments.map) {
                binding.setVariable(k, v)
            }
        }
        mBinding = binding
    }

    abstract fun init(savedInstanceState: Bundle?)

    open fun getDataBindingArguments(): DataBindingArguments? {
        return null
    }

    fun <T : ViewModel> getViewModel(clazz: Class<T>): T {
        val model = ViewModelProvider(this)[clazz]
        if (model is BaseViewModel) {
            initViewEffect(model)
        }
        return model
    }

    private fun initViewEffect(model: BaseViewModel) {
        model.viewEffectLiveData.observe(this) {
            when (it) {
                is ViewEffect.ShowLoading -> {
                    showLoading(it.msg, it.cancelable)
                }
                is ViewEffect.HideLoading -> {
                    dismissLoading()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mActivity = null
        mBinding = mBinding?.let {
            it.unbind()
            null
        }
    }

    private var progressDialog: ProgressDialog? = null

    override fun showLoading(msg: String?, cancelable: Boolean) {
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)
        msg?.let {
            progressDialog?.setMessage(msg)
        }
        progressDialog?.setCancelable(cancelable)
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.takeIf { it.isShowing }?.dismiss()
    }
}