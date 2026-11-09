// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.model.localBook

import io.legado.app.data.entities.Book
import io.legado.app.data.entities.BookChapter
import io.legado.app.data.entities.TxtTocRule
import io.legado.app.help.DefaultData
import io.legado.app.utils.EncodingDetect
import io.legado.app.utils.MD5Utils
import io.legado.app.utils.StringUtils
import io.legado.app.utils.Utf8BomUtils
import mu.KLogger
import mu.KotlinLogging
import java.io.FileNotFoundException
import java.nio.charset.Charset
import java.util.ArrayList
import java.util.regex.Pattern
import kotlin.math.min

private val logger: KLogger = KotlinLogging.logger { }

class TextFile(private val book: Book) {

    private val blank: Byte = '\n'.toByte()

    private val bufferSize = 512000

    private val maxLengthWithNoToc = 10240

    private val maxLengthWithToc = 102400

    private var charset: Charset = book.fileCharset()

    @Throws(FileNotFoundException::class)
    fun getChapterList(): ArrayList<BookChapter> {
        if (book.charset == null || book.tocUrl.isBlank()) {
            LocalBook.getBookInputStream(book).use { bis ->
                val buffer = ByteArray(bufferSize)
                val length = bis.read(buffer)
                if (book.charset.isNullOrBlank()) {
                    book.charset = EncodingDetect.getEncode(buffer.copyOf(length))
                }
                charset = book.fileCharset()
                if (book.tocUrl.isBlank()) {
                    val blockContent = String(buffer, 0, length, charset)
                    book.tocUrl = getTocRule(blockContent)?.pattern() ?: ""
                }
            }
        }
        val toc = analyze(Pattern.compile(book.tocUrl, Pattern.MULTILINE))
        toc.forEachIndexed { index, bookChapter ->
            bookChapter.index = index
            bookChapter.bookUrl = book.bookUrl
            bookChapter.url = MD5Utils.md5Encode16(book.originName + index + bookChapter.title)
        }
        book.latestChapterTitle = toc.last().title
        book.totalChapterNum = toc.size
        return toc
    }

    private fun analyze(pattern: Pattern?): ArrayList<BookChapter> {
        if (pattern?.pattern().isNullOrEmpty()) {
            return analyze()
        }
        val pat = pattern ?: return analyze()
        val toc = ArrayList<BookChapter>()
        LocalBook.getBookInputStream(book).use { bis ->
            var blockContent: String? = null
            var curOffset = 0L
            var length = 0
            val buffer = ByteArray(bufferSize)
            var bufferStart = 3
            bis.read(buffer, 0, 3)
            if (Utf8BomUtils.hasBom(buffer)) {
                bufferStart = 0
                curOffset = 3L
            }
            while (true) {
                val readSize = bis.read(buffer, bufferStart, bufferSize - bufferStart)
                length = readSize
                if (readSize <= 0) break
                var end = bufferStart + length
                if (end == bufferSize) {
                    for (i in bufferStart + length - 1 downTo 0) {
                        if (buffer[i] == blank) {
                            end = i
                            break
                        }
                    }
                }
                blockContent = String(buffer, 0, end, charset)
                buffer.copyInto(buffer, 0, end, bufferStart + length)
                bufferStart = bufferStart + length - end
                length = end
                var seekPos = 0
                val matcher = pat.matcher(blockContent)
                while (matcher.find()) {
                    val chapterStart = matcher.start()
                    val chapterContent = blockContent.substring(seekPos, chapterStart)
                    val chapterLength = chapterContent.toByteArray(charset).size
                    val lastChapter = toc.lastOrNull()
                    val lastStart = lastChapter?.start ?: curOffset
                    if (book.getSplitLongChapter() &&
                        curOffset + chapterLength - lastStart > maxLengthWithToc
                    ) {
                        lastChapter?.let { it.end = it.start }
                        val lastTitle = toc.lastOrNull()?.title
                        val chapters = analyze(
                            lastStart + (lastTitle?.toByteArray(charset)?.size ?: 0),
                            curOffset + chapterLength
                        )
                        if (lastTitle != null) {
                            chapters.forEachIndexed { index, bookChapter ->
                                bookChapter.title = "" + lastTitle + '(' + (index + 1) + ')'
                            }
                        }
                        toc.addAll(chapters)
                        val curChapter = BookChapter()
                        curChapter.title = matcher.group()
                        curChapter.start = curOffset + chapterLength
                        toc.add(curChapter)
                    } else if (seekPos == 0 && chapterStart != 0) {
                        if (toc.isEmpty()) {
                            val lastTitle = StringUtils.trim(chapterContent)
                            if (lastTitle.length > 0) {
                                val qyChapter = BookChapter()
                                qyChapter.title = "前言"
                                qyChapter.start = curOffset
                                qyChapter.end = chapterLength.toLong()
                                toc.add(qyChapter)
                            }
                            val curChapter = BookChapter()
                            curChapter.title = matcher.group()
                            curChapter.start = chapterLength.toLong()
                            toc.add(curChapter)
                        } else {
                            val lastChapter = toc.last()
                            lastChapter.isVolume = chapterContent.substringAfter(lastChapter.title).isBlank()
                            lastChapter.end = lastChapter.end!! + chapterLength
                            val curChapter = BookChapter()
                            curChapter.title = matcher.group()
                            curChapter.start = lastChapter.end
                            toc.add(curChapter)
                        }
                    } else {
                        if (toc.isNotEmpty()) {
                            val lastChapter = toc.last()
                            lastChapter.isVolume = chapterContent.substringAfter(lastChapter.title).isBlank()
                            lastChapter.end = lastChapter.start!! + chapterContent.toByteArray(charset).size
                            val curChapter = BookChapter()
                            curChapter.title = matcher.group()
                            curChapter.start = lastChapter.end
                            toc.add(curChapter)
                        } else {
                            val curChapter = BookChapter()
                            curChapter.title = matcher.group()
                            curChapter.start = curOffset
                            curChapter.end = curOffset
                            toc.add(curChapter)
                        }
                    }
                    seekPos += chapterContent.length
                }
                curOffset += length
                toc.lastOrNull()?.end = curOffset
            }
        }
        System.gc()
        System.runFinalization()
        return toc
    }

