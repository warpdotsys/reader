// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.help.http

import io.legado.app.utils.EncodingDetect
import io.legado.app.utils.UTF8BOMFighter
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.nio.charset.Charset

class EncodeConverter @JvmOverloads constructor(
    private val encode: String? = null
) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, String> {
        return Converter { value ->
            val responseBytes = UTF8BOMFighter.removeUTF8BOM(value.bytes())
            val encode = this.encode
            if (encode != null) {
                return@Converter String(responseBytes, Charset.forName(encode))
            }
            var charsetName: String? = value.contentType()?.charset()?.displayName()
            if (charsetName == null) {
                charsetName = EncodingDetect.getHtmlEncode(responseBytes)
            }
            String(responseBytes, Charset.forName(charsetName))
        }
    }
}
