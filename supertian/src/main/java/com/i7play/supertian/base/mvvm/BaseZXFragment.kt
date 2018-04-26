package com.i7play.supertian.base.mvvm

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.github.jdsjlzx.recyclerview.LRecyclerView
import com.i7play.supertian.R
import com.i7play.supertian.beans.BaseBean
import com.i7play.supertian.utils.DensityUtil
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import com.zhy.autolayout.utils.AutoUtils
import io.reactivex.disposables.CompositeDisposable
import java.io.Serializable

abstract class BaseZXFragment : Fragment() {
    val compositeDisposable = CompositeDisposable()

    protected var mParam1: String? = null

    protected abstract fun getLayoutId(): Int

    protected abstract fun initView()
    protected open fun initListener() {}

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initListener()
    }

    protected fun initList(list: RecyclerView) {
        list.layoutManager = LinearLayoutManager(activity)
        list.addItemDecoration(HorizontalDividerItemDecoration.Builder(activity)
                .colorResId(R.color.line_color).build())
        if (list is LRecyclerView) {
            //设置头部加载颜色
            list.setHeaderViewColor(R.color.blue, R.color.blue, R.color.default_bg)
            //设置底部加载颜色
            list.setFooterViewColor(R.color.blue, R.color.blue, R.color.default_bg)
            //设置底部加载文字提示
            //list.setFooterViewHint(getString(R.string.refresh_loading), getString(R.string.refresh_all), getString(R.string.refresh_no_network))
        }
    }

    protected fun initList(list: RecyclerView, lineHeight: Float) {
        val size = DensityUtil.dip2px(activity, lineHeight)
        list.layoutManager = LinearLayoutManager(activity)
        list.addItemDecoration(HorizontalDividerItemDecoration.Builder(activity)
                .colorResId(R.color.line_color).size(size).build())
        if (list is LRecyclerView) {
            //设置头部加载颜色
            list.setHeaderViewColor(R.color.blue, R.color.blue, R.color.default_bg)
            //设置底部加载颜色
            list.setFooterViewColor(R.color.blue, R.color.blue, R.color.default_bg)
            //设置底部加载文字提示
            list.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧")
        }
    }

    protected fun initListLeftRight(list: RecyclerView, left: Int, right: Int) {
        var left = left
        var right = right
        left = AutoUtils.getPercentWidthSize(left)
        right = AutoUtils.getPercentWidthSize(right)

        list.layoutManager = LinearLayoutManager(activity)

        list.addItemDecoration(HorizontalDividerItemDecoration.Builder(activity)
                .margin(left, right).colorResId(R.color.line_color).size(DensityUtil.dip2px(this!!.context!!, 0.5f)).build())
        if (list is LRecyclerView) {
            //设置头部加载颜色
            list.setHeaderViewColor(R.color.blue, R.color.blue, R.color.default_bg)
            //设置底部加载颜色
            list.setFooterViewColor(R.color.blue, R.color.blue, R.color.default_bg)
            //设置底部加载文字提示
            //list.setFooterViewHint(getString(R.string.refresh_loading), getString(R.string.refresh_all), getString(R.string.refresh_no_network))
        }
    }

    protected fun initNoDivList(list: LRecyclerView) {
        list.setHasFixedSize(true)
        list.layoutManager = LinearLayoutManager(activity)
        //设置头部加载颜色
        list.setHeaderViewColor(R.color.blue, R.color.blue, R.color.default_bg)
        //设置底部加载颜色
        list.setFooterViewColor(R.color.blue, R.color.blue, R.color.default_bg)
        //设置底部加载文字提示
        //list.setFooterViewHint(getString(R.string.refresh_loading), getString(R.string.refresh_all), getString(R.string.refresh_no_network))
    }

    protected fun jumpTo(cls: Class<*>) {
        val i = Intent(context, cls)
        activity?.startActivity(i)
    }

    /*protected void jumpToDilaog(Class cls, Serializable listener) {
        Intent i = new Intent(getContext(), cls);
        i.putExtra("builder", listener);
        getActivity().startActivity(i);
    })*/

    protected fun jumpToAndFinish(cls: Class<*>) {
        val i = Intent(context, cls)
        activity?.startActivity(i)
        //finish();
        activity?.finish()
    }

    protected fun jumpToAndFinish(cls: Class<*>, baseBean: BaseBean) {
        val i = Intent(context, cls)
        i.putExtra("item", baseBean)
        activity?.startActivity(i)
        //finish();
        activity?.finish()
    }


    protected fun jumpForResult(cls: Class<*>, baseBean: Serializable, requestCode: Int) {
        val i = Intent(context, cls)
        i.putExtra("item", baseBean)
        startActivityForResult(i, requestCode)
    }

    protected fun jumpForResult(cls: Class<*>, requestCode: Int) {
        val i = Intent(context, cls)
        startActivityForResult(i, requestCode)
    }

    protected fun jumpForResult(cls: Class<*>, requestCode: Int, fromCode: Int) {
        val i = Intent(context, cls)
        i.putExtra("from", fromCode)
        startActivityForResult(i, requestCode)
    }

    protected fun jumpTo(cls: Class<*>, key: String, value: String) {
        val i = Intent(context, cls)
        i.putExtra(key, value)
        startActivity(i)
    }

    protected fun jumpTo(cls: Class<*>, key: String, value: Int) {
        val i = Intent(context, cls)
        i.putExtra(key, value)
        startActivity(i)
    }

    protected fun jumpTo(cls: Class<*>, item: BaseBean) {
        val i = Intent(context, cls)
        i.putExtra("item", item)
        startActivity(i)
    }

    protected fun jumpTo(cls: Class<*>, item: Serializable) {
        val i = Intent(context, cls)
        i.putExtra("item", item)
        startActivity(i)
    }

    companion object {
        protected val ARG_PARAM1 = "param1"
    }

    override fun onDetach() {
        super.onDetach()
        compositeDisposable.clear()
    }
}