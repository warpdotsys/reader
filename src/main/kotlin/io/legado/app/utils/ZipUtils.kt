// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.ArrayList
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import kotlin.io.use

private val logger = KotlinLogging.logger {}

object ZipUtils {

    suspend fun zipFiles(srcFiles: Collection<String>, zipFilePath: String): Boolean {
        return zipFiles(srcFiles, zipFilePath, null)
    }

    suspend fun zipFiles(
        srcFilePaths: Collection<String>?,
        zipFilePath: String?,
        comment: String?
    ): Boolean = withContext(Dispatchers.IO) {
        if (srcFilePaths == null || zipFilePath == null) {
            return@withContext false
        }
        ZipOutputStream(FileOutputStream(zipFilePath)).use { zos ->
            for (srcFile in srcFilePaths) {
                val file = getFileByPath(srcFile)!!
                if (!zipFile(file, "", zos, comment)) {
                    return@withContext false
                }
            }
            true
        }
    }

    @Throws(IOException::class)
    @JvmOverloads
    fun zipFiles(
        srcFiles: Collection<File>?,
        zipFile: File?,
        comment: String? = null
    ): Boolean {
        if (srcFiles == null || zipFile == null) {
            return false
        }
        ZipOutputStream(FileOutputStream(zipFile)).use { zos ->
            for (file in srcFiles) {
                if (!zipFile(file, "", zos, comment)) {
                    return false
                }
            }
            return true
        }
    }

    @Throws(IOException::class)
    fun zipFile(srcFilePath: String, zipFilePath: String): Boolean {
        return zipFile(getFileByPath(srcFilePath), getFileByPath(zipFilePath), null)
    }

    @Throws(IOException::class)
    fun zipFile(srcFilePath: String, zipFilePath: String, comment: String): Boolean {
        return zipFile(getFileByPath(srcFilePath), getFileByPath(zipFilePath), comment)
    }

    @Throws(IOException::class)
    @JvmOverloads
    fun zipFile(srcFile: File?, zipFile: File?, comment: String? = null): Boolean {
        if (srcFile == null || zipFile == null) {
            return false
        }
        ZipOutputStream(FileOutputStream(zipFile)).use { zos ->
            return zipFile(srcFile, "", zos, comment)
        }
    }

    @Throws(IOException::class)
    private fun zipFile(
        srcFile: File,
        rootPath: String,
        zos: ZipOutputStream,
        comment: String?
    ): Boolean {
        if (!srcFile.exists()) {
            return true
        }
        val rootPath1 = rootPath + (if (isSpace(rootPath)) "" else File.separator) + srcFile.name
        if (srcFile.isDirectory) {
            val fileList = srcFile.listFiles()
            if (fileList == null || fileList.isEmpty()) {
                val entry = ZipEntry("$rootPath1/")
                entry.comment = comment
                zos.putNextEntry(entry)
                zos.closeEntry()
                return true
            }
            for (file in fileList) {
                if (!zipFile(file, rootPath1, zos, comment)) {
                    return false
                }
            }
            return true
        }
        BufferedInputStream(FileInputStream(srcFile)).use { inputStream ->
            val entry = ZipEntry(rootPath1)
            entry.comment = comment
            zos.putNextEntry(entry)
            zos.write(inputStream.readBytes())
            zos.closeEntry()
        }
        return true
    }

    @Throws(IOException::class)
    fun unzipFile(zipFilePath: String, destDirPath: String): List<File>? {
        return unzipFileByKeyword(zipFilePath, destDirPath, null)
    }

    @Throws(IOException::class)
    fun unzipFile(zipFile: File, destDir: File): List<File>? {
        return unzipFileByKeyword(zipFile, destDir, null)
    }

    @Throws(IOException::class)
    fun unzipFileByKeyword(
        zipFilePath: String,
        destDirPath: String,
        keyword: String?
    ): List<File>? {
        return unzipFileByKeyword(getFileByPath(zipFilePath), getFileByPath(destDirPath), keyword)
    }

