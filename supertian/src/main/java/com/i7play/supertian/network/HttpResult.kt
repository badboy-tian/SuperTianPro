package io.haobi.wallet.network

class HttpResult<T> {
    var errno = 0
    var msg = ""
    var data: T? = null
}
