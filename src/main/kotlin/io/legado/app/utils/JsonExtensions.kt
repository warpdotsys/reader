// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.utils

import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.Option
import com.jayway.jsonpath.ParseContext
import com.jayway.jsonpath.ReadContext

val jsonPath: ParseContext by lazy {
    JsonPath.using(Configuration.builder().options(Option.SUPPRESS_EXCEPTIONS).build())
}

fun ReadContext.readString(path: String): String? = read(path, String::class.java)

fun ReadContext.readBool(path: String): Boolean? =
    read(path, Boolean::class.javaPrimitiveType)

fun ReadContext.readInt(path: String): Int? =
    read(path, Int::class.javaPrimitiveType)

fun ReadContext.readLong(path: String): Long? =
    read(path, Long::class.javaPrimitiveType)
