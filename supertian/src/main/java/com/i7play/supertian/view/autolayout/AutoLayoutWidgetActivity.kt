package com.i7play.supertian.view.autolayout

import android.content.Context
import android.util.AttributeSet
import android.view.View

import com.zhy.autolayout.AutoLayoutActivity

/**
 * Created by tian on 2017/11/29.
 */

open class AutoLayoutWidgetActivity : AutoLayoutActivity() {

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        var view: View? = null

        /*if (name.equals(ACTION_MENU_ITEM_VIEW)) {
            view = new AutoActionMenuItemView(context, attrs);
        }*/
        if (name == TAB_LAYOUT) {
            view = AutoTabLayout(context, attrs)
        }

        if (name == "RadioGroup") {
            view = AutoRadioGroup(context, attrs)
        }

        return if (view != null) view else super.onCreateView(name, context, attrs)
    }

    companion object {

        private val ACTION_MENU_ITEM_VIEW = "android.support.v7.view.menu.ActionMenuItemView"
        private val TAB_LAYOUT = "android.support.design.widget.TabLayout"
    }
}