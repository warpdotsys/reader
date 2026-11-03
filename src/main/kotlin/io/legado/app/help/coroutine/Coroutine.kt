// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.help.coroutine

import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext

class Coroutine<T> constructor(
    val scope: CoroutineScope,
    context: CoroutineContext = Dispatchers.IO,
    block: suspend CoroutineScope.() -> T
) {

    private val job: Job = executeInternal(context, block)

    private var start: VoidCallback? = null
    private var success: Callback<T>? = null
    private var error: Callback<Throwable>? = null
    private var finally: VoidCallback? = null
    private var cancel: VoidCallback? = null

    private var timeMillis: Long? = null
    private var errorReturn: Result<out T>? = null

    val isCancelled: Boolean
        get() = job.isCancelled

    val isActive: Boolean
        get() = job.isActive

    val isCompleted: Boolean
        get() = job.isCompleted

    fun timeout(timeMillis: () -> Long): Coroutine<T> {
        this.timeMillis = timeMillis.invoke()
        return this
    }

    fun timeout(timeMillis: Long): Coroutine<T> {
        this.timeMillis = timeMillis
        return this
    }

    fun onErrorReturn(value: () -> T): Coroutine<T> {
        errorReturn = Result(value.invoke())
        return this
    }

    fun onErrorReturn(value: T): Coroutine<T> {
        errorReturn = Result(value)
        return this
    }

    fun onStart(
        context: CoroutineContext? = null,
        block: suspend CoroutineScope.() -> Unit
    ): Coroutine<T> {
        start = VoidCallback(context, block)
        return this
    }

    fun onSuccess(
        context: CoroutineContext? = null,
        block: suspend CoroutineScope.(T) -> Unit
    ): Coroutine<T> {
        success = Callback(context, block)
        return this
    }

    fun onError(
        context: CoroutineContext? = null,
        block: suspend CoroutineScope.(Throwable) -> Unit
    ): Coroutine<T> {
        error = Callback(context, block)
        return this
    }

    fun onFinally(
        context: CoroutineContext? = null,
        block: suspend CoroutineScope.() -> Unit
    ): Coroutine<T> {
        finally = VoidCallback(context, block)
        return this
    }

    fun onCancel(
        context: CoroutineContext? = null,
        block: suspend CoroutineScope.() -> Unit
    ): Coroutine<T> {
        cancel = VoidCallback(context, block)
        return this
    }

    fun cancel(cause: CancellationException? = null) {
        job.cancel(cause)
        cancel?.let {
            MainScope().launch {
                if (it.context == null) {
                    it.block(scope)
                } else {
                    withContext(scope.coroutineContext + it.context) {
                        it.block(this)
                    }
                }
            }
        }
    }

    fun invokeOnCompletion(handler: CompletionHandler): DisposableHandle {
        return job.invokeOnCompletion(handler)
    }

    private fun executeInternal(
        context: CoroutineContext,
        block: suspend CoroutineScope.() -> T
    ): Job = scope.plus(Dispatchers.IO).launch {
        try {
            start?.let { dispatchVoidCallback(this, it) }
            val result = executeBlock(this, context, timeMillis ?: 0L, block)
            if (isActive) {
                success?.let { dispatchCallback(this, result, it) }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            errorReturn?.value?.let { value ->
                if (isActive) {
                    success?.let { dispatchCallback(this, value, it) }
                }
            }
            if (isActive) {
                error?.let { dispatchCallback(this, e, it) }
            }
        } finally {
            if (isActive) {
                finally?.let { dispatchVoidCallback(this, it) }
            }
        }
    }

    private inline suspend fun dispatchVoidCallback(scope: CoroutineScope, callback: VoidCallback) {
        if (callback.context == null) {
            callback.block.invoke(scope)
            return
        }
        withContext(scope.coroutineContext.plus(callback.context)) {
            callback.block.invoke(this)
        }
    }

    private inline suspend fun <R> dispatchCallback(
        scope: CoroutineScope,
        value: R,
        callback: Callback<R>
    ) {
        if (!scope.isActive) {
            return
        }
        if (callback.context == null) {
            callback.block.invoke(scope, value)
            return
        }
        withContext(scope.coroutineContext.plus(callback.context)) {
            callback.block.invoke(this, value)
        }
    }

    private inline suspend fun executeBlock(
        scope: CoroutineScope,
        context: CoroutineContext,
        timeMillis: Long,
        crossinline block: suspend CoroutineScope.() -> T
    ): T {
        val context2 = scope.coroutineContext.plus(context)
        return withContext(context2) {
            if (timeMillis > 0) {
                withTimeout(timeMillis) {
                    block.invoke(this)
                }
            } else {
                block.invoke(this)
            }
        }
    }

    companion object {
        private val DEFAULT = MainScope()

        fun <T> async(
            scope: CoroutineScope = DEFAULT,
            context: CoroutineContext = Dispatchers.IO,
            block: suspend CoroutineScope.() -> T
        ): Coroutine<T> = Coroutine(scope, context, block)
    }

    private data class Result<T>(val value: T?)

    private inner class VoidCallback(
        val context: CoroutineContext?,
        val block: suspend CoroutineScope.() -> Unit
    )

    private inner class Callback<VALUE>(
        val context: CoroutineContext?,
        val block: suspend CoroutineScope.(VALUE) -> Unit
    )
}
