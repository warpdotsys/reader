// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.data.entities

data class ReplaceRule(
    var id: Long = System.currentTimeMillis(),
    var name: String = "",
    var group: String? = null,
    var pattern: String = "",
    var replacement: String = "",
    var scope: String? = null,
    var scopeTitle: Boolean = false,
    var scopeContent: Boolean = true,
    var isEnabled: Boolean = true,
    var isRegex: Boolean = false,
    var timeoutMillisecond: Long = 3000L,
    var order: Int = 0
)
