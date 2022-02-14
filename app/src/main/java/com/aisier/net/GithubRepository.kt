package com.aisier.net

import com.aisier.architecture.net.base.BaseRepository

/**
 * @author 再战科技
 * @date 2022/2/8
 * @description
 */
object GithubRepository : BaseRepository() {

    private val githubService by lazy {
        RetrofitClient.githubService
    }

    fun searchRepositories(
        keyWords: String
    ) = executePagingHttp { page: Int, PageSize: Int ->
        githubService.searchRepositories(
            keyWords,
            PageSize,
            page
        )
    }


}