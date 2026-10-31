/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.help;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DESede;
import com.google.gson.reflect.TypeToken;
import io.legado.app.adapters.ReaderAdapterHelper;
import io.legado.app.constant.AppConst;
import io.legado.app.data.entities.BaseSource;
import io.legado.app.exception.NoStackTraceException;
import io.legado.app.help.CacheManager;
import io.legado.app.help.http.CookieStore;
import io.legado.app.help.http.HttpHelperKt;
import io.legado.app.help.http.OkHttpUtilsKt;
import io.legado.app.help.http.SSLHelper;
import io.legado.app.help.http.StrResponse;
import io.legado.app.model.Debug;
import io.legado.app.model.DebugLog;
import io.legado.app.model.analyzeRule.AnalyzeUrl;
import io.legado.app.model.analyzeRule.QueryTTF;
import io.legado.app.utils.Base64;
import io.legado.app.utils.EncoderUtils;
import io.legado.app.utils.EncodingDetect;
import io.legado.app.utils.FileUtils;
import io.legado.app.utils.GsonExtensionsKt;
import io.legado.app.utils.HtmlFormatter;
import io.legado.app.utils.LogUtilsKt;
import io.legado.app.utils.MD5Utils;
import io.legado.app.utils.NetworkUtils;
import io.legado.app.utils.StringExtensionsKt;
import io.legado.app.utils.StringUtils;
import io.legado.app.utils.ThrowableExtensionsKt;
import io.legado.app.utils.ZipUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\t\n\u0002\b\u000f\bf\u0018\u00002\u00020\u0001J*\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J*\u0010\t\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J2\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J*\u0010\u000e\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J*\u0010\u000f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J2\u0010\u0010\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J*\u0010\u0011\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J*\u0010\u0012\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J*\u0010\u0013\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J*\u0010\u0014\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0016\u001a\u00020\u0005H\u0016J#\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u00182\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\u0018H\u0016\u00a2\u0006\u0002\u0010\u001bJ\b\u0010\u001c\u001a\u00020\u0005H\u0016J\u0010\u0010\u001d\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0018\u0010\u001d\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0014\u0010 \u001a\u0004\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016J\u001c\u0010 \u001a\u0004\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0012\u0010!\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u001a\u0010!\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0012\u0010\"\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0016\u001a\u00020\u0005H\u0016J\u001c\u0010\"\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0016\u001a\u00020\u00052\b\b\u0002\u0010#\u001a\u00020\u001fH\u0016J\u0010\u0010$\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u0005H\u0016J\u001a\u0010$\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u00052\b\u0010%\u001a\u0004\u0018\u00010\u0005H\u0016J\u0010\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020\u0005H\u0016J*\u0010)\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J*\u0010*\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J*\u0010+\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J*\u0010,\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J\u001a\u0010-\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\u0005H\u0016J\u001a\u0010/\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\u0005H\u0016J\u0018\u00100\u001a\u00020\u00052\u0006\u00101\u001a\u00020\u00052\u0006\u00102\u001a\u00020\u0005H\u0016J\u0010\u00103\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0018\u00103\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u00104\u001a\u00020\u0005H\u0016J$\u00105\u001a\u0002062\u0006\u0010\u0016\u001a\u00020\u00052\u0012\u00107\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000508H\u0016J\u001c\u00109\u001a\u00020\u00052\u0006\u0010:\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u0016J\u0010\u0010;\u001a\u00020<2\u0006\u0010(\u001a\u00020\u0005H\u0016J\n\u0010=\u001a\u0004\u0018\u00010>H&J\n\u0010?\u001a\u0004\u0018\u00010@H&J\u0010\u0010A\u001a\u00020\u00052\u0006\u0010B\u001a\u00020\u0005H\u0016J\b\u0010C\u001a\u00020\u0005H&J\u001a\u0010D\u001a\u0004\u0018\u00010\u00032\u0006\u00102\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u0005H\u0016J\u0018\u0010E\u001a\u00020\u00052\u0006\u00102\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u0005H\u0016J \u0010E\u001a\u00020\u00052\u0006\u00102\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u00052\u0006\u0010F\u001a\u00020\u0005H\u0016J$\u0010G\u001a\u0002062\u0006\u0010\u0016\u001a\u00020\u00052\u0012\u00107\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000508H\u0016J\u0010\u0010H\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010I\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u0005H\u0016J\u0010\u0010J\u001a\u00020\u00052\u0006\u0010K\u001a\u00020\u0005H\u0016J\u0012\u0010L\u001a\u00020'2\b\u0010M\u001a\u0004\u0018\u00010\u0001H\u0016J\u0012\u0010N\u001a\u00020'2\b\u0010K\u001a\u0004\u0018\u00010\u0001H\u0016J\u0010\u0010O\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010P\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J,\u0010Q\u001a\u0002062\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010R\u001a\u00020\u00052\u0012\u00107\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000508H\u0016J\u0014\u0010S\u001a\u0004\u0018\u00010T2\b\u0010U\u001a\u0004\u0018\u00010\u0005H\u0016J\u0014\u0010V\u001a\u0004\u0018\u00010T2\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016J\b\u0010W\u001a\u00020\u0005H\u0016J\u0012\u0010X\u001a\u0004\u0018\u00010\u00032\u0006\u0010(\u001a\u00020\u0005H\u0016J\u0010\u0010Y\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u0005H\u0016J\u0018\u0010Y\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u00052\u0006\u0010F\u001a\u00020\u0005H\u0016J$\u0010Z\u001a\u00020\u00052\u0006\u0010[\u001a\u00020\u00052\b\u0010\\\u001a\u0004\u0018\u00010T2\b\u0010]\u001a\u0004\u0018\u00010TH\u0016J\u0010\u0010^\u001a\u00020\u00052\u0006\u0010_\u001a\u00020`H\u0016J\"\u0010a\u001a\u0004\u0018\u00010\u00052\u0006\u0010_\u001a\u00020`2\u0006\u0010b\u001a\u00020\u00052\u0006\u0010c\u001a\u00020\u001fH\u0016J\u0012\u0010d\u001a\u00020'2\b\u0010K\u001a\u0004\u0018\u00010\u0001H\u0016J2\u0010e\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J2\u0010f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J2\u0010g\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J2\u0010h\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0016J\u0010\u0010i\u001a\u00020\u00052\u0006\u0010j\u001a\u00020\u0005H\u0016J\u0010\u0010k\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J(\u0010l\u001a\u0004\u0018\u00010\u00052\b\u0010m\u001a\u0004\u0018\u00010\u00052\b\u00102\u001a\u0004\u0018\u00010\u00052\b\u0010n\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006o"}, d2={"Lio/legado/app/help/JsExtensions;", "", "aesBase64DecodeToByteArray", "", "str", "", "key", "transformation", "iv", "aesBase64DecodeToString", "aesDecodeArgsBase64Str", "data", "mode", "padding", "aesDecodeToByteArray", "aesDecodeToString", "aesEncodeArgsBase64Str", "aesEncodeToBase64ByteArray", "aesEncodeToBase64String", "aesEncodeToByteArray", "aesEncodeToString", "ajax", "urlStr", "ajaxAll", "", "Lio/legado/app/help/http/StrResponse;", "urlList", "([Ljava/lang/String;)[Lio/legado/app/help/http/StrResponse;", "androidId", "base64Decode", "flags", "", "base64DecodeToByteArray", "base64Encode", "cacheFile", "saveTime", "connect", "header", "deleteFile", "", "path", "desBase64DecodeToString", "desDecodeToString", "desEncodeToBase64String", "desEncodeToString", "digestBase64Str", "algorithm", "digestHex", "downloadFile", "content", "url", "encodeURI", "enc", "get", "Lorg/jsoup/Connection$Response;", "headers", "", "getCookie", "tag", "getFile", "Ljava/io/File;", "getLogger", "Lio/legado/app/model/DebugLog;", "getSource", "Lio/legado/app/data/entities/BaseSource;", "getTxtInFolder", "unzipPath", "getUserNameSpace", "getZipByteArrayContent", "getZipStringContent", "charsetName", "head", "htmlFormat", "importScript", "log", "msg", "logType", "any", "longToast", "md5Encode", "md5Encode16", "post", "body", "queryBase64TTF", "Lio/legado/app/model/analyzeRule/QueryTTF;", "base64", "queryTTF", "randomUUID", "readFile", "readTxtFile", "replaceFont", "text", "font1", "font2", "timeFormat", "time", "", "timeFormatUTC", "format", "sh", "toast", "tripleDESDecodeArgsBase64Str", "tripleDESDecodeStr", "tripleDESEncodeArgsBase64Str", "tripleDESEncodeBase64Str", "unzipFile", "zipPath", "utf8ToGbk", "webView", "html", "js", "reader-pro"})
public interface JsExtensions {
    @Nullable
    public BaseSource getSource();

