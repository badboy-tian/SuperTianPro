package com.i7play.supertian.base.mvvm

import android.databinding.ObservableBoolean
import android.databinding.ObservableField

open class BaseViewModel {
    var title = ObservableField<String>("")
    var showBack = ObservableBoolean(false)
    var right = ObservableField<String>("")

    fun setTitle(title: String) {
        this.title.set(title)
    }

    fun showBack(){
        showBack.set(true)
    }

    fun setRight(right: String){
        this.right.set(right)
    }
}