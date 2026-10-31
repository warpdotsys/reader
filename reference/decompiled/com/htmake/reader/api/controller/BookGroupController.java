/*
 * Decompiled with CFR 0.152.
 */
package com.htmake.reader.api.controller;

import com.htmake.reader.api.ReturnData;
import com.htmake.reader.api.controller.BaseController;
import com.htmake.reader.api.controller.BookGroupController;
import com.htmake.reader.api.controller.CURD;
import com.htmake.reader.db.DB;
import com.htmake.reader.utils.ExtKt;
import io.legado.app.data.entities.BookGroup;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import java.util.Iterator;
import java.util.LinkedHashMap;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J \u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\u00032\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000bH\u0016J\u0019\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u0018\u0010\u0011\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0003H\u0016J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J \u0010\u0019\u001a\u00020\u001a2\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0018\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\t\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u0017H\u0016J\u0019\u0010\u001f\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006 "}, d2={"Lcom/htmake/reader/api/controller/BookGroupController;", "Lcom/htmake/reader/api/controller/BaseController;", "Lcom/htmake/reader/api/controller/CURD;", "Lio/legado/app/data/entities/BookGroup;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)V", "beforeSave", "Lcom/htmake/reader/api/ReturnData;", "var1", "db", "Lcom/htmake/reader/db/DB;", "checkUserAuth", "", "context", "Lio/vertx/ext/web/RoutingContext;", "(Lio/vertx/ext/web/RoutingContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checker", "Lio/vertx/core/json/JsonObject;", "var2", "getEntityClass", "Ljava/lang/Class;", "getTableName", "", "getUserNS", "onCheckEnd", "", "bookGroupList", "Lio/vertx/core/json/JsonArray;", "onList", "userNameSpace", "saveBookGroupOrder", "reader-pro"})
public final class BookGroupController
extends BaseController
implements CURD<BookGroup> {
    public BookGroupController(@NotNull CoroutineContext coroutineContext2) {
        Intrinsics.checkNotNullParameter(coroutineContext2, "coroutineContext");
        super(coroutineContext2);
    }

    @Override
    @NotNull
    public String getTableName() {
        return "bookGroup";
    }

    @Override
    @NotNull
    public JsonArray onList(@NotNull JsonArray var1, @NotNull String userNameSpace) {
        JsonArray var2;
        Intrinsics.checkNotNullParameter(var1, "var1");
        Intrinsics.checkNotNullParameter(userNameSpace, "userNameSpace");
        if (var1.size() == 0 && (var2 = ExtKt.asJsonArray("\n            [{\"groupId\":-1,\"groupName\":\"\u5168\u90e8\",\"order\":-10,\"show\":true},{\"groupId\":-2,\"groupName\":\"\u672c\u5730\",\"order\":-9,\"show\":true},{\"groupId\":-3,\"groupName\":\"\u97f3\u9891\",\"order\":-8,\"show\":true},{\"groupId\":-4,\"groupName\":\"\u672a\u5206\u7ec4\",\"order\":-7,\"show\":true},{\"groupId\":-5,\"groupName\":\"\u66f4\u65b0\u9519\u8bef\",\"order\":-6,\"show\":true}]\n            ")) != null) {
            this.saveUserStorage(userNameSpace, "bookGroup", var2);
            return var2;
        }
        return var1;
    }

    @Override
    public boolean checker(@NotNull JsonObject var1, @NotNull BookGroup var2) {
        Intrinsics.checkNotNullParameter(var1, "var1");
        Intrinsics.checkNotNullParameter(var2, "var2");
        return ((Object)var2.getGroupId()).equals(var1.getLong("groupId"));
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void onCheckEnd(@NotNull BookGroup var1, boolean var2, @NotNull JsonArray bookGroupList) {
        Intrinsics.checkNotNullParameter(var1, "var1");
        Intrinsics.checkNotNullParameter(bookGroupList, "bookGroupList");
        if (!var2) {
            int maxOrder = 0;
            Iterable iterable = bookGroupList;
            boolean bl = false;
            long l = 0L;
            Iterator iterator2 = iterable.iterator();
            while (iterator2.hasNext()) {
                Integer n;
                Object object;
                void it;
                Object t;
                Object t2 = t = iterator2.next();
                long l2 = l;
                boolean bl2 = false;
                JsonObject jsonObject = ExtKt.asJsonObject(it);
                long id = jsonObject == null ? 0L : ((object = jsonObject.getLong("groupId", 0L)) == null ? 0L : (Long)object);
                object = ExtKt.asJsonObject(it);
                int order = object == null ? 0 : ((n = ((JsonObject)object).getInteger("order", 0)) == null ? 0 : n);
                maxOrder = order > maxOrder ? order : maxOrder;
                long l3 = id > 0L ? id : 0L;
                l = l2 + l3;
            }
            long idsSum = l;
            long id = 1L;
            while ((id & idsSum) != 0L) {
                id <<= 1;
            }
            var1.setGroupId(id);
            var1.setOrder(maxOrder + 1);
        }
    }

    @Override
    @Nullable
    public ReturnData beforeSave(@NotNull BookGroup var1, @NotNull DB<BookGroup> db) {
        Intrinsics.checkNotNullParameter(var1, "var1");
        Intrinsics.checkNotNullParameter(db, "db");
        ReturnData returnData = new ReturnData();
        CharSequence charSequence = var1.getGroupName();
        boolean bl = false;
        if (charSequence.length() == 0) {
            return returnData.setErrorMsg("\u5206\u7ec4\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return null;
    }

    @Override
    @Nullable
    public Object checkUserAuth(@NotNull RoutingContext context, @NotNull Continuation<? super Boolean> $completion) {
        return this.checkAuth(context, $completion);
    }

    @Override
    @NotNull
    public String getUserNS(@NotNull RoutingContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return this.getUserNameSpace(context);
    }

    @Override
    @NotNull
    public Class<BookGroup> getEntityClass() {
        return BookGroup.class;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Nullable
    public final Object saveBookGroupOrder(@NotNull RoutingContext var1_1, @NotNull Continuation<? super ReturnData> var2_2) {
        if (!(var2_2 instanceof saveBookGroupOrder.1)) ** GOTO lbl-1000
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
                /* synthetic */ Object result;
                final /* synthetic */ BookGroupController this$0;
                int label;
                {
                    this.this$0 = this$0;
                    super($completion);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.saveBookGroupOrder(null, this);
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
                this = (BookGroupController)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v0 = $result;
lbl27:
                // 2 sources

                if (!((Boolean)v0).booleanValue()) {
                    return ReturnData.setData$default(returnData, "NEED_LOGIN", null, 2, null).setErrorMsg("\u8bf7\u767b\u5f55\u540e\u4f7f\u7528");
                }
                bookGroupOrder = context.getBodyAsJson().getJsonArray("order", null);
                if (bookGroupOrder == null) {
                    return returnData.setErrorMsg("\u53c2\u6570\u9519\u8bef");
                }
                userNameSpace = this.getUserNameSpace(context);
                bookGroupList = ExtKt.asJsonArray(this.getUserStorage(userNameSpace, var7_9 = new String[]{"bookGroup"}));
                if (bookGroupList == null) {
                    bookGroupList = new JsonArray();
                }
                var8_11 = 0;
                orderMap = new LinkedHashMap<K, V>();
                var8_11 = 0;
                var9_13 = bookGroupOrder.size();
                if (var8_11 < var9_13) {
                    do {
                        i = var8_11++;
                        var11_15 /* !! */  = bookGroupOrder.getJsonObject(i).getLong("groupId");
                        Intrinsics.checkNotNullExpressionValue(var11_15 /* !! */ , "bookGroupOrder.getJsonObject(i).getLong(\"groupId\")");
                        v1 = var11_15 /* !! */ ;
                        var11_15 /* !! */  = bookGroupOrder.getJsonObject(i).getInteger("order");
                        Intrinsics.checkNotNullExpressionValue(var11_15 /* !! */ , "bookGroupOrder.getJsonObject(i).getInteger(\"order\")");
                        orderMap.put(v1, var11_15 /* !! */ );
                    } while (var8_11 < var9_13);
                }
                groupList = bookGroupList.getList();
                var9_13 = 0;
                var10_14 = bookGroupList.size();
                if (var9_13 < var10_14) {
                    do {
                        if (!orderMap.containsKey(Boxing.boxLong((bookGroup = bookGroupList.getJsonObject(i = var9_13++).mapTo(BookGroup.class)).getGroupId()))) continue;
                        var14_20 = orderMap.get(Boxing.boxLong(bookGroup.getGroupId()));
                        var13_19 = var14_20 instanceof Integer != false ? (Integer)var14_20 : null;
                        bookGroup.setOrder(var13_19 == null ? bookGroup.getOrder() : var13_19.intValue());
                        groupList.set(i, JsonObject.mapFrom(bookGroup));
                    } while (var9_13 < var10_14);
                }
                bookGroupList = new JsonArray(groupList);
                this.saveUserStorage(userNameSpace, "bookGroup", bookGroupList);
                return ReturnData.setData$default(returnData, "", null, 2, null);
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    @Override
    @Nullable
    public ReturnData beforeAdd(@NotNull BookGroup val1, @NotNull DB<BookGroup> db) {
        return CURD.DefaultImpls.beforeAdd(this, val1, db);
    }

    @Override
    @Nullable
    public ReturnData beforeDelete(@NotNull BookGroup val1, @NotNull DB<BookGroup> db) {
        return CURD.DefaultImpls.beforeDelete(this, val1, db);
    }

    @Override
    @NotNull
    public BookGroup convertToEntity(@NotNull JsonObject var1) {
        return CURD.DefaultImpls.convertToEntity(this, var1);
    }

    @NotNull
    public BookGroup[] convertToEntityList(@NotNull String var1) {
        return CURD.DefaultImpls.convertToEntityList(this, var1);
    }

    @Override
    @Nullable
    public Object delete(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        return CURD.DefaultImpls.delete(this, context, $completion);
    }

    @Override
    @Nullable
    public Object deleteMulti(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        return CURD.DefaultImpls.deleteMulti(this, context, $completion);
    }

    @Override
    @Nullable
    public Object list(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        return CURD.DefaultImpls.list(this, context, $completion);
    }

    @Override
    @Nullable
    public Object save(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        return CURD.DefaultImpls.save(this, context, $completion);
    }

    @Override
    @Nullable
    public Object saveMulti(@NotNull RoutingContext context, @NotNull Continuation<? super ReturnData> $completion) {
        return CURD.DefaultImpls.saveMulti(this, context, $completion);
    }
}

