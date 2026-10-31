/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.model.webBook;

import io.legado.app.data.entities.BaseSource;
import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookChapter;
import io.legado.app.data.entities.BookSource;
import io.legado.app.data.entities.rule.ContentRule;
import io.legado.app.help.http.StrResponse;
import io.legado.app.model.DebugLog;
import io.legado.app.model.analyzeRule.AnalyzeRule;
import io.legado.app.model.analyzeRule.AnalyzeUrl;
import io.legado.app.model.analyzeRule.RuleDataInterface;
import io.legado.app.model.webBook.BookContent;
import io.legado.app.utils.HtmlFormatter;
import io.legado.app.utils.NetworkUtils;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.JobKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002Jr\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00060\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002J[\u0010\u0003\u001a\u00020\u00052\b\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00052\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0019"}, d2={"Lio/legado/app/model/webBook/BookContent;", "", "()V", "analyzeContent", "Lkotlin/Pair;", "", "", "book", "Lio/legado/app/data/entities/Book;", "baseUrl", "redirectUrl", "body", "contentRule", "Lio/legado/app/data/entities/rule/ContentRule;", "chapter", "Lio/legado/app/data/entities/BookChapter;", "bookSource", "Lio/legado/app/data/entities/BookSource;", "nextChapterUrl", "printLog", "", "debugLog", "Lio/legado/app/model/DebugLog;", "bookChapter", "(Ljava/lang/String;Lio/legado/app/data/entities/Book;Lio/legado/app/data/entities/BookChapter;Lio/legado/app/data/entities/BookSource;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lio/legado/app/model/DebugLog;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "reader-pro"})
public final class BookContent {
    @NotNull
    public static final BookContent INSTANCE = new BookContent();

