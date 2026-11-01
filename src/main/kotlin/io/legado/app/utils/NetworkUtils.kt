// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.utils

import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.net.URL
import java.util.BitSet
import java.util.Enumeration
import java.util.regex.Pattern

object NetworkUtils {

    private val notNeedEncoding: BitSet by lazy {
        BitSet(256).apply {
            for (c in 'a'..'z') set(c.code)
            for (c in 'A'..'Z') set(c.code)
            for (c in '0'..'9') set(c.code)
            for (c in "+-_.\$:()!*@&#,[]") set(c.code)
        }
    }

    private val IPV4_PATTERN: Pattern = Pattern.compile(
        "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}" +
            "([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$"
    )

    fun getUrl(response: retrofit2.Response<*>): String {
        val networkResponse = response.raw().networkResponse
        val url = networkResponse?.request?.url?.toString()
        return url ?: response.raw().request.url.toString()
    }

    fun hasUrlEncoded(str: String): Boolean {
        var needEncode = false
        var i = 0
        while (i < str.length) {
            val c = str[i]
            if (notNeedEncoding.get(c.code)) {
                i++
                continue
            }
            if (c == '%' && i + 2 < str.length) {
                val c1 = str[++i]
                val c2 = str[++i]
                if (isDigit16Char(c1) && isDigit16Char(c2)) {
                    i++
                    continue
                }
            }
            needEncode = true
            break
        }
        return !needEncode
    }

    private fun isDigit16Char(c: Char): Boolean {
        return c in '0'..'9' || c in 'A'..'F' || c in 'a'..'f'
    }

    fun getAbsoluteURL(baseURL: String?, relativePath: String): String {
        if (baseURL.isNullOrEmpty()) {
            return relativePath
        }
        if (relativePath.isEmpty()) {
            return baseURL
        }
        var relativeUrl = relativePath
        try {
            val absoluteUrl = URL(baseURL.substringBefore(","))
            relativeUrl = URL(absoluteUrl, relativePath).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return relativeUrl
    }

    fun getAbsoluteURL(baseURL: URL?, relativePath: String): String {
        if (baseURL == null) {
            return relativePath
        }
        var relativeUrl = relativePath
        try {
            relativeUrl = URL(baseURL, relativePath).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return relativeUrl
    }

    fun getBaseUrl(url: String?): String? {
        if (url == null || !url.startsWith("http")) {
            return null
        }
        val index = url.indexOf("/", 9)
        return if (index == -1) {
            url
        } else {
            url.substring(0, index)
        }
    }

    fun getSubDomain(url: String?): String {
        val baseUrl = getBaseUrl(url) ?: return ""
        return if (baseUrl.indexOf(".") == baseUrl.lastIndexOf(".")) {
            baseUrl.substring(baseUrl.lastIndexOf("/") + 1)
        } else {
            baseUrl.substring(baseUrl.indexOf(".") + 1)
        }
    }

    fun getLocalIPAddress(): InetAddress? {
        var enumeration: Enumeration<NetworkInterface>? = null
        try {
            enumeration = NetworkInterface.getNetworkInterfaces()
        } catch (e: SocketException) {
            e.printStackTrace()
        }
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                val nif = enumeration.nextElement()
                val addresses = nif.inetAddresses
                while (addresses.hasMoreElements()) {
                    val address = addresses.nextElement()
                    if (address.isLoopbackAddress) continue
                    if (!isIPv4Address(address.hostAddress)) continue
                    return address
                }
            }
        }
        return null
    }

    fun isIPv4Address(input: String): Boolean {
        return IPV4_PATTERN.matcher(input).matches()
    }
}
