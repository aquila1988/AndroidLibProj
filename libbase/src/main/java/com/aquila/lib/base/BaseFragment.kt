package com.aquila.lib.base

import android.content.Intent
import androidx.fragment.app.Fragment


/***
 * @date 创建时间 2018/3/22 13:20
 * @author 作者: W.YuLong
 * @description 基类的Fragment
 */
open class BaseFragment : Fragment() {

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        enterPendingAnim()
    }


    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
        enterPendingAnim()
    }


    private fun enterPendingAnim() {
        activity!!.overridePendingTransition(R.anim.base_slide_in_right, R.anim.base_anim_normal)
    }

}
