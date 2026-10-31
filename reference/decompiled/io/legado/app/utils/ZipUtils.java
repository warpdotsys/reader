/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.utils;

import io.legado.app.utils.ZipUtilsKt;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u0012\u0010\u0007\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u0018\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006J\u0016\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\u0006\u0010\f\u001a\u00020\nJ\u0012\u0010\r\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000e\u001a\u00020\nH\u0002J\u0018\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006J\u0016\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\u0006\u0010\f\u001a\u00020\nJ\u0012\u0010\u0010\u001a\u00020\u00042\b\u0010\u0011\u001a\u0004\u0018\u00010\nH\u0002J6\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00062\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\nH\u0002J\u001e\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\t2\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0006J\u001e\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\t2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\nJ,\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\t2\b\u0010\u000b\u001a\u0004\u0018\u00010\u00062\b\u0010\u0013\u001a\u0004\u0018\u00010\u00062\b\u0010\u001e\u001a\u0004\u0018\u00010\nJ(\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\t2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\n2\b\u0010\u001e\u001a\u0004\u0018\u00010\nJ*\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\n2\u0006\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\nH\u0002J(\u0010\u000b\u001a\u00020\u00042\b\u0010\u001f\u001a\u0004\u0018\u00010\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\nH\u0007J\u0016\u0010\u000b\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nJ\u001e\u0010\u000b\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010#\u001a\u00020\nJ.\u0010%\u001a\u00020\u00042\u000e\u0010&\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010'2\b\u0010\u000b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\nH\u0007J'\u0010%\u001a\u00020\u00042\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\n0'2\u0006\u0010\f\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(J5\u0010%\u001a\u00020\u00042\u000e\u0010)\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010'2\b\u0010\f\u001a\u0004\u0018\u00010\n2\b\u0010#\u001a\u0004\u0018\u00010\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010*\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006+"}, d2={"Lio/legado/app/utils/ZipUtils;", "", "()V", "createOrExistsDir", "", "file", "Ljava/io/File;", "createOrExistsFile", "getComments", "", "", "zipFile", "zipFilePath", "getFileByPath", "filePath", "getFilesPath", "isSpace", "s", "unzipChildFile", "destDir", "files", "", "zip", "Ljava/util/zip/ZipFile;", "entry", "Ljava/util/zip/ZipEntry;", "name", "unzipFile", "destDirPath", "unzipFileByKeyword", "keyword", "srcFile", "rootPath", "zos", "Ljava/util/zip/ZipOutputStream;", "comment", "srcFilePath", "zipFiles", "srcFiles", "", "(Ljava/util/Collection;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "srcFilePaths", "(Ljava/util/Collection;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "reader-pro"})
public final class ZipUtils {
    @NotNull
    public static final ZipUtils INSTANCE = new ZipUtils();

    private ZipUtils() {
    }

    @Nullable
    public final Object zipFiles(@NotNull Collection<String> srcFiles, @NotNull String zipFilePath, @NotNull Continuation<? super Boolean> $completion) {
        return this.zipFiles(srcFiles, zipFilePath, null, $completion);
    }

    @Nullable
    public final Object zipFiles(@Nullable Collection<String> srcFilePaths, @Nullable String zipFilePath, @Nullable String comment, @NotNull Continuation<? super Boolean> $completion) {
        return BuildersKt.withContext(Dispatchers.getIO(), (Function2)new Function2<CoroutineScope, Continuation<? super Boolean>, Object>(srcFilePaths, zipFilePath, comment, null){
            int label;
            final /* synthetic */ Collection<String> $srcFilePaths;
            final /* synthetic */ String $zipFilePath;
            final /* synthetic */ String $comment;
            {
                this.$srcFilePaths = $srcFilePaths;
                this.$zipFilePath = $zipFilePath;
                this.$comment = $comment;
                super(2, $completion);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Nullable
            public final Object invokeSuspend(@NotNull Object object) {
                Object object2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0: {
                        ResultKt.throwOnFailure(object);
                        if (this.$srcFilePaths == null || this.$zipFilePath == null) {
                            return Boxing.boxBoolean(false);
                        }
                        Closeable closeable = new ZipOutputStream(new FileOutputStream(this.$zipFilePath));
                        Collection<String> collection = this.$srcFilePaths;
                        String string = this.$comment;
                        boolean bl = false;
                        boolean bl2 = false;
                        Throwable throwable = null;
                        try {
                            ZipOutputStream it = (ZipOutputStream)closeable;
                            boolean bl3 = false;
                            for (String srcFile : collection) {
                                File file = ZipUtils.access$getFileByPath(ZipUtils.INSTANCE, srcFile);
                                Intrinsics.checkNotNull(file);
                                if (ZipUtils.access$zipFile(ZipUtils.INSTANCE, file, "", it, string)) continue;
                                Boolean bl4 = Boxing.boxBoolean(false);
                                return bl4;
                            }
                            Boolean bl5 = Boxing.boxBoolean(true);
                            return bl5;
                        }
                        catch (Throwable throwable2) {
                            throwable = throwable2;
                            throw throwable2;
                        }
                        finally {
                            CloseableKt.closeFinally(closeable, throwable);
                        }
                    }
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            @NotNull
            public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                return (Continuation)((Object)new /* invalid duplicate definition of identical inner class */);
            }

            @Nullable
            public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Boolean> p2) {
                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
            }
        }, $completion);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @JvmOverloads
    public final boolean zipFiles(@Nullable Collection<? extends File> srcFiles, @Nullable File zipFile, @Nullable String comment) throws IOException {
        if (srcFiles == null || zipFile == null) {
            return false;
        }
        Closeable closeable = new ZipOutputStream(new FileOutputStream(zipFile));
        boolean bl = false;
        boolean bl2 = false;
        Throwable throwable = null;
        try {
            ZipOutputStream it = (ZipOutputStream)closeable;
            boolean bl3 = false;
            for (File file : srcFiles) {
                if (INSTANCE.zipFile(file, "", it, comment)) continue;
                boolean bl4 = false;
                return bl4;
            }
            boolean bl5 = true;
            return bl5;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally(closeable, throwable);
        }
    }

    public static /* synthetic */ boolean zipFiles$default(ZipUtils zipUtils, Collection collection, File file, String string, int n, Object object) throws IOException {
        if ((n & 4) != 0) {
            string = null;
        }
        return zipUtils.zipFiles(collection, file, string);
    }

    public final boolean zipFile(@NotNull String srcFilePath, @NotNull String zipFilePath) throws IOException {
        Intrinsics.checkNotNullParameter(srcFilePath, "srcFilePath");
        Intrinsics.checkNotNullParameter(zipFilePath, "zipFilePath");
        return this.zipFile(this.getFileByPath(srcFilePath), this.getFileByPath(zipFilePath), null);
    }

    public final boolean zipFile(@NotNull String srcFilePath, @NotNull String zipFilePath, @NotNull String comment) throws IOException {
        Intrinsics.checkNotNullParameter(srcFilePath, "srcFilePath");
        Intrinsics.checkNotNullParameter(zipFilePath, "zipFilePath");
        Intrinsics.checkNotNullParameter(comment, "comment");
        return this.zipFile(this.getFileByPath(srcFilePath), this.getFileByPath(zipFilePath), comment);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @JvmOverloads
    public final boolean zipFile(@Nullable File srcFile, @Nullable File zipFile, @Nullable String comment) throws IOException {
        if (srcFile == null || zipFile == null) {
            return false;
        }
        Closeable closeable = new ZipOutputStream(new FileOutputStream(zipFile));
        boolean bl = false;
        boolean bl2 = false;
        Throwable throwable = null;
        try {
            ZipOutputStream zos = (ZipOutputStream)closeable;
            boolean bl3 = false;
            boolean bl4 = INSTANCE.zipFile(srcFile, "", zos, comment);
            return bl4;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally(closeable, throwable);
        }
    }

    public static /* synthetic */ boolean zipFile$default(ZipUtils zipUtils, File file, File file2, String string, int n, Object object) throws IOException {
        if ((n & 4) != 0) {
            string = null;
        }
        return zipUtils.zipFile(file, file2, string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final boolean zipFile(File srcFile, String rootPath, ZipOutputStream zos, String comment) throws IOException {
        String rootPath1;
        block10: {
            File file;
            int n;
            File[] fileArray;
            File[] fileList;
            block12: {
                block11: {
                    rootPath1 = null;
                    rootPath1 = rootPath;
                    if (!srcFile.exists()) {
                        return true;
                    }
                    rootPath1 = rootPath1 + (this.isSpace(rootPath1) ? "" : File.separator) + srcFile.getName();
                    if (!srcFile.isDirectory()) break block10;
                    fileList = srcFile.listFiles();
                    if (fileList == null) break block11;
                    fileArray = fileList;
                    n = 0;
                    if (!(fileArray.length == 0)) break block12;
                }
                ZipEntry entry = new ZipEntry(Intrinsics.stringPlus(rootPath1, "/"));
                entry.setComment(comment);
                zos.putNextEntry(entry);
                zos.closeEntry();
                return true;
            }
            fileArray = fileList;
            n = 0;
            int n2 = fileArray.length;
            do {
                if (n >= n2) return true;
                file = fileArray[n];
                ++n;
                Intrinsics.checkNotNullExpressionValue(file, "file");
            } while (this.zipFile(file, rootPath1, zos, comment));
            return false;
        }
        Closeable closeable = new BufferedInputStream(new FileInputStream(srcFile));
        boolean bl = false;
        boolean bl2 = false;
        Throwable throwable = null;
        try {
            BufferedInputStream is = (BufferedInputStream)closeable;
            boolean bl3 = false;
            ZipEntry entry = new ZipEntry(rootPath1);
            entry.setComment(comment);
            zos.putNextEntry(entry);
            zos.write(ByteStreamsKt.readBytes(is));
            zos.closeEntry();
            Unit unit = Unit.INSTANCE;
            return true;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally(closeable, throwable);
        }
    }

    @Nullable
    public final List<File> unzipFile(@NotNull String zipFilePath, @NotNull String destDirPath) throws IOException {
        Intrinsics.checkNotNullParameter(zipFilePath, "zipFilePath");
        Intrinsics.checkNotNullParameter(destDirPath, "destDirPath");
        return this.unzipFileByKeyword(zipFilePath, destDirPath, null);
    }

    @Nullable
    public final List<File> unzipFile(@NotNull File zipFile, @NotNull File destDir) throws IOException {
        Intrinsics.checkNotNullParameter(zipFile, "zipFile");
        Intrinsics.checkNotNullParameter(destDir, "destDir");
        return this.unzipFileByKeyword(zipFile, destDir, null);
    }

    @Nullable
    public final List<File> unzipFileByKeyword(@NotNull String zipFilePath, @NotNull String destDirPath, @Nullable String keyword) throws IOException {
        Intrinsics.checkNotNullParameter(zipFilePath, "zipFilePath");
        Intrinsics.checkNotNullParameter(destDirPath, "destDirPath");
        return this.unzipFileByKeyword(this.getFileByPath(zipFilePath), this.getFileByPath(destDirPath), keyword);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public final List<File> unzipFileByKeyword(@Nullable File zipFile, @Nullable File destDir, @Nullable String keyword) throws IOException {
        if (zipFile == null || destDir == null) {
            return null;
        }
        ArrayList files = new ArrayList();
        ZipFile zip2 = new ZipFile(zipFile);
        Enumeration<? extends ZipEntry> entries = zip2.entries();
        Closeable closeable = zip2;
        boolean bl = false;
        boolean bl2 = false;
        Throwable throwable = null;
        try {
            ZipFile it = (ZipFile)closeable;
            boolean bl3 = false;
            if (INSTANCE.isSpace(keyword)) {
                while (entries.hasMoreElements()) {
                    ZipEntry zipEntry = entries.nextElement();
                    if (zipEntry == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.util.zip.ZipEntry");
                    }
                    ZipEntry entry = zipEntry;
                    String entryName = entry.getName();
                    Intrinsics.checkNotNullExpressionValue(entryName, "entryName");
                    if (StringsKt.contains$default((CharSequence)entryName, "../", false, 2, null)) {
                        ZipUtilsKt.access$getLogger$p().error("ZipUtils entryName: " + entryName + " is dangerous!");
                        continue;
                    }
                    if (INSTANCE.unzipChildFile(destDir, files, zip2, entry, entryName)) continue;
                    List list2 = files;
                    return list2;
                }
            } else {
                while (entries.hasMoreElements()) {
                    Object entryName = entries.nextElement();
                    if (entryName == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.util.zip.ZipEntry");
                    }
                    ZipEntry entry = entryName;
                    entryName = entry.getName();
                    Intrinsics.checkNotNullExpressionValue(entryName, "entryName");
                    if (StringsKt.contains$default((CharSequence)entryName, "../", false, 2, null)) {
                        ZipUtilsKt.access$getLogger$p().error("ZipUtils entryName: " + entryName + " is dangerous!");
                        continue;
                    }
                    CharSequence charSequence = (CharSequence)entryName;
                    String string = keyword;
                    Intrinsics.checkNotNull(string);
                    if (!StringsKt.contains$default(charSequence, string, false, 2, null) || INSTANCE.unzipChildFile(destDir, files, zip2, entry, (String)entryName)) continue;
                    List list3 = files;
                    return list3;
                }
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
        return files;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean unzipChildFile(File destDir, List<File> files, ZipFile zip2, ZipEntry entry, String name) throws IOException {
        File file = new File(destDir, name);
        files.add(file);
        if (entry.isDirectory()) {
            return this.createOrExistsDir(file);
        }
        if (!this.createOrExistsFile(file)) {
            return false;
        }
        Closeable closeable = new BufferedInputStream(zip2.getInputStream(entry));
        boolean bl = false;
        boolean bl2 = false;
        Throwable throwable = null;
        try {
            BufferedInputStream in = (BufferedInputStream)closeable;
            boolean bl3 = false;
            Closeable closeable2 = new BufferedOutputStream(new FileOutputStream(file));
            boolean bl4 = false;
            boolean bl5 = false;
            Throwable throwable2 = null;
            try {
                BufferedOutputStream out = (BufferedOutputStream)closeable2;
                boolean bl6 = false;
                out.write(ByteStreamsKt.readBytes(in));
                Unit unit = Unit.INSTANCE;
            }
            catch (Throwable throwable3) {
                throwable2 = throwable3;
                throw throwable3;
            }
            finally {
                CloseableKt.closeFinally(closeable2, throwable2);
            }
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable4) {
            throwable = throwable4;
            throw throwable4;
        }
        finally {
            CloseableKt.closeFinally(closeable, throwable);
        }
        return true;
    }

    @Nullable
    public final List<String> getFilesPath(@NotNull String zipFilePath) throws IOException {
        Intrinsics.checkNotNullParameter(zipFilePath, "zipFilePath");
        return this.getFilesPath(this.getFileByPath(zipFilePath));
    }

    @Nullable
    public final List<String> getFilesPath(@Nullable File zipFile) throws IOException {
        if (zipFile == null) {
            return null;
        }
        ArrayList<String> paths = new ArrayList<String>();
        ZipFile zip2 = new ZipFile(zipFile);
        Enumeration<? extends ZipEntry> entries = zip2.entries();
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = entries.nextElement();
            if (zipEntry == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.util.zip.ZipEntry");
            }
            String entryName = zipEntry.getName();
            Intrinsics.checkNotNullExpressionValue(entryName, "entryName");
            if (StringsKt.contains$default((CharSequence)entryName, "../", false, 2, null)) {
                ZipUtilsKt.access$getLogger$p().error("ZipUtils entryName: " + entryName + " is dangerous!");
                paths.add(entryName);
                continue;
            }
            paths.add(entryName);
        }
        zip2.close();
        return paths;
    }

    @Nullable
    public final List<String> getComments(@NotNull String zipFilePath) throws IOException {
        Intrinsics.checkNotNullParameter(zipFilePath, "zipFilePath");
        return this.getComments(this.getFileByPath(zipFilePath));
    }

    @Nullable
    public final List<String> getComments(@Nullable File zipFile) throws IOException {
        if (zipFile == null) {
            return null;
        }
        ArrayList<String> comments = new ArrayList<String>();
        ZipFile zip2 = new ZipFile(zipFile);
        Enumeration<? extends ZipEntry> entries = zip2.entries();
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = entries.nextElement();
            if (zipEntry == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.util.zip.ZipEntry");
            }
            ZipEntry entry = zipEntry;
            comments.add(entry.getComment());
        }
        zip2.close();
        return comments;
    }

    private final boolean createOrExistsDir(File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private final boolean createOrExistsFile(File file) {
        boolean bl;
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile();
        }
        if (!this.createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            bl = file.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
            bl = false;
        }
        return bl;
    }

    private final File getFileByPath(String filePath) {
        return this.isSpace(filePath) ? null : new File(filePath);
    }

    private final boolean isSpace(String s) {
        if (s == null) {
            return true;
        }
        int len = s.length();
        for (int i = 0; i < len; ++i) {
            if (Character.isWhitespace(s.charAt(i))) continue;
            return false;
        }
        return true;
    }

    @JvmOverloads
    public final boolean zipFiles(@Nullable Collection<? extends File> srcFiles, @Nullable File zipFile) throws IOException {
        return ZipUtils.zipFiles$default(this, srcFiles, zipFile, null, 4, null);
    }

    @JvmOverloads
    public final boolean zipFile(@Nullable File srcFile, @Nullable File zipFile) throws IOException {
        return ZipUtils.zipFile$default(this, srcFile, zipFile, null, 4, null);
    }

    public static final /* synthetic */ boolean access$zipFile(ZipUtils $this, File srcFile, String rootPath, ZipOutputStream zos, String comment) {
        return $this.zipFile(srcFile, rootPath, zos, comment);
    }

    public static final /* synthetic */ File access$getFileByPath(ZipUtils $this, String filePath) {
        return $this.getFileByPath(filePath);
    }
}

