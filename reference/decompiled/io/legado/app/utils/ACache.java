/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.utils;

import io.legado.app.adapters.ReaderAdapterHelper;
import io.legado.app.utils.ACacheKt;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u0000 \u001d2\u00020\u0001:\u0003\u001c\u001d\u001eB\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\r\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0015\u001a\u00020\u000fJ\"\u0010\u0016\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u0007H\u0007J\u0016\u0010\u0016\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0011J\u001e\u0010\u0016\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u0007J\u0016\u0010\u0016\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000fJ\u001e\u0010\u0016\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u0007J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u000e\u001a\u00020\u000fR\u0014\u0010\t\u001a\b\u0018\u00010\nR\u00020\u0000X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2={"Lio/legado/app/utils/ACache;", "", "cacheDir", "Ljava/io/File;", "max_size", "", "max_count", "", "(Ljava/io/File;JI)V", "mCache", "Lio/legado/app/utils/ACache$ACacheManager;", "clear", "", "file", "key", "", "getAsBinary", "", "getAsObject", "getAsString", "getByHashCode", "hashCode", "put", "value", "Ljava/io/Serializable;", "saveTime", "remove", "", "ACacheManager", "Companion", "Utils", "reader-pro"})
public final class ACache {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @Nullable
    private ACacheManager mCache;
    public static final int TIME_HOUR = 3600;
    public static final int TIME_DAY = 86400;
    private static final int MAX_SIZE = 50000000;
    private static final int MAX_COUNT = Integer.MAX_VALUE;
    @NotNull
    private static final HashMap<String, ACache> mInstanceMap = new HashMap();

