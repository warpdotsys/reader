// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.help.http

import okhttp3.Headers
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody

class StrResponse {

    var raw: Response
        private set

    var body: String? = null
        private set

    var errorBody: ResponseBody? = null
        private set

    constructor(rawResponse: Response, body: String?) {
        this.raw = rawResponse
        this.body = body
    }

    constructor(url: String, body: String?) {
        this.raw = Response.Builder()
            .code(200)
            .message("OK")
            .protocol(Protocol.HTTP_1_1)
            .request(Request.Builder().url(url).build())
            .build()
        this.body = body
    }

    constructor(rawResponse: Response, errorBody: ResponseBody?) {
        this.raw = rawResponse
        this.errorBody = errorBody
    }

    fun raw(): Response {
        return raw
    }

    fun url(): String {
        raw.networkResponse?.let {
            return it.request.url.toString()
        }
        return raw.request.url.toString()
    }

    fun getUrl(): String {
        return url()
    }

    fun body(): String? {
        return body
    }

    fun code(): Int {
        return raw.code
    }

    fun message(): String {
        return raw.message
    }

    fun headers(): Headers {
        return raw.headers
    }

    fun isSuccessful(): Boolean {
        return raw.isSuccessful
    }

    fun errorBody(): ResponseBody? {
        return errorBody
    }

    override fun toString(): String {
        return raw.toString()
    }
}
