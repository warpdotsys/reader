/*
 * Decompiled with CFR 0.152.
 */
package com.htmake.reader.api.controller;

import com.htmake.reader.api.ReturnData;
import com.htmake.reader.api.controller.BaseController;
import com.htmake.reader.api.controller.LicenseController;
import com.htmake.reader.api.controller.LicenseControllerKt;
import com.htmake.reader.entity.ActiveLicense;
import com.htmake.reader.entity.License;
import com.htmake.reader.utils.ExtKt;
import com.htmake.reader.utils.VertExtKt;
import io.legado.app.utils.ACache;
import io.legado.app.utils.Base64;
import io.legado.app.utils.EncoderUtils;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.kotlin.coroutines.VertxCoroutineKt;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineExceptionHandler;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.slf4j.MDCContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u0019\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001dJ\u0019\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u0019\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u0019\u0010 \u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u0019\u0010!\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u0019\u0010\"\u001a\u00020\u001a2\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u0019\u0010#\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u0019\u0010$\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u0019\u0010%\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u0019\u0010&\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018R!\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u000e\u0010\f\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0013\u0010\u000b\u001a\u0004\b\u0011\u0010\u0012\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006'"}, d2={"Lcom/htmake/reader/api/controller/LicenseController;", "Lcom/htmake/reader/api/controller/BaseController;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)V", "backupFileNames", "", "", "getBackupFileNames", "()[Ljava/lang/String;", "backupFileNames$delegate", "Lkotlin/Lazy;", "privateKeyContent", "tryCodeCache", "Lio/legado/app/utils/ACache;", "webClient", "Lio/vertx/ext/web/client/WebClient;", "getWebClient", "()Lio/vertx/ext/web/client/WebClient;", "webClient$delegate", "activateLicense", "Lcom/htmake/reader/api/ReturnData;", "context", "Lio/vertx/ext/web/RoutingContext;", "(Lio/vertx/ext/web/RoutingContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkLicense", "", "license", "Lcom/htmake/reader/entity/License;", "(Lcom/htmake/reader/entity/License;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "decryptLicense", "generateKeys", "generateLicense", "getLicense", "importLicense", "isHostValid", "isLicenseValid", "sendCodeToEmail", "supplyLicense", "reader-pro"})
public final class LicenseController
extends BaseController {
    @NotNull
    private final Lazy webClient$delegate;
    @NotNull
    private String privateKeyContent;
    @NotNull
    private ACache tryCodeCache;
    @NotNull
    private final Lazy backupFileNames$delegate;

    public LicenseController(@NotNull CoroutineContext coroutineContext2) {
        Intrinsics.checkNotNullParameter(coroutineContext2, "coroutineContext");
        super(coroutineContext2);
        this.webClient$delegate = LazyKt.lazy(webClient.2.INSTANCE);
        this.privateKeyContent = "";
        this.tryCodeCache = ACache.Companion.get("tryCodeCache", 2000000L, 10000);
        this.backupFileNames$delegate = LazyKt.lazy(backupFileNames.2.INSTANCE);
    }

    private final WebClient getWebClient() {
        Lazy lazy = this.webClient$delegate;
        boolean bl = false;
        Object t = lazy.getValue();
        Intrinsics.checkNotNullExpressionValue(t, "<get-webClient>(...)");
        return (WebClient)t;
    }

    @NotNull
    public final String[] getBackupFileNames() {
        Lazy lazy = this.backupFileNames$delegate;
        boolean bl = false;
        return (String[])lazy.getValue();
    }

    @Nullable
    public final Object getLicense(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        ReturnData returnData = new ReturnData();
        License license = ExtKt.getInstalledLicense$default(false, 1, null);
        return ReturnData.setData$default(returnData, MapsKt.mapOf(TuplesKt.to("license", license)), null, 2, null);
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object importLicense(@NotNull RoutingContext var1_1, @NotNull Continuation<? super Unit> var2_2) {
        if (!(var2_2 instanceof importLicense.1)) ** GOTO lbl-1000
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
                final /* synthetic */ LicenseController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.importLicense(null, this);
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
                this = (LicenseController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    VertExtKt.success(context, ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528"));
                    return Unit.INSTANCE;
                }
                if (!this.checkManagerAuth(context)) {
                    VertExtKt.success(context, ReturnData.setData$default(returnData, "NEED_SECURE_KEY", null, 2, null).setErrorMsg("\u8bf7\u8f93\u5165\u7ba1\u7406\u5bc6\u7801"));
                    return Unit.INSTANCE;
                }
                var5_7 = context.getBodyAsJson().getString("content");
                content = var5_7 == null ? "" : var5_7;
                var5_7 = content;
                var6_9 = false;
                var7_11 = false;
                if (var5_7.length() == 0) {
                    VertExtKt.success(context, returnData.setErrorMsg("\u8bf7\u8f93\u5165\u5bc6\u94a5"));
                    return Unit.INSTANCE;
                }
                $i$f$CoroutineExceptionHandler = false;
                var7_12 = CoroutineExceptionHandler.Key;
                exceptionHandler = new CoroutineExceptionHandler(var7_12, context, returnData){
                    final /* synthetic */ RoutingContext $context$inlined;
                    final /* synthetic */ ReturnData $returnData$inlined;
                    {
                        this.$context$inlined = routingContext;
                        this.$returnData$inlined = returnData;
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
                        LicenseControllerKt.access$getLogger$p().info("activate license error: {}", (Object)ex.getMessage());
                        VertExtKt.success(this.$context$inlined, this.$returnData$inlined.setErrorMsg(Intrinsics.stringPlus("\u5bc6\u94a5\u6fc0\u6d3b\u5931\u8d25: ", ex.getMessage())));
                    }
                };
                checkUrl = new Ref.ObjectRef<String>();
                checkUrl.element = "https://r.htmake.com/reader3/activateLicense";
                BuildersKt.launch$default(this, new MDCContext(null, 1, null).plus(Dispatchers.getIO()).plus(exceptionHandler), null, new Function2<CoroutineScope, Continuation<? super Unit>, Object>(this, checkUrl, content, context, returnData, null){
                    int label;
                    private /* synthetic */ Object L$0;
                    final /* synthetic */ LicenseController this$0;
                    final /* synthetic */ Ref.ObjectRef<String> $checkUrl;
                    final /* synthetic */ String $content;
                    final /* synthetic */ RoutingContext $context;
                    final /* synthetic */ ReturnData $returnData;
                    {
                        this.this$0 = $receiver;
                        this.$checkUrl = $checkUrl;
                        this.$content = $content;
                        this.$context = $context;
                        this.$returnData = $returnData;
                        super(2, $completion);
                    }

                    /*
                     * Unable to fully structure code
                     */
                    @Nullable
                    public final Object invokeSuspend(@NotNull Object var1_1) {
                        var22_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        switch (this.label) {
                            case 0: {
                                ResultKt.throwOnFailure(var1_1);
                                $this$launch = (CoroutineScope)this.L$0;
                                this.L$0 = $this$launch;
                                this.label = 1;
                                v0 = VertxCoroutineKt.awaitResult((Function1)new Function1<Handler<AsyncResult<HttpResponse<Buffer>>>, Unit>(this.this$0, this.$checkUrl, this.$content){
                                    final /* synthetic */ LicenseController this$0;
                                    final /* synthetic */ Ref.ObjectRef<String> $checkUrl;
                                    final /* synthetic */ String $content;
                                    {
                                        this.this$0 = $receiver;
                                        this.$checkUrl = $checkUrl;
                                        this.$content = $content;
                                        super(1);
                                    }

                                    public final void invoke(@NotNull Handler<AsyncResult<HttpResponse<Buffer>>> handler2) {
                                        Intrinsics.checkNotNullParameter(handler2, "handler");
                                        LicenseController.access$getWebClient(this.this$0).postAbs((String)this.$checkUrl.element).timeout(5000L).sendJsonObject(new JsonObject(MapsKt.mapOf(TuplesKt.to("content", this.$content))), handler2);
                                    }
                                }, this);
                                if (v0 == var22_2) {
                                    return var22_2;
                                }
                                ** GOTO lbl16
                            }
                            case 1: {
                                $this$launch = (CoroutineScope)this.L$0;
                                ResultKt.throwOnFailure($result);
                                v0 = $result;
lbl16:
                                // 2 sources

                                result = (HttpResponse)v0;
                                var4_5 = $this$launch;
                                var5_6 = this.$context;
                                var6_7 = false;
                                try {
                                    var7_9 = Result.Companion;
                                    var8_11 = var4_5;
                                    $i$a$-runCatching-LicenseController$importLicense$2$1 = false;
                                    var10_19 = result;
                                    if (var10_19 == null) {
                                        v1 = null;
                                    } else {
                                        var11_23 = var10_19.bodyAsJsonObject();
                                        if (var11_23 == null) {
                                            v1 = null;
                                        } else {
                                            var12_24 = var11_23;
                                            var13_26 = false;
                                            var14_29 = false;
                                            it = var12_24;
                                            $i$a$-let-LicenseController$importLicense$2$1$1 = false;
                                            if (!it.getBoolean("isSuccess").booleanValue()) {
                                                throw new Exception(it.getString("errorMsg"));
                                            }
                                            var17_34 = it;
                                            var12_24 = var17_34.getJsonObject("data");
                                            if (var12_24 == null) {
                                                v1 = null;
                                            } else {
                                                var13_27 = var12_24.getString("result");
                                                if (var13_27 == null) {
                                                    v1 = null;
                                                } else {
                                                    var14_30 = var13_27;
                                                    var15_32 = false;
                                                    var16_33 = false;
                                                    it = var14_30;
                                                    $i$a$-let-LicenseController$importLicense$2$1$2 = false;
                                                    license = ExtKt.decryptToLicense(it);
                                                    if (license == null) {
                                                        throw new Exception("\u5bc6\u94a5\u9519\u8bef");
                                                    }
                                                    var21_38 = var5_6.request().host();
                                                    Intrinsics.checkNotNullExpressionValue(var21_38, "context.request().host()");
                                                    if (!license.validHost(var21_38)) {
                                                        throw new Exception("\u5bc6\u94a5\u6388\u6743\u57df\u540d\u9519\u8bef");
                                                    }
                                                    v1 = new Pair<String, License>(it, license);
                                                }
                                            }
                                        }
                                    }
                                    var8_11 = v1;
                                    $i$a$-runCatching-LicenseController$importLicense$2$1 = false;
                                    var7_9 = Result.constructor-impl(var8_11);
                                }
                                catch (Throwable var8_12) {
                                    $i$a$-runCatching-LicenseController$importLicense$2$1 = Result.Companion;
                                    var10_20 = false;
                                    var7_9 = Result.constructor-impl(ResultKt.createFailure(var8_12));
                                }
                                var4_5 = var7_9;
                                var5_6 = this.$context;
                                var6_8 = this.$returnData;
                                var7_10 = false;
                                var8_13 = false;
                                if (Result.isSuccess-impl(var4_5)) {
                                    it = (Pair)var4_5;
                                    $i$a$-onSuccess-LicenseController$importLicense$2$2 = false;
                                    if (it != null) {
                                        var11_23 = new String[]{"data", "license"};
                                        ExtKt.saveStorage$default((String[])var11_23, it.getFirst(), false, ".key", 4, null);
                                        VertExtKt.success(var5_6, ReturnData.setData$default(var6_8, MapsKt.mapOf(TuplesKt.to("license", it.getSecond())), null, 2, null));
                                    } else {
                                        VertExtKt.success(var5_6, var6_8.setErrorMsg("\u5bc6\u94a5\u6fc0\u6d3b\u5931\u8d25"));
                                    }
                                }
                                var5_6 = this.$context;
                                var6_8 = this.$returnData;
                                var7_10 = false;
                                var8_13 = false;
                                v2 = Result.exceptionOrNull-impl(var4_5);
                                if (v2 != null) {
                                    var8_14 = v2;
                                    var9_18 = false;
                                    var10_22 = false;
                                    var11_23 = var8_14;
                                    var12_25 = false;
                                    it = var11_23;
                                    $i$a$-onFailure-LicenseController$importLicense$2$3 = false;
                                    LicenseControllerKt.access$getLogger$p().info("import license error: {}", (Object)it.getMessage());
                                    var15_31 = it.getMessage();
                                    VertExtKt.success(var5_6, var6_8.setErrorMsg(var15_31 == null ? "\u5bc6\u94a5\u6fc0\u6d3b\u9519\u8bef" : var15_31));
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
                return Unit.INSTANCE;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @Nullable
    public final Object generateKeys(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        ReturnData returnData = new ReturnData();
        KeyPair keyPair = EncoderUtils.INSTANCE.generateKeys();
        Pair[] pairArray = new Pair[]{TuplesKt.to("publicKey", Base64.encodeToString(keyPair.getPublic().getEncoded(), 2)), TuplesKt.to("privateKey", Base64.encodeToString(keyPair.getPrivate().getEncoded(), 2))};
        return ReturnData.setData$default(returnData, MapsKt.mapOf(pairArray), null, 2, null);
    }

    @Nullable
    public final Object generateLicense(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        boolean bl;
        Object object;
        ReturnData returnData = new ReturnData();
        Object host = null;
        long expiredAt = 0L;
        int userMaxLimit = 0;
        boolean openApi = false;
        long simpleWebExpiredAt = 0L;
        int instances = 0;
        Object type = null;
        Object key = null;
        Object code = null;
        if (context.request().method() == HttpMethod.POST) {
            object = context.getBodyAsJson().getString("host");
            host = object == null ? "" : object;
            object = context.getBodyAsJson().getLong("expiredAt");
            expiredAt = object == null ? 0L : (Long)object;
            object = context.getBodyAsJson().getInteger("userMaxLimit");
            userMaxLimit = object == null ? 15 : (Integer)object;
            object = context.getBodyAsJson().getBoolean("openApi");
            openApi = object == null ? false : (Boolean)object;
            object = context.getBodyAsJson().getLong("simpleWebExpiredAt");
            simpleWebExpiredAt = object == null ? 0L : (Long)object;
            object = context.getBodyAsJson().getInteger("instances");
            instances = object == null ? 1 : (Integer)object;
            object = context.getBodyAsJson().getString("type");
            type = object == null ? "" : object;
            object = context.getBodyAsJson().getString("key");
            key = object == null ? "" : object;
            object = context.getBodyAsJson().getString("code");
            code = object == null ? "" : object;
        } else {
            int n;
            long l;
            boolean bl2;
            int n2;
            Object object2;
            long l2;
            Object object3 = context.queryParam("host");
            Intrinsics.checkNotNullExpressionValue(object3, "context.queryParam(\"host\")");
            object = CollectionsKt.firstOrNull(object3);
            host = object == null ? "" : object;
            object3 = context.queryParam("expiredAt");
            Intrinsics.checkNotNullExpressionValue(object3, "context.queryParam(\"expiredAt\")");
            object = CollectionsKt.firstOrNull(object3);
            if (object == null) {
                l2 = 0L;
            } else {
                object2 = object;
                bl = false;
                object3 = Boxing.boxLong(Long.parseLong((String)object2));
                l2 = object3 == null ? 0L : (Long)object3;
            }
            expiredAt = l2;
            object3 = context.queryParam("userMaxLimit");
            Intrinsics.checkNotNullExpressionValue(object3, "context.queryParam(\"userMaxLimit\")");
            object = (String)CollectionsKt.firstOrNull(object3);
            if (object == null) {
                n2 = 15;
            } else {
                object2 = object;
                bl = false;
                object3 = Boxing.boxInt(Integer.parseInt((String)object2));
                n2 = object3 == null ? 15 : (Integer)object3;
            }
            userMaxLimit = n2;
            object3 = context.queryParam("openApi");
            Intrinsics.checkNotNullExpressionValue(object3, "context.queryParam(\"openApi\")");
            object = (String)CollectionsKt.firstOrNull(object3);
            if (object == null) {
                bl2 = false;
            } else {
                object2 = object;
                bl = false;
                object3 = Boxing.boxBoolean(Boolean.parseBoolean((String)object2));
                bl2 = object3 == null ? false : (Boolean)object3;
            }
            openApi = bl2;
            object3 = context.queryParam("simpleWebExpiredAt");
            Intrinsics.checkNotNullExpressionValue(object3, "context.queryParam(\"simpleWebExpiredAt\")");
            object = (String)CollectionsKt.firstOrNull(object3);
            if (object == null) {
                l = 0L;
            } else {
                object2 = object;
                bl = false;
                object3 = Boxing.boxLong(Long.parseLong((String)object2));
                l = object3 == null ? 0L : (Long)object3;
            }
            simpleWebExpiredAt = l;
            object3 = context.queryParam("instances");
            Intrinsics.checkNotNullExpressionValue(object3, "context.queryParam(\"instances\")");
            object = (String)CollectionsKt.firstOrNull(object3);
            if (object == null) {
                n = 1;
            } else {
                object2 = object;
                bl = false;
                object3 = Boxing.boxInt(Integer.parseInt((String)object2));
                n = object3 == null ? 1 : (Integer)object3;
            }
            instances = n;
            object3 = context.queryParam("type");
            Intrinsics.checkNotNullExpressionValue(object3, "context.queryParam(\"type\")");
            object = (String)CollectionsKt.firstOrNull(object3);
            type = object == null ? "" : object;
            object3 = context.queryParam("key");
            Intrinsics.checkNotNullExpressionValue(object3, "context.queryParam(\"key\")");
            object = CollectionsKt.firstOrNull(object3);
            key = object == null ? "" : object;
            object3 = context.queryParam("code");
            Intrinsics.checkNotNullExpressionValue(object3, "context.queryParam(\"code\")");
            object = CollectionsKt.firstOrNull(object3);
            code = object == null ? "" : object;
        }
        object = (CharSequence)host;
        boolean bl3 = false;
        boolean bl4 = false;
        if (object.length() == 0) {
            return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u57df\u540d");
        }
        if (!"Pvkp7tMQJpi4kWBE".equals(key)) {
            return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
        }
        License license = new License((String)host, userMaxLimit, expiredAt, openApi, simpleWebExpiredAt, instances, (String)type, null, null, false, null, 1408, null);
        CharSequence charSequence = (CharSequence)code;
        bl4 = false;
        bl = false;
        if (!(charSequence.length() == 0)) {
            license.setCode((String)code);
        }
        String licenseContent = ExtKt.jsonEncode$default(license, false, 2, null);
        CharSequence charSequence2 = this.privateKeyContent;
        bl = false;
        if (charSequence2.length() == 0) {
            String[] stringArray = new String[]{"data", "privateKey"};
            charSequence2 = ExtKt.getStorage(stringArray, ".key");
            this.privateKeyContent = charSequence2 == null ? "" : charSequence2;
        }
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(this.privateKeyContent, 2)));
        Intrinsics.checkNotNullExpressionValue(privateKey, "privateKey");
        String licenseKey = EncoderUtils.encryptSegmentByPrivateKey$default(EncoderUtils.INSTANCE, licenseContent, privateKey, 0, 4, null);
        Pair[] pairArray = new Pair[]{TuplesKt.to("host", host), TuplesKt.to("key", licenseKey)};
        return ReturnData.setData$default(returnData, MapsKt.mapOf(pairArray), null, 2, null);
    }

    @Nullable
    public final Object isHostValid(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        String string;
        ReturnData returnData = new ReturnData();
        String host = null;
        if (context.request().method() == HttpMethod.POST) {
            string = context.getBodyAsJson().getString("host");
            Intrinsics.checkNotNullExpressionValue(string, "context.bodyAsJson.getString(\"host\")");
            host = string;
        } else {
            List<String> list2 = context.queryParam("host");
            Intrinsics.checkNotNullExpressionValue(list2, "context.queryParam(\"host\")");
            string = CollectionsKt.firstOrNull(list2);
            host = string == null ? "" : string;
        }
        License license = ExtKt.getInstalledLicense$default(false, 1, null);
        return ReturnData.setData$default(returnData, MapsKt.mapOf(TuplesKt.to("isValid", Boxing.boxBoolean(license.validHost(host)))), null, 2, null);
    }

    @Nullable
    public final Object decryptLicense(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        ReturnData returnData = new ReturnData();
        CharSequence charSequence = context.getBodyAsJson().getString("content");
        String content = charSequence == null ? "" : charSequence;
        charSequence = content;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence.length() == 0) {
            return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u5bc6\u94a5");
        }
        License license = ExtKt.decryptToLicense(content);
        if (license == null) {
            return returnData.setErrorMsg("\u5bc6\u94a5\u9519\u8bef");
        }
        return ReturnData.setData$default(returnData, license, null, 2, null);
    }

    @Nullable
    public final Object activateLicense(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        Object object;
        String ip;
        int n;
        int activeTimes;
        JsonArray activeLicenseList;
        License license;
        ReturnData returnData;
        block12: {
            String i2;
            block11: {
                int i2;
                returnData = new ReturnData();
                CharSequence charSequence = context.getBodyAsJson().getString("content");
                String content = charSequence == null ? "" : charSequence;
                charSequence = content;
                boolean bl = false;
                boolean bl2 = false;
                if (charSequence.length() == 0) {
                    return returnData.setErrorMsg("\u8bf7\u8f93\u5165\u5bc6\u94a5");
                }
                license = ExtKt.decryptToLicense(content);
                if (license == null) {
                    return returnData.setErrorMsg("\u5bc6\u94a5\u9519\u8bef");
                }
                if (license.getVerified()) {
                    return returnData.setErrorMsg("\u5bc6\u94a5\u5df2\u88ab\u4f7f\u7528");
                }
                String[] stringArray = new String[]{"data", "activeLicense"};
                activeLicenseList = ExtKt.asJsonArray(ExtKt.getStorage$default(stringArray, null, 2, null));
                if (activeLicenseList == null) {
                    activeLicenseList = new JsonArray();
                }
                activeTimes = 0;
                int n2 = 0;
                int n3 = activeLicenseList.size();
                if (n2 < n3) {
                    do {
                        ActiveLicense _activeLicense22;
                        if (!(_activeLicense22 = activeLicenseList.getJsonObject(i2 = n2++).mapTo(ActiveLicense.class)).getType().equals(license.getType()) || !_activeLicense22.getCode().equals(license.getCode())) continue;
                        n = activeTimes;
                        activeTimes = n + 1;
                    } while (n2 < n3);
                }
                if (activeTimes >= license.getInstances()) {
                    return returnData.setErrorMsg("\u5bc6\u94a5\u5df2\u8d85\u8fc7\u6700\u5927\u4f7f\u7528\u6b21\u6570");
                }
                ip = null;
                ip = context.request().getHeader("X-Real-IP");
                if (ip == null) break block11;
                object = ip;
                object = (CharSequence)object;
                i2 = 0;
                if (!(object.length() == 0)) break block12;
            }
            ip = (object = context.request().remoteAddress()) == null ? "" : ((i2 = object.host()) == null ? "" : i2);
        }
        license.setVerified(true);
        license.setVerifyTime(Boxing.boxLong(System.currentTimeMillis()));
        object = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(object, "randomUUID().toString()");
        license.setId((String)object);
        ActiveLicense i2 = license.toActiveLicense();
        boolean _activeLicense22 = false;
        n = 0;
        ActiveLicense it222 = i2;
        boolean bl = false;
        it222.setActiveOrder(activeTimes + 1);
        it222.setActiveTime(System.currentTimeMillis());
        String string = ip;
        it222.setActiveIp(string);
        it222.setActiveEmail("");
        ActiveLicense activeLicense = it222;
        String[] _activeLicense22 = JsonObject.mapFrom(activeLicense);
        Intrinsics.checkNotNullExpressionValue(_activeLicense22, "mapFrom(activeLicense)");
        String[] activeLicenseObject = _activeLicense22;
        activeLicenseList.add((JsonObject)activeLicenseObject);
        LicenseControllerKt.access$getLogger$p().info("activeLicenseList: {}", (Object)activeLicenseList);
        _activeLicense22 = new String[]{"data", "activeLicense"};
        ExtKt.saveStorage$default(_activeLicense22, activeLicenseList, false, null, 12, null);
        String licenseContent = ExtKt.jsonEncode$default(license, false, 2, null);
        CharSequence charSequence = this.privateKeyContent;
        boolean it222 = false;
        if (charSequence.length() == 0) {
            String[] it222 = new String[]{"data", "privateKey"};
            charSequence = ExtKt.getStorage(it222, ".key");
            this.privateKeyContent = charSequence == null ? "" : charSequence;
        }
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(this.privateKeyContent, 2)));
        Intrinsics.checkNotNullExpressionValue(privateKey, "privateKey");
        String licenseKey = EncoderUtils.encryptSegmentByPrivateKey$default(EncoderUtils.INSTANCE, licenseContent, privateKey, 0, 4, null);
        return ReturnData.setData$default(returnData, MapsKt.mapOf(TuplesKt.to("result", licenseKey)), null, 2, null);
    }

    @Nullable
    public final Object isLicenseValid(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        Object[] i2;
        String ip;
        Map result2;
        int index;
        ActiveLicense activeLicense;
        JsonArray activeLicenseList;
        ReturnData returnData;
        block14: {
            Object _activeLicense2;
            block13: {
                String[] stringArray;
                String string;
                returnData = new ReturnData();
                String id = null;
                if (context.request().method() == HttpMethod.POST) {
                    string = context.getBodyAsJson().getString("id");
                    Intrinsics.checkNotNullExpressionValue(string, "context.bodyAsJson.getString(\"id\")");
                    id = string;
                } else {
                    stringArray = context.queryParam("id");
                    Intrinsics.checkNotNullExpressionValue(stringArray, "context.queryParam(\"id\")");
                    string = CollectionsKt.firstOrNull(stringArray);
                    id = string == null ? "" : string;
                }
                stringArray = new String[]{"data", "activeLicense"};
                activeLicenseList = ExtKt.asJsonArray(ExtKt.getStorage$default(stringArray, null, 2, null));
                if (activeLicenseList == null) {
                    activeLicenseList = new JsonArray();
                }
                activeLicense = null;
                index = -1;
                int n = 0;
                int n2 = activeLicenseList.size();
                if (n < n2) {
                    do {
                        int i2;
                        if (!((ActiveLicense)(_activeLicense2 = activeLicenseList.getJsonObject(i2 = n++).mapTo(ActiveLicense.class))).getId().equals(id)) continue;
                        activeLicense = _activeLicense2;
                        index = i2;
                        break;
                    } while (n < n2);
                }
                n2 = 0;
                result2 = new LinkedHashMap();
                ip = context.request().getHeader("X-Real-IP");
                if (ip == null) break block13;
                i2 = ip;
                i2 = (CharSequence)i2;
                boolean _activeLicense2 = false;
                if (!(i2.length() == 0)) break block14;
            }
            String string = (i2 = context.request().remoteAddress()) == null ? "" : (ip = (_activeLicense2 = i2.host()) == null ? "" : _activeLicense2);
        }
        if (activeLicense == null) {
            result2.put("isValid", Boxing.boxBoolean(false));
            result2.put("errorMsg", "\u5bc6\u94a5\u672a\u6fc0\u6d3b");
        } else {
            result2.put("isValid", Boxing.boxBoolean(activeLicense.getVerified()));
            result2.put("errorMsg", activeLicense.getErrorMsg());
            if (activeLicense.getLastOnlineTime() != null) {
                long l = System.currentTimeMillis();
                Long l2 = activeLicense.getLastOnlineTime();
                Intrinsics.checkNotNull(l2);
                if (l < l2 + (long)600000 && !ip.equals(activeLicense.getLastOnlineIp())) {
                    i2 = new Pair[]{TuplesKt.to("lastOnlineTime", activeLicense.getLastOnlineTime()), TuplesKt.to("lastOnlineIp", activeLicense.getLastOnlineIp())};
                    result2.put("repeat", MapsKt.mapOf(i2));
                }
            }
            activeLicense.setLastOnlineTime(Boxing.boxLong(System.currentTimeMillis()));
            i2 = ip;
            activeLicense.setLastOnlineIp((String)i2);
            activeLicenseList.set(index, JsonObject.mapFrom(activeLicense));
            i2 = new String[]{"data", "activeLicense"};
            ExtKt.saveStorage$default((String[])i2, activeLicenseList, false, null, 12, null);
        }
        String resultContent = ExtKt.jsonEncode$default(result2, false, 2, null);
        CharSequence _activeLicense2 = this.privateKeyContent;
        boolean bl = false;
        if (_activeLicense2.length() == 0) {
            String[] stringArray = new String[]{"data", "privateKey"};
            _activeLicense2 = ExtKt.getStorage(stringArray, ".key");
            this.privateKeyContent = _activeLicense2 == null ? "" : _activeLicense2;
        }
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(this.privateKeyContent, 2)));
        Intrinsics.checkNotNullExpressionValue(privateKey, "privateKey");
        String resultKey = EncoderUtils.encryptSegmentByPrivateKey$default(EncoderUtils.INSTANCE, resultContent, privateKey, 0, 4, null);
        return ReturnData.setData$default(returnData, MapsKt.mapOf(TuplesKt.to("result", resultKey)), null, 2, null);
    }

    @Nullable
    public final Object checkLicense(@NotNull License license, @NotNull Continuation<? super Unit> $completion) {
        boolean $i$f$CoroutineExceptionHandler = false;
        CoroutineExceptionHandler.Key key = CoroutineExceptionHandler.Key;
        CoroutineExceptionHandler exceptionHandler = new CoroutineExceptionHandler(key){

            /*
             * WARNING - void declaration
             */
            public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
                void ex;
                Throwable throwable = exception;
                CoroutineContext ctx = context;
                boolean bl = false;
                LicenseControllerKt.access$getLogger$p().info("check license error: {}", (Object)ex.getMessage());
            }
        };
        Ref.ObjectRef<String> checkUrl = new Ref.ObjectRef<String>();
        checkUrl.element = Intrinsics.stringPlus("https://r.htmake.com/reader3/isLicenseValid?id=", license.getId());
        Job job = BuildersKt.launch$default(this, new MDCContext(null, 1, null).plus(Dispatchers.getIO()).plus(exceptionHandler), null, new Function2<CoroutineScope, Continuation<? super Unit>, Object>(this, checkUrl, null){
            int label;
            private /* synthetic */ Object L$0;
            final /* synthetic */ LicenseController this$0;
            final /* synthetic */ Ref.ObjectRef<String> $checkUrl;
            {
                this.this$0 = $receiver;
                this.$checkUrl = $checkUrl;
                super(2, $completion);
            }

            /*
             * Unable to fully structure code
             */
            @Nullable
            public final Object invokeSuspend(@NotNull Object var1_1) {
                var23_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0: {
                        ResultKt.throwOnFailure(var1_1);
                        $this$launch = (CoroutineScope)this.L$0;
                        this.L$0 = $this$launch;
                        this.label = 1;
                        v0 = VertxCoroutineKt.awaitResult((Function1)new Function1<Handler<AsyncResult<HttpResponse<Buffer>>>, Unit>(this.this$0, this.$checkUrl){
                            final /* synthetic */ LicenseController this$0;
                            final /* synthetic */ Ref.ObjectRef<String> $checkUrl;
                            {
                                this.this$0 = $receiver;
                                this.$checkUrl = $checkUrl;
                                super(1);
                            }

                            public final void invoke(@NotNull Handler<AsyncResult<HttpResponse<Buffer>>> handler2) {
                                Intrinsics.checkNotNullParameter(handler2, "handler");
                                LicenseController.access$getWebClient(this.this$0).getAbs((String)this.$checkUrl.element).timeout(5000L).send(handler2);
                            }
                        }, this);
                        if (v0 == var23_2) {
                            return var23_2;
                        }
                        ** GOTO lbl16
                    }
                    case 1: {
                        $this$launch = (CoroutineScope)this.L$0;
                        ResultKt.throwOnFailure($result);
                        v0 = $result;
lbl16:
                        // 2 sources

                        result = (HttpResponse)v0;
                        var4_5 = $this$launch;
                        var5_6 = false;
                        try {
                            var6_7 = Result.Companion;
                            var7_10 = var4_5;
                            $i$a$-runCatching-LicenseController$checkLicense$2$1 = false;
                            var9_17 = result;
                            if (var9_17 == null) {
                                v1 = null;
                            } else {
                                var10_19 = var9_17.bodyAsJsonObject();
                                if (var10_19 == null) {
                                    v1 = null;
                                } else {
                                    var11_21 = var10_19;
                                    var12_22 = false;
                                    var13_24 = false;
                                    it = var11_21;
                                    $i$a$-let-LicenseController$checkLicense$2$1$1 = false;
                                    LicenseControllerKt.access$getLogger$p().info("isLicenseValid: {}", (Object)it);
                                    var16_29 = it;
                                    var11_21 = var16_29.getJsonObject("data");
                                    if (var11_21 == null) {
                                        v1 = null;
                                    } else {
                                        var12_23 = var11_21.getString("result");
                                        if (var12_23 == null) {
                                            v1 = null;
                                        } else {
                                            var14_26 = var12_23;
                                            var15_27 = false;
                                            var17_30 = false;
                                            it = var14_26;
                                            $i$a$-let-LicenseController$checkLicense$2$1$2 = false;
                                            var13_25 = new JsonObject(ExtKt.decryptData(it));
                                            var15_28 = var13_25;
                                            var17_30 = false;
                                            var18_32 = false;
                                            it = var15_28;
                                            $i$a$-let-LicenseController$checkLicense$2$1$3 = false;
                                            var21_36 = it.getBoolean("isValid");
                                            isValid = var21_36 == null ? true : var21_36.booleanValue();
                                            ExtKt.setLicenseValid(isValid != false);
                                            if (!isValid) {
                                                LicenseControllerKt.access$getLogger$p().info("\u5bc6\u94a5\u9519\u8bef\uff1a{}", (var21_36 = it.getString("errorMsg")) == null ? "" : var21_36);
                                            }
                                            var14_26 = it.getJsonObject("repeat");
                                            if (var14_26 == null) {
                                                v1 = null;
                                            } else {
                                                var15_28 = var14_26;
                                                var17_30 = false;
                                                var18_32 = false;
                                                it = var15_28;
                                                $i$a$-let-LicenseController$checkLicense$2$1$4 = false;
                                                v2 = LicenseControllerKt.access$getLogger$p();
                                                var21_36 = it.getLong("lastOnlineTime");
                                                Intrinsics.checkNotNullExpressionValue(var21_36, "it.getLong(\"lastOnlineTime\")");
                                                v2.info("\u8bf7\u52ff\u91cd\u590d\u4f7f\u7528\u6388\u6743\uff0c\u4e0a\u6b21\u68c0\u67e5\u65f6\u95f4\uff1a{}\uff0c\u4e0a\u6b21\u68c0\u67e5ip\uff1a{}", (Object)LocalDateTime.ofInstant(Instant.ofEpochMilli(((Number)var21_36).longValue()), ZoneId.systemDefault()), (Object)it.getString("lastOnlineIp"));
                                                v1 = Unit.INSTANCE;
                                            }
                                        }
                                    }
                                }
                            }
                            var7_10 = v1;
                            $i$a$-runCatching-LicenseController$checkLicense$2$1 = false;
                            var6_7 = Result.constructor-impl(var7_10);
                        }
                        catch (Throwable var7_11) {
                            $i$a$-runCatching-LicenseController$checkLicense$2$1 = Result.Companion;
                            var9_18 = false;
                            var6_7 = Result.constructor-impl(ResultKt.createFailure(var7_11));
                        }
                        var4_5 = var6_7;
                        var5_6 = false;
                        var6_8 = false;
                        if (Result.isSuccess-impl(var4_5)) {
                            var7_10 = (Unit)var4_5;
                            $i$a$-onSuccess-LicenseController$checkLicense$2$2 = false;
                        }
                        var5_6 = false;
                        var6_8 = false;
                        v3 = Result.exceptionOrNull-impl(var4_5);
                        if (v3 != null) {
                            var6_9 = v3;
                            var7_12 = false;
                            var8_16 = false;
                            var9_17 = var6_9;
                            var10_20 = false;
                            it = var9_17;
                            $i$a$-onFailure-LicenseController$checkLicense$2$3 = false;
                            LicenseControllerKt.access$getLogger$p().info("check license error: {}", (Object)it.getMessage());
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
        if (job == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return job;
        }
        return Unit.INSTANCE;
    }

    @Nullable
    public final Object sendCodeToEmail(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        int n;
        int n2;
        CharSequence charSequence;
        ReturnData returnData = new ReturnData();
        String email = null;
        if (context.request().method() == HttpMethod.POST) {
            charSequence = context.getBodyAsJson().getString("email");
            email = charSequence == null ? "" : charSequence;
        } else {
            List<String> list2 = context.queryParam("email");
            Intrinsics.checkNotNullExpressionValue(list2, "context.queryParam(\"email\")");
            charSequence = CollectionsKt.firstOrNull(list2);
            email = charSequence == null ? "" : charSequence;
        }
        charSequence = email;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return returnData.setErrorMsg("\u90ae\u7bb1\u9519\u8bef");
        }
        if (!ExtKt.validateEmail(email)) {
            return returnData.setErrorMsg("\u4ec5\u652f\u6301 163|126|qq|yahoo|sina|sohu|yeah|139|189|21cn|outlook|gmail|icloud \u7b49\u90ae\u7bb1");
        }
        String[] stringArray = new String[]{"data", "activeLicense"};
        JsonArray activeLicenseList = ExtKt.asJsonArray(ExtKt.getStorage$default(stringArray, null, 2, null));
        if (activeLicenseList == null) {
            activeLicenseList = new JsonArray();
        }
        if ((n2 = 0) < (n = activeLicenseList.size())) {
            do {
                int i;
                if (!"trial".equals(activeLicenseList.getJsonObject(i = n2++).getString("type")) || !email.equals(activeLicenseList.getJsonObject(i).getString("code"))) continue;
                return returnData.setErrorMsg("\u8be5\u90ae\u7bb1\u5df2\u88ab\u4f7f\u7528");
            } while (n2 < n);
        }
        String verifyCode = this.tryCodeCache.getAsString(email);
        CharSequence charSequence2 = verifyCode;
        boolean bl2 = false;
        int n3 = 0;
        if (!(charSequence2 == null || charSequence2.length() == 0)) {
            return returnData.setData("", "\u60a8\u7684\u9a8c\u8bc1\u7801\u4ecd\u5728\u6709\u6548\u671f\u5185\uff0c\u8bf7\u52ff\u91cd\u590d\u83b7\u53d6");
        }
        String string = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(string, "randomUUID().toString()");
        n3 = 0;
        int n4 = 6;
        boolean bl3 = false;
        String string2 = string.substring(n3, n4);
        Intrinsics.checkNotNullExpressionValue(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        String code = string2;
        this.tryCodeCache.put(email, code, 900);
        ExtKt.sendEmail(email, "Reader Kindle\u7aef\u7684\u8bd5\u7528\u7533\u8bf7\u9a8c\u8bc1", "\u60a8\u6b63\u5728\u7533\u8bf7Reader Kindle\u7aef\u7684\u8bd5\u7528\uff0c\u9a8c\u8bc1\u7801\u662f: " + code + "\uff0c15\u5206\u949f\u5185\u6709\u6548\uff0c\u8bf7\u52ff\u56de\u590d");
        return returnData.setData("", "\u8bf7\u67e5\u6536\u90ae\u4ef6");
    }

    @Nullable
    public final Object supplyLicense(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        String code;
        String email;
        ReturnData returnData;
        block6: {
            block5: {
                returnData = new ReturnData();
                String string = context.getBodyAsJson().getString("email");
                email = string == null ? "" : string;
                CharSequence charSequence = context.getBodyAsJson().getString("code");
                code = charSequence == null ? "" : charSequence;
                charSequence = email;
                boolean bl = false;
                boolean bl2 = false;
                if (charSequence.length() == 0) break block5;
                charSequence = code;
                bl = false;
                bl2 = false;
                if (!(charSequence.length() == 0)) break block6;
            }
            return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
        }
        String verifyCode = this.tryCodeCache.getAsString(email);
        this.tryCodeCache.remove(email);
        if (!code.equals(verifyCode)) {
            return returnData.setErrorMsg("\u9a8c\u8bc1\u7801\u9519\u8bef");
        }
        long l = System.currentTimeMillis() + 604800000L;
        License license = new License("*", 15, 0L, false, l, 1, "trial", null, email, false, null, 1152, null);
        String licenseContent = ExtKt.jsonEncode$default(license, false, 2, null);
        CharSequence charSequence = this.privateKeyContent;
        boolean bl = false;
        if (charSequence.length() == 0) {
            String[] stringArray = new String[]{"data", "privateKey"};
            charSequence = ExtKt.getStorage(stringArray, ".key");
            this.privateKeyContent = charSequence == null ? "" : charSequence;
        }
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(this.privateKeyContent, 2)));
        Intrinsics.checkNotNullExpressionValue(privateKey, "privateKey");
        String licenseKey = EncoderUtils.encryptSegmentByPrivateKey$default(EncoderUtils.INSTANCE, licenseContent, privateKey, 0, 4, null);
        return ReturnData.setData$default(returnData, MapsKt.mapOf(TuplesKt.to("key", licenseKey)), null, 2, null);
    }

    public static final /* synthetic */ WebClient access$getWebClient(LicenseController $this) {
        return $this.getWebClient();
    }
}

