// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.api.controller

import com.htmake.reader.api.ReturnData
import com.htmake.reader.entity.User
import com.htmake.reader.utils.SpringContextUtils
import com.htmake.reader.utils.UserMutex
import com.htmake.reader.utils.deleteRecursively
import com.htmake.reader.utils.getWorkDir
import com.htmake.reader.utils.jsonEncode
import com.htmake.reader.utils.toDataClass
import com.htmake.reader.utils.toMap
import io.legado.app.constant.AppPattern
import io.legado.app.data.entities.Book
import io.legado.app.data.entities.BookChapter
import io.legado.app.data.entities.BookSource
import io.legado.app.data.entities.TxtTocRule
import io.legado.app.exception.TocEmptyException
import io.legado.app.help.DefaultData
import io.legado.app.model.localBook.LocalBook
import io.legado.app.model.webBook.WebBook
import io.legado.app.model.analyzeRule.AnalyzeUrl
import io.legado.app.utils.ACache
import io.legado.app.utils.FileUtils
import io.legado.app.utils.HtmlFormatter
import io.legado.app.utils.GSON
import io.legado.app.utils.fromJsonArray
import io.legado.app.utils.MD5Utils
import io.vertx.core.buffer.Buffer
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.client.HttpResponse
import io.vertx.ext.web.client.WebClient
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import io.vertx.kotlin.coroutines.awaitResult
import kotlinx.coroutines.slf4j.MDCContext
import mu.KLogger
import mu.KotlinLogging
import java.io.File
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.Image
import java.awt.image.RenderedImage
import org.apache.pdfbox.rendering.ImageType
import com.htmake.reader.utils.unzip
import com.htmake.reader.utils.MongoManager
import com.htmake.reader.utils.asJsonArray
import com.htmake.reader.utils.asJsonObject
import com.htmake.reader.utils.getRelativePath
import com.htmake.reader.utils.readMongoFile
import com.htmake.reader.utils.saveMongoFile
import com.htmake.reader.lib.tts.constant.TtsStyleEnum
import com.htmake.reader.lib.tts.constant.VoiceEnum
import com.htmake.reader.lib.tts.model.SSML
import com.htmake.reader.lib.tts.service.TTSService
import io.legado.app.data.entities.HttpTTS
import io.legado.app.data.entities.SearchBook
import io.legado.app.data.entities.SearchResult
import io.legado.app.help.BookHelp
import io.legado.app.model.Debug
import io.legado.app.model.Debugger
import io.legado.app.utils.fromJsonObject
import io.legado.app.utils.NetworkUtils
import io.legado.app.utils.ZipUtils
import io.vertx.core.http.CaseInsensitiveHeaders
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import me.ag2s.epublib.domain.Author
import me.ag2s.epublib.domain.Date
import me.ag2s.epublib.domain.FileResourceProvider
import me.ag2s.epublib.domain.LazyResource
import me.ag2s.epublib.domain.Metadata
import me.ag2s.epublib.util.ResourceUtil
import me.ag2s.epublib.domain.EpubBook
import me.ag2s.epublib.domain.MediaTypes
import me.ag2s.epublib.domain.Resource
import me.ag2s.epublib.epub.EpubWriter
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.PDFRenderer
import java.awt.image.BufferedImage
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Locale
import java.util.function.BiConsumer
import javax.imageio.ImageIO
import com.htmake.reader.utils.parseJsonStringList
import com.htmake.reader.utils.saveStorage
import com.htmake.reader.utils.fillData
import com.htmake.reader.utils.getStorage
import com.htmake.reader.utils.getStorageFile
import com.fasterxml.jackson.databind.node.ObjectNode
import kotlin.math.max
import kotlin.jvm.internal.Ref
import java.nio.file.Paths
import kotlin.coroutines.CoroutineContext
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.JsonNode
import kotlin.io.use
import io.legado.app.model.analyzeRule.AnalyzeRule
import kotlinx.coroutines.ensureActive
import okhttp3.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import com.script.ScriptException
import org.mozilla.javascript.WrappedException
import kotlinx.coroutines.CancellationException
import io.legado.app.exception.NoStackTraceException
import com.htmake.reader.utils.success
import java.nio.charset.Charset

private val logger: KLogger = KotlinLogging.logger { }

class BookController(coroutineContext: CoroutineContext) : BaseController(coroutineContext) {

    private var bookInfoCache: ACache = ACache.get("bookInfoCache", 2000000L, 10000)

    private val concurrentLoopCount: Int = 8

    private val backupFileNames: Array<String> by lazy {
        arrayOf(
            "bookSource.json",
            "bookshelf.json",
            "bookGroup.json",
            "rssSources.json",
            "replaceRule.json",
            "bookmark.json",
            "userConfig.json",
            "httpTTS.json",
            "remoteBookSourceSub.json",
            "txtTocRule.json"
        )
    }

    private var webClient: WebClient =
        SpringContextUtils.getBean("webClient", WebClient::class.java)

    private fun getInvalidBookSourceCache(userNameSpace: String): ACache {
        val cacheDir = File(getWorkDir("storage", "cache", "invalidBookSourceCache", userNameSpace))
        val cache = ACache.get(cacheDir, 5000000L, 1000000)
        return cache
    }

    private fun isInvalidBookSource(bookSource: BookSource, userNameSpace: String): Boolean {
        return getInvalidBookSourceCache(userNameSpace).getAsString(bookSource.bookSourceUrl) != null
    }

    private fun addInvalidBookSource(
        sourceUrl: String,
        invalidInfo: Map<String, Any>,
        userNameSpace: String
    ) {
        getInvalidBookSourceCache(userNameSpace).put(sourceUrl, jsonEncode(invalidInfo), 600)
    }

    private fun getBookChaptersCache(userNameSpace: String): ACache {
        val cacheDir = File(getWorkDir("storage", "cache", "bookChaptersCache", userNameSpace))
        val cache = ACache.get(cacheDir, 5000000L, 1000000)
        return cache
    }

    suspend fun getInvalidBookSources(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        val userNameSpace = getUserNameSpace(context)
        val invalidBookSourceCache = getInvalidBookSourceCache(userNameSpace)
        val cacheDir = File(getWorkDir("storage", "cache", "invalidBookSourceCache", userNameSpace))
        val files = cacheDir.listFiles()
        val invalidBookSourceList = arrayListOf<Map<String, Any>>()
        if (files != null) {
            for (f in files) {
                invalidBookSourceCache.getByHashCode(f.name)?.let {
                    invalidBookSourceList.add(it.toMap())
                }
            }
        }
        return returnData.setData(invalidBookSourceList)
    }

    suspend fun getBookInfo(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        var bookUrl = ""
        if (context.request().method() == HttpMethod.POST) {
            bookUrl = context.bodyAsJson.getString("url")
                ?: context.bodyAsJson.getJsonObject("searchBook").getString("bookUrl") ?: ""
        } else {
            bookUrl = context.queryParam("url").firstOrNull() ?: ""
        }
        if (bookUrl.isEmpty()) {
            return returnData.setErrorMsg("请输入书籍链接")
        }
        val userNameSpace = getUserNameSpace(context)
        logger.info("getBookInfo with bookUrl: {}", bookUrl)
        var bookInfo: Book? = null
        if (checkAuth(context)) {
            bookInfo = getShelfBookByURL(bookUrl, userNameSpace)
        }
        if (bookInfo == null) {
            bookInfo = bookInfoCache.getAsString(bookUrl)?.toMap()?.toDataClass<Book>()
            var bookSource: String? = null
            if (bookInfo != null) {
                bookSource = getBookSourceString(context, bookInfo.origin)
            }
            if (bookInfo == null) {
                bookSource = getBookSourceString(context)
            }
            if (bookSource.isNullOrEmpty()) {
                return returnData.setErrorMsg("未配置书源")
            }
            bookInfo = WebBook(
                bookSource,
                appConfig.debugLog,
                userNameSpace = userNameSpace
            ).getBookInfo(bookUrl)
            bookInfo = mergeBookCacheInfo(bookInfo)
        }
        val book = bookInfo
        saveBookInfoCache(arrayListOf(book))
        return returnData.setData(book)
    }

    suspend fun getBookCover(context: RoutingContext) {
        val coverUrl = context.queryParam("path").firstOrNull() ?: ""
        if (coverUrl.isEmpty()) {
            context.response().setStatusCode(404).end()
            return
        }
        val ext = getFileExt(coverUrl, "png")
        val md5Encode = MD5Utils.md5Encode(coverUrl)
        val cachePath = getWorkDir("storage", "cache", "bookCoverCache", "$md5Encode.$ext")
        val cacheFile = File(cachePath)
        if (cacheFile.exists()) {
            logger.info("send cache: {}", cacheFile)
            context.response().putHeader("Cache-Control", "86400").sendFile(cacheFile.toString())
            return
        }
        if (!cacheFile.parentFile.exists()) {
            cacheFile.parentFile.mkdirs()
        }
        val exceptionHandler = CoroutineExceptionHandler { _, ex ->
            logger.info("get cover error: {}", ex.message)
            context.response().setStatusCode(404).end()
        }
        launch(MDCContext() + Dispatchers.IO + exceptionHandler) {
            val result = awaitResult<HttpResponse<Buffer>> { handler ->
                webClient.getAbs(coverUrl).timeout(3000L).send(handler)
            }
            val bodyBytes = result.bodyAsBuffer()?.bytes
            if (bodyBytes != null) {
                val res = context.response().putHeader("Cache-Control", "86400")
                cacheFile.writeBytes(bodyBytes)
                res.sendFile(cacheFile.toString())
            } else {
                context.response().setStatusCode(404).end()
            }
        }
    }

    suspend fun importBookPreview(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (context.fileUploads() == null || context.fileUploads().isEmpty()) {
            return returnData.setErrorMsg("请上传书籍文件")
        }
        val userNameSpace = getUserNameSpace(context)
        val fileList = arrayListOf<Map<String, Any>>()
        context.fileUploads().forEach { it ->
            val file = File(it.uploadedFileName())
            logger.info("uploadFile: {} {} {}", it.uploadedFileName(), it.fileName(), file)
            if (file.exists()) {
                var fileName = it.fileName()
                val ext = getFileExt(fileName)
                if (!(ext == "txt" || ext == "epub" || ext == "umd" || ext == "cbz" || ext == "pdf")) {
                    file.deleteRecursively()
                    return returnData.setErrorMsg("不支持导入${ext}格式的书籍文件")
                }
                fileName = FileUtils.getNameExcludeExtension(fileName)
                fileName = AppPattern.fileNameRegex.replace(fileName, "")
                fileName = fileName.substring(0, Math.min(50, fileName.length)) + "." + ext
                val localFilePath =
                    Paths.get("storage", "assets", userNameSpace, "book", fileName).toString()
                val localFileUrl = "/assets/$userNameSpace/book/$fileName"
                var filePath = localFilePath
                if (fileName.endsWith(".epub", true)) {
                    filePath = filePath + File.separator + "index.epub"
                }
                if (fileName.endsWith(".cbz", true)) {
                    filePath = filePath + File.separator + "index.cbz"
                }
                if (fileName.endsWith(".pdf", true)) {
                    filePath = filePath + File.separator + "index.pdf"
                }
                val newFile = File(getWorkDir(filePath))
                if (!newFile.parentFile.exists()) {
                    newFile.parentFile.mkdirs()
                }
                if (newFile.exists()) {
                    newFile.delete()
                }
                logger.info("moveTo: {}", newFile)
                if (file.copyRecursively(newFile)) {
                    val book = Book.initLocalBook(localFileUrl, localFilePath, getWorkDir())
                    book.setUserNameSpace(userNameSpace)
                    try {
                        val chapters = LocalBook.getChapterList(book)
                        fileList.add(mapOf("book" to book, "chapters" to chapters))
                    } catch (e: TocEmptyException) {
                        fileList.add(mapOf("book" to book, "chapters" to arrayListOf<BookChapter>()))
                    }
                }
                file.deleteRecursively()
            }
        }
        return returnData.setData(fileList)
    }

    suspend fun getTxtTocRules(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        val userNameSpace = getUserNameSpace(context)
        val txtTocRules = getUserStorage(userNameSpace, "txtTocRule")
        val rules = arrayListOf<TxtTocRule>()
        rules.addAll(DefaultData.txtTocRules)
        if (txtTocRules != null) {
            val customRule = GSON.fromJsonArray<TxtTocRule>(txtTocRules).getOrNull() ?: emptyList()
            rules.addAll(customRule)
        }
        return returnData.setData(rules)
    }

    suspend fun getChapterListByRule(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        val book = context.bodyAsJson.mapTo(Book::class.java)
        if (book.origin.isEmpty()) {
            return returnData.setErrorMsg("未找到书源信息")
        }
        if (!(book.isLocalTxt() || book.isLocalEpub() || book.isLocalPdf())) {
            return returnData.setErrorMsg("非本地txt/epub/pdf书籍")
        }
        book.setRootDir(getWorkDir())
        book.setUserNameSpace(getUserNameSpace(context))
        val chapters = LocalBook.getChapterList(book)
        return returnData.setData(mapOf("book" to book, "chapters" to chapters))
    }

    suspend fun refreshLocalBook(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        var bookUrl: String
        if (context.request().method() == HttpMethod.POST) {
            bookUrl = context.bodyAsJson.getString("bookUrl")
        } else {
            bookUrl = context.queryParam("bookUrl").firstOrNull() ?: ""
        }
        if (bookUrl.isEmpty()) {
            return returnData.setErrorMsg("请输入书籍链接")
        }
        val userNameSpace = getUserNameSpace(context)
        val bookInfo = getShelfBookByURL(bookUrl, userNameSpace)
            ?: return returnData.setErrorMsg("书籍信息错误")
        bookInfo.updateFromLocal(true)
        editShelfBook(bookInfo, userNameSpace) { existBook ->
            existBook.coverUrl = bookInfo.coverUrl
            logger.info("refreshLocalBook: {}", existBook)
            existBook
        }
        return returnData.setData(bookInfo)
    }

    suspend fun getChapterList(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        var bookUrl: String
        var refresh = 0
        if (context.request().method() == HttpMethod.POST) {
            bookUrl = context.bodyAsJson.getString("url")
                ?: context.bodyAsJson.getJsonObject("book").getString("bookUrl") ?: ""
            refresh = context.bodyAsJson.getInteger("refresh", 0)
        } else {
            bookUrl = context.queryParam("url").firstOrNull() ?: ""
            refresh = context.queryParam("refresh").firstOrNull()?.toInt() ?: 0
        }
        if (bookUrl.isEmpty()) {
            return returnData.setErrorMsg("请输入书籍链接")
        }
        var userNameSpace = getUserNameSpace(context)
        var bookInfo = getShelfBookByURL(bookUrl, userNameSpace)
        var bookSource: String? = null
        if (bookInfo == null) {
            var cacheInfo: Book? = bookInfoCache.getAsString(bookUrl)?.toMap()?.toDataClass<Book>()
            if (cacheInfo != null) {
                bookSource = getBookSourceString(context, cacheInfo.origin)
            } else {
                bookSource = getBookSourceString(context)
            }
            if (bookSource.isNullOrEmpty()) {
                return returnData.setErrorMsg("未配置书源")
            }
            bookInfo = WebBook(bookSource, appConfig.debugLog, userNameSpace = userNameSpace)
                .getBookInfo(bookUrl)
            bookInfo = mergeBookCacheInfo(bookInfo)
            saveBookInfoCache(arrayListOf<Book>(bookInfo))
        } else {
            bookSource = getBookSourceString(context, bookInfo.origin)
        }
        if (!bookInfo.isLocalBook() && bookSource.isNullOrEmpty()) {
            return returnData.setErrorMsg("未配置书源")
        }
        bookInfo.setRootDir(getWorkDir())
        bookInfo.setUserNameSpace(userNameSpace)
        if (bookInfo.isLocalBook()) {
            val localFile = bookInfo.getLocalFile()
            if (!localFile.exists()) {
                logger.info("localFile: {} not exists", localFile)
                return returnData.setErrorMsg("本地书籍源文件不存在")
            }
        }
        logger.info("bookInfo: {}", bookInfo)
        val chapterList = getLocalChapterList(bookInfo, bookSource ?: "", refresh > 0, getUserNameSpace(context))
        return returnData.setData(chapterList)
    }

