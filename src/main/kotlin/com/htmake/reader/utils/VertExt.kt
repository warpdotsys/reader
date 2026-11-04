// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.utils

import com.htmake.reader.entity.BasicError
import io.vertx.core.Handler
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Route
import io.vertx.ext.web.RoutingContext
import org.slf4j.MDC
import java.net.URLDecoder

fun RoutingContext.success(any: Any?) {
    val toJson = if (any is JsonObject) {
        any.toString()
    } else {
        gson.toJson(any)!!
    }
    response().putHeader("content-type", "application/json; charset=utf-8").end(toJson)
}

fun RoutingContext.error(throwable: Throwable) {
    val path = URLDecoder.decode(request().absoluteURI(), "UTF-8")
    val basicError = BasicError(
        "Internal Server Error",
        throwable.toString(),
        throwable.message.toString(),
        path,
        500,
        System.currentTimeMillis()
    )
    val errorJson = gson.toJson(basicError)
    logger.error("Internal Server Error", throwable)
    logger.error { errorJson }
    response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(500).end(errorJson)
}

fun Route.globalHandler(handler: Handler<RoutingContext>) {
    this.handler { ctx ->
        var id = ctx.get<String>("traceId")
        if (id.isNullOrEmpty()) {
            id = getTraceId()
        }
        MDC.put("traceId", id)
        ctx.put("traceId", id)
        handler.handle(ctx)
    }
}
