// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.utils

import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

object StringUtils {

    private val TAG = "StringUtils"
    private const val HOUR_OF_DAY = 24
    private const val DAY_OF_YESTERDAY = 2
    private const val TIME_UNIT = 60

    private val ChnMap: HashMap<Char, Int> = getChnMap()

    private fun getChnMap(): HashMap<Char, Int> {
        val map = HashMap<Char, Int>()
        var cnStr = "零一二三四五六七八九十"
        var cn = cnStr.toCharArray()
        for (i in cn.indices) {
            map[cn[i]] = i
        }
        cnStr = "〇壹贰叁肆伍陆柒捌玖拾"
        cn = cnStr.toCharArray()
        for (i in cn.indices) {
            map[cn[i]] = i
        }
        map['两'] = 2
        map['百'] = 100
        map['佰'] = 100
        map['千'] = 1000
        map['仟'] = 1000
        map['万'] = 10000
        map['亿'] = 100000000
        return map
    }

    fun dateConvert(time: Long, pattern: String): String {
        val date = Date(time)
        val format = SimpleDateFormat(pattern)
        return format.format(date)
    }

    fun dateConvert(source: String, pattern: String): String {
        val format = SimpleDateFormat(pattern)
        val calendar = Calendar.getInstance()
        try {
            val date = format.parse(source)!!
            val curTime = calendar.timeInMillis
            calendar.time = date
            val difSec = Math.abs((curTime - date.time) / 1000)
            val difMin = difSec / 60
            val difHour = difMin / 60
            val difDate = difHour / 60
            val oldHour = calendar.get(10)
            if (oldHour == 0) {
                if (difDate == 0L) {
                    return "今天"
                }
                if (difDate < 2) {
                    return "昨天"
                }
                val convertFormat = SimpleDateFormat("yyyy-MM-dd")
                return convertFormat.format(date)
            }
            return when {
                difSec < 60 -> difSec.toString() + "秒前"
                difMin < 60 -> difMin.toString() + "分钟前"
                difHour < 24 -> difHour.toString() + "小时前"
                difDate < 2 -> "昨天"
                else -> {
                    val convertFormat = SimpleDateFormat("yyyy-MM-dd")
                    convertFormat.format(date)
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }
    }

    fun toSize(length: Long): String {
        if (length <= 0) {
            return "0"
        }
        val units = arrayOf("b", "kb", "M", "G", "T")
        val digitGroups = (Math.log10(length.toDouble()) / Math.log10(1024.0)).toInt()
        return DecimalFormat("#,##0.##")
            .format(length.toDouble() / Math.pow(1024.0, digitGroups.toDouble())) + ' ' + units[digitGroups]
    }

    fun toFirstCapital(str: String): String {
        val first = str.substring(0, 1)
        val upper = first.uppercase(Locale.getDefault())
        return upper + str.substring(1)
    }

    fun halfToFull(input: String): String {
        val c = input.toCharArray()
        for (i in c.indices) {
            if (c[i] == ' ') {
                c[i] = 12288.toChar()
                continue
            }
            if (c[i] in '!'..'~') {
                c[i] = (c[i] + 65248).toChar()
            }
        }
        return String(c)
    }

    fun fullToHalf(input: String): String {
        val c = input.toCharArray()
        for (i in c.indices) {
            if (c[i] == '\u3000') {
                c[i] = 32.toChar()
                continue
            }
            if (c[i] in '\uff01'..'\uff5e') {
                c[i] = (c[i] - 65248).toChar()
            }
        }
        return String(c)
    }

    fun chineseNumToInt(chNum: String): Int {
        var result = 0
        var tmp = 0
        var billion = 0
        val cn = chNum.toCharArray()
        if (cn.size > 1) {
            if (Regex("^[〇零一二三四五六七八九壹贰叁肆伍陆柒捌玖]$").matches(chNum)) {
                for (i in cn.indices) {
                    cn[i] = (48 + ChnMap[cn[i]]!!).toChar()
                }
                return Integer.parseInt(String(cn))
            }
        }
        val res = runCatching {
            for (i in cn.indices) {
                val tmpNum = ChnMap[cn[i]]!!
                if (tmpNum == 100000000) {
                    result += tmp
                    billion = billion * 100000000 + result * tmpNum
                    result = 0
                    tmp = 0
                    continue
                }
                if (tmpNum == 10000) {
                    result += tmp
                    result *= tmpNum
                    tmp = 0
                    continue
                }
                if (tmpNum >= 10) {
                    if (tmp == 0) {
                        tmp = 1
                    }
                    result += tmpNum * tmp
                    tmp = 0
                    continue
                }
                if (i >= 2 && i == cn.size - 1) {
                    val prev = ChnMap[cn[i - 1]]!!
                    if (prev > 10) {
                        tmp = tmpNum * prev / 10
                        continue
                    }
                }
                tmp = tmp * 10 + tmpNum
            }
            result + tmp + billion
        }
        return res.getOrElse { -1 }
    }

    fun stringToInt(str: String?): Int {
        if (str != null) {
            val num = Regex("\\s+").replace(fullToHalf(str), "")
            return runCatching { Integer.parseInt(num) }.getOrElse { chineseNumToInt(num) }
        }
        return -1
    }

    fun isContainNumber(company: String): Boolean {
        val p = Pattern.compile("[0-9]+")
        val m = p.matcher(company)
        return m.find()
    }

    fun isNumeric(str: String): Boolean {
        val pattern = Pattern.compile("-?[0-9]+")
        val isNum = pattern.matcher(str)
        return isNum.matches()
    }

    fun wordCountFormat(wc: String?): String {
        if (wc == null) {
            return ""
        }
        var wordsS = ""
        var words = 0
        if (isNumeric(wc)) {
            words = Integer.parseInt(wc)
            if (words > 0) {
                wordsS = "" + words + '字'
                if (words > 10000) {
                    val df = DecimalFormat("#.#")
                    wordsS = df.format((words * 1.0f).toDouble() / 10000.0) + "万字"
                }
            }
        } else {
            wordsS = wc
        }
        return wordsS
    }

    fun trim(s: String): String {
        if (s.isEmpty()) {
            return ""
        }
        val len = s.length
        var end = len - 1
        var start = 0
        while (start < end) {
            val c = s[start]
            if (c > ' ' && s[start] != '\u3000') break
            start++
        }
        while (start < end) {
            val c = s[end]
            if (c > ' ' && s[end] != '\u3000') break
            end--
        }
        if (end < len) {
            end++
        }
        return if (start > 0 || end < len) s.substring(start, end) else s
    }

    fun repeat(str: String, n: Int): String {
        val stringBuilder = StringBuilder()
        for (i in 0 until n) {
            stringBuilder.append(str)
        }
        return stringBuilder.toString()
    }

    fun removeUTFCharacters(data: String?): String? {
        if (data == null) {
            return null
        }
        val p = Pattern.compile("\\\\u(\\p{XDigit}{4})")
        val m = p.matcher(data)
        val buf = StringBuffer(data.length)
        while (m.find()) {
            val ch = Integer.parseInt(m.group(1), 16).toChar().toString()
            m.appendReplacement(buf, java.util.regex.Matcher.quoteReplacement(ch))
        }
        m.appendTail(buf)
        return buf.toString()
    }

    fun formatHtml(html: String): String {
        if (TextUtils.isEmpty(html)) {
            return ""
        }
        return html
            .replace(Regex("(?i)<(br[\\s/]*|/*p.*?|/*div.*?)>"), "\n")
            .replace(Regex("<[script>]*.*?>|&nbsp;"), "")
            .replace(Regex("\\s*\\n+\\s*"), "\n　　")
            .replace(Regex("^[\\n\\s]+"), "　　")
            .replace(Regex("[\\n\\s]+$"), "")
    }

    fun byteToHexString(bytes: ByteArray?): String {
        if (bytes == null) {
            return ""
        }
        val sb = StringBuilder(bytes.size * 2)
        for (b in bytes) {
            val hex = 0xFF and b.toInt()
            if (hex < 16) {
                sb.append('0')
            }
            sb.append(Integer.toHexString(hex))
        }
        return sb.toString()
    }

    fun hexStringToByte(hexString: String): ByteArray {
        val hexStr = hexString.replace(" ", "")
        val len = hexStr.length
        val bytes = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            bytes[i / 2] = ((Character.digit(hexString[i], 16) shl 4) +
                Character.digit(hexString[i + 1], 16)).toByte()
            i += 2
        }
        return bytes
    }
}
