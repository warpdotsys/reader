// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.utils

import io.legado.app.adapters.ReaderAdapterHelper
import mu.KotlinLogging
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.util.Collections
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

private val logger = KotlinLogging.logger {}

class ACache private constructor(
    cacheDir: File,
    max_size: Long,
    max_count: Int
) {

    private var mCache: ACacheManager? = null

    companion object {
        const val TIME_HOUR = 3600
        const val TIME_DAY = 86400
        private const val MAX_SIZE = 50000000
        private const val MAX_COUNT = Int.MAX_VALUE
        private val mInstanceMap = HashMap<String, ACache>()

        @JvmOverloads
        fun get(
            cacheName: String = "ACache",
            maxSize: Long = 50000000,
            maxCount: Int = Int.MAX_VALUE
        ): ACache {
            val f = File(ReaderAdapterHelper.getAdapter().getCacheDir(), cacheName)
            return get(f, maxSize, maxCount)
        }

        @JvmOverloads
        fun get(
            cacheDir: File,
            maxSize: Long = 50000000,
            maxCount: Int = Int.MAX_VALUE
        ): ACache {
            synchronized(this) {
                var manager = mInstanceMap[cacheDir.absoluteFile.toString()]
                if (manager == null) {
                    manager = ACache(cacheDir, maxSize, maxCount)
                    mInstanceMap[cacheDir.absolutePath] = manager
                }
                return manager
            }
        }
    }

    init {
        try {
            if (!cacheDir.exists() && !cacheDir.mkdirs()) {
                logger.info("ACache can't make dirs in %s", cacheDir.absolutePath)
            }
            mCache = ACacheManager(cacheDir, max_size, max_count)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun put(key: String, value: String) {
        mCache?.let { mCache ->
            try {
                val file = mCache.newFile(key)
                file.writeText(value)
                mCache.put(file)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun put(key: String, value: String, saveTime: Int) {
        if (saveTime <= 0) {
            put(key, value)
            return
        }
        put(key, Utils.newStringWithDateInfo(saveTime, value))
    }

    fun getAsString(key: String): String? {
        val mCache = mCache ?: return null
        var removeFile = false
        try {
            val file = mCache.get(key)
            if (!file.exists()) {
                return null
            }
            val text = file.readText()
            if (!Utils.isDue(text)) {
                return Utils.clearDateInfo(text)
            }
            removeFile = true
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (removeFile) {
                remove(key)
            }
        }
        return null
    }

    fun getByHashCode(hashCode: String): String? {
        val mCache = mCache ?: return null
        var removeFile = false
        var file: File? = null
        try {
            val f = mCache.newFileFromHashCode(hashCode)
            file = f
            if (!f.exists()) {
                return null
            }
            val text = f.readText()
            if (!Utils.isDue(text)) {
                return Utils.clearDateInfo(text)
            }
            removeFile = true
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (removeFile) {
                file?.delete()
            }
        }
        return null
    }

    fun put(key: String, value: ByteArray) {
        mCache?.let { mCache ->
            val file = mCache.newFile(key)
            file.writeBytes(value)
            mCache.put(file)
        }
    }

    fun put(key: String, value: ByteArray, saveTime: Int) {
        if (saveTime <= 0) {
            put(key, value)
            return
        }
        put(key, Utils.newByteArrayWithDateInfo(saveTime, value))
    }

    fun getAsBinary(key: String): ByteArray? {
        val mCache = mCache ?: return null
        var removeFile = false
        try {
            val file = mCache.get(key)
            if (!file.exists()) {
                return null
            }
            val data = file.readBytes()
            return if (!Utils.isDue(data)) {
                Utils.clearDateInfo(data)
            } else {
                removeFile = true
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (removeFile) {
                remove(key)
            }
        }
        return null
    }

    @JvmOverloads
    fun put(key: String, value: Serializable, saveTime: Int = -1) {
        try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            ObjectOutputStream(byteArrayOutputStream).use { oos ->
                oos.writeObject(value)
                val data = byteArrayOutputStream.toByteArray()
                if (saveTime != -1) {
                    put(key, data, saveTime)
                } else {
                    put(key, data)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getAsObject(key: String): Any? {
        val data = getAsBinary(key)
        if (data != null) {
            var bis: ByteArrayInputStream? = null
            var ois: ObjectInputStream? = null
            try {
                bis = ByteArrayInputStream(data)
                ois = ObjectInputStream(bis)
                return ois.readObject()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    bis?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                try {
                    ois?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }

    fun file(key: String): File? {
        mCache?.let { mCache ->
            try {
                val f = mCache.newFile(key)
                if (f.exists()) {
                    return f
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    fun remove(key: String): Boolean {
        return mCache?.remove(key) ?: false
    }

    fun clear() {
        mCache?.clear()
    }

    open inner class ACacheManager(
        private var cacheDir: File,
        private val sizeLimit: Long,
        private val countLimit: Int
    ) {

        private val cacheSize = AtomicLong()
        private val cacheCount = AtomicInteger()
        private val lastUsageDates =
            Collections.synchronizedMap(HashMap<File, Long>())

        init {
            calculateCacheSizeAndCacheCount()
        }

        private fun calculateCacheSizeAndCacheCount() {
            Thread {
                try {
                    var size = 0
                    var count = 0
                    val cachedFiles = cacheDir.listFiles()
                    if (cachedFiles != null) {
                        for (cachedFile in cachedFiles) {
                            size += calculateSize(cachedFile).toInt()
                            count++
                            lastUsageDates[cachedFile] = cachedFile.lastModified()
                        }
                        cacheSize.set(size.toLong())
                        cacheCount.set(count)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        }

        fun put(file: File) {
            try {
                var curCacheCount = cacheCount.get()
                while (curCacheCount + 1 > countLimit) {
                    val freedSize = removeNext()
                    cacheSize.addAndGet(-freedSize)
                    curCacheCount = cacheCount.addAndGet(-1)
                }
                cacheCount.addAndGet(1)
                val valueSize = calculateSize(file)
                var curCacheSize = cacheSize.get()
                while (curCacheSize + valueSize > sizeLimit) {
                    val freedSize = removeNext()
                    curCacheSize = cacheSize.addAndGet(-freedSize)
                }
                cacheSize.addAndGet(valueSize)
                val currentTime = System.currentTimeMillis()
                file.setLastModified(currentTime)
                lastUsageDates[file] = currentTime
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun get(key: String): File {
            val file = newFile(key)
            val currentTime = System.currentTimeMillis()
            file.setLastModified(currentTime)
            lastUsageDates[file] = currentTime
            return file
        }

        fun newFile(key: String): File {
            return File(cacheDir, key.hashCode().toString() + "")
        }

        fun newFileFromHashCode(hashCode: String): File {
            return File(cacheDir, hashCode)
        }

        fun remove(key: String): Boolean {
            val image = get(key)
            return image.delete()
        }

        fun clear() {
            try {
                lastUsageDates.clear()
                cacheSize.set(0L)
                val files = cacheDir.listFiles()
                if (files != null) {
                    for (f in files) {
                        f.delete()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        private fun removeNext(): Long {
            try {
                if (lastUsageDates.isEmpty()) {
                    return 0L
                }
                var oldestUsage: Long? = null
                var mostLongUsedFile: File? = null
                val entries = lastUsageDates.entries
                synchronized(lastUsageDates) {
                    for ((key, lastValueUsage) in entries) {
                        if (mostLongUsedFile == null) {
                            mostLongUsedFile = key
                            oldestUsage = lastValueUsage
                            continue
                        }
                        if (lastValueUsage >= oldestUsage!!) continue
                        oldestUsage = lastValueUsage
                        mostLongUsedFile = key
                    }
                }
                var fileSize = 0L
                val file = mostLongUsedFile
                if (file != null) {
                    fileSize = calculateSize(file)
                    if (file.delete()) {
                        lastUsageDates.remove(file)
                    }
                }
                return fileSize
            } catch (e: Exception) {
                e.printStackTrace()
                return 0L
            }
        }

        private fun calculateSize(file: File): Long {
            return file.length()
        }
    }

    private object Utils {

        private const val mSeparator = ' '

        fun isDue(str: String): Boolean {
            return isDue(str.toByteArray(Charsets.UTF_8))
        }

        fun isDue(data: ByteArray): Boolean {
            try {
                val text = getDateInfoFromDate(data)
                if (text != null && text.size == 2) {
                    var saveTimeStr = text[0]
                    while (saveTimeStr.startsWith("0")) {
                        saveTimeStr = saveTimeStr.substring(1)
                    }
                    val saveTime = saveTimeStr.toLong()
                    val deleteAfter = text[1].toLong()
                    if (System.currentTimeMillis() > saveTime + deleteAfter * 1000) {
                        return true
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }

        fun newStringWithDateInfo(second: Int, strInfo: String): String {
            return createDateInfo(second) + strInfo
        }

        fun newByteArrayWithDateInfo(second: Int, data2: ByteArray): ByteArray {
            val data1 = createDateInfo(second).toByteArray(Charsets.UTF_8)
            val retData = ByteArray(data1.size + data2.size)
            System.arraycopy(data1, 0, retData, 0, data1.size)
            System.arraycopy(data2, 0, retData, data1.size, data2.size)
            return retData
        }

        fun clearDateInfo(strInfo: String?): String? {
            if (strInfo != null) {
                if (hasDateInfo(strInfo.toByteArray(Charsets.UTF_8))) {
                    return strInfo.substring(strInfo.indexOf(' ') + 1)
                }
            }
            return strInfo
        }

        fun clearDateInfo(data: ByteArray): ByteArray {
            return if (hasDateInfo(data)) {
                copyOfRange(data, indexOf(data, mSeparator) + 1, data.size)
            } else {
                data
            }
        }

        fun hasDateInfo(data: ByteArray?): Boolean {
            if (data == null) return false
            if (data.size <= 15) return false
            if (data[13] != 45.toByte()) return false
            if (indexOf(data, mSeparator) <= 14) return false
            return true
        }

        fun getDateInfoFromDate(data: ByteArray): Array<String>? {
            return if (hasDateInfo(data)) {
                val saveDate = String(copyOfRange(data, 0, 13), Charsets.UTF_8)
                val deleteAfter = String(copyOfRange(data, 14, indexOf(data, mSeparator)), Charsets.UTF_8)
                arrayOf(saveDate, deleteAfter)
            } else {
                null
            }
        }

        private fun indexOf(data: ByteArray, c: Char): Int {
            for (i in data.indices) {
                if (data[i] == c.code.toByte()) {
                    return i
                }
            }
            return -1
        }

        private fun copyOfRange(original: ByteArray, from: Int, to: Int): ByteArray {
            val newLength = to - from
            require(newLength >= 0) { "$from > $to" }
            val copy = ByteArray(newLength)
            System.arraycopy(original, from, copy, 0, Math.min(original.size - from, newLength))
            return copy
        }

        private fun createDateInfo(second: Int): String {
            val currentTime = StringBuilder(System.currentTimeMillis().toString() + "")
            while (currentTime.length < 13) {
                currentTime.insert(0, "0")
            }
            return "" + currentTime + '-' + second + ' '
        }
    }
}
