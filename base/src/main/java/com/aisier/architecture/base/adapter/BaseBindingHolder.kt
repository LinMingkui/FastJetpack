package com.aisier.architecture.base.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * @author
 * @date 2022/2/11
 * @description
 */
class BaseBindingHolder<B : ViewBinding>(val binding: B) : RecyclerView.ViewHolder(binding.root)