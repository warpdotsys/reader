<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=289916ad；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog v-if="$store.getters.isNormalPage" :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' :title='isShowBookGroupSettingDialog ? "设置分组" : "分组管理"' :visible="show" :width="dialogWidth" :top="dialogTop" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }" @opened="opened"><div class="source-container table-container"><el-table :key="isShowBookGroupSettingDialog" ref='"bookGroupTableRef"' :data="showBookGroupList" :height="dialogContentHeight" @selection-change="(t) => {
                            bookGroupSelection = t
                        }"><template v-if="isShowBookGroupSettingDialog"><el-table-column type="selection" width="25" /></template><el-table-column property="groupName" label="分组名" min-width="100"><template v-slot:default="o"><div class="drag-icon"><i class="el-icon-rank" /></div><span>{{ " " + _s(displayBookGroupName(o.row)) }}</span></template></el-table-column><template v-if="!(isShowBookGroupSettingDialog)"><el-table-column property="show" label="显示" min-width="80"><template v-slot:default="o"><el-switch active-color="#13ce66" inactive-color="#ff4949" :active-value="true" :inactive-value="false" v-model="o.row.show" @change="(t) => {
                                        return toggleBookGroupShow(o.row, t)
                                    }" /></template></el-table-column></template><el-table-column label="操作" width="100px"><template v-slot:default="o"><el-button type="text" @click="(t) => {
                                        return saveBookGroup(o.row)
                                    }">编辑</el-button><template v-if="!isShowBookGroupSettingDialog && o.row.groupId > 0 && !getShowShelfBooks(o.row.groupId).length"><el-button style="color: #f56c6c" type="text" @click="(t) => {
                                        return deleteBookGroup(o.row)
                                    }">删除</el-button></template></template></el-table-column></el-table></div><div class="dialog-footer" slot="footer"><el-button class="float-left" type="primary" size="medium" @click="(t) => {
                            return saveBookGroup()
                        }">添加分组</el-button><template v-if="isShowSaveOrderButton"><el-button class="float-left" type="primary" size="medium" @click="(t) => {
                            return saveOrder()
                        }">保存排序</el-button></template><template v-if="isShowBookGroupSettingDialog"><el-button type="primary" size="medium" @click="setBookGroup">确认</el-button></template><el-button size="medium" @click="cancel">取消</el-button></div></el-dialog></el-dialog>
</template>

