// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.model.analyzeRule

/**
 * 规则求值时变量表的统一入口：一张字符串表 + 一个命名空间。
 *
 * [getVariable] 有默认实现，取不到就是 null；[putVariable] 传 null 表示删除。
 */
interface RuleDataInterface {

    val variableMap: HashMap<String, String>

    fun putVariable(key: String, value: String?)

    fun getVariable(key: String): String? = variableMap[key]

    fun getUserNameSpace(): String
}
