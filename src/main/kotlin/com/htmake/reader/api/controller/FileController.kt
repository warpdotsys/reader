// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.api.controller

import com.htmake.reader.api.ReturnData
import com.htmake.reader.entity.User
import com.htmake.reader.utils.getWorkDir
import com.htmake.reader.utils.listFilesRecursively
import com.htmake.reader.utils.success
import com.htmake.reader.utils.toDir
import io.legado.app.data.entities.Book
import io.legado.app.exception.TocEmptyException
import io.legado.app.model.localBook.LocalBook
import io.legado.app.utils.FileUtils
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.RoutingContext
import mu.KLogger
import mu.KotlinLogging
import java.io.File
import java.net.URLEncoder
import kotlin.coroutines.CoroutineContext

private val logger: KLogger = KotlinLogging.logger { }

/**
 * 文件管理接口：本地书仓 / WebDAV / 存储目录的浏览、上传、下载、增删改，以及备份恢复与导入预览。
 * __FILE_HOME__ 由 [checkAccess] 依据请求参数写入上下文，后续方法都从它取根目录。
 */
class FileController(coroutineContext: CoroutineContext) : BaseController(coroutineContext) {

    suspend fun checkAccess(
        context: RoutingContext,
        isSave: Boolean = false,
        isDelete: Boolean = false
    ): ReturnData? {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        context.put("__FILE_HOME__", null)
        var home: String? = null
        if (context.request().method() == HttpMethod.POST) {
            if (context.fileUploads() == null || context.fileUploads().isEmpty()) {
                home = context.bodyAsJson.getString("home") ?: ""
            } else {
                home = context.request().getParam("home") ?: ""
            }
        } else {
            home = context.queryParam("home").firstOrNull() ?: ""
        }
        when (home) {
            "__WEBDAV__" -> {
                if (appConfig.secure) {
                    val userInfo = context.get<User>("userInfo")
                    if (userInfo == null) {
                        return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
                    }
                    if (!userInfo.enable_webdav) {
                        return returnData.setErrorMsg("未开启webdav功能")
                    }
                }
                context.put("__FILE_HOME__", getUserWebdavHome(context).toDir())
            }
            "__LOCAL_STORE__" -> {
                if (appConfig.secure) {
                    val userInfo = context.get<User>("userInfo")
                    if (userInfo == null) {
                        return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
                    }
                    if (!userInfo.enable_local_store) {
                        return returnData.setErrorMsg("未开启本地书仓功能")
                    }
                }
                if ((isSave || isDelete) && !checkManagerAuth(context)) {
                    return returnData.setData("NEED_SECURE_KEY").setErrorMsg("请输入管理密码")
                }
                context.put("__FILE_HOME__", getWorkDir("storage", "localStore").toDir())
            }
            "__HOME__" -> {
                val userNameSpace = getUserNameSpace(context)
                context.put("__FILE_HOME__", getWorkDir("storage", "data", userNameSpace).toDir())
            }
            "__STORAGE__" -> {
                if (!checkManagerAuth(context)) {
                    return returnData.setData("NEED_SECURE_KEY").setErrorMsg("请输入管理密码")
                }
                context.put("__FILE_HOME__", getWorkDir("storage").toDir())
            }
            else -> {
                return returnData.setErrorMsg("非法访问")
            }
        }
        logger.info("context.__FILE_HOME__ {}", context.get<Any>("__FILE_HOME__"))
        return null
    }

    suspend fun list(context: RoutingContext): ReturnData? {
        val checkResult = checkAccess(context)
        if (checkResult != null) {
            return checkResult
        }
        val returnData = ReturnData()
        var path: String? = null
        if (context.request().method() == HttpMethod.POST) {
            path = context.bodyAsJson.getString("path") ?: ""
        } else {
            path = context.queryParam("path").firstOrNull() ?: ""
        }
        if (path.isEmpty()) {
            path = "/"
        }
        var home: Any? = null
        home = context.get("__FILE_HOME__")
        if (home == null) {
            return returnData.setErrorMsg("参数错误")
        }
        path = path.toDir(true)
        val file = File((home as String?) + path)
        logger.info("file: {} {}", path, file)
        if (!file.exists()) {
            if (path != "/") {
                return returnData.setErrorMsg("路径不存在")
            }
            file.mkdirs()
        }
        if (!file.isDirectory()) {
            return returnData.setErrorMsg("路径不是目录")
        }
        var fileList: MutableList<Map<String, Any?>>? = null
        fileList = arrayListOf()
        file.listFiles().forEach {
            if (!it.name.startsWith(".")) {
                fileList.add(
                    mapOf(
                        "name" to it.name,
                        "size" to it.length(),
                        "path" to it.toString().replace(home as String, ""),
                        "lastModified" to it.lastModified(),
                        "isDirectory" to it.isDirectory()
                    )
                )
            }
        }
        return returnData.setData(fileList)
    }

