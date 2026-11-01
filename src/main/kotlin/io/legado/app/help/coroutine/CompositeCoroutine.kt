// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.help.coroutine

import java.util.HashSet

class CompositeCoroutine : CoroutineContainer {

    private var resources: HashSet<Coroutine<*>>? = null

    val size: Int
        get() = resources?.size ?: 0

    val isEmpty: Boolean
        get() = size == 0

    constructor()

    constructor(vararg coroutines: Coroutine<*>) {
        resources = hashSetOf(*coroutines)
    }

    constructor(coroutines: Iterable<Coroutine<*>>) {
        resources = hashSetOf()
        for (coroutine in coroutines) {
            resources?.add(coroutine)
        }
    }

    override fun add(coroutine: Coroutine<*>): Boolean {
        synchronized(this) {
            var set = resources
            if (resources == null) {
                set = hashSetOf()
                resources = set
            }
            return set!!.add(coroutine)
        }
    }

    override fun addAll(vararg coroutines: Coroutine<*>): Boolean {
        synchronized(this) {
            var set = resources
            if (resources == null) {
                set = hashSetOf()
                resources = set
            }
            for (coroutine in coroutines) {
                val add = set!!.add(coroutine)
                if (add) {
                    continue
                }
                return false
            }
        }
        return true
    }

    override fun remove(coroutine: Coroutine<*>): Boolean {
        if (delete(coroutine)) {
            coroutine.cancel()
            return true
        }
        return false
    }

    override fun delete(coroutine: Coroutine<*>): Boolean {
        synchronized(this) {
            val set = resources
            if (set == null || !set.remove(coroutine)) {
                return false
            }
        }
        return true
    }

    override fun clear() {
        val set: HashSet<Coroutine<*>>?
        synchronized(this) {
            set = resources
            resources = null
        }
        set?.forEachIndexed { _, coroutine ->
            coroutine.cancel()
        }
    }
}
