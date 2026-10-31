/*
 * Decompiled with CFR 0.152.
 */
package com.htmake.reader.api.controller;

import com.htmake.reader.api.ReturnData;
import com.htmake.reader.api.controller.CURD;
import com.htmake.reader.db.DB;
import com.htmake.reader.utils.ExtKt;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import java.lang.reflect.Array;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J%\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00028\u00002\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H\u0016\u00a2\u0006\u0002\u0010\bJ%\u0010\t\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00028\u00002\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H\u0016\u00a2\u0006\u0002\u0010\bJ%\u0010\n\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00028\u00002\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H\u0016\u00a2\u0006\u0002\u0010\bJ\u0019\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\u001d\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00028\u0000H&\u00a2\u0006\u0002\u0010\u0014J\u0015\u0010\u0015\u001a\u00028\u00002\u0006\u0010\u0011\u001a\u00020\u0012H\u0016\u00a2\u0006\u0002\u0010\u0016J\u001b\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\u00182\u0006\u0010\u0011\u001a\u00020\u0019H\u0016\u00a2\u0006\u0002\u0010\u001aJ\u0019\u0010\u001b\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\u0019\u0010\u001c\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\u000e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00000\u001eH&J\b\u0010\u001f\u001a\u00020\u0019H&J\u0010\u0010 \u001a\u00020\u00192\u0006\u0010\r\u001a\u00020\u000eH&J\u0019\u0010!\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ%\u0010\"\u001a\u00020#2\u0006\u0010\u0011\u001a\u00028\u00002\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010$\u001a\u00020%H\u0016\u00a2\u0006\u0002\u0010&J\u0018\u0010'\u001a\u00020%2\u0006\u0010\u0011\u001a\u00020%2\u0006\u0010(\u001a\u00020\u0019H\u0016J\u0019\u0010)\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\u0019\u0010*\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000f\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006+"}, d2={"Lcom/htmake/reader/api/controller/CURD;", "T", "", "beforeAdd", "Lcom/htmake/reader/api/ReturnData;", "val1", "db", "Lcom/htmake/reader/db/DB;", "(Ljava/lang/Object;Lcom/htmake/reader/db/DB;)Lcom/htmake/reader/api/ReturnData;", "beforeDelete", "beforeSave", "checkUserAuth", "", "context", "Lio/vertx/ext/web/RoutingContext;", "(Lio/vertx/ext/web/RoutingContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checker", "var1", "Lio/vertx/core/json/JsonObject;", "var2", "(Lio/vertx/core/json/JsonObject;Ljava/lang/Object;)Z", "convertToEntity", "(Lio/vertx/core/json/JsonObject;)Ljava/lang/Object;", "convertToEntityList", "", "", "(Ljava/lang/String;)[Ljava/lang/Object;", "delete", "deleteMulti", "getEntityClass", "Ljava/lang/Class;", "getTableName", "getUserNS", "list", "onCheckEnd", "", "var3", "Lio/vertx/core/json/JsonArray;", "(Ljava/lang/Object;ZLio/vertx/core/json/JsonArray;)V", "onList", "userNameSpace", "save", "saveMulti", "reader-pro"})
public interface CURD<T> {
    @NotNull
    public String getTableName();

    public T convertToEntity(@NotNull JsonObject var1);

    @NotNull
    public T[] convertToEntityList(@NotNull String var1);

    @NotNull
    public JsonArray onList(@NotNull JsonArray var1, @NotNull String var2);

    public boolean checker(@NotNull JsonObject var1, T var2);

    public void onCheckEnd(T var1, boolean var2, @NotNull JsonArray var3);

    @Nullable
    public ReturnData beforeSave(T var1, @NotNull DB<T> var2);

    @Nullable
    public ReturnData beforeAdd(T var1, @NotNull DB<T> var2);

    @Nullable
    public ReturnData beforeDelete(T var1, @NotNull DB<T> var2);

