package com.aisier.bean

import com.google.gson.annotations.SerializedName


/**
 * @author 再战科技
 * @date 2022/2/9
 * @description
 */
data class WanPage<T>(
    @SerializedName("curPage")
    val curPage: Int = 0,
    @SerializedName("offset")
    val offset: Int = 0,
    @SerializedName("over")
    val over: Boolean = false,
    @SerializedName("pageCount")
    val pageCount: Int = 0,
    @SerializedName("size")
    val size: Int = 0,
    @SerializedName("total")
    val total: Int = 0,
    @SerializedName("datas")
    val datas: List<T>? = null
)