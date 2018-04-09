package com.i7play.supertian.utils

import com.orhanobut.logger.Logger

/**
 * Created by Administrator on 2017/7/1.
 * 打印类
 */

object LogHelper {
    fun LogE(`object`: Any) {
        Logger.e(`object`.toString())
    }

    fun LogE(cls: Class<*>, `object`: Any) {
        Logger.e(" " + cls.name + " : " + `object`.toString())
    }
}
