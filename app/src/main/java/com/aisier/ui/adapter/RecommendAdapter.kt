package com.aisier.ui.adapter

import com.aisier.R
import com.aisier.architecture.base.adapter.BaseBindingHolder
import com.aisier.architecture.base.adapter.BasePagingBindingAdapter
import com.aisier.bean.Article
import com.aisier.databinding.ItemArticleBinding

/**
 * @author
 * @date 2022/2/8
 * @description
 */
class RecommendAdapter : BasePagingBindingAdapter<Article, ItemArticleBinding>(
    R.layout.item_article,
    Article.diffCallback,
    {_,_,_->}
) {

    override fun onBindData(
        holder: BaseBindingHolder<ItemArticleBinding>,
        binding: ItemArticleBinding,
        position: Int
    ) {
        binding.bean = getItem(position)
    }
}