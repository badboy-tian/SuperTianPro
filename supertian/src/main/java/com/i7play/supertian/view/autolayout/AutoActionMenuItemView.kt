package com.i7play.supertian.view.autolayout

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import com.zhy.autolayout.utils.AutoUtils
import com.zhy.autolayout.utils.DimenUtils
import android.support.v7.view.menu.ActionMenuItemView
import android.util.AttributeSet
import com.i7play.supertian.R


@SuppressLint("RestrictedApi")
class AutoActionMenuItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ActionMenuItemView(context, attrs, defStyle) {
    private val mMenuTextSize: Int

    init {
        val a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Theme,
                defStyle, R.style.ThemeOverlay_AppCompat)
        val menuTextAppearance = a.getResourceId(R.styleable.Theme_actionBarTheme,
                R.style.ThemeOverlay_AppCompat_ActionBar)
        mMenuTextSize = loadTextSizeFromTextAppearance(menuTextAppearance)
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (!this.isInEditMode) {
            setUpTitleTextSize()
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun loadTextSizeFromTextAppearance(textAppearanceResId: Int): Int {
        val a = context.obtainStyledAttributes(textAppearanceResId,
                R.styleable.TextAppearance)
        try {
            return if (!DimenUtils.isPxVal(a.peekValue(R.styleable.TextAppearance_android_textSize))) NO_VALID else a.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, NO_VALID)
        } finally {
            a.recycle()
        }
    }

    private fun setUpTitleTextSize() {
        if (mMenuTextSize == -1) return
        val autoTextSize = AutoUtils.getPercentHeightSize(mMenuTextSize)
        setTextSize(TypedValue.COMPLEX_UNIT_PX, autoTextSize.toFloat())
    }

    companion object {
        private val NO_VALID = -1
    }
}