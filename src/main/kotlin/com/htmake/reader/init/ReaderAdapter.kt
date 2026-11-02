// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.init

import com.htmake.reader.utils.RemoteWebview
import com.htmake.reader.utils.getWorkDir as extGetWorkDir
import io.legado.app.adapters.ReaderAdapterInterface
import io.legado.app.help.http.StrResponse
import io.legado.app.model.DebugLog
import java.io.File

object ReaderAdapter : ReaderAdapterInterface {

    override fun getWorkDir(subPath: String): String {
        return extGetWorkDir(subPath)
    }

    override fun getWorkDir(vararg subDirFiles: String): String {
        return extGetWorkDir(getRelativePath(*subDirFiles))
    }

    fun getRelativePath(vararg subDirFiles: String): String {
        val path = StringBuilder("")
        subDirFiles.forEach {
            if (it.length > 0) {
                path.append(File.separator).append(it)
            }
        }
        val it = path.toString()
        return if (it.startsWith("/")) it.substring(1) else it
    }

    override fun getCacheDir(): String {
        return getWorkDir("storage", "cache")
    }

    override suspend fun getStrResponseByRemoteWebview(
        url: String?,
        html: String?,
        encode: String?,
        tag: String?,
        headerMap: Map<String, String>?,
        sourceRegex: String?,
        javaScript: String?,
        proxy: String?,
        post: Boolean,
        body: String?,
        userNameSpace: String,
        debugLog: DebugLog?
    ): StrResponse? {
        var encodeStr = encode
        if (encode.isNullOrEmpty()) {
            encodeStr = headerMap?.get("charset")
        }
        return RemoteWebview.getStrResponse(
            url, html, encodeStr, tag, headerMap, sourceRegex, javaScript, proxy,
            post, body, userNameSpace, debugLog
        )
    }
}
