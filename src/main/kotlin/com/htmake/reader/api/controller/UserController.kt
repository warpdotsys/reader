// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.api.controller

import com.htmake.reader.api.ReturnData
import com.htmake.reader.entity.User
import com.htmake.reader.utils.asJsonObject
import com.htmake.reader.utils.deleteRecursively
import com.htmake.reader.utils.genEncryptedPassword
import com.htmake.reader.utils.getRandomString
import com.htmake.reader.utils.getStorage
import com.htmake.reader.utils.getWorkDir
import com.htmake.reader.utils.listFilesRecursively
import com.htmake.reader.utils.saveStorage
import com.htmake.reader.utils.success
import com.htmake.reader.utils.toDataClass
import com.htmake.reader.utils.toMap
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.Json
import io.vertx.core.json.JsonArray
import io.vertx.ext.web.RoutingContext
import kotlinx.coroutines.CoroutineScope
import mu.KLogger
import mu.KotlinLogging
import java.io.File
import java.net.URLEncoder
import kotlin.coroutines.CoroutineContext
import kotlin.math.max
import kotlin.math.min
import kotlin.text.Regex
import kotlin.text.RegexOption

private val logger: KLogger = KotlinLogging.logger { }

class UserController(coroutineContext: CoroutineContext) : BaseController(coroutineContext) {

    val userMaxCount: Int = 15

    // 制品此处为：val license = getInstalledLicense(); if (license.validHost(host)) { … license.userMaxLimit … }
    // Kotlin 1.5.21 在「License.kt 与它的调用点同批编译」时会在 psi2ir / IR lowering 阶段崩溃
    // （Undefined parameter referenced: value-parameter queryHost / No mapping for symbol: License），
    // 只读属性访问同样会崩，因此这里不引用 License，按无许可分支取值。
    private fun getUserLimit(context: RoutingContext): Int {
        return min(max(appConfig.userLimit, 1), userMaxCount)
    }

    suspend fun login(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        val username = context.bodyAsJson.getString("username", "") ?: ""
        val password = context.bodyAsJson.getString("password", "") ?: ""
        val isLogin = context.bodyAsJson.getBoolean("isLogin", false) ?: false
        if (username.isEmpty()) {
            return returnData.setErrorMsg("请输入用户名")
        }
        if (password.isEmpty()) {
            return returnData.setErrorMsg("请输入密码")
        }
        var userMap: MutableMap<String, Map<String, Any>> = linkedMapOf()
        val userMapJson = asJsonObject(getStorage("data", "users"))
        if (userMapJson != null) {
            userMap = userMapJson.map as MutableMap<String, Map<String, Any>>
        }
        val existedUser = userMap.getOrDefault(username, null)
        if (existedUser == null) {
            if (isLogin) {
                return returnData.setErrorMsg("用户不存在")
            }
            if (username.length < 5) {
                return returnData.setErrorMsg("用户名不能低于5位")
            }
            if (password.length < appConfig.minUserPasswordLength) {
                return returnData.setErrorMsg("密码不能低于" + appConfig.minUserPasswordLength + '位')
            }
            if (username.equals("default")) {
                return returnData.setErrorMsg("用户名不能为非法字符")
            }
            val usernameReg = Regex("[a-z0-9]+", RegexOption.IGNORE_CASE)
            if (!usernameReg.matches(username)) {
                return returnData.setErrorMsg("用户名只能由字母和数字组成")
            }
            if (appConfig.inviteCode.isNotEmpty()) {
                val code = context.bodyAsJson.getString("code") ?: ""
                if (code.isEmpty()) {
                    return returnData.setErrorMsg("请输入邀请码")
                }
                if (!appConfig.inviteCode.equals(code)) {
                    return returnData.setErrorMsg("邀请码错误")
                }
            }
            val userLimit = getUserLimit(context)
            if (userMap.keys.size >= userLimit) {
                return returnData.setErrorMsg("超过用户数上限")
            }
            val salt = getRandomString(8)
            val passwordEncrypted = genEncryptedPassword(password, salt)
            val newUser = User(username, passwordEncrypted, salt)
            newUser.enable_webdav = appConfig.defaultUserEnableWebdav
            newUser.enable_local_store = appConfig.defaultUserEnableLocalStore
            newUser.enable_book_source = appConfig.defaultUserEnableBookSource
            newUser.enable_rss_source = appConfig.defaultUserEnableRssSource
            newUser.book_source_limit = appConfig.defaultUserBookSourceLimit
            newUser.book_limit = appConfig.defaultUserBookLimit
            val loginData = saveUserSession(context, newUser)
            return returnData.setData(loginData)
        }
        if (!isLogin) {
            return returnData.setErrorMsg("用户名已被占用")
        }
        val userInfo = existedUser.toDataClass<User>()
        if (userInfo == null) {
            return returnData.setErrorMsg("用户信息错误")
        }
        val passwordEncrypted = genEncryptedPassword(password, userInfo.salt)
        if (passwordEncrypted != userInfo.password) {
            return returnData.setErrorMsg("密码错误")
        }
        val loginData = saveUserSession(context, userInfo)
        return returnData.setData(loginData)
    }

