package com.aisier.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aisier.bean.Article
import com.aisier.databinding.ItemArticleBinding

/**
 * @author 再战科技
 * @date 2022/2/8
 * @description
 */
class RecommendAdapter : PagingDataAdapter<Article, Holder>(Article.diffCallback) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.bean = getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

class Holder(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)