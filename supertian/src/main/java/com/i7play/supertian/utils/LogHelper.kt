package com.i7play.supertian.utils

import com.i7play.supertian.BuildConfig
import com.orhanobut.logger.Logger

/**
 * Created by Administrator on 2017/7/1.
 * 打印类
 */

object LogHelper {
    private val DEBUG = BuildConfig.DEBUG

    fun LogE(`object`: Any) {
        if (DEBUG) {
            Logger.e(`object`.toString())
        }
    }

    fun LogE(cls: Class<*>, `object`: Any) {
        if (DEBUG)
            Logger.e(" " + cls.name + " : " + `object`.toString())
    }
}