<script>
// 依赖的 webpack 模块 id 见同目录 _meta.json 与 docs/FRONTEND-RECONSTRUCTION.md
export default {
  name: "app",
    components: {
    ImageViewer: f["a"],
    ReplaceRule: w,
    ReplaceRuleForm: y,
    MPCode: $,
    BookManage: G,
    BookInfo: J,
    UserManage: te,
    UserForm: ce,
    BookGroup: ge,
    RssSourceList: Ae,
    RssArticleList: Ie,
    RssArticle: Fe,
    SearchBookContent: _e,
    Bookmark: Ve,
    BookmarkForm: Ze,
    FileManager: at,
    BookCover: ht,
    License: St,
    BookConfig: Ot,
    RemoteBookSourceSub: Ut,
    ActiveLicense: Gt,
    HttpTTS: Kt
    },
    data: function() {
    return {
    remember: !0,
    loginForm: {
    username: "",
    password: "",
    code: ""
    },
    showEditor: !1,
    editorTitle: "编辑器",
    editorContent: "",
    showReplaceRuleDialog: !1,
    showReplaceRuleForm: !1,
    replaceRule: {},
    isAddReplaceRule: !0,
    showMPCodeDialog: !1,
    showBookManageDialog: !1,
    showBookInfoDialog: !1,
    showUserManageDialog: !1,
    showUserFormDialog: !1,
    userForm: {},
    showBookGroupDialog: !1,
    isSetBookGroup: !1,
    showRssSourceListDialog: !1,
    showRssArticleListDialog: !1,
    rssSource: {},
    showRssArticleDialog: !1,
    rssArticleInfo: {},
    showSearchBookContentDialog: !1,
    searchBook: {},
    isLogin: !0,
    showBookmarkDialog: !1,
    showBookmarkForm: !1,
    bookmark: {},
    isAddBookmark: !0,
    bookmarkInBook: {},
    showFileManager: !1,
    fileManagerHome: "",
    fileManagerTitle: "文件管理",
    showBookCoverDialog: !1,
    bookCoverBook: {},
    showLicenseDialog: !1,
    showBookConfigDialog: !1,
    showRemoteBookSourceSubDialog: !1,
    showActiveLicenseDialog: !1,
    showHttpTTSDialog: !1
    }
    },
    beforeCreate: function() {
    var e = this;
    this.$store.dispatch("init"), this.$store.dispatch("syncFromLocalStorage"), this.$store.commit("setMiniInterface", Object(U["e"])()), document.documentElement.style.setProperty("--vh", "".concat(.01 * window.innerHeight, "px")), window.onresize = function() {
    document.documentElement.style.setProperty("--vh", "".concat(.01 * window.innerHeight, "px")), e.$store.commit("setMiniInterface", Object(U["e"])()), e.$store.commit("setWindowSize", {
    width: window.innerWidth,
    height: window.innerHeight
    }), e.$store.commit("setTouchable", "ontouchstart" in document)
    };
    var t = window.getQueryString("api");
    t && this.$store.commit("setApi", t), (window.navigator.userAgent.indexOf("iPhone") >= 0 || window.navigator.userAgent.indexOf("iPad") >= 0) && (document.documentElement.style.setProperty("height", "100vh"), document.body.style.setProperty("height", "100vh"));
    try {
    var o = getComputedStyle(document.documentElement);
    this.$store.commit("setSafeArea", {
    top: 0 | o.getPropertyValue("--sat"),
    bottom: 0 | o.getPropertyValue("--sab"),
    left: 0 | o.getPropertyValue("--sal"),
    right: 0 | o.getPropertyValue("--sar")
    })
    } catch (n) {}
    },
    created: function() {
    var e = this;
    return Object(c["a"])(Object(s["a"])().mark((function t() {
    return Object(s["a"])().wrap((function(t) {
    while (1) switch (t.prev = t.next) {
    case 0:
    return e.$route && e.$route.query && e.$route.query.api && e.$store.commit("setApi", e.$route.query.api), window.matchMedia("(prefers-color-scheme: dark)").addEventListener("change", (function() {
    e.autoSetTheme(e.autoTheme)
    })), t.next = 4, e.tauriInit()["catch"]((function() {}));
    case 4:
    e.getUserInfo().then((function() {
    e.$store.state.notAutoSync || e.restoreUserConfig(!0), e.$store.dispatch("syncFromLocalStorage"), e.autoSetTheme(e.autoTheme), e.init()
    })), e.loadTxtTocRules();
    case 6:
    case "end":
    return t.stop()
    }
    }), t)
    })))()
    },
    beforeMount: function() {
    var e = this;
    this.setTheme(this.isNight), this.setMiniInterfaceClass(), this.setPageTypeClass(), this.setDocumentProperty(), this.eventBus = u["a"], u["a"].$on("showEditor", this.showEditorListener), u["a"].$on("showReplaceRuleForm", this.showReplaceRuleFormListener), u["a"].$on("showReplaceRuleDialog", (function() {
    e.showReplaceRuleDialog = !0
    })), u["a"].$on("showMPCodeDialog", (function() {
    e.showMPCodeDialog = !0
    })), u["a"].$on("showBookManageDialog", (function() {
    e.showBookManageDialog = !0
    })), u["a"].$on("showBookInfoDialog", (function(t) {
    e.showBookInfo = t, e.showBookInfoDialog = !0
    })), u["a"].$on("showUserManageDialog", (function() {
    e.showUserManageDialog = !0
    })), u["a"].$on("showUserFormDialog", (function(t) {
    e.showUserFormDialog = !0, e.userForm = Object(i["a"])({}, t)
    })), u["a"].$on("showBookGroupDialog", (function(t) {
    e.showBookGroupDialog = !0, e.isSetBookGroup = !!t
    })), u["a"].$on("showRssArticleListDialog", (function(t) {
    e.showRssArticleListDialog = !0, e.rssSource = t
    })), u["a"].$on("showRssSourceListDialog", (function() {
    e.showRssSourceListDialog = !0
    })), u["a"].$on("showRssArticleDialog", (function(t) {
    e.showRssArticleDialog = !0, e.rssArticleInfo = t
    })), u["a"].$on("showSearchBookContentDialog", (function(t) {
    e.showSearchBookContentDialog = !0, e.searchBook = t
    })), u["a"].$on("showBookmarkForm", (function(t, o, n) {
    e.bookmark = t, e.isAddBookmark = o, e.bookmarkCallback = n, e.showBookmarkForm = !0
    })), u["a"].$on("showBookmarkDialog", (function(t) {
    e.showBookmarkDialog = !0, e.bookmarkInBook = t
    })), u["a"].$on("showFileManagerDialog", (function(t, o) {
    e.fileManagerHome = t, e.fileManagerTitle = o, e.showFileManager = !0
    })), u["a"].$on("showBookCoverDialog", (function(t) {
    e.showBookCoverDialog = !0, e.bookCoverBook = t
    })), u["a"].$on("showBookConfigDialog", (function() {
    e.showBookConfigDialog = !0
    })), u["a"].$on("showLicenseDialog", (function() {
    e.showLicenseDialog = !0
    })), u["a"].$on("showRemoteBookSourceSubDialog", (function() {
    e.showRemoteBookSourceSubDialog = !0
    })), u["a"].$on("showActiveLicenseDialog", (function() {
    e.showActiveLicenseDialog = !0
    })), u["a"].$on("showHttpTTSDialog", (function() {
    e.showHttpTTSDialog = !0
    }))
    },
    mounted: function() {
    document.documentElement.style.setProperty("--vh", "".concat(.01 * window.innerHeight, "px")), window.reader = this
    },
    computed: {
    isNight: function() {
    return this.$store.getters.isNight
    },
    autoTheme: function() {
    return this.$store.getters.config.autoTheme
    },
    dialogWidth: function() {
    return this.$store.getters.dialogSmallWidth
    },
    dialogTop: function() {
    return this.$store.getters.dialogTop
    },
    showLogin: {
    get: function() {
    return this.$store.state.showLogin
    },
    set: function(e) {
    this.$store.commit("setShowLogin", e)
    }
    },
    connected: function() {
    return this.$store.state.connected
    },
    showBookInfo: {
    get: function() {
    return this.$store.state.showBookInfo
    },
    set: function(e) {
    this.$store.commit("setShowBookInfo", e)
    }
    }
    },
    watch: {
    isNight: function(e) {
    this.setTheme(e)
    },
    autoTheme: function(e) {
    this.autoSetTheme(e)
    },
    miniInterface: function() {
    this.setMiniInterfaceClass()
    },
    connected: function(e) {
    e && (window.customCSSLoad || window.loadLink(this.$store.getters.customCSSUrl, (function() {
    window.customCSSLoad = !0
    })))
    },
    "$store.state.config.pageType": function() {
    this.setPageTypeClass()
    },
    "$store.state.config.horizontalPadding": function() {
    this.setDocumentProperty()
    },
    "$store.state.config.topPadding": function() {
    this.setDocumentProperty()
    },
    "$store.state.config.bottomPadding": function() {
    this.setDocumentProperty()
    },
    showReplaceRuleForm: function(e) {
    e || this.replaceRuleCallback && (this.replaceRuleCallback(), this.replaceRuleCallback = null)
    },
    showBookmarkForm: function(e) {
    e || this.bookmarkCallback && (this.bookmarkCallback(), this.bookmarkCallback = null)
    },
    showLogin: function(e) {
    e || (this.isLogin = !0)
    }
    },
    methods: {
    tauriInit: function() {
    var e = this;
    return Object(c["a"])(Object(s["a"])().mark((function t() {
    var o, n, r;
    return Object(s["a"])().wrap((function(t) {
    while (1) switch (t.prev = t.next) {
    case 0:
    if (!window.__TAURI__ || !window.__TAURI__.invoke) {
    t.next = 14;
    break
    }
    return t.next = 3, window.__TAURI__.invoke("is_server_running")["catch"]((function() {
    return !1
    }));
    case 3:
    if (o = t.sent, n = function() {
    var t = Object(c["a"])(Object(s["a"])().mark((function t() {
    var o;
    return Object(s["a"])().wrap((function(t) {
    while (1) switch (t.prev = t.next) {
    case 0:
    return t.next = 2, window.__TAURI__.invoke("get_server_port")["catch"]((function() {
    return !1
    }));
    case 2:
    o = t.sent, o && e.$store.commit("setApi", "http://localhost:".concat(o, "/reader3/"));
    case 4:
    case "end":
    return t.stop()
    }
    }), t)
    })));
    return function() {
    return t.apply(this, arguments)
    }
    }(), o) {
    t.next = 12;
    break
    }
    return r = e.$loading({
    lock: !0,
    text: "正在启动服务...",
    spinner: "el-icon-loading",
    background: "rgba(0, 0, 0, 0.7)"
    }), t.next = 9, window.__TAURI__.invoke("start_server")["catch"]((function(t) {
    e.$message.error(t), e.$nextTick((function() {
    e.$router.push("/setting")
    }))
    })).then(n);
    case 9:
    r.close(), t.next = 14;
    break;
    case 12:
    return t.next = 14, n();
    case 14:
    case "end":
    return t.stop()
    }
    }), t)
    })))()
    },
    autoSetTheme: function(e) {
    e && (window.matchMedia("(prefers-color-scheme: dark)").matches ? this.$store.commit("setNightTheme", !0) : this.$store.commit("setNightTheme", !1))
    },
    setTheme: function(e) {
    document.body.className = e ? (document.body.className || "").replace("night-theme", "") + " night-theme" : (document.body.className || "").replace("night-theme", "")
    },
    setMiniInterfaceClass: function() {
    this.$store.state.miniInterface ? document.body.className = (document.body.className || "").replace("mini-interface", "") + " mini-interface" : document.body.className = (document.body.className || "").replace("mini-interface", "")
    },
    setPageTypeClass: function() {
    this.$store.getters.isKindlePage ? document.body.className = (document.body.className || "").replace("kindle-page", "") + " kindle-page" : document.body.className = (document.body.className || "").replace("kindle-page", "")
    },
    setDocumentProperty: function() {
    if (document.documentElement.style.setProperty("--horizontal-padding", "".concat(0 | this.$store.state.config.horizontalPadding, "px")), document.documentElement.style.setProperty("--top-padding", "".concat(0 | this.$store.state.config.topPadding, "px")), document.documentElement.style.setProperty("--bottom-padding", "".concat(0 | this.$store.state.config.bottomPadding, "px")), window.contentCom) try {
    window.contentCom.syncCSSProperty()
    } catch (e) {}
    },
    cancel: function() {
    this.showLogin = !1, this.loginForm = {
    username: "",
    password: "",
    code: ""
    }
    },
    login: function() {
    var e = this;
    return Object(c["a"])(Object(s["a"])().mark((function t() {
    var o;
    return Object(s["a"])().wrap((function(t) {
    while (1) switch (t.prev = t.next) {
    case 0:
    if (!e.isLogining) {
    t.next = 2;
    break
    }
    return t.abrupt("return");
    case 2:
    return e.isLogining = !0, t.next = 5, l["a"].post("/login", Object(i["a"])(Object(i["a"])({}, e.loginForm), {}, {
    isLogin: e.isLogin
    }));
    case 5:
    o = t.sent, o.data.isSuccess && (e.$store.commit("setShowLogin", !1), e.$nextTick((function() {
    e.$store.commit("setLoginAuth", !0)
    })), e.remember && o.data.data && o.data.data.accessToken && e.$store.commit("setToken", o.data.data.accessToken), e.getUserInfo().then((function() {
    e.$store.dispatch("syncFromLocalStorage"), e.init(!0)
    }))), e.isLogining = !1;
    case 8:
    case "end":
    return t.stop()
    }
    }), t)
    })))()
    },
    init: function(e) {
    var t = this;
    return Object(c["a"])(Object(s["a"])().mark((function o() {
    return Object(s["a"])().wrap((function(o) {
    while (1) switch (o.prev = o.next) {
    case 0:
    if (!t.initing) {
    o.next = 3;
    break
    }
    return e && setTimeout((function() {
    t.init(e)
    }), 100), o.abrupt("return");
    case 3:
    if (t.initing = !0, !e && t.$store.state.shelfBooks.length) {
    o.next = 7;
    break
    }
    return o.next = 7, t.loadBookShelf()["catch"]((function() {
    t.initing = !1
    }));
    case 7:
    return o.next = 9, Promise.all([t.loadBookSource(e), t.loadBookGroup(e), t.loadRssSources(e), t.loadReplaceRules(e), t.loadBookmarks(e), t.loadHttpTTS(e)]);
    case 9:
    t.initing = !1;
    case 10:
    case "end":
    return o.stop()
    }
    }), o)
    })))()
    },
    getUserInfo: function() {
    var e = this;
    return Object(U["g"])((function() {
    return l["a"].get(e.api + "/getUserInfo")
    }), "userInfo").then((function(t) {
    t.data.isCache ? e.$store.commit("setConnected", !1) : e.$store.commit("setConnected", !0), t.data.isSuccess && (e.$store.commit("setIsSecureMode", t.data.data.secure), t.data.data.secure && t.data.data.secureKey && e.$store.commit("setShowManagerMode", !0), t.data.data.userInfo && e.$store.commit("setUserInfo", t.data.data.userInfo))
    }), (function(t) {
    e.$message.error("加载用户信息失败 " + (t && t.toString()))
    }))
    },
    loadTxtTocRules: function(e) {
    var t = this,
    o = function(e) {
    e.data.isSuccess && t.$store.commit("setTxtTocRules", e.data.data || [])
    };
    return Object(U["i"])((function() {
    return l["a"].get("/getTxtTocRules")
    }), "txtTocRules", e, o).then(o, (function(e) {
    t.$message.error("加载txt章节规则失败 " + (e && e.toString()))
    }))
    },
    loadHttpTTS: function(e) {
    var t = this,
    o = function(e) {
    e.data.isSuccess && t.$store.commit("setHttpTTS", e.data.data || [])
    };
    return Object(U["i"])((function() {
    return l["a"].get("/httpTTS/list")
    }), "httpTTS", e, o).then(o, (function(e) {
    t.$message.error("加载httpTTS失败 " + (e && e.toString()))
    }))
    },
    showEditorListener: function(e, t, o) {
    var n = this;
    this.editorTitle = e, this.editorContent = t, this.showEditor = !0, this.callback = o, this.$nextTick((function() {
    n.initEditor()
    }))
    },
    showReplaceRuleFormListener: function(e, t, o) {
    this.replaceRule = e, this.isAddReplaceRule = t, this.replaceRuleCallback = o, this.showReplaceRuleForm = !0
    },
    loadBookShelf: function(e, t) {
    var o = this;
    return t = t || this.api, Object(U["g"])((function() {
    return l["a"].get(t + "/getBookshelf?refresh=" + (e ? 1 : 0), {
    timeout: 3e5
    })
    }), "getBookshelf@" + this.currentUserName).then((function(e) {
    e.data.isCache ? o.$store.commit("setConnected", !1) : o.$store.commit("setConnected", !0), e.data.isSuccess && (o.$store.commit("setShelfBooks", e.data.data), u["a"].$emit("shelfBooksLoaded", e.data.data))
    }))["catch"]((function(e) {
    throw o.$store.commit("setConnected", !1), o.$message.error("后端连接失败 " + (e && e.toString())), e
    }))
    },
    loadBookGroup: function(e) {
    var t = this,
    o = function(e) {
    e.data.isSuccess && t.$store.commit("setBookGroupList", e.data.data || [])
    };
    return Object(U["i"])((function() {
    return l["a"].get(t.api + "/getBookGroups")
    }), "bookGroup@" + this.currentUserName, e, o).then(o, (function(e) {
    t.$message.error("加载分组列表失败 " + (e && e.toString()))
    }))
    },
    loadRssSources: function(e) {
    var t = this,
    o = function(e) {
    e.data.isSuccess && t.$store.commit("setRssSourceList", e.data.data || [])
    };
    return Object(U["i"])((function() {
    return l["a"].get(t.api + "/getRssSources", {
    params: {
    simple: 1
    }
    })
    }), "rssSources@" + this.currentUserName, e).then(o, (function(e) {
    t.$message.error("加载RSS订阅列表失败 " + (e && e.toString()))
    }))
    },
    loadBookSource: function(e) {
    var t = this,
    o = function(e) {
    e.data.isSuccess && t.$store.commit("setBookSourceList", e.data.data || [])
    };
    return Object(U["i"])((function() {
    return l["a"].get(t.api + "/getBookSources", {
    params: {
    simple: 1
    }
    })
    }), "bookSourceList@" + this.currentUserName, e, o).then(o, (function(e) {
    t.$message.error("加载书源列表失败 " + (e && e.toString()))
    }))
    },
    loadReplaceRules: function(e) {
    var t = this,
    o = function(e) {
    e.data.isSuccess && t.$store.commit("setFilterRules", e.data.data || [])
    };
    return Object(U["i"])((function() {
    return l["a"].get(t.api + "/getReplaceRules")
    }), "replaceRule@" + this.currentUserName, e, o).then(o, (function(e) {
    t.$message.error("加载替换规则失败 " + (e && e.toString()))
    }))
    },
    loadBookmarks: function(e) {
    var t = this,
    o = function(e) {
    e.data.isSuccess && t.$store.commit("setBookmarks", e.data.data || [])
    };
    return Object(U["i"])((function() {
    return l["a"].get(t.api + "/getBookmarks")
    }), "bookmark@" + this.currentUserName, e, o).then(o, (function(e) {
    t.$message.error("加载书签失败 " + (e && e.toString()))
    }))
    },
    isInShelf: function(e, t) {
    var o = this;
    return Object(c["a"])(Object(s["a"])().mark((function n() {
    var r, a;
    return Object(s["a"])().wrap((function(n) {
    while (1) switch (n.prev = n.next) {
    case 0:
    if (e && e.bookUrl && e.origin) {
    n.next = 3;
    break
    }
    return o.$message.error("书籍信息错误"), n.abrupt("return", !1);
    case 3:
    if (r = o.$store.getters.shelfBooks.find((function(t) {
    return t.bookUrl === e.bookUrl
    })), r) {
    n.next = 12;
    break
    }
    if (!t) {
    n.next = 12;
    break
    }
    return n.next = 8, o.$confirm(t, "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
    })["catch"]((function() {
    return !1
    }));
    case 8:
    if (a = n.sent, a) {
    n.next = 11;
    break
    }
    return n.abrupt("return", !1);
    case 11:
    return n.abrupt("return", l["a"].post(o.api + "/saveBook", e).then((function(e) {
    if (e.data.isSuccess) return !0
    }), (function() {
    return o.$message.error("导入书籍失败"), !1
    })));
    case 12:
    return n.abrupt("return", !!r);
    case 13:
    case "end":
    return n.stop()
    }
    }), n)
    })))()
    },
    getBookContent: function(e, t, o, n, r) {
    var a = this;
    r = r || {
    name: this.$store.getters.readingBook.name,
    author: this.$store.getters.readingBook.author,
    bookUrl: this.$store.getters.readingBook.bookUrl
    };
    var s = {
    url: r.bookUrl,
    index: e
    };
    return o && (s.refresh = 1), n && (s.cache = 1), Object(U["b"])((function() {
    return l["a"].post(a.api + "/getBookContent", s, Object(i["a"])({
    timeout: 1e3 * a.$store.getters.config.chapterRequestTimeout
    }, t))
    }), r.name + "_" + r.author + "@" + r.bookUrl + "@chapterContent-" + e, o)
    },
    initEditor: function() {
    var e = this,
    t = this.$refs.editorRef;
    t || setTimeout((function() {
    e.initEditor()
    }), 10);
    try {
    this.jar = Object(Jt["a"])(t, Yt.a.highlightElement, {
    tab: "\t"
    }), this.jar.updateCode(this.editorContent), this.jar.onUpdate((function(t) {
    e.editorContent = t
    }))
    } catch (o) {}
    },
    closeEditor: function() {
    this.jar && this.jar.destroy && this.jar.destroy(), this.showEditor = !1, this.editorTitle = "", this.editorContent = "", this.callback = null
    },
    saveEditor: function() {
    var e = this;
    this.callback && this.callback(this.editorContent, (function() {
    e.closeEditor()
    }))
    },
    closeViewer: function() {
    this.$store.commit("setPreviewImgList", !1)
    },
    saveUserConfig: function(e) {
    var t = this;
    return Object(c["a"])(Object(s["a"])().mark((function o() {
    var n, r;
    return Object(s["a"])().wrap((function(o) {
    while (1) switch (o.prev = o.next) {
    case 0:
    if (window.localStorage) {
    o.next = 3;
    break
    }
    return t.$message.error("当前终端不支持localStorage"), o.abrupt("return");
    case 3:
    if (e) {
    o.next = 9;
    break
    }
    return o.next = 6, t.$confirm("确认要备份当前终端的阅读配置、书架设置、搜索设置、自定义配置方案、朗读配置吗?", "提示")["catch"]((function() {
    return !1
    }));
    case 6:
    if (n = o.sent, n) {
    o.next = 9;
    break
    }
    return o.abrupt("return");
    case 9:
    r = {}, ["config", "shelfConfig", "searchConfig", "customConfigList", "speechVoiceConfig"].forEach((function(e) {
    var t = Object(Mt["b"])(e);
    t && (r[e] = t)
    })), l["a"].post(t.api + "/saveUserConfig", r).then((function(e) {
    e.data.isSuccess && t.$message.success("备份配置成功")
    }), (function(e) {
    t.$message.error("备份配置失败 " + (e && e.toString()))
    }));
    case 12:
    case "end":
    return o.stop()
    }
    }), o)
    })))()
    },
    restoreUserConfig: function(e) {
    var t = this;
    return Object(c["a"])(Object(s["a"])().mark((function o() {
    var n;
    return Object(s["a"])().wrap((function(o) {
    while (1) switch (o.prev = o.next) {
    case 0:
    if (window.localStorage) {
    o.next = 3;
    break
    }
    return t.$message.error("当前终端不支持localStorage"), o.abrupt("return");
    case 3:
    if (e) {
    o.next = 9;
    break
    }
    return o.next = 6, t.$confirm("确认要从备份文件中恢复当前终端的阅读配置、书架设置、搜索设置、自定义配置方案、朗读方案吗?", "提示")["catch"]((function() {
    return !1
    }));
    case 6:
    if (n = o.sent, n) {
    o.next = 9;
    break
    }
    return o.abrupt("return");
    case 9:
    l["a"].get(t.api + "/getUserConfig").then((function(e) {
    if (e.data.isSuccess) {
    for (var o in e.data.data) Object.hasOwnProperty.call(e.data.data, o) && Object(Mt["c"])(o, e.data.data[o]);
    t.$store.dispatch("syncFromLocalStorage"), t.$message.success("同步配置成功")
    }
    }), (function(e) {
    t.$message.error("同步配置失败 " + (e && e.toString()))
    }));
    case 10:
    case "end":
    return o.stop()
    }
    }), o)
    })))()
    },
    deleteBook: function(e) {
    var t = this;
    return Object(c["a"])(Object(s["a"])().mark((function o() {
    var n;
    return Object(s["a"])().wrap((function(o) {
    while (1) switch (o.prev = o.next) {
    case 0:
    if (e && (e.name || e.bookUrl)) {
    o.next = 3;
    break
    }
    return t.$message.error("书籍信息错误"), o.abrupt("return");
    case 3:
    return o.next = 5, t.$confirm("此操作将删除书籍信息以及阅读进度, 是否继续?", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
    })["catch"]((function() {
    return !1
    }));
    case 5:
    if (n = o.sent, n) {
    o.next = 8;
    break
    }
    return o.abrupt("return");
    case 8:
    l["a"].post(t.api + "/deleteBook", e).then((function(e) {
    e.data.isSuccess && (t.$message.success("删除成功"), t.loadBookShelf())
    }), (function(e) {
    t.$message.error("删除失败 " + (e && e.toString()))
    }));
    case 9:
    case "end":
    return o.stop()
    }
    }), o)
    })))()
    }
    },
  render: function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: e.isShowBookGroupSettingDialog ? "设置分组" : "分组管理",
                        visible: e.show,
                        width: e.dialogWidth,
                        top: e.dialogTop,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        },
                        opened: e.opened
                    }
                }, [t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    key: e.isShowBookGroupSettingDialog,
                    ref: "bookGroupTableRef",
                    attrs: {
                        data: e.showBookGroupList,
                        height: e.dialogContentHeight
                    },
                    on: {
                        "selection-change": function(t) {
                            e.bookGroupSelection = t
                        }
                    }
                }, [e.isShowBookGroupSettingDialog ? t("el-table-column", {
                    attrs: {
                        type: "selection",
                        width: "25"
                    }
                }) : e._e(), t("el-table-column", {
                    attrs: {
                        property: "groupName",
                        label: "分组名",
                        "min-width": "100"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("div", {
                                staticClass: "drag-icon"
                            }, [t("i", {
                                staticClass: "el-icon-rank"
                            })]), t("span", [e._v(" " + e._s(e.displayBookGroupName(o.row)))])]
                        }
                    }], null, !1, 4111896031)
                }), e.isShowBookGroupSettingDialog ? e._e() : t("el-table-column", {
                    attrs: {
                        property: "show",
                        label: "显示",
                        "min-width": "80"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("el-switch", {
                                attrs: {
                                    "active-color": "#13ce66",
                                    "inactive-color": "#ff4949",
                                    "active-value": !0,
                                    "inactive-value": !1
                                },
                                on: {
                                    change: function(t) {
                                        return e.toggleBookGroupShow(o.row, t)
                                    }
                                },
                                model: {
                                    value: o.row.show,
                                    callback: function(t) {
                                        e.$set(o.row, "show", t)
                                    },
                                    expression: "scope.row.show"
                                }
                            })]
                        }
                    }], null, !1, 3235955515)
                }), t("el-table-column", {
                    attrs: {
                        label: "操作",
                        width: "100px"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("el-button", {
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.saveBookGroup(o.row)
                                    }
                                }
                            }, [e._v("编辑")]), !e.isShowBookGroupSettingDialog && o.row.groupId > 0 && !e.getShowShelfBooks(o.row.groupId).length ? t("el-button", {
                                staticStyle: {
                                    color: "#f56c6c"
                                },
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.deleteBookGroup(o.row)
                                    }
                                }
                            }, [e._v("删除")]) : e._e()]
                        }
                    }], null, !1, 1414828911)
                })], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: function(t) {
                            return e.saveBookGroup()
                        }
                    }
                }, [e._v("添加分组")]), e.isShowSaveOrderButton ? t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: function(t) {
                            return e.saveOrder()
                        }
                    }
                }, [e._v("保存排序")]) : e._e(), e.isShowBookGroupSettingDialog ? t("el-button", {
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.setBookGroup
                    }
                }, [e._v("确认")]) : e._e(), t("el-button", {
                    attrs: {
                        size: "medium"
                    },
                    on: {
                        click: e.cancel
                    }
                }, [e._v("取消")])], 1)]) : e._e()
            },
  staticRenderFns: []
}
</script>

<style scoped>
.qrcode-img { display:block;margin:0 auto }
.drag-icon { cursor:move;display:inline-block;margin-right:5px;-webkit-user-select:none;-ms-user-select:none;user-select:none }
</style>
