/*
 * Decompiled with CFR 0.152.
 */
package com.htmake.reader.utils;

import com.htmake.reader.utils.ExtKt;
import com.htmake.reader.utils.RemoteWebview;
import io.legado.app.help.http.CookieStore;
import io.legado.app.help.http.HttpHelperKt;
import io.legado.app.help.http.OkHttpUtilsKt;
import io.legado.app.help.http.StrResponse;
import io.legado.app.model.DebugLog;
import io.legado.app.utils.NetworkUtils;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u00a9\u0001\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00042\u0016\b\u0002\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u0014\u001a\u00020\u00152\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u0017\u001a\u00020\u00042\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001aJ\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001e"}, d2={"Lcom/htmake/reader/utils/RemoteWebview;", "", "()V", "remoteWebviewApi", "", "getRemoteWebviewApi", "()Ljava/lang/String;", "setRemoteWebviewApi", "(Ljava/lang/String;)V", "getStrResponse", "Lio/legado/app/help/http/StrResponse;", "url", "html", "encode", "tag", "headerMap", "", "sourceRegex", "javaScript", "proxy", "post", "", "body", "userNameSpace", "debugLog", "Lio/legado/app/model/DebugLog;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;Lio/legado/app/model/DebugLog;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setRemoteApi", "", "remoteApi", "reader-pro"})
public final class RemoteWebview {
    @NotNull
    public static final RemoteWebview INSTANCE = new RemoteWebview();
    @NotNull
    private static String remoteWebviewApi = "";

    private RemoteWebview() {
    }

    @NotNull
    public final String getRemoteWebviewApi() {
        return remoteWebviewApi;
    }

    public final void setRemoteWebviewApi(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        remoteWebviewApi = string;
    }

    public final void setRemoteApi(@NotNull String remoteApi) {
        Intrinsics.checkNotNullParameter(remoteApi, "remoteApi");
        remoteWebviewApi = remoteApi;
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getStrResponse(@Nullable String var1_1, @Nullable String var2_2, @Nullable String var3_3, @Nullable String var4_4, @Nullable Map<String, String> var5_5, @Nullable String var6_6, @Nullable String var7_7, @Nullable String var8_8, boolean var9_9, @Nullable String var10_10, @NotNull String var11_11, @Nullable DebugLog var12_12, @NotNull Continuation<? super StrResponse> var13_13) {
        if (!(var13_13 instanceof getStrResponse.1)) ** GOTO lbl-1000
        var27_14 = var13_13;
        if ((var27_14.label & -2147483648) != 0) {
            var27_14.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var13_13){
                Object L$0;
                Object L$1;
                /* synthetic */ Object result;
                final /* synthetic */ RemoteWebview this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.getStrResponse(null, null, null, null, null, null, null, null, false, null, null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var28_16 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                var14_17 = this.getRemoteWebviewApi();
                var15_18 = false;
                var16_20 = false;
                if (var14_17 == null || var14_17.length() == 0) {
                    throw new Exception("\u4e0d\u652f\u6301webview");
                }
                requestBody = new Ref.ObjectRef<String>();
                var15_19 = new Pair[]{TuplesKt.to("url", url), TuplesKt.to("html", html), TuplesKt.to("headers", headerMap), TuplesKt.to("js_source", javaScript), TuplesKt.to("proxy", proxy), TuplesKt.to("http_method", post != false ? "POST" : "GET"), TuplesKt.to("body", body), TuplesKt.to("encode", encode), TuplesKt.to("tag", tag), TuplesKt.to("sourceRegex", sourceRegex)};
                requestBody.element = ExtKt.jsonEncode$default(MapsKt.mapOf(var15_19), false, 2, null);
                remoteApi = new Ref.ObjectRef<String>();
                remoteApi.element = Intrinsics.stringPlus(this.getRemoteWebviewApi(), "/render.html");
                $continuation.L$0 = url;
                $continuation.L$1 = userNameSpace;
                $continuation.label = 1;
                v0 = OkHttpUtilsKt.newCallStrResponse(HttpHelperKt.getProxyClient$default(null, (DebugLog)debugLog, 1, null), 0, (Function1<? super Request.Builder, Unit>)new Function1<Request.Builder, Unit>(remoteApi, requestBody){
                    final /* synthetic */ Ref.ObjectRef<String> $remoteApi;
                    final /* synthetic */ Ref.ObjectRef<String> $requestBody;
                    {
                        this.$remoteApi = $remoteApi;
                        this.$requestBody = $requestBody;
                        super(1);
                    }

                    public final void invoke(@NotNull Request.Builder $this$newCallStrResponse) {
                        Intrinsics.checkNotNullParameter($this$newCallStrResponse, "$this$newCallStrResponse");
                        $this$newCallStrResponse.url((String)this.$remoteApi.element);
                        OkHttpUtilsKt.postJson($this$newCallStrResponse, (String)this.$requestBody.element);
                    }
                }, $continuation);
                if (v0 == var28_16) {
                    return var28_16;
                }
                ** GOTO lbl34
            }
            case 1: {
                var11_11 = (String)$continuation.L$1;
                var1_1 = (String)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl34:
                // 2 sources

                strResponse = (StrResponse)v0;
                if (var1_1 != null) {
                    domain = NetworkUtils.INSTANCE.getSubDomain(var1_1);
                    var18_23 = domain;
                    var19_24 = false;
                    if (var18_23.length() > 0 && (cookieList = strResponse.getRaw().headers("Set-Cookie")).size() > 0) {
                        cookieStore = new CookieStore(var11_11);
                        $this$forEach$iv = cookieList;
                        $i$f$forEach = false;
                        for (T element$iv : $this$forEach$iv) {
                            it = (String)element$iv;
                            $i$a$-forEach-RemoteWebview$getStrResponse$2 = false;
                            cookieStore.replaceCookie(Intrinsics.stringPlus(domain, "_cookieJar"), it);
                        }
                    }
                }
                return new StrResponse((var17_22 = var1_1) == null ? "" : var17_22, strResponse.getBody());
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object getStrResponse$default(RemoteWebview remoteWebview, String string, String string2, String string3, String string4, Map map, String string5, String string6, String string7, boolean bl, String string8, String string9, DebugLog debugLog, Continuation continuation, int n, Object object) {
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
            map = null;
        }
        if ((n & 0x20) != 0) {
            string5 = null;
        }
        if ((n & 0x40) != 0) {
            string6 = null;
        }
        if ((n & 0x80) != 0) {
            string7 = null;
        }
        if ((n & 0x100) != 0) {
            bl = false;
        }
        if ((n & 0x200) != 0) {
            string8 = null;
        }
        if ((n & 0x400) != 0) {
            string9 = "";
        }
        if ((n & 0x800) != 0) {
            debugLog = null;
        }
        return remoteWebview.getStrResponse(string, string2, string3, string4, map, string5, string6, string7, bl, string8, string9, debugLog, continuation);
    }
}

