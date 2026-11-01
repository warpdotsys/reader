// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.help.http

import java.io.IOException
import java.io.InputStream
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.KeyManager
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

object SSLHelper {

    val unsafeTrustManager: X509TrustManager = object : X509TrustManager {

        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    }

    val unsafeSSLSocketFactory: SSLSocketFactory by lazy {
        try {
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, arrayOf(unsafeTrustManager), SecureRandom())
            sslContext.socketFactory
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    val unsafeHostnameVerifier: HostnameVerifier = HostnameVerifier { _, _ -> true }

    val sslSocketFactory: SSLParams?
        get() = getSslSocketFactoryBase(null, null, null)

    fun getSslSocketFactory(trustManager: X509TrustManager): SSLParams? {
        return getSslSocketFactoryBase(trustManager, null, null)
    }

    fun getSslSocketFactory(vararg certificates: InputStream): SSLParams? {
        return getSslSocketFactoryBase(null, null, null, *certificates)
    }

    fun getSslSocketFactory(
        bksFile: InputStream,
        password: String,
        vararg certificates: InputStream
    ): SSLParams? {
        return getSslSocketFactoryBase(null, bksFile, password, *certificates)
    }

    fun getSslSocketFactory(
        bksFile: InputStream,
        password: String,
        trustManager: X509TrustManager
    ): SSLParams? {
        return getSslSocketFactoryBase(trustManager, bksFile, password)
    }

    private fun getSslSocketFactoryBase(
        trustManager: X509TrustManager?,
        bksFile: InputStream?,
        password: String?,
        vararg certificates: InputStream
    ): SSLParams? {
        val sslParams = SSLParams()
        try {
            val keyManagers = prepareKeyManager(bksFile, password)
            val trustManagers = prepareTrustManager(*certificates)
            val manager = trustManager ?: chooseTrustManager(trustManagers)
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(keyManagers, arrayOf<TrustManager>(manager), null)
            sslParams.sSLSocketFactory = sslContext.socketFactory
            sslParams.trustManager = manager
            return sslParams
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }
        return null
    }

    private fun prepareKeyManager(bksFile: InputStream?, password: String?): Array<KeyManager>? {
        try {
            if (bksFile == null || password == null) {
                return null
            }
            val clientKeyStore = KeyStore.getInstance("BKS")
            clientKeyStore.load(bksFile, password.toCharArray())
            val kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
            kmf.init(clientKeyStore, password.toCharArray())
            return kmf.keyManagers
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun prepareTrustManager(vararg certificates: InputStream): Array<TrustManager> {
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(null)
        certificates.forEachIndexed { index, certStream ->
            val certificateAlias = Integer.toString(index)
            val cert = certificateFactory.generateCertificate(certStream)
            keyStore.setCertificateEntry(certificateAlias, cert)
            try {
                certStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        tmf.init(keyStore)
        return tmf.trustManagers
    }

    private fun chooseTrustManager(trustManagers: Array<TrustManager>): X509TrustManager {
        for (trustManager in trustManagers) {
            if (trustManager is X509TrustManager) {
                return trustManager
            }
        }
        throw NullPointerException()
    }

    class SSLParams {
        lateinit var sSLSocketFactory: SSLSocketFactory
        lateinit var trustManager: X509TrustManager
    }
}
