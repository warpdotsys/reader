/*
 * Decompiled with CFR 0.152.
 */
package com.htmake.reader.verticle;

import com.htmake.reader.utils.ExtKt;
import com.htmake.reader.utils.VertExtKt;
import com.htmake.reader.verticle.RestVerticle;
import com.htmake.reader.verticle.RestVerticleKt;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.kotlin.coroutines.CoroutineVerticle;
import java.lang.invoke.LambdaMetafactory;
import java.net.URLDecoder;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.slf4j.MDCContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H&J\u0019\u0010\u0011\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\nH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J\u0010\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u001c\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00192\n\u0010\u0015\u001a\u00060\u001aj\u0002`\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u0012H\u0016J\u0011\u0010\u001d\u001a\u00020\u0012H\u0094@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001eJ\b\u0010\u001f\u001a\u00020\u0012H\u0016J6\u0010 \u001a\u00020\u0012*\u00020!2\"\u0010\"\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0$\u0012\u0006\u0012\u0004\u0018\u00010%0#\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010&J6\u0010'\u001a\u00020\u0012*\u00020!2\"\u0010\"\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0$\u0012\u0006\u0012\u0004\u0018\u00010%0#\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010&R\u001a\u0010\u0003\u001a\u00020\u0004X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0084.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006("}, d2={"Lcom/htmake/reader/verticle/RestVerticle;", "Lio/vertx/kotlin/coroutines/CoroutineVerticle;", "()V", "port", "", "getPort", "()I", "setPort", "(I)V", "router", "Lio/vertx/ext/web/Router;", "getRouter", "()Lio/vertx/ext/web/Router;", "setRouter", "(Lio/vertx/ext/web/Router;)V", "getContextPath", "", "initRouter", "", "(Lio/vertx/ext/web/Router;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onException", "error", "", "onHandlerError", "ctx", "Lio/vertx/ext/web/RoutingContext;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "onStartError", "start", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "started", "coroutineHandler", "Lio/vertx/ext/web/Route;", "fn", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "(Lio/vertx/ext/web/Route;Lkotlin/jvm/functions/Function2;)V", "coroutineHandlerWithoutRes", "reader-pro"})
public abstract class RestVerticle
extends CoroutineVerticle {
    protected Router router;
    private int port = 8080;

    @NotNull
    protected final Router getRouter() {
        Router router = this.router;
        if (router != null) {
            return router;
        }
        Intrinsics.throwUninitializedPropertyAccessException("router");
        throw null;
    }

    protected final void setRouter(@NotNull Router router) {
        Intrinsics.checkNotNullParameter(router, "<set-?>");
        this.router = router;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int n) {
        this.port = n;
    }

    @Override
    @Nullable
    protected Object start(@NotNull Continuation<? super Unit> $completion) {
        return RestVerticle.start$suspendImpl(this, $completion);
    }

    /*
     * Unable to fully structure code
     */
    static /* synthetic */ Object start$suspendImpl(RestVerticle var0, Continuation var1_1) {
        if (!(var1_1 instanceof start.1)) ** GOTO lbl-1000
        var8_2 = var1_1;
        if ((var8_2.label & -2147483648) != 0) {
            var8_2.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(var0, var1_1){
                Object L$0;
                /* synthetic */ Object result;
                final /* synthetic */ RestVerticle this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return RestVerticle.start$suspendImpl(this.this$0, this);
                }
            };
        }
        $result = $continuation.result;
        var9_4 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                $continuation.L$0 = this;
                $continuation.label = 1;
                v0 = super.start($continuation);
                if (v0 == var9_4) {
                    return var9_4;
                }
                ** GOTO lbl22
            }
            case 1: {
                this = (RestVerticle)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl22:
                // 2 sources

                var2_5 = Router.router(this.getVertx());
                Intrinsics.checkNotNullExpressionValue(var2_5, "router(vertx)");
                this.setRouter(var2_5);
                cookieName = "reader.session";
                var3_6 = this.getRouter().route();
                Intrinsics.checkNotNullExpressionValue(var3_6, "router.route()");
                v1 = var3_6;
                var3_6 = SessionHandler.create(LocalSessionStore.create(this.getVertx())).setSessionCookieName(cookieName).setSessionTimeout(604800000L).setSessionCookiePath("/");
                Intrinsics.checkNotNullExpressionValue(var3_6, "create(LocalSessionStore.create(vertx))\n                            .setSessionCookieName(cookieName)\n                            .setSessionTimeout(7L * 86400 * 1000)\n                            .setSessionCookiePath(\"/\")");
                VertExtKt.globalHandler(v1, (Handler)var3_6);
                var3_6 = this.getRouter().route();
                Intrinsics.checkNotNullExpressionValue(var3_6, "router.route()");
                VertExtKt.globalHandler((Route)var3_6, (Handler<RoutingContext>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, start$lambda-1(java.lang.String io.vertx.ext.web.RoutingContext ), (Lio/vertx/ext/web/RoutingContext;)V)((String)cookieName));
                var3_6 = this.getRouter().route();
                Intrinsics.checkNotNullExpressionValue(var3_6, "router.route()");
                VertExtKt.globalHandler((Route)var3_6, (Handler<RoutingContext>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, start$lambda-3(java.lang.String io.vertx.ext.web.RoutingContext ), (Lio/vertx/ext/web/RoutingContext;)V)((String)cookieName));
                var3_6 = this.getRouter().route();
                Intrinsics.checkNotNullExpressionValue(var3_6, "router.route()");
                v2 = var3_6;
                var3_6 = BodyHandler.create();
                Intrinsics.checkNotNullExpressionValue(var3_6, "create()");
                VertExtKt.globalHandler((Route)v2, (Handler)var3_6);
                var3_6 = this.getRouter().route();
                Intrinsics.checkNotNullExpressionValue(var3_6, "router.route()");
                v3 = var3_6;
                var3_6 = LoggerHandler.create(LoggerFormat.DEFAULT);
                Intrinsics.checkNotNullExpressionValue(var3_6, "create(LoggerFormat.DEFAULT)");
                VertExtKt.globalHandler((Route)v3, (Handler)var3_6);
                var3_6 = this.getRouter().route("/reader3/*");
                Intrinsics.checkNotNullExpressionValue(var3_6, "router.route(\"/reader3/*\")");
                VertExtKt.globalHandler((Route)var3_6, (Handler<RoutingContext>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, start$lambda-4(io.vertx.ext.web.RoutingContext ), (Lio/vertx/ext/web/RoutingContext;)V)());
                var3_6 = this.getRouter().get("/health");
                Intrinsics.checkNotNullExpressionValue(var3_6, "router.get(\"/health\")");
                VertExtKt.globalHandler((Route)var3_6, (Handler<RoutingContext>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, start$lambda-5(io.vertx.ext.web.RoutingContext ), (Lio/vertx/ext/web/RoutingContext;)V)());
                $continuation.L$0 = this;
                $continuation.label = 2;
                v4 = this.initRouter(this.getRouter(), $continuation);
                if (v4 == var9_4) {
                    return var9_4;
                }
                ** GOTO lbl66
            }
            case 2: {
                this = (RestVerticle)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v4 = $result;
lbl66:
                // 2 sources

                this.getRouter().route().last().failureHandler((Handler<RoutingContext>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, start$lambda-6(io.vertx.ext.web.RoutingContext ), (Lio/vertx/ext/web/RoutingContext;)V)());
                contextPath = this.getContextPath();
                var4_7 = null;
                var5_8 = contextPath;
                var6_9 = false;
                if (var5_8.length() > 0) {
                    var5_8 = Router.router(this.getVertx());
                    Intrinsics.checkNotNullExpressionValue(var5_8, "router(vertx)");
                    mainRouter = var5_8;
                    mainRouter.mountSubRouter(ExtKt.toDir(contextPath, true), this.getRouter());
                } else {
                    mainRouter = this.getRouter();
                }
                RestVerticleKt.access$getLogger$p().info("port: {}", (Object)Boxing.boxInt(this.getPort()));
                this.getVertx().createHttpServer().requestHandler((Handler)mainRouter).exceptionHandler((Handler<Throwable>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, start$lambda-7(com.htmake.reader.verticle.RestVerticle java.lang.Throwable ), (Ljava/lang/Throwable;)V)((RestVerticle)this)).listen(this.getPort(), (Handler<AsyncResult<HttpServer>>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, start$lambda-8(com.htmake.reader.verticle.RestVerticle io.vertx.core.AsyncResult ), (Lio/vertx/core/AsyncResult;)V)((RestVerticle)this));
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @Nullable
    public abstract Object initRouter(@NotNull Router var1, @NotNull Continuation<? super Unit> var2);

    @NotNull
    public abstract String getContextPath();

    public void onException(@NotNull Throwable error2) {
        Intrinsics.checkNotNullParameter(error2, "error");
        RestVerticleKt.access$getLogger$p().error("vertx exception: {}", error2);
    }

    public void onStartError() {
    }

    public void started() {
    }

    public void onHandlerError(@NotNull RoutingContext ctx, @NotNull Exception error2) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(error2, "error");
        RestVerticleKt.access$getLogger$p().error("Error: {}", error2);
        VertExtKt.error(ctx, error2);
    }

    public final void coroutineHandler(@NotNull Route $this$coroutineHandler, @NotNull Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object> fn) {
        Intrinsics.checkNotNullParameter($this$coroutineHandler, "<this>");
        Intrinsics.checkNotNullParameter(fn, "fn");
        VertExtKt.globalHandler($this$coroutineHandler, arg_0 -> RestVerticle.coroutineHandler$lambda-10(this, fn, arg_0));
    }

    public final void coroutineHandlerWithoutRes(@NotNull Route $this$coroutineHandlerWithoutRes, @NotNull Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object> fn) {
        Intrinsics.checkNotNullParameter($this$coroutineHandlerWithoutRes, "<this>");
        Intrinsics.checkNotNullParameter(fn, "fn");
        VertExtKt.globalHandler($this$coroutineHandlerWithoutRes, arg_0 -> RestVerticle.coroutineHandlerWithoutRes$lambda-12(this, fn, arg_0));
    }

    private static final void start$lambda-1$lambda-0(RoutingContext $it, String $cookieName, Void $noName_0) {
        Intrinsics.checkNotNullParameter($cookieName, "$cookieName");
        Cookie cookie = $it.getCookie($cookieName);
        if (cookie != null) {
            cookie.setMaxAge(172800000L);
            cookie.setPath("/");
        }
    }

    private static final void start$lambda-1(String $cookieName, RoutingContext it) {
        Intrinsics.checkNotNullParameter($cookieName, "$cookieName");
        it.addHeadersEndHandler(arg_0 -> RestVerticle.start$lambda-1$lambda-0(it, $cookieName, arg_0));
        it.next();
    }

    private static final void start$lambda-3$lambda-2(RoutingContext $it, Void $noName_0) {
        String origin = $it.request().getHeader("Origin");
        if (origin != null) {
            CharSequence charSequence = origin;
            boolean bl = false;
            if (charSequence.length() > 0) {
                HttpServerResponse res = $it.response();
                res.putHeader("Access-Control-Allow-Origin", origin);
                res.putHeader("Access-Control-Allow-Credentials", "true");
                res.putHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE");
                res.putHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, If-Match, If-Modified-Since, If-None-Match, If-Unmodified-Since, X-Requested-With");
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static final void start$lambda-3(String $cookieName, RoutingContext it) {
        Intrinsics.checkNotNullParameter($cookieName, "$cookieName");
        it.addHeadersEndHandler(arg_0 -> RestVerticle.start$lambda-3$lambda-2(it, arg_0));
        String origin = it.request().getHeader("Origin");
        if (origin != null) {
            CharSequence charSequence = origin;
            boolean bl = false;
            if (charSequence.length() > 0 && it.request().method() == HttpMethod.OPTIONS) {
                it.removeCookie($cookieName);
                Intrinsics.checkNotNullExpressionValue(it, "it");
                VertExtKt.success(it, "");
                return;
            }
        }
        it.next();
    }

    private static final void start$lambda-4(RoutingContext it) {
        String rawMethod = it.request().rawMethod();
        RestVerticleKt.access$getLogger$p().info("{} {}", (Object)rawMethod, (Object)URLDecoder.decode(it.request().absoluteURI(), "UTF-8"));
        if (!rawMethod.equals("PUT") && (it.fileUploads() == null || it.fileUploads().isEmpty()) && it.getBodyAsString() != null && it.getBodyAsString().length() > 0 && it.getBodyAsString().length() < 1000) {
            RestVerticleKt.access$getLogger$p().info("Request body: {}", (Object)it.getBodyAsString());
        }
        it.next();
    }

    private static final void start$lambda-5(RoutingContext it) {
        Intrinsics.checkNotNullExpressionValue(it, "it");
        VertExtKt.success(it, "ok!");
    }

    private static final void start$lambda-6(RoutingContext ctx) {
        Intrinsics.checkNotNullExpressionValue(ctx, "ctx");
        Throwable throwable = ctx.failure();
        Intrinsics.checkNotNullExpressionValue(throwable, "ctx.failure()");
        VertExtKt.error(ctx, throwable);
    }

    private static final void start$lambda-7(RestVerticle this$0, Throwable error2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullExpressionValue(error2, "error");
        this$0.onException(error2);
    }

    private static final void start$lambda-8(RestVerticle this$0, AsyncResult res) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (res.succeeded()) {
            RestVerticleKt.access$getLogger$p().info("Server running at: http://localhost:{}", (Object)this$0.getPort());
            RestVerticleKt.access$getLogger$p().info("Web reader running at: http://localhost:{}", (Object)this$0.getPort());
            String string = "ReaderApplication Started";
            boolean bl = false;
            System.out.println((Object)string);
            this$0.started();
        } else {
            this$0.onStartError();
        }
    }

    private static final void coroutineHandler$lambda-10$lambda-9(Ref.ObjectRef $job, Void it) {
        Intrinsics.checkNotNullParameter($job, "$job");
        RestVerticleKt.access$getLogger$p().info("\u5ba2\u6237\u7aef\u5df2\u65ad\u5f00\u94fe\u63a5\uff0c\u7ec8\u6b62\u8fd0\u884c");
        Job job = (Job)$job.element;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
    }

    private static final void coroutineHandler$lambda-10(RestVerticle this$0, Function2 $fn, RoutingContext ctx) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter($fn, "$fn");
        Ref.ObjectRef job = new Ref.ObjectRef();
        ctx.request().connection().closeHandler(arg_0 -> RestVerticle.coroutineHandler$lambda-10$lambda-9(job, arg_0));
        job.element = BuildersKt.launch$default(this$0, new MDCContext(null, 1, null).plus(Dispatchers.getIO()), null, new Function2<CoroutineScope, Continuation<? super Unit>, Object>(ctx, (Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)$fn, this$0, null){
            Object L$0;
            int label;
            final /* synthetic */ RoutingContext $ctx;
            final /* synthetic */ Function2<RoutingContext, Continuation<Object>, Object> $fn;
            final /* synthetic */ RestVerticle this$0;
            {
                this.$ctx = $ctx;
                this.$fn = $fn;
                this.this$0 = $receiver;
                super(2, $completion);
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
                        var2_3 = this.$ctx;
                        Intrinsics.checkNotNullExpressionValue(var2_3, "ctx");
                        var4_5 = var2_3;
                        var2_3 = this.$ctx;
                        Intrinsics.checkNotNullExpressionValue(var2_3, "ctx");
                        this.L$0 = var4_5;
                        this.label = 1;
                        v0 = this.$fn.invoke(var2_3, this);
                        ** if (v0 != var6_2) goto lbl17
lbl16:
                        // 1 sources

                        return var6_2;
lbl17:
                        // 1 sources

                        ** GOTO lbl24
                    }
                    case 1: {
                        var4_5 = (RoutingContext)this.L$0;
                        try {
                            ResultKt.throwOnFailure($result);
                            v0 = $result;
lbl24:
                            // 2 sources

                            var5_6 = v0;
                            VertExtKt.success(var4_5, var5_6);
                        }
                        catch (Exception e) {
                            var3_7 = this.$ctx;
                            Intrinsics.checkNotNullExpressionValue(var3_7, "ctx");
                            this.this$0.onHandlerError(var3_7, e);
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
    }

    private static final void coroutineHandlerWithoutRes$lambda-12$lambda-11(Ref.ObjectRef $job, Void it) {
        Intrinsics.checkNotNullParameter($job, "$job");
        RestVerticleKt.access$getLogger$p().info("\u5ba2\u6237\u7aef\u5df2\u65ad\u5f00\u94fe\u63a5\uff0c\u7ec8\u6b62\u8fd0\u884c");
        Job job = (Job)$job.element;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
    }

    private static final void coroutineHandlerWithoutRes$lambda-12(RestVerticle this$0, Function2 $fn, RoutingContext ctx) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter($fn, "$fn");
        Ref.ObjectRef job = new Ref.ObjectRef();
        ctx.request().connection().closeHandler(arg_0 -> RestVerticle.coroutineHandlerWithoutRes$lambda-12$lambda-11(job, arg_0));
        job.element = BuildersKt.launch$default(this$0, new MDCContext(null, 1, null).plus(Dispatchers.getIO()), null, new Function2<CoroutineScope, Continuation<? super Unit>, Object>((Function2<? super RoutingContext, ? super Continuation<Object>, ? extends Object>)$fn, ctx, this$0, null){
            int label;
            final /* synthetic */ Function2<RoutingContext, Continuation<Object>, Object> $fn;
            final /* synthetic */ RoutingContext $ctx;
            final /* synthetic */ RestVerticle this$0;
            {
                this.$fn = $fn;
                this.$ctx = $ctx;
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
                        RoutingContext routingContext = this.$ctx;
                        Intrinsics.checkNotNullExpressionValue(routingContext, "ctx");
                        this.label = 1;
                        Object object3 = this.$fn.invoke(routingContext, this);
                        if (object3 != object2) return Unit.INSTANCE;
                        return object2;
                    }
                    case 1: {
                        Object object3;
                        try {
                            void $result;
                            ResultKt.throwOnFailure($result);
                            object3 = $result;
                            return Unit.INSTANCE;
                        }
                        catch (Exception e) {
                            RoutingContext routingContext = this.$ctx;
                            Intrinsics.checkNotNullExpressionValue(routingContext, "ctx");
                            this.this$0.onHandlerError(routingContext, e);
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
    }
}

