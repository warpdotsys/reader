// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.utils

import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.htmake.reader.config.AppConfig
import com.htmake.reader.entity.License
import com.htmake.reader.entity.MongoFile
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.ReplaceOptions
import io.legado.app.data.entities.Book
import io.legado.app.utils.EncoderUtils
import io.legado.app.utils.FileUtils
import io.legado.app.utils.MD5Utils
import io.legado.app.utils.MapDeserializerDoubleAsIntFix
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import mu.KLogger
import mu.KotlinLogging
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.InputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.Socket
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import java.util.UUID
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import javax.net.ssl.SSLSocketFactory
import kotlin.jvm.internal.Reflection
import kotlin.random.Random
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties

private const val MAX_CACHE_SIZE = 1000

val logger: KLogger = KotlinLogging.logger {}

val gson = GsonBuilder()
    .registerTypeAdapter(object : TypeToken<Map<String, Any>>() {}.type, MapDeserializerDoubleAsIntFix())
    .registerTypeAdapter(Int::class.javaPrimitiveType, IntTypeAdapter())
    .registerTypeAdapter(Long::class.javaPrimitiveType, LongTypeAdapter())
    .disableHtmlEscaping()
    .create()

val prettyGson = GsonBuilder()
    .registerTypeAdapter(object : TypeToken<Map<String, Any>>() {}.type, MapDeserializerDoubleAsIntFix())
    .registerTypeAdapter(Int::class.javaPrimitiveType, IntTypeAdapter())
    .registerTypeAdapter(Long::class.javaPrimitiveType, LongTypeAdapter())
    .disableHtmlEscaping()
    .setPrettyPrinting()
    .create()

var storageFinalPath: String = ""

var workDirPath: String = ""

var workDirInit: Boolean = false

private val lockMap = object : LinkedHashMap<String, ReadWriteLock>(16, 0.75f, true) {
    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, ReadWriteLock>?): Boolean {
        return size > MAX_CACHE_SIZE
    }
}

fun String.url(): String {
    if (startsWith("//")) {
        return "http:$this".toHttpUrl().toString()
    }
    if (startsWith("http")) {
        return toHttpUrl().toString()
    }
    return this
}

fun String.toDir(absolute: Boolean = false): String {
    var path = this
    if (path.endsWith("/")) {
        path = path.substring(0, path.length - 1)
    }
    if (absolute && !path.startsWith("/")) {
        path = "/$path"
    }
    return path
}

fun File.deleteRecursively() {
    if (exists()) {
        if (isFile) {
            delete()
        } else {
            listFiles().forEach {
                it.deleteRecursively()
            }
            delete()
        }
    }
}

fun File.listFilesRecursively(): List<File> {
    val list = ArrayList<File>()
    if (exists()) {
        if (isFile) {
            list.add(this)
        } else {
            listFiles().forEach {
                list.add(it)
                if (it.isDirectory) {
                    list.addAll(it.listFilesRecursively())
                }
            }
        }
    }
    return list
}

fun File.unzip(descDir: String): Boolean {
    if (!exists()) {
        return false
    }
    val buffer = ByteArray(1024)
    var outputStream: OutputStream? = null
    var inputStream: InputStream? = null
    try {
        val zf = ZipFile(toString())
        val entries = zf.entries()
        while (entries.hasMoreElements()) {
            val zipEntry = entries.nextElement()!!
            val zipEntryName = zipEntry.name
            val descFilePath = descDir + File.separator + zipEntryName
            if (zipEntry.isDirectory) {
                createDir(descFilePath)
                continue
            }
            inputStream = zf.getInputStream(zipEntry)
            val descFile = createFile(descFilePath)
            outputStream = FileOutputStream(descFile)
            var len = 0
            while (true) {
                if (inputStream.read(buffer).also { len = it } <= 0) {
                    break
                }
                outputStream.write(buffer, 0, len)
            }
            inputStream.close()
            outputStream.close()
        }
        return true
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        inputStream?.close()
        outputStream?.close()
    }
    return false
}

