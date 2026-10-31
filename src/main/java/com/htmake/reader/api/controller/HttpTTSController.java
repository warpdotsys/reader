/*
 * Decompiled with CFR 0.152.
 */
package com.htmake.reader.api.controller;

import com.htmake.reader.api.ReturnData;
import com.htmake.reader.api.controller.BaseController;
import com.htmake.reader.api.controller.CURD;
import com.htmake.reader.db.DB;
import com.htmake.reader.utils.ExtKt;
import io.legado.app.data.entities.HttpTTS;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J \u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\u00032\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000bH\u0016J\u0019\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u0018\u0010\u0011\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0003H\u0016J\u0010\u0010\u0014\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0012H\u0016J\u001b\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u00162\u0006\u0010\t\u001a\u00020\u0017H\u0016\u00a2\u0006\u0002\u0010\u0018J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00030\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u0017H\u0016J\u0010\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\u000fH\u0016\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001d"}, d2={"Lcom/htmake/reader/api/controller/HttpTTSController;", "Lcom/htmake/reader/api/controller/BaseController;", "Lcom/htmake/reader/api/controller/CURD;", "Lio/legado/app/data/entities/HttpTTS;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)V", "beforeSave", "Lcom/htmake/reader/api/ReturnData;", "var1", "db", "Lcom/htmake/reader/db/DB;", "checkUserAuth", "", "context", "Lio/vertx/ext/web/RoutingContext;", "(Lio/vertx/ext/web/RoutingContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checker", "Lio/vertx/core/json/JsonObject;", "var2", "convertToEntity", "convertToEntityList", "", "", "(Ljava/lang/String;)[Lio/legado/app/data/entities/HttpTTS;", "getEntityClass", "Ljava/lang/Class;", "getTableName", "getUserNS", "reader-pro"})
public final class HttpTTSController
extends BaseController
implements CURD<HttpTTS> {
    public HttpTTSController(@NotNull CoroutineContext coroutineContext2) {
        Intrinsics.checkNotNullParameter(coroutineContext2, "coroutineContext");
        super(coroutineContext2);
    }

    @Override
    @NotNull
    public String getTableName() {
        return "httpTTS";
    }

    @Override
    public boolean checker(@NotNull JsonObject var1, @NotNull HttpTTS var2) {
        Intrinsics.checkNotNullParameter(var1, "var1");
        Intrinsics.checkNotNullParameter(var2, "var2");
        return var2.getName().equals(var1.getString("name"));
    }

    @Override
    @Nullable
    public ReturnData beforeSave(@NotNull HttpTTS var1, @NotNull DB<HttpTTS> db) {
        Intrinsics.checkNotNullParameter(var1, "var1");
        Intrinsics.checkNotNullParameter(db, "db");
        ReturnData returnData = new ReturnData();
        CharSequence charSequence = var1.getName();
        boolean bl = false;
        if (charSequence.length() == 0) {
            return returnData.setErrorMsg("\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        }
        charSequence = var1.getUrl();
        bl = false;
        if (charSequence.length() == 0) {
            return returnData.setErrorMsg("\u94fe\u63a5\u4e0d\u80fd\u4e3a\u7a7a");
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
    public Class<HttpTTS> getEntityClass() {
        return HttpTTS.class;
    }

    @Override
    @NotNull
    public HttpTTS convertToEntity(@NotNull JsonObject var1) {
        Intrinsics.checkNotNullParameter(var1, "var1");
        Object object = var1.toString();
        Intrinsics.checkNotNullExpressionValue(object, "var1.toString()");
        object = HttpTTS.Companion.fromJson-IoAF18A((String)object);
        boolean bl = false;
        Object object2 = Result.isFailure-impl(object) ? null : object;
        Intrinsics.checkNotNull(object2);
        return (HttpTTS)object2;
    }

    @NotNull
    public HttpTTS[] convertToEntityList(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "var1");
        JsonArray jsonArray = ExtKt.asJsonArray(var1);
        boolean bl = false;
        List list2 = new ArrayList();
        JsonArray jsonArray2 = jsonArray;
        if (jsonArray2 != null) {
            Iterable $this$forEach$iv = jsonArray2;
            boolean $i$f$forEach = false;
            Iterator iterator2 = $this$forEach$iv.iterator();
            while (iterator2.hasNext()) {
                Object element$iv;
                Object it = element$iv = iterator2.next();
                boolean bl2 = false;
                Object object = HttpTTS.Companion.fromJson-IoAF18A(it.toString());
                boolean bl3 = false;
                Object object2 = Result.isFailure-impl(object) ? null : object;
                Intrinsics.checkNotNull(object2);
                list2.add(object2);
            }
        }
        Collection $this$toTypedArray$iv = list2;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        HttpTTS[] httpTTSArray = thisCollection$iv.toArray(new HttpTTS[0]);
        if (httpTTSArray == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        return httpTTSArray;
    }

    @Override
    @Nullable
    public ReturnData beforeAdd(@NotNull HttpTTS val1, @NotNull DB<HttpTTS> db) {
        return CURD.DefaultImpls.beforeAdd(this, val1, db);
    }

    @Override
    @Nullable
    public ReturnData beforeDelete(@NotNull HttpTTS val1, @NotNull DB<HttpTTS> db) {
        return CURD.DefaultImpls.beforeDelete(this, val1, db);
    }

    @Override
    public void onCheckEnd(@NotNull HttpTTS var1, boolean var2, @NotNull JsonArray var3) {
        CURD.DefaultImpls.onCheckEnd(this, var1, var2, var3);
    }

    @Override
    @NotNull
    public JsonArray onList(@NotNull JsonArray var1, @NotNull String userNameSpace) {
        return CURD.DefaultImpls.onList(this, var1, userNameSpace);
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

