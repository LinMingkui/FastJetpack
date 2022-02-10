package com.aisier.net

import com.aisier.architecture.net.entity.ApiResponse
import com.aisier.bean.RecommendUser
import com.aisier.bean.Repository
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") keyWords: String,
        @Query("per_page") limit: Int,
        @Query("page") page: Int
    ): ApiResponse<List<Repository>>

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}