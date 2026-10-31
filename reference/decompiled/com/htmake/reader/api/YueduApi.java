/*
 * Decompiled with CFR 0.152.
 */
package com.htmake.reader.api;

import com.htmake.reader.SpringEvent;
import com.htmake.reader.api.ReturnData;
import com.htmake.reader.api.YueduApi;
import com.htmake.reader.api.YueduApiKt;
import com.htmake.reader.api.controller.BookController;
import com.htmake.reader.api.controller.BookGroupController;
import com.htmake.reader.api.controller.BookSourceController;
import com.htmake.reader.api.controller.BookmarkController;
import com.htmake.reader.api.controller.FileController;
import com.htmake.reader.api.controller.HttpTTSController;
import com.htmake.reader.api.controller.LicenseController;
import com.htmake.reader.api.controller.ReplaceRuleController;
import com.htmake.reader.api.controller.RssSourceController;
import com.htmake.reader.api.controller.UserController;
import com.htmake.reader.api.controller.WebdavController;
import com.htmake.reader.config.AppConfig;
import com.htmake.reader.config.BookConfig;
import com.htmake.reader.entity.License;
import com.htmake.reader.entity.User;
import com.htmake.reader.init.ReaderAdapter;
import com.htmake.reader.utils.ExtKt;
import com.htmake.reader.utils.MongoManager;
import com.htmake.reader.utils.RemoteWebview;
import com.htmake.reader.utils.SpringContextUtils;
import com.htmake.reader.utils.VertExtKt;
import com.htmake.reader.verticle.RestVerticle;
import io.legado.app.adapters.ReaderAdapterHelper;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.net.impl.URIDecoder;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import java.io.File;
import java.lang.invoke.LambdaMetafactory;
import java.net.URLDecoder;
import java.util.Calendar;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.slf4j.MDCContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Component
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0017\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0017J\b\u0010\t\u001a\u00020\bH\u0017J\b\u0010\n\u001a\u00020\bH\u0017J\b\u0010\u000b\u001a\u00020\bH\u0017J\b\u0010\f\u001a\u00020\rH\u0016J\u0019\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J\u0019\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u0011\u0010\u0017\u001a\u00020\bH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u001c\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u00112\n\u0010\u001b\u001a\u00060\u001cj\u0002`\u001dH\u0016J\b\u0010\u001e\u001a\u00020\bH\u0016J\b\u0010\u001f\u001a\u00020\bH\u0017J\u0011\u0010 \u001a\u00020\bH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\b\u0010!\u001a\u00020\bH\u0017J\b\u0010\"\u001a\u00020\bH\u0016R\u0012\u0010\u0003\u001a\u00020\u00048\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00068\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006#"}, d2={"Lcom/htmake/reader/api/YueduApi;", "Lcom/htmake/reader/verticle/RestVerticle;", "()V", "appConfig", "Lcom/htmake/reader/config/AppConfig;", "env", "Lorg/springframework/core/env/Environment;", "autoBackup", "", "autoGC", "checkLicense", "clearUser", "getContextPath", "", "getSystemInfo", "Lcom/htmake/reader/api/ReturnData;", "context", "Lio/vertx/ext/web/RoutingContext;", "(Lio/vertx/ext/web/RoutingContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "initRouter", "router", "Lio/vertx/ext/web/Router;", "(Lio/vertx/ext/web/Router;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "migration", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onHandlerError", "ctx", "error", "Ljava/lang/Exception;", "Lkotlin/Exception;", "onStartError", "remoteBookSourceSubUpdateJob", "setupPort", "shelfUpdateJob", "started", "reader-pro"})
public class YueduApi
extends RestVerticle {
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private Environment env;

    @Override
    @Nullable
    public Object initRouter(@NotNull Router router, @NotNull Continuation<? super Unit> $completion) {
        return YueduApi.initRouter$suspendImpl(this, router, $completion);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    static /* synthetic */ Object initRouter$suspendImpl(YueduApi var0, Router var1_1, Continuation var2_2) {
        if (!(var2_2 instanceof initRouter.1)) ** GOTO lbl-1000
        var21_3 = var2_2;
        if ((var21_3.label & -2147483648) != 0) {
            var21_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(var0, var2_2){
                Object L$0;
                Object L$1;
                /* synthetic */ Object result;
                final /* synthetic */ YueduApi this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return YueduApi.initRouter$suspendImpl(this.this$0, null, this);
                }
            };
        }
        $result = $continuation.result;
        var22_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                $continuation.L$0 = this;
                $continuation.L$1 = router;
                $continuation.label = 1;
                v0 = this.setupPort($continuation);
                if (v0 == var22_5) {
                    return var22_5;
                }
                ** GOTO lbl24
            }
            case 1: {
                router = (Router)$continuation.L$1;
                this = (YueduApi)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl24:
                // 2 sources

                if ((var3_6 = this.appConfig) == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("appConfig");
                    throw null;
                }
                var3_6 = var3_6.getMongoUri();
                var4_7 = false;
                if (var3_6.length() > 0) {
                    var3_6 = this.appConfig;
                    if (var3_6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("appConfig");
                        throw null;
                    }
                    MongoManager.INSTANCE.connect(var3_6.getMongoUri());
                }
                if ((var3_6 = this.appConfig) == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("appConfig");
                    throw null;
                }
                var3_6 = var3_6.getRemoteWebviewApi();
                var4_7 = false;
                if (var3_6.length() > 0) {
                    var3_6 = this.appConfig;
                    if (var3_6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("appConfig");
                        throw null;
                    }
                    RemoteWebview.INSTANCE.setRemoteApi(var3_6.getRemoteWebviewApi());
                }
                ReaderAdapterHelper.INSTANCE.setAdapter(ReaderAdapter.INSTANCE);
                $continuation.L$0 = this;
                $continuation.L$1 = router;
                $continuation.label = 2;
                v1 = this.migration($continuation);
                if (v1 == var22_5) {
                    return var22_5;
                }
                ** GOTO lbl59
            }
            case 2: {
                var1_1 = (Router)$continuation.L$1;
                var0 = (YueduApi)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v1 = $result;
lbl59:
                // 2 sources

                var1_1.route("/*").handler(StaticHandler.create("web").setDefaultContentEncoding("UTF-8"));
                var4_8 = new String[]{"storage", "assets"};
                assetsDir = ExtKt.getWorkDir(var4_8);
                assetsDirFile = new File(assetsDir);
                if (!assetsDirFile.exists()) {
                    assetsDirFile.mkdirs();
                }
                if (!(assetsCssFile = new File(assetsCss = ExtKt.getWorkDir(var6_9 = new String[]{"storage", "assets", "reader.css"}))).exists()) {
                    FilesKt.writeText$default(assetsCssFile, "/* \u5728\u6b64\u5904\u53ef\u4ee5\u7f16\u5199CSS\u6837\u5f0f\u6765\u81ea\u5b9a\u4e49\u9875\u9762 */", null, 2, null);
                }
                var1_1.route("/assets/*").handler(StaticHandler.create().setAllowRootFileSystemAccess(true).setWebRoot(assetsDir).setDefaultContentEncoding("UTF-8"));
                dataDir = new Ref.ObjectRef<T>();
                var8_12 /* !! */  = new String[]{"storage", "data"};
                dataDir.element = ExtKt.getWorkDir(var8_12 /* !! */ );
                var1_1.route("/book-assets/*").handler((Handler<RoutingContext>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, initRouter$lambda-0(kotlin.jvm.internal.Ref$ObjectRef io.vertx.ext.web.RoutingContext ), (Lio/vertx/ext/web/RoutingContext;)V)(dataDir));
                var1_1.route("/book-assets/*").handler(StaticHandler.create().setAllowRootFileSystemAccess(true).setWebRoot((String)dataDir.element).setDefaultContentEncoding("UTF-8"));
                var1_1.route("/epub/*").handler((Handler<RoutingContext>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, initRouter$lambda-1(kotlin.jvm.internal.Ref$ObjectRef io.vertx.ext.web.RoutingContext ), (Lio/vertx/ext/web/RoutingContext;)V)(dataDir));
                var1_1.route("/epub/*").handler(StaticHandler.create().setAllowRootFileSystemAccess(true).setWebRoot((String)dataDir.element).setDefaultContentEncoding("UTF-8"));
                var1_1.route("/simple-web").handler((Handler<RoutingContext>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, initRouter$lambda-2(io.vertx.ext.web.RoutingContext ), (Lio/vertx/ext/web/RoutingContext;)V)());
                var1_1.route("/simple-web/*").handler((Handler<RoutingContext>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, initRouter$lambda-3(io.vertx.ext.web.RoutingContext ), (Lio/vertx/ext/web/RoutingContext;)V)());
                var1_1.route("/simple-web/*").handler(StaticHandler.create("simple-web").setDefaultContentEncoding("UTF-8"));
                var8_12 /* !! */  = var1_1.get("/reader3/getSystemInfo");
                Intrinsics.checkNotNullExpressionValue(var8_12 /* !! */ , "router.get(\"/reader3/getSystemInfo\")");
                var0.coroutineHandler((Route)var8_12 /* !! */ , (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(var0, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ YueduApi this$0;
                    {
                        this.this$0 = $receiver;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = YueduApi.access$getSystemInfo(this.this$0, it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                bookController = new BookController(var0.getCoroutineContext());
                bookGroupController = new BookGroupController(var0.getCoroutineContext());
                bookSourceController = new BookSourceController(var0.getCoroutineContext());
                rssSourceController = new RssSourceController(var0.getCoroutineContext());
                userController = new UserController(var0.getCoroutineContext());
                webdavController = new WebdavController(var0.getCoroutineContext(), var1_1, (Function2<? super RoutingContext, ? super Exception, Unit>)new Function2<RoutingContext, Exception, Unit>(var0){
                    final /* synthetic */ YueduApi this$0;
                    {
                        this.this$0 = $receiver;
                        super(2);
                    }

                    public final void invoke(@NotNull RoutingContext ctx, @NotNull Exception error2) {
                        Intrinsics.checkNotNullParameter(ctx, "ctx");
                        Intrinsics.checkNotNullParameter(error2, "error");
                        this.this$0.onHandlerError(ctx, error2);
                    }
                });
                replaceRuleController = new ReplaceRuleController(var0.getCoroutineContext());
                bookmarkController = new BookmarkController(var0.getCoroutineContext());
                fileController = new FileController(var0.getCoroutineContext());
                licenseController = new LicenseController(var0.getCoroutineContext());
                httpTTSController = new HttpTTSController(var0.getCoroutineContext());
                var19_23 = var1_1.post("/reader3/saveBookSource");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/saveBookSource\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookSourceController $bookSourceController;
                    {
                        this.$bookSourceController = $bookSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookSourceController.saveBookSource(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/saveBookSources");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/saveBookSources\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookSourceController $bookSourceController;
                    {
                        this.$bookSourceController = $bookSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookSourceController.saveBookSources(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/getBookSource");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/getBookSource\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookSourceController $bookSourceController;
                    {
                        this.$bookSourceController = $bookSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookSourceController.getBookSource(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/getBookSource");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/getBookSource\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookSourceController $bookSourceController;
                    {
                        this.$bookSourceController = $bookSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookSourceController.getBookSource(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/getBookSources");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/getBookSources\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookSourceController $bookSourceController;
                    {
                        this.$bookSourceController = $bookSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookSourceController.getBookSources(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/getBookSources");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/getBookSources\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookSourceController $bookSourceController;
                    {
                        this.$bookSourceController = $bookSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookSourceController.getBookSources(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/deleteAllBookSources");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/deleteAllBookSources\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookSourceController $bookSourceController;
                    {
                        this.$bookSourceController = $bookSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookSourceController.deleteAllBookSources(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/deleteBookSource");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/deleteBookSource\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookSourceController $bookSourceController;
                    {
                        this.$bookSourceController = $bookSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookSourceController.deleteBookSource(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/deleteBookSources");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/deleteBookSources\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookSourceController $bookSourceController;
                    {
                        this.$bookSourceController = $bookSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookSourceController.deleteBookSources(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/readSourceFile");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/readSourceFile\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookSourceController $bookSourceController;
                    {
                        this.$bookSourceController = $bookSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookSourceController.readSourceFile(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/saveFromRemoteSource");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/saveFromRemoteSource\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Unit>, Object>(bookSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookSourceController $bookSourceController;
                    {
                        this.$bookSourceController = $bookSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookSourceController.saveFromRemoteSource(it, this);
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
                        Function2<RoutingContext, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<? super Unit> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/setAsDefaultBookSources");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/setAsDefaultBookSources\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookSourceController $bookSourceController;
                    {
                        this.$bookSourceController = $bookSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookSourceController.setAsDefaultBookSources(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/deleteUserBookSource");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/deleteUserBookSource\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookSourceController $bookSourceController;
                    {
                        this.$bookSourceController = $bookSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookSourceController.deleteUserBookSource(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/deleteBookSourcesFile");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/deleteBookSourcesFile\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookSourceController $bookSourceController;
                    {
                        this.$bookSourceController = $bookSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookSourceController.deleteBookSourcesFile(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/getBookshelf");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/getBookshelf\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.getBookshelf(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/getShelfBook");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/getShelfBook\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.getShelfBook(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/saveBook");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/saveBook\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.saveBook(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/deleteBook");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/deleteBook\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.deleteBook(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/deleteBooks");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/deleteBooks\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.deleteBooks(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/getInvalidBookSources");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/getInvalidBookSources\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.getInvalidBookSources(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/exploreBook");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/exploreBook\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.exploreBook(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/exploreBook");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/exploreBook\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.exploreBook(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/searchBook");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/searchBook\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.searchBook(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/searchBook");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/searchBook\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.searchBook(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/searchBookMulti");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/searchBookMulti\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.searchBookMulti(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/searchBookMulti");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/searchBookMulti\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.searchBookMulti(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/searchBookMultiSSE");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/searchBookMultiSSE\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Unit>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.searchBookMultiSSE(it, this);
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
                        Function2<RoutingContext, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<? super Unit> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/getBookInfo");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/getBookInfo\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.getBookInfo(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/getBookInfo");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/getBookInfo\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.getBookInfo(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/getChapterList");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/getChapterList\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.getChapterList(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/getChapterList");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/getChapterList\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.getChapterList(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/getBookContent");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/getBookContent\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.getBookContent(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/getBookContent");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/getBookContent\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.getBookContent(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/saveBookContent");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/saveBookContent\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.saveBookContent(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/saveBookProgress");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/saveBookProgress\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.saveBookProgress(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/cover");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/cover\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Unit>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.getBookCover(it, this);
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
                        Function2<RoutingContext, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<? super Unit> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/searchBookSource");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/searchBookSource\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.searchBookSource(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/searchBookSource");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/searchBookSource\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.searchBookSource(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/getAvailableBookSource");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/getAvailableBookSource\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.getAvailableBookSource(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/getAvailableBookSource");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/getAvailableBookSource\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.getAvailableBookSource(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/searchBookSourceSSE");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/searchBookSourceSSE\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Unit>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.searchBookSourceSSE(it, this);
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
                        Function2<RoutingContext, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<? super Unit> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/setBookSource");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/setBookSource\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.setBookSource(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/setBookSource");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/setBookSource\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.setBookSource(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/saveBookGroupId");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/saveBookGroupId\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.saveBookGroupId(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/addBookGroupMulti");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/addBookGroupMulti\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.addBookGroupMulti(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/removeBookGroupMulti");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/removeBookGroupMulti\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.removeBookGroupMulti(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/importBookPreview");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/importBookPreview\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.importBookPreview(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/refreshLocalBook");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/refreshLocalBook\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.refreshLocalBook(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/getTxtTocRules");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/getTxtTocRules\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.getTxtTocRules(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/getChapterListByRule");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/getChapterListByRule\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.getChapterListByRule(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/getBookGroups");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/getBookGroups\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookGroupController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookGroupController $bookGroupController;
                    {
                        this.$bookGroupController = $bookGroupController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookGroupController.list(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/saveBookGroup");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/saveBookGroup\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookGroupController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookGroupController $bookGroupController;
                    {
                        this.$bookGroupController = $bookGroupController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookGroupController.save(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/deleteBookGroup");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/deleteBookGroup\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookGroupController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookGroupController $bookGroupController;
                    {
                        this.$bookGroupController = $bookGroupController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookGroupController.delete(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/saveBookGroupOrder");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/saveBookGroupOrder\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookGroupController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookGroupController $bookGroupController;
                    {
                        this.$bookGroupController = $bookGroupController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookGroupController.saveBookGroupOrder(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/bookSourceDebugSSE");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/bookSourceDebugSSE\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Unit>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.bookSourceDebugSSE(it, this);
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
                        Function2<RoutingContext, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<? super Unit> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/cacheBookSSE");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/cacheBookSSE\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Unit>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.cacheBookSSE(it, this);
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
                        Function2<RoutingContext, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<? super Unit> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/cacheBookOnServer");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/cacheBookOnServer\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.cacheBookOnServer(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/getShelfBookWithCacheInfo");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/getShelfBookWithCacheInfo\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.getShelfBookWithCacheInfo(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/deleteBookCache");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/deleteBookCache\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.deleteBookCache(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/exportBook");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/exportBook\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Unit>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.exportBook(it, this);
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
                        Function2<RoutingContext, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<? super Unit> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/exportBook");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/exportBook\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Unit>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.exportBook(it, this);
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
                        Function2<RoutingContext, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<? super Unit> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/searchBookContent");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/searchBookContent\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.searchBookContent(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/searchBookContent");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/searchBookContent\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.searchBookContent(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/backupToMongodb");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/backupToMongodb\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.backupToMongodb(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/restoreFromMongodb");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/restoreFromMongodb\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.restoreFromMongodb(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/book/saveBookConfig");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/book/saveBookConfig\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.saveBookConfig(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/book/tts");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/book/tts\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Unit>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.textToSpeech(it, this);
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
                        Function2<RoutingContext, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<? super Unit> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/book/tts");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/book/tts\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Unit>, Object>(bookController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookController $bookController;
                    {
                        this.$bookController = $bookController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookController.textToSpeech(it, this);
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
                        Function2<RoutingContext, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<? super Unit> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/uploadFile");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/uploadFile\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(userController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ UserController $userController;
                    {
                        this.$userController = $userController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$userController.uploadFile(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/deleteFile");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/deleteFile\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(userController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ UserController $userController;
                    {
                        this.$userController = $userController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$userController.deleteFile(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/login");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/login\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(userController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ UserController $userController;
                    {
                        this.$userController = $userController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$userController.login(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/logout");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/logout\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(userController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ UserController $userController;
                    {
                        this.$userController = $userController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$userController.logout(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/getUserInfo");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/getUserInfo\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(userController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ UserController $userController;
                    {
                        this.$userController = $userController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$userController.getUserInfo(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/saveUserConfig");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/saveUserConfig\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(userController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ UserController $userController;
                    {
                        this.$userController = $userController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$userController.saveUserConfig(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/getUserConfig");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/getUserConfig\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(userController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ UserController $userController;
                    {
                        this.$userController = $userController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$userController.getUserConfig(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/getUserList");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/getUserList\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(userController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ UserController $userController;
                    {
                        this.$userController = $userController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$userController.getUserList(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/deleteUsers");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/deleteUsers\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(userController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ UserController $userController;
                    {
                        this.$userController = $userController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$userController.deleteUsers(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/clearInactiveUsers");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/clearInactiveUsers\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(userController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ UserController $userController;
                    {
                        this.$userController = $userController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$userController.clearInactiveUsers(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/addUser");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/addUser\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(userController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ UserController $userController;
                    {
                        this.$userController = $userController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$userController.addUser(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/resetPassword");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/resetPassword\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(userController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ UserController $userController;
                    {
                        this.$userController = $userController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$userController.resetPassword(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/updateUser");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/updateUser\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(userController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ UserController $userController;
                    {
                        this.$userController = $userController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$userController.updateUser(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/user/downloadBackupFile");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/user/downloadBackupFile\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Unit>, Object>(userController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ UserController $userController;
                    {
                        this.$userController = $userController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$userController.downloadBackupFile(it, this);
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
                        Function2<RoutingContext, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<? super Unit> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/getLicense");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/getLicense\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(licenseController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ LicenseController $licenseController;
                    {
                        this.$licenseController = $licenseController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$licenseController.getLicense(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/importLicense");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/importLicense\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Unit>, Object>(licenseController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ LicenseController $licenseController;
                    {
                        this.$licenseController = $licenseController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$licenseController.importLicense(it, this);
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
                        Function2<RoutingContext, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<? super Unit> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/generateKeys");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/generateKeys\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(licenseController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ LicenseController $licenseController;
                    {
                        this.$licenseController = $licenseController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$licenseController.generateKeys(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/generateKeys");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/generateKeys\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(licenseController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ LicenseController $licenseController;
                    {
                        this.$licenseController = $licenseController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$licenseController.generateKeys(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/generateLicense");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/generateLicense\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(licenseController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ LicenseController $licenseController;
                    {
                        this.$licenseController = $licenseController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$licenseController.generateLicense(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/generateLicense");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/generateLicense\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(licenseController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ LicenseController $licenseController;
                    {
                        this.$licenseController = $licenseController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$licenseController.generateLicense(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/isHostValid");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/isHostValid\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(licenseController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ LicenseController $licenseController;
                    {
                        this.$licenseController = $licenseController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$licenseController.isHostValid(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/isHostValid");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/isHostValid\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(licenseController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ LicenseController $licenseController;
                    {
                        this.$licenseController = $licenseController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$licenseController.isHostValid(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/activateLicense");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/activateLicense\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(licenseController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ LicenseController $licenseController;
                    {
                        this.$licenseController = $licenseController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$licenseController.activateLicense(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/isLicenseValid");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/isLicenseValid\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(licenseController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ LicenseController $licenseController;
                    {
                        this.$licenseController = $licenseController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$licenseController.isLicenseValid(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/isLicenseValid");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/isLicenseValid\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(licenseController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ LicenseController $licenseController;
                    {
                        this.$licenseController = $licenseController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$licenseController.isLicenseValid(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/decryptLicense");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/decryptLicense\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(licenseController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ LicenseController $licenseController;
                    {
                        this.$licenseController = $licenseController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$licenseController.decryptLicense(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/sendCodeToEmail");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/sendCodeToEmail\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(licenseController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ LicenseController $licenseController;
                    {
                        this.$licenseController = $licenseController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$licenseController.sendCodeToEmail(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/supplyLicense");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/supplyLicense\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(licenseController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ LicenseController $licenseController;
                    {
                        this.$licenseController = $licenseController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$licenseController.supplyLicense(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/backupToWebdav");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/backupToWebdav\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(webdavController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ WebdavController $webdavController;
                    {
                        this.$webdavController = $webdavController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$webdavController.backupToWebdav(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/getRssSources");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/getRssSources\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(rssSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ RssSourceController $rssSourceController;
                    {
                        this.$rssSourceController = $rssSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$rssSourceController.getRssSources(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/saveRssSource");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/saveRssSource\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(rssSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ RssSourceController $rssSourceController;
                    {
                        this.$rssSourceController = $rssSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$rssSourceController.saveRssSource(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/saveRssSources");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/saveRssSources\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(rssSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ RssSourceController $rssSourceController;
                    {
                        this.$rssSourceController = $rssSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$rssSourceController.saveRssSources(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/deleteRssSource");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/deleteRssSource\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(rssSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ RssSourceController $rssSourceController;
                    {
                        this.$rssSourceController = $rssSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$rssSourceController.deleteRssSource(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/getRssArticles");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/getRssArticles\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(rssSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ RssSourceController $rssSourceController;
                    {
                        this.$rssSourceController = $rssSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$rssSourceController.getRssArticles(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/getRssArticles");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/getRssArticles\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(rssSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ RssSourceController $rssSourceController;
                    {
                        this.$rssSourceController = $rssSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$rssSourceController.getRssArticles(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/getRssContent");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/getRssContent\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(rssSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ RssSourceController $rssSourceController;
                    {
                        this.$rssSourceController = $rssSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$rssSourceController.getRssContent(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/getRssContent");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/getRssContent\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(rssSourceController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ RssSourceController $rssSourceController;
                    {
                        this.$rssSourceController = $rssSourceController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$rssSourceController.getRssContent(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/getReplaceRules");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/getReplaceRules\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(replaceRuleController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ ReplaceRuleController $replaceRuleController;
                    {
                        this.$replaceRuleController = $replaceRuleController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$replaceRuleController.list(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/saveReplaceRule");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/saveReplaceRule\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(replaceRuleController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ ReplaceRuleController $replaceRuleController;
                    {
                        this.$replaceRuleController = $replaceRuleController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$replaceRuleController.save(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/saveReplaceRules");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/saveReplaceRules\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(replaceRuleController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ ReplaceRuleController $replaceRuleController;
                    {
                        this.$replaceRuleController = $replaceRuleController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$replaceRuleController.saveMulti(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/deleteReplaceRule");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/deleteReplaceRule\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(replaceRuleController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ ReplaceRuleController $replaceRuleController;
                    {
                        this.$replaceRuleController = $replaceRuleController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$replaceRuleController.delete(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/deleteReplaceRules");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/deleteReplaceRules\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(replaceRuleController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ ReplaceRuleController $replaceRuleController;
                    {
                        this.$replaceRuleController = $replaceRuleController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$replaceRuleController.deleteMulti(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/getBookmarks");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/getBookmarks\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookmarkController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookmarkController $bookmarkController;
                    {
                        this.$bookmarkController = $bookmarkController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookmarkController.list(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/saveBookmark");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/saveBookmark\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookmarkController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookmarkController $bookmarkController;
                    {
                        this.$bookmarkController = $bookmarkController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookmarkController.save(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/saveBookmarks");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/saveBookmarks\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookmarkController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookmarkController $bookmarkController;
                    {
                        this.$bookmarkController = $bookmarkController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookmarkController.saveMulti(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/deleteBookmark");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/deleteBookmark\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookmarkController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookmarkController $bookmarkController;
                    {
                        this.$bookmarkController = $bookmarkController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookmarkController.delete(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/deleteBookmarks");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/deleteBookmarks\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(bookmarkController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ BookmarkController $bookmarkController;
                    {
                        this.$bookmarkController = $bookmarkController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$bookmarkController.deleteMulti(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/file/list");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/file/list\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(fileController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ FileController $fileController;
                    {
                        this.$fileController = $fileController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$fileController.list(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/file/get");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/file/get\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(fileController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ FileController $fileController;
                    {
                        this.$fileController = $fileController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$fileController.get(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/file/save");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/file/save\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(fileController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ FileController $fileController;
                    {
                        this.$fileController = $fileController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$fileController.save(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/file/mkdir");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/file/mkdir\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(fileController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ FileController $fileController;
                    {
                        this.$fileController = $fileController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$fileController.mkdir(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/file/download");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/file/download\")");
                var0.coroutineHandlerWithoutRes(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Unit>, Object>(fileController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ FileController $fileController;
                    {
                        this.$fileController = $fileController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$fileController.download(it, this);
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
                        Function2<RoutingContext, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<? super Unit> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/file/upload");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/file/upload\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(fileController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ FileController $fileController;
                    {
                        this.$fileController = $fileController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$fileController.upload(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/file/delete");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/file/delete\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(fileController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ FileController $fileController;
                    {
                        this.$fileController = $fileController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$fileController.delete(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/file/deleteMulti");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/file/deleteMulti\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(fileController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ FileController $fileController;
                    {
                        this.$fileController = $fileController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$fileController.deleteMulti(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/file/importPreview");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/file/importPreview\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(fileController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ FileController $fileController;
                    {
                        this.$fileController = $fileController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$fileController.importPreview(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/file/restore");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/file/restore\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(fileController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ FileController $fileController;
                    {
                        this.$fileController = $fileController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$fileController.restore(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/file/parse");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/file/parse\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(fileController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ FileController $fileController;
                    {
                        this.$fileController = $fileController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$fileController.parse(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/file/parse");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/file/parse\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(fileController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ FileController $fileController;
                    {
                        this.$fileController = $fileController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$fileController.parse(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.get("/reader3/httpTTS/list");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.get(\"/reader3/httpTTS/list\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(httpTTSController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ HttpTTSController $httpTTSController;
                    {
                        this.$httpTTSController = $httpTTSController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$httpTTSController.list(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/httpTTS/save");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/httpTTS/save\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(httpTTSController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ HttpTTSController $httpTTSController;
                    {
                        this.$httpTTSController = $httpTTSController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$httpTTSController.save(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/httpTTS/saveMulti");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/httpTTS/saveMulti\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(httpTTSController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ HttpTTSController $httpTTSController;
                    {
                        this.$httpTTSController = $httpTTSController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$httpTTSController.saveMulti(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/httpTTS/delete");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/httpTTS/delete\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(httpTTSController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ HttpTTSController $httpTTSController;
                    {
                        this.$httpTTSController = $httpTTSController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$httpTTSController.delete(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                var19_23 = var1_1.post("/reader3/httpTTS/deleteMulti");
                Intrinsics.checkNotNullExpressionValue(var19_23, "router.post(\"/reader3/httpTTS/deleteMulti\")");
                var0.coroutineHandler(var19_23, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)new Function2<RoutingContext, Continuation<? super Object>, Object>(httpTTSController, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ HttpTTSController $httpTTSController;
                    {
                        this.$httpTTSController = $httpTTSController;
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
                                RoutingContext it = (RoutingContext)this.L$0;
                                this.label = 1;
                                Object object3 = this.$httpTTSController.deleteMulti(it, this);
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
                        Function2<RoutingContext, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                        function2.L$0 = value;
                        return (Continuation)((Object)function2);
                    }

                    @Nullable
                    public final Object invoke(@NotNull RoutingContext p1, @Nullable Continuation<Object> p2) {
                        return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                    }
                });
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @Nullable
    public Object setupPort(@NotNull Continuation<? super Unit> $completion) {
        return YueduApi.setupPort$suspendImpl(this, $completion);
    }

    static /* synthetic */ Object setupPort$suspendImpl(YueduApi this_, Continuation $completion) {
        YueduApiKt.access$getLogger$p().info("port: {}", (Object)Boxing.boxInt(this_.getPort()));
        Object object = this_.env;
        if (object == null) {
            Intrinsics.throwUninitializedPropertyAccessException("env");
            throw null;
        }
        Integer serverPort = object.getProperty("reader.server.port", Integer.TYPE);
        YueduApiKt.access$getLogger$p().info("serverPort: {}", (Object)serverPort);
        if (serverPort != null && ((Number)(object = serverPort)).intValue() > 0) {
            object = serverPort;
            this_.setPort(((Number)object).intValue());
        }
        return Unit.INSTANCE;
    }

    @Nullable
    public Object migration(@NotNull Continuation<? super Unit> $completion) {
        return YueduApi.migration$suspendImpl(this, $completion);
    }

    static /* synthetic */ Object migration$suspendImpl(YueduApi this_, Continuation $completion) {
        try {
            File storageDir = new File(ExtKt.getWorkDir("storage"));
            String[] stringArray = new String[]{"storage", "data", "default"};
            File dataDir = new File(ExtKt.getWorkDir(stringArray));
            if (!storageDir.exists()) {
                dataDir.mkdirs();
            } else if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return Unit.INSTANCE;
    }

    @Override
    @NotNull
    public String getContextPath() {
        Object object = this.env;
        if (object == null) {
            Intrinsics.throwUninitializedPropertyAccessException("env");
            throw null;
        }
        String contextPath = object.getProperty("reader.server.contextPath", String.class);
        object = contextPath;
        boolean bl = false;
        boolean bl2 = false;
        if (!(object == null || object.length() == 0)) {
            object = contextPath;
            Intrinsics.checkNotNullExpressionValue(object, "contextPath");
            return object;
        }
        return "";
    }

    @Override
    public void started() {
        SpringContextUtils.getApplicationContext().publishEvent(new SpringEvent(this, "READY", ""));
    }

    @Override
    public void onStartError() {
        YueduApiKt.access$getLogger$p().error("\u5e94\u7528\u542f\u52a8\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5" + this.getPort() + "\u7aef\u53e3\u662f\u5426\u88ab\u5360\u7528");
        SpringContextUtils.getApplicationContext().publishEvent(new SpringEvent(this, "START_ERROR", "\u5e94\u7528\u542f\u52a8\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5" + this.getPort() + "\u7aef\u53e3\u662f\u5426\u88ab\u5360\u7528"));
    }

    @Override
    public void onHandlerError(@NotNull RoutingContext ctx, @NotNull Exception error2) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(error2, "error");
        ReturnData returnData = new ReturnData();
        YueduApiKt.access$getLogger$p().error("onHandlerError: ", error2);
        if (!ctx.response().headWritten()) {
            VertExtKt.success(ctx, returnData.setErrorMsg(error2.toString()));
        } else {
            ctx.response().end(error2.toString());
        }
    }

    /*
     * Unable to fully structure code
     */
    private final Object getSystemInfo(RoutingContext var1_1, Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof getSystemInfo.1)) ** GOTO lbl-1000
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
                Object L$7;
                Object L$8;
                Object L$9;
                Object L$10;
                Object L$11;
                /* synthetic */ Object result;
                final /* synthetic */ YueduApi this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return YueduApi.access$getSystemInfo(this.this$0, null, this);
                }
            };
        }
        $result = $continuation.result;
        var20_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                returnData = new ReturnData();
                systemFont = System.getProperty("reader.system.fonts");
                freeMemory = "" + Runtime.getRuntime().freeMemory() / (long)1024 / (long)1024 + 'M';
                totalMemory = "" + Runtime.getRuntime().totalMemory() / (long)1024 / (long)1024 + 'M';
                maxMemory = "" + Runtime.getRuntime().maxMemory() / (long)1024 / (long)1024 + 'M';
                userController = new UserController(this.getCoroutineContext());
                dayLoginUser = new Ref.IntRef();
                sevenDayLoginUser = new Ref.IntRef();
                monthLoginUser = new Ref.IntRef();
                keepUser = new Ref.IntRef();
                dayRegisterUser = new Ref.IntRef();
                sevenDayRegisterUser = new Ref.IntRef();
                monthRegisterUser = new Ref.IntRef();
                calendar = Calendar.getInstance();
                calendar.set(5, 1);
                calendar.set(11, 0);
                calendar.set(12, 0);
                calendar.set(13, 0);
                calendar.set(14, 0);
                calendar.getTimeInMillis();
                $continuation.L$0 = returnData;
                $continuation.L$1 = systemFont;
                $continuation.L$2 = freeMemory;
                $continuation.L$3 = totalMemory;
                $continuation.L$4 = maxMemory;
                $continuation.L$5 = dayLoginUser;
                $continuation.L$6 = sevenDayLoginUser;
                $continuation.L$7 = monthLoginUser;
                $continuation.L$8 = keepUser;
                $continuation.L$9 = dayRegisterUser;
                $continuation.L$10 = sevenDayRegisterUser;
                $continuation.L$11 = monthRegisterUser;
                $continuation.label = 1;
                v0 = userController.forEachUser((Function3<? super CoroutineScope, ? super User, ? super Continuation<? super Boolean>, ? extends Object>)new Function3<CoroutineScope, User, Continuation<? super Boolean>, Object>(dayLoginUser, sevenDayLoginUser, calendar, monthLoginUser, dayRegisterUser, sevenDayRegisterUser, monthRegisterUser, keepUser, null){
                    int label;
                    /* synthetic */ Object L$0;
                    final /* synthetic */ Ref.IntRef $dayLoginUser;
                    final /* synthetic */ Ref.IntRef $sevenDayLoginUser;
                    final /* synthetic */ Calendar $calendar;
                    final /* synthetic */ Ref.IntRef $monthLoginUser;
                    final /* synthetic */ Ref.IntRef $dayRegisterUser;
                    final /* synthetic */ Ref.IntRef $sevenDayRegisterUser;
                    final /* synthetic */ Ref.IntRef $monthRegisterUser;
                    final /* synthetic */ Ref.IntRef $keepUser;
                    {
                        this.$dayLoginUser = $dayLoginUser;
                        this.$sevenDayLoginUser = $sevenDayLoginUser;
                        this.$calendar = $calendar;
                        this.$monthLoginUser = $monthLoginUser;
                        this.$dayRegisterUser = $dayRegisterUser;
                        this.$sevenDayRegisterUser = $sevenDayRegisterUser;
                        this.$monthRegisterUser = $monthRegisterUser;
                        this.$keepUser = $keepUser;
                        super(3, $completion);
                    }

                    @Nullable
                    public final Object invokeSuspend(@NotNull Object object) {
                        Object object2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        switch (this.label) {
                            case 0: {
                                ResultKt.throwOnFailure(object);
                                User user = (User)this.L$0;
                                if (user.getLast_login_at() >= System.currentTimeMillis() - 86400000L) {
                                    ++this.$dayLoginUser.element;
                                }
                                if (user.getLast_login_at() >= System.currentTimeMillis() - 604800000L) {
                                    ++this.$sevenDayLoginUser.element;
                                }
                                if (user.getLast_login_at() >= this.$calendar.getTimeInMillis()) {
                                    ++this.$monthLoginUser.element;
                                }
                                if (user.getCreated_at() >= System.currentTimeMillis() - 86400000L) {
                                    ++this.$dayRegisterUser.element;
                                }
                                if (user.getCreated_at() >= System.currentTimeMillis() - 604800000L) {
                                    ++this.$sevenDayRegisterUser.element;
                                }
                                if (user.getCreated_at() >= this.$calendar.getTimeInMillis()) {
                                    ++this.$monthRegisterUser.element;
                                }
                                if (user.getLast_login_at() >= user.getCreated_at() + 604800000L && user.getLast_login_at() >= System.currentTimeMillis() - 604800000L) {
                                    ++this.$keepUser.element;
                                }
                                return Boxing.boxBoolean(false);
                            }
                        }
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }

                    @Nullable
                    public final Object invoke(@NotNull CoroutineScope p1, @NotNull User p2, @Nullable Continuation<? super Boolean> p3) {
                        Function3<CoroutineScope, User, Continuation<? super Boolean>, Object> function3 = new /* invalid duplicate definition of identical inner class */;
                        function3.L$0 = p2;
                        return function3.invokeSuspend(Unit.INSTANCE);
                    }
                }, $continuation);
                if (v0 == var20_5) {
                    return var20_5;
                }
                ** GOTO lbl65
            }
            case 1: {
                monthRegisterUser = (Ref.IntRef)$continuation.L$11;
                sevenDayRegisterUser = (Ref.IntRef)$continuation.L$10;
                dayRegisterUser = (Ref.IntRef)$continuation.L$9;
                keepUser = (Ref.IntRef)$continuation.L$8;
                monthLoginUser = (Ref.IntRef)$continuation.L$7;
                sevenDayLoginUser = (Ref.IntRef)$continuation.L$6;
                dayLoginUser = (Ref.IntRef)$continuation.L$5;
                maxMemory = (String)$continuation.L$4;
                totalMemory = (String)$continuation.L$3;
                freeMemory = (String)$continuation.L$2;
                systemFont = (String)$continuation.L$1;
                returnData = (ReturnData)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl65:
                // 2 sources

                var17_20 = new Pair[]{TuplesKt.to("fonts", systemFont), TuplesKt.to("freeMemory", freeMemory), TuplesKt.to("totalMemory", totalMemory), TuplesKt.to("maxMemory", maxMemory), TuplesKt.to("dayRegisterUser", Boxing.boxInt(dayRegisterUser.element)), TuplesKt.to("dayLoginUser", Boxing.boxInt(dayLoginUser.element)), TuplesKt.to("sevenDayRegisterUser", Boxing.boxInt(sevenDayRegisterUser.element)), TuplesKt.to("sevenDayLoginUser", Boxing.boxInt(sevenDayLoginUser.element)), TuplesKt.to("monthRegisterUser", Boxing.boxInt(monthRegisterUser.element)), TuplesKt.to("monthLoginUser", Boxing.boxInt(monthLoginUser.element)), TuplesKt.to("keepUser", Boxing.boxInt(keepUser.element))};
                return ReturnData.setData$default(returnData, MapsKt.mapOf(var17_20), null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @Scheduled(cron="0 0/10 * * * ?")
    public void shelfUpdateJob() {
        AppConfig appConfig = this.appConfig;
        if (appConfig == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appConfig");
            throw null;
        }
        if (appConfig.getShelfUpdateInteval() <= 0) {
            return;
        }
        Calendar now = Calendar.getInstance();
        int hour = now.get(11);
        int munite = now.get(12);
        int muniteFromToday = hour * 60 + munite;
        AppConfig appConfig2 = this.appConfig;
        if (appConfig2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appConfig");
            throw null;
        }
        if (muniteFromToday % appConfig2.getShelfUpdateInteval() != 0) {
            return;
        }
        MDC.put("traceId", ExtKt.getTraceId());
        BuildersKt.launch$default(this, new MDCContext(null, 1, null).plus(Dispatchers.getIO()), null, new Function2<CoroutineScope, Continuation<? super Unit>, Object>(null){
            Object L$1;
            int label;
            private /* synthetic */ Object L$0;

            /*
             * Unable to fully structure code
             */
            @Nullable
            public final Object invokeSuspend(@NotNull Object var1_1) {
                var5_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0: {
                        ResultKt.throwOnFailure(var1_1);
                        $this$launch = (CoroutineScope)this.L$0;
                        bookController = new BookController($this$launch.getCoroutineContext());
                        YueduApiKt.access$getLogger$p().info("\u5f00\u59cb\u68c0\u67e5\u4e66\u67b6\u4e66\u7c4d\u66f4\u65b0");
                        this.L$0 = $this$launch;
                        this.L$1 = bookController;
                        this.label = 1;
                        v0 = bookController.getBookShelfBooks(true, "default", this);
                        ** if (v0 != var5_2) goto lbl16
lbl15:
                        // 1 sources

                        return var5_2;
lbl16:
                        // 1 sources

                        ** GOTO lbl24
                    }
                    case 1: {
                        bookController = (BookController)this.L$1;
                        $this$launch = (CoroutineScope)this.L$0;
                        ResultKt.throwOnFailure($result);
                        v0 = $result;
lbl24:
                        // 2 sources

                        userController = new UserController($this$launch.getCoroutineContext());
                        this.L$0 = null;
                        this.L$1 = null;
                        this.label = 2;
                        v1 = userController.forEachUser((Function3<? super CoroutineScope, ? super User, ? super Continuation<? super Boolean>, ? extends Object>)new Function3<CoroutineScope, User, Continuation<? super Boolean>, Object>(bookController, null){
                            int label;
                            /* synthetic */ Object L$0;
                            final /* synthetic */ BookController $bookController;
                            {
                                this.$bookController = $bookController;
                                super(3, $completion);
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
                                        User user = (User)this.L$0;
                                        if (user.getLast_login_at() < System.currentTimeMillis() - 259200000L) return Boxing.boxBoolean(false);
                                        this.label = 1;
                                        Object object3 = this.$bookController.getBookShelfBooks(true, user.getUsername(), this);
                                        if (object3 != object2) return Boxing.boxBoolean(false);
                                        return object2;
                                    }
                                    case 1: {
                                        void $result;
                                        ResultKt.throwOnFailure($result);
                                        Object object3 = $result;
                                        return Boxing.boxBoolean(false);
                                    }
                                }
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }

                            @Nullable
                            public final Object invoke(@NotNull CoroutineScope p1, @NotNull User p2, @Nullable Continuation<? super Boolean> p3) {
                                Function3<CoroutineScope, User, Continuation<? super Boolean>, Object> function3 = new /* invalid duplicate definition of identical inner class */;
                                function3.L$0 = p2;
                                return function3.invokeSuspend(Unit.INSTANCE);
                            }
                        }, this);
                        ** if (v1 != var5_2) goto lbl31
lbl30:
                        // 1 sources

                        return var5_2;
lbl31:
                        // 1 sources

                        ** GOTO lbl37
                    }
                    case 2: {
                        try {
                            ResultKt.throwOnFailure($result);
                            v1 = $result;
lbl37:
                            // 2 sources

                            YueduApiKt.access$getLogger$p().info("\u4e66\u67b6\u4e66\u7c4d\u66f4\u65b0\u68c0\u67e5\u7ed3\u675f");
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        return Unit.INSTANCE;
                    }
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            @NotNull
            public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                Function2<CoroutineScope, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                function2.L$0 = value;
                return (Continuation)((Object)function2);
            }

            @Nullable
            public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Unit> p2) {
                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
            }
        }, 2, null);
    }

    @Scheduled(cron="0 0/10 * * * ?")
    public void remoteBookSourceSubUpdateJob() {
        AppConfig appConfig = this.appConfig;
        if (appConfig == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appConfig");
            throw null;
        }
        if (appConfig.getRemoteBookSourceUpdateInterval() <= 0) {
            return;
        }
        Calendar now = Calendar.getInstance();
        int hour = now.get(11);
        int munite = now.get(12);
        int muniteFromToday = hour * 60 + munite;
        AppConfig appConfig2 = this.appConfig;
        if (appConfig2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appConfig");
            throw null;
        }
        if (muniteFromToday % appConfig2.getRemoteBookSourceUpdateInterval() != 0) {
            return;
        }
        MDC.put("traceId", ExtKt.getTraceId());
        BuildersKt.launch$default(this, new MDCContext(null, 1, null).plus(Dispatchers.getIO()), null, new Function2<CoroutineScope, Continuation<? super Unit>, Object>(null){
            Object L$1;
            int label;
            private /* synthetic */ Object L$0;

            /*
             * Unable to fully structure code
             */
            @Nullable
            public final Object invokeSuspend(@NotNull Object var1_1) {
                var5_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0: {
                        ResultKt.throwOnFailure(var1_1);
                        $this$launch = (CoroutineScope)this.L$0;
                        bookSourceController = new BookSourceController($this$launch.getCoroutineContext());
                        YueduApiKt.access$getLogger$p().info("\u5f00\u59cb\u68c0\u67e5\u8fdc\u7a0b\u4e66\u6e90\u66f4\u65b0");
                        this.L$0 = $this$launch;
                        this.L$1 = bookSourceController;
                        this.label = 1;
                        v0 = bookSourceController.updateRemoteSourceSub("default", null, this);
                        ** if (v0 != var5_2) goto lbl16
lbl15:
                        // 1 sources

                        return var5_2;
lbl16:
                        // 1 sources

                        ** GOTO lbl24
                    }
                    case 1: {
                        bookSourceController = (BookSourceController)this.L$1;
                        $this$launch = (CoroutineScope)this.L$0;
                        ResultKt.throwOnFailure($result);
                        v0 = $result;
lbl24:
                        // 2 sources

                        userController = new UserController($this$launch.getCoroutineContext());
                        this.L$0 = null;
                        this.L$1 = null;
                        this.label = 2;
                        v1 = userController.forEachUser((Function3<? super CoroutineScope, ? super User, ? super Continuation<? super Boolean>, ? extends Object>)new Function3<CoroutineScope, User, Continuation<? super Boolean>, Object>(bookSourceController, null){
                            int label;
                            /* synthetic */ Object L$0;
                            final /* synthetic */ BookSourceController $bookSourceController;
                            {
                                this.$bookSourceController = $bookSourceController;
                                super(3, $completion);
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
                                        User user = (User)this.L$0;
                                        if (user.getLast_login_at() < System.currentTimeMillis() - 259200000L) return Boxing.boxBoolean(false);
                                        this.label = 1;
                                        Object object3 = this.$bookSourceController.updateRemoteSourceSub(user.getUsername(), user, this);
                                        if (object3 != object2) return Boxing.boxBoolean(false);
                                        return object2;
                                    }
                                    case 1: {
                                        void $result;
                                        ResultKt.throwOnFailure($result);
                                        Object object3 = $result;
                                        return Boxing.boxBoolean(false);
                                    }
                                }
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }

                            @Nullable
                            public final Object invoke(@NotNull CoroutineScope p1, @NotNull User p2, @Nullable Continuation<? super Boolean> p3) {
                                Function3<CoroutineScope, User, Continuation<? super Boolean>, Object> function3 = new /* invalid duplicate definition of identical inner class */;
                                function3.L$0 = p2;
                                return function3.invokeSuspend(Unit.INSTANCE);
                            }
                        }, this);
                        ** if (v1 != var5_2) goto lbl31
lbl30:
                        // 1 sources

                        return var5_2;
lbl31:
                        // 1 sources

                        ** GOTO lbl37
                    }
                    case 2: {
                        try {
                            ResultKt.throwOnFailure($result);
                            v1 = $result;
lbl37:
                            // 2 sources

                            YueduApiKt.access$getLogger$p().info("\u8fdc\u7a0b\u4e66\u6e90\u66f4\u65b0\u68c0\u67e5\u7ed3\u675f");
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        return Unit.INSTANCE;
                    }
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            @NotNull
            public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                Function2<CoroutineScope, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                function2.L$0 = value;
                return (Continuation)((Object)function2);
            }

            @Nullable
            public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Unit> p2) {
                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
            }
        }, 2, null);
    }

    @Scheduled(cron="0 59 23 * * ?")
    public void clearUser() {
        block6: {
            block5: {
                AppConfig appConfig = this.appConfig;
                if (appConfig == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("appConfig");
                    throw null;
                }
                if (appConfig.getAutoClearInactiveUser() <= 0) break block5;
                appConfig = this.appConfig;
                if (appConfig == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("appConfig");
                    throw null;
                }
                if (appConfig.getSecure()) break block6;
            }
            return;
        }
        MDC.put("traceId", ExtKt.getTraceId());
        BuildersKt.launch$default(this, new MDCContext(null, 1, null).plus(Dispatchers.getIO()), null, new Function2<CoroutineScope, Continuation<? super Unit>, Object>(this, null){
            int label;
            private /* synthetic */ Object L$0;
            final /* synthetic */ YueduApi this$0;
            {
                this.this$0 = $receiver;
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
                        $this$launch = (CoroutineScope)this.L$0;
                        var3_4 = YueduApi.access$getAppConfig$p(this.this$0);
                        if (var3_4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("appConfig");
                            throw null;
                        }
                        YueduApiKt.access$getLogger$p().info("\u5f00\u59cb\u6e05\u7406 {} \u5929\u672a\u767b\u5f55\u7528\u6237", (Object)Boxing.boxInt(var3_4.getAutoClearInactiveUser()));
                        userController = new UserController($this$launch.getCoroutineContext());
                        var4_6 = YueduApi.access$getAppConfig$p(this.this$0);
                        if (var4_6 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("appConfig");
                            throw null;
                        }
                        this.label = 1;
                        v0 = userController.clearInactiveUsers(var4_6.getAutoClearInactiveUser(), this);
                        ** if (v0 != var5_2) goto lbl22
lbl21:
                        // 1 sources

                        return var5_2;
lbl22:
                        // 1 sources

                        ** GOTO lbl28
                    }
                    case 1: {
                        try {
                            ResultKt.throwOnFailure($result);
                            v0 = $result;
lbl28:
                            // 2 sources

                            YueduApiKt.access$getLogger$p().info("\u4e0d\u6d3b\u8dc3\u7528\u6237\u81ea\u52a8\u6e05\u7406\u7ed3\u675f");
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        return Unit.INSTANCE;
                    }
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            @NotNull
            public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                Function2<CoroutineScope, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                function2.L$0 = value;
                return (Continuation)((Object)function2);
            }

            @Nullable
            public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Unit> p2) {
                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
            }
        }, 2, null);
    }

    @Scheduled(cron="0 50 23 * * ?")
    public void autoBackup() {
        AppConfig appConfig = this.appConfig;
        if (appConfig == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appConfig");
            throw null;
        }
        if (!appConfig.getAutoBackupUserData()) {
            return;
        }
        MDC.put("traceId", ExtKt.getTraceId());
        BuildersKt.launch$default(this, new MDCContext(null, 1, null).plus(Dispatchers.getIO()), null, new Function2<CoroutineScope, Continuation<? super Unit>, Object>(null){
            Object L$1;
            int label;
            private /* synthetic */ Object L$0;

            /*
             * Unable to fully structure code
             */
            @Nullable
            public final Object invokeSuspend(@NotNull Object var1_1) {
                var5_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0: {
                        ResultKt.throwOnFailure(var1_1);
                        $this$launch = (CoroutineScope)this.L$0;
                        bookController = new BookController($this$launch.getCoroutineContext());
                        YueduApiKt.access$getLogger$p().info("\u5f00\u59cb\u5907\u4efd\u7528\u6237\u6570\u636e");
                        this.L$0 = $this$launch;
                        this.L$1 = bookController;
                        this.label = 1;
                        v0 = BookController.saveToWebdav$default(bookController, "default", null, this, 2, null);
                        ** if (v0 != var5_2) goto lbl16
lbl15:
                        // 1 sources

                        return var5_2;
lbl16:
                        // 1 sources

                        ** GOTO lbl24
                    }
                    case 1: {
                        bookController = (BookController)this.L$1;
                        $this$launch = (CoroutineScope)this.L$0;
                        ResultKt.throwOnFailure($result);
                        v0 = $result;
lbl24:
                        // 2 sources

                        userController = new UserController($this$launch.getCoroutineContext());
                        this.L$0 = null;
                        this.L$1 = null;
                        this.label = 2;
                        v1 = userController.forEachUser((Function3<? super CoroutineScope, ? super User, ? super Continuation<? super Boolean>, ? extends Object>)new Function3<CoroutineScope, User, Continuation<? super Boolean>, Object>(bookController, null){
                            int label;
                            /* synthetic */ Object L$0;
                            final /* synthetic */ BookController $bookController;
                            {
                                this.$bookController = $bookController;
                                super(3, $completion);
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
                                        User user = (User)this.L$0;
                                        if (user.getLast_login_at() < System.currentTimeMillis() - 259200000L) return Boxing.boxBoolean(false);
                                        this.label = 1;
                                        Object object3 = BookController.saveToWebdav$default(this.$bookController, user.getUsername(), null, this, 2, null);
                                        if (object3 != object2) return Boxing.boxBoolean(false);
                                        return object2;
                                    }
                                    case 1: {
                                        void $result;
                                        ResultKt.throwOnFailure($result);
                                        Object object3 = $result;
                                        return Boxing.boxBoolean(false);
                                    }
                                }
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }

                            @Nullable
                            public final Object invoke(@NotNull CoroutineScope p1, @NotNull User p2, @Nullable Continuation<? super Boolean> p3) {
                                Function3<CoroutineScope, User, Continuation<? super Boolean>, Object> function3 = new /* invalid duplicate definition of identical inner class */;
                                function3.L$0 = p2;
                                return function3.invokeSuspend(Unit.INSTANCE);
                            }
                        }, this);
                        ** if (v1 != var5_2) goto lbl31
lbl30:
                        // 1 sources

                        return var5_2;
lbl31:
                        // 1 sources

                        ** GOTO lbl37
                    }
                    case 2: {
                        try {
                            ResultKt.throwOnFailure($result);
                            v1 = $result;
lbl37:
                            // 2 sources

                            YueduApiKt.access$getLogger$p().info("\u5907\u4efd\u7528\u6237\u6570\u636e\u7ed3\u675f");
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        return Unit.INSTANCE;
                    }
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            @NotNull
            public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                Function2<CoroutineScope, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                function2.L$0 = value;
                return (Continuation)((Object)function2);
            }

            @Nullable
            public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Unit> p2) {
                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
            }
        }, 2, null);
    }

    @Scheduled(cron="0 0 2 * * ?")
    public void autoGC() {
        System.gc();
    }

    @Scheduled(cron="0 4/15 7-23 * * ?")
    public void checkLicense() {
        License license = ExtKt.getInstalledLicense(true);
        if ("default".equals(license.getType())) {
            return;
        }
        MDC.put("traceId", ExtKt.getTraceId());
        BuildersKt.launch$default(this, new MDCContext(null, 1, null).plus(Dispatchers.getIO()), null, new Function2<CoroutineScope, Continuation<? super Unit>, Object>(license, null){
            int label;
            private /* synthetic */ Object L$0;
            final /* synthetic */ License $license;
            {
                this.$license = $license;
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
                        $this$launch = (CoroutineScope)this.L$0;
                        var3_4 = new IntRange(10, 120);
                        var4_6 = false;
                        this.L$0 = $this$launch;
                        this.label = 1;
                        v0 = DelayKt.delay((long)RangesKt.random(var3_4, (Random)Random.Default) * 1000L, this);
                        ** if (v0 != var5_2) goto lbl15
lbl14:
                        // 1 sources

                        return var5_2;
lbl15:
                        // 1 sources

                        ** GOTO lbl22
                    }
                    case 1: {
                        var2_3 = (CoroutineScope)this.L$0;
                        ResultKt.throwOnFailure($result);
                        v0 = $result;
lbl22:
                        // 2 sources

                        var3_4 = new IntRange(1, 10);
                        var4_6 = false;
                        this.L$0 = var2_3;
                        this.label = 2;
                        v1 = DelayKt.delay((long)RangesKt.random(var3_4, (Random)Random.Default) * 1000L, this);
                        ** if (v1 != var5_2) goto lbl29
lbl28:
                        // 1 sources

                        return var5_2;
lbl29:
                        // 1 sources

                        ** GOTO lbl36
                    }
                    case 2: {
                        var2_3 = (CoroutineScope)this.L$0;
                        ResultKt.throwOnFailure($result);
                        v1 = $result;
lbl36:
                        // 2 sources

                        YueduApiKt.access$getLogger$p().info("\u5f00\u59cb\u68c0\u67e5\u6388\u6743\u662f\u5426\u6b63\u5e38");
                        licenseController = new LicenseController(var2_3.getCoroutineContext());
                        this.L$0 = null;
                        this.label = 3;
                        v2 = licenseController.checkLicense(this.$license, this);
                        ** if (v2 != var5_2) goto lbl43
lbl42:
                        // 1 sources

                        return var5_2;
lbl43:
                        // 1 sources

                        ** GOTO lbl52
                    }
                    case 3: {
                        try {
                            ResultKt.throwOnFailure($result);
                            v2 = $result;
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
lbl52:
                        // 3 sources

                        return Unit.INSTANCE;
                    }
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            @NotNull
            public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                Function2<CoroutineScope, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                function2.L$0 = value;
                return (Continuation)((Object)function2);
            }

            @Nullable
            public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Unit> p2) {
                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
            }
        }, 2, null);
    }

    private static final void initRouter$lambda-0(Ref.ObjectRef $dataDir, RoutingContext it) {
        File filePath;
        Intrinsics.checkNotNullParameter($dataDir, "$dataDir");
        String string = it.request().path();
        Intrinsics.checkNotNullExpressionValue(string, "it.request().path()");
        String path = StringsKt.replace(string, "/book-assets/", "/", true);
        string = URIDecoder.decodeURIComponent(path, false);
        Intrinsics.checkNotNullExpressionValue(string, "decodeURIComponent(path, false)");
        path = string;
        if ((StringsKt.endsWith(path, "html", true) || StringsKt.endsWith(path, "htm", true)) && (filePath = new File(Intrinsics.stringPlus((String)$dataDir.element, path))).exists()) {
            String string2 = filePath.toString();
            Intrinsics.checkNotNullExpressionValue(string2, "filePath.toString()");
            BookConfig.INSTANCE.injectJavascriptToEpubChapter(string2);
        }
        it.next();
    }

    private static final void initRouter$lambda-1(Ref.ObjectRef $dataDir, RoutingContext it) {
        File filePath;
        Intrinsics.checkNotNullParameter($dataDir, "$dataDir");
        String string = it.request().path();
        Intrinsics.checkNotNullExpressionValue(string, "it.request().path()");
        String path = StringsKt.replace(string, "/epub/", "/", true);
        string = URLDecoder.decode(path, "UTF-8");
        Intrinsics.checkNotNullExpressionValue(string, "decode(path, \"UTF-8\")");
        path = string;
        if (StringsKt.endsWith(path, "html", true) && (filePath = new File(Intrinsics.stringPlus((String)$dataDir.element, path))).exists()) {
            String string2 = filePath.toString();
            Intrinsics.checkNotNullExpressionValue(string2, "filePath.toString()");
            BookConfig.INSTANCE.injectJavascriptToEpubChapter(string2);
        }
        it.next();
    }

    private static final void initRouter$lambda-2(RoutingContext it) {
        String string = it.request().path();
        Intrinsics.checkNotNullExpressionValue(string, "it.request().path()");
        if (StringsKt.endsWith$default(string, "/simple-web", false, 2, null)) {
            HttpServerResponse httpServerResponse = it.response();
            string = URLDecoder.decode(it.request().absoluteURI(), "UTF-8");
            Intrinsics.checkNotNullExpressionValue(string, "decode(it.request().absoluteURI(), \"UTF-8\")");
            httpServerResponse.putHeader("Location", StringsKt.replace$default(string, "/simple-web", "/simple-web/", false, 4, null)).setStatusCode(302).end();
        } else {
            it.next();
        }
    }

    private static final void initRouter$lambda-3(RoutingContext it) {
        License license = ExtKt.getInstalledLicense$default(false, 1, null);
        long simpleWebExpiredAt = 0L;
        String string = it.request().host();
        Intrinsics.checkNotNullExpressionValue(string, "it.request().host()");
        if (license.validHost(string)) {
            simpleWebExpiredAt = license.getSimpleWebExpiredAt();
        }
        if (simpleWebExpiredAt != 0L && simpleWebExpiredAt < System.currentTimeMillis()) {
            it.response().putHeader("content-type", "text/html; charset=UTF-8").setStatusCode(403).end("<html><head><title>\u672a\u6fc0\u6d3b\u8be5\u529f\u80fd</title></head><body><div style='text-align: center;padding: 30px 0;'>\u672a\u6fc0\u6d3b\u8be5\u529f\u80fd\uff0c\u8bf7\u52a0<a href='https://t.me/+pQ8HDlANPZ84ZWNl'>TG\u7fa4</a>\u6fc0\u6d3b</div></body></html>");
        } else {
            it.next();
        }
    }

    public static final /* synthetic */ Object access$getSystemInfo(YueduApi $this, RoutingContext context, Continuation $completion) {
        return $this.getSystemInfo(context, $completion);
    }

    public static final /* synthetic */ AppConfig access$getAppConfig$p(YueduApi $this) {
        return $this.appConfig;
    }
}

