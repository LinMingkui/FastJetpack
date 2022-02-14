package com.lmk.jetpack.net

import com.lmk.architecture.net.entity.ApiResponse
import com.lmk.jetpack.bean.Article
import com.lmk.jetpack.bean.WanPage
import com.lmk.jetpack.bean.User
import com.lmk.jetpack.bean.WxArticleBean
import retrofit2.http.*

interface WanApi {

    @GET("wxarticle/chapters/json")
    suspend fun getWxArticle(): ApiResponse<List<WxArticleBean>>

    @GET("abc/chapters/json")
    suspend fun getWxArticleError(): ApiResponse<List<WxArticleBean>>

    @GET("article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): ApiResponse<WanPage<Article>>

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