/*
 * Decompiled with CFR 0.152.
 */
package com.htmake.reader.api.controller;

import com.htmake.reader.api.ReturnData;
import com.htmake.reader.api.controller.BaseController;
import com.htmake.reader.api.controller.RssSourceController;
import com.htmake.reader.entity.User;
import com.htmake.reader.utils.ExtKt;
import io.legado.app.data.entities.RssArticle;
import io.legado.app.data.entities.RssSource;
import io.legado.app.model.Debug;
import io.legado.app.model.rss.Rss;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u0019\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u0019\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u0019\u0010\r\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011J\u0019\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u0019\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u0019\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0016"}, d2={"Lcom/htmake/reader/api/controller/RssSourceController;", "Lcom/htmake/reader/api/controller/BaseController;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)V", "canEditRssSource", "", "context", "Lio/vertx/ext/web/RoutingContext;", "(Lio/vertx/ext/web/RoutingContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteRssSource", "Lcom/htmake/reader/api/ReturnData;", "getRssArticles", "getRssContent", "getRssSourceByURL", "Lio/legado/app/data/entities/RssSource;", "url", "", "userNameSpace", "getRssSources", "saveRssSource", "saveRssSources", "reader-pro"})
public final class RssSourceController
extends BaseController {
    public RssSourceController(@NotNull CoroutineContext coroutineContext2) {
        Intrinsics.checkNotNullParameter(coroutineContext2, "coroutineContext");
        super(coroutineContext2);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object getRssSources(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof getRssSources.1)) ** GOTO lbl-1000
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
                final /* synthetic */ RssSourceController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.getRssSources(null, this);
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
                this = (RssSourceController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                userNameSpace = this.getUserNameSpace(context);
                list = ExtKt.asJsonArray(this.getUserStorage(userNameSpace, var6_8 /* !! */  = new String[]{"rssSources"}));
                if (list != null) {
                    var6_8 /* !! */  = list.getList();
                    Intrinsics.checkNotNullExpressionValue(var6_8 /* !! */ , "list.getList()");
                    return ReturnData.setData$default(returnData, var6_8 /* !! */ , null, 2, null);
                }
                var6_9 = false;
                return ReturnData.setData$default(returnData, new ArrayList<E>(), null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @Nullable
    public final Object canEditRssSource(@NotNull RoutingContext context, @NotNull Continuation<? super Boolean> $completion) {
        if (!this.getAppConfig().getSecure()) {
            return Boxing.boxBoolean(true);
        }
        User userInfo = (User)context.get("userInfo");
        if (userInfo == null) {
            return Boxing.boxBoolean(false);
        }
        return Boxing.boxBoolean(userInfo.getEnable_book_source());
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object saveRssSource(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof saveRssSource.1)) ** GOTO lbl-1000
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
                final /* synthetic */ RssSourceController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.saveRssSource(null, this);
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
                this = (RssSourceController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 2;
                v1 = this.canEditRssSource(context, $continuation);
                if (v1 == var16_5) {
                    return var16_5;
                }
                ** GOTO lbl43
            }
            case 2: {
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (RssSourceController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v1 = $result;
lbl43:
                // 2 sources

                if (!((Boolean)v1).booleanValue()) {
                    return var3_6.setErrorMsg("\u6743\u9650\u4e0d\u8db3");
                }
                var5_7 = var1_1.getBodyAsString();
                Intrinsics.checkNotNullExpressionValue(var5_7, "context.bodyAsString");
                var5_7 = RssSource.Companion.fromJson-IoAF18A((String)var5_7);
                var6_8 = false;
                rssSource = (RssSource)(Result.isFailure-impl(var5_7) != false ? null : var5_7);
                if (rssSource == null) {
                    return var3_6.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                var5_7 = rssSource.getSourceUrl();
                var6_8 = false;
                if (var5_7.length() == 0) {
                    return var3_6.setErrorMsg("RSS\u94fe\u63a5\u4e0d\u80fd\u4e3a\u7a7a");
                }
                var5_7 = rssSource.getSourceName();
                var6_8 = false;
                if (var5_7.length() == 0) {
                    return var3_6.setErrorMsg("RSS\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
                }
                userNameSpace = this.getUserNameSpace(var1_1);
                rssSourceList = ExtKt.asJsonArray(this.getUserStorage(userNameSpace, var7_11 = new String[]{"rssSources"}));
                if (rssSourceList == null) {
                    rssSourceList = new JsonArray();
                }
                existIndex = -1;
                var8_13 = 0;
                var9_15 = rssSourceList.size();
                if (var8_13 < var9_15) {
                    do {
                        i = var8_13++;
                        var12_18 = rssSourceList.getJsonObject(i).toString();
                        Intrinsics.checkNotNullExpressionValue(var12_18, "rssSourceList.getJsonObject(i).toString()");
                        var12_18 = RssSource.Companion.fromJson-IoAF18A((String)var12_18);
                        var13_19 = false;
                        _rssSource = (RssSource)(Result.isFailure-impl(var12_18) != false ? null : var12_18);
                        if (_rssSource == null || !_rssSource.getSourceUrl().equals(rssSource.getSourceUrl())) continue;
                        existIndex = i;
                        break;
                    } while (var8_13 < var9_15);
                }
                if (existIndex >= 0) {
                    list = rssSourceList.getList();
                    list.set(existIndex, JsonObject.mapFrom(rssSource));
                    rssSourceList = new JsonArray(list);
                } else {
                    rssSourceList.add(JsonObject.mapFrom(rssSource));
                }
                this.saveUserStorage(userNameSpace, "rssSources", rssSourceList);
                return ReturnData.setData$default(var3_6, "", null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object saveRssSources(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof saveRssSources.1)) ** GOTO lbl-1000
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
                /* synthetic */ Object result;
                final /* synthetic */ RssSourceController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.saveRssSources(null, this);
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
                this = (RssSourceController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 2;
                v1 = this.canEditRssSource(context, $continuation);
                if (v1 == var20_5) {
                    return var20_5;
                }
                ** GOTO lbl43
            }
            case 2: {
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (RssSourceController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v1 = $result;
lbl43:
                // 2 sources

                if (!((Boolean)v1).booleanValue()) {
                    return var3_6.setErrorMsg("\u6743\u9650\u4e0d\u8db3");
                }
                rssSourceJsonArray = var1_1.getBodyAsJsonArray();
                if (rssSourceJsonArray == null) {
                    return var3_6.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                userNameSpace = this.getUserNameSpace(var1_1);
                rssSourceList = ExtKt.asJsonArray(this.getUserStorage(userNameSpace, var7_9 = new String[]{"rssSources"}));
                if (rssSourceList == null) {
                    rssSourceList = new JsonArray();
                }
                if ((var7_10 = 0) < (var8_12 = rssSourceJsonArray.size())) {
                    do {
                        k = var7_10++;
                        var11_15 = rssSourceJsonArray.getJsonObject(k).toString();
                        Intrinsics.checkNotNullExpressionValue(var11_15, "rssSourceJsonArray.getJsonObject(k).toString()");
                        var11_15 = RssSource.Companion.fromJson-IoAF18A((String)var11_15);
                        var12_17 = 0;
                        rssSource = (RssSource)(Result.isFailure-impl(var11_15) != false ? null : var11_15);
                        if (rssSource == null) continue;
                        var11_15 = rssSource.getSourceUrl();
                        var12_17 = 0;
                        if (var11_15.length() == 0) continue;
                        var11_15 = rssSource.getSourceName();
                        var12_17 = 0;
                        if (var11_15.length() == 0) continue;
                        existIndex = -1;
                        var12_17 = 0;
                        var13_19 = rssSourceList.size();
                        if (var12_17 < var13_19) {
                            do {
                                i = var12_17++;
                                var16_22 = rssSourceList.getJsonObject(i).toString();
                                Intrinsics.checkNotNullExpressionValue(var16_22, "rssSourceList.getJsonObject(i).toString()");
                                var16_22 = RssSource.Companion.fromJson-IoAF18A((String)var16_22);
                                var17_23 = false;
                                _rssSource = (RssSource)(Result.isFailure-impl(var16_22) != false ? null : var16_22);
                                if (_rssSource == null || !_rssSource.getSourceUrl().equals(rssSource.getSourceUrl())) continue;
                                existIndex = i;
                                break;
                            } while (var12_17 < var13_19);
                        }
                        if (existIndex >= 0) {
                            list = rssSourceList.getList();
                            list.set(existIndex, JsonObject.mapFrom(rssSource));
                            rssSourceList = new JsonArray(list);
                            continue;
                        }
                        rssSourceList.add(JsonObject.mapFrom(rssSource));
                    } while (var7_10 < var8_12);
                }
                this.saveUserStorage(userNameSpace, "rssSources", rssSourceList);
                return ReturnData.setData$default(var3_6, "", null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object deleteRssSource(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof deleteRssSource.1)) ** GOTO lbl-1000
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
                final /* synthetic */ RssSourceController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.deleteRssSource(null, this);
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
                this = (RssSourceController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                $continuation.L$0 = this;
                $continuation.L$1 = context;
                $continuation.L$2 = returnData;
                $continuation.label = 2;
                v1 = this.canEditRssSource(context, $continuation);
                if (v1 == var16_5) {
                    return var16_5;
                }
                ** GOTO lbl43
            }
            case 2: {
                var3_6 = (ReturnData)$continuation.L$2;
                var1_1 = (RoutingContext)$continuation.L$1;
                this = (RssSourceController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v1 = $result;
lbl43:
                // 2 sources

                if (!((Boolean)v1).booleanValue()) {
                    return var3_6.setErrorMsg("\u6743\u9650\u4e0d\u8db3");
                }
                var5_7 = var1_1.getBodyAsString();
                Intrinsics.checkNotNullExpressionValue(var5_7, "context.bodyAsString");
                var5_7 = RssSource.Companion.fromJson-IoAF18A((String)var5_7);
                var6_8 = false;
                rssSource = (RssSource)(Result.isFailure-impl(var5_7) != false ? null : var5_7);
                if (rssSource == null) {
                    return var3_6.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                userNameSpace = this.getUserNameSpace(var1_1);
                rssSourceList = ExtKt.asJsonArray(this.getUserStorage(userNameSpace, var7_11 = new String[]{"rssSources"}));
                if (rssSourceList == null) {
                    rssSourceList = new JsonArray();
                }
                existIndex = -1;
                var8_13 = 0;
                var9_14 = rssSourceList.size();
                if (var8_13 < var9_14) {
                    do {
                        i = var8_13++;
                        var12_17 = rssSourceList.getJsonObject(i).toString();
                        Intrinsics.checkNotNullExpressionValue(var12_17, "rssSourceList.getJsonObject(i).toString()");
                        var12_17 = RssSource.Companion.fromJson-IoAF18A((String)var12_17);
                        var13_18 = false;
                        _rssSource = (RssSource)(Result.isFailure-impl(var12_17) != false ? null : var12_17);
                        if (_rssSource == null || !_rssSource.getSourceUrl().equals(rssSource.getSourceUrl())) continue;
                        existIndex = i;
                        break;
                    } while (var8_13 < var9_14);
                }
                if (existIndex >= 0) {
                    rssSourceList.remove(existIndex);
                }
                this.saveUserStorage(userNameSpace, "rssSources", rssSourceList);
                return ReturnData.setData$default(var3_6, "", null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @Nullable
    public final RssSource getRssSourceByURL(@NotNull String url2, @NotNull String userNameSpace) {
        Intrinsics.checkNotNullParameter(url2, "url");
        Intrinsics.checkNotNullParameter(userNameSpace, "userNameSpace");
        CharSequence charSequence = url2;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return null;
        }
        String[] stringArray = new String[]{"rssSources"};
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
                object = RssSource.Companion.fromJson-IoAF18A((String)object);
                boolean bl2 = false;
                RssSource _rssSource = (RssSource)(Result.isFailure-impl(object) ? null : object);
                if (_rssSource == null || !_rssSource.getSourceUrl().equals(url2)) continue;
                return _rssSource;
            } while (n < n2);
        }
        return null;
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public final Object getRssArticles(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof getRssArticles.1)) ** GOTO lbl-1000
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
                /* synthetic */ Object result;
                final /* synthetic */ RssSourceController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.getRssArticles(null, this);
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
                this = (RssSourceController)$continuation.L$0;
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
                var7_10 = 0;
                if (context.request().method() == HttpMethod.POST) {
                    var8_11 = context.getBodyAsJson().getString("sourceUrl");
                    Intrinsics.checkNotNullExpressionValue(var8_11, "context.bodyAsJson.getString(\"sourceUrl\")");
                    sourceUrl = var8_11;
                    var8_11 = context.getBodyAsJson().getString("sortName", "");
                    Intrinsics.checkNotNullExpressionValue(var8_11, "context.bodyAsJson.getString(\"sortName\", \"\")");
                    sortName = var8_11;
                    var8_11 = context.getBodyAsJson().getString("sortUrl", "");
                    Intrinsics.checkNotNullExpressionValue(var8_11, "context.bodyAsJson.getString(\"sortUrl\", \"\")");
                    sortUrl = var8_11;
                    var8_11 = context.getBodyAsJson().getInteger("page", Boxing.boxInt(1));
                    Intrinsics.checkNotNullExpressionValue(var8_11, "context.bodyAsJson.getInteger(\"page\", 1)");
                    var7_10 = ((Number)var8_11).intValue();
                } else {
                    var9_12 = context.queryParam("sourceUrl");
                    Intrinsics.checkNotNullExpressionValue(var9_12, "context.queryParam(\"sourceUrl\")");
                    var8_11 = CollectionsKt.firstOrNull(var9_12);
                    sourceUrl = var8_11 == null ? "" : var8_11;
                    var9_12 = context.queryParam("sortName");
                    Intrinsics.checkNotNullExpressionValue(var9_12, "context.queryParam(\"sortName\")");
                    var8_11 = CollectionsKt.firstOrNull(var9_12);
                    sortName = var8_11 == null ? "" : var8_11;
                    var9_12 = context.queryParam("sortUrl");
                    Intrinsics.checkNotNullExpressionValue(var9_12, "context.queryParam(\"sortUrl\")");
                    var8_11 = CollectionsKt.firstOrNull(var9_12);
                    sortUrl = var8_11 == null ? "" : var8_11;
                    var9_12 = context.queryParam("page");
                    Intrinsics.checkNotNullExpressionValue(var9_12, "context.queryParam(\"page\")");
                    var8_11 = CollectionsKt.firstOrNull(var9_12);
                    if (var8_11 == null) {
                        v1 = 1;
                    } else {
                        var10_15 = var8_11;
                        var11_16 = false;
                        var9_12 = Boxing.boxInt(Integer.parseInt((String)var10_15));
                        v1 = var9_12 == null ? 1 : var9_12.intValue();
                    }
                    page = v1;
                }
                var8_11 = sourceUrl;
                var9_13 = false;
                if (var8_11.length() == 0) {
                    return returnData.setErrorMsg("RSS\u6e90\u94fe\u63a5\u4e0d\u80fd\u4e3a\u7a7a");
                }
                var8_11 = (CharSequence)sortUrl;
                var9_13 = false;
                if (var8_11.length() == 0) {
                    sortUrl = sourceUrl;
                }
                if ((rssSource = this.getRssSourceByURL(sourceUrl, userNameSpace = this.getUserNameSpace(context))) == null) {
                    return returnData.setErrorMsg("RSS\u6e90\u4e0d\u5b58\u5728");
                }
                $continuation.L$0 = returnData;
                $continuation.L$1 = null;
                $continuation.L$2 = null;
                $continuation.label = 2;
                v2 = Rss.INSTANCE.getArticles(sortName, (String)sortUrl, rssSource, page, Debug.INSTANCE, $continuation);
                if (v2 == var14_5) {
                    return var14_5;
                }
                ** GOTO lbl92
            }
            case 2: {
                var3_6 = (ReturnData)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v2 = $result;
lbl92:
                // 2 sources

                rssArtcles = (Pair)v2;
                return ReturnData.setData$default(var3_6, rssArtcles, null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object getRssContent(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof getRssContent.1)) ** GOTO lbl-1000
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
                /* synthetic */ Object result;
                final /* synthetic */ RssSourceController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.getRssContent(null, this);
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
                this = (RssSourceController)$continuation.L$0;
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
                    var7_10 = context.getBodyAsJson().getString("sourceUrl");
                    Intrinsics.checkNotNullExpressionValue(var7_10, "context.bodyAsJson.getString(\"sourceUrl\")");
                    sourceUrl = var7_10;
                    var7_10 = context.getBodyAsJson().getString("link");
                    Intrinsics.checkNotNullExpressionValue(var7_10, "context.bodyAsJson.getString(\"link\")");
                    link = var7_10;
                    var7_10 = context.getBodyAsJson().getString("origin");
                    Intrinsics.checkNotNullExpressionValue(var7_10, "context.bodyAsJson.getString(\"origin\")");
                    var6_9 = var7_10;
                } else {
                    var8_11 = context.queryParam("sourceUrl");
                    Intrinsics.checkNotNullExpressionValue(var8_11, "context.queryParam(\"sourceUrl\")");
                    var7_10 = CollectionsKt.firstOrNull(var8_11);
                    sourceUrl = var7_10 == null ? "" : var7_10;
                    var8_11 = context.queryParam("link");
                    Intrinsics.checkNotNullExpressionValue(var8_11, "context.queryParam(\"link\")");
                    var7_10 = CollectionsKt.firstOrNull(var8_11);
                    link = var7_10 == null ? "" : var7_10;
                    var8_11 = context.queryParam("origin");
                    Intrinsics.checkNotNullExpressionValue(var8_11, "context.queryParam(\"origin\")");
                    var7_10 = CollectionsKt.firstOrNull(var8_11);
                    origin /* !! */  = var7_10 == null ? "" : var7_10;
                }
                var7_10 = sourceUrl;
                var8_12 = false;
                if (var7_10.length() == 0) {
                    return returnData.setErrorMsg("RSS\u94fe\u63a5\u4e0d\u80fd\u4e3a\u7a7a");
                }
                var7_10 = link;
                var8_12 = false;
                if (var7_10.length() == 0) {
                    return returnData.setErrorMsg("RSS\u6587\u7ae0\u94fe\u63a5\u4e0d\u80fd\u4e3a\u7a7a");
                }
                var7_10 = origin /* !! */ ;
                var8_12 = false;
                if (var7_10.length() == 0) {
                    return returnData.setErrorMsg("RSS\u6587\u7ae0\u6765\u6e90\u4e0d\u80fd\u4e3a\u7a7a");
                }
                userNameSpace = this.getUserNameSpace(context);
                rssSource = this.getRssSourceByURL(sourceUrl, userNameSpace);
                if (rssSource == null) {
                    return returnData.setErrorMsg("RSS\u6e90\u4e0d\u5b58\u5728");
                }
                rssArticle = new RssArticle(origin /* !! */ , null, null, 0L, link, null, null, null, null, false, null, 2030, null);
                content = "";
                if (rssSource.getRuleContent() == null) ** GOTO lbl90
                var11_16 = rssSource.getRuleContent();
                if (var11_16 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
                }
                $continuation.L$0 = returnData;
                $continuation.L$1 = null;
                $continuation.L$2 = null;
                $continuation.label = 2;
                v1 = Rss.INSTANCE.getContent(rssArticle, var11_16, rssSource, Debug.INSTANCE, $continuation);
                if (v1 == var14_5) {
                    return var14_5;
                }
                ** GOTO lbl89
            }
            case 2: {
                var3_6 = (ReturnData)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v1 = $result;
lbl89:
                // 2 sources

                content = (String)v1;
lbl90:
                // 2 sources

                return ReturnData.setData$default(var3_6, content, null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}

