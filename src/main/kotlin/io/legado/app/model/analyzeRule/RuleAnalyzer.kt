package io.legado.app.model.analyzeRule

import java.util.Arrays
import kotlin.jvm.JvmName
import kotlin.reflect.KFunction

class RuleAnalyzer(data: String, code: Boolean = false) {

    private var queue = data
    private var pos = 0
    private var start = 0
    private var startX = 0
    private var rule = ArrayList<String>()
    private var step = 0
    var elementsType = ""
    var innerType = true
    val ruleTypeList = ArrayList<String>()
    val chompBalanced: KFunction<Boolean> =
        if (code) ::chompCodeBalanced else ::chompRuleBalanced

    companion object {
        private const val ESC = '\\'
    }

    fun trim() {
        if (queue[pos] == '@' || queue[pos] < '!') {
            pos++
            while (queue[pos] == '@' || queue[pos] < '!') {
                pos++
            }
            start = pos
            startX = pos
        }
    }

    fun reSetPos() {
        pos = 0
        startX = 0
    }

    fun consumeTo(seq: String): Boolean {
        start = pos
        val offset = queue.indexOf(seq, pos)
        return if (offset != -1) {
            pos = offset
            true
        } else {
            false
        }
    }

    fun consumeToAny(vararg seq: String): Boolean {
        var pos = pos
        while (pos != queue.length) {
            for (s in seq) {
                if (queue.regionMatches(pos, s, 0, s.length)) {
                    step = s.length
                    this.pos = pos
                    return true
                }
            }
            pos++
        }
        return false
    }

    private fun findToAny(vararg seq: Char): Int {
        var pos = pos
        while (pos != queue.length) {
            for (s in seq) {
                if (queue[pos] == s) return pos
            }
            pos++
        }
        return -1
    }

    fun chompCodeBalanced(open: Char, close: Char): Boolean {
        var pos = pos
        var depth = 0
        var otherDepth = 0
        var inSingleQuote = false
        var inDoubleQuote = false
        while (pos != queue.length) {
            val c = queue[pos++]
            if (c != ESC) {
                if (c == '\'' && !inDoubleQuote) {
                    inSingleQuote = !inSingleQuote
                } else if (c == '"' && !inSingleQuote) {
                    inDoubleQuote = !inDoubleQuote
                }
                if (!inSingleQuote && !inDoubleQuote) {
                    when (c) {
                        '[' -> depth++
                        ']' -> depth--
                        else -> if (depth == 0) {
                            when (c) {
                                open -> otherDepth++
                                close -> otherDepth--
                            }
                        }
                    }
                }
            } else {
                pos++
            }
            if (depth <= 0 && otherDepth <= 0) break
        }
        return if (depth > 0 || otherDepth > 0) {
            false
        } else {
            this.pos = pos
            true
        }
    }

    fun chompRuleBalanced(open: Char, close: Char): Boolean {
        var pos = pos
        var depth = 0
        var inSingleQuote = false
        var inDoubleQuote = false
        while (pos != queue.length) {
            val c = queue[pos++]
            if (c == '\'' && !inDoubleQuote) {
                inSingleQuote = !inSingleQuote
            } else if (c == '"' && !inSingleQuote) {
                inDoubleQuote = !inDoubleQuote
            }
            if (!inSingleQuote && !inDoubleQuote) {
                when (c) {
                    ESC -> pos++
                    open -> depth++
                    close -> depth--
                }
            }
            if (depth <= 0) break
        }
        return if (depth > 0) {
            false
        } else {
            this.pos = pos
            true
        }
    }

