// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.help

import io.legado.app.constant.AppConst
import io.legado.app.data.entities.BaseSource
import io.legado.app.help.http.CookieStore
import io.legado.app.help.http.StrResponse
import io.legado.app.help.http.newCall as newCallExt
import io.legado.app.help.http.okHttpClient
import io.legado.app.help.http.text
import io.legado.app.model.Debug
import io.legado.app.model.DebugLog
import io.legado.app.model.analyzeRule.AnalyzeUrl
import io.legado.app.model.analyzeRule.QueryTTF
import io.legado.app.utils.EncoderUtils
import io.legado.app.utils.EncodingDetect
import io.legado.app.utils.FileUtils
import io.legado.app.utils.HtmlFormatter
import io.legado.app.utils.MD5Utils
import io.legado.app.utils.NetworkUtils
import io.legado.app.utils.StringUtils
import io.legado.app.utils.ZipUtils
import io.legado.app.utils.isAbsUrl
import io.legado.app.utils.msg
import io.legado.app.utils.printOnDebug
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.jsoup.Connection
import org.jsoup.Jsoup
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URLEncoder
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.SimpleTimeZone
import java.util.UUID
import java.util.zip.ZipInputStream
import io.legado.app.utils.GSON
import io.legado.app.utils.fromJsonObject

interface JsExtensions {

    val source: BaseSource?

    val userNameSpace: String

    val logger: DebugLog?

    fun ajax(urlStr: String): String? = runBlocking {
        try {
            val analyzeUrl = AnalyzeUrl(urlStr, source = source, debugLog = logger)
            analyzeUrl.getStrResponse(urlStr).body
        } catch (e: Exception) {
            e.printOnDebug()
            e.msg
        }
    }

    fun ajaxAll(urlList: Array<String>): Array<StrResponse?> = runBlocking {
        val asyncArray = Array(urlList.size) { index ->
            async(Dispatchers.IO) {
                val url = urlList[index]
                val analyzeUrl = AnalyzeUrl(url, source = source, debugLog = logger)
                analyzeUrl.getStrResponse(url)
            }
        }
        val strResponses = arrayOfNulls<StrResponse>(asyncArray.size)
        asyncArray.forEachIndexed { index, deferred ->
            strResponses[index] = deferred.await()
        }
        strResponses
    }

    fun connect(urlStr: String): StrResponse = runBlocking {
        val analyzeUrl = AnalyzeUrl(urlStr, source = source, debugLog = logger)
        analyzeUrl.getStrResponse(urlStr)
    }

    fun connect(urlStr: String, header: String?): StrResponse = runBlocking {
        val headerMap = GSON.fromJsonObject<Map<String, String>>(header).getOrNull()
        val analyzeUrl = AnalyzeUrl(
            urlStr,
            source = source,
            headerMapF = headerMap,
            debugLog = logger
        )
        runCatching {
            analyzeUrl.getStrResponseAwait()
        }.onFailure {
            it.printOnDebug()
        }.getOrElse {
            StrResponse(analyzeUrl.url, it.localizedMessage)
        }
    }

    fun webView(html: String?, url: String?, js: String?): String? {
        return null
    }

    fun importScript(path: String): String {
        val result = if (path.startsWith("http")) {
            cacheFile(path) ?: ""
        } else {
            if (path.startsWith("/storage")) {
                FileUtils.readText(path)
            } else {
                readTxtFile(path)
            }
        }
        if (result.isBlank()) {
            throw Exception("$path 内容获取失败或者为空")
        }
        return result
    }

    fun cacheFile(urlStr: String): String? {
        return cacheFile(urlStr, 0)
    }

    fun cacheFile(urlStr: String, saveTime: Int = 0): String? {
        val key = md5Encode16(urlStr)
        val cacheInstance = CacheManager(userNameSpace)
        val cache = cacheInstance.getFile(key)
        if (cache.isNullOrBlank()) {
            log("首次下载 $urlStr")
            val value = ajax(urlStr) ?: return null
            cacheInstance.putFile(key, value, saveTime)
            return value
        }
        return cache
    }

    fun getCookie(tag: String, key: String? = null): String {
        val cookieStore = CookieStore(userNameSpace)
        val cookie = cookieStore.getCookie(tag)
        if (key != null) {
            val cookieMap = cookieStore.cookieToMap(cookie)
            return cookieMap[key] ?: ""
        }
        return cookie
    }

