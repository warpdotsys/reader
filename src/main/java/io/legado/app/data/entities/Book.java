/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.Predicate;
import io.legado.app.constant.AppPattern;
import io.legado.app.data.entities.BaseBook;
import io.legado.app.data.entities.BookKt;
import io.legado.app.data.entities.SearchBook;
import io.legado.app.model.localBook.CbzFile;
import io.legado.app.model.localBook.EpubFile;
import io.legado.app.model.localBook.LocalBook;
import io.legado.app.model.localBook.UmdFile;
import io.legado.app.utils.FileUtils;
import io.legado.app.utils.GsonExtensionsKt;
import io.legado.app.utils.JsonExtensionsKt;
import io.legado.app.utils.MD5Utils;
import java.io.File;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@JsonIgnoreProperties(value={"variableMap", "infoHtml", "tocHtml", "config", "rootDir", "localBook", "epub", "epubRootDir", "onLineTxt", "localTxt", "umd", "realAuthor", "unreadChapterNum", "folderName", "pdfImageWidth", "localFile", "kindList", "_userNameSpace", "bookDir", "userNameSpace"})
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\bU\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b+\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\b\u0087\b\u0018\u0000 \u00d5\u00012\u00020\u0001:\u0006\u00d5\u0001\u00d6\u0001\u00d7\u0001B\u00e9\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0011\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0013\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u001f\u0012\b\b\u0002\u0010 \u001a\u00020\u0011\u0012\b\b\u0002\u0010!\u001a\u00020\u0011\u0012\b\b\u0002\u0010\"\u001a\u00020\u001f\u0012\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010$\u001a\u0004\u0018\u00010%\u0012\b\b\u0002\u0010&\u001a\u00020\u001f\u0012\n\b\u0002\u0010'\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010(J\n\u0010\u0083\u0001\u001a\u00020\u0003H\u00c6\u0003J\f\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\f\u0010\u0085\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\f\u0010\u0086\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\f\u0010\u0087\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\n\u0010\u0088\u0001\u001a\u00020\u0011H\u00c6\u0003J\n\u0010\u0089\u0001\u001a\u00020\u0013H\u00c6\u0003J\f\u0010\u008a\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\n\u0010\u008b\u0001\u001a\u00020\u0013H\u00c6\u0003J\n\u0010\u008c\u0001\u001a\u00020\u0013H\u00c6\u0003J\n\u0010\u008d\u0001\u001a\u00020\u0011H\u00c6\u0003J\n\u0010\u008e\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u008f\u0001\u001a\u00020\u0011H\u00c6\u0003J\f\u0010\u0090\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\n\u0010\u0091\u0001\u001a\u00020\u0011H\u00c6\u0003J\n\u0010\u0092\u0001\u001a\u00020\u0011H\u00c6\u0003J\n\u0010\u0093\u0001\u001a\u00020\u0013H\u00c6\u0003J\f\u0010\u0094\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\n\u0010\u0095\u0001\u001a\u00020\u001fH\u00c6\u0003J\n\u0010\u0096\u0001\u001a\u00020\u0011H\u00c6\u0003J\n\u0010\u0097\u0001\u001a\u00020\u0011H\u00c6\u0003J\n\u0010\u0098\u0001\u001a\u00020\u001fH\u00c6\u0003J\n\u0010\u0099\u0001\u001a\u00020\u0003H\u00c6\u0003J\f\u0010\u009a\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\f\u0010\u009b\u0001\u001a\u0004\u0018\u00010%H\u00c6\u0003J\n\u0010\u009c\u0001\u001a\u00020\u001fH\u00c6\u0003J\f\u0010\u009d\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\n\u0010\u009e\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u009f\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00a0\u0001\u001a\u00020\u0003H\u00c6\u0003J\f\u0010\u00a1\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\f\u0010\u00a2\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\f\u0010\u00a3\u0001\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\u00a4\u0001\u001a\u00020%H\u0002J\u00ee\u0002\u0010\u00a5\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00132\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u00132\b\b\u0002\u0010\u0017\u001a\u00020\u00112\b\b\u0002\u0010\u0018\u001a\u00020\u00112\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u001a\u001a\u00020\u00112\b\b\u0002\u0010\u001b\u001a\u00020\u00112\b\b\u0002\u0010\u001c\u001a\u00020\u00132\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u001e\u001a\u00020\u001f2\b\b\u0002\u0010 \u001a\u00020\u00112\b\b\u0002\u0010!\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020\u001f2\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010$\u001a\u0004\u0018\u00010%2\b\b\u0002\u0010&\u001a\u00020\u001f2\n\b\u0002\u0010'\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0016\u0010\u00a6\u0001\u001a\u00020\u001f2\n\u0010\u00a7\u0001\u001a\u0005\u0018\u00010\u00a8\u0001H\u0096\u0002J\b\u0010\u00a9\u0001\u001a\u00030\u00aa\u0001J\u0007\u0010\u00ab\u0001\u001a\u00020\u0003J\u0010\u0010\u00ac\u0001\u001a\u00020\u001f2\u0007\u0010\u00ad\u0001\u001a\u00020\u0013J\t\u0010\u00ae\u0001\u001a\u0004\u0018\u00010\u0003J\t\u0010\u00af\u0001\u001a\u0004\u0018\u00010\u0003J\u0007\u0010\u00b0\u0001\u001a\u00020\u0003J\u0007\u0010\u00b1\u0001\u001a\u00020\u0003J\b\u0010\u00b2\u0001\u001a\u00030\u00b3\u0001J\b\u0010\u00b4\u0001\u001a\u00030\u00b5\u0001J\u0007\u0010\u00b6\u0001\u001a\u00020\u0003J\u0007\u0010\u00b7\u0001\u001a\u00020\u001fJ\u0007\u0010\u00b8\u0001\u001a\u00020\u0011J\t\u0010\u00b9\u0001\u001a\u00020\u0003H\u0016J\t\u0010\u00ba\u0001\u001a\u00020\u0011H\u0016J\u0007\u0010\u00bb\u0001\u001a\u00020\u001fJ\u0007\u0010\u00bc\u0001\u001a\u00020\u001fJ\u0007\u0010\u00bd\u0001\u001a\u00020\u001fJ\u0007\u0010\u00be\u0001\u001a\u00020\u001fJ\u0007\u0010\u00bf\u0001\u001a\u00020\u001fJ\u0007\u0010\u00c0\u0001\u001a\u00020\u001fJ\u0007\u0010\u00c1\u0001\u001a\u00020\u001fJ\u0007\u0010\u00c2\u0001\u001a\u00020\u001fJ\u0007\u0010\u00c3\u0001\u001a\u00020\u001fJ\u001e\u0010\u00c4\u0001\u001a\u00030\u00c5\u00012\u0007\u0010\u00c6\u0001\u001a\u00020\u00032\t\u0010\u00c7\u0001\u001a\u0004\u0018\u00010\u0003H\u0016J\u0011\u0010\u00c8\u0001\u001a\u00030\u00c5\u00012\u0007\u0010\u00ad\u0001\u001a\u00020\u0013J\u0012\u0010\u00c9\u0001\u001a\u00030\u00c5\u00012\b\u0010\u00ca\u0001\u001a\u00030\u00b5\u0001J\u0011\u0010\u00cb\u0001\u001a\u00030\u00c5\u00012\u0007\u0010\u00cc\u0001\u001a\u00020\u0003J\u0011\u0010\u00cd\u0001\u001a\u00030\u00c5\u00012\u0007\u0010\u00ce\u0001\u001a\u00020\u0003J\b\u0010\u00cf\u0001\u001a\u00030\u00d0\u0001J\n\u0010\u00d1\u0001\u001a\u00020\u0003H\u00d6\u0001J\u0013\u0010\u00d2\u0001\u001a\u00030\u00c5\u00012\t\b\u0002\u0010\u00d3\u0001\u001a\u00020\u001fJ\u0007\u0010\u00d4\u0001\u001a\u00020\u0003R\u000e\u0010)\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b.\u0010+\"\u0004\b/\u0010-R\u001a\u0010\u001e\u001a\u00020\u001fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b4\u0010+\"\u0004\b5\u0010-R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b6\u0010+\"\u0004\b7\u0010-R\u001c\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b8\u0010+\"\u0004\b9\u0010-R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b:\u0010+\"\u0004\b;\u0010-R\u001c\u0010\n\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b<\u0010+\"\u0004\b=\u0010-R\u001a\u0010\u001a\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b>\u0010?\"\u0004\b@\u0010AR\u001a\u0010\u001b\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bB\u0010?\"\u0004\bC\u0010AR\u001a\u0010\u001c\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bD\u0010E\"\u0004\bF\u0010GR\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bH\u0010+\"\u0004\bI\u0010-R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010E\"\u0004\bK\u0010GR\u001c\u0010L\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bM\u0010+\"\u0004\bN\u0010-R\u001c\u0010\r\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bO\u0010+\"\u0004\bP\u0010-R\u001c\u0010&\u001a\u00020\u001f8\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u00101\"\u0004\bQ\u00103R\u001c\u0010\t\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bR\u0010+\"\u0004\bS\u0010-R\u001a\u0010\u0017\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bT\u0010?\"\u0004\bU\u0010AR\u001c\u0010'\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bV\u0010+\"\u0004\bW\u0010-R\u001a\u0010\u0016\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bX\u0010E\"\u0004\bY\u0010GR\u001a\u0010\u0015\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bZ\u0010E\"\u0004\b[\u0010GR\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\\\u0010+\"\u0004\b]\u0010-R\u001a\u0010\u0007\u001a\u00020\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b^\u0010+\"\u0004\b_\u0010-R\u001a\u0010 \u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b`\u0010?\"\u0004\ba\u0010AR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bb\u0010+\"\u0004\bc\u0010-R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bd\u0010+\"\u0004\be\u0010-R\u001a\u0010!\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bf\u0010?\"\u0004\bg\u0010AR\u001c\u0010$\u001a\u0004\u0018\u00010%X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bh\u0010i\"\u0004\bj\u0010kR\u000e\u0010l\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010m\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bn\u0010+\"\u0004\bo\u0010-R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bp\u0010+\"\u0004\bq\u0010-R\u001a\u0010\u0018\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\br\u0010?\"\u0004\bs\u0010AR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bt\u0010?\"\u0004\bu\u0010AR\u001a\u0010\"\u001a\u00020\u001fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bv\u00101\"\u0004\bw\u00103R\u001c\u0010#\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bx\u0010+\"\u0004\by\u0010-R8\u0010z\u001a\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030{j\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003`|8VX\u0096\u0084\u0002\u00a2\u0006\r\n\u0005\b\u007f\u0010\u0080\u0001\u001a\u0004\b}\u0010~R\u001e\u0010\u001d\u001a\u0004\u0018\u00010\u0003X\u0096\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0081\u0001\u0010+\"\u0005\b\u0082\u0001\u0010-\u00a8\u0006\u00d8\u0001"}, d2={"Lio/legado/app/data/entities/Book;", "Lio/legado/app/data/entities/BaseBook;", "bookUrl", "", "tocUrl", "origin", "originName", "name", "author", "kind", "customTag", "coverUrl", "customCoverUrl", "intro", "customIntro", "charset", "type", "", "group", "", "latestChapterTitle", "latestChapterTime", "lastCheckTime", "lastCheckCount", "totalChapterNum", "durChapterTitle", "durChapterIndex", "durChapterPos", "durChapterTime", "wordCount", "canUpdate", "", "order", "originOrder", "useReplaceRule", "variable", "readConfig", "Lio/legado/app/data/entities/Book$ReadConfig;", "isInShelf", "lastCheckError", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IJLjava/lang/String;JJIILjava/lang/String;IIJLjava/lang/String;ZIIZLjava/lang/String;Lio/legado/app/data/entities/Book$ReadConfig;ZLjava/lang/String;)V", "_userNameSpace", "getAuthor", "()Ljava/lang/String;", "setAuthor", "(Ljava/lang/String;)V", "getBookUrl", "setBookUrl", "getCanUpdate", "()Z", "setCanUpdate", "(Z)V", "getCharset", "setCharset", "getCoverUrl", "setCoverUrl", "getCustomCoverUrl", "setCustomCoverUrl", "getCustomIntro", "setCustomIntro", "getCustomTag", "setCustomTag", "getDurChapterIndex", "()I", "setDurChapterIndex", "(I)V", "getDurChapterPos", "setDurChapterPos", "getDurChapterTime", "()J", "setDurChapterTime", "(J)V", "getDurChapterTitle", "setDurChapterTitle", "getGroup", "setGroup", "infoHtml", "getInfoHtml", "setInfoHtml", "getIntro", "setIntro", "setInShelf", "getKind", "setKind", "getLastCheckCount", "setLastCheckCount", "getLastCheckError", "setLastCheckError", "getLastCheckTime", "setLastCheckTime", "getLatestChapterTime", "setLatestChapterTime", "getLatestChapterTitle", "setLatestChapterTitle", "getName", "setName", "getOrder", "setOrder", "getOrigin", "setOrigin", "getOriginName", "setOriginName", "getOriginOrder", "setOriginOrder", "getReadConfig", "()Lio/legado/app/data/entities/Book$ReadConfig;", "setReadConfig", "(Lio/legado/app/data/entities/Book$ReadConfig;)V", "rootDir", "tocHtml", "getTocHtml", "setTocHtml", "getTocUrl", "setTocUrl", "getTotalChapterNum", "setTotalChapterNum", "getType", "setType", "getUseReplaceRule", "setUseReplaceRule", "getVariable", "setVariable", "variableMap", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "getVariableMap", "()Ljava/util/HashMap;", "variableMap$delegate", "Lkotlin/Lazy;", "getWordCount", "setWordCount", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component31", "component32", "component33", "component4", "component5", "component6", "component7", "component8", "component9", "config", "copy", "equals", "other", "", "fileCharset", "Ljava/nio/charset/Charset;", "getBookDir", "getDelTag", "tag", "getDisplayCover", "getDisplayIntro", "getEpubRootDir", "getFolderName", "getLocalFile", "Ljava/io/File;", "getPdfImageWidth", "", "getRealAuthor", "getSplitLongChapter", "getUnreadChapterNum", "getUserNameSpace", "hashCode", "isCbz", "isEpub", "isLocalBook", "isLocalEpub", "isLocalPdf", "isLocalTxt", "isOnLineTxt", "isPdf", "isUmd", "putVariable", "", "key", "value", "setDelTag", "setPdfImageWidth", "pdfImageWidth", "setRootDir", "root", "setUserNameSpace", "nameSpace", "toSearchBook", "Lio/legado/app/data/entities/SearchBook;", "toString", "updateFromLocal", "onlyCover", "workRoot", "Companion", "Converters", "ReadConfig", "reader-pro"})
public final class Book
implements BaseBook {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private String bookUrl;
    @NotNull
    private String tocUrl;
    @NotNull
    private String origin;
    @NotNull
    private String originName;
    @NotNull
    private String name;
    @NotNull
    private String author;
    @Nullable
    private String kind;
    @Nullable
    private String customTag;
    @Nullable
    private String coverUrl;
    @Nullable
    private String customCoverUrl;
    @Nullable
    private String intro;
    @Nullable
    private String customIntro;
    @Nullable
    private String charset;
    private int type;
    private long group;
    @Nullable
    private String latestChapterTitle;
    private long latestChapterTime;
    private long lastCheckTime;
    private int lastCheckCount;
    private int totalChapterNum;
    @Nullable
    private String durChapterTitle;
    private int durChapterIndex;
    private int durChapterPos;
    private long durChapterTime;
    @Nullable
    private String wordCount;
    private boolean canUpdate;
    private int order;
    private int originOrder;
    private boolean useReplaceRule;
    @Nullable
    private String variable;
    @Nullable
    private ReadConfig readConfig;
    private boolean isInShelf;
    @Nullable
    private String lastCheckError;
    @NotNull
    private final transient Lazy variableMap$delegate;
    @Nullable
    private String infoHtml;
    @Nullable
    private String tocHtml;
    @NotNull
    private transient String rootDir;
    @NotNull
    private transient String _userNameSpace;
    public static final long hTag = 2L;
    public static final long rubyTag = 4L;
    public static final long imgTag = 8L;
    @NotNull
    public static final String imgStyleDefault = "DEFAULT";
    @NotNull
    public static final String imgStyleFull = "FULL";
    @NotNull
    public static final String imgStyleText = "TEXT";

    public Book(@NotNull String bookUrl, @NotNull String tocUrl, @NotNull String origin, @NotNull String originName, @NotNull String name, @NotNull String author, @Nullable String kind, @Nullable String customTag, @Nullable String coverUrl, @Nullable String customCoverUrl, @Nullable String intro, @Nullable String customIntro, @Nullable String charset, int type, long group, @Nullable String latestChapterTitle, long latestChapterTime, long lastCheckTime, int lastCheckCount, int totalChapterNum, @Nullable String durChapterTitle, int durChapterIndex, int durChapterPos, long durChapterTime, @Nullable String wordCount, boolean canUpdate, int order, int originOrder, boolean useReplaceRule, @Nullable String variable, @Nullable ReadConfig readConfig, boolean isInShelf, @Nullable String lastCheckError) {
        Intrinsics.checkNotNullParameter(bookUrl, "bookUrl");
        Intrinsics.checkNotNullParameter(tocUrl, "tocUrl");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(originName, "originName");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(author, "author");
        this.bookUrl = bookUrl;
        this.tocUrl = tocUrl;
        this.origin = origin;
        this.originName = originName;
        this.name = name;
        this.author = author;
        this.kind = kind;
        this.customTag = customTag;
        this.coverUrl = coverUrl;
        this.customCoverUrl = customCoverUrl;
        this.intro = intro;
        this.customIntro = customIntro;
        this.charset = charset;
        this.type = type;
        this.group = group;
        this.latestChapterTitle = latestChapterTitle;
        this.latestChapterTime = latestChapterTime;
        this.lastCheckTime = lastCheckTime;
        this.lastCheckCount = lastCheckCount;
        this.totalChapterNum = totalChapterNum;
        this.durChapterTitle = durChapterTitle;
        this.durChapterIndex = durChapterIndex;
        this.durChapterPos = durChapterPos;
        this.durChapterTime = durChapterTime;
        this.wordCount = wordCount;
        this.canUpdate = canUpdate;
        this.order = order;
        this.originOrder = originOrder;
        this.useReplaceRule = useReplaceRule;
        this.variable = variable;
        this.readConfig = readConfig;
        this.isInShelf = isInShelf;
        this.lastCheckError = lastCheckError;
        this.variableMap$delegate = LazyKt.lazy((Function0)new Function0<HashMap<String, String>>(this){
            final /* synthetic */ Book this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final HashMap<String, String> invoke() {
                HashMap hashMap;
                Object object;
                Object object2 = GsonExtensionsKt.getGSON();
                String json$iv = this.this$0.getVariable();
                boolean $i$f$fromJsonObject = false;
                boolean bl = false;
                try {
                    void $this$fromJsonObject$iv;
                    object = Result.Companion;
                    boolean bl2 = false;
                    boolean $i$f$genericType = false;
                    Type type = new TypeToken<HashMap<String, String>>(){}.getType();
                    Intrinsics.checkNotNullExpressionValue(type, "object : TypeToken<T>() {}.type");
                    T t = $this$fromJsonObject$iv.fromJson(json$iv, type);
                    if (!(t instanceof HashMap)) {
                        t = null;
                    }
                    HashMap hashMap2 = (HashMap)t;
                    boolean bl3 = false;
                    object = Result.constructor-impl(hashMap2);
                }
                catch (Throwable throwable) {
                    Result.Companion companion = Result.Companion;
                    boolean bl4 = false;
                    object = Result.constructor-impl(ResultKt.createFailure(throwable));
                }
                object2 = object;
                boolean bl5 = false;
                HashMap hashMap3 = (HashMap)(Result.isFailure-impl(object2) ? null : object2);
                if (hashMap3 == null) {
                    boolean bl6 = false;
                    hashMap = new HashMap<K, V>();
                } else {
                    hashMap = hashMap3;
                }
                return hashMap;
            }
        });
        this.rootDir = "";
        this._userNameSpace = "";
    }

    public /* synthetic */ Book(String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, String string11, String string12, String string13, int n, long l, String string14, long l2, long l3, int n2, int n3, String string15, int n4, int n5, long l4, String string16, boolean bl, int n6, int n7, boolean bl2, String string17, ReadConfig readConfig, boolean bl3, String string18, int n8, int n9, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n8 & 1) != 0) {
            string = "";
        }
        if ((n8 & 2) != 0) {
            string2 = "";
        }
        if ((n8 & 4) != 0) {
            string3 = "loc_book";
        }
        if ((n8 & 8) != 0) {
            string4 = "";
        }
        if ((n8 & 0x10) != 0) {
            string5 = "";
        }
        if ((n8 & 0x20) != 0) {
            string6 = "";
        }
        if ((n8 & 0x40) != 0) {
            string7 = null;
        }
        if ((n8 & 0x80) != 0) {
            string8 = null;
        }
        if ((n8 & 0x100) != 0) {
            string9 = null;
        }
        if ((n8 & 0x200) != 0) {
            string10 = null;
        }
        if ((n8 & 0x400) != 0) {
            string11 = null;
        }
        if ((n8 & 0x800) != 0) {
            string12 = null;
        }
        if ((n8 & 0x1000) != 0) {
            string13 = null;
        }
        if ((n8 & 0x2000) != 0) {
            n = 0;
        }
        if ((n8 & 0x4000) != 0) {
            l = 0L;
        }
        if ((n8 & 0x8000) != 0) {
            string14 = null;
        }
        if ((n8 & 0x10000) != 0) {
            l2 = System.currentTimeMillis();
        }
        if ((n8 & 0x20000) != 0) {
            l3 = System.currentTimeMillis();
        }
        if ((n8 & 0x40000) != 0) {
            n2 = 0;
        }
        if ((n8 & 0x80000) != 0) {
            n3 = 0;
        }
        if ((n8 & 0x100000) != 0) {
            string15 = null;
        }
        if ((n8 & 0x200000) != 0) {
            n4 = 0;
        }
        if ((n8 & 0x400000) != 0) {
            n5 = 0;
        }
        if ((n8 & 0x800000) != 0) {
            l4 = System.currentTimeMillis();
        }
        if ((n8 & 0x1000000) != 0) {
            string16 = null;
        }
        if ((n8 & 0x2000000) != 0) {
            bl = true;
        }
        if ((n8 & 0x4000000) != 0) {
            n6 = 0;
        }
        if ((n8 & 0x8000000) != 0) {
            n7 = 0;
        }
        if ((n8 & 0x10000000) != 0) {
            bl2 = true;
        }
        if ((n8 & 0x20000000) != 0) {
            string17 = null;
        }
        if ((n8 & 0x40000000) != 0) {
            readConfig = null;
        }
        if ((n8 & Integer.MIN_VALUE) != 0) {
            bl3 = false;
        }
        if ((n9 & 1) != 0) {
            string18 = null;
        }
        this(string, string2, string3, string4, string5, string6, string7, string8, string9, string10, string11, string12, string13, n, l, string14, l2, l3, n2, n3, string15, n4, n5, l4, string16, bl, n6, n7, bl2, string17, readConfig, bl3, string18);
    }

    @Override
    @NotNull
    public String getBookUrl() {
        return this.bookUrl;
    }

    @Override
    public void setBookUrl(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.bookUrl = string;
    }

    @NotNull
    public final String getTocUrl() {
        return this.tocUrl;
    }

    public final void setTocUrl(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.tocUrl = string;
    }

    @NotNull
    public final String getOrigin() {
        return this.origin;
    }

    public final void setOrigin(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.origin = string;
    }

    @NotNull
    public final String getOriginName() {
        return this.originName;
    }

    public final void setOriginName(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.originName = string;
    }

    @Override
    @NotNull
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.name = string;
    }

    @Override
    @NotNull
    public String getAuthor() {
        return this.author;
    }

    @Override
    public void setAuthor(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.author = string;
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

    @Nullable
    public final String getCustomTag() {
        return this.customTag;
    }

    public final void setCustomTag(@Nullable String string) {
        this.customTag = string;
    }

    @Nullable
    public final String getCoverUrl() {
        return this.coverUrl;
    }

    public final void setCoverUrl(@Nullable String string) {
        this.coverUrl = string;
    }

    @Nullable
    public final String getCustomCoverUrl() {
        return this.customCoverUrl;
    }

    public final void setCustomCoverUrl(@Nullable String string) {
        this.customCoverUrl = string;
    }

    @Nullable
    public final String getIntro() {
        return this.intro;
    }

    public final void setIntro(@Nullable String string) {
        this.intro = string;
    }

    @Nullable
    public final String getCustomIntro() {
        return this.customIntro;
    }

    public final void setCustomIntro(@Nullable String string) {
        this.customIntro = string;
    }

    @Nullable
    public final String getCharset() {
        return this.charset;
    }

    public final void setCharset(@Nullable String string) {
        this.charset = string;
    }

    public final int getType() {
        return this.type;
    }

    public final void setType(int n) {
        this.type = n;
    }

    public final long getGroup() {
        return this.group;
    }

    public final void setGroup(long l) {
        this.group = l;
    }

    @Nullable
    public final String getLatestChapterTitle() {
        return this.latestChapterTitle;
    }

    public final void setLatestChapterTitle(@Nullable String string) {
        this.latestChapterTitle = string;
    }

    public final long getLatestChapterTime() {
        return this.latestChapterTime;
    }

    public final void setLatestChapterTime(long l) {
        this.latestChapterTime = l;
    }

    public final long getLastCheckTime() {
        return this.lastCheckTime;
    }

    public final void setLastCheckTime(long l) {
        this.lastCheckTime = l;
    }

    public final int getLastCheckCount() {
        return this.lastCheckCount;
    }

    public final void setLastCheckCount(int n) {
        this.lastCheckCount = n;
    }

    public final int getTotalChapterNum() {
        return this.totalChapterNum;
    }

    public final void setTotalChapterNum(int n) {
        this.totalChapterNum = n;
    }

    @Nullable
    public final String getDurChapterTitle() {
        return this.durChapterTitle;
    }

    public final void setDurChapterTitle(@Nullable String string) {
        this.durChapterTitle = string;
    }

    public final int getDurChapterIndex() {
        return this.durChapterIndex;
    }

    public final void setDurChapterIndex(int n) {
        this.durChapterIndex = n;
    }

    public final int getDurChapterPos() {
        return this.durChapterPos;
    }

    public final void setDurChapterPos(int n) {
        this.durChapterPos = n;
    }

    public final long getDurChapterTime() {
        return this.durChapterTime;
    }

    public final void setDurChapterTime(long l) {
        this.durChapterTime = l;
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

    public final boolean getCanUpdate() {
        return this.canUpdate;
    }

    public final void setCanUpdate(boolean bl) {
        this.canUpdate = bl;
    }

    public final int getOrder() {
        return this.order;
    }

    public final void setOrder(int n) {
        this.order = n;
    }

    public final int getOriginOrder() {
        return this.originOrder;
    }

    public final void setOriginOrder(int n) {
        this.originOrder = n;
    }

    public final boolean getUseReplaceRule() {
        return this.useReplaceRule;
    }

    public final void setUseReplaceRule(boolean bl) {
        this.useReplaceRule = bl;
    }

    @Nullable
    public final String getVariable() {
        return this.variable;
    }

    public final void setVariable(@Nullable String string) {
        this.variable = string;
    }

    @Nullable
    public final ReadConfig getReadConfig() {
        return this.readConfig;
    }

    public final void setReadConfig(@Nullable ReadConfig readConfig) {
        this.readConfig = readConfig;
    }

    @JsonProperty(value="isInShelf")
    public final boolean isInShelf() {
        return this.isInShelf;
    }

    public final void setInShelf(boolean bl) {
        this.isInShelf = bl;
    }

    @Nullable
    public final String getLastCheckError() {
        return this.lastCheckError;
    }

    public final void setLastCheckError(@Nullable String string) {
        this.lastCheckError = string;
    }

    public final boolean isLocalBook() {
        return Intrinsics.areEqual(this.origin, "loc_book");
    }

    public final boolean isLocalTxt() {
        return this.isLocalBook() && StringsKt.endsWith(this.originName, ".txt", true);
    }

    public final boolean isLocalEpub() {
        return this.isLocalBook() && StringsKt.endsWith(this.originName, ".epub", true);
    }

    public final boolean isLocalPdf() {
        return this.isLocalBook() && StringsKt.endsWith(this.originName, ".pdf", true);
    }

    public final boolean isEpub() {
        return StringsKt.endsWith(this.originName, ".epub", true);
    }

    public final boolean isCbz() {
        return StringsKt.endsWith(this.originName, ".cbz", true);
    }

    public final boolean isPdf() {
        return StringsKt.endsWith(this.originName, ".pdf", true);
    }

    public final boolean isUmd() {
        return StringsKt.endsWith(this.originName, ".umd", true);
    }

    public final boolean isOnLineTxt() {
        return !this.isLocalBook() && this.type == 0;
    }

    public boolean equals(@Nullable Object other) {
        if (other instanceof Book) {
            return Intrinsics.areEqual(((Book)other).getBookUrl(), this.getBookUrl());
        }
        return false;
    }

    public int hashCode() {
        return this.getBookUrl().hashCode();
    }

    @Override
    @NotNull
    public HashMap<String, String> getVariableMap() {
        Lazy lazy = this.variableMap$delegate;
        boolean bl = false;
        return (HashMap)lazy.getValue();
    }

    @Override
    public void putVariable(@NotNull String key, @Nullable String value) {
        Intrinsics.checkNotNullParameter(key, "key");
        if (value != null) {
            Map map = this.getVariableMap();
            boolean bl = false;
            map.put(key, value);
        } else {
            this.getVariableMap().remove(key);
        }
        this.variable = GsonExtensionsKt.getGSON().toJson(this.getVariableMap());
    }

    @Override
    @Nullable
    public String getInfoHtml() {
        return this.infoHtml;
    }

    @Override
    public void setInfoHtml(@Nullable String string) {
        this.infoHtml = string;
    }

    @Override
    @Nullable
    public String getTocHtml() {
        return this.tocHtml;
    }

    @Override
    public void setTocHtml(@Nullable String string) {
        this.tocHtml = string;
    }

    @NotNull
    public final String getRealAuthor() {
        CharSequence charSequence = this.getAuthor();
        Regex regex = AppPattern.INSTANCE.getAuthorRegex();
        String string = "";
        boolean bl = false;
        return regex.replace(charSequence, string);
    }

    public final int getUnreadChapterNum() {
        int n = this.totalChapterNum - this.durChapterIndex - 1;
        int n2 = 0;
        boolean bl = false;
        return Math.max(n, n2);
    }

    @Nullable
    public final String getDisplayCover() {
        CharSequence charSequence = this.customCoverUrl;
        boolean bl = false;
        boolean bl2 = false;
        return charSequence == null || charSequence.length() == 0 ? this.coverUrl : this.customCoverUrl;
    }

    @Nullable
    public final String getDisplayIntro() {
        CharSequence charSequence = this.customIntro;
        boolean bl = false;
        boolean bl2 = false;
        return charSequence == null || charSequence.length() == 0 ? this.intro : this.customIntro;
    }

    @NotNull
    public final Charset fileCharset() {
        String string = this.charset;
        string = string == null ? "UTF-8" : string;
        boolean bl = false;
        Charset charset = Charset.forName(string);
        Intrinsics.checkNotNullExpressionValue(charset, "Charset.forName(charsetName)");
        return charset;
    }

    private final ReadConfig config() {
        if (this.readConfig == null) {
            this.readConfig = new ReadConfig(false, 0, false, null, false, 0L, 0.0f, 127, null);
        }
        ReadConfig readConfig = this.readConfig;
        Intrinsics.checkNotNull(readConfig);
        return readConfig;
    }

    public final void setDelTag(long tag) {
        this.config().setDelTag((this.config().getDelTag() & tag) == tag ? this.config().getDelTag() & (tag ^ 0xFFFFFFFFFFFFFFFFL) : this.config().getDelTag() | tag);
    }

    public final boolean getDelTag(long tag) {
        return (this.config().getDelTag() & tag) == tag;
    }

    public final float getPdfImageWidth() {
        return this.config().getPdfImageWidth();
    }

    public final void setPdfImageWidth(float pdfImageWidth) {
        this.config().setPdfImageWidth(pdfImageWidth);
    }

    @NotNull
    public final String getFolderName() {
        CharSequence charSequence = this.getName();
        Regex regex = AppPattern.INSTANCE.getFileNameRegex();
        String string = "";
        int n = 0;
        String folderName = regex.replace(charSequence, string);
        charSequence = folderName;
        int n2 = 0;
        int n3 = 9;
        n = folderName.length();
        boolean bl = false;
        n3 = Math.min(n3, n);
        n = 0;
        CharSequence charSequence2 = charSequence;
        if (charSequence2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        }
        String string2 = ((String)charSequence2).substring(n2, n3);
        Intrinsics.checkNotNullExpressionValue(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        folderName = string2;
        return Intrinsics.stringPlus(folderName, MD5Utils.INSTANCE.md5Encode16(this.getBookUrl()));
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void setRootDir(@NotNull String root) {
        Intrinsics.checkNotNullParameter(root, "root");
        CharSequence charSequence = root;
        boolean bl = false;
        if (charSequence.length() > 0) {
            charSequence = File.separator;
            Intrinsics.checkNotNullExpressionValue(charSequence, "separator");
            if (!StringsKt.endsWith$default(root, (String)charSequence, false, 2, null)) {
                this.rootDir = Intrinsics.stringPlus(root, File.separator);
                return;
            }
        }
        this.rootDir = root;
    }

    @NotNull
    public final File getLocalFile() {
        if (StringsKt.startsWith$default(this.originName, this.rootDir, false, 2, null)) {
            this.originName = StringsKt.replaceFirst$default(this.originName, this.rootDir, "", false, 4, null);
        }
        BookKt.getLogger().info("getLocalFile rootDir: {} originName: {}", (Object)this.rootDir, (Object)this.originName);
        if (this.isEpub() && StringsKt.indexOf$default((CharSequence)this.originName, "localStore", 0, false, 6, null) < 0 && StringsKt.indexOf$default((CharSequence)this.originName, "webdav", 0, false, 6, null) < 0) {
            String[] stringArray = new String[]{"index.epub"};
            return FileUtils.INSTANCE.getFile(new File(Intrinsics.stringPlus(this.rootDir, this.originName)), stringArray);
        }
        if (this.isCbz() && StringsKt.indexOf$default((CharSequence)this.originName, "localStore", 0, false, 6, null) < 0 && StringsKt.indexOf$default((CharSequence)this.originName, "webdav", 0, false, 6, null) < 0) {
            String[] stringArray = new String[]{"index.cbz"};
            return FileUtils.INSTANCE.getFile(new File(Intrinsics.stringPlus(this.rootDir, this.originName)), stringArray);
        }
        if (this.isPdf() && StringsKt.indexOf$default((CharSequence)this.originName, "localStore", 0, false, 6, null) < 0 && StringsKt.indexOf$default((CharSequence)this.originName, "webdav", 0, false, 6, null) < 0) {
            String[] stringArray = new String[]{"index.pdf"};
            return FileUtils.INSTANCE.getFile(new File(Intrinsics.stringPlus(this.rootDir, this.originName)), stringArray);
        }
        return new File(Intrinsics.stringPlus(this.rootDir, this.originName));
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

    @NotNull
    public final String getBookDir() {
        String[] stringArray = new String[]{"storage", "data", this._userNameSpace, this.getName() + '_' + this.getAuthor()};
        return FileUtils.INSTANCE.getPath(new File(this.rootDir), stringArray);
    }

    public final boolean getSplitLongChapter() {
        return false;
    }

    @NotNull
    public final SearchBook toSearchBook() {
        Object object = this.getName();
        String string = this.getAuthor();
        String string2 = this.getKind();
        String string3 = this.getBookUrl();
        String string4 = this.origin;
        String string5 = this.originName;
        int n = this.type;
        String string6 = this.getWordCount();
        String string7 = this.latestChapterTitle;
        String string8 = this.coverUrl;
        String string9 = this.intro;
        String string10 = this.tocUrl;
        String string11 = this.variable;
        object = new SearchBook(string3, string4, string5, n, (String)object, string, string2, string8, string9, string6, string7, string10, 0L, string11, 0, 20480, null);
        boolean bl = false;
        boolean bl2 = false;
        Object $this$toSearchBook_u24lambda_u2d0 = object;
        boolean bl3 = false;
        ((SearchBook)$this$toSearchBook_u24lambda_u2d0).setInfoHtml(this.getInfoHtml());
        ((SearchBook)$this$toSearchBook_u24lambda_u2d0).setTocHtml(this.getTocHtml());
        ((SearchBook)$this$toSearchBook_u24lambda_u2d0).setUserNameSpace(this.getUserNameSpace());
        return object;
    }

    @NotNull
    public final String getEpubRootDir() {
        String defaultPath = "OEBPS";
        File containerRes = new File(this.getBookUrl() + File.separator + "index" + File.separator + "META-INF" + File.separator + "container.xml");
        if (containerRes.exists()) {
            try {
                Document document = Jsoup.parse(FilesKt.readText$default(containerRes, null, 1, null));
                Element rootFileElement = (Element)((Element)document.getElementsByTag("rootfiles").get(0)).getElementsByTag("rootfile").get(0);
                String result2 = rootFileElement.attr("full-path");
                System.out.println(Intrinsics.stringPlus("result: ", result2));
                if (result2 != null) {
                    Object object = result2;
                    boolean bl = false;
                    if (object.length() > 0) {
                        String string;
                        object = new File(result2).getParentFile();
                        if (object == null) {
                            string = "";
                        } else {
                            Object object2 = object;
                            boolean bl2 = false;
                            boolean bl3 = false;
                            Object it = object2;
                            boolean bl4 = false;
                            String string2 = ((File)it).toString();
                            string = string2 == null ? "" : string2;
                        }
                        return string;
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultPath;
    }

    public final void updateFromLocal(boolean onlyCover) {
        try {
            if (this.isEpub()) {
                EpubFile.Companion.upBookInfo(this, onlyCover);
            } else if (this.isUmd()) {
                UmdFile.Companion.upBookInfo(this, onlyCover);
            } else if (this.isCbz()) {
                CbzFile.Companion.upBookInfo(this, onlyCover);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static /* synthetic */ void updateFromLocal$default(Book book, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            bl = false;
        }
        book.updateFromLocal(bl);
    }

    @NotNull
    public final String workRoot() {
        return this.rootDir;
    }

    @Override
    @NotNull
    public List<String> getKindList() {
        return BaseBook.DefaultImpls.getKindList(this);
    }

    @Override
    @Nullable
    public String getVariable(@NotNull String key) {
        return BaseBook.DefaultImpls.getVariable(this, key);
    }

    @NotNull
    public final String component1() {
        return this.getBookUrl();
    }

    @NotNull
    public final String component2() {
        return this.tocUrl;
    }

    @NotNull
    public final String component3() {
        return this.origin;
    }

    @NotNull
    public final String component4() {
        return this.originName;
    }

    @NotNull
    public final String component5() {
        return this.getName();
    }

    @NotNull
    public final String component6() {
        return this.getAuthor();
    }

    @Nullable
    public final String component7() {
        return this.getKind();
    }

    @Nullable
    public final String component8() {
        return this.customTag;
    }

    @Nullable
    public final String component9() {
        return this.coverUrl;
    }

    @Nullable
    public final String component10() {
        return this.customCoverUrl;
    }

    @Nullable
    public final String component11() {
        return this.intro;
    }

    @Nullable
    public final String component12() {
        return this.customIntro;
    }

    @Nullable
    public final String component13() {
        return this.charset;
    }

    public final int component14() {
        return this.type;
    }

    public final long component15() {
        return this.group;
    }

    @Nullable
    public final String component16() {
        return this.latestChapterTitle;
    }

    public final long component17() {
        return this.latestChapterTime;
    }

    public final long component18() {
        return this.lastCheckTime;
    }

    public final int component19() {
        return this.lastCheckCount;
    }

    public final int component20() {
        return this.totalChapterNum;
    }

    @Nullable
    public final String component21() {
        return this.durChapterTitle;
    }

    public final int component22() {
        return this.durChapterIndex;
    }

    public final int component23() {
        return this.durChapterPos;
    }

    public final long component24() {
        return this.durChapterTime;
    }

    @Nullable
    public final String component25() {
        return this.getWordCount();
    }

    public final boolean component26() {
        return this.canUpdate;
    }

    public final int component27() {
        return this.order;
    }

    public final int component28() {
        return this.originOrder;
    }

    public final boolean component29() {
        return this.useReplaceRule;
    }

    @Nullable
    public final String component30() {
        return this.variable;
    }

    @Nullable
    public final ReadConfig component31() {
        return this.readConfig;
    }

    public final boolean component32() {
        return this.isInShelf;
    }

    @Nullable
    public final String component33() {
        return this.lastCheckError;
    }

    @NotNull
    public final Book copy(@NotNull String bookUrl, @NotNull String tocUrl, @NotNull String origin, @NotNull String originName, @NotNull String name, @NotNull String author, @Nullable String kind, @Nullable String customTag, @Nullable String coverUrl, @Nullable String customCoverUrl, @Nullable String intro, @Nullable String customIntro, @Nullable String charset, int type, long group, @Nullable String latestChapterTitle, long latestChapterTime, long lastCheckTime, int lastCheckCount, int totalChapterNum, @Nullable String durChapterTitle, int durChapterIndex, int durChapterPos, long durChapterTime, @Nullable String wordCount, boolean canUpdate, int order, int originOrder, boolean useReplaceRule, @Nullable String variable, @Nullable ReadConfig readConfig, boolean isInShelf, @Nullable String lastCheckError) {
        Intrinsics.checkNotNullParameter(bookUrl, "bookUrl");
        Intrinsics.checkNotNullParameter(tocUrl, "tocUrl");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(originName, "originName");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(author, "author");
        return new Book(bookUrl, tocUrl, origin, originName, name, author, kind, customTag, coverUrl, customCoverUrl, intro, customIntro, charset, type, group, latestChapterTitle, latestChapterTime, lastCheckTime, lastCheckCount, totalChapterNum, durChapterTitle, durChapterIndex, durChapterPos, durChapterTime, wordCount, canUpdate, order, originOrder, useReplaceRule, variable, readConfig, isInShelf, lastCheckError);
    }

    public static /* synthetic */ Book copy$default(Book book, String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, String string11, String string12, String string13, int n, long l, String string14, long l2, long l3, int n2, int n3, String string15, int n4, int n5, long l4, String string16, boolean bl, int n6, int n7, boolean bl2, String string17, ReadConfig readConfig, boolean bl3, String string18, int n8, int n9, Object object) {
        if ((n8 & 1) != 0) {
            string = book.getBookUrl();
        }
        if ((n8 & 2) != 0) {
            string2 = book.tocUrl;
        }
        if ((n8 & 4) != 0) {
            string3 = book.origin;
        }
        if ((n8 & 8) != 0) {
            string4 = book.originName;
        }
        if ((n8 & 0x10) != 0) {
            string5 = book.getName();
        }
        if ((n8 & 0x20) != 0) {
            string6 = book.getAuthor();
        }
        if ((n8 & 0x40) != 0) {
            string7 = book.getKind();
        }
        if ((n8 & 0x80) != 0) {
            string8 = book.customTag;
        }
        if ((n8 & 0x100) != 0) {
            string9 = book.coverUrl;
        }
        if ((n8 & 0x200) != 0) {
            string10 = book.customCoverUrl;
        }
        if ((n8 & 0x400) != 0) {
            string11 = book.intro;
        }
        if ((n8 & 0x800) != 0) {
            string12 = book.customIntro;
        }
        if ((n8 & 0x1000) != 0) {
            string13 = book.charset;
        }
        if ((n8 & 0x2000) != 0) {
            n = book.type;
        }
        if ((n8 & 0x4000) != 0) {
            l = book.group;
        }
        if ((n8 & 0x8000) != 0) {
            string14 = book.latestChapterTitle;
        }
        if ((n8 & 0x10000) != 0) {
            l2 = book.latestChapterTime;
        }
        if ((n8 & 0x20000) != 0) {
            l3 = book.lastCheckTime;
        }
        if ((n8 & 0x40000) != 0) {
            n2 = book.lastCheckCount;
        }
        if ((n8 & 0x80000) != 0) {
            n3 = book.totalChapterNum;
        }
        if ((n8 & 0x100000) != 0) {
            string15 = book.durChapterTitle;
        }
        if ((n8 & 0x200000) != 0) {
            n4 = book.durChapterIndex;
        }
        if ((n8 & 0x400000) != 0) {
            n5 = book.durChapterPos;
        }
        if ((n8 & 0x800000) != 0) {
            l4 = book.durChapterTime;
        }
        if ((n8 & 0x1000000) != 0) {
            string16 = book.getWordCount();
        }
        if ((n8 & 0x2000000) != 0) {
            bl = book.canUpdate;
        }
        if ((n8 & 0x4000000) != 0) {
            n6 = book.order;
        }
        if ((n8 & 0x8000000) != 0) {
            n7 = book.originOrder;
        }
        if ((n8 & 0x10000000) != 0) {
            bl2 = book.useReplaceRule;
        }
        if ((n8 & 0x20000000) != 0) {
            string17 = book.variable;
        }
        if ((n8 & 0x40000000) != 0) {
            readConfig = book.readConfig;
        }
        if ((n8 & Integer.MIN_VALUE) != 0) {
            bl3 = book.isInShelf;
        }
        if ((n9 & 1) != 0) {
            string18 = book.lastCheckError;
        }
        return book.copy(string, string2, string3, string4, string5, string6, string7, string8, string9, string10, string11, string12, string13, n, l, string14, l2, l3, n2, n3, string15, n4, n5, l4, string16, bl, n6, n7, bl2, string17, readConfig, bl3, string18);
    }

    @NotNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Book(bookUrl=").append(this.getBookUrl()).append(", tocUrl=").append(this.tocUrl).append(", origin=").append(this.origin).append(", originName=").append(this.originName).append(", name=").append(this.getName()).append(", author=").append(this.getAuthor()).append(", kind=").append((Object)this.getKind()).append(", customTag=").append((Object)this.customTag).append(", coverUrl=").append((Object)this.coverUrl).append(", customCoverUrl=").append((Object)this.customCoverUrl).append(", intro=").append((Object)this.intro).append(", customIntro=");
        stringBuilder.append((Object)this.customIntro).append(", charset=").append((Object)this.charset).append(", type=").append(this.type).append(", group=").append(this.group).append(", latestChapterTitle=").append((Object)this.latestChapterTitle).append(", latestChapterTime=").append(this.latestChapterTime).append(", lastCheckTime=").append(this.lastCheckTime).append(", lastCheckCount=").append(this.lastCheckCount).append(", totalChapterNum=").append(this.totalChapterNum).append(", durChapterTitle=").append((Object)this.durChapterTitle).append(", durChapterIndex=").append(this.durChapterIndex).append(", durChapterPos=").append(this.durChapterPos);
        stringBuilder.append(", durChapterTime=").append(this.durChapterTime).append(", wordCount=").append((Object)this.getWordCount()).append(", canUpdate=").append(this.canUpdate).append(", order=").append(this.order).append(", originOrder=").append(this.originOrder).append(", useReplaceRule=").append(this.useReplaceRule).append(", variable=").append((Object)this.variable).append(", readConfig=").append(this.readConfig).append(", isInShelf=").append(this.isInShelf).append(", lastCheckError=").append((Object)this.lastCheckError).append(')');
        return stringBuilder.toString();
    }

    public Book() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, 0, 0L, null, 0L, 0L, 0, 0, null, 0, 0, 0L, null, false, 0, 0, false, null, null, false, null, -1, 1, null);
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J$\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u0006\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J4\u0010\u0011\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\r0\u0012j\b\u0012\u0004\u0012\u00020\r`\u00130\f2\u0006\u0010\u0014\u001a\u00020\u0006\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0010J$\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u0017\u001a\u00020\u0018\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\u0019\u0010\u001aJ \u0010\u001b\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u00062\b\b\u0002\u0010\u001e\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001f"}, d2={"Lio/legado/app/data/entities/Book$Companion;", "", "()V", "hTag", "", "imgStyleDefault", "", "imgStyleFull", "imgStyleText", "imgTag", "rubyTag", "fromJson", "Lkotlin/Result;", "Lio/legado/app/data/entities/Book;", "json", "fromJson-IoAF18A", "(Ljava/lang/String;)Ljava/lang/Object;", "fromJsonArray", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "jsonArray", "fromJsonArray-IoAF18A", "fromJsonDoc", "doc", "Lcom/jayway/jsonpath/DocumentContext;", "fromJsonDoc-IoAF18A", "(Lcom/jayway/jsonpath/DocumentContext;)Ljava/lang/Object;", "initLocalBook", "bookUrl", "localPath", "rootDir", "reader-pro"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Book initLocalBook(@NotNull String bookUrl, @NotNull String localPath, @NotNull String rootDir) {
            Intrinsics.checkNotNullParameter(bookUrl, "bookUrl");
            Intrinsics.checkNotNullParameter(localPath, "localPath");
            Intrinsics.checkNotNullParameter(rootDir, "rootDir");
            String fileName = new File(localPath).getName();
            Intrinsics.checkNotNullExpressionValue(fileName, "fileName");
            Pair<String, String> nameAuthor = LocalBook.INSTANCE.analyzeNameAuthor(fileName);
            Book book = new Book(bookUrl, "", "loc_book", localPath, nameAuthor.getFirst(), nameAuthor.getSecond(), null, null, null, null, null, null, null, 0, 0L, null, 0L, 0L, 0, 0, null, 0, 0, 0L, null, false, 0, 0, false, null, null, false, null, -64, 1, null);
            boolean bl = false;
            boolean bl2 = false;
            Book it = book;
            boolean bl3 = false;
            it.setCanUpdate(false);
            Book book2 = book;
            book2.setRootDir(rootDir);
            Book.updateFromLocal$default(book2, false, 1, null);
            return book2;
        }

        public static /* synthetic */ Book initLocalBook$default(Companion companion, String string, String string2, String string3, int n, Object object) {
            if ((n & 4) != 0) {
                string3 = "";
            }
            return companion.initLocalBook(string, string2, string3);
        }

        @NotNull
        public final Object fromJsonDoc-IoAF18A(@NotNull DocumentContext doc) {
            Object object;
            Intrinsics.checkNotNullParameter(doc, "doc");
            boolean bl = false;
            try {
                ReadConfig readConfig;
                object = Result.Companion;
                boolean bl2 = false;
                Object readConfig2 = doc.read("$.readConfig", new Predicate[0]);
                String string = JsonExtensionsKt.readString(doc, "$.bookUrl");
                String string2 = string;
                Intrinsics.checkNotNull(string);
                String string3 = JsonExtensionsKt.readString(doc, "$.tocUrl");
                String string4 = string3;
                Intrinsics.checkNotNull(string3);
                Object object2 = JsonExtensionsKt.readString(doc, "$.origin");
                Object object3 = object2 == null ? "loc_book" : object2;
                object2 = JsonExtensionsKt.readString(doc, "$.originName");
                Object object4 = object2 == null ? "" : object2;
                String string5 = JsonExtensionsKt.readString(doc, "$.name");
                String string6 = string5;
                Intrinsics.checkNotNull(string5);
                object2 = JsonExtensionsKt.readString(doc, "$.author");
                Object object5 = object2 == null ? "" : object2;
                String string7 = JsonExtensionsKt.readString(doc, "$.kind");
                String string8 = JsonExtensionsKt.readString(doc, "$.customTag");
                String string9 = JsonExtensionsKt.readString(doc, "$.coverUrl");
                String string10 = JsonExtensionsKt.readString(doc, "$.customCoverUrl");
                String string11 = JsonExtensionsKt.readString(doc, "$.intro");
                String string12 = JsonExtensionsKt.readString(doc, "$.customIntro");
                String string13 = JsonExtensionsKt.readString(doc, "$.charset");
                object2 = JsonExtensionsKt.readInt(doc, "$.type");
                int n = object2 == null ? 0 : (Integer)object2;
                object2 = JsonExtensionsKt.readLong(doc, "$.group");
                long l = object2 == null ? 0L : (Long)object2;
                String string14 = JsonExtensionsKt.readString(doc, "$.latestChapterTitle");
                object2 = JsonExtensionsKt.readLong(doc, "$.latestChapterTime");
                long l2 = object2 == null ? System.currentTimeMillis() : (Long)object2;
                object2 = JsonExtensionsKt.readLong(doc, "$.lastCheckTime");
                long l3 = object2 == null ? System.currentTimeMillis() : (Long)object2;
                object2 = JsonExtensionsKt.readInt(doc, "$.lastCheckCount");
                int n2 = object2 == null ? 0 : (Integer)object2;
                object2 = JsonExtensionsKt.readInt(doc, "$.totalChapterNum");
                int n3 = object2 == null ? 0 : (Integer)object2;
                String string15 = JsonExtensionsKt.readString(doc, "$.durChapterTitle");
                object2 = JsonExtensionsKt.readInt(doc, "$.durChapterIndex");
                int n4 = object2 == null ? 0 : (Integer)object2;
                object2 = JsonExtensionsKt.readInt(doc, "$.durChapterPos");
                int n5 = object2 == null ? 0 : (Integer)object2;
                object2 = JsonExtensionsKt.readLong(doc, "$.durChapterTime");
                long l4 = object2 == null ? System.currentTimeMillis() : (Long)object2;
                String string16 = JsonExtensionsKt.readString(doc, "$.wordCount");
                object2 = JsonExtensionsKt.readBool(doc, "$.canUpdate");
                boolean bl3 = object2 == null ? true : (Boolean)object2;
                object2 = JsonExtensionsKt.readInt(doc, "$.order");
                int n6 = object2 == null ? 0 : (Integer)object2;
                object2 = JsonExtensionsKt.readInt(doc, "$.originOrder");
                int n7 = object2 == null ? 0 : (Integer)object2;
                object2 = JsonExtensionsKt.readBool(doc, "$.useReplaceRule");
                boolean bl4 = object2 == null ? true : (Boolean)object2;
                String string17 = JsonExtensionsKt.readString(doc, "$.variable");
                if (readConfig2 != null) {
                    Object object6;
                    String string18 = string17;
                    boolean bl5 = bl4;
                    int n8 = n7;
                    int n9 = n6;
                    boolean bl6 = bl3;
                    String string19 = string16;
                    long l5 = l4;
                    int n10 = n5;
                    int n11 = n4;
                    String string20 = string15;
                    int n12 = n3;
                    int n13 = n2;
                    long l6 = l3;
                    long l7 = l2;
                    String string21 = string14;
                    long l8 = l;
                    int n14 = n;
                    String string22 = string13;
                    String string23 = string12;
                    String string24 = string11;
                    String string25 = string10;
                    String string26 = string9;
                    String string27 = string8;
                    String string28 = string7;
                    String string29 = object5;
                    String string30 = string6;
                    String string31 = object4;
                    String string32 = object3;
                    String string33 = string4;
                    String string34 = string2;
                    boolean bl7 = false;
                    try {
                        object6 = Result.Companion;
                        boolean bl8 = false;
                        Comparable<Boolean> comparable = JsonExtensionsKt.readBool(doc, "$.readConfig.reverseToc");
                        boolean bl9 = comparable == null ? false : (Boolean)comparable;
                        comparable = JsonExtensionsKt.readInt(doc, "$.readConfig.pageAnim");
                        int n15 = comparable == null ? -1 : (Integer)comparable;
                        comparable = JsonExtensionsKt.readBool(doc, "$.readConfig.reSegment");
                        boolean bl10 = comparable == null ? false : (Boolean)comparable;
                        comparable = JsonExtensionsKt.readBool(doc, "$.readConfig.useReplaceRule");
                        boolean bl11 = comparable == null ? false : (Boolean)comparable;
                        comparable = JsonExtensionsKt.readLong(doc, "$.readConfig.delTag");
                        ReadConfig readConfig3 = new ReadConfig(bl9, n15, bl10, JsonExtensionsKt.readString(doc, "$.readConfig.imageStyle"), bl11, comparable == null ? 0L : (Long)comparable, 0.0f, 64, null);
                        boolean bl12 = false;
                        object6 = Result.constructor-impl(readConfig3);
                    }
                    catch (Throwable throwable) {
                        Result.Companion companion = Result.Companion;
                        boolean bl13 = false;
                        object6 = Result.constructor-impl(ResultKt.createFailure(throwable));
                    }
                    Object object7 = object6;
                    string2 = string34;
                    string4 = string33;
                    object3 = string32;
                    object4 = string31;
                    string6 = string30;
                    object5 = string29;
                    string7 = string28;
                    string8 = string27;
                    string9 = string26;
                    string10 = string25;
                    string11 = string24;
                    string12 = string23;
                    string13 = string22;
                    n = n14;
                    l = l8;
                    string14 = string21;
                    l2 = l7;
                    l3 = l6;
                    n2 = n13;
                    n3 = n12;
                    string15 = string20;
                    n4 = n11;
                    n5 = n10;
                    l4 = l5;
                    string16 = string19;
                    bl3 = bl6;
                    n6 = n9;
                    n7 = n8;
                    bl4 = bl5;
                    string17 = string18;
                    object2 = object7;
                    boolean bl14 = false;
                    readConfig = (ReadConfig)(Result.isFailure-impl(object2) ? null : object2);
                } else {
                    readConfig = null;
                }
                object2 = JsonExtensionsKt.readBool(doc, "$.isInShelf");
                DefaultConstructorMarker defaultConstructorMarker = null;
                int n16 = 1;
                int n17 = 0;
                String string35 = null;
                boolean bl15 = object2 == null ? false : (Boolean)object2;
                ReadConfig readConfig4 = readConfig;
                String string36 = string17;
                boolean bl16 = bl4;
                int n18 = n7;
                int n19 = n6;
                boolean bl17 = bl3;
                String string37 = string16;
                long l9 = l4;
                int n20 = n5;
                int n21 = n4;
                String string38 = string15;
                int n22 = n3;
                int n23 = n2;
                long l10 = l3;
                long l11 = l2;
                String string39 = string14;
                long l12 = l;
                int n24 = n;
                String string40 = string13;
                String string41 = string12;
                String string42 = string11;
                String string43 = string10;
                String string44 = string9;
                String string45 = string8;
                String string46 = string7;
                String string47 = object5;
                String string48 = string6;
                String string49 = object4;
                String string50 = object3;
                String string51 = string4;
                String string52 = string2;
                Book book = new Book(string52, string51, string50, string49, string48, string47, string46, string45, string44, string43, string42, string41, string40, n24, l12, string39, l11, l10, n23, n22, string38, n21, n20, l9, string37, bl17, n19, n18, bl16, string36, readConfig4, bl15, string35, n17, n16, defaultConstructorMarker);
                boolean bl18 = false;
                object = Result.constructor-impl(book);
            }
            catch (Throwable throwable) {
                Result.Companion companion = Result.Companion;
                boolean bl19 = false;
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
                ArrayList<Book> sources = new ArrayList<Book>();
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
                    Book source = (Book)object2;
                    boolean bl7 = false;
                    sources.add(source);
                }
                ArrayList<Book> arrayList = sources;
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

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b&\b\u0086\b\u0018\u00002\u00020\u0001BM\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\t\u0010'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0005H\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\t\u0010,\u001a\u00020\u000bH\u00c6\u0003J\t\u0010-\u001a\u00020\rH\u00c6\u0003JQ\u0010.\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\rH\u00c6\u0001J\u0013\u0010/\u001a\u00020\u00032\b\u00100\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00101\u001a\u00020\u0005H\u00d6\u0001J\t\u00102\u001a\u00020\bH\u00d6\u0001R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010 \"\u0004\b$\u0010\"R\u001a\u0010\t\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010 \"\u0004\b&\u0010\"\u00a8\u00063"}, d2={"Lio/legado/app/data/entities/Book$ReadConfig;", "", "reverseToc", "", "pageAnim", "", "reSegment", "imageStyle", "", "useReplaceRule", "delTag", "", "pdfImageWidth", "", "(ZIZLjava/lang/String;ZJF)V", "getDelTag", "()J", "setDelTag", "(J)V", "getImageStyle", "()Ljava/lang/String;", "setImageStyle", "(Ljava/lang/String;)V", "getPageAnim", "()I", "setPageAnim", "(I)V", "getPdfImageWidth", "()F", "setPdfImageWidth", "(F)V", "getReSegment", "()Z", "setReSegment", "(Z)V", "getReverseToc", "setReverseToc", "getUseReplaceRule", "setUseReplaceRule", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "hashCode", "toString", "reader-pro"})
    public static final class ReadConfig {
        private boolean reverseToc;
        private int pageAnim;
        private boolean reSegment;
        @Nullable
        private String imageStyle;
        private boolean useReplaceRule;
        private long delTag;
        private float pdfImageWidth;

        public ReadConfig(boolean reverseToc, int pageAnim, boolean reSegment, @Nullable String imageStyle, boolean useReplaceRule, long delTag, float pdfImageWidth) {
            this.reverseToc = reverseToc;
            this.pageAnim = pageAnim;
            this.reSegment = reSegment;
            this.imageStyle = imageStyle;
            this.useReplaceRule = useReplaceRule;
            this.delTag = delTag;
            this.pdfImageWidth = pdfImageWidth;
        }

        public /* synthetic */ ReadConfig(boolean bl, int n, boolean bl2, String string, boolean bl3, long l, float f, int n2, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n2 & 1) != 0) {
                bl = false;
            }
            if ((n2 & 2) != 0) {
                n = -1;
            }
            if ((n2 & 4) != 0) {
                bl2 = false;
            }
            if ((n2 & 8) != 0) {
                string = null;
            }
            if ((n2 & 0x10) != 0) {
                bl3 = false;
            }
            if ((n2 & 0x20) != 0) {
                l = 0L;
            }
            if ((n2 & 0x40) != 0) {
                f = 800.0f;
            }
            this(bl, n, bl2, string, bl3, l, f);
        }

        public final boolean getReverseToc() {
            return this.reverseToc;
        }

        public final void setReverseToc(boolean bl) {
            this.reverseToc = bl;
        }

        public final int getPageAnim() {
            return this.pageAnim;
        }

        public final void setPageAnim(int n) {
            this.pageAnim = n;
        }

        public final boolean getReSegment() {
            return this.reSegment;
        }

        public final void setReSegment(boolean bl) {
            this.reSegment = bl;
        }

        @Nullable
        public final String getImageStyle() {
            return this.imageStyle;
        }

        public final void setImageStyle(@Nullable String string) {
            this.imageStyle = string;
        }

        public final boolean getUseReplaceRule() {
            return this.useReplaceRule;
        }

        public final void setUseReplaceRule(boolean bl) {
            this.useReplaceRule = bl;
        }

        public final long getDelTag() {
            return this.delTag;
        }

        public final void setDelTag(long l) {
            this.delTag = l;
        }

        public final float getPdfImageWidth() {
            return this.pdfImageWidth;
        }

        public final void setPdfImageWidth(float f) {
            this.pdfImageWidth = f;
        }

        public final boolean component1() {
            return this.reverseToc;
        }

        public final int component2() {
            return this.pageAnim;
        }

        public final boolean component3() {
            return this.reSegment;
        }

        @Nullable
        public final String component4() {
            return this.imageStyle;
        }

        public final boolean component5() {
            return this.useReplaceRule;
        }

        public final long component6() {
            return this.delTag;
        }

        public final float component7() {
            return this.pdfImageWidth;
        }

        @NotNull
        public final ReadConfig copy(boolean reverseToc, int pageAnim, boolean reSegment, @Nullable String imageStyle, boolean useReplaceRule, long delTag, float pdfImageWidth) {
            return new ReadConfig(reverseToc, pageAnim, reSegment, imageStyle, useReplaceRule, delTag, pdfImageWidth);
        }

        public static /* synthetic */ ReadConfig copy$default(ReadConfig readConfig, boolean bl, int n, boolean bl2, String string, boolean bl3, long l, float f, int n2, Object object) {
            if ((n2 & 1) != 0) {
                bl = readConfig.reverseToc;
            }
            if ((n2 & 2) != 0) {
                n = readConfig.pageAnim;
            }
            if ((n2 & 4) != 0) {
                bl2 = readConfig.reSegment;
            }
            if ((n2 & 8) != 0) {
                string = readConfig.imageStyle;
            }
            if ((n2 & 0x10) != 0) {
                bl3 = readConfig.useReplaceRule;
            }
            if ((n2 & 0x20) != 0) {
                l = readConfig.delTag;
            }
            if ((n2 & 0x40) != 0) {
                f = readConfig.pdfImageWidth;
            }
            return readConfig.copy(bl, n, bl2, string, bl3, l, f);
        }

        @NotNull
        public String toString() {
            return "ReadConfig(reverseToc=" + this.reverseToc + ", pageAnim=" + this.pageAnim + ", reSegment=" + this.reSegment + ", imageStyle=" + this.imageStyle + ", useReplaceRule=" + this.useReplaceRule + ", delTag=" + this.delTag + ", pdfImageWidth=" + this.pdfImageWidth + ')';
        }

        public int hashCode() {
            int n = this.reverseToc ? 1 : 0;
            if (n != 0) {
                n = 1;
            }
            int result2 = n;
            result2 = result2 * 31 + Integer.hashCode(this.pageAnim);
            int n2 = this.reSegment ? 1 : 0;
            if (n2 != 0) {
                n2 = 1;
            }
            result2 = result2 * 31 + n2;
            result2 = result2 * 31 + (this.imageStyle == null ? 0 : this.imageStyle.hashCode());
            int n3 = this.useReplaceRule ? 1 : 0;
            if (n3 != 0) {
                n3 = 1;
            }
            result2 = result2 * 31 + n3;
            result2 = result2 * 31 + Long.hashCode(this.delTag);
            result2 = result2 * 31 + Float.hashCode(this.pdfImageWidth);
            return result2;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ReadConfig)) {
                return false;
            }
            ReadConfig readConfig = (ReadConfig)other;
            if (this.reverseToc != readConfig.reverseToc) {
                return false;
            }
            if (this.pageAnim != readConfig.pageAnim) {
                return false;
            }
            if (this.reSegment != readConfig.reSegment) {
                return false;
            }
            if (!Intrinsics.areEqual(this.imageStyle, readConfig.imageStyle)) {
                return false;
            }
            if (this.useReplaceRule != readConfig.useReplaceRule) {
                return false;
            }
            if (this.delTag != readConfig.delTag) {
                return false;
            }
            return Intrinsics.areEqual((Object)Float.valueOf(this.pdfImageWidth), (Object)Float.valueOf(readConfig.pdfImageWidth));
        }

        public ReadConfig() {
            this(false, 0, false, null, false, 0L, 0.0f, 127, null);
        }
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006J(\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00f8\u0001\u0002\u00a2\u0006\u0004\b\n\u0010\u000b\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\f"}, d2={"Lio/legado/app/data/entities/Book$Converters;", "", "()V", "readConfigToString", "", "config", "Lio/legado/app/data/entities/Book$ReadConfig;", "stringToReadConfig", "Lkotlin/Result;", "json", "stringToReadConfig-IoAF18A", "(Ljava/lang/String;)Ljava/lang/Object;", "reader-pro"})
    public static final class Converters {
        @NotNull
        public final String readConfigToString(@Nullable ReadConfig config2) {
            String string = GsonExtensionsKt.getGSON().toJson(config2);
            Intrinsics.checkNotNullExpressionValue(string, "GSON.toJson(config)");
            return string;
        }

        @NotNull
        public final Object stringToReadConfig-IoAF18A(@Nullable String json) {
            Object object;
            Gson $this$fromJsonObject$iv = GsonExtensionsKt.getGSON();
            boolean $i$f$fromJsonObject = false;
            boolean bl = false;
            try {
                object = Result.Companion;
                boolean bl2 = false;
                boolean $i$f$genericType = false;
                Type type = new TypeToken<ReadConfig>(){}.getType();
                Intrinsics.checkNotNullExpressionValue(type, "object : TypeToken<T>() {}.type");
                Object t = $this$fromJsonObject$iv.fromJson(json, type);
                if (!(t instanceof ReadConfig)) {
                    t = null;
                }
                ReadConfig readConfig = (ReadConfig)t;
                boolean bl3 = false;
                object = Result.constructor-impl(readConfig);
            }
            catch (Throwable throwable) {
                Result.Companion companion = Result.Companion;
                boolean bl4 = false;
                object = Result.constructor-impl(ResultKt.createFailure(throwable));
            }
            return object;
        }
    }
}

