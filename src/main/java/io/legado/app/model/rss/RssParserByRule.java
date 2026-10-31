/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.model.rss;

import io.legado.app.data.entities.RssArticle;
import io.legado.app.data.entities.RssSource;
import io.legado.app.exception.NoStackTraceException;
import io.legado.app.model.DebugLog;
import io.legado.app.model.analyzeRule.AnalyzeRule;
import io.legado.app.model.analyzeRule.RuleData;
import io.legado.app.model.rss.RssParserDefault;
import io.legado.app.utils.NetworkUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0098\u0001\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0010\u0010\r\u001a\f\u0012\b\u0012\u00060\u000fR\u00020\t0\u000e2\u0010\u0010\u0010\u001a\f\u0012\b\u0012\u00060\u000fR\u00020\t0\u000e2\u0010\u0010\u0011\u001a\f\u0012\b\u0012\u00060\u000fR\u00020\t0\u000e2\u0010\u0010\u0012\u001a\f\u0012\b\u0012\u00060\u000fR\u00020\t0\u000e2\u0010\u0010\u0013\u001a\f\u0012\b\u0012\u00060\u000fR\u00020\t0\u000e2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0002JN\u0010\u0016\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00172\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u00062\b\u0010\u001b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u00a8\u0006 "}, d2={"Lio/legado/app/model/rss/RssParserByRule;", "", "()V", "getItem", "Lio/legado/app/data/entities/RssArticle;", "sourceUrl", "", "item", "analyzeRule", "Lio/legado/app/model/analyzeRule/AnalyzeRule;", "variable", "log", "", "ruleTitle", "", "Lio/legado/app/model/analyzeRule/AnalyzeRule$SourceRule;", "rulePubDate", "ruleDescription", "ruleImage", "ruleLink", "debugLog", "Lio/legado/app/model/DebugLog;", "parseXML", "Lkotlin/Pair;", "", "sortName", "sortUrl", "body", "rssSource", "Lio/legado/app/data/entities/RssSource;", "ruleData", "Lio/legado/app/model/analyzeRule/RuleData;", "reader-pro"})
public final class RssParserByRule {
    @NotNull
    public static final RssParserByRule INSTANCE = new RssParserByRule();

    private RssParserByRule() {
    }