    private BookContent() {
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object analyzeContent(@Nullable String var1_1, @NotNull Book var2_2, @NotNull BookChapter var3_3, @NotNull BookSource var4_4, @NotNull String var5_5, @NotNull String var6_6, @Nullable String var7_7, @Nullable DebugLog var8_8, @NotNull Continuation<? super String> var9_9) {
        block25: {
            if (!(var9_9 instanceof analyzeContent.1)) ** GOTO lbl-1000
            var27_10 = var9_9;
            if ((var27_10.label & -2147483648) != 0) {
                var27_10.label -= -2147483648;
            } else lbl-1000:
            // 2 sources

            {
                $continuation = new ContinuationImpl(this, var9_9){
                    Object L$0;
                    Object L$1;
                    Object L$2;
                    Object L$3;
                    Object L$4;
                    Object L$5;
                    Object L$6;
                    Object L$7;
                    Object L$8;
                    Object L$9;
                    Object L$10;
                    Object L$11;
                    /* synthetic */ Object result;
                    final /* synthetic */ BookContent this$0;
                    int label;
                    {
                        this.this$0 = this$0;
                        super($completion);
                    }

                    @Nullable
                    public final Object invokeSuspend(@NotNull Object $result) {
                        this.result = $result;
                        this.label |= Integer.MIN_VALUE;
                        return this.this$0.analyzeContent(null, null, null, null, null, null, null, null, this);
                    }
                };
            }
            $result = $continuation.result;
            var28_12 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            block0 : switch ($continuation.label) {
                case 0: {
                    ResultKt.throwOnFailure($result);
                    var10_13 = body;
                    if (var10_13 == null) {
                        throw new Exception("error_get_web_content");
                    }
                    var10_13 = debugLog;
                    if (var10_13 != null) {
                        DebugLog.DefaultImpls.log$default((DebugLog)var10_13, bookSource.getBookSourceUrl(), Intrinsics.stringPlus("\u2261\u83b7\u53d6\u6210\u529f:", baseUrl), false, 4, null);
                    }
                    var11_14 = (CharSequence)nextChapterUrl;
                    var12_15 = false;
                    var13_17 = false;
                    mNextChapterUrl = (var11_14 == null || var11_14.length() == 0) == false ? nextChapterUrl : (String)null;
                    content = new StringBuilder();
                    var13_18 = new String[]{redirectUrl};
                    nextUrlList = CollectionsKt.arrayListOf(var13_18);
                    contentRule = bookSource.getContentRule();
                    analyzeRule = new AnalyzeRule((RuleDataInterface)book, (BaseSource)bookSource, (DebugLog)debugLog).setContent(body, (String)baseUrl);
                    analyzeRule.setRedirectUrl((String)redirectUrl);
                    analyzeRule.setChapter((BookChapter)bookChapter);
                    analyzeRule.setNextChapterUrl(mNextChapterUrl);
                    JobKt.ensureActive($continuation.getContext());
                    contentData = new Ref.ObjectRef<T>();
                    contentData.element = BookContent.analyzeContent$default(this, (Book)book, (String)baseUrl, (String)redirectUrl, (String)body, contentRule, (BookChapter)bookChapter, (BookSource)bookSource, mNextChapterUrl, false, (DebugLog)debugLog, 256, null);
                    content.append((String)((Pair)contentData.element).getFirst());
                    if (((List)((Pair)contentData.element).getSecond()).size() != 1) break block25;
                    nextUrl = new Ref.ObjectRef<T>();
                    nextUrl.element = ((List)((Pair)contentData.element).getSecond()).get(0);
lbl39:
                    // 3 sources

                    while (true) {
                        var17_22 = (CharSequence)nextUrl.element;
                        var18_24 = false;
                        if (!(var17_22.length() > 0) || nextUrlList.contains(nextUrl.element)) break block0;
                        var17_22 = mNextChapterUrl;
                        var18_24 = false;
                        var19_26 = false;
                        if (!(var17_22 == null || var17_22.length() == 0) && Intrinsics.areEqual(NetworkUtils.INSTANCE.getAbsoluteURL((String)redirectUrl, (String)nextUrl.element), NetworkUtils.INSTANCE.getAbsoluteURL((String)redirectUrl, mNextChapterUrl))) break block0;
                        nextUrlList.add((String)nextUrl.element);
                        JobKt.ensureActive($continuation.getContext());
                        $continuation.L$0 = book;
                        $continuation.L$1 = bookChapter;
                        $continuation.L$2 = bookSource;
                        $continuation.L$3 = redirectUrl;
                        $continuation.L$4 = debugLog;
                        $continuation.L$5 = mNextChapterUrl;
                        $continuation.L$6 = content;
                        $continuation.L$7 = nextUrlList;
                        $continuation.L$8 = contentRule;
                        $continuation.L$9 = analyzeRule;
                        $continuation.L$10 = contentData;
                        $continuation.L$11 = nextUrl;
                        $continuation.label = 1;
                        v0 = AnalyzeUrl.getStrResponseAwait$default(new AnalyzeUrl((String)nextUrl.element, null, null, null, null, null, (BaseSource)bookSource, (RuleDataInterface)book, null, BaseSource.DefaultImpls.getHeaderMap$default((BaseSource)bookSource, false, 1, null), (DebugLog)debugLog, 318, null), null, null, false, $continuation, 7, null);
                        if (v0 == var28_12) {
                            return var28_12;
                        }
                        ** GOTO lbl82
                        break;
                    }
                }
                case 1: {
                    nextUrl = (Ref.ObjectRef<T>)$continuation.L$11;
                    var15_20 = (Ref.ObjectRef)$continuation.L$10;
                    var14_19 = (AnalyzeRule)$continuation.L$9;
                    var13_18 = (ContentRule)$continuation.L$8;
                    var12_16 = (ArrayList)$continuation.L$7;
                    var11_14 = (StringBuilder)$continuation.L$6;
                    var10_13 = (String)$continuation.L$5;
                    var8_8 = (DebugLog)$continuation.L$4;
                    var6_6 = (String)$continuation.L$3;
                    var4_4 = (BookSource)$continuation.L$2;
                    var3_3 = (BookChapter)$continuation.L$1;
                    var2_2 = (Book)$continuation.L$0;
                    ResultKt.throwOnFailure($result);
                    v0 = $result;
lbl82:
                    // 2 sources

                    if ((var18_23 = (res = (StrResponse)v0).getBody()) == null) ** GOTO lbl39
                    var19_27 = var18_23;
                    var20_29 = 0;
                    var21_30 = 0;
                    nextBody = var19_27;
                    $i$a$-let-BookContent$analyzeContent$2 = false;
                    var15_20.element = BookContent.INSTANCE.analyzeContent(var2_2, (String)nextUrl.element, res.getUrl(), nextBody, (ContentRule)var13_18, var3_3, var4_4, var10_13, false, var8_8);
                    var24_34 = (Collection)((Pair)var15_20.element).getSecond();
                    var25_35 = false;
                    nextUrl.element = var24_34.isEmpty() == false != false ? (String)((List)((Pair)var15_20.element).getSecond()).get(0) : "";
                    var11_14.append("\n").append((String)((Pair)var15_20.element).getFirst());
                    ** continue;
                }
            }
            if ((res = var8_8) != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)res, var4_4.getBookSourceUrl(), Intrinsics.stringPlus("\u25c7\u672c\u7ae0\u603b\u9875\u6570:", Boxing.boxInt(var12_16.size())), false, 4, null);
            }
            ** GOTO lbl125
        }
        if (((List)((Pair)var15_20.element).getSecond()).size() > 1) {
            JobKt.ensureActive($continuation.getContext());
            nextUrl = var8_8;
            if (nextUrl != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)nextUrl, var4_4.getBookSourceUrl(), Intrinsics.stringPlus("\u25c7\u5e76\u53d1\u89e3\u6790\u6b63\u6587,\u603b\u9875\u6570:", Boxing.boxInt(((List)((Pair)var15_20.element).getSecond()).size())), false, 4, null);
            }
            $continuation.L$0 = var3_3;
            $continuation.L$1 = var4_4;
            $continuation.L$2 = var8_8;
            $continuation.L$3 = var11_14;
            $continuation.L$4 = var13_18;
            $continuation.L$5 = var14_19;
            $continuation.label = 2;
            v1 = BuildersKt.withContext(Dispatchers.getIO(), (Function2)new Function2<CoroutineScope, Continuation<? super Unit>, Object>((Ref.ObjectRef<Pair<String, List<String>>>)var15_20, var4_4, var2_2, var8_8, (ContentRule)var13_18, var3_3, var10_13, (StringBuilder)var11_14, null){
                Object L$1;
                Object L$2;
                Object L$3;
                int I$0;
                int I$1;
                int label;
                private /* synthetic */ Object L$0;
                final /* synthetic */ Ref.ObjectRef<Pair<String, List<String>>> $contentData;
                final /* synthetic */ BookSource $bookSource;
                final /* synthetic */ Book $book;
                final /* synthetic */ DebugLog $debugLog;
                final /* synthetic */ ContentRule $contentRule;
                final /* synthetic */ BookChapter $bookChapter;
                final /* synthetic */ String $mNextChapterUrl;
                final /* synthetic */ StringBuilder $content;
                {
                    this.$contentData = $contentData;
                    this.$bookSource = $bookSource;
                    this.$book = $book;
                    this.$debugLog = $debugLog;
                    this.$contentRule = $contentRule;
                    this.$bookChapter = $bookChapter;
                    this.$mNextChapterUrl = $mNextChapterUrl;
                    this.$content = $content;
                    super(2, $completion);
                }

                /*
                 * Unable to fully structure code
                 */
                @Nullable
                public final Object invokeSuspend(@NotNull Object var1_1) {
                    var15_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    switch (this.label) {
                        case 0: {
                            ResultKt.throwOnFailure(var1_1);
                            $this$withContext = (CoroutineScope)this.L$0;
                            var4_4 = 0;
                            var5_6 = ((List)((Pair)this.$contentData.element).getSecond()).size();
                            var6_8 = new Deferred[var5_6];
                            while (var4_4 < var5_6) {
                                var7_10 = var4_4++;
                                var6_8[var7_10] = BuildersKt.async$default($this$withContext, Dispatchers.getIO(), null, new Function2<CoroutineScope, Continuation<? super String>, Object>(this.$contentData, var7_10, this.$bookSource, this.$book, this.$debugLog, this.$contentRule, this.$bookChapter, this.$mNextChapterUrl, null){
                                    Object L$0;
                                    int label;
                                    final /* synthetic */ Ref.ObjectRef<Pair<String, List<String>>> $contentData;
                                    final /* synthetic */ int $tmp;
                                    final /* synthetic */ BookSource $bookSource;
                                    final /* synthetic */ Book $book;
                                    final /* synthetic */ DebugLog $debugLog;
                                    final /* synthetic */ ContentRule $contentRule;
                                    final /* synthetic */ BookChapter $bookChapter;
                                    final /* synthetic */ String $mNextChapterUrl;
                                    {
                                        this.$contentData = $contentData;
                                        this.$tmp = $tmp;
                                        this.$bookSource = $bookSource;
                                        this.$book = $book;
                                        this.$debugLog = $debugLog;
                                        this.$contentRule = $contentRule;
                                        this.$bookChapter = $bookChapter;
                                        this.$mNextChapterUrl = $mNextChapterUrl;
                                        super(2, $completion);
                                    }

                                    /*
                                     * Unable to fully structure code
                                     */
                                    @Nullable
                                    public final Object invokeSuspend(@NotNull Object var1_1) {
                                        var5_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                        switch (this.label) {
                                            case 0: {
                                                ResultKt.throwOnFailure(var1_1);
                                                urlStr = (String)((List)((Pair)this.$contentData.element).getSecond()).get(this.$tmp);
                                                analyzeUrl = new AnalyzeUrl(urlStr, null, null, null, null, null, this.$bookSource, this.$book, null, BaseSource.DefaultImpls.getHeaderMap$default(this.$bookSource, false, 1, null), this.$debugLog, 318, null);
                                                this.L$0 = urlStr;
                                                this.label = 1;
                                                v0 = AnalyzeUrl.getStrResponseAwait$default(analyzeUrl, null, null, false, this, 7, null);
                                                if (v0 == var5_2) {
                                                    return var5_2;
                                                }
                                                ** GOTO lbl17
                                            }
                                            case 1: {
                                                urlStr = (String)this.L$0;
                                                ResultKt.throwOnFailure($result);
                                                v0 = $result;
lbl17:
                                                // 2 sources

                                                res = (StrResponse)v0;
                                                v1 = res.getUrl();
                                                v2 = res.getBody();
                                                Intrinsics.checkNotNull(v2);
                                                return BookContent.access$analyzeContent(BookContent.INSTANCE, this.$book, urlStr, v1, v2, this.$contentRule, this.$bookChapter, this.$bookSource, this.$mNextChapterUrl, false, this.$debugLog).getFirst();
                                            }
                                        }
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }

                                    @NotNull
                                    public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                                        return (Continuation)((Object)new /* invalid duplicate definition of identical inner class */);
                                    }

                                    @Nullable
                                    public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super String> p2) {
                                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                                    }
                                }, 2, null);
                            }
                            var4_5 = asyncArray = var6_8;
                            var5_7 = this.$content;
                            $i$f$forEach = false;
                            var7_11 = $this$forEach$iv;
                            var8_13 = var7_11.length;
                            var9_14 = 0;
lbl19:
                            // 2 sources

                            while (var9_14 < var8_13) {
                                coroutine = element$iv = var7_11[var9_14];
                                $i$a$-forEach-BookContent$analyzeContent$3$1 = false;
                                JobKt.ensureActive($this$withContext.getCoroutineContext());
                                var13_18 = var5_7.append("\n");
                                this.L$0 = $this$withContext;
                                this.L$1 = var5_7;
                                this.L$2 = var7_11;
                                this.L$3 = var13_18;
                                this.I$0 = var8_13;
                                this.I$1 = var9_14;
                                this.label = 1;
                                v0 = coroutine.await(this);
                                if (v0 == var15_2) {
                                    return var15_2;
                                }
                                ** GOTO lbl47
                            }
                            break;
                        }
                        case 1: {
                            $i$f$forEach = false;
                            $i$a$-forEach-BookContent$analyzeContent$3$1 = false;
                            var9_14 = this.I$1;
                            var8_13 = this.I$0;
                            var13_18 = (StringBuilder)this.L$3;
                            var7_11 = (Deferred[])this.L$2;
                            var5_7 = (StringBuilder)this.L$1;
                            $this$withContext = (CoroutineScope)this.L$0;
                            ResultKt.throwOnFailure($result);
                            v0 = $result;
lbl47:
                            // 2 sources

                            var14_19 = v0;
                            var13_18.append((String)var14_19);
                            ++var9_14;
                            ** GOTO lbl19
                        }
                    }
                    return Unit.INSTANCE;
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }

                @NotNull
                public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                    Function2<CoroutineScope, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                    function2.L$0 = value;
                    return (Continuation)((Object)function2);
                }

                @Nullable
                public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Unit> p2) {
                    return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                }
            }, $continuation);
            if (v1 == var28_12) {
                return var28_12;
            }
        }
        ** GOTO lbl125
        {
            case 2: {
                var14_19 = (AnalyzeRule)$continuation.L$5;
                var13_18 = (String[])$continuation.L$4;
                var11_14 = (StringBuilder)$continuation.L$3;
                var8_8 = (DebugLog)$continuation.L$2;
                var4_4 = (BookSource)$continuation.L$1;
                var3_3 = (BookChapter)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v1 = $result;
lbl125:
                // 3 sources

                res = var11_14.toString();
                Intrinsics.checkNotNullExpressionValue(res, "content.toString()");
                contentStr = res;
                replaceRegex = var13_18.getReplaceRegex();
                var18_25 = replaceRegex;
                var19_26 = false;
                var20_29 = 0;
                if (!(var18_25 == null || var18_25.length() == 0)) {
                    contentStr = AnalyzeRule.getString$default(var14_19, replaceRegex, (Object)contentStr, false, 4, null);
                }
                if ((var18_25 = var8_8) != null) {
                    DebugLog.DefaultImpls.log$default((DebugLog)var18_25, var4_4.getBookSourceUrl(), "\u250c\u83b7\u53d6\u7ae0\u8282\u540d\u79f0", false, 4, null);
                }
                var18_25 = var8_8;
                if (var18_25 != null) {
                    DebugLog.DefaultImpls.log$default((DebugLog)var18_25, var4_4.getBookSourceUrl(), Intrinsics.stringPlus("\u2514", var3_3.getTitle()), false, 4, null);
                }
                var18_25 = var8_8;
                if (var18_25 != null) {
                    DebugLog.DefaultImpls.log$default((DebugLog)var18_25, var4_4.getBookSourceUrl(), "\u250c\u83b7\u53d6\u6b63\u6587\u5185\u5bb9 (\u957f\u5ea6\uff1a" + contentStr.length() + ')', false, 4, null);
                }
                if (contentStr.length() > 300) {
                    var18_25 = var8_8;
                    if (var18_25 != null) {
                        v2 = var4_4.getBookSourceUrl();
                        v3 = new StringBuilder().append("\u2514\n");
                        var19_28 = contentStr;
                        var20_29 = 0;
                        var21_30 = 150;
                        var22_32 = false;
                        v4 = var19_28;
                        if (v4 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                        }
                        v5 = v4.substring(var20_29, var21_30);
                        Intrinsics.checkNotNullExpressionValue(v5, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                        v6 = v3.append(v5).append(" ... ");
                        var19_28 = contentStr;
                        var20_29 = contentStr.length() - 150;
                        var21_30 = contentStr.length();
                        var22_32 = false;
                        v7 = var19_28;
                        if (v7 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                        }
                        v8 = v7.substring(var20_29, var21_30);
                        Intrinsics.checkNotNullExpressionValue(v8, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                        DebugLog.DefaultImpls.log$default((DebugLog)var18_25, v2, v6.append(v8).toString(), false, 4, null);
                    }
                } else {
                    var18_25 = var8_8;
                    if (var18_25 != null) {
                        DebugLog.DefaultImpls.log$default((DebugLog)var18_25, var4_4.getBookSourceUrl(), Intrinsics.stringPlus("\u2514\n", contentStr), false, 4, null);
                    }
                }
                return contentStr;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object analyzeContent$default(BookContent bookContent, String string, Book book, BookChapter bookChapter, BookSource bookSource, String string2, String string3, String string4, DebugLog debugLog, Continuation continuation, int n, Object object) {
        if ((n & 0x40) != 0) {
            string4 = null;
        }
        if ((n & 0x80) != 0) {
            debugLog = null;
        }
        return bookContent.analyzeContent(string, book, bookChapter, bookSource, string2, string3, string4, debugLog, continuation);
    }

    private final Pair<String, List<String>> analyzeContent(Book book, String baseUrl, String redirectUrl, String body, ContentRule contentRule, BookChapter chapter, BookSource bookSource, String nextChapterUrl, boolean printLog, DebugLog debugLog) throws Exception {
        AnalyzeRule analyzeRule = new AnalyzeRule(book, bookSource, debugLog);
        analyzeRule.setContent(body, baseUrl);
        analyzeRule.setChapter(chapter);
        URL rUrl = analyzeRule.setRedirectUrl(redirectUrl);
        analyzeRule.setNextChapterUrl(nextChapterUrl);
        boolean bl = false;
        ArrayList nextUrlList = new ArrayList();
        analyzeRule.setChapter(chapter);
        String content = AnalyzeRule.getString$default(analyzeRule, contentRule.getContent(), null, false, 6, null);
        content = HtmlFormatter.INSTANCE.formatKeepImg(content, rUrl);
        String nextUrlRule = contentRule.getNextContentUrl();
        Object object = nextUrlRule;
        boolean bl2 = false;
        boolean bl3 = false;
        if (!(object == null || object.length() == 0)) {
            if (printLog && (object = debugLog) != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)object, bookSource.getBookSourceUrl(), "\u250c\u83b7\u53d6\u6b63\u6587\u4e0b\u4e00\u9875\u94fe\u63a5", false, 4, null);
            }
            if ((object = AnalyzeRule.getStringList$default(analyzeRule, nextUrlRule, null, true, 2, null)) != null) {
                Object object2 = object;
                bl3 = false;
                boolean bl4 = false;
                Object it = object2;
                boolean bl5 = false;
                nextUrlList.addAll((Collection)it);
            }
            if (printLog && (object = debugLog) != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)object, bookSource.getBookSourceUrl(), Intrinsics.stringPlus("\u2514", CollectionsKt.joinToString$default(nextUrlList, "\uff0c", null, null, 0, null, null, 62, null)), false, 4, null);
            }
        }
        return new Pair<String, List<String>>(content, nextUrlList);
    }

    static /* synthetic */ Pair analyzeContent$default(BookContent bookContent, Book book, String string, String string2, String string3, ContentRule contentRule, BookChapter bookChapter, BookSource bookSource, String string4, boolean bl, DebugLog debugLog, int n, Object object) throws Exception {
        if ((n & 0x100) != 0) {
            bl = true;
        }
        if ((n & 0x200) != 0) {
            debugLog = null;
        }
        return bookContent.analyzeContent(book, string, string2, string3, contentRule, bookChapter, bookSource, string4, bl, debugLog);
    }

    public static final /* synthetic */ Pair access$analyzeContent(BookContent $this, Book book, String baseUrl, String redirectUrl, String body, ContentRule contentRule, BookChapter chapter, BookSource bookSource, String nextChapterUrl, boolean printLog, DebugLog debugLog) {
        return $this.analyzeContent(book, baseUrl, redirectUrl, body, contentRule, chapter, bookSource, nextChapterUrl, printLog, debugLog);
    }
}

