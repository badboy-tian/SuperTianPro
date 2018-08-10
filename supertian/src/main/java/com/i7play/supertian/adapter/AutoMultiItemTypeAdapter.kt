package com.i7play.supertian.adapter

import android.content.Context
import android.view.ViewGroup
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import com.zhy.autolayout.attr.AutoAttr
import com.zhy.autolayout.utils.AutoUtils

/**
 * Created by tian on 2018/3/15.
 */
open class AutoMultiItemTypeAdapter<T>(context: Context, datas: ArrayList<T>) : MultiItemTypeAdapter<T>(context, datas) {
    /*override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val viewHolder = super.onCreateViewHolder(parent, viewType)
        AutoUtils.autoSize(viewHolder.convertView, AutoAttr.BASE_HEIGHT)
        return viewHolder
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       // return super.onCreateViewHolder(parent, viewType)
        val viewHolder = super.onCreateViewHolder(parent, viewType)
        AutoUtils.autoSize(viewHolder.convertView, AutoAttr.BASE_HEIGHT)
        return viewHolder
    }
}