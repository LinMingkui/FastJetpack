package com.lmk.jetpack.ui.fragment

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.apkfuns.logutils.LogUtils
import com.lmk.architecture.base.BaseBindingFragment
import com.lmk.architecture.base.DataBindingArguments
import com.lmk.architecture.net.entity.ApiResponse
import com.lmk.jetpack.BR
import com.lmk.jetpack.R
import com.lmk.jetpack.databinding.FragmentSearchBinding
import com.lmk.jetpack.vm.ApiViewModel
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.collectLatest
import androidx.annotation.Keep

import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


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
}

@Keep
@JsonClass(generateAdapter = true)
data class Test(
    @Json(name = "errorCode")
    val errorCode: Int = 0,
    @Json(name = "errorMsg")
    val errorMsg: String = "",
    @Json(name = "items")
    val items: List<Any> = listOf()
)