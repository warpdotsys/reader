// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.data.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import io.legado.app.model.DebugLog
import kotlin.jvm.Transient
import io.legado.app.utils.readLong
import io.legado.app.utils.readString
import io.legado.app.utils.GSON
import io.legado.app.utils.jsonPath

@JsonIgnoreProperties(value = ["headerMap", "source", "_userNameSpace", "userNameSpace"])
data class HttpTTS(
    val id: Long = System.currentTimeMillis(),
    var name: String = "",
    var url: String = "",
    var contentType: String? = null,
    override var concurrentRate: String? = "0",
    override var loginUrl: String? = null,
    override var loginUi: String? = null,
    override var header: String? = null,
    var jsLib: String? = null,
    override var enabledCookieJar: Boolean? = false,
    var loginCheckJs: String? = null,
    var lastUpdateTime: Long = System.currentTimeMillis()
) : BaseSource {

    @Transient
    private var _userNameSpace: String = ""

    @Transient
    private var debugLog: DebugLog? = null

    override val tag: String
        get() = name

    override val key: String
        get() = "httpTts:$id"

    override val userNameSpace: String
        get() = _userNameSpace

    fun setUserNameSpace(nameSpace: String) {
        _userNameSpace = nameSpace
    }

    override val logger: DebugLog?
        get() = debugLog

    fun setLogger(logger: DebugLog?) {
        debugLog = logger
    }

    companion object {

        fun fromJsonDoc(doc: DocumentContext): Result<HttpTTS> {
            return runCatching {
                HttpTTS(
                    id = doc.readLong("$.id") ?: System.currentTimeMillis(),
                    name = doc.readString("$.name")!!,
                    url = doc.readString("$.url")!!,
                    contentType = doc.readString("$.contentType"),
                    concurrentRate = doc.readString("$.concurrentRate"),
                    loginUrl = doc.readString("$.loginUrl"),
                    loginUi = doc.read<Any>("$.loginUi").let {
                        if (it is List<*>) GSON.toJson(it) else it?.toString()
                    },
                    header = doc.readString("$.header"),
                    loginCheckJs = doc.readString("$.loginCheckJs")
                )
            }
        }

        fun fromJson(json: String): Result<HttpTTS> {
            return fromJsonDoc(jsonPath.parse(json))
        }

        fun fromJsonArray(jsonArray: String): Result<List<HttpTTS>> {
            return runCatching {
                val list = arrayListOf<HttpTTS>()
                (jsonPath.parse(jsonArray).read<Any>("$") as List<Map<String, Any>>).forEach {
                    list.add(fromJsonDoc(JsonPath.parse(it)).getOrThrow())
                }
                list
            }
        }
    }
}
