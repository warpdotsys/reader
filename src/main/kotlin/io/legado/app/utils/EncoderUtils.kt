// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.utils

import java.io.ByteArrayOutputStream
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.jvm.Throws

object EncoderUtils {

    fun escape(src: String): String {
        val tmp = StringBuilder()
        for (charCode in src) {
            val plain = (charCode in '0'..'9') || (charCode in 'A'..'Z') || (charCode in 'a'..'z')
            if (plain) {
                tmp.append(charCode)
            } else {
                val prefix = if (charCode < '\u0010') "%0" else if (charCode < '\u0100') "%" else "%u"
                tmp.append(prefix).append(Integer.toString(charCode.code, 16))
            }
        }
        return tmp.toString()
    }

    @JvmOverloads
    fun base64Decode(str: String, flags: Int = 0): String {
        val bytes = Base64.decode(str, flags)
        return String(bytes, Charsets.UTF_8)
    }

    @JvmOverloads
    fun base64Encode(str: String, flags: Int = Base64.NO_WRAP): String {
        return Base64.encodeToString(str.toByteArray(Charsets.UTF_8), flags)
    }

    @Throws(Exception::class)

    fun encryptAES2Base64(
        data: ByteArray?,
        key: ByteArray?,
        transformation: String? = "DES/ECB/PKCS5Padding",
        iv: ByteArray? = null
    ): ByteArray? {
        return Base64.encode(encryptAES(data, key, transformation, iv), Base64.NO_WRAP)
    }

    @Throws(Exception::class)

    fun encryptAES(
        data: ByteArray?,
        key: ByteArray?,
        transformation: String? = "DES/ECB/PKCS5Padding",
        iv: ByteArray? = null
    ): ByteArray? {
        return symmetricTemplate(data, key, "AES", transformation!!, iv, true)
    }

    @Throws(Exception::class)

    fun decryptBase64AES(
        data: ByteArray?,
        key: ByteArray?,
        transformation: String = "DES/ECB/PKCS5Padding",
        iv: ByteArray? = null
    ): ByteArray? {
        return decryptAES(Base64.decode(data, Base64.NO_WRAP), key, transformation, iv)
    }

    @Throws(Exception::class)

    fun decryptAES(
        data: ByteArray?,
        key: ByteArray?,
        transformation: String = "DES/ECB/PKCS5Padding",
        iv: ByteArray? = null
    ): ByteArray? {
        return symmetricTemplate(data, key, "AES", transformation, iv, false)
    }

    @Throws(Exception::class)
    private fun symmetricTemplate(
        data: ByteArray?,
        key: ByteArray?,
        algorithm: String,
        transformation: String,
        iv: ByteArray?,
        isEncrypt: Boolean
    ): ByteArray? {
        if (data == null || data.isEmpty() || key == null || key.isEmpty()) {
            return null
        }
        val keySpec = SecretKeySpec(key, algorithm)
        val cipher = Cipher.getInstance(transformation)
        val mode = if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE
        if (iv == null || iv.isEmpty()) {
            cipher.init(mode, keySpec)
        } else {
            cipher.init(mode, keySpec, IvParameterSpec(iv))
        }
        return cipher.doFinal(data)
    }

    @Throws(Exception::class)

    fun encryptDES2Base64(
        data: ByteArray?,
        key: ByteArray?,
        transformation: String? = "DES/ECB/PKCS5Padding",
        iv: ByteArray? = null
    ): ByteArray? {
        return Base64.encode(encryptDES(data, key, transformation, iv), Base64.NO_WRAP)
    }

    @Throws(Exception::class)

    fun encryptDES(
        data: ByteArray?,
        key: ByteArray?,
        transformation: String? = "DES/ECB/PKCS5Padding",
        iv: ByteArray? = null
    ): ByteArray? {
        return symmetricTemplate(data, key, "DES", transformation!!, iv, true)
    }

    @Throws(Exception::class)

    fun decryptBase64DES(
        data: ByteArray?,
        key: ByteArray?,
        transformation: String = "DES/ECB/PKCS5Padding",
        iv: ByteArray? = null
    ): ByteArray? {
        return decryptDES(Base64.decode(data, Base64.NO_WRAP), key, transformation, iv)
    }

    @Throws(Exception::class)

    fun decryptDES(
        data: ByteArray?,
        key: ByteArray?,
        transformation: String = "DES/ECB/PKCS5Padding",
        iv: ByteArray? = null
    ): ByteArray? {
        return symmetricTemplate(data, key, "DES", transformation, iv, false)
    }

    @Throws(Exception::class)

    fun encryptDESede2Base64(
        data: ByteArray?,
        key: ByteArray?,
        transformation: String? = "DESede/ECB/PKCS5Padding",
        iv: ByteArray? = null
    ): ByteArray? {
        return Base64.encode(encryptDESede(data, key, transformation, iv), Base64.NO_WRAP)
    }

    @Throws(Exception::class)

    fun encryptDESede(
        data: ByteArray?,
        key: ByteArray?,
        transformation: String? = "DESede/ECB/PKCS5Padding",
        iv: ByteArray? = null
    ): ByteArray? {
        return symmetricTemplate(data, key, "DESede", transformation!!, iv, true)
    }

