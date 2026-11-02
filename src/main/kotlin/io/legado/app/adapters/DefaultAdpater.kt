// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.adapters

import io.legado.app.help.http.StrResponse
import io.legado.app.model.DebugLog
import java.io.File
import java.nio.file.Paths

class DefaultAdpater : ReaderAdapterInterface {

    override fun getWorkDir(subPath: String): String {
        val workDirPath: String
        val osName = System.getProperty("os.name")
        val currentDir = System.getProperty("user.dir")
        workDirPath = if (osName.startsWith("Mac OS", true) && !currentDir.startsWith("/Users/")) {
            Paths.get(System.getProperty("user.home"), ".reader").toString()
        } else {
            currentDir
        }
        return Paths.get(workDirPath, subPath).toString()
    }

    override fun getWorkDir(vararg subDirFiles: String): String {
        return getWorkDir(getRelativePath(*subDirFiles))
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
        throw Exception("不支持webview")
    }
}
