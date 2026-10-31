/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.help;

import io.legado.app.constant.AppPattern;
import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookChapter;
import io.legado.app.data.entities.BookSource;
import io.legado.app.help.BookHelp;
import io.legado.app.model.analyzeRule.AnalyzeUrl;
import io.legado.app.model.localBook.LocalBook;
import io.legado.app.utils.FileExtensionsKt;
import io.legado.app.utils.FileUtils;
import io.legado.app.utils.MD5Utils;
import io.legado.app.utils.NetworkUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.DelayKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\r\u001a\u00020\u00042\b\u0010\u000e\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0004J\u000e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0004J\u0010\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0004H\u0002J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\t\u001a\u00020\nJ\u0018\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\u0017\u001a\u00020\u00152\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\u0004J\u000e\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004J9\u0010\u001a\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001f\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010 J+\u0010!\u001a\u00020\b2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\"J9\u0010#\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001f\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010 J\u001e\u0010$\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001f\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006%"}, d2={"Lio/legado/app/help/BookHelp;", "", "()V", "cacheImageFolderName", "", "downloadImages", "Ljava/util/concurrent/CopyOnWriteArraySet;", "delContent", "", "book", "Lio/legado/app/data/entities/Book;", "bookChapter", "Lio/legado/app/data/entities/BookChapter;", "formatAuthor", "author", "formatBookAuthor", "formatBookName", "name", "formatFolderName", "folderName", "getBookCacheDir", "Ljava/io/File;", "getContent", "getImage", "src", "getImageSuffix", "saveContent", "scope", "Lkotlinx/coroutines/CoroutineScope;", "bookSource", "Lio/legado/app/data/entities/BookSource;", "content", "(Lkotlinx/coroutines/CoroutineScope;Lio/legado/app/data/entities/BookSource;Lio/legado/app/data/entities/Book;Lio/legado/app/data/entities/BookChapter;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveImage", "(Lio/legado/app/data/entities/BookSource;Lio/legado/app/data/entities/Book;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveImages", "saveText", "reader-pro"})
public final class BookHelp {
    @NotNull
    public static final BookHelp INSTANCE = new BookHelp();
    @NotNull
    private static final String cacheImageFolderName = "images";
    @NotNull
    private static final CopyOnWriteArraySet<String> downloadImages = new CopyOnWriteArraySet();

    private BookHelp() {
    }

    private final String formatFolderName(String folderName) {
        CharSequence charSequence = folderName;
        Object object = "[\\\\/:*?\"<>|.]";
        boolean bl = false;
        object = new Regex((String)object);
        String string = "";
        boolean bl2 = false;
        return ((Regex)object).replace(charSequence, string);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final String formatAuthor(@Nullable String author) {
        Object object;
        String string = author;
        if (string == null) {
            object = "";
        } else {
            CharSequence charSequence = string;
            Object object2 = "\u4f5c\\s*\u8005[\\s:\uff1a]*";
            boolean bl = false;
            object2 = new Regex((String)object2);
            Object object3 = "";
            boolean bl2 = false;
            String string2 = ((Regex)object2).replace(charSequence, (String)object3);
            if (string2 == null) {
                object = "";
            } else {
                object2 = string2;
                object3 = "\\s+";
                bl2 = false;
                object3 = new Regex((String)object3);
                String string3 = " ";
                boolean bl3 = false;
                charSequence = ((Regex)object3).replace((CharSequence)object2, string3);
                if (charSequence == null) {
                    object = "";
                } else {
                    void $this$trim$iv;
                    object3 = charSequence;
                    boolean $i$f$trim = false;
                    CharSequence $this$trim$iv$iv = (CharSequence)$this$trim$iv;
                    boolean $i$f$trim2 = false;
                    int startIndex$iv$iv = 0;
                    int endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
                    boolean startFound$iv$iv = false;
                    while (startIndex$iv$iv <= endIndex$iv$iv) {
                        boolean match$iv$iv;
                        int index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
                        char it = $this$trim$iv$iv.charAt(index$iv$iv);
                        boolean bl4 = false;
                        boolean bl5 = match$iv$iv = Intrinsics.compare(it, 32) <= 0;
                        if (!startFound$iv$iv) {
                            if (!match$iv$iv) {
                                startFound$iv$iv = true;
                                continue;
                            }
                            ++startIndex$iv$iv;
                            continue;
                        }
                        if (!match$iv$iv) break;
                        --endIndex$iv$iv;
                    }
                    object = (object2 = ((Object)$this$trim$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1)).toString()) == null ? "" : object2;
                }
            }
        }
        return object;
    }

