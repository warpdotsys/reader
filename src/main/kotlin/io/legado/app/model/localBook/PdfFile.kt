// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.model.localBook

import io.legado.app.data.entities.Book
import io.legado.app.data.entities.BookChapter
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList

class PdfFile(var book: Book) {

    var info: Map<String, @JvmSuppressWildcards Any>? = null

    var cover: InputStream? = null

    private fun parseBookInfo(): Pair<Map<String, @JvmSuppressWildcards Any>?, InputStream?> {
        return Pair(info, cover)
    }

    private fun upBookInfo() {
        val result = parseBookInfo()
        if (result.first != null) {
            val bookInfo = result.first!!
            val info = bookInfo["ComicInfo"] as Map<*, *>?
            book.name = info?.get("Title") as String? ?: book.name
            book.author = info?.get("Writer") as String? ?: book.author
        }
        updateCover()
    }

    private fun updateCover() {
    }

    private fun getContent(chapter: BookChapter): String? {
        return ""
    }

    private fun getChapterList(): ArrayList<BookChapter> {
        if (book.tocUrl.isEmpty()) {
            book.tocUrl = "page"
        }
        return if (book.tocUrl == "page") {
            getChapterListByPage()
        } else {
            getChapterListByOutline()
        }
    }

    private fun getChapterListByPage(): ArrayList<BookChapter> {
        val chapterList = ArrayList<BookChapter>()
        val document = PDDocument.load(book.getLocalFile())
        for (pageIndex in 0 until document.numberOfPages) {
            val name = "output-$pageIndex.png"
            val chapter = BookChapter()
            chapter.title = name
            chapter.index = pageIndex
            chapter.bookUrl = book.bookUrl
            chapter.url = name
            chapter.start = pageIndex.toLong()
            chapter.end = pageIndex.toLong()
            chapterList.add(chapter)
        }
        book.latestChapterTitle = chapterList.lastOrNull()?.title
        book.totalChapterNum = chapterList.size
        try {
            document.close()
        } catch (e: IOException) {
        }
        return chapterList
    }

    private fun getChapterListByOutline(): ArrayList<BookChapter> {
        val chapterList = ArrayList<BookChapter>()
        val document = PDDocument.load(book.getLocalFile())
        val outline: PDDocumentOutline? = document.documentCatalog.documentOutline
        if (outline == null) {
            return chapterList
        }
        processOutline(document, chapterList, outline)
        if (chapterList.size > 0) {
            chapterList[chapterList.size - 1].end = document.numberOfPages.toLong()
        }
        try {
            document.close()
        } catch (e: IOException) {
        }
        return chapterList
    }

    private fun processOutline(
        document: PDDocument,
        chapterList: ArrayList<BookChapter>,
        outline: PDOutlineNode
    ) {
        var current: PDOutlineItem? = outline.firstChild
        while (current != null) {
            val page: PDPage = current.findDestinationPage(document)
            val pageIndex = document.documentCatalog.pages.indexOf(page)
            if (chapterList.size == 0 && pageIndex >= 1) {
                val chapter = BookChapter()
                chapter.title = "首章"
                chapter.index = 0
                chapter.bookUrl = book.bookUrl
                chapter.url = "chapter-0"
                chapter.start = 0L
                chapter.end = pageIndex.toLong()
                chapterList.add(chapter)
            }
            if (chapterList.size > 0) {
                val start = chapterList[chapterList.size - 1].start
                if (start != null && start == pageIndex.toLong()) {
                    current = current.nextSibling
                    continue
                }
                val chapter = BookChapter()
                chapter.title = current.title
                chapter.index = chapterList.size
                chapter.bookUrl = book.bookUrl
                chapter.url = "chapter-${chapterList.size}"
                chapter.start = pageIndex.toLong()
                chapterList[chapterList.size - 1].end = pageIndex - 1L
                chapterList.add(chapter)
            }
            if (current.hasChildren()) {
                processOutline(document, chapterList, current)
            }
            current = current.nextSibling
        }
    }

    companion object {

        private var cFile: PdfFile? = null

        @Synchronized
        private fun getPdfFile(book: Book): PdfFile {
            if (cFile?.book?.bookUrl != book.bookUrl) {
                cFile = PdfFile(book)
                return cFile!!
            }
            cFile?.book = book
            return cFile!!
        }

        @Synchronized
        fun getChapterList(book: Book): ArrayList<BookChapter> {
            return getPdfFile(book).getChapterList()
        }

        @Synchronized
        fun getContent(book: Book, chapter: BookChapter): String? {
            return getPdfFile(book).getContent(chapter)
        }

        @Synchronized
        fun upBookInfo(book: Book, onlyCover: Boolean = false) {
            if (onlyCover) {
                getPdfFile(book).updateCover()
                return
            }
            getPdfFile(book).upBookInfo()
        }
    }
}
