/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.model.localBook;

import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookChapter;
import io.legado.app.model.localBook.LocalBook;
import io.legado.app.utils.FileUtils;
import io.legado.app.utils.MD5Utils;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import me.ag2s.umdlib.domain.UmdBook;
import me.ag2s.umdlib.domain.UmdChapters;
import me.ag2s.umdlib.domain.UmdCover;
import me.ag2s.umdlib.domain.UmdHeader;
import me.ag2s.umdlib.umd.UmdReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0002J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u000eH\u0002J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0010H\u0002J\n\u0010\u0015\u001a\u0004\u0018\u00010\tH\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0017H\u0002R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004R\u0018\u0010\b\u001a\u0004\u0018\u00010\t8BX\u0082\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u001a"}, d2={"Lio/legado/app/model/localBook/UmdFile;", "", "book", "Lio/legado/app/data/entities/Book;", "(Lio/legado/app/data/entities/Book;)V", "getBook", "()Lio/legado/app/data/entities/Book;", "setBook", "umdBook", "Lme/ag2s/umdlib/domain/UmdBook;", "getUmdBook", "()Lme/ag2s/umdlib/domain/UmdBook;", "getChapterList", "Ljava/util/ArrayList;", "Lio/legado/app/data/entities/BookChapter;", "getContent", "", "chapter", "getImage", "Ljava/io/InputStream;", "href", "readUmd", "upBookInfo", "", "updateCover", "Companion", "reader-pro"})
public final class UmdFile {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Book book;
    @Nullable
    private UmdBook umdBook;
    @Nullable
    private static UmdFile uFile;

