/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.script.SimpleBindings;
import io.legado.app.data.entities.BaseSource;
import io.legado.app.data.entities.rule.BookInfoRule;
import io.legado.app.data.entities.rule.ContentRule;
import io.legado.app.data.entities.rule.ExploreRule;
import io.legado.app.data.entities.rule.SearchRule;
import io.legado.app.data.entities.rule.TocRule;
import io.legado.app.help.SourceAnalyzer;
import io.legado.app.help.http.StrResponse;
import io.legado.app.model.DebugLog;
import io.legado.app.model.analyzeRule.QueryTTF;
import io.legado.app.utils.GsonExtensionsKt;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Connection;

@JsonIgnoreProperties(value={"headerMap", "source", "_userNameSpace", "userNameSpace", "loginHeader", "loginHeaderMap", "loginInfo", "loginInfoMap"})
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\bZ\n\u0002\u0010\u0000\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\b\b\u0087\b\u0018\u0000 \u00aa\u00012\u00020\u0001:\u0006\u00aa\u0001\u00ab\u0001\u00ac\u0001B\u00ab\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0007\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0016\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0016\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0007\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u001b\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001e\u0012\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 \u0012\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\"\u0012\n\b\u0002\u0010#\u001a\u0004\u0018\u00010$\u00a2\u0006\u0002\u0010%J\t\u0010w\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010x\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010y\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010z\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010{\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010|\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010}\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010~\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\u007f\u001a\u00020\u0016H\u00c6\u0003J\n\u0010\u0080\u0001\u001a\u00020\u0016H\u00c6\u0003J\n\u0010\u0081\u0001\u001a\u00020\u0007H\u00c6\u0003J\n\u0010\u0082\u0001\u001a\u00020\u0003H\u00c6\u0003J\f\u0010\u0083\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\f\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u001bH\u00c6\u0003J\f\u0010\u0085\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\f\u0010\u0086\u0001\u001a\u0004\u0018\u00010\u001eH\u00c6\u0003J\f\u0010\u0087\u0001\u001a\u0004\u0018\u00010 H\u00c6\u0003J\f\u0010\u0088\u0001\u001a\u0004\u0018\u00010\"H\u00c6\u0003J\f\u0010\u0089\u0001\u001a\u0004\u0018\u00010$H\u00c6\u0003J\f\u0010\u008a\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\n\u0010\u008b\u0001\u001a\u00020\u0007H\u00c6\u0003J\f\u0010\u008c\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\n\u0010\u008d\u0001\u001a\u00020\u0007H\u00c6\u0003J\n\u0010\u008e\u0001\u001a\u00020\u000bH\u00c6\u0003J\n\u0010\u008f\u0001\u001a\u00020\u000bH\u00c6\u0003J\u0011\u0010\u0090\u0001\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003\u00a2\u0006\u0002\u0010DJ\u00b6\u0002\u0010\u0091\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u00162\b\b\u0002\u0010\u0018\u001a\u00020\u00072\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\"2\n\b\u0002\u0010#\u001a\u0004\u0018\u00010$H\u00c6\u0001\u00a2\u0006\u0003\u0010\u0092\u0001J\u0010\u0010\u0093\u0001\u001a\u00020\u000b2\u0007\u0010\u0094\u0001\u001a\u00020\u0000J\u001f\u0010\u0093\u0001\u001a\u00020\u000b2\t\u0010\u0095\u0001\u001a\u0004\u0018\u00010\u00032\t\u0010\u0096\u0001\u001a\u0004\u0018\u00010\u0003H\u0002J\u0016\u0010\u0097\u0001\u001a\u00020\u000b2\n\u0010\u0098\u0001\u001a\u0005\u0018\u00010\u0099\u0001H\u0096\u0002J\u0007\u0010\u009a\u0001\u001a\u00020 J\u0007\u0010\u009b\u0001\u001a\u00020$J\u0007\u0010\u009c\u0001\u001a\u00020\u001bJ\t\u0010\u009d\u0001\u001a\u00020\u0003H\u0016J\u000b\u0010\u009e\u0001\u001a\u0004\u0018\u00010>H\u0016J\u0007\u0010\u009f\u0001\u001a\u00020\u001eJ\t\u0010\u00a0\u0001\u001a\u00020\u0003H\u0016J\u0007\u0010\u00a1\u0001\u001a\u00020\"J\t\u0010\u00a2\u0001\u001a\u00020\u0003H\u0016J\t\u0010\u00a3\u0001\u001a\u00020\u0007H\u0016J\u0013\u0010\u00a4\u0001\u001a\u00030\u00a5\u00012\t\u0010\u00a6\u0001\u001a\u0004\u0018\u00010>J\u0011\u0010\u00a7\u0001\u001a\u00030\u00a5\u00012\u0007\u0010\u00a8\u0001\u001a\u00020\u0003J\n\u0010\u00a9\u0001\u001a\u00020\u0003H\u00d6\u0001R\u000e\u0010&\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010'\u001a\u0004\u0018\u00010 X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010)\"\u0004\b-\u0010+R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b.\u0010)\"\u0004\b/\u0010+R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b4\u0010)\"\u0004\b5\u0010+R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b6\u0010)\"\u0004\b7\u0010+R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b8\u0010)\"\u0004\b9\u0010+R\u0010\u0010:\u001a\u0004\u0018\u00010$X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b;\u00101\"\u0004\b<\u00103R\u0010\u0010=\u001a\u0004\u0018\u00010>X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b?\u0010@\"\u0004\bA\u0010BR\u001e\u0010\r\u001a\u0004\u0018\u00010\u000bX\u0096\u000e\u00a2\u0006\u0010\n\u0002\u0010G\u001a\u0004\bC\u0010D\"\u0004\bE\u0010FR\u001a\u0010\f\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bH\u0010@\"\u0004\bI\u0010BR\u0010\u0010J\u001a\u0004\u0018\u00010\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bK\u0010)\"\u0004\bL\u0010+R\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bM\u0010)\"\u0004\bN\u0010+R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bO\u0010P\"\u0004\bQ\u0010RR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bS\u0010)\"\u0004\bT\u0010+R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bU\u0010)\"\u0004\bV\u0010+R\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bW\u0010)\"\u0004\bX\u0010+R\u001a\u0010\u0017\u001a\u00020\u0016X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bY\u0010P\"\u0004\bZ\u0010RR\u001c\u0010\u001f\u001a\u0004\u0018\u00010 X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b[\u0010\\\"\u0004\b]\u0010^R\u001c\u0010#\u001a\u0004\u0018\u00010$X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b_\u0010`\"\u0004\ba\u0010bR\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bc\u0010d\"\u0004\be\u0010fR\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bg\u0010h\"\u0004\bi\u0010jR\u001c\u0010!\u001a\u0004\u0018\u00010\"X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bk\u0010l\"\u0004\bm\u0010nR\u0010\u0010o\u001a\u0004\u0018\u00010\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u001c\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bp\u0010)\"\u0004\bq\u0010+R\u0010\u0010r\u001a\u0004\u0018\u00010\"X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bs\u0010)\"\u0004\bt\u0010+R\u001a\u0010\u0018\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bu\u00101\"\u0004\bv\u00103\u00a8\u0006\u00ad\u0001"}, d2={"Lio/legado/app/data/entities/BookSource;", "Lio/legado/app/data/entities/BaseSource;", "bookSourceUrl", "", "bookSourceName", "bookSourceGroup", "bookSourceType", "", "bookUrlPattern", "customOrder", "enabled", "", "enabledExplore", "enabledCookieJar", "concurrentRate", "header", "loginUrl", "loginUi", "loginCheckJs", "bookSourceComment", "variableComment", "lastUpdateTime", "", "respondTime", "weight", "exploreUrl", "ruleExplore", "Lio/legado/app/data/entities/rule/ExploreRule;", "searchUrl", "ruleSearch", "Lio/legado/app/data/entities/rule/SearchRule;", "ruleBookInfo", "Lio/legado/app/data/entities/rule/BookInfoRule;", "ruleToc", "Lio/legado/app/data/entities/rule/TocRule;", "ruleContent", "Lio/legado/app/data/entities/rule/ContentRule;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;IZZLjava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJILjava/lang/String;Lio/legado/app/data/entities/rule/ExploreRule;Ljava/lang/String;Lio/legado/app/data/entities/rule/SearchRule;Lio/legado/app/data/entities/rule/BookInfoRule;Lio/legado/app/data/entities/rule/TocRule;Lio/legado/app/data/entities/rule/ContentRule;)V", "_userNameSpace", "bookInfoRuleV", "getBookSourceComment", "()Ljava/lang/String;", "setBookSourceComment", "(Ljava/lang/String;)V", "getBookSourceGroup", "setBookSourceGroup", "getBookSourceName", "setBookSourceName", "getBookSourceType", "()I", "setBookSourceType", "(I)V", "getBookSourceUrl", "setBookSourceUrl", "getBookUrlPattern", "setBookUrlPattern", "getConcurrentRate", "setConcurrentRate", "contentRuleV", "getCustomOrder", "setCustomOrder", "debugLog", "Lio/legado/app/model/DebugLog;", "getEnabled", "()Z", "setEnabled", "(Z)V", "getEnabledCookieJar", "()Ljava/lang/Boolean;", "setEnabledCookieJar", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "getEnabledExplore", "setEnabledExplore", "exploreRuleV", "getExploreUrl", "setExploreUrl", "getHeader", "setHeader", "getLastUpdateTime", "()J", "setLastUpdateTime", "(J)V", "getLoginCheckJs", "setLoginCheckJs", "getLoginUi", "setLoginUi", "getLoginUrl", "setLoginUrl", "getRespondTime", "setRespondTime", "getRuleBookInfo", "()Lio/legado/app/data/entities/rule/BookInfoRule;", "setRuleBookInfo", "(Lio/legado/app/data/entities/rule/BookInfoRule;)V", "getRuleContent", "()Lio/legado/app/data/entities/rule/ContentRule;", "setRuleContent", "(Lio/legado/app/data/entities/rule/ContentRule;)V", "getRuleExplore", "()Lio/legado/app/data/entities/rule/ExploreRule;", "setRuleExplore", "(Lio/legado/app/data/entities/rule/ExploreRule;)V", "getRuleSearch", "()Lio/legado/app/data/entities/rule/SearchRule;", "setRuleSearch", "(Lio/legado/app/data/entities/rule/SearchRule;)V", "getRuleToc", "()Lio/legado/app/data/entities/rule/TocRule;", "setRuleToc", "(Lio/legado/app/data/entities/rule/TocRule;)V", "searchRuleV", "getSearchUrl", "setSearchUrl", "tocRuleV", "getVariableComment", "setVariableComment", "getWeight", "setWeight", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;IZZLjava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJILjava/lang/String;Lio/legado/app/data/entities/rule/ExploreRule;Ljava/lang/String;Lio/legado/app/data/entities/rule/SearchRule;Lio/legado/app/data/entities/rule/BookInfoRule;Lio/legado/app/data/entities/rule/TocRule;Lio/legado/app/data/entities/rule/ContentRule;)Lio/legado/app/data/entities/BookSource;", "equal", "source", "a", "b", "equals", "other", "", "getBookInfoRule", "getContentRule", "getExploreRule", "getKey", "getLogger", "getSearchRule", "getTag", "getTocRule", "getUserNameSpace", "hashCode", "setLogger", "", "logger", "setUserNameSpace", "nameSpace", "toString", "Companion", "Converters", "ExploreKind", "reader-pro"})
public final class BookSource
implements BaseSource {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private String bookSourceUrl;
    @NotNull
    private String bookSourceName;
    @Nullable
    private String bookSourceGroup;
    private int bookSourceType;
    @Nullable
    private String bookUrlPattern;
    private int customOrder;
    private boolean enabled;
    private boolean enabledExplore;
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
    private String bookSourceComment;
    @Nullable
    private String variableComment;
    private long lastUpdateTime;
    private long respondTime;
    private int weight;
    @Nullable
    private String exploreUrl;
    @Nullable
    private ExploreRule ruleExplore;
    @Nullable
    private String searchUrl;
    @Nullable
    private SearchRule ruleSearch;
    @Nullable
    private BookInfoRule ruleBookInfo;
    @Nullable
    private TocRule ruleToc;
    @Nullable
    private ContentRule ruleContent;
    @Nullable
    private SearchRule searchRuleV;
    @Nullable
    private ExploreRule exploreRuleV;
    @Nullable
    private BookInfoRule bookInfoRuleV;
    @Nullable
    private TocRule tocRuleV;
    @Nullable
    private ContentRule contentRuleV;
    @NotNull
    private transient String _userNameSpace;
    @Nullable
    private transient DebugLog debugLog;

    public BookSource(@NotNull String bookSourceUrl, @NotNull String bookSourceName, @Nullable String bookSourceGroup, int bookSourceType, @Nullable String bookUrlPattern, int customOrder, boolean enabled, boolean enabledExplore, @Nullable Boolean enabledCookieJar, @Nullable String concurrentRate, @Nullable String header, @Nullable String loginUrl, @Nullable String loginUi, @Nullable String loginCheckJs, @Nullable String bookSourceComment, @Nullable String variableComment, long lastUpdateTime, long respondTime, int weight, @Nullable String exploreUrl, @Nullable ExploreRule ruleExplore, @Nullable String searchUrl, @Nullable SearchRule ruleSearch, @Nullable BookInfoRule ruleBookInfo, @Nullable TocRule ruleToc, @Nullable ContentRule ruleContent) {
        Intrinsics.checkNotNullParameter(bookSourceUrl, "bookSourceUrl");
        Intrinsics.checkNotNullParameter(bookSourceName, "bookSourceName");
        this.bookSourceUrl = bookSourceUrl;
        this.bookSourceName = bookSourceName;
        this.bookSourceGroup = bookSourceGroup;
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
        this.variableComment = variableComment;
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
        this._userNameSpace = "";
    }

    public /* synthetic */ BookSource(String string, String string2, String string3, int n, String string4, int n2, boolean bl, boolean bl2, Boolean bl3, String string5, String string6, String string7, String string8, String string9, String string10, String string11, long l, long l2, int n3, String string12, ExploreRule exploreRule, String string13, SearchRule searchRule, BookInfoRule bookInfoRule, TocRule tocRule, ContentRule contentRule, int n4, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n4 & 1) != 0) {
            string = "";
        }
        if ((n4 & 2) != 0) {
            string2 = "";
        }
        if ((n4 & 4) != 0) {
            string3 = null;
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
            string7 = null;
        }
        if ((n4 & 0x1000) != 0) {
            string8 = null;
        }
        if ((n4 & 0x2000) != 0) {
            string9 = null;
        }
        if ((n4 & 0x4000) != 0) {
            string10 = null;
        }
        if ((n4 & 0x8000) != 0) {
            string11 = null;
        }
        if ((n4 & 0x10000) != 0) {
            l = 0L;
        }
        if ((n4 & 0x20000) != 0) {
            l2 = 180000L;
        }
        if ((n4 & 0x40000) != 0) {
            n3 = 0;
        }
        if ((n4 & 0x80000) != 0) {
            string12 = null;
        }
        if ((n4 & 0x100000) != 0) {
            exploreRule = null;
        }
        if ((n4 & 0x200000) != 0) {
            string13 = null;
        }
        if ((n4 & 0x400000) != 0) {
            searchRule = null;
        }
        if ((n4 & 0x800000) != 0) {
            bookInfoRule = null;
        }
        if ((n4 & 0x1000000) != 0) {
            tocRule = null;
        }
        if ((n4 & 0x2000000) != 0) {
            contentRule = null;
        }
        this(string, string2, string3, n, string4, n2, bl, bl2, bl3, string5, string6, string7, string8, string9, string10, string11, l, l2, n3, string12, exploreRule, string13, searchRule, bookInfoRule, tocRule, contentRule);
    }

    @NotNull
    public final String getBookSourceUrl() {
        return this.bookSourceUrl;
    }

    public final void setBookSourceUrl(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.bookSourceUrl = string;
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
    public final String getBookSourceComment() {
        return this.bookSourceComment;
    }

    public final void setBookSourceComment(@Nullable String string) {
        this.bookSourceComment = string;
    }

    @Nullable
    public final String getVariableComment() {
        return this.variableComment;
    }

    public final void setVariableComment(@Nullable String string) {
        this.variableComment = string;
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
    public final ExploreRule getRuleExplore() {
        return this.ruleExplore;
    }

    public final void setRuleExplore(@Nullable ExploreRule exploreRule) {
        this.ruleExplore = exploreRule;
    }

    @Nullable
    public final String getSearchUrl() {
        return this.searchUrl;
    }

    public final void setSearchUrl(@Nullable String string) {
        this.searchUrl = string;
    }

    @Nullable
    public final SearchRule getRuleSearch() {
        return this.ruleSearch;
    }

    public final void setRuleSearch(@Nullable SearchRule searchRule) {
        this.ruleSearch = searchRule;
    }

    @Nullable
    public final BookInfoRule getRuleBookInfo() {
        return this.ruleBookInfo;
    }

    public final void setRuleBookInfo(@Nullable BookInfoRule bookInfoRule) {
        this.ruleBookInfo = bookInfoRule;
    }

    @Nullable
    public final TocRule getRuleToc() {
        return this.ruleToc;
    }

    public final void setRuleToc(@Nullable TocRule tocRule) {
        this.ruleToc = tocRule;
    }

    @Nullable
    public final ContentRule getRuleContent() {
        return this.ruleContent;
    }

    public final void setRuleContent(@Nullable ContentRule contentRule) {
        this.ruleContent = contentRule;
    }

    @Override
    @NotNull
    public String getTag() {
        return this.bookSourceName;
    }

    @Override
    @NotNull
    public String getKey() {
        return this.bookSourceUrl;
    }

    public int hashCode() {
        return this.bookSourceUrl.hashCode();
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof BookSource ? Intrinsics.areEqual(((BookSource)other).bookSourceUrl, this.bookSourceUrl) : false;
    }

    @NotNull
    public final SearchRule getSearchRule() {
        SearchRule searchRule = this.ruleSearch;
        return searchRule == null ? new SearchRule(null, null, null, null, null, null, null, null, null, null, 1023, null) : searchRule;
    }

    @NotNull
    public final ExploreRule getExploreRule() {
        ExploreRule exploreRule = this.ruleExplore;
        return exploreRule == null ? new ExploreRule(null, null, null, null, null, null, null, null, null, null, 1023, null) : exploreRule;
    }

    @NotNull
    public final BookInfoRule getBookInfoRule() {
        BookInfoRule bookInfoRule = this.ruleBookInfo;
        return bookInfoRule == null ? new BookInfoRule(null, null, null, null, null, null, null, null, null, null, null, 2047, null) : bookInfoRule;
    }

    @NotNull
    public final TocRule getTocRule() {
        TocRule tocRule = this.ruleToc;
        return tocRule == null ? new TocRule(null, null, null, null, null, null, null, null, 255, null) : tocRule;
    }

    @NotNull
    public final ContentRule getContentRule() {
        ContentRule contentRule = this.ruleContent;
        return contentRule == null ? new ContentRule(null, null, null, null, null, null, 63, null) : contentRule;
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
        return this.debugLog;
    }

    public final boolean equal(@NotNull BookSource source) {
        Intrinsics.checkNotNullParameter(source, "source");
        return this.equal(this.bookSourceName, source.bookSourceName) && this.equal(this.bookSourceUrl, source.bookSourceUrl) && this.equal(this.bookSourceGroup, source.bookSourceGroup) && this.bookSourceType == source.bookSourceType && this.equal(this.bookUrlPattern, source.bookUrlPattern) && this.enabled == source.enabled && this.enabledExplore == source.enabledExplore && Intrinsics.areEqual(this.getEnabledCookieJar(), source.getEnabledCookieJar()) && this.equal(this.getHeader(), source.getHeader()) && this.equal(this.getLoginUrl(), source.getLoginUrl()) && this.equal(this.exploreUrl, source.exploreUrl) && this.equal(this.searchUrl, source.searchUrl) && Intrinsics.areEqual(this.getSearchRule(), source.getSearchRule()) && Intrinsics.areEqual(this.getExploreRule(), source.getExploreRule()) && Intrinsics.areEqual(this.getBookInfoRule(), source.getBookInfoRule()) && Intrinsics.areEqual(this.getTocRule(), source.getTocRule()) && Intrinsics.areEqual(this.getContentRule(), source.getContentRule());
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
        return this.bookSourceUrl;
    }

    @NotNull
    public final String component2() {
        return this.bookSourceName;
    }

    @Nullable
    public final String component3() {
        return this.bookSourceGroup;
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

    @Nullable
    public final Boolean component9() {
        return this.getEnabledCookieJar();
    }

    @Nullable
    public final String component10() {
        return this.getConcurrentRate();
    }

    @Nullable
    public final String component11() {
        return this.getHeader();
    }

    @Nullable
    public final String component12() {
        return this.getLoginUrl();
    }

    @Nullable
    public final String component13() {
        return this.getLoginUi();
    }

    @Nullable
    public final String component14() {
        return this.loginCheckJs;
    }

    @Nullable
    public final String component15() {
        return this.bookSourceComment;
    }

    @Nullable
    public final String component16() {
        return this.variableComment;
    }

    public final long component17() {
        return this.lastUpdateTime;
    }

    public final long component18() {
        return this.respondTime;
    }

    public final int component19() {
        return this.weight;
    }

    @Nullable
    public final String component20() {
        return this.exploreUrl;
    }

    @Nullable
    public final ExploreRule component21() {
        return this.ruleExplore;
    }

    @Nullable
    public final String component22() {
        return this.searchUrl;
    }

    @Nullable
    public final SearchRule component23() {
        return this.ruleSearch;
    }

    @Nullable
    public final BookInfoRule component24() {
        return this.ruleBookInfo;
    }

    @Nullable
    public final TocRule component25() {
        return this.ruleToc;
    }

    @Nullable
    public final ContentRule component26() {
        return this.ruleContent;
    }

    @NotNull
    public final BookSource copy(@NotNull String bookSourceUrl, @NotNull String bookSourceName, @Nullable String bookSourceGroup, int bookSourceType, @Nullable String bookUrlPattern, int customOrder, boolean enabled, boolean enabledExplore, @Nullable Boolean enabledCookieJar, @Nullable String concurrentRate, @Nullable String header, @Nullable String loginUrl, @Nullable String loginUi, @Nullable String loginCheckJs, @Nullable String bookSourceComment, @Nullable String variableComment, long lastUpdateTime, long respondTime, int weight, @Nullable String exploreUrl, @Nullable ExploreRule ruleExplore, @Nullable String searchUrl, @Nullable SearchRule ruleSearch, @Nullable BookInfoRule ruleBookInfo, @Nullable TocRule ruleToc, @Nullable ContentRule ruleContent) {
        Intrinsics.checkNotNullParameter(bookSourceUrl, "bookSourceUrl");
        Intrinsics.checkNotNullParameter(bookSourceName, "bookSourceName");
        return new BookSource(bookSourceUrl, bookSourceName, bookSourceGroup, bookSourceType, bookUrlPattern, customOrder, enabled, enabledExplore, enabledCookieJar, concurrentRate, header, loginUrl, loginUi, loginCheckJs, bookSourceComment, variableComment, lastUpdateTime, respondTime, weight, exploreUrl, ruleExplore, searchUrl, ruleSearch, ruleBookInfo, ruleToc, ruleContent);
    }

    public static /* synthetic */ BookSource copy$default(BookSource bookSource, String string, String string2, String string3, int n, String string4, int n2, boolean bl, boolean bl2, Boolean bl3, String string5, String string6, String string7, String string8, String string9, String string10, String string11, long l, long l2, int n3, String string12, ExploreRule exploreRule, String string13, SearchRule searchRule, BookInfoRule bookInfoRule, TocRule tocRule, ContentRule contentRule, int n4, Object object) {
        if ((n4 & 1) != 0) {
            string = bookSource.bookSourceUrl;
        }
        if ((n4 & 2) != 0) {
            string2 = bookSource.bookSourceName;
        }
        if ((n4 & 4) != 0) {
            string3 = bookSource.bookSourceGroup;
        }
        if ((n4 & 8) != 0) {
            n = bookSource.bookSourceType;
        }
        if ((n4 & 0x10) != 0) {
            string4 = bookSource.bookUrlPattern;
        }
        if ((n4 & 0x20) != 0) {
            n2 = bookSource.customOrder;
        }
        if ((n4 & 0x40) != 0) {
            bl = bookSource.enabled;
        }
        if ((n4 & 0x80) != 0) {
            bl2 = bookSource.enabledExplore;
        }
        if ((n4 & 0x100) != 0) {
            bl3 = bookSource.getEnabledCookieJar();
        }
        if ((n4 & 0x200) != 0) {
            string5 = bookSource.getConcurrentRate();
        }
        if ((n4 & 0x400) != 0) {
            string6 = bookSource.getHeader();
        }
        if ((n4 & 0x800) != 0) {
            string7 = bookSource.getLoginUrl();
        }
        if ((n4 & 0x1000) != 0) {
            string8 = bookSource.getLoginUi();
        }
        if ((n4 & 0x2000) != 0) {
            string9 = bookSource.loginCheckJs;
        }
        if ((n4 & 0x4000) != 0) {
            string10 = bookSource.bookSourceComment;
        }
        if ((n4 & 0x8000) != 0) {
            string11 = bookSource.variableComment;
        }
        if ((n4 & 0x10000) != 0) {
            l = bookSource.lastUpdateTime;
        }
        if ((n4 & 0x20000) != 0) {
            l2 = bookSource.respondTime;
        }
        if ((n4 & 0x40000) != 0) {
            n3 = bookSource.weight;
        }
        if ((n4 & 0x80000) != 0) {
            string12 = bookSource.exploreUrl;
        }
        if ((n4 & 0x100000) != 0) {
            exploreRule = bookSource.ruleExplore;
        }
        if ((n4 & 0x200000) != 0) {
            string13 = bookSource.searchUrl;
        }
        if ((n4 & 0x400000) != 0) {
            searchRule = bookSource.ruleSearch;
        }
        if ((n4 & 0x800000) != 0) {
            bookInfoRule = bookSource.ruleBookInfo;
        }
        if ((n4 & 0x1000000) != 0) {
            tocRule = bookSource.ruleToc;
        }
        if ((n4 & 0x2000000) != 0) {
            contentRule = bookSource.ruleContent;
        }
        return bookSource.copy(string, string2, string3, n, string4, n2, bl, bl2, bl3, string5, string6, string7, string8, string9, string10, string11, l, l2, n3, string12, exploreRule, string13, searchRule, bookInfoRule, tocRule, contentRule);
    }

    @NotNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BookSource(bookSourceUrl=").append(this.bookSourceUrl).append(", bookSourceName=").append(this.bookSourceName).append(", bookSourceGroup=").append((Object)this.bookSourceGroup).append(", bookSourceType=").append(this.bookSourceType).append(", bookUrlPattern=").append((Object)this.bookUrlPattern).append(", customOrder=").append(this.customOrder).append(", enabled=").append(this.enabled).append(", enabledExplore=").append(this.enabledExplore).append(", enabledCookieJar=").append(this.getEnabledCookieJar()).append(", concurrentRate=").append((Object)this.getConcurrentRate()).append(", header=").append((Object)this.getHeader()).append(", loginUrl=");
        stringBuilder.append((Object)this.getLoginUrl()).append(", loginUi=").append((Object)this.getLoginUi()).append(", loginCheckJs=").append((Object)this.loginCheckJs).append(", bookSourceComment=").append((Object)this.bookSourceComment).append(", variableComment=").append((Object)this.variableComment).append(", lastUpdateTime=").append(this.lastUpdateTime).append(", respondTime=").append(this.respondTime).append(", weight=").append(this.weight).append(", exploreUrl=").append((Object)this.exploreUrl).append(", ruleExplore=").append(this.ruleExplore).append(", searchUrl=").append((Object)this.searchUrl).append(", ruleSearch=").append(this.ruleSearch);
        stringBuilder.append(", ruleBookInfo=").append(this.ruleBookInfo).append(", ruleToc=").append(this.ruleToc).append(", ruleContent=").append(this.ruleContent).append(')');
        return stringBuilder.toString();
    }

    public BookSource() {
        this(null, null, null, 0, null, 0, false, false, null, null, null, null, null, null, null, null, 0L, 0L, 0, null, null, null, null, null, null, null, 0x3FFFFFF, null);
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u001f\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0003H\u00d6\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t\u00a8\u0006\u0015"}, d2={"Lio/legado/app/data/entities/BookSource$ExploreKind;", "", "title", "", "url", "(Ljava/lang/String;Ljava/lang/String;)V", "getTitle", "()Ljava/lang/String;", "setTitle", "(Ljava/lang/String;)V", "getUrl", "setUrl", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "reader-pro"})
    public static final class ExploreKind {
        @NotNull
        private String title;
        @Nullable
        private String url;

        public ExploreKind(@NotNull String title, @Nullable String url2) {
            Intrinsics.checkNotNullParameter(title, "title");
            this.title = title;
            this.url = url2;
        }

        public /* synthetic */ ExploreKind(String string, String string2, int n, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n & 2) != 0) {
                string2 = null;
            }
            this(string, string2);
        }

        @NotNull
        public final String getTitle() {
            return this.title;
        }

        public final void setTitle(@NotNull String string) {
            Intrinsics.checkNotNullParameter(string, "<set-?>");
            this.title = string;
        }

        @Nullable
        public final String getUrl() {
            return this.url;
        }

        public final void setUrl(@Nullable String string) {
            this.url = string;
        }

        @NotNull
        public final String component1() {
            return this.title;
        }

        @Nullable
        public final String component2() {
            return this.url;
        }

        @NotNull
        public final ExploreKind copy(@NotNull String title, @Nullable String url2) {
            Intrinsics.checkNotNullParameter(title, "title");
            return new ExploreKind(title, url2);
        }

        public static /* synthetic */ ExploreKind copy$default(ExploreKind exploreKind, String string, String string2, int n, Object object) {
            if ((n & 1) != 0) {
                string = exploreKind.title;
            }
            if ((n & 2) != 0) {
                string2 = exploreKind.url;
            }
            return exploreKind.copy(string, string2);
        }

        @NotNull
        public String toString() {
            return "ExploreKind(title=" + this.title + ", url=" + this.url + ')';
        }

        public int hashCode() {
            int result2 = this.title.hashCode();
            result2 = result2 * 31 + (this.url == null ? 0 : this.url.hashCode());
            return result2;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ExploreKind)) {
                return false;
            }
            ExploreKind exploreKind = (ExploreKind)other;
            if (!Intrinsics.areEqual(this.title, exploreKind.title)) {
                return false;
            }
            return Intrinsics.areEqual(this.url, exploreKind.url);
        }
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\b\u0010\tJ*\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000b0\u00042\u0006\u0010\f\u001a\u00020\r\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fJ*\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000b0\u00042\u0006\u0010\u0006\u001a\u00020\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u000e\u0010\t\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0010"}, d2={"Lio/legado/app/data/entities/BookSource$Companion;", "", "()V", "fromJson", "Lkotlin/Result;", "Lio/legado/app/data/entities/BookSource;", "json", "", "fromJson-IoAF18A", "(Ljava/lang/String;)Ljava/lang/Object;", "fromJsonArray", "", "inputStream", "Ljava/io/InputStream;", "fromJsonArray-IoAF18A", "(Ljava/io/InputStream;)Ljava/lang/Object;", "reader-pro"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Object fromJson-IoAF18A(@NotNull String json) {
            Intrinsics.checkNotNullParameter(json, "json");
            return SourceAnalyzer.INSTANCE.jsonToBookSource-IoAF18A(json);
        }

        @NotNull
        public final Object fromJsonArray-IoAF18A(@NotNull String json) {
            Intrinsics.checkNotNullParameter(json, "json");
            return SourceAnalyzer.INSTANCE.jsonToBookSources-IoAF18A(json);
        }

        @NotNull
        public final Object fromJsonArray-IoAF18A(@NotNull InputStream inputStream2) {
            Intrinsics.checkNotNullParameter(inputStream2, "inputStream");
            return SourceAnalyzer.INSTANCE.jsonToBookSources-IoAF18A(inputStream2);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006J\u0010\u0010\u0007\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\tJ\u0010\u0010\n\u001a\u00020\u00042\b\u0010\u000b\u001a\u0004\u0018\u00010\fJ\u0010\u0010\r\u001a\u00020\u00042\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fJ\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u00062\b\u0010\u0011\u001a\u0004\u0018\u00010\u0004J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\t2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0004J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0004J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0004J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\u0010\u0011\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0017\u001a\u00020\u00042\b\u0010\u0018\u001a\u0004\u0018\u00010\u0016\u00a8\u0006\u0019"}, d2={"Lio/legado/app/data/entities/BookSource$Converters;", "", "()V", "bookInfoRuleToString", "", "bookInfoRule", "Lio/legado/app/data/entities/rule/BookInfoRule;", "contentRuleToString", "contentRule", "Lio/legado/app/data/entities/rule/ContentRule;", "exploreRuleToString", "exploreRule", "Lio/legado/app/data/entities/rule/ExploreRule;", "searchRuleToString", "searchRule", "Lio/legado/app/data/entities/rule/SearchRule;", "stringToBookInfoRule", "json", "stringToContentRule", "stringToExploreRule", "stringToSearchRule", "stringToTocRule", "Lio/legado/app/data/entities/rule/TocRule;", "tocRuleToString", "tocRule", "reader-pro"})
    public static final class Converters {
        @NotNull
        public final String exploreRuleToString(@Nullable ExploreRule exploreRule) {
            String string = GsonExtensionsKt.getGSON().toJson(exploreRule);
            Intrinsics.checkNotNullExpressionValue(string, "GSON.toJson(exploreRule)");
            return string;
        }

        @Nullable
        public final ExploreRule stringToExploreRule(@Nullable String json) {
            Object object;
            Gson $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
            boolean $i$f$fromJsonObject = false;
            boolean bl = false;
            try {
                object = Result.Companion;
                boolean bl2 = false;
                boolean $i$f$genericType = false;
                Type type = new TypeToken<ExploreRule>(){}.getType();
                Intrinsics.checkNotNullExpressionValue(type, "object : TypeToken<T>() {}.type");
                Object t = $this$fromJsonObject$iv.fromJson(json, type);
                if (!(t instanceof ExploreRule)) {
                    t = null;
                }
                ExploreRule exploreRule = (ExploreRule)t;
                boolean bl3 = false;
                object = Result.constructor-impl(exploreRule);
            }
            catch (Throwable throwable) {
                Result.Companion companion = Result.Companion;
                boolean bl4 = false;
                object = Result.constructor-impl(ResultKt.createFailure(throwable));
            }
            Object object2 = object;
            boolean bl5 = false;
            return (ExploreRule)(Result.isFailure-impl(object2) ? null : object2);
        }

        @NotNull
        public final String searchRuleToString(@Nullable SearchRule searchRule) {
            String string = GsonExtensionsKt.getGSON().toJson(searchRule);
            Intrinsics.checkNotNullExpressionValue(string, "GSON.toJson(searchRule)");
            return string;
        }

        @Nullable
        public final SearchRule stringToSearchRule(@Nullable String json) {
            Object object;
            Gson $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
            boolean $i$f$fromJsonObject = false;
            boolean bl = false;
            try {
                object = Result.Companion;
                boolean bl2 = false;
                boolean $i$f$genericType = false;
                Type type = new TypeToken<SearchRule>(){}.getType();
                Intrinsics.checkNotNullExpressionValue(type, "object : TypeToken<T>() {}.type");
                Object t = $this$fromJsonObject$iv.fromJson(json, type);
                if (!(t instanceof SearchRule)) {
                    t = null;
                }
                SearchRule searchRule = (SearchRule)t;
                boolean bl3 = false;
                object = Result.constructor-impl(searchRule);
            }
            catch (Throwable throwable) {
                Result.Companion companion = Result.Companion;
                boolean bl4 = false;
                object = Result.constructor-impl(ResultKt.createFailure(throwable));
            }
            Object object2 = object;
            boolean bl5 = false;
            return (SearchRule)(Result.isFailure-impl(object2) ? null : object2);
        }

        @NotNull
        public final String bookInfoRuleToString(@Nullable BookInfoRule bookInfoRule) {
            String string = GsonExtensionsKt.getGSON().toJson(bookInfoRule);
            Intrinsics.checkNotNullExpressionValue(string, "GSON.toJson(bookInfoRule)");
            return string;
        }

        @Nullable
        public final BookInfoRule stringToBookInfoRule(@Nullable String json) {
            Object object;
            Gson $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
            boolean $i$f$fromJsonObject = false;
            boolean bl = false;
            try {
                object = Result.Companion;
                boolean bl2 = false;
                boolean $i$f$genericType = false;
                Type type = new TypeToken<BookInfoRule>(){}.getType();
                Intrinsics.checkNotNullExpressionValue(type, "object : TypeToken<T>() {}.type");
                Object t = $this$fromJsonObject$iv.fromJson(json, type);
                if (!(t instanceof BookInfoRule)) {
                    t = null;
                }
                BookInfoRule bookInfoRule = (BookInfoRule)t;
                boolean bl3 = false;
                object = Result.constructor-impl(bookInfoRule);
            }
            catch (Throwable throwable) {
                Result.Companion companion = Result.Companion;
                boolean bl4 = false;
                object = Result.constructor-impl(ResultKt.createFailure(throwable));
            }
            Object object2 = object;
            boolean bl5 = false;
            return (BookInfoRule)(Result.isFailure-impl(object2) ? null : object2);
        }

        @NotNull
        public final String tocRuleToString(@Nullable TocRule tocRule) {
            String string = GsonExtensionsKt.getGSON().toJson(tocRule);
            Intrinsics.checkNotNullExpressionValue(string, "GSON.toJson(tocRule)");
            return string;
        }

        @Nullable
        public final TocRule stringToTocRule(@Nullable String json) {
            Object object;
            Gson $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
            boolean $i$f$fromJsonObject = false;
            boolean bl = false;
            try {
                object = Result.Companion;
                boolean bl2 = false;
                boolean $i$f$genericType = false;
                Type type = new TypeToken<TocRule>(){}.getType();
                Intrinsics.checkNotNullExpressionValue(type, "object : TypeToken<T>() {}.type");
                Object t = $this$fromJsonObject$iv.fromJson(json, type);
                if (!(t instanceof TocRule)) {
                    t = null;
                }
                TocRule tocRule = (TocRule)t;
                boolean bl3 = false;
                object = Result.constructor-impl(tocRule);
            }
            catch (Throwable throwable) {
                Result.Companion companion = Result.Companion;
                boolean bl4 = false;
                object = Result.constructor-impl(ResultKt.createFailure(throwable));
            }
            Object object2 = object;
            boolean bl5 = false;
            return (TocRule)(Result.isFailure-impl(object2) ? null : object2);
        }

        @NotNull
        public final String contentRuleToString(@Nullable ContentRule contentRule) {
            String string = GsonExtensionsKt.getGSON().toJson(contentRule);
            Intrinsics.checkNotNullExpressionValue(string, "GSON.toJson(contentRule)");
            return string;
        }

        @Nullable
        public final ContentRule stringToContentRule(@Nullable String json) {
            Object object;
            Gson $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
            boolean $i$f$fromJsonObject = false;
            boolean bl = false;
            try {
                object = Result.Companion;
                boolean bl2 = false;
                boolean $i$f$genericType = false;
                Type type = new TypeToken<ContentRule>(){}.getType();
                Intrinsics.checkNotNullExpressionValue(type, "object : TypeToken<T>() {}.type");
                Object t = $this$fromJsonObject$iv.fromJson(json, type);
                if (!(t instanceof ContentRule)) {
                    t = null;
                }
                ContentRule contentRule = (ContentRule)t;
                boolean bl3 = false;
                object = Result.constructor-impl(contentRule);
            }
            catch (Throwable throwable) {
                Result.Companion companion = Result.Companion;
                boolean bl4 = false;
                object = Result.constructor-impl(ResultKt.createFailure(throwable));
            }
            Object object2 = object;
            boolean bl5 = false;
            return (ContentRule)(Result.isFailure-impl(object2) ? null : object2);
        }
    }
}

