// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.utils

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object UserMutex {

    val mutex: Mutex = Mutex()

    val lockerMap: LRUCache<String, Mutex> = LRUCache(10)

    suspend fun getLocker(lockKey: String): Mutex {
        return mutex.withLock {
            if (lockerMap.get(lockKey) == null) {
                lockerMap.put(lockKey, Mutex())
            }
            lockerMap.get(lockKey)!!
        }
    }
}
