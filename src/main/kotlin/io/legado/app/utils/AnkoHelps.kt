// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.utils

data class AttemptResult<T> @PublishedApi internal constructor(
    val value: T?,
    val error: Throwable?
) {

    @Suppress("UNCHECKED_CAST")
    inline fun <R> then(f: (T) -> R): AttemptResult<R> {
        if (error != null) return this as AttemptResult<R>
        return attempt { f(value as T) }
    }

    val isError: Boolean get() = error != null

    val hasValue: Boolean get() = error == null
}

inline fun <T> attempt(f: () -> T): AttemptResult<T> {
    var value: T? = null
    var error: Throwable? = null
    try {
        value = f()
    } catch (t: Throwable) {
        error = t
    }
    return AttemptResult(value, error)
}
