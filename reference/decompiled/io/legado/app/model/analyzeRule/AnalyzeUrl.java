/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.model.analyzeRule;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.script.Bindings;
import com.script.SimpleBindings;
import io.legado.app.adapters.ReaderAdapterHelper;
import io.legado.app.adapters.ReaderAdapterInterface;
import io.legado.app.constant.AppConst;
import io.legado.app.constant.AppPattern;
import io.legado.app.data.entities.BaseSource;
import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookChapter;
import io.legado.app.exception.ConcurrentException;
import io.legado.app.help.CacheManager;
import io.legado.app.help.JsExtensions;
import io.legado.app.help.http.CookieStore;
import io.legado.app.help.http.HttpHelperKt;
import io.legado.app.help.http.OkHttpUtilsKt;
import io.legado.app.help.http.RequestMethod;
import io.legado.app.help.http.StrResponse;
import io.legado.app.model.DebugLog;
import io.legado.app.model.analyzeRule.AnalyzeUrl;
import io.legado.app.model.analyzeRule.QueryTTF;
import io.legado.app.model.analyzeRule.RuleAnalyzer;
import io.legado.app.model.analyzeRule.RuleDataInterface;
import io.legado.app.utils.Base64;
import io.legado.app.utils.EncoderUtils;
import io.legado.app.utils.GsonExtensionsKt;
import io.legado.app.utils.NetworkUtils;
import io.legado.app.utils.ParameterizedTypeImpl;
import io.legado.app.utils.StringExtensionsKt;
import io.legado.app.utils.StringUtils;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Connection;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0017\u0018\u0000 l2\u00020\u0001:\u0003lmnB\u008f\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\u0016\b\u0002\u0010\u0010\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0011\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\u0002\u0010\u0014J\u0010\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020\u0003H\u0002J\b\u0010C\u001a\u00020AH\u0002J\b\u0010D\u001a\u00020AH\u0002J\u001c\u0010E\u001a\u0004\u0018\u00010F2\u0006\u0010G\u001a\u00020\u00032\n\b\u0002\u0010H\u001a\u0004\u0018\u00010FJ\u0012\u0010I\u001a\u00020A2\b\u0010J\u001a\u0004\u0018\u00010KH\u0002J\n\u0010L\u001a\u0004\u0018\u00010KH\u0002J\u000e\u0010M\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003J\u0006\u0010N\u001a\u00020OJ\u0011\u0010P\u001a\u00020OH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010QJ\n\u0010R\u001a\u0004\u0018\u00010\u0013H\u0016J\u0006\u0010S\u001a\u00020TJ\u0011\u0010U\u001a\u00020TH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010QJ\n\u0010V\u001a\u0004\u0018\u00010\u000bH\u0016J*\u0010W\u001a\u00020X2\n\b\u0002\u0010G\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010Y\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010>\u001a\u00020\"H\u0007J3\u0010Z\u001a\u00020X2\n\b\u0002\u0010G\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010Y\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010>\u001a\u00020\"H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010[J\u0006\u0010\\\u001a\u00020\u0003J\b\u0010]\u001a\u00020\u0003H\u0016J\u0006\u0010^\u001a\u00020AJ\u0006\u0010_\u001a\u00020\"J\u0016\u0010`\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010a\u001a\u00020\u0003J\b\u0010b\u001a\u00020AH\u0002J\u000e\u0010c\u001a\u00020A2\u0006\u0010d\u001a\u00020TJ\u0012\u0010e\u001a\u00020A2\b\u0010f\u001a\u0004\u0018\u00010\u0003H\u0002J)\u0010g\u001a\u00020X2\u0006\u0010h\u001a\u00020\u00032\u0006\u0010i\u001a\u00020F2\u0006\u0010j\u001a\u00020\u0003H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010kR\u001a\u0010\t\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\"\u0010\u001a\u001a\u0004\u0018\u00010\u00032\b\u0010\u0019\u001a\u0004\u0018\u00010\u0003@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0016R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u000e\u0010!\u001a\u00020\"X\u0082\u0004\u00a2\u0006\u0002\n\u0000R*\u0010#\u001a\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030$j\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003`%X\u0082\u0004\u00a2\u0006\u0002\n\u0000R-\u0010&\u001a\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030'j\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003`(\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0016R\u000e\u0010-\u001a\u00020.X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u00101\u001a\u0004\b/\u00100R\u0010\u00102\u001a\u0004\u0018\u00010\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00103\u001a\u0004\u0018\u00010\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u00105\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u0003@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010\u0016R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0015\u0010\b\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u00101\u001a\u0004\b7\u00100R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u0010\u0016R\"\u00109\u001a\u0004\u0018\u00010\u00032\b\u0010\u0019\u001a\u0004\u0018\u00010\u0003@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u0010\u0016R\u001e\u0010;\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u0003@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b<\u0010\u0016R\u000e\u0010=\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\"X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010?\u001a\u0004\u0018\u00010\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006o"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeUrl;", "Lio/legado/app/help/JsExtensions;", "mUrl", "", "key", "page", "", "speakText", "speakSpeed", "baseUrl", "source", "Lio/legado/app/data/entities/BaseSource;", "ruleData", "Lio/legado/app/model/analyzeRule/RuleDataInterface;", "chapter", "Lio/legado/app/data/entities/BookChapter;", "headerMapF", "", "debugLog", "Lio/legado/app/model/DebugLog;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Lio/legado/app/data/entities/BaseSource;Lio/legado/app/model/analyzeRule/RuleDataInterface;Lio/legado/app/data/entities/BookChapter;Ljava/util/Map;Lio/legado/app/model/DebugLog;)V", "getBaseUrl", "()Ljava/lang/String;", "setBaseUrl", "(Ljava/lang/String;)V", "<set-?>", "body", "getBody", "charset", "getDebugLog", "()Lio/legado/app/model/DebugLog;", "setDebugLog", "(Lio/legado/app/model/DebugLog;)V", "enabledCookieJar", "", "fieldMap", "Ljava/util/LinkedHashMap;", "Lkotlin/collections/LinkedHashMap;", "headerMap", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "getHeaderMap", "()Ljava/util/HashMap;", "getKey", "getMUrl", "method", "Lio/legado/app/help/http/RequestMethod;", "getPage", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "proxy", "queryStr", "retry", "ruleUrl", "getRuleUrl", "getSpeakSpeed", "getSpeakText", "type", "getType", "url", "getUrl", "urlNoQuery", "useWebView", "webJs", "analyzeFields", "", "fieldsTxt", "analyzeJs", "analyzeUrl", "evalJS", "", "jsStr", "result", "fetchEnd", "concurrentRecord", "Lio/legado/app/model/analyzeRule/AnalyzeUrl$ConcurrentRecord;", "fetchStart", "get", "getByteArray", "", "getByteArrayAwait", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLogger", "getResponse", "Lokhttp3/Response;", "getResponseAwait", "getSource", "getStrResponse", "Lio/legado/app/help/http/StrResponse;", "sourceRegex", "getStrResponseAwait", "(Ljava/lang/String;Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserAgent", "getUserNameSpace", "initUrl", "isPost", "put", "value", "replaceKeyPageJs", "saveCookieJar", "response", "setCookie", "tag", "upload", "fileName", "file", "contentType", "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "ConcurrentRecord", "UrlOption", "reader-pro"})
public final class AnalyzeUrl
implements JsExtensions {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final String mUrl;
    @Nullable
    private final String key;
    @Nullable
    private final Integer page;
    @Nullable
    private final String speakText;
    @Nullable
    private final Integer speakSpeed;
    @NotNull
    private String baseUrl;
    @Nullable
    private final BaseSource source;
    @Nullable
    private final RuleDataInterface ruleData;
    @Nullable
    private final BookChapter chapter;
    @Nullable
    private DebugLog debugLog;
    @NotNull
    private String ruleUrl;
    @NotNull
    private String url;
    @Nullable
    private String body;
    @Nullable
    private String type;
    @NotNull
    private final HashMap<String, String> headerMap;
    @NotNull
    private String urlNoQuery;
    @Nullable
    private String queryStr;
    @NotNull
    private final LinkedHashMap<String, String> fieldMap;
    @Nullable
    private String charset;
    @NotNull
    private RequestMethod method;
    @Nullable
    private String proxy;
    private int retry;
    private boolean useWebView;
    @Nullable
    private String webJs;
    private final boolean enabledCookieJar;
    @NotNull
    private static final Pattern paramPattern;
    private static final Pattern pagePattern;
    @NotNull
    private static final HashMap<String, ConcurrentRecord> concurrentRecordMap;

    public AnalyzeUrl(@NotNull String mUrl, @Nullable String key, @Nullable Integer page, @Nullable String speakText, @Nullable Integer speakSpeed, @NotNull String baseUrl, @Nullable BaseSource source, @Nullable RuleDataInterface ruleData, @Nullable BookChapter chapter, @Nullable Map<String, String> headerMapF, @Nullable DebugLog debugLog) {
        Map map;
        Intrinsics.checkNotNullParameter(mUrl, "mUrl");
        Intrinsics.checkNotNullParameter(baseUrl, "baseUrl");
        this.mUrl = mUrl;
        this.key = key;
        this.page = page;
        this.speakText = speakText;
        this.speakSpeed = speakSpeed;
        this.baseUrl = baseUrl;
        this.source = source;
        this.ruleData = ruleData;
        this.chapter = chapter;
        this.debugLog = debugLog;
        this.ruleUrl = "";
        this.url = "";
        this.headerMap = new HashMap();
        this.urlNoQuery = "";
        this.fieldMap = new LinkedHashMap();
        this.method = RequestMethod.GET;
        BaseSource baseSource = this.source;
        this.enabledCookieJar = baseSource == null ? false : ((map = baseSource.getEnabledCookieJar()) == null ? false : (Boolean)((Object)map));
        if (!StringExtensionsKt.isDataUrl(this.mUrl)) {
            BaseSource baseSource2;
            Map map2;
            boolean bl;
            Matcher urlMatcher = paramPattern.matcher(this.baseUrl);
            if (urlMatcher.find()) {
                map = this.baseUrl;
                int n = 0;
                int n2 = urlMatcher.start();
                bl = false;
                Map map3 = map;
                if (map3 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String string = ((String)((Object)map3)).substring(n, n2);
                Intrinsics.checkNotNullExpressionValue(string, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                this.baseUrl = string;
            }
            Map map4 = (map2 = headerMapF) == null ? (Map)((baseSource2 = this.source) == null ? null : baseSource2.getHeaderMap(true)) : (map = map2);
            if (map != null) {
                map2 = map;
                boolean bl2 = false;
                bl = false;
                Map it = map2;
                boolean bl3 = false;
                this.getHeaderMap().putAll(it);
                if (it.containsKey("proxy")) {
                    this.proxy = (String)it.get("proxy");
                    this.getHeaderMap().remove("proxy");
                }
            }
            this.initUrl();
        }
    }

    public /* synthetic */ AnalyzeUrl(String string, String string2, Integer n, String string3, Integer n2, String string4, BaseSource baseSource, RuleDataInterface ruleDataInterface, BookChapter bookChapter, Map map, DebugLog debugLog, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 2) != 0) {
            string2 = null;
        }
        if ((n3 & 4) != 0) {
            n = null;
        }
        if ((n3 & 8) != 0) {
            string3 = null;
        }
        if ((n3 & 0x10) != 0) {
            n2 = null;
        }
        if ((n3 & 0x20) != 0) {
            string4 = "";
        }
        if ((n3 & 0x40) != 0) {
            baseSource = null;
        }
        if ((n3 & 0x80) != 0) {
            ruleDataInterface = null;
        }
        if ((n3 & 0x100) != 0) {
            bookChapter = null;
        }
        if ((n3 & 0x200) != 0) {
            map = null;
        }
        if ((n3 & 0x400) != 0) {
            debugLog = null;
        }
        this(string, string2, n, string3, n2, string4, baseSource, ruleDataInterface, bookChapter, map, debugLog);
    }

    @NotNull
    public final String getMUrl() {
        return this.mUrl;
    }

    @Nullable
    public final String getKey() {
        return this.key;
    }

    @Nullable
    public final Integer getPage() {
        return this.page;
    }

    @Nullable
    public final String getSpeakText() {
        return this.speakText;
    }

    @Nullable
    public final Integer getSpeakSpeed() {
        return this.speakSpeed;
    }

    @NotNull
    public final String getBaseUrl() {
        return this.baseUrl;
    }

    public final void setBaseUrl(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.baseUrl = string;
    }

    @Nullable
    public final DebugLog getDebugLog() {
        return this.debugLog;
    }

    public final void setDebugLog(@Nullable DebugLog debugLog) {
        this.debugLog = debugLog;
    }

    @NotNull
    public final String getRuleUrl() {
        return this.ruleUrl;
    }

    @NotNull
    public final String getUrl() {
        return this.url;
    }

    @Nullable
    public final String getBody() {
        return this.body;
    }

    @Nullable
    public final String getType() {
        return this.type;
    }

    @NotNull
    public final HashMap<String, String> getHeaderMap() {
        return this.headerMap;
    }

    @Override
    @NotNull
    public String getUserNameSpace() {
        String string;
        RuleDataInterface ruleDataInterface = this.ruleData;
        return ruleDataInterface == null ? "unknow" : ((string = ruleDataInterface.getUserNameSpace()) == null ? "unknow" : string);
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

    public final void initUrl() {
        this.ruleUrl = this.mUrl;
        this.analyzeJs();
        this.replaceKeyPageJs();
        this.analyzeUrl();
    }

    private final void analyzeJs() {
        boolean match$iv$iv;
        char it;
        int index$iv$iv;
        boolean startFound$iv$iv;
        int endIndex$iv$iv;
        int startIndex$iv$iv;
        boolean $i$f$trim;
        CharSequence $this$trim$iv$iv;
        Object $this$trim$iv;
        CharSequence charSequence;
        int start2 = 0;
        String tmp = null;
        Matcher jsMatcher = AppPattern.INSTANCE.getJS_PATTERN().matcher(this.ruleUrl);
        while (jsMatcher.find()) {
            if (jsMatcher.start() > start2) {
                charSequence = this.ruleUrl;
                int n = jsMatcher.start();
                boolean bl = false;
                String string = charSequence;
                if (string == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String string2 = string.substring(start2, n);
                Intrinsics.checkNotNullExpressionValue(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                charSequence = string2;
                boolean $i$f$trim2 = false;
                $this$trim$iv$iv = (CharSequence)$this$trim$iv;
                $i$f$trim = false;
                startIndex$iv$iv = 0;
                endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
                startFound$iv$iv = false;
                while (startIndex$iv$iv <= endIndex$iv$iv) {
                    index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
                    it = $this$trim$iv$iv.charAt(index$iv$iv);
                    boolean bl2 = false;
                    boolean bl3 = match$iv$iv = Intrinsics.compare(it, 32) <= 0;
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
                $i$f$trim2 = false;
                if ($this$trim$iv.length() > 0) {
                    this.ruleUrl = StringsKt.replace$default(tmp, "@result", this.ruleUrl, false, 4, null);
                }
            }
            String $i$f$trim2 = ($this$trim$iv$iv = jsMatcher.group(2)) == null ? jsMatcher.group(1) : $this$trim$iv$iv;
            Intrinsics.checkNotNullExpressionValue($i$f$trim2, "jsMatcher.group(2) ?: jsMatcher.group(1)");
            $this$trim$iv = this.evalJS($i$f$trim2, this.ruleUrl);
            if ($this$trim$iv == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
            }
            this.ruleUrl = (String)$this$trim$iv;
            start2 = jsMatcher.end();
        }
        if (this.ruleUrl.length() > start2) {
            $this$trim$iv = this.ruleUrl;
            boolean $i$f$trim2 = false;
            String string = $this$trim$iv;
            if (string == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string3 = string.substring(start2);
            Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.String).substring(startIndex)");
            $this$trim$iv = string3;
            $i$f$trim2 = false;
            $this$trim$iv$iv = (CharSequence)$this$trim$iv;
            $i$f$trim = false;
            startIndex$iv$iv = 0;
            endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
            startFound$iv$iv = false;
            while (startIndex$iv$iv <= endIndex$iv$iv) {
                index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
                it = $this$trim$iv$iv.charAt(index$iv$iv);
                boolean bl = false;
                boolean bl4 = match$iv$iv = Intrinsics.compare(it, 32) <= 0;
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
            boolean bl = false;
            if (charSequence.length() > 0) {
                this.ruleUrl = StringsKt.replace$default(tmp, "@result", this.ruleUrl, false, 4, null);
            }
        }
    }

    private final void replaceKeyPageJs() {
        Integer n;
        boolean bl;
        if (StringsKt.contains$default((CharSequence)this.ruleUrl, "{{", false, 2, null) && StringsKt.contains$default((CharSequence)this.ruleUrl, "}}", false, 2, null)) {
            RuleAnalyzer analyze = new RuleAnalyzer(this.ruleUrl, false, 2, null);
            String url2 = analyze.innerRule("{{", "}}", (Function1<? super String, String>)new Function1<String, String>(this){
                final /* synthetic */ AnalyzeUrl this$0;
                {
                    this.this$0 = $receiver;
                    super(1);
                }

                @Nullable
                public final String invoke(@NotNull String it) {
                    String string;
                    Intrinsics.checkNotNullParameter(it, "it");
                    Object object = AnalyzeUrl.evalJS$default(this.this$0, it, null, 2, null);
                    Object jsEval = object == null ? "" : object;
                    if (jsEval instanceof String) {
                        string = (String)jsEval;
                    } else if (jsEval instanceof Double && ((Number)jsEval).doubleValue() % 1.0 == 0.0) {
                        object = StringCompanionObject.INSTANCE;
                        String string2 = "%.0f";
                        Object[] objectArray = new Object[]{jsEval};
                        boolean bl = false;
                        String string3 = String.format(string2, Arrays.copyOf(objectArray, objectArray.length));
                        string = string3;
                        Intrinsics.checkNotNullExpressionValue(string3, "java.lang.String.format(format, *args)");
                    } else {
                        string = jsEval.toString();
                    }
                    return string;
                }
            });
            CharSequence charSequence = url2;
            bl = false;
            if (charSequence.length() > 0) {
                this.ruleUrl = url2;
            }
        }
        if ((n = this.page) != null) {
            Integer n2 = n;
            boolean bl2 = false;
            bl = false;
            int it = ((Number)n2).intValue();
            boolean bl3 = false;
            Matcher matcher = pagePattern.matcher(this.getRuleUrl());
            while (matcher.find()) {
                String string;
                AnalyzeUrl analyzeUrl;
                String string2;
                boolean match$iv$iv;
                char it2;
                int index$iv$iv;
                boolean startFound$iv$iv;
                int endIndex$iv$iv;
                int startIndex$iv$iv;
                boolean $i$f$trim;
                CharSequence $this$trim$iv$iv;
                Object $this$trim$iv;
                boolean $i$f$trim2;
                AnalyzeUrl analyzeUrl2;
                String string3;
                Object object;
                String string4 = matcher.group(1);
                Intrinsics.checkNotNull(string4);
                Object object2 = new String[]{","};
                List pages = StringsKt.split$default((CharSequence)string4, object2, false, 0, 6, null);
                if (this.getPage() < pages.size()) {
                    String string5 = this.getRuleUrl();
                    object2 = matcher.group();
                    Intrinsics.checkNotNullExpressionValue(object2, "matcher.group()");
                    Object object3 = object2;
                    object2 = (String)pages.get(this.getPage() - 1);
                    object = object3;
                    string3 = string5;
                    analyzeUrl2 = this;
                    $i$f$trim2 = false;
                    $this$trim$iv$iv = (CharSequence)$this$trim$iv;
                    $i$f$trim = false;
                    startIndex$iv$iv = 0;
                    endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
                    startFound$iv$iv = false;
                    while (startIndex$iv$iv <= endIndex$iv$iv) {
                        index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
                        it2 = $this$trim$iv$iv.charAt(index$iv$iv);
                        boolean bl4 = false;
                        boolean bl5 = match$iv$iv = Intrinsics.compare(it2, 32) <= 0;
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
                    string2 = ((Object)$this$trim$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1)).toString();
                    analyzeUrl = analyzeUrl2;
                    string = StringsKt.replace$default(string3, (String)object, string2, false, 4, null);
                } else {
                    String string6 = this.getRuleUrl();
                    $this$trim$iv = matcher.group();
                    Intrinsics.checkNotNullExpressionValue($this$trim$iv, "matcher.group()");
                    Object object4 = $this$trim$iv;
                    $this$trim$iv = (String)CollectionsKt.last(pages);
                    object = object4;
                    string3 = string6;
                    $i$f$trim2 = false;
                    $this$trim$iv$iv = (CharSequence)$this$trim$iv;
                    $i$f$trim = false;
                    startIndex$iv$iv = 0;
                    endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
                    startFound$iv$iv = false;
                    while (startIndex$iv$iv <= endIndex$iv$iv) {
                        index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
                        it2 = $this$trim$iv$iv.charAt(index$iv$iv);
                        boolean bl6 = false;
                        boolean bl7 = match$iv$iv = Intrinsics.compare(it2, 32) <= 0;
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
                    string2 = ((Object)$this$trim$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1)).toString();
                    analyzeUrl = analyzeUrl2;
                    string = StringsKt.replace$default(string3, (String)object, string2, false, 4, null);
                }
                analyzeUrl.ruleUrl = string;
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void analyzeUrl() {
        boolean bl;
        boolean json$iv2;
        Object $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22;
        Object object;
        Object it2;
        String string;
        int n;
        int n2;
        Object object2;
        Matcher urlMatcher = paramPattern.matcher(this.ruleUrl);
        if (urlMatcher.find()) {
            object2 = this.ruleUrl;
            int n3 = 0;
            n2 = urlMatcher.start();
            n = 0;
            String string2 = object2;
            if (string2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string3 = string2.substring(n3, n2);
            string = string3;
            Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        } else {
            string = this.ruleUrl;
        }
        String urlNoOption = string;
        this.url = NetworkUtils.INSTANCE.getAbsoluteURL(this.baseUrl, urlNoOption);
        object2 = NetworkUtils.INSTANCE.getBaseUrl(this.url);
        if (object2 != null) {
            String string4 = object2;
            n2 = 0;
            n = 0;
            it2 = string4;
            boolean bl2 = false;
            this.setBaseUrl((String)it2);
        }
        if (urlNoOption.length() != this.ruleUrl.length()) {
            Object bl2;
            Object object3 = GsonExtensionsKt.getGSON();
            String string5 = this.ruleUrl;
            n = urlMatcher.end();
            boolean it2 = false;
            String string6 = string5;
            if (string6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string7 = string6.substring(n);
            Intrinsics.checkNotNullExpressionValue(string7, "(this as java.lang.String).substring(startIndex)");
            string5 = string7;
            boolean $i$f$fromJsonObject = false;
            it2 = false;
            try {
                void json$iv2;
                void $this$fromJsonObject$iv;
                bl2 = Result.Companion;
                boolean $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 = false;
                boolean $i$f$genericType = false;
                object = new TypeToken<UrlOption>(){}.getType();
                Intrinsics.checkNotNullExpressionValue(object, "object : TypeToken<T>() {}.type");
                Object t = $this$fromJsonObject$iv.fromJson((String)json$iv2, (Type)object);
                if (!(t instanceof UrlOption)) {
                    t = null;
                }
                $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 = (UrlOption)t;
                $i$f$genericType = false;
                bl2 = Result.constructor-impl($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22);
            }
            catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22) {
                Result.Companion $i$f$genericType = Result.Companion;
                boolean bl3 = false;
                bl2 = Result.constructor-impl(ResultKt.createFailure($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22));
            }
            object3 = bl2;
            json$iv2 = false;
            object2 = (UrlOption)(Result.isFailure-impl(object3) ? null : object3);
            if (object2 != null) {
                Object it32;
                object3 = object2;
                json$iv2 = false;
                n = 0;
                Object option = object3;
                boolean bl4 = false;
                $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 = ((UrlOption)option).getMethod();
                if ($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 != null) {
                    Map<?, ?> $i$f$genericType = $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22;
                    boolean bl5 = false;
                    bl = false;
                    it32 = $i$f$genericType;
                    boolean bl6 = false;
                    if (StringsKt.equals(it32, "POST", true)) {
                        this.method = RequestMethod.POST;
                    }
                }
                $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 = ((UrlOption)option).getHeaderMap();
                if ($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 != null) {
                    Map<?, ?> $this$forEach$iv = $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22;
                    boolean $i$f$forEach = false;
                    Map<?, ?> map = $this$forEach$iv;
                    boolean it32 = false;
                    Iterator<Map.Entry<?, ?>> bl6 = map.entrySet().iterator();
                    while (bl6.hasNext()) {
                        Map.Entry<?, ?> element$iv;
                        Map.Entry<?, ?> entry = element$iv = bl6.next();
                        boolean bl7 = false;
                        Map map2 = this.getHeaderMap();
                        String string8 = String.valueOf(entry.getKey());
                        String string9 = String.valueOf(entry.getValue());
                        boolean bl8 = false;
                        map2.put(string8, string9);
                    }
                }
                $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 = ((UrlOption)option).getBody();
                if ($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 != null) {
                    Object $this$forEach$iv = $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22;
                    boolean bl9 = false;
                    bl = false;
                    it32 = $this$forEach$iv;
                    boolean bl10 = false;
                    this.body = it32;
                }
                this.type = ((UrlOption)option).getType();
                this.charset = ((UrlOption)option).getCharset();
                this.retry = ((UrlOption)option).getRetry();
                this.useWebView = ((UrlOption)option).useWebView();
                this.webJs = ((UrlOption)option).getWebJs();
                $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 = ((UrlOption)option).getJs();
                if ($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 != null) {
                    String string10;
                    Object $this$forEach$iv = $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22;
                    boolean bl11 = false;
                    bl = false;
                    Object jsStr = $this$forEach$iv;
                    boolean bl12 = false;
                    Object object4 = this.evalJS((String)jsStr, this.getUrl());
                    if (object4 != null && (string10 = object4.toString()) != null) {
                        String string11 = string10;
                        boolean bl13 = false;
                        boolean bl14 = false;
                        String it4 = string11;
                        boolean bl15 = false;
                        this.url = it4;
                    }
                }
            }
        }
        if ((object2 = this.headerMap.get("User-Agent")) == null) {
            AnalyzeUrl analyzeUrl = this;
            json$iv2 = false;
            n = 0;
            it2 = analyzeUrl;
            boolean bl16 = false;
            $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 = this.getHeaderMap();
            String $this$forEach$iv = "User-Agent";
            object = AppConst.INSTANCE.getUserAgent();
            bl = false;
            $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22.put($this$forEach$iv, object);
        }
        this.urlNoQuery = this.url;
        object2 = this.method;
        int n4 = WhenMappings.$EnumSwitchMapping$0[((Enum)object2).ordinal()];
        switch (n4) {
            case 1: {
                int pos = StringsKt.indexOf$default((CharSequence)this.url, '?', 0, false, 6, null);
                if (pos == -1) break;
                String string12 = this.url;
                int n5 = pos + 1;
                boolean bl17 = false;
                String string13 = string12;
                if (string13 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String string14 = string13.substring(n5);
                Intrinsics.checkNotNullExpressionValue(string14, "(this as java.lang.String).substring(startIndex)");
                this.analyzeFields(string14);
                string12 = this.url;
                n5 = 0;
                bl17 = false;
                String string15 = string12;
                if (string15 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String string16 = string15.substring(n5, pos);
                Intrinsics.checkNotNullExpressionValue(string16, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                this.urlNoQuery = string16;
                break;
            }
            case 2: {
                String string17 = this.body;
                if (string17 == null) break;
                String string18 = string17;
                boolean bl18 = false;
                boolean bl17 = false;
                String it5 = string18;
                boolean bl19 = false;
                if (StringExtensionsKt.isJson(it5) || StringExtensionsKt.isXml(it5)) break;
                object = this.getHeaderMap().get("Content-Type");
                bl = false;
                boolean bl20 = false;
                if (!(object == null || object.length() == 0)) break;
                this.analyzeFields(it5);
            }
        }
    }

    private final void analyzeFields(String fieldsTxt) {
        this.queryStr = fieldsTxt;
        String[] stringArray = new String[]{"&"};
        String[] queryS = StringExtensionsKt.splitNotBlank(fieldsTxt, stringArray);
        for (String query : queryS) {
            boolean bl;
            String string;
            String[] stringArray2 = new String[]{"="};
            String[] queryM = StringExtensionsKt.splitNotBlank(query, stringArray2);
            String value = queryM.length > 1 ? queryM[1] : "";
            Object object = this.charset;
            boolean bl2 = false;
            boolean bl3 = false;
            if (object == null || object.length() == 0) {
                if (NetworkUtils.INSTANCE.hasUrlEncoded(value)) {
                    object = this.fieldMap;
                    string = queryM[0];
                    bl3 = false;
                    object.put(string, value);
                    continue;
                }
                object = this.fieldMap;
                string = queryM[0];
                String string2 = URLEncoder.encode(value, "UTF-8");
                Intrinsics.checkNotNullExpressionValue(string2, "encode(value, \"UTF-8\")");
                bl = false;
                object.put(string, string2);
                continue;
            }
            if (Intrinsics.areEqual(this.charset, "escape")) {
                object = this.fieldMap;
                string = queryM[0];
                String string3 = EncoderUtils.INSTANCE.escape(value);
                bl = false;
                object.put(string, string3);
                continue;
            }
            object = this.fieldMap;
            string = queryM[0];
            String string4 = URLEncoder.encode(value, this.charset);
            Intrinsics.checkNotNullExpressionValue(string4, "encode(value, charset)");
            bl = false;
            object.put(string, string4);
        }
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
        string = "baseUrl";
        Object object = this.baseUrl;
        boolean bl2 = false;
        map.put(string, object);
        map = bindings;
        string = "cookie";
        object = new CookieStore(this.getUserNameSpace());
        bl2 = false;
        map.put(string, object);
        map = bindings;
        string = "cache";
        object = new CacheManager(this.getUserNameSpace());
        bl2 = false;
        map.put(string, object);
        map = bindings;
        string = "page";
        object = this.page;
        bl2 = false;
        map.put(string, object);
        map = bindings;
        string = "key";
        object = this.key;
        bl2 = false;
        map.put(string, object);
        map = bindings;
        string = "speakText";
        object = this.speakText;
        bl2 = false;
        map.put(string, object);
        map = bindings;
        string = "speakSpeed";
        object = this.speakSpeed;
        bl2 = false;
        map.put(string, object);
        map = bindings;
        string = "book";
        object = this.ruleData;
        object = object instanceof Book ? (Book)object : null;
        bl2 = false;
        map.put(string, object);
        map = bindings;
        string = "source";
        object = this.source;
        bl2 = false;
        map.put(string, object);
        map = bindings;
        string = "result";
        boolean bl3 = false;
        map.put(string, result2);
        return AppConst.INSTANCE.getSCRIPT_ENGINE().eval(jsStr, (Bindings)bindings);
    }

    public static /* synthetic */ Object evalJS$default(AnalyzeUrl analyzeUrl, String string, Object object, int n, Object object2) {
        if ((n & 2) != 0) {
            object = null;
        }
        return analyzeUrl.evalJS(string, object);
    }

    @NotNull
    public final String put(@NotNull String key, @NotNull String value) {
        block2: {
            Unit unit;
            Unit unit2;
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            RuleDataInterface ruleDataInterface = this.chapter;
            if (ruleDataInterface == null) {
                unit2 = null;
            } else {
                ((BookChapter)ruleDataInterface).putVariable(key, value);
                unit2 = unit = Unit.INSTANCE;
            }
            if (unit != null || (ruleDataInterface = this.ruleData) == null) break block2;
            ruleDataInterface.putVariable(key, value);
        }
        return value;
    }

    @NotNull
    public final String get(@NotNull String key) {
        RuleDataInterface ruleDataInterface;
        Object object;
        Intrinsics.checkNotNullParameter(key, "key");
        String string = key;
        if (Intrinsics.areEqual(string, "bookName")) {
            object = this.ruleData;
            RuleDataInterface ruleDataInterface2 = ruleDataInterface = object instanceof Book ? (Book)object : null;
            if (ruleDataInterface != null) {
                object = ruleDataInterface;
                boolean bl = false;
                boolean bl2 = false;
                RuleDataInterface it = object;
                boolean bl3 = false;
                return ((Book)it).getName();
            }
        } else if (Intrinsics.areEqual(string, "title") && (ruleDataInterface = this.chapter) != null) {
            RuleDataInterface ruleDataInterface3 = ruleDataInterface;
            boolean bl = false;
            boolean bl4 = false;
            RuleDataInterface it = ruleDataInterface3;
            boolean bl5 = false;
            return ((BookChapter)it).getTitle();
        }
        ruleDataInterface = this.chapter;
        String string2 = string = ruleDataInterface == null ? null : ((BookChapter)ruleDataInterface).getVariable(key);
        return string == null ? ((ruleDataInterface = this.ruleData) == null ? "" : ((object = ruleDataInterface.getVariable(key)) == null ? "" : object)) : string;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final ConcurrentRecord fetchStart() {
        BaseSource baseSource = this.source;
        if (baseSource == null) {
            return null;
        }
        String concurrentRate = this.source.getConcurrentRate();
        CharSequence charSequence = concurrentRate;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence == null || charSequence.length() == 0) {
            return null;
        }
        int rateIndex = StringsKt.indexOf$default((CharSequence)concurrentRate, "/", 0, false, 6, null);
        ConcurrentRecord fetchRecord = null;
        fetchRecord = concurrentRecordMap.get(this.source.getKey());
        if (fetchRecord == null) {
            fetchRecord = new ConcurrentRecord(rateIndex > 0, System.currentTimeMillis(), 1);
            Map map = concurrentRecordMap;
            String string = this.source.getKey();
            ConcurrentRecord concurrentRecord = fetchRecord;
            boolean bl3 = false;
            map.put(string, concurrentRecord);
            return fetchRecord;
        }
        ConcurrentRecord concurrentRecord = fetchRecord;
        boolean bl4 = false;
        int n = 0;
        synchronized (concurrentRecord) {
            int n2;
            boolean bl5 = false;
            try {
                if (rateIndex == -1) {
                    if (fetchRecord.getFrequency() > 0) {
                        String string = concurrentRate;
                        boolean bl6 = false;
                        n2 = Integer.parseInt(string);
                    } else {
                        String string = concurrentRate;
                        boolean bl7 = false;
                        long nextTime = fetchRecord.getTime() + (long)Integer.parseInt(string);
                        if (System.currentTimeMillis() >= nextTime) {
                            fetchRecord.setTime(System.currentTimeMillis());
                            fetchRecord.setFrequency(1);
                            n2 = 0;
                        } else {
                            n2 = (int)(nextTime - System.currentTimeMillis());
                        }
                    }
                } else {
                    String sj;
                    String string = concurrentRate;
                    int n3 = rateIndex + 1;
                    boolean bl8 = false;
                    String string2 = string;
                    if (string2 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    String string3 = string2.substring(n3);
                    Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.String).substring(startIndex)");
                    String string4 = sj = string3;
                    boolean bl9 = false;
                    long nextTime = fetchRecord.getTime() + (long)Integer.parseInt(string4);
                    if (System.currentTimeMillis() >= nextTime) {
                        fetchRecord.setTime(System.currentTimeMillis());
                        fetchRecord.setFrequency(1);
                        n2 = 0;
                    } else {
                        String cs;
                        String string5 = concurrentRate;
                        int n4 = 0;
                        boolean bl10 = false;
                        String string6 = string5;
                        if (string6 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                        }
                        String string7 = string6.substring(n4, rateIndex);
                        Intrinsics.checkNotNullExpressionValue(string7, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                        string5 = cs = string7;
                        n4 = 0;
                        if (fetchRecord.getFrequency() > Integer.parseInt(string5)) {
                            n2 = (int)(nextTime - System.currentTimeMillis());
                        } else {
                            fetchRecord.setFrequency(fetchRecord.getFrequency() + 1);
                            n2 = 0;
                        }
                    }
                }
            }
            catch (Exception e) {
                n2 = 0;
            }
            n = n2;
        }
        int waitTime = n;
        if (waitTime > 0) {
            throw new ConcurrentException("\u6839\u636e\u5e76\u53d1\u7387\u8fd8\u9700\u7b49\u5f85" + waitTime + "\u6beb\u79d2\u624d\u53ef\u4ee5\u8bbf\u95ee", waitTime);
        }
        return fetchRecord;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final void fetchEnd(ConcurrentRecord concurrentRecord) {
        if (concurrentRecord != null && !concurrentRecord.getConcurrent()) {
            boolean bl = false;
            boolean bl2 = false;
            synchronized (concurrentRecord) {
                boolean bl3 = false;
                concurrentRecord.setFrequency(concurrentRecord.getFrequency() - 1);
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getStrResponseAwait(@Nullable String var1_1, @Nullable String var2_2, boolean var3_3, @NotNull Continuation<? super StrResponse> var4_4) {
        block13: {
            block14: {
                if (!(var4_4 instanceof getStrResponseAwait.1)) ** GOTO lbl-1000
                var22_5 = var4_4;
                if ((var22_5.label & -2147483648) != 0) {
                    var22_5.label -= -2147483648;
                } else lbl-1000:
                // 2 sources

                {
                    $continuation = new ContinuationImpl(this, var4_4){
                        Object L$0;
                        Object L$1;
                        /* synthetic */ Object result;
                        final /* synthetic */ AnalyzeUrl this$0;
                        int label;
                        {
                            this.this$0 = this$0;
                            super($completion);
                        }

                        @Nullable
                        public final Object invokeSuspend(@NotNull Object $result) {
                            this.result = $result;
                            this.label |= Integer.MIN_VALUE;
                            return this.this$0.getStrResponseAwait(null, null, false, this);
                        }
                    };
                }
                $result = $continuation.result;
                var25_7 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch ($continuation.label) {
                    case 0: {
                        ResultKt.throwOnFailure($result);
                        if (this.getType() == null) break;
                        var19_8 = StringUtils.INSTANCE;
                        var18_9 = this.getUrl();
                        $continuation.L$0 = var18_9;
                        $continuation.L$1 = var19_8;
                        $continuation.label = 1;
                        v0 = this.getByteArrayAwait($continuation);
                        if (v0 == var25_7) {
                            return var25_7;
                        }
                        ** GOTO lbl27
                    }
                    case 1: {
                        var19_8 = (StringUtils)$continuation.L$1;
                        var18_9 = (String)$continuation.L$0;
                        ResultKt.throwOnFailure($result);
                        v0 = $result;
lbl27:
                        // 2 sources

                        var20_10 = v0;
                        var23_11 = var19_8.byteToHexString((byte[])var20_10);
                        var24_12 = var18_9;
                        return new StrResponse(var24_12, var23_11);
                    }
                }
                concurrentRecord = this.fetchStart();
                var6_14 = this.source;
                this.setCookie(var6_14 == null ? null : var6_14.getKey());
                var6_14 = null;
                if (!this.useWebView || useWebView == false) break block13;
                var7_15 = this.method;
                var8_16 = WhenMappings.$EnumSwitchMapping$0[var7_15.ordinal()];
                if (var8_16 != 2) break block14;
                var9_17 = this.urlNoQuery;
                var11_18 = this.source;
                var10_19 = var11_18 == null ? null : var11_18.getKey();
                var12_20 = this.webJs;
                var11_18 = var12_20 == null ? jsStr : var12_20;
                var12_20 = this.getHeaderMap();
                var13_21 = this.getBody();
                var14_22 = this.getUserNameSpace();
                var15_23 = this.getDebugLog();
                $continuation.L$0 = this;
                $continuation.L$1 = concurrentRecord;
                $continuation.label = 2;
                v1 = ReaderAdapterInterface.DefaultImpls.getStrResponseByRemoteWebview$default(ReaderAdapterHelper.INSTANCE.getAdapter(), var9_17, null, null, var10_19, var12_20, (String)sourceRegex, (String)var11_18, null, true, var13_21, (String)var14_22, var15_23, $continuation, 134, null);
                if (v1 == var25_7) {
                    return var25_7;
                }
                ** GOTO lbl60
                {
                    case 2: {
                        concurrentRecord = (ConcurrentRecord)$continuation.L$1;
                        this = (AnalyzeUrl)$continuation.L$0;
                        ResultKt.throwOnFailure($result);
                        v1 = $result;
lbl60:
                        // 2 sources

                        v2 = (StrResponse)v1;
                        ** GOTO lbl84
                    }
                }
            }
            var9_17 = this.getUrl();
            var11_18 = this.source;
            var10_19 = var11_18 == null ? null : var11_18.getKey();
            var12_20 = this.webJs;
            var11_18 = var12_20 == null ? jsStr : var12_20;
            var12_20 = this.getHeaderMap();
            var13_21 = this.getUserNameSpace();
            var14_22 = this.getDebugLog();
            $continuation.L$0 = this;
            $continuation.L$1 = concurrentRecord;
            $continuation.label = 3;
            v3 = ReaderAdapterInterface.DefaultImpls.getStrResponseByRemoteWebview$default(ReaderAdapterHelper.INSTANCE.getAdapter(), var9_17, null, null, var10_19, var12_20, (String)sourceRegex, (String)var11_18, null, false, null, var13_21, (DebugLog)var14_22, $continuation, 902, null);
            if (v3 == var25_7) {
                return var25_7;
            }
            ** GOTO lbl83
            {
                case 3: {
                    concurrentRecord = (ConcurrentRecord)$continuation.L$1;
                    this = (AnalyzeUrl)$continuation.L$0;
                    ResultKt.throwOnFailure($result);
                    v3 = $result;
lbl83:
                    // 2 sources

                    v2 = (StrResponse)v3;
lbl84:
                    // 2 sources

                    var6_14 = v2;
                    ** GOTO lbl101
                }
            }
        }
        $continuation.L$0 = this;
        $continuation.L$1 = concurrentRecord;
        $continuation.label = 4;
        v4 = OkHttpUtilsKt.newCallStrResponse(HttpHelperKt.getProxyClient(this.proxy, this.getDebugLog()), this.retry, (Function1<? super Request.Builder, Unit>)new Function1<Request.Builder, Unit>(this){
            final /* synthetic */ AnalyzeUrl this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            /*
             * Enabled aggressive block sorting
             */
            public final void invoke(@NotNull Request.Builder $this$newCallStrResponse) {
                boolean bl;
                boolean bl2;
                Object object;
                String body;
                String contentType;
                block6: {
                    block5: {
                        Intrinsics.checkNotNullParameter($this$newCallStrResponse, "$this$newCallStrResponse");
                        OkHttpUtilsKt.addHeaders($this$newCallStrResponse, (Map<String, String>)this.this$0.getHeaderMap());
                        RequestMethod requestMethod = AnalyzeUrl.access$getMethod$p(this.this$0);
                        int n = getStrResponseAwait.WhenMappings.$EnumSwitchMapping$0[requestMethod.ordinal()];
                        if (n != 1) {
                            OkHttpUtilsKt.get($this$newCallStrResponse, AnalyzeUrl.access$getUrlNoQuery$p(this.this$0), AnalyzeUrl.access$getFieldMap$p(this.this$0), true);
                            return;
                        }
                        $this$newCallStrResponse.url(AnalyzeUrl.access$getUrlNoQuery$p(this.this$0));
                        contentType = this.this$0.getHeaderMap().get("Content-Type");
                        body = this.this$0.getBody();
                        object = AnalyzeUrl.access$getFieldMap$p(this.this$0);
                        bl2 = false;
                        if (!object.isEmpty()) break block5;
                        object = body;
                        bl2 = false;
                        bl = false;
                        if (!(object == null || StringsKt.isBlank((CharSequence)object))) break block6;
                    }
                    OkHttpUtilsKt.postForm($this$newCallStrResponse, AnalyzeUrl.access$getFieldMap$p(this.this$0), true);
                    return;
                }
                object = contentType;
                bl2 = false;
                bl = false;
                if (!(object == null || StringsKt.isBlank((CharSequence)object))) {
                    RequestBody requestBody = RequestBody.Companion.create(body, MediaType.Companion.get(contentType));
                    $this$newCallStrResponse.post(requestBody);
                    return;
                }
                OkHttpUtilsKt.postJson($this$newCallStrResponse, body);
            }
        }, $continuation);
        if (v4 == var25_7) {
            return var25_7;
        }
        ** GOTO lbl99
        {
            case 4: {
                concurrentRecord = (ConcurrentRecord)$continuation.L$1;
                this = (AnalyzeUrl)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v4 = $result;
lbl99:
                // 2 sources

                strResponse = (StrResponse)v4;
                this.saveCookieJar(strResponse.getRaw());
lbl101:
                // 2 sources

                this.fetchEnd(concurrentRecord);
                return strResponse;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object getStrResponseAwait$default(AnalyzeUrl analyzeUrl, String string, String string2, boolean bl, Continuation continuation, int n, Object object) {
        if ((n & 1) != 0) {
            string = null;
        }
        if ((n & 2) != 0) {
            string2 = null;
        }
        if ((n & 4) != 0) {
            bl = true;
        }
        return analyzeUrl.getStrResponseAwait(string, string2, bl, continuation);
    }

    public final void saveCookieJar(@NotNull Response response2) {
        Intrinsics.checkNotNullParameter(response2, "response");
        List<String> cookieList = response2.headers("Set-Cookie");
        if (cookieList.size() > 0) {
            CookieStore cookieStore = new CookieStore(this.getUserNameSpace());
            String domain = NetworkUtils.INSTANCE.getSubDomain(this.url);
            Iterable $this$forEach$iv = cookieList;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                String it = (String)element$iv;
                boolean bl = false;
                cookieStore.replaceCookie(Intrinsics.stringPlus(domain, "_cookieJar"), it);
            }
        }
    }

    @JvmOverloads
    @NotNull
    public final StrResponse getStrResponse(@Nullable String jsStr, @Nullable String sourceRegex, boolean useWebView) {
        return (StrResponse)BuildersKt.runBlocking$default(null, new Function2<CoroutineScope, Continuation<? super StrResponse>, Object>(this, jsStr, sourceRegex, useWebView, null){
            int label;
            final /* synthetic */ AnalyzeUrl this$0;
            final /* synthetic */ String $jsStr;
            final /* synthetic */ String $sourceRegex;
            final /* synthetic */ boolean $useWebView;
            {
                this.this$0 = $receiver;
                this.$jsStr = $jsStr;
                this.$sourceRegex = $sourceRegex;
                this.$useWebView = $useWebView;
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
                        Object object3 = this.this$0.getStrResponseAwait(this.$jsStr, this.$sourceRegex, this.$useWebView, this);
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
            public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super StrResponse> p2) {
                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
            }
        }, 1, null);
    }

    public static /* synthetic */ StrResponse getStrResponse$default(AnalyzeUrl analyzeUrl, String string, String string2, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            string = null;
        }
        if ((n & 2) != 0) {
            string2 = null;
        }
        if ((n & 4) != 0) {
            bl = true;
        }
        return analyzeUrl.getStrResponse(string, string2, bl);
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getResponseAwait(@NotNull Continuation<? super Response> var1_1) {
        if (!(var1_1 instanceof getResponseAwait.1)) ** GOTO lbl-1000
        var5_2 = var1_1;
        if ((var5_2.label & -2147483648) != 0) {
            var5_2.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var1_1){
                Object L$0;
                Object L$1;
                /* synthetic */ Object result;
                final /* synthetic */ AnalyzeUrl this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.getResponseAwait(this);
                }
            };
        }
        $result = $continuation.result;
        var6_4 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                concurrentRecord = this.fetchStart();
                var3_6 = this.source;
                this.setCookie(var3_6 == null ? null : var3_6.getKey());
                $continuation.L$0 = this;
                $continuation.L$1 = concurrentRecord;
                $continuation.label = 1;
                v0 = OkHttpUtilsKt.newCallResponse(HttpHelperKt.getProxyClient$default(this.proxy, null, 2, null), this.retry, (Function1<? super Request.Builder, Unit>)new Function1<Request.Builder, Unit>(this){
                    final /* synthetic */ AnalyzeUrl this$0;
                    {
                        this.this$0 = $receiver;
                        super(1);
                    }

                    /*
                     * Enabled aggressive block sorting
                     */
                    public final void invoke(@NotNull Request.Builder $this$newCallResponse) {
                        boolean bl;
                        boolean bl2;
                        Object object;
                        String body;
                        String contentType;
                        block6: {
                            block5: {
                                Intrinsics.checkNotNullParameter($this$newCallResponse, "$this$newCallResponse");
                                OkHttpUtilsKt.addHeaders($this$newCallResponse, (Map<String, String>)this.this$0.getHeaderMap());
                                RequestMethod requestMethod = AnalyzeUrl.access$getMethod$p(this.this$0);
                                int n = getResponseAwait.response.WhenMappings.$EnumSwitchMapping$0[requestMethod.ordinal()];
                                if (n != 1) {
                                    OkHttpUtilsKt.get($this$newCallResponse, AnalyzeUrl.access$getUrlNoQuery$p(this.this$0), AnalyzeUrl.access$getFieldMap$p(this.this$0), true);
                                    return;
                                }
                                $this$newCallResponse.url(AnalyzeUrl.access$getUrlNoQuery$p(this.this$0));
                                contentType = this.this$0.getHeaderMap().get("Content-Type");
                                body = this.this$0.getBody();
                                object = AnalyzeUrl.access$getFieldMap$p(this.this$0);
                                bl2 = false;
                                if (!object.isEmpty()) break block5;
                                object = body;
                                bl2 = false;
                                bl = false;
                                if (!(object == null || StringsKt.isBlank((CharSequence)object))) break block6;
                            }
                            OkHttpUtilsKt.postForm($this$newCallResponse, AnalyzeUrl.access$getFieldMap$p(this.this$0), true);
                            return;
                        }
                        object = contentType;
                        bl2 = false;
                        bl = false;
                        if (!(object == null || StringsKt.isBlank((CharSequence)object))) {
                            RequestBody requestBody = RequestBody.Companion.create(body, MediaType.Companion.get(contentType));
                            $this$newCallResponse.post(requestBody);
                            return;
                        }
                        OkHttpUtilsKt.postJson($this$newCallResponse, body);
                    }
                }, $continuation);
                if (v0 == var6_4) {
                    return var6_4;
                }
                ** GOTO lbl27
            }
            case 1: {
                concurrentRecord = (ConcurrentRecord)$continuation.L$1;
                this = (AnalyzeUrl)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                response = (Response)v0;
                this.fetchEnd(concurrentRecord);
                return response;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @NotNull
    public final Response getResponse() {
        return (Response)BuildersKt.runBlocking$default(null, new Function2<CoroutineScope, Continuation<? super Response>, Object>(this, null){
            int label;
            final /* synthetic */ AnalyzeUrl this$0;
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
                        ResultKt.throwOnFailure(object);
                        this.label = 1;
                        Object object3 = this.this$0.getResponseAwait(this);
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
            public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Response> p2) {
                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
            }
        }, 1, null);
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getByteArrayAwait(@NotNull Continuation<? super byte[]> var1_1) {
        if (!(var1_1 instanceof getByteArrayAwait.1)) ** GOTO lbl-1000
        var7_2 = var1_1;
        if ((var7_2.label & -2147483648) != 0) {
            var7_2.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var1_1){
                Object L$0;
                Object L$1;
                /* synthetic */ Object result;
                final /* synthetic */ AnalyzeUrl this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.getByteArrayAwait(this);
                }
            };
        }
        $result = $continuation.result;
        var8_4 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                concurrentRecord = this.fetchStart();
                dataUriFindResult = Regex.find$default(AppPattern.INSTANCE.getDataUriRegex(), this.urlNoQuery, 0, 2, null);
                if (dataUriFindResult != null) {
                    dataUriBase64 = dataUriFindResult.getGroupValues().get(1);
                    byteArray = Base64.decode(dataUriBase64, 0);
                    this.fetchEnd(concurrentRecord);
                    Intrinsics.checkNotNullExpressionValue(byteArray, "byteArray");
                    return byteArray;
                }
                dataUriBase64 = this.source;
                this.setCookie(dataUriBase64 == null ? null : dataUriBase64.getKey());
                $continuation.L$0 = this;
                $continuation.L$1 = concurrentRecord;
                $continuation.label = 1;
                v0 = OkHttpUtilsKt.newCallResponseBody(HttpHelperKt.getProxyClient$default(this.proxy, null, 2, null), this.retry, (Function1<? super Request.Builder, Unit>)new Function1<Request.Builder, Unit>(this){
                    final /* synthetic */ AnalyzeUrl this$0;
                    {
                        this.this$0 = $receiver;
                        super(1);
                    }

                    /*
                     * Enabled aggressive block sorting
                     */
                    public final void invoke(@NotNull Request.Builder $this$newCallResponseBody) {
                        boolean bl;
                        boolean bl2;
                        Object object;
                        String body;
                        String contentType;
                        block6: {
                            block5: {
                                Intrinsics.checkNotNullParameter($this$newCallResponseBody, "$this$newCallResponseBody");
                                OkHttpUtilsKt.addHeaders($this$newCallResponseBody, (Map<String, String>)this.this$0.getHeaderMap());
                                RequestMethod requestMethod = AnalyzeUrl.access$getMethod$p(this.this$0);
                                int n = getByteArrayAwait.byteArray.WhenMappings.$EnumSwitchMapping$0[requestMethod.ordinal()];
                                if (n != 1) {
                                    OkHttpUtilsKt.get($this$newCallResponseBody, AnalyzeUrl.access$getUrlNoQuery$p(this.this$0), AnalyzeUrl.access$getFieldMap$p(this.this$0), true);
                                    return;
                                }
                                $this$newCallResponseBody.url(AnalyzeUrl.access$getUrlNoQuery$p(this.this$0));
                                contentType = this.this$0.getHeaderMap().get("Content-Type");
                                body = this.this$0.getBody();
                                object = AnalyzeUrl.access$getFieldMap$p(this.this$0);
                                bl2 = false;
                                if (!object.isEmpty()) break block5;
                                object = body;
                                bl2 = false;
                                bl = false;
                                if (!(object == null || StringsKt.isBlank((CharSequence)object))) break block6;
                            }
                            OkHttpUtilsKt.postForm($this$newCallResponseBody, AnalyzeUrl.access$getFieldMap$p(this.this$0), true);
                            return;
                        }
                        object = contentType;
                        bl2 = false;
                        bl = false;
                        if (!(object == null || StringsKt.isBlank((CharSequence)object))) {
                            RequestBody requestBody = RequestBody.Companion.create(body, MediaType.Companion.get(contentType));
                            $this$newCallResponseBody.post(requestBody);
                            return;
                        }
                        OkHttpUtilsKt.postJson($this$newCallResponseBody, body);
                    }
                }, $continuation);
                if (v0 == var8_4) {
                    return var8_4;
                }
                ** GOTO lbl34
            }
            case 1: {
                var2_5 = (ConcurrentRecord)$continuation.L$1;
                this = (AnalyzeUrl)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl34:
                // 2 sources

                byteArray = ((ResponseBody)v0).bytes();
                this.fetchEnd(var2_5);
                return byteArray;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @NotNull
    public final byte[] getByteArray() {
        return (byte[])BuildersKt.runBlocking$default(null, new Function2<CoroutineScope, Continuation<? super byte[]>, Object>(this, null){
            int label;
            final /* synthetic */ AnalyzeUrl this$0;
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
                        ResultKt.throwOnFailure(object);
                        this.label = 1;
                        Object object3 = this.this$0.getByteArrayAwait(this);
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
            public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super byte[]> p2) {
                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
            }
        }, 1, null);
    }

    @Nullable
    public final Object upload(@NotNull String fileName, @NotNull Object file, @NotNull String contentType, @NotNull Continuation<? super StrResponse> $completion) {
        return OkHttpUtilsKt.newCallStrResponse(HttpHelperKt.getProxyClient$default(this.proxy, null, 2, null), this.retry, (Function1<? super Request.Builder, Unit>)new Function1<Request.Builder, Unit>(this, fileName, file, contentType){
            final /* synthetic */ AnalyzeUrl this$0;
            final /* synthetic */ String $fileName;
            final /* synthetic */ Object $file;
            final /* synthetic */ String $contentType;
            {
                this.this$0 = $receiver;
                this.$fileName = $fileName;
                this.$file = $file;
                this.$contentType = $contentType;
                super(1);
            }

            /*
             * WARNING - void declaration
             */
            public final void invoke(@NotNull Request.Builder $this$newCallStrResponse) {
                void $this$forEach$iv;
                HashMap hashMap;
                Object $this$fromJsonObject$iv;
                Object object;
                Object object2;
                Intrinsics.checkNotNullParameter($this$newCallStrResponse, "$this$newCallStrResponse");
                $this$newCallStrResponse.url(AnalyzeUrl.access$getUrlNoQuery$p(this.this$0));
                Gson gson2 = GsonExtensionsKt.getGSON();
                String json$iv = this.this$0.getBody();
                boolean $i$f$fromJsonObject = false;
                boolean bl = false;
                try {
                    object2 = Result.Companion;
                    boolean bl2 = false;
                    boolean $i$f$genericType = false;
                    object = new TypeToken<HashMap<String, Object>>(){}.getType();
                    Intrinsics.checkNotNullExpressionValue(object, "object : TypeToken<T>() {}.type");
                    T t = ((Gson)$this$fromJsonObject$iv).fromJson(json$iv, (Type)object);
                    if (!(t instanceof HashMap)) {
                        t = null;
                    }
                    hashMap = (HashMap)t;
                    boolean bl3 = false;
                    object2 = Result.constructor-impl(hashMap);
                }
                catch (Throwable throwable) {
                    Result.Companion companion = Result.Companion;
                    boolean bl4 = false;
                    object2 = Result.constructor-impl(ResultKt.createFailure(throwable));
                }
                $this$fromJsonObject$iv = object2;
                boolean bl5 = false;
                Object object3 = Result.isFailure-impl($this$fromJsonObject$iv) ? null : $this$fromJsonObject$iv;
                Intrinsics.checkNotNull(object3);
                HashMap bodyMap = (HashMap)object3;
                $this$fromJsonObject$iv = bodyMap;
                String string = this.$fileName;
                Object object4 = this.$file;
                String string2 = this.$contentType;
                boolean $i$f$forEach = false;
                hashMap = $this$forEach$iv;
                boolean bl6 = false;
                object = hashMap.entrySet().iterator();
                while (object.hasNext()) {
                    Map.Entry element$iv;
                    Map.Entry entry = element$iv = (Map.Entry)object.next();
                    boolean bl7 = false;
                    if (!Intrinsics.areEqual(entry.getValue().toString(), "fileRequest")) continue;
                    Map map = bodyMap;
                    K k = entry.getKey();
                    Object object5 = new Pair[]{new Pair<String, String>("fileName", string), new Pair<String, Object>("file", object4), new Pair<String, String>("contentType", string2)};
                    object5 = MapsKt.mapOf(object5);
                    boolean bl8 = false;
                    map.put(k, object5);
                }
                OkHttpUtilsKt.postMultipart($this$newCallStrResponse, this.this$0.getType(), bodyMap);
            }
        }, $completion);
    }

    private final void setCookie(String tag) {
        String key;
        CharSequence charSequence;
        CharSequence charSequence2 = tag;
        String domain = NetworkUtils.INSTANCE.getSubDomain((String)(charSequence2 == null ? this.url : charSequence2));
        charSequence2 = domain;
        boolean bl = false;
        if (charSequence2.length() == 0) {
            return;
        }
        CookieStore cookieStore = new CookieStore(this.getUserNameSpace());
        if (this.enabledCookieJar && (charSequence = cookieStore.getCookie(key = Intrinsics.stringPlus(domain, "_cookieJar"))) != null) {
            CharSequence charSequence3 = charSequence;
            boolean bl2 = false;
            boolean bl3 = false;
            CharSequence it = charSequence3;
            boolean bl4 = false;
            cookieStore.replaceCookie(domain, (String)it);
        }
        String cookie = cookieStore.getCookie(domain);
        charSequence = cookie;
        boolean bl5 = false;
        if (charSequence.length() > 0) {
            String newCookie;
            Map<String, String> cookieMap = cookieStore.cookieToMap(cookie);
            String string = this.headerMap.get("Cookie");
            String string2 = string == null ? "" : string;
            Map<String, String> customCookieMap = cookieStore.cookieToMap(string2);
            cookieMap.putAll(customCookieMap);
            string = newCookie = cookieStore.mapToCookie(cookieMap);
            if (string != null) {
                String string3 = string;
                boolean bl6 = false;
                boolean bl7 = false;
                String it = string3;
                boolean bl8 = false;
                this.getHeaderMap().put("Cookie", it);
            }
        }
    }

    @NotNull
    public final String getUserAgent() {
        String string = this.headerMap.get("User-Agent");
        return string == null ? AppConst.INSTANCE.getUserAgent() : string;
    }

    public final boolean isPost() {
        return this.method == RequestMethod.POST;
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
    @Nullable
    public String ajax(@NotNull String urlStr) {
        return JsExtensions.DefaultImpls.ajax(this, urlStr);
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
    public final StrResponse getStrResponse(@Nullable String jsStr, @Nullable String sourceRegex) {
        return AnalyzeUrl.getStrResponse$default(this, jsStr, sourceRegex, false, 4, null);
    }

    @JvmOverloads
    @NotNull
    public final StrResponse getStrResponse(@Nullable String jsStr) {
        return AnalyzeUrl.getStrResponse$default(this, jsStr, null, false, 6, null);
    }

    @JvmOverloads
    @NotNull
    public final StrResponse getStrResponse() {
        return AnalyzeUrl.getStrResponse$default(this, null, null, false, 7, null);
    }

    public static final /* synthetic */ RequestMethod access$getMethod$p(AnalyzeUrl $this) {
        return $this.method;
    }

    public static final /* synthetic */ String access$getUrlNoQuery$p(AnalyzeUrl $this) {
        return $this.urlNoQuery;
    }

    public static final /* synthetic */ LinkedHashMap access$getFieldMap$p(AnalyzeUrl $this) {
        return $this.fieldMap;
    }

    static {
        Pattern pattern = Pattern.compile("\\s*,\\s*(?=\\{)");
        Intrinsics.checkNotNullExpressionValue(pattern, "compile(\"\\\\s*,\\\\s*(?=\\\\{)\")");
        paramPattern = pattern;
        pagePattern = Pattern.compile("<(.*?)>");
        boolean bl = false;
        concurrentRecordMap = new HashMap();
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n \n*\u0004\u0018\u00010\t0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u000e"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeUrl$Companion;", "", "()V", "concurrentRecordMap", "Ljava/util/HashMap;", "", "Lio/legado/app/model/analyzeRule/AnalyzeUrl$ConcurrentRecord;", "Lkotlin/collections/HashMap;", "pagePattern", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "paramPattern", "getParamPattern", "()Ljava/util/regex/Pattern;", "reader-pro"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Pattern getParamPattern() {
            return paramPattern;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\f\b\u0086\b\u0018\u00002\u00020\u0001Bq\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\rJ\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u00c2\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003H\u00c2\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00c2\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u00c2\u0003J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\bH\u00c2\u0003\u00a2\u0006\u0002\u0010\u0014J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0003H\u00c2\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00c2\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0003H\u00c2\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0003H\u00c2\u0003Jz\u0010\u0019\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010\u001aJ\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\b\u0010\u001e\u001a\u0004\u0018\u00010\u0003J\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003J\u0010\u0010 \u001a\f\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0018\u00010!J\b\u0010\"\u001a\u0004\u0018\u00010\u0003J\b\u0010#\u001a\u0004\u0018\u00010\u0003J\u0006\u0010$\u001a\u00020\bJ\b\u0010%\u001a\u0004\u0018\u00010\u0003J\b\u0010&\u001a\u0004\u0018\u00010\u0003J\t\u0010'\u001a\u00020\bH\u00d6\u0001J\u0010\u0010(\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010\u0003J\u0010\u0010+\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010\u0003J\u0010\u0010,\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010\u0003J\u0010\u0010-\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010\u0003J\u0010\u0010.\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010\u0003J\u0010\u0010/\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010\u0003J\u0010\u00100\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010\u0003J\u0010\u00101\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010\u0003J\t\u00102\u001a\u00020\u0003H\u00d6\u0001J\u0006\u00103\u001a\u00020\u001cJ\u000e\u00103\u001a\u00020)2\u0006\u00104\u001a\u00020\u001cR\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0001X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0001X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\u000eR\u0010\u0010\t\u001a\u0004\u0018\u00010\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0001X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00065"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeUrl$UrlOption;", "", "method", "", "charset", "headers", "body", "retry", "", "type", "webView", "webJs", "js", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)V", "Ljava/lang/Integer;", "component1", "component2", "component3", "component4", "component5", "()Ljava/lang/Integer;", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)Lio/legado/app/model/analyzeRule/AnalyzeUrl$UrlOption;", "equals", "", "other", "getBody", "getCharset", "getHeaderMap", "", "getJs", "getMethod", "getRetry", "getType", "getWebJs", "hashCode", "setBody", "", "value", "setCharset", "setHeaders", "setJs", "setMethod", "setRetry", "setType", "setWebJs", "toString", "useWebView", "boolean", "reader-pro"})
    public static final class UrlOption {
        @Nullable
        private String method;
        @Nullable
        private String charset;
        @Nullable
        private Object headers;
        @Nullable
        private Object body;
        @Nullable
        private Integer retry;
        @Nullable
        private String type;
        @Nullable
        private Object webView;
        @Nullable
        private String webJs;
        @Nullable
        private String js;

        public UrlOption(@Nullable String method, @Nullable String charset, @Nullable Object headers, @Nullable Object body, @Nullable Integer retry, @Nullable String type, @Nullable Object webView, @Nullable String webJs, @Nullable String js) {
            this.method = method;
            this.charset = charset;
            this.headers = headers;
            this.body = body;
            this.retry = retry;
            this.type = type;
            this.webView = webView;
            this.webJs = webJs;
            this.js = js;
        }

        public /* synthetic */ UrlOption(String string, String string2, Object object, Object object2, Integer n, String string3, Object object3, String string4, String string5, int n2, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n2 & 1) != 0) {
                string = null;
            }
            if ((n2 & 2) != 0) {
                string2 = null;
            }
            if ((n2 & 4) != 0) {
                object = null;
            }
            if ((n2 & 8) != 0) {
                object2 = null;
            }
            if ((n2 & 0x10) != 0) {
                n = null;
            }
            if ((n2 & 0x20) != 0) {
                string3 = null;
            }
            if ((n2 & 0x40) != 0) {
                object3 = null;
            }
            if ((n2 & 0x80) != 0) {
                string4 = null;
            }
            if ((n2 & 0x100) != 0) {
                string5 = null;
            }
            this(string, string2, object, object2, n, string3, object3, string4, string5);
        }

        public final void setMethod(@Nullable String value) {
            CharSequence charSequence = value;
            boolean bl = false;
            boolean bl2 = false;
            this.method = charSequence == null || StringsKt.isBlank(charSequence) ? null : value;
        }

        @Nullable
        public final String getMethod() {
            return this.method;
        }

        public final void setCharset(@Nullable String value) {
            CharSequence charSequence = value;
            boolean bl = false;
            boolean bl2 = false;
            this.charset = charSequence == null || StringsKt.isBlank(charSequence) ? null : value;
        }

        @Nullable
        public final String getCharset() {
            return this.charset;
        }

        public final void setRetry(@Nullable String value) {
            CharSequence charSequence = value;
            boolean bl = false;
            boolean bl2 = false;
            this.retry = charSequence == null || charSequence.length() == 0 ? null : StringsKt.toIntOrNull(value);
        }

        public final int getRetry() {
            Integer n = this.retry;
            return n == null ? 0 : n;
        }

        public final void setType(@Nullable String value) {
            CharSequence charSequence = value;
            boolean bl = false;
            boolean bl2 = false;
            this.type = charSequence == null || StringsKt.isBlank(charSequence) ? null : value;
        }

        @Nullable
        public final String getType() {
            return this.type;
        }

        public final boolean useWebView() {
            Object object = this.webView;
            return !(((object == null ? true : Intrinsics.areEqual(object, "")) ? true : Intrinsics.areEqual(object, false)) ? true : Intrinsics.areEqual(object, "false"));
        }

        public final void useWebView(boolean bl) {
            this.webView = bl ? Boolean.valueOf(true) : null;
        }

        /*
         * WARNING - void declaration
         */
        public final void setHeaders(@Nullable String value) {
            Map map;
            UrlOption urlOption = this;
            Object object = value;
            boolean bl = false;
            boolean bl2 = false;
            if (object == null || StringsKt.isBlank((CharSequence)object)) {
                map = null;
            } else {
                Object object2;
                object = GsonExtensionsKt.getGSON();
                UrlOption urlOption2 = urlOption;
                boolean $i$f$fromJsonObject = false;
                bl2 = false;
                try {
                    void $this$fromJsonObject$iv;
                    object2 = Result.Companion;
                    boolean bl3 = false;
                    boolean $i$f$genericType = false;
                    Type type = new TypeToken<Map<String, ? extends Object>>(){}.getType();
                    Intrinsics.checkNotNullExpressionValue(type, "object : TypeToken<T>() {}.type");
                    Object t = $this$fromJsonObject$iv.fromJson(value, type);
                    if (!(t instanceof Map)) {
                        t = null;
                    }
                    Map map2 = (Map)t;
                    boolean bl4 = false;
                    object2 = Result.constructor-impl(map2);
                }
                catch (Throwable throwable) {
                    Result.Companion companion = Result.Companion;
                    boolean bl5 = false;
                    object2 = Result.constructor-impl(ResultKt.createFailure(throwable));
                }
                Object object3 = object2;
                urlOption = urlOption2;
                object = object3;
                bl = false;
                map = (Map)(Result.isFailure-impl(object) ? null : object);
            }
            urlOption.headers = map;
        }

        /*
         * WARNING - void declaration
         */
        @Nullable
        public final Map<?, ?> getHeaderMap() {
            Map map;
            Object value = this.headers;
            if (value instanceof Map) {
                map = (Map)value;
            } else if (value instanceof String) {
                Object object;
                Object object2 = GsonExtensionsKt.getGSON();
                String json$iv = (String)value;
                boolean $i$f$fromJsonObject = false;
                boolean bl = false;
                try {
                    void $this$fromJsonObject$iv;
                    object = Result.Companion;
                    boolean bl2 = false;
                    boolean $i$f$genericType = false;
                    Type type = new TypeToken<Map<String, ? extends Object>>(){}.getType();
                    Intrinsics.checkNotNullExpressionValue(type, "object : TypeToken<T>() {}.type");
                    Object t = $this$fromJsonObject$iv.fromJson(json$iv, type);
                    if (!(t instanceof Map)) {
                        t = null;
                    }
                    Map map2 = (Map)t;
                    boolean bl3 = false;
                    object = Result.constructor-impl(map2);
                }
                catch (Throwable throwable) {
                    Result.Companion companion = Result.Companion;
                    boolean bl4 = false;
                    object = Result.constructor-impl(ResultKt.createFailure(throwable));
                }
                object2 = object;
                boolean bl5 = false;
                map = (Map)(Result.isFailure-impl(object2) ? null : object2);
            } else {
                map = null;
            }
            return map;
        }

        /*
         * WARNING - void declaration
         */
        public final void setBody(@Nullable String value) {
            Gson $this$fromJsonObject$iv;
            Serializable serializable;
            UrlOption urlOption = this;
            Object object = value;
            boolean bl = false;
            boolean bl2 = false;
            if (object == null || StringsKt.isBlank((CharSequence)object)) {
                serializable = null;
            } else if (StringExtensionsKt.isJsonObject(value)) {
                Object object2;
                object = GsonExtensionsKt.getGSON();
                UrlOption urlOption2 = urlOption;
                boolean $i$f$fromJsonObject = false;
                bl2 = false;
                try {
                    object2 = Result.Companion;
                    boolean $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 = false;
                    boolean $i$f$genericType = false;
                    Type type = new TypeToken<Map<String, ? extends Object>>(){}.getType();
                    Intrinsics.checkNotNullExpressionValue(type, "object : TypeToken<T>() {}.type");
                    Object t = $this$fromJsonObject$iv.fromJson(value, type);
                    if (!(t instanceof Map)) {
                        t = null;
                    }
                    Map $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22 = (Map)t;
                    boolean bl3 = false;
                    object2 = Result.constructor-impl($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22);
                }
                catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22) {
                    Result.Companion companion = Result.Companion;
                    boolean bl4 = false;
                    object2 = Result.constructor-impl(ResultKt.createFailure($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv22));
                }
                Object object3 = object2;
                urlOption = urlOption2;
                serializable = Result.box-impl(object3);
            } else if (StringExtensionsKt.isJsonArray(value)) {
                Object object4;
                $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
                UrlOption urlOption3 = urlOption;
                boolean $i$f$fromJsonArray = false;
                bl2 = false;
                try {
                    void $this$fromJsonArray$iv;
                    object4 = Result.Companion;
                    boolean bl5 = false;
                    Object t = $this$fromJsonArray$iv.fromJson(value, (Type)new ParameterizedTypeImpl(Map.class));
                    List list2 = t instanceof List ? (List)t : null;
                    boolean bl6 = false;
                    object4 = Result.constructor-impl(list2);
                }
                catch (Throwable throwable) {
                    Result.Companion companion = Result.Companion;
                    boolean bl7 = false;
                    object4 = Result.constructor-impl(ResultKt.createFailure(throwable));
                }
                Object object5 = object4;
                urlOption = urlOption3;
                serializable = Result.box-impl(object5);
            } else {
                serializable = (Serializable)((Object)value);
            }
            urlOption.body = serializable;
        }

        @Nullable
        public final String getBody() {
            String string;
            Object object = this.body;
            if (object == null) {
                string = null;
            } else {
                Object object2 = object;
                boolean bl = false;
                boolean bl2 = false;
                Object it = object2;
                boolean bl3 = false;
                string = it instanceof String ? (String)it : GsonExtensionsKt.getGSON().toJson(it);
            }
            return string;
        }

        public final void setWebJs(@Nullable String value) {
            CharSequence charSequence = value;
            boolean bl = false;
            boolean bl2 = false;
            this.webJs = charSequence == null || StringsKt.isBlank(charSequence) ? null : value;
        }

        @Nullable
        public final String getWebJs() {
            return this.webJs;
        }

        public final void setJs(@Nullable String value) {
            CharSequence charSequence = value;
            boolean bl = false;
            boolean bl2 = false;
            this.js = charSequence == null || StringsKt.isBlank(charSequence) ? null : value;
        }

        @Nullable
        public final String getJs() {
            return this.js;
        }

        private final String component1() {
            return this.method;
        }

        private final String component2() {
            return this.charset;
        }

        private final Object component3() {
            return this.headers;
        }

        private final Object component4() {
            return this.body;
        }

        private final Integer component5() {
            return this.retry;
        }

        private final String component6() {
            return this.type;
        }

        private final Object component7() {
            return this.webView;
        }

        private final String component8() {
            return this.webJs;
        }

        private final String component9() {
            return this.js;
        }

        @NotNull
        public final UrlOption copy(@Nullable String method, @Nullable String charset, @Nullable Object headers, @Nullable Object body, @Nullable Integer retry, @Nullable String type, @Nullable Object webView, @Nullable String webJs, @Nullable String js) {
            return new UrlOption(method, charset, headers, body, retry, type, webView, webJs, js);
        }

        public static /* synthetic */ UrlOption copy$default(UrlOption urlOption, String string, String string2, Object object, Object object2, Integer n, String string3, Object object3, String string4, String string5, int n2, Object object4) {
            if ((n2 & 1) != 0) {
                string = urlOption.method;
            }
            if ((n2 & 2) != 0) {
                string2 = urlOption.charset;
            }
            if ((n2 & 4) != 0) {
                object = urlOption.headers;
            }
            if ((n2 & 8) != 0) {
                object2 = urlOption.body;
            }
            if ((n2 & 0x10) != 0) {
                n = urlOption.retry;
            }
            if ((n2 & 0x20) != 0) {
                string3 = urlOption.type;
            }
            if ((n2 & 0x40) != 0) {
                object3 = urlOption.webView;
            }
            if ((n2 & 0x80) != 0) {
                string4 = urlOption.webJs;
            }
            if ((n2 & 0x100) != 0) {
                string5 = urlOption.js;
            }
            return urlOption.copy(string, string2, object, object2, n, string3, object3, string4, string5);
        }

        @NotNull
        public String toString() {
            return "UrlOption(method=" + this.method + ", charset=" + this.charset + ", headers=" + this.headers + ", body=" + this.body + ", retry=" + this.retry + ", type=" + this.type + ", webView=" + this.webView + ", webJs=" + this.webJs + ", js=" + this.js + ')';
        }

        public int hashCode() {
            int result2 = this.method == null ? 0 : this.method.hashCode();
            result2 = result2 * 31 + (this.charset == null ? 0 : this.charset.hashCode());
            result2 = result2 * 31 + (this.headers == null ? 0 : this.headers.hashCode());
            result2 = result2 * 31 + (this.body == null ? 0 : this.body.hashCode());
            result2 = result2 * 31 + (this.retry == null ? 0 : ((Object)this.retry).hashCode());
            result2 = result2 * 31 + (this.type == null ? 0 : this.type.hashCode());
            result2 = result2 * 31 + (this.webView == null ? 0 : this.webView.hashCode());
            result2 = result2 * 31 + (this.webJs == null ? 0 : this.webJs.hashCode());
            result2 = result2 * 31 + (this.js == null ? 0 : this.js.hashCode());
            return result2;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof UrlOption)) {
                return false;
            }
            UrlOption urlOption = (UrlOption)other;
            if (!Intrinsics.areEqual(this.method, urlOption.method)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.charset, urlOption.charset)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.headers, urlOption.headers)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.body, urlOption.body)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.retry, urlOption.retry)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.type, urlOption.type)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.webView, urlOption.webView)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.webJs, urlOption.webJs)) {
                return false;
            }
            return Intrinsics.areEqual(this.js, urlOption.js);
        }

        public UrlOption() {
            this(null, null, null, null, null, null, null, null, null, 511, null);
        }
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0013\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0007H\u00c6\u0003J'\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0017\u001a\u00020\u00032\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\u0007H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001c"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeUrl$ConcurrentRecord;", "", "concurrent", "", "time", "", "frequency", "", "(ZJI)V", "getConcurrent", "()Z", "getFrequency", "()I", "setFrequency", "(I)V", "getTime", "()J", "setTime", "(J)V", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "toString", "", "reader-pro"})
    public static final class ConcurrentRecord {
        private final boolean concurrent;
        private long time;
        private int frequency;

        public ConcurrentRecord(boolean concurrent, long time, int frequency) {
            this.concurrent = concurrent;
            this.time = time;
            this.frequency = frequency;
        }

        public final boolean getConcurrent() {
            return this.concurrent;
        }

        public final long getTime() {
            return this.time;
        }

        public final void setTime(long l) {
            this.time = l;
        }

        public final int getFrequency() {
            return this.frequency;
        }

        public final void setFrequency(int n) {
            this.frequency = n;
        }

        public final boolean component1() {
            return this.concurrent;
        }

        public final long component2() {
            return this.time;
        }

        public final int component3() {
            return this.frequency;
        }

        @NotNull
        public final ConcurrentRecord copy(boolean concurrent, long time, int frequency) {
            return new ConcurrentRecord(concurrent, time, frequency);
        }

        public static /* synthetic */ ConcurrentRecord copy$default(ConcurrentRecord concurrentRecord, boolean bl, long l, int n, int n2, Object object) {
            if ((n2 & 1) != 0) {
                bl = concurrentRecord.concurrent;
            }
            if ((n2 & 2) != 0) {
                l = concurrentRecord.time;
            }
            if ((n2 & 4) != 0) {
                n = concurrentRecord.frequency;
            }
            return concurrentRecord.copy(bl, l, n);
        }

        @NotNull
        public String toString() {
            return "ConcurrentRecord(concurrent=" + this.concurrent + ", time=" + this.time + ", frequency=" + this.frequency + ')';
        }

        public int hashCode() {
            int n = this.concurrent ? 1 : 0;
            if (n != 0) {
                n = 1;
            }
            int result2 = n;
            result2 = result2 * 31 + Long.hashCode(this.time);
            result2 = result2 * 31 + Integer.hashCode(this.frequency);
            return result2;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ConcurrentRecord)) {
                return false;
            }
            ConcurrentRecord concurrentRecord = (ConcurrentRecord)other;
            if (this.concurrent != concurrentRecord.concurrent) {
                return false;
            }
            if (this.time != concurrentRecord.time) {
                return false;
            }
            return this.frequency == concurrentRecord.frequency;
        }
    }

    @Metadata(mv={1, 5, 1}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[RequestMethod.values().length];
            nArray[RequestMethod.GET.ordinal()] = 1;
            nArray[RequestMethod.POST.ordinal()] = 2;
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

