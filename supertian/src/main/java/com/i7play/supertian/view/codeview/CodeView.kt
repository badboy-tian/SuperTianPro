package com.i7play.supertian.view.codeview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.InputType
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputMethodManager

import com.i7play.supertian.R
import com.i7play.supertian.utils.DensityUtil

/**
 * Created by tian on 2017/11/9.
 */

class CodeView : View {

    //密码长度，默认6位
    private var length: Int = 0
    //描边颜色，默认#E1E1E1
    private var borderColor: Int = 0
    //描边宽度，默认1px
    private var borderWidth: Float = 0.toFloat()
    //分割线颜色，默认#E1E1E1
    private var dividerColor: Int = 0
    //分割线宽度，默认1px
    private var dividerWidth: Float = 0.toFloat()
    //默认文本，在XML设置后可预览效果
    private var code: String? = null
    //密码点颜色，默认#000000
    private var codeColor: Int = 0
    //密码点半径，默认8dp
    private var pointRadius: Float = 0.toFloat()
    //显示明文时的文字大小，默认unitWidth/2
    private var textSize: Float = 0.toFloat()
    //显示类型，支持密码、明文，默认明文
    private var showType: Int = 0

    private var unitWidth: Float = 0.toFloat()
    private var paint: Paint? = null
    private var listener: Listener? = null

