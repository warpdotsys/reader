// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.api.controller

import com.htmake.reader.api.ReturnData
import com.htmake.reader.db.DB
import com.htmake.reader.utils.asJsonArray
import com.htmake.reader.utils.asJsonObject
import io.legado.app.data.entities.BookGroup
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import mu.KLogger
import mu.KotlinLogging
import java.util.LinkedHashMap
import kotlin.coroutines.CoroutineContext

private val logger: KLogger = KotlinLogging.logger { }

class BookGroupController(coroutineContext: CoroutineContext) :
    BaseController(coroutineContext), CURD<BookGroup> {

    override fun getTableName(): String {
        return "bookGroup"
    }

    override fun onList(var1: JsonArray, userNameSpace: String): JsonArray {
        if (var1.size() == 0) {
            asJsonArray(
                """
            [{"groupId":-1,"groupName":"全部","order":-10,"show":true},{"groupId":-2,"groupName":"本地","order":-9,"show":true},{"groupId":-3,"groupName":"音频","order":-8,"show":true},{"groupId":-4,"groupName":"未分组","order":-7,"show":true},{"groupId":-5,"groupName":"更新错误","order":-6,"show":true}]
            """
            )?.let {
                saveUserStorage(userNameSpace, "bookGroup", it)
                return it
            }
        }
        return var1
    }

    override fun checker(var1: JsonObject, var2: BookGroup): Boolean {
        return var2.groupId.equals(var1.getLong("groupId"))
    }

    override fun onCheckEnd(var1: BookGroup, var2: Boolean, bookGroupList: JsonArray) {
        if (!var2) {
            var maxOrder = 0
            var idsSum = 0L
            for (it in bookGroupList) {
                val jsonObject = asJsonObject(it)
                val id = jsonObject?.getLong("groupId", 0L) ?: 0L
                val order = jsonObject?.getInteger("order", 0) ?: 0
                maxOrder = if (order > maxOrder) order else maxOrder
                idsSum += if (id > 0L) id else 0L
            }
            var id = 1L
            while (idsSum and id != 0L) {
                id = id shl 1
            }
            var1.groupId = id
            var1.order = maxOrder + 1
        }
    }

    override fun beforeSave(var1: BookGroup, db: DB<BookGroup>): ReturnData? {
        val returnData = ReturnData()
        if (var1.groupName.isEmpty()) {
            return returnData.setErrorMsg("分组名称不能为空")
        }
        return null
    }

    override suspend fun checkUserAuth(context: RoutingContext): Boolean {
        return checkAuth(context)
    }

    override fun getUserNS(context: RoutingContext): String {
        return getUserNameSpace(context)
    }

    override fun getEntityClass(): Class<BookGroup> {
        return BookGroup::class.java
    }

    suspend fun saveBookGroupOrder(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        val bookGroupOrder = context.bodyAsJson.getJsonArray("order", null)
            ?: return returnData.setErrorMsg("参数错误")
        val userNameSpace = getUserNameSpace(context)
        var bookGroupList = asJsonArray(getUserStorage(userNameSpace, "bookGroup"))
            ?: JsonArray()
        val orderMap = LinkedHashMap<Any?, Any?>()
        var index = 0
        while (index < bookGroupOrder.size()) {
            val i = index++
            val bookGroupOrder1 = bookGroupOrder.getJsonObject(i)
            orderMap[bookGroupOrder1.getLong("groupId")] = bookGroupOrder1.getInteger("order")
        }
        val groupList = bookGroupList.list
        var i = 0
        while (i < bookGroupList.size()) {
            val current = i++
            val bookGroup = bookGroupList.getJsonObject(current).mapTo(BookGroup::class.java)
            if (!orderMap.containsKey(bookGroup.groupId)) continue
            val order = orderMap[bookGroup.groupId]
            if (order is Int) {
                bookGroup.order = order
            }
            groupList[current] = JsonObject.mapFrom(bookGroup)
        }
        bookGroupList = JsonArray(groupList)
        saveUserStorage(userNameSpace, "bookGroup", bookGroupList)
        return returnData.setData("")
    }
}
