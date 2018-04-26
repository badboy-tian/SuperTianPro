package com.i7play.supertian.base.mvvm

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.View

abstract class BaseMvvmActivity<T: ViewDataBinding> : BaseZXActivity(), Presenter {
    lateinit var mBinding: T

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        initCreate()
    }
}

interface Presenter: View.OnClickListener{
    override fun onClick(v: View?)
}