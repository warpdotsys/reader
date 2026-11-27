// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.utils

import java.util.concurrent.ConcurrentHashMap

class LRUCache<K, V>(size: Int) {

    private var cacheCapacity: Int = size
    private var caches = ConcurrentHashMap<K, CacheNode>(size)
    private var first: CacheNode? = null
    private var last: CacheNode? = null

    fun put(k: K, v: V) {
        var node = caches[k]
        if (node == null) {
            if (caches.size >= cacheCapacity) {
                last?.key?.let { caches.remove(it) }
                removeLast()
            }
            node = CacheNode()
            node.key = k
        }
        node.value = v
        moveToFirst(node)
        caches[k] = node
    }

    fun get(k: K): V? {
        val node = caches[k] ?: return null
        moveToFirst(node)
        return node.value
    }

    fun remove(k: K): CacheNode? {
        val node = caches[k]
        if (node != null) {
            node.pre?.next = node.next
            node.next?.pre = node.pre
            if (node == first) {
                first = node.next
            }
            if (node == last) {
                last = node.pre
            }
        }
        return caches.remove(k)
    }

    private fun moveToFirst(node: CacheNode) {
        if (first == node) {
            return
        }
        node.next?.pre = node.pre
        node.pre?.next = node.next
        if (node == last) {
            last = last?.pre
        }
        if (first == null || last == null) {
            first = node
            last = node
            return
        }
        node.next = first
        first?.pre = node
        first = node
        first?.pre = null
    }

    private fun removeLast() {
        if (last != null) {
            last = last?.pre
            if (last == null) {
                first = null
            } else {
                last?.next = null
            }
        }
    }

    fun clear() {
        first = null
        last = null
        caches.clear()
    }

    override fun toString(): String {
        val sb = StringBuilder()
        var node = first
        while (node != null) {
            sb.append(String.format("%s:%s ", node.key, node.value))
            node = node.next
        }
        return sb.toString()
    }

    inner class CacheNode {
        var pre: CacheNode? = null
        var next: CacheNode? = null
        var key: K? = null
        var value: V? = null
    }
}
