package com.aisier.net

import com.aisier.architecture.net.entity.ApiResponse
import com.aisier.bean.RecommendUser
import com.aisier.bean.User
import com.aisier.bean.WxArticleBean
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MeetiApi {

    @FormUrlEncoded
    @POST("api/v1/getHots/")
    fun getRecommendUser(
        @Field("token") token: String?,
        @Field("page") page: Int,
        @Field("limit") limit: Int
    ): ApiResponse<List<RecommendUser>>


    companion object {
        const val MEETI_URL = "https://test.meeti.link/"
    }
}