    suspend fun saveBookProgress(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        var bookUrl: String
        var chapterIndex: Int
        if (context.request().method() == HttpMethod.POST) {
            bookUrl = context.bodyAsJson.getString("url")
                ?: context.bodyAsJson.getJsonObject("searchBook").getString("bookUrl") ?: ""
            chapterIndex = context.bodyAsJson.getInteger("index", -1)
        } else {
            bookUrl = context.queryParam("url").firstOrNull() ?: ""
            chapterIndex = context.queryParam("index").firstOrNull()?.toInt() ?: -1
        }
        if (bookUrl.isEmpty()) {
            return returnData.setErrorMsg("请输入书籍链接")
        }
        val userNameSpace = getUserNameSpace(context)
        val bookInfo = getShelfBookByURL(bookUrl, userNameSpace)
        if (bookInfo == null || bookInfo.origin.isEmpty()) {
            return returnData.setErrorMsg("书籍未加入书架")
        }
        val bookSource = getBookSourceStringBySourceURLOpt(bookInfo.origin, userNameSpace)
        if (!bookInfo.isLocalBook()) {
            if (bookSource.isNullOrEmpty()) {
                return returnData.setErrorMsg("未配置书源")
            }
        }
        val chapterList = getLocalChapterList(bookInfo, bookSource ?: "", false, userNameSpace)
        if (chapterIndex >= chapterList.size) {
            return returnData.setErrorMsg("章节不存在")
        }
        val chapterInfo = chapterList[chapterIndex]
        saveShelfBookProgress(bookInfo, chapterInfo, userNameSpace)
        saveBookProgressToWebdav(bookInfo, chapterInfo, userNameSpace)
        return returnData.setData("")
    }

    suspend fun getBookContent(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        var chapterUrl: String
        var bookUrl: String
        var chapterIndex: Int
        var cache: Int
        var refresh: Int
        var epubContent: Int
        if (context.request().method() == HttpMethod.POST) {
            chapterUrl = context.bodyAsJson.getString("chapterUrl")
                ?: (context.bodyAsJson.getJsonObject("bookChapter")?.getString("url") ?: "")
            bookUrl = context.bodyAsJson.getString("url")
                ?: (context.bodyAsJson.getJsonObject("searchBook")?.getString("bookUrl") ?: "")
            chapterIndex = context.bodyAsJson.getInteger("index", -1)
            cache = context.bodyAsJson.getInteger("cache", 0)
            refresh = context.bodyAsJson.getInteger("refresh", 0)
            epubContent = context.bodyAsJson.getInteger("epubContent", 0)
        } else {
            chapterUrl = context.queryParam("chapterUrl").firstOrNull() ?: ""
            bookUrl = context.queryParam("url").firstOrNull() ?: ""
            chapterIndex = context.queryParam("index").firstOrNull()?.toInt() ?: -1
            cache = context.queryParam("cache").firstOrNull()?.toInt() ?: 0
            refresh = context.queryParam("refresh").firstOrNull()?.toInt() ?: 0
            epubContent = context.queryParam("epubContent").firstOrNull()?.toInt() ?: 0
        }
        if (bookUrl.isEmpty()) {
            return returnData.setErrorMsg("请输入书籍链接")
        }
        var bookSource = getBookSourceString(context)
        val userNameSpace = getUserNameSpace(context)
        var isInBookShelf = 0
        var bookInfo: Book? = null
        var chapterInfo: BookChapter? = null
        var nextChapterUrl: String? = null
        if (chapterUrl.length > 0) {
            bookInfo = getShelfBookByURL(bookUrl, userNameSpace)
            if (bookInfo != null && bookInfo.origin.length > 0) {
                isInBookShelf = 1
                bookSource = (getBookSourceStringBySourceURLOpt(bookInfo.origin, userNameSpace) ?: "")
            }
        }
        if (bookInfo == null) {
            val cacheInfo = bookInfoCache.getAsString(bookUrl)?.toMap()?.toDataClass<Book>()
            if (cacheInfo != null) {
                bookSource = getBookSourceString(context, cacheInfo.origin)
            }
            if (chapterUrl.length == 0 && chapterIndex >= 0) {
                if (bookUrl.isEmpty()) {
                    return returnData.setErrorMsg("请输入书籍链接")
                }
                if (!bookSource.isNullOrEmpty()) {
                    bookInfo = mergeBookCacheInfo(
                        WebBook(
                            bookSource,
                            appConfig.debugLog,
                            userNameSpace = userNameSpace
                        ).getBookInfo(bookUrl)
                    )
                }
            }
        }
        val book = bookInfo ?: return returnData.setErrorMsg("获取书籍信息失败")
        if (!book.isLocalBook()) {
            if (bookSource.isNullOrEmpty()) {
                return returnData.setErrorMsg("未配置书源")
            }
        }
        val chapterList = getLocalChapterList(book, bookSource ?: "", false, userNameSpace)
        if (chapterIndex >= chapterList.size) {
            return returnData.setErrorMsg("章节不存在")
        }
        chapterInfo = chapterList[chapterIndex]
        if (cache != 0 && refresh != 1) {
            saveShelfBookProgress(book, chapterInfo, userNameSpace)
            saveBookProgressToWebdav(book, chapterInfo, userNameSpace)
        }
        chapterUrl = chapterInfo.url
        if (chapterIndex + 1 < chapterList.size) {
            nextChapterUrl = chapterList[chapterIndex + 1].url
        }
        if (chapterUrl.length == 0) {
            return returnData.setErrorMsg("获取章节链接失败")
        }
        book.setRootDir(getWorkDir())
        book.setUserNameSpace(userNameSpace)
        if (book.isLocalBook()) {
            val localFile = book.getLocalFile()
            if (!localFile.exists()) {
                return returnData.setErrorMsg("本地源书籍文件不存在")
            }
            if (book.isEpub()) {
                if (!extractEpub(book)) {
                    return returnData.setErrorMsg("Epub书籍解压失败")
                }
                val epubRootDir = book.getEpubRootDir()
                val chapterFilePath =
                    getWorkDir(book.bookUrl, "index", epubRootDir, chapterInfo.url)
                logger.info("chapterFilePath: {} {}", chapterFilePath, epubRootDir)
                if (!File(chapterFilePath).exists()) {
                    return returnData.setErrorMsg("章节文件不存在")
                }
                val content = if (epubRootDir.length == 0) {
                    book.bookUrl.replace("\\", "/")
                        .replace("storage/data/", "/book-assets/") + "/index/" + chapterInfo.url
                } else {
                    book.bookUrl.replace("\\", "/")
                        .replace("storage/data/", "/book-assets/") + "/index/" + epubRootDir + '/' + chapterInfo.url
                }
                if (epubContent > 0) {
                    return returnData.setData(
                        mapOf(
                            "url" to "__API_ROOT__$content",
                            "content" to File(chapterFilePath).readText()
                        )
                    )
                }
                return returnData.setData(content)
            }
            if (book.isCbz()) {
                if (!extractCbz(book)) {
                    return returnData.setErrorMsg("CBZ书籍解压失败")
                }
                val chapterFilePath = getWorkDir(book.bookUrl, "index", chapterInfo.url)
                logger.info("chapterFilePath: {}", chapterFilePath)
                val chapterFile = File(chapterFilePath)
                if (!chapterFile.exists()) {
                    return returnData.setErrorMsg("章节文件不存在")
                }
                val ext = getFileExt(chapterFile.name).toLowerCase(Locale.ROOT)
                val imageExt = listOf("jpg", "jpeg", "gif", "png", "bmp", "webp", "svg")
                val fileUrl = "__API_ROOT__" + book.bookUrl.replace("\\", "/")
                    .replace("storage/data/", "/book-assets/") + "/index/" + chapterInfo.url
                if (!imageExt.contains(ext)) {
                    return returnData.setData(fileUrl)
                }
                return returnData.setData("<img src='$fileUrl' />")
            }
            if (book.isPdf()) {
                if (!convertPdfToImage(book)) {
                    return returnData.setErrorMsg("PDF生成图片失败")
                }
                var content = ""
                val start = chapterInfo.start
                val end = chapterInfo.end
                if (start != null && end != null && start <= end) {
                    var i = start.toInt()
                    val endIndex = end.toInt()
                    while (i <= endIndex) {
                        convertPdfPageToImage(book, i, refresh > 0)
                        val chapterFilePath = getWorkDir(book.bookUrl, "index", "output-$i.png")
                        logger.info("chapterFilePath: {}", chapterFilePath)
                        val chapterFile = File(chapterFilePath)
                        if (!chapterFile.exists()) {
                            return returnData.setErrorMsg("章节文件不存在")
                        }
                        val fileUrl = "__API_ROOT__" + book.bookUrl.replace("\\", "/")
                            .replace("storage/data/", "/book-assets/") + "/index/output-$i.png"
                        content += "<img src='$fileUrl' />"
                        i++
                    }
                }
                return returnData.setData(content)
            }
            val bookContent = LocalBook.getContent(book, chapterInfo)
                ?: return returnData.setErrorMsg("获取章节内容失败")
            return returnData.setData(bookContent)
        }
        var chapterCacheFile: File? = null
        if (book.isInShelf && appConfig.cacheChapterContent) {
            val localCacheDir = getChapterCacheDir(book, userNameSpace)
            chapterCacheFile =
                File(localCacheDir.absolutePath + File.separator + chapterIndex + ".txt")
            if (refresh <= 0 && chapterCacheFile.exists()) {
                var content = chapterCacheFile.readText()
                if (content.indexOf("<img") >= 0) {
                    content = updateImageLinkInContent(book, chapterInfo, content)
                }
                logger.info("使用缓存的章节内容: {}", chapterCacheFile.toString())
                return returnData.setData(content)
            }
        }
        var content = WebBook(bookSource ?: "", appConfig.debugLog, userNameSpace = userNameSpace)
            .getBookContent(book, chapterInfo, nextChapterUrl)
        if (appConfig.cacheChapterContent && chapterCacheFile != null) {
            chapterCacheFile.writeText(content)
        }
        try {
            val bookSourceObject = BookSource.fromJson(bookSource ?: "").getOrNull() ?: BookSource()
            BookHelp.saveImages(this, bookSourceObject, book, chapterInfo, content)
            content = updateImageLinkInContent(book, chapterInfo, content)
        } catch (e: Exception) {
            if (!bookSource.isNullOrEmpty()) {
                val bookSourceObject = BookSource.fromJson(bookSource).getOrNull()
                if (bookSourceObject != null) {
                    val info = mutableMapOf<String, Any>(
                        "sourceUrl" to bookSourceObject.bookSourceUrl,
                        "time" to System.currentTimeMillis(),
                        "error" to e.toString()
                    )
                    addInvalidBookSource(bookSourceObject.bookSourceUrl, info, userNameSpace)
                }
            }
            throw e
        }
        return returnData.setData(content)
    }

    suspend fun saveBookContent(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        val bookUrl = context.bodyAsJson.getString("url") ?: ""
        val chapterIndex = context.bodyAsJson.getInteger("index", -1)
        val content = context.bodyAsJson.getString("content") ?: ""
        if (bookUrl.isEmpty()) {
            return returnData.setErrorMsg("请输入书籍链接")
        }
        val userNameSpace = getUserNameSpace(context)
        val bookInfo = getShelfBookByURL(bookUrl, userNameSpace)
        if (bookInfo == null) {
            return returnData.setErrorMsg("获取书籍信息失败")
        }
        val localCacheDir = getChapterCacheDir(bookInfo, userNameSpace)
        val chapterCacheFile =
            File(localCacheDir.absolutePath + File.separator + chapterIndex + ".txt")
        chapterCacheFile.writeText(content)
        val customCacheDirPath = getWorkDir(
            "storage", "data", userNameSpace, bookInfo.name + '_' + bookInfo.author, "custom"
        )
        val customCacheDir = File(customCacheDirPath)
        if (!customCacheDir.exists()) {
            customCacheDir.mkdirs()
        }
        val cacheFile = File(customCacheDir.absolutePath + File.separator + chapterIndex + ".txt")
        cacheFile.writeText(content)
        return returnData.setData("")
    }

    private fun updateImageLinkInContent(book: Book, chapter: BookChapter, content: String): String {
        val data = StringBuilder("")
        val dataDir = getWorkDir("storage", "data")
        content.split("\n").forEach { text ->
            var text1 = text
            val matcher = AppPattern.imgPattern.matcher(text)
            while (matcher.find()) {
                val src = matcher.group(1) ?: continue
                val imageUrlSrc = NetworkUtils.getAbsoluteURL(chapter.url, src)
                val imageFile = BookHelp.getImage(book, imageUrlSrc)
                if (src.indexOf("__API_ROOT__") >= 0 || !imageFile.exists()) continue
                val imageUrl = "__API_ROOT__" + imageFile.path.replace(dataDir, "/book-assets")
                text1 = text1.replace(src, imageUrl + "\" data-error=\"" + src)
            }
            data.append(text1).append("\n")
        }
        return data.toString()
    }

    suspend fun exploreBook(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        checkAuth(context)
        val bookSource = getBookSourceString(context)
        if (bookSource.isNullOrEmpty()) {
            return returnData.setErrorMsg("未配置书源")
        }
        var page: Int
        var ruleFindUrl: String
        if (context.request().method() == HttpMethod.POST) {
            ruleFindUrl = context.bodyAsJson.getString("ruleFindUrl")
            page = context.bodyAsJson.getInteger("page", 1)
        } else {
            ruleFindUrl = context.queryParam("ruleFindUrl").firstOrNull() ?: ""
            page = context.queryParam("page").firstOrNull()?.toInt() ?: 1
        }
        val userNameSpace = getUserNameSpace(context)
        val result = WebBook(bookSource, false, userNameSpace = userNameSpace)
            .exploreBook(ruleFindUrl, page)
        return returnData.setData(result)
    }

    suspend fun searchBook(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        checkAuth(context)
        val bookSource = getBookSourceString(context)
        if (bookSource.isNullOrEmpty()) {
            return returnData.setErrorMsg("未配置书源")
        }
        var key: String
        var page: Int
        if (context.request().method() == HttpMethod.POST) {
            key = context.bodyAsJson.getString("key")
            page = context.bodyAsJson.getInteger("page", 1)
        } else {
            key = context.queryParam("key").firstOrNull() ?: ""
            page = context.queryParam("page").firstOrNull()?.toInt() ?: 1
        }
        if (key.isNullOrEmpty()) {
            return returnData.setErrorMsg("请输入搜索关键字")
        }
        val userNameSpace = getUserNameSpace(context)
        val result = WebBook(bookSource, appConfig.debugLog, userNameSpace = userNameSpace)
            .searchBook(key, page)
        return returnData.setData(result)
    }

    suspend fun searchBookMulti(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        var key: String
        var lastIndex = 0
        var searchSize = 0
        var bookSourceGroup = ""
        var concurrentCount: Int
        if (context.request().method() == HttpMethod.POST) {
            key = context.bodyAsJson.getString("key", "")
            bookSourceGroup = context.bodyAsJson.getString("bookSourceGroup", "")
            lastIndex = context.bodyAsJson.getInteger("lastIndex", -1)
            searchSize = context.bodyAsJson.getInteger("searchSize", 20)
            concurrentCount = context.bodyAsJson.getInteger("concurrentCount", 36)
        } else {
            key = context.queryParam("key").firstOrNull() ?: ""
            bookSourceGroup = context.queryParam("bookSourceGroup").firstOrNull() ?: ""
            lastIndex = context.queryParam("lastIndex").firstOrNull()?.toInt() ?: -1
            searchSize = context.queryParam("searchSize").firstOrNull()?.toInt() ?: 20
            concurrentCount = context.queryParam("concurrentCount").firstOrNull()?.toInt() ?: 36
        }
        var userNameSpace = getUserNameSpace(context)
        val bookSourceController = BookSourceController(coroutineContext)
        val urlMap = bookSourceController.getBookSourceMap(userNameSpace)
        if (urlMap.size <= 0) {
            return returnData.setErrorMsg("未配置书源")
        }
        if (key.length == 0) {
            return returnData.setErrorMsg("请输入搜索关键字")
        }
        var accurate = false
        if (key.startsWith("=", true)) {
            accurate = true
            key = key.replaceFirst("=", "")
        }
        if (key.isNullOrEmpty()) {
            return returnData.setErrorMsg("请输入搜索关键字")
        }
        if (lastIndex >= urlMap.size - 1) {
            return returnData.setErrorMsg("没有更多了")
        }
        searchSize = if (searchSize > 0) searchSize else 20
        concurrentCount = if (concurrentCount > 0) concurrentCount else 36
        logger.info("searchBookMulti from lastIndex: {} searchSize: {}", lastIndex, searchSize)
        var isEnd = false
        context.request().connection().closeHandler {
            logger.info("客户端已断开连接，停止 searchBookMulti")
            isEnd = true
            coroutineContext.cancel()
        }
        var resultList = arrayListOf<SearchBook>()
        var resultMap = mutableMapOf<String, Int>()
        val book = Book()
        book.name = key
        var maxSize = urlMap.size
        var bookSourceFile = getStorageFile("data", userNameSpace, "bookSource")
        if (!bookSourceFile.exists()) {
            bookSourceFile = getStorageFile("data", "default", "bookSource")
        }
        limitConcurrent(
            concurrentCount, lastIndex + 1, urlMap.size,
            { index ->
                if (index > maxSize) {
                    arrayListOf<SearchBook>()
                } else {
                    lastIndex = max(lastIndex, index)
                    val bookSourceList = parseJsonStringList(
                        bookSourceFile, startIndex = index, endIndex = index,
                        filter = if (bookSourceGroup.isEmpty()) null else { it: ObjectNode ->
                            val group = it.get("bookSourceGroup").asText()
                            !group.isNullOrEmpty() && ("$group,").indexOf("$bookSourceGroup,") >= 0
                        }
                    )
                    if (bookSourceList == null || bookSourceList.isEmpty()) {
                        maxSize = index
                        arrayListOf<SearchBook>()
                    } else {
                        searchBookWithSource(
                            bookSourceList.getString(0), book, accurate, userNameSpace
                        )
                    }
                }
            },
            { list, loopCount ->
                list.forEach {
                    (it as? Collection<*>)?.forEach { b ->
                        val searchBook = b as SearchBook
                        val bookKey = searchBook.name + "_" + searchBook.author
                        if (!resultMap.containsKey(bookKey)) {
                            resultList.add(searchBook)
                            resultMap[bookKey] = 1
                        }
                    }
                }
                logger.info("Loop: {} resultList.size: {}", loopCount, resultList.size)
                !(isEnd || loopCount >= concurrentLoopCount) && resultList.size < searchSize
            }
        )
        return returnData.setData(mapOf("lastIndex" to lastIndex, "list" to resultList))
    }

