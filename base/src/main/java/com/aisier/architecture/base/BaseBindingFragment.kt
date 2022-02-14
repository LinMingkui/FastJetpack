package com.aisier.architecture.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aisier.architecture.util.singleToast
import com.aisier.architecture.util.toast
import com.apkfuns.logutils.LogUtils

abstract class BaseBindingFragment<B : ViewDataBinding>(@LayoutRes val layoutResId: Int) :
    Fragment(), IUiView {

    val TAG = javaClass.simpleName
    var mActivity: AppCompatActivity? = null
    lateinit var mBinding: B

    companion object {
        const val TAG_LIFECYCLE = "FragmentLifecycle"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (enablePrintLifecycle()) {
            LogUtils.v("onAttach")
        }
        mActivity = context as AppCompatActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (enablePrintLifecycle()) {
            LogUtils.v("onCreateView")
        }
        val dataBindingArguments: DataBindingArguments? = getDataBindingArguments()
        val binding: B = DataBindingUtil.inflate(
            inflater, layoutResId,
            container, false, getDataBindingComponent()
        )
        binding.lifecycleOwner = viewLifecycleOwner
        if (dataBindingArguments != null) {
            for ((k, v) in dataBindingArguments.map) {
                binding.setVariable(k, v)
            }
        }
        mBinding = binding
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (enablePrintLifecycle()) {
            LogUtils.tag(TAG_LIFECYCLE).v("onViewCreated")
        }
        init(savedInstanceState)
    }

    protected abstract fun init(savedInstanceState: Bundle?)

    open fun getDataBindingArguments(): DataBindingArguments? {
        return null
    }

    protected open fun getDataBindingComponent(): DataBindingComponent? {
        return DataBindingUtil.getDefaultComponent()
    }

    fun <T : ViewModel> getViewModel(clazz: Class<T>): T {
        val model = ViewModelProvider(this)[clazz]
        if (model is BaseViewModel) {
            initViewEffect(model)
        }
        return model
    }

    fun <T : ViewModel> getActivityViewModel(clazz: Class<T>): T {
        val model = ViewModelProvider(requireActivity())[clazz]
        if (model is BaseViewModel) {
            initViewEffect(model)
        }
        return model
    }

    private fun initViewEffect(model: BaseViewModel) {
        model.viewEffectLiveData.observe(viewLifecycleOwner) {
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (enablePrintLifecycle()) {
            LogUtils.tag(TAG_LIFECYCLE).v("onActivityCreated")
        }
    }

    override fun onStart() {
        super.onStart()
        if (enablePrintLifecycle()) {
            LogUtils.tag(TAG_LIFECYCLE).v("onStart")
        }
    }

    override fun onResume() {
        super.onResume()
        if (enablePrintLifecycle()) {
            LogUtils.tag(TAG_LIFECYCLE).v("onResume")
        }
    }

    override fun onPause() {
        super.onPause()
        if (enablePrintLifecycle()) {
            LogUtils.tag(TAG_LIFECYCLE).v("onPause")
        }
    }

    override fun onStop() {
        super.onStop()
        if (enablePrintLifecycle()) {
            LogUtils.tag(TAG_LIFECYCLE).v("onStop")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (enablePrintLifecycle()) {
            LogUtils.tag(TAG_LIFECYCLE).v("onDestroyView")
        }
        mBinding.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (enablePrintLifecycle()) {
            LogUtils.tag(TAG_LIFECYCLE).v("onDestroy")
        }
        mActivity = null
    }

    private var progressDialog: ProgressDialog? = null

    override fun showLoading(msg: String?, cancelable: Boolean) {
        if (mActivity == null) return
        if (progressDialog == null)
            progressDialog = ProgressDialog(mActivity)
        msg?.let {
            progressDialog?.setMessage(msg)
        }
        progressDialog?.setCancelable(cancelable)
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.takeIf { it.isShowing }?.dismiss()
    }

    open fun enablePrintLifecycle() = false
}