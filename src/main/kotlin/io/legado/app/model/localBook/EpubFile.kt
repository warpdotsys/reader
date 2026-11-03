// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.model.localBook

import io.legado.app.data.entities.Book
import io.legado.app.data.entities.BookChapter
import io.legado.app.utils.FileUtils
import io.legado.app.utils.HtmlFormatter
import io.legado.app.utils.MD5Utils
import me.ag2s.epublib.domain.EpubBook
import me.ag2s.epublib.domain.Resource
import me.ag2s.epublib.domain.SpineReference
import me.ag2s.epublib.epub.EpubReader
import mu.KLogger
import mu.KotlinLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import java.nio.file.Paths
import java.util.ArrayList
import java.util.LinkedHashMap
import java.util.zip.ZipFile

private val logger: KLogger = KotlinLogging.logger { }

class EpubFile(var book: Book) {

    private var mCharset: Charset = Charset.defaultCharset()

    private var epubBook: EpubBook? = null

    init {
        try {
            getEpubBook()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getEpubBook(): EpubBook? {
        if (epubBook != null) {
            return epubBook
        }
        epubBook = readEpub()
        return epubBook
    }

    private fun readEpub(): EpubBook? {
        return try {
            val file = book.getLocalFile()
            EpubReader().readEpubLazy(ZipFile(file), "utf-8")
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getContent(chapter: BookChapter): String? {
        if (chapter.url.contains("titlepage.xhtml")) {
            return "<img src=\"cover.jpeg\" />"
        }
        val epubBook = getEpubBook() ?: return null
        val nextUrl = chapter.getVariable("nextUrl")
        val startFragmentId = chapter.startFragmentId
        val endFragmentId = chapter.endFragmentId
        val elements = Elements()
        var isChapter = false
        for (res in epubBook.contents) {
            if (chapter.url.substringBeforeLast("#") == res.href) {
                elements.add(getBody(res, startFragmentId, endFragmentId))
                isChapter = true
                if (nextUrl == null) break
                if (res.href == nextUrl.substringBeforeLast("#")) break
                continue
            }
            if (!isChapter) continue
            if (res.href == nextUrl?.substringBeforeLast("#")) break
            elements.add(getBody(res, startFragmentId, endFragmentId))
        }
        var html = elements.outerHtml()
        if (book.getDelTag(4L)) {
            html = html.replace(Regex("<ruby>\\s?([\\u4e00-\\u9fa5])\\s?.*?</ruby>"), "$1")
        }
        return HtmlFormatter.formatKeepImg(html)
    }

    private fun getBody(res: Resource, startFragmentId: String?, endFragmentId: String?): Element {
        val body = Jsoup.parse(String(res.data, mCharset)).body()
        if (!startFragmentId.isNullOrBlank()) {
            body.getElementById(startFragmentId)?.previousElementSiblings()?.remove()
        }
        if (!endFragmentId.isNullOrBlank() && endFragmentId != startFragmentId) {
            body.getElementById(endFragmentId)?.let {
                it.nextElementSiblings().remove()
                it.remove()
            }
        }
        if (book.getDelTag(2L)) {
            body.getElementsByTag("h1").remove()
            body.getElementsByTag("h2").remove()
            body.getElementsByTag("h3").remove()
            body.getElementsByTag("h4").remove()
            body.getElementsByTag("h5").remove()
            body.getElementsByTag("h6").remove()
        }
        val children = body.children()
        children.select("script").remove()
        children.select("style").remove()
        return body
    }

    private fun getImage(href: String): InputStream? {
        val abHref = href.replace("../", "")
        return getEpubBook()?.resources?.getByHref(abHref)?.inputStream
    }

    private fun upBookInfo() {
        if (getEpubBook() == null) {
            eFile = null
            book.intro = "书籍导入异常"
        } else {
            val metadata = getEpubBook()!!.metadata
            book.name = metadata.firstTitle
            if (book.name.isEmpty()) {
                book.name = book.originName.replace(".epub", "")
            }
            if (metadata.authors.size > 0) {
                book.author = metadata.authors[0].toString().replace(Regex("^, |, $"), "")
            }
            if (metadata.descriptions.size > 0) {
                book.intro = Jsoup.parse(metadata.descriptions[0]).text()
            }
            updateCover()
        }
    }

    fun updateCover() {
        val coverFile = MD5Utils.md5Encode16(book.bookUrl) + ".jpg"
        val relativeCoverUrl = Paths.get("assets", book.getUserNameSpace(), "covers", coverFile).toString()
        book.coverUrl = "/" + relativeCoverUrl.replace("\\", "/")
        val coverUrl = Paths.get(book.workRoot(), "storage", relativeCoverUrl).toString()
        if (!File(coverUrl).exists()) {
            val coverData = getEpubBook()?.coverImage?.data
            if (coverData != null) {
                FileUtils.writeBytes(coverUrl, coverData)
            }
        }
    }

    fun getChapterListBySpine(): ArrayList<BookChapter> {
        val chapterList = ArrayList<BookChapter>()
        getEpubBook()?.spine?.spineReferences?.forEachIndexed { index, spinResource ->
            val resource = spinResource.resource
            var title = resource.title
            if (title.isNullOrEmpty()) {
                try {
                    val doc = Jsoup.parse(String(resource.data, mCharset))
                    val elements = doc.getElementsByTag("title")
                    if (elements.size > 0) {
                        title = elements[0].text()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            val chapter = BookChapter()
            chapter.index = index
            chapter.bookUrl = book.bookUrl
            chapter.url = resource.href
            if (index == 0 && title.isNullOrEmpty()) {
                chapter.title = "封面"
            } else {
                chapter.title = title ?: ""
            }
            chapterList.add(chapter)
        }
        book.latestChapterTitle = chapterList.lastOrNull()?.title
        book.totalChapterNum = chapterList.size
        return chapterList
    }

    fun getChapterList(): ArrayList<BookChapter> {
        val chapterList = ArrayList<BookChapter>()
        getEpubBook()?.tableOfContents?.allUniqueResources?.forEachIndexed { index, resource ->
            var title = resource.title
            if (title.isNullOrEmpty()) {
                try {
                    val doc = Jsoup.parse(String(resource.data, mCharset))
                    val elements = doc.getElementsByTag("title")
                    if (elements.size > 0) {
                        title = elements[0].text()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            val chapter = BookChapter()
            chapter.index = index
            chapter.bookUrl = book.bookUrl
            chapter.url = resource.href
            if (index == 0 && title.isNullOrEmpty()) {
                chapter.title = "封面"
            } else {
                chapter.title = title ?: ""
            }
            chapterList.add(chapter)
        }
        book.latestChapterTitle = chapterList.lastOrNull()?.title
        book.totalChapterNum = chapterList.size
        return chapterList
    }

    fun getChapterListBySpinAndToc(useTocTitle: Boolean = false): ArrayList<BookChapter> {
        val tocChapterList = getChapterList()
        val spinChapterList = getChapterListBySpine()
        if (spinChapterList.size == 0) {
            return tocChapterList
        }
        if (tocChapterList.size == 0) {
            return spinChapterList
        }
        val titleMap = LinkedHashMap<String, BookChapter>()
        for (i in tocChapterList.indices) {
            titleMap[tocChapterList[i].url] = tocChapterList[i]
        }
        for (i in spinChapterList.indices) {
            val chapter = spinChapterList[i]
            val tocChapter = titleMap[chapter.url] ?: continue
            if (tocChapter.title.isEmpty()) continue
            if (!useTocTitle && chapter.title.isNotEmpty()) continue
            chapter.title = tocChapter.title
        }
        book.latestChapterTitle = spinChapterList.lastOrNull()?.title
        book.totalChapterNum = spinChapterList.size
        return spinChapterList
    }

    fun getChapterListByTocAndSpin(useSpinTitle: Boolean = false): ArrayList<BookChapter> {
        val tocChapterList = getChapterList()
        val spinChapterList = getChapterListBySpine()
        if (tocChapterList.size == 0) {
            return spinChapterList
        }
        if (spinChapterList.size == 0) {
            return tocChapterList
        }
        val titleMap = LinkedHashMap<String, BookChapter>()
        for (i in spinChapterList.indices) {
            titleMap[spinChapterList[i].url] = spinChapterList[i]
        }
        for (i in tocChapterList.indices) {
            val chapter = tocChapterList[i]
            val tocChapter = titleMap[chapter.url] ?: continue
            if (tocChapter.title.isEmpty()) continue
            if (!useSpinTitle && chapter.title.isNotEmpty()) continue
            chapter.title = tocChapter.title
        }
        book.latestChapterTitle = tocChapterList.lastOrNull()?.title
        book.totalChapterNum = tocChapterList.size
        return tocChapterList
    }

    companion object {

        private var eFile: EpubFile? = null

        @Synchronized
        private fun getEFile(book: Book): EpubFile {
            if (eFile?.book?.bookUrl != book.bookUrl) {
                eFile = EpubFile(book)
                return eFile!!
            }
            eFile?.book = book
            return eFile!!
        }

        @Synchronized
        fun getChapterList(book: Book): ArrayList<BookChapter> {
            if (book.tocUrl.isEmpty()) {
                book.tocUrl = "spin+toc"
            }
            val epubFile = getEFile(book)
            return when (book.tocUrl) {
                "toc" -> {
                    logger.info("epubFile.getChapterList")
                    epubFile.getChapterList()
                }
                "spin" -> {
                    logger.info("epubFile.getChapterListBySpine")
                    epubFile.getChapterListBySpine()
                }
                "spin<toc" -> {
                    logger.info("epubFile.getChapterListBySpinAndToc true")
                    epubFile.getChapterListBySpinAndToc(true)
                }
                "spin+toc" -> {
                    logger.info("epubFile.getChapterListBySpinAndToc")
                    epubFile.getChapterListBySpinAndToc()
                }
                "toc+spin" -> {
                    logger.info("epubFile.getChapterListByTocAndSpin")
                    epubFile.getChapterListByTocAndSpin()
                }
                "toc<spin" -> {
                    logger.info("epubFile.getChapterListByTocAndSpin true")
                    epubFile.getChapterListByTocAndSpin(true)
                }
                else -> {
                    logger.info("epubFile.getChapterListBySpinAndToc")
                    epubFile.getChapterListBySpinAndToc()
                }
            }
        }

        @Synchronized
        fun getContent(book: Book, chapter: BookChapter): String? {
            return getEFile(book).getContent(chapter)
        }

        @Synchronized
        fun getImage(book: Book, href: String): InputStream? {
            return getEFile(book).getImage(href)
        }

        @Synchronized
        fun upBookInfo(book: Book, onlyCover: Boolean = false) {
            if (onlyCover) {
                getEFile(book).updateCover()
                return
            }
            getEFile(book).upBookInfo()
        }
    }
}
