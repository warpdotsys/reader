// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.utils

import org.jsoup.internal.StringUtil
import org.jsoup.nodes.CDataNode
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode
import org.jsoup.select.NodeTraversor
import org.jsoup.select.NodeVisitor

fun Element.textArray(): Array<String> {
    val sb = StringUtil.borrowBuilder()
    NodeTraversor.traverse(object : NodeVisitor {
        override fun head(node: Node, depth: Int) {
            if (node is TextNode) {
                appendNormalisedText(sb, node)
            } else if (node is Element) {
                if (sb.isNotEmpty() && (node.isBlock || node.tag().name == "br")) {
                    if (!lastCharIsWhitespace(sb)) {
                        sb.append("\n")
                    }
                }
            }
        }

        override fun tail(node: Node, depth: Int) {
            if (node is Element && node.isBlock && node.nextSibling() is TextNode) {
                if (!lastCharIsWhitespace(sb)) {
                    sb.append("\n")
                }
            }
        }
    }, this)
    return StringUtil.releaseBuilder(sb).trim().splitNotBlank("\n")
}

private fun appendNormalisedText(sb: StringBuilder, textNode: TextNode) {
    val text = textNode.wholeText
    if (preserveWhitespace(textNode.parentNode()) || textNode is CDataNode) {
        sb.append(text)
    } else {
        StringUtil.appendNormalisedWhitespace(sb, text, lastCharIsWhitespace(sb))
    }
}

private fun preserveWhitespace(node: Node?): Boolean {
    var el: Element? = node as? Element
    var i = 0
    while (i < 6) {
        val current = el ?: return false
        if (current.tag().preserveWhitespace()) {
            return true
        }
        el = current.parent()
        i++
    }
    return false
}

private fun lastCharIsWhitespace(sb: StringBuilder): Boolean {
    return sb.isNotEmpty() && sb[sb.length - 1] == ' '
}
