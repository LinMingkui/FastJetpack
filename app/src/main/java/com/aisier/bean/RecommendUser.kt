package com.aisier.bean

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName


/**
 * @author 再战科技
 * @date 2022/2/8
 * @description
 */
data class RecommendUser(
    @SerializedName("age")
    val age: Int = 0,
    @SerializedName("avatar")
    val avatar: String = "",
    @SerializedName("completion")
    val completion: Int = 0,
    @SerializedName("country")
    val country: String = "",
    @SerializedName("hots")
    val hots: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("img_count")
    val imgCount: Int = 0,
    @SerializedName("isVip")
    val isVip: Boolean = false,
    @SerializedName("lan")
    val lan: Int = 0,
    @SerializedName("last_msg")
    val lastMsg: String = "",
    @SerializedName("like_time")
    val likeTime: String = "",
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("region")
    val region: String = "",
    @SerializedName("remark")
    val remark: String = "",
    @SerializedName("server_num")
    val serverNum: Int = 0,
    @SerializedName("sex")
    val sex: Int = 0,
    @SerializedName("talkM")
    val talkM: Boolean = false
) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<RecommendUser>() {
            override fun areItemsTheSame(oldItem: RecommendUser, newItem: RecommendUser): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RecommendUser,
                newItem: RecommendUser
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}