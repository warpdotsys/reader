// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.model

import mu.KLogger
import mu.KotlinLogging
import okhttp3.logging.HttpLoggingInterceptor

private val logger: KLogger = KotlinLogging.logger { }

interface DebugLog : HttpLoggingInterceptor.Logger {
    fun log(
        sourceUrl: String? = "",
        msg: String? = "",
        isHtml: Boolean = false
    ) {
        logger.info("sourceUrl: {}, msg: {}", sourceUrl, msg)
    }

    override fun log(message: String) {
        logger.debug(message)
    }
}
