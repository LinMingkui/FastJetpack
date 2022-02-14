package com.aisier.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.aisier.architecture.base.BaseViewModel
import com.aisier.architecture.net.entity.ApiResponse
import com.aisier.architecture.net.launchRequestWithLoading
import com.aisier.architecture.net.launchRequestWithLoadingOnIO
import com.aisier.architecture.net.parseData
import com.aisier.bean.User
import com.aisier.bean.WxArticleBean
import com.aisier.net.GithubRepository
import com.aisier.net.WanRepository
import com.aisier.ui.adapter.RefreshStateAdapter
import com.aisier.ui.adapter.RepositoryAdapter
import com.apkfuns.logutils.LogUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * <pre>
 * @author : wutao
 * time   : 2019/08/17
 * desc   :
 * version: 1.0
</pre> *
 */
class ApiViewModel : BaseViewModel() {

    val adapter = RepositoryAdapter()

    val concatAdapter = adapter.withLoadStateHeaderAndFooter(
        RefreshStateAdapter { adapter.retry() },
        RefreshStateAdapter { adapter.retry() })


    val articleLiveData: MutableLiveData<ApiResponse<List<WxArticleBean>>> = MutableLiveData()

    private val _uiState = MutableStateFlow<ApiResponse<List<WxArticleBean>>>(ApiResponse())

    fun fetchWxArticleFromNet() {
        viewModelScope.launch {
            launchRequestWithLoading { WanRepository.fetchWxArticleFromNet() }.collect {
                it.parseData { LogUtils.i(it.javaClass.simpleName) }
                articleLiveData.value = it
            }
        }
        launchRequestWithLoadingOnIO(articleLiveData) { WanRepository.fetchWxArticleFromNet() }
    }

    suspend fun requestNetError() {
        _uiState.value = WanRepository.fetchWxArticleError()
    }

    /**
     * 场景：不需要监听数据变化
     */
    suspend fun login(username: String, password: String): ApiResponse<User?> {
        return WanRepository.login(username, password)
    }

    fun searchRepository(keyWords: String) {
        viewModelScope.launch {
            GithubRepository.searchRepositories(keyWords)
                .cachedIn(viewModelScope)
                .collectLatest {
                    adapter.submitData(it)
                }
        }
    }
}