    fun downloadFile(content: String, url: String): String {
        val type = AnalyzeUrl(url).type ?: return ""
        val zipPath = FileUtils.getPath(
            FileUtils.createFolderIfNotExist(FileUtils.getCachePath()),
            md5Encode16(url) + '.' + type
        )
        FileUtils.deleteFile(zipPath)
        val zipFile = FileUtils.createFileIfNotExist(zipPath)
        val it = StringUtils.hexStringToByte(content)
        if (it.isNotEmpty()) {
            zipFile.writeBytes(it)
        }
        return zipPath.substring(FileUtils.getCachePath().length)
    }

    fun get(urlStr: String, headers: Map<String, String>): Connection.Response {
        val response = Jsoup.connect(urlStr)
            .sslSocketFactory(io.legado.app.help.http.SSLHelper.unsafeSSLSocketFactory)
            .ignoreContentType(true)
            .followRedirects(false)
            .headers(headers)
            .method(Connection.Method.GET)
            .execute()
        val cookies = response.cookies()
        val cookieStore = CookieStore(userNameSpace)
        val cookie = cookieStore.mapToCookie(cookies)
        if (cookie != null) {
            val domain = NetworkUtils.getSubDomain(urlStr)
            cookieStore.replaceCookie("${domain}_cookieJar", cookie)
        }
        return response
    }


    fun head(urlStr: String, headers: Map<String, String>): Connection.Response {
        val response = Jsoup.connect(urlStr)
            .sslSocketFactory(io.legado.app.help.http.SSLHelper.unsafeSSLSocketFactory)
            .ignoreContentType(true)
            .followRedirects(false)
            .headers(headers)
            .method(Connection.Method.HEAD)
            .execute()
        val cookies = response.cookies()
        val cookieStore = CookieStore(userNameSpace)
        val cookie = cookieStore.mapToCookie(cookies)
        if (cookie != null) {
            val domain = NetworkUtils.getSubDomain(urlStr)
            cookieStore.replaceCookie("${domain}_cookieJar", cookie)
        }
        return response
    }


    fun post(urlStr: String, body: String, headers: Map<String, String>): Connection.Response {
        val response = Jsoup.connect(urlStr)
            .sslSocketFactory(io.legado.app.help.http.SSLHelper.unsafeSSLSocketFactory)
            .ignoreContentType(true)
            .followRedirects(false)
            .requestBody(body)
            .headers(headers)
            .method(Connection.Method.POST)
            .execute()
        val cookies = response.cookies()
        val cookieStore = CookieStore(userNameSpace)
        val cookie = cookieStore.mapToCookie(cookies)
        if (cookie != null) {
            val domain = NetworkUtils.getSubDomain(urlStr)
            cookieStore.replaceCookie("${domain}_cookieJar", cookie)
        }
        return response
    }



    fun base64Decode(str: String): String {
        return EncoderUtils.base64Decode(str, 2)
    }

    fun base64Decode(str: String, flags: Int): String {
        return EncoderUtils.base64Decode(str, flags)
    }

    fun base64DecodeToByteArray(str: String?): ByteArray? {
        return base64DecodeToByteArray(str, 2)
    }

    fun base64DecodeToByteArray(str: String?, flags: Int): ByteArray? {
        if (str == null) return null
        return io.legado.app.utils.Base64.decode(str, flags)
    }

    fun base64Encode(str: String): String {
        return EncoderUtils.base64Encode(str, 2)
    }

    fun base64Encode(str: String, flags: Int): String {
        return EncoderUtils.base64Encode(str, flags)
    }

    fun md5Encode(str: String): String {
        return MD5Utils.md5Encode(str)
    }

    fun md5Encode16(str: String): String {
        return MD5Utils.md5Encode16(str)
    }

    fun timeFormatUTC(time: Long, format: String, sh: Int): String {
        val utc = SimpleTimeZone(sh, "UTC")
        val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        simpleDateFormat.timeZone = utc
        return simpleDateFormat.format(Date(time))
    }

    fun timeFormat(time: Long): String {
        return AppConst.dateFormat.format(Date(time))
    }