fun File.zip(zipFilePath: String): Boolean {
    if (!exists()) {
        return false
    }
    if (isDirectory) {
        val files = listFiles()
        val filesList = files.toList()
        return filesList.zip(zipFilePath)
    }
    return arrayListOf(this).zip(zipFilePath)
}

fun List<File>.zip(zipFilePath: String): Boolean {
    if (isEmpty()) {
        return false
    }
    val zipFile = createFile(zipFilePath)
    val buffer = ByteArray(1024)
    var zipOutputStream: ZipOutputStream? = null
    var inputStream: java.io.FileInputStream? = null
    try {
        zipOutputStream = ZipOutputStream(FileOutputStream(zipFile))
        for (closeable in this) {
            if (!closeable.exists()) {
                continue
            }
            zipOutputStream.putNextEntry(ZipEntry(closeable.name))
            inputStream = java.io.FileInputStream(closeable)
            var len = 0
            while (true) {
                if (inputStream.read(buffer).also { len = it } <= 0) {
                    break
                }
                zipOutputStream.write(buffer, 0, len)
            }
            zipOutputStream.closeEntry()
        }
        return true
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        inputStream?.close()
        zipOutputStream?.close()
    }
    return false
}

fun createDir(filePath: String): File {
    logger.debug("createDir filePath {}", filePath)
    val file = File(filePath)
    if (!file.exists()) {
        file.mkdirs()
    }
    return file
}

fun createFile(filePath: String): File {
    logger.debug("createFile filePath {}", filePath)
    val file = File(filePath)
    val parentFile = file.parentFile!!
    if (!parentFile.exists()) {
        parentFile.mkdirs()
    }
    if (!file.exists()) {
        file.createNewFile()
    }
    return file
}

fun getWorkDir(subPath: String = ""): String {
    if (!workDirInit && workDirPath.isEmpty()) {
        val appConfig = SpringContextUtils.getBean("appConfig", AppConfig::class.java)
        if (appConfig != null) {
            if (appConfig.workDir.length > 0 && appConfig.workDir != ".") {
                val workDirFile = File(appConfig.workDir)
                if (workDirFile.exists() && !workDirFile.isDirectory) {
                    logger.error("reader.app.workDir={} is not a directory", appConfig.workDir)
                } else {
                    if (!workDirFile.exists()) {
                        logger.info("reader.app.workDir={} not exists, creating", appConfig.workDir)
                        workDirFile.mkdirs()
                    }
                    workDirPath = workDirFile.absolutePath
                }
            }
        }
        if (workDirPath.isEmpty()) {
            val osName = System.getProperty("os.name")
            val currentDir = System.getProperty("user.dir")
            logger.info("osName: {} currentDir: {}", osName, currentDir)
            if (osName!!.startsWith("Mac OS", true)) {
                if (!currentDir!!.startsWith("/Users/")) {
                    workDirPath = Paths.get(System.getProperty("user.home"), ".reader").toString()
                } else {
                    workDirPath = currentDir
                }
            } else {
                workDirPath = currentDir
            }
        }
        logger.info("Using workdir: {}", workDirPath)
        workDirInit = true
    }
    return Paths.get(workDirPath, subPath).toString()
}

fun getWorkDir(vararg subDirFiles: String): String {
    return getWorkDir(getRelativePath(*subDirFiles))
}

fun getRelativePath(vararg subDirFiles: String): String {
    val path = StringBuilder("")
    subDirFiles.forEach {
        if (it.isNotEmpty()) {
            path.append(File.separator).append(it)
        }
    }
    return path.toString().let {
        if (it.startsWith("/")) {
            it.substring(1)
        } else {
            it
        }
    }
}

