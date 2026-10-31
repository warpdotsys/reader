/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.data.entities.rule;

import io.legado.app.data.entities.rule.BookListRule;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b,\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B}\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\rJ\u000b\u0010$\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010'\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010+\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0081\u0001\u0010.\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010/\u001a\u0002002\b\u00101\u001a\u0004\u0018\u000102H\u00d6\u0003J\t\u00103\u001a\u000204H\u00d6\u0001J\t\u00105\u001a\u00020\u0003H\u00d6\u0001R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000f\"\u0004\b\u0013\u0010\u0011R\u001c\u0010\n\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u000f\"\u0004\b\u0015\u0010\u0011R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000f\"\u0004\b\u0017\u0010\u0011R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u000f\"\u0004\b\u0019\u0010\u0011R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u000f\"\u0004\b\u001b\u0010\u0011R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u000f\"\u0004\b\u001d\u0010\u0011R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u000f\"\u0004\b\u001f\u0010\u0011R\u001c\u0010\t\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u000f\"\u0004\b!\u0010\u0011R\u001c\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u000f\"\u0004\b#\u0010\u0011\u00a8\u00066"}, d2={"Lio/legado/app/data/entities/rule/ExploreRule;", "Lio/legado/app/data/entities/rule/BookListRule;", "bookList", "", "name", "author", "intro", "kind", "lastChapter", "updateTime", "bookUrl", "coverUrl", "wordCount", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAuthor", "()Ljava/lang/String;", "setAuthor", "(Ljava/lang/String;)V", "getBookList", "setBookList", "getBookUrl", "setBookUrl", "getCoverUrl", "setCoverUrl", "getIntro", "setIntro", "getKind", "setKind", "getLastChapter", "setLastChapter", "getName", "setName", "getUpdateTime", "setUpdateTime", "getWordCount", "setWordCount", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "", "hashCode", "", "toString", "reader-pro"})
public final class ExploreRule
implements BookListRule {
    @Nullable
    private String bookList;
    @Nullable
    private String name;
    @Nullable
    private String author;
    @Nullable
    private String intro;
    @Nullable
    private String kind;
    @Nullable
    private String lastChapter;
    @Nullable
    private String updateTime;
    @Nullable
    private String bookUrl;
    @Nullable
    private String coverUrl;
    @Nullable
    private String wordCount;

    public ExploreRule(@Nullable String bookList, @Nullable String name, @Nullable String author, @Nullable String intro, @Nullable String kind, @Nullable String lastChapter, @Nullable String updateTime, @Nullable String bookUrl, @Nullable String coverUrl, @Nullable String wordCount) {
        this.bookList = bookList;
        this.name = name;
        this.author = author;
        this.intro = intro;
        this.kind = kind;
        this.lastChapter = lastChapter;
        this.updateTime = updateTime;
        this.bookUrl = bookUrl;
        this.coverUrl = coverUrl;
        this.wordCount = wordCount;
    }

    public /* synthetic */ ExploreRule(String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            string = null;
        }
        if ((n & 2) != 0) {
            string2 = null;
        }
        if ((n & 4) != 0) {
            string3 = null;
        }
        if ((n & 8) != 0) {
            string4 = null;
        }
        if ((n & 0x10) != 0) {
            string5 = null;
        }
        if ((n & 0x20) != 0) {
            string6 = null;
        }
        if ((n & 0x40) != 0) {
            string7 = null;
        }
        if ((n & 0x80) != 0) {
            string8 = null;
        }
        if ((n & 0x100) != 0) {
            string9 = null;
        }
        if ((n & 0x200) != 0) {
            string10 = null;
        }
        this(string, string2, string3, string4, string5, string6, string7, string8, string9, string10);
    }

    @Override
    @Nullable
    public String getBookList() {
        return this.bookList;
    }

    @Override
    public void setBookList(@Nullable String string) {
        this.bookList = string;
    }

    @Override
    @Nullable
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(@Nullable String string) {
        this.name = string;
    }

    @Override
    @Nullable
    public String getAuthor() {
        return this.author;
    }

    @Override
    public void setAuthor(@Nullable String string) {
        this.author = string;
    }

    @Override
    @Nullable
    public String getIntro() {
        return this.intro;
    }

    @Override
    public void setIntro(@Nullable String string) {
        this.intro = string;
    }

    @Override
    @Nullable
    public String getKind() {
        return this.kind;
    }

    @Override
    public void setKind(@Nullable String string) {
        this.kind = string;
    }

    @Override
    @Nullable
    public String getLastChapter() {
        return this.lastChapter;
    }

    @Override
    public void setLastChapter(@Nullable String string) {
        this.lastChapter = string;
    }

    @Override
    @Nullable
    public String getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(@Nullable String string) {
        this.updateTime = string;
    }

    @Override
    @Nullable
    public String getBookUrl() {
        return this.bookUrl;
    }

    @Override
    public void setBookUrl(@Nullable String string) {
        this.bookUrl = string;
    }

    @Override
    @Nullable
    public String getCoverUrl() {
        return this.coverUrl;
    }

    @Override
    public void setCoverUrl(@Nullable String string) {
        this.coverUrl = string;
    }

    @Override
    @Nullable
    public String getWordCount() {
        return this.wordCount;
    }

    @Override
    public void setWordCount(@Nullable String string) {
        this.wordCount = string;
    }

    @Nullable
    public final String component1() {
        return this.getBookList();
    }

    @Nullable
    public final String component2() {
        return this.getName();
    }

    @Nullable
    public final String component3() {
        return this.getAuthor();
    }

    @Nullable
    public final String component4() {
        return this.getIntro();
    }

    @Nullable
    public final String component5() {
        return this.getKind();
    }

    @Nullable
    public final String component6() {
        return this.getLastChapter();
    }

    @Nullable
    public final String component7() {
        return this.getUpdateTime();
    }

    @Nullable
    public final String component8() {
        return this.getBookUrl();
    }

    @Nullable
    public final String component9() {
        return this.getCoverUrl();
    }

    @Nullable
    public final String component10() {
        return this.getWordCount();
    }

    @NotNull
    public final ExploreRule copy(@Nullable String bookList, @Nullable String name, @Nullable String author, @Nullable String intro, @Nullable String kind, @Nullable String lastChapter, @Nullable String updateTime, @Nullable String bookUrl, @Nullable String coverUrl, @Nullable String wordCount) {
        return new ExploreRule(bookList, name, author, intro, kind, lastChapter, updateTime, bookUrl, coverUrl, wordCount);
    }

    public static /* synthetic */ ExploreRule copy$default(ExploreRule exploreRule, String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, int n, Object object) {
        if ((n & 1) != 0) {
            string = exploreRule.getBookList();
        }
        if ((n & 2) != 0) {
            string2 = exploreRule.getName();
        }
        if ((n & 4) != 0) {
            string3 = exploreRule.getAuthor();
        }
        if ((n & 8) != 0) {
            string4 = exploreRule.getIntro();
        }
        if ((n & 0x10) != 0) {
            string5 = exploreRule.getKind();
        }
        if ((n & 0x20) != 0) {
            string6 = exploreRule.getLastChapter();
        }
        if ((n & 0x40) != 0) {
            string7 = exploreRule.getUpdateTime();
        }
        if ((n & 0x80) != 0) {
            string8 = exploreRule.getBookUrl();
        }
        if ((n & 0x100) != 0) {
            string9 = exploreRule.getCoverUrl();
        }
        if ((n & 0x200) != 0) {
            string10 = exploreRule.getWordCount();
        }
        return exploreRule.copy(string, string2, string3, string4, string5, string6, string7, string8, string9, string10);
    }

    @NotNull
    public String toString() {
        return "ExploreRule(bookList=" + this.getBookList() + ", name=" + this.getName() + ", author=" + this.getAuthor() + ", intro=" + this.getIntro() + ", kind=" + this.getKind() + ", lastChapter=" + this.getLastChapter() + ", updateTime=" + this.getUpdateTime() + ", bookUrl=" + this.getBookUrl() + ", coverUrl=" + this.getCoverUrl() + ", wordCount=" + this.getWordCount() + ')';
    }

    public int hashCode() {
        int result2 = this.getBookList() == null ? 0 : this.getBookList().hashCode();
        result2 = result2 * 31 + (this.getName() == null ? 0 : this.getName().hashCode());
        result2 = result2 * 31 + (this.getAuthor() == null ? 0 : this.getAuthor().hashCode());
        result2 = result2 * 31 + (this.getIntro() == null ? 0 : this.getIntro().hashCode());
        result2 = result2 * 31 + (this.getKind() == null ? 0 : this.getKind().hashCode());
        result2 = result2 * 31 + (this.getLastChapter() == null ? 0 : this.getLastChapter().hashCode());
        result2 = result2 * 31 + (this.getUpdateTime() == null ? 0 : this.getUpdateTime().hashCode());
        result2 = result2 * 31 + (this.getBookUrl() == null ? 0 : this.getBookUrl().hashCode());
        result2 = result2 * 31 + (this.getCoverUrl() == null ? 0 : this.getCoverUrl().hashCode());
        result2 = result2 * 31 + (this.getWordCount() == null ? 0 : this.getWordCount().hashCode());
        return result2;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExploreRule)) {
            return false;
        }
        ExploreRule exploreRule = (ExploreRule)other;
        if (!Intrinsics.areEqual(this.getBookList(), exploreRule.getBookList())) {
            return false;
        }
        if (!Intrinsics.areEqual(this.getName(), exploreRule.getName())) {
            return false;
        }
        if (!Intrinsics.areEqual(this.getAuthor(), exploreRule.getAuthor())) {
            return false;
        }
        if (!Intrinsics.areEqual(this.getIntro(), exploreRule.getIntro())) {
            return false;
        }
        if (!Intrinsics.areEqual(this.getKind(), exploreRule.getKind())) {
            return false;
        }
        if (!Intrinsics.areEqual(this.getLastChapter(), exploreRule.getLastChapter())) {
            return false;
        }
        if (!Intrinsics.areEqual(this.getUpdateTime(), exploreRule.getUpdateTime())) {
            return false;
        }
        if (!Intrinsics.areEqual(this.getBookUrl(), exploreRule.getBookUrl())) {
            return false;
        }
        if (!Intrinsics.areEqual(this.getCoverUrl(), exploreRule.getCoverUrl())) {
            return false;
        }
        return Intrinsics.areEqual(this.getWordCount(), exploreRule.getWordCount());
    }

    public ExploreRule() {
        this(null, null, null, null, null, null, null, null, null, null, 1023, null);
    }
}

