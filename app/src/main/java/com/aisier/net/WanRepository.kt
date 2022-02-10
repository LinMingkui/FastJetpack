package com.aisier.net

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.aisier.architecture.net.base.BaseRepository
import com.aisier.architecture.net.entity.ApiResponse
import com.aisier.bean.User
import com.aisier.bean.WxArticleBean
import com.aisier.page.ArticleSource

object WanRepository : BaseRepository() {

    private val mService by lazy {
        RetrofitClient.service
    }

//    suspend fun fetchWxArticleFromNet(): ApiResponse<List<WxArticleBean>> {
//        return executeHttp {
//            mService.getWxArticle()
//        }
//    }

    suspend fun fetchWxArticleFromNet(): ApiResponse<List<WxArticleBean>> {
        return executeHttp {
            mService.getWxArticle()
        }
    }

    suspend fun fetchWxArticleError(): ApiResponse<List<WxArticleBean>> {
        return executeHttp {
            mService.getWxArticleError()
        }
    }

    suspend fun login(username: String, password: String): ApiResponse<User?> {
        return executeHttp {
            mService.login(username, password)
        }
    }

//    suspend fun getArticleList(page: Int): ApiResponse<Page<Article>> {
//        return executeHttp {
//            mService.getArticleList(page)
//        }
//    }

    fun getArticleList(page: Int) = Pager(
        PagingConfig(40, initialLoadSize = 40, prefetchDistance = 5),
        0,
    ) {
        ArticleSource()
    }.flow


}