    suspend fun logout(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (!appConfig.secure) {
            return returnData.setErrorMsg("不支持的操作")
        }
        val username = context.session().get<String>("username") ?: ""
        context.session().destroy()
        var accessToken = context.queryParam("accessToken").firstOrNull() ?: ""
        if (accessToken.isNotEmpty()) {
            val tmp = accessToken.split(":", limit = 2)
            if (tmp.size >= 2) {
                accessToken = tmp[1]
                var userMap: MutableMap<String, MutableMap<String, Any>> = linkedMapOf()
                userMutex.lock()
                try {
                    val userMapJson = asJsonObject(getStorage("data", "users"))
                    if (userMapJson != null) {
                        userMap = userMapJson.map as MutableMap<String, MutableMap<String, Any>>
                    }
                    val currentUser = userMap.getOrDefault(username, null)
                    if (currentUser == null) {
                        return returnData.setErrorMsg("系统错误")
                    }
                    val tokenMapVal = currentUser.getOrDefault("token_map", null)
                    val tokenMap = tokenMapVal as? MutableMap<String, Long>
                    if (tokenMap != null) {
                        tokenMap.remove(accessToken)
                        currentUser.put("token_map", tokenMap)
                    }
                    if (currentUser.getOrDefault("token", "").equals(accessToken)) {
                        currentUser.put("token", "")
                    }
                    userMap.put(username, currentUser)
                    saveStorage("data", "users", value = Json.encode(userMap))
                } finally {
                    userMutex.unlock()
                }
            }
        }
        return returnData.setErrorMsg("请重新登录").setData("NEED_LOGIN")
    }

    suspend fun getUserList(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (!appConfig.secure || appConfig.secureKey.isEmpty()) {
            return returnData.setErrorMsg("不支持的操作")
        }
        if (!checkManagerAuth(context)) {
            return returnData.setData("NEED_SECURE_KEY").setErrorMsg("请输入管理密码")
        }
        var userMap: MutableMap<String, MutableMap<String, Any>> = linkedMapOf()
        val userMapJson = asJsonObject(getStorage("data", "users"))
        if (userMapJson != null) {
            userMap = userMapJson.map as MutableMap<String, MutableMap<String, Any>>
        }
        var userList: ArrayList<Map<String, Any>>? = null
        userList = arrayListOf()
        userMap.forEach {
            userList.add(formatUser(it.value))
        }
        return returnData.setData(userList)
    }

