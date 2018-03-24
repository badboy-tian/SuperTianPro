package com.i7play.supertian.view

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.text.InputFilter



/**
 * Created by tian on 2017/11/30.
 */

class BindLimitTextWatcher(val btn: Button, val editText: EditText, val len: Int): TextWatcher {
    init {
        editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(len))
    }
    override fun afterTextChanged(s: Editable?) {
        if (TextUtils.isEmpty(editText.text.toString())) {
            btn.isEnabled = false
            return
        }

        if (editText.text.length != len){
            btn.isEnabled = false
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        if (s.toString() !== "") {
            btn.isEnabled = true
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        
    }
}