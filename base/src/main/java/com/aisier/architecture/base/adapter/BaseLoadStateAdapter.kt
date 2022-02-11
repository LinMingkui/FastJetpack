package com.aisier.architecture.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.viewbinding.ViewBinding

/**
 * @author 再战科技
 * @date 2022/2/11
 * @description
 */
abstract class BaseLoadStateAdapter<B : ViewBinding>(
    private val layoutResId: Int,
    retry: (() -> Unit)? = null
) :
    LoadStateAdapter<BaseBindingHolder<B>>() {

    override fun onBindViewHolder(holder: BaseBindingHolder<B>, loadState: LoadState) {
        onBindViewHolder(holder, holder.binding, loadState)
    }

    abstract fun onBindViewHolder(holder: BaseBindingHolder<B>, binding: B, loadState: LoadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): BaseBindingHolder<B> {
        val holder: BaseBindingHolder<B> = BaseBindingHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutResId,
                parent,
                false
            )
        )
        onBindViewHolder(holder, holder.binding, loadState)
        return holder
    }
}