// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.api

import com.htmake.reader.SpringEvent
import com.htmake.reader.api.controller.BookController
import com.htmake.reader.api.controller.BookGroupController
import com.htmake.reader.api.controller.BookSourceController
import com.htmake.reader.api.controller.BookmarkController
import com.htmake.reader.api.controller.FileController
import com.htmake.reader.api.controller.HttpTTSController
import com.htmake.reader.api.controller.LicenseController
import com.htmake.reader.api.controller.ReplaceRuleController
import com.htmake.reader.api.controller.RssSourceController
import com.htmake.reader.api.controller.UserController
import com.htmake.reader.api.controller.WebdavController
import com.htmake.reader.config.AppConfig
import com.htmake.reader.config.BookConfig
import com.htmake.reader.init.ReaderAdapter
import com.htmake.reader.utils.MongoManager
import com.htmake.reader.utils.RemoteWebview
import com.htmake.reader.utils.SpringContextUtils
import com.htmake.reader.utils.getInstalledLicense
import com.htmake.reader.utils.getTraceId
import com.htmake.reader.utils.getWorkDir
import com.htmake.reader.utils.success
import com.htmake.reader.verticle.RestVerticle
import io.legado.app.adapters.ReaderAdapterHelper
import io.vertx.core.net.impl.URIDecoder
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.StaticHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.slf4j.MDCContext
import mu.KLogger
import mu.KotlinLogging
import org.slf4j.MDC
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.File
import java.net.URLDecoder
import java.util.Calendar

private val logger: KLogger = KotlinLogging.logger { }

@Component
open class YueduApi : RestVerticle() {

    @Autowired
    private lateinit var appConfig: AppConfig

    @Autowired
    private lateinit var env: Environment

