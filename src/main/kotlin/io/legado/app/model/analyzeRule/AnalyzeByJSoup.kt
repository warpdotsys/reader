// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.model.analyzeRule

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import org.jsoup.select.Collector
import org.jsoup.select.Elements
import org.jsoup.select.Evaluator
import org.seimicrawler.xpath.JXNode

/**
 * jsoup 规则求值：按 && / || / %% 组合多条规则，末段规则决定取值方式。
 */
class AnalyzeByJSoup(doc: Any) {

    private var element: Element = parse(doc)

    @JvmName("getElements\$reader_pro")
    fun getElements(rule: String): Elements {
        return getElements(element, rule)
    }

    @JvmName("getString\$reader_pro")
    fun getString(ruleStr: String): String? {
        return if (ruleStr.isEmpty()) {
            null
        } else {
            val list = getStringList(ruleStr)
            list.takeIf { it.isNotEmpty() }?.joinToString("\n")
        }
    }

    @JvmName("getString0\$reader_pro")
    fun getString0(ruleStr: String): String {
        val list = getStringList(ruleStr)
        return if (list.isEmpty()) "" else list[0]
    }

    @JvmName("getStringList\$reader_pro")
    fun getStringList(ruleStr: String): List<String> {
        val textS = ArrayList<String>()
        if (ruleStr.isEmpty()) return textS
        val sourceRule = SourceRule(ruleStr)
        if (sourceRule.elementsRule.isEmpty()) {
            textS.add(element.data() ?: "")
        } else {
            val ruleAnalyzes = RuleAnalyzer(sourceRule.elementsRule)
            val ruleStrS = ruleAnalyzes.splitRule("&&", "||", "%%")
            val results = ArrayList<List<String>>()
            for (ruleStrX in ruleStrS) {
                val temp = if (sourceRule.isCss) {
                    val lastIndex = ruleStrX.lastIndexOf('@')
                    getResultLast(
                        element.select(ruleStrX.substring(0, lastIndex)),
                        ruleStrX.substring(lastIndex + 1)
                    )
                } else {
                    getResultList(ruleStrX)
                }
                if (temp.isNullOrEmpty()) continue
                results.add(temp)
                if (ruleAnalyzes.elementsType == "||") break
            }
            if (results.size > 0) {
                if ("%%" == ruleAnalyzes.elementsType) {
                    for (i in 0..results[0].size - 1) {
                        for (temp in results) {
                            if (i >= temp.size) continue
                            textS.add(temp[i])
                        }
                    }
                } else {
                    for (temp in results) {
                        textS.addAll(temp)
                    }
                }
            }
        }
        return textS
    }

    private fun getElements(temp: Element?, rule: String): Elements {
        // `===` 而非 `==`：-Xjsr305=strict 把 jsoup Node.equals(Object) 的参数当非空，
        // `temp == null` 会报 "No method 'equals(Any?): Boolean' available"。两者字节码相同。
        if (temp === null || rule.isEmpty()) return Elements()
        val elements = Elements()
        val sourceRule = SourceRule(rule)
        val ruleAnalyzes = RuleAnalyzer(sourceRule.elementsRule)
        val ruleStrS = ruleAnalyzes.splitRule("&&", "||", "%%")
        val elementsList = ArrayList<Elements>()
        if (sourceRule.isCss) {
            for (ruleStr in ruleStrS) {
                val tempS = temp.select(ruleStr)
                elementsList.add(tempS)
                if (tempS.size <= 0 || ruleAnalyzes.elementsType != "||") continue
                break
            }
        } else {
            for (ruleStr in ruleStrS) {
                val rsRule = RuleAnalyzer(ruleStr)
                rsRule.trim()
                val rs = rsRule.splitRule("@")
                val elements2 = if (rs.size > 1) {
                    val el = Elements()
                    el.add(temp)
                    for (rl in rs) {
                        val es = Elements()
                        for (et in el) {
                            es.addAll(getElements(et, rl))
                        }
                        el.clear()
                        el.addAll(es)
                    }
                    el
                } else {
                    ElementsSingle().getElementsSingle(temp, ruleStr)
                }
                elementsList.add(elements2)
                if (elements2.size <= 0 || ruleAnalyzes.elementsType != "||") continue
                break
            }
        }
        if (elementsList.size > 0) {
            if ("%%" == ruleAnalyzes.elementsType) {
                for (i in 0 until elementsList[0].size) {
                    for (es in elementsList) {
                        if (i >= es.size) continue
                        elements.add(es[i])
                    }
                }
            } else {
                for (es in elementsList) {
                    elements.addAll(es)
                }
            }
        }
        return elements
    }