    suspend fun searchBookMultiSSE(context: RoutingContext) {
        val returnData = ReturnData()
        val response = context.response().putHeader("Content-Type", "text/event-stream")
            .putHeader("Cache-Control", "no-cache").setChunked(true)
        if (!checkAuth(context)) {
            response.write("event: error\n")
            response.end(
                "data: " + jsonEncode(
                    returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用"), false
                ) + "\n\n"
            )
            return
        }
        var key: String
        var lastIndex = 0
        var searchSize = 0
        var bookSourceGroup = ""
        var concurrentCount: Int
        if (context.request().method() == HttpMethod.POST) {
            key = context.bodyAsJson.getString("key", "")
            bookSourceGroup = context.bodyAsJson.getString("bookSourceGroup", "")
            lastIndex = context.bodyAsJson.getInteger("lastIndex", -1)
            searchSize = context.bodyAsJson.getInteger("searchSize", 50)
            concurrentCount = context.bodyAsJson.getInteger("concurrentCount", 24)
        } else {
            key = context.queryParam("key").firstOrNull() ?: ""
            bookSourceGroup = context.queryParam("bookSourceGroup").firstOrNull() ?: ""
            lastIndex = context.queryParam("lastIndex").firstOrNull()?.toInt() ?: -1
            searchSize = context.queryParam("searchSize").firstOrNull()?.toInt() ?: 50
            concurrentCount = context.queryParam("concurrentCount").firstOrNull()?.toInt() ?: 24
        }
        var userNameSpace = getUserNameSpace(context)
        val bookSourceController = BookSourceController(coroutineContext)
        val urlMap = bookSourceController.getBookSourceMap(userNameSpace)
        if (urlMap.size <= 0) {
            response.write("event: error\n")
            response.end("data: " + jsonEncode(returnData.setErrorMsg("未配置书源"), false)!! + "\n\n")
            return
        }
        if (key.length == 0) {
            response.write("event: error\n")
            response.end(
                "data: " + jsonEncode(returnData.setErrorMsg("请输入搜索关键字"), false)!! + "\n\n"
            )
            return
        }
        var accurate = false
        if (key.startsWith("=", true)) {
            accurate = true
            key = key.replaceFirst("=", "")
        }
        if (key.isNullOrEmpty()) {
            response.write("event: error\n")
            response.end(
                "data: " + jsonEncode(returnData.setErrorMsg("请输入搜索关键字"), false)!! + "\n\n"
            )
            return
        }
        if (lastIndex >= urlMap.size - 1) {
            response.write("event: error\n")
            response.end("data: " + jsonEncode(returnData.setErrorMsg("没有更多了"), false)!! + "\n\n")
            return
        }
        searchSize = if (searchSize > 0) searchSize else 50
        concurrentCount = if (concurrentCount > 0) concurrentCount else 24
        logger.info(
            "searchBookMulti from lastIndex: {} concurrentCount: {} searchSize: {}",
            lastIndex, concurrentCount, searchSize
        )
        var isEnd = false
        context.request().connection().closeHandler {
            logger.info("客户端已断开连接，停止 searchBookMultiSSE")
            isEnd = true
            coroutineContext.cancel()
        }
        var resultList = arrayListOf<SearchBook>()
        val book = Book()
        book.name = key
        var maxSize = urlMap.size
        var bookSourceFile = getStorageFile("data", userNameSpace, "bookSource")
        if (!bookSourceFile.exists()) {
            bookSourceFile = getStorageFile("data", "default", "bookSource")
        }
        limitConcurrent(
            concurrentCount, lastIndex + 1, urlMap.size,
            { index ->
                if (index > maxSize) {
                    arrayListOf<SearchBook>()
                } else {
                    lastIndex = max(lastIndex, index)
                    val bookSourceList = parseJsonStringList(
                        bookSourceFile, startIndex = index, endIndex = index,
                        filter = if (bookSourceGroup.isEmpty()) null else { it: ObjectNode ->
                            val group = it.get("bookSourceGroup").asText()
                            !group.isNullOrEmpty() && ("$group,").indexOf("$bookSourceGroup,") >= 0
                        }
                    )
                    if (bookSourceList == null || bookSourceList.isEmpty()) {
                        maxSize = index
                        arrayListOf<SearchBook>()
                    } else {
                        searchBookWithSource(
                            bookSourceList.getString(0), book, accurate, userNameSpace
                        )
                    }
                }
            },
            { list, loopCount ->
                val loopResult = arrayListOf<SearchBook>()
                list.forEach {
                    (it as? Collection<*>)?.forEach { b ->
                        val searchBook = b as SearchBook
                        resultList.add(searchBook)
                        loopResult.add(searchBook)
                    }
                }
                response.write(
                    "data: " + jsonEncode(
                        mapOf("lastIndex" to lastIndex, "data" to loopResult), false
                    ) + "\n\n"
                )
                logger.info("Loop: {} resultList.size: {}", loopCount, resultList.size)
                !(isEnd || loopCount >= concurrentLoopCount) && resultList.size < searchSize
            }
        )
        response.write("event: end\n")
        response.end(
            "data: " + jsonEncode(
                mapOf("lastIndex" to lastIndex, "isEnd" to (lastIndex >= maxSize)), false
            ) + "\n\n"
        )
    }

    suspend fun searchBookSource(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        var bookUrl: String
        val lastIndex = Ref.IntRef()
        val searchSize = Ref.IntRef()
        val bookSourceGroup = Ref.ObjectRef<String>()
        if (context.request().method() == HttpMethod.POST) {
            bookUrl = context.bodyAsJson.getString("url")
            lastIndex.element = context.bodyAsJson.getInteger("lastIndex", -1)
            searchSize.element = context.bodyAsJson.getInteger("searchSize", 5)
            bookSourceGroup.element = context.bodyAsJson.getString("bookSourceGroup", "")
        } else {
            bookUrl = context.queryParam("url").firstOrNull() ?: ""
            lastIndex.element = context.queryParam("lastIndex").firstOrNull()?.toInt() ?: -1
            searchSize.element = context.queryParam("searchSize").firstOrNull()?.toInt() ?: 5
            bookSourceGroup.element = context.queryParam("bookSourceGroup").firstOrNull() ?: ""
        }
        val userNameSpace = Ref.ObjectRef<String>()
        userNameSpace.element = getUserNameSpace(context)
        val bookSourceController = BookSourceController(coroutineContext)
        val urlMap = bookSourceController.getBookSourceMap(userNameSpace.element)
        if (urlMap.size <= 0) {
            return returnData.setErrorMsg("未配置书源")
        }
        if (bookUrl.isEmpty()) {
            return returnData.setErrorMsg("请输入书籍链接")
        }
        if (lastIndex.element >= urlMap.size - 1) {
            return returnData.setErrorMsg("没有更多了")
        }
        val book = Ref.ObjectRef<Book>()
        book.element = getShelfBookByURL(bookUrl, userNameSpace.element)
        if (book.element == null) {
            book.element = bookInfoCache.getAsString(bookUrl)?.toMap()?.toDataClass<Book>()
        }
        if (book.element == null) {
            return returnData.setErrorMsg("书籍信息错误")
        }
        logger.info("searchBookSource from lastIndex: {}", lastIndex.element)
        val isEnd = Ref.BooleanRef()
        context.request().connection().closeHandler {
            logger.info("客户端已断开连接，停止 searchBookSource")
            isEnd.element = true
            coroutineContext.cancel()
        }
        searchSize.element = if (searchSize.element > 0) searchSize.element else 5
        val resultList = Ref.ObjectRef<ArrayList<SearchBook>>()
        resultList.element = arrayListOf()
        val concurrentCount = max(searchSize.element * 2, 24)
        val maxSize = Ref.IntRef()
        maxSize.element = urlMap.size
        val bookSourceFile = Ref.ObjectRef<File>()
        bookSourceFile.element = getStorageFile("data", userNameSpace.element, "bookSource")
        if (!bookSourceFile.element!!.exists()) {
            bookSourceFile.element = getStorageFile("data", "default", "bookSource")
        }
        limitConcurrent(
            concurrentCount, lastIndex.element + 1, urlMap.size,
            { index ->
                if (index > maxSize.element) {
                    arrayListOf<SearchBook>()
                } else {
                    lastIndex.element = max(lastIndex.element, index)
                    val bookSourceList = parseJsonStringList(
                        bookSourceFile.element!!, startIndex = index, endIndex = index,
                        filter = if (bookSourceGroup.element!!.isEmpty()) null else { it: ObjectNode ->
                            val group = it.get("bookSourceGroup").asText()
                            !group.isNullOrEmpty() &&
                                    ("$group,").indexOf(bookSourceGroup.element + ",") >= 0
                        }
                    )
                    if (bookSourceList == null || bookSourceList.isEmpty()) {
                        maxSize.element = index
                        arrayListOf<SearchBook>()
                    } else {
                        searchBookWithSource(
                            bookSourceList.getString(0), book.element!!, false, userNameSpace.element
                        )
                    }
                }
            },
            { list, loopCount ->
                list.forEach { it ->
                    (it as? Collection<*>)?.let { c ->
                        @Suppress("UNCHECKED_CAST")
                        resultList.element.addAll(c as Collection<SearchBook>)
                    }
                }
                !(isEnd.element || loopCount >= concurrentLoopCount) &&
                        resultList.element.size < searchSize.element
            }
        )
        saveBookSources(book.element!!, resultList.element, userNameSpace.element)
        return returnData.setData(mapOf("lastIndex" to lastIndex.element, "list" to resultList.element))
    }

    suspend fun searchBookSourceSSE(context: RoutingContext) {
        val returnData = ReturnData()
        val response = context.response().putHeader("Content-Type", "text/event-stream")
            .putHeader("Cache-Control", "no-cache").setChunked(true)
        if (!checkAuth(context)) {
            response.write("event: error\n")
            response.end(
                "data: " + jsonEncode(
                    returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用"), false
                ) + "\n\n"
            )
            return
        }
        var bookUrl: String
        val lastIndex = Ref.IntRef()
        val searchSize = Ref.IntRef()
        val bookSourceGroup = Ref.ObjectRef<String>()
        var refresh = 0
        if (context.request().method() == HttpMethod.POST) {
            bookUrl = context.bodyAsJson.getString("url") ?: ""
            lastIndex.element = context.bodyAsJson.getInteger("lastIndex", -1)
            searchSize.element = context.bodyAsJson.getInteger("searchSize", 30)
            bookSourceGroup.element = context.bodyAsJson.getString("bookSourceGroup", "")
            refresh = context.bodyAsJson.getInteger("refresh", 0)
        } else {
            bookUrl = context.queryParam("url").firstOrNull() ?: ""
            lastIndex.element = context.queryParam("lastIndex").firstOrNull()?.toInt() ?: -1
            searchSize.element = context.queryParam("searchSize").firstOrNull()?.toInt() ?: 30
            bookSourceGroup.element = context.queryParam("bookSourceGroup").firstOrNull() ?: ""
            refresh = context.queryParam("refresh").firstOrNull()?.toInt() ?: 0
        }
        val userNameSpace = Ref.ObjectRef<String>()
        userNameSpace.element = getUserNameSpace(context)
        val bookSourceController = BookSourceController(coroutineContext)
        val urlMap = bookSourceController.getBookSourceMap(userNameSpace.element)
        if (urlMap.size <= 0) {
            response.write("event: error\n")
            response.end("data: " + jsonEncode(returnData.setErrorMsg("未配置书源"), false)!! + "\n\n")
            return
        }
        if (bookUrl.isEmpty()) {
            response.write("event: error\n")
            response.end(
                "data: " + jsonEncode(returnData.setErrorMsg("请输入书籍链接"), false)!! + "\n\n"
            )
            return
        }
        val book = Ref.ObjectRef<Book>()
        book.element = getShelfBookByURL(bookUrl, userNameSpace.element)
        if (book.element == null) {
            book.element = bookInfoCache.getAsString(bookUrl)?.toMap()?.toDataClass<Book>()
        }
        if (book.element == null) {
            response.write("event: error\n")
            response.end(
                "data: " + jsonEncode(returnData.setErrorMsg("书籍信息错误"), false)!! + "\n\n"
            )
            return
        }
        if (lastIndex.element >= urlMap.size - 1) {
            response.write("event: error\n")
            response.end(
                "data: " + jsonEncode(
                    returnData.setData(mapOf("lastIndex" to lastIndex.element))
                        .setErrorMsg("没有更多了"), false
                ) + "\n\n"
            )
            return
        }
        searchSize.element = if (searchSize.element > 0) searchSize.element else 30
        val resultList = Ref.ObjectRef<ArrayList<SearchBook>>()
        resultList.element = arrayListOf()
        val concurrentCount = max(searchSize.element * 2, 24)
        logger.info(
            "searchBookMulti from lastIndex: {} concurrentCount: {} searchSize: {}",
            lastIndex.element, concurrentCount, searchSize.element
        )
        val isEnd = Ref.BooleanRef()
        context.request().connection().closeHandler {
            logger.info("客户端已断开连接，停止 searchBookSourceSSE")
            isEnd.element = true
            coroutineContext.cancel()
        }
        val bookSourceFile = Ref.ObjectRef<File>()
        bookSourceFile.element = getStorageFile("data", userNameSpace.element, "bookSource")
        if (!bookSourceFile.element!!.exists()) {
            bookSourceFile.element = getStorageFile("data", "default", "bookSource")
        }
        val maxSize = Ref.IntRef()
        maxSize.element = urlMap.size
        limitConcurrent(
            concurrentCount, lastIndex.element + 1, urlMap.size,
            { index ->
                if (index > maxSize.element) {
                    arrayListOf<SearchBook>()
                } else {
                    lastIndex.element = max(lastIndex.element, index)
                    val bookSourceList = parseJsonStringList(
                        bookSourceFile.element!!, startIndex = index, endIndex = index,
                        filter = if (bookSourceGroup.element!!.isEmpty()) null else { it: ObjectNode ->
                            val group = it.get("bookSourceGroup").asText()
                            !group.isNullOrEmpty() &&
                                    ("$group,").indexOf(bookSourceGroup.element + ",") >= 0
                        }
                    )
                    if (bookSourceList == null || bookSourceList.isEmpty()) {
                        maxSize.element = index
                        arrayListOf<SearchBook>()
                    } else {
                        searchBookWithSource(
                            bookSourceList.getString(0), book.element!!, false, userNameSpace.element
                        )
                    }
                }
            },
            { list, loopCount ->
                val loopResult = arrayListOf<SearchBook>()
                list.forEach { it ->
                    (it as? Collection<*>)?.let { c ->
                        @Suppress("UNCHECKED_CAST")
                        resultList.element.addAll(c as Collection<SearchBook>)
                        @Suppress("UNCHECKED_CAST")
                        loopResult.addAll(c as Collection<SearchBook>)
                    }
                }
                response.write(
                    "data: " + jsonEncode(
                        mapOf("lastIndex" to lastIndex.element, "data" to loopResult), false
                    ) + "\n\n"
                )
                logger.info("Loop: {} resultList.size: {}", loopCount, resultList.element.size)
                !(isEnd.element || loopCount >= concurrentLoopCount) &&
                        resultList.element.size < searchSize.element
            }
        )
        saveBookSources(book.element!!, resultList.element, userNameSpace.element)
        response.write("event: end\n")
        response.end(
            "data: " + jsonEncode(
                mapOf(
                    "lastIndex" to lastIndex.element,
                    "isEnd" to (lastIndex.element >= maxSize.element)
                ), false
            ) + "\n\n"
        )
    }

