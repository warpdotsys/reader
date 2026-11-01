// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.utils

object UTF8BOMFighter {

    private val UTF8_BOM_BYTES = byteArrayOf(0xEF.toByte(), 0xBB.toByte(), 0xBF.toByte())

    fun removeUTF8BOM(xmlText: String): String {
        val bytes = xmlText.toByteArray(Charsets.UTF_8)
        val containsBOM = bytes.size > 3 &&
            bytes[0] == UTF8_BOM_BYTES[0] &&
            bytes[1] == UTF8_BOM_BYTES[1] &&
            bytes[2] == UTF8_BOM_BYTES[2]
        return if (containsBOM) {
            String(bytes, 3, bytes.size - 3, Charsets.UTF_8)
        } else {
            xmlText
        }
    }

    fun removeUTF8BOM(bytes: ByteArray): ByteArray {
        val containsBOM = bytes.size > 3 &&
            bytes[0] == UTF8_BOM_BYTES[0] &&
            bytes[1] == UTF8_BOM_BYTES[1] &&
            bytes[2] == UTF8_BOM_BYTES[2]
        return if (containsBOM) {
            val copy = ByteArray(bytes.size - 3)
            System.arraycopy(bytes, 3, copy, 0, bytes.size - 3)
            copy
        } else {
            bytes
        }
    }
}
