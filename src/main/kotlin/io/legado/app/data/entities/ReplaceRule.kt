// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.data.entities

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 替换净化规则：按正则或纯文本把正文/标题里的命中替换掉，可限定生效范围与超时。
 */
data class ReplaceRule(
    var id: Long = System.currentTimeMillis(),
    var name: String = "",
    var group: String? = null,
    var pattern: String = "",
    var replacement: String = "",
    var scope: String? = null,
    var scopeTitle: Boolean = false,
    var scopeContent: Boolean = true,
    @get:JsonProperty("isEnabled")
    var isEnabled: Boolean = true,
    @get:JsonProperty("isRegex")
    var isRegex: Boolean = false,
    var timeoutMillisecond: Long = 3000L,
    var order: Int = 0
)