    @Throws(IOException::class)
    fun unzipFileByKeyword(zipFile: File?, destDir: File?, keyword: String?): List<File>? {
        if (zipFile == null || destDir == null) {
            return null
        }
        val files = ArrayList<File>()
        val zip2 = ZipFile(zipFile)
        val entries = zip2.entries()
        zip2.use {
            if (isSpace(keyword)) {
                while (entries.hasMoreElements()) {
                    val entry = entries.nextElement()!!
                    val entryName = entry.name
                    if (entryName.contains("../")) {
                        logger.error("ZipUtils entryName: $entryName is dangerous!")
                        continue
                    }
                    if (!unzipChildFile(destDir, files, zip2, entry, entryName)) {
                        return files
                    }
                }
            } else {
                while (entries.hasMoreElements()) {
                    val entry = entries.nextElement()!!
                    val entryName = entry.name
                    if (entryName.contains("../")) {
                        logger.error("ZipUtils entryName: $entryName is dangerous!")
                        continue
                    }
                    if (!entryName.contains(keyword!!)) continue
                    if (!unzipChildFile(destDir, files, zip2, entry, entryName)) {
                        return files
                    }
                }
            }
        }
        return files
    }

    @Throws(IOException::class)
    private fun unzipChildFile(
        destDir: File,
        files: MutableList<File>,
        zip2: ZipFile,
        entry: ZipEntry,
        name: String
    ): Boolean {
        val file = File(destDir, name)
        files.add(file)
        if (entry.isDirectory) {
            return createOrExistsDir(file)
        }
        if (!createOrExistsFile(file)) {
            return false
        }
        BufferedInputStream(zip2.getInputStream(entry)).use { inputStream ->
            BufferedOutputStream(FileOutputStream(file)).use { out ->
                out.write(inputStream.readBytes())
            }
        }
        return true
    }

    @Throws(IOException::class)
    fun getFilesPath(zipFilePath: String): List<String>? {
        return getFilesPath(getFileByPath(zipFilePath))
    }

    @Throws(IOException::class)
    fun getFilesPath(zipFile: File?): List<String>? {
        if (zipFile == null) {
            return null
        }
        val paths = ArrayList<String>()
        val zip2 = ZipFile(zipFile)
        val entries = zip2.entries()
        while (entries.hasMoreElements()) {
            val entryName = entries.nextElement()!!.name
            if (entryName.contains("../")) {
                logger.error("ZipUtils entryName: $entryName is dangerous!")
                paths.add(entryName)
                continue
            }
            paths.add(entryName)
        }
        zip2.close()
        return paths
    }

    @Throws(IOException::class)
    fun getComments(zipFilePath: String): List<String?>? {
        return getComments(getFileByPath(zipFilePath))
    }

    @Throws(IOException::class)
    fun getComments(zipFile: File?): List<String?>? {
        if (zipFile == null) {
            return null
        }
        val comments = ArrayList<String?>()
        val zip2 = ZipFile(zipFile)
        val entries = zip2.entries()
        while (entries.hasMoreElements()) {
            val entry = entries.nextElement()!!
            comments.add(entry.comment)
        }
        zip2.close()
        return comments
    }

    private fun createOrExistsDir(file: File?): Boolean {
        return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
    }

    private fun createOrExistsFile(file: File?): Boolean {
        if (file == null) {
            return false
        }
        if (file.exists()) {
            return file.isFile
        }
        if (!createOrExistsDir(file.parentFile)) {
            return false
        }
        return try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    private fun getFileByPath(filePath: String?): File? {
        return if (isSpace(filePath)) null else File(filePath)
    }

    private fun isSpace(s: String?): Boolean {
        if (s == null) {
            return true
        }
        val len = s.length
        for (i in 0 until len) {
            if (Character.isWhitespace(s[i])) continue
            return false
        }
        return true
    }
}
