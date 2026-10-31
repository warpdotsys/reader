/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.help;

import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;
import io.legado.app.data.entities.BookSource;
import io.legado.app.data.entities.rule.BookInfoRule;
import io.legado.app.data.entities.rule.ContentRule;
import io.legado.app.data.entities.rule.ExploreRule;
import io.legado.app.data.entities.rule.SearchRule;
import io.legado.app.data.entities.rule.TocRule;
import io.legado.app.exception.NoStackTraceException;
import io.legado.app.model.Debug;
import io.legado.app.model.DebugLog;
import io.legado.app.utils.GsonExtensionsKt;
import io.legado.app.utils.JsonExtensionsKt;
import io.legado.app.utils.StringExtensionsKt;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u001cB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\f\u0010\rJ*\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u000f0\b2\u0006\u0010\u0010\u001a\u00020\u0011\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J*\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u000f0\b2\u0006\u0010\n\u001a\u00020\u000b\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0012\u0010\rJ\u0014\u0010\u0014\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0015\u001a\u0004\u0018\u00010\u000bH\u0002J\u0014\u0010\u0016\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0017\u001a\u0004\u0018\u00010\u000bH\u0002J\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0019\u001a\u0004\u0018\u00010\u000bH\u0002J\u0014\u0010\u001a\u001a\u0004\u0018\u00010\u000b2\b\u0010\u001b\u001a\u0004\u0018\u00010\u000bH\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001d"}, d2={"Lio/legado/app/help/SourceAnalyzer;", "", "()V", "headerPattern", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "jsPattern", "jsonToBookSource", "Lkotlin/Result;", "Lio/legado/app/data/entities/BookSource;", "json", "", "jsonToBookSource-IoAF18A", "(Ljava/lang/String;)Ljava/lang/Object;", "jsonToBookSources", "", "inputStream", "Ljava/io/InputStream;", "jsonToBookSources-IoAF18A", "(Ljava/io/InputStream;)Ljava/lang/Object;", "toNewRule", "oldRule", "toNewUrl", "oldUrl", "toNewUrls", "oldUrls", "uaToHeader", "ua", "BookSourceAny", "reader-pro"})
public final class SourceAnalyzer {
    @NotNull
    public static final SourceAnalyzer INSTANCE = new SourceAnalyzer();
    private static final Pattern headerPattern = Pattern.compile("@Header:\\{.+?\\}", 2);
    private static final Pattern jsPattern = Pattern.compile("\\{\\{.+?\\}\\}", 2);

    private SourceAnalyzer() {
    }

    @NotNull
    public final Object jsonToBookSources-IoAF18A(@NotNull String json) {
        Object object;
        Intrinsics.checkNotNullParameter(json, "json");
        boolean bl = false;
        try {
            object = Result.Companion;
            boolean bl2 = false;
            boolean bl3 = false;
            List bookSources = new ArrayList();
            if (StringExtensionsKt.isJsonArray(json)) {
                Object t = JsonExtensionsKt.getJsonPath().parse(json).read("$", new Predicate[0]);
                Intrinsics.checkNotNullExpressionValue(t, "jsonPath.parse(json).read(\"$\")");
                List items = (List)t;
                for (Map item : items) {
                    DocumentContext jsonItem = JsonExtensionsKt.getJsonPath().parse(item);
                    Object object2 = jsonItem.jsonString();
                    Intrinsics.checkNotNullExpressionValue(object2, "jsonItem.jsonString()");
                    object2 = INSTANCE.jsonToBookSource-IoAF18A((String)object2);
                    boolean bl4 = false;
                    ResultKt.throwOnFailure(object2);
                    bl4 = false;
                    boolean bl5 = false;
                    BookSource it = (BookSource)object2;
                    boolean bl6 = false;
                    bookSources.add(it);
                }
            } else if (StringExtensionsKt.isJsonObject(json)) {
                Object object3 = INSTANCE.jsonToBookSource-IoAF18A(json);
                boolean bl7 = false;
                ResultKt.throwOnFailure(object3);
                bl7 = false;
                boolean bl8 = false;
                BookSource it = (BookSource)object3;
                boolean bl9 = false;
                bookSources.add(it);
            } else {
                throw new NoStackTraceException("\u683c\u5f0f\u4e0d\u5bf9");
            }
            List list2 = bookSources;
            boolean bl10 = false;
            object = Result.constructor-impl(list2);
        }
        catch (Throwable throwable) {
            Result.Companion companion = Result.Companion;
            boolean bl11 = false;
            object = Result.constructor-impl(ResultKt.createFailure(throwable));
        }
        return object;
    }

    @NotNull
    public final Object jsonToBookSources-IoAF18A(@NotNull InputStream inputStream2) {
        Object object;
        Intrinsics.checkNotNullParameter(inputStream2, "inputStream");
        boolean bl = false;
        try {
            Object object2;
            object = Result.Companion;
            boolean bl2 = false;
            boolean bl3 = false;
            List bookSources = new ArrayList();
            bl3 = false;
            try {
                object2 = Result.Companion;
                boolean bl4 = false;
                Object t = JsonExtensionsKt.getJsonPath().parse(inputStream2).read("$", new Predicate[0]);
                Intrinsics.checkNotNullExpressionValue(t, "jsonPath.parse(inputStream).read(\"$\")");
                List items = (List)t;
                for (Map item : items) {
                    DocumentContext jsonItem = JsonExtensionsKt.getJsonPath().parse(item);
                    Object object3 = jsonItem.jsonString();
                    Intrinsics.checkNotNullExpressionValue(object3, "jsonItem.jsonString()");
                    object3 = INSTANCE.jsonToBookSource-IoAF18A((String)object3);
                    boolean bl5 = false;
                    ResultKt.throwOnFailure(object3);
                    bl5 = false;
                    boolean bl6 = false;
                    BookSource it = (BookSource)object3;
                    boolean bl7 = false;
                    bookSources.add(it);
                }
                Unit unit = Unit.INSTANCE;
                boolean bl8 = false;
                object2 = Result.constructor-impl(unit);
            }
            catch (Throwable throwable) {
                Result.Companion companion = Result.Companion;
                boolean bl9 = false;
                object2 = Result.constructor-impl(ResultKt.createFailure(throwable));
            }
            Object object4 = object2;
            boolean bl10 = false;
            boolean bl11 = false;
            Throwable throwable = Result.exceptionOrNull-impl(object4);
            if (throwable != null) {
                Throwable throwable2 = throwable;
                boolean bl12 = false;
                boolean bl13 = false;
                Throwable throwable3 = throwable2;
                boolean bl14 = false;
                Throwable it = throwable3;
                boolean bl15 = false;
                Object t = JsonExtensionsKt.getJsonPath().parse(inputStream2).read("$", new Predicate[0]);
                Intrinsics.checkNotNullExpressionValue(t, "jsonPath.parse(inputStream).read(\"$\")");
                Map item = (Map)t;
                DocumentContext jsonItem = JsonExtensionsKt.getJsonPath().parse(item);
                Object object5 = jsonItem.jsonString();
                Intrinsics.checkNotNullExpressionValue(object5, "jsonItem.jsonString()");
                object5 = INSTANCE.jsonToBookSource-IoAF18A((String)object5);
                boolean bl16 = false;
                ResultKt.throwOnFailure(object5);
                bl16 = false;
                boolean bl17 = false;
                BookSource it2 = (BookSource)object5;
                boolean bl18 = false;
                bookSources.add(it2);
            }
            List list2 = bookSources;
            boolean bl19 = false;
            object = Result.constructor-impl(list2);
        }
        catch (Throwable throwable) {
            Result.Companion companion = Result.Companion;
            boolean bl20 = false;
            object = Result.constructor-impl(ResultKt.createFailure(throwable));
        }
        return object;
    }

