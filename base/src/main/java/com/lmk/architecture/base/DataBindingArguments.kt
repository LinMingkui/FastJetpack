package com.lmk.architecture.base

/**
 * @author 再战科技
 * @date 2021/12/30
 * @description
 */
class DataBindingArguments() {
    val map = mutableMapOf<Int, Any>()

    constructor(brId: Int, value: Any) : this() {
        map[brId] = value
    }

    fun addArgument(brId: Int, value: Any): DataBindingArguments {
        map[brId] = value
        return this;
    }
}