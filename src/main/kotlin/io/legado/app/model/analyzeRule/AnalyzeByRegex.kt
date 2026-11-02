// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.model.analyzeRule

import java.util.regex.Pattern

/**
 * 正则链求值：每一层匹配的结果拼起来喂给下一层，最后一层按分组拆成字段。
 */
object AnalyzeByRegex {

    fun getElement(res: String, regs: Array<String>, index: Int = 0): List<String>? {
        var vIndex = index
        val resM = Pattern.compile(regs[vIndex]).matcher(res)
        if (!resM.find()) {
            return null
        }
        return if (vIndex + 1 == regs.size) {
            val info = arrayListOf<String>()
            for (groupIndex in 0..resM.groupCount()) {
                info.add(resM.group(groupIndex)!!)
            }
            info
        } else {
            val result = StringBuilder()
            do {
                result.append(resM.group())
            } while (resM.find())
            getElement(result.toString(), regs, ++vIndex)
        }
    }

    fun getElements(res: String, regs: Array<String>, index: Int = 0): List<List<String>> {
        var vIndex = index
        val resM = Pattern.compile(regs[vIndex]).matcher(res)
        if (!resM.find()) {
            return arrayListOf()
        }
        if (vIndex + 1 == regs.size) {
            val books = ArrayList<ArrayList<String>>()
            do {
                val info = arrayListOf<String>()
                for (groupIndex in 0..resM.groupCount()) {
                    info.add(resM.group(groupIndex) ?: "")
                }
                books.add(info)
            } while (resM.find())
            return books
        }
        val result = StringBuilder()
        do {
            result.append(resM.group())
        } while (resM.find())
        return getElements(result.toString(), regs, ++vIndex)
    }
}
