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
import io.legado.app.utils.GsonExtensionsKt;
import io.legado.app.utils.JsonExtensionsKt;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

@JsonIgnoreProperties(value={"headerMap", "source", "_userNameSpace", "userNameSpace"})
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b(\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0087\b\u0018\u0000 P2\u00020\u0001:\u0001PB\u008d\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0011J\t\u00103\u001a\u00020\u0003H\u00c6\u0003J\u0010\u00104\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001cJ\u000b\u00105\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u00106\u001a\u00020\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\u0005H\u00c6\u0003J\t\u00108\u001a\u00020\u0005H\u00c6\u0003J\u000b\u00109\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010:\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010;\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010<\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010=\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010>\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0096\u0001\u0010?\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010@J\u0013\u0010A\u001a\u00020\u000e2\b\u0010B\u001a\u0004\u0018\u00010CH\u00d6\u0003J\b\u0010D\u001a\u00020\u0005H\u0016J\n\u0010E\u001a\u0004\u0018\u00010\u001aH\u0016J\b\u0010F\u001a\u00020\u0005H\u0016J\b\u0010G\u001a\u00020\u0005H\u0016J\t\u0010H\u001a\u00020IH\u00d6\u0001J\u0010\u0010J\u001a\u00020K2\b\u0010L\u001a\u0004\u0018\u00010\u001aJ\u000e\u0010M\u001a\u00020K2\u0006\u0010N\u001a\u00020\u0005J\t\u0010O\u001a\u00020\u0005H\u00d6\u0001R\u000e\u0010\u0012\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0014\"\u0004\b\u0018\u0010\u0016R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0096\u000e\u00a2\u0006\u0010\n\u0002\u0010\u001f\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0014\"\u0004\b!\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u001c\u0010\f\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u0014\"\u0004\b%\u0010\u0016R\u001a\u0010\u0010\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010#\"\u0004\b'\u0010(R\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u0014\"\u0004\b*\u0010\u0016R\u001c\u0010\n\u001a\u0004\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0014\"\u0004\b,\u0010\u0016R\u001c\u0010\t\u001a\u0004\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u0014\"\u0004\b.\u0010\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u0014\"\u0004\b0\u0010\u0016R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\u0014\"\u0004\b2\u0010\u0016\u00a8\u0006Q"}, d2={"Lio/legado/app/data/entities/HttpTTS;", "Lio/legado/app/data/entities/BaseSource;", "id", "", "name", "", "url", "contentType", "concurrentRate", "loginUrl", "loginUi", "header", "jsLib", "enabledCookieJar", "", "loginCheckJs", "lastUpdateTime", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;J)V", "_userNameSpace", "getConcurrentRate", "()Ljava/lang/String;", "setConcurrentRate", "(Ljava/lang/String;)V", "getContentType", "setContentType", "debugLog", "Lio/legado/app/model/DebugLog;", "getEnabledCookieJar", "()Ljava/lang/Boolean;", "setEnabledCookieJar", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "getHeader", "setHeader", "getId", "()J", "getJsLib", "setJsLib", "getLastUpdateTime", "setLastUpdateTime", "(J)V", "getLoginCheckJs", "setLoginCheckJs", "getLoginUi", "setLoginUi", "getLoginUrl", "setLoginUrl", "getName", "setName", "getUrl", "setUrl", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;J)Lio/legado/app/data/entities/HttpTTS;", "equals", "other", "", "getKey", "getLogger", "getTag", "getUserNameSpace", "hashCode", "", "setLogger", "", "logger", "setUserNameSpace", "nameSpace", "toString", "Companion", "reader-pro"})
public final class HttpTTS
implements BaseSource {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final long id;
    @NotNull
    private String name;
    @NotNull
    private String url;
    @Nullable
    private String contentType;
    @Nullable
    private String concurrentRate;
    @Nullable
    private String loginUrl;
    @Nullable
    private String loginUi;
    @Nullable
    private String header;
    @Nullable
    private String jsLib;
    @Nullable
    private Boolean enabledCookieJar;
    @Nullable
    private String loginCheckJs;
    private long lastUpdateTime;
    @NotNull
    private transient String _userNameSpace;
    @Nullable
    private transient DebugLog debugLog;

    public HttpTTS(long id, @NotNull String name, @NotNull String url2, @Nullable String contentType, @Nullable String concurrentRate, @Nullable String loginUrl, @Nullable String loginUi, @Nullable String header, @Nullable String jsLib, @Nullable Boolean enabledCookieJar, @Nullable String loginCheckJs, long lastUpdateTime) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(url2, "url");
        this.id = id;
        this.name = name;
        this.url = url2;
        this.contentType = contentType;
        this.concurrentRate = concurrentRate;
        this.loginUrl = loginUrl;
        this.loginUi = loginUi;
        this.header = header;
        this.jsLib = jsLib;
        this.enabledCookieJar = enabledCookieJar;
        this.loginCheckJs = loginCheckJs;
        this.lastUpdateTime = lastUpdateTime;
        this._userNameSpace = "";
    }

    public /* synthetic */ HttpTTS(long l, String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, Boolean bl, String string9, long l2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            l = System.currentTimeMillis();
        }
        if ((n & 2) != 0) {
            string = "";
        }
        if ((n & 4) != 0) {
            string2 = "";
        }
        if ((n & 8) != 0) {
            string3 = null;
        }
        if ((n & 0x10) != 0) {
            string4 = "0";
        }
        if ((n & 0x20) != 0) {
            string5 = null;
        }
        if ((n & 0x40) != 0) {
            string6 = null;
        }
        if ((n & 0x80) != 0) {
            string7 = null;
        }
        if ((n & 0x100) != 0) {
            string8 = null;
        }
        if ((n & 0x200) != 0) {
            bl = false;
        }
        if ((n & 0x400) != 0) {
            string9 = null;
        }
        if ((n & 0x800) != 0) {
            l2 = System.currentTimeMillis();
        }
        this(l, string, string2, string3, string4, string5, string6, string7, string8, bl, string9, l2);
    }

    public final long getId() {
        return this.id;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final void setName(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.name = string;
    }

    @NotNull
    public final String getUrl() {
        return this.url;
    }

    public final void setUrl(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.url = string;
    }

    @Nullable
    public final String getContentType() {
        return this.contentType;
    }

    public final void setContentType(@Nullable String string) {
        this.contentType = string;
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

    @Override
    @Nullable
    public String getHeader() {
        return this.header;
    }

    @Override
    public void setHeader(@Nullable String string) {
        this.header = string;
    }

    @Nullable
    public final String getJsLib() {
        return this.jsLib;
    }

    public final void setJsLib(@Nullable String string) {
        this.jsLib = string;
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

    @Nullable
    public final String getLoginCheckJs() {
        return this.loginCheckJs;
    }

    public final void setLoginCheckJs(@Nullable String string) {
        this.loginCheckJs = string;
    }

    public final long getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public final void setLastUpdateTime(long l) {
        this.lastUpdateTime = l;
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

    @Override
    @NotNull
    public String getTag() {
        return this.name;
    }

    @Override
    @NotNull
    public String getKey() {
        return Intrinsics.stringPlus("httpTts:", this.id);
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

    public final long component1() {
        return this.id;
    }

    @NotNull
    public final String component2() {
        return this.name;
    }

    @NotNull
    public final String component3() {
        return this.url;
    }

    @Nullable
    public final String component4() {
        return this.contentType;
    }

    @Nullable
    public final String component5() {
        return this.getConcurrentRate();
    }

    @Nullable
    public final String component6() {
        return this.getLoginUrl();
    }

    @Nullable
    public final String component7() {
        return this.getLoginUi();
    }

    @Nullable
    public final String component8() {
        return this.getHeader();
    }

    @Nullable
    public final String component9() {
        return this.jsLib;
    }

    @Nullable
    public final Boolean component10() {
        return this.getEnabledCookieJar();
    }

    @Nullable
    public final String component11() {
        return this.loginCheckJs;
    }

    public final long component12() {
        return this.lastUpdateTime;
    }

    @NotNull
    public final HttpTTS copy(long id, @NotNull String name, @NotNull String url2, @Nullable String contentType, @Nullable String concurrentRate, @Nullable String loginUrl, @Nullable String loginUi, @Nullable String header, @Nullable String jsLib, @Nullable Boolean enabledCookieJar, @Nullable String loginCheckJs, long lastUpdateTime) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(url2, "url");
        return new HttpTTS(id, name, url2, contentType, concurrentRate, loginUrl, loginUi, header, jsLib, enabledCookieJar, loginCheckJs, lastUpdateTime);
    }

    public static /* synthetic */ HttpTTS copy$default(HttpTTS httpTTS, long l, String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, Boolean bl, String string9, long l2, int n, Object object) {
        if ((n & 1) != 0) {
            l = httpTTS.id;
        }
        if ((n & 2) != 0) {
            string = httpTTS.name;
        }
        if ((n & 4) != 0) {
            string2 = httpTTS.url;
        }
        if ((n & 8) != 0) {
            string3 = httpTTS.contentType;
        }
        if ((n & 0x10) != 0) {
            string4 = httpTTS.getConcurrentRate();
        }
        if ((n & 0x20) != 0) {
            string5 = httpTTS.getLoginUrl();
        }
        if ((n & 0x40) != 0) {
            string6 = httpTTS.getLoginUi();
        }
        if ((n & 0x80) != 0) {
            string7 = httpTTS.getHeader();
        }
        if ((n & 0x100) != 0) {
            string8 = httpTTS.jsLib;
        }
        if ((n & 0x200) != 0) {
            bl = httpTTS.getEnabledCookieJar();
        }
        if ((n & 0x400) != 0) {
            string9 = httpTTS.loginCheckJs;
        }
        if ((n & 0x800) != 0) {
            l2 = httpTTS.lastUpdateTime;
        }
        return httpTTS.copy(l, string, string2, string3, string4, string5, string6, string7, string8, bl, string9, l2);
    }

    @NotNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HttpTTS(id=").append(this.id).append(", name=").append(this.name).append(", url=").append(this.url).append(", contentType=").append((Object)this.contentType).append(", concurrentRate=").append((Object)this.getConcurrentRate()).append(", loginUrl=").append((Object)this.getLoginUrl()).append(", loginUi=").append((Object)this.getLoginUi()).append(", header=").append((Object)this.getHeader()).append(", jsLib=").append((Object)this.jsLib).append(", enabledCookieJar=").append(this.getEnabledCookieJar()).append(", loginCheckJs=").append((Object)this.loginCheckJs).append(", lastUpdateTime=");
        stringBuilder.append(this.lastUpdateTime).append(')');
        return stringBuilder.toString();
    }

    public int hashCode() {
        int result2 = Long.hashCode(this.id);
        result2 = result2 * 31 + this.name.hashCode();
        result2 = result2 * 31 + this.url.hashCode();
        result2 = result2 * 31 + (this.contentType == null ? 0 : this.contentType.hashCode());
        result2 = result2 * 31 + (this.getConcurrentRate() == null ? 0 : this.getConcurrentRate().hashCode());
        result2 = result2 * 31 + (this.getLoginUrl() == null ? 0 : this.getLoginUrl().hashCode());
        result2 = result2 * 31 + (this.getLoginUi() == null ? 0 : this.getLoginUi().hashCode());
        result2 = result2 * 31 + (this.getHeader() == null ? 0 : this.getHeader().hashCode());
        result2 = result2 * 31 + (this.jsLib == null ? 0 : this.jsLib.hashCode());
        result2 = result2 * 31 + (this.getEnabledCookieJar() == null ? 0 : ((Object)this.getEnabledCookieJar()).hashCode());
        result2 = result2 * 31 + (this.loginCheckJs == null ? 0 : this.loginCheckJs.hashCode());
        result2 = result2 * 31 + Long.hashCode(this.lastUpdateTime);
        return result2;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HttpTTS)) {
            return false;
        }
        HttpTTS httpTTS = (HttpTTS)other;
        if (this.id != httpTTS.id) {
            return false;
        }
        if (!Intrinsics.areEqual(this.name, httpTTS.name)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.url, httpTTS.url)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.contentType, httpTTS.contentType)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.getConcurrentRate(), httpTTS.getConcurrentRate())) {
            return false;
        }
        if (!Intrinsics.areEqual(this.getLoginUrl(), httpTTS.getLoginUrl())) {
            return false;
        }
        if (!Intrinsics.areEqual(this.getLoginUi(), httpTTS.getLoginUi())) {
            return false;
        }
        if (!Intrinsics.areEqual(this.getHeader(), httpTTS.getHeader())) {
            return false;
        }
        if (!Intrinsics.areEqual(this.jsLib, httpTTS.jsLib)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.getEnabledCookieJar(), httpTTS.getEnabledCookieJar())) {
            return false;
        }
        if (!Intrinsics.areEqual(this.loginCheckJs, httpTTS.loginCheckJs)) {
            return false;
        }
        return this.lastUpdateTime == httpTTS.lastUpdateTime;
    }

    public HttpTTS() {
        this(0L, null, null, null, null, null, null, null, null, null, null, 0L, 4095, null);
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\b\u0010\tJ4\u0010\n\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00050\u000bj\b\u0012\u0004\u0012\u00020\u0005`\f0\u00042\u0006\u0010\r\u001a\u00020\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u000e\u0010\tJ$\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0010\u001a\u00020\u0011\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0014"}, d2={"Lio/legado/app/data/entities/HttpTTS$Companion;", "", "()V", "fromJson", "Lkotlin/Result;", "Lio/legado/app/data/entities/HttpTTS;", "json", "", "fromJson-IoAF18A", "(Ljava/lang/String;)Ljava/lang/Object;", "fromJsonArray", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "jsonArray", "fromJsonArray-IoAF18A", "fromJsonDoc", "doc", "Lcom/jayway/jsonpath/DocumentContext;", "fromJsonDoc-IoAF18A", "(Lcom/jayway/jsonpath/DocumentContext;)Ljava/lang/Object;", "reader-pro"})
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
                Object loginUi = doc.read("$.loginUi", new Predicate[0]);
                Long l = JsonExtensionsKt.readLong(doc, "$.id");
                long l2 = l == null ? System.currentTimeMillis() : l;
                String string = JsonExtensionsKt.readString(doc, "$.name");
                Intrinsics.checkNotNull(string);
                String string2 = JsonExtensionsKt.readString(doc, "$.url");
                Intrinsics.checkNotNull(string2);
                HttpTTS httpTTS = new HttpTTS(l2, string, string2, JsonExtensionsKt.readString(doc, "$.contentType"), JsonExtensionsKt.readString(doc, "$.concurrentRate"), JsonExtensionsKt.readString(doc, "$.loginUrl"), (String)(loginUi instanceof List ? GsonExtensionsKt.getGSON().toJson(loginUi) : ((l = loginUi) == null ? null : ((Object)l).toString())), JsonExtensionsKt.readString(doc, "$.header"), null, null, JsonExtensionsKt.readString(doc, "$.loginCheckJs"), 0L, 2816, null);
                boolean bl3 = false;
                object = Result.constructor-impl(httpTTS);
            }
            catch (Throwable throwable) {
                Result.Companion companion = Result.Companion;
                boolean bl4 = false;
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
                ArrayList<HttpTTS> sources = new ArrayList<HttpTTS>();
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
                    HttpTTS source = (HttpTTS)object2;
                    boolean bl7 = false;
                    sources.add(source);
                }
                ArrayList<HttpTTS> arrayList = sources;
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

