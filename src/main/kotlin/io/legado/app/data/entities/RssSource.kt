// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.data.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import io.legado.app.model.DebugLog
import kotlin.jvm.Transient
import io.legado.app.utils.jsonPath
import io.legado.app.utils.readBool
import io.legado.app.utils.readInt
import io.legado.app.utils.readString

@JsonIgnoreProperties(
    value = [
        "headerMap", "source", "_userNameSpace", "userNameSpace",
        "loginHeader", "loginHeaderMap", "loginInfo", "loginInfoMap"
    ]
)
data class RssSource(
    var sourceUrl: String = "",
    var sourceName: String = "",
    var sourceIcon: String = "",
    var sourceGroup: String? = null,
    var sourceComment: String? = null,
    var enabled: Boolean = true,
    var variableComment: String? = null,
    override var enabledCookieJar: Boolean? = false,
    override var concurrentRate: String? = null,
    override var header: String? = null,
    override var loginUrl: String? = null,
    override var loginUi: String? = null,
    var loginCheckJs: String? = null,
    var sortUrl: String? = null,
    var singleUrl: Boolean = false,
    var articleStyle: Int = 0,
    var ruleArticles: String? = null,
    var ruleNextPage: String? = null,
    var ruleTitle: String? = null,
    var rulePubDate: String? = null,
    var ruleDescription: String? = null,
    var ruleImage: String? = null,
    var ruleLink: String? = null,
    var ruleContent: String? = null,
    var style: String? = null,
    var enableJs: Boolean = true,
    var loadWithBaseUrl: Boolean = true,
    var customOrder: Int = 0
) : BaseSource {

    @Transient
    private var _userNameSpace: String = ""

    @Transient
    private var debugLog: DebugLog? = null

    override val tag: String
        get() = sourceName

    override val key: String
        get() = sourceUrl

    override fun equals(other: Any?): Boolean {
        if (other is RssSource) {
            return other.sourceUrl == sourceUrl
        }
        return false
    }

    override fun hashCode(): Int {
        return sourceUrl.hashCode()
    }

    fun equal(source: RssSource): Boolean {
        return equal(sourceUrl, source.sourceUrl) &&
                equal(sourceIcon, source.sourceIcon) &&
                enabled == source.enabled &&
                enabledCookieJar == source.enabledCookieJar &&
                equal(sourceComment, source.sourceComment) &&
                equal(sourceGroup, source.sourceGroup) &&
                equal(ruleArticles, source.ruleArticles) &&
                equal(ruleNextPage, source.ruleNextPage) &&
                equal(ruleTitle, source.ruleTitle) &&
                equal(rulePubDate, source.rulePubDate) &&
                equal(ruleDescription, source.ruleDescription) &&
                equal(ruleLink, source.ruleLink) &&
                equal(ruleContent, source.ruleContent) &&
                enableJs == source.enableJs &&
                loadWithBaseUrl == source.loadWithBaseUrl
    }

    private fun equal(a: String?, b: String?): Boolean {
        return a == b || (a.isNullOrEmpty() && b.isNullOrEmpty())
    }

    fun sortUrls(): List<Pair<String, String>> {
        val urls = arrayListOf<Pair<String, String>>()
        runCatching {
            var a = sortUrl
            if (sortUrl?.startsWith("<js>") == true || sortUrl?.startsWith("@js:") == true) {
                val jsStr = if (sortUrl!!.startsWith("@")) {
                    sortUrl!!.substring(4)
                } else {
                    sortUrl!!.substring(4, sortUrl!!.lastIndexOf("<"))
                }
                a = evalJS(jsStr).toString()
            }
            a?.split(Regex("(&&|\n)+"))?.forEach { c ->
                val d = c.split("::")
                if (d.size > 1) {
                    urls.add(Pair(d[0], d[1]))
                }
            }
            if (urls.isEmpty()) {
                urls.add(Pair("", sourceUrl))
            }
        }
        return urls
    }

    override val userNameSpace: String
        get() = _userNameSpace

    fun setUserNameSpace(nameSpace: String) {
        _userNameSpace = nameSpace
    }

    override val logger: DebugLog?
        get() = null

    fun setLogger(logger: DebugLog?) {
        debugLog = logger
    }

    companion object {

        fun fromJsonDoc(doc: DocumentContext): Result<RssSource> {
            return runCatching {
                RssSource(
                    sourceUrl = doc.readString("$.sourceUrl")!!,
                    sourceName = doc.readString("$.sourceName")!!,
                    sourceIcon = doc.readString("$.sourceIcon") ?: "",
                    sourceGroup = doc.readString("$.sourceGroup"),
                    sourceComment = doc.readString("$.sourceComment"),
                    enabled = doc.readBool("$.enabled") ?: true,
                    enabledCookieJar = doc.readBool("$.enabledCookieJar") ?: false,
                    concurrentRate = doc.readString("$.concurrentRate"),
                    header = doc.readString("$.header"),
                    loginUrl = doc.readString("$.loginUrl"),
                    loginCheckJs = doc.readString("$.loginCheckJs"),
                    sortUrl = doc.readString("$.sortUrl"),
                    singleUrl = doc.readBool("$.singleUrl") ?: false,
                    articleStyle = doc.readInt("$.articleStyle") ?: 0,
                    ruleArticles = doc.readString("$.ruleArticles"),
                    ruleNextPage = doc.readString("$.ruleNextPage"),
                    ruleTitle = doc.readString("$.ruleTitle"),
                    rulePubDate = doc.readString("$.rulePubDate"),
                    ruleDescription = doc.readString("$.ruleDescription"),
                    ruleImage = doc.readString("$.ruleImage"),
                    ruleLink = doc.readString("$.ruleLink"),
                    ruleContent = doc.readString("$.ruleContent"),
                    style = doc.readString("$.style"),
                    enableJs = doc.readBool("$.enableJs") ?: true,
                    loadWithBaseUrl = doc.readBool("$.loadWithBaseUrl") ?: true,
                    customOrder = doc.readInt("$.customOrder") ?: 0
                )
            }
        }

        fun fromJson(json: String): Result<RssSource> {
            return fromJsonDoc(jsonPath.parse(json))
        }

        fun fromJsonArray(jsonArray: String): Result<List<RssSource>> {
            return runCatching {
                val list = arrayListOf<RssSource>()
                (jsonPath.parse(jsonArray).read<Any>("$") as List<Map<String, Any>>).forEach {
                    list.add(fromJsonDoc(jsonPath.parse(it)).getOrThrow())
                }
                list
            }
        }
    }
}
