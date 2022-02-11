package com.aisier.ui.adapter

import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.aisier.R
import com.aisier.architecture.base.adapter.BaseBindingHolder
import com.aisier.architecture.base.adapter.BaseLoadStateAdapter
import com.aisier.databinding.ItemRefreshStateBinding

/**
 * @author
 * @date 2022/2/11
 * @description
 */
class RefreshStateAdapter(private val retry: (() -> Unit)? = null) :
    BaseLoadStateAdapter<ItemRefreshStateBinding>(R.layout.item_refresh_state, retry) {

    override fun onBindViewHolder(
        holder: BaseBindingHolder<ItemRefreshStateBinding>,
        binding: ItemRefreshStateBinding,
        loadState: LoadState
    ) {
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.button.isVisible = loadState is LoadState.Error
        binding.button.setOnClickListener {
            retry?.invoke()
        }
    }

}