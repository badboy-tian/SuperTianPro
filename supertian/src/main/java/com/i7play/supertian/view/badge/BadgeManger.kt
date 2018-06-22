package com.i7play.supertian.view.badge

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TabWidget
import android.widget.TextView

import com.zhy.autolayout.utils.AutoUtils

import java.io.Serializable

class BadgeManger @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = android.R.attr.textViewStyle) : android.support.v7.widget.AppCompatTextView(context, attrs, defStyle), Serializable {
    var isHideOnNull = true
        set(hideOnNull) {
            field = hideOnNull
            text = text
        }
    private var style: Int = 0

    var badgeGravity: Int
        get() {
            val params = layoutParams as FrameLayout.LayoutParams
            return params.gravity
        }
        set(gravity) {
            val params = layoutParams as FrameLayout.LayoutParams
            params.gravity = gravity
            layoutParams = params
        }

    val badgeMargin: IntArray
        get() {
            val params = layoutParams as FrameLayout.LayoutParams
            return intArrayOf(params.leftMargin, params.topMargin, params.rightMargin, params.bottomMargin)
        }

    init {

        init()
    }

    private fun init() {
        if (layoutParams !is FrameLayout.LayoutParams) {
            val layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.RIGHT or Gravity.TOP)
            setLayoutParams(layoutParams)
        }

        // set default font
        setTextColor(Color.WHITE)
        typeface = Typeface.DEFAULT_BOLD
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 11f)
        setPadding(dip2Px(5f), dip2Px(1f), dip2Px(5f), dip2Px(1f))

        // set default background
        setBackground(9, Color.parseColor("#d3321b"))

        gravity = Gravity.CENTER

        // default values
        isHideOnNull = true
        setBadgeCount(0)
    }

    fun setBackground(dipRadius: Int, badgeColor: Int) {
        val radius = dip2Px(dipRadius.toFloat())
        val radiusArray = floatArrayOf(radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat())

        val roundRect = RoundRectShape(radiusArray, null, null)
        val bgDrawable = ShapeDrawable(roundRect)
        bgDrawable.paint.color = badgeColor
        setBackgroundDrawable(bgDrawable)
    }

    fun setBadgeStyle(style: Int) {
        this.style = style
        when (style) {
            STYLE_DEFAULT -> {
                visibility = View.VISIBLE
                init()
            }
            STYLE_SMALL -> {
                visibility = View.VISIBLE
                setSmallBadge()
            }
            STYLE_GONE -> visibility = View.GONE
        }

    }

    private fun setSmallBadge() {
        width = dip2Px(7.5f)
        height = dip2Px(7.5f)
    }


    override fun setText(text: CharSequence?, type: TextView.BufferType) {
        if (isHideOnNull && (text == null || text.toString().equals("0", ignoreCase = true))) {
            visibility = View.GONE
        } else {
            visibility = View.VISIBLE
        }
        if (style == STYLE_GONE) {
            visibility = View.GONE
        }
        super.setText(text, type)
    }


    fun setBadgeCount(count: Int) {

        if (style == STYLE_SMALL && count != 0) {
            text = ""
        } else {
            text = count.toString()
        }
    }

    fun setBadgeMargin(leftDipMargin: Int, topDipMargin: Int, rightDipMargin: Int, bottomDipMargin: Int) {
        val params = layoutParams as FrameLayout.LayoutParams
        params.leftMargin = dip2Px(leftDipMargin.toFloat())
        params.topMargin = dip2Px(topDipMargin.toFloat())
        params.rightMargin = dip2Px(rightDipMargin.toFloat())
        params.bottomMargin = dip2Px(bottomDipMargin.toFloat())
        layoutParams = params
    }

    fun setBadgeMargin(dipMargin: Int) {
        setBadgeMargin(dipMargin, dipMargin, dipMargin, dipMargin)
    }


    fun setTargetView(target: TabWidget, tabIndex: Int) {
        val tabView = target.getChildTabViewAt(tabIndex)
        setTargetView(tabView)
    }


    fun setTargetView(target: View?) {
        if (parent != null) {
            (parent as ViewGroup).removeView(this)
        }

        if (target == null) {
            return
        }


        if (target.parent is FrameLayout) {
            (target.parent as FrameLayout).addView(this)

        } else if (target.parent is ViewGroup) {

            val parentContainer = target.parent as ViewGroup
            val groupIndex = parentContainer.indexOfChild(target)
            parentContainer.removeView(target)

            val badgeContainer = FrameLayout(context)
            val parentLayoutParams = target.layoutParams

            badgeContainer.layoutParams = parentLayoutParams
            target.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            parentContainer.addView(badgeContainer, groupIndex, parentLayoutParams)
            badgeContainer.addView(target)

            badgeContainer.addView(this)
            //添加说明
            AutoUtils.auto(target)
        } else if (target.parent == null) {
            Log.e(javaClass.simpleName, "ParentView is needed")
        }
    }


    private fun dip2Px(dip: Float): Int {
        return (dip * context.resources.displayMetrics.density + 0.5f).toInt()
    }

    companion object {

        val STYLE_DEFAULT = 1
        val STYLE_GONE = 0
        val STYLE_SMALL = 2
    }
}