fun getStoragePath(): String {
    if (storageFinalPath.isNotEmpty()) {
        return storageFinalPath
    }
    var storagePath = ""
    val appConfig = SpringContextUtils.getBean("appConfig", AppConfig::class.java)
    if (appConfig != null) {
        storagePath = getWorkDir("storage")
        storageFinalPath = storagePath
    } else {
        storagePath = File("storage").path
    }
    logger.info("Using storagePath: {}", storagePath)
    return storagePath
}

fun saveStorage(vararg name: String, value: Any, pretty: Boolean = false, ext: String = ".json") {
    val toJson = when {
        value is String -> value
        value is JsonObject || value is JsonArray -> value.toString()
        pretty -> {
            prettyGson.toJson(value)
        }
        else -> {
            gson.toJson(value)
        }
    }
    val storagePath = getStoragePath()
    val storageDir = File(storagePath)
    if (!storageDir.exists()) {
        storageDir.mkdirs()
    }
    val filename = name.last()
    val path = getRelativePath(*name.copyOfRange(0, name.size - 1), "$filename$ext")
    val file = File(storagePath + File.separator + path)
    logger.info("Save file to storage name: {} path: {}", name, file.absoluteFile)
    if (!file.parentFile.exists()) {
        file.parentFile.mkdirs()
    }
    val lock = synchronized(lockMap) {
        lockMap.getOrPut(file.absoluteFile.nameWithoutExtension) {
            ReentrantReadWriteLock()
        }
    }
    var acquired = false
    try {
        acquired = lock.writeLock().tryLock(10L, TimeUnit.SECONDS)
        if (!acquired) {
            throw Exception("保存文件超时: " + file.absolutePath)
        }
        val tmp = Files.createTempFile(Paths.get(file.parentFile.path).toAbsolutePath(), filename, ".temp")
        Files.write(tmp, toJson.toByteArray(Charsets.UTF_8))
        val filePath = Paths.get(file.path)
        val backupPath = Paths.get(file.parentFile.path, "$filename.backup.json").toAbsolutePath()
        if (Files.exists(filePath)) {
            Files.move(filePath, backupPath, StandardCopyOption.ATOMIC_MOVE)
        }
        Files.move(tmp, filePath, StandardCopyOption.ATOMIC_MOVE)
        Files.deleteIfExists(tmp)
        if (filename.length >= 32) {
            Files.deleteIfExists(backupPath)
        }
        if (filename == "users") {
            val userCount = countOccurrences(toJson, "username")
            val verifyKeyPath = getRelativePath(*name.copyOfRange(0, name.size - 1), ".$filename.key")
            val verifyKeyFile = File(storagePath + File.separator + verifyKeyPath)
            if (!verifyKeyFile.exists()) {
                verifyKeyFile.createNewFile()
            }
            val md5Encode = MD5Utils.md5Encode("userCount=$userCount").toString()
            verifyKeyFile.writeText(md5Encode.substring(md5Encode.length - 16))
        }
        saveMongoFile(path, toJson)
    } catch (e: Exception) {
        logger.error("保存文件失败: ", e)
        throw Exception("保存文件失败: " + file.absolutePath)
    } finally {
        if (acquired) {
            lock.writeLock().unlock()
        }
    }
}

fun getStorageFile(vararg name: String, ext: String = ".json"): File {
    val storagePath = getStoragePath()
    val storageDir = File(storagePath)
    if (!storageDir.exists()) {
        storageDir.mkdirs()
    }
    val filename = name.last()
    val path = getRelativePath(*name.copyOfRange(0, name.size - 1), "$filename$ext")
    return File(storagePath + File.separator + path)
}