    fun utf8ToGbk(str: String): String {
        val utf8 = String(str.toByteArray(Charsets.UTF_8), Charsets.UTF_8)
        val unicode = String(utf8.toByteArray(Charset.forName("UTF-8")), Charset.forName("UTF-8"))
        val gbk = unicode.toByteArray(Charset.forName("GBK"))
        return String(gbk, Charsets.UTF_8)
    }

    fun encodeURI(str: String): String {
        return try {
            URLEncoder.encode(str, "UTF-8")
        } catch (e: Exception) {
            ""
        }
    }

    fun encodeURI(str: String, enc: String): String {
        return try {
            URLEncoder.encode(str, enc)
        } catch (e: Exception) {
            ""
        }
    }

    fun htmlFormat(str: String): String {
        return HtmlFormatter.formatKeepImg(str)
    }

    fun getFile(path: String): File {
        val cachePath = io.legado.app.adapters.ReaderAdapterHelper.getAdapter().getCacheDir()
        val aPath = if (path.startsWith(File.separator)) {
            cachePath + path
        } else {
            cachePath + File.separator + path
        }
        return File(aPath)
    }

    fun readFile(path: String): ByteArray? {
        val file = getFile(path)
        if (file.exists()) {
            return file.readBytes()
        }
        return null
    }

    fun readTxtFile(path: String): String {
        val file = getFile(path)
        if (file.exists()) {
            val charsetName = EncodingDetect.getEncode(file)
            val byArray = file.readBytes()
            return String(byArray, Charset.forName(charsetName))
        }
        return ""
    }

    fun readTxtFile(path: String, charsetName: String): String {
        val file = getFile(path)
        if (file.exists()) {
            val byArray = file.readBytes()
            return String(byArray, Charset.forName(charsetName))
        }
        return ""
    }

    fun deleteFile(path: String) {
        FileUtils.deleteFile(path)
    }

    fun unzipFile(zipPath: String): String {
        if (zipPath.isEmpty()) {
            return ""
        }
        val unzipPath = FileUtils.getPath(
            FileUtils.createFolderIfNotExist(FileUtils.getCachePath()),
            FileUtils.getNameExcludeExtension(zipPath)
        )
        FileUtils.deleteFile(unzipPath)
        val zipFile = getFile(zipPath)
        val unzipFolder = FileUtils.createFolderIfNotExist(unzipPath)
        ZipUtils.unzipFile(zipFile, unzipFolder)
        FileUtils.deleteFile(zipFile.absolutePath)
        return unzipPath.substring(FileUtils.getCachePath().length)
    }

    fun getTxtInFolder(unzipPath: String): String {
        if (unzipPath.isEmpty()) {
            return ""
        }
        val unzipFolder = getFile(unzipPath)
        val contents = StringBuilder()
        unzipFolder.listFiles()?.forEach { f ->
            val charsetName = EncodingDetect.getEncode(f)
            val byArray = f.readBytes()
            contents.append(String(byArray, Charset.forName(charsetName))).append("\n")
        }
        contents.deleteCharAt(contents.length - 1)
        FileUtils.deleteFile(unzipFolder.absolutePath)
        return contents.toString()
    }

    fun getZipStringContent(url: String, path: String): String {
        val byteArray = getZipByteArrayContent(url, path) ?: return ""
        val charsetName = EncodingDetect.getEncode(byteArray)
        return String(byteArray, Charset.forName(charsetName))
    }

    fun getZipStringContent(url: String, path: String, charsetName: String): String {
        val byteArray = getZipByteArrayContent(url, path) ?: return ""
        return String(byteArray, Charset.forName(charsetName))
    }

    fun getZipByteArrayContent(url: String, path: String): ByteArray? {
        val bytes = if (url.startsWith("http://") || url.startsWith("https://")) {
            runBlocking {
                okHttpClient.newCallExt {
                    url(url)
                }.bytes()
            }
        } else {
            StringUtils.hexStringToByte(url)
        }
        val bos = ByteArrayOutputStream()
        val zis = ZipInputStream(ByteArrayInputStream(bytes))
        var entry = zis.nextEntry
        while (entry != null) {
            if (entry.name == path) {
                zis.copyTo(bos)
                return bos.toByteArray()
            }
            entry = zis.nextEntry
        }
        Debug.log("getZipContent 未发现内容")
        return null
    }

    fun queryBase64TTF(base64: String?): QueryTTF? {
        val bytes = base64DecodeToByteArray(base64) ?: return null
        return QueryTTF(bytes)
    }

