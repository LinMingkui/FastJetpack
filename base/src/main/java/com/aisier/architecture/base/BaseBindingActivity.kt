package com.aisier.architecture.base

import android.app.ProgressDialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * <pre>
 * author : wutao
 * time   : 2021/6/18
 * desc   : 去掉类上面的泛型，因为反射会影响性能。并且优先选择组合而不是继承。
 * version: 1.3
</pre> *
 */
abstract class BaseBindingActivity<B : ViewDataBinding>(@LayoutRes val layoutResId: Int) :
    AppCompatActivity(), IUiView {
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
        return ViewModelProvider(this)[clazz]
    }

    override fun onDestroy() {
        super.onDestroy()
        mActivity = null
        mBinding?.apply {
            unbind()
            mBinding = null
        }
    }

    private var progressDialog: ProgressDialog? = null

    override fun showLoading() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.takeIf { it.isShowing }?.dismiss()
    }
}