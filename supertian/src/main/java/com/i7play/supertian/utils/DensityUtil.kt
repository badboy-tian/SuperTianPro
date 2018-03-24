package com.i7play.supertian.utils

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

/**
 * Created by Administrator on 2017/6/28.
 * 分辨率相关的工具类
 */

object DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 获取屏幕分辨率
     * @param context
     * @return
     */
    fun getScreenDispaly(context: Context): IntArray {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        return intArrayOf(size.x, size.y)
    }
}
