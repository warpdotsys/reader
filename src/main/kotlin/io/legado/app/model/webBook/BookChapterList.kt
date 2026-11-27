// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.model.webBook

import io.legado.app.data.entities.Book
import io.legado.app.data.entities.BookChapter
import io.legado.app.data.entities.BookSource
import io.legado.app.data.entities.rule.TocRule
import io.legado.app.exception.TocEmptyException
import io.legado.app.model.DebugLog
import io.legado.app.model.analyzeRule.AnalyzeRule
import io.legado.app.model.analyzeRule.AnalyzeUrl
import io.legado.app.utils.TextUtils
import io.legado.app.utils.isTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

/**
 * 目录列表解析。
 *
 * 分页策略：`nextTocUrl` 只给出一个下一页链接时串行翻页（每翻一页把新链接写回 while 条件），
 * 给出多个时用 [async] 并发抓取再合并。合并后按 `chapterList` 规则的前缀决定是否倒序
 * （`-` 倒序、`+` 顺序），最后用 [LinkedHashSet] 去重并按列表长度回写书籍的章节数。
 */
object BookChapterList {

    suspend fun analyzeChapterList(
        book: Book,
        body: String?,
        bookSource: BookSource,
        baseUrl: String,
        redirectUrl: String,
        debugLog: DebugLog? = null
    ): List<BookChapter> {
        if (body == null) {
            throw Exception("error_get_web_content")
        }
        val chapterList = ArrayList<BookChapter>()
        if (debugLog != null) {
            debugLog.log(bookSource.bookSourceUrl, "≡获取成功:$baseUrl")
        }
        val tocRule = bookSource.tocRule
        val nextUrlList = arrayListOf(redirectUrl)
        var reverse = false
        var listRule = tocRule.chapterList ?: ""
        if (listRule.startsWith("-")) {
            reverse = true
            listRule = listRule.substring(1)
        }
        if (listRule.startsWith("+")) {
            listRule = listRule.substring(1)
        }
        var chapterData = analyzeChapterList(
            book, baseUrl, redirectUrl, body,
            tocRule, listRule, bookSource, true, true, debugLog
        )
        chapterList.addAll(chapterData.first)
        when (chapterData.second.size) {
            0 -> Unit
            1 -> {
                var nextUrl = chapterData.second[0]
                while (nextUrl.isNotEmpty() && !nextUrlList.contains(nextUrl)) {
                    nextUrlList.add(nextUrl)
                    val analyzeUrl = AnalyzeUrl(
                        nextUrl,
                        source = bookSource,
                        ruleData = book,
                        headerMapF = bookSource.getHeaderMap(),
                        debugLog = debugLog
                    )
                    val res = analyzeUrl.getStrResponseAwait()
                    res.body?.let { nextBody ->
                        chapterData = analyzeChapterList(
                            book, nextUrl, nextUrl, nextBody,
                            tocRule, listRule, bookSource, true, false, debugLog
                        )
                        nextUrl = chapterData.second.firstOrNull() ?: ""
                        chapterList.addAll(chapterData.first)
                    }
                }
                debugLog?.log(bookSource.bookSourceUrl, "◇目录总页数:${nextUrlList.size}")
            }

            else -> {
                debugLog?.log(
                    bookSource.bookSourceUrl,
                    "◇并发解析目录,总页数:${chapterData.second.size}"
                )
                withContext(Dispatchers.IO) {
                    val asyncArray = Array(chapterData.second.size) { index ->
                        async(Dispatchers.IO) {
                            val urlStr = chapterData.second[index]
                            val analyzeUrl = AnalyzeUrl(
                                urlStr,
                                source = bookSource,
                                ruleData = book,
                                headerMapF = bookSource.getHeaderMap(),
                                debugLog = debugLog
                            )
                            val res = analyzeUrl.getStrResponseAwait()
                            analyzeChapterList(
                                book, urlStr, res.getUrl(), res.body!!,
                                tocRule, listRule, bookSource, false, false, debugLog
                            ).first
                        }
                    }
                    asyncArray.forEach { coroutine ->
                        chapterList.addAll(coroutine.await())
                    }
                }
            }
        }
        if (chapterList.isEmpty()) {
            throw TocEmptyException("目录为空")
        }
        if (!reverse) {
            chapterList.reverse()
        }
        coroutineContext.ensureActive()
        val lh = LinkedHashSet(chapterList)
        val list = ArrayList(lh)
        list.reverse()
        debugLog?.let {
            it.log(book.origin, "◇目录总数:${list.size}")
        }
        coroutineContext.ensureActive()
        list.forEachIndexed { index, bookChapter ->
            bookChapter.index = index
        }
        if (list.size > 0) {
            book.latestChapterTitle = list.last().title
        }
        if (book.totalChapterNum < list.size) {
            book.lastCheckCount = list.size - book.totalChapterNum
        }
        book.totalChapterNum = list.size
        coroutineContext.ensureActive()
        return list
    }

