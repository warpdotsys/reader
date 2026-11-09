// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.api.controller

import com.htmake.reader.api.ReturnData
import com.htmake.reader.entity.User
import com.htmake.reader.utils.asJsonObject
import com.htmake.reader.utils.deleteRecursively
import com.htmake.reader.utils.genEncryptedPassword
import com.htmake.reader.utils.getStorage
import com.htmake.reader.utils.globalHandler
import com.htmake.reader.utils.toDataClass
import io.legado.app.utils.EncoderUtils
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.slf4j.MDCContext
import mu.KLogger
import mu.KotlinLogging
import java.io.File
import java.net.URL
import java.net.URLDecoder.decode
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.UUID
import kotlin.coroutines.CoroutineContext

private val logger: KLogger = KotlinLogging.logger { }

/**
 * WebDAV 服务端：把用户书仓目录按 WebDAV 协议暴露给客户端（PROPFIND/MKCOL/PUT/GET/DELETE/MOVE/COPY/LOCK/UNLOCK）。
 * 路由在 init 里挂到 `/reader3/webdav*`，鉴权走 Basic，业务方法都从 URL 推导本地路径。
 */
class WebdavController(
    coroutineContext: CoroutineContext,
    router: Router,
    onHandlerError: (RoutingContext, Exception) -> Unit
) : BaseController(coroutineContext) {

    init {
        router.route("/reader3/webdav*").globalHandler {
            it.addHeadersEndHandler { _ ->
                val res = it.response()
                res.putHeader("DAV", "1,2")
                res.putHeader("Access-Control-Allow-Origin", "*")
                res.putHeader("Access-Control-Allow-Credentials", "true")
                res.putHeader("Access-Control-Expose-Headers", "DAV, content-length, Allow")
                res.putHeader("MS-Author-Via", "DAV")
                res.putHeader("Allow", "OPTIONS,DELETE,GET,PUT,PROPFIND,MKCOL,MOVE,COPY,LOCK,UNLOCK")
                if (appConfig.secure) {
                    res.putHeader("WWW-Authenticate", "Basic realm=\"Default realm\"")
                }
            }
            val rawMethod = it.request().rawMethod()
            if (!checkAuthorization(it)) {
                if (
                    rawMethod.equals("PROPFIND") ||
                    rawMethod.equals("MKCOL") ||
                    rawMethod.equals("PUT") ||
                    rawMethod.equals("GET") ||
                    rawMethod.equals("DELETE") ||
                    rawMethod.equals("MOVE") ||
                    rawMethod.equals("COPY") ||
                    rawMethod.equals("LOCK") ||
                    rawMethod.equals("UNLOCK")
                ) {
                    it.response().setStatusCode(401).end()
                    return@globalHandler
                }
                if (rawMethod.equals("OPTIONS") && it.request().getHeader("Authorization") != null) {
                    it.response().setStatusCode(401).end()
                    return@globalHandler
                }
            }

            when (rawMethod) {
                "PROPFIND" -> launch(MDCContext() + Dispatchers.IO) {
                    try {
                        webdavList(it)
                    } catch (e: Exception) {
                        onHandlerError(it, e)
                    }
                }

                "MKCOL" -> launch(MDCContext() + Dispatchers.IO) {
                    try {
                        webdavMkdir(it)
                    } catch (e: Exception) {
                        onHandlerError(it, e)
                    }
                }

                "PUT" -> launch(MDCContext() + Dispatchers.IO) {
                    try {
                        webdavUpload(it)
                    } catch (e: Exception) {
                        onHandlerError(it, e)
                    }
                }

                "GET" -> launch(MDCContext() + Dispatchers.IO) {
                    try {
                        webdavDownload(it)
                    } catch (e: Exception) {
                        onHandlerError(it, e)
                    }
                }

                "DELETE" -> launch(MDCContext() + Dispatchers.IO) {
                    try {
                        webdavDelete(it)
                    } catch (e: Exception) {
                        onHandlerError(it, e)
                    }
                }

                "MOVE" -> launch(MDCContext() + Dispatchers.IO) {
                    try {
                        webdavMove(it)
                    } catch (e: Exception) {
                        onHandlerError(it, e)
                    }
                }

                "COPY" -> launch(MDCContext() + Dispatchers.IO) {
                    try {
                        webdavCopy(it)
                    } catch (e: Exception) {
                        onHandlerError(it, e)
                    }
                }

                "LOCK" -> launch(MDCContext() + Dispatchers.IO) {
                    try {
                        webdavLock(it)
                    } catch (e: Exception) {
                        onHandlerError(it, e)
                    }
                }

                "UNLOCK" -> launch(MDCContext() + Dispatchers.IO) {
                    try {
                        webdavUnLock(it)
                    } catch (e: Exception) {
                        onHandlerError(it, e)
                    }
                }

                "OPTIONS" -> it.response().setStatusCode(200).end()
                else -> it.response().setStatusCode(405).end()
            }
        }
    }

    fun checkAuthorization(context: RoutingContext): Boolean {
        if (!appConfig.secure) {
            return true
        }
        var authorization: String? = context.request().getHeader("Authorization")
        logger.info("authorization: {}", authorization)
        if (authorization.isNullOrEmpty()) {
            return false
        }
        val auth = EncoderUtils.base64Decode(authorization.replace("Basic ", "", true)).split(":", limit = 2)
        if (auth.size < 2) {
            return false
        }
        val username = auth[0]
        val password = auth[1]
        var userMap: MutableMap<String, Map<String, Any>> = LinkedHashMap()
        val userMapJson = asJsonObject(getStorage("data", "users"))
        if (userMapJson != null) {
            userMap = userMapJson.map as MutableMap<String, Map<String, Any>>
        }
        val existedUser = userMap[username]
        if (existedUser == null) {
            return false
        }
        val userInfo = existedUser.toDataClass<User>()
        if (userInfo == null) {
            return false
        }
        val passwordEncrypted = genEncryptedPassword(password, userInfo.salt)
        if (passwordEncrypted != userInfo.password) {
            logger.info("user: {} password error", userInfo.username)
            return false
        }
        if (!userInfo.enable_webdav) {
            logger.info("user: {} enable_webdav: false", userInfo.username)
            return false
        }
        context.put("username", userInfo.username)
        return true
    }

    suspend fun webdavList(context: RoutingContext) {
        val home = getUserWebdavHome(context)
        var path = context.request().path().replace("/reader3/webdav/", "/", true)
        path = decode(path, "UTF-8")
        val file = File(home + path)
        if (!file.exists()) {
            context.response().setStatusCode(404).end()
            return
        }
        val xml = """<?xml version="1.0" encoding="utf-8"?>
            <D:multistatus xmlns:D="DAV:">
                %s
            </D:multistatus>
        """
        var dirResponse = """<D:response>
                <D:href>%s</D:href>
                <D:propstat>
                    <D:status>HTTP/1.1 200 OK</D:status>
                    <D:prop>
                        <D:getlastmodified>%s</D:getlastmodified>
                        <D:creationdate>%s</D:creationdate>
                        <D:resourcetype>
                            <D:collection />
                        </D:resourcetype>
                        <D:displayname>%s</D:displayname>
                    </D:prop>
                </D:propstat>
            </D:response>
        """
        var fileResponse = """<D:response>
                <D:href>%s</D:href>
                <D:propstat>
                    <D:status>HTTP/1.1 200 OK</D:status>
                    <D:prop>
                        <D:getlastmodified>%s</D:getlastmodified>
                        <D:creationdate>%s</D:creationdate>
                        <D:resourcetype />
                        <D:displayname>%s</D:displayname>
                        <D:getcontentlength>%s</D:getcontentlength>
                        <D:getcontenttype>%s</D:getcontenttype>
                    </D:prop>
                </D:propstat>
            </D:response>
        """
        var fileUrl = context.request().absoluteURI()
        val formatter: (File, String, Boolean) -> String = { f, url, showName ->
            val name = if (showName) f.name else ""
            val modifiedDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(f.lastModified())
            if (f.isFile) {
                String.format(
                    fileResponse, url, modifiedDate, modifiedDate, name, f.length(), ""
                )
            } else {
                String.format(dirResponse, url, modifiedDate, modifiedDate, name)
            }
        }
        var response: String? = null
        response = ""
        if (file.isFile) {
            response = String.format(xml, formatter(file, fileUrl, true))
            context.response().setStatusCode(207).end(response)
            return
        }
        if (file.isDirectory) {
            fileUrl = if (fileUrl.endsWith("/")) fileUrl else fileUrl + "/"
            response = formatter(file, fileUrl, false)
            file.listFiles().forEach {
                val fileName = URLEncoder.encode(it.name, "UTF-8")
                response = response + formatter(it, fileUrl + fileName, true)
            }
            response = String.format(xml, response)
            context.response().setStatusCode(207).end(response)
            return
        }
        context.response().setStatusCode(404).end()
    }

    suspend fun webdavMkdir(context: RoutingContext) {
        val home = getUserWebdavHome(context)
        var path = context.request().path().replace("/reader3/webdav/", "/", true)
        path = decode(path, "UTF-8")
        val file = File(home + path)
        if (file.exists()) {
            context.response().setStatusCode(201).end()
            return
        }
        try {
            file.mkdirs()
            context.response().setStatusCode(201).end()
        } catch (e: Exception) {
            context.response().setStatusCode(500).end()
        }
    }

    suspend fun webdavUpload(context: RoutingContext) {
        val home = getUserWebdavHome(context)
        var path = context.request().path().replace("/reader3/webdav/", "/", true)
        path = decode(path, "UTF-8")
        val file = File(home + path)
        if (!file.parentFile.exists()) {
            context.response().setStatusCode(409).end()
            return
        }
        if (file.isDirectory) {
            context.response().setStatusCode(405).end()
            return
        }
        if (file.exists()) {
            file.delete()
        }
        try {
            file.writeBytes(context.getBody().getBytes())
            if (file.toString().indexOf("bookProgress") > 0 && file.toString().indexOf(".json") > 0) {
                val userNameSpace = getUserNameSpace(context)
                val bookController = BookController(coroutineContext)
                bookController.syncBookProgressFromWebdav(file, userNameSpace)
            }
            context.response().setStatusCode(201).end()
        } catch (e: Exception) {
            context.response().setStatusCode(500).end()
        }
    }

    suspend fun webdavDownload(context: RoutingContext) {
        val home = getUserWebdavHome(context)
        var path = context.request().path().replace("/reader3/webdav/", "/", true)
        path = decode(path, "UTF-8")
        val file = File(home + path)
        if (!file.exists()) {
            context.response().setStatusCode(404).end()
            return
        }
        if (file.isDirectory) {
            context.response().setStatusCode(405).end()
            return
        }
        context.response().putHeader("Cache-Control", "86400")
            .putHeader(
                "Content-Disposition",
                "attachment; filename=" + URLEncoder.encode(file.name, "UTF-8")
            )
            .sendFile(file.toString())
    }

    suspend fun webdavDelete(context: RoutingContext) {
        val home = getUserWebdavHome(context)
        var path = context.request().path().replace("/reader3/webdav/", "/", true)
        path = decode(path, "UTF-8")
        val file = File(home + path)
        if (!file.exists()) {
            context.response().setStatusCode(404).end()
            return
        }
        file.deleteRecursively()
        context.response().setStatusCode(200).end()
    }

    suspend fun webdavMove(context: RoutingContext) {
        val home = getUserWebdavHome(context)
        var path = context.request().path().replace("/reader3/webdav/", "/", true)
        path = decode(path, "UTF-8")
        val file = File(home + path)
        if (!file.exists()) {
            context.response().setStatusCode(412).end()
            return
        }
        val destination = context.request().getHeader("Destination")
        if (destination == null) {
            context.response().setStatusCode(400).end()
            return
        }
        val destinationUrl = URL(destination)
        val destPath = destinationUrl.path?.replace("/reader3/webdav/", "/", true)
        if (destPath == null) {
            context.response().setStatusCode(400).end()
            return
        }
        val overwrite = context.request().getHeader("Overwrite")
        val destinationFile = File(home + decode(destPath, "UTF-8"))
        if (destinationFile.exists()) {
            if (overwrite == null || overwrite.isEmpty()) {
                context.response().setStatusCode(412).end()
                return
            }
            destinationFile.deleteRecursively()
        }
        file.renameTo(destinationFile)
        context.response().setStatusCode(201).end()
    }

    suspend fun webdavCopy(context: RoutingContext) {
        val home = getUserWebdavHome(context)
        var path = context.request().path().replace("/reader3/webdav/", "/", true)
        path = decode(path, "UTF-8")
        val file = File(home + path)
        if (!file.exists()) {
            context.response().setStatusCode(412).end()
            return
        }
        val destination = context.request().getHeader("Destination")
        if (destination == null) {
            context.response().setStatusCode(400).end()
            return
        }
        val destinationUrl = URL(destination)
        val destPath = destinationUrl.path?.replace("/reader3/webdav/", "/", true)
        if (destPath == null) {
            context.response().setStatusCode(400).end()
            return
        }
        val overwrite = context.request().getHeader("Overwrite")
        val destinationFile = File(home + decode(destPath, "UTF-8"))
        if (destinationFile.exists()) {
            if (overwrite == null || overwrite.isEmpty()) {
                context.response().setStatusCode(412).end()
                return
            }
            destinationFile.deleteRecursively()
        }
        file.copyRecursively(destinationFile)
        context.response().setStatusCode(201).end()
    }

    suspend fun webdavLock(context: RoutingContext) {
        val response = """<?xml version="1.0" encoding="utf-8"?>
        <D:prop xmlns:D="DAV:">
            <D:lockdiscovery>
                <D:activelock>
                    <D:locktype>
                        <write />
                    </D:locktype>
                    <D:lockscope>
                        <exclusive />
                    </D:lockscope>
                    <D:locktoken>
                        <D:href>%s</D:href>
                    </D:locktoken>
                    <D:lockroot>
                        <D:href>%s</D:href>
                    </D:lockroot>
                    <D:depth>infinity</D:depth>
                    <D:owner>
                        <a:href xmlns:a="DAV:">http://www.apple.com/webdav_fs/</a:href>
                    </D:owner>
                    <D:timeout>%s</D:timeout>
                </D:activelock>
            </D:lockdiscovery>
        </D:prop>
        """
        val lockToken = "urn:uuid:" + UUID.randomUUID()
        val timeout = context.request().getHeader("Timeout") ?: "Second-3600"
        val fileUrl = context.request().absoluteURI()
        context.response().putHeader("Lock-Token", lockToken).setStatusCode(200)
            .end(String.format(response, lockToken, fileUrl, timeout))
    }

    suspend fun webdavUnLock(context: RoutingContext) {
        val lockToken = context.request().getHeader("Lock-Token")
        if (lockToken == null) {
            context.response().setStatusCode(400).end()
            return
        }
        context.response().putHeader("Lock-Token", lockToken).setStatusCode(204).end()
    }

    suspend fun backupToWebdav(context: RoutingContext): ReturnData? {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (appConfig.secure) {
            val userInfo = context.get<User>("userInfo")
            if (userInfo == null) {
                return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
            }
            if (!userInfo.enable_webdav) {
                return returnData.setErrorMsg("未开启webdav功能")
            }
        }
        val bookController = BookController(coroutineContext)
        val userNameSpace = getUserNameSpace(context)
        val latestZipFilePath = bookController.getLastBackFileFromWebdav(userNameSpace)
        if (!bookController.saveToWebdav(userNameSpace, latestZipFilePath)) {
            return returnData.setErrorMsg("备份失败")
        }
        return returnData.setData("")
    }
}
