// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.utils

import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.InputStream
import java.util.LinkedHashMap
import javax.xml.parsers.DocumentBuilderFactory
import org.xml.sax.InputSource

object XmlUtils {

    fun xml2map(source: Any): Map<String, Any> {
        val factory = DocumentBuilderFactory.newInstance()
        val doc: MutableMap<String, Any> = LinkedHashMap()
        try {
            val builder = factory.newDocumentBuilder()
            if (source is String) {
                return parseNode(builder.parse(source).childNodes)
            }
            if (source is InputStream) {
                return parseNode(builder.parse(source).childNodes)
            }
            if (source is InputSource) {
                return parseNode(builder.parse(source).childNodes)
            }
            return doc
        } catch (e: Exception) {
            e.printStackTrace()
            return doc
        }
    }

    fun parseNode(list: NodeList): Map<String, Any> {
        val doc: MutableMap<String, Any> = LinkedHashMap()
        var n = 0
        val size = list.length
        while (n < size) {
            val node = list.item(n++)
            if (node.nodeType != Node.ELEMENT_NODE) continue
            val childNodes = node.childNodes
            if (childNodes.length == 1 && node.firstChild.nodeType == Node.TEXT_NODE) {
                doc[node.nodeName] = node.firstChild.nodeValue
            } else if (childNodes.length > 1) {
                doc[node.nodeName] = parseNode(childNodes)
            }
        }
        return doc
    }
}
