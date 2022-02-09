package com.aisier.net

import com.aisier.architecture.net.entity.ApiResponse
import com.aisier.bean.Article
import com.aisier.bean.Page
import com.aisier.bean.User
import com.aisier.bean.WxArticleBean
import retrofit2.http.*

interface WanApi {

    @GET("wxarticle/chapters/json")
    suspend fun getWxArticle(): ApiResponse<List<WxArticleBean>>

    @GET("abc/chapters/json")
    suspend fun getWxArticleError(): ApiResponse<List<WxArticleBean>>

    @GET("article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): ApiResponse<Page<Article>>

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") userName: String,
        @Field("password") passWord: String
    ): ApiResponse<User?>

    companion object {
        const val BASE_URL = "https://wanandroid.com/"
    }
}