    private ACache(File cacheDir2, long max_size, int max_count) {
        try {
            if (!cacheDir2.exists() && !cacheDir2.mkdirs()) {
                ACacheKt.access$getLogger$p().info(Intrinsics.stringPlus("ACache can't make dirs in %s", cacheDir2.getAbsolutePath()));
            }
            this.mCache = new ACacheManager(cacheDir2, max_size, max_count);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void put(@NotNull String key, @NotNull String value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        ACacheManager aCacheManager = this.mCache;
        if (aCacheManager != null) {
            ACacheManager aCacheManager2 = aCacheManager;
            boolean bl = false;
            boolean bl2 = false;
            ACacheManager mCache = aCacheManager2;
            boolean bl3 = false;
            try {
                File file = mCache.newFile(key);
                FilesKt.writeText$default(file, value, null, 2, null);
                mCache.put(file);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void put(@NotNull String key, @NotNull String value, int saveTime) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        if (saveTime <= 0) {
            this.put(key, value);
            return;
        }
        this.put(key, Utils.INSTANCE.newStringWithDateInfo(saveTime, value));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public final String getAsString(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        ACacheManager aCacheManager = this.mCache;
        if (aCacheManager != null) {
            boolean removeFile;
            block7: {
                ACacheManager aCacheManager2 = aCacheManager;
                boolean bl = false;
                boolean bl2 = false;
                ACacheManager mCache = aCacheManager2;
                boolean bl3 = false;
                File file = mCache.get(key);
                if (!file.exists()) {
                    return null;
                }
                removeFile = false;
                String text = FilesKt.readText$default(file, null, 1, null);
                if (Utils.INSTANCE.isDue(text)) break block7;
                String string = Utils.INSTANCE.clearDateInfo(text);
                return string;
            }
            try {
                removeFile = true;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            this.remove(key);
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public final String getByHashCode(@NotNull String hashCode) {
        Intrinsics.checkNotNullParameter(hashCode, "hashCode");
        ACacheManager aCacheManager = this.mCache;
        if (aCacheManager != null) {
            boolean removeFile;
            File file;
            block7: {
                ACacheManager aCacheManager2 = aCacheManager;
                boolean bl = false;
                boolean bl2 = false;
                ACacheManager mCache = aCacheManager2;
                boolean bl3 = false;
                file = mCache.newFileFromHashCode(hashCode);
                if (!file.exists()) {
                    return null;
                }
                removeFile = false;
                String text = FilesKt.readText$default(file, null, 1, null);
                if (Utils.INSTANCE.isDue(text)) break block7;
                String string = Utils.INSTANCE.clearDateInfo(text);
                return string;
            }
            try {
                removeFile = true;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            file.delete();
        }
        return null;
    }

    public final void put(@NotNull String key, @NotNull byte[] value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        ACacheManager aCacheManager = this.mCache;
        if (aCacheManager != null) {
            ACacheManager aCacheManager2 = aCacheManager;
            boolean bl = false;
            boolean bl2 = false;
            ACacheManager mCache = aCacheManager2;
            boolean bl3 = false;
            File file = mCache.newFile(key);
            FilesKt.writeBytes(file, value);
            mCache.put(file);
        }
    }

    public final void put(@NotNull String key, @NotNull byte[] value, int saveTime) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        if (saveTime <= 0) {
            this.put(key, value);
            return;
        }
        this.put(key, Utils.INSTANCE.newByteArrayWithDateInfo(saveTime, value));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public final byte[] getAsBinary(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        ACacheManager aCacheManager = this.mCache;
        if (aCacheManager != null) {
            ACacheManager aCacheManager2 = aCacheManager;
            boolean bl = false;
            boolean bl2 = false;
            ACacheManager mCache = aCacheManager2;
            boolean bl3 = false;
            boolean removeFile = false;
            try {
                byte[] byArray;
                File file = mCache.get(key);
                if (!file.exists()) {
                    byte[] byArray2 = null;
                    return byArray2;
                }
                byte[] byteArray2 = FilesKt.readBytes(file);
                if (!Utils.INSTANCE.isDue(byteArray2)) {
                    byArray = Utils.INSTANCE.clearDateInfo(byteArray2);
                } else {
                    removeFile = true;
                    byArray = null;
                }
                byte[] byArray3 = byArray;
                return byArray3;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (removeFile) {
                    this.remove(key);
                }
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @JvmOverloads
    public final void put(@NotNull String key, @NotNull Serializable value, int saveTime) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Closeable closeable = new ObjectOutputStream(byteArrayOutputStream);
            boolean bl = false;
            boolean bl2 = false;
            Throwable throwable = null;
            try {
                ObjectOutputStream oos = (ObjectOutputStream)closeable;
                boolean bl3 = false;
                oos.writeObject(value);
                byte[] data = byteArrayOutputStream.toByteArray();
                if (saveTime != -1) {
                    Intrinsics.checkNotNullExpressionValue(data, "data");
                    this.put(key, data, saveTime);
                } else {
                    Intrinsics.checkNotNullExpressionValue(data, "data");
                    this.put(key, data);
                }
                Unit unit = Unit.INSTANCE;
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                CloseableKt.closeFinally(closeable, throwable);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static /* synthetic */ void put$default(ACache aCache, String string, Serializable serializable, int n, int n2, Object object) {
        if ((n2 & 4) != 0) {
            n = -1;
        }
        aCache.put(string, serializable, n);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @Nullable
    public final Object getAsObject(@NotNull String key) {
        block20: {
            Intrinsics.checkNotNullParameter(key, "key");
            data = this.getAsBinary(key);
            if (data != null) {
                bis = null;
                ois = null;
                bis = new ByteArrayInputStream(data);
                ois = new ObjectInputStream(bis);
                var5_5 = ois.readObject();
                ** try [egrp 1[TRYBLOCK] [0 : 51->63)] { 
lbl-1000:
                // 1 sources

                {
                    var6_11 = bis;
                    var6_11.close();
                }
lbl16:
                // 1 sources

                catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    e = ois;
                    e.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                return var5_5;
                catch (Exception e) {
                    e.printStackTrace();
                    break block20;
                }
                finally {
                    try {
                        e = bis;
                        if (e != null) {
                            e.close();
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        e = ois;
                        if (e != null) {
                            e.close();
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    @Nullable
    public final File file(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        ACacheManager aCacheManager = this.mCache;
        if (aCacheManager != null) {
            ACacheManager aCacheManager2 = aCacheManager;
            boolean bl = false;
            boolean bl2 = false;
            ACacheManager mCache = aCacheManager2;
            boolean bl3 = false;
            try {
                File f = mCache.newFile(key);
                if (f.exists()) {
                    return f;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public final boolean remove(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        ACacheManager aCacheManager = this.mCache;
        return aCacheManager == null ? false : aCacheManager.remove(key);
    }

    public final void clear() {
        ACacheManager aCacheManager = this.mCache;
        if (aCacheManager != null) {
            aCacheManager.clear();
        }
    }

    @JvmOverloads
    public final void put(@NotNull String key, @NotNull Serializable value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        ACache.put$default(this, key, value, 0, 4, null);
    }

    public /* synthetic */ ACache(File cacheDir2, long max_size, int max_count, DefaultConstructorMarker $constructor_marker) {
        this(cacheDir2, max_size, max_count);
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J$\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0004H\u0007J&\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\u0012\u001a\u00020\n2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2={"Lio/legado/app/utils/ACache$Companion;", "", "()V", "MAX_COUNT", "", "MAX_SIZE", "TIME_DAY", "TIME_HOUR", "mInstanceMap", "Ljava/util/HashMap;", "", "Lio/legado/app/utils/ACache;", "get", "cacheDir", "Ljava/io/File;", "maxSize", "", "maxCount", "cacheName", "reader-pro"})
    public static final class Companion {
        private Companion() {
        }

        @JvmOverloads
        @NotNull
        public final ACache get(@NotNull String cacheName, long maxSize, int maxCount) {
            Intrinsics.checkNotNullParameter(cacheName, "cacheName");
            File f = new File(ReaderAdapterHelper.INSTANCE.getAdapter().getCacheDir(), cacheName);
            return this.get(f, maxSize, maxCount);
        }

        public static /* synthetic */ ACache get$default(Companion companion, String string, long l, int n, int n2, Object object) {
            if ((n2 & 1) != 0) {
                string = "ACache";
            }
            if ((n2 & 2) != 0) {
                l = 50000000L;
            }
            if ((n2 & 4) != 0) {
                n = Integer.MAX_VALUE;
            }
            return companion.get(string, l, n);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @JvmOverloads
        @NotNull
        public final ACache get(@NotNull File cacheDir2, long maxSize, int maxCount) {
            Intrinsics.checkNotNullParameter(cacheDir2, "cacheDir");
            boolean bl = false;
            boolean bl2 = false;
            synchronized (this) {
                boolean bl3 = false;
                ACache manager = (ACache)mInstanceMap.get(cacheDir2.getAbsoluteFile().toString());
                if (manager == null) {
                    manager = new ACache(cacheDir2, maxSize, maxCount, null);
                    Map map = mInstanceMap;
                    String string = cacheDir2.getAbsolutePath();
                    Intrinsics.checkNotNullExpressionValue(string, "cacheDir.absolutePath");
                    boolean bl4 = false;
                    map.put(string, manager);
                }
                ACache aCache = manager;
                return aCache;
            }
        }

        public static /* synthetic */ ACache get$default(Companion companion, File file, long l, int n, int n2, Object object) {
            if ((n2 & 2) != 0) {
                l = 50000000L;
            }
            if ((n2 & 4) != 0) {
                n = Integer.MAX_VALUE;
            }
            return companion.get(file, l, n);
        }

        @JvmOverloads
        @NotNull
        public final ACache get(@NotNull String cacheName, long maxSize) {
            Intrinsics.checkNotNullParameter(cacheName, "cacheName");
            return io.legado.app.utils.ACache$Companion.get$default(this, cacheName, maxSize, 0, 4, null);
        }

        @JvmOverloads
        @NotNull
        public final ACache get(@NotNull String cacheName) {
            Intrinsics.checkNotNullParameter(cacheName, "cacheName");
            return io.legado.app.utils.ACache$Companion.get$default(this, cacheName, 0L, 0, 6, null);
        }

        @JvmOverloads
        @NotNull
        public final ACache get() {
            return io.legado.app.utils.ACache$Companion.get$default(this, null, 0L, 0, 7, null);
        }

        @JvmOverloads
        @NotNull
        public final ACache get(@NotNull File cacheDir2, long maxSize) {
            Intrinsics.checkNotNullParameter(cacheDir2, "cacheDir");
            return io.legado.app.utils.ACache$Companion.get$default(this, cacheDir2, maxSize, 0, 4, null);
        }

        @JvmOverloads
        @NotNull
        public final ACache get(@NotNull File cacheDir2) {
            Intrinsics.checkNotNullParameter(cacheDir2, "cacheDir");
            return io.legado.app.utils.ACache$Companion.get$default(this, cacheDir2, 0L, 0, 6, null);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\b\u00c2\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\bJ \u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0002J\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\rH\u0002J\u001b\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00122\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0013J\u0010\u0010\u0014\u001a\u00020\u00152\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006J\u0018\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0004H\u0002J\u000e\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0007\u001a\u00020\u0006J\u000e\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\bJ\u0016\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u0006J\u0016\u0010\u001c\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2={"Lio/legado/app/utils/ACache$Utils;", "", "()V", "mSeparator", "", "clearDateInfo", "", "data", "", "strInfo", "copyOfRange", "original", "from", "", "to", "createDateInfo", "second", "getDateInfoFromDate", "", "([B)[Ljava/lang/String;", "hasDateInfo", "", "indexOf", "c", "isDue", "str", "newByteArrayWithDateInfo", "data2", "newStringWithDateInfo", "reader-pro"})
    private static final class Utils {
        @NotNull
        public static final Utils INSTANCE = new Utils();
        private static final char mSeparator = ' ';

        private Utils() {
        }

        public final boolean isDue(@NotNull String str) {
            Intrinsics.checkNotNullParameter(str, "str");
            String string = str;
            Charset charset = Charsets.UTF_8;
            boolean bl = false;
            byte[] byArray = string.getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(byArray, "(this as java.lang.String).getBytes(charset)");
            return this.isDue(byArray);
        }

        public final boolean isDue(@NotNull byte[] data) {
            Intrinsics.checkNotNullParameter(data, "data");
            try {
                String[] text = this.getDateInfoFromDate(data);
                if (text != null && text.length == 2) {
                    String saveTimeStr = text[0];
                    while (StringsKt.startsWith$default(saveTimeStr, "0", false, 2, null)) {
                        String string = saveTimeStr;
                        int n = 1;
                        boolean bl = false;
                        String string2 = string;
                        if (string2 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                        }
                        Intrinsics.checkNotNullExpressionValue(string2.substring(n), "(this as java.lang.String).substring(startIndex)");
                    }
                    Long saveTime = Long.valueOf(saveTimeStr);
                    Long deleteAfter = Long.valueOf(text[1]);
                    if (System.currentTimeMillis() > saveTime + deleteAfter * (long)1000) {
                        return true;
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @NotNull
        public final String newStringWithDateInfo(int second, @NotNull String strInfo) {
            Intrinsics.checkNotNullParameter(strInfo, "strInfo");
            return Intrinsics.stringPlus(this.createDateInfo(second), strInfo);
        }

        @NotNull
        public final byte[] newByteArrayWithDateInfo(int second, @NotNull byte[] data2) {
            Intrinsics.checkNotNullParameter(data2, "data2");
            String string = this.createDateInfo(second);
            Charset charset = Charsets.UTF_8;
            boolean bl = false;
            String string2 = string;
            if (string2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            byte[] byArray = string2.getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(byArray, "(this as java.lang.String).getBytes(charset)");
            byte[] data1 = byArray;
            byte[] retData = new byte[data1.length + data2.length];
            System.arraycopy(data1, 0, retData, 0, data1.length);
            System.arraycopy(data2, 0, retData, data1.length, data2.length);
            return retData;
        }

        @Nullable
        public final String clearDateInfo(@Nullable String strInfo) {
            String string = strInfo;
            if (string != null) {
                String string2 = string;
                boolean bl = false;
                boolean bl2 = false;
                String it = string2;
                boolean bl3 = false;
                String string3 = strInfo;
                Charset charset = Charsets.UTF_8;
                boolean bl4 = false;
                String string4 = string3;
                if (string4 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                byte[] byArray = string4.getBytes(charset);
                Intrinsics.checkNotNullExpressionValue(byArray, "(this as java.lang.String).getBytes(charset)");
                if (INSTANCE.hasDateInfo(byArray)) {
                    string3 = strInfo;
                    int n = StringsKt.indexOf$default((CharSequence)strInfo, ' ', 0, false, 6, null) + 1;
                    bl4 = false;
                    String string5 = string3;
                    if (string5 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    String string6 = string5.substring(n);
                    Intrinsics.checkNotNullExpressionValue(string6, "(this as java.lang.String).substring(startIndex)");
                    return string6;
                }
            }
            return strInfo;
        }

        @NotNull
        public final byte[] clearDateInfo(@NotNull byte[] data) {
            Intrinsics.checkNotNullParameter(data, "data");
            return this.hasDateInfo(data) ? this.copyOfRange(data, this.indexOf(data, ' ') + 1, data.length) : data;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public final boolean hasDateInfo(@Nullable byte[] data) {
            if (data == null) return false;
            if (data.length <= 15) return false;
            int n = 45;
            boolean bl = false;
            if (data[13] != (byte)n) return false;
            if (this.indexOf(data, ' ') <= 14) return false;
            return true;
        }

        @Nullable
        public final String[] getDateInfoFromDate(@NotNull byte[] data) {
            Intrinsics.checkNotNullParameter(data, "data");
            if (this.hasDateInfo(data)) {
                byte[] byArray = this.copyOfRange(data, 0, 13);
                boolean bl = false;
                String saveDate = new String(byArray, Charsets.UTF_8);
                Object[] objectArray = this.copyOfRange(data, 14, this.indexOf(data, ' '));
                boolean bl2 = false;
                String deleteAfter = new String((byte[])objectArray, Charsets.UTF_8);
                objectArray = new String[2];
                objectArray[0] = (byte)saveDate;
                objectArray[1] = (byte)deleteAfter;
                return objectArray;
            }
            return null;
        }

        private final int indexOf(byte[] data, char c) {
            int n = 0;
            int n2 = data.length + -1;
            if (n <= n2) {
                do {
                    int i = n++;
                    char c2 = c;
                    boolean bl = false;
                    if (data[i] != (byte)c2) continue;
                    return i;
                } while (n <= n2);
            }
            return -1;
        }

        private final byte[] copyOfRange(byte[] original, int from, int to) {
            int newLength = to - from;
            boolean bl = newLength >= 0;
            int n = 0;
            boolean bl2 = false;
            if (!bl) {
                boolean bl3 = false;
                String string = from + " > " + to;
                throw (Throwable)new IllegalArgumentException(string.toString());
            }
            byte[] copy = new byte[newLength];
            n = original.length - from;
            bl2 = false;
            System.arraycopy(original, from, copy, 0, Math.min(n, newLength));
            return copy;
        }

        private final String createDateInfo(int second) {
            StringBuilder currentTime = new StringBuilder(System.currentTimeMillis() + "");
            while (currentTime.length() < 13) {
                currentTime.insert(0, "0");
            }
            return "" + currentTime + '-' + second + ' ';
        }
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0096\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0003H\u0002J\u0006\u0010\u0015\u001a\u00020\u0012J\u0011\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0018H\u0086\u0002J\u000e\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u0018J\u000e\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0003J\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0017\u001a\u00020\u0018J\b\u0010\u001f\u001a\u00020\u0005H\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000RN\u0010\r\u001aB\u0012\f\u0012\n \u000f*\u0004\u0018\u00010\u00030\u0003\u0012\f\u0012\n \u000f*\u0004\u0018\u00010\u00050\u0005 \u000f* \u0012\f\u0012\n \u000f*\u0004\u0018\u00010\u00030\u0003\u0012\f\u0012\n \u000f*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00100\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2={"Lio/legado/app/utils/ACache$ACacheManager;", "", "cacheDir", "Ljava/io/File;", "sizeLimit", "", "countLimit", "", "(Lio/legado/app/utils/ACache;Ljava/io/File;JI)V", "cacheCount", "Ljava/util/concurrent/atomic/AtomicInteger;", "cacheSize", "Ljava/util/concurrent/atomic/AtomicLong;", "lastUsageDates", "", "kotlin.jvm.PlatformType", "", "calculateCacheSizeAndCacheCount", "", "calculateSize", "file", "clear", "get", "key", "", "newFile", "newFileFromHashCode", "hashCode", "put", "remove", "", "removeNext", "reader-pro"})
    public class ACacheManager {
        @NotNull
        private File cacheDir;
        private final long sizeLimit;
        private final int countLimit;
        @NotNull
        private final AtomicLong cacheSize;
        @NotNull
        private final AtomicInteger cacheCount;
        private final Map<File, Long> lastUsageDates;

        public ACacheManager(File cacheDir2, long sizeLimit, int countLimit) {
            Intrinsics.checkNotNullParameter(ACache.this, "this$0");
            Intrinsics.checkNotNullParameter(cacheDir2, "cacheDir");
            this.cacheDir = cacheDir2;
            this.sizeLimit = sizeLimit;
            this.countLimit = countLimit;
            this.cacheSize = new AtomicLong();
            this.cacheCount = new AtomicInteger();
            this.lastUsageDates = Collections.synchronizedMap(new HashMap());
            this.calculateCacheSizeAndCacheCount();
        }

        private final void calculateCacheSizeAndCacheCount() {
            new Thread(() -> ACacheManager.calculateCacheSizeAndCacheCount$lambda-0(this)).start();
        }

        public final void put(@NotNull File file) {
            Intrinsics.checkNotNullParameter(file, "file");
            try {
                int curCacheCount = this.cacheCount.get();
                while (curCacheCount + 1 > this.countLimit) {
                    long freedSize = this.removeNext();
                    this.cacheSize.addAndGet(-freedSize);
                    curCacheCount = this.cacheCount.addAndGet(-1);
                }
                this.cacheCount.addAndGet(1);
                long valueSize = this.calculateSize(file);
                long curCacheSize = this.cacheSize.get();
                while (curCacheSize + valueSize > this.sizeLimit) {
                    long freedSize = this.removeNext();
                    curCacheSize = this.cacheSize.addAndGet(-freedSize);
                }
                this.cacheSize.addAndGet(valueSize);
                long currentTime = System.currentTimeMillis();
                file.setLastModified(currentTime);
                Map<File, Long> map = this.lastUsageDates;
                Intrinsics.checkNotNullExpressionValue(map, "lastUsageDates");
                Long l = currentTime;
                boolean bl = false;
                map.put(file, l);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        @NotNull
        public final File get(@NotNull String key) {
            Intrinsics.checkNotNullParameter(key, "key");
            File file = this.newFile(key);
            long currentTime = System.currentTimeMillis();
            file.setLastModified(currentTime);
            Map<File, Long> map = this.lastUsageDates;
            Intrinsics.checkNotNullExpressionValue(map, "lastUsageDates");
            Long l = currentTime;
            boolean bl = false;
            map.put(file, l);
            return file;
        }

        @NotNull
        public final File newFile(@NotNull String key) {
            Intrinsics.checkNotNullParameter(key, "key");
            return new File(this.cacheDir, key.hashCode() + "");
        }

        @NotNull
        public final File newFileFromHashCode(@NotNull String hashCode) {
            Intrinsics.checkNotNullParameter(hashCode, "hashCode");
            return new File(this.cacheDir, hashCode);
        }

        public final boolean remove(@NotNull String key) {
            Intrinsics.checkNotNullParameter(key, "key");
            File image = this.get(key);
            return image.delete();
        }

        public final void clear() {
            try {
                this.lastUsageDates.clear();
                this.cacheSize.set(0L);
                File[] files = this.cacheDir.listFiles();
                if (files != null) {
                    for (File f : files) {
                        f.delete();
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private final long removeNext() {
            try {
                if (this.lastUsageDates.isEmpty()) {
                    return 0L;
                }
                Long oldestUsage = null;
                File mostLongUsedFile = null;
                Set<Map.Entry<File, Long>> entries = this.lastUsageDates.entrySet();
                Map<File, Long> map = this.lastUsageDates;
                Intrinsics.checkNotNullExpressionValue(map, "lastUsageDates");
                boolean bl = false;
                boolean bl2 = false;
                synchronized (map) {
                    boolean bl3 = false;
                    Iterator<Map.Entry<File, Long>> iterator2 = entries.iterator();
                    while (iterator2.hasNext()) {
                        Map.Entry<File, Long> entry;
                        Map.Entry<File, Long> entry2 = entry = iterator2.next();
                        boolean bl4 = false;
                        File key = entry2.getKey();
                        Map.Entry<File, Long> entry3 = entry;
                        boolean bl5 = false;
                        Long lastValueUsage = entry3.getValue();
                        if (mostLongUsedFile == null) {
                            mostLongUsedFile = key;
                            oldestUsage = lastValueUsage;
                            continue;
                        }
                        Intrinsics.checkNotNullExpressionValue(lastValueUsage, "lastValueUsage");
                        long l = lastValueUsage;
                        Long l2 = oldestUsage;
                        Intrinsics.checkNotNull(l2);
                        if (l >= ((Number)l2).longValue()) continue;
                        oldestUsage = lastValueUsage;
                        mostLongUsedFile = key;
                    }
                    Unit unit = Unit.INSTANCE;
                }
                long fileSize = 0L;
                if (mostLongUsedFile != null) {
                    fileSize = this.calculateSize(mostLongUsedFile);
                    if (mostLongUsedFile.delete()) {
                        this.lastUsageDates.remove(mostLongUsedFile);
                    }
                }
                return fileSize;
            }
            catch (Exception e) {
                e.printStackTrace();
                return 0L;
            }
        }

        private final long calculateSize(File file) {
            return file.length();
        }

        private static final void calculateCacheSizeAndCacheCount$lambda-0(ACacheManager this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            try {
                int size = 0;
                int count = 0;
                File[] cachedFiles = this$0.cacheDir.listFiles();
                if (cachedFiles != null) {
                    File[] fileArray = cachedFiles;
                    int n = 0;
                    int n2 = fileArray.length;
                    while (n < n2) {
                        File cachedFile = fileArray[n];
                        ++n;
                        Intrinsics.checkNotNullExpressionValue(cachedFile, "cachedFile");
                        size += (int)this$0.calculateSize(cachedFile);
                        ++count;
                        Map<File, Long> map = this$0.lastUsageDates;
                        Intrinsics.checkNotNullExpressionValue(map, "lastUsageDates");
                        Long l = cachedFile.lastModified();
                        boolean bl = false;
                        map.put(cachedFile, l);
                    }
                    this$0.cacheSize.set(size);
                    this$0.cacheCount.set(count);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

