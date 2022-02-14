package com.lmk.jetpack.ui.adapter

import com.lmk.jetpack.R
import com.lmk.architecture.base.adapter.BaseBindingHolder
import com.lmk.architecture.base.adapter.BasePagingBindingAdapter
import com.lmk.jetpack.bean.Article
import com.lmk.jetpack.databinding.ItemArticleBinding

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