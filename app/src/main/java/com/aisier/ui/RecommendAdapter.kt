package com.aisier.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aisier.bean.RecommendUser
import com.aisier.databinding.ItemRecommendUserBinding

/**
 * @author 再战科技
 * @date 2022/2/8
 * @description
 */
class RecommendAdapter() : PagingDataAdapter<RecommendUser, Holder>(RecommendUser.diffCallback) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.bean = getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemRecommendUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

class Holder(val binding: ItemRecommendUserBinding) : RecyclerView.ViewHolder(binding.root)