    suspend fun searchBookWithSource(
        bookSourceString: String,
        book: Book,
        accurate: Boolean = true,
        userNameSpace: String = "default"
    ): ArrayList<SearchBook> {
        var resultList = arrayListOf<SearchBook>()
        var bookSource = BookSource.fromJson(bookSourceString).getOrNull()
        if (bookSource == null) {
            return resultList
        }
        if (isInvalidBookSource(bookSource, userNameSpace)) {
            return resultList
        }
        withContext(Dispatchers.IO) {
            val start = System.currentTimeMillis()
            try {
                val result = WebBook(bookSource, false, userNameSpace = userNameSpace)
                    .searchBook(book.name, 1)
                val end = System.currentTimeMillis()
                if (result.isNotEmpty()) {
                    for (_book in result) {
                        if (accurate && _book.name == book.name) {
                            if (book.author.isEmpty() || _book.author == book.author) {
                                _book.time = end - start
                                resultList.add(_book)
                                continue
                            }
                        }
                        if (!accurate && (_book.name.contains(book.name, true) ||
                                _book.author.contains(book.name, true))
                        ) {
                            _book.time = end - start
                            resultList.add(_book)
                        }
                    }
                }
            } catch (e: Exception) {
                val info: MutableMap<String, Any> = mutableMapOf(
                    "sourceUrl" to (bookSource.bookSourceUrl ?: ""),
                    "time" to System.currentTimeMillis(),
                    "error" to e.toString()
                )
                addInvalidBookSource(bookSource.bookSourceUrl ?: "", info, userNameSpace)
                e.printStackTrace()
            }
        }
        return resultList
    }

    suspend fun getAvailableBookSource(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        var bookUrl: String
        var refresh = 0
        if (context.request().method() == HttpMethod.POST) {
            bookUrl = context.bodyAsJson.getString("url")
            refresh = context.bodyAsJson.getInteger("refresh", 0)
        } else {
            bookUrl = context.queryParam("url").firstOrNull() ?: ""
            refresh = context.queryParam("refresh").firstOrNull()?.toInt() ?: 0
        }
        if (bookUrl.isNullOrEmpty()) {
            return returnData.setErrorMsg("请输入书籍链接")
        }
        var userNameSpace = getUserNameSpace(context)
        var book = getShelfBookByURL(bookUrl, userNameSpace)
        if (book == null) {
            book = bookInfoCache.getAsString(bookUrl)?.toMap()?.toDataClass<Book>()
        }
        if (book == null) {
            return returnData.setErrorMsg("书籍信息错误")
        }
        var bookSourceList = asJsonArray(getUserStorage(userNameSpace, book.name + "_" + book.author, "bookSource"))
        if (bookSourceList != null && bookSourceList.size() > 0) {
            if (refresh <= 0) {
                return returnData.setData(bookSourceList.getList())
            }
            var resultList = arrayListOf<SearchBook>()
            val concurrentCount = 16
            limitConcurrent(
                concurrentCount, 0, bookSourceList.size(),
                { index ->
                    val searchBook = bookSourceList.getJsonObject(index)
                        .mapTo(SearchBook::class.java)
                    if (searchBook.origin.equals("loc_book")) {
                        arrayListOf(searchBook)
                    } else {
                        val bookSource =
                            (getBookSourceStringBySourceURLOpt(searchBook.origin, userNameSpace) ?: "")
                        if (bookSource == null) {
                            arrayListOf<SearchBook>()
                        } else {
                            searchBookWithSource(
                                bookSource, book, false, userNameSpace
                            )
                        }
                    }
                },
                { list, _ ->
                    list.forEach { it ->
                        (it as? Collection<*>)?.let { c ->
                            @Suppress("UNCHECKED_CAST")
                            resultList.addAll(c as Collection<SearchBook>)
                        }
                    }
                    true
                }
            )
            saveBookSources(book, resultList, userNameSpace, true)
            return returnData.setData(resultList)
        }
        return returnData.setData(arrayListOf<SearchBook>())
    }

    suspend fun getBookshelf(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        var refresh = 0
        if (context.request().method() == HttpMethod.POST) {
            refresh = context.bodyAsJson.getInteger("refresh", 0)
        } else {
            refresh = context.queryParam("refresh").firstOrNull()?.toIntOrNull() ?: 0
        }
        val bookList = getBookShelfBooks(refresh > 0, getUserNameSpace(context))
        return returnData.setData(bookList)
    }

    suspend fun getShelfBook(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        var url: String
        if (context.request().method() == HttpMethod.POST) {
            url = context.bodyAsJson.getString("url")
        } else {
            url = context.queryParam("url").firstOrNull() ?: ""
        }
        if (url.isNullOrEmpty()) {
            return returnData.setErrorMsg("书源链接不能为空")
        }
        val book = getShelfBookByURL(url, getUserNameSpace(context))
        if (book == null) {
            return returnData.setErrorMsg("书籍不存在")
        }
        return returnData.setData(book)
    }

    suspend fun saveBook(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        var book = context.bodyAsJson.mapTo(Book::class.java)
        val userNameSpace = getUserNameSpace(context)
        if (!book.isLocalBook()) {
            val bookSource = getBookSourceStringBySourceURLOpt(book.origin, userNameSpace)
            if (bookSource == null) {
                return returnData.setErrorMsg("书源信息错误")
            }
            if (book.tocUrl.isNullOrEmpty()) {
                book = WebBook(bookSource, appConfig.debugLog, userNameSpace = userNameSpace)
                    .getBookInfo(book)
                book = mergeBookCacheInfo(book)
            }
            saveBookCover(book, userNameSpace, bookSource)
        } else {
            saveLocalBookCover(book, userNameSpace)
        }
        val result = saveBookToShelf(book, userNameSpace, context)
        if (result.second != null) {
            return returnData.setErrorMsg(result.second ?: "")
        }
        return returnData.setData(result.first)
    }

    // TODO(restore): 本方法尚未按 CFR 还原，先返回占位错误以保证 ABI 不新增差异。
    fun saveBookToShelf(_book: Book, userNameSpace: String, context: RoutingContext): Pair<Book, String?> {
        var book = _book
        if (book.origin.isNullOrEmpty()) {
            return Pair(book, "未找到书源信息")
        }
        if (book.bookUrl.isNullOrEmpty()) {
            return Pair(book, "书籍链接不能为空")
        }
        var bookshelf: JsonArray? = asJsonArray(getUserStorage(userNameSpace, "bookshelf"))
        if (bookshelf == null) {
            bookshelf = JsonArray()
        }
        var existIndex = -1
        for (i in 0 until bookshelf.size()) {
            val name = bookshelf.getJsonObject(i).getString("name", "")
            val author = bookshelf.getJsonObject(i).getString("author", "")
            if (name.equals(book.name) && author.equals(book.author)) {
                existIndex = i
                break
            }
        }
        if (existIndex < 0) {
            val userInfo = context.get<User>("userInfo")
            if (userInfo != null && bookshelf.size() >= userInfo.book_limit) {
                return Pair(book, "你已达到书籍数上限，请联系管理员")
            }
        }
        if (book.isLocalBook()) {
            if (book.bookUrl.startsWith("/assets/") || book.bookUrl.startsWith("assets/")) {
                val tempFile = File(getWorkDir("storage" + book.bookUrl))
                if (!tempFile.exists()) {
                    return Pair(book, "上传书籍不存在")
                }
                val relativeLocalFilePath = Paths.get(
                    "storage", "data", userNameSpace,
                    book.name + "_" + book.author, tempFile.name
                ).toString()
                val relativeLocalFileUrl = "storage/data/" + userNameSpace + "/" + book.name + "_" + book.author + "/" + tempFile.name
                val localFilePath = getWorkDir(relativeLocalFilePath)
                logger.info("localFilePath: {}", localFilePath)
                var localFile = File(localFilePath)
                localFile.deleteRecursively()
                if (!localFile.parentFile.exists()) {
                    localFile.parentFile.mkdirs()
                }
                if (!tempFile.copyRecursively(localFile)) {
                    return Pair(book, "导入本地书籍失败")
                }
                tempFile.deleteRecursively()
                book.bookUrl = relativeLocalFileUrl
                book.originName = relativeLocalFilePath
                if (book.isEpub()) {
                    if (!extractEpub(book)) {
                        return Pair(book, "导入本地Epub书籍失败")
                    }
                } else if (book.isCbz()) {
                    if (!extractCbz(book)) {
                        return Pair(book, "导入本地CBZ书籍失败")
                    }
                } else if (book.isPdf()) {
                    if (!convertPdfToImage(book)) {
                        return Pair(book, "本地PDF书籍转换失败")
                    }
                }
            } else if (book.bookUrl.indexOf("localStore") >= 0) {
                val tempFile = File(getWorkDir(book.bookUrl))
                if (!tempFile.exists()) {
                    return Pair(book, "本地书仓书籍不存在")
                }
                val relativeLocalFileUrl = "storage/data/" + userNameSpace + "/" + book.name + "_" + book.author + "/" + tempFile.name
                book.bookUrl = relativeLocalFileUrl
                if (book.isEpub()) {
                    if (!extractEpub(book)) {
                        return Pair(book, "导入本地Epub书籍失败")
                    }
                } else if (book.isCbz()) {
                    if (!extractCbz(book)) {
                        return Pair(book, "导入本地CBZ书籍失败")
                    }
                } else if (book.isPdf()) {
                    if (!convertPdfToImage(book)) {
                        return Pair(book, "本地PDF书籍转换失败")
                    }
                }
            } else if (book.bookUrl.indexOf("webdav") >= 0) {
                val tempFile = File(getWorkDir(book.bookUrl))
                if (!tempFile.exists()) {
                    return Pair(book, "webdav书仓书籍不存在")
                }
                val relativeLocalFileUrl = "storage/data/" + userNameSpace + "/" + book.name + "_" + book.author + "/" + tempFile.name
                book.bookUrl = relativeLocalFileUrl
                if (book.isEpub()) {
                    if (!extractEpub(book)) {
                        return Pair(book, "导入本地Epub书籍失败")
                    }
                } else if (book.isCbz()) {
                    if (!extractCbz(book)) {
                        return Pair(book, "导入本地CBZ书籍失败")
                    }
                } else if (book.isPdf()) {
                    if (!convertPdfToImage(book)) {
                        return Pair(book, "本地PDF书籍转换失败")
                    }
                }
            }
        }
        book.isInShelf = true
        if (existIndex >= 0) {
            var bookList = bookshelf.getList()
            var existBook = bookshelf.getJsonObject(existIndex).mapTo(Book::class.java)
            book.durChapterIndex = existBook.durChapterIndex
            book.durChapterTitle = existBook.durChapterTitle
            book.durChapterTime = existBook.durChapterTime
            if (!existBook.getDisplayCover().isNullOrEmpty() && existBook.getDisplayCover()!!.startsWith("/") &&
                !existBook.getDisplayCover()!!.equals(book.getDisplayCover())
            ) {
                val cachePath = getWorkDir("storage", existBook.getDisplayCover()!!)
                FileUtils.deleteFile(cachePath)
            }
            bookList.set(existIndex, JsonObject.mapFrom(book))
            bookshelf = JsonArray(bookList)
        } else {
            bookshelf.add(JsonObject.mapFrom(book))
        }
        val sourceList = listOf(book.toSearchBook())
        saveBookSources(book, sourceList, userNameSpace)
        saveUserStorage(userNameSpace, "bookshelf", bookshelf)
        return Pair(book, null)
    }

    private suspend fun saveLocalBookCover(book: Book, userNameSpace: String) {
        val coverUrl = book.getDisplayCover()
        if (coverUrl != null && !coverUrl.startsWith("/")) {
        val ext = getFileExt(coverUrl, "jpg")
        val md5Encode = MD5Utils.md5Encode(coverUrl).toString()
        val cachePath = getWorkDir("storage", "assets", userNameSpace, "covers", "$md5Encode.$ext")
        val cachedCoverUrl = "/assets/$userNameSpace/covers/$md5Encode.$ext"
        val cacheFile = File(cachePath)
        if (cacheFile.exists()) {
            book.coverUrl = cachedCoverUrl
            return
        }
        val result = awaitResult<HttpResponse<Buffer>> { handler ->
            webClient.getAbs(coverUrl).timeout(3000L).send(handler)
        }
        val bodyBytes = result.bodyAsBuffer()?.bytes
        if (bodyBytes != null) {
            cacheFile.writeBytes(bodyBytes)
            book.coverUrl = cachedCoverUrl
        }
        }
    }

