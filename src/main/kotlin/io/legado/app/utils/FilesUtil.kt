// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.utils

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.Closeable
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Collections
import java.util.Comparator
import java.util.Locale
import java.util.regex.Pattern

object FileUtils {

    const val GB = 0x40000000L
    const val MB = 0x100000L
    const val KB = 1024L

    const val BY_NAME_ASC = 0
    const val BY_NAME_DESC = 1
    const val BY_TIME_ASC = 2
    const val BY_TIME_DESC = 3
    const val BY_SIZE_ASC = 4
    const val BY_SIZE_DESC = 5
    const val BY_EXTENSION_ASC = 6
    const val BY_EXTENSION_DESC = 7

    fun exists(root: File, vararg subDirFiles: String): Boolean {
        return getFile(root, *subDirFiles).exists()
    }

    fun createFileIfNotExist(root: File, vararg subDirFiles: String): File {
        val filePath = getPath(root, *subDirFiles)
        return createFileIfNotExist(filePath)
    }

    fun createFolderIfNotExist(root: File, vararg subDirs: String): File {
        val filePath = getPath(root, *subDirs)
        return createFolderIfNotExist(filePath)
    }

    fun createFolderIfNotExist(filePath: String): File {
        val file = File(filePath)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file
    }

    @Synchronized
    fun createFileIfNotExist(filePath: String): File {
        val file = File(filePath)
        try {
            if (!file.exists()) {
                file.parent?.let { createFolderIfNotExist(it) }
                file.createNewFile()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

    fun createFileWithReplace(filePath: String): File {
        val file = File(filePath)
        if (!file.exists()) {
            file.parent?.let { createFolderIfNotExist(it) }
            file.createNewFile()
        } else {
            file.delete()
            file.createNewFile()
        }
        return file
    }

    fun getFile(root: File, vararg subDirFiles: String): File {
        val filePath = getPath(root, *subDirFiles)
        return File(filePath)
    }

    fun getPath(root: File, vararg subDirFiles: String): String {
        val path = StringBuilder(root.absolutePath)
        subDirFiles.forEach { it ->
            if (it.length > 0) {
                path.append(File.separator).append(it)
            }
        }
        return path.toString()
    }

    @Synchronized
    fun deleteFile(filePath: String) {
        val file = File(filePath)
        if (!file.exists()) {
            return
        }
        if (file.isDirectory) {
            val files = file.listFiles()
            files?.forEach { subFile ->
                val path = subFile.path
                deleteFile(path)
            }
        }
        file.delete()
    }

    fun getCachePath(): String {
        throw Exception("Not implemented")
    }

    fun separator(path: String): String {
        var path1 = path
        val separator = File.separator
        path1 = path1.replace("\\", separator)
        if (!path1.endsWith(separator)) {
            path1 += separator
        }
        return path1
    }

    fun closeSilently(c: Closeable?) {
        if (c == null) {
            return
        }
        try {
            c.close()
        } catch (e: IOException) {
        }
    }

    @JvmOverloads
    fun listDirs(
        startDirPath: String,
        excludeDirs1: Array<String>? = null,
        sortType: Int = 0
    ): Array<File> {
        val dirList = ArrayList<File>()
        val startDir = File(startDirPath)
        if (!startDir.isDirectory) {
            return arrayOf()
        }
        val dirs = startDir.listFiles { f -> f?.isDirectory ?: false } ?: return arrayOf()
        val excludes = excludeDirs1 ?: arrayOf()
        for (dir in dirs) {
            val file = dir.absoluteFile
            if (excludes.contentDeepToString().contains(file.name)) continue
            dirList.add(file)
        }
        when (sortType) {
            0 -> Collections.sort(dirList, SortByName())
            1 -> {
                Collections.sort(dirList, SortByName())
                dirList.reverse()
            }
            2 -> Collections.sort(dirList, SortByTime())
            3 -> {
                Collections.sort(dirList, SortByTime())
                dirList.reverse()
            }
            4 -> Collections.sort(dirList, SortBySize())
            5 -> {
                Collections.sort(dirList, SortBySize())
                dirList.reverse()
            }
            6 -> Collections.sort(dirList, SortByExtension())
            7 -> {
                Collections.sort(dirList, SortByExtension())
                dirList.reverse()
            }
        }
        return dirList.toTypedArray()
    }

    @JvmOverloads
    fun listDirsAndFiles(
        startDirPath: String,
        allowExtensions: Array<String>? = null
    ): Array<File>? {
        val files = if (allowExtensions == null) {
            listFiles(startDirPath)
        } else {
            listFiles(startDirPath, allowExtensions)
        }
        val dirs = listDirs(startDirPath)
        if (files == null) {
            return null
        }
        return dirs + files
    }

    @JvmOverloads
    fun listFiles(
        startDirPath: String,
        filterPattern: Pattern? = null,
        sortType: Int = 0
    ): Array<File> {
        val fileList = ArrayList<File>()
        val f = File(startDirPath)
        if (!f.isDirectory) {
            return arrayOf()
        }
        val files = f.listFiles { file ->
            if (file == null) {
                false
            } else if (file.isDirectory) {
                false
            } else {
                filterPattern?.matcher(file.name)?.find() ?: true
            }
        } ?: return arrayOf()
        for (file in files) {
            fileList.add(file.absoluteFile)
        }
        when (sortType) {
            0 -> Collections.sort(fileList, SortByName())
            1 -> {
                Collections.sort(fileList, SortByName())
                fileList.reverse()
            }
            2 -> Collections.sort(fileList, SortByTime())
            3 -> {
                Collections.sort(fileList, SortByTime())
                fileList.reverse()
            }
            4 -> Collections.sort(fileList, SortBySize())
            5 -> {
                Collections.sort(fileList, SortBySize())
                fileList.reverse()
            }
            6 -> Collections.sort(fileList, SortByExtension())
            7 -> {
                Collections.sort(fileList, SortByExtension())
                fileList.reverse()
            }
        }
        return fileList.toTypedArray()
    }

    fun listFiles(startDirPath: String, allowExtensions: Array<String>?): Array<File>? {
        return File(startDirPath).listFiles { _, name ->
            val extension = getExtension(name!!)
            (allowExtensions?.contentDeepToString()?.contains(extension) ?: false) ||
                allowExtensions == null
        }
    }

    fun listFiles(startDirPath: String, allowExtension: String?): Array<File>? {
        return if (allowExtension == null) {
            listFiles(startDirPath, null as Array<String>?)
        } else {
            listFiles(startDirPath, arrayOf(allowExtension))
        }
    }

    fun exist(path: String): Boolean {
        val file = File(path)
        return file.exists()
    }

    @JvmOverloads
    fun delete(file: File, deleteRootDir: Boolean = false): Boolean {
        var result = false
        if (file.isFile) {
            result = deleteResolveEBUSY(file)
        } else {
            val files = file.listFiles() ?: return false
            if (files.isEmpty()) {
                result = deleteRootDir && deleteResolveEBUSY(file)
            } else {
                for (f in files) {
                    delete(f, deleteRootDir)
                    result = deleteResolveEBUSY(f)
                }
            }
            if (deleteRootDir) {
                result = deleteResolveEBUSY(file)
            }
        }
        return result
    }

    private fun deleteResolveEBUSY(file: File): Boolean {
        val to = File(file.absolutePath + System.currentTimeMillis())
        file.renameTo(to)
        return to.delete()
    }

    @JvmOverloads
    fun delete(path: String, deleteRootDir: Boolean = false): Boolean {
        val file = File(path)
        return if (file.exists()) delete(file, deleteRootDir) else false
    }

    fun copy(src: String, tar: String): Boolean {
        val srcFile = File(src)
        return srcFile.exists() && copy(srcFile, File(tar))
    }

    fun copy(src: File, tar: File): Boolean {
        try {
            if (src.isFile) {
                val inputStream = FileInputStream(src)
                val out = FileOutputStream(tar)
                val bis = BufferedInputStream(inputStream)
                val bos = BufferedOutputStream(out)
                val bt = ByteArray(8192)
                var len: Int
                while (bis.read(bt).also { len = it } != -1) {
                    bos.write(bt, 0, len)
                }
                bis.close()
                bos.close()
            } else if (src.isDirectory) {
                tar.mkdirs()
                src.listFiles()?.forEach { file ->
                    copy(file.absoluteFile, File(tar.absoluteFile, file.name))
                }
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun move(src: String, tar: String): Boolean {
        return move(File(src), File(tar))
    }

    fun move(src: File, tar: File): Boolean {
        return rename(src, tar)
    }

    fun rename(oldPath: String, newPath: String): Boolean {
        return rename(File(oldPath), File(newPath))
    }

    fun rename(src: File, tar: File): Boolean {
        return src.renameTo(tar)
    }

    @JvmOverloads
    fun readText(filepath: String, charset: String = "utf-8"): String {
        try {
            val data = readBytes(filepath)
            if (data != null) {
                return String(data, Charset.forName(charset)).trim { it <= ' ' }
            }
        } catch (e: UnsupportedEncodingException) {
        }
        return ""
    }

    fun readBytes(filepath: String): ByteArray? {
        var fis: FileInputStream? = null
        try {
            fis = FileInputStream(filepath)
            val baos = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var len: Int
            while (fis.read(buffer, 0, buffer.size).also { len = it } != -1) {
                baos.write(buffer, 0, len)
            }
            val data = baos.toByteArray()
            baos.close()
            return data
        } catch (e: IOException) {
            return null
        } finally {
            closeSilently(fis)
        }
    }

    @JvmOverloads
    fun writeText(filepath: String, content: String, charset: String = "utf-8"): Boolean {
        return try {
            val charset2 = Charset.forName(charset)
            writeBytes(filepath, content.toByteArray(charset2))
        } catch (e: UnsupportedEncodingException) {
            false
        }
    }

    fun writeBytes(filepath: String, data: ByteArray): Boolean {
        val file = File(filepath)
        var fos: FileOutputStream? = null
        var result = false
        try {
            if (!file.exists()) {
                file.parentFile?.mkdirs()
                file.createNewFile()
            }
            fos = FileOutputStream(filepath)
            fos.write(data)
            result = true
        } catch (e: IOException) {
            result = false
        } finally {
            closeSilently(fos)
        }
        return result
    }

    fun writeInputStream(filepath: String, data: InputStream): Boolean {
        val file = File(filepath)
        return writeInputStream(file, data)
    }

    fun writeInputStream(file: File, data: InputStream): Boolean {
        var fos: FileOutputStream? = null
        var result = false
        try {
            if (!file.exists()) {
                file.parentFile?.mkdirs()
                file.createNewFile()
            }
            val buffer = ByteArray(4096)
            fos = FileOutputStream(file)
            var len: Int
            while (data.read(buffer, 0, buffer.size).also { len = it } != -1) {
                fos.write(buffer, 0, len)
            }
            data.close()
            fos.flush()
            result = true
        } catch (e: IOException) {
            result = false
        } finally {
            closeSilently(fos)
        }
        return result
    }

    fun appendText(path: String, content: String): Boolean {
        val file = File(path)
        var writer: FileWriter? = null
        var result = false
        try {
            if (!file.exists()) {
                file.createNewFile()
            }
            writer = FileWriter(file, true)
            writer.write(content)
            result = true
        } catch (e: IOException) {
            result = false
        } finally {
            closeSilently(writer)
        }
        return result
    }

    fun getLength(path: String): Long {
        val file = File(path)
        return if (!file.isFile || !file.exists()) 0L else file.length()
    }

    fun getName(pathOrUrl: String?): String {
        if (pathOrUrl == null) {
            return ""
        }
        val pos = pathOrUrl.lastIndexOf('/')
        return if (0 <= pos) {
            pathOrUrl.substring(pos + 1)
        } else {
            System.currentTimeMillis().toString() + '.' + getExtension(pathOrUrl)
        }
    }

    fun getNameExcludeExtension(path: String): String {
        return try {
            var fileName = File(path).name
            val lastIndexOf = fileName.lastIndexOf(".")
            if (lastIndexOf != -1) {
                fileName = fileName.substring(0, lastIndexOf)
            }
            fileName
        } catch (e: Exception) {
            ""
        }
    }

    fun getSize(path: String): String {
        val fileSize = getLength(path)
        return toFileSizeString(fileSize)
    }

    fun toFileSizeString(fileSize: Long): String {
        val df = DecimalFormat("0.00")
        var fileSizeString: String? = null
        fileSizeString = if (fileSize < KB) {
            "" + fileSize + 'B'
        } else if (fileSize < MB) {
            df.format(fileSize.toDouble() / KB.toDouble()) + "K"
        } else if (fileSize < GB) {
            df.format(fileSize.toDouble() / MB.toDouble()) + "M"
        } else {
            df.format(fileSize.toDouble() / GB.toDouble()) + "G"
        }
        return fileSizeString
    }

    fun getExtension(pathOrUrl: String): String {
        val dotPos = pathOrUrl.lastIndexOf('.')
        return if (0 <= dotPos) {
            pathOrUrl.substring(dotPos + 1)
        } else {
            "ext"
        }
    }

    fun getFileExtetion(url: String, defaultExt: String = ""): String {
        return try {
            val seqs = url.split("?", ignoreCase = true, limit = 2)
            val file = seqs[0].split("/").last()
            val dotPos = file.lastIndexOf('.')
            if (0 <= dotPos) {
                file.substring(dotPos + 1)
            } else {
                defaultExt
            }
        } catch (e: Exception) {
            defaultExt
        }
    }

    fun getMimeType(pathOrUrl: String): String {
        throw Exception("Not implemented")
    }

    @JvmOverloads
    fun getDateTime(path: String, format: String = "yyyy年MM月dd日HH:mm"): String {
        val file = File(path)
        return getDateTime(file, format)
    }

    fun getDateTime(file: File, format: String): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = file.lastModified()
        return SimpleDateFormat(format, Locale.PRC).format(cal.time)
    }

    fun compareLastModified(path1: String, path2: String): Int {
        val stamp1 = File(path1).lastModified()
        val stamp2 = File(path2).lastModified()
        return if (stamp1 > stamp2) 1 else if (stamp1 < stamp2) -1 else 0
    }

    fun makeDirs(path: String): Boolean {
        return makeDirs(File(path))
    }

    fun makeDirs(file: File): Boolean {
        return file.mkdirs()
    }

    @Retention(AnnotationRetention.SOURCE)
    annotation class SortType

    class SortByExtension : Comparator<File> {
        override fun compare(f1: File?, f2: File?): Int {
            return if (f1 == null || f2 == null) {
                if (f1 == null) -1 else 1
            } else if (f1.isDirectory && f2.isFile) {
                -1
            } else if (f1.isFile && f2.isDirectory) {
                1
            } else {
                f1.name.compareTo(f2.name, true)
            }
        }
    }

    class SortByName(private var caseSensitive: Boolean) : Comparator<File> {

        constructor() : this(false)

        override fun compare(f1: File?, f2: File?): Int {
            if (f1 == null || f2 == null) {
                return if (f1 == null) -1 else 1
            }
            return if (f1.isDirectory && f2.isFile) {
                -1
            } else if (f1.isFile && f2.isDirectory) {
                1
            } else {
                val s1 = f1.name
                val s2 = f2.name
                if (caseSensitive) s1.compareTo(s2, false) else s1.compareTo(s2, true)
            }
        }
    }

    class SortBySize : Comparator<File> {
        override fun compare(f1: File?, f2: File?): Int {
            return if (f1 == null || f2 == null) {
                if (f1 == null) -1 else 1
            } else if (f1.isDirectory && f2.isFile) {
                -1
            } else if (f1.isFile && f2.isDirectory) {
                1
            } else if (f1.length() < f2.length()) -1 else 1
        }
    }

    class SortByTime : Comparator<File> {
        override fun compare(f1: File?, f2: File?): Int {
            return if (f1 == null || f2 == null) {
                if (f1 == null) -1 else 1
            } else if (f1.isDirectory && f2.isFile) {
                -1
            } else if (f1.isFile && f2.isDirectory) {
                1
            } else if (f1.lastModified() > f2.lastModified()) -1 else 1
        }
    }
}
