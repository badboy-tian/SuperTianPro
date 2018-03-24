package com.i7play.supertian.beans

/**
 * Created by tian on 2017/12/7.
 */
class MsgBean: BaseBean {
    companion object {
        val LOGINED = 1000
    }

    var from = ""
    var data:Any ?= null
    var code = 0

    /*constructor(type: Int) : super() {
        this.type = type
    }

    constructor(type: Int, from:String) : super() {
        this.type = type
        this.from = from
    }

    constructor(type: Int, from:String, reqNum: Int) : super() {
        this.type = type
        this.from = from
        this.reqNum = reqNum
    }*/

    constructor(from: String, code: Int, data: Any){
        this.from = from
        this.code = code
        this.data = data
    }

}