    suspend fun saveBookCover(book: Book, userNameSpace: String, bookSource: String? = null) {
        val coverUrl = book.getDisplayCover()
        if (coverUrl != null && !coverUrl.startsWith("/")) {
            val bookSource = bookSource ?: getBookSourceStringBySourceURLOpt(book.origin, userNameSpace)
            val ext = getFileExt(coverUrl, "jpg")
            val md5Encode = MD5Utils.md5Encode(coverUrl).toString()
            val cachePath = getWorkDir("storage", "assets", userNameSpace, "covers", "$md5Encode.$ext")
            val cachedCoverUrl = "/assets/$userNameSpace/covers/$md5Encode.$ext"
            val cacheFile = File(cachePath)
            if (cacheFile.exists()) {
                book.coverUrl = cachedCoverUrl
                return
            }
            val analyzeUrl = AnalyzeUrl(
                coverUrl,
                source = BookSource.fromJson(bookSource!!).getOrNull()
            )
            try {
                analyzeUrl.getByteArrayAwait().let {
                    FileUtils.writeBytes(cachePath, it)
                    book.coverUrl = cachedCoverUrl
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun setBookSource(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        var bookUrl: String
        var newBookUrl: String
        var bookSourceUrl: String
        if (context.request().method() == HttpMethod.POST) {
            bookUrl = context.bodyAsJson.getString("bookUrl")
            newBookUrl = context.bodyAsJson.getString("newUrl")
            bookSourceUrl = context.bodyAsJson.getString("bookSourceUrl")
        } else {
            bookUrl = context.queryParam("bookUrl").firstOrNull() ?: ""
            newBookUrl = context.queryParam("newUrl").firstOrNull() ?: ""
            bookSourceUrl = context.queryParam("bookSourceUrl").firstOrNull() ?: ""
        }
        if (bookUrl.isNullOrEmpty()) {
            return returnData.setErrorMsg("书籍链接不能为空")
        }
        if (newBookUrl.isNullOrEmpty()) {
            return returnData.setErrorMsg("新源书籍链接不能为空")
        }
        if (bookSourceUrl.isNullOrEmpty()) {
            return returnData.setErrorMsg("书源链接不能为空")
        }
        var userNameSpace = getUserNameSpace(context)
        var book = getShelfBookByURL(bookUrl, userNameSpace)
        if (book == null) {
            return returnData.setErrorMsg("书籍信息错误")
        }
        var bookSourceString = getBookSourceStringBySourceURLOpt(bookSourceUrl, userNameSpace)
        var searchBook: Book? = null
        if (bookSourceString.isNullOrEmpty()) {
            val localBookSourceList = asJsonArray(
                getUserStorage(userNameSpace, book.name + "_" + book.author, "bookSource")
            )
            if (localBookSourceList != null) {
                for (i in 0 until localBookSourceList.size()) {
                    var _searchBook = localBookSourceList.getJsonObject(i)
                        .mapTo(SearchBook::class.java)
                    if (_searchBook.bookUrl.equals(newBookUrl)) {
                        searchBook = _searchBook.toBook()
                        break
                    }
                }
            }
            if (searchBook == null) {
                return returnData.setErrorMsg("书源信息错误")
            }
        }
        var newBookInfo = if (searchBook != null) {
            searchBook
        } else {
            if (bookSourceString.isNullOrEmpty()) {
                return returnData.setErrorMsg("书源信息错误")
            }
            WebBook(bookSourceString, appConfig.debugLog, userNameSpace = userNameSpace)
                .getBookInfo(newBookUrl)
        }
        editShelfBook(book, userNameSpace) { existBook ->
            existBook.origin = newBookInfo.origin
            existBook.originName = newBookInfo.originName
            existBook.bookUrl = newBookInfo.bookUrl
            existBook.tocUrl = newBookInfo.tocUrl
            existBook.isInShelf = true
            if (existBook.coverUrl.isNullOrEmpty() && !newBookInfo.coverUrl.isNullOrEmpty()) {
                existBook.coverUrl = newBookInfo.coverUrl
            }
            logger.info("setBookSource: {}", existBook)
            newBookInfo = existBook
            existBook
        }
        try {
            getLocalChapterList(newBookInfo, bookSourceString ?: "", true, userNameSpace)
        } catch (e: Exception) {
        }
        return returnData.setData(newBookInfo)
    }

    suspend fun saveBookConfig(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        var bookUrl: String
        val pdfImageWidth = Ref.FloatRef()
        if (context.request().method() == HttpMethod.POST) {
            bookUrl = context.bodyAsJson.getString("bookUrl")
            pdfImageWidth.element = context.bodyAsJson.getFloat("pdfImageWidth", 0f)
        } else {
            bookUrl = context.queryParam("bookUrl").firstOrNull() ?: ""
            pdfImageWidth.element = context.queryParam("pdfImageWidth").firstOrNull()?.toFloat() ?: 0f
        }
        if (bookUrl.isNullOrEmpty()) {
            return returnData.setErrorMsg("书籍链接不能为空")
        }
        val userNameSpace = getUserNameSpace(context)
        val book = getShelfBookByURL(bookUrl, userNameSpace)
        if (book == null) {
            return returnData.setErrorMsg("书籍信息错误")
        }
        if (pdfImageWidth.element <= 0f) {
            return returnData.setErrorMsg("pdf图片宽度错误")
        }
        val newBook = editShelfBook(book, userNameSpace) { existBook ->
            existBook.setPdfImageWidth(pdfImageWidth.element)
            logger.info("saveBookConfig: {}", existBook)
            existBook
        }
        return returnData.setData(newBook ?: book)
    }

    suspend fun saveBookGroupId(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        var bookUrl: String
        val groupId = Ref.LongRef()
        if (context.request().method() == HttpMethod.POST) {
            bookUrl = context.bodyAsJson.getString("bookUrl")
            groupId.element = context.bodyAsJson.getLong("groupId", 0)
        } else {
            bookUrl = context.queryParam("bookUrl").firstOrNull() ?: ""
            groupId.element = context.queryParam("groupId").firstOrNull()?.toLong() ?: 0L
        }
        if (bookUrl.isNullOrEmpty()) {
            return returnData.setErrorMsg("书籍链接不能为空")
        }
        val userNameSpace = getUserNameSpace(context)
        val book = getShelfBookByURL(bookUrl, userNameSpace)
        if (book == null) {
            return returnData.setErrorMsg("书籍信息错误")
        }
        if (groupId.element <= 0L) {
            return returnData.setErrorMsg("分组信息错误")
        }
        editShelfBook(book, userNameSpace) { existBook ->
            existBook.group = groupId.element
            logger.info("saveBookGroupId: {}", existBook)
            existBook
        }
        book.group = groupId.element
        return returnData.setData(book)
    }

    suspend fun addBookGroupMulti(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        val groupId: Long = context.bodyAsJson.getLong("groupId", 0)
        if (groupId <= 0L) {
            return returnData.setErrorMsg("分组信息错误")
        }
        val userNameSpace = getUserNameSpace(context)
        val bookJsonArray = context.bodyAsJson.getJsonArray("bookList", JsonArray())
        for (k in 0 until bookJsonArray.size()) {
            val book = bookJsonArray.getJsonObject(k).mapTo(Book::class.java)
            editShelfBook(book, userNameSpace) { existBook ->
                existBook.group = existBook.group or groupId
                logger.info("saveBookGroupId: {}", existBook)
                existBook
            }
        }
        return returnData.setData("")
    }

    suspend fun removeBookGroupMulti(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        val groupId: Long = context.bodyAsJson.getLong("groupId", 0)
        if (groupId <= 0L) {
            return returnData.setErrorMsg("分组信息错误")
        }
        val userNameSpace = getUserNameSpace(context)
        val bookJsonArray = context.bodyAsJson.getJsonArray("bookList", JsonArray())
        for (k in 0 until bookJsonArray.size()) {
            val book = bookJsonArray.getJsonObject(k).mapTo(Book::class.java)
            editShelfBook(book, userNameSpace) { existBook ->
                existBook.group = existBook.group xor groupId
                logger.info("saveBookGroupId: {}", existBook)
                existBook
            }
        }
        return returnData.setData("")
    }

    suspend fun deleteBook(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        val book = context.bodyAsJson.mapTo(Book::class.java)
        val userNameSpace = getUserNameSpace(context)
        var bookshelf = asJsonArray(getUserStorage(userNameSpace, "bookshelf")) ?: JsonArray()
        var existIndex = -1
        var bookName = ""
        var bookAuthor = ""
        var bookUrl = ""
        for (i in 0 until bookshelf.size()) {
            bookName = bookshelf.getJsonObject(i).getString("name", "")
            bookAuthor = bookshelf.getJsonObject(i).getString("author", "")
            bookUrl = bookshelf.getJsonObject(i).getString("bookUrl", "")
            if (bookUrl.equals(book.bookUrl)) {
                existIndex = i
                break
            }
            if (!bookName.equals(book.name) || !bookAuthor.equals(book.author)) continue
            existIndex = i
            break
        }
        if (existIndex < 0) {
            return returnData.setErrorMsg("书架书籍不存在")
        }
        val existBook = bookshelf.getJsonObject(existIndex)
        bookshelf.remove(existIndex)
        saveUserStorage(userNameSpace, "bookshelf", bookshelf)
        val localBookPath = File(getWorkDir("storage", "data", userNameSpace, bookName + '_' + bookAuthor))
        localBookPath.deleteRecursively()
        val coverUrl = existBook.getString("coverUrl")
        if (!coverUrl.isNullOrEmpty() && coverUrl.startsWith("/")) {
            FileUtils.deleteFile(getWorkDir("storage" + existBook.getString("coverUrl")))
        }
        return returnData.setData("删除书籍成功")
    }

    suspend fun deleteBooks(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        val bookJsonArray = context.bodyAsJsonArray
        val userNameSpace = getUserNameSpace(context)
        var bookshelf = asJsonArray(getUserStorage(userNameSpace, "bookshelf"))
        if (bookshelf == null) {
            bookshelf = JsonArray()
        }
        val infoMap: MutableMap<String, Int> = linkedMapOf()
        for (i in 0 until bookJsonArray.size()) {
            infoMap.put(bookJsonArray.getJsonObject(i).getString("bookUrl", ""), i)
            infoMap.put(bookJsonArray.getJsonObject(i).getString("name", "") + '_' +
                bookshelf.getJsonObject(i).getString("author", ""), i)
        }
        val iterator = bookshelf.iterator()
        while (iterator.hasNext()) {
            val book = iterator.next() as JsonObject
            val bookName = book.getString("name", "")
            val bookAuthor = book.getString("author", "")
            var bookUrl = book.getString("bookUrl", "")
            val existIndex = infoMap.getOrDefault(
                bookUrl,
                infoMap.getOrDefault(bookName + '_' + bookAuthor, -1)
            )
            if (existIndex >= 0) {
                iterator.remove()
                val localBookPath =
                    File(getWorkDir("storage", "data", userNameSpace, bookName + '_' + bookAuthor))
                localBookPath.deleteRecursively()
            }
        }
        saveUserStorage(userNameSpace, "bookshelf", bookshelf)
        return returnData.setData("")
    }

    suspend fun saveBookInfoCache(bookList: List<Book>): List<Book> {
        if (bookList.size > 0) {
            for (i in 0 until bookList.size) {
                val book = bookList[i]
                bookInfoCache.put(book.bookUrl, jsonEncode(JsonObject.mapFrom(book).map))
            }
        }
        return bookList
    }

    suspend fun mergeBookCacheInfo(book: Book): Book {
        val cacheInfo = bookInfoCache.getAsString(book.bookUrl)?.toMap()?.toDataClass<Book>()
        if (cacheInfo != null) {
            return book.fillData(
                cacheInfo,
                listOf("name", "author", "coverUrl", "tocUrl", "intro", "latestChapterTitle", "wordCount")
            )
        }
        return book
    }

    suspend fun getBookShelfBooks(refresh: Boolean = false, userNameSpace: String): List<Book> {
        var bookshelf: JsonArray? = asJsonArray(getUserStorage(userNameSpace, "bookshelf"))
        if (bookshelf == null) {
            return arrayListOf()
        }
        if (bookshelf.size() == 0) {
            return arrayListOf()
        }
        var bookList = arrayListOf<Book>()
        val concurrentCount = 16
        val mutex = Mutex()
        val syncMutex = Mutex()
        limitConcurrent(
            concurrentCount, 0, bookshelf.size(),
            { index ->
                var book = bookshelf.getJsonObject(index).mapTo(Book::class.java)
                book.isInShelf = true
                if (!book.isLocalBook() && book.canUpdate && refresh) {
                    try {
                        var bookSource = getBookSourceStringBySourceURLOpt(book.origin, userNameSpace)
                        if (bookSource != null) {
                            withContext(Dispatchers.IO) {
                                getLocalChapterList(
                                    book,
                                    bookSource,
                                    refresh,
                                    userNameSpace,
                                    false,
                                    mutex
                                )
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                syncMutex.withLock {
                    bookList.add(book)
                }
                true
            })
        return bookList
    }

    suspend fun getLocalChapterList(
        book: Book,
        bookSource: String?,
        refresh: Boolean = false,
        userNameSpace: String,
        debugLog: Boolean = true,
        mutex: Mutex? = null
    ): List<BookChapter> {
        val md5Encode = MD5Utils.md5Encode(book.bookUrl).toString()
        var chapterList: JsonArray? = null
        val bookChaptersCache = getBookChaptersCache(userNameSpace)
        if (book.isInShelf) {
            chapterList = asJsonArray(getUserStorage(userNameSpace, book.name + "_" + book.author, md5Encode))
        } else {
            chapterList = asJsonArray(bookChaptersCache.getAsString(book.name + "_" + book.author + md5Encode))
        }
        if (chapterList == null || refresh) {
            var newChapterList: List<BookChapter>
            book.setRootDir(getWorkDir())
            book.setUserNameSpace(userNameSpace)
            if (book.isLocalBook()) {
                if (book.isEpub() && !extractEpub(book, refresh)) {
                    throw Exception("Epub书籍解压失败")
                }
                if (book.isCbz() && !extractCbz(book, refresh)) {
                    throw Exception("CBZ书籍解压失败")
                }
                if (book.isPdf() && !convertPdfToImage(book, refresh)) {
                    throw Exception("PDF书籍转换失败")
                }
                newChapterList = LocalBook.getChapterList(book)
            } else {
                try {
                    if (!bookSource.isNullOrEmpty()) {
                        val bookSourceObject = BookSource.fromJson(bookSource).getOrNull()
                        bookSourceObject?.ruleToc?.preUpdateJs?.let {
                            AnalyzeRule(book, bookSourceObject).evalJS(it)
                        }
                        if (book.tocUrl.isBlank()) {
                            WebBook(bookSource, debugLog, userNameSpace = userNameSpace).getBookInfo(book)
                        }
                    }
                    newChapterList = WebBook(bookSource!!, debugLog, userNameSpace = userNameSpace)
                        .getChapterList(book)
                } catch (e: Exception) {
                    if (!bookSource.isNullOrEmpty()) {
                        val bookSourceObject = BookSource.fromJson(bookSource).getOrNull()
                        if (bookSourceObject != null) {
                            val info = mutableMapOf(
                                "sourceUrl" to bookSourceObject.bookSourceUrl,
                                "time" to System.currentTimeMillis(),
                                "error" to e.toString()
                            )
                            addInvalidBookSource(bookSourceObject.bookSourceUrl, info, userNameSpace)
                        }
                    }
                    if (mutex != null) mutex.lock()
                    try {
                        book.lastCheckError = e.toString()
                        editShelfBook(book, userNameSpace) { existBook ->
                            existBook.lastCheckError = e.toString()
                            existBook
                        }
                    } finally {
                        mutex?.unlock()
                    }
                    throw e
                }
            }
            if (book.isInShelf) {
                saveUserStorage(
                    userNameSpace,
                    getRelativePath(book.name + "_" + book.author, md5Encode),
                    newChapterList
                )
            } else {
                bookChaptersCache.put(book.name + "_" + book.author + md5Encode, jsonEncode(newChapterList), 3600)
            }
            saveShelfBookLatestChapter(book, newChapterList, userNameSpace, mutex)
            return newChapterList
        }
        var localChapterList = arrayListOf<BookChapter>()
        for (i in 0 until chapterList.size()) {
            var _chapter = chapterList.getJsonObject(i).mapTo(BookChapter::class.java)
            localChapterList.add(_chapter)
        }
        return localChapterList
    }

    suspend fun getBookSourceString(
        context: RoutingContext,
        sourceUrl: String = "",
        withExploreUrl: Boolean = false
    ): String? {
        var bookSourceString: String? = null
        if (context.request().method() == HttpMethod.POST) {
            val bookSource = context.bodyAsJson.getJsonObject("bookSource")
            if (bookSource != null) {
                bookSourceString = bookSource.toString()
            }
        }
        val userNameSpace = getUserNameSpace(context)
        if (bookSourceString.isNullOrEmpty()) {
            var bookSourceUrl: String
            if (context.request().method() == HttpMethod.POST) {
                bookSourceUrl = context.bodyAsJson.getString("bookSourceUrl", "")
            } else {
                bookSourceUrl = context.queryParam("bookSourceUrl").firstOrNull() ?: ""
            }
            if (!bookSourceUrl.isNullOrBlank()) {
                bookSourceString = getBookSourceStringBySourceURLOpt(bookSourceUrl, userNameSpace)
            }
        }
        if (bookSourceString.isNullOrEmpty() && !sourceUrl.isNullOrEmpty()) {
            bookSourceString = getBookSourceStringBySourceURLOpt(sourceUrl, userNameSpace)
        }
        return bookSourceString
    }


    fun getBookSourceStringBySourceURLOpt(sourceUrl: String, userNameSpace: String): String? {
        if (sourceUrl.isBlank()) {
            return null
        }
        var file = getStorageFile("data", userNameSpace, "bookSource")
        if (!file.exists()) {
            file = getStorageFile("data", "default", "bookSource")
            if (!file.exists()) {
                return null
            }
        }
        try {
            val objectMapper = ObjectMapper()
            val factory = objectMapper.factory
            var bookSourceString: String? = null
            factory.createParser(file).use { parser ->
                if (parser.nextToken() == JsonToken.START_ARRAY) {
                    while (parser.nextToken() != JsonToken.END_ARRAY) {
                        if (parser.currentToken() == JsonToken.START_OBJECT) {
                            val jsonNode: JsonNode = parser.readValueAsTree()
                            if (sourceUrl.equals(jsonNode.get("bookSourceUrl").asText())) {
                                bookSourceString = jsonNode.toString()
                                break
                            }
                        }
                    }
                }
            }
            logger.info { bookSourceString }
            return bookSourceString
        } catch (e: Exception) {
            logger.error("解析文件内容出错: {}  文件: \n{}", e, file)
            throw e
        }
    }

    fun getShelfBookByURL(url: String, userNameSpace: String): Book? {
        if (url.isEmpty()) {
            return null
        }
        val bookshelf = asJsonArray(getUserStorage(userNameSpace, "bookshelf"))
        if (bookshelf == null) {
            return null
        }
        for (i in 0 until bookshelf.size()) {
            val _book = bookshelf.getJsonObject(i).map.toDataClass<Book>()
            if (_book.bookUrl.equals(url)) {
                _book.setRootDir(getWorkDir())
                _book.setUserNameSpace(userNameSpace)
                _book.isInShelf = true
                return _book
            }
        }
        return null
    }

    suspend fun saveShelfBookProgress(book: Book, bookChapter: BookChapter, userNameSpace: String) {
        editShelfBook(book, userNameSpace) { existBook ->
            existBook.durChapterIndex = bookChapter.index
            existBook.durChapterTitle = bookChapter.title
            existBook.durChapterTime = System.currentTimeMillis()
            existBook
        }
    }

    suspend fun saveShelfBookLatestChapter(
        book: Book,
        bookChapterList: List<BookChapter>,
        userNameSpace: String,
        mutex: Mutex? = null
    ) {
        mutex?.lock()
        try {
            editShelfBook(book, userNameSpace) { existBook ->
                if (bookChapterList.size > 0) {
                    val latestChapter = bookChapterList.last()
                    existBook.latestChapterTitle = latestChapter.title
                }
                if (bookChapterList.size - existBook.totalChapterNum > 0) {
                    existBook.lastCheckCount = bookChapterList.size - existBook.totalChapterNum
                    existBook.lastCheckTime = System.currentTimeMillis()
                }
                existBook.lastCheckError = null
                existBook.totalChapterNum = bookChapterList.size
                book.latestChapterTitle = existBook.latestChapterTitle
                book.lastCheckCount = existBook.lastCheckCount
                book.lastCheckTime = existBook.lastCheckTime
                book.lastCheckError = existBook.lastCheckError
                book.totalChapterNum = existBook.totalChapterNum
                existBook
            }
        } finally {
            mutex?.unlock()
        }
    }

    suspend fun editShelfBook(
        book: Book,
        userNameSpace: String,
        handler: (Book) -> Book
    ): Book? {
        val mutex = UserMutex.getLocker("$userNameSpace@bookshelf")
        logger.info("wait for lock {}", "$userNameSpace@bookshelf")
        mutex.lock()
        try {
            logger.info("lock success")
            var bookshelf: JsonArray? = asJsonArray(getUserStorage(userNameSpace, "bookshelf"))
            if (bookshelf == null) {
                bookshelf = JsonArray()
            }
            var existIndex = -1
            for (i in 0 until bookshelf.size()) {
                var _book = bookshelf.getJsonObject(i).mapTo(Book::class.java)
                if (book.bookUrl.isNotEmpty() && _book.bookUrl.equals(book.bookUrl)) {
                    existIndex = i
                    break
                }
                if (book.name.isNotEmpty() && _book.name.equals(book.name) &&
                    book.author.isNotEmpty() && _book.author.equals(book.author)
                ) {
                    existIndex = i
                    break
                }
            }
            if (existIndex >= 0) {
                var bookList = bookshelf.getList()
                var existBook = bookshelf.getJsonObject(existIndex).mapTo(Book::class.java)
                existBook = handler(existBook)
                bookList.set(existIndex, JsonObject.mapFrom(existBook))
                bookshelf = JsonArray(bookList)
                saveUserStorage(userNameSpace, "bookshelf", bookshelf)
                return existBook
            }
        } finally {
            mutex.unlock()
        }
        return null
    }

    fun saveBookSources(
        book: Book,
        sourceList: List<SearchBook>,
        userNameSpace: String,
        replace: Boolean = false
    ) {
        if (book.name.isEmpty()) {
            return
        }
        var bookSourceList = JsonArray()
        if (!replace) {
            asJsonArray(
                getUserStorage(
                    userNameSpace,
                    book.name + '_' + book.author,
                    "bookSource"
                )
            )?.let {
                bookSourceList = it
            }
        }
        val urlMap = linkedMapOf<String, Int>()
        for (i in 0 until bookSourceList.size()) {
            val bookUrl: String = bookSourceList.getJsonObject(i).getString("bookUrl")
            urlMap[bookUrl] = i
        }
        sourceList.forEach { searchBook ->
            val existIndex = urlMap.getOrDefault(searchBook.bookUrl, -1)
            if (existIndex >= 0) {
                bookSourceList.set(existIndex, JsonObject.mapFrom(searchBook))
            } else {
                bookSourceList.add(JsonObject.mapFrom(searchBook))
                urlMap[searchBook.bookUrl] = bookSourceList.size() - 1
            }
        }
        saveUserStorage(
            userNameSpace,
            getRelativePath(book.name + '_' + book.author, "bookSource"),
            bookSourceList
        )
    }

    fun extractEpub(book: Book, force: Boolean = false): Boolean {
        val epubExtractDir = File(getWorkDir(book.bookUrl + File.separator + "index"))
        if (force || !epubExtractDir.exists()) {
            epubExtractDir.deleteRecursively()
            var localEpubFile = File(getWorkDir(book.originName + File.separator + "index.epub"))
            if (book.originName.indexOf("localStore") > 0) {
                localEpubFile = File(getWorkDir(book.originName))
            }
            if (book.originName.indexOf("webdav") > 0) {
                localEpubFile = File(getWorkDir(book.originName))
            }
            logger.info("extractEpub from {} to {}", localEpubFile, epubExtractDir)
            if (!localEpubFile.unzip(epubExtractDir.toString())) {
                return false
            }
        }
        return true
    }

    fun extractCbz(book: Book, force: Boolean = false): Boolean {
        val extractDir = File(getWorkDir(book.bookUrl + File.separator + "index"))
        if (force || !extractDir.exists()) {
            extractDir.deleteRecursively()
            var localFile = File(getWorkDir(book.originName + File.separator + "index.cbz"))
            if (book.originName.indexOf("localStore") > 0) {
                localFile = File(getWorkDir(book.originName))
            }
            if (book.originName.indexOf("webdav") > 0) {
                localFile = File(getWorkDir(book.originName))
            }
            if (!localFile.unzip(extractDir.toString())) {
                return false
            }
        }
        return true
    }

    fun convertPdfToImage(book: Book, force: Boolean = false): Boolean {
        return true
    }

    fun convertPdfPageToImage(book: Book, index: Int, force: Boolean = false) {
        val extractDir = File(getWorkDir(book.bookUrl + File.separator + "index"))
        if (!extractDir.exists()) {
            extractDir.mkdirs()
        }
        val imageFormat = "png"
        val output = File(extractDir.toString() + File.separator + "output-" + index + '.' + imageFormat)
        if (force || !output.exists()) {
            output.deleteRecursively()
            var localFile = File(getWorkDir(book.originName + File.separator + "index.pdf"))
            if (book.originName.indexOf("localStore") > 0) {
                localFile = File(getWorkDir(book.originName))
            }
            if (book.originName.indexOf("webdav") > 0) {
                localFile = File(getWorkDir(book.originName))
            }
            val document = PDDocument.load(localFile)
            val renderer = PDFRenderer(document)
            val targetWidth = book.getPdfImageWidth()
            savePdfPageToImage(document, renderer, index, targetWidth, imageFormat, output)
        }
    }

    fun savePdfPageToImage(
        document: PDDocument,
        renderer: PDFRenderer,
        index: Int,
        targetWidth: Float,
        imageFormat: String,
        output: File
    ) {
        val dpi = 300.0f
        val page = document.getPage(index)
        val pageSize = page.cropBox
        val targetHeight = 0.0f
        val scaleFactor = targetWidth / pageSize.width
        val scaledHeight = pageSize.height * scaleFactor
        val targetHeightDimension = if (targetHeight == 0.0f) scaledHeight.toInt() else targetHeight.toInt()
        val targetDimension = Dimension(targetWidth.toInt(), targetHeightDimension)
        val image = renderer.renderImageWithDPI(index, dpi, ImageType.RGB)
        val scaledImage = image.getScaledInstance(targetDimension.width, targetDimension.height, 4)
        val scaledBufferedImage = BufferedImage(targetDimension.width, targetDimension.height, 1)
        val graphics = scaledBufferedImage.createGraphics()
        graphics.drawImage(scaledImage, 0, 0, null)
        graphics.dispose()
        ImageIO.write(scaledBufferedImage, imageFormat, output)
    }

    suspend fun syncBookProgressFromWebdav(progressFilePath: Any, userNameSpace: String) {
        var progressFile: File? = null
        when (progressFilePath) {
            is File -> progressFile = progressFilePath
            is String -> progressFile = File(progressFilePath)
        }
        if (progressFile == null) {
            return
        }
        var book = asJsonObject(progressFile.readText())?.mapTo(Book::class.java)
        if (book != null) {
            editShelfBook(book, userNameSpace) { existBook ->
                existBook.durChapterIndex = book.durChapterIndex
                existBook.durChapterPos = book.durChapterPos
                existBook.durChapterTime = book.durChapterTime
                existBook.durChapterTitle = book.durChapterTitle
                logger.info("syncShelfBookProgress: {}", existBook)
                existBook
            }
        }
    }

    suspend fun saveBookProgressToWebdav(book: Book, bookChapter: BookChapter, userNameSpace: String) {
        val userHome = getUserWebdavHome(userNameSpace)
        var bookProgressDir = File(userHome + File.separator + "bookProgress")
        if (!bookProgressDir.exists()) {
            bookProgressDir = File(userHome + File.separator + "legado" + File.separator + "bookProgress")
            if (!bookProgressDir.exists()) {
                return
            }
        }
        val progressFile = File(bookProgressDir.toString() + File.separator + book.name + '_' + book.author + ".json")
        progressFile.writeText(
            jsonEncode(
                mapOf(
                    "name" to book.name,
                    "author" to book.author,
                    "durChapterIndex" to bookChapter.index,
                    "durChapterPos" to 0,
                    "durChapterTime" to System.currentTimeMillis(),
                    "durChapterTitle" to bookChapter.title
                ), true
            )
        )
    }

    suspend fun syncFromWebdav(zipFilePath: String, userNameSpace: String): Boolean {
        val descDir = getWorkDir("storage", "data", userNameSpace, "tmp")
        val descDirFile = File(descDir)
        return try {
            val userHome = getUserWebdavHome(userNameSpace)
            val zipFile = File(zipFilePath)
            if (!zipFile.exists()) {
                descDirFile.deleteRecursively()
                return false
            }
            descDirFile.deleteRecursively()
            ZipUtils.unzipFile(zipFile, descDirFile)
            val syncDataFileList = arrayListOf(*backupFileNames)
            syncDataFileList.forEach { fileName ->
                val backupFile = File(descDir + File.separator + fileName)
                if (backupFile.exists()) {
                    val userDataFile = File(getWorkDir("storage", "data", userNameSpace, fileName))
                    userDataFile.deleteRecursively()
                    backupFile.copyRecursively(userDataFile)
                }
            }
            val backupBooksDir = File(descDir + File.separator + "books")
            if (backupBooksDir.exists()) {
                val webdavBooksDir =
                    File(getWorkDir("storage", "data", userNameSpace, "webdav", "books"))
                webdavBooksDir.deleteRecursively()
                backupBooksDir.copyRecursively(webdavBooksDir)
            }
            var bookProgressDir = File(userHome + File.separator + "bookProgress")
            if (!bookProgressDir.exists()) {
                bookProgressDir =
                    File(userHome + File.separator + "legado" + File.separator + "bookProgress")
            }
            if (bookProgressDir.exists() && bookProgressDir.isDirectory) {
                val files: Array<File> = bookProgressDir.listFiles()
                files.forEach { file ->
                    syncBookProgressFromWebdav(file, userNameSpace)
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            descDirFile.deleteRecursively()
        }
    }

    suspend fun saveToWebdav(userNameSpace: String, latestZipFilePath: String? = null): Boolean {
        val userHome = getUserWebdavHome(userNameSpace)
        val _latestZipFilePath = latestZipFilePath ?: getLastBackFileFromWebdav(userNameSpace)
        var legadoHome = userHome
        if (_latestZipFilePath == null) {
            legadoHome = userHome + File.separator + "legado"
        } else if (_latestZipFilePath.indexOf("legado") > 0) {
            legadoHome = userHome + File.separator + "legado"
        }
        val backupFile = createUserBackup(userNameSpace, legadoHome, _latestZipFilePath)
        return backupFile != null
    }

    suspend fun createUserBackup(
        userNameSpace: String,
        backupDir: String,
        latestZipFilePath: String? = null
    ): File? {
        val today = SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis())
        var descDir: String
        descDir = getWorkDir("storage", "data", userNameSpace, "backup$today")
        val descDirFile = File(descDir)
        descDirFile.deleteRecursively()
        try {
            if (latestZipFilePath != null && !File(latestZipFilePath).unzip(descDir)) {
                return null
            }
            val syncDataFileList = arrayListOf(*backupFileNames)
            syncDataFileList.forEach { it ->
                val userDataFile = File(getWorkDir("storage", "data", userNameSpace, it))
                if (userDataFile.exists()) {
                    val backupFile = File(descDir + File.separator + it)
                    backupFile.deleteRecursively()
                    userDataFile.copyRecursively(backupFile)
                }
            }
            val webdavBooksDir = File(getWorkDir("storage", "data", userNameSpace, "webdav", "books"))
            if (webdavBooksDir.exists()) {
                val backupBooksDir = File(descDir + File.separator + "books")
                backupBooksDir.deleteRecursively()
                webdavBooksDir.copyRecursively(backupBooksDir)
            }
            val backupFile = FileUtils.createFileWithReplace(
                backupDir + File.separator + "backup" + today + ".zip"
            )
            return if (ZipUtils.zipFiles(arrayListOf(*descDirFile.listFiles()), backupFile, null)) backupFile else null as File?
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            descDirFile.deleteRecursively()
        }
        return null
    }

    suspend fun getLastBackFileFromWebdav(userNameSpace: String): String? {
        val userHome = getUserWebdavHome(userNameSpace)
        var legadoHome = File(userHome + File.separator + "legado")
        if (!legadoHome.exists()) {
            legadoHome = File(userHome)
        }
        if (!legadoHome.exists()) {
            return null
        }
        var latestZipFile: String? = null
        val zipFileReg = Regex("^backup[0-9-]+.zip$", RegexOption.IGNORE_CASE)
        legadoHome.listFiles().also{
            it.sortByDescending {
                it.lastModified()
            }
        }.forEach { it ->
            if (zipFileReg.matches(it.name)) {
                latestZipFile = it.toString()
            }
        }
        return latestZipFile
    }

    suspend fun bookSourceDebugSSE(context: RoutingContext) {
        val returnData = ReturnData()
        val response = context.response()
            .putHeader("Content-Type", "text/event-stream")
            .putHeader("Cache-Control", "no-cache")
            .setChunked(true)
        if (!checkAuth(context)) {
            response.write("event: error\n")
            response.end(
                "data: " + jsonEncode(
                    returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用"),
                    false
                ) + "\n\n"
            )
            return
        }
        val bookSourceUrl = context.queryParam("bookSourceUrl").firstOrNull() ?: ""
        val keyword = context.queryParam("keyword").firstOrNull() ?: ""
        if (bookSourceUrl.isNullOrEmpty()) {
            response.write("event: error\n")
            response.end(
                "data: " + jsonEncode(returnData.setErrorMsg("未配置书源"), false) + "\n\n"
            )
            return
        }
        if (keyword.isNullOrEmpty()) {
            response.write("event: error\n")
            response.end(
                "data: " + jsonEncode(returnData.setErrorMsg("请输入搜索关键词"), false) + "\n\n"
            )
            return
        }
        val userNameSpace = getUserNameSpace(context)
        val bookSourceString = getBookSourceStringBySourceURLOpt(bookSourceUrl, userNameSpace)
        if (bookSourceString.isNullOrEmpty()) {
            response.write("event: error\n")
            response.end(
                "data: " + jsonEncode(returnData.setErrorMsg("未配置书源"), false) + "\n\n"
            )
            return
        }
        context.request().connection().closeHandler {
            logger.info("客户端已断开链接，停止 bookSourceDebugSSE")
            coroutineContext.cancel()
        }
        logger.info("bookSourceDebugSSE bookSource: {} keyword: {}", bookSourceString, keyword)
        val debugger = Debugger { msg ->
            response.write("data: " + jsonEncode(mapOf("msg" to msg), false) + "\n\n")
        }
        val webBook = WebBook(bookSourceString, userNameSpace = userNameSpace)
        debugger.startDebug(webBook, keyword)
        response.write("event: end\n")
        response.end("data: " + jsonEncode(mapOf("end" to true), false) + "\n\n")
    }

    suspend fun cacheBookSSE(context: RoutingContext) {
        val returnData = ReturnData()
        val response = context.response()
            .putHeader("Content-Type", "text/event-stream")
            .putHeader("Cache-Control", "no-cache")
            .setChunked(true)
        if (!checkAuth(context)) {
            response.write("event: error\n")
            response.end(
                "data: " + jsonEncode(returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用"), false) + "\n\n"
            )
            return
        }
        var bookUrl: String
        var refresh: Int
        var concurrentCount: Int
        if (context.request().method() == HttpMethod.POST) {
            val url = context.bodyAsJson.getString("url")
            bookUrl = if (url != null) url else (context.bodyAsJson.getString("bookUrl") ?: "")
            refresh = context.bodyAsJson.getInteger("refresh", 0)
            concurrentCount = context.bodyAsJson.getInteger("concurrentCount", 24)
        } else {
            bookUrl = context.queryParam("url").firstOrNull() ?: ""
            refresh = context.queryParam("refresh").firstOrNull()?.toInt() ?: 0
            concurrentCount = context.queryParam("concurrentCount").firstOrNull()?.toInt() ?: 24
        }
        if (bookUrl.isEmpty()) {
            response.write("event: error\n")
            response.end("data: " + jsonEncode(returnData.setErrorMsg("请输入书籍链接"), false)!! + "\n\n")
            return
        }
        var userNameSpace = getUserNameSpace(context)
        val bookInfo = getShelfBookByURL(bookUrl, userNameSpace)
        if (bookInfo == null) {
            response.write("event: error\n")
            response.end("data: " + jsonEncode(returnData.setErrorMsg("请先加入书架"), false)!! + "\n\n")
            return
        }
        if (bookInfo.isLocalBook()) {
            response.write("event: error\n")
            response.end("data: " + jsonEncode(returnData.setErrorMsg("本地书籍无需缓存"), false)!! + "\n\n")
            return
        }
        var bookSource = getBookSourceString(context, bookInfo.origin, false)
        if (bookSource.isNullOrEmpty()) {
            response.write("event: error\n")
            response.end("data: " + jsonEncode(returnData.setErrorMsg("未配置书源"), false) + "\n\n")
            return
        }
        var chapterList = WebBook(bookSource, appConfig.debugLog, userNameSpace = userNameSpace)
            .getChapterList(bookInfo)
        val cachedChapterContentSet = Ref.ObjectRef<MutableSet<Int>>()
        cachedChapterContentSet.element = getCachedChapterContentSet(bookInfo, userNameSpace)
        val localCacheDir = getChapterCacheDir(bookInfo, userNameSpace)
        val isEnd = Ref.BooleanRef()
        val successCount = Ref.IntRef()
        val failedCount = Ref.IntRef()
        context.request().connection().closeHandler { isEnd.element = true }
        val count = if (concurrentCount > 0) concurrentCount else 24
        logger.info("cacheBookSSE concurrentCount: {} refresh: {}", count, refresh)
        limitConcurrent(count, 0, chapterList.size, handler = { it ->
            val chapterIndex = it
            if (!cachedChapterContentSet.element.contains(chapterIndex)) {
                val chapterInfo = chapterList[chapterIndex]
                var nextChapterUrl: String? = null
                if (chapterIndex + 1 < chapterList.size) {
                    val nextChapterInfo = chapterList[chapterIndex + 1]
                    nextChapterUrl = nextChapterInfo.url
                }
                try {
                    val content = WebBook(bookSource, appConfig.debugLog, userNameSpace = userNameSpace)
                        .getBookContent(bookInfo, chapterInfo, nextChapterUrl)
                    val chapterCacheFile =
                        File(localCacheDir.absolutePath + File.separator + chapterIndex + ".txt")
                    chapterCacheFile.writeText(content)
                    BookHelp.saveImages(
                        this,
                        BookSource.fromJson(bookSource).getOrNull() ?: BookSource(),
                        bookInfo, chapterInfo, content
                    )
                    successCount.element++
                    cachedChapterContentSet.element.add(chapterIndex)
                } catch (e: Exception) {
                    isEnd.element = true
                    failedCount.element++
                }
            }
            chapterIndex
        }, needContinue = { list, loopCount ->
            if (isEnd.element) {
                false
            } else {
                val result = mapOf(
                    "cachedCount" to cachedChapterContentSet.element.size,
                    "successCount" to successCount.element,
                    "failedCount" to failedCount.element
                )
                response.write("data: " + jsonEncode(result, false) + "\n\n")
                logger.info("Loop: {} list.size: {} result: {}", loopCount, list.size, result)
                true
            }
        })
        if (!isEnd.element) {
            response.write("event: end\n")
            response.end(
                "data: " + jsonEncode(
                    returnData.setData(
                        mapOf(
                            "cachedCount" to cachedChapterContentSet.element.size,
                            "successCount" to successCount.element,
                            "failedCount" to failedCount.element
                        )
                    ),
                    false
                ) + "\n\n"
            )
        }
    }

    suspend fun cacheBookOnServer(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        val bookUrlList = context.bodyAsJson.getJsonArray("bookUrlList") ?: JsonArray()
        if (bookUrlList.size() <= 0) {
            return returnData.setErrorMsg("请输入书籍链接")
        }
        val userNameSpace = getUserNameSpace(context)
        val exceptionHandler = CoroutineExceptionHandler { _, ex ->
            logger.info("cacheBookOnServer error: {}", ex.message)
        }
        launch(MDCContext() + Dispatchers.IO + exceptionHandler) {
            cacheBookOnServer(bookUrlList, userNameSpace)
        }
        return returnData.setData("")
    }

    suspend fun cacheBookOnServer(bookUrlList: JsonArray, userNameSpace: String) {
        for (i in 0 until bookUrlList.size()) {
            val bookUrl = bookUrlList.getString(i)
            val bookInfo = getShelfBookByURL(bookUrl, userNameSpace)
            if (bookInfo == null) {
                logger.info("未找到书籍信息: {}", bookUrl)
                continue
            }
            if (bookInfo.isLocalBook()) {
                logger.info("本地书籍跳过缓存: {}", bookUrl)
                continue
            }
            logger.info("开始缓存书籍: {}", bookInfo)
            val bookSource = getBookSourceStringBySourceURLOpt(bookInfo.origin, userNameSpace)
            if (bookSource.isNullOrEmpty()) {
                logger.info("未找到书源信息: {}", bookUrl)
                continue
            }
            val chapterList = getLocalChapterList(bookInfo, bookSource, false, userNameSpace)
            val cachedChapterContentSet = getCachedChapterContentSet(bookInfo, userNameSpace)
            val localCacheDir = getChapterCacheDir(bookInfo, userNameSpace)
            for (chapterIndex in chapterList.indices) {
                if (!cachedChapterContentSet.contains(chapterIndex)) {
                    val index = chapterIndex
                    val chapterInfo = chapterList[chapterIndex]
                    try {
                        var nextChapterUrl: String? = null
                        if (index + 1 < chapterList.size) {
                            val nextChapterInfo = chapterList[index + 1]
                            nextChapterUrl = nextChapterInfo.url
                        }
                        val content = WebBook(bookSource, appConfig.debugLog, userNameSpace = userNameSpace)
                            .getBookContent(bookInfo, chapterInfo, nextChapterUrl)
                        val chapterCacheFile =
                            File(localCacheDir.absolutePath + File.separator + index + ".txt")
                        chapterCacheFile.writeText(content)
                        BookHelp.saveImages(
                            this,
                            BookSource.fromJson(bookSource).getOrNull() ?: BookSource(),
                            bookInfo, chapterInfo, content
                        )
                        cachedChapterContentSet.add(index)
                    } catch (e: Exception) {
                        logger.info("cacheBookOnServer error: {}", e.message)
                    }
                }
            }
            logger.info("缓存书籍完成: {}", bookInfo)
        }
    }

    suspend fun deleteBookCache(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        var bookUrl: String
        if (context.request().method() == HttpMethod.POST) {
            bookUrl = context.bodyAsJson.getString("url")
                ?: context.bodyAsJson.getString("bookUrl") ?: ""
        } else {
            bookUrl = context.queryParam("url").firstOrNull() ?: ""
        }
        if (bookUrl.isNullOrEmpty()) {
            return returnData.setErrorMsg("请输入书籍链接")
        }
        val userNameSpace = getUserNameSpace(context)
        val bookInfo = getShelfBookByURL(bookUrl, userNameSpace)
        if (bookInfo == null) {
            return returnData.setErrorMsg("请先加入书架")
        }
        if (bookInfo.isLocalBook()) {
            return returnData.setErrorMsg("本地书籍无需删除缓存")
        }
        val localCacheDir = getChapterCacheDir(bookInfo, userNameSpace)
        localCacheDir.deleteRecursively()
        return returnData.setData("")
    }

    suspend fun textToSpeech(context: RoutingContext) {
        val response = context.response()
        if (!checkAuth(context)) {
            response.setStatusCode(403).end("未登录")
            return
        }
        val text = Ref.ObjectRef<String>()
        val type = Ref.ObjectRef<String>()
        var voice: String
        var pitch: String
        var rate: String
        var base64: String
        if (context.request().method() == HttpMethod.POST) {
            text.element = context.bodyAsJson.getString("text") ?: ""
            type.element = context.bodyAsJson.getString("type") ?: ""
            voice = context.bodyAsJson.getString("voice") ?: ""
            pitch = context.bodyAsJson.getString("pitch") ?: ""
            rate = context.bodyAsJson.getString("rate") ?: ""
            base64 = context.bodyAsJson.getString("base64") ?: ""
        } else {
            text.element = context.queryParam("text").firstOrNull() ?: ""
            type.element = context.queryParam("type").firstOrNull() ?: ""
            voice = context.queryParam("voice").firstOrNull() ?: ""
            pitch = context.queryParam("pitch").firstOrNull() ?: ""
            rate = context.queryParam("rate").firstOrNull() ?: ""
            base64 = context.queryParam("base64").firstOrNull() ?: ""
        }
        if (type.element.isNullOrEmpty()) {
            type.element = "edge"
        }
        if (text.element.isNullOrEmpty()) {
            response.setStatusCode(404).end("参数错误")
            return
        }
        val exceptionHandler = CoroutineExceptionHandler { _, ex ->
            logger.info("tts error: {}", ex.message)
            response.setStatusCode(404).end()
        }
        val options = mapOf(
            "voice" to voice,
            "pitch" to pitch,
            "rate" to rate,
            "base64" to base64
        )
        launch(MDCContext() + Dispatchers.IO + exceptionHandler) {
            when (type.element) {
                "edge" -> ttsByEdge(response, text.element!!, options)
                "textToSpeechCn" -> ttsByTextToSpeechCn(response, text.element!!, options)
                else -> ttsByApi(response, text.element!!, getUserNameSpace(context), options)
            }
        }
    }

    suspend fun ttsByEdge(
        response: HttpServerResponse,
        text: String,
        options: Map<String, String>? = null
    ) {
        var voice = VoiceEnum.zh_CN_XiaoxiaoNeural
        var rate = "0"
        var pitch = "0%"
        if (options != null) {
            if (options.containsKey("voice")) {
                voice = VoiceEnum.fromSortName(options["voice"] as String)
                    ?: VoiceEnum.zh_CN_XiaoxiaoNeural
            }
            if (options.containsKey("rate")) {
                rate = (options["rate"] as String?) ?: "0"
            }
            if (options.containsKey("pitch")) {
                pitch = (options["pitch"] as String) + "%"
            }
        }
        val ttsService = TTSService.builder().build()
        val ssml = SSML.builder()
            .synthesisText(text)
            .voice(voice)
            .rate(rate)
            .pitch(pitch)
            .style(TtsStyleEnum.chat)
            .build()
        val mp3byte = ttsService.sendText(ssml)
        if (options != null && "1".equals(options["base64"])) {
            val returnData = ReturnData()
            response.putHeader("content-type", "application/json; charset=utf-8")
            val encoded = Base64.getEncoder().encodeToString(mp3byte)
            returnData.setData(encoded)
            response.end(jsonEncode(returnData))
        } else {
            response.putHeader("Content-Type", "audio/mpeg")
            response.end(Buffer.buffer(mp3byte))
        }
    }

    fun getHttpTTSByName(name: String, userNameSpace: String): HttpTTS? {
        if (name.isEmpty()) {
            return null
        }
        val list = asJsonArray(getUserStorage(userNameSpace, "httpTTS"))
        if (list == null) {
            return null
        }
        for (i in 0 until list.size()) {
            val httpTTS = HttpTTS.fromJson(list.getJsonObject(i).toString()).getOrNull()
            if (httpTTS != null) {
                if (httpTTS.name.equals(name)) {
                    return httpTTS
                }
            }
        }
        return null
    }

    suspend fun ttsByApi(
        response: HttpServerResponse,
        text: String,
        userNameSpace: String,
        options: Map<String, String>? = null
    ) {
        val voice = options?.get("voice")
        if (voice.isNullOrEmpty()) {
            response.setStatusCode(404).end()
            return
        }
        val httpTTS = getHttpTTSByName(voice, userNameSpace)
        if (httpTTS == null) {
            response.setStatusCode(404).end()
            return
        }
        var speechRate = options?.get("rate")?.toDouble() ?: 1.0
        speechRate = 5 + (speechRate - 0.5) * 30
        val stream = getSpeakStream(httpTTS, text, speechRate.toInt())
        if (stream != null) {
            if (options != null && "1".equals(options["base64"])) {
                val returnData = ReturnData()
                response.putHeader("content-type", "application/json; charset=utf-8")
                    .end(jsonEncode(returnData.setData(Base64.getEncoder().encodeToString(stream.readBytes()))))
            } else {
                response.putHeader("Content-Type", httpTTS.contentType ?: "audio/mpeg")
                    .end(Buffer.buffer(stream.readBytes()))
            }
        } else {
            response.setStatusCode(404).end()
        }
    }

    suspend fun getSpeakStream(
        httpTts: HttpTTS,
        speakText: String,
        speechRate: Int
    ): InputStream? {
        val downloadErrorNo = Ref.IntRef()
        while (true) {
            try {
                val analyzeUrl = AnalyzeUrl(
                    mUrl = httpTts.url,
                    speakText = speakText,
                    speakSpeed = speechRate,
                    source = httpTts,
                    headerMapF = httpTts.getHeaderMap(true),
                    debugLog = Debug
                )
                var response = analyzeUrl.getResponseAwait()
                coroutineContext.ensureActive()
                val checkJs = httpTts.loginCheckJs
                if (checkJs?.isNotBlank() == true) {
                    response = analyzeUrl.evalJS(checkJs, response) as okhttp3.Response
                }
                response.headers.get("Content-Type")?.let { contentType ->
                    val ct = httpTts.contentType
                    if (contentType == "application/json") {
                        throw NoStackTraceException(response.body!!.string())
                    }
                    if (!ct.isNullOrBlank() && !Regex(ct).matches(contentType)) {
                        throw NoStackTraceException(
                            "TTS服务器返回错误：" + response.body!!.string()
                        )
                    }
                }
                coroutineContext.ensureActive()
                response.body!!.byteStream().let { stream ->
                    downloadErrorNo.element = 0
                    return stream
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                if (e is ScriptException || e is WrappedException) {
                    logger.error("js错误\n" + e.localizedMessage, e)
                    throw e
                }
                if (e is SocketTimeoutException || e is ConnectException) {
                    downloadErrorNo.element++
                    if (downloadErrorNo.element <= 5) continue
                    val msg = "tts超时或连接错误超过5次\n" + e.localizedMessage
                    logger.error(msg, e)
                    throw e
                }
                downloadErrorNo.element++
                val msg = "tts下载错误\n" + e.localizedMessage
                logger.error(msg, e)
                if (downloadErrorNo.element > 5) {
                    val msg1 = "TTS服务器连续5次错误，已暂停阅读。"
                    logger.error(msg1, e)
                    throw e
                }
                logger.error("TTS下载音频出错，使用无声音频代替。\n朗读文本：$speakText")
                return null
            }
        }
    }

    suspend fun ttsByTextToSpeechCn(
        response: HttpServerResponse,
        text: String,
        options: Map<String, String>? = null
    ) {
        val map = mutableMapOf(
            "language" to "中文（普通话，简体）",
            "voice" to "zh-CN-XiaoxiaoNeural",
            "text" to text,
            "role" to "0",
            "style" to "0",
            "rate" to "0",
            "pitch" to "0",
            "kbitrate" to "audio-16khz-32kbitrate-mono-mp3",
            "silence" to "",
            "styledegree" to "1",
            "user_id" to "",
            "yzm" to ""
        )
        if (options != null) {
            map.putAll(options)
        }
        val multiMap = CaseInsensitiveHeaders()
        fun add(headers: CaseInsensitiveHeaders, key: String, value: String) {
            headers.add(key, value)
        }
        map.forEach(multiMap::add)
        val ttsUrl = "https://www.text-to-speech.cn/getSpeek.php"
        val result = awaitResult<HttpResponse<Buffer>> { handler ->
            webClient.postAbs(ttsUrl)
                .timeout(5000L)
                .putHeader("Origin", "https://www.text-to-speech.cn")
                .putHeader("Referer", "https://www.text-to-speech.cn/")
                .putHeader(
                    "User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36"
                )
                .sendForm(multiMap, handler)
        }
        logger.info("res: {}", result)
        if (result != null) {
            val jsonRes = result.bodyAsJsonObject()
            logger.info("jsonRes: {}", jsonRes)
            if (jsonRes != null && jsonRes.getString("download") != null) {
                response.setStatusCode(302)
                    .putHeader("Location", jsonRes.getString("download"))
                    .end()
            } else {
                response.setStatusCode(404).end()
            }
        } else {
            response.setStatusCode(404).end()
        }
    }

    fun getChapterCacheDir(bookInfo: Book, userNameSpace: String): File {
        val md5Encode = MD5Utils.md5Encode(bookInfo.bookUrl).toString()
        val localCacheDirPath =
            getWorkDir("storage", "data", userNameSpace, bookInfo.name + '_' + bookInfo.author, md5Encode)
        val localCacheDir = File(localCacheDirPath)
        if (!localCacheDir.exists()) {
            localCacheDir.mkdirs()
        }
        return localCacheDir
    }

    fun getCachedChapterContentSet(bookInfo: Book, userNameSpace: String): MutableSet<Int> {
        val localCacheDir = getChapterCacheDir(bookInfo, userNameSpace)
        val cachedChapterContentSet: MutableSet<Int> = linkedSetOf()
        localCacheDir.listFiles().forEach { it ->
            if (!it.name.startsWith(".")) {
                if (it.name.endsWith(".txt")) {
                    cachedChapterContentSet.add(it.name.replace(".txt", "").toInt())
                }
            }
        }
        return cachedChapterContentSet
    }

    suspend fun getShelfBookWithCacheInfo(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        val userNameSpace = getUserNameSpace(context)
        val bookList = getBookShelfBooks(false, userNameSpace)
        val result: MutableList<Any> = arrayListOf()
        var i = 0
        while (i < bookList.size) {
            val bookInfo = bookList[i++]
            if (!bookInfo.isLocalBook()) {
                val cachedSet = getCachedChapterContentSet(bookInfo, userNameSpace)
                val bookInfoMap = bookInfo.toMap() as MutableMap<String, Any>
                bookInfoMap.put("cachedChapterCount", cachedSet.size)
                result.add(bookInfoMap)
            } else {
                result.add(bookInfo)
            }
        }
        return returnData.setData(result)
    }

    suspend fun exportBook(context: RoutingContext) {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            context.success(returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用"))
            return
        }
        val bookUrl: String
        val isEpub: Int
        if (context.request().method() == HttpMethod.POST) {
            bookUrl = context.bodyAsJson.getString("url")
                ?: (context.bodyAsJson.getString("bookUrl") ?: "")
            isEpub = context.bodyAsJson.getInteger("isEpub", 0)
        } else {
            bookUrl = context.queryParam("url").firstOrNull() ?: ""
            isEpub = context.queryParam("isEpub").firstOrNull()?.toInt() ?: 0
        }
        if (bookUrl.length == 0) {
            context.success(returnData.setErrorMsg("请输入书籍链接"))
            return
        }
        val userNameSpace = getUserNameSpace(context)
        val book = getShelfBookByURL(bookUrl, userNameSpace)
        if (book == null) {
            context.success(returnData.setErrorMsg("请先加入书架"))
            return
        }
        if (book.isLocalBook() && book.isLocalTxt()) {
            val file = book.getLocalFile()
            context.response()
                .putHeader("Cache-Control", "300")
                .putHeader(
                    "Content-Disposition",
                    "attachment; filename=" + java.net.URLEncoder.encode(file.name, "UTF-8")
                )
                .sendFile(file.toString())
            return
        }
        if (book.isLocalTxt()) {
            val file = book.getLocalFile()
            context.response()
                .putHeader("Cache-Control", "300")
                .putHeader(
                    "Content-Disposition",
                    "attachment; filename=" + java.net.URLEncoder.encode(file.name, "UTF-8")
                )
                .sendFile(file.toString())
            return
        }
        val bookSource = getBookSourceString(context, book.origin) ?: ""
        if (!book.isLocalBook() && bookSource.length == 0) {
            context.success(returnData.setErrorMsg("未配置书源"))
            return
        }
        val exportDir = File(getWorkDir("storage", "assets", "export"))
        val file = if (isEpub == 1) {
            exportToEpub(exportDir, book, bookSource, userNameSpace)
        } else {
            exportToTxt(exportDir, book, bookSource, userNameSpace)
        }
        context.response()
            .putHeader("Cache-Control", "300")
            .putHeader(
                "Content-Disposition",
                "attachment; filename=" + java.net.URLEncoder.encode(file.name, "UTF-8")
            )
            .sendFile(file.toString())
    }

    suspend fun exportToTxt(
        exportDir: File,
        bookInfo: Book,
        bookSource: String,
        userNameSpace: String
    ): File {
        val filename = "《${bookInfo.name}》作者：${bookInfo.getRealAuthor()}.txt"
        val bookPath = FileUtils.getPath(exportDir, filename)
        val bookFile = FileUtils.createFileWithReplace(bookPath)
        getAllContents(bookInfo, bookSource, userNameSpace) { text, srcList ->
            bookFile.appendText(text, Charset.forName(appConfig.exportCharset))
        }
        return bookFile
    }

    private suspend fun getAllContents(
        book: Book,
        bookSourceString: String,
        userNameSpace: String,
        append: (String, ArrayList<Triple<String, Int, String>>?) -> Unit
    ) {
        val text =
            "${book.name}\n作者：${book.getRealAuthor()}\n简介：${HtmlFormatter.format(book.getDisplayIntro())}"
        append(text, null)
        val chapterList = getLocalChapterList(book, bookSourceString, false, userNameSpace)
        val localCacheDir = getChapterCacheDir(book, userNameSpace)
        chapterList.forEachIndexed { index, chapter ->
            val chapterCacheFile =
                File(localCacheDir.absolutePath + File.separator + index + ".txt")
            var content = ""
            if (!appConfig.exportNoChapterName) {
                content += chapter.title + '\n'
            }
            if (chapterCacheFile.exists()) {
                content += chapterCacheFile.readText() + '\n'
            } else {
                content += "暂无缓存内容。\n"
            }
            append("\n\n" + content, null)
        }
    }

    private suspend fun exportToEpub(
        exportDir: File,
        book: Book,
        bookSource: String,
        userNameSpace: String
    ): File {
        val fileName = "《${book.name}》作者：${book.getRealAuthor()}.epub"
        val filePath = FileUtils.getPath(exportDir, fileName)
        val file = FileUtils.createFileWithReplace(filePath)
        val epubBook = EpubBook()
        epubBook.version = "2.0"
        setEpubMetadata(book, epubBook)
        setCover(book, epubBook, bookSource)
        val assets = setAssets(book, epubBook)
        setEpubContent(assets, book, epubBook, bookSource, userNameSpace)
        EpubWriter().write(epubBook, FileOutputStream(file))
        return file
    }

    private fun setAssets(book: Book, epubBook: EpubBook): String {
        epubBook.resources.add(
            Resource(
                BookController::class.java.getResource("/epub/fonts.css").readBytes(),
                "Styles/fonts.css"
            )
        )
        epubBook.resources.add(
            Resource(
                BookController::class.java.getResource("/epub/main.css").readBytes(),
                "Styles/main.css"
            )
        )
        epubBook.resources.add(
            Resource(
                BookController::class.java.getResource("/epub/logo.png").readBytes(),
                "Images/logo.png"
            )
        )
        epubBook.addSection(
            "封面", ResourceUtil.createPublicResource(
                book.name, book.getRealAuthor(), book.getDisplayIntro(),
                book.kind, book.wordCount,
                String(
                    BookController::class.java.getResource("/epub/cover.html").readBytes(),
                    Charsets.UTF_8
                ), "Text/cover.html"
            )
        )
        epubBook.addSection(
            "简介", ResourceUtil.createPublicResource(
                book.name, book.getRealAuthor(), book.getDisplayIntro(),
                book.kind, book.wordCount,
                String(
                    BookController::class.java.getResource("/epub/intro.html").readBytes(),
                    Charsets.UTF_8
                ), "Text/intro.html"
            )
        )
        return String(
            BookController::class.java.getResource("/epub/chapter.html").readBytes(),
            Charsets.UTF_8
        )
    }

    private suspend fun setCover(book: Book, epubBook: EpubBook, bookSourceString: String?) {
        val coverUrl = book.getDisplayCover() ?: return
        if (coverUrl.startsWith("/")) {
            val coverFile = File(getWorkDir("storage", coverUrl.replace("/", File.separator).substring(1)))
            val byteArray = coverFile.readBytes()
            epubBook.coverImage = Resource(byteArray, "Images/cover.jpg")
        } else if (bookSourceString != null) {
            val ext = getFileExt(coverUrl, "jpg")
            val md5Encode = MD5Utils.md5Encode(coverUrl).toString()
            val cachePath = getWorkDir("storage", "cache", "$md5Encode.$ext")
            val cacheFile = File(cachePath)
            if (cacheFile.exists()) {
                val byteArray = cacheFile.readBytes()
                epubBook.coverImage = Resource(byteArray, "Images/cover.jpg")
                return
            }
            val analyzeUrl = AnalyzeUrl(
                coverUrl,
                source = BookSource.fromJson(bookSourceString).getOrNull()
            )
            try {
                epubBook.coverImage = Resource(analyzeUrl.getByteArrayAwait(), "Images/cover.jpg")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun setEpubContent(
        contentModel: String,
        book: Book,
        epubBook: EpubBook,
        bookSourceString: String,
        userNameSpace: String
    ) {
        val chapterList = getLocalChapterList(book, bookSourceString, false, userNameSpace)
        val localCacheDir = getChapterCacheDir(book, userNameSpace)
        chapterList.forEachIndexed { index, chapter ->
            var content = ""
            if (!appConfig.exportNoChapterName) {
                content += chapter.title + '\n'
            }
            if (book.isLocalTxt()) {
                content += LocalBook.getContent(book, chapter) ?: ""
            } else {
                val chapterCacheFile =
                    File(localCacheDir.absolutePath + File.separator + index + ".txt")
                if (chapterCacheFile.exists()) {
                    content += chapterCacheFile.readText() + '\n'
                } else {
                    content += "暂无缓存内容。\n"
                }
            }
            val content1 = fixPic(epubBook, book, content, chapter)
            val title = chapter.title
            epubBook.addSection(
                title, ResourceUtil.createChapterResource(
                    title.replace("🔒", ""),
                    content1, contentModel, "Text/chapter_$index.html"
                )
            )
        }
    }

    private fun fixPic(
        epubBook: EpubBook,
        book: Book,
        content: String,
        chapter: BookChapter
    ): String {
        val data = StringBuilder("")
        content.split("\n").forEach { text ->
            var text1 = text
            val matcher = AppPattern.imgPattern.matcher(text)
            while (matcher.find()) {
                val it = matcher.group(1) ?: continue
                val src = NetworkUtils.getAbsoluteURL(chapter.url, it)
                val originalHref = MD5Utils.md5Encode16(src) + '.' + BookHelp.getImageSuffix(src)
                val href = "Images/$originalHref"
                val imageFile = BookHelp.getImage(book, src)
                if (!imageFile.exists()) continue
                val fp = FileResourceProvider(imageFile.parent)
                val img = LazyResource(fp, href, originalHref)
                epubBook.resources.add(img)
                text1 = text1.replace(it, "../$href")
            }
            data.append(text1).append("\n")
        }
        return data.toString()
    }

    private fun setEpubMetadata(book: Book, epubBook: EpubBook) {
        val metadata = Metadata()
        metadata.titles.add(book.name)
        metadata.authors.add(Author(book.getRealAuthor()))
        metadata.language = "zh"
        metadata.dates.add(Date())
        metadata.publishers.add("Legado")
        metadata.descriptions.add(book.getDisplayIntro())
        epubBook.metadata = metadata
    }

    suspend fun searchBookContent(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        val bookUrl: String
        val keyword: String
        val lastIndex: Int
        val size: Int
        if (context.request().method() == HttpMethod.POST) {
            val url = context.bodyAsJson.getString("url")
            bookUrl = url ?: context.bodyAsJson.getString("bookUrl") ?: ""
            keyword = context.bodyAsJson.getString("keyword") ?: ""
            lastIndex = context.bodyAsJson.getInteger("lastIndex", 0)!!
            size = context.bodyAsJson.getInteger("size", 20)!!
        } else {
            bookUrl = context.queryParam("url").firstOrNull() ?: ""
            keyword = context.queryParam("keyword").firstOrNull() ?: ""
            lastIndex = context.queryParam("lastIndex").firstOrNull()?.toInt() ?: 0
            size = context.queryParam("size").firstOrNull()?.toInt() ?: 20
        }
        if (bookUrl.isEmpty()) {
            return returnData.setErrorMsg("请输入书籍链接")
        }
        if (keyword.isEmpty()) {
            return returnData.setErrorMsg("请输入搜索关键字")
        }
        val userNameSpace = getUserNameSpace(context)
        val book = getShelfBookByURL(bookUrl, userNameSpace)
        if (book == null) {
            return returnData.setErrorMsg("请先加入书架")
        }
        var bookSource: String? = null
        if (!book.isLocalBook()) {
            bookSource = getBookSourceString(context, book.origin)
            if (bookSource.isNullOrEmpty()) {
                return returnData.setErrorMsg("未配置书源")
            }
        }
        val chapterList = if (book.isLocalBook()) {
            getLocalChapterList(book, bookSource ?: "", false, userNameSpace)
        } else {
            WebBook(bookSource!!, appConfig.debugLog, userNameSpace = userNameSpace).getChapterList(book)
        }
        if (lastIndex >= chapterList.size) {
            return returnData.setErrorMsg("没有更多了")
        }
        val resultList = arrayListOf<SearchResult>()
        val isEnd = Ref.BooleanRef()
        context.request().connection().closeHandler { isEnd.element = true }
        logger.info("searchBookContent keyword: {} lastIndex: {}", keyword, lastIndex)
        var currentIndex = lastIndex + 1
        while (currentIndex < chapterList.size) {
            val chapter = chapterList[currentIndex]
            currentIndex++
            val chapterResult = searchChapter(book, chapter, keyword)
            if (chapterResult.isNotEmpty()) {
                resultList.addAll(chapterResult)
            }
            if (resultList.size < size && !isEnd.element && currentIndex < chapterList.size) {
                continue
            }
            break
        }
        return returnData.setData(
            mapOf(
                "list" to resultList,
                "lastIndex" to currentIndex
            )
        )
    }

    suspend fun searchChapter(book: Book, chapter: BookChapter, query: String): List<SearchResult> {
        val searchResultsWithinChapter: MutableList<SearchResult> = mutableListOf()
        val chapterContent = BookHelp.getContent(book, chapter)
        if (chapterContent != null) {
            val positions = searchPosition(chapterContent, query)
            logger.info("positions: {}", positions)
            positions.forEachIndexed { index, position ->
                val construct = getResultAndQueryIndex(chapterContent, position, query)
                val result = SearchResult(
                    resultCountWithinChapter = index,
                    resultText = construct.second,
                    chapterTitle = chapter.title,
                    query = query,
                    chapterIndex = chapter.index,
                    queryIndexInResult = construct.first,
                    queryIndexInChapter = position
                )
                searchResultsWithinChapter.add(result)
            }
        }
        return searchResultsWithinChapter
    }

    private suspend fun searchPosition(mContent: String, pattern: String): List<Int> {
        val position: MutableList<Int> = mutableListOf()
        var index = mContent.indexOf(pattern)
        if (index >= 0) {
            while (index >= 0) {
                position.add(index)
                index = mContent.indexOf(pattern, index + 1)
            }
        }
        return position
    }

    private fun getResultAndQueryIndex(
        content: String,
        queryIndexInContent: Int,
        query: String
    ): Pair<Int, String> {
        val length = 20
        var po1 = queryIndexInContent - length
        var po2 = queryIndexInContent + query.length + length
        if (po1 < 0) {
            po1 = 0
        }
        if (po2 > content.length) {
            po2 = content.length
        }
        val queryIndexInResult = queryIndexInContent - po1
        val newText = content.substring(po1, po2)
        return queryIndexInResult to newText
    }

    suspend fun backupToMongodb(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (!MongoManager.isInit()) {
            return returnData.setErrorMsg("请先设置 mongoUri")
        }
        if (!checkManagerAuth(context)) {
            return returnData.setData("NEED_SECURE_KEY").setErrorMsg("请输入管理密码")
        }
        val syncDataFileList = arrayListOf(*backupFileNames)
        val handler = { userNameSpace: String ->
            syncDataFileList.forEach { it ->
                val content = getUserStorage(userNameSpace, it)
                if (content != null) {
                    saveUserStorage(userNameSpace, it, content)
                }
            }
        }
        handler("default")
        if (appConfig.secure) {
            var userMap: MutableMap<String, Map<String, Any>> = linkedMapOf()
            val userMapJson = asJsonObject(getStorage("data", "users"))
            if (userMapJson != null) {
                userMap = userMapJson.map as MutableMap<String, Map<String, Any>>
            }
            userMap.forEach { it ->
                try {
                    val ns = it.value.getOrDefault("username", "") as String? ?: ""
                    if (ns.isNotEmpty()) {
                        handler(ns)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        getStorage("users")?.let {
            saveStorage("users", value = it)
        }
        return returnData.setData("")
    }

    suspend fun restoreFromMongodb(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (!MongoManager.isInit()) {
            return returnData.setErrorMsg("请先设置 mongoUri")
        }
        if (!checkManagerAuth(context)) {
            return returnData.setData("NEED_SECURE_KEY").setErrorMsg("请输入管理密码")
        }
        val syncDataFileList = arrayListOf(*backupFileNames)
        val handler = { userNameSpace: String ->
            syncDataFileList.forEach { fileName ->
                val file = File(getWorkDir("storage", "data", userNameSpace, "$fileName.json"))
                if (!file.exists()) return@forEach
                file.delete()
            }
        }
        handler("default")
        if (appConfig.secure) {
            var userMap: MutableMap<String, Map<String, Any>> = linkedMapOf()
            val userMapJson = asJsonObject(getStorage("data", "users"))
            if (userMapJson != null) {
                userMap = userMapJson.map as MutableMap<String, Map<String, Any>>
            }
            userMap.forEach { entry ->
                try {
                    val ns = entry.value.getOrDefault("username", "") as String? ?: ""
                    if (ns.isNotEmpty()) {
                        handler(ns)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        val usersFile = File(getWorkDir("storage", "users.json"))
        if (usersFile.exists()) {
            usersFile.delete()
        }
        getStorage("users")
        return returnData.setData("")
    }
}
