// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.data.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.jayway.jsonpath.DocumentContext
import io.legado.app.constant.AppPattern
import io.legado.app.model.localBook.CbzFile
import io.legado.app.model.localBook.EpubFile
import io.legado.app.model.localBook.LocalBook
import io.legado.app.model.localBook.UmdFile
import io.legado.app.utils.FileUtils
import io.legado.app.utils.GSON
import io.legado.app.utils.MD5Utils
import io.legado.app.utils.fromJsonObject
import io.legado.app.utils.jsonPath
import io.legado.app.utils.readBool
import io.legado.app.utils.readInt
import io.legado.app.utils.readLong
import io.legado.app.utils.readString
import mu.KLogger
import mu.KotlinLogging
import org.jsoup.Jsoup
import java.io.File
import java.nio.charset.Charset
import kotlin.math.max
import kotlin.math.min

val logger: KLogger = KotlinLogging.logger { }

/**
 * 书架上的书。以 bookUrl 为唯一标识，readConfig 的读写走 [Converters]。
 */
@JsonIgnoreProperties(
    value = [
        "variableMap", "infoHtml", "tocHtml", "config", "rootDir", "localBook", "epub",
        "epubRootDir", "onLineTxt", "localTxt", "umd", "realAuthor", "unreadChapterNum",
        "folderName", "pdfImageWidth", "localFile", "kindList", "_userNameSpace", "bookDir",
        "userNameSpace"
    ]
)
data class Book(
    override var bookUrl: String = "",
    var tocUrl: String = "",
    var origin: String = "loc_book",
    var originName: String = "",
    override var name: String = "",
    override var author: String = "",
    override var kind: String? = null,
    var customTag: String? = null,
    var coverUrl: String? = null,
    var customCoverUrl: String? = null,
    var intro: String? = null,
    var customIntro: String? = null,
    var charset: String? = null,
    var type: Int = 0,
    var group: Long = 0,
    var latestChapterTitle: String? = null,
    var latestChapterTime: Long = System.currentTimeMillis(),
    var lastCheckTime: Long = System.currentTimeMillis(),
    var lastCheckCount: Int = 0,
    var totalChapterNum: Int = 0,
    var durChapterTitle: String? = null,
    var durChapterIndex: Int = 0,
    var durChapterPos: Int = 0,
    var durChapterTime: Long = System.currentTimeMillis(),
    override var wordCount: String? = null,
    var canUpdate: Boolean = true,
    var order: Int = 0,
    var originOrder: Int = 0,
    var useReplaceRule: Boolean = true,
    var variable: String? = null,
    var readConfig: ReadConfig? = null,
    @get:JsonProperty("isInShelf")
    @get:JvmName("isInShelf")
    var inShelf: Boolean = false,
    var lastCheckError: String? = null
): BaseBook {

    fun isLocalBook(): Boolean {
        return origin == "loc_book"
    }

    fun isLocalTxt(): Boolean {
        return isLocalBook() && originName.endsWith(".txt", true)
    }

    fun isLocalEpub(): Boolean {
        return isLocalBook() && originName.endsWith(".epub", true)
    }

    fun isLocalPdf(): Boolean {
        return isLocalBook() && originName.endsWith(".pdf", true)
    }

    fun isEpub(): Boolean {
        return originName.endsWith(".epub", true)
    }

    fun isCbz(): Boolean {
        return originName.endsWith(".cbz", true)
    }

    fun isPdf(): Boolean {
        return originName.endsWith(".pdf", true)
    }

    fun isUmd(): Boolean {
        return originName.endsWith(".umd", true)
    }

    fun isOnLineTxt(): Boolean {
        return !isLocalBook() && type == 0
    }

    override fun equals(other: Any?): Boolean {
        if (other is Book) {
            return other.bookUrl == bookUrl
        }
        return false
    }

    override fun hashCode(): Int {
        return bookUrl.hashCode()
    }

    @delegate:Transient
    override val variableMap: HashMap<String, String> by lazy {
        GSON.fromJsonObject<HashMap<String, String>>(variable).getOrNull() ?: hashMapOf()
    }

    override fun putVariable(key: String, value: String?) {
        if (value != null) {
            variableMap[key] = value
        } else {
            variableMap.remove(key)
        }
        variable = GSON.toJson(variableMap)
    }

    override var infoHtml: String? = null

    override var tocHtml: String? = null

    fun getRealAuthor(): String = author.replace(AppPattern.authorRegex, "")

    fun getUnreadChapterNum(): Int = max(totalChapterNum - durChapterIndex - 1, 0)

    fun getDisplayCover(): String? =
        if (customCoverUrl.isNullOrEmpty()) coverUrl else customCoverUrl

    fun getDisplayIntro(): String? =
        if (customIntro.isNullOrEmpty()) intro else customIntro

    fun fileCharset(): Charset = Charset.forName(charset ?: "UTF-8")

    private fun config(): ReadConfig {
        if (readConfig == null) {
            readConfig = ReadConfig()
        }
        return readConfig!!
    }

    fun setDelTag(tag: Long) {
        config().delTag = if (config().delTag and tag == tag) {
            config().delTag and tag.inv()
        } else {
            config().delTag or tag
        }
    }

    fun getDelTag(tag: Long): Boolean {
        return config().delTag and tag == tag
    }

    fun getPdfImageWidth(): Float {
        return config().pdfImageWidth
    }

    fun setPdfImageWidth(pdfImageWidth: Float) {
        config().pdfImageWidth = pdfImageWidth
    }

    fun getFolderName(): String {
        var folderName = name.replace(AppPattern.fileNameRegex, "")
        folderName = folderName.substring(0, min(9, folderName.length))
        return folderName + MD5Utils.md5Encode16(bookUrl)
    }

    @Transient
    private var rootDir: String = ""

    fun setRootDir(root: String) {
        if (root.isNotEmpty() && !root.endsWith(File.separator)) {
            rootDir = root + File.separator
        } else {
            rootDir = root
        }
    }

    fun getLocalFile(): File {
        if (originName.startsWith(rootDir)) {
            originName = originName.replaceFirst(rootDir, "")
        }
        logger.info("getLocalFile rootDir: {} originName: {}", rootDir, originName)
        if (isEpub() && originName.indexOf("localStore") < 0 && originName.indexOf("webdav") < 0) {
            return FileUtils.getFile(File(rootDir + originName), "index.epub")
        }
        if (isCbz() && originName.indexOf("localStore") < 0 && originName.indexOf("webdav") < 0) {
            return FileUtils.getFile(File(rootDir + originName), "index.cbz")
        }
        if (isPdf() && originName.indexOf("localStore") < 0 && originName.indexOf("webdav") < 0) {
            return FileUtils.getFile(File(rootDir + originName), "index.pdf")
        }
        return File(rootDir + originName)
    }

    @Transient
    private var _userNameSpace: String = ""

    fun setUserNameSpace(nameSpace: String) {
        _userNameSpace = nameSpace
    }

    override fun getUserNameSpace(): String {
        return _userNameSpace
    }

    fun getBookDir(): String {
        return FileUtils.getPath(
            File(rootDir), "storage", "data", _userNameSpace, name + '_' + author
        )
    }

    fun getSplitLongChapter(): Boolean {
        return false
    }

    fun toSearchBook(): SearchBook {
        return SearchBook(
            name = name,
            author = author,
            kind = kind,
            bookUrl = bookUrl,
            origin = origin,
            originName = originName,
            type = type,
            wordCount = wordCount,
            latestChapterTitle = latestChapterTitle,
            coverUrl = coverUrl,
            intro = intro,
            tocUrl = tocUrl,
            variable = variable
        ).apply {
            infoHtml = this@Book.infoHtml
            tocHtml = this@Book.tocHtml
            setUserNameSpace(this@Book.getUserNameSpace())
        }
    }

    fun getEpubRootDir(): String {
        val defaultPath = "OEBPS"
        val containerRes = File(
            bookUrl + File.separator + "index" + File.separator + "META-INF" +
                File.separator + "container.xml"
        )
        if (containerRes.exists()) {
            try {
                val document = Jsoup.parse(containerRes.readText())
                val rootFileElement = document.getElementsByTag("rootfiles")[0]
                    .getElementsByTag("rootfile")[0]
                val result = rootFileElement.attr("full-path")
                println("result: $result")
                if (result != null && result.isNotEmpty()) {
                    return File(result).parentFile?.let { it.toString() } ?: ""
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return defaultPath
    }

    fun updateFromLocal(onlyCover: Boolean = false) {
        try {
            if (isEpub()) {
                EpubFile.upBookInfo(this, onlyCover)
            } else if (isUmd()) {
                UmdFile.upBookInfo(this, onlyCover)
            } else if (isCbz()) {
                CbzFile.upBookInfo(this, onlyCover)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun workRoot(): String {
        return rootDir
    }

    companion object {

        const val hTag = 2L
        const val rubyTag = 4L
        const val imgTag = 8L

        const val imgStyleDefault = "DEFAULT"
        const val imgStyleFull = "FULL"
        const val imgStyleText = "TEXT"

        fun initLocalBook(bookUrl: String, localPath: String, rootDir: String = ""): Book {
            val fileName = File(localPath).getName()
            val nameAuthor = LocalBook.analyzeNameAuthor(fileName)
            val book = Book(
                bookUrl,
                "",
                "loc_book",
                localPath,
                nameAuthor.first,
                nameAuthor.second
            ).also {
                it.canUpdate = false
            }
            book.setRootDir(rootDir)
            book.updateFromLocal()
            return book
        }

        fun fromJsonDoc(doc: DocumentContext): Result<Book> = runCatching {
            val readConfig = doc.read<Any>("$.readConfig")
            Book(
                bookUrl = doc.readString("$.bookUrl")!!,
                tocUrl = doc.readString("$.tocUrl")!!,
                origin = doc.readString("$.origin") ?: "loc_book",
                originName = doc.readString("$.originName") ?: "",
                name = doc.readString("$.name")!!,
                author = doc.readString("$.author") ?: "",
                kind = doc.readString("$.kind"),
                customTag = doc.readString("$.customTag"),
                coverUrl = doc.readString("$.coverUrl"),
                customCoverUrl = doc.readString("$.customCoverUrl"),
                intro = doc.readString("$.intro"),
                customIntro = doc.readString("$.customIntro"),
                charset = doc.readString("$.charset"),
                type = doc.readInt("$.type") ?: 0,
                group = doc.readLong("$.group") ?: 0L,
                latestChapterTitle = doc.readString("$.latestChapterTitle"),
                latestChapterTime = doc.readLong("$.latestChapterTime")
                    ?: System.currentTimeMillis(),
                lastCheckTime = doc.readLong("$.lastCheckTime") ?: System.currentTimeMillis(),
                lastCheckCount = doc.readInt("$.lastCheckCount") ?: 0,
                totalChapterNum = doc.readInt("$.totalChapterNum") ?: 0,
                durChapterTitle = doc.readString("$.durChapterTitle"),
                durChapterIndex = doc.readInt("$.durChapterIndex") ?: 0,
                durChapterPos = doc.readInt("$.durChapterPos") ?: 0,
                durChapterTime = doc.readLong("$.durChapterTime") ?: System.currentTimeMillis(),
                wordCount = doc.readString("$.wordCount"),
                canUpdate = doc.readBool("$.canUpdate") ?: true,
                order = doc.readInt("$.order") ?: 0,
                originOrder = doc.readInt("$.originOrder") ?: 0,
                useReplaceRule = doc.readBool("$.useReplaceRule") ?: true,
                variable = doc.readString("$.variable"),
                readConfig = if (readConfig != null) {
                    runCatching {
                        ReadConfig(
                            reverseToc = doc.readBool("$.readConfig.reverseToc") ?: false,
                            pageAnim = doc.readInt("$.readConfig.pageAnim") ?: -1,
                            reSegment = doc.readBool("$.readConfig.reSegment") ?: false,
                            useReplaceRule = doc.readBool("$.readConfig.useReplaceRule") ?: false,
                            delTag = doc.readLong("$.readConfig.delTag") ?: 0L,
                            imageStyle = doc.readString("$.readConfig.imageStyle")
                        )
                    }.getOrNull()
                } else null,
                inShelf = doc.readBool("$.isInShelf") ?: false
            )
        }

        fun fromJson(json: String): Result<Book> = fromJsonDoc(jsonPath.parse(json))

        fun fromJsonArray(jsonArray: String): Result<List<Book>> = runCatching {
            val sources = arrayListOf<Book>()
            val doc = jsonPath.parse(jsonArray).read<List<Any>>("$")
            doc.forEach {
                val jsonItem = jsonPath.parse(it)
                fromJsonDoc(jsonItem).getOrThrow().let { source ->
                    sources.add(source)
                }
            }
            sources
        }
    }

    data class ReadConfig(
        var reverseToc: Boolean = false,
        var pageAnim: Int = -1,
        var reSegment: Boolean = false,
        var imageStyle: String? = null,
        var useReplaceRule: Boolean = false,
        var delTag: Long = 0,
        var pdfImageWidth: Float = 800f
    )

    class Converters {

        fun readConfigToString(config: ReadConfig?): String = GSON.toJson(config)

        fun stringToReadConfig(json: String?): Result<ReadConfig?> =
            GSON.fromJsonObject<ReadConfig>(json)
    }
}