    @NotNull
    public final Pair<List<RssArticle>, String> parseXML(@NotNull String sortName, @NotNull String sortUrl, @Nullable String body, @NotNull RssSource rssSource, @NotNull RuleData ruleData, @Nullable DebugLog debugLog) throws Exception {
        boolean bl;
        Object object;
        Intrinsics.checkNotNullParameter(sortName, "sortName");
        Intrinsics.checkNotNullParameter(sortUrl, "sortUrl");
        Intrinsics.checkNotNullParameter(rssSource, "rssSource");
        Intrinsics.checkNotNullParameter(ruleData, "ruleData");
        String sourceUrl = rssSource.getSourceUrl();
        String nextUrl = null;
        CharSequence charSequence = body;
        boolean bl2 = false;
        boolean bl3 = false;
        if (charSequence == null || StringsKt.isBlank(charSequence)) {
            throw new NoStackTraceException(Intrinsics.stringPlus("error_get_web_content: ", rssSource.getSourceUrl()));
        }
        String ruleArticles = rssSource.getRuleArticles();
        Object object2 = ruleArticles;
        bl3 = false;
        boolean bl4 = false;
        if (object2 == null || StringsKt.isBlank((CharSequence)object2)) {
            object2 = debugLog;
            if (object2 != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)object2, sourceUrl, "\u21d2\u5217\u8868\u89c4\u5219\u4e3a\u7a7a, \u4f7f\u7528\u9ed8\u8ba4\u89c4\u5219\u89e3\u6790", false, 4, null);
            }
            return RssParserDefault.INSTANCE.parseXML(sortName, body, sourceUrl, debugLog);
        }
        bl3 = false;
        List articleList = new ArrayList();
        AnalyzeRule analyzeRule = new AnalyzeRule(ruleData, rssSource, debugLog);
        AnalyzeRule.setContent$default(analyzeRule, body, null, 2, null).setBaseUrl(sortUrl);
        analyzeRule.setRedirectUrl(sortUrl);
        boolean reverse = false;
        if (StringsKt.startsWith$default(ruleArticles, "-", false, 2, null)) {
            reverse = true;
            object = ruleArticles;
            int n = 1;
            bl = false;
            Object object3 = object;
            if (object3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string = ((String)object3).substring(n);
            Intrinsics.checkNotNullExpressionValue(string, "(this as java.lang.String).substring(startIndex)");
            ruleArticles = string;
        }
        if ((object = debugLog) != null) {
            DebugLog.DefaultImpls.log$default((DebugLog)object, sourceUrl, "\u250c\u83b7\u53d6\u5217\u8868", false, 4, null);
        }
        List<Object> collections = analyzeRule.getElements(ruleArticles);
        Object object4 = debugLog;
        if (object4 != null) {
            DebugLog.DefaultImpls.log$default((DebugLog)object4, sourceUrl, Intrinsics.stringPlus("\u2514\u5217\u8868\u5927\u5c0f:", collections.size()), false, 4, null);
        }
        object4 = rssSource.getRuleNextPage();
        bl = false;
        boolean bl5 = false;
        if (!(object4 == null || object4.length() == 0)) {
            object4 = debugLog;
            if (object4 != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)object4, sourceUrl, "\u250c\u83b7\u53d6\u4e0b\u4e00\u9875\u94fe\u63a5", false, 4, null);
            }
            String string = rssSource.getRuleNextPage();
            Intrinsics.checkNotNull(string);
            object4 = string;
            Locale locale = Locale.getDefault();
            Intrinsics.checkNotNullExpressionValue(locale, "getDefault()");
            bl5 = false;
            Object object5 = object4;
            if (object5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string2 = ((String)object5).toUpperCase(locale);
            Intrinsics.checkNotNullExpressionValue(string2, "(this as java.lang.String).toUpperCase(locale)");
            if (Intrinsics.areEqual(string2, "PAGE")) {
                nextUrl = sortUrl;
            } else {
                nextUrl = AnalyzeRule.getString$default(analyzeRule, rssSource.getRuleNextPage(), null, false, 6, null);
                object4 = nextUrl;
                boolean bl6 = false;
                if (object4.length() > 0) {
                    nextUrl = NetworkUtils.INSTANCE.getAbsoluteURL(sortUrl, nextUrl);
                }
            }
            object4 = debugLog;
            if (object4 != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)object4, sourceUrl, Intrinsics.stringPlus("\u2514", nextUrl), false, 4, null);
            }
        }
        List ruleTitle = AnalyzeRule.splitSourceRule$default(analyzeRule, rssSource.getRuleTitle(), false, 2, null);
        List rulePubDate = AnalyzeRule.splitSourceRule$default(analyzeRule, rssSource.getRulePubDate(), false, 2, null);
        List ruleDescription = AnalyzeRule.splitSourceRule$default(analyzeRule, rssSource.getRuleDescription(), false, 2, null);
        List ruleImage = AnalyzeRule.splitSourceRule$default(analyzeRule, rssSource.getRuleImage(), false, 2, null);
        List ruleLink = AnalyzeRule.splitSourceRule$default(analyzeRule, rssSource.getRuleLink(), false, 2, null);
        String variable = ruleData.getVariable();
        Iterator<Object> iterator2 = collections.iterator();
        int n = 0;
        while (iterator2.hasNext()) {
            int index = n++;
            Object item = iterator2.next();
            RssArticle rssArticle = this.getItem(sourceUrl, item, analyzeRule, variable, index == 0, ruleTitle, rulePubDate, ruleDescription, ruleImage, ruleLink, debugLog);
            if (rssArticle == null) continue;
            RssArticle rssArticle2 = rssArticle;
            boolean bl7 = false;
            boolean bl8 = false;
            RssArticle it = rssArticle2;
            boolean bl9 = false;
            it.setSort(sortName);
            it.setOrigin(sourceUrl);
            articleList.add(it);
        }
        if (reverse) {
            CollectionsKt.reverse(articleList);
        }
        return new Pair<List<RssArticle>, String>(articleList, nextUrl);
    }

    private final RssArticle getItem(String sourceUrl, Object item, AnalyzeRule analyzeRule, String variable, boolean log, List<AnalyzeRule.SourceRule> ruleTitle, List<AnalyzeRule.SourceRule> rulePubDate, List<AnalyzeRule.SourceRule> ruleDescription, List<AnalyzeRule.SourceRule> ruleImage, List<AnalyzeRule.SourceRule> ruleLink, DebugLog debugLog) {
        RssArticle rssArticle = new RssArticle(null, null, null, 0L, null, null, null, null, null, false, variable, 1023, null);
        analyzeRule.setRuleData(rssArticle);
        AnalyzeRule.setContent$default(analyzeRule, item, null, 2, null);
        Object object = debugLog;
        if (object != null) {
            object.log(sourceUrl, "\u250c\u83b7\u53d6\u6807\u9898", log);
        }
        rssArticle.setTitle(AnalyzeRule.getString$default(analyzeRule, ruleTitle, null, false, 6, null));
        object = debugLog;
        if (object != null) {
            object.log(sourceUrl, Intrinsics.stringPlus("\u2514", rssArticle.getTitle()), log);
        }
        object = debugLog;
        if (object != null) {
            object.log(sourceUrl, "\u250c\u83b7\u53d6\u65f6\u95f4", log);
        }
        rssArticle.setPubDate(AnalyzeRule.getString$default(analyzeRule, rulePubDate, null, false, 6, null));
        object = debugLog;
        if (object != null) {
            object.log(sourceUrl, Intrinsics.stringPlus("\u2514", rssArticle.getPubDate()), log);
        }
        object = debugLog;
        if (object != null) {
            object.log(sourceUrl, "\u250c\u83b7\u53d6\u63cf\u8ff0", log);
        }
        object = ruleDescription;
        boolean bl = false;
        boolean bl2 = false;
        if (object == null || object.isEmpty()) {
            rssArticle.setDescription(null);
            object = debugLog;
            if (object != null) {
                object.log(sourceUrl, "\u2514\u63cf\u8ff0\u89c4\u5219\u4e3a\u7a7a\uff0c\u5c06\u4f1a\u89e3\u6790\u5185\u5bb9\u9875", log);
            }
        } else {
            rssArticle.setDescription(AnalyzeRule.getString$default(analyzeRule, ruleDescription, null, false, 6, null));
            object = debugLog;
            if (object != null) {
                object.log(sourceUrl, Intrinsics.stringPlus("\u2514", rssArticle.getDescription()), log);
            }
        }
        object = debugLog;
        if (object != null) {
            object.log(sourceUrl, "\u250c\u83b7\u53d6\u56fe\u7247url", log);
        }
        rssArticle.setImage(AnalyzeRule.getString$default(analyzeRule, ruleImage, null, true, 2, null));
        object = debugLog;
        if (object != null) {
            object.log(sourceUrl, Intrinsics.stringPlus("\u2514", rssArticle.getImage()), log);
        }
        object = debugLog;
        if (object != null) {
            object.log(sourceUrl, "\u250c\u83b7\u53d6\u6587\u7ae0\u94fe\u63a5", log);
        }
        rssArticle.setLink(NetworkUtils.INSTANCE.getAbsoluteURL(sourceUrl, AnalyzeRule.getString$default(analyzeRule, ruleLink, null, false, 6, null)));
        object = debugLog;
        if (object != null) {
            object.log(sourceUrl, Intrinsics.stringPlus("\u2514", rssArticle.getLink()), log);
        }
        if (StringsKt.isBlank(rssArticle.getTitle())) {
            return null;
        }
        return rssArticle;
    }
}

