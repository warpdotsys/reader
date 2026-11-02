// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.data.entities

/**
 * 阅读书签：记录某章某处的字符偏移与选中原文，用于在正文里定位回跳。
 */
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
