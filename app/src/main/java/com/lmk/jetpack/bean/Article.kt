package com.lmk.jetpack.bean

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName


/**
 * @author 再战科技
 * @date 2022/2/9
 * @description
 */
data class Article(
    @SerializedName("apkLink")
    val apkLink: String = "",
    @SerializedName("audit")
    val audit: Int = 0,
    @SerializedName("author")
    val author: String = "",
    @SerializedName("canEdit")
    val canEdit: Boolean = false,
    @SerializedName("chapterId")
    val chapterId: Int = 0,
    @SerializedName("chapterName")
    val chapterName: String = "",
    @SerializedName("collect")
    val collect: Boolean = false,
    @SerializedName("courseId")
    val courseId: Int = 0,
    @SerializedName("desc")
    val desc: String = "",
    @SerializedName("descMd")
    val descMd: String = "",
    @SerializedName("envelopePic")
    val envelopePic: String = "",
    @SerializedName("fresh")
    val fresh: Boolean = false,
    @SerializedName("host")
    val host: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("link")
    val link: String = "",
    @SerializedName("niceDate")
    val niceDate: String = "",
    @SerializedName("niceShareDate")
    val niceShareDate: String = "",
    @SerializedName("origin")
    val origin: String = "",
    @SerializedName("prefix")
    val prefix: String = "",
    @SerializedName("projectLink")
    val projectLink: String = "",
    @SerializedName("publishTime")
    val publishTime: Long = 0,
    @SerializedName("realSuperChapterId")
    val realSuperChapterId: Int = 0,
    @SerializedName("selfVisible")
    val selfVisible: Int = 0,
    @SerializedName("shareDate")
    val shareDate: Long = 0,
    @SerializedName("shareUser")
    val shareUser: String = "",
    @SerializedName("superChapterId")
    val superChapterId: Int = 0,
    @SerializedName("superChapterName")
    val superChapterName: String = "",
    @SerializedName("tags")
    val tags: List<Tag> = listOf(),
    @SerializedName("title")
    val title: String = "",
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("userId")
    val userId: Int = 0,
    @SerializedName("visible")
    val visible: Int = 0,
    @SerializedName("zan")
    val zan: Int = 0
) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

data class Tag(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = ""
)