/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.model.localBook;

import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookChapter;
import io.legado.app.utils.FileUtils;
import io.legado.app.utils.MD5Utils;
import io.legado.app.utils.XmlUtils;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0002J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0019\u001a\u00020\u0017H\u0002J$\u0010\u001a\u001a\u001e\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000f\u0012\u0006\u0012\u0004\u0018\u00010\t0\u001bH\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u0010\u001e\u001a\u00020\u001dH\u0002R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR(\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014\u00a8\u0006 "}, d2={"Lio/legado/app/model/localBook/CbzFile;", "", "book", "Lio/legado/app/data/entities/Book;", "(Lio/legado/app/data/entities/Book;)V", "getBook", "()Lio/legado/app/data/entities/Book;", "setBook", "cover", "Ljava/io/InputStream;", "getCover", "()Ljava/io/InputStream;", "setCover", "(Ljava/io/InputStream;)V", "info", "", "", "getInfo", "()Ljava/util/Map;", "setInfo", "(Ljava/util/Map;)V", "getChapterList", "Ljava/util/ArrayList;", "Lio/legado/app/data/entities/BookChapter;", "getContent", "chapter", "parseBookInfo", "Lkotlin/Pair;", "upBookInfo", "", "updateCover", "Companion", "reader-pro"})
public final class CbzFile {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Book book;
    @Nullable
    private Map<String, Object> info;
    @Nullable
    private InputStream cover;
    @Nullable
    private static CbzFile cFile;

    public CbzFile(@NotNull Book book) {
        Intrinsics.checkNotNullParameter(book, "book");
        this.book = book;
    }

    @NotNull
    public final Book getBook() {
        return this.book;
    }

    public final void setBook(@NotNull Book book) {
        Intrinsics.checkNotNullParameter(book, "<set-?>");
        this.book = book;
    }

    @Nullable
    public final Map<String, Object> getInfo() {
        return this.info;
    }

    public final void setInfo(@Nullable Map<String, Object> map) {
        this.info = map;
    }

    @Nullable
    public final InputStream getCover() {
        return this.cover;
    }

    public final void setCover(@Nullable InputStream inputStream2) {
        this.cover = inputStream2;
    }

    private final Pair<Map<String, Object>, InputStream> parseBookInfo() {
        if (this.cover != null || this.info != null) {
            return new Pair<Map<String, Object>, InputStream>(this.info, this.cover);
        }
        ZipFile zf = new ZipFile(this.book.getLocalFile());
        Enumeration<? extends ZipEntry> entries = zf.entries();
        String[] stringArray = new String[]{"jpg", "jpeg", "gif", "png", "bmp", "webp", "svg"};
        List<String> imageExt = CollectionsKt.listOf(stringArray);
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = entries.nextElement();
            if (zipEntry == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.util.zip.ZipEntry");
            }
            ZipEntry zipEntry2 = zipEntry;
            if (!zipEntry2.isDirectory()) {
                Object object;
                String name = zipEntry2.getName();
                if (name.equals("ComicInfo.xml")) {
                    InputStream inputStream2 = zf.getInputStream(zipEntry2);
                    object = inputStream2;
                    Intrinsics.checkNotNullExpressionValue(object, "inputStream");
                    this.info = XmlUtils.INSTANCE.xml2map(object);
                } else if (this.cover == null) {
                    String ext;
                    Intrinsics.checkNotNullExpressionValue(name, "name");
                    object = FileUtils.getFileExtetion$default(FileUtils.INSTANCE, name, null, 2, null);
                    boolean bl = false;
                    Object object2 = object;
                    if (object2 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    Intrinsics.checkNotNullExpressionValue(((String)object2).toLowerCase(Locale.ROOT), "(this as java.lang.Strin\u2026.toLowerCase(Locale.ROOT)");
                    if (imageExt.contains(ext)) {
                        this.cover = zf.getInputStream(zipEntry2);
                    }
                }
            }
            if (this.cover == null || this.info == null) continue;
        }
        return new Pair<Map<String, Object>, InputStream>(this.info, this.cover);
    }

    private final void upBookInfo() {
        Pair<Map<String, Object>, InputStream> result2 = this.parseBookInfo();
        if (result2.getFirst() != null) {
            Map info;
            Map<String, Object> map = result2.getFirst();
            if (map == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Map<kotlin.String, kotlin.Any>");
            }
            Map<String, Object> bookInfo = map;
            Map map2 = (Map)bookInfo.get("ComicInfo");
            Map map3 = info = map2 == null ? null : map2;
            map2 = map3 == null ? null : map3.get("Title");
            this.book.setName((String)(map2 == null ? this.book.getName() : map2));
            map3 = info;
            map2 = map3 == null ? null : map3.get("Writer");
            this.book.setAuthor((String)(map2 == null ? this.book.getAuthor() : map2));
        }
        this.updateCover();
    }

    private final void updateCover() {
        Pair<Map<String, Object>, InputStream> result2;
        String coverFile = Intrinsics.stringPlus(MD5Utils.INSTANCE.md5Encode16(this.book.getBookUrl()), ".jpg");
        String[] stringArray = new String[]{this.book.getUserNameSpace(), "covers", coverFile};
        String relativeCoverUrl = ((Object)Paths.get("assets", stringArray)).toString();
        this.book.setCoverUrl(Intrinsics.stringPlus("/", StringsKt.replace$default(relativeCoverUrl, "\\", "/", false, 4, null)));
        String[] stringArray2 = new String[]{"storage", relativeCoverUrl};
        String coverUrl = ((Object)Paths.get(this.book.workRoot(), stringArray2)).toString();
        if (!new File(coverUrl).exists() && (result2 = this.parseBookInfo()).getSecond() != null) {
            InputStream inputStream2 = result2.getSecond();
            if (inputStream2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.io.InputStream");
            }
            InputStream coverStream = inputStream2;
            FileUtils.INSTANCE.writeInputStream(coverUrl, coverStream);
        }
    }

    private final String getContent(BookChapter chapter) {
        return "";
    }

    private final ArrayList<BookChapter> getChapterList() {
        BookChapter bookChapter;
        ArrayList<BookChapter> chapterList = new ArrayList<BookChapter>();
        ZipFile zf = new ZipFile(this.book.getLocalFile());
        Enumeration<? extends ZipEntry> entries = zf.entries();
        int n = 0;
        ArrayList<String> imageFileList = new ArrayList<String>();
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = entries.nextElement();
            if (zipEntry == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.util.zip.ZipEntry");
            }
            ZipEntry zipEntry2 = zipEntry;
            if (zipEntry2.isDirectory()) continue;
            String name = zipEntry2.getName();
            Intrinsics.checkNotNullExpressionValue(name, "name");
            if (StringsKt.endsWith$default(name, ".xml", false, 2, null)) continue;
            imageFileList.add(name);
        }
        CollectionsKt.sort((List)imageFileList);
        n = 0;
        int n2 = imageFileList.size();
        if (n < n2) {
            do {
                int i = n++;
                Object e = imageFileList.get(i);
                Intrinsics.checkNotNullExpressionValue(e, "imageFileList.get(i)");
                String name = (String)e;
                BookChapter chapter = new BookChapter(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
                chapter.setTitle(name);
                chapter.setIndex(i);
                chapter.setBookUrl(this.book.getBookUrl());
                chapter.setUrl(name);
                chapterList.add(chapter);
            } while (n < n2);
        }
        this.book.setLatestChapterTitle((bookChapter = (BookChapter)CollectionsKt.lastOrNull((List)chapterList)) == null ? null : bookChapter.getTitle());
        this.book.setTotalChapterNum(chapterList.size());
        return chapterList;
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u0006\u001a\u00020\u0007J\u0018\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\nJ\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\u0010\u001a\u00020\u0011R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lio/legado/app/model/localBook/CbzFile$Companion;", "", "()V", "cFile", "Lio/legado/app/model/localBook/CbzFile;", "getCbzFile", "book", "Lio/legado/app/data/entities/Book;", "getChapterList", "Ljava/util/ArrayList;", "Lio/legado/app/data/entities/BookChapter;", "getContent", "", "chapter", "upBookInfo", "", "onlyCover", "", "reader-pro"})
    public static final class Companion {
        private Companion() {
        }

        private final synchronized CbzFile getCbzFile(Book book) {
            CbzFile cbzFile;
            block7: {
                block6: {
                    String string;
                    if (cFile == null) break block6;
                    cbzFile = cFile;
                    if (cbzFile == null) {
                        string = null;
                    } else {
                        Book book2 = cbzFile.getBook();
                        string = book2.getBookUrl();
                    }
                    if (Intrinsics.areEqual(string, book.getBookUrl())) break block7;
                }
                cFile = new CbzFile(book);
                CbzFile cbzFile2 = cFile;
                Intrinsics.checkNotNull(cbzFile2);
                return cbzFile2;
            }
            cbzFile = cFile;
            if (cbzFile != null) {
                cbzFile.setBook(book);
            }
            CbzFile cbzFile3 = cFile;
            Intrinsics.checkNotNull(cbzFile3);
            return cbzFile3;
        }

        @NotNull
        public final synchronized ArrayList<BookChapter> getChapterList(@NotNull Book book) {
            Intrinsics.checkNotNullParameter(book, "book");
            return this.getCbzFile(book).getChapterList();
        }

        @Nullable
        public final synchronized String getContent(@NotNull Book book, @NotNull BookChapter chapter) {
            Intrinsics.checkNotNullParameter(book, "book");
            Intrinsics.checkNotNullParameter(chapter, "chapter");
            return this.getCbzFile(book).getContent(chapter);
        }

        public final synchronized void upBookInfo(@NotNull Book book, boolean onlyCover) {
            Intrinsics.checkNotNullParameter(book, "book");
            if (onlyCover) {
                this.getCbzFile(book).updateCover();
                return;
            }
            this.getCbzFile(book).upBookInfo();
        }

        public static /* synthetic */ void upBookInfo$default(Companion companion, Book book, boolean bl, int n, Object object) {
            if ((n & 2) != 0) {
                bl = false;
            }
            companion.upBookInfo(book, bl);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

