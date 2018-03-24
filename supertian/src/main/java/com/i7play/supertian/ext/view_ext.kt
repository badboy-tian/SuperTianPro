package com.i7play.supertian.ext

import android.view.View

/**
 * Created by tian on 2018/3/15.
 */
fun View.hide(){
    visibility = View.GONE
}

fun View.visable(){
    visibility = View.VISIBLE
}

fun View.isVisable(): Boolean{
    return visibility == View.VISIBLE
}