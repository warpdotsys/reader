// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.help.coroutine

interface CoroutineContainer {
    fun add(coroutine: Coroutine<*>): Boolean
    fun addAll(vararg coroutines: Coroutine<*>): Boolean
    fun remove(coroutine: Coroutine<*>): Boolean
    fun delete(coroutine: Coroutine<*>): Boolean
    fun clear()
}
