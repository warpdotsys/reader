/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.adapters;

import io.legado.app.help.http.StrResponse;
import io.legado.app.model.DebugLog;
import java.util.Map;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u00a9\u0001\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\u0016\b\u0002\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015J!\u0010\u0016\u001a\u00020\u00032\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0018\"\u00020\u0003H&\u00a2\u0006\u0002\u0010\u0019J\u0012\u0010\u0016\u001a\u00020\u00032\b\b\u0002\u0010\u001a\u001a\u00020\u0003H&\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001b"}, d2={"Lio/legado/app/adapters/ReaderAdapterInterface;", "", "getCacheDir", "", "getStrResponseByRemoteWebview", "Lio/legado/app/help/http/StrResponse;", "url", "html", "encode", "tag", "headerMap", "", "sourceRegex", "javaScript", "proxy", "post", "", "body", "userNameSpace", "debugLog", "Lio/legado/app/model/DebugLog;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;Lio/legado/app/model/DebugLog;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWorkDir", "subDirFiles", "", "([Ljava/lang/String;)Ljava/lang/String;", "subPath", "reader-pro"})
public interface ReaderAdapterInterface {
    @NotNull
    public String getWorkDir(@NotNull String var1);

    @NotNull
    public String getWorkDir(String ... var1);

    @NotNull
    public String getCacheDir();

    @Nullable
    public Object getStrResponseByRemoteWebview(@Nullable String var1, @Nullable String var2, @Nullable String var3, @Nullable String var4, @Nullable Map<String, String> var5, @Nullable String var6, @Nullable String var7, @Nullable String var8, boolean var9, @Nullable String var10, @NotNull String var11, @Nullable DebugLog var12, @NotNull Continuation<? super StrResponse> var13);

    @Metadata(mv={1, 5, 1}, k=3, xi=48)
    public static final class DefaultImpls {
        public static /* synthetic */ String getWorkDir$default(ReaderAdapterInterface readerAdapterInterface, String string, int n, Object object) {
            if (object != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getWorkDir");
            }
            if ((n & 1) != 0) {
                string = "";
            }
            return readerAdapterInterface.getWorkDir(string);
        }

        public static /* synthetic */ Object getStrResponseByRemoteWebview$default(ReaderAdapterInterface readerAdapterInterface, String string, String string2, String string3, String string4, Map map, String string5, String string6, String string7, boolean bl, String string8, String string9, DebugLog debugLog, Continuation continuation, int n, Object object) {
            if (object != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getStrResponseByRemoteWebview");
            }
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
            return readerAdapterInterface.getStrResponseByRemoteWebview(string, string2, string3, string4, map, string5, string6, string7, bl, string8, string9, debugLog, continuation);
        }
    }
}

