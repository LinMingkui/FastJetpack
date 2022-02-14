package com.lmk.jetpack.ui.fragment

import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import com.apkfuns.logutils.LogUtils
import com.lmk.architecture.base.BaseBindingFragment
import com.lmk.architecture.net.launchAndCollectIn
import com.lmk.architecture.net.launchRequestWithLoading
import com.lmk.architecture.net.launchRequestWithLoadingOnIO
import com.lmk.architecture.net.observeResult
import com.lmk.jetpack.R
import com.lmk.jetpack.databinding.FragmentMineBinding
import com.lmk.jetpack.net.WanRepository
import com.lmk.jetpack.vm.ApiViewModel

/**
 * dev 分支去掉LiveData，使用Flow
 */
class MineFragment : BaseBindingFragment<FragmentMineBinding>(R.layout.fragment_mine) {

    private val mViewModel by lazy {
        getViewModel(ApiViewModel::class.java)
    }

    override fun init(savedInstanceState: Bundle?) {
        initData()
        mViewModel.articleLiveData.observeResult(viewLifecycleOwner) {
            onSuccess = {
                mBinding.tvContent.text = it.toString()
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
//                mViewModel.fetchWxArticleFromNet()

                launchRequestWithLoading { WanRepository.fetchWxArticleFromNet() }
                    .asLiveData()
//                    .observe(this@NetListFragment){
//                        mViewModel.articleLiveData.value=it
//                    }
                    .observeResult(this@MineFragment) {
                        onSuccess
                    }
                launchRequestWithLoadingOnIO({ WanRepository.fetchWxArticleFromNet() }) {

                }
            }

            btnNetError.setOnClickListener {
                showNetErrorPic(false)
                launchRequestWithLoading {
                    WanRepository.login(
                        "FastJetpack",
                        "FastJetpack"
                    )
                }.launchAndCollectIn(viewLifecycleOwner) {

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