    /*
     * Unable to fully structure code
     */
    @NotNull
    public final Object jsonToBookSource-IoAF18A(@NotNull String json) {
        Intrinsics.checkNotNullParameter(json, "json");
        source = new BookSource(null, null, null, 0, null, 0, false, false, null, null, null, null, null, null, null, null, 0L, 0L, 0, null, null, null, null, null, null, null, 0x3FFFFFF, null);
        var4_3 = GsonExtensionsKt.getGSON();
        var5_5 = json;
        var6_8 = false;
        var5_5 = StringsKt.trim((CharSequence)var5_5).toString();
        $i$f$fromJsonObject = false;
        var7_13 = false;
        try {
            var8_17 = Result.Companion;
            $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
            $i$f$genericType = false;
            var11_48 = new TypeToken<BookSourceAny>(){}.getType();
            Intrinsics.checkNotNullExpressionValue(var11_48, "object : TypeToken<T>() {}.type");
            v0 = $this$fromJsonObject$iv.fromJson((String)json$iv, (Type)var11_48);
            if (!(v0 instanceof BookSourceAny)) {
                v0 = null;
            }
            $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (BookSourceAny)v0;
            $i$f$genericType = false;
            var8_17 = Result.constructor-impl($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
        }
        catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
            $i$f$genericType = Result.Companion;
            var11_49 = false;
            var8_17 = Result.constructor-impl(ResultKt.createFailure($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
        }
        var4_3 = var8_17;
        var5_6 = false;
        $i$f$fromJsonObject = false;
        v1 = Result.exceptionOrNull-impl(var4_3);
        if (v1 != null) {
            $i$f$fromJsonObject = v1;
            var7_13 = false;
            var8_18 = false;
            $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = $i$f$fromJsonObject;
            $i$f$genericType = false;
            it = $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv;
            $i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1 = false;
            DebugLog.DefaultImpls.log$default(Debug.INSTANCE, "\u8f6c\u5316\u4e66\u6e90\u51fa\u9519", it.getLocalizedMessage(), false, 4, null);
        }
        var5_6 = false;
        sourceAny = (BookSourceAny)(Result.isFailure-impl(var4_3) != false ? null : var4_3);
        var4_4 = false;
        try {
            block86: {
                block84: {
                    var5_7 = Result.Companion;
                    $i$a$-runCatching-SourceAnalyzer$jsonToBookSource$1 = false;
                    var7_14 = sourceAny;
                    if ((var7_14 == null ? null : var7_14.getRuleToc()) != null) break block84;
                    var7_14 = source;
                    var8_19 = false;
                    $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9 = var7_14;
                    $i$a$-apply-SourceAnalyzer$jsonToBookSource$1$1 = false;
                    $i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1 = json;
                    var13_78 = false;
                    jsonItem = JsonExtensionsKt.getJsonPath().parse(StringsKt.trim((CharSequence)$i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1).toString());
                    Intrinsics.checkNotNullExpressionValue(jsonItem, "jsonItem");
                    $i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1 = JsonExtensionsKt.readString(jsonItem, "bookSourceUrl");
                    if ($i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1 == null) {
                        throw new NoStackTraceException("\u683c\u5f0f\u4e0d\u5bf9");
                    }
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setBookSourceUrl((String)$i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1);
                    $i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1 = JsonExtensionsKt.readString(jsonItem, "bookSourceName");
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setBookSourceName((String)($i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1 == null ? "" : $i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1));
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setBookSourceGroup(JsonExtensionsKt.readString(jsonItem, "bookSourceGroup"));
                    $i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1 = JsonExtensionsKt.readString(jsonItem, "bookSourceComment");
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setBookSourceComment((String)($i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1 == null ? "" : $i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1));
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setBookUrlPattern(JsonExtensionsKt.readString(jsonItem, "ruleBookUrlPattern"));
                    $i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1 = JsonExtensionsKt.readInt(jsonItem, "serialNumber");
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setCustomOrder($i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1 == null ? 0 : $i$a$-onFailure-SourceAnalyzer$jsonToBookSource$sourceAny$1.intValue());
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setHeader(SourceAnalyzer.INSTANCE.uaToHeader(JsonExtensionsKt.readString(jsonItem, "httpUserAgent")));
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setSearchUrl(SourceAnalyzer.INSTANCE.toNewUrl(JsonExtensionsKt.readString(jsonItem, "ruleSearchUrl")));
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setExploreUrl(SourceAnalyzer.INSTANCE.toNewUrls(JsonExtensionsKt.readString(jsonItem, "ruleFindUrl")));
                    sourceType = JsonExtensionsKt.readString(jsonItem, "bookSourceType");
                    var13_79 = sourceType;
                    if (var13_79 == null) ** GOTO lbl-1000
                    tmp = -1;
                    switch (var13_79.hashCode()) {
                        case 49: {
                            if (var13_79.equals("1")) {
                                tmp = 1;
                            }
                            break;
                        }
                        case 100313435: {
                            if (var13_79.equals("image")) {
                                tmp = 2;
                            }
                            break;
                        }
                        case 50: {
                            if (var13_79.equals("2")) {
                                tmp = 3;
                            }
                            break;
                        }
                        case 69775675: {
                            if (var13_79.equals("IMAGE")) {
                                tmp = 4;
                            }
                            break;
                        }
                        case 3143036: {
                            if (var13_79.equals("file")) {
                                tmp = 5;
                            }
                            break;
                        }
                        case 51: {
                            if (var13_79.equals("3")) {
                                tmp = 6;
                            }
                            break;
                        }
                        case 93166550: {
                            if (var13_79.equals("audio")) {
                                tmp = 7;
                            }
                            break;
                        }
                        case 2157948: {
                            if (var13_79.equals("FILE")) {
                                tmp = 8;
                            }
                            break;
                        }
                        case 62628790: {
                            if (var13_79.equals("AUDIO")) {
                                tmp = 9;
                            }
                            break;
                        }
                    }
                    switch (tmp) {
                        case 9: {
                            v2 = 1;
                            break;
                        }
                        case 7: {
                            v2 = 1;
                            break;
                        }
                        case 1: {
                            v2 = 1;
                            break;
                        }
                        case 4: {
                            v2 = 2;
                            break;
                        }
                        case 2: {
                            v2 = 2;
                            break;
                        }
                        case 3: {
                            v2 = 2;
                            break;
                        }
                        case 8: {
                            v2 = 3;
                            break;
                        }
                        case 5: {
                            v2 = 3;
                            break;
                        }
                        case 6: {
                            v2 = 3;
                            break;
                        }
                        default: lbl-1000:
                        // 2 sources

                        {
                            v2 = 0;
                        }
                    }
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setBookSourceType(v2);
                    var13_79 = JsonExtensionsKt.readBool(jsonItem, "enable");
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setEnabled(var13_79 == null ? true : var13_79.booleanValue());
                    var13_79 = $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.getExploreUrl();
                    var15_121 = false;
                    var16_123 = false;
                    if (var13_79 == null || StringsKt.isBlank((CharSequence)var13_79) != false) {
                        $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setEnabledExplore(false);
                    }
                    var13_79 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleSearchList"));
                    var15_122 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleSearchName"));
                    var16_124 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleSearchAuthor"));
                    var17_127 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleSearchIntroduce"));
                    var18_129 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleSearchKind"));
                    var19_130 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleSearchNoteUrl"));
                    var20_131 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleSearchCoverUrl"));
                    var21_132 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleSearchLastChapter"));
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setRuleSearch(new SearchRule((String)var13_79, var15_122, var16_124, var17_127, var18_129, var21_132, null, var19_130, var20_131, null, 576, null));
                    var13_79 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleFindList"));
                    var15_122 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleFindName"));
                    var16_124 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleFindAuthor"));
                    var17_127 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleFindIntroduce"));
                    var18_129 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleFindKind"));
                    var19_130 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleFindNoteUrl"));
                    var20_131 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleFindCoverUrl"));
                    var21_132 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleFindLastChapter"));
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setRuleExplore(new ExploreRule((String)var13_79, var15_122, var16_124, var17_127, var18_129, var21_132, null, var19_130, var20_131, null, 576, null));
                    var13_79 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleBookInfoInit"));
                    var15_122 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleBookName"));
                    var16_124 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleBookAuthor"));
                    var17_127 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleIntroduce"));
                    var18_129 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleBookKind"));
                    var19_130 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleCoverUrl"));
                    var20_131 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleBookLastChapter"));
                    var21_132 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleChapterUrl"));
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setRuleBookInfo(new BookInfoRule((String)var13_79, var15_122, var16_124, var17_127, var18_129, var20_131, null, var19_130, var21_132, null, null, 1600, null));
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setRuleToc(new TocRule(null, SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleChapterList")), SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleChapterName")), SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleContentUrl")), null, null, null, SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleChapterUrlNext")), 113, null));
                    var15_122 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleBookContent"));
                    v3 = content = var15_122 == null ? "" : var15_122;
                    if (StringsKt.startsWith$default(content, "$", false, 2, null) && !StringsKt.startsWith$default(content, "$.", false, 2, null)) {
                        var15_122 = content;
                        var16_125 = 1;
                        var17_128 = false;
                        v4 = var15_122.substring(var16_125);
                        Intrinsics.checkNotNullExpressionValue(v4, "(this as java.lang.String).substring(startIndex)");
                        content = v4;
                    }
                    var15_122 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleBookContentReplace"));
                    var16_126 = SourceAnalyzer.INSTANCE.toNewRule(JsonExtensionsKt.readString(jsonItem, "ruleContentUrlNext"));
                    $this$jsonToBookSource_IoAF18A_u24lambda_u2d10_u24lambda_u2d9.setRuleContent(new ContentRule(content, var16_126, null, null, var15_122, null, 44, null));
                    break block86;
                }
                source.setBookSourceUrl(sourceAny.getBookSourceUrl());
                source.setBookSourceName(sourceAny.getBookSourceName());
                source.setBookSourceGroup(sourceAny.getBookSourceGroup());
                source.setBookSourceType(sourceAny.getBookSourceType());
                source.setBookUrlPattern(sourceAny.getBookUrlPattern());
                source.setCustomOrder(sourceAny.getCustomOrder());
                source.setEnabled(sourceAny.getEnabled());
                source.setEnabledExplore(sourceAny.getEnabledExplore());
                source.setEnabledCookieJar(sourceAny.getEnabledCookieJar());
                source.setConcurrentRate(sourceAny.getConcurrentRate());
                source.setHeader(sourceAny.getHeader());
                var7_14 = sourceAny.getLoginUrl();
                if (var7_14 == null) {
                    v5 = null;
                } else if (var7_14 instanceof String) {
                    v5 = String.valueOf(sourceAny.getLoginUrl());
                } else {
                    var8_20 = JsonPath.parse(sourceAny.getLoginUrl());
                    Intrinsics.checkNotNullExpressionValue(var8_20, "parse(sourceAny.loginUrl)");
                    v5 = JsonExtensionsKt.readString(var8_20, "url");
                }
                source.setLoginUrl(v5);
                source.setLoginCheckJs(sourceAny.getLoginCheckJs());
                source.setBookSourceComment(sourceAny.getBookSourceComment());
                source.setLastUpdateTime(sourceAny.getLastUpdateTime());
                source.setRespondTime(sourceAny.getRespondTime());
                source.setWeight(sourceAny.getWeight());
                source.setExploreUrl(sourceAny.getExploreUrl());
                v6 = source;
                if (sourceAny.getRuleExplore() instanceof String) {
                    var7_14 = GsonExtensionsKt.getGSON();
                    var8_21 = String.valueOf(sourceAny.getRuleExplore());
                    var22_133 = v6;
                    $i$f$fromJsonObject = false;
                    var10_47 = false;
                    try {
                        var11_48 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                        $i$f$genericType = false;
                        var14_101 = new TypeToken<ExploreRule>(){}.getType();
                        Intrinsics.checkNotNullExpressionValue(var14_101, "object : TypeToken<T>() {}.type");
                        v7 = $this$fromJsonObject$iv.fromJson((String)json$iv, var14_101);
                        if (!(v7 instanceof ExploreRule)) {
                            v7 = null;
                        }
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (ExploreRule)v7;
                        $i$f$genericType = false;
                        var11_48 = Result.constructor-impl($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
                    }
                    catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
                        $i$f$genericType = Result.Companion;
                        var14_102 = false;
                        var11_48 = Result.constructor-impl(ResultKt.createFailure($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
                    }
                    var23_134 = var11_48;
                    v8 = var22_133;
                    $this$fromJsonObject$iv = var23_134;
                    json$iv = false;
                    v9 = (ExploreRule)(Result.isFailure-impl($this$fromJsonObject$iv) != false ? null : $this$fromJsonObject$iv);
                } else {
                    $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
                    json$iv = GsonExtensionsKt.getGSON().toJson(sourceAny.getRuleExplore());
                    var22_133 = v6;
                    $i$f$fromJsonObject = false;
                    var10_47 = false;
                    try {
                        var11_48 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                        $i$f$genericType = false;
                        var14_103 = new TypeToken<ExploreRule>(){}.getType();
                        Intrinsics.checkNotNullExpressionValue(var14_103, "object : TypeToken<T>() {}.type");
                        v10 = $this$fromJsonObject$iv.fromJson(json$iv, var14_103);
                        if (!(v10 instanceof ExploreRule)) {
                            v10 = null;
                        }
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (ExploreRule)v10;
                        $i$f$genericType = false;
                        var11_48 = Result.constructor-impl($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
                    }
                    catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
                        $i$f$genericType = Result.Companion;
                        var14_104 = false;
                        var11_48 = Result.constructor-impl(ResultKt.createFailure($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
                    }
                    var23_134 = var11_48;
                    v8 = var22_133;
                    $this$fromJsonObject$iv = var23_134;
                    json$iv = false;
                    v9 = (ExploreRule)(Result.isFailure-impl($this$fromJsonObject$iv) != false ? null : $this$fromJsonObject$iv);
                }
                v8.setRuleExplore(v9);
                source.setSearchUrl(sourceAny.getSearchUrl());
                v11 = source;
                if (sourceAny.getRuleSearch() instanceof String) {
                    $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
                    json$iv = String.valueOf(sourceAny.getRuleSearch());
                    var22_133 = v11;
                    $i$f$fromJsonObject = false;
                    var10_47 = false;
                    try {
                        var11_48 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                        $i$f$genericType = false;
                        var14_105 = new TypeToken<SearchRule>(){}.getType();
                        Intrinsics.checkNotNullExpressionValue(var14_105, "object : TypeToken<T>() {}.type");
                        v12 = $this$fromJsonObject$iv.fromJson(json$iv, var14_105);
                        if (!(v12 instanceof SearchRule)) {
                            v12 = null;
                        }
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (SearchRule)v12;
                        $i$f$genericType = false;
                        var11_48 = Result.constructor-impl($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
                    }
                    catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
                        $i$f$genericType = Result.Companion;
                        var14_106 = false;
                        var11_48 = Result.constructor-impl(ResultKt.createFailure($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
                    }
                    var23_134 = var11_48;
                    v13 = var22_133;
                    $this$fromJsonObject$iv = var23_134;
                    json$iv = false;
                    v14 = (SearchRule)(Result.isFailure-impl($this$fromJsonObject$iv) != false ? null : $this$fromJsonObject$iv);
                } else {
                    $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
                    json$iv = GsonExtensionsKt.getGSON().toJson(sourceAny.getRuleSearch());
                    var22_133 = v11;
                    $i$f$fromJsonObject = false;
                    var10_47 = false;
                    try {
                        var11_48 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                        $i$f$genericType = false;
                        var14_107 = new TypeToken<SearchRule>(){}.getType();
                        Intrinsics.checkNotNullExpressionValue(var14_107, "object : TypeToken<T>() {}.type");
                        v15 = $this$fromJsonObject$iv.fromJson(json$iv, var14_107);
                        if (!(v15 instanceof SearchRule)) {
                            v15 = null;
                        }
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (SearchRule)v15;
                        $i$f$genericType = false;
                        var11_48 = Result.constructor-impl($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
                    }
                    catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
                        $i$f$genericType = Result.Companion;
                        var14_108 = false;
                        var11_48 = Result.constructor-impl(ResultKt.createFailure($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
                    }
                    var23_134 = var11_48;
                    v13 = var22_133;
                    $this$fromJsonObject$iv = var23_134;
                    json$iv = false;
                    v14 = (SearchRule)(Result.isFailure-impl($this$fromJsonObject$iv) != false ? null : $this$fromJsonObject$iv);
                }
                v13.setRuleSearch(v14);
                v16 = source;
                if (sourceAny.getRuleBookInfo() instanceof String) {
                    $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
                    json$iv = String.valueOf(sourceAny.getRuleBookInfo());
                    var22_133 = v16;
                    $i$f$fromJsonObject = false;
                    var10_47 = false;
                    try {
                        var11_48 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                        $i$f$genericType = false;
                        var14_109 = new TypeToken<BookInfoRule>(){}.getType();
                        Intrinsics.checkNotNullExpressionValue(var14_109, "object : TypeToken<T>() {}.type");
                        v17 = $this$fromJsonObject$iv.fromJson(json$iv, var14_109);
                        if (!(v17 instanceof BookInfoRule)) {
                            v17 = null;
                        }
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (BookInfoRule)v17;
                        $i$f$genericType = false;
                        var11_48 = Result.constructor-impl($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
                    }
                    catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
                        $i$f$genericType = Result.Companion;
                        var14_110 = false;
                        var11_48 = Result.constructor-impl(ResultKt.createFailure($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
                    }
                    var23_134 = var11_48;
                    v18 = var22_133;
                    $this$fromJsonObject$iv = var23_134;
                    json$iv = false;
                    v19 = (BookInfoRule)(Result.isFailure-impl($this$fromJsonObject$iv) != false ? null : $this$fromJsonObject$iv);
                } else {
                    $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
                    json$iv = GsonExtensionsKt.getGSON().toJson(sourceAny.getRuleBookInfo());
                    var22_133 = v16;
                    $i$f$fromJsonObject = false;
                    var10_47 = false;
                    try {
                        var11_48 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                        $i$f$genericType = false;
                        var14_111 = new TypeToken<BookInfoRule>(){}.getType();
                        Intrinsics.checkNotNullExpressionValue(var14_111, "object : TypeToken<T>() {}.type");
                        v20 = $this$fromJsonObject$iv.fromJson(json$iv, var14_111);
                        if (!(v20 instanceof BookInfoRule)) {
                            v20 = null;
                        }
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (BookInfoRule)v20;
                        $i$f$genericType = false;
                        var11_48 = Result.constructor-impl($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
                    }
                    catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
                        $i$f$genericType = Result.Companion;
                        var14_112 = false;
                        var11_48 = Result.constructor-impl(ResultKt.createFailure($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
                    }
                    var23_134 = var11_48;
                    v18 = var22_133;
                    $this$fromJsonObject$iv = var23_134;
                    json$iv = false;
                    v19 = (BookInfoRule)(Result.isFailure-impl($this$fromJsonObject$iv) != false ? null : $this$fromJsonObject$iv);
                }
                v18.setRuleBookInfo(v19);
                v21 = source;
                if (sourceAny.getRuleToc() instanceof String) {
                    $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
                    json$iv = String.valueOf(sourceAny.getRuleToc());
                    var22_133 = v21;
                    $i$f$fromJsonObject = false;
                    var10_47 = false;
                    try {
                        var11_48 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                        $i$f$genericType = false;
                        var14_113 = new TypeToken<TocRule>(){}.getType();
                        Intrinsics.checkNotNullExpressionValue(var14_113, "object : TypeToken<T>() {}.type");
                        v22 = $this$fromJsonObject$iv.fromJson(json$iv, var14_113);
                        if (!(v22 instanceof TocRule)) {
                            v22 = null;
                        }
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (TocRule)v22;
                        $i$f$genericType = false;
                        var11_48 = Result.constructor-impl($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
                    }
                    catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
                        $i$f$genericType = Result.Companion;
                        var14_114 = false;
                        var11_48 = Result.constructor-impl(ResultKt.createFailure($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
                    }
                    var23_134 = var11_48;
                    v23 = var22_133;
                    $this$fromJsonObject$iv = var23_134;
                    json$iv = false;
                    v24 = (TocRule)(Result.isFailure-impl($this$fromJsonObject$iv) != false ? null : $this$fromJsonObject$iv);
                } else {
                    $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
                    json$iv = GsonExtensionsKt.getGSON().toJson(sourceAny.getRuleToc());
                    var22_133 = v21;
                    $i$f$fromJsonObject = false;
                    var10_47 = false;
                    try {
                        var11_48 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                        $i$f$genericType = false;
                        var14_115 = new TypeToken<TocRule>(){}.getType();
                        Intrinsics.checkNotNullExpressionValue(var14_115, "object : TypeToken<T>() {}.type");
                        v25 = $this$fromJsonObject$iv.fromJson(json$iv, var14_115);
                        if (!(v25 instanceof TocRule)) {
                            v25 = null;
                        }
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (TocRule)v25;
                        $i$f$genericType = false;
                        var11_48 = Result.constructor-impl($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
                    }
                    catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
                        $i$f$genericType = Result.Companion;
                        var14_116 = false;
                        var11_48 = Result.constructor-impl(ResultKt.createFailure($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
                    }
                    var23_134 = var11_48;
                    v23 = var22_133;
                    $this$fromJsonObject$iv = var23_134;
                    json$iv = false;
                    v24 = (TocRule)(Result.isFailure-impl($this$fromJsonObject$iv) != false ? null : $this$fromJsonObject$iv);
                }
                v23.setRuleToc(v24);
                v26 = source;
                if (sourceAny.getRuleContent() instanceof String) {
                    $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
                    json$iv = String.valueOf(sourceAny.getRuleContent());
                    var22_133 = v26;
                    $i$f$fromJsonObject = false;
                    var10_47 = false;
                    try {
                        var11_48 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                        $i$f$genericType = false;
                        var14_117 = new TypeToken<ContentRule>(){}.getType();
                        Intrinsics.checkNotNullExpressionValue(var14_117, "object : TypeToken<T>() {}.type");
                        v27 = $this$fromJsonObject$iv.fromJson(json$iv, var14_117);
                        if (!(v27 instanceof ContentRule)) {
                            v27 = null;
                        }
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (ContentRule)v27;
                        $i$f$genericType = false;
                        var11_48 = Result.constructor-impl($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
                    }
                    catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
                        $i$f$genericType = Result.Companion;
                        var14_118 = false;
                        var11_48 = Result.constructor-impl(ResultKt.createFailure($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
                    }
                    var23_134 = var11_48;
                    v28 = var22_133;
                    $this$fromJsonObject$iv = var23_134;
                    json$iv = false;
                    v29 = (ContentRule)(Result.isFailure-impl($this$fromJsonObject$iv) != false ? null : $this$fromJsonObject$iv);
                } else {
                    $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
                    json$iv = GsonExtensionsKt.getGSON().toJson(sourceAny.getRuleContent());
                    var22_133 = v26;
                    $i$f$fromJsonObject = false;
                    var10_47 = false;
                    try {
                        var11_48 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                        $i$f$genericType = false;
                        var14_119 = new TypeToken<ContentRule>(){}.getType();
                        Intrinsics.checkNotNullExpressionValue(var14_119, "object : TypeToken<T>() {}.type");
                        v30 = $this$fromJsonObject$iv.fromJson(json$iv, var14_119);
                        if (!(v30 instanceof ContentRule)) {
                            v30 = null;
                        }
                        var12_75 = (ContentRule)v30;
                        var13_98 = false;
                        var11_48 = Result.constructor-impl(var12_75);
                    }
                    catch (Throwable var12_76) {
                        var13_99 = Result.Companion;
                        var14_120 = false;
                        var11_48 = Result.constructor-impl(ResultKt.createFailure(var12_76));
                    }
                    var23_134 = var11_48;
                    v28 = var22_133;
                    var7_14 = var23_134;
                    var8_36 = false;
                    v29 = (ContentRule)(Result.isFailure-impl(var7_14) != false ? null : var7_14);
                }
                v28.setRuleContent(v29);
            }
            var6_11 = source;
            var7_15 = false;
            var5_7 = Result.constructor-impl(var6_11);
        }
        catch (Throwable var6_12) {
            var7_16 = Result.Companion;
            var8_37 = false;
            var5_7 = Result.constructor-impl(ResultKt.createFailure(var6_12));
        }
        return var5_7;
    }

    private final String toNewRule(String oldRule) {
        int n;
        int n2;
        String string;
        CharSequence charSequence = oldRule;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence == null || StringsKt.isBlank(charSequence)) {
            return null;
        }
        String newRule = oldRule;
        boolean reverse = false;
        boolean allinone = false;
        if (StringsKt.startsWith$default(oldRule, "-", false, 2, null)) {
            reverse = true;
            string = oldRule;
            n2 = 1;
            n = 0;
            String string2 = string;
            if (string2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string3 = string2.substring(n2);
            Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.String).substring(startIndex)");
            newRule = string3;
        }
        if (StringsKt.startsWith$default(newRule, "+", false, 2, null)) {
            allinone = true;
            string = newRule;
            n2 = 1;
            n = 0;
            String string4 = string;
            if (string4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string5 = string4.substring(n2);
            Intrinsics.checkNotNullExpressionValue(string5, "(this as java.lang.String).substring(startIndex)");
            newRule = string5;
        }
        if (!(StringsKt.startsWith(newRule, "@CSS:", true) || StringsKt.startsWith(newRule, "@XPath:", true) || StringsKt.startsWith$default(newRule, "//", false, 2, null) || StringsKt.startsWith$default(newRule, "##", false, 2, null) || StringsKt.startsWith$default(newRule, ":", false, 2, null) || StringsKt.contains((CharSequence)newRule, "@js:", true) || StringsKt.contains((CharSequence)newRule, "<js>", true))) {
            if (StringsKt.contains$default((CharSequence)newRule, "#", false, 2, null) && !StringsKt.contains$default((CharSequence)newRule, "##", false, 2, null)) {
                newRule = StringsKt.replace$default(oldRule, "#", "##", false, 4, null);
            }
            if (StringsKt.contains$default((CharSequence)newRule, "|", false, 2, null) && !StringsKt.contains$default((CharSequence)newRule, "||", false, 2, null)) {
                if (StringsKt.contains$default((CharSequence)newRule, "##", false, 2, null)) {
                    String[] stringArray = new String[]{"##"};
                    List list2 = StringsKt.split$default((CharSequence)newRule, stringArray, false, 0, 6, null);
                    if (StringsKt.contains$default((CharSequence)list2.get(0), "|", false, 2, null)) {
                        newRule = StringsKt.replace$default((String)list2.get(0), "|", "||", false, 4, null);
                        int n3 = 1;
                        n = list2.size();
                        if (n3 < n) {
                            do {
                                int i = n3++;
                                newRule = newRule + "##" + (String)list2.get(i);
                            } while (n3 < n);
                        }
                    }
                } else {
                    newRule = StringsKt.replace$default(newRule, "|", "||", false, 4, null);
                }
            }
            if (StringsKt.contains$default((CharSequence)newRule, "&", false, 2, null) && !StringsKt.contains$default((CharSequence)newRule, "&&", false, 2, null) && !StringsKt.contains$default((CharSequence)newRule, "http", false, 2, null) && !StringsKt.startsWith$default(newRule, "/", false, 2, null)) {
                newRule = StringsKt.replace$default(newRule, "&", "&&", false, 4, null);
            }
        }
        if (allinone) {
            newRule = Intrinsics.stringPlus("+", newRule);
        }
        if (reverse) {
            newRule = Intrinsics.stringPlus("-", newRule);
        }
        return newRule;
    }

    /*
     * WARNING - void declaration
     */
    private final String toNewUrls(String oldUrls) {
        void $this$mapTo$iv$iv;
        CharSequence charSequence = oldUrls;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence == null || StringsKt.isBlank(charSequence)) {
            return null;
        }
        if (StringsKt.startsWith$default(oldUrls, "@js:", false, 2, null) || StringsKt.startsWith$default(oldUrls, "<js>", false, 2, null)) {
            return oldUrls;
        }
        if (!StringsKt.contains$default((CharSequence)oldUrls, "\n", false, 2, null) && !StringsKt.contains$default((CharSequence)oldUrls, "&&", false, 2, null)) {
            return this.toNewUrl(oldUrls);
        }
        CharSequence charSequence2 = oldUrls;
        Object object = "(&&|\r?\n)+";
        int n = 0;
        object = new Regex((String)object);
        n = 0;
        boolean bl3 = false;
        List<String> urls = ((Regex)object).split(charSequence2, n);
        Iterable $this$map$iv = urls;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            String string;
            void it;
            String string2 = (String)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl4 = false;
            String string3 = INSTANCE.toNewUrl((String)it);
            if (string3 == null) {
                string = null;
            } else {
                CharSequence charSequence3 = string3;
                Object object2 = "\n\\s*";
                boolean bl5 = false;
                object2 = new Regex((String)object2);
                String string4 = "";
                boolean bl6 = false;
                string = ((Regex)object2).replace(charSequence3, string4);
            }
            String string5 = string;
            collection.add(string5);
        }
        return CollectionsKt.joinToString$default((List)destination$iv$iv, "\n", null, null, 0, null, null, 62, null);
    }

    private final String toNewUrl(String oldUrl) {
        boolean bl;
        int n;
        Object object;
        String string;
        Object object2;
        CharSequence charSequence = oldUrl;
        boolean bl2 = false;
        boolean bl3 = false;
        if (charSequence == null || StringsKt.isBlank(charSequence)) {
            return null;
        }
        String url2 = oldUrl;
        if (StringsKt.startsWith(oldUrl, "<js>", true)) {
            url2 = StringsKt.replace$default(StringsKt.replace$default(url2, "=searchKey", "={{key}}", false, 4, null), "=searchPage", "={{page}}", false, 4, null);
            return url2;
        }
        HashMap map = new HashMap();
        Matcher mather = headerPattern.matcher(url2);
        if (mather.find()) {
            String[] header = mather.group();
            Intrinsics.checkNotNullExpressionValue(header, "header");
            url2 = StringsKt.replace$default(url2, (String)header, "", false, 4, null);
            object2 = map;
            string = "headers";
            object = header;
            n = 8;
            bl = false;
            String string2 = ((String)object).substring(n);
            Intrinsics.checkNotNullExpressionValue(string2, "(this as java.lang.String).substring(startIndex)");
            object = string2;
            n = 0;
            object2.put(string, object);
        }
        object2 = new String[]{"|"};
        List urlList = StringsKt.split$default((CharSequence)url2, (String[])object2, false, 0, 6, null);
        url2 = (String)urlList.get(0);
        if (urlList.size() > 1) {
            object2 = map;
            string = "charset";
            object = new String[]{"="};
            object = StringsKt.split$default((CharSequence)urlList.get(1), (String[])object, false, 0, 6, null).get(1);
            n = 0;
            object2.put(string, object);
        }
        mather = jsPattern.matcher(url2);
        boolean bl4 = false;
        ArrayList<String> jsList = new ArrayList<String>();
        while (mather.find()) {
            jsList.add(mather.group());
            url2 = StringsKt.replace$default(url2, (String)CollectionsKt.last((List)jsList), Intrinsics.stringPlus("$", jsList.size() - 1), false, 4, null);
        }
        url2 = StringsKt.replace$default(StringsKt.replace$default(url2, "{", "<", false, 4, null), "}", ">", false, 4, null);
        url2 = StringsKt.replace$default(url2, "searchKey", "{{key}}", false, 4, null);
        Object object3 = url2;
        object = "<searchPage([-+]1)>";
        n = 0;
        object = new Regex((String)object);
        String string3 = "{{page$1}}";
        bl = false;
        object3 = ((Regex)object).replace((CharSequence)object3, string3);
        object = "searchPage([-+]1)";
        boolean bl5 = false;
        object = new Regex((String)object);
        String string4 = "{{page$1}}";
        bl = false;
        url2 = StringsKt.replace$default(((Regex)object).replace((CharSequence)object3, string4), "searchPage", "{{page}}", false, 4, null);
        object3 = jsList.iterator();
        int n2 = 0;
        while (object3.hasNext()) {
            int index = n2++;
            String item = (String)object3.next();
            url2 = StringsKt.replace$default(url2, Intrinsics.stringPlus("$", index), StringsKt.replace$default(StringsKt.replace$default(item, "searchKey", "key", false, 4, null), "searchPage", "page", false, 4, null), false, 4, null);
        }
        object3 = new String[]{"@"};
        urlList = StringsKt.split$default((CharSequence)url2, (String[])object3, false, 0, 6, null);
        url2 = (String)urlList.get(0);
        if (urlList.size() > 1) {
            object3 = map;
            String string5 = "method";
            String string6 = "POST";
            bl = false;
            object3.put(string5, string6);
            object3 = map;
            string5 = "body";
            string6 = urlList.get(1);
            bl = false;
            object3.put(string5, string6);
        }
        if (map.size() > 0) {
            url2 = url2 + ',' + GsonExtensionsKt.getGSON().toJson(map);
        }
        return url2;
    }

    private final String uaToHeader(String ua) {
        CharSequence charSequence = ua;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence == null || charSequence.length() == 0) {
            return null;
        }
        Map<String, String> map = MapsKt.mapOf(new Pair<String, String>("User-Agent", ua));
        return GsonExtensionsKt.getGSON().toJson(map);
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\t\n\u0002\be\b\u0086\b\u0018\u00002\u00020\u0001B\u009d\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0007\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u0012\b\b\u0002\u0010\r\u001a\u00020\u000b\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0015\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0015\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0007\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u0001\u00a2\u0006\u0002\u0010\u001fJ\t\u0010\\\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010]\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010^\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010_\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003J\u000b\u0010`\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003J\u000b\u0010a\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010b\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010c\u001a\u00020\u0015H\u00c6\u0003J\t\u0010d\u001a\u00020\u0015H\u00c6\u0003J\t\u0010e\u001a\u00020\u0007H\u00c6\u0003J\u000b\u0010f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010g\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010h\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003J\u000b\u0010i\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010j\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003J\u000b\u0010k\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003J\u000b\u0010l\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003J\u000b\u0010m\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003J\t\u0010n\u001a\u00020\u0003H\u00c6\u0003J\t\u0010o\u001a\u00020\u0007H\u00c6\u0003J\u000b\u0010p\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010q\u001a\u00020\u0007H\u00c6\u0003J\t\u0010r\u001a\u00020\u000bH\u00c6\u0003J\t\u0010s\u001a\u00020\u000bH\u00c6\u0003J\t\u0010t\u001a\u00020\u000bH\u00c6\u0003J\u00a1\u0002\u0010u\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000b2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00152\b\b\u0002\u0010\u0017\u001a\u00020\u00072\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00c6\u0001J\u0013\u0010v\u001a\u00020\u000b2\b\u0010w\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010x\u001a\u00020\u0007H\u00d6\u0001J\t\u0010y\u001a\u00020\u0003H\u00d6\u0001R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010!\"\u0004\b%\u0010#R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010!\"\u0004\b'\u0010#R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010!\"\u0004\b-\u0010#R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b.\u0010!\"\u0004\b/\u0010#R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u0010!\"\u0004\b1\u0010#R\u001a\u0010\t\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b2\u0010)\"\u0004\b3\u0010+R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\u001a\u0010\r\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b8\u00105\"\u0004\b9\u00107R\u001a\u0010\f\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b:\u00105\"\u0004\b;\u00107R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b<\u0010!\"\u0004\b=\u0010#R\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b>\u0010!\"\u0004\b?\u0010#R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b@\u0010A\"\u0004\bB\u0010CR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bD\u0010!\"\u0004\bE\u0010#R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0001X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bF\u0010G\"\u0004\bH\u0010IR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0001X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010G\"\u0004\bK\u0010IR\u001a\u0010\u0016\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bL\u0010A\"\u0004\bM\u0010CR\u001c\u0010\u001c\u001a\u0004\u0018\u00010\u0001X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bN\u0010G\"\u0004\bO\u0010IR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0001X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bP\u0010G\"\u0004\bQ\u0010IR\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u0001X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bR\u0010G\"\u0004\bS\u0010IR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0001X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bT\u0010G\"\u0004\bU\u0010IR\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u0001X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bV\u0010G\"\u0004\bW\u0010IR\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bX\u0010!\"\u0004\bY\u0010#R\u001a\u0010\u0017\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bZ\u0010)\"\u0004\b[\u0010+\u00a8\u0006z"}, d2={"Lio/legado/app/help/SourceAnalyzer$BookSourceAny;", "", "bookSourceName", "", "bookSourceGroup", "bookSourceUrl", "bookSourceType", "", "bookUrlPattern", "customOrder", "enabled", "", "enabledExplore", "enabledCookieJar", "concurrentRate", "header", "loginUrl", "loginUi", "loginCheckJs", "bookSourceComment", "lastUpdateTime", "", "respondTime", "weight", "exploreUrl", "ruleExplore", "searchUrl", "ruleSearch", "ruleBookInfo", "ruleToc", "ruleContent", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;IZZZLjava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;JJILjava/lang/String;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", "getBookSourceComment", "()Ljava/lang/String;", "setBookSourceComment", "(Ljava/lang/String;)V", "getBookSourceGroup", "setBookSourceGroup", "getBookSourceName", "setBookSourceName", "getBookSourceType", "()I", "setBookSourceType", "(I)V", "getBookSourceUrl", "setBookSourceUrl", "getBookUrlPattern", "setBookUrlPattern", "getConcurrentRate", "setConcurrentRate", "getCustomOrder", "setCustomOrder", "getEnabled", "()Z", "setEnabled", "(Z)V", "getEnabledCookieJar", "setEnabledCookieJar", "getEnabledExplore", "setEnabledExplore", "getExploreUrl", "setExploreUrl", "getHeader", "setHeader", "getLastUpdateTime", "()J", "setLastUpdateTime", "(J)V", "getLoginCheckJs", "setLoginCheckJs", "getLoginUi", "()Ljava/lang/Object;", "setLoginUi", "(Ljava/lang/Object;)V", "getLoginUrl", "setLoginUrl", "getRespondTime", "setRespondTime", "getRuleBookInfo", "setRuleBookInfo", "getRuleContent", "setRuleContent", "getRuleExplore", "setRuleExplore", "getRuleSearch", "setRuleSearch", "getRuleToc", "setRuleToc", "getSearchUrl", "setSearchUrl", "getWeight", "setWeight", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "reader-pro"})
    public static final class BookSourceAny {
        @NotNull
        private String bookSourceName;
        @Nullable
        private String bookSourceGroup;
        @NotNull
        private String bookSourceUrl;
        private int bookSourceType;
        @Nullable
        private String bookUrlPattern;
        private int customOrder;
        private boolean enabled;
        private boolean enabledExplore;
        private boolean enabledCookieJar;
        @Nullable
        private String concurrentRate;
        @Nullable
        private String header;
        @Nullable
        private Object loginUrl;
        @Nullable
        private Object loginUi;
        @Nullable
        private String loginCheckJs;
        @Nullable
        private String bookSourceComment;
        private long lastUpdateTime;
        private long respondTime;
        private int weight;
        @Nullable
        private String exploreUrl;
        @Nullable
        private Object ruleExplore;
        @Nullable
        private String searchUrl;
        @Nullable
        private Object ruleSearch;
        @Nullable
        private Object ruleBookInfo;
        @Nullable
        private Object ruleToc;
        @Nullable
        private Object ruleContent;

        public BookSourceAny(@NotNull String bookSourceName, @Nullable String bookSourceGroup, @NotNull String bookSourceUrl, int bookSourceType, @Nullable String bookUrlPattern, int customOrder, boolean enabled, boolean enabledExplore, boolean enabledCookieJar, @Nullable String concurrentRate, @Nullable String header, @Nullable Object loginUrl, @Nullable Object loginUi, @Nullable String loginCheckJs, @Nullable String bookSourceComment, long lastUpdateTime, long respondTime, int weight, @Nullable String exploreUrl, @Nullable Object ruleExplore, @Nullable String searchUrl, @Nullable Object ruleSearch, @Nullable Object ruleBookInfo, @Nullable Object ruleToc, @Nullable Object ruleContent) {
            Intrinsics.checkNotNullParameter(bookSourceName, "bookSourceName");
            Intrinsics.checkNotNullParameter(bookSourceUrl, "bookSourceUrl");
            this.bookSourceName = bookSourceName;
            this.bookSourceGroup = bookSourceGroup;
            this.bookSourceUrl = bookSourceUrl;
            this.bookSourceType = bookSourceType;
            this.bookUrlPattern = bookUrlPattern;
            this.customOrder = customOrder;
            this.enabled = enabled;
            this.enabledExplore = enabledExplore;
            this.enabledCookieJar = enabledCookieJar;
            this.concurrentRate = concurrentRate;
            this.header = header;
            this.loginUrl = loginUrl;
            this.loginUi = loginUi;
            this.loginCheckJs = loginCheckJs;
            this.bookSourceComment = bookSourceComment;
            this.lastUpdateTime = lastUpdateTime;
            this.respondTime = respondTime;
            this.weight = weight;
            this.exploreUrl = exploreUrl;
            this.ruleExplore = ruleExplore;
            this.searchUrl = searchUrl;
            this.ruleSearch = ruleSearch;
            this.ruleBookInfo = ruleBookInfo;
            this.ruleToc = ruleToc;
            this.ruleContent = ruleContent;
        }

        public /* synthetic */ BookSourceAny(String string, String string2, String string3, int n, String string4, int n2, boolean bl, boolean bl2, boolean bl3, String string5, String string6, Object object, Object object2, String string7, String string8, long l, long l2, int n3, String string9, Object object3, String string10, Object object4, Object object5, Object object6, Object object7, int n4, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n4 & 1) != 0) {
                string = "";
            }
            if ((n4 & 2) != 0) {
                string2 = null;
            }
            if ((n4 & 4) != 0) {
                string3 = "";
            }
            if ((n4 & 8) != 0) {
                n = 0;
            }
            if ((n4 & 0x10) != 0) {
                string4 = null;
            }
            if ((n4 & 0x20) != 0) {
                n2 = 0;
            }
            if ((n4 & 0x40) != 0) {
                bl = true;
            }
            if ((n4 & 0x80) != 0) {
                bl2 = true;
            }
            if ((n4 & 0x100) != 0) {
                bl3 = false;
            }
            if ((n4 & 0x200) != 0) {
                string5 = null;
            }
            if ((n4 & 0x400) != 0) {
                string6 = null;
            }
            if ((n4 & 0x800) != 0) {
                object = null;
            }
            if ((n4 & 0x1000) != 0) {
                object2 = null;
            }
            if ((n4 & 0x2000) != 0) {
                string7 = null;
            }
            if ((n4 & 0x4000) != 0) {
                string8 = "";
            }
            if ((n4 & 0x8000) != 0) {
                l = 0L;
            }
            if ((n4 & 0x10000) != 0) {
                l2 = 180000L;
            }
            if ((n4 & 0x20000) != 0) {
                n3 = 0;
            }
            if ((n4 & 0x40000) != 0) {
                string9 = null;
            }
            if ((n4 & 0x80000) != 0) {
                object3 = null;
            }
            if ((n4 & 0x100000) != 0) {
                string10 = null;
            }
            if ((n4 & 0x200000) != 0) {
                object4 = null;
            }
            if ((n4 & 0x400000) != 0) {
                object5 = null;
            }
            if ((n4 & 0x800000) != 0) {
                object6 = null;
            }
            if ((n4 & 0x1000000) != 0) {
                object7 = null;
            }
            this(string, string2, string3, n, string4, n2, bl, bl2, bl3, string5, string6, object, object2, string7, string8, l, l2, n3, string9, object3, string10, object4, object5, object6, object7);
        }

        @NotNull
        public final String getBookSourceName() {
            return this.bookSourceName;
        }

        public final void setBookSourceName(@NotNull String string) {
            Intrinsics.checkNotNullParameter(string, "<set-?>");
            this.bookSourceName = string;
        }

        @Nullable
        public final String getBookSourceGroup() {
            return this.bookSourceGroup;
        }

        public final void setBookSourceGroup(@Nullable String string) {
            this.bookSourceGroup = string;
        }

        @NotNull
        public final String getBookSourceUrl() {
            return this.bookSourceUrl;
        }

        public final void setBookSourceUrl(@NotNull String string) {
            Intrinsics.checkNotNullParameter(string, "<set-?>");
            this.bookSourceUrl = string;
        }

        public final int getBookSourceType() {
            return this.bookSourceType;
        }

        public final void setBookSourceType(int n) {
            this.bookSourceType = n;
        }

        @Nullable
        public final String getBookUrlPattern() {
            return this.bookUrlPattern;
        }

        public final void setBookUrlPattern(@Nullable String string) {
            this.bookUrlPattern = string;
        }

        public final int getCustomOrder() {
            return this.customOrder;
        }

        public final void setCustomOrder(int n) {
            this.customOrder = n;
        }

        public final boolean getEnabled() {
            return this.enabled;
        }

        public final void setEnabled(boolean bl) {
            this.enabled = bl;
        }

        public final boolean getEnabledExplore() {
            return this.enabledExplore;
        }

        public final void setEnabledExplore(boolean bl) {
            this.enabledExplore = bl;
        }

        public final boolean getEnabledCookieJar() {
            return this.enabledCookieJar;
        }

        public final void setEnabledCookieJar(boolean bl) {
            this.enabledCookieJar = bl;
        }

        @Nullable
        public final String getConcurrentRate() {
            return this.concurrentRate;
        }

        public final void setConcurrentRate(@Nullable String string) {
            this.concurrentRate = string;
        }

        @Nullable
        public final String getHeader() {
            return this.header;
        }

        public final void setHeader(@Nullable String string) {
            this.header = string;
        }

        @Nullable
        public final Object getLoginUrl() {
            return this.loginUrl;
        }

        public final void setLoginUrl(@Nullable Object object) {
            this.loginUrl = object;
        }

        @Nullable
        public final Object getLoginUi() {
            return this.loginUi;
        }

        public final void setLoginUi(@Nullable Object object) {
            this.loginUi = object;
        }

        @Nullable
        public final String getLoginCheckJs() {
            return this.loginCheckJs;
        }

        public final void setLoginCheckJs(@Nullable String string) {
            this.loginCheckJs = string;
        }

        @Nullable
        public final String getBookSourceComment() {
            return this.bookSourceComment;
        }

        public final void setBookSourceComment(@Nullable String string) {
            this.bookSourceComment = string;
        }

        public final long getLastUpdateTime() {
            return this.lastUpdateTime;
        }

        public final void setLastUpdateTime(long l) {
            this.lastUpdateTime = l;
        }

        public final long getRespondTime() {
            return this.respondTime;
        }

        public final void setRespondTime(long l) {
            this.respondTime = l;
        }

        public final int getWeight() {
            return this.weight;
        }

        public final void setWeight(int n) {
            this.weight = n;
        }

        @Nullable
        public final String getExploreUrl() {
            return this.exploreUrl;
        }

        public final void setExploreUrl(@Nullable String string) {
            this.exploreUrl = string;
        }

        @Nullable
        public final Object getRuleExplore() {
            return this.ruleExplore;
        }

        public final void setRuleExplore(@Nullable Object object) {
            this.ruleExplore = object;
        }

        @Nullable
        public final String getSearchUrl() {
            return this.searchUrl;
        }

        public final void setSearchUrl(@Nullable String string) {
            this.searchUrl = string;
        }

        @Nullable
        public final Object getRuleSearch() {
            return this.ruleSearch;
        }

        public final void setRuleSearch(@Nullable Object object) {
            this.ruleSearch = object;
        }

        @Nullable
        public final Object getRuleBookInfo() {
            return this.ruleBookInfo;
        }

        public final void setRuleBookInfo(@Nullable Object object) {
            this.ruleBookInfo = object;
        }

        @Nullable
        public final Object getRuleToc() {
            return this.ruleToc;
        }

        public final void setRuleToc(@Nullable Object object) {
            this.ruleToc = object;
        }

        @Nullable
        public final Object getRuleContent() {
            return this.ruleContent;
        }

        public final void setRuleContent(@Nullable Object object) {
            this.ruleContent = object;
        }

        @NotNull
        public final String component1() {
            return this.bookSourceName;
        }

        @Nullable
        public final String component2() {
            return this.bookSourceGroup;
        }

        @NotNull
        public final String component3() {
            return this.bookSourceUrl;
        }

        public final int component4() {
            return this.bookSourceType;
        }

        @Nullable
        public final String component5() {
            return this.bookUrlPattern;
        }

        public final int component6() {
            return this.customOrder;
        }

        public final boolean component7() {
            return this.enabled;
        }

        public final boolean component8() {
            return this.enabledExplore;
        }

        public final boolean component9() {
            return this.enabledCookieJar;
        }

        @Nullable
        public final String component10() {
            return this.concurrentRate;
        }

        @Nullable
        public final String component11() {
            return this.header;
        }

        @Nullable
        public final Object component12() {
            return this.loginUrl;
        }

        @Nullable
        public final Object component13() {
            return this.loginUi;
        }

        @Nullable
        public final String component14() {
            return this.loginCheckJs;
        }

        @Nullable
        public final String component15() {
            return this.bookSourceComment;
        }

        public final long component16() {
            return this.lastUpdateTime;
        }

        public final long component17() {
            return this.respondTime;
        }

        public final int component18() {
            return this.weight;
        }

        @Nullable
        public final String component19() {
            return this.exploreUrl;
        }

        @Nullable
        public final Object component20() {
            return this.ruleExplore;
        }

        @Nullable
        public final String component21() {
            return this.searchUrl;
        }

        @Nullable
        public final Object component22() {
            return this.ruleSearch;
        }

        @Nullable
        public final Object component23() {
            return this.ruleBookInfo;
        }

        @Nullable
        public final Object component24() {
            return this.ruleToc;
        }

        @Nullable
        public final Object component25() {
            return this.ruleContent;
        }

        @NotNull
        public final BookSourceAny copy(@NotNull String bookSourceName, @Nullable String bookSourceGroup, @NotNull String bookSourceUrl, int bookSourceType, @Nullable String bookUrlPattern, int customOrder, boolean enabled, boolean enabledExplore, boolean enabledCookieJar, @Nullable String concurrentRate, @Nullable String header, @Nullable Object loginUrl, @Nullable Object loginUi, @Nullable String loginCheckJs, @Nullable String bookSourceComment, long lastUpdateTime, long respondTime, int weight, @Nullable String exploreUrl, @Nullable Object ruleExplore, @Nullable String searchUrl, @Nullable Object ruleSearch, @Nullable Object ruleBookInfo, @Nullable Object ruleToc, @Nullable Object ruleContent) {
            Intrinsics.checkNotNullParameter(bookSourceName, "bookSourceName");
            Intrinsics.checkNotNullParameter(bookSourceUrl, "bookSourceUrl");
            return new BookSourceAny(bookSourceName, bookSourceGroup, bookSourceUrl, bookSourceType, bookUrlPattern, customOrder, enabled, enabledExplore, enabledCookieJar, concurrentRate, header, loginUrl, loginUi, loginCheckJs, bookSourceComment, lastUpdateTime, respondTime, weight, exploreUrl, ruleExplore, searchUrl, ruleSearch, ruleBookInfo, ruleToc, ruleContent);
        }

        public static /* synthetic */ BookSourceAny copy$default(BookSourceAny bookSourceAny, String string, String string2, String string3, int n, String string4, int n2, boolean bl, boolean bl2, boolean bl3, String string5, String string6, Object object, Object object2, String string7, String string8, long l, long l2, int n3, String string9, Object object3, String string10, Object object4, Object object5, Object object6, Object object7, int n4, Object object8) {
            if ((n4 & 1) != 0) {
                string = bookSourceAny.bookSourceName;
            }
            if ((n4 & 2) != 0) {
                string2 = bookSourceAny.bookSourceGroup;
            }
            if ((n4 & 4) != 0) {
                string3 = bookSourceAny.bookSourceUrl;
            }
            if ((n4 & 8) != 0) {
                n = bookSourceAny.bookSourceType;
            }
            if ((n4 & 0x10) != 0) {
                string4 = bookSourceAny.bookUrlPattern;
            }
            if ((n4 & 0x20) != 0) {
                n2 = bookSourceAny.customOrder;
            }
            if ((n4 & 0x40) != 0) {
                bl = bookSourceAny.enabled;
            }
            if ((n4 & 0x80) != 0) {
                bl2 = bookSourceAny.enabledExplore;
            }
            if ((n4 & 0x100) != 0) {
                bl3 = bookSourceAny.enabledCookieJar;
            }
            if ((n4 & 0x200) != 0) {
                string5 = bookSourceAny.concurrentRate;
            }
            if ((n4 & 0x400) != 0) {
                string6 = bookSourceAny.header;
            }
            if ((n4 & 0x800) != 0) {
                object = bookSourceAny.loginUrl;
            }
            if ((n4 & 0x1000) != 0) {
                object2 = bookSourceAny.loginUi;
            }
            if ((n4 & 0x2000) != 0) {
                string7 = bookSourceAny.loginCheckJs;
            }
            if ((n4 & 0x4000) != 0) {
                string8 = bookSourceAny.bookSourceComment;
            }
            if ((n4 & 0x8000) != 0) {
                l = bookSourceAny.lastUpdateTime;
            }
            if ((n4 & 0x10000) != 0) {
                l2 = bookSourceAny.respondTime;
            }
            if ((n4 & 0x20000) != 0) {
                n3 = bookSourceAny.weight;
            }
            if ((n4 & 0x40000) != 0) {
                string9 = bookSourceAny.exploreUrl;
            }
            if ((n4 & 0x80000) != 0) {
                object3 = bookSourceAny.ruleExplore;
            }
            if ((n4 & 0x100000) != 0) {
                string10 = bookSourceAny.searchUrl;
            }
            if ((n4 & 0x200000) != 0) {
                object4 = bookSourceAny.ruleSearch;
            }
            if ((n4 & 0x400000) != 0) {
                object5 = bookSourceAny.ruleBookInfo;
            }
            if ((n4 & 0x800000) != 0) {
                object6 = bookSourceAny.ruleToc;
            }
            if ((n4 & 0x1000000) != 0) {
                object7 = bookSourceAny.ruleContent;
            }
            return bookSourceAny.copy(string, string2, string3, n, string4, n2, bl, bl2, bl3, string5, string6, object, object2, string7, string8, l, l2, n3, string9, object3, string10, object4, object5, object6, object7);
        }

        @NotNull
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("BookSourceAny(bookSourceName=").append(this.bookSourceName).append(", bookSourceGroup=").append((Object)this.bookSourceGroup).append(", bookSourceUrl=").append(this.bookSourceUrl).append(", bookSourceType=").append(this.bookSourceType).append(", bookUrlPattern=").append((Object)this.bookUrlPattern).append(", customOrder=").append(this.customOrder).append(", enabled=").append(this.enabled).append(", enabledExplore=").append(this.enabledExplore).append(", enabledCookieJar=").append(this.enabledCookieJar).append(", concurrentRate=").append((Object)this.concurrentRate).append(", header=").append((Object)this.header).append(", loginUrl=");
            stringBuilder.append(this.loginUrl).append(", loginUi=").append(this.loginUi).append(", loginCheckJs=").append((Object)this.loginCheckJs).append(", bookSourceComment=").append((Object)this.bookSourceComment).append(", lastUpdateTime=").append(this.lastUpdateTime).append(", respondTime=").append(this.respondTime).append(", weight=").append(this.weight).append(", exploreUrl=").append((Object)this.exploreUrl).append(", ruleExplore=").append(this.ruleExplore).append(", searchUrl=").append((Object)this.searchUrl).append(", ruleSearch=").append(this.ruleSearch).append(", ruleBookInfo=").append(this.ruleBookInfo);
            stringBuilder.append(", ruleToc=").append(this.ruleToc).append(", ruleContent=").append(this.ruleContent).append(')');
            return stringBuilder.toString();
        }

        public int hashCode() {
            int result2 = this.bookSourceName.hashCode();
            result2 = result2 * 31 + (this.bookSourceGroup == null ? 0 : this.bookSourceGroup.hashCode());
            result2 = result2 * 31 + this.bookSourceUrl.hashCode();
            result2 = result2 * 31 + Integer.hashCode(this.bookSourceType);
            result2 = result2 * 31 + (this.bookUrlPattern == null ? 0 : this.bookUrlPattern.hashCode());
            result2 = result2 * 31 + Integer.hashCode(this.customOrder);
            int n = this.enabled ? 1 : 0;
            if (n != 0) {
                n = 1;
            }
            result2 = result2 * 31 + n;
            int n2 = this.enabledExplore ? 1 : 0;
            if (n2 != 0) {
                n2 = 1;
            }
            result2 = result2 * 31 + n2;
            int n3 = this.enabledCookieJar ? 1 : 0;
            if (n3 != 0) {
                n3 = 1;
            }
            result2 = result2 * 31 + n3;
            result2 = result2 * 31 + (this.concurrentRate == null ? 0 : this.concurrentRate.hashCode());
            result2 = result2 * 31 + (this.header == null ? 0 : this.header.hashCode());
            result2 = result2 * 31 + (this.loginUrl == null ? 0 : this.loginUrl.hashCode());
            result2 = result2 * 31 + (this.loginUi == null ? 0 : this.loginUi.hashCode());
            result2 = result2 * 31 + (this.loginCheckJs == null ? 0 : this.loginCheckJs.hashCode());
            result2 = result2 * 31 + (this.bookSourceComment == null ? 0 : this.bookSourceComment.hashCode());
            result2 = result2 * 31 + Long.hashCode(this.lastUpdateTime);
            result2 = result2 * 31 + Long.hashCode(this.respondTime);
            result2 = result2 * 31 + Integer.hashCode(this.weight);
            result2 = result2 * 31 + (this.exploreUrl == null ? 0 : this.exploreUrl.hashCode());
            result2 = result2 * 31 + (this.ruleExplore == null ? 0 : this.ruleExplore.hashCode());
            result2 = result2 * 31 + (this.searchUrl == null ? 0 : this.searchUrl.hashCode());
            result2 = result2 * 31 + (this.ruleSearch == null ? 0 : this.ruleSearch.hashCode());
            result2 = result2 * 31 + (this.ruleBookInfo == null ? 0 : this.ruleBookInfo.hashCode());
            result2 = result2 * 31 + (this.ruleToc == null ? 0 : this.ruleToc.hashCode());
            result2 = result2 * 31 + (this.ruleContent == null ? 0 : this.ruleContent.hashCode());
            return result2;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof BookSourceAny)) {
                return false;
            }
            BookSourceAny bookSourceAny = (BookSourceAny)other;
            if (!Intrinsics.areEqual(this.bookSourceName, bookSourceAny.bookSourceName)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.bookSourceGroup, bookSourceAny.bookSourceGroup)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.bookSourceUrl, bookSourceAny.bookSourceUrl)) {
                return false;
            }
            if (this.bookSourceType != bookSourceAny.bookSourceType) {
                return false;
            }
            if (!Intrinsics.areEqual(this.bookUrlPattern, bookSourceAny.bookUrlPattern)) {
                return false;
            }
            if (this.customOrder != bookSourceAny.customOrder) {
                return false;
            }
            if (this.enabled != bookSourceAny.enabled) {
                return false;
            }
            if (this.enabledExplore != bookSourceAny.enabledExplore) {
                return false;
            }
            if (this.enabledCookieJar != bookSourceAny.enabledCookieJar) {
                return false;
            }
            if (!Intrinsics.areEqual(this.concurrentRate, bookSourceAny.concurrentRate)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.header, bookSourceAny.header)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.loginUrl, bookSourceAny.loginUrl)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.loginUi, bookSourceAny.loginUi)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.loginCheckJs, bookSourceAny.loginCheckJs)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.bookSourceComment, bookSourceAny.bookSourceComment)) {
                return false;
            }
            if (this.lastUpdateTime != bookSourceAny.lastUpdateTime) {
                return false;
            }
            if (this.respondTime != bookSourceAny.respondTime) {
                return false;
            }
            if (this.weight != bookSourceAny.weight) {
                return false;
            }
            if (!Intrinsics.areEqual(this.exploreUrl, bookSourceAny.exploreUrl)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.ruleExplore, bookSourceAny.ruleExplore)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.searchUrl, bookSourceAny.searchUrl)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.ruleSearch, bookSourceAny.ruleSearch)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.ruleBookInfo, bookSourceAny.ruleBookInfo)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.ruleToc, bookSourceAny.ruleToc)) {
                return false;
            }
            return Intrinsics.areEqual(this.ruleContent, bookSourceAny.ruleContent);
        }

        public BookSourceAny() {
            this(null, null, null, 0, null, 0, false, false, false, null, null, null, null, null, null, 0L, 0L, 0, null, null, null, null, null, null, null, 0x1FFFFFF, null);
        }
    }
}

