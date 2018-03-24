package com.i7play.supertian.ext

import android.text.TextUtils
import android.widget.EditText

/**
 * Created by tian on 2018/3/13.
 */
fun EditText.isEmputy(): Boolean{
    return TextUtils.isEmpty(text)
}