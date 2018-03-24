package com.i7play.supertian.network

import android.app.Activity
import android.app.ProgressDialog
import com.i7play.supertian.R
import com.i7play.supertian.ext.toast
import io.haobi.wallet.network.HttpResult
import com.i7play.supertian.utils.LogHelper
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.io.Serializable

/**
 * Created by Administrator on 2017/8/15.
 * 所有的响应应答类
 */

abstract class BaseObserver<T>() : Observer<T>, Serializable {
    var activity: Activity? = null

    constructor(activity: Activity?, currentReqNum: Int) : this() {
        this.currentReqNum = currentReqNum
        this.activity = activity
        make()
    }

    constructor(activity: Activity?) : this() {
        this.activity = activity
        make()
    }

    var progressDialog: ProgressDialog? = null
    //默认链接顺序
    var currentReqNum = 1

    init {
        make()
    }
    fun make() {
        activity?.let {
            progressDialog = ProgressDialog(it)
            progressDialog?.setMessage(it.getString(R.string.loading))
            progressDialog?.setCanceledOnTouchOutside(false)
            progressDialog?.setCancelable(false)
        }
    }

    fun show() {
        progressDialog?.let {
            it.show()
        }
    }

    fun show(msg: String) {
        progressDialog?.let {
            it.show()
        }
    }

    override fun onSubscribe(d: Disposable) {}

    override fun onNext(t: T) {
        progressDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }

        if (t is HttpResult<*>) {
            if ((t as HttpResult<*>).errno != 0) {
                onError(ApiException((t as HttpResult<*>).errno, (t as HttpResult<*>).msg))
            } else {
                _onNext(t)
            }
        } else {
            _onNext(t)
        }
    }

    override fun onError(e: Throwable) {
        //e.printStackTrace()
        e.message?.let {
            LogHelper.LogE(it)
        }

        progressDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }

        /*if (e is SocketTimeoutException) {
            activity?.let {
                it.toast(e.toString())
            }
        }else if (e is UnknownHostException){
            activity?.let {
                it.toast(e.toString())
            }
        }else{
            activity?.let {
                it.toast(e.toString())
            }
        }*/
        activity?.toast(e.message.toString())
        /*activity?.let {
            it.toast(it.getString(R.string.connect_timeout))
        }
        //activity?.toast(activity.getString(R.string.connect_timeout))
    } else if (e is ConnectException) {
        activity?.let {
            it.toast(it.getString(R.string.connect_interept))
        }
        //activity?.toast(activity.getString(R.string.connect_interept))
    } else if (e is ApiException) {
        e.message?.let {
            if (it.contains("未登录") || it.contains("not log in")) {
                jumpToLogin()
                return
            }
        }
        activity?.let {
            it.toast(it.getString(R.string.error) + e.message)
        }
        //activity?.toast(activity.getString(R.string.error) + e.message)
    } else if (e is UnknownHostException) {
        activity?.let {
            it.toast(it.getString(R.string.unknow_text))
        }
        //activity?.toast(activity.getString(R.string.unknow_text))
    } else {
        activity?.let {
            it.toast(it.getString(R.string.error) + e.message)
        }
        //activity?.toast(activity.getString(R.string.error) + e.message)
    }*/
    }

    /*fun jumpToLogin() {
        if (ActivityManager.isActivityExist(LoginActivity::class.java)) {
            return
        }
        User.getUser()?.clear()
        val i = Intent(activity, LoginActivity::class.java)
        i.putExtra(AppConstants.FromCodeKey, activity?.javaClass?.simpleName)
        i.putExtra(AppConstants.FromReqNumKey, currentReqNum)
        activity?.startActivity(i)
        activity?.let {
            it.toast(it.getString(R.string.token_timeout))
        }
        //activity?.toast(activity.getString(R.string.token_timeout))
    }*/

    abstract fun _onNext(t: T)
    override fun onComplete() {}
}
