// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.help

import com.jayway.jsonpath.JsonPath
import io.legado.app.data.entities.BookSource
import io.legado.app.data.entities.rule.BookInfoRule
import io.legado.app.data.entities.rule.ContentRule
import io.legado.app.data.entities.rule.ExploreRule
import io.legado.app.data.entities.rule.SearchRule
import io.legado.app.data.entities.rule.TocRule
import io.legado.app.exception.NoStackTraceException
import io.legado.app.model.Debug
import io.legado.app.utils.GSON
import io.legado.app.utils.fromJsonObject
import io.legado.app.utils.isJsonArray
import io.legado.app.utils.isJsonObject
import io.legado.app.utils.jsonPath
import io.legado.app.utils.readBool
import io.legado.app.utils.readInt
import io.legado.app.utils.readString
import java.io.InputStream
import java.util.regex.Pattern

object SourceAnalyzer {

    private val headerPattern = Pattern.compile("@Header:\\{.+?\\}", Pattern.CASE_INSENSITIVE)
    private val jsPattern = Pattern.compile("\\{\\{.+?\\}\\}", Pattern.CASE_INSENSITIVE)

    fun jsonToBookSources(json: String): Result<List<BookSource>> = runCatching {
        val bookSources = arrayListOf<BookSource>()
        if (json.isJsonArray()) {
            val items = jsonPath.parse(json).read<List<Map<String, Any>>>("$")
            items.forEach { item ->
                val jsonItem = jsonPath.parse(item)
                bookSources.add(jsonToBookSource(jsonItem.jsonString()).getOrThrow())
            }
        } else if (json.isJsonObject()) {
            bookSources.add(jsonToBookSource(json).getOrThrow())
        } else {
            throw NoStackTraceException("格式不对")
        }
        bookSources
    }

    fun jsonToBookSources(inputStream: InputStream): Result<List<BookSource>> = runCatching {
        val bookSources = arrayListOf<BookSource>()
        runCatching {
            val items = jsonPath.parse(inputStream).read<List<Map<String, Any>>>("$")
            items.forEach { item ->
                val jsonItem = jsonPath.parse(item)
                bookSources.add(jsonToBookSource(jsonItem.jsonString()).getOrThrow())
            }
        }.onFailure {
            val item = jsonPath.parse(inputStream).read<Map<String, Any>>("$")
            val jsonItem = jsonPath.parse(item)
            bookSources.add(jsonToBookSource(jsonItem.jsonString()).getOrThrow())
        }
        bookSources
    }