    @NotNull
    public String getUserNameSpace();

    @Nullable
    public DebugLog getLogger();

    @Nullable
    public String ajax(@NotNull String var1);

    @NotNull
    public StrResponse[] ajaxAll(@NotNull String[] var1);

    @NotNull
    public StrResponse connect(@NotNull String var1);

    @NotNull
    public StrResponse connect(@NotNull String var1, @Nullable String var2);

    @Nullable
    public String webView(@Nullable String var1, @Nullable String var2, @Nullable String var3);

    @NotNull
    public String importScript(@NotNull String var1);

    @Nullable
    public String cacheFile(@NotNull String var1);

    @Nullable
    public String cacheFile(@NotNull String var1, int var2);

    @NotNull
    public String getCookie(@NotNull String var1, @Nullable String var2);

    @NotNull
    public String downloadFile(@NotNull String var1, @NotNull String var2);

    @NotNull
    public Connection.Response get(@NotNull String var1, @NotNull Map<String, String> var2);

    @NotNull
    public Connection.Response head(@NotNull String var1, @NotNull Map<String, String> var2);

    @NotNull
    public Connection.Response post(@NotNull String var1, @NotNull String var2, @NotNull Map<String, String> var3);

    @NotNull
    public String base64Decode(@NotNull String var1);

    @NotNull
    public String base64Decode(@NotNull String var1, int var2);

    @Nullable
    public byte[] base64DecodeToByteArray(@Nullable String var1);

    @Nullable
    public byte[] base64DecodeToByteArray(@Nullable String var1, int var2);

    @Nullable
    public String base64Encode(@NotNull String var1);

    @Nullable
    public String base64Encode(@NotNull String var1, int var2);

    @NotNull
    public String md5Encode(@NotNull String var1);

    @NotNull
    public String md5Encode16(@NotNull String var1);

    @Nullable
    public String timeFormatUTC(long var1, @NotNull String var3, int var4);

    @NotNull
    public String timeFormat(long var1);

    @NotNull
    public String utf8ToGbk(@NotNull String var1);

    @NotNull
    public String encodeURI(@NotNull String var1);

    @NotNull
    public String encodeURI(@NotNull String var1, @NotNull String var2);

    @NotNull
    public String htmlFormat(@NotNull String var1);

    @NotNull
    public File getFile(@NotNull String var1);

    @Nullable
    public byte[] readFile(@NotNull String var1);

    @NotNull
    public String readTxtFile(@NotNull String var1);

    @NotNull
    public String readTxtFile(@NotNull String var1, @NotNull String var2);

    public void deleteFile(@NotNull String var1);

    @NotNull
    public String unzipFile(@NotNull String var1);

    @NotNull
    public String getTxtInFolder(@NotNull String var1);

    @NotNull
    public String getZipStringContent(@NotNull String var1, @NotNull String var2);

    @NotNull
    public String getZipStringContent(@NotNull String var1, @NotNull String var2, @NotNull String var3);

    @Nullable
    public byte[] getZipByteArrayContent(@NotNull String var1, @NotNull String var2);

    @Nullable
    public QueryTTF queryBase64TTF(@Nullable String var1);

    @Nullable
    public QueryTTF queryTTF(@Nullable String var1);

    @NotNull
    public String replaceFont(@NotNull String var1, @Nullable QueryTTF var2, @Nullable QueryTTF var3);

    public void toast(@Nullable Object var1);

    public void longToast(@Nullable Object var1);

    @NotNull
    public String log(@NotNull String var1);

    public void logType(@Nullable Object var1);

    @NotNull
    public String randomUUID();

