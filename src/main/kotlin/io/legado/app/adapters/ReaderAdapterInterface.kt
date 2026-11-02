// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.adapters

import io.legado.app.help.http.StrResponse
import io.legado.app.model.DebugLog

interface ReaderAdapterInterface {

    fun getWorkDir(vararg subDirFiles: String): String

    fun getWorkDir(subPath: String = ""): String

    fun getCacheDir(): String

    suspend fun getStrResponseByRemoteWebview(
        url: String? = null,
        html: String? = null,
        encode: String? = null,
        tag: String? = null,
        headerMap: Map<String, String>? = null,
        sourceRegex: String? = null,
        javaScript: String? = null,
        proxy: String? = null,
        post: Boolean = false,
        body: String? = null,
        userNameSpace: String = "",
        debugLog: DebugLog? = null
    ): StrResponse?
}
