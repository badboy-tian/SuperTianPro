package com.i7play.supertian.manager

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import java.util.Collections
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by Administrator on 2017/8/9.
 * activity管理
 */

class ActivityManager private constructor() {


    /**
     * @return 作用说明 ：获取当前最顶部activity的实例
     */
    val topActivity: Activity?
        get() {
            var mBaseActivity: Activity? = null
            synchronized(mActivitys) {
                val size = mActivitys!!.size - 1
                if (size < 0) {
                    return null
                }
                mBaseActivity = mActivitys[size]
            }
            return mBaseActivity

        }


    /**
     * @return 作用说明 ：获取当前最顶部的acitivity 名字
     */
    val topActivityName: String?
        get() {
            var mBaseActivity: Activity? = null
            synchronized(mActivitys) {
                val size = mActivitys!!.size - 1
                if (size < 0) {
                    return null
                }
                mBaseActivity = mActivitys[size]
            }
            return mBaseActivity!!.javaClass.name
        }

    var isForeground = false//应用是否处于前端


    /**
     * @param activity 作用说明 ：添加一个activity到管理里
     */
    fun pushActivity(activity: Activity) {
        mActivitys!!.add(activity)
        //LogHelper.LogE("activityList:size:" + mActivitys.size());
    }


    /**
     * @param activity 作用说明 ：删除一个activity在管理里
     */
    fun popActivity(activity: Activity) {
        mActivitys!!.remove(activity)
        //LogHelper.LogE("activityList:size:" + mActivitys.size());
    }

    private fun registerActivityListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    /**
                     * 监听到 Activity创建事件 将该 Activity 加入list
                     */
                    pushActivity(activity)

                }


                override fun onActivityStarted(activity: Activity) {

                }


                override fun onActivityResumed(activity: Activity) {
                    isForeground = true
                }


                override fun onActivityPaused(activity: Activity) {
                    isForeground = false
                }


                override fun onActivityStopped(activity: Activity) {

                }


                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

                }


                override fun onActivityDestroyed(activity: Activity) {
                    if (null == mActivitys && mActivitys!!.isEmpty()) {
                        return
                    }
                    if (mActivitys.contains(activity)) {
                        /**
                         * 监听到 Activity销毁事件 将该Activity 从list中移除
                         */
                        popActivity(activity)
                    }
                }
            })
        }
    }

    lateinit var application: Application
    companion object {
        var instance = ActivityManager()

        fun init(application: Application) {
            instance.application = application
            instance.registerActivityListener()
        }

        private val mActivitys = Collections
                .synchronizedList(CopyOnWriteArrayList<Activity>())


        /**
         * get current Activity 获取当前Activity（栈中最后一个压入的）
         */
        fun currentActivity(): Activity? {
            return if (mActivitys == null || mActivitys.isEmpty()) {
                null
            } else mActivitys[mActivitys.size - 1]
        }


        /**
         * 结束当前Activity（栈中最后一个压入的）
         */
        fun finishCurrentActivity() {
            if (mActivitys == null || mActivitys.isEmpty()) {
                return
            }
            val activity = mActivitys[mActivitys.size - 1]
            finishActivity(activity)
        }


        /**
         * 结束指定的Activity
         */
        fun finishActivity(activity: Activity?) {
            var activity = activity
            if (mActivitys == null || mActivitys.isEmpty()) {
                return
            }
            if (activity != null) {
                mActivitys.remove(activity)
                activity.finish()
            }
        }


        /**
         * 结束指定类名的Activity
         */
        fun finishActivity(cls: Class<*>) {
            if (mActivitys == null || mActivitys.isEmpty()) {
                return
            }
            for (activity in mActivitys) {
                if (activity.javaClass == cls) {
                    finishActivity(activity)
                }
            }
        }


        /**
         * 按照指定类名找到activity
         */
        fun findActivity(cls: Class<*>): Activity? {
            var targetActivity: Activity? = null
            if (mActivitys != null) {
                for (activity in mActivitys) {
                    if (activity.javaClass == cls) {
                        targetActivity = activity
                        break
                    }
                }
            }
            return targetActivity
        }


        fun <T : Activity> isActivityExist(clz: Class<T>): Boolean {
            val res: Boolean
            val activity = findActivity(clz)
            if (activity == null) {
                res = false
            } else {
                res = !activity.isFinishing
            }

            return res
        }


        /**
         * 结束所有Activity
         */
        fun finishAllActivity() {
            if (mActivitys == null) {
                return
            }
            for (activity in mActivitys) {
                activity.finish()
            }
            mActivitys.clear()
        }


        /**
         * 退出应用程序
         */
        fun appExit() {
            try {
                finishAllActivity()
            } catch (e: Exception) {
            }

        }
    }
}
