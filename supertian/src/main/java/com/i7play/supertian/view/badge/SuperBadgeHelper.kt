package com.i7play.supertian.view.badge

import android.app.Activity
import android.graphics.Color
import android.view.View

import java.io.Serializable
import java.util.ArrayList

class SuperBadgeHelper
/**
 * @param context 当前Avtivity
 * @param view    绑定角标view
 * @param tag     用于绑定的唯一标记
 * @param num     角标数字
 * @param style   是否显示数字
 * @return SuperBadgeHelper
 */
private constructor(context: Activity?, view: View, tag: String?, num: Int, private var style: Int // 数字样式
) : Serializable, Cloneable {

    var tag: String? = null
        private set //标签
    var view: View? = null
        private set //寄生控件
    var num: Int = 0
        private set //计数
    private val paterBadge = ArrayList<SuperBadgeHelper>()//关联的上级节点
    private val childBadge = ArrayList<SuperBadgeHelper>()//关联的下级节点
    var context: Activity? = null
        private set //控件所在页面
    val badge: BadgeManger//红点管理器
    private var onNumCallback: OnNumCallback? = null

    init {
        if (SuperBadgeDater.instance.getBadge(tag!!) != null) {
            throw IllegalArgumentException(tag + "标记已经被其他控件注册")
        }
        if (context == null) {
            throw NullPointerException("context not is null ")
        }
        if (num < 0) {
            throw IllegalArgumentException("初始化角标数字不能小于0")
        }
        if (tag == null) {
            throw IllegalArgumentException("tag 不能为空")
        }

        this.tag = tag
        this.view = view
        this.num = num
        this.context = context

        badge = BadgeManger(context)
        badge.setTargetView(view)
        badge.setBadgeStyle(style)
        paterAddNum(num)
    }


    @Deprecated("")
    fun setOnNumCallback(onNumCallback: OnNumCallback) {
        this.onNumCallback = onNumCallback
    }

    /**
     * 设置角标半径
     *
     * @param dipRadius 半径
     */
    fun setDipRadius(dipRadius: Int) {
        badge.setBackground(dipRadius, Color.parseColor("#d3321b"))
    }

    /**
     * 设置角标颜色
     *
     * @param badgeColor 颜色
     */
    fun setBadgeColor(badgeColor: Int) {
        badge.setBackground(9, badgeColor)
    }


    fun setBackground(dipRadius: Int, badgeColor: Int) {
        badge.setBackground(dipRadius, badgeColor)

    }

    /**
     * @return
     */
    fun getStyle(): Boolean {
        return style != STYLE_GONE
    }

    /**
     * 控件添加数字
     *
     * @param i 添加数字大小
     */
    fun addNum(i: Int) {
        if (childBadge.size >= 1) {
            throw IllegalArgumentException("该控件不是根节点数据控件（该控件包含下级控件），无法完成添加操作")
        }
        paterAddNum(i)
    }


    private fun paterAddNum(i: Int) {
        if (i < 0) {
            // throw new IllegalArgumentException("添加数字不能小于0");
        } else {
            this.num = this.num + i
            badge.setBadgeCount(num)
            SuperBadgeDater.instance.addBadge(this)
            //传递变化到上级控件
            for (bean in paterBadge) {
                bean?.paterAddNum(i)
            }
        }
    }

    /**
     * 读取所有消息，减去所有数字
     */
    fun read() {
        chlidLessNum(num)
    }

    /**
     * @param i 减少数字
     */
    private fun chlidLessNum(i: Int) {
        if (i > 0) {

            badge.setBadgeCount(num - i)

            this.num = num - i
            SuperBadgeDater.instance.addBadge(this)
            changeBadge(i)
        }
    }

    /**
     * 减少
     *
     * @param i 减少数字
     */
    fun lessNum(i: Int) {
        if (childBadge.size >= 1) {
            throw IllegalArgumentException("该控件不是根节点数据控件（包含下级控件），无法完成减少操作")
        }
        chlidLessNum(i)
    }


    /**
     * 根据父级控件标签将他绑定到本级控件
     *
     * @param tag 父级控件的Tag
     */
    fun bindView(tag: String?) {

        for (pater in paterBadge) {
            if (pater.tag == tag) {
                //  throw new IllegalArgumentException("不能重复添加相同控件");
                return
            }
        }
        val paterBadgeHelper = SuperBadgeDater.instance.getBadge(tag!!)
        if (paterBadgeHelper != null) {
            paterBadge.add(paterBadgeHelper) //添加本级父控件
            paterBadgeHelper!!.addChild(this)//添加到父级子控件
        } else {
            throw NullPointerException("没有找到标记为[$tag]的控件")
        }
    }


    fun bindView(pater: SuperBadgeHelper) {
        bindView(pater.tag)
    }


    private fun addChild(superBadgeHelper: SuperBadgeHelper?) {
        if (superBadgeHelper != null) {
            childBadge.add(superBadgeHelper)
            paterAddNum(superBadgeHelper.num)
        }
    }


    /**
     * 传递关联的View
     *
     * @param num 减少的数字
     */
    private fun changeBadge(num: Int) {

        if (num > 0) {
            //传递变化到上级控件
            for (bean in paterBadge) {
                if (bean != null && bean.num != 0) {
                    bean.chlidLessNum(num)
                }
            }
        }

        //清空下级控件数字
        if (num == 0) {
            if (childBadge.size > 0) {
                for (bean in childBadge) {
                    bean?.read()
                }

            }
        }
    }

    /**
     * 角标样式
     *
     * @param style
     */
    fun setBadgeStyle(style: Int) {
        this.style = style
        badge.setBadgeStyle(style)
    }

    /**
     * 为0时是否显示
     * @param mHideOnNull
     */
    fun setHideOnNull(mHideOnNull: Boolean) {
        badge.isHideOnNull = mHideOnNull
    }


    interface OnNumCallback {
        fun lodingNum(): Int //加载数字方法
    }


    fun setBadgeGravity(gravity: Int) {
        badge.badgeGravity = gravity
    }


    fun setBadgeMargin(dipMargin: Int) {
        badge.setBadgeMargin(dipMargin)
    }

    fun setBadgeMargin(leftDipMargin: Int, topDipMargin: Int, rightDipMargin: Int, bottomDipMargin: Int) {
        badge.setBadgeMargin(leftDipMargin, topDipMargin, rightDipMargin, bottomDipMargin)
    }

    companion object {

        val STYLE_DEFAULT = 1
        val STYLE_GONE = 0
        val STYLE_SMALL = 2


        /**
         * @param context 当前Avtivity
         * @param view    绑定角标view
         * @param tag     用于绑定的唯一标记
         * @param num     角标数字
         * @param style   显示样式
         * @return SuperBadgeHelper
         */
        @JvmOverloads
        fun init(context: Activity, view: View, tag: String, num: Int = 0, style: Int = STYLE_DEFAULT): SuperBadgeHelper {
            val superBadge = SuperBadgeDater.instance.getBadge(tag)
            if (superBadge != null) {
                superBadge!!.view = view
                superBadge!!.context = context
                superBadge!!.setBadgeStyle(style)
                superBadge!!.badge.setTargetView(view)
                if (superBadge!!.getStyle()) {
                    superBadge!!.badge.setBadgeCount(superBadge!!.num)
                }
                return superBadge
            } else {
                return SuperBadgeHelper(context, view, tag, num, style)
            }
        }


        fun getSBHelper(tag: String): SuperBadgeHelper {
            return SuperBadgeDater.instance.getBadge(tag)
                    ?: throw NullPointerException("没有找到标记为[$tag]的控件")
        }
    }


}