    private suspend fun analyzeChapterList(
        book: Book,
        baseUrl: String,
        redirectUrl: String,
        body: String,
        tocRule: TocRule,
        listRule: String,
        bookSource: BookSource,
        getNextUrl: Boolean = true,
        log: Boolean = false,
        debugLog: DebugLog? = null
    ): Pair<List<BookChapter>, List<String>> {
        val analyzeRule = AnalyzeRule(book, bookSource, debugLog)
        analyzeRule.setContent(body).setBaseUrl(baseUrl)
        analyzeRule.setRedirectUrl(redirectUrl)
        val chapterList = ArrayList<BookChapter>()
        if (log) {
            debugLog?.log(bookSource.bookSourceUrl, "┌获取目录列表")
        }
        val elements = analyzeRule.getElements(listRule)
        if (log) {
            debugLog?.log(bookSource.bookSourceUrl, "└列表大小:${elements.size}")
        }
        val nextUrlList = ArrayList<String>()
        val nextTocRule = tocRule.nextTocUrl
        if (getNextUrl && !nextTocRule.isNullOrEmpty()) {
            if (log) {
                debugLog?.log(bookSource.bookSourceUrl, "┌获取目录下一页列表")
            }
            analyzeRule.getStringList(nextTocRule, isUrl = true)?.let {
                it.forEach { item ->
                    if (item == redirectUrl) return@forEach
                    nextUrlList.add(item)
                }
            }
            if (log) {
                debugLog?.log(
                    bookSource.bookSourceUrl,
                    "└" + TextUtils.join("，\n", nextUrlList)
                )
            }
        }
        coroutineContext.ensureActive()
        if (elements.isNotEmpty()) {
            if (log) {
                debugLog?.log(bookSource.bookSourceUrl, "┌解析目录列表")
            }
            val nameRule = analyzeRule.splitSourceRule(tocRule.chapterName)
            val urlRule = analyzeRule.splitSourceRule(tocRule.chapterUrl)
            val vipRule = analyzeRule.splitSourceRule(tocRule.isVip)
            val upTimeRule = analyzeRule.splitSourceRule(tocRule.updateTime)
            val isVolumeRule = analyzeRule.splitSourceRule(tocRule.isVolume)
            elements.forEachIndexed { index, item ->
                coroutineContext.ensureActive()
                analyzeRule.setContent(item)
                val bookChapter = BookChapter(
                    baseUrl = redirectUrl,
                    bookUrl = book.bookUrl
                )
                analyzeRule.chapter = bookChapter
                bookChapter.title = analyzeRule.getString(nameRule)
                bookChapter.url = analyzeRule.getString(urlRule)
                bookChapter.tag = analyzeRule.getString(upTimeRule)
                bookChapter.setUserNameSpace(book.getUserNameSpace())
                val isVolume = analyzeRule.getString(isVolumeRule)
                bookChapter.isVolume = false
                if (isVolume.isTrue()) {
                    bookChapter.isVolume = true
                }
                if (bookChapter.url.isEmpty()) {
                    if (bookChapter.isVolume) {
                        bookChapter.url = bookChapter.title + index
                        if (log) {
                            debugLog?.log(
                                bookSource.bookSourceUrl,
                                "⇒一级目录" + index + "未获取到url,使用标题替代"
                            )
                        }
                    } else {
                        bookChapter.url = baseUrl
                        if (log) {
                            debugLog?.log(
                                bookSource.bookSourceUrl,
                                "⇒目录" + index + "未获取到url,使用baseUrl替代"
                            )
                        }
                    }
                }
                if (bookChapter.title.isNotEmpty()) {
                    val isVip = analyzeRule.getString(vipRule)
                    if (isVip.isTrue()) {
                        bookChapter.title = "🔒" + bookChapter.title
                    }
                    chapterList.add(bookChapter)
                } else {
                    if (log) {
                        debugLog?.log(bookSource.bookSourceUrl, "章节名为空")
                    }
                }
            }
            if (log) {
                debugLog?.log(bookSource.bookSourceUrl, "└目录列表解析完成")
            }
            if (chapterList.size > 0) {
                if (log) {
                    debugLog?.log(bookSource.bookSourceUrl, "≡首章信息")
                }
                if (log) {
                    debugLog?.log(
                        bookSource.bookSourceUrl,
                        "◇章节名称:" + chapterList[0].title
                    )
                }
                if (log) {
                    debugLog?.log(
                        bookSource.bookSourceUrl,
                        "◇章节链接:" + chapterList[0].url
                    )
                }
                if (log) {
                    debugLog?.log(
                        bookSource.bookSourceUrl,
                        "◇章节信息:" + chapterList[0].tag
                    )
                }
                if (log) {
                    debugLog?.log(
                        bookSource.bookSourceUrl,
                        "◇是否卷名:" + chapterList[0].isVolume
                    )
                }
            } else {
                if (log) {
                    debugLog?.log(bookSource.bookSourceUrl, "章节列表为空")
                }
            }
        }
        return Pair(chapterList, nextUrlList)
    }
}
