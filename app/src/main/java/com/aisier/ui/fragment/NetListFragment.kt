package com.aisier.ui.fragment

import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import com.aisier.R
import com.aisier.architecture.base.BaseBindingFragment
import com.aisier.architecture.net.launchAndCollectIn
import com.aisier.architecture.net.launchRequestWithLoading
import com.aisier.architecture.net.observeResult
import com.aisier.databinding.FragmentNetListBinding
import com.aisier.net.WxArticleRepository
import com.aisier.vm.ApiViewModel
import com.apkfuns.logutils.LogUtils

/**
 * dev 分支去掉LiveData，使用Flow
 */
class NetListFragment : BaseBindingFragment<FragmentNetListBinding>(R.layout.fragment_net_list) {

    private val mViewModel by lazy {
        getViewModel(ApiViewModel::class.java)
    }

    override fun init(savedInstanceState: Bundle?) {
        initData()
        mViewModel.articleLiveData.observeResult(viewLifecycleOwner) {
            onSuccess = {
                mBinding?.tvContent?.text = it.toString()
                LogUtils.i("Success")
            }
//            onFailed = { c, m ->
//                LogUtils.e("onFailed code=$c,msg=$m")
//            }
//            onError = {
//                LogUtils.e("onError $it")
//            }
            onStart = {
                LogUtils.i("onStart")
            }
            onComplete = {
                LogUtils.i("onComplete")
            }
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
                mViewModel.fetchWxArticleFromNet()
            }

            btnNetError.setOnClickListener {
                showNetErrorPic(false)
                launchRequestWithLoading({
                    WxArticleRepository().login(
                        "FastJetpack",
                        "FastJetpack"
                    )
                }).launchAndCollectIn(viewLifecycleOwner) {

                }
            }
            btLogin.setOnClickListener {
                login()
            }
        }

    }

    /**
     * 链式调用，返回结果的处理都在一起，viewmodel中不需要创建一个livedata对象
     * 适用于不需要监听数据变化的场景
     * 屏幕旋转，Activity销毁重建，数据会消失
     */
    private fun login() {
//        launchRequestWithLoadingOnIO({
//            WxArticleRepository().login(
//                "FastJetpack",
//                "FastJetpack"
//            )
//        }) {
//            onSuccess = { LogUtils.e(it); }
//        }
        loginAsLiveData()
    }

    /**
     * 将Flow转变为LiveData
     */
    private fun loginAsLiveData() {
        val loginLiveData =
            launchRequestWithLoading(requestBlock = {
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