/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.model.localBook;

import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookChapter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 '2\u00020\u0001:\u0001'B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0002J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0002J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0002J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u001b\u001a\u00020\u0017H\u0002J$\u0010\u001c\u001a\u001e\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000f\u0012\u0006\u0012\u0004\u0018\u00010\t0\u001dH\u0002J&\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010#\u001a\u00020$H\u0002J\b\u0010%\u001a\u00020\u001fH\u0002J\b\u0010&\u001a\u00020\u001fH\u0002R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR(\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014\u00a8\u0006("}, d2={"Lio/legado/app/model/localBook/PdfFile;", "", "book", "Lio/legado/app/data/entities/Book;", "(Lio/legado/app/data/entities/Book;)V", "getBook", "()Lio/legado/app/data/entities/Book;", "setBook", "cover", "Ljava/io/InputStream;", "getCover", "()Ljava/io/InputStream;", "setCover", "(Ljava/io/InputStream;)V", "info", "", "", "getInfo", "()Ljava/util/Map;", "setInfo", "(Ljava/util/Map;)V", "getChapterList", "Ljava/util/ArrayList;", "Lio/legado/app/data/entities/BookChapter;", "getChapterListByOutline", "getChapterListByPage", "getContent", "chapter", "parseBookInfo", "Lkotlin/Pair;", "processOutline", "", "document", "Lorg/apache/pdfbox/pdmodel/PDDocument;", "chapterList", "outline", "Lorg/apache/pdfbox/pdmodel/interactive/documentnavigation/outline/PDOutlineNode;", "upBookInfo", "updateCover", "Companion", "reader-pro"})
public final class PdfFile {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Book book;
    @Nullable
    private Map<String, Object> info;
    @Nullable
    private InputStream cover;
    @Nullable
    private static PdfFile cFile;

    public PdfFile(@NotNull Book book) {
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
    }

    private final String getContent(BookChapter chapter) {
        return "";
    }

    private final ArrayList<BookChapter> getChapterList() {
        CharSequence charSequence = this.book.getTocUrl();
        boolean bl = false;
        if (charSequence.length() == 0) {
            this.book.setTocUrl("page");
        }
        if (Intrinsics.areEqual(this.book.getTocUrl(), "page")) {
            return this.getChapterListByPage();
        }
        return this.getChapterListByOutline();
    }

