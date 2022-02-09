package com.aisier.net

import com.aisier.architecture.net.entity.ApiResponse
import com.aisier.bean.RecommendUser
import retrofit2.http.*

interface MeetiApi {

    @FormUrlEncoded
    @POST("api/v1/getHots/")
    suspend fun getRecommendUser(
        @Field("token") token: String?,
        @Field("page") page: Int,
        @Field("limit") limit: Int
    ): ApiResponse<List<RecommendUser>>

    @GET("/r/{subreddit}/hot.json")
    suspend fun getTop(
        @Path("subreddit") subreddit: String,
        @Query("limit") limit: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ):ApiResponse<List<RecommendUser>>

    companion object {
//        const val MEETI_URL = "https://test.meeti.link/"
        const val MEETI_URL = "https://www.reddit.com/"
    }
}