// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.model.rss

import io.legado.app.data.entities.RssArticle
import io.legado.app.model.DebugLog
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.StringReader
import java.util.regex.Pattern

object RssParserDefault {

    private const val RSS_ITEM = "item"
    private const val RSS_ITEM_TITLE = "title"
    private const val RSS_ITEM_LINK = "link"
    private const val RSS_ITEM_CATEGORY = "category"
    private const val RSS_ITEM_THUMBNAIL = "media:thumbnail"
    private const val RSS_ITEM_ENCLOSURE = "enclosure"
    private const val RSS_ITEM_DESCRIPTION = "description"
    private const val RSS_ITEM_CONTENT = "content:encoded"
    private const val RSS_ITEM_PUB_DATE = "pubDate"
    private const val RSS_ITEM_TIME = "time"
    private const val RSS_ITEM_URL = "url"
    private const val RSS_ITEM_TYPE = "type"

    @Throws(XmlPullParserException::class, IOException::class)
    fun parseXML(
        sortName: String,
        xml: String,
        sourceUrl: String,
        debugLog: DebugLog?
    ): Pair<List<RssArticle>, String> {
        val articleList = ArrayList<RssArticle>()
        var currentArticle = RssArticle()
        val factory = XmlPullParserFactory.newInstance(
            """
        org.kxml2.io.KXmlParser
        org.kxml2.io.KXmlSerializer
               """,
            Thread.currentThread().contextClassLoader.javaClass
        )
        factory.isNamespaceAware = false
        val xmlPullParser = factory.newPullParser()
        xmlPullParser.setInput(StringReader(xml))
        var insideItem = false
        var eventType = xmlPullParser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                if (xmlPullParser.name.equals(RSS_ITEM, ignoreCase = true)) {
                    insideItem = true
                } else if (xmlPullParser.name.equals(RSS_ITEM_TITLE, ignoreCase = true)) {
                    if (insideItem) {
                        currentArticle.title = xmlPullParser.nextText().trim()
                    }
                } else if (xmlPullParser.name.equals(RSS_ITEM_LINK, ignoreCase = true)) {
                    if (insideItem) {
                        currentArticle.link = xmlPullParser.nextText().trim()
                    }
                } else if (xmlPullParser.name.equals(RSS_ITEM_THUMBNAIL, ignoreCase = true)) {
                    if (insideItem) {
                        currentArticle.image = xmlPullParser.getAttributeValue(null, RSS_ITEM_URL)
                    }
                } else if (xmlPullParser.name.equals(RSS_ITEM_ENCLOSURE, ignoreCase = true)) {
                    if (insideItem) {
                        val type = xmlPullParser.getAttributeValue(null, RSS_ITEM_TYPE)
                        if (type != null && type.contains("image/")) {
                            currentArticle.image =
                                xmlPullParser.getAttributeValue(null, RSS_ITEM_URL)
                        }
                    }
                } else if (xmlPullParser.name.equals(RSS_ITEM_DESCRIPTION, ignoreCase = true)) {
                    if (insideItem) {
                        val description = xmlPullParser.nextText()
                        currentArticle.description = description.trim()
                        if (currentArticle.image == null) {
                            currentArticle.image = getImageUrl(description)
                        }
                    }
                } else if (xmlPullParser.name.equals(RSS_ITEM_CONTENT, ignoreCase = true)) {
                    if (insideItem) {
                        val content = xmlPullParser.nextText().trim()
                        currentArticle.content = content
                        if (currentArticle.image == null) {
                            currentArticle.image = getImageUrl(content)
                        }
                    }
                } else if (xmlPullParser.name.equals(RSS_ITEM_PUB_DATE, ignoreCase = true)) {
                    if (insideItem) {
                        if (xmlPullParser.next() == XmlPullParser.TEXT) {
                            currentArticle.pubDate = xmlPullParser.text.trim()
                        }
                    }
                } else if (xmlPullParser.name.equals(RSS_ITEM_TIME, ignoreCase = true) &&
                    insideItem
                ) {
                    currentArticle.pubDate = xmlPullParser.nextText()
                }
            } else if (eventType == XmlPullParser.END_TAG &&
                xmlPullParser.name.equals(RSS_ITEM, ignoreCase = true)
            ) {
                insideItem = false
                currentArticle.origin = sourceUrl
                currentArticle.sort = sortName
                articleList.add(currentArticle)
                currentArticle = RssArticle()
            }
            eventType = xmlPullParser.next()
        }
        articleList.firstOrNull()?.let {
            debugLog?.log(sourceUrl, "┌获取标题")
            debugLog?.log(sourceUrl, "└${it.title}")
            debugLog?.log(sourceUrl, "┌获取时间")
            debugLog?.log(sourceUrl, "└${it.pubDate}")
            debugLog?.log(sourceUrl, "┌获取描述")
            debugLog?.log(sourceUrl, "└${it.description}")
            debugLog?.log(sourceUrl, "┌获取图片url")
            debugLog?.log(sourceUrl, "└${it.image}")
            debugLog?.log(sourceUrl, "┌获取文章链接")
            debugLog?.log(sourceUrl, "└${it.link}")
        }
        return Pair(articleList, "")
    }

    private fun getImageUrl(input: String): String? {
        var url2: String? = null
        val patternImg = Pattern.compile("(<img [^>]*>)")
        val matcherImg = patternImg.matcher(input)
        if (matcherImg.find()) {
            val imgTag = matcherImg.group(1)
            val patternLink = Pattern.compile("src\\s*=\\s*\"([^\"]+)\"")
            val matcherLink = patternLink.matcher(imgTag)
            if (matcherLink.find()) {
                url2 = matcherLink.group(1).trim()
            }
        }
        return url2
    }
}
