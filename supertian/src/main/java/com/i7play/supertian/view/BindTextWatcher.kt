package com.i7play.supertian.view

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.Button

/**
 * Created by tian on 2017/11/30.
 */

class BindTextWatcher(val btn: Button): TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        val mycommment = s.toString()
        if (TextUtils.isEmpty(mycommment)) {
            if (btn.isEnabled)
            btn.isEnabled = false
        }else{
            if (!btn.isEnabled)
            btn.isEnabled = true
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}