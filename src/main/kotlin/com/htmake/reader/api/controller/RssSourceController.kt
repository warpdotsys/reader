// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.api.controller

import com.htmake.reader.api.ReturnData
import com.htmake.reader.entity.User
import com.htmake.reader.utils.asJsonArray
import io.legado.app.data.entities.RssArticle
import io.legado.app.data.entities.RssSource
import io.legado.app.model.Debug
import io.legado.app.model.rss.Rss
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import mu.KLogger
import mu.KotlinLogging
import kotlin.coroutines.CoroutineContext

private val logger: KLogger = KotlinLogging.logger { }

class RssSourceController(coroutineContext: CoroutineContext) : BaseController(coroutineContext) {

    suspend fun getRssSources(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        val userNameSpace = getUserNameSpace(context)
        val list = asJsonArray(getUserStorage(userNameSpace, "rssSources"))
        if (list != null) {
            return returnData.setData(list.getList())
        }
        return returnData.setData(ArrayList<Any>())
    }

    suspend fun canEditRssSource(context: RoutingContext): Boolean {
        if (!appConfig.secure) {
            return true
        }
        val userInfo = context.get<User>("userInfo")
        if (userInfo == null) {
            return false
        }
        return userInfo.enable_book_source
    }

    suspend fun saveRssSource(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (!canEditRssSource(context)) {
            return returnData.setErrorMsg("权限不足")
        }
        val rssSource = RssSource.fromJson(context.bodyAsString).getOrNull()
        if (rssSource == null) {
            return returnData.setErrorMsg("参数错误")
        }
        if (rssSource.sourceUrl.isEmpty()) {
            return returnData.setErrorMsg("RSS链接不能为空")
        }
        if (rssSource.sourceName.isEmpty()) {
            return returnData.setErrorMsg("RSS名称不能为空")
        }
        val userNameSpace = getUserNameSpace(context)
        var rssSourceList = asJsonArray(getUserStorage(userNameSpace, "rssSources"))
        if (rssSourceList == null) {
            rssSourceList = JsonArray()
        }
        var existIndex = -1
        for (i in 0 until rssSourceList.size()) {
            val _rssSource =
                RssSource.fromJson(rssSourceList.getJsonObject(i).toString()).getOrNull()
            if (_rssSource == null || _rssSource.sourceUrl != rssSource.sourceUrl) continue
            existIndex = i
            break
        }
        if (existIndex >= 0) {
            val list = rssSourceList.getList()
            list[existIndex] = JsonObject.mapFrom(rssSource)
            rssSourceList = JsonArray(list)
        } else {
            rssSourceList.add(JsonObject.mapFrom(rssSource))
        }
        saveUserStorage(userNameSpace, "rssSources", rssSourceList)
        return returnData.setData("")
    }

    suspend fun saveRssSources(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (!canEditRssSource(context)) {
            return returnData.setErrorMsg("权限不足")
        }
        val rssSourceJsonArray = context.bodyAsJsonArray
        if (rssSourceJsonArray == null) {
            return returnData.setErrorMsg("参数错误")
        }
        val userNameSpace = getUserNameSpace(context)
        var rssSourceList: JsonArray =
            asJsonArray(getUserStorage(userNameSpace, "rssSources")) ?: JsonArray()
        for (k in 0 until rssSourceJsonArray.size()) {
            val rssSource =
                RssSource.fromJson(rssSourceJsonArray.getJsonObject(k).toString()).getOrNull()
            if (rssSource == null) continue
            if (rssSource.sourceUrl.isEmpty()) continue
            if (rssSource.sourceName.isEmpty()) continue
            var existIndex = -1
            for (i in 0 until rssSourceList.size()) {
                val _rssSource =
                    RssSource.fromJson(rssSourceList.getJsonObject(i).toString()).getOrNull()
                if (_rssSource == null || _rssSource.sourceUrl != rssSource.sourceUrl) continue
                existIndex = i
                break
            }
            if (existIndex >= 0) {
                val list = rssSourceList.getList()
                list[existIndex] = JsonObject.mapFrom(rssSource)
                rssSourceList = JsonArray(list)
                continue
            }
            rssSourceList.add(JsonObject.mapFrom(rssSource))
        }
        saveUserStorage(userNameSpace, "rssSources", rssSourceList)
        return returnData.setData("")
    }

