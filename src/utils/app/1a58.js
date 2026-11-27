// 恢复自 reader-pro-3.2.14 前端产物 beauty/app.js 的 webpack 模块 1a58（逐字切片，形参 e, t, o）
const e = module;
const t = module.exports;
const o = require;

        "use strict";
        var n = o("53ca"),
            r = (o("0fb7"), o("450d"), o("f529")),
            a = o.n(r),
            i = o("15fd"),
            s = o("5530"),
            c = (o("99af"), o("7db0"), o("c740"), o("4160"), o("277d"), o("d81d"), o("14d9"), o("13d5"), o("45fc"), o("4e82"), o("a434"), o("f4b3"), o("0d03"), o("b0c0"), o("e9f5"), o("f665"), o("7d54"), o("ab43"), o("a732"), o("e9c4"), o("d3b7"), o("ac1f"), o("5319"), o("2ca0"), o("159b"), o("2b0e")),
            l = o("2f62"),
            u = o("bfff"),
            f = o("63f0"),
            d = ["catalog", "latestChapterTitle", "intro"],
            p = [{
                username: "默认",
                userNS: "default"
            }],
            h = [{
                groupId: -1,
                groupName: "全部",
                order: -10,
                show: !0
            }, {
                groupId: -2,
                groupName: "本地",
                order: -9,
                show: !0
            }, {
                groupId: -3,
                groupName: "音频",
                order: -8,
                show: !0
            }, {
                groupId: -4,
                groupName: "未分组",
                order: -7,
                show: !0
            }, {
                groupId: -5,
                groupName: "更新错误",
                order: -6,
                show: !0
            }];
        c["default"].use(l["a"]);
        var m = function(e) {
            return e.isManagerMode ? e.userNS : (e.userInfo || {}).username || "default"
        };
        t["a"] = new l["a"].Store({
            state: {
                connected: !1,
                api: Object(f["b"])("api_prefix") || location.host + location.pathname.replace("index.html", "").replace(/\/$/, "") + "/reader3",
                shelfBooks: [],
                readingBook: {},
                config: Object(s["a"])({}, u["b"].config),
                miniInterface: !1,
                windowSize: {
                    width: window.innerWidth,
                    height: window.innerHeight
                },
                touchable: "ontouchstart" in document,
                showLogin: !1,
                loginAuth: !0,
                token: Object(f["b"])("api_token") || "",
                bookSourceList: [],
                isSecureMode: !1,
                isManagerMode: !1,
                secureKey: "",
                userInfo: {},
                userList: [].concat(p),
                userNS: "default",
                showManagerMode: !1,
                version: "v3.2.14-05250935",
                filterRules: [],
                speechVoiceConfig: Object(s["a"])({}, u["b"].speechVoiceConfig),
                safeArea: {
                    top: 0,
                    bottom: 0,
                    left: 0,
                    right: 0
                },
                autoPlay: !1,
                failureIncludeTimeout: !1,
                failureBookSource: [],
                bookGroupList: [],
                rssSourceList: [],
                shelfConfig: Object(s["a"])({}, u["b"].shelfConfig),
                showImageViewer: !1,
                previewImageIndex: 0,
                previewImgList: [],
                searchConfig: Object(s["a"])({}, u["b"].searchConfig),
                txtTocRules: [],
                customConfigList: [],
                showBookInfo: {},
                cachingBookList: [],
                bookmarks: [],
                httpTTS: [],
                notAutoSync: !1
            },
            mutations: {
                setShelfBooks: function(e, t) {
                    window.shelfBooks = t, e.shelfBooks = t.map((function(e) {
                        return {
                            author: e.author,
                            bookUrl: e.bookUrl,
                            coverUrl: e.coverUrl,
                            tocUrl: e.tocUrl,
                            charset: e.charset,
                            customCoverUrl: e.customCoverUrl,
                            canUpdate: e.canUpdate,
                            durChapterIndex: e.durChapterIndex,
                            durChapterPos: e.durChapterPos,
                            durChapterTime: e.durChapterTime,
                            durChapterTitle: e.durChapterTitle,
                            kind: e.kind,
                            intro: e.intro,
                            lastCheckTime: e.lastCheckTime,
                            lastCheckError: e.lastCheckError,
                            latestChapterTitle: e.latestChapterTitle,
                            name: e.name,
                            origin: e.origin,
                            originName: e.originName,
                            totalChapterNum: e.totalChapterNum,
                            type: e.type,
                            group: e.group,
                            wordCount: e.wordCount,
                            readConfig: e.readConfig
                        }
                    }))
                },
                updateShelfBook: function(e, t) {
                    var o = e.shelfBooks.findIndex((function(e) {
                        return e.bookUrl === t.bookUrl
                    }));
                    o >= 0 && (e.shelfBooks[o] = Object(s["a"])(Object(s["a"])({}, e.shelfBooks[o]), {
                        author: t.author || e.shelfBooks[o].author,
                        bookUrl: t.bookUrl || e.shelfBooks[o].bookUrl,
                        coverUrl: t.coverUrl || e.shelfBooks[o].coverUrl,
                        tocUrl: t.tocUrl || e.shelfBooks[o].tocUrl,
                        charset: t.charset || e.shelfBooks[o].charset,
                        customCoverUrl: t.customCoverUrl || e.shelfBooks[o].customCoverUrl,
                        canUpdate: "undefined" === typeof t.canUpdate ? e.shelfBooks[o].canUpdate : t.canUpdate,
                        durChapterIndex: t.durChapterIndex || e.shelfBooks[o].durChapterIndex,
                        durChapterPos: t.durChapterPos || e.shelfBooks[o].durChapterPos,
                        durChapterTime: t.durChapterTime || e.shelfBooks[o].durChapterTime,
                        durChapterTitle: t.durChapterTitle || e.shelfBooks[o].durChapterTitle,
                        kind: t.kind || e.shelfBooks[o].kind,
                        intro: t.intro || e.shelfBooks[o].intro,
                        lastCheckTime: t.lastCheckTime || e.shelfBooks[o].lastCheckTime,
                        latestChapterTitle: t.latestChapterTitle || e.shelfBooks[o].latestChapterTitle,
                        name: t.name || e.shelfBooks[o].name,
                        origin: t.origin || e.shelfBooks[o].origin,
                        originName: t.originName || e.shelfBooks[o].originName,
                        totalChapterNum: t.totalChapterNum || e.shelfBooks[o].totalChapterNum,
                        type: t.type || e.shelfBooks[o].type,
                        group: t.group || e.shelfBooks[o].group,
                        readConfig: t.readConfig || e.shelfBooks[o].readConfig
                    }), e.shelfBooks = [].concat(e.shelfBooks))
                },
                setReadingBook: function(e, t, o) {
                    if (!o && e.shelfBooks && e.shelfBooks.length) {
                        for (var n = 0; n < e.shelfBooks.length; n++)
                            if (e.shelfBooks[n].bookUrl === t.bookUrl) {
                                var r = ((t.catalog || [])[t.index] || {}).title;
                                e.shelfBooks[n] = Object(s["a"])(Object(s["a"])({}, e.shelfBooks[n]), {}, {
                                    durChapterTime: (new Date).getTime(),
                                    durChapterIndex: t.index
                                }, r ? {
                                    durChapterTitle: r
                                } : {});
                                break
                            } e.shelfBooks = [].concat(e.shelfBooks)
                    }
                    e.readingBook = t;
                    t.catalog, t.latestChapterTitle, t.intro;
                    var a = Object(i["a"])(t, d);
                    Object(f["c"])(m(e) + "@readingRecent", JSON.stringify(a))
                },
                clearReadingBook: function(e) {
                    e.readingBook = {}, Object(f["a"])(m(e) + "@readingRecent")
                },
                setConfig: function(e, t) {
                    if (delete t.name, delete t.configDefaultType, t.theme !== u["b"].defaultNightTheme && "custom" !== t.theme ? t.themeType = "day" : t.theme === u["b"].defaultNightTheme && (t.themeType = "night"), e.config = t, t.customConfig) {
                        var o = e.customConfigList.findIndex((function(e) {
                            return e.name === t.customConfig
                        }));
                        if (o >= 0) {
                            var n = Object(s["a"])({}, e.customConfigList[o]);
                            u["f"].forEach((function(e) {
                                "undefined" !== typeof t[e] && "name" !== e && "configDefaultType" !== e && (n[e] = t[e])
                            })), e.customConfigList[o] = n, e.customConfigList = [].concat(e.customConfigList), Object(f["c"])("customConfigList", JSON.stringify(e.customConfigList))
                        }
                    }
                    Object(f["c"])("config", JSON.stringify(t))
                },
                setMiniInterface: function(e, t) {
                    "自适应" === e.config.pageMode ? e.miniInterface = t : e.miniInterface = !0
                },
                setWindowSize: function(e, t) {
                    e.windowSize = t
                },
                setTouchable: function(e, t) {
                    e.touchable = t
                },
                setApi: function(e, t) {
                    e.api = t
                },
                setConnected: function(e, t) {
                    e.connected = t
                },
                setShowLogin: function(e, t) {
                    e.showLogin = t, t && (e.loginAuth = !1)
                },
                setLoginAuth: function(e, t) {
                    e.loginAuth = t
                },
                setToken: function(e, t) {
                    e.token = t, Object(f["c"])("api_token", t)
                },
                setBookSourceList: function(e, t) {
                    e.bookSourceList = t.map((function(e) {
                        return {
                            bookSourceGroup: e.bookSourceGroup,
                            bookSourceName: e.bookSourceName,
                            bookSourceType: e.bookSourceType,
                            bookSourceUrl: e.bookSourceUrl,
                            exploreUrl: e.exploreUrl
                        }
                    }))
                },
                setUserNS: function(e, t) {
                    e.userNS = t
                },
                setIsSecureMode: function(e, t) {
                    e.isSecureMode = t
                },
                setSecureKey: function(e, t) {
                    e.secureKey = t
                },
                setIsManagerMode: function(e, t) {
                    e.isManagerMode = t
                },
                setShowManagerMode: function(e, t) {
                    e.showManagerMode = t
                },
                setUserInfo: function(e, t) {
                    e.userInfo = t
                },
                setUserList: function(e, t) {
                    t.length ? e.userList = [].concat([{
                        username: "系统默认",
                        userNS: "default"
                    }]).concat(t) : e.userList = [].concat(p)
                },
                setFilterRules: function(e, t) {
                    e.filterRules = t, Object(f["c"])("filterRules", JSON.stringify(t))
                },
                addFilterRule: function(e, t) {
                    var o = [].concat(e.filterRules);
                    "undefined" !== typeof t.index && t.index >= 0 ? (o[t.index] = t, e.filterRules = o) : (o = o.concat([t]), e.filterRules = o)
                },
                setNightTheme: function(e, t) {
                    var o, n = Object(s["a"])({}, e.config);
                    o = t ? e.customConfigList.find((function(e) {
                        return "黑夜默认" === e.configDefaultType
                    })) : e.customConfigList.find((function(e) {
                        return "白天默认" === e.configDefaultType
                    })), o ? (n = Object(s["a"])(Object(s["a"])({}, n), o), n.customConfig = o.name, e.config = n, Object(f["c"])("config", JSON.stringify(n))) : a.a.error("未配置" + (t ? "黑夜默认" : "白天默认") + "方案")
                },
                setCustomConfig: function(e, t) {
                    var o = e.customConfigList.find((function(e) {
                        return e.name === t
                    }));
                    if (o) {
                        var n = Object(s["a"])(Object(s["a"])({}, e.config), o);
                        n.customConfig = o.name, e.config = n, Object(f["c"])("config", JSON.stringify(n))
                    }
                },
                setSpeechVoiceConfig: function(e, t) {
                    e.speechVoiceConfig = t, Object(f["c"])("speechVoiceConfig", JSON.stringify(t))
                },
                setSafeArea: function(e, t) {
                    e.safeArea = Object(s["a"])(Object(s["a"])({}, e.safeArea), t)
                },
                setAutoPlay: function(e, t) {
                    e.autoPlay = t
                },
                addFailureBookSource: function(e, t) {
                    var o = t.bookSourceUrl,
                        n = t.errorMsg,
                        r = e.failureBookSource.findIndex((function(e) {
                            return e.bookSourceUrl === o
                        }));
                    if (!(r >= 0)) {
                        var a = e.bookSourceList.find((function(e) {
                            return e.bookSourceUrl === o
                        }));
                        a && (e.failureBookSource = e.failureBookSource.concat([Object(s["a"])(Object(s["a"])({}, a), {}, {
                            errorMsg: n
                        })]))
                    }
                },
                removeFailureBookSource: function(e, t) {
                    for (var o = function(o) {
                            var n = e.failureBookSource.findIndex((function(e) {
                                return e.bookSourceUrl === t[o].bookSourceUrl
                            }));
                            n >= 0 && e.failureBookSource.splice(n, 1)
                        }, n = 0; n < t.length; n++) o(n)
                },
                setFailureIncludeTimeout: function(e, t) {
                    e.failureIncludeTimeout = t
                },
                setBookGroupList: function(e, t) {
                    var o = [];
                    h.forEach((function(e) {
                        t.some((function(t) {
                            return t.groupId === e.groupId
                        })) || o.push(e)
                    })), e.bookGroupList = o.concat(t).sort((function(e, t) {
                        return e.order - t.order
                    }))
                },
                setRssSourceList: function(e, t) {
                    e.rssSourceList = [].concat(t)
                },
                setShelfConfig: function(e, t) {
                    e.shelfConfig = t, Object(f["c"])("shelfConfig", JSON.stringify(t))
                },
                setPreviewImageIndex: function(e, t) {
                    e.previewImageIndex = t
                },
                setPreviewImgList: function(e, t) {
                    !1 === t ? (e.showImageViewer = !1, e.previewImgList = [], e.previewImageIndex = 0) : (e.previewImgList = t, e.showImageViewer = !0)
                },
                setSearchConfig: function(e, t) {
                    e.searchConfig = t, Object(f["c"])("searchConfig", JSON.stringify(t))
                },
                setTxtTocRules: function(e, t) {
                    e.txtTocRules = [].concat(t)
                },
                setCustomConfigList: function(e, t) {
                    e.customConfigList = [].concat(t), Object(f["c"])("customConfigList", JSON.stringify(t))
                },
                setShowBookInfo: function(e, t) {
                    e.showBookInfo = t
                },
                setCachingBookList: function(e, t) {
                    e.cachingBookList = [].concat(t)
                },
                setBookmarks: function(e, t) {
                    e.bookmarks = t
                },
                setHttpTTS: function(e, t) {
                    e.httpTTS = t
                },
                setNotAutoSync: function(e, t) {
                    e.notAutoSync = t, Object(f["c"])("notAutoSync", t)
                }
            },
            getters: {
                api: function(e) {
                    return e.api.startsWith("http://") || e.api.startsWith("https://") ? e.api : e.api.startsWith("//") ? window.location.protocol.startsWith("http") ? e.api : "http:" + e.api : window.location.protocol.startsWith("http") ? "//" + e.api : "http://" + e.api
                },
                apiRoot: function(e, t) {
                    return t.api.replace(/\/reader3\/?/, "")
                },
                isSlideRead: function(e) {
                    return e.miniInterface && "左右滑动" === e.config.readMethod
                },
                isSystemNight: function(e) {
                    return e.config.theme === u["b"].defaultNightTheme
                },
                isNight: function(e) {
                    return "night" === e.config.themeType
                },
                isKindlePage: function(e) {
                    return "Kindle" === e.config.pageType
                },
                isNormalPage: function(e) {
                    return "正常" === e.config.pageType
                },
                currentContentBGImg: function(e, t) {
                    if (e.config.contentBGImg) return e.config.contentBGImg.startsWith("bg/") || e.config.contentBGImg.startsWith("http://") || e.config.contentBGImg.startsWith("https://") || e.config.contentBGImg.startsWith("//") ? e.config.contentBGImg : t.apiRoot + e.config.contentBGImg
                },
                customCSSUrl: function(e, t) {
                    return !!t.api && t.apiRoot + "/assets/reader.css"
                },
                currentFontFamily: function(e) {
                    return u["b"].fonts[e.config.font]
                },
                currentCustomFontFamily: function(e, t) {
                    var o = u["a"][e.config.font];
                    if (e.config.customFontsMap && e.config.customFontsMap[o]) {
                        var n = e.config.customFontsMap[o];
                        return {
                            name: o,
                            url: n.startsWith("http://") || n.startsWith("https://") || n.startsWith("//") ? n : t.apiRoot + n
                        }
                    }
                    return null
                },
                currentThemeConfig: function(e, t) {
                    if ("custom" === e.config.theme) return {
                        body: (e.miniInterface && e.config.isNormalPage ? "linear-gradient(to bottom,rgba(0,0,0,.2) 0,transparent 56px), " : "") + (e.config.bodyColor || "#eadfca"),
                        content: {
                            backgroundImage: t.currentContentBGImg ? "".concat(e.miniInterface && e.config.isNormalPage ? "linear-gradient(to bottom,rgba(0,0,0,.2) 0,transparent 56px), " : "", "url(").concat(t.currentContentBGImg, ")") : null,
                            backgroundPosition: "center",
                            backgroundRepeat: "no-repeat",
                            backgroundAttachment: "fixed",
                            backgroundColor: e.config.contentColor || "#ede7da",
                            backgroundSize: "cover"
                        },
                        popupPure: e.config.popupColor || "#ede7da",
                        popup: (e.miniInterface && e.config.isNormalPage ? "linear-gradient(to bottom,rgba(0,0,0,.2) 0,transparent 36px), " : "") + (e.config.popupColor || "#ede7da")
                    };
                    var o = Object(s["a"])({}, u["b"].themes[e.config.theme]);
                    return o.popupPure = o.popup, e.miniInterface && e.config.isNormalPage && (o.body = "linear-gradient(to bottom,rgba(0,0,0,.2) 0,transparent 36px), " + o.body, o.content = "linear-gradient(to bottom,rgba(0,0,0,.2) 0,transparent 36px), " + o.content, o.popup = "linear-gradient(to bottom,rgba(0,0,0,.2) 0,transparent 36px), " + o.popup), o
                },
                shelfBooks: function(e) {
                    return e.shelfBooks.sort((function(e, t) {
                        var o = e["durChapterTime"] || 0,
                            n = t["durChapterTime"] || 0;
                        return n - o
                    }))
                },
                bookSourceGroupList: function(e) {
                    var t = {};
                    e.bookSourceList.forEach((function(e) {
                        e.bookSourceGroup && e.bookSourceGroup.split(",").forEach((function(e) {
                            t[e] = 1 + (0 | t[e])
                        }))
                    }));
                    var o = [{
                        name: "全部分组",
                        value: "",
                        count: e.bookSourceList.length
                    }];
                    for (var n in t) Object.hasOwnProperty.call(t, n) && o.push({
                        name: n,
                        value: n,
                        count: t[n]
                    });
                    return o
                },
                builtInBookGroupMap: function() {
                    return h.reduce((function(e, t) {
                        return e[t.groupId] = t.groupName, e
                    }), {})
                },
                config: function(e) {
                    return e.config
                },
                shelfConfig: function(e) {
                    return e.shelfConfig
                },
                collapseMenu: function(e) {
                    return e.miniInterface
                },
                dialogWidth: function(e) {
                    return e.miniInterface ? "85%" : Math.min(Math.max(.7 * e.windowSize.width, 750), 1e3) + "px"
                },
                dialogSmallWidth: function(e) {
                    return e.miniInterface ? "85%" : "500px"
                },
                dialogTop: function(e, t) {
                    return (e.windowSize.height - t.dialogContentHeight - 70 - 54 - 60) / 2 + "px"
                },
                dialogContentHeight: function(e) {
                    return e.miniInterface ? e.windowSize.height - 54 - 60 - 70 : Math.min(.7 * e.windowSize.height - 70 - 54 - 60, 400)
                },
                popupWidth: function(e) {
                    return e.miniInterface ? e.windowSize.width : "600"
                },
                currentUserName: function(e) {
                    return m(e)
                },
                currentChapter: function(e) {
                    return e.readingBook && e.readingBook.catalog ? e.readingBook.catalog[e.readingBook.index] : {}
                },
                readingBook: function(e) {
                    return Object(s["a"])(Object(s["a"])({}, e.readingBook), e.shelfBooks.find((function(t) {
                        return t.bookUrl === e.readingBook.bookUrl
                    })) || {})
                },
                hasLogin: function(e) {
                    return !e.isSecureMode || e.isSecureMode && e.userInfo.username
                }
            },
            actions: {
                init: function(e) {
                    var t = e.commit;
                    try {
                        var o = Object(f["b"])("notAutoSync");
                        null !== o && t("setNotAutoSync", o)
                    } catch (n) {}
                },
                syncFromLocalStorage: function(e) {
                    var t = e.commit,
                        o = e.getters;
                    try {
                        var r = Object(f["b"])("config");
                        r && "object" === Object(n["a"])(r) && t("setConfig", Object(s["a"])(Object(s["a"])({}, u["b"].config), r))
                    } catch (p) {}
                    try {
                        var a = Object(f["b"])(o.currentUserName + "@readingRecent");
                        a && "object" === Object(n["a"])(a) && ("undefined" == typeof a.index && (a.index = 0), t("setReadingBook", a, !0))
                    } catch (p) {}
                    try {
                        var i = Object(f["b"])("customConfigList");
                        i && Array.isArray(i) ? t("setCustomConfigList", i) : t("setCustomConfigList", [].concat(u["b"].customConfigList)), o.config && o.config.customConfig && t("setCustomConfig", o.config.customConfig)
                    } catch (p) {}
                    try {
                        var c = Object(f["b"])("speechVoiceConfig");
                        c && "object" === Object(n["a"])(c) && t("setSpeechVoiceConfig", Object(s["a"])(Object(s["a"])({}, u["b"].speechVoiceConfig), c))
                    } catch (p) {}
                    try {
                        var l = Object(f["b"])("shelfConfig");
                        l && "object" === Object(n["a"])(l) && t("setShelfConfig", Object(s["a"])(Object(s["a"])({}, u["b"].shelfConfig), l))
                    } catch (p) {}
                    try {
                        var d = Object(f["b"])("searchConfig");
                        d && "object" === Object(n["a"])(d) && t("setSearchConfig", Object(s["a"])(Object(s["a"])({}, u["b"].searchConfig), d))
                    } catch (p) {}
                }
            }
        })
    
