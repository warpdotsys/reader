/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.help.coroutine;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.TimeoutKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 E*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0004DEFGBC\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012'\u0010\u0007\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\t\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b\u00a2\u0006\u0002\b\n\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0018\u0010\f\u001a\u00020\"2\u0010\b\u0002\u0010#\u001a\n\u0018\u00010$j\u0004\u0018\u0001`%J?\u0010&\u001a\u00020\"\"\u0004\b\u0001\u0010'2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010(\u001a\u0002H'2\u0016\u0010)\u001a\u0012\u0012\u0004\u0012\u0002H'0\u000fR\b\u0012\u0004\u0012\u00028\u00000\u0000H\u0082H\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010*J+\u0010+\u001a\u00020\"2\u0006\u0010\u0003\u001a\u00020\u00042\u0010\u0010)\u001a\f0\rR\b\u0012\u0004\u0012\u00028\u00000\u0000H\u0082H\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010,JT\u0010-\u001a\u00028\u00002\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020 2)\b\b\u0010\u0007\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\t\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b\u00a2\u0006\u0002\b\nH\u0082H\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010.JA\u0010/\u001a\u00020\u001a2\u0006\u0010\u0005\u001a\u00020\u00062'\u0010\u0007\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\t\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b\u00a2\u0006\u0002\b\nH\u0002\u00f8\u0001\u0000\u00a2\u0006\u0002\u00100J/\u00101\u001a\u0002022'\u00103\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0010\u00a2\u0006\f\b5\u0012\b\b6\u0012\u0004\b\b(#\u0012\u0004\u0012\u00020\"04j\u0002`7JI\u00108\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062'\u0010\u0007\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\t\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b\u00a2\u0006\u0002\b\n\u00f8\u0001\u0000\u00a2\u0006\u0002\u00109JO\u0010:\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062-\u0010\u0007\u001a)\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\t\u0012\u0006\u0012\u0004\u0018\u00010\u00020;\u00a2\u0006\u0002\b\n\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010<J\u001c\u0010=\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u000e\u0010(\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000>J\u001b\u0010=\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\u0010(\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\u0002\u0010?JI\u0010@\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062'\u0010\u0007\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\t\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b\u00a2\u0006\u0002\b\n\u00f8\u0001\u0000\u00a2\u0006\u0002\u00109JI\u0010A\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062'\u0010\u0007\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\t\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b\u00a2\u0006\u0002\b\n\u00f8\u0001\u0000\u00a2\u0006\u0002\u00109JO\u0010B\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062-\u0010\u0007\u001a)\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\t\u0012\u0006\u0012\u0004\u0018\u00010\u00020;\u00a2\u0006\u0002\b\n\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010<J\u001a\u0010C\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0>J\u0014\u0010C\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u001f\u001a\u00020 R\u001a\u0010\f\u001a\u000e\u0018\u00010\rR\b\u0012\u0004\u0012\u00028\u00000\u0000X\u0082\u000e\u00a2\u0006\u0002\n\u0000R \u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fR\b\u0012\u0004\u0012\u00028\u00000\u0000X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u000e\u0018\u00010\rR\b\u0012\u0004\u0012\u00028\u00000\u0000X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0014\u001a\u00020\u00158F\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u00158F\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0016R\u0011\u0010\u0018\u001a\u00020\u00158F\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0016R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u000e\u0018\u00010\rR\b\u0012\u0004\u0012\u00028\u00000\u0000X\u0082\u000e\u00a2\u0006\u0002\n\u0000R \u0010\u001e\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u000fR\b\u0012\u0004\u0012\u00028\u00000\u0000X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010!\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006H"}, d2={"Lio/legado/app/help/coroutine/Coroutine;", "T", "", "scope", "Lkotlinx/coroutines/CoroutineScope;", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)V", "cancel", "Lio/legado/app/help/coroutine/Coroutine$VoidCallback;", "error", "Lio/legado/app/help/coroutine/Coroutine$Callback;", "", "errorReturn", "Lio/legado/app/help/coroutine/Coroutine$Result;", "finally", "isActive", "", "()Z", "isCancelled", "isCompleted", "job", "Lkotlinx/coroutines/Job;", "getScope", "()Lkotlinx/coroutines/CoroutineScope;", "start", "success", "timeMillis", "", "Ljava/lang/Long;", "", "cause", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "dispatchCallback", "R", "value", "callback", "(Lkotlinx/coroutines/CoroutineScope;Ljava/lang/Object;Lio/legado/app/help/coroutine/Coroutine$Callback;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "dispatchVoidCallback", "(Lkotlinx/coroutines/CoroutineScope;Lio/legado/app/help/coroutine/Coroutine$VoidCallback;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "executeBlock", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;JLkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "executeInternal", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/Job;", "invokeOnCompletion", "Lkotlinx/coroutines/DisposableHandle;", "handler", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlinx/coroutines/CompletionHandler;", "onCancel", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Lio/legado/app/help/coroutine/Coroutine;", "onError", "Lkotlin/Function3;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function3;)Lio/legado/app/help/coroutine/Coroutine;", "onErrorReturn", "Lkotlin/Function0;", "(Ljava/lang/Object;)Lio/legado/app/help/coroutine/Coroutine;", "onFinally", "onStart", "onSuccess", "timeout", "Callback", "Companion", "Result", "VoidCallback", "reader-pro"})
public final class Coroutine<T> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final CoroutineScope scope;
    @NotNull
    private final Job job;
    @Nullable
    private VoidCallback start;
    @Nullable
    private Callback<T> success;
    @Nullable
    private Callback<Throwable> error;
    @Nullable
    private VoidCallback finally;
    @Nullable
    private VoidCallback cancel;
    @Nullable
    private Long timeMillis;
    @Nullable
    private Result<? extends T> errorReturn;
    @NotNull
    private static final CoroutineScope DEFAULT = CoroutineScopeKt.MainScope();

    public Coroutine(@NotNull CoroutineScope scope, @NotNull CoroutineContext context, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(block, "block");
        this.scope = scope;
        this.job = this.executeInternal(context, block);
    }

    public /* synthetic */ Coroutine(CoroutineScope coroutineScope, CoroutineContext coroutineContext2, Function2 function2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            coroutineContext2 = Dispatchers.getIO();
        }
        this(coroutineScope, coroutineContext2, function2);
    }

    @NotNull
    public final CoroutineScope getScope() {
        return this.scope;
    }

    public final boolean isCancelled() {
        return this.job.isCancelled();
    }

    public final boolean isActive() {
        return this.job.isActive();
    }

    public final boolean isCompleted() {
        return this.job.isCompleted();
    }

    @NotNull
    public final Coroutine<T> timeout(@NotNull Function0<Long> timeMillis) {
        Intrinsics.checkNotNullParameter(timeMillis, "timeMillis");
        this.timeMillis = timeMillis.invoke();
        return this;
    }

    @NotNull
    public final Coroutine<T> timeout(long timeMillis) {
        this.timeMillis = timeMillis;
        return this;
    }

    @NotNull
    public final Coroutine<T> onErrorReturn(@NotNull Function0<? extends T> value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.errorReturn = new Result<T>(value.invoke());
        return this;
    }

    @NotNull
    public final Coroutine<T> onErrorReturn(@Nullable T value) {
        this.errorReturn = new Result<T>(value);
        return this;
    }

    @NotNull
    public final Coroutine<T> onStart(@Nullable CoroutineContext context, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        this.start = new VoidCallback(context, block);
        return this;
    }

    public static /* synthetic */ Coroutine onStart$default(Coroutine coroutine, CoroutineContext coroutineContext2, Function2 function2, int n, Object object) {
        if ((n & 1) != 0) {
            coroutineContext2 = null;
        }
        return coroutine.onStart(coroutineContext2, function2);
    }

    @NotNull
    public final Coroutine<T> onSuccess(@Nullable CoroutineContext context, @NotNull Function3<? super CoroutineScope, ? super T, ? super Continuation<? super Unit>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        this.success = new Callback<T>(context, block);
        return this;
    }

    public static /* synthetic */ Coroutine onSuccess$default(Coroutine coroutine, CoroutineContext coroutineContext2, Function3 function3, int n, Object object) {
        if ((n & 1) != 0) {
            coroutineContext2 = null;
        }
        return coroutine.onSuccess(coroutineContext2, function3);
    }

    @NotNull
    public final Coroutine<T> onError(@Nullable CoroutineContext context, @NotNull Function3<? super CoroutineScope, ? super Throwable, ? super Continuation<? super Unit>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        this.error = new Callback<Throwable>(context, block);
        return this;
    }

    public static /* synthetic */ Coroutine onError$default(Coroutine coroutine, CoroutineContext coroutineContext2, Function3 function3, int n, Object object) {
        if ((n & 1) != 0) {
            coroutineContext2 = null;
        }
        return coroutine.onError(coroutineContext2, function3);
    }

    @NotNull
    public final Coroutine<T> onFinally(@Nullable CoroutineContext context, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        this.finally = new VoidCallback(context, block);
        return this;
    }

    public static /* synthetic */ Coroutine onFinally$default(Coroutine coroutine, CoroutineContext coroutineContext2, Function2 function2, int n, Object object) {
        if ((n & 1) != 0) {
            coroutineContext2 = null;
        }
        return coroutine.onFinally(coroutineContext2, function2);
    }

    @NotNull
    public final Coroutine<T> onCancel(@Nullable CoroutineContext context, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        this.cancel = new VoidCallback(context, block);
        return this;
    }

    public static /* synthetic */ Coroutine onCancel$default(Coroutine coroutine, CoroutineContext coroutineContext2, Function2 function2, int n, Object object) {
        if ((n & 1) != 0) {
            coroutineContext2 = null;
        }
        return coroutine.onCancel(coroutineContext2, function2);
    }

    public final void cancel(@Nullable CancellationException cause) {
        this.job.cancel(cause);
        VoidCallback voidCallback = this.cancel;
        if (voidCallback != null) {
            VoidCallback voidCallback2 = voidCallback;
            boolean bl = false;
            boolean bl2 = false;
            VoidCallback it = voidCallback2;
            boolean bl3 = false;
            BuildersKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new Function2<CoroutineScope, Continuation<? super Unit>, Object>(it, this, null){
                int label;
                final /* synthetic */ VoidCallback $it;
                final /* synthetic */ Coroutine<T> this$0;
                {
                    this.$it = $it;
                    this.this$0 = $receiver;
                    super(2, $completion);
                }

                /*
                 * WARNING - void declaration
                 * Enabled force condition propagation
                 * Lifted jumps to return sites
                 */
                @Nullable
                public final Object invokeSuspend(@NotNull Object object) {
                    void $result;
                    Object object2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    switch (this.label) {
                        case 0: {
                            ResultKt.throwOnFailure(object);
                            if (this.$it.getContext() != null) break;
                            this.label = 1;
                            Object object3 = this.$it.getBlock().invoke(this.this$0.getScope(), this);
                            if (object3 != object2) return Unit.INSTANCE;
                            return object2;
                        }
                        case 1: {
                            ResultKt.throwOnFailure($result);
                            Object object3 = $result;
                            return Unit.INSTANCE;
                        }
                    }
                    this.label = 2;
                    Object object4 = BuildersKt.withContext(this.this$0.getScope().getCoroutineContext().plus(this.$it.getContext()), (Function2)new Function2<CoroutineScope, Continuation<? super Unit>, Object>(this.$it, null){
                        int label;
                        private /* synthetic */ Object L$0;
                        final /* synthetic */ VoidCallback $it;
                        {
                            this.$it = $it;
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
                                    CoroutineScope $this$withContext = (CoroutineScope)this.L$0;
                                    this.label = 1;
                                    Object object3 = this.$it.getBlock().invoke($this$withContext, this);
                                    if (object3 != object2) return Unit.INSTANCE;
                                    return object2;
                                }
                                case 1: {
                                    void $result;
                                    ResultKt.throwOnFailure($result);
                                    Object object3 = $result;
                                    return Unit.INSTANCE;
                                }
                            }
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        @NotNull
                        public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                            Function2<CoroutineScope, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                            function2.L$0 = value;
                            return (Continuation)((Object)function2);
                        }

                        @Nullable
                        public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Unit> p2) {
                            return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                        }
                    }, this);
                    if (object4 != object2) return Unit.INSTANCE;
                    return object2;
                    {
                        case 2: {
                            ResultKt.throwOnFailure($result);
                            object4 = $result;
                            return Unit.INSTANCE;
                        }
                    }
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }

                @NotNull
                public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                    return (Continuation)((Object)new /* invalid duplicate definition of identical inner class */);
                }

                @Nullable
                public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Unit> p2) {
                    return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                }
            }, 3, null);
        }
    }

    public static /* synthetic */ void cancel$default(Coroutine coroutine, CancellationException cancellationException, int n, Object object) {
        if ((n & 1) != 0) {
            cancellationException = null;
        }
        coroutine.cancel(cancellationException);
    }

    @NotNull
    public final DisposableHandle invokeOnCompletion(@NotNull Function1<? super Throwable, Unit> handler2) {
        Intrinsics.checkNotNullParameter(handler2, "handler");
        return this.job.invokeOnCompletion(handler2);
    }

    private final Job executeInternal(CoroutineContext context, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block) {
        return BuildersKt.launch$default(CoroutineScopeKt.plus(this.scope, Dispatchers.getIO()), null, null, new Function2<CoroutineScope, Continuation<? super Unit>, Object>(this, context, block, null){
            Object L$1;
            int label;
            private /* synthetic */ Object L$0;
            final /* synthetic */ Coroutine<T> this$0;
            final /* synthetic */ CoroutineContext $context;
            final /* synthetic */ Function2<CoroutineScope, Continuation<? super T>, Object> $block;
            {
                this.this$0 = $receiver;
                this.$context = $context;
                this.$block = $block;
                super(2, $completion);
            }

            /*
             * Exception decompiling
             */
            @Nullable
            public final Object invokeSuspend(@NotNull Object var1_1) {
                /*
                 * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                 * 
                 * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [11[SWITCH], 13[CASE]], but top level block is 1[TRYBLOCK]
                 *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
                 *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
                 *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
                 *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
                 *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
                 *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
                 *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
                 *     at org.benf.cfr.reader.entities.Method.dump(Method.java:598)
                 *     at org.benf.cfr.reader.entities.classfilehelpers.ClassFileDumperAnonymousInner.dumpWithArgs(ClassFileDumperAnonymousInner.java:87)
                 *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.ConstructorInvokationAnonymousInner.dumpInner(ConstructorInvokationAnonymousInner.java:82)
                 *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractExpression.dumpWithOuterPrecedence(AbstractExpression.java:142)
                 *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractExpression.dump(AbstractExpression.java:98)
                 *     at org.benf.cfr.reader.state.TypeUsageCollectingDumper.dump(TypeUsageCollectingDumper.java:194)
                 *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.StaticFunctionInvokation.dumpInner(StaticFunctionInvokation.java:143)
                 *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractExpression.dumpWithOuterPrecedence(AbstractExpression.java:142)
                 *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractExpression.dump(AbstractExpression.java:98)
                 *     at org.benf.cfr.reader.state.TypeUsageCollectingDumper.dump(TypeUsageCollectingDumper.java:194)
                 *     at org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredReturn.dump(StructuredReturn.java:60)
                 *     at org.benf.cfr.reader.state.TypeUsageCollectingDumper.dump(TypeUsageCollectingDumper.java:194)
                 *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.dump(Op04StructuredStatement.java:220)
                 *     at org.benf.cfr.reader.bytecode.analysis.structured.statement.Block.dump(Block.java:564)
                 *     at org.benf.cfr.reader.state.TypeUsageCollectingDumper.dump(TypeUsageCollectingDumper.java:194)
                 *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.dump(Op04StructuredStatement.java:220)
                 *     at org.benf.cfr.reader.entities.attributes.AttributeCode.dump(AttributeCode.java:135)
                 *     at org.benf.cfr.reader.state.TypeUsageCollectingDumper.dump(TypeUsageCollectingDumper.java:194)
                 *     at org.benf.cfr.reader.entities.Method.dump(Method.java:627)
                 *     at org.benf.cfr.reader.entities.classfilehelpers.AbstractClassFileDumper.dumpMethods(AbstractClassFileDumper.java:211)
                 *     at org.benf.cfr.reader.entities.classfilehelpers.ClassFileDumperNormal.dump(ClassFileDumperNormal.java:70)
                 *     at org.benf.cfr.reader.entities.ClassFile.dump(ClassFile.java:1167)
                 *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:952)
                 *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
                 *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
                 *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
                 *     at org.benf.cfr.reader.Main.main(Main.java:54)
                 */
                throw new IllegalStateException("Decompilation failed");
            }

            @NotNull
            public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                Function2<CoroutineScope, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                function2.L$0 = value;
                return (Continuation)((Object)function2);
            }

            @Nullable
            public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Unit> p2) {
                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
            }
        }, 3, null);
    }

    private final Object dispatchVoidCallback(CoroutineScope scope, VoidCallback callback, Continuation<? super Unit> $completion) {
        boolean $i$f$dispatchVoidCallback = false;
        if (callback.getContext() == null) {
            Function2<CoroutineScope, Continuation<? super Unit>, Object> function2 = callback.getBlock();
            InlineMarker.mark(0);
            function2.invoke(scope, $completion);
            InlineMarker.mark(1);
            return Unit.INSTANCE;
        }
        CoroutineContext coroutineContext2 = scope.getCoroutineContext().plus(callback.getContext());
        Function2 function2 = new Function2<CoroutineScope, Continuation<? super Unit>, Object>(callback, null){
            int label;
            private /* synthetic */ Object L$0;
            final /* synthetic */ VoidCallback $callback;
            {
                this.$callback = $callback;
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
                        CoroutineScope $this$withContext = (CoroutineScope)this.L$0;
                        this.label = 1;
                        Object object3 = this.$callback.getBlock().invoke($this$withContext, this);
                        if (object3 != object2) return Unit.INSTANCE;
                        return object2;
                    }
                    case 1: {
                        void $result;
                        ResultKt.throwOnFailure($result);
                        Object object3 = $result;
                        return Unit.INSTANCE;
                    }
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            @NotNull
            public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                Function2<CoroutineScope, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                function2.L$0 = value;
                return (Continuation)((Object)function2);
            }

            @Nullable
            public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Unit> p2) {
                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
            }
        };
        InlineMarker.mark(0);
        BuildersKt.withContext(coroutineContext2, function2, $completion);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }

    private final <R> Object dispatchCallback(CoroutineScope scope, R value, Callback<R> callback, Continuation<? super Unit> $completion) {
        boolean $i$f$dispatchCallback = false;
        if (!CoroutineScopeKt.isActive(scope)) {
            return Unit.INSTANCE;
        }
        if (callback.getContext() == null) {
            Function3<CoroutineScope, R, Continuation<Unit>, Object> function3 = callback.getBlock();
            InlineMarker.mark(0);
            function3.invoke(scope, value, $completion);
            InlineMarker.mark(1);
            return Unit.INSTANCE;
        }
        CoroutineContext coroutineContext2 = scope.getCoroutineContext().plus(callback.getContext());
        Function2 function2 = new Function2<CoroutineScope, Continuation<? super Unit>, Object>(callback, value, null){
            int label;
            private /* synthetic */ Object L$0;
            final /* synthetic */ Callback<R> $callback;
            final /* synthetic */ R $value;
            {
                this.$callback = $callback;
                this.$value = $value;
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
                        CoroutineScope $this$withContext = (CoroutineScope)this.L$0;
                        this.label = 1;
                        Object object3 = this.$callback.getBlock().invoke($this$withContext, this.$value, this);
                        if (object3 != object2) return Unit.INSTANCE;
                        return object2;
                    }
                    case 1: {
                        void $result;
                        ResultKt.throwOnFailure($result);
                        Object object3 = $result;
                        return Unit.INSTANCE;
                    }
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            @NotNull
            public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                Function2<CoroutineScope, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                function2.L$0 = value;
                return (Continuation)((Object)function2);
            }

            @Nullable
            public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super Unit> p2) {
                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
            }
        };
        InlineMarker.mark(0);
        BuildersKt.withContext(coroutineContext2, function2, $completion);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }

    private final Object executeBlock(CoroutineScope scope, CoroutineContext context, long timeMillis, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block, Continuation<? super T> $completion) {
        boolean $i$f$executeBlock = false;
        CoroutineContext coroutineContext2 = scope.getCoroutineContext().plus(context);
        Function2 function2 = new Function2<CoroutineScope, Continuation<? super T>, Object>(timeMillis, block, null){
            int label;
            private /* synthetic */ Object L$0;
            final /* synthetic */ long $timeMillis;
            final /* synthetic */ Function2<CoroutineScope, Continuation<? super T>, Object> $block;
            {
                this.$timeMillis = $timeMillis;
                this.$block = $block;
                super(2, $completion);
            }

            /*
             * WARNING - void declaration
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Nullable
            public final Object invokeSuspend(@NotNull Object object) {
                void $result;
                Object object2;
                CoroutineScope $this$withContext;
                Object object3 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0: {
                        ResultKt.throwOnFailure(object);
                        $this$withContext = (CoroutineScope)this.L$0;
                        if (this.$timeMillis <= 0L) break;
                        this.label = 1;
                        object2 = TimeoutKt.withTimeout(this.$timeMillis, (Function2)new Function2<CoroutineScope, Continuation<? super T>, Object>(this.$block, null){
                            int label;
                            private /* synthetic */ Object L$0;
                            final /* synthetic */ Function2<CoroutineScope, Continuation<? super T>, Object> $block;
                            {
                                this.$block = $block;
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
                                        CoroutineScope $this$withTimeout = (CoroutineScope)this.L$0;
                                        this.label = 1;
                                        Object object3 = this.$block.invoke($this$withTimeout, this);
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
                                Function2<CoroutineScope, Continuation<? super T>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                                function2.L$0 = value;
                                return (Continuation)((Object)function2);
                            }

                            @Nullable
                            public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super T> p2) {
                                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
                            }
                        }, this);
                        if (object2 != object3) return object2;
                        return object3;
                    }
                    case 1: {
                        ResultKt.throwOnFailure($result);
                        object2 = $result;
                        return object2;
                    }
                }
                this.label = 2;
                object2 = this.$block.invoke($this$withContext, this);
                if (object2 != object3) return object2;
                return object3;
                {
                    case 2: {
                        ResultKt.throwOnFailure($result);
                        object2 = $result;
                        return object2;
                    }
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            @NotNull
            public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                Function2<CoroutineScope, Continuation<? super T>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                function2.L$0 = value;
                return (Continuation)((Object)function2);
            }

            @Nullable
            public final Object invoke(@NotNull CoroutineScope p1, @Nullable Continuation<? super T> p2) {
                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
            }
        };
        InlineMarker.mark(0);
        Object object = BuildersKt.withContext(coroutineContext2, function2, $completion);
        InlineMarker.mark(1);
        return object;
    }

    public static final /* synthetic */ VoidCallback access$getStart$p(Coroutine $this) {
        return $this.start;
    }

    public static final /* synthetic */ Long access$getTimeMillis$p(Coroutine $this) {
        return $this.timeMillis;
    }

    public static final /* synthetic */ Callback access$getSuccess$p(Coroutine $this) {
        return $this.success;
    }

    public static final /* synthetic */ Result access$getErrorReturn$p(Coroutine $this) {
        return $this.errorReturn;
    }

    public static final /* synthetic */ Callback access$getError$p(Coroutine $this) {
        return $this.error;
    }

    public static final /* synthetic */ VoidCallback access$getFinally$p(Coroutine $this) {
        return $this.finally;
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002JW\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0001\u0010\u00072\b\b\u0002\u0010\b\u001a\u00020\u00042\b\b\u0002\u0010\t\u001a\u00020\n2'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\r\u0012\u0006\u0012\u0004\u0018\u00010\u00010\f\u00a2\u0006\u0002\b\u000e\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0010"}, d2={"Lio/legado/app/help/coroutine/Coroutine$Companion;", "", "()V", "DEFAULT", "Lkotlinx/coroutines/CoroutineScope;", "async", "Lio/legado/app/help/coroutine/Coroutine;", "T", "scope", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Lio/legado/app/help/coroutine/Coroutine;", "reader-pro"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final <T> Coroutine<T> async(@NotNull CoroutineScope scope, @NotNull CoroutineContext context, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block) {
            Intrinsics.checkNotNullParameter(scope, "scope");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(block, "block");
            return new Coroutine(scope, context, block);
        }

        public static /* synthetic */ Coroutine async$default(Companion companion, CoroutineScope coroutineScope, CoroutineContext coroutineContext2, Function2 function2, int n, Object object) {
            if ((n & 1) != 0) {
                coroutineScope = DEFAULT;
            }
            if ((n & 2) != 0) {
                coroutineContext2 = Dispatchers.getIO();
            }
            return companion.async(coroutineScope, coroutineContext2, function2);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\u00020\u0002B\u000f\u0012\b\u0010\u0003\u001a\u0004\u0018\u00018\u0001\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u0004\u0018\u00018\u0001H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0006J \u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00018\u0001H\u00c6\u0001\u00a2\u0006\u0002\u0010\nJ\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0002H\u00d6\u0003J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001R\u0015\u0010\u0003\u001a\u0004\u0018\u00018\u0001\u00a2\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0012"}, d2={"Lio/legado/app/help/coroutine/Coroutine$Result;", "T", "", "value", "(Ljava/lang/Object;)V", "getValue", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "copy", "(Ljava/lang/Object;)Lio/legado/app/help/coroutine/Coroutine$Result;", "equals", "", "other", "hashCode", "", "toString", "", "reader-pro"})
    private static final class Result<T> {
        @Nullable
        private final T value;

        public Result(@Nullable T value) {
            this.value = value;
        }

        @Nullable
        public final T getValue() {
            return this.value;
        }

        @Nullable
        public final T component1() {
            return this.value;
        }

        @NotNull
        public final Result<T> copy(@Nullable T value) {
            return new Result<T>(value);
        }

        public static /* synthetic */ Result copy$default(Result result2, Object object, int n, Object object2) {
            if ((n & 1) != 0) {
                object = result2.value;
            }
            return result2.copy(object);
        }

        @NotNull
        public String toString() {
            return "Result(value=" + this.value + ')';
        }

        public int hashCode() {
            return this.value == null ? 0 : this.value.hashCode();
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Result)) {
                return false;
            }
            Result result2 = (Result)other;
            return Intrinsics.areEqual(this.value, result2.value);
        }
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0082\u0004\u0018\u00002\u00020\u0001B;\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012'\u0010\u0004\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0005\u00a2\u0006\u0002\b\t\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nR7\u0010\u0004\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0005\u00a2\u0006\u0002\b\t\u00f8\u0001\u0000\u00a2\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0010"}, d2={"Lio/legado/app/help/coroutine/Coroutine$VoidCallback;", "", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lio/legado/app/help/coroutine/Coroutine;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)V", "getBlock", "()Lkotlin/jvm/functions/Function2;", "Lkotlin/jvm/functions/Function2;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "reader-pro"})
    private final class VoidCallback {
        @Nullable
        private final CoroutineContext context;
        @NotNull
        private final Function2<CoroutineScope, Continuation<? super Unit>, Object> block;

        public VoidCallback(@NotNull CoroutineContext context, Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> block) {
            Intrinsics.checkNotNullParameter(Coroutine.this, "this$0");
            Intrinsics.checkNotNullParameter(block, "block");
            this.context = context;
            this.block = block;
        }

        @Nullable
        public final CoroutineContext getContext() {
            return this.context;
        }

        @NotNull
        public final Function2<CoroutineScope, Continuation<? super Unit>, Object> getBlock() {
            return this.block;
        }
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0082\u0004\u0018\u0000*\u0004\b\u0001\u0010\u00012\u00020\u0002BA\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012-\u0010\u0005\u001a)\b\u0001\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00028\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0006\u00a2\u0006\u0002\b\n\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bR=\u0010\u0005\u001a)\b\u0001\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00028\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0006\u00a2\u0006\u0002\b\n\u00f8\u0001\u0000\u00a2\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0011"}, d2={"Lio/legado/app/help/coroutine/Coroutine$Callback;", "VALUE", "", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Lkotlin/Function3;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lio/legado/app/help/coroutine/Coroutine;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function3;)V", "getBlock", "()Lkotlin/jvm/functions/Function3;", "Lkotlin/jvm/functions/Function3;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "reader-pro"})
    private final class Callback<VALUE> {
        @Nullable
        private final CoroutineContext context;
        @NotNull
        private final Function3<CoroutineScope, VALUE, Continuation<? super Unit>, Object> block;

        public Callback(@NotNull CoroutineContext context, Function3<? super CoroutineScope, ? super VALUE, ? super Continuation<? super Unit>, ? extends Object> block) {
            Intrinsics.checkNotNullParameter(Coroutine.this, "this$0");
            Intrinsics.checkNotNullParameter(block, "block");
            this.context = context;
            this.block = block;
        }

        @Nullable
        public final CoroutineContext getContext() {
            return this.context;
        }

        @NotNull
        public final Function3<CoroutineScope, VALUE, Continuation<? super Unit>, Object> getBlock() {
            return this.block;
        }
    }
}

