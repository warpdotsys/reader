// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.api.controller

import com.htmake.reader.api.ReturnData
import com.htmake.reader.entity.User
import com.htmake.reader.utils.SpringContextUtils
import com.htmake.reader.utils.asJsonArray
import com.htmake.reader.utils.asJsonObject
import com.htmake.reader.utils.deleteRecursively
import com.htmake.reader.utils.getStorageFile
import com.htmake.reader.utils.getWorkDir
import com.htmake.reader.utils.parseJsonStringList
import com.htmake.reader.utils.success
import io.legado.app.data.entities.BookSource
import io.vertx.core.Future
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.client.WebClient
import io.vertx.kotlin.coroutines.awaitResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.slf4j.MDCContext
import mu.KLogger
import mu.KotlinLogging
import java.io.File
import kotlin.coroutines.CoroutineContext

private val logger: KLogger = KotlinLogging.logger { }

class BookSourceController(coroutineContext: CoroutineContext) : BaseController(coroutineContext) {

    private var webClient: WebClient =
        SpringContextUtils.getBean("webClient", WebClient::class.java)

    fun getUserBookSourceJsonOpt(
        userNameSpace: String,
        fields: Set<String>? = null,
        checkNotEmpty: Set<String>? = null
    ): JsonArray? {
        var bookSourceFile = getStorageFile("data", userNameSpace, "bookSource")
        if (!bookSourceFile.exists()) {
            bookSourceFile = getStorageFile("data", "default", "bookSource")
        }
        val bookSourceList = parseJsonStringList(
            bookSourceFile,
            fields,
            checkNotEmpty = checkNotEmpty
        )
        return bookSourceList
    }

    fun getUserBookSourceJson(userNameSpace: String): JsonArray? {
        var bookSourceList = asJsonArray(getUserStorage(userNameSpace, "bookSource"))
        if (bookSourceList == null && !userNameSpace.equals("default")) {
            val systemBookSourceList = asJsonArray(getUserStorage("default", "bookSource"))
            if (systemBookSourceList != null) {
                bookSourceList = systemBookSourceList
            }
        }
        return bookSourceList
    }

    suspend fun canEditBookSource(context: RoutingContext): Boolean {
        if (!appConfig.secure) {
            return true
        }
        val userInfo = context.get<User>("userInfo")
        if (userInfo == null) {
            return false
        }
        return userInfo.enable_book_source
    }

    suspend fun saveBookSource(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (!canEditBookSource(context)) {
            return returnData.setErrorMsg("权限不足")
        }
        val bookSource = BookSource.fromJson(context.bodyAsString).getOrNull()
        if (bookSource == null) {
            return returnData.setErrorMsg("参数错误")
        }
        val userNameSpace = getUserNameSpace(context)
        var bookSourceList = getUserBookSourceJson(userNameSpace) ?: JsonArray()
        val urlMap = LinkedHashMap<String, Int>()
        for (i in 0 until bookSourceList.size()) {
            val bookSourceUrl =
                bookSourceList.getJsonObject(i).getString("bookSourceUrl")
            urlMap[bookSourceUrl] = i
        }
        val existIndex = urlMap.getOrDefault(bookSource.bookSourceUrl, -1)
        if (existIndex >= 0) {
            val sourceList = bookSourceList.list
            sourceList[existIndex] = JsonObject.mapFrom(bookSource)
            bookSourceList = JsonArray(sourceList)
        } else {
            val userInfo = context.get<User>("userInfo")
            if (userInfo != null && bookSourceList.size() >= userInfo.book_source_limit) {
                return returnData.setErrorMsg("你已达到书源数上限，请联系管理员")
            }
            bookSourceList.add(JsonObject.mapFrom(bookSource))
        }
        saveUserStorage(userNameSpace, "bookSource", bookSourceList)
        generateBookSourceMap(userNameSpace, bookSourceList)
        return returnData.setData("")
    }

    suspend fun saveBookSources(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (!canEditBookSource(context)) {
            return returnData.setErrorMsg("权限不足")
        }
        val bookSourceJsonArray = context.bodyAsJsonArray
        if (bookSourceJsonArray == null) {
            return returnData.setErrorMsg("参数错误")
        }
        return saveBookSources(context, bookSourceJsonArray)
    }

