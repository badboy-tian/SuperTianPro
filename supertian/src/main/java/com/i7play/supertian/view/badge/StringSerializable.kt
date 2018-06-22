package com.i7play.supertian.view.badge

import android.content.Context
import android.text.TextUtils
import android.util.Log

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.StreamCorruptedException

object StringSerializable {


    private val FILENAME = "SuperBadgeDater"

    /**
     * desc:保存对象
     * @param context
     * @param key
     * @param obj 要保存的对象，只能保存实现了serializable的对象
     * modified:
     */
    internal fun saveSuperBadgeHelper(context: Context, key: String, obj: SuperBadgeHelper) {
        try {
            // 保存对象
            val sharedata = context.getSharedPreferences(FILENAME, 0).edit()
            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            val bos = ByteArrayOutputStream()
            val os = ObjectOutputStream(bos)
            //将对象序列化写入byte缓存
            os.writeObject(obj)
            //将序列化的数据转为16进制保存
            val bytesToHexString = bytesToHexString(bos.toByteArray())
            //保存该16进制数组
            sharedata.putString(key, bytesToHexString)
            sharedata.commit()
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("", "保存SuperBadgeHelper失败")
        }

    }

    /**
     * desc:将数组转为16进制
     * @param bArray
     * @return
     * modified:
     */
    private fun bytesToHexString(bArray: ByteArray?): String? {
        if (bArray == null) {
            return null
        }
        if (bArray.isEmpty()) {
            return ""
        }
        val sb = StringBuffer(bArray.size)
        var sTemp: String
        for (i in bArray.indices) {
            sTemp = Integer.toHexString(0xFF and bArray[i].toInt())
            if (sTemp.length < 2)
                sb.append(0)
            sb.append(sTemp.toUpperCase())
        }
        return sb.toString()
    }

    /**
     * desc:获取保存的SuperBadgeHelper对象
     * @param context
     * @param key
     * @return
     * modified:
     */
    internal fun readSuperBadgeHelper(context: Context, key: String): SuperBadgeHelper? {
        try {
            val sharedata = context.getSharedPreferences(FILENAME, 0)
            if (sharedata.contains(key)) {
                val string = sharedata.getString(key, "")
                if (TextUtils.isEmpty(string)) {
                    return null
                } else {
                    //将16进制的数据转为数组，准备反序列化
                    val stringToBytes = StringToBytes(string!!)
                    val bis = ByteArrayInputStream(stringToBytes)
                    val `is` = ObjectInputStream(bis)
                    //返回反序列化得到的对象
                    return `is`.readObject() as SuperBadgeHelper
                }
            }
        } catch (e: StreamCorruptedException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        //所有异常返回null
        return null

    }

    /**
     * desc:将16进制的数据转为数组
     *
     * @param data
     * @return
     * modified:
     */
    private fun StringToBytes(data: String): ByteArray? {
        val hexString = data.toUpperCase().trim { it <= ' ' }
        if (hexString.length % 2 != 0) {
            return null
        }
        val retData = ByteArray(hexString.length / 2)
        var i = 0
        while (i < hexString.length) {
            val int_ch: Int  // 两位16进制数转化后的10进制数
            val hex_char1 = hexString[i] ////两位16进制数中的第一位(高位*16)
            val int_ch1: Int
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1.toInt() - 48) * 16   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1.toInt() - 55) * 16 //// A 的Ascll - 65
            else
                return null
            i++
            val hex_char2 = hexString[i] ///两位16进制数中的第二位(低位)
            val int_ch2: Int
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = hex_char2.toInt() - 48 //// 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2.toInt() - 55 //// A 的Ascll - 65
            else
                return null
            int_ch = int_ch1 + int_ch2
            retData[i / 2] = int_ch.toByte()//将转化后的数放入Byte里
            i++
        }
        return retData
    }
}
