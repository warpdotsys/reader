// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.model

import io.legado.app.data.entities.Book
import io.legado.app.data.entities.BookChapter
import io.legado.app.data.entities.BookSource
import io.legado.app.model.webBook.WebBook
import io.legado.app.utils.GSON
import io.legado.app.utils.HtmlFormatter
import io.legado.app.utils.isAbsUrl
import mu.KLogger
import mu.KotlinLogging
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val logger: KLogger = KotlinLogging.logger { }

class Debugger(val logMsg: (String) -> Unit) : DebugLog {

    private val debugTimeFormat: SimpleDateFormat =
        SimpleDateFormat("[mm:ss.SSS]", Locale.getDefault())

    private var startTime: Long = System.currentTimeMillis()

    fun log(sourceUrl: String?, msg: String?) {
        log(sourceUrl, msg, false)
    }

    override fun log(message: String) {
        val time = debugTimeFormat.format(Date(System.currentTimeMillis() - startTime))
        logMsg("$time $message")
    }

    override fun log(sourceUrl: String?, msg: String?, isHtml: Boolean) {
        if (sourceUrl == null || msg == null) {
            return
        }
        logger.info("sourceUrl: {}, msg: {}", sourceUrl, msg)
        var printMsg = msg
        if (isHtml) {
            printMsg = HtmlFormatter.format(msg)
        }
        val time = debugTimeFormat.format(Date(System.currentTimeMillis() - startTime))
        printMsg = "$time $printMsg"
        logMsg(printMsg)
    }

    suspend fun startDebug(webBook: WebBook, key: String) {
        val bookSource: BookSource = webBook.getBookSource()
        webBook.debugLogger = this
        startTime = System.currentTimeMillis()
        when {
            key.isAbsUrl() -> {
                val book = Book(
                    origin = bookSource.bookSourceUrl,
                    bookUrl = key
                )
                log(bookSource.bookSourceUrl, "⇒开始访问详情页:$key")
                infoDebug(webBook, book)
            }

            key.contains("::") -> {
                val url = key.substringAfter("::")
                log(bookSource.bookSourceUrl, "⇒开始访问发现页:$url")
                exploreDebug(webBook, url)
            }

            key.startsWith("++") -> {
                val url = key.substring(2)
                val book = Book(origin = bookSource.bookSourceUrl).apply {
                    tocUrl = url
                }
                log(bookSource.bookSourceUrl, "⇒开始访目录页:$url")
                tocDebug(webBook, book)
            }

            key.startsWith("--") -> {
                val url = key.substring(2)
                val book = Book(origin = bookSource.bookSourceUrl)
                val chapter = BookChapter().apply {
                    title = "调试"
                    this.url = url
                }
                log(bookSource.bookSourceUrl, "⇒开始访正文页:$url")
                contentDebug(webBook, book, chapter, null)
            }

            else -> {
                log(bookSource.bookSourceUrl, "⇒开始搜索关键字:$key")
                searchDebug(webBook, key)
            }
        }
    }

    private suspend fun exploreDebug(webBook: WebBook, url: String) {
        webBook.debugLogger = this
        log(msg = "︾开始解析发现页")
        runCatching {
            webBook.exploreBook(url, 1)
        }.onSuccess { exploreBooks ->
            if (exploreBooks.isNotEmpty()) {
                log("┌发现结果列表")
                log("└${GSON.toJson(exploreBooks)}")
                log(webBook.sourceUrl, "︽发现页解析完成\n\n")
                infoDebug(webBook, exploreBooks[0].toBook())
            } else {
                log(webBook.sourceUrl, "︽未获取到书籍")
            }
        }.onFailure {
            log(webBook.sourceUrl, "Error: ${it.localizedMessage}")
            throw it
        }
    }

    private suspend fun searchDebug(webBook: WebBook, key: String) {
        webBook.debugLogger = this
        log(msg = "︾开始解析搜索页")
        runCatching {
            webBook.searchBook(key, 1)
        }.onSuccess { searchBooks ->
            if (searchBooks.isNotEmpty()) {
                log("┌搜索结果列表")
                log("└${GSON.toJson(searchBooks)}")
                log(webBook.sourceUrl, "︽搜索页解析完成\n\n")
                infoDebug(webBook, searchBooks[0].toBook())
            } else {
                log(webBook.sourceUrl, "︽未获取到书籍")
            }
        }.onFailure {
            log(webBook.sourceUrl, "Error: ${it.localizedMessage}")
            throw it
        }
    }

    private suspend fun infoDebug(webBook: WebBook, book: Book) {
        webBook.debugLogger = this
        log(msg = "︾开始解析详情页")
        runCatching {
            webBook.getBookInfo(book)
        }.onSuccess {
            log("┌书籍详情")
            log("└${GSON.toJson(it)}")
            log(webBook.sourceUrl, "︽详情页解析完成\n\n")
            tocDebug(webBook, it)
        }.onFailure {
            log(webBook.sourceUrl, "Error: ${it.localizedMessage}")
            throw it
        }
    }

    private suspend fun tocDebug(webBook: WebBook, book: Book) {
        webBook.debugLogger = this
        log(msg = "︾开始解析目录页")
        runCatching {
            webBook.getChapterList(book)
        }.onSuccess { chapterList ->
            chapterList?.takeIf { it.isNotEmpty() }?.let {
                log("┌目录列表")
                log("└${GSON.toJson(it)}")
                log(webBook.sourceUrl, "︽目录页解析完成\n\n")
                val nextChapterUrl = if (it.size > 1) it[1].url else null
                contentDebug(webBook, book, it[0], nextChapterUrl)
            } ?: log(webBook.sourceUrl, "︽目录列表为空")
        }.onFailure {
            log(webBook.sourceUrl, "Error: ${it.localizedMessage}")
            throw it
        }
    }

    private suspend fun contentDebug(
        webBook: WebBook,
        book: Book,
        bookChapter: BookChapter,
        nextChapterUrl: String?
    ) {
        webBook.debugLogger = this
        log(webBook.sourceUrl, "︾开始解析正文页")
        runCatching {
            webBook.getBookContent(book, bookChapter, nextChapterUrl)
        }.onSuccess {
            log("┌正文内容")
            log("└$it")
            log(webBook.sourceUrl, "︽正文页解析完成")
        }.onFailure {
            log(webBook.sourceUrl, "Error: ${it.localizedMessage}")
        }
    }
}
