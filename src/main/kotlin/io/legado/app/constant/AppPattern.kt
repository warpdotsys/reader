// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.constant

import java.util.regex.Pattern

object AppPattern {

    val JS_PATTERN: Pattern =
        Pattern.compile("<js>([\\w\\W]*?)</js>|@js:([\\w\\W]*)", Pattern.CASE_INSENSITIVE)

    val EXP_PATTERN: Pattern = Pattern.compile("\\{\\{([\\w\\W]*?)\\}\\}")

    val imgPattern: Pattern = Pattern.compile("<img[^>]*src=\"([^\"]*(?:\"[^>]+\\})?)\"[^>]*>")

    val dataUriRegex = Regex("data:.*?;base64,(.*)")

    val nameRegex = Regex("\\s+作\\s*者.*|\\s+\\S+\\s+著")

    val authorRegex = Regex("^\\s*作\\s*者[:：\\s]+|\\s+著")

    val fileNameRegex = Regex("[\\\\/:*?\"<>|.]")

    val splitGroupRegex = Regex("[,;，；]")

    val debugMessageSymbolRegex = Regex("[⇒◇┌└≡]")

    val bookFileRegex = Regex(".*\\.(txt|epub|umd)", RegexOption.IGNORE_CASE)

    val bdRegex = Regex("(\\p{P})+")

    val rnRegex = Regex("[\\r\\n]")

    val notReadAloudRegex = Regex("^(\\s|\\p{C}|\\p{P}|\\p{Z}|\\p{S})+\$")
}
