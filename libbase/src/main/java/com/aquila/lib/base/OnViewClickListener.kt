package com.aquila.lib.base

import android.view.View

/***
 *@date 创建时间 2019-09-26 13:05
 *@author 作者: W.YuLong
 *@description  点击事件的接口
 */
interface OnViewClickListener {

    /* 通用的点击事件接管*/
    fun <T> onClickAction(v: View, tag: String, t: T?)
}
