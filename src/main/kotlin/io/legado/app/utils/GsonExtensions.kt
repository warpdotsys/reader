// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonWriter
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.lang.reflect.Type

val GSON: Gson by lazy {
    GsonBuilder()
        .registerTypeAdapter(
            object : TypeToken<Map<String, Any?>>() {}.type,
            MapDeserializerDoubleAsIntFix()
        )
        .registerTypeAdapter(Int::class.javaPrimitiveType, IntJsonDeserializer())
        .disableHtmlEscaping()
        .setPrettyPrinting()
        .create()
}

inline fun <reified T> genericType(): Type = object : TypeToken<T>() {}.type

inline fun <reified T> Gson.fromJsonObject(json: String?): Result<T?> =
    runCatching { fromJson<T>(json, genericType<T>()) }

inline fun <reified T> Gson.fromJsonArray(json: String?): Result<List<T>?> =
    runCatching { fromJson<List<T>>(json, ParameterizedTypeImpl(T::class.java)) }

inline fun <reified T> Gson.fromJsonObject(inputStream: InputStream?): Result<T?> =
    runCatching {
        val reader = InputStreamReader(inputStream)
        fromJson<T>(reader, genericType<T>())
    }

inline fun <reified T> Gson.fromJsonArray(inputStream: InputStream?): Result<List<T>?> =
    runCatching {
        val reader = InputStreamReader(inputStream)
        fromJson<List<T>>(reader, ParameterizedTypeImpl(T::class.java))
    }

fun Gson.writeToOutputStream(out: OutputStream, any: Any) {
    val writer = JsonWriter(OutputStreamWriter(out, "UTF-8"))
    writer.setIndent("  ")
    if (any is List<*>) {
        writer.beginArray()
        any.forEach { item ->
            item?.let { toJson(it, it::class.java, writer) }
        }
        writer.endArray()
    } else {
        toJson(any, any::class.java, writer)
    }
    writer.close()
}
