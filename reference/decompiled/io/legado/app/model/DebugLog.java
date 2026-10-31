/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.model;

import io.legado.app.model.DebugLogKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J*\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\b\u001a\u00020\tH\u0016\u00a8\u0006\n"}, d2={"Lio/legado/app/model/DebugLog;", "Lokhttp3/logging/HttpLoggingInterceptor$Logger;", "log", "", "message", "", "sourceUrl", "msg", "isHtml", "", "reader-pro"})
public interface DebugLog
extends HttpLoggingInterceptor.Logger {
    public void log(@Nullable String var1, @Nullable String var2, boolean var3);

    @Override
    public void log(@NotNull String var1);

    @Metadata(mv={1, 5, 1}, k=3, xi=48)
    public static final class DefaultImpls {
        public static void log(@NotNull DebugLog this_, @Nullable String sourceUrl, @Nullable String msg, boolean isHtml) {
            Intrinsics.checkNotNullParameter(this_, "this");
            DebugLogKt.access$getLogger$p().info("sourceUrl: {}, msg: {}", (Object)sourceUrl, (Object)msg);
        }

        public static /* synthetic */ void log$default(DebugLog debugLog, String string, String string2, boolean bl, int n, Object object) {
            if (object != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: log");
            }
            if ((n & 1) != 0) {
                string = "";
            }
            if ((n & 2) != 0) {
                string2 = "";
            }
            if ((n & 4) != 0) {
                bl = false;
            }
            debugLog.log(string, string2, bl);
        }

        public static void log(@NotNull DebugLog this_, @NotNull String message) {
            Intrinsics.checkNotNullParameter(this_, "this");
            Intrinsics.checkNotNullParameter(message, "message");
            DebugLogKt.access$getLogger$p().debug(message);
        }
    }
}

