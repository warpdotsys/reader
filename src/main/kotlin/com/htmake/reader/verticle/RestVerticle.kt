// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.verticle

import com.htmake.reader.utils.error
import com.htmake.reader.utils.globalHandler
import com.htmake.reader.utils.success
import com.htmake.reader.utils.toDir
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.LoggerFormat
import io.vertx.ext.web.handler.LoggerHandler
import io.vertx.ext.web.handler.SessionHandler
import io.vertx.ext.web.sstore.LocalSessionStore
import io.vertx.kotlin.coroutines.CoroutineVerticle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.slf4j.MDCContext
import mu.KLogger
import mu.KotlinLogging
import java.net.URLDecoder

private val logger: KLogger = KotlinLogging.logger { }

abstract class RestVerticle : CoroutineVerticle() {

    protected lateinit var router: Router

    open var port: Int = 8080

    override suspend fun start() {
        super.start()
        router = Router.router(vertx)
        val cookieName = "reader.session"
        router.route().globalHandler(
            SessionHandler.create(LocalSessionStore.create(vertx))
                .setSessionCookieName(cookieName)
                .setSessionTimeout(7L * 86400 * 1000)
                .setSessionCookiePath("/")
        )
        router.route().globalHandler { it: RoutingContext ->
            it.addHeadersEndHandler { _ ->
                val cookie = it.getCookie(cookieName)
                if (cookie != null) {
                    cookie.setMaxAge(172800000L)
                    cookie.setPath("/")
                }
            }
            it.next()
        }
        router.route().globalHandler { it: RoutingContext ->
            it.addHeadersEndHandler { _ ->
                val origin = it.request().getHeader("Origin")
                if (origin != null && origin.isNotEmpty()) {
                    val res = it.response()
                    res.putHeader("Access-Control-Allow-Origin", origin)
                    res.putHeader("Access-Control-Allow-Credentials", "true")
                    res.putHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE")
                    res.putHeader(
                        "Access-Control-Allow-Headers",
                        "Authorization, Content-Type, If-Match, If-Modified-Since, " +
                            "If-None-Match, If-Unmodified-Since, X-Requested-With"
                    )
                }
            }
            val origin = it.request().getHeader("Origin")
            if (origin != null && origin.isNotEmpty() &&
                it.request().method() == HttpMethod.OPTIONS
            ) {
                it.removeCookie(cookieName)
                it.success("")
                return@globalHandler
            }
            it.next()
        }
        router.route().globalHandler(BodyHandler.create())
        router.route().globalHandler(LoggerHandler.create(LoggerFormat.DEFAULT))
        router.route("/reader3/*").globalHandler { it: RoutingContext ->
            val rawMethod = it.request().rawMethod()
            logger.info(
                "{} {}",
                rawMethod,
                URLDecoder.decode(it.request().absoluteURI(), "UTF-8")
            )
            if (rawMethod != "PUT" && (it.fileUploads() == null || it.fileUploads().isEmpty()) &&
                it.bodyAsString != null && it.bodyAsString.length > 0 &&
                it.bodyAsString.length < 1000
            ) {
                logger.info("Request body: {}", it.bodyAsString)
            }
            it.next()
        }
        router.get("/health").globalHandler { it: RoutingContext ->
            it.success("ok!")
        }
        initRouter(router)
        router.route().last().failureHandler { ctx ->
            ctx.error(ctx.failure()!!)
        }
        val contextPath = contextPath
        val mainRouter = if (contextPath.length > 0) {
            val r = Router.router(vertx)
            r.mountSubRouter(contextPath.toDir(true), router)
            r
        } else {
            router
        }
        logger.info("port: {}", port)
        vertx.createHttpServer()
            .requestHandler(mainRouter)
            .exceptionHandler { error -> onException(error) }
            .listen(port) { res ->
                if (res.succeeded()) {
                    logger.info("Server running at: http://localhost:{}", port)
                    logger.info("Web reader running at: http://localhost:{}", port)
                    println("ReaderApplication Started")
                    started()
                } else {
                    onStartError()
                }
            }
    }

    abstract suspend fun initRouter(router: Router)

    abstract val contextPath: String

    open fun onException(error: Throwable) {
        logger.error("vertx exception: {}", error)
    }

    open fun onStartError() {
    }

    open fun started() {
    }

    open fun onHandlerError(ctx: RoutingContext, error: Exception) {
        logger.error("Error: {}", error)
        ctx.error(error)
    }

    fun Route.coroutineHandler(fn: suspend (RoutingContext) -> Any?) {
        globalHandler { ctx ->
            var job: Job? = null
            ctx.request().connection().closeHandler {
                logger.info("客户端已断开链接，终止运行")
                job?.cancel()
            }
            job = launch(MDCContext() + Dispatchers.IO) {
                try {
                    ctx.success(fn(ctx))
                } catch (e: Exception) {
                    onHandlerError(ctx, e)
                }
            }
        }
    }

    fun Route.coroutineHandlerWithoutRes(fn: suspend (RoutingContext) -> Any?) {
        globalHandler { ctx ->
            var job: Job? = null
            ctx.request().connection().closeHandler {
                logger.info("客户端已断开链接，终止运行")
                job?.cancel()
            }
            job = launch(MDCContext() + Dispatchers.IO) {
                try {
                    fn(ctx)
                } catch (e: Exception) {
                    onHandlerError(ctx, e)
                }
            }
        }
    }
}
