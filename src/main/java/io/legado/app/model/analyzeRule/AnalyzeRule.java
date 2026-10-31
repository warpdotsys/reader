/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.model.analyzeRule;

import com.google.gson.reflect.TypeToken;
import com.script.Bindings;
import com.script.SimpleBindings;
import io.legado.app.constant.AppConst;
import io.legado.app.constant.AppPattern;
import io.legado.app.data.entities.BaseBook;
import io.legado.app.data.entities.BaseSource;
import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookChapter;
import io.legado.app.data.entities.BookSource;
import io.legado.app.data.entities.SearchBook;
import io.legado.app.help.CacheManager;
import io.legado.app.help.JsExtensions;
import io.legado.app.help.http.CookieStore;
import io.legado.app.help.http.StrResponse;
import io.legado.app.model.DebugLog;
import io.legado.app.model.analyzeRule.AnalyzeByJSonPath;
import io.legado.app.model.analyzeRule.AnalyzeByJSoup;
import io.legado.app.model.analyzeRule.AnalyzeByRegex;
import io.legado.app.model.analyzeRule.AnalyzeByXPath;
import io.legado.app.model.analyzeRule.AnalyzeUrl;
import io.legado.app.model.analyzeRule.QueryTTF;
import io.legado.app.model.analyzeRule.RuleDataInterface;
import io.legado.app.model.webBook.WebBook;
import io.legado.app.utils.GsonExtensionsKt;
import io.legado.app.utils.NetworkUtils;
import io.legado.app.utils.StringExtensionsKt;
import io.legado.app.utils.StringUtils;
import io.legado.app.utils.TextUtils;
import io.legado.app.utils.ThrowableExtensionsKt;
import java.io.File;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.TimeoutKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Connection;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.mozilla.javascript.NativeObject;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 g2\u00020\u0001:\u0003ghiB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\bJ\u0012\u00108\u001a\u0004\u0018\u00010\u00102\u0006\u00109\u001a\u00020\u0010H\u0016J\u001c\u0010:\u001a\u0004\u0018\u00010\u001e2\u0006\u0010;\u001a\u00020\u00102\n\b\u0002\u0010<\u001a\u0004\u0018\u00010\u001eJ\u000e\u0010=\u001a\u00020\u00102\u0006\u0010>\u001a\u00020\u0010J\u0010\u0010?\u001a\u00020\n2\u0006\u0010@\u001a\u00020\u001eH\u0002J\u0010\u0010A\u001a\u00020\f2\u0006\u0010@\u001a\u00020\u001eH\u0002J\u0010\u0010B\u001a\u00020\u000e2\u0006\u0010@\u001a\u00020\u001eH\u0002J\u0010\u0010C\u001a\u0004\u0018\u00010\u001e2\u0006\u0010D\u001a\u00020\u0010J\u0014\u0010E\u001a\b\u0012\u0004\u0012\u00020\u001e0F2\u0006\u0010D\u001a\u00020\u0010J\n\u0010G\u001a\u0004\u0018\u00010\u0007H\u0016J\n\u0010H\u001a\u0004\u0018\u00010\u0005H\u0016J(\u0010I\u001a\u00020\u00102\b\u0010D\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010J\u001a\u0004\u0018\u00010\u001e2\b\b\u0002\u0010K\u001a\u00020'H\u0007J0\u0010I\u001a\u00020\u00102\u0010\u0010L\u001a\f\u0012\b\u0012\u00060MR\u00020\u00000F2\n\b\u0002\u0010J\u001a\u0004\u0018\u00010\u001e2\b\b\u0002\u0010K\u001a\u00020'H\u0007J0\u0010N\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010F2\b\u0010O\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010J\u001a\u0004\u0018\u00010\u001e2\b\b\u0002\u0010K\u001a\u00020'H\u0007J8\u0010N\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010F2\u0010\u0010L\u001a\f\u0012\b\u0012\u00060MR\u00020\u00000F2\n\b\u0002\u0010J\u001a\u0004\u0018\u00010\u001e2\b\b\u0002\u0010K\u001a\u00020'H\u0007J\b\u0010P\u001a\u00020\u0010H\u0016J\u0016\u0010Q\u001a\u00020\u00102\u0006\u0010>\u001a\u00020\u00102\u0006\u0010R\u001a\u00020\u0010J\u001c\u0010S\u001a\u00020T2\u0012\u0010U\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100VH\u0002J\u0006\u0010W\u001a\u00020TJ\u0006\u0010X\u001a\u00020TJ\u0006\u0010Y\u001a\u00020TJ\u001c\u0010Z\u001a\u00020\u00102\u0006\u0010<\u001a\u00020\u00102\n\u0010O\u001a\u00060MR\u00020\u0000H\u0002J\u0010\u0010[\u001a\u00020\u00002\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010J\u001e\u0010\\\u001a\u00020\u00002\b\u0010\u001f\u001a\u0004\u0018\u00010\u001e2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0010H\u0007J\u0010\u0010]\u001a\u0004\u0018\u0001002\u0006\u0010^\u001a\u00020\u0010J4\u0010_\u001a\u00020\u00102\u0006\u0010D\u001a\u00020\u00102\"\u0010`\u001a\u001e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100aj\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0010`bH\u0002J$\u0010c\u001a\f\u0012\b\u0012\u00060MR\u00020\u00000F2\b\u0010D\u001a\u0004\u0018\u00010\u00102\b\b\u0002\u0010d\u001a\u00020'J\u0012\u0010e\u001a\u0004\u0018\u00010\u00102\b\u0010f\u001a\u0004\u0018\u00010\u0010R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\"\u0010\u0011\u001a\u0004\u0018\u00010\u00102\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u00158F\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\"\u0010\u001f\u001a\u0004\u0018\u00010\u001e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u001e@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u000e\u0010&\u001a\u00020'X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020'X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010)\u001a\u0004\u0018\u00010\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u0013\"\u0004\b+\u0010,R\u000e\u0010-\u001a\u00020'X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020'X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020'X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\"\u00101\u001a\u0004\u0018\u0001002\b\u0010\u000f\u001a\u0004\u0018\u000100@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006j"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeRule;", "Lio/legado/app/help/JsExtensions;", "ruleData", "Lio/legado/app/model/analyzeRule/RuleDataInterface;", "source", "Lio/legado/app/data/entities/BaseSource;", "debugLog", "Lio/legado/app/model/DebugLog;", "(Lio/legado/app/model/analyzeRule/RuleDataInterface;Lio/legado/app/data/entities/BaseSource;Lio/legado/app/model/DebugLog;)V", "analyzeByJSonPath", "Lio/legado/app/model/analyzeRule/AnalyzeByJSonPath;", "analyzeByJSoup", "Lio/legado/app/model/analyzeRule/AnalyzeByJSoup;", "analyzeByXPath", "Lio/legado/app/model/analyzeRule/AnalyzeByXPath;", "<set-?>", "", "baseUrl", "getBaseUrl", "()Ljava/lang/String;", "book", "Lio/legado/app/data/entities/BaseBook;", "getBook", "()Lio/legado/app/data/entities/BaseBook;", "chapter", "Lio/legado/app/data/entities/BookChapter;", "getChapter", "()Lio/legado/app/data/entities/BookChapter;", "setChapter", "(Lio/legado/app/data/entities/BookChapter;)V", "", "content", "getContent", "()Ljava/lang/Object;", "getDebugLog", "()Lio/legado/app/model/DebugLog;", "setDebugLog", "(Lio/legado/app/model/DebugLog;)V", "isJSON", "", "isRegex", "nextChapterUrl", "getNextChapterUrl", "setNextChapterUrl", "(Ljava/lang/String;)V", "objectChangedJP", "objectChangedJS", "objectChangedXP", "Ljava/net/URL;", "redirectUrl", "getRedirectUrl", "()Ljava/net/URL;", "getRuleData", "()Lio/legado/app/model/analyzeRule/RuleDataInterface;", "setRuleData", "(Lio/legado/app/model/analyzeRule/RuleDataInterface;)V", "ajax", "urlStr", "evalJS", "jsStr", "result", "get", "key", "getAnalyzeByJSonPath", "o", "getAnalyzeByJSoup", "getAnalyzeByXPath", "getElement", "ruleStr", "getElements", "", "getLogger", "getSource", "getString", "mContent", "isUrl", "ruleList", "Lio/legado/app/model/analyzeRule/AnalyzeRule$SourceRule;", "getStringList", "rule", "getUserNameSpace", "put", "value", "putRule", "", "map", "", "reGetBook", "refreshBookUrl", "refreshTocUrl", "replaceRegex", "setBaseUrl", "setContent", "setRedirectUrl", "url", "splitPutRule", "putMap", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "splitSourceRule", "allInOne", "toNumChapter", "s", "Companion", "Mode", "SourceRule", "reader-pro"})
public final class AnalyzeRule
implements JsExtensions {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private RuleDataInterface ruleData;
    @Nullable
    private final BaseSource source;
    @Nullable
    private DebugLog debugLog;
    @Nullable
    private BookChapter chapter;
    @Nullable
    private String nextChapterUrl;
    @Nullable
    private Object content;
    @Nullable
    private String baseUrl;
    @Nullable
    private URL redirectUrl;
    private boolean isJSON;
    private boolean isRegex;
    @Nullable
    private AnalyzeByXPath analyzeByXPath;
    @Nullable
    private AnalyzeByJSoup analyzeByJSoup;
    @Nullable
    private AnalyzeByJSonPath analyzeByJSonPath;
    private boolean objectChangedXP;
    private boolean objectChangedJS;
    private boolean objectChangedJP;
    private static final Pattern putPattern = Pattern.compile("@put:(\\{[^}]+?\\})", 2);
    private static final Pattern evalPattern = Pattern.compile("@get:\\{[^}]+?\\}|\\{\\{[\\w\\W]*?\\}\\}", 2);
    private static final Pattern regexPattern = Pattern.compile("\\$\\d{1,2}");
    private static final Pattern titleNumPattern = Pattern.compile("(\u7b2c)(.+?)(\u7ae0)");

    public AnalyzeRule(@NotNull RuleDataInterface ruleData, @Nullable BaseSource source, @Nullable DebugLog debugLog) {
        Intrinsics.checkNotNullParameter(ruleData, "ruleData");
        this.ruleData = ruleData;
        this.source = source;
        this.debugLog = debugLog;
    }

    public /* synthetic */ AnalyzeRule(RuleDataInterface ruleDataInterface, BaseSource baseSource, DebugLog debugLog, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            baseSource = null;
        }
        if ((n & 4) != 0) {
            debugLog = null;
        }
        this(ruleDataInterface, baseSource, debugLog);
    }

    @NotNull
    public final RuleDataInterface getRuleData() {
        return this.ruleData;
    }

    public final void setRuleData(@NotNull RuleDataInterface ruleDataInterface) {
        Intrinsics.checkNotNullParameter(ruleDataInterface, "<set-?>");
        this.ruleData = ruleDataInterface;
    }

    @Nullable
    public final DebugLog getDebugLog() {
        return this.debugLog;
    }

    public final void setDebugLog(@Nullable DebugLog debugLog) {
        this.debugLog = debugLog;
    }

    @Nullable
    public final BaseBook getBook() {
        RuleDataInterface ruleDataInterface = this.ruleData;
        return ruleDataInterface instanceof BaseBook ? (BaseBook)ruleDataInterface : null;
    }

    @Nullable
    public final BookChapter getChapter() {
        return this.chapter;
    }

    public final void setChapter(@Nullable BookChapter bookChapter) {
        this.chapter = bookChapter;
    }

    @Nullable
    public final String getNextChapterUrl() {
        return this.nextChapterUrl;
    }

    public final void setNextChapterUrl(@Nullable String string) {
        this.nextChapterUrl = string;
    }

    @Nullable
    public final Object getContent() {
        return this.content;
    }

    @Nullable
    public final String getBaseUrl() {
        return this.baseUrl;
    }

    @Nullable
    public final URL getRedirectUrl() {
        return this.redirectUrl;
    }

    @Override
    @NotNull
    public String getUserNameSpace() {
        return this.ruleData.getUserNameSpace();
    }

    @Override
    @Nullable
    public BaseSource getSource() {
        return this.source;
    }

    @Override
    @Nullable
    public DebugLog getLogger() {
        return this.debugLog;
    }

    @JvmOverloads
    @NotNull
    public final AnalyzeRule setContent(@Nullable Object content, @Nullable String baseUrl) {
        if (content == null) {
            throw new AssertionError((Object)"\u5185\u5bb9\u4e0d\u53ef\u7a7a\uff08Content cannot be null\uff09");
        }
        this.content = content;
        this.isJSON = StringExtensionsKt.isJson(content.toString());
        this.setBaseUrl(baseUrl);
        this.objectChangedXP = true;
        this.objectChangedJS = true;
        this.objectChangedJP = true;
        return this;
    }

    public static /* synthetic */ AnalyzeRule setContent$default(AnalyzeRule analyzeRule, Object object, String string, int n, Object object2) {
        if ((n & 2) != 0) {
            string = null;
        }
        return analyzeRule.setContent(object, string);
    }

    @NotNull
    public final AnalyzeRule setBaseUrl(@Nullable String baseUrl) {
        String string = baseUrl;
        if (string != null) {
            String string2 = string;
            boolean bl = false;
            boolean bl2 = false;
            String it = string2;
            boolean bl3 = false;
            this.baseUrl = baseUrl;
        }
        return this;
    }

    @Nullable
    public final URL setRedirectUrl(@NotNull String url2) {
        Intrinsics.checkNotNullParameter(url2, "url");
        try {
            this.redirectUrl = new URL(url2);
        }
        catch (Exception e) {
            this.log("URL(" + url2 + ") error\n" + e.getLocalizedMessage());
        }
        return this.redirectUrl;
    }

    private final AnalyzeByXPath getAnalyzeByXPath(Object o) {
        AnalyzeByXPath analyzeByXPath;
        if (!Intrinsics.areEqual(o, this.content)) {
            analyzeByXPath = new AnalyzeByXPath(o);
        } else {
            if (this.analyzeByXPath == null || this.objectChangedXP) {
                Object object = this.content;
                Intrinsics.checkNotNull(object);
                this.analyzeByXPath = new AnalyzeByXPath(object);
                this.objectChangedXP = false;
            }
            AnalyzeByXPath analyzeByXPath2 = this.analyzeByXPath;
            analyzeByXPath = analyzeByXPath2;
            Intrinsics.checkNotNull(analyzeByXPath2);
        }
        return analyzeByXPath;
    }

    private final AnalyzeByJSoup getAnalyzeByJSoup(Object o) {
        AnalyzeByJSoup analyzeByJSoup;
        if (!Intrinsics.areEqual(o, this.content)) {
            analyzeByJSoup = new AnalyzeByJSoup(o);
        } else {
            if (this.analyzeByJSoup == null || this.objectChangedJS) {
                Object object = this.content;
                Intrinsics.checkNotNull(object);
                this.analyzeByJSoup = new AnalyzeByJSoup(object);
                this.objectChangedJS = false;
            }
            AnalyzeByJSoup analyzeByJSoup2 = this.analyzeByJSoup;
            analyzeByJSoup = analyzeByJSoup2;
            Intrinsics.checkNotNull(analyzeByJSoup2);
        }
        return analyzeByJSoup;
    }

    private final AnalyzeByJSonPath getAnalyzeByJSonPath(Object o) {
        AnalyzeByJSonPath analyzeByJSonPath;
        if (!Intrinsics.areEqual(o, this.content)) {
            analyzeByJSonPath = new AnalyzeByJSonPath(o);
        } else {
            if (this.analyzeByJSonPath == null || this.objectChangedJP) {
                Object object = this.content;
                Intrinsics.checkNotNull(object);
                this.analyzeByJSonPath = new AnalyzeByJSonPath(object);
                this.objectChangedJP = false;
            }
            AnalyzeByJSonPath analyzeByJSonPath2 = this.analyzeByJSonPath;
            analyzeByJSonPath = analyzeByJSonPath2;
            Intrinsics.checkNotNull(analyzeByJSonPath2);
        }
        return analyzeByJSonPath;
    }

    @JvmOverloads
    @Nullable
    public final List<String> getStringList(@Nullable String rule, @Nullable Object mContent, boolean isUrl) {
        CharSequence charSequence = rule;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence == null || charSequence.length() == 0) {
            return null;
        }
        List<SourceRule> ruleList = this.splitSourceRule(rule, false);
        return this.getStringList(ruleList, mContent, isUrl);
    }

    public static /* synthetic */ List getStringList$default(AnalyzeRule analyzeRule, String string, Object object, boolean bl, int n, Object object2) {
        if ((n & 2) != 0) {
            object = null;
        }
        if ((n & 4) != 0) {
            bl = false;
        }
        return analyzeRule.getStringList(string, object, bl);
    }

    @JvmOverloads
    @Nullable
    public final List<String> getStringList(@NotNull List<SourceRule> ruleList, @Nullable Object mContent, boolean isUrl) {
        boolean bl;
        String[] stringArray;
        String[] content;
        Intrinsics.checkNotNullParameter(ruleList, "ruleList");
        Object result2 = null;
        Object object = mContent;
        Object object2 = content = object == null ? this.content : object;
        if (content != null) {
            object = ruleList;
            boolean bl2 = false;
            if (!object.isEmpty()) {
                result2 = content;
                if (content instanceof NativeObject) {
                    object = ((NativeObject)content).get(ruleList.get(0).getRule$reader_pro());
                    result2 = object == null ? null : object.toString();
                } else {
                    for (SourceRule sourceRule : ruleList) {
                        this.putRule((Map<String, String>)sourceRule.getPutMap$reader_pro());
                        sourceRule.makeUpRule(result2);
                        stringArray = result2;
                        if (stringArray == null) continue;
                        String[] stringArray2 = stringArray;
                        boolean bl3 = false;
                        bl = false;
                        String[] it = stringArray2;
                        boolean bl4 = false;
                        Object object3 = sourceRule.getRule$reader_pro();
                        int n = 0;
                        if (object3.length() > 0) {
                            List<String> list2;
                            object3 = sourceRule.getMode$reader_pro();
                            n = WhenMappings.$EnumSwitchMapping$0[((Enum)object3).ordinal()];
                            switch (n) {
                                case 1: {
                                    list2 = this.evalJS(sourceRule.getRule$reader_pro(), result2);
                                    break;
                                }
                                case 2: {
                                    list2 = this.getAnalyzeByJSonPath(it).getStringList$reader_pro(sourceRule.getRule$reader_pro());
                                    break;
                                }
                                case 3: {
                                    list2 = this.getAnalyzeByXPath(it).getStringList$reader_pro(sourceRule.getRule$reader_pro());
                                    break;
                                }
                                case 4: {
                                    list2 = this.getAnalyzeByJSoup(it).getStringList$reader_pro(sourceRule.getRule$reader_pro());
                                    break;
                                }
                                default: {
                                    list2 = sourceRule.getRule$reader_pro();
                                }
                            }
                            result2 = list2;
                        }
                        object3 = sourceRule.getReplaceRegex$reader_pro();
                        n = 0;
                        if (object3.length() > 0 && result2 instanceof List) {
                            ArrayList<String> newList = new ArrayList<String>();
                            Object object4 = result2;
                            for (Object item : (List)object4) {
                                newList.add(this.replaceRegex(String.valueOf(item), sourceRule));
                            }
                            result2 = newList;
                            continue;
                        }
                        object3 = sourceRule.getReplaceRegex$reader_pro();
                        n = 0;
                        if (!(object3.length() > 0)) continue;
                        result2 = this.replaceRegex(String.valueOf(result2), sourceRule);
                    }
                }
            }
        }
        if (result2 == null) {
            return null;
        }
        if (result2 instanceof String) {
            object = result2;
            CharSequence charSequence = (String)object;
            object = new String[]{"\n"};
            result2 = StringsKt.split$default(charSequence, (String[])object, false, 0, 6, null);
        }
        if (isUrl) {
            ArrayList<String> urlList = new ArrayList<String>();
            if (result2 instanceof List) {
                stringArray = result2;
                for (Object url2 : (List)stringArray) {
                    String absoluteURL = NetworkUtils.INSTANCE.getAbsoluteURL(this.redirectUrl, String.valueOf(url2));
                    CharSequence charSequence = absoluteURL;
                    bl = false;
                    if (!(charSequence.length() > 0) || urlList.contains(absoluteURL)) continue;
                    urlList.add(absoluteURL);
                }
            }
            return urlList;
        }
        object = result2;
        return object instanceof List ? (List)object : null;
    }

    public static /* synthetic */ List getStringList$default(AnalyzeRule analyzeRule, List list2, Object object, boolean bl, int n, Object object2) {
        if ((n & 2) != 0) {
            object = null;
        }
        if ((n & 4) != 0) {
            bl = false;
        }
        return analyzeRule.getStringList(list2, object, bl);
    }

    @JvmOverloads
    @NotNull
    public final String getString(@Nullable String ruleStr, @Nullable Object mContent, boolean isUrl) {
        if (TextUtils.isEmpty(ruleStr)) {
            return "";
        }
        List ruleList = AnalyzeRule.splitSourceRule$default(this, ruleStr, false, 2, null);
        return this.getString(ruleList, mContent, isUrl);
    }

    public static /* synthetic */ String getString$default(AnalyzeRule analyzeRule, String string, Object object, boolean bl, int n, Object object2) {
        if ((n & 2) != 0) {
            object = null;
        }
        if ((n & 4) != 0) {
            bl = false;
        }
        return analyzeRule.getString(string, object, bl);
    }

    @JvmOverloads
    @NotNull
    public final String getString(@NotNull List<SourceRule> ruleList, @Nullable Object mContent, boolean isUrl) {
        Object object;
        boolean bl;
        boolean bl2;
        Object object2;
        boolean bl3;
        Iterator<SourceRule> result2;
        block18: {
            block19: {
                Iterator<SourceRule> content;
                Intrinsics.checkNotNullParameter(ruleList, "ruleList");
                result2 = null;
                Object object3 = mContent;
                Iterator<SourceRule> iterator2 = content = object3 == null ? this.content : object3;
                if (content == null) break block18;
                object3 = ruleList;
                bl3 = false;
                if (!(!object3.isEmpty())) break block18;
                result2 = content;
                if (!(result2 instanceof NativeObject)) break block19;
                object3 = ((NativeObject)((Object)result2)).get(ruleList.get(0).getRule$reader_pro());
                result2 = object3 == null ? null : object3.toString();
                break block18;
            }
            for (SourceRule sourceRule : ruleList) {
                int n;
                Object object4;
                block21: {
                    Object it;
                    block20: {
                        this.putRule((Map<String, String>)sourceRule.getPutMap$reader_pro());
                        sourceRule.makeUpRule(result2);
                        object2 = result2;
                        if (object2 == null) continue;
                        Object object5 = object2;
                        bl2 = false;
                        bl = false;
                        it = object5;
                        boolean bl4 = false;
                        object4 = sourceRule.getRule$reader_pro();
                        n = 0;
                        if (!StringsKt.isBlank((CharSequence)object4)) break block20;
                        object4 = sourceRule.getReplaceRegex$reader_pro();
                        n = 0;
                        if (!(object4.length() == 0)) break block21;
                    }
                    object4 = sourceRule.getMode$reader_pro();
                    n = WhenMappings.$EnumSwitchMapping$0[((Enum)object4).ordinal()];
                    switch (n) {
                        case 1: {
                            Object object6 = this.evalJS(sourceRule.getRule$reader_pro(), it);
                            break;
                        }
                        case 2: {
                            Object object6 = this.getAnalyzeByJSonPath(it).getString(sourceRule.getRule$reader_pro());
                            break;
                        }
                        case 3: {
                            Object object6 = this.getAnalyzeByXPath(it).getString(sourceRule.getRule$reader_pro());
                            break;
                        }
                        case 4: {
                            Object object6;
                            if (isUrl) {
                                object6 = this.getAnalyzeByJSoup(it).getString0$reader_pro(sourceRule.getRule$reader_pro());
                                break;
                            }
                            object6 = this.getAnalyzeByJSoup(it).getString$reader_pro(sourceRule.getRule$reader_pro());
                            break;
                        }
                        default: {
                            Object object6 = result2 = sourceRule.getRule$reader_pro();
                        }
                    }
                }
                if (result2 == null) continue;
                object4 = sourceRule.getReplaceRegex$reader_pro();
                n = 0;
                if (!(object4.length() > 0)) continue;
                result2 = this.replaceRegex(String.valueOf(result2), sourceRule);
            }
        }
        if (result2 == null) {
            result2 = "";
        }
        bl3 = false;
        try {
            object2 = Result.Companion;
            boolean bl5 = false;
            String string = Entities.unescape(String.valueOf(result2));
            bl2 = false;
            object2 = Result.constructor-impl(string);
        }
        catch (Throwable throwable) {
            Result.Companion companion = Result.Companion;
            bl = false;
            object2 = Result.constructor-impl(ResultKt.createFailure(throwable));
        }
        Object object7 = object2;
        boolean bl6 = false;
        boolean bl7 = false;
        Throwable throwable = Result.exceptionOrNull-impl(object7);
        if (throwable != null) {
            Throwable throwable2 = throwable;
            boolean bl8 = false;
            bl = false;
            Throwable throwable3 = throwable2;
            boolean bl9 = false;
            Throwable it = throwable3;
            boolean bl10 = false;
            this.log(Intrinsics.stringPlus("Entities.unescape() error\n", it.getLocalizedMessage()));
        }
        bl6 = false;
        boolean bl11 = false;
        Throwable throwable4 = Result.exceptionOrNull-impl(object7);
        if (throwable4 == null) {
            object = object7;
        } else {
            Throwable it = throwable4;
            boolean bl12 = false;
            object = String.valueOf(result2);
        }
        String str = (String)object;
        if (isUrl) {
            Intrinsics.checkNotNullExpressionValue(str, "str");
            return StringsKt.isBlank(str) ? ((object7 = this.baseUrl) == null ? "" : object7) : NetworkUtils.INSTANCE.getAbsoluteURL(this.redirectUrl, str);
        }
        Intrinsics.checkNotNullExpressionValue(str, "str");
        return str;
    }

    public static /* synthetic */ String getString$default(AnalyzeRule analyzeRule, List list2, Object object, boolean bl, int n, Object object2) {
        if ((n & 2) != 0) {
            object = null;
        }
        if ((n & 4) != 0) {
            bl = false;
        }
        return analyzeRule.getString(list2, object, bl);
    }

    @Nullable
    public final Object getElement(@NotNull String ruleStr) {
        Intrinsics.checkNotNullParameter(ruleStr, "ruleStr");
        if (TextUtils.isEmpty(ruleStr)) {
            return null;
        }
        Object result2 = null;
        Object content = this.content;
        List<SourceRule> ruleList = this.splitSourceRule(ruleStr, true);
        if (content != null) {
            Collection collection = ruleList;
            boolean bl = false;
            if (!collection.isEmpty()) {
                result2 = content;
                for (SourceRule sourceRule : ruleList) {
                    List<Element> list2;
                    this.putRule((Map<String, String>)sourceRule.getPutMap$reader_pro());
                    sourceRule.makeUpRule(result2);
                    Object object = result2;
                    if (object == null) continue;
                    Object object2 = object;
                    boolean bl2 = false;
                    boolean bl3 = false;
                    Object it = object2;
                    boolean bl4 = false;
                    Object object3 = sourceRule.getMode$reader_pro();
                    int n = WhenMappings.$EnumSwitchMapping$0[((Enum)object3).ordinal()];
                    switch (n) {
                        case 5: {
                            String[] stringArray = new String[]{"&&"};
                            list2 = AnalyzeByRegex.getElement$default(AnalyzeByRegex.INSTANCE, String.valueOf(result2), StringExtensionsKt.splitNotBlank(sourceRule.getRule$reader_pro(), stringArray), 0, 4, null);
                            break;
                        }
                        case 1: {
                            list2 = this.evalJS(sourceRule.getRule$reader_pro(), it);
                            break;
                        }
                        case 2: {
                            list2 = this.getAnalyzeByJSonPath(it).getObject$reader_pro(sourceRule.getRule$reader_pro());
                            break;
                        }
                        case 3: {
                            list2 = this.getAnalyzeByXPath(it).getElements$reader_pro(sourceRule.getRule$reader_pro());
                            break;
                        }
                        default: {
                            list2 = this.getAnalyzeByJSoup(it).getElements$reader_pro(sourceRule.getRule$reader_pro());
                        }
                    }
                    result2 = list2;
                    object3 = sourceRule.getReplaceRegex$reader_pro();
                    n = 0;
                    if (!(object3.length() > 0)) continue;
                    result2 = this.replaceRegex(String.valueOf(result2), sourceRule);
                }
            }
        }
        return result2;
    }

    @NotNull
    public final List<Object> getElements(@NotNull String ruleStr) {
        Object object;
        Intrinsics.checkNotNullParameter(ruleStr, "ruleStr");
        Object result2 = null;
        Object content = this.content;
        List<SourceRule> ruleList = this.splitSourceRule(ruleStr, true);
        if (content != null) {
            object = ruleList;
            boolean bl = false;
            if (!object.isEmpty()) {
                result2 = content;
                object = ruleList.iterator();
                while (object.hasNext()) {
                    List<Object> list2;
                    SourceRule sourceRule = (SourceRule)object.next();
                    this.putRule((Map<String, String>)sourceRule.getPutMap$reader_pro());
                    List<Object> list3 = result2;
                    if (list3 == null) continue;
                    List<Object> list4 = list3;
                    boolean bl2 = false;
                    boolean bl3 = false;
                    List<Object> it = list4;
                    boolean bl4 = false;
                    Object object2 = sourceRule.getMode$reader_pro();
                    int n = WhenMappings.$EnumSwitchMapping$0[((Enum)object2).ordinal()];
                    switch (n) {
                        case 5: {
                            String[] stringArray = new String[]{"&&"};
                            list2 = AnalyzeByRegex.getElements$default(AnalyzeByRegex.INSTANCE, String.valueOf(result2), StringExtensionsKt.splitNotBlank(sourceRule.getRule$reader_pro(), stringArray), 0, 4, null);
                            break;
                        }
                        case 1: {
                            list2 = this.evalJS(sourceRule.getRule$reader_pro(), result2);
                            break;
                        }
                        case 2: {
                            list2 = this.getAnalyzeByJSonPath(it).getList$reader_pro(sourceRule.getRule$reader_pro());
                            break;
                        }
                        case 3: {
                            list2 = this.getAnalyzeByXPath(it).getElements$reader_pro(sourceRule.getRule$reader_pro());
                            break;
                        }
                        default: {
                            list2 = this.getAnalyzeByJSoup(it).getElements$reader_pro(sourceRule.getRule$reader_pro());
                        }
                    }
                    result2 = list2;
                    object2 = sourceRule.getReplaceRegex$reader_pro();
                    n = 0;
                    if (!(object2.length() > 0)) continue;
                    result2 = this.replaceRegex(String.valueOf(result2), sourceRule);
                }
            }
        }
        if ((object = result2) != null) {
            Collection collection = object;
            boolean bl = false;
            boolean bl5 = false;
            Collection it = collection;
            boolean bl6 = false;
            return (List)it;
        }
        return new ArrayList();
    }

    private final void putRule(Map<String, String> map) {
        Object object = map;
        boolean bl = false;
        Iterator<Map.Entry<String, String>> iterator2 = object.entrySet().iterator();
        while (iterator2.hasNext()) {
            Object object2 = object = iterator2.next();
            boolean bl2 = false;
            String key = (String)object2.getKey();
            Object object3 = object;
            boolean bl3 = false;
            String value = (String)object3.getValue();
            this.put(key, AnalyzeRule.getString$default(this, value, null, false, 6, null));
        }
    }

    /*
     * WARNING - void declaration
     */
    private final String splitPutRule(String ruleStr, HashMap<String, String> putMap) {
        String vRuleStr = ruleStr;
        Matcher putMatcher = putPattern.matcher(vRuleStr);
        while (putMatcher.find()) {
            Object object;
            Object object2 = putMatcher.group();
            Intrinsics.checkNotNullExpressionValue(object2, "putMatcher.group()");
            vRuleStr = StringsKt.replace$default(vRuleStr, (String)object2, "", false, 4, null);
            Object object3 = GsonExtensionsKt.getGSON();
            String json$iv = putMatcher.group(1);
            boolean $i$f$fromJsonObject = false;
            boolean bl = false;
            try {
                void $this$fromJsonObject$iv;
                object = Result.Companion;
                boolean bl2 = false;
                boolean $i$f$genericType = false;
                Type type = new TypeToken<Map<String, ? extends String>>(){}.getType();
                Intrinsics.checkNotNullExpressionValue(type, "object : TypeToken<T>() {}.type");
                Object t = $this$fromJsonObject$iv.fromJson(json$iv, type);
                if (!(t instanceof Map)) {
                    t = null;
                }
                Map map = (Map)t;
                boolean bl3 = false;
                object = Result.constructor-impl(map);
            }
            catch (Throwable throwable) {
                Result.Companion companion = Result.Companion;
                boolean bl4 = false;
                object = Result.constructor-impl(ResultKt.createFailure(throwable));
            }
            object3 = object;
            boolean bl5 = false;
            object2 = (Map)(Result.isFailure-impl(object3) ? null : object3);
            if (object2 == null) continue;
            object3 = object2;
            bl5 = false;
            boolean bl6 = false;
            Object it = object3;
            boolean bl7 = false;
            putMap.putAll((Map<String, String>)it);
        }
        return vRuleStr;
    }

    private final String replaceRegex(String result2, SourceRule rule) {
        String string;
        boolean bl;
        Object object;
        Object object2;
        CharSequence charSequence;
        CharSequence charSequence2 = rule.getReplaceRegex$reader_pro();
        boolean bl2 = false;
        if (charSequence2.length() == 0) {
            return result2;
        }
        String vResult = null;
        vResult = result2;
        if (rule.getReplaceFirst$reader_pro()) {
            Object object3;
            Object object4;
            bl2 = false;
            try {
                String string2;
                object4 = Result.Companion;
                boolean $i$a$-runCatching-AnalyzeRule$replaceRegex$422 = false;
                Pattern pattern22 = Pattern.compile(rule.getReplaceRegex$reader_pro());
                Matcher matcher = pattern22.matcher(vResult);
                if (matcher.find()) {
                    String string3 = matcher.group(0);
                    Intrinsics.checkNotNull(string3);
                    charSequence = string3;
                    Object object5 = rule.getReplaceRegex$reader_pro();
                    boolean bl3 = false;
                    object5 = new Regex((String)object5);
                    String string4 = rule.getReplacement$reader_pro();
                    boolean bl4 = false;
                    string2 = ((Regex)object5).replaceFirst(charSequence, string4);
                } else {
                    string2 = "";
                }
                String $i$a$-runCatching-AnalyzeRule$replaceRegex$422 = string2;
                boolean pattern22 = false;
                object4 = Result.constructor-impl($i$a$-runCatching-AnalyzeRule$replaceRegex$422);
            }
            catch (Throwable $i$a$-runCatching-AnalyzeRule$replaceRegex$422) {
                Result.Companion pattern22 = Result.Companion;
                boolean matcher = false;
                object4 = Result.constructor-impl(ResultKt.createFailure($i$a$-runCatching-AnalyzeRule$replaceRegex$422));
            }
            object = object2 = object4;
            bl = false;
            boolean $i$a$-runCatching-AnalyzeRule$replaceRegex$422 = false;
            Throwable $i$a$-runCatching-AnalyzeRule$replaceRegex$422 = Result.exceptionOrNull-impl(object);
            if ($i$a$-runCatching-AnalyzeRule$replaceRegex$422 == null) {
                object3 = object;
            } else {
                Throwable it22 = $i$a$-runCatching-AnalyzeRule$replaceRegex$422;
                boolean bl5 = false;
                object3 = object2 = StringsKt.replaceFirst$default(vResult, rule.getReplaceRegex$reader_pro(), rule.getReplacement$reader_pro(), false, 4, null);
            }
            string = (String)object3;
        } else {
            Object object6;
            Object object7;
            bl2 = false;
            try {
                object7 = Result.Companion;
                boolean bl6 = false;
                CharSequence it22 = vResult;
                Object bl5 = rule.getReplaceRegex$reader_pro();
                boolean bl7 = false;
                bl5 = new Regex((String)bl5);
                charSequence = rule.getReplacement$reader_pro();
                boolean bl8 = false;
                String string5 = ((Regex)bl5).replace(it22, (String)charSequence);
                boolean it22 = false;
                object7 = Result.constructor-impl(string5);
            }
            catch (Throwable throwable) {
                Result.Companion it22 = Result.Companion;
                boolean bl5 = false;
                object7 = Result.constructor-impl(ResultKt.createFailure(throwable));
            }
            object = object2 = object7;
            bl = false;
            boolean bl9 = false;
            Throwable throwable = Result.exceptionOrNull-impl(object);
            if (throwable == null) {
                object6 = object;
            } else {
                Throwable it = throwable;
                boolean bl10 = false;
                object6 = object2 = StringsKt.replace$default(vResult, rule.getReplaceRegex$reader_pro(), rule.getReplacement$reader_pro(), false, 4, null);
            }
            string = (String)object6;
        }
        vResult = string;
        return vResult;
    }

    @NotNull
    public final List<SourceRule> splitSourceRule(@Nullable String ruleStr, boolean allInOne) {
        boolean match$iv$iv;
        char it;
        int index$iv$iv;
        boolean startFound$iv$iv;
        int endIndex$iv$iv;
        int startIndex$iv$iv;
        boolean $i$f$trim;
        CharSequence $this$trim$iv$iv;
        CharSequence $this$trim$iv;
        CharSequence charSequence;
        CharSequence charSequence2 = ruleStr;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence2 == null || charSequence2.length() == 0) {
            return CollectionsKt.emptyList();
        }
        ArrayList<SourceRule> ruleList = new ArrayList<SourceRule>();
        Mode mMode = Mode.Default;
        int start2 = 0;
        if (allInOne && StringsKt.startsWith$default(ruleStr, ":", false, 2, null)) {
            mMode = Mode.Regex;
            this.isRegex = true;
            start2 = 1;
        } else if (this.isRegex) {
            mMode = Mode.Regex;
        }
        String tmp = null;
        Matcher jsMatcher = AppPattern.INSTANCE.getJS_PATTERN().matcher(ruleStr);
        while (jsMatcher.find()) {
            String $i$f$trim22;
            if (jsMatcher.start() > start2) {
                charSequence = ruleStr;
                int n = jsMatcher.start();
                boolean bl3 = false;
                String string = charSequence;
                if (string == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String string2 = string.substring(start2, n);
                Intrinsics.checkNotNullExpressionValue(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                charSequence = string2;
                boolean $i$f$trim22 = false;
                $this$trim$iv$iv = $this$trim$iv;
                $i$f$trim = false;
                startIndex$iv$iv = 0;
                endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
                startFound$iv$iv = false;
                while (startIndex$iv$iv <= endIndex$iv$iv) {
                    index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
                    it = $this$trim$iv$iv.charAt(index$iv$iv);
                    boolean bl4 = false;
                    boolean bl5 = match$iv$iv = Intrinsics.compare(it, 32) <= 0;
                    if (!startFound$iv$iv) {
                        if (!match$iv$iv) {
                            startFound$iv$iv = true;
                            continue;
                        }
                        ++startIndex$iv$iv;
                        continue;
                    }
                    if (!match$iv$iv) break;
                    --endIndex$iv$iv;
                }
                tmp = ((Object)$this$trim$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1)).toString();
                $this$trim$iv = tmp;
                $i$f$trim22 = false;
                if ($this$trim$iv.length() > 0) {
                    ruleList.add(new SourceRule(tmp, mMode));
                }
            }
            $this$trim$iv = ($i$f$trim22 = jsMatcher.group(2)) == null ? jsMatcher.group(1) : $i$f$trim22;
            Intrinsics.checkNotNullExpressionValue($this$trim$iv, "jsMatcher.group(2) ?: jsMatcher.group(1)");
            ruleList.add(new SourceRule((String)$this$trim$iv, Mode.Js));
            start2 = jsMatcher.end();
        }
        if (ruleStr.length() > start2) {
            $this$trim$iv = ruleStr;
            boolean $i$f$trim22 = false;
            String string = $this$trim$iv;
            if (string == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string3 = string.substring(start2);
            Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.String).substring(startIndex)");
            $this$trim$iv = string3;
            $i$f$trim22 = false;
            $this$trim$iv$iv = $this$trim$iv;
            $i$f$trim = false;
            startIndex$iv$iv = 0;
            endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
            startFound$iv$iv = false;
            while (startIndex$iv$iv <= endIndex$iv$iv) {
                index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
                it = $this$trim$iv$iv.charAt(index$iv$iv);
                boolean bl6 = false;
                boolean bl7 = match$iv$iv = Intrinsics.compare(it, 32) <= 0;
                if (!startFound$iv$iv) {
                    if (!match$iv$iv) {
                        startFound$iv$iv = true;
                        continue;
                    }
                    ++startIndex$iv$iv;
                    continue;
                }
                if (!match$iv$iv) break;
                --endIndex$iv$iv;
            }
            tmp = ((Object)$this$trim$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1)).toString();
            charSequence = tmp;
            boolean bl8 = false;
            if (charSequence.length() > 0) {
                ruleList.add(new SourceRule(tmp, mMode));
            }
        }
        return ruleList;
    }

    public static /* synthetic */ List splitSourceRule$default(AnalyzeRule analyzeRule, String string, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return analyzeRule.splitSourceRule(string, bl);
    }

    @NotNull
    public final String put(@NotNull String key, @NotNull String value) {
        block6: {
            Unit unit;
            Unit unit2;
            Unit unit3;
            Unit unit4;
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            RuleDataInterface ruleDataInterface = this.chapter;
            if (ruleDataInterface == null) {
                unit4 = null;
            } else {
                ((BookChapter)ruleDataInterface).putVariable(key, value);
                unit4 = unit3 = Unit.INSTANCE;
            }
            if (unit3 == null) {
                ruleDataInterface = this.getBook();
                if (ruleDataInterface == null) {
                    unit2 = null;
                } else {
                    ruleDataInterface.putVariable(key, value);
                    unit2 = Unit.INSTANCE;
                }
            } else {
                unit2 = unit = unit3;
            }
            if (unit != null) break block6;
            this.ruleData.putVariable(key, value);
        }
        return value;
    }

    @NotNull
    public final String get(@NotNull String key) {
        Object object;
        RuleDataInterface ruleDataInterface;
        Intrinsics.checkNotNullParameter(key, "key");
        String string = key;
        if (Intrinsics.areEqual(string, "bookName")) {
            ruleDataInterface = this.getBook();
            if (ruleDataInterface != null) {
                BaseBook baseBook = ruleDataInterface;
                boolean bl = false;
                boolean bl2 = false;
                BaseBook it = baseBook;
                boolean bl3 = false;
                return it.getName();
            }
        } else if (Intrinsics.areEqual(string, "title") && (ruleDataInterface = this.chapter) != null) {
            RuleDataInterface ruleDataInterface2 = ruleDataInterface;
            boolean bl = false;
            boolean bl4 = false;
            RuleDataInterface it = ruleDataInterface2;
            boolean bl5 = false;
            return ((BookChapter)it).getTitle();
        }
        ruleDataInterface = this.chapter;
        String string2 = string = ruleDataInterface == null ? null : ((BookChapter)ruleDataInterface).getVariable(key);
        if (string == null) {
            String string3;
            RuleDataInterface ruleDataInterface3 = this.getBook();
            RuleDataInterface ruleDataInterface4 = ruleDataInterface = ruleDataInterface3 == null ? null : ruleDataInterface3.getVariable(key);
            object = ruleDataInterface == null ? ((ruleDataInterface3 = this.ruleData) == null ? "" : ((string3 = ruleDataInterface3.getVariable(key)) == null ? "" : string3)) : ruleDataInterface;
        } else {
            object = string;
        }
        return object;
    }

    @Nullable
    public final Object evalJS(@NotNull String jsStr, @Nullable Object result2) {
        Intrinsics.checkNotNullParameter(jsStr, "jsStr");
        SimpleBindings bindings = new SimpleBindings();
        Map map = bindings;
        String string = "java";
        boolean bl = false;
        map.put(string, this);
        map = bindings;
        string = "cookie";
        Object object = new CookieStore(this.getUserNameSpace());
        boolean bl2 = false;
        map.put(string, object);
        map = bindings;
        string = "cache";
        object = new CacheManager(this.getUserNameSpace());
        bl2 = false;
        map.put(string, object);
        map = bindings;
        string = "source";
        object = this.source;
        bl2 = false;
        map.put(string, object);
        map = bindings;
        string = "book";
        object = this.getBook();
        bl2 = false;
        map.put(string, object);
        map = bindings;
        string = "result";
        boolean bl3 = false;
        map.put(string, result2);
        map = bindings;
        string = "baseUrl";
        Object object2 = this.baseUrl;
        bl2 = false;
        map.put(string, object2);
        map = bindings;
        string = "chapter";
        object2 = this.chapter;
        bl2 = false;
        map.put(string, object2);
        map = bindings;
        string = "title";
        object2 = this.chapter;
        object2 = object2 == null ? null : ((BookChapter)object2).getTitle();
        bl2 = false;
        map.put(string, object2);
        map = bindings;
        string = "src";
        object2 = this.content;
        bl2 = false;
        map.put(string, object2);
        map = bindings;
        string = "nextChapterUrl";
        object2 = this.nextChapterUrl;
        bl2 = false;
        map.put(string, object2);
        return AppConst.INSTANCE.getSCRIPT_ENGINE().eval(jsStr, (Bindings)bindings);
    }

    public static /* synthetic */ Object evalJS$default(AnalyzeRule analyzeRule, String string, Object object, int n, Object object2) {
        if ((n & 2) != 0) {
            object = null;
        }
        return analyzeRule.evalJS(string, object);
    }

    @Override
    @Nullable
    public String ajax(@NotNull String urlStr) {
        Intrinsics.checkNotNullParameter(urlStr, "urlStr");
        return (String)BuildersKt.runBlocking$default(null, new Function2<CoroutineScope, Continuation<? super String>, Object>(urlStr, this, null){
            int label;
            final /* synthetic */ String $urlStr;
            final /* synthetic */ AnalyzeRule this$0;
            {
                this.$urlStr = $urlStr;
                this.this$0 = $receiver;
                super(2, $completion);
            }

            /*
             * Unable to fully structure code
             */
            @Nullable
            public final Object invokeSuspend(@NotNull Object var1_1) {
                var13_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0: {
                        ResultKt.throwOnFailure(var1_1);
                        var2_3 = this.$urlStr;
                        var3_4 = this.this$0;
                        var4_6 = false;
                        var5_10 = Result.Companion;
                        $i$a$-runCatching-AnalyzeRule$ajax$1$1 = false;
                        analyzeUrl = new AnalyzeUrl((String)var2_3, null, null, null, null, null, AnalyzeRule.access$getSource$p(var3_4), var3_4.getBook(), null, null, var3_4.getDebugLog(), 830, null);
                        this.label = 1;
                        v0 = AnalyzeUrl.getStrResponseAwait$default(analyzeUrl, null, null, false, this, 7, null);
                        ** if (v0 != var13_2) goto lbl17
lbl16:
                        // 1 sources

                        return var13_2;
lbl17:
                        // 1 sources

                        ** GOTO lbl24
                    }
                    case 1: {
                        $i$a$-runCatching-AnalyzeRule$ajax$1$1 = false;
                        try {
                            ResultKt.throwOnFailure($result);
                            v0 = $result;
lbl24:
                            // 2 sources

                            $i$a$-runCatching-AnalyzeRule$ajax$1$1 = ((StrResponse)v0).getBody();
                            var7_20 = false;
                            var5_10 = Result.constructor-impl($i$a$-runCatching-AnalyzeRule$ajax$1$1);
                        }
                        catch (Throwable $i$a$-runCatching-AnalyzeRule$ajax$1$1) {
                            var7_21 = Result.Companion;
                            var8_23 = false;
                            var5_10 = Result.constructor-impl(ResultKt.createFailure($i$a$-runCatching-AnalyzeRule$ajax$1$1));
                        }
                        var2_3 = var5_10;
                        var3_4 = this.this$0;
                        var4_7 = this.$urlStr;
                        var5_11 = false;
                        $i$a$-runCatching-AnalyzeRule$ajax$1$1 = false;
                        v1 = Result.exceptionOrNull-impl(var2_3);
                        if (v1 != null) {
                            $i$a$-runCatching-AnalyzeRule$ajax$1$1 = v1;
                            var7_22 = false;
                            var8_23 = false;
                            var9_24 = $i$a$-runCatching-AnalyzeRule$ajax$1$1;
                            var10_25 = false;
                            it = var9_24;
                            $i$a$-onFailure-AnalyzeRule$ajax$1$2 = false;
                            var3_4.log("ajax(" + var4_7 + ") error\n" + ExceptionsKt.stackTraceToString(it));
                        }
                        var3_5 = false;
                        var4_8 = false;
                        var4_9 = Result.exceptionOrNull-impl(var2_3);
                        if (var4_9 == null) {
                            v2 = var2_3;
                        } else {
                            it = var4_9;
                            $i$a$-getOrElse-AnalyzeRule$ajax$1$3 = false;
                            v2 = ThrowableExtensionsKt.getMsg(it);
                        }
                        return v2;
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
        }, 1, null);
    }

    @Nullable
    public final String toNumChapter(@Nullable String s) {
        String string = s;
        if (string == null) {
            return null;
        }
        Matcher matcher = titleNumPattern.matcher(s);
        if (matcher.find()) {
            return matcher.group(1) + StringUtils.INSTANCE.stringToInt(matcher.group(2)) + matcher.group(3);
        }
        return s;
    }

    public final void reGetBook() {
        Book book;
        BaseSource baseSource = this.source;
        BookSource bookSource = baseSource instanceof BookSource ? (BookSource)baseSource : null;
        BaseBook baseBook = this.getBook();
        Book book2 = book = baseBook instanceof Book ? (Book)baseBook : null;
        if (bookSource == null || book == null) {
            return;
        }
        BuildersKt.runBlocking$default(null, new Function2<CoroutineScope, Continuation<? super Book>, Object>(bookSource, this, book, null){
            int label;
            final /* synthetic */ BookSource $bookSource;
            final /* synthetic */ AnalyzeRule this$0;
            final /* synthetic */ Book $book;
            {
                this.$bookSource = $bookSource;
                this.this$0 = $receiver;
                this.$book = $book;
                super(2, $completion);
            }

            /*
             * WARNING - void declaration
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Nullable
            public final Object invokeSuspend(@NotNull Object object) {
                Object object2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0: {
                        ResultKt.throwOnFailure(object);
                        this.label = 1;
                        Object object3 = TimeoutKt.withTimeout(1800000L, (Function2)new Function2<CoroutineScope, Continuation<? super Book>, Object>(this.$bookSource, this.this$0, this.$book, null){
                            int label;
                            final /* synthetic */ BookSource $bookSource;
                            final /* synthetic */ AnalyzeRule this$0;
                            final /* synthetic */ Book $book;
                            {
                                this.$bookSource = $bookSource;
                                this.this$0 = $receiver;
                                this.$book = $book;
                                super(2, $completion);
                            }

                            /*
                             * Unable to fully structure code
                             */
                            @Nullable
                            public final Object invokeSuspend(@NotNull Object var1_1) {
                                var16_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                switch (this.label) {
                                    case 0: {
                                        ResultKt.throwOnFailure(var1_1);
                                        this.label = 1;
                                        v0 = new WebBook(this.$bookSource, false, null, this.this$0.getUserNameSpace(), 6, null).preciseSearch-0E7RQCE(this.$book.getName(), this.$book.getAuthor(), this);
                                        if (v0 == var16_2) {
                                            return var16_2;
                                        }
                                        ** GOTO lbl13
                                    }
                                    case 1: {
                                        ResultKt.throwOnFailure($result);
                                        v0 = ((Result)$result).unbox-impl();
lbl13:
                                        // 2 sources

                                        var2_3 = v0;
                                        var3_4 = false;
                                        ResultKt.throwOnFailure(var2_3);
                                        var3_5 = this.$book;
                                        var4_6 = false;
                                        var5_7 = false;
                                        it = (Book)var2_3;
                                        $i$a$-let-AnalyzeRule$reGetBook$1$1$1 = false;
                                        var3_5.setBookUrl(it.getBookUrl());
                                        $this$forEach$iv = it.getVariableMap();
                                        $i$f$forEach = false;
                                        var10_12 = $this$forEach$iv;
                                        var11_13 = false;
                                        var12_14 = var10_12.entrySet().iterator();
                                        while (var12_14.hasNext()) {
                                            entry = element$iv = var12_14.next();
                                            $i$a$-forEach-AnalyzeRule$reGetBook$1$1$1$1 = false;
                                            var3_5.putVariable((String)entry.getKey(), (String)entry.getValue());
                                        }
                                        this.label = 2;
                                        v1 = new WebBook(this.$bookSource, false, null, this.this$0.getUserNameSpace(), 6, null).getBookInfo(this.$book, false, (Continuation<? super Book>)this);
                                        if (v1 == var16_2) {
                                            return var16_2;
                                        }
                                        ** GOTO lbl41
                                    }
                                    case 2: {
                                        ResultKt.throwOnFailure($result);
                                        v1 = $result;
lbl41:
                                        // 2 sources

                                        return v1;
                                    }
                                }
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }

                            @NotNull
                            public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                                return (Continuation)((Object)new /* invalid duplicate definition of identical inner class */);
                            }

                            @Nullable
                            public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Book> p2) {
                                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                            }
                        }, this);
                        if (object3 != object2) return object3;
                        return object2;
                    }
                    case 1: {
                        void $result;
                        ResultKt.throwOnFailure($result);
                        Object object3 = $result;
                        return object3;
                    }
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            @NotNull
            public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                return (Continuation)((Object)new /* invalid duplicate definition of identical inner class */);
            }

            @Nullable
            public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Book> p2) {
                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
            }
        }, 1, null);
    }

    public final void refreshBookUrl() {
        BuildersKt.runBlocking$default(null, new Function2<CoroutineScope, Continuation<? super Unit>, Object>(this, null){
            Object L$0;
            int label;
            final /* synthetic */ AnalyzeRule this$0;
            {
                this.this$0 = $receiver;
                super(2, $completion);
            }

            /*
             * Unable to fully structure code
             */
            @Nullable
            public final Object invokeSuspend(@NotNull Object var1_1) {
                var13_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0: {
                        ResultKt.throwOnFailure(var1_1);
                        var3_3 = AnalyzeRule.access$getSource$p(this.this$0);
                        bookSource = var3_3 instanceof BookSource != false ? (BookSource)var3_3 : null;
                        var4_5 = this.this$0.getBook();
                        v0 = book = var4_5 instanceof Book != false ? (Book)var4_5 : null;
                        if (bookSource == null || book == null) {
                            return Unit.INSTANCE;
                        }
                        this.L$0 = book;
                        this.label = 1;
                        v1 = WebBook.searchBook$default(new WebBook(bookSource, false, null, this.this$0.getUserNameSpace(), 6, null), book.getName(), null, this, 2, null);
                        if (v1 == var13_2) {
                            return var13_2;
                        }
                        ** GOTO lbl21
                    }
                    case 1: {
                        var3_3 = (Book)this.L$0;
                        ResultKt.throwOnFailure($result);
                        v1 = $result;
lbl21:
                        // 2 sources

                        books = (List)v1;
                        $this$forEach$iv = books;
                        $i$f$forEach = false;
                        for (T element$iv : $this$forEach$iv) {
                            it = (SearchBook)element$iv;
                            $i$a$-forEach-AnalyzeRule$refreshBookUrl$1$1 = false;
                            if (!Intrinsics.areEqual(it.getName(), var3_3.getName()) || !Intrinsics.areEqual(it.getAuthor(), var3_3.getAuthor())) continue;
                            var3_3.setBookUrl(it.getBookUrl());
                            var11_12 = it.getTocUrl();
                            var12_13 = false;
                            if (StringsKt.isBlank(var11_12) == false) {
                                var3_3.setTocUrl(it.getTocUrl());
                            }
                            return Unit.INSTANCE;
                        }
                        return Unit.INSTANCE;
                    }
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            @NotNull
            public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                return (Continuation)((Object)new /* invalid duplicate definition of identical inner class */);
            }

            @Nullable
            public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Unit> p2) {
                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
            }
        }, 1, null);
    }

    public final void refreshTocUrl() {
        BuildersKt.runBlocking$default(null, new Function2<CoroutineScope, Continuation<? super Unit>, Object>(this, null){
            int label;
            final /* synthetic */ AnalyzeRule this$0;
            {
                this.this$0 = $receiver;
                super(2, $completion);
            }

            /*
             * WARNING - void declaration
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Nullable
            public final Object invokeSuspend(@NotNull Object object) {
                Object object2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0: {
                        Book book;
                        ResultKt.throwOnFailure(object);
                        BaseSource baseSource = AnalyzeRule.access$getSource$p(this.this$0);
                        BookSource bookSource = baseSource instanceof BookSource ? (BookSource)baseSource : null;
                        BaseBook baseBook = this.this$0.getBook();
                        Book book2 = book = baseBook instanceof Book ? (Book)baseBook : null;
                        if (bookSource == null || book == null) {
                            return Unit.INSTANCE;
                        }
                        this.label = 1;
                        Object object3 = WebBook.getBookInfo$default(new WebBook(bookSource, false, null, this.this$0.getUserNameSpace(), 6, null), book, false, (Continuation)this, 2, null);
                        if (object3 != object2) return Unit.INSTANCE;
                        return object2;
                    }
                    case 1: {
                        void $result;
                        ResultKt.throwOnFailure($result);
                        Object object3 = $result;
                        return Unit.INSTANCE;
                    }
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            @NotNull
            public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                return (Continuation)((Object)new /* invalid duplicate definition of identical inner class */);
            }

            @Nullable
            public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Unit> p2) {
                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
            }
        }, 1, null);
    }

    @Override
    @Nullable
    public byte[] aesBase64DecodeToByteArray(@NotNull String str, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.aesBase64DecodeToByteArray(this, str, key, transformation, iv);
    }

    @Override
    @Nullable
    public String aesBase64DecodeToString(@NotNull String str, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.aesBase64DecodeToString(this, str, key, transformation, iv);
    }

    @Override
    @Nullable
    public String aesDecodeArgsBase64Str(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return JsExtensions.DefaultImpls.aesDecodeArgsBase64Str(this, data, key, mode, padding, iv);
    }

    @Override
    @Nullable
    public byte[] aesDecodeToByteArray(@NotNull String str, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.aesDecodeToByteArray(this, str, key, transformation, iv);
    }

    @Override
    @Nullable
    public String aesDecodeToString(@NotNull String str, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.aesDecodeToString(this, str, key, transformation, iv);
    }

    @Override
    @Nullable
    public String aesEncodeArgsBase64Str(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return JsExtensions.DefaultImpls.aesEncodeArgsBase64Str(this, data, key, mode, padding, iv);
    }

    @Override
    @Nullable
    public byte[] aesEncodeToBase64ByteArray(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.aesEncodeToBase64ByteArray(this, data, key, transformation, iv);
    }

    @Override
    @Nullable
    public String aesEncodeToBase64String(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.aesEncodeToBase64String(this, data, key, transformation, iv);
    }

    @Override
    @Nullable
    public byte[] aesEncodeToByteArray(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.aesEncodeToByteArray(this, data, key, transformation, iv);
    }

    @Override
    @Nullable
    public String aesEncodeToString(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.aesEncodeToString(this, data, key, transformation, iv);
    }

    @Override
    @NotNull
    public StrResponse[] ajaxAll(@NotNull String[] urlList) {
        return JsExtensions.DefaultImpls.ajaxAll(this, urlList);
    }

    @Override
    @NotNull
    public String androidId() {
        return JsExtensions.DefaultImpls.androidId(this);
    }

    @Override
    @NotNull
    public String base64Decode(@NotNull String str) {
        return JsExtensions.DefaultImpls.base64Decode(this, str);
    }

    @Override
    @NotNull
    public String base64Decode(@NotNull String str, int flags) {
        return JsExtensions.DefaultImpls.base64Decode(this, str, flags);
    }

    @Override
    @Nullable
    public byte[] base64DecodeToByteArray(@Nullable String str) {
        return JsExtensions.DefaultImpls.base64DecodeToByteArray(this, str);
    }

    @Override
    @Nullable
    public byte[] base64DecodeToByteArray(@Nullable String str, int flags) {
        return JsExtensions.DefaultImpls.base64DecodeToByteArray(this, str, flags);
    }

    @Override
    @Nullable
    public String base64Encode(@NotNull String str) {
        return JsExtensions.DefaultImpls.base64Encode(this, str);
    }

    @Override
    @Nullable
    public String base64Encode(@NotNull String str, int flags) {
        return JsExtensions.DefaultImpls.base64Encode(this, str, flags);
    }

    @Override
    @Nullable
    public String cacheFile(@NotNull String urlStr) {
        return JsExtensions.DefaultImpls.cacheFile(this, urlStr);
    }

    @Override
    @Nullable
    public String cacheFile(@NotNull String urlStr, int saveTime) {
        return JsExtensions.DefaultImpls.cacheFile(this, urlStr, saveTime);
    }

    @Override
    @NotNull
    public StrResponse connect(@NotNull String urlStr) {
        return JsExtensions.DefaultImpls.connect(this, urlStr);
    }

    @Override
    @NotNull
    public StrResponse connect(@NotNull String urlStr, @Nullable String header) {
        return JsExtensions.DefaultImpls.connect(this, urlStr, header);
    }

    @Override
    public void deleteFile(@NotNull String path) {
        JsExtensions.DefaultImpls.deleteFile(this, path);
    }

    @Override
    @Nullable
    public String desBase64DecodeToString(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.desBase64DecodeToString(this, data, key, transformation, iv);
    }

    @Override
    @Nullable
    public String desDecodeToString(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.desDecodeToString(this, data, key, transformation, iv);
    }

    @Override
    @Nullable
    public String desEncodeToBase64String(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.desEncodeToBase64String(this, data, key, transformation, iv);
    }

    @Override
    @Nullable
    public String desEncodeToString(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return JsExtensions.DefaultImpls.desEncodeToString(this, data, key, transformation, iv);
    }

    @Override
    @Nullable
    public String digestBase64Str(@NotNull String data, @NotNull String algorithm) {
        return JsExtensions.DefaultImpls.digestBase64Str(this, data, algorithm);
    }

    @Override
    @Nullable
    public String digestHex(@NotNull String data, @NotNull String algorithm) {
        return JsExtensions.DefaultImpls.digestHex(this, data, algorithm);
    }

    @Override
    @NotNull
    public String downloadFile(@NotNull String content, @NotNull String url2) {
        return JsExtensions.DefaultImpls.downloadFile(this, content, url2);
    }

    @Override
    @NotNull
    public String encodeURI(@NotNull String str) {
        return JsExtensions.DefaultImpls.encodeURI(this, str);
    }

    @Override
    @NotNull
    public String encodeURI(@NotNull String str, @NotNull String enc) {
        return JsExtensions.DefaultImpls.encodeURI(this, str, enc);
    }

    @Override
    @NotNull
    public Connection.Response get(@NotNull String urlStr, @NotNull Map<String, String> headers) {
        return JsExtensions.DefaultImpls.get(this, urlStr, headers);
    }

    @Override
    @NotNull
    public String getCookie(@NotNull String tag, @Nullable String key) {
        return JsExtensions.DefaultImpls.getCookie(this, tag, key);
    }

    @Override
    @NotNull
    public File getFile(@NotNull String path) {
        return JsExtensions.DefaultImpls.getFile(this, path);
    }

    @Override
    @NotNull
    public String getTxtInFolder(@NotNull String unzipPath) {
        return JsExtensions.DefaultImpls.getTxtInFolder(this, unzipPath);
    }

    @Override
    @Nullable
    public byte[] getZipByteArrayContent(@NotNull String url2, @NotNull String path) {
        return JsExtensions.DefaultImpls.getZipByteArrayContent(this, url2, path);
    }

    @Override
    @NotNull
    public String getZipStringContent(@NotNull String url2, @NotNull String path) {
        return JsExtensions.DefaultImpls.getZipStringContent(this, url2, path);
    }

    @Override
    @NotNull
    public String getZipStringContent(@NotNull String url2, @NotNull String path, @NotNull String charsetName) {
        return JsExtensions.DefaultImpls.getZipStringContent(this, url2, path, charsetName);
    }

    @Override
    @NotNull
    public Connection.Response head(@NotNull String urlStr, @NotNull Map<String, String> headers) {
        return JsExtensions.DefaultImpls.head(this, urlStr, headers);
    }

    @Override
    @NotNull
    public String htmlFormat(@NotNull String str) {
        return JsExtensions.DefaultImpls.htmlFormat(this, str);
    }

    @Override
    @NotNull
    public String importScript(@NotNull String path) {
        return JsExtensions.DefaultImpls.importScript(this, path);
    }

    @Override
    @NotNull
    public String log(@NotNull String msg) {
        return JsExtensions.DefaultImpls.log(this, msg);
    }

    @Override
    public void logType(@Nullable Object any) {
        JsExtensions.DefaultImpls.logType(this, any);
    }

    @Override
    public void longToast(@Nullable Object msg) {
        JsExtensions.DefaultImpls.longToast(this, msg);
    }

    @Override
    @NotNull
    public String md5Encode(@NotNull String str) {
        return JsExtensions.DefaultImpls.md5Encode(this, str);
    }

    @Override
    @NotNull
    public String md5Encode16(@NotNull String str) {
        return JsExtensions.DefaultImpls.md5Encode16(this, str);
    }

    @Override
    @NotNull
    public Connection.Response post(@NotNull String urlStr, @NotNull String body, @NotNull Map<String, String> headers) {
        return JsExtensions.DefaultImpls.post(this, urlStr, body, headers);
    }

    @Override
    @Nullable
    public QueryTTF queryBase64TTF(@Nullable String base64) {
        return JsExtensions.DefaultImpls.queryBase64TTF(this, base64);
    }

    @Override
    @Nullable
    public QueryTTF queryTTF(@Nullable String str) {
        return JsExtensions.DefaultImpls.queryTTF(this, str);
    }

    @Override
    @NotNull
    public String randomUUID() {
        return JsExtensions.DefaultImpls.randomUUID(this);
    }

    @Override
    @Nullable
    public byte[] readFile(@NotNull String path) {
        return JsExtensions.DefaultImpls.readFile(this, path);
    }

    @Override
    @NotNull
    public String readTxtFile(@NotNull String path) {
        return JsExtensions.DefaultImpls.readTxtFile(this, path);
    }

    @Override
    @NotNull
    public String readTxtFile(@NotNull String path, @NotNull String charsetName) {
        return JsExtensions.DefaultImpls.readTxtFile(this, path, charsetName);
    }

    @Override
    @NotNull
    public String replaceFont(@NotNull String text, @Nullable QueryTTF font1, @Nullable QueryTTF font2) {
        return JsExtensions.DefaultImpls.replaceFont(this, text, font1, font2);
    }

    @Override
    @NotNull
    public String timeFormat(long time) {
        return JsExtensions.DefaultImpls.timeFormat(this, time);
    }

    @Override
    @Nullable
    public String timeFormatUTC(long time, @NotNull String format, int sh) {
        return JsExtensions.DefaultImpls.timeFormatUTC(this, time, format, sh);
    }

    @Override
    public void toast(@Nullable Object msg) {
        JsExtensions.DefaultImpls.toast(this, msg);
    }

    @Override
    @Nullable
    public String tripleDESDecodeArgsBase64Str(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return JsExtensions.DefaultImpls.tripleDESDecodeArgsBase64Str(this, data, key, mode, padding, iv);
    }

    @Override
    @Nullable
    public String tripleDESDecodeStr(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return JsExtensions.DefaultImpls.tripleDESDecodeStr(this, data, key, mode, padding, iv);
    }

    @Override
    @Nullable
    public String tripleDESEncodeArgsBase64Str(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return JsExtensions.DefaultImpls.tripleDESEncodeArgsBase64Str(this, data, key, mode, padding, iv);
    }

    @Override
    @Nullable
    public String tripleDESEncodeBase64Str(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return JsExtensions.DefaultImpls.tripleDESEncodeBase64Str(this, data, key, mode, padding, iv);
    }

    @Override
    @NotNull
    public String unzipFile(@NotNull String zipPath) {
        return JsExtensions.DefaultImpls.unzipFile(this, zipPath);
    }

    @Override
    @NotNull
    public String utf8ToGbk(@NotNull String str) {
        return JsExtensions.DefaultImpls.utf8ToGbk(this, str);
    }

    @Override
    @Nullable
    public String webView(@Nullable String html, @Nullable String url2, @Nullable String js) {
        return JsExtensions.DefaultImpls.webView(this, html, url2, js);
    }

    @JvmOverloads
    @NotNull
    public final AnalyzeRule setContent(@Nullable Object content) {
        return AnalyzeRule.setContent$default(this, content, null, 2, null);
    }

    @JvmOverloads
    @Nullable
    public final List<String> getStringList(@Nullable String rule, @Nullable Object mContent) {
        return AnalyzeRule.getStringList$default(this, rule, mContent, false, 4, null);
    }

    @JvmOverloads
    @Nullable
    public final List<String> getStringList(@Nullable String rule) {
        return AnalyzeRule.getStringList$default(this, rule, null, false, 6, null);
    }

    @JvmOverloads
    @Nullable
    public final List<String> getStringList(@NotNull List<SourceRule> ruleList, @Nullable Object mContent) {
        Intrinsics.checkNotNullParameter(ruleList, "ruleList");
        return AnalyzeRule.getStringList$default(this, ruleList, mContent, false, 4, null);
    }

    @JvmOverloads
    @Nullable
    public final List<String> getStringList(@NotNull List<SourceRule> ruleList) {
        Intrinsics.checkNotNullParameter(ruleList, "ruleList");
        return AnalyzeRule.getStringList$default(this, ruleList, null, false, 6, null);
    }

    @JvmOverloads
    @NotNull
    public final String getString(@Nullable String ruleStr, @Nullable Object mContent) {
        return AnalyzeRule.getString$default(this, ruleStr, mContent, false, 4, null);
    }

    @JvmOverloads
    @NotNull
    public final String getString(@Nullable String ruleStr) {
        return AnalyzeRule.getString$default(this, ruleStr, null, false, 6, null);
    }

    @JvmOverloads
    @NotNull
    public final String getString(@NotNull List<SourceRule> ruleList, @Nullable Object mContent) {
        Intrinsics.checkNotNullParameter(ruleList, "ruleList");
        return AnalyzeRule.getString$default(this, ruleList, mContent, false, 4, null);
    }

    @JvmOverloads
    @NotNull
    public final String getString(@NotNull List<SourceRule> ruleList) {
        Intrinsics.checkNotNullParameter(ruleList, "ruleList");
        return AnalyzeRule.getString$default(this, ruleList, null, false, 6, null);
    }

    public static final /* synthetic */ BaseSource access$getSource$p(AnalyzeRule $this) {
        return $this.source;
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0019\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010(\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0010\u0010)\u001a\u00020*2\b\u0010+\u001a\u0004\u0018\u00010\u0001J\u0010\u0010,\u001a\u00020*2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0004\u001a\u00020\u0005X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR0\u0010\u000f\u001a\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0010j\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003`\u0011X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u0003X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020\u0003X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u001c\"\u0004\b!\u0010\u001eR\u001a\u0010\"\u001a\u00020\u0003X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u001c\"\u0004\b$\u0010\u001eR\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00030&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010'\u001a\b\u0012\u0004\u0012\u00020\b0&X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeRule$SourceRule;", "", "ruleStr", "", "mode", "Lio/legado/app/model/analyzeRule/AnalyzeRule$Mode;", "(Lio/legado/app/model/analyzeRule/AnalyzeRule;Ljava/lang/String;Lio/legado/app/model/analyzeRule/AnalyzeRule$Mode;)V", "defaultRuleType", "", "getRuleType", "jsRuleType", "getMode$reader_pro", "()Lio/legado/app/model/analyzeRule/AnalyzeRule$Mode;", "setMode$reader_pro", "(Lio/legado/app/model/analyzeRule/AnalyzeRule$Mode;)V", "putMap", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "getPutMap$reader_pro", "()Ljava/util/HashMap;", "replaceFirst", "", "getReplaceFirst$reader_pro", "()Z", "setReplaceFirst$reader_pro", "(Z)V", "replaceRegex", "getReplaceRegex$reader_pro", "()Ljava/lang/String;", "setReplaceRegex$reader_pro", "(Ljava/lang/String;)V", "replacement", "getReplacement$reader_pro", "setReplacement$reader_pro", "rule", "getRule$reader_pro", "setRule$reader_pro", "ruleParam", "Ljava/util/ArrayList;", "ruleType", "isRule", "makeUpRule", "", "result", "splitRegex", "reader-pro"})
    public final class SourceRule {
        @NotNull
        private Mode mode;
        @NotNull
        private String rule;
        @NotNull
        private String replaceRegex;
        @NotNull
        private String replacement;
        private boolean replaceFirst;
        @NotNull
        private final HashMap<String, String> putMap;
        @NotNull
        private final ArrayList<String> ruleParam;
        @NotNull
        private final ArrayList<Integer> ruleType;
        private final int getRuleType;
        private final int jsRuleType;
        private final int defaultRuleType;

        public SourceRule(@NotNull String ruleStr, Mode mode) {
            int n;
            String string;
            boolean bl;
            int n2;
            String string2;
            String string3;
            Intrinsics.checkNotNullParameter(AnalyzeRule.this, "this$0");
            Intrinsics.checkNotNullParameter(ruleStr, "ruleStr");
            Intrinsics.checkNotNullParameter((Object)mode, "mode");
            this.mode = mode;
            this.replaceRegex = "";
            this.replacement = "";
            this.putMap = new HashMap();
            this.ruleParam = new ArrayList();
            this.ruleType = new ArrayList();
            this.getRuleType = -2;
            this.jsRuleType = -1;
            if (this.mode == Mode.Js || this.mode == Mode.Regex) {
                string3 = ruleStr;
            } else if (StringsKt.startsWith(ruleStr, "@CSS:", true)) {
                this.mode = Mode.Default;
                string3 = ruleStr;
            } else if (StringsKt.startsWith$default(ruleStr, "@@", false, 2, null)) {
                this.mode = Mode.Default;
                string2 = ruleStr;
                n2 = 2;
                bl = false;
                String string4 = string2.substring(n2);
                string3 = string4;
                Intrinsics.checkNotNullExpressionValue(string4, "(this as java.lang.String).substring(startIndex)");
            } else if (StringsKt.startsWith(ruleStr, "@XPath:", true)) {
                this.mode = Mode.XPath;
                string2 = ruleStr;
                n2 = 7;
                bl = false;
                String string5 = string2.substring(n2);
                string3 = string5;
                Intrinsics.checkNotNullExpressionValue(string5, "(this as java.lang.String).substring(startIndex)");
            } else if (StringsKt.startsWith(ruleStr, "@Json:", true)) {
                this.mode = Mode.Json;
                string2 = ruleStr;
                n2 = 6;
                bl = false;
                String string6 = string2.substring(n2);
                string3 = string6;
                Intrinsics.checkNotNullExpressionValue(string6, "(this as java.lang.String).substring(startIndex)");
            } else if (AnalyzeRule.this.isJSON || StringsKt.startsWith$default(ruleStr, "$.", false, 2, null) || StringsKt.startsWith$default(ruleStr, "$[", false, 2, null)) {
                this.mode = Mode.Json;
                string3 = ruleStr;
            } else if (StringsKt.startsWith$default(ruleStr, "/", false, 2, null)) {
                this.mode = Mode.XPath;
                string3 = ruleStr;
            } else {
                string3 = ruleStr;
            }
            this.rule = string3;
            this.rule = AnalyzeRule.this.splitPutRule(this.rule, this.putMap);
            int start2 = 0;
            String tmp = null;
            Matcher evalMatcher = evalPattern.matcher(this.rule);
            if (evalMatcher.find()) {
                string = this.rule;
                n = evalMatcher.start();
                int n3 = 0;
                String string7 = string;
                if (string7 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String string8 = string7.substring(start2, n);
                Intrinsics.checkNotNullExpressionValue(string8, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                tmp = string8;
                if (!(this.mode == Mode.Js || this.mode == Mode.Regex || evalMatcher.start() != 0 && StringsKt.contains$default((CharSequence)tmp, "##", false, 2, null))) {
                    this.mode = Mode.Regex;
                }
                do {
                    boolean bl2;
                    if (evalMatcher.start() > start2) {
                        string = this.rule;
                        n = evalMatcher.start();
                        n3 = 0;
                        String string9 = string;
                        if (string9 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                        }
                        String string10 = string9.substring(start2, n);
                        Intrinsics.checkNotNullExpressionValue(string10, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                        tmp = string10;
                        this.splitRegex(tmp);
                    }
                    string = evalMatcher.group();
                    Intrinsics.checkNotNullExpressionValue(string, "evalMatcher.group()");
                    tmp = string;
                    if (StringsKt.startsWith(tmp, "@get:", true)) {
                        this.ruleType.add(this.getRuleType);
                        string = tmp;
                        n = 6;
                        n3 = StringsKt.getLastIndex(tmp);
                        bl2 = false;
                        String string11 = string.substring(n, n3);
                        Intrinsics.checkNotNullExpressionValue(string11, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                        this.ruleParam.add(string11);
                    } else if (StringsKt.startsWith$default(tmp, "{{", false, 2, null)) {
                        this.ruleType.add(this.jsRuleType);
                        string = tmp;
                        n = 2;
                        n3 = tmp.length() - 2;
                        bl2 = false;
                        String string12 = string.substring(n, n3);
                        Intrinsics.checkNotNullExpressionValue(string12, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                        this.ruleParam.add(string12);
                    } else {
                        this.splitRegex(tmp);
                    }
                    start2 = evalMatcher.end();
                } while (evalMatcher.find());
            }
            if (this.rule.length() > start2) {
                string = this.rule;
                n = 0;
                String string13 = string;
                if (string13 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String string14 = string13.substring(start2);
                Intrinsics.checkNotNullExpressionValue(string14, "(this as java.lang.String).substring(startIndex)");
                tmp = string14;
                this.splitRegex(tmp);
            }
        }

        public /* synthetic */ SourceRule(String string, Mode mode, int n, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n & 2) != 0) {
                mode = Mode.Default;
            }
            this(string, mode);
        }

        @NotNull
        public final Mode getMode$reader_pro() {
            return this.mode;
        }

        public final void setMode$reader_pro(@NotNull Mode mode) {
            Intrinsics.checkNotNullParameter((Object)mode, "<set-?>");
            this.mode = mode;
        }

        @NotNull
        public final String getRule$reader_pro() {
            return this.rule;
        }

        public final void setRule$reader_pro(@NotNull String string) {
            Intrinsics.checkNotNullParameter(string, "<set-?>");
            this.rule = string;
        }

        @NotNull
        public final String getReplaceRegex$reader_pro() {
            return this.replaceRegex;
        }

        public final void setReplaceRegex$reader_pro(@NotNull String string) {
            Intrinsics.checkNotNullParameter(string, "<set-?>");
            this.replaceRegex = string;
        }

        @NotNull
        public final String getReplacement$reader_pro() {
            return this.replacement;
        }

        public final void setReplacement$reader_pro(@NotNull String string) {
            Intrinsics.checkNotNullParameter(string, "<set-?>");
            this.replacement = string;
        }

        public final boolean getReplaceFirst$reader_pro() {
            return this.replaceFirst;
        }

        public final void setReplaceFirst$reader_pro(boolean bl) {
            this.replaceFirst = bl;
        }

        @NotNull
        public final HashMap<String, String> getPutMap$reader_pro() {
            return this.putMap;
        }

        private final void splitRegex(String ruleStr) {
            int n;
            String string;
            int start2 = 0;
            String tmp = null;
            String[] stringArray = new String[]{"##"};
            List ruleStrArray = StringsKt.split$default((CharSequence)ruleStr, stringArray, false, 0, 6, null);
            Matcher regexMatcher = regexPattern.matcher((CharSequence)ruleStrArray.get(0));
            if (regexMatcher.find()) {
                if (this.mode != Mode.Js && this.mode != Mode.Regex) {
                    this.mode = Mode.Regex;
                }
                do {
                    boolean bl;
                    if (regexMatcher.start() > start2) {
                        string = ruleStr;
                        n = regexMatcher.start();
                        bl = false;
                        String string2 = string;
                        if (string2 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                        }
                        String string3 = string2.substring(start2, n);
                        Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                        tmp = string3;
                        this.ruleType.add(this.defaultRuleType);
                        this.ruleParam.add(tmp);
                    }
                    string = regexMatcher.group();
                    Intrinsics.checkNotNullExpressionValue(string, "regexMatcher.group()");
                    string = tmp = string;
                    n = 1;
                    bl = false;
                    String string4 = string.substring(n);
                    Intrinsics.checkNotNullExpressionValue(string4, "(this as java.lang.String).substring(startIndex)");
                    string = string4;
                    n = 0;
                    this.ruleType.add(Integer.parseInt(string));
                    this.ruleParam.add(tmp);
                    start2 = regexMatcher.end();
                } while (regexMatcher.find());
            }
            if (ruleStr.length() > start2) {
                string = ruleStr;
                n = 0;
                String string5 = string;
                if (string5 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String string6 = string5.substring(start2);
                Intrinsics.checkNotNullExpressionValue(string6, "(this as java.lang.String).substring(startIndex)");
                tmp = string6;
                this.ruleType.add(this.defaultRuleType);
                this.ruleParam.add(tmp);
            }
        }

        public final void makeUpRule(@Nullable Object result2) {
            StringBuilder infoVal = new StringBuilder();
            Collection collection = this.ruleParam;
            int n = 0;
            if (!collection.isEmpty()) {
                int index = this.ruleParam.size();
                while (true) {
                    boolean bl;
                    Object object;
                    Object object2;
                    n = index;
                    index = n + -1;
                    if (n <= 0) break;
                    Object object3 = this.ruleType.get(index);
                    Intrinsics.checkNotNullExpressionValue(object3, "ruleType[index]");
                    int regType = ((Number)object3).intValue();
                    if (regType > this.defaultRuleType) {
                        Unit unit;
                        Object object4 = object2 = result2 instanceof List ? (List)result2 : null;
                        if (object2 == null) {
                            unit = null;
                        } else {
                            String string;
                            object = object2;
                            boolean bl2 = false;
                            bl = false;
                            List $this$makeUpRule_u24lambda_u2d1 = object;
                            boolean bl3 = false;
                            if ($this$makeUpRule_u24lambda_u2d1.size() > regType && (string = (String)$this$makeUpRule_u24lambda_u2d1.get(regType)) != null) {
                                String string2 = string;
                                boolean bl4 = false;
                                boolean bl5 = false;
                                String it = string2;
                                boolean bl6 = false;
                                infoVal.insert(0, it);
                            }
                            unit = Unit.INSTANCE;
                        }
                        if ((object3 = unit) != null) continue;
                        infoVal.insert(0, this.ruleParam.get(index));
                        continue;
                    }
                    if (regType == this.jsRuleType) {
                        object3 = this.ruleParam.get(index);
                        Intrinsics.checkNotNullExpressionValue(object3, "ruleParam[index]");
                        if (this.isRule((String)object3)) {
                            object3 = new SourceRule[1];
                            object2 = this.ruleParam.get(index);
                            Intrinsics.checkNotNullExpressionValue(object2, "ruleParam[index]");
                            object3[0] = new SourceRule((String)object2, null, 2, null);
                            object3 = AnalyzeRule.getString$default(AnalyzeRule.this, CollectionsKt.arrayListOf(object3), null, false, 6, null);
                            boolean bl7 = false;
                            boolean bl8 = false;
                            Object it = object3;
                            boolean bl9 = false;
                            infoVal.insert(0, (String)it);
                            continue;
                        }
                        object2 = this.ruleParam.get(index);
                        Intrinsics.checkNotNullExpressionValue(object2, "ruleParam[index]");
                        Object jsEval = AnalyzeRule.this.evalJS((String)object2, result2);
                        if (jsEval == null) continue;
                        if (jsEval instanceof String) {
                            infoVal.insert(0, (String)jsEval);
                            continue;
                        }
                        if (jsEval instanceof Double && ((Number)jsEval).doubleValue() % 1.0 == 0.0) {
                            object2 = StringCompanionObject.INSTANCE;
                            object = "%.0f";
                            Object[] objectArray = new Object[]{jsEval};
                            bl = false;
                            String string = String.format((String)object, Arrays.copyOf(objectArray, objectArray.length));
                            Intrinsics.checkNotNullExpressionValue(string, "java.lang.String.format(format, *args)");
                            infoVal.insert(0, string);
                            continue;
                        }
                        infoVal.insert(0, jsEval.toString());
                        continue;
                    }
                    if (regType == this.getRuleType) {
                        object3 = this.ruleParam.get(index);
                        Intrinsics.checkNotNullExpressionValue(object3, "ruleParam[index]");
                        infoVal.insert(0, AnalyzeRule.this.get((String)object3));
                        continue;
                    }
                    infoVal.insert(0, this.ruleParam.get(index));
                }
                String string = infoVal.toString();
                Intrinsics.checkNotNullExpressionValue(string, "infoVal.toString()");
                this.rule = string;
            }
            Object object = new String[]{"##"};
            List ruleStrS = StringsKt.split$default((CharSequence)this.rule, object, false, 0, 6, null);
            object = (String)ruleStrS.get(0);
            boolean bl = false;
            if (object == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.CharSequence");
            }
            this.rule = ((Object)StringsKt.trim((CharSequence)object)).toString();
            if (ruleStrS.size() > 1) {
                this.replaceRegex = (String)ruleStrS.get(1);
            }
            if (ruleStrS.size() > 2) {
                this.replacement = (String)ruleStrS.get(2);
            }
            if (ruleStrS.size() > 3) {
                this.replaceFirst = true;
            }
        }

        private final boolean isRule(String ruleStr) {
            return StringsKt.startsWith$default((CharSequence)ruleStr, '@', false, 2, null) || StringsKt.startsWith$default(ruleStr, "$.", false, 2, null) || StringsKt.startsWith$default(ruleStr, "$[", false, 2, null) || StringsKt.startsWith$default(ruleStr, "//", false, 2, null);
        }
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeRule$Mode;", "", "(Ljava/lang/String;I)V", "XPath", "Json", "Default", "Js", "Regex", "reader-pro"})
    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode XPath = new Mode();
        public static final /* enum */ Mode Json = new Mode();
        public static final /* enum */ Mode Default = new Mode();
        public static final /* enum */ Mode Js = new Mode();
        public static final /* enum */ Mode Regex = new Mode();
        private static final /* synthetic */ Mode[] $VALUES;

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String value) {
            return Enum.valueOf(Mode.class, value);
        }

        static {
            $VALUES = modeArray = new Mode[]{Mode.XPath, Mode.Json, Mode.Default, Mode.Js, Mode.Regex};
        }
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeRule$Companion;", "", "()V", "evalPattern", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "putPattern", "regexPattern", "titleNumPattern", "reader-pro"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 5, 1}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[Mode.values().length];
            nArray[Mode.Js.ordinal()] = 1;
            nArray[Mode.Json.ordinal()] = 2;
            nArray[Mode.XPath.ordinal()] = 3;
            nArray[Mode.Default.ordinal()] = 4;
            nArray[Mode.Regex.ordinal()] = 5;
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

