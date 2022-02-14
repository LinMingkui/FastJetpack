package com.lmk.architecture.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding

/**
 * @author
 * @date 2022/2/11
 * @description Paging3
 */
abstract class BasePagingBindingAdapter<D : Any, B : ViewBinding>(
    private val layoutResId: Int,
    diffCallback: DiffUtil.ItemCallback<D>,
    open val onClick: ((View, D?, Int) -> Unit)? = null,
    open var onLongClick: ((View, D?, Int) -> Boolean)? = null
) : PagingDataAdapter<D, BaseBindingHolder<B>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingHolder<B> {
        val holder: BaseBindingHolder<B> = BaseBindingHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutResId,
                parent,
                false
            )
        )
        holder.binding.root.setOnClickListener { view ->
            onClick?.apply {
                val position = holder.bindingAdapterPosition
                val data: D? = try {
                    getItem(position)
                } catch (t: Throwable) {
                    null
                }
                invoke(view, data, position)
            }
        }
        holder.binding.root.setOnLongClickListener { view ->
            onLongClick?.let {
                val position = holder.bindingAdapterPosition
                val data: D? = try {
                    getItem(position)
                } catch (t: Throwable) {
                    null
                }
                it.invoke(view, data, position)
            } ?: false
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseBindingHolder<B>, position: Int) {
        onBindData(holder, holder.binding, position)
    }

    abstract fun onBindData(holder: BaseBindingHolder<B>, binding: B, position: Int)

}