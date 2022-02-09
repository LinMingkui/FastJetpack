package com.aisier.net

import com.aisier.architecture.net.base.BaseRepository
import com.aisier.architecture.net.entity.ApiResponse
import com.aisier.bean.Article
import com.aisier.bean.Page
import com.aisier.bean.User
import com.aisier.bean.WxArticleBean

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

    suspend fun getArticleList(page: Int): ApiResponse<Page<Article>> {
        return executeHttp {
            mService.getArticleList(page)
        }
    }

}