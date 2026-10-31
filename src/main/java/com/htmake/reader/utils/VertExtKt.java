/*
 * Decompiled with CFR 0.152.
 */
package com.htmake.reader.utils;

import com.htmake.reader.entity.BasicError;
import com.htmake.reader.utils.ExtKt;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.RoutingContext;
import java.net.URLDecoder;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.MDC;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 5, 1}, k=2, xi=48, d1={"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0018\u0010\u0005\u001a\u00020\u0001*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\b\u001a\u0014\u0010\t\u001a\u00020\u0001*\u00020\u00022\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a8\u0006\f"}, d2={"error", "", "Lio/vertx/ext/web/RoutingContext;", "throwable", "", "globalHandler", "Lio/vertx/ext/web/Route;", "handler", "Lio/vertx/core/Handler;", "success", "any", "", "reader-pro"})
public final class VertExtKt {
    public static final void success(@NotNull RoutingContext $this$success, @Nullable Object any) {
        String string;
        Intrinsics.checkNotNullParameter($this$success, "<this>");
        if (any instanceof JsonObject) {
            string = ((JsonObject)any).toString();
        } else {
            String string2 = ExtKt.getGson().toJson(any);
            Intrinsics.checkNotNullExpressionValue(string2, "{\n        gson.toJson(any)\n    }");
            string = string2;
        }
        String toJson = string;
        $this$success.response().putHeader("content-type", "application/json; charset=utf-8").end(toJson);
    }

    public static final void error(@NotNull RoutingContext $this$error, @NotNull Throwable throwable) {
        Intrinsics.checkNotNullParameter($this$error, "<this>");
        Intrinsics.checkNotNullParameter(throwable, "throwable");
        String path = URLDecoder.decode($this$error.request().absoluteURI(), "UTF-8");
        String string = throwable.toString();
        String string2 = String.valueOf(throwable.getMessage());
        Intrinsics.checkNotNullExpressionValue(path, "path");
        BasicError basicError = new BasicError("Internal Server Error", string, string2, path, 500, System.currentTimeMillis());
        String errorJson = ExtKt.getGson().toJson(basicError);
        ExtKt.getLogger().error("Internal Server Error", throwable);
        ExtKt.getLogger().error((Function0<? extends Object>)new Function0<Object>(errorJson){
            final /* synthetic */ String $errorJson;
            {
                this.$errorJson = $errorJson;
                super(0);
            }

            @Nullable
            public final Object invoke() {
                return this.$errorJson;
            }
        });
        $this$error.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(500).end(errorJson);
    }

    public static final void globalHandler(@NotNull Route $this$globalHandler, @NotNull Handler<RoutingContext> handler2) {
        Intrinsics.checkNotNullParameter($this$globalHandler, "<this>");
        Intrinsics.checkNotNullParameter(handler2, "handler");
        $this$globalHandler.handler(arg_0 -> VertExtKt.globalHandler$lambda-0(handler2, arg_0));
    }

    private static final void globalHandler$lambda-0(Handler $handler, RoutingContext ctx) {
        Intrinsics.checkNotNullParameter($handler, "$handler");
        String traceId = (String)ctx.get("traceId");
        CharSequence charSequence = traceId;
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence == null || charSequence.length() == 0) {
            traceId = ExtKt.getTraceId();
        }
        MDC.put("traceId", traceId);
        ctx.put("traceId", traceId);
        $handler.handle(ctx);
    }
}

