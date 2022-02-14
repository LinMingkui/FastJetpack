package com.lmk.jetpack.net

import com.lmk.architecture.net.base.BaseRetrofitClient
import com.lmk.architecture.net.base.MyInterceptor
import okhttp3.Interceptor

object RetrofitClient : BaseRetrofitClient() {

    val wanService by lazy { getService(WanApi::class.java, WanApi.BASE_URL) }

    val githubService by lazy { getService(GithubApi::class.java, GithubApi.BASE_URL) }

    override fun getHttpLoggingInterceptor(): Interceptor {
        return MyInterceptor()
    }
}
