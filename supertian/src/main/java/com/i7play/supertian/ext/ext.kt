package com.i7play.supertian.ext

import android.app.Activity
import android.content.Intent
import android.text.Html
import android.text.Spanned
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.i7play.supertian.beans.BaseBean
import com.i7play.supertian.view.BindTextsWatcher
import com.jkt.tdialog.TDialog
import io.haobi.wallet.utils.NetworkUtil
import io.haobi.wallet.utils.SharedPrefsUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.math.BigDecimal


/**
 * Created by Administrator on 2017/10/1.
 */
fun <T : Activity> Activity.jumpTo(cls: Class<T>) {
    startActivity(Intent(this, cls))
}

/*fun Activity.toSetLanguage(type: Int) {
    I18NUtils.putLanguageType(this, type)
    if (I18NUtils.isSameLanguage(this, type)) {
        return
    }
    I18NUtils.setLocale(this, type)
    I18NUtils.toRestartMainActvity(this)
}*/

fun <T : Activity> Activity.jumpToAndFinish(cls: Class<T>) {
    startActivity(Intent(this, cls))
    finish()
}

fun <T : Activity> Activity.jumpForResult(cls: Class<T>, requestCode: Int) {
    val i = Intent(this, cls)
    startActivityForResult(i, requestCode)
}

fun <T : Activity> Activity.jumpForResult(cls: Class<T>, map: HashMap<String, Any>, requestCode: Int) {
    val i = Intent(this, cls)
    i.putExtra("map", map)
    startActivityForResult(i, requestCode)
}

fun <T : Activity> Activity.jumpForResult(cls: Class<T>, requestCode: Int, key: String, value: String) {
    val i = Intent(this, cls)
    i.putExtra(key, value)
    startActivityForResult(i, requestCode)
}

fun <T : Activity> Activity.jumpForResult(cls: Class<T>, requestCode: Int, fromCode: Int) {
    val i = Intent(this, cls)
    i.putExtra("from", fromCode)
    startActivityForResult(i, requestCode)
}

fun <T : Activity> Activity.jumpForResult(cls: Class<T>, requestCode: Int, fromCode: String) {
    val i = Intent(this, cls)
    i.putExtra("from", fromCode)
    startActivityForResult(i, requestCode)
}

fun <T : Activity> Activity.jumpForResult(cls: Class<T>, requestCode: Int, bean: BaseBean) {
    val i = Intent(this, cls)
    i.putExtra("item", bean)
    startActivityForResult(i, requestCode)
}

fun <T : Activity> Activity.jumpTo(cls: Class<T>, key: String, value: String) {
    val i = Intent(this, cls)
    i.putExtra(key, value)
    startActivity(i)
}

fun <T : Activity> Activity.jumpTo(cls: Class<T>, map: HashMap<String, Any>) {
    val i = Intent(this, cls)
    i.putExtra("map", map)
    startActivity(i)
}

fun <T : Activity> Activity.jumpTo(cls: Class<T>, intent: Intent) {
    intent.setClass(this, cls)
    startActivity(intent)
}

fun <T : Activity> Activity.jumpToAndFinish(cls: Class<T>, key: String, value: String) {
    val i = Intent(this, cls)
    i.putExtra(key, value)
    startActivity(i)
    finish()
}

fun <T : Activity> Activity.jumpToAndFinish(cls: Class<T>, map: HashMap<String, Any>) {
    val i = Intent(this, cls)
    i.putExtra("map", map)
    startActivity(i)
    finish()
}

fun <T : Activity> Activity.jumpToAndFinish(cls: Class<T>, key: String, value: Int) {
    val i = Intent(this, cls)
    i.putExtra(key, value)
    startActivity(i)
    finish()
}

fun <T : Activity> Activity.jumpTo(cls: Class<T>, item: BaseBean) {
    val i = Intent(this, cls)
    i.putExtra("item", item)
    startActivity(i)
}

fun <T : Activity> Activity.jumpToAndFinish(cls: Class<T>, bean: BaseBean) {
    val i = Intent(this, cls)
    i.putExtra("item", bean)
    startActivity(i)
    finish()
}

fun <T : Activity> Activity.jumpToAndFinish(cls: Class<T>, intent: Intent) {
    intent.setClass(this, cls)
    startActivity(intent)
    finish()
}

