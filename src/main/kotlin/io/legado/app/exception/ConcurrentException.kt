// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.exception

class ConcurrentException(msg: String, val waitTime: Int) : NoStackTraceException(msg)
