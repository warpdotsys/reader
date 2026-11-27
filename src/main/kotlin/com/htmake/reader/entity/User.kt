// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.entity

data class User(
    var username: String = "",
    var password: String = "",
    var salt: String = "",
    var token: String = "",
    var last_login_at: Long = System.currentTimeMillis(),
    var created_at: Long = System.currentTimeMillis(),
    var enable_webdav: Boolean = false,
    var token_map: Map<String, Long>? = null,
    var enable_local_store: Boolean = false,
    var enable_book_source: Boolean = true,
    var enable_rss_source: Boolean = true,
    var book_source_limit: Int = 100,
    var book_limit: Int = 200
)
