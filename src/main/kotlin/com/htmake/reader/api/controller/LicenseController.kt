// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.api.controller

import com.htmake.reader.api.ReturnData
import com.htmake.reader.entity.ActiveLicense
import com.htmake.reader.entity.License
import com.htmake.reader.utils.SpringContextUtils
import com.htmake.reader.utils.asJsonArray
import com.htmake.reader.utils.decryptData
import com.htmake.reader.utils.decryptToLicense
import com.htmake.reader.utils.getInstalledLicense
import com.htmake.reader.utils.getStorage
import com.htmake.reader.utils.jsonEncode
import com.htmake.reader.utils.saveStorage
import com.htmake.reader.utils.sendEmail
import com.htmake.reader.utils.setLicenseValid
import com.htmake.reader.utils.success
import com.htmake.reader.utils.validateEmail
import io.legado.app.utils.ACache
import io.legado.app.utils.Base64
import io.legado.app.utils.EncoderUtils
import io.vertx.core.buffer.Buffer
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.client.HttpResponse
import io.vertx.ext.web.client.WebClient
import io.vertx.kotlin.coroutines.awaitResult
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.slf4j.MDCContext
import mu.KLogger
import mu.KotlinLogging
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID
import kotlin.coroutines.CoroutineContext

private val logger: KLogger = KotlinLogging.logger { }

class LicenseController(coroutineContext: CoroutineContext) : BaseController(coroutineContext) {

    private val webClient: WebClient by lazy {
        SpringContextUtils.getBean("webClient", WebClient::class.java)
    }

    private var privateKeyContent: String = ""

    private var tryCodeCache: ACache = ACache.get("tryCodeCache", 2000000L, 10000)

    val backupFileNames: Array<String> by lazy {
        arrayOf(
            "bookSource.json",
            "bookshelf.json",
            "bookGroup.json",
            "rssSources.json",
            "replaceRule.json",
            "bookmark.json",
            "userConfig.json"
        )
    }

    suspend fun getLicense(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        val license = getInstalledLicense()
        return returnData.setData(mapOf("license" to license))
    }

    suspend fun importLicense(context: RoutingContext) {
        val returnData = ReturnData()
        if (!checkAuth(context)) {
            context.success(returnData.setData("NEED_LOGIN").setErrorMsg("请登录后使用"))
            return
        }
        if (!checkManagerAuth(context)) {
            context.success(returnData.setData("NEED_SECURE_KEY").setErrorMsg("请输入管理密码"))
            return
        }
        val content = context.bodyAsJson.getString("content") ?: ""
        if (content.isEmpty()) {
            context.success(returnData.setErrorMsg("请输入密钥"))
            return
        }
        val exceptionHandler = CoroutineExceptionHandler { _, ex ->
            logger.info("activate license error: {}", ex.message)
            context.success(returnData.setErrorMsg("密钥激活失败: ${ex.message}"))
        }
        var checkUrl = "https://r.htmake.com/reader3/activateLicense"
        launch(MDCContext() + Dispatchers.IO + exceptionHandler) {
            val result = awaitResult<HttpResponse<Buffer>> { handler ->
                webClient.postAbs(checkUrl).timeout(5000L)
                    .sendJsonObject(JsonObject(mapOf("content" to content)), handler)
            }
            runCatching {
                result?.bodyAsJsonObject()?.let {
                    if (!it.getBoolean("isSuccess")) {
                        throw Exception(it.getString("errorMsg"))
                    }
                    it.getJsonObject("data")?.getString("result")?.let {
                        val license = decryptToLicense(it) ?: throw Exception("密钥错误")
                        if (!license.validHost(context.request().host())) {
                            throw Exception("密钥授权域名错误")
                        }
                        Pair(it, license)
                    }
                }
            }.onSuccess {
                if (it != null) {
                    saveStorage("data", "license", value = it.first, ext = ".key")
                    context.success(returnData.setData(mapOf("license" to it.second)))
                } else {
                    context.success(returnData.setErrorMsg("密钥激活失败"))
                }
            }.onFailure {
                logger.info("import license error: {}", it.message)
                context.success(returnData.setErrorMsg(it.message ?: "密钥激活错误"))
            }
        }
    }

