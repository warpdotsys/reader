// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.model.analyzeRule

import io.legado.app.utils.GSON

/**
 * 不落盘的变量表：只活在内存里，[getVariable] 直接把它序列化成 JSON。
 */
class RuleData : RuleDataInterface {

    override val variableMap: HashMap<String, String> by lazy { hashMapOf() }

    override fun putVariable(key: String, value: String?) {
        if (value == null) {
            variableMap.remove(key)
        } else {
            variableMap[key] = value
        }
    }

    fun getVariable(): String? {
        if (variableMap.isEmpty()) {
            return null
        }
        return GSON.toJson(variableMap)
    }

    override fun getUserNameSpace(): String {
        return "unknow"
    }
}
