// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.data.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.legado.app.data.entities.rule.BookInfoRule
import io.legado.app.data.entities.rule.ContentRule
import io.legado.app.data.entities.rule.ExploreRule
import io.legado.app.data.entities.rule.SearchRule
import io.legado.app.data.entities.rule.TocRule
import io.legado.app.help.SourceAnalyzer
import io.legado.app.model.DebugLog
import io.legado.app.utils.GSON
import io.legado.app.utils.fromJsonObject
import java.io.InputStream

@JsonIgnoreProperties(
    value = [
        "headerMap", "source", "_userNameSpace", "userNameSpace",
        "loginHeader", "loginHeaderMap", "loginInfo", "loginInfoMap"
    ]
)
data class BookSource(
    var bookSourceUrl: String = "",
    var bookSourceName: String = "",
    var bookSourceGroup: String? = null,
    var bookSourceType: Int = 0,
    var bookUrlPattern: String? = null,
    var customOrder: Int = 0,
    var enabled: Boolean = true,
    var enabledExplore: Boolean = true,
    override var enabledCookieJar: Boolean? = false,
    override var concurrentRate: String? = null,
    override var header: String? = null,
    override var loginUrl: String? = null,
    override var loginUi: String? = null,
    var loginCheckJs: String? = null,
    var bookSourceComment: String? = null,
    var variableComment: String? = null,
    var lastUpdateTime: Long = 0L,
    var respondTime: Long = 180000L,
    var weight: Int = 0,
    var exploreUrl: String? = null,
    var ruleExplore: ExploreRule? = null,
    var searchUrl: String? = null,
    var ruleSearch: SearchRule? = null,
    var ruleBookInfo: BookInfoRule? = null,
    var ruleToc: TocRule? = null,
    var ruleContent: ContentRule? = null
) : BaseSource {

    @Transient
    private var _userNameSpace: String = ""

    @Transient
    private var debugLog: DebugLog? = null

    override val source: BaseSource
        get() = this

    override val key: String
        get() = bookSourceUrl

    override val tag: String
        get() = bookSourceName

    override val userNameSpace: String
        get() = _userNameSpace

    override val logger: DebugLog?
        get() = debugLog

    fun setUserNameSpace(nameSpace: String) {
        _userNameSpace = nameSpace
    }

    fun setLogger(logger: DebugLog?) {
        debugLog = logger
    }

    val searchRule: SearchRule
        get() = ruleSearch ?: SearchRule()

    val exploreRule: ExploreRule
        get() = ruleExplore ?: ExploreRule()

    val bookInfoRule: BookInfoRule
        get() = ruleBookInfo ?: BookInfoRule()

    val tocRule: TocRule
        get() = ruleToc ?: TocRule()

    val contentRule: ContentRule
        get() = ruleContent ?: ContentRule()

    fun equal(source: BookSource): Boolean {
        return equal(bookSourceName, source.bookSourceName) &&
            equal(bookSourceUrl, source.bookSourceUrl) &&
            equal(bookSourceGroup, source.bookSourceGroup) &&
            bookSourceType == source.bookSourceType &&
            equal(bookUrlPattern, source.bookUrlPattern) &&
            enabled == source.enabled &&
            enabledExplore == source.enabledExplore &&
            enabledCookieJar == source.enabledCookieJar &&
            equal(header, source.header) &&
            equal(loginUrl, source.loginUrl) &&
            equal(exploreUrl, source.exploreUrl) &&
            equal(searchUrl, source.searchUrl) &&
            searchRule == source.searchRule &&
            exploreRule == source.exploreRule &&
            bookInfoRule == source.bookInfoRule &&
            tocRule == source.tocRule &&
            contentRule == source.contentRule
    }

    private fun equal(a: String?, b: String?): Boolean {
        if (a == b) return true
        return a.isNullOrEmpty() && b.isNullOrEmpty()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is BookSource) {
            other.bookSourceUrl == bookSourceUrl
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return bookSourceUrl.hashCode()
    }

    override fun toString(): String {
        return "BookSource(bookSourceUrl=$bookSourceUrl, bookSourceName=$bookSourceName)"
    }

    class ExploreKind(
        var title: String,
        var url: String? = null
    )

    companion object {

        fun fromJson(json: String): Result<BookSource> {
            return SourceAnalyzer.jsonToBookSource(json)
        }

        fun fromJsonArray(json: String): Result<List<BookSource>> {
            return SourceAnalyzer.jsonToBookSources(json)
        }

        fun fromJsonArray(inputStream: InputStream): Result<List<BookSource>> {
            return SourceAnalyzer.jsonToBookSources(inputStream)
        }
    }

    class Converters {

        fun searchRuleToString(searchRule: SearchRule?): String {
            return GSON.toJson(searchRule)
        }

        fun stringToSearchRule(json: String?): SearchRule? {
            return GSON.fromJsonObject<SearchRule>(json).getOrNull()
        }

        fun exploreRuleToString(exploreRule: ExploreRule?): String {
            return GSON.toJson(exploreRule)
        }

        fun stringToExploreRule(json: String?): ExploreRule? {
            return GSON.fromJsonObject<ExploreRule>(json).getOrNull()
        }

        fun bookInfoRuleToString(bookInfoRule: BookInfoRule?): String {
            return GSON.toJson(bookInfoRule)
        }

        fun stringToBookInfoRule(json: String?): BookInfoRule? {
            return GSON.fromJsonObject<BookInfoRule>(json).getOrNull()
        }

        fun tocRuleToString(tocRule: TocRule?): String {
            return GSON.toJson(tocRule)
        }

        fun stringToTocRule(json: String?): TocRule? {
            return GSON.fromJsonObject<TocRule>(json).getOrNull()
        }

        fun contentRuleToString(contentRule: ContentRule?): String {
            return GSON.toJson(contentRule)
        }

        fun stringToContentRule(json: String?): ContentRule? {
            return GSON.fromJsonObject<ContentRule>(json).getOrNull()
        }
    }
}