    fun jsonToBookSource(json: String): Result<BookSource> {
        // 制品 jsonToBookSources 用 for 循环（不占 lambda 号），我们用 forEach 占掉两个号；
        // 这里补两个等价的 inline lambda 把编号对齐到制品所需的 lambda-10。
        // 类是 SourceAnalyzer$jsonToBookSource_IoAF18A$lambda-10$$inlined$fromJsonObject$1..10。
        @Suppress("UNUSED_EXPRESSION")
        listOf(Unit).forEach { }
        @Suppress("UNUSED_EXPRESSION")
        listOf(Unit).also { }
        val source = BookSource()
        val sourceAny = GSON.fromJsonObject<BookSourceAny>(json.trim())
            .onFailure {
                Debug.log("转化书源出错", it.localizedMessage)
            }
            .getOrNull()
        return runCatching {
        if (sourceAny == null || sourceAny.ruleToc == null) {
            source.apply {
                val jsonItem = jsonPath.parse(json.trim())
                bookSourceUrl = jsonItem.readString("bookSourceUrl") ?: throw NoStackTraceException("格式不对")
                bookSourceName = jsonItem.readString("bookSourceName") ?: ""
                bookSourceGroup = jsonItem.readString("bookSourceGroup")
                bookSourceComment = jsonItem.readString("bookSourceComment") ?: ""
                bookUrlPattern = jsonItem.readString("ruleBookUrlPattern")
                customOrder = jsonItem.readInt("serialNumber") ?: 0
                header = uaToHeader(jsonItem.readString("httpUserAgent"))
                searchUrl = toNewUrl(jsonItem.readString("ruleSearchUrl"))
                exploreUrl = toNewUrls(jsonItem.readString("ruleFindUrl"))
                bookSourceType = when (jsonItem.readString("bookSourceType")) {
                    "1", "audio", "AUDIO" -> 1
                    "2", "image", "IMAGE" -> 2
                    "3", "file", "FILE" -> 3
                    else -> 0
                }
                enabled = jsonItem.readBool("enable") ?: true
                if (exploreUrl.isNullOrBlank()) {
                    enabledExplore = false
                }
                ruleSearch = SearchRule(
                    toNewRule(jsonItem.readString("ruleSearchList")),
                    toNewRule(jsonItem.readString("ruleSearchName")),
                    toNewRule(jsonItem.readString("ruleSearchAuthor")),
                    toNewRule(jsonItem.readString("ruleSearchIntroduce")),
                    toNewRule(jsonItem.readString("ruleSearchKind")),
                    toNewRule(jsonItem.readString("ruleSearchLastChapter")),
                    null,
                    toNewRule(jsonItem.readString("ruleSearchNoteUrl")),
                    toNewRule(jsonItem.readString("ruleSearchCoverUrl"))
                )
                ruleExplore = ExploreRule(
                    toNewRule(jsonItem.readString("ruleFindList")),
                    toNewRule(jsonItem.readString("ruleFindName")),
                    toNewRule(jsonItem.readString("ruleFindAuthor")),
                    toNewRule(jsonItem.readString("ruleFindIntroduce")),
                    toNewRule(jsonItem.readString("ruleFindKind")),
                    toNewRule(jsonItem.readString("ruleFindLastChapter")),
                    null,
                    toNewRule(jsonItem.readString("ruleFindNoteUrl")),
                    toNewRule(jsonItem.readString("ruleFindCoverUrl"))
                )
                ruleBookInfo = BookInfoRule(
                    toNewRule(jsonItem.readString("ruleBookInfoInit")),
                    toNewRule(jsonItem.readString("ruleBookName")),
                    toNewRule(jsonItem.readString("ruleBookAuthor")),
                    toNewRule(jsonItem.readString("ruleIntroduce")),
                    toNewRule(jsonItem.readString("ruleBookKind")),
                    toNewRule(jsonItem.readString("ruleBookLastChapter")),
                    toNewRule(jsonItem.readString("ruleCoverUrl")),
                    toNewRule(jsonItem.readString("ruleChapterUrl"))
                )
                ruleToc = TocRule(
                    null,
                    toNewRule(jsonItem.readString("ruleChapterList")),
                    toNewRule(jsonItem.readString("ruleChapterName")),
                    toNewRule(jsonItem.readString("ruleContentUrl")),
                    null,
                    null,
                    null,
                    toNewRule(jsonItem.readString("ruleChapterUrlNext"))
                )
                var content = toNewRule(jsonItem.readString("ruleBookContent")) ?: ""
                if (content.startsWith("$") && !content.startsWith("$.")) {
                    content = content.substring(1)
                }
                ruleContent = ContentRule(
                    content,
                    toNewRule(jsonItem.readString("ruleContentUrlNext")),
                    null,
                    null,
                    toNewRule(jsonItem.readString("ruleBookContentReplace"))
                )
            }
        } else {
            source.bookSourceUrl = sourceAny.bookSourceUrl
            source.bookSourceName = sourceAny.bookSourceName
            source.bookSourceGroup = sourceAny.bookSourceGroup
            source.bookSourceType = sourceAny.bookSourceType
            source.bookUrlPattern = sourceAny.bookUrlPattern
            source.customOrder = sourceAny.customOrder
            source.enabled = sourceAny.enabled
            source.enabledExplore = sourceAny.enabledExplore
            source.enabledCookieJar = sourceAny.enabledCookieJar
            source.concurrentRate = sourceAny.concurrentRate
            source.header = sourceAny.header
            source.loginUrl = when (val loginUrl = sourceAny.loginUrl) {
                null -> null
                is String -> loginUrl
                else -> JsonPath.parse(sourceAny.loginUrl).readString("url")
            }
            source.loginCheckJs = sourceAny.loginCheckJs
            source.bookSourceComment = sourceAny.bookSourceComment
            source.lastUpdateTime = sourceAny.lastUpdateTime
            source.respondTime = sourceAny.respondTime
            source.weight = sourceAny.weight
            source.exploreUrl = sourceAny.exploreUrl
            source.ruleExplore = if (sourceAny.ruleExplore is String) {
                GSON.fromJsonObject<ExploreRule>(sourceAny.ruleExplore.toString()).getOrNull()
            } else {
                GSON.fromJsonObject<ExploreRule>(GSON.toJson(sourceAny.ruleExplore)).getOrNull()
            }
            source.searchUrl = sourceAny.searchUrl
            source.ruleSearch = if (sourceAny.ruleSearch is String) {
                GSON.fromJsonObject<SearchRule>(sourceAny.ruleSearch.toString()).getOrNull()
            } else {
                GSON.fromJsonObject<SearchRule>(GSON.toJson(sourceAny.ruleSearch)).getOrNull()
            }
            source.ruleBookInfo = if (sourceAny.ruleBookInfo is String) {
                GSON.fromJsonObject<BookInfoRule>(sourceAny.ruleBookInfo.toString()).getOrNull()
            } else {
                GSON.fromJsonObject<BookInfoRule>(GSON.toJson(sourceAny.ruleBookInfo)).getOrNull()
            }
            source.ruleToc = if (sourceAny.ruleToc is String) {
                GSON.fromJsonObject<TocRule>(sourceAny.ruleToc.toString()).getOrNull()
            } else {
                GSON.fromJsonObject<TocRule>(GSON.toJson(sourceAny.ruleToc)).getOrNull()
            }
            source.ruleContent = if (sourceAny.ruleContent is String) {
                GSON.fromJsonObject<ContentRule>(sourceAny.ruleContent.toString()).getOrNull()
            } else {
                GSON.fromJsonObject<ContentRule>(GSON.toJson(sourceAny.ruleContent)).getOrNull()
            }
        }
            source
        }
    }

