package com.aisier.ui.adapter

import android.view.View
import com.aisier.R
import com.aisier.architecture.base.adapter.BaseBindingHolder
import com.aisier.architecture.base.adapter.BasePagingBindingAdapter
import com.aisier.bean.Repository
import com.aisier.databinding.ItemRepositoryBinding
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