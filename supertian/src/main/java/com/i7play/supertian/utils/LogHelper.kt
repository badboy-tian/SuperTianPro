package io.haobi.wallet.utils

import io.haobi.wallet.BuildConfig
import com.orhanobut.logger.Logger

/**
 * Created by Administrator on 2017/7/1.
 * 打印类
 */

object LogHelper {
    private val DEBUG = BuildConfig.DEBUG
    private val TAG = "io.haobi.wallet"

    fun LogE(`object`: Any) {
        if (DEBUG){
            Logger.e(`object`.toString())
        }
    }

    fun LogE(cls: Class<*>, `object`: Any) {
        if (DEBUG)
            Logger.e(TAG + " " + cls.name + " : " + `object`.toString())
    }
}