    suspend fun generateKeys(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        val keyPair = EncoderUtils.generateKeys()
        return returnData.setData(
            mapOf(
                "publicKey" to Base64.encodeToString(keyPair.public.encoded, 2),
                "privateKey" to Base64.encodeToString(keyPair.private.encoded, 2)
            )
        )
    }

    suspend fun generateLicense(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        var host = ""
        var expiredAt = 0L
        var userMaxLimit = 0
        var openApi = false
        var simpleWebExpiredAt = 0L
        var instances = 0
        var type = ""
        var key = ""
        var code = ""
        if (context.request().method() == HttpMethod.POST) {
            host = context.bodyAsJson.getString("host") ?: ""
            expiredAt = context.bodyAsJson.getLong("expiredAt") ?: 0L
            userMaxLimit = context.bodyAsJson.getInteger("userMaxLimit") ?: 15
            openApi = context.bodyAsJson.getBoolean("openApi") ?: false
            simpleWebExpiredAt = context.bodyAsJson.getLong("simpleWebExpiredAt") ?: 0L
            instances = context.bodyAsJson.getInteger("instances") ?: 1
            type = context.bodyAsJson.getString("type") ?: ""
            key = context.bodyAsJson.getString("key") ?: ""
            code = context.bodyAsJson.getString("code") ?: ""
        } else {
            host = context.queryParam("host").firstOrNull() ?: ""
            expiredAt = context.queryParam("expiredAt").firstOrNull()?.toLong() ?: 0L
            userMaxLimit = context.queryParam("userMaxLimit").firstOrNull()?.toInt() ?: 15
            openApi = context.queryParam("openApi").firstOrNull()?.toBoolean() ?: false
            simpleWebExpiredAt = context.queryParam("simpleWebExpiredAt").firstOrNull()?.toLong() ?: 0L
            instances = context.queryParam("instances").firstOrNull()?.toInt() ?: 1
            type = context.queryParam("type").firstOrNull() ?: ""
            key = context.queryParam("key").firstOrNull() ?: ""
            code = context.queryParam("code").firstOrNull() ?: ""
        }
        if (host.isEmpty()) {
            return returnData.setErrorMsg("请输入域名")
        }
        if (!"Pvkp7tMQJpi4kWBE".equals(key)) {
            return returnData.setErrorMsg("参数错误")
        }
        val license = License(
            host,
            userMaxLimit,
            expiredAt,
            openApi,
            simpleWebExpiredAt,
            instances,
            type,
            verified = false
        )
        if (code.isNotEmpty()) {
            license.code = code
        }
        val licenseContent = jsonEncode(license)
        if (privateKeyContent.isEmpty()) {
            privateKeyContent = getStorage("data", "privateKey", ext = ".key") ?: ""
        }
        val privateKey = KeyFactory.getInstance("RSA")
            .generatePrivate(PKCS8EncodedKeySpec(Base64.decode(privateKeyContent, 2)))
        val licenseKey = EncoderUtils.encryptSegmentByPrivateKey(licenseContent, privateKey)
        return returnData.setData(mapOf("host" to host, "key" to licenseKey))
    }

    suspend fun isHostValid(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        var host: String
        if (context.request().method() == HttpMethod.POST) {
            host = context.bodyAsJson.getString("host")
        } else {
            host = context.queryParam("host").firstOrNull() ?: ""
        }
        val license = getInstalledLicense()
        return returnData.setData(mapOf("isValid" to license.validHost(host)))
    }

    suspend fun decryptLicense(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        val content = context.bodyAsJson.getString("content") ?: ""
        if (content.isEmpty()) {
            return returnData.setErrorMsg("请输入密钥")
        }
        val license = decryptToLicense(content) ?: return returnData.setErrorMsg("密钥错误")
        return returnData.setData(license)
    }

