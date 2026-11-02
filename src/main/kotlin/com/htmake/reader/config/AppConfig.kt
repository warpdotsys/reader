// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.config

open class AppConfig {

    open var workDir: String = ""

    open var showUI: Boolean = false

    open var debug: Boolean = false

    open var packaged: Boolean = false

    open var secure: Boolean = false

    open var inviteCode: String = ""

    open var secureKey: String = ""

    open var cacheChapterContent: Boolean = false

    open var userLimit: Int = 15

    open var userBookLimit: Int = 200

    open var debugLog: Boolean = false

    open var autoClearInactiveUser: Int = 0

    open var exportUseReplace: Boolean = false

    open var exportCharset: String = "UTF-8"

    open var exportNoChapterName: Boolean = false

    open var exportPictureFile: Boolean = false

    open var mongoUri: String = ""

    open var mongoDbName: String = "reader"

    open var shelfUpdateInteval: Int = 10

    open var remoteWebviewApi: String = ""

    open var defaultUserEnableWebdav: Boolean = false

    open var defaultUserEnableLocalStore: Boolean = false

    open var defaultUserEnableBookSource: Boolean = true

    open var defaultUserEnableRssSource: Boolean = true

    open var defaultUserBookSourceLimit: Int = 200

    open var defaultUserBookLimit: Int = 200

    open var autoBackupUserData: Boolean = false

    open var minUserPasswordLength: Int = 8

    open var remoteBookSourceUpdateInterval: Int = 720
}
