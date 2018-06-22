package com.i7play.supertian.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.RadioButton

import com.zhy.autolayout.utils.AutoUtils

/**
 * 多行RadioGroup
 */
class MultiRadioGroup : LinearLayout {
    // holds the checked id; the selection is empty by default
    /**
     *
     *
     * Returns the identifier of the selected radio button in this group. Upon
     * empty selection, the returned value is -1.
     *
     *
     * @return the unique id of the selected radio button in this group
     * @see .check
     * @see .clearCheck
     */
    var checkedRadioButtonId = -1
        internal set
    // tracks children radio buttons checked state
    private var mChildOnCheckedChangeListener: CheckedStateTracker? = null
    // when true, mOnCheckedChangeListener discards events
    internal var mProtectFromCheckedChange = false
    private var mOnCheckedChangeListener: OnCheckedChangeListener? = null
    private var mPassThroughListener: PassThroughHierarchyChangeListener? = null

    internal var onMyCheckedChangeListener: OnMyRbCheckedChangeListener? = null

    constructor(context: Context) : super(context) {
        orientation = LinearLayout.VERTICAL
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    fun setOnMyCheckedChangeListener(onMyCheckedChangeListener: OnMyRbCheckedChangeListener) {
        this.onMyCheckedChangeListener = onMyCheckedChangeListener
    }

    private fun init() {
        mChildOnCheckedChangeListener = CheckedStateTracker()
        mPassThroughListener = PassThroughHierarchyChangeListener()
        super.setOnHierarchyChangeListener(mPassThroughListener)
    }

    override fun setOnHierarchyChangeListener(listener: ViewGroup.OnHierarchyChangeListener) {
        // the user listener is delegated to our pass-through listener
        mPassThroughListener!!.mOnHierarchyChangeListener = listener
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        // checks the appropriate radio button as requested in the XML file
        if (checkedRadioButtonId != -1) {
            mProtectFromCheckedChange = true
            setCheckedStateForView(checkedRadioButtonId, true)
            mProtectFromCheckedChange = false
            setCheckedId(checkedRadioButtonId)
        }
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (child is RadioButton) {
            if (child.isChecked) {
                mProtectFromCheckedChange = true
                if (checkedRadioButtonId != -1) {
                    setCheckedStateForView(checkedRadioButtonId, false)
                }
                mProtectFromCheckedChange = false
                setCheckedId(child.id)
            }
        } else if (child is ViewGroup) {
            val button = findRadioButton(child)
            if (button!!.isChecked) {
                mProtectFromCheckedChange = true
                if (checkedRadioButtonId != -1) {
                    setCheckedStateForView(checkedRadioButtonId, false)
                }
                mProtectFromCheckedChange = false
                setCheckedId(button.id)
            }
        }

        super.addView(child, index, params)
        AutoUtils.auto(child)
    }

    /**
     * 查找radioButton控件
     */
    fun findRadioButton(group: ViewGroup): RadioButton? {
        var resBtn: RadioButton? = null
        val len = group.childCount
        for (i in 0 until len) {
            if (group.getChildAt(i) is RadioButton) {
                resBtn = group.getChildAt(i) as RadioButton
            } else if (group.getChildAt(i) is ViewGroup) {
                findRadioButton(group.getChildAt(i) as ViewGroup)
            }
        }
        return resBtn
    }

    /**
     *
     *
     * Sets the selection to the radio button whose identifier is passed in
     * parameter. Using -1 as the selection identifier clears the selection;
     * such an operation is equivalent to invoking [.clearCheck].
     *
     *
     * @param id the unique id of the radio button to select in this group
     * @see .getCheckedRadioButtonId
     * @see .clearCheck
     */
    fun check(id: Int) {
        // don't even bother
        if (id != -1 && id == checkedRadioButtonId) {
            return
        }

        if (checkedRadioButtonId != -1) {
            setCheckedStateForView(checkedRadioButtonId, false)
        }

        if (id != -1) {
            setCheckedStateForView(id, true)
        }

        setCheckedId(id)
    }

    internal fun setCheckedId(id: Int) {
        checkedRadioButtonId = id
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener!!.onCheckedChanged(this, checkedRadioButtonId)
        }
    }

