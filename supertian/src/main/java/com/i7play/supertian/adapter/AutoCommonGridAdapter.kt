package com.i7play.supertian.adapter

import android.content.Context
import android.view.ViewGroup

import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import com.zhy.autolayout.attr.AutoAttr
import com.zhy.autolayout.utils.AutoUtils

/**
 * Created by Administrator on 2017/8/2.
 */

abstract class AutoCommonGridAdapter<T>(context: Context, layoutId: Int, datas: ArrayList<T>) : CommonAdapter<T>(context, layoutId, datas) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = super.onCreateViewHolder(parent, viewType)
        AutoUtils.autoSize(viewHolder.convertView, AutoAttr.BASE_HEIGHT)
        return viewHolder
    }
}
