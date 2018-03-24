package com.i7play.supertian.listener

import android.view.View
import java.util.*

/**
 * Created by tian on 2018/1/31.
 */
abstract class NoDoubleClickListener : View.OnClickListener {
    private val MIN_CLICK_DELAY_TIME = 1000
    private var lastClickTime: Long = 0

    override fun onClick(v: View) {
        val currentTime = Calendar.getInstance().timeInMillis
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime
            onNoDoubleClick(v)
        }
    }

    abstract fun onNoDoubleClick(v: View)
}