// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.model.webBook

import io.legado.app.data.entities.Book
import io.legado.app.data.entities.BookChapter
import io.legado.app.data.entities.BookSource
import io.legado.app.data.entities.SearchBook
import io.legado.app.exception.NoStackTraceException
import io.legado.app.help.http.StrResponse
import io.legado.app.model.Debug
import io.legado.app.model.DebugLog
import io.legado.app.model.analyzeRule.AnalyzeUrl
import kotlin.jvm.internal.Ref
import mu.KLogger
import mu.KotlinLogging

/**
 * 网络书源调度器。
 *
 * 每个入口都先把自己的命名空间与调试器写到书源上（书源是共享对象，规则求值时的
 * `source` 变量依赖这两项），再走「构造 [AnalyzeUrl] → 取响应 → 交给对应解析器」的
 * 固定三段式。`loginCheckJs` 命中时先拿响应跑一遍校验脚本再解析。
 */
class WebBook(
    private val bookSource: BookSource,
    val debugLog: Boolean = true,
    var debugLogger: DebugLog? = null,
    var userNameSpace: String? = null
) {

    constructor(
        bookSourceString: String,
        debugLog: Boolean = true,
        debugLogger: DebugLog? = null,
        userNameSpace: String? = null
    ) : this(
        BookSource.fromJson(bookSourceString).getOrNull() ?: BookSource(),
        debugLog,
        debugLogger,
        userNameSpace
    )

    /**
     * 制品里本类对书源的**每一次**访问都是 `invokevirtual getBookSource()`（只有 [sourceUrl]
     * 直接读字段），这是类文件里验证过的形态：源码写成 `bookSource.xxx` 会编成 `getfield`，
     * 得不到这个形状。别把它改回属性访问。
     */
    fun getBookSource(): BookSource = bookSource

    val sourceUrl: String
        get() = bookSource.bookSourceUrl

    val debugger: DebugLog?
        get() {
            if (debugLogger != null) {
                return debugLogger
            }
            if (debugLog) {
                return Debug
            }
            return null
        }

    val userNS: String
        get() = userNameSpace ?: "unknow"

    suspend fun searchBook(
        key: String,
        page: Int? = 1
    ): List<SearchBook> {
        val variableBook = SearchBook()
        variableBook.setUserNameSpace(userNS)
        getBookSource().setUserNameSpace(userNS)
        getBookSource().setLogger(debugger)
        return getBookSource().searchUrl?.let { searchUrl ->
            val analyzeUrl = AnalyzeUrl(
                searchUrl,
                key,
                page,
                baseUrl = getBookSource().bookSourceUrl,
                source = getBookSource(),
                ruleData = variableBook,
                headerMapF = getBookSource().getHeaderMap(true),
                debugLog = debugger
            )
            val res = Ref.ObjectRef<StrResponse>()
            res.element = analyzeUrl.getStrResponseAwait()
            getBookSource().loginCheckJs?.let { checkJs ->
                if (checkJs.isNotBlank()) {
                    res.element = analyzeUrl.evalJS(checkJs, res.element) as StrResponse
                }
            }
            BookList.analyzeBookList(
                res.element.body,
                getBookSource(),
                analyzeUrl,
                res.element.getUrl(),
                variableBook,
                true,
                debugLog = debugger
            ).map {
                it.tocHtml = ""
                it.infoHtml = ""
                it
            }
        } ?: arrayListOf()
    }

    suspend fun exploreBook(
        url: String,
        page: Int? = 1
    ): List<SearchBook> {
        val variableBook = SearchBook()
        variableBook.setUserNameSpace(userNS)
        getBookSource().setUserNameSpace(userNS)
        getBookSource().setLogger(debugger)
        val analyzeUrl = AnalyzeUrl(
            url,
            page = page,
            baseUrl = getBookSource().bookSourceUrl,
            source = getBookSource(),
            ruleData = variableBook,
            headerMapF = getBookSource().getHeaderMap(true),
            debugLog = debugger
        )
        val res = Ref.ObjectRef<StrResponse>()
        res.element = analyzeUrl.getStrResponseAwait()
        getBookSource().loginCheckJs?.let {
            if (it.isNotBlank()) {
                res.element = analyzeUrl.evalJS(it, res.element) as StrResponse
            }
        }
        return BookList.analyzeBookList(
            res.element.body,
            getBookSource(),
            analyzeUrl,
            res.element.getUrl(),
            variableBook,
            false,
            debugLog = debugger
        )
    }

    suspend fun getBookInfo(
        book: Book,
        canReName: Boolean = true
    ): Book {
        book.type = getBookSource().bookSourceType
        book.setUserNameSpace(userNS)
        getBookSource().setUserNameSpace(userNS)
        getBookSource().setLogger(debugger)
        if (!book.infoHtml.isNullOrEmpty()) {
            BookInfo.analyzeBookInfo(
                book,
                book.infoHtml,
                getBookSource(),
                book.bookUrl,
                book.bookUrl,
                canReName
            )
            return book
        }
        val analyzeUrl = AnalyzeUrl(
            book.bookUrl,
            baseUrl = getBookSource().bookSourceUrl,
            source = getBookSource(),
            ruleData = book,
            headerMapF = getBookSource().getHeaderMap(true),
            debugLog = debugger
        )
        val res = Ref.ObjectRef<StrResponse>()
        res.element = analyzeUrl.getStrResponseAwait()
        getBookSource().loginCheckJs?.let { checkJs ->
            if (checkJs.isNotBlank()) {
                res.element = analyzeUrl.evalJS(checkJs, res.element) as StrResponse
            }
        }
        BookInfo.analyzeBookInfo(
            book,
            res.element.body,
            getBookSource(),
            book.bookUrl,
            res.element.getUrl(),
            canReName,
            debugger
        )
        book.tocHtml = null
        return book
    }

    suspend fun getBookInfo(
        bookUrl: String,
        canReName: Boolean = true
    ): Book {
        val book = Book()
        book.bookUrl = bookUrl
        book.origin = getBookSource().bookSourceUrl
        book.originName = getBookSource().bookSourceName
        book.originOrder = getBookSource().customOrder
        book.type = getBookSource().bookSourceType
        book.setUserNameSpace(userNS)
        return getBookInfo(book, canReName)
    }

    suspend fun getChapterList(book: Book): List<BookChapter> {
        book.type = getBookSource().bookSourceType
        book.setUserNameSpace(userNS)
        getBookSource().setUserNameSpace(userNS)
        getBookSource().setLogger(debugger)
        if (book.bookUrl == book.tocUrl) {
            if (!book.tocHtml.isNullOrEmpty()) {
                return BookChapterList.analyzeChapterList(
                    book,
                    book.tocHtml,
                    getBookSource(),
                    book.tocUrl,
                    book.tocUrl
                )
            }
        }
        val analyzeUrl = AnalyzeUrl(
            book.tocUrl,
            baseUrl = book.bookUrl,
            source = getBookSource(),
            ruleData = book,
            headerMapF = getBookSource().getHeaderMap(true),
            debugLog = debugger
        )
        val res = Ref.ObjectRef<StrResponse>()
        res.element = analyzeUrl.getStrResponseAwait()
        getBookSource().loginCheckJs?.let { checkJs ->
            if (checkJs.isNotBlank()) {
                res.element = analyzeUrl.evalJS(checkJs, res.element) as StrResponse
            }
        }
        return BookChapterList.analyzeChapterList(
            book,
            res.element.body,
            getBookSource(),
            book.tocUrl,
            res.element.getUrl(),
            debugger
        )
    }

    suspend fun getBookContent(
        book: Book,
        bookChapter: BookChapter,
        nextChapterUrl: String? = null
    ): String {
        book.setUserNameSpace(userNS)
        getBookSource().setUserNameSpace(userNS)
        getBookSource().setLogger(debugger)
        if (getBookSource().contentRule.content.isNullOrEmpty()) {
            debugger?.log(getBookSource().bookSourceUrl, "⇒正文规则为空,使用章节链接: ${bookChapter.url}")
            return bookChapter.url
        }
        if (bookChapter.isVolume && bookChapter.url.startsWith(bookChapter.title)) {
            debugger?.log(getBookSource().bookSourceUrl, "⇒一级目录正文不解析规则")
            return bookChapter.tag ?: ""
        }
        logger.info("bookChapterUrl: {}", bookChapter.url, bookChapter.getAbsoluteURL())
        val analyzeUrl = AnalyzeUrl(
            bookChapter.getAbsoluteURL(),
            baseUrl = book.tocUrl,
            source = getBookSource(),
            ruleData = book,
            chapter = bookChapter,
            headerMapF = getBookSource().getHeaderMap(true),
            debugLog = debugger
        )
        val res = analyzeUrl.getStrResponseAwait(
            getBookSource().contentRule.webJs,
            getBookSource().contentRule.sourceRegex
        )
        return BookContent.analyzeContent(
            res.body,
            book,
            bookChapter,
            getBookSource(),
            bookChapter.url,
            res.getUrl(),
            nextChapterUrl,
            debugger
        )
    }

    suspend fun preciseSearch(name: String, author: String): Result<Book> = runCatching {
        searchBook(name).firstOrNull {
            it.name == name && it.author == author
        }?.let { searchBook ->
            var book = searchBook.toBook()
            if (book.tocUrl.isBlank()) {
                book = getBookInfo(book, false)
            }
            book
        } ?: throw NoStackTraceException("未搜索到 $name($author) 书籍")
    }
}

private val logger: KLogger = KotlinLogging.logger { }