fun Activity.hasNetWork(): Boolean {
    if (!NetworkUtil.isNetworkAvailable(this)) {
        toast("网络不可用, 请检查网络!")
        return false
    }

    return true
}

fun Activity.toast(s: String) {
    Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
}

fun Activity.toast(s: CharSequence) {
    Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
}

/*fun Context.sendType(type: Int) {
    val msg = io.haobi.wallet.beans.MsgBean(type)
    EventBus.getDefault().post(msg)
}

fun Context.sendType(type: Int, from: String) {
    val msg = io.haobi.wallet.beans.MsgBean(type, from)
    EventBus.getDefault().post(msg)
}

fun Context.sendType(type: Int, from: String, reqNum: Int) {
    val msg = io.haobi.wallet.beans.MsgBean(type, from, reqNum)
    EventBus.getDefault().post(msg)
}*/

fun Activity.setHtml(textView: TextView, s: String) {
    val result: Spanned = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(s)
    }
    textView.text = result
}

/**
 * 设置页面的透明度
 * @param bgAlpha 1表示不透明
 */
fun Activity.setBackgroundAlpha(bgAlpha: Float) {
    val lp = window.attributes
    lp.alpha = bgAlpha
    if (bgAlpha == 1f) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    } else {
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }
    window.attributes = lp
}

fun Activity.showTDialog(title: String, msg: String, btns: Array<String>, listener: TDialog.onItemClickListener): TDialog {
    val dialog = TDialog(this, TDialog.Style.Center, btns, title, msg, listener)
    dialog.setCancelable(false)
    dialog.show()

    return dialog
}


fun Activity.getSelected(): Int {
    return SharedPrefsUtils.getIntegerPreference(this, "defaultSelectedId", 35)
}

fun Activity.setSelected(value: Int) {
    SharedPrefsUtils.setIntegerPreference(this, "defaultSelectedId", value)
}

fun Activity.getLangungeSelected(): Int {
    return SharedPrefsUtils.getIntegerPreference(this, "defaultSelectedLangunge", 0)
}

fun Activity.setLangungeSelected(value: Int) {
    SharedPrefsUtils.setIntegerPreference(this, "defaultSelectedLangunge", value)
}

/**
 * 设置当前的国家
 */
/*fun Activity.setCurrentCountry(value: CodeItem) {
    SharedPrefsUtils.setStringPreference(this, "defaultSelectedCountry", "${value.name}|${value.nameEn}|${value.code}")
}*/

/**
 * 获取当前的国家
 */
fun Activity.getCurrentCountry(): List<String> {
    return SharedPrefsUtils.getStringPreference(this, "defaultSelectedCountry", "中国|China|+86")!!.split("|")!!
}

/*fun Activity.getCurrentCountryName(): String {
    val code = I18NUtils.getLanguageType(this)
    when (code) {
        0 -> {//跟随系统
            return if (CommonUtils.isChina) getCurrentCountry()[0] else getCurrentCountry()[1]
        }
        1 -> {//中文
            return getCurrentCountry()[0]
        }

        2 -> {//英文
            return getCurrentCountry()[1]
        }
    }

    return if (CommonUtils.isChina) getCurrentCountry()[0] else getCurrentCountry()[1]
}*/

fun Activity.getCurrentCountryCode(): String {
    return getCurrentCountry()[2]
}

/**
 * 默认的法币
 */
fun Activity.getFaSelected(): Int {
    return SharedPrefsUtils.getIntegerPreference(this, "defaultFaSelectedId", 0)
}

fun Activity.setFaSelected(value: Int) {
    SharedPrefsUtils.setIntegerPreference(this, "defaultFaSelectedId", value)
}


fun Activity.addTextWatcher(btn: Button, editTexts: Array<EditText>) {
    editTexts.forEach {
        it.addTextChangedListener(BindTextsWatcher(btn, editTexts))
    }
}

fun Disposable.addTo(com: CompositeDisposable): Disposable = apply {
    com.add(this)
}

fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this).toDouble()

fun BigDecimal.format(n: Int) : String{
    return setScale(n, BigDecimal.ROUND_HALF_DOWN).toPlainString()
}