    suspend fun activateLicense(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        val content = context.bodyAsJson.getString("content") ?: ""
        if (content.isEmpty()) {
            return returnData.setErrorMsg("请输入密钥")
        }
        val license = decryptToLicense(content) ?: return returnData.setErrorMsg("密钥错误")
        if (license.verified) {
            return returnData.setErrorMsg("密钥已被使用")
        }
        val activeLicenseList = asJsonArray(getStorage("data", "activeLicense")) ?: JsonArray()
        var activeTimes = 0
        for (i in 0 until activeLicenseList.size()) {
            val activeLicense = activeLicenseList.getJsonObject(i).mapTo(ActiveLicense::class.java)
            if (activeLicense.type.equals(license.type) && activeLicense.code.equals(license.code)) {
                activeTimes++
            }
        }
        if (activeTimes >= license.instances) {
            return returnData.setErrorMsg("密钥已超过最大使用次数")
        }
        var ip = context.request().getHeader("X-Real-IP")
        if (ip.isNullOrEmpty()) {
            ip = context.request().remoteAddress()?.host()
        }
        license.verified = true
        license.verifyTime = System.currentTimeMillis()
        license.id = UUID.randomUUID().toString()
        val activeLicense = license.toActiveLicense()
        activeLicense.activeOrder = activeTimes + 1
        activeLicense.activeTime = System.currentTimeMillis()
        activeLicense.activeIp = ip
        activeLicense.activeEmail = ""
        val activeLicenseObject: JsonObject = JsonObject.mapFrom(activeLicense)
        activeLicenseList.add(activeLicenseObject)
        logger.info("activeLicenseList: {}", activeLicenseList)
        saveStorage("data", "activeLicense", value = activeLicenseList)
        val licenseContent = jsonEncode(license)
        if (privateKeyContent.isEmpty()) {
            privateKeyContent = getStorage("data", "privateKey", ext = ".key") ?: ""
        }
        val privateKey = KeyFactory.getInstance("RSA")
            .generatePrivate(PKCS8EncodedKeySpec(Base64.decode(privateKeyContent, 2)))
        val licenseKey = EncoderUtils.encryptSegmentByPrivateKey(licenseContent, privateKey)
        return returnData.setData(mapOf("result" to licenseKey))
    }

    suspend fun isLicenseValid(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        var id: String
        if (context.request().method() == HttpMethod.POST) {
            id = context.bodyAsJson.getString("id")
        } else {
            id = context.queryParam("id").firstOrNull() ?: ""
        }
        val activeLicenseList = asJsonArray(getStorage("data", "activeLicense")) ?: JsonArray()
        var activeLicense: ActiveLicense? = null
        var index = -1
        for (i in 0 until activeLicenseList.size()) {
            val temp = activeLicenseList.getJsonObject(i).mapTo(ActiveLicense::class.java)
            if (temp.id.equals(id)) {
                activeLicense = temp
                index = i
                break
            }
        }
        val result = mutableMapOf<String, Any>()
        var ip = context.request().getHeader("X-Real-IP")
        if (ip.isNullOrEmpty()) {
            ip = context.request().remoteAddress()?.host() ?: ""
        }
        if (activeLicense == null) {
            result["isValid"] = false
            result["errorMsg"] = "密钥未激活"
        } else {
            result["isValid"] = activeLicense.verified
            result["errorMsg"] = activeLicense.errorMsg
            if (activeLicense.lastOnlineTime != null) {
                if (System.currentTimeMillis() < activeLicense.lastOnlineTime!! + 600000 &&
                    !ip.equals(activeLicense.lastOnlineIp)
                ) {
                    result["repeat"] = mapOf(
                        "lastOnlineTime" to activeLicense.lastOnlineTime,
                        "lastOnlineIp" to activeLicense.lastOnlineIp
                    )
                }
            }
            activeLicense.lastOnlineTime = System.currentTimeMillis()
            activeLicense.lastOnlineIp = ip
            activeLicenseList.set(index, JsonObject.mapFrom(activeLicense))
            saveStorage("data", "activeLicense", value = activeLicenseList)
        }
        val resultContent = jsonEncode(result)
        if (privateKeyContent.isEmpty()) {
            privateKeyContent = getStorage("data", "privateKey", ext = ".key") ?: ""
        }
        val privateKey = KeyFactory.getInstance("RSA")
            .generatePrivate(PKCS8EncodedKeySpec(Base64.decode(privateKeyContent, 2)))
        val resultKey = EncoderUtils.encryptSegmentByPrivateKey(resultContent, privateKey)
        return returnData.setData(mapOf("result" to resultKey))
    }

