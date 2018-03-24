package com.i7play.supertian.view

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.widget.EditText

/**
 * Created by tian on 2017/12/22.
 * 不能输入空格
 */
class NoSpaceEditText : EditText {
    constructor(context: Context?) : super(context){
        initView()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        initView()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        initView()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView()
    }

    private fun initView() {
        val filter = InputFilter { source, _, _, _, _, _ ->
            source?.let {
                if (it == " ") return@InputFilter ""
            }

            source
        }

        filters = arrayOf(filter)
    }
}