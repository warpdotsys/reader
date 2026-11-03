// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.model.localBook

import io.legado.app.data.entities.Book
import io.legado.app.data.entities.BookChapter
import io.legado.app.utils.FileUtils
import io.legado.app.utils.MD5Utils
import io.legado.app.utils.XmlUtils
import java.io.File
import java.io.InputStream
import java.nio.file.Paths
import java.util.ArrayList
import java.util.zip.ZipEntry
import java.util.Locale
import java.util.zip.ZipFile

class CbzFile(var book: Book) {

    var info: Map<String, @JvmSuppressWildcards Any>? = null

    var cover: InputStream? = null

    private fun parseBookInfo(): Pair<Map<String, @JvmSuppressWildcards Any>?, InputStream?> {
        if (cover != null || info != null) {
            return Pair(info, cover)
        }
        val zf = ZipFile(book.getLocalFile())
        val imageExt = listOf("jpg", "jpeg", "gif", "png", "bmp", "webp", "svg")
        val entries = zf.entries()
        while (entries.hasMoreElements()) {
            val entry: ZipEntry = entries.nextElement()
            if (!entry.isDirectory) {
                val name = entry.name
                if (name.equals("ComicInfo.xml")) {
                    info = XmlUtils.xml2map(zf.getInputStream(entry))
                } else if (cover == null) {
                    val ext = FileUtils.getFileExtetion(name).toLowerCase(Locale.ROOT)
                    if (imageExt.contains(ext)) {
                        cover = zf.getInputStream(entry)
                    }
                }
            }
            if (cover != null && info != null) break
        }
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
        val coverFile = MD5Utils.md5Encode16(book.bookUrl) + ".jpg"
        val relativeCoverUrl = Paths.get("assets", book.getUserNameSpace(), "covers", coverFile).toString()
        book.coverUrl = "/" + relativeCoverUrl.replace("\\", "/")
        val coverUrl = Paths.get(book.workRoot(), "storage", relativeCoverUrl).toString()
        val result = parseBookInfo()
        if (!File(coverUrl).exists() && result.second != null) {
            FileUtils.writeInputStream(coverUrl, result.second!!)
        }
    }

    private fun getContent(chapter: BookChapter): String? {
        return ""
    }

    private fun getChapterList(): ArrayList<BookChapter> {
        val chapterList = ArrayList<BookChapter>()
        val zf = ZipFile(book.getLocalFile())
        val imageFileList = ArrayList<String>()
        val entries = zf.entries()
        while (entries.hasMoreElements()) {
            val entry: ZipEntry = entries.nextElement()
            if (entry.isDirectory) continue
            val name = entry.name
            if (name.endsWith(".xml")) continue
            imageFileList.add(name)
        }
        imageFileList.sort()
        for (i in imageFileList.indices) {
            val name = imageFileList[i]
            val chapter = BookChapter()
            chapter.title = name
            chapter.index = i
            chapter.bookUrl = book.bookUrl
            chapter.url = name
            chapterList.add(chapter)
        }
        book.latestChapterTitle = chapterList.lastOrNull()?.title
        book.totalChapterNum = chapterList.size
        return chapterList
    }

    companion object {

        private var cFile: CbzFile? = null

        @Synchronized
        private fun getCbzFile(book: Book): CbzFile {
            if (cFile?.book?.bookUrl != book.bookUrl) {
                cFile = CbzFile(book)
                return cFile!!
            }
            cFile?.book = book
            return cFile!!
        }

        @Synchronized
        fun getChapterList(book: Book): ArrayList<BookChapter> {
            return getCbzFile(book).getChapterList()
        }

        @Synchronized
        fun getContent(book: Book, chapter: BookChapter): String? {
            return getCbzFile(book).getContent(chapter)
        }

        @Synchronized
        fun upBookInfo(book: Book, onlyCover: Boolean = false) {
            if (onlyCover) {
                getCbzFile(book).updateCover()
                return
            }
            getCbzFile(book).upBookInfo()
        }
    }
}