fun getStorage(vararg name: String, ext: String = ".json"): String? {
    val storagePath = getStoragePath()
    val filename = name.last()
    val path = getRelativePath(*name.copyOfRange(0, name.size - 1), "$filename$ext")
    val file = getStorageFile(*name, ext = ext)
    logger.info("Read file from storage name: {} path: {}", name, file.absoluteFile)
    if (!file.exists()) {
        return readMongoFile(path)?.also { content ->
            if (content.isNotEmpty()) {
                if (!file.parentFile.exists()) {
                    file.parentFile.mkdirs()
                }
                file.createNewFile()
                file.writeText(content)
            }
        }
    }
    val lock = synchronized(lockMap) {
        lockMap.getOrPut(file.absolutePath) {
            ReentrantReadWriteLock()
        }
    }
    var content = ""
    var acquired = false
    try {
        acquired = lock.readLock().tryLock(10L, TimeUnit.SECONDS)
        if (!acquired) {
            throw Exception("读取文件超时: " + file.absolutePath)
        }
        val reader = FileReader(file)
        try {
            content = reader.readText()
            if (content.isEmpty()) {
                return readMongoFile(path)?.also { content ->
                    if (content.isNotEmpty()) {
                        if (!file.parentFile.exists()) {
                            file.parentFile.mkdirs()
                        }
                        file.createNewFile()
                        file.writeText(content)
                    }
                } ?: content
            }
            if (filename == "users") {
                val verifyKeyPath = getRelativePath(*name.copyOfRange(0, name.size - 1), ".$filename.key")
                val verifyKeyFile = File(storagePath + File.separator + verifyKeyPath)
                if (verifyKeyFile.exists()) {
                    val verifyKeyContent = verifyKeyFile.readText()
                    val userCount = countOccurrences(content, "username")
                    val md5Encode = MD5Utils.md5Encode("userCount=$userCount").toString()
                    if (verifyKeyContent != md5Encode.substring(md5Encode.length - 16)) {
                        throw Exception("用户数据被篡改，请联系开发者修复")
                    }
                }
            }
        } finally {
            reader.close()
        }
    } catch (e: Exception) {
        logger.error("读取文件失败: ", e)
        throw Exception("读取文件失败: " + file.absolutePath)
    } finally {
        if (acquired) {
            lock.readLock().unlock()
        }
    }
    return content
}

fun getMongoFileStorage(): MongoCollection<MongoFile>? {
    val appConfig = SpringContextUtils.getBean("appConfig", AppConfig::class.java)
    return MongoManager.fileStorage(appConfig.mongoDbName, "storage")
}

fun readMongoFile(path: String): String? {
    if (MongoManager.isInit()) {
        logger.info("Get mongoFile {}", path)
        val doc = getMongoFileStorage()?.find(Filters.eq("path", path))?.first()
        if (doc != null) {
            return doc.content
        }
    }
    return null
}

fun saveMongoFile(path: String, content: String): Boolean {
    if (MongoManager.isInit()) {
        logger.info("Save mongoFile {}", path)
        val doc = getMongoFileStorage()?.find(Filters.eq("path", path))?.first()
        if (doc != null) {
            doc.content = content
            doc.updated_at = System.currentTimeMillis()
            val result = getMongoFileStorage()?.replaceOne(Filters.eq("path", path), doc, ReplaceOptions().upsert(true))
            return result != null && result.modifiedCount > 0L
        }
        val newDoc = MongoFile(path, content)
        try {
            getMongoFileStorage()?.insertOne(newDoc)
            return true
        } catch (e: Exception) {
            logger.info("Save mongoFile {} failed", path)
            e.printStackTrace()
        }
    }
    return false
}

fun countOccurrences(str: String, subStr: String): Int {
    var count = 0
    var startIndex = 0
    while (startIndex < str.length) {
        val index = str.indexOf(subStr, startIndex)
        if (index != -1) {
            count++
            startIndex = index + subStr.length
        } else {
            break
        }
    }
    return count
}

fun asJsonArray(value: Any?): JsonArray? {
    if (value is JsonArray) {
        return value
    }
    if (value is String) {
        try {
            return JsonArray(value)
        } catch (e: Exception) {
            logger.error("解析内容出错: {}  内容: \n{}", e, value)
            throw e
        }
    }
    return null
}

