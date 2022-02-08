package com.aisier.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.aisier.architecture.base.BaseViewModel
import com.aisier.architecture.net.entity.ApiResponse
import com.aisier.architecture.net.launchRequestWithLoading
import com.aisier.architecture.net.launchRequestWithLoadingOnIO
import com.aisier.architecture.net.parseData
import com.aisier.bean.User
import com.aisier.bean.WxArticleBean
import com.aisier.net.WxArticleRepository
import com.aisier.page.RecommendUserSource
import com.aisier.ui.RecommendAdapter
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

    private val adapter = RecommendAdapter()

    private val repository by lazy { WxArticleRepository() }

    val articleLiveData: MutableLiveData<ApiResponse<List<WxArticleBean>>> = MutableLiveData()

    private val _uiState = MutableStateFlow<ApiResponse<List<WxArticleBean>>>(ApiResponse())
    fun fetchWxArticleFromNet() {
        viewModelScope.launch {
            launchRequestWithLoading({ repository.fetchWxArticleFromNet() }).collect {
                it.parseData { LogUtils.i(it.javaClass.simpleName) }
                articleLiveData.value = it
            }
        }
        launchRequestWithLoadingOnIO(articleLiveData, { repository.fetchWxArticleFromNet() })
    }

    suspend fun requestNetError() {
        _uiState.value = repository.fetchWxArticleError()
    }

    /**
     * 场景：不需要监听数据变化
     */
    suspend fun login(username: String, password: String): ApiResponse<User?> {
        return repository.login(username, password)
    }

    fun getRecommendUser() {
        viewModelScope.launch {
            Pager(
                PagingConfig(20),
                1,
            ) {
                RecommendUserSource()
            }.flow.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}