    @Throws(Exception::class)

    fun decryptBase64DESede(
        data: ByteArray?,
        key: ByteArray?,
        transformation: String = "DESede/ECB/PKCS5Padding",
        iv: ByteArray? = null
    ): ByteArray? {
        return decryptDESede(Base64.decode(data, Base64.NO_WRAP), key, transformation, iv)
    }

    @Throws(Exception::class)

    fun decryptDESede(
        data: ByteArray?,
        key: ByteArray?,
        transformation: String = "DESede/ECB/PKCS5Padding",
        iv: ByteArray? = null
    ): ByteArray? {
        return symmetricTemplate(data, key, "DESede", transformation, iv, false)
    }

    fun encryptByPrivateKey(input: String, privateKey: PrivateKey): String {
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, privateKey)
        val encrypt = cipher.doFinal(input.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encrypt, Base64.NO_WRAP)
    }

    fun decryptByPublicKey(input: String, publicKey: PublicKey): String {
        val decode = Base64.decode(input, Base64.NO_WRAP)
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.DECRYPT_MODE, publicKey)
        val encrypt = cipher.doFinal(decode)
        return String(encrypt, Charsets.UTF_8)
    }

    fun encryptByPublicKey(input: String, publicKey: PublicKey): String {
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val encrypt = cipher.doFinal(input.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encrypt, Base64.NO_WRAP)
    }

    fun decryptByPrivateKey(input: String, privateKey: PrivateKey): String {
        val decode = Base64.decode(input, Base64.NO_WRAP)
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        val encrypt = cipher.doFinal(decode)
        return String(encrypt, Charsets.UTF_8)
    }

    fun encryptSegmentByPrivateKey(
        input: String,
        privateKey: PrivateKey,
        keySize: Int = 2048
    ): String {
        val bytes = input.toByteArray(Charsets.UTF_8)
        var offset = 0
        val maxEncryptBlock = keySize / 8 - 11
        val bos = ByteArrayOutputStream()
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, privateKey)
        while (bytes.size - offset > 0) {
            val temp: ByteArray = if (bytes.size - offset >= maxEncryptBlock) {
                val block = cipher.doFinal(bytes, offset, maxEncryptBlock)
                offset += maxEncryptBlock
                block
            } else {
                val block = cipher.doFinal(bytes, offset, bytes.size - offset)
                offset = bytes.size
                block
            }
            bos.write(temp)
        }
        bos.close()
        return Base64.encodeToString(bos.toByteArray(), Base64.NO_WRAP)
    }

    fun decryptSegmentByPublicKey(
        input: String,
        publicKey: PublicKey,
        keySize: Int = 2048
    ): String? {
        val bytes = Base64.decode(input, Base64.NO_WRAP)
        var offset = 0
        val maxDecryptBlock = keySize / 8
        val bos = ByteArrayOutputStream()
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.DECRYPT_MODE, publicKey)
        while (bytes.size - offset > 0) {
            val temp: ByteArray = if (bytes.size - offset >= maxDecryptBlock) {
                val block = cipher.doFinal(bytes, offset, maxDecryptBlock)
                offset += maxDecryptBlock
                block
            } else {
                val block = cipher.doFinal(bytes, offset, bytes.size - offset)
                offset = bytes.size
                block
            }
            bos.write(temp)
        }
        bos.close()
        return String(bos.toByteArray(), Charsets.UTF_8)
    }

    fun encryptSegmentByPublicKey(
        input: String,
        publicKey: PublicKey,
        keySize: Int = 2048
    ): String {
        val bytes = input.toByteArray(Charsets.UTF_8)
        var offset = 0
        val maxEncryptBlock = keySize / 8 - 11
        val bos = ByteArrayOutputStream()
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        while (bytes.size - offset > 0) {
            val temp: ByteArray = if (bytes.size - offset >= maxEncryptBlock) {
                val block = cipher.doFinal(bytes, offset, maxEncryptBlock)
                offset += maxEncryptBlock
                block
            } else {
                val block = cipher.doFinal(bytes, offset, bytes.size - offset)
                offset = bytes.size
                block
            }
            bos.write(temp)
        }
        bos.close()
        return Base64.encodeToString(bos.toByteArray(), Base64.NO_WRAP)
    }

    fun decryptSegmentByPrivateKey(
        input: String,
        privateKey: PrivateKey,
        keySize: Int = 2048
    ): String? {
        val bytes = Base64.decode(input, Base64.NO_WRAP)
        var offset = 0
        val maxDecryptBlock = keySize / 8
        val bos = ByteArrayOutputStream()
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        while (bytes.size - offset > 0) {
            val temp: ByteArray = if (bytes.size - offset >= maxDecryptBlock) {
                val block = cipher.doFinal(bytes, offset, maxDecryptBlock)
                offset += maxDecryptBlock
                block
            } else {
                val block = cipher.doFinal(bytes, offset, bytes.size - offset)
                offset = bytes.size
                block
            }
            bos.write(temp)
        }
        bos.close()
        return String(bos.toByteArray(), Charsets.UTF_8)
    }

    fun generateKeys(): KeyPair {
        val generator = KeyPairGenerator.getInstance("RSA")
        return generator.genKeyPair()
    }
}
