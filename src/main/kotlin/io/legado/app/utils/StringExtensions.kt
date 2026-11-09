// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.utils

import io.legado.app.constant.AppPattern

fun String.cnCompare(other: String): Int {
    return this.compareTo(other)
}

fun String?.safeTrim(): String? {
    return if (isNullOrBlank()) {
        null
    } else {
        this.trim().toString()
    }
}

fun String?.isAbsUrl(): Boolean {
    if (isNullOrBlank()) {
        return false
    }
    return this.startsWith("http://", true) || this.startsWith("https://", true)
}

fun String?.isDataUrl(): Boolean {
    return this?.let { AppPattern.dataUriRegex.matches(it) } ?: false
}

fun String?.isJson(): Boolean {
    return this?.let {
        val str = it.trim().toString()
        (str.startsWith("{") && str.endsWith("}")) ||
            (str.startsWith("[") && str.endsWith("]"))
    } ?: false
}

fun String?.isJsonObject(): Boolean {
    return this?.let {
        val str = it.trim().toString()
        str.startsWith("{") && str.endsWith("}")
    } ?: false
}

fun String?.isJsonArray(): Boolean {
    return this?.let {
        val str = it.trim().toString()
        str.startsWith("[") && str.endsWith("]")
    } ?: false
}

fun String?.isXml(): Boolean {
    return this?.let {
        val str = it.trim().toString()
        str.startsWith("<") && str.endsWith(">")
    } ?: false
}

fun String?.isTrue(nullIsTrue: Boolean = false): Boolean {
    if (this == null || this.isBlank() || this == "null") {
        return nullIsTrue
    }
    return !Regex("\\s*(?i)(false|no|not|0)\\s*").matches(this)
}

fun String?.htmlFormat(): String {
    if (this == null || this.isBlank()) {
        return ""
    }
    return this
        .replace(Regex("(?i)<(br[\\s/]*|/*p\\b.*?|/*div\\b.*?)>"), "\n")
        .replace(Regex("<[script>]*.*?>|&nbsp;"), "")
        .replace(Regex("\\s*\\n+\\s*"), "\n　　")
        .replace(Regex("^[\\n\\s]+"), "　　")
        .replace(Regex("[\\n\\s]+$"), "")
}

fun String.splitNotBlank(vararg delimiter: String): Array<String> {
    return split(*delimiter)
        .map { it.trim().toString() }
        .filterNot { it.isBlank() }
        .toTypedArray()
}

fun String.splitNotBlank(regex: Regex, limit: Int = 0): Array<String> {
    return regex.split(this, limit)
        .map { it.trim().toString() }
        .filterNot { it.isBlank() }
        .toTypedArray()
}

fun String.startWithIgnoreCase(start: String): Boolean {
    return if (this.isBlank()) false else this.startsWith(start, true)
}

fun String.toStringArray(): Array<String> {
    var codePointIndex = 0
    return try {
        val count = this.codePointCount(0, this.length)
        val result = Array(count) { "" }
        for (i in 0 until count) {
            val start = codePointIndex
            codePointIndex = this.offsetByCodePoints(start, 1)
            result[i] = this.substring(start, codePointIndex)
        }
        result
    } catch (e: Exception) {
        this.split("").toTypedArray()
    }
}
