package com.i7play.supertian.network

/**
 * Created by Administrator on 2017/8/15.
 * 自定义API异常类
 */

class ApiException
/*public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }*/
(code: Int, detailMessage: String) : RuntimeException(detailMessage) {

    var code = -1

    init {
        this.code = code
    }

    companion object {

        val USER_NOT_EXIST = 100
        val WRONG_PASSWORD = 101


        /**
         * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
         * 需要根据错误码对错误信息进行一个转换，在显示给用户
         * @param code
         * @return
         */
        private fun getApiExceptionMessage(code: Int): String {
            var message = ""
            when (code) {
                USER_NOT_EXIST -> message = "该用户不存在"
                WRONG_PASSWORD -> message = "密码错误"
                else -> message = "未知错误"
            }
            return message
        }
    }
}