    @Nullable
    public byte[] aesDecodeToByteArray(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public String aesDecodeToString(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public byte[] aesBase64DecodeToByteArray(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public String aesBase64DecodeToString(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public byte[] aesEncodeToByteArray(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public String aesEncodeToString(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public byte[] aesEncodeToBase64ByteArray(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public String aesEncodeToBase64String(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @NotNull
    public String androidId();

    @Nullable
    public String aesDecodeArgsBase64Str(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4, @NotNull String var5);

    @Nullable
    public String tripleDESDecodeStr(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4, @NotNull String var5);

    @Nullable
    public String tripleDESDecodeArgsBase64Str(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4, @NotNull String var5);

    @Nullable
    public String aesEncodeArgsBase64Str(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4, @NotNull String var5);

    @Nullable
    public String desDecodeToString(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public String desBase64DecodeToString(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public String desEncodeToString(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public String desEncodeToBase64String(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

    @Nullable
    public String tripleDESEncodeBase64Str(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4, @NotNull String var5);

    @Nullable
    public String tripleDESEncodeArgsBase64Str(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4, @NotNull String var5);

    @Nullable
    public String digestHex(@NotNull String var1, @NotNull String var2);

    @Nullable
    public String digestBase64Str(@NotNull String var1, @NotNull String var2);

    @Metadata(mv={1, 5, 1}, k=3, xi=48)
    public static final class DefaultImpls {
        @Nullable
        public static String ajax(@NotNull JsExtensions this_, @NotNull String urlStr) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(urlStr, "urlStr");
            return (String)BuildersKt.runBlocking$default(null, new Function2<CoroutineScope, Continuation<? super String>, Object>(urlStr, this_, null){
                int label;
                final /* synthetic */ String $urlStr;
                final /* synthetic */ JsExtensions this$0;
                {
                    this.$urlStr = $urlStr;
                    this.this$0 = $receiver;
                    super(2, $completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object object) {
                    Object object2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    switch (this.label) {
                        case 0: {
                            Object object3;
                            boolean bl;
                            Object object4;
                            ResultKt.throwOnFailure(object);
                            Object object5 = this.$urlStr;
                            JsExtensions jsExtensions = this.this$0;
                            boolean bl2 = false;
                            try {
                                object4 = Result.Companion;
                                boolean $i$a$-runCatching-JsExtensions$ajax$1$22 = false;
                                AnalyzeUrl analyzeUrl = new AnalyzeUrl((String)object5, null, null, null, null, null, jsExtensions.getSource(), null, null, null, jsExtensions.getLogger(), 958, null);
                                String $i$a$-runCatching-JsExtensions$ajax$1$22 = AnalyzeUrl.getStrResponse$default(analyzeUrl, (String)object5, null, false, 6, null).getBody();
                                boolean bl3 = false;
                                object4 = Result.constructor-impl($i$a$-runCatching-JsExtensions$ajax$1$22);
                            }
                            catch (Throwable $i$a$-runCatching-JsExtensions$ajax$1$22) {
                                Result.Companion companion = Result.Companion;
                                bl = false;
                                object4 = Result.constructor-impl(ResultKt.createFailure($i$a$-runCatching-JsExtensions$ajax$1$22));
                            }
                            object5 = object4;
                            boolean bl4 = false;
                            bl2 = false;
                            Throwable throwable = Result.exceptionOrNull-impl(object5);
                            if (throwable != null) {
                                Throwable throwable2 = throwable;
                                boolean bl5 = false;
                                boolean $i$a$-runCatching-JsExtensions$ajax$1$22 = false;
                                Throwable throwable3 = throwable2;
                                bl = false;
                                Throwable it = throwable3;
                                boolean bl6 = false;
                                LogUtilsKt.printOnDebug(it);
                            }
                            bl4 = false;
                            boolean bl7 = false;
                            Throwable throwable4 = Result.exceptionOrNull-impl(object5);
                            if (throwable4 == null) {
                                object3 = object5;
                            } else {
                                Throwable it = throwable4;
                                boolean bl8 = false;
                                object3 = ThrowableExtensionsKt.getMsg(it);
                            }
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
                public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super String> p2) {
                    return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                }
            }, 1, null);
        }

        @NotNull
        public static StrResponse[] ajaxAll(@NotNull JsExtensions this_, @NotNull String[] urlList) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(urlList, "urlList");
            return (StrResponse[])BuildersKt.runBlocking$default(null, new Function2<CoroutineScope, Continuation<? super StrResponse[]>, Object>(urlList, this_, null){
                Object L$1;
                Object L$2;
                int I$0;
                int I$1;
                int I$2;
                int label;
                private /* synthetic */ Object L$0;
                final /* synthetic */ String[] $urlList;
                final /* synthetic */ JsExtensions this$0;
                {
                    this.$urlList = $urlList;
                    this.this$0 = $receiver;
                    super(2, $completion);
                }

                /*
                 * Unable to fully structure code
                 */
                @Nullable
                public final Object invokeSuspend(@NotNull Object var1_1) {
                    var12_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    switch (this.label) {
                        case 0: {
                            ResultKt.throwOnFailure(var1_1);
                            $this$runBlocking = (CoroutineScope)this.L$0;
                            var4_4 = 0;
                            var5_6 = this.$urlList.length;
                            var6_7 = new Deferred[var5_6];
                            while (var4_4 < var5_6) {
                                var7_9 = var4_4++;
                                var6_7[var7_9] = BuildersKt.async$default($this$runBlocking, Dispatchers.getIO(), null, new Function2<CoroutineScope, Continuation<? super StrResponse>, Object>(this.$urlList, var7_9, this.this$0, null){
                                    int label;
                                    final /* synthetic */ String[] $urlList;
                                    final /* synthetic */ int $tmp;
                                    final /* synthetic */ JsExtensions this$0;
                                    {
                                        this.$urlList = $urlList;
                                        this.$tmp = $tmp;
                                        this.this$0 = $receiver;
                                        super(2, $completion);
                                    }

                                    @Nullable
                                    public final Object invokeSuspend(@NotNull Object object) {
                                        Object object2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                        switch (this.label) {
                                            case 0: {
                                                ResultKt.throwOnFailure(object);
                                                String url2 = this.$urlList[this.$tmp];
                                                AnalyzeUrl analyzeUrl = new AnalyzeUrl(url2, null, null, null, null, null, this.this$0.getSource(), null, null, null, this.this$0.getLogger(), 958, null);
                                                return AnalyzeUrl.getStrResponse$default(analyzeUrl, url2, null, false, 6, null);
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
                                }, 2, null);
                            }
                            asyncArray = var6_7;
                            var5_6 = 0;
                            var6_8 = this.$urlList.length;
                            var7_10 = new StrResponse[var6_8];
lbl17:
                            // 2 sources

                            while (var5_6 < var6_8) {
                                var10_14 = var8_12 = var5_6;
                                var9_13 = var7_10;
                                this.L$0 = asyncArray;
                                this.L$1 = var7_10;
                                this.L$2 = var9_13;
                                this.I$0 = var5_6;
                                this.I$1 = var6_8;
                                this.I$2 = var10_14;
                                this.label = 1;
                                v0 = asyncArray[var8_12].await(this);
                                if (v0 == var12_2) {
                                    return var12_2;
                                }
                                ** GOTO lbl41
                            }
                            break;
                        }
                        case 1: {
                            var10_14 = this.I$2;
                            var6_8 = this.I$1;
                            var5_6 = this.I$0;
                            var9_13 = (StrResponse[])this.L$2;
                            var7_10 = (StrResponse[])this.L$1;
                            asyncArray = (Deferred[])this.L$0;
                            ResultKt.throwOnFailure($result);
                            v0 = $result;
lbl41:
                            // 2 sources

                            var11_15 = v0;
                            var9_13[var10_14] = (StrResponse)var11_15;
                            ++var5_6;
                            ** GOTO lbl17
                        }
                    }
                    resArray = var7_10;
                    return resArray;
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }

                @NotNull
                public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                    Function2<CoroutineScope, Continuation<? super StrResponse[]>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                    function2.L$0 = value;
                    return (Continuation)((Object)function2);
                }

                @Nullable
                public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super StrResponse[]> p2) {
                    return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                }
            }, 1, null);
        }

        @NotNull
        public static StrResponse connect(@NotNull JsExtensions this_, @NotNull String urlStr) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(urlStr, "urlStr");
            return (StrResponse)BuildersKt.runBlocking$default(null, new Function2<CoroutineScope, Continuation<? super StrResponse>, Object>(urlStr, this_, null){
                Object L$0;
                int label;
                final /* synthetic */ String $urlStr;
                final /* synthetic */ JsExtensions this$0;
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
                    var12_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    switch (this.label) {
                        case 0: {
                            ResultKt.throwOnFailure(var1_1);
                            analyzeUrl = new AnalyzeUrl(this.$urlStr, null, null, null, null, null, this.this$0.getSource(), null, null, null, this.this$0.getLogger(), 958, null);
                            var3_4 = false;
                            var4_6 = Result.Companion;
                            $i$a$-runCatching-JsExtensions$connect$1$1 = false;
                            this.L$0 = analyzeUrl;
                            this.label = 1;
                            v0 = AnalyzeUrl.getStrResponseAwait$default(analyzeUrl, null, null, false, this, 7, null);
                            ** if (v0 != var12_2) goto lbl16
lbl15:
                            // 1 sources

                            return var12_2;
lbl16:
                            // 1 sources

                            ** GOTO lbl24
                        }
                        case 1: {
                            $i$a$-runCatching-JsExtensions$connect$1$1 = false;
                            analyzeUrl = (AnalyzeUrl)this.L$0;
                            try {
                                ResultKt.throwOnFailure($result);
                                v0 = $result;
lbl24:
                                // 2 sources

                                var5_9 = (StrResponse)v0;
                                var6_15 = false;
                                var4_6 = Result.constructor-impl(var5_9);
                            }
                            catch (Throwable var5_10) {
                                var6_16 = Result.Companion;
                                var7_18 = false;
                                var4_6 = Result.constructor-impl(ResultKt.createFailure(var5_10));
                            }
                            var3_5 = var4_6;
                            var4_7 = false;
                            var5_11 = false;
                            v1 = Result.exceptionOrNull-impl(var3_5);
                            if (v1 != null) {
                                var5_12 = v1;
                                var6_15 = false;
                                var7_18 = false;
                                var8_19 = var5_12;
                                var9_20 = false;
                                it = var8_19;
                                $i$a$-onFailure-JsExtensions$connect$1$2 = false;
                                LogUtilsKt.printOnDebug(it);
                            }
                            var4_7 = false;
                            var5_13 = false;
                            var5_14 = Result.exceptionOrNull-impl(var3_5);
                            if (var5_14 == null) {
                                v2 = var3_5;
                            } else {
                                it = var5_14;
                                $i$a$-getOrElse-JsExtensions$connect$1$3 = false;
                                v2 = new StrResponse(analyzeUrl.getUrl(), it.getLocalizedMessage());
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
                public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super StrResponse> p2) {
                    return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                }
            }, 1, null);
        }

        @NotNull
        public static StrResponse connect(@NotNull JsExtensions this_, @NotNull String urlStr, @Nullable String header) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(urlStr, "urlStr");
            return (StrResponse)BuildersKt.runBlocking$default(null, new Function2<CoroutineScope, Continuation<? super StrResponse>, Object>(header, this_, urlStr, null){
                Object L$0;
                int label;
                final /* synthetic */ String $header;
                final /* synthetic */ JsExtensions this$0;
                final /* synthetic */ String $urlStr;
                {
                    this.$header = $header;
                    this.this$0 = $receiver;
                    this.$urlStr = $urlStr;
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
                            var3_3 = GsonExtensionsKt.getGSON();
                            json$iv = this.$header;
                            $i$f$fromJsonObject = false;
                            var6_12 = false;
                            try {
                                var7_19 = Result.Companion;
                                $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                                $i$f$genericType = false;
                                var10_32 = new TypeToken<Map<String, ? extends String>>(){}.getType();
                                Intrinsics.checkNotNullExpressionValue(var10_32, "object : TypeToken<T>() {}.type");
                                v0 = $this$fromJsonObject$iv.fromJson(json$iv, var10_32);
                                if (!(v0 instanceof Map)) {
                                    v0 = null;
                                }
                                $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = (Map)v0;
                                var9_29 = false;
                                var7_19 = Result.constructor-impl($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv);
                            }
                            catch (Throwable $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv) {
                                var9_30 = Result.Companion;
                                var10_33 = false;
                                var7_19 = Result.constructor-impl(ResultKt.createFailure($i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv));
                            }
                            $this$fromJsonObject$iv = var7_19;
                            var4_5 = false;
                            headerMap = (Map)(Result.isFailure-impl($this$fromJsonObject$iv) != false ? null : $this$fromJsonObject$iv);
                            var4_6 = this.this$0.getSource();
                            var5_10 = this.this$0.getLogger();
                            analyzeUrl = new AnalyzeUrl(this.$urlStr, null, null, null, null, null, var4_6, null, null, headerMap, (DebugLog)var5_10, 446, null);
                            var4_7 = false;
                            var5_10 = Result.Companion;
                            $i$a$-runCatching-JsExtensions$connect$2$1 = false;
                            this.L$0 = analyzeUrl;
                            this.label = 1;
                            v1 = AnalyzeUrl.getStrResponseAwait$default(analyzeUrl, null, null, false, this, 7, null);
                            ** if (v1 != var13_2) goto lbl44
lbl43:
                            // 1 sources

                            return var13_2;
lbl44:
                            // 1 sources

                            ** GOTO lbl52
                        }
                        case 1: {
                            $i$a$-runCatching-JsExtensions$connect$2$1 = false;
                            analyzeUrl = (AnalyzeUrl)this.L$0;
                            try {
                                ResultKt.throwOnFailure($result);
                                v1 = $result;
lbl52:
                                // 2 sources

                                var6_13 = (StrResponse)v1;
                                var7_20 = false;
                                var5_10 = Result.constructor-impl(var6_13);
                            }
                            catch (Throwable var6_14) {
                                var7_19 = Result.Companion;
                                $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                                var5_10 = Result.constructor-impl(ResultKt.createFailure(var6_14));
                            }
                            var4_8 = var5_10;
                            var5_11 = false;
                            var6_15 = false;
                            v2 = Result.exceptionOrNull-impl(var4_8);
                            if (v2 != null) {
                                var6_16 = v2;
                                var7_21 = false;
                                $i$a$-runCatching-GsonExtensionsKt$fromJsonObject$1$iv = false;
                                var9_31 = var6_16;
                                var10_34 = false;
                                it = var9_31;
                                $i$a$-onFailure-JsExtensions$connect$2$2 = false;
                                LogUtilsKt.printOnDebug(it);
                            }
                            var5_11 = false;
                            var6_17 = false;
                            var6_18 = Result.exceptionOrNull-impl(var4_8);
                            if (var6_18 == null) {
                                v3 = var4_8;
                            } else {
                                it = var6_18;
                                $i$a$-getOrElse-JsExtensions$connect$2$3 = false;
                                v3 = new StrResponse(analyzeUrl.getUrl(), it.getLocalizedMessage());
                            }
                            return v3;
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

        @Nullable
        public static String webView(@NotNull JsExtensions this_, @Nullable String html, @Nullable String url2, @Nullable String js) {
            Intrinsics.checkNotNullParameter(this_, "this");
            return null;
        }

        @NotNull
        public static String importScript(@NotNull JsExtensions this_, @NotNull String path) {
            String result2;
            String string;
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(path, "path");
            String string2 = StringsKt.startsWith$default(path, "http", false, 2, null) ? ((string = this_.cacheFile(path)) == null ? "" : string) : (result2 = StringsKt.startsWith$default(path, "/storage", false, 2, null) ? FileUtils.readText$default(FileUtils.INSTANCE, path, null, 2, null) : this_.readTxtFile(path));
            if (StringsKt.isBlank(result2)) {
                throw new NoStackTraceException(Intrinsics.stringPlus(path, " \u5185\u5bb9\u83b7\u53d6\u5931\u8d25\u6216\u8005\u4e3a\u7a7a"));
            }
            return result2;
        }

        @Nullable
        public static String cacheFile(@NotNull JsExtensions this_, @NotNull String urlStr) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(urlStr, "urlStr");
            return this_.cacheFile(urlStr, 0);
        }

        @Nullable
        public static String cacheFile(@NotNull JsExtensions this_, @NotNull String urlStr, int saveTime) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(urlStr, "urlStr");
            String key = this_.md5Encode16(urlStr);
            CacheManager cacheInstance = new CacheManager(this_.getUserNameSpace());
            String cache = cacheInstance.getFile(key);
            CharSequence charSequence = cache;
            boolean bl = false;
            boolean bl2 = false;
            if (charSequence == null || StringsKt.isBlank(charSequence)) {
                this_.log(Intrinsics.stringPlus("\u9996\u6b21\u4e0b\u8f7d ", urlStr));
                String string = this_.ajax(urlStr);
                if (string == null) {
                    return null;
                }
                String value = string;
                cacheInstance.putFile(key, value, saveTime);
                return value;
            }
            return cache;
        }

        public static /* synthetic */ String cacheFile$default(JsExtensions jsExtensions, String string, int n, int n2, Object object) {
            if (object != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: cacheFile");
            }
            if ((n2 & 2) != 0) {
                n = 0;
            }
            return jsExtensions.cacheFile(string, n);
        }

        @NotNull
        public static String getCookie(@NotNull JsExtensions this_, @NotNull String tag, @Nullable String key) {
            String string;
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(tag, "tag");
            CookieStore cookieStore = new CookieStore(this_.getUserNameSpace());
            String cookie = cookieStore.getCookie(tag);
            Map<String, String> cookieMap = cookieStore.cookieToMap(cookie);
            return key != null ? ((string = cookieMap.get(key)) == null ? "" : string) : cookie;
        }

        public static /* synthetic */ String getCookie$default(JsExtensions jsExtensions, String string, String string2, int n, Object object) {
            if (object != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getCookie");
            }
            if ((n & 2) != 0) {
                string2 = null;
            }
            return jsExtensions.getCookie(string, string2);
        }

        @NotNull
        public static String downloadFile(@NotNull JsExtensions this_, @NotNull String content, @NotNull String url2) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(content, "content");
            Intrinsics.checkNotNullParameter(url2, "url");
            String string = new AnalyzeUrl(url2, null, null, null, null, null, null, null, null, null, null, 2046, null).getType();
            if (string == null) {
                return "";
            }
            String type = string;
            String[] stringArray = new String[]{MD5Utils.INSTANCE.md5Encode16(url2) + '.' + type};
            String zipPath = FileUtils.INSTANCE.getPath(FileUtils.INSTANCE.createFolderIfNotExist(FileUtils.INSTANCE.getCachePath()), stringArray);
            FileUtils.INSTANCE.deleteFile(zipPath);
            File zipFile = FileUtils.INSTANCE.createFileIfNotExist(zipPath);
            Object object = StringUtils.INSTANCE.hexStringToByte(content);
            int n = 0;
            boolean bl = false;
            byte[] it = object;
            boolean bl2 = false;
            byte[] byArray = it;
            boolean bl3 = false;
            byte[] byArray2 = byArray;
            boolean bl4 = false;
            if (!(byArray2.length == 0)) {
                FilesKt.writeBytes(zipFile, it);
            }
            object = zipPath;
            n = FileUtils.INSTANCE.getCachePath().length();
            bl = false;
            Object object2 = object;
            if (object2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string2 = ((String)object2).substring(n);
            Intrinsics.checkNotNullExpressionValue(string2, "(this as java.lang.String).substring(startIndex)");
            return string2;
        }

        @NotNull
        public static Connection.Response get(@NotNull JsExtensions this_, @NotNull String urlStr, @NotNull Map<String, String> headers) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(urlStr, "urlStr");
            Intrinsics.checkNotNullParameter(headers, "headers");
            Connection.Response response2 = Jsoup.connect(urlStr).sslSocketFactory(SSLHelper.INSTANCE.getUnsafeSSLSocketFactory()).ignoreContentType(true).followRedirects(false).headers(headers).method(Connection.Method.GET).execute();
            Map<String, String> cookies = response2.cookies();
            CookieStore cookieStore = new CookieStore(this_.getUserNameSpace());
            String string = cookieStore.mapToCookie(cookies);
            if (string != null) {
                String string2 = string;
                boolean bl = false;
                boolean bl2 = false;
                String it = string2;
                boolean bl3 = false;
                String domain = NetworkUtils.INSTANCE.getSubDomain(urlStr);
                cookieStore.replaceCookie(Intrinsics.stringPlus(domain, "_cookieJar"), it);
            }
            Intrinsics.checkNotNullExpressionValue(response2, "response");
            return response2;
        }

        @NotNull
        public static Connection.Response head(@NotNull JsExtensions this_, @NotNull String urlStr, @NotNull Map<String, String> headers) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(urlStr, "urlStr");
            Intrinsics.checkNotNullParameter(headers, "headers");
            Connection.Response response2 = Jsoup.connect(urlStr).sslSocketFactory(SSLHelper.INSTANCE.getUnsafeSSLSocketFactory()).ignoreContentType(true).followRedirects(false).headers(headers).method(Connection.Method.HEAD).execute();
            Map<String, String> cookies = response2.cookies();
            CookieStore cookieStore = new CookieStore(this_.getUserNameSpace());
            String string = cookieStore.mapToCookie(cookies);
            if (string != null) {
                String string2 = string;
                boolean bl = false;
                boolean bl2 = false;
                String it = string2;
                boolean bl3 = false;
                String domain = NetworkUtils.INSTANCE.getSubDomain(urlStr);
                cookieStore.replaceCookie(Intrinsics.stringPlus(domain, "_cookieJar"), it);
            }
            Intrinsics.checkNotNullExpressionValue(response2, "response");
            return response2;
        }

        @NotNull
        public static Connection.Response post(@NotNull JsExtensions this_, @NotNull String urlStr, @NotNull String body, @NotNull Map<String, String> headers) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(urlStr, "urlStr");
            Intrinsics.checkNotNullParameter(body, "body");
            Intrinsics.checkNotNullParameter(headers, "headers");
            Connection.Response response2 = Jsoup.connect(urlStr).sslSocketFactory(SSLHelper.INSTANCE.getUnsafeSSLSocketFactory()).ignoreContentType(true).followRedirects(false).requestBody(body).headers(headers).method(Connection.Method.POST).execute();
            Map<String, String> cookies = response2.cookies();
            CookieStore cookieStore = new CookieStore(this_.getUserNameSpace());
            String string = cookieStore.mapToCookie(cookies);
            if (string != null) {
                String string2 = string;
                boolean bl = false;
                boolean bl2 = false;
                String it = string2;
                boolean bl3 = false;
                String domain = NetworkUtils.INSTANCE.getSubDomain(urlStr);
                cookieStore.replaceCookie(Intrinsics.stringPlus(domain, "_cookieJar"), it);
            }
            Intrinsics.checkNotNullExpressionValue(response2, "response");
            return response2;
        }

        @NotNull
        public static String base64Decode(@NotNull JsExtensions this_, @NotNull String str) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(str, "str");
            return EncoderUtils.INSTANCE.base64Decode(str, 2);
        }

        @NotNull
        public static String base64Decode(@NotNull JsExtensions this_, @NotNull String str, int flags) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(str, "str");
            return EncoderUtils.INSTANCE.base64Decode(str, flags);
        }

        @Nullable
        public static byte[] base64DecodeToByteArray(@NotNull JsExtensions this_, @Nullable String str) {
            Intrinsics.checkNotNullParameter(this_, "this");
            CharSequence charSequence = str;
            boolean bl = false;
            boolean bl2 = false;
            if (charSequence == null || StringsKt.isBlank(charSequence)) {
                return null;
            }
            return Base64.decode(str, 0);
        }

        @Nullable
        public static byte[] base64DecodeToByteArray(@NotNull JsExtensions this_, @Nullable String str, int flags) {
            Intrinsics.checkNotNullParameter(this_, "this");
            CharSequence charSequence = str;
            boolean bl = false;
            boolean bl2 = false;
            if (charSequence == null || StringsKt.isBlank(charSequence)) {
                return null;
            }
            return Base64.decode(str, flags);
        }

        @Nullable
        public static String base64Encode(@NotNull JsExtensions this_, @NotNull String str) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(str, "str");
            return EncoderUtils.INSTANCE.base64Encode(str, 2);
        }

        @Nullable
        public static String base64Encode(@NotNull JsExtensions this_, @NotNull String str, int flags) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(str, "str");
            return EncoderUtils.INSTANCE.base64Encode(str, flags);
        }

        @NotNull
        public static String md5Encode(@NotNull JsExtensions this_, @NotNull String str) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(str, "str");
            return MD5Utils.INSTANCE.md5Encode(str);
        }

        @NotNull
        public static String md5Encode16(@NotNull JsExtensions this_, @NotNull String str) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(str, "str");
            return MD5Utils.INSTANCE.md5Encode16(str);
        }

        @Nullable
        public static String timeFormatUTC(@NotNull JsExtensions this_, long time, @NotNull String format, int sh) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(format, "format");
            SimpleTimeZone utc = new SimpleTimeZone(sh, "UTC");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
            boolean bl = false;
            boolean bl2 = false;
            SimpleDateFormat receiver = simpleDateFormat;
            boolean bl3 = false;
            receiver.setTimeZone(utc);
            return receiver.format(new Date(time));
        }

        @NotNull
        public static String timeFormat(@NotNull JsExtensions this_, long time) {
            Intrinsics.checkNotNullParameter(this_, "this");
            String string = AppConst.INSTANCE.getDateFormat().format(new Date(time));
            Intrinsics.checkNotNullExpressionValue(string, "dateFormat.format(Date(time))");
            return string;
        }

        @NotNull
        public static String utf8ToGbk(@NotNull JsExtensions this_, @NotNull String str) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(str, "str");
            Object object = str;
            Object object2 = "UTF-8";
            boolean bl = false;
            Charset charset = Charset.forName((String)object2);
            Intrinsics.checkNotNullExpressionValue(charset, "Charset.forName(charsetName)");
            object2 = charset;
            bl = false;
            byte[] byArray = ((String)object).getBytes((Charset)object2);
            Intrinsics.checkNotNullExpressionValue(byArray, "(this as java.lang.String).getBytes(charset)");
            object = byArray;
            boolean bl2 = false;
            String utf8 = new String((byte[])object, Charsets.UTF_8);
            Object object3 = utf8;
            Object object4 = Charsets.UTF_8;
            boolean bl3 = false;
            byte[] byArray2 = ((String)object3).getBytes((Charset)object4);
            Intrinsics.checkNotNullExpressionValue(byArray2, "(this as java.lang.String).getBytes(charset)");
            object3 = byArray2;
            object4 = "UTF-8";
            bl3 = false;
            Charset charset2 = Charset.forName((String)object4);
            Intrinsics.checkNotNullExpressionValue(charset2, "Charset.forName(charsetName)");
            object4 = charset2;
            bl3 = false;
            String unicode = new String((byte[])object3, (Charset)object4);
            object3 = unicode;
            object4 = "GBK";
            bl3 = false;
            Charset charset3 = Charset.forName((String)object4);
            Intrinsics.checkNotNullExpressionValue(charset3, "Charset.forName(charsetName)");
            object4 = charset3;
            bl3 = false;
            byte[] byArray3 = ((String)object3).getBytes((Charset)object4);
            Intrinsics.checkNotNullExpressionValue(byArray3, "(this as java.lang.String).getBytes(charset)");
            object3 = byArray3;
            boolean bl4 = false;
            return new String((byte[])object3, Charsets.UTF_8);
        }

        @NotNull
        public static String encodeURI(@NotNull JsExtensions this_, @NotNull String str) {
            String string;
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(str, "str");
            try {
                string = URLEncoder.encode(str, "UTF-8");
                Intrinsics.checkNotNullExpressionValue(string, "{\n            URLEncoder.encode(str, \"UTF-8\")\n        }");
            }
            catch (Exception e) {
                string = "";
            }
            return string;
        }

        @NotNull
        public static String encodeURI(@NotNull JsExtensions this_, @NotNull String str, @NotNull String enc) {
            String string;
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(str, "str");
            Intrinsics.checkNotNullParameter(enc, "enc");
            try {
                string = URLEncoder.encode(str, enc);
                Intrinsics.checkNotNullExpressionValue(string, "{\n            URLEncoder.encode(str, enc)\n        }");
            }
            catch (Exception e) {
                string = "";
            }
            return string;
        }

        @NotNull
        public static String htmlFormat(@NotNull JsExtensions this_, @NotNull String str) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(str, "str");
            return HtmlFormatter.formatKeepImg$default(HtmlFormatter.INSTANCE, str, null, 2, null);
        }

        @NotNull
        public static File getFile(@NotNull JsExtensions this_, @NotNull String path) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(path, "path");
            String cachePath = ReaderAdapterHelper.INSTANCE.getAdapter().getCacheDir();
            String string = File.separator;
            Intrinsics.checkNotNullExpressionValue(string, "separator");
            String aPath = StringsKt.startsWith$default(path, string, false, 2, null) ? Intrinsics.stringPlus(cachePath, path) : cachePath + File.separator + path;
            return new File(aPath);
        }

        @Nullable
        public static byte[] readFile(@NotNull JsExtensions this_, @NotNull String path) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(path, "path");
            File file = this_.getFile(path);
            if (file.exists()) {
                return FilesKt.readBytes(file);
            }
            return null;
        }

        @NotNull
        public static String readTxtFile(@NotNull JsExtensions this_, @NotNull String path) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(path, "path");
            File file = this_.getFile(path);
            if (file.exists()) {
                String charsetName = EncodingDetect.INSTANCE.getEncode(file);
                byte[] byArray = FilesKt.readBytes(file);
                boolean bl = false;
                Charset charset = Charset.forName(charsetName);
                Intrinsics.checkNotNullExpressionValue(charset, "Charset.forName(charsetName)");
                Charset charset2 = charset;
                boolean bl2 = false;
                return new String(byArray, charset2);
            }
            return "";
        }

        @NotNull
        public static String readTxtFile(@NotNull JsExtensions this_, @NotNull String path, @NotNull String charsetName) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(path, "path");
            Intrinsics.checkNotNullParameter(charsetName, "charsetName");
            File file = this_.getFile(path);
            if (file.exists()) {
                byte[] byArray = FilesKt.readBytes(file);
                boolean bl = false;
                Charset charset = Charset.forName(charsetName);
                Intrinsics.checkNotNullExpressionValue(charset, "Charset.forName(charsetName)");
                Charset charset2 = charset;
                boolean bl2 = false;
                return new String(byArray, charset2);
            }
            return "";
        }

        public static void deleteFile(@NotNull JsExtensions this_, @NotNull String path) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(path, "path");
            File file = this_.getFile(path);
            FileUtils.INSTANCE.delete(file, true);
        }

