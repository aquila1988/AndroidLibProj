package com.aquila.lib.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/***
 * @date 创建时间 2018/3/22 13:21
 * @author 作者: yulong
 * @description 基类的ViewHolder
 */
abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    constructor(
        viewGroup: ViewGroup,
        layoutId: Int
    ) : this(LayoutInflater.from(viewGroup.context).inflate(layoutId, viewGroup, false))


    open fun <T> initUIData(t: T, position: Int) {
        initUIData(t)
    }

    open fun <T> initUIData(t: T) {}

    open fun onSelectPosition(isSelected: Boolean) {}

}
