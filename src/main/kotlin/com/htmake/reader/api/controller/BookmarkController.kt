// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.api.controller

import com.htmake.reader.api.ReturnData
import com.htmake.reader.db.DB
import io.legado.app.data.entities.Bookmark
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import mu.KLogger
import mu.KotlinLogging
import kotlin.coroutines.CoroutineContext

private val logger: KLogger = KotlinLogging.logger { }

class BookmarkController(coroutineContext: CoroutineContext) :
    BaseController(coroutineContext), CURD<Bookmark> {

    override fun getTableName(): String {
        return "bookmark"
    }

    override fun checker(var1: JsonObject, var2: Bookmark): Boolean {
        return var2.time == var1.getLong("time")
    }

    override fun beforeSave(var1: Bookmark, db: DB<Bookmark>): ReturnData? {
        val returnData = ReturnData()
        if (var1.bookName.length == 0) {
            if (var1.bookAuthor.length == 0) {
                return returnData.setErrorMsg("书签信息错误")
            }
        }
        return null
    }

    override suspend fun checkUserAuth(context: RoutingContext): Boolean {
        return checkAuth(context)
    }

    override fun getUserNS(context: RoutingContext): String {
        return getUserNameSpace(context)
    }

    override fun getEntityClass(): Class<Bookmark> {
        return Bookmark::class.java
    }
}
