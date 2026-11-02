// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.data.entities

import io.legado.app.constant.AppConst
import io.legado.app.help.CacheManager
import io.legado.app.help.JsExtensions
import io.legado.app.help.http.CookieStore
import io.legado.app.utils.Base64
import io.legado.app.utils.EncoderUtils
import io.legado.app.utils.GSON
import io.legado.app.utils.fromJsonObject
import java.lang.Exception
import com.script.SimpleBindings

interface BaseSource : JsExtensions {

    var concurrentRate: String?
    var loginUrl: String?
    var loginUi: String?
    var header: String?
    var enabledCookieJar: Boolean?
    val tag: String

    val key: String

    override val source: BaseSource
        get() = this

    val loginJs: String?
        get() {
            val loginJs = loginUrl ?: return null
            return when {
                loginJs.startsWith("@js:") -> loginJs.substring(4)
                loginJs.startsWith("<js>") -> loginJs.substring(4, loginJs.lastIndexOf("<"))
                else -> loginJs
            }
        }

    fun login() {
        loginJs?.let { evalJS(it) }
    }

    fun getHeaderMap(hasLoginHeader: Boolean = false): HashMap<String, String> {
        val map = HashMap<String, String>()
        map["User-Agent"] = AppConst.userAgent
        header?.let { header ->
            val headerStr = when {
                header.startsWith("@js:", true) -> evalJS(header.substring(4)).toString()
                header.startsWith("<js>", true) -> evalJS(header.substring(4, header.lastIndexOf("<"))).toString()
                else -> header
            }
            GSON.fromJsonObject<Map<String, String>>(headerStr).getOrNull()?.let { map.putAll(it) }
        }
        if (hasLoginHeader) {
            loginHeaderMap?.let { map.putAll(it) }
        }
        return map
    }

    val loginHeader: String?
        get() = CacheManager(userNameSpace).get("loginHeader_$key")

    val loginHeaderMap: Map<String, String>?
        get() = GSON.fromJsonObject<Map<String, String>>(loginHeader).getOrNull()

    fun putLoginHeader(header: String) {
        CacheManager(userNameSpace).put("loginHeader_$key", header)
    }

    fun removeLoginHeader() {
        CacheManager(userNameSpace).delete("loginHeader_$key")
    }

    val loginInfo: String?
        get() {
            return try {
            val key = AppConst.userAgent.encodeToByteArray()
            val cache = CacheManager(userNameSpace).get("userInfo_$key") ?: return null
            val encodeBytes = EncoderUtils.base64Decode(cache, 0).toByteArray(Charsets.UTF_8)
            val decodeBytes = EncoderUtils.decryptAES(encodeBytes, key) ?: return null
            String(decodeBytes, Charsets.UTF_8)
            } catch (e: Exception) {
                log("获取登陆信息出错 ${e.localizedMessage}")
                null
            }
        }

    val loginInfoMap: Map<String, String>?
        get() = GSON.fromJsonObject<Map<String, String>>(loginInfo).getOrNull()

    fun putLoginInfo(info: String): Boolean {
        return try {
            val key = AppConst.userAgent.encodeToByteArray()
            val encodeBytes = EncoderUtils.encryptAES(info.toByteArray(Charsets.UTF_8), key)!!
            val encodeStr = Base64.encodeToString(encodeBytes, 0)
            CacheManager(userNameSpace).put("userInfo_$key", encodeStr)
            true
        } catch (e: Exception) {
            log("保存登陆信息出错 ${e.localizedMessage}")
            false
        }
    }

    fun removeLoginInfo() {
        CacheManager(userNameSpace).delete("userInfo_$key")
    }

    var variable: String?
        get() = CacheManager(userNameSpace).get("sourceVariable_$key")
        set(variable) {
            val cache = CacheManager(userNameSpace)
            if (variable != null) {
                cache.put("sourceVariable_$key", variable)
            } else {
                cache.delete("sourceVariable_$key")
            }
        }

    @Throws(Exception::class)
    fun evalJS(jsStr: String, bindingsConfig: (SimpleBindings) -> Unit = {}): Any? {
        val bindings = SimpleBindings()
        bindingsConfig(bindings)
        bindings["java"] = this
        bindings["source"] = this
        bindings["baseUrl"] = key
        bindings["cookie"] = CookieStore(userNameSpace)
        bindings["cache"] = CacheManager(userNameSpace)
        return AppConst.SCRIPT_ENGINE.eval(jsStr, bindings)
    }
}
