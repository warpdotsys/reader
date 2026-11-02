// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.api.controller

import com.google.gson.reflect.TypeToken
import com.htmake.reader.config.AppConfig
import com.htmake.reader.entity.User
import com.htmake.reader.utils.SpringContextUtils
import com.htmake.reader.utils.asJsonObject
import com.htmake.reader.utils.genEncryptedPassword
import com.htmake.reader.utils.getStorage
import com.htmake.reader.utils.getWorkDir
import com.htmake.reader.utils.saveStorage
import com.htmake.reader.utils.toDataClass
import com.htmake.reader.utils.toMap
import io.legado.app.utils.FileUtils
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import mu.KLogger
import mu.KotlinLogging
import org.springframework.core.env.Environment
import java.io.File
import kotlin.coroutines.CoroutineContext

private val logger: KLogger = KotlinLogging.logger { }

open class BaseController(override val coroutineContext: CoroutineContext) : CoroutineScope {

    private var loginExpireDays: Int = 7

    val appConfig: AppConfig = SpringContextUtils.getBean("appConfig", AppConfig::class.java)

    val env: Environment = SpringContextUtils.getBean(Environment::class.java)

    val userMutex: Mutex = Mutex()

    suspend fun saveUserSession(
        context: RoutingContext,
        user: User,
        regenerateToken: Boolean = true
    ): Map<String, Any?> {
        userMutex.lock()
        try {
            user.last_login_at = System.currentTimeMillis()
            if (regenerateToken) {
                user.token = genEncryptedPassword(user.username, System.currentTimeMillis().toString())
                var tokenMap: MutableMap<String, Long>? = null
                val expire = System.currentTimeMillis() + loginExpireDays * 86400 * 1000L
                if (user.token_map != null) {
                    tokenMap = user.token_map as? MutableMap<String, Long>
                }
                if (tokenMap == null) {
                    tokenMap = mutableMapOf(user.token to expire)
                } else {
                    tokenMap[user.token] = expire
                }
                tokenMap.values.removeAll { it < user.last_login_at }
                user.token_map = tokenMap
            }
            var userMap: MutableMap<String, Map<String, Any>> = LinkedHashMap()
            val userMapJson = asJsonObject(getStorage("data", "users"))
            if (userMapJson != null) {
                userMap = userMapJson.map as MutableMap<String, Map<String, Any>>
            }
            userMap[user.username] = user.toMap()
            saveStorage("data", "users", value = Json.encode(userMap))
            val loginData = formatUser(user)
            context.session().put("username", user.username)
            context.put("username", user.username)
            return loginData
        } finally {
            userMutex.unlock()
        }
    }

    suspend fun checkAuth(context: RoutingContext): Boolean {
        if (!appConfig.secure) {
            return true
        }
        val username = context.session().get<String>("username") ?: ""
        val userInfo = getUserInfoClass(username)
        if (userInfo != null) {
            context.put("username", userInfo.username)
            context.put("userInfo", userInfo)
            return true
        }
        val accessToken = context.queryParam("accessToken").firstOrNull() ?: ""
        if (accessToken.isNotEmpty()) {
            var userMap: MutableMap<String, Map<String, Any>> = LinkedHashMap()
            val userMapJson = asJsonObject(getStorage("data", "users"))
            if (userMapJson != null) {
                userMap = userMapJson.map as MutableMap<String, Map<String, Any>>
            }
            val split = accessToken.split(":")
            if (split.size >= 2) {
                val _username = split[0]
                val token = split[1]
                val existedUser = userMap[_username]?.toDataClass<User>()
                if (existedUser != null) {
                    var isLogin = 0
                    if (token.isNotEmpty()) {
                        if (existedUser.token.isNotEmpty() && existedUser.token == token) {
                            isLogin = 1
                        }
                        if (isLogin == 0 && existedUser.token_map != null) {
                            val tokenMap = existedUser.token_map as? MutableMap<String, Long>
                            if (tokenMap != null && tokenMap.containsKey(token)) {
                                if (tokenMap.getOrDefault(token, 0L) > System.currentTimeMillis()) {
                                    isLogin = 1
                                    tokenMap[token] =
                                        System.currentTimeMillis() + loginExpireDays * 86400 * 1000L
                                } else {
                                    tokenMap.remove(token)
                                }
                                existedUser.token_map = tokenMap
                            }
                        }
                    }
                    if (isLogin != 0) {
                        saveUserSession(context, existedUser, false)
                        context.put("username", existedUser.username)
                        context.put("userInfo", existedUser)
                    }
                    return isLogin != 0
                }
            }
        }
        return false
    }

    fun checkManagerAuth(context: RoutingContext): Boolean {
        if (!appConfig.secure) {
            return true
        }
        if (appConfig.secureKey.isEmpty()) {
            return true
        }
        val secureKey = context.queryParam("secureKey").firstOrNull() ?: ""
        if (secureKey == appConfig.secureKey) {
            val userNS = context.queryParam("userNS").firstOrNull()
            if (!userNS.isNullOrEmpty()) {
                context.put("userNameSpace", userNS)
            } else {
                context.remove("userNameSpace")
            }
            return true
        }
        return false
    }

    fun getUserNameSpace(context: RoutingContext): String {
        if (!appConfig.secure) {
            return "default"
        }
        checkManagerAuth(context)
        val userNS: String? = context.get<String>("userNameSpace")
        if (!userNS.isNullOrEmpty()) {
            return userNS
        }
        val username: String? = context.get<String>("username")
        if (username != null) {
            return username
        }
        return "default"
    }