fun parseJsonStringList(
    file: File,
    fields: Set<String>? = null,
    exclude: Set<String>? = null,
    startIndex: Int = 0,
    endIndex: Int = Int.MAX_VALUE,
    checkNotEmpty: Set<String>? = null,
    filter: ((ObjectNode) -> Boolean)? = null
): JsonArray? {
    if (!file.exists()) {
        return null
    }
    try {
        val objectMapper = ObjectMapper()
        val factory = objectMapper.factory
        val resultList = JsonArray()
        var currentIndex = 0
        currentIndex = -1
        factory.createParser(file).use { parser ->
            if (parser.nextToken() == JsonToken.START_ARRAY) {
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    if (parser.currentToken() != JsonToken.START_OBJECT) {
                        continue
                    }
                    if (fields.isNullOrEmpty()) {
                        if (filter == null) {
                            if (++currentIndex < startIndex) {
                                parser.skipChildren()
                                continue
                            }
                            if (currentIndex > endIndex) {
                                break
                            }
                            val objectNode = parser.readValueAsTree<ObjectNode>()
                            if (!exclude.isNullOrEmpty()) {
                                exclude.forEach {
                                    objectNode.remove(it)
                                }
                            }
                            resultList.add(objectNode.toString())
                        } else {
                            val objectNode = parser.readValueAsTree<ObjectNode>()
                            if (filter.invoke(objectNode)) {
                                currentIndex++
                            }
                            if (currentIndex < startIndex) {
                                continue
                            }
                            if (currentIndex > endIndex) {
                                break
                            }
                            resultList.add(objectNode.toString())
                        }
                    } else {
                        if (++currentIndex < startIndex) {
                            parser.skipChildren()
                            continue
                        }
                        if (currentIndex > endIndex) {
                            break
                        }
                        val item = JsonObject()
                        while (parser.nextToken() != JsonToken.END_OBJECT) {
                            val fieldName = parser.currentName
                            parser.nextToken()
                            if (fields.contains(fieldName)) {
                                item.put(fieldName, parser.valueAsString)
                                continue
                            }
                            if (checkNotEmpty != null && checkNotEmpty.contains(fieldName)) {
                                item.put(fieldName, !parser.valueAsString.isNullOrEmpty())
                                continue
                            }
                            parser.skipChildren()
                        }
                        resultList.add(item.toString())
                    }
                }
            }
        }
        return resultList
    } catch (e: Exception) {
        logger.error("解析文件内容出错: {}  文件: \n{}", e, file)
        throw e
    }
}

fun asJsonObject(value: Any?): JsonObject? {
    if (value is JsonObject) {
        return value
    }
    if (value is String) {
        try {
            return JsonObject(value)
        } catch (e: Exception) {
            logger.error("解析内容出错: {}  内容: \n{}", e, value)
            throw e
        }
    }
    return null
}

fun <T> T.serializeToMap(): Map<String, Any> = convert()

fun <T> T.toMap(): Map<String, Any> = convert()

inline fun <reified T> Map<String, Any>.toDataClass(): T = convert()

inline fun <I, reified O> I.convert(): O {
    val json = if (this is String) this else gson.toJson(this)
    return gson.fromJson(json, object : TypeToken<O>() {}.type)
}

inline fun <T> Class<T>.arrayType(): Class<Any> {
    return (java.lang.reflect.Array.newInstance(this, 0) as Any).javaClass
}

fun <R> readInstanceProperty(instance: Any, propertyName: String): R {
    val property = Reflection.getOrCreateKotlinClass(instance.javaClass).memberProperties.first {
        it.name == propertyName
    }
    return property.get(instance) as R
}

