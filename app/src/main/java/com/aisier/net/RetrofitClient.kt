package com.aisier.net

import com.aisier.architecture.net.base.BaseRetrofitClient
import com.aisier.architecture.net.base.MyInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient

object RetrofitClient : BaseRetrofitClient() {

    val service by lazy { getService(WanApi::class.java, WanApi.BASE_URL) }

    override fun getHttpLoggingInterceptor(): Interceptor {
        return MyInterceptor()
    }
}
