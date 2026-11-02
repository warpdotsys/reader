// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.api.controller

import com.htmake.reader.api.ReturnData
import com.htmake.reader.db.DB
import com.htmake.reader.utils.gson
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext

/**
 * Controller 的通用 CRUD 契约。
 *
 * 默认实现承担鉴权、表定位、保存前钩子与 checker 转发的固定流程，
 * 子类只需给出 [tableName]、[entityClass]、[checkUserAuth] 等少数决策点。
 */
interface CURD<T> {

    fun getTableName(): String

    fun getEntityClass(): Class<T>

    fun convertToEntity(var1: JsonObject): T = var1.mapTo(getEntityClass())

    fun convertToEntityList(var1: String): Array<T> {
        val clazz = java.lang.reflect.Array.newInstance(getEntityClass(), 0).javaClass
        return gson.fromJson(var1, clazz) as Array<T>
    }

    fun onList(var1: JsonArray, userNameSpace: String): JsonArray = var1

    fun checker(var1: JsonObject, var2: T): Boolean

    fun onCheckEnd(var1: T, var2: Boolean, var3: JsonArray) {
    }

    fun beforeSave(val1: T, db: DB<T>): ReturnData? = null

    fun beforeAdd(val1: T, db: DB<T>): ReturnData? = null

    fun beforeDelete(val1: T, db: DB<T>): ReturnData? = null

    suspend fun checkUserAuth(context: RoutingContext): Boolean

    fun getUserNS(context: RoutingContext): String

    suspend fun list(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkUserAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        val userNameSpace = getUserNS(context)
        var list = DB.table<T>(userNameSpace, getTableName()).readAll()
        list = onList(list, userNameSpace)
        return returnData.setData(list.list!!)
    }

    suspend fun save(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkUserAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        val entity = convertToEntity(context.bodyAsJson!!)
        val userNameSpace = getUserNS(context)
        val db = DB.table<T>(userNameSpace, getTableName())
        val result = beforeSave(entity, db)
        if (result != null) {
            return result
        }
        db.save(entity, this::onCheckEnd) { jsonObject, value -> checker(jsonObject, value) }
        return returnData.setData("")
    }

    suspend fun saveMulti(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkUserAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        val itemList = convertToEntityList(context.bodyAsString!!)
        if (itemList.isEmpty()) {
            return returnData.setErrorMsg("参数错误")
        }
        val userNameSpace = getUserNS(context)
        val db = DB.table<T>(userNameSpace, getTableName())
        for (item in itemList) {
            val result = beforeSave(item, db)
            if (result != null) {
                return result
            }
        }
        db.saveMulti(itemList, this::onCheckEnd) { jsonObject, value -> checker(jsonObject, value) }
        return returnData.setData("")
    }

    suspend fun delete(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkUserAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        val entity = convertToEntity(context.bodyAsJson!!)
        val userNameSpace = getUserNS(context)
        val db = DB.table<T>(userNameSpace, getTableName())
        val result = beforeDelete(entity, db)
        if (result != null) {
            return result
        }
        db.delete { checker(it, entity) }
        return returnData.setData("")
    }

    suspend fun deleteMulti(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        if (!checkUserAuth(context)) {
            return returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用")
        }
        var itemList = convertToEntityList(context.bodyAsString!!)
        val userNameSpace = getUserNS(context)
        val db = DB.table<T>(userNameSpace, getTableName())
        for (item in itemList) {
            val result = beforeDelete(item, db)
            if (result != null) {
                return result
            }
        }
        db.delete { jsonObject -> itemList.any { checker(jsonObject, it) } }
        return returnData.setData("")
    }
}