fun setInstanceProperty(instance: Any, propertyName: String, propertyValue: Any) {
    val property = Reflection.getOrCreateKotlinClass(instance.javaClass).memberProperties.first {
        it.name == propertyName
    }
    if (property is KMutableProperty<*>) {
        property.setter.call(instance, propertyValue)
    }
}

fun Book.fillData(newBook: Book, keys: List<String>): Book {
    keys.let {
        for (key in it) {
            val current: String = readInstanceProperty(this, key)
            if (current.isNullOrEmpty()) {
                val cacheValue: String = readInstanceProperty(newBook, key)
                if (!cacheValue.isNullOrEmpty()) {
                    setInstanceProperty(this, key, cacheValue)
                }
            }
        }
    }
    return this
}

fun getRandomString(length: Int): String {
    val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
    return (1..length).map { allowedChars.random() }.joinToString("")
}

fun genEncryptedPassword(password: String, salt: String): String {
    return MD5Utils.md5Encode(MD5Utils.md5Encode(password + salt) + salt).toString()
}

fun jsonEncode(value: Any, pretty: Boolean = false): String {
    if (pretty) {
        return prettyGson.toJson(value)
    }
    return gson.toJson(value)
}

fun File.deepListFiles(allowExtensions: Array<String>?): List<File> {
    val fileList = ArrayList<File>()
    listFiles().forEach {
        if (it.isDirectory) {
            fileList.addAll(it.deepListFiles(allowExtensions))
        } else {
            val extension = FileUtils.getExtension(it.name)
            if (allowExtensions?.contentDeepToString()?.contains(extension) != true && allowExtensions != null) {
                return@forEach
            }
            fileList.add(it)
        }
    }
    return fileList
}

fun getTraceId(): String {
    return UUID.randomUUID().toString().subSequence(0, 8).toString()
}

var _licenseValid = true

fun setLicenseValid(isValid: Boolean) {
    _licenseValid = isValid
}

fun getInstalledLicense(ignoreInvalid: Boolean = false): License {
    val licenseKeyString = getStorage("data", "license", ext = ".key")
    if (licenseKeyString.isNullOrEmpty()) {
        return License()
    }
    if (!ignoreInvalid && !_licenseValid) {
        return License()
    }
    val license = decryptToLicense(licenseKeyString)
    logger.info("license: {}", license)
    if (license == null || !license.verified) {
        return License()
    }
    return license
}

fun decryptToLicense(content: String): License? {
    if (content.isEmpty()) {
        return null
    }
    return decryptData(content)?.let {
        it.toMap().toDataClass<License>()
    }
}

fun decryptData(content: String): String? {
    val publicKeyString = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj0G3qEPjVTvVd7pXFUVYZFHT8KaoG4onc5rLUKqFQ2DCh/5hFK9t2nKh2XB+C2Jp/GSK2ONwD7ceXenmA6uvr90uCK/gp6j62XFVRvc8sIm0d/bGbzZFJRk3HKtxEckBmASduPObY691DVVixxNtUrSJktx/TZaB42pUQk4j+7FuOVNNPra44hDdnyGhmYBBf2B4kjXVMjL+0NCblFIN1+qjmcol44k6NFKFF54q05bjR3CRyYdAnNTCOyt9va0oB6lDlKHplSZmAOH9JGMUki/HDJbABESXMnyIpux27w9SQ8aJStYttnJWHALO1hiFJsxbz5KUkldH6Ny1p/2W5QIDAQAB"
    val publicKey = KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(io.legado.app.utils.Base64.decode(publicKeyString, 2)))
    return EncoderUtils.decryptSegmentByPublicKey(content, publicKey)
}

fun validateEmail(email: String): Boolean {
    val regex = Regex("^[A-Za-z0-9._%+-]+@(163|126|qq|yahoo|sina|sohu|yeah|139|189|21cn|outlook|gmail|icloud).com$")
    return regex.matches(email)
}

