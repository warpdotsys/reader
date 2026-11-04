// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.data.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.legado.app.model.analyzeRule.RuleDataInterface
import io.legado.app.utils.GSON
import io.legado.app.utils.fromJsonObject

@JsonIgnoreProperties(value = ["variableMap", "_userNameSpace", "userNameSpace"])
data class RssArticle(
    var origin: String = "",
    var sort: String = "",
    var title: String = "",
    var order: Long = 0L,
    var link: String = "",
    var pubDate: String? = null,
    var description: String? = null,
    var content: String? = null,
    var image: String? = null,
    var read: Boolean = false,
    var variable: String? = null
) : RuleDataInterface {

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is RssArticle) return false
        return origin == other.origin && link == other.link
    }

    override fun hashCode(): Int {
        return link.hashCode()
    }

    @delegate:Transient
    override val variableMap: HashMap<String, String> by lazy {
        GSON.fromJsonObject<HashMap<String, String>>(variable).getOrNull() ?: hashMapOf()
    }

    @Transient
    private var _userNameSpace: String = ""

    fun setUserNameSpace(nameSpace: String) {
        _userNameSpace = nameSpace
    }

    override fun getUserNameSpace(): String {
        return _userNameSpace
    }

    override fun putVariable(key: String, value: String?) {
        val map: MutableMap<String, String> = variableMap
        if (value != null) {
            map.put(key, value)
        } else {
            variableMap.remove(key)
        }
        variable = GSON.toJson(variableMap)
    }
}
