package com.i7play.supertian.base.mvvm

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.i7play.supertian.beans.MsgBean
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract class BaseLazyMvvmFragment<T: ViewDataBinding>: BaseZXFragment(), Presenter {
    protected var isViewInitiated: Boolean = false
    protected var isVisibleToUser: Boolean = false
    protected var isDataInitiated: Boolean = false
    lateinit var mBinding: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), null, false)
        return mBinding.root
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