fun sendEmail(toEmail: String, subject: String, body: String): Boolean {
    val host = "smtp.qiye.aliyun.com"
    val port = 465
    val sendCommand = fun(writer: OutputStreamWriter, reader: BufferedReader, command: Pair<String, Int>): Boolean {
        val cmd = command.first
        val expectCode = command.second
        logger.debug("Send command {}, expect code {}", cmd.trim(), expectCode)
        writer.write("$cmd")
        writer.flush()
        val response = reader.readLine()
        logger.debug("Response {}", response)
        if (!response.isNullOrEmpty()) {
            if (!response.startsWith("" + expectCode)) {
                logger.error("Error response from SMTP server.")
                return false
            }
            return true
        }
        logger.error("SMTP server no response.")
        return false
    }
    try {
        val sslSocketFactory = SSLSocketFactory.getDefault()
        val socket = sslSocketFactory.createSocket(host, port)
        val outputStream = socket.getOutputStream()
        val writer = OutputStreamWriter(outputStream)
        val inputStream = socket.getInputStream()
        val reader = inputStream.reader(Charsets.UTF_8).buffered()
        val response = reader.readLine()
        if (!response.startsWith("220")) {
            logger.error("Error connecting to the SMTP server.")
            return false
        }
        val commandList = getCommand(arrayListOf(toEmail), subject, body)
        var res = false
        var i = 0
        val n = commandList.size
        while (i < n) {
            res = sendCommand(writer, reader, commandList[i++])
            if (!res) {
                break
            }
        }
        writer.close()
        reader.close()
        socket.close()
        return res
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
}

fun getCommand(to: List<String>, subject: String, body: String): List<Pair<String, Int>> {
    val username = "no-reply@onmy.top"
    val password = "no-reply@1."
    val from = "no-reply@onmy.top"
    val fromname = "Reader"
    val separator = "----=_Part_" + System.currentTimeMillis() + UUID.randomUUID()
    val command = mutableListOf(Pair("HELO sendmail\r\n", 250))
    if (username.isNotEmpty()) {
        command.add(Pair("AUTH LOGIN\r\n", 334))
        command.add(Pair(encodeBase64(username) + "\r\n", 334))
        command.add(Pair(encodeBase64(password) + "\r\n", 235))
    }
    command.add(Pair("MAIL FROM: <" + from + ">\r\n", 250))
    var header = "FROM: " + fromname + '<' + from + ">\r\n"
    if (to.isNotEmpty()) {
        val count = to.size
        if (count == 1) {
            command.add(Pair("RCPT TO: <" + to[0] + ">\r\n", 250))
            header = header + "TO: <" + to[0] + ">\r\n"
        } else {
            var index = 0
            while (index < count) {
                val i = index++
                command.add(Pair("RCPT TO: <" + to[i] + ">\r\n", 250))
                header = if (i == 0) {
                    header + "TO: <" + to[i] + '>'
                } else if (i + 1 == count) {
                    header + ",<" + to[i] + ">\r\n"
                } else {
                    header + ",<" + to[i] + '>'
                }
            }
        }
    }
    header = header + "Subject: =?UTF-8?B?" + encodeBase64(subject) + "?=\r\n"
    header = header + "Content-Type: multipart/alternative;\r\n"
    header = header + "\tboundary=\"" + separator + '"'
    header = header + "\r\nMIME-Version: 1.0\r\n"
    header = header + "\r\n--" + separator + "\r\n"
    header = header + "Content-Type:text/html; charset=utf-8\r\n"
    header = header + "Content-Transfer-Encoding: base64\r\n\r\n"
    header = header + encodeBase64(body) + "\r\n"
    header = header + "--" + separator + "\r\n"
    header = header + "\r\n.\r\n"
    command.add(Pair("DATA\r\n", 354))
    command.add(Pair(header, 250))
    command.add(Pair("QUIT\r\n", 221))
    return command
}

fun encodeBase64(text: String): String {
    return Base64.getEncoder().encodeToString(text.toByteArray())
}