    override suspend fun initRouter(router: Router) {
        setupPort()
        if (appConfig.mongoUri.length > 0) {
            MongoManager.connect(appConfig.mongoUri)
        }
        if (appConfig.remoteWebviewApi.length > 0) {
            RemoteWebview.setRemoteApi(appConfig.remoteWebviewApi)
        }
        ReaderAdapterHelper.setAdapter(ReaderAdapter)
        migration()
        router.route("/*").handler(StaticHandler.create("web").setDefaultContentEncoding("UTF-8"))
        val assetsDir = getWorkDir("storage", "assets")
        val assetsDirFile = File(assetsDir)
        if (!assetsDirFile.exists()) {
            assetsDirFile.mkdirs()
        }
        val assetsCss = getWorkDir("storage", "assets", "reader.css")
        val assetsCssFile = File(assetsCss)
        if (!assetsCssFile.exists()) {
            assetsCssFile.writeText("/* 在此处可以编写CSS样式来自定义页面 */")
        }
        router.route("/assets/*").handler(
            StaticHandler.create().setAllowRootFileSystemAccess(true).setWebRoot(assetsDir)
                .setDefaultContentEncoding("UTF-8")
        )
        var dataDir = getWorkDir("storage", "data")
        router.route("/book-assets/*").handler { it ->
            var path = it.request().path().replace("/book-assets/", "/")
            path = URIDecoder.decodeURIComponent(path, false)
            if (path.endsWith("html", true) || path.endsWith("htm", true)) {
                val filePath = File(dataDir + path)
                if (filePath.exists()) {
                    BookConfig.injectJavascriptToEpubChapter(filePath.toString())
                }
            }
            it.next()
        }
        router.route("/book-assets/*").handler(
            StaticHandler.create().setAllowRootFileSystemAccess(true).setWebRoot(dataDir)
                .setDefaultContentEncoding("UTF-8")
        )
        router.route("/epub/*").handler { it ->
            var path = it.request().path().replace("/epub/", "/")
            path = URLDecoder.decode(path, "UTF-8")
            if (path.endsWith("html", true)) {
                val filePath = File(dataDir + path)
                if (filePath.exists()) {
                    BookConfig.injectJavascriptToEpubChapter(filePath.toString())
                }
            }
            it.next()
        }
        router.route("/epub/*").handler(
            StaticHandler.create().setAllowRootFileSystemAccess(true).setWebRoot(dataDir)
                .setDefaultContentEncoding("UTF-8")
        )
        router.route("/simple-web").handler { it ->
            val path = it.request().path()
            if (path.endsWith("/simple-web", false)) {
                it.response().putHeader(
                    "Location",
                    URLDecoder.decode(it.request().absoluteURI(), "UTF-8")
                        .replace("/simple-web", "/simple-web/")
                ).setStatusCode(302).end()
            } else {
                it.next()
            }
        }
        router.route("/simple-web/*").handler { it ->
            val license = getInstalledLicense()
            var simpleWebExpiredAt = 0L
            if (license.validHost(it.request().host())) {
                simpleWebExpiredAt = license.simpleWebExpiredAt
            }
            if (simpleWebExpiredAt != 0L && simpleWebExpiredAt < System.currentTimeMillis()) {
                it.response().putHeader("content-type", "text/html; charset=UTF-8")
                    .setStatusCode(403).end("<html><head><title>未激活该功能</title></head><body><div style='text-align: center;padding: 30px 0;'>未激活该功能，请加<a href='https://t.me/+pQ8HDlANPZ84ZWNl'>TG群</a>激活</div></body></html>")
            } else {
                it.next()
            }
        }
        router.route("/simple-web/*").handler(
            StaticHandler.create("simple-web").setDefaultContentEncoding("UTF-8")
        )
        router.get("/reader3/getSystemInfo").coroutineHandler { it ->
            getSystemInfo(it)
        }
        val bookController = BookController(coroutineContext)
        val bookGroupController = BookGroupController(coroutineContext)
        val bookSourceController = BookSourceController(coroutineContext)
        val rssSourceController = RssSourceController(coroutineContext)
        val userController = UserController(coroutineContext)
        val webdavController = WebdavController(coroutineContext, router) { ctx, error ->
            onHandlerError(ctx, error)
        }
        val replaceRuleController = ReplaceRuleController(coroutineContext)
        val bookmarkController = BookmarkController(coroutineContext)
        val fileController = FileController(coroutineContext)
        val licenseController = LicenseController(coroutineContext)
        val httpTTSController = HttpTTSController(coroutineContext)
        router.post("/reader3/saveBookSource").coroutineHandler { it ->
            bookSourceController.saveBookSource(it)
        }
        router.post("/reader3/saveBookSources").coroutineHandler { it ->
            bookSourceController.saveBookSources(it)
        }
        router.get("/reader3/getBookSource").coroutineHandler { it ->
            bookSourceController.getBookSource(it)
        }
        router.post("/reader3/getBookSource").coroutineHandler { it ->
            bookSourceController.getBookSource(it)
        }
        router.get("/reader3/getBookSources").coroutineHandler { it ->
            bookSourceController.getBookSources(it)
        }
        router.post("/reader3/getBookSources").coroutineHandler { it ->
            bookSourceController.getBookSources(it)
        }
        router.post("/reader3/deleteAllBookSources").coroutineHandler { it ->
            bookSourceController.deleteAllBookSources(it)
        }
        router.post("/reader3/deleteBookSource").coroutineHandler { it ->
            bookSourceController.deleteBookSource(it)
        }
        router.post("/reader3/deleteBookSources").coroutineHandler { it ->
            bookSourceController.deleteBookSources(it)
        }
        router.post("/reader3/readSourceFile").coroutineHandler { it ->
            bookSourceController.readSourceFile(it)
        }
        router.post("/reader3/saveFromRemoteSource").coroutineHandlerWithoutRes { it ->
            bookSourceController.saveFromRemoteSource(it)
        }
        router.post("/reader3/setAsDefaultBookSources").coroutineHandler { it ->
            bookSourceController.setAsDefaultBookSources(it)
        }
        router.post("/reader3/deleteUserBookSource").coroutineHandler { it ->
            bookSourceController.deleteUserBookSource(it)
        }
        router.post("/reader3/deleteBookSourcesFile").coroutineHandler { it ->
            bookSourceController.deleteBookSourcesFile(it)
        }
        router.get("/reader3/getBookshelf").coroutineHandler { it ->
            bookController.getBookshelf(it)
        }
        router.get("/reader3/getShelfBook").coroutineHandler { it ->
            bookController.getShelfBook(it)
        }
        router.post("/reader3/saveBook").coroutineHandler { it ->
            bookController.saveBook(it)
        }
        router.post("/reader3/deleteBook").coroutineHandler { it ->
            bookController.deleteBook(it)
        }
        router.post("/reader3/deleteBooks").coroutineHandler { it ->
            bookController.deleteBooks(it)
        }
        router.post("/reader3/getInvalidBookSources").coroutineHandler { it ->
            bookController.getInvalidBookSources(it)
        }
        router.post("/reader3/exploreBook").coroutineHandler { it ->
            bookController.exploreBook(it)
        }
        router.get("/reader3/exploreBook").coroutineHandler { it ->
            bookController.exploreBook(it)
        }
        router.get("/reader3/searchBook").coroutineHandler { it ->
            bookController.searchBook(it)
        }
        router.post("/reader3/searchBook").coroutineHandler { it ->
            bookController.searchBook(it)
        }
        router.get("/reader3/searchBookMulti").coroutineHandler { it ->
            bookController.searchBookMulti(it)
        }
        router.post("/reader3/searchBookMulti").coroutineHandler { it ->
            bookController.searchBookMulti(it)
        }
        router.get("/reader3/searchBookMultiSSE").coroutineHandlerWithoutRes { it ->
            bookController.searchBookMultiSSE(it)
        }
        router.get("/reader3/getBookInfo").coroutineHandler { it ->
            bookController.getBookInfo(it)
        }
        router.post("/reader3/getBookInfo").coroutineHandler { it ->
            bookController.getBookInfo(it)
        }
        router.get("/reader3/getChapterList").coroutineHandler { it ->
            bookController.getChapterList(it)
        }
        router.post("/reader3/getChapterList").coroutineHandler { it ->
            bookController.getChapterList(it)
        }
        router.get("/reader3/getBookContent").coroutineHandler { it ->
            bookController.getBookContent(it)
        }
        router.post("/reader3/getBookContent").coroutineHandler { it ->
            bookController.getBookContent(it)
        }
        router.post("/reader3/saveBookContent").coroutineHandler { it ->
            bookController.saveBookContent(it)
        }
        router.post("/reader3/saveBookProgress").coroutineHandler { it ->
            bookController.saveBookProgress(it)
        }
        router.get("/reader3/cover").coroutineHandlerWithoutRes { it ->
            bookController.getBookCover(it)
        }
        router.get("/reader3/searchBookSource").coroutineHandler { it ->
            bookController.searchBookSource(it)
        }
        router.post("/reader3/searchBookSource").coroutineHandler { it ->
            bookController.searchBookSource(it)
        }
        router.get("/reader3/getAvailableBookSource").coroutineHandler { it ->
            bookController.getAvailableBookSource(it)
        }
        router.post("/reader3/getAvailableBookSource").coroutineHandler { it ->
            bookController.getAvailableBookSource(it)
        }
        router.get("/reader3/searchBookSourceSSE").coroutineHandlerWithoutRes { it ->
            bookController.searchBookSourceSSE(it)
        }
        router.get("/reader3/setBookSource").coroutineHandler { it ->
            bookController.setBookSource(it)
        }
        router.post("/reader3/setBookSource").coroutineHandler { it ->
            bookController.setBookSource(it)
        }
        router.post("/reader3/saveBookGroupId").coroutineHandler { it ->
            bookController.saveBookGroupId(it)
        }
        router.post("/reader3/addBookGroupMulti").coroutineHandler { it ->
            bookController.addBookGroupMulti(it)
        }
        router.post("/reader3/removeBookGroupMulti").coroutineHandler { it ->
            bookController.removeBookGroupMulti(it)
        }
        router.post("/reader3/importBookPreview").coroutineHandler { it ->
            bookController.importBookPreview(it)
        }
        router.post("/reader3/refreshLocalBook").coroutineHandler { it ->
            bookController.refreshLocalBook(it)
        }
        router.get("/reader3/getTxtTocRules").coroutineHandler { it ->
            bookController.getTxtTocRules(it)
        }
        router.post("/reader3/getChapterListByRule").coroutineHandler { it ->
            bookController.getChapterListByRule(it)
        }
        router.get("/reader3/getBookGroups").coroutineHandler { it ->
            bookGroupController.list(it)
        }
        router.post("/reader3/saveBookGroup").coroutineHandler { it ->
            bookGroupController.save(it)
        }
        router.post("/reader3/deleteBookGroup").coroutineHandler { it ->
            bookGroupController.delete(it)
        }
        router.post("/reader3/saveBookGroupOrder").coroutineHandler { it ->
            bookGroupController.saveBookGroupOrder(it)
        }
        router.get("/reader3/bookSourceDebugSSE").coroutineHandlerWithoutRes { it ->
            bookController.bookSourceDebugSSE(it)
        }
        router.get("/reader3/cacheBookSSE").coroutineHandlerWithoutRes { it ->
            bookController.cacheBookSSE(it)
        }
        router.post("/reader3/cacheBookOnServer").coroutineHandler { it ->
            bookController.cacheBookOnServer(it)
        }
        router.get("/reader3/getShelfBookWithCacheInfo").coroutineHandler { it ->
            bookController.getShelfBookWithCacheInfo(it)
        }
        router.post("/reader3/deleteBookCache").coroutineHandler { it ->
            bookController.deleteBookCache(it)
        }
        router.post("/reader3/exportBook").coroutineHandlerWithoutRes { it ->
            bookController.exportBook(it)
        }
        router.get("/reader3/exportBook").coroutineHandlerWithoutRes { it ->
            bookController.exportBook(it)
        }
        router.get("/reader3/searchBookContent").coroutineHandler { it ->
            bookController.searchBookContent(it)
        }
        router.post("/reader3/searchBookContent").coroutineHandler { it ->
            bookController.searchBookContent(it)
        }
        router.post("/reader3/backupToMongodb").coroutineHandler { it ->
            bookController.backupToMongodb(it)
        }
        router.post("/reader3/restoreFromMongodb").coroutineHandler { it ->
            bookController.restoreFromMongodb(it)
        }
        router.post("/reader3/book/saveBookConfig").coroutineHandler { it ->
            bookController.saveBookConfig(it)
        }
        router.get("/reader3/book/tts").coroutineHandlerWithoutRes { it ->
            bookController.textToSpeech(it)
        }
        router.post("/reader3/book/tts").coroutineHandlerWithoutRes { it ->
            bookController.textToSpeech(it)
        }
        router.post("/reader3/uploadFile").coroutineHandler { it ->
            userController.uploadFile(it)
        }
        router.post("/reader3/deleteFile").coroutineHandler { it ->
            userController.deleteFile(it)
        }
        router.post("/reader3/login").coroutineHandler { it ->
            userController.login(it)
        }
        router.post("/reader3/logout").coroutineHandler { it ->
            userController.logout(it)
        }
        router.get("/reader3/getUserInfo").coroutineHandler { it ->
            userController.getUserInfo(it)
        }
        router.post("/reader3/saveUserConfig").coroutineHandler { it ->
            userController.saveUserConfig(it)
        }
        router.get("/reader3/getUserConfig").coroutineHandler { it ->
            userController.getUserConfig(it)
        }
        router.get("/reader3/getUserList").coroutineHandler { it ->
            userController.getUserList(it)
        }
        router.post("/reader3/deleteUsers").coroutineHandler { it ->
            userController.deleteUsers(it)
        }
        router.post("/reader3/clearInactiveUsers").coroutineHandler { it ->
            userController.clearInactiveUsers(it)
        }
        router.post("/reader3/addUser").coroutineHandler { it ->
            userController.addUser(it)
        }
        router.post("/reader3/resetPassword").coroutineHandler { it ->
            userController.resetPassword(it)
        }
        router.post("/reader3/updateUser").coroutineHandler { it ->
            userController.updateUser(it)
        }
        router.get("/reader3/user/downloadBackupFile").coroutineHandlerWithoutRes { it ->
            userController.downloadBackupFile(it)
        }
        router.get("/reader3/getLicense").coroutineHandler { it ->
            licenseController.getLicense(it)
        }
        router.post("/reader3/importLicense").coroutineHandlerWithoutRes { it ->
            licenseController.importLicense(it)
        }
        router.get("/reader3/generateKeys").coroutineHandler { it ->
            licenseController.generateKeys(it)
        }
        router.post("/reader3/generateKeys").coroutineHandler { it ->
            licenseController.generateKeys(it)
        }
        router.get("/reader3/generateLicense").coroutineHandler { it ->
            licenseController.generateLicense(it)
        }
        router.post("/reader3/generateLicense").coroutineHandler { it ->
            licenseController.generateLicense(it)
        }
        router.get("/reader3/isHostValid").coroutineHandler { it ->
            licenseController.isHostValid(it)
        }
        router.post("/reader3/isHostValid").coroutineHandler { it ->
            licenseController.isHostValid(it)
        }
        router.post("/reader3/activateLicense").coroutineHandler { it ->
            licenseController.activateLicense(it)
        }
        router.get("/reader3/isLicenseValid").coroutineHandler { it ->
            licenseController.isLicenseValid(it)
        }
        router.post("/reader3/isLicenseValid").coroutineHandler { it ->
            licenseController.isLicenseValid(it)
        }
        router.post("/reader3/decryptLicense").coroutineHandler { it ->
            licenseController.decryptLicense(it)
        }
        router.post("/reader3/sendCodeToEmail").coroutineHandler { it ->
            licenseController.sendCodeToEmail(it)
        }
        router.post("/reader3/supplyLicense").coroutineHandler { it ->
            licenseController.supplyLicense(it)
        }
        router.post("/reader3/backupToWebdav").coroutineHandler { it ->
            webdavController.backupToWebdav(it)
        }
        router.get("/reader3/getRssSources").coroutineHandler { it ->
            rssSourceController.getRssSources(it)
        }
        router.post("/reader3/saveRssSource").coroutineHandler { it ->
            rssSourceController.saveRssSource(it)
        }
        router.post("/reader3/saveRssSources").coroutineHandler { it ->
            rssSourceController.saveRssSources(it)
        }
        router.post("/reader3/deleteRssSource").coroutineHandler { it ->
            rssSourceController.deleteRssSource(it)
        }
        router.get("/reader3/getRssArticles").coroutineHandler { it ->
            rssSourceController.getRssArticles(it)
        }
        router.post("/reader3/getRssArticles").coroutineHandler { it ->
            rssSourceController.getRssArticles(it)
        }
        router.get("/reader3/getRssContent").coroutineHandler { it ->
            rssSourceController.getRssContent(it)
        }
        router.post("/reader3/getRssContent").coroutineHandler { it ->
            rssSourceController.getRssContent(it)
        }
        router.get("/reader3/getReplaceRules").coroutineHandler { it ->
            replaceRuleController.list(it)
        }
        router.post("/reader3/saveReplaceRule").coroutineHandler { it ->
            replaceRuleController.save(it)
        }
        router.post("/reader3/saveReplaceRules").coroutineHandler { it ->
            replaceRuleController.saveMulti(it)
        }
        router.post("/reader3/deleteReplaceRule").coroutineHandler { it ->
            replaceRuleController.delete(it)
        }
        router.post("/reader3/deleteReplaceRules").coroutineHandler { it ->
            replaceRuleController.deleteMulti(it)
        }
        router.get("/reader3/getBookmarks").coroutineHandler { it ->
            bookmarkController.list(it)
        }
        router.post("/reader3/saveBookmark").coroutineHandler { it ->
            bookmarkController.save(it)
        }
        router.post("/reader3/saveBookmarks").coroutineHandler { it ->
            bookmarkController.saveMulti(it)
        }
        router.post("/reader3/deleteBookmark").coroutineHandler { it ->
            bookmarkController.delete(it)
        }
        router.post("/reader3/deleteBookmarks").coroutineHandler { it ->
            bookmarkController.deleteMulti(it)
        }
        router.get("/reader3/file/list").coroutineHandler { it ->
            fileController.list(it)
        }
        router.get("/reader3/file/get").coroutineHandler { it ->
            fileController.get(it)
        }
        router.post("/reader3/file/save").coroutineHandler { it ->
            fileController.save(it)
        }
        router.post("/reader3/file/mkdir").coroutineHandler { it ->
            fileController.mkdir(it)
        }
        router.get("/reader3/file/download").coroutineHandlerWithoutRes { it ->
            fileController.download(it)
        }
        router.post("/reader3/file/upload").coroutineHandler { it ->
            fileController.upload(it)
        }
        router.post("/reader3/file/delete").coroutineHandler { it ->
            fileController.delete(it)
        }
        router.post("/reader3/file/deleteMulti").coroutineHandler { it ->
            fileController.deleteMulti(it)
        }
        router.post("/reader3/file/importPreview").coroutineHandler { it ->
            fileController.importPreview(it)
        }
        router.post("/reader3/file/restore").coroutineHandler { it ->
            fileController.restore(it)
        }
        router.get("/reader3/file/parse").coroutineHandler { it ->
            fileController.parse(it)
        }
        router.post("/reader3/file/parse").coroutineHandler { it ->
            fileController.parse(it)
        }
        router.get("/reader3/httpTTS/list").coroutineHandler { it ->
            httpTTSController.list(it)
        }
        router.post("/reader3/httpTTS/save").coroutineHandler { it ->
            httpTTSController.save(it)
        }
        router.post("/reader3/httpTTS/saveMulti").coroutineHandler { it ->
            httpTTSController.saveMulti(it)
        }
        router.post("/reader3/httpTTS/delete").coroutineHandler { it ->
            httpTTSController.delete(it)
        }
        router.post("/reader3/httpTTS/deleteMulti").coroutineHandler { it ->
            httpTTSController.deleteMulti(it)
        }

    }

