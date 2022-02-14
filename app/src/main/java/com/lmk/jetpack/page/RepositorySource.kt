package com.lmk.jetpack.page

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lmk.architecture.util.singleToast
import com.lmk.jetpack.bean.Repository
import com.lmk.jetpack.net.RetrofitClient
import com.apkfuns.logutils.LogUtils

/**
 * @author
 * @date 2022/2/8
 * @description
 */
class RepositorySource(
    private val keyWords: String
) : PagingSource<Int, Repository>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        val currPage = params.key ?: 1
        LogUtils.e("key=${params.key},size=${params.loadSize}");
        return try {
            val response =
                RetrofitClient.githubService.searchRepositories(keyWords, params.loadSize, currPage)
            LoadResult.Page(
                response.data ?: listOf(),
                if (currPage <= 1) null else currPage - 1,
                if (response.data?.size ?: 0 < params.loadSize) null else currPage + 1
            )
        } catch (e: Throwable) {
            LogUtils.e(e)
            singleToast(e.localizedMessage)
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        return null
    }

}