    fun saveBookSources(context: RoutingContext, bookSourceJsonArray: JsonArray): ReturnData {
        return saveUserBookSources(
            getUserNameSpace(context),
            context.get<User>("userInfo"),
            bookSourceJsonArray
        )
    }

    fun saveUserBookSources(
        userNameSpace: String,
        userInfo: User?,
        bookSourceJsonArray: JsonArray
    ): ReturnData {
        val returnData = ReturnData()
        var bookSourceList = getUserBookSourceJson(userNameSpace)
        if (bookSourceList == null) {
            bookSourceList = JsonArray()
        }
        val urlMap = LinkedHashMap<String, Int>()
        for (i in 0 until bookSourceList.size()) {
            val bookSourceUrl =
                bookSourceList.getJsonObject(i).getString("bookSourceUrl")
            urlMap[bookSourceUrl] = i
        }
        var isOverLimit = false
        var addCnt = 0
        val maxIndex = bookSourceList.size() - 1
        val updateIndex = LinkedHashSet<Int>()
        for (k in 0 until bookSourceJsonArray.size()) {
            val bookSource = try {
                BookSource.fromJson(bookSourceJsonArray.getJsonObject(k).toString()).getOrNull()
            } catch (e: Exception) {
                null
            }
            if (bookSource == null) continue
            val existIndex = urlMap.getOrDefault(bookSource.bookSourceUrl, -1)
            if (existIndex >= 0) {
                bookSourceList.set(existIndex, JsonObject.mapFrom(bookSource))
                if (existIndex > maxIndex) continue
                updateIndex.add(existIndex)
                continue
            }
            if (userInfo != null && bookSourceList.size() >= userInfo.book_source_limit) {
                isOverLimit = true
                break
            }
            addCnt++
            bookSourceList.add(JsonObject.mapFrom(bookSource))
            urlMap[bookSource.bookSourceUrl] = bookSourceList.size() - 1
        }
        saveUserStorage(userNameSpace, "bookSource", bookSourceList)
        generateBookSourceMap(userNameSpace, bookSourceList)
        val tip = "新增${addCnt}条书源，更新${updateIndex.size}条书源"
        if (isOverLimit) {
            return returnData.setErrorMsg(tip + "。你已达到书源数上限，请联系管理员")
        }
        return returnData.setData("", tip)
    }

    suspend fun getBookSource(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        checkAuth(context)
        var bookSourceUrl: String
        if (context.request().method() == HttpMethod.POST) {
            bookSourceUrl = context.bodyAsJson.getString("bookSourceUrl")
        } else {
            bookSourceUrl = context.queryParam("bookSourceUrl").firstOrNull() ?: ""
        }
        if (bookSourceUrl.isEmpty()) {
            return returnData.setErrorMsg("书源链接不能为空")
        }
        val userNameSpace = getUserNameSpace(context)
        val urlMap = getBookSourceMap(userNameSpace)
        val existIndex = urlMap.getOrDefault(bookSourceUrl, -1)
        if (existIndex < 0) {
            return returnData.setErrorMsg("书源信息不存在")
        }
        var bookSourceFile = getStorageFile("data", userNameSpace, "bookSource")
        if (!bookSourceFile.exists()) {
            bookSourceFile = getStorageFile("data", "default", "bookSource")
        }
        val bookSourceList = parseJsonStringList(
            bookSourceFile,
            startIndex = existIndex,
            endIndex = existIndex
        ) ?: return returnData.setErrorMsg("书源信息不存在")
        val data = JsonObject(bookSourceList.getString(0)).map
        return returnData.setData(data)
    }

    suspend fun getBookSources(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        checkAuth(context)
        var simple = 0
        if (context.request().method() == HttpMethod.POST) {
            simple = context.bodyAsJson.getInteger("simple", 0)
        } else {
            simple = context.queryParam("simple").firstOrNull()?.toInt() ?: 0
        }
        val userNameSpace = getUserNameSpace(context)
        val bookSourceList = getUserBookSourceJsonOpt(
            userNameSpace,
            if (simple > 0) setOf("bookSourceGroup", "bookSourceName", "bookSourceUrl") else null,
            if (simple > 0) setOf("exploreUrl") else null
        )
        if (bookSourceList != null) {
            return returnData.setData(bookSourceList.list.map { JsonObject(it as String).map })
        }
        return returnData.setData(ArrayList<Any>())
    }

