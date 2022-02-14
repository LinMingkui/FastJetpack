package com.lmk.jetpack.ui.adapter

import android.view.View
import com.lmk.jetpack.R
import com.lmk.architecture.base.adapter.BaseBindingHolder
import com.lmk.architecture.base.adapter.BasePagingBindingAdapter
import com.lmk.jetpack.bean.Repository
import com.lmk.jetpack.databinding.ItemRepositoryBinding
import com.apkfuns.logutils.LogUtils

/**
 * @author 再战科技
 * @date 2022/2/8
 * @description
 */
class RepositoryAdapter :
    BasePagingBindingAdapter<Repository, ItemRepositoryBinding>(
        R.layout.item_repository,
        Repository.diffCallback
    ) {

    override fun onBindData(
        holder: BaseBindingHolder<ItemRepositoryBinding>,
        binding: ItemRepositoryBinding,
        position: Int
    ) {
        binding.bean = getItem(position)
    }

    override val onClick: ((View, Repository?, Int) -> Unit) = { _, d, p ->
        LogUtils.i("position=$p,name=${d?.fullName}")
    }
}