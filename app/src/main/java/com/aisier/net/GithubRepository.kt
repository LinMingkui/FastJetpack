package com.aisier.net

import com.aisier.architecture.net.base.BaseRepository
import com.aisier.architecture.net.entity.ApiResponse
import com.aisier.bean.RecommendUser

/**
 * @author 再战科技
 * @date 2022/2/8
 * @description
 */
object GithubRepository : BaseRepository() {

    private val service by lazy {
        GithubClient.service
    }

    suspend fun getRecommendUser(page: Int, pageSize: Int): ApiResponse<List<RecommendUser>> {
        return executeHttp {
            service.getRecommendUser("0000", page, pageSize)
        }
    }

}