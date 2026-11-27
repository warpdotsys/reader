// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.data.entities

/**
 * 本地 txt 的目录规则。serialNumber 越小越先尝试，rule 是匹配章节标题的正则。
 */
data class TxtTocRule(
    var id: Long = System.currentTimeMillis(),
    var name: String = "",
    var rule: String = "",
    var serialNumber: Int = -1,
    var enable: Boolean = true
)