    private final ArrayList<BookChapter> getChapterListByPage() {
        BookChapter bookChapter;
        ArrayList<BookChapter> chapterList = new ArrayList<BookChapter>();
        int n = 0;
        PDDocument document = PDDocument.load(this.book.getLocalFile());
        int n2 = document.getNumberOfPages();
        if (n < n2) {
            do {
                int pageIndex = n++;
                String name = "output-" + pageIndex + ".png";
                BookChapter chapter = new BookChapter(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
                chapter.setTitle(name);
                chapter.setIndex(pageIndex);
                chapter.setBookUrl(this.book.getBookUrl());
                chapter.setUrl(name);
                chapter.setStart(Long.valueOf(pageIndex));
                chapter.setEnd(Long.valueOf(pageIndex));
                chapterList.add(chapter);
            } while (n < n2);
        }
        this.book.setLatestChapterTitle((bookChapter = (BookChapter)CollectionsKt.lastOrNull((List)chapterList)) == null ? null : bookChapter.getTitle());
        this.book.setTotalChapterNum(chapterList.size());
        Intrinsics.checkNotNullExpressionValue(document, "document");
        Util.closeQuietly(document);
        return chapterList;
    }

    private final ArrayList<BookChapter> getChapterListByOutline() {
        ArrayList<BookChapter> chapterList = new ArrayList<BookChapter>();
        PDDocument document = PDDocument.load(this.book.getLocalFile());
        PDDocumentOutline outline = document.getDocumentCatalog().getDocumentOutline();
        if (outline == null) {
            return chapterList;
        }
        Intrinsics.checkNotNullExpressionValue(document, "document");
        this.processOutline(document, chapterList, outline);
        if (chapterList.size() > 0) {
            chapterList.get(chapterList.size() - 1).setEnd(Long.valueOf(document.getNumberOfPages()));
        }
        Util.closeQuietly(document);
        return chapterList;
    }

    private final void processOutline(PDDocument document, ArrayList<BookChapter> chapterList, PDOutlineNode outline) {
        PDOutlineItem current = outline.getFirstChild();
        while (current != null) {
            BookChapter chapter22;
            PDPage page = current.findDestinationPage(document);
            int pageIndex = document.getDocumentCatalog().getPages().indexOf(page);
            if (chapterList.size() == 0 && pageIndex >= 1) {
                chapter22 = new BookChapter(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
                chapter22.setTitle("\u9996\u7ae0");
                chapter22.setIndex(0);
                chapter22.setBookUrl(this.book.getBookUrl());
                chapter22.setUrl("chapter-0");
                chapter22.setStart(0L);
                chapter22.setEnd(Long.valueOf(pageIndex));
                chapterList.add(chapter22);
            }
            if (chapterList.size() > 0) {
                Long l = chapterList.get(chapterList.size() - 1).getStart();
                long chapter22 = pageIndex;
                if (l != null && l == chapter22) {
                    current = current.getNextSibling();
                    continue;
                }
                chapter22 = new BookChapter(null, null, false, null, null, 0, null, null, null, null, null, null, null, 8191, null);
                String string = current.getTitle();
                Intrinsics.checkNotNullExpressionValue(string, "current.getTitle()");
                chapter22.setTitle(string);
                chapter22.setIndex(chapterList.size());
                chapter22.setBookUrl(this.book.getBookUrl());
                chapter22.setUrl(Intrinsics.stringPlus("chapter-", chapterList.size()));
                chapter22.setStart(Long.valueOf(pageIndex));
                chapterList.get(chapterList.size() - 1).setEnd((long)pageIndex - 1L);
                chapterList.add(chapter22);
            }
            if (current.hasChildren()) {
                PDOutlineItem pDOutlineItem = current;
                this.processOutline(document, chapterList, pDOutlineItem);
            }
            current = current.getNextSibling();
        }
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tJ\u0018\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u0007J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0002J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\u0010\u001a\u00020\u0011R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lio/legado/app/model/localBook/PdfFile$Companion;", "", "()V", "cFile", "Lio/legado/app/model/localBook/PdfFile;", "getChapterList", "Ljava/util/ArrayList;", "Lio/legado/app/data/entities/BookChapter;", "book", "Lio/legado/app/data/entities/Book;", "getContent", "", "chapter", "getPdfFile", "upBookInfo", "", "onlyCover", "", "reader-pro"})
    public static final class Companion {
        private Companion() {
        }

        private final synchronized PdfFile getPdfFile(Book book) {
            PdfFile pdfFile;
            block7: {
                block6: {
                    String string;
                    if (cFile == null) break block6;
                    pdfFile = cFile;
                    if (pdfFile == null) {
                        string = null;
                    } else {
                        Book book2 = pdfFile.getBook();
                        string = book2.getBookUrl();
                    }
                    if (Intrinsics.areEqual(string, book.getBookUrl())) break block7;
                }
                cFile = new PdfFile(book);
                PdfFile pdfFile2 = cFile;
                Intrinsics.checkNotNull(pdfFile2);
                return pdfFile2;
            }
            pdfFile = cFile;
            if (pdfFile != null) {
                pdfFile.setBook(book);
            }
            PdfFile pdfFile3 = cFile;
            Intrinsics.checkNotNull(pdfFile3);
            return pdfFile3;
        }

        @NotNull
        public final synchronized ArrayList<BookChapter> getChapterList(@NotNull Book book) {
            Intrinsics.checkNotNullParameter(book, "book");
            return this.getPdfFile(book).getChapterList();
        }

        @Nullable
        public final synchronized String getContent(@NotNull Book book, @NotNull BookChapter chapter) {
            Intrinsics.checkNotNullParameter(book, "book");
            Intrinsics.checkNotNullParameter(chapter, "chapter");
            return this.getPdfFile(book).getContent(chapter);
        }

        public final synchronized void upBookInfo(@NotNull Book book, boolean onlyCover) {
            Intrinsics.checkNotNullParameter(book, "book");
            if (onlyCover) {
                this.getPdfFile(book).updateCover();
                return;
            }
            this.getPdfFile(book).upBookInfo();
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

