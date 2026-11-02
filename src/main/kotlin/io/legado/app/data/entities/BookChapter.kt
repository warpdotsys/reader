// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.data.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.legado.app.model.analyzeRule.AnalyzeUrl
import io.legado.app.model.analyzeRule.RuleDataInterface
import io.legado.app.utils.GSON
import io.legado.app.utils.MD5Utils
import io.legado.app.utils.NetworkUtils
import io.legado.app.utils.fromJsonObject

/**
 * 目录里的一个章节。url 是唯一标识，equals/hashCode 只认 url。
 * 章节缓存文件名由 [getFileName] 给出，绝对化地址由 [getAbsoluteURL] 给出。
 */
@JsonIgnoreProperties(value = ["variableMap", "_userNameSpace", "userNameSpace"])
data class BookChapter(
    var url: String = "",
    var title: String = "",
    var isVolume: Boolean = false,
    var baseUrl: String = "",
    var bookUrl: String = "",
    var index: Int = 0,
    var resourceUrl: String? = null,
    var tag: String? = null,
    var start: Long? = null,
    var end: Long? = null,
    var startFragmentId: String? = null,
    var endFragmentId: String? = null,
    var variable: String? = null
) : RuleDataInterface {

    @delegate:Transient
    override val variableMap: HashMap<String, String> by lazy {
        GSON.fromJsonObject<HashMap<String, String>>(variable).getOrNull() ?: hashMapOf()
    }

    override fun putVariable(key: String, value: String?) {
        if (value != null) {
            variableMap[key] = value
        } else {
            variableMap.remove(key)
        }
        variable = GSON.toJson(variableMap)
    }

    @Transient
    private var _userNameSpace: String = ""

    fun setUserNameSpace(nameSpace: String) {
        _userNameSpace = nameSpace
    }

    override fun getUserNameSpace(): String {
        return _userNameSpace
    }

    override fun hashCode(): Int {
        return url.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other is BookChapter) {
            return other.url == url
        }
        return false
    }

    /**
     * 章节 url 后面可能带 `,{...}` 形式的请求参数，绝对化时只把 url 部分拼到 baseUrl 上，
     * 参数原样接在后面。
     */
    fun getAbsoluteURL(): String {
        val urlMatcher = AnalyzeUrl.paramPattern.matcher(url)
        val urlBefore = if (urlMatcher.find()) {
            url.substring(0, urlMatcher.start())
        } else {
            url
        }
        val urlAbsoluteBefore = NetworkUtils.getAbsoluteURL(baseUrl, urlBefore)
        return if (urlBefore.length == url.length) {
            urlAbsoluteBefore
        } else {
            "$urlAbsoluteBefore,${url.substring(urlMatcher.end())}"
        }
    }

    /**
     * 章节缓存文件名：序号 + 标题的 16 位 MD5。
     */
    fun getFileName(): String {
        return String.format("%05d-%s.nb", index, MD5Utils.md5Encode16(title))
    }
}
