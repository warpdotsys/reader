// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.help

import io.legado.app.data.entities.TxtTocRule
import io.legado.app.utils.GSON
import io.legado.app.utils.fromJsonArray

object DefaultData {

    const val txtTocRuleFileName = "txtTocRule.json"

    val txtTocRules: List<TxtTocRule> by lazy {
        val bytes = DefaultData::class.java.getResource("/defaultData/$txtTocRuleFileName")!!.readBytes()
        GSON.fromJsonArray<TxtTocRule>(String(bytes, Charsets.UTF_8)).getOrThrow()!!
    }
}
