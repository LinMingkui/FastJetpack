package com.aisier.page

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aisier.architecture.net.entity.ApiEmptyResponse
import com.aisier.architecture.net.entity.ApiSuccessResponse
import com.aisier.architecture.net.parseData
import com.aisier.bean.RecommendUser
import com.aisier.net.MeetiRepository

/**
 * @author 再战科技
 * @date 2022/2/8
 * @description
 */
class RecommendUserSource : PagingSource<Int, RecommendUser>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecommendUser> {
        val currPage = params.key ?: 1
        val response = MeetiRepository.getRecommendUser(currPage, params.loadSize)
        when (response) {
            is ApiSuccessResponse -> {
                return LoadResult.Page(
                    response.data ?: listOf(),
                    if (currPage == 1) {
                        null
                    } else {
                        currPage - 1
                    },
                    if (response.data?.size == params.loadSize) {
                        currPage + 1
                    } else {
                        null
                    }
                )
            }
            is ApiEmptyResponse -> {
                return LoadResult.Page(
                    listOf(),
                    if (currPage == 1) {
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

    override fun getRefreshKey(state: PagingState<Int, RecommendUser>): Int? {
        return null
    }

}