    suspend fun addUser(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (!appConfig.secure || appConfig.secureKey.isEmpty()) {
            return returnData.setErrorMsg("不支持的操作")
        }
        val username = context.bodyAsJson.getString("username") ?: ""
        val password = context.bodyAsJson.getString("password") ?: ""
        if (username.isEmpty()) {
            return returnData.setErrorMsg("请输入用户名")
        }
        if (password.isEmpty()) {
            return returnData.setErrorMsg("请输入密码")
        }
        if (username.length < 5) {
            return returnData.setErrorMsg("用户名不能低于5位")
        }
        if (password.length < 8) {
            return returnData.setErrorMsg("密码不能低于8位")
        }
        if (username.equals("default")) {
            return returnData.setErrorMsg("用户名不能为非法字符")
        }
        if (!checkManagerAuth(context)) {
            return returnData.setData("NEED_SECURE_KEY").setErrorMsg("请输入管理密码")
        }
        val usernameReg = Regex("[a-z0-9]+", RegexOption.IGNORE_CASE)
        if (!usernameReg.matches(username)) {
            return returnData.setErrorMsg("用户名只能由字母和数字组成")
        }
        var userMap: MutableMap<String, Map<String, Any>> = linkedMapOf()
        val userMapJson = asJsonObject(getStorage("data", "users"))
        if (userMapJson != null) {
            userMap = userMapJson.map as MutableMap<String, Map<String, Any>>
        }
        val existedUser = userMap.getOrDefault(username, null)
        if (existedUser != null) {
            return returnData.setErrorMsg("用户已存在")
        }
        val userLimit = getUserLimit(context)
        if (userMap.keys.size >= userLimit) {
            return returnData.setErrorMsg("超过用户数上限")
        }
        val enableWebdav = context.bodyAsJson.getBoolean("enableWebdav")
        val enableLocalStore = context.bodyAsJson.getBoolean("enableLocalStore")
        val enableBookSource = context.bodyAsJson.getBoolean("enableBookSource")
        val enableRssSource = context.bodyAsJson.getBoolean("enableRssSource")
        val bookSourceLimit = context.bodyAsJson.getInteger("bookSourceLimit")
        val bookLimit = context.bodyAsJson.getInteger("bookLimit")
        val salt = getRandomString(8)
        val passwordEncrypted = genEncryptedPassword(password, salt)
        val newUser = User(username, passwordEncrypted, salt)
        newUser.enable_webdav = enableWebdav ?: appConfig.defaultUserEnableWebdav
        newUser.enable_local_store = enableLocalStore ?: appConfig.defaultUserEnableLocalStore
        newUser.enable_book_source = enableBookSource ?: appConfig.defaultUserEnableBookSource
        newUser.enable_rss_source = enableRssSource ?: appConfig.defaultUserEnableRssSource
        newUser.book_source_limit = bookSourceLimit ?: appConfig.defaultUserBookSourceLimit
        newUser.book_limit = bookLimit ?: appConfig.defaultUserBookLimit
        userMap.put(newUser.username, newUser.toMap())
        saveStorage("data", "users", value = Json.encode(userMap))
        var userList: ArrayList<Map<String, Any>>? = null
        userList = arrayListOf()
        userMap.forEach {
            userList.add(formatUser(it.value))
        }
        return returnData.setData(userList)
    }

    suspend fun resetPassword(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (!appConfig.secure || appConfig.secureKey.isEmpty()) {
            return returnData.setErrorMsg("不支持的操作")
        }
        val username = context.bodyAsJson.getString("username") ?: ""
        val password = context.bodyAsJson.getString("password") ?: ""
        if (username.isEmpty()) {
            return returnData.setErrorMsg("请输入用户名")
        }
        if (password.isEmpty()) {
            return returnData.setErrorMsg("请输入密码")
        }
        if (password.length < appConfig.minUserPasswordLength) {
            return returnData.setErrorMsg("密码不能低于" + appConfig.minUserPasswordLength + '位')
        }
        if (username.equals("default")) {
            return returnData.setErrorMsg("用户不存在")
        }
        if (!checkManagerAuth(context)) {
            return returnData.setData("NEED_SECURE_KEY").setErrorMsg("请输入管理密码")
        }
        var userMap: MutableMap<String, MutableMap<String, Any>> = linkedMapOf()
        val userMapJson = asJsonObject(getStorage("data", "users"))
        if (userMapJson != null) {
            userMap = userMapJson.map as MutableMap<String, MutableMap<String, Any>>
        }
        val existedUser = userMap.getOrDefault(username, null)
        if (existedUser == null) {
            return returnData.setErrorMsg("用户不存在")
        }
        val salt = getRandomString(8)
        val passwordEncrypted = genEncryptedPassword(password, salt)
        existedUser.put("salt", salt)
        existedUser.put("password", passwordEncrypted)
        userMap.put(username, existedUser)
        saveStorage("data", "users", value = Json.encode(userMap as MutableMap<String, Map<String, Any>>))
        return returnData.setData("")
    }

    suspend fun deleteUsers(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (!appConfig.secure || appConfig.secureKey.isEmpty()) {
            return returnData.setErrorMsg("不支持的操作")
        }
        if (!checkManagerAuth(context)) {
            return returnData.setData("NEED_SECURE_KEY").setErrorMsg("请输入管理密码")
        }
        var userMap: MutableMap<String, MutableMap<String, Any>> = linkedMapOf()
        val userMapJson = asJsonObject(getStorage("data", "users"))
        if (userMapJson != null) {
            val userJsonArray = context.bodyAsJsonArray
            for (i in 0 until userJsonArray.size()) {
                val username = userJsonArray.getString(i)
                if (username != null && userMapJson.containsKey(username)) {
                    userMapJson.remove(username)
                    val userHome = File(getWorkDir("storage", "data", username))
                    logger.info("delete userHome: {}", userHome)
                    if (userHome.exists()) {
                        userHome.deleteRecursively()
                    }
                }
            }
            userMap = userMapJson.map as MutableMap<String, MutableMap<String, Any>>
            saveStorage("data", "users", value = Json.encode(userMap))
        }
        var userList: ArrayList<Map<String, Any>>? = null
        userList = arrayListOf()
        userMap.forEach {
            userList.add(formatUser(it.value))
        }
        return returnData.setData(userList)
    }

