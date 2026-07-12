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
import java.util.concurrent.Callable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.zip.CRC32
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.xml.parsers.DocumentBuilderFactory
import org.xml.sax.InputSource
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.isRegularFile
import kotlin.io.path.name

fun main(args: Array<String>) {
    val options = ServerOptions.parse(args) ?: return
    Files.createDirectories(options.dataDir)

    val gson = GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create()
    val store = LegadoStore(options.dataDir, gson)
    val httpPort = if (options.portExplicit) options.port else store.webPortPreference(options.port)
    val wsPort = if (options.wsPortExplicit) options.wsPort else httpPort + 1
    val assets = StaticAssets(options.webRoot)
    val httpServer = LegadoHttpServer(options.host, httpPort, store, assets, gson)
    val wsServer = if (options.noWebSocket) null else LegadoWebSocketServer(options.host, wsPort)

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

            "/getServerInfo" -> store.getServerInfo()
            "/checkForUpdates" -> store.checkForUpdates()
            "/exportData" -> store.exportData()
            "/getBackups" -> store.getBackups()
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
            "/getChapterList", "/refreshToc" -> store.getChapterList(parameters.first("url"))
            "/getBookContent" -> store.getBookContent(
                parameters.first("url"),
                parameters.first("index")?.toIntOrNull(),
            )

            "/getReadConfig" -> store.getReadConfig()
            "/cover", "/image" -> throw ImageResponse(imageProxy.get(parameters.first("path")))
            else -> null
        }
    }

    private fun handlePost(session: IHTTPSession): ReturnData? {
        val post = parsePost(session)

        return when (session.uri) {
            "/saveBookSource" -> store.saveSource("bookSources", "bookSourceUrl", post.postData, single = true)
            "/saveBookSources" -> store.saveSource("bookSources", "bookSourceUrl", post.postData, single = false)
            "/deleteBookSources" -> store.deleteSources("bookSources", "bookSourceUrl", post.postData)
            "/saveRssSource" -> store.saveSource("rssSources", "sourceUrl", post.postData, single = true)
            "/saveRssSources" -> store.saveSource("rssSources", "sourceUrl", post.postData, single = false)
            "/deleteRssSources" -> store.deleteSources("rssSources", "sourceUrl", post.postData)
            "/saveReplaceRule" -> store.saveReplaceRule(post.postData)
            "/deleteReplaceRule" -> store.deleteReplaceRule(post.postData)
            "/testReplaceRule" -> store.testReplaceRule(post.postData)
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

class LegadoStore(
    val dataDir: Path,
    private val gson: Gson,
) {
    private val booksDir = dataDir.resolve("books")
    @Volatile private var sourceCheckClientMode = false
    @Volatile private var sourceCheckClient = buildNetworkClient(false)
    private val appDataKinds = listOf(
        AppDataKind("bookGroups", "书籍分组", "书架分组、排序、刷新策略", "groupId", "Web 可用"),
        AppDataKind("bookmarks", "书签摘录", "阅读器书签、划线、摘录内容", "time", "Web 可用"),
        AppDataKind("readRecords", "阅读记录", "最近阅读、阅读时长和入口历史", "id", "配置保留"),
        AppDataKind("httpTTS", "HTTP TTS", "在线朗读引擎、请求头和登录脚本", "id", "Linux 需实现"),
        AppDataKind("cookies", "Cookie 管理", "书源/RSS/HTTP TTS 登录 Cookie", "url", "Linux 需实现"),
        AppDataKind("dictRules", "字典规则", "划词字典查询规则", "name", "Linux 需实现"),
        AppDataKind("rssArticles", "RSS 文章缓存", "订阅文章列表、分组、阅读状态", "link", "配置保留"),
        AppDataKind("rssReadRecords", "RSS 阅读记录", "订阅阅读进度和已读记录", "record", "配置保留"),
        AppDataKind("rssStars", "RSS 收藏", "订阅文章收藏和星标", "link", "配置保留"),
        AppDataKind("cacheRecords", "缓存记录", "通用缓存、源变量和临时数据", "key", "配置保留"),
        AppDataKind("downloadTasks", "下载任务", "离线下载、缓存书籍、媒体下载队列", "id", "Linux 需实现"),
        AppDataKind("themeConfigs", "主题方案", "Android 主题方案列表", "themeName", "配置保留"),
        AppDataKind("readStyles", "阅读样式", "阅读排版方案、背景、字体和提示栏", "name", "Web 可用"),
    )

    init {
        Files.createDirectories(dataDir)
        Files.createDirectories(booksDir)
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
        val compactResult = if (maintenance["shrinkDatabase"]?.safeBoolean() == true) {
            compactJsonStores()
        } else {
            JsonCompactionResult(0, 0)
        }
        return ReturnData.ok(mapOf(
            "completedAt" to completedAt,
            "cacheEntriesCleared" to (cacheResult["entries"] ?: 0),
            "cacheBytesCleared" to (cacheResult["bytes"] ?: 0),
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
        return ReturnData.ok(readAppSettings())
    }

    @Synchronized
    fun resetAppSettings(): ReturnData {
        writeAppSettings(defaultAppSettings())
        applyCustomHostsPreference()
        applyHeapDumpPreference()
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
        return ReturnData.ok(readList(kind.kind))
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
        val kept = readList("books").filterNot { it.string("bookUrl") == bookUrl }
        writeList("books", kept)
        val chaptersFile = chaptersFile(bookUrl)
        if (chaptersFile.exists()) Files.delete(chaptersFile)
        return ReturnData.ok("")
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
        return ReturnData.ok("")
    }

    @Synchronized
    fun getChapterList(bookUrl: String?): ReturnData {
        if (bookUrl.isNullOrBlank()) return ReturnData.error("Parameter url is required")
        val chapters = readChapterList(bookUrl)
        return if (chapters.isEmpty()) {
            ReturnData.error("No chapters saved for this book")
        } else {
            ReturnData.ok(chapters.map { it.withoutInternalContent() })
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
        val chapter = readChapterList(bookUrl).firstOrNull { it["index"].safeInt() == index }
            ?: return ReturnData.error("Chapter not found")
        return ReturnData.ok(chapter.string("content") ?: "")
    }

    @Synchronized
    fun addLocalBook(parameters: Map<String, List<String>>, files: Map<String, String>): ReturnData {
        val fileName = parameters["fileName"]?.firstOrNull()
            ?: return ReturnData.error("fileName is required")
        val fileData = files["fileData"]
            ?: return ReturnData.error("fileData is required")
        val source = Paths.get(fileData)
        if (!source.isRegularFile()) return ReturnData.error("Uploaded file not found")

        val bytes = Files.readAllBytes(source)
        val displayName = fileName.replace('\\', '/').substringAfterLast('/')
        val baseName = displayName.substringBeforeLast('.', displayName)
        val backup = readAppSettings()["backup"].asObjectOrNull() ?: JsonObject()
        val importPattern = backup.string("bookImportFileName")?.trim().orEmpty()
        val parsed = if (importPattern.isNotEmpty()) {
            try { importPattern.toRegex().matchEntire(baseName) } catch (_: Exception) { null }
        } else null
        val name = parsed?.groups?.get("name")?.value?.trim().orEmpty().ifEmpty { baseName }
        val author = parsed?.groups?.get("author")?.value?.trim().orEmpty()
        val hash = sha256(bytes).take(16)
        val configuredDir = backup.string("defaultBookTreeUri")?.trim().orEmpty()
        val targetDir = if (configuredDir.isEmpty()) booksDir else {
            val path = Paths.get(configuredDir)
            if (path.isAbsolute) path else dataDir.resolve(path)
        }.toAbsolutePath().normalize()
        Files.createDirectories(targetDir)
        val target = targetDir.resolve("$hash-$displayName")
        Files.write(target, bytes)

        val text = bytes.toString(StandardCharsets.UTF_8)
        val bookUrl = "local://$hash/$displayName"
        val chapters = splitTextBook(bookUrl, text)
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
            withBookDefaults()
            addProperty("totalChapterNum", chapters.size)
            addProperty("latestChapterTitle", chapters.lastOrNull()?.string("title") ?: "")
        }
        upsert("books", "bookUrl", book)
        return ReturnData.ok(book)
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

class LegadoWebSocketServer(host: String, port: Int) : NanoWSD(host, port) {
    override fun openWebSocket(handshake: IHTTPSession): WebSocket {
        val endpoint = handshake.uri.trim('/')
        return object : WebSocket(handshake) {
            override fun onOpen() = Unit

            override fun onClose(
                code: WebSocketFrame.CloseCode?,
                reason: String?,
                initiatedByRemote: Boolean,
            ) = Unit

            override fun onMessage(message: WebSocketFrame) {
                when (endpoint) {
                    "searchBook" -> {
                        send("[]")
                        close(WebSocketFrame.CloseCode.NormalClosure, "search is not implemented in JVM server yet", false)
                    }

                    "bookSourceDebug", "rssSourceDebug" -> {
                        send("Rule debugging is not implemented in the JVM server yet.")
                        close(WebSocketFrame.CloseCode.NormalClosure, "debug is not implemented", false)
                    }

                    else -> close(WebSocketFrame.CloseCode.UnsupportedData, "unknown endpoint", false)
                }
            }

            override fun onPong(pong: WebSocketFrame) = Unit

            override fun onException(exception: IOException) {
                exception.printStackTrace()
            }
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
