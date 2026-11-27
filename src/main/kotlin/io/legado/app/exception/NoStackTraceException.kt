// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.exception

open class NoStackTraceException(msg: String) : Exception(msg) {
    override fun fillInStackTrace(): Throwable {
        return this
    }
}