    fun queryTTF(str: String?): QueryTTF? {
        if (str == null) {
            return null
        }
        val key = md5Encode16(str)
        val cacheInstance = CacheManager(userNameSpace)
        cacheInstance.getQueryTTF(key)?.let { return it }
        val font = if (str.isAbsUrl()) {
            runBlocking {
                var x = cacheInstance.getByteArray(key)
                if (x == null) {
                    x = okHttpClient.newCallExt {
                        url(str)
                    }.bytes()
                    cacheInstance.put(key, x)
                }
                x
            }
        } else {
            if (str.indexOf("storage/") > 0) {
                File(str).readBytes()
            } else {
                base64DecodeToByteArray(str)
            }
        }
        if (font == null) {
            return null
        }
        val qTTF = QueryTTF(font)
        cacheInstance.put(key, qTTF)
        return qTTF
    }

    fun replaceFont(text: String, font1: QueryTTF?, font2: QueryTTF?): String {
        if (font1 == null || font2 == null) {
            return text
        }
        val contentArray = text.toCharArray()
        contentArray.forEachIndexed { index, c ->
            if (font1.inLimit(c)) {
                val code = font2.getCodeByGlyf(font1.getGlyfByCode(c.code))
                if (code != 0) {
                    contentArray[index] = code.toChar()
                }
            }
        }
        return contentArray.joinToString("")
    }

    fun toast(msg: Any?) {
        logger?.log("toast: $msg")
        Debug.log("toast: $msg")
    }

    fun longToast(msg: Any?) {
        logger?.log("longToast: $msg")
        Debug.log("longToast: $msg")
    }

    fun log(msg: String): String {
        logger?.log(msg)
        Debug.log(msg)
        return msg
    }

    fun logType(any: Any?) {
        if (any == null) {
            log("null")
        } else {
            log(any.javaClass.name)
        }
    }

    fun randomUUID(): String {
        return UUID.randomUUID().toString()
    }

    fun aesDecodeToByteArray(
        str: String,
        key: String,
        transformation: String,
        iv: String
    ): ByteArray? {
        return EncoderUtils.decryptAES(
            str.encodeToByteArray(),
            key.encodeToByteArray(),
            transformation,
            iv.encodeToByteArray()
        )
    }

    fun aesDecodeToString(
        str: String,
        key: String,
        transformation: String,
        iv: String
    ): String? {
        return aesDecodeToByteArray(str, key, transformation, iv)?.toString(Charsets.UTF_8)
    }

    fun aesBase64DecodeToByteArray(
        str: String,
        key: String,
        transformation: String,
        iv: String
    ): ByteArray? {
        return EncoderUtils.decryptBase64AES(
            str.encodeToByteArray(),
            key.encodeToByteArray(),
            transformation,
            iv.encodeToByteArray()
        )
    }

    fun aesBase64DecodeToString(
        str: String,
        key: String,
        transformation: String,
        iv: String
    ): String? {
        return aesBase64DecodeToByteArray(str, key, transformation, iv)?.toString(Charsets.UTF_8)
    }

    fun aesEncodeToByteArray(
        data: String,
        key: String,
        transformation: String,
        iv: String
    ): ByteArray? {
        return EncoderUtils.encryptAES(
            data.encodeToByteArray(),
            key.encodeToByteArray(),
            transformation,
            iv.encodeToByteArray()
        )
    }

    fun aesEncodeToString(
        data: String,
        key: String,
        transformation: String,
        iv: String
    ): String? {
        return aesEncodeToByteArray(data, key, transformation, iv)?.toString(Charsets.UTF_8)
    }

    fun aesEncodeToBase64ByteArray(
        data: String,
        key: String,
        transformation: String,
        iv: String
    ): ByteArray? {
        return EncoderUtils.encryptAES2Base64(
            data.encodeToByteArray(),
            key.encodeToByteArray(),
            transformation,
            iv.encodeToByteArray()
        )
    }

    fun aesEncodeToBase64String(
        data: String,
        key: String,
        transformation: String,
        iv: String
    ): String? {
        return aesEncodeToBase64ByteArray(data, key, transformation, iv)?.toString(Charsets.UTF_8)
    }

    fun androidId(): String {
        return ""
    }

