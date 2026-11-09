// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.model.analyzeRule

import com.script.SimpleBindings
import io.legado.app.adapters.ReaderAdapterHelper
import io.legado.app.constant.AppConst
import io.legado.app.constant.AppPattern
import io.legado.app.data.entities.BaseSource
import io.legado.app.data.entities.Book
import io.legado.app.data.entities.BookChapter
import io.legado.app.exception.ConcurrentException
import io.legado.app.help.CacheManager
import io.legado.app.help.JsExtensions
import io.legado.app.help.http.CookieStore
import io.legado.app.help.http.RequestMethod
import io.legado.app.help.http.StrResponse
import io.legado.app.help.http.addHeaders
import io.legado.app.help.http.get
import io.legado.app.help.http.getProxyClient
import io.legado.app.help.http.newCallResponse
import io.legado.app.help.http.newCallResponseBody
import io.legado.app.help.http.newCallStrResponse
import io.legado.app.help.http.postForm
import io.legado.app.help.http.postJson
import io.legado.app.help.http.postMultipart
import io.legado.app.model.DebugLog
import io.legado.app.utils.Base64
import io.legado.app.utils.EncoderUtils
import io.legado.app.utils.GSON
import io.legado.app.utils.NetworkUtils
import io.legado.app.utils.StringUtils
import io.legado.app.utils.fromJsonArray
import io.legado.app.utils.fromJsonObject
import io.legado.app.utils.isDataUrl
import io.legado.app.utils.isJson
import io.legado.app.utils.isJsonArray
import io.legado.app.utils.isJsonObject
import io.legado.app.utils.isXml
import io.legado.app.utils.splitNotBlank
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.URLEncoder
import java.util.HashMap
import java.util.LinkedHashMap
import java.util.regex.Pattern

/**
 * 一次网络请求的完整描述：把书源里的 url 规则（含 js、`{{}}`、`<页码>`、`,{...}` 选项）
 * 解析成真正的 url / 请求头 / 请求体，并持有对应的 okhttp 调用与并发率控制。
 */
