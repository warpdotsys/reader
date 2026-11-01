// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.data.entities

data class TxtTocRule(
    var id: Long = System.currentTimeMillis(),
    var name: String = "",
    var rule: String = "",
    var serialNumber: Int = -1,
    var enable: Boolean = true
)