    fun aesDecodeArgsBase64Str(
        data: String,
        key: String,
        mode: String,
        padding: String,
        iv: String
    ): String {
        return EncoderUtils.decryptAES(
            data.encodeToByteArray(),
            io.legado.app.utils.Base64.decode(key, 2),
            "${mode}/${padding}",
            io.legado.app.utils.Base64.decode(iv, 2)
        )?.toString(Charsets.UTF_8) ?: ""
    }

    fun tripleDESDecodeStr(
        data: String,
        key: String,
        mode: String,
        padding: String,
        iv: String
    ): String {
        return EncoderUtils.decryptDES(
            data.encodeToByteArray(),
            key.encodeToByteArray(),
            "${mode}/${padding}",
            iv.encodeToByteArray()
        )?.toString(Charsets.UTF_8) ?: ""
    }

    fun tripleDESDecodeArgsBase64Str(
        data: String,
        key: String,
        mode: String,
        padding: String,
        iv: String
    ): String {
        return EncoderUtils.decryptBase64DES(
            data.encodeToByteArray(),
            io.legado.app.utils.Base64.decode(key, 2),
            "${mode}/${padding}",
            io.legado.app.utils.Base64.decode(iv, 2)
        )?.toString(Charsets.UTF_8) ?: ""
    }

    fun aesEncodeArgsBase64Str(
        data: String,
        key: String,
        mode: String,
        padding: String,
        iv: String
    ): String {
        return EncoderUtils.encryptAES2Base64(
            data.encodeToByteArray(),
            io.legado.app.utils.Base64.decode(key, 2),
            "${mode}/${padding}",
            io.legado.app.utils.Base64.decode(iv, 2)
        )?.toString(Charsets.UTF_8) ?: ""
    }

    fun desDecodeToString(
        data: String,
        key: String,
        transformation: String,
        iv: String
    ): String? {
        return EncoderUtils.decryptDES(
            data.encodeToByteArray(),
            key.encodeToByteArray(),
            transformation,
            iv.encodeToByteArray()
        )?.toString(Charsets.UTF_8)
    }

    fun desBase64DecodeToString(
        data: String,
        key: String,
        transformation: String,
        iv: String
    ): String? {
        return EncoderUtils.decryptBase64DES(
            data.encodeToByteArray(),
            key.encodeToByteArray(),
            transformation,
            iv.encodeToByteArray()
        )?.toString(Charsets.UTF_8)
    }

    fun desEncodeToString(
        data: String,
        key: String,
        transformation: String,
        iv: String
    ): String? {
        return EncoderUtils.encryptDES(
            data.encodeToByteArray(),
            key.encodeToByteArray(),
            transformation,
            iv.encodeToByteArray()
        )?.toString(Charsets.UTF_8)
    }

    fun desEncodeToBase64String(
        data: String,
        key: String,
        transformation: String,
        iv: String
    ): String? {
        return EncoderUtils.encryptDES2Base64(
            data.encodeToByteArray(),
            key.encodeToByteArray(),
            transformation,
            iv.encodeToByteArray()
        )?.toString(Charsets.UTF_8)
    }

    fun tripleDESEncodeBase64Str(
        data: String,
        key: String,
        mode: String,
        padding: String,
        iv: String
    ): String {
        return EncoderUtils.encryptDES2Base64(
            data.encodeToByteArray(),
            key.encodeToByteArray(),
            "${mode}/${padding}",
            iv.encodeToByteArray()
        )?.toString(Charsets.UTF_8) ?: ""
    }

    fun tripleDESEncodeArgsBase64Str(
        data: String,
        key: String,
        mode: String,
        padding: String,
        iv: String
    ): String {
        return EncoderUtils.encryptDES2Base64(
            data.encodeToByteArray(),
            io.legado.app.utils.Base64.decode(key, 2),
            "${mode}/${padding}",
            io.legado.app.utils.Base64.decode(iv, 2)
        )?.toString(Charsets.UTF_8) ?: ""
    }

    fun digestHex(data: String, algorithm: String): String {
        return cn.hutool.crypto.digest.DigestUtil.digester(algorithm).digestHex(data)
    }

    fun digestBase64Str(data: String, algorithm: String): String {
        return io.legado.app.utils.Base64.encodeToString(cn.hutool.crypto.digest.DigestUtil.digester(algorithm).digest(data), 2)
    }
}
