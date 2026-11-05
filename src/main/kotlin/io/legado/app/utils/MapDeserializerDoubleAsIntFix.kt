// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.internal.LinkedTreeMap
import java.lang.reflect.Type

class MapDeserializerDoubleAsIntFix : JsonDeserializer<Map<String, Any?>> {

    @Throws(JsonParseException::class)
    override fun deserialize(
        jsonElement: JsonElement,
        type: Type,
        jsonDeserializationContext: JsonDeserializationContext
    ): Map<String, Any?>? {
        val obj = read(jsonElement)
        return if (obj is Map<*, *>) {
            @Suppress("UNCHECKED_CAST")
            obj as Map<String, Any?>
        } else {
            null
        }
    }

    fun read(json: JsonElement): Any? {
        if (json.isJsonArray) {
            val list = ArrayList<Any?>()
            for (anArr in json.asJsonArray) {
                list.add(read(anArr))
            }
            return list
        }
        if (json.isJsonObject) {
            val map = LinkedTreeMap<String, Any?>()
            val obj = json.asJsonObject
            val entitySet = obj.entrySet()
            for (entry in entitySet) {
                val key: String = entry.key
                val value = entry.value
                map.put(key, read(value))
            }
            return map
        }
        if (json.isJsonPrimitive) {
            val prim = json.asJsonPrimitive
            if (prim.isBoolean) return prim.asBoolean
            if (prim.isString) return prim.asString
            if (prim.isNumber) {
                val num = prim.asNumber
                return if (Math.ceil(num.toDouble()) == num.toLong().toDouble()) {
                    num.toLong()
                } else {
                    num.toDouble()
                }
            }
        }
        return null
    }
}
