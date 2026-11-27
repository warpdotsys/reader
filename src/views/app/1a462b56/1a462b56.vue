<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=1a462b56；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' title="书籍信息" :visible="show" :width="dialogSmallWidth" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }"><div class="custom-dialog-title" slot="title"><span class="el-dialog__title">书籍信息 <template v-if='isInShelf && "index" === $route.name'><span class="float-right span-btn" @click="editBook">编辑</span></template><template v-if="isInShelf"><span class="float-right span-btn" @click="showBookConfig">设置</span></template><template v-if='isInShelf && "index" === $route.name'><span class="float-right span-btn" @click="deleteBook">移出书架</span></template><template v-if="isInShelf"><span class="float-right span-btn" @click="triggerBookCoverRefClick">修改封面</span></template><template v-else><template v-if="!(isInShelf)"><span class="float-right span-btn" @click="(t) => {
                            return saveBook(showBookInfo, !0)
                        }">加入书架</span></template></template></span></div><template v-if="show"><div class="book-info-container"><div class="book-cover"><div class="book-cover-bg" :style="bookCoverBgStyle" /><div class="book-cover-bg-image"><img :key="showBookInfo.name" alt="" v-lazy="getCover(getBookCoverUrl(showBookInfo))" @click="triggerBookCoverRefClick" /><input ref='"bookCoverRef"' style="display: none" type="file" accept="image/jpg, image/png, image/jpeg" @change="onCoverFileChange" /></div></div><div class="book-name">{{ showBookInfo.name }}</div><BookKind class="text-center" :book="showBookInfo" /><div class="book-props"><div class="book-prop book-author">{{ " 作者： " + _s(showBookInfo.author || "未知") + " " }}</div><div class="book-prop book-origin">{{ " 来源： " + _s(displayOriginName(showBookInfo.origin)) + " " }}<template v-if='"loc_book" === showBookInfo.origin'><el-button class="book-prop-btn" type="text" @click="(t) => {
                            return refreshLocalBook(showBookInfo)
                        }">更新</el-button></template></div><div class="book-prop book-latest"><span class="latest-title">{{ "最新： " + _s(showBookInfo.latestChapterTitle) + " " }}</span><template v-if="isInShelf"><span class="book-prop-btn"> 追更 <el-switch active-color="#13ce66" inactive-color="#ff4949" :active-value="true" :inactive-value="false" v-model="showBookInfo.canUpdate" @change="(t) => {
                            return toggleBookCanUpdate(showBookInfo)
                        }" /></span></template></div><template v-if="showBookInfo.lastCheckError"><div class="book-prop last-check-error">{{ " 错误： " + _s(showBookInfo.lastCheckError) + " " }}</div></template><template v-if="isInShelf"><div class="book-prop book-group">{{ " 分组： " + _s(displayGroupName(showBookInfo.group)) + " " }}<el-button class="book-prop-btn" type="text" @click="(t) => {
                            return showSetBookGroup()
                        }">设置分组</el-button></div></template></div><div class="book-intro" /></div></template></el-dialog>
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
                return t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "书籍信息",
                        visible: e.show,
                        width: e.dialogSmallWidth,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("div", {
                    staticClass: "custom-dialog-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [e._v("书籍信息 "), e.isInShelf && "index" === e.$route.name ? t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.editBook
                    }
                }, [e._v("编辑")]) : e._e(), e.isInShelf ? t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.showBookConfig
                    }
                }, [e._v("设置")]) : e._e(), e.isInShelf && "index" === e.$route.name ? t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.deleteBook
                    }
                }, [e._v("移出书架")]) : e._e(), e.isInShelf ? t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.triggerBookCoverRefClick
                    }
                }, [e._v("修改封面")]) : e.isInShelf ? e._e() : t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: function(t) {
                            return e.saveBook(e.showBookInfo, !0)
                        }
                    }
                }, [e._v("加入书架")])])]), e.show ? t("div", {
                    staticClass: "book-info-container"
                }, [t("div", {
                    staticClass: "book-cover"
                }, [t("div", {
                    staticClass: "book-cover-bg",
                    style: e.bookCoverBgStyle
                }), t("div", {
                    staticClass: "book-cover-bg-image"
                }, [t("img", {
                    directives: [{
                        name: "lazy",
                        rawName: "v-lazy",
                        value: e.getCover(e.getBookCoverUrl(e.showBookInfo)),
                        expression: "getCover(getBookCoverUrl(showBookInfo))"
                    }],
                    key: e.showBookInfo.name,
                    attrs: {
                        alt: ""
                    },
                    on: {
                        click: e.triggerBookCoverRefClick
                    }
                }), t("input", {
                    ref: "bookCoverRef",
                    staticStyle: {
                        display: "none"
                    },
                    attrs: {
                        type: "file",
                        accept: "image/jpg, image/png, image/jpeg"
                    },
                    on: {
                        change: e.onCoverFileChange
                    }
                })])]), t("div", {
                    staticClass: "book-name"
                }, [e._v(e._s(e.showBookInfo.name))]), t("BookKind", {
                    staticClass: "text-center",
                    attrs: {
                        book: e.showBookInfo
                    }
                }), t("div", {
                    staticClass: "book-props"
                }, [t("div", {
                    staticClass: "book-prop book-author"
                }, [e._v(" 作者： " + e._s(e.showBookInfo.author || "未知") + " ")]), t("div", {
                    staticClass: "book-prop book-origin"
                }, [e._v(" 来源： " + e._s(e.displayOriginName(e.showBookInfo.origin)) + " "), "loc_book" === e.showBookInfo.origin ? t("el-button", {
                    staticClass: "book-prop-btn",
                    attrs: {
                        type: "text"
                    },
                    on: {
                        click: function(t) {
                            return e.refreshLocalBook(e.showBookInfo)
                        }
                    }
                }, [e._v("更新")]) : e._e()], 1), t("div", {
                    staticClass: "book-prop book-latest"
                }, [t("span", {
                    staticClass: "latest-title"
                }, [e._v("最新： " + e._s(e.showBookInfo.latestChapterTitle) + " ")]), e.isInShelf ? t("span", {
                    staticClass: "book-prop-btn"
                }, [e._v(" 追更 "), t("el-switch", {
                    attrs: {
                        "active-color": "#13ce66",
                        "inactive-color": "#ff4949",
                        "active-value": !0,
                        "inactive-value": !1
                    },
                    on: {
                        change: function(t) {
                            return e.toggleBookCanUpdate(e.showBookInfo)
                        }
                    },
                    model: {
                        value: e.showBookInfo.canUpdate,
                        callback: function(t) {
                            e.$set(e.showBookInfo, "canUpdate", t)
                        },
                        expression: "showBookInfo.canUpdate"
                    }
                })], 1) : e._e()]), e.showBookInfo.lastCheckError ? t("div", {
                    staticClass: "book-prop last-check-error"
                }, [e._v(" 错误： " + e._s(e.showBookInfo.lastCheckError) + " ")]) : e._e(), e.isInShelf ? t("div", {
                    staticClass: "book-prop book-group"
                }, [e._v(" 分组： " + e._s(e.displayGroupName(e.showBookInfo.group)) + " "), t("el-button", {
                    staticClass: "book-prop-btn",
                    attrs: {
                        type: "text"
                    },
                    on: {
                        click: function(t) {
                            return e.showSetBookGroup()
                        }
                    }
                }, [e._v("设置分组")])], 1) : e._e()]), t("div", {
                    staticClass: "book-intro",
                    domProps: {
                        innerHTML: e._s(e.renderBookIntro(e.showBookInfo))
                    }
                })], 1) : e._e()])
            },
  staticRenderFns: []
}
</script>

