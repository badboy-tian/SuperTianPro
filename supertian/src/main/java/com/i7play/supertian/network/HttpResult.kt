package com.i7play.supertian.network

class HttpResult<T> {
    var errno = 0
    var msg = ""
    var data: T? = null
}
