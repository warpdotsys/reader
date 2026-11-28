// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.utils

import io.legado.app.model.analyzeRule.AnalyzeUrl
import java.net.URL
import java.util.regex.Pattern

object HtmlFormatter {

    private val wrapHtmlRegex = Regex("</?(?:div|p|br|hr|h\\d|article|dd|dl)[^>]*>")
    private val commentRegex = Regex("<!--[^>]*-->")
    private val notImgHtmlRegex = Regex("</?(?!img)[a-zA-Z]+(?=[ >])[^<>]*>")
    private val otherHtmlRegex = Regex("</?[a-zA-Z]+(?=[ >])[^<>]*>")

    private val formatImagePattern: Pattern = Pattern.compile(
        "<img[^>]*src *= *\"([^\"{]*\\{(?:[^{}]|\\{[^}]+\\})+\\})\"[^>]*>" +
            "|<img[^>]*data-[^=]*= *\"([^\"]*)\"[^>]*>" +
            "|<img[^>]*src *= *\"([^\"]*)\"[^>]*>",
        Pattern.CASE_INSENSITIVE
    )

    fun format(html: String?, otherRegex: Regex = otherHtmlRegex): String {
        if (html == null) {
            return ""
        }
        return html
            .replace(wrapHtmlRegex, "\n")
            .replace(commentRegex, "")
            .replace(otherRegex, "")
            .replace(Regex("\\s*\\n+\\s*"), "\n　　")
            .replace(Regex("^[\\n\\s]+"), "　　")
            .replace(Regex("[\\n\\s]+$"), "")
    }

    fun formatKeepImg(html: String?, redirectUrl: URL? = null): String {
        if (html == null) {
            return ""
        }
        val keepImgHtml = format(html, notImgHtmlRegex)
        val matcher = formatImagePattern.matcher(keepImgHtml)
        var appendPos = 0
        val sb = StringBuffer()
        while (matcher.find()) {
            var param = ""
            val firstGroup = matcher.group(1)
            val url: String? = if (firstGroup == null) {
                null
            } else {
                val urlMatcher = AnalyzeUrl.paramPattern.matcher(firstGroup)
                if (urlMatcher.find()) {
                    param = "," + firstGroup.substring(urlMatcher.end())
                    firstGroup.substring(0, urlMatcher.start())
                } else {
                    firstGroup
                }
            }
            val src = url ?: matcher.group(2) ?: matcher.group(3)!!
            sb.append(keepImgHtml.substring(appendPos, matcher.start()))
            sb.append("<img src=\"")
                .append(NetworkUtils.getAbsoluteURL(redirectUrl, src))
                .append(param)
                .append("\">")
            appendPos = matcher.end()
        }
        if (appendPos < keepImgHtml.length) {
            sb.append(keepImgHtml.substring(appendPos, keepImgHtml.length))
        }
        return sb.toString()
    }
}
