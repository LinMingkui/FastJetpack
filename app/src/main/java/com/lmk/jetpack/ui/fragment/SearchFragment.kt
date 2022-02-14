package com.lmk.jetpack.ui.fragment

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.lmk.architecture.base.BaseBindingFragment
import com.lmk.architecture.base.DataBindingArguments
import com.lmk.jetpack.BR
import com.lmk.jetpack.R
import com.lmk.jetpack.databinding.FragmentSearchBinding
import com.lmk.jetpack.vm.ApiViewModel
import kotlinx.coroutines.flow.collectLatest

class SearchFragment : BaseBindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val model by lazy { getViewModel(ApiViewModel::class.java) }
    override fun init(savedInstanceState: Bundle?) {
        mBinding.refreshLayout.setOnRefreshListener {
            model.adapter.refresh()
        }
        lifecycleScope.launchWhenCreated {
            model.adapter.loadStateFlow.collectLatest {
                if (it.refresh !is LoadState.Loading) {
                    mBinding.refreshLayout.isRefreshing = false
                }
                if (it.refresh is LoadState.Error && model.adapter.itemCount == 0) {
//                    singleToast("Error")
                }
            }
        }
        if (model.adapter.itemCount == 0) {
            mBinding.refreshLayout.isRefreshing = true
            model.searchRepository("Android")
        }
    }

    override fun getDataBindingArguments(): DataBindingArguments {
        return DataBindingArguments(BR.vm, model)
    }

//    override fun enablePrintLifecycle(): Boolean {
//        return true
//    }
}
