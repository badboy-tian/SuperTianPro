package com.i7play.supertian.utils

import android.os.Build
import android.util.Base64

import java.security.Provider
import java.security.SecureRandom

import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/*******************************************************************************
 * AES加解密算法
 *
 * @author arix04
 */

object AES {
    // 加密
    @Throws(Exception::class)
    fun Encrypt(sSrc: String, password: String): String {
        val skeySpec = SecretKeySpec(generateKey(password.toByteArray()), "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")//"算法/模式/补码方式"
        val iv = IvParameterSpec(ByteArray(cipher.blockSize))//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
        val encrypted = cipher.doFinal(sSrc.toByteArray())
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    @Throws(Exception::class)
    fun generateKey(bArr: ByteArray): ByteArray {
        val instance: SecureRandom
        val instance2 = KeyGenerator.getInstance("AES")
        if (Build.VERSION.SDK_INT >= 17) {
            instance = SecureRandom.getInstance("SHA1PRNG", MyProvider())
        } else {
            instance = SecureRandom.getInstance("SHA1PRNG")
        }
        instance.setSeed(bArr)
        instance2.init(128, instance)
        return instance2.generateKey().encoded
    }

    internal class MyProvider : Provider("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)") {
        init {
            put("SecureRandom.SHA1PRNG", "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl")
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software")
        }
    }

    // 解密
    @Throws(Exception::class)
    fun Decrypt(sSrc: String, password: String): String? {
        try {
            val skeySpec = SecretKeySpec(generateKey(password.toByteArray()), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val iv = IvParameterSpec(ByteArray(cipher.blockSize))
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)

            val encrypted1 = Base64.decode(sSrc, Base64.DEFAULT)
            try {
                val original = cipher.doFinal(encrypted1)
                return String(original)
            } catch (e: Exception) {
                return null
            }

        } catch (ex: Exception) {
            println(ex.toString())
            return null
        }

    }
}