// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.data.entities

data class Bookmark(
    val time: Long = System.currentTimeMillis(),
    val bookName: String = "",
    val bookAuthor: String = "",
    var chapterIndex: Int = 0,
    var chapterPos: Int = 0,
    var chapterName: String = "",
    var bookText: String = "",
    var content: String = ""
)