    suspend fun upload(context: RoutingContext): ReturnData? {
        val returnData = ReturnData()
        if (context.fileUploads() == null || context.fileUploads().isEmpty()) {
            return returnData.setErrorMsg("请上传文件")
        }
        val checkResult = checkAccess(context, true)
        if (checkResult != null) {
            return checkResult
        }
        var path = context.request().getParam("path")
        if (path.isNullOrEmpty()) {
            path = "/"
        }
        path = path.toDir(true)
        var fileList: MutableList<Map<String, Any?>>? = null
        fileList = arrayListOf()
        var home: Any? = null
        home = context.get("__FILE_HOME__")
        if (home == null) {
            return returnData.setErrorMsg("参数错误")
        }
        context.fileUploads().forEach {
            val file = File(it.uploadedFileName())
            logger.info("uploadFile: {} {} {}", it.uploadedFileName(), it.fileName(), file)
            if (file.exists()) {
                val fileName = it.fileName()
                val newFile = File("$home$path${File.separator}$fileName")
                if (!newFile.parentFile.exists()) {
                    newFile.parentFile.mkdirs()
                }
                if (newFile.exists()) {
                    newFile.delete()
                }
                logger.info("moveTo: {}", newFile)
                if (file.copyRecursively(newFile)) {
                    fileList.add(
                        mapOf(
                            "name" to newFile.name,
                            "size" to newFile.length(),
                            "path" to newFile.toString().replace(home as String, ""),
                            "lastModified" to newFile.lastModified(),
                            "isDirectory" to newFile.isDirectory()
                        )
                    )
                }
                file.deleteRecursively()
            }
        }
        return returnData.setData(fileList)
    }

