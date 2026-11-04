// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.model.rss

import io.legado.app.data.entities.RssArticle
import io.legado.app.data.entities.RssSource
import io.legado.app.model.DebugLog
import io.legado.app.model.analyzeRule.AnalyzeRule
import io.legado.app.model.analyzeRule.AnalyzeUrl
import io.legado.app.model.analyzeRule.RuleData
import io.legado.app.utils.NetworkUtils

object Rss {

    suspend fun getArticles(
        sortName: String,
        sortUrl: String,
        rssSource: RssSource,
        page: Int,
        debugLog: DebugLog?
    ): Pair<List<RssArticle>, String> {
        val ruleData = RuleData()
        val analyzeUrl = AnalyzeUrl(
            mUrl = sortUrl,
            page = page,
            source = rssSource,
            ruleData = ruleData,
            headerMapF = rssSource.getHeaderMap(),
            debugLog = debugLog
        )
        val res = analyzeUrl.getStrResponseAwait()
        return RssParserByRule.parseXML(
            sortName, sortUrl, res.body,
            rssSource, ruleData, debugLog
        )
    }

    suspend fun getContent(
        rssArticle: RssArticle,
        ruleContent: String,
        rssSource: RssSource,
        debugLog: DebugLog?
    ): String {
        val analyzeUrl = AnalyzeUrl(
            rssArticle.link,
            baseUrl = rssArticle.origin,
            source = rssSource,
            ruleData = rssArticle,
            headerMapF = rssSource.getHeaderMap(),
            debugLog = debugLog
        )
        val res = analyzeUrl.getStrResponseAwait()
        val analyzeRule = AnalyzeRule(rssArticle, rssSource, debugLog)
        analyzeRule.setContent(res.body).setBaseUrl(
            NetworkUtils.getAbsoluteURL(rssArticle.origin, rssArticle.link)
        )
        return analyzeRule.getString(ruleContent)
    }
}
