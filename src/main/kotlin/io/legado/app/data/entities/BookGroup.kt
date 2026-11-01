// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.data.entities

data class BookGroup(
    var groupId: Long = 0L,
    var groupName: String = "",
    var cover: String? = null,
    var order: Int = 0,
    var show: Boolean = true
)
