// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.model.localBook

import io.legado.app.data.entities.Book
import io.legado.app.data.entities.BookChapter
import io.legado.app.exception.TocEmptyException
import io.legado.app.help.BookHelp
import io.legado.app.utils.FileUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.ArrayList
import java.util.regex.Pattern

object LocalBook {

    private val nameAuthorPatterns = arrayOf(
        Pattern.compile("(.*?)《([^《》]+)》.*?作者：(.*)"),
        Pattern.compile("(.*?)《([^《》]+)》(.*)"),
        Pattern.compile("(^)(.+) 作者：(.+)$"),
        Pattern.compile("(^)(.+) by (.+)$")
    )

    @Throws(FileNotFoundException::class, SecurityException::class)
    fun getBookInputStream(book: Book): InputStream {
        val file = book.getLocalFile()
        if (file.exists()) {
            return FileInputStream(file)
        }
        throw FileNotFoundException("${book.name} 文件不存在")
    }

    @Throws(Exception::class)
    fun getChapterList(book: Book): ArrayList<BookChapter> {
        val chapters = when {
            book.isEpub() -> EpubFile.getChapterList(book)
            book.isUmd() -> UmdFile.getChapterList(book)
            book.isCbz() -> CbzFile.getChapterList(book)
            book.isPdf() -> PdfFile.getChapterList(book)
            else -> TextFile.getChapterList(book)
        }
        if (chapters.isEmpty()) {
            throw TocEmptyException("Chapterlist is empty  ${book.getLocalFile()}")
        }
        return chapters
    }

    fun getContent(book: Book, chapter: BookChapter): String? {
        return when {
            book.isEpub() -> EpubFile.getContent(book, chapter)
            book.isUmd() -> UmdFile.getContent(book, chapter)
            book.isCbz() -> CbzFile.getContent(book, chapter)
            book.isPdf() -> PdfFile.getContent(book, chapter)
            else -> TextFile.getContent(book, chapter)
        }
    }

    fun analyzeNameAuthor(fileName: String): Pair<String, String> {
        val tempFileName = fileName.substringBeforeLast(".")
        var name: String
        var author: String
        for (pattern in nameAuthorPatterns) {
            val matcher = pattern.matcher(tempFileName)
            if (!matcher.find()) continue
            name = matcher.group(2)!!
            val group1 = matcher.group(1) ?: ""
            val group3 = matcher.group(3) ?: ""
            author = BookHelp.formatBookAuthor(group1 + group3)
            return Pair(name, author)
        }
        name = BookHelp.formatBookName(tempFileName)
        val author2 = BookHelp.formatBookAuthor(tempFileName.replace(name, ""))
        author = if (author2.length != tempFileName.length) author2 else ""
        return Pair(name, author)
    }

    fun deleteBook(book: Book) {
        runCatching {
            var bookFile: File = book.getLocalFile()
            if ((book.isLocalTxt() || book.isUmd()) && bookFile.exists()) {
                bookFile.delete()
            }
            if (book.isEpub()) {
                bookFile = bookFile.parentFile!!
                if (bookFile.exists()) {
                    FileUtils.delete(bookFile, true)
                }
            }
        }
    }
}
