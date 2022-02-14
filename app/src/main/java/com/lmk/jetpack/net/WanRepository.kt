package com.lmk.jetpack.net

import com.lmk.architecture.net.base.BaseRepository
import com.lmk.architecture.net.entity.ApiResponse
import com.lmk.jetpack.bean.Article
import com.lmk.jetpack.bean.User
import com.lmk.jetpack.bean.WanPage
import com.lmk.jetpack.bean.WxArticleBean

object WanRepository : BaseRepository() {

    private val wanService by lazy {
        RetrofitClient.wanService
    }

    suspend fun fetchWxArticleFromNet(): ApiResponse<List<WxArticleBean>> {
        return executeHttp {
            wanService.getWxArticle()
        }
    }

    suspend fun fetchWxArticleError(): ApiResponse<List<WxArticleBean>> {
        return executeHttp {
            wanService.getWxArticleError()
        }
    }

    suspend fun login(username: String, password: String): ApiResponse<User?> {
        return executeHttp {
            wanService.login(username, password)
        }
    }

    suspend fun getArticleList(page: Int): ApiResponse<WanPage<Article>> {
        return executeHttp {
            wanService.getArticleList(page)
        }
    }

}