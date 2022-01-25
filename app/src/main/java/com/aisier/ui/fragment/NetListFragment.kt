package com.aisier.ui.fragment

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import com.aisier.R
import com.aisier.architecture.base.BaseBindingFragment
import com.aisier.architecture.base.ViewEffect
import com.aisier.architecture.net.observeResult
import com.aisier.architecture.util.launchAndCollectIn
import com.aisier.architecture.util.launchFlow
import com.aisier.architecture.util.launchWithLoading
import com.aisier.architecture.util.launchWithLoadingAndCollect
import com.aisier.bean.WxArticleBean
import com.aisier.databinding.FragmentNetListBinding
import com.aisier.vm.ApiViewModel

/**
 * dev 分支去掉LiveData，使用Flow
 */
class NetListFragment : BaseBindingFragment<FragmentNetListBinding>(R.layout.fragment_net_list) {

    // navigation情况下不能用Activity的ViewModel
    private val mViewModel by viewModels<ApiViewModel>()


    override fun init(savedInstanceState: Bundle?) {
        initData()
        initObserver()
        mViewModel.viewEffectLiveData.observe(viewLifecycleOwner) {
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
        mViewModel.articleLiveData.observeResult(viewLifecycleOwner) {
            onSuccess = {
                mBinding?.tvContent?.text = it.toString()
                Log.e(TAG, "Success")
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            }
            onFailed = { c, m ->
                Log.e(TAG, "onFailed code=$c,msg=$m")
            }
//            onError = {
//                Log.e(TAG, "onError $it")
//            }
            onStart = {
                Log.e(TAG, "onStart")
            }
            onComplete = {
                Log.e(TAG, "onComplete")
            }
        }
    }

    private fun initObserver() {
        mViewModel.uiState.launchAndCollectIn(this, Lifecycle.State.STARTED) {
            onSuccess = { result: List<WxArticleBean>? ->
                showNetErrorPic(false)
                mBinding?.tvContent?.text = result.toString()
            }

            onComplete = { Log.i("NetListFragment", ": onComplete") }

            onFailed = { code, msg -> Log.i("tag", "errorCode: $code   errorMsg: $msg") }

            onError = { showNetErrorPic(true) }
        }
    }

    private fun showNetErrorPic(isShowError: Boolean) {
        mBinding?.apply {
            tvContent.isGone = isShowError
            ivContent.isVisible = isShowError
        }
    }

    private fun initData() {
        mBinding?.apply {
            btnNet.setOnClickListener {
//            requestNet()
                mViewModel.requestNet()
            }

            btnNetError.setOnClickListener {
                showNetErrorPic(false)
                requestNetError()
            }

            btLogin.setOnClickListener {
                showNetErrorPic(false)
                login()
            }
        }

    }

    private fun requestNet() = launchWithLoading(mViewModel::requestNet)

    private fun requestNetError() = launchWithLoading(mViewModel::requestNetError)

    /**
     * 链式调用，返回结果的处理都在一起，viewmodel中不需要创建一个livedata对象
     * 适用于不需要监听数据变化的场景
     * 屏幕旋转，Activity销毁重建，数据会消失
     */
    private fun login() {
        launchWithLoadingAndCollect({
            mViewModel.login("FastJetpack", "FastJetpack")
        }) {
            onSuccess = {
                mBinding?.tvContent?.text = it.toString()
            }
            onFailed = { errorCode, errorMsg ->
//                Log.i("errorCode: $errorCode   errorMsg: $errorMsg")
            }
        }
    }

    /**
     * 将Flow转变为LiveData
     */
    private fun loginAsLiveData() {
        val loginLiveData =
            launchFlow(requestBlock = {
                mViewModel.login(
                    "FastJetpack",
                    "FastJetpack11"
                )
            }).asLiveData()

        loginLiveData.observeResult(this) {
            onSuccess = { mBinding?.tvContent?.text = it.toString() }
//            onFailed =
//                { errorCode, errorMsg ->  Log.i("errorCode: $errorCode   errorMsg: $errorMsg") }
        }
    }
}