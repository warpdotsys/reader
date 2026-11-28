// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.utils

import io.legado.app.lib.icu4j.CharsetDetector
import org.jsoup.Jsoup
import java.io.File
import java.io.FileInputStream
import java.nio.charset.StandardCharsets
import java.util.Locale

object EncodingDetect {

    fun getHtmlEncode(bytes: ByteArray): String? {
        try {
            val doc = Jsoup.parse(String(bytes, StandardCharsets.UTF_8))
            val metaTags = doc.getElementsByTag("meta")
            var charsetStr: String? = null
            for (metaTag in metaTags) {
                charsetStr = metaTag.attr("charset")
                if (!charsetStr.isNullOrEmpty()) {
                    return charsetStr
                }
                val content = metaTag.attr("content")
                val httpEquiv = metaTag.attr("http-equiv")
                if (httpEquiv.toLowerCase(Locale.getDefault()) != "content-type") continue
                val lowerContent = content.toLowerCase(Locale.getDefault())
                charsetStr = if (lowerContent.contains("charset")) {
                    content.substring(lowerContent.indexOf("charset") + "charset=".length)
                } else {
                    content.substring(lowerContent.indexOf(";") + 1)
                }
                if (charsetStr.isNullOrEmpty()) continue
                return charsetStr
            }
        } catch (e: Exception) {
            // 解析失败就走下面的嗅探
        }
        return getEncode(bytes)
    }

    fun getEncode(bytes: ByteArray): String {
        val match = CharsetDetector().setText(bytes).detect()
        return match?.name ?: "UTF-8"
    }

    fun getEncode(filePath: String): String {
        return getEncode(File(filePath))
    }

    fun getEncode(file: File): String {
        val tempByte = getFileBytes(file)
        return getEncode(tempByte)
    }

    private fun getFileBytes(file: File): ByteArray {
        val tempByte = ByteArray(8000)
        try {
            FileInputStream(file).use {
                it.read(tempByte)
            }
        } catch (e: Exception) {
            System.err.println("Error: " + e)
        }
        return tempByte
    }
}
