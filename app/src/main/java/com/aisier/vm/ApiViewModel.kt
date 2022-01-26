package com.aisier.vm

import androidx.lifecycle.MutableLiveData
import com.aisier.architecture.base.BaseViewModel
import com.aisier.bean.User
import com.aisier.bean.WxArticleBean
import com.aisier.net.WxArticleRepository
import com.aisier.architecture.net.entity.ApiResponse
import com.aisier.architecture.net.launchRequestOnIO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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
    // 使用StateFlow 替代livedata
//    val wxArticleLiveData = StateMutableLiveData<List<WxArticleBean>>()

    private val _uiState = MutableStateFlow<ApiResponse<List<WxArticleBean>>>(ApiResponse())
    val uiState: StateFlow<ApiResponse<List<WxArticleBean>>> = _uiState.asStateFlow()

    //    suspend fun requestNet() {
//        _uiState.value = repository.fetchWxArticleFromNet()
//        launchRequestWithLoading { repository.fetchWxArticleFromNet() }.collect {
//            articleLiveData.value = it
//        }
//    }
    fun requestNet() {
//        viewModelScope.launch(Dispatchers.IO) {
//            launchRequestWithLoading { repository.fetchWxArticleFromNet() }.collect {
//                articleLiveData.postValue(it)
//            }
//        }
//        launchRequestWithLoadingOnIO(articleLiveData) { repository.fetchWxArticleFromNet() }
        launchRequestOnIO(articleLiveData, requestBlock = { repository.fetchWxArticleFromNet() })
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