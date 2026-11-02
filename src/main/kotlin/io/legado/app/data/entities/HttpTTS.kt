// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.data.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import io.legado.app.model.DebugLog
import kotlin.jvm.Transient

@JsonIgnoreProperties(value = ["headerMap", "source", "_userNameSpace", "userNameSpace"])
data class HttpTTS(
    val id: Long = 0,
    var name: String = "",
    var url: String = "",
    var contentType: String? = null,
    override var concurrentRate: String? = null,
    override var loginUrl: String? = null,
    override var loginUi: String? = null,
    override var header: String? = null,
    var jsLib: String? = null,
    override var enabledCookieJar: Boolean? = false,
    var loginCheckJs: String? = null,
    var lastUpdateTime: Long = 0
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
                    id = doc.read("$.id"),
                    name = doc.read("$.name"),
                    url = doc.read("$.url"),
                    contentType = doc.read("$.contentType"),
                    concurrentRate = doc.read("$.concurrentRate"),
                    loginUrl = doc.read("$.loginUrl"),
                    loginUi = doc.read("$.loginUi"),
                    header = doc.read("$.header"),
                    loginCheckJs = doc.read("$.loginCheckJs")
                )
            }
        }

        fun fromJson(json: String): Result<HttpTTS> {
            return fromJsonDoc(JsonPath.parse(json))
        }

        fun fromJsonArray(jsonArray: String): Result<List<HttpTTS>> {
            return runCatching {
                val list = arrayListOf<HttpTTS>()
                JsonPath.parse(jsonArray).read<List<Map<String, Any>>>("$").forEach {
                    list.add(fromJsonDoc(JsonPath.parse(it)).getOrThrow())
                }
                list
            }
        }
    }
}
