// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.data.entities

/**
 * 书架分组。groupId 是分组标识，order 决定排序，show 控制是否在书架上显示。
 */
data class BookGroup(
    var groupId: Long = 0L,
    var groupName: String = "",
    var cover: String? = null,
    var order: Int = 0,
    var show: Boolean = true
)
