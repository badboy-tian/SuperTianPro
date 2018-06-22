package com.i7play.supertianpro

import com.i7play.supertian.base.BaseActivity
import com.i7play.supertian.view.badge.SuperBadgeHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        val i = SuperBadgeHelper.init(this, ss, "")
        i.setMaxNum(10)
        i.addNum(11)
    }

    override fun initListener() {
    }

}
