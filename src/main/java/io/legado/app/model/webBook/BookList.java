/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.model.webBook;

import io.legado.app.data.entities.BaseSource;
import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookSource;
import io.legado.app.data.entities.SearchBook;
import io.legado.app.data.entities.rule.BookListRule;
import io.legado.app.help.BookHelp;
import io.legado.app.model.DebugLog;
import io.legado.app.model.analyzeRule.AnalyzeRule;
import io.legado.app.model.analyzeRule.AnalyzeUrl;
import io.legado.app.model.analyzeRule.RuleDataInterface;
import io.legado.app.model.webBook.BookInfo;
import io.legado.app.model.webBook.BookList;
import io.legado.app.utils.NetworkUtils;
import io.legado.app.utils.StringExtensionsKt;
import io.legado.app.utils.StringUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.JobKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002Ja\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013JQ\u0010\u0014\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\b\u0010\u0017\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u00e1\u0001\u0010\u0019\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\b2\b\u0010\u0017\u001a\u0004\u0018\u00010\b2\u0006\u0010\u001b\u001a\u00020\u00102\u0010\u0010\u001c\u001a\f\u0012\b\u0012\u00060\u001eR\u00020\u00160\u001d2\u0010\u0010\u001f\u001a\f\u0012\b\u0012\u00060\u001eR\u00020\u00160\u001d2\u0010\u0010 \u001a\f\u0012\b\u0012\u00060\u001eR\u00020\u00160\u001d2\u0010\u0010!\u001a\f\u0012\b\u0012\u00060\u001eR\u00020\u00160\u001d2\u0010\u0010\"\u001a\f\u0012\b\u0012\u00060\u001eR\u00020\u00160\u001d2\u0010\u0010#\u001a\f\u0012\b\u0012\u00060\u001eR\u00020\u00160\u001d2\u0010\u0010$\u001a\f\u0012\b\u0012\u00060\u001eR\u00020\u00160\u001d2\u0010\u0010%\u001a\f\u0012\b\u0012\u00060\u001eR\u00020\u00160\u001d2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010&\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006'"}, d2={"Lio/legado/app/model/webBook/BookList;", "", "()V", "analyzeBookList", "Ljava/util/ArrayList;", "Lio/legado/app/data/entities/SearchBook;", "Lkotlin/collections/ArrayList;", "body", "", "bookSource", "Lio/legado/app/data/entities/BookSource;", "analyzeUrl", "Lio/legado/app/model/analyzeRule/AnalyzeUrl;", "baseUrl", "variableBook", "isSearch", "", "debugLog", "Lio/legado/app/model/DebugLog;", "(Ljava/lang/String;Lio/legado/app/data/entities/BookSource;Lio/legado/app/model/analyzeRule/AnalyzeUrl;Ljava/lang/String;Lio/legado/app/data/entities/SearchBook;ZLio/legado/app/model/DebugLog;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getInfoItem", "analyzeRule", "Lio/legado/app/model/analyzeRule/AnalyzeRule;", "variable", "(Ljava/lang/String;Lio/legado/app/model/analyzeRule/AnalyzeRule;Lio/legado/app/data/entities/BookSource;Lio/legado/app/model/analyzeRule/AnalyzeUrl;Ljava/lang/String;Ljava/lang/String;Lio/legado/app/model/DebugLog;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSearchItem", "item", "log", "ruleName", "", "Lio/legado/app/model/analyzeRule/AnalyzeRule$SourceRule;", "ruleBookUrl", "ruleAuthor", "ruleKind", "ruleCoverUrl", "ruleWordCount", "ruleIntro", "ruleLastChapter", "(Ljava/lang/Object;Lio/legado/app/model/analyzeRule/AnalyzeRule;Lio/legado/app/data/entities/BookSource;Ljava/lang/String;Ljava/lang/String;ZLjava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lio/legado/app/model/DebugLog;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "reader-pro"})
public final class BookList {
    @NotNull
    public static final BookList INSTANCE = new BookList();

    private BookList() {
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object analyzeBookList(@Nullable String var1_1, @NotNull BookSource var2_2, @NotNull AnalyzeUrl var3_3, @NotNull String var4_4, @NotNull SearchBook var5_5, boolean var6_6, @Nullable DebugLog var7_7, @NotNull Continuation<? super ArrayList<SearchBook>> var8_8) throws Exception {
        block36: {
            block35: {
                block34: {
                    if (!(var8_8 instanceof analyzeBookList.1)) ** GOTO lbl-1000
                    var34_9 = var8_8;
                    if ((var34_9.label & -2147483648) != 0) {
                        var34_9.label -= -2147483648;
                    } else lbl-1000:
                    // 2 sources

                    {
                        $continuation = new ContinuationImpl(this, var8_8){
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
                            Object L$12;
                            Object L$13;
                            Object L$14;
                            Object L$15;
                            Object L$16;
                            int I$0;
                            int I$1;
                            /* synthetic */ Object result;
                            final /* synthetic */ BookList this$0;
                            int label;
                            {
                                this.this$0 = this$0;
                                super($completion);
                            }

                            @Nullable
                            public final Object invokeSuspend(@NotNull Object $result) {
                                this.result = $result;
                                this.label |= Integer.MIN_VALUE;
                                return this.this$0.analyzeBookList(null, null, null, null, null, false, null, this);
                            }
                        };
                    }
                    $result = $continuation.result;
                    var35_11 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    switch ($continuation.label) {
                        case 0: {
                            ResultKt.throwOnFailure($result);
                            bookList = new ArrayList<E>();
                            var10_13 = body;
                            if (var10_13 == null) {
                                throw new Exception("error_get_web_content");
                            }
                            var10_13 = debugLog;
                            if (var10_13 == null) {
                                v0 = null;
                            } else {
                                DebugLog.DefaultImpls.log$default((DebugLog)var10_13, bookSource.getBookSourceUrl(), Intrinsics.stringPlus("\u2261\u83b7\u53d6\u6210\u529f:", analyzeUrl.getRuleUrl()), false, 4, null);
                                v0 = Unit.INSTANCE;
                            }
                            analyzeRule = new AnalyzeRule((RuleDataInterface)variableBook, (BaseSource)bookSource, (DebugLog)debugLog);
                            AnalyzeRule.setContent$default(analyzeRule, body, null, 2, null).setBaseUrl((String)baseUrl);
                            analyzeRule.setRedirectUrl((String)baseUrl);
                            var11_14 = bookSource.getBookUrlPattern();
                            if (var11_14 != null) ** GOTO lbl31
                            v1 = null;
                            break block34;
lbl31:
                            // 1 sources

                            var12_15 = var11_14;
                            var13_17 = false;
                            var14_19 = false;
                            it = var12_15;
                            $i$a$-let-BookList$analyzeBookList$2 = 0;
                            JobKt.ensureActive($continuation.getContext());
                            var17_28 = (CharSequence)baseUrl;
                            var18_32 = it;
                            var19_34 = false;
                            var18_32 = new Regex((String)var18_32);
                            var19_34 = false;
                            if (!var18_32.matches((CharSequence)var17_28)) break;
                            var17_28 = debugLog;
                            if (var17_28 == null) {
                                v2 = null;
                            } else {
                                DebugLog.DefaultImpls.log$default((DebugLog)var17_28, bookSource.getBookSourceUrl(), "\u2261\u94fe\u63a5\u4e3a\u8be6\u60c5\u9875", false, 4, null);
                                v2 = Unit.INSTANCE;
                            }
                            $continuation.L$0 = body;
                            $continuation.L$1 = bookList;
                            $continuation.label = 1;
                            v3 = BookList.INSTANCE.getInfoItem((String)body, analyzeRule, (BookSource)bookSource, (AnalyzeUrl)analyzeUrl, (String)baseUrl, variableBook.getVariable(), (DebugLog)debugLog, $continuation);
                            if (v3 == var35_11) {
                                return var35_11;
                            }
                            ** GOTO lbl62
                        }
                        case 1: {
                            $i$a$-let-BookList$analyzeBookList$2 = false;
                            var9_12 = (ArrayList)$continuation.L$1;
                            var1_1 = (String)$continuation.L$0;
                            ResultKt.throwOnFailure($result);
                            v3 = $result;
lbl62:
                            // 2 sources

                            if ((var17_28 = (SearchBook)v3) == null) {
                                v4 = null;
                            } else {
                                var18_32 = var17_28;
                                var19_34 = false;
                                var20_37 = false;
                                searchBook = var18_32;
                                $i$a$-let-BookList$analyzeBookList$2$1 = false;
                                searchBook.setInfoHtml(var1_1);
                                v4 = Boxing.boxBoolean(var9_12.add(searchBook));
                            }
                            return var9_12;
                        }
                    }
                    v1 = Unit.INSTANCE;
                }
                var11_14 = null;
                reverse = false;
                if (var6_6) {
                    v5 = var2_2.getSearchRule();
                } else {
                    var14_20 = var2_2.getExploreRule().getBookList();
                    it = false;
                    $i$a$-let-BookList$analyzeBookList$2 = 0;
                    v5 = (var14_20 == null || StringsKt.isBlank(var14_20) != false) != false ? (BookListRule)var2_2.getSearchRule() : (BookListRule)var2_2.getExploreRule();
                }
                bookListRule = v5;
                it = bookListRule.getBookList();
                v6 = ruleList = it == null ? "" : it;
                if (StringsKt.startsWith$default((String)ruleList, "-", false, 2, null)) {
                    reverse = true;
                    it = ruleList;
                    $i$a$-let-BookList$analyzeBookList$2 = 1;
                    var17_29 = false;
                    v7 = it.substring($i$a$-let-BookList$analyzeBookList$2);
                    Intrinsics.checkNotNullExpressionValue(v7, "(this as java.lang.String).substring(startIndex)");
                    ruleList = v7;
                }
                if (StringsKt.startsWith$default((String)ruleList, "+", false, 2, null)) {
                    it = ruleList;
                    $i$a$-let-BookList$analyzeBookList$2 = 1;
                    var17_30 = false;
                    v8 = it;
                    if (v8 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    v9 = v8.substring($i$a$-let-BookList$analyzeBookList$2);
                    Intrinsics.checkNotNullExpressionValue(v9, "(this as java.lang.String).substring(startIndex)");
                    ruleList = v9;
                }
                if ((it = var7_7) == null) {
                    v10 = null;
                } else {
                    DebugLog.DefaultImpls.log$default((DebugLog)it, var2_2.getBookSourceUrl(), "\u250c\u83b7\u53d6\u4e66\u7c4d\u5217\u8868", false, 4, null);
                    v10 = Unit.INSTANCE;
                }
                collections = analyzeRule.getElements((String)ruleList);
                JobKt.ensureActive($continuation.getContext());
                if (!collections.isEmpty()) break block35;
                it = var2_2.getBookUrlPattern();
                $i$a$-let-BookList$analyzeBookList$2 = 0;
                var17_31 = false;
                if (!(it == null || it.length() == 0)) break block35;
                it = var7_7;
                if (it == null) {
                    v11 = null;
                } else {
                    DebugLog.DefaultImpls.log$default((DebugLog)it, var2_2.getBookSourceUrl(), "\u2514\u5217\u8868\u4e3a\u7a7a,\u6309\u8be6\u60c5\u9875\u89e3\u6790", false, 4, null);
                    v11 = Unit.INSTANCE;
                }
                $continuation.L$0 = var1_1;
                $continuation.L$1 = var9_12;
                $continuation.label = 2;
                v12 = this.getInfoItem(var1_1, analyzeRule, var2_2, var3_3, var4_4, var5_5.getVariable(), var7_7, $continuation);
                if (v12 == var35_11) {
                    return var35_11;
                }
                ** GOTO lbl136
                {
                    case 2: {
                        var9_12 = (ArrayList)$continuation.L$1;
                        var1_1 = (String)$continuation.L$0;
                        ResultKt.throwOnFailure($result);
                        v12 = $result;
lbl136:
                        // 2 sources

                        if ((it = (SearchBook)v12) == null) {
                            v13 = null;
                        } else {
                            $i$a$-let-BookList$analyzeBookList$2 = it;
                            var17_31 = false;
                            var18_33 = false;
                            searchBook = $i$a$-let-BookList$analyzeBookList$2;
                            $i$a$-let-BookList$analyzeBookList$3 = false;
                            searchBook.setInfoHtml(var1_1);
                            v13 = Boxing.boxBoolean(var9_12.add(searchBook));
                        }
                        break block36;
                    }
                }
            }
            ruleName = AnalyzeRule.splitSourceRule$default(var10_13, var13_18.getName(), false, 2, null);
            ruleBookUrl = AnalyzeRule.splitSourceRule$default(var10_13, var13_18.getBookUrl(), false, 2, null);
            ruleAuthor = AnalyzeRule.splitSourceRule$default(var10_13, var13_18.getAuthor(), false, 2, null);
            ruleCoverUrl = AnalyzeRule.splitSourceRule$default(var10_13, var13_18.getCoverUrl(), false, 2, null);
            ruleIntro = AnalyzeRule.splitSourceRule$default(var10_13, var13_18.getIntro(), false, 2, null);
            ruleKind = AnalyzeRule.splitSourceRule$default(var10_13, var13_18.getKind(), false, 2, null);
            ruleLastChapter = AnalyzeRule.splitSourceRule$default(var10_13, var13_18.getLastChapter(), false, 2, null);
            ruleWordCount = AnalyzeRule.splitSourceRule$default(var10_13, var13_18.getWordCount(), false, 2, null);
            var23_44 = var7_7;
            if (var23_44 == null) {
                v14 = null;
            } else {
                DebugLog.DefaultImpls.log$default(var23_44, var2_2.getBookSourceUrl(), Intrinsics.stringPlus("\u2514\u5217\u8868\u5927\u5c0f:", Boxing.boxInt(var11_14.size())), false, 4, null);
                v14 = Unit.INSTANCE;
            }
            var23_44 = var11_14.iterator();
            var24_45 = 0;
lbl164:
            // 3 sources

            while (var23_44.hasNext()) {
                index = var24_45++;
                item = var23_44.next();
                var28_49 = var5_5.getVariable();
                var29_50 = index == 0;
                $continuation.L$0 = this;
                $continuation.L$1 = var1_1;
                $continuation.L$2 = var2_2;
                $continuation.L$3 = var4_4;
                $continuation.L$4 = var5_5;
                $continuation.L$5 = var7_7;
                $continuation.L$6 = var9_12;
                $continuation.L$7 = var10_13;
                $continuation.L$8 = ruleName;
                $continuation.L$9 = ruleBookUrl;
                $continuation.L$10 = ruleAuthor;
                $continuation.L$11 = ruleCoverUrl;
                $continuation.L$12 = ruleIntro;
                $continuation.L$13 = ruleKind;
                $continuation.L$14 = ruleLastChapter;
                $continuation.L$15 = ruleWordCount;
                $continuation.L$16 = var23_44;
                $continuation.I$0 = var12_16;
                $continuation.I$1 = var24_45;
                $continuation.label = 3;
                v15 = this.getSearchItem(item, var10_13, var2_2, var4_4, (String)var28_49, var29_50 != false, ruleName, ruleBookUrl, ruleAuthor, ruleKind, ruleCoverUrl, ruleWordCount, ruleIntro, ruleLastChapter, var7_7, $continuation);
                if (v15 == var35_11) {
                    return var35_11;
                }
                ** GOTO lbl216
            }
            {
                break;
                case 3: {
                    var24_45 = $continuation.I$1;
                    var12_16 = $continuation.I$0;
                    var23_44 = (Iterator)$continuation.L$16;
                    ruleWordCount = (List)$continuation.L$15;
                    ruleLastChapter = (List)$continuation.L$14;
                    ruleKind = (List)$continuation.L$13;
                    ruleIntro = (List)$continuation.L$12;
                    ruleCoverUrl = (List)$continuation.L$11;
                    ruleAuthor = (List)$continuation.L$10;
                    ruleBookUrl = (List)$continuation.L$9;
                    ruleName = (List)$continuation.L$8;
                    var10_13 = (AnalyzeRule)$continuation.L$7;
                    var9_12 = (ArrayList)$continuation.L$6;
                    var7_7 = (DebugLog)$continuation.L$5;
                    var5_5 = (SearchBook)$continuation.L$4;
                    var4_4 = (String)$continuation.L$3;
                    var2_2 = (BookSource)$continuation.L$2;
                    var1_1 = (String)$continuation.L$1;
                    this = (BookList)$continuation.L$0;
                    ResultKt.throwOnFailure($result);
                    v15 = $result;
lbl216:
                    // 2 sources

                    if ((var27_48 = (SearchBook)v15) != null) ** GOTO lbl219
                    v16 = null;
                    ** GOTO lbl164
lbl219:
                    // 1 sources

                    var28_49 = var27_48;
                    var29_50 = false;
                    var30_51 = false;
                    searchBook = var28_49;
                    $i$a$-let-BookList$analyzeBookList$4 = false;
                    if (Intrinsics.areEqual(var4_4, searchBook.getBookUrl())) {
                        searchBook.setInfoHtml(var1_1);
                    }
                    v16 = Boxing.boxBoolean(var9_12.add(searchBook));
                    ** GOTO lbl164
                }
            }
            if (var12_16 != 0) {
                CollectionsKt.reverse(var9_12);
            }
        }
        return var9_12;
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object analyzeBookList$default(BookList bookList, String string, BookSource bookSource, AnalyzeUrl analyzeUrl, String string2, SearchBook searchBook2, boolean bl, DebugLog debugLog, Continuation continuation, int n, Object object) throws Exception {
        if ((n & 0x20) != 0) {
            bl = true;
        }
        if ((n & 0x40) != 0) {
            debugLog = null;
        }
        return bookList.analyzeBookList(string, bookSource, analyzeUrl, string2, searchBook2, bl, debugLog, continuation);
    }

    /*
     * Unable to fully structure code
     */
    private final Object getInfoItem(String var1_1, AnalyzeRule var2_2, BookSource var3_3, AnalyzeUrl var4_4, String var5_5, String var6_6, DebugLog var7_7, Continuation<? super SearchBook> var8_8) {
        if (!(var8_8 instanceof getInfoItem.1)) ** GOTO lbl-1000
        var13_9 = var8_8;
        if ((var13_9.label & -2147483648) != 0) {
            var13_9.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var8_8){
                Object L$0;
                /* synthetic */ Object result;
                final /* synthetic */ BookList this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return BookList.access$getInfoItem(this.this$0, null, null, null, null, null, null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var14_11 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                book = new Book(null, null, null, null, null, null, null, null, null, null, null, null, null, 0, 0L, null, 0L, 0L, 0, 0, null, 0, 0, 0L, null, false, 0, 0, false, (String)variable, null, false, null, -536870913, 1, null);
                book.setBookUrl(analyzeUrl.getRuleUrl());
                book.setOrigin(bookSource.getBookSourceUrl());
                book.setOriginName(bookSource.getBookSourceName());
                book.setOriginOrder(bookSource.getCustomOrder());
                book.setType(bookSource.getBookSourceType());
                book.setUserNameSpace(analyzeRule.getUserNameSpace());
                analyzeRule.setRuleData(book);
                $continuation.L$0 = book;
                $continuation.label = 1;
                v0 = BookInfo.INSTANCE.analyzeBookInfo(book, (String)body, (AnalyzeRule)analyzeRule, (BookSource)bookSource, (String)baseUrl, (String)baseUrl, false, (DebugLog)debugLog, $continuation);
                if (v0 == var14_11) {
                    return var14_11;
                }
                ** GOTO lbl30
            }
            case 1: {
                book = (Book)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl30:
                // 2 sources

                var10_13 = book.getName();
                var11_14 = false;
                if (StringsKt.isBlank(var10_13) == false) {
                    return book.toSearchBook();
                }
                return null;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    static /* synthetic */ Object getInfoItem$default(BookList bookList, String string, AnalyzeRule analyzeRule, BookSource bookSource, AnalyzeUrl analyzeUrl, String string2, String string3, DebugLog debugLog, Continuation continuation, int n, Object object) {
        if ((n & 0x40) != 0) {
            debugLog = null;
        }
        return bookList.getInfoItem(string, analyzeRule, bookSource, analyzeUrl, string2, string3, debugLog, continuation);
    }

    private final Object getSearchItem(Object item, AnalyzeRule analyzeRule, BookSource bookSource, String baseUrl, String variable, boolean log, List<AnalyzeRule.SourceRule> ruleName, List<AnalyzeRule.SourceRule> ruleBookUrl, List<AnalyzeRule.SourceRule> ruleAuthor, List<AnalyzeRule.SourceRule> ruleKind, List<AnalyzeRule.SourceRule> ruleCoverUrl, List<AnalyzeRule.SourceRule> ruleWordCount, List<AnalyzeRule.SourceRule> ruleIntro, List<AnalyzeRule.SourceRule> ruleLastChapter, DebugLog debugLog, Continuation<? super SearchBook> $completion) {
        Object object;
        SearchBook searchBook2 = new SearchBook(null, null, null, 0, null, null, null, null, null, null, null, null, 0L, variable, 0, 24575, null);
        searchBook2.setOrigin(bookSource.getBookSourceUrl());
        searchBook2.setOriginName(bookSource.getBookSourceName());
        searchBook2.setType(bookSource.getBookSourceType());
        searchBook2.setOriginOrder(bookSource.getCustomOrder());
        searchBook2.setUserNameSpace(analyzeRule.getUserNameSpace());
        analyzeRule.setRuleData(searchBook2);
        AnalyzeRule.setContent$default(analyzeRule, item, null, 2, null);
        JobKt.ensureActive($completion.getContext());
        if (log && (object = debugLog) != null) {
            DebugLog.DefaultImpls.log$default((DebugLog)object, bookSource.getBookSourceUrl(), "\u250c\u83b7\u53d6\u4e66\u540d", false, 4, null);
        }
        searchBook2.setName(BookHelp.INSTANCE.formatBookName(AnalyzeRule.getString$default(analyzeRule, ruleName, null, false, 6, null)));
        if (log && (object = debugLog) != null) {
            DebugLog.DefaultImpls.log$default((DebugLog)object, bookSource.getBookSourceUrl(), Intrinsics.stringPlus("\u2514", searchBook2.getName()), false, 4, null);
        }
        object = searchBook2.getName();
        boolean bl = false;
        if (object.length() > 0) {
            block33: {
                block32: {
                    Object e2;
                    block31: {
                        block30: {
                            block29: {
                                JobKt.ensureActive($completion.getContext());
                                if (log && (object = debugLog) != null) {
                                    DebugLog.DefaultImpls.log$default((DebugLog)object, bookSource.getBookSourceUrl(), "\u250c\u83b7\u53d6\u4f5c\u8005", false, 4, null);
                                }
                                searchBook2.setAuthor(BookHelp.INSTANCE.formatBookAuthor(AnalyzeRule.getString$default(analyzeRule, ruleAuthor, null, false, 6, null)));
                                if (log && (object = debugLog) != null) {
                                    DebugLog.DefaultImpls.log$default((DebugLog)object, bookSource.getBookSourceUrl(), Intrinsics.stringPlus("\u2514", searchBook2.getAuthor()), false, 4, null);
                                }
                                JobKt.ensureActive($completion.getContext());
                                if (log && (object = debugLog) != null) {
                                    DebugLog.DefaultImpls.log$default((DebugLog)object, bookSource.getBookSourceUrl(), "\u250c\u83b7\u53d6\u5206\u7c7b", false, 4, null);
                                }
                                try {
                                    object = AnalyzeRule.getStringList$default(analyzeRule, ruleKind, null, false, 6, null);
                                    searchBook2.setKind(object == null ? null : CollectionsKt.joinToString$default((Iterable)object, ",", null, null, 0, null, null, 62, null));
                                    if (log && (object = debugLog) != null) {
                                        DebugLog.DefaultImpls.log$default((DebugLog)object, bookSource.getBookSourceUrl(), Intrinsics.stringPlus("\u2514", searchBook2.getKind()), false, 4, null);
                                    }
                                }
                                catch (Exception e2) {
                                    DebugLog debugLog2;
                                    if (!log || (debugLog2 = debugLog) == null) break block29;
                                    DebugLog.DefaultImpls.log$default(debugLog2, bookSource.getBookSourceUrl(), Intrinsics.stringPlus("\u2514", e2.getLocalizedMessage()), false, 4, null);
                                }
                            }
                            JobKt.ensureActive($completion.getContext());
                            if (log && (e2 = debugLog) != null) {
                                DebugLog.DefaultImpls.log$default((DebugLog)e2, bookSource.getBookSourceUrl(), "\u250c\u83b7\u53d6\u5b57\u6570", false, 4, null);
                            }
                            try {
                                searchBook2.setWordCount(StringUtils.INSTANCE.wordCountFormat(AnalyzeRule.getString$default(analyzeRule, ruleWordCount, null, false, 6, null)));
                                if (log && (e2 = debugLog) != null) {
                                    DebugLog.DefaultImpls.log$default((DebugLog)e2, bookSource.getBookSourceUrl(), Intrinsics.stringPlus("\u2514", searchBook2.getWordCount()), false, 4, null);
                                }
                            }
                            catch (Exception e2) {
                                DebugLog debugLog3;
                                if (!log || (debugLog3 = debugLog) == null) break block30;
                                DebugLog.DefaultImpls.log$default(debugLog3, bookSource.getBookSourceUrl(), Intrinsics.stringPlus("\u2514", e2.getLocalizedMessage()), false, 4, null);
                            }
                        }
                        JobKt.ensureActive($completion.getContext());
                        if (log && (e2 = debugLog) != null) {
                            DebugLog.DefaultImpls.log$default((DebugLog)e2, bookSource.getBookSourceUrl(), "\u250c\u83b7\u53d6\u6700\u65b0\u7ae0\u8282", false, 4, null);
                        }
                        try {
                            searchBook2.setLatestChapterTitle(AnalyzeRule.getString$default(analyzeRule, ruleLastChapter, null, false, 6, null));
                            if (log && (e2 = debugLog) != null) {
                                DebugLog.DefaultImpls.log$default((DebugLog)e2, bookSource.getBookSourceUrl(), Intrinsics.stringPlus("\u2514", searchBook2.getLatestChapterTitle()), false, 4, null);
                            }
                        }
                        catch (Exception e3) {
                            DebugLog debugLog4;
                            if (!log || (debugLog4 = debugLog) == null) break block31;
                            DebugLog.DefaultImpls.log$default(debugLog4, bookSource.getBookSourceUrl(), Intrinsics.stringPlus("\u2514", e3.getLocalizedMessage()), false, 4, null);
                        }
                    }
                    JobKt.ensureActive($completion.getContext());
                    if (log && (e2 = debugLog) != null) {
                        DebugLog.DefaultImpls.log$default((DebugLog)e2, bookSource.getBookSourceUrl(), "\u250c\u83b7\u53d6\u7b80\u4ecb", false, 4, null);
                    }
                    try {
                        searchBook2.setIntro(StringExtensionsKt.htmlFormat(AnalyzeRule.getString$default(analyzeRule, ruleIntro, null, false, 6, null)));
                        if (log && (e2 = debugLog) != null) {
                            DebugLog.DefaultImpls.log$default((DebugLog)e2, bookSource.getBookSourceUrl(), Intrinsics.stringPlus("\u2514", searchBook2.getIntro()), false, 4, null);
                        }
                    }
                    catch (Exception e3) {
                        DebugLog debugLog5;
                        if (!log || (debugLog5 = debugLog) == null) break block32;
                        DebugLog.DefaultImpls.log$default(debugLog5, bookSource.getBookSourceUrl(), Intrinsics.stringPlus("\u2514", e3.getLocalizedMessage()), false, 4, null);
                    }
                }
                JobKt.ensureActive($completion.getContext());
                if (log && (e2 = debugLog) != null) {
                    DebugLog.DefaultImpls.log$default((DebugLog)e2, bookSource.getBookSourceUrl(), "\u250c\u83b7\u53d6\u5c01\u9762\u94fe\u63a5", false, 4, null);
                }
                try {
                    e2 = AnalyzeRule.getString$default(analyzeRule, ruleCoverUrl, null, false, 6, null);
                    boolean bl2 = false;
                    boolean bl3 = false;
                    Object it = e2;
                    boolean bl4 = false;
                    CharSequence charSequence = (CharSequence)it;
                    boolean bl5 = false;
                    if (charSequence.length() > 0) {
                        searchBook2.setCoverUrl(NetworkUtils.INSTANCE.getAbsoluteURL(baseUrl, (String)it));
                    }
                    if (log && (e2 = debugLog) != null) {
                        DebugLog.DefaultImpls.log$default((DebugLog)e2, bookSource.getBookSourceUrl(), Intrinsics.stringPlus("\u2514", searchBook2.getCoverUrl()), false, 4, null);
                    }
                }
                catch (Exception e) {
                    DebugLog debugLog6;
                    if (!log || (debugLog6 = debugLog) == null) break block33;
                    DebugLog.DefaultImpls.log$default(debugLog6, bookSource.getBookSourceUrl(), Intrinsics.stringPlus("\u2514", e.getLocalizedMessage()), false, 4, null);
                }
            }
            JobKt.ensureActive($completion.getContext());
            if (log && (object = debugLog) != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)object, bookSource.getBookSourceUrl(), "\u250c\u83b7\u53d6\u8be6\u60c5\u9875\u94fe\u63a5", false, 4, null);
            }
            searchBook2.setBookUrl(AnalyzeRule.getString$default(analyzeRule, ruleBookUrl, null, true, 2, null));
            object = searchBook2.getBookUrl();
            boolean bl6 = false;
            if (object.length() == 0) {
                searchBook2.setBookUrl(baseUrl);
            }
            if (log && (object = debugLog) != null) {
                DebugLog.DefaultImpls.log$default((DebugLog)object, bookSource.getBookSourceUrl(), Intrinsics.stringPlus("\u2514", searchBook2.getBookUrl()), false, 4, null);
            }
            return searchBook2;
        }
        return null;
    }

    static /* synthetic */ Object getSearchItem$default(BookList bookList, Object object, AnalyzeRule analyzeRule, BookSource bookSource, String string, String string2, boolean bl, List list2, List list3, List list4, List list5, List list6, List list7, List list8, List list9, DebugLog debugLog, Continuation continuation, int n, Object object2) {
        if ((n & 0x4000) != 0) {
            debugLog = null;
        }
        return bookList.getSearchItem(object, analyzeRule, bookSource, string, string2, bl, list2, list3, list4, list5, list6, list7, list8, list9, debugLog, continuation);
    }

    public static final /* synthetic */ Object access$getInfoItem(BookList $this, String body, AnalyzeRule analyzeRule, BookSource bookSource, AnalyzeUrl analyzeUrl, String baseUrl, String variable, DebugLog debugLog, Continuation $completion) {
        return $this.getInfoItem(body, analyzeRule, bookSource, analyzeUrl, baseUrl, variable, debugLog, $completion);
    }

    public static final /* synthetic */ Object access$getSearchItem(BookList $this, Object item, AnalyzeRule analyzeRule, BookSource bookSource, String baseUrl, String variable, boolean log, List ruleName, List ruleBookUrl, List ruleAuthor, List ruleKind, List ruleCoverUrl, List ruleWordCount, List ruleIntro, List ruleLastChapter, DebugLog debugLog, Continuation $completion) {
        return $this.getSearchItem(item, analyzeRule, bookSource, baseUrl, variable, log, ruleName, ruleBookUrl, ruleAuthor, ruleKind, ruleCoverUrl, ruleWordCount, ruleIntro, ruleLastChapter, debugLog, $completion);
    }
}