    fun splitRule(vararg split: String): ArrayList<String> {
        var tmpSplit = split
        while (true) {
            if (tmpSplit.size == 1) {
                elementsType = tmpSplit[0]
                if (!consumeTo(elementsType)) {
                    rule += queue.substring(startX)
                    return rule
                }
                step = elementsType.length
                return splitRuleNext()
            }
            if (!consumeToAny(*tmpSplit)) {
                rule += queue.substring(startX)
                return rule
            }
            val end = pos
            pos = start
            do {
                val st = findToAny('[', '(')
                if (st == -1) {
                    rule = arrayListOf(queue.substring(startX, end))
                    elementsType = queue.substring(end, end + step)
                    pos = end + step
                    while (consumeTo(elementsType)) {
                        rule += queue.substring(start, pos)
                        pos += step
                    }
                    rule += queue.substring(pos)
                    return rule
                }
                if (st > end) {
                    rule = arrayListOf(queue.substring(startX, end))
                    elementsType = queue.substring(end, end + step)
                    pos = end + step
                    while (consumeTo(elementsType) && pos < st) {
                        rule += queue.substring(start, pos)
                        pos += step
                    }
                    if (pos > st) {
                        startX = start
                        return splitRuleNext()
                    } else {
                        rule += queue.substring(pos)
                        return rule
                    }
                }
                pos = st
                val next = if (queue[pos] == '[') ']' else ')'
                @Suppress("UNCHECKED_CAST")
                if ((chompBalanced as Function2<Char, Char, Boolean>)(queue[pos], next)) continue
                throw Error(queue.substring(0, start) + "后未平衡")
            } while (end > pos)
            start = pos
            tmpSplit = Arrays.copyOf(tmpSplit, tmpSplit.size)
        }
    }

    @JvmName("splitRuleNext")
    private fun splitRuleNext(): ArrayList<String> {
        block0@ while (true) {
            val end = pos
            pos = start
            do {
                val st = findToAny('[', '(')
                if (st == -1) {
                    rule += arrayOf(queue.substring(startX, end))
                    pos = end + step
                    while (consumeTo(elementsType)) {
                        rule += queue.substring(start, pos)
                        pos += step
                    }
                    rule += queue.substring(pos)
                    return rule
                }
                if (st > end) {
                    rule += arrayListOf(queue.substring(startX, end))
                    pos = end + step
                    while (consumeTo(elementsType) && pos < st) {
                        rule += queue.substring(start, pos)
                        pos += step
                    }
                    if (pos > st) {
                        startX = start
                        continue@block0
                    }
                    rule += queue.substring(pos)
                    return rule
                }
                pos = st
                val next = if (queue[pos] == '[') ']' else ')'
                @Suppress("UNCHECKED_CAST")
                if ((chompBalanced as Function2<Char, Char, Boolean>)(queue[pos], next)) continue
                throw Error(queue.substring(0, start) + "后未平衡")
            } while (end > pos)
            start = pos
            if (!consumeTo(elementsType)) break
        }
        rule += queue.substring(startX)
        return rule
    }

    fun innerRule(
        inner: String,
        startStep: Int = 1,
        endStep: Int = 1,
        fr: (String) -> String?
    ): String {
        val st = StringBuilder()
        while (consumeTo(inner)) {
            val posPre = pos
            if (chompCodeBalanced('{', '}')) {
                val frv = fr(queue.substring(posPre + startStep, pos - endStep))
                if (!frv.isNullOrEmpty()) {
                    st.append(queue.substring(startX, posPre) + frv)
                    startX = pos
                    continue
                }
            }
            pos += inner.length
        }
        return if (startX == 0) "" else st.apply {
            append(queue.substring(startX))
        }.toString()
    }

    fun innerRule(startStr: String, endStr: String, fr: (String) -> String): String {
        val st = StringBuilder()
        while (consumeTo(startStr)) {
            pos += startStr.length
            val posPre = pos
            if (consumeTo(endStr)) {
                val frv = fr(queue.substring(posPre, pos))
                st.append(queue.substring(startX, posPre - startStr.length) + frv)
                pos += endStr.length
                startX = pos
            }
        }
        return if (startX == 0) queue else st.apply {
            append(queue.substring(startX))
        }.toString()
    }
}
