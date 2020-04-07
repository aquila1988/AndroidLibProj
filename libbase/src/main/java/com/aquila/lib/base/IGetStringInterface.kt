package com.aquila.lib.base

/***
 * @date 创建时间 2020/4/7 16:05
 * @author 作者: W.YuLong
 * @description
 */
interface IGetStringInterface {
    fun getString(): String


}

open class IGetStringInterfaceImpl<T>(var bean: T) : IGetStringInterface {
    override fun getString(): String {
        return ""
    }
}