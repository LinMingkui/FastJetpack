package com.aisier.page

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aisier.architecture.net.entity.ApiEmptyResponse
import com.aisier.architecture.net.entity.ApiSuccessResponse
import com.aisier.architecture.net.parseData
import com.aisier.bean.Article
import com.aisier.net.WanRepository

/**
 * @author 再战科技
 * @date 2022/2/8
 * @description
 */
class ArticleSource : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val currPage = params.key ?: 0
        val response = WanRepository.getArticleList(currPage)
        when (response) {
            is ApiSuccessResponse -> {
                return LoadResult.Page(
                    response.data?.datas ?: listOf(),
                    if (currPage == 0) {
                        null
                    } else {
                        currPage - 1
                    },
                    if (response.data?.pageCount ?: Int.MAX_VALUE < currPage) {
                        currPage + 1
                    } else {
                        null
                    }
                )
            }
            is ApiEmptyResponse -> {
                return LoadResult.Page(
                    listOf(),
                    if (currPage == 0) {
                        null
                    } else {
                        currPage - 1
                    },
                    null
                )
            }

        }
        response.parseData {}
        return LoadResult.Error(
            response.error ?: Throwable(response.errorMsg ?: "Unknown Error")
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return null
    }

}