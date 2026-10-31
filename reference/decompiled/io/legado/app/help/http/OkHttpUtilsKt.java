/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.help.http;

import io.legado.app.help.http.OkHttpUtilsKt;
import io.legado.app.help.http.StrResponse;
import io.legado.app.utils.EncodingDetect;
import io.legado.app.utils.GsonExtensionsKt;
import io.legado.app.utils.Utf8BomUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.JobKt;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=2, xi=48, d1={"\u0000T\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0002\b\u0003\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004\u001a\u0015\u0010\u0006\u001a\u00020\u0007*\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\t\u001a0\u0010\n\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00052\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00042\b\b\u0002\u0010\r\u001a\u00020\u000e\u001a8\u0010\u000f\u001a\u00020\u0010*\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00132\u0017\u0010\u0014\u001a\u0013\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00010\u0015\u00a2\u0006\u0002\b\u0016H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017\u001a8\u0010\u0018\u001a\u00020\u0007*\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00132\u0017\u0010\u0014\u001a\u0013\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00010\u0015\u00a2\u0006\u0002\b\u0016H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017\u001a8\u0010\u0019\u001a\u00020\u0010*\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00132\u0017\u0010\u0014\u001a\u0013\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00010\u0015\u00a2\u0006\u0002\b\u0016H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017\u001a8\u0010\u001a\u001a\u00020\u001b*\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00132\u0017\u0010\u0014\u001a\u0013\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00010\u0015\u00a2\u0006\u0002\b\u0016H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017\u001a(\u0010\u001c\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00042\b\b\u0002\u0010\r\u001a\u00020\u000e\u001a\u0014\u0010\u001e\u001a\u00020\u0001*\u00020\u00022\b\u0010\u001f\u001a\u0004\u0018\u00010\u0005\u001a(\u0010 \u001a\u00020\u0001*\u00020\u00022\b\u0010!\u001a\u0004\u0018\u00010\u00052\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\"0\u0004\u001a\u0016\u0010#\u001a\u00020\u0005*\u00020\u00102\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u0005\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006%"}, d2={"addHeaders", "", "Lokhttp3/Request$Builder;", "headers", "", "", "await", "Lokhttp3/Response;", "Lokhttp3/Call;", "(Lokhttp3/Call;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "get", "url", "queryMap", "encoded", "", "newCall", "Lokhttp3/ResponseBody;", "Lokhttp3/OkHttpClient;", "retry", "", "builder", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lokhttp3/OkHttpClient;ILkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "newCallResponse", "newCallResponseBody", "newCallStrResponse", "Lio/legado/app/help/http/StrResponse;", "postForm", "form", "postJson", "json", "postMultipart", "type", "", "text", "encode", "reader-pro"})
public final class OkHttpUtilsKt {
    @Nullable
    public static final Object newCallResponse(@NotNull OkHttpClient $this$newCallResponse, int retry, @NotNull Function1<? super Request.Builder, Unit> builder, @NotNull Continuation<? super Response> $completion) {
        return BuildersKt.withContext(Dispatchers.getIO(), (Function2)new Function2<CoroutineScope, Continuation<? super Response>, Object>(builder, retry, $this$newCallResponse, null){
            Object L$0;
            int I$0;
            int I$1;
            int label;
            final /* synthetic */ Function1<Request.Builder, Unit> $builder;
            final /* synthetic */ int $retry;
            final /* synthetic */ OkHttpClient $this_newCallResponse;
            {
                this.$builder = $builder;
                this.$retry = $retry;
                this.$this_newCallResponse = $receiver;
                super(2, $completion);
            }

            /*
             * Unable to fully structure code
             */
            @Nullable
            public final Object invokeSuspend(@NotNull Object var1_1) {
                var7_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0: {
                        ResultKt.throwOnFailure(var1_1);
                        var3_4 = requestBuilder = new Request.Builder();
                        var4_5 = this.$builder;
                        var5_7 = false;
                        var6_8 = false;
                        var4_5.invoke(var3_4);
                        response = null;
                        var4_6 = 0;
                        if (var4_6 > this.$retry) ** GOTO lbl33
                        while (true) {
                            i = var4_6++;
                            this.L$0 = requestBuilder;
                            this.I$0 = var4_6;
                            this.I$1 = i;
                            this.label = 1;
                            v0 = OkHttpUtilsKt.await(this.$this_newCallResponse.newCall(requestBuilder.build()), this);
                            if (v0 == var7_2) {
                                return var7_2;
                            }
                            ** GOTO lbl30
                            break;
                        }
                    }
                    case 1: {
                        i = this.I$1;
                        var4_6 = this.I$0;
                        var2_3 = (Request.Builder)this.L$0;
                        ResultKt.throwOnFailure($result);
                        v0 = $result;
lbl30:
                        // 2 sources

                        if ((response = (Response)v0).isSuccessful()) {
                            return response;
                        }
                        if (i != this.$retry) ** continue;
lbl33:
                        // 2 sources

                        v1 = response;
                        Intrinsics.checkNotNull(v1);
                        return v1;
                    }
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            @NotNull
            public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                return (Continuation)((Object)new /* invalid duplicate definition of identical inner class */);
            }

            @Nullable
            public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Response> p2) {
                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
            }
        }, $completion);
    }

    public static /* synthetic */ Object newCallResponse$default(OkHttpClient okHttpClient2, int n, Function1 function1, Continuation continuation, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = 0;
        }
        return OkHttpUtilsKt.newCallResponse(okHttpClient2, n, function1, continuation);
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public static final Object newCallResponseBody(@NotNull OkHttpClient var0, int var1_1, @NotNull Function1<? super Request.Builder, Unit> var2_2, @NotNull Continuation<? super ResponseBody> var3_3) {
        if (!(var3_3 instanceof newCallResponseBody.1)) ** GOTO lbl-1000
        var11_4 = var3_3;
        if ((var11_4.label & -2147483648) != 0) {
            var11_4.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl((Continuation<? super newCallResponseBody.1>)var3_3){
                /* synthetic */ Object result;
                int label;

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return OkHttpUtilsKt.newCallResponseBody(null, 0, null, this);
                }
            };
        }
        $result = $continuation.result;
        var12_6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                $continuation.label = 1;
                v0 = OkHttpUtilsKt.newCallResponse($this$newCallResponseBody, (int)retry, (Function1<? super Request.Builder, Unit>)builder, $continuation);
                if (v0 == var12_6) {
                    return var12_6;
                }
                ** GOTO lbl20
            }
            case 1: {
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl20:
                // 2 sources

                var4_7 = v0;
                var5_8 = false;
                var6_9 = false;
                it = (Response)var4_7;
                $i$a$-let-OkHttpUtilsKt$newCallResponseBody$2 = false;
                var9_12 = it.body();
                if (var9_12 == null) {
                    throw new IOException(it.message());
                }
                return var9_12;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object newCallResponseBody$default(OkHttpClient okHttpClient2, int n, Function1 function1, Continuation continuation, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = 0;
        }
        return OkHttpUtilsKt.newCallResponseBody(okHttpClient2, n, function1, continuation);
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public static final Object newCall(@NotNull OkHttpClient var0, int var1_1, @NotNull Function1<? super Request.Builder, Unit> var2_2, @NotNull Continuation<? super ResponseBody> var3_3) {
        if (!(var3_3 instanceof newCall.1)) ** GOTO lbl-1000
        var9_4 = var3_3;
        if ((var9_4.label & -2147483648) != 0) {
            var9_4.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl((Continuation<? super newCall.1>)var3_3){
                Object L$0;
                Object L$1;
                int I$0;
                int I$1;
                int I$2;
                /* synthetic */ Object result;
                int label;

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return OkHttpUtilsKt.newCall(null, 0, null, this);
                }
            };
        }
        $result = $continuation.result;
        var10_6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                var5_8 = requestBuilder = new Request.Builder();
                var6_9 = 0;
                var7_11 = false;
                builder.invoke(var5_8);
                response = null;
                var6_9 = 0;
                if (var6_9 > retry) ** GOTO lbl45
                while (true) {
                    i = var6_9++;
                    $continuation.L$0 = $this$newCall;
                    $continuation.L$1 = requestBuilder;
                    $continuation.I$0 = retry;
                    $continuation.I$1 = var6_9;
                    $continuation.I$2 = i;
                    $continuation.label = 1;
                    v0 = OkHttpUtilsKt.await($this$newCall.newCall(requestBuilder.build()), $continuation);
                    if (v0 == var10_6) {
                        return var10_6;
                    }
                    ** GOTO lbl40
                    break;
                }
            }
            case 1: {
                i = $continuation.I$2;
                var6_9 = $continuation.I$1;
                var1_1 = $continuation.I$0;
                var4_7 = (Request.Builder)$continuation.L$1;
                var0 = (OkHttpClient)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl40:
                // 2 sources

                if ((response = (Response)v0).isSuccessful()) {
                    v1 = response.body();
                    Intrinsics.checkNotNull(v1);
                    return v1;
                }
                if (i != var1_1) ** continue;
lbl45:
                // 2 sources

                v2 = response;
                Intrinsics.checkNotNull(v2);
                var6_10 = v2.body();
                if (var6_10 == null) {
                    throw new IOException(response.message());
                }
                return var6_10;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object newCall$default(OkHttpClient okHttpClient2, int n, Function1 function1, Continuation continuation, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = 0;
        }
        return OkHttpUtilsKt.newCall(okHttpClient2, n, function1, continuation);
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public static final Object newCallStrResponse(@NotNull OkHttpClient var0, int var1_1, @NotNull Function1<? super Request.Builder, Unit> var2_2, @NotNull Continuation<? super StrResponse> var3_3) {
        if (!(var3_3 instanceof newCallStrResponse.1)) ** GOTO lbl-1000
        var9_4 = var3_3;
        if ((var9_4.label & -2147483648) != 0) {
            var9_4.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl((Continuation<? super newCallStrResponse.1>)var3_3){
                Object L$0;
                Object L$1;
                int I$0;
                int I$1;
                int I$2;
                /* synthetic */ Object result;
                int label;

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return OkHttpUtilsKt.newCallStrResponse(null, 0, null, this);
                }
            };
        }
        $result = $continuation.result;
        var10_6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                var5_8 = requestBuilder = new Request.Builder();
                var6_9 = 0;
                var7_11 = 0;
                builder.invoke(var5_8);
                response = null;
                var6_9 = 0;
                if (var6_9 > retry) ** GOTO lbl46
                while (true) {
                    i = var6_9++;
                    JobKt.ensureActive($continuation.getContext());
                    $continuation.L$0 = $this$newCallStrResponse;
                    $continuation.L$1 = requestBuilder;
                    $continuation.I$0 = retry;
                    $continuation.I$1 = var6_9;
                    $continuation.I$2 = i;
                    $continuation.label = 1;
                    v0 = OkHttpUtilsKt.await($this$newCallStrResponse.newCall(requestBuilder.build()), $continuation);
                    if (v0 == var10_6) {
                        return var10_6;
                    }
                    ** GOTO lbl41
                    break;
                }
            }
            case 1: {
                var7_11 = $continuation.I$2;
                var6_9 = $continuation.I$1;
                var1_1 = $continuation.I$0;
                var4_7 = (Request.Builder)$continuation.L$1;
                var0 = (OkHttpClient)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl41:
                // 2 sources

                if ((response = (Response)v0).isSuccessful()) {
                    v1 = response.body();
                    Intrinsics.checkNotNull(v1);
                    return new StrResponse(response, OkHttpUtilsKt.text$default(v1, null, 1, null));
                }
                if (var7_11 != var1_1) ** continue;
lbl46:
                // 2 sources

                v2 = response;
                Intrinsics.checkNotNull(v2);
                var6_10 = response.body();
                return new StrResponse(v2, var6_10 == null ? response.message() : OkHttpUtilsKt.text$default(var6_10, null, 1, null));
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object newCallStrResponse$default(OkHttpClient okHttpClient2, int n, Function1 function1, Continuation continuation, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = 0;
        }
        return OkHttpUtilsKt.newCallStrResponse(okHttpClient2, n, function1, continuation);
    }

    @Nullable
    public static final Object await(@NotNull Call $this$await, @NotNull Continuation<? super Response> $completion) {
        boolean $i$f$suspendCancellableCoroutine = false;
        Continuation<? super Response> uCont$iv = $completion;
        boolean bl = false;
        CancellableContinuationImpl<? super Response> cancellable$iv = new CancellableContinuationImpl<Response>(IntrinsicsKt.intercepted(uCont$iv), 1);
        cancellable$iv.initCancellability();
        CancellableContinuation block = cancellable$iv;
        boolean bl2 = false;
        block.invokeOnCancellation((Function1<Throwable, Unit>)new Function1<Throwable, Unit>($this$await){
            final /* synthetic */ Call $this_await;
            {
                this.$this_await = $receiver;
                super(1);
            }

            public final void invoke(@Nullable Throwable it) {
                this.$this_await.cancel();
            }
        });
        $this$await.enqueue(new Callback(block){
            final /* synthetic */ CancellableContinuation<Response> $block;
            {
                this.$block = $block;
            }

            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(e, "e");
                Continuation continuation = this.$block;
                boolean bl = false;
                Result.Companion companion = Result.Companion;
                boolean bl2 = false;
                continuation.resumeWith(Result.constructor-impl(ResultKt.createFailure(e)));
            }

            public void onResponse(@NotNull Call call, @NotNull Response response2) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(response2, "response");
                Continuation continuation = this.$block;
                boolean bl = false;
                Result.Companion companion = Result.Companion;
                boolean bl2 = false;
                continuation.resumeWith(Result.constructor-impl(response2));
            }
        });
        Object object = cancellable$iv.getResult();
        if (object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return object;
    }

    @NotNull
    public static final String text(@NotNull ResponseBody $this$text, @Nullable String encode) {
        Charset charset;
        Intrinsics.checkNotNullParameter($this$text, "<this>");
        byte[] responseBytes = Utf8BomUtils.INSTANCE.removeUTF8BOM($this$text.bytes());
        String charsetName = null;
        charsetName = encode;
        Object object = charsetName;
        if (object != null) {
            String string = object;
            boolean bl = false;
            boolean bl2 = false;
            String it = string;
            boolean bl3 = false;
            Charset charset2 = Charset.forName(charsetName);
            Intrinsics.checkNotNullExpressionValue(charset2, "forName(charsetName)");
            boolean bl4 = false;
            return new String(responseBytes, charset2);
        }
        object = $this$text.contentType();
        if (object != null && (charset = MediaType.charset$default((MediaType)object, null, 1, null)) != null) {
            Charset charset3 = charset;
            boolean bl = false;
            boolean bl5 = false;
            Charset it = charset3;
            boolean bl6 = false;
            boolean bl7 = false;
            return new String(responseBytes, it);
        }
        charsetName = EncodingDetect.INSTANCE.getHtmlEncode(responseBytes);
        object = Charset.forName(charsetName);
        Intrinsics.checkNotNullExpressionValue(object, "forName(charsetName)");
        boolean bl = false;
        return new String(responseBytes, (Charset)object);
    }

    public static /* synthetic */ String text$default(ResponseBody responseBody, String string, int n, Object object) {
        if ((n & 1) != 0) {
            string = null;
        }
        return OkHttpUtilsKt.text(responseBody, string);
    }

    public static final void addHeaders(@NotNull Request.Builder $this$addHeaders, @NotNull Map<String, String> headers) {
        Intrinsics.checkNotNullParameter($this$addHeaders, "<this>");
        Intrinsics.checkNotNullParameter(headers, "headers");
        Map<String, String> $this$forEach$iv = headers;
        boolean $i$f$forEach = false;
        Map<String, String> map = $this$forEach$iv;
        boolean bl = false;
        Iterator<Map.Entry<String, String>> iterator2 = map.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<String, String> element$iv;
            Map.Entry<String, String> it = element$iv = iterator2.next();
            boolean bl2 = false;
            $this$addHeaders.addHeader(it.getKey(), it.getValue());
        }
    }

    public static final void get(@NotNull Request.Builder $this$get, @NotNull String url2, @NotNull Map<String, String> queryMap, boolean encoded) {
        Intrinsics.checkNotNullParameter($this$get, "<this>");
        Intrinsics.checkNotNullParameter(url2, "url");
        Intrinsics.checkNotNullParameter(queryMap, "queryMap");
        HttpUrl.Builder httpBuilder = HttpUrl.Companion.get(url2).newBuilder();
        Map<String, String> $this$forEach$iv = queryMap;
        boolean $i$f$forEach = false;
        Map<String, String> map = $this$forEach$iv;
        boolean bl = false;
        Iterator<Map.Entry<String, String>> iterator2 = map.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<String, String> element$iv;
            Map.Entry<String, String> it = element$iv = iterator2.next();
            boolean bl2 = false;
            if (encoded) {
                httpBuilder.addEncodedQueryParameter(it.getKey(), it.getValue());
                continue;
            }
            httpBuilder.addQueryParameter(it.getKey(), it.getValue());
        }
        $this$get.url(httpBuilder.build());
    }

    public static /* synthetic */ void get$default(Request.Builder builder, String string, Map map, boolean bl, int n, Object object) {
        if ((n & 4) != 0) {
            bl = false;
        }
        OkHttpUtilsKt.get(builder, string, map, bl);
    }

    public static final void postForm(@NotNull Request.Builder $this$postForm, @NotNull Map<String, String> form, boolean encoded) {
        Intrinsics.checkNotNullParameter($this$postForm, "<this>");
        Intrinsics.checkNotNullParameter(form, "form");
        FormBody.Builder formBody = new FormBody.Builder(null, 1, null);
        Map<String, String> $this$forEach$iv = form;
        boolean $i$f$forEach = false;
        Map<String, String> map = $this$forEach$iv;
        boolean bl = false;
        Iterator<Map.Entry<String, String>> iterator2 = map.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<String, String> element$iv;
            Map.Entry<String, String> it = element$iv = iterator2.next();
            boolean bl2 = false;
            if (encoded) {
                formBody.addEncoded(it.getKey(), it.getValue());
                continue;
            }
            formBody.add(it.getKey(), it.getValue());
        }
        $this$postForm.post(formBody.build());
    }

    public static /* synthetic */ void postForm$default(Request.Builder builder, Map map, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        OkHttpUtilsKt.postForm(builder, map, bl);
    }

    public static final void postMultipart(@NotNull Request.Builder $this$postMultipart, @Nullable String type, @NotNull Map<String, ? extends Object> form) {
        boolean bl;
        Intrinsics.checkNotNullParameter($this$postMultipart, "<this>");
        Intrinsics.checkNotNullParameter(form, "form");
        MultipartBody.Builder multipartBody = new MultipartBody.Builder(null, 1, null);
        String string = type;
        if (string != null) {
            String string2 = string;
            boolean bl2 = false;
            bl = false;
            String it = string2;
            boolean bl3 = false;
            multipartBody.setType(MediaType.Companion.get(type));
        }
        Map<String, ? extends Object> $this$forEach$iv = form;
        boolean $i$f$forEach = false;
        Map<String, ? extends Object> map = $this$forEach$iv;
        bl = false;
        Iterator<Map.Entry<String, ? extends Object>> iterator2 = map.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<String, ? extends Object> element$iv;
            Map.Entry<String, ? extends Object> it = element$iv = iterator2.next();
            boolean bl4 = false;
            Object value = it.getValue();
            if (value instanceof Map) {
                RequestBody requestBody;
                Map map2 = (Map)value;
                String string3 = "fileName";
                boolean bl5 = false;
                Object v = map2.get(string3);
                if (v == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
                }
                String fileName = (String)v;
                map2 = (Map)value;
                string3 = "file";
                bl5 = false;
                Object file = map2.get(string3);
                Object object = (Map)value;
                String string4 = "contentType";
                boolean bl6 = false;
                Object v2 = object.get(string4);
                string3 = v2 instanceof String ? (String)v2 : null;
                MediaType mediaType = string3 == null ? null : MediaType.Companion.get(string3);
                v2 = file;
                if (v2 instanceof File) {
                    requestBody = RequestBody.Companion.create((File)file, mediaType);
                } else if (v2 instanceof byte[]) {
                    requestBody = RequestBody.Companion.create$default(RequestBody.Companion, (byte[])file, mediaType, 0, 0, 6, null);
                } else if (v2 instanceof String) {
                    requestBody = RequestBody.Companion.create((String)file, mediaType);
                } else {
                    object = GsonExtensionsKt.getGSON().toJson(file);
                    Intrinsics.checkNotNullExpressionValue(object, "GSON.toJson(file)");
                    requestBody = RequestBody.Companion.create((String)object, mediaType);
                }
                RequestBody requestBody2 = requestBody;
                multipartBody.addFormDataPart(it.getKey(), fileName, requestBody2);
                continue;
            }
            multipartBody.addFormDataPart(it.getKey(), it.getValue().toString());
        }
        $this$postMultipart.post(multipartBody.build());
    }

    public static final void postJson(@NotNull Request.Builder $this$postJson, @Nullable String json) {
        Intrinsics.checkNotNullParameter($this$postJson, "<this>");
        String string = json;
        if (string != null) {
            String string2 = string;
            boolean bl = false;
            boolean bl2 = false;
            String it = string2;
            boolean bl3 = false;
            RequestBody requestBody = RequestBody.Companion.create(json, MediaType.Companion.get("application/json; charset=UTF-8"));
            $this$postJson.post(requestBody);
        }
    }
}

