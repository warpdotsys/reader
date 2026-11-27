// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.help

import io.legado.app.data.entities.TxtTocRule
import io.legado.app.utils.GSON
import io.legado.app.utils.fromJsonArray

object DefaultData {

    const val txtTocRuleFileName = "txtTocRule.json"

    val txtTocRules: List<TxtTocRule> by lazy {
        val json = String(
            DefaultData::class.java.getResource("/defaultData/${txtTocRuleFileName}").readBytes(),
            Charsets.UTF_8
        )
        GSON.fromJsonArray<TxtTocRule>(json).getOrThrow()!!
    }
}
