// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.api

class ReturnData {

    var isSuccess: Boolean = false
        private set

    var errorMsg: String = "未知错误,请联系开发者!"
        private set

    var data: Any? = null
        private set

    fun setErrorMsg(errorMsg: String): ReturnData {
        isSuccess = false
        this.errorMsg = errorMsg
        return this
    }

    fun setData(data: Any, msg: String = ""): ReturnData {
        isSuccess = true
        errorMsg = msg
        this.data = data
        return this
    }
}
