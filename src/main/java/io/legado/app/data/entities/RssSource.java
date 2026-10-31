/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.Predicate;
import com.script.SimpleBindings;
import io.legado.app.data.entities.BaseSource;
import io.legado.app.help.http.StrResponse;
import io.legado.app.model.DebugLog;
import io.legado.app.model.analyzeRule.QueryTTF;
import io.legado.app.utils.JsonExtensionsKt;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Connection;

@JsonIgnoreProperties(value={"headerMap", "source", "_userNameSpace", "userNameSpace", "loginHeader", "loginHeaderMap", "loginInfo", "loginInfoMap"})
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\b[\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 \u0099\u00012\u00020\u0001:\u0002\u0099\u0001B\u00c3\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0012\u001a\u00020\t\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u001e\u001a\u00020\t\u0012\b\b\u0002\u0010\u001f\u001a\u00020\t\u0012\b\b\u0002\u0010 \u001a\u00020\u0014\u00a2\u0006\u0002\u0010!J\t\u0010f\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010g\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010h\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010i\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010j\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010k\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010l\u001a\u00020\tH\u00c6\u0003J\t\u0010m\u001a\u00020\u0014H\u00c6\u0003J\u000b\u0010n\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010o\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010p\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010q\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010r\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010s\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010t\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010u\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010v\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010w\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010x\u001a\u00020\tH\u00c6\u0003J\t\u0010y\u001a\u00020\tH\u00c6\u0003J\t\u0010z\u001a\u00020\u0014H\u00c6\u0003J\t\u0010{\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010|\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010}\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010~\u001a\u00020\tH\u00c6\u0003J\u000b\u0010\u007f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0011\u0010\u0080\u0001\u001a\u0004\u0018\u00010\tH\u00c6\u0003\u00a2\u0006\u0002\u00106J\f\u0010\u0081\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u00ce\u0002\u0010\u0082\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0012\u001a\u00020\t2\b\b\u0002\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u001e\u001a\u00020\t2\b\b\u0002\u0010\u001f\u001a\u00020\t2\b\b\u0002\u0010 \u001a\u00020\u0014H\u00c6\u0001\u00a2\u0006\u0003\u0010\u0083\u0001J\u0010\u0010\u0084\u0001\u001a\u00020\t2\u0007\u0010\u0085\u0001\u001a\u00020\u0000J\u001f\u0010\u0084\u0001\u001a\u00020\t2\t\u0010\u0086\u0001\u001a\u0004\u0018\u00010\u00032\t\u0010\u0087\u0001\u001a\u0004\u0018\u00010\u0003H\u0002J\u0016\u0010\u0088\u0001\u001a\u00020\t2\n\u0010\u0089\u0001\u001a\u0005\u0018\u00010\u008a\u0001H\u0096\u0002J\t\u0010\u008b\u0001\u001a\u00020\u0003H\u0016J\u000b\u0010\u008c\u0001\u001a\u0004\u0018\u00010.H\u0016J\t\u0010\u008d\u0001\u001a\u00020\u0003H\u0016J\t\u0010\u008e\u0001\u001a\u00020\u0003H\u0016J\t\u0010\u008f\u0001\u001a\u00020\u0014H\u0016J\u0013\u0010\u0090\u0001\u001a\u00030\u0091\u00012\t\u0010\u0092\u0001\u001a\u0004\u0018\u00010.J\u0011\u0010\u0093\u0001\u001a\u00030\u0091\u00012\u0007\u0010\u0094\u0001\u001a\u00020\u0003J\u001b\u0010\u0095\u0001\u001a\u0016\u0012\u0011\u0012\u000f\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0097\u00010\u0096\u0001J\n\u0010\u0098\u0001\u001a\u00020\u0003H\u00d6\u0001R\u000e\u0010\"\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001c\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001a\u0010 \u001a\u00020\u0014X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010$\"\u0004\b,\u0010&R\u0010\u0010-\u001a\u0004\u0018\u00010.X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001e\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b/\u00100\"\u0004\b1\u00102R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b3\u00100\"\u0004\b4\u00102R\u001e\u0010\u000b\u001a\u0004\u0018\u00010\tX\u0096\u000e\u00a2\u0006\u0010\n\u0002\u00109\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u001c\u0010\r\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b:\u0010(\"\u0004\b;\u0010*R\u001a\u0010\u001f\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b<\u00100\"\u0004\b=\u00102R\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b>\u0010(\"\u0004\b?\u0010*R\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b@\u0010(\"\u0004\bA\u0010*R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bB\u0010(\"\u0004\bC\u0010*R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bD\u0010(\"\u0004\bE\u0010*R\u001c\u0010\u001c\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bF\u0010(\"\u0004\bG\u0010*R\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bH\u0010(\"\u0004\bI\u0010*R\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010(\"\u0004\bK\u0010*R\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bL\u0010(\"\u0004\bM\u0010*R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bN\u0010(\"\u0004\bO\u0010*R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bP\u0010(\"\u0004\bQ\u0010*R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bR\u0010(\"\u0004\bS\u0010*R\u001a\u0010\u0012\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bT\u00100\"\u0004\bU\u00102R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bV\u0010(\"\u0004\bW\u0010*R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bX\u0010(\"\u0004\bY\u0010*R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bZ\u0010(\"\u0004\b[\u0010*R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\\\u0010(\"\u0004\b]\u0010*R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b^\u0010(\"\u0004\b_\u0010*R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b`\u0010(\"\u0004\ba\u0010*R\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bb\u0010(\"\u0004\bc\u0010*R\u001c\u0010\n\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bd\u0010(\"\u0004\be\u0010*\u00a8\u0006\u009a\u0001"}, d2={"Lio/legado/app/data/entities/RssSource;", "Lio/legado/app/data/entities/BaseSource;", "sourceUrl", "", "sourceName", "sourceIcon", "sourceGroup", "sourceComment", "enabled", "", "variableComment", "enabledCookieJar", "concurrentRate", "header", "loginUrl", "loginUi", "loginCheckJs", "sortUrl", "singleUrl", "articleStyle", "", "ruleArticles", "ruleNextPage", "ruleTitle", "rulePubDate", "ruleDescription", "ruleImage", "ruleLink", "ruleContent", "style", "enableJs", "loadWithBaseUrl", "customOrder", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZI)V", "_userNameSpace", "getArticleStyle", "()I", "setArticleStyle", "(I)V", "getConcurrentRate", "()Ljava/lang/String;", "setConcurrentRate", "(Ljava/lang/String;)V", "getCustomOrder", "setCustomOrder", "debugLog", "Lio/legado/app/model/DebugLog;", "getEnableJs", "()Z", "setEnableJs", "(Z)V", "getEnabled", "setEnabled", "getEnabledCookieJar", "()Ljava/lang/Boolean;", "setEnabledCookieJar", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "getHeader", "setHeader", "getLoadWithBaseUrl", "setLoadWithBaseUrl", "getLoginCheckJs", "setLoginCheckJs", "getLoginUi", "setLoginUi", "getLoginUrl", "setLoginUrl", "getRuleArticles", "setRuleArticles", "getRuleContent", "setRuleContent", "getRuleDescription", "setRuleDescription", "getRuleImage", "setRuleImage", "getRuleLink", "setRuleLink", "getRuleNextPage", "setRuleNextPage", "getRulePubDate", "setRulePubDate", "getRuleTitle", "setRuleTitle", "getSingleUrl", "setSingleUrl", "getSortUrl", "setSortUrl", "getSourceComment", "setSourceComment", "getSourceGroup", "setSourceGroup", "getSourceIcon", "setSourceIcon", "getSourceName", "setSourceName", "getSourceUrl", "setSourceUrl", "getStyle", "setStyle", "getVariableComment", "setVariableComment", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZI)Lio/legado/app/data/entities/RssSource;", "equal", "source", "a", "b", "equals", "other", "", "getKey", "getLogger", "getTag", "getUserNameSpace", "hashCode", "setLogger", "", "logger", "setUserNameSpace", "nameSpace", "sortUrls", "", "Lkotlin/Pair;", "toString", "Companion", "reader-pro"})
public final class RssSource
implements BaseSource {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private String sourceUrl;
    @NotNull
    private String sourceName;
    @NotNull
    private String sourceIcon;
    @Nullable
    private String sourceGroup;
    @Nullable
    private String sourceComment;
    private boolean enabled;
    @Nullable
    private String variableComment;
    @Nullable
    private Boolean enabledCookieJar;
    @Nullable
    private String concurrentRate;
    @Nullable
    private String header;
    @Nullable
    private String loginUrl;
    @Nullable
    private String loginUi;
    @Nullable
    private String loginCheckJs;
    @Nullable
    private String sortUrl;
    private boolean singleUrl;
    private int articleStyle;
    @Nullable
    private String ruleArticles;
    @Nullable
    private String ruleNextPage;
    @Nullable
    private String ruleTitle;
    @Nullable
    private String rulePubDate;
    @Nullable
    private String ruleDescription;
    @Nullable
    private String ruleImage;
    @Nullable
    private String ruleLink;
    @Nullable
    private String ruleContent;
    @Nullable
    private String style;
    private boolean enableJs;
    private boolean loadWithBaseUrl;
    private int customOrder;
    @NotNull
    private transient String _userNameSpace;
    @Nullable
    private transient DebugLog debugLog;

    public RssSource(@NotNull String sourceUrl, @NotNull String sourceName, @NotNull String sourceIcon, @Nullable String sourceGroup, @Nullable String sourceComment, boolean enabled, @Nullable String variableComment, @Nullable Boolean enabledCookieJar, @Nullable String concurrentRate, @Nullable String header, @Nullable String loginUrl, @Nullable String loginUi, @Nullable String loginCheckJs, @Nullable String sortUrl, boolean singleUrl, int articleStyle, @Nullable String ruleArticles, @Nullable String ruleNextPage, @Nullable String ruleTitle, @Nullable String rulePubDate, @Nullable String ruleDescription, @Nullable String ruleImage, @Nullable String ruleLink, @Nullable String ruleContent, @Nullable String style, boolean enableJs, boolean loadWithBaseUrl, int customOrder) {
        Intrinsics.checkNotNullParameter(sourceUrl, "sourceUrl");
        Intrinsics.checkNotNullParameter(sourceName, "sourceName");
        Intrinsics.checkNotNullParameter(sourceIcon, "sourceIcon");
        this.sourceUrl = sourceUrl;
        this.sourceName = sourceName;
        this.sourceIcon = sourceIcon;
        this.sourceGroup = sourceGroup;
        this.sourceComment = sourceComment;
        this.enabled = enabled;
        this.variableComment = variableComment;
        this.enabledCookieJar = enabledCookieJar;
        this.concurrentRate = concurrentRate;
        this.header = header;
        this.loginUrl = loginUrl;
        this.loginUi = loginUi;
        this.loginCheckJs = loginCheckJs;
        this.sortUrl = sortUrl;
        this.singleUrl = singleUrl;
        this.articleStyle = articleStyle;
        this.ruleArticles = ruleArticles;
        this.ruleNextPage = ruleNextPage;
        this.ruleTitle = ruleTitle;
        this.rulePubDate = rulePubDate;
        this.ruleDescription = ruleDescription;
        this.ruleImage = ruleImage;
        this.ruleLink = ruleLink;
        this.ruleContent = ruleContent;
        this.style = style;
        this.enableJs = enableJs;
        this.loadWithBaseUrl = loadWithBaseUrl;
        this.customOrder = customOrder;
        this._userNameSpace = "";
    }

    public /* synthetic */ RssSource(String string, String string2, String string3, String string4, String string5, boolean bl, String string6, Boolean bl2, String string7, String string8, String string9, String string10, String string11, String string12, boolean bl3, int n, String string13, String string14, String string15, String string16, String string17, String string18, String string19, String string20, String string21, boolean bl4, boolean bl5, int n2, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 1) != 0) {
            string = "";
        }
        if ((n3 & 2) != 0) {
            string2 = "";
        }
        if ((n3 & 4) != 0) {
            string3 = "";
        }
        if ((n3 & 8) != 0) {
            string4 = null;
        }
        if ((n3 & 0x10) != 0) {
            string5 = null;
        }
        if ((n3 & 0x20) != 0) {
            bl = true;
        }
        if ((n3 & 0x40) != 0) {
            string6 = null;
        }
        if ((n3 & 0x80) != 0) {
            bl2 = false;
        }
        if ((n3 & 0x100) != 0) {
            string7 = null;
        }
        if ((n3 & 0x200) != 0) {
            string8 = null;
        }
        if ((n3 & 0x400) != 0) {
            string9 = null;
        }
        if ((n3 & 0x800) != 0) {
            string10 = null;
        }
        if ((n3 & 0x1000) != 0) {
            string11 = null;
        }
        if ((n3 & 0x2000) != 0) {
            string12 = null;
        }
        if ((n3 & 0x4000) != 0) {
            bl3 = false;
        }
        if ((n3 & 0x8000) != 0) {
            n = 0;
        }
        if ((n3 & 0x10000) != 0) {
            string13 = null;
        }
        if ((n3 & 0x20000) != 0) {
            string14 = null;
        }
        if ((n3 & 0x40000) != 0) {
            string15 = null;
        }
        if ((n3 & 0x80000) != 0) {
            string16 = null;
        }
        if ((n3 & 0x100000) != 0) {
            string17 = null;
        }
        if ((n3 & 0x200000) != 0) {
            string18 = null;
        }
        if ((n3 & 0x400000) != 0) {
            string19 = null;
        }
        if ((n3 & 0x800000) != 0) {
            string20 = null;
        }
        if ((n3 & 0x1000000) != 0) {
            string21 = null;
        }
        if ((n3 & 0x2000000) != 0) {
            bl4 = true;
        }
        if ((n3 & 0x4000000) != 0) {
            bl5 = true;
        }
        if ((n3 & 0x8000000) != 0) {
            n2 = 0;
        }
        this(string, string2, string3, string4, string5, bl, string6, bl2, string7, string8, string9, string10, string11, string12, bl3, n, string13, string14, string15, string16, string17, string18, string19, string20, string21, bl4, bl5, n2);
    }

    @NotNull
    public final String getSourceUrl() {
        return this.sourceUrl;
    }

    public final void setSourceUrl(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.sourceUrl = string;
    }

    @NotNull
    public final String getSourceName() {
        return this.sourceName;
    }

    public final void setSourceName(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.sourceName = string;
    }

    @NotNull
    public final String getSourceIcon() {
        return this.sourceIcon;
    }

    public final void setSourceIcon(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.sourceIcon = string;
    }

    @Nullable
    public final String getSourceGroup() {
        return this.sourceGroup;
    }

    public final void setSourceGroup(@Nullable String string) {
        this.sourceGroup = string;
    }

    @Nullable
    public final String getSourceComment() {
        return this.sourceComment;
    }

    public final void setSourceComment(@Nullable String string) {
        this.sourceComment = string;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    public final void setEnabled(boolean bl) {
        this.enabled = bl;
    }

    @Nullable
    public final String getVariableComment() {
        return this.variableComment;
    }

    public final void setVariableComment(@Nullable String string) {
        this.variableComment = string;
    }

    @Override
    @Nullable
    public Boolean getEnabledCookieJar() {
        return this.enabledCookieJar;
    }

    @Override
    public void setEnabledCookieJar(@Nullable Boolean bl) {
        this.enabledCookieJar = bl;
    }

    @Override
    @Nullable
    public String getConcurrentRate() {
        return this.concurrentRate;
    }

    @Override
    public void setConcurrentRate(@Nullable String string) {
        this.concurrentRate = string;
    }

    @Override
    @Nullable
    public String getHeader() {
        return this.header;
    }

    @Override
    public void setHeader(@Nullable String string) {
        this.header = string;
    }

    @Override
    @Nullable
    public String getLoginUrl() {
        return this.loginUrl;
    }

    @Override
    public void setLoginUrl(@Nullable String string) {
        this.loginUrl = string;
    }

    @Override
    @Nullable
    public String getLoginUi() {
        return this.loginUi;
    }

    @Override
    public void setLoginUi(@Nullable String string) {
        this.loginUi = string;
    }

    @Nullable
    public final String getLoginCheckJs() {
        return this.loginCheckJs;
    }

    public final void setLoginCheckJs(@Nullable String string) {
        this.loginCheckJs = string;
    }

    @Nullable
    public final String getSortUrl() {
        return this.sortUrl;
    }

    public final void setSortUrl(@Nullable String string) {
        this.sortUrl = string;
    }

    public final boolean getSingleUrl() {
        return this.singleUrl;
    }

    public final void setSingleUrl(boolean bl) {
        this.singleUrl = bl;
    }

    public final int getArticleStyle() {
        return this.articleStyle;
    }

    public final void setArticleStyle(int n) {
        this.articleStyle = n;
    }

    @Nullable
    public final String getRuleArticles() {
        return this.ruleArticles;
    }

    public final void setRuleArticles(@Nullable String string) {
        this.ruleArticles = string;
    }

    @Nullable
    public final String getRuleNextPage() {
        return this.ruleNextPage;
    }

    public final void setRuleNextPage(@Nullable String string) {
        this.ruleNextPage = string;
    }

    @Nullable
    public final String getRuleTitle() {
        return this.ruleTitle;
    }

    public final void setRuleTitle(@Nullable String string) {
        this.ruleTitle = string;
    }

    @Nullable
    public final String getRulePubDate() {
        return this.rulePubDate;
    }

    public final void setRulePubDate(@Nullable String string) {
        this.rulePubDate = string;
    }

    @Nullable
    public final String getRuleDescription() {
        return this.ruleDescription;
    }

    public final void setRuleDescription(@Nullable String string) {
        this.ruleDescription = string;
    }

    @Nullable
    public final String getRuleImage() {
        return this.ruleImage;
    }

    public final void setRuleImage(@Nullable String string) {
        this.ruleImage = string;
    }

    @Nullable
    public final String getRuleLink() {
        return this.ruleLink;
    }

    public final void setRuleLink(@Nullable String string) {
        this.ruleLink = string;
    }

    @Nullable
    public final String getRuleContent() {
        return this.ruleContent;
    }

    public final void setRuleContent(@Nullable String string) {
        this.ruleContent = string;
    }

    @Nullable
    public final String getStyle() {
        return this.style;
    }

    public final void setStyle(@Nullable String string) {
        this.style = string;
    }

    public final boolean getEnableJs() {
        return this.enableJs;
    }

    public final void setEnableJs(boolean bl) {
        this.enableJs = bl;
    }

    public final boolean getLoadWithBaseUrl() {
        return this.loadWithBaseUrl;
    }

    public final void setLoadWithBaseUrl(boolean bl) {
        this.loadWithBaseUrl = bl;
    }

    public final int getCustomOrder() {
        return this.customOrder;
    }

    public final void setCustomOrder(int n) {
        this.customOrder = n;
    }

    @Override
    @NotNull
    public String getTag() {
        return this.sourceName;
    }

    @Override
    @NotNull
    public String getKey() {
        return this.sourceUrl;
    }

    public boolean equals(@Nullable Object other) {
        if (other instanceof RssSource) {
            return Intrinsics.areEqual(((RssSource)other).sourceUrl, this.sourceUrl);
        }
        return false;
    }

    public int hashCode() {
        return this.sourceUrl.hashCode();
    }

    public final boolean equal(@NotNull RssSource source) {
        Intrinsics.checkNotNullParameter(source, "source");
        return this.equal(this.sourceUrl, source.sourceUrl) && this.equal(this.sourceIcon, source.sourceIcon) && this.enabled == source.enabled && Intrinsics.areEqual(this.getEnabledCookieJar(), source.getEnabledCookieJar()) && this.equal(this.sourceComment, source.sourceComment) && this.equal(this.sourceGroup, source.sourceGroup) && this.equal(this.ruleArticles, source.ruleArticles) && this.equal(this.ruleNextPage, source.ruleNextPage) && this.equal(this.ruleTitle, source.ruleTitle) && this.equal(this.rulePubDate, source.rulePubDate) && this.equal(this.ruleDescription, source.ruleDescription) && this.equal(this.ruleLink, source.ruleLink) && this.equal(this.ruleContent, source.ruleContent) && this.enableJs == source.enableJs && this.loadWithBaseUrl == source.loadWithBaseUrl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final boolean equal(String a, String b) {
        if (Intrinsics.areEqual(a, b)) return true;
        CharSequence charSequence = a;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence != null) {
            if (charSequence.length() != 0) return false;
        }
        boolean bl3 = true;
        if (!bl3) return false;
        charSequence = b;
        bl = false;
        bl2 = false;
        if (charSequence == null) return true;
        if (charSequence.length() != 0) return false;
        return true;
    }

    @NotNull
    public final List<Pair<String, String>> sortUrls() {
        boolean bl = false;
        ArrayList<Pair<Object, Object>> arrayList = new ArrayList<Pair<Object, Object>>();
        boolean bl2 = false;
        boolean bl3 = false;
        ArrayList<Pair<Object, Object>> $this$sortUrls_u24lambda_u2d2 = arrayList;
        boolean bl4 = false;
        boolean bl5 = false;
        try {
            int n;
            List<String> list2;
            Object object = Result.Companion;
            boolean bl6 = false;
            String a = this.getSortUrl();
            String string = this.getSortUrl();
            if ((string == null ? false : StringsKt.startsWith(string, "<js>", false)) || ((string = this.getSortUrl()) == null ? false : StringsKt.startsWith(string, "@js:", false))) {
                String string2;
                int n2;
                int n3;
                String string3 = this.getSortUrl();
                Intrinsics.checkNotNull(string3);
                if (StringsKt.startsWith$default(string3, "@", false, 2, null)) {
                    String string4 = this.getSortUrl();
                    Intrinsics.checkNotNull(string4);
                    list2 = string4;
                    n3 = 4;
                    n2 = 0;
                    String string5 = list2;
                    if (string5 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    String string6 = string5.substring(n3);
                    string2 = string6;
                    Intrinsics.checkNotNullExpressionValue(string6, "(this as java.lang.String).substring(startIndex)");
                } else {
                    String string7 = this.getSortUrl();
                    Intrinsics.checkNotNull(string7);
                    list2 = string7;
                    n3 = 4;
                    String string8 = this.getSortUrl();
                    Intrinsics.checkNotNull(string8);
                    n2 = StringsKt.lastIndexOf$default((CharSequence)string8, "<", 0, false, 6, null);
                    n = 0;
                    String string9 = list2;
                    if (string9 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    String string10 = string9.substring(n3, n2);
                    string2 = string10;
                    Intrinsics.checkNotNullExpressionValue(string10, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                }
                String jsStr = string2;
                a = String.valueOf(BaseSource.DefaultImpls.evalJS$default(this, jsStr, null, 2, null));
            }
            if ((string = a) != null) {
                CharSequence charSequence = string;
                Object object2 = "(&&|\n)+";
                n = 0;
                object2 = new Regex((String)object2);
                n = 0;
                boolean bl7 = false;
                list2 = ((Regex)object2).split(charSequence, n);
                if (list2 != null) {
                    Iterable $this$forEach$iv = list2;
                    boolean $i$f$forEach = false;
                    for (Object element$iv : $this$forEach$iv) {
                        String c = (String)element$iv;
                        boolean bl8 = false;
                        String[] stringArray = new String[]{"::"};
                        List d = StringsKt.split$default((CharSequence)c, stringArray, false, 0, 6, null);
                        if (d.size() <= 1) continue;
                        $this$sortUrls_u24lambda_u2d2.add(new Pair(d.get(0), d.get(1)));
                    }
                }
            }
            if ($this$sortUrls_u24lambda_u2d2.isEmpty()) {
                $this$sortUrls_u24lambda_u2d2.add(new Pair<String, String>("", this.getSourceUrl()));
            }
            Unit unit = Unit.INSTANCE;
            boolean bl9 = false;
            object = Result.constructor-impl(unit);
        }
        catch (Throwable throwable) {
            Result.Companion companion = Result.Companion;
            boolean bl10 = false;
            Object object = Result.constructor-impl(ResultKt.createFailure(throwable));
        }
        return arrayList;
    }

    public final void setUserNameSpace(@NotNull String nameSpace) {
        Intrinsics.checkNotNullParameter(nameSpace, "nameSpace");
        this._userNameSpace = nameSpace;
    }

    @Override
    @NotNull
    public String getUserNameSpace() {
        return this._userNameSpace;
    }

    public final void setLogger(@Nullable DebugLog logger2) {
        this.debugLog = logger2;
    }

    @Override
    @Nullable
    public DebugLog getLogger() {
        return null;
    }

    @Override
    @Nullable
    public Object evalJS(@NotNull String jsStr, @NotNull Function1<? super SimpleBindings, Unit> bindingsConfig) throws Exception {
        return BaseSource.DefaultImpls.evalJS(this, jsStr, bindingsConfig);
    }

    @Override
    @Nullable
    public byte[] aesBase64DecodeToByteArray(@NotNull String str, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.aesBase64DecodeToByteArray(this, str, key, transformation, iv);
    }

    @Override
    @Nullable
    public String aesBase64DecodeToString(@NotNull String str, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.aesBase64DecodeToString(this, str, key, transformation, iv);
    }

    @Override
    @Nullable
    public String aesDecodeArgsBase64Str(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return BaseSource.DefaultImpls.aesDecodeArgsBase64Str(this, data, key, mode, padding, iv);
    }

    @Override
    @Nullable
    public byte[] aesDecodeToByteArray(@NotNull String str, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.aesDecodeToByteArray(this, str, key, transformation, iv);
    }

    @Override
    @Nullable
    public String aesDecodeToString(@NotNull String str, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.aesDecodeToString(this, str, key, transformation, iv);
    }

    @Override
    @Nullable
    public String aesEncodeArgsBase64Str(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return BaseSource.DefaultImpls.aesEncodeArgsBase64Str(this, data, key, mode, padding, iv);
    }

    @Override
    @Nullable
    public byte[] aesEncodeToBase64ByteArray(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.aesEncodeToBase64ByteArray(this, data, key, transformation, iv);
    }

    @Override
    @Nullable
    public String aesEncodeToBase64String(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.aesEncodeToBase64String(this, data, key, transformation, iv);
    }

    @Override
    @Nullable
    public byte[] aesEncodeToByteArray(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.aesEncodeToByteArray(this, data, key, transformation, iv);
    }

    @Override
    @Nullable
    public String aesEncodeToString(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.aesEncodeToString(this, data, key, transformation, iv);
    }

    @Override
    @Nullable
    public String ajax(@NotNull String urlStr) {
        return BaseSource.DefaultImpls.ajax(this, urlStr);
    }

    @Override
    @NotNull
    public StrResponse[] ajaxAll(@NotNull String[] urlList) {
        return BaseSource.DefaultImpls.ajaxAll(this, urlList);
    }

    @Override
    @NotNull
    public String androidId() {
        return BaseSource.DefaultImpls.androidId(this);
    }

    @Override
    @NotNull
    public String base64Decode(@NotNull String str) {
        return BaseSource.DefaultImpls.base64Decode(this, str);
    }

    @Override
    @NotNull
    public String base64Decode(@NotNull String str, int flags) {
        return BaseSource.DefaultImpls.base64Decode(this, str, flags);
    }

    @Override
    @Nullable
    public byte[] base64DecodeToByteArray(@Nullable String str) {
        return BaseSource.DefaultImpls.base64DecodeToByteArray(this, str);
    }

    @Override
    @Nullable
    public byte[] base64DecodeToByteArray(@Nullable String str, int flags) {
        return BaseSource.DefaultImpls.base64DecodeToByteArray(this, str, flags);
    }

    @Override
    @Nullable
    public String base64Encode(@NotNull String str) {
        return BaseSource.DefaultImpls.base64Encode(this, str);
    }

    @Override
    @Nullable
    public String base64Encode(@NotNull String str, int flags) {
        return BaseSource.DefaultImpls.base64Encode(this, str, flags);
    }

    @Override
    @Nullable
    public String cacheFile(@NotNull String urlStr) {
        return BaseSource.DefaultImpls.cacheFile(this, urlStr);
    }

    @Override
    @Nullable
    public String cacheFile(@NotNull String urlStr, int saveTime) {
        return BaseSource.DefaultImpls.cacheFile(this, urlStr, saveTime);
    }

    @Override
    @NotNull
    public StrResponse connect(@NotNull String urlStr) {
        return BaseSource.DefaultImpls.connect(this, urlStr);
    }

    @Override
    @NotNull
    public StrResponse connect(@NotNull String urlStr, @Nullable String header) {
        return BaseSource.DefaultImpls.connect(this, urlStr, header);
    }

    @Override
    public void deleteFile(@NotNull String path) {
        BaseSource.DefaultImpls.deleteFile(this, path);
    }

    @Override
    @Nullable
    public String desBase64DecodeToString(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.desBase64DecodeToString(this, data, key, transformation, iv);
    }

    @Override
    @Nullable
    public String desDecodeToString(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.desDecodeToString(this, data, key, transformation, iv);
    }

    @Override
    @Nullable
    public String desEncodeToBase64String(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.desEncodeToBase64String(this, data, key, transformation, iv);
    }

    @Override
    @Nullable
    public String desEncodeToString(@NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
        return BaseSource.DefaultImpls.desEncodeToString(this, data, key, transformation, iv);
    }

    @Override
    @Nullable
    public String digestBase64Str(@NotNull String data, @NotNull String algorithm) {
        return BaseSource.DefaultImpls.digestBase64Str(this, data, algorithm);
    }

    @Override
    @Nullable
    public String digestHex(@NotNull String data, @NotNull String algorithm) {
        return BaseSource.DefaultImpls.digestHex(this, data, algorithm);
    }

    @Override
    @NotNull
    public String downloadFile(@NotNull String content, @NotNull String url2) {
        return BaseSource.DefaultImpls.downloadFile(this, content, url2);
    }

    @Override
    @NotNull
    public String encodeURI(@NotNull String str) {
        return BaseSource.DefaultImpls.encodeURI(this, str);
    }

    @Override
    @NotNull
    public String encodeURI(@NotNull String str, @NotNull String enc) {
        return BaseSource.DefaultImpls.encodeURI(this, str, enc);
    }

    @Override
    @NotNull
    public Connection.Response get(@NotNull String urlStr, @NotNull Map<String, String> headers) {
        return BaseSource.DefaultImpls.get(this, urlStr, headers);
    }

    @Override
    @NotNull
    public String getCookie(@NotNull String tag, @Nullable String key) {
        return BaseSource.DefaultImpls.getCookie(this, tag, key);
    }

    @Override
    @NotNull
    public File getFile(@NotNull String path) {
        return BaseSource.DefaultImpls.getFile(this, path);
    }

    @Override
    @NotNull
    public HashMap<String, String> getHeaderMap(boolean hasLoginHeader) {
        return BaseSource.DefaultImpls.getHeaderMap(this, hasLoginHeader);
    }

    @Override
    @Nullable
    public String getLoginHeader() {
        return BaseSource.DefaultImpls.getLoginHeader(this);
    }

    @Override
    @Nullable
    public Map<String, String> getLoginHeaderMap() {
        return BaseSource.DefaultImpls.getLoginHeaderMap(this);
    }

    @Override
    @Nullable
    public String getLoginInfo() {
        return BaseSource.DefaultImpls.getLoginInfo(this);
    }

    @Override
    @Nullable
    public Map<String, String> getLoginInfoMap() {
        return BaseSource.DefaultImpls.getLoginInfoMap(this);
    }

    @Override
    @Nullable
    public String getLoginJs() {
        return BaseSource.DefaultImpls.getLoginJs(this);
    }

    @Override
    @Nullable
    public BaseSource getSource() {
        return BaseSource.DefaultImpls.getSource(this);
    }

    @Override
    @NotNull
    public String getTxtInFolder(@NotNull String unzipPath) {
        return BaseSource.DefaultImpls.getTxtInFolder(this, unzipPath);
    }

    @Override
    @Nullable
    public String getVariable() {
        return BaseSource.DefaultImpls.getVariable(this);
    }

    @Override
    @Nullable
    public byte[] getZipByteArrayContent(@NotNull String url2, @NotNull String path) {
        return BaseSource.DefaultImpls.getZipByteArrayContent(this, url2, path);
    }

    @Override
    @NotNull
    public String getZipStringContent(@NotNull String url2, @NotNull String path) {
        return BaseSource.DefaultImpls.getZipStringContent(this, url2, path);
    }

    @Override
    @NotNull
    public String getZipStringContent(@NotNull String url2, @NotNull String path, @NotNull String charsetName) {
        return BaseSource.DefaultImpls.getZipStringContent(this, url2, path, charsetName);
    }

    @Override
    @NotNull
    public Connection.Response head(@NotNull String urlStr, @NotNull Map<String, String> headers) {
        return BaseSource.DefaultImpls.head(this, urlStr, headers);
    }

    @Override
    @NotNull
    public String htmlFormat(@NotNull String str) {
        return BaseSource.DefaultImpls.htmlFormat(this, str);
    }

    @Override
    @NotNull
    public String importScript(@NotNull String path) {
        return BaseSource.DefaultImpls.importScript(this, path);
    }

    @Override
    @NotNull
    public String log(@NotNull String msg) {
        return BaseSource.DefaultImpls.log(this, msg);
    }

    @Override
    public void logType(@Nullable Object any) {
        BaseSource.DefaultImpls.logType(this, any);
    }

    @Override
    public void login() {
        BaseSource.DefaultImpls.login(this);
    }

    @Override
    public void longToast(@Nullable Object msg) {
        BaseSource.DefaultImpls.longToast(this, msg);
    }

    @Override
    @NotNull
    public String md5Encode(@NotNull String str) {
        return BaseSource.DefaultImpls.md5Encode(this, str);
    }

    @Override
    @NotNull
    public String md5Encode16(@NotNull String str) {
        return BaseSource.DefaultImpls.md5Encode16(this, str);
    }

    @Override
    @NotNull
    public Connection.Response post(@NotNull String urlStr, @NotNull String body, @NotNull Map<String, String> headers) {
        return BaseSource.DefaultImpls.post(this, urlStr, body, headers);
    }

    @Override
    public void putLoginHeader(@NotNull String header) {
        BaseSource.DefaultImpls.putLoginHeader(this, header);
    }

    @Override
    public boolean putLoginInfo(@NotNull String info) {
        return BaseSource.DefaultImpls.putLoginInfo(this, info);
    }

    @Override
    @Nullable
    public QueryTTF queryBase64TTF(@Nullable String base64) {
        return BaseSource.DefaultImpls.queryBase64TTF(this, base64);
    }

    @Override
    @Nullable
    public QueryTTF queryTTF(@Nullable String str) {
        return BaseSource.DefaultImpls.queryTTF(this, str);
    }

    @Override
    @NotNull
    public String randomUUID() {
        return BaseSource.DefaultImpls.randomUUID(this);
    }

    @Override
    @Nullable
    public byte[] readFile(@NotNull String path) {
        return BaseSource.DefaultImpls.readFile(this, path);
    }

    @Override
    @NotNull
    public String readTxtFile(@NotNull String path) {
        return BaseSource.DefaultImpls.readTxtFile(this, path);
    }

    @Override
    @NotNull
    public String readTxtFile(@NotNull String path, @NotNull String charsetName) {
        return BaseSource.DefaultImpls.readTxtFile(this, path, charsetName);
    }

    @Override
    public void removeLoginHeader() {
        BaseSource.DefaultImpls.removeLoginHeader(this);
    }

    @Override
    public void removeLoginInfo() {
        BaseSource.DefaultImpls.removeLoginInfo(this);
    }

    @Override
    @NotNull
    public String replaceFont(@NotNull String text, @Nullable QueryTTF font1, @Nullable QueryTTF font2) {
        return BaseSource.DefaultImpls.replaceFont(this, text, font1, font2);
    }

    @Override
    public void setVariable(@Nullable String variable) {
        BaseSource.DefaultImpls.setVariable(this, variable);
    }

    @Override
    @NotNull
    public String timeFormat(long time) {
        return BaseSource.DefaultImpls.timeFormat(this, time);
    }

    @Override
    @Nullable
    public String timeFormatUTC(long time, @NotNull String format, int sh) {
        return BaseSource.DefaultImpls.timeFormatUTC(this, time, format, sh);
    }

    @Override
    public void toast(@Nullable Object msg) {
        BaseSource.DefaultImpls.toast(this, msg);
    }

    @Override
    @Nullable
    public String tripleDESDecodeArgsBase64Str(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return BaseSource.DefaultImpls.tripleDESDecodeArgsBase64Str(this, data, key, mode, padding, iv);
    }

    @Override
    @Nullable
    public String tripleDESDecodeStr(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return BaseSource.DefaultImpls.tripleDESDecodeStr(this, data, key, mode, padding, iv);
    }

    @Override
    @Nullable
    public String tripleDESEncodeArgsBase64Str(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return BaseSource.DefaultImpls.tripleDESEncodeArgsBase64Str(this, data, key, mode, padding, iv);
    }

    @Override
    @Nullable
    public String tripleDESEncodeBase64Str(@NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
        return BaseSource.DefaultImpls.tripleDESEncodeBase64Str(this, data, key, mode, padding, iv);
    }

    @Override
    @NotNull
    public String unzipFile(@NotNull String zipPath) {
        return BaseSource.DefaultImpls.unzipFile(this, zipPath);
    }

    @Override
    @NotNull
    public String utf8ToGbk(@NotNull String str) {
        return BaseSource.DefaultImpls.utf8ToGbk(this, str);
    }

    @Override
    @Nullable
    public String webView(@Nullable String html, @Nullable String url2, @Nullable String js) {
        return BaseSource.DefaultImpls.webView(this, html, url2, js);
    }

    @NotNull
    public final String component1() {
        return this.sourceUrl;
    }

    @NotNull
    public final String component2() {
        return this.sourceName;
    }

    @NotNull
    public final String component3() {
        return this.sourceIcon;
    }

    @Nullable
    public final String component4() {
        return this.sourceGroup;
    }

    @Nullable
    public final String component5() {
        return this.sourceComment;
    }

    public final boolean component6() {
        return this.enabled;
    }

    @Nullable
    public final String component7() {
        return this.variableComment;
    }

    @Nullable
    public final Boolean component8() {
        return this.getEnabledCookieJar();
    }

    @Nullable
    public final String component9() {
        return this.getConcurrentRate();
    }

    @Nullable
    public final String component10() {
        return this.getHeader();
    }

    @Nullable
    public final String component11() {
        return this.getLoginUrl();
    }

    @Nullable
    public final String component12() {
        return this.getLoginUi();
    }

    @Nullable
    public final String component13() {
        return this.loginCheckJs;
    }

    @Nullable
    public final String component14() {
        return this.sortUrl;
    }

    public final boolean component15() {
        return this.singleUrl;
    }

    public final int component16() {
        return this.articleStyle;
    }

    @Nullable
    public final String component17() {
        return this.ruleArticles;
    }

    @Nullable
    public final String component18() {
        return this.ruleNextPage;
    }

    @Nullable
    public final String component19() {
        return this.ruleTitle;
    }

    @Nullable
    public final String component20() {
        return this.rulePubDate;
    }

    @Nullable
    public final String component21() {
        return this.ruleDescription;
    }

    @Nullable
    public final String component22() {
        return this.ruleImage;
    }

    @Nullable
    public final String component23() {
        return this.ruleLink;
    }

    @Nullable
    public final String component24() {
        return this.ruleContent;
    }

    @Nullable
    public final String component25() {
        return this.style;
    }

    public final boolean component26() {
        return this.enableJs;
    }

    public final boolean component27() {
        return this.loadWithBaseUrl;
    }

    public final int component28() {
        return this.customOrder;
    }

    @NotNull
    public final RssSource copy(@NotNull String sourceUrl, @NotNull String sourceName, @NotNull String sourceIcon, @Nullable String sourceGroup, @Nullable String sourceComment, boolean enabled, @Nullable String variableComment, @Nullable Boolean enabledCookieJar, @Nullable String concurrentRate, @Nullable String header, @Nullable String loginUrl, @Nullable String loginUi, @Nullable String loginCheckJs, @Nullable String sortUrl, boolean singleUrl, int articleStyle, @Nullable String ruleArticles, @Nullable String ruleNextPage, @Nullable String ruleTitle, @Nullable String rulePubDate, @Nullable String ruleDescription, @Nullable String ruleImage, @Nullable String ruleLink, @Nullable String ruleContent, @Nullable String style, boolean enableJs, boolean loadWithBaseUrl, int customOrder) {
        Intrinsics.checkNotNullParameter(sourceUrl, "sourceUrl");
        Intrinsics.checkNotNullParameter(sourceName, "sourceName");
        Intrinsics.checkNotNullParameter(sourceIcon, "sourceIcon");
        return new RssSource(sourceUrl, sourceName, sourceIcon, sourceGroup, sourceComment, enabled, variableComment, enabledCookieJar, concurrentRate, header, loginUrl, loginUi, loginCheckJs, sortUrl, singleUrl, articleStyle, ruleArticles, ruleNextPage, ruleTitle, rulePubDate, ruleDescription, ruleImage, ruleLink, ruleContent, style, enableJs, loadWithBaseUrl, customOrder);
    }

    public static /* synthetic */ RssSource copy$default(RssSource rssSource, String string, String string2, String string3, String string4, String string5, boolean bl, String string6, Boolean bl2, String string7, String string8, String string9, String string10, String string11, String string12, boolean bl3, int n, String string13, String string14, String string15, String string16, String string17, String string18, String string19, String string20, String string21, boolean bl4, boolean bl5, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            string = rssSource.sourceUrl;
        }
        if ((n3 & 2) != 0) {
            string2 = rssSource.sourceName;
        }
        if ((n3 & 4) != 0) {
            string3 = rssSource.sourceIcon;
        }
        if ((n3 & 8) != 0) {
            string4 = rssSource.sourceGroup;
        }
        if ((n3 & 0x10) != 0) {
            string5 = rssSource.sourceComment;
        }
        if ((n3 & 0x20) != 0) {
            bl = rssSource.enabled;
        }
        if ((n3 & 0x40) != 0) {
            string6 = rssSource.variableComment;
        }
        if ((n3 & 0x80) != 0) {
            bl2 = rssSource.getEnabledCookieJar();
        }
        if ((n3 & 0x100) != 0) {
            string7 = rssSource.getConcurrentRate();
        }
        if ((n3 & 0x200) != 0) {
            string8 = rssSource.getHeader();
        }
        if ((n3 & 0x400) != 0) {
            string9 = rssSource.getLoginUrl();
        }
        if ((n3 & 0x800) != 0) {
            string10 = rssSource.getLoginUi();
        }
        if ((n3 & 0x1000) != 0) {
            string11 = rssSource.loginCheckJs;
        }
        if ((n3 & 0x2000) != 0) {
            string12 = rssSource.sortUrl;
        }
        if ((n3 & 0x4000) != 0) {
            bl3 = rssSource.singleUrl;
        }
        if ((n3 & 0x8000) != 0) {
            n = rssSource.articleStyle;
        }
        if ((n3 & 0x10000) != 0) {
            string13 = rssSource.ruleArticles;
        }
        if ((n3 & 0x20000) != 0) {
            string14 = rssSource.ruleNextPage;
        }
        if ((n3 & 0x40000) != 0) {
            string15 = rssSource.ruleTitle;
        }
        if ((n3 & 0x80000) != 0) {
            string16 = rssSource.rulePubDate;
        }
        if ((n3 & 0x100000) != 0) {
            string17 = rssSource.ruleDescription;
        }
        if ((n3 & 0x200000) != 0) {
            string18 = rssSource.ruleImage;
        }
        if ((n3 & 0x400000) != 0) {
            string19 = rssSource.ruleLink;
        }
        if ((n3 & 0x800000) != 0) {
            string20 = rssSource.ruleContent;
        }
        if ((n3 & 0x1000000) != 0) {
            string21 = rssSource.style;
        }
        if ((n3 & 0x2000000) != 0) {
            bl4 = rssSource.enableJs;
        }
        if ((n3 & 0x4000000) != 0) {
            bl5 = rssSource.loadWithBaseUrl;
        }
        if ((n3 & 0x8000000) != 0) {
            n2 = rssSource.customOrder;
        }
        return rssSource.copy(string, string2, string3, string4, string5, bl, string6, bl2, string7, string8, string9, string10, string11, string12, bl3, n, string13, string14, string15, string16, string17, string18, string19, string20, string21, bl4, bl5, n2);
    }

    @NotNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RssSource(sourceUrl=").append(this.sourceUrl).append(", sourceName=").append(this.sourceName).append(", sourceIcon=").append(this.sourceIcon).append(", sourceGroup=").append((Object)this.sourceGroup).append(", sourceComment=").append((Object)this.sourceComment).append(", enabled=").append(this.enabled).append(", variableComment=").append((Object)this.variableComment).append(", enabledCookieJar=").append(this.getEnabledCookieJar()).append(", concurrentRate=").append((Object)this.getConcurrentRate()).append(", header=").append((Object)this.getHeader()).append(", loginUrl=").append((Object)this.getLoginUrl()).append(", loginUi=");
        stringBuilder.append((Object)this.getLoginUi()).append(", loginCheckJs=").append((Object)this.loginCheckJs).append(", sortUrl=").append((Object)this.sortUrl).append(", singleUrl=").append(this.singleUrl).append(", articleStyle=").append(this.articleStyle).append(", ruleArticles=").append((Object)this.ruleArticles).append(", ruleNextPage=").append((Object)this.ruleNextPage).append(", ruleTitle=").append((Object)this.ruleTitle).append(", rulePubDate=").append((Object)this.rulePubDate).append(", ruleDescription=").append((Object)this.ruleDescription).append(", ruleImage=").append((Object)this.ruleImage).append(", ruleLink=").append((Object)this.ruleLink);
        stringBuilder.append(", ruleContent=").append((Object)this.ruleContent).append(", style=").append((Object)this.style).append(", enableJs=").append(this.enableJs).append(", loadWithBaseUrl=").append(this.loadWithBaseUrl).append(", customOrder=").append(this.customOrder).append(')');
        return stringBuilder.toString();
    }

    public RssSource() {
        this(null, null, null, null, null, false, null, null, null, null, null, null, null, null, false, 0, null, null, null, null, null, null, null, null, null, false, false, 0, 0xFFFFFFF, null);
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\b\u0010\tJ4\u0010\n\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00050\u000bj\b\u0012\u0004\u0012\u00020\u0005`\f0\u00042\u0006\u0010\r\u001a\u00020\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u000e\u0010\tJ$\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0010\u001a\u00020\u0011\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0014"}, d2={"Lio/legado/app/data/entities/RssSource$Companion;", "", "()V", "fromJson", "Lkotlin/Result;", "Lio/legado/app/data/entities/RssSource;", "json", "", "fromJson-IoAF18A", "(Ljava/lang/String;)Ljava/lang/Object;", "fromJsonArray", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "jsonArray", "fromJsonArray-IoAF18A", "fromJsonDoc", "doc", "Lcom/jayway/jsonpath/DocumentContext;", "fromJsonDoc-IoAF18A", "(Lcom/jayway/jsonpath/DocumentContext;)Ljava/lang/Object;", "reader-pro"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Object fromJsonDoc-IoAF18A(@NotNull DocumentContext doc) {
            Object object;
            Intrinsics.checkNotNullParameter(doc, "doc");
            boolean bl = false;
            try {
                object = Result.Companion;
                boolean bl2 = false;
                String string = JsonExtensionsKt.readString(doc, "$.sourceUrl");
                Intrinsics.checkNotNull(string);
                String string2 = string;
                String string3 = JsonExtensionsKt.readString(doc, "$.sourceName");
                Intrinsics.checkNotNull(string3);
                String string4 = string3;
                String string5 = JsonExtensionsKt.readString(doc, "$.sourceIcon");
                String string6 = string5 == null ? "" : string5;
                string5 = JsonExtensionsKt.readString(doc, "$.sourceGroup");
                String string7 = JsonExtensionsKt.readString(doc, "$.sourceComment");
                Object object2 = JsonExtensionsKt.readBool(doc, "$.enabled");
                boolean bl3 = object2 == null ? true : (Boolean)object2;
                object2 = JsonExtensionsKt.readString(doc, "$.concurrentRate");
                String string8 = JsonExtensionsKt.readString(doc, "$.header");
                String string9 = JsonExtensionsKt.readString(doc, "$.loginUrl");
                String string10 = JsonExtensionsKt.readString(doc, "$.loginCheckJs");
                String string11 = JsonExtensionsKt.readString(doc, "$.sortUrl");
                Boolean bl4 = JsonExtensionsKt.readBool(doc, "$.singleUrl");
                boolean bl5 = bl4 == null ? false : bl4;
                Object object3 = JsonExtensionsKt.readInt(doc, "$.articleStyle");
                int n = object3 == null ? 0 : (Integer)object3;
                object3 = JsonExtensionsKt.readString(doc, "$.ruleArticles");
                String string12 = JsonExtensionsKt.readString(doc, "$.ruleNextPage");
                String string13 = JsonExtensionsKt.readString(doc, "$.ruleTitle");
                String string14 = JsonExtensionsKt.readString(doc, "$.rulePubDate");
                String string15 = JsonExtensionsKt.readString(doc, "$.ruleDescription");
                String string16 = JsonExtensionsKt.readString(doc, "$.ruleImage");
                String string17 = JsonExtensionsKt.readString(doc, "$.ruleLink");
                String string18 = JsonExtensionsKt.readString(doc, "$.ruleContent");
                String string19 = JsonExtensionsKt.readString(doc, "$.style");
                Boolean bl6 = JsonExtensionsKt.readBool(doc, "$.enableJs");
                boolean bl7 = bl6 == null ? true : bl6;
                Boolean bl8 = JsonExtensionsKt.readBool(doc, "$.loadWithBaseUrl");
                boolean bl9 = bl8 == null ? true : bl8;
                Boolean bl10 = JsonExtensionsKt.readBool(doc, "$.enabledCookieJar");
                boolean bl11 = bl10 == null ? false : bl10;
                Integer n2 = JsonExtensionsKt.readInt(doc, "$.customOrder");
                int n3 = n2 == null ? 0 : n2;
                RssSource rssSource = new RssSource(string2, string4, string6, string5, string7, bl3, null, bl11, (String)object2, string8, string9, null, string10, string11, bl5, n, (String)object3, string12, string13, string14, string15, string16, string17, string18, string19, bl7, bl9, n3, 2112, null);
                boolean bl12 = false;
                object = Result.constructor-impl(rssSource);
            }
            catch (Throwable throwable) {
                Result.Companion companion = Result.Companion;
                boolean bl13 = false;
                object = Result.constructor-impl(ResultKt.createFailure(throwable));
            }
            return object;
        }

        @NotNull
        public final Object fromJson-IoAF18A(@NotNull String json) {
            Intrinsics.checkNotNullParameter(json, "json");
            DocumentContext documentContext = JsonExtensionsKt.getJsonPath().parse(json);
            Intrinsics.checkNotNullExpressionValue(documentContext, "jsonPath.parse(json)");
            return this.fromJsonDoc-IoAF18A(documentContext);
        }

        @NotNull
        public final Object fromJsonArray-IoAF18A(@NotNull String jsonArray) {
            Object object;
            Intrinsics.checkNotNullParameter(jsonArray, "jsonArray");
            boolean bl = false;
            try {
                object = Result.Companion;
                boolean bl2 = false;
                boolean bl3 = false;
                ArrayList<RssSource> sources = new ArrayList<RssSource>();
                List doc = (List)JsonExtensionsKt.getJsonPath().parse(jsonArray).read("$", new Predicate[0]);
                Intrinsics.checkNotNullExpressionValue(doc, "doc");
                Iterable $this$forEach$iv = doc;
                boolean $i$f$forEach = false;
                Iterator iterator2 = $this$forEach$iv.iterator();
                while (iterator2.hasNext()) {
                    Object element$iv;
                    Object it = element$iv = iterator2.next();
                    boolean bl4 = false;
                    DocumentContext jsonItem = JsonExtensionsKt.getJsonPath().parse(it);
                    Intrinsics.checkNotNullExpressionValue(jsonItem, "jsonItem");
                    Object object2 = Companion.fromJsonDoc-IoAF18A(jsonItem);
                    boolean bl5 = false;
                    ResultKt.throwOnFailure(object2);
                    bl5 = false;
                    boolean bl6 = false;
                    RssSource source = (RssSource)object2;
                    boolean bl7 = false;
                    sources.add(source);
                }
                ArrayList<RssSource> arrayList = sources;
                boolean bl8 = false;
                object = Result.constructor-impl(arrayList);
            }
            catch (Throwable throwable) {
                Result.Companion companion = Result.Companion;
                boolean bl9 = false;
                object = Result.constructor-impl(ResultKt.createFailure(throwable));
            }
            return object;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