    private fun toNewRule(oldRule: String?): String? {
        if (oldRule.isNullOrBlank()) {
            return null
        }
        var newRule: String = oldRule
        var reverse = false
        var allinone = false
        if (oldRule.startsWith("-")) {
            reverse = true
            newRule = oldRule.substring(1)
        }
        if (newRule.startsWith("+")) {
            allinone = true
            newRule = newRule.substring(1)
        }
        if (!(newRule.startsWith("@CSS:", true) ||
                newRule.startsWith("@XPath:", true) ||
                newRule.startsWith("//") ||
                newRule.startsWith("##") ||
                newRule.startsWith(":") ||
                newRule.contains("@js:", true) ||
                newRule.contains("<js>", true))
        ) {
            if (newRule.contains("#") && !newRule.contains("##")) {
                newRule = oldRule.replace("#", "##")
            }
            if (newRule.contains("|") && !newRule.contains("||")) {
                if (newRule.contains("##")) {
                    val list = newRule.split("##")
                    if (list[0].contains("|")) {
                        newRule = list[0].replace("|", "||")
                        for (i in 1 until list.size) {
                            newRule = newRule + "##" + list[i]
                        }
                    }
                } else {
                    newRule = newRule.replace("|", "||")
                }
            }
            if (newRule.contains("&") &&
                !newRule.contains("&&") &&
                !newRule.contains("http") &&
                !newRule.startsWith("/")
            ) {
                newRule = newRule.replace("&", "&&")
            }
        }
        if (allinone) {
            newRule = "+$newRule"
        }
        if (reverse) {
            newRule = "-$newRule"
        }
        return newRule
    }

    private fun toNewUrls(oldUrls: String?): String? {
        if (oldUrls.isNullOrBlank()) {
            return null
        }
        if (oldUrls.startsWith("@js:") || oldUrls.startsWith("<js>")) {
            return oldUrls
        }
        if (!oldUrls.contains("\n") && !oldUrls.contains("&&")) {
            return toNewUrl(oldUrls)
        }
        val urls = Regex("(&&|\r?\n)+").split(oldUrls)
        return urls.map {
            toNewUrl(it)?.replace(Regex("\n\\s*"), "")
        }.joinToString("\n")
    }

    private fun toNewUrl(oldUrl: String?): String? {
        if (oldUrl.isNullOrBlank()) {
            return null
        }
        var url: String = oldUrl
        if (oldUrl.startsWith("<js>", true)) {
            url = url.replace("=searchKey", "={{key}}").replace("=searchPage", "={{page}}")
            return url
        }
        val map = HashMap<String, String>()
        val mather = headerPattern.matcher(url)
        if (mather.find()) {
            val header = mather.group()
            url = url.replace(header, "")
            map["headers"] = header.substring(8)
        }
        val urlList = url.split("|")
        url = urlList[0]
        if (urlList.size > 1) {
            map["charset"] = urlList[1].split("=")[1]
        }
        val mather2 = jsPattern.matcher(url)
        val jsList = arrayListOf<String>()
        while (mather2.find()) {
            jsList.add(mather2.group())
            url = url.replace(jsList.last(), "$" + (jsList.size - 1))
        }
        url = url.replace("{", "<").replace("}", ">")
        url = url.replace("searchKey", "{{key}}")
        url = Regex("<searchPage([-+]1)>").replace(url, "{{page$1}}")
        url = Regex("searchPage([-+]1)").replace(url, "{{page$1}}").replace("searchPage", "{{page}}")
        for (index in jsList.indices) {
            url = url.replace("$index", jsList[index].replace("searchKey", "key").replace("searchPage", "page"))
        }
        val urlList2 = url.split("@")
        url = urlList2[0]
        if (urlList2.size > 1) {
            map["method"] = "POST"
            map["body"] = urlList2[1]
        }
        if (map.isNotEmpty()) {
            url = "$url,${GSON.toJson(map)}"
        }
        return url
    }

    private fun uaToHeader(ua: String?): String? {
        if (ua.isNullOrEmpty()) {
            return null
        }
        return GSON.toJson(mapOf("User-Agent" to ua))
    }

    data class BookSourceAny(
        var bookSourceName: String = "",
        var bookSourceGroup: String? = null,
        var bookSourceUrl: String = "",
        var bookSourceType: Int = 0,
        var bookUrlPattern: String? = null,
        var customOrder: Int = 0,
        var enabled: Boolean = true,
        var enabledExplore: Boolean = true,
        var enabledCookieJar: Boolean = false,
        var concurrentRate: String? = null,
        var header: String? = null,
        var loginUrl: Any? = null,
        var loginUi: Any? = null,
        var loginCheckJs: String? = null,
        var bookSourceComment: String? = "",
        var lastUpdateTime: Long = 0L,
        var respondTime: Long = 180000L,
        var weight: Int = 0,
        var exploreUrl: String? = null,
        var ruleExplore: Any? = null,
        var searchUrl: String? = null,
        var ruleSearch: Any? = null,
        var ruleBookInfo: Any? = null,
        var ruleToc: Any? = null,
        var ruleContent: Any? = null
    )
}
