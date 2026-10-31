// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.data.entities

data class SearchKeyword(
    var word: String = "",
    var usage: Int = 1,
    var lastUseTime: Long = System.currentTimeMillis()
)