class AnalyzeUrl(
    val mUrl: String,
    val key: String? = null,
    val page: Int? = null,
    val speakText: String? = null,
    val speakSpeed: Int? = null,
    var baseUrl: String = "",
    override val source: BaseSource? = null,
    private val ruleData: RuleDataInterface? = null,
    private val chapter: BookChapter? = null,
    headerMapF: Map<String, String>? = null,
    var debugLog: DebugLog? = null
) : JsExtensions {

    override val logger: DebugLog?
        get() = debugLog

    var ruleUrl: String = ""
        private set
    var url: String = ""
        private set
    var body: String? = null
        private set
    var type: String? = null
        private set
    val headerMap = HashMap<String, String>()
    private var urlNoQuery: String = ""
    private var queryStr: String? = null
    private val fieldMap = LinkedHashMap<String, String>()
    private var charset: String? = null
    private var method: RequestMethod = RequestMethod.GET
    private var proxy: String? = null
    private var retry: Int = 0
    private var useWebView: Boolean = false
    private var webJs: String? = null
    private val enabledCookieJar: Boolean = source?.enabledCookieJar ?: false

    init {
        if (!mUrl.isDataUrl()) {
            val urlMatcher = paramPattern.matcher(baseUrl)
            if (urlMatcher.find()) {
                baseUrl = baseUrl.substring(0, urlMatcher.start())
            }
            (headerMapF ?: source?.getHeaderMap(true))?.let {
                headerMap.putAll(it)
                if (it.containsKey("proxy")) {
                    proxy = it["proxy"]
                    headerMap.remove("proxy")
                }
            }
            initUrl()
        }
    }

    override val userNameSpace: String
        get() = ruleData?.getUserNameSpace() ?: "unknow"

    fun initUrl() {
        ruleUrl = mUrl
        analyzeJs()
        replaceKeyPageJs()
        analyzeUrl()
    }

    private fun analyzeJs() {
        var start = 0
        var tmp: String
        val jsMatcher = AppPattern.JS_PATTERN.matcher(ruleUrl)
        while (jsMatcher.find()) {
            if (jsMatcher.start() > start) {
                tmp = ruleUrl.substring(start, jsMatcher.start()).trim()
                if (tmp.length > 0) {
                    ruleUrl = tmp.replace("@result", ruleUrl)
                }
            }
            val js = jsMatcher.group(2) ?: jsMatcher.group(1)
            ruleUrl = evalJS(js, ruleUrl) as String
            start = jsMatcher.end()
        }
        if (ruleUrl.length > start) {
            tmp = ruleUrl.substring(start).trim()
            if (tmp.length > 0) {
                ruleUrl = tmp.replace("@result", ruleUrl)
            }
        }
    }

    private fun replaceKeyPageJs() {
        if (ruleUrl.contains("{{") && ruleUrl.contains("}}")) {
            val analyze = RuleAnalyzer(ruleUrl)
            val url = analyze.innerRule("{{", "}}") {
                val jsEval = evalJS(it) ?: ""
                when {
                    jsEval is String -> jsEval
                    jsEval is Double && jsEval % 1.0 == 0.0 -> String.format("%.0f", jsEval)
                    else -> jsEval.toString()
                }
            }
            if (url.length > 0) {
                ruleUrl = url
            }
        }
        page?.let {
            val matcher = pagePattern.matcher(ruleUrl)
            while (matcher.find()) {
                val pages = matcher.group(1)!!.split(",")
                ruleUrl = if (page < pages.size) {
                    ruleUrl.replace(matcher.group(), pages[page - 1].trim())
                } else {
                    ruleUrl.replace(matcher.group(), pages.last().trim())
                }
            }
        }
    }

    private fun analyzeUrl() {
        val urlMatcher = paramPattern.matcher(ruleUrl)
        val urlNoOption = if (urlMatcher.find()) {
            ruleUrl.substring(0, urlMatcher.start())
        } else {
            ruleUrl
        }
        url = NetworkUtils.getAbsoluteURL(baseUrl, urlNoOption)
        NetworkUtils.getBaseUrl(url)?.let { baseUrl = it }
        if (urlNoOption.length != ruleUrl.length) {
            val option = GSON.fromJsonObject<UrlOption>(ruleUrl.substring(urlMatcher.end())).getOrNull()
            if (option != null) {
                option.getMethod()?.let {
                    if (it.equals("POST", true)) {
                        method = RequestMethod.POST
                    }
                }
                option.getHeaderMap()?.let { headers ->
                    headers.forEach { entry ->
                        headerMap[entry.key.toString()] = entry.value.toString()
                    }
                }
                option.getBody()?.let { body = it }
                type = option.getType()
                charset = option.getCharset()
                retry = option.getRetry()
                useWebView = option.useWebView()
                webJs = option.getWebJs()
                option.getJs()?.let { js ->
                    evalJS(js, url)?.toString()?.let { url = it }
                }
            }
        }
        if (headerMap["User-Agent"] == null) {
            headerMap["User-Agent"] = AppConst.userAgent
        }
        urlNoQuery = url
        when (method) {
            RequestMethod.GET -> {
                val pos = url.indexOf('?')
                if (pos != -1) {
                    analyzeFields(url.substring(pos + 1))
                    urlNoQuery = url.substring(0, pos)
                }
            }
            RequestMethod.POST -> {
                body?.let {
                    if (it.isJson() || it.isXml()) return@let
                    if (!headerMap["Content-Type"].isNullOrEmpty()) return@let
                    analyzeFields(it)
                }
            }
        }
    }

    private fun analyzeFields(fieldsTxt: String) {
        queryStr = fieldsTxt
        val queryS = fieldsTxt.splitNotBlank("&")
        for (query in queryS) {
            val queryM = query.splitNotBlank("=")
            val value = if (queryM.size > 1) queryM[1] else ""
            when {
                charset.isNullOrEmpty() -> {
                    if (NetworkUtils.hasUrlEncoded(value)) {
                        fieldMap[queryM[0]] = value
                    } else {
                        fieldMap[queryM[0]] = URLEncoder.encode(value, "UTF-8")
                    }
                }
                charset == "escape" -> {
                    fieldMap[queryM[0]] = EncoderUtils.escape(value)
                }
                else -> {
                    fieldMap[queryM[0]] = URLEncoder.encode(value, charset)
                }
            }
        }
    }

    fun evalJS(jsStr: String, result: Any? = null): Any? {
        val bindings = SimpleBindings()
        bindings["java"] = this
        bindings["baseUrl"] = baseUrl
        bindings["cookie"] = CookieStore(userNameSpace)
        bindings["cache"] = CacheManager(userNameSpace)
        bindings["page"] = page
        bindings["key"] = key
        bindings["speakText"] = speakText
        bindings["speakSpeed"] = speakSpeed
        bindings["book"] = ruleData as? Book
        bindings["source"] = source
        bindings["result"] = result
        return AppConst.SCRIPT_ENGINE.eval(jsStr, bindings)
    }

    fun put(key: String, value: String): String {
        chapter?.putVariable(key, value) ?: ruleData?.putVariable(key, value)
        return value
    }

    fun get(key: String): String {
        if (key == "bookName") {
            (ruleData as? Book)?.let { return it.name }
        } else if (key == "title") {
            chapter?.let { return it.title }
        }
        chapter?.getVariable(key)?.let { return it }
        return ruleData?.getVariable(key) ?: ""
    }

    private fun fetchStart(): ConcurrentRecord? {
        val source = source ?: return null
        val concurrentRate = source.concurrentRate
        if (concurrentRate.isNullOrEmpty()) {
            return null
        }
        val rateIndex = concurrentRate.indexOf("/")
        var fetchRecord: ConcurrentRecord? = null
        fetchRecord = concurrentRecordMap[source.key]
        if (fetchRecord == null) {
            fetchRecord = ConcurrentRecord(rateIndex > 0, System.currentTimeMillis(), 1)
            concurrentRecordMap[source.key] = fetchRecord
            return fetchRecord
        }
        val waitTime = synchronized(fetchRecord) {
            try {
                if (rateIndex == -1) {
                    if (fetchRecord.frequency > 0) {
                        concurrentRate.toInt()
                    } else {
                        val nextTime = fetchRecord.time + concurrentRate.toInt()
                        if (System.currentTimeMillis() >= nextTime) {
                            fetchRecord.time = System.currentTimeMillis()
                            fetchRecord.frequency = 1
                            0
                        } else {
                            (nextTime - System.currentTimeMillis()).toInt()
                        }
                    }
                } else {
                    val sj = concurrentRate.substring(rateIndex + 1)
                    val nextTime = fetchRecord.time + sj.toInt()
                    if (System.currentTimeMillis() >= nextTime) {
                        fetchRecord.time = System.currentTimeMillis()
                        fetchRecord.frequency = 1
                        0
                    } else {
                        val cs = concurrentRate.substring(0, rateIndex)
                        if (fetchRecord.frequency > cs.toInt()) {
                            (nextTime - System.currentTimeMillis()).toInt()
                        } else {
                            fetchRecord.frequency++
                            0
                        }
                    }
                }
            } catch (e: Exception) {
                0
            }
        }
        if (waitTime > 0) {
            throw ConcurrentException("根据并发率还需等待${waitTime}毫秒才可以访问", waitTime)
        }
        return fetchRecord
    }

    private fun fetchEnd(concurrentRecord: ConcurrentRecord?) {
        if (concurrentRecord != null && !concurrentRecord.concurrent) {
            synchronized(concurrentRecord) {
                concurrentRecord.frequency = concurrentRecord.frequency - 1
            }
        }
    }

    suspend fun getStrResponseAwait(
        jsStr: String? = null,
        sourceRegex: String? = null,
        useWebView: Boolean = true
    ): StrResponse {
        if (type != null) {
            val url = url
            val bytes = getByteArrayAwait()
            return StrResponse(url, StringUtils.byteToHexString(bytes))
        }
        val concurrentRecord = fetchStart()
        setCookie(source?.key)
        val strResponse: StrResponse
        if (this.useWebView && useWebView) {
            strResponse = when (method) {
                RequestMethod.POST -> {
                    ReaderAdapterHelper.getAdapter().getStrResponseByRemoteWebview(
                        url = urlNoQuery,
                        tag = source?.key,
                        headerMap = headerMap,
                        sourceRegex = sourceRegex,
                        javaScript = webJs ?: jsStr,
                        post = true,
                        body = body,
                        userNameSpace = userNameSpace,
                        debugLog = debugLog
                    )!!
                }
                else -> {
                    ReaderAdapterHelper.getAdapter().getStrResponseByRemoteWebview(
                        url = url,
                        tag = source?.key,
                        headerMap = headerMap,
                        sourceRegex = sourceRegex,
                        javaScript = webJs ?: jsStr,
                        userNameSpace = userNameSpace,
                        debugLog = debugLog
                    )!!
                }
            }
        } else {
            strResponse = getProxyClient(proxy, debugLog).newCallStrResponse(retry) {
                addHeaders(headerMap)
                when (method) {
                    RequestMethod.POST -> {
                        url(urlNoQuery)
                        val contentType = headerMap["Content-Type"]
                        val body = body
                        if (fieldMap.isEmpty()) {
                            if (body.isNullOrBlank()) {
                                postForm(fieldMap, true)
                            } else if (!contentType.isNullOrBlank()) {
                                post(body.toRequestBody(contentType.toMediaType()))
                            } else {
                                postJson(body)
                            }
                        } else {
                            postForm(fieldMap, true)
                        }
                    }
                    else -> get(urlNoQuery, fieldMap, true)
                }
            }.also {
                saveCookieJar(it.raw)
            }
        }
        fetchEnd(concurrentRecord)
        return strResponse
    }

    fun saveCookieJar(response: okhttp3.Response) {
        val cookieList = response.headers("Set-Cookie")
        if (cookieList.size > 0) {
            val cookieStore = CookieStore(userNameSpace)
            val domain = NetworkUtils.getSubDomain(url)
            cookieList.forEach {
                cookieStore.replaceCookie("${domain}_cookieJar", it)
            }
        }
    }

    @JvmOverloads
    fun getStrResponse(
        jsStr: String? = null,
        sourceRegex: String? = null,
        useWebView: Boolean = true
    ): StrResponse = runBlocking {
        getStrResponseAwait(jsStr, sourceRegex, useWebView)
    }

    suspend fun getResponseAwait(): okhttp3.Response {
        val concurrentRecord = fetchStart()
        setCookie(source?.key)
        val response = getProxyClient(proxy).newCallResponse(retry) {
            addHeaders(headerMap)
            when (method) {
                RequestMethod.POST -> {
                    url(urlNoQuery)
                    val contentType = headerMap["Content-Type"]
                    val body = body
                    if (fieldMap.isEmpty()) {
                        if (body.isNullOrBlank()) {
                            postForm(fieldMap, true)
                        } else if (!contentType.isNullOrBlank()) {
                            post(body.toRequestBody(contentType.toMediaType()))
                        } else {
                            postJson(body)
                        }
                    } else {
                        postForm(fieldMap, true)
                    }
                }
                else -> get(urlNoQuery, fieldMap, true)
            }
        }
        fetchEnd(concurrentRecord)
        return response
    }

    fun getResponse(): okhttp3.Response = runBlocking {
        getResponseAwait()
    }

    suspend fun getByteArrayAwait(): ByteArray {
        val concurrentRecord = fetchStart()
        val dataUriFindResult = AppPattern.dataUriRegex.find(urlNoQuery)
        if (dataUriFindResult != null) {
            val dataUriBase64 = dataUriFindResult.groupValues[1]
            val byteArray = Base64.decode(dataUriBase64, 0)
            fetchEnd(concurrentRecord)
            return byteArray
        }
        setCookie(source?.key)
        val byteArray = getProxyClient(proxy).newCallResponseBody(retry) {
            addHeaders(headerMap)
            when (method) {
                RequestMethod.POST -> {
                    url(urlNoQuery)
                    val contentType = headerMap["Content-Type"]
                    val body = body
                    if (fieldMap.isEmpty()) {
                        if (body.isNullOrBlank()) {
                            postForm(fieldMap, true)
                        } else if (!contentType.isNullOrBlank()) {
                            post(body.toRequestBody(contentType.toMediaType()))
                        } else {
                            postJson(body)
                        }
                    } else {
                        postForm(fieldMap, true)
                    }
                }
                else -> get(urlNoQuery, fieldMap, true)
            }
        }.bytes()
        fetchEnd(concurrentRecord)
        return byteArray
    }

    fun getByteArray(): ByteArray = runBlocking {
        getByteArrayAwait()
    }

    suspend fun upload(fileName: String, file: Any, contentType: String): StrResponse {
        return getProxyClient(proxy).newCallStrResponse(retry) {
            url(urlNoQuery)
            val bodyMap = GSON.fromJsonObject<HashMap<String, Any>>(body).getOrNull()!!
            bodyMap.forEach { (k, v) ->
                if (v.toString() == "fileRequest") {
                    bodyMap[k] = mapOf(
                        "fileName" to fileName,
                        "file" to file,
                        "contentType" to contentType
                    )
                }
            }
            postMultipart(type, bodyMap)
        }
    }

    private fun setCookie(tag: String?) {
        val domain = NetworkUtils.getSubDomain(tag ?: url)
        if (domain.isEmpty()) {
            return
        }
        val cookieStore = CookieStore(userNameSpace)
        if (enabledCookieJar) {
            val key = "${domain}_cookieJar"
            cookieStore.getCookie(key)?.let {
                cookieStore.replaceCookie(domain, it)
            }
        }
        val cookie = cookieStore.getCookie(domain)
        if (cookie.isNotEmpty()) {
            val cookieMap = cookieStore.cookieToMap(cookie)
            val customCookieMap = cookieStore.cookieToMap(headerMap["Cookie"] ?: "")
            cookieMap.putAll(customCookieMap)
            cookieStore.mapToCookie(cookieMap)?.let {
                headerMap["Cookie"] = it
            }
        }
    }

    fun getUserAgent(): String {
        return headerMap["User-Agent"] ?: AppConst.userAgent
    }

    fun isPost(): Boolean = method == RequestMethod.POST

    data class UrlOption(
        private var method: String? = null,
        private var charset: String? = null,
        private var headers: Any? = null,
        private var body: Any? = null,
        private var retry: Int? = null,
        private var type: String? = null,
        private var webView: Any? = null,
        private var webJs: String? = null,
        private var js: String? = null
    ) {

        fun getMethod(): String? = method

        fun setMethod(value: String?) {
            method = if (value.isNullOrBlank()) null else value
        }

        fun getCharset(): String? = charset

        fun setCharset(value: String?) {
            charset = if (value.isNullOrBlank()) null else value
        }

        fun setHeaders(value: String?) {
            headers = if (value.isNullOrBlank()) {
                null
            } else {
                GSON.fromJsonObject<Map<String, Any>>(value).getOrNull()
            }
        }

        fun getHeaderMap(): Map<*, *>? {
            return when (val header = headers) {
                is Map<*, *> -> header
                is String -> GSON.fromJsonObject<Map<*, *>>(header).getOrNull()
                else -> null
            }
        }

        fun setBody(value: String?) {
            body = if (value.isNullOrBlank()) {
                null
            } else if (value.isJsonObject()) {
                GSON.fromJsonObject<Map<String, Any>>(value).getOrNull()
            } else if (value.isJsonArray()) {
                GSON.fromJsonArray<Any>(value).getOrNull()
            } else {
                value
            }
        }

        fun getBody(): String? {
            return body?.let {
                if (it is String) it else GSON.toJson(it)
            }
        }

        fun getRetry(): Int = retry ?: 0

        fun setRetry(value: String?) {
            retry = if (value.isNullOrEmpty()) null else value.toIntOrNull()
        }

        fun getType(): String? = type

        fun setType(value: String?) {
            type = if (value.isNullOrBlank()) null else value
        }

        fun useWebView(): Boolean {
            return !(webView == null || webView == "" || webView == false || webView == "false")
        }

        fun useWebView(b: Boolean) {
            webView = if (b) true else null
        }

        fun getWebJs(): String? = webJs

        fun setWebJs(value: String?) {
            webJs = if (value.isNullOrBlank()) null else value
        }

        fun getJs(): String? = js

        fun setJs(value: String?) {
            js = if (value.isNullOrBlank()) null else value
        }
    }

    data class ConcurrentRecord(
        val concurrent: Boolean,
        var time: Long,
        var frequency: Int
    )

    companion object {
        val paramPattern: Pattern = Pattern.compile("\\s*,\\s*(?=\\{)")
        private val pagePattern = Pattern.compile("<(.*?)>")
        private val concurrentRecordMap = hashMapOf<String, ConcurrentRecord>()
    }
}
