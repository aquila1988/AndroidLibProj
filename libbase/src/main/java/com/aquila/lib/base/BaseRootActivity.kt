package com.aquila.lib.base

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


/***
 * @date 创建时间 2018/3/22 11:07
 * @author 作者: W.YuLong
 * @description 所有Activity的基类
 */
open class BaseRootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarUtil.setColor(this, Color.WHITE)
            StatusBarUtil.setStatusBarLightMode(this)
        }
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        enterPendingAnim()
    }

    override fun finish() {
        super.finish()
        exitPendingAnim()
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
        enterPendingAnim()
    }

    fun exitPendingAnim() {
        overridePendingTransition(R.anim.base_anim_normal, R.anim.base_slide_out_right)
    }

    fun enterPendingAnim() {
        overridePendingTransition(R.anim.base_slide_in_right, R.anim.base_anim_normal)
    }

}