<style scoped>
.book-info-container .book-cover { width:100%;position:relative;height:150px }
.book-info-container .book-cover .book-cover-bg { position:absolute;height:100%;width:100%;filter:blur(50px) }
.book-info-container .book-cover .book-cover-bg-image { position:absolute;height:100%;width:100% }
.book-info-container .book-cover .book-cover-bg-image img { margin:0 auto;display:block;height:150px }
.book-info-container .book-name { display:block;font-size:16px;font-weight:500;text-align:center;padding:10px 0 }
.book-info-container .text-center { text-align:center }
.book-info-container .book-props { padding:5px 0 }
.book-info-container .book-props .book-prop { padding:3px 0 }
.book-info-container .book-props .book-prop.book-latest { display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-pack:justify;justify-content:space-between }
.book-info-container .book-props .book-prop.book-latest .latest-title { overflow:hidden;text-overflow:ellipsis;white-space:nowrap }
.book-info-container .book-props .book-prop.book-latest .book-prop-btn { width:80px;text-align:right }
.book-info-container .book-props .book-prop.last-check-error { color:red }
.book-info-container .book-props .book-prop .book-prop-btn { float:right;height:19px;padding:0;cursor:pointer }
.book-info-container .book-props .book-prop.book-operate-zone { text-align:center }
.book-info-container .book-props .book-prop.book-operate-zone .book-operate-btn { cursor:pointer }
.book-info-container .book-intro { line-height:1.6;max-height:calc(var(--vh, 1vh)*70 - 399px);overflow-y:auto }
</style>