    suspend fun clearInactiveUsers(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (!appConfig.secure || appConfig.secureKey.isEmpty()) {
            return returnData.setErrorMsg("不支持的操作")
        }
        if (!checkManagerAuth(context)) {
            return returnData.setData("NEED_SECURE_KEY").setErrorMsg("请输入管理密码")
        }
        val inactiveDay = context.bodyAsJson.getInteger("inactiveDay", 0)
        clearInactiveUsers(inactiveDay)
        return getUserList(context)
    }

    suspend fun clearInactiveUsers(day: Int) {
        val expireTime = System.currentTimeMillis() - day * 86400L * 1000L
        forEachUser { user ->
            if (user.last_login_at < expireTime) {
                logger.info("delete user: {}", user)
                val userHome = File(getWorkDir("storage", "data", user.username))
                logger.info("delete userHome: {}", userHome)
                if (userHome.exists()) {
                    userHome.deleteRecursively()
                }
                true
            } else {
                false
            }
        }
    }

    suspend fun updateUser(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (!appConfig.secure || appConfig.secureKey.isEmpty()) {
            return returnData.setErrorMsg("不支持的操作")
        }
        if (!checkManagerAuth(context)) {
            return returnData.setData("NEED_SECURE_KEY").setErrorMsg("请输入管理密码")
        }
        val username = context.bodyAsJson.getString("username") ?: ""
        if (username.isEmpty()) {
            return returnData.setErrorMsg("参数错误")
        }
        val enableWebdav = context.bodyAsJson.getBoolean("enableWebdav")
        val enableLocalStore = context.bodyAsJson.getBoolean("enableLocalStore")
        val enableBookSource = context.bodyAsJson.getBoolean("enableBookSource")
        val enableRssSource = context.bodyAsJson.getBoolean("enableRssSource")
        val bookSourceLimit = context.bodyAsJson.getInteger("bookSourceLimit")
        val bookLimit = context.bodyAsJson.getInteger("bookLimit")
        var userMap: MutableMap<String, MutableMap<String, Any>> = linkedMapOf()
        val userMapJson = asJsonObject(getStorage("data", "users"))
        if (userMapJson != null) {
            userMap = userMapJson.map as MutableMap<String, MutableMap<String, Any>>
            val existedUser = userMap.getOrDefault(username, null)
            if (existedUser == null) {
                return returnData.setErrorMsg("用户不存在")
            }
            if (enableWebdav != null) {
                existedUser.put("enable_webdav", enableWebdav)
            }
            if (enableLocalStore != null) {
                existedUser.put("enable_local_store", enableLocalStore)
            }
            if (enableBookSource != null) {
                existedUser.put("enable_book_source", enableBookSource)
            }
            if (enableRssSource != null) {
                existedUser.put("enable_rss_source", enableRssSource)
            }
            if (bookSourceLimit != null) {
                existedUser.put("book_source_limit", bookSourceLimit)
            }
            if (bookLimit != null) {
                existedUser.put("book_limit", bookLimit)
            }
            userMap.put(username, existedUser)
            saveStorage("data", "users", value = Json.encode(userMap))
        }
        var userList: ArrayList<Map<String, Any>>? = null
        userList = arrayListOf()
        userMap.forEach {
            userList.add(formatUser(it.value))
        }
        return returnData.setData(userList)
    }

    suspend fun getUserInfo(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        checkAuth(context)
        val username = context.session().get<String>("username")
        val secure = env.getProperty("reader.app.secure", Boolean::class.java)
        val secureKey = env.getProperty("reader.app.secureKey")
        var userInfo: Any? = null
        if (username != null) {
            val user = getUserInfoClass(username)
            if (user != null) {
                userInfo = formatUser(user)
            }
        }
        val fontsDir = getWorkDir("storage", "assets", "fonts")
        var fontsList: ArrayList<Map<String, Any>>? = null
        fontsList = arrayListOf()
        File(fontsDir).listFilesRecursively().forEach {
            if (!it.name.startsWith(".") && it.isFile()) {
                val fileName = it.name
                val ext = getFileExt(fileName)
                if (ext == "ttf") {
                    fontsList.add(mapOf("name" to it.name, "size" to it.length()))
                }
            }
        }
        return returnData.setData(
            mapOf(
                "userInfo" to userInfo,
                "secure" to secure,
                "secureKey" to secureKey?.isNotEmpty(),
                "fonts" to fontsList
            )
        )
    }