    private fun getResultList(ruleStr: String): List<String>? {
        if (ruleStr.isEmpty()) return null
        var elements = Elements()
        elements.add(element)
        val rule = RuleAnalyzer(ruleStr)
        rule.trim()
        val rules = rule.splitRule("@")
        val last = rules.size - 1
        for (i in 0 until last) {
            val es = Elements()
            for (elt in elements) {
                es.addAll(ElementsSingle().getElementsSingle(elt, rules[i]))
            }
            elements.clear()
            elements = es
        }
        return if (elements.isEmpty()) null else getResultLast(elements, rules[last])
    }

    private fun getResultLast(elements: Elements, lastRule: String): List<String> {
        val textS = ArrayList<String>()
        when (lastRule) {
            "text" -> {
                for (element in elements) {
                    val text = element.text()
                    if (text.isNotEmpty()) textS.add(text)
                }
            }
            "textNodes" -> {
                for (element in elements) {
                    val tn = arrayListOf<String>()
                    val contentEs: List<TextNode> = element.textNodes()
                    for (item in contentEs) {
                        val text = item.text().trim { it <= ' ' }
                        if (text.isNotEmpty()) tn.add(text)
                    }
                    if (tn.isNotEmpty()) textS.add(tn.joinToString("\n"))
                }
            }
            "ownText" -> {
                for (element in elements) {
                    val text = element.ownText()
                    if (text.isNotEmpty()) textS.add(text)
                }
            }
            "html" -> {
                elements.select("script").remove()
                elements.select("style").remove()
                val html = elements.outerHtml()
                if (html.isNotEmpty()) textS.add(html)
            }
            "all" -> {
                textS.add(elements.outerHtml())
            }
            else -> {
                for (element in elements) {
                    val url = element.attr(lastRule)
                    if (url.isBlank() || textS.contains(url)) continue
                    textS.add(url)
                }
            }
        }
        return textS
    }

    companion object {
        val validKeys = arrayOf("class", "id", "tag", "text", "children")

        fun parse(doc: Any): Element {
            return when (doc) {
                is Element -> doc
                is JXNode -> if (doc.isElement) doc.asElement() else Jsoup.parse(doc.toString())
                else -> Jsoup.parse(doc.toString())
            }
        }
    }

    inner class SourceRule(ruleStr: String) {

        var isCss = false

        var elementsRule: String = if (ruleStr.startsWith("@CSS:", true)) {
            isCss = true
            ruleStr.substring(5).trim { it <= ' ' }
        } else {
            ruleStr
        }
    }