    @Nullable
    public Object checkUserAuth(@NotNull RoutingContext var1, @NotNull Continuation<? super Boolean> var2);

    @NotNull
    public String getUserNS(@NotNull RoutingContext var1);

    @NotNull
    public Class<T> getEntityClass();

    @Nullable
    public Object list(@NotNull RoutingContext var1, @NotNull Continuation<? super ReturnData> var2);

    @Nullable
    public Object save(@NotNull RoutingContext var1, @NotNull Continuation<? super ReturnData> var2);

    @Nullable
    public Object saveMulti(@NotNull RoutingContext var1, @NotNull Continuation<? super ReturnData> var2);

    @Nullable
    public Object delete(@NotNull RoutingContext var1, @NotNull Continuation<? super ReturnData> var2);

    @Nullable
    public Object deleteMulti(@NotNull RoutingContext var1, @NotNull Continuation<? super ReturnData> var2);

    @Metadata(mv={1, 5, 1}, k=3, xi=48)
    public static final class DefaultImpls {
        public static <T> T convertToEntity(@NotNull CURD<T> this_, @NotNull JsonObject var1) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(var1, "var1");
            return var1.mapTo(this_.getEntityClass());
        }

        @NotNull
        public static <T> T[] convertToEntityList(@NotNull CURD<T> this_, @NotNull String var1) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(var1, "var1");
            Class<?> clazz = Array.newInstance(this_.getEntityClass(), 0).getClass();
            Object obj = ExtKt.getGson().fromJson(var1, clazz);
            Intrinsics.checkNotNullExpressionValue(obj, "gson.fromJson(var1, clazz)");
            Object[] itemList = (Object[])obj;
            return itemList;
        }

        @NotNull
        public static <T> JsonArray onList(@NotNull CURD<T> this_, @NotNull JsonArray var1, @NotNull String userNameSpace) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(var1, "var1");
            Intrinsics.checkNotNullParameter(userNameSpace, "userNameSpace");
            return var1;
        }

        public static <T> void onCheckEnd(@NotNull CURD<T> this_, T var1, boolean var2, @NotNull JsonArray var3) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(var3, "var3");
        }

        @Nullable
        public static <T> ReturnData beforeSave(@NotNull CURD<T> this_, T val1, @NotNull DB<T> db) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(db, "db");
            return null;
        }

        @Nullable
        public static <T> ReturnData beforeAdd(@NotNull CURD<T> this_, T val1, @NotNull DB<T> db) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(db, "db");
            return null;
        }

        @Nullable
        public static <T> ReturnData beforeDelete(@NotNull CURD<T> this_, T val1, @NotNull DB<T> db) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(db, "db");
            return null;
        }

        /*
         * Unable to fully structure code
         */
        @Nullable
        public static <T> Object list(@NotNull CURD<T> var0, @NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
            if (!(var2_2 instanceof list.1)) ** GOTO lbl-1000
            var8_3 = var2_2;
            if ((var8_3.label & -2147483648) != 0) {
                var8_3.label -= -2147483648;
            } else lbl-1000:
            // 2 sources

            {
                $continuation = new ContinuationImpl((Continuation<? super list.1>)var2_2){
                    Object L$0;
                    Object L$1;
                    Object L$2;
                    /* synthetic */ Object result;
                    int label;

                    @Nullable
                    public final Object invokeSuspend(@NotNull Object $result) {
                        this.result = $result;
                        this.label |= Integer.MIN_VALUE;
                        return DefaultImpls.list(null, null, this);
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
                    v0 = this.checkUserAuth(context, $continuation);
                    if (v0 == var9_5) {
                        return var9_5;
                    }
                    ** GOTO lbl27
                }
                case 1: {
                    returnData = (ReturnData)$continuation.L$2;
                    context = (RoutingContext)$continuation.L$1;
                    this = (CURD)$continuation.L$0;
                    ResultKt.throwOnFailure($result);
                    v0 = $result;
lbl27:
                    // 2 sources

                    if (!((Boolean)v0).booleanValue()) {
                        return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                    }
                    userNameSpace = this.getUserNS(context);
                    list = DB.Companion.table$default(DB.Companion, userNameSpace, this.getTableName(), null, 4, null).readAll();
                    list = this.onList(list, userNameSpace);
                    var6_9 = list.getList();
                    Intrinsics.checkNotNullExpressionValue(var6_9, "list.getList()");
                    return ReturnData.setData$default(returnData, var6_9, null, 2, null);
                }
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }

        /*
         * Unable to fully structure code
         */
        @Nullable
        public static <T> Object save(@NotNull CURD<T> var0, @NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
            if (!(var2_2 instanceof save.1)) ** GOTO lbl-1000
            var9_3 = var2_2;
            if ((var9_3.label & -2147483648) != 0) {
                var9_3.label -= -2147483648;
            } else lbl-1000:
            // 2 sources

            {
                $continuation = new ContinuationImpl((Continuation<? super save.1>)var2_2){
                    Object L$0;
                    Object L$1;
                    Object L$2;
                    /* synthetic */ Object result;
                    int label;

                    @Nullable
                    public final Object invokeSuspend(@NotNull Object $result) {
                        this.result = $result;
                        this.label |= Integer.MIN_VALUE;
                        return DefaultImpls.save(null, null, this);
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
                    v0 = this.checkUserAuth(context, $continuation);
                    if (v0 == var10_5) {
                        return var10_5;
                    }
                    ** GOTO lbl27
                }
                case 1: {
                    returnData = (ReturnData)$continuation.L$2;
                    context = (RoutingContext)$continuation.L$1;
                    this = (CURD)$continuation.L$0;
                    ResultKt.throwOnFailure($result);
                    v0 = $result;
lbl27:
                    // 2 sources

                    if (!((Boolean)v0).booleanValue()) {
                        return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                    }
                    var5_7 = context.getBodyAsJson();
                    Intrinsics.checkNotNullExpressionValue(var5_7, "context.bodyAsJson");
                    entity = this.convertToEntity(var5_7);
                    userNameSpace = this.getUserNS(context);
                    db = DB.Companion.table$default(DB.Companion, userNameSpace, this.getTableName(), null, 4, null);
                    result = this.beforeSave(entity, db);
                    if (result != null) {
                        return result;
                    }
                    db.save(entity, (Function3)new Function3<T, Boolean, JsonArray, Unit>(this){

                        public final void invoke(T p0, boolean p1, @NotNull JsonArray p2) {
                            Intrinsics.checkNotNullParameter(p2, "p2");
                            ((CURD)this.receiver).onCheckEnd(p0, p1, p2);
                        }
                    }, (Function2)new Function2<JsonObject, T, Boolean>(this){
                        final /* synthetic */ CURD<T> this$0;
                        {
                            this.this$0 = $receiver;
                            super(2);
                        }

                        public final boolean invoke(@NotNull JsonObject jsonObject, T value) {
                            Intrinsics.checkNotNullParameter(jsonObject, "jsonObject");
                            return this.this$0.checker(jsonObject, value);
                        }
                    });
                    return ReturnData.setData$default(returnData, "", null, 2, null);
                }
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }

        /*
         * Unable to fully structure code
         */
        @Nullable
        public static <T> Object saveMulti(@NotNull CURD<T> var0, @NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
            if (!(var2_2 instanceof saveMulti.1)) ** GOTO lbl-1000
            var13_3 = var2_2;
            if ((var13_3.label & -2147483648) != 0) {
                var13_3.label -= -2147483648;
            } else lbl-1000:
            // 2 sources

            {
                $continuation = new ContinuationImpl((Continuation<? super saveMulti.1>)var2_2){
                    Object L$0;
                    Object L$1;
                    Object L$2;
                    /* synthetic */ Object result;
                    int label;

                    @Nullable
                    public final Object invokeSuspend(@NotNull Object $result) {
                        this.result = $result;
                        this.label |= Integer.MIN_VALUE;
                        return DefaultImpls.saveMulti(null, null, this);
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
                    v0 = this.checkUserAuth(context, $continuation);
                    if (v0 == var14_5) {
                        return var14_5;
                    }
                    ** GOTO lbl27
                }
                case 1: {
                    returnData = (ReturnData)$continuation.L$2;
                    context = (RoutingContext)$continuation.L$1;
                    this = (CURD)$continuation.L$0;
                    ResultKt.throwOnFailure($result);
                    v0 = $result;
lbl27:
                    // 2 sources

                    if (!((Boolean)v0).booleanValue()) {
                        return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                    }
                    var5_7 = context.getBodyAsString();
                    Intrinsics.checkNotNullExpressionValue(var5_7, "context.bodyAsString");
                    var5_7 = itemList = this.convertToEntityList((String)var5_7);
                    var6_9 = false;
                    if (var5_7.length == 0) {
                        return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                    }
                    userNameSpace = this.getUserNS(context);
                    db = DB.Companion.table$default(DB.Companion, userNameSpace, this.getTableName(), null, 4, null);
                    for (T item : itemList) {
                        result = this.beforeSave(item, db);
                        if (result == null) continue;
                        return result;
                    }
                    db.saveMulti(itemList, (Function3)new Function3<T, Boolean, JsonArray, Unit>(this){

                        public final void invoke(T p0, boolean p1, @NotNull JsonArray p2) {
                            Intrinsics.checkNotNullParameter(p2, "p2");
                            ((CURD)this.receiver).onCheckEnd(p0, p1, p2);
                        }
                    }, (Function2)new Function2<JsonObject, T, Boolean>(this){
                        final /* synthetic */ CURD<T> this$0;
                        {
                            this.this$0 = $receiver;
                            super(2);
                        }

                        public final boolean invoke(@NotNull JsonObject jsonObject, T value) {
                            Intrinsics.checkNotNullParameter(jsonObject, "jsonObject");
                            return this.this$0.checker(jsonObject, value);
                        }
                    });
                    return ReturnData.setData$default(returnData, "", null, 2, null);
                }
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }

        /*
         * Unable to fully structure code
         */
        @Nullable
        public static <T> Object delete(@NotNull CURD<T> var0, @NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
            if (!(var2_2 instanceof delete.1)) ** GOTO lbl-1000
            var9_3 = var2_2;
            if ((var9_3.label & -2147483648) != 0) {
                var9_3.label -= -2147483648;
            } else lbl-1000:
            // 2 sources

            {
                $continuation = new ContinuationImpl((Continuation<? super delete.1>)var2_2){
                    Object L$0;
                    Object L$1;
                    Object L$2;
                    /* synthetic */ Object result;
                    int label;

                    @Nullable
                    public final Object invokeSuspend(@NotNull Object $result) {
                        this.result = $result;
                        this.label |= Integer.MIN_VALUE;
                        return DefaultImpls.delete(null, null, this);
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
                    v0 = this.checkUserAuth(context, $continuation);
                    if (v0 == var10_5) {
                        return var10_5;
                    }
                    ** GOTO lbl27
                }
                case 1: {
                    returnData = (ReturnData)$continuation.L$2;
                    context = (RoutingContext)$continuation.L$1;
                    this = (CURD)$continuation.L$0;
                    ResultKt.throwOnFailure($result);
                    v0 = $result;
lbl27:
                    // 2 sources

                    if (!((Boolean)v0).booleanValue()) {
                        return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                    }
                    var5_7 = context.getBodyAsJson();
                    Intrinsics.checkNotNullExpressionValue(var5_7, "context.bodyAsJson");
                    entity = this.convertToEntity(var5_7);
                    userNameSpace = this.getUserNS(context);
                    db = DB.Companion.table$default(DB.Companion, userNameSpace, this.getTableName(), null, 4, null);
                    result = this.beforeDelete(entity, db);
                    if (result != null) {
                        return result;
                    }
                    db.delete((Function1<JsonObject, Boolean>)new Function1<JsonObject, Boolean>(this, entity){
                        final /* synthetic */ CURD<T> this$0;
                        final /* synthetic */ T $entity;
                        {
                            this.this$0 = $receiver;
                            this.$entity = $entity;
                            super(1);
                        }

                        public final boolean invoke(@NotNull JsonObject it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            return this.this$0.checker(it, this.$entity);
                        }
                    });
                    return ReturnData.setData$default(returnData, "", null, 2, null);
                }
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }

        /*
         * Unable to fully structure code
         */
        @Nullable
        public static <T> Object deleteMulti(@NotNull CURD<T> var0, @NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
            if (!(var2_2 instanceof deleteMulti.1)) ** GOTO lbl-1000
            var13_3 = var2_2;
            if ((var13_3.label & -2147483648) != 0) {
                var13_3.label -= -2147483648;
            } else lbl-1000:
            // 2 sources

            {
                $continuation = new ContinuationImpl((Continuation<? super deleteMulti.1>)var2_2){
                    Object L$0;
                    Object L$1;
                    Object L$2;
                    /* synthetic */ Object result;
                    int label;

                    @Nullable
                    public final Object invokeSuspend(@NotNull Object $result) {
                        this.result = $result;
                        this.label |= Integer.MIN_VALUE;
                        return DefaultImpls.deleteMulti(null, null, this);
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
                    v0 = this.checkUserAuth(context, $continuation);
                    if (v0 == var14_5) {
                        return var14_5;
                    }
                    ** GOTO lbl27
                }
                case 1: {
                    returnData = (ReturnData)$continuation.L$2;
                    context = (RoutingContext)$continuation.L$1;
                    this = (CURD)$continuation.L$0;
                    ResultKt.throwOnFailure($result);
                    v0 = $result;
lbl27:
                    // 2 sources

                    if (!((Boolean)v0).booleanValue()) {
                        return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                    }
                    itemList = new Ref.ObjectRef<T[]>();
                    var5_8 = context.getBodyAsString();
                    Intrinsics.checkNotNullExpressionValue(var5_8, "context.bodyAsString");
                    itemList.element = this.convertToEntityList((String)var5_8);
                    var5_8 = (Object[])itemList.element;
                    var6_9 = false;
                    if (var5_8.length == 0) {
                        return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                    }
                    userNameSpace = this.getUserNS(context);
                    db = DB.Companion.table$default(DB.Companion, userNameSpace, this.getTableName(), null, 4, null);
                    for (Object item : (Object[])itemList.element) {
                        result = this.beforeDelete(item, db);
                        if (result == null) continue;
                        return result;
                    }
                    db.delete((Function1<JsonObject, Boolean>)new Function1<JsonObject, Boolean>(itemList, this){
                        final /* synthetic */ Ref.ObjectRef<T[]> $itemList;
                        final /* synthetic */ CURD<T> this$0;
                        {
                            this.$itemList = $itemList;
                            this.this$0 = $receiver;
                            super(1);
                        }

                        public final boolean invoke(@NotNull JsonObject it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            int n = 0;
                            int n2 = ((Object[])this.$itemList.element).length;
                            if (n < n2) {
                                do {
                                    int k;
                                    if (!this.this$0.checker(it, ((Object[])this.$itemList.element)[k = n++])) continue;
                                    return true;
                                } while (n < n2);
                            }
                            return false;
                        }
                    });
                    return ReturnData.setData$default(returnData, "", null, 2, null);
                }
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}

