package com.aisier.net

import com.aisier.architecture.net.base.BaseRepository
import com.aisier.architecture.net.entity.ApiResponse
import com.aisier.bean.Article
import com.aisier.bean.User
import com.aisier.bean.WanPage
import com.aisier.bean.WxArticleBean

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