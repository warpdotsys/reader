// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.model.localBook

import io.legado.app.data.entities.Book
import io.legado.app.data.entities.BookChapter
import io.legado.app.utils.FileUtils
import io.legado.app.utils.MD5Utils
import me.ag2s.umdlib.domain.UmdBook
import me.ag2s.umdlib.umd.UmdReader
import java.io.File
import java.io.InputStream
import java.nio.file.Paths
import java.util.ArrayList

class UmdFile(var book: Book) {

    private var umdBook: UmdBook? = null

    init {
        try {
            getUmdBook()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getUmdBook(): UmdBook? {
        if (umdBook != null) {
            return umdBook
        }
        umdBook = readUmd()
        return umdBook
    }

    private fun readUmd(): UmdBook {
        val input = LocalBook.getBookInputStream(book)
        return UmdReader().read(input)
    }

    private fun upBookInfo() {
        if (getUmdBook() == null) {
            uFile = null
            book.intro = "书籍导入异常"
            return
        }
        val hd = getUmdBook()!!.header
        book.name = hd.title
        book.author = hd.author
        book.kind = hd.bookType
        updateCover()
    }

    private fun updateCover() {
        if (getUmdBook() == null) {
            uFile = null
            return
        }
        val coverFile = MD5Utils.md5Encode16(book.bookUrl) + ".jpg"
        val relativeCoverUrl = Paths.get("assets", book.getUserNameSpace(), "covers", coverFile).toString()
        book.coverUrl = "/" + relativeCoverUrl.replace("\\", "/")
        val coverUrl = Paths.get(book.workRoot(), "storage", relativeCoverUrl).toString()
        if (!File(coverUrl).exists()) {
            val coverData = getUmdBook()?.cover?.coverData
            if (coverData != null) {
                FileUtils.writeBytes(coverUrl, coverData)
            }
        }
    }

    private fun getContent(chapter: BookChapter): String? {
        return getUmdBook()?.chapters?.getContentString(chapter.index)
    }

    private fun getChapterList(): ArrayList<BookChapter> {
        val chapterList = ArrayList<BookChapter>()
        getUmdBook()?.chapters?.titles?.forEachIndexed { index, _ ->
            val title = getUmdBook()!!.chapters.getTitle(index)
            val chapter = BookChapter()
            chapter.title = title
            chapter.index = index
            chapter.bookUrl = book.bookUrl
            chapter.url = index.toString()
            System.out.println("UMD" + chapter.url)
            chapterList.add(chapter)
        }
        book.latestChapterTitle = chapterList.lastOrNull()?.title
        book.totalChapterNum = chapterList.size
        return chapterList
    }

    private fun getImage(href: String): InputStream? {
        return null
    }

    companion object {

        private var uFile: UmdFile? = null

        @Synchronized
        private fun getUFile(book: Book): UmdFile {
            if (uFile == null || uFile?.book?.bookUrl != book.bookUrl) {
                uFile = UmdFile(book)
                return uFile!!
            }
            uFile?.book = book
            return uFile!!
        }

        @Synchronized
        fun getChapterList(book: Book): ArrayList<BookChapter> {
            return getUFile(book).getChapterList()
        }

        @Synchronized
        fun getContent(book: Book, chapter: BookChapter): String? {
            return getUFile(book).getContent(chapter)
        }

        @Synchronized
        fun getImage(book: Book, href: String): InputStream? {
            return getUFile(book).getImage(href)
        }

        @Synchronized
        fun upBookInfo(book: Book, onlyCover: Boolean = false) {
            if (onlyCover) {
                getUFile(book).updateCover()
                return
            }
            getUFile(book).upBookInfo()
        }
    }
}