    fun getUserStorage(context: Any, vararg path: String): String? {
        var userNameSpace = ""
        when (context) {
            is RoutingContext -> userNameSpace = getUserNameSpace(context)
            is String -> userNameSpace = context
        }
        if (userNameSpace.isEmpty()) {
            return getStorage("data", *path)
        }
        return getStorage("data", userNameSpace, *path)
    }

    fun saveUserStorage(context: Any, path: String, value: Any) {
        var userNameSpace = ""
        when (context) {
            is RoutingContext -> userNameSpace = getUserNameSpace(context)
            is String -> userNameSpace = context
        }
        if (userNameSpace.isEmpty()) {
            saveStorage("data", path, value = value)
            return
        }
        saveStorage("data", userNameSpace, path, value = value)
    }

    fun getUserInfoClass(username: String): User? {
        return getUserInfoMap(username)?.toDataClass<User>()
    }

    fun getUserInfoMap(username: String): Map<String, Any>? {
        if (username.isEmpty()) {
            return null
        }
        var userMap: MutableMap<String, Map<String, Any>> = LinkedHashMap()
        val userMapJson = asJsonObject(getStorage("data", "users"))
        if (userMapJson != null) {
            userMap = userMapJson.map as MutableMap<String, Map<String, Any>>
        }
        return userMap[username]
    }

    fun formatUser(userInfo: Any): Map<String, Any> {
        var user: User? = null
        if (userInfo !is User) {
            val userMap = userInfo as? Map<String, Any>
            if (userMap != null) {
                user = userMap.toDataClass<User>()
            }
        } else {
            user = userInfo
        }
        if (user == null) {
            return LinkedHashMap()
        }
        return mutableMapOf(
            "username" to user.username,
            "lastLoginAt" to user.last_login_at,
            "accessToken" to "${user.username}:${user.token}",
            "enableWebdav" to user.enable_webdav,
            "enableLocalStore" to user.enable_local_store,
            "enableBookSource" to user.enable_book_source,
            "enableRssSource" to user.enable_rss_source,
            "bookSourceLimit" to user.book_source_limit,
            "bookLimit" to user.book_limit,
            "createdAt" to user.created_at
        )
    }

    fun getUserWebdavHome(context: Any): String {
        var prefix = getWorkDir("storage", "data")
        var userNameSpace = ""
        when (context) {
            is RoutingContext -> userNameSpace = getUserNameSpace(context)
            is String -> userNameSpace = context
        }
        if (userNameSpace.isNotEmpty()) {
            prefix = prefix + File.separator + userNameSpace
        }
        prefix += File.separator + "webdav"
        val file = File(prefix)
        if (!file.exists()) {
            file.mkdirs()
        }
        return prefix
    }

    fun getFileExt(url: String, defaultExt: String = ""): String {
        return FileUtils.getFileExtetion(url, defaultExt)
    }

    suspend fun limitConcurrent(
        concurrentCount: Int,
        startIndex: Int,
        endIndex: Int,
        handler: suspend CoroutineScope.(Int) -> Any?
    ): Unit {
        limitConcurrent(concurrentCount, startIndex, endIndex, handler) { _, _ -> true }
    }

    suspend fun limitConcurrent(
        concurrentCount: Int,
        startIndex: Int,
        endIndex: Int,
        handler: suspend CoroutineScope.(Int) -> Any?,
        needContinue: (ArrayList<Any?>, Int) -> Boolean
    ) {
        var lastIndex = startIndex
        var loopCount = 0
        var resultCount = 0
        val loopStart = System.currentTimeMillis()
        var coroutineCount = 0
        val deferredList = arrayListOf<Deferred<Any?>>()
        while (true) {
            coroutineCount = deferredList.size
            if (coroutineCount < concurrentCount && lastIndex < endIndex) {
                do {
                    val i = lastIndex++
                    deferredList.add(async { handler(i) })
                } while (++coroutineCount < concurrentCount && lastIndex < endIndex)
            }
            val resultList = arrayListOf<Any?>()
            while (resultList.size <= 0) {
                delay(10)
                val stillDeferredList = arrayListOf<Deferred<Any?>>()
                for (i in deferredList.indices) {
                    try {
                        val deferred = deferredList[i]
                        if (deferred.isCompleted) {
                            resultCount++
                            resultList.add(deferred.getCompleted())
                            continue
                        }
                        if (!deferred.isCancelled) {
                            stillDeferredList.add(deferred)
                            continue
                        }
                        resultCount++
                    } catch (e: Exception) {
                    }
                }
                deferredList.clear()
                deferredList.addAll(stillDeferredList)
            }
            if (resultCount / concurrentCount > loopCount) {
                loopCount = resultCount / concurrentCount
                val costTime = System.currentTimeMillis() - loopStart
                logger.info(
                    "Loop: {} concurrentCount: {} lastIndex: {} endIndex: {} costTime: {} ms deferredList size: {}",
                    loopCount, coroutineCount, lastIndex, endIndex, costTime, deferredList.size
                )
            }
            if (lastIndex < endIndex - 1) {
                continue
            }
            for (i in deferredList.indices) {
                try {
                    resultList.add(deferredList[i].await())
                } catch (e: Exception) {
                }
            }
            deferredList.clear()
            needContinue(resultList, loopCount)
            break
        }
    }
}
