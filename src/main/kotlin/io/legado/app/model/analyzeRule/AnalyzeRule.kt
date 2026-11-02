// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
@file:Suppress(
    "INVISIBLE_MEMBER",
    "INVISIBLE_REFERENCE",
    "UNCHECKED_CAST",
    "UNUSED_EXPRESSION",
    "VIRTUAL_MEMBER_HIDDEN",
    "CANNOT_WEAKEN_ACCESS_PRIVILEGE",
    "NOTHING_TO_OVERRIDE"
)

package io.legado.app.model.analyzeRule

import com.script.SimpleBindings
import io.legado.app.constant.AppConst
import io.legado.app.constant.AppPattern
import io.legado.app.data.entities.BaseBook
import io.legado.app.data.entities.BaseSource
import io.legado.app.data.entities.Book
import io.legado.app.data.entities.BookChapter
import io.legado.app.data.entities.BookSource
import io.legado.app.data.entities.SearchBook
import io.legado.app.help.CacheManager
import io.legado.app.help.JsExtensions
import io.legado.app.help.http.CookieStore
import io.legado.app.model.DebugLog
import io.legado.app.model.webBook.WebBook
import io.legado.app.utils.GSON
import io.legado.app.utils.NetworkUtils
import io.legado.app.utils.StringUtils
import io.legado.app.utils.TextUtils
import io.legado.app.utils.fromJsonObject
import io.legado.app.utils.isJson
import io.legado.app.utils.msg
import io.legado.app.utils.splitNotBlank
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import mu.KLogger
import mu.KotlinLogging
import org.jsoup.nodes.Entities
import org.mozilla.javascript.NativeObject
import java.net.URL
import java.util.regex.Pattern

private val logger: KLogger = KotlinLogging.logger { }

/**
 * 规则求值器：把一条书源规则跑在正文上，返回字符串 / 列表 / 元素。
 *
 * 三个解析器（XPath、JSoup、JSonPath）按 [content] 惰性构造并缓存，
 * [setContent] 会把三份缓存一起置脏。
 */
