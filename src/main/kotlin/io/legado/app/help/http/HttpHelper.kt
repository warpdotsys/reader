// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.help.http

import io.legado.app.constant.AppConst
import io.legado.app.model.DebugLog
import okhttp3.Authenticator
import okhttp3.ConnectionSpec
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

private val proxyClientCache: ConcurrentHashMap<String, OkHttpClient> by lazy {
    ConcurrentHashMap()
}

val okHttpClient: OkHttpClient by lazy {
    val specs = arrayListOf(
        ConnectionSpec.MODERN_TLS,
        ConnectionSpec.COMPATIBLE_TLS,
        ConnectionSpec.CLEARTEXT
    )
    val builder = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .sslSocketFactory(SSLHelper.unsafeSSLSocketFactory, SSLHelper.unsafeTrustManager)
        .retryOnConnectionFailure(true)
        .hostnameVerifier(SSLHelper.unsafeHostnameVerifier)
        .connectionSpecs(specs)
        .followRedirects(true)
        .followSslRedirects(true)
        .addInterceptor(Interceptor { chain ->
            val request = chain.request()
            val builder = request.newBuilder()
            if (request.header("User-Agent") == null) {
                builder.addHeader("User-Agent", AppConst.userAgent)
            } else if (request.header("User-Agent") == "null") {
                builder.removeHeader("User-Agent")
            }
            builder.addHeader("Keep-Alive", "10")
                .addHeader("Connection", "Keep-Alive")
                .addHeader("Cache-Control", "no-cache")
            chain.proceed(builder.build())
        })
    builder.build()
}

fun getProxyClient(proxy: String? = null, debugLog: DebugLog? = null): OkHttpClient {
    if (proxy == null || proxy.isBlank()) {
        if (debugLog == null) {
            return okHttpClient
        }
        val builder = okHttpClient.newBuilder()
        val logInterceptor = HttpLoggingInterceptor(debugLog)
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addNetworkInterceptor(logInterceptor)
        return builder.build()
    }
    if (debugLog == null) {
        proxyClientCache[proxy]?.let { return it }
    }
    val r = Regex("(http|socks4|socks5)://(.*):(\\d{2,5})(@.*@.*)?")
    val group = r.findAll(proxy).first()
    var username = ""
    var password = ""
    val type = if (group.groupValues[1] == "http") "http" else "socks"
    val host = group.groupValues[2]
    val port = group.groupValues[3].toInt()
    if (group.groupValues[4] != "") {
        username = group.groupValues[4].split("@")[1]
        password = group.groupValues[4].split("@")[2]
    }
    if (type != "direct" && host != "") {
        val builder = okHttpClient.newBuilder()
        if (type == "http") {
            builder.proxy(Proxy(Proxy.Type.HTTP, InetSocketAddress(host, port)))
        } else {
            builder.proxy(Proxy(Proxy.Type.SOCKS, InetSocketAddress(host, port)))
        }
        if (username != "" && password != "") {
            val proxyAuthenticator = object : Authenticator {
                @Throws(IOException::class)
                override fun authenticate(route: Route?, response: Response): Request? {
                    val credential = Credentials.basic(username, password)
                    return response.request.newBuilder()
                        .header("Proxy-Authorization", credential).build()
                }
            }
            builder.proxyAuthenticator(proxyAuthenticator)
        }
        if (debugLog != null) {
            val logInterceptor = HttpLoggingInterceptor(debugLog)
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addNetworkInterceptor(logInterceptor)
            return builder.build()
        }
        val proxyClient = builder.build()
        proxyClientCache[proxy] = proxyClient
        return proxyClient
    }
    return okHttpClient
}
