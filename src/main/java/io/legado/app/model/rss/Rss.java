/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.model.rss;

import io.legado.app.data.entities.BaseSource;
import io.legado.app.data.entities.RssArticle;
import io.legado.app.data.entities.RssSource;
import io.legado.app.help.http.StrResponse;
import io.legado.app.model.DebugLog;
import io.legado.app.model.analyzeRule.AnalyzeRule;
import io.legado.app.model.analyzeRule.AnalyzeUrl;
import io.legado.app.model.analyzeRule.RuleData;
import io.legado.app.model.rss.Rss;
import io.legado.app.model.rss.RssParserByRule;
import io.legado.app.utils.NetworkUtils;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002JO\u0010\u0003\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00042\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J3\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0015"}, d2={"Lio/legado/app/model/rss/Rss;", "", "()V", "getArticles", "Lkotlin/Pair;", "", "Lio/legado/app/data/entities/RssArticle;", "", "sortName", "sortUrl", "rssSource", "Lio/legado/app/data/entities/RssSource;", "page", "", "debugLog", "Lio/legado/app/model/DebugLog;", "(Ljava/lang/String;Ljava/lang/String;Lio/legado/app/data/entities/RssSource;ILio/legado/app/model/DebugLog;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getContent", "rssArticle", "ruleContent", "(Lio/legado/app/data/entities/RssArticle;Ljava/lang/String;Lio/legado/app/data/entities/RssSource;Lio/legado/app/model/DebugLog;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "reader-pro"})
public final class Rss {
    @NotNull
    public static final Rss INSTANCE = new Rss();

    private Rss() {
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getArticles(@NotNull String var1_1, @NotNull String var2_2, @NotNull RssSource var3_3, int var4_4, @Nullable DebugLog var5_5, @NotNull Continuation<? super Pair<? extends List<RssArticle>, String>> var6_6) {
        if (!(var6_6 instanceof getArticles.1)) ** GOTO lbl-1000
        var11_7 = var6_6;
        if ((var11_7.label & -2147483648) != 0) {
            var11_7.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var6_6){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                /* synthetic */ Object result;
                final /* synthetic */ Rss this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.getArticles(null, null, null, 0, null, this);
                }
            };
        }
        $result = $continuation.result;
        var12_9 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                ruleData = new RuleData();
                analyzeUrl = new AnalyzeUrl(sortUrl, null, Boxing.boxInt((int)page), null, null, null, rssSource, ruleData, null, BaseSource.DefaultImpls.getHeaderMap$default(rssSource, false, 1, null), debugLog, 314, null);
                $continuation.L$0 = sortName;
                $continuation.L$1 = sortUrl;
                $continuation.L$2 = rssSource;
                $continuation.L$3 = debugLog;
                $continuation.L$4 = ruleData;
                $continuation.label = 1;
                v0 = AnalyzeUrl.getStrResponseAwait$default(analyzeUrl, null, null, false, $continuation, 7, null);
                if (v0 == var12_9) {
                    return var12_9;
                }
                ** GOTO lbl32
            }
            case 1: {
                ruleData = (RuleData)$continuation.L$4;
                debugLog = (DebugLog)$continuation.L$3;
                rssSource = (RssSource)$continuation.L$2;
                sortUrl = (String)$continuation.L$1;
                sortName = (String)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl32:
                // 2 sources

                body = ((StrResponse)v0).getBody();
                return RssParserByRule.INSTANCE.parseXML(sortName, sortUrl, body, rssSource, ruleData, debugLog);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getContent(@NotNull RssArticle var1_1, @NotNull String var2_2, @NotNull RssSource var3_3, @Nullable DebugLog var4_4, @NotNull Continuation<? super String> var5_5) {
        if (!(var5_5 instanceof getContent.1)) ** GOTO lbl-1000
        var10_6 = var5_5;
        if ((var10_6.label & -2147483648) != 0) {
            var10_6.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var5_5){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                /* synthetic */ Object result;
                final /* synthetic */ Rss this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.getContent(null, null, null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var11_8 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                analyzeUrl = new AnalyzeUrl(rssArticle.getLink(), null, null, null, null, rssArticle.getOrigin(), rssSource, rssArticle, null, BaseSource.DefaultImpls.getHeaderMap$default(rssSource, false, 1, null), debugLog, 286, null);
                $continuation.L$0 = rssArticle;
                $continuation.L$1 = ruleContent;
                $continuation.L$2 = rssSource;
                $continuation.L$3 = debugLog;
                $continuation.label = 1;
                v0 = AnalyzeUrl.getStrResponseAwait$default(analyzeUrl, null, null, false, $continuation, 7, null);
                if (v0 == var11_8) {
                    return var11_8;
                }
                ** GOTO lbl29
            }
            case 1: {
                debugLog = (DebugLog)$continuation.L$3;
                rssSource = (RssSource)$continuation.L$2;
                ruleContent = (String)$continuation.L$1;
                rssArticle = (RssArticle)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl29:
                // 2 sources

                body = ((StrResponse)v0).getBody();
                analyzeRule = new AnalyzeRule(rssArticle, rssSource, debugLog);
                AnalyzeRule.setContent$default(analyzeRule, body, null, 2, null).setBaseUrl(NetworkUtils.INSTANCE.getAbsoluteURL(rssArticle.getOrigin(), rssArticle.getLink()));
                return AnalyzeRule.getString$default(analyzeRule, ruleContent, null, false, 6, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}

