package io.legado.server

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sun.management.HotSpotDiagnosticMXBean
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.net.URI
import java.net.URLDecoder
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.io.StringReader
import java.lang.management.ManagementFactory
import java.nio.charset.StandardCharsets
import java.nio.charset.Charset
import java.nio.file.AtomicMoveNotSupportedException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.nio.file.StandardOpenOption
import java.security.MessageDigest
import java.time.Duration
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Base64
import java.util.LinkedHashMap
import java.util.UUID
import java.util.concurrent.Callable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.zip.CRC32
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import javax.xml.parsers.DocumentBuilderFactory
import org.xml.sax.InputSource
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.HostAccess
import org.graalvm.polyglot.Value
import org.graalvm.polyglot.io.IOAccess
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.isRegularFile
import kotlin.io.path.name

private const val MAX_LOCAL_BOOK_BYTES = 64L * 1024 * 1024
private const val MAX_EPUB_ENTRY_BYTES = 4 * 1024 * 1024
private const val MAX_EPUB_IMAGE_BYTES = 1024 * 1024
private const val MAX_EPUB_CHAPTER_IMAGE_BYTES = 4 * 1024 * 1024
private const val JAVA_SCRIPT_RULE_TIMEOUT_MILLIS = 1_500L

fun main(args: Array<String>) {
    val options = ServerOptions.parse(args) ?: return
    Files.createDirectories(options.dataDir)

    val gson = GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create()
    val store = LegadoStore(options.dataDir, gson)
    val httpPort = if (options.portExplicit) options.port else store.webPortPreference(options.port)
    val wsPort = if (options.wsPortExplicit) options.wsPort else httpPort + 1
    val assets = StaticAssets(options.webRoot)
    val httpServer = LegadoHttpServer(options.host, httpPort, store, assets, gson)
    val wsServer = if (options.noWebSocket) null else LegadoWebSocketServer(options.host, wsPort, store, gson)

    httpServer.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false)
    wsServer?.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false)

    println("Legado server listening:")
    println("  HTTP      http://${displayHost(options.host)}:$httpPort/")
    if (wsServer != null) {
        println("  WebSocket ws://${displayHost(options.host)}:$wsPort/")
    }
    println("  Data      ${options.dataDir.toAbsolutePath().normalize()}")
    println("Press Ctrl+C to stop.")

    Runtime.getRuntime().addShutdownHook(Thread {
        httpServer.stop()
        wsServer?.stop()
    })
    CountDownLatch(1).await()
}

private fun displayHost(host: String): String = if (host == "0.0.0.0") "localhost" else host

data class ServerOptions(
    val host: String = "0.0.0.0",
    val port: Int = 1122,
    val wsPort: Int = port + 1,
    val dataDir: Path = defaultDataDir(),
    val webRoot: Path? = null,
    val noWebSocket: Boolean = false,
    val portExplicit: Boolean = false,
    val wsPortExplicit: Boolean = false,
) {
    companion object {
        fun parse(args: Array<String>): ServerOptions? {
            var host = "0.0.0.0"
            var port = 1122
            var wsPort: Int? = null
            var dataDir = defaultDataDir()
            var webRoot: Path? = null
            var noWebSocket = false
            var portExplicit = false
            var wsPortExplicit = false

            var index = 0
            while (index < args.size) {
                when (val arg = args[index]) {
                    "--help", "-h" -> {
                        printHelp()
                        return null
                    }

                    "--host" -> host = args.valueAfter(index++, arg)
                    "--port" -> {
                        port = args.valueAfter(index++, arg).toInt()
                        portExplicit = true
                    }
                    "--ws-port" -> {
                        wsPort = args.valueAfter(index++, arg).toInt()
                        wsPortExplicit = true
                    }
                    "--data-dir" -> dataDir = Paths.get(args.valueAfter(index++, arg))
                    "--web-root" -> webRoot = Paths.get(args.valueAfter(index++, arg))
                    "--no-websocket" -> noWebSocket = true
                    else -> error("Unknown option: $arg")
                }
                index++
            }
            return ServerOptions(
                host, port, wsPort ?: (port + 1), dataDir, webRoot, noWebSocket,
                portExplicit, wsPortExplicit,
            )
        }

        private fun Array<String>.valueAfter(index: Int, option: String): String {
            return getOrNull(index + 1) ?: error("$option requires a value")
        }

        private fun printHelp() {
            println(
                """
                Usage: legado-server [options]

                Options:
                  --host <host>       Bind address, default 0.0.0.0
                  --port <port>       HTTP port, default 1122
                  --ws-port <port>    WebSocket port, default port + 1
                  --data-dir <path>   Data directory
                  --web-root <path>   Override static web asset directory
                  --no-websocket      Disable compatibility WebSocket listener
                  -h, --help          Show help
                """.trimIndent()
            )
        }
    }
}

private fun defaultDataDir(): Path {
    val configured = System.getenv("LEGADO_SERVER_DATA")
    if (!configured.isNullOrBlank()) return Paths.get(configured)
    val xdg = System.getenv("XDG_DATA_HOME")
    return if (!xdg.isNullOrBlank()) {
        Paths.get(xdg, "legado-server")
    } else {
        Paths.get(System.getProperty("user.home"), ".local", "share", "legado-server")
    }
}

data class ReturnData(
    val isSuccess: Boolean,
    val errorMsg: String,
    val data: Any?,
) {
    companion object {
        fun ok(data: Any? = "") = ReturnData(true, "", data)
        fun error(message: String) = ReturnData(false, message, null)
    }
}

class LegadoHttpServer(
    host: String,
    port: Int,
    private val store: LegadoStore,
    private val assets: StaticAssets,
    private val gson: Gson,
) : NanoHTTPD(host, port) {
    private val imageProxy = ImageProxy(store::networkUserAgent, store::imageCachePolicy)
    private val maintenanceScheduler = Executors.newSingleThreadScheduledExecutor { runnable ->
        Thread(runnable, "legado-maintenance").apply { isDaemon = true }
    }

    init {
        maintenanceScheduler.scheduleAtFixedRate({
            try {
                if (store.scheduledMaintenanceDue()) {
                    store.runMaintenance(imageProxy.clear(), scheduled = true)
                }
            } catch (_: Exception) {
                // Scheduled maintenance must not interrupt the HTTP service.
            }
        }, 0, 1, TimeUnit.HOURS)
    }

    override fun serve(session: IHTTPSession): Response {
        store.recordRequest(session.method.name, session.uri)
        return try {
            if (session.method == Method.OPTIONS) {
                return cors(newFixedLengthResponse(""))
            }
            if (session.method == Method.GET && session.uri == "/") {
                return cors(redirectToWeb())
            }
            if (isProtectedEndpoint(session.uri) && store.requiresLocalAuthentication() && !store.isAuthenticated(session.headers["authorization"])) {
                return cors(unauthorized())
            }

            val result = when (session.method) {
                Method.GET -> handleGet(session)
                Method.POST -> handlePost(session)
                else -> ReturnData.error("Unsupported method: ${session.method}")
            }

            if (result == null) {
                cors(assets.response(session.uri))
            } else {
                cors(json(result))
            }
        } catch (image: ImageResponse) {
            cors(image.response)
        } catch (audio: AudioResponse) {
            cors(audio.response)
        } catch (download: DownloadResponse) {
            cors(download.response)
        } catch (error: Exception) {
            cors(json(ReturnData.error(error.message ?: error.javaClass.simpleName)))
        }
    }

    private fun handleGet(session: IHTTPSession): ReturnData? {
        val parameters = session.parameters
        return when (session.uri) {
            "/health" -> ReturnData.ok(
                mapOf(
                    "service" to "legado-server",
                    "dataDir" to store.dataDir.toAbsolutePath().normalize().toString(),
                )
            )

            "/getAuthState" -> store.getAuthState()
            "/getServerInfo" -> store.getServerInfo()
            "/checkForUpdates" -> store.checkForUpdates()
            "/exportData" -> store.exportData()
            "/getBackups" -> store.getBackups()
            "/getWebDavBackups" -> store.getWebDavBackups()
            "/getSourceChecks" -> store.getSourceChecks()
            "/getAppSettings" -> store.getAppSettings()
            "/getAppDataKinds" -> store.getAppDataKinds()
            "/getAppData" -> store.getAppData(parameters.first("kind"))
            "/getBookSource" -> store.getSource("bookSources", "bookSourceUrl", parameters.first("url"))
            "/getBookSources" -> store.getSources("bookSources")
            "/getRssSource" -> store.getSource("rssSources", "sourceUrl", parameters.first("url"))
            "/getRssSources" -> store.getSources("rssSources")
            "/getReplaceRules" -> store.getReplaceRules()
            "/getTxtTocRules" -> store.getTxtTocRules()
            "/getBookshelf" -> store.getBookshelf()
            "/refreshBookInfo" -> store.refreshBookInfo(parameters.first("url"))
            "/getChapterList" -> store.getChapterList(parameters.first("url"))
            "/refreshToc" -> store.getChapterList(parameters.first("url"), refresh = true)
            "/getBookContent" -> store.getBookContent(
                parameters.first("url"),
                parameters.first("index")?.toIntOrNull(),
            )
            "/getExploreSources" -> store.getExploreSources()

            "/getReadConfig" -> store.getReadConfig()
            "/downloadTaskFile" -> throw DownloadResponse(store.downloadTaskFile(parameters.first("id")))
            "/cover", "/image" -> throw ImageResponse(imageProxy.get(parameters.first("path")))
            else -> null
        }
    }

    private fun handlePost(session: IHTTPSession): ReturnData? {
        val post = parsePost(session)

        return when (session.uri) {
            "/authenticate" -> store.authenticate(post.postData)
            "/saveBookSource" -> store.saveSource("bookSources", "bookSourceUrl", post.postData, single = true)
            "/saveBookSources" -> store.saveSource("bookSources", "bookSourceUrl", post.postData, single = false)
            "/deleteBookSources" -> store.deleteSources("bookSources", "bookSourceUrl", post.postData)
            "/saveRssSource" -> store.saveSource("rssSources", "sourceUrl", post.postData, single = true)
            "/saveRssSources" -> store.saveSource("rssSources", "sourceUrl", post.postData, single = false)
            "/deleteRssSources" -> store.deleteSources("rssSources", "sourceUrl", post.postData)
            "/saveReplaceRule" -> store.saveReplaceRule(post.postData)
            "/deleteReplaceRule" -> store.deleteReplaceRule(post.postData)
            "/testReplaceRule" -> store.testReplaceRule(post.postData)
            "/searchBooks" -> store.searchBooks(post.postData)
            "/exploreBooks" -> store.exploreBooks(post.postData)
            "/debugSource" -> store.debugSource(post.postData)
            "/refreshRssSources" -> store.refreshRssSources(post.postData)
            "/updateRssArticles" -> store.updateRssArticles(post.postData)
            "/getRssArticleContent" -> store.getRssArticleContent(post.postData)
            "/requestHttpTts" -> throw AudioResponse(store.requestHttpTts(post.postData))
            "/lookupDictionary" -> store.lookupDictionary(post.postData)
            "/applyThemeConfig" -> store.applyThemeConfig(post.postData)
            "/applyReadStyle" -> store.applyReadStyle(post.postData)
            "/startBookDownload" -> store.startBookDownload(post.postData)
            "/cancelBookDownload" -> store.cancelBookDownload(post.postData)
            "/retryBookDownload" -> store.retryBookDownload(post.postData)
            "/clearExpiredCacheRecords" -> store.clearExpiredCacheRecords()
            "/findBookSourceCandidates" -> store.findBookSourceCandidates(post.postData)
            "/changeBookSource" -> store.changeBookSource(post.postData)
            "/autoChangeBookSource" -> store.autoChangeBookSource(post.postData)
            "/batchChangeBookSources" -> store.batchChangeBookSources(post.postData)
            "/saveTxtTocRule" -> store.saveTxtTocRule(post.postData)
            "/deleteTxtTocRule" -> store.deleteTxtTocRule(post.postData)
            "/saveBook" -> store.saveBook(post.postData)
            "/exportBook" -> store.exportBook(post.postData)
            "/exportBooks" -> store.exportBooks(post.postData)
            "/exportBookEpisodes" -> store.exportBookEpisodes(post.postData)
            "/uploadBook" -> store.uploadBook(post.postData)
            "/deleteBook" -> store.deleteBook(post.postData)
            "/saveBookProgress" -> store.saveBookProgress(post.postData)
            "/saveChapterList" -> store.saveChapterList(session.parameters.first("url"), post.postData)
            "/addLocalBook" -> store.addLocalBook(session.parameters, post.files)
            "/saveReadConfig" -> store.saveReadConfig(post.postData)
            "/saveAppSettings" -> store.saveAppSettings(post.postData)
            "/resetAppSettings" -> store.resetAppSettings()
            "/testWebDav" -> store.testWebDav()
            "/testUploadRule" -> store.testUploadRule()
            "/checkNewBackup" -> store.checkNewBackup()
            "/saveAppData" -> store.saveAppData(
                session.parameters.first("kind"),
                post.postData,
                session.parameters.first("mode"),
            )
            "/deleteAppData" -> store.deleteAppData(session.parameters.first("kind"), post.postData)
            "/createBackup" -> store.createBackup()
            "/restoreBackup" -> store.restoreBackup(post.postData)
            "/restoreWebDavBackup" -> store.restoreWebDavBackup(post.postData)
            "/deleteWebDavBackup" -> store.deleteWebDavBackup(post.postData)
            "/deleteBackup" -> store.deleteBackup(post.postData)
            "/checkSources" -> store.checkSources(post.postData)
            "/deleteSourceChecks" -> store.deleteSourceChecks()
            "/runMaintenance" -> store.runMaintenance(
                if (store.shouldCleanImageCache()) imageProxy.clear() else mapOf("entries" to 0, "bytes" to 0),
                scheduled = false,
            )
            "/importData" -> store.importData(post.postData)
            else -> null
        }
    }

    private fun parsePost(session: IHTTPSession): ParsedPost {
        val contentType = session.headers["content-type"].orEmpty()
        if (contentType.startsWith("multipart/form-data", ignoreCase = true)) {
            val files = HashMap<String, String>()
            session.parseBody(files)
            return ParsedPost(files["postData"], files)
        }
        val length = session.headers["content-length"]?.toIntOrNull() ?: 0
        val body = if (length > 0) {
            session.inputStream.readNBytes(length).toString(StandardCharsets.UTF_8)
        } else {
            ""
        }
        return ParsedPost(body, emptyMap())
    }

    private fun redirectToWeb(): Response =
        newFixedLengthResponse(
            Response.Status.REDIRECT,
            "text/plain; charset=utf-8",
            "Redirecting to Legado Web",
        ).apply {
            addHeader("Location", "/vue/index.html")
        }

    private fun json(data: ReturnData): Response {
        val bytes = gson.toJson(data).toByteArray(StandardCharsets.UTF_8)
        return newFixedLengthResponse(
            Response.Status.OK,
            "application/json; charset=utf-8",
            ByteArrayInputStream(bytes),
            bytes.size.toLong(),
        )
    }

    private fun unauthorized(): Response {
        val bytes = gson.toJson(ReturnData.error("Authentication required")).toByteArray(StandardCharsets.UTF_8)
        return newFixedLengthResponse(Response.Status.UNAUTHORIZED, "application/json; charset=utf-8", ByteArrayInputStream(bytes), bytes.size.toLong())
    }

    private fun isProtectedEndpoint(uri: String): Boolean = uri !in setOf("/health", "/getAuthState", "/authenticate", "/cover", "/image") &&
        !uri.contains('.') && !uri.startsWith("/vue/") && !uri.startsWith("/help/")

    private fun cors(response: Response): Response {
        response.addHeader("Access-Control-Allow-Origin", "*")
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
        response.addHeader("Access-Control-Allow-Headers", "content-type, authorization")
        return response
    }

    private fun Map<String, List<String>>.first(key: String): String? = this[key]?.firstOrNull()
}

private data class ParsedPost(
    val postData: String?,
    val files: Map<String, String>,
)

private data class ChapterMatch(
    val start: Int,
    val end: Int,
    val title: String,
)

private data class AppDataKind(
    val kind: String,
    val label: String,
    val description: String,
    val primaryKey: String,
    val status: String,
)

private data class JsonCompactionResult(val files: Int, val bytesSaved: Long)

private data class EpubImage(val fileName: String, val mime: String, val bytes: ByteArray)

class ImageResponse(val response: NanoHTTPD.Response) : RuntimeException()
class AudioResponse(val response: NanoHTTPD.Response) : RuntimeException()
class DownloadResponse(val response: NanoHTTPD.Response) : RuntimeException()

class ImageProxy(
    private val userAgent: () -> String,
    private val cachePolicy: () -> ImageCachePolicy,
) {
    private data class CachedImage(
        val statusCode: Int,
        val mime: String,
        val body: ByteArray,
        val cachedAt: Long,
    )

    private val client = HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.NORMAL)
        .connectTimeout(Duration.ofSeconds(10))
        .build()
    private val cache = LinkedHashMap<String, CachedImage>(16, 0.75f, true)
    private var cacheBytes = 0L

    fun get(path: String?): NanoHTTPD.Response {
        if (path.isNullOrBlank()) {
            return placeholder()
        }

        if (path.startsWith("http://", ignoreCase = true) || path.startsWith("https://", ignoreCase = true)) {
            cached(path)?.let { return response(it.statusCode, it.mime, it.body) }
            return try {
                val request = HttpRequest.newBuilder(URI.create(path))
                    .timeout(Duration.ofSeconds(20))
                    .header("User-Agent", userAgent())
                    .GET()
                    .build()
                val response = client.send(request, HttpResponse.BodyHandlers.ofByteArray())
                val body = response.body()
                val mime = response.headers().firstValue("content-type").orElse("application/octet-stream")
                cache(path, response.statusCode(), mime, body)
                response(response.statusCode(), mime, body)
            } catch (_: Exception) {
                placeholder()
            }
        }

        val file = Paths.get(path).normalize()
        if (file.isRegularFile()) {
            return NanoHTTPD.newFixedLengthResponse(
                NanoHTTPD.Response.Status.OK,
                mimeType(file),
                Files.newInputStream(file),
                Files.size(file),
            )
        }
        return placeholder()
    }

    @Synchronized
    private fun cached(path: String): CachedImage? {
        val policy = cachePolicy()
        if (policy.maxBytes <= 0) {
            cache.clear()
            cacheBytes = 0
            return null
        }
        trim(policy)
        return cache[path]
    }

    @Synchronized
    private fun cache(path: String, statusCode: Int, mime: String, body: ByteArray) {
        val policy = cachePolicy()
        if (policy.maxBytes <= 0 || body.size.toLong() > policy.maxBytes || statusCode !in 200..299) return
        cache.remove(path)?.let { cacheBytes -= it.body.size.toLong() }
        cache[path] = CachedImage(statusCode, mime, body, System.currentTimeMillis())
        cacheBytes += body.size.toLong()
        trim(policy)
    }

    private fun trim(policy: ImageCachePolicy) {
        if (policy.expireAfterMillis > 0) {
            val cutoff = System.currentTimeMillis() - policy.expireAfterMillis
            val expired = cache.entries.filter { it.value.cachedAt < cutoff }.map { it.key }
            expired.forEach { key ->
                cache.remove(key)?.let { cacheBytes -= it.body.size.toLong() }
            }
        }
        while (cache.isNotEmpty() &&
            (cacheBytes > policy.maxBytes || (policy.maxEntries > 0 && cache.size > policy.maxEntries))
        ) {
            val eldest = cache.entries.iterator().next()
            cacheBytes -= eldest.value.body.size.toLong()
            cache.remove(eldest.key)
        }
    }

    @Synchronized
    fun clear(): Map<String, Long> {
        val entries = cache.size.toLong()
        val bytes = cacheBytes
        cache.clear()
        cacheBytes = 0
        return mapOf("entries" to entries, "bytes" to bytes)
    }

    private fun response(statusCode: Int, mime: String, body: ByteArray): NanoHTTPD.Response =
        NanoHTTPD.newFixedLengthResponse(
            NanoHTTPD.Response.Status.lookup(statusCode) ?: NanoHTTPD.Response.Status.OK,
            mime,
            ByteArrayInputStream(body),
            body.size.toLong(),
        )

    private fun placeholder(): NanoHTTPD.Response {
        val svg = """
            <svg xmlns="http://www.w3.org/2000/svg" width="84" height="112" viewBox="0 0 84 112">
              <rect width="84" height="112" rx="6" fill="#e9ecef"/>
              <path d="M18 24h48v64H18z" fill="#ced4da"/>
              <path d="M26 36h32M26 48h32M26 60h24" stroke="#868e96" stroke-width="4" stroke-linecap="round"/>
            </svg>
        """.trimIndent().toByteArray(StandardCharsets.UTF_8)
        return NanoHTTPD.newFixedLengthResponse(
            NanoHTTPD.Response.Status.OK,
            "image/svg+xml",
            ByteArrayInputStream(svg),
            svg.size.toLong(),
        )
    }
}

data class ImageCachePolicy(val maxBytes: Long, val maxEntries: Int, val expireAfterMillis: Long)
data class RemoteBackup(val fileName: String, val modifiedTime: Long, val size: Long, val href: String)
private const val maxHttpTtsBytes = 20L * 1024 * 1024

