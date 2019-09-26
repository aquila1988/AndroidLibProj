package com.aquila.lib.base


import androidx.recyclerview.widget.RecyclerView
import java.util.*

/***
 * @date 创建时间 2018/3/22 13:28
 * @author 作者: yulong
 * @description 基类的RecyclerAdapter
 */
abstract class BaseRecycleAdapter<T, VH : BaseViewHolder> : RecyclerView.Adapter<VH>() {

    private var dataCount = 0

    private var dataList: MutableList<T>? = null
        get


    fun deleteItemIndex(position:Int) {
        notifyItemRemoved(position)
        dataList!!.removeAt(position)
        updateDataCount()
    }

    fun deleteItem(t: T) {
        notifyItemRemoved(getDataPosition(t))
        dataList!!.remove(t)
        updateDataCount()
    }

    fun getDataFromPosition(position: Int): T? {
        return if (!dataList.isNullOrEmpty() && position in 0 until dataCount) dataList!![position] else null
    }

    /***
     * 获取数据在索引的位置
     */
    fun getDataPosition(t: T): Int {
        return dataList?.let {
            it.indexOf(t)
        } ?: -1
    }


    //添加一条数据
    fun addOneData(t: T) {
        if (dataList == null) {
            dataList = ArrayList()
        }
        dataList!!.add(t)
        updateDataCount()
        notifyDataSetChanged()
    }


    //设置数据源
    fun setDataList(list: MutableList<T>) {
        this.dataList = list
        updateDataCount()
        notifyDataSetChanged()
    }

    //添加一组数据
    fun addDataList(list: MutableList<T>?) {
        if (list.isNullOrEmpty()) {
            return
        }
        if (dataList == null) {
            dataList = ArrayList()
        }
        dataList!!.addAll(list)
        updateDataCount()
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        val t = dataList!![position]
        holder.initUIData(t, position)
    }

    private fun updateDataCount() {
        if (dataList.isNullOrEmpty()) {
            dataCount = 0
        } else {
            dataCount = dataList!!.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return dataCount
    }
}
