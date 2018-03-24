package com.i7play.supertian.view

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText

/**
 * Created by tian on 2017/11/30.
 */

class BindTextsWatcher(val btn: Button, val editTexts: Array<EditText>) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        handle()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        handle()
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    private fun handle(){
        btn.isEnabled = true
        editTexts.forEach {
            val mycommment = it.text.toString()
            if (TextUtils.isEmpty(mycommment)) {
                if (btn.isEnabled)
                    btn.isEnabled = false

                return@forEach
            }
        }
    }
}