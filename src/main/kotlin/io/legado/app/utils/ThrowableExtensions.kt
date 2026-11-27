// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.utils

val Throwable.msg: String
    get() {
        val stackTrace = stackTraceToString()
        val lMsg = localizedMessage ?: "noErrorMsg"
        return if (stackTrace.isNotEmpty()) stackTrace else lMsg
    }
