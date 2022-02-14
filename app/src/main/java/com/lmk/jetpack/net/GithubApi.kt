package com.lmk.jetpack.net

import com.lmk.architecture.net.entity.ApiResponse
import com.lmk.jetpack.bean.Repository
import retrofit2.http.GET
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