package com.aisier.net

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.aisier.architecture.net.base.BaseRepository
import com.aisier.page.RepositorySource

/**
 * @author 再战科技
 * @date 2022/2/8
 * @description
 */
object GithubRepository : BaseRepository() {

    private val service by lazy {
        GithubClient.service
    }

    fun searchRepositories(
        keyWords: String,
        pageSize: Int = 20
    ) = Pager(
        PagingConfig(pageSize, initialLoadSize = pageSize, prefetchDistance = pageSize / 2),
        1,
    ) {
        RepositorySource(keyWords)
    }.flow

}