    internal fun setCheckedStateForView(viewId: Int, checked: Boolean) {
        val checkedView = findViewById<View>(viewId)
        if (checkedView != null && checkedView is RadioButton) {
            checkedView.isChecked = checked
        }
    }

    /**
     *
     *
     * Clears the selection. When the selection is cleared, no radio button in
     * this group is selected and [.getCheckedRadioButtonId] returns
     * null.
     *
     *
     * @see .check
     * @see .getCheckedRadioButtonId
     */
    fun clearCheck() {
        check(-1)
    }

    /**
     *
     *
     * Register a callback to be invoked when the checked radio button changes
     * in this group.
     *
     *
     * @param listener the callback to call on checked state change
     */
    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener) {
        mOnCheckedChangeListener = listener
    }

    /**
     * {@inheritDoc}
     */
    override fun generateLayoutParams(attrs: AttributeSet): LinearLayout.LayoutParams {
        return LayoutParams(context, attrs)
    }

    /**
     * {@inheritDoc}
     */
    override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean {
        return p is LayoutParams
    }

    override fun generateDefaultLayoutParams(): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    interface OnCheckedChangeListener {
        fun onCheckedChanged(group: MultiRadioGroup, checkedId: Int)
    }


    /**
     *
     *
     * A pass-through listener acts upon the events and dispatches them to
     * another listener. This allows the table layout to set its own internal
     * hierarchy change listener without preventing the user to setup his.
     *
     */
    private inner class PassThroughHierarchyChangeListener : ViewGroup.OnHierarchyChangeListener {
        var mOnHierarchyChangeListener: ViewGroup.OnHierarchyChangeListener? = null

        override fun onChildViewAdded(parent: View, child: View) {
            if (parent === this@MultiRadioGroup && child is RadioButton) {
                var id = child.getId()
                // generates an id if it's missing
                if (id == View.NO_ID) {
                    id = child.hashCode()
                    child.setId(id)
                }
                child.setOnCheckedChangeListener(mChildOnCheckedChangeListener)
            } else if (parent === this@MultiRadioGroup && child is ViewGroup) {
                val btn = findRadioButton(child)
                var id = btn!!.id
                // generates an id if it's missing
                if (id == View.NO_ID) {
                    id = btn.hashCode()
                    btn.id = id
                }
                btn.setOnCheckedChangeListener(mChildOnCheckedChangeListener)
            }

            mOnHierarchyChangeListener?.onChildViewAdded(parent, child)
        }

        override fun onChildViewRemoved(parent: View, child: View) {
            if (parent === this@MultiRadioGroup && child is RadioButton) {
                child.setOnCheckedChangeListener(null)
            } else if (parent === this@MultiRadioGroup && child is ViewGroup) {
                findRadioButton(child)!!.setOnCheckedChangeListener(null)
            }
            mOnHierarchyChangeListener?.onChildViewRemoved(parent, child)
        }
    }

    internal inner class CheckedStateTracker : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton,
                                      isChecked: Boolean) {
            if (mProtectFromCheckedChange) {
                return
            }

            mProtectFromCheckedChange = true
            if (checkedRadioButtonId != -1) {
                setCheckedStateForView(checkedRadioButtonId, false)
            }
            mProtectFromCheckedChange = false

            val id = buttonView.id
            setCheckedId(id)

            if (onMyCheckedChangeListener != null) {
                onMyCheckedChangeListener!!.onChanged(buttonView, isChecked)
            }
        }
    }

    interface OnMyRbCheckedChangeListener {
        fun onChanged(view: View, isChecked: Boolean)
    }
}