// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.db

import com.htmake.reader.utils.asJsonArray
import com.htmake.reader.utils.getStorage
import com.htmake.reader.utils.saveStorage
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import mu.KLogger
import mu.KotlinLogging
import java.util.ArrayList

private val logger: KLogger = KotlinLogging.logger { }

class SQLTable<T>(userNameSpace: String, name: String) : DB<T>(userNameSpace, name) {

    override fun readAll(): JsonArray {
        var dataList = asJsonArray(getStorage("data", userNameSpace, name))
        if (dataList == null) {
            dataList = JsonArray()
        }
        cachedValue = dataList
        return dataList
    }

    override fun <P : Any> findBy(field: String, value: P, clazz: Class<T>): T? {
        val dataList = readAll()
        for (i in 0 until dataList.size()) {
            val json = dataList.getJsonObject(i)
            if (json.getValue(field).equals(value)) {
                return json.mapTo(clazz)
            }
        }
        return null
    }

    override fun save(
        value: T,
        onCheckEnd: ((T, Boolean, JsonArray) -> Unit)?,
        checker: (JsonObject, T) -> Boolean
    ) {
        var dataList = readAll()
        var existIndex = -1
        for (i in 0 until dataList.size()) {
            val jsonObject = dataList.getJsonObject(i)
            if (checker(jsonObject, value)) {
                existIndex = i
                break
            }
        }
        onCheckEnd?.invoke(value, existIndex >= 0, dataList)
        if (existIndex >= 0) {
            @Suppress("UNCHECKED_CAST")
            val list = dataList.list as MutableList<Any?>
            list[existIndex] = JsonObject.mapFrom(value)
            dataList = JsonArray(list)
        } else {
            dataList.add(JsonObject.mapFrom(value))
        }
        cachedValue = dataList
        save()
    }

    override fun saveMulti(
        value: Array<T>,
        onCheckEnd: ((T, Boolean, JsonArray) -> Unit)?,
        checker: (JsonObject, T) -> Boolean
    ) {
        var dataList = readAll()
        var existIndex = -1
        for (j in value) {
            for (i in 0 until dataList.size()) {
                val jsonObject = dataList.getJsonObject(i)
                if (checker(jsonObject, j)) {
                    existIndex = i
                    break
                }
            }
            onCheckEnd?.invoke(j, existIndex >= 0, dataList)
            if (existIndex >= 0) {
                @Suppress("UNCHECKED_CAST")
                val list = dataList.list as MutableList<Any?>
                list[existIndex] = JsonObject.mapFrom(j)
                dataList = JsonArray(list)
            } else {
                dataList.add(JsonObject.mapFrom(j))
            }
        }
        cachedValue = dataList
        save()
    }

    override fun delete(checker: (JsonObject) -> Boolean) {
        var dataList = readAll()
        val removeIndexList = ArrayList<Any>()
        for (i in 0 until dataList.size()) {
            val jsonObject = dataList.getJsonObject(i)
            if (checker(jsonObject)) {
                removeIndexList.add(i)
            }
        }
        if (removeIndexList.size > 0) {
            for (it in removeIndexList) {
                dataList.remove((it as Number).toInt())
            }
        }
        cachedValue = dataList
        save()
    }

    override fun save() {
        saveStorage("data", userNameSpace, name, value = cachedValue)
    }
}
