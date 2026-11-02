// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.entity

import java.util.UUID

data class License(
    var host: String = "*",
    var userMaxLimit: Int = 15,
    var expiredAt: Long = 0L,
    var openApi: Boolean = false,
    var simpleWebExpiredAt: Long = 1688140799000L,
    var instances: Int = 1,
    var type: String = "default",
    var id: String = UUID.randomUUID().toString(),
    var code: String = UUID.randomUUID().toString(),
    var verified: Boolean = false,
    var verifyTime: Long? = null
) {

    fun isValid(): Boolean {
        return expiredAt == 0L || expiredAt >= System.currentTimeMillis()
    }

    fun validHost(queryHost: String): Boolean {
        if (!isValid()) {
            return false
        }
        if (queryHost.length == 0) {
            return false
        }
        if ("*" == host) {
            return true
        }
        val hostParts = queryHost.split(":")
        val queryParts = hostParts[0].split(".")
        val hostList = host.split(",")
        for (hostname in hostList) {
            val parts = hostname.split(".")
            if (parts.size != queryParts.size) continue
            var isValid = true
            for (i in parts.indices) {
                if ("*" == parts[i] || parts[i] == queryParts[i]) continue
                isValid = false
            }
            if (!isValid) continue
            return true
        }
        return false
    }

    fun toActiveLicense(): ActiveLicense {
        return ActiveLicense(
            host,
            userMaxLimit,
            expiredAt,
            openApi,
            simpleWebExpiredAt,
            id,
            code,
            verified,
            verifyTime,
            instances,
            type
        )
    }
}
