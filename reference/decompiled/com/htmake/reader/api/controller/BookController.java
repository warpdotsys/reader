/*
 * Decompiled with CFR 0.152.
 */
package com.htmake.reader.api.controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.reflect.TypeToken;
import com.htmake.reader.api.ReturnData;
import com.htmake.reader.api.controller.BaseController;
import com.htmake.reader.api.controller.BookController;
import com.htmake.reader.api.controller.BookControllerKt;
import com.htmake.reader.api.controller.BookSourceController;
import com.htmake.reader.entity.User;
import com.htmake.reader.lib.tts.constant.TtsStyleEnum;
import com.htmake.reader.lib.tts.constant.VoiceEnum;
import com.htmake.reader.lib.tts.model.SSML;
import com.htmake.reader.lib.tts.service.TTSService;
import com.htmake.reader.utils.ExtKt;
import com.htmake.reader.utils.MongoManager;
import com.htmake.reader.utils.SpringContextUtils;
import com.htmake.reader.utils.UserMutex;
import com.htmake.reader.utils.VertExtKt;
import com.script.ScriptException;
import io.legado.app.constant.AppPattern;
import io.legado.app.data.entities.BaseSource;
import io.legado.app.data.entities.Book;
import io.legado.app.data.entities.BookChapter;
import io.legado.app.data.entities.BookSource;
import io.legado.app.data.entities.HttpTTS;
import io.legado.app.data.entities.SearchBook;
import io.legado.app.data.entities.SearchResult;
import io.legado.app.data.entities.TxtTocRule;
import io.legado.app.exception.NoStackTraceException;
import io.legado.app.exception.TocEmptyException;
import io.legado.app.help.BookHelp;
import io.legado.app.help.DefaultData;
import io.legado.app.model.Debug;
import io.legado.app.model.Debugger;
import io.legado.app.model.analyzeRule.AnalyzeRule;
import io.legado.app.model.analyzeRule.AnalyzeUrl;
import io.legado.app.model.analyzeRule.RuleDataInterface;
import io.legado.app.model.localBook.LocalBook;
import io.legado.app.model.webBook.WebBook;
import io.legado.app.utils.ACache;
import io.legado.app.utils.FileUtils;
import io.legado.app.utils.GsonExtensionsKt;
import io.legado.app.utils.HtmlFormatter;
import io.legado.app.utils.MD5Utils;
import io.legado.app.utils.NetworkUtils;
import io.legado.app.utils.ParameterizedTypeImpl;
import io.legado.app.utils.ZipUtils;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.CaseInsensitiveHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.kotlin.coroutines.VertxCoroutineKt;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.invoke.LambdaMetafactory;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import javax.imageio.ImageIO;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineExceptionHandler;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.slf4j.MDCContext;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;
import me.ag2s.epublib.domain.Author;
import me.ag2s.epublib.domain.Date;
import me.ag2s.epublib.domain.EpubBook;
import me.ag2s.epublib.domain.FileResourceProvider;
import me.ag2s.epublib.domain.LazyResource;
import me.ag2s.epublib.domain.LazyResourceProvider;
import me.ag2s.epublib.domain.Metadata;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.domain.Resources;
import me.ag2s.epublib.epub.EpubWriter;
import me.ag2s.epublib.util.ResourceUtil;
import okhttp3.Response;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mozilla.javascript.WrappedException;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@kotlin.Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u008a\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010#\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J,\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00072\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u001c0\u001b2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\u0019\u0010\u001e\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u0019\u0010\u001f\u001a\u00020\u00182\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J!\u0010 \u001a\u00020\u00182\u0006\u0010!\u001a\u00020\"2\u0006\u0010\u001d\u001a\u00020\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010#J\u0019\u0010 \u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u0019\u0010$\u001a\u00020\u00182\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J \u0010%\u001a\u00020\u00182\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020\u000f2\b\b\u0002\u0010)\u001a\u00020*J\u0018\u0010+\u001a\u00020*2\u0006\u0010&\u001a\u00020'2\b\b\u0002\u0010)\u001a\u00020*J/\u0010,\u001a\u0004\u0018\u00010-2\u0006\u0010\u001d\u001a\u00020\u00072\u0006\u0010.\u001a\u00020\u00072\n\b\u0002\u0010/\u001a\u0004\u0018\u00010\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00100J\u0019\u00101\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u0019\u00102\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u0019\u00103\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J7\u00104\u001a\u0004\u0018\u00010'2\u0006\u0010&\u001a\u00020'2\u0006\u0010\u001d\u001a\u00020\u00072\u0012\u00105\u001a\u000e\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020'06H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00107J\u0019\u00108\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u0019\u00109\u001a\u00020\u00182\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J3\u0010:\u001a\u00020-2\u0006\u0010;\u001a\u00020-2\u0006\u0010&\u001a\u00020'2\b\u0010<\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u001d\u001a\u00020\u0007H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010=J1\u0010>\u001a\u00020-2\u0006\u0010;\u001a\u00020-2\u0006\u0010?\u001a\u00020'2\u0006\u0010<\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010=J\u0018\u0010@\u001a\u00020*2\u0006\u0010&\u001a\u00020'2\b\b\u0002\u0010)\u001a\u00020*J\u0018\u0010A\u001a\u00020*2\u0006\u0010&\u001a\u00020'2\b\b\u0002\u0010)\u001a\u00020*J(\u0010B\u001a\u00020\u00072\u0006\u0010C\u001a\u00020D2\u0006\u0010&\u001a\u00020'2\u0006\u0010E\u001a\u00020\u00072\u0006\u0010F\u001a\u00020GH\u0002J\u0099\u0001\u0010H\u001a\u00020\u00182\u0006\u0010&\u001a\u00020'2\u0006\u0010I\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u00072n\u0010J\u001aj\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(N\u0012K\u0012I\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00070P\u0018\u00010Oj\u001c\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00070P\u0018\u0001`Q\u00a2\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(R\u0012\u0004\u0012\u00020\u00180KH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010SJ\u0019\u0010T\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u0010\u0010U\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\u0019\u0010V\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u0019\u0010W\u001a\u00020\u00182\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u0019\u0010X\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J)\u0010Y\u001a\b\u0012\u0004\u0012\u00020'0Z2\b\b\u0002\u0010[\u001a\u00020*2\u0006\u0010\u001d\u001a\u00020\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\\J/\u0010]\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0019\u001a\u00020\u00072\b\b\u0002\u0010^\u001a\u00020*H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010_J\u0018\u0010`\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u0007J\u0019\u0010a\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u001c\u0010b\u001a\b\u0012\u0004\u0012\u00020\u000f0c2\u0006\u0010?\u001a\u00020'2\u0006\u0010\u001d\u001a\u00020\u0007J\u0016\u0010d\u001a\u00020-2\u0006\u0010?\u001a\u00020'2\u0006\u0010\u001d\u001a\u00020\u0007J\u0019\u0010e\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u0019\u0010f\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u0018\u0010g\u001a\u0004\u0018\u00010h2\u0006\u0010M\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u0007J\u0010\u0010i\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\u0019\u0010j\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u001b\u0010k\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u001d\u001a\u00020\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010lJQ\u0010m\u001a\b\u0012\u0004\u0012\u00020G0Z2\u0006\u0010&\u001a\u00020'2\b\u0010<\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010[\u001a\u00020*2\u0006\u0010\u001d\u001a\u00020\u00072\b\b\u0002\u0010n\u001a\u00020*2\n\b\u0002\u0010o\u001a\u0004\u0018\u00010pH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010qJ,\u0010r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00070s2\u0006\u0010E\u001a\u00020\u00072\u0006\u0010t\u001a\u00020\u000f2\u0006\u0010u\u001a\u00020\u0007H\u0002J\u0019\u0010v\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u0018\u0010w\u001a\u0004\u0018\u00010'2\u0006\u0010x\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u0007J\u0019\u0010y\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J+\u0010z\u001a\u0004\u0018\u00010{2\u0006\u0010|\u001a\u00020h2\u0006\u0010}\u001a\u00020\u00072\u0006\u0010~\u001a\u00020\u000fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u007fJ\u001a\u0010\u0080\u0001\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u001a\u0010\u0081\u0001\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u001a\u0010\u0082\u0001\u001a\u00020*2\u0007\u0010<\u001a\u00030\u0083\u00012\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\u001b\u0010\u0084\u0001\u001a\u00020'2\u0006\u0010&\u001a\u00020'H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u0085\u0001J\u001a\u0010\u0086\u0001\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u001a\u0010\u0087\u0001\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u001a\u0010\u0088\u0001\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u001a\u0010\u0089\u0001\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u001a\u0010\u008a\u0001\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u001a\u0010\u008b\u0001\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J/\u0010\u008c\u0001\u001a\u00020\u00182\u0006\u0010&\u001a\u00020'2\u0006\u0010\u001d\u001a\u00020\u00072\n\b\u0002\u0010<\u001a\u0004\u0018\u00010\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u008d\u0001J\u001a\u0010\u008e\u0001\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J(\u0010\u008f\u0001\u001a\b\u0012\u0004\u0012\u00020'0Z2\r\u0010\u0090\u0001\u001a\b\u0012\u0004\u0012\u00020'0ZH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u0091\u0001J\u001a\u0010\u0092\u0001\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J,\u0010\u0093\u0001\u001a\u00020\u00182\u0006\u0010&\u001a\u00020'2\u0007\u0010\u0094\u0001\u001a\u00020G2\u0006\u0010\u001d\u001a\u00020\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u0095\u0001J2\u0010\u0096\u0001\u001a\u00020\u00182\u0006\u0010&\u001a\u00020'2\u000e\u0010\u0097\u0001\u001a\t\u0012\u0005\u0012\u00030\u0098\u00010Z2\u0006\u0010\u001d\u001a\u00020\u00072\t\b\u0002\u0010\u0099\u0001\u001a\u00020*J.\u0010\u009a\u0001\u001a\u0010\u0012\u0004\u0012\u00020'\u0012\u0006\u0012\u0004\u0018\u00010\u00070s2\u0007\u0010\u009b\u0001\u001a\u00020'2\u0006\u0010\u001d\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u0015J#\u0010\u009c\u0001\u001a\u00020\u00182\u0006\u0010&\u001a\u00020'2\u0006\u0010\u001d\u001a\u00020\u0007H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u009d\u0001J?\u0010\u009e\u0001\u001a\u00020\u00182\b\u0010\u009f\u0001\u001a\u00030\u00a0\u00012\b\u0010\u00a1\u0001\u001a\u00030\u00a2\u00012\u0006\u0010(\u001a\u00020\u000f2\b\u0010\u00a3\u0001\u001a\u00030\u00a4\u00012\u0007\u0010\u00a5\u0001\u001a\u00020\u00072\u0007\u0010\u00a6\u0001\u001a\u00020-J>\u0010\u00a7\u0001\u001a\u00020\u00182\u0006\u0010&\u001a\u00020'2\r\u0010\u00a8\u0001\u001a\b\u0012\u0004\u0012\u00020G0Z2\u0006\u0010\u001d\u001a\u00020\u00072\n\b\u0002\u0010o\u001a\u0004\u0018\u00010pH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u00a9\u0001J,\u0010\u00aa\u0001\u001a\u00020\u00182\u0006\u0010&\u001a\u00020'2\u0007\u0010\u0094\u0001\u001a\u00020G2\u0006\u0010\u001d\u001a\u00020\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u0095\u0001J'\u0010\u00ab\u0001\u001a\u00020*2\u0006\u0010\u001d\u001a\u00020\u00072\n\b\u0002\u0010/\u001a\u0004\u0018\u00010\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u00ac\u0001J\u001a\u0010\u00ad\u0001\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u001a\u0010\u00ae\u0001\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u001a\u0010\u00af\u0001\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u001a\u0010\u00b0\u0001\u001a\u00020\u00182\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u001a\u0010\u00b1\u0001\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u001a\u0010\u00b2\u0001\u001a\u00020\u00182\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016JJ\u0010\u00b3\u0001\u001a\u0014\u0012\u0005\u0012\u00030\u0098\u00010Oj\t\u0012\u0005\u0012\u00030\u0098\u0001`Q2\u0006\u0010I\u001a\u00020\u00072\u0006\u0010&\u001a\u00020'2\t\b\u0002\u0010\u00b4\u0001\u001a\u00020*2\b\b\u0002\u0010\u001d\u001a\u00020\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u00b5\u0001J2\u0010\u00b6\u0001\u001a\t\u0012\u0005\u0012\u00030\u00b7\u00010Z2\u0006\u0010&\u001a\u00020'2\u0006\u0010F\u001a\u00020G2\u0006\u0010u\u001a\u00020\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u0095\u0001J+\u0010\u00b8\u0001\u001a\b\u0012\u0004\u0012\u00020\u000f0Z2\u0007\u0010\u00b9\u0001\u001a\u00020\u00072\u0007\u0010\u00ba\u0001\u001a\u00020\u0007H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u00ac\u0001J\u0019\u0010\u00bb\u0001\u001a\u00020\u00072\u0006\u0010&\u001a\u00020'2\u0006\u0010C\u001a\u00020DH\u0002J\u001a\u0010\u00bc\u0001\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J-\u0010\u00bd\u0001\u001a\u00020\u00182\u0006\u0010&\u001a\u00020'2\u0006\u0010C\u001a\u00020D2\b\u0010I\u001a\u0004\u0018\u00010\u0007H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u00be\u0001J>\u0010\u00bf\u0001\u001a\u00020\u00182\u0007\u0010\u00c0\u0001\u001a\u00020\u00072\u0006\u0010&\u001a\u00020'2\u0006\u0010C\u001a\u00020D2\b\u0010I\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u001d\u001a\u00020\u0007H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u00c1\u0001J\u0019\u0010\u00c2\u0001\u001a\u00020\u00182\u0006\u0010&\u001a\u00020'2\u0006\u0010C\u001a\u00020DH\u0002J$\u0010\u00c3\u0001\u001a\u00020\u00182\u0007\u0010\u00c4\u0001\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u00c5\u0001J$\u0010\u00c6\u0001\u001a\u00020*2\u0007\u0010\u00c7\u0001\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u00ac\u0001J\u001a\u0010\u00c8\u0001\u001a\u00020\u00182\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016JF\u0010\u00c9\u0001\u001a\u00020\u00182\b\u0010\u00ca\u0001\u001a\u00030\u00cb\u00012\u0006\u0010N\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u00072\u0017\b\u0002\u0010\u00cc\u0001\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u001bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u00cd\u0001J>\u0010\u00ce\u0001\u001a\u00020\u00182\b\u0010\u00ca\u0001\u001a\u00030\u00cb\u00012\u0006\u0010N\u001a\u00020\u00072\u0017\b\u0002\u0010\u00cc\u0001\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u001bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u00cf\u0001J>\u0010\u00d0\u0001\u001a\u00020\u00182\b\u0010\u00ca\u0001\u001a\u00030\u00cb\u00012\u0006\u0010N\u001a\u00020\u00072\u0017\b\u0002\u0010\u00cc\u0001\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u001bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u00cf\u0001J!\u0010\u00d1\u0001\u001a\u00020\u00072\u0006\u0010&\u001a\u00020'2\u0006\u0010F\u001a\u00020G2\u0006\u0010E\u001a\u00020\u0007H\u0002R!\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u00d2\u0001"}, d2={"Lcom/htmake/reader/api/controller/BookController;", "Lcom/htmake/reader/api/controller/BaseController;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)V", "backupFileNames", "", "", "getBackupFileNames", "()[Ljava/lang/String;", "backupFileNames$delegate", "Lkotlin/Lazy;", "bookInfoCache", "Lio/legado/app/utils/ACache;", "concurrentLoopCount", "", "webClient", "Lio/vertx/ext/web/client/WebClient;", "addBookGroupMulti", "Lcom/htmake/reader/api/ReturnData;", "context", "Lio/vertx/ext/web/RoutingContext;", "(Lio/vertx/ext/web/RoutingContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addInvalidBookSource", "", "sourceUrl", "invalidInfo", "", "", "userNameSpace", "backupToMongodb", "bookSourceDebugSSE", "cacheBookOnServer", "bookUrlList", "Lio/vertx/core/json/JsonArray;", "(Lio/vertx/core/json/JsonArray;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cacheBookSSE", "convertPdfPageToImage", "book", "Lio/legado/app/data/entities/Book;", "index", "force", "", "convertPdfToImage", "createUserBackup", "Ljava/io/File;", "backupDir", "latestZipFilePath", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteBook", "deleteBookCache", "deleteBooks", "editShelfBook", "handler", "Lkotlin/Function1;", "(Lio/legado/app/data/entities/Book;Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "exploreBook", "exportBook", "exportToEpub", "exportDir", "bookSource", "(Ljava/io/File;Lio/legado/app/data/entities/Book;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "exportToTxt", "bookInfo", "extractCbz", "extractEpub", "fixPic", "epubBook", "Lme/ag2s/epublib/domain/EpubBook;", "content", "chapter", "Lio/legado/app/data/entities/BookChapter;", "getAllContents", "bookSourceString", "append", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "text", "Ljava/util/ArrayList;", "Lkotlin/Triple;", "Lkotlin/collections/ArrayList;", "srcList", "(Lio/legado/app/data/entities/Book;Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAvailableBookSource", "getBookChaptersCache", "getBookContent", "getBookCover", "getBookInfo", "getBookShelfBooks", "", "refresh", "(ZLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBookSourceString", "withExploreUrl", "(Lio/vertx/ext/web/RoutingContext;Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBookSourceStringBySourceURLOpt", "getBookshelf", "getCachedChapterContentSet", "", "getChapterCacheDir", "getChapterList", "getChapterListByRule", "getHttpTTSByName", "Lio/legado/app/data/entities/HttpTTS;", "getInvalidBookSourceCache", "getInvalidBookSources", "getLastBackFileFromWebdav", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLocalChapterList", "debugLog", "mutex", "Lkotlinx/coroutines/sync/Mutex;", "(Lio/legado/app/data/entities/Book;Ljava/lang/String;ZLjava/lang/String;ZLkotlinx/coroutines/sync/Mutex;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getResultAndQueryIndex", "Lkotlin/Pair;", "queryIndexInContent", "query", "getShelfBook", "getShelfBookByURL", "url", "getShelfBookWithCacheInfo", "getSpeakStream", "Ljava/io/InputStream;", "httpTts", "speakText", "speechRate", "(Lio/legado/app/data/entities/HttpTTS;Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTxtTocRules", "importBookPreview", "isInvalidBookSource", "Lio/legado/app/data/entities/BookSource;", "mergeBookCacheInfo", "(Lio/legado/app/data/entities/Book;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "refreshLocalBook", "removeBookGroupMulti", "restoreFromMongodb", "saveBook", "saveBookConfig", "saveBookContent", "saveBookCover", "(Lio/legado/app/data/entities/Book;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveBookGroupId", "saveBookInfoCache", "bookList", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveBookProgress", "saveBookProgressToWebdav", "bookChapter", "(Lio/legado/app/data/entities/Book;Lio/legado/app/data/entities/BookChapter;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveBookSources", "sourceList", "Lio/legado/app/data/entities/SearchBook;", "replace", "saveBookToShelf", "_book", "saveLocalBookCover", "(Lio/legado/app/data/entities/Book;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "savePdfPageToImage", "document", "Lorg/apache/pdfbox/pdmodel/PDDocument;", "renderer", "Lorg/apache/pdfbox/rendering/PDFRenderer;", "targetWidth", "", "imageFormat", "output", "saveShelfBookLatestChapter", "bookChapterList", "(Lio/legado/app/data/entities/Book;Ljava/util/List;Ljava/lang/String;Lkotlinx/coroutines/sync/Mutex;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveShelfBookProgress", "saveToWebdav", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchBook", "searchBookContent", "searchBookMulti", "searchBookMultiSSE", "searchBookSource", "searchBookSourceSSE", "searchBookWithSource", "accurate", "(Ljava/lang/String;Lio/legado/app/data/entities/Book;ZLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchChapter", "Lio/legado/app/data/entities/SearchResult;", "searchPosition", "mContent", "pattern", "setAssets", "setBookSource", "setCover", "(Lio/legado/app/data/entities/Book;Lme/ag2s/epublib/domain/EpubBook;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setEpubContent", "contentModel", "(Ljava/lang/String;Lio/legado/app/data/entities/Book;Lme/ag2s/epublib/domain/EpubBook;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setEpubMetadata", "syncBookProgressFromWebdav", "progressFilePath", "(Ljava/lang/Object;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "syncFromWebdav", "zipFilePath", "textToSpeech", "ttsByApi", "response", "Lio/vertx/core/http/HttpServerResponse;", "options", "(Lio/vertx/core/http/HttpServerResponse;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "ttsByEdge", "(Lio/vertx/core/http/HttpServerResponse;Ljava/lang/String;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "ttsByTextToSpeechCn", "updateImageLinkInContent", "reader-pro"})
public final class BookController
extends BaseController {
    @NotNull
    private ACache bookInfoCache;
    private final int concurrentLoopCount;
    @NotNull
    private WebClient webClient;
    @NotNull
    private final Lazy backupFileNames$delegate;

    public BookController(@NotNull CoroutineContext coroutineContext2) {
        Intrinsics.checkNotNullParameter(coroutineContext2, "coroutineContext");
        super(coroutineContext2);
        this.bookInfoCache = ACache.Companion.get("bookInfoCache", 2000000L, 10000);
        this.concurrentLoopCount = 8;
        this.backupFileNames$delegate = LazyKt.lazy(backupFileNames.2.INSTANCE);
        WebClient webClient2 = SpringContextUtils.getBean("webClient", WebClient.class);
        Intrinsics.checkNotNullExpressionValue(webClient2, "getBean(\"webClient\", WebClient::class.java)");
        this.webClient = webClient2;
    }

    private final String[] getBackupFileNames() {
        Lazy lazy = this.backupFileNames$delegate;
        boolean bl = false;
        return (String[])lazy.getValue();
    }

    private final ACache getInvalidBookSourceCache(String userNameSpace) {
        String[] stringArray = new String[]{"storage", "cache", "invalidBookSourceCache", userNameSpace};
        File cacheDir2 = new File(ExtKt.getWorkDir(stringArray));
        ACache invalidBookSourceCache = ACache.Companion.get(cacheDir2, 5000000L, 1000000);
        return invalidBookSourceCache;
    }

    private final boolean isInvalidBookSource(BookSource bookSource, String userNameSpace) {
        return this.getInvalidBookSourceCache(userNameSpace).getAsString(bookSource.getBookSourceUrl()) != null;
    }

    private final void addInvalidBookSource(String sourceUrl, Map<String, ? extends Object> invalidInfo, String userNameSpace) {
        this.getInvalidBookSourceCache(userNameSpace).put(sourceUrl, ExtKt.jsonEncode$default(invalidInfo, false, 2, null), 600);
    }

    private final ACache getBookChaptersCache(String userNameSpace) {
        String[] stringArray = new String[]{"storage", "cache", "bookChaptersCache", userNameSpace};
        File cacheDir2 = new File(ExtKt.getWorkDir(stringArray));
        ACache bookChaptersCache = ACache.Companion.get(cacheDir2, 5000000L, 1000000);
        return bookChaptersCache;
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getInvalidBookSources(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof getInvalidBookSources.1)) ** GOTO lbl-1000
        var20_3 = var2_2;
        if ((var20_3.label & -2147483648) != 0) {
            var20_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.getInvalidBookSources(null, this);
                }
            };
        }
        $result = $continuation.result;
        var21_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var21_5) {
                    return var21_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                userNameSpace = this.getUserNameSpace(context);
                invalidBookSourceCache = this.getInvalidBookSourceCache(userNameSpace);
                var7_9 = new String[]{"storage", "cache", "invalidBookSourceCache", userNameSpace};
                cacheDir = new File(ExtKt.getWorkDir(var7_9));
                files = cacheDir.listFiles();
                var9_11 = false;
                invalidBookSourceList = new ArrayList<Map<String, Object>>();
                if (files != null) {
                    for (File f : files) {
                        var14_18 = f.getName();
                        Intrinsics.checkNotNullExpressionValue(var14_18, "f.name");
                        var13_17 = invalidBookSourceCache.getByHashCode(var14_18);
                        if (var13_17 == null) continue;
                        var14_18 = var13_17;
                        var15_19 = false;
                        var16_20 = false;
                        info = var14_18;
                        $i$a$-let-BookController$getInvalidBookSources$2 = false;
                        Boxing.boxBoolean(invalidBookSourceList.add(ExtKt.toMap(info)));
                    }
                }
                return ReturnData.setData$default(returnData, invalidBookSourceList, null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object getBookInfo(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof getBookInfo.1)) ** GOTO lbl-1000
        var19_3 = var2_2;
        if ((var19_3.label & -2147483648) != 0) {
            var19_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.getBookInfo(null, this);
                }
            };
        }
        $result = $continuation.result;
        var20_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                var4_7 = null;
                if (context.request().method() == HttpMethod.POST) {
                    var6_8 = context.getBodyAsJson().getString("url");
                    var5_11 = var6_8 == null ? context.getBodyAsJson().getJsonObject("searchBook").getString("bookUrl") : var6_8;
                    var4_7 = var5_11 == null ? "" : var5_11;
                } else {
                    var6_8 = context.queryParam("url");
                    Intrinsics.checkNotNullExpressionValue(var6_8, "context.queryParam(\"url\")");
                    var5_11 = (String)CollectionsKt.firstOrNull(var6_8);
                    bookUrl = var5_11 == null ? "" : var5_11;
                }
                var5_11 = (CharSequence)bookUrl;
                var6_9 = false;
                if (var5_11.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u4e66\u7c4d\u94fe\u63a5");
                }
                userNameSpace = this.getUserNameSpace((RoutingContext)context);
                BookControllerKt.access$getLogger$p().info("getBookInfo with bookUrl: {}", bookUrl);
                bookInfo = null;
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.L$3 = bookUrl;
                $continuation.L$4 = userNameSpace;
                $continuation.label = 1;
                v0 = this.checkAuth((RoutingContext)context, $continuation);
                if (v0 == var20_5) {
                    return var20_5;
                }
                ** GOTO lbl49
            }
            case 1: {
                bookInfo = null;
                userNameSpace = (String)$continuation.L$4;
                var4_7 = (String)$continuation.L$3;
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl49:
                // 2 sources

                if (((Boolean)v0).booleanValue()) {
                    bookInfo = this.getShelfBookByURL((String)var4_7, userNameSpace);
                }
                if (bookInfo != null) ** GOTO lbl142
                var7_12 /* !! */  = null;
                var9_13 = this.bookInfoCache.getAsString((String)var4_7);
                if (var9_13 == null) {
                    v1 = null;
                } else {
                    var10_14 = ExtKt.toMap(var9_13);
                    if (var10_14 == null) {
                        v1 = null;
                    } else {
                        $this$toDataClass$iv = var10_14;
                        $i$f$toDataClass = false;
                        $this$convert$iv$iv = $this$toDataClass$iv;
                        $i$f$convert = false;
                        json$iv$iv = $this$convert$iv$iv instanceof String != false ? (String)$this$convert$iv$iv : ExtKt.getGson().toJson($this$convert$iv$iv);
                        v1 = cacheInfo = (Book)ExtKt.getGson().fromJson(json$iv$iv, new TypeToken<Book>(){}.getType());
                    }
                }
                if (cacheInfo == null) break;
                $continuation.L$0 = this;
                $continuation.L$1 = var3_6;
                $continuation.L$2 = var4_7;
                $continuation.L$3 = userNameSpace;
                $continuation.L$4 = null;
                $continuation.label = 2;
                v2 = BookController.getBookSourceString$default(this, var1_1, cacheInfo.getOrigin(), false, $continuation, 4, null);
                if (v2 == var20_5) {
                    return var20_5;
                }
                ** GOTO lbl86
            }
            case 2: {
                var5_11 = (String)$continuation.L$3;
                var4_7 = (String)$continuation.L$2;
                var3_6 = (ReturnData)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v2 = $result;
lbl86:
                // 2 sources

                var7_12 /* !! */  = (String)v2;
                ** GOTO lbl106
            }
        }
        $continuation.L$0 = this;
        $continuation.L$1 = var3_6;
        $continuation.L$2 = var4_7;
        $continuation.L$3 = var5_11;
        $continuation.L$4 = null;
        $continuation.label = 3;
        v3 = BookController.getBookSourceString$default(this, var1_1, null, false, $continuation, 6, null);
        if (v3 == var20_5) {
            return var20_5;
        }
        ** GOTO lbl105
        {
            case 3: {
                var5_11 = (String)$continuation.L$3;
                var4_7 = (String)$continuation.L$2;
                var3_6 = (ReturnData)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v3 = $result;
lbl105:
                // 2 sources

                bookSource = (String)v3;
lbl106:
                // 2 sources

                var9_13 = bookSource;
                var10_15 = false;
                var11_17 = false;
                if (var9_13 == null || var9_13.length() == 0) {
                    return var3_6.setErrorMsg("\u672a\u914d\u7f6e\u4e66\u6e90");
                }
                var16_23 = this;
                $continuation.L$0 = this;
                $continuation.L$1 = var3_6;
                $continuation.L$2 = var16_23;
                $continuation.L$3 = null;
                $continuation.label = 4;
                v4 = WebBook.getBookInfo$default(new WebBook(bookSource, this.getAppConfig().getDebugLog(), null, (String)var5_11, 4, null), (String)var4_7, false, (Continuation)$continuation, 2, null);
                if (v4 == var20_5) {
                    return var20_5;
                }
                ** GOTO lbl127
            }
            case 4: {
                var16_23 = (BookController)$continuation.L$2;
                var3_6 = (ReturnData)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v4 = $result;
lbl127:
                // 2 sources

                var17_24 = v4;
                $continuation.L$0 = this;
                $continuation.L$1 = var3_6;
                $continuation.L$2 = null;
                $continuation.label = 5;
                v5 = var16_23.mergeBookCacheInfo((Book)var17_24, $continuation);
                if (v5 == var20_5) {
                    return var20_5;
                }
                ** GOTO lbl141
            }
            case 5: {
                var3_6 = (ReturnData)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v5 = $result;
lbl141:
                // 2 sources

                var6_10 = (Book)v5;
lbl142:
                // 2 sources

                var7_12 /* !! */  = new Book[]{var6_10};
                $continuation.L$0 = var3_6;
                $continuation.L$1 = var6_10;
                $continuation.L$2 = null;
                $continuation.L$3 = null;
                $continuation.L$4 = null;
                $continuation.label = 6;
                v6 = this.saveBookInfoCache((List<Book>)CollectionsKt.arrayListOf(var7_12 /* !! */ ), $continuation);
                if (v6 == var20_5) {
                    return var20_5;
                }
                ** GOTO lbl158
            }
            case 6: {
                var6_10 = (Book)$continuation.L$1;
                var3_6 = (ReturnData)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v6 = $result;
lbl158:
                // 2 sources

                return ReturnData.setData$default(var3_6, var6_10, null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @Nullable
    public final Object getBookCover(@NotNull RoutingContext context, @NotNull Continuation<? super Unit> $completion) {
        List<String> list2 = context.queryParam("path");
        Intrinsics.checkNotNullExpressionValue(list2, "context.queryParam(\"path\")");
        CharSequence charSequence = CollectionsKt.firstOrNull(list2);
        String coverUrl = charSequence == null ? "" : charSequence;
        charSequence = coverUrl;
        boolean bl = false;
        if (charSequence.length() == 0) {
            context.response().setStatusCode(404).end();
            return Unit.INSTANCE;
        }
        String ext = this.getFileExt(coverUrl, "png");
        String md5Encode = MD5Utils.INSTANCE.md5Encode(coverUrl);
        String[] stringArray = new String[]{"storage", "cache", "bookCoverCache", md5Encode + '.' + ext};
        String cachePath = ExtKt.getWorkDir(stringArray);
        File cacheFile = new File(cachePath);
        if (cacheFile.exists()) {
            BookControllerKt.access$getLogger$p().info("send cache: {}", (Object)cacheFile);
            HttpServerResponse httpServerResponse = context.response().putHeader("Cache-Control", "86400").sendFile(cacheFile.toString());
            if (httpServerResponse == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                return httpServerResponse;
            }
            return Unit.INSTANCE;
        }
        if (!cacheFile.getParentFile().exists()) {
            cacheFile.getParentFile().mkdirs();
        }
        boolean $i$f$CoroutineExceptionHandler = false;
        CoroutineExceptionHandler.Key key = CoroutineExceptionHandler.Key;
        CoroutineExceptionHandler exceptionHandler = new CoroutineExceptionHandler(key, context){
            final /* synthetic */ RoutingContext $context$inlined;
            {
                this.$context$inlined = routingContext;
                super($super_call_param$1);
            }

            /*
             * WARNING - void declaration
             */
            public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
                void ex;
                Throwable throwable = exception;
                CoroutineContext ctx = context;
                boolean bl = false;
                BookControllerKt.access$getLogger$p().info("get cover error: {}", (Object)ex.getMessage());
                this.$context$inlined.response().setStatusCode(404).end();
            }
        };
        Job job = BuildersKt.launch$default(this, new MDCContext(null, 1, null).plus(Dispatchers.getIO()).plus(exceptionHandler), null, new Function2<CoroutineScope, Continuation<? super Unit>, Object>(context, cacheFile, this, coverUrl, null){
            int label;
            final /* synthetic */ RoutingContext $context;
            final /* synthetic */ File $cacheFile;
            final /* synthetic */ BookController this$0;
            final /* synthetic */ String $coverUrl;
            {
                this.$context = $context;
                this.$cacheFile = $cacheFile;
                this.this$0 = $receiver;
                this.$coverUrl = $coverUrl;
                super(2, $completion);
            }

            /*
             * Unable to fully structure code
             */
            @Nullable
            public final Object invokeSuspend(@NotNull Object var1_1) {
                var5_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0: {
                        ResultKt.throwOnFailure(var1_1);
                        this.label = 1;
                        v0 = VertxCoroutineKt.awaitResult((Function1)new Function1<Handler<AsyncResult<HttpResponse<Buffer>>>, Unit>(this.this$0, this.$coverUrl){
                            final /* synthetic */ BookController this$0;
                            final /* synthetic */ String $coverUrl;
                            {
                                this.this$0 = $receiver;
                                this.$coverUrl = $coverUrl;
                                super(1);
                            }

                            public final void invoke(@NotNull Handler<AsyncResult<HttpResponse<Buffer>>> handler2) {
                                Intrinsics.checkNotNullParameter(handler2, "handler");
                                BookController.access$getWebClient$p(this.this$0).getAbs(this.$coverUrl).timeout(3000L).send(handler2);
                            }
                        }, this);
                        if (v0 == var5_2) {
                            return var5_2;
                        }
                        ** GOTO lbl13
                    }
                    case 1: {
                        ResultKt.throwOnFailure($result);
                        v0 = $result;
lbl13:
                        // 2 sources

                        v1 = bodyBytes = (var4_4 = (result = (HttpResponse)v0).bodyAsBuffer()) == null ? null : var4_4.getBytes();
                        if (bodyBytes != null) {
                            res = this.$context.response().putHeader("Cache-Control", "86400");
                            FilesKt.writeBytes(this.$cacheFile, bodyBytes);
                            res.sendFile(this.$cacheFile.toString());
                        } else {
                            this.$context.response().setStatusCode(404).end();
                        }
                        return Unit.INSTANCE;
                    }
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            @NotNull
            public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                return (Continuation)((Object)new /* invalid duplicate definition of identical inner class */);
            }

            @Nullable
            public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Unit> p2) {
                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
            }
        }, 2, null);
        if (job == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return job;
        }
        return Unit.INSTANCE;
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object importBookPreview(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof importBookPreview.1)) ** GOTO lbl-1000
        var24_3 = var2_2;
        if ((var24_3.label & -2147483648) != 0) {
            var24_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.importBookPreview(null, this);
                }
            };
        }
        $result = $continuation.result;
        var25_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var25_5) {
                    return var25_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                if (context.fileUploads() == null || context.fileUploads().isEmpty()) {
                    return returnData.setErrorMsg("\u8bf7\u4e0a\u4f20\u4e66\u7c4d\u6587\u4ef6");
                }
                userNameSpace = this.getUserNameSpace(context);
                var6_8 = false;
                fileList = new ArrayList<Map<K, V>>();
                var6_9 = context.fileUploads();
                Intrinsics.checkNotNullExpressionValue(var6_9, "context.fileUploads()");
                $this$forEach$iv = var6_9;
                $i$f$forEach = false;
                for (T element$iv : $this$forEach$iv) {
                    it = (FileUpload)element$iv;
                    $i$a$-forEach-BookController$importBookPreview$2 = false;
                    file = new File(it.uploadedFileName());
                    var13_17 = new Object[]{it.uploadedFileName(), it.fileName(), file};
                    BookControllerKt.access$getLogger$p().info("uploadFile: {} {} {}", var13_17);
                    if (!file.exists()) continue;
                    fileName = it.fileName();
                    v1 = this;
                    var14_18 = fileName;
                    Intrinsics.checkNotNullExpressionValue(var14_18, "fileName");
                    ext = BaseController.getFileExt$default(v1, (String)var14_18, null, 2, null);
                    if (!(Intrinsics.areEqual(ext, "txt") || Intrinsics.areEqual(ext, "epub") || Intrinsics.areEqual(ext, "umd") || Intrinsics.areEqual(ext, "cbz") || Intrinsics.areEqual(ext, "pdf"))) {
                        ExtKt.deleteRecursively(file);
                        return returnData.setErrorMsg("\u4e0d\u652f\u6301\u5bfc\u5165" + ext + "\u683c\u5f0f\u7684\u4e66\u7c4d\u6587\u4ef6");
                    }
                    var14_18 = fileName;
                    Intrinsics.checkNotNullExpressionValue(var14_18, "fileName");
                    fileName = FileUtils.INSTANCE.getNameExcludeExtension((String)var14_18);
                    var14_18 = fileName;
                    Intrinsics.checkNotNullExpressionValue(var14_18, "fileName");
                    var14_18 = var14_18;
                    var16_20 = AppPattern.INSTANCE.getFileNameRegex();
                    var17_22 = "";
                    var18_25 = false;
                    fileName = var16_20.replace(var14_18, var17_22);
                    v2 = new StringBuilder();
                    var14_18 = fileName;
                    Intrinsics.checkNotNullExpressionValue(var14_18, "fileName");
                    var16_21 = 0;
                    var17_23 = Math.min(50, fileName.length());
                    var18_25 = false;
                    v3 = var14_18.substring(var16_21, var17_23);
                    Intrinsics.checkNotNullExpressionValue(v3, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    fileName = v2.append(v3).append('.').append(ext).toString();
                    var16_20 = new String[]{"assets", userNameSpace, "book", fileName};
                    localFilePath = Paths.get("storage", var16_20).toString();
                    localFileUrl = "/assets/" + userNameSpace + "/book/" + fileName;
                    filePath = localFilePath;
                    var18_24 = fileName;
                    Intrinsics.checkNotNullExpressionValue(var18_24, "fileName");
                    if (StringsKt.endsWith(var18_24, ".epub", true)) {
                        filePath = filePath + File.separator + "index.epub";
                    }
                    var18_24 = fileName;
                    Intrinsics.checkNotNullExpressionValue(var18_24, "fileName");
                    if (StringsKt.endsWith(var18_24, ".cbz", true)) {
                        filePath = filePath + File.separator + "index.cbz";
                    }
                    var18_24 = fileName;
                    Intrinsics.checkNotNullExpressionValue(var18_24, "fileName");
                    if (StringsKt.endsWith(var18_24, ".pdf", true)) {
                        filePath = filePath + File.separator + "index.pdf";
                    }
                    if (!(newFile = new File(ExtKt.getWorkDir(filePath))).getParentFile().exists()) {
                        newFile.getParentFile().mkdirs();
                    }
                    if (newFile.exists()) {
                        newFile.delete();
                    }
                    BookControllerKt.access$getLogger$p().info("moveTo: {}", (Object)newFile);
                    if (FilesKt.copyRecursively$default(file, newFile, false, null, 6, null)) {
                        book = Book.Companion.initLocalBook(localFileUrl, localFilePath, ExtKt.getWorkDir$default(null, 1, null));
                        book.setUserNameSpace(userNameSpace);
                        try {
                            chapters = LocalBook.INSTANCE.getChapterList(book);
                            var21_29 = new Pair[]{TuplesKt.to("book", book), TuplesKt.to("chapters", chapters)};
                            fileList.add(MapsKt.mapOf(var21_29));
                        }
                        catch (TocEmptyException var20_28) {
                            var21_29 = new Pair[2];
                            var21_29[0] = TuplesKt.to("book", book);
                            var22_30 = false;
                            var21_29[1] = TuplesKt.to("chapters", new ArrayList<E>());
                            fileList.add(MapsKt.mapOf(var21_29));
                        }
                    }
                    ExtKt.deleteRecursively(file);
                }
                return ReturnData.setData$default(returnData, fileList, null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getTxtTocRules(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof getTxtTocRules.1)) ** GOTO lbl-1000
        var17_3 = var2_2;
        if ((var17_3.label & -2147483648) != 0) {
            var17_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.getTxtTocRules(null, this);
                }
            };
        }
        $result = $continuation.result;
        var18_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var18_5) {
                    return var18_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                userNameSpace = this.getUserNameSpace(context);
                var6_8 = new String[]{"txtTocRule"};
                txtTocRules = this.getUserStorage(userNameSpace, var6_8);
                var7_10 = false;
                rules = new ArrayList<E>();
                rules.addAll((Collection)DefaultData.INSTANCE.getTxtTocRules());
                if (txtTocRules != null) {
                    $this$fromJsonArray$iv = GsonExtensionsKt.getGSON();
                    $i$f$fromJsonArray = false;
                    var11_14 = false;
                    try {
                        var12_15 = Result.Companion;
                        $i$a$-runCatching-GsonExtensionsKt$fromJsonArray$1$iv = false;
                        var14_19 = $this$fromJsonArray$iv.fromJson(txtTocRules, (Type)new ParameterizedTypeImpl(TxtTocRule.class));
                        var13_17 = var14_19 instanceof List != false ? (List)var14_19 : null;
                        var14_20 = false;
                        var12_15 = Result.constructor-impl(var13_17);
                    }
                    catch (Throwable var13_18) {
                        var14_21 = Result.Companion;
                        var15_22 = false;
                        var12_15 = Result.constructor-impl(ResultKt.createFailure(var13_18));
                    }
                    var9_12 = var12_15;
                    var10_13 = false;
                    var8_23 = (List)(Result.isFailure-impl(var9_12) != false ? null : var9_12);
                    customRule = var8_23 == null ? CollectionsKt.emptyList() : var8_23;
                    rules.addAll(customRule);
                }
                return ReturnData.setData$default(returnData, rules, null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getChapterListByRule(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof getChapterListByRule.1)) ** GOTO lbl-1000
        var8_3 = var2_2;
        if ((var8_3.label & -2147483648) != 0) {
            var8_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.getChapterListByRule(null, this);
                }
            };
        }
        $result = $continuation.result;
        var9_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var9_5) {
                    return var9_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                book = context.getBodyAsJson().mapTo(Book.class);
                var5_8 = book.getOrigin();
                var6_9 = false;
                if (var5_8.length() == 0) {
                    return returnData.setErrorMsg("\u672a\u627e\u5230\u4e66\u6e90\u4fe1\u606f");
                }
                if (!(book.isLocalTxt() || book.isLocalEpub() || book.isLocalPdf())) {
                    return returnData.setErrorMsg("\u975e\u672c\u5730txt/epub/pdf\u4e66\u7c4d");
                }
                book.setRootDir(ExtKt.getWorkDir$default(null, 1, null));
                book.setUserNameSpace(this.getUserNameSpace(context));
                Intrinsics.checkNotNullExpressionValue(book, "book");
                chapters = LocalBook.INSTANCE.getChapterList(book);
                var6_10 = new Pair[]{TuplesKt.to("book", book), TuplesKt.to("chapters", chapters)};
                return ReturnData.setData$default(returnData, MapsKt.mapOf(var6_10), null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object refreshLocalBook(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof refreshLocalBook.1)) ** GOTO lbl-1000
        var8_3 = var2_2;
        if ((var8_3.label & -2147483648) != 0) {
            var8_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.refreshLocalBook(null, this);
                }
            };
        }
        $result = $continuation.result;
        var9_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var9_5) {
                    return var9_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                var4_7 = null;
                if (context.request().method() == HttpMethod.POST) {
                    var5_8 = context.getBodyAsJson().getString("bookUrl");
                    Intrinsics.checkNotNullExpressionValue(var5_8, "context.bodyAsJson.getString(\"bookUrl\")");
                    var4_7 = var5_8;
                } else {
                    var6_9 = context.queryParam("bookUrl");
                    Intrinsics.checkNotNullExpressionValue(var6_9, "context.queryParam(\"bookUrl\")");
                    var5_8 = CollectionsKt.firstOrNull(var6_9);
                    bookUrl = var5_8 == null ? "" : var5_8;
                }
                var5_8 = bookUrl;
                var6_10 = false;
                if (var5_8.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u4e66\u7c4d\u94fe\u63a5");
                }
                userNameSpace = this.getUserNameSpace(context);
                bookInfo = this.getShelfBookByURL(bookUrl, userNameSpace);
                if (bookInfo == null) {
                    return returnData.setErrorMsg("\u4e66\u7c4d\u4fe1\u606f\u9519\u8bef");
                }
                bookInfo.updateFromLocal(true);
                $continuation.L$0 = returnData;
                $continuation.L$1 = bookInfo;
                $continuation.L$2 = null;
                $continuation.label = 2;
                v1 = this.editShelfBook(bookInfo, userNameSpace, (Function1<? super Book, Book>)new Function1<Book, Book>(bookInfo){
                    final /* synthetic */ Book $bookInfo;
                    {
                        this.$bookInfo = $bookInfo;
                        super(1);
                    }

                    @NotNull
                    public final Book invoke(@NotNull Book existBook) {
                        Intrinsics.checkNotNullParameter(existBook, "existBook");
                        existBook.setCoverUrl(this.$bookInfo.getCoverUrl());
                        BookControllerKt.access$getLogger$p().info("refreshLocalBook: {}", (Object)existBook);
                        return existBook;
                    }
                }, $continuation);
                if (v1 == var9_5) {
                    return var9_5;
                }
                ** GOTO lbl61
            }
            case 2: {
                var6_11 = (Book)$continuation.L$1;
                var3_6 = (ReturnData)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v1 = $result;
lbl61:
                // 2 sources

                return ReturnData.setData$default(var3_6, var6_11, null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object getChapterList(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof getChapterList.1)) ** GOTO lbl-1000
        var20_3 = var2_2;
        if ((var20_3.label & -2147483648) != 0) {
            var20_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                Object L$5;
                int I$0;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.getChapterList(null, this);
                }
            };
        }
        $result = $continuation.result;
        var21_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var21_5) {
                    return var21_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                var4_7 = null;
                var5_8 = 0;
                if (context.request().method() == HttpMethod.POST) {
                    var7_9 = context.getBodyAsJson().getString("url");
                    var6_12 = var7_9 == null ? context.getBodyAsJson().getJsonObject("book").getString("bookUrl") : var7_9;
                    bookUrl = var6_12 == null ? "" : var6_12;
                    var6_12 = context.getBodyAsJson().getInteger("refresh", Boxing.boxInt(0));
                    Intrinsics.checkNotNullExpressionValue(var6_12, "context.bodyAsJson.getInteger(\"refresh\", 0)");
                    var5_8 = ((Number)var6_12).intValue();
                } else {
                    var7_9 = context.queryParam("url");
                    Intrinsics.checkNotNullExpressionValue(var7_9, "context.queryParam(\"url\")");
                    var6_12 = (String)CollectionsKt.firstOrNull(var7_9);
                    bookUrl = var6_12 == null ? "" : var6_12;
                    var7_9 = context.queryParam("refresh");
                    Intrinsics.checkNotNullExpressionValue(var7_9, "context.queryParam(\"refresh\")");
                    var6_12 = CollectionsKt.firstOrNull(var7_9);
                    if (var6_12 == null) {
                        v1 = 0;
                    } else {
                        var8_13 = var6_12;
                        var9_14 = false;
                        var7_9 = Boxing.boxInt(Integer.parseInt((String)var8_13));
                        v1 = var7_9 == null ? 0 : var7_9.intValue();
                    }
                    refresh = v1;
                }
                var6_12 = (CharSequence)bookUrl;
                var7_10 = false;
                if (var6_12.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u4e66\u7c4d\u94fe\u63a5");
                }
                userNameSpace = this.getUserNameSpace(context);
                bookInfo = this.getShelfBookByURL((String)bookUrl, userNameSpace);
                var8_13 = null;
                if (bookInfo != null) break;
                var10_19 /* !! */  = this.bookInfoCache.getAsString((String)bookUrl);
                if (var10_19 /* !! */  == null) {
                    v2 = null;
                } else {
                    var11_22 = ExtKt.toMap(var10_19 /* !! */ );
                    if (var11_22 == null) {
                        v2 = null;
                    } else {
                        $this$toDataClass$iv = var11_22;
                        $i$f$toDataClass = false;
                        $this$convert$iv$iv = $this$toDataClass$iv;
                        $i$f$convert = false;
                        json$iv$iv = $this$convert$iv$iv instanceof String != false ? (String)$this$convert$iv$iv : ExtKt.getGson().toJson($this$convert$iv$iv);
                        v2 = cacheInfo = (Book)ExtKt.getGson().fromJson(json$iv$iv, new TypeToken<Book>(){}.getType());
                    }
                }
                if (cacheInfo == null) break;
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.L$3 = bookUrl;
                $continuation.L$4 = userNameSpace;
                $continuation.I$0 = refresh;
                $continuation.label = 2;
                v3 = BookController.getBookSourceString$default(this, context, cacheInfo.getOrigin(), false, $continuation, 4, null);
                if (v3 == var21_5) {
                    return var21_5;
                }
                ** GOTO lbl98
            }
            case 2: {
                var5_8 = $continuation.I$0;
                var6_12 = (String)$continuation.L$4;
                var4_7 = (String)$continuation.L$3;
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v3 = $result;
lbl98:
                // 2 sources

                var8_13 = (String)v3;
                ** GOTO lbl121
            }
        }
        $continuation.L$0 = this;
        $continuation.L$1 = var1_1;
        $continuation.L$2 = var3_6;
        $continuation.L$3 = var4_7;
        $continuation.L$4 = var6_12;
        $continuation.I$0 = var5_8;
        $continuation.label = 3;
        v4 = BookController.getBookSourceString$default(this, var1_1, null, false, $continuation, 6, null);
        if (v4 == var21_5) {
            return var21_5;
        }
        ** GOTO lbl120
        {
            case 3: {
                var5_8 = $continuation.I$0;
                var6_12 = (String)$continuation.L$4;
                var4_7 = (String)$continuation.L$3;
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v4 = $result;
lbl120:
                // 2 sources

                bookSource = (String)v4;
lbl121:
                // 2 sources

                var10_19 /* !! */  = (Book[])bookSource;
                var11_23 = false;
                var12_25 = false;
                if (var10_19 /* !! */  == null || var10_19 /* !! */ .length() == 0) {
                    return var3_6.setErrorMsg("\u672a\u914d\u7f6e\u4e66\u6e90");
                }
                var17_30 = this;
                $continuation.L$0 = this;
                $continuation.L$1 = var1_1;
                $continuation.L$2 = var3_6;
                $continuation.L$3 = var6_12;
                $continuation.L$4 = bookSource;
                $continuation.L$5 = var17_30;
                $continuation.I$0 = var5_8;
                $continuation.label = 4;
                v5 = WebBook.getBookInfo$default(new WebBook(bookSource, this.getAppConfig().getDebugLog(), null, (String)var6_12, 4, null), var4_7, false, (Continuation)$continuation, 2, null);
                if (v5 == var21_5) {
                    return var21_5;
                }
                ** GOTO lbl149
            }
            case 4: {
                var5_8 = $continuation.I$0;
                var17_30 = (BookController)$continuation.L$5;
                var8_13 = (String)$continuation.L$4;
                var6_12 = (String)$continuation.L$3;
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v5 = $result;
lbl149:
                // 2 sources

                var18_31 = v5;
                $continuation.L$0 = this;
                $continuation.L$1 = var1_1;
                $continuation.L$2 = var3_6;
                $continuation.L$3 = var6_12;
                $continuation.L$4 = var8_13;
                $continuation.L$5 = null;
                $continuation.I$0 = var5_8;
                $continuation.label = 5;
                v6 = var17_30.mergeBookCacheInfo((Book)var18_31, $continuation);
                if (v6 == var21_5) {
                    return var21_5;
                }
                ** GOTO lbl171
            }
            case 5: {
                var5_8 = $continuation.I$0;
                var8_13 = (String)$continuation.L$4;
                var6_12 = (String)$continuation.L$3;
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v6 = $result;
lbl171:
                // 2 sources

                var7_11 = (Book)v6;
                var10_19 /* !! */  = new Book[]{var7_11};
                $continuation.L$0 = this;
                $continuation.L$1 = var1_1;
                $continuation.L$2 = var3_6;
                $continuation.L$3 = var6_12;
                $continuation.L$4 = var7_11;
                $continuation.L$5 = var8_13;
                $continuation.I$0 = var5_8;
                $continuation.label = 6;
                v7 = this.saveBookInfoCache((List<Book>)CollectionsKt.arrayListOf(var10_19 /* !! */ ), $continuation);
                if (v7 == var21_5) {
                    return var21_5;
                }
                ** GOTO lbl217
            }
            case 6: {
                var5_8 = $continuation.I$0;
                var8_13 = (String)$continuation.L$5;
                var7_11 = (Book)$continuation.L$4;
                var6_12 = (String)$continuation.L$3;
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v7 = $result;
                ** GOTO lbl217
            }
        }
        $continuation.L$0 = this;
        $continuation.L$1 = var1_1;
        $continuation.L$2 = var3_6;
        $continuation.L$3 = var6_12;
        $continuation.L$4 = var7_11;
        $continuation.I$0 = var5_8;
        $continuation.label = 7;
        v8 = BookController.getBookSourceString$default(this, var1_1, var7_11.getOrigin(), false, $continuation, 4, null);
        if (v8 == var21_5) {
            return var21_5;
        }
        ** GOTO lbl216
        {
            case 7: {
                var5_8 = $continuation.I$0;
                var7_11 = (Book)$continuation.L$4;
                var6_12 = (String)$continuation.L$3;
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v8 = $result;
lbl216:
                // 2 sources

                var8_13 = (String)v8;
lbl217:
                // 3 sources

                if (!var7_11.isLocalBook()) {
                    cacheInfo = (CharSequence)var8_13;
                    var10_20 = false;
                    var11_23 = false;
                    if (cacheInfo == null || cacheInfo.length() == 0) {
                        return var3_6.setErrorMsg("\u672a\u914d\u7f6e\u4e66\u6e90");
                    }
                }
                var7_11.setRootDir(ExtKt.getWorkDir$default(null, 1, null));
                var7_11.setUserNameSpace((String)var6_12);
                if (var7_11.isLocalBook() && !(localFile = var7_11.getLocalFile()).exists()) {
                    BookControllerKt.access$getLogger$p().info("localFile: {} not exists", (Object)localFile);
                    return var3_6.setErrorMsg("\u672c\u5730\u4e66\u7c4d\u6e90\u6587\u4ef6\u4e0d\u5b58\u5728");
                }
                BookControllerKt.access$getLogger$p().info("bookInfo: {}", (Object)var7_11);
                var10_21 = var8_13;
                $continuation.L$0 = var3_6;
                $continuation.L$1 = null;
                $continuation.L$2 = null;
                $continuation.L$3 = null;
                $continuation.L$4 = null;
                $continuation.L$5 = null;
                $continuation.label = 8;
                v9 = BookController.getLocalChapterList$default(this, var7_11, var10_21 == null ? "" : var10_21, var5_8 > 0, this.getUserNameSpace(var1_1), false, null, $continuation, 48, null);
                if (v9 == var21_5) {
                    return var21_5;
                }
                ** GOTO lbl245
            }
            case 8: {
                var3_6 = (ReturnData)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v9 = $result;
lbl245:
                // 2 sources

                chapterList = (List)v9;
                return ReturnData.setData$default(var3_6, chapterList, null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object saveBookProgress(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof saveBookProgress.1)) ** GOTO lbl-1000
        var13_3 = var2_2;
        if ((var13_3.label & -2147483648) != 0) {
            var13_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                int I$0;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.saveBookProgress(null, this);
                }
            };
        }
        $result = $continuation.result;
        var14_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var14_5) {
                    return var14_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                var4_7 = null;
                var5_8 = 0;
                if (context.request().method() == HttpMethod.POST) {
                    var7_9 = context.getBodyAsJson().getString("url");
                    var6_12 = var7_9 == null ? context.getBodyAsJson().getJsonObject("searchBook").getString("bookUrl") : var7_9;
                    bookUrl = var6_12 == null ? "" : var6_12;
                    var6_12 = context.getBodyAsJson().getInteger("index", Boxing.boxInt(-1));
                    Intrinsics.checkNotNullExpressionValue(var6_12, "context.bodyAsJson.getInteger(\"index\", -1)");
                    var5_8 = ((Number)var6_12).intValue();
                } else {
                    var7_9 = context.queryParam("url");
                    Intrinsics.checkNotNullExpressionValue(var7_9, "context.queryParam(\"url\")");
                    var6_12 = (String)CollectionsKt.firstOrNull(var7_9);
                    bookUrl = var6_12 == null ? "" : var6_12;
                    var7_9 = context.queryParam("index");
                    Intrinsics.checkNotNullExpressionValue(var7_9, "context.queryParam(\"index\")");
                    var6_12 = CollectionsKt.firstOrNull(var7_9);
                    if (var6_12 == null) {
                        v1 = -1;
                    } else {
                        var8_13 = var6_12;
                        var9_14 = false;
                        var7_9 = Boxing.boxInt(Integer.parseInt((String)var8_13));
                        v1 = var7_9 == null ? -1 : var7_9.intValue();
                    }
                    chapterIndex = v1;
                }
                var6_12 = (CharSequence)bookUrl;
                var7_10 = false;
                if (var6_12.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u4e66\u7c4d\u94fe\u63a5");
                }
                userNameSpace = this.getUserNameSpace(context);
                bookInfo = this.getShelfBookByURL((String)bookUrl, userNameSpace);
                if (bookInfo == null) ** GOTO lbl64
                var8_13 = bookInfo.getOrigin();
                var9_14 = false;
                if (!(var8_13.length() == 0)) ** GOTO lbl65
lbl64:
                // 2 sources

                return returnData.setErrorMsg("\u4e66\u7c4d\u672a\u52a0\u5165\u4e66\u67b6");
lbl65:
                // 1 sources

                bookSource = this.getBookSourceStringBySourceURLOpt(bookInfo.getOrigin(), userNameSpace);
                if (!bookInfo.isLocalBook()) {
                    var9_15 = bookSource;
                    var10_17 = false;
                    var11_19 = false;
                    if (var9_15 == null || var9_15.length() == 0) {
                        return returnData.setErrorMsg("\u672a\u914d\u7f6e\u4e66\u6e90");
                    }
                }
                v2 = (var10_18 = bookSource) == null ? "" : var10_18;
                $continuation.L$0 = this;
                $continuation.L$1 = returnData;
                $continuation.L$2 = userNameSpace;
                $continuation.L$3 = bookInfo;
                $continuation.I$0 = chapterIndex;
                $continuation.label = 2;
                v3 = BookController.getLocalChapterList$default(this, bookInfo, v2, false, userNameSpace, false, null, $continuation, 48, null);
                if (v3 == var14_5) {
                    return var14_5;
                }
                ** GOTO lbl91
            }
            case 2: {
                var5_8 = $continuation.I$0;
                var7_11 = (Book)$continuation.L$3;
                var6_12 = (String)$continuation.L$2;
                var3_6 = (ReturnData)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v3 = $result;
lbl91:
                // 2 sources

                if (var5_8 >= (chapterList = (List)v3).size()) {
                    return var3_6.setErrorMsg("\u7ae0\u8282\u4e0d\u5b58\u5728");
                }
                chapterInfo = (BookChapter)chapterList.get(var5_8);
                $continuation.L$0 = this;
                $continuation.L$1 = var3_6;
                $continuation.L$2 = var6_12;
                $continuation.L$3 = var7_11;
                $continuation.L$4 = chapterInfo;
                $continuation.label = 3;
                v4 = this.saveShelfBookProgress(var7_11, chapterInfo, (String)var6_12, $continuation);
                if (v4 == var14_5) {
                    return var14_5;
                }
                ** GOTO lbl112
            }
            case 3: {
                chapterInfo = (BookChapter)$continuation.L$4;
                var7_11 = (Book)$continuation.L$3;
                var6_12 = (String)$continuation.L$2;
                var3_6 = (ReturnData)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v4 = $result;
lbl112:
                // 2 sources

                $continuation.L$0 = var3_6;
                $continuation.L$1 = null;
                $continuation.L$2 = null;
                $continuation.L$3 = null;
                $continuation.L$4 = null;
                $continuation.label = 4;
                v5 = this.saveBookProgressToWebdav(var7_11, chapterInfo, (String)var6_12, $continuation);
                if (v5 == var14_5) {
                    return var14_5;
                }
                ** GOTO lbl126
            }
            case 4: {
                var3_6 = (ReturnData)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v5 = $result;
lbl126:
                // 2 sources

                return ReturnData.setData$default(var3_6, "", null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object getBookContent(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        block85: {
            if (!(var2_2 instanceof getBookContent.1)) ** GOTO lbl-1000
            var30_3 = var2_2;
            if ((var30_3.label & -2147483648) != 0) {
                var30_3.label -= -2147483648;
            } else lbl-1000:
            // 2 sources

            {
                $continuation = new ContinuationImpl(this, var2_2){
                    Object L$0;
                    Object L$1;
                    Object L$2;
                    Object L$3;
                    Object L$4;
                    Object L$5;
                    Object L$6;
                    int I$0;
                    int I$1;
                    int I$2;
                    int I$3;
                    int I$4;
                    /* synthetic */ Object result;
                    final /* synthetic */ BookController this$0;
                    int label;
                    {
                        this.this$0 = this$0;
                        super($completion);
                    }

                    @Nullable
                    public final Object invokeSuspend(@NotNull Object $result) {
                        this.result = $result;
                        this.label |= Integer.MIN_VALUE;
                        return this.this$0.getBookContent(null, this);
                    }
                };
            }
            $result = $continuation.result;
            var31_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch ($continuation.label) {
                case 0: {
                    ResultKt.throwOnFailure($result);
                    returnData = new ReturnData();
                    $continuation.L$0 = this;
                    $continuation.L$1 = context;
                    $continuation.L$2 = returnData;
                    $continuation.label = 1;
                    v0 = this.checkAuth(context, $continuation);
                    if (v0 == var31_5) {
                        return var31_5;
                    }
                    ** GOTO lbl27
                }
                case 1: {
                    returnData = (ReturnData)$continuation.L$2;
                    context = (RoutingContext)$continuation.L$1;
                    this = (BookController)$continuation.L$0;
                    ResultKt.throwOnFailure($result);
                    v0 = $result;
lbl27:
                    // 2 sources

                    if (!((Boolean)v0).booleanValue()) {
                        return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                    }
                    var4_7 = null;
                    var5_8 = null;
                    var6_9 = 0;
                    var7_10 = 0;
                    var8_11 = 0;
                    var9_12 = 0;
                    if (context.request().method() == HttpMethod.POST) {
                        var10_13 = context.getBodyAsJson().getString("chapterUrl");
                        chapterUrl = var10_13 == null ? ((var11_14 = context.getBodyAsJson().getJsonObject("bookChapter")) == null ? "" : ((var12_17 = var11_14.getString("url")) == null ? "" : var12_17)) : var10_13;
                        var10_13 = context.getBodyAsJson().getString("url");
                        bookUrl = var10_13 == null ? ((var11_14 = context.getBodyAsJson().getJsonObject("searchBook")) == null ? "" : ((var12_17 = var11_14.getString("bookUrl")) == null ? "" : var12_17)) : var10_13;
                        var10_13 = context.getBodyAsJson().getInteger("index", Boxing.boxInt(-1));
                        Intrinsics.checkNotNullExpressionValue(var10_13, "context.bodyAsJson.getInteger(\"index\", -1)");
                        chapterIndex = ((Number)var10_13).intValue();
                        var10_13 = context.getBodyAsJson().getInteger("cache", Boxing.boxInt(0));
                        Intrinsics.checkNotNullExpressionValue(var10_13, "context.bodyAsJson.getInteger(\"cache\", 0)");
                        cache = ((Number)var10_13).intValue();
                        var10_13 = context.getBodyAsJson().getInteger("refresh", Boxing.boxInt(0));
                        Intrinsics.checkNotNullExpressionValue(var10_13, "context.bodyAsJson.getInteger(\"refresh\", 0)");
                        refresh = ((Number)var10_13).intValue();
                        var10_13 = context.getBodyAsJson().getInteger("epubContent", Boxing.boxInt(0));
                        Intrinsics.checkNotNullExpressionValue(var10_13, "context.bodyAsJson.getInteger(\"epubContent\", 0)");
                        var9_12 = ((Number)var10_13).intValue();
                    } else {
                        var11_14 = context.queryParam("chapterUrl");
                        Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"chapterUrl\")");
                        var10_13 = CollectionsKt.firstOrNull(var11_14);
                        chapterUrl = var10_13 == null ? "" : var10_13;
                        var11_14 = context.queryParam("url");
                        Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"url\")");
                        var10_13 = CollectionsKt.firstOrNull(var11_14);
                        bookUrl = var10_13 == null ? "" : var10_13;
                        var11_14 = context.queryParam("index");
                        Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"index\")");
                        var10_13 = CollectionsKt.firstOrNull(var11_14);
                        if (var10_13 == null) {
                            v1 = -1;
                        } else {
                            var12_17 = var10_13;
                            var13_19 = false;
                            var11_14 = Boxing.boxInt(Integer.parseInt((String)var12_17));
                            v1 = var11_14 == null ? -1 : var11_14.intValue();
                        }
                        chapterIndex = v1;
                        var11_14 = context.queryParam("cache");
                        Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"cache\")");
                        var10_13 = (String)CollectionsKt.firstOrNull(var11_14);
                        if (var10_13 == null) {
                            v2 = 0;
                        } else {
                            var12_17 = var10_13;
                            var13_19 = false;
                            var11_14 = Boxing.boxInt(Integer.parseInt((String)var12_17));
                            v2 = var11_14 == null ? 0 : var11_14.intValue();
                        }
                        cache = v2;
                        var11_14 = context.queryParam("refresh");
                        Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"refresh\")");
                        var10_13 = (String)CollectionsKt.firstOrNull(var11_14);
                        if (var10_13 == null) {
                            v3 = 0;
                        } else {
                            var12_17 = var10_13;
                            var13_19 = false;
                            var11_14 = Boxing.boxInt(Integer.parseInt((String)var12_17));
                            v3 = var11_14 == null ? 0 : var11_14.intValue();
                        }
                        refresh = v3;
                        var11_14 = context.queryParam("epubContent");
                        Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"epubContent\")");
                        var10_13 = (String)CollectionsKt.firstOrNull(var11_14);
                        if (var10_13 == null) {
                            v4 = 0;
                        } else {
                            var12_17 = var10_13;
                            var13_19 = false;
                            var11_14 = Boxing.boxInt(Integer.parseInt((String)var12_17));
                            v4 = var11_14 == null ? 0 : var11_14.intValue();
                        }
                        epubContent = v4;
                    }
                    var10_13 = (CharSequence)bookUrl;
                    var11_15 = false;
                    if (var10_13.length() == 0) {
                        return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u4e66\u7c4d\u94fe\u63a5");
                    }
                    $continuation.L$0 = this;
                    $continuation.L$1 = context;
                    $continuation.L$2 = returnData;
                    $continuation.L$3 = chapterUrl;
                    $continuation.L$4 = bookUrl;
                    $continuation.I$0 = chapterIndex;
                    $continuation.I$1 = cache;
                    $continuation.I$2 = refresh;
                    $continuation.I$3 = epubContent;
                    $continuation.label = 2;
                    v5 = BookController.getBookSourceString$default(this, context, null, false, $continuation, 6, null);
                    if (v5 == var31_5) {
                        return var31_5;
                    }
                    ** GOTO lbl135
                }
                case 2: {
                    var9_12 = $continuation.I$3;
                    var8_11 = $continuation.I$2;
                    var7_10 = $continuation.I$1;
                    var6_9 = $continuation.I$0;
                    var5_8 = (String)$continuation.L$4;
                    var4_7 = (String)$continuation.L$3;
                    var3_6 = (ReturnData)$continuation.L$2;
                    var1_1 = (RoutingContext)$continuation.L$1;
                    this = (BookController)$continuation.L$0;
                    ResultKt.throwOnFailure($result);
                    v5 = $result;
lbl135:
                    // 2 sources

                    bookSource = (String)v5;
                    userNameSpace = this.getUserNameSpace(var1_1);
                    isInBookShelf = 0;
                    bookInfo = null;
                    chapterInfo = null;
                    nextChapterUrl = null;
                    var16_23 = var5_8;
                    var17_24 = false;
                    if (!(var16_23.length() > 0)) ** GOTO lbl383
                    bookInfo = this.getShelfBookByURL(var5_8, userNameSpace);
                    if (bookInfo != null) {
                        var16_23 = bookInfo.getOrigin();
                        var17_24 = false;
                        if (var16_23.length() > 0) {
                            isInBookShelf = 1;
                            bookSource = this.getBookSourceStringBySourceURLOpt(bookInfo.getOrigin(), userNameSpace);
                        }
                    }
                    if ((var17_25 = this.bookInfoCache.getAsString(var5_8)) == null) {
                        v6 = null;
                    } else {
                        var18_28 = ExtKt.toMap(var17_25);
                        if (var18_28 == null) {
                            v6 = null;
                        } else {
                            $this$toDataClass$iv = var18_28;
                            $i$f$toDataClass = false;
                            $this$convert$iv$iv = $this$toDataClass$iv;
                            $i$f$convert = false;
                            json$iv$iv = $this$convert$iv$iv instanceof String != false ? (String)$this$convert$iv$iv : ExtKt.getGson().toJson($this$convert$iv$iv);
                            v6 = cacheInfo = (Book)ExtKt.getGson().fromJson(json$iv$iv, new TypeToken<Book>(){}.getType());
                        }
                    }
                    if (cacheInfo == null) ** GOTO lbl199
                    $continuation.L$0 = this;
                    $continuation.L$1 = var3_6;
                    $continuation.L$2 = var4_7;
                    $continuation.L$3 = var5_8;
                    $continuation.L$4 = userNameSpace;
                    $continuation.L$5 = bookInfo;
                    $continuation.I$0 = var6_9;
                    $continuation.I$1 = var7_10;
                    $continuation.I$2 = var8_11;
                    $continuation.I$3 = var9_12;
                    $continuation.I$4 = isInBookShelf;
                    $continuation.label = 3;
                    v7 = BookController.getBookSourceString$default(this, var1_1, cacheInfo.getOrigin(), false, $continuation, 4, null);
                    if (v7 == var31_5) {
                        return var31_5;
                    }
                    ** GOTO lbl198
                }
                case 3: {
                    var12_18 = $continuation.I$4;
                    var9_12 = $continuation.I$3;
                    var8_11 = $continuation.I$2;
                    var7_10 = $continuation.I$1;
                    var6_9 = $continuation.I$0;
                    var15_22 = null;
                    var14_21 = null;
                    var13_20 = (Book)$continuation.L$5;
                    var11_16 = (String)$continuation.L$4;
                    var5_8 = (String)$continuation.L$3;
                    var4_7 = (String)$continuation.L$2;
                    var3_6 = (ReturnData)$continuation.L$1;
                    this = (BookController)$continuation.L$0;
                    ResultKt.throwOnFailure($result);
                    v7 = $result;
lbl198:
                    // 2 sources

                    var10_13 = (String)v7;
lbl199:
                    // 2 sources

                    var17_25 = var4_7;
                    var18_29 = false;
                    if (!(var17_25.length() == 0) || var6_9 < 0) ** GOTO lbl383
                    var17_25 = var5_8;
                    var18_29 = false;
                    if (var17_25.length() == 0) {
                        return var3_6.setErrorMsg("\u8bf7\u8f93\u5165\u4e66\u7c4d\u94fe\u63a5");
                    }
                    if (var13_20 != null && !var13_20.isLocalBook()) {
                        var17_25 = (CharSequence)var10_13;
                        var18_29 = false;
                        $this$toDataClass$iv = false;
                        if (var17_25 == null || var17_25.length() == 0) {
                            return var3_6.setErrorMsg("\u672a\u914d\u7f6e\u4e66\u6e90");
                        }
                    }
                    if ((var17_25 = var13_20) != null) break;
                    var27_55 = this;
                    var18_30 = var10_13;
                    v8 = var18_30 == null ? "" : var18_30;
                    $continuation.L$0 = this;
                    $continuation.L$1 = var3_6;
                    $continuation.L$2 = var4_7;
                    $continuation.L$3 = var10_13;
                    $continuation.L$4 = var11_16;
                    $continuation.L$5 = var27_55;
                    $continuation.I$0 = var6_9;
                    $continuation.I$1 = var7_10;
                    $continuation.I$2 = var8_11;
                    $continuation.I$3 = var9_12;
                    $continuation.I$4 = var12_18;
                    $continuation.label = 4;
                    v9 = WebBook.getBookInfo$default(new WebBook((String)v8, this.getAppConfig().getDebugLog(), null, var11_16, 4, null), var5_8, false, (Continuation)$continuation, 2, null);
                    if (v9 == var31_5) {
                        return var31_5;
                    }
                    ** GOTO lbl248
                }
                case 4: {
                    var12_18 = $continuation.I$4;
                    var9_12 = $continuation.I$3;
                    var8_11 = $continuation.I$2;
                    var7_10 = $continuation.I$1;
                    var6_9 = $continuation.I$0;
                    var27_55 = (BookController)$continuation.L$5;
                    var15_22 = null;
                    var14_21 = null;
                    var11_16 = (String)$continuation.L$4;
                    var10_13 = (String)$continuation.L$3;
                    var4_7 = (String)$continuation.L$2;
                    var3_6 = (ReturnData)$continuation.L$1;
                    this = (BookController)$continuation.L$0;
                    ResultKt.throwOnFailure($result);
                    v9 = $result;
lbl248:
                    // 2 sources

                    var28_56 = v9;
                    $continuation.L$0 = this;
                    $continuation.L$1 = var3_6;
                    $continuation.L$2 = var4_7;
                    $continuation.L$3 = var10_13;
                    $continuation.L$4 = var11_16;
                    $continuation.L$5 = null;
                    $continuation.I$0 = var6_9;
                    $continuation.I$1 = var7_10;
                    $continuation.I$2 = var8_11;
                    $continuation.I$3 = var9_12;
                    $continuation.I$4 = var12_18;
                    $continuation.label = 5;
                    v10 = var27_55.mergeBookCacheInfo((Book)var28_56, $continuation);
                    if (v10 == var31_5) {
                        return var31_5;
                    }
                    ** GOTO lbl280
                }
                case 5: {
                    var12_18 = $continuation.I$4;
                    var9_12 = $continuation.I$3;
                    var8_11 = $continuation.I$2;
                    var7_10 = $continuation.I$1;
                    var6_9 = $continuation.I$0;
                    var15_22 = null;
                    var14_21 = null;
                    var11_16 = (String)$continuation.L$4;
                    var10_13 = (String)$continuation.L$3;
                    var4_7 = (String)$continuation.L$2;
                    var3_6 = (ReturnData)$continuation.L$1;
                    this = (BookController)$continuation.L$0;
                    ResultKt.throwOnFailure($result);
                    v10 = $result;
lbl280:
                    // 2 sources

                    v11 = (Book)v10;
                    break block85;
                }
            }
            v11 = var17_25;
        }
        var13_20 = v11;
        var18_31 = var10_13;
        $continuation.L$0 = this;
        $continuation.L$1 = var3_6;
        $continuation.L$2 = var4_7;
        $continuation.L$3 = var10_13;
        $continuation.L$4 = var11_16;
        $continuation.L$5 = var13_20;
        $continuation.I$0 = var6_9;
        $continuation.I$1 = var7_10;
        $continuation.I$2 = var8_11;
        $continuation.I$3 = var9_12;
        $continuation.I$4 = var12_18;
        $continuation.label = 6;
        v12 = BookController.getLocalChapterList$default(this, var13_20, var18_31 == null ? "" : var18_31, false, var11_16, false, null, $continuation, 48, null);
        if (v12 == var31_5) {
            return var31_5;
        }
        ** GOTO lbl318
        {
            case 6: {
                var12_18 = $continuation.I$4;
                var9_12 = $continuation.I$3;
                var8_11 = $continuation.I$2;
                var7_10 = $continuation.I$1;
                var6_9 = $continuation.I$0;
                var15_22 = null;
                var14_21 = null;
                var13_20 = (Book)$continuation.L$5;
                var11_16 = (String)$continuation.L$4;
                var10_13 = (String)$continuation.L$3;
                var4_7 = (String)$continuation.L$2;
                var3_6 = (ReturnData)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v12 = $result;
lbl318:
                // 2 sources

                if (var6_9 >= (chapterList = (List)v12).size()) ** GOTO lbl383
                var14_21 = (BookChapter)chapterList.get(var6_9);
                if (var12_18 == 0 || var7_10 == 1) ** GOTO lbl379
                $continuation.L$0 = this;
                $continuation.L$1 = var3_6;
                $continuation.L$2 = var10_13;
                $continuation.L$3 = var11_16;
                $continuation.L$4 = var13_20;
                $continuation.L$5 = var14_21;
                $continuation.L$6 = chapterList;
                $continuation.I$0 = var6_9;
                $continuation.I$1 = var8_11;
                $continuation.I$2 = var9_12;
                $continuation.label = 7;
                v13 = this.saveShelfBookProgress(var13_20, var14_21, var11_16, $continuation);
                if (v13 == var31_5) {
                    return var31_5;
                }
                ** GOTO lbl350
            }
            case 7: {
                var9_12 = $continuation.I$2;
                var8_11 = $continuation.I$1;
                var6_9 = $continuation.I$0;
                chapterList = (List)$continuation.L$6;
                var15_22 = null;
                var14_21 = (BookChapter)$continuation.L$5;
                var13_20 = (Book)$continuation.L$4;
                var11_16 = (String)$continuation.L$3;
                var10_13 = (String)$continuation.L$2;
                var3_6 = (ReturnData)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v13 = $result;
lbl350:
                // 2 sources

                $continuation.L$0 = this;
                $continuation.L$1 = var3_6;
                $continuation.L$2 = var10_13;
                $continuation.L$3 = var11_16;
                $continuation.L$4 = var13_20;
                $continuation.L$5 = var14_21;
                $continuation.L$6 = chapterList;
                $continuation.I$0 = var6_9;
                $continuation.I$1 = var8_11;
                $continuation.I$2 = var9_12;
                $continuation.label = 8;
                v14 = this.saveBookProgressToWebdav(var13_20, var14_21, var11_16, $continuation);
                if (v14 == var31_5) {
                    return var31_5;
                }
                ** GOTO lbl379
            }
            case 8: {
                var9_12 = $continuation.I$2;
                var8_11 = $continuation.I$1;
                var6_9 = $continuation.I$0;
                chapterList = (List)$continuation.L$6;
                var15_22 = null;
                var14_21 = (BookChapter)$continuation.L$5;
                var13_20 = (Book)$continuation.L$4;
                var11_16 = (String)$continuation.L$3;
                var10_13 = (String)$continuation.L$2;
                var3_6 = (ReturnData)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v14 = $result;
lbl379:
                // 3 sources

                var4_7 = var14_21.getUrl();
                if (var6_9 + 1 < chapterList.size()) {
                    nextChapterInfo = (BookChapter)chapterList.get(var6_9 + 1);
                    var15_22 = nextChapterInfo.getUrl();
                }
lbl383:
                // 6 sources

                if (var13_20 == null) {
                    return var3_6.setErrorMsg("\u83b7\u53d6\u4e66\u7c4d\u4fe1\u606f\u5931\u8d25");
                }
                if (!var13_20.isLocalBook()) {
                    cacheInfo = (CharSequence)var10_13;
                    chapterList = false;
                    nextChapterInfo = false;
                    if (cacheInfo == null || cacheInfo.length() == 0) {
                        return var3_6.setErrorMsg("\u672a\u914d\u7f6e\u4e66\u6e90");
                    }
                }
                if (var14_21 == null) ** GOTO lbl395
                cacheInfo = var4_7;
                chapterList = false;
                if (!(cacheInfo.length() == 0)) ** GOTO lbl396
lbl395:
                // 2 sources

                return var3_6.setErrorMsg("\u83b7\u53d6\u7ae0\u8282\u94fe\u63a5\u5931\u8d25");
lbl396:
                // 1 sources

                cacheInfo = null;
                var13_20.setRootDir(ExtKt.getWorkDir$default(null, 1, null));
                var13_20.setUserNameSpace(var11_16);
                if (!var13_20.isLocalBook()) ** GOTO lbl485
                localFile = var13_20.getLocalFile();
                if (!localFile.exists()) {
                    return var3_6.setErrorMsg("\u672c\u5730\u6e90\u4e66\u7c4d\u6587\u4ef6\u4e0d\u5b58\u5728");
                }
                if (var13_20.isEpub()) {
                    if (!BookController.extractEpub$default(this, var13_20, false, 2, null)) {
                        return var3_6.setErrorMsg("Epub\u4e66\u7c4d\u89e3\u538b\u5931\u8d25");
                    }
                    epubRootDir = var13_20.getEpubRootDir();
                    $i$f$toDataClass = new String[]{var13_20.getBookUrl(), "index", epubRootDir, var14_21.getUrl()};
                    chapterFilePath /* !! */  = ExtKt.getWorkDir((String[])$i$f$toDataClass);
                    BookControllerKt.access$getLogger$p().info("chapterFilePath: {} {}", (Object)chapterFilePath /* !! */ , (Object)epubRootDir);
                    if (!new File((String)chapterFilePath /* !! */ ).exists()) {
                        return var3_6.setErrorMsg("\u7ae0\u8282\u6587\u4ef6\u4e0d\u5b58\u5728");
                    }
                    $i$f$toDataClass = epubRootDir;
                    $this$convert$iv$iv = false;
                    if ($i$f$toDataClass.length() == 0) {
                        cacheInfo = StringsKt.replace$default(StringsKt.replace$default(var13_20.getBookUrl(), "\\", "/", false, 4, null), "storage/data/", "/book-assets/", false, 4, null) + "/index/" + var14_21.getUrl();
                    } else {
                        content = StringsKt.replace$default(StringsKt.replace$default(var13_20.getBookUrl(), "\\", "/", false, 4, null), "storage/data/", "/book-assets/", false, 4, null) + "/index/" + epubRootDir + '/' + var14_21.getUrl();
                    }
                    if (var9_12 > 0) {
                        $i$f$toDataClass = new Pair[]{TuplesKt.to("url", Intrinsics.stringPlus("__API_ROOT__", content)), TuplesKt.to("content", FilesKt.readText$default(new File((String)chapterFilePath /* !! */ ), null, 1, null))};
                        return ReturnData.setData$default(var3_6, MapsKt.mapOf($i$f$toDataClass), null, 2, null);
                    }
                    return ReturnData.setData$default(var3_6, content, null, 2, null);
                }
                if (var13_20.isCbz()) {
                    if (!BookController.extractCbz$default(this, var13_20, false, 2, null)) {
                        return var3_6.setErrorMsg("CBZ\u4e66\u7c4d\u89e3\u538b\u5931\u8d25");
                    }
                    chapterFilePath /* !! */  = new String[]{var13_20.getBookUrl(), "index", var14_21.getUrl()};
                    chapterFilePath = ExtKt.getWorkDir(chapterFilePath /* !! */ );
                    BookControllerKt.access$getLogger$p().info("chapterFilePath: {}", (Object)chapterFilePath);
                    chapterFile = new File(chapterFilePath);
                    if (!chapterFile.exists()) {
                        return var3_6.setErrorMsg("\u7ae0\u8282\u6587\u4ef6\u4e0d\u5b58\u5728");
                    }
                    v15 = this;
                    $this$convert$iv$iv = chapterFile.getName();
                    Intrinsics.checkNotNullExpressionValue($this$convert$iv$iv, "chapterFile.name");
                    $this$convert$iv$iv = BaseController.getFileExt$default(v15, (String)$this$convert$iv$iv, null, 2, null);
                    $i$f$convert = false;
                    v16 = $this$convert$iv$iv;
                    if (v16 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    v17 = v16.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(v17, "(this as java.lang.Strin\u2026.toLowerCase(Locale.ROOT)");
                    ext = v17;
                    $i$f$convert = new String[]{"jpg", "jpeg", "gif", "png", "bmp", "webp", "svg"};
                    imageExt = CollectionsKt.listOf($i$f$convert);
                    fileUrl = "__API_ROOT__" + StringsKt.replace$default(StringsKt.replace$default(var13_20.getBookUrl(), "\\", "/", false, 4, null), "storage/data/", "/book-assets/", false, 4, null) + "/index/" + var14_21.getUrl();
                    if (!imageExt.contains(ext)) {
                        return ReturnData.setData$default(var3_6, fileUrl, null, 2, null);
                    }
                    content = "<img src='" + fileUrl + "' />";
                    return ReturnData.setData$default(var3_6, content, null, 2, null);
                }
                if (var13_20.isPdf()) {
                    if (!BookController.convertPdfToImage$default(this, var13_20, false, 2, null)) {
                        return var3_6.setErrorMsg("PDF\u751f\u6210\u56fe\u7247\u5931\u8d25");
                    }
                    content = "";
                    if (var14_21.getStart() != null && var14_21.getEnd() != null) {
                        v18 = var14_21.getStart();
                        Intrinsics.checkNotNull(v18);
                        v19 = v18;
                        v20 = var14_21.getEnd();
                        Intrinsics.checkNotNull(v20);
                        if (v19 <= v20) {
                            v21 = var14_21.getStart();
                            Intrinsics.checkNotNull(v21);
                            chapterFilePath = v21;
                            v22 = var14_21.getEnd();
                            Intrinsics.checkNotNull(v22);
                            ext = v22;
                            if (chapterFilePath <= ext) {
                                do {
                                    i = chapterFilePath++;
                                    this.convertPdfPageToImage(var13_20, (int)i, var8_11 > 0);
                                    var25_58 = new String[]{var13_20.getBookUrl(), "index", "output-" + i + ".png"};
                                    chapterFilePath = ExtKt.getWorkDir(var25_58);
                                    BookControllerKt.access$getLogger$p().info("chapterFilePath: {}", (Object)chapterFilePath);
                                    chapterFile = new File(chapterFilePath);
                                    if (!chapterFile.exists()) {
                                        return var3_6.setErrorMsg("\u7ae0\u8282\u6587\u4ef6\u4e0d\u5b58\u5728");
                                    }
                                    fileUrl = "__API_ROOT__" + StringsKt.replace$default(StringsKt.replace$default(var13_20.getBookUrl(), "\\", "/", false, 4, null), "storage/data/", "/book-assets/", false, 4, null) + "/index/output-" + i + ".png";
                                    content = (String)content + "<img src='" + fileUrl + "' />";
                                } while (i != ext);
                            }
                        }
                    }
                    return ReturnData.setData$default(var3_6, content, null, 2, null);
                }
                chapterFile = LocalBook.INSTANCE.getContent(var13_20, var14_21);
                if (chapterFile == null) {
                    return var3_6.setErrorMsg("\u83b7\u53d6\u7ae0\u8282\u5185\u5bb9\u5931\u8d25");
                }
                content = bookContent = chapterFile;
                ** GOTO lbl568
lbl485:
                // 1 sources

                chapterCacheFile = null;
                if (var13_20.isInShelf() && this.getAppConfig().getCacheChapterContent()) {
                    localCacheDir = this.getChapterCacheDir(var13_20, var11_16);
                    chapterCacheFile = new File(localCacheDir.getAbsolutePath() + File.separator + var6_9 + ".txt");
                    if (var8_11 <= 0 && chapterCacheFile.exists()) {
                        content = FilesKt.readText$default(chapterCacheFile, null, 1, null);
                        if (StringsKt.indexOf$default((CharSequence)content, "<img", 0, false, 6, null) >= 0) {
                            content = this.updateImageLinkInContent(var13_20, var14_21, (String)content);
                        }
                        BookControllerKt.access$getLogger$p().info("\u4f7f\u7528\u7f13\u5b58\u7684\u7ae0\u8282\u5185\u5bb9: {}", (Object)chapterCacheFile.toString());
                        return ReturnData.setData$default(var3_6, content, null, 2, null);
                    }
                }
                localCacheDir = var10_13;
                $continuation.L$0 = this;
                $continuation.L$1 = var3_6;
                $continuation.L$2 = var10_13;
                $continuation.L$3 = var11_16;
                $continuation.L$4 = var13_20;
                $continuation.L$5 = var14_21;
                $continuation.L$6 = chapterCacheFile;
                $continuation.label = 9;
                v23 = new WebBook((String)(localCacheDir == null ? "" : localCacheDir), this.getAppConfig().getDebugLog(), null, var11_16, 4, null).getBookContent(var13_20, var14_21, var15_22, $continuation);
                ** if (v23 != var31_5) goto lbl509
lbl508:
                // 1 sources

                return var31_5;
lbl509:
                // 1 sources

                ** GOTO lbl522
            }
            case 9: {
                var17_27 = (File)$continuation.L$6;
                var14_21 = (BookChapter)$continuation.L$5;
                var13_20 = (Book)$continuation.L$4;
                var11_16 = (String)$continuation.L$3;
                var10_13 = (String)$continuation.L$2;
                var3_6 = (ReturnData)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v23 = $result;
lbl522:
                // 2 sources

                var16_23 = (String)v23;
                if (!this.getAppConfig().getCacheChapterContent() || var17_27 == null) ** GOTO lbl568
                FilesKt.writeText$default(var17_27, (String)var16_23, null, 2, null);
                chapterFile = var10_13;
                chapterFile = BookSource.Companion.fromJson-IoAF18A((String)(chapterFile == null ? "" : chapterFile));
                ext = false;
                localCacheDir = (BookSource)(Result.isFailure-impl(chapterFile) != false ? null : chapterFile);
                $continuation.L$0 = this;
                $continuation.L$1 = var3_6;
                $continuation.L$2 = var10_13;
                $continuation.L$3 = var11_16;
                $continuation.L$4 = var13_20;
                $continuation.L$5 = var14_21;
                $continuation.L$6 = var16_23;
                $continuation.label = 10;
                v24 = BookHelp.INSTANCE.saveImages(this, (BookSource)(localCacheDir == null ? new BookSource(null, null, null, 0, null, 0, false, false, null, null, null, null, null, null, null, null, 0L, 0L, 0, null, null, null, null, null, null, null, 0x3FFFFFF, null) : localCacheDir), var13_20, var14_21, (String)var16_23, $continuation);
                ** if (v24 != var31_5) goto lbl540
lbl539:
                // 1 sources

                return var31_5;
lbl540:
                // 1 sources

                ** GOTO lbl553
            }
            case 10: {
                var16_23 = (String)$continuation.L$6;
                var14_21 = (BookChapter)$continuation.L$5;
                var13_20 = (Book)$continuation.L$4;
                var11_16 = (String)$continuation.L$3;
                var10_13 = (String)$continuation.L$2;
                var3_6 = (ReturnData)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                try {
                    ResultKt.throwOnFailure($result);
                    v24 = $result;
lbl553:
                    // 2 sources

                    var16_23 = this.updateImageLinkInContent(var13_20, var14_21, (String)var16_23);
                }
                catch (Exception e) {
                    chapterFile = (CharSequence)var10_13;
                    ext = false;
                    var21_49 = false;
                    if (!(chapterFile == null || chapterFile.length() == 0)) {
                        ext = BookSource.Companion.fromJson-IoAF18A((String)var10_13);
                        var21_49 = false;
                        bookSourceObject = (BookSource)(Result.isFailure-impl(ext) != false ? null : ext);
                        if (bookSourceObject != null) {
                            var21_50 = new Pair[]{TuplesKt.to("sourceUrl", bookSourceObject.getBookSourceUrl()), TuplesKt.to("time", Boxing.boxLong(System.currentTimeMillis())), TuplesKt.to("error", e.toString())};
                            info = MapsKt.mutableMapOf(var21_50);
                            this.addInvalidBookSource(bookSourceObject.getBookSourceUrl(), info, var11_16);
                        }
                    }
                    throw e;
                }
lbl568:
                // 3 sources

                return ReturnData.setData$default(var3_6, var16_23, null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object saveBookContent(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof saveBookContent.1)) ** GOTO lbl-1000
        var15_3 = var2_2;
        if ((var15_3.label & -2147483648) != 0) {
            var15_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.saveBookContent(null, this);
                }
            };
        }
        $result = $continuation.result;
        var16_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var16_5) {
                    return var16_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                var5_7 = context.getBodyAsJson().getString("url");
                bookUrl = var5_7 == null ? "" : var5_7;
                chapterIndex = context.getBodyAsJson().getInteger("index", Boxing.boxInt(-1));
                var7_9 = context.getBodyAsJson().getString("content");
                content = var7_9 == null ? "" : var7_9;
                var7_9 = bookUrl;
                var8_11 = false;
                if (var7_9.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u4e66\u7c4d\u94fe\u63a5");
                }
                userNameSpace = this.getUserNameSpace(context);
                bookInfo = this.getShelfBookByURL(bookUrl, userNameSpace);
                if (bookInfo == null) {
                    return returnData.setErrorMsg("\u83b7\u53d6\u4e66\u7c4d\u4fe1\u606f\u5931\u8d25");
                }
                localCacheDir = this.getChapterCacheDir(bookInfo, userNameSpace);
                chapterCacheFile = new File(localCacheDir.getAbsolutePath() + File.separator + chapterIndex + ".txt");
                FilesKt.writeText$default(chapterCacheFile, content, null, 2, null);
                var12_15 = new String[]{"storage", "data", userNameSpace, bookInfo.getName() + '_' + bookInfo.getAuthor(), "custom"};
                customCacheDirPath = ExtKt.getWorkDir(var12_15);
                customCacheDir = new File(customCacheDirPath);
                if (!customCacheDir.exists()) {
                    customCacheDir.mkdirs();
                }
                cacheFile = new File(customCacheDir.getAbsolutePath() + File.separator + chapterIndex + ".txt");
                FilesKt.writeText$default(cacheFile, content, null, 2, null);
                return ReturnData.setData$default(returnData, "", null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    private final String updateImageLinkInContent(Book book, BookChapter chapter, String content) {
        StringBuilder data = new StringBuilder("");
        Object object = new String[]{"storage", "data"};
        String dataDir = ExtKt.getWorkDir(object);
        object = new String[]{"\n"};
        Iterable $this$forEach$iv = StringsKt.split$default((CharSequence)content, object, false, 0, 6, null);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String text = (String)element$iv;
            boolean bl = false;
            String text1 = null;
            text1 = text;
            Matcher matcher = AppPattern.INSTANCE.getImgPattern().matcher(text);
            while (matcher.find()) {
                String src;
                File imageFile;
                String string = matcher.group(1);
                if (string == null) continue;
                String string2 = string;
                boolean bl2 = false;
                boolean bl3 = false;
                String it = string2;
                boolean bl4 = false;
                if (StringsKt.indexOf$default((CharSequence)it, "__API_ROOT__", 0, false, 6, null) >= 0 || !(imageFile = BookHelp.INSTANCE.getImage(book, src = NetworkUtils.INSTANCE.getAbsoluteURL(chapter.getUrl(), it))).exists()) continue;
                String string3 = imageFile.getPath();
                Intrinsics.checkNotNullExpressionValue(string3, "imageFile.path");
                String imageUrl = Intrinsics.stringPlus("__API_ROOT__", StringsKt.replace$default(string3, dataDir, "/book-assets", false, 4, null));
                text1 = StringsKt.replace$default(text1, it, imageUrl + "\" data-error=\"" + it, false, 4, null);
            }
            data.append(text1).append("\n");
        }
        object = data.toString();
        Intrinsics.checkNotNullExpressionValue(object, "data.toString()");
        return object;
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object exploreBook(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof exploreBook.1)) ** GOTO lbl-1000
        var12_3 = var2_2;
        if ((var12_3.label & -2147483648) != 0) {
            var12_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.exploreBook(null, this);
                }
            };
        }
        $result = $continuation.result;
        var13_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var13_5) {
                    return var13_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 2;
                v1 = BookController.getBookSourceString$default(this, context, null, false, $continuation, 6, null);
                if (v1 == var13_5) {
                    return var13_5;
                }
                ** GOTO lbl41
            }
            case 2: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v1 = $result;
lbl41:
                // 2 sources

                bookSource = (String)v1;
                var5_8 = bookSource;
                var6_10 = false;
                var7_12 = false;
                if (var5_8 == null || var5_8.length() == 0) {
                    return returnData.setErrorMsg("\u672a\u914d\u7f6e\u4e66\u6e90");
                }
                var5_9 = 0;
                var6_11 = null;
                if (context.request().method() == HttpMethod.POST) {
                    var7_13 = context.getBodyAsJson().getString("ruleFindUrl");
                    Intrinsics.checkNotNullExpressionValue(var7_13, "context.bodyAsJson.getString(\"ruleFindUrl\")");
                    ruleFindUrl = var7_13;
                    var7_13 = context.getBodyAsJson().getInteger("page", Boxing.boxInt(1));
                    Intrinsics.checkNotNullExpressionValue(var7_13, "context.bodyAsJson.getInteger(\"page\", 1)");
                    var5_9 = ((Number)var7_13).intValue();
                } else {
                    var8_14 = context.queryParam("ruleFindUrl");
                    Intrinsics.checkNotNullExpressionValue(var8_14, "context.queryParam(\"ruleFindUrl\")");
                    var7_13 = CollectionsKt.firstOrNull(var8_14);
                    ruleFindUrl = var7_13 == null ? "" : var7_13;
                    var8_14 = context.queryParam("page");
                    Intrinsics.checkNotNullExpressionValue(var8_14, "context.queryParam(\"page\")");
                    var7_13 = CollectionsKt.firstOrNull(var8_14);
                    if (var7_13 == null) {
                        v2 = 1;
                    } else {
                        var9_15 = var7_13;
                        var10_16 = false;
                        var8_14 = Boxing.boxInt(Integer.parseInt((String)var9_15));
                        v2 = var8_14 == null ? 1 : var8_14.intValue();
                    }
                    page = v2;
                }
                userNameSpace = this.getUserNameSpace(context);
                $continuation.L$0 = returnData;
                $continuation.L$1 = null;
                $continuation.L$2 = null;
                $continuation.label = 3;
                v3 = new WebBook(bookSource, false, null, userNameSpace, 4, null).exploreBook(ruleFindUrl, Boxing.boxInt(page), $continuation);
                if (v3 == var13_5) {
                    return var13_5;
                }
                ** GOTO lbl85
            }
            case 3: {
                var3_6 = (ReturnData)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v3 = $result;
lbl85:
                // 2 sources

                result = (List)v3;
                return ReturnData.setData$default(var3_6, result, null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object searchBook(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof searchBook.1)) ** GOTO lbl-1000
        var12_3 = var2_2;
        if ((var12_3.label & -2147483648) != 0) {
            var12_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.searchBook(null, this);
                }
            };
        }
        $result = $continuation.result;
        var13_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var13_5) {
                    return var13_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 2;
                v1 = BookController.getBookSourceString$default(this, context, null, false, $continuation, 6, null);
                if (v1 == var13_5) {
                    return var13_5;
                }
                ** GOTO lbl41
            }
            case 2: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v1 = $result;
lbl41:
                // 2 sources

                bookSource = (String)v1;
                var5_8 = bookSource;
                var6_9 = 0;
                var7_10 = false;
                if (var5_8 == null || var5_8.length() == 0) {
                    return returnData.setErrorMsg("\u672a\u914d\u7f6e\u4e66\u6e90");
                }
                var5_8 = null;
                var6_9 = 0;
                if (context.request().method() == HttpMethod.POST) {
                    var7_11 = context.getBodyAsJson().getString("key");
                    Intrinsics.checkNotNullExpressionValue(var7_11, "context.bodyAsJson.getString(\"key\")");
                    key = var7_11;
                    var7_11 = context.getBodyAsJson().getInteger("page", Boxing.boxInt(1));
                    Intrinsics.checkNotNullExpressionValue(var7_11, "context.bodyAsJson.getInteger(\"page\", 1)");
                    var6_9 = ((Number)var7_11).intValue();
                } else {
                    var8_12 = context.queryParam("key");
                    Intrinsics.checkNotNullExpressionValue(var8_12, "context.queryParam(\"key\")");
                    var7_11 = CollectionsKt.firstOrNull(var8_12);
                    key = var7_11 == null ? "" : var7_11;
                    var8_12 = context.queryParam("page");
                    Intrinsics.checkNotNullExpressionValue(var8_12, "context.queryParam(\"page\")");
                    var7_11 = CollectionsKt.firstOrNull(var8_12);
                    if (var7_11 == null) {
                        v2 = 1;
                    } else {
                        var9_15 = var7_11;
                        var10_17 = false;
                        var8_12 = Boxing.boxInt(Integer.parseInt((String)var9_15));
                        v2 = var8_12 == null ? 1 : var8_12.intValue();
                    }
                    page = v2;
                }
                var7_11 = key;
                var8_13 = false;
                var9_16 = false;
                if (var7_11.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u641c\u7d22\u5173\u952e\u5b57");
                }
                userNameSpace = this.getUserNameSpace(context);
                $continuation.L$0 = returnData;
                $continuation.L$1 = null;
                $continuation.L$2 = null;
                $continuation.label = 3;
                v3 = new WebBook(bookSource, this.getAppConfig().getDebugLog(), null, userNameSpace, 4, null).searchBook(key, Boxing.boxInt(page), $continuation);
                if (v3 == var13_5) {
                    return var13_5;
                }
                ** GOTO lbl90
            }
            case 3: {
                var3_6 = (ReturnData)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v3 = $result;
lbl90:
                // 2 sources

                result = (List)v3;
                return ReturnData.setData$default(var3_6, result, null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object searchBookMulti(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof searchBookMulti.1)) ** GOTO lbl-1000
        var20_3 = var2_2;
        if ((var20_3.label & -2147483648) != 0) {
            var20_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.searchBookMulti(null, this);
                }
            };
        }
        $result = $continuation.result;
        var21_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var21_5) {
                    return var21_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                var4_7 = null;
                lastIndex = new Ref.IntRef();
                searchSize = new Ref.IntRef();
                bookSourceGroup = new Ref.ObjectRef<String>();
                var8_11 = 0;
                if (context.request().method() == HttpMethod.POST) {
                    var9_12 = context.getBodyAsJson().getString("key", "");
                    Intrinsics.checkNotNullExpressionValue(var9_12, "context.bodyAsJson.getString(\"key\", \"\")");
                    key = var9_12;
                    var9_12 = context.getBodyAsJson().getString("bookSourceGroup", "");
                    Intrinsics.checkNotNullExpressionValue(var9_12, "context.bodyAsJson.getString(\"bookSourceGroup\", \"\")");
                    bookSourceGroup.element = var9_12;
                    var9_12 = context.getBodyAsJson().getInteger("lastIndex", Boxing.boxInt(-1));
                    Intrinsics.checkNotNullExpressionValue(var9_12, "context.bodyAsJson.getInteger(\"lastIndex\", -1)");
                    lastIndex.element = ((Number)var9_12).intValue();
                    var9_12 = context.getBodyAsJson().getInteger("searchSize", Boxing.boxInt(20));
                    Intrinsics.checkNotNullExpressionValue(var9_12, "context.bodyAsJson.getInteger(\"searchSize\", 20)");
                    searchSize.element = ((Number)var9_12).intValue();
                    var9_12 = context.getBodyAsJson().getInteger("concurrentCount", Boxing.boxInt(36));
                    Intrinsics.checkNotNullExpressionValue(var9_12, "context.bodyAsJson.getInteger(\"concurrentCount\", 36)");
                    var8_11 = ((Number)var9_12).intValue();
                } else {
                    var10_13 = context.queryParam("key");
                    Intrinsics.checkNotNullExpressionValue(var10_13, "context.queryParam(\"key\")");
                    var9_12 = CollectionsKt.firstOrNull(var10_13);
                    key = var9_12 == null ? "" : var9_12;
                    var10_13 = context.queryParam("bookSourceGroup");
                    Intrinsics.checkNotNullExpressionValue(var10_13, "context.queryParam(\"bookSourceGroup\")");
                    var9_12 = CollectionsKt.firstOrNull(var10_13);
                    bookSourceGroup.element = var9_12 == null ? "" : var9_12;
                    var10_13 = context.queryParam("lastIndex");
                    Intrinsics.checkNotNullExpressionValue(var10_13, "context.queryParam(\"lastIndex\")");
                    var9_12 = CollectionsKt.firstOrNull(var10_13);
                    if (var9_12 == null) {
                        v1 = -1;
                    } else {
                        var11_14 = var9_12;
                        var12_15 = false;
                        var10_13 = Boxing.boxInt(Integer.parseInt((String)var11_14));
                        v1 = var10_13 == null ? -1 : var10_13.intValue();
                    }
                    lastIndex.element = v1;
                    var10_13 = context.queryParam("searchSize");
                    Intrinsics.checkNotNullExpressionValue(var10_13, "context.queryParam(\"searchSize\")");
                    var9_12 = (String)CollectionsKt.firstOrNull(var10_13);
                    if (var9_12 == null) {
                        v2 = 20;
                    } else {
                        var11_14 = var9_12;
                        var12_15 = false;
                        var10_13 = Boxing.boxInt(Integer.parseInt((String)var11_14));
                        v2 = var10_13 == null ? 20 : var10_13.intValue();
                    }
                    searchSize.element = v2;
                    var10_13 = context.queryParam("concurrentCount");
                    Intrinsics.checkNotNullExpressionValue(var10_13, "context.queryParam(\"concurrentCount\")");
                    var9_12 = (String)CollectionsKt.firstOrNull(var10_13);
                    if (var9_12 == null) {
                        v3 = 36;
                    } else {
                        var11_14 = var9_12;
                        var12_15 = false;
                        var10_13 = Boxing.boxInt(Integer.parseInt((String)var11_14));
                        v3 = var10_13 == null ? 36 : var10_13.intValue();
                    }
                    concurrentCount = v3;
                }
                userNameSpace = new Ref.ObjectRef<String>();
                userNameSpace.element = this.getUserNameSpace(context);
                urlMap = new BookSourceController(this.getCoroutineContext()).getBookSourceMap((String)userNameSpace.element);
                if (urlMap.size() <= 0) {
                    return returnData.setErrorMsg("\u672a\u914d\u7f6e\u4e66\u6e90");
                }
                var11_14 = key;
                var12_15 = false;
                var13_17 = false;
                if (var11_14.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u641c\u7d22\u5173\u952e\u5b57");
                }
                accurate = new Ref.BooleanRef();
                if (StringsKt.startsWith(key, "=", true)) {
                    accurate.element = true;
                    key = StringsKt.replaceFirst$default(key, "=", "", false, 4, null);
                }
                var12_16 = key;
                var13_17 = false;
                var14_19 = false;
                if (var12_16 == null || var12_16.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u641c\u7d22\u5173\u952e\u5b57");
                }
                if (lastIndex.element >= urlMap.size() - 1) {
                    return returnData.setErrorMsg("\u6ca1\u6709\u66f4\u591a\u4e86");
                }
                searchSize.element = searchSize.element > 0 ? searchSize.element : 20;
                concurrentCount = concurrentCount > 0 ? concurrentCount : 36;
                BookControllerKt.access$getLogger$p().info("searchBookMulti from lastIndex: {} searchSize: {}", (Object)Boxing.boxInt(lastIndex.element), (Object)Boxing.boxInt(searchSize.element));
                isEnd = new Ref.BooleanRef();
                context.request().connection().closeHandler((Handler<Void>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, searchBookMulti$lambda-5(kotlin.jvm.internal.Ref$BooleanRef com.htmake.reader.api.controller.BookController java.lang.Void ), (Ljava/lang/Void;)V)((Ref.BooleanRef)isEnd, (BookController)this));
                resultList = new Ref.ObjectRef<ArrayList<SearchBook>>();
                var14_19 = false;
                resultList.element = new ArrayList<E>();
                resultMap = new Ref.ObjectRef<Map<String, Integer>>();
                var15_21 = false;
                resultMap.element = new LinkedHashMap<K, V>();
                book = new Book(null, null, null, null, null, null, null, null, null, null, null, null, null, 0, 0L, null, 0L, 0L, 0, 0, null, 0, 0, 0L, null, false, 0, 0, false, null, null, false, null, -1, 1, null);
                book.setName(key);
                maxSize = new Ref.IntRef();
                maxSize.element = urlMap.size();
                bookSourceFile = new Ref.ObjectRef<File>();
                var18_25 = new String[]{"data", (String)userNameSpace.element, "bookSource"};
                bookSourceFile.element = ExtKt.getStorageFile$default((String[])var18_25, null, 2, null);
                if (!((File)bookSourceFile.element).exists()) {
                    var18_25 = new String[]{"data", "default", "bookSource"};
                    bookSourceFile.element = ExtKt.getStorageFile$default((String[])var18_25, null, 2, null);
                }
                $continuation.L$0 = returnData;
                $continuation.L$1 = lastIndex;
                $continuation.L$2 = resultList;
                $continuation.label = 2;
                v4 = this.limitConcurrent(concurrentCount, lastIndex.element + 1, urlMap.size(), (Function3<? super CoroutineScope, ? super Integer, ? super Continuation<Object>, ? extends Object>)new Function3<CoroutineScope, Integer, Continuation<? super Object>, Object>(maxSize, lastIndex, bookSourceFile, bookSourceGroup, this, book, accurate, userNameSpace, null){
                    int label;
                    /* synthetic */ int I$0;
                    final /* synthetic */ Ref.IntRef $maxSize;
                    final /* synthetic */ Ref.IntRef $lastIndex;
                    final /* synthetic */ Ref.ObjectRef<File> $bookSourceFile;
                    final /* synthetic */ Ref.ObjectRef<String> $bookSourceGroup;
                    final /* synthetic */ BookController this$0;
                    final /* synthetic */ Book $book;
                    final /* synthetic */ Ref.BooleanRef $accurate;
                    final /* synthetic */ Ref.ObjectRef<String> $userNameSpace;
                    {
                        this.$maxSize = $maxSize;
                        this.$lastIndex = $lastIndex;
                        this.$bookSourceFile = $bookSourceFile;
                        this.$bookSourceGroup = $bookSourceGroup;
                        this.this$0 = $receiver;
                        this.$book = $book;
                        this.$accurate = $accurate;
                        this.$userNameSpace = $userNameSpace;
                        super(3, $completion);
                    }

                    /*
                     * Unable to fully structure code
                     */
                    @Nullable
                    public final Object invokeSuspend(@NotNull Object var1_1) {
                        var6_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        switch (this.label) {
                            case 0: {
                                ResultKt.throwOnFailure(var1_1);
                                it = this.I$0;
                                if (it <= this.$maxSize.element) ** GOTO lbl10
                                var3_4 = false;
                                v0 = new ArrayList<E>();
                                ** GOTO lbl32
lbl10:
                                // 1 sources

                                var3_5 = this.$lastIndex.element;
                                var4_7 = false;
                                this.$lastIndex.element = Math.max(var3_5, it);
                                var4_8 = (CharSequence)this.$bookSourceGroup.element;
                                var5_10 = false;
                                bookSourceList = ExtKt.parseJsonStringList$default((File)this.$bookSourceFile.element, null, null, it, it, null, var4_8.length() == 0 != false ? null : (Function1)new Function1<ObjectNode, Boolean>(this.$bookSourceGroup){
                                    final /* synthetic */ Ref.ObjectRef<String> $bookSourceGroup;
                                    {
                                        this.$bookSourceGroup = $bookSourceGroup;
                                        super(1);
                                    }

                                    public final boolean invoke(@NotNull ObjectNode it) {
                                        Intrinsics.checkNotNullParameter(it, "it");
                                        String _bookSourceGroup = it.get("bookSourceGroup").asText();
                                        CharSequence charSequence = _bookSourceGroup;
                                        boolean bl = false;
                                        boolean bl2 = false;
                                        return !(charSequence == null || charSequence.length() == 0) && StringsKt.indexOf$default((CharSequence)Intrinsics.stringPlus(_bookSourceGroup, ","), Intrinsics.stringPlus((String)this.$bookSourceGroup.element, ","), 0, false, 6, null) >= 0;
                                    }
                                }, 38, null);
                                if (bookSourceList != null && !bookSourceList.isEmpty()) ** GOTO lbl21
                                this.$maxSize.element = it;
                                var4_9 = false;
                                v0 = new ArrayList<E>();
                                ** GOTO lbl32
lbl21:
                                // 1 sources

                                var4_8 = bookSourceList.getString(0);
                                Intrinsics.checkNotNullExpressionValue(var4_8, "bookSourceList.getString(0)");
                                this.label = 1;
                                v1 = this.this$0.searchBookWithSource((String)var4_8, this.$book, this.$accurate.element, (String)this.$userNameSpace.element, this);
                                if (v1 == var6_2) {
                                    return var6_2;
                                }
                                ** GOTO lbl31
                            }
                            case 1: {
                                ResultKt.throwOnFailure($result);
                                v1 = $result;
lbl31:
                                // 2 sources

                                v0 = (ArrayList)v1;
lbl32:
                                // 3 sources

                                return v0;
                            }
                        }
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }

                    @Nullable
                    public final Object invoke(@NotNull CoroutineScope p1, int p2, @Nullable Continuation<Object> p3) {
                        Function3<CoroutineScope, Integer, Continuation<? super Object>, Object> function3 = new /* invalid duplicate definition of identical inner class */;
                        function3.I$0 = p2;
                        return function3.invokeSuspend(Unit.INSTANCE);
                    }
                }, (Function2<? super ArrayList<Object>, ? super Integer, Boolean>)new Function2<ArrayList<Object>, Integer, Boolean>(resultList, isEnd, this, searchSize, resultMap){
                    final /* synthetic */ Ref.ObjectRef<ArrayList<SearchBook>> $resultList;
                    final /* synthetic */ Ref.BooleanRef $isEnd;
                    final /* synthetic */ BookController this$0;
                    final /* synthetic */ Ref.IntRef $searchSize;
                    final /* synthetic */ Ref.ObjectRef<Map<String, Integer>> $resultMap;
                    {
                        this.$resultList = $resultList;
                        this.$isEnd = $isEnd;
                        this.this$0 = $receiver;
                        this.$searchSize = $searchSize;
                        this.$resultMap = $resultMap;
                        super(2);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    public final boolean invoke(@NotNull ArrayList<Object> list2, int loopCount) {
                        void $this$forEach$iv;
                        Intrinsics.checkNotNullParameter(list2, "list");
                        Iterable iterable = list2;
                        Ref.ObjectRef<Map<String, Integer>> objectRef = this.$resultMap;
                        Ref.ObjectRef<ArrayList<SearchBook>> objectRef2 = this.$resultList;
                        boolean $i$f$forEach = false;
                        Iterator<T> iterator2 = $this$forEach$iv.iterator();
                        while (iterator2.hasNext()) {
                            T element$iv;
                            T it = element$iv = iterator2.next();
                            boolean bl = false;
                            Collection bookList = it instanceof Collection ? (Collection)it : null;
                            Collection collection = bookList;
                            if (collection == null) continue;
                            Iterable $this$forEach$iv2 = collection;
                            boolean $i$f$forEach2 = false;
                            for (T element$iv2 : $this$forEach$iv2) {
                                SearchBook book = (SearchBook)element$iv2;
                                boolean bl2 = false;
                                String bookKey = book.getName() + '_' + book.getAuthor();
                                if (((Map)objectRef.element).containsKey(bookKey)) continue;
                                ((ArrayList)objectRef2.element).add(book);
                                ((Map)objectRef.element).put(bookKey, 1);
                            }
                        }
                        BookControllerKt.access$getLogger$p().info("Loop: {} resultList.size: {}", (Object)loopCount, (Object)((ArrayList)this.$resultList.element).size());
                        return this.$isEnd.element || loopCount >= BookController.access$getConcurrentLoopCount$p(this.this$0) ? false : ((ArrayList)this.$resultList.element).size() < this.$searchSize.element;
                    }
                }, $continuation);
                if (v4 == var21_5) {
                    return var21_5;
                }
                ** GOTO lbl149
            }
            case 2: {
                var13_18 = (Ref.ObjectRef)$continuation.L$2;
                var5_8 = (Ref.IntRef)$continuation.L$1;
                var3_6 = (ReturnData)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v4 = $result;
lbl149:
                // 2 sources

                var18_25 = new Pair[]{TuplesKt.to("lastIndex", Boxing.boxInt(var5_8.element)), TuplesKt.to("list", var13_18.element)};
                return ReturnData.setData$default(var3_6, MapsKt.mapOf(var18_25), null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object searchBookMultiSSE(@NotNull RoutingContext var1_1, @NotNull Continuation<? super Unit> var2_2) {
        if (!(var2_2 instanceof searchBookMultiSSE.1)) ** GOTO lbl-1000
        var20_3 = var2_2;
        if ((var20_3.label & -2147483648) != 0) {
            var20_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.searchBookMultiSSE(null, this);
                }
            };
        }
        $result = $continuation.result;
        var21_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                response = context.response().putHeader("Content-Type", "text/event-stream").putHeader("Cache-Control", "no-cache").setChunked(true);
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.L$3 = response;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var21_5) {
                    return var21_5;
                }
                ** GOTO lbl30
            }
            case 1: {
                response = (HttpServerResponse)$continuation.L$3;
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl30:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    response.write("event: error\n");
                    response.end("data: " + ExtKt.jsonEncode(ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528"), false) + "\n\n");
                    return Unit.INSTANCE;
                }
                var5_8 = null;
                lastIndex = new Ref.IntRef();
                searchSize = new Ref.IntRef();
                bookSourceGroup = new Ref.ObjectRef<String>();
                var9_12 = 0;
                if (context.request().method() == HttpMethod.POST) {
                    var10_13 = context.getBodyAsJson().getString("key", "");
                    Intrinsics.checkNotNullExpressionValue(var10_13, "context.bodyAsJson.getString(\"key\", \"\")");
                    key = var10_13;
                    var10_13 = context.getBodyAsJson().getString("bookSourceGroup", "");
                    Intrinsics.checkNotNullExpressionValue(var10_13, "context.bodyAsJson.getString(\"bookSourceGroup\", \"\")");
                    bookSourceGroup.element = var10_13;
                    var10_13 = context.getBodyAsJson().getInteger("lastIndex", Boxing.boxInt(-1));
                    Intrinsics.checkNotNullExpressionValue(var10_13, "context.bodyAsJson.getInteger(\"lastIndex\", -1)");
                    lastIndex.element = ((Number)var10_13).intValue();
                    var10_13 = context.getBodyAsJson().getInteger("searchSize", Boxing.boxInt(50));
                    Intrinsics.checkNotNullExpressionValue(var10_13, "context.bodyAsJson.getInteger(\"searchSize\", 50)");
                    searchSize.element = ((Number)var10_13).intValue();
                    var10_13 = context.getBodyAsJson().getInteger("concurrentCount", Boxing.boxInt(24));
                    Intrinsics.checkNotNullExpressionValue(var10_13, "context.bodyAsJson.getInteger(\"concurrentCount\", 24)");
                    var9_12 = ((Number)var10_13).intValue();
                } else {
                    var11_14 = context.queryParam("key");
                    Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"key\")");
                    var10_13 = CollectionsKt.firstOrNull(var11_14);
                    key = var10_13 == null ? "" : var10_13;
                    var11_14 = context.queryParam("bookSourceGroup");
                    Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"bookSourceGroup\")");
                    var10_13 = CollectionsKt.firstOrNull(var11_14);
                    bookSourceGroup.element = var10_13 == null ? "" : var10_13;
                    var11_14 = context.queryParam("lastIndex");
                    Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"lastIndex\")");
                    var10_13 = CollectionsKt.firstOrNull(var11_14);
                    if (var10_13 == null) {
                        v1 = -1;
                    } else {
                        var12_15 = var10_13;
                        var13_16 = false;
                        var11_14 = Boxing.boxInt(Integer.parseInt((String)var12_15));
                        v1 = var11_14 == null ? -1 : var11_14.intValue();
                    }
                    lastIndex.element = v1;
                    var11_14 = context.queryParam("searchSize");
                    Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"searchSize\")");
                    var10_13 = (String)CollectionsKt.firstOrNull(var11_14);
                    if (var10_13 == null) {
                        v2 = 50;
                    } else {
                        var12_15 = var10_13;
                        var13_16 = false;
                        var11_14 = Boxing.boxInt(Integer.parseInt((String)var12_15));
                        v2 = var11_14 == null ? 50 : var11_14.intValue();
                    }
                    searchSize.element = v2;
                    var11_14 = context.queryParam("concurrentCount");
                    Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"concurrentCount\")");
                    var10_13 = (String)CollectionsKt.firstOrNull(var11_14);
                    if (var10_13 == null) {
                        v3 = 24;
                    } else {
                        var12_15 = var10_13;
                        var13_16 = false;
                        var11_14 = Boxing.boxInt(Integer.parseInt((String)var12_15));
                        v3 = var11_14 == null ? 24 : var11_14.intValue();
                    }
                    concurrentCount = v3;
                }
                userNameSpace = new Ref.ObjectRef<String>();
                userNameSpace.element = this.getUserNameSpace(context);
                urlMap = new BookSourceController(this.getCoroutineContext()).getBookSourceMap((String)userNameSpace.element);
                if (urlMap.size() <= 0) {
                    response.write("event: error\n");
                    response.end("data: " + ExtKt.jsonEncode(returnData.setErrorMsg("\u672a\u914d\u7f6e\u4e66\u6e90"), false) + "\n\n");
                    return Unit.INSTANCE;
                }
                var12_15 = key;
                var13_16 = false;
                var14_18 = false;
                if (var12_15.length() == 0) {
                    response.write("event: error\n");
                    response.end("data: " + ExtKt.jsonEncode(returnData.setErrorMsg("\u8bf7\u8f93\u5165\u641c\u7d22\u5173\u952e\u5b57"), false) + "\n\n");
                    return Unit.INSTANCE;
                }
                accurate = new Ref.BooleanRef();
                if (StringsKt.startsWith(key, "=", true)) {
                    accurate.element = true;
                    key = StringsKt.replaceFirst$default(key, "=", "", false, 4, null);
                }
                var13_17 = (Object[])key;
                var14_18 = false;
                var15_20 = false;
                if (var13_17 == null || var13_17.length() == 0) {
                    response.write("event: error\n");
                    response.end("data: " + ExtKt.jsonEncode(returnData.setErrorMsg("\u8bf7\u8f93\u5165\u641c\u7d22\u5173\u952e\u5b57"), false) + "\n\n");
                    return Unit.INSTANCE;
                }
                if (lastIndex.element >= urlMap.size() - 1) {
                    response.write("event: error\n");
                    response.end("data: " + ExtKt.jsonEncode(returnData.setErrorMsg("\u6ca1\u6709\u66f4\u591a\u4e86"), false) + "\n\n");
                    return Unit.INSTANCE;
                }
                searchSize.element = searchSize.element > 0 ? searchSize.element : 50;
                concurrentCount = concurrentCount > 0 ? concurrentCount : 24;
                var13_17 = new Object[]{Boxing.boxInt(lastIndex.element), Boxing.boxInt(concurrentCount), Boxing.boxInt(searchSize.element)};
                BookControllerKt.access$getLogger$p().info("searchBookMulti from lastIndex: {} concurrentCount: {} searchSize: {}", var13_17);
                isEnd = new Ref.BooleanRef();
                context.request().connection().closeHandler((Handler<Void>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, searchBookMultiSSE$lambda-6(kotlin.jvm.internal.Ref$BooleanRef com.htmake.reader.api.controller.BookController java.lang.Void ), (Ljava/lang/Void;)V)((Ref.BooleanRef)isEnd, (BookController)this));
                resultList = new Ref.ObjectRef<ArrayList<SearchBook>>();
                var15_20 = false;
                resultList.element = new ArrayList<E>();
                book = new Book(null, null, null, null, null, null, null, null, null, null, null, null, null, 0, 0L, null, 0L, 0L, 0, 0, null, 0, 0, 0L, null, false, 0, 0, false, null, null, false, null, -1, 1, null);
                book.setName(key);
                maxSize = new Ref.IntRef();
                maxSize.element = urlMap.size();
                bookSourceFile = new Ref.ObjectRef<File>();
                var18_24 = new String[]{"data", (String)userNameSpace.element, "bookSource"};
                bookSourceFile.element = ExtKt.getStorageFile$default((String[])var18_24, null, 2, null);
                if (!((File)bookSourceFile.element).exists()) {
                    var18_24 = new String[]{"data", "default", "bookSource"};
                    bookSourceFile.element = ExtKt.getStorageFile$default((String[])var18_24, null, 2, null);
                }
                $continuation.L$0 = response;
                $continuation.L$1 = lastIndex;
                $continuation.L$2 = maxSize;
                $continuation.L$3 = null;
                $continuation.label = 2;
                v4 = this.limitConcurrent(concurrentCount, lastIndex.element + 1, urlMap.size(), (Function3<? super CoroutineScope, ? super Integer, ? super Continuation<Object>, ? extends Object>)new Function3<CoroutineScope, Integer, Continuation<? super Object>, Object>(maxSize, lastIndex, bookSourceFile, bookSourceGroup, this, book, accurate, userNameSpace, null){
                    int label;
                    /* synthetic */ int I$0;
                    final /* synthetic */ Ref.IntRef $maxSize;
                    final /* synthetic */ Ref.IntRef $lastIndex;
                    final /* synthetic */ Ref.ObjectRef<File> $bookSourceFile;
                    final /* synthetic */ Ref.ObjectRef<String> $bookSourceGroup;
                    final /* synthetic */ BookController this$0;
                    final /* synthetic */ Book $book;
                    final /* synthetic */ Ref.BooleanRef $accurate;
                    final /* synthetic */ Ref.ObjectRef<String> $userNameSpace;
                    {
                        this.$maxSize = $maxSize;
                        this.$lastIndex = $lastIndex;
                        this.$bookSourceFile = $bookSourceFile;
                        this.$bookSourceGroup = $bookSourceGroup;
                        this.this$0 = $receiver;
                        this.$book = $book;
                        this.$accurate = $accurate;
                        this.$userNameSpace = $userNameSpace;
                        super(3, $completion);
                    }

                    /*
                     * Unable to fully structure code
                     */
                    @Nullable
                    public final Object invokeSuspend(@NotNull Object var1_1) {
                        var6_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        switch (this.label) {
                            case 0: {
                                ResultKt.throwOnFailure(var1_1);
                                it = this.I$0;
                                if (it <= this.$maxSize.element) ** GOTO lbl10
                                var3_4 = false;
                                v0 = new ArrayList<E>();
                                ** GOTO lbl32
lbl10:
                                // 1 sources

                                var3_5 = this.$lastIndex.element;
                                var4_7 = false;
                                this.$lastIndex.element = Math.max(var3_5, it);
                                var4_8 = (CharSequence)this.$bookSourceGroup.element;
                                var5_10 = false;
                                bookSourceList = ExtKt.parseJsonStringList$default((File)this.$bookSourceFile.element, null, null, it, it, null, var4_8.length() == 0 != false ? null : (Function1)new Function1<ObjectNode, Boolean>(this.$bookSourceGroup){
                                    final /* synthetic */ Ref.ObjectRef<String> $bookSourceGroup;
                                    {
                                        this.$bookSourceGroup = $bookSourceGroup;
                                        super(1);
                                    }

                                    public final boolean invoke(@NotNull ObjectNode it) {
                                        Intrinsics.checkNotNullParameter(it, "it");
                                        String _bookSourceGroup = it.get("bookSourceGroup").asText();
                                        CharSequence charSequence = _bookSourceGroup;
                                        boolean bl = false;
                                        boolean bl2 = false;
                                        return !(charSequence == null || charSequence.length() == 0) && StringsKt.indexOf$default((CharSequence)Intrinsics.stringPlus(_bookSourceGroup, ","), Intrinsics.stringPlus((String)this.$bookSourceGroup.element, ","), 0, false, 6, null) >= 0;
                                    }
                                }, 38, null);
                                if (bookSourceList != null && !bookSourceList.isEmpty()) ** GOTO lbl21
                                this.$maxSize.element = it;
                                var4_9 = false;
                                v0 = new ArrayList<E>();
                                ** GOTO lbl32
lbl21:
                                // 1 sources

                                var4_8 = bookSourceList.getString(0);
                                Intrinsics.checkNotNullExpressionValue(var4_8, "bookSourceList.getString(0)");
                                this.label = 1;
                                v1 = this.this$0.searchBookWithSource((String)var4_8, this.$book, this.$accurate.element, (String)this.$userNameSpace.element, this);
                                if (v1 == var6_2) {
                                    return var6_2;
                                }
                                ** GOTO lbl31
                            }
                            case 1: {
                                ResultKt.throwOnFailure($result);
                                v1 = $result;
lbl31:
                                // 2 sources

                                v0 = (ArrayList)v1;
lbl32:
                                // 3 sources

                                return v0;
                            }
                        }
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }

                    @Nullable
                    public final Object invoke(@NotNull CoroutineScope p1, int p2, @Nullable Continuation<Object> p3) {
                        Function3<CoroutineScope, Integer, Continuation<? super Object>, Object> function3 = new /* invalid duplicate definition of identical inner class */;
                        function3.I$0 = p2;
                        return function3.invokeSuspend(Unit.INSTANCE);
                    }
                }, (Function2<? super ArrayList<Object>, ? super Integer, Boolean>)new Function2<ArrayList<Object>, Integer, Boolean>(response, lastIndex, resultList, isEnd, this, searchSize){
                    final /* synthetic */ HttpServerResponse $response;
                    final /* synthetic */ Ref.IntRef $lastIndex;
                    final /* synthetic */ Ref.ObjectRef<ArrayList<SearchBook>> $resultList;
                    final /* synthetic */ Ref.BooleanRef $isEnd;
                    final /* synthetic */ BookController this$0;
                    final /* synthetic */ Ref.IntRef $searchSize;
                    {
                        this.$response = $response;
                        this.$lastIndex = $lastIndex;
                        this.$resultList = $resultList;
                        this.$isEnd = $isEnd;
                        this.this$0 = $receiver;
                        this.$searchSize = $searchSize;
                        super(2);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    public final boolean invoke(@NotNull ArrayList<Object> list2, int loopCount) {
                        void $this$forEach$iv;
                        Intrinsics.checkNotNullParameter(list2, "list");
                        boolean bl = false;
                        ArrayList<SearchBook> loopResult = new ArrayList<SearchBook>();
                        Pair[] pairArray = (Pair[])list2;
                        Ref.ObjectRef<ArrayList<SearchBook>> objectRef = this.$resultList;
                        boolean $i$f$forEach = false;
                        Iterator<T> iterator2 = $this$forEach$iv.iterator();
                        while (iterator2.hasNext()) {
                            T element$iv;
                            T it = element$iv = iterator2.next();
                            boolean bl2 = false;
                            Collection bookList = it instanceof Collection ? (Collection)it : null;
                            Collection collection = bookList;
                            if (collection == null) continue;
                            Iterable $this$forEach$iv2 = collection;
                            boolean $i$f$forEach2 = false;
                            for (T element$iv2 : $this$forEach$iv2) {
                                SearchBook book = (SearchBook)element$iv2;
                                boolean bl3 = false;
                                String bookKey = book.getName() + '_' + book.getAuthor();
                                ((ArrayList)objectRef.element).add(book);
                                loopResult.add(book);
                            }
                        }
                        pairArray = new Pair[]{TuplesKt.to("lastIndex", this.$lastIndex.element), TuplesKt.to("data", loopResult)};
                        this.$response.write("data: " + ExtKt.jsonEncode(MapsKt.mapOf(pairArray), false) + "\n\n");
                        BookControllerKt.access$getLogger$p().info("Loop: {} resultList.size: {}", (Object)loopCount, (Object)((ArrayList)this.$resultList.element).size());
                        return this.$isEnd.element || loopCount >= BookController.access$getConcurrentLoopCount$p(this.this$0) ? false : ((ArrayList)this.$resultList.element).size() < this.$searchSize.element;
                    }
                }, $continuation);
                if (v4 == var21_5) {
                    return var21_5;
                }
                ** GOTO lbl166
            }
            case 2: {
                maxSize = (Ref.IntRef)$continuation.L$2;
                var6_9 = (Ref.IntRef)$continuation.L$1;
                var4_7 = (HttpServerResponse)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v4 = $result;
lbl166:
                // 2 sources

                var4_7.write("event: end\n");
                var18_24 = new Pair[]{TuplesKt.to("lastIndex", Boxing.boxInt(var6_9.element)), TuplesKt.to("isEnd", Boxing.boxBoolean(var6_9.element >= maxSize.element))};
                var4_7.end("data: " + ExtKt.jsonEncode(MapsKt.mapOf(var18_24), false) + "\n\n");
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object searchBookSource(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof searchBookSource.1)) ** GOTO lbl-1000
        var19_3 = var2_2;
        if ((var19_3.label & -2147483648) != 0) {
            var19_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                Object L$5;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.searchBookSource(null, this);
                }
            };
        }
        $result = $continuation.result;
        var20_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var20_5) {
                    return var20_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                var4_7 = null;
                lastIndex = new Ref.IntRef();
                searchSize = new Ref.IntRef();
                bookSourceGroup = new Ref.ObjectRef<String>();
                if (context.request().method() == HttpMethod.POST) {
                    var8_11 = context.getBodyAsJson().getString("url");
                    Intrinsics.checkNotNullExpressionValue(var8_11, "context.bodyAsJson.getString(\"url\")");
                    bookUrl = var8_11;
                    var8_11 = context.getBodyAsJson().getInteger("lastIndex", Boxing.boxInt(-1));
                    Intrinsics.checkNotNullExpressionValue(var8_11, "context.bodyAsJson.getInteger(\"lastIndex\", -1)");
                    lastIndex.element = ((Number)var8_11).intValue();
                    var8_11 = context.getBodyAsJson().getInteger("searchSize", Boxing.boxInt(5));
                    Intrinsics.checkNotNullExpressionValue(var8_11, "context.bodyAsJson.getInteger(\"searchSize\", 5)");
                    searchSize.element = ((Number)var8_11).intValue();
                    var8_11 = context.getBodyAsJson().getString("bookSourceGroup", "");
                    Intrinsics.checkNotNullExpressionValue(var8_11, "context.bodyAsJson.getString(\"bookSourceGroup\", \"\")");
                    bookSourceGroup.element = var8_11;
                } else {
                    var9_12 = context.queryParam("url");
                    Intrinsics.checkNotNullExpressionValue(var9_12, "context.queryParam(\"url\")");
                    var8_11 = CollectionsKt.firstOrNull(var9_12);
                    bookUrl = var8_11 == null ? "" : var8_11;
                    var9_12 = context.queryParam("lastIndex");
                    Intrinsics.checkNotNullExpressionValue(var9_12, "context.queryParam(\"lastIndex\")");
                    var8_11 = CollectionsKt.firstOrNull(var9_12);
                    if (var8_11 == null) {
                        v1 = -1;
                    } else {
                        var10_13 = var8_11;
                        var11_14 = false;
                        var9_12 = Boxing.boxInt(Integer.parseInt((String)var10_13));
                        v1 = var9_12 == null ? -1 : var9_12.intValue();
                    }
                    lastIndex.element = v1;
                    var9_12 = context.queryParam("searchSize");
                    Intrinsics.checkNotNullExpressionValue(var9_12, "context.queryParam(\"searchSize\")");
                    var8_11 = (String)CollectionsKt.firstOrNull(var9_12);
                    if (var8_11 == null) {
                        v2 = 5;
                    } else {
                        var10_13 = var8_11;
                        var11_14 = false;
                        var9_12 = Boxing.boxInt(Integer.parseInt((String)var10_13));
                        v2 = var9_12 == null ? 5 : var9_12.intValue();
                    }
                    searchSize.element = v2;
                    var9_12 = context.queryParam("bookSourceGroup");
                    Intrinsics.checkNotNullExpressionValue(var9_12, "context.queryParam(\"bookSourceGroup\")");
                    var8_11 = (String)CollectionsKt.firstOrNull(var9_12);
                    bookSourceGroup.element = var8_11 == null ? "" : var8_11;
                }
                userNameSpace = new Ref.ObjectRef<String>();
                userNameSpace.element = this.getUserNameSpace(context);
                urlMap = new BookSourceController(this.getCoroutineContext()).getBookSourceMap((String)userNameSpace.element);
                if (urlMap.size() <= 0) {
                    return returnData.setErrorMsg("\u672a\u914d\u7f6e\u4e66\u6e90");
                }
                var10_13 = (CharSequence)bookUrl;
                var11_14 = false;
                var12_17 = false;
                if (var10_13.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u4e66\u7c4d\u94fe\u63a5");
                }
                if (lastIndex.element >= urlMap.size() - 1) {
                    return returnData.setErrorMsg("\u6ca1\u6709\u66f4\u591a\u4e86");
                }
                book = new Ref.ObjectRef<Book>();
                book.element = this.getShelfBookByURL((String)bookUrl, (String)userNameSpace.element);
                if (book.element == null) {
                    var11_15 = this.bookInfoCache.getAsString((String)bookUrl);
                    if (var11_15 == null) {
                        v3 /* !! */  = null;
                    } else {
                        var12_18 = ExtKt.toMap(var11_15);
                        if (var12_18 == null) {
                            v3 /* !! */  = null;
                        } else {
                            $this$toDataClass$iv = var12_18;
                            $i$f$toDataClass = false;
                            $this$convert$iv$iv = $this$toDataClass$iv;
                            $i$f$convert = false;
                            json$iv$iv = $this$convert$iv$iv instanceof String != false ? (String)$this$convert$iv$iv : ExtKt.getGson().toJson($this$convert$iv$iv);
                            v3 /* !! */  = book.element = (Book)ExtKt.getGson().fromJson(json$iv$iv, new TypeToken<Book>(){}.getType());
                        }
                    }
                }
                if (book.element == null) {
                    return returnData.setErrorMsg("\u4e66\u7c4d\u4fe1\u606f\u9519\u8bef");
                }
                BookControllerKt.access$getLogger$p().info("searchBookSource from lastIndex: {}", (Object)Boxing.boxInt(lastIndex.element));
                isEnd = new Ref.BooleanRef();
                context.request().connection().closeHandler((Handler<Void>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, searchBookSource$lambda-7(kotlin.jvm.internal.Ref$BooleanRef com.htmake.reader.api.controller.BookController java.lang.Void ), (Ljava/lang/Void;)V)((Ref.BooleanRef)isEnd, (BookController)this));
                searchSize.element = searchSize.element > 0 ? searchSize.element : 5;
                resultList = new Ref.ObjectRef<ArrayList<SearchBook>>();
                $this$toDataClass$iv = false;
                resultList.element = new ArrayList<E>();
                concurrentCount = Math.max(searchSize.element * 2, 24);
                maxSize = new Ref.IntRef();
                maxSize.element = urlMap.size();
                bookSourceFile = new Ref.ObjectRef<File>();
                var16_26 = new String[]{"data", (String)userNameSpace.element, "bookSource"};
                bookSourceFile.element = ExtKt.getStorageFile$default((String[])var16_26, null, 2, null);
                if (!((File)bookSourceFile.element).exists()) {
                    var16_26 = new String[]{"data", "default", "bookSource"};
                    bookSourceFile.element = ExtKt.getStorageFile$default((String[])var16_26, null, 2, null);
                }
                $continuation.L$0 = this;
                $continuation.L$1 = returnData;
                $continuation.L$2 = lastIndex;
                $continuation.L$3 = userNameSpace;
                $continuation.L$4 = book;
                $continuation.L$5 = resultList;
                $continuation.label = 2;
                v4 = this.limitConcurrent(concurrentCount, lastIndex.element + 1, urlMap.size(), (Function3<? super CoroutineScope, ? super Integer, ? super Continuation<Object>, ? extends Object>)new Function3<CoroutineScope, Integer, Continuation<? super Object>, Object>(maxSize, lastIndex, bookSourceFile, bookSourceGroup, this, book, userNameSpace, null){
                    int label;
                    /* synthetic */ int I$0;
                    final /* synthetic */ Ref.IntRef $maxSize;
                    final /* synthetic */ Ref.IntRef $lastIndex;
                    final /* synthetic */ Ref.ObjectRef<File> $bookSourceFile;
                    final /* synthetic */ Ref.ObjectRef<String> $bookSourceGroup;
                    final /* synthetic */ BookController this$0;
                    final /* synthetic */ Ref.ObjectRef<Book> $book;
                    final /* synthetic */ Ref.ObjectRef<String> $userNameSpace;
                    {
                        this.$maxSize = $maxSize;
                        this.$lastIndex = $lastIndex;
                        this.$bookSourceFile = $bookSourceFile;
                        this.$bookSourceGroup = $bookSourceGroup;
                        this.this$0 = $receiver;
                        this.$book = $book;
                        this.$userNameSpace = $userNameSpace;
                        super(3, $completion);
                    }

                    /*
                     * Unable to fully structure code
                     */
                    @Nullable
                    public final Object invokeSuspend(@NotNull Object var1_1) {
                        var6_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        switch (this.label) {
                            case 0: {
                                ResultKt.throwOnFailure(var1_1);
                                it = this.I$0;
                                if (it <= this.$maxSize.element) ** GOTO lbl10
                                var3_4 = false;
                                v0 = new ArrayList<E>();
                                ** GOTO lbl32
lbl10:
                                // 1 sources

                                var3_5 = this.$lastIndex.element;
                                var4_7 = false;
                                this.$lastIndex.element = Math.max(var3_5, it);
                                var4_8 = (CharSequence)this.$bookSourceGroup.element;
                                var5_10 = false;
                                bookSourceList = ExtKt.parseJsonStringList$default((File)this.$bookSourceFile.element, null, null, it, it, null, var4_8.length() == 0 != false ? null : (Function1)new Function1<ObjectNode, Boolean>(this.$bookSourceGroup){
                                    final /* synthetic */ Ref.ObjectRef<String> $bookSourceGroup;
                                    {
                                        this.$bookSourceGroup = $bookSourceGroup;
                                        super(1);
                                    }

                                    public final boolean invoke(@NotNull ObjectNode it) {
                                        Intrinsics.checkNotNullParameter(it, "it");
                                        String _bookSourceGroup = it.get("bookSourceGroup").asText();
                                        CharSequence charSequence = _bookSourceGroup;
                                        boolean bl = false;
                                        boolean bl2 = false;
                                        return !(charSequence == null || charSequence.length() == 0) && StringsKt.indexOf$default((CharSequence)Intrinsics.stringPlus(_bookSourceGroup, ","), Intrinsics.stringPlus((String)this.$bookSourceGroup.element, ","), 0, false, 6, null) >= 0;
                                    }
                                }, 38, null);
                                if (bookSourceList != null && !bookSourceList.isEmpty()) ** GOTO lbl21
                                this.$maxSize.element = it;
                                var4_9 = false;
                                v0 = new ArrayList<E>();
                                ** GOTO lbl32
lbl21:
                                // 1 sources

                                var4_8 = bookSourceList.getString(0);
                                Intrinsics.checkNotNullExpressionValue(var4_8, "bookSourceList.getString(0)");
                                this.label = 1;
                                v1 = BookController.searchBookWithSource$default(this.this$0, (String)var4_8, (Book)this.$book.element, false, (String)this.$userNameSpace.element, this, 4, null);
                                if (v1 == var6_2) {
                                    return var6_2;
                                }
                                ** GOTO lbl31
                            }
                            case 1: {
                                ResultKt.throwOnFailure($result);
                                v1 = $result;
lbl31:
                                // 2 sources

                                v0 = (ArrayList)v1;
lbl32:
                                // 3 sources

                                return v0;
                            }
                        }
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }

                    @Nullable
                    public final Object invoke(@NotNull CoroutineScope p1, int p2, @Nullable Continuation<Object> p3) {
                        Function3<CoroutineScope, Integer, Continuation<? super Object>, Object> function3 = new /* invalid duplicate definition of identical inner class */;
                        function3.I$0 = p2;
                        return function3.invokeSuspend(Unit.INSTANCE);
                    }
                }, (Function2<? super ArrayList<Object>, ? super Integer, Boolean>)new Function2<ArrayList<Object>, Integer, Boolean>(isEnd, this, resultList, searchSize){
                    final /* synthetic */ Ref.BooleanRef $isEnd;
                    final /* synthetic */ BookController this$0;
                    final /* synthetic */ Ref.ObjectRef<ArrayList<SearchBook>> $resultList;
                    final /* synthetic */ Ref.IntRef $searchSize;
                    {
                        this.$isEnd = $isEnd;
                        this.this$0 = $receiver;
                        this.$resultList = $resultList;
                        this.$searchSize = $searchSize;
                        super(2);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    public final boolean invoke(@NotNull ArrayList<Object> list2, int loopCount) {
                        void $this$forEach$iv;
                        Intrinsics.checkNotNullParameter(list2, "list");
                        Iterable iterable = list2;
                        Ref.ObjectRef<ArrayList<SearchBook>> objectRef = this.$resultList;
                        boolean $i$f$forEach = false;
                        Iterator<T> iterator2 = $this$forEach$iv.iterator();
                        while (iterator2.hasNext()) {
                            T element$iv;
                            T it = element$iv = iterator2.next();
                            boolean bl = false;
                            Collection bookList = it instanceof Collection ? (Collection)it : null;
                            Collection collection = bookList;
                            if (collection == null) continue;
                            Collection collection2 = collection;
                            boolean bl2 = false;
                            boolean bl3 = false;
                            Collection it2 = collection2;
                            boolean bl4 = false;
                            ((ArrayList)objectRef.element).addAll(it2);
                        }
                        return this.$isEnd.element || loopCount >= BookController.access$getConcurrentLoopCount$p(this.this$0) ? false : ((ArrayList)this.$resultList.element).size() < this.$searchSize.element;
                    }
                }, $continuation);
                if (v4 == var20_5) {
                    return var20_5;
                }
                ** GOTO lbl146
            }
            case 2: {
                var12_19 = (Ref.ObjectRef)$continuation.L$5;
                var10_13 = (Ref.ObjectRef)$continuation.L$4;
                var8_11 = (Ref.ObjectRef)$continuation.L$3;
                var5_8 = (Ref.IntRef)$continuation.L$2;
                var3_6 = (ReturnData)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v4 = $result;
lbl146:
                // 2 sources

                BookController.saveBookSources$default(this, (Book)var10_13.element, (List)var12_19.element, (String)var8_11.element, false, 8, null);
                var16_26 = new Pair[]{TuplesKt.to("lastIndex", Boxing.boxInt(var5_8.element)), TuplesKt.to("list", var12_19.element)};
                return ReturnData.setData$default(var3_6, MapsKt.mapOf(var16_26), null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object searchBookSourceSSE(@NotNull RoutingContext var1_1, @NotNull Continuation<? super Unit> var2_2) {
        if (!(var2_2 instanceof searchBookSourceSSE.1)) ** GOTO lbl-1000
        var21_3 = var2_2;
        if ((var21_3.label & -2147483648) != 0) {
            var21_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                Object L$5;
                Object L$6;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.searchBookSourceSSE(null, this);
                }
            };
        }
        $result = $continuation.result;
        var22_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                response = context.response().putHeader("Content-Type", "text/event-stream").putHeader("Cache-Control", "no-cache").setChunked(true);
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.L$3 = response;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var22_5) {
                    return var22_5;
                }
                ** GOTO lbl30
            }
            case 1: {
                response = (HttpServerResponse)$continuation.L$3;
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl30:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    response.write("event: error\n");
                    response.end("data: " + ExtKt.jsonEncode(ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528"), false) + "\n\n");
                    return Unit.INSTANCE;
                }
                var5_8 = null;
                lastIndex = new Ref.IntRef();
                searchSize = new Ref.IntRef();
                bookSourceGroup = new Ref.ObjectRef<String>();
                refresh = 0;
                if (context.request().method() == HttpMethod.POST) {
                    var10_13 = context.getBodyAsJson().getString("url");
                    Intrinsics.checkNotNullExpressionValue(var10_13, "context.bodyAsJson.getString(\"url\")");
                    bookUrl = var10_13;
                    var10_13 = context.getBodyAsJson().getInteger("lastIndex", Boxing.boxInt(-1));
                    Intrinsics.checkNotNullExpressionValue(var10_13, "context.bodyAsJson.getInteger(\"lastIndex\", -1)");
                    lastIndex.element = ((Number)var10_13).intValue();
                    var10_13 = context.getBodyAsJson().getInteger("searchSize", Boxing.boxInt(30));
                    Intrinsics.checkNotNullExpressionValue(var10_13, "context.bodyAsJson.getInteger(\"searchSize\", 30)");
                    searchSize.element = ((Number)var10_13).intValue();
                    var10_13 = context.getBodyAsJson().getString("bookSourceGroup", "");
                    Intrinsics.checkNotNullExpressionValue(var10_13, "context.bodyAsJson.getString(\"bookSourceGroup\", \"\")");
                    bookSourceGroup.element = var10_13;
                    var10_13 = context.getBodyAsJson().getInteger("refresh", Boxing.boxInt(0));
                    Intrinsics.checkNotNullExpressionValue(var10_13, "context.bodyAsJson.getInteger(\"refresh\", 0)");
                    refresh = ((Number)var10_13).intValue();
                } else {
                    var11_14 = context.queryParam("url");
                    Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"url\")");
                    var10_13 = CollectionsKt.firstOrNull(var11_14);
                    bookUrl = var10_13 == null ? "" : var10_13;
                    var11_14 = context.queryParam("lastIndex");
                    Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"lastIndex\")");
                    var10_13 = CollectionsKt.firstOrNull(var11_14);
                    if (var10_13 == null) {
                        v1 = -1;
                    } else {
                        var12_15 = var10_13;
                        var13_16 = false;
                        var11_14 = Boxing.boxInt(Integer.parseInt((String)var12_15));
                        v1 = var11_14 == null ? -1 : var11_14.intValue();
                    }
                    lastIndex.element = v1;
                    var11_14 = context.queryParam("searchSize");
                    Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"searchSize\")");
                    var10_13 = (String)CollectionsKt.firstOrNull(var11_14);
                    if (var10_13 == null) {
                        v2 = 30;
                    } else {
                        var12_15 = var10_13;
                        var13_16 = false;
                        var11_14 = Boxing.boxInt(Integer.parseInt((String)var12_15));
                        v2 = var11_14 == null ? 30 : var11_14.intValue();
                    }
                    searchSize.element = v2;
                    var11_14 = context.queryParam("bookSourceGroup");
                    Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"bookSourceGroup\")");
                    var10_13 = (String)CollectionsKt.firstOrNull(var11_14);
                    bookSourceGroup.element = var10_13 == null ? "" : var10_13;
                    var11_14 = context.queryParam("refresh");
                    Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"refresh\")");
                    var10_13 = CollectionsKt.firstOrNull(var11_14);
                    if (var10_13 == null) {
                        v3 = 0;
                    } else {
                        var12_15 = var10_13;
                        var13_16 = false;
                        var11_14 = Boxing.boxInt(Integer.parseInt((String)var12_15));
                        v3 = var11_14 == null ? 0 : var11_14.intValue();
                    }
                    refresh = v3;
                }
                userNameSpace = new Ref.ObjectRef<String>();
                userNameSpace.element = this.getUserNameSpace(context);
                urlMap = new BookSourceController(this.getCoroutineContext()).getBookSourceMap((String)userNameSpace.element);
                if (urlMap.size() <= 0) {
                    response.write("event: error\n");
                    response.end("data: " + ExtKt.jsonEncode(returnData.setErrorMsg("\u672a\u914d\u7f6e\u4e66\u6e90"), false) + "\n\n");
                    return Unit.INSTANCE;
                }
                var12_15 = (CharSequence)bookUrl;
                var13_16 = false;
                if (var12_15.length() == 0) {
                    response.write("event: error\n");
                    response.end("data: " + ExtKt.jsonEncode(returnData.setErrorMsg("\u8bf7\u8f93\u5165\u4e66\u7c4d\u94fe\u63a5"), false) + "\n\n");
                    return Unit.INSTANCE;
                }
                book = new Ref.ObjectRef<Book>();
                book.element = this.getShelfBookByURL((String)bookUrl, (String)userNameSpace.element);
                if (book.element == null) {
                    var13_17 = this.bookInfoCache.getAsString((String)bookUrl);
                    if (var13_17 == null) {
                        v4 /* !! */  = null;
                    } else {
                        var14_19 = ExtKt.toMap(var13_17);
                        if (var14_19 == null) {
                            v4 /* !! */  = null;
                        } else {
                            $this$toDataClass$iv = var14_19;
                            $i$f$toDataClass = false;
                            $this$convert$iv$iv = $this$toDataClass$iv;
                            $i$f$convert = false;
                            json$iv$iv = $this$convert$iv$iv instanceof String != false ? (String)$this$convert$iv$iv : ExtKt.getGson().toJson($this$convert$iv$iv);
                            v4 /* !! */  = book.element = (Book)ExtKt.getGson().fromJson(json$iv$iv, new TypeToken<Book>(){}.getType());
                        }
                    }
                }
                if (book.element == null) {
                    response.write("event: error\n");
                    response.end("data: " + ExtKt.jsonEncode(returnData.setErrorMsg("\u4e66\u7c4d\u4fe1\u606f\u9519\u8bef"), false) + "\n\n");
                    return Unit.INSTANCE;
                }
                if (lastIndex.element >= urlMap.size() - 1) {
                    response.write("event: error\n");
                    response.end("data: " + ExtKt.jsonEncode(ReturnData.setData$default(returnData, MapsKt.mapOf(TuplesKt.to("lastIndex", Boxing.boxInt(lastIndex.element))), null, 2, null).setErrorMsg("\u6ca1\u6709\u66f4\u591a\u4e86"), false) + "\n\n");
                    return Unit.INSTANCE;
                }
                searchSize.element = searchSize.element > 0 ? searchSize.element : 30;
                resultList = new Ref.ObjectRef<ArrayList<SearchBook>>();
                var14_20 = false;
                resultList.element = new ArrayList<E>();
                concurrentCount = Math.max(searchSize.element * 2, 24);
                $this$toDataClass$iv = new Object[]{Boxing.boxInt(lastIndex.element), Boxing.boxInt(concurrentCount), Boxing.boxInt(searchSize.element)};
                BookControllerKt.access$getLogger$p().info("searchBookMulti from lastIndex: {} concurrentCount: {} searchSize: {}", $this$toDataClass$iv);
                isEnd = new Ref.BooleanRef();
                context.request().connection().closeHandler((Handler<Void>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, searchBookSourceSSE$lambda-8(kotlin.jvm.internal.Ref$BooleanRef com.htmake.reader.api.controller.BookController java.lang.Void ), (Ljava/lang/Void;)V)((Ref.BooleanRef)isEnd, (BookController)this));
                bookSourceFile = new Ref.ObjectRef<File>();
                $this$convert$iv$iv = new String[]{"data", (String)userNameSpace.element, "bookSource"};
                bookSourceFile.element = ExtKt.getStorageFile$default((String[])$this$convert$iv$iv, null, 2, null);
                if (!((File)bookSourceFile.element).exists()) {
                    $this$convert$iv$iv = new String[]{"data", "default", "bookSource"};
                    bookSourceFile.element = ExtKt.getStorageFile$default((String[])$this$convert$iv$iv, null, 2, null);
                }
                maxSize = new Ref.IntRef();
                maxSize.element = urlMap.size();
                $continuation.L$0 = this;
                $continuation.L$1 = response;
                $continuation.L$2 = lastIndex;
                $continuation.L$3 = userNameSpace;
                $continuation.L$4 = book;
                $continuation.L$5 = resultList;
                $continuation.L$6 = maxSize;
                $continuation.label = 2;
                v5 = this.limitConcurrent(concurrentCount, lastIndex.element + 1, urlMap.size(), (Function3<? super CoroutineScope, ? super Integer, ? super Continuation<Object>, ? extends Object>)new Function3<CoroutineScope, Integer, Continuation<? super Object>, Object>(maxSize, lastIndex, bookSourceFile, bookSourceGroup, this, book, userNameSpace, null){
                    int label;
                    /* synthetic */ int I$0;
                    final /* synthetic */ Ref.IntRef $maxSize;
                    final /* synthetic */ Ref.IntRef $lastIndex;
                    final /* synthetic */ Ref.ObjectRef<File> $bookSourceFile;
                    final /* synthetic */ Ref.ObjectRef<String> $bookSourceGroup;
                    final /* synthetic */ BookController this$0;
                    final /* synthetic */ Ref.ObjectRef<Book> $book;
                    final /* synthetic */ Ref.ObjectRef<String> $userNameSpace;
                    {
                        this.$maxSize = $maxSize;
                        this.$lastIndex = $lastIndex;
                        this.$bookSourceFile = $bookSourceFile;
                        this.$bookSourceGroup = $bookSourceGroup;
                        this.this$0 = $receiver;
                        this.$book = $book;
                        this.$userNameSpace = $userNameSpace;
                        super(3, $completion);
                    }

                    /*
                     * Unable to fully structure code
                     */
                    @Nullable
                    public final Object invokeSuspend(@NotNull Object var1_1) {
                        var6_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        switch (this.label) {
                            case 0: {
                                ResultKt.throwOnFailure(var1_1);
                                it = this.I$0;
                                if (it <= this.$maxSize.element) ** GOTO lbl10
                                var3_4 = false;
                                v0 = new ArrayList<E>();
                                ** GOTO lbl32
lbl10:
                                // 1 sources

                                var3_5 = this.$lastIndex.element;
                                var4_7 = false;
                                this.$lastIndex.element = Math.max(var3_5, it);
                                var4_8 = (CharSequence)this.$bookSourceGroup.element;
                                var5_10 = false;
                                bookSourceList = ExtKt.parseJsonStringList$default((File)this.$bookSourceFile.element, null, null, it, it, null, var4_8.length() == 0 != false ? null : (Function1)new Function1<ObjectNode, Boolean>(this.$bookSourceGroup){
                                    final /* synthetic */ Ref.ObjectRef<String> $bookSourceGroup;
                                    {
                                        this.$bookSourceGroup = $bookSourceGroup;
                                        super(1);
                                    }

                                    public final boolean invoke(@NotNull ObjectNode it) {
                                        Intrinsics.checkNotNullParameter(it, "it");
                                        String _bookSourceGroup = it.get("bookSourceGroup").asText();
                                        CharSequence charSequence = _bookSourceGroup;
                                        boolean bl = false;
                                        boolean bl2 = false;
                                        return !(charSequence == null || charSequence.length() == 0) && StringsKt.indexOf$default((CharSequence)Intrinsics.stringPlus(_bookSourceGroup, ","), Intrinsics.stringPlus((String)this.$bookSourceGroup.element, ","), 0, false, 6, null) >= 0;
                                    }
                                }, 38, null);
                                if (bookSourceList != null && !bookSourceList.isEmpty()) ** GOTO lbl21
                                this.$maxSize.element = it;
                                var4_9 = false;
                                v0 = new ArrayList<E>();
                                ** GOTO lbl32
lbl21:
                                // 1 sources

                                var4_8 = bookSourceList.getString(0);
                                Intrinsics.checkNotNullExpressionValue(var4_8, "bookSourceList.getString(0)");
                                this.label = 1;
                                v1 = BookController.searchBookWithSource$default(this.this$0, (String)var4_8, (Book)this.$book.element, false, (String)this.$userNameSpace.element, this, 4, null);
                                if (v1 == var6_2) {
                                    return var6_2;
                                }
                                ** GOTO lbl31
                            }
                            case 1: {
                                ResultKt.throwOnFailure($result);
                                v1 = $result;
lbl31:
                                // 2 sources

                                v0 = (ArrayList)v1;
lbl32:
                                // 3 sources

                                return v0;
                            }
                        }
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }

                    @Nullable
                    public final Object invoke(@NotNull CoroutineScope p1, int p2, @Nullable Continuation<Object> p3) {
                        Function3<CoroutineScope, Integer, Continuation<? super Object>, Object> function3 = new /* invalid duplicate definition of identical inner class */;
                        function3.I$0 = p2;
                        return function3.invokeSuspend(Unit.INSTANCE);
                    }
                }, (Function2<? super ArrayList<Object>, ? super Integer, Boolean>)new Function2<ArrayList<Object>, Integer, Boolean>(response, lastIndex, resultList, isEnd, this, searchSize){
                    final /* synthetic */ HttpServerResponse $response;
                    final /* synthetic */ Ref.IntRef $lastIndex;
                    final /* synthetic */ Ref.ObjectRef<ArrayList<SearchBook>> $resultList;
                    final /* synthetic */ Ref.BooleanRef $isEnd;
                    final /* synthetic */ BookController this$0;
                    final /* synthetic */ Ref.IntRef $searchSize;
                    {
                        this.$response = $response;
                        this.$lastIndex = $lastIndex;
                        this.$resultList = $resultList;
                        this.$isEnd = $isEnd;
                        this.this$0 = $receiver;
                        this.$searchSize = $searchSize;
                        super(2);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    public final boolean invoke(@NotNull ArrayList<Object> list2, int loopCount) {
                        void $this$forEach$iv;
                        Intrinsics.checkNotNullParameter(list2, "list");
                        boolean bl = false;
                        ArrayList<E> loopResult = new ArrayList<E>();
                        Pair[] pairArray = (Pair[])list2;
                        Ref.ObjectRef<ArrayList<SearchBook>> objectRef = this.$resultList;
                        boolean $i$f$forEach = false;
                        Iterator<T> iterator2 = $this$forEach$iv.iterator();
                        while (iterator2.hasNext()) {
                            T element$iv;
                            T it = element$iv = iterator2.next();
                            boolean bl2 = false;
                            Collection bookList = it instanceof Collection ? (Collection)it : null;
                            Collection collection = bookList;
                            if (collection == null) continue;
                            Collection collection2 = collection;
                            boolean bl3 = false;
                            boolean bl4 = false;
                            Collection it2 = collection2;
                            boolean bl5 = false;
                            ((ArrayList)objectRef.element).addAll(it2);
                            loopResult.addAll(it2);
                        }
                        pairArray = new Pair[]{TuplesKt.to("lastIndex", this.$lastIndex.element), TuplesKt.to("data", loopResult)};
                        this.$response.write("data: " + ExtKt.jsonEncode(MapsKt.mapOf(pairArray), false) + "\n\n");
                        BookControllerKt.access$getLogger$p().info("Loop: {} resultList.size: {}", (Object)loopCount, (Object)((ArrayList)this.$resultList.element).size());
                        return this.$isEnd.element || loopCount >= BookController.access$getConcurrentLoopCount$p(this.this$0) ? false : ((ArrayList)this.$resultList.element).size() < this.$searchSize.element;
                    }
                }, $continuation);
                if (v5 == var22_5) {
                    return var22_5;
                }
                ** GOTO lbl181
            }
            case 2: {
                maxSize = (Ref.IntRef)$continuation.L$6;
                var13_18 = (Ref.ObjectRef)$continuation.L$5;
                var12_15 = (Ref.ObjectRef)$continuation.L$4;
                var10_13 = (Ref.ObjectRef)$continuation.L$3;
                var6_9 = (Ref.IntRef)$continuation.L$2;
                var4_7 = (HttpServerResponse)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v5 = $result;
lbl181:
                // 2 sources

                BookController.saveBookSources$default(this, (Book)var12_15.element, (List)var13_18.element, (String)var10_13.element, false, 8, null);
                var4_7.write("event: end\n");
                var18_26 = new Pair[]{TuplesKt.to("lastIndex", Boxing.boxInt(var6_9.element)), TuplesKt.to("isEnd", Boxing.boxBoolean(var6_9.element >= maxSize.element))};
                var4_7.end("data: " + ExtKt.jsonEncode(MapsKt.mapOf(var18_26), false) + "\n\n");
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object searchBookWithSource(@NotNull String var1_1, @NotNull Book var2_2, boolean var3_3, @NotNull String var4_4, @NotNull Continuation<? super ArrayList<SearchBook>> var5_5) {
        if (!(var5_5 instanceof searchBookWithSource.1)) ** GOTO lbl-1000
        var11_6 = var5_5;
        if ((var11_6.label & -2147483648) != 0) {
            var11_6.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var5_5){
                Object L$0;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.searchBookWithSource(null, null, false, null, this);
                }
            };
        }
        $result = $continuation.result;
        var12_8 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                resultList = new Ref.ObjectRef<ArrayList<SearchBook>>();
                var7_10 = false;
                resultList.element = new ArrayList<E>();
                bookSource = new Ref.ObjectRef<BookSource>();
                var8_12 = BookSource.Companion.fromJson-IoAF18A((String)bookSourceString);
                var9_13 = false;
                v0 = bookSource.element = Result.isFailure-impl(var8_12) != false ? null : var8_12;
                if (bookSource.element == null) {
                    return resultList.element;
                }
                if (this.isInvalidBookSource((BookSource)bookSource.element, (String)userNameSpace)) {
                    return resultList.element;
                }
                $continuation.L$0 = resultList;
                $continuation.label = 1;
                v1 = BuildersKt.withContext(Dispatchers.getIO(), (Function2)new Function2<CoroutineScope, Continuation<? super Unit>, Object>(bookSource, (String)userNameSpace, (Book)book, accurate != false, resultList, this, null){
                    long J$0;
                    int label;
                    final /* synthetic */ Ref.ObjectRef<BookSource> $bookSource;
                    final /* synthetic */ String $userNameSpace;
                    final /* synthetic */ Book $book;
                    final /* synthetic */ boolean $accurate;
                    final /* synthetic */ Ref.ObjectRef<ArrayList<SearchBook>> $resultList;
                    final /* synthetic */ BookController this$0;
                    {
                        this.$bookSource = $bookSource;
                        this.$userNameSpace = $userNameSpace;
                        this.$book = $book;
                        this.$accurate = $accurate;
                        this.$resultList = $resultList;
                        this.this$0 = $receiver;
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
                                this.J$0 = start = System.currentTimeMillis();
                                this.label = 1;
                                v0 = new WebBook((BookSource)this.$bookSource.element, false, null, this.$userNameSpace, 4, null).searchBook(this.$book.getName(), Boxing.boxInt(1), this);
                                ** if (v0 != var13_2) goto lbl12
lbl11:
                                // 1 sources

                                return var13_2;
lbl12:
                                // 1 sources

                                ** GOTO lbl19
                            }
                            case 1: {
                                start = this.J$0;
                                try {
                                    ResultKt.throwOnFailure($result);
                                    v0 = $result;
lbl19:
                                    // 2 sources

                                    result = (List)v0;
                                    end = System.currentTimeMillis();
                                    if (result.size() > 0 && (var7_8 = 0) < (var8_9 = result.size())) {
                                        do {
                                            j = var7_8++;
                                            _book = (SearchBook)result.get(j);
                                            if (this.$accurate && _book.getName().equals(this.$book.getName())) {
                                                var11_12 = this.$book.getAuthor();
                                                var12_13 = false;
                                                if (var11_12.length() == 0 || _book.getAuthor().equals(this.$book.getAuthor())) {
                                                    _book.setTime(end - start);
                                                    ((ArrayList)this.$resultList.element).add(_book);
                                                    continue;
                                                }
                                            }
                                            if (this.$accurate || StringsKt.indexOf$default((CharSequence)_book.getName(), this.$book.getName(), 0, true, 2, null) < 0 && StringsKt.indexOf$default((CharSequence)_book.getAuthor(), this.$book.getName(), 0, true, 2, null) < 0) continue;
                                            _book.setTime(end - start);
                                            ((ArrayList)this.$resultList.element).add(_book);
                                        } while (var7_8 < var8_9);
                                    }
                                }
                                catch (Exception e) {
                                    var4_6 = new Pair[]{TuplesKt.to("sourceUrl", ((BookSource)this.$bookSource.element).getBookSourceUrl()), TuplesKt.to("time", Boxing.boxLong(System.currentTimeMillis())), TuplesKt.to("error", e.toString())};
                                    info = MapsKt.mutableMapOf(var4_6);
                                    BookController.access$addInvalidBookSource(this.this$0, ((BookSource)this.$bookSource.element).getBookSourceUrl(), info, this.$userNameSpace);
                                    e.printStackTrace();
                                }
                                return Unit.INSTANCE;
                            }
                        }
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }

                    @NotNull
                    public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                        return (Continuation)((Object)new /* invalid duplicate definition of identical inner class */);
                    }

                    @Nullable
                    public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Unit> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                }, $continuation);
                if (v1 == var12_8) {
                    return var12_8;
                }
                ** GOTO lbl33
            }
            case 1: {
                var6_9 = (Ref.ObjectRef)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v1 = $result;
lbl33:
                // 2 sources

                return var6_9.element;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object searchBookWithSource$default(BookController bookController, String string, Book book, boolean bl, String string2, Continuation continuation, int n, Object object) {
        if ((n & 4) != 0) {
            bl = true;
        }
        if ((n & 8) != 0) {
            string2 = "default";
        }
        return bookController.searchBookWithSource(string, book, bl, string2, continuation);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object getAvailableBookSource(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof getAvailableBookSource.1)) ** GOTO lbl-1000
        var16_3 = var2_2;
        if ((var16_3.label & -2147483648) != 0) {
            var16_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.getAvailableBookSource(null, this);
                }
            };
        }
        $result = $continuation.result;
        var17_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var17_5) {
                    return var17_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                var4_7 = null;
                var5_8 = 0;
                if (context.request().method() == HttpMethod.POST) {
                    var6_9 = context.getBodyAsJson().getString("url");
                    Intrinsics.checkNotNullExpressionValue(var6_9, "context.bodyAsJson.getString(\"url\")");
                    bookUrl = var6_9;
                    var6_9 = context.getBodyAsJson().getInteger("refresh", Boxing.boxInt(0));
                    Intrinsics.checkNotNullExpressionValue(var6_9, "context.bodyAsJson.getInteger(\"refresh\", 0)");
                    var5_8 = ((Number)var6_9).intValue();
                } else {
                    var7_10 = context.queryParam("url");
                    Intrinsics.checkNotNullExpressionValue(var7_10, "context.queryParam(\"url\")");
                    var6_9 = CollectionsKt.firstOrNull(var7_10);
                    bookUrl = var6_9 == null ? "" : var6_9;
                    var7_10 = context.queryParam("refresh");
                    Intrinsics.checkNotNullExpressionValue(var7_10, "context.queryParam(\"refresh\")");
                    var6_9 = CollectionsKt.firstOrNull(var7_10);
                    if (var6_9 == null) {
                        v1 = 0;
                    } else {
                        var8_13 = var6_9;
                        var9_17 = false;
                        var7_10 = Boxing.boxInt(Integer.parseInt((String)var8_13));
                        v1 = var7_10 == null ? 0 : var7_10.intValue();
                    }
                    refresh = v1;
                }
                var6_9 = (CharSequence)bookUrl;
                var7_11 = false;
                var8_14 = false;
                if (var6_9.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u4e66\u7c4d\u94fe\u63a5");
                }
                userNameSpace = new Ref.ObjectRef<String>();
                userNameSpace.element = this.getUserNameSpace(context);
                book = new Ref.ObjectRef<Book>();
                book.element = this.getShelfBookByURL((String)bookUrl, (String)userNameSpace.element);
                if (book.element == null) {
                    var8_15 = this.bookInfoCache.getAsString((String)bookUrl);
                    if (var8_15 == null) {
                        v2 /* !! */  = null;
                    } else {
                        var9_18 = ExtKt.toMap(var8_15);
                        if (var9_18 == null) {
                            v2 /* !! */  = null;
                        } else {
                            $this$toDataClass$iv = var9_18;
                            $i$f$toDataClass = false;
                            $this$convert$iv$iv = $this$toDataClass$iv;
                            $i$f$convert = false;
                            json$iv$iv = $this$convert$iv$iv instanceof String != false ? (String)$this$convert$iv$iv : ExtKt.getGson().toJson($this$convert$iv$iv);
                            v2 /* !! */  = book.element = (Book)ExtKt.getGson().fromJson(json$iv$iv, new TypeToken<Book>(){}.getType());
                        }
                    }
                }
                if (book.element == null) {
                    return returnData.setErrorMsg("\u4e66\u7c4d\u4fe1\u606f\u9519\u8bef");
                }
                bookSourceList = new Ref.ObjectRef<JsonArray>();
                var9_19 /* !! */  = new String[]{((Book)book.element).getName() + '_' + ((Book)book.element).getAuthor(), "bookSource"};
                bookSourceList.element = ExtKt.asJsonArray(this.getUserStorage(userNameSpace.element, var9_19 /* !! */ ));
                if (bookSourceList.element == null || ((JsonArray)bookSourceList.element).size() <= 0) break;
                if (refresh <= 0) {
                    var9_19 /* !! */  = ((JsonArray)bookSourceList.element).getList();
                    Intrinsics.checkNotNullExpressionValue(var9_19 /* !! */ , "bookSourceList.getList()");
                    return ReturnData.setData$default(returnData, var9_19 /* !! */ , null, 2, null);
                }
                resultList = new Ref.ObjectRef<ArrayList<SearchBook>>();
                $this$toDataClass$iv = false;
                resultList.element = new ArrayList<E>();
                concurrentCount = 16;
                $continuation.L$0 = this;
                $continuation.L$1 = returnData;
                $continuation.L$2 = userNameSpace;
                $continuation.L$3 = book;
                $continuation.L$4 = resultList;
                $continuation.label = 2;
                v3 = this.limitConcurrent(concurrentCount, 0, ((JsonArray)bookSourceList.element).size(), (Function3<? super CoroutineScope, ? super Integer, ? super Continuation<Object>, ? extends Object>)new Function3<CoroutineScope, Integer, Continuation<? super Object>, Object>(bookSourceList, this, userNameSpace, book, null){
                    int label;
                    /* synthetic */ int I$0;
                    final /* synthetic */ Ref.ObjectRef<JsonArray> $bookSourceList;
                    final /* synthetic */ BookController this$0;
                    final /* synthetic */ Ref.ObjectRef<String> $userNameSpace;
                    final /* synthetic */ Ref.ObjectRef<Book> $book;
                    {
                        this.$bookSourceList = $bookSourceList;
                        this.this$0 = $receiver;
                        this.$userNameSpace = $userNameSpace;
                        this.$book = $book;
                        super(3, $completion);
                    }

                    /*
                     * Unable to fully structure code
                     */
                    @Nullable
                    public final Object invokeSuspend(@NotNull Object var1_1) {
                        block5: {
                            var6_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                            switch (this.label) {
                                case 0: {
                                    ResultKt.throwOnFailure(var1_1);
                                    it = this.I$0;
                                    searchBook = ((JsonArray)this.$bookSourceList.element).getJsonObject(it).mapTo(SearchBook.class);
                                    if (!searchBook.getOrigin().equals("loc_book")) ** GOTO lbl11
                                    var4_5 = new SearchBook[]{searchBook};
                                    v0 = CollectionsKt.arrayListOf(var4_5);
                                    break block5;
lbl11:
                                    // 1 sources

                                    bookSource = this.this$0.getBookSourceStringBySourceURLOpt(searchBook.getOrigin(), (String)this.$userNameSpace.element);
                                    if (bookSource == null) break;
                                    this.label = 1;
                                    v1 = BookController.searchBookWithSource$default(this.this$0, bookSource, (Book)this.$book.element, false, (String)this.$userNameSpace.element, this, 4, null);
                                    if (v1 == var6_2) {
                                        return var6_2;
                                    }
                                    ** GOTO lbl21
                                }
                                case 1: {
                                    ResultKt.throwOnFailure($result);
                                    v1 = $result;
lbl21:
                                    // 2 sources

                                    v0 = (ArrayList<E>)v1;
                                    break block5;
                                }
                            }
                            var5_7 = false;
                            v0 = new ArrayList<E>();
                        }
                        return v0;
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }

                    @Nullable
                    public final Object invoke(@NotNull CoroutineScope p1, int p2, @Nullable Continuation<Object> p3) {
                        Function3<CoroutineScope, Integer, Continuation<? super Object>, Object> function3 = new /* invalid duplicate definition of identical inner class */;
                        function3.I$0 = p2;
                        return function3.invokeSuspend(Unit.INSTANCE);
                    }
                }, (Function2<? super ArrayList<Object>, ? super Integer, Boolean>)new Function2<ArrayList<Object>, Integer, Boolean>(resultList){
                    final /* synthetic */ Ref.ObjectRef<ArrayList<SearchBook>> $resultList;
                    {
                        this.$resultList = $resultList;
                        super(2);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    public final boolean invoke(@NotNull ArrayList<Object> list2, int $noName_1) {
                        void $this$forEach$iv;
                        Intrinsics.checkNotNullParameter(list2, "list");
                        Iterable iterable = list2;
                        Ref.ObjectRef<ArrayList<SearchBook>> objectRef = this.$resultList;
                        boolean $i$f$forEach = false;
                        Iterator<T> iterator2 = $this$forEach$iv.iterator();
                        while (iterator2.hasNext()) {
                            T element$iv;
                            T it = element$iv = iterator2.next();
                            boolean bl = false;
                            Collection bookList = it instanceof Collection ? (Collection)it : null;
                            Collection collection = bookList;
                            if (collection == null) continue;
                            Collection collection2 = collection;
                            boolean bl2 = false;
                            boolean bl3 = false;
                            Collection it2 = collection2;
                            boolean bl4 = false;
                            ((ArrayList)objectRef.element).addAll(it2);
                        }
                        return true;
                    }
                }, $continuation);
                if (v3 == var17_5) {
                    return var17_5;
                }
                ** GOTO lbl111
            }
            case 2: {
                var9_19 /* !! */  = (String[])$continuation.L$4;
                var7_12 = (Ref.ObjectRef)$continuation.L$3;
                var6_9 = (Ref.ObjectRef)$continuation.L$2;
                var3_6 = (ReturnData)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v3 = $result;
lbl111:
                // 2 sources

                this.saveBookSources((Book)var7_12.element, (List)var9_19 /* !! */ .element, (String)var6_9.element, true);
                return ReturnData.setData$default(var3_6, var9_19 /* !! */ .element, null, 2, null);
            }
        }
        var9_20 = false;
        return ReturnData.setData$default(var3_6, new ArrayList<E>(), null, 2, null);
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getBookshelf(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof getBookshelf.1)) ** GOTO lbl-1000
        var10_3 = var2_2;
        if ((var10_3.label & -2147483648) != 0) {
            var10_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.getBookshelf(null, this);
                }
            };
        }
        $result = $continuation.result;
        var11_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var11_5) {
                    return var11_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                var4_7 = 0;
                if (context.request().method() == HttpMethod.POST) {
                    var5_8 = context.getBodyAsJson().getInteger("refresh", Boxing.boxInt(0));
                    Intrinsics.checkNotNullExpressionValue(var5_8, "context.bodyAsJson.getInteger(\"refresh\", 0)");
                    var4_7 = ((Number)var5_8).intValue();
                } else {
                    var6_9 = context.queryParam("refresh");
                    Intrinsics.checkNotNullExpressionValue(var6_9, "context.queryParam(\"refresh\")");
                    var5_8 = CollectionsKt.firstOrNull(var6_9);
                    if (var5_8 == null) {
                        v1 = 0;
                    } else {
                        var7_10 = var5_8;
                        var8_11 = false;
                        var6_9 = Boxing.boxInt(Integer.parseInt((String)var7_10));
                        v1 = var6_9 == null ? 0 : var6_9.intValue();
                    }
                    refresh = v1;
                }
                $continuation.L$0 = returnData;
                $continuation.L$1 = null;
                $continuation.L$2 = null;
                $continuation.label = 2;
                v2 = this.getBookShelfBooks(refresh > 0, this.getUserNameSpace(context), $continuation);
                if (v2 == var11_5) {
                    return var11_5;
                }
                ** GOTO lbl58
            }
            case 2: {
                var3_6 = (ReturnData)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v2 = $result;
lbl58:
                // 2 sources

                bookList = (List)v2;
                return ReturnData.setData$default(var3_6, bookList, null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getShelfBook(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof getShelfBook.1)) ** GOTO lbl-1000
        var9_3 = var2_2;
        if ((var9_3.label & -2147483648) != 0) {
            var9_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.getShelfBook(null, this);
                }
            };
        }
        $result = $continuation.result;
        var10_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var10_5) {
                    return var10_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                var4_7 = null;
                if (context.request().method() == HttpMethod.POST) {
                    var5_8 = context.getBodyAsJson().getString("url");
                    Intrinsics.checkNotNullExpressionValue(var5_8, "context.bodyAsJson.getString(\"url\")");
                    var4_7 = var5_8;
                } else {
                    var6_9 = context.queryParam("url");
                    Intrinsics.checkNotNullExpressionValue(var6_9, "context.queryParam(\"url\")");
                    var5_8 = CollectionsKt.firstOrNull(var6_9);
                    url = var5_8 == null ? "" : var5_8;
                }
                var5_8 = url;
                var6_10 = false;
                var7_11 = false;
                if (var5_8.length() == 0) {
                    return returnData.setErrorMsg("\u4e66\u6e90\u94fe\u63a5\u4e0d\u80fd\u4e3a\u7a7a");
                }
                book = this.getShelfBookByURL(url, this.getUserNameSpace(context));
                if (book == null) {
                    return returnData.setErrorMsg("\u4e66\u7c4d\u4e0d\u5b58\u5728");
                }
                return ReturnData.setData$default(returnData, book, null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object saveBook(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof saveBook.1)) ** GOTO lbl-1000
        var11_3 = var2_2;
        if ((var11_3.label & -2147483648) != 0) {
            var11_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                Object L$5;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.saveBook(null, this);
                }
            };
        }
        $result = $continuation.result;
        var12_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var12_5) {
                    return var12_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                book = context.getBodyAsJson().mapTo(Book.class);
                userNameSpace = this.getUserNameSpace(context);
                if (book.isLocalBook()) break;
                bookSource = this.getBookSourceStringBySourceURLOpt(book.getOrigin(), userNameSpace);
                if (bookSource == null) {
                    return returnData.setErrorMsg("\u4e66\u6e90\u4fe1\u606f\u9519\u8bef");
                }
                var7_10 = book.getTocUrl();
                var8_11 = false;
                var9_12 = false;
                if (!(var7_10 == null || var7_10.length() == 0)) ** GOTO lbl84
                v1 = new WebBook((String)bookSource, this.getAppConfig().getDebugLog(), null, userNameSpace, 4, null);
                var7_10 = book;
                Intrinsics.checkNotNullExpressionValue(var7_10, "book");
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.L$3 = book;
                $continuation.L$4 = userNameSpace;
                $continuation.L$5 = bookSource;
                $continuation.label = 2;
                v2 = WebBook.getBookInfo$default(v1, (Book)var7_10, false, (Continuation)$continuation, 2, null);
                if (v2 == var12_5) {
                    return var12_5;
                }
                ** GOTO lbl62
            }
            case 2: {
                bookSource = (String)$continuation.L$5;
                var5_8 = (String)$continuation.L$4;
                var4_7 = (Book)$continuation.L$3;
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v2 = $result;
lbl62:
                // 2 sources

                var7_10 = var4_7;
                Intrinsics.checkNotNullExpressionValue(var7_10, "book");
                $continuation.L$0 = this;
                $continuation.L$1 = var1_1;
                $continuation.L$2 = var3_6;
                $continuation.L$3 = var5_8;
                $continuation.L$4 = bookSource;
                $continuation.L$5 = null;
                $continuation.label = 3;
                v3 = this.mergeBookCacheInfo((Book)var7_10, $continuation);
                if (v3 == var12_5) {
                    return var12_5;
                }
                ** GOTO lbl83
            }
            case 3: {
                bookSource = (String)$continuation.L$4;
                var5_8 = (String)$continuation.L$3;
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v3 = $result;
lbl83:
                // 2 sources

                var4_7 = (Book)v3;
lbl84:
                // 2 sources

                var7_10 = var4_7;
                Intrinsics.checkNotNullExpressionValue(var7_10, "book");
                $continuation.L$0 = this;
                $continuation.L$1 = var1_1;
                $continuation.L$2 = var3_6;
                $continuation.L$3 = var4_7;
                $continuation.L$4 = var5_8;
                $continuation.label = 4;
                v4 = this.saveBookCover((Book)var7_10, var5_8, (String)bookSource, $continuation);
                if (v4 == var12_5) {
                    return var12_5;
                }
                ** GOTO lbl125
            }
            case 4: {
                var5_8 = (String)$continuation.L$4;
                var4_7 = (Book)$continuation.L$3;
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v4 = $result;
                ** GOTO lbl125
            }
        }
        bookSource = var4_7;
        Intrinsics.checkNotNullExpressionValue(bookSource, "book");
        $continuation.L$0 = this;
        $continuation.L$1 = var1_1;
        $continuation.L$2 = var3_6;
        $continuation.L$3 = var4_7;
        $continuation.L$4 = var5_8;
        $continuation.label = 5;
        v5 = this.saveLocalBookCover((Book)bookSource, var5_8, $continuation);
        if (v5 == var12_5) {
            return var12_5;
        }
        ** GOTO lbl125
        {
            case 5: {
                var5_8 = (String)$continuation.L$4;
                var4_7 = (Book)$continuation.L$3;
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v5 = $result;
lbl125:
                // 4 sources

                var7_10 = var4_7;
                Intrinsics.checkNotNullExpressionValue(var7_10, "book");
                result = this.saveBookToShelf((Book)var7_10, var5_8, var1_1);
                if (result.getSecond() != null) {
                    var7_10 = result.getSecond();
                    return var3_6.setErrorMsg((String)(var7_10 == null ? "" : var7_10));
                }
                return ReturnData.setData$default(var3_6, result.getFirst(), null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @NotNull
    public final Pair<Book, String> saveBookToShelf(@NotNull Book _book, @NotNull String userNameSpace, @NotNull RoutingContext context) {
        User userInfo;
        Intrinsics.checkNotNullParameter(_book, "_book");
        Intrinsics.checkNotNullParameter(userNameSpace, "userNameSpace");
        Intrinsics.checkNotNullParameter(context, "context");
        Book book = _book;
        CharSequence charSequence = book.getOrigin();
        boolean bl = false;
        int n = 0;
        if (charSequence == null || charSequence.length() == 0) {
            return new Pair<Book, String>(book, "\u672a\u627e\u5230\u4e66\u6e90\u4fe1\u606f");
        }
        charSequence = book.getBookUrl();
        bl = false;
        n = 0;
        if (charSequence == null || charSequence.length() == 0) {
            return new Pair<Book, String>(book, "\u4e66\u7c4d\u94fe\u63a5\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String[] stringArray = new String[]{"bookshelf"};
        JsonArray bookshelf = ExtKt.asJsonArray(this.getUserStorage(userNameSpace, stringArray));
        if (bookshelf == null) {
            bookshelf = new JsonArray();
        }
        int existIndex = -1;
        n = 0;
        int n2 = bookshelf.size();
        if (n < n2) {
            do {
                int i = n++;
                String name = bookshelf.getJsonObject(i).getString("name", "");
                String author = bookshelf.getJsonObject(i).getString("author", "");
                if (!name.equals(book.getName()) || !author.equals(book.getAuthor())) continue;
                existIndex = i;
                break;
            } while (n < n2);
        }
        if (existIndex < 0 && (userInfo = (User)context.get("userInfo")) != null && bookshelf.size() >= userInfo.getBook_limit()) {
            return new Pair<Book, String>(book, "\u4f60\u5df2\u8fbe\u5230\u4e66\u7c4d\u6570\u4e0a\u9650\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
        if (book.isLocalBook()) {
            if (StringsKt.startsWith$default(book.getBookUrl(), "/assets/", false, 2, null) || StringsKt.startsWith$default(book.getBookUrl(), "assets/", false, 2, null)) {
                File tempFile = new File(ExtKt.getWorkDir(Intrinsics.stringPlus("storage", book.getBookUrl())));
                if (!tempFile.exists()) {
                    return new Pair<Book, String>(book, "\u4e0a\u4f20\u4e66\u7c4d\u4e0d\u5b58\u5728");
                }
                String[] i = new String[]{"data", userNameSpace, book.getName() + '_' + book.getAuthor(), tempFile.getName()};
                String relativeLocalFilePath = ((Object)Paths.get("storage", i)).toString();
                String relativeLocalFileUrl = "storage/data/" + userNameSpace + '/' + book.getName() + '_' + book.getAuthor() + '/' + tempFile.getName();
                String localFilePath = ExtKt.getWorkDir(relativeLocalFilePath);
                BookControllerKt.access$getLogger$p().info("localFilePath: {}", (Object)localFilePath);
                File localFile = new File(localFilePath);
                ExtKt.deleteRecursively(localFile);
                if (!localFile.getParentFile().exists()) {
                    localFile.getParentFile().mkdirs();
                }
                if (!FilesKt.copyRecursively$default(tempFile, localFile, false, null, 6, null)) {
                    return new Pair<Book, String>(book, "\u5bfc\u5165\u672c\u5730\u4e66\u7c4d\u5931\u8d25");
                }
                ExtKt.deleteRecursively(tempFile);
                book.setBookUrl(relativeLocalFileUrl);
                book.setOriginName(relativeLocalFilePath);
                if (book.isEpub()) {
                    if (!BookController.extractEpub$default(this, book, false, 2, null)) {
                        return new Pair<Book, String>(book, "\u5bfc\u5165\u672c\u5730Epub\u4e66\u7c4d\u5931\u8d25");
                    }
                } else if (book.isCbz()) {
                    if (!BookController.extractCbz$default(this, book, false, 2, null)) {
                        return new Pair<Book, String>(book, "\u5bfc\u5165\u672c\u5730CBZ\u4e66\u7c4d\u5931\u8d25");
                    }
                } else if (book.isPdf() && !BookController.convertPdfToImage$default(this, book, false, 2, null)) {
                    return new Pair<Book, String>(book, "\u672c\u5730PDF\u4e66\u7c4d\u8f6c\u6362\u5931\u8d25");
                }
            } else if (StringsKt.indexOf$default((CharSequence)book.getBookUrl(), "localStore", 0, false, 6, null) >= 0) {
                File tempFile = new File(ExtKt.getWorkDir(book.getBookUrl()));
                if (!tempFile.exists()) {
                    return new Pair<Book, String>(book, "\u672c\u5730\u4e66\u4ed3\u4e66\u7c4d\u4e0d\u5b58\u5728");
                }
                String relativeLocalFileUrl = "storage/data/" + userNameSpace + '/' + book.getName() + '_' + book.getAuthor() + '/' + tempFile.getName();
                book.setBookUrl(relativeLocalFileUrl);
                if (book.isEpub()) {
                    if (!BookController.extractEpub$default(this, book, false, 2, null)) {
                        return new Pair<Book, String>(book, "\u5bfc\u5165\u672c\u5730Epub\u4e66\u7c4d\u5931\u8d25");
                    }
                } else if (book.isCbz()) {
                    if (!BookController.extractCbz$default(this, book, false, 2, null)) {
                        return new Pair<Book, String>(book, "\u5bfc\u5165\u672c\u5730CBZ\u4e66\u7c4d\u5931\u8d25");
                    }
                } else if (book.isPdf() && !BookController.convertPdfToImage$default(this, book, false, 2, null)) {
                    return new Pair<Book, String>(book, "\u672c\u5730PDF\u4e66\u7c4d\u8f6c\u6362\u5931\u8d25");
                }
            } else if (StringsKt.indexOf$default((CharSequence)book.getBookUrl(), "webdav", 0, false, 6, null) >= 0) {
                File tempFile = new File(ExtKt.getWorkDir(book.getBookUrl()));
                if (!tempFile.exists()) {
                    return new Pair<Book, String>(book, "webdav\u4e66\u4ed3\u4e66\u7c4d\u4e0d\u5b58\u5728");
                }
                String relativeLocalFileUrl = "storage/data/" + userNameSpace + '/' + book.getName() + '_' + book.getAuthor() + '/' + tempFile.getName();
                book.setBookUrl(relativeLocalFileUrl);
                if (book.isEpub()) {
                    if (!BookController.extractEpub$default(this, book, false, 2, null)) {
                        return new Pair<Book, String>(book, "\u5bfc\u5165\u672c\u5730Epub\u4e66\u7c4d\u5931\u8d25");
                    }
                } else if (book.isCbz()) {
                    if (!BookController.extractCbz$default(this, book, false, 2, null)) {
                        return new Pair<Book, String>(book, "\u5bfc\u5165\u672c\u5730CBZ\u4e66\u7c4d\u5931\u8d25");
                    }
                } else if (book.isPdf() && !BookController.convertPdfToImage$default(this, book, false, 2, null)) {
                    return new Pair<Book, String>(book, "\u672c\u5730PDF\u4e66\u7c4d\u8f6c\u6362\u5931\u8d25");
                }
            }
        }
        book.setInShelf(true);
        if (existIndex >= 0) {
            List bookList = bookshelf.getList();
            Book existBook = bookshelf.getJsonObject(existIndex).mapTo(Book.class);
            book.setDurChapterIndex(existBook.getDurChapterIndex());
            book.setDurChapterTitle(existBook.getDurChapterTitle());
            book.setDurChapterTime(existBook.getDurChapterTime());
            CharSequence relativeLocalFileUrl = existBook.getDisplayCover();
            boolean bl2 = false;
            boolean bl3 = false;
            if (!(relativeLocalFileUrl == null || relativeLocalFileUrl.length() == 0)) {
                String string = existBook.getDisplayCover();
                Intrinsics.checkNotNull(string);
                if (StringsKt.startsWith$default(string, "/", false, 2, null)) {
                    String string2 = existBook.getDisplayCover();
                    Intrinsics.checkNotNull(string2);
                    if (!string2.equals(book.getDisplayCover())) {
                        String[] stringArray2 = new String[2];
                        stringArray2[0] = "storage";
                        Intrinsics.checkNotNull(existBook.getDisplayCover());
                        String cachePath = ExtKt.getWorkDir(stringArray2);
                        FileUtils.INSTANCE.deleteFile(cachePath);
                    }
                }
            }
            bookList.set(existIndex, JsonObject.mapFrom(book));
            bookshelf = new JsonArray(bookList);
        } else {
            bookshelf.add(JsonObject.mapFrom(book));
        }
        List<SearchBook> sourceList = CollectionsKt.listOf(book.toSearchBook());
        BookController.saveBookSources$default(this, book, sourceList, userNameSpace, false, 8, null);
        this.saveUserStorage(userNameSpace, "bookshelf", bookshelf);
        return new Pair<Book, Object>(book, null);
    }

    /*
     * Unable to fully structure code
     */
    private final Object saveLocalBookCover(Book var1_1, String var2_2, Continuation<? super Unit> var3_3) {
        if (!(var3_3 instanceof saveLocalBookCover.1)) ** GOTO lbl-1000
        var14_4 = var3_3;
        if ((var14_4.label & -2147483648) != 0) {
            var14_4.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var3_3){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return BookController.access$saveLocalBookCover(this.this$0, null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var15_6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                coverUrl = book.getDisplayCover();
                if (coverUrl == null || StringsKt.startsWith$default(coverUrl, "/", false, 2, null)) ** GOTO lbl41
                ext = this.getFileExt(coverUrl, "jpg");
                md5Encode = MD5Utils.INSTANCE.md5Encode(coverUrl).toString();
                var8_10 = new String[]{"storage", "assets", userNameSpace, "covers", md5Encode + '.' + ext};
                cachePath = ExtKt.getWorkDir(var8_10);
                cachedCoverUrl = "/assets/" + (String)userNameSpace + "/covers/" + md5Encode + '.' + ext;
                cacheFile = new File(cachePath);
                if (cacheFile.exists()) {
                    book.setCoverUrl(cachedCoverUrl);
                    return Unit.INSTANCE;
                }
                $continuation.L$0 = book;
                $continuation.L$1 = cachedCoverUrl;
                $continuation.L$2 = cacheFile;
                $continuation.label = 1;
                v0 = VertxCoroutineKt.awaitResult((Function1)new Function1<Handler<AsyncResult<HttpResponse<Buffer>>>, Unit>(this, coverUrl){
                    final /* synthetic */ BookController this$0;
                    final /* synthetic */ String $coverUrl;
                    {
                        this.this$0 = $receiver;
                        this.$coverUrl = $coverUrl;
                        super(1);
                    }

                    public final void invoke(@NotNull Handler<AsyncResult<HttpResponse<Buffer>>> handler2) {
                        Intrinsics.checkNotNullParameter(handler2, "handler");
                        BookController.access$getWebClient$p(this.this$0).getAbs(this.$coverUrl).timeout(3000L).send(handler2);
                    }
                }, $continuation);
                if (v0 == var15_6) {
                    return var15_6;
                }
                ** GOTO lbl37
            }
            case 1: {
                var9_12 = (File)$continuation.L$2;
                var8_10 = (String[])$continuation.L$1;
                var1_1 = (Book)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl37:
                // 2 sources

                v1 = bodyBytes = (var12_14 = (result = (HttpResponse)v0).bodyAsBuffer()) == null ? null : var12_14.getBytes();
                if (bodyBytes != null) {
                    FilesKt.writeBytes(var9_12, bodyBytes);
                    var1_1.setCoverUrl((String)var8_10);
                }
lbl41:
                // 4 sources

                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @Nullable
    public final Object saveBookCover(@NotNull Book var1_1, @NotNull String var2_2, @Nullable String var3_3, @NotNull Continuation<? super Unit> var4_4) {
        if (!(var4_4 instanceof saveBookCover.1)) ** GOTO lbl-1000
        var19_5 = var4_4;
        if ((var19_5.label & -2147483648) != 0) {
            var19_5.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var4_4){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.saveBookCover(null, null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var20_7 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                coverUrl = book.getDisplayCover();
                if (coverUrl == null || StringsKt.startsWith$default(coverUrl, "/", false, 2, null)) ** GOTO lbl65
                var7_9 = bookSource;
                bookSource = var7_9 == null ? this.getBookSourceStringBySourceURLOpt(book.getOrigin(), (String)userNameSpace) : var7_9;
                ext = this.getFileExt(coverUrl, "jpg");
                md5Encode = MD5Utils.INSTANCE.md5Encode(coverUrl).toString();
                var10_12 = new String[]{"storage", "assets", userNameSpace, "covers", md5Encode + '.' + ext};
                cachePath = ExtKt.getWorkDir(var10_12);
                cachedCoverUrl = "/assets/" + (String)userNameSpace + "/covers/" + md5Encode + '.' + ext;
                cacheFile = new File(cachePath);
                if (cacheFile.exists()) {
                    book.setCoverUrl(cachedCoverUrl);
                    return Unit.INSTANCE;
                }
                v0 = bookSource;
                Intrinsics.checkNotNull(v0);
                var13_15 = BookSource.Companion.fromJson-IoAF18A((String)v0);
                var14_18 = false;
                analyzeUrl = new AnalyzeUrl(coverUrl, null, null, null, null, null, (BaseSource)(Result.isFailure-impl(var13_15) != false ? null : var13_15), null, null, null, null, 1982, null);
                $continuation.L$0 = book;
                $continuation.L$1 = cachePath;
                $continuation.L$2 = cachedCoverUrl;
                $continuation.label = 1;
                v1 = analyzeUrl.getByteArrayAwait($continuation);
                ** if (v1 != var20_7) goto lbl39
lbl38:
                // 1 sources

                return var20_7;
lbl39:
                // 1 sources

                ** GOTO lbl49
            }
            case 1: {
                var10_12 = (String[])$continuation.L$2;
                var9_13 = (String)$continuation.L$1;
                var1_1 = (Book)$continuation.L$0;
                try {
                    ResultKt.throwOnFailure($result);
                    v1 = $result;
lbl49:
                    // 2 sources

                    var13_15 = v1;
                    var14_18 = false;
                    var15_20 = false;
                    it = (byte[])var13_15;
                    $i$a$-let-BookController$saveBookCover$2 = false;
                    FileUtils.INSTANCE.writeBytes(var9_13, it);
                    var1_1.setCoverUrl((String)var10_12);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
lbl65:
                // 3 sources

                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object saveBookCover$default(BookController bookController, Book book, String string, String string2, Continuation continuation, int n, Object object) {
        if ((n & 4) != 0) {
            string2 = null;
        }
        return bookController.saveBookCover(book, string, string2, continuation);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object setBookSource(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof setBookSource.1)) ** GOTO lbl-1000
        var19_3 = var2_2;
        if ((var19_3.label & -2147483648) != 0) {
            var19_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                Object L$5;
                Object L$6;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.setBookSource(null, this);
                }
            };
        }
        $result = $continuation.result;
        var20_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var20_5) {
                    return var20_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                var4_7 = null;
                var5_8 = null;
                var6_9 = null;
                if (context.request().method() == HttpMethod.POST) {
                    var7_10 = context.getBodyAsJson().getString("bookUrl");
                    Intrinsics.checkNotNullExpressionValue(var7_10, "context.bodyAsJson.getString(\"bookUrl\")");
                    bookUrl = var7_10;
                    var7_10 = context.getBodyAsJson().getString("newUrl");
                    Intrinsics.checkNotNullExpressionValue(var7_10, "context.bodyAsJson.getString(\"newUrl\")");
                    newBookUrl = var7_10;
                    var7_10 = context.getBodyAsJson().getString("bookSourceUrl");
                    Intrinsics.checkNotNullExpressionValue(var7_10, "context.bodyAsJson.getString(\"bookSourceUrl\")");
                    var6_9 = var7_10;
                } else {
                    var8_11 = context.queryParam("bookUrl");
                    Intrinsics.checkNotNullExpressionValue(var8_11, "context.queryParam(\"bookUrl\")");
                    var7_10 = CollectionsKt.firstOrNull(var8_11);
                    bookUrl = var7_10 == null ? "" : var7_10;
                    var8_11 = context.queryParam("newUrl");
                    Intrinsics.checkNotNullExpressionValue(var8_11, "context.queryParam(\"newUrl\")");
                    var7_10 = CollectionsKt.firstOrNull(var8_11);
                    newBookUrl = var7_10 == null ? "" : var7_10;
                    var8_11 = context.queryParam("bookSourceUrl");
                    Intrinsics.checkNotNullExpressionValue(var8_11, "context.queryParam(\"bookSourceUrl\")");
                    var7_10 = CollectionsKt.firstOrNull(var8_11);
                    bookSourceUrl /* !! */  = var7_10 == null ? "" : var7_10;
                }
                var7_10 = bookUrl;
                var8_12 = false;
                var9_14 = false;
                if (var7_10.length() == 0) {
                    return returnData.setErrorMsg("\u4e66\u7c4d\u94fe\u63a5\u4e0d\u80fd\u4e3a\u7a7a");
                }
                var7_10 = newBookUrl;
                var8_12 = false;
                var9_14 = false;
                if (var7_10.length() == 0) {
                    return returnData.setErrorMsg("\u65b0\u6e90\u4e66\u7c4d\u94fe\u63a5\u4e0d\u80fd\u4e3a\u7a7a");
                }
                var7_10 = bookSourceUrl /* !! */ ;
                var8_12 = false;
                var9_14 = false;
                if (var7_10.length() == 0) {
                    return returnData.setErrorMsg("\u4e66\u6e90\u94fe\u63a5\u4e0d\u80fd\u4e3a\u7a7a");
                }
                userNameSpace = this.getUserNameSpace(context);
                book = this.getShelfBookByURL(bookUrl, userNameSpace);
                if (book == null) {
                    return returnData.setErrorMsg("\u4e66\u7c4d\u4fe1\u606f\u9519\u8bef");
                }
                bookSourceString = this.getBookSourceStringBySourceURLOpt(bookSourceUrl /* !! */ , userNameSpace);
                searchBook = null;
                var11_17 = bookSourceString;
                var12_18 = false;
                var13_24 = 0;
                if (var11_17 == null || var11_17.length() == 0) {
                    var12_19 = new String[]{book.getName() + '_' + book.getAuthor(), "bookSource"};
                    localBookSourceList = ExtKt.asJsonArray(this.getUserStorage(userNameSpace, var12_19));
                    if (localBookSourceList != null && (var12_20 = 0) < (var13_24 = localBookSourceList.size())) {
                        do {
                            if (!(_searchBook = localBookSourceList.getJsonObject(i = var12_20++).mapTo(SearchBook.class)).getBookUrl().equals(newBookUrl)) continue;
                            searchBook = _searchBook.toBook();
                            break;
                        } while (var12_20 < var13_24);
                    }
                    if (searchBook == null) {
                        return returnData.setErrorMsg("\u4e66\u6e90\u4fe1\u606f\u9519\u8bef");
                    }
                }
                v1 = newBookInfo = new Ref.ObjectRef<T>();
                if (searchBook == null) ** GOTO lbl94
                v2 = searchBook;
                ** GOTO lbl125
lbl94:
                // 1 sources

                var12_21 = bookSourceString;
                var13_24 = 0;
                var14_25 = false;
                if (var12_21 == null || var12_21.length() == 0) {
                    return returnData.setErrorMsg("\u4e66\u6e90\u4fe1\u606f\u9519\u8bef");
                }
                var16_27 = v1;
                $continuation.L$0 = this;
                $continuation.L$1 = returnData;
                $continuation.L$2 = userNameSpace;
                $continuation.L$3 = book;
                $continuation.L$4 = bookSourceString;
                $continuation.L$5 = newBookInfo;
                $continuation.L$6 = var16_27;
                $continuation.label = 2;
                v3 = WebBook.getBookInfo$default(new WebBook(bookSourceString, this.getAppConfig().getDebugLog(), null, userNameSpace, 4, null), newBookUrl, false, (Continuation)$continuation, 2, null);
                if (v3 == var20_5) {
                    return var20_5;
                }
                ** GOTO lbl122
            }
            case 2: {
                var16_27 = (Ref.ObjectRef<T>)$continuation.L$6;
                var11_17 = (Ref.ObjectRef)$continuation.L$5;
                var9_15 = (String)$continuation.L$4;
                var8_13 = (Book)$continuation.L$3;
                var7_10 = (String)$continuation.L$2;
                var3_6 = (ReturnData)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v3 = $result;
lbl122:
                // 2 sources

                var17_28 = v3;
                v1 = var16_27;
                v2 = (Book)var17_28;
lbl125:
                // 2 sources

                v1.element = v2;
                $continuation.L$0 = this;
                $continuation.L$1 = var3_6;
                $continuation.L$2 = var7_10;
                $continuation.L$3 = var9_15;
                $continuation.L$4 = var11_17;
                $continuation.L$5 = null;
                $continuation.L$6 = null;
                $continuation.label = 3;
                v4 = this.editShelfBook(var8_13, (String)var7_10, (Function1<? super Book, Book>)new Function1<Book, Book>((Ref.ObjectRef<Book>)var11_17){
                    final /* synthetic */ Ref.ObjectRef<Book> $newBookInfo;
                    {
                        this.$newBookInfo = $newBookInfo;
                        super(1);
                    }

                    @NotNull
                    public final Book invoke(@NotNull Book existBook) {
                        Intrinsics.checkNotNullParameter(existBook, "existBook");
                        existBook.setOrigin(((Book)this.$newBookInfo.element).getOrigin());
                        existBook.setOriginName(((Book)this.$newBookInfo.element).getOriginName());
                        existBook.setBookUrl(((Book)this.$newBookInfo.element).getBookUrl());
                        existBook.setTocUrl(((Book)this.$newBookInfo.element).getTocUrl());
                        existBook.setInShelf(true);
                        CharSequence charSequence = existBook.getCoverUrl();
                        boolean bl = false;
                        boolean bl2 = false;
                        if (charSequence == null || charSequence.length() == 0) {
                            charSequence = ((Book)this.$newBookInfo.element).getCoverUrl();
                            bl = false;
                            bl2 = false;
                            if (!(charSequence == null || charSequence.length() == 0)) {
                                existBook.setCoverUrl(((Book)this.$newBookInfo.element).getCoverUrl());
                            }
                        }
                        BookControllerKt.access$getLogger$p().info("setBookSource: {}", (Object)existBook);
                        this.$newBookInfo.element = existBook;
                        return existBook;
                    }
                }, $continuation);
                if (v4 == var20_5) {
                    return var20_5;
                }
                ** GOTO lbl147
            }
            case 3: {
                var11_17 = (Ref.ObjectRef)$continuation.L$4;
                var9_15 = (String)$continuation.L$3;
                var7_10 = (String)$continuation.L$2;
                var3_6 = (ReturnData)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v4 = $result;
lbl147:
                // 3 sources

                var12_22 = var9_15;
                $continuation.L$0 = var3_6;
                $continuation.L$1 = var11_17;
                $continuation.L$2 = null;
                $continuation.L$3 = null;
                $continuation.L$4 = null;
                $continuation.label = 4;
                v5 = BookController.getLocalChapterList$default(this, (Book)var11_17.element, var12_22 == null ? "" : var12_22, true, (String)var7_10, false, null, $continuation, 48, null);
                ** if (v5 != var20_5) goto lbl158
lbl157:
                // 1 sources

                return var20_5;
lbl158:
                // 1 sources

                ** GOTO lbl169
            }
            case 4: {
                var11_17 = (Ref.ObjectRef)$continuation.L$1;
                var3_6 = (ReturnData)$continuation.L$0;
                try {
                    ResultKt.throwOnFailure($result);
                    v5 = $result;
                }
                catch (Exception var12_23) {
                    // empty catch block
                }
lbl169:
                // 3 sources

                return ReturnData.setData$default(var3_6, var11_17.element, null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object saveBookConfig(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof saveBookConfig.1)) ** GOTO lbl-1000
        var11_3 = var2_2;
        if ((var11_3.label & -2147483648) != 0) {
            var11_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.saveBookConfig(null, this);
                }
            };
        }
        $result = $continuation.result;
        var12_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var12_5) {
                    return var12_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                var4_7 = null;
                pdfImageWidth = new Ref.FloatRef();
                if (context.request().method() == HttpMethod.POST) {
                    var6_9 = context.getBodyAsJson().getString("bookUrl");
                    Intrinsics.checkNotNullExpressionValue(var6_9, "context.bodyAsJson.getString(\"bookUrl\")");
                    bookUrl = var6_9;
                    var6_9 = context.getBodyAsJson().getFloat("pdfImageWidth", Boxing.boxFloat(0.0f));
                    Intrinsics.checkNotNullExpressionValue(var6_9, "context.bodyAsJson.getFloat(\"pdfImageWidth\", 0f)");
                    pdfImageWidth.element = ((Number)var6_9).floatValue();
                } else {
                    var7_10 = context.queryParam("bookUrl");
                    Intrinsics.checkNotNullExpressionValue(var7_10, "context.queryParam(\"bookUrl\")");
                    var6_9 = CollectionsKt.firstOrNull(var7_10);
                    bookUrl = var6_9 == null ? "" : var6_9;
                    var7_10 = context.queryParam("pdfImageWidth");
                    Intrinsics.checkNotNullExpressionValue(var7_10, "context.queryParam(\"pdfImageWidth\")");
                    var6_9 = CollectionsKt.firstOrNull(var7_10);
                    if (var6_9 == null) {
                        v1 = 0.0f;
                    } else {
                        var8_13 = var6_9;
                        var9_16 = false;
                        var7_10 = Boxing.boxFloat(Float.parseFloat((String)var8_13));
                        v1 = var7_10 == null ? 0.0f : var7_10.floatValue();
                    }
                    pdfImageWidth.element = v1;
                }
                var6_9 = bookUrl;
                var7_11 = false;
                var8_14 = false;
                if (var6_9.length() == 0) {
                    return returnData.setErrorMsg("\u4e66\u7c4d\u94fe\u63a5\u4e0d\u80fd\u4e3a\u7a7a");
                }
                userNameSpace = this.getUserNameSpace(context);
                book = this.getShelfBookByURL(bookUrl, userNameSpace);
                if (book == null) {
                    return returnData.setErrorMsg("\u4e66\u7c4d\u4fe1\u606f\u9519\u8bef");
                }
                if (pdfImageWidth.element <= 0.0f) {
                    return returnData.setErrorMsg("pdf\u56fe\u7247\u5bbd\u5ea6\u9519\u8bef");
                }
                $continuation.L$0 = returnData;
                $continuation.L$1 = book;
                $continuation.L$2 = null;
                $continuation.label = 2;
                v2 = this.editShelfBook(book, userNameSpace, (Function1<? super Book, Book>)new Function1<Book, Book>(pdfImageWidth){
                    final /* synthetic */ Ref.FloatRef $pdfImageWidth;
                    {
                        this.$pdfImageWidth = $pdfImageWidth;
                        super(1);
                    }

                    @NotNull
                    public final Book invoke(@NotNull Book existBook) {
                        Intrinsics.checkNotNullParameter(existBook, "existBook");
                        existBook.setPdfImageWidth(this.$pdfImageWidth.element);
                        BookControllerKt.access$getLogger$p().info("saveBookConfig: {}", (Object)existBook);
                        return existBook;
                    }
                }, $continuation);
                if (v2 == var12_5) {
                    return var12_5;
                }
                ** GOTO lbl78
            }
            case 2: {
                var7_12 = (Book)$continuation.L$1;
                var3_6 = (ReturnData)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v2 = $result;
lbl78:
                // 2 sources

                return ReturnData.setData$default(var3_6, (var9_17 = (newBook = (Book)v2)) == null ? var7_12 : var9_17, null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object saveBookGroupId(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof saveBookGroupId.1)) ** GOTO lbl-1000
        var11_3 = var2_2;
        if ((var11_3.label & -2147483648) != 0) {
            var11_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.saveBookGroupId(null, this);
                }
            };
        }
        $result = $continuation.result;
        var12_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var12_5) {
                    return var12_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                var4_7 = null;
                groupId = new Ref.LongRef();
                if (context.request().method() == HttpMethod.POST) {
                    var6_9 = context.getBodyAsJson().getString("bookUrl");
                    Intrinsics.checkNotNullExpressionValue(var6_9, "context.bodyAsJson.getString(\"bookUrl\")");
                    bookUrl = var6_9;
                    var6_9 = context.getBodyAsJson().getLong("groupId", Boxing.boxLong(0L));
                    Intrinsics.checkNotNullExpressionValue(var6_9, "context.bodyAsJson.getLong(\"groupId\", 0)");
                    groupId.element = ((Number)var6_9).longValue();
                } else {
                    var7_10 = context.queryParam("bookUrl");
                    Intrinsics.checkNotNullExpressionValue(var7_10, "context.queryParam(\"bookUrl\")");
                    var6_9 = CollectionsKt.firstOrNull(var7_10);
                    bookUrl = var6_9 == null ? "" : var6_9;
                    var7_10 = context.queryParam("groupId");
                    Intrinsics.checkNotNullExpressionValue(var7_10, "context.queryParam(\"groupId\")");
                    var6_9 = CollectionsKt.firstOrNull(var7_10);
                    if (var6_9 == null) {
                        v1 = 0L;
                    } else {
                        var8_13 = var6_9;
                        var9_15 = false;
                        var7_10 = Boxing.boxLong(Long.parseLong((String)var8_13));
                        v1 = var7_10 == null ? 0L : var7_10.longValue();
                    }
                    groupId.element = v1;
                }
                var6_9 = bookUrl;
                var7_11 = false;
                var8_14 = false;
                if (var6_9.length() == 0) {
                    return returnData.setErrorMsg("\u4e66\u7c4d\u94fe\u63a5\u4e0d\u80fd\u4e3a\u7a7a");
                }
                userNameSpace = this.getUserNameSpace(context);
                book = this.getShelfBookByURL(bookUrl, userNameSpace);
                if (book == null) {
                    return returnData.setErrorMsg("\u4e66\u7c4d\u4fe1\u606f\u9519\u8bef");
                }
                if (groupId.element <= 0L) {
                    return returnData.setErrorMsg("\u5206\u7ec4\u4fe1\u606f\u9519\u8bef");
                }
                $continuation.L$0 = returnData;
                $continuation.L$1 = groupId;
                $continuation.L$2 = book;
                $continuation.label = 2;
                v2 = this.editShelfBook(book, userNameSpace, (Function1<? super Book, Book>)new Function1<Book, Book>(groupId){
                    final /* synthetic */ Ref.LongRef $groupId;
                    {
                        this.$groupId = $groupId;
                        super(1);
                    }

                    @NotNull
                    public final Book invoke(@NotNull Book existBook) {
                        Intrinsics.checkNotNullParameter(existBook, "existBook");
                        existBook.setGroup(this.$groupId.element);
                        BookControllerKt.access$getLogger$p().info("saveBookGroupId: {}", (Object)existBook);
                        return existBook;
                    }
                }, $continuation);
                if (v2 == var12_5) {
                    return var12_5;
                }
                ** GOTO lbl79
            }
            case 2: {
                var7_12 = (Book)$continuation.L$2;
                var5_8 = (Ref.LongRef)$continuation.L$1;
                var3_6 = (ReturnData)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v2 = $result;
lbl79:
                // 2 sources

                var7_12.setGroup(var5_8.element);
                return ReturnData.setData$default(var3_6, var7_12, null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object addBookGroupMulti(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof addBookGroupMulti.1)) ** GOTO lbl-1000
        var14_3 = var2_2;
        if ((var14_3.label & -2147483648) != 0) {
            var14_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                long J$0;
                int I$0;
                int I$1;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.addBookGroupMulti(null, this);
                }
            };
        }
        $result = $continuation.result;
        var15_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var15_5) {
                    return var15_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                var6_7 = context.getBodyAsJson().getLong("groupId", Boxing.boxLong(0L));
                Intrinsics.checkNotNullExpressionValue(var6_7, "context.bodyAsJson.getLong(\"groupId\", 0)");
                groupId = ((Number)var6_7).longValue();
                if (groupId <= 0L) {
                    return returnData.setErrorMsg("\u5206\u7ec4\u4fe1\u606f\u9519\u8bef");
                }
                userNameSpace = this.getUserNameSpace(context);
                var8_10 = 0;
                bookJsonArray = context.getBodyAsJson().getJsonArray("bookList", new JsonArray());
                var9_11 = bookJsonArray.size();
                if (var8_10 < var9_11) {
                    while (true) {
                        k = var8_10++;
                        var12_14 = book = bookJsonArray.getJsonObject(k).mapTo(Book.class);
                        Intrinsics.checkNotNullExpressionValue(var12_14, "book");
                        $continuation.L$0 = this;
                        $continuation.L$1 = returnData;
                        $continuation.L$2 = userNameSpace;
                        $continuation.L$3 = bookJsonArray;
                        $continuation.J$0 = groupId;
                        $continuation.I$0 = var8_10;
                        $continuation.I$1 = var9_11;
                        $continuation.label = 2;
                        v1 = this.editShelfBook(var12_14, userNameSpace, (Function1<? super Book, Book>)new Function1<Book, Book>(groupId){
                            final /* synthetic */ long $groupId;
                            {
                                this.$groupId = $groupId;
                                super(1);
                            }

                            @NotNull
                            public final Book invoke(@NotNull Book existBook) {
                                Intrinsics.checkNotNullParameter(existBook, "existBook");
                                existBook.setGroup(existBook.getGroup() | this.$groupId);
                                BookControllerKt.access$getLogger$p().info("saveBookGroupId: {}", (Object)existBook);
                                return existBook;
                            }
                        }, $continuation);
                        if (v1 != var15_5) continue;
                        return var15_5;
                    }
                }
                ** GOTO lbl66
            }
            case 2: {
                var9_11 = $continuation.I$1;
                var8_10 = $continuation.I$0;
                var4_8 = $continuation.J$0;
                bookJsonArray = (JsonArray)$continuation.L$3;
                userNameSpace = (String)$continuation.L$2;
                var3_6 = (ReturnData)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v1 = $result;
                if (var8_10 < var9_11) ** continue;
lbl66:
                // 2 sources

                return ReturnData.setData$default(var3_6, "", null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object removeBookGroupMulti(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof removeBookGroupMulti.1)) ** GOTO lbl-1000
        var14_3 = var2_2;
        if ((var14_3.label & -2147483648) != 0) {
            var14_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                long J$0;
                int I$0;
                int I$1;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.removeBookGroupMulti(null, this);
                }
            };
        }
        $result = $continuation.result;
        var15_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var15_5) {
                    return var15_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                var6_7 = context.getBodyAsJson().getLong("groupId", Boxing.boxLong(0L));
                Intrinsics.checkNotNullExpressionValue(var6_7, "context.bodyAsJson.getLong(\"groupId\", 0)");
                groupId = ((Number)var6_7).longValue();
                if (groupId <= 0L) {
                    return returnData.setErrorMsg("\u5206\u7ec4\u4fe1\u606f\u9519\u8bef");
                }
                userNameSpace = this.getUserNameSpace(context);
                var8_10 = 0;
                bookJsonArray = context.getBodyAsJson().getJsonArray("bookList", new JsonArray());
                var9_11 = bookJsonArray.size();
                if (var8_10 < var9_11) {
                    while (true) {
                        k = var8_10++;
                        var12_14 = book = bookJsonArray.getJsonObject(k).mapTo(Book.class);
                        Intrinsics.checkNotNullExpressionValue(var12_14, "book");
                        $continuation.L$0 = this;
                        $continuation.L$1 = returnData;
                        $continuation.L$2 = userNameSpace;
                        $continuation.L$3 = bookJsonArray;
                        $continuation.J$0 = groupId;
                        $continuation.I$0 = var8_10;
                        $continuation.I$1 = var9_11;
                        $continuation.label = 2;
                        v1 = this.editShelfBook(var12_14, userNameSpace, (Function1<? super Book, Book>)new Function1<Book, Book>(groupId){
                            final /* synthetic */ long $groupId;
                            {
                                this.$groupId = $groupId;
                                super(1);
                            }

                            @NotNull
                            public final Book invoke(@NotNull Book existBook) {
                                Intrinsics.checkNotNullParameter(existBook, "existBook");
                                existBook.setGroup(existBook.getGroup() ^ this.$groupId);
                                BookControllerKt.access$getLogger$p().info("saveBookGroupId: {}", (Object)existBook);
                                return existBook;
                            }
                        }, $continuation);
                        if (v1 != var15_5) continue;
                        return var15_5;
                    }
                }
                ** GOTO lbl66
            }
            case 2: {
                var9_11 = $continuation.I$1;
                var8_10 = $continuation.I$0;
                var4_8 = $continuation.J$0;
                bookJsonArray = (JsonArray)$continuation.L$3;
                userNameSpace = (String)$continuation.L$2;
                var3_6 = (ReturnData)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v1 = $result;
                if (var8_10 < var9_11) ** continue;
lbl66:
                // 2 sources

                return ReturnData.setData$default(var3_6, "", null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object deleteBook(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof deleteBook.1)) ** GOTO lbl-1000
        var17_3 = var2_2;
        if ((var17_3.label & -2147483648) != 0) {
            var17_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.deleteBook(null, this);
                }
            };
        }
        $result = $continuation.result;
        var18_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var18_5) {
                    return var18_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                book = context.getBodyAsJson().mapTo(Book.class);
                userNameSpace = this.getUserNameSpace(context);
                bookshelf = ExtKt.asJsonArray(this.getUserStorage(userNameSpace, var7_9 = new String[]{"bookshelf"}));
                if (bookshelf == null) {
                    bookshelf = new JsonArray();
                }
                existIndex = -1;
                bookName = "";
                bookAuthor = "";
                var10_14 = "";
                var11_15 = 0;
                var12_17 = bookshelf.size();
                if (var11_15 < var12_17) {
                    do {
                        i = var11_15++;
                        var14_21 = bookshelf.getJsonObject(i).getString("name", "");
                        Intrinsics.checkNotNullExpressionValue(var14_21, "bookshelf.getJsonObject(i).getString(\"name\", \"\")");
                        bookName = var14_21;
                        var14_21 = bookshelf.getJsonObject(i).getString("author", "");
                        Intrinsics.checkNotNullExpressionValue(var14_21, "bookshelf.getJsonObject(i).getString(\"author\", \"\")");
                        bookAuthor = var14_21;
                        var14_21 = bookshelf.getJsonObject(i).getString("bookUrl", "");
                        Intrinsics.checkNotNullExpressionValue(var14_21, "bookshelf.getJsonObject(i).getString(\"bookUrl\", \"\")");
                        bookUrl = var14_21;
                        if (bookUrl.equals(book.getBookUrl())) {
                            existIndex = i;
                            break;
                        }
                        if (!bookName.equals(book.getName()) || !bookAuthor.equals(book.getAuthor())) continue;
                        existIndex = i;
                        break;
                    } while (var11_15 < var12_17);
                }
                if (existIndex < 0) {
                    return returnData.setErrorMsg("\u4e66\u67b6\u4e66\u7c4d\u4e0d\u5b58\u5728");
                }
                existBook = bookshelf.getJsonObject(existIndex);
                bookshelf.remove(existIndex);
                this.saveUserStorage(userNameSpace, "bookshelf", bookshelf);
                i = new String[]{"storage", "data", userNameSpace, bookName + '_' + bookAuthor};
                localBookPath = new File(ExtKt.getWorkDir((String[])i));
                ExtKt.deleteRecursively(localBookPath);
                i = existBook.getString("coverUrl");
                var14_22 = false;
                var15_24 = false;
                if (!(i == null || i.length() == 0)) {
                    v1 = existBook.getString("coverUrl");
                    Intrinsics.checkNotNull(v1);
                    if (StringsKt.startsWith$default(v1, "/", false, 2, null)) {
                        var14_23 = new String[2];
                        var14_23[0] = "storage";
                        Intrinsics.checkNotNull(existBook.getString("coverUrl"));
                        cachePath = ExtKt.getWorkDir(var14_23);
                        FileUtils.INSTANCE.deleteFile(cachePath);
                    }
                }
                return ReturnData.setData$default(returnData, "\u5220\u9664\u4e66\u7c4d\u6210\u529f", null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object deleteBooks(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof deleteBooks.1)) ** GOTO lbl-1000
        var17_3 = var2_2;
        if ((var17_3.label & -2147483648) != 0) {
            var17_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.deleteBooks(null, this);
                }
            };
        }
        $result = $continuation.result;
        var18_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var18_5) {
                    return var18_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                bookJsonArray = context.getBodyAsJsonArray();
                userNameSpace = this.getUserNameSpace(context);
                bookshelf = ExtKt.asJsonArray(this.getUserStorage(userNameSpace, var7_9 = new String[]{"bookshelf"}));
                if (bookshelf == null) {
                    bookshelf = new JsonArray();
                }
                var8_11 = 0;
                infoMap = new LinkedHashMap<K, V>();
                var8_11 = 0;
                var9_13 = bookJsonArray.size();
                if (var8_11 < var9_13) {
                    do {
                        i = var8_11++;
                        var11_17 = bookJsonArray.getJsonObject(i).getString("bookUrl", "");
                        Intrinsics.checkNotNullExpressionValue(var11_17, "bookJsonArray.getJsonObject(i).getString(\"bookUrl\", \"\")");
                        infoMap.put(var11_17, Boxing.boxInt(i));
                        infoMap.put(bookJsonArray.getJsonObject(i).getString("name", "") + '_' + bookshelf.getJsonObject(i).getString("author", ""), Boxing.boxInt(i));
                    } while (var8_11 < var9_13);
                }
                var9_14 = bookshelf.iterator();
                Intrinsics.checkNotNullExpressionValue(var9_14, "bookshelf.iterator()");
                iterator = var9_14;
                while (iterator.hasNext()) {
                    i = iterator.next();
                    if (i == null) {
                        throw new NullPointerException("null cannot be cast to non-null type io.vertx.core.json.JsonObject");
                    }
                    book = (JsonObject)i;
                    bookName = book.getString("name", "");
                    bookAuthor = book.getString("author", "");
                    var14_20 = bookUrl = book.getString("bookUrl", "");
                    Intrinsics.checkNotNullExpressionValue(var14_20, "bookUrl");
                    existIndex = ((Number)infoMap.getOrDefault(var14_20, infoMap.getOrDefault(bookName + '_' + bookAuthor, Boxing.boxInt(-1)))).intValue();
                    if (existIndex < 0) continue;
                    iterator.remove();
                    var15_21 = new String[]{"storage", "data", userNameSpace, bookName + '_' + bookAuthor};
                    localBookPath = new File(ExtKt.getWorkDir(var15_21));
                    ExtKt.deleteRecursively(localBookPath);
                }
                this.saveUserStorage(userNameSpace, "bookshelf", bookshelf);
                return ReturnData.setData$default(returnData, "", null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @Nullable
    public final Object saveBookInfoCache(@NotNull List<Book> bookList, @NotNull Continuation<? super List<Book>> $completion) {
        int n;
        int n2;
        if (bookList.size() > 0 && (n2 = 0) < (n = bookList.size())) {
            do {
                int i = n2++;
                Book book = bookList.get(i);
                String string = book.getBookUrl();
                Map<String, Object> map = JsonObject.mapFrom(book).getMap();
                Intrinsics.checkNotNullExpressionValue(map, "mapFrom(book).map");
                this.bookInfoCache.put(string, ExtKt.jsonEncode$default(map, false, 2, null));
            } while (n2 < n);
        }
        return bookList;
    }

    @Nullable
    public final Object mergeBookCacheInfo(@NotNull Book book, @NotNull Continuation<? super Book> $completion) {
        Book cacheInfo;
        Book book2;
        String[] stringArray = this.bookInfoCache.getAsString(book.getBookUrl());
        if (stringArray == null) {
            book2 = null;
        } else {
            Map<String, Object> map = ExtKt.toMap(stringArray);
            if (map == null) {
                book2 = null;
            } else {
                Map<String, Object> $this$toDataClass$iv = map;
                boolean $i$f$toDataClass = false;
                Map<String, Object> $this$convert$iv$iv = $this$toDataClass$iv;
                boolean $i$f$convert = false;
                String json$iv$iv = $this$convert$iv$iv instanceof String ? (String)((Object)$this$convert$iv$iv) : ExtKt.getGson().toJson($this$convert$iv$iv);
                book2 = cacheInfo = (Book)ExtKt.getGson().fromJson(json$iv$iv, new TypeToken<Book>(){}.getType());
            }
        }
        if (cacheInfo != null) {
            stringArray = new String[]{"name", "author", "coverUrl", "tocUrl", "intro", "latestChapterTitle", "wordCount"};
            return ExtKt.fillData(book, cacheInfo, CollectionsKt.listOf(stringArray));
        }
        return book;
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getBookShelfBooks(boolean var1_1, @NotNull String var2_2, @NotNull Continuation<? super List<Book>> var3_3) {
        if (!(var3_3 instanceof getBookShelfBooks.1)) ** GOTO lbl-1000
        var10_4 = var3_3;
        if ((var10_4.label & -2147483648) != 0) {
            var10_4.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var3_3){
                Object L$0;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.getBookShelfBooks(false, null, this);
                }
            };
        }
        $result = $continuation.result;
        var11_6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                bookshelf = new Ref.ObjectRef<JsonArray>();
                var5_8 = new String[]{"bookshelf"};
                bookshelf.element = ExtKt.asJsonArray(this.getUserStorage(userNameSpace, var5_8));
                if (bookshelf.element == null) {
                    var5_9 = false;
                    return new ArrayList<E>();
                }
                if (((JsonArray)bookshelf.element).size() == 0) {
                    var5_10 = false;
                    return new ArrayList<E>();
                }
                bookList = new Ref.ObjectRef<ArrayList<Book>>();
                var6_11 = false;
                bookList.element = new ArrayList<E>();
                concurrentCount = 16;
                mutex = MutexKt.Mutex$default(false, 1, null);
                syncMutex = MutexKt.Mutex$default(false, 1, null);
                $continuation.L$0 = bookList;
                $continuation.label = 1;
                v0 = this.limitConcurrent(concurrentCount, 0, ((JsonArray)bookshelf.element).size(), (Function3<? super CoroutineScope, ? super Integer, ? super Continuation<Object>, ? extends Object>)new Function3<CoroutineScope, Integer, Continuation<? super Object>, Object>(bookshelf, refresh != false, this, (String)userNameSpace, syncMutex, bookList, mutex, null){
                    Object L$0;
                    int label;
                    /* synthetic */ int I$0;
                    final /* synthetic */ Ref.ObjectRef<JsonArray> $bookshelf;
                    final /* synthetic */ boolean $refresh;
                    final /* synthetic */ BookController this$0;
                    final /* synthetic */ String $userNameSpace;
                    final /* synthetic */ Mutex $syncMutex;
                    final /* synthetic */ Ref.ObjectRef<ArrayList<Book>> $bookList;
                    final /* synthetic */ Mutex $mutex;
                    {
                        this.$bookshelf = $bookshelf;
                        this.$refresh = $refresh;
                        this.this$0 = $receiver;
                        this.$userNameSpace = $userNameSpace;
                        this.$syncMutex = $syncMutex;
                        this.$bookList = $bookList;
                        this.$mutex = $mutex;
                        super(3, $completion);
                    }

                    /*
                     * WARNING - Removed try catching itself - possible behaviour change.
                     * Unable to fully structure code
                     */
                    @Nullable
                    public final Object invokeSuspend(@NotNull Object var1_1) {
                        var6_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        switch (this.label) {
                            case 0: {
                                ResultKt.throwOnFailure(var1_1);
                                it = this.I$0;
                                book = new Ref.ObjectRef<Book>();
                                book.element = ((JsonArray)this.$bookshelf.element).getJsonObject(it).mapTo(Book.class);
                                ((Book)book.element).setInShelf(true);
                                if (((Book)book.element).isLocalBook() || !((Book)book.element).getCanUpdate() || !this.$refresh) ** GOTO lbl31
                                bookSource = new Ref.ObjectRef<String>();
                                bookSource.element = this.this$0.getBookSourceStringBySourceURLOpt(((Book)book.element).getOrigin(), this.$userNameSpace);
                                if (bookSource.element == null) ** GOTO lbl31
                                this.L$0 = book;
                                this.label = 1;
                                v0 = BuildersKt.withContext(Dispatchers.getIO(), (Function2)new Function2<CoroutineScope, Continuation<? super List<? extends BookChapter>>, Object>(this.this$0, book, bookSource, this.$refresh, this.$userNameSpace, this.$mutex, null){
                                    int label;
                                    final /* synthetic */ BookController this$0;
                                    final /* synthetic */ Ref.ObjectRef<Book> $book;
                                    final /* synthetic */ Ref.ObjectRef<String> $bookSource;
                                    final /* synthetic */ boolean $refresh;
                                    final /* synthetic */ String $userNameSpace;
                                    final /* synthetic */ Mutex $mutex;
                                    {
                                        this.this$0 = $receiver;
                                        this.$book = $book;
                                        this.$bookSource = $bookSource;
                                        this.$refresh = $refresh;
                                        this.$userNameSpace = $userNameSpace;
                                        this.$mutex = $mutex;
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
                                                T t = this.$book.element;
                                                Intrinsics.checkNotNullExpressionValue(t, "book");
                                                this.label = 1;
                                                Object object3 = this.this$0.getLocalChapterList((Book)t, (String)this.$bookSource.element, this.$refresh, this.$userNameSpace, false, this.$mutex, this);
                                                if (object3 != object2) return object3;
                                                return object2;
                                            }
                                            case 1: {
                                                void $result;
                                                ResultKt.throwOnFailure($result);
                                                Object object3 = $result;
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
                                    public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super List<BookChapter>> p2) {
                                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                                    }
                                }, this);
                                ** if (v0 != var6_2) goto lbl20
lbl19:
                                // 1 sources

                                return var6_2;
lbl20:
                                // 1 sources

                                ** GOTO lbl31
                            }
                            case 1: {
                                book = (Ref.ObjectRef<Book>)this.L$0;
                                try {
                                    ResultKt.throwOnFailure($result);
                                    v0 = $result;
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
lbl31:
                                // 6 sources

                                this.L$0 = book;
                                this.label = 2;
                                v1 = Mutex.DefaultImpls.lock$default(this.$syncMutex, null, this, 1, null);
                                ** if (v1 != var6_2) goto lbl37
lbl36:
                                // 1 sources

                                return var6_2;
lbl37:
                                // 1 sources

                                ** GOTO lbl44
                            }
                            case 2: {
                                book = (Ref.ObjectRef)this.L$0;
                                try {
                                    ResultKt.throwOnFailure($result);
                                    v1 = $result;
lbl44:
                                    // 2 sources

                                    var4_7 = ((ArrayList)this.$bookList.element).add(book.element);
                                }
                                catch (Throwable var5_8) {
                                    throw var5_8;
                                }
                                finally {
                                    Mutex.DefaultImpls.unlock$default(this.$syncMutex, null, 1, null);
                                }
                                return Boxing.boxBoolean(var4_7 != false);
                            }
                        }
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }

                    @Nullable
                    public final Object invoke(@NotNull CoroutineScope p1, int p2, @Nullable Continuation<Object> p3) {
                        Function3<CoroutineScope, Integer, Continuation<? super Object>, Object> function3 = new /* invalid duplicate definition of identical inner class */;
                        function3.I$0 = p2;
                        return function3.invokeSuspend(Unit.INSTANCE);
                    }
                }, $continuation);
                if (v0 == var11_6) {
                    return var11_6;
                }
                ** GOTO lbl37
            }
            case 1: {
                var5_8 = (String[])$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl37:
                // 2 sources

                return var5_8.element;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object getBookShelfBooks$default(BookController bookController, boolean bl, String string, Continuation continuation, int n, Object object) {
        if ((n & 1) != 0) {
            bl = false;
        }
        return bookController.getBookShelfBooks(bl, string, continuation);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object getLocalChapterList(@NotNull Book var1_1, @Nullable String var2_2, boolean var3_3, @NotNull String var4_4, boolean var5_5, @Nullable Mutex var6_6, @NotNull Continuation<? super List<BookChapter>> var7_7) {
        if (!(var7_7 instanceof getLocalChapterList.1)) ** GOTO lbl-1000
        var22_8 = var7_7;
        if ((var22_8.label & -2147483648) != 0) {
            var22_8.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var7_7){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                Object L$5;
                Object L$6;
                boolean Z$0;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.getLocalChapterList(null, null, false, null, false, null, this);
                }
            };
        }
        $result = $continuation.result;
        var23_10 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                md5Encode = MD5Utils.INSTANCE.md5Encode(book.getBookUrl()).toString();
                var9_12 = null;
                bookChaptersCache = this.getBookChaptersCache((String)userNameSpace);
                if (book.isInShelf()) {
                    var11_14 /* !! */  = new String[]{book.getName() + '_' + book.getAuthor(), md5Encode};
                    var9_12 = ExtKt.asJsonArray(this.getUserStorage(userNameSpace, var11_14 /* !! */ ));
                } else {
                    chapterList = ExtKt.asJsonArray(bookChaptersCache.getAsString(book.getName() + '_' + book.getAuthor() + md5Encode));
                }
                if (chapterList != null && refresh == false) break;
                var11_14 /* !! */  = null;
                book.setRootDir(ExtKt.getWorkDir$default(null, 1, null));
                book.setUserNameSpace((String)userNameSpace);
                if (book.isLocalBook()) {
                    if (book.isEpub() && !this.extractEpub((Book)book, refresh != false)) {
                        throw new Exception("Epub\u4e66\u7c4d\u89e3\u538b\u5931\u8d25");
                    }
                    if (book.isCbz() && !this.extractCbz((Book)book, refresh != false)) {
                        throw new Exception("CBZ\u4e66\u7c4d\u89e3\u538b\u5931\u8d25");
                    }
                    if (book.isPdf() && !this.convertPdfToImage((Book)book, refresh != false)) {
                        throw new Exception("PDF\u4e66\u7c4d\u8f6c\u6362\u5931\u8d25");
                    }
                    var11_14 /* !! */  = LocalBook.INSTANCE.getChapterList((Book)book);
                    break;
                }
                var12_15 = (CharSequence)bookSource;
                var13_19 = false;
                var14_24 = false;
                if (var12_15 == null || var12_15.length() == 0) ** GOTO lbl79
                bookSourceObject = null;
                var13_20 = BookSource.Companion.fromJson-IoAF18A((String)bookSource);
                var14_24 = false;
                bookSourceObject = Result.isFailure-impl(var13_20) != false ? null : var13_20;
                if ((var13_20 = (BookSource)bookSourceObject) != null && (var14_25 = var13_20.getRuleToc()) != null && (var15_30 = var14_25.getPreUpdateJs()) != null) {
                    var16_34 = var15_30;
                    var17_35 = false;
                    var18_36 = false;
                    it = var16_34;
                    $i$a$-let-BookController$getLocalChapterList$2 = false;
                    AnalyzeRule.evalJS$default(new AnalyzeRule((RuleDataInterface)book, (BaseSource)bookSourceObject, null, 4, null), it, null, 2, null);
                }
                if (!StringsKt.isBlank(book.getTocUrl())) ** GOTO lbl79
                $continuation.L$0 = this;
                $continuation.L$1 = book;
                $continuation.L$2 = bookSource;
                $continuation.L$3 = userNameSpace;
                $continuation.L$4 = mutex;
                $continuation.L$5 = md5Encode;
                $continuation.L$6 = bookChaptersCache;
                $continuation.Z$0 = debugLog;
                $continuation.label = 1;
                v0 = WebBook.getBookInfo$default(new WebBook((String)bookSource, debugLog != false, null, (String)userNameSpace, 4, null), (Book)book, false, (Continuation)$continuation, 2, null);
                ** if (v0 != var23_10) goto lbl65
lbl64:
                // 1 sources

                return var23_10;
lbl65:
                // 1 sources

                ** GOTO lbl79
            }
            case 1: {
                var5_5 = $continuation.Z$0;
                var10_13 = (ACache)$continuation.L$6;
                var8_11 = (String)$continuation.L$5;
                var6_6 = (Mutex)$continuation.L$4;
                var4_4 = (String)$continuation.L$3;
                var2_2 = (String)$continuation.L$2;
                var1_1 = (Book)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl79:
                // 4 sources

                v1 = var2_2;
                Intrinsics.checkNotNull(v1);
                $continuation.L$0 = this;
                $continuation.L$1 = var1_1;
                $continuation.L$2 = var2_2;
                $continuation.L$3 = var4_4;
                $continuation.L$4 = var6_6;
                $continuation.L$5 = var8_11;
                $continuation.L$6 = var10_13;
                $continuation.label = 2;
                v2 = new WebBook(v1, var5_5 != false, null, var4_4, 4, null).getChapterList(var1_1, $continuation);
                ** if (v2 != var23_10) goto lbl92
lbl91:
                // 1 sources

                return var23_10;
lbl92:
                // 1 sources

                ** GOTO lbl105
            }
            case 2: {
                var10_13 = (ACache)$continuation.L$6;
                var8_11 = (String)$continuation.L$5;
                var6_6 = (Mutex)$continuation.L$4;
                var4_4 = (String)$continuation.L$3;
                var2_2 = (String)$continuation.L$2;
                var1_1 = (Book)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                try {
                    ResultKt.throwOnFailure($result);
                    v2 = $result;
lbl105:
                    // 2 sources

                    newChapterList = (List)v2;
                    break;
                }
                catch (Exception e) {
                    var13_21 = var2_2;
                    var14_26 = false;
                    var15_31 = false;
                    if (!(var13_21 == null || var13_21.length() == 0)) {
                        var14_27 = BookSource.Companion.fromJson-IoAF18A(var2_2);
                        var15_31 = false;
                        bookSourceObject = (BookSource)(Result.isFailure-impl(var14_27) != false ? null : var14_27);
                        if (bookSourceObject != null) {
                            var15_32 = new Pair[]{TuplesKt.to("sourceUrl", bookSourceObject.getBookSourceUrl()), TuplesKt.to("time", Boxing.boxLong(System.currentTimeMillis())), TuplesKt.to("error", e.toString())};
                            info = MapsKt.mutableMapOf(var15_32);
                            this.addInvalidBookSource(bookSourceObject.getBookSourceUrl(), info, var4_4);
                        }
                    }
                    var13_21 = var6_6;
                    if (var13_21 == null) ** GOTO lbl145
                    $continuation.L$0 = this;
                    $continuation.L$1 = var1_1;
                    $continuation.L$2 = var4_4;
                    $continuation.L$3 = var6_6;
                    $continuation.L$4 = e;
                    $continuation.L$5 = null;
                    $continuation.L$6 = null;
                    $continuation.label = 3;
                    v3 = Mutex.DefaultImpls.lock$default((Mutex)var13_21, null, $continuation, 1, null);
                    ** if (v3 != var23_10) goto lbl134
lbl133:
                    // 1 sources

                    return var23_10;
lbl134:
                    // 1 sources

                    ** GOTO lbl145
                }
            }
            case 3: {
                var12_17 = (Exception)$continuation.L$4;
                var6_6 = (Mutex)$continuation.L$3;
                var4_4 = (String)$continuation.L$2;
                var1_1 = (Book)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v3 = $result;
lbl145:
                // 3 sources

                var1_1.setLastCheckError(var12_17.toString());
                $continuation.L$0 = var6_6;
                $continuation.L$1 = var12_17;
                $continuation.L$2 = null;
                $continuation.L$3 = null;
                $continuation.L$4 = null;
                $continuation.L$5 = null;
                $continuation.L$6 = null;
                $continuation.label = 4;
                v4 = this.editShelfBook(var1_1, var4_4, (Function1<? super Book, Book>)new Function1<Book, Book>(var12_17){
                    final /* synthetic */ Exception $e;
                    {
                        this.$e = $e;
                        super(1);
                    }

                    @NotNull
                    public final Book invoke(@NotNull Book existBook) {
                        Intrinsics.checkNotNullParameter(existBook, "existBook");
                        existBook.setLastCheckError(this.$e.toString());
                        return existBook;
                    }
                }, $continuation);
                ** if (v4 != var23_10) goto lbl157
lbl156:
                // 1 sources

                return var23_10;
lbl157:
                // 1 sources

                ** GOTO lbl173
            }
            case 4: {
                var12_17 = (Exception)$continuation.L$1;
                var6_6 = (Mutex)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v4 = $result;
                ** GOTO lbl173
                {
                    catch (Throwable var13_22) {
                        throw var13_22;
                    }
                }
                finally {
                    var13_21 = var6_6;
                    if (var13_21 != null) {
                        Mutex.DefaultImpls.unlock$default((Mutex)var13_21, null, 1, null);
                    }
                }
lbl173:
                // 2 sources

                throw var12_17;
            }
        }
        if (var1_1.isInShelf()) {
            var12_15 = new String[]{var1_1.getName() + '_' + var1_1.getAuthor(), var8_11};
            this.saveUserStorage(var4_4, ExtKt.getRelativePath((String[])var12_15), newChapterList);
        } else {
            var10_13.put(var1_1.getName() + '_' + var1_1.getAuthor() + var8_11, ExtKt.jsonEncode$default(newChapterList, false, 2, null), 3600);
        }
        $continuation.L$0 = newChapterList;
        $continuation.L$1 = null;
        $continuation.L$2 = null;
        $continuation.L$3 = null;
        $continuation.L$4 = null;
        $continuation.L$5 = null;
        $continuation.L$6 = null;
        $continuation.label = 5;
        v5 = this.saveShelfBookLatestChapter(var1_1, newChapterList, var4_4, var6_6, $continuation);
        if (v5 == var23_10) {
            return var23_10;
        }
        ** GOTO lbl195
        {
            case 5: {
                newChapterList = (List)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v5 = $result;
lbl195:
                // 2 sources

                return newChapterList;
            }
        }
        var12_18 = 0;
        localChapterList = new ArrayList<BookChapter>();
        var12_18 = 0;
        var13_23 = var9_12.size();
        if (var12_18 < var13_23) {
            do {
                i = var12_18++;
                _chapter = var9_12.getJsonObject(i).mapTo(BookChapter.class);
                localChapterList.add(_chapter);
            } while (var12_18 < var13_23);
        }
        return localChapterList;
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object getLocalChapterList$default(BookController bookController, Book book, String string, boolean bl, String string2, boolean bl2, Mutex mutex, Continuation continuation, int n, Object object) {
        if ((n & 4) != 0) {
            bl = false;
        }
        if ((n & 0x10) != 0) {
            bl2 = true;
        }
        if ((n & 0x20) != 0) {
            mutex = null;
        }
        return bookController.getLocalChapterList(book, string, bl, string2, bl2, mutex, continuation);
    }

    @Nullable
    public final Object getBookSourceString(@NotNull RoutingContext context, @NotNull String sourceUrl, boolean withExploreUrl, @NotNull Continuation<? super String> $completion) {
        JsonObject bookSource;
        String bookSourceString = null;
        if (context.request().method() == HttpMethod.POST && (bookSource = context.getBodyAsJson().getJsonObject("bookSource")) != null) {
            bookSourceString = bookSource.toString();
        }
        String userNameSpace = this.getUserNameSpace(context);
        CharSequence charSequence = bookSourceString;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence == null || charSequence.length() == 0) {
            CharSequence charSequence2;
            String bookSourceUrl = null;
            if (context.request().method() == HttpMethod.POST) {
                charSequence2 = context.getBodyAsJson().getString("bookSourceUrl", "");
                Intrinsics.checkNotNullExpressionValue(charSequence2, "context.bodyAsJson.getString(\"bookSourceUrl\", \"\")");
                bookSourceUrl = charSequence2;
            } else {
                List<String> list2 = context.queryParam("bookSourceUrl");
                Intrinsics.checkNotNullExpressionValue(list2, "context.queryParam(\"bookSourceUrl\")");
                charSequence2 = CollectionsKt.firstOrNull(list2);
                bookSourceUrl = charSequence2 == null ? "" : charSequence2;
            }
            charSequence2 = bookSourceUrl;
            bl2 = false;
            if (!StringsKt.isBlank(charSequence2)) {
                bookSourceString = this.getBookSourceStringBySourceURLOpt(bookSourceUrl, userNameSpace);
            }
        }
        charSequence = bookSourceString;
        bl = false;
        bl2 = false;
        if (charSequence == null || charSequence.length() == 0) {
            charSequence = sourceUrl;
            bl = false;
            bl2 = false;
            if (!(charSequence == null || charSequence.length() == 0)) {
                bookSourceString = this.getBookSourceStringBySourceURLOpt(sourceUrl, userNameSpace);
            }
        }
        return bookSourceString;
    }

    public static /* synthetic */ Object getBookSourceString$default(BookController bookController, RoutingContext routingContext, String string, boolean bl, Continuation continuation, int n, Object object) {
        if ((n & 2) != 0) {
            string = "";
        }
        if ((n & 4) != 0) {
            bl = false;
        }
        return bookController.getBookSourceString(routingContext, string, bl, continuation);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public final String getBookSourceStringBySourceURLOpt(@NotNull String sourceUrl, @NotNull String userNameSpace) {
        Intrinsics.checkNotNullParameter(sourceUrl, "sourceUrl");
        Intrinsics.checkNotNullParameter(userNameSpace, "userNameSpace");
        if (StringsKt.isBlank(sourceUrl)) {
            return null;
        }
        String[] stringArray = new String[]{"data", userNameSpace, "bookSource"};
        File file = ExtKt.getStorageFile$default(stringArray, null, 2, null);
        if (!file.exists()) {
            stringArray = new String[]{"data", "default", "bookSource"};
            file = ExtKt.getStorageFile$default(stringArray, null, 2, null);
            if (!file.exists()) {
                return null;
            }
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonFactory factory = objectMapper.getFactory();
            Ref.ObjectRef<String> bookSourceString = new Ref.ObjectRef<String>();
            Closeable closeable = factory.createParser(file);
            boolean bl = false;
            boolean bl2 = false;
            Throwable throwable = null;
            try {
                JsonParser parser = (JsonParser)closeable;
                boolean bl3 = false;
                if (parser.nextToken() == JsonToken.START_ARRAY) {
                    while (parser.nextToken() != JsonToken.END_ARRAY) {
                        if (parser.currentToken() != JsonToken.START_OBJECT) continue;
                        Object t = parser.readValueAsTree();
                        Intrinsics.checkNotNullExpressionValue(t, "parser.readValueAsTree()");
                        JsonNode jsonNode = (JsonNode)t;
                        if (!sourceUrl.equals(jsonNode.get("bookSourceUrl").asText())) continue;
                        bookSourceString.element = jsonNode.toString();
                        break;
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
            BookControllerKt.access$getLogger$p().info((Function0<? extends Object>)new Function0<Object>(bookSourceString){
                final /* synthetic */ Ref.ObjectRef<String> $bookSourceString;
                {
                    this.$bookSourceString = $bookSourceString;
                    super(0);
                }

                @Nullable
                public final Object invoke() {
                    return this.$bookSourceString.element;
                }
            });
            return (String)bookSourceString.element;
        }
        catch (Exception e) {
            BookControllerKt.access$getLogger$p().error("\u89e3\u6790\u6587\u4ef6\u5185\u5bb9\u51fa\u9519: {}  \u6587\u4ef6: \n{}", (Object)e, (Object)file);
            throw e;
        }
    }

    @Nullable
    public final Book getShelfBookByURL(@NotNull String url2, @NotNull String userNameSpace) {
        Intrinsics.checkNotNullParameter(url2, "url");
        Intrinsics.checkNotNullParameter(userNameSpace, "userNameSpace");
        CharSequence charSequence = url2;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return null;
        }
        String[] stringArray = new String[]{"bookshelf"};
        JsonArray bookshelf = ExtKt.asJsonArray(this.getUserStorage(userNameSpace, stringArray));
        if (bookshelf == null) {
            return null;
        }
        int n = 0;
        int n2 = bookshelf.size();
        if (n < n2) {
            do {
                int i = n++;
                Map<String, Object> map = bookshelf.getJsonObject(i).getMap();
                Intrinsics.checkNotNullExpressionValue(map, "bookshelf.getJsonObject(i).map");
                Map<String, Object> $this$toDataClass$iv = map;
                boolean $i$f$toDataClass = false;
                Map<String, Object> $this$convert$iv$iv = $this$toDataClass$iv;
                boolean $i$f$convert = false;
                String json$iv$iv = $this$convert$iv$iv instanceof String ? (String)((Object)$this$convert$iv$iv) : ExtKt.getGson().toJson($this$convert$iv$iv);
                Book _book = (Book)ExtKt.getGson().fromJson(json$iv$iv, new TypeToken<Book>(){}.getType());
                if (!_book.getBookUrl().equals(url2)) continue;
                _book.setRootDir(ExtKt.getWorkDir$default(null, 1, null));
                _book.setUserNameSpace(userNameSpace);
                _book.setInShelf(true);
                return _book;
            } while (n < n2);
        }
        return null;
    }

    @Nullable
    public final Object saveShelfBookProgress(@NotNull Book book, @NotNull BookChapter bookChapter, @NotNull String userNameSpace, @NotNull Continuation<? super Unit> $completion) {
        Object object = this.editShelfBook(book, userNameSpace, (Function1<? super Book, Book>)new Function1<Book, Book>(bookChapter){
            final /* synthetic */ BookChapter $bookChapter;
            {
                this.$bookChapter = $bookChapter;
                super(1);
            }

            @NotNull
            public final Book invoke(@NotNull Book existBook) {
                Intrinsics.checkNotNullParameter(existBook, "existBook");
                existBook.setDurChapterIndex(this.$bookChapter.getIndex());
                existBook.setDurChapterTitle(this.$bookChapter.getTitle());
                existBook.setDurChapterTime(System.currentTimeMillis());
                return existBook;
            }
        }, $completion);
        if (object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return object;
        }
        return Unit.INSTANCE;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @Nullable
    public final Object saveShelfBookLatestChapter(@NotNull Book var1_1, @NotNull List<BookChapter> var2_2, @NotNull String var3_3, @Nullable Mutex var4_4, @NotNull Continuation<? super Unit> var5_5) {
        if (!(var5_5 instanceof saveShelfBookLatestChapter.1)) ** GOTO lbl-1000
        var9_6 = var5_5;
        if ((var9_6.label & -2147483648) != 0) {
            var9_6.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var5_5){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.saveShelfBookLatestChapter(null, null, null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var10_8 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                var6_9 = mutex;
                if (var6_9 == null) ** GOTO lbl36
                $continuation.L$0 = this;
                $continuation.L$1 = book;
                $continuation.L$2 = bookChapterList;
                $continuation.L$3 = userNameSpace;
                $continuation.L$4 = mutex;
                $continuation.label = 1;
                v0 = Mutex.DefaultImpls.lock$default(var6_9, null, $continuation, 1, null);
                ** if (v0 != var10_8) goto lbl25
lbl24:
                // 1 sources

                return var10_8;
lbl25:
                // 1 sources

                ** GOTO lbl36
            }
            case 1: {
                mutex = (Mutex)$continuation.L$4;
                userNameSpace = (String)$continuation.L$3;
                bookChapterList = (List)$continuation.L$2;
                book = (Book)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl36:
                // 3 sources

                $continuation.L$0 = mutex;
                $continuation.L$1 = null;
                $continuation.L$2 = null;
                $continuation.L$3 = null;
                $continuation.L$4 = null;
                $continuation.label = 2;
                v1 = this.editShelfBook(book, userNameSpace, (Function1<? super Book, Book>)new Function1<Book, Book>((List<BookChapter>)bookChapterList, book){
                    final /* synthetic */ List<BookChapter> $bookChapterList;
                    final /* synthetic */ Book $book;
                    {
                        this.$bookChapterList = $bookChapterList;
                        this.$book = $book;
                        super(1);
                    }

                    @NotNull
                    public final Book invoke(@NotNull Book existBook) {
                        Intrinsics.checkNotNullParameter(existBook, "existBook");
                        if (this.$bookChapterList.size() > 0) {
                            BookChapter bookChapter = CollectionsKt.last(this.$bookChapterList);
                            existBook.setLatestChapterTitle(bookChapter.getTitle());
                        }
                        if (this.$bookChapterList.size() - existBook.getTotalChapterNum() > 0) {
                            existBook.setLastCheckCount(this.$bookChapterList.size() - existBook.getTotalChapterNum());
                            existBook.setLastCheckTime(System.currentTimeMillis());
                        }
                        existBook.setLastCheckError(null);
                        existBook.setTotalChapterNum(this.$bookChapterList.size());
                        this.$book.setLatestChapterTitle(existBook.getLatestChapterTitle());
                        this.$book.setLastCheckCount(existBook.getLastCheckCount());
                        this.$book.setLastCheckTime(existBook.getLastCheckTime());
                        this.$book.setLastCheckError(existBook.getLastCheckError());
                        this.$book.setTotalChapterNum(existBook.getTotalChapterNum());
                        return existBook;
                    }
                }, $continuation);
                ** if (v1 != var10_8) goto lbl45
lbl44:
                // 1 sources

                return var10_8;
lbl45:
                // 1 sources

                ** GOTO lbl60
            }
            case 2: {
                mutex = (Mutex)$continuation.L$0;
                try {
                    ResultKt.throwOnFailure($result);
                    v1 = $result;
                }
                catch (Throwable var6_10) {
                    throw var6_10;
                }
                finally {
                    var6_9 = mutex;
                    if (var6_9 != null) {
                        Mutex.DefaultImpls.unlock$default(var6_9, null, 1, null);
                    }
                }
lbl60:
                // 2 sources

                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object saveShelfBookLatestChapter$default(BookController bookController, Book book, List list2, String string, Mutex mutex, Continuation continuation, int n, Object object) {
        if ((n & 8) != 0) {
            mutex = null;
        }
        return bookController.saveShelfBookLatestChapter(book, list2, string, mutex, continuation);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @Nullable
    public final Object editShelfBook(@NotNull Book var1_1, @NotNull String var2_2, @NotNull Function1<? super Book, Book> var3_3, @NotNull Continuation<? super Book> var4_4) {
        if (!(var4_4 instanceof editShelfBook.1)) ** GOTO lbl-1000
        var15_5 = var4_4;
        if ((var15_5.label & -2147483648) != 0) {
            var15_5.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var4_4){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.editShelfBook(null, null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var16_7 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                $continuation.L$0 = this;
                $continuation.L$1 = book;
                $continuation.L$2 = userNameSpace;
                $continuation.L$3 = handler;
                $continuation.label = 1;
                v0 = UserMutex.INSTANCE.getLocker(Intrinsics.stringPlus(userNameSpace, "@bookshelf"), $continuation);
                if (v0 == var16_7) {
                    return var16_7;
                }
                ** GOTO lbl28
            }
            case 1: {
                handler = (Function1)$continuation.L$3;
                userNameSpace = (String)$continuation.L$2;
                book = (Book)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl28:
                // 2 sources

                mutex = (Mutex)v0;
                BookControllerKt.access$getLogger$p().info("wait for lock {}", (Object)Intrinsics.stringPlus(userNameSpace, "@bookshelf"));
                $continuation.L$0 = this;
                $continuation.L$1 = book;
                $continuation.L$2 = userNameSpace;
                $continuation.L$3 = handler;
                $continuation.L$4 = mutex;
                $continuation.label = 2;
                v1 = Mutex.DefaultImpls.lock$default(mutex, null, $continuation, 1, null);
                ** if (v1 != var16_7) goto lbl41
lbl40:
                // 1 sources

                return var16_7;
lbl41:
                // 1 sources

                ** GOTO lbl52
            }
            case 2: {
                mutex = (Mutex)$continuation.L$4;
                handler = (Function1)$continuation.L$3;
                userNameSpace = (String)$continuation.L$2;
                book = (Book)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                try {
                    ResultKt.throwOnFailure($result);
                    v1 = $result;
lbl52:
                    // 2 sources

                    BookControllerKt.access$getLogger$p().info("lock success");
                    var7_9 = new String[]{"bookshelf"};
                    bookshelf = ExtKt.asJsonArray(this.getUserStorage(userNameSpace, var7_9));
                    if (bookshelf == null) {
                        bookshelf = new JsonArray();
                    }
                    existIndex = -1;
                    var8_13 = 0;
                    var9_15 = bookshelf.size();
                    if (var8_13 < var9_15) {
                        do {
                            i = var8_13++;
                            _book = bookshelf.getJsonObject(i).mapTo(Book.class);
                            var12_20 = book.getBookUrl();
                            var13_21 = false;
                            if (var12_20.length() > 0 && _book.getBookUrl().equals(book.getBookUrl())) {
                                existIndex = i;
                                break;
                            }
                            var12_20 = book.getName();
                            var13_21 = false;
                            if (!(var12_20.length() > 0) || !_book.getName().equals(book.getName())) continue;
                            var12_20 = book.getAuthor();
                            var13_21 = false;
                            if (!(var12_20.length() > 0) || !_book.getAuthor().equals(book.getAuthor())) continue;
                            existIndex = i;
                            break;
                        } while (var8_13 < var9_15);
                    }
                    if (existIndex >= 0) {
                        bookList = bookshelf.getList();
                        var10_18 = existBook = bookshelf.getJsonObject(existIndex).mapTo(Book.class);
                        Intrinsics.checkNotNullExpressionValue(var10_18, "existBook");
                        existBook = (Book)handler.invoke(var10_18);
                        bookList.set(existIndex, JsonObject.mapFrom(existBook));
                        bookshelf = new JsonArray(bookList);
                        this.saveUserStorage(userNameSpace, "bookshelf", bookshelf);
                        var10_18 = existBook;
                        return var10_18;
                    }
                }
                catch (Throwable var6_12) {
                    throw var6_12;
                }
                finally {
                    Mutex.DefaultImpls.unlock$default(mutex, null, 1, null);
                }
                return null;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public final void saveBookSources(@NotNull Book book, @NotNull List<SearchBook> sourceList, @NotNull String userNameSpace, boolean replace) {
        String[] stringArray;
        JsonArray localBookSourceList;
        Intrinsics.checkNotNullParameter(book, "book");
        Intrinsics.checkNotNullParameter(sourceList, "sourceList");
        Intrinsics.checkNotNullParameter(userNameSpace, "userNameSpace");
        CharSequence charSequence = book.getName();
        boolean bl = false;
        if (charSequence.length() == 0) {
            return;
        }
        JsonArray bookSourceList2 = new JsonArray();
        if (!replace && (localBookSourceList = ExtKt.asJsonArray(this.getUserStorage(userNameSpace, stringArray = new String[]{book.getName() + '_' + book.getAuthor(), "bookSource"}))) != null) {
            bookSourceList2 = localBookSourceList;
        }
        int n = 0;
        Map urlMap = new LinkedHashMap();
        n = 0;
        int n2 = bookSourceList2.size();
        if (n < n2) {
            do {
                String bookUrl;
                int i = n++;
                String string = bookUrl = bookSourceList2.getJsonObject(i).getString("bookUrl");
                Intrinsics.checkNotNullExpressionValue(string, "bookUrl");
                urlMap.put(string, i);
            } while (n < n2);
        }
        if ((n = 0) < (n2 = sourceList.size())) {
            do {
                int k;
                SearchBook searchBook2;
                int existIndex;
                if ((existIndex = ((Number)urlMap.getOrDefault((searchBook2 = sourceList.get(k = n++)).getBookUrl(), -1)).intValue()) >= 0) {
                    bookSourceList2.set(existIndex, JsonObject.mapFrom(searchBook2));
                    continue;
                }
                bookSourceList2.add(JsonObject.mapFrom(searchBook2));
                urlMap.put(searchBook2.getBookUrl(), bookSourceList2.size() - 1);
            } while (n < n2);
        }
        String[] stringArray2 = new String[]{book.getName() + '_' + book.getAuthor(), "bookSource"};
        this.saveUserStorage(userNameSpace, ExtKt.getRelativePath(stringArray2), bookSourceList2);
    }

    public static /* synthetic */ void saveBookSources$default(BookController bookController, Book book, List list2, String string, boolean bl, int n, Object object) {
        if ((n & 8) != 0) {
            bl = false;
        }
        bookController.saveBookSources(book, list2, string, bl);
    }

    public final boolean extractEpub(@NotNull Book book, boolean force) {
        Intrinsics.checkNotNullParameter(book, "book");
        File epubExtractDir = new File(ExtKt.getWorkDir(book.getBookUrl() + File.separator + "index"));
        if (force || !epubExtractDir.exists()) {
            ExtKt.deleteRecursively(epubExtractDir);
            File localEpubFile = new File(ExtKt.getWorkDir(book.getOriginName() + File.separator + "index.epub"));
            if (StringsKt.indexOf$default((CharSequence)book.getOriginName(), "localStore", 0, false, 6, null) > 0) {
                localEpubFile = new File(ExtKt.getWorkDir(book.getOriginName()));
            }
            if (StringsKt.indexOf$default((CharSequence)book.getOriginName(), "webdav", 0, false, 6, null) > 0) {
                localEpubFile = new File(ExtKt.getWorkDir(book.getOriginName()));
            }
            BookControllerKt.access$getLogger$p().info("extractEpub from {} to {}", (Object)localEpubFile, (Object)epubExtractDir);
            String string = epubExtractDir.toString();
            Intrinsics.checkNotNullExpressionValue(string, "epubExtractDir.toString()");
            if (!ExtKt.unzip(localEpubFile, string)) {
                return false;
            }
        }
        return true;
    }

    public static /* synthetic */ boolean extractEpub$default(BookController bookController, Book book, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return bookController.extractEpub(book, bl);
    }

    public final boolean extractCbz(@NotNull Book book, boolean force) {
        Intrinsics.checkNotNullParameter(book, "book");
        File extractDir = new File(ExtKt.getWorkDir(book.getBookUrl() + File.separator + "index"));
        if (force || !extractDir.exists()) {
            ExtKt.deleteRecursively(extractDir);
            File localFile = new File(ExtKt.getWorkDir(book.getOriginName() + File.separator + "index.cbz"));
            if (StringsKt.indexOf$default((CharSequence)book.getOriginName(), "localStore", 0, false, 6, null) > 0) {
                localFile = new File(ExtKt.getWorkDir(book.getOriginName()));
            }
            if (StringsKt.indexOf$default((CharSequence)book.getOriginName(), "webdav", 0, false, 6, null) > 0) {
                localFile = new File(ExtKt.getWorkDir(book.getOriginName()));
            }
            String string = extractDir.toString();
            Intrinsics.checkNotNullExpressionValue(string, "extractDir.toString()");
            if (!ExtKt.unzip(localFile, string)) {
                return false;
            }
        }
        return true;
    }

    public static /* synthetic */ boolean extractCbz$default(BookController bookController, Book book, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return bookController.extractCbz(book, bl);
    }

    public final boolean convertPdfToImage(@NotNull Book book, boolean force) {
        Intrinsics.checkNotNullParameter(book, "book");
        return true;
    }

    public static /* synthetic */ boolean convertPdfToImage$default(BookController bookController, Book book, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return bookController.convertPdfToImage(book, bl);
    }

    public final void convertPdfPageToImage(@NotNull Book book, int index, boolean force) {
        Intrinsics.checkNotNullParameter(book, "book");
        File extractDir = new File(ExtKt.getWorkDir(book.getBookUrl() + File.separator + "index"));
        if (!extractDir.exists()) {
            extractDir.mkdirs();
        }
        String imageFormat = "png";
        File output = new File(extractDir.toString() + File.separator + "output-" + index + '.' + imageFormat);
        if (force || !output.exists()) {
            ExtKt.deleteRecursively(output);
            File localFile = new File(ExtKt.getWorkDir(book.getOriginName() + File.separator + "index.pdf"));
            if (StringsKt.indexOf$default((CharSequence)book.getOriginName(), "localStore", 0, false, 6, null) > 0) {
                localFile = new File(ExtKt.getWorkDir(book.getOriginName()));
            }
            if (StringsKt.indexOf$default((CharSequence)book.getOriginName(), "webdav", 0, false, 6, null) > 0) {
                localFile = new File(ExtKt.getWorkDir(book.getOriginName()));
            }
            PDDocument document = PDDocument.load(localFile);
            PDFRenderer renderer = new PDFRenderer(document);
            float targetWidth = book.getPdfImageWidth();
            Intrinsics.checkNotNullExpressionValue(document, "document");
            this.savePdfPageToImage(document, renderer, index, targetWidth, imageFormat, output);
        }
    }

    public static /* synthetic */ void convertPdfPageToImage$default(BookController bookController, Book book, int n, boolean bl, int n2, Object object) {
        if ((n2 & 4) != 0) {
            bl = false;
        }
        bookController.convertPdfPageToImage(book, n, bl);
    }

    public final void savePdfPageToImage(@NotNull PDDocument document, @NotNull PDFRenderer renderer, int index, float targetWidth, @NotNull String imageFormat, @NotNull File output) {
        Intrinsics.checkNotNullParameter(document, "document");
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        Intrinsics.checkNotNullParameter(imageFormat, "imageFormat");
        Intrinsics.checkNotNullParameter(output, "output");
        float dpi = 300.0f;
        PDPage page = document.getPage(index);
        PDRectangle pageSize = page.getCropBox();
        float targetHeight = 0.0f;
        float scaleFactor = targetWidth / pageSize.getWidth();
        float scaledHeight = pageSize.getHeight() * scaleFactor;
        int targetHeightDimension = targetHeight == 0.0f ? (int)scaledHeight : (int)targetHeight;
        Dimension targetDimension = new Dimension((int)targetWidth, targetHeightDimension);
        BufferedImage image = renderer.renderImageWithDPI(index, dpi, ImageType.RGB);
        Image scaledImage = image.getScaledInstance(targetDimension.width, targetDimension.height, 4);
        BufferedImage scaledBufferedImage = new BufferedImage(targetDimension.width, targetDimension.height, 1);
        Graphics2D graphics = scaledBufferedImage.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        graphics.dispose();
        ImageIO.write((RenderedImage)scaledBufferedImage, imageFormat, output);
    }

    @Nullable
    public final Object syncBookProgressFromWebdav(@NotNull Object progressFilePath, @NotNull String userNameSpace, @NotNull Continuation<? super Unit> $completion) {
        File progressFile = null;
        Object object = progressFilePath;
        if (object instanceof File) {
            progressFile = (File)progressFilePath;
        } else if (object instanceof String) {
            progressFile = new File((String)progressFilePath);
        }
        if (progressFile == null) {
            return Unit.INSTANCE;
        }
        Ref.ObjectRef<Book> book = new Ref.ObjectRef<Book>();
        JsonObject jsonObject = ExtKt.asJsonObject(FilesKt.readText$default(progressFile, null, 1, null));
        Book book2 = book.element = jsonObject == null ? null : jsonObject.mapTo(Book.class);
        if (book.element != null) {
            Object object2 = this.editShelfBook((Book)book.element, userNameSpace, (Function1<? super Book, Book>)new Function1<Book, Book>(book){
                final /* synthetic */ Ref.ObjectRef<Book> $book;
                {
                    this.$book = $book;
                    super(1);
                }

                @NotNull
                public final Book invoke(@NotNull Book existBook) {
                    Intrinsics.checkNotNullParameter(existBook, "existBook");
                    existBook.setDurChapterIndex(((Book)this.$book.element).getDurChapterIndex());
                    existBook.setDurChapterPos(((Book)this.$book.element).getDurChapterPos());
                    existBook.setDurChapterTime(((Book)this.$book.element).getDurChapterTime());
                    existBook.setDurChapterTitle(((Book)this.$book.element).getDurChapterTitle());
                    BookControllerKt.access$getLogger$p().info("syncShelfBookProgress: {}", (Object)existBook);
                    return existBook;
                }
            }, $completion);
            if (object2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                return object2;
            }
            return Unit.INSTANCE;
        }
        return Unit.INSTANCE;
    }

    @Nullable
    public final Object saveBookProgressToWebdav(@NotNull Book book, @NotNull BookChapter bookChapter, @NotNull String userNameSpace, @NotNull Continuation<? super Unit> $completion) {
        String userHome = this.getUserWebdavHome(userNameSpace);
        File bookProgressDir = new File(userHome + File.separator + "bookProgress");
        if (!bookProgressDir.exists() && !(bookProgressDir = new File(userHome + File.separator + "legado" + File.separator + "bookProgress")).exists()) {
            return Unit.INSTANCE;
        }
        File progressFile = new File(bookProgressDir.toString() + File.separator + book.getName() + '_' + book.getAuthor() + ".json");
        Pair[] pairArray = new Pair[]{TuplesKt.to("name", book.getName()), TuplesKt.to("author", book.getAuthor()), TuplesKt.to("durChapterIndex", Boxing.boxInt(bookChapter.getIndex())), TuplesKt.to("durChapterPos", Boxing.boxInt(0)), TuplesKt.to("durChapterTime", Boxing.boxLong(System.currentTimeMillis())), TuplesKt.to("durChapterTitle", bookChapter.getTitle())};
        FilesKt.writeText$default(progressFile, ExtKt.jsonEncode(MapsKt.mapOf(pairArray), true), null, 2, null);
        return Unit.INSTANCE;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    public final Object syncFromWebdav(@NotNull String var1_1, @NotNull String var2_2, @NotNull Continuation<? super Boolean> var3_3) {
        if (!(var3_3 instanceof syncFromWebdav.1)) ** GOTO lbl-1000
        var20_4 = var3_3;
        if ((var20_4.label & -2147483648) != 0) {
            var20_4.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var3_3){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                int I$0;
                int I$1;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.syncFromWebdav(null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var21_6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        block4 : switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                descDir = null;
                var5_8 = new String[]{"storage", "data", userNameSpace, "tmp"};
                descDir = ExtKt.getWorkDir(var5_8);
                descDirFile = new File(descDir);
                try {
                    userHome = this.getUserWebdavHome(userNameSpace);
                    zipFile = new File((String)zipFilePath);
                    if (!zipFile.exists()) {
                        var8_13 = Boxing.boxBoolean(false);
                        ExtKt.deleteRecursively(descDirFile);
                        return var8_13;
                    }
                    ExtKt.deleteRecursively(descDirFile);
                    ZipUtils.INSTANCE.unzipFile(zipFile, descDirFile);
                    var9_15 = this.getBackupFileNames();
                    syncDataFileList = CollectionsKt.arrayListOf(Arrays.copyOf(var9_15, var9_15.length));
                    $this$forEach$iv = syncDataFileList;
                    $i$f$forEach = false;
                    for (E element$iv : $this$forEach$iv) {
                        it = (String)element$iv;
                        $i$a$-forEach-BookController$syncFromWebdav$2 = false;
                        backupFile = new File(descDir + File.separator + it);
                        if (!backupFile.exists()) continue;
                        var16_26 = new String[]{"storage", "data", userNameSpace, it};
                        userDataFile = new File(ExtKt.getWorkDir(var16_26));
                        ExtKt.deleteRecursively(userDataFile);
                        FilesKt.copyRecursively$default(backupFile, userDataFile, false, null, 6, null);
                    }
                    backupBooksDir = new File(descDir + File.separator + "books");
                    if (backupBooksDir.exists()) {
                        var11_19 = new String[]{"storage", "data", userNameSpace, "webdav", "books"};
                        webdavBooksDir = new File(ExtKt.getWorkDir((String[])var11_19));
                        ExtKt.deleteRecursively(webdavBooksDir);
                        FilesKt.copyRecursively$default(backupBooksDir, webdavBooksDir, false, null, 6, null);
                    }
                    if (!(bookProgressDir = new File(userHome + File.separator + "bookProgress")).exists()) {
                        bookProgressDir = new File(userHome + File.separator + "legado" + File.separator + "bookProgress");
                    }
                    if (!bookProgressDir.exists() || !bookProgressDir.isDirectory()) ** GOTO lbl62
                    var11_19 = bookProgressDir.listFiles();
                    Intrinsics.checkNotNullExpressionValue(var11_19, "bookProgressDir.listFiles()");
                    $this$forEach$iv = var11_19;
                    $i$f$forEach = false;
                    var13_22 = $this$forEach$iv;
                    var14_23 = var13_22.length;
                    var15_25 = 0;
lbl60:
                    // 2 sources

                    while (true) {
                        if (var15_25 < var14_23) break;
lbl62:
                        // 2 sources

                        var11_19 = Boxing.boxBoolean(true);
                        break block4;
                        break;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return Boxing.boxBoolean(false);
                }
                element$iv = var13_22[var15_25];
                it = (File)element$iv;
                $i$a$-forEach-BookController$syncFromWebdav$3 = false;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                $continuation.L$0 = this;
                $continuation.L$1 = userNameSpace;
                $continuation.L$2 = descDirFile;
                $continuation.L$3 = var13_22;
                $continuation.I$0 = var14_23;
                $continuation.I$1 = var15_25;
                $continuation.label = 1;
                v0 = this.syncBookProgressFromWebdav(it, (String)userNameSpace, $continuation);
                if (v0 == var21_6) {
                    return var21_6;
                }
                ** GOTO lbl98
            }
            case 1: {
                $i$f$forEach = false;
                $i$a$-forEach-BookController$syncFromWebdav$3 = false;
                var15_25 = $continuation.I$1;
                var14_23 = $continuation.I$0;
                var13_22 = (Object[])$continuation.L$3;
                var5_8 = (File)$continuation.L$2;
                var2_2 = (String)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl98:
                // 2 sources

                ++var15_25;
                ** continue;
            }
        }
        ExtKt.deleteRecursively((File)var5_8);
        return var11_19;
        finally {
            ExtKt.deleteRecursively((File)var5_8);
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object saveToWebdav(@NotNull String var1_1, @Nullable String var2_2, @NotNull Continuation<? super Boolean> var3_3) {
        block12: {
            if (!(var3_3 instanceof saveToWebdav.1)) ** GOTO lbl-1000
            var8_4 = var3_3;
            if ((var8_4.label & -2147483648) != 0) {
                var8_4.label -= -2147483648;
            } else lbl-1000:
            // 2 sources

            {
                $continuation = new ContinuationImpl(this, var3_3){
                    Object L$0;
                    Object L$1;
                    Object L$2;
                    Object L$3;
                    /* synthetic */ Object result;
                    final /* synthetic */ BookController this$0;
                    int label;
                    {
                        this.this$0 = this$0;
                        super($completion);
                    }

                    @Nullable
                    public final Object invokeSuspend(@NotNull Object $result) {
                        this.result = $result;
                        this.label |= Integer.MIN_VALUE;
                        return this.this$0.saveToWebdav(null, null, this);
                    }
                };
            }
            $result = $continuation.result;
            var9_6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch ($continuation.label) {
                case 0: {
                    ResultKt.throwOnFailure($result);
                    legadoHome = userHome = this.getUserWebdavHome(userNameSpace);
                    if (latestZipFilePath != null) break;
                    $continuation.L$0 = this;
                    $continuation.L$1 = userNameSpace;
                    $continuation.L$2 = userHome;
                    $continuation.L$3 = legadoHome;
                    $continuation.label = 1;
                    v0 = this.getLastBackFileFromWebdav(userNameSpace, $continuation);
                    if (v0 == var9_6) {
                        return var9_6;
                    }
                    ** GOTO lbl30
                }
                case 1: {
                    legadoHome = (String)$continuation.L$3;
                    userHome = (String)$continuation.L$2;
                    userNameSpace = (String)$continuation.L$1;
                    this = (BookController)$continuation.L$0;
                    ResultKt.throwOnFailure($result);
                    v0 = $result;
lbl30:
                    // 2 sources

                    v1 = (String)v0;
                    break block12;
                }
            }
            v1 = _latestZipFilePath = latestZipFilePath;
        }
        if (_latestZipFilePath == null) {
            legadoHome = userHome + File.separator + "legado";
        } else if (StringsKt.indexOf$default((CharSequence)_latestZipFilePath, "legado", 0, false, 6, null) > 0) {
            legadoHome = userHome + File.separator + "legado";
        }
        $continuation.L$0 = null;
        $continuation.L$1 = null;
        $continuation.L$2 = null;
        $continuation.L$3 = null;
        $continuation.label = 2;
        v2 = this.createUserBackup(userNameSpace, legadoHome, (String)_latestZipFilePath, $continuation);
        if (v2 == var9_6) {
            return var9_6;
        }
        ** GOTO lbl51
        {
            case 2: {
                ResultKt.throwOnFailure($result);
                v2 = $result;
lbl51:
                // 2 sources

                return Boxing.boxBoolean(v2 != null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object saveToWebdav$default(BookController bookController, String string, String string2, Continuation continuation, int n, Object object) {
        if ((n & 2) != 0) {
            string2 = null;
        }
        return bookController.saveToWebdav(string, string2, continuation);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public final Object createUserBackup(@NotNull String userNameSpace, @NotNull String backupDir, @Nullable String latestZipFilePath, @NotNull Continuation<? super File> $completion) {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(Boxing.boxLong(System.currentTimeMillis()));
        String descDir = null;
        String[] stringArray = new String[]{"storage", "data", userNameSpace, Intrinsics.stringPlus("backup", today)};
        descDir = ExtKt.getWorkDir(stringArray);
        File descDirFile = new File(descDir);
        ExtKt.deleteRecursively(descDirFile);
        try {
            if (latestZipFilePath != null && !ExtKt.unzip(new File(latestZipFilePath), descDir)) {
                Object var8_8 = null;
                return var8_8;
            }
            String[] stringArray2 = this.getBackupFileNames();
            ArrayList<String> syncDataFileList = CollectionsKt.arrayListOf(Arrays.copyOf(stringArray2, stringArray2.length));
            Iterable $this$forEach$iv = syncDataFileList;
            boolean $i$f$forEach22 = false;
            for (Object element$iv : $this$forEach$iv) {
                String it = (String)element$iv;
                boolean bl = false;
                String[] stringArray3 = new String[]{"storage", "data", userNameSpace, it};
                File userDataFile = new File(ExtKt.getWorkDir(stringArray3));
                if (!userDataFile.exists()) continue;
                File backupFile = new File(descDir + File.separator + it);
                ExtKt.deleteRecursively(backupFile);
                FilesKt.copyRecursively$default(userDataFile, backupFile, false, null, 6, null);
            }
            String[] $i$f$forEach22 = new String[]{"storage", "data", userNameSpace, "webdav", "books"};
            File webdavBooksDir = new File(ExtKt.getWorkDir($i$f$forEach22));
            if (webdavBooksDir.exists()) {
                File backupBooksDir = new File(descDir + File.separator + "books");
                ExtKt.deleteRecursively(backupBooksDir);
                FilesKt.copyRecursively$default(webdavBooksDir, backupBooksDir, false, null, 6, null);
            }
            File backupFile = FileUtils.INSTANCE.createFileWithReplace(backupDir + File.separator + "backup" + today + ".zip");
            File[] fileArray = descDirFile.listFiles();
            Intrinsics.checkNotNullExpressionValue(fileArray, "descDirFile.listFiles()");
            Object object = fileArray;
            object = ZipUtils.INSTANCE.zipFiles((Collection<? extends File>)CollectionsKt.arrayListOf(Arrays.copyOf(object, ((File[])object).length)), backupFile, null) ? backupFile : (File)null;
            return object;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            ExtKt.deleteRecursively(descDirFile);
        }
        return null;
    }

    public static /* synthetic */ Object createUserBackup$default(BookController bookController, String string, String string2, String string3, Continuation continuation, int n, Object object) {
        if ((n & 4) != 0) {
            string3 = null;
        }
        return bookController.createUserBackup(string, string2, string3, continuation);
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public final Object getLastBackFileFromWebdav(@NotNull String userNameSpace, @NotNull Continuation<? super String> $completion) {
        void $this$forEach$iv;
        String userHome = this.getUserWebdavHome(userNameSpace);
        File legadoHome = new File(userHome + File.separator + "legado");
        if (!legadoHome.exists()) {
            legadoHome = new File(userHome);
        }
        if (!legadoHome.exists()) {
            return null;
        }
        String latestZipFile = null;
        Regex zipFileReg = new Regex("^backup[0-9-]+.zip$", RegexOption.IGNORE_CASE);
        File[] fileArray = legadoHome.listFiles();
        boolean bl = false;
        int n = 0;
        File[] it = fileArray;
        boolean bl2 = false;
        Intrinsics.checkNotNullExpressionValue(it, "it");
        File[] $this$sortByDescending$iv = it;
        boolean $i$f$sortByDescending = false;
        if ($this$sortByDescending$iv.length > 1) {
            boolean bl3 = false;
            ArraysKt.sortWith($this$sortByDescending$iv, new Comparator<T>(){

                public final int compare(T a, T b) {
                    boolean bl = false;
                    File it = (File)b;
                    boolean bl2 = false;
                    Comparable comparable = Long.valueOf(it.lastModified());
                    it = (File)a;
                    Comparable comparable2 = comparable;
                    bl2 = false;
                    Long l = it.lastModified();
                    return ComparisonsKt.compareValues(comparable2, (Comparable)l);
                }
            });
        }
        Object[] objectArray = fileArray;
        Intrinsics.checkNotNullExpressionValue(objectArray, "legadoHome.listFiles().also{\n            it.sortByDescending {\n                it.lastModified()\n            }\n        }");
        objectArray = objectArray;
        boolean $i$f$forEach = false;
        for (void element$iv : $this$forEach$iv) {
            File it2 = (File)element$iv;
            boolean bl4 = false;
            String string = it2.getName();
            Intrinsics.checkNotNullExpressionValue(string, "it.name");
            if (!zipFileReg.matches(string)) continue;
            latestZipFile = it2.toString();
        }
        return latestZipFile;
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object bookSourceDebugSSE(@NotNull RoutingContext var1_1, @NotNull Continuation<? super Unit> var2_2) {
        if (!(var2_2 instanceof bookSourceDebugSSE.1)) ** GOTO lbl-1000
        var13_3 = var2_2;
        if ((var13_3.label & -2147483648) != 0) {
            var13_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.bookSourceDebugSSE(null, this);
                }
            };
        }
        $result = $continuation.result;
        var14_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                response = context.response().putHeader("Content-Type", "text/event-stream").putHeader("Cache-Control", "no-cache").setChunked(true);
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.L$3 = response;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var14_5) {
                    return var14_5;
                }
                ** GOTO lbl30
            }
            case 1: {
                response = (HttpServerResponse)$continuation.L$3;
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl30:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    response.write("event: error\n");
                    response.end("data: " + ExtKt.jsonEncode(ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528"), false) + "\n\n");
                    return Unit.INSTANCE;
                }
                var7_8 = context.queryParam("bookSourceUrl");
                Intrinsics.checkNotNullExpressionValue(var7_8, "context.queryParam(\"bookSourceUrl\")");
                var6_9 = CollectionsKt.firstOrNull(var7_8);
                bookSourceUrl = var6_9 == null ? "" : var6_9;
                var8_11 = context.queryParam("keyword");
                Intrinsics.checkNotNullExpressionValue(var8_11, "context.queryParam(\"keyword\")");
                var7_8 = CollectionsKt.firstOrNull(var8_11);
                keyword = var7_8 == null ? "" : var7_8;
                var7_8 = bookSourceUrl;
                var8_12 = false;
                var9_14 = false;
                if (var7_8.length() == 0) {
                    response.write("event: error\n");
                    response.end("data: " + ExtKt.jsonEncode(returnData.setErrorMsg("\u672a\u914d\u7f6e\u4e66\u6e90"), false) + "\n\n");
                    return Unit.INSTANCE;
                }
                var7_8 = (CharSequence)keyword;
                var8_12 = false;
                var9_14 = false;
                if (var7_8.length() == 0) {
                    response.write("event: error\n");
                    response.end("data: " + ExtKt.jsonEncode(returnData.setErrorMsg("\u8bf7\u8f93\u5165\u641c\u7d22\u5173\u952e\u8bcd"), false) + "\n\n");
                    return Unit.INSTANCE;
                }
                userNameSpace = this.getUserNameSpace(context);
                bookSourceString = this.getBookSourceStringBySourceURLOpt(bookSourceUrl, userNameSpace);
                var9_15 = bookSourceString;
                var10_16 = false;
                var11_18 = false;
                if (var9_15 == null || var9_15.length() == 0) {
                    response.write("event: error\n");
                    response.end("data: " + ExtKt.jsonEncode(returnData.setErrorMsg("\u672a\u914d\u7f6e\u4e66\u6e90"), false) + "\n\n");
                    return Unit.INSTANCE;
                }
                context.request().connection().closeHandler((Handler<Void>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, bookSourceDebugSSE$lambda-18(com.htmake.reader.api.controller.BookController java.lang.Void ), (Ljava/lang/Void;)V)((BookController)this));
                BookControllerKt.access$getLogger$p().info("bookSourceDebugSSE bookSource: {} keyword: {}", (Object)bookSourceString, keyword);
                debugger = new Debugger((Function1<? super String, Unit>)new Function1<String, Unit>(response){
                    final /* synthetic */ HttpServerResponse $response;
                    {
                        this.$response = $response;
                        super(1);
                    }

                    public final void invoke(@NotNull String msg) {
                        Intrinsics.checkNotNullParameter(msg, "msg");
                        this.$response.write("data: " + ExtKt.jsonEncode(MapsKt.mapOf(TuplesKt.to("msg", msg)), false) + "\n\n");
                    }
                });
                webBook = new WebBook(bookSourceString, false, null, userNameSpace, 6, null);
                $continuation.L$0 = response;
                $continuation.L$1 = null;
                $continuation.L$2 = null;
                $continuation.L$3 = null;
                $continuation.label = 2;
                v1 = debugger.startDebug(webBook, (String)keyword, $continuation);
                if (v1 == var14_5) {
                    return var14_5;
                }
                ** GOTO lbl87
            }
            case 2: {
                var4_7 = (HttpServerResponse)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v1 = $result;
lbl87:
                // 2 sources

                var4_7.write("event: end\n");
                var4_7.end("data: " + ExtKt.jsonEncode(MapsKt.mapOf(TuplesKt.to("end", Boxing.boxBoolean(true))), false) + "\n\n");
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object cacheBookSSE(@NotNull RoutingContext var1_1, @NotNull Continuation<? super Unit> var2_2) {
        if (!(var2_2 instanceof cacheBookSSE.1)) ** GOTO lbl-1000
        var21_3 = var2_2;
        if ((var21_3.label & -2147483648) != 0) {
            var21_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                Object L$5;
                Object L$6;
                Object L$7;
                int I$0;
                int I$1;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.cacheBookSSE(null, this);
                }
            };
        }
        $result = $continuation.result;
        var22_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                response = context.response().putHeader("Content-Type", "text/event-stream").putHeader("Cache-Control", "no-cache").setChunked(true);
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.L$3 = response;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var22_5) {
                    return var22_5;
                }
                ** GOTO lbl30
            }
            case 1: {
                response = (HttpServerResponse)$continuation.L$3;
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl30:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    response.write("event: error\n");
                    response.end("data: " + ExtKt.jsonEncode(ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528"), false) + "\n\n");
                    return Unit.INSTANCE;
                }
                var5_8 = null;
                var6_9 = 0;
                var7_10 = 0;
                if (context.request().method() == HttpMethod.POST) {
                    var9_11 = context.getBodyAsJson().getString("url");
                    var8_14 = var9_11 == null ? context.getBodyAsJson().getString("bookUrl") : var9_11;
                    bookUrl = var8_14 == null ? "" : var8_14;
                    var8_14 = context.getBodyAsJson().getInteger("refresh", Boxing.boxInt(0));
                    Intrinsics.checkNotNullExpressionValue(var8_14, "context.bodyAsJson.getInteger(\"refresh\", 0)");
                    refresh = ((Number)var8_14).intValue();
                    var8_14 = context.getBodyAsJson().getInteger("concurrentCount", Boxing.boxInt(24));
                    Intrinsics.checkNotNullExpressionValue(var8_14, "context.bodyAsJson.getInteger(\"concurrentCount\", 24)");
                    var7_10 = ((Number)var8_14).intValue();
                } else {
                    var9_11 = context.queryParam("url");
                    Intrinsics.checkNotNullExpressionValue(var9_11, "context.queryParam(\"url\")");
                    var8_14 = (String)CollectionsKt.firstOrNull(var9_11);
                    bookUrl = var8_14 == null ? "" : var8_14;
                    var9_11 = context.queryParam("refresh");
                    Intrinsics.checkNotNullExpressionValue(var9_11, "context.queryParam(\"refresh\")");
                    var8_14 = CollectionsKt.firstOrNull(var9_11);
                    if (var8_14 == null) {
                        v1 = 0;
                    } else {
                        var10_15 = var8_14;
                        var11_18 = false;
                        var9_11 = Boxing.boxInt(Integer.parseInt((String)var10_15));
                        v1 = var9_11 == null ? 0 : var9_11.intValue();
                    }
                    refresh = v1;
                    var9_11 = context.queryParam("concurrentCount");
                    Intrinsics.checkNotNullExpressionValue(var9_11, "context.queryParam(\"concurrentCount\")");
                    var8_14 = (String)CollectionsKt.firstOrNull(var9_11);
                    if (var8_14 == null) {
                        v2 = 24;
                    } else {
                        var10_15 = var8_14;
                        var11_18 = false;
                        var9_11 = Boxing.boxInt(Integer.parseInt((String)var10_15));
                        v2 = var9_11 == null ? 24 : var9_11.intValue();
                    }
                    concurrentCount = v2;
                }
                var8_14 = (CharSequence)bookUrl;
                var9_12 = false;
                var10_16 = false;
                if (var8_14.length() == 0) {
                    response.write("event: error\n");
                    response.end("data: " + ExtKt.jsonEncode(returnData.setErrorMsg("\u8bf7\u8f93\u5165\u4e66\u7c4d\u94fe\u63a5"), false) + "\n\n");
                    return Unit.INSTANCE;
                }
                userNameSpace = new Ref.ObjectRef<T>();
                userNameSpace.element = this.getUserNameSpace(context);
                bookInfo = this.getShelfBookByURL((String)bookUrl, (String)userNameSpace.element);
                if (bookInfo == null) {
                    response.write("event: error\n");
                    response.end("data: " + ExtKt.jsonEncode(returnData.setErrorMsg("\u8bf7\u5148\u52a0\u5165\u4e66\u67b6"), false) + "\n\n");
                    return Unit.INSTANCE;
                }
                if (bookInfo.isLocalBook()) {
                    response.write("event: error\n");
                    response.end("data: " + ExtKt.jsonEncode(returnData.setErrorMsg("\u672c\u5730\u4e66\u7c4d\u65e0\u9700\u7f13\u5b58"), false) + "\n\n");
                    return Unit.INSTANCE;
                }
                bookSource = new Ref.ObjectRef<T>();
                var18_20 = bookSource;
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.L$3 = response;
                $continuation.L$4 = userNameSpace;
                $continuation.L$5 = bookInfo;
                $continuation.L$6 = bookSource;
                $continuation.L$7 = var18_20;
                $continuation.I$0 = refresh;
                $continuation.I$1 = concurrentCount;
                $continuation.label = 2;
                v3 = BookController.getBookSourceString$default(this, context, bookInfo.getOrigin(), false, $continuation, 4, null);
                if (v3 == var22_5) {
                    return var22_5;
                }
                ** GOTO lbl126
            }
            case 2: {
                var7_10 = $continuation.I$1;
                var6_9 = $continuation.I$0;
                var18_20 = (Ref.ObjectRef<List<BookChapter>>)$continuation.L$7;
                bookSource = (Ref.ObjectRef<T>)$continuation.L$6;
                var9_13 = (Book)$continuation.L$5;
                var8_14 = (Ref.ObjectRef)$continuation.L$4;
                var4_7 = (HttpServerResponse)$continuation.L$3;
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v3 = $result;
lbl126:
                // 2 sources

                var19_21 = v3;
                var18_20.element = var19_21;
                var11_19 = (CharSequence)bookSource.element;
                var12_22 = false;
                var13_24 = false;
                if (var11_19 == null || var11_19.length() == 0) {
                    var4_7.write("event: error\n");
                    var4_7.end("data: " + ExtKt.jsonEncode(var3_6.setErrorMsg("\u672a\u914d\u7f6e\u4e66\u6e90"), false) + "\n\n");
                    return Unit.INSTANCE;
                }
                var18_20 = chapterList = new Ref.ObjectRef<List<BookChapter>>();
                $continuation.L$0 = this;
                $continuation.L$1 = var1_1;
                $continuation.L$2 = var4_7;
                $continuation.L$3 = var8_14;
                $continuation.L$4 = var9_13;
                $continuation.L$5 = bookSource;
                $continuation.L$6 = chapterList;
                $continuation.L$7 = var18_20;
                $continuation.I$0 = var6_9;
                $continuation.I$1 = var7_10;
                $continuation.label = 3;
                v4 = BookController.getLocalChapterList$default(this, var9_13, (String)bookSource.element, false, (String)var8_14.element, false, null, $continuation, 48, null);
                if (v4 == var22_5) {
                    return var22_5;
                }
                ** GOTO lbl165
            }
            case 3: {
                var7_10 = $continuation.I$1;
                var6_9 = $continuation.I$0;
                var18_20 = (Ref.ObjectRef)$continuation.L$7;
                chapterList = (Ref.ObjectRef<List<BookChapter>>)$continuation.L$6;
                var10_17 = (Ref.ObjectRef)$continuation.L$5;
                var9_13 = (Book)$continuation.L$4;
                var8_14 = (Ref.ObjectRef)$continuation.L$3;
                var4_7 = (HttpServerResponse)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v4 = $result;
lbl165:
                // 2 sources

                var19_21 = v4;
                var18_20.element = var19_21;
                cachedChapterContentSet = new Ref.ObjectRef<Set<Integer>>();
                var13_24 = false;
                cachedChapterContentSet.element = new LinkedHashSet<E>();
                if (var6_9 <= 0) {
                    cachedChapterContentSet.element = this.getCachedChapterContentSet(var9_13, (String)var8_14.element);
                }
                localCacheDir = this.getChapterCacheDir(var9_13, (String)var8_14.element);
                isEnd = new Ref.BooleanRef();
                successCount = new Ref.IntRef();
                failedCount = new Ref.IntRef();
                var1_1.request().connection().closeHandler((Handler<Void>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, cacheBookSSE$lambda-19(kotlin.jvm.internal.Ref$BooleanRef com.htmake.reader.api.controller.BookController java.lang.Void ), (Ljava/lang/Void;)V)((Ref.BooleanRef)isEnd, (BookController)this));
                var7_10 = var7_10 > 0 ? var7_10 : 24;
                BookControllerKt.access$getLogger$p().info("cacheBookSSE concurrentCount: {} refresh: {}", (Object)Boxing.boxInt(var7_10), (Object)Boxing.boxInt(var6_9));
                $continuation.L$0 = var4_7;
                $continuation.L$1 = cachedChapterContentSet;
                $continuation.L$2 = successCount;
                $continuation.L$3 = failedCount;
                $continuation.L$4 = null;
                $continuation.L$5 = null;
                $continuation.L$6 = null;
                $continuation.L$7 = null;
                $continuation.label = 4;
                v5 = this.limitConcurrent(var7_10, 0, ((List)chapterList.element).size(), (Function3<? super CoroutineScope, ? super Integer, ? super Continuation<Object>, ? extends Object>)new Function3<CoroutineScope, Integer, Continuation<? super Object>, Object>(cachedChapterContentSet, chapterList, (Ref.ObjectRef<String>)var10_17, this, (Ref.ObjectRef<String>)var8_14, var9_13, localCacheDir, successCount, isEnd, failedCount, null){
                    int I$1;
                    Object L$1;
                    int label;
                    private /* synthetic */ Object L$0;
                    /* synthetic */ int I$0;
                    final /* synthetic */ Ref.ObjectRef<Set<Integer>> $cachedChapterContentSet;
                    final /* synthetic */ Ref.ObjectRef<List<BookChapter>> $chapterList;
                    final /* synthetic */ Ref.ObjectRef<String> $bookSource;
                    final /* synthetic */ BookController this$0;
                    final /* synthetic */ Ref.ObjectRef<String> $userNameSpace;
                    final /* synthetic */ Book $bookInfo;
                    final /* synthetic */ File $localCacheDir;
                    final /* synthetic */ Ref.IntRef $successCount;
                    final /* synthetic */ Ref.BooleanRef $isEnd;
                    final /* synthetic */ Ref.IntRef $failedCount;
                    {
                        this.$cachedChapterContentSet = $cachedChapterContentSet;
                        this.$chapterList = $chapterList;
                        this.$bookSource = $bookSource;
                        this.this$0 = $receiver;
                        this.$userNameSpace = $userNameSpace;
                        this.$bookInfo = $bookInfo;
                        this.$localCacheDir = $localCacheDir;
                        this.$successCount = $successCount;
                        this.$isEnd = $isEnd;
                        this.$failedCount = $failedCount;
                        super(3, $completion);
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
                                $this$limitConcurrent = (CoroutineScope)this.L$0;
                                it = this.I$0;
                                if (((Set)this.$cachedChapterContentSet.element).contains(Boxing.boxInt(it))) ** GOTO lbl65
                                chapterIndex = it;
                                chapterInfo = (BookChapter)((List)this.$chapterList.element).get(it);
                                nextChapterUrl = null;
                                if (chapterIndex + 1 < ((List)this.$chapterList.element).size()) {
                                    nextChapterInfo = (BookChapter)((List)this.$chapterList.element).get(chapterIndex + 1);
                                    nextChapterUrl = nextChapterInfo.getUrl();
                                }
                                this.L$0 = $this$limitConcurrent;
                                this.L$1 = chapterInfo;
                                this.I$0 = it;
                                this.I$1 = chapterIndex;
                                this.label = 1;
                                v0 = new WebBook((String)this.$bookSource.element, this.this$0.getAppConfig().getDebugLog(), null, (String)this.$userNameSpace.element, 4, null).getBookContent(this.$bookInfo, chapterInfo, nextChapterUrl, this);
                                ** if (v0 != var12_2) goto lbl24
lbl23:
                                // 1 sources

                                return var12_2;
lbl24:
                                // 1 sources

                                ** GOTO lbl34
                            }
                            case 1: {
                                chapterIndex = this.I$1;
                                it = this.I$0;
                                chapterInfo = (BookChapter)this.L$1;
                                $this$limitConcurrent = (CoroutineScope)this.L$0;
                                ResultKt.throwOnFailure($result);
                                v0 = $result;
lbl34:
                                // 2 sources

                                content = (String)v0;
                                chapterCacheFile = new File(this.$localCacheDir.getAbsolutePath() + File.separator + chapterIndex + ".txt");
                                FilesKt.writeText$default(chapterCacheFile, content, null, 2, null);
                                var10_12 = BookSource.Companion.fromJson-IoAF18A((String)this.$bookSource.element);
                                var11_13 = false;
                                var9_14 = (BookSource)(Result.isFailure-impl(var10_12) != false ? null : var10_12);
                                this.L$0 = null;
                                this.L$1 = null;
                                this.I$0 = it;
                                this.I$1 = chapterIndex;
                                this.label = 2;
                                v1 = BookHelp.INSTANCE.saveImages($this$limitConcurrent, var9_14 == null ? new BookSource(null, null, null, 0, null, 0, false, false, null, null, null, null, null, null, null, null, 0L, 0L, 0, null, null, null, null, null, null, null, 0x3FFFFFF, null) : var9_14, this.$bookInfo, chapterInfo, content, this);
                                ** if (v1 != var12_2) goto lbl48
lbl47:
                                // 1 sources

                                return var12_2;
lbl48:
                                // 1 sources

                                ** GOTO lbl56
                            }
                            case 2: {
                                var4_5 = this.I$1;
                                var3_4 = this.I$0;
                                try {
                                    ResultKt.throwOnFailure($result);
                                    v1 = $result;
lbl56:
                                    // 2 sources

                                    var9_15 = this.$successCount.element;
                                    this.$successCount.element = var9_15 + 1;
                                    ((Set)this.$cachedChapterContentSet.element).add(Boxing.boxInt(var4_5));
                                }
                                catch (Exception var6_8) {
                                    this.$isEnd.element = true;
                                    var7_10 = this.$failedCount.element;
                                    this.$failedCount.element = var7_10 + 1;
                                }
lbl65:
                                // 3 sources

                                return Boxing.boxInt(var3_4);
                            }
                        }
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }

                    @Nullable
                    public final Object invoke(@NotNull CoroutineScope p1, int p2, @Nullable Continuation<Object> p3) {
                        Function3<CoroutineScope, Integer, Continuation<? super Object>, Object> function3 = new /* invalid duplicate definition of identical inner class */;
                        function3.L$0 = p1;
                        function3.I$0 = p2;
                        return function3.invokeSuspend(Unit.INSTANCE);
                    }
                }, (Function2<? super ArrayList<Object>, ? super Integer, Boolean>)new Function2<ArrayList<Object>, Integer, Boolean>(isEnd, cachedChapterContentSet, successCount, failedCount, var4_7){
                    final /* synthetic */ Ref.BooleanRef $isEnd;
                    final /* synthetic */ Ref.ObjectRef<Set<Integer>> $cachedChapterContentSet;
                    final /* synthetic */ Ref.IntRef $successCount;
                    final /* synthetic */ Ref.IntRef $failedCount;
                    final /* synthetic */ HttpServerResponse $response;
                    {
                        this.$isEnd = $isEnd;
                        this.$cachedChapterContentSet = $cachedChapterContentSet;
                        this.$successCount = $successCount;
                        this.$failedCount = $failedCount;
                        this.$response = $response;
                        super(2);
                    }

                    public final boolean invoke(@NotNull ArrayList<Object> list2, int loopCount) {
                        boolean bl;
                        Intrinsics.checkNotNullParameter(list2, "list");
                        if (this.$isEnd.element) {
                            bl = false;
                        } else {
                            Object[] objectArray = new Pair[]{TuplesKt.to("cachedCount", ((Set)this.$cachedChapterContentSet.element).size()), TuplesKt.to("successCount", this.$successCount.element), TuplesKt.to("failedCount", this.$failedCount.element)};
                            Map<K, V> result2 = MapsKt.mapOf(objectArray);
                            this.$response.write("data: " + ExtKt.jsonEncode(result2, false) + "\n\n");
                            objectArray = new Object[]{loopCount, list2.size(), result2};
                            BookControllerKt.access$getLogger$p().info("Loop: {} list.size: {} result: {}", objectArray);
                            bl = true;
                        }
                        return bl;
                    }
                }, $continuation);
                if (v5 == var22_5) {
                    return var22_5;
                }
                ** GOTO lbl200
            }
            case 4: {
                failedCount = (Ref.IntRef)$continuation.L$3;
                successCount = (Ref.IntRef)$continuation.L$2;
                var12_23 = (Ref.ObjectRef)$continuation.L$1;
                var4_7 = (HttpServerResponse)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v5 = $result;
lbl200:
                // 2 sources

                var4_7.write("event: end\n");
                var17_29 = new Pair[]{TuplesKt.to("cachedCount", Boxing.boxInt(((Set)var12_23.element).size())), TuplesKt.to("successCount", Boxing.boxInt(successCount.element)), TuplesKt.to("failedCount", Boxing.boxInt(failedCount.element))};
                var4_7.end("data: " + ExtKt.jsonEncode(MapsKt.mapOf(var17_29), false) + "\n\n");
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object cacheBookOnServer(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof cacheBookOnServer.1)) ** GOTO lbl-1000
        var9_3 = var2_2;
        if ((var9_3.label & -2147483648) != 0) {
            var9_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.cacheBookOnServer(null, this);
                }
            };
        }
        $result = $continuation.result;
        var10_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var10_5) {
                    return var10_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                var5_7 = context.getBodyAsJson().getJsonArray("bookUrlList");
                v1 = bookUrlList = var5_7 == null ? new JsonArray() : var5_7;
                if (bookUrlList.size() <= 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u4e66\u7c4d\u94fe\u63a5");
                }
                $i$f$CoroutineExceptionHandler = false;
                var7_11 = CoroutineExceptionHandler.Key;
                exceptionHandler = new CoroutineExceptionHandler(var7_11){

                    /*
                     * WARNING - void declaration
                     */
                    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
                        void ex;
                        Throwable throwable = exception;
                        CoroutineContext ctx = context;
                        boolean bl = false;
                        BookControllerKt.access$getLogger$p().info("cacheBookOnServer error: {}", (Object)ex.getMessage());
                    }
                };
                userNameSpace = this.getUserNameSpace(context);
                BuildersKt.launch$default(this, new MDCContext(null, 1, null).plus(Dispatchers.getIO()).plus(exceptionHandler), null, new Function2<CoroutineScope, Continuation<? super Unit>, Object>(this, bookUrlList, userNameSpace, null){
                    int label;
                    final /* synthetic */ BookController this$0;
                    final /* synthetic */ JsonArray $bookUrlList;
                    final /* synthetic */ String $userNameSpace;
                    {
                        this.this$0 = $receiver;
                        this.$bookUrlList = $bookUrlList;
                        this.$userNameSpace = $userNameSpace;
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
                                Object object3 = this.this$0.cacheBookOnServer(this.$bookUrlList, this.$userNameSpace, this);
                                if (object3 != object2) return Unit.INSTANCE;
                                return object2;
                            }
                            case 1: {
                                void $result;
                                ResultKt.throwOnFailure($result);
                                Object object3 = $result;
                                return Unit.INSTANCE;
                            }
                        }
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }

                    @NotNull
                    public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                        return (Continuation)((Object)new /* invalid duplicate definition of identical inner class */);
                    }

                    @Nullable
                    public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Unit> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                }, 2, null);
                return ReturnData.setData$default(returnData, "", null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object cacheBookOnServer(@NotNull JsonArray var1_1, @NotNull String var2_2, @NotNull Continuation<? super Unit> var3_3) {
        if (!(var3_3 instanceof cacheBookOnServer.3)) ** GOTO lbl-1000
        var25_4 = var3_3;
        if ((var25_4.label & -2147483648) != 0) {
            var25_4.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var3_3){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                Object L$5;
                Object L$6;
                Object L$7;
                Object L$8;
                int I$0;
                int I$1;
                int I$2;
                int I$3;
                int I$4;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.cacheBookOnServer(null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var26_6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                var4_7 = 0;
                var5_8 = bookUrlList.size();
                if (var4_7 >= var5_8) ** GOTO lbl165
                while (true) {
                    i = var4_7++;
                    bookUrl = bookUrlList.getString(i);
                    Intrinsics.checkNotNullExpressionValue(bookUrl, "bookUrl");
                    bookInfo = this.getShelfBookByURL(bookUrl, (String)userNameSpace);
                    if (bookInfo == null) {
                        BookControllerKt.access$getLogger$p().info("\u672a\u627e\u5230\u4e66\u7c4d\u4fe1\u606f: {}", (Object)bookUrl);
                        continue;
                    }
                    if (bookInfo.isLocalBook()) {
                        BookControllerKt.access$getLogger$p().info("\u672c\u5730\u4e66\u7c4d\u8df3\u8fc7\u7f13\u5b58: {}", (Object)bookUrl);
                        continue;
                    }
                    BookControllerKt.access$getLogger$p().info("\u5f00\u59cb\u7f13\u5b58\u4e66\u7c4d: {}", (Object)bookInfo);
                    bookSource = this.getBookSourceStringBySourceURLOpt(bookInfo.getOrigin(), (String)userNameSpace);
                    var10_13 = bookSource;
                    var11_14 = false;
                    var12_16 = false;
                    if (var10_13 == null || var10_13.length() == 0) {
                        BookControllerKt.access$getLogger$p().info("\u672a\u627e\u5230\u4e66\u6e90\u4fe1\u606f: {}", (Object)bookUrl);
                        continue;
                    }
                    $continuation.L$0 = this;
                    $continuation.L$1 = bookUrlList;
                    $continuation.L$2 = userNameSpace;
                    $continuation.L$3 = bookInfo;
                    $continuation.L$4 = bookSource;
                    $continuation.L$5 = null;
                    $continuation.L$6 = null;
                    $continuation.L$7 = null;
                    $continuation.L$8 = null;
                    $continuation.I$0 = var4_7;
                    $continuation.I$1 = var5_8;
                    $continuation.label = 1;
                    v0 = BookController.getLocalChapterList$default(this, bookInfo, bookSource, false, (String)userNameSpace, false, null, $continuation, 48, null);
                    if (v0 == var26_6) {
                        return var26_6;
                    }
                    ** GOTO lbl60
                    break;
                }
            }
            case 1: {
                var5_8 = $continuation.I$1;
                var4_7 = $continuation.I$0;
                var9_12 = (String)$continuation.L$4;
                var8_11 = (Book)$continuation.L$3;
                var2_2 = (String)$continuation.L$2;
                var1_1 = (JsonArray)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl60:
                // 2 sources

                chapterList = (List)v0;
                cachedChapterContentSet = this.getCachedChapterContentSet(var8_11, var2_2);
                localCacheDir = this.getChapterCacheDir(var8_11, var2_2);
                var13_18 = 0;
                var14_19 = chapterList.size() + -1;
                if (var13_18 > var14_19) ** GOTO lbl163
                while (true) {
                    if (cachedChapterContentSet.contains(Boxing.boxInt(j = var13_18++))) continue;
                    chapterIndex = j;
                    chapterInfo = (BookChapter)chapterList.get(j);
                    nextChapterUrl = null;
                    if (chapterIndex + 1 < chapterList.size()) {
                        nextChapterInfo = (BookChapter)chapterList.get(chapterIndex + 1);
                        nextChapterUrl = nextChapterInfo.getUrl();
                    }
                    $continuation.L$0 = this;
                    $continuation.L$1 = var1_1;
                    $continuation.L$2 = var2_2;
                    $continuation.L$3 = var8_11;
                    $continuation.L$4 = var9_12;
                    $continuation.L$5 = chapterList;
                    $continuation.L$6 = cachedChapterContentSet;
                    $continuation.L$7 = localCacheDir;
                    $continuation.L$8 = chapterInfo;
                    $continuation.I$0 = var4_7;
                    $continuation.I$1 = var5_8;
                    $continuation.I$2 = var13_18;
                    $continuation.I$3 = var14_19;
                    $continuation.I$4 = chapterIndex;
                    $continuation.label = 2;
                    v1 = new WebBook(var9_12, this.getAppConfig().getDebugLog(), null, var2_2, 4, null).getBookContent(var8_11, chapterInfo, nextChapterUrl, $continuation);
                    ** if (v1 != var26_6) goto lbl94
lbl93:
                    // 1 sources

                    return var26_6;
lbl94:
                    // 1 sources

                    ** GOTO lbl114
                    break;
                }
            }
            case 2: {
                chapterIndex = $continuation.I$4;
                var14_19 = $continuation.I$3;
                var13_18 = $continuation.I$2;
                var5_8 = $continuation.I$1;
                var4_7 = $continuation.I$0;
                chapterInfo = (BookChapter)$continuation.L$8;
                localCacheDir = (File)$continuation.L$7;
                cachedChapterContentSet = (Set<Integer>)$continuation.L$6;
                chapterList = (List)$continuation.L$5;
                var9_12 = (String)$continuation.L$4;
                var8_11 = (Book)$continuation.L$3;
                var2_2 = (String)$continuation.L$2;
                var1_1 = (JsonArray)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v1 = $result;
lbl114:
                // 2 sources

                content = (String)v1;
                chapterCacheFile = new File(localCacheDir.getAbsolutePath() + File.separator + chapterIndex + ".txt");
                FilesKt.writeText$default(chapterCacheFile, content, null, 2, null);
                var22_28 = BookSource.Companion.fromJson-IoAF18A(var9_12);
                var23_29 = false;
                var21_27 = (BookSource)(Result.isFailure-impl(var22_28) != false ? null : var22_28);
                $continuation.L$0 = this;
                $continuation.L$1 = var1_1;
                $continuation.L$2 = var2_2;
                $continuation.L$3 = var8_11;
                $continuation.L$4 = var9_12;
                $continuation.L$5 = chapterList;
                $continuation.L$6 = cachedChapterContentSet;
                $continuation.L$7 = localCacheDir;
                $continuation.L$8 = null;
                $continuation.I$0 = var4_7;
                $continuation.I$1 = var5_8;
                $continuation.I$2 = var13_18;
                $continuation.I$3 = var14_19;
                $continuation.I$4 = chapterIndex;
                $continuation.label = 3;
                v2 = BookHelp.INSTANCE.saveImages(this, var21_27 == null ? new BookSource(null, null, null, 0, null, 0, false, false, null, null, null, null, null, null, null, null, 0L, 0L, 0, null, null, null, null, null, null, null, 0x3FFFFFF, null) : var21_27, var8_11, chapterInfo, content, $continuation);
                ** if (v2 != var26_6) goto lbl138
lbl137:
                // 1 sources

                return var26_6;
lbl138:
                // 1 sources

                ** GOTO lbl157
            }
            case 3: {
                var16_21 = $continuation.I$4;
                var14_19 = $continuation.I$3;
                var13_18 = $continuation.I$2;
                var5_8 = $continuation.I$1;
                var4_7 = $continuation.I$0;
                var12_17 = (File)$continuation.L$7;
                var11_15 = (Set)$continuation.L$6;
                var10_13 = (List)$continuation.L$5;
                var9_12 = (String)$continuation.L$4;
                var8_11 = (Book)$continuation.L$3;
                var2_2 = (String)$continuation.L$2;
                var1_1 = (JsonArray)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                try {
                    ResultKt.throwOnFailure($result);
                    v2 = $result;
lbl157:
                    // 2 sources

                    var11_15.add(Boxing.boxInt(var16_21));
                }
                catch (Exception e) {
                    BookControllerKt.access$getLogger$p().info("cacheBookOnServer error: {}", (Object)e.getMessage());
                }
                if (var13_18 <= var14_19) ** continue;
lbl163:
                // 2 sources

                BookControllerKt.access$getLogger$p().info("\u7f13\u5b58\u4e66\u7c4d\u5b8c\u6210: {}", (Object)var8_11);
                if (var4_7 < var5_8) ** continue;
lbl165:
                // 2 sources

                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object deleteBookCache(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof deleteBookCache.1)) ** GOTO lbl-1000
        var9_3 = var2_2;
        if ((var9_3.label & -2147483648) != 0) {
            var9_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.deleteBookCache(null, this);
                }
            };
        }
        $result = $continuation.result;
        var10_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var10_5) {
                    return var10_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                var4_7 = null;
                if (context.request().method() == HttpMethod.POST) {
                    var6_8 = context.getBodyAsJson().getString("url");
                    var5_11 = var6_8 == null ? context.getBodyAsJson().getString("bookUrl") : var6_8;
                    var4_7 = var5_11 == null ? "" : var5_11;
                } else {
                    var6_8 = context.queryParam("url");
                    Intrinsics.checkNotNullExpressionValue(var6_8, "context.queryParam(\"url\")");
                    var5_11 = (String)CollectionsKt.firstOrNull(var6_8);
                    bookUrl = var5_11 == null ? "" : var5_11;
                }
                var5_11 = (CharSequence)bookUrl;
                var6_9 = false;
                var7_12 = false;
                if (var5_11.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u4e66\u7c4d\u94fe\u63a5");
                }
                userNameSpace = this.getUserNameSpace(context);
                bookInfo = this.getShelfBookByURL((String)bookUrl, userNameSpace);
                if (bookInfo == null) {
                    return returnData.setErrorMsg("\u8bf7\u5148\u52a0\u5165\u4e66\u67b6");
                }
                if (bookInfo.isLocalBook()) {
                    return returnData.setErrorMsg("\u672c\u5730\u4e66\u7c4d\u65e0\u9700\u5220\u9664\u7f13\u5b58");
                }
                localCacheDir = this.getChapterCacheDir(bookInfo, userNameSpace);
                ExtKt.deleteRecursively(localCacheDir);
                return ReturnData.setData$default(returnData, "", null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object textToSpeech(@NotNull RoutingContext var1_1, @NotNull Continuation<? super Unit> var2_2) {
        if (!(var2_2 instanceof textToSpeech.1)) ** GOTO lbl-1000
        var14_3 = var2_2;
        if ((var14_3.label & -2147483648) != 0) {
            var14_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.textToSpeech(null, this);
                }
            };
        }
        $result = $continuation.result;
        var15_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                response = context.response();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = response;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var15_5) {
                    return var15_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                response = (HttpServerResponse)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    response.setStatusCode(403).end("\u672a\u767b\u5f55");
                    return Unit.INSTANCE;
                }
                text = new Ref.ObjectRef<String>();
                type = new Ref.ObjectRef<String>();
                var6_9 = null;
                var7_10 = null;
                var8_11 = null;
                var9_12 = null;
                if (context.request().method() == HttpMethod.POST) {
                    var10_13 = context.getBodyAsJson().getString("text");
                    text.element = var10_13 == null ? "" : var10_13;
                    var10_13 = context.getBodyAsJson().getString("type");
                    type.element = var10_13 == null ? "" : var10_13;
                    var10_13 = context.getBodyAsJson().getString("voice");
                    voice = var10_13 == null ? "" : var10_13;
                    var10_13 = context.getBodyAsJson().getString("pitch");
                    pitch = var10_13 == null ? "" : var10_13;
                    var10_13 = context.getBodyAsJson().getString("rate");
                    rate = var10_13 == null ? "" : var10_13;
                    var10_13 = context.getBodyAsJson().getString("base64");
                    var9_12 = var10_13 == null ? "" : var10_13;
                } else {
                    var11_14 = context.queryParam("text");
                    Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"text\")");
                    var10_13 = CollectionsKt.firstOrNull(var11_14);
                    text.element = var10_13 == null ? "" : var10_13;
                    var11_14 = context.queryParam("type");
                    Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"type\")");
                    var10_13 = CollectionsKt.firstOrNull(var11_14);
                    type.element = var10_13 == null ? "" : var10_13;
                    var11_14 = context.queryParam("voice");
                    Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"voice\")");
                    var10_13 = CollectionsKt.firstOrNull(var11_14);
                    voice = var10_13 == null ? "" : var10_13;
                    var11_14 = context.queryParam("pitch");
                    Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"pitch\")");
                    var10_13 = CollectionsKt.firstOrNull(var11_14);
                    pitch = var10_13 == null ? "" : var10_13;
                    var11_14 = context.queryParam("rate");
                    Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"rate\")");
                    var10_13 = CollectionsKt.firstOrNull(var11_14);
                    rate = var10_13 == null ? "" : var10_13;
                    var11_14 = context.queryParam("base64");
                    Intrinsics.checkNotNullExpressionValue(var11_14, "context.queryParam(\"base64\")");
                    var10_13 = CollectionsKt.firstOrNull(var11_14);
                    base64 /* !! */  = var10_13 == null ? "" : var10_13;
                }
                var10_13 = (CharSequence)type.element;
                var11_15 = false;
                var12_17 = false;
                if (var10_13 == null || var10_13.length() == 0) {
                    type.element = "edge";
                }
                var10_13 = (CharSequence)text.element;
                var11_15 = false;
                var12_17 = false;
                if (var10_13 == null || var10_13.length() == 0) {
                    response.setStatusCode(404).end("\u53c2\u6570\u9519\u8bef");
                    return Unit.INSTANCE;
                }
                $i$f$CoroutineExceptionHandler = false;
                var12_18 = CoroutineExceptionHandler.Key;
                exceptionHandler = new CoroutineExceptionHandler((CoroutineExceptionHandler.Key)var12_18, response){
                    final /* synthetic */ HttpServerResponse $response$inlined;
                    {
                        this.$response$inlined = httpServerResponse;
                        super($super_call_param$1);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
                        void ex;
                        Throwable throwable = exception;
                        CoroutineContext ctx = context;
                        boolean bl = false;
                        BookControllerKt.access$getLogger$p().info("tts error: {}", (Object)ex.getMessage());
                        this.$response$inlined.setStatusCode(404).end();
                    }
                };
                var12_18 = new Pair[]{TuplesKt.to("voice", voice), TuplesKt.to("pitch", pitch), TuplesKt.to("rate", rate), TuplesKt.to("base64", base64 /* !! */ )};
                options = MapsKt.mapOf(var12_18);
                BuildersKt.launch$default(this, new MDCContext(null, 1, null).plus(Dispatchers.getIO()).plus(exceptionHandler), null, new Function2<CoroutineScope, Continuation<? super Unit>, Object>(type, this, response, text, options, context, null){
                    int label;
                    final /* synthetic */ Ref.ObjectRef<String> $type;
                    final /* synthetic */ BookController this$0;
                    final /* synthetic */ HttpServerResponse $response;
                    final /* synthetic */ Ref.ObjectRef<String> $text;
                    final /* synthetic */ Map<String, String> $options;
                    final /* synthetic */ RoutingContext $context;
                    {
                        this.$type = $type;
                        this.this$0 = $receiver;
                        this.$response = $response;
                        this.$text = $text;
                        this.$options = $options;
                        this.$context = $context;
                        super(2, $completion);
                    }

                    /*
                     * WARNING - void declaration
                     * Enabled force condition propagation
                     * Lifted jumps to return sites
                     */
                    @Nullable
                    public final Object invokeSuspend(@NotNull Object object) {
                        void $result;
                        Object object2;
                        block9: {
                            Object object3;
                            String string;
                            object2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                            switch (this.label) {
                                case 0: {
                                    ResultKt.throwOnFailure(object);
                                    string = (String)this.$type.element;
                                    if (!Intrinsics.areEqual(string, "edge")) break;
                                    HttpServerResponse httpServerResponse = this.$response;
                                    Intrinsics.checkNotNullExpressionValue(httpServerResponse, "response");
                                    this.label = 1;
                                    Object object4 = this.this$0.ttsByEdge(httpServerResponse, (String)this.$text.element, this.$options, this);
                                    if (object4 != object2) return Unit.INSTANCE;
                                    return object2;
                                }
                                case 1: {
                                    ResultKt.throwOnFailure($result);
                                    Object object4 = $result;
                                    return Unit.INSTANCE;
                                }
                            }
                            if (Intrinsics.areEqual(string, "textToSpeechCn")) {
                                HttpServerResponse httpServerResponse = this.$response;
                                Intrinsics.checkNotNullExpressionValue(httpServerResponse, "response");
                                this.label = 2;
                                object3 = this.this$0.ttsByTextToSpeechCn(httpServerResponse, (String)this.$text.element, this.$options, this);
                                if (object3 != object2) return Unit.INSTANCE;
                                return object2;
                            }
                            break block9;
                            {
                                case 2: {
                                    ResultKt.throwOnFailure($result);
                                    object3 = $result;
                                    return Unit.INSTANCE;
                                }
                            }
                        }
                        HttpServerResponse httpServerResponse = this.$response;
                        Intrinsics.checkNotNullExpressionValue(httpServerResponse, "response");
                        this.label = 3;
                        Object object5 = this.this$0.ttsByApi(httpServerResponse, (String)this.$text.element, this.this$0.getUserNameSpace(this.$context), this.$options, this);
                        if (object5 != object2) return Unit.INSTANCE;
                        return object2;
                        {
                            case 3: {
                                ResultKt.throwOnFailure($result);
                                object5 = $result;
                                return Unit.INSTANCE;
                            }
                        }
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }

                    @NotNull
                    public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                        return (Continuation)((Object)new /* invalid duplicate definition of identical inner class */);
                    }

                    @Nullable
                    public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Unit> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                }, 2, null);
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @Nullable
    public final Object ttsByEdge(@NotNull HttpServerResponse response2, @NotNull String text, @Nullable Map<String, String> options, @NotNull Continuation<? super Unit> $completion) {
        VoiceEnum voice = VoiceEnum.zh_CN_XiaoxiaoNeural;
        String rate = "0";
        String pitch = "0%";
        if (options != null) {
            Object object = options;
            String string = "voice";
            boolean bl = false;
            Map<String, String> map = object;
            boolean bl2 = false;
            if (map.containsKey(string)) {
                object = VoiceEnum.fromSortName(options.get("voice"));
                voice = object == null ? VoiceEnum.zh_CN_XiaoxiaoNeural : object;
            }
            object = options;
            string = "rate";
            bl = false;
            map = object;
            bl2 = false;
            if (map.containsKey(string)) {
                object = options.get("rate");
                rate = object == null ? "0" : object;
            }
            object = options;
            string = "pitch";
            bl = false;
            map = object;
            bl2 = false;
            if (map.containsKey(string)) {
                pitch = Intrinsics.stringPlus(options.get("pitch"), "%");
            }
        }
        TTSService ts = TTSService.builder().build();
        SSML ssml = SSML.builder().synthesisText(text).voice(voice).rate(rate).pitch(pitch).style(TtsStyleEnum.chat).build();
        byte[] mp3byte = ts.sendText(ssml);
        if (options != null && "1".equals(options.get("base64"))) {
            ReturnData returnData = new ReturnData();
            HttpServerResponse httpServerResponse = response2.putHeader("content-type", "application/json; charset=utf-8");
            String string = Base64.getEncoder().encodeToString(mp3byte);
            Intrinsics.checkNotNullExpressionValue(string, "getEncoder().encodeToString(mp3byte)");
            httpServerResponse.end(ExtKt.jsonEncode$default(ReturnData.setData$default(returnData, string, null, 2, null), false, 2, null));
        } else {
            response2.putHeader("Content-Type", "audio/mpeg").end(Buffer.buffer(mp3byte));
        }
        return Unit.INSTANCE;
    }

    public static /* synthetic */ Object ttsByEdge$default(BookController bookController, HttpServerResponse httpServerResponse, String string, Map map, Continuation continuation, int n, Object object) {
        if ((n & 4) != 0) {
            map = null;
        }
        return bookController.ttsByEdge(httpServerResponse, string, map, continuation);
    }

    @Nullable
    public final HttpTTS getHttpTTSByName(@NotNull String name, @NotNull String userNameSpace) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(userNameSpace, "userNameSpace");
        CharSequence charSequence = name;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return null;
        }
        String[] stringArray = new String[]{"httpTTS"};
        JsonArray list2 = ExtKt.asJsonArray(this.getUserStorage(userNameSpace, stringArray));
        if (list2 == null) {
            return null;
        }
        int n = 0;
        int n2 = list2.size();
        if (n < n2) {
            do {
                int i = n++;
                Object object = list2.getJsonObject(i).toString();
                Intrinsics.checkNotNullExpressionValue(object, "list.getJsonObject(i).toString()");
                object = HttpTTS.Companion.fromJson-IoAF18A((String)object);
                boolean bl2 = false;
                HttpTTS httpTTS = (HttpTTS)(Result.isFailure-impl(object) ? null : object);
                if (httpTTS == null || !httpTTS.getName().equals(name)) continue;
                return httpTTS;
            } while (n < n2);
        }
        return null;
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object ttsByApi(@NotNull HttpServerResponse var1_1, @NotNull String var2_2, @NotNull String var3_3, @Nullable Map<String, String> var4_4, @NotNull Continuation<? super Unit> var5_5) {
        if (!(var5_5 instanceof ttsByApi.1)) ** GOTO lbl-1000
        var16_6 = var5_5;
        if ((var16_6.label & -2147483648) != 0) {
            var16_6.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var5_5){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.ttsByApi(null, null, null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var17_8 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                var7_9 = options;
                voice = var7_9 == null ? null : (String)var7_9.get("voice");
                var7_9 = voice;
                var8_11 = false;
                var9_13 = false;
                if (var7_9 == null || var7_9.length() == 0) {
                    response.setStatusCode(404).end();
                    return Unit.INSTANCE;
                }
                httpTTS = this.getHttpTTSByName(voice, (String)userNameSpace);
                if (httpTTS == null) {
                    response.setStatusCode(404).end();
                    return Unit.INSTANCE;
                }
                var10_14 = options;
                if (var10_14 == null) {
                    v0 = 1.0;
                } else {
                    var11_15 = (String)var10_14.get("rate");
                    if (var11_15 == null) {
                        v0 = 1.0;
                    } else {
                        var13_16 = var11_15;
                        var14_17 = false;
                        var12_18 = Boxing.boxDouble(Double.parseDouble(var13_16));
                        v0 = var12_18 == null ? 1.0 : var12_18.doubleValue();
                    }
                }
                speechRate = v0;
                speechRate = (double)5 + (speechRate - 0.5) * (double)30;
                $continuation.L$0 = response;
                $continuation.L$1 = options;
                $continuation.L$2 = httpTTS;
                $continuation.label = 1;
                v1 = this.getSpeakStream(httpTTS, (String)text, (int)speechRate, $continuation);
                if (v1 == var17_8) {
                    return var17_8;
                }
                ** GOTO lbl52
            }
            case 1: {
                var7_9 = (HttpTTS)$continuation.L$2;
                var4_4 = (Map)$continuation.L$1;
                var1_1 = (HttpServerResponse)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v1 = $result;
lbl52:
                // 2 sources

                if ((stream = (InputStream)v1) != null) {
                    if (var4_4 != null && "1".equals(var4_4.get("base64"))) {
                        returnData = new ReturnData();
                        v2 = var1_1.putHeader("content-type", "application/json; charset=utf-8");
                        var12_18 = Base64.getEncoder().encodeToString(ByteStreamsKt.readBytes(stream));
                        Intrinsics.checkNotNullExpressionValue(var12_18, "getEncoder().encodeToString(stream.readBytes())");
                        v2.end(ExtKt.jsonEncode$default(ReturnData.setData$default(returnData, var12_18, null, 2, null), false, 2, null));
                    } else {
                        var11_15 = var7_9.getContentType();
                        var1_1.putHeader("Content-Type", var11_15 == null ? "audio/mpeg" : var11_15).end(Buffer.buffer(ByteStreamsKt.readBytes(stream)));
                    }
                } else {
                    var1_1.setStatusCode(404).end();
                }
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object ttsByApi$default(BookController bookController, HttpServerResponse httpServerResponse, String string, String string2, Map map, Continuation continuation, int n, Object object) {
        if ((n & 8) != 0) {
            map = null;
        }
        return bookController.ttsByApi(httpServerResponse, string, string2, map, continuation);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object getSpeakStream(@NotNull HttpTTS var1_1, @NotNull String var2_2, int var3_3, @NotNull Continuation<? super InputStream> var4_4) {
        if (!(var4_4 instanceof getSpeakStream.1)) ** GOTO lbl-1000
        var22_5 = var4_4;
        if ((var22_5.label & -2147483648) != 0) {
            var22_5.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var4_4){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                Object L$5;
                Object L$6;
                int I$0;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.getSpeakStream(null, null, 0, this);
                }
            };
        }
        $result = $continuation.result;
        var23_7 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                downloadErrorNo = new Ref.IntRef();
                while (true) {
                    analyzeUrl = new AnalyzeUrl(httpTts.getUrl(), null, null, speakText, Boxing.boxInt(speechRate), null, httpTts, null, null, httpTts.getHeaderMap(true), Debug.INSTANCE, 422, null);
                    var19_16 = response = new Ref.ObjectRef<T>();
                    $continuation.L$0 = this;
                    $continuation.L$1 = httpTts;
                    $continuation.L$2 = speakText;
                    $continuation.L$3 = downloadErrorNo;
                    $continuation.L$4 = analyzeUrl;
                    $continuation.L$5 = response;
                    $continuation.L$6 = var19_16;
                    $continuation.I$0 = speechRate;
                    $continuation.label = 1;
                    v0 = analyzeUrl.getResponseAwait($continuation);
                    ** if (v0 != var23_7) goto lbl31
lbl30:
                    // 1 sources

                    return var23_7;
lbl31:
                    // 1 sources

                    ** GOTO lbl45
                    break;
                }
            }
            case 1: {
                speechRate = $continuation.I$0;
                var19_16 = (Ref.ObjectRef<T>)$continuation.L$6;
                response = (Ref.ObjectRef<T>)$continuation.L$5;
                analyzeUrl = (AnalyzeUrl)$continuation.L$4;
                downloadErrorNo = (Ref.IntRef)$continuation.L$3;
                speakText = (String)$continuation.L$2;
                httpTts = (HttpTTS)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                try {
                    ResultKt.throwOnFailure($result);
                    v0 = $result;
lbl45:
                    // 2 sources

                    var20_17 = v0;
                    var19_16.element = var20_17;
                    JobKt.ensureActive(this.getCoroutineContext());
                    checkJs = httpTts.getLoginCheckJs();
                    var9_18 = checkJs;
                    if (var9_18 == null) {
                        v1 = false;
                    } else {
                        var10_19 = (CharSequence)var9_18;
                        var11_21 = false;
                        v1 = StringsKt.isBlank((CharSequence)var10_19) == false == true;
                    }
                    if (v1) {
                        var9_18 = analyzeUrl.evalJS(checkJs, response.element);
                        if (var9_18 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type okhttp3.Response");
                        }
                        response.element = (Response)var9_18;
                    }
                    if ((var9_18 = ((Response)response.element).headers().get("Content-Type")) != null) {
                        var10_19 = var9_18;
                        var11_21 = false;
                        var12_22 = false;
                        contentType = var10_19;
                        $i$a$-let-BookController$getSpeakStream$2 = false;
                        ct = httpTts.getContentType();
                        if (Intrinsics.areEqual(contentType, "application/json")) {
                            v2 = ((Response)response.element).body();
                            Intrinsics.checkNotNull(v2);
                            throw new NoStackTraceException(v2.string());
                        }
                        var16_28 /* !! */  = ct;
                        if (var16_28 /* !! */  == null) {
                            v3 = false;
                        } else {
                            var17_29 = var16_28 /* !! */ ;
                            var18_30 = false;
                            v3 = StringsKt.isBlank((CharSequence)var17_29) == false == true;
                        }
                        if (v3) {
                            var16_28 /* !! */  = (CharSequence)contentType;
                            var17_29 = ct;
                            var18_30 = false;
                            var17_29 = new Regex((String)var17_29);
                            var18_30 = false;
                            if (!var17_29.matches(var16_28 /* !! */ )) {
                                v4 = ((Response)response.element).body();
                                Intrinsics.checkNotNull(v4);
                                throw new NoStackTraceException(Intrinsics.stringPlus("TTS\u670d\u52a1\u5668\u8fd4\u56de\u9519\u8bef\uff1a", v4.string()));
                            }
                        }
                    }
                    JobKt.ensureActive(this.getCoroutineContext());
                    v5 = ((Response)response.element).body();
                    Intrinsics.checkNotNull(v5);
                    var9_18 = v5.byteStream();
                    var10_20 = false;
                    var11_21 = false;
                    stream = var9_18;
                    $i$a$-let-BookController$getSpeakStream$3 = false;
                    downloadErrorNo.element = 0;
                    return stream;
                }
                catch (Exception e) {
                    var7_11 = e;
                    if (var7_11 instanceof CancellationException) {
                        throw e;
                    }
                    if (var7_11 instanceof ScriptException != false ? true : var7_11 instanceof WrappedException) {
                        BookControllerKt.access$getLogger$p().error(Intrinsics.stringPlus("js\u9519\u8bef\n", e.getLocalizedMessage()), e);
                        throw e;
                    }
                    if (var7_11 instanceof SocketTimeoutException != false ? true : var7_11 instanceof ConnectException) {
                        checkJs = downloadErrorNo.element;
                        downloadErrorNo.element = checkJs + 1;
                        if (downloadErrorNo.element <= 5) ** continue;
                        msg = Intrinsics.stringPlus("tts\u8d85\u65f6\u6216\u8fde\u63a5\u9519\u8bef\u8d85\u8fc75\u6b21\n", e.getLocalizedMessage());
                        BookControllerKt.access$getLogger$p().error(msg, e);
                        throw e;
                    }
                    msg = downloadErrorNo.element;
                    downloadErrorNo.element = msg + 1;
                    BookControllerKt.access$getLogger$p().error(Intrinsics.stringPlus("tts\u4e0b\u8f7d\u9519\u8bef\n", e.getLocalizedMessage()), e);
                    if (downloadErrorNo.element > 5) {
                        msg1 = "TTS\u670d\u52a1\u5668\u8fde\u7eed5\u6b21\u9519\u8bef\uff0c\u5df2\u6682\u505c\u9605\u8bfb\u3002";
                        BookControllerKt.access$getLogger$p().error(msg1, e);
                        throw e;
                    }
                    BookControllerKt.access$getLogger$p().error(Intrinsics.stringPlus("TTS\u4e0b\u8f7d\u97f3\u9891\u51fa\u9519\uff0c\u4f7f\u7528\u65e0\u58f0\u97f3\u9891\u4ee3\u66ff\u3002\n\u6717\u8bfb\u6587\u672c\uff1a", speakText));
                    return null;
                }
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object ttsByTextToSpeechCn(@NotNull HttpServerResponse var1_1, @NotNull String var2_2, @Nullable Map<String, String> var3_3, @NotNull Continuation<? super Unit> var4_4) {
        if (!(var4_4 instanceof ttsByTextToSpeechCn.1)) ** GOTO lbl-1000
        var11_5 = var4_4;
        if ((var11_5.label & -2147483648) != 0) {
            var11_5.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var4_4){
                Object L$0;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.ttsByTextToSpeechCn(null, null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var12_7 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                var6_8 = new Pair[]{TuplesKt.to("language", "\u4e2d\u6587\uff08\u666e\u901a\u8bdd\uff0c\u7b80\u4f53\uff09"), TuplesKt.to("voice", "zh-CN-XiaoxiaoNeural"), TuplesKt.to("text", text), TuplesKt.to("role", "0"), TuplesKt.to("style", "0"), TuplesKt.to("rate", "0"), TuplesKt.to("pitch", "0"), TuplesKt.to("kbitrate", "audio-16khz-32kbitrate-mono-mp3"), TuplesKt.to("silence", ""), TuplesKt.to("styledegree", "1"), TuplesKt.to("user_id", ""), TuplesKt.to("yzm", "")};
                map = MapsKt.mutableMapOf(var6_8);
                if (options != null) {
                    map.putAll(options);
                }
                multiMap = new CaseInsensitiveHeaders();
                var7_10 = new Function2<String, String, Unit>(multiMap){

                    public final void invoke(String p0, String p1) {
                        BookController.access$ttsByTextToSpeechCn$add((CaseInsensitiveHeaders)ttsByTextToSpeechCn.2.access$getReceiver$p(this), p0, p1);
                    }

                    public static final /* synthetic */ Object access$getReceiver$p(ttsByTextToSpeechCn.2 $this) {
                        return $this.receiver;
                    }
                };
                map.forEach(new BiConsumer(var7_10){
                    private final /* synthetic */ Function2 function;
                    {
                        this.function = function;
                    }

                    public final /* synthetic */ void accept(Object p0, Object p1) {
                        this.function.invoke(p0, p1);
                    }
                });
                ttsUrl = "https://www.text-to-speech.cn/getSpeek.php";
                $continuation.L$0 = response;
                $continuation.label = 1;
                v0 = VertxCoroutineKt.awaitResult((Function1)new Function1<Handler<AsyncResult<HttpResponse<Buffer>>>, Unit>(this, ttsUrl, multiMap){
                    final /* synthetic */ BookController this$0;
                    final /* synthetic */ String $ttsUrl;
                    final /* synthetic */ CaseInsensitiveHeaders $multiMap;
                    {
                        this.this$0 = $receiver;
                        this.$ttsUrl = $ttsUrl;
                        this.$multiMap = $multiMap;
                        super(1);
                    }

                    public final void invoke(@NotNull Handler<AsyncResult<HttpResponse<Buffer>>> handler2) {
                        Intrinsics.checkNotNullParameter(handler2, "handler");
                        BookController.access$getWebClient$p(this.this$0).postAbs(this.$ttsUrl).timeout(5000L).putHeader("Origin", "https://www.text-to-speech.cn").putHeader("Referer", "https://www.text-to-speech.cn/").putHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36").sendForm(this.$multiMap, handler2);
                    }
                }, $continuation);
                if (v0 == var12_7) {
                    return var12_7;
                }
                ** GOTO lbl30
            }
            case 1: {
                response = (HttpServerResponse)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl30:
                // 2 sources

                result = (HttpResponse)v0;
                BookControllerKt.access$getLogger$p().info("res: {}", (Object)result);
                if (result != null) {
                    jsonRes = result.bodyAsJsonObject();
                    BookControllerKt.access$getLogger$p().info("jsonRes: {}", (Object)jsonRes);
                    if (jsonRes != null && jsonRes.getString("download") != null) {
                        response.setStatusCode(302).putHeader("Location", jsonRes.getString("download")).end();
                    } else {
                        response.setStatusCode(404).end();
                    }
                } else {
                    response.setStatusCode(404).end();
                }
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object ttsByTextToSpeechCn$default(BookController bookController, HttpServerResponse httpServerResponse, String string, Map map, Continuation continuation, int n, Object object) {
        if ((n & 4) != 0) {
            map = null;
        }
        return bookController.ttsByTextToSpeechCn(httpServerResponse, string, map, continuation);
    }

    @NotNull
    public final File getChapterCacheDir(@NotNull Book bookInfo, @NotNull String userNameSpace) {
        Intrinsics.checkNotNullParameter(bookInfo, "bookInfo");
        Intrinsics.checkNotNullParameter(userNameSpace, "userNameSpace");
        String md5Encode = MD5Utils.INSTANCE.md5Encode(bookInfo.getBookUrl()).toString();
        String[] stringArray = new String[]{"storage", "data", userNameSpace, bookInfo.getName() + '_' + bookInfo.getAuthor(), md5Encode};
        String localCacheDirPath = ExtKt.getWorkDir(stringArray);
        File localCacheDir = new File(localCacheDirPath);
        if (!localCacheDir.exists()) {
            localCacheDir.mkdirs();
        }
        return localCacheDir;
    }

    @NotNull
    public final Set<Integer> getCachedChapterContentSet(@NotNull Book bookInfo, @NotNull String userNameSpace) {
        Intrinsics.checkNotNullParameter(bookInfo, "bookInfo");
        Intrinsics.checkNotNullParameter(userNameSpace, "userNameSpace");
        File localCacheDir = this.getChapterCacheDir(bookInfo, userNameSpace);
        boolean bl = false;
        Set cachedChapterContentSet = new LinkedHashSet();
        File[] fileArray = localCacheDir.listFiles();
        Intrinsics.checkNotNullExpressionValue(fileArray, "localCacheDir.listFiles()");
        Object[] $this$forEach$iv = fileArray;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            File it = (File)element$iv;
            boolean bl2 = false;
            String string = it.getName();
            Intrinsics.checkNotNullExpressionValue(string, "it.name");
            if (StringsKt.startsWith$default(string, ".", false, 2, null)) continue;
            string = it.getName();
            Intrinsics.checkNotNullExpressionValue(string, "it.name");
            if (!StringsKt.endsWith$default(string, ".txt", false, 2, null)) continue;
            string = it.getName();
            Intrinsics.checkNotNullExpressionValue(string, "it.name");
            string = StringsKt.replace$default(string, ".txt", "", false, 4, null);
            boolean bl3 = false;
            cachedChapterContentSet.add(Integer.parseInt(string));
        }
        return cachedChapterContentSet;
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getShelfBookWithCacheInfo(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof getShelfBookWithCacheInfo.1)) ** GOTO lbl-1000
        var14_3 = var2_2;
        if ((var14_3.label & -2147483648) != 0) {
            var14_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.getShelfBookWithCacheInfo(null, this);
                }
            };
        }
        $result = $continuation.result;
        var15_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var15_5) {
                    return var15_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                userNameSpace = this.getUserNameSpace(context);
                $continuation.L$0 = this;
                $continuation.L$1 = returnData;
                $continuation.L$2 = userNameSpace;
                $continuation.label = 2;
                v1 = this.getBookShelfBooks(false, userNameSpace, $continuation);
                if (v1 == var15_5) {
                    return var15_5;
                }
                ** GOTO lbl44
            }
            case 2: {
                userNameSpace = (String)$continuation.L$2;
                var3_6 = (ReturnData)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v1 = $result;
lbl44:
                // 2 sources

                bookList = (List)v1;
                var7_9 = 0;
                result = new ArrayList<E>();
                var7_9 = 0;
                var8_11 = bookList.size();
                if (var7_9 < var8_11) {
                    do {
                        if (!(bookInfo = (Book)bookList.get(i = var7_9++)).isLocalBook()) {
                            cachedSet = this.getCachedChapterContentSet(bookInfo, userNameSpace);
                            bookInfoMap = TypeIntrinsics.asMutableMap(ExtKt.toMap(bookInfo));
                            bookInfoMap.put("cachedChapterCount", Boxing.boxInt(cachedSet.size()));
                            result.add(bookInfoMap);
                            continue;
                        }
                        result.add(bookInfo);
                    } while (var7_9 < var8_11);
                }
                return ReturnData.setData$default(var3_6, result, null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object exportBook(@NotNull RoutingContext var1_1, @NotNull Continuation<? super Unit> var2_2) {
        if (!(var2_2 instanceof exportBook.1)) ** GOTO lbl-1000
        var13_3 = var2_2;
        if ((var13_3.label & -2147483648) != 0) {
            var13_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                int I$0;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.exportBook(null, this);
                }
            };
        }
        $result = $continuation.result;
        var14_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var14_5) {
                    return var14_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    VertExtKt.success(context, ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528"));
                    return Unit.INSTANCE;
                }
                var4_7 = null;
                var5_8 = 0;
                if (context.request().method() == HttpMethod.POST) {
                    var7_9 = context.getBodyAsJson().getString("url");
                    var6_12 = var7_9 == null ? context.getBodyAsJson().getString("bookUrl") : var7_9;
                    bookUrl = var6_12 == null ? "" : var6_12;
                    var6_12 = context.getBodyAsJson().getInteger("isEpub", Boxing.boxInt(0));
                    Intrinsics.checkNotNullExpressionValue(var6_12, "context.bodyAsJson.getInteger(\"isEpub\", 0)");
                    var5_8 = ((Number)var6_12).intValue();
                } else {
                    var7_9 = context.queryParam("url");
                    Intrinsics.checkNotNullExpressionValue(var7_9, "context.queryParam(\"url\")");
                    var6_12 = (String)CollectionsKt.firstOrNull(var7_9);
                    bookUrl = var6_12 == null ? "" : var6_12;
                    var7_9 = context.queryParam("isEpub");
                    Intrinsics.checkNotNullExpressionValue(var7_9, "context.queryParam(\"isEpub\")");
                    var6_12 = CollectionsKt.firstOrNull(var7_9);
                    if (var6_12 == null) {
                        v1 = 0;
                    } else {
                        var8_13 = var6_12;
                        var9_18 = false;
                        var7_9 = Boxing.boxInt(Integer.parseInt((String)var8_13));
                        v1 = var7_9 == null ? 0 : var7_9.intValue();
                    }
                    isEpub = v1;
                }
                var6_12 = (CharSequence)bookUrl;
                var7_10 = false;
                var8_14 = false;
                if (var6_12.length() == 0) {
                    VertExtKt.success(context, returnData.setErrorMsg("\u8bf7\u8f93\u5165\u4e66\u7c4d\u94fe\u63a5"));
                    return Unit.INSTANCE;
                }
                userNameSpace = this.getUserNameSpace(context);
                bookInfo = this.getShelfBookByURL((String)bookUrl, userNameSpace);
                if (bookInfo == null) {
                    VertExtKt.success(context, returnData.setErrorMsg("\u8bf7\u5148\u52a0\u5165\u4e66\u67b6"));
                    return Unit.INSTANCE;
                }
                if (bookInfo.isLocalBook() && !bookInfo.isLocalTxt()) {
                    localFile = bookInfo.getLocalFile();
                    context.response().putHeader("Cache-Control", "300").putHeader("Content-Disposition", Intrinsics.stringPlus("attachment; filename=", URLEncoder.encode(localFile.getName(), "UTF-8"))).sendFile(localFile.toString());
                    return Unit.INSTANCE;
                }
                if (bookInfo.isLocalTxt() && isEpub <= 0) {
                    localFile = bookInfo.getLocalFile();
                    context.response().putHeader("Cache-Control", "300").putHeader("Content-Disposition", Intrinsics.stringPlus("attachment; filename=", URLEncoder.encode(localFile.getName(), "UTF-8"))).sendFile(localFile.toString());
                    return Unit.INSTANCE;
                }
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.L$3 = userNameSpace;
                $continuation.L$4 = bookInfo;
                $continuation.I$0 = isEpub;
                $continuation.label = 2;
                v2 = BookController.getBookSourceString$default(this, context, bookInfo.getOrigin(), false, $continuation, 4, null);
                if (v2 == var14_5) {
                    return var14_5;
                }
                ** GOTO lbl96
            }
            case 2: {
                var5_8 = $continuation.I$0;
                var7_11 = (Book)$continuation.L$4;
                var6_12 = (String)$continuation.L$3;
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v2 = $result;
lbl96:
                // 2 sources

                bookSource = (String)v2;
                if (!var7_11.isLocalBook()) {
                    var9_19 = bookSource;
                    var10_21 = false;
                    var11_23 = false;
                    if (var9_19 == null || var9_19.length() == 0) {
                        VertExtKt.success(var1_1, var3_6.setErrorMsg("\u672a\u914d\u7f6e\u4e66\u6e90"));
                        return Unit.INSTANCE;
                    }
                }
                var10_22 = new String[]{"storage", "assets", var6_12, "export"};
                exportDir = new File(ExtKt.getWorkDir(var10_22));
                if (var5_8 <= 0) break;
                $continuation.L$0 = var1_1;
                $continuation.L$1 = null;
                $continuation.L$2 = null;
                $continuation.L$3 = null;
                $continuation.L$4 = null;
                $continuation.label = 3;
                v3 = this.exportToEpub(exportDir, var7_11, bookSource, (String)var6_12, $continuation);
                if (v3 == var14_5) {
                    return var14_5;
                }
                ** GOTO lbl121
            }
            case 3: {
                var1_1 = (RoutingContext)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v3 = $result;
lbl121:
                // 2 sources

                v4 = (File)v3;
                ** GOTO lbl140
            }
        }
        v5 = var8_17;
        Intrinsics.checkNotNull(v5);
        $continuation.L$0 = var1_1;
        $continuation.L$1 = null;
        $continuation.L$2 = null;
        $continuation.L$3 = null;
        $continuation.L$4 = null;
        $continuation.label = 4;
        v6 = this.exportToTxt(exportDir, var7_11, (String)v5, (String)var6_12, $continuation);
        if (v6 == var14_5) {
            return var14_5;
        }
        ** GOTO lbl139
        {
            case 4: {
                var1_1 = (RoutingContext)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v6 = $result;
lbl139:
                // 2 sources

                v4 = (File)v6;
lbl140:
                // 2 sources

                bookFile = v4;
                var1_1.response().putHeader("Cache-Control", "300").putHeader("Content-Disposition", Intrinsics.stringPlus("attachment; filename=", URLEncoder.encode(bookFile.getName(), "UTF-8"))).sendFile(bookFile.toString());
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object exportToTxt(@NotNull File var1_1, @NotNull Book var2_2, @NotNull String var3_3, @NotNull String var4_4, @NotNull Continuation<? super File> var5_5) {
        if (!(var5_5 instanceof exportToTxt.1)) ** GOTO lbl-1000
        var10_6 = var5_5;
        if ((var10_6.label & -2147483648) != 0) {
            var10_6.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var5_5){
                Object L$0;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.exportToTxt(null, null, null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var11_8 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                filename = '\u300a' + bookInfo.getName() + "\u300b\u4f5c\u8005\uff1a" + bookInfo.getRealAuthor() + ".txt";
                var8_10 = new String[]{filename};
                bookPath = FileUtils.INSTANCE.getPath((File)exportDir, var8_10);
                bookFile = FileUtils.INSTANCE.createFileWithReplace(bookPath);
                $continuation.L$0 = bookFile;
                $continuation.label = 1;
                v0 = this.getAllContents((Book)bookInfo, (String)bookSource, (String)userNameSpace, (Function2<? super String, ? super ArrayList<Triple<String, Integer, String>>, Unit>)new Function2<String, ArrayList<Triple<? extends String, ? extends Integer, ? extends String>>, Unit>(bookFile, this){
                    final /* synthetic */ File $bookFile;
                    final /* synthetic */ BookController this$0;
                    {
                        this.$bookFile = $bookFile;
                        this.this$0 = $receiver;
                        super(2);
                    }

                    public final void invoke(@NotNull String text, @Nullable ArrayList<Triple<String, Integer, String>> srcList) {
                        Intrinsics.checkNotNullParameter(text, "text");
                        Charset charset = Charset.forName(this.this$0.getAppConfig().getExportCharset());
                        Intrinsics.checkNotNullExpressionValue(charset, "forName(appConfig.exportCharset)");
                        FilesKt.appendText(this.$bookFile, text, charset);
                    }
                }, $continuation);
                if (v0 == var11_8) {
                    return var11_8;
                }
                ** GOTO lbl26
            }
            case 1: {
                bookFile = (File)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl26:
                // 2 sources

                return bookFile;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    private final Object getAllContents(Book var1_1, String var2_2, String var3_3, Function2<? super String, ? super ArrayList<Triple<String, Integer, String>>, Unit> var4_4, Continuation<? super Unit> var5_5) {
        if (!(var5_5 instanceof getAllContents.1)) ** GOTO lbl-1000
        var22_6 = var5_5;
        if ((var22_6.label & -2147483648) != 0) {
            var22_6.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var5_5){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return BookController.access$getAllContents(this.this$0, null, null, null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var23_8 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                qy = book.getName() + "\n\u4f5c\u8005\uff1a" + book.getRealAuthor() + "\n\u7b80\u4ecb\uff1a" + HtmlFormatter.format$default(HtmlFormatter.INSTANCE, book.getDisplayIntro(), null, 2, null);
                append.invoke(qy, null);
                $continuation.L$0 = this;
                $continuation.L$1 = book;
                $continuation.L$2 = userNameSpace;
                $continuation.L$3 = append;
                $continuation.label = 1;
                v0 = BookController.getLocalChapterList$default(this, book, (String)bookSourceString, false, userNameSpace, false, null, $continuation, 48, null);
                if (v0 == var23_8) {
                    return var23_8;
                }
                ** GOTO lbl31
            }
            case 1: {
                append = (Function2)$continuation.L$3;
                userNameSpace = (String)$continuation.L$2;
                book = (Book)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl31:
                // 2 sources

                chapterList = (List)v0;
                localCacheDir = this.getChapterCacheDir(book, userNameSpace);
                $this$forEachIndexed$iv = chapterList;
                $i$f$forEachIndexed = false;
                index$iv = 0;
                for (T item$iv : $this$forEachIndexed$iv) {
                    var14_17 = index$iv++;
                    var15_18 = false;
                    if (var14_17 < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    var16_19 = (BookChapter)item$iv;
                    index = ((Number)Boxing.boxInt(var14_17)).intValue();
                    $i$a$-forEachIndexed-BookController$getAllContents$2 = false;
                    chapterCacheFile = new File(localCacheDir.getAbsolutePath() + File.separator + index + ".txt");
                    content = "";
                    if (!this.getAppConfig().getExportNoChapterName()) {
                        content = content + chapter.getTitle() + '\n';
                    }
                    content = chapterCacheFile.exists() != false ? content + FilesKt.readText$default(chapterCacheFile, null, 1, null) + '\n' : Intrinsics.stringPlus(content, "\u6682\u65e0\u7f13\u5b58\u5185\u5bb9\u3002\n");
                    append.invoke(Intrinsics.stringPlus("\n\n", content), null);
                }
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    private final Object exportToEpub(File var1_1, Book var2_2, String var3_3, String var4_4, Continuation<? super File> var5_5) {
        if (!(var5_5 instanceof exportToEpub.1)) ** GOTO lbl-1000
        var12_6 = var5_5;
        if ((var12_6.label & -2147483648) != 0) {
            var12_6.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var5_5){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                Object L$5;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return BookController.access$exportToEpub(this.this$0, null, null, null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var13_8 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                filename = '\u300a' + book.getName() + "\u300b\u4f5c\u8005\uff1a" + book.getRealAuthor() + ".epub";
                var8_10 = new String[]{filename};
                bookPath = FileUtils.INSTANCE.getPath((File)exportDir, var8_10);
                bookFile = FileUtils.INSTANCE.createFileWithReplace(bookPath);
                epubBook = new EpubBook();
                epubBook.setVersion("2.0");
                this.setEpubMetadata(book, epubBook);
                $continuation.L$0 = this;
                $continuation.L$1 = book;
                $continuation.L$2 = bookSource;
                $continuation.L$3 = userNameSpace;
                $continuation.L$4 = bookFile;
                $continuation.L$5 = epubBook;
                $continuation.label = 1;
                v0 = this.setCover(book, epubBook, bookSource, $continuation);
                if (v0 == var13_8) {
                    return var13_8;
                }
                ** GOTO lbl39
            }
            case 1: {
                epubBook = (EpubBook)$continuation.L$5;
                bookFile = (File)$continuation.L$4;
                userNameSpace = (String)$continuation.L$3;
                bookSource = (String)$continuation.L$2;
                book = (Book)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl39:
                // 2 sources

                contentModel = this.setAssets(book, epubBook);
                $continuation.L$0 = bookFile;
                $continuation.L$1 = epubBook;
                $continuation.L$2 = null;
                $continuation.L$3 = null;
                $continuation.L$4 = null;
                $continuation.L$5 = null;
                $continuation.label = 2;
                v1 = this.setEpubContent(contentModel, book, epubBook, bookSource, userNameSpace, $continuation);
                if (v1 == var13_8) {
                    return var13_8;
                }
                ** GOTO lbl56
            }
            case 2: {
                epubBook = (EpubBook)$continuation.L$1;
                bookFile = (File)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v1 = $result;
lbl56:
                // 2 sources

                new EpubWriter().write(epubBook, new FileOutputStream(bookFile));
                return bookFile;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    private final String setAssets(Book book, EpubBook epubBook) {
        Resources resources = epubBook.getResources();
        Object object = BookController.class.getResource("/epub/fonts.css");
        Intrinsics.checkNotNullExpressionValue(object, "BookController::class.java.getResource(\"/epub/fonts.css\")");
        resources.add(new Resource(TextStreamsKt.readBytes((URL)object), "Styles/fonts.css"));
        Resources resources2 = epubBook.getResources();
        object = BookController.class.getResource("/epub/main.css");
        Intrinsics.checkNotNullExpressionValue(object, "BookController::class.java.getResource(\"/epub/main.css\")");
        resources2.add(new Resource(TextStreamsKt.readBytes((URL)object), "Styles/main.css"));
        Resources resources3 = epubBook.getResources();
        object = BookController.class.getResource("/epub/logo.png");
        Intrinsics.checkNotNullExpressionValue(object, "BookController::class.java.getResource(\"/epub/logo.png\")");
        resources3.add(new Resource(TextStreamsKt.readBytes((URL)object), "Images/logo.png"));
        String string = book.getName();
        String string2 = book.getRealAuthor();
        String string3 = book.getDisplayIntro();
        String string4 = book.getKind();
        String string5 = book.getWordCount();
        object = BookController.class.getResource("/epub/cover.html");
        Intrinsics.checkNotNullExpressionValue(object, "BookController::class.java.getResource(\"/epub/cover.html\")");
        object = TextStreamsKt.readBytes((URL)object);
        boolean bl = false;
        epubBook.addSection("\u5c01\u9762", ResourceUtil.createPublicResource(string, string2, string3, string4, string5, new String((byte[])object, Charsets.UTF_8), "Text/cover.html"));
        String string6 = book.getName();
        String string7 = book.getRealAuthor();
        String string8 = book.getDisplayIntro();
        String string9 = book.getKind();
        String string10 = book.getWordCount();
        object = BookController.class.getResource("/epub/intro.html");
        Intrinsics.checkNotNullExpressionValue(object, "BookController::class.java.getResource(\"/epub/intro.html\")");
        object = TextStreamsKt.readBytes((URL)object);
        bl = false;
        epubBook.addSection("\u7b80\u4ecb", ResourceUtil.createPublicResource(string6, string7, string8, string9, string10, new String((byte[])object, Charsets.UTF_8), "Text/intro.html"));
        object = BookController.class.getResource("/epub/chapter.html");
        Intrinsics.checkNotNullExpressionValue(object, "BookController::class.java.getResource(\"/epub/chapter.html\")");
        object = TextStreamsKt.readBytes((URL)object);
        bl = false;
        return new String((byte[])object, Charsets.UTF_8);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    private final Object setCover(Book var1_1, EpubBook var2_2, String var3_3, Continuation<? super Unit> var4_4) {
        if (!(var4_4 instanceof setCover.1)) ** GOTO lbl-1000
        var17_5 = var4_4;
        if ((var17_5.label & -2147483648) != 0) {
            var17_5.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var4_4){
                Object L$0;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return BookController.access$setCover(this.this$0, null, null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var18_7 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                coverUrl = book.getDisplayCover();
                if (coverUrl == null) ** GOTO lbl72
                if (!StringsKt.startsWith$default(coverUrl, "/", false, 2, null)) ** GOTO lbl30
                var7_9 = new String[2];
                var7_9[0] = "storage";
                var8_11 = File.separator;
                Intrinsics.checkNotNullExpressionValue(var8_11, "separator");
                var8_11 = StringsKt.replace$default(coverUrl, "/", var8_11, false, 4, null);
                var9_13 = 1;
                var10_15 = false;
                v0 = var8_11;
                if (v0 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                Intrinsics.checkNotNullExpressionValue(v0.substring(var9_13), "(this as java.lang.String).substring(startIndex)");
                coverFile = new File(ExtKt.getWorkDir(var7_9));
                byteArray = FilesKt.readBytes(coverFile);
                epubBook.setCoverImage(new Resource(byteArray, "Images/cover.jpg"));
                ** GOTO lbl72
lbl30:
                // 1 sources

                if (bookSourceString == null) ** GOTO lbl72
                ext = this.getFileExt(coverUrl, "jpg");
                md5Encode = MD5Utils.INSTANCE.md5Encode(coverUrl).toString();
                var9_14 = new String[]{"storage", "cache", md5Encode + '.' + ext};
                cachePath = ExtKt.getWorkDir(var9_14);
                cacheFile = new File(cachePath);
                if (cacheFile.exists()) {
                    byteArray = FilesKt.readBytes(cacheFile);
                    epubBook.setCoverImage(new Resource(byteArray, "Images/cover.jpg"));
                    return Unit.INSTANCE;
                }
                var11_20 = BookSource.Companion.fromJson-IoAF18A((String)bookSourceString);
                var12_23 = false;
                analyzeUrl = new AnalyzeUrl(coverUrl, null, null, null, null, null, (BaseSource)(Result.isFailure-impl(var11_20) != false ? null : var11_20), null, null, null, null, 1982, null);
                $continuation.L$0 = epubBook;
                $continuation.label = 1;
                v1 = analyzeUrl.getByteArrayAwait($continuation);
                ** if (v1 != var18_7) goto lbl50
lbl49:
                // 1 sources

                return var18_7;
lbl50:
                // 1 sources

                ** GOTO lbl58
            }
            case 1: {
                var2_2 = (EpubBook)$continuation.L$0;
                try {
                    ResultKt.throwOnFailure($result);
                    v1 = $result;
lbl58:
                    // 2 sources

                    var11_20 = v1;
                    var12_23 = false;
                    var13_24 = false;
                    it = (byte[])var11_20;
                    $i$a$-let-BookController$setCover$2 = false;
                    var2_2.setCoverImage(new Resource(it, "Images/cover.jpg"));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
lbl72:
                // 5 sources

                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    private final Object setEpubContent(String var1_1, Book var2_2, EpubBook var3_3, String var4_4, String var5_5, Continuation<? super Unit> var6_6) {
        if (!(var6_6 instanceof setEpubContent.1)) ** GOTO lbl-1000
        var23_7 = var6_6;
        if ((var23_7.label & -2147483648) != 0) {
            var23_7.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var6_6){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return BookController.access$setEpubContent(this.this$0, null, null, null, null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var24_9 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                $continuation.L$0 = this;
                $continuation.L$1 = contentModel;
                $continuation.L$2 = book;
                $continuation.L$3 = epubBook;
                $continuation.L$4 = userNameSpace;
                $continuation.label = 1;
                v0 = BookController.getLocalChapterList$default(this, book, (String)bookSourceString, false, userNameSpace, false, null, $continuation, 48, null);
                if (v0 == var24_9) {
                    return var24_9;
                }
                ** GOTO lbl30
            }
            case 1: {
                userNameSpace = (String)$continuation.L$4;
                epubBook = (EpubBook)$continuation.L$3;
                book = (Book)$continuation.L$2;
                contentModel = (String)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl30:
                // 2 sources

                chapterList = (List)v0;
                localCacheDir = this.getChapterCacheDir(book, userNameSpace);
                $this$forEachIndexed$iv = chapterList;
                $i$f$forEachIndexed = false;
                index$iv = 0;
                for (T item$iv : $this$forEachIndexed$iv) {
                    var14_17 = index$iv++;
                    var15_18 = false;
                    if (var14_17 < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    var16_19 = (BookChapter)item$iv;
                    index = ((Number)Boxing.boxInt(var14_17)).intValue();
                    $i$a$-forEachIndexed-BookController$setEpubContent$2 = false;
                    content = "";
                    if (!this.getAppConfig().getExportNoChapterName()) {
                        content = content + chapter.getTitle() + '\n';
                    }
                    content = book.isLocalTxt() != false ? Intrinsics.stringPlus(content, (var20_23 = LocalBook.INSTANCE.getContent(book, (BookChapter)chapter)) == null ? "" : var20_23) : ((chapterCacheFile = new File(localCacheDir.getAbsolutePath() + File.separator + index + ".txt")).exists() != false ? content + FilesKt.readText$default(chapterCacheFile, null, 1, null) + '\n' : Intrinsics.stringPlus(content, "\u6682\u65e0\u7f13\u5b58\u5185\u5bb9\u3002\n"));
                    content1 = this.fixPic(epubBook, book, content, (BookChapter)chapter);
                    title = chapter.getTitle();
                    epubBook.addSection(title, ResourceUtil.createChapterResource(StringsKt.replace$default(title, "\ud83d\udd12", "", false, 4, null), content1, contentModel, "Text/chapter_" + index + ".html"));
                }
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    private final String fixPic(EpubBook epubBook, Book book, String content, BookChapter chapter) {
        StringBuilder data = new StringBuilder("");
        Object object = new String[]{"\n"};
        Iterable $this$forEach$iv = StringsKt.split$default((CharSequence)content, object, false, 0, 6, null);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String text = (String)element$iv;
            boolean bl = false;
            String text1 = null;
            text1 = text;
            Matcher matcher = AppPattern.INSTANCE.getImgPattern().matcher(text);
            while (matcher.find()) {
                String string = matcher.group(1);
                if (string == null) continue;
                String string2 = string;
                boolean bl2 = false;
                boolean bl3 = false;
                String it = string2;
                boolean bl4 = false;
                String src = NetworkUtils.INSTANCE.getAbsoluteURL(chapter.getUrl(), it);
                String originalHref = MD5Utils.INSTANCE.md5Encode16(src) + '.' + BookHelp.INSTANCE.getImageSuffix(src);
                String href = Intrinsics.stringPlus("Images/", originalHref);
                File vFile = BookHelp.INSTANCE.getImage(book, src);
                if (!vFile.exists()) continue;
                FileResourceProvider fp = new FileResourceProvider(vFile.getParent());
                LazyResource img = new LazyResource((LazyResourceProvider)fp, href, originalHref);
                epubBook.getResources().add(img);
                text1 = StringsKt.replace$default(text1, it, Intrinsics.stringPlus("../", href), false, 4, null);
            }
            data.append(text1).append("\n");
        }
        object = data.toString();
        Intrinsics.checkNotNullExpressionValue(object, "data.toString()");
        return object;
    }

    private final void setEpubMetadata(Book book, EpubBook epubBook) {
        Metadata metadata = new Metadata();
        metadata.getTitles().add(book.getName());
        metadata.getAuthors().add(new Author(book.getRealAuthor()));
        metadata.setLanguage("zh");
        metadata.getDates().add(new Date());
        metadata.getPublishers().add("Legado");
        metadata.getDescriptions().add(book.getDisplayIntro());
        epubBook.setMetadata(metadata);
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object searchBookContent(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof searchBookContent.1)) ** GOTO lbl-1000
        var21_3 = var2_2;
        if ((var21_3.label & -2147483648) != 0) {
            var21_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                Object L$5;
                Object L$6;
                int I$0;
                int I$1;
                int I$2;
                int I$3;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.searchBookContent(null, this);
                }
            };
        }
        $result = $continuation.result;
        var22_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var22_5) {
                    return var22_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                var4_7 = null;
                var5_8 = null;
                var6_9 = 0;
                var7_10 = 0;
                if (context.request().method() == HttpMethod.POST) {
                    var9_11 = context.getBodyAsJson().getString("url");
                    var8_14 = var9_11 == null ? context.getBodyAsJson().getString("bookUrl") : var9_11;
                    bookUrl = var8_14 == null ? "" : var8_14;
                    var8_14 = context.getBodyAsJson().getString("keyword");
                    keyword = var8_14 == null ? "" : var8_14;
                    var8_14 = context.getBodyAsJson().getInteger("lastIndex", Boxing.boxInt(0));
                    Intrinsics.checkNotNullExpressionValue(var8_14, "context.bodyAsJson.getInteger(\"lastIndex\", 0)");
                    lastIndex = ((Number)var8_14).intValue();
                    var8_14 = context.getBodyAsJson().getInteger("size", Boxing.boxInt(20));
                    Intrinsics.checkNotNullExpressionValue(var8_14, "context.bodyAsJson.getInteger(\"size\", 20)");
                    var7_10 = ((Number)var8_14).intValue();
                } else {
                    var9_11 = context.queryParam("url");
                    Intrinsics.checkNotNullExpressionValue(var9_11, "context.queryParam(\"url\")");
                    var8_14 = (String)CollectionsKt.firstOrNull(var9_11);
                    bookUrl = var8_14 == null ? "" : var8_14;
                    var9_11 = context.queryParam("keyword");
                    Intrinsics.checkNotNullExpressionValue(var9_11, "context.queryParam(\"keyword\")");
                    var8_14 = CollectionsKt.firstOrNull(var9_11);
                    keyword = var8_14 == null ? "" : var8_14;
                    var9_11 = context.queryParam("lastIndex");
                    Intrinsics.checkNotNullExpressionValue(var9_11, "context.queryParam(\"lastIndex\")");
                    var8_14 = CollectionsKt.firstOrNull(var9_11);
                    if (var8_14 == null) {
                        v1 = 0;
                    } else {
                        var10_15 = var8_14;
                        var11_18 = false;
                        var9_11 = Boxing.boxInt(Integer.parseInt((String)var10_15));
                        v1 = var9_11 == null ? 0 : var9_11.intValue();
                    }
                    lastIndex = v1;
                    var9_11 = context.queryParam("size");
                    Intrinsics.checkNotNullExpressionValue(var9_11, "context.queryParam(\"size\")");
                    var8_14 = (String)CollectionsKt.firstOrNull(var9_11);
                    if (var8_14 == null) {
                        v2 = 20;
                    } else {
                        var10_15 = var8_14;
                        var11_18 = false;
                        var9_11 = Boxing.boxInt(Integer.parseInt((String)var10_15));
                        v2 = var9_11 == null ? 20 : var9_11.intValue();
                    }
                    size = v2;
                }
                var8_14 = (CharSequence)bookUrl;
                var9_12 = false;
                var10_16 = false;
                if (var8_14.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u4e66\u7c4d\u94fe\u63a5");
                }
                var8_14 = (CharSequence)keyword;
                var9_12 = false;
                var10_16 = false;
                if (var8_14.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u641c\u7d22\u5173\u952e\u8bcd");
                }
                userNameSpace = this.getUserNameSpace(context);
                bookInfo = this.getShelfBookByURL((String)bookUrl, userNameSpace);
                if (bookInfo == null) {
                    return returnData.setErrorMsg("\u8bf7\u5148\u52a0\u5165\u4e66\u67b6");
                }
                bookSource = null;
                if (bookInfo.isLocalBook()) ** GOTO lbl122
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.L$3 = keyword;
                $continuation.L$4 = userNameSpace;
                $continuation.L$5 = bookInfo;
                $continuation.I$0 = lastIndex;
                $continuation.I$1 = size;
                $continuation.label = 2;
                v3 = BookController.getBookSourceString$default(this, context, bookInfo.getOrigin(), false, $continuation, 4, null);
                if (v3 == var22_5) {
                    return var22_5;
                }
                ** GOTO lbl116
            }
            case 2: {
                var7_10 = $continuation.I$1;
                var6_9 = $continuation.I$0;
                var9_13 = (Book)$continuation.L$5;
                var8_14 = (String)$continuation.L$4;
                var5_8 = (String)$continuation.L$3;
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v3 = $result;
lbl116:
                // 2 sources

                bookSource = (String)v3;
                var11_19 = bookSource;
                var12_21 = false;
                var13_23 = false;
                if (var11_19 == null || var11_19.length() == 0) {
                    return var3_6.setErrorMsg("\u672a\u914d\u7f6e\u4e66\u6e90");
                }
lbl122:
                // 3 sources

                v4 = (var12_22 = bookSource) == null ? "" : var12_22;
                $continuation.L$0 = this;
                $continuation.L$1 = var1_1;
                $continuation.L$2 = var3_6;
                $continuation.L$3 = var5_8;
                $continuation.L$4 = var9_13;
                $continuation.L$5 = null;
                $continuation.I$0 = var6_9;
                $continuation.I$1 = var7_10;
                $continuation.label = 3;
                v5 = BookController.getLocalChapterList$default(this, var9_13, (String)v4, false, (String)var8_14, false, null, $continuation, 48, null);
                if (v5 == var22_5) {
                    return var22_5;
                }
                ** GOTO lbl146
            }
            case 3: {
                var7_10 = $continuation.I$1;
                var6_9 = $continuation.I$0;
                var9_13 = (Book)$continuation.L$4;
                var5_8 = (String)$continuation.L$3;
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v5 = $result;
lbl146:
                // 2 sources

                if (var6_9 >= (chapterList = (List)v5).size()) {
                    return var3_6.setErrorMsg("\u6ca1\u6709\u66f4\u591a\u4e86");
                }
                isEnd = new Ref.BooleanRef();
                var1_1.request().connection().closeHandler((Handler<Void>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, searchBookContent$lambda-30(kotlin.jvm.internal.Ref$BooleanRef com.htmake.reader.api.controller.BookController java.lang.Void ), (Ljava/lang/Void;)V)((Ref.BooleanRef)isEnd, (BookController)this));
                BookControllerKt.access$getLogger$p().info("searchBookContent keyword: {} lastIndex: {}", (Object)var5_8, (Object)Boxing.boxInt(var6_9));
                var14_25 = 0;
                resultList = new ArrayList<E>();
                currentIndex = ++var6_9;
                var15_26 = var6_9;
                var16_28 = chapterList.size();
                if (var15_26 >= var16_28) ** GOTO lbl195
                while (true) {
                    currentIndex = chapterIndex = var15_26++;
                    chapter = (BookChapter)chapterList.get(chapterIndex);
                    $continuation.L$0 = this;
                    $continuation.L$1 = var3_6;
                    $continuation.L$2 = var5_8;
                    $continuation.L$3 = var9_13;
                    $continuation.L$4 = chapterList;
                    $continuation.L$5 = isEnd;
                    $continuation.L$6 = resultList;
                    $continuation.I$0 = var7_10;
                    $continuation.I$1 = currentIndex;
                    $continuation.I$2 = var15_26;
                    $continuation.I$3 = var16_28;
                    $continuation.label = 4;
                    v6 = this.searchChapter(var9_13, chapter, var5_8, $continuation);
                    if (v6 == var22_5) {
                        return var22_5;
                    }
                    ** GOTO lbl191
                    break;
                }
            }
            case 4: {
                var16_28 = $continuation.I$3;
                var15_26 = $continuation.I$2;
                var14_25 = $continuation.I$1;
                var7_10 = $continuation.I$0;
                resultList = (List)$continuation.L$6;
                var12_22 = (Ref.BooleanRef)$continuation.L$5;
                var11_20 = (List)$continuation.L$4;
                var9_13 = (Book)$continuation.L$3;
                var5_8 = (String)$continuation.L$2;
                var3_6 = (ReturnData)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v6 = $result;
lbl191:
                // 2 sources

                if ((chapterResult = (List)v6).size() > 0) {
                    resultList.addAll(chapterResult);
                }
                if (resultList.size() < var7_10 && !var12_22.element && var15_26 < var16_28) ** continue;
lbl195:
                // 2 sources

                var15_27 = new Pair[]{TuplesKt.to("list", resultList), TuplesKt.to("lastIndex", Boxing.boxInt(var14_25))};
                return ReturnData.setData$default(var3_6, MapsKt.mapOf(var15_27), null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object searchChapter(@NotNull Book var1_1, @NotNull BookChapter var2_2, @NotNull String var3_3, @NotNull Continuation<? super List<SearchResult>> var4_4) {
        if (!(var4_4 instanceof searchChapter.1)) ** GOTO lbl-1000
        var21_5 = var4_4;
        if ((var21_5.label & -2147483648) != 0) {
            var21_5.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var4_4){
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.searchChapter(null, null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var22_7 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                var6_8 = false;
                searchResultsWithinChapter = new ArrayList<E>();
                chapterContent = BookHelp.INSTANCE.getContent((Book)book, (BookChapter)chapter);
                if (chapterContent == null) ** GOTO lbl53
                $continuation.L$0 = this;
                $continuation.L$1 = chapter;
                $continuation.L$2 = query;
                $continuation.L$3 = searchResultsWithinChapter;
                $continuation.L$4 = chapterContent;
                $continuation.label = 1;
                v0 = this.searchPosition(chapterContent, (String)query, $continuation);
                if (v0 == var22_7) {
                    return var22_7;
                }
                ** GOTO lbl34
            }
            case 1: {
                chapterContent = (String)$continuation.L$4;
                searchResultsWithinChapter = (List)$continuation.L$3;
                var3_3 = (String)$continuation.L$2;
                var2_2 = (BookChapter)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl34:
                // 2 sources

                positions = (List)v0;
                BookControllerKt.access$getLogger$p().info("positions: {}", (Object)positions);
                $this$forEachIndexed$iv = positions;
                $i$f$forEachIndexed = false;
                index$iv = 0;
                for (T item$iv : $this$forEachIndexed$iv) {
                    var13_17 = index$iv++;
                    var14_18 = false;
                    if (var13_17 < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    var15_19 = ((Number)item$iv).intValue();
                    index = ((Number)Boxing.boxInt(var13_17)).intValue();
                    $i$a$-forEachIndexed-BookController$searchChapter$2 = false;
                    construct = this.getResultAndQueryIndex(chapterContent, (int)position, var3_3);
                    result = new SearchResult(0, index, construct.getSecond(), var2_2.getTitle(), var3_3, 0, var2_2.getIndex(), 0, ((Number)construct.getFirst()).intValue(), (int)position, 161, null);
                    searchResultsWithinChapter.add(result);
                }
lbl53:
                // 2 sources

                return searchResultsWithinChapter;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    private final Object searchPosition(String mContent, String pattern, Continuation<? super List<Integer>> $completion) {
        boolean bl = false;
        List position = new ArrayList();
        int index = StringsKt.indexOf$default((CharSequence)mContent, pattern, 0, false, 6, null);
        if (index >= 0) {
            while (index >= 0) {
                position.add(Boxing.boxInt(index));
                index = StringsKt.indexOf$default((CharSequence)mContent, pattern, index + 1, false, 4, null);
            }
        }
        return position;
    }

    private final Pair<Integer, String> getResultAndQueryIndex(String content, int queryIndexInContent, String query) {
        int length = 20;
        int po1 = queryIndexInContent - length;
        int po2 = queryIndexInContent + query.length() + length;
        if (po1 < 0) {
            po1 = 0;
        }
        if (po2 > content.length()) {
            po2 = content.length();
        }
        int queryIndexInResult = queryIndexInContent - po1;
        String string = content;
        boolean bl = false;
        String string2 = string;
        if (string2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        }
        String string3 = string2.substring(po1, po2);
        Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        String newText = string3;
        return TuplesKt.to(queryIndexInResult, newText);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object backupToMongodb(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof backupToMongodb.1)) ** GOTO lbl-1000
        var20_3 = var2_2;
        if ((var20_3.label & -2147483648) != 0) {
            var20_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.backupToMongodb(null, this);
                }
            };
        }
        $result = $continuation.result;
        var21_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var21_5) {
                    return var21_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                if (!MongoManager.INSTANCE.isInit()) {
                    return returnData.setErrorMsg("\u8bf7\u5148\u8bbe\u7f6e mongoUri");
                }
                if (!this.checkManagerAuth(context)) {
                    return ReturnData.setData$default(returnData, "NEED_SECURE_KEY", null, 2, null).setErrorMsg("\u8bf7\u8f93\u5165\u7ba1\u7406\u5bc6\u7801");
                }
                var5_7 = this.getBackupFileNames();
                syncDataFileList = CollectionsKt.arrayListOf(Arrays.copyOf(var5_7, var5_7.length));
                handler = new Function1<String, Unit>(syncDataFileList, this){
                    final /* synthetic */ ArrayList<String> $syncDataFileList;
                    final /* synthetic */ BookController this$0;
                    {
                        this.$syncDataFileList = $syncDataFileList;
                        this.this$0 = $receiver;
                        super(1);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    public final void invoke(@NotNull String userNameSpace) {
                        void $this$forEach$iv;
                        Intrinsics.checkNotNullParameter(userNameSpace, "userNameSpace");
                        Iterable iterable = this.$syncDataFileList;
                        BookController bookController = this.this$0;
                        boolean $i$f$forEach = false;
                        for (T element$iv : $this$forEach$iv) {
                            String it = (String)element$iv;
                            boolean bl = false;
                            Object object = new String[]{it};
                            String string = bookController.getUserStorage(userNameSpace, (String)object);
                            if (string == null) continue;
                            object = string;
                            boolean bl2 = false;
                            boolean bl3 = false;
                            Object content = object;
                            boolean bl4 = false;
                            bookController.saveUserStorage(userNameSpace, it, content);
                        }
                    }
                };
                handler.invoke("default");
                if (this.getAppConfig().getSecure()) {
                    var7_9 = false;
                    userMap = new LinkedHashMap<K, V>();
                    var8_12 /* !! */  = new String[]{"data", "users"};
                    userMapJson = ExtKt.asJsonObject(ExtKt.getStorage$default(var8_12 /* !! */ , null, 2, null));
                    if (userMapJson != null) {
                        var8_12 /* !! */  = userMapJson.getMap();
                        if (var8_12 /* !! */  == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.String, kotlin.collections.Map<kotlin.String, kotlin.Any>>");
                        }
                        userMap = TypeIntrinsics.asMutableMap(var8_12 /* !! */ );
                    }
                    $this$forEach$iv = userMap;
                    $i$f$forEach = false;
                    var10_15 = $this$forEach$iv;
                    var11_16 = false;
                    var12_17 = var10_15.entrySet().iterator();
                    while (var12_17.hasNext()) {
                        it = element$iv = (Map.Entry)var12_17.next();
                        $i$a$-forEach-BookController$backupToMongodb$2 = false;
                        try {
                            var16_21 = ((Map)it.getValue()).getOrDefault("username", "");
                            ns = var16_21 == null ? "" : var16_21;
                            var16_21 = ns;
                            var18_24 = false;
                            if (!(var16_21.length() > 0)) continue;
                            handler.invoke(ns);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if ((var6_11 = ExtKt.getStorage$default(var7_10 /* !! */  = new String[]{"users"}, null, 2, null)) != null) {
                    var7_10 /* !! */  = var6_11;
                    var8_13 = false;
                    var9_14 = false;
                    content /* !! */  = var7_10 /* !! */ ;
                    $i$a$-let-BookController$backupToMongodb$3 = false;
                    var12_17 = new String[]{"users"};
                    ExtKt.saveStorage$default((String[])var12_17, content /* !! */ , false, null, 12, null);
                }
                return ReturnData.setData$default(returnData, "", null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object restoreFromMongodb(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof restoreFromMongodb.1)) ** GOTO lbl-1000
        var20_3 = var2_2;
        if ((var20_3.label & -2147483648) != 0) {
            var20_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                Object L$2;
                /* synthetic */ Object result;
                final /* synthetic */ BookController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.restoreFromMongodb(null, this);
                }
            };
        }
        $result = $continuation.result;
        var21_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 1;
                v0 = this.checkAuth(context, $continuation);
                if (v0 == var21_5) {
                    return var21_5;
                }
                ** GOTO lbl27
            }
            case 1: {
                returnData = (ReturnData)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BookController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                if (!MongoManager.INSTANCE.isInit()) {
                    return returnData.setErrorMsg("\u8bf7\u5148\u8bbe\u7f6e mongoUri");
                }
                if (!this.checkManagerAuth(context)) {
                    return ReturnData.setData$default(returnData, "NEED_SECURE_KEY", null, 2, null).setErrorMsg("\u8bf7\u8f93\u5165\u7ba1\u7406\u5bc6\u7801");
                }
                var5_7 = this.getBackupFileNames();
                syncDataFileList = CollectionsKt.arrayListOf(Arrays.copyOf(var5_7, var5_7.length));
                handler = new Function1<String, Unit>(syncDataFileList){
                    final /* synthetic */ ArrayList<String> $syncDataFileList;
                    {
                        this.$syncDataFileList = $syncDataFileList;
                        super(1);
                    }

                    public final void invoke(@NotNull String userNameSpace) {
                        Intrinsics.checkNotNullParameter(userNameSpace, "userNameSpace");
                        Iterable $this$forEach$iv = this.$syncDataFileList;
                        boolean $i$f$forEach = false;
                        for (T element$iv : $this$forEach$iv) {
                            String it = (String)element$iv;
                            boolean bl = false;
                            String[] stringArray = new String[]{"storage", "data", userNameSpace, Intrinsics.stringPlus(it, ".json")};
                            File file = new File(ExtKt.getWorkDir(stringArray));
                            if (!file.exists()) continue;
                            file.delete();
                        }
                    }
                };
                handler.invoke("default");
                if (this.getAppConfig().getSecure()) {
                    var7_9 = false;
                    userMap = new LinkedHashMap<K, V>();
                    var8_12 /* !! */  = new String[]{"data", "users"};
                    userMapJson = ExtKt.asJsonObject(ExtKt.getStorage$default(var8_12 /* !! */ , null, 2, null));
                    if (userMapJson != null) {
                        var8_12 /* !! */  = userMapJson.getMap();
                        if (var8_12 /* !! */  == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.String, kotlin.collections.Map<kotlin.String, kotlin.Any>>");
                        }
                        userMap = TypeIntrinsics.asMutableMap(var8_12 /* !! */ );
                    }
                    $this$forEach$iv = userMap;
                    $i$f$forEach = false;
                    var10_14 = $this$forEach$iv;
                    var11_15 = false;
                    var12_16 = var10_14.entrySet().iterator();
                    while (var12_16.hasNext()) {
                        it = element$iv = var12_16.next();
                        $i$a$-forEach-BookController$restoreFromMongodb$2 = false;
                        try {
                            var16_20 = ((Map)it.getValue()).getOrDefault("username", "");
                            ns = var16_20 == null ? "" : var16_20;
                            var16_20 = ns;
                            var18_23 = false;
                            if (!(var16_20.length() > 0)) continue;
                            handler.invoke(ns);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if ((usersFile = new File(ExtKt.getWorkDir(var7_10 = new String[]{"storage", "users.json"}))).exists()) {
                    usersFile.delete();
                    var7_10 = new String[]{"users"};
                    ExtKt.getStorage$default(var7_10, null, 2, null);
                }
                return ReturnData.setData$default(returnData, "", null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    private static final void searchBookMulti$lambda-5(Ref.BooleanRef $isEnd, BookController this$0, Void it) {
        Intrinsics.checkNotNullParameter($isEnd, "$isEnd");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        BookControllerKt.access$getLogger$p().info("\u5ba2\u6237\u7aef\u5df2\u65ad\u5f00\u94fe\u63a5\uff0c\u505c\u6b62 searchBookMulti");
        $isEnd.element = true;
        JobKt.cancel$default(this$0.getCoroutineContext(), null, 1, null);
    }

    private static final void searchBookMultiSSE$lambda-6(Ref.BooleanRef $isEnd, BookController this$0, Void it) {
        Intrinsics.checkNotNullParameter($isEnd, "$isEnd");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        BookControllerKt.access$getLogger$p().info("\u5ba2\u6237\u7aef\u5df2\u65ad\u5f00\u94fe\u63a5\uff0c\u505c\u6b62 searchBookMultiSSE");
        $isEnd.element = true;
        JobKt.cancel$default(this$0.getCoroutineContext(), null, 1, null);
    }

    private static final void searchBookSource$lambda-7(Ref.BooleanRef $isEnd, BookController this$0, Void it) {
        Intrinsics.checkNotNullParameter($isEnd, "$isEnd");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        BookControllerKt.access$getLogger$p().info("\u5ba2\u6237\u7aef\u5df2\u65ad\u5f00\u94fe\u63a5\uff0c\u505c\u6b62 searchBookSource");
        $isEnd.element = true;
        JobKt.cancel$default(this$0.getCoroutineContext(), null, 1, null);
    }

    private static final void searchBookSourceSSE$lambda-8(Ref.BooleanRef $isEnd, BookController this$0, Void it) {
        Intrinsics.checkNotNullParameter($isEnd, "$isEnd");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        BookControllerKt.access$getLogger$p().info("\u5ba2\u6237\u7aef\u5df2\u65ad\u5f00\u94fe\u63a5\uff0c\u505c\u6b62 searchBookSourceSSE");
        $isEnd.element = true;
        JobKt.cancel$default(this$0.getCoroutineContext(), null, 1, null);
    }

    private static final void bookSourceDebugSSE$lambda-18(BookController this$0, Void it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        BookControllerKt.access$getLogger$p().info("\u5ba2\u6237\u7aef\u5df2\u65ad\u5f00\u94fe\u63a5\uff0c\u505c\u6b62 bookSourceDebugSSE");
        JobKt.cancel$default(this$0.getCoroutineContext(), null, 1, null);
    }

    private static final void cacheBookSSE$lambda-19(Ref.BooleanRef $isEnd, BookController this$0, Void it) {
        Intrinsics.checkNotNullParameter($isEnd, "$isEnd");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        BookControllerKt.access$getLogger$p().info("\u5ba2\u6237\u7aef\u5df2\u65ad\u5f00\u94fe\u63a5\uff0c\u505c\u6b62 cacheBookSSE");
        $isEnd.element = true;
        JobKt.cancel$default(this$0.getCoroutineContext(), null, 1, null);
    }

    private static final void searchBookContent$lambda-30(Ref.BooleanRef $isEnd, BookController this$0, Void it) {
        Intrinsics.checkNotNullParameter($isEnd, "$isEnd");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        BookControllerKt.access$getLogger$p().info("\u5ba2\u6237\u7aef\u5df2\u65ad\u5f00\u94fe\u63a5\uff0c\u505c\u6b62 searchBookContent");
        $isEnd.element = true;
        JobKt.cancel$default(this$0.getCoroutineContext(), null, 1, null);
    }

    public static final /* synthetic */ WebClient access$getWebClient$p(BookController $this) {
        return $this.webClient;
    }

    public static final /* synthetic */ int access$getConcurrentLoopCount$p(BookController $this) {
        return $this.concurrentLoopCount;
    }

    public static final /* synthetic */ void access$addInvalidBookSource(BookController $this, String sourceUrl, Map invalidInfo, String userNameSpace) {
        $this.addInvalidBookSource(sourceUrl, invalidInfo, userNameSpace);
    }

    public static final /* synthetic */ Object access$saveLocalBookCover(BookController $this, Book book, String userNameSpace, Continuation $completion) {
        return $this.saveLocalBookCover(book, userNameSpace, $completion);
    }

    public static final /* synthetic */ void access$ttsByTextToSpeechCn$add(CaseInsensitiveHeaders $receiver, String p0, String p1) {
        $receiver.add(p0, p1);
    }

    public static final /* synthetic */ Object access$getAllContents(BookController $this, Book book, String bookSourceString, String userNameSpace, Function2 append, Continuation $completion) {
        return $this.getAllContents(book, bookSourceString, userNameSpace, append, $completion);
    }

    public static final /* synthetic */ Object access$exportToEpub(BookController $this, File exportDir, Book book, String bookSource, String userNameSpace, Continuation $completion) {
        return $this.exportToEpub(exportDir, book, bookSource, userNameSpace, $completion);
    }

    public static final /* synthetic */ Object access$setCover(BookController $this, Book book, EpubBook epubBook, String bookSourceString, Continuation $completion) {
        return $this.setCover(book, epubBook, bookSourceString, $completion);
    }

    public static final /* synthetic */ Object access$setEpubContent(BookController $this, String contentModel, Book book, EpubBook epubBook, String bookSourceString, String userNameSpace, Continuation $completion) {
        return $this.setEpubContent(contentModel, book, epubBook, bookSourceString, userNameSpace, $completion);
    }

    public static final /* synthetic */ Object access$searchPosition(BookController $this, String mContent, String pattern, Continuation $completion) {
        return $this.searchPosition(mContent, pattern, $completion);
    }
}

