/*
 * Decompiled with CFR 0.152.
 */
package com.htmake.reader.api.controller;

import com.htmake.reader.api.ReturnData;
import com.htmake.reader.api.controller.BaseController;
import com.htmake.reader.api.controller.CURD;
import com.htmake.reader.db.DB;
import io.legado.app.data.entities.Bookmark;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J \u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\u00032\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000bH\u0016J\u0019\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u0018\u0010\u0011\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0003H\u0016J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\u000fH\u0016\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0019"}, d2={"Lcom/htmake/reader/api/controller/BookmarkController;", "Lcom/htmake/reader/api/controller/BaseController;", "Lcom/htmake/reader/api/controller/CURD;", "Lio/legado/app/data/entities/Bookmark;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)V", "beforeSave", "Lcom/htmake/reader/api/ReturnData;", "var1", "db", "Lcom/htmake/reader/db/DB;", "checkUserAuth", "", "context", "Lio/vertx/ext/web/RoutingContext;", "(Lio/vertx/ext/web/RoutingContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checker", "Lio/vertx/core/json/JsonObject;", "var2", "getEntityClass", "Ljava/lang/Class;", "getTableName", "", "getUserNS", "reader-pro"})
public final class BookmarkController
extends BaseController
implements CURD<Bookmark> {
    public BookmarkController(@NotNull CoroutineContext coroutineContext2) {
        Intrinsics.checkNotNullParameter(coroutineContext2, "coroutineContext");
        super(coroutineContext2);
    }

    @Override
    @NotNull
    public String getTableName() {
        return "bookmark";
    }

    @Override
    public boolean checker(@NotNull JsonObject var1, @NotNull Bookmark var2) {
        Intrinsics.checkNotNullParameter(var1, "var1");
        Intrinsics.checkNotNullParameter(var2, "var2");
        return ((Object)var2.getTime()).equals(var1.getLong("time"));
    }

    @Override
    @Nullable
    public ReturnData beforeSave(@NotNull Bookmark var1, @NotNull DB<Bookmark> db) {
        Intrinsics.checkNotNullParameter(var1, "var1");
        Intrinsics.checkNotNullParameter(db, "db");
        ReturnData returnData = new ReturnData();
        CharSequence charSequence = var1.getBookName();
        boolean bl = false;
        if (charSequence.length() == 0) {
            charSequence = var1.getBookAuthor();
            bl = false;
            if (charSequence.length() == 0) {
                return returnData.setErrorMsg("\u4e66\u7b7e\u4fe1\u606f\u9519\u8bef");
            }
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
    public Class<Bookmark> getEntityClass() {
        return Bookmark.class;
    }

    @Override
    @Nullable
    public ReturnData beforeAdd(@NotNull Bookmark val1, @NotNull DB<Bookmark> db) {
        return CURD.DefaultImpls.beforeAdd(this, val1, db);
    }

    @Override
    @Nullable
    public ReturnData beforeDelete(@NotNull Bookmark val1, @NotNull DB<Bookmark> db) {
        return CURD.DefaultImpls.beforeDelete(this, val1, db);
    }

    @Override
    @NotNull
    public Bookmark convertToEntity(@NotNull JsonObject var1) {
        return CURD.DefaultImpls.convertToEntity(this, var1);
    }

    @NotNull
    public Bookmark[] convertToEntityList(@NotNull String var1) {
        return CURD.DefaultImpls.convertToEntityList(this, var1);
    }

    @Override
    public void onCheckEnd(@NotNull Bookmark var1, boolean var2, @NotNull JsonArray var3) {
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

