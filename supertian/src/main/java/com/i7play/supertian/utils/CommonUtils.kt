package com.i7play.supertian.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v4.content.FileProvider
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.SocketException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object CommonUtils {


    private val MIME_MapTable = arrayOf(
            //{后缀名，MIME类型}
            arrayOf(".3gp", "video/3gpp"), arrayOf(".apk", "application/vnd.android.package-archive"), arrayOf(".asf", "video/x-ms-asf"), arrayOf(".avi", "video/x-msvideo"), arrayOf(".bin", "application/octet-stream"), arrayOf(".bmp", "image/bmp"), arrayOf(".c", "text/plain"), arrayOf(".class", "application/octet-stream"), arrayOf(".conf", "text/plain"), arrayOf(".cpp", "text/plain"), arrayOf(".doc", "application/msword"), arrayOf(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"), arrayOf(".xls", "application/vnd.ms-excel"), arrayOf(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), arrayOf(".exe", "application/octet-stream"), arrayOf(".gif", "image/gif"), arrayOf(".gtar", "application/x-gtar"), arrayOf(".gz", "application/x-gzip"), arrayOf(".h", "text/plain"), arrayOf(".htm", "text/html"), arrayOf(".html", "text/html"), arrayOf(".jar", "application/java-archive"), arrayOf(".java", "text/plain"), arrayOf(".jpeg", "image/jpeg"), arrayOf(".jpg", "image/jpeg"), arrayOf(".js", "application/x-javascript"), arrayOf(".log", "text/plain"), arrayOf(".m3u", "audio/x-mpegurl"), arrayOf(".m4a", "audio/mp4a-latm"), arrayOf(".m4b", "audio/mp4a-latm"), arrayOf(".m4p", "audio/mp4a-latm"), arrayOf(".m4u", "video/vnd.mpegurl"), arrayOf(".m4v", "video/x-m4v"), arrayOf(".mov", "video/quicktime"), arrayOf(".mp2", "audio/x-mpeg"), arrayOf(".mp3", "audio/x-mpeg"), arrayOf(".mp4", "video/mp4"), arrayOf(".mpc", "application/vnd.mpohun.certificate"), arrayOf(".mpe", "video/mpeg"), arrayOf(".mpeg", "video/mpeg"), arrayOf(".mpg", "video/mpeg"), arrayOf(".mpg4", "video/mp4"), arrayOf(".mpga", "audio/mpeg"), arrayOf(".msg", "application/vnd.ms-outlook"), arrayOf(".ogg", "audio/ogg"), arrayOf(".pdf", "application/pdf"), arrayOf(".png", "image/png"), arrayOf(".pps", "application/vnd.ms-powerpoint"), arrayOf(".ppt", "application/vnd.ms-powerpoint"), arrayOf(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"), arrayOf(".prop", "text/plain"), arrayOf(".rc", "text/plain"), arrayOf(".rmvb", "audio/x-pn-realaudio"), arrayOf(".rtf", "application/rtf"), arrayOf(".sh", "text/plain"), arrayOf(".tar", "application/x-tar"), arrayOf(".tgz", "application/x-compressed"), arrayOf(".txt", "text/plain"), arrayOf(".wav", "audio/x-wav"), arrayOf(".wma", "audio/x-ms-wma"), arrayOf(".wmv", "audio/x-ms-wmv"), arrayOf(".wps", "application/vnd.ms-works"), arrayOf(".xml", "text/plain"), arrayOf(".z", "application/x-compress"), arrayOf(".zip", "application/x-zip-compressed"), arrayOf("", "*/*"))

    /*if ("cn".equals(country)) {
                language = "zh-CN";
            } else if ("tw".equals(country)) {
                language = "zh-TW";
            }*/ val isChina: Boolean
        get() {
            val l = Locale.getDefault()
            val language = l.language
            val country = l.country.toLowerCase()
            return "zh" == language

        }

    fun isMobileNO(mobiles: String): Boolean {
        val p = Pattern.compile("^1[0-9]{10}$")
        val m = p.matcher(mobiles)
        return m.matches()
    }

    /* fun md5(str: String): String? {
         val hash: ByteArray

         try {
             hash = MessageDigest.getInstance("MD5").digest(str.toByteArray(charset("UTF-8")))
         } catch (e: NoSuchAlgorithmException) {
             e.printStackTrace()
             return null
         } catch (e: UnsupportedEncodingException) {
             e.printStackTrace()
             return null
         }

         val hex = StringBuilder(hash.size * 2)
         for (b in hash) {
             if (b and 0xFF < 0x10)
                 hex.append("0")
             hex.append(Integer.toHexString(b and 0xFF))
         }

         return hex.toString()
     }*/

    fun isMobileNO(mobiles: Editable): Boolean {
        val p = Pattern.compile("^1[0-9]{10}$")
        val m = p.matcher(mobiles)
        return m.matches()
    }


    fun timeFormat(date: Date): String {
        val formatter = SimpleDateFormat("yyyy.MM.dd")
        return formatter.format(date)
    }


    fun timeFormatYM(date: Date): String {
        val formatter = SimpleDateFormat("yyyy.MM")
        return formatter.format(date)
    }


    fun timeFormatYMD(date: Date): String {
        val formatter = SimpleDateFormat("yyyy.MM.dd")
        return formatter.format(date)
    }


    fun timeFormatY_M_D(date: Date): String {
        val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        return formatter.format(date)
    }


    fun timeFormatY(date: Date): String {
        val formatter = SimpleDateFormat("yyyy")
        return formatter.format(date)
    }

    fun jjTimeFormatYMD(source: String): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        var date: String? = null
        try {
            date = dateFormat.format(dateFormat.parse(source))
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date
    }

    fun jjTimeFormatYMDMS(source: String): String? {
        val formatNew = SimpleDateFormat("yyyy-MM-dd HH:mm")

        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'")
        df.timeZone = TimeZone.getTimeZone("UTC")

        var date: String? = null
        try {
            date = formatNew.format(df.parse(source))
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date
    }

    fun getVerCode(context: Context): Int {
        var verCode = -1
        try {
            verCode = context.packageManager.getPackageInfo(context.packageName, 0).versionCode
        } catch (e: PackageManager.NameNotFoundException) {
        }

        return verCode
    }

    fun getVerName(context: Context): String {
        var verCode = ""
        try {
            verCode = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
        }

        return verCode
    }

    fun getDeviceCode(): String {
        return android.os.Build.VERSION.RELEASE
    }


    fun hideKeyBoard(context: Context, editText: View) {
        editText.clearFocus()
        val `in` = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        `in`.hideSoftInputFromWindow(editText.windowToken, 0)
    }


    fun beforeBigThanAfterDate(before: String, after: String): Boolean {
        val df = SimpleDateFormat("yyyy.MM.dd")
        try {
            val beforeDate = df.parse(before)
            val afterDate = df.parse(after)
            return beforeDate.time > afterDate.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return false
    }


    /**
     * 补充：计算两点之间真实距离
     *
     * @return 米
     */
    fun getDistance(longitude1: Double, latitude1: Double, longitude2: Double, latitude2: Double): Int {
        // 维度
        val lat1 = Math.PI / 180 * latitude1
        val lat2 = Math.PI / 180 * latitude2

        // 经度
        val lon1 = Math.PI / 180 * longitude1
        val lon2 = Math.PI / 180 * longitude2

        // 地球半径
        val R = 6371.0

        // 两点间距离 km，如果想要米的话，结果*1000就可以了
        val d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R
        return (d * 1000).toInt()
    }


    /*public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        final byte[] bytes = bos.toByteArray();
        return Base64Util.encryptBASE64(bytes);
    }*/


    @Throws(Exception::class)
    fun getAge(strDate: String): Int {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val birthDay = sdf.parse(strDate)

        val cal = Calendar.getInstance()

        if (cal.before(birthDay)) {
            throw IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!")
        }
        val yearNow = cal.get(Calendar.YEAR)
        val monthNow = cal.get(Calendar.MONTH)
        val dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH)
        cal.time = birthDay

        val yearBirth = cal.get(Calendar.YEAR)
        val monthBirth = cal.get(Calendar.MONTH)
        val dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH)

        var age = yearNow - yearBirth

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--
            } else {
                age--
            }
        }
        return age
    }


    /* 检查手机上是否安装了指定的软件
      * @param context
      * @param packageName：应用包名
      * @return
          */
    fun isAvilible(context: Context, packageName: String): Boolean {
        //获取packagemanager
        val packageManager = context.packageManager
        //获取所有已安装程序的包信息
        val packageInfos = packageManager.getInstalledPackages(0)
        //用于存储所有已安装程序的包名
        val packageNames = ArrayList<String>()
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (i in packageInfos.indices) {
                val packName = packageInfos[i].packageName
                packageNames.add(packName)
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName)
    }


    /*fun getJson(context: Context, fileName: String): String {

        val stringBuilder = StringBuilder()
        try {
            val assetManager = context.assets
            val bf = BufferedReader(InputStreamReader(assetManager.open(fileName)))
            var line: String
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }*/


    /**
     * @param length 文件大小(以Byte为单位)
     * @return String 格式化的常见文件大小(保留两位小数)
     */
    fun formatFileSize(length: Long): String? {
        var result: String? = null
        var sub_string = 0
        // 如果文件长度大于1GB
        if (length >= 1073741824) {
            sub_string = (length.toFloat() / 1073741824).toString().indexOf(
                    ".")
            result = ((length.toFloat() / 1073741824).toString() + "000").substring(0,
                    sub_string + 3) + "GB"
        } else if (length >= 1048576) {
            // 如果文件长度大于1MB且小于1GB,substring(int beginIndex, int endIndex)
            sub_string = (length.toFloat() / 1048576).toString().indexOf(".")
            result = ((length.toFloat() / 1048576).toString() + "000").substring(0,
                    sub_string + 3) + "MB"
        } else if (length >= 1024) {
            // 如果文件长度大于1KB且小于1MB
            sub_string = (length.toFloat() / 1024).toString().indexOf(".")
            result = ((length.toFloat() / 1024).toString() + "000").substring(0,
                    sub_string + 3) + "KB"
        } else if (length < 1024) {
            result = java.lang.Long.toString(length) + "B"
        }
        return result
    }


    /**
     * 安装APK
     */
    fun install(context: Context, path: String) {
        val file = File(path)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) { //判读版本是否在7.0以上
            val apkUri = FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
        }
        context.startActivity(intent)
    }


    fun showVersion(context: Context): String {
        return getLocalVersionName(context)
    }


    fun getLocalVersion(ctx: Context): Int {
        var localVersion = 0
        try {
            val packageInfo = ctx.applicationContext
                    .packageManager
                    .getPackageInfo(ctx.packageName, 0)
            localVersion = packageInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return localVersion
    }


    /**
     * 获取本地软件版本号名称
     */
    private fun getLocalVersionName(ctx: Context): String {
        var localVersion = ""
        try {
            val packageInfo = ctx.applicationContext.packageManager.getPackageInfo(ctx.packageName, 0)
            localVersion = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return localVersion
    }


    /*public static void update(final Context context, String url) {
        final AppUpdateProgressDialog dialog = new AppUpdateProgressDialog(context);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    Toast.makeText(context, "正在下载请稍后", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    return false;
                }
            }
        });

        dialog.show();
        String path = context.getExternalCacheDir() + "/update.apk";
        BaseDownloadTask task = FileDownloader.getImpl().create(url).setPath(path).setListener(new FileDownloadListenerAdapter() {
            @Override protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                dialog.setProgress((int) ((soFarBytes / (float) totalBytes) * 100f));
            }


            @Override protected void completed(BaseDownloadTask task) {
                dialog.dismiss();
                install(context, task.getPath());
            }


            @Override protected void error(BaseDownloadTask task, Throwable e) {
                e.printStackTrace();
                dialog.dismiss();
                TipUtils.toast(context, "下载失败!错误原因:" + e.getMessage());
            }
        });
        task.setForceReDownload(true);
        task.start();
    }*/


    /*
 * Java文件操作 获取文件扩展名
 *
 *  Created on: 2011-8-2
 *      Author: blueeagle
 */
    fun getExtensionName(filename: String?): String? {
        if (filename != null && filename.length > 0) {
            val dot = filename.lastIndexOf('.')
            if (dot > -1 && dot < filename.length - 1) {
                return filename.substring(dot + 1)
            }
        }
        return filename
    }


    /*
     * Java文件操作 获取不带扩展名的文件名
     *
     *  Created on: 2011-8-2
     *      Author: blueeagle
     */
    fun getFileNameNoEx(filename: String?): String? {
        if (filename != null && filename.length > 0) {
            val dot = filename.lastIndexOf('.')
            if (dot > -1 && dot < filename.length) {
                return filename.substring(0, dot)
            }
        }
        return filename
    }


    fun openUrl(context: Context, url: String) {
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        val content_url = Uri.parse(url)
        intent.data = content_url
        context.startActivity(intent)
    }


    /*fun getMIMEType(file: File): String {
        val type = "*//*"
        val fName = file.name
        //获取后缀名前的分隔符"."在fName中的位置。
        val dotIndex = fName.lastIndexOf(".")
        if (dotIndex < 0) {
            return type
        }
        /* 获取文件的后缀名*/
        val end = fName.substring(dotIndex, fName.length).toLowerCase()
        if (end === "") return type
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (i in MIME_MapTable.indices) {
            if (end == MIME_MapTable[i]) {
                return MIME_MapTable[i][0]
            }
        }

        return type
    }*/

    fun installAPK(context: Context, file: File) {
        if (!file.exists()) return
        val installIntent = Intent(Intent.ACTION_VIEW)
        installIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        var apkUri: Uri? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //判读版本是否在7.0以上
            apkUri = FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else {
            apkUri = Uri.fromFile(file)
        }
        installIntent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        context.startActivity(installIntent)
    }

    //保存文件到指定路径
    fun saveImageToGallery(context: Context, bmp: Bitmap, name: String): Boolean {
        // 首先保存图片
        val storePath = Environment.getExternalStorageDirectory().absolutePath + File.separator + "dcon"
        val appDir = File(storePath)
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        // String fileName = System.currentTimeMillis() + ".jpg";
        val fileName = name + ".jpg"
        val file = File(appDir, fileName)
        try {
            val fos = FileOutputStream(file)
            //通过io流的方式来压缩保存图片
            val isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos)
            fos.flush()
            fos.close()

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            val uri = Uri.fromFile(file)
            context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
            return isSuccess
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return false
    }

    fun isChinese(str: String): Boolean {
        for (i in 0 until str.length) {
            val bb = str.substring(i, i + 1)
            val cc = java.util.regex.Pattern.matches("[\u4E00-\u9FA5]", bb)
            if (!cc) {
                return cc
            }
        }
        return true
    }

    @Throws(SocketException::class)
    fun macAddress(context: Context): String? {
        return Uuid.get(context)
    }


}