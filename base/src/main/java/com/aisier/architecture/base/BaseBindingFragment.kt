package com.aisier.architecture.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * author : wutao
 * time   : 2020/10/14
 * desc   :
 * version: 1.1
 */
abstract class BaseBindingFragment<B : ViewDataBinding>(@LayoutRes val layoutResId: Int) :
    Fragment(), IUiView {

    val TAG = javaClass.simpleName
    var mActivity: AppCompatActivity? = null
    var mBinding: B? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBindingArguments: DataBindingArguments? = getDataBindingArguments()
        val binding: B = DataBindingUtil.inflate(
            inflater, layoutResId,
            container, false, getDataBindingComponent()
        )
        binding.lifecycleOwner = this

        if (dataBindingArguments != null) {
            for ((k, v) in dataBindingArguments.map) {
                binding.setVariable(k, v)
            }
        }
        mBinding = binding
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        val model = ViewModelProvider(activity!!)[clazz]
        if (model is BaseViewModel) {
            initViewEffect(model)
        }
        return model
    }

    private fun initViewEffect(model: BaseViewModel) {
        model.viewEffectLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ViewEffect.ShowLoading -> {
                    Log.e(TAG, "Show Loading")
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is ViewEffect.HideLoading -> {
                    Log.e(TAG, "Hide Loading")
                    Toast.makeText(context, "Hide Loading", Toast.LENGTH_SHORT).show()
                }
                is ViewEffect.ShowToast -> {
                    Toast.makeText(context, it.message, it.length).show()
                }
            }
        }
    }

    private var progressDialog: ProgressDialog? = null

    override fun showLoading() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(requireActivity())
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.takeIf { it.isShowing }?.dismiss()
    }
}