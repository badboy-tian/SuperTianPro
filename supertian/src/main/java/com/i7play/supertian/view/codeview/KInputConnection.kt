package com.i7play.supertian.view.codeview

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.BaseInputConnection

/**
 * Created by tian on 2018/3/6.
 */
class KInputConnection(targetView: View, fullEditor: Boolean) : BaseInputConnection(targetView, fullEditor) {

    // 输入法提交了一个 text
    /*@Override
    public boolean commitText(CharSequence text, int newCursorPosition) {
        if (onCommitTextListener != null) {
            return onCommitTextListener.commitText(text, newCursorPosition);
        }
        return true;
    }*/

    private var onCommitTextListener: OnCommitTextListener? = null

    // 输入法的按键信息
    override fun sendKeyEvent(event: KeyEvent): Boolean {
        when (event.keyCode) {
            KeyEvent.KEYCODE_DEL -> if (event.action == KeyEvent.ACTION_UP && onCommitTextListener != null) {
                onCommitTextListener!!.onDeleteText()
            }
        }
        return super.sendKeyEvent(event)
    }

    fun setOnCommitTextListener(onCommitTextListener: OnCommitTextListener) {
        this.onCommitTextListener = onCommitTextListener
    }

    interface OnCommitTextListener {
        fun commitText(text: CharSequence, newCursorPosition: Int): Boolean
        fun onDeleteText()
    }
}
