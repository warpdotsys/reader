// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class IntJsonDeserializer : JsonDeserializer<Int?> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Int? {
        if (json.isJsonPrimitive) {
            val prim = json.asJsonPrimitive
            return if (prim.isNumber) prim.asNumber.toInt() else null
        }
        return null
    }
}