class LegadoStore(
    val dataDir: Path,
    private val gson: Gson,
) {
    private val booksDir = dataDir.resolve("books")
    private val downloadsDir = dataDir.resolve("downloads")
    @Volatile private var sourceCheckClientMode = false
    @Volatile private var sourceCheckClient = buildNetworkClient(false)
    private val authSessions = ConcurrentHashMap<String, Long>()
    private val sourceCookieLock = Any()
    private val sourceRateLocks = ConcurrentHashMap<String, SourceRateState>()
    private val sourceRuleContext = ThreadLocal<JsonObject?>()
    private val javaScriptRuleContext = ThreadLocal<JavaScriptRuleContext?>()
    private val javaScriptRuleExecutor = Executors.newFixedThreadPool(2) { runnable ->
        Thread(runnable, "legado-js-rule").apply { isDaemon = true }
    }
    private val downloadExecutor = Executors.newFixedThreadPool(2) { runnable ->
        Thread(runnable, "legado-download").apply { isDaemon = true }
    }
    private val appDataKinds = listOf(
        AppDataKind("bookGroups", "书籍分组", "书架分组、排序、刷新策略", "groupId", "Web 可用"),
        AppDataKind("bookmarks", "书签摘录", "阅读器书签、划线、摘录内容", "time", "Web 可用"),
        AppDataKind("readRecords", "阅读记录", "最近阅读、阅读时长和入口历史", "id", "Web 可用"),
        AppDataKind("httpTTS", "HTTP TTS", "在线朗读引擎、请求头和登录脚本", "id", "Web 可用"),
        AppDataKind("cookies", "Cookie 管理", "启用 Cookie Jar 的书源登录 Cookie", "url", "Web 可用"),
        AppDataKind("dictRules", "字典规则", "划词字典查询规则", "name", "Web 可用"),
        AppDataKind("rssArticles", "RSS 文章缓存", "订阅源规则解析后的文章列表、分组和阅读状态", "link", "Web 可用"),
        AppDataKind("rssReadRecords", "RSS 阅读记录", "订阅阅读进度和已读记录", "record", "Web 可用"),
        AppDataKind("rssStars", "RSS 收藏", "订阅文章收藏和星标", "link", "Web 可用"),
        AppDataKind("cacheRecords", "缓存记录", "通用缓存、源变量和临时数据", "key", "Web 可用"),
        AppDataKind("downloadTasks", "下载任务", "离线下载、缓存书籍、媒体下载队列", "id", "Web 可用"),
        AppDataKind("themeConfigs", "主题方案", "Android 主题方案列表", "themeName", "Web 可用"),
        AppDataKind("readStyles", "阅读样式", "阅读排版方案、背景、字体和提示栏", "name", "Web 可用"),
    )

    init {
        Files.createDirectories(dataDir)
        Files.createDirectories(booksDir)
        Files.createDirectories(downloadsDir)
        seedDefaultData()
        applyCustomHostsPreference()
        applyHeapDumpPreference()
    }

    private fun buildNetworkClient(http2: Boolean): HttpClient = HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.NORMAL)
        .connectTimeout(Duration.ofSeconds(6))
        .version(if (http2) HttpClient.Version.HTTP_2 else HttpClient.Version.HTTP_1_1)
        .build()

    private fun networkClient(): HttpClient {
        val http2 = readAppSettings()["network"]
            .asObjectOrNull()
            ?.get("Cronet")
            ?.safeBoolean()
            ?: false
        if (sourceCheckClientMode == http2) return sourceCheckClient
        synchronized(this) {
            if (sourceCheckClientMode != http2) {
                sourceCheckClient = buildNetworkClient(http2)
                sourceCheckClientMode = http2
            }
            return sourceCheckClient
        }
    }

    @Synchronized
    fun getServerInfo(): ReturnData {
        networkClient()
        return ReturnData.ok(
            mapOf(
                "service" to "legado-server",
                "version" to serverVersion(),
                "dataDir" to dataDir.toAbsolutePath().normalize().toString(),
                "networkTransport" to if (sourceCheckClientMode) "HTTP/2" else "HTTP/1.1",
                "customHostCount" to customHosts().size,
                "heapDumpOnOom" to heapDumpEnabled(),
                "counts" to mapOf(
                    "books" to readList("books").size,
                    "bookSources" to readList("bookSources").size,
                    "rssSources" to readList("rssSources").size,
                    "replaceRules" to readList("replaceRules").size,
                    "txtTocRules" to readList("txtTocRules").size,
                    "settings" to readAppSettings().entrySet().sumOf { entry ->
                        entry.value.asObjectOrNull()?.entrySet()?.size ?: 1
                    },
                    "appData" to appDataKinds.sumOf { readList(it.kind).size },
                    "backups" to listBackupFiles().size,
                    "sourceChecks" to readList("sourceCheckReports").size,
                ),
            )
        )
    }

    fun recordRequest(method: String, uri: String) {
        val enabled = readAppSettings()["maintenance"]
            .asObjectOrNull()
            ?.get("recordLog")
            ?.safeBoolean()
            ?: false
        if (!enabled) return
        try {
            val logDir = dataDir.resolve("logs")
            Files.createDirectories(logDir)
            val logFile = logDir.resolve("server.log")
            val line = "${java.time.Instant.now()}\t$method\t${uri.take(2048)}\n"
            if (logFile.exists() && Files.size(logFile) > 10L * 1024 * 1024) {
                Files.writeString(logFile, line, StandardCharsets.UTF_8)
            } else {
                Files.writeString(
                    logFile,
                    line,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND,
                )
            }
        } catch (_: Exception) {
            // Logging must never interrupt a request.
        }
    }

    fun shouldCleanImageCache(): Boolean = readAppSettings()["maintenance"]
        .asObjectOrNull()
        ?.get("cleanCache")
        ?.safeBoolean()
        ?: false

    @Synchronized
    fun runMaintenance(cacheResult: Map<String, Long>, scheduled: Boolean): ReturnData {
        val settings = readAppSettings()
        val maintenance = settings["maintenance"].asObjectOrNull() ?: JsonObject().also {
            settings.add("maintenance", it)
        }
        val completedAt = java.time.Instant.now().toString()
        maintenance.addProperty("lastMaintenanceAt", completedAt)
        writeAppSettings(settings)
        val expiredCacheRecords = clearExpiredCacheRecordsCount()
        val compactResult = if (maintenance["shrinkDatabase"]?.safeBoolean() == true) {
            compactJsonStores()
        } else {
            JsonCompactionResult(0, 0)
        }
        return ReturnData.ok(mapOf(
            "completedAt" to completedAt,
            "cacheEntriesCleared" to (cacheResult["entries"] ?: 0),
            "cacheBytesCleared" to (cacheResult["bytes"] ?: 0),
            "expiredCacheRecordsRemoved" to expiredCacheRecords,
            "jsonFilesCompacted" to compactResult.files,
            "jsonBytesSaved" to compactResult.bytesSaved,
            "scheduled" to scheduled,
            "logFile" to dataDir.resolve("logs/server.log").toAbsolutePath().normalize().toString(),
        ))
    }

    fun scheduledMaintenanceDue(now: java.time.Instant = java.time.Instant.now()): Boolean {
        val maintenance = readAppSettings()["maintenance"].asObjectOrNull() ?: return false
        if (maintenance["cleanCacheOnSchedule"]?.safeBoolean() != true) return false
        val lastRun = maintenance.string("lastMaintenanceAt")
            ?.takeIf(String::isNotBlank)
            ?.let { runCatching { java.time.Instant.parse(it) }.getOrNull() }
        return lastRun == null || Duration.between(lastRun, now) >= Duration.ofHours(24)
    }

    @Synchronized
    fun exportData(): ReturnData {
        return ReturnData.ok(
            mapOf(
                "books" to readList("books"),
                "bookSources" to readList("bookSources"),
                "rssSources" to readList("rssSources"),
                "replaceRules" to readList("replaceRules"),
                "txtTocRules" to readList("txtTocRules"),
                "appSettings" to readAppSettings(),
                "appData" to exportAppData(),
            )
        )
    }

    @Synchronized
    fun importData(postData: String?): ReturnData {
        val data = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val ignored = restoreIgnoreSet()
        val allowLegacy = readAppSettings()["backup"]
            .asObjectOrNull()
            ?.get("import_old")
            ?.safeBoolean() == true
        val supported = mapOf(
            "books" to listOf("book", "bookList"),
            "bookSources" to listOf("bookSource", "sources"),
            "rssSources" to listOf("rssSource"),
            "replaceRules" to listOf("replaceRule"),
            "txtTocRules" to listOf("txtTocRule"),
        )
        val imported = linkedMapOf<String, Int>()
        for ((field, aliases) in supported) {
            if (field in ignored) continue
            val array = importArray(data, field, if (allowLegacy) aliases else emptyList()) ?: continue
            val items = array.mapNotNull { it.asObjectOrNull() }
            writeList(field, items)
            imported[field] = items.size
        }
        val importedSettings = importObject(
            data,
            "appSettings",
            if (allowLegacy) listOf("settings", "preferences") else emptyList(),
        )
        importedSettings?.let { settings ->
            if ("appSettings" !in ignored) {
                val kept = settings.deepCopy()
                val current = readAppSettings()
                ignored
                    .filter { it.startsWith("appSettings.") }
                    .map { it.removePrefix("appSettings.").substringBefore('.') }
                    .forEach { group ->
                        current[group]?.let { kept.add(group, it.deepCopy()) } ?: kept.remove(group)
                    }
                writeAppSettings(kept)
                imported["appSettings"] = kept.entrySet().sumOf { entry ->
                entry.value.asObjectOrNull()?.entrySet()?.size ?: 1
            }
            }
        }
        data["appData"].asObjectOrNull()?.let { appData ->
            for (kind in appDataKinds) {
                if (kind.kind in ignored || "appData.${kind.kind}" in ignored) continue
                val items = appData[kind.kind].asArrayOrNull()?.mapNotNull { it.asObjectOrNull() } ?: continue
                writeList(kind.kind, items)
                imported["appData.${kind.kind}"] = items.size
            }
        }
        return ReturnData.ok(imported)
    }

    private fun importArray(data: JsonObject, field: String, aliases: List<String>): JsonArray? {
        for (name in listOf(field) + aliases) {
            val value = data[name] ?: continue
            value.asArrayOrNull()?.let { return it }
            if (value.isJsonPrimitive && value.asJsonPrimitive.isString) {
                runCatching { JsonParser.parseString(value.asString).asArrayOrNull() }
                    .getOrNull()
                    ?.let { return it }
            }
        }
        return null
    }

    private fun importObject(data: JsonObject, field: String, aliases: List<String>): JsonObject? {
        for (name in listOf(field) + aliases) {
            val value = data[name] ?: continue
            value.asObjectOrNull()?.let { return it }
            if (value.isJsonPrimitive && value.asJsonPrimitive.isString) {
                runCatching { JsonParser.parseString(value.asString).asObjectOrNull() }
                    .getOrNull()
                    ?.let { return it }
            }
        }
        return null
    }

    @Synchronized
    fun getBackups(): ReturnData = ReturnData.ok(listBackups())

    @Synchronized
    fun createBackup(): ReturnData {
        val dir = backupDir()
        Files.createDirectories(dir)
        val fileName = "legado-server-backup-${System.currentTimeMillis()}.json"
        val target = dir.resolve(fileName).normalize()
        val payload = exportData().data ?: return ReturnData.error("Unable to export data")
        writeStringAtomic(target, gson.toJson(payload))
        if (onlyLatestBackup()) {
            listBackupFiles().filterNot { it == target }.forEach(Files::deleteIfExists)
        }
        val entry = backupEntry(target).toMutableMap()
        val backupSettings = readAppSettings()["backup"].asObjectOrNull() ?: JsonObject()
        if (backupSettings["exportToWebDav"]?.safeBoolean() == true) {
            val remote = uploadBackupToWebDav(target, backupSettings)
                ?: return ReturnData.error("Local backup created, but WebDAV upload failed")
            entry["remotePath"] = remote
        }
        return ReturnData.ok(entry)
    }

    @Synchronized
    fun restoreBackup(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val file = backupFile(payload.string("fileName")) ?: return ReturnData.error("fileName is required")
        if (!file.isRegularFile()) return ReturnData.error("Backup not found")
        return importData(Files.readString(file, StandardCharsets.UTF_8))
    }

    @Synchronized
    fun deleteBackup(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val file = backupFile(payload.string("fileName")) ?: return ReturnData.error("fileName is required")
        if (!file.isRegularFile()) return ReturnData.error("Backup not found")
        Files.delete(file)
        return ReturnData.ok(listBackups())
    }

    @Synchronized
    fun getWebDavBackups(): ReturnData {
        val backup = readAppSettings()["backup"]?.asObjectOrNull() ?: JsonObject()
        val directory = webDavBackupDirectory(backup) ?: return ReturnData.error("WebDAV is not configured")
        return try {
            val builder = HttpRequest.newBuilder(directory).timeout(Duration.ofSeconds(20))
                .method("PROPFIND", HttpRequest.BodyPublishers.noBody()).header("Depth", "1")
                .header("User-Agent", networkUserAgent())
            webDavAuthorization(backup)?.let { builder.header("Authorization", it) }
            val response = networkClient().send(builder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
            if (response.statusCode() !in 200..299 && response.statusCode() != 207) return ReturnData.error("WebDAV returned HTTP ${response.statusCode()}")
            ReturnData.ok(parseWebDavBackups(response.body()).sortedByDescending(RemoteBackup::modifiedTime))
        } catch (error: Exception) { ReturnData.error(error.message ?: error.javaClass.simpleName) }
    }

    @Synchronized
    fun restoreWebDavBackup(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val fileName = payload.string("fileName")?.takeIf { it.matches(Regex("[A-Za-z0-9._-]+\\.json")) }
            ?: return ReturnData.error("A valid backup fileName is required")
        val backup = readAppSettings()["backup"]?.asObjectOrNull() ?: JsonObject()
        val directory = webDavBackupDirectory(backup) ?: return ReturnData.error("WebDAV is not configured")
        return try {
            val target = directory.resolve("./${encodePathSegment(fileName)}")
            if (target.host != directory.host || !target.path.startsWith(directory.path.trimEnd('/') + "/")) return ReturnData.error("Invalid WebDAV backup path")
            val builder = HttpRequest.newBuilder(target).timeout(Duration.ofSeconds(45)).GET().header("User-Agent", networkUserAgent())
            webDavAuthorization(backup)?.let { builder.header("Authorization", it) }
            val response = networkClient().send(builder.build(), HttpResponse.BodyHandlers.ofByteArray())
            if (response.statusCode() !in 200..299) return ReturnData.error("WebDAV returned HTTP ${response.statusCode()}")
            if (response.body().size > 64 * 1024 * 1024) return ReturnData.error("Remote backup exceeds 64 MiB")
            importData(response.body().toString(StandardCharsets.UTF_8))
        } catch (error: Exception) { ReturnData.error(error.message ?: error.javaClass.simpleName) }
    }

    @Synchronized
    fun deleteWebDavBackup(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val fileName = payload.string("fileName")?.takeIf { it.matches(Regex("[A-Za-z0-9._-]+\\.json")) }
            ?: return ReturnData.error("A valid backup fileName is required")
        val backup = readAppSettings()["backup"]?.asObjectOrNull() ?: JsonObject()
        val directory = webDavBackupDirectory(backup) ?: return ReturnData.error("WebDAV is not configured")
        return try {
            val target = directory.resolve("./${encodePathSegment(fileName)}")
            if (target.host != directory.host || !target.path.startsWith(directory.path.trimEnd('/') + "/")) return ReturnData.error("Invalid WebDAV backup path")
            val builder = HttpRequest.newBuilder(target).timeout(Duration.ofSeconds(30))
                .method("DELETE", HttpRequest.BodyPublishers.noBody()).header("User-Agent", networkUserAgent())
            webDavAuthorization(backup)?.let { builder.header("Authorization", it) }
            val response = networkClient().send(builder.build(), HttpResponse.BodyHandlers.discarding())
            if (response.statusCode() !in 200..299 && response.statusCode() != 404) return ReturnData.error("WebDAV returned HTTP ${response.statusCode()}")
            getWebDavBackups()
        } catch (error: Exception) { ReturnData.error(error.message ?: error.javaClass.simpleName) }
    }

    @Synchronized
    fun getSourceChecks(): ReturnData {
        return ReturnData.ok(
            readList("sourceCheckReports").sortedByDescending { it["checkedAt"].safeLong() }
        )
    }

    @Synchronized
    fun checkSources(postData: String?): ReturnData {
        val options = parseJson(postData).asObjectOrNull() ?: JsonObject()
        val configured = parseJson(networkCheckConfig()).asObjectOrNull() ?: JsonObject()
        val scope = options.string("scope") ?: configured.string("scope") ?: "all"
        val onlyEnabled = options["onlyEnabled"]?.safeBoolean()
            ?: configured["onlyEnabled"]?.safeBoolean()
            ?: false
        val timeoutMillis = (
            options["timeoutMillis"].safeIntOrNull()
                ?: configured["timeoutMillis"].safeIntOrNull()
                ?: 6000
            ).coerceIn(1000, 15000)
        val limit = (
            options["limit"].safeIntOrNull()
                ?: configured["limit"].safeIntOrNull()
                ?: 80
            ).coerceIn(1, 300)
        val checkedAt = System.currentTimeMillis()
        val reports = mutableListOf<JsonObject>()
        val candidates = mutableListOf<JsonObject>()

        if (scope == "all" || scope == "bookSources") {
            readList("bookSources").forEach { source ->
                candidates.add(sourceCheckCandidate("bookSource", source, "bookSourceName", "bookSourceUrl"))
            }
        }
        if (scope == "all" || scope == "rssSources") {
            readList("rssSources").forEach { source ->
                candidates.add(sourceCheckCandidate("rssSource", source, "sourceName", "sourceUrl"))
            }
        }

        val selected = candidates
            .filter { !onlyEnabled || it["enabled"]?.safeBoolean() != false }
            .take(limit)
        val workers = networkThreadCount().coerceAtMost(selected.size).coerceAtLeast(1)
        val executor = Executors.newFixedThreadPool(workers)
        try {
            executor.invokeAll(
                selected.map { candidate ->
                    Callable { checkSourceCandidate(candidate, timeoutMillis, checkedAt) }
                }
            ).forEach { reports.add(it.get()) }
        } finally {
            executor.shutdown()
        }

        writeList("sourceCheckReports", reports)
        return ReturnData.ok(
            mapOf(
                "summary" to mapOf(
                    "total" to reports.size,
                    "ok" to reports.count { it["ok"]?.safeBoolean() == true },
                    "failed" to reports.count { it["ok"]?.safeBoolean() != true },
                    "skipped" to (candidates.size - selected.size).coerceAtLeast(0),
                    "checkedAt" to checkedAt,
                ),
                "reports" to reports,
            )
        )
    }

    @Synchronized
    fun deleteSourceChecks(): ReturnData {
        writeList("sourceCheckReports", emptyList())
        return ReturnData.ok(emptyList<JsonObject>())
    }

    @Synchronized
    fun getAppSettings(): ReturnData = ReturnData.ok(readAppSettings())

    fun getAuthState(): ReturnData = ReturnData.ok(mapOf("required" to requiresLocalAuthentication()))

    fun authenticate(postData: String?): ReturnData {
        val password = parseJson(postData)?.asObjectOrNull()?.string("password").orEmpty()
        val configured = localPassword()
        if (configured.isBlank()) return ReturnData.ok(mapOf("token" to "", "expiresAt" to 0L))
        if (!MessageDigest.isEqual(configured.toByteArray(StandardCharsets.UTF_8), password.toByteArray(StandardCharsets.UTF_8))) return ReturnData.error("Invalid password")
        val token = UUID.randomUUID().toString().replace("-", "")
        val expiresAt = System.currentTimeMillis() + Duration.ofDays(30).toMillis()
        authSessions[token] = expiresAt
        return ReturnData.ok(mapOf("token" to token, "expiresAt" to expiresAt))
    }

    fun requiresLocalAuthentication(): Boolean = localPassword().isNotBlank()

    fun isAuthenticated(authorization: String?): Boolean {
        val token = authorization?.removePrefix("Bearer ")?.trim().orEmpty()
        val expiresAt = authSessions[token] ?: return false
        if (expiresAt > System.currentTimeMillis()) return true
        authSessions.remove(token)
        return false
    }

    @Synchronized
    fun checkForUpdates(): ReturnData {
        val maintenance = readAppSettings()["maintenance"].asObjectOrNull() ?: JsonObject()
        val channel = maintenance.string("updateToVariant").orEmpty().ifBlank { "default_version" }
        if (channel != "default_version") {
            return ReturnData.ok(
                mapOf(
                    "channel" to channel,
                    "currentVersion" to serverVersion(),
                    "newer" to false,
                    "message" to "该 Android 分发渠道不提供 Linux 服务端更新，请使用默认版检查 GitHub Releases。",
                )
            )
        }
        val started = System.nanoTime()
        return try {
            val request = HttpRequest.newBuilder(URI.create("https://api.github.com/repos/warpdotsys/legado/releases"))
                .timeout(Duration.ofSeconds(12))
                .header("Accept", "application/vnd.github+json")
                .header("User-Agent", networkUserAgent())
                .GET()
                .build()
            val response = networkClient().send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
            if (response.statusCode() !in 200..299) {
                return ReturnData.ok(
                    mapOf(
                        "channel" to channel,
                        "currentVersion" to serverVersion(),
                        "newer" to false,
                        "statusCode" to response.statusCode(),
                        "message" to "GitHub API returned HTTP ${response.statusCode()}",
                    )
                )
            }
            val releases = JsonParser.parseString(response.body()).asArrayOrNull() ?: JsonArray()
            val release = releases.mapNotNull { it.asObjectOrNull() }
                .firstOrNull { it["draft"]?.safeBoolean() != true && it["prerelease"]?.safeBoolean() != true }
            if (release == null) {
                return ReturnData.ok(
                    mapOf(
                        "channel" to channel,
                        "currentVersion" to serverVersion(),
                        "newer" to false,
                        "message" to "GitHub Releases 中没有稳定版本。",
                    )
                )
            }
            val latestVersion = release.string("tag_name").orEmpty().removePrefix("v")
            val current = serverVersion()
            val versionedBuild = current.any(Char::isDigit)
            val newer = versionedBuild && compareVersion(latestVersion, current) > 0
            ReturnData.ok(
                mapOf(
                    "channel" to channel,
                    "currentVersion" to current,
                    "latestVersion" to latestVersion,
                    "releaseName" to release.string("name").orEmpty(),
                    "releaseUrl" to release.string("html_url").orEmpty(),
                    "publishedAt" to release.string("published_at").orEmpty(),
                    "newer" to newer,
                    "latencyMs" to ((System.nanoTime() - started) / 1_000_000).coerceAtLeast(0),
                    "message" to if (versionedBuild) "已检查 GitHub Releases。" else "当前服务端构建未标记版本，已返回最新发布信息。",
                )
            )
        } catch (error: Exception) {
            ReturnData.ok(
                mapOf(
                    "channel" to channel,
                    "currentVersion" to serverVersion(),
                    "newer" to false,
                    "latencyMs" to ((System.nanoTime() - started) / 1_000_000).coerceAtLeast(0),
                    "message" to (error.message ?: error.javaClass.simpleName),
                )
            )
        }
    }

    @Synchronized
    fun saveAppSettings(postData: String?): ReturnData {
        val settings = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        writeAppSettings(mergeDefaults(defaultAppSettings(), settings))
        applyCustomHostsPreference()
        applyHeapDumpPreference()
        authSessions.clear()
        return ReturnData.ok(readAppSettings())
    }

    @Synchronized
    fun resetAppSettings(): ReturnData {
        writeAppSettings(defaultAppSettings())
        applyCustomHostsPreference()
        applyHeapDumpPreference()
        authSessions.clear()
        return ReturnData.ok(readAppSettings())
    }


    @Synchronized
    fun testWebDav(): ReturnData {
        val backup = readAppSettings()["backup"]?.asObjectOrNull() ?: JsonObject()
        val rawUrl = backup.string("web_dav_url")?.trim().orEmpty()
        if (rawUrl.isBlank()) return ReturnData.error("WebDAV URL is required")

        val uri = try {
            URI.create(rawUrl)
        } catch (_: Exception) {
            return ReturnData.error("WebDAV URL is invalid")
        }
        if (uri.scheme !in setOf("http", "https") || uri.host.isNullOrBlank()) {
            return ReturnData.error("WebDAV URL must be HTTP or HTTPS")
        }

        val started = System.nanoTime()
        return try {
            val requestBuilder = HttpRequest.newBuilder(uri)
                .timeout(Duration.ofSeconds(12))
                .method("PROPFIND", HttpRequest.BodyPublishers.noBody())
                .header("Depth", "0")
                .header("User-Agent", networkUserAgent())
            val account = backup.string("web_dav_account")?.trim().orEmpty()
            val password = backup.string("web_dav_password").orEmpty()
            if (account.isNotEmpty() || password.isNotEmpty()) {
                val credentials = Base64.getEncoder().encodeToString("$account:$password".toByteArray(StandardCharsets.UTF_8))
                requestBuilder.header("Authorization", "Basic $credentials")
            }
            val response = networkClient().send(requestBuilder.build(), HttpResponse.BodyHandlers.discarding())
            val latencyMs = ((System.nanoTime() - started) / 1_000_000).coerceAtLeast(0)
            val status = response.statusCode()
            val ok = status in 200..299 || status == 207
            ReturnData.ok(
                mapOf(
                    "ok" to ok,
                    "statusCode" to status,
                    "latencyMs" to latencyMs,
                    "message" to if (ok) "WebDAV endpoint is reachable" else "HTTP $status",
                    "target" to "${uri.scheme}://${uri.authority}${uri.path.orEmpty()}",
                )
            )
        } catch (error: Exception) {
            val latencyMs = ((System.nanoTime() - started) / 1_000_000).coerceAtLeast(0)
            ReturnData.ok(
                mapOf(
                    "ok" to false,
                    "latencyMs" to latencyMs,
                    "message" to (error.message ?: error.javaClass.simpleName),
                )
            )
        }
    }

    @Synchronized
    fun checkNewBackup(): ReturnData {
        val backup = readAppSettings()["backup"]?.asObjectOrNull() ?: JsonObject()
        if (backup["autoCheckNewBackup"]?.safeBoolean() == false) {
            return ReturnData.ok(mapOf("enabled" to false, "newer" to false))
        }
        val remoteUri = webDavBackupDirectory(backup)
            ?: return ReturnData.ok(mapOf("enabled" to true, "configured" to false, "newer" to false))
        return try {
            val builder = HttpRequest.newBuilder(remoteUri)
                .timeout(Duration.ofSeconds(15))
                .method("PROPFIND", HttpRequest.BodyPublishers.noBody())
                .header("Depth", "1")
                .header("User-Agent", networkUserAgent())
            webDavAuthorization(backup)?.let { builder.header("Authorization", it) }
            val response = networkClient().send(builder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
            if (response.statusCode() !in 200..299 && response.statusCode() != 207) {
                return ReturnData.ok(mapOf(
                    "enabled" to true,
                    "configured" to true,
                    "newer" to false,
                    "statusCode" to response.statusCode(),
                    "message" to "HTTP ${response.statusCode()}",
                ))
            }
            val remote = parseWebDavBackups(response.body()).maxByOrNull(RemoteBackup::modifiedTime)
            val local = listBackupFiles().firstOrNull()
            val localModified = local?.let { Files.getLastModifiedTime(it).toMillis() } ?: 0L
            ReturnData.ok(mapOf(
                "enabled" to true,
                "configured" to true,
                "newer" to (remote != null && remote.modifiedTime > localModified + 1000),
                "remote" to remote,
                "localModifiedTime" to localModified,
                "statusCode" to response.statusCode(),
            ))
        } catch (error: Exception) {
            ReturnData.ok(mapOf(
                "enabled" to true,
                "configured" to true,
                "newer" to false,
                "message" to (error.message ?: error.javaClass.simpleName),
            ))
        }
    }

    fun testUploadRule(): ReturnData = uploadByRule(
        "legado-upload-test.json",
        "application/json",
        "{}".toByteArray(StandardCharsets.UTF_8),
    )

    fun uploadBook(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val bookUrl = payload.string("bookUrl") ?: return ReturnData.error("bookUrl is required")
        val request = JsonObject().apply { addProperty("bookUrl", bookUrl) }
        val exported = exportBook(request.toString())
        if (!exported.isSuccess) return exported
        val data = exported.data as? Map<*, *> ?: return ReturnData.error("Export result is invalid")
        val fileName = data["fileName"]?.toString() ?: return ReturnData.error("Export filename is missing")
        val mime = data["mime"]?.toString() ?: "application/octet-stream"
        val encoded = data["base64"]?.toString() ?: return ReturnData.error("Export data is missing")
        val bytes = runCatching { Base64.getDecoder().decode(encoded) }
            .getOrElse { return ReturnData.error("Export data is invalid") }
        return uploadByRule(fileName, mime, bytes)
    }

    private fun uploadByRule(fileName: String, contentType: String, sourceBytes: ByteArray): ReturnData {
        if (sourceBytes.size > 64 * 1024 * 1024) return ReturnData.error("Upload exceeds 64 MiB")
        val rawRule = readAppSettings()["network"].asObjectOrNull()?.string("uploadRule")?.trim().orEmpty()
        val rule = parseJson(rawRule)?.asObjectOrNull() ?: return ReturnData.error("Upload rule must be a JSON object")
        val rawUrl = rule.string("uploadUrl")?.trim().orEmpty()
        val resultRule = rule.string("downloadUrlRule")?.trim().orEmpty()
        if (rawUrl.isEmpty()) return ReturnData.error("uploadUrl is required")
        if (resultRule.isEmpty()) return ReturnData.error("downloadUrlRule is required")
        var uploadName = fileName
        var uploadType = contentType
        var uploadBytes = sourceBytes
        if (rule["compress"]?.safeBoolean() == true && contentType != "application/zip") {
            uploadName = "$fileName.zip"
            uploadType = "application/zip"
            uploadBytes = ByteArrayOutputStream().use { output ->
                ZipOutputStream(output, StandardCharsets.UTF_8).use { zip ->
                    zip.putNextEntry(ZipEntry(fileName.replace('/', '_').replace('\\', '_')))
                    zip.write(sourceBytes)
                    zip.closeEntry()
                }
                output.toByteArray()
            }
        }
        val target = runCatching {
            URI.create(rawUrl.replace("{fileName}", encodePathSegment(uploadName)))
        }.getOrElse { return ReturnData.error("uploadUrl is invalid") }
        if (target.scheme !in setOf("http", "https") || target.host.isNullOrBlank()) {
            return ReturnData.error("uploadUrl must be HTTP or HTTPS")
        }
        val boundary = "----Legado${System.nanoTime()}"
        val safeName = uploadName.replace('"', '_').replace('\r', '_').replace('\n', '_')
        val body = ByteArrayOutputStream().use { output ->
            output.write(("--$boundary\r\n" +
                "Content-Disposition: form-data; name=\"file\"; filename=\"$safeName\"\r\n" +
                "Content-Type: $uploadType\r\n\r\n").toByteArray(StandardCharsets.UTF_8))
            output.write(uploadBytes)
            output.write("\r\n--$boundary--\r\n".toByteArray(StandardCharsets.UTF_8))
            output.toByteArray()
        }
        return try {
            val request = HttpRequest.newBuilder(target)
                .timeout(Duration.ofSeconds(60))
                .header("Content-Type", "multipart/form-data; boundary=$boundary")
                .header("User-Agent", networkUserAgent())
                .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                .build()
            val response = networkClient().send(request, HttpResponse.BodyHandlers.ofByteArray())
            if (response.statusCode() !in 200..299) return ReturnData.error("Upload failed: HTTP ${response.statusCode()}")
            if (response.body().size > 1024 * 1024) return ReturnData.error("Upload response exceeds 1 MiB")
            val responseText = response.body().toString(StandardCharsets.UTF_8)
            val downloadUrl = extractDownloadUrl(responseText, resultRule)
                ?: return ReturnData.error("downloadUrlRule did not match the response")
            ReturnData.ok(mapOf(
                "downloadUrl" to downloadUrl,
                "statusCode" to response.statusCode(),
                "fileName" to uploadName,
                "summary" to rule.string("summary").orEmpty(),
            ))
        } catch (error: Exception) {
            ReturnData.error(error.message ?: error.javaClass.simpleName)
        }
    }

    private fun extractDownloadUrl(response: String, rule: String): String? {
        if (rule == "$") return response.trim().takeIf(String::isNotEmpty)
        if (rule.startsWith("$.")) {
            var current: JsonElement = parseJson(response) ?: return null
            for (segment in rule.removePrefix("$.").split('.')) {
                current = current.asObjectOrNull()?.get(segment) ?: return null
            }
            return current.takeIf(JsonElement::isJsonPrimitive)?.asString?.trim()?.takeIf(String::isNotEmpty)
        }
        val match = runCatching { Regex(rule).find(response) }.getOrNull() ?: return null
        return (match.groupValues.getOrNull(1) ?: match.value).trim().takeIf(String::isNotEmpty)
    }

    private fun webDavBackupDirectory(backup: JsonObject): URI? {
        val rawUrl = backup.string("web_dav_url")?.trim()?.trimEnd('/').orEmpty()
        if (rawUrl.isEmpty()) return null
        val segments = listOf(
            backup.string("webDavDir")?.trim().orEmpty(),
            backup.string("webDavDeviceName")?.trim().orEmpty(),
        ).filter(String::isNotEmpty)
        return runCatching {
            URI.create(rawUrl + segments.joinToString(separator = "", prefix = "") { "/${encodePathSegment(it)}" })
        }.getOrNull()?.takeIf { it.scheme in setOf("http", "https") && !it.host.isNullOrBlank() }
    }

    private fun webDavAuthorization(backup: JsonObject): String? {
        val account = backup.string("web_dav_account")?.trim().orEmpty()
        val password = backup.string("web_dav_password").orEmpty()
        if (account.isEmpty() && password.isEmpty()) return null
        return "Basic " + Base64.getEncoder().encodeToString(
            "$account:$password".toByteArray(StandardCharsets.UTF_8)
        )
    }

    private fun parseWebDavBackups(xml: String): List<RemoteBackup> {
        val factory = DocumentBuilderFactory.newInstance().apply {
            isNamespaceAware = true
            setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
            setFeature("http://xml.org/sax/features/external-general-entities", false)
            setFeature("http://xml.org/sax/features/external-parameter-entities", false)
            setAttribute("http://javax.xml.XMLConstants/property/accessExternalDTD", "")
            setAttribute("http://javax.xml.XMLConstants/property/accessExternalSchema", "")
        }
        val document = factory.newDocumentBuilder().parse(InputSource(StringReader(xml)))
        val responses = document.getElementsByTagNameNS("*", "response")
        return (0 until responses.length).mapNotNull { index ->
            val element = responses.item(index) as? org.w3c.dom.Element ?: return@mapNotNull null
            val href = element.getElementsByTagNameNS("*", "href").item(0)?.textContent?.trim().orEmpty()
            val decoded = runCatching { URLDecoder.decode(href, StandardCharsets.UTF_8) }.getOrDefault(href)
            val fileName = decoded.trimEnd('/').substringAfterLast('/')
            if (!fileName.endsWith(".json", ignoreCase = true)) return@mapNotNull null
            val modifiedText = element.getElementsByTagNameNS("*", "getlastmodified").item(0)?.textContent?.trim().orEmpty()
            val modified = runCatching {
                ZonedDateTime.parse(modifiedText, DateTimeFormatter.RFC_1123_DATE_TIME).toInstant().toEpochMilli()
            }.getOrDefault(0L)
            val size = element.getElementsByTagNameNS("*", "getcontentlength").item(0)
                ?.textContent?.trim()?.toLongOrNull() ?: 0L
            RemoteBackup(fileName, modified, size, href)
        }
    }

    @Synchronized
    fun getAppDataKinds(): ReturnData {
        return ReturnData.ok(
            appDataKinds.map { kind ->
                mapOf(
                    "kind" to kind.kind,
                    "label" to kind.label,
                    "description" to kind.description,
                    "primaryKey" to kind.primaryKey,
                    "status" to kind.status,
                    "count" to readList(kind.kind).size,
                )
            }
        )
    }

    @Synchronized
    fun getAppData(kindName: String?): ReturnData {
        val kind = appDataKind(kindName) ?: return ReturnData.error("Unsupported app data kind: $kindName")
        return ReturnData.ok(readList(kind.kind))
    }

    @Synchronized
    fun saveAppData(kindName: String?, postData: String?, mode: String?): ReturnData {
        val kind = appDataKind(kindName) ?: return ReturnData.error("Unsupported app data kind: $kindName")
        val data = parseJson(postData) ?: return ReturnData.error("Request body is required")
        val items = when {
            data.isJsonArray -> data.asJsonArray.mapNotNull { it.asObjectOrNull() }
            data.isJsonObject -> listOf(data.asJsonObject)
            else -> return ReturnData.error("Expected JSON object or array")
        }.mapIndexed { index, item -> item.withAppDataIdentity(kind, index) }

        if (mode == "replace") {
            writeList(kind.kind, items)
        } else {
            for (item in items) {
                upsert(kind.kind, kind.primaryKey, item)
            }
        }
        if (kind.kind == "rssArticles") syncRssArticleMetadata(readList("rssArticles"))
        return ReturnData.ok(readList(kind.kind))
    }

    @Synchronized
    fun applyThemeConfig(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val themeName = payload.string("themeName")?.trim().orEmpty()
        if (themeName.isBlank()) return ReturnData.error("themeName is required")
        val config = readList("themeConfigs").firstOrNull { it.string("themeName") == themeName }
            ?: return ReturnData.error("Theme config not found")
        val settings = readAppSettings()
        val theme = settings["theme"].asObjectOrNull() ?: JsonObject().also { settings.add("theme", it) }
        val night = config["isNight"]?.safeBoolean() == true
        config.string("colorPrimary")?.takeIf(String::isNotBlank)?.let {
            theme.addProperty(if (night) "colorPrimaryNight" else "colorPrimary", it)
        }
        config.string("colorAccent")?.takeIf(String::isNotBlank)?.let {
            theme.addProperty(if (night) "colorAccentNight" else "colorAccent", it)
        }
        config.string("backgroundColor")?.takeIf(String::isNotBlank)?.let {
            theme.addProperty(if (night) "colorBackgroundNight" else "colorBackground", it)
        }
        config.string("textColor")?.takeIf(String::isNotBlank)?.let {
            theme.addProperty(if (night) "textColorNight" else "textColor", it)
        }
        config.string("config")?.let { raw ->
            val extra = runCatching { JsonParser.parseString(raw).asObjectOrNull() }.getOrNull()
            extra?.entrySet()?.forEach { (key, value) -> theme.add(key, value.deepCopy()) }
        }
        val main = settings["main"].asObjectOrNull() ?: JsonObject().also { settings.add("main", it) }
        main.addProperty("themeMode", if (night) "2" else "1")
        writeAppSettings(settings)
        return ReturnData.ok(settings)
    }

    @Synchronized
    fun applyReadStyle(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val styleName = payload.string("name")?.trim().orEmpty()
        if (styleName.isBlank()) return ReturnData.error("name is required")
        val styles = readList("readStyles")
        val index = styles.indexOfFirst { it.string("name") == styleName }
        if (index < 0) return ReturnData.error("Read style not found")
        val settings = readAppSettings()
        val read = settings["read"].asObjectOrNull() ?: JsonObject().also { settings.add("read", it) }
        val target = payload["target"]?.asString?.takeIf { it == "comic" } ?: "normal"
        read.addProperty(if (target == "comic") "comicStyleSelect" else "readStyleSelect", index)
        writeAppSettings(settings)
        return ReturnData.ok(mapOf("settings" to settings, "style" to styles[index], "index" to index, "target" to target))
    }

    @Synchronized
    fun deleteAppData(kindName: String?, postData: String?): ReturnData {
        val kind = appDataKind(kindName) ?: return ReturnData.error("Unsupported app data kind: $kindName")
        val data = parseJson(postData) ?: return ReturnData.error("Request body is required")
        val keys = when {
            data.isJsonArray -> data.asJsonArray.mapNotNull { it.asObjectOrNull()?.string(kind.primaryKey) }.toSet()
            data.isJsonObject -> setOfNotNull(data.asJsonObject.string(kind.primaryKey))
            else -> emptySet()
        }
        if (keys.isEmpty()) return ReturnData.error("No ${kind.primaryKey} values found")
        val kept = readList(kind.kind).filterNot { it.string(kind.primaryKey) in keys }
        writeList(kind.kind, kept)
        if (kind.kind == "rssArticles") syncRssArticleMetadata(kept)
        return ReturnData.ok(readList(kind.kind))
    }

    @Synchronized
    fun getSources(kind: String): ReturnData {
        val sources = readList(kind)
        return if (sources.isEmpty()) {
            ReturnData.error("No sources saved")
        } else {
            ReturnData.ok(sources)
        }
    }

    @Synchronized
    fun getSource(kind: String, key: String, url: String?): ReturnData {
        if (url.isNullOrBlank()) return ReturnData.error("Parameter url is required")
        val item = readList(kind).firstOrNull { it.string(key) == url }
            ?: return ReturnData.error("Source not found")
        return ReturnData.ok(item)
    }

    /**
     * Executes the portable portion of a book-source search rule.  Android's
     * WebView/JavaScript, CSS and XPath rule dialects deliberately stay out of
     * this server; JSONPath and regular-expression sources remain usable on a
     * plain JVM.
     */
    fun searchBooks(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val key = payload.string("key")?.trim().orEmpty()
        if (key.isBlank()) return ReturnData.error("key is required")
        val group = payload.string("group")?.trim().orEmpty()
        return ReturnData.ok(searchBookSources(key, group))
    }

    @Synchronized
    fun getExploreSources(): ReturnData {
        val sources = readList("bookSources")
            .filter { it["enabled"]?.safeBoolean() != false && it["enabledExplore"]?.safeBoolean() != false }
            .mapNotNull { source ->
                val entries = exploreEntries(source)
                if (entries.isEmpty()) return@mapNotNull null
                mapOf(
                    "sourceUrl" to source.string("bookSourceUrl").orEmpty(),
                    "sourceName" to source.string("bookSourceName").orEmpty(),
                    "entries" to entries.map { entry -> mapOf("title" to entry.title, "url" to entry.url) },
                )
            }
        return ReturnData.ok(sources)
    }

    @Synchronized
    fun exploreBooks(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val sourceUrl = payload.string("sourceUrl")?.trim().orEmpty()
        val entryUrl = payload.string("url")?.trim().orEmpty()
        if (sourceUrl.isBlank() || entryUrl.isBlank()) return ReturnData.error("sourceUrl and url are required")
        val page = payload["page"].safeInt().coerceIn(1, 999)
        val source = readList("bookSources").firstOrNull {
            it.string("bookSourceUrl") == sourceUrl &&
                it["enabled"]?.safeBoolean() != false &&
                it["enabledExplore"]?.safeBoolean() != false
        } ?: return ReturnData.error("Explore source is unavailable")
        val entry = exploreEntries(source).firstOrNull { it.url == entryUrl }
            ?: return ReturnData.error("Explore entry is not declared by this source")
        return ReturnData.ok(exploreSource(source, entry, page))
    }

    private data class ExploreEntry(val title: String, val url: String)

    private fun exploreEntries(source: JsonObject): List<ExploreEntry> {
        val raw = source.string("exploreUrl")?.trim().orEmpty()
        if (raw.isBlank()) return emptyList()
        val items = if (isJavaScriptRule(raw)) {
            evaluateJavaScriptRule("", raw).asValues()
        } else {
            runCatching { JsonParser.parseString(raw) }.getOrNull()?.let { value ->
                when {
                    value.isJsonArray -> value.asJsonArray.map { it.toString() }
                    value.isJsonObject -> listOf(value.toString())
                    else -> emptyList()
                }
            } ?: raw.split(Regex("\\r?\\n|&&")).map(String::trim).filter(String::isNotBlank)
        }
        return items.mapNotNull { item ->
            val objectValue = runCatching { JsonParser.parseString(item).asObjectOrNull() }.getOrNull()
            val pair = if (objectValue != null) {
                val url = objectValue.string("url")?.trim().orEmpty()
                val title = objectValue.string("title")?.trim().orEmpty()
                title to url
            } else if ("::" in item) {
                item.substringBefore("::").trim() to item.substringAfter("::").trim()
            } else {
                source.string("bookSourceName").orEmpty() to item.trim()
            }
            val resolvedUrl = resolveSearchUrl(source.string("bookSourceUrl").orEmpty(), pair.second)
            if (resolvedUrl.startsWith("http://", true) || resolvedUrl.startsWith("https://", true)) {
                ExploreEntry(pair.first.ifBlank { source.string("bookSourceName").orEmpty() }, resolvedUrl)
            } else null
        }.distinctBy { it.url }
    }

    private fun exploreSource(source: JsonObject, entry: ExploreEntry, page: Int): List<JsonObject> {
        return withSourceRuleContext(source) {
            val rule = source["ruleExplore"].asObjectOrNull() ?: return@withSourceRuleContext emptyList()
            val requestUrl = entry.url
                .replace("{{page}}", page.toString())
                .replace("{page}", page.toString())
            val startedAt = System.nanoTime()
            val body = fetchSourceText(source, requestUrl) ?: return@withSourceRuleContext emptyList()
            val latency = ((System.nanoTime() - startedAt) / 1_000_000).coerceAtLeast(0)
            extractRuleValues(body, rule.string("bookList").orEmpty()).mapNotNull { item ->
            val name = extractRuleValue(item, rule.string("name")).trim()
            val bookUrl = resolveSearchUrl(requestUrl, extractRuleValue(item, rule.string("bookUrl")).trim())
            if (name.isBlank() || bookUrl.isBlank()) return@mapNotNull null
            JsonObject().apply {
                addProperty("name", name)
                addProperty("author", extractRuleValue(item, rule.string("author")).trim())
                addProperty("bookUrl", bookUrl)
                addProperty("kind", extractRuleValue(item, rule.string("kind")).trim())
                addProperty("wordCount", extractRuleValue(item, rule.string("wordCount")).trim())
                addProperty("origin", source.string("bookSourceUrl") ?: "")
                addProperty("originName", source.string("bookSourceName") ?: "")
                addProperty("type", source["bookSourceType"].safeInt())
                val cover = resolveSearchUrl(requestUrl, extractRuleValue(item, rule.string("coverUrl")).trim())
                if (cover.isNotBlank()) addProperty("coverUrl", cover)
                extractRuleValue(item, rule.string("intro")).trim().takeIf(String::isNotBlank)
                    ?.let { addProperty("intro", it) }
                extractRuleValue(item, rule.string("lastChapter")).trim().takeIf(String::isNotBlank)
                    ?.let { addProperty("latestChapterTitle", it) }
                addProperty("tocUrl", bookUrl)
                addProperty("time", System.currentTimeMillis())
                addProperty("originOrder", source["customOrder"].safeInt())
                addProperty("chapterWordCount", 0)
                addProperty("respondTime", latency)
            }
            }
        }
    }

    @Synchronized
    fun debugSource(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val kind = payload.string("kind").orEmpty().lowercase()
        val sourceUrl = payload.string("sourceUrl")?.trim().orEmpty()
        if (sourceUrl.isBlank()) return ReturnData.error("sourceUrl is required")
        return if (kind == "rss") {
            val source = readList("rssSources").firstOrNull { it.string("sourceUrl") == sourceUrl }
                ?: return ReturnData.error("RSS source not found")
            val started = System.nanoTime()
            val trace = debugRssSource(source)
            val articles = trace["articles"] as? List<*> ?: emptyList<Any>()
            ReturnData.ok(mapOf(
                "kind" to "rss",
                "sourceName" to (source.string("sourceName") ?: ""),
                "latencyMs" to ((System.nanoTime() - started) / 1_000_000).coerceAtLeast(0),
                "resultCount" to articles.size,
                "results" to articles,
                "trace" to trace.filterKeys { it != "articles" },
                "ruleDiagnostics" to sourceRuleDiagnostics(source),
                "message" to "RSS articles were parsed and cached in the RSS article collection.",
            ))
        } else {
            val source = readList("bookSources").firstOrNull { it.string("bookSourceUrl") == sourceUrl }
                ?: return ReturnData.error("Book source not found")
            val key = payload.string("key")?.trim().orEmpty()
            if (key.isBlank()) return ReturnData.error("key is required for book source debugging")
            val started = System.nanoTime()
            val trace = debugBookSource(source, key)
            val results = trace["results"] as? List<*> ?: emptyList<Any>()
            ReturnData.ok(mapOf(
                "kind" to "book",
                "sourceName" to (source.string("bookSourceName") ?: ""),
                "searchKey" to key,
                "latencyMs" to ((System.nanoTime() - started) / 1_000_000).coerceAtLeast(0),
                "resultCount" to results.size,
                "results" to results,
                "trace" to trace.filterKeys { it != "results" },
                "ruleDiagnostics" to sourceRuleDiagnostics(source),
            ))
        }
    }

    private fun debugBookSource(source: JsonObject, key: String): Map<String, Any> {
        val rule = source["ruleSearch"].asObjectOrNull()
            ?: return mapOf("error" to "ruleSearch is required", "results" to emptyList<JsonObject>())
        val requestSpec = sourceSearchRequest(source, source.string("searchUrl").orEmpty(), key)
            ?: return mapOf("error" to "searchUrl is not a portable HTTP request", "results" to emptyList<JsonObject>())
        val startedAt = System.nanoTime()
        return try {
            val response = sendSourceResponse(source, requestSpec.requestUrl, Duration.ofSeconds(20))
                ?: return mapOf("error" to "Source request failed", "results" to emptyList<JsonObject>())
            val body = response.body().decodeSourceText(response.headers(), requestSpec.requestUrl.options)
            val latency = ((System.nanoTime() - startedAt) / 1_000_000).coerceAtLeast(0)
            val accepted = response.statusCode() in 200..299 && matchesSearchCheckWord(body, rule.string("checkKeyWord"))
            val entries = if (accepted) {
                extractRuleValues(body, rule.string("bookList").orEmpty())
            } else emptyList()
            val results = if (accepted) {
                withJavaScriptRuleContext(key, requestSpec.baseUrl) {
                    extractSourceBooks(source, rule, body, requestSpec.baseUrl, latency)
                }
            } else emptyList()
            mapOf(
                "request" to mapOf("url" to requestSpec.baseUrl, "method" to sourceRequestMethod(requestSpec.requestUrl)),
                "response" to mapOf(
                    "statusCode" to response.statusCode(),
                    "bytes" to body.toByteArray(StandardCharsets.UTF_8).size,
                    "preview" to body.take(8_000),
                ),
                "listRule" to rule.string("bookList").orEmpty(),
                "checkKeyWordMatched" to accepted,
                "entryCount" to entries.size,
                "firstEntry" to entries.firstOrNull()?.take(8_000).orEmpty(),
                "fieldPreview" to entries.firstOrNull()?.let { previewRuleFields(it, rule) }.orEmpty(),
                "latencyMs" to latency,
                "results" to results,
            )
        } catch (error: Exception) {
            mapOf(
                "request" to mapOf("url" to requestSpec.baseUrl, "method" to sourceRequestMethod(requestSpec.requestUrl)),
                "error" to (error.message ?: error.javaClass.simpleName),
                "results" to emptyList<JsonObject>(),
            )
        }
    }

    private fun debugRssSource(source: JsonObject): Map<String, Any> {
        val rawUrl = source.string("sourceUrl").orEmpty()
        val requestUrl = parseSourceRequestUrl(expandSourceVariables(source, rawUrl))
            ?: return mapOf("error" to "sourceUrl is not a portable HTTP request", "articles" to emptyList<JsonObject>())
        val startedAt = System.nanoTime()
        return try {
            val response = sendSourceResponse(source, requestUrl, Duration.ofSeconds(20))
                ?: return mapOf("error" to "Source request failed", "articles" to emptyList<JsonObject>())
            val body = response.body().decodeSourceText(response.headers(), requestUrl.options)
            val articles = if (response.statusCode() in 200..299) parseRssArticles(source, body) else emptyList()
            if (articles.isNotEmpty()) cacheRssArticles(articles)
            mapOf(
                "request" to mapOf("url" to requestUrl.url, "method" to sourceRequestMethod(requestUrl)),
                "response" to mapOf("statusCode" to response.statusCode(), "bytes" to body.toByteArray(StandardCharsets.UTF_8).size, "preview" to body.take(8_000)),
                "articleRule" to source.string("ruleArticles").orEmpty(),
                "latencyMs" to ((System.nanoTime() - startedAt) / 1_000_000).coerceAtLeast(0),
                "firstArticle" to articles.firstOrNull()?.let(gson::toJson).orEmpty(),
                "articles" to articles,
            )
        } catch (error: Exception) {
            mapOf("request" to mapOf("url" to requestUrl.url), "error" to (error.message ?: error.javaClass.simpleName), "articles" to emptyList<JsonObject>())
        }
    }

    private fun previewRuleFields(entry: String, rule: JsonObject): Map<String, Map<String, String>> =
        listOf("name", "author", "bookUrl", "kind", "wordCount", "coverUrl", "intro", "lastChapter")
            .associateWith { field ->
                val configured = rule.string(field).orEmpty()
                mapOf("rule" to configured, "value" to extractRuleValue(entry, configured).take(2_000))
            }

    private fun sourceRuleDiagnostics(source: JsonObject): Map<String, Any> {
        val javaScriptFields = mutableListOf<String>()
        val bridgeFields = mutableListOf<String>()
        fun visit(path: String, value: JsonElement?) {
            when {
                value == null || value.isJsonNull -> Unit
                value.isJsonObject -> value.asJsonObject.entrySet().forEach { (key, nested) ->
                    visit(if (path.isBlank()) key else "$path.$key", nested)
                }
                value.isJsonArray -> value.asJsonArray.forEachIndexed { index, nested ->
                    visit("$path[$index]", nested)
                }
                value.isJsonPrimitive -> {
                    val text = runCatching { value.asString }.getOrDefault("")
                    if (text.contains("@js:", true)) javaScriptFields += path
                    if (Regex("""\b(?:java|source|book|chapter|webView|document)\.""", RegexOption.IGNORE_CASE)
                            .containsMatchIn(text)
                    ) {
                        bridgeFields += path
                    }
                }
            }
        }
        source.entrySet().forEach { (key, value) -> visit(key, value) }
        return mapOf(
            "javaScriptRuntime" to "restricted-graaljs",
            "javaScriptFields" to javaScriptFields.distinct(),
            "androidBridgeFields" to bridgeFields.distinct(),
            "message" to if (bridgeFields.isEmpty()) {
                "JavaScript transformation rules run in the restricted Linux server runtime."
            } else {
                "Rules using Android bridge objects are reported but cannot run in the Linux server."
            },
        )
    }

    @Synchronized
    fun refreshRssSources(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: JsonObject()
        val requested = payload["sourceUrls"].asArrayOrNull()
            ?.mapNotNull { runCatching { it.asString.trim() }.getOrNull()?.takeIf(String::isNotBlank) }
            ?.toSet()
            .orEmpty()
        val sources = readList("rssSources")
            .filter { it["enabled"]?.safeBoolean() != false }
            .filter { requested.isEmpty() || it.string("sourceUrl") in requested }
            .take(100)
        val results = sources.map { source ->
            val articles = refreshRssSource(source)
            mapOf(
                "sourceUrl" to (source.string("sourceUrl") ?: ""),
                "sourceName" to (source.string("sourceName") ?: ""),
                "articleCount" to articles.size,
                "isSuccess" to articles.isNotEmpty(),
            )
        }
        return ReturnData.ok(mapOf(
            "attempted" to results.size,
            "succeeded" to results.count { it["isSuccess"] == true },
            "articleCount" to results.sumOf { it["articleCount"] as Int },
            "results" to results,
        ))
    }

    @Synchronized
    fun updateRssArticles(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val links = payload["links"].asArrayOrNull()
            ?.mapNotNull { runCatching { it.asString.trim() }.getOrNull()?.takeIf(String::isNotBlank) }
            ?.take(2_000)
            ?.toSet()
            .orEmpty()
        if (links.isEmpty()) return ReturnData.error("links is required")
        val articles = readList("rssArticles")
        val hasGroup = payload.has("group")
        val hasRead = payload.has("isRead")
        val hasStarred = payload.has("starred")
        val group = payload.string("group")?.trim().orEmpty()
        val isRead = payload["isRead"]?.safeBoolean() == true
        val starred = payload["starred"]?.safeBoolean() == true
        val now = System.currentTimeMillis()
        var changed = 0
        articles.filter { it.string("link") in links }.forEach { article ->
            if (hasGroup) article.addProperty("group", group)
            if (hasRead) {
                article.addProperty("isRead", isRead)
                if (isRead) article.addProperty("readAt", now) else article.remove("readAt")
            }
            if (hasStarred) {
                article.addProperty("starred", starred)
                if (starred) article.addProperty("starTime", now) else article.remove("starTime")
            }
            changed++
        }
        writeList("rssArticles", articles)
        syncRssArticleMetadata(articles)
        return ReturnData.ok(mapOf("changed" to changed, "articles" to articles))
    }

    @Synchronized
    fun getRssArticleContent(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val link = payload.string("link")?.trim().orEmpty()
        if (link.isBlank()) return ReturnData.error("link is required")
        val articles = readList("rssArticles")
        val article = articles.firstOrNull { it.string("link") == link }
            ?: return ReturnData.error("RSS article not found")
        article.string("articleContent")?.takeIf(String::isNotBlank)?.let { cached ->
            return ReturnData.ok(mapOf("content" to cached, "cached" to true))
        }
        val sourceUrl = article.string("sourceUrl").orEmpty()
        val source = readList("rssSources").firstOrNull { it.string("sourceUrl") == sourceUrl }
            ?: return ReturnData.error("RSS source not found")
        if (!isAllowedRssContentUrl(source, link)) return ReturnData.error("Article URL is blocked by this RSS source")
        val body = fetchSourceText(source, link)
            ?: return ReturnData.error("Unable to load RSS article")
        val content = extractRssArticleContent(source, body)
            .ifBlank { article.string("content").orEmpty() }
            .ifBlank { article.string("description").orEmpty() }
        if (content.isBlank()) return ReturnData.error("No readable article content was found")
        article.addProperty("articleContent", content)
        article.addProperty("articleContentFetchedAt", System.currentTimeMillis())
        writeList("rssArticles", articles)
        return ReturnData.ok(mapOf("content" to content, "cached" to false))
    }

    @Synchronized
    fun requestHttpTts(postData: String?): NanoHTTPD.Response {
        val payload = parseJson(postData)?.asObjectOrNull() ?: throw IllegalArgumentException("Expected JSON object")
        val engineId = payload.string("engineId")?.trim().orEmpty()
        val text = payload.string("text")?.trim().orEmpty()
        val speed = payload["speed"].safeIntOrNull()?.coerceIn(5, 50) ?: 25
        if (engineId.isBlank()) throw IllegalArgumentException("engineId is required")
        if (text.isBlank()) throw IllegalArgumentException("text is required")
        if (text.length > 12_000) throw IllegalArgumentException("TTS text exceeds 12000 characters")
        val engine = readList("httpTTS").firstOrNull { it.string("id") == engineId }
            ?: throw IllegalArgumentException("HTTP TTS engine not found")
        if (!engine.string("loginUrl").isNullOrBlank()) {
            throw IllegalArgumentException("This HTTP TTS engine requires an Android JavaScript login flow")
        }
        val requestTemplate = expandHttpTtsTemplate(engine.string("url").orEmpty(), text, speed)
        val requestUrl = parseSourceRequestUrl(requestTemplate)
            ?: throw IllegalArgumentException("HTTP TTS URL must use HTTP or HTTPS")
        val configuredHeaders = parseHttpTtsHeaders(engine.string("header"))
        val builder = HttpRequest.newBuilder(URI.create(requestUrl.url))
            .timeout(Duration.ofSeconds(45))
            .header("User-Agent", networkUserAgent())
        configuredHeaders.forEach { (name, value) -> builder.header(name, value) }
        applyHttpTtsCookies(builder, engine)
        requestUrl.options?.get("headers")?.asObjectOrNull()?.entrySet()?.forEach { (name, value) ->
            if (name.isNotBlank()) builder.header(name, value.asString)
        }
        val method = requestUrl.options?.string("method")?.uppercase().orEmpty()
        if (method == "POST") {
            val body = requestBodyText(requestUrl.options)
            val hasContentType = configuredHeaders.keys.any { it.equals("content-type", true) } ||
                requestUrl.options?.get("headers")?.asObjectOrNull()?.keySet()?.any { it.equals("content-type", true) } == true
            if (!hasContentType) {
                val contentType = if (requestUrl.options?.get("body")?.isJsonPrimitive == true &&
                    requestUrl.options["body"].asJsonPrimitive.isString
                ) "application/x-www-form-urlencoded; charset=utf-8" else "application/json; charset=utf-8"
                builder.header("Content-Type", contentType)
            }
            builder.POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
        } else {
            builder.GET()
        }
        val response = try {
            networkClient().send(builder.build(), HttpResponse.BodyHandlers.ofByteArray())
        } catch (error: Exception) {
            throw IllegalArgumentException("HTTP TTS request failed: ${error.message ?: error.javaClass.simpleName}")
        }
        captureHttpTtsCookies(response, engine)
        if (response.statusCode() !in 200..299) {
            val message = response.body().toString(StandardCharsets.UTF_8).take(500).replace(Regex("\\s+"), " ")
            throw IllegalArgumentException("HTTP TTS returned HTTP ${response.statusCode()}${if (message.isBlank()) "" else ": $message"}")
        }
        if (response.body().size > maxHttpTtsBytes) throw IllegalArgumentException("HTTP TTS audio exceeds 20 MiB")
        val responseMime = response.headers().firstValue("content-type").orElse("").substringBefore(';').trim()
        val configuredMime = engine.string("contentType")?.substringBefore(';')?.trim().orEmpty()
        val mime = responseMime.takeIf { it.startsWith("audio/", true) || it == "application/octet-stream" }
            ?: configuredMime.takeIf { it.startsWith("audio/", true) }
            ?: "application/octet-stream"
        return NanoHTTPD.newFixedLengthResponse(
            NanoHTTPD.Response.Status.OK,
            mime,
            ByteArrayInputStream(response.body()),
            response.body().size.toLong(),
        ).apply {
            addHeader("Cache-Control", "no-store")
        }
    }

    private fun expandHttpTtsTemplate(template: String, text: String, speed: Int): String {
        if (template.isBlank()) throw IllegalArgumentException("HTTP TTS URL is empty")
        val encoded = encodeUrlComponent(text)
        val doubleEncoded = encodeUrlComponent(encoded)
        val jsonText = gson.toJson(text).removePrefix("\"").removeSuffix("\"")
        val baiduSpeed = ((speed + 5) / 10 + 4).toString()
        val aliyunSpeed = (speed * 20 - 400).toString()
        return template
            .replace("{{java.encodeURI(java.encodeURI(speakText))}}", doubleEncoded)
            .replace("{{java.encodeURI(speakText)}}", encoded)
            .replace("{{String((speakSpeed + 5) / 10 + 4)}}", baiduSpeed)
            .replace("{{String((speakSpeed) * 20 - 400)}}", aliyunSpeed)
            .replace("{{speakSpeed}}", speed.toString())
            .replace("{{speakText}}", jsonText)
    }

    private fun requestBodyText(options: JsonObject?): String {
        val body = options?.get("body") ?: return ""
        return if (body.isJsonPrimitive && body.asJsonPrimitive.isString) body.asString else gson.toJson(body)
    }

    private fun encodeUrlComponent(value: String): String =
        URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20")

    private fun parseHttpTtsHeaders(rawHeaders: String?): Map<String, String> {
        if (rawHeaders.isNullOrBlank()) return emptyMap()
        val parsed = runCatching { JsonParser.parseString(rawHeaders).asObjectOrNull() }.getOrNull()
            ?: throw IllegalArgumentException("HTTP TTS headers must be a JSON object")
        return parsed.entrySet().associate { (name, value) ->
            if (name.isBlank()) throw IllegalArgumentException("HTTP TTS header name is empty")
            name to value.asString
        }
    }

    private fun applyHttpTtsCookies(builder: HttpRequest.Builder, engine: JsonObject) {
        if (engine["enabledCookieJar"]?.safeBoolean() != true) return
        val sourceUrl = engine.string("url").orEmpty().substringBefore(",{")
        val sourceHost = runCatching { URI.create(sourceUrl).host.orEmpty() }.getOrDefault("")
        val cookie = readList("cookies").firstOrNull { item ->
            val url = item.string("url").orEmpty()
            url == "httpTts:${engine.string("id")}" || url == sourceUrl ||
                runCatching { URI.create(url).host.orEmpty() == sourceHost }.getOrDefault(false)
        }?.string("cookie").orEmpty()
        if (cookie.isNotBlank()) builder.header("Cookie", cookie.take(8192))
    }

    private fun captureHttpTtsCookies(response: HttpResponse<*>, engine: JsonObject) {
        if (engine["enabledCookieJar"]?.safeBoolean() != true) return
        val updates = response.headers().allValues("set-cookie")
            .map { it.substringBefore(';').trim() }
            .filter { '=' in it }
        if (updates.isEmpty()) return
        val key = "httpTts:${engine.string("id")}"
        val cookies = readList("cookies")
        val record = cookies.firstOrNull { it.string("url") == key } ?: JsonObject().also(cookies::add)
        val values = record.string("cookie").orEmpty().split(';').mapNotNull { pair ->
            val index = pair.indexOf('=')
            pair.takeIf { index > 0 }?.let { it.substring(0, index).trim() to it.substring(index + 1).trim() }
        }.toMap(LinkedHashMap())
        updates.forEach { pair ->
            val index = pair.indexOf('=')
            values[pair.substring(0, index).trim()] = pair.substring(index + 1).trim()
        }
        record.addProperty("url", key)
        record.addProperty("sourceName", engine.string("name") ?: "HTTP TTS")
        record.addProperty("cookie", values.entries.joinToString("; ") { "${it.key}=${it.value}" }.take(8192))
        record.addProperty("lastUseTime", System.currentTimeMillis())
        writeList("cookies", cookies)
    }

    @Synchronized
    fun lookupDictionary(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val text = payload.string("text")?.trim().orEmpty()
        if (text.isBlank()) return ReturnData.error("text is required")
        if (text.length > 256) return ReturnData.error("Dictionary lookup text exceeds 256 characters")
        val requestedNames = payload["names"].asArrayOrNull()
            ?.mapNotNull { runCatching { it.asString.trim() }.getOrNull()?.takeIf(String::isNotBlank) }
            ?.toSet()
            .orEmpty()
        val rules = readList("dictRules")
            .asSequence()
            .filter { it["enabled"]?.safeBoolean() != false }
            .filter { requestedNames.isEmpty() || it.string("name") in requestedNames }
            .sortedBy { it["sortNumber"].safeInt() }
            .take(12)
            .toList()
        if (rules.isEmpty()) return ReturnData.error("No enabled dictionary rules")
        return ReturnData.ok(rules.map { rule -> lookupDictionaryRule(rule, text) })
    }

    private fun lookupDictionaryRule(rule: JsonObject, text: String): Map<String, Any> {
        val name = rule.string("name").orEmpty().ifBlank { "Dictionary" }
        return try {
            val url = expandDictionaryUrl(rule.string("urlRule").orEmpty(), text)
            val request = parseSourceRequestUrl(url) ?: throw IllegalArgumentException("urlRule must use HTTP or HTTPS")
            val builder = HttpRequest.newBuilder(URI.create(request.url))
                .timeout(Duration.ofSeconds(20))
                .header("User-Agent", networkUserAgent())
            parseHttpTtsHeaders(rule.string("header")).forEach { (header, value) -> builder.header(header, value) }
            val response = networkClient().send(builder.GET().build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
            if (response.statusCode() !in 200..299) throw IllegalArgumentException("HTTP ${response.statusCode()}")
            val extracted = extractDictionaryContent(response.body(), rule.string("showRule"))
            mapOf(
                "name" to name,
                "content" to extracted.first,
                "url" to request.url,
                "degraded" to extracted.second.orEmpty(),
                "isSuccess" to true,
            )
        } catch (error: Exception) {
            mapOf(
                "name" to name,
                "content" to "",
                "url" to "",
                "degraded" to "",
                "isSuccess" to false,
                "errorMsg" to (error.message ?: error.javaClass.simpleName),
            )
        }
    }

    private fun expandDictionaryUrl(template: String, text: String): String {
        if (template.isBlank()) throw IllegalArgumentException("Dictionary urlRule is empty")
        val key = encodeUrlComponent(text)
        return template
            .replace("{{key}}", key)
            .replace("{{searchKey}}", key)
            .replace("{key}", key)
            .replace("%s", key)
    }

    private fun extractDictionaryContent(body: String, rule: String?): Pair<String, String?> {
        if (rule.isNullOrBlank()) return readableDictionaryText(body) to null
        val legacyTagRule = rule.trim().takeIf { it.startsWith("tag.", true) }
        val extracted = if (legacyTagRule != null) {
            val selector = legacyTagRule.substringAfter("tag.").substringBefore('@').trim()
            Jsoup.parse(body).selectFirst(selector)?.text().orEmpty()
        } else {
            extractRuleValue(body, rule)
        }
        return readableDictionaryText(extracted) to null
    }

    private fun readableDictionaryText(value: String): String =
        Jsoup.parseBodyFragment(value).text().replace(Regex("\\s+"), " ").trim().take(50_000)

    @Synchronized
    fun startBookDownload(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val bookUrl = payload.string("bookUrl")?.trim().orEmpty()
        if (bookUrl.isBlank()) return ReturnData.error("bookUrl is required")
        val book = readList("books").firstOrNull { it.string("bookUrl") == bookUrl }
            ?: return ReturnData.error("Book not found")
        val active = readList("downloadTasks").firstOrNull {
            it.string("bookUrl") == bookUrl && it.string("kind") == "book" && it.string("status") in setOf("queued", "running")
        }
        if (active != null) return ReturnData.ok(active)
        val task = JsonObject().apply {
            addProperty("id", UUID.randomUUID().toString())
            addProperty("kind", "book")
            addProperty("bookUrl", bookUrl)
            addProperty("name", book.string("name") ?: "Book")
            addProperty("status", "queued")
            addProperty("progress", 0)
            addProperty("updatedAt", System.currentTimeMillis())
        }
        val tasks = readList("downloadTasks")
        tasks.add(task)
        writeList("downloadTasks", tasks)
        val taskId = task.string("id").orEmpty()
        downloadExecutor.submit { runBookDownload(taskId, bookUrl) }
        return ReturnData.ok(task)
    }

    @Synchronized
    fun cancelBookDownload(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val taskId = payload.string("id")?.trim().orEmpty()
        if (taskId.isBlank()) return ReturnData.error("id is required")
        val tasks = readList("downloadTasks")
        val task = tasks.firstOrNull { it.string("id") == taskId } ?: return ReturnData.error("Download task not found")
        if (task.string("status") !in setOf("queued", "running")) return ReturnData.ok(task)
        task.addProperty("status", "cancelled")
        task.addProperty("updatedAt", System.currentTimeMillis())
        writeList("downloadTasks", tasks)
        return ReturnData.ok(task)
    }

    @Synchronized
    fun retryBookDownload(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val taskId = payload.string("id")?.trim().orEmpty()
        if (taskId.isBlank()) return ReturnData.error("id is required")
        val tasks = readList("downloadTasks")
        val task = tasks.firstOrNull { it.string("id") == taskId }
            ?: return ReturnData.error("Download task not found")
        val bookUrl = task.string("bookUrl")?.trim().orEmpty()
        if (bookUrl.isBlank()) return ReturnData.error("Download task has no book URL")
        if (task.string("status") in setOf("queued", "running")) return ReturnData.ok(task)
        task.addProperty("status", "queued")
        task.addProperty("progress", 0)
        task.remove("error")
        task.remove("path")
        task.addProperty("updatedAt", System.currentTimeMillis())
        writeList("downloadTasks", tasks)
        downloadExecutor.submit { runBookDownload(taskId, bookUrl) }
        return ReturnData.ok(task)
    }

    private fun runBookDownload(taskId: String, bookUrl: String) {
        updateDownloadTask(taskId) { task ->
            if (task.string("status") != "cancelled") {
                task.addProperty("status", "running")
                task.addProperty("progress", 1)
                task.remove("error")
            }
        }
        try {
            if (downloadTaskCancelled(taskId)) return
            val book = synchronized(this) { readList("books").firstOrNull { it.string("bookUrl") == bookUrl } }
                ?: throw IllegalStateException("Book not found")
            val source = sourceForBook(book)
            if (source != null && source["bookSourceType"].safeInt() == 3) {
                downloadRemoteFileBook(taskId, book, source)
                return
            }
            val toc = getChapterList(bookUrl)
            if (!toc.isSuccess) throw IllegalStateException(toc.errorMsg)
            val chapters = synchronized(this) { readChapterList(bookUrl).sortedBy { it["index"].safeInt() } }
            if (chapters.isEmpty()) throw IllegalStateException("No chapters available for download")
            if (chapters.size > 3_000) throw IllegalStateException("Book exceeds the 3000 chapter offline download limit")
            val content = buildString {
                append(book.string("name") ?: "Book").append('\n')
                book.string("author")?.takeIf(String::isNotBlank)?.let { append(it).append('\n') }
                append('\n')
                chapters.forEachIndexed { position, chapter ->
                    if (downloadTaskCancelled(taskId)) return
                    val index = chapter["index"].safeInt()
                    val result = getBookContent(bookUrl, index)
                    if (!result.isSuccess) throw IllegalStateException("Chapter ${position + 1}: ${result.errorMsg}")
                    val body = result.data as? String ?: throw IllegalStateException("Chapter ${position + 1}: invalid content")
                    append(chapter.string("title") ?: "Chapter ${position + 1}").append("\n\n")
                    append(body.trim()).append("\n\n")
                    updateDownloadTask(taskId) { task ->
                        task.addProperty("progress", ((position + 1) * 100 / chapters.size).coerceIn(1, 99))
                        task.addProperty("chapterCount", chapters.size)
                    }
                }
            }
            if (downloadTaskCancelled(taskId)) return
            val baseName = (book.string("name") ?: "book")
                .replace(Regex("[\\\\/:*?\"<>|]"), "_").trim().ifEmpty { "book" }
            val target = downloadsDir.resolve("$baseName-${System.currentTimeMillis()}.txt").normalize()
            if (!target.startsWith(downloadsDir)) throw IllegalStateException("Invalid download target")
            writeStringAtomic(target, content)
            updateDownloadTask(taskId) { task ->
                task.addProperty("status", "done")
                task.addProperty("progress", 100)
                task.addProperty("path", target.toAbsolutePath().normalize().toString())
                task.addProperty("size", Files.size(target))
            }
        } catch (error: Exception) {
            updateDownloadTask(taskId) { task ->
                task.addProperty("status", "failed")
                task.addProperty("error", error.message ?: error.javaClass.simpleName)
            }
        }
    }

    private fun downloadRemoteFileBook(taskId: String, book: JsonObject, source: JsonObject) {
        val urls = loadRemoteFileUrls(book, source)
        if (urls.isEmpty()) throw IllegalStateException("downloadUrls did not produce an HTTP download link")
        if (downloadTaskCancelled(taskId)) return
        val response = fetchSourceBytes(source, urls.first())
            ?: throw IllegalStateException("Unable to download file from source")
        if (response.bytes.isEmpty()) throw IllegalStateException("Downloaded file is empty")
        val baseName = (book.string("name") ?: "book")
            .replace(Regex("[\\\\/:*?\"<>|]"), "_").trim().ifEmpty { "book" }
        val extension = response.fileName.substringAfterLast('.', "").takeIf { it.matches(Regex("[A-Za-z0-9]{1,12}")) }
            ?.let { ".$it" }
            ?: extensionFromContentType(response.contentType)
        val target = downloadsDir.resolve("$baseName-${System.currentTimeMillis()}$extension").normalize()
        if (!target.startsWith(downloadsDir) || response.bytes.size > MAX_LOCAL_BOOK_BYTES) {
            throw IllegalStateException("Downloaded file is too large or has an invalid target")
        }
        Files.write(target, response.bytes, StandardOpenOption.CREATE_NEW)
        updateDownloadTask(taskId) { task ->
            task.addProperty("status", "done")
            task.addProperty("progress", 100)
            task.addProperty("path", target.toAbsolutePath().normalize().toString())
            task.addProperty("size", response.bytes.size)
            task.addProperty("fileName", target.fileName.toString())
            task.addProperty("sourceUrl", urls.first())
        }
    }

    private fun loadRemoteFileUrls(book: JsonObject, source: JsonObject): List<String> {
        return withSourceRuleContext(source) {
            val rule = source["ruleBookInfo"].asObjectOrNull() ?: return@withSourceRuleContext emptyList()
            val downloadRule = rule.string("downloadUrls") ?: return@withSourceRuleContext emptyList()
            val detailUrl = book.string("bookUrl").orEmpty()
            val detail = fetchSourceText(source, detailUrl) ?: return@withSourceRuleContext emptyList()
            val scope = extractBookInfoScope(detail, rule.string("init"))
            extractRuleValues(scope, downloadRule)
                .map { resolveSearchUrl(detailUrl, it.trim()) }
                .filter { it.startsWith("http://", true) || it.startsWith("https://", true) }
                .distinct()
                .take(8)
        }
    }

    private data class SourceBytesResponse(
        val bytes: ByteArray,
        val contentType: String,
        val fileName: String,
    )

    private fun fetchSourceBytes(source: JsonObject, rawUrl: String): SourceBytesResponse? {
        val requestUrl = parseSourceRequestUrl(expandSourceVariables(source, rawUrl)) ?: return null
        return try {
            val response = sendSourceResponse(source, requestUrl, Duration.ofSeconds(60)) ?: return null
            if (response.statusCode() !in 200..299 || response.body().size > MAX_LOCAL_BOOK_BYTES) return null
            val disposition = response.headers().firstValue("content-disposition").orElse("")
            val fileName = Regex("""filename\\*?=(?:UTF-8''|[\"])?([^;\"]+)""", RegexOption.IGNORE_CASE)
                .find(disposition)?.groupValues?.getOrNull(1)?.let { URLDecoder.decode(it.trim(), StandardCharsets.UTF_8) }
                ?: URI.create(requestUrl.url).path.substringAfterLast('/').ifBlank { "book" }
            SourceBytesResponse(
                response.body(),
                response.headers().firstValue("content-type").orElse("").substringBefore(';'),
                fileName,
            )
        } catch (_: Exception) {
            null
        }
    }

    private fun extensionFromContentType(contentType: String): String = when (contentType.lowercase()) {
        "application/epub+zip" -> ".epub"
        "application/pdf" -> ".pdf"
        "application/zip", "application/x-zip-compressed" -> ".zip"
        "text/plain" -> ".txt"
        else -> ""
    }

    private fun updateDownloadTask(taskId: String, update: (JsonObject) -> Unit) {
        synchronized(this) {
            val tasks = readList("downloadTasks")
            val task = tasks.firstOrNull { it.string("id") == taskId } ?: return
            update(task)
            task.addProperty("updatedAt", System.currentTimeMillis())
            writeList("downloadTasks", tasks)
        }
    }

    private fun downloadTaskCancelled(taskId: String): Boolean = synchronized(this) {
        readList("downloadTasks").firstOrNull { it.string("id") == taskId }?.string("status") == "cancelled"
    }

    @Synchronized
    fun downloadTaskFile(taskId: String?): NanoHTTPD.Response {
        if (taskId.isNullOrBlank()) throw IllegalArgumentException("id is required")
        val task = readList("downloadTasks").firstOrNull { it.string("id") == taskId }
            ?: throw IllegalArgumentException("Download task not found")
        if (task.string("status") != "done") throw IllegalArgumentException("Download task is not complete")
        val target = task.string("path")?.let(Paths::get)?.toAbsolutePath()?.normalize()
            ?: throw IllegalArgumentException("Download task has no file")
        if (!target.startsWith(downloadsDir.toAbsolutePath().normalize()) || !target.isRegularFile()) {
            throw IllegalArgumentException("Download task file is unavailable")
        }
        val fileName = target.fileName.toString().replace('"', '_').replace('\r', '_').replace('\n', '_')
        return NanoHTTPD.newFixedLengthResponse(
            NanoHTTPD.Response.Status.OK,
            "text/plain; charset=utf-8",
            Files.newInputStream(target),
            Files.size(target),
        ).apply {
            addHeader("Content-Disposition", "attachment; filename=\"$fileName\"")
            addHeader("Cache-Control", "no-store")
        }
    }

    private fun searchBookSources(key: String, group: String = ""): List<JsonObject> {
        val sources = readList("bookSources")
            .asSequence()
            .filter { it["enabled"]?.safeBoolean() != false }
            .filter { group.isBlank() || it.string("bookSourceGroup")?.split(',')?.any { value -> value.trim() == group } == true }
            .filter { !it.string("searchUrl").isNullOrBlank() && it["ruleSearch"].asObjectOrNull() != null }
            .take(80)
            .toList()
        if (sources.isEmpty()) return emptyList()

        val workers = networkThreadCount().coerceIn(1, minOf(12, sources.size))
        val executor = Executors.newFixedThreadPool(workers)
        return try {
            executor.invokeAll(sources.map { source ->
                Callable { searchSource(source, key) }
            }).flatMap { future -> runCatching { future.get() }.getOrDefault(emptyList()) }
        } finally {
            executor.shutdown()
        }
    }

    private fun refreshRssSource(source: JsonObject): List<JsonObject> {
        return withSourceRuleContext(source) {
            val sourceUrl = source.string("sourceUrl").orEmpty()
            val body = fetchSourceText(source, sourceUrl) ?: return@withSourceRuleContext emptyList()
            val articles = parseRssArticles(source, body)
            if (articles.isNotEmpty()) cacheRssArticles(articles)
            articles
        }
    }

    private fun cacheRssArticles(articles: List<JsonObject>) {
        synchronized(this) {
            val cached = readList("rssArticles")
            for (article in articles) {
                val link = article.string("link").orEmpty()
                val old = cached.firstOrNull { it.string("link") == link }
                if (old != null) {
                    for (key in listOf("isRead", "starred", "group", "readAt", "starTime")) {
                        old[key]?.let { article.add(key, it.deepCopy()) }
                    }
                    val index = cached.indexOf(old)
                    cached[index] = article
                } else {
                    cached.add(article)
                }
            }
            writeList("rssArticles", cached)
            syncRssArticleMetadata(cached)
        }
    }

    private fun syncRssArticleMetadata(articles: List<JsonObject>) {
        val readRecords = articles.filter { it["isRead"]?.safeBoolean() == true }.map { article ->
            JsonObject().apply {
                val link = article.string("link").orEmpty()
                addProperty("record", link)
                addProperty("link", link)
                addProperty("title", article.string("title") ?: "")
                addProperty("sourceName", article.string("sourceName") ?: "")
                addProperty("progress", "read")
                addProperty("readTime", article["readAt"].safeLong().takeIf { it > 0 } ?: System.currentTimeMillis())
            }
        }
        val stars = articles.filter { it["starred"]?.safeBoolean() == true }.map { article ->
            JsonObject().apply {
                val link = article.string("link").orEmpty()
                addProperty("link", link)
                addProperty("title", article.string("title") ?: "")
                addProperty("sourceName", article.string("sourceName") ?: "")
                addProperty("summary", article.string("description") ?: article.string("content") ?: "")
                addProperty("starTime", article["starTime"].safeLong().takeIf { it > 0 } ?: System.currentTimeMillis())
            }
        }
        writeList("rssReadRecords", readRecords)
        writeList("rssStars", stars)
    }

    private fun parseRssArticles(source: JsonObject, body: String): List<JsonObject> {
        val sourceUrl = source.string("sourceUrl").orEmpty()
        val articleRule = source.string("ruleArticles").orEmpty()
        val entries = if (articleRule.isBlank()) {
            runCatching { Jsoup.parse(body, "", Parser.xmlParser()).select("item, entry").map { it.outerHtml() } }
                .getOrDefault(emptyList())
        } else {
            extractRssRuleValues(body, articleRule)
        }
        return entries.mapNotNull { entry ->
            val title = rssRuleValue(entry, source.string("ruleTitle"), "title@text").trim()
            val rawLink = rssRuleValue(entry, source.string("ruleLink"), "link@href")
                .ifBlank { rssRuleValue(entry, source.string("ruleLink"), "link@text") }
                .trim()
            val link = resolveSearchUrl(sourceUrl, rawLink)
            if (title.isBlank() || link.isBlank()) return@mapNotNull null
            JsonObject().apply {
                addProperty("link", link)
                addProperty("title", title)
                addProperty("sourceUrl", sourceUrl)
                addProperty("sourceName", source.string("sourceName") ?: "")
                rssRuleValue(entry, source.string("rulePubDate"), "pubDate@text").trim()
                    .takeIf(String::isNotBlank)?.let { addProperty("pubDate", it) }
                rssRuleValue(entry, source.string("ruleDescription"), "description@text").trim()
                    .takeIf(String::isNotBlank)?.let { addProperty("description", it) }
                rssRuleValue(entry, source.string("ruleImage"), "enclosure@url").trim()
                    .takeIf(String::isNotBlank)?.let { addProperty("image", resolveSearchUrl(sourceUrl, it)) }
                rssRuleValue(entry, null, "content@text").trim()
                    .takeIf(String::isNotBlank)?.let { addProperty("content", it) }
                addProperty("isRead", false)
                addProperty("starred", false)
                addProperty("refreshedAt", System.currentTimeMillis())
            }
        }
    }

    private fun rssRuleValue(entry: String, configured: String?, fallback: String): String {
        if (!configured.isNullOrBlank()) return extractRssRuleValue(entry, configured)
        val (selector, attribute) = cssRuleParts(fallback)
        return runCatching {
            val element = Jsoup.parse(entry, "", Parser.xmlParser()).selectFirst(selector) ?: return ""
            when (attribute?.lowercase()) {
                null, "text" -> element.text()
                "html" -> element.html()
                else -> element.attr(attribute)
            }
        }.getOrDefault("")
    }

    private fun extractRssRuleValues(input: String, rule: String): List<String> {
        if (rule.isBlank()) return emptyList()
        if (isJavaScriptRule(rule)) return evaluateJavaScriptRule(input, rule).asValues()
        splitJavaScriptTransform(rule)?.let { (baseRule, transform) ->
            return extractRssRuleValues(input, baseRule)
                .flatMap { value -> evaluateJavaScriptRule(value, transform).asValues() }
        }
        if (rule.trimStart().startsWith("$") || !isCssRule(rule) && !isXpathRule(rule)) return extractRuleValues(input, rule)
        return runCatching {
            if (isXpathRule(rule)) {
                Jsoup.parse(input, "", Parser.xmlParser()).selectXpath(xpathRuleParts(rule).first).map { it.outerHtml() }
            } else {
                Jsoup.parse(input, "", Parser.xmlParser()).select(cssSelector(rule)).map { it.outerHtml() }
            }
        }.getOrDefault(emptyList())
    }

    private fun extractRssRuleValue(input: String, rule: String): String {
        if (rule.isBlank()) return ""
        if (isJavaScriptRule(rule)) return evaluateJavaScriptRule(input, rule).asValue()
        splitJavaScriptTransform(rule)?.let { (baseRule, transform) ->
            return evaluateJavaScriptRule(extractRssRuleValue(input, baseRule), transform).asValue()
        }
        if (rule.trimStart().startsWith("$") || !isCssRule(rule) && !isXpathRule(rule)) return extractRuleValue(input, rule)
        return runCatching {
            val (selector, attribute) = if (isXpathRule(rule)) xpathRuleParts(rule) else cssRuleParts(rule)
            val document = Jsoup.parse(input, "", Parser.xmlParser())
            val element = (if (isXpathRule(rule)) document.selectXpath(selector).firstOrNull() else document.selectFirst(selector))
                ?: return ""
            when (attribute?.lowercase()) {
                null, "text" -> element.text()
                "owntext" -> element.ownText()
                "html" -> element.html()
                "outerhtml" -> element.outerHtml()
                else -> element.attr(attribute)
            }
        }.getOrDefault("")
    }

    private fun extractRssArticleContent(source: JsonObject, body: String): String {
        val configuredRule = source.string("ruleContent")
        if (!configuredRule.isNullOrBlank()) return extractRuleValue(body, configuredRule).trim()
        val document = Jsoup.parse(body)
        return document.selectFirst("article, main, .article, .post, .entry, .content, #content")
            ?.text()
            ?.trim()
            .orEmpty()
            .ifBlank { document.body().text().trim() }
    }

    private fun isAllowedRssContentUrl(source: JsonObject, rawUrl: String): Boolean {
        val url = rawUrl.substringBefore(",{").trim()
        val host = runCatching { URI.create(url).host?.lowercase().orEmpty() }.getOrDefault("")
        if (host.isBlank()) return false
        val isMatch = { list: String? ->
            list.orEmpty().split(',').map(String::trim).filter(String::isNotBlank).any { entry ->
                val normalized = entry.substringBefore("/").substringAfter("://", entry).lowercase()
                normalized == "*" || wildcardMatches(host, normalized)
            }
        }
        if (isMatch(source.string("contentBlacklist"))) return false
        val whitelist = source.string("contentWhitelist")?.trim().orEmpty()
        return whitelist.isBlank() || isMatch(whitelist)
    }

    private fun wildcardMatches(value: String, pattern: String): Boolean {
        val expression = pattern.split('*').joinToString(".*") { Regex.escape(it) }
        return Regex("^$expression$").matches(value)
    }

    @Synchronized
    fun findBookSourceCandidates(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val bookUrl = payload.string("bookUrl") ?: return ReturnData.error("bookUrl is required")
        val book = readList("books").firstOrNull { it.string("bookUrl") == bookUrl }
            ?: return ReturnData.error("Book not found")
        val candidates = searchBookSources(book.string("name").orEmpty())
            .filter { sameBookTitle(it.string("name"), book.string("name")) }
            .filter { it.string("origin") != book.string("origin") }
            .filter { !changeSourceCheckAuthor() || compatibleAuthors(book.string("author"), it.string("author")) }
            .distinctBy { "${it.string("origin")}\u0000${it.string("bookUrl")}" }
        if (loadSourceWordCount()) candidates.forEach(::loadCandidateWordCount)
        return ReturnData.ok(candidates)
    }

    @Synchronized
    fun changeBookSource(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val oldBookUrl = payload.string("bookUrl") ?: return ReturnData.error("bookUrl is required")
        val candidate = payload["candidate"].asObjectOrNull() ?: return ReturnData.error("candidate is required")
        val books = readList("books")
        val original = books.firstOrNull { it.string("bookUrl") == oldBookUrl }
            ?: return ReturnData.error("Book not found")
        val newBookUrl = candidate.string("bookUrl")?.trim().orEmpty()
        val newOrigin = candidate.string("origin")?.trim().orEmpty()
        if (!newBookUrl.startsWith("http://", true) && !newBookUrl.startsWith("https://", true)) {
            return ReturnData.error("candidate bookUrl must use HTTP or HTTPS")
        }
        val source = readList("bookSources").firstOrNull {
            it.string("bookSourceUrl") == newOrigin && it["enabled"]?.safeBoolean() != false
        } ?: return ReturnData.error("Candidate source is not enabled or no longer exists")
        if (!sameBookTitle(original.string("name"), candidate.string("name"))) {
            return ReturnData.error("Candidate title does not match the bookshelf book")
        }
        if (changeSourceCheckAuthor() && !compatibleAuthors(original.string("author"), candidate.string("author"))) {
            return ReturnData.error("Candidate author does not match the bookshelf book")
        }
        if (newBookUrl != oldBookUrl && books.any { it.string("bookUrl") == newBookUrl }) {
            return ReturnData.error("The candidate book is already on the bookshelf")
        }
        if (loadSourceDetails()) enrichCandidateBookInfo(candidate, source)

        val switched = original.deepCopy()
        for (key in listOf("bookUrl", "tocUrl", "origin", "originName", "type", "originOrder")) {
            candidate[key]?.let { switched.add(key, it.deepCopy()) }
        }
        if (loadSourceDetails()) {
            for (key in listOf("coverUrl", "intro", "kind", "wordCount", "latestChapterTitle")) {
                candidate[key]?.let { switched.add(key, it.deepCopy()) }
            }
        }
        switched.addProperty("lastCheckTime", System.currentTimeMillis())
        switched.withBookDefaults()

        if (preloadSourceToc()) {
            val chapters = loadRemoteChapters(switched, source)
            if (chapters.isNotEmpty()) {
                writeChapterList(newBookUrl, chapters)
                switched.addProperty("totalChapterNum", chapters.size)
                switched.addProperty("latestChapterTitle", chapters.last().string("title") ?: "")
            }
        }
        val index = books.indexOfFirst { it.string("bookUrl") == oldBookUrl }
        books[index] = switched
        writeList("books", books)
        return ReturnData.ok(switched)
    }

    private fun enrichCandidateBookInfo(candidate: JsonObject, source: JsonObject) {
        applyBookInfoRules(candidate, source, allowRename = true)
    }

    private fun applyBookInfoRules(book: JsonObject, source: JsonObject, allowRename: Boolean): Boolean =
        withSourceRuleContext(source) {
            val rule = source["ruleBookInfo"].asObjectOrNull() ?: return@withSourceRuleContext false
            val detailUrl = book.string("bookUrl").orEmpty()
            val detail = fetchSourceText(source, detailUrl) ?: return@withSourceRuleContext false
            val detailScope = extractBookInfoScope(detail, rule.string("init"))
            if (allowRename) {
                extractRuleValue(detailScope, rule.string("name")).trim()
                    .takeIf(String::isNotBlank)?.let { book.addProperty("name", it) }
                extractRuleValue(detailScope, rule.string("author")).trim()
                    .takeIf(String::isNotBlank)?.let { book.addProperty("author", it) }
            }
            for (field in listOf("kind", "wordCount", "intro")) {
                extractRuleValue(detailScope, rule.string(field)).trim()
                    .takeIf(String::isNotBlank)?.let { book.addProperty(field, it) }
            }
            extractRuleValue(detailScope, rule.string("coverUrl")).trim()
                .takeIf(String::isNotBlank)
                ?.let { book.addProperty("coverUrl", resolveSearchUrl(detailUrl, it)) }
            extractRuleValue(detailScope, rule.string("tocUrl")).trim()
                .takeIf(String::isNotBlank)
                ?.let { book.addProperty("tocUrl", resolveSearchUrl(detailUrl, it)) }
            extractRuleValue(detailScope, rule.string("lastChapter")).trim()
                .takeIf(String::isNotBlank)?.let { book.addProperty("latestChapterTitle", it) }
            extractRuleValue(detailScope, rule.string("updateTime")).trim()
                .takeIf(String::isNotBlank)?.let { book.addProperty("latestChapterTimeText", it) }
            true
        }

    private fun extractBookInfoScope(response: String, initRule: String?): String {
        if (initRule.isNullOrBlank()) return response
        return extractRuleValues(response, initRule).firstOrNull()
            ?.takeIf(String::isNotBlank)
            ?: extractRuleValue(response, initRule).takeIf(String::isNotBlank)
            ?: response
    }

    @Synchronized
    fun autoChangeBookSource(postData: String?): ReturnData {
        if (!autoChangeSourceEnabled()) return ReturnData.error("Automatic source change is disabled")
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val bookUrl = payload.string("bookUrl") ?: return ReturnData.error("bookUrl is required")
        val book = readList("books").firstOrNull { it.string("bookUrl") == bookUrl }
            ?: return ReturnData.error("Book not found")
        val candidate = findAlternativeSourceCandidate(book)
            ?: return ReturnData.error("No matching alternative source found")
        val request = JsonObject().apply {
            addProperty("bookUrl", bookUrl)
            add("candidate", candidate.deepCopy())
        }
        return changeBookSource(gson.toJson(request))
    }

    @Synchronized
    fun batchChangeBookSources(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: JsonObject()
        val requested = payload["bookUrls"].asArrayOrNull()
            ?.mapNotNull { element -> runCatching { element.asString.trim() }.getOrNull()?.takeIf(String::isNotBlank) }
            ?.distinct()
            ?.take(200)
            ?: readList("books").mapNotNull { it.string("bookUrl") }.take(200)
        if (requested.isEmpty()) return ReturnData.error("No bookshelf books to change")
        val delay = batchChangeSourceDelay()
        val results = mutableListOf<Map<String, Any?>>()
        for ((index, bookUrl) in requested.withIndex()) {
            val book = readList("books").firstOrNull { it.string("bookUrl") == bookUrl }
            val candidate = book?.let(::findAlternativeSourceCandidate)
            val outcome = if (book == null) {
                ReturnData.error("Book not found")
            } else if (candidate == null) {
                ReturnData.error("No matching alternative source found")
            } else {
                val request = JsonObject().apply {
                    addProperty("bookUrl", bookUrl)
                    add("candidate", candidate.deepCopy())
                }
                changeBookSource(gson.toJson(request))
            }
            results.add(
                mapOf(
                    "bookUrl" to bookUrl,
                    "isSuccess" to outcome.isSuccess,
                    "errorMsg" to outcome.errorMsg,
                    "data" to outcome.data,
                )
            )
            if (delay > 0 && index < requested.lastIndex) {
                try {
                    Thread.sleep(delay.toLong())
                } catch (_: InterruptedException) {
                    Thread.currentThread().interrupt()
                    break
                }
            }
        }
        return ReturnData.ok(
            mapOf(
                "attempted" to results.size,
                "succeeded" to results.count { it["isSuccess"] == true },
                "failed" to results.count { it["isSuccess"] != true },
                "delayMillis" to delay,
                "results" to results,
            )
        )
    }

    private fun findAlternativeSourceCandidate(book: JsonObject): JsonObject? {
        val candidate =
        searchBookSources(book.string("name").orEmpty())
            .firstOrNull { result ->
                sameBookTitle(result.string("name"), book.string("name")) &&
                    result.string("origin") != book.string("origin") &&
                    (!changeSourceCheckAuthor() || compatibleAuthors(book.string("author"), result.string("author")))
            }
        if (candidate != null && loadSourceWordCount()) loadCandidateWordCount(candidate)
        return candidate
    }

    private fun loadCandidateWordCount(candidate: JsonObject) {
        val source = readList("bookSources").firstOrNull {
            it.string("bookSourceUrl") == candidate.string("origin") && it["enabled"]?.safeBoolean() != false
        } ?: return
        val chapters = loadRemoteChapters(candidate, source)
        val latest = chapters.lastOrNull() ?: return
        val content = loadRemoteChapterContent(latest, source) ?: return
        val count = content.count { !it.isWhitespace() }
        if (count <= 0) return
        candidate.addProperty("chapterWordCount", count)
        candidate.addProperty("chapterWordCountText", "$count 字")
        if (candidate.string("latestChapterTitle").isNullOrBlank()) {
            candidate.addProperty("latestChapterTitle", latest.string("title") ?: "")
        }
    }

    private fun sameBookTitle(left: String?, right: String?): Boolean =
        left?.trim()?.equals(right?.trim(), ignoreCase = true) == true

    private fun compatibleAuthors(left: String?, right: String?): Boolean {
        val first = left?.trim().orEmpty()
        val second = right?.trim().orEmpty()
        return first.isNotBlank() && second.isNotBlank() &&
            (first.equals(second, ignoreCase = true) || first.contains(second, true) || second.contains(first, true))
    }

    private fun changeSourceCheckAuthor(): Boolean = readAppSettings()["network"]
        .asObjectOrNull()?.get("changeSourceCheckAuthor")?.safeBoolean() == true

    private fun loadSourceDetails(): Boolean = readAppSettings()["network"]
        .asObjectOrNull()?.get("changeSourceLoadInfo")?.safeBoolean() == true

    private fun preloadSourceToc(): Boolean = readAppSettings()["network"]
        .asObjectOrNull()?.get("changeSourceLoadToc")?.safeBoolean() == true

    private fun autoChangeSourceEnabled(): Boolean = readAppSettings()["read"]
        .asObjectOrNull()?.get("autoChangeSource")?.safeBoolean() != false

    private fun batchChangeSourceDelay(): Int = readAppSettings()["network"]
        .asObjectOrNull()?.get("batchChangeSourceDelay")?.safeIntOrNull()?.coerceIn(0, 30000) ?: 0

    private fun loadSourceWordCount(): Boolean = readAppSettings()["network"]
        .asObjectOrNull()?.get("changeSourceLoadWordCount")?.safeBoolean() == true

    private fun searchSource(source: JsonObject, key: String): List<JsonObject> {
        return withSourceRuleContext(source) {
            val startedAt = System.nanoTime()
            val rule = source["ruleSearch"].asObjectOrNull() ?: return@withSourceRuleContext emptyList()
            val searchUrl = source.string("searchUrl").orEmpty()
            val requestSpec = sourceSearchRequest(source, searchUrl, key) ?: return@withSourceRuleContext emptyList()
            try {
                val response = sendSourceResponse(source, requestSpec.requestUrl, Duration.ofSeconds(15)) ?: return@withSourceRuleContext emptyList()
                val body = response.body().decodeSourceText(response.headers(), requestSpec.requestUrl.options)
                if (response.statusCode() !in 200..299) return@withSourceRuleContext emptyList()
                if (!matchesSearchCheckWord(body, rule.string("checkKeyWord"))) return@withSourceRuleContext emptyList()
                val latency = ((System.nanoTime() - startedAt) / 1_000_000).coerceAtLeast(0)
                withJavaScriptRuleContext(key, requestSpec.baseUrl) {
                    extractSourceBooks(source, rule, body, requestSpec.baseUrl, latency)
                }
            } catch (_: Exception) {
                emptyList()
            }
        }
    }

    private fun matchesSearchCheckWord(body: String, rule: String?): Boolean {
        val checks = rule.orEmpty().split("&&", "||", "\n")
            .map(String::trim)
            .filter(String::isNotBlank)
        if (checks.isEmpty()) return true
        return checks.any { check ->
            if (check.startsWith("@regex:", true)) {
                runCatching { Regex(check.substringAfter(':')).containsMatchIn(body) }.getOrDefault(false)
            } else body.contains(check)
        }
    }

    private data class JavaScriptRuleContext(
        val key: String = "",
        val baseUrl: String = "",
        val index: Int = 0,
        val gInt: Int = 0,
    )

    private fun <T> withJavaScriptRuleContext(
        key: String,
        baseUrl: String,
        index: Int = 0,
        gInt: Int = 0,
        block: () -> T,
    ): T {
        val previous = javaScriptRuleContext.get()
        javaScriptRuleContext.set(JavaScriptRuleContext(key, baseUrl, index, gInt))
        return try {
            block()
        } finally {
            javaScriptRuleContext.set(previous)
        }
    }

    private fun extractSourceBooks(
        source: JsonObject,
        rule: JsonObject,
        responseBody: String,
        baseUrl: String,
        latency: Long,
    ): List<JsonObject> = extractRuleValues(responseBody, rule.string("bookList").orEmpty()).mapNotNull { entry ->
        val name = extractRuleValue(entry, rule.string("name")).trim()
        val bookUrl = resolveSearchUrl(baseUrl, extractRuleValue(entry, rule.string("bookUrl")).trim())
        if (name.isBlank() || bookUrl.isBlank()) return@mapNotNull null
        JsonObject().apply {
            addProperty("name", name)
            addProperty("author", extractRuleValue(entry, rule.string("author")).trim())
            addProperty("bookUrl", bookUrl)
            addProperty("kind", extractRuleValue(entry, rule.string("kind")).trim())
            addProperty("wordCount", extractRuleValue(entry, rule.string("wordCount")).trim())
            addProperty("origin", source.string("bookSourceUrl") ?: "")
            addProperty("originName", source.string("bookSourceName") ?: "")
            addProperty("type", source["bookSourceType"].safeInt())
            resolveSearchUrl(baseUrl, extractRuleValue(entry, rule.string("coverUrl")).trim())
                .takeIf(String::isNotBlank)?.let { addProperty("coverUrl", it) }
            extractRuleValue(entry, rule.string("intro")).trim().takeIf(String::isNotBlank)
                ?.let { addProperty("intro", it) }
            extractRuleValue(entry, rule.string("lastChapter")).trim().takeIf(String::isNotBlank)
                ?.let { addProperty("latestChapterTitle", it) }
            addProperty("tocUrl", bookUrl)
            addProperty("time", System.currentTimeMillis())
            addProperty("originOrder", source["customOrder"].safeInt())
            addProperty("chapterWordCount", 0)
            addProperty("respondTime", latency)
        }
    }

    private data class SourceSearchRequest(val requestUrl: SourceRequestUrl, val baseUrl: String)

    private fun sourceSearchRequest(source: JsonObject, rawSearchUrl: String, key: String): SourceSearchRequest? {
        val separator = rawSearchUrl.indexOf(",{")
        val rawUrl = if (separator >= 0) rawSearchUrl.substring(0, separator) else rawSearchUrl
        val rawOptions = if (separator >= 0) rawSearchUrl.substring(separator + 1) else ""
        val encodedKey = URLEncoder.encode(key, StandardCharsets.UTF_8).replace("+", "%20")
        fun substitute(value: String): String = value
            .replace("{{key}}", encodedKey)
            .replace("{{searchKey}}", encodedKey)
            .replace("{key}", encodedKey)
            .replace("searchKey", key)
            .replace("%s", encodedKey)
        val url = expandSourceVariables(source, substitute(rawUrl)).trim()
        if (!url.startsWith("http://", true) && !url.startsWith("https://", true)) return null
        val options = runCatching { JsonParser.parseString(expandSourceVariables(source, substitute(rawOptions))).asObjectOrNull() }.getOrNull()
        return SourceSearchRequest(SourceRequestUrl(url, options), url)
    }

    private fun applySourceHeaders(builder: HttpRequest.Builder, source: JsonObject) {
        source.string("header")
            ?.let { expandSourceVariables(source, it) }
            ?.let { runCatching { JsonParser.parseString(it).asObjectOrNull() }.getOrNull() }
            ?.entrySet()
            ?.forEach { (name, value) -> if (name.isNotBlank()) builder.header(name, value.asString) }
    }

    private fun applySourceCookies(builder: HttpRequest.Builder, source: JsonObject) {
        if (!sourceCookieJarEnabled(source) || sourceHasCookieHeader(source)) return
        val sourceUrl = source.string("bookSourceUrl").orEmpty()
        if (sourceUrl.isBlank()) return
        val cookies = synchronized(sourceCookieLock) {
            readList("cookies").firstOrNull { it.string("url") == sourceUrl }
                ?.string("cookie")
                ?.trim()
                .orEmpty()
        }
        if (cookies.isNotBlank()) builder.header("Cookie", cookies.take(8192))
    }

    private fun captureSourceCookies(response: HttpResponse<*>, source: JsonObject) {
        if (!sourceCookieJarEnabled(source)) return
        val sourceUrl = source.string("bookSourceUrl").orEmpty()
        if (sourceUrl.isBlank()) return
        val updates = response.headers().allValues("set-cookie")
            .map { it.substringBefore(';').trim() }
            .filter { '=' in it }
        if (updates.isEmpty()) return
        synchronized(sourceCookieLock) {
            val values = linkedMapOf<String, String>()
            val list = readList("cookies")
            val existing = list.firstOrNull { it.string("url") == sourceUrl }
            existing?.string("cookie")?.split(';')?.forEach { item ->
                val pair = item.trim()
                val split = pair.indexOf('=')
                if (split > 0) values[pair.substring(0, split).trim()] = pair.substring(split + 1).trim()
            }
            updates.forEach { item ->
                val split = item.indexOf('=')
                values[item.substring(0, split).trim()] = item.substring(split + 1).trim()
            }
            val cookie = values.entries.joinToString("; ") { "${it.key}=${it.value}" }.take(8192)
            val record = existing ?: JsonObject().also { list.add(it) }
            record.addProperty("url", sourceUrl)
            record.addProperty("cookie", cookie)
            record.addProperty("updatedAt", System.currentTimeMillis())
            writeList("cookies", list)
        }
    }

    private fun sourceCookieJarEnabled(source: JsonObject): Boolean = source["enabledCookieJar"]?.safeBoolean() == true

    private fun expandSourceVariables(source: JsonObject, value: String): String {
        val context = sourceRuleContext.get() ?: source
        return expandRuleVariables(context, value)
    }

    private fun <T> withSourceRuleContext(source: JsonObject, block: () -> T): T {
        val previous = sourceRuleContext.get()
        sourceRuleContext.set(source)
        return try {
            block()
        } finally {
            sourceRuleContext.set(previous)
        }
    }

    private fun expandRuleVariables(source: JsonObject, value: String): String {
        if (!value.contains("{{") && !value.contains("@get:", true)) return value
        val sourceUrl = source.string("bookSourceUrl") ?: source.string("sourceUrl").orEmpty()
        val variables = sourceVariables(sourceUrl)
        val bracesExpanded = Regex("""\{\{([A-Za-z0-9_.-]+)}}""").replace(value) { match ->
            variables[match.groupValues[1]] ?: match.value
        }
        return Regex("""@get:\{([A-Za-z0-9_.-]+)}""", RegexOption.IGNORE_CASE)
            .replace(bracesExpanded) { match -> variables[match.groupValues[1]] ?: "" }
    }

    private fun sourceVariables(sourceUrl: String): Map<String, String> {
        val now = System.currentTimeMillis()
        val records = readList("cacheRecords")
            .asSequence()
            .filter { it["expires"].safeLong().let { expires -> expires <= 0 || expires > now } }
            .toList()
        val variables = linkedMapOf<String, String>()
        records.filter { it.string("sourceUrl").isNullOrBlank() }.forEach { record ->
            record.string("key")?.let { variables[it] = record.string("value").orEmpty() }
        }
        records.filter { it.string("sourceUrl") == sourceUrl }.forEach { record ->
            record.string("key")?.let { variables[it] = record.string("value").orEmpty() }
        }
        return variables
    }

    @Synchronized
    fun clearExpiredCacheRecords(): ReturnData {
        val removed = clearExpiredCacheRecordsCount()
        val remaining = readList("cacheRecords").size
        return ReturnData.ok(mapOf("removed" to removed, "remaining" to remaining, "at" to System.currentTimeMillis()))
    }

    private fun clearExpiredCacheRecordsCount(): Int {
        val now = System.currentTimeMillis()
        val records = readList("cacheRecords")
        val kept = records.filterNot { record ->
            val expires = record["expires"].safeLong()
            expires > 0 && expires <= now
        }
        val removed = records.size - kept.size
        if (removed > 0) writeList("cacheRecords", kept)
        return removed
    }

    private fun saveSourceVariable(source: JsonObject, key: String, value: String) {
        if (key.isBlank() || key.length > 100 || value.length > 65_536) return
        val sourceUrl = source.string("bookSourceUrl") ?: source.string("sourceUrl").orEmpty()
        synchronized(this) {
            val records = readList("cacheRecords")
            val record = records.firstOrNull { it.string("key") == key && it.string("sourceUrl") == sourceUrl }
                ?: JsonObject().also(records::add)
            record.addProperty("key", key)
            record.addProperty("value", value)
            record.addProperty("sourceUrl", sourceUrl)
            record.addProperty("updatedAt", System.currentTimeMillis())
            record.remove("expires")
            writeList("cacheRecords", records)
        }
    }

    private data class PortableRule(
        val expression: String,
        val puts: Map<String, String>,
        val replacePattern: String? = null,
        val replacement: String = "",
        val replaceFirst: Boolean = false,
    )

    private fun parsePortableRule(rule: String): PortableRule {
        val puts = linkedMapOf<String, String>()
        val withoutPuts = Regex("""@put:(\{[^{}]*})""", RegexOption.IGNORE_CASE).replace(rule) { match ->
            val objectValue = runCatching { JsonParser.parseString(match.groupValues[1]).asObjectOrNull() }.getOrNull()
            objectValue?.entrySet()?.forEach { (key, value) ->
                if (key.matches(Regex("[A-Za-z0-9_.-]{1,100}"))) {
                    puts[key] = runCatching { value.asString }.getOrDefault("")
                }
            }
            ""
        }
        val parts = withoutPuts.split("##")
        return PortableRule(
            expression = parts.firstOrNull().orEmpty().trim(),
            puts = puts,
            replacePattern = parts.getOrNull(1),
            replacement = parts.getOrNull(2).orEmpty(),
            replaceFirst = parts.size > 3,
        )
    }

    private fun applyRulePuts(source: JsonObject?, input: String, puts: Map<String, String>) {
        val activeSource = source ?: sourceRuleContext.get() ?: return
        puts.forEach { (key, valueRule) ->
            val value = extractRuleValue(input, valueRule)
            saveSourceVariable(activeSource, key, value)
        }
    }

    private fun sourceHasCookieHeader(source: JsonObject): Boolean = source.string("header")
        ?.let { runCatching { JsonParser.parseString(it).asObjectOrNull() }.getOrNull() }
        ?.entrySet()
        ?.any { (name, _) -> name.equals("cookie", ignoreCase = true) }
        ?: false

    private fun extractRuleValues(input: String, rule: String): List<String> {
        if (rule.isBlank()) return emptyList()
        val portableRule = parsePortableRule(rule)
        applyRulePuts(sourceRuleContext.get(), input, portableRule.puts)
        val expression = normalizePortableRuleExpression(
            expandRuleVariables(sourceRuleContext.get() ?: JsonObject(), portableRule.expression),
            listRule = true,
        )
        val values = when {
            expression.isBlank() && portableRule.replacePattern != null -> listOf(input)
            expression.isBlank() -> emptyList()
            isJavaScriptRule(expression) -> evaluateJavaScriptRule(input, expression).asValues()
            splitJavaScriptTransform(expression) != null -> {
                val (baseRule, transform) = splitJavaScriptTransform(expression)!!
                extractRuleValues(input, baseRule)
                    .flatMap { value -> evaluateJavaScriptRule(value, transform).asValues() }
            }
            expression.trimStart().startsWith("$") -> {
                val root = runCatching { JsonParser.parseString(input) }.getOrNull()
                root?.let { jsonPath(it, expression).map { value -> gson.toJson(value) } } ?: emptyList()
            }
            isXpathRule(expression) -> {
                val (xpath, attribute) = xpathRuleParts(expression)
                if (xpath.isBlank()) emptyList() else runCatching {
                    Jsoup.parseBodyFragment(input).body().selectXpath(xpath)
                        .map { elementRuleValue(it, attribute, outerHtmlWhenUnspecified = true) }
                }.getOrDefault(emptyList())
            }
            isCssRule(expression) -> {
                val (selector, attribute) = cssRuleParts(expression)
                if (selector.isBlank()) emptyList() else runCatching {
                    Jsoup.parseBodyFragment(input).select(selector)
                        .map { elementRuleValue(it, attribute, outerHtmlWhenUnspecified = true) }
                }.getOrDefault(emptyList())
            }
            else -> runCatching {
                Regex(expression, setOf(RegexOption.DOT_MATCHES_ALL)).findAll(input)
                    .map { match -> match.groups.drop(1).firstOrNull { it != null }?.value ?: match.value }
                    .toList()
            }.getOrDefault(emptyList())
        }
        return values.map { value -> applyPortableRuleReplacement(value, portableRule) }
    }

    private fun extractRuleValue(input: String, rule: String?): String {
        if (rule.isNullOrBlank()) return ""
        val portableRule = parsePortableRule(rule)
        applyRulePuts(sourceRuleContext.get(), input, portableRule.puts)
        val expression = normalizePortableRuleExpression(
            expandRuleVariables(sourceRuleContext.get() ?: JsonObject(), portableRule.expression),
            listRule = false,
        )
        val value = when {
            expression.isBlank() && portableRule.replacePattern != null -> input
            expression.isBlank() -> ""
            isJavaScriptRule(expression) -> evaluateJavaScriptRule(input, expression).asValue()
            splitJavaScriptTransform(expression) != null -> {
                val (baseRule, transform) = splitJavaScriptTransform(expression)!!
                evaluateJavaScriptRule(extractRuleValue(input, baseRule), transform).asValue()
            }
            expression.trimStart().startsWith("$") -> {
                val root = runCatching { JsonParser.parseString(input) }.getOrNull()
                val item = root?.let { jsonPath(it, expression).firstOrNull() }
                if (item == null) "" else if (item.isJsonPrimitive) item.asString else gson.toJson(item)
            }
            isXpathRule(expression) -> runCatching {
                val (xpath, attribute) = xpathRuleParts(expression)
                val element = Jsoup.parseBodyFragment(input).body().selectXpath(xpath).firstOrNull() ?: return@runCatching ""
                elementRuleValue(element, attribute)
            }.getOrDefault("")
            isCssRule(expression) -> runCatching {
                val (selector, attribute) = cssRuleParts(expression)
                val element = if (selector.isBlank()) Jsoup.parseBodyFragment(input).body()
                    else Jsoup.parseBodyFragment(input).selectFirst(selector)
                    ?: return@runCatching ""
                elementRuleValue(element, attribute)
            }.getOrDefault("")
            else -> runCatching {
                val match = Regex(expression, setOf(RegexOption.DOT_MATCHES_ALL)).find(input) ?: return@runCatching ""
                match.groups.drop(1).firstOrNull { it != null }?.value ?: match.value
            }.getOrDefault("")
        }
        return applyPortableRuleReplacement(value, portableRule)
    }

    private fun applyPortableRuleReplacement(value: String, rule: PortableRule): String {
        val rawPattern = rule.replacePattern ?: return value
        val source = sourceRuleContext.get() ?: JsonObject()
        val pattern = expandRuleVariables(source, rawPattern)
        val replacement = expandRuleVariables(source, rule.replacement)
        if (pattern.isBlank()) return value
        val regex = runCatching { Regex(pattern) }.getOrNull()
        if (regex == null) return value.replace(pattern, replacement)
        if (!rule.replaceFirst) return regex.replace(value, replacement)
        val match = regex.find(value) ?: return ""
        return regex.replaceFirst(match.value, replacement)
    }

    private fun normalizePortableRuleExpression(rawRule: String, listRule: Boolean): String {
        val rule = rawRule.trim()
        return when {
            rule.startsWith("@@") -> rule.substring(2).trim()
            rule.startsWith("@json:", true) -> rule.substring(6).trim()
            rule.startsWith("@regex:", true) -> rule.substring(7).trim()
            listRule && rule.startsWith(':') -> rule.substring(1).trim()
            else -> rule
        }
    }

    private fun elementRuleValue(element: org.jsoup.nodes.Element, attribute: String?, outerHtmlWhenUnspecified: Boolean = false): String =
        when (attribute?.lowercase()) {
            null -> if (outerHtmlWhenUnspecified) element.outerHtml() else element.text()
            "text" -> element.text()
            "owntext" -> element.ownText()
            "html" -> element.html()
            "outerhtml" -> element.outerHtml()
            else -> element.attr(attribute)
        }

    private data class JavaScriptRuleResult(
        val json: String? = null,
        val error: String? = null,
    ) {
        fun asValue(): String {
            val value = json?.let { runCatching { JsonParser.parseString(it) }.getOrNull() } ?: return ""
            return if (value.isJsonPrimitive) runCatching { value.asString }.getOrDefault("") else value.toString()
        }

        fun asValues(): List<String> {
            val value = json?.let { runCatching { JsonParser.parseString(it) }.getOrNull() } ?: return emptyList()
            return when {
                value.isJsonArray -> value.asJsonArray.map { item ->
                    if (item.isJsonPrimitive) runCatching { item.asString }.getOrDefault("") else item.toString()
                }
                value.isJsonNull -> emptyList()
                value.isJsonPrimitive -> listOf(runCatching { value.asString }.getOrDefault(""))
                else -> listOf(value.toString())
            }
        }
    }

    private fun isJavaScriptRule(rule: String): Boolean = rule.trimStart().startsWith("@js:", true)

    private fun splitJavaScriptTransform(rule: String): Pair<String, String>? {
        val marker = rule.indexOf("@js:", ignoreCase = true)
        if (marker <= 0) return null
        val baseRule = rule.substring(0, marker).trim()
        val script = rule.substring(marker).trim()
        return baseRule.takeIf(String::isNotBlank)?.let { it to script }
    }

    /**
     * Executes only transformation rules. JavaScript receives plain strings and
     * no Java, file, network, process, or thread access. Each invocation gets a
     * fresh context so state cannot leak across sources or requests.
     */
    private fun evaluateJavaScriptRule(
        input: String,
        rule: String,
        includeGInt: Boolean = false,
    ): JavaScriptRuleResult {
        val script = rule.trimStart().substringAfter("@js:", "").trim()
        if (script.isBlank()) return JavaScriptRuleResult(error = "JavaScript rule is empty")

        val contextRef = java.util.concurrent.atomic.AtomicReference<Context?>()
        val task = javaScriptRuleExecutor.submit<JavaScriptRuleResult> {
            val context = Context.newBuilder("js")
                .allowAllAccess(false)
                .allowHostAccess(HostAccess.NONE)
                .allowHostClassLookup { false }
                .allowCreateThread(false)
                .allowNativeAccess(false)
                .allowIO(IOAccess.NONE)
                .option("engine.WarnInterpreterOnly", "false")
                .build()
            contextRef.set(context)
            try {
                val bindings = context.getBindings("js")
                bindings.putMember("input", input)
                val ruleContext = javaScriptRuleContext.get() ?: JavaScriptRuleContext()
                bindings.putMember("inputKey", ruleContext.key)
                bindings.putMember("inputBaseUrl", ruleContext.baseUrl)
                bindings.putMember("inputIndex", ruleContext.index)
                bindings.putMember("inputGInt", ruleContext.gInt)
                val expression = """
                    (function () {
                      const result = String(input);
                      const key = String(inputKey);
                      const searchKey = key;
                      const baseUrl = String(inputBaseUrl);
                      ${if (includeGInt) "let" else "const"} title = result;
                      const index = Number(inputIndex);
                      ${if (includeGInt) "let" else "const"} gInt = Number(inputGInt);
                      let resultJson = null;
                      try { resultJson = JSON.parse(result); } catch (_) {}
                      const __value = ($script);
                      return JSON.stringify($includeGInt
                        ? { value: __value === undefined ? "" : __value, gInt: gInt }
                        : (__value === undefined ? "" : __value));
                    })()
                """.trimIndent()
                val statement = """
                    (function () {
                      let result = String(input);
                      const key = String(inputKey);
                      const searchKey = key;
                      const baseUrl = String(inputBaseUrl);
                      ${if (includeGInt) "let" else "const"} title = result;
                      const index = Number(inputIndex);
                      let gInt = Number(inputGInt);
                      let resultJson = null;
                      try { resultJson = JSON.parse(result); } catch (_) {}
                      const __value = (function () {
                        $script
                        return result;
                      })();
                      return JSON.stringify($includeGInt
                        ? { value: __value === undefined ? "" : __value, gInt: gInt }
                        : (__value === undefined ? "" : __value));
                    })()
                """.trimIndent()
                val value = runCatching { context.eval("js", expression) }
                    .recoverCatching { context.eval("js", statement) }
                    .getOrElse { error -> return@submit JavaScriptRuleResult(error = ruleError(error)) }
                JavaScriptRuleResult(json = polyglotString(value))
            } finally {
                runCatching { context.close(true) }
                contextRef.compareAndSet(context, null)
            }
        }
        return try {
            task.get(JAVA_SCRIPT_RULE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
        } catch (_: java.util.concurrent.TimeoutException) {
            contextRef.getAndSet(null)?.let { context -> runCatching { context.close(true) } }
            task.cancel(true)
            JavaScriptRuleResult(error = "JavaScript rule timed out after ${JAVA_SCRIPT_RULE_TIMEOUT_MILLIS}ms")
        } catch (error: Exception) {
            JavaScriptRuleResult(error = ruleError(error))
        }
    }

    private fun polyglotString(value: Value): String? = runCatching {
        if (value.isNull) "null" else value.asString()
    }.getOrNull()

    private fun ruleError(error: Throwable): String = (error.message ?: error.javaClass.simpleName)
        .lineSequence()
        .firstOrNull()
        ?.take(300)
        .orEmpty()

    private fun isXpathRule(rawRule: String): Boolean {
        val rule = rawRule.trim()
        return rule.startsWith("@xpath:", true) || rule.startsWith("//") || rule.startsWith(".//")
    }

    private fun xpathRuleParts(rawRule: String): Pair<String, String?> {
        val rule = if (rawRule.trim().startsWith("@xpath:", true)) rawRule.trim().substring(7).trim() else rawRule.trim()
        if (rule.endsWith("/text()")) return rule.removeSuffix("/text()") to "text"
        val attribute = Regex("""/@([A-Za-z_:][-A-Za-z0-9_:.]*)$""").find(rule)
        return if (attribute != null) rule.removeSuffix(attribute.value) to attribute.groupValues[1] else rule to null
    }

    private fun isCssRule(rawRule: String): Boolean {
        val rule = if (rawRule.trim().startsWith("@css:", true)) rawRule.trim().substring(5).trim() else rawRule.trim()
        if (rawRule.trim().startsWith("@css:", true)) return true
        if (rule.isBlank() || rule.startsWith("@xpath:", true) || rule.startsWith("@regex:", true)) return false
        if (Regex("""[\\(){}|]""").containsMatchIn(rule)) return false
        if (rule.contains('@')) return true
        return Regex("""^[A-Za-z][A-Za-z0-9_-]*(?:[ .#:\[>+~][^\n]*)?$|^[.#][A-Za-z0-9_-].*$""").matches(rule)
    }

    private fun cssSelector(rawRule: String): String = cssRuleParts(rawRule).first

    private fun cssRuleParts(rawRule: String): Pair<String, String?> {
        val rule = if (rawRule.trim().startsWith("@css:", true)) rawRule.trim().substring(5).trim() else rawRule.trim()
        val marker = rule.lastIndexOf('@')
        if (marker <= 0) return rule to null
        val selector = rule.substring(0, marker).trim()
        val attribute = rule.substring(marker + 1).trim().takeIf(String::isNotBlank)
        return selector to attribute
    }

    /** Minimal JSONPath: $.field.nested, [index], [*], and dot traversal. */
    private fun jsonPath(root: JsonElement, expression: String): List<JsonElement> {
        val tokens = Regex("""(?:^|\.)([A-Za-z0-9_-]+)|\[([0-9*]+)]""")
            .findAll(expression.removePrefix("$"))
            .map { it.groupValues[1].ifBlank { "[${it.groupValues[2]}]" } }
            .toList()
        var values: List<JsonElement> = listOf(root)
        for (token in tokens) {
            val next = mutableListOf<JsonElement>()
            for (value in values) {
                when {
                    token.startsWith("[") -> {
                        val index = token.removePrefix("[").removeSuffix("]")
                        val array = value.asArrayOrNull() ?: continue
                        if (index == "*") {
                            array.forEach { next.add(it) }
                        } else {
                            val item = index.toIntOrNull()?.let { i -> if (i in 0 until array.size()) array[i] else null }
                            if (item != null) next.add(item)
                        }
                    }
                    value.isJsonObject -> value.asJsonObject[token]?.let(next::add)
                    value.isJsonArray -> value.asJsonArray.forEach { item ->
                        item.asObjectOrNull()?.get(token)?.let(next::add)
                    }
                }
            }
            values = next
        }
        return values
    }

    private fun resolveSearchUrl(baseUrl: String, candidate: String): String {
        if (candidate.isBlank()) return ""
        val separator = candidate.indexOf(",{")
        val candidateUrl = if (separator >= 0) candidate.substring(0, separator) else candidate
        val options = if (separator >= 0) candidate.substring(separator) else ""
        val cleanBase = baseUrl.substringBefore(",{")
        return runCatching { URI.create(cleanBase).resolve(candidateUrl).toString() + options }.getOrDefault(candidate)
    }

    @Synchronized
    fun saveSource(kind: String, key: String, postData: String?, single: Boolean): ReturnData {
        val data = parseJson(postData) ?: return ReturnData.error("Request body is required")
        return if (single) {
            val item = data.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
            validateSource(item, key)?.let { return ReturnData.error(it) }
            upsert(kind, key, item)
            ReturnData.ok("")
        } else {
            val items = data.asArrayOrNull() ?: return ReturnData.error("Expected JSON array")
            val saved = JsonArray()
            for (element in items) {
                val item = element.asObjectOrNull() ?: continue
                if (validateSource(item, key) == null) {
                    upsert(kind, key, item)
                    saved.add(item)
                }
            }
            ReturnData.ok(saved)
        }
    }

    @Synchronized
    fun deleteSources(kind: String, key: String, postData: String?): ReturnData {
        val data = parseJson(postData) ?: return ReturnData.error("Request body is required")
        val keys = when {
            data.isJsonArray -> data.asJsonArray.mapNotNull { it.asObjectOrNull()?.string(key) }.toSet()
            data.isJsonObject -> setOfNotNull(data.asJsonObject.string(key))
            else -> emptySet()
        }
        if (keys.isEmpty()) return ReturnData.error("No source keys found")
        val list = readList(kind)
        val kept = list.filterNot { it.string(key) in keys }
        writeList(kind, kept)
        return ReturnData.ok("ok")
    }

    @Synchronized
    fun getReplaceRules(): ReturnData = ReturnData.ok(gson.toJson(readList("replaceRules")))

    @Synchronized
    fun saveReplaceRule(postData: String?): ReturnData {
        val rule = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        if (rule.string("id").isNullOrBlank()) {
            rule.addProperty("id", System.currentTimeMillis())
        }
        if (!rule.has("order") || rule["order"].safeInt() == Int.MIN_VALUE) {
            val maxOrder = readList("replaceRules").mapNotNull { it["order"].safeIntOrNull() }.maxOrNull() ?: 0
            rule.addProperty("order", maxOrder + 1)
        }
        upsert("replaceRules", "id", rule)
        return ReturnData.ok("")
    }

    @Synchronized
    fun getTxtTocRules(): ReturnData = ReturnData.ok(readList("txtTocRules"))

    @Synchronized
    fun saveTxtTocRule(postData: String?): ReturnData {
        val rule = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        if (rule.string("id").isNullOrBlank()) {
            rule.addProperty("id", System.currentTimeMillis())
        }
        if (rule.string("name").isNullOrBlank()) return ReturnData.error("name is required")
        if (rule.string("rule").isNullOrBlank()) return ReturnData.error("rule is required")
        upsert("txtTocRules", "id", rule)
        return ReturnData.ok("")
    }

    @Synchronized
    fun deleteTxtTocRule(postData: String?): ReturnData {
        val rule = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val id = rule.string("id") ?: return ReturnData.error("Rule id is required")
        val kept = readList("txtTocRules").filterNot { it.string("id") == id }
        writeList("txtTocRules", kept)
        return ReturnData.ok("")
    }

    @Synchronized
    fun deleteReplaceRule(postData: String?): ReturnData {
        val rule = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val id = rule.string("id") ?: return ReturnData.error("Rule id is required")
        val kept = readList("replaceRules").filterNot { it.string("id") == id }
        writeList("replaceRules", kept)
        return ReturnData.ok("")
    }

    fun testReplaceRule(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val ruleElement = payload["rule"] ?: return ReturnData.error("rule is required")
        val rule = if (ruleElement.isJsonPrimitive && ruleElement.asJsonPrimitive.isString) {
            parseJson(ruleElement.asString)?.asObjectOrNull()
        } else {
            ruleElement.asObjectOrNull()
        } ?: return ReturnData.error("rule must be an object")
        val text = payload.string("text") ?: ""
        val pattern = rule.string("pattern") ?: return ReturnData.error("pattern is required")
        val replacement = rule.string("replacement") ?: ""
        val isRegex = rule["isRegex"]?.safeBoolean() ?: true
        return try {
            ReturnData.ok(if (isRegex) text.replace(pattern.toRegex(), replacement) else text.replace(pattern, replacement))
        } catch (error: Exception) {
            ReturnData.ok(error.stackTraceToString())
        }
    }

    @Synchronized
    fun getBookshelf(): ReturnData {
        val books = readList("books").sortedByDescending { it["durChapterTime"].safeLong() }
        return if (books.isEmpty()) ReturnData.error("No books saved") else ReturnData.ok(books)
    }

    @Synchronized
    fun refreshBookInfo(bookUrl: String?): ReturnData {
        if (bookUrl.isNullOrBlank()) return ReturnData.error("Parameter url is required")
        val books = readList("books")
        val book = books.firstOrNull { it.string("bookUrl") == bookUrl }
            ?: return ReturnData.error("Book not found")
        val source = sourceForBook(book)
            ?: return ReturnData.error("This book has no portable remote source rule")
        val previousLatest = book.string("latestChapterTitle").orEmpty()
        if (!applyBookInfoRules(book, source, allowRename = false)) {
            return ReturnData.error("Unable to load book details with this source rule")
        }
        val currentLatest = book.string("latestChapterTitle").orEmpty()
        book.addProperty("lastCheckTime", System.currentTimeMillis())
        book.addProperty("lastCheckCount", if (currentLatest.isNotBlank() && currentLatest != previousLatest) 1 else 0)
        book.withBookDefaults()
        writeList("books", books)
        return ReturnData.ok(book)
    }

    @Synchronized
    fun saveBook(postData: String?): ReturnData {
        val book = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        book.withBookDefaults()
        if (book.string("bookUrl").isNullOrBlank()) return ReturnData.error("bookUrl is required")
        upsert("books", "bookUrl", book)
        return ReturnData.ok("")
    }

    fun exportBook(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val bookUrl = payload.string("bookUrl") ?: return ReturnData.error("bookUrl is required")
        val book = readList("books").firstOrNull { it.string("bookUrl") == bookUrl }
            ?: return ReturnData.error("Book not found")
        val chapters = readChapterList(bookUrl).sortedBy { it["index"].safeInt() }
        if (chapters.isEmpty()) return ReturnData.error("No chapters saved for this book")
        val backup = readAppSettings()["backup"].asObjectOrNull() ?: JsonObject()
        val exportType = backup["exportType"].safeIntOrNull() ?: 0
        val exportPictures = exportType == 1 && backup["exportPictureFile"]?.safeBoolean() == true
        val epubImages = linkedMapOf<String, EpubImage>()
        val includeTitle = backup["exportNoChapterName"]?.safeBoolean() != true
        val useReplace = backup["exportUseReplace"]?.safeBoolean() != false
        val rules = if (useReplace) {
            readList("replaceRules")
                .filter { it["isEnabled"]?.safeBoolean() != false && it["scopeContent"]?.safeBoolean() != false }
                .sortedBy { it["order"].safeIntOrNull() ?: Int.MAX_VALUE }
        } else emptyList()
        val text = buildString {
            chapters.forEachIndexed { index, chapter ->
                if (includeTitle) append(chapter.string("title").orEmpty()).append("\n\n")
                var content = chapter.string("content").orEmpty()
                rules.forEach { rule ->
                    val pattern = rule.string("pattern").orEmpty()
                    val replacement = rule.string("replacement").orEmpty()
                    if (pattern.isNotEmpty()) {
                        content = try {
                            if (rule["isRegex"]?.safeBoolean() != false) content.replace(pattern.toRegex(), replacement)
                            else content.replace(pattern, replacement)
                        } catch (_: Exception) { content }
                    }
                }
                if (exportPictures) {
                    collectEpubImages(content, chapter.string("url").orEmpty(), epubImages)
                }
                append(content.replace(Regex("<img[^>]*>", RegexOption.IGNORE_CASE), "[图片]")
                    .replace(Regex("<[^>]+>"), "")
                    .trim())
                if (index < chapters.lastIndex) append("\n\n")
            }
        }
        val name = book.string("name").orEmpty().ifEmpty { "book" }
        val author = book.string("author").orEmpty()
        val template = backup.string("bookExportFileName").orEmpty()
        val custom = backup["enableCustomExport"]?.safeBoolean() == true && template.isNotBlank()
        val baseName = if (custom) template
            .replace("{name}", name)
            .replace("{author}", author)
            .replace("{date}", java.time.LocalDate.now().toString())
            else listOf(name, author).filter(String::isNotBlank).joinToString(" - ")
        val safeBaseName = baseName.replace(Regex("[\\/:*?\"<>|]"), "_").trim().ifEmpty { "book" }
        val (fileName, mime, bytes) = if (exportType == 1) {
            Triple(
                "$safeBaseName.epub",
                "application/epub+zip",
                createEpub(name, author, text, epubImages.values.toList()),
            )
        } else {
            val charsetName = backup.string("exportCharset")?.trim().orEmpty().ifEmpty { "UTF-8" }
            val charset = try { Charset.forName(charsetName) } catch (_: Exception) { StandardCharsets.UTF_8 }
            Triple(
                "$safeBaseName.txt",
                "text/plain; charset=${charset.name()}",
                text.toByteArray(charset),
            )
        }
        return ReturnData.ok(mapOf(
            "fileName" to fileName,
            "mime" to mime,
            "base64" to Base64.getEncoder().encodeToString(bytes),
            "imageCount" to epubImages.size,
        ))
    }

    fun exportBooks(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: JsonObject()
        val requested = payload["bookUrls"].asArrayOrNull()
            ?.mapNotNull { it.takeIf(JsonElement::isJsonPrimitive)?.asString }
            ?.filter(String::isNotBlank)
            ?.distinct()
            .orEmpty()
        val bookUrls = if (requested.isEmpty()) {
            readList("books").mapNotNull { it.string("bookUrl") }.filter(String::isNotBlank)
        } else requested
        if (bookUrls.isEmpty()) return ReturnData.error("No books selected")
        val parallel = readAppSettings()["backup"]
            .asObjectOrNull()
            ?.get("parallelExportBook")
            ?.safeBoolean()
            ?: false
        val exportOne = { bookUrl: String ->
            val request = JsonObject().apply { addProperty("bookUrl", bookUrl) }
            val result = exportBook(request.toString())
            if (result.isSuccess) {
                mapOf("bookUrl" to bookUrl, "isSuccess" to true, "data" to result.data)
            } else {
                mapOf("bookUrl" to bookUrl, "isSuccess" to false, "errorMsg" to result.errorMsg)
            }
        }
        val results = if (parallel && bookUrls.size > 1) {
            val executor = Executors.newFixedThreadPool(minOf(networkThreadCount(), bookUrls.size, 8))
            try {
                executor.invokeAll(bookUrls.map { bookUrl -> Callable { exportOne(bookUrl) } })
                    .map { it.get() }
            } finally {
                executor.shutdown()
            }
        } else {
            bookUrls.map(exportOne)
        }
        return ReturnData.ok(mapOf(
            "parallel" to (parallel && bookUrls.size > 1),
            "succeeded" to results.count { it["isSuccess"] == true },
            "failed" to results.count { it["isSuccess"] != true },
            "results" to results,
        ))
    }

    fun exportBookEpisodes(postData: String?): ReturnData {
        val payload = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val bookUrl = payload.string("bookUrl") ?: return ReturnData.error("bookUrl is required")
        val book = readList("books").firstOrNull { it.string("bookUrl") == bookUrl }
            ?: return ReturnData.error("Book not found")
        val chapters = readChapterList(bookUrl).sortedBy { it["index"].safeInt() }
        if (chapters.isEmpty()) return ReturnData.error("No chapters saved for this book")
        val backup = readAppSettings()["backup"].asObjectOrNull() ?: JsonObject()
        val template = backup.string("episodeExportFileName")?.trim().orEmpty()
            .ifEmpty { "{index} - {title}" }
        val charsetName = backup.string("exportCharset")?.trim().orEmpty().ifEmpty { "UTF-8" }
        val charset = try { Charset.forName(charsetName) } catch (_: Exception) { StandardCharsets.UTF_8 }
        val bookName = book.string("name").orEmpty().ifEmpty { "book" }
        val author = book.string("author").orEmpty()
        val bytes = ByteArrayOutputStream().use { output ->
            ZipOutputStream(output, StandardCharsets.UTF_8).use { zip ->
                val usedNames = mutableSetOf<String>()
                chapters.forEachIndexed { position, chapter ->
                    val title = chapter.string("title").orEmpty().ifEmpty { "chapter-${position + 1}" }
                    val rawName = template
                        .replace("{index}", (position + 1).toString().padStart(4, '0'))
                        .replace("{title}", title)
                        .replace("{book}", bookName)
                        .replace("{author}", author)
                    val baseName = rawName.replace(Regex("[\\/:*?\"<>|]"), "_")
                        .replace("..", "_")
                        .trim(' ', '.')
                        .take(180)
                        .ifEmpty { "chapter-${position + 1}" }
                    var entryName = "$baseName.txt"
                    if (!usedNames.add(entryName.lowercase())) {
                        entryName = "$baseName-${position + 1}.txt"
                        usedNames.add(entryName.lowercase())
                    }
                    val content = chapter.string("content").orEmpty()
                        .replace(Regex("<img[^>]*>", RegexOption.IGNORE_CASE), "[图片]")
                        .replace(Regex("<[^>]+>"), "")
                        .trim()
                    zip.putNextEntry(ZipEntry(entryName))
                    zip.write(content.toByteArray(charset))
                    zip.closeEntry()
                }
            }
            output.toByteArray()
        }
        val zipName = listOf(bookName, author).filter(String::isNotBlank).joinToString(" - ")
            .replace(Regex("[\\/:*?\"<>|]"), "_").trim().ifEmpty { "book" }
        return ReturnData.ok(mapOf(
            "fileName" to "$zipName - chapters.zip",
            "mime" to "application/zip",
            "base64" to Base64.getEncoder().encodeToString(bytes),
            "episodeCount" to chapters.size,
        ))
    }

    private fun collectEpubImages(
        content: String,
        chapterUrl: String,
        images: MutableMap<String, EpubImage>,
    ) {
        if (images.size >= 32) return
        val imagePattern = Regex("""<img[^>]*\bsrc\s*=\s*(["'])(.*?)\1[^>]*>""", RegexOption.IGNORE_CASE)
        var totalBytes = images.values.sumOf { it.bytes.size.toLong() }
        for (match in imagePattern.findAll(content)) {
            if (images.size >= 32 || totalBytes >= 32L * 1024 * 1024) break
            val rawSource = match.groupValues[2].trim()
            if (rawSource.isEmpty()) continue
            val source = resolveExportImageUrl(chapterUrl, rawSource)
            if (source in images) continue
            val loaded = loadExportImage(source, images.size + 1) ?: continue
            if (totalBytes + loaded.bytes.size > 32L * 1024 * 1024) continue
            images[source] = loaded
            totalBytes += loaded.bytes.size
        }
    }

    private fun resolveExportImageUrl(chapterUrl: String, source: String): String {
        if (source.startsWith("data:", ignoreCase = true)) return source
        return runCatching {
            val base = URI.create(chapterUrl)
            if (base.scheme == "http" || base.scheme == "https") base.resolve(source).toString() else source
        }.getOrDefault(source)
    }

    private fun loadExportImage(source: String, index: Int): EpubImage? {
        val maxImageBytes = 8L * 1024 * 1024
        return try {
            val (mime, bytes) = if (source.startsWith("data:", ignoreCase = true)) {
                val match = Regex("""^data:(image/[a-zA-Z0-9.+-]+);base64,(.+)$""", RegexOption.DOT_MATCHES_ALL)
                    .matchEntire(source) ?: return null
                match.groupValues[1].lowercase() to Base64.getDecoder().decode(
                    match.groupValues[2].filterNot(Char::isWhitespace)
                )
            } else if (source.startsWith("http://", true) || source.startsWith("https://", true)) {
                val request = HttpRequest.newBuilder(URI.create(source))
                    .timeout(Duration.ofSeconds(20))
                    .header("User-Agent", networkUserAgent())
                    .GET()
                    .build()
                val response = networkClient().send(request, HttpResponse.BodyHandlers.ofByteArray())
                if (response.statusCode() !in 200..299) return null
                response.headers().firstValue("content-type").orElse("")
                    .substringBefore(';').lowercase() to response.body()
            } else {
                val path = Paths.get(source).toAbsolutePath().normalize()
                val allowedRoot = dataDir.toAbsolutePath().normalize()
                if (!path.startsWith(allowedRoot) || !path.isRegularFile() || Files.size(path) > maxImageBytes) return null
                mimeType(path).substringBefore(';').lowercase() to Files.readAllBytes(path)
            }
            if (!mime.startsWith("image/") || bytes.isEmpty() || bytes.size > maxImageBytes) return null
            val extension = when (mime) {
                "image/jpeg" -> "jpg"
                "image/svg+xml" -> "svg"
                "image/png", "image/gif", "image/webp", "image/avif" -> mime.substringAfter('/')
                else -> "bin"
            }
            EpubImage("image-$index.$extension", mime, bytes)
        } catch (_: Exception) {
            null
        }
    }

    private fun createEpub(title: String, author: String, text: String, images: List<EpubImage>): ByteArray {
        val identifier = "urn:legado:${sha256("$title\u0000$author".toByteArray(StandardCharsets.UTF_8)).take(32)}"
        val escapedTitle = xmlEscape(title)
        val escapedAuthor = xmlEscape(author.ifBlank { "Unknown" })
        val escapedText = xmlEscape(text)
        val imageMarkup = images.joinToString("\n") { image ->
            "<figure><img src=\"images/${xmlEscape(image.fileName)}\" alt=\"Chapter image\"/></figure>"
        }
        val imageManifest = images.mapIndexed { index, image ->
            "<item id=\"image-${index + 1}\" href=\"images/${xmlEscape(image.fileName)}\" media-type=\"${xmlEscape(image.mime)}\"/>"
        }.joinToString("")
        val container = """<?xml version="1.0" encoding="UTF-8"?>
            |<container version="1.0" xmlns="urn:oasis:names:tc:opendocument:xmlns:container">
            |  <rootfiles><rootfile full-path="OEBPS/content.opf" media-type="application/oebps-package+xml"/></rootfiles>
            |</container>
        """.trimMargin()
        val content = """<?xml version="1.0" encoding="UTF-8"?>
            |<!DOCTYPE html>
            |<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
            |<head><meta charset="UTF-8"/><title>$escapedTitle</title><style>body{font-family:serif;line-height:1.7;margin:5%;}pre{font:inherit;white-space:pre-wrap;overflow-wrap:anywhere;}</style></head>
            |<body><h1>$escapedTitle</h1><p>$escapedAuthor</p><pre>$escapedText</pre>$imageMarkup</body>
            |</html>
        """.trimMargin()
        val packageDocument = """<?xml version="1.0" encoding="UTF-8"?>
            |<package xmlns="http://www.idpf.org/2007/opf" unique-identifier="book-id" version="2.0">
            |  <metadata xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:opf="http://www.idpf.org/2007/opf">
            |    <dc:identifier id="book-id" opf:scheme="URI">$identifier</dc:identifier>
            |    <dc:title>$escapedTitle</dc:title><dc:creator>$escapedAuthor</dc:creator><dc:language>zh-CN</dc:language>
            |  </metadata>
            |  <manifest><item id="content" href="content.xhtml" media-type="application/xhtml+xml"/><item id="ncx" href="toc.ncx" media-type="application/x-dtbncx+xml"/>$imageManifest</manifest>
            |  <spine toc="ncx"><itemref idref="content"/></spine>
            |</package>
        """.trimMargin()
        val toc = """<?xml version="1.0" encoding="UTF-8"?>
            |<!DOCTYPE ncx PUBLIC "-//NISO//DTD ncx 2005-1//EN" "http://www.daisy.org/z3986/2005/ncx-2005-1.dtd">
            |<ncx xmlns="http://www.daisy.org/z3986/2005/ncx/" version="2005-1">
            |  <head><meta name="dtb:uid" content="$identifier"/></head><docTitle><text>$escapedTitle</text></docTitle>
            |  <navMap><navPoint id="content" playOrder="1"><navLabel><text>$escapedTitle</text></navLabel><content src="content.xhtml"/></navPoint></navMap>
            |</ncx>
        """.trimMargin()

        val output = ByteArrayOutputStream()
        ZipOutputStream(output).use { zip ->
            val mimeBytes = "application/epub+zip".toByteArray(StandardCharsets.US_ASCII)
            val crc = CRC32().apply { update(mimeBytes) }
            zip.putNextEntry(ZipEntry("mimetype").apply {
                method = ZipEntry.STORED
                size = mimeBytes.size.toLong()
                compressedSize = size
                this.crc = crc.value
            })
            zip.write(mimeBytes)
            zip.closeEntry()
            zip.writeUtf8Entry("META-INF/container.xml", container)
            zip.writeUtf8Entry("OEBPS/content.xhtml", content)
            zip.writeUtf8Entry("OEBPS/content.opf", packageDocument)
            zip.writeUtf8Entry("OEBPS/toc.ncx", toc)
            images.forEach { image -> zip.writeEntry("OEBPS/images/${image.fileName}", image.bytes) }
        }
        return output.toByteArray()
    }

    @Synchronized
    fun deleteBook(postData: String?): ReturnData {
        val book = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val bookUrl = book.string("bookUrl") ?: return ReturnData.error("bookUrl is required")
        val existing = readList("books").firstOrNull { it.string("bookUrl") == bookUrl }
        val kept = readList("books").filterNot { it.string("bookUrl") == bookUrl }
        writeList("books", kept)
        val chaptersFile = chaptersFile(bookUrl)
        if (chaptersFile.exists()) Files.delete(chaptersFile)
        if (existing?.get("managedLocalFile")?.safeBoolean() == true) {
            existing.string("localFile")?.let(::deleteLocalBookFile)
        }
        return ReturnData.ok("")
    }

    private fun deleteLocalBookFile(rawPath: String) {
        val file = runCatching { Paths.get(rawPath).toAbsolutePath().normalize() }.getOrNull() ?: return
        if (!file.isRegularFile()) return
        runCatching { Files.deleteIfExists(file) }
    }

    @Synchronized
    fun saveBookProgress(postData: String?): ReturnData {
        val progress = parseJson(postData)?.asObjectOrNull() ?: return ReturnData.error("Expected JSON object")
        val syncEnabled = readAppSettings()["backup"]
            .asObjectOrNull()
            ?.get("syncBookProgress")
            ?.safeBoolean()
            ?: true
        if (!syncEnabled) return ReturnData.ok("")
        val name = progress.string("name")
        val author = progress.string("author")
        val books = readList("books")
        val book = books.firstOrNull { it.string("name") == name && it.string("author") == author }
            ?: return ReturnData.error("Book not found")
        for (key in listOf("durChapterIndex", "durChapterPos", "durChapterTime", "durChapterTitle")) {
            progress[key]?.let { book.add(key, it.deepCopy()) }
        }
        writeList("books", books)
        val records = readList("readRecords")
        val bookUrl = book.string("bookUrl").orEmpty()
        val record = records.firstOrNull { it.string("id") == bookUrl }
            ?: JsonObject().also(records::add)
        record.addProperty("id", bookUrl)
        record.addProperty("bookUrl", bookUrl)
        record.addProperty("bookName", book.string("name") ?: "")
        record.addProperty("bookAuthor", book.string("author") ?: "")
        record.addProperty("chapterTitle", book.string("durChapterTitle") ?: "")
        record.addProperty("chapterIndex", book["durChapterIndex"].safeIntOrNull() ?: 0)
        record.addProperty("chapterPos", book["durChapterPos"].safeIntOrNull() ?: 0)
        record.addProperty("readTime", System.currentTimeMillis())
        writeList("readRecords", records)
        return ReturnData.ok("")
    }

    @Synchronized
    fun getChapterList(bookUrl: String?, refresh: Boolean = false): ReturnData {
        if (bookUrl.isNullOrBlank()) return ReturnData.error("Parameter url is required")
        var chapters = readChapterList(bookUrl)
        if (chapters.isEmpty() || refresh) {
            val cachedChapters = chapters
            val book = readList("books").firstOrNull { it.string("bookUrl") == bookUrl }
                ?: return ReturnData.error("Book not found")
            val source = sourceForBook(book) ?: return ReturnData.error("This book has no portable remote source rule")
            runPortablePreUpdate(book, source)
            val refreshedChapters = loadRemoteChapters(book, source)
            if (refreshedChapters.isNotEmpty()) {
                chapters = refreshedChapters
                writeChapterList(bookUrl, chapters)
                updateBookChapterCount(bookUrl, chapters.size)
            } else if (cachedChapters.isNotEmpty()) {
                chapters = cachedChapters
            }
        }
        return if (chapters.isEmpty()) {
            ReturnData.error("No chapters found; this source may require an unsupported JavaScript, CSS, or XPath rule")
        } else {
            ReturnData.ok(chapters.map { it.withoutInternalContent() })
        }
    }

    private fun runPortablePreUpdate(book: JsonObject, source: JsonObject) {
        val script = source["ruleToc"].asObjectOrNull()?.string("preUpdateJs")?.trim().orEmpty()
        if (script.isBlank()) return
        val input = gson.toJson(book)
        val output = withSourceRuleContext(source) {
            withJavaScriptRuleContext(book.string("name").orEmpty(), book.string("tocUrl").orEmpty()) {
                evaluateJavaScriptRule(input, if (isJavaScriptRule(script)) script else "@js:$script").asValue().trim()
            }
        }
        if (output.isBlank()) return
        val rawTocUrl = runCatching { JsonParser.parseString(output).asObjectOrNull()?.string("tocUrl") }.getOrNull()
            ?: output
        val updatedTocUrl = resolveSearchUrl(book.string("bookUrl").orEmpty(), rawTocUrl.trim())
        if (!updatedTocUrl.startsWith("http://", true) && !updatedTocUrl.startsWith("https://", true)) return
        book.addProperty("tocUrl", updatedTocUrl)
        val books = readList("books")
        books.indexOfFirst { it.string("bookUrl") == book.string("bookUrl") }
            .takeIf { it >= 0 }
            ?.let { index ->
                books[index].addProperty("tocUrl", updatedTocUrl)
                books[index].addProperty("tocUrlUpdatedAt", System.currentTimeMillis())
                writeList("books", books)
            }
    }

    @Synchronized
    fun saveChapterList(bookUrlParameter: String?, postData: String?): ReturnData {
        val data = parseJson(postData) ?: return ReturnData.error("Request body is required")
        val chapters = data.asArrayOrNull() ?: return ReturnData.error("Expected JSON array")
        val bookUrl = bookUrlParameter
            ?: chapters.firstOrNull()?.asObjectOrNull()?.string("bookUrl")
            ?: return ReturnData.error("Book url is required")
        writeChapterList(bookUrl, chapters.mapNotNull { it.asObjectOrNull() })
        updateBookChapterCount(bookUrl, chapters.size())
        return ReturnData.ok(chapters)
    }

    @Synchronized
    fun getBookContent(bookUrl: String?, index: Int?): ReturnData {
        if (bookUrl.isNullOrBlank()) return ReturnData.error("Parameter url is required")
        if (index == null) return ReturnData.error("Parameter index is required")
        var chapters = readChapterList(bookUrl)
        if (chapters.isEmpty()) {
            val result = getChapterList(bookUrl)
            if (!result.isSuccess) return result
            chapters = readChapterList(bookUrl)
        }
        val chapter = chapters.firstOrNull { it["index"].safeInt() == index }
            ?: return ReturnData.error("Chapter not found")
        chapter.string("content")?.let { return ReturnData.ok(it) }
        val book = readList("books").firstOrNull { it.string("bookUrl") == bookUrl }
            ?: return ReturnData.error("Book not found")
        val source = sourceForBook(book) ?: return ReturnData.error("This book has no portable remote source rule")
        val content = loadRemoteChapterContent(chapter, source)
            ?: return ReturnData.error("Unable to load chapter content with this source rule")
        chapter.addProperty("content", content)
        writeChapterList(bookUrl, chapters)
        return ReturnData.ok(content)
    }

    private fun sourceForBook(book: JsonObject): JsonObject? {
        val origin = book.string("origin").orEmpty()
        if (origin.isBlank() || origin == "local") return null
        return readList("bookSources").firstOrNull { it.string("bookSourceUrl") == origin }
    }

    private fun loadRemoteChapters(book: JsonObject, source: JsonObject): List<JsonObject> {
        return withSourceRuleContext(source) {
            val rule = source["ruleToc"].asObjectOrNull() ?: return@withSourceRuleContext emptyList()
            val bookUrl = book.string("bookUrl").orEmpty()
            var pageUrl = resolveRemoteTocUrl(book, source)
            val visited = linkedSetOf<String>()
            val chapters = mutableListOf<JsonObject>()
            for (page in 0 until 5) {
                if (pageUrl.isBlank() || !visited.add(pageUrl)) break
                val response = fetchSourceText(source, pageUrl) ?: break
                extractRuleValues(response, rule.string("chapterList").orEmpty()).forEach { entry ->
                    val title = extractRuleValue(entry, rule.string("chapterName")).trim()
                    val chapterUrl = resolveSearchUrl(pageUrl, extractRuleValue(entry, rule.string("chapterUrl")).trim())
                    if (title.isBlank() || chapterUrl.isBlank() || chapters.any { it.string("url") == chapterUrl }) return@forEach
                    chapters += JsonObject().apply {
                        addProperty("url", chapterUrl)
                        addProperty("title", title)
                        addProperty("isVolume", sourceRuleBoolean(rule.string("isVolume")?.let { extractRuleValue(entry, it) }))
                        addProperty("baseUrl", pageUrl)
                        addProperty("bookUrl", bookUrl)
                        addProperty("index", chapters.size)
                        addProperty("isVip", sourceRuleBoolean(rule.string("isVip")?.let { extractRuleValue(entry, it) }))
                        addProperty("isPay", sourceRuleBoolean(rule.string("isPay")?.let { extractRuleValue(entry, it) }))
                        if (source["bookSourceType"].safeInt() in setOf(1, 2, 4)) addProperty("resourceUrl", chapterUrl)
                        extractRuleValue(entry, rule.string("chapterTag") ?: rule.string("updateTime"))
                            .trim()
                            .takeIf(String::isNotBlank)
                            ?.let { addProperty("tag", it) }
                    }
                }
                val next = rule.string("nextTocUrl")?.let { extractRuleValue(response, it).trim() }.orEmpty()
                if (next.isBlank()) break
                pageUrl = resolveSearchUrl(pageUrl, next)
            }
            formatChapterTitles(chapters, rule.string("formatJs"))
            chapters
        }
    }

    private fun formatChapterTitles(chapters: List<JsonObject>, formatJs: String?) {
        if (formatJs.isNullOrBlank()) return
        val script = if (isJavaScriptRule(formatJs)) formatJs else "@js:$formatJs"
        var gInt = 0
        chapters.forEachIndexed { index, chapter ->
            val title = chapter.string("title").orEmpty()
            if (title.isBlank()) return@forEachIndexed
            val result = withJavaScriptRuleContext("", chapter.string("baseUrl").orEmpty(), index + 1, gInt) {
                evaluateJavaScriptRule(title, script, includeGInt = true)
            }
            val objectResult = result.json
                ?.let { runCatching { JsonParser.parseString(it).asObjectOrNull() }.getOrNull() }
            val formatted = objectResult?.string("value")?.trim().orEmpty()
                .ifBlank { result.asValue().trim() }
            if (formatted.isNotBlank()) chapter.addProperty("title", formatted)
            objectResult?.get("gInt")?.safeIntOrNull()?.let { gInt = it }
        }
    }

    private fun loadRemoteChapterContent(chapter: JsonObject, source: JsonObject): String? {
        return withSourceRuleContext(source) {
            val rules = source["ruleContent"].asObjectOrNull()
                ?: return@withSourceRuleContext mediaChapterContent(
                    chapter.string("resourceUrl") ?: chapter.string("url").orEmpty(),
                    chapter.string("url").orEmpty(),
                    source["bookSourceType"].safeInt(),
                ) ?: fetchSourceText(source, chapter.string("url").orEmpty())
            val contentRule = rules.string("content")
            val subContentRule = rules.string("subContent")
            val titleRule = rules.string("title")
            val nextRule = rules.string("nextContentUrl")
            val replaceRule = rules.string("replaceRegex")
            var pageUrl = chapter.string("url").orEmpty()
            val visited = linkedSetOf<String>()
            val pages = mutableListOf<String>()
            val mediaType = source["bookSourceType"].safeInt()
            if (contentRule.isNullOrBlank() && mediaType in setOf(1, 2, 4)) {
                return@withSourceRuleContext mediaChapterContent(
                    chapter.string("resourceUrl") ?: pageUrl,
                    pageUrl,
                    mediaType,
                )
            }
            for (attempt in 0 until 5) {
                if (pageUrl.isBlank() || !visited.add(pageUrl)) break
                val response = fetchSourceText(source, pageUrl) ?: break
                val mainContent = if (contentRule.isNullOrBlank()) response else extractRuleValue(response, contentRule)
                val subContent = subContentRule?.let { extractRuleValue(response, it) }.orEmpty()
                val content = listOf(mainContent, subContent).filter(String::isNotBlank).joinToString("\n\n")
                content.trim().takeIf(String::isNotBlank)?.let { extracted ->
                    pages += mediaChapterContent(extracted, pageUrl, mediaType) ?: extracted
                }
                if (titleRule != null) {
                    extractRuleValue(response, titleRule).trim().takeIf(String::isNotBlank)
                        ?.let { chapter.addProperty("title", it) }
                }
                val next = nextRule?.let { extractRuleValue(response, it).trim() }.orEmpty()
                pageUrl = resolveSearchUrl(pageUrl, next)
                if (next.isBlank()) break
            }
            applyContentReplaceRules(pages.joinToString("\n\n"), replaceRule).takeIf(String::isNotBlank)
        }
    }

    private fun mediaChapterContent(content: String, baseUrl: String, sourceType: Int): String? {
        if (sourceType !in setOf(1, 2, 4)) return null
        val value = content.trim()
        if (value.isBlank()) return null
        if (Regex("""<\s*(?:img|audio|video)\b""", RegexOption.IGNORE_CASE).containsMatchIn(value)) {
            return absolutizeMediaSources(value, baseUrl)
        }
        if (value.contains('\n') || value.contains('<') || value.contains('>')) return null
        val url = resolveSearchUrl(baseUrl, value).substringBefore(",{").trim()
        if (!url.startsWith("http://", true) && !url.startsWith("https://", true)) return null
        val escapedUrl = xmlEscape(url)
        return when (sourceType) {
            1 -> "<audio controls preload=\"metadata\" src=\"$escapedUrl\"></audio>"
            2 -> "<img src=\"$escapedUrl\"/>"
            4 -> "<video controls preload=\"metadata\" src=\"$escapedUrl\"></video>"
            else -> null
        }
    }

    private fun absolutizeMediaSources(content: String, baseUrl: String): String = runCatching {
        val document = Jsoup.parseBodyFragment(content)
        document.select("img[src], audio[src], video[src], source[src]").forEach { element ->
            val source = element.attr("src").trim()
            if (source.isNotBlank()) element.attr("src", resolveSearchUrl(baseUrl, source).substringBefore(",{"))
        }
        document.body().html()
    }.getOrDefault(content)

    private fun applyContentReplaceRules(content: String, rawRules: String?): String {
        if (content.isBlank() || rawRules.isNullOrBlank()) return content
        val parsed = runCatching { JsonParser.parseString(rawRules) }.getOrNull()
        val rules = when {
            parsed?.isJsonObject == true -> listOf(parsed.asJsonObject)
            parsed?.isJsonArray == true -> parsed.asJsonArray.mapNotNull { it.asObjectOrNull() }
            else -> emptyList()
        }
        if (rules.isNotEmpty()) return rules.take(20).fold(content) { value, rule ->
            val pattern = rule.string("pattern") ?: rule.string("regex") ?: return@fold value
            val replacement = rule.string("replacement") ?: rule.string("replace") ?: ""
            runCatching { Regex(pattern).replace(value, replacement) }.getOrDefault(value)
        }
        val parts = rawRules.split("##")
        if (parts.size < 3 || parts[0].isNotBlank()) return content
        val pattern = parts[1]
        val replacement = parts[2]
        if (pattern.isBlank()) return content
        return runCatching { Regex(pattern).replace(content, replacement) }.getOrDefault(content)
    }

    private fun sourceRuleBoolean(value: String?): Boolean {
        val normalized = value?.trim().orEmpty()
        return normalized.isNotEmpty() && !normalized.equals("null", true) &&
            !normalized.matches(Regex("^(false|no|not|0)$", RegexOption.IGNORE_CASE))
    }

    private fun resolveRemoteTocUrl(book: JsonObject, source: JsonObject): String {
        val fallback = book.string("tocUrl")?.ifBlank { book.string("bookUrl") }.orEmpty()
        val infoRule = source["ruleBookInfo"].asObjectOrNull()
        val tocRule = infoRule?.string("tocUrl")
        if (tocRule.isNullOrBlank()) return fallback
        val detailUrl = book.string("bookUrl").orEmpty()
        if (fallback.isNotBlank() && fallback != detailUrl) return fallback
        val detail = fetchSourceText(source, detailUrl) ?: return fallback
        val extracted = extractRuleValue(extractBookInfoScope(detail, infoRule.string("init")), tocRule).trim()
        return resolveSearchUrl(detailUrl, extracted).ifBlank { fallback }
    }

    private fun fetchSourceText(source: JsonObject, rawUrl: String): String? {
        val requestUrl = parseSourceRequestUrl(expandSourceVariables(source, rawUrl)) ?: return null
        return try {
            val response = sendSourceResponse(source, requestUrl, Duration.ofSeconds(20)) ?: return null
            response.body().decodeSourceText(response.headers(), requestUrl.options).takeIf { response.statusCode() in 200..299 }
        } catch (_: Exception) {
            null
        }
    }

    private data class SourceRateState(
        var windowStart: Long = 0,
        var requestCount: Int = 0,
        var lastRequestAt: Long = 0,
    )

    private fun waitForSourceRate(source: JsonObject) {
        val rawRate = source.string("concurrentRate")?.trim().orEmpty()
        if (rawRate.isEmpty() || rawRate == "0") return
        val sourceKey = source.string("bookSourceUrl") ?: source.string("sourceUrl") ?: return
        val state = sourceRateLocks.computeIfAbsent(sourceKey) { SourceRateState() }
        synchronized(state) {
            val now = System.currentTimeMillis()
            val ratio = Regex("^(\\d+)\\s*/\\s*(\\d+)$").matchEntire(rawRate)
            if (ratio != null) {
                val maximum = ratio.groupValues[1].toIntOrNull()?.coerceIn(1, 1000) ?: return
                val windowMillis = ratio.groupValues[2].toLongOrNull()?.coerceIn(1, 3_600_000) ?: return
                if (state.windowStart == 0L || now - state.windowStart >= windowMillis) {
                    state.windowStart = now
                    state.requestCount = 0
                }
                if (state.requestCount >= maximum) {
                    Thread.sleep((windowMillis - (now - state.windowStart)).coerceAtLeast(1))
                    state.windowStart = System.currentTimeMillis()
                    state.requestCount = 0
                }
                state.requestCount += 1
                return
            }
            val interval = rawRate.toLongOrNull()?.coerceIn(0, 3_600_000) ?: return
            val delay = interval - (now - state.lastRequestAt)
            if (delay > 0) Thread.sleep(delay)
            state.lastRequestAt = System.currentTimeMillis()
        }
    }

    private fun sourceRequestBody(options: JsonObject?): String {
        val body = options?.get("body") ?: return ""
        return if (body.isJsonPrimitive && body.asJsonPrimitive.isString) body.asString else gson.toJson(body)
    }

    private fun sourceRequestCharset(options: JsonObject?): Charset = options?.string("charset")
        ?.trim()
        ?.takeIf(String::isNotBlank)
        ?.let { runCatching { Charset.forName(it) }.getOrNull() }
        ?: Charsets.UTF_8

    private fun sourceRequestBuilder(source: JsonObject, requestUrl: SourceRequestUrl, timeout: Duration): HttpRequest.Builder {
        val builder = HttpRequest.newBuilder(URI.create(requestUrl.url))
            .timeout(timeout)
            .header("User-Agent", networkUserAgent())
        applySourceHeaders(builder, source)
        applySourceCookies(builder, source)
        requestUrl.options?.get("headers")?.asObjectOrNull()?.entrySet()?.forEach { (name, value) ->
            if (name.isNotBlank()) builder.header(name, value.asString)
        }
        val method = requestUrl.options?.string("method")?.uppercase()?.takeIf(String::isNotBlank) ?: "GET"
        return when (method) {
            "GET" -> builder.GET()
            "POST", "PUT", "PATCH", "DELETE" -> builder.method(
                method,
                HttpRequest.BodyPublishers.ofString(sourceRequestBody(requestUrl.options), sourceRequestCharset(requestUrl.options)),
            )
            else -> builder.GET()
        }
    }

    private fun sendSourceResponse(
        source: JsonObject,
        requestUrl: SourceRequestUrl,
        timeout: Duration,
    ): HttpResponse<ByteArray>? {
        val retries = requestUrl.options?.get("retry")?.safeIntOrNull()?.coerceIn(0, 3) ?: 0
        repeat(retries + 1) { attempt ->
            try {
                waitForSourceRate(source)
                val response = networkClient().send(
                    sourceRequestBuilder(source, requestUrl, timeout).build(),
                    HttpResponse.BodyHandlers.ofByteArray(),
                )
                captureSourceCookies(response, source)
                if (response.statusCode() in 200..299 || attempt == retries) return response
            } catch (_: Exception) {
                if (attempt == retries) return null
            }
        }
        return null
    }

    private fun ByteArray.decodeSourceText(headers: java.net.http.HttpHeaders, options: JsonObject?): String {
        val declared = headers.firstValue("content-type").orElse("")
            .let { Regex("charset=([^;\\s]+)", RegexOption.IGNORE_CASE).find(it)?.groupValues?.getOrNull(1) }
        val charset = options?.string("charset")
            ?.trim()
            ?.takeIf(String::isNotBlank)
            ?.let { runCatching { Charset.forName(it) }.getOrNull() }
            ?: declared?.let { runCatching { Charset.forName(it.trim('"', '\'')) }.getOrNull() }
            ?: Charsets.UTF_8
        return toString(charset)
    }

    private data class SourceRequestUrl(val url: String, val options: JsonObject?)

    private fun sourceRequestMethod(requestUrl: SourceRequestUrl): String =
        requestUrl.options?.string("method")?.uppercase()?.takeIf(String::isNotBlank) ?: "GET"

    private fun parseSourceRequestUrl(rawUrl: String): SourceRequestUrl? {
        val separator = rawUrl.indexOf(",{")
        val url = (if (separator >= 0) rawUrl.substring(0, separator) else rawUrl).trim()
        if (!url.startsWith("http://", true) && !url.startsWith("https://", true)) return null
        val options = if (separator >= 0) {
            runCatching { JsonParser.parseString(rawUrl.substring(separator + 1)).asObjectOrNull() }.getOrNull()
        } else null
        return SourceRequestUrl(url, options)
    }

    @Synchronized
    fun addLocalBook(parameters: Map<String, List<String>>, files: Map<String, String>): ReturnData {
        val fileName = parameters["fileName"]?.firstOrNull()
            ?: return ReturnData.error("fileName is required")
        val fileData = files["fileData"]
            ?: return ReturnData.error("fileData is required")
        val source = Paths.get(fileData)
        if (!source.isRegularFile()) return ReturnData.error("Uploaded file not found")

        if (Files.size(source) > MAX_LOCAL_BOOK_BYTES) {
            return ReturnData.error("Local books must be 64 MiB or smaller")
        }
        val bytes = Files.readAllBytes(source)
        val displayName = fileName.replace('\\', '/').substringAfterLast('/').take(180)
        if (displayName.isBlank()) return ReturnData.error("fileName is required")
        val extension = displayName.substringAfterLast('.', "").lowercase()
        if (extension !in setOf("txt", "epub", "cbz")) {
            return ReturnData.error("Only TXT, EPUB, and CBZ files are supported")
        }
        val baseName = displayName.substringBeforeLast('.', displayName)
        val backup = readAppSettings()["backup"].asObjectOrNull() ?: JsonObject()
        val importPattern = backup.string("bookImportFileName")?.trim().orEmpty()
        val parsed = if (importPattern.isNotEmpty()) {
            try { importPattern.toRegex().matchEntire(baseName) } catch (_: Exception) { null }
        } else null
        val hash = sha256(bytes).take(16)
        val bookUrl = "local://$hash/$displayName"
        val epub = if (extension == "epub") parseEpubBook(source, bookUrl) else null
        val comic = if (extension == "cbz") parseCbzBook(source, bookUrl) else null
        if (extension == "epub" && epub == null) {
            return ReturnData.error("Unable to read this EPUB file")
        }
        if (extension == "cbz" && comic == null) return ReturnData.error("Unable to read this CBZ file")
        val name = parsed?.groups?.get("name")?.value?.trim().orEmpty()
            .ifEmpty { epub?.title.orEmpty() }
            .ifEmpty { baseName }
        val author = parsed?.groups?.get("author")?.value?.trim().orEmpty()
            .ifEmpty { epub?.author.orEmpty() }
        val configuredDir = backup.string("defaultBookTreeUri")?.trim().orEmpty()
        val targetDir = if (configuredDir.isEmpty()) booksDir else {
            val path = Paths.get(configuredDir)
            if (path.isAbsolute) path else dataDir.resolve(path)
        }.toAbsolutePath().normalize()
        Files.createDirectories(targetDir)
        val safeFileName = displayName.replace(Regex("[^\\p{L}\\p{N}._ -]"), "_")
        val target = targetDir.resolve("$hash-$safeFileName")
        Files.write(target, bytes)

        val chapters = epub?.chapters ?: comic?.chapters ?: splitTextBook(bookUrl, bytes.toString(StandardCharsets.UTF_8))
        writeChapterList(bookUrl, chapters)

        val book = JsonObject().apply {
            addProperty("bookUrl", bookUrl)
            addProperty("tocUrl", bookUrl)
            addProperty("origin", "local")
            addProperty("originName", displayName)
            addProperty("name", name)
            addProperty("author", author)
            addProperty("type", 0)
            addProperty("localFile", target.toAbsolutePath().normalize().toString())
            addProperty("managedLocalFile", true)
            epub?.coverUrl?.takeIf(String::isNotBlank)?.let { addProperty("coverUrl", it) }
            comic?.coverUrl?.takeIf(String::isNotBlank)?.let { addProperty("coverUrl", it) }
            withBookDefaults()
            addProperty("totalChapterNum", chapters.size)
            addProperty("latestChapterTitle", chapters.lastOrNull()?.string("title") ?: "")
        }
        upsert("books", "bookUrl", book)
        return ReturnData.ok(book)
    }

    private data class EpubBook(
        val title: String,
        val author: String,
        val chapters: List<JsonObject>,
        val coverUrl: String,
    )

    private data class CbzBook(val chapters: List<JsonObject>, val coverUrl: String)

    private fun parseCbzBook(file: Path, bookUrl: String): CbzBook? = runCatching {
        ZipFile(file.toFile(), StandardCharsets.UTF_8).use { zip ->
            var totalBytes = 0
            val pages = zip.entries().asSequence()
                .filter { !it.isDirectory && epubImageMime(it.name) != null }
                .sortedWith { left, right -> naturalArchivePathCompare(left.name, right.name) }
                .take(500)
                .mapIndexedNotNull { index, entry ->
                    if (entry.size !in 1..MAX_EPUB_IMAGE_BYTES.toLong()) return@mapIndexedNotNull null
                    val bytes = zip.getInputStream(entry).use { it.readNBytes(MAX_EPUB_IMAGE_BYTES + 1) }
                    if (bytes.isEmpty() || bytes.size > MAX_EPUB_IMAGE_BYTES || totalBytes + bytes.size > 32 * 1024 * 1024) return@mapIndexedNotNull null
                    totalBytes += bytes.size
                    val image = "data:${epubImageMime(entry.name)};base64,${Base64.getEncoder().encodeToString(bytes)}"
                    chapter(bookUrl, index, "Page ${index + 1}", "<img src=\"$image\"/>")
                }
                .toList()
            pages.takeIf { it.isNotEmpty() }?.let { CbzBook(it, it.first().string("content").orEmpty().substringAfter("src=\"").substringBefore('"')) }
        }
    }.getOrNull()

    private fun naturalArchivePathCompare(left: String, right: String): Int {
        val leftParts = Regex("\\d+|\\D+").findAll(left.lowercase()).map { it.value }.toList()
        val rightParts = Regex("\\d+|\\D+").findAll(right.lowercase()).map { it.value }.toList()
        for (index in 0 until minOf(leftParts.size, rightParts.size)) {
            val a = leftParts[index]
            val b = rightParts[index]
            if (a == b) continue
            val comparison = if (a.all(Char::isDigit) && b.all(Char::isDigit)) {
                val normalizedA = a.trimStart('0').ifEmpty { "0" }
                val normalizedB = b.trimStart('0').ifEmpty { "0" }
                normalizedA.length.compareTo(normalizedB.length).takeIf { it != 0 }
                    ?: normalizedA.compareTo(normalizedB)
            } else a.compareTo(b)
            if (comparison != 0) return comparison
        }
        return leftParts.size.compareTo(rightParts.size).takeIf { it != 0 } ?: left.compareTo(right)
    }

    private fun parseEpubBook(file: Path, bookUrl: String): EpubBook? = runCatching {
        ZipFile(file.toFile(), StandardCharsets.UTF_8).use { zip ->
            val container = readZipText(zip, "META-INF/container.xml") ?: return@use null
            val containerDocument = Jsoup.parse(container, "", Parser.xmlParser())
            val packagePath = containerDocument.getAllElements()
                .firstOrNull { epubLocalName(it.tagName()) == "rootfile" }
                ?.attr("full-path")
                ?.let(::normalizeArchivePath)
                ?: zip.entries().asSequence()
                    .firstOrNull { entry ->
                        !entry.isDirectory && entry.name.endsWith(".opf", ignoreCase = true)
                    }
                    ?.name
                    ?.let(::normalizeArchivePath)
                ?: return@use null
            val packageDocument = Jsoup.parse(
                readZipText(zip, packagePath) ?: return@use null,
                "",
                Parser.xmlParser(),
            )
            val title = epubText(packageDocument, "title")
            val author = epubText(packageDocument, "creator")
            val manifest = packageDocument.getAllElements()
                .filter { epubLocalName(it.tagName()) == "item" }
                .mapNotNull { item ->
                    val id = item.attr("id").trim()
                    val href = item.attr("href").trim()
                    if (id.isEmpty() || href.isEmpty()) null else id to href
                }
                .toMap(LinkedHashMap())
            val coverReference = packageDocument.getAllElements()
                .firstOrNull {
                    epubLocalName(it.tagName()) == "meta" &&
                        it.attr("name").equals("cover", ignoreCase = true)
                }
                ?.attr("content")
                ?.trim()
                ?.let(manifest::get)
                ?: manifest["cover"]
                ?: packageDocument.getAllElements()
                    .firstOrNull {
                        epubLocalName(it.tagName()) == "item" &&
                            it.attr("properties").split(Regex("\\s+")).any { property ->
                                property.equals("cover-image", ignoreCase = true)
                            }
                    }
                    ?.attr("href")
                    ?.trim()
                    ?.takeIf(String::isNotBlank)
            val coverUrl = coverReference
                ?.let { resolveArchivePath(packagePath, it) }
                ?.let { inlineEpubImage(zip, it) }
            val navigationReference = packageDocument.getAllElements()
                .firstOrNull {
                    epubLocalName(it.tagName()) == "item" &&
                        it.attr("properties").split(Regex("\\s+")).any { property ->
                            property.equals("nav", ignoreCase = true)
                        }
                }
                ?.attr("href")
                ?.trim()
                ?.takeIf(String::isNotBlank)
                ?: manifest["nav"]
            val navigationTitles = navigationReference
                ?.let { resolveArchivePath(packagePath, it) }
                ?.let { navigationPath ->
                    readZipText(zip, navigationPath)?.let { navigation ->
                        Jsoup.parse(navigation, "", Parser.xmlParser()).select("a[href]")
                            .mapNotNull { link ->
                                resolveArchivePath(navigationPath, link.attr("href"))
                                    ?.let { path -> path to link.text().trim().takeIf(String::isNotBlank) }
                            }
                            .filter { it.second != null }
                            .associate { it.first to it.second.orEmpty() }
                    }
                }
                .orEmpty()
            val ncxReference = packageDocument.getAllElements()
                .firstOrNull { epubLocalName(it.tagName()) == "spine" }
                ?.attr("toc")
                ?.trim()
                ?.let(manifest::get)
                ?: packageDocument.getAllElements()
                    .firstOrNull {
                        epubLocalName(it.tagName()) == "item" &&
                            it.attr("media-type").equals("application/x-dtbncx+xml", ignoreCase = true)
                    }
                    ?.attr("href")
                    ?.trim()
                    ?.takeIf(String::isNotBlank)
            val ncxTitles = if (navigationTitles.isNotEmpty()) emptyMap() else ncxReference
                ?.let { resolveArchivePath(packagePath, it) }
                ?.let { ncxPath ->
                    readZipText(zip, ncxPath)?.let { ncx ->
                        Jsoup.parse(ncx, "", Parser.xmlParser()).getAllElements()
                            .filter { epubLocalName(it.tagName()) == "navpoint" }
                            .mapNotNull { point ->
                                val target = point.getAllElements()
                                    .firstOrNull { epubLocalName(it.tagName()) == "content" }
                                    ?.attr("src")
                                    ?.let { resolveArchivePath(ncxPath, it) }
                                val label = point.getAllElements()
                                    .firstOrNull { epubLocalName(it.tagName()) == "text" }
                                    ?.text()
                                    ?.trim()
                                if (target == null || label.isNullOrBlank()) null else target to label
                            }
                            .associate { it }
                    }
                }
                .orEmpty()
            val readingOrder = packageDocument.getAllElements()
                .filter { epubLocalName(it.tagName()) == "itemref" }
                .mapNotNull { item -> manifest[item.attr("idref").trim()] }
                .ifEmpty {
                    manifest.values.filter { href ->
                        href.substringBefore('#').lowercase().let { it.endsWith(".xhtml") || it.endsWith(".html") || it.endsWith(".htm") }
                    }
                }
                .distinct()
            val chapters = readingOrder.mapIndexedNotNull { index, href ->
                val entryPath = resolveArchivePath(packagePath, href) ?: return@mapIndexedNotNull null
                val chapterDocument = Jsoup.parse(
                    readZipText(zip, entryPath) ?: return@mapIndexedNotNull null,
                    "",
                    Parser.xmlParser(),
                )
                val body = chapterDocument.getAllElements()
                    .firstOrNull { epubLocalName(it.tagName()) == "body" }
                    ?: return@mapIndexedNotNull null
                inlineEpubImages(body, zip, entryPath)
                sanitizeEpubBody(body)
                val content = body.html().trim()
                if (content.isEmpty()) return@mapIndexedNotNull null
                val heading = body.getAllElements()
                    .firstOrNull { epubLocalName(it.tagName()) in setOf("h1", "h2", "h3") }
                    ?.text()
                    ?.trim()
                    .orEmpty()
                val chapterTitle = navigationTitles[entryPath].orEmpty()
                    .ifEmpty { ncxTitles[entryPath].orEmpty() }
                    .ifEmpty { heading }
                    .ifEmpty { href.substringAfterLast('/').substringBeforeLast('.') }
                chapter(bookUrl, index, chapterTitle, content)
            }
            if (chapters.isEmpty()) return@use null
            EpubBook(title, author, chapters, coverUrl.orEmpty())
        }
    }.getOrNull()

    private fun readZipText(zip: ZipFile, rawPath: String): String? {
        val path = normalizeArchivePath(rawPath) ?: return null
        val entry = zipEntry(zip, path)
            ?: return null
        if (entry.isDirectory || entry.size > MAX_EPUB_ENTRY_BYTES.toLong()) return null
        val bytes = zip.getInputStream(entry).use { input -> input.readNBytes(MAX_EPUB_ENTRY_BYTES + 1) }
        if (bytes.size > MAX_EPUB_ENTRY_BYTES) return null
        return bytes.toString(StandardCharsets.UTF_8)
    }

    private fun zipEntry(zip: ZipFile, path: String): ZipEntry? = zip.getEntry(path)
            ?: zip.entries().asSequence().firstOrNull { candidate ->
                candidate.name.replace('\\', '/') == path
            }

    private fun normalizeArchivePath(rawPath: String): String? {
        val value = rawPath.substringBefore('#').substringBefore('?').replace('\\', '/').trim()
        if (value.isEmpty() || value.startsWith('/')) return null
        val normalized = Paths.get(value).normalize().toString().replace('\\', '/')
        return normalized.takeIf { it.isNotEmpty() && it != "." && !it.startsWith("../") }
    }

    private fun resolveArchivePath(basePath: String, href: String): String? {
        val target = href.substringBefore('#').substringBefore('?').trim()
        if (target.isEmpty()) return null
        val base = basePath.substringBeforeLast('/', "")
        return normalizeArchivePath(if (base.isEmpty()) target else "$base/$target")
    }

    private fun epubLocalName(name: String): String = name.substringAfterLast(':').lowercase()

    private fun epubText(document: org.jsoup.nodes.Document, name: String): String = document.getAllElements()
        .firstOrNull { epubLocalName(it.tagName()) == name }
        ?.text()
        ?.trim()
        .orEmpty()

    private fun inlineEpubImages(body: org.jsoup.nodes.Element, zip: ZipFile, chapterPath: String) {
        var totalBytes = 0
        body.select("img").forEach { image ->
            val source = image.attr("src").trim()
            val imagePath = resolveArchivePath(chapterPath, source)
            val mime = imagePath?.let(::epubImageMime)
            val entry = imagePath?.let { zipEntry(zip, it) }
            if (mime == null || entry == null || entry.isDirectory || entry.size !in 1..MAX_EPUB_IMAGE_BYTES.toLong()) {
                image.remove()
                return@forEach
            }
            val bytes = zip.getInputStream(entry).use { it.readNBytes(MAX_EPUB_IMAGE_BYTES + 1) }
            if (bytes.isEmpty() || bytes.size > MAX_EPUB_IMAGE_BYTES || totalBytes + bytes.size > MAX_EPUB_CHAPTER_IMAGE_BYTES) {
                image.remove()
                return@forEach
            }
            totalBytes += bytes.size
            image.attr("src", "data:$mime;base64,${Base64.getEncoder().encodeToString(bytes)}")
        }
    }

    private fun inlineEpubImage(zip: ZipFile, path: String): String? {
        val mime = epubImageMime(path) ?: return null
        val entry = zipEntry(zip, path) ?: return null
        if (entry.isDirectory || entry.size !in 1..MAX_EPUB_IMAGE_BYTES.toLong()) return null
        val bytes = zip.getInputStream(entry).use { it.readNBytes(MAX_EPUB_IMAGE_BYTES + 1) }
        if (bytes.isEmpty() || bytes.size > MAX_EPUB_IMAGE_BYTES) return null
        return "data:$mime;base64,${Base64.getEncoder().encodeToString(bytes)}"
    }

    private fun epubImageMime(path: String): String? = when (path.substringAfterLast('.', "").lowercase()) {
        "jpg", "jpeg" -> "image/jpeg"
        "png" -> "image/png"
        "gif" -> "image/gif"
        "webp" -> "image/webp"
        "avif" -> "image/avif"
        else -> null
    }

    private fun sanitizeEpubBody(body: org.jsoup.nodes.Element) {
        body.select("script, style, iframe, frame, object, embed, form, svg, link, meta").remove()
        body.getAllElements().forEach { element ->
            element.attributes().asList()
                .filter { attribute ->
                    val key = attribute.key.lowercase()
                    key.startsWith("on") || key == "style" || key == "href" ||
                        (key == "src" && !(
                            epubLocalName(element.tagName()) == "img" &&
                                attribute.value.startsWith("data:image/", ignoreCase = true)
                            ))
                }
                .forEach { attribute -> element.removeAttr(attribute.key) }
        }
    }

    @Synchronized
    fun getReadConfig(): ReturnData {
        val file = dataDir.resolve("webReadConfig.json")
        return if (file.exists()) {
            ReturnData.ok(Files.readString(file))
        } else {
            ReturnData.error("No read config saved")
        }
    }

    @Synchronized
    fun saveReadConfig(postData: String?): ReturnData {
        val file = dataDir.resolve("webReadConfig.json")
        if (postData == null) {
            Files.deleteIfExists(file)
        } else {
            Files.writeString(file, postData)
        }
        return ReturnData.ok("")
    }

    private fun validateSource(item: JsonObject, key: String): String? {
        val nameKey = if (key == "bookSourceUrl") "bookSourceName" else "sourceName"
        return when {
            item.string(nameKey).isNullOrBlank() -> "$nameKey is required"
            item.string(key).isNullOrBlank() -> "$key is required"
            else -> null
        }
    }

    private fun upsert(kind: String, key: String, item: JsonObject) {
        val value = item.string(key) ?: error("$key is required")
        val list = readList(kind)
        val index = list.indexOfFirst { it.string(key) == value }
        if (index >= 0) {
            list[index] = item
        } else {
            list.add(item)
        }
        writeList(kind, list)
    }

    private fun updateBookChapterCount(bookUrl: String, count: Int) {
        val books = readList("books")
        val book = books.firstOrNull { it.string("bookUrl") == bookUrl } ?: return
        book.addProperty("totalChapterNum", count)
        writeList("books", books)
    }

    private fun readList(kind: String): MutableList<JsonObject> {
        val file = dataDir.resolve("$kind.json")
        if (!file.exists()) return mutableListOf()
        return Files.newBufferedReader(file, StandardCharsets.UTF_8).use { reader ->
            val parsed = JsonParser.parseReader(reader)
            parsed.asArrayOrNull()?.mapNotNull { it.asObjectOrNull() }?.toMutableList() ?: mutableListOf()
        }
    }

    private fun writeList(kind: String, values: List<JsonObject>) {
        val array = JsonArray()
        values.forEach(array::add)
        writeJson(dataDir.resolve("$kind.json"), array)
    }

    private fun readAppSettings(): JsonObject {
        val file = dataDir.resolve("appSettings.json")
        if (!file.exists()) return defaultAppSettings()
        val saved = Files.newBufferedReader(file, StandardCharsets.UTF_8).use { reader ->
            JsonParser.parseReader(reader).asObjectOrNull()
        } ?: JsonObject()
        return mergeDefaults(defaultAppSettings(), saved)
    }

    private fun writeAppSettings(settings: JsonObject) {
        writeJson(dataDir.resolve("appSettings.json"), settings)
    }

    fun webPortPreference(fallback: Int): Int = readAppSettings()["main"]
        .asObjectOrNull()
        ?.get("webPort")
        .safeIntOrNull()
        ?.takeIf { it in 1..65535 }
        ?: fallback

    fun networkUserAgent(): String {
        val configured = readAppSettings()["network"]
            .asObjectOrNull()
            ?.string("userAgent")
            ?.trim()
            .orEmpty()
        return configured.takeIf { it.isNotEmpty() }?.take(512) ?: "Legado-Server/1.0"
    }

    private fun localPassword(): String = readAppSettings()["network"].asObjectOrNull()?.string("localPassword")?.trim().orEmpty()

    private fun serverVersion(): String = System.getenv("LEGADO_SERVER_VERSION")
        ?.trim()
        ?.removePrefix("v")
        ?.takeIf(String::isNotBlank)
        ?: "dev"

    private fun compareVersion(left: String, right: String): Int {
        val leftParts = left.split(Regex("[^0-9]+"))
            .filter(String::isNotBlank)
            .map { it.toIntOrNull() ?: 0 }
        val rightParts = right.split(Regex("[^0-9]+"))
            .filter(String::isNotBlank)
            .map { it.toIntOrNull() ?: 0 }
        val size = maxOf(leftParts.size, rightParts.size)
        for (index in 0 until size) {
            val comparison = (leftParts.getOrElse(index) { 0 }).compareTo(rightParts.getOrElse(index) { 0 })
            if (comparison != 0) return comparison
        }
        return 0
    }

    private fun customHosts(): Map<String, List<String>> {
        val raw = readAppSettings()["network"]
            .asObjectOrNull()
            ?.string("customHosts")
            ?.trim()
            .orEmpty()
        if (raw.isBlank()) return emptyMap()
        val configured = runCatching { JsonParser.parseString(raw).asObjectOrNull() }.getOrNull()
            ?: return emptyMap()
        val hostPattern = Regex("^[A-Za-z0-9](?:[A-Za-z0-9.-]{0,251}[A-Za-z0-9])?$")
        val addressPattern = Regex("^[0-9A-Fa-f:.]+$")
        return configured.entrySet().mapNotNull { (host, value) ->
            val normalizedHost = host.trim().lowercase()
            if (!hostPattern.matches(normalizedHost)) return@mapNotNull null
            val addresses = when {
                value.isJsonArray -> value.asJsonArray.mapNotNull { element ->
                    runCatching { element.asString.trim() }.getOrNull()
                }
                value.isJsonPrimitive -> listOf(value.asString.trim())
                else -> emptyList()
            }.filter(addressPattern::matches).distinct()
            normalizedHost.takeIf { addresses.isNotEmpty() }?.let { it to addresses }
        }.toMap()
    }

    private fun applyCustomHostsPreference() {
        val target = dataDir.resolve("custom-hosts").toAbsolutePath().normalize()
        val entries = customHosts()
        if (entries.isEmpty()) {
            Files.deleteIfExists(target)
            System.clearProperty("jdk.net.hosts.file")
            return
        }
        val content = entries.entries.joinToString("\n", postfix = "\n") { (host, addresses) ->
            addresses.joinToString("\n") { address -> "$address $host" }
        }
        writeStringAtomic(target, content)
        System.setProperty("jdk.net.hosts.file", target.toString())
    }

    private fun heapDumpEnabled(): Boolean = readAppSettings()["maintenance"]
        .asObjectOrNull()
        ?.get("recordHeapDump")
        ?.safeBoolean()
        ?: false

    private fun applyHeapDumpPreference() {
        try {
            val enabled = heapDumpEnabled()
            val bean = ManagementFactory.getPlatformMXBean(HotSpotDiagnosticMXBean::class.java)
            bean.setVMOption("HeapDumpOnOutOfMemoryError", enabled.toString())
            if (enabled) {
                val directory = dataDir.resolve("heap-dumps").toAbsolutePath().normalize()
                Files.createDirectories(directory)
                bean.setVMOption("HeapDumpPath", directory.toString())
            }
        } catch (_: Exception) {
            // Non-HotSpot JVMs may not expose writable diagnostic options.
        }
    }

    private fun networkThreadCount(): Int = readAppSettings()["network"]
        .asObjectOrNull()
        ?.get("threadCount")
        .safeIntOrNull()
        ?.coerceIn(1, 32)
        ?: 16

    private fun networkCheckConfig(): String = readAppSettings()["network"]
        .asObjectOrNull()
        ?.string("checkSource")
        .orEmpty()

    private fun restoreIgnoreSet(): Set<String> = readAppSettings()["backup"]
        .asObjectOrNull()
        ?.string("restoreIgnore")
        .orEmpty()
        .split(',', ';', '\n', '\r')
        .map(String::trim)
        .filter(String::isNotEmpty)
        .toSet()

    private fun onlyLatestBackup(): Boolean = readAppSettings()["backup"]
        .asObjectOrNull()
        ?.get("onlyLatestBackup")
        ?.safeBoolean()
        ?: true

    fun imageCachePolicy(): ImageCachePolicy {
        val maintenance = readAppSettings()["maintenance"].asObjectOrNull()
        val megabytes = maintenance?.get("bitmapCacheSize")?.safeIntOrNull()?.coerceIn(0, 2048) ?: 50
        val entries = maintenance?.get("imageRetainNum")?.safeIntOrNull()?.coerceIn(0, 10000) ?: 0
        val expireAfterMillis = if (maintenance?.get("autoClearExpired")?.safeBoolean() != false) {
            Duration.ofHours(24).toMillis()
        } else {
            0L
        }
        return ImageCachePolicy(megabytes.toLong() * 1024 * 1024, entries, expireAfterMillis)
    }

    private fun compactJsonStores(): JsonCompactionResult {
        var files = 0
        var bytesSaved = 0L
        if (!dataDir.isDirectory()) return JsonCompactionResult(files, bytesSaved)
        Files.list(dataDir).use { paths ->
            paths.filter { path ->
                path.isRegularFile() && path.fileName.toString().endsWith(".json", ignoreCase = true)
            }.forEach { path ->
                try {
                    val before = Files.size(path)
                    val parsed = Files.newBufferedReader(path, StandardCharsets.UTF_8).use(JsonParser::parseReader)
                    writeStringAtomic(path, parsed.toString())
                    val after = Files.size(path)
                    files += 1
                    bytesSaved += (before - after).coerceAtLeast(0)
                } catch (_: Exception) {
                    // Keep maintenance best-effort when one data file is malformed or locked.
                }
            }
        }
        return JsonCompactionResult(files, bytesSaved)
    }

    private fun appDataKind(kindName: String?): AppDataKind? {
        if (kindName.isNullOrBlank()) return null
        return appDataKinds.firstOrNull { it.kind == kindName }
    }

    private fun exportAppData(): JsonObject {
        val data = JsonObject()
        appDataKinds.forEach { kind ->
            val array = JsonArray()
            readList(kind.kind).forEach(array::add)
            data.add(kind.kind, array)
        }
        return data
    }

    private fun backupDir(): Path {
        val configured = readAppSettings()["backup"]
            .asObjectOrNull()
            ?.string("backupUri")
            ?.takeIf { it.isNotBlank() }
        val path = if (configured == null) {
            dataDir.resolve("backups")
        } else {
            val configuredPath = Paths.get(configured)
            if (configuredPath.isAbsolute) configuredPath else dataDir.resolve(configuredPath)
        }
        return path.toAbsolutePath().normalize()
    }

    private fun listBackupFiles(): List<Path> {
        val dir = backupDir()
        if (!dir.isDirectory()) return emptyList()
        return Files.list(dir).use { stream ->
            stream
                .filter { it.isRegularFile() && it.fileName.toString().endsWith(".json", ignoreCase = true) }
                .sorted { left, right ->
                    Files.getLastModifiedTime(right).toMillis().compareTo(Files.getLastModifiedTime(left).toMillis())
                }
                .toList()
        }
    }

    private fun listBackups(): List<Map<String, Any>> = listBackupFiles().map(::backupEntry)

    private fun backupEntry(path: Path): Map<String, Any> {
        return mapOf(
            "fileName" to path.fileName.toString(),
            "path" to path.toAbsolutePath().normalize().toString(),
            "size" to Files.size(path),
            "modifiedTime" to Files.getLastModifiedTime(path).toMillis(),
        )
    }

    private fun backupFile(fileName: String?): Path? {
        if (fileName.isNullOrBlank()) return null
        if (fileName.contains('/') || fileName.contains('\\')) return null
        val dir = backupDir()
        val target = dir.resolve(fileName).normalize()
        return if (target.startsWith(dir)) target else null
    }

    private fun uploadBackupToWebDav(file: Path, backup: JsonObject): String? {
        val rawUrl = backup.string("web_dav_url")?.trim()?.trimEnd('/').orEmpty()
        if (rawUrl.isEmpty()) return null
        val account = backup.string("web_dav_account")?.trim().orEmpty()
        val password = backup.string("web_dav_password").orEmpty()
        val authorization = if (account.isNotEmpty() || password.isNotEmpty()) {
            "Basic " + Base64.getEncoder().encodeToString(
                "$account:$password".toByteArray(StandardCharsets.UTF_8)
            )
        } else null
        val segments = listOf(
            backup.string("webDavDir")?.trim().orEmpty(),
            backup.string("webDavDeviceName")?.trim().orEmpty(),
        ).filter(String::isNotEmpty)
        var current = rawUrl
        return try {
            for (segment in segments) {
                current += "/" + encodePathSegment(segment)
                val builder = HttpRequest.newBuilder(URI.create(current))
                    .timeout(Duration.ofSeconds(12))
                    .method("MKCOL", HttpRequest.BodyPublishers.noBody())
                    .header("User-Agent", networkUserAgent())
                authorization?.let { builder.header("Authorization", it) }
                val response = networkClient().send(builder.build(), HttpResponse.BodyHandlers.discarding())
                if (response.statusCode() !in 200..299 && response.statusCode() != 405) return null
            }
            val target = "$current/${encodePathSegment(file.fileName.toString())}"
            val builder = HttpRequest.newBuilder(URI.create(target))
                .timeout(Duration.ofSeconds(30))
                .PUT(HttpRequest.BodyPublishers.ofFile(file))
                .header("Content-Type", "application/json")
                .header("User-Agent", networkUserAgent())
            authorization?.let { builder.header("Authorization", it) }
            val response = networkClient().send(builder.build(), HttpResponse.BodyHandlers.discarding())
            if (response.statusCode() in 200..299) target else null
        } catch (_: Exception) {
            null
        }
    }

    private fun encodePathSegment(value: String): String =
        URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20")

    private fun sourceCheckCandidate(kind: String, source: JsonObject, nameKey: String, urlKey: String): JsonObject {
        return JsonObject().apply {
            addProperty("kind", kind)
            addProperty("sourceName", source.string(nameKey) ?: "")
            addProperty("sourceUrl", source.string(urlKey) ?: "")
            addProperty("enabled", source["enabled"]?.safeBoolean() != false)
        }
    }

    private fun checkSourceCandidate(candidate: JsonObject, timeoutMillis: Int, checkedAt: Long): JsonObject {
        val sourceUrl = candidate.string("sourceUrl").orEmpty()
        val started = System.nanoTime()
        val result = JsonObject().apply {
            addProperty("id", "${candidate.string("kind")}:$sourceUrl")
            addProperty("kind", candidate.string("kind") ?: "")
            addProperty("sourceName", candidate.string("sourceName") ?: "")
            addProperty("sourceUrl", sourceUrl)
            addProperty("enabled", candidate["enabled"]?.safeBoolean() != false)
            addProperty("checkedAt", checkedAt)
            addProperty("timeoutMillis", timeoutMillis)
        }

        if (!sourceUrl.startsWith("http://", ignoreCase = true) &&
            !sourceUrl.startsWith("https://", ignoreCase = true)
        ) {
            result.addProperty("ok", false)
            result.addProperty("message", "Only HTTP/HTTPS source URLs can be checked")
            result.addProperty("latencyMs", 0)
            return result
        }

        return try {
            val request = HttpRequest.newBuilder(URI.create(sourceUrl))
                .timeout(Duration.ofMillis(timeoutMillis.toLong()))
                .header("User-Agent", networkUserAgent())
                .GET()
                .build()
            val response = networkClient().send(request, HttpResponse.BodyHandlers.discarding())
            val latencyMs = ((System.nanoTime() - started) / 1_000_000).coerceAtLeast(0)
            val status = response.statusCode()
            result.apply {
                addProperty("statusCode", status)
                addProperty("latencyMs", latencyMs)
                addProperty("ok", status in 200..399)
                addProperty("message", if (status in 200..399) "OK" else "HTTP $status")
            }
        } catch (error: Exception) {
            val latencyMs = ((System.nanoTime() - started) / 1_000_000).coerceAtLeast(0)
            result.apply {
                addProperty("ok", false)
                addProperty("latencyMs", latencyMs)
                addProperty("message", error.message ?: error.javaClass.simpleName)
            }
        }
    }

    private fun JsonObject.withAppDataIdentity(kind: AppDataKind, index: Int): JsonObject {
        if (string(kind.primaryKey).isNullOrBlank()) {
            addProperty(
                kind.primaryKey,
                "${System.currentTimeMillis()}-${System.nanoTime()}-$index",
            )
        }
        return this
    }

    private fun mergeDefaults(defaults: JsonObject, saved: JsonObject): JsonObject {
        val merged = defaults.deepCopy()
        for ((key, value) in saved.entrySet()) {
            val defaultValue = merged[key]
            if (defaultValue != null && defaultValue.isJsonObject && value.isJsonObject) {
                merged.add(key, mergeDefaults(defaultValue.asJsonObject, value.asJsonObject))
            } else {
                merged.add(key, value.deepCopy())
            }
        }
        return merged
    }

    private fun defaultAppSettings(): JsonObject = jsonObject {
        obj("main") {
            put("language", "auto")
            put("defaultHomePage", "bookshelf")
            put("themeMode", "0")
            put("showDiscovery", true)
            put("showRss", true)
            put("auto_refresh", false)
            put("onlyUpdateRead", false)
            put("defaultToRead", false)
            put("webService", true)
            put("webServiceWakeLock", false)
            put("webPort", 1122)
            put("bookshelfLayout", 0)
            put("bookshelfSort", 0)
            put("bookGroupStyle", 0)
            put("bookshelfMargin", 12)
            put("showUnread", true)
            put("showLastUpdateTime", false)
            put("showWaitUpCount", false)
            put("showBookshelfFastScroller", false)
            put("openBookInfoByClickTitle", true)
            put("saveTabPosition", 0)
            put("enableReadRecord", true)
            put("searchScope", "")
            put("searchGroup", "")
            put("remoteServerId", 0)
        }
        obj("theme") {
            put("transparentStatusBar", true)
            put("immNavigationBar", true)
            put("barElevation", 4)
            put("fontScale", 100)
            put("launcherIcon", "ic_launcher")
            put("colorPrimary", "#795548")
            put("colorAccent", "#e53935")
            put("colorBackground", "#f5f5f5")
            put("colorBottomBackground", "#eeeeee")
            put("colorPrimaryNight", "#546e7a")
            put("colorAccentNight", "#bf360c")
            put("colorBackgroundNight", "#212121")
            put("colorBottomBackgroundNight", "#303030")
            put("durThemeName", "")
            put("durThemeNameNight", "")
            put("backgroundImage", "")
            put("backgroundImageBlurring", 0)
            put("backgroundImageNight", "")
            put("backgroundImageNightBlurring", 0)
            put("transparentNavBar", false)
            put("transparentNavBarNight", false)
            put("useDefaultCover", false)
            put("loadCoverOnlyWifi", false)
            put("coverRule", "")
            put("defaultCover", "")
            put("defaultCoverDark", "")
            put("coverShowName", true)
            put("coverShowAuthor", true)
            put("coverShowNameN", true)
            put("coverShowAuthorN", true)
        }
        obj("welcome") {
            put("welcomeShowTime", 500)
            put("customWelcome", false)
            put("welcomeImagePath", "")
            put("welcomeShowText", true)
            put("welcomeShowIcon", true)
            put("welcomeImagePathDark", "")
            put("welcomeShowTextDark", true)
            put("welcomeShowIconDark", true)
        }
        obj("read") {
            put("screenOrientation", "0")
            put("keep_light", "0")
            put("hideStatusBar", false)
            put("hideNavigationBar", false)
            put("readBodyToLh", true)
            put("paddingDisplayCutouts", false)
            put("doubleHorizontalPage", "0")
            put("progressBarBehavior", "page")
            put("useZhLayout", false)
            put("textFullJustify", true)
            put("textBottomJustify", true)
            put("adaptSpecialStyle", true)
            put("mouseWheelPage", true)
            put("volumeKeyPage", true)
            put("volumeKeyPageOnPlay", false)
            put("keyPageOnLongPress", false)
            put("pageTouchSlop", 0)
            put("pageTouchClick", 0)
            put("autoChangeSource", true)
            put("selectText", true)
            put("showBrightnessView", true)
            put("noAnimScrollPage", false)
            put("clickImgWay", "0")
            put("optimizeRender", false)
            put("disableReturnKey", false)
            put("expandTextMenu", false)
            put("showReadTitleAddition", true)
            put("readBarStyleFollowPage", false)
            put("preDownloadNum", 10)
            put("brightness", 100)
            put("nightBrightness", 100)
            put("brightnessVwPos", false)
            put("readUrlInBrowser", false)
            put("prevKeyCodes", "")
            put("nextKeyCodes", "")
            put("clickActionTopLeft", 2)
            put("clickActionTopCenter", 2)
            put("clickActionTopRight", 1)
            put("clickActionMiddleLeft", 2)
            put("clickActionMiddleCenter", 0)
            put("clickActionMiddleRight", 1)
            put("clickActionBottomLeft", 2)
            put("clickActionBottomCenter", 1)
            put("clickActionBottomRight", 1)
            put("autoReadSpeed", 10)
            put("readStyleSelect", 0)
            put("comicStyleSelect", 0)
            put("shareLayout", false)
            put("system_typefaces", 0)
            put("chineseConverterType", 0)
            put("contentReadAloudMod", 0)
        }
        obj("aloud") {
            put("ignoreAudioFocus", false)
            put("pauseReadAloudWhilePhoneCalls", false)
            put("readAloudWakeLock", false)
            put("audioPlayWakeLock", false)
            put("mediaButtonPerNext", false)
            put("mediaButtonOnExit", true)
            put("readAloudByMediaButton", false)
            put("readAloudByPage", false)
            put("streamReadAloudAudio", false)
            put("ttsFollowSys", true)
            put("ttsSpeechRate", 5)
            put("ttsTimer", 0)
            put("ttsEngine", "")
        }
        obj("backup") {
            put("web_dav_url", "")
            put("web_dav_account", "")
            put("web_dav_password", "")
            put("webDavDir", "legado")
            put("webDavDeviceName", "Linux Server")
            put("syncBookProgress", true)
            put("syncBookProgressPlus", false)
            put("backupUri", "")
            put("restoreIgnore", "")
            put("import_old", false)
            put("onlyLatestBackup", true)
            put("autoCheckNewBackup", true)
            put("exportCharset", "UTF-8")
            put("exportUseReplace", true)
            put("exportToWebDav", false)
            put("exportNoChapterName", false)
            put("enableCustomExport", false)
            put("exportType", 0)
            put("exportPictureFile", false)
            put("parallelExportBook", false)
            put("bookExportFileName", "")
            put("episodeExportFileName", "")
            put("bookImportFileName", "")
            put("defaultBookTreeUri", "")
            put("localBookImportSort", 0)
        }
        obj("network") {
            put("userAgent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome Safari/537.36")
            put("customHosts", "{}")
            put("localPassword", "")
            put("precisionSearch", false)
            put("Cronet", false)
            put("antiAlias", false)
            put("threadCount", 16)
            put("sourceEditMaxLine", 0)
            put("checkSource", "{}")
            put("uploadRule", "")
            put("replaceEnableDefault", true)
            put("importKeepName", false)
            put("importKeepGroup", false)
            put("importKeepEnable", false)
            put("importShowComment", false)
            put("changeSourceCheckAuthor", false)
            put("changeSourceLoadInfo", false)
            put("changeSourceLoadToc", false)
            put("changeSourceLoadWordCount", false)
            put("batchChangeSourceDelay", 0)
        }
        obj("manga") {
            put("showMangaUi", true)
            put("disableMangaScale", true)
            put("disableMangaPageAnim", false)
            put("mangaPreDownloadNum", 10)
            put("disableClickScroll", false)
            put("mangaAutoPageSpeed", 3)
            put("enableMangaHorizontalScroll", false)
            put("hideMangaTitle", false)
            put("enableMangaEInk", false)
            put("mangaEInkThreshold", 150)
            put("disableHorizontalPageSnap", false)
            put("enableMangaGray", false)
            put("mangaColorFilter", "")
            put("mangaFooterConfig", "")
        }
        obj("maintenance") {
            put("bitmapCacheSize", 50)
            put("imageRetainNum", 0)
            put("autoClearExpired", true)
            put("showAddToShelfAlert", true)
            put("updateToVariant", "default_version")
            put("autoUpdateVariant", true)
            put("recordLog", false)
            put("recordHeapDump", false)
            put("process_text", true)
            put("cleanCache", false)
            put("clearWebViewData", false)
            put("shrinkDatabase", false)
            put("cleanCacheOnSchedule", false)
            put("videoSetting", "{}")
            put("editFontScale", 16)
            put("editNonPrintable", 0)
            put("editAutoWrap", true)
            put("editAutoComplete", true)
            put("showBoardLine", 1)
            put("lastMaintenanceAt", "")
        }
    }

    private fun readChapterList(bookUrl: String): List<JsonObject> {
        val file = chaptersFile(bookUrl)
        if (!file.exists()) return emptyList()
        return Files.newBufferedReader(file, StandardCharsets.UTF_8).use { reader ->
            JsonParser.parseReader(reader).asArrayOrNull()?.mapNotNull { it.asObjectOrNull() } ?: emptyList()
        }
    }

    private fun writeChapterList(bookUrl: String, chapters: List<JsonObject>) {
        val array = JsonArray()
        chapters.forEach(array::add)
        writeJson(chaptersFile(bookUrl), array)
    }

    private fun chaptersFile(bookUrl: String): Path {
        val name = Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(bookUrl.toByteArray(StandardCharsets.UTF_8))
        return dataDir.resolve("chapters-$name.json")
    }

    private fun writeJson(path: Path, value: JsonElement) {
        Files.createDirectories(path.parent)
        val tmp = Files.createTempFile(path.parent, path.fileName.toString(), ".tmp")
        Files.newBufferedWriter(tmp, StandardCharsets.UTF_8).use { gson.toJson(value, it) }
        try {
            Files.move(tmp, path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE)
        } catch (_: AtomicMoveNotSupportedException) {
            Files.move(tmp, path, StandardCopyOption.REPLACE_EXISTING)
        }
    }

    private fun writeStringAtomic(path: Path, value: String) {
        Files.createDirectories(path.parent)
        val tmp = Files.createTempFile(path.parent, path.fileName.toString(), ".tmp")
        Files.writeString(tmp, value, StandardCharsets.UTF_8)
        try {
            Files.move(tmp, path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE)
        } catch (_: AtomicMoveNotSupportedException) {
            Files.move(tmp, path, StandardCopyOption.REPLACE_EXISTING)
        }
    }

    private fun parseJson(raw: String?): JsonElement? {
        if (raw.isNullOrBlank()) return null
        return JsonParser.parseString(raw)
    }

    private fun seedDefaultData() {
        seedDefaultFile("bookSources", "defaultData/bookSources.json")
        seedDefaultFile("rssSources", "defaultData/rssSources.json")
        seedDefaultFile("txtTocRules", "defaultData/txtTocRule.json")
    }

    private fun seedDefaultFile(kind: String, resourcePath: String) {
        val target = dataDir.resolve("$kind.json")
        if (target.exists()) return
        val stream = Thread.currentThread().contextClassLoader.getResourceAsStream(resourcePath) ?: return
        val bytes = stream.use { it.readBytes() }
        if (bytes.isEmpty()) return
        Files.write(target, bytes)
    }

    private fun splitTextBook(bookUrl: String, text: String): List<JsonObject> {
        val normalized = text.removePrefix("\uFEFF").replace("\r\n", "\n").replace('\r', '\n')
        val matches = chapterMatches(normalized)
        if (matches.isEmpty()) {
            return listOf(chapter(bookUrl, 0, "正文", normalized))
        }
        return matches.mapIndexed { index, match ->
            val contentEnd = matches.getOrNull(index + 1)?.start ?: normalized.length
            chapter(bookUrl, index, match.title, normalized.substring(match.end, contentEnd).trim())
        }
    }

    private fun chapterMatches(text: String): List<ChapterMatch> {
        val customRules = readList("txtTocRules")
            .filter { it["enable"]?.safeBoolean() != false }
            .mapNotNull { it.string("rule")?.takeIf(String::isNotBlank)?.toRegexOrNull() }
        val fallback = listOf(Regex("""^\s*(第[\d零〇一二三四五六七八九十百千万两]+[章回节卷].*)$"""))
        val rules = if (customRules.isEmpty()) fallback else customRules + fallback
        val matches = mutableListOf<ChapterMatch>()
        var offset = 0
        for (line in text.lineSequence()) {
            val title = line.trim()
            if (title.length in 2..80 && rules.any { it.containsMatchIn(title) }) {
                matches.add(ChapterMatch(offset, offset + line.length, title))
            }
            offset += line.length + 1
        }
        return matches.distinctBy { it.start }
    }

    private fun chapter(bookUrl: String, index: Int, title: String, content: String): JsonObject {
        return JsonObject().apply {
            addProperty("url", "local://chapter/$index")
            addProperty("title", title)
            addProperty("isVolume", false)
            addProperty("baseUrl", bookUrl)
            addProperty("bookUrl", bookUrl)
            addProperty("index", index)
            addProperty("isVip", false)
            addProperty("isPay", false)
            addProperty("content", content)
        }
    }
}

class StaticAssets(private val configuredRoot: Path?) {
    private val fallbackRoot = Paths.get("app", "src", "main", "assets", "web").toAbsolutePath().normalize()

    fun response(uri: String): NanoHTTPD.Response {
        val cleanPath = normalizeUri(uri)
        val root = configuredRoot?.toAbsolutePath()?.normalize()
            ?: fallbackRoot.takeIf { it.isDirectory() }
        if (root != null) {
            val response = fromFileSystem(root, cleanPath)
            if (response != null) return response
        }
        return fromResource(cleanPath) ?: NanoHTTPD.newFixedLengthResponse(
            NanoHTTPD.Response.Status.NOT_FOUND,
            "text/plain; charset=utf-8",
            "Not found",
        )
    }

    private fun fromFileSystem(root: Path, cleanPath: String): NanoHTTPD.Response? {
        var target = root.resolve(cleanPath).normalize()
        if (!target.startsWith(root)) return null
        if (target.isDirectory()) target = target.resolve("index.html")
        if (!target.isRegularFile()) return null
        return NanoHTTPD.newFixedLengthResponse(
            NanoHTTPD.Response.Status.OK,
            mimeType(target),
            Files.newInputStream(target),
            Files.size(target),
        )
    }

    private fun fromResource(cleanPath: String): NanoHTTPD.Response? {
        val resourcePath = "web/$cleanPath".let { if (it.endsWith("/")) "${it}index.html" else it }
        val stream = Thread.currentThread().contextClassLoader.getResourceAsStream(resourcePath) ?: return null
        val bytes = stream.use { it.readBytes() }
        return NanoHTTPD.newFixedLengthResponse(
            NanoHTTPD.Response.Status.OK,
            mimeType(Paths.get(resourcePath)),
            ByteArrayInputStream(bytes),
            bytes.size.toLong(),
        )
    }

    private fun normalizeUri(uri: String): String {
        var path = uri.substringBefore('?')
        path = URLDecoder.decode(path, StandardCharsets.UTF_8)
        path = path.trimStart('/')
        if (path.isBlank()) path = "index.html"
        if (path.endsWith('/')) path += "index.html"
        return path
    }
}

class LegadoWebSocketServer(
    host: String,
    port: Int,
    private val store: LegadoStore,
    private val gson: Gson,
) : NanoWSD(host, port) {
    override fun openWebSocket(handshake: IHTTPSession): WebSocket {
        val endpoint = handshake.uri.trim('/')
        val queryToken = handshake.parameters["token"]?.firstOrNull()?.trim().orEmpty()
        val authorization = handshake.headers["authorization"]
            ?: queryToken.takeIf(String::isNotBlank)?.let { "Bearer $it" }
        val authorized = !store.requiresLocalAuthentication() || store.isAuthenticated(authorization)
        return object : WebSocket(handshake) {
            override fun onOpen() {
                if (!authorized) {
                    sendResult(ReturnData.error("Unauthorized"))
                    close(WebSocketFrame.CloseCode.PolicyViolation, "Unauthorized", false)
                }
            }

            override fun onClose(
                code: WebSocketFrame.CloseCode?,
                reason: String?,
                initiatedByRemote: Boolean,
            ) = Unit

            override fun onMessage(message: WebSocketFrame) {
                if (!authorized) return
                when (endpoint) {
                    "searchBook" -> handleSearch(message.textPayload)

                    "bookSourceDebug" -> handleDebug(message.textPayload, "book")

                    "rssSourceDebug" -> handleDebug(message.textPayload, "rss")

                    else -> close(WebSocketFrame.CloseCode.UnsupportedData, "unknown endpoint", false)
                }
            }

            override fun onPong(pong: WebSocketFrame) = Unit

            override fun onException(exception: IOException) {
                exception.printStackTrace()
            }

            private fun handleSearch(payload: String) {
                val request = parseWebSocketObject(payload) ?: JsonObject().apply {
                    addProperty("key", payload.trim())
                }
                sendResult(store.searchBooks(gson.toJson(request)))
            }

            private fun handleDebug(payload: String, kind: String) {
                val request = parseWebSocketObject(payload)
                    ?: JsonObject().also { it.addProperty("sourceUrl", payload.trim()) }
                request.addProperty("kind", kind)
                sendResult(store.debugSource(gson.toJson(request)))
            }

            private fun sendResult(result: ReturnData) {
                runCatching { send(gson.toJson(result)) }
            }

            private fun parseWebSocketObject(payload: String): JsonObject? = runCatching {
                JsonParser.parseString(payload).asObjectOrNull()
            }.getOrNull()
        }
    }
}

private fun JsonElement?.asObjectOrNull(): JsonObject? = if (this != null && isJsonObject) asJsonObject else null

private fun JsonElement?.asArrayOrNull(): JsonArray? = if (this != null && isJsonArray) asJsonArray else null

private fun JsonObject.string(name: String): String? {
    val value = get(name) ?: return null
    if (value.isJsonNull) return null
    return runCatching { value.asString }.getOrNull()
}

private fun JsonObject.withBookDefaults() {
    val now = System.currentTimeMillis()
    default("tocUrl", "")
    default("origin", "local")
    default("originName", "")
    default("name", "")
    default("author", "")
    default("type", 0)
    default("group", 0)
    default("latestChapterTime", now)
    default("lastCheckTime", now)
    default("lastCheckCount", 0)
    default("totalChapterNum", 0)
    default("durChapterIndex", 0)
    default("durChapterPos", 0)
    default("durChapterTime", now)
    default("canUpdate", true)
    default("order", 0)
    default("originOrder", 0)
    default("syncTime", 0)
}

private fun JsonObject.default(name: String, value: String) {
    if (!has(name) || get(name).isJsonNull) addProperty(name, value)
}

private fun JsonObject.default(name: String, value: Number) {
    if (!has(name) || get(name).isJsonNull) addProperty(name, value)
}

private fun JsonObject.default(name: String, value: Boolean) {
    if (!has(name) || get(name).isJsonNull) addProperty(name, value)
}

private fun JsonObject.withoutInternalContent(): JsonObject {
    val copy = deepCopy()
    copy.remove("content")
    return copy
}

private fun JsonElement?.safeInt(): Int = safeIntOrNull() ?: 0

private fun JsonElement?.safeIntOrNull(): Int? {
    if (this == null || isJsonNull) return null
    return runCatching { asInt }.getOrNull()
}

private fun JsonElement?.safeLong(): Long {
    if (this == null || isJsonNull) return 0L
    return runCatching { asLong }.getOrDefault(0L)
}

private fun JsonElement.safeBoolean(): Boolean? {
    if (isJsonNull) return null
    return runCatching { asBoolean }.getOrNull()
}

private fun ZipOutputStream.writeUtf8Entry(name: String, content: String) {
    putNextEntry(ZipEntry(name))
    write(content.toByteArray(StandardCharsets.UTF_8))
    closeEntry()
}

private fun ZipOutputStream.writeEntry(name: String, bytes: ByteArray) {
    putNextEntry(ZipEntry(name))
    write(bytes)
    closeEntry()
}

private fun xmlEscape(value: String): String = value
    .replace("&", "&amp;")
    .replace("<", "&lt;")
    .replace(">", "&gt;")
    .replace("\"", "&quot;")
    .replace("'", "&apos;")

private fun jsonObject(block: JsonObjectBuilder.() -> Unit): JsonObject =
    JsonObjectBuilder().apply(block).build()

private class JsonObjectBuilder {
    private val value = JsonObject()

    fun put(name: String, property: String) = value.addProperty(name, property)

    fun put(name: String, property: Number) = value.addProperty(name, property)

    fun put(name: String, property: Boolean) = value.addProperty(name, property)

    fun obj(name: String, block: JsonObjectBuilder.() -> Unit) {
        value.add(name, jsonObject(block))
    }

    fun build(): JsonObject = value
}

private fun String.toRegexOrNull(): Regex? = runCatching { toRegex() }.getOrNull()

private fun mimeType(path: Path): String {
    val lower = path.name.lowercase()
    return when {
        lower.endsWith(".html") || lower.endsWith(".htm") -> "text/html; charset=utf-8"
        lower.endsWith(".js") -> "text/javascript; charset=utf-8"
        lower.endsWith(".css") -> "text/css; charset=utf-8"
        lower.endsWith(".json") -> "application/json; charset=utf-8"
        lower.endsWith(".svg") -> "image/svg+xml"
        lower.endsWith(".png") -> "image/png"
        lower.endsWith(".jpg") || lower.endsWith(".jpeg") -> "image/jpeg"
        lower.endsWith(".gif") -> "image/gif"
        lower.endsWith(".webp") -> "image/webp"
        lower.endsWith(".ico") -> "image/x-icon"
        lower.endsWith(".ttf") -> "font/ttf"
        lower.endsWith(".woff") -> "font/woff"
        lower.endsWith(".woff2") -> "font/woff2"
        else -> Files.probeContentType(path) ?: "application/octet-stream"
    }
}

private fun sha256(bytes: ByteArray): String {
    val digest = MessageDigest.getInstance("SHA-256").digest(bytes)
    return digest.joinToString("") { "%02x".format(it) }
}
