package com.aisier.vm

import androidx.lifecycle.MutableLiveData
import com.aisier.architecture.base.BaseViewModel
import com.aisier.architecture.net.entity.ApiResponse
import com.aisier.architecture.net.launchRequestOnIO
import com.aisier.architecture.net.launchRequestWithLoadingOnIO
import com.aisier.bean.User
import com.aisier.bean.WxArticleBean
import com.aisier.net.WxArticleRepository
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * <pre>
 * @author : wutao
 * time   : 2019/08/17
 * desc   :
 * version: 1.0
</pre> *
 */
class ApiViewModel : BaseViewModel() {

    private val repository by lazy { WxArticleRepository() }

    val articleLiveData: MutableLiveData<ApiResponse<List<WxArticleBean>>> = MutableLiveData()

    private val _uiState = MutableStateFlow<ApiResponse<List<WxArticleBean>>>(ApiResponse())
    fun fetchWxArticleFromNet() {
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
}