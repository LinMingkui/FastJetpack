package com.lmk.jetpack.bean

/**
 * author : wutao
 * time   : 2020/01/01
 * desc   :
 * version: 1.0
 */
class WxArticleBean {
    /**
     * id : 408
     * name : 鸿洋
     * order : 190000
     * visible : 1
     */
    var id = 0
    var name: String? = null
    var visible = 0

    override fun toString(): String {
        return "TestBean(id=$id, name=$name, visible=$visible)"
    }
}