package com.i7play.supertian.base

import android.content.Context
import android.os.Bundle
import com.i7play.supertian.beans.MsgBean
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Administrator on 2017/7/12.
 */

abstract class BaseLazyFragment : BaseFragment() {
    protected var isViewInitiated: Boolean = false
    protected var isVisibleToUser: Boolean = false
    protected var isDataInitiated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewInitiated = true
        prepareFetchData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        prepareFetchData()
    }

    abstract fun fetchData()

    @JvmOverloads
    fun prepareFetchData(forceUpdate: Boolean = false): Boolean {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData()
            isDataInitiated = true
            return true
        }
        return false
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    public open fun onReceived(msgBean: MsgBean){

    }
}