    suspend fun deleteBookSource(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (!canEditBookSource(context)) {
            return returnData.setErrorMsg("权限不足")
        }
        val bookSource = BookSource.fromJson(context.bodyAsString).getOrNull()
        if (bookSource == null) {
            return returnData.setErrorMsg("参数错误")
        }
        val userNameSpace = getUserNameSpace(context)
        var bookSourceList = getUserBookSourceJson(userNameSpace)
        if (bookSourceList == null) {
            bookSourceList = JsonArray()
        }
        val urlMap = getBookSourceMap(userNameSpace)
        val existIndex = urlMap.getOrDefault(bookSource.bookSourceUrl, -1)
        if (existIndex >= 0) {
            bookSourceList.remove(existIndex)
        }
        saveUserStorage(userNameSpace, "bookSource", bookSourceList)
        generateBookSourceMap(userNameSpace, bookSourceList)
        return returnData.setData("")
    }

    suspend fun deleteBookSources(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (!canEditBookSource(context)) {
            return returnData.setErrorMsg("权限不足")
        }
        val bookSourceJsonArray = context.bodyAsJsonArray
        val userNameSpace = getUserNameSpace(context)
        var bookSourceList = getUserBookSourceJson(userNameSpace)
        if (bookSourceList == null) {
            bookSourceList = JsonArray()
        }
        for (k in 0 until bookSourceJsonArray.size()) {
            val bookSourceUrl = bookSourceJsonArray.getJsonObject(k).getString("bookSourceUrl")
            if (bookSourceUrl.isNullOrEmpty()) continue
            var existIndex = -1
            for (i in 0 until bookSourceList.size()) {
                val _bookSourceUrl = bookSourceList.getJsonObject(i).getString("bookSourceUrl")
                if (bookSourceUrl.equals(_bookSourceUrl)) {
                    existIndex = i
                    break
                }
            }
            if (existIndex < 0) continue
            bookSourceList.remove(existIndex)
        }
        saveUserStorage(userNameSpace, "bookSource", bookSourceList)
        generateBookSourceMap(userNameSpace, bookSourceList)
        return returnData.setData("")
    }

    suspend fun deleteAllBookSources(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (!canEditBookSource(context)) {
            return returnData.setErrorMsg("权限不足")
        }
        val userNameSpace = getUserNameSpace(context)
        saveUserStorage(userNameSpace, "bookSource", JsonArray())
        generateBookSourceMap(userNameSpace, JsonArray())
        return returnData.setData("")
    }

    suspend fun setAsDefaultBookSources(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (!checkManagerAuth(context)) {
            return returnData.setData("NEED_SECURE_KEY").setErrorMsg("请输入管理密码")
        }
        val username = context.bodyAsJson.getString("username")
        val bookSourceList = asJsonArray(getUserStorage(username, "bookSource"))
            ?: return returnData.setErrorMsg("用户书源不存在")
        saveUserStorage("default", "bookSource", bookSourceList.list)
        generateBookSourceMap("default", bookSourceList)
        return returnData.setData("设置默认书源成功")
    }

    suspend fun readSourceFile(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (context.fileUploads() == null || context.fileUploads().isEmpty()) {
            return returnData.setErrorMsg("请上传文件")
        }
        var sourceList: JsonArray? = null
        sourceList = JsonArray()
        context.fileUploads().forEach {
            val file = File(it.uploadedFileName())
            if (file.exists()) {
                sourceList.add(file.readText())
                file.delete()
            }
        }
        return returnData.setData(sourceList.getList())
    }

    suspend fun saveFromRemoteSource(context: RoutingContext) {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            context.success(returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用"))
            return
        }
        var url = ""
        if (context.request().method() == HttpMethod.POST) {
            url = context.bodyAsJson.getString("url") ?: ""
        } else {
            url = context.queryParam("url").firstOrNull() ?: ""
        }
        if (url.isEmpty()) {
            context.success(returnData.setErrorMsg("请输入远程书源链接"))
            return
        }
        launch(MDCContext() + Dispatchers.IO) {
            webClient.getAbs(url).timeout(3000).send { res ->
                val body = res.result()?.bodyAsJsonArray()
                if (body != null) {
                    context.success(saveBookSources(context, body))
                } else {
                    context.success(returnData.setErrorMsg("远程书源链接错误"))
                }
            }
        }
    }

