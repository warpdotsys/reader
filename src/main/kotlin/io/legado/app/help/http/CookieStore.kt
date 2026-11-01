// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.help.http

import io.legado.app.adapters.ReaderAdapterHelper
import io.legado.app.help.http.api.CookieManager
import io.legado.app.utils.ACache
import io.legado.app.utils.NetworkUtils
import io.legado.app.utils.TextUtils
import java.io.File

class CookieStore(private val userNameSpace: String) : CookieManager {

    val cacheInstance: ACache

    init {
        val cacheDir =
            File(ReaderAdapterHelper.getAdapter().getWorkDir("storage", "cache", "cookie", userNameSpace))
        cacheInstance = ACache.get(cacheDir, 50000000L, 1000000)
    }

    fun getUserNameSpace(): String {
        return userNameSpace
    }

    override fun setCookie(url: String, cookie: String?) {
        val domain = NetworkUtils.getSubDomain(url)
        if (domain.isNotEmpty()) {
            cacheInstance.put(domain, cookie ?: "")
        }
    }

    override fun replaceCookie(url: String, cookie: String) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(cookie)) {
            return
        }
        val oldCookie = getCookie(url)
        if (TextUtils.isEmpty(oldCookie)) {
            setCookie(url, cookie)
        } else {
            val cookieMap = cookieToMap(oldCookie)
            cookieMap.putAll(cookieToMap(cookie))
            val newCookie = mapToCookie(cookieMap)
            setCookie(url, newCookie)
        }
    }

    override fun getCookie(url: String): String {
        val domain = NetworkUtils.getSubDomain(url)
        if (domain.isEmpty()) {
            return ""
        }
        return cacheInstance.getAsString(domain) ?: ""
    }

    fun getKey(url: String, key: String): String {
        val cookie = getCookie(url)
        val cookieMap = cookieToMap(cookie)
        return cookieMap[key] ?: ""
    }

    override fun removeCookie(url: String) {
        val domain = NetworkUtils.getSubDomain(url)
        if (domain.isEmpty()) {
            return
        }
        cacheInstance.remove(domain)
    }

    override fun cookieToMap(cookie: String): MutableMap<String, String> {
        val cookieMap = LinkedHashMap<String, String>()
        if (cookie.isBlank()) {
            return cookieMap
        }
        cookie.split(Regex(";")).dropLastWhile { it.isEmpty() }.toTypedArray().forEach { pair ->
            val pairs = pair.split(Regex("=")).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (pairs.size == 1) {
                return@forEach
            }
            val key = pairs[0].trim()
            val value = pairs[1]
            if (value.isBlank() || value.trim() == "null") {
                return@forEach
            }
            cookieMap[key] = value.trim()
        }
        return cookieMap
    }

    override fun mapToCookie(cookieMap: Map<String, String>?): String? {
        if (cookieMap == null || cookieMap.isEmpty()) {
            return null
        }
        val builder = StringBuilder()
        for (key in cookieMap.keys) {
            val value = cookieMap[key]
            if (value != null && !value.isBlank()) {
                builder.append(key).append("=").append(value).append(";")
            }
        }
        return builder.deleteCharAt(builder.lastIndexOf(";")).toString()
    }

    fun clear() {
        cacheInstance.clear()
    }
}
