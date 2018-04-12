package com.i7play.supertian.view.autolayout

import android.content.Context
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import com.i7play.supertian.R
import com.zhy.autolayout.AutoLayoutInfo
import com.zhy.autolayout.utils.AutoLayoutHelper
import com.zhy.autolayout.utils.AutoUtils
import com.zhy.autolayout.utils.DimenUtils


class AutoToolbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : Toolbar(context, attrs, defStyleAttr) {
    private var mTextSize: Int = 0
    private var mSubTextSize: Int = 0
    private val mHelper = AutoLayoutHelper(this)

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.Toolbar,
                defStyleAttr, R.style.Widget_AppCompat_Toolbar)

        val titleTextAppearance = a.getResourceId(R.styleable.Toolbar_titleTextAppearance,
                R.style.TextAppearance_Widget_AppCompat_Toolbar_Title)

        val subtitleTextAppearance = a.getResourceId(R.styleable.Toolbar_subtitleTextAppearance,
                R.style.TextAppearance_Widget_AppCompat_Toolbar_Subtitle)

        mTextSize = loadTextSizeFromTextAppearance(titleTextAppearance)
        mSubTextSize = loadTextSizeFromTextAppearance(subtitleTextAppearance)

        val menuA = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Theme,
                defStyleAttr, R.style.ThemeOverlay_AppCompat)
        val menuTextAppearance = menuA.getResourceId(R.styleable.Theme_actionBarTheme,
                R.style.ThemeOverlay_AppCompat_ActionBar)
        val menuTextSize = loadTextSizeFromTextAppearance(menuTextAppearance)

        //防止 menu 定义 textSize，而 Toolbar 无定义 textSize 时，title 的 textSize 随 menu 变化
        if (mTextSize == NO_VALID) mTextSize = menuTextSize
        if (mSubTextSize == NO_VALID) mSubTextSize = menuTextSize

        a.recycle()
        menuA.recycle()
    }

    private fun loadTextSizeFromTextAppearance(textAppearanceResId: Int): Int {
        val a = getContext().obtainStyledAttributes(textAppearanceResId,
                R.styleable.TextAppearance)
        try {
            return if (!DimenUtils.isPxVal(a.peekValue(R.styleable.TextAppearance_android_textSize))) NO_VALID else a.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, NO_VALID)
        } finally {
            a.recycle()
        }
    }

    private fun setUpTitleTextSize() {
        val title = getTitle()
        if (!TextUtils.isEmpty(title) && mTextSize != NO_VALID)
            setUpTitleTextSize("mTitleTextView", mTextSize)
        val subtitle = getSubtitle()
        if (!TextUtils.isEmpty(subtitle) && mSubTextSize != NO_VALID)
            setUpTitleTextSize("mSubtitleTextView", mSubTextSize)
    }

    private fun setUpTitleTextSize(name: String, `val`: Int) {
        try {
            //反射 Toolbar 的 TextView
            val f = this::class.java.superclass.getDeclaredField(name)
            f.isAccessible = true
            val textView = f.get(this) as TextView
            if (textView != null) {
                val autoTextSize = AutoUtils.getPercentHeightSize(`val`)
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, autoTextSize.toFloat())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    protected override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (!this.isInEditMode()) {
            setUpTitleTextSize()
            this.mHelper.adjustChildren()
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    protected override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return LayoutParams(this.getContext(), attrs)
    }

    class LayoutParams : Toolbar.LayoutParams, AutoLayoutHelper.AutoLayoutParams {
        lateinit var mDimenLayoutInfo: AutoLayoutInfo

        constructor(c: Context, attrs: AttributeSet) : super(c, attrs) {
            this.mDimenLayoutInfo = AutoLayoutHelper.getAutoLayoutInfo(c, attrs)
        }

        override fun getAutoLayoutInfo(): AutoLayoutInfo {
            return this.mDimenLayoutInfo
        }

        constructor(width: Int, height: Int) : super(width, height) {}

        constructor(source: android.view.ViewGroup.LayoutParams) : super(source) {}

        constructor(source: MarginLayoutParams) : super(source) {}
    }

    companion object {
        private val NO_VALID = -1
    }
}
