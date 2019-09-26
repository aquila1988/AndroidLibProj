package com.aquila.lib.base

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import java.util.*

/***
 *@date 创建时间 2019-09-26 16:50
 *@author 作者: W.YuLong
 *@description  ViewPager的基类Adapter
 */
abstract class BasePagerAdapter<T> : PagerAdapter() {
    private var dataList: MutableList<T>? = null
        get

    /***
     * 获取实际数据的数目
     */
    var realCount: Int = 0

    fun updateDataCount() {
        realCount = if (dataList.isNullOrEmpty()) 0 else dataList!!.size
    }

    /**** 添加数据列表*/
    fun addDataList(list: MutableList<T>) {
        if (dataList == null) {
            dataList = ArrayList()
        }
        dataList!!.addAll(list)
        updateDataCount()
        notifyDataSetChanged()
    }

    /* 添加一条数据*/
    fun addOneData(t: T) {
        if (dataList == null) {
            dataList = ArrayList()
        }
        dataList!!.add(t)
        updateDataCount()
        notifyDataSetChanged()
    }

    /* 删除一条数据*/
    fun deleteItem(t: T): Boolean {
        var flag = false
        if (dataList != null) {
            flag = dataList!!.remove(t)
            updateDataCount()
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


    /**
     * 获取制定位置的数据
     */
    fun getItem(position: Int): T? {
        if (dataList.isNullOrEmpty()) {
            return null
        } else {
            return dataList!![position % realCount]
        }
    }

    /**
     * 设置数据源
     */
    fun setDataList(list: MutableList<T>) {
        dataList = list
        updateDataCount()
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return realCount
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as View)
    }

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view === any
    }
}
