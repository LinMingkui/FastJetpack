package com.aisier.net

import com.aisier.architecture.net.base.BaseRetrofitClient
import okhttp3.OkHttpClient

object GithubClient : BaseRetrofitClient() {

    val service by lazy { getService(GithubApi::class.java, GithubApi.BASE_URL) }

    override fun handleBuilder(builder: OkHttpClient.Builder) = Unit

}
