// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.model.webBook

import io.legado.app.data.entities.Book
import io.legado.app.data.entities.BookChapter
import io.legado.app.data.entities.BookSource
import io.legado.app.data.entities.rule.ContentRule
import io.legado.app.model.DebugLog
import io.legado.app.model.analyzeRule.AnalyzeRule
import io.legado.app.model.analyzeRule.AnalyzeUrl
import io.legado.app.utils.HtmlFormatter
import io.legado.app.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext
import kotlin.jvm.internal.Ref

/**
 * 正文解析。
 *
 * 先按正文规则取第一页；`nextContentUrl` 只给一个链接时串行翻页（遇到下一章地址就停），
 * 给出多个时用 [async] 并发抓取再按顺序拼接。最后套 `replaceRegex` 做一次替换。
 */
object BookContent {

    suspend fun analyzeContent(
        body: String?,
        book: Book,
        bookChapter: BookChapter,
        bookSource: BookSource,
        baseUrl: String,
        redirectUrl: String,
        nextChapterUrl: String? = null,
        debugLog: DebugLog? = null
    ): String {
        if (body == null) {
            throw Exception("error_get_web_content")
        }
        debugLog?.log(bookSource.bookSourceUrl, "≡获取成功:$baseUrl")
        val mNextChapterUrl = if (nextChapterUrl.isNullOrEmpty()) null else nextChapterUrl
        val content = StringBuilder()
        val nextUrlList = arrayListOf(redirectUrl)
        val contentRule = bookSource.contentRule
        val analyzeRule = AnalyzeRule(book, bookSource, debugLog).setContent(body, baseUrl)
        analyzeRule.setRedirectUrl(redirectUrl)
        analyzeRule.chapter = bookChapter
        analyzeRule.nextChapterUrl = mNextChapterUrl
        coroutineContext.ensureActive()
        val contentData = Ref.ObjectRef<Pair<String, List<String>>>()
        contentData.element = analyzeContent(
            book, baseUrl, redirectUrl, body, contentRule, bookChapter, bookSource,
            mNextChapterUrl, debugLog = debugLog
        )
        content.append(contentData.element.first)
        if (contentData.element.second.size == 1) {
            val nextUrl = Ref.ObjectRef<String>()
            nextUrl.element = contentData.element.second[0]
            while (nextUrl.element.isNotEmpty() && !nextUrlList.contains(nextUrl.element)) {
                if (!mNextChapterUrl.isNullOrEmpty() &&
                    NetworkUtils.getAbsoluteURL(redirectUrl, nextUrl.element) ==
                    NetworkUtils.getAbsoluteURL(redirectUrl, mNextChapterUrl)
                ) {
                    break
                }
                nextUrlList.add(nextUrl.element)
                coroutineContext.ensureActive()
                val analyzeUrl = AnalyzeUrl(
                    nextUrl.element,
                    source = bookSource, ruleData = book, headerMapF = bookSource.getHeaderMap(),
                    debugLog = debugLog
                )
                val res = analyzeUrl.getStrResponseAwait()
                res.body?.let { nextBody ->
                    contentData.element = analyzeContent(
                        book, nextUrl.element, res.getUrl(), nextBody, contentRule, bookChapter,
                        bookSource, mNextChapterUrl, printLog = false, debugLog = debugLog
                    )
                    nextUrl.element = if (contentData.element.second.isNotEmpty()) {
                        contentData.element.second[0]
                    } else {
                        ""
                    }
                    content.append("\n").append(contentData.element.first)
                }
            }
            debugLog?.log(bookSource.bookSourceUrl, "◇本章总页数:${nextUrlList.size}")
        }
        if (contentData.element.second.size > 1) {
            coroutineContext.ensureActive()
            debugLog?.log(
                bookSource.bookSourceUrl,
                "◇并发解析正文,总页数:${contentData.element.second.size}"
            )
            withContext(Dispatchers.IO) {
                val asyncArray = Array(contentData.element.second.size) { index ->
                    async(Dispatchers.IO) {
                        val urlStr = contentData.element.second[index]
                        val analyzeUrl = AnalyzeUrl(
                            urlStr,
                            source = bookSource, ruleData = book, headerMapF = bookSource.getHeaderMap(),
                            debugLog = debugLog
                        )
                        val res = analyzeUrl.getStrResponseAwait()
                        analyzeContent(
                            book, urlStr, res.getUrl(), res.body!!, contentRule, bookChapter,
                            bookSource, mNextChapterUrl, printLog = false, debugLog = debugLog
                        ).first
                    }
                }
                asyncArray.forEach { coroutine ->
                    coroutineContext.ensureActive()
                    content.append("\n").append(coroutine.await())
                }
            }
        }
        var contentStr = content.toString()
        val replaceRegex = contentRule.replaceRegex
        if (!replaceRegex.isNullOrEmpty()) {
            contentStr = analyzeRule.getString(replaceRegex, contentStr)
        }
        debugLog?.log(bookSource.bookSourceUrl, "┌获取章节名称")
        debugLog?.log(bookSource.bookSourceUrl, "└${bookChapter.title}")
        debugLog?.log(bookSource.bookSourceUrl, "┌获取正文内容 (长度：${contentStr.length})")
        if (contentStr.length > 300) {
            debugLog?.log(
                bookSource.bookSourceUrl,
                "└\n${contentStr.substring(0, 150)} ... " +
                        contentStr.substring(contentStr.length - 150)
            )
        } else {
            debugLog?.log(bookSource.bookSourceUrl, "└\n$contentStr")
        }
        return contentStr
    }

    @Throws(Exception::class)
    private fun analyzeContent(
        book: Book,
        baseUrl: String,
        redirectUrl: String,
        body: String,
        contentRule: ContentRule,
        chapter: BookChapter,
        bookSource: BookSource,
        nextChapterUrl: String?,
        printLog: Boolean = true,
        debugLog: DebugLog? = null
    ): Pair<String, List<String>> {
        val analyzeRule = AnalyzeRule(book, bookSource, debugLog)
        analyzeRule.setContent(body, baseUrl)
        analyzeRule.chapter = chapter
        val rUrl = analyzeRule.setRedirectUrl(redirectUrl)
        analyzeRule.nextChapterUrl = nextChapterUrl
        val nextUrlList = arrayListOf<String>()
        // 制品在这里第二次调用 setChapter——字节码逐条比对确认不是反编译噪声，照抄。
        analyzeRule.chapter = chapter
        var content = analyzeRule.getString(contentRule.content)
        content = HtmlFormatter.formatKeepImg(content, rUrl)
        val nextUrlRule = contentRule.nextContentUrl
        if (!nextUrlRule.isNullOrEmpty()) {
            if (printLog) {
                debugLog?.log(bookSource.bookSourceUrl, "┌获取正文下一页链接")
            }
            analyzeRule.getStringList(nextUrlRule, isUrl = true)?.let {
                nextUrlList.addAll(it)
            }
            if (printLog) {
                debugLog?.log(bookSource.bookSourceUrl, "└${nextUrlList.joinToString("，")}")
            }
        }
        return Pair(content, nextUrlList)
    }
}
