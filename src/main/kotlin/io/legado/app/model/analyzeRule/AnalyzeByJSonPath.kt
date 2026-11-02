// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.model.analyzeRule

import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.ReadContext

/**
 * JSONPath 求值：把入参统一解析成 ReadContext，再按 && / || / %% 组合多条规则。
 */
class AnalyzeByJSonPath(json: Any) {

    private var ctx: ReadContext = Companion.parse(json)

    fun getString(rule: String): String? {
        if (rule.isEmpty()) return null
        var result: String? = null
        val ruleAnalyzes = RuleAnalyzer(rule, true)
        val rules = ruleAnalyzes.splitRule("&&", "||")
        if (rules.size == 1) {
            ruleAnalyzes.reSetPos()
            result = ruleAnalyzes.innerRule("{$.") { getString(it) }
            if (result.isEmpty()) {
                try {
                    val ob = ctx.read<Any>(rule)
                    result = if (ob is List<*>) {
                        ob.joinToString("\n")
                    } else {
                        ob.toString()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return result
        }
        val textList = arrayListOf<String>()
        for (rl in rules) {
            val temp = getString(rl)
            if (!temp.isNullOrEmpty()) {
                textList.add(temp)
                if (ruleAnalyzes.elementsType == "||") break
            }
        }
        return textList.joinToString("\n")
    }

    @JvmName("getStringList\$reader_pro")
    fun getStringList(rule: String): List<String> {
        val result = ArrayList<String>()
        if (rule.isEmpty()) return result
        val ruleAnalyzes = RuleAnalyzer(rule, true)
        val rules = ruleAnalyzes.splitRule("&&", "||", "%%")
        if (rules.size == 1) {
            ruleAnalyzes.reSetPos()
            val st = ruleAnalyzes.innerRule("{$.") { getString(it) }
            if (st.isEmpty()) {
                try {
                    val obj = ctx.read<Any>(rule)
                    if (obj is List<*>) {
                        for (o in obj) {
                            result.add(o.toString())
                        }
                    } else {
                        result.add(obj.toString())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                result.add(st)
            }
            return result
        }
        val results = ArrayList<List<String>>()
        for (rl in rules) {
            val temp = getStringList(rl)
            if (temp.isNotEmpty()) {
                results.add(temp)
                if (temp.isNotEmpty() && ruleAnalyzes.elementsType == "||") break
            }
        }
        if (results.size > 0) {
            if ("%%" == ruleAnalyzes.elementsType) {
                for (i in results[0].indices) {
                    for (temp in results) {
                        if (i < temp.size) {
                            result.add(temp[i])
                        }
                    }
                }
            } else {
                for (temp in results) {
                    result.addAll(temp)
                }
            }
        }
        return result
    }

    @JvmName("getObject\$reader_pro")
    fun getObject(rule: String): Any {
        return ctx.read(rule)
    }

    @JvmName("getList\$reader_pro")
    fun getList(rule: String): ArrayList<Any>? {
        val result = ArrayList<Any>()
        if (rule.isEmpty()) return result
        val ruleAnalyzes = RuleAnalyzer(rule, true)
        val rules = ruleAnalyzes.splitRule("&&", "||", "%%")
        if (rules.size == 1) {
            ctx.let {
                try {
                    return it.read<ArrayList<Any>>(rules[0])
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            val results = ArrayList<ArrayList<Any>>()
            for (rl in rules) {
                val temp = getList(rl)
                if (temp != null && temp.isNotEmpty()) {
                    results.add(temp)
                    if (temp.isNotEmpty() && ruleAnalyzes.elementsType == "||") break
                }
            }
            if (results.size > 0) {
                if ("%%" == ruleAnalyzes.elementsType) {
                    for (i in 0 until results[0].size) {
                        for (temp in results) {
                            if (i < temp.size) {
                                temp[i]?.let {
                                    result.add(it)
                                }
                            }
                        }
                    }
                } else {
                    for (temp in results) {
                        result.addAll(temp)
                    }
                }
            }
        }
        return result
    }

    companion object {
        fun parse(json: Any): ReadContext {
            return when (json) {
                is ReadContext -> json
                is String -> JsonPath.parse(json)
                else -> JsonPath.parse(json)
            }
        }
    }
}