    open suspend fun setupPort() {
        logger.info("port: {}", port)
        val serverPort = env.getProperty("reader.server.port", Int::class.java)
        logger.info("serverPort: {}", serverPort)
        if (serverPort != null && serverPort > 0) {
            port = serverPort
        }
    }

    open suspend fun migration() {
        try {
            val storageDir = File(getWorkDir("storage"))
            val dataDir = File(getWorkDir("storage", "data", "default"))
            if (!storageDir.exists()) {
                dataDir.mkdirs()
            } else if (!dataDir.exists()) {
                dataDir.mkdirs()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override val contextPath: String
        get() {
            val contextPath = env.getProperty("reader.server.contextPath", String::class.java)
            if (contextPath != null && contextPath.length > 0) {
                return contextPath
            }
            return ""
        }

    override fun started() {
        SpringContextUtils.getApplicationContext().publishEvent(SpringEvent(this, "READY", ""))
    }

    override fun onStartError() {
        logger.error("应用启动失败，请检查" + port + "端口是否被占用")
        SpringContextUtils.getApplicationContext()
            .publishEvent(SpringEvent(this, "START_ERROR", "应用启动失败，请检查" + port + "端口是否被占用"))
    }

    override fun onHandlerError(ctx: RoutingContext, error: Exception) {
        val returnData = ReturnData()
        logger.error("onHandlerError: ", error)
        if (!ctx.response().headWritten()) {
            ctx.success(returnData.setErrorMsg(error.toString()))
        } else {
            ctx.response().end(error.toString())
        }
    }

    private suspend fun getSystemInfo(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        val systemFont = System.getProperty("reader.system.fonts")
        val freeMemory = "" + Runtime.getRuntime().freeMemory() / 1024 / 1024 + 'M'
        val totalMemory = "" + Runtime.getRuntime().totalMemory() / 1024 / 1024 + 'M'
        val maxMemory = "" + Runtime.getRuntime().maxMemory() / 1024 / 1024 + 'M'
        val userController = UserController(coroutineContext)
        var dayLoginUser = 0
        var sevenDayLoginUser = 0
        var monthLoginUser = 0
        var keepUser = 0
        var dayRegisterUser = 0
        var sevenDayRegisterUser = 0
        var monthRegisterUser = 0
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.timeInMillis
        userController.forEachUser { user ->
            if (user.last_login_at >= System.currentTimeMillis() - 86400000L) {
                dayLoginUser++
            }
            if (user.last_login_at >= System.currentTimeMillis() - 604800000L) {
                sevenDayLoginUser++
            }
            if (user.last_login_at >= calendar.timeInMillis) {
                monthLoginUser++
            }
            if (user.created_at >= System.currentTimeMillis() - 86400000L) {
                dayRegisterUser++
            }
            if (user.created_at >= System.currentTimeMillis() - 604800000L) {
                sevenDayRegisterUser++
            }
            if (user.created_at >= calendar.timeInMillis) {
                monthRegisterUser++
            }
            if (user.last_login_at >= user.created_at + 604800000L &&
                user.last_login_at >= System.currentTimeMillis() - 604800000L
            ) {
                keepUser++
            }
            false
        }
        return returnData.setData(
            mapOf(
                "fonts" to systemFont,
                "freeMemory" to freeMemory,
                "totalMemory" to totalMemory,
                "maxMemory" to maxMemory,
                "dayRegisterUser" to dayRegisterUser,
                "dayLoginUser" to dayLoginUser,
                "sevenDayRegisterUser" to sevenDayRegisterUser,
                "sevenDayLoginUser" to sevenDayLoginUser,
                "monthRegisterUser" to monthRegisterUser,
                "monthLoginUser" to monthLoginUser,
                "keepUser" to keepUser
            )
        )
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    fun shelfUpdateJob() {
        if (appConfig.shelfUpdateInteval <= 0) {
            return
        }
        val now = Calendar.getInstance()
        val hour = now.get(Calendar.HOUR_OF_DAY)
        val munite = now.get(Calendar.MINUTE)
        val muniteFromToday = hour * 60 + munite
        if (muniteFromToday % appConfig.shelfUpdateInteval != 0) {
            return
        }
        MDC.put("traceId", getTraceId())
        launch(MDCContext() + Dispatchers.IO) {
            try {
                val bookController = BookController(coroutineContext)
                logger.info("开始检查书架书籍更新")
                bookController.getBookShelfBooks(true, "default")
                val userController = UserController(coroutineContext)
                userController.forEachUser { user ->
                    if (user.last_login_at >= System.currentTimeMillis() - 259200000L) {
                        bookController.getBookShelfBooks(true, user.username)
                    }
                    false
                }
                logger.info("书架书籍更新检查结束")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    fun remoteBookSourceSubUpdateJob() {
        if (appConfig.remoteBookSourceUpdateInterval <= 0) {
            return
        }
        val now = Calendar.getInstance()
        val hour = now.get(Calendar.HOUR_OF_DAY)
        val munite = now.get(Calendar.MINUTE)
        val muniteFromToday = hour * 60 + munite
        if (muniteFromToday % appConfig.remoteBookSourceUpdateInterval != 0) {
            return
        }
        MDC.put("traceId", getTraceId())
        launch(MDCContext() + Dispatchers.IO) {
            try {
                val bookSourceController = BookSourceController(coroutineContext)
                logger.info("开始检查远程书源更新")
                bookSourceController.updateRemoteSourceSub("default", null)
                val userController = UserController(coroutineContext)
                userController.forEachUser { user ->
                    if (user.last_login_at >= System.currentTimeMillis() - 259200000L) {
                        bookSourceController.updateRemoteSourceSub(user.username, user)
                    }
                    false
                }
                logger.info("远程书源更新检查结束")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @Scheduled(cron = "0 59 23 * * ?")
    fun clearUser() {
        if (appConfig.autoClearInactiveUser <= 0 || appConfig.secure) {
            return
        }
        MDC.put("traceId", getTraceId())
        launch(MDCContext() + Dispatchers.IO) {
            try {
                logger.info("开始清理 {} 天未登录用户", appConfig.autoClearInactiveUser)
                val userController = UserController(coroutineContext)
                userController.clearInactiveUsers(appConfig.autoClearInactiveUser)
                logger.info("不活跃用户自动清理结束")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @Scheduled(cron = "0 50 23 * * ?")
    fun autoBackup() {
        if (!appConfig.autoBackupUserData) {
            return
        }
        MDC.put("traceId", getTraceId())
        launch(MDCContext() + Dispatchers.IO) {
            try {
                val bookController = BookController(coroutineContext)
                logger.info("开始备份用户数据")
                bookController.saveToWebdav("default")
                val userController = UserController(coroutineContext)
                userController.forEachUser { user ->
                    if (user.last_login_at >= System.currentTimeMillis() - 259200000L) {
                        bookController.saveToWebdav(user.username)
                    }
                    false
                }
                logger.info("备份用户数据结束")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @Scheduled(cron = "0 0 2 * * ?")
    fun autoGC() {
        System.gc()
    }

    @Scheduled(cron = "0 4/15 7-23 * * ?")
    fun checkLicense() {
        val license = getInstalledLicense(true)
        if ("default" == license.type) {
            return
        }
        MDC.put("traceId", getTraceId())
        launch(MDCContext() + Dispatchers.IO) {
            delay((10..120).random() * 1000L)
            delay((1..10).random() * 1000L)
            try {
                logger.info("开始检查授权是否正常")
                val licenseController = LicenseController(coroutineContext)
                licenseController.checkLicense(license)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
