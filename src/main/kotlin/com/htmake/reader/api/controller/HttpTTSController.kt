// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.api.controller

import com.htmake.reader.api.ReturnData
import com.htmake.reader.db.DB
import com.htmake.reader.utils.asJsonArray
import io.legado.app.data.entities.HttpTTS
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import kotlin.coroutines.CoroutineContext

class HttpTTSController(coroutineContext: CoroutineContext) :
    BaseController(coroutineContext), CURD<HttpTTS> {

    override fun getTableName(): String {
        return "httpTTS"
    }

    override fun checker(var1: JsonObject, var2: HttpTTS): Boolean {
        return var2.name.equals(var1.getString("name"))
    }

    override fun beforeSave(var1: HttpTTS, db: DB<HttpTTS>): ReturnData? {
        val returnData = ReturnData()
        if (var1.name.length == 0) {
            return returnData.setErrorMsg("名称不能为空")
        }
        if (var1.url.length == 0) {
            return returnData.setErrorMsg("链接不能为空")
        }
        return null
    }

    override suspend fun checkUserAuth(context: RoutingContext): Boolean {
        return checkAuth(context)
    }

    override fun getUserNS(context: RoutingContext): String {
        return getUserNameSpace(context)
    }

    override fun getEntityClass(): Class<HttpTTS> {
        return HttpTTS::class.java
    }

    override fun convertToEntity(var1: JsonObject): HttpTTS {
        return HttpTTS.fromJson(var1.toString()).getOrThrow()
    }

    override fun convertToEntityList(var1: String): Array<HttpTTS> {
        val list = ArrayList<HttpTTS>()
        asJsonArray(var1)?.forEach {
            list.add(HttpTTS.fromJson(it.toString()).getOrThrow())
        }
        return list.toTypedArray()
    }
}