        @NotNull
        public static String unzipFile(@NotNull JsExtensions this_, @NotNull String zipPath) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(zipPath, "zipPath");
            CharSequence charSequence = zipPath;
            boolean bl = false;
            if (charSequence.length() == 0) {
                return "";
            }
            String[] stringArray = new String[]{FileUtils.INSTANCE.getNameExcludeExtension(zipPath)};
            String unzipPath = FileUtils.INSTANCE.getPath(FileUtils.INSTANCE.createFolderIfNotExist(FileUtils.INSTANCE.getCachePath()), stringArray);
            FileUtils.INSTANCE.deleteFile(unzipPath);
            File zipFile = this_.getFile(zipPath);
            File unzipFolder = FileUtils.INSTANCE.createFolderIfNotExist(unzipPath);
            ZipUtils.INSTANCE.unzipFile(zipFile, unzipFolder);
            String string = zipFile.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(string, "zipFile.absolutePath");
            FileUtils.INSTANCE.deleteFile(string);
            string = unzipPath;
            int n = FileUtils.INSTANCE.getCachePath().length();
            boolean bl2 = false;
            String string2 = string;
            if (string2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string3 = string2.substring(n);
            Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.String).substring(startIndex)");
            return string3;
        }

        @NotNull
        public static String getTxtInFolder(@NotNull JsExtensions this_, @NotNull String unzipPath) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(unzipPath, "unzipPath");
            CharSequence charSequence = unzipPath;
            boolean bl = false;
            if (charSequence.length() == 0) {
                return "";
            }
            File unzipFolder = this_.getFile(unzipPath);
            StringBuilder contents = new StringBuilder();
            Object object = unzipFolder.listFiles();
            boolean bl2 = false;
            boolean bl3 = false;
            File[] it = object;
            boolean bl4 = false;
            if (it != null) {
                for (File f : it) {
                    Charset charset;
                    Intrinsics.checkNotNullExpressionValue(f, "f");
                    String charsetName = EncodingDetect.INSTANCE.getEncode(f);
                    byte[] byArray = FilesKt.readBytes(f);
                    boolean bl5 = false;
                    Intrinsics.checkNotNullExpressionValue(Charset.forName(charsetName), "Charset.forName(charsetName)");
                    boolean bl6 = false;
                    contents.append(new String(byArray, charset)).append("\n");
                }
                contents.deleteCharAt(contents.length() - 1);
            }
            object = unzipFolder.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(object, "unzipFolder.absolutePath");
            FileUtils.INSTANCE.deleteFile((String)object);
            object = contents.toString();
            Intrinsics.checkNotNullExpressionValue(object, "contents.toString()");
            return object;
        }

        @NotNull
        public static String getZipStringContent(@NotNull JsExtensions this_, @NotNull String url2, @NotNull String path) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(url2, "url");
            Intrinsics.checkNotNullParameter(path, "path");
            byte[] byArray = this_.getZipByteArrayContent(url2, path);
            if (byArray == null) {
                return "";
            }
            byte[] byteArray2 = byArray;
            String charsetName = EncodingDetect.INSTANCE.getEncode(byteArray2);
            Charset charset = Charset.forName(charsetName);
            Intrinsics.checkNotNullExpressionValue(charset, "forName(charsetName)");
            boolean bl = false;
            return new String(byteArray2, charset);
        }

        @NotNull
        public static String getZipStringContent(@NotNull JsExtensions this_, @NotNull String url2, @NotNull String path, @NotNull String charsetName) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(url2, "url");
            Intrinsics.checkNotNullParameter(path, "path");
            Intrinsics.checkNotNullParameter(charsetName, "charsetName");
            Object object = this_.getZipByteArrayContent(url2, path);
            if (object == null) {
                return "";
            }
            byte[] byteArray2 = object;
            object = Charset.forName(charsetName);
            Intrinsics.checkNotNullExpressionValue(object, "forName(charsetName)");
            boolean bl = false;
            return new String(byteArray2, (Charset)object);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Nullable
        public static byte[] getZipByteArrayContent(@NotNull JsExtensions this_, @NotNull String url2, @NotNull String path) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(url2, "url");
            Intrinsics.checkNotNullParameter(path, "path");
            byte[] bytes2 = StringsKt.startsWith$default(url2, "http://", false, 2, null) || StringsKt.startsWith$default(url2, "https://", false, 2, null) ? (byte[])BuildersKt.runBlocking$default(null, new Function2<CoroutineScope, Continuation<? super byte[]>, Object>(url2, null){
                int label;
                final /* synthetic */ String $url;
                {
                    this.$url = $url;
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
                            Object object3 = OkHttpUtilsKt.newCall$default(HttpHelperKt.getOkHttpClient(), 0, new Function1<Request.Builder, Unit>(this.$url){
                                final /* synthetic */ String $url;
                                {
                                    this.$url = $url;
                                    super(1);
                                }

                                public final void invoke(@NotNull Request.Builder $this$newCall) {
                                    Intrinsics.checkNotNullParameter($this$newCall, "$this$newCall");
                                    $this$newCall.url(this.$url);
                                }
                            }, this, 1, null);
                            if (object3 != object2) return ((ResponseBody)object3).bytes();
                            return object2;
                        }
                        case 1: {
                            void $result;
                            ResultKt.throwOnFailure($result);
                            Object object3 = $result;
                            return ((ResponseBody)object3).bytes();
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
            }, 1, null) : StringUtils.INSTANCE.hexStringToByte(url2);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(bytes2));
            ZipEntry entry = zis.getNextEntry();
            while (entry != null) {
                if (entry.getName().equals(path)) {
                    Closeable closeable = zis;
                    boolean bl = false;
                    boolean bl2 = false;
                    Throwable throwable = null;
                    try {
                        ZipInputStream it = (ZipInputStream)closeable;
                        boolean bl3 = false;
                        long l = ByteStreamsKt.copyTo$default(it, bos, 0, 2, null);
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    finally {
                        CloseableKt.closeFinally(closeable, throwable);
                    }
                    return bos.toByteArray();
                }
                entry = zis.getNextEntry();
            }
            Debug.INSTANCE.log("getZipContent \u672a\u53d1\u73b0\u5185\u5bb9");
            return null;
        }

        @Nullable
        public static QueryTTF queryBase64TTF(@NotNull JsExtensions this_, @Nullable String base64) {
            Intrinsics.checkNotNullParameter(this_, "this");
            byte[] byArray = this_.base64DecodeToByteArray(base64);
            if (byArray != null) {
                byte[] byArray2 = byArray;
                boolean bl = false;
                boolean bl2 = false;
                byte[] it = byArray2;
                boolean bl3 = false;
                return new QueryTTF(it);
            }
            return null;
        }

        @Nullable
        public static QueryTTF queryTTF(@NotNull JsExtensions this_, @Nullable String str) {
            Intrinsics.checkNotNullParameter(this_, "this");
            String string = str;
            if (string == null) {
                return null;
            }
            String key = this_.md5Encode16(str);
            Ref.ObjectRef<CacheManager> cacheInstance = new Ref.ObjectRef<CacheManager>();
            cacheInstance.element = new CacheManager(this_.getUserNameSpace());
            QueryTTF qTTF = ((CacheManager)cacheInstance.element).getQueryTTF(key);
            if (qTTF != null) {
                return qTTF;
            }
            byte[] font2 = StringExtensionsKt.isAbsUrl(str) ? (byte[])BuildersKt.runBlocking$default(null, new Function2<CoroutineScope, Continuation<? super byte[]>, Object>(cacheInstance, key, str, null){
                int label;
                final /* synthetic */ Ref.ObjectRef<CacheManager> $cacheInstance;
                final /* synthetic */ String $key;
                final /* synthetic */ String $str;
                {
                    this.$cacheInstance = $cacheInstance;
                    this.$key = $key;
                    this.$str = $str;
                    super(2, $completion);
                }

                /*
                 * Unable to fully structure code
                 */
                @Nullable
                public final Object invokeSuspend(@NotNull Object var1_1) {
                    var10_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    switch (this.label) {
                        case 0: {
                            ResultKt.throwOnFailure(var1_1);
                            x = ((CacheManager)this.$cacheInstance.element).getByteArray(this.$key);
                            if (x != null) ** GOTO lbl25
                            this.label = 1;
                            v0 = OkHttpUtilsKt.newCall$default(HttpHelperKt.getOkHttpClient(), 0, new Function1<Request.Builder, Unit>(this.$str){
                                final /* synthetic */ String $str;
                                {
                                    this.$str = $str;
                                    super(1);
                                }

                                public final void invoke(@NotNull Request.Builder $this$newCall) {
                                    Intrinsics.checkNotNullParameter($this$newCall, "$this$newCall");
                                    $this$newCall.url(this.$str);
                                }
                            }, this, 1, null);
                            if (v0 == var10_2) {
                                return var10_2;
                            }
                            ** GOTO lbl15
                        }
                        case 1: {
                            ResultKt.throwOnFailure($result);
                            v0 = $result;
lbl15:
                            // 2 sources

                            var3_4 = x = ((ResponseBody)v0).bytes();
                            var4_5 = this.$cacheInstance;
                            var5_6 = this.$key;
                            var6_7 = false;
                            var7_8 = false;
                            it = var3_4;
                            $i$a$-let-JsExtensions$queryTTF$font$1$2 = false;
                            CacheManager.put$default((CacheManager)var4_5.element, var5_6, it, 0, 4, null);
lbl25:
                            // 2 sources

                            return x;
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
            }, 1, null) : (StringsKt.indexOf$default((CharSequence)str, "storage/", 0, false, 6, null) > 0 ? FilesKt.readBytes(new File(str)) : this_.base64DecodeToByteArray(str));
            byte[] byArray = font2;
            if (byArray == null) {
                return null;
            }
            qTTF = new QueryTTF(font2);
            CacheManager.put$default((CacheManager)cacheInstance.element, key, qTTF, 0, 4, null);
            return qTTF;
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public static String replaceFont(@NotNull JsExtensions this_, @NotNull String text, @Nullable QueryTTF font1, @Nullable QueryTTF font2) {
            char[] contentArray;
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(text, "text");
            if (font1 == null || font2 == null) {
                return text;
            }
            String string = text;
            boolean bl = false;
            char[] cArray = string.toCharArray();
            Intrinsics.checkNotNullExpressionValue(cArray, "(this as java.lang.String).toCharArray()");
            char[] $this$forEachIndexed$iv = contentArray = cArray;
            boolean $i$f$forEachIndexed = false;
            int index$iv = 0;
            for (char item$iv : $this$forEachIndexed$iv) {
                int code;
                void s;
                int n = index$iv++;
                char c = item$iv;
                int index = n;
                boolean bl2 = false;
                void var15_15 = s;
                boolean bl3 = false;
                void oldCode = var15_15;
                if (!font1.inLimit((char)s) || (code = font2.getCodeByGlyf(font1.getGlyfByCode((int)oldCode))) == 0) continue;
                contentArray[index] = (char)code;
            }
            return ArraysKt.joinToString$default(contentArray, (CharSequence)"", null, null, 0, null, null, 62, null);
        }

        public static void toast(@NotNull JsExtensions this_, @Nullable Object msg) {
            Intrinsics.checkNotNullParameter(this_, "this");
            DebugLog debugLog = this_.getLogger();
            if (debugLog != null) {
                debugLog.log(Intrinsics.stringPlus("toast: ", msg));
            }
            Debug.INSTANCE.log(Intrinsics.stringPlus("toast: ", msg));
        }

        public static void longToast(@NotNull JsExtensions this_, @Nullable Object msg) {
            Intrinsics.checkNotNullParameter(this_, "this");
            DebugLog debugLog = this_.getLogger();
            if (debugLog != null) {
                debugLog.log(Intrinsics.stringPlus("longToast: ", msg));
            }
            Debug.INSTANCE.log(Intrinsics.stringPlus("longToast: ", msg));
        }

        @NotNull
        public static String log(@NotNull JsExtensions this_, @NotNull String msg) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(msg, "msg");
            DebugLog debugLog = this_.getLogger();
            if (debugLog != null) {
                debugLog.log(msg);
            }
            Debug.INSTANCE.log(msg);
            return msg;
        }

        public static void logType(@NotNull JsExtensions this_, @Nullable Object any) {
            Intrinsics.checkNotNullParameter(this_, "this");
            if (any == null) {
                this_.log("null");
            } else {
                String string = any.getClass().getName();
                Intrinsics.checkNotNullExpressionValue(string, "any.javaClass.name");
                this_.log(string);
            }
        }

        @NotNull
        public static String randomUUID(@NotNull JsExtensions this_) {
            Intrinsics.checkNotNullParameter(this_, "this");
            String string = UUID.randomUUID().toString();
            Intrinsics.checkNotNullExpressionValue(string, "randomUUID().toString()");
            return string;
        }

        @Nullable
        public static byte[] aesDecodeToByteArray(@NotNull JsExtensions this_, @NotNull String str, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
            byte[] byArray;
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(str, "str");
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(transformation, "transformation");
            Intrinsics.checkNotNullParameter(iv, "iv");
            try {
                byArray = EncoderUtils.INSTANCE.decryptAES(StringsKt.encodeToByteArray(str), StringsKt.encodeToByteArray(key), transformation, StringsKt.encodeToByteArray(iv));
            }
            catch (Exception e) {
                LogUtilsKt.printOnDebug(e);
                String string = e.getLocalizedMessage();
                this_.log(string == null ? "aesDecodeToByteArrayERROR" : string);
                byArray = null;
            }
            return byArray;
        }

        @Nullable
        public static String aesDecodeToString(@NotNull JsExtensions this_, @NotNull String str, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
            String string;
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(str, "str");
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(transformation, "transformation");
            Intrinsics.checkNotNullParameter(iv, "iv");
            byte[] byArray = this_.aesDecodeToByteArray(str, key, transformation, iv);
            if (byArray == null) {
                string = null;
            } else {
                byte[] byArray2 = byArray;
                boolean bl = false;
                boolean bl2 = false;
                byte[] it = byArray2;
                boolean bl3 = false;
                boolean bl4 = false;
                string = new String(it, Charsets.UTF_8);
            }
            return string;
        }

        @Nullable
        public static byte[] aesBase64DecodeToByteArray(@NotNull JsExtensions this_, @NotNull String str, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
            byte[] byArray;
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(str, "str");
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(transformation, "transformation");
            Intrinsics.checkNotNullParameter(iv, "iv");
            try {
                byArray = EncoderUtils.INSTANCE.decryptBase64AES(StringsKt.encodeToByteArray(str), StringsKt.encodeToByteArray(key), transformation, StringsKt.encodeToByteArray(iv));
            }
            catch (Exception e) {
                LogUtilsKt.printOnDebug(e);
                String string = e.getLocalizedMessage();
                this_.log(string == null ? "aesDecodeToByteArrayERROR" : string);
                byArray = null;
            }
            return byArray;
        }

        @Nullable
        public static String aesBase64DecodeToString(@NotNull JsExtensions this_, @NotNull String str, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
            String string;
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(str, "str");
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(transformation, "transformation");
            Intrinsics.checkNotNullParameter(iv, "iv");
            byte[] byArray = this_.aesBase64DecodeToByteArray(str, key, transformation, iv);
            if (byArray == null) {
                string = null;
            } else {
                byte[] byArray2 = byArray;
                boolean bl = false;
                boolean bl2 = false;
                byte[] it = byArray2;
                boolean bl3 = false;
                boolean bl4 = false;
                string = new String(it, Charsets.UTF_8);
            }
            return string;
        }

        @Nullable
        public static byte[] aesEncodeToByteArray(@NotNull JsExtensions this_, @NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
            byte[] byArray;
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(transformation, "transformation");
            Intrinsics.checkNotNullParameter(iv, "iv");
            try {
                byArray = EncoderUtils.INSTANCE.encryptAES(StringsKt.encodeToByteArray(data), StringsKt.encodeToByteArray(key), transformation, StringsKt.encodeToByteArray(iv));
            }
            catch (Exception e) {
                LogUtilsKt.printOnDebug(e);
                String string = e.getLocalizedMessage();
                this_.log(string == null ? "aesEncodeToByteArrayERROR" : string);
                byArray = null;
            }
            return byArray;
        }

        @Nullable
        public static String aesEncodeToString(@NotNull JsExtensions this_, @NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
            String string;
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(transformation, "transformation");
            Intrinsics.checkNotNullParameter(iv, "iv");
            byte[] byArray = this_.aesEncodeToByteArray(data, key, transformation, iv);
            if (byArray == null) {
                string = null;
            } else {
                byte[] byArray2 = byArray;
                boolean bl = false;
                boolean bl2 = false;
                byte[] it = byArray2;
                boolean bl3 = false;
                boolean bl4 = false;
                string = new String(it, Charsets.UTF_8);
            }
            return string;
        }

        @Nullable
        public static byte[] aesEncodeToBase64ByteArray(@NotNull JsExtensions this_, @NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
            byte[] byArray;
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(transformation, "transformation");
            Intrinsics.checkNotNullParameter(iv, "iv");
            try {
                byArray = EncoderUtils.INSTANCE.encryptAES2Base64(StringsKt.encodeToByteArray(data), StringsKt.encodeToByteArray(key), transformation, StringsKt.encodeToByteArray(iv));
            }
            catch (Exception e) {
                LogUtilsKt.printOnDebug(e);
                String string = e.getLocalizedMessage();
                this_.log(string == null ? "aesEncodeToBase64ByteArrayERROR" : string);
                byArray = null;
            }
            return byArray;
        }

        @Nullable
        public static String aesEncodeToBase64String(@NotNull JsExtensions this_, @NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
            String string;
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(transformation, "transformation");
            Intrinsics.checkNotNullParameter(iv, "iv");
            byte[] byArray = this_.aesEncodeToBase64ByteArray(data, key, transformation, iv);
            if (byArray == null) {
                string = null;
            } else {
                byte[] byArray2 = byArray;
                boolean bl = false;
                boolean bl2 = false;
                byte[] it = byArray2;
                boolean bl3 = false;
                boolean bl4 = false;
                string = new String(it, Charsets.UTF_8);
            }
            return string;
        }

        @NotNull
        public static String androidId(@NotNull JsExtensions this_) {
            Intrinsics.checkNotNullParameter(this_, "this");
            return "";
        }

        @Nullable
        public static String aesDecodeArgsBase64Str(@NotNull JsExtensions this_, @NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(mode, "mode");
            Intrinsics.checkNotNullParameter(padding, "padding");
            Intrinsics.checkNotNullParameter(iv, "iv");
            return new AES(mode, padding, Base64.decode(key, 2), Base64.decode(iv, 2)).decryptStr(data);
        }

        @Nullable
        public static String tripleDESDecodeStr(@NotNull JsExtensions this_, @NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(mode, "mode");
            Intrinsics.checkNotNullParameter(padding, "padding");
            Intrinsics.checkNotNullParameter(iv, "iv");
            String string = key;
            Charset charset = Charsets.UTF_8;
            boolean bl = false;
            byte[] byArray = string.getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(byArray, "(this as java.lang.String).getBytes(charset)");
            string = iv;
            charset = Charsets.UTF_8;
            bl = false;
            byte[] byArray2 = string.getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(byArray2, "(this as java.lang.String).getBytes(charset)");
            return new DESede(mode, padding, byArray, byArray2).decryptStr(data);
        }

        @Nullable
        public static String tripleDESDecodeArgsBase64Str(@NotNull JsExtensions this_, @NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(mode, "mode");
            Intrinsics.checkNotNullParameter(padding, "padding");
            Intrinsics.checkNotNullParameter(iv, "iv");
            return new DESede(mode, padding, Base64.decode(key, 2), Base64.decode(iv, 2)).decryptStr(data);
        }

        @Nullable
        public static String aesEncodeArgsBase64Str(@NotNull JsExtensions this_, @NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(mode, "mode");
            Intrinsics.checkNotNullParameter(padding, "padding");
            Intrinsics.checkNotNullParameter(iv, "iv");
            return new AES(mode, padding, Base64.decode(key, 2), Base64.decode(iv, 2)).encryptBase64(data);
        }

        @Nullable
        public static String desDecodeToString(@NotNull JsExtensions this_, @NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
            String string;
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(transformation, "transformation");
            Intrinsics.checkNotNullParameter(iv, "iv");
            byte[] byArray = EncoderUtils.INSTANCE.decryptDES(StringsKt.encodeToByteArray(data), StringsKt.encodeToByteArray(key), transformation, StringsKt.encodeToByteArray(iv));
            if (byArray == null) {
                string = null;
            } else {
                byte[] byArray2 = byArray;
                boolean bl = false;
                boolean bl2 = false;
                byte[] it = byArray2;
                boolean bl3 = false;
                boolean bl4 = false;
                string = new String(it, Charsets.UTF_8);
            }
            return string;
        }

        @Nullable
        public static String desBase64DecodeToString(@NotNull JsExtensions this_, @NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
            String string;
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(transformation, "transformation");
            Intrinsics.checkNotNullParameter(iv, "iv");
            byte[] byArray = EncoderUtils.INSTANCE.decryptBase64DES(StringsKt.encodeToByteArray(data), StringsKt.encodeToByteArray(key), transformation, StringsKt.encodeToByteArray(iv));
            if (byArray == null) {
                string = null;
            } else {
                byte[] byArray2 = byArray;
                boolean bl = false;
                boolean bl2 = false;
                byte[] it = byArray2;
                boolean bl3 = false;
                boolean bl4 = false;
                string = new String(it, Charsets.UTF_8);
            }
            return string;
        }

        @Nullable
        public static String desEncodeToString(@NotNull JsExtensions this_, @NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
            String string;
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(transformation, "transformation");
            Intrinsics.checkNotNullParameter(iv, "iv");
            byte[] byArray = EncoderUtils.INSTANCE.encryptDES(StringsKt.encodeToByteArray(data), StringsKt.encodeToByteArray(key), transformation, StringsKt.encodeToByteArray(iv));
            if (byArray == null) {
                string = null;
            } else {
                byte[] byArray2 = byArray;
                boolean bl = false;
                boolean bl2 = false;
                byte[] it = byArray2;
                boolean bl3 = false;
                boolean bl4 = false;
                string = new String(it, Charsets.UTF_8);
            }
            return string;
        }

        @Nullable
        public static String desEncodeToBase64String(@NotNull JsExtensions this_, @NotNull String data, @NotNull String key, @NotNull String transformation, @NotNull String iv) {
            String string;
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(transformation, "transformation");
            Intrinsics.checkNotNullParameter(iv, "iv");
            byte[] byArray = EncoderUtils.INSTANCE.encryptDES2Base64(StringsKt.encodeToByteArray(data), StringsKt.encodeToByteArray(key), transformation, StringsKt.encodeToByteArray(iv));
            if (byArray == null) {
                string = null;
            } else {
                byte[] byArray2 = byArray;
                boolean bl = false;
                boolean bl2 = false;
                byte[] it = byArray2;
                boolean bl3 = false;
                boolean bl4 = false;
                string = new String(it, Charsets.UTF_8);
            }
            return string;
        }

        @Nullable
        public static String tripleDESEncodeBase64Str(@NotNull JsExtensions this_, @NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(mode, "mode");
            Intrinsics.checkNotNullParameter(padding, "padding");
            Intrinsics.checkNotNullParameter(iv, "iv");
            String string = key;
            Charset charset = Charsets.UTF_8;
            boolean bl = false;
            byte[] byArray = string.getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(byArray, "(this as java.lang.String).getBytes(charset)");
            string = iv;
            charset = Charsets.UTF_8;
            bl = false;
            byte[] byArray2 = string.getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(byArray2, "(this as java.lang.String).getBytes(charset)");
            return new DESede(mode, padding, byArray, byArray2).encryptBase64(data);
        }

        @Nullable
        public static String tripleDESEncodeArgsBase64Str(@NotNull JsExtensions this_, @NotNull String data, @NotNull String key, @NotNull String mode, @NotNull String padding, @NotNull String iv) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(mode, "mode");
            Intrinsics.checkNotNullParameter(padding, "padding");
            Intrinsics.checkNotNullParameter(iv, "iv");
            return new DESede(mode, padding, Base64.decode(key, 2), Base64.decode(iv, 2)).encryptBase64(data);
        }

        @Nullable
        public static String digestHex(@NotNull JsExtensions this_, @NotNull String data, @NotNull String algorithm) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(algorithm, "algorithm");
            return DigestUtil.digester(algorithm).digestHex(data);
        }

        @Nullable
        public static String digestBase64Str(@NotNull JsExtensions this_, @NotNull String data, @NotNull String algorithm) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(algorithm, "algorithm");
            return Base64.encodeToString(DigestUtil.digester(algorithm).digest(data), 2);
        }
    }
}