    suspend fun checkLicense(license: License) {
        val exceptionHandler = CoroutineExceptionHandler { _, ex ->
            logger.info("check license error: {}", ex.message)
        }
        var checkUrl = "https://r.htmake.com/reader3/isLicenseValid?id=${license.id}"
        launch(MDCContext() + Dispatchers.IO + exceptionHandler) {
            val result = awaitResult<HttpResponse<Buffer>> { handler ->
                webClient.getAbs(checkUrl).timeout(5000L).send(handler)
            }
            runCatching {
                result?.bodyAsJsonObject()?.let {
                    logger.info("isLicenseValid: {}", it)
                    it.getJsonObject("data")?.getString("result")?.let {
                        JsonObject(decryptData(it)).let {
                            val isValid = it.getBoolean("isValid") ?: true
                            setLicenseValid(isValid)
                            if (!isValid) {
                                logger.info("密钥错误：{}", it.getString("errorMsg") ?: "")
                            }
                            it.getJsonObject("repeat")?.let {
                                logger.info(
                                    "请勿重复使用授权，上次检查时间：{}，上次检查ip：{}",
                                    LocalDateTime.ofInstant(
                                        Instant.ofEpochMilli(it.getLong("lastOnlineTime")),
                                        ZoneId.systemDefault()
                                    ),
                                    it.getString("lastOnlineIp")
                                )
                            }
                        }
                    }
                }
            }.onFailure {
                logger.info("check license error: {}", it.message)
            }
        }
    }

    suspend fun sendCodeToEmail(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        var email: String
        if (context.request().method() == HttpMethod.POST) {
            email = context.bodyAsJson.getString("email") ?: ""
        } else {
            email = context.queryParam("email").firstOrNull() ?: ""
        }
        if (email.isEmpty()) {
            return returnData.setErrorMsg("邮箱错误")
        }
        if (!validateEmail(email)) {
            return returnData.setErrorMsg("仅支持 163|126|qq|yahoo|sina|sohu|yeah|139|189|21cn|outlook|gmail|icloud 等邮箱")
        }
        val activeLicenseList = asJsonArray(getStorage("data", "activeLicense")) ?: JsonArray()
        for (i in 0 until activeLicenseList.size()) {
            if ("trial".equals(activeLicenseList.getJsonObject(i).getString("type")) &&
                email.equals(activeLicenseList.getJsonObject(i).getString("code"))
            ) {
                return returnData.setErrorMsg("该邮箱已被使用")
            }
        }
        val verifyCode = tryCodeCache.getAsString(email)
        if (!verifyCode.isNullOrEmpty()) {
            return returnData.setData("", "您的验证码仍在有效期内，请勿重复获取")
        }
        val code = UUID.randomUUID().toString().substring(0, 6)
        tryCodeCache.put(email, code, 900)
        sendEmail(
            email,
            "Reader Kindle端的试用申请验证",
            "您正在申请Reader Kindle端的试用，验证码是: $code，15分钟内有效，请勿回复"
        )
        return returnData.setData("", "请查收邮件")
    }

    suspend fun supplyLicense(context: RoutingContext): ReturnData {
        val returnData = ReturnData()
        val email = context.bodyAsJson.getString("email") ?: ""
        val code = context.bodyAsJson.getString("code") ?: ""
        if (email.isEmpty() || code.isEmpty()) {
            return returnData.setErrorMsg("参数错误")
        }
        val verifyCode = tryCodeCache.getAsString(email)
        tryCodeCache.remove(email)
        if (!code.equals(verifyCode)) {
            return returnData.setErrorMsg("验证码错误")
        }
        val expiredAt = System.currentTimeMillis() + 604800000L
        val license = License(
            "*",
            15,
            0L,
            false,
            expiredAt,
            1,
            "trial",
            code = email,
            verified = false
        )
        val licenseContent = jsonEncode(license)
        if (privateKeyContent.isEmpty()) {
            privateKeyContent = getStorage("data", "privateKey", ext = ".key") ?: ""
        }
        val privateKey = KeyFactory.getInstance("RSA")
            .generatePrivate(PKCS8EncodedKeySpec(Base64.decode(privateKeyContent, 2)))
        val licenseKey = EncoderUtils.encryptSegmentByPrivateKey(licenseContent, privateKey)
        return returnData.setData(mapOf("key" to licenseKey))
    }
}
