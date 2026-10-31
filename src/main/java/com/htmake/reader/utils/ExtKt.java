/*
 * Decompiled with CFR 0.152.
 */
package com.htmake.reader.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.htmake.reader.config.AppConfig;
import com.htmake.reader.entity.License;
import com.htmake.reader.entity.MongoFile;
import com.htmake.reader.utils.ExtKt;
import com.htmake.reader.utils.IntTypeAdapter;
import com.htmake.reader.utils.LongTypeAdapter;
import com.htmake.reader.utils.MongoManager;
import com.htmake.reader.utils.SpringContextUtils;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.UpdateResult;
import io.legado.app.data.entities.Book;
import io.legado.app.utils.EncoderUtils;
import io.legado.app.utils.FileUtils;
import io.legado.app.utils.MD5Utils;
import io.legado.app.utils.MapDeserializerDoubleAsIntFix;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.reflect.Array;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import kotlin.reflect.KMutableProperty;
import kotlin.reflect.KProperty1;
import kotlin.reflect.full.KClasses;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import mu.KLogger;
import mu.KotlinLogging;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=2, xi=48, d1={"\u0000\u00a7\u0001\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0010\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\n*\u0001\u000e\u001a\u0012\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010$\u001a\u0004\u0018\u00010%\u001a\u0012\u0010&\u001a\u0004\u0018\u00010'2\b\u0010$\u001a\u0004\u0018\u00010%\u001a\u0016\u0010(\u001a\u00020\u00012\u0006\u0010)\u001a\u00020\u00172\u0006\u0010*\u001a\u00020\u0017\u001a\u000e\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u0017\u001a\u000e\u0010.\u001a\u00020,2\u0006\u0010-\u001a\u00020\u0017\u001a\u0010\u0010/\u001a\u0004\u0018\u00010\u00172\u0006\u00100\u001a\u00020\u0017\u001a\u0010\u00101\u001a\u0004\u0018\u0001022\u0006\u00100\u001a\u00020\u0017\u001a\u000e\u00103\u001a\u00020\u00172\u0006\u00104\u001a\u00020\u0017\u001a\u0016\u00105\u001a\u00020\u00172\u0006\u00106\u001a\u00020\u00172\u0006\u00107\u001a\u00020\u0017\u001a6\u00108\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00010:092\f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u0017092\u0006\u0010<\u001a\u00020\u00172\u0006\u0010=\u001a\u00020\u0017\u001a\u0010\u0010>\u001a\u0002022\b\b\u0002\u0010?\u001a\u00020\u0003\u001a\u000e\u0010@\u001a\n\u0012\u0004\u0012\u00020B\u0018\u00010A\u001a\u000e\u0010C\u001a\u00020\u00172\u0006\u0010D\u001a\u00020\u0001\u001a\u001f\u0010E\u001a\u00020\u00172\u0012\u0010F\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00170G\"\u00020\u0017\u00a2\u0006\u0002\u0010H\u001a+\u0010I\u001a\u0004\u0018\u00010\u00172\u0012\u0010J\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00170G\"\u00020\u00172\b\b\u0002\u0010K\u001a\u00020\u0017\u00a2\u0006\u0002\u0010L\u001a)\u0010M\u001a\u00020,2\u0012\u0010J\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00170G\"\u00020\u00172\b\b\u0002\u0010K\u001a\u00020\u0017\u00a2\u0006\u0002\u0010N\u001a\u0006\u0010O\u001a\u00020\u0017\u001a\u0006\u0010P\u001a\u00020\u0017\u001a\u001f\u0010Q\u001a\u00020\u00172\u0012\u0010F\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00170G\"\u00020\u0017\u00a2\u0006\u0002\u0010H\u001a\u0010\u0010Q\u001a\u00020\u00172\b\b\u0002\u0010R\u001a\u00020\u0017\u001a\u0018\u0010S\u001a\u00020\u00172\u0006\u0010$\u001a\u00020%2\b\b\u0002\u0010T\u001a\u00020\u0003\u001ar\u0010U\u001a\u0004\u0018\u00010#2\u0006\u0010V\u001a\u00020,2\u0010\b\u0002\u0010W\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010X2\u0010\b\u0002\u0010Y\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010X2\b\b\u0002\u0010Z\u001a\u00020\u00012\b\b\u0002\u0010[\u001a\u00020\u00012\u0010\b\u0002\u0010\\\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010X2\u0016\b\u0002\u0010]\u001a\u0010\u0012\u0004\u0012\u00020_\u0012\u0004\u0012\u00020\u0003\u0018\u00010^\u001a!\u0010`\u001a\u0002Ha\"\u0004\b\u0000\u0010a2\u0006\u0010b\u001a\u00020%2\u0006\u0010c\u001a\u00020\u0017\u00a2\u0006\u0002\u0010d\u001a\u0010\u0010e\u001a\u0004\u0018\u00010\u00172\u0006\u0010f\u001a\u00020\u0017\u001a\u0016\u0010g\u001a\u00020\u00032\u0006\u0010f\u001a\u00020\u00172\u0006\u00100\u001a\u00020\u0017\u001a;\u0010h\u001a\u00020i2\u0012\u0010J\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00170G\"\u00020\u00172\u0006\u0010$\u001a\u00020%2\b\b\u0002\u0010T\u001a\u00020\u00032\b\b\u0002\u0010K\u001a\u00020\u0017\u00a2\u0006\u0002\u0010j\u001a\u001e\u0010k\u001a\u00020\u00032\u0006\u0010l\u001a\u00020\u00172\u0006\u0010<\u001a\u00020\u00172\u0006\u0010=\u001a\u00020\u0017\u001a\u001e\u0010m\u001a\u00020i2\u0006\u0010b\u001a\u00020%2\u0006\u0010c\u001a\u00020\u00172\u0006\u0010n\u001a\u00020%\u001a\u000e\u0010o\u001a\u00020i2\u0006\u0010p\u001a\u00020\u0003\u001a\u000e\u0010q\u001a\u00020\u00032\u0006\u0010r\u001a\u00020\u0017\u001a\u001c\u0010s\u001a\u00020\u00032\f\u0010t\u001a\b\u0012\u0004\u0012\u00020,092\u0006\u0010u\u001a\u00020\u0017\u001a)\u0010v\u001a\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010%0%0w\"\u0006\b\u0000\u0010x\u0018\u0001*\b\u0012\u0004\u0012\u0002Hx0wH\u0086\b\u001a \u0010y\u001a\u0002Hz\"\u0004\b\u0000\u0010{\"\u0006\b\u0001\u0010z\u0018\u0001*\u0002H{H\u0086\b\u00a2\u0006\u0002\u0010|\u001a%\u0010}\u001a\b\u0012\u0004\u0012\u00020,09*\u00020,2\u000e\u0010~\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010G\u00a2\u0006\u0002\u0010\u007f\u001a\u000b\u0010\u0080\u0001\u001a\u00020i*\u00020,\u001a&\u0010\u0081\u0001\u001a\u00030\u0082\u0001*\u00030\u0082\u00012\b\u0010\u0083\u0001\u001a\u00030\u0082\u00012\r\u0010\u0084\u0001\u001a\b\u0012\u0004\u0012\u00020\u001709\u001a\u0011\u0010\u0085\u0001\u001a\b\u0012\u0004\u0012\u00020,09*\u00020,\u001a$\u0010\u0086\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020%0\u0087\u0001\"\u0004\b\u0000\u0010x*\u0002Hx\u00a2\u0006\u0003\u0010\u0088\u0001\u001a)\u0010\u0089\u0001\u001a\u0002Hx\"\u0006\b\u0000\u0010x\u0018\u0001*\u000f\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020%0\u0087\u0001H\u0086\b\u00a2\u0006\u0003\u0010\u008a\u0001\u001a\u0016\u0010\u008b\u0001\u001a\u00020\u0017*\u00020\u00172\t\b\u0002\u0010\u008c\u0001\u001a\u00020\u0003\u001a$\u0010\u008d\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020%0\u0087\u0001\"\u0004\b\u0000\u0010x*\u0002Hx\u00a2\u0006\u0003\u0010\u0088\u0001\u001a\u0014\u0010\u008e\u0001\u001a\u00020\u0003*\u00020,2\u0007\u0010\u008f\u0001\u001a\u00020\u0017\u001a\u000b\u0010\u0090\u0001\u001a\u00020\u0017*\u00020\u0017\u001a\u0012\u0010s\u001a\u00020\u0003*\u00020,2\u0006\u0010u\u001a\u00020\u0017\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007\"\u0019\u0010\b\u001a\n \n*\u0004\u0018\u00010\t0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0010\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u000f\"\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0019\u0010\u0014\u001a\n \n*\u0004\u0018\u00010\t0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\f\"\u001a\u0010\u0016\u001a\u00020\u0017X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001b\"\u001a\u0010\u001c\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0005\"\u0004\b\u001e\u0010\u0007\"\u001a\u0010\u001f\u001a\u00020\u0017X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0019\"\u0004\b!\u0010\u001b\u00a8\u0006\u0091\u0001"}, d2={"MAX_CACHE_SIZE", "", "_licenseValid", "", "get_licenseValid", "()Z", "set_licenseValid", "(Z)V", "gson", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "getGson", "()Lcom/google/gson/Gson;", "lockMap", "com/htmake/reader/utils/ExtKt$lockMap$1", "Lcom/htmake/reader/utils/ExtKt$lockMap$1;", "logger", "Lmu/KLogger;", "getLogger", "()Lmu/KLogger;", "prettyGson", "getPrettyGson", "storageFinalPath", "", "getStorageFinalPath", "()Ljava/lang/String;", "setStorageFinalPath", "(Ljava/lang/String;)V", "workDirInit", "getWorkDirInit", "setWorkDirInit", "workDirPath", "getWorkDirPath", "setWorkDirPath", "asJsonArray", "Lio/vertx/core/json/JsonArray;", "value", "", "asJsonObject", "Lio/vertx/core/json/JsonObject;", "countOccurrences", "str", "subStr", "createDir", "Ljava/io/File;", "filePath", "createFile", "decryptData", "content", "decryptToLicense", "Lcom/htmake/reader/entity/License;", "encodeBase64", "text", "genEncryptedPassword", "password", "salt", "getCommand", "", "Lkotlin/Pair;", "to", "subject", "body", "getInstalledLicense", "ignoreInvalid", "getMongoFileStorage", "Lcom/mongodb/client/MongoCollection;", "Lcom/htmake/reader/entity/MongoFile;", "getRandomString", "length", "getRelativePath", "subDirFiles", "", "([Ljava/lang/String;)Ljava/lang/String;", "getStorage", "name", "ext", "([Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", "getStorageFile", "([Ljava/lang/String;Ljava/lang/String;)Ljava/io/File;", "getStoragePath", "getTraceId", "getWorkDir", "subPath", "jsonEncode", "pretty", "parseJsonStringList", "file", "fields", "", "exclude", "startIndex", "endIndex", "checkNotEmpty", "filter", "Lkotlin/Function1;", "Lcom/fasterxml/jackson/databind/node/ObjectNode;", "readInstanceProperty", "R", "instance", "propertyName", "(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;", "readMongoFile", "path", "saveMongoFile", "saveStorage", "", "([Ljava/lang/String;Ljava/lang/Object;ZLjava/lang/String;)V", "sendEmail", "toEmail", "setInstanceProperty", "propertyValue", "setLicenseValid", "isValid", "validateEmail", "email", "zip", "files", "zipFilePath", "arrayType", "Ljava/lang/Class;", "T", "convert", "O", "I", "(Ljava/lang/Object;)Ljava/lang/Object;", "deepListFiles", "allowExtensions", "(Ljava/io/File;[Ljava/lang/String;)Ljava/util/List;", "deleteRecursively", "fillData", "Lio/legado/app/data/entities/Book;", "newBook", "keys", "listFilesRecursively", "serializeToMap", "", "(Ljava/lang/Object;)Ljava/util/Map;", "toDataClass", "(Ljava/util/Map;)Ljava/lang/Object;", "toDir", "absolute", "toMap", "unzip", "descDir", "url", "reader-pro"})
public final class ExtKt {
    @NotNull
    private static final KLogger logger = KotlinLogging.INSTANCE.logger(logger.1.INSTANCE);
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<Map<String, ? extends Object>>(){}.getType(), new MapDeserializerDoubleAsIntFix()).registerTypeAdapter(Integer.TYPE, new IntTypeAdapter()).registerTypeAdapter(Long.TYPE, new LongTypeAdapter()).disableHtmlEscaping().create();
    private static final Gson prettyGson = new GsonBuilder().registerTypeAdapter(new TypeToken<Map<String, ? extends Object>>(){}.getType(), new MapDeserializerDoubleAsIntFix()).registerTypeAdapter(Integer.TYPE, new IntTypeAdapter()).registerTypeAdapter(Long.TYPE, new LongTypeAdapter()).disableHtmlEscaping().setPrettyPrinting().create();
    @NotNull
    private static String storageFinalPath = "";
    @NotNull
    private static String workDirPath = "";
    private static boolean workDirInit;
    private static final int MAX_CACHE_SIZE = 1000;
    @NotNull
    private static final lockMap.1 lockMap;
    private static boolean _licenseValid;

    @NotNull
    public static final KLogger getLogger() {
        return logger;
    }

    public static final Gson getGson() {
        return gson;
    }

    public static final Gson getPrettyGson() {
        return prettyGson;
    }

    @NotNull
    public static final String getStorageFinalPath() {
        return storageFinalPath;
    }

    public static final void setStorageFinalPath(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        storageFinalPath = string;
    }

    @NotNull
    public static final String getWorkDirPath() {
        return workDirPath;
    }

    public static final void setWorkDirPath(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        workDirPath = string;
    }

    public static final boolean getWorkDirInit() {
        return workDirInit;
    }

    public static final void setWorkDirInit(boolean bl) {
        workDirInit = bl;
    }

    @NotNull
    public static final String url(@NotNull String $this$url) {
        Intrinsics.checkNotNullParameter($this$url, "<this>");
        if (StringsKt.startsWith$default($this$url, "//", false, 2, null)) {
            return HttpUrl.Companion.get(Intrinsics.stringPlus("http:", $this$url)).toString();
        }
        if (StringsKt.startsWith$default($this$url, "http", false, 2, null)) {
            return HttpUrl.Companion.get($this$url).toString();
        }
        return $this$url;
    }

    @NotNull
    public static final String toDir(@NotNull String $this$toDir, boolean absolute) {
        Intrinsics.checkNotNullParameter($this$toDir, "<this>");
        String path = $this$toDir;
        if (StringsKt.endsWith$default(path, "/", false, 2, null)) {
            String string = path;
            int n = 0;
            int n2 = path.length() - 1;
            boolean bl = false;
            String string2 = string.substring(n, n2);
            Intrinsics.checkNotNullExpressionValue(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
            path = string2;
        }
        if (absolute && !StringsKt.startsWith$default(path, "/", false, 2, null)) {
            path = Intrinsics.stringPlus("/", path);
        }
        return path;
    }

    public static /* synthetic */ String toDir$default(String string, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            bl = false;
        }
        return ExtKt.toDir(string, bl);
    }

    public static final void deleteRecursively(@NotNull File $this$deleteRecursively) {
        Intrinsics.checkNotNullParameter($this$deleteRecursively, "<this>");
        if ($this$deleteRecursively.exists()) {
            if ($this$deleteRecursively.isFile()) {
                $this$deleteRecursively.delete();
            } else {
                File[] fileArray = $this$deleteRecursively.listFiles();
                Intrinsics.checkNotNullExpressionValue(fileArray, "this.listFiles()");
                Object[] $this$forEach$iv = fileArray;
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    File it = (File)element$iv;
                    boolean bl = false;
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    ExtKt.deleteRecursively(it);
                }
                $this$deleteRecursively.delete();
            }
        }
    }

    @NotNull
    public static final List<File> listFilesRecursively(@NotNull File $this$listFilesRecursively) {
        Intrinsics.checkNotNullParameter($this$listFilesRecursively, "<this>");
        ArrayList<File> list2 = null;
        boolean bl = false;
        list2 = new ArrayList<File>();
        if ($this$listFilesRecursively.exists()) {
            if ($this$listFilesRecursively.isFile()) {
                list2.add($this$listFilesRecursively);
            } else {
                File[] fileArray = $this$listFilesRecursively.listFiles();
                Intrinsics.checkNotNullExpressionValue(fileArray, "this.listFiles()");
                Object[] $this$forEach$iv = fileArray;
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    File it = (File)element$iv;
                    boolean bl2 = false;
                    list2.add(it);
                    if (!it.isDirectory()) continue;
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    list2.addAll((Collection)ExtKt.listFilesRecursively(it));
                }
            }
        }
        return list2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final boolean unzip(@NotNull File $this$unzip, @NotNull String descDir) {
        Intrinsics.checkNotNullParameter($this$unzip, "<this>");
        Intrinsics.checkNotNullParameter(descDir, "descDir");
        if (!$this$unzip.exists()) {
            return false;
        }
        byte[] buffer = new byte[1024];
        OutputStream outputStream2 = null;
        InputStream inputStream2 = null;
        try {
            ZipFile zf = new ZipFile($this$unzip.toString());
            Enumeration<? extends ZipEntry> entries = zf.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                if (zipEntry == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.util.zip.ZipEntry");
                }
                ZipEntry zipEntry2 = zipEntry;
                String string = zipEntry2.getName();
                Intrinsics.checkNotNullExpressionValue(string, "zipEntry.name");
                String zipEntryName = string;
                String descFilePath = descDir + File.separator + zipEntryName;
                if (zipEntry2.isDirectory()) {
                    ExtKt.createDir(descFilePath);
                    continue;
                }
                inputStream2 = zf.getInputStream(zipEntry2);
                File descFile = ExtKt.createFile(descFilePath);
                outputStream2 = new FileOutputStream(descFile);
                int len = 0;
                while (true) {
                    int n = inputStream2.read(buffer);
                    boolean bl = false;
                    boolean bl2 = false;
                    int it = n;
                    boolean bl3 = false;
                    len = it;
                    if (n <= 0) break;
                    ((FileOutputStream)outputStream2).write(buffer, 0, len);
                }
                inputStream2.close();
                ((FileOutputStream)outputStream2).close();
            }
            boolean bl = true;
            return bl;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            InputStream inputStream3 = inputStream2;
            if (inputStream3 != null) {
                inputStream3.close();
            }
            inputStream3 = outputStream2;
            if (inputStream3 != null) {
                ((OutputStream)((Object)inputStream3)).close();
            }
        }
        return false;
    }

    public static final boolean zip(@NotNull File $this$zip, @NotNull String zipFilePath) {
        Intrinsics.checkNotNullParameter($this$zip, "<this>");
        Intrinsics.checkNotNullParameter(zipFilePath, "zipFilePath");
        if (!$this$zip.exists()) {
            return false;
        }
        if ($this$zip.isDirectory()) {
            File[] files = $this$zip.listFiles();
            Intrinsics.checkNotNullExpressionValue(files, "files");
            List<File> filesList = ArraysKt.toList(files);
            return ExtKt.zip(filesList, zipFilePath);
        }
        File[] fileArray = new File[]{$this$zip};
        return ExtKt.zip((List<? extends File>)CollectionsKt.arrayListOf(fileArray), zipFilePath);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final boolean zip(@NotNull List<? extends File> files, @NotNull String zipFilePath) {
        block11: {
            boolean bl;
            ZipOutputStream zipOutputStream;
            block10: {
                FileInputStream fileInputStream;
                Intrinsics.checkNotNullParameter(files, "files");
                Intrinsics.checkNotNullParameter(zipFilePath, "zipFilePath");
                if (files.isEmpty()) {
                    return false;
                }
                File zipFile = ExtKt.createFile(zipFilePath);
                byte[] buffer = new byte[1024];
                zipOutputStream = null;
                FileInputStream inputStream2 = null;
                try {
                    zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
                    for (File closeable : files) {
                        if (!closeable.exists()) continue;
                        zipOutputStream.putNextEntry(new ZipEntry(closeable.getName()));
                        inputStream2 = new FileInputStream(closeable);
                        int len = 0;
                        while (true) {
                            int n = inputStream2.read(buffer);
                            boolean bl2 = false;
                            boolean bl3 = false;
                            int it = n;
                            boolean bl4 = false;
                            len = it;
                            if (n <= 0) break;
                            zipOutputStream.write(buffer, 0, len);
                        }
                        zipOutputStream.closeEntry();
                    }
                    bl = true;
                    fileInputStream = inputStream2;
                    if (fileInputStream == null) break block10;
                }
                catch (Exception e) {
                    e.printStackTrace();
                    break block11;
                }
                finally {
                    Closeable closeable2 = inputStream2;
                    if (closeable2 != null) {
                        ((FileInputStream)closeable2).close();
                    }
                    closeable2 = zipOutputStream;
                    if (closeable2 != null) {
                        ((ZipOutputStream)closeable2).close();
                    }
                }
                fileInputStream.close();
            }
            ZipOutputStream zipOutputStream2 = zipOutputStream;
            zipOutputStream2.close();
            return bl;
        }
        return false;
    }

    @NotNull
    public static final File createDir(@NotNull String filePath) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        logger.debug("createDir filePath {}", (Object)filePath);
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    @NotNull
    public static final File createFile(@NotNull String filePath) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        logger.debug("createFile filePath {}", (Object)filePath);
        File file = new File(filePath);
        File file2 = file.getParentFile();
        Intrinsics.checkNotNull(file2);
        File parentFile = file2;
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /*
     * Unable to fully structure code
     */
    @NotNull
    public static final String getWorkDir(@NotNull String subPath) {
        block7: {
            block8: {
                Intrinsics.checkNotNullParameter(subPath, "subPath");
                if (ExtKt.workDirInit) break block7;
                var1_1 = ExtKt.workDirPath;
                var2_2 = false;
                if (!(var1_1.length() == 0)) break block7;
                appConfig = SpringContextUtils.getBean("appConfig", AppConfig.class);
                if (appConfig != null) {
                    var2_3 = appConfig.getWorkDir();
                    var3_6 = false;
                    if (var2_3.length() > 0 && !appConfig.getWorkDir().equals(".")) {
                        workDirFile = new File(appConfig.getWorkDir());
                        if (workDirFile.exists() && !workDirFile.isDirectory()) {
                            ExtKt.logger.error("reader.app.workDir={} is not a directory", (Object)appConfig.getWorkDir());
                        } else {
                            if (!workDirFile.exists()) {
                                ExtKt.logger.info("reader.app.workDir={} not exists, creating", (Object)appConfig.getWorkDir());
                                workDirFile.mkdirs();
                            }
                            var3_7 = workDirFile.getAbsolutePath();
                            Intrinsics.checkNotNullExpressionValue(var3_7, "workDirFile.absolutePath");
                            ExtKt.workDirPath = var3_7;
                        }
                    }
                }
                workDirFile = ExtKt.workDirPath;
                var3_6 = false;
                if (!(workDirFile.length() == 0)) break block8;
                osName = System.getProperty("os.name");
                currentDir = System.getProperty("user.dir");
                ExtKt.logger.info("osName: {} currentDir: {}", (Object)osName, (Object)currentDir);
                var4_9 = osName;
                Intrinsics.checkNotNullExpressionValue(var4_9, "osName");
                if (!StringsKt.startsWith((String)var4_9, "Mac OS", true)) ** GOTO lbl-1000
                var4_9 = currentDir;
                Intrinsics.checkNotNullExpressionValue(var4_9, "currentDir");
                if (!StringsKt.startsWith$default((String)var4_9, "/Users/", false, 2, null)) {
                    var4_9 = new String[]{".reader"};
                    ExtKt.workDirPath = Paths.get(System.getProperty("user.home"), var4_9).toString();
                } else lbl-1000:
                // 2 sources

                {
                    var4_9 = currentDir;
                    Intrinsics.checkNotNullExpressionValue(var4_9, "currentDir");
                    ExtKt.workDirPath = var4_9;
                }
            }
            ExtKt.logger.info("Using workdir: {}", (Object)ExtKt.workDirPath);
            ExtKt.workDirInit = true;
        }
        var2_5 = new String[]{subPath};
        path = Paths.get(ExtKt.workDirPath, var2_5);
        return path.toString();
    }

    public static /* synthetic */ String getWorkDir$default(String string, int n, Object object) {
        if ((n & 1) != 0) {
            string = "";
        }
        return ExtKt.getWorkDir(string);
    }

    @NotNull
    public static final String getWorkDir(String ... subDirFiles) {
        Intrinsics.checkNotNullParameter(subDirFiles, "subDirFiles");
        return ExtKt.getWorkDir(ExtKt.getRelativePath(Arrays.copyOf(subDirFiles, subDirFiles.length)));
    }

    @NotNull
    public static final String getRelativePath(String ... subDirFiles) {
        String string;
        String string2;
        Intrinsics.checkNotNullParameter(subDirFiles, "subDirFiles");
        StringBuilder path = new StringBuilder("");
        String[] $this$forEach$iv = subDirFiles;
        boolean $i$f$forEach = false;
        String[] stringArray = $this$forEach$iv;
        int n = stringArray.length;
        for (int i = 0; i < n; ++i) {
            String element$iv;
            String it = element$iv = stringArray[i];
            boolean bl = false;
            CharSequence charSequence = it;
            boolean bl2 = false;
            if (!(charSequence.length() > 0)) continue;
            path.append(File.separator).append(it);
        }
        String string3 = path.toString();
        boolean bl = false;
        boolean bl3 = false;
        String it = string3;
        boolean bl4 = false;
        Intrinsics.checkNotNullExpressionValue(it, "it");
        if (StringsKt.startsWith$default(it, "/", false, 2, null)) {
            string2 = it;
            int n2 = 1;
            boolean bl5 = false;
            String string4 = string2.substring(n2);
            string = string4;
            Intrinsics.checkNotNullExpressionValue(string4, "(this as java.lang.String).substring(startIndex)");
        } else {
            string = string2 = it;
        }
        return string;
    }

    @NotNull
    public static final String getStoragePath() {
        CharSequence charSequence = storageFinalPath;
        boolean bl = false;
        if (charSequence.length() > 0) {
            return storageFinalPath;
        }
        String storagePath = "";
        AppConfig appConfig = SpringContextUtils.getBean("appConfig", AppConfig.class);
        if (appConfig != null) {
            storageFinalPath = storagePath = ExtKt.getWorkDir("storage");
        } else {
            String string = new File("storage").getPath();
            Intrinsics.checkNotNullExpressionValue(string, "File(\"storage\").path");
            storagePath = string;
        }
        logger.info("Using storagePath: {}", (Object)storagePath);
        return storagePath;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    public static final void saveStorage(@NotNull String[] name, @NotNull Object value, boolean pretty, @NotNull String ext) {
        Object object;
        Object value$iv;
        String string;
        String string2;
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(ext, "ext");
        if (value instanceof String) {
            string2 = (String)value;
        } else if (value instanceof JsonObject || value instanceof JsonArray) {
            string2 = value.toString();
        } else if (pretty) {
            string = prettyGson.toJson(value);
            Intrinsics.checkNotNullExpressionValue(string, "{\n        prettyGson.toJson(value)\n    }");
            string2 = string;
        } else {
            string = gson.toJson(value);
            Intrinsics.checkNotNullExpressionValue(string, "{\n        gson.toJson(value)\n    }");
            string2 = string;
        }
        String toJson = string2;
        String storagePath = ExtKt.getStoragePath();
        File storageDir = new File(storagePath);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        String filename = ArraysKt.last(name);
        SpreadBuilder spreadBuilder = new SpreadBuilder(2);
        Object object2 = name;
        int n = 0;
        int n2 = name.length - 1;
        boolean bl = false;
        spreadBuilder.addSpread(ArraysKt.copyOfRange(object2, n, n2));
        spreadBuilder.add(Intrinsics.stringPlus(filename, ext));
        String path = ExtKt.getRelativePath((String[])spreadBuilder.toArray(new String[spreadBuilder.size()]));
        File file = new File(storagePath + File.separator + path);
        logger.info("Save file to storage name: {} path: {}", (Object)name, (Object)file.getAbsoluteFile());
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        object2 = file.getAbsoluteFile();
        Intrinsics.checkNotNullExpressionValue(object2, "file.absoluteFile");
        filename = FilesKt.getNameWithoutExtension((File)object2);
        lockMap.1 var11_11 = lockMap;
        n2 = 0;
        bl = false;
        synchronized (var11_11) {
            Object object3;
            void $this$getOrPut$iv22;
            boolean bl2 = false;
            Map map = lockMap;
            String string3 = file.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(string3, "file.absolutePath");
            String key$iv = string3;
            boolean $i$f$getOrPut = false;
            value$iv = $this$getOrPut$iv22.get(key$iv);
            if (value$iv == null) {
                boolean bl3 = false;
                ReadWriteLock answer$iv = new ReentrantReadWriteLock();
                $this$getOrPut$iv22.put(key$iv, answer$iv);
                object3 = answer$iv;
            } else {
                object3 = value$iv;
            }
            object = (ReadWriteLock)object3;
        }
        ReadWriteLock lock = object;
        boolean acquired = false;
        try {
            acquired = lock.writeLock().tryLock(10L, TimeUnit.SECONDS);
            if (!acquired) {
                throw new Exception(Intrinsics.stringPlus("\u4fdd\u5b58\u6587\u4ef6\u8d85\u65f6: ", file.getAbsolutePath()));
            }
            Path tmp = Files.createTempFile(Paths.get(file.getParentFile().getPath(), new String[0]).toAbsolutePath(), filename, ".temp", new FileAttribute[0]);
            object = toJson;
            Charset bl2 = Charsets.UTF_8;
            boolean $this$getOrPut$iv22 = false;
            Object object4 = object;
            if (object4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            byte[] byArray = ((String)object4).getBytes(bl2);
            Intrinsics.checkNotNullExpressionValue(byArray, "(this as java.lang.String).getBytes(charset)");
            Files.write(tmp, byArray, new OpenOption[0]);
            Path filePath = Paths.get(file.getPath(), new String[0]);
            Object[] $this$getOrPut$iv22 = new String[]{Intrinsics.stringPlus(filename, ".backup.json")};
            Path backupPath = Paths.get(file.getParentFile().getPath(), (String[])$this$getOrPut$iv22).toAbsolutePath();
            if (Files.exists(filePath, new LinkOption[0])) {
                $this$getOrPut$iv22 = new CopyOption[]{StandardCopyOption.ATOMIC_MOVE};
                Files.move(filePath, backupPath, (CopyOption[])$this$getOrPut$iv22);
            }
            $this$getOrPut$iv22 = new CopyOption[]{StandardCopyOption.ATOMIC_MOVE};
            Files.move(tmp, filePath, (CopyOption[])$this$getOrPut$iv22);
            Files.deleteIfExists(tmp);
            if (filename.length() >= 32) {
                Files.deleteIfExists(backupPath);
            }
            if ("users".equals(filename)) {
                String md5Encode;
                int userCount = ExtKt.countOccurrences(toJson, "username");
                SpreadBuilder $i$f$getOrPut = new SpreadBuilder(2);
                value$iv = name;
                int n3 = 0;
                int n4 = name.length - 1;
                boolean bl4 = false;
                $i$f$getOrPut.addSpread(ArraysKt.copyOfRange(value$iv, n3, n4));
                $i$f$getOrPut.add('.' + filename + ".key");
                String verifyKeyPath = ExtKt.getRelativePath((String[])$i$f$getOrPut.toArray(new String[$i$f$getOrPut.size()]));
                File verifyKeyFile = new File(storagePath + File.separator + verifyKeyPath);
                if (!verifyKeyFile.exists()) {
                    verifyKeyFile.createNewFile();
                }
                String string4 = md5Encode = MD5Utils.INSTANCE.md5Encode(Intrinsics.stringPlus("userCount=", userCount)).toString();
                n4 = md5Encode.length() - 16;
                bl4 = false;
                String string5 = string4;
                if (string5 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String string6 = string5.substring(n4);
                Intrinsics.checkNotNullExpressionValue(string6, "(this as java.lang.String).substring(startIndex)");
                FilesKt.writeText$default(verifyKeyFile, string6, null, 2, null);
            }
            ExtKt.saveMongoFile(path, toJson);
        }
        catch (Exception e) {
            logger.error("\u4fdd\u5b58\u6587\u4ef6\u5931\u8d25: ", e);
            throw new Exception(Intrinsics.stringPlus("\u4fdd\u5b58\u6587\u4ef6\u5931\u8d25: ", file.getAbsolutePath()));
        }
        finally {
            if (acquired) {
                lock.writeLock().unlock();
            }
        }
    }

    public static /* synthetic */ void saveStorage$default(String[] stringArray, Object object, boolean bl, String string, int n, Object object2) {
        if ((n & 4) != 0) {
            bl = false;
        }
        if ((n & 8) != 0) {
            string = ".json";
        }
        ExtKt.saveStorage(stringArray, object, bl, string);
    }

    @NotNull
    public static final File getStorageFile(@NotNull String[] name, @NotNull String ext) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(ext, "ext");
        String storagePath = ExtKt.getStoragePath();
        File storageDir = new File(storagePath);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        String filename = ArraysKt.last(name);
        SpreadBuilder spreadBuilder = new SpreadBuilder(2);
        String[] stringArray = name;
        int n = 0;
        int n2 = name.length - 1;
        boolean bl = false;
        spreadBuilder.addSpread(ArraysKt.copyOfRange(stringArray, n, n2));
        spreadBuilder.add(Intrinsics.stringPlus(filename, ext));
        String path = ExtKt.getRelativePath((String[])spreadBuilder.toArray(new String[spreadBuilder.size()]));
        return new File(storagePath + File.separator + path);
    }

    public static /* synthetic */ File getStorageFile$default(String[] stringArray, String string, int n, Object object) {
        if ((n & 2) != 0) {
            string = ".json";
        }
        return ExtKt.getStorageFile(stringArray, string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    @Nullable
    public static final String getStorage(@NotNull String[] name, @NotNull String ext) {
        ReadWriteLock readWriteLock;
        int $i$f$getOrPut;
        String[] key$iv;
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(ext, "ext");
        String storagePath = ExtKt.getStoragePath();
        String filename = ArraysKt.last(name);
        SpreadBuilder spreadBuilder = new SpreadBuilder(2);
        Object object = name;
        int n = 0;
        int n2 = name.length - 1;
        boolean bl = false;
        spreadBuilder.addSpread(ArraysKt.copyOfRange(object, n, n2));
        spreadBuilder.add(Intrinsics.stringPlus(filename, ext));
        String path = ExtKt.getRelativePath((String[])spreadBuilder.toArray(new String[spreadBuilder.size()]));
        File file = ExtKt.getStorageFile(Arrays.copyOf(name, name.length), ext);
        logger.info("Read file from storage name: {} path: {}", (Object)name, (Object)file.getAbsoluteFile());
        if (!file.exists()) {
            Object object2;
            object = ExtKt.readMongoFile(path);
            if (object == null) {
                object2 = null;
            } else {
                Object object3 = object;
                n2 = 0;
                bl = false;
                Object content = object3;
                boolean bl2 = false;
                CharSequence charSequence = (CharSequence)content;
                boolean bl3 = false;
                if (charSequence.length() > 0) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                    FilesKt.writeText$default(file, (String)content, null, 2, null);
                }
                object2 = object3;
            }
            return object2;
        }
        lockMap.1 var7_8 = lockMap;
        n2 = 0;
        bl = false;
        synchronized (var7_8) {
            Object object4;
            void $this$getOrPut$iv2;
            boolean bl4 = false;
            Map bl2 = lockMap;
            String string = file.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(string, "file.absolutePath");
            key$iv = string;
            $i$f$getOrPut = 0;
            Object value$iv = $this$getOrPut$iv2.get(key$iv);
            if (value$iv == null) {
                boolean bl5 = false;
                ReadWriteLock answer$iv = new ReentrantReadWriteLock();
                $this$getOrPut$iv2.put(key$iv, answer$iv);
                object4 = answer$iv;
            } else {
                object4 = value$iv;
            }
            readWriteLock = (ReadWriteLock)object4;
        }
        ReadWriteLock lock = readWriteLock;
        String[] content = "";
        boolean acquired = false;
        try {
            acquired = lock.readLock().tryLock(10L, TimeUnit.SECONDS);
            if (!acquired) {
                throw new Exception(Intrinsics.stringPlus("\u8bfb\u53d6\u6587\u4ef6\u8d85\u65f6: ", file.getAbsolutePath()));
            }
            try (FileReader reader = new FileReader(file);){
                content = TextStreamsKt.readText(reader);
                CharSequence bl4 = (CharSequence)content;
                boolean $this$getOrPut$iv2 = false;
                if (bl4.length() == 0) {
                    String[] stringArray;
                    bl4 = ExtKt.readMongoFile(path);
                    if (bl4 == null) {
                        stringArray = content;
                    } else {
                        key$iv = bl4;
                        $i$f$getOrPut = 0;
                        boolean value$iv = false;
                        CharSequence content2 = key$iv;
                        boolean bl6 = false;
                        CharSequence charSequence = content2;
                        boolean bl7 = false;
                        if (charSequence.length() > 0) {
                            if (!file.getParentFile().exists()) {
                                file.getParentFile().mkdirs();
                            }
                            file.createNewFile();
                            FilesKt.writeText$default(file, content2, null, 2, null);
                        }
                        stringArray = $this$getOrPut$iv2 = key$iv;
                    }
                    bl4 = stringArray;
                    return bl4;
                }
                if ("users".equals(filename)) {
                    SpreadBuilder $this$getOrPut$iv2 = new SpreadBuilder(2);
                    key$iv = name;
                    $i$f$getOrPut = 0;
                    int value$iv = name.length - 1;
                    boolean bl8 = false;
                    $this$getOrPut$iv2.addSpread(ArraysKt.copyOfRange(key$iv, $i$f$getOrPut, value$iv));
                    $this$getOrPut$iv2.add('.' + filename + ".key");
                    String verifyKeyPath = ExtKt.getRelativePath((String[])$this$getOrPut$iv2.toArray(new String[$this$getOrPut$iv2.size()]));
                    File verifyKeyFile = new File(storagePath + File.separator + verifyKeyPath);
                    if (verifyKeyFile.exists()) {
                        String md5Encode;
                        String verifyKeyContent = FilesKt.readText$default(verifyKeyFile, null, 1, null);
                        int userCount = ExtKt.countOccurrences((String)content, "username");
                        String string = md5Encode = MD5Utils.INSTANCE.md5Encode(Intrinsics.stringPlus("userCount=", userCount)).toString();
                        int n3 = md5Encode.length() - 16;
                        boolean bl9 = false;
                        String string2 = string;
                        if (string2 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                        }
                        String string3 = string2.substring(n3);
                        Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.String).substring(startIndex)");
                        if (!verifyKeyContent.equals(string3)) {
                            throw new Exception("\u7528\u6237\u6570\u636e\u88ab\u7be1\u6539\uff0c\u8bf7\u8054\u7cfb\u5f00\u53d1\u8005\u4fee\u590d");
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error("\u8bfb\u53d6\u6587\u4ef6\u5931\u8d25: ", e);
            throw new Exception(Intrinsics.stringPlus("\u8bfb\u53d6\u6587\u4ef6\u5931\u8d25: ", file.getAbsolutePath()));
        }
        finally {
            if (acquired) {
                lock.readLock().unlock();
            }
        }
        return content;
    }

    public static /* synthetic */ String getStorage$default(String[] stringArray, String string, int n, Object object) {
        if ((n & 2) != 0) {
            string = ".json";
        }
        return ExtKt.getStorage(stringArray, string);
    }

    @Nullable
    public static final MongoCollection<MongoFile> getMongoFileStorage() {
        AppConfig appConfig = SpringContextUtils.getBean("appConfig", AppConfig.class);
        return MongoManager.INSTANCE.fileStorage(appConfig.getMongoDbName(), "storage");
    }

    @Nullable
    public static final String readMongoFile(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        if (MongoManager.INSTANCE.isInit()) {
            FindIterable<MongoFile> findIterable;
            MongoFile doc;
            logger.info("Get mongoFile {}", (Object)path);
            MongoCollection<MongoFile> mongoCollection = ExtKt.getMongoFileStorage();
            MongoFile mongoFile = mongoCollection == null ? null : (doc = (findIterable = mongoCollection.find(Filters.eq("path", path))) == null ? null : (MongoFile)findIterable.first());
            if (doc != null) {
                return doc.getContent();
            }
        }
        return null;
    }

    public static final boolean saveMongoFile(@NotNull String path, @NotNull String content) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(content, "content");
        if (MongoManager.INSTANCE.isInit()) {
            MongoCollection<MongoFile> result2;
            Object object;
            MongoFile doc;
            logger.info("Save mongoFile {}", (Object)path);
            MongoCollection<MongoFile> mongoCollection = ExtKt.getMongoFileStorage();
            MongoFile mongoFile = mongoCollection == null ? null : (doc = (object = mongoCollection.find(Filters.eq("path", path))) == null ? null : (MongoFile)object.first());
            if (doc != null) {
                doc.setContent(content);
                doc.setUpdated_at(System.currentTimeMillis());
                object = ExtKt.getMongoFileStorage();
                result2 = object == null ? null : object.replaceOne(Filters.eq("path", path), doc, new ReplaceOptions().upsert(true));
                return result2 != null && ((UpdateResult)((Object)result2)).getModifiedCount() > 0L;
            }
            doc = new MongoFile(path, content, 0L, 0L, 12, null);
            try {
                result2 = ExtKt.getMongoFileStorage();
                if (result2 != null) {
                    result2.insertOne(doc);
                }
                return true;
            }
            catch (Exception e) {
                logger.info("Save mongoFile {} failed", (Object)path);
                e.printStackTrace();
            }
        }
        return false;
    }

    public static final int countOccurrences(@NotNull String str, @NotNull String subStr) {
        int index;
        Intrinsics.checkNotNullParameter(str, "str");
        Intrinsics.checkNotNullParameter(subStr, "subStr");
        int count = 0;
        int startIndex = 0;
        while (startIndex < str.length() && (index = StringsKt.indexOf$default((CharSequence)str, subStr, startIndex, false, 4, null)) != -1) {
            int n = count;
            count = n + 1;
            startIndex = index + subStr.length();
        }
        return count;
    }

    @Nullable
    public static final JsonArray asJsonArray(@Nullable Object value) {
        if (value instanceof JsonArray) {
            return (JsonArray)value;
        }
        if (value instanceof String) {
            try {
                return new JsonArray((String)value);
            }
            catch (Exception e) {
                logger.error("\u89e3\u6790\u5185\u5bb9\u51fa\u9519: {}  \u5185\u5bb9: \n{}", (Object)e, value);
                throw e;
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public static final JsonArray parseJsonStringList(@NotNull File file, @Nullable Set<String> fields, @Nullable Set<String> exclude, int startIndex, int endIndex, @Nullable Set<String> checkNotEmpty, @Nullable Function1<? super ObjectNode, Boolean> filter) {
        Intrinsics.checkNotNullParameter(file, "file");
        if (!file.exists()) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonFactory factory = objectMapper.getFactory();
            JsonArray resultList = new JsonArray();
            int currentIndex = 0;
            currentIndex = -1;
            Closeable closeable = factory.createParser(file);
            boolean bl = false;
            boolean bl2 = false;
            Throwable throwable = null;
            try {
                JsonParser parser = (JsonParser)closeable;
                boolean bl3 = false;
                if (parser.nextToken() == JsonToken.START_ARRAY) {
                    while (parser.nextToken() != JsonToken.END_ARRAY) {
                        boolean bl4;
                        if (parser.currentToken() != JsonToken.START_OBJECT) continue;
                        Collection collection = fields;
                        boolean bl5 = false;
                        boolean bl6 = false;
                        if (collection == null || collection.isEmpty()) {
                            if (filter == null) {
                                int n = currentIndex;
                                if ((currentIndex = n + 1) < startIndex) {
                                    parser.skipChildren();
                                    continue;
                                }
                                if (currentIndex > endIndex) break;
                                Object t = parser.readValueAsTree();
                                Intrinsics.checkNotNullExpressionValue(t, "parser.readValueAsTree()");
                                JsonNode jsonNode = (JsonNode)t;
                                ObjectNode objectNode = (ObjectNode)jsonNode;
                                Collection collection2 = exclude;
                                boolean bl7 = false;
                                bl4 = false;
                                if (!(collection2 == null || collection2.isEmpty())) {
                                    Iterable $this$forEach$iv = exclude;
                                    boolean $i$f$forEach = false;
                                    for (Object element$iv : $this$forEach$iv) {
                                        String it = (String)element$iv;
                                        boolean bl8 = false;
                                        objectNode.remove(it);
                                    }
                                }
                                String string = objectNode.toString();
                                Intrinsics.checkNotNullExpressionValue(string, "objectNode.toString()");
                                String jsonString = string;
                                resultList.add(jsonString);
                                continue;
                            }
                            Object objectNode = parser.readValueAsTree();
                            Intrinsics.checkNotNullExpressionValue(objectNode, "parser.readValueAsTree()");
                            JsonNode jsonNode = (JsonNode)objectNode;
                            objectNode = (ObjectNode)jsonNode;
                            if (filter.invoke((ObjectNode)objectNode).booleanValue()) {
                                int jsonString = currentIndex;
                                currentIndex = jsonString + 1;
                            }
                            if (currentIndex < startIndex) continue;
                            if (currentIndex > endIndex) break;
                            String string = ((ObjectNode)objectNode).toString();
                            Intrinsics.checkNotNullExpressionValue(string, "objectNode.toString()");
                            String jsonString = string;
                            resultList.add(jsonString);
                            continue;
                        }
                        int jsonNode = currentIndex;
                        if ((currentIndex = jsonNode + 1) < startIndex) {
                            parser.skipChildren();
                            continue;
                        }
                        if (currentIndex > endIndex) break;
                        JsonObject item = new JsonObject();
                        while (parser.nextToken() != JsonToken.END_OBJECT) {
                            String fieldName = parser.getCurrentName();
                            parser.nextToken();
                            if (fields.contains(fieldName)) {
                                item.put(fieldName, parser.getValueAsString());
                                continue;
                            }
                            if (checkNotEmpty != null && checkNotEmpty.contains(fieldName)) {
                                CharSequence charSequence = parser.getValueAsString();
                                boolean bl9 = false;
                                bl4 = false;
                                item.put(fieldName, !(charSequence == null || charSequence.length() == 0));
                                continue;
                            }
                            parser.skipChildren();
                        }
                        resultList.add(item.toString());
                    }
                }
                parser.close();
                Unit unit = Unit.INSTANCE;
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                CloseableKt.closeFinally(closeable, throwable);
            }
            return resultList;
        }
        catch (Exception e) {
            logger.error("\u89e3\u6790\u6587\u4ef6\u5185\u5bb9\u51fa\u9519: {}  \u6587\u4ef6: \n{}", (Object)e, (Object)file);
            throw e;
        }
    }

    public static /* synthetic */ JsonArray parseJsonStringList$default(File file, Set set, Set set2, int n, int n2, Set set3, Function1 function1, int n3, Object object) {
        if ((n3 & 2) != 0) {
            set = null;
        }
        if ((n3 & 4) != 0) {
            set2 = null;
        }
        if ((n3 & 8) != 0) {
            n = 0;
        }
        if ((n3 & 0x10) != 0) {
            n2 = Integer.MAX_VALUE;
        }
        if ((n3 & 0x20) != 0) {
            set3 = null;
        }
        if ((n3 & 0x40) != 0) {
            function1 = null;
        }
        return ExtKt.parseJsonStringList(file, set, set2, n, n2, set3, function1);
    }

    @Nullable
    public static final JsonObject asJsonObject(@Nullable Object value) {
        if (value instanceof JsonObject) {
            return (JsonObject)value;
        }
        if (value instanceof String) {
            try {
                return new JsonObject((String)value);
            }
            catch (Exception e) {
                logger.error("\u89e3\u6790\u5185\u5bb9\u51fa\u9519: {}  \u5185\u5bb9: \n{}", (Object)e, value);
                throw e;
            }
        }
        return null;
    }

    @NotNull
    public static final <T> Map<String, Object> serializeToMap(T $this$serializeToMap) {
        T $this$convert$iv = $this$serializeToMap;
        boolean $i$f$convert = false;
        String json$iv = $this$convert$iv instanceof String ? (String)$this$convert$iv : ExtKt.getGson().toJson($this$convert$iv);
        return (Map)ExtKt.getGson().fromJson(json$iv, new TypeToken<Map<String, ? extends Object>>(){}.getType());
    }

    @NotNull
    public static final <T> Map<String, Object> toMap(T $this$toMap) {
        T $this$convert$iv = $this$toMap;
        boolean $i$f$convert = false;
        String json$iv = $this$convert$iv instanceof String ? (String)$this$convert$iv : ExtKt.getGson().toJson($this$convert$iv);
        return (Map)ExtKt.getGson().fromJson(json$iv, new TypeToken<Map<String, ? extends Object>>(){}.getType());
    }

    public static final /* synthetic */ <T> T toDataClass(Map<String, ? extends Object> $this$toDataClass) {
        Intrinsics.checkNotNullParameter($this$toDataClass, "<this>");
        boolean $i$f$toDataClass = false;
        Map<String, ? extends Object> $this$convert$iv = $this$toDataClass;
        boolean $i$f$convert = false;
        String json$iv = $this$convert$iv instanceof String ? (String)((Object)$this$convert$iv) : ExtKt.getGson().toJson($this$convert$iv);
        Gson gson2 = ExtKt.getGson();
        Intrinsics.needClassReification();
        return gson2.fromJson(json$iv, new TypeToken<T>(){}.getType());
    }

    public static final /* synthetic */ <I, O> O convert(I $this$convert) {
        boolean $i$f$convert = false;
        String json = $this$convert instanceof String ? (String)$this$convert : ExtKt.getGson().toJson($this$convert);
        Gson gson2 = ExtKt.getGson();
        Intrinsics.needClassReification();
        return (O)gson2.fromJson(json, new TypeToken<O>(){}.getType());
    }

    public static final /* synthetic */ <T> Class<Object> arrayType(Class<T> $this$arrayType) {
        Intrinsics.checkNotNullParameter($this$arrayType, "<this>");
        boolean $i$f$arrayType = false;
        return Array.newInstance($this$arrayType, 0).getClass();
    }

    public static final <R> R readInstanceProperty(@NotNull Object instance, @NotNull String propertyName) {
        Object element$iv2;
        block1: {
            Intrinsics.checkNotNullParameter(instance, "instance");
            Intrinsics.checkNotNullParameter(propertyName, "propertyName");
            Iterable $this$first$iv = KClasses.getMemberProperties(Reflection.getOrCreateKotlinClass(instance.getClass()));
            boolean $i$f$first = false;
            for (Object element$iv2 : $this$first$iv) {
                KProperty1 it = (KProperty1)element$iv2;
                boolean bl = false;
                if (!Intrinsics.areEqual(it.getName(), propertyName)) continue;
                break block1;
            }
            throw (Throwable)new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        KProperty1 property = (KProperty1)element$iv2;
        return (R)property.get(instance);
    }

    /*
     * WARNING - void declaration
     */
    public static final void setInstanceProperty(@NotNull Object instance, @NotNull String propertyName, @NotNull Object propertyValue) {
        Object element$iv2;
        Object[] objectArray;
        block2: {
            void $this$first$iv;
            Intrinsics.checkNotNullParameter(instance, "instance");
            Intrinsics.checkNotNullParameter(propertyName, "propertyName");
            Intrinsics.checkNotNullParameter(propertyValue, "propertyValue");
            objectArray = (Object[])KClasses.getMemberProperties(Reflection.getOrCreateKotlinClass(instance.getClass()));
            boolean $i$f$first = false;
            for (Object element$iv2 : $this$first$iv) {
                KProperty1 it = (KProperty1)element$iv2;
                boolean bl = false;
                if (!Intrinsics.areEqual(it.getName(), propertyName)) continue;
                break block2;
            }
            throw (Throwable)new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        KProperty1 property = (KProperty1)element$iv2;
        if (property instanceof KMutableProperty) {
            objectArray = new Object[]{instance, propertyValue};
            ((KMutableProperty)((Object)property)).getSetter().call(objectArray);
        }
    }

    @NotNull
    public static final Book fillData(@NotNull Book $this$fillData, @NotNull Book newBook2, @NotNull List<String> keys) {
        Intrinsics.checkNotNullParameter($this$fillData, "<this>");
        Intrinsics.checkNotNullParameter(newBook2, "newBook");
        Intrinsics.checkNotNullParameter(keys, "keys");
        List<String> list2 = keys;
        boolean bl = false;
        boolean bl2 = false;
        List<String> it = list2;
        boolean bl3 = false;
        for (String key : it) {
            String current = (String)ExtKt.readInstanceProperty($this$fillData, key);
            CharSequence charSequence = current;
            boolean bl4 = false;
            boolean bl5 = false;
            if (!(charSequence == null || charSequence.length() == 0)) continue;
            String cacheValue = (String)ExtKt.readInstanceProperty(newBook2, key);
            CharSequence charSequence2 = cacheValue;
            bl5 = false;
            boolean bl6 = false;
            if (charSequence2 == null || charSequence2.length() == 0) continue;
            ExtKt.setInstanceProperty($this$fillData, key, cacheValue);
        }
        return $this$fillData;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final String getRandomString(int length) {
        void $this$mapTo$iv$iv;
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789";
        Iterable $this$map$iv = new IntRange(1, length);
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        Iterator iterator2 = $this$mapTo$iv$iv.iterator();
        while (iterator2.hasNext()) {
            int item$iv$iv;
            int n = item$iv$iv = ((IntIterator)iterator2).nextInt();
            Collection collection = destination$iv$iv;
            boolean bl = false;
            CharSequence charSequence = allowedChars;
            boolean bl2 = false;
            Character c = Character.valueOf(StringsKt.random(charSequence, Random.Default));
            collection.add(c);
        }
        return CollectionsKt.joinToString$default((List)destination$iv$iv, "", null, null, 0, null, null, 62, null);
    }

    @NotNull
    public static final String genEncryptedPassword(@NotNull String password, @NotNull String salt) {
        Intrinsics.checkNotNullParameter(password, "password");
        Intrinsics.checkNotNullParameter(salt, "salt");
        return MD5Utils.INSTANCE.md5Encode(Intrinsics.stringPlus(MD5Utils.INSTANCE.md5Encode(Intrinsics.stringPlus(password, salt)), salt)).toString();
    }

    @NotNull
    public static final String jsonEncode(@NotNull Object value, boolean pretty) {
        Intrinsics.checkNotNullParameter(value, "value");
        if (pretty) {
            String string = prettyGson.toJson(value);
            Intrinsics.checkNotNullExpressionValue(string, "prettyGson.toJson(value)");
            return string;
        }
        String string = gson.toJson(value);
        Intrinsics.checkNotNullExpressionValue(string, "gson.toJson(value)");
        return string;
    }

    public static /* synthetic */ String jsonEncode$default(Object object, boolean bl, int n, Object object2) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return ExtKt.jsonEncode(object, bl);
    }

    @NotNull
    public static final List<File> deepListFiles(@NotNull File $this$deepListFiles, @Nullable String[] allowExtensions) {
        Intrinsics.checkNotNullParameter($this$deepListFiles, "<this>");
        boolean bl = false;
        ArrayList<File> fileList = new ArrayList<File>();
        File[] fileArray = $this$deepListFiles.listFiles();
        Intrinsics.checkNotNullExpressionValue(fileArray, "this.listFiles()");
        Object[] $this$forEach$iv = fileArray;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            boolean bl2;
            File it = (File)element$iv;
            boolean bl3 = false;
            if (it.isDirectory()) {
                Intrinsics.checkNotNullExpressionValue(it, "it");
                fileList.addAll((Collection)ExtKt.deepListFiles(it, allowExtensions));
                continue;
            }
            String[] stringArray = it.getName();
            Intrinsics.checkNotNullExpressionValue(stringArray, "it.name");
            String extension = FileUtils.INSTANCE.getExtension((String)stringArray);
            stringArray = allowExtensions;
            if (stringArray == null) {
                bl2 = false;
            } else {
                String[] stringArray2 = stringArray;
                boolean bl4 = false;
                String string = ArraysKt.contentDeepToString(stringArray2);
                bl2 = string == null ? false : StringsKt.contains$default((CharSequence)string, extension, false, 2, null);
            }
            if (!bl2 && allowExtensions != null) continue;
            fileList.add(it);
        }
        return fileList;
    }

    @NotNull
    public static final String getTraceId() {
        return ((Object)UUID.randomUUID().toString().subSequence(0, 8)).toString();
    }

    public static final boolean get_licenseValid() {
        return _licenseValid;
    }

    public static final void set_licenseValid(boolean bl) {
        _licenseValid = bl;
    }

    public static final void setLicenseValid(boolean isValid) {
        _licenseValid = isValid;
    }

    @NotNull
    public static final License getInstalledLicense(boolean ignoreInvalid) {
        Object object = new String[]{"data", "license"};
        String licenseKeyString = ExtKt.getStorage((String[])object, ".key");
        object = licenseKeyString;
        boolean bl = false;
        boolean bl2 = false;
        if (object == null || object.length() == 0) {
            return new License(null, 0, 0L, false, 0L, 0, null, null, null, false, null, 2047, null);
        }
        if (!ignoreInvalid && !_licenseValid) {
            return new License(null, 0, 0L, false, 0L, 0, null, null, null, false, null, 2047, null);
        }
        License license = ExtKt.decryptToLicense(licenseKeyString);
        logger.info("license: {}", (Object)license);
        if (license == null || !license.getVerified()) {
            return new License(null, 0, 0L, false, 0L, 0, null, null, null, false, null, 2047, null);
        }
        return license;
    }

    public static /* synthetic */ License getInstalledLicense$default(boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            bl = false;
        }
        return ExtKt.getInstalledLicense(bl);
    }

    @Nullable
    public static final License decryptToLicense(@NotNull String content) {
        License license;
        Intrinsics.checkNotNullParameter(content, "content");
        CharSequence charSequence = content;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence.length() == 0) {
            return null;
        }
        charSequence = ExtKt.decryptData(content);
        if (charSequence == null) {
            license = null;
        } else {
            CharSequence charSequence2 = charSequence;
            boolean bl3 = false;
            boolean bl4 = false;
            CharSequence it = charSequence2;
            boolean bl5 = false;
            Map<String, Object> $this$toDataClass$iv = ExtKt.toMap(it);
            boolean $i$f$toDataClass = false;
            Map<String, Object> $this$convert$iv$iv = $this$toDataClass$iv;
            boolean $i$f$convert = false;
            String json$iv$iv = $this$convert$iv$iv instanceof String ? (String)((Object)$this$convert$iv$iv) : ExtKt.getGson().toJson($this$convert$iv$iv);
            License license2 = (License)ExtKt.getGson().fromJson(json$iv$iv, new TypeToken<License>(){}.getType());
            license = license2 == null ? null : license2;
        }
        return license;
    }

    @Nullable
    public static final String decryptData(@NotNull String content) {
        Intrinsics.checkNotNullParameter(content, "content");
        String publicKeyString = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj0G3qEPjVTvVd7pXFUVYZFHT8KaoG4onc5rLUKqFQ2DCh/5hFK9t2nKh2XB+C2Jp/GSK2ONwD7ceXenmA6uvr90uCK/gp6j62XFVRvc8sIm0d/bGbzZFJRk3HKtxEckBmASduPObY691DVVixxNtUrSJktx/TZaB42pUQk4j+7FuOVNNPra44hDdnyGhmYBBf2B4kjXVMjL+0NCblFIN1+qjmcol44k6NFKFF54q05bjR3CRyYdAnNTCOyt9va0oB6lDlKHplSZmAOH9JGMUki/HDJbABESXMnyIpux27w9SQ8aJStYttnJWHALO1hiFJsxbz5KUkldH6Ny1p/2W5QIDAQAB";
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(io.legado.app.utils.Base64.decode(publicKeyString, 2)));
        Intrinsics.checkNotNullExpressionValue(publicKey, "publicKey");
        return EncoderUtils.decryptSegmentByPublicKey$default(EncoderUtils.INSTANCE, content, publicKey, 0, 4, null);
    }

    public static final boolean validateEmail(@NotNull String email) {
        Intrinsics.checkNotNullParameter(email, "email");
        Regex regex = new Regex("^[A-Za-z0-9._%+-]+@(163|126|qq|yahoo|sina|sohu|yeah|139|189|21cn|outlook|gmail|icloud).com$");
        return regex.matches(email);
    }

    public static final boolean sendEmail(@NotNull String toEmail, @NotNull String subject, @NotNull String body) {
        Intrinsics.checkNotNullParameter(toEmail, "toEmail");
        Intrinsics.checkNotNullParameter(subject, "subject");
        Intrinsics.checkNotNullParameter(body, "body");
        String host = "smtp.qiye.aliyun.com";
        int port = 465;
        Function3 sendCommand2 = sendEmail.sendCommand.1.INSTANCE;
        try {
            SocketFactory sslSocketFactory = SSLSocketFactory.getDefault();
            Socket socket = sslSocketFactory.createSocket(host, port);
            OutputStream outputStream2 = socket.getOutputStream();
            Intrinsics.checkNotNullExpressionValue(outputStream2, "socket.getOutputStream()");
            OutputStream outputStream3 = outputStream2;
            OutputStreamWriter writer = new OutputStreamWriter(outputStream3);
            InputStream inputStream2 = socket.getInputStream();
            Intrinsics.checkNotNullExpressionValue(inputStream2, "socket.getInputStream()");
            Object object = Charsets.UTF_8;
            boolean bl = false;
            Closeable closeable = inputStream2;
            int n = 0;
            closeable = new InputStreamReader((InputStream)closeable, (Charset)object);
            n = 8192;
            boolean bl2 = false;
            BufferedReader reader = closeable instanceof BufferedReader ? (BufferedReader)closeable : new BufferedReader((Reader)closeable, n);
            String response2 = reader.readLine();
            object = response2;
            Intrinsics.checkNotNullExpressionValue(object, "response");
            if (!StringsKt.startsWith$default((String)object, "220", false, 2, null)) {
                logger.error("Error connecting to the SMTP server.");
                return false;
            }
            String[] stringArray = new String[]{toEmail};
            List<Pair<String, Integer>> commandList = ExtKt.getCommand((List<String>)CollectionsKt.arrayListOf(stringArray), subject, body);
            boolean res = false;
            int n2 = 0;
            n = commandList.size();
            if (n2 < n) {
                int i;
                while ((res = ((Boolean)sendCommand2.invoke(writer, reader, commandList.get(i = n2++))).booleanValue()) && n2 < n) {
                }
            }
            writer.close();
            reader.close();
            socket.close();
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @NotNull
    public static final List<Pair<String, Integer>> getCommand(@NotNull List<String> to, @NotNull String subject, @NotNull String body) {
        Intrinsics.checkNotNullParameter(to, "to");
        Intrinsics.checkNotNullParameter(subject, "subject");
        Intrinsics.checkNotNullParameter(body, "body");
        String username = "no-reply@onmy.top";
        String password = "no-reply@1.";
        String from = "no-reply@onmy.top";
        String fromname = "Reader";
        String separator = "----=_Part_" + System.currentTimeMillis() + UUID.randomUUID();
        Object object = new Pair[]{new Pair<String, Integer>("HELO sendmail\r\n", 250)};
        List<Pair<String, Integer>> command = CollectionsKt.mutableListOf(object);
        object = username;
        boolean bl = false;
        int n = 0;
        if (!(object.length() == 0)) {
            command.add(new Pair<String, Integer>("AUTH LOGIN\r\n", 334));
            command.add(new Pair<String, Integer>(Intrinsics.stringPlus(ExtKt.encodeBase64(username), "\r\n"), 334));
            command.add(new Pair<String, Integer>(Intrinsics.stringPlus(ExtKt.encodeBase64(password), "\r\n"), 235));
        }
        command.add(new Pair<String, Integer>("MAIL FROM: <" + from + ">\r\n", 250));
        String header = "FROM: " + fromname + '<' + from + ">\r\n";
        Collection collection = to;
        n = 0;
        boolean bl2 = false;
        if (!collection.isEmpty()) {
            int count = to.size();
            if (count == 1) {
                command.add(new Pair<String, Integer>("RCPT TO: <" + to.get(0) + ">\r\n", 250));
                header = header + "TO: <" + to.get(0) + ">\r\n";
            } else {
                n = 0;
                if (n < count) {
                    do {
                        int i = n++;
                        command.add(new Pair<String, Integer>("RCPT TO: <" + to.get(i) + ">\r\n", 250));
                        header = i == 0 ? header + "TO: <" + to.get(i) + '>' : (i + 1 == count ? header + ",<" + to.get(i) + ">\r\n" : header + ",<" + to.get(i) + '>');
                    } while (n < count);
                }
            }
        }
        header = header + "Subject: =?UTF-8?B?" + ExtKt.encodeBase64(subject) + "?=\r\n";
        header = Intrinsics.stringPlus(header, "Content-Type: multipart/alternative;\r\n");
        header = header + "\tboundary=\"" + separator + '\"';
        header = Intrinsics.stringPlus(header, "\r\nMIME-Version: 1.0\r\n");
        header = header + "\r\n--" + separator + "\r\n";
        header = Intrinsics.stringPlus(header, "Content-Type:text/html; charset=utf-8\r\n");
        header = Intrinsics.stringPlus(header, "Content-Transfer-Encoding: base64\r\n\r\n");
        header = header + ExtKt.encodeBase64(body) + "\r\n";
        header = header + "--" + separator + "\r\n";
        header = Intrinsics.stringPlus(header, "\r\n.\r\n");
        command.add(new Pair<String, Integer>("DATA\r\n", 354));
        command.add(new Pair<String, Integer>(header, 250));
        command.add(new Pair<String, Integer>("QUIT\r\n", 221));
        return command;
    }

    @NotNull
    public static final String encodeBase64(@NotNull String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        Base64.Encoder encoder = Base64.getEncoder();
        String string = text;
        Charset charset = Charsets.UTF_8;
        boolean bl = false;
        byte[] byArray = string.getBytes(charset);
        Intrinsics.checkNotNullExpressionValue(byArray, "(this as java.lang.String).getBytes(charset)");
        String string2 = encoder.encodeToString(byArray);
        Intrinsics.checkNotNullExpressionValue(string2, "getEncoder().encodeToString(text.toByteArray())");
        return string2;
    }

    static {
        lockMap = new LinkedHashMap<String, ReadWriteLock>(){

            protected boolean removeEldestEntry(@Nullable Map.Entry<String, ReadWriteLock> eldest) {
                return this.size() > 1000;
            }
        };
        _licenseValid = true;
    }
}