    private fun analyze(fileStart: Long = 0L, fileEnd: Long = Long.MAX_VALUE): ArrayList<BookChapter> {
        val toc = ArrayList<BookChapter>()
        LocalBook.getBookInputStream(book).use { bis ->
            var blockPos = 0
            var curOffset = 0L
            var chapterPos = 0
            var length = 0
            val buffer = ByteArray(bufferSize)
            var bufferStart = 0
            bufferStart = 3
            if (fileStart == 0L) {
                bis.read(buffer, 0, 3)
                if (Utf8BomUtils.hasBom(buffer)) {
                    bufferStart = 0
                    curOffset = 3L
                }
            } else {
                bis.skip(fileStart)
                curOffset = fileStart
                bufferStart = 0
            }
            while (fileEnd - curOffset - bufferStart > 0) {
                val readSize = bis.read(
                    buffer,
                    bufferStart,
                    min((bufferSize - bufferStart).toLong(), fileEnd - curOffset - bufferStart).toInt()
                )
                length = readSize
                if (readSize <= 0) break
                blockPos++
                var chapterOffset = 0
                length += bufferStart
                var strLength = length
                chapterPos = 0
                while (strLength > 0) {
                    chapterPos++
                    if (strLength > maxLengthWithNoToc) {
                        var end = length
                        var i = chapterOffset + maxLengthWithNoToc
                        if (i < length) {
                            while (i < length) {
                                if (buffer[i++] == blank) {
                                    end = i - 1
                                    break
                                }
                            }
                        }
                        val chapter = BookChapter()
                        chapter.title = "第${blockPos}章(${chapterPos})"
                        chapter.start = toc.lastOrNull()?.end ?: curOffset
                        chapter.end = chapter.start!! + end - chapterOffset
                        toc.add(chapter)
                        strLength -= end - chapterOffset
                        chapterOffset = end
                        continue
                    }
                    buffer.copyInto(buffer, 0, length - strLength, length)
                    length -= strLength
                    bufferStart = strLength
                    strLength = 0
                }
                curOffset += length
            }
            if (bufferStart > 100 || toc.isEmpty()) {
                val chapter = BookChapter()
                chapter.title = "第${blockPos}章(${chapterPos})"
                chapter.start = toc.lastOrNull()?.end ?: curOffset
                chapter.end = chapter.start!! + bufferStart
                toc.add(chapter)
            } else {
                toc.lastOrNull()?.end = toc.last().end!! + bufferStart
            }
        }
        return toc
    }

    private fun getTocRule(content: String): Pattern? {
        val rules = getTocRules().reversed()
        var maxCs = 1
        var tocPattern: Pattern? = null
        for (tocRule in rules) {
            val pattern = Pattern.compile(tocRule.rule, Pattern.MULTILINE)
            val matcher = pattern.matcher(content)
            var cs = 0
            while (matcher.find()) {
                cs++
            }
            if (cs < maxCs) continue
            maxCs = cs
            tocPattern = pattern
        }
        return tocPattern
    }

    private fun getTocRules(): List<TxtTocRule> {
        return DefaultData.txtTocRules.filter { it.enable }
    }

    companion object {

        @Throws(FileNotFoundException::class)
        fun getChapterList(book: Book): ArrayList<BookChapter> {
            return TextFile(book).getChapterList()
        }

        @Throws(FileNotFoundException::class)
        fun getContent(book: Book, bookChapter: BookChapter): String {
            val count = (bookChapter.end!! - bookChapter.start!!).toInt()
            val buffer = ByteArray(count)
            LocalBook.getBookInputStream(book).use { bis ->
                bis.skip(bookChapter.start!!)
                bis.read(buffer)
            }
            if (book.charset == null) {
                book.charset = EncodingDetect.getEncode(book.getLocalFile())
            }
            return String(buffer, book.fileCharset())
                .substringAfter(bookChapter.title)
                .replace(Regex("^[\\n\\s]+"), "　　")
        }
    }
}