    suspend fun download(context: RoutingContext) {
        val checkResult = checkAccess(context)
        if (checkResult != null) {
            context.success(checkResult)
            return
        }
        val returnData = ReturnData()
        var path: String? = null
        var stream = 0
        if (context.request().method() == HttpMethod.POST) {
            path = context.bodyAsJson.getString("path") ?: ""
            stream = context.bodyAsJson.getInteger("stream", 0)
        } else {
            path = context.queryParam("path").firstOrNull() ?: ""
            stream = context.queryParam("stream").firstOrNull()?.toInt() ?: 0
        }
        if (path.isEmpty()) {
            context.success(returnData.setErrorMsg("参数错误"))
            return
        }
        val home = context.get<String>("__FILE_HOME__")
        if (home == null) {
            context.success(returnData.setErrorMsg("参数错误"))
            return
        }
        path = path.toDir(true)
        val file = File(home + path)
        logger.info("file: {} {}", path, file)
        if (!file.exists()) {
            context.success(returnData.setErrorMsg("路径不存在"))
            return
        }
        val response = context.response().putHeader("Cache-Control", "86400")
        if (stream <= 0) {
            response.putHeader(
                "Content-Disposition",
                "attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8")
            )
        }
        response.sendFile(file.toString())
    }

    suspend fun get(context: RoutingContext): ReturnData? {
        val checkResult = checkAccess(context)
        if (checkResult != null) {
            return checkResult
        }
        val returnData = ReturnData()
        var path: String? = null
        if (context.request().method() == HttpMethod.POST) {
            path = context.bodyAsJson.getString("path") ?: ""
        } else {
            path = context.queryParam("path").firstOrNull() ?: ""
        }
        if (path.isEmpty()) {
            return returnData.setErrorMsg("参数错误")
        }
        val home = context.get<String>("__FILE_HOME__")
        if (home == null) {
            return returnData.setErrorMsg("参数错误")
        }
        path = path.toDir(true)
        val file = File(home + path)
        logger.info("file: {} {}", path, file)
        if (!file.exists()) {
            return returnData.setErrorMsg("路径不存在")
        }
        return returnData.setData(file.readText())
    }

    suspend fun save(context: RoutingContext): ReturnData? {
        val checkResult = checkAccess(context, true)
        if (checkResult != null) {
            return checkResult
        }
        val returnData = ReturnData()
        var path = context.bodyAsJson.getString("path", "") ?: ""
        var content = context.bodyAsJson.getString("content", "") ?: ""
        if (path.isEmpty()) {
            return returnData.setErrorMsg("参数错误")
        }
        val home = context.get<String>("__FILE_HOME__")
        if (home == null) {
            return returnData.setErrorMsg("参数错误")
        }
        path = path.toDir(true)
        val file = FileUtils.createFileWithReplace(home + path)
        logger.info("file: {} {}", path, file)
        file.writeText(content)
        return returnData.setData("")
    }

    suspend fun mkdir(context: RoutingContext): ReturnData? {
        val checkResult = checkAccess(context, true)
        if (checkResult != null) {
            return checkResult
        }
        val returnData = ReturnData()
        var path = context.bodyAsJson.getString("path", "") ?: ""
        if (path.isEmpty()) {
            return returnData.setErrorMsg("参数错误")
        }
        var name = context.bodyAsJson.getString("name", "") ?: ""
        if (name.isEmpty() || name.startsWith(".")) {
            return returnData.setErrorMsg("参数错误")
        }
        val home = context.get<String>("__FILE_HOME__")
        if (home == null) {
            return returnData.setErrorMsg("参数错误")
        }
        path = path.toDir(true)
        val file = File(home + path + File.separator + name)
        logger.info("file: {} {}", path, file)
        if (file.exists()) {
            return returnData.setErrorMsg("路径已存在")
        }
        file.mkdirs()
        return returnData.setData("")
    }

    suspend fun delete(context: RoutingContext): ReturnData? {
        val checkResult = checkAccess(context, false, true)
        if (checkResult != null) {
            return checkResult
        }
        val returnData = ReturnData()
        var path: String? = null
        if (context.request().method() == HttpMethod.POST) {
            path = context.bodyAsJson.getString("path") ?: ""
        } else {
            path = context.queryParam("path").firstOrNull() ?: ""
        }
        if (path.isEmpty()) {
            return returnData.setErrorMsg("参数错误")
        }
        val home = context.get<String>("__FILE_HOME__")
        if (home == null) {
            return returnData.setErrorMsg("参数错误")
        }
        path = path.toDir(true)
        val file = File(home + path)
        logger.info("file: {} {}", path, file)
        if (!file.exists()) {
            return returnData.setErrorMsg("路径不存在")
        }
        file.deleteRecursively()
        return returnData.setData("")
    }

    suspend fun deleteMulti(context: RoutingContext): ReturnData? {
        val checkResult = checkAccess(context, false, true)
        if (checkResult != null) {
            return checkResult
        }
        val returnData = ReturnData()
        val paths = context.bodyAsJson.getJsonArray("path")
        if (paths == null) {
            return returnData.setErrorMsg("参数错误")
        }
        var home: Any? = null
        home = context.get("__FILE_HOME__")
        if (home == null) {
            return returnData.setErrorMsg("参数错误")
        }
        paths.forEach {
            val filePath = it as String? ?: ""
            if (filePath.isNotEmpty()) {
                val file = File((home as String?) + filePath.toDir(true))
                file.deleteRecursively()
            }
        }
        return returnData.setData("")
    }

    suspend fun importPreview(context: RoutingContext): ReturnData? {
        val checkResult = checkAccess(context)
        if (checkResult != null) {
            return checkResult
        }
        val returnData = ReturnData()
        var paths = context.bodyAsJson.getJsonArray("path")
        if (paths == null) {
            return returnData.setErrorMsg("参数错误")
        }
        var home: Any? = null
        home = context.get("__FILE_HOME__")
        if (home == null) {
            return returnData.setErrorMsg("参数错误")
        }
        var fileList: MutableList<Map<String, Any?>>? = null
        fileList = arrayListOf()
        var userNameSpace: String? = null
        userNameSpace = getUserNameSpace(context)
        paths.forEach {
            var path = it as String? ?: ""
            if (path.isNotEmpty()) {
                path = (home as String?) + path
                val file = File(path)
                logger.info("localFile: {} {}", path, file)
                if (file.exists() && !file.isDirectory()) {
                    val fileName = file.name
                    val ext = getFileExt(fileName)
                    if (ext != "txt" && ext != "epub" && ext != "umd" && ext != "cbz" && ext != "pdf") {
                        return returnData.setErrorMsg("不支持导入" + ext + "格式的书籍文件")
                    }
                    var rootDir = getWorkDir()
                    if (!rootDir.endsWith(File.separator)) {
                        rootDir = rootDir + File.separator
                    }
                    var relativePath = path
                    logger.info("rootDir: {} path: {}", rootDir, path)
                    if (relativePath.startsWith(rootDir)) {
                        relativePath = relativePath.replaceFirst(rootDir, "")
                    }
                    logger.info("relative path: {}", relativePath)
                    val url = relativePath.replace("\\", "/")
                    val book = Book.initLocalBook(url, relativePath, rootDir)
                    book.setUserNameSpace(userNameSpace)
                    logger.info("book {}", book)
                    try {
                        val chapters = LocalBook.getChapterList(book)
                        fileList.add(mapOf("book" to book, "chapters" to chapters))
                    } catch (e: TocEmptyException) {
                        fileList.add(mapOf("book" to book, "chapters" to arrayListOf<Any>()))
                    }
                }
            }
        }
        return returnData.setData(fileList)
    }

    suspend fun restore(context: RoutingContext): ReturnData? {
        val checkResult = checkAccess(context)
        if (checkResult != null) {
            return checkResult
        }
        val returnData = ReturnData()
        var path: String? = null
        if (context.request().method() == HttpMethod.POST) {
            path = context.bodyAsJson.getString("path") ?: ""
        } else {
            path = context.queryParam("path").firstOrNull() ?: ""
        }
        if (path.isEmpty()) {
            path = "/"
        }
        val ext = getFileExt(path)
        if (ext != "zip") {
            return returnData.setErrorMsg("路径不是zip备份文件")
        }
        val home = context.get<String>("__FILE_HOME__")
        if (home == null) {
            return returnData.setErrorMsg("参数错误")
        }
        val file = File(home + path)
        logger.info("file: {} {}", path, file)
        if (!file.exists()) {
            return returnData.setErrorMsg("路径不存在")
        }
        val bookController = BookController(coroutineContext)
        if (!bookController.syncFromWebdav(file.toString(), getUserNameSpace(context))) {
            return returnData.setErrorMsg("恢复失败")
        }
        return returnData.setData("")
    }

    suspend fun parse(context: RoutingContext): ReturnData? {
        val checkResult = checkAccess(context)
        if (checkResult != null) {
            return checkResult
        }
        val returnData = ReturnData()
        var home: Any? = null
        home = context.get("__FILE_HOME__")
        if (home == null) {
            return returnData.setErrorMsg("参数错误")
        }
        var path: String? = null
        var import = 0
        if (context.request().method() == HttpMethod.POST) {
            path = context.bodyAsJson.getString("path") ?: ""
            import = context.bodyAsJson.getInteger("import", 0)
        } else {
            path = context.queryParam("path").firstOrNull() ?: ""
            import = context.queryParam("import").firstOrNull()?.toInt() ?: 0
        }
        if (path.isEmpty()) {
            path = "/"
        }
        val file = File((home as String?) + path)
        logger.info("file: {} {}", path, file)
        if (!file.exists()) {
            return returnData.setErrorMsg("路径不存在")
        }
        if (!file.isDirectory()) {
            return returnData.setErrorMsg("路径不是目录")
        }
        var fileList: MutableList<Map<String, Any?>>? = null
        fileList = arrayListOf()
        var userNameSpace: String? = null
        userNameSpace = getUserNameSpace(context)
        var rootDir = getWorkDir()
        if (!rootDir.endsWith(File.separator)) {
            rootDir = rootDir + File.separator
        }
        val bookController = BookController(coroutineContext)
        file.listFilesRecursively().forEach {
            if (!it.name.startsWith(".") && it.isFile) {
                val fileName = it.name
                val ext = getFileExt(fileName)
                when (ext) {
                    "txt", "pdf", "epub", "cbz", "umd" -> {
                        var relativePath = it.path
                        logger.debug("rootDir: {} path: {}", rootDir, path)
                        if (relativePath.startsWith(rootDir)) {
                            relativePath = relativePath.replaceFirst(rootDir, "")
                        }
                        logger.debug("relative path: {}", relativePath)
                        val url = relativePath.replace("\\", "/")
                        val book = Book.initLocalBook(url, relativePath, rootDir)
                        book.setUserNameSpace(userNameSpace)
                        logger.debug("book {}", book)
                        if (import > 0) {
                            val result = bookController.saveBookToShelf(book, userNameSpace, context)
                            if (result.second == null && result.first.inShelf) {
                                fileList.add(mapOf("name" to it.name))
                            }
                        } else {
                            fileList.add(
                                mapOf(
                                    "name" to it.name,
                                    "size" to it.length(),
                                    "path" to it.toString().replaceFirst(home as String, ""),
                                    "lastModified" to it.lastModified(),
                                    "book" to book
                                )
                            )
                        }
                    }
                }
            }
        }
        return returnData.setData(fileList)
    }
}
