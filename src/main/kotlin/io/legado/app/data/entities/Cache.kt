// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.data.entities

data class Cache(
    val key: String = "",
    var value: String? = null,
    var deadline: Long = 0L
)
