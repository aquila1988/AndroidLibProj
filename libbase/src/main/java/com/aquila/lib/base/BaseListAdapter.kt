package com.aquila.lib.base

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.util.*

/***
 * @date 创建时间 2019-09-26 13:09
 * @author 作者: W.YuLong
 * @description  ListAdapter的基类
 */
abstract class BaseListAdapter<T, VH : BaseViewHolder> : BaseAdapter() {
    private var dataList: MutableList<T>? = null


    private var dataCount: Int = 0

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var convertView = view
        var holder: VH? = null
        if (convertView == null) {
            holder = onCreateViewHolder(parent, getViewType(position))
            convertView = holder.itemView
            convertView.tag = holder
        } else {
            holder = convertView.tag as VH
        }
        onBindHolder(holder, position)
        return convertView
    }


    abstract fun onBindHolder(holder: VH, position: Int)

    abstract fun onCreateViewHolder(parent: ViewGroup, type: Int): VH

    fun getViewType(position: Int): Int {
        return position
    }


    /**
     * 设置数据源
     */
    fun setDataList(dataList: MutableList<T>) {
        this.dataList = dataList
        dataCount = getDateCount()
        notifyDataSetChanged()
    }


    fun getDateCount(): Int {
        return if (dataList.isNullOrEmpty()) 0 else dataList!!.size
    }

    /***
     * 添加数据的列表
     * @param list
     */
    fun addDataList(list: MutableList<T>) {
        if (this.dataList == null) {
            this.dataList = list
        } else {
            dataList!!.addAll(list)
        }
        dataCount = getDateCount()
        notifyDataSetChanged()
    }

    fun getDataList(): MutableList<T>? {
        return dataList
    }

    /**
     * 添加一条数据
     */
    fun addOneData(t: T) {
        if (dataList == null) {
            dataList = ArrayList()
        }
        dataList!!.add(t)
        dataCount = getDateCount()
        notifyDataSetChanged()
    }

    /***
     * 删除一条数据
     */
    fun deleteItem(t: T): Boolean {
        var flag = false
        if (dataList != null) {
            flag = dataList!!.remove(t)
            dataCount = getDateCount()
            notifyDataSetChanged()
        }
        return flag
    }

    /***
     * 获取数据在索引的位置
     */
    fun getDataPosition(t: T): Int {
        return if (dataList == null) {
            -1
        } else dataList!!.indexOf(t)
    }

    override fun getCount(): Int {
        return dataCount
    }

    override fun getItem(position: Int): T? {
        return if (dataList == null) {
            null
        } else dataList!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}
