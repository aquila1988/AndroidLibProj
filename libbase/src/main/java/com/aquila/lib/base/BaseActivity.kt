package com.aquila.lib.base

import android.os.Bundle
import android.view.View

import com.aquila.lib.swipeBack.SwipeBackActivityBase
import com.aquila.lib.swipeBack.SwipeBackActivityHelper
import com.aquila.lib.swipeBack.SwipeBackLayout
import com.aquila.lib.swipeBack.SwipeBackUtils

/***
 *@date 创建时间 2019-09-26 21:04
 *@author 作者: W.YuLong
 *@description  基类Activity,支持右滑返回上一页
 */
open class BaseActivity : BaseRootActivity(), SwipeBackActivityBase {

    private var mHelper: SwipeBackActivityHelper? = null

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mHelper?.let {
            it.onPostCreate()
        }

    }

    override fun <T : View> findViewById(id: Int): T {
        val v = super.findViewById<T>(id)
        return if (v == null && mHelper != null) {
            mHelper!!.findViewById(id)
        } else v
    }

    override fun getSwipeBackLayout(): SwipeBackLayout? {
        return mHelper?.swipeBackLayout
    }

    override fun setSwipeBackEnable(enable: Boolean) {
        swipeBackLayout?.setEnableGesture(enable)
    }

    override fun scrollToFinishActivity() {
        SwipeBackUtils.convertActivityToTranslucent(this)
        swipeBackLayout?.scrollToFinishActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHelper = SwipeBackActivityHelper(this)
        mHelper?.onActivityCreate()
    }


}
