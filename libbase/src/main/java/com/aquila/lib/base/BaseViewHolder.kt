package com.aquila.lib.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/***
 *@date 创建时间 2019-09-27 14:19
 *@author 作者: W.YuLong
 *@description 基类的ViewHolder
 */
abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    constructor(viewGroup: ViewGroup, layoutId: Int) : this(LayoutInflater.from(viewGroup.context).inflate(layoutId, viewGroup, false))

    open fun <T> initUIData(t: T, position: Int) {
        initUIData(t)
    }

    open fun <T> initUIData(t: T) {}
}
