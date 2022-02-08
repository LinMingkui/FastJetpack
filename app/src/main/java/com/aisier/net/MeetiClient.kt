package com.aisier.net

import com.aisier.architecture.net.base.BaseRetrofitClient
import okhttp3.OkHttpClient

object MeetiClient : BaseRetrofitClient() {

    val service by lazy { getService(MeetiApi::class.java, MeetiApi.MEETI_URL) }

    override fun handleBuilder(builder: OkHttpClient.Builder) = Unit

}
