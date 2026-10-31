/*
 * Decompiled with CFR 0.152.
 */
package com.htmake.reader.api.controller;

import com.google.gson.reflect.TypeToken;
import com.htmake.reader.api.controller.BaseController;
import com.htmake.reader.api.controller.BaseControllerKt;
import com.htmake.reader.config.AppConfig;
import com.htmake.reader.entity.User;
import com.htmake.reader.utils.ExtKt;
import com.htmake.reader.utils.SpringContextUtils;
import io.legado.app.utils.FileUtils;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
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
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.env.Environment;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019J\u000e\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\u001a\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001e0\u001c2\u0006\u0010\u001f\u001a\u00020\u001eJ\u0018\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\u001d2\b\b\u0002\u0010\"\u001a\u00020\u001dJ\u0010\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020\u001dJ\u001c\u0010&\u001a\u0010\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001e\u0018\u00010'2\u0006\u0010%\u001a\u00020\u001dJ\u000e\u0010(\u001a\u00020\u001d2\u0006\u0010\u0017\u001a\u00020\u0018J)\u0010)\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u0017\u001a\u00020\u001e2\u0012\u0010*\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d0+\"\u00020\u001d\u00a2\u0006\u0002\u0010,J\u000e\u0010-\u001a\u00020\u001d2\u0006\u0010\u0017\u001a\u00020\u001eJX\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u00102\u0006\u00101\u001a\u00020\u00102\u0006\u00102\u001a\u00020\u00102-\u00103\u001a)\b\u0001\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e05\u0012\u0006\u0012\u0004\u0018\u00010\u001e04\u00a2\u0006\u0002\b6H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00107J\u0082\u0001\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u00102\u0006\u00101\u001a\u00020\u00102\u0006\u00102\u001a\u00020\u00102-\u00103\u001a)\b\u0001\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e05\u0012\u0006\u0012\u0004\u0018\u00010\u001e04\u00a2\u0006\u0002\b62(\u00108\u001a$\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u001e0:j\b\u0012\u0004\u0012\u00020\u001e`;\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u001609H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010<J7\u0010=\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001e0'2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010>\u001a\u00020$2\b\b\u0002\u0010?\u001a\u00020\u0016H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010@J\u001e\u0010A\u001a\u00020/2\u0006\u0010\u0017\u001a\u00020\u001e2\u0006\u0010*\u001a\u00020\u001d2\u0006\u0010B\u001a\u00020\u001eR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006C"}, d2={"Lcom/htmake/reader/api/controller/BaseController;", "Lkotlinx/coroutines/CoroutineScope;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)V", "appConfig", "Lcom/htmake/reader/config/AppConfig;", "getAppConfig", "()Lcom/htmake/reader/config/AppConfig;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "env", "Lorg/springframework/core/env/Environment;", "getEnv", "()Lorg/springframework/core/env/Environment;", "loginExpireDays", "", "userMutex", "Lkotlinx/coroutines/sync/Mutex;", "getUserMutex", "()Lkotlinx/coroutines/sync/Mutex;", "checkAuth", "", "context", "Lio/vertx/ext/web/RoutingContext;", "(Lio/vertx/ext/web/RoutingContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkManagerAuth", "formatUser", "", "", "", "userInfo", "getFileExt", "url", "defaultExt", "getUserInfoClass", "Lcom/htmake/reader/entity/User;", "username", "getUserInfoMap", "", "getUserNameSpace", "getUserStorage", "path", "", "(Ljava/lang/Object;[Ljava/lang/String;)Ljava/lang/String;", "getUserWebdavHome", "limitConcurrent", "", "concurrentCount", "startIndex", "endIndex", "handler", "Lkotlin/Function3;", "Lkotlin/coroutines/Continuation;", "Lkotlin/ExtensionFunctionType;", "(IIILkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "needContinue", "Lkotlin/Function2;", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "(IIILkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveUserSession", "user", "regenerateToken", "(Lio/vertx/ext/web/RoutingContext;Lcom/htmake/reader/entity/User;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveUserStorage", "value", "reader-pro"})
public class BaseController
implements CoroutineScope {
    @NotNull
    private final CoroutineContext coroutineContext;
    private int loginExpireDays;
    @NotNull
    private final AppConfig appConfig;
    @NotNull
    private final Environment env;
    @NotNull
    private final Mutex userMutex;

    public BaseController(@NotNull CoroutineContext coroutineContext2) {
        Intrinsics.checkNotNullParameter(coroutineContext2, "coroutineContext");
        this.coroutineContext = coroutineContext2;
        this.loginExpireDays = 7;
        Object object = SpringContextUtils.getBean("appConfig", AppConfig.class);
        Intrinsics.checkNotNullExpressionValue(object, "getBean(\"appConfig\", AppConfig::class.java)");
        this.appConfig = object;
        object = SpringContextUtils.getBean(Environment.class);
        Intrinsics.checkNotNullExpressionValue(object, "getBean(Environment::class.java)");
        this.env = (Environment)object;
        this.userMutex = MutexKt.Mutex$default(false, 1, null);
    }

    @Override
    @NotNull
    public CoroutineContext getCoroutineContext() {
        return this.coroutineContext;
    }

    @NotNull
    public final AppConfig getAppConfig() {
        return this.appConfig;
    }

    @NotNull
    public final Environment getEnv() {
        return this.env;
    }

    @NotNull
    public final Mutex getUserMutex() {
        return this.userMutex;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object saveUserSession(@NotNull RoutingContext var1_1, @NotNull User var2_2, boolean var3_3, @NotNull Continuation<? super Map<String, ? extends Object>> var4_4) {
        if (!(var4_4 instanceof saveUserSession.1)) ** GOTO lbl-1000
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
                boolean Z$0;
                /* synthetic */ Object result;
                final /* synthetic */ BaseController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.saveUserSession(null, null, false, this);
                }
            };
        }
        $result = $continuation.result;
        var14_7 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = user;
                $continuation.Z$0 = regenerateToken;
                $continuation.label = 1;
                v0 = Mutex.DefaultImpls.lock$default(this.getUserMutex(), null, $continuation, 1, null);
                ** if (v0 != var14_7) goto lbl22
lbl21:
                // 1 sources

                return var14_7;
lbl22:
                // 1 sources

                ** GOTO lbl32
            }
            case 1: {
                regenerateToken = $continuation.Z$0;
                user = (User)$continuation.L$2;
                context = (RoutingContext)$continuation.L$1;
                this = (BaseController)$continuation.L$0;
                try {
                    ResultKt.throwOnFailure($result);
                    v0 = $result;
lbl32:
                    // 2 sources

                    user.setLast_login_at(System.currentTimeMillis());
                    if (regenerateToken) {
                        user.setToken(ExtKt.genEncryptedPassword(user.getUsername(), String.valueOf(System.currentTimeMillis())));
                        tokenMap = null;
                        expire = System.currentTimeMillis() + (long)(this.loginExpireDays * 86400 * 1000);
                        if (user.getToken_map() != null) {
                            var8_13 = user.getToken_map();
                            v1 /* !! */  = tokenMap = TypeIntrinsics.isMutableMap(var8_13) != false ? var8_13 : null;
                        }
                        if (tokenMap == null) {
                            var8_13 = new Pair[]{TuplesKt.to(user.getToken(), Boxing.boxLong(expire))};
                            tokenMap = MapsKt.mutableMapOf(var8_13);
                        } else {
                            var8_13 = tokenMap;
                            var9_14 = user.getToken();
                            var10_15 = Boxing.boxLong(expire);
                            var11_17 = false;
                            var8_13.put(var9_14, var10_15);
                        }
                        CollectionsKt.removeAll((Iterable)tokenMap.values(), (Function1)new Function1<Long, Boolean>(user){
                            final /* synthetic */ User $user;
                            {
                                this.$user = $user;
                                super(1);
                            }

                            public final boolean invoke(long it) {
                                return it < this.$user.getLast_login_at();
                            }
                        });
                        user.setToken_map((Map<String, Long>)tokenMap);
                    }
                    expire = false;
                    userMap = new LinkedHashMap<K, V>();
                    var7_18 = new String[]{"data", "users"};
                    userMapJson = ExtKt.asJsonObject(ExtKt.getStorage$default((String[])var7_18, null, 2, null));
                    if (userMapJson != null) {
                        var7_18 = userMapJson.getMap();
                        if (var7_18 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.String, kotlin.collections.Map<kotlin.String, kotlin.Any>>");
                        }
                        userMap = TypeIntrinsics.asMutableMap(var7_18);
                    }
                    var7_18 = userMap;
                    var8_13 = user.getUsername();
                    var9_14 = ExtKt.toMap(user);
                    var10_16 = false;
                    var7_18.put(var8_13, var9_14);
                    var7_18 = new String[]{"data", "users"};
                    v2 = var7_18;
                    var7_18 = Json.encode(userMap);
                    Intrinsics.checkNotNullExpressionValue(var7_18, "encode(userMap)");
                    ExtKt.saveStorage$default((String[])v2, var7_18, false, null, 12, null);
                    loginData = this.formatUser(user);
                    context.session().put("username", user.getUsername());
                    context.put("username", user.getUsername());
                    var8_13 = loginData;
                    return var8_13;
                }
                catch (Throwable var5_9) {
                    throw var5_9;
                }
                finally {
                    Mutex.DefaultImpls.unlock$default(this.getUserMutex(), null, 1, null);
                }
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public static /* synthetic */ Object saveUserSession$default(BaseController baseController, RoutingContext routingContext, User user, boolean bl, Continuation continuation, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: saveUserSession");
        }
        if ((n & 4) != 0) {
            bl = true;
        }
        return baseController.saveUserSession(routingContext, user, bl, continuation);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object checkAuth(@NotNull RoutingContext var1_1, @NotNull Continuation<? super Boolean> var2_2) {
        if (!(var2_2 instanceof checkAuth.1)) ** GOTO lbl-1000
        var19_3 = var2_2;
        if ((var19_3.label & -2147483648) != 0) {
            var19_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl(this, var2_2){
                Object L$0;
                Object L$1;
                int I$0;
                /* synthetic */ Object result;
                final /* synthetic */ BaseController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.checkAuth(null, this);
                }
            };
        }
        $result = $continuation.result;
        var20_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                if (!this.getAppConfig().getSecure()) {
                    return Boxing.boxBoolean(true);
                }
                var4_6 = (String)context.session().get("username");
                username = var4_6 == null ? "" : var4_6;
                userInfo = this.getUserInfoClass(username);
                if (userInfo != null) {
                    context.put("username", userInfo.getUsername());
                    context.put("userInfo", userInfo);
                    return Boxing.boxBoolean(true);
                }
                var7_8 = context.queryParam("accessToken");
                Intrinsics.checkNotNullExpressionValue(var7_8, "context.queryParam(\"accessToken\")");
                var6_11 = CollectionsKt.firstOrNull(var7_8);
                accessToken = var6_11 == null ? "" : var6_11;
                var6_11 = accessToken;
                var7_9 = false;
                if (!(var6_11.length() > 0)) break;
                var7_9 = false;
                userMap = new LinkedHashMap<K, V>();
                var8_13 = new String[]{"data", "users"};
                userMapJson = ExtKt.asJsonObject(ExtKt.getStorage$default((String[])var8_13, null, 2, null));
                if (userMapJson != null) {
                    var9_14 = userMapJson.getMap();
                    v0 = var8_13 = TypeIntrinsics.isMutableMap(var9_14) != false ? var9_14 : null;
                    if (var8_13 == null) {
                        var9_15 = false;
                        v1 /* !! */  = new LinkedHashMap<K, V>();
                    } else {
                        v1 /* !! */  = userMap = var8_13;
                    }
                }
                if ((tmp = StringsKt.split$default((CharSequence)accessToken, var9_16 = new String[]{":"}, false, 2, 2, null)).size() < 2) break;
                _username = (String)tmp.get(0);
                token = (String)tmp.get(1);
                var13_18 = userMap;
                var14_21 = null;
                var15_25 = false;
                var12_28 = var13_18.getOrDefault(_username, var14_21);
                if (var12_28 == null) {
                    v2 = null;
                } else {
                    $this$toDataClass$iv = var12_28;
                    $i$f$toDataClass = false;
                    $this$convert$iv$iv = $this$toDataClass$iv;
                    $i$f$convert = false;
                    json$iv$iv = $this$convert$iv$iv instanceof String != false ? (String)$this$convert$iv$iv : ExtKt.getGson().toJson($this$convert$iv$iv);
                    v2 = existedUser = (User)ExtKt.getGson().fromJson(json$iv$iv, new TypeToken<User>(){}.getType());
                }
                if (existedUser == null) break;
                var12_28 = token;
                $this$toDataClass$iv = false;
                if (!(var12_28.length() > 0)) break;
                isLogin = 0;
                $this$toDataClass$iv = existedUser.getToken();
                var14_23 = false;
                if ($this$toDataClass$iv.length() > 0 && Intrinsics.areEqual(existedUser.getToken(), token)) {
                    isLogin = 1;
                }
                if (isLogin == 0 && existedUser.getToken_map() != null) {
                    var14_24 = existedUser.getToken_map();
                    v3 = tokenMap = TypeIntrinsics.isMutableMap(var14_24) != false ? var14_24 : null;
                    if (tokenMap != null && tokenMap.containsKey(token)) {
                        if (((Number)tokenMap.getOrDefault(token, Boxing.boxLong(0L))).longValue() > System.currentTimeMillis()) {
                            isLogin = 1;
                            var14_24 = tokenMap;
                            var15_27 = Boxing.boxLong(System.currentTimeMillis() + (long)(this.loginExpireDays * 86400 * 1000));
                            var16_30 = false;
                            var14_24.put(token, var15_27);
                        } else {
                            tokenMap.remove(token);
                        }
                        existedUser.setToken_map(tokenMap);
                    }
                }
                if (isLogin == 0) ** GOTO lbl102
                $continuation.L$0 = context;
                $continuation.L$1 = existedUser;
                $continuation.I$0 = isLogin;
                $continuation.label = 1;
                v4 = this.saveUserSession((RoutingContext)context, existedUser, false, $continuation);
                if (v4 == var20_5) {
                    return var20_5;
                }
                ** GOTO lbl98
            }
            case 1: {
                var12_29 = $continuation.I$0;
                var11_32 = (User)$continuation.L$1;
                var1_1 = (RoutingContext)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v4 = $result;
lbl98:
                // 2 sources

                var1_1.put("username", var11_32.getUsername());
                var1_1.put("userInfo", var11_32);
lbl102:
                // 2 sources

                return Boxing.boxBoolean(var12_29 != 0);
            }
        }
        return Boxing.boxBoolean(false);
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    public final boolean checkManagerAuth(@NotNull RoutingContext context) {
        String secureKey;
        Intrinsics.checkNotNullParameter(context, "context");
        if (!this.appConfig.getSecure()) {
            return true;
        }
        CharSequence charSequence = this.appConfig.getSecureKey();
        boolean bl = false;
        if (charSequence.length() == 0) {
            return true;
        }
        Object object = context.queryParam("secureKey");
        Intrinsics.checkNotNullExpressionValue(object, "context.queryParam(\"secureKey\")");
        String string = CollectionsKt.firstOrNull(object);
        String string2 = secureKey = string == null ? "" : string;
        if (Intrinsics.areEqual(secureKey, this.appConfig.getSecureKey())) {
            object = context.queryParam("userNS");
            Intrinsics.checkNotNullExpressionValue(object, "context.queryParam(\"userNS\")");
            String userNS = CollectionsKt.firstOrNull(object);
            object = userNS;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!(object == null || object.length() == 0)) {
                context.put("userNameSpace", userNS);
            } else {
                context.remove("userNameSpace");
            }
            return true;
        }
        return false;
    }

    @NotNull
    public final String getUserNameSpace(@NotNull RoutingContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (!this.appConfig.getSecure()) {
            return "default";
        }
        this.checkManagerAuth(context);
        String userNS = (String)context.get("userNameSpace");
        CharSequence charSequence = userNS;
        boolean bl = false;
        boolean bl2 = false;
        if (!(charSequence == null || charSequence.length() == 0)) {
            return userNS;
        }
        String username = (String)context.get("username");
        if (username != null) {
            return username;
        }
        return "default";
    }

    @Nullable
    public final String getUserStorage(@NotNull Object context, String ... path) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(path, "path");
        String userNameSpace = "";
        Object object = context;
        if (object instanceof RoutingContext) {
            userNameSpace = this.getUserNameSpace((RoutingContext)context);
        } else if (object instanceof String) {
            userNameSpace = (String)context;
        }
        object = userNameSpace;
        boolean bl = false;
        if (object.length() == 0) {
            object = new SpreadBuilder(2);
            ((SpreadBuilder)object).add("data");
            ((SpreadBuilder)object).addSpread(path);
            return ExtKt.getStorage$default((String[])((SpreadBuilder)object).toArray(new String[((SpreadBuilder)object).size()]), null, 2, null);
        }
        object = new SpreadBuilder(3);
        ((SpreadBuilder)object).add("data");
        ((SpreadBuilder)object).add(userNameSpace);
        ((SpreadBuilder)object).addSpread(path);
        return ExtKt.getStorage$default((String[])((SpreadBuilder)object).toArray(new String[((SpreadBuilder)object).size()]), null, 2, null);
    }

    public final void saveUserStorage(@NotNull Object context, @NotNull String path, @NotNull Object value) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(value, "value");
        String userNameSpace = "";
        String[] stringArray = context;
        if (stringArray instanceof RoutingContext) {
            userNameSpace = this.getUserNameSpace((RoutingContext)context);
        } else if (stringArray instanceof String) {
            userNameSpace = (String)context;
        }
        stringArray = userNameSpace;
        boolean bl = false;
        if (stringArray.length() == 0) {
            stringArray = new String[]{"data", path};
            ExtKt.saveStorage$default(stringArray, value, false, null, 12, null);
            return;
        }
        stringArray = new String[]{"data", userNameSpace, path};
        ExtKt.saveStorage$default(stringArray, value, false, null, 12, null);
    }

    @Nullable
    public final User getUserInfoClass(@NotNull String username) {
        User user;
        Intrinsics.checkNotNullParameter(username, "username");
        Map<String, Object> map = this.getUserInfoMap(username);
        if (map == null) {
            user = null;
        } else {
            Map<String, Object> $this$toDataClass$iv = map;
            boolean $i$f$toDataClass = false;
            Map<String, Object> $this$convert$iv$iv = $this$toDataClass$iv;
            boolean $i$f$convert = false;
            String json$iv$iv = $this$convert$iv$iv instanceof String ? (String)((Object)$this$convert$iv$iv) : ExtKt.getGson().toJson($this$convert$iv$iv);
            user = (User)ExtKt.getGson().fromJson(json$iv$iv, new TypeToken<User>(){}.getType());
        }
        User user2 = user;
        return user2;
    }

    @Nullable
    public final Map<String, Object> getUserInfoMap(@NotNull String username) {
        Intrinsics.checkNotNullParameter(username, "username");
        CharSequence charSequence = username;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return null;
        }
        bl = false;
        Map userMap = new LinkedHashMap();
        Object object = new String[]{"data", "users"};
        JsonObject userMapJson = ExtKt.asJsonObject(ExtKt.getStorage$default((String[])object, null, 2, null));
        if (userMapJson != null) {
            object = userMapJson.getMap();
            if (object == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.String, kotlin.collections.Map<kotlin.String, kotlin.Any>>");
            }
            userMap = TypeIntrinsics.asMutableMap(object);
        }
        object = userMap;
        Object v = null;
        boolean bl2 = false;
        Object object2 = object;
        if (object2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
        }
        return object2.getOrDefault(username, v);
    }

    @NotNull
    public final Map<String, Object> formatUser(@NotNull Object userInfo) {
        Intrinsics.checkNotNullParameter(userInfo, "userInfo");
        User user = null;
        if (!(userInfo instanceof User)) {
            Map userMap;
            Map map = userMap = userInfo instanceof Map ? (Map)userInfo : null;
            if (userMap != null) {
                Map $this$toDataClass$iv = userMap;
                boolean $i$f$toDataClass = false;
                Map $this$convert$iv$iv = $this$toDataClass$iv;
                boolean $i$f$convert = false;
                String json$iv$iv = $this$convert$iv$iv instanceof String ? (String)((Object)$this$convert$iv$iv) : ExtKt.getGson().toJson($this$convert$iv$iv);
                user = (User)ExtKt.getGson().fromJson(json$iv$iv, new TypeToken<User>(){}.getType());
            }
        } else {
            user = (User)userInfo;
        }
        if (user == null) {
            boolean bl = false;
            return new LinkedHashMap();
        }
        Pair[] pairArray = new Pair[]{TuplesKt.to("username", user.getUsername()), TuplesKt.to("lastLoginAt", user.getLast_login_at()), TuplesKt.to("accessToken", user.getUsername() + ':' + user.getToken()), TuplesKt.to("enableWebdav", user.getEnable_webdav()), TuplesKt.to("enableLocalStore", user.getEnable_local_store()), TuplesKt.to("enableBookSource", user.getEnable_book_source()), TuplesKt.to("enableRssSource", user.getEnable_rss_source()), TuplesKt.to("bookSourceLimit", user.getBook_source_limit()), TuplesKt.to("bookLimit", user.getBook_limit()), TuplesKt.to("createdAt", user.getCreated_at())};
        return MapsKt.mutableMapOf(pairArray);
    }

    @NotNull
    public final String getUserWebdavHome(@NotNull Object context) {
        File file;
        Intrinsics.checkNotNullParameter(context, "context");
        String[] stringArray = new String[]{"storage", "data"};
        String prefix = ExtKt.getWorkDir(stringArray);
        String userNameSpace = "";
        Object object = context;
        if (object instanceof RoutingContext) {
            userNameSpace = this.getUserNameSpace((RoutingContext)context);
        } else if (object instanceof String) {
            userNameSpace = (String)context;
        }
        object = userNameSpace;
        boolean bl = false;
        if (object.length() > 0) {
            prefix = prefix + File.separator + userNameSpace;
        }
        if (!(file = new File(prefix = prefix + File.separator + "webdav")).exists()) {
            file.mkdirs();
        }
        return prefix;
    }

    @NotNull
    public final String getFileExt(@NotNull String url2, @NotNull String defaultExt) {
        Intrinsics.checkNotNullParameter(url2, "url");
        Intrinsics.checkNotNullParameter(defaultExt, "defaultExt");
        return FileUtils.INSTANCE.getFileExtetion(url2, defaultExt);
    }

    public static /* synthetic */ String getFileExt$default(BaseController baseController, String string, String string2, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getFileExt");
        }
        if ((n & 2) != 0) {
            string2 = "";
        }
        return baseController.getFileExt(string, string2);
    }

    @Nullable
    public final Object limitConcurrent(int concurrentCount, int startIndex, int endIndex, @NotNull Function3<? super CoroutineScope, ? super Integer, ? super Continuation<Object>, ? extends Object> handler2, @NotNull Continuation<? super Unit> $completion) {
        Object object = this.limitConcurrent(concurrentCount, startIndex, endIndex, handler2, limitConcurrent.2.INSTANCE, $completion);
        if (object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return object;
        }
        return Unit.INSTANCE;
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object limitConcurrent(int var1_1, int var2_2, int var3_3, @NotNull Function3<? super CoroutineScope, ? super Integer, ? super Continuation<Object>, ? extends Object> var4_4, @NotNull Function2<? super ArrayList<Object>, ? super Integer, Boolean> var5_5, @NotNull Continuation<? super Unit> var6_6) {
        block26: {
            block25: {
                if (!(var6_6 instanceof limitConcurrent.3)) ** GOTO lbl-1000
                var26_7 = var6_6;
                if ((var26_7.label & -2147483648) != 0) {
                    var26_7.label -= -2147483648;
                } else lbl-1000:
                // 2 sources

                {
                    $continuation = new ContinuationImpl(this, var6_6){
                        Object L$0;
                        Object L$1;
                        Object L$2;
                        Object L$3;
                        Object L$4;
                        int I$0;
                        int I$1;
                        int I$2;
                        int I$3;
                        int I$4;
                        int I$5;
                        long J$0;
                        /* synthetic */ Object result;
                        final /* synthetic */ BaseController this$0;
                        int label;
                        {
                            this.this$0 = this$0;
                            super($completion);
                        }

                        @Nullable
                        public final Object invokeSuspend(@NotNull Object $result) {
                            this.result = $result;
                            this.label |= Integer.MIN_VALUE;
                            return this.this$0.limitConcurrent(0, 0, 0, null, null, this);
                        }
                    };
                }
                $result = $continuation.result;
                var27_9 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                block3 : switch ($continuation.label) {
                    case 0: {
                        ResultKt.throwOnFailure($result);
                        lastIndex = startIndex;
                        loopCount = 0;
                        resultCount = 0;
                        loopStart = System.currentTimeMillis();
                        var12_14 = 0L;
                        var15_15 = 0;
                        deferredList = new ArrayList<Deferred>();
lbl20:
                        // 2 sources

                        while (true) {
                            coroutineCount = deferredList.size();
                            if (coroutineCount < concurrentCount && (var16_18 = lastIndex) < endIndex) {
                                do {
                                    i = var16_18++;
                                    deferredList.add(BuildersKt.async$default(this, null, null, new Function2<CoroutineScope, Continuation<? super Object>, Object>((Function3<? super CoroutineScope, ? super Integer, ? super Continuation<Object>, ? extends Object>)handler, i, null){
                                        int label;
                                        private /* synthetic */ Object L$0;
                                        final /* synthetic */ Function3<CoroutineScope, Integer, Continuation<Object>, Object> $handler;
                                        final /* synthetic */ int $i;
                                        {
                                            this.$handler = $handler;
                                            this.$i = $i;
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
                                                    CoroutineScope $this$async = (CoroutineScope)this.L$0;
                                                    this.label = 1;
                                                    Object object3 = this.$handler.invoke($this$async, Boxing.boxInt(this.$i), this);
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
                                            Function2<CoroutineScope, Continuation<? super Object>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                                            function2.L$0 = value;
                                            return (Continuation)((Object)function2);
                                        }

                                        @Nullable
                                        public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<Object> p2) {
                                            return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                                        }
                                    }, 3, null));
                                    lastIndex = i;
                                } while (++coroutineCount < concurrentCount && var16_18 < endIndex);
                            }
                            i = 0;
                            resultList = new ArrayList<Object>();
lbl31:
                            // 2 sources

                            while (resultList.size() <= 0) {
                                $continuation.L$0 = this;
                                $continuation.L$1 = handler;
                                $continuation.L$2 = needContinue;
                                $continuation.L$3 = deferredList;
                                $continuation.L$4 = resultList;
                                $continuation.I$0 = concurrentCount;
                                $continuation.I$1 = endIndex;
                                $continuation.I$2 = lastIndex;
                                $continuation.I$3 = loopCount;
                                $continuation.I$4 = resultCount;
                                $continuation.J$0 = loopStart;
                                $continuation.I$5 = coroutineCount;
                                $continuation.label = 1;
                                v0 = DelayKt.delay(10L, $continuation);
                                if (v0 == var27_9) {
                                    return var27_9;
                                }
                                ** GOTO lbl65
                            }
                            break block3;
                            break;
                        }
                    }
                    case 1: {
                        var15_15 = $continuation.I$5;
                        var10_13 = $continuation.J$0;
                        var9_12 = $continuation.I$4;
                        var8_11 = $continuation.I$3;
                        var7_10 = $continuation.I$2;
                        var3_3 = $continuation.I$1;
                        var1_1 = $continuation.I$0;
                        resultList = (ArrayList<Object>)$continuation.L$4;
                        var14_16 = (ArrayList)$continuation.L$3;
                        var5_5 = (Function2)$continuation.L$2;
                        var4_4 = (Function3)$continuation.L$1;
                        this = (BaseController)$continuation.L$0;
                        ResultKt.throwOnFailure($result);
                        v0 = $result;
lbl65:
                        // 2 sources

                        var18_23 = 0;
                        stillDeferredList = new ArrayList<Deferred>();
                        var18_23 = 0;
                        var19_24 = var14_16.size();
                        if (var18_23 < var19_24) {
                            do {
                                i = var18_23++;
                                try {
                                    var22_29 = var14_16.get(i);
                                    Intrinsics.checkNotNullExpressionValue(var22_29, "deferredList[i]");
                                    deferred = (Deferred)var22_29;
                                    if (deferred.isCompleted()) {
                                        var22_30 = var9_12;
                                        var9_12 = var22_30 + 1;
                                        resultList.add(deferred.getCompleted());
                                        continue;
                                    }
                                    if (!deferred.isCancelled()) {
                                        stillDeferredList.add(deferred);
                                        continue;
                                    }
                                    var22_31 = var9_12;
                                    var9_12 = var22_31 + 1;
                                }
                                catch (Exception var21_28) {
                                    // empty catch block
                                }
                            } while (var18_23 < var19_24);
                        }
                        var14_16.clear();
                        var14_16.addAll((Collection)stillDeferredList);
                        ** GOTO lbl31
                    }
                }
                if (var9_12 / var1_1 > var8_11) {
                    var8_11 = var9_12 / var1_1;
                    costTime = System.currentTimeMillis() - var10_13;
                    var17_21 = new Object[]{Boxing.boxInt(var8_11), Boxing.boxInt(var15_15), Boxing.boxInt(var7_10), Boxing.boxInt(var3_3), Boxing.boxLong(costTime), Boxing.boxInt(var14_16.size())};
                    BaseControllerKt.access$getLogger$p().info("Loop: {} concurrentCount: {} lastIndex: {} endIndex: {} costTime: {} ms deferredList size: {}", var17_21);
                }
                if (var7_10 < var3_3 - 1) break block25;
                var17_22 = 0;
                var18_23 = var14_16.size();
                if (var17_22 >= var18_23) ** GOTO lbl143
                while (true) {
                    i = var17_22++;
                    var23_32 = resultList;
                    $continuation.L$0 = var5_5;
                    $continuation.L$1 = var14_16;
                    $continuation.L$2 = resultList;
                    $continuation.L$3 = var23_32;
                    $continuation.L$4 = null;
                    $continuation.I$0 = var8_11;
                    $continuation.I$1 = var17_22;
                    $continuation.I$2 = var18_23;
                    $continuation.label = 2;
                    v1 = ((Deferred)var14_16.get(i)).await($continuation);
                    ** if (v1 != var27_9) goto lbl123
lbl122:
                    // 1 sources

                    return var27_9;
lbl123:
                    // 1 sources

                    ** GOTO lbl136
                    break;
                }
                {
                    case 2: {
                        var18_23 = $continuation.I$2;
                        var17_22 = $continuation.I$1;
                        var8_11 = $continuation.I$0;
                        var23_32 = (ArrayList<Object>)$continuation.L$3;
                        var16_17 = (ArrayList)$continuation.L$2;
                        var14_16 = (ArrayList)$continuation.L$1;
                        var5_5 = (Function2)$continuation.L$0;
                        try {
                            ResultKt.throwOnFailure($result);
                            v1 = $result;
lbl136:
                            // 2 sources

                            var24_33 = v1;
                            var23_32.add(var24_33);
                        }
                        catch (Exception var20_26) {
                            // empty catch block
                        }
                        if (var17_22 < var18_23) ** continue;
lbl143:
                        // 2 sources

                        var14_16.clear();
                        var5_5.invoke(var16_17, Boxing.boxInt(var8_11));
                        break block26;
                    }
                }
            }
            if (var16_17.size() <= 0 || ((Boolean)var5_5.invoke(var16_17, Boxing.boxInt(var8_11))).booleanValue()) {
                ++var7_10;
                ** continue;
            }
        }
        return Unit.INSTANCE;
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}