    suspend fun deleteRssSource(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        if (!canEditRssSource(context)) {
            return returnData.setErrorMsg("权限不足")
        }
        val rssSource = RssSource.fromJson(context.bodyAsString).getOrNull()
        if (rssSource == null) {
            return returnData.setErrorMsg("参数错误")
        }
        val userNameSpace = getUserNameSpace(context)
        val rssSourceList = asJsonArray(getUserStorage(userNameSpace, "rssSources"))
            ?: JsonArray()
        var existIndex = -1
        for (i in 0 until rssSourceList.size()) {
            val _rssSource =
                RssSource.fromJson(rssSourceList.getJsonObject(i).toString()).getOrNull()
            if (_rssSource == null || _rssSource.sourceUrl != rssSource.sourceUrl) continue
            existIndex = i
            break
        }
        if (existIndex >= 0) {
            rssSourceList.remove(existIndex)
        }
        saveUserStorage(userNameSpace, "rssSources", rssSourceList)
        return returnData.setData("")
    }

    fun getRssSourceByURL(url: String, userNameSpace: String): RssSource? {
        if (url.isEmpty()) {
            return null
        }
        val list = asJsonArray(getUserStorage(userNameSpace, "rssSources")) ?: return null
        for (i in 0 until list.size()) {
            val _rssSource = RssSource.fromJson(list.getJsonObject(i).toString()).getOrNull()
            if (_rssSource == null || _rssSource.sourceUrl != url) continue
            return _rssSource
        }
        return null
    }

    suspend fun getRssArticles(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        var sourceUrl = ""
        var sortName = ""
        var sortUrl = ""
        var page = 0
        if (context.request().method() == HttpMethod.POST) {
            sourceUrl = context.bodyAsJson.getString("sourceUrl")!!
            sortName = context.bodyAsJson.getString("sortName", "")!!
            sortUrl = context.bodyAsJson.getString("sortUrl", "")!!
            page = context.bodyAsJson.getInteger("page", 1)!!
        } else {
            sourceUrl = context.queryParam("sourceUrl").firstOrNull() ?: ""
            sortName = context.queryParam("sortName").firstOrNull() ?: ""
            sortUrl = context.queryParam("sortUrl").firstOrNull() ?: ""
            page = context.queryParam("page").firstOrNull()?.toInt() ?: 1
        }
        if (sourceUrl.isEmpty()) {
            return returnData.setErrorMsg("RSS源链接不能为空")
        }
        if (sortUrl.isEmpty()) {
            sortUrl = sourceUrl
        }
        val rssSource = getRssSourceByURL(sourceUrl, getUserNameSpace(context))
            ?: return returnData.setErrorMsg("RSS源不存在")
        val rssArtcles = Rss.getArticles(sortName, sortUrl, rssSource, page, Debug)
        return returnData.setData(rssArtcles)
    }

    suspend fun getRssContent(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        var sourceUrl = ""
        var link = ""
        var origin = ""
        if (context.request().method() == HttpMethod.POST) {
            sourceUrl = context.bodyAsJson.getString("sourceUrl")!!
            link = context.bodyAsJson.getString("link")!!
            origin = context.bodyAsJson.getString("origin")!!
        } else {
            sourceUrl = context.queryParam("sourceUrl").firstOrNull() ?: ""
            link = context.queryParam("link").firstOrNull() ?: ""
            origin = context.queryParam("origin").firstOrNull() ?: ""
        }
        if (sourceUrl.isEmpty()) {
            return returnData.setErrorMsg("RSS链接不能为空")
        }
        if (link.isEmpty()) {
            return returnData.setErrorMsg("RSS文章链接不能为空")
        }
        if (origin.isEmpty()) {
            return returnData.setErrorMsg("RSS文章来源不能为空")
        }
        val userNameSpace = getUserNameSpace(context)
        val rssSource = getRssSourceByURL(sourceUrl, userNameSpace)
            ?: return returnData.setErrorMsg("RSS源不存在")
        val rssArticle = RssArticle(origin = origin, link = link)
        var content = ""
        if (rssSource.ruleContent != null) {
            content = Rss.getContent(rssArticle, rssSource.ruleContent!!, rssSource, Debug)
        }
        return returnData.setData(content)
    }
}
