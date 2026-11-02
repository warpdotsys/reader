// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.model.analyzeRule

import io.legado.app.adapters.ReaderAdapterHelper
import io.legado.app.adapters.ReaderAdapterInterface
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
import io.legado.app.utils.fromJsonObject
import io.legado.app.utils.isDataUrl
import io.legado.app.utils.isJson
import io.legado.app.utils.isXml
import io.legado.app.utils.msg
import io.legado.app.utils.splitNotBlank
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.URLEncoder
import java.util.HashMap
import java.util.LinkedHashMap
import java.util.regex.Pattern
import com.script.SimpleBindings

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
    var url: String = ""
    var body: String? = null
    var type: String? = null
    val headerMap = HashMap<String, String>()
    var urlNoQuery: String = ""
    var queryStr: String? = null
    private val fieldMap = LinkedHashMap<String, String>()
    var charset: String? = null
    var method: RequestMethod = RequestMethod.GET
    var proxy: String? = null
    var retry: Int = 0
    var useWebView: Boolean = false
    var webJs: String? = null
    private val enabledCookieJar: Boolean = source?.enabledCookieJar ?: false

    init {
        if (!mUrl.isDataUrl()) {
            val urlMatcher = paramPattern.matcher(baseUrl)
            if (urlMatcher.find()) {
                baseUrl = baseUrl.substring(0, urlMatcher.start())
            }
            val headers = headerMapF ?: source?.getHeaderMap(true)
            headers?.let {
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
        page?.let { page ->
            val matcher = pagePattern.matcher(ruleUrl)
            while (matcher.find()) {
                val pages = matcher.group(1)?.split(",") ?: emptyList()
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
        url = io.legado.app.utils.NetworkUtils.getAbsoluteURL(baseUrl, urlNoOption)
        io.legado.app.utils.NetworkUtils.getBaseUrl(url)?.let { baseUrl = it }
        if (urlNoOption.length != ruleUrl.length) {
            val option = GSON.fromJsonObject<UrlOption>(ruleUrl.substring(urlMatcher.end())).getOrNull()
            if (option != null) {
                option.method?.let {
                    if (it.equals("POST", true)) {
                        method = RequestMethod.POST
                    }
                }
                option.getHeaderMap()?.let { headers ->
                    headers.forEach { (k, v) -> headerMap[k.toString()] = v.toString() }
                }
                option.body?.let { body = it.toString() }
                type = option.type
                charset = option.charset
                retry = option.retry?.toInt() ?: 0
                useWebView = option.useWebView()
                webJs = option.webJs
                option.js?.let { js ->
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
        queryS.forEach { query ->
            val queryM = query.splitNotBlank("=")
            val value = if (queryM.size > 1) queryM[1] else ""
            when {
                charset.isNullOrEmpty() -> {
                    if (io.legado.app.utils.NetworkUtils.hasUrlEncoded(value)) {
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
        var fetchRecord = concurrentRecordMap[source.key]
        if (fetchRecord == null) {
            fetchRecord = ConcurrentRecord(rateIndex > 0, System.currentTimeMillis(), 1)
            concurrentRecordMap[source.key] = fetchRecord
        }
        synchronized(fetchRecord) {
            var waitTime = 0
            try {
                if (rateIndex == -1) {
                    if (fetchRecord.frequency > 0) {
                        val nextTime = fetchRecord.time + concurrentRate.toInt()
                        if (System.currentTimeMillis() >= nextTime) {
                            fetchRecord.time = System.currentTimeMillis()
                            fetchRecord.frequency = 1
                            waitTime = 0
                        } else {
                            waitTime = (nextTime - System.currentTimeMillis()).toInt()
                        }
                    }
                } else {
                    val sj = concurrentRate.substring(rateIndex + 1)
                    val nextTime = fetchRecord.time + sj.toInt()
                    if (System.currentTimeMillis() >= nextTime) {
                        fetchRecord.time = System.currentTimeMillis()
                        fetchRecord.frequency = 1
                        waitTime = 0
                    }
                    val cs = concurrentRate.substring(0, rateIndex)
                    if (fetchRecord.frequency > cs.toInt()) {
                        waitTime = (nextTime - System.currentTimeMillis()).toInt()
                    } else {
                        fetchRecord.frequency++
                        waitTime = 0
                    }
                }
            } catch (e: Exception) {
                waitTime = 0
            }
            if (waitTime > 0) {
                throw ConcurrentException("根据并发率还需等待${waitTime}毫秒才可以访问", waitTime)
            }
        }
        return fetchRecord
    }

    private fun fetchEnd(concurrentRecord: ConcurrentRecord?) {
        if (concurrentRecord != null && !concurrentRecord.concurrent) {
            synchronized(concurrentRecord) {
                concurrentRecord.frequency--
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
            return StrResponse(url, io.legado.app.utils.StringUtils.byteToHexString(bytes))
        }
        val concurrentRecord = fetchStart()
        setCookie(source?.key)
        val strResponse: StrResponse = if (this.useWebView && useWebView) {
            when (method) {
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
            getProxyClient(proxy, debugLog).newCallStrResponse(retry) {
                addHeaders(headerMap)
                when (method) {
                    RequestMethod.GET -> get(urlNoQuery, fieldMap, true)
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
        if (cookieList.isNotEmpty()) {
            val cookieStore = CookieStore(userNameSpace)
            val domain = io.legado.app.utils.NetworkUtils.getSubDomain(url)
            cookieList.forEach {
                cookieStore.replaceCookie("${domain}_cookieJar", it)
            }
        }
    }

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
                RequestMethod.GET -> get(urlNoQuery, fieldMap, true)
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
                RequestMethod.GET -> get(urlNoQuery, fieldMap, true)
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
            val bodyMap = GSON.fromJsonObject<HashMap<String, Any>>(body).getOrNull()
            bodyMap?.forEach { (k, v) ->
                if (v.toString() == "fileRequest") {
                    bodyMap[k] = mapOf(
                        "fileName" to fileName,
                        "file" to file,
                        "contentType" to contentType
                    )
                }
            }
            postMultipart(type, bodyMap ?: hashMapOf())
        }
    }

    private fun setCookie(tag: String?) {
        val domain = io.legado.app.utils.NetworkUtils.getSubDomain(tag ?: url)
        if (domain.isEmpty()) {
            return
        }
        val cookieStore = CookieStore(userNameSpace)
        if (enabledCookieJar) {
            val key = "${domain}_cookieJar"
            cookieStore.getCookie(key)?.let {
                cookieStore.replaceCookie(domain, it)
                val cookie = cookieStore.getCookie(domain)
                if (cookie.length > 0) {
                    val cookieMap = cookieStore.cookieToMap(cookie)
                    val customCookieMap = cookieStore.cookieToMap(headerMap["Cookie"] ?: "")
                    cookieMap.putAll(customCookieMap)
                    cookieStore.mapToCookie(cookieMap)?.let {
                        headerMap["Cookie"] = it
                    }
                }
            }
        }
    }

    fun getUserAgent(): String {
        return headerMap["User-Agent"] ?: AppConst.userAgent
    }

    fun isPost(): Boolean = method == RequestMethod.POST

    data class UrlOption(
        var method: String? = null,
        var charset: String? = null,
        var headers: String? = null,
        var body: String? = null,
        var retry: String? = null,
        var type: String? = null,
        var webView: Any? = null,
        var webJs: String? = null,
        var js: String? = null
    ) {

        fun useWebView(b: Boolean) {
            webView = if (b) true else null
        }
        fun getHeaderMap(): Map<*, *>? {
            val header = headers ?: return null
            return GSON.fromJsonObject<Map<String, String>>(header).getOrNull()
        }

        fun useWebView(): Boolean {
            return when (val view = webView) {
                null -> false
                is Boolean -> view
                else -> view.toString().toBoolean()
            }
        }
    }

    data class ConcurrentRecord(
        val concurrent: Boolean,
        var time: Long,
        var frequency: Int
    )

    companion object {
        val paramPattern: Pattern = Pattern.compile("\\s*,\\s*(?=\\{)")
        private val pagePattern: Pattern = Pattern.compile("<(.*?)>")
        private val concurrentRecordMap = HashMap<String, ConcurrentRecord>()
    }
}
