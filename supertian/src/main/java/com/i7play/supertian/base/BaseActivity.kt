package com.i7play.supertian.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.github.jdsjlzx.recyclerview.LRecyclerView
import com.i7play.supertian.R
import com.i7play.supertian.beans.MsgBean
import com.i7play.supertian.view.autolayout.AutoLayoutWidgetActivity
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.lang.reflect.Field


/**
 * Created by Administrator on 2017/8/7.
 */

abstract class BaseActivity : AutoLayoutWidgetActivity() {
    val compositeDisposable = CompositeDisposable()

    abstract fun getLayoutId(): Int

    protected val `this`: BaseActivity
        get() = this

    val statusBarHeight: Int
        get() {
            var c: Class<*>? = null
            var obj: Any? = null
            var field: Field? = null
            var x = 0
            try {
                c = Class.forName("com.android.internal.R\$dimen")
                obj = c!!.newInstance()
                field = c.getField("status_bar_height")
                x = Integer.parseInt(field!!.get(obj).toString())
                return resources.getDimensionPixelSize(x)
            } catch (e1: Exception) {
                Log.d(packageName, "get status bar height fail")
                e1.printStackTrace()
                return 75
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setContentView(getLayoutId())
        initListener()
        initData()
        EventBus.getDefault().register(this)
    }

      /**
     * 设置Locale
     */
     /* fun setLocale() {
        if (!I18NUtils.isSameLanguage(this)) {
            I18NUtils.setLocale(this)
            I18NUtils.toRestartMainActvity(this)
        }
    }*/

    private var statusBarView: View? = null
    private fun initStatusBar() {
        if (statusBarView == null) {
            val identifier = resources.getIdentifier("statusBarBackground", "id", "android")
            statusBarView = window.findViewById<View>(identifier)
        }
        if (statusBarView != null) {
            //statusBarView!!.setBackgroundResource(R.drawable.shape_background)
        }
    }

    protected fun isStatusBar(): Boolean {
        return true
    }

    abstract fun initListener()
    protected abstract fun initData()


    /**
     * 全屏
     */
    protected fun fullScreen() {
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //全屏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    protected fun initList(list: RecyclerView) {
        list.layoutManager = LinearLayoutManager(this)
        list.addItemDecoration(HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.line_color).build())
        if (list is LRecyclerView) {
            //设置头部加载颜色
            list.setHeaderViewColor(R.color.blue, R.color.blue, R.color.default_bg)
            //设置底部加载颜色
            list.setFooterViewColor(R.color.blue, R.color.blue, R.color.default_bg)
            //设置底部加载文字提示
            //list.setFooterViewHint(getString(R.string.refresh_loading), getString(R.string.refresh_all), getString(R.string.refresh_no_network))
        }
    }

    protected fun initListNoDiv(list: RecyclerView) {
        list.layoutManager = LinearLayoutManager(this)
        //list.addItemDecoration(new HorizontalDividerItemDecoration.DialogBuilder(this).colorResId(R.color.line_color).build());
        if (list is LRecyclerView) {
            //设置头部加载颜色
            list.setHeaderViewColor(R.color.blue, R.color.blue, R.color.default_bg)
            //设置底部加载颜色
            list.setFooterViewColor(R.color.blue, R.color.blue, R.color.default_bg)
            //设置底部加载文字提示
            //list.setFooterViewHint(getString(R.string.refresh_loading), getString(R.string.refresh_all), getString(R.string.refresh_no_network))
        }
    }

    /*protected fun initList(list: RecyclerView, lineHeight: Int) {
        val size = DensityUtil.dip2px(`this`, lineHeight.toFloat())
        list.layoutManager = LinearLayoutManager(this)
        list.addItemDecoration(HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.line_color).size(size).build())
        if (list is LRecyclerView) {
            //设置头部加载颜色
            list.setHeaderViewColor(R.color.blue, R.color.blue, R.color.default_bg)
            //设置底部加载颜色
            list.setFooterViewColor(R.color.blue, R.color.blue, R.color.default_bg)
            //设置底部加载文字提示
            list.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧")
        }
    }*/

    protected fun initGrid(list: RecyclerView, spanCount: Int) {
        list.setHasFixedSize(true)
        list.layoutManager = GridLayoutManager(this, spanCount)
        if (list is LRecyclerView) {
            //设置头部加载颜色
            list.setHeaderViewColor(R.color.blue, R.color.blue, R.color.default_bg)
            //设置底部加载颜色
            list.setFooterViewColor(R.color.blue, R.color.blue, R.color.default_bg)
            //设置底部加载文字提示
            //list.setFooterViewHint(getString(R.string.refresh_loading), getString(R.string.refresh_all), getString(R.string.refresh_no_network))
        }
        //list.addItemDecoration(new HorizontalDividerItemDecoration.DialogBuilder(this).colorResId(R.color.line_color).build());
    }

    fun setTranslucentStatus() {
        //透明状态栏
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    public override fun onResume() {
        super.onResume()
    }

    public override fun onPause() {
        super.onPause()
    }

    protected fun setDrawableLeft(textView: TextView, srcId: Int) {
        val drawable = resources.getDrawable(srcId)
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        textView.setCompoundDrawables(drawable, null, null, null)
    }

    protected fun setDrawableTop(textView: TextView, srcId: Int) {
        val drawable = resources.getDrawable(srcId)
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        textView.setCompoundDrawables(null, drawable, null, null)
    }

    @Subscribe
    public open fun onReceived(msgBean: MsgBean){

    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
        compositeDisposable.clear()
    }
}
