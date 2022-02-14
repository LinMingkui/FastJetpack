package com.aisier.architecture.net.base

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aisier.architecture.net.entity.*
import com.aisier.architecture.util.singleToast
import com.apkfuns.logutils.LogUtils

open class BaseRepository {

    suspend fun <T> executeHttp(block: suspend () -> ApiResponse<T>): ApiResponse<T> {
        runCatching {
            block.invoke()
        }.onSuccess { data: ApiResponse<T> ->
            return handleHttpOk(data)
        }.onFailure { e ->
            return handleHttpError(e)
        }
        return ApiEmptyResponse()
    }

    fun <Value : Any> executePagingHttp(
        pageSize: Int = 40,
        initialKey: Int = 1,
        repository: suspend (page: Int, pageSize: Int) -> ApiResponse<List<Value>>
    ) = Pager(
        PagingConfig(pageSize, initialLoadSize = pageSize, prefetchDistance = pageSize / 2),
        initialKey,
    ) {
        object : PagingSource<Int, Value>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Value> {
                val currPage = params.key ?: 1
                return try {
                    val response = repository.invoke(currPage, params.loadSize)
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

            override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
                return null
            }
        }
    }.flow


    /**
     * 非后台返回错误，捕获到的异常
     */
    private fun <T> handleHttpError(e: Throwable): ApiErrorResponse<T> {
        LogUtils.e(e)
        return ApiErrorResponse(e)
    }

    /**
     * 返回200，但是还要判断isSuccess
     */
    private fun <T> handleHttpOk(data: ApiResponse<T>): ApiResponse<T> {
        return if (data.isSuccess) {
            getHttpSuccessResponse(data)
        } else {
            ApiFailedResponse(data.errorCode, data.errorMsg)
        }
    }

    /**
     * 成功和数据为空的处理
     */
    private fun <T> getHttpSuccessResponse(response: ApiResponse<T>): ApiResponse<T> {
        val data = response.data
        return if (data == null || (data is List<*> && data.isEmpty())) {
            ApiEmptyResponse()
        } else {
            ApiSuccessResponse(data)
        }
    }
}