    @NotNull
    public final String formatBookName(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        CharSequence charSequence = name;
        Regex regex = AppPattern.INSTANCE.getNameRegex();
        String string = "";
        boolean bl = false;
        String $this$trim$iv = regex.replace(charSequence, string);
        boolean $i$f$trim = false;
        CharSequence $this$trim$iv$iv = $this$trim$iv;
        boolean $i$f$trim2 = false;
        int startIndex$iv$iv = 0;
        int endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
        boolean startFound$iv$iv = false;
        while (startIndex$iv$iv <= endIndex$iv$iv) {
            boolean match$iv$iv;
            int index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
            char it = $this$trim$iv$iv.charAt(index$iv$iv);
            boolean bl2 = false;
            boolean bl3 = match$iv$iv = Intrinsics.compare(it, 32) <= 0;
            if (!startFound$iv$iv) {
                if (!match$iv$iv) {
                    startFound$iv$iv = true;
                    continue;
                }
                ++startIndex$iv$iv;
                continue;
            }
            if (!match$iv$iv) break;
            --endIndex$iv$iv;
        }
        return ((Object)$this$trim$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1)).toString();
    }

    @NotNull
    public final String formatBookAuthor(@NotNull String author) {
        Intrinsics.checkNotNullParameter(author, "author");
        CharSequence charSequence = author;
        Regex regex = AppPattern.INSTANCE.getAuthorRegex();
        String string = "";
        boolean bl = false;
        String $this$trim$iv = regex.replace(charSequence, string);
        boolean $i$f$trim = false;
        CharSequence $this$trim$iv$iv = $this$trim$iv;
        boolean $i$f$trim2 = false;
        int startIndex$iv$iv = 0;
        int endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
        boolean startFound$iv$iv = false;
        while (startIndex$iv$iv <= endIndex$iv$iv) {
            boolean match$iv$iv;
            int index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
            char it = $this$trim$iv$iv.charAt(index$iv$iv);
            boolean bl2 = false;
            boolean bl3 = match$iv$iv = Intrinsics.compare(it, 32) <= 0;
            if (!startFound$iv$iv) {
                if (!match$iv$iv) {
                    startFound$iv$iv = true;
                    continue;
                }
                ++startIndex$iv$iv;
                continue;
            }
            if (!match$iv$iv) break;
            --endIndex$iv$iv;
        }
        return ((Object)$this$trim$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1)).toString();
    }

    @NotNull
    public final File getBookCacheDir(@NotNull Book book) {
        Intrinsics.checkNotNullParameter(book, "book");
        String md5Encode = MD5Utils.INSTANCE.md5Encode(book.getBookUrl()).toString();
        String bookDir = book.getBookDir();
        CharSequence charSequence = bookDir;
        boolean bl = false;
        if (charSequence.length() == 0) {
            throw new Exception("bookDir\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String[] stringArray = new String[]{md5Encode};
        File localCacheDir = FileExtensionsKt.getFile(new File(bookDir), stringArray);
        if (!localCacheDir.exists()) {
            localCacheDir.mkdirs();
        }
        return localCacheDir;
    }

    @Nullable
    public final String getContent(@NotNull Book book, @NotNull BookChapter bookChapter) {
        Intrinsics.checkNotNullParameter(book, "book");
        Intrinsics.checkNotNullParameter(bookChapter, "bookChapter");
        File file = this.getBookCacheDir(book);
        String[] stringArray = new String[1];
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String string = "%d.txt";
        Object[] objectArray = new Object[]{bookChapter.getIndex()};
        boolean bl = false;
        Intrinsics.checkNotNullExpressionValue(String.format(string, Arrays.copyOf(objectArray, objectArray.length)), "java.lang.String.format(format, *args)");
        File file2 = FileExtensionsKt.getFile(file, stringArray);
        if (file2.exists()) {
            return FilesKt.readText$default(file2, null, 1, null);
        }
        if (book.isLocalBook()) {
            String content = LocalBook.INSTANCE.getContent(book, bookChapter);
            if (content != null && book.isEpub()) {
                this.saveText(book, bookChapter, content);
            }
            return content;
        }
        return null;
    }

    public final void delContent(@NotNull Book book, @NotNull BookChapter bookChapter) {
        Intrinsics.checkNotNullParameter(book, "book");
        Intrinsics.checkNotNullParameter(bookChapter, "bookChapter");
        File file = this.getBookCacheDir(book);
        String[] stringArray = new String[1];
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String string = "%d.txt";
        Object[] objectArray = new Object[]{bookChapter.getIndex()};
        boolean bl = false;
        Intrinsics.checkNotNullExpressionValue(String.format(string, Arrays.copyOf(objectArray, objectArray.length)), "java.lang.String.format(format, *args)");
        FileUtils.INSTANCE.createFileIfNotExist(file, stringArray).delete();
    }

    @Nullable
    public final Object saveContent(@NotNull CoroutineScope scope, @NotNull BookSource bookSource, @NotNull Book book, @NotNull BookChapter bookChapter, @NotNull String content, @NotNull Continuation<? super Unit> $completion) {
        this.saveText(book, bookChapter, content);
        Object object = this.saveImages(scope, bookSource, book, bookChapter, content, $completion);
        if (object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return object;
        }
        return Unit.INSTANCE;
    }

    public final void saveText(@NotNull Book book, @NotNull BookChapter bookChapter, @NotNull String content) {
        Intrinsics.checkNotNullParameter(book, "book");
        Intrinsics.checkNotNullParameter(bookChapter, "bookChapter");
        Intrinsics.checkNotNullParameter(content, "content");
        File file = this.getBookCacheDir(book);
        String[] stringArray = new String[1];
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String string = "%d.txt";
        Object[] objectArray = new Object[]{bookChapter.getIndex()};
        boolean bl = false;
        Intrinsics.checkNotNullExpressionValue(String.format(string, Arrays.copyOf(objectArray, objectArray.length)), "java.lang.String.format(format, *args)");
        FilesKt.writeText$default(FileUtils.INSTANCE.createFileIfNotExist(file, stringArray), content, null, 2, null);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object saveImages(@NotNull CoroutineScope var1_1, @NotNull BookSource var2_2, @NotNull Book var3_3, @NotNull BookChapter var4_4, @NotNull String var5_5, @NotNull Continuation<? super Unit> var6_6) {
        if (!(var6_6 instanceof saveImages.1)) ** GOTO lbl-1000
        var24_7 = var6_6;
        if ((var24_7.label & -2147483648) != 0) {
            var24_7.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var6_6){
                Object L$0;
                /* synthetic */ Object result;
                final /* synthetic */ BookHelp this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.saveImages(null, null, null, null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var25_9 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                var8_10 = false;
                awaitList = new ArrayList<Deferred>();
                var8_11 = new String[]{"\n"};
                $this$forEach$iv = StringsKt.split$default((CharSequence)content, var8_11, false, 0, 6, null);
                $i$f$forEach = false;
                for (E element$iv : $this$forEach$iv) {
                    it = (String)element$iv /* !! */ ;
                    $i$a$-forEach-BookHelp$saveImages$2 = false;
                    matcher = AppPattern.INSTANCE.getImgPattern().matcher((CharSequence)it);
                    if (!matcher.find() || (var15_19 = matcher.group(1)) == null) continue;
                    var16_20 = var15_19;
                    var17_21 = false;
                    var18_22 = false;
                    src = var16_20;
                    $i$a$-let-BookHelp$saveImages$2$1 = false;
                    mSrc = NetworkUtils.INSTANCE.getAbsoluteURL(bookChapter.getUrl(), src);
                    req = BuildersKt.async$default((CoroutineScope)scope, null, null, new Function2<CoroutineScope, Continuation<? super Integer>, Object>((BookSource)bookSource, (Book)book, mSrc, null){
                        int label;
                        final /* synthetic */ BookSource $bookSource;
                        final /* synthetic */ Book $book;
                        final /* synthetic */ String $mSrc;
                        {
                            this.$bookSource = $bookSource;
                            this.$book = $book;
                            this.$mSrc = $mSrc;
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
                                    Object object3 = BookHelp.INSTANCE.saveImage(this.$bookSource, this.$book, this.$mSrc, this);
                                    if (object3 != object2) return Boxing.boxInt(1);
                                    return object2;
                                }
                                case 1: {
                                    void $result;
                                    ResultKt.throwOnFailure($result);
                                    Object object3 = $result;
                                    return Boxing.boxInt(1);
                                }
                            }
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        @NotNull
                        public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                            return (Continuation)((Object)new /* invalid duplicate definition of identical inner class */);
                        }

                        @Nullable
                        public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Integer> p2) {
                            return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                        }
                    }, 3, null);
                    Boxing.boxBoolean(awaitList.add(req));
                }
                $this$forEach$iv = awaitList;
                $i$f$forEach = false;
                var10_14 = $this$forEach$iv.iterator();
lbl37:
                // 3 sources

                while (var10_14.hasNext()) {
                    element$iv /* !! */  = var10_14.next();
                    it = (Deferred)element$iv /* !! */ ;
                    $i$a$-forEach-BookHelp$saveImages$3 = false;
                    $continuation.L$0 = var10_14;
                    $continuation.label = 1;
                    v0 = it.await($continuation);
                    if (v0 != var25_9) continue;
                    return var25_9;
                }
                break;
            }
            case 1: {
                $i$f$forEach = false;
                $i$a$-forEach-BookHelp$saveImages$3 = false;
                var10_14 = (Iterator<T>)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
                ** GOTO lbl37
            }
        }
        return Unit.INSTANCE;
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Nullable
    public final Object saveImage(@Nullable BookSource var1_1, @NotNull Book var2_2, @NotNull String var3_3, @NotNull Continuation<? super Unit> var4_4) {
        if (!(var4_4 instanceof saveImage.1)) ** GOTO lbl-1000
        var13_5 = var4_4;
        if ((var13_5.label & -2147483648) != 0) {
            var13_5.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var4_4){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                /* synthetic */ Object result;
                final /* synthetic */ BookHelp this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.saveImage(null, null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var14_7 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
lbl12:
                // 3 sources

                while (BookHelp.downloadImages.contains(src)) {
                    $continuation.L$0 = this;
                    $continuation.L$1 = bookSource;
                    $continuation.L$2 = book;
                    $continuation.L$3 = src;
                    $continuation.label = 1;
                    v0 = DelayKt.delay(100L, $continuation);
                    if (v0 != var14_7) continue;
                    return var14_7;
                }
                break;
            }
            case 1: {
                src = (String)$continuation.L$3;
                book = (Book)$continuation.L$2;
                bookSource = (BookSource)$continuation.L$1;
                this = (BookHelp)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
                ** GOTO lbl12
            }
        }
        if (this.getImage(book, src).exists()) {
            return Unit.INSTANCE;
        }
        BookHelp.downloadImages.add(src);
        analyzeUrl = new AnalyzeUrl(src, null, null, null, null, null, bookSource, null, null, null, null, 1982, null);
        $continuation.L$0 = book;
        $continuation.L$1 = src;
        $continuation.L$2 = null;
        $continuation.L$3 = null;
        $continuation.label = 2;
        v1 = analyzeUrl.getByteArrayAwait($continuation);
        ** if (v1 != var14_7) goto lbl45
lbl44:
        // 1 sources

        return var14_7;
lbl45:
        // 1 sources

        ** GOTO lbl53
        {
            case 2: {
                var3_3 = (String)$continuation.L$1;
                var2_2 = (Book)$continuation.L$0;
                try {
                    ResultKt.throwOnFailure($result);
                    v1 = $result;
lbl53:
                    // 2 sources

                    var6_9 = v1;
                    var7_12 = false;
                    var8_13 = false;
                    it = (byte[])var6_9;
                    $i$a$-let-BookHelp$saveImage$2 = false;
                    var11_16 = new String[]{"images", MD5Utils.INSTANCE.md5Encode16(var3_3) + '.' + BookHelp.INSTANCE.getImageSuffix(var3_3)};
                    FilesKt.writeBytes(FileUtils.INSTANCE.createFileIfNotExist(BookHelp.INSTANCE.getBookCacheDir(var2_2), var11_16), it);
                    BookHelp.downloadImages.remove(var3_3);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return Unit.INSTANCE;
                }
                return Unit.INSTANCE;
            }
        }
        finally {
            BookHelp.downloadImages.remove(var3_3);
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @NotNull
    public final File getImage(@NotNull Book book, @NotNull String src) {
        Intrinsics.checkNotNullParameter(book, "book");
        Intrinsics.checkNotNullParameter(src, "src");
        String[] stringArray = new String[]{cacheImageFolderName, MD5Utils.INSTANCE.md5Encode16(src) + '.' + this.getImageSuffix(src)};
        return FileExtensionsKt.getFile(this.getBookCacheDir(book), stringArray);
    }

    @NotNull
    public final String getImageSuffix(@NotNull String src) {
        String suffix;
        block3: {
            block2: {
                Intrinsics.checkNotNullParameter(src, "src");
                suffix = StringsKt.substringBefore$default(StringsKt.substringAfterLast$default(src, ".", null, 2, null), ",", null, 2, null);
                Regex fileSuffixRegex = new Regex("^[a-z0-9]+$", RegexOption.IGNORE_CASE);
                if (suffix.length() > 5) break block2;
                CharSequence charSequence = suffix;
                boolean bl = false;
                if (fileSuffixRegex.matches(charSequence)) break block3;
            }
            suffix = "jpg";
        }
        return suffix;
    }
}

