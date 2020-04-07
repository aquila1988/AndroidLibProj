package com.aquila.lib.base;

import android.view.View;

/***
 * @date 创建时间 2020/3/23 10:22
 * @author 作者: W.YuLong
 * @description
 */
public interface OnViewClickListener {
    /* 通用的点击事件接管*/
    <T> void onClickAction(View v, String str, T t);
}