    data class ElementsSingle(
        var split: Char = '.',
        var beforeRule: String = "",
        val indexDefault: MutableList<Int> = arrayListOf(),
        val indexes: MutableList<Any> = arrayListOf()
    ) {

        fun getElementsSingle(temp: Element, rule: String): Elements {
            findIndexSet(rule)
            var elements = if (beforeRule.isEmpty()) {
                temp.children()
            } else {
                val rules = beforeRule.split(".")
                when (rules[0]) {
                    "children" -> temp.children()
                    "class" -> temp.getElementsByClass(rules[1])
                    "tag" -> temp.getElementsByTag(rules[1])
                    "id" -> Collector.collect(Evaluator.Id(rules[1]), temp)
                    "text" -> temp.getElementsContainingOwnText(rules[1])
                    else -> temp.select(beforeRule)
                }
            }
            val len = elements.size
            val lastIndexes = (indexDefault.size - 1).takeIf { it != -1 } ?: (indexes.size - 1)
            val indexSet: MutableSet<Int> = linkedSetOf()
            if (indexes.isEmpty()) {
                var n = lastIndexes
                while (0 <= n) {
                    val ix = n
                    n--
                    val it3 = indexDefault[ix]
                    if (it3 in 0 until len) {
                        indexSet.add(it3)
                        continue
                    }
                    if (it3 < 0 && len >= -it3) {
                        indexSet.add(it3 + len)
                    }
                }
            } else {
                var n = lastIndexes
                while (0 <= n) {
                    val ix = n
                    n--
                    if (indexes[ix] is Triple<*, *, *>) {
                        val it3 = indexes[ix] as Triple<Int?, Int?, Int>
                        val (startX, endX, stepX) = it3
                        val start = if (startX == null) 0 else if (startX >= 0) {
                            if (startX < len) startX else len - 1
                        } else {
                            if (-startX <= len) len + startX else 0
                        }
                        val end = if (endX == null) len - 1 else if (endX >= 0) {
                            if (endX < len) endX else len - 1
                        } else {
                            if (-endX <= len) len + endX else 0
                        }
                        if (start == end || stepX >= len) {
                            indexSet.add(start)
                            continue
                        }
                        val step = if (stepX > 0) stepX else if (-stepX < len) stepX + len else 1
                        indexSet.addAll(
                            if (end > start) {
                                (start..end).step(step)
                            } else {
                                (start downTo end).step(step)
                            }
                        )
                        continue
                    }
                    val it3 = indexes[ix] as Int
                    if (it3 in 0 until len) {
                        indexSet.add(it3)
                        continue
                    }
                    if (it3 < 0 && len >= -it3) {
                        indexSet.add(it3 + len)
                    }
                }
            }
            if (split == '!') {
                for (pcInt in indexSet) {
                    elements.set(pcInt, null)
                }
                elements.removeAll(listOf(null))
            } else if (split == '.') {
                val es = Elements()
                for (pcInt in indexSet) {
                    es.add(elements[pcInt])
                }
                elements = es
            }
            return elements
        }

        private fun findIndexSet(rule: String) {
            val rus = rule.trim { it <= ' ' }
            var len = rus.length
            var curInt: Int? = null
            var curMinus = false
            val curList: MutableList<Int?> = arrayListOf()
            var l = ""
            val head = rus.last() == ']'
            if (head) {
                len--
                while (len-- >= 0) {
                    var rl = rus[len]
                    if (rl == ' ') continue
                    if (rl in '0'..'9') {
                        l = rl + l
                        continue
                    }
                    if (rl == '-') {
                        curMinus = true
                        continue
                    }
                    curInt = if (l.isEmpty()) null else if (curMinus) -l.toInt() else l.toInt()
                    when (rl) {
                        ':' -> curList.add(curInt)
                        else -> {
                            if (curList.isEmpty()) {
                                if (curInt == null) break
                                indexes.add(curInt)
                            } else {
                                indexes.add(
                                    Triple(
                                        curInt,
                                        curList.last(),
                                        if (curList.size == 2) curList.first() else 1
                                    )
                                )
                                curList.clear()
                            }
                            if (rl == '!') {
                                split = '!'
                                do {
                                    rl = rus[--len]
                                } while (len > 0 && rl == ' ')
                            }
                            if (rl == '[') {
                                beforeRule = rus.substring(0, len)
                                return
                            }
                            if (rl != ',') break
                        }
                    }
                    l = ""
                    curMinus = false
                }
            }
            while (len-- >= 0) {
                var rl = rus[len]
                if (rl == ' ') continue
                if (rl in '0'..'9') {
                    l = rl + l
                    continue
                }
                if (rl == '-') {
                    curMinus = true
                    continue
                }
                if (rl == '!' || rl == '.' || rl == ':') {
                    indexDefault.add(if (curMinus) -l.toInt() else l.toInt())
                    if (rl != ':') {
                        split = rl
                        beforeRule = rus.substring(0, len)
                        return
                    } else {
                        l = ""
                        curMinus = false
                    }
                } else {
                    break
                }
            }
            split = ' '
            beforeRule = rus
        }
    }
}
