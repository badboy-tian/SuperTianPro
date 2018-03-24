package com.i7play.supertian.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by Administrator on 2017/6/29.
 * 网络相关的类
 */

object NetworkUtil {
    fun isNetworkAvailable(activity: Context): Boolean {
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        if (connectivityManager == null) {
            return false
        } else {
            val networkInfo = connectivityManager.allNetworkInfo

            if (networkInfo != null && networkInfo.size > 0) {
                for (i in networkInfo.indices) {

                    if (networkInfo[i].state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
                }
            }
        }
        return false
    }
}
