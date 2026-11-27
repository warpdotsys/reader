// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.help.http

import io.legado.app.utils.EncodingDetect
import io.legado.app.utils.GSON
import io.legado.app.utils.Utf8BomUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import kotlin.coroutines.coroutineContext

suspend fun OkHttpClient.newCallResponse(
    retry: Int = 0,
    builder: Request.Builder.() -> Unit
): Response = withContext(Dispatchers.IO) {
    val requestBuilder = Request.Builder()
    builder.invoke(requestBuilder)
    var response: Response? = null
    var i = 0
    while (i <= retry) {
        val index = i++
        response = newCall(requestBuilder.build()).await()
        if (response.isSuccessful) {
            return@withContext response
        }
        if (index != retry) {
            continue
        }
        break
    }
    response!!
}

suspend fun OkHttpClient.newCallResponseBody(
    retry: Int = 0,
    builder: Request.Builder.() -> Unit
): ResponseBody = newCallResponse(retry, builder).let {
    it.body ?: throw IOException(it.message)
}

suspend fun OkHttpClient.newCall(
    retry: Int = 0,
    builder: Request.Builder.() -> Unit
): ResponseBody {
    val requestBuilder = Request.Builder()
    builder.invoke(requestBuilder)
    var response: Response? = null
    var i = 0
    while (i <= retry) {
        val index = i++
        response = newCall(requestBuilder.build()).await()
        if (response.isSuccessful) {
            return response.body!!
        }
        if (index != retry) {
            continue
        }
        break
    }
    return response!!.body ?: throw IOException(response.message)
}

suspend fun OkHttpClient.newCallStrResponse(
    retry: Int = 0,
    builder: Request.Builder.() -> Unit
): StrResponse {
    val requestBuilder = Request.Builder()
    builder.invoke(requestBuilder)
    var response: Response? = null
    var i = 0
    while (i <= retry) {
        val index = i++
        coroutineContext.ensureActive()
        response = newCall(requestBuilder.build()).await()
        if (response.isSuccessful) {
            return StrResponse(response, response.body!!.text())
        }
        if (index != retry) {
            continue
        }
        break
    }
    return StrResponse(response!!, response.body?.text() ?: response.message)
}

suspend fun Call.await(): Response = suspendCancellableCoroutine { block ->
    block.invokeOnCancellation {
        cancel()
    }
    enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            block.resumeWith(Result.failure(e))
        }

        override fun onResponse(call: Call, response: Response) {
            block.resumeWith(Result.success(response))
        }
    })
}

fun ResponseBody.text(encode: String? = null): String {
    val responseBytes = Utf8BomUtils.removeUTF8BOM(bytes())
    var charsetName: String? = null
    charsetName = encode
    if (charsetName != null) {
        return String(responseBytes, Charset.forName(charsetName))
    }
    val contentType = contentType()
    if (contentType != null) {
        val charset = contentType.charset()
        if (charset != null) {
            return String(responseBytes, charset)
        }
    }
    charsetName = EncodingDetect.getHtmlEncode(responseBytes)
    return String(responseBytes, Charset.forName(charsetName))
}

fun Request.Builder.addHeaders(headers: Map<String, String>) {
    headers.forEach {
        addHeader(it.key, it.value)
    }
}

fun Request.Builder.get(
    url: String,
    queryMap: Map<String, String>,
    encoded: Boolean = false
) {
    val httpBuilder = url.toHttpUrl().newBuilder()
    queryMap.forEach {
        if (encoded) {
            httpBuilder.addEncodedQueryParameter(it.key, it.value)
        } else {
            httpBuilder.addQueryParameter(it.key, it.value)
        }
    }
    url(httpBuilder.build())
}

fun Request.Builder.postForm(form: Map<String, String>, encoded: Boolean = false) {
    val formBody = FormBody.Builder()
    form.forEach {
        if (encoded) {
            formBody.addEncoded(it.key, it.value)
        } else {
            formBody.add(it.key, it.value)
        }
    }
    post(formBody.build())
}

fun Request.Builder.postMultipart(type: String?, form: Map<String, Any>) {
    val multipartBody = MultipartBody.Builder()
    type?.let {
        multipartBody.setType(it.toMediaType())
    }
    form.forEach {
        val value = it.value
        if (value is Map<*, *>) {
            val fileName = value["fileName"] as String
            val file = value["file"]
            val contentType = value["contentType"] as? String
            val mediaType = contentType?.toMediaType()
            val requestBody = when (file) {
                is File -> file.asRequestBody(mediaType)
                is ByteArray -> file.toRequestBody(mediaType)
                is String -> file.toRequestBody(mediaType)
                else -> GSON.toJson(file).toRequestBody(mediaType)
            }
            multipartBody.addFormDataPart(it.key, fileName, requestBody)
        } else {
            multipartBody.addFormDataPart(it.key, value.toString())
        }
    }
    post(multipartBody.build())
}

fun Request.Builder.postJson(json: String?) {
    json?.let {
        post(it.toRequestBody("application/json; charset=UTF-8".toMediaType()))
    }
}