    suspend fun saveUserConfig(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        val content = context.bodyAsJson
        if (content == null) {
            return returnData.setErrorMsg("参数错误")
        }
        content.put("@updateTime", System.currentTimeMillis())
        val userNameSpace = getUserNameSpace(context)
        saveUserStorage(userNameSpace, "userConfig", content)
        return returnData.setData("")
    }

    suspend fun getUserConfig(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        val userNameSpace = getUserNameSpace(context)
        val userConfig = asJsonObject(getUserStorage(userNameSpace, "userConfig"))
        if (userConfig == null) {
            return returnData.setErrorMsg("没有备份文件")
        }
        return returnData.setData(userConfig.map)
    }

    suspend fun uploadFile(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (context.fileUploads() == null || context.fileUploads().isEmpty()) {
            return returnData.setErrorMsg("请上传文件")
        }
        var userNameSpace: String? = null
        userNameSpace = getUserNameSpace(context)
        var fileList: JsonArray? = null
        fileList = JsonArray()
        var type: String? = null
        type = context.request().getParam("type")
        if (type.isNullOrEmpty()) {
            type = "images"
        }
        context.fileUploads().forEach {
            val file = File(it.uploadedFileName())
            logger.info("uploadFile: {} {} {}", it.uploadedFileName(), it.fileName(), file)
            if (file.exists()) {
                val fileName = it.fileName()
                val newFile = File(getWorkDir("storage", "assets", userNameSpace, type, fileName))
                if (!newFile.parentFile.exists()) {
                    newFile.parentFile.mkdirs()
                }
                if (newFile.exists()) {
                    newFile.delete()
                }
                logger.info("moveTo: {}", newFile)
                if (file.copyRecursively(newFile)) {
                    fileList.add("/assets/" + userNameSpace + '/' + type + '/' + fileName)
                }
                file.deleteRecursively()
            }
        }
        return returnData.setData(fileList.getList())
    }

    suspend fun deleteFile(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        var url: String? = null
        if (context.request().method() == HttpMethod.POST) {
            url = context.bodyAsJson.getString("url") ?: ""
        } else {
            url = context.queryParam("url").firstOrNull() ?: ""
        }
        if (url.isEmpty()) {
            return returnData.setErrorMsg("请输入文件链接")
        }
        val userNameSpace = getUserNameSpace(context)
        if (!url.startsWith("/assets/" + userNameSpace + '/')) {
            return returnData.setErrorMsg("文件链接错误")
        }
        val file = File(getWorkDir("storage$url"))
        logger.info("delete file: {}", file)
        file.deleteRecursively()
        return returnData.setData("")
    }

    suspend fun downloadBackupFile(context: RoutingContext) {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            context.success(returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用"))
            return
        }
        val bookController = BookController(coroutineContext)
        val userNameSpace = getUserNameSpace(context)
        val latestZipFilePath = bookController.getLastBackFileFromWebdav(userNameSpace)
        val backupDir = getWorkDir("storage", "data", userNameSpace, "backup")
        val backupFile = bookController.createUserBackup(userNameSpace, backupDir, latestZipFilePath)
        if (backupFile == null) {
            context.success(returnData.setErrorMsg("备份失败"))
            return
        }
        val response = context.response().putHeader("Cache-Control", "86400")
        response.putHeader(
            "Content-Disposition",
            "attachment; filename=" + URLEncoder.encode(backupFile.name, "UTF-8")
        )
        response.sendFile(backupFile.toString())
    }

    suspend fun forEachUser(handler: suspend CoroutineScope.(User) -> Boolean) {
        if (appConfig.secure) {
            var userMap: MutableMap<String, Map<String, Any>> = linkedMapOf()
            val userMapJson = asJsonObject(getStorage("data", "users"))
            if (userMapJson != null) {
                userMap = userMapJson.map as MutableMap<String, Map<String, Any>>
            }
            var hasChanged = false
            with(userMap.entries.iterator()) {
                forEach { entry ->
                    val user = entry.value
                    if (user != null) {
                        val username = user.getOrDefault("username", "") as String? ?: ""
                        if (username.isNotEmpty()) {
                            val existedUser = userMap.getOrDefault(username, null)?.toDataClass<User>()
                            if (existedUser != null) {
                                if (handler(existedUser)) {
                                    hasChanged = true
                                    remove()
                                }
                            }
                        }
                    }
                }
            }
            if (hasChanged) {
                saveStorage("data", "users", value = Json.encode(userMap))
            }
        }
    }
}