    suspend fun updateRemoteSourceSub(userNameSpace: String, user: User?) {
        var remoteBookSourceList =
            asJsonArray(getUserStorage(userNameSpace, "remoteBookSourceSub")) ?: return
        for (i in 0 until remoteBookSourceList.size()) {
            var remoteBookSource = remoteBookSourceList.getJsonObject(i)
            var url = remoteBookSource.getString("link")
            if (url.isNullOrEmpty()) continue
            awaitResult<Boolean> { handler ->
                webClient.getAbs(url).timeout(3000).send { res ->
                    val body = res.result()?.bodyAsJsonArray()
                    if (body != null) {
                        try {
                            logger.info(
                                "updateRemoteSourceSub link={}, result={}",
                                url,
                                saveUserBookSources(userNameSpace, user, body).errorMsg
                            )
                            remoteBookSourceList.set(
                                i,
                                remoteBookSource.put("lastSyncTime", System.currentTimeMillis())
                            )
                            saveUserStorage(
                                userNameSpace,
                                "remoteBookSourceSub",
                                remoteBookSourceList
                            )
                        } catch (e: Exception) {
                            logger.error(e) { "更新远程书源失败" }
                        }
                    }
                    handler.handle(Future.succeededFuture(true))
                }
            }
        }
        generateBookSourceMap(userNameSpace)
    }

    suspend fun deleteUserBookSource(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (!checkManagerAuth(context)) {
            return returnData.setData("NEED_SECURE_KEY").setErrorMsg("请输入管理密码")
        }
        val userJsonArray = context.bodyAsJsonArray
        for (i in 0 until userJsonArray.size()) {
            val username = userJsonArray.getString(i)
            val userBookSourceFile =
                File(getWorkDir("storage", "data", username, "bookSource.json"))
            if (!userBookSourceFile.exists()) continue
            userBookSourceFile.deleteRecursively()
        }
        return returnData.setData("删除书源成功")
    }

    suspend fun deleteBookSourcesFile(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        val userNameSpace = getUserNameSpace(context)
        val userBookSourceFile =
            File(getWorkDir("storage", "data", userNameSpace, "bookSource.json"))
        if (userBookSourceFile.exists()) {
            userBookSourceFile.deleteRecursively()
        }
        return returnData.setData("")
    }

    fun generateBookSourceMap(
        userNameSpace: String,
        bookSourceList: JsonArray? = null
    ): Map<String, Int> {
        var bookSourceJsonArray = bookSourceList ?: getUserBookSourceJson(userNameSpace)
        if (bookSourceJsonArray == null) {
            bookSourceJsonArray = JsonArray()
        }
        val urlMap = LinkedHashMap<String, Int>()
        val exploreList = ArrayList<Map<String, String?>>()
        for (i in 0 until bookSourceJsonArray.size()) {
            urlMap[bookSourceJsonArray.getJsonObject(i).getString("bookSourceUrl")] = i
            val exploreUrl = bookSourceJsonArray.getJsonObject(i).getString("exploreUrl")
            if (exploreUrl.isNullOrEmpty()) continue
            exploreList.add(
                mutableMapOf(
                    "bookSourceUrl" to
                        bookSourceJsonArray.getJsonObject(i).getString("bookSourceUrl"),
                    "bookSourceGroup" to
                        bookSourceJsonArray.getJsonObject(i).getString("bookSourceGroup"),
                    "bookSourceName" to
                        bookSourceJsonArray.getJsonObject(i).getString("bookSourceName")
                )
            )
        }
        saveUserStorage(userNameSpace, "bookSourceMap", urlMap)
        saveUserStorage(userNameSpace, "bookSourceExploreList", exploreList)
        return urlMap
    }

    fun getBookSourceMap(userNameSpace: String): Map<String, Int> {
        val content = if (getStorageFile("data", userNameSpace, "bookSource").exists()) {
            getUserStorage(userNameSpace, "bookSourceMap")
        } else {
            getUserStorage("default", "bookSourceMap")
        }
        if (!content.isNullOrEmpty()) {
            return asJsonObject(content)?.map as MutableMap<String, Int>
        }
        if (getStorageFile("data", userNameSpace, "bookSource").exists()) {
            return generateBookSourceMap(userNameSpace)
        }
        return generateBookSourceMap("default")
    }
}
