package com.aisier.ui.fragment

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.aisier.BR
import com.aisier.R
import com.aisier.architecture.base.BaseBindingFragment
import com.aisier.architecture.base.DataBindingArguments
import com.aisier.databinding.FragmentSearchBinding
import com.aisier.vm.ApiViewModel
import com.apkfuns.logutils.LogUtils
import kotlinx.coroutines.flow.collectLatest

class SearchFragment : BaseBindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val model by lazy { getViewModel(ApiViewModel::class.java) }
    override fun init(savedInstanceState: Bundle?) {
        model.getRecommendUser()
        mBinding.refreshLayout.setOnRefreshListener {
            model.adapter.refresh()
        }
        lifecycleScope.launchWhenCreated {
            model.adapter.loadStateFlow.collectLatest {
                LogUtils.i(it)
                if (it.refresh is LoadState.NotLoading) {
                    mBinding.refreshLayout.isRefreshing = false
                }

            }
        }
//        mBinding.refreshLayout.
    }

    override fun getDataBindingArguments(): DataBindingArguments? {
        return DataBindingArguments(BR.vm, model)
    }
}