    public UmdFile(@NotNull Book book) {
        Intrinsics.checkNotNullParameter(book, "book");
        this.book = book;
        try {
            UmdBook umdBook = this.getUmdBook();
            if (umdBook != null) {
                UmdBook umdBook2 = umdBook;
                boolean bl = false;
                boolean bl2 = false;
                UmdBook it = umdBook2;
                boolean bl3 = false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NotNull
    public final Book getBook() {
        return this.book;
    }

    public final void setBook(@NotNull Book book) {
        Intrinsics.checkNotNullParameter(book, "<set-?>");
        this.book = book;
    }

    private final UmdBook getUmdBook() {
        if (this.umdBook != null) {
            return this.umdBook;
        }
        this.umdBook = this.readUmd();
        return this.umdBook;
    }

    private final UmdBook readUmd() {
        InputStream input = LocalBook.INSTANCE.getBookInputStream(this.book);
        return new UmdReader().read(input);
    }

    private final void upBookInfo() {
        if (this.getUmdBook() == null) {
            uFile = null;
            this.book.setIntro("\u4e66\u7c4d\u5bfc\u5165\u5f02\u5e38");
        } else {
            UmdBook umdBook = this.getUmdBook();
            Intrinsics.checkNotNull(umdBook);
            UmdHeader hd = umdBook.getHeader();
            String string = hd.getTitle();
            Intrinsics.checkNotNullExpressionValue(string, "hd.title");
            this.book.setName(string);
            string = hd.getAuthor();
            Intrinsics.checkNotNullExpressionValue(string, "hd.author");
            this.book.setAuthor(string);
            this.book.setKind(hd.getBookType());
            this.updateCover();
        }
    }

    private final void updateCover() {
        byte[] byArray;
        UmdCover umdCover;
        if (this.getUmdBook() == null) {
            uFile = null;
            return;
        }
        String coverFile = Intrinsics.stringPlus(MD5Utils.INSTANCE.md5Encode16(this.book.getBookUrl()), ".jpg");
        String[] stringArray = new String[]{this.book.getUserNameSpace(), "covers", coverFile};
        String relativeCoverUrl = ((Object)Paths.get("assets", stringArray)).toString();
        this.book.setCoverUrl(Intrinsics.stringPlus("/", StringsKt.replace$default(relativeCoverUrl, "\\", "/", false, 4, null)));
        Object object = new String[]{"storage", relativeCoverUrl};
        String coverUrl = ((Object)Paths.get(this.book.workRoot(), (String[])object)).toString();
        if (!new File(coverUrl).exists() && (object = this.getUmdBook()) != null && (umdCover = ((UmdBook)object).getCover()) != null && (byArray = umdCover.getCoverData()) != null) {
            byte[] byArray2 = byArray;
            boolean bl = false;
            boolean bl2 = false;
            byte[] it = byArray2;
            boolean bl3 = false;
            FileUtils.INSTANCE.writeBytes(coverUrl, it);
        }
    }

    private final String getContent(BookChapter chapter) {
        UmdChapters umdChapters;
        UmdBook umdBook = this.getUmdBook();
        return umdBook == null ? null : ((umdChapters = umdBook.getChapters()) == null ? null : umdChapters.getContentString(chapter.getIndex()));
    }

    private final ArrayList<BookChapter> getChapterList() {
        List<byte[]> list2;
        UmdChapters umdChapters;
        ArrayList<BookChapter> chapterList = new ArrayList<BookChapter>();
        Object object = this.getUmdBook();
        if (object != null && (umdChapters = ((UmdBook)object).getChapters()) != null && (list2 = umdChapters.getTitles()) != null) {
            Iterable $this$forEachIndexed$iv = list2;
            boolean $i$f$forEachIndexed = false;
            int index$iv = 0;
            for (Object item$iv : $this$forEachIndexed$iv) {
                int n = index$iv++;
                boolean bl = false;
                if (n < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                byte[] byArray = (byte[])item$iv;
                int index = n;
                boolean bl2 = false;
                UmdBook umdBook = this.getUmdBook();
                Intrinsics.checkNotNull(umdBook);
                String title = umdBook.getChapters().getTitle(index);
                BookChapter chapter = new BookChapter(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
                Intrinsics.checkNotNullExpressionValue(title, "title");
                chapter.setTitle(title);
                chapter.setIndex(index);
                chapter.setBookUrl(this.getBook().getBookUrl());
                chapter.setUrl(String.valueOf(index));
                System.out.println(Intrinsics.stringPlus("UMD", chapter.getUrl()));
                chapterList.add(chapter);
            }
        }
        this.book.setLatestChapterTitle((object = (BookChapter)CollectionsKt.lastOrNull((List)chapterList)) == null ? null : ((BookChapter)object).getTitle());
        this.book.setTotalChapterNum(chapterList.size());
        return chapterList;
    }

    private final InputStream getImage(String href) {
        return null;
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tJ\u0018\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u0007J\u0018\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u000bJ\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0002J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\u0013\u001a\u00020\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2={"Lio/legado/app/model/localBook/UmdFile$Companion;", "", "()V", "uFile", "Lio/legado/app/model/localBook/UmdFile;", "getChapterList", "Ljava/util/ArrayList;", "Lio/legado/app/data/entities/BookChapter;", "book", "Lio/legado/app/data/entities/Book;", "getContent", "", "chapter", "getImage", "Ljava/io/InputStream;", "href", "getUFile", "upBookInfo", "", "onlyCover", "", "reader-pro"})
    public static final class Companion {
        private Companion() {
        }

        private final synchronized UmdFile getUFile(Book book) {
            UmdFile umdFile;
            block7: {
                block6: {
                    String string;
                    if (uFile == null) break block6;
                    umdFile = uFile;
                    if (umdFile == null) {
                        string = null;
                    } else {
                        Book book2 = umdFile.getBook();
                        string = book2.getBookUrl();
                    }
                    if (Intrinsics.areEqual(string, book.getBookUrl())) break block7;
                }
                uFile = new UmdFile(book);
                UmdFile umdFile2 = uFile;
                Intrinsics.checkNotNull(umdFile2);
                return umdFile2;
            }
            umdFile = uFile;
            if (umdFile != null) {
                umdFile.setBook(book);
            }
            UmdFile umdFile3 = uFile;
            Intrinsics.checkNotNull(umdFile3);
            return umdFile3;
        }

        @NotNull
        public final synchronized ArrayList<BookChapter> getChapterList(@NotNull Book book) {
            Intrinsics.checkNotNullParameter(book, "book");
            return this.getUFile(book).getChapterList();
        }

        @Nullable
        public final synchronized String getContent(@NotNull Book book, @NotNull BookChapter chapter) {
            Intrinsics.checkNotNullParameter(book, "book");
            Intrinsics.checkNotNullParameter(chapter, "chapter");
            return this.getUFile(book).getContent(chapter);
        }

        @Nullable
        public final synchronized InputStream getImage(@NotNull Book book, @NotNull String href) {
            Intrinsics.checkNotNullParameter(book, "book");
            Intrinsics.checkNotNullParameter(href, "href");
            return this.getUFile(book).getImage(href);
        }

        public final synchronized void upBookInfo(@NotNull Book book, boolean onlyCover) {
            Intrinsics.checkNotNullParameter(book, "book");
            if (onlyCover) {
                this.getUFile(book).updateCover();
                return;
            }
            this.getUFile(book).upBookInfo();
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