    private var inputConnection: KInputConnection? = null
    private var inputMethodManager: InputMethodManager? = null

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //根据宽度来计算单元格大小（高度）
        val width = measuredWidth.toFloat()
        //宽度-左右边宽-中间分割线宽度
        unitWidth = (width - 2 * borderWidth - (length - 1) * dividerWidth) / length
        if (textSize == 0f) {
            textSize = unitWidth / 2
        }
        setMeasuredDimension(width.toInt(), (unitWidth + 2 * borderWidth).toInt())
    }

    private fun init(attrs: AttributeSet?) {
        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.textAlign = Paint.Align.CENTER
        if (attrs == null) {
            length = 6
            borderColor = Color.parseColor("#E1E1E1")
            borderWidth = 1f
            dividerColor = Color.parseColor("#E1E1E1")
            dividerWidth = 1f
            code = ""
            codeColor = Color.parseColor("#000000")

            pointRadius = DensityUtil.dip2px(context, 8.0f).toFloat()
            showType = SHOW_TYPE_WORD
            textSize = 0f
        } else {
            val typedArray = resources.obtainAttributes(attrs, R.styleable.CodeView)
            length = typedArray.getInt(R.styleable.CodeView_length, 6)
            borderColor = typedArray.getColor(R.styleable.CodeView_borderColor, Color.parseColor("#E1E1E1"))
            borderWidth = typedArray.getDimensionPixelSize(R.styleable.CodeView_borderWidth, 1).toFloat()
            dividerColor = typedArray.getColor(R.styleable.CodeView_dividerColor, Color.parseColor("#E1E1E1"))
            dividerWidth = typedArray.getDimensionPixelSize(R.styleable.CodeView_dividerWidth, 1).toFloat()
            code = typedArray.getString(R.styleable.CodeView_code)
            if (code == null) {
                code = ""
            }
            codeColor = typedArray.getColor(R.styleable.CodeView_codeColor, Color.parseColor("#000000"))
            pointRadius = typedArray.getDimensionPixelSize(R.styleable.CodeView_pointRadius, DensityUtil.dip2px(context, 8f)).toFloat()
            showType = typedArray.getInt(R.styleable.CodeView_showType, SHOW_TYPE_WORD)
            textSize = typedArray.getDimensionPixelSize(R.styleable.CodeView_textSize, 0).toFloat()
        }

        initKeyBoard()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawDivider(canvas)
        drawBorder(canvas)
        when (showType) {
            SHOW_TYPE_PASSWORD -> drawPoint(canvas)
            else -> drawValue(canvas)
        }
    }

    /**
     * 描边
     */
    private fun drawBorder(canvas: Canvas) {
        if (borderWidth > 0) {
            paint!!.color = borderColor
            canvas.drawRect(0f, 0f, width.toFloat(), borderWidth, paint!!)
            canvas.drawRect(0f, height - borderWidth, width.toFloat(), height.toFloat(), paint!!)
            canvas.drawRect(0f, 0f, borderWidth, height.toFloat(), paint!!)
            canvas.drawRect(width - borderWidth, 0f, width.toFloat(), height.toFloat(), paint!!)
        }
    }

    /**
     * 画分割线
     */
    private fun drawDivider(canvas: Canvas) {
        if (dividerWidth > 0) {
            paint!!.color = dividerColor
            for (i in 0 until length - 1) {
                val left = unitWidth * (i + 1) + dividerWidth * i + borderWidth
                canvas.drawRect(left, 0f, left + dividerWidth, height.toFloat(), paint!!)
            }
        }
    }

    /**
     * 画输入文字
     */
    private fun drawValue(canvas: Canvas) {
        if (pointRadius > 0) {
            paint!!.color = codeColor
            paint!!.textSize = textSize
            for (i in 0 until code!!.length) {
                val left = unitWidth * i + dividerWidth * i + borderWidth
                canvas.drawText(code!![i] + "",
                        left + unitWidth / 2,
                        getTextBaseLine(0f, height.toFloat(), paint!!),
                        paint!!)
            }
        }
    }

    /**
     * 画密码点
     */
    private fun drawPoint(canvas: Canvas) {
        if (pointRadius > 0) {
            paint!!.color = codeColor
            for (i in 0 until code!!.length) {
                val left = unitWidth * i + dividerWidth * i + borderWidth
                canvas.drawCircle(left + unitWidth / 2, (height / 2).toFloat(), pointRadius, paint!!)
            }
        }
    }

    fun input(number: String) {
        if (code!!.length < length) {
            code += number
            if (listener != null) {
                listener!!.onValueChanged(code!!)
                if (code!!.length == length) {
                    listener!!.onComplete(code!!)
                }
            }
            invalidate()
        }
    }

    fun delete() {
        if (code!!.length > 0) {
            code = code!!.substring(0, code!!.length - 1)
            if (listener != null) {
                listener!!.onValueChanged(code!!)
            }
            invalidate()
        }
    }

    fun clear() {
        if (code!!.length > 0) {
            code = ""
            if (listener != null) {
                listener!!.onValueChanged(code!!)
            }
            invalidate()
        }
    }

    fun getLength(): Int {
        return length
    }

    fun setLength(length: Int) {
        this.length = length
        invalidate()
    }

    fun getBorderColor(): Int {
        return borderColor
    }

    fun setBorderColor(borderColor: Int) {
        this.borderColor = borderColor
        invalidate()
    }

    fun getBorderWidth(): Float {
        return borderWidth
    }

    fun setBorderWidth(borderWidth: Float) {
        this.borderWidth = borderWidth
        invalidate()
    }

    fun getDividerColor(): Int {
        return dividerColor
    }

    fun setDividerColor(dividerColor: Int) {
        this.dividerColor = dividerColor
        invalidate()
    }

    fun getDividerWidth(): Float {
        return dividerWidth
    }

    fun setDividerWidth(dividerWidth: Float) {
        this.dividerWidth = dividerWidth
        invalidate()
    }

    fun getCode(): String? {
        return code
    }

    fun setCode(code: String) {
        this.code = code
        invalidate()
    }

    fun getCodeColor(): Int {
        return codeColor
    }

    fun setCodeColor(codeColor: Int) {
        this.codeColor = codeColor
        invalidate()
    }

    fun getPointRadius(): Float {
        return pointRadius
    }

    fun setPointRadius(pointRadius: Float) {
        this.pointRadius = pointRadius
        invalidate()
    }

    fun getTextSize(): Float {
        return textSize
    }

    fun setTextSize(textSize: Float) {
        this.textSize = textSize
        invalidate()
    }

    fun getShowType(): Int {
        return showType
    }

    fun setShowType(showType: Int) {
        this.showType = showType
        invalidate()
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun onValueChanged(value: String)
        fun onComplete(value: String)
    }

    override fun onCreateInputConnection(editorInfo: EditorInfo): InputConnection {
        return getInputConnection(editorInfo)
    }

    private fun initKeyBoard() {
        this.isFocusable = true
        this.isFocusableInTouchMode = true
        inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        setOnClickListener { inputMethodManager!!.showSoftInput(this@CodeView, InputMethodManager.SHOW_FORCED) }

        setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP) {
                if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
                    input((keyCode - 7).toString() + "")
                }
            }
            false
        }
    }

    private fun getInputConnection(editorInfo: EditorInfo): InputConnection {
        editorInfo.inputType = InputType.TYPE_CLASS_NUMBER
        editorInfo.imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI

        if (inputConnection != null) {
            return inputConnection as KInputConnection
        }
        inputConnection = KInputConnection(this, false)
        inputConnection!!.setOnCommitTextListener(object : KInputConnection.OnCommitTextListener {
            override fun commitText(text: CharSequence, newCursorPosition: Int): Boolean {
                return true
            }

            override fun onDeleteText() {
                delete()
                invalidate()
            }
        })
        return inputConnection as KInputConnection
    }

    companion object {
        val SHOW_TYPE_WORD = 1
        val SHOW_TYPE_PASSWORD = 2

        fun getTextBaseLine(backgroundTop: Float, backgroundBottom: Float, paint: Paint): Float {
            val fontMetrics = paint.fontMetrics
            return (backgroundTop + backgroundBottom - fontMetrics.bottom - fontMetrics.top) / 2
        }
    }
}