class AnalyzeRule(
    var ruleData: RuleDataInterface,
    source: BaseSource? = null,
    var debugLog: DebugLog? = null
) : JsExtensions {

    // 制品把 source 存成私有字段：方法体内走 getfield，lambda 里走 access$getSource$p。
    // 属性形式（override val）做不到——override 成员隐式 open，编译器对内一率发 getSource()。
    // 仓库里的 JsExtensions 把 source 声明成 val，属性就无法私有，只能用「私有字段 + 显式
    // getter」还原字段访问形态；override/hide 两条诊断在文件头 @file:Suppress 里压掉。
    private val source: BaseSource? = source

    override fun getSource(): BaseSource? = this.source

    var chapter: BookChapter? = null

    var nextChapterUrl: String? = null

    var content: Any? = null
        private set

    var baseUrl: String? = null
        private set

    var redirectUrl: URL? = null
        private set

    private var isJSON = false

    private var isRegex = false

    private var analyzeByXPath: AnalyzeByXPath? = null

    private var analyzeByJSoup: AnalyzeByJSoup? = null

    private var analyzeByJSonPath: AnalyzeByJSonPath? = null

    private var objectChangedXP = false

    private var objectChangedJS = false

    private var objectChangedJP = false

    val book: BaseBook?
        get() = ruleData as? BaseBook

    override val userNameSpace: String
        get() = ruleData.getUserNameSpace()

    override val logger: DebugLog?
        get() = debugLog

    @JvmOverloads
    fun setContent(content: Any?, baseUrl: String? = null): AnalyzeRule {
        if (content == null) {
            throw AssertionError("内容不可空（Content cannot be null）")
        }
        this.content = content
        isJSON = content.toString().isJson()
        setBaseUrl(baseUrl)
        objectChangedXP = true
        objectChangedJS = true
        objectChangedJP = true
        return this
    }

    fun setBaseUrl(baseUrl: String?): AnalyzeRule {
        baseUrl?.let { this.baseUrl = it }
        return this
    }

    fun setRedirectUrl(url: String): URL? {
        try {
            redirectUrl = URL(url)
        } catch (e: Exception) {
            log("URL($url) error\n" + e.localizedMessage)
        }
        return redirectUrl
    }

    private fun getAnalyzeByXPath(o: Any): AnalyzeByXPath {
        return if (o != content) {
            AnalyzeByXPath(o)
        } else {
            if (analyzeByXPath == null || objectChangedXP) {
                analyzeByXPath = AnalyzeByXPath(content!!)
                objectChangedXP = false
            }
            analyzeByXPath!!
        }
    }

    private fun getAnalyzeByJSoup(o: Any): AnalyzeByJSoup {
        return if (o != content) {
            AnalyzeByJSoup(o)
        } else {
            if (analyzeByJSoup == null || objectChangedJS) {
                analyzeByJSoup = AnalyzeByJSoup(content!!)
                objectChangedJS = false
            }
            analyzeByJSoup!!
        }
    }

    private fun getAnalyzeByJSonPath(o: Any): AnalyzeByJSonPath {
        return if (o != content) {
            AnalyzeByJSonPath(o)
        } else {
            if (analyzeByJSonPath == null || objectChangedJP) {
                analyzeByJSonPath = AnalyzeByJSonPath(content!!)
                objectChangedJP = false
            }
            analyzeByJSonPath!!
        }
    }

    @JvmOverloads
    fun getStringList(
        rule: String?,
        mContent: Any? = null,
        isUrl: Boolean = false
    ): List<String>? {
        if (rule.isNullOrEmpty()) {
            return null
        }
        val ruleList = splitSourceRule(rule, false)
        return getStringList(ruleList, mContent, isUrl)
    }

    @JvmOverloads
    fun getStringList(
        ruleList: List<SourceRule>,
        mContent: Any? = null,
        isUrl: Boolean = false
    ): List<String>? {
        var result: Any? = null
        val content = mContent ?: this.content
        if (content != null && ruleList.isNotEmpty()) {
            result = content
            if (content is NativeObject) {
                result = content.get(ruleList[0].rule)?.toString()
            } else {
                for (sourceRule in ruleList) {
                    putRule(sourceRule.putMap)
                    sourceRule.makeUpRule(result)
                    val cur = result ?: continue
                    if (sourceRule.rule.isNotEmpty()) {
                        result = when (sourceRule.mode) {
                            Mode.Js -> evalJS(sourceRule.rule, result)
                            Mode.Json -> getAnalyzeByJSonPath(cur)
                                .getStringList(sourceRule.rule)

                            Mode.XPath -> getAnalyzeByXPath(cur)
                                .getStringList(sourceRule.rule)

                            Mode.Default -> getAnalyzeByJSoup(cur)
                                .getStringList(sourceRule.rule)

                            else -> sourceRule.rule
                        }
                    }
                    if (sourceRule.replaceRegex.isNotEmpty() && result is List<*>) {
                        val newList = ArrayList<String>()
                        for (item in result as List<*>) {
                            newList.add(replaceRegex(item.toString(), sourceRule))
                        }
                        result = newList
                        continue
                    }
                    if (sourceRule.replaceRegex.isEmpty()) continue
                    result = replaceRegex(result.toString(), sourceRule)
                }
            }
        }
        if (result == null) {
            return null
        }
        if (result is String) {
            result = (result as String).split("\n")
        }
        if (isUrl) {
            val urlList = ArrayList<String>()
            if (result is List<*>) {
                for (url in result as List<*>) {
                    val absoluteURL = NetworkUtils.getAbsoluteURL(redirectUrl, url.toString())
                    if (absoluteURL.isEmpty() || urlList.contains(absoluteURL)) continue
                    urlList.add(absoluteURL)
                }
            }
            return urlList
        }
        return result as? List<String>
    }

    @JvmOverloads
    fun getString(
        ruleStr: String?,
        mContent: Any? = null,
        isUrl: Boolean = false
    ): String {
        if (TextUtils.isEmpty(ruleStr)) {
            return ""
        }
        val ruleList = splitSourceRule(ruleStr)
        return getString(ruleList, mContent, isUrl)
    }

    @JvmOverloads
    fun getString(
        ruleList: List<SourceRule>,
        mContent: Any? = null,
        isUrl: Boolean = false
    ): String {
        var result: Any? = null
        val content = mContent ?: this.content
        if (content != null && ruleList.isNotEmpty()) {
            result = content
            if (content is NativeObject) {
                result = content.get(ruleList[0].rule)?.toString()
            } else {
                for (sourceRule in ruleList) {
                    putRule(sourceRule.putMap)
                    sourceRule.makeUpRule(result)
                    val cur = result ?: continue
                    if (sourceRule.rule.isNotBlank() || sourceRule.replaceRegex.isEmpty()) {
                        result = when (sourceRule.mode) {
                            Mode.Js -> evalJS(sourceRule.rule, cur)
                            Mode.Json -> getAnalyzeByJSonPath(cur).getString(sourceRule.rule)
                            Mode.XPath -> getAnalyzeByXPath(cur).getString(sourceRule.rule)
                            Mode.Default -> if (isUrl) {
                                getAnalyzeByJSoup(cur).getString0(sourceRule.rule)
                            } else {
                                getAnalyzeByJSoup(cur).getString(sourceRule.rule)
                            }

                            else -> sourceRule.rule
                        }
                    }
                    if (result == null) continue
                    if (sourceRule.replaceRegex.isEmpty()) continue
                    result = replaceRegex(result.toString(), sourceRule)
                }
            }
        }
        if (result == null) {
            result = ""
        }
        val str = runCatching {
            Entities.unescape(result.toString())
        }.onFailure {
            log("Entities.unescape() error\n" + it.localizedMessage)
        }.getOrElse {
            result.toString()
        }
        if (isUrl) {
            return if (str.isBlank()) baseUrl ?: "" else NetworkUtils.getAbsoluteURL(
                redirectUrl,
                str
            )
        }
        return str
    }

    fun getElement(ruleStr: String): Any? {
        if (TextUtils.isEmpty(ruleStr)) {
            return null
        }
        var result: Any? = null
        val content = content
        val ruleList = splitSourceRule(ruleStr, true)
        if (content != null && ruleList.isNotEmpty()) {
            result = content
            for (sourceRule in ruleList) {
                putRule(sourceRule.putMap)
                sourceRule.makeUpRule(result)
                val cur = result ?: continue
                result = when (sourceRule.mode) {
                    Mode.Regex -> AnalyzeByRegex.getElement(
                        result.toString(),
                        sourceRule.rule.splitNotBlank("&&")
                    )

                    Mode.Js -> evalJS(sourceRule.rule, cur)
                    Mode.Json -> getAnalyzeByJSonPath(cur).getObject(sourceRule.rule)
                    Mode.XPath -> getAnalyzeByXPath(cur).getElements(sourceRule.rule)
                    else -> getAnalyzeByJSoup(cur).getElements(sourceRule.rule)
                }
                if (sourceRule.replaceRegex.isEmpty()) continue
                result = replaceRegex(result.toString(), sourceRule)
            }
        }
        return result
    }

    fun getElements(ruleStr: String): List<Any> {
        var result: Any? = null
        val content = content
        val ruleList = splitSourceRule(ruleStr, true)
        if (content != null && ruleList.isNotEmpty()) {
            result = content
            for (sourceRule in ruleList) {
                putRule(sourceRule.putMap)
                val cur = result ?: continue
                result = when (sourceRule.mode) {
                    Mode.Regex -> AnalyzeByRegex.getElements(
                        result.toString(),
                        sourceRule.rule.splitNotBlank("&&")
                    )

                    Mode.Js -> evalJS(sourceRule.rule, result)
                    Mode.Json -> getAnalyzeByJSonPath(cur).getList(sourceRule.rule)
                    Mode.XPath -> getAnalyzeByXPath(cur).getElements(sourceRule.rule)
                    else -> getAnalyzeByJSoup(cur).getElements(sourceRule.rule)
                }
                if (sourceRule.replaceRegex.isEmpty()) continue
                result = replaceRegex(result.toString(), sourceRule)
            }
        }
        if (result != null) {
            return result as List<Any>
        }
        return ArrayList()
    }

    private fun putRule(map: Map<String, String>) {
        for ((key, value) in map) {
            put(key, getString(value))
        }
    }

    private fun splitPutRule(ruleStr: String, putMap: HashMap<String, String>): String {
        var vRuleStr = ruleStr
        val putMatcher = putPattern.matcher(vRuleStr)
        while (putMatcher.find()) {
            vRuleStr = vRuleStr.replace(putMatcher.group(), "")
            val json = putMatcher.group(1)
            val map: Map<String, String>? = GSON.fromJsonObject<Map<String, String>>(json).getOrNull()
            if (map == null) continue
            putMap.putAll(map)
        }
        return vRuleStr
    }

    private fun replaceRegex(result: String, rule: SourceRule): String {
        if (rule.replaceRegex.isEmpty()) {
            return result
        }
        var vResult = result
        vResult = if (rule.replaceFirst) {
            runCatching {
                val pattern = Pattern.compile(rule.replaceRegex)
                val matcher = pattern.matcher(vResult)
                if (matcher.find()) {
                    val group = matcher.group(0)
                    Regex(rule.replaceRegex).replaceFirst(group!!, rule.replacement)
                } else {
                    ""
                }
            }.getOrElse {
                vResult.replaceFirst(rule.replaceRegex, rule.replacement)
            }
        } else {
            runCatching {
                Regex(rule.replaceRegex).replace(vResult, rule.replacement)
            }.getOrElse {
                vResult.replace(rule.replaceRegex, rule.replacement)
            }
        }
        return vResult
    }

    fun splitSourceRule(ruleStr: String?, allInOne: Boolean = false): List<SourceRule> {
        if (ruleStr.isNullOrEmpty()) {
            return emptyList()
        }
        val ruleList = ArrayList<SourceRule>()
        var mode = Mode.Default
        var start = 0
        if (allInOne && ruleStr.startsWith(":")) {
            mode = Mode.Regex
            isRegex = true
            start = 1
        } else if (isRegex) {
            mode = Mode.Regex
        }
        var tmp: String
        val jsMatcher = AppPattern.JS_PATTERN.matcher(ruleStr)
        while (jsMatcher.find()) {
            if (jsMatcher.start() > start) {
                tmp = ruleStr.substring(start, jsMatcher.start()).trim()
                if (tmp.isNotEmpty()) {
                    ruleList.add(SourceRule(tmp, mode))
                }
            }
            tmp = jsMatcher.group(2) ?: jsMatcher.group(1)!!
            ruleList.add(SourceRule(tmp, Mode.Js))
            start = jsMatcher.end()
        }
        if (ruleStr.length > start) {
            tmp = ruleStr.substring(start).trim()
            if (tmp.isNotEmpty()) {
                ruleList.add(SourceRule(tmp, mode))
            }
        }
        return ruleList
    }

    fun put(key: String, value: String): String {
        chapter?.putVariable(key, value)
            ?: book?.putVariable(key, value)
            ?: ruleData.putVariable(key, value)
        return value
    }

    fun get(key: String): String {
        if (key == "bookName") {
            book?.let { return it.name }
        } else if (key == "title") {
            chapter?.let { return it.title }
        }
        return chapter?.getVariable(key)
            ?: book?.getVariable(key)
            ?: ruleData.getVariable(key)
            ?: ""
    }

    fun evalJS(jsStr: String, result: Any? = null): Any? {
        val bindings = SimpleBindings()
        bindings["java"] = this
        bindings["cookie"] = CookieStore(userNameSpace)
        bindings["cache"] = CacheManager(userNameSpace)
        bindings["source"] = source
        bindings["book"] = book
        bindings["result"] = result
        bindings["baseUrl"] = baseUrl
        bindings["chapter"] = chapter
        bindings["title"] = chapter?.title
        bindings["src"] = content
        bindings["nextChapterUrl"] = nextChapterUrl
        return AppConst.SCRIPT_ENGINE.eval(jsStr, bindings)
    }

    override fun ajax(urlStr: String): String? = runBlocking {
        runCatching {
            AnalyzeUrl(
                mUrl = urlStr,
                source = source,
                ruleData = book,
                debugLog = debugLog
            ).getStrResponseAwait().body
        }.onFailure {
            log("ajax($urlStr) error\n" + it.stackTraceToString())
        }.getOrElse {
            it.msg
        }
    }

    fun toNumChapter(s: String?): String? {
        if (s == null) {
            return null
        }
        val matcher = titleNumPattern.matcher(s)
        if (matcher.find()) {
            return matcher.group(1) + StringUtils.stringToInt(matcher.group(2)) + matcher.group(3)
        }
        return s
    }

    fun reGetBook() {
        val bookSource = source as? BookSource
        val book = book as? Book
        if (bookSource == null || book == null) {
            return
        }
        runBlocking {
            withTimeout(1800000L) {
                WebBook(bookSource, userNameSpace = userNameSpace)
                    .preciseSearch(book.name, book.author)
                    .getOrThrow()
                    .let {
                        book.bookUrl = it.bookUrl
                        it.variableMap.forEach { (key, value) ->
                            book.putVariable(key, value)
                        }
                    }
                WebBook(bookSource, userNameSpace = userNameSpace).getBookInfo(book, false)
            }
        }
    }

    fun refreshBookUrl() {
        runBlocking {
            val bookSource = source as? BookSource ?: return@runBlocking
            val book = book as? Book ?: return@runBlocking
            val books = WebBook(bookSource, userNameSpace = userNameSpace).searchBook(book.name)
            books.forEach {
                if (it.name != book.name || it.author != book.author) return@forEach
                book.bookUrl = it.bookUrl
                if (it.tocUrl.isNotBlank()) {
                    book.tocUrl = it.tocUrl
                }
                return@runBlocking
            }
        }
    }

    fun refreshTocUrl() {
        runBlocking {
            val bookSource = source as? BookSource ?: return@runBlocking
            val book = book as? Book ?: return@runBlocking
            WebBook(bookSource, userNameSpace = userNameSpace).getBookInfo(book)
        }
    }

    companion object {
        private val putPattern = Pattern.compile("@put:(\\{[^}]+?\\})", Pattern.CASE_INSENSITIVE)
        private val evalPattern =
            Pattern.compile("@get:\\{[^}]+?\\}|\\{\\{[\\w\\W]*?\\}\\}", Pattern.CASE_INSENSITIVE)
        private val regexPattern = Pattern.compile("\\$\\d{1,2}")
        private val titleNumPattern = Pattern.compile("(第)(.+?)(章)")
    }

    enum class Mode {
        XPath, Json, Default, Js, Regex
    }

    inner class SourceRule(ruleStr: String, mode: Mode = Mode.Default) {

        @get:JvmName("getMode\$reader_pro")
        @set:JvmName("setMode\$reader_pro")
        var mode: Mode = mode

        @get:JvmName("getRule\$reader_pro")
        @set:JvmName("setRule\$reader_pro")
        var rule: String

        @get:JvmName("getReplaceRegex\$reader_pro")
        @set:JvmName("setReplaceRegex\$reader_pro")
        var replaceRegex = ""

        @get:JvmName("getReplacement\$reader_pro")
        @set:JvmName("setReplacement\$reader_pro")
        var replacement = ""

        @get:JvmName("getReplaceFirst\$reader_pro")
        @set:JvmName("setReplaceFirst\$reader_pro")
        var replaceFirst = false

        @get:JvmName("getPutMap\$reader_pro")
        val putMap = HashMap<String, String>()

        private val ruleParam = ArrayList<String>()

        private val ruleType = ArrayList<Int>()

        private val getRuleType = -2

        private val jsRuleType = -1

        private val defaultRuleType = 0

        init {
            rule = if (mode == Mode.Js || mode == Mode.Regex) {
                ruleStr
            } else if (ruleStr.startsWith("@CSS:", true)) {
                this.mode = Mode.Default
                ruleStr
            } else if (ruleStr.startsWith("@@")) {
                this.mode = Mode.Default
                ruleStr.substring(2)
            } else if (ruleStr.startsWith("@XPath:", true)) {
                this.mode = Mode.XPath
                ruleStr.substring(7)
            } else if (ruleStr.startsWith("@Json:", true)) {
                this.mode = Mode.Json
                ruleStr.substring(6)
            } else if (isJSON || ruleStr.startsWith("$.") || ruleStr.startsWith("$[")) {
                this.mode = Mode.Json
                ruleStr
            } else if (ruleStr.startsWith("/")) {
                this.mode = Mode.XPath
                ruleStr
            } else {
                ruleStr
            }
            rule = splitPutRule(rule, putMap)
            var start = 0
            var tmp: String
            val evalMatcher = evalPattern.matcher(rule)
            if (evalMatcher.find()) {
                tmp = rule.substring(start, evalMatcher.start())
                if (!(mode == Mode.Js || mode == Mode.Regex ||
                        evalMatcher.start() != 0 && tmp.contains("##"))
                ) {
                    this.mode = Mode.Regex
                }
                do {
                    if (evalMatcher.start() > start) {
                        splitRegex(rule.substring(start, evalMatcher.start()))
                    }
                    tmp = evalMatcher.group()
                    if (tmp.startsWith("@get:", true)) {
                        ruleType.add(getRuleType)
                        ruleParam.add(tmp.substring(6, tmp.lastIndex))
                    } else if (tmp.startsWith("{{")) {
                        ruleType.add(jsRuleType)
                        ruleParam.add(tmp.substring(2, tmp.length - 2))
                    } else {
                        splitRegex(tmp)
                    }
                    start = evalMatcher.end()
                } while (evalMatcher.find())
            }
            if (rule.length > start) {
                splitRegex(rule.substring(start))
            }
        }

        private fun splitRegex(ruleStr: String) {
            var start = 0
            var tmp: String
            val ruleStrArray = ruleStr.split("##")
            val regexMatcher = regexPattern.matcher(ruleStrArray[0])
            if (regexMatcher.find()) {
                if (mode != Mode.Js && mode != Mode.Regex) {
                    this.mode = Mode.Regex
                }
                do {
                    if (regexMatcher.start() > start) {
                        tmp = ruleStr.substring(start, regexMatcher.start())
                        ruleType.add(defaultRuleType)
                        ruleParam.add(tmp)
                    }
                    tmp = regexMatcher.group()
                    ruleType.add(tmp.substring(1).toInt())
                    ruleParam.add(tmp)
                    start = regexMatcher.end()
                } while (regexMatcher.find())
            }
            if (ruleStr.length > start) {
                tmp = ruleStr.substring(start)
                ruleType.add(defaultRuleType)
                ruleParam.add(tmp)
            }
        }

        fun makeUpRule(result: Any?) {
            val infoVal = StringBuilder()
            if (ruleParam.isNotEmpty()) {
                var index = ruleParam.size
                while (true) {
                    index -= 1
                    if (index < 0) break
                    val regType = ruleType[index]
                    if (regType > defaultRuleType) {
                        (result as? List<*>)?.let {
                            if (it.size > regType) {
                                val str = it[regType] as? String
                                if (str != null) {
                                    infoVal.insert(0, str)
                                }
                            }
                        } ?: infoVal.insert(0, ruleParam[index])
                    } else if (regType == jsRuleType) {
                        if (isRule(ruleParam[index])) {
                            infoVal.insert(0, getString(arrayListOf(SourceRule(ruleParam[index]))))
                        } else {
                            val jsEval = evalJS(ruleParam[index], result)
                            when {
                                jsEval == null -> Unit
                                jsEval is String -> infoVal.insert(0, jsEval)
                                jsEval is Double && jsEval % 1.0 == 0.0 ->
                                    infoVal.insert(0, String.format("%.0f", jsEval))

                                else -> infoVal.insert(0, jsEval.toString())
                            }
                        }
                    } else if (regType == getRuleType) {
                        infoVal.insert(0, get(ruleParam[index]))
                    } else {
                        infoVal.insert(0, ruleParam[index])
                    }
                }
                rule = infoVal.toString()
            }
            val ruleStrS = rule.split("##")
            rule = ruleStrS[0].trim()
            if (ruleStrS.size > 1) {
                replaceRegex = ruleStrS[1]
            }
            if (ruleStrS.size > 2) {
                replacement = ruleStrS[2]
            }
            if (ruleStrS.size > 3) {
                replaceFirst = true
            }
        }

        private fun isRule(ruleStr: String): Boolean {
            return ruleStr.startsWith('@') || ruleStr.startsWith("$.") ||
                    ruleStr.startsWith("$[") || ruleStr.startsWith("//")
        }
    }
}
