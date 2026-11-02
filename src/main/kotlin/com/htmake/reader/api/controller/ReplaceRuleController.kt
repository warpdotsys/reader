// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.api.controller

import com.htmake.reader.api.ReturnData
import com.htmake.reader.db.DB
import io.legado.app.data.entities.ReplaceRule
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import mu.KLogger
import mu.KotlinLogging
import kotlin.coroutines.CoroutineContext

private val logger: KLogger = KotlinLogging.logger { }

class ReplaceRuleController(coroutineContext: CoroutineContext) :
    BaseController(coroutineContext), CURD<ReplaceRule> {

    override fun getTableName(): String {
        return "replaceRule"
    }

    override fun checker(var1: JsonObject, var2: ReplaceRule): Boolean {
        return var2.name.equals(var1.getString("name"))
    }

    override fun beforeSave(var1: ReplaceRule, db: DB<ReplaceRule>): ReturnData? {
        val returnData = ReturnData()
        if (var1.name.length == 0) {
            return returnData.setErrorMsg("名称不能为空")
        }
        if (var1.pattern.length == 0) {
            return returnData.setErrorMsg("规则不能为空")
        }
        return null
    }

    override suspend fun checkUserAuth(context: RoutingContext): Boolean {
        return checkAuth(context)
    }

    override fun getUserNS(context: RoutingContext): String {
        return getUserNameSpace(context)
    }

    override fun getEntityClass(): Class<ReplaceRule> {
        return ReplaceRule::class.java
    }
}
