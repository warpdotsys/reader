// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.db

import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject

open class DB<T>(val userNameSpace: String, val name: String) {

    var cachedValue: JsonArray = JsonArray()

    open fun readAll(): JsonArray {
        return JsonArray()
    }

    open fun <P> findBy(field: String, value: P, clazz: Class<T>): T? {
        return null
    }

    open fun save(
        value: T,
        onCheckEnd: ((T, Boolean, JsonArray) -> Unit)?,
        checker: (JsonObject, T) -> Boolean
    ) {
    }

    open fun saveMulti(
        value: Array<T>,
        onCheckEnd: ((T, Boolean, JsonArray) -> Unit)?,
        checker: (JsonObject, T) -> Boolean
    ) {
    }

    open fun delete(checker: (JsonObject) -> Boolean) {
    }

    open fun save() {
    }

    companion object {
        fun <T> table(userNameSpace: String, name: String, driver: String = "JSON"): DB<T> {
            return if (driver == "SQL") {
                SQLTable(userNameSpace, name)
            } else {
                JSONTable(userNameSpace, name)
            }
        }
    }
}
