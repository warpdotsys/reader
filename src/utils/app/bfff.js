// 恢复自 reader-pro-3.2.14 前端产物 beauty/app.js 的 webpack 模块 bfff（逐字切片，形参 e, t, o）
const e = module;
const t = module.exports;
const o = require;

        "use strict";
        o.d(t, "e", (function() {
            return P
        })), o.d(t, "d", (function() {
            return G
        })), o.d(t, "c", (function() {
            return W
        })), o.d(t, "f", (function() {
            return z
        })), o.d(t, "a", (function() {
            return H
        }));
        var n = o("5530"),
            r = (o("99af"), o("b64b"), o("7286")),
            a = o.n(r),
            i = o("477e"),
            s = o.n(i),
            c = o("e160"),
            l = o.n(c),
            u = o("df5e"),
            f = o.n(u),
            d = o("ec0f"),
            p = o.n(d),
            h = o("b671"),
            m = o.n(h),
            g = o("5629"),
            b = o.n(g),
            k = o("d0e3"),
            w = o.n(k),
            v = o("4039"),
            S = o.n(v),
            A = o("1e75"),
            x = o.n(A),
            B = o("1632"),
            C = o.n(B),
            y = o("7abd"),
            O = o.n(y),
            I = o("356c"),
            T = o.n(I),
            j = o("b165"),
            L = o.n(j),
            $ = o("cf68"),
            R = o.n($),
            F = o("4400"),
            U = o.n(F),
            D = o("802e"),
            E = o.n(D),
            M = {
                configDefaultType: "白天默认",
                name: "内置白天",
                theme: 0,
                font: 0,
                chineseFont: "简体",
                fontSize: 18,
                fontWeight: 400,
                fontColor: "#262626",
                bodyColor: "#eadfca",
                contentColor: "#fff",
                popupColor: "#ede7da",
                themeType: "day",
                readMethod: "上下滑动",
                clickMethod: "自动",
                animateMSTime: 300,
                readWidth: 800,
                lineHeight: 1.8,
                paragraphSpace: .2,
                autoReadingMethod: "像素滚动",
                autoReadingPixel: 1,
                autoReadingLineTime: 1e3,
                pageMode: "自适应",
                selectionAction: "操作弹窗",
                topPadding: 0,
                bottomPadding: 0,
                horizontalPadding: 0,
                epubMode: "iframe",
                chapterRequestTimeout: 10,
                quickKeyMode: "默认",
                quickKey: {
                    ArrowUp: "上一页",
                    ArrowDown: "下一页",
                    ArrowLeft: "上一章",
                    ArrowRight: "下一章",
                    Escape: "返回",
                    Space: "下半页",
                    PageUp: "下一页",
                    PageDown: "上一页",
                    Home: "首页",
                    End: "尾页"
                }
            },
            N = {
                configDefaultType: "黑夜默认",
                name: "内置黑夜",
                theme: 6,
                font: 0,
                chineseFont: "简体",
                fontSize: 18,
                fontWeight: 400,
                fontColor: "#666666",
                bodyColor: "#121212",
                contentColor: "#171717",
                popupColor: "#121212",
                themeType: "night",
                readMethod: "上下滑动",
                clickMethod: "自动",
                animateMSTime: 300,
                readWidth: 800,
                lineHeight: 1.8,
                paragraphSpace: .2,
                autoReadingMethod: "像素滚动",
                autoReadingPixel: 1,
                autoReadingLineTime: 1e3,
                pageMode: "自适应",
                selectionAction: "操作弹窗",
                topPadding: 0,
                bottomPadding: 0,
                horizontalPadding: 0,
                epubMode: "iframe",
                chapterRequestTimeout: 10,
                quickKeyMode: "默认",
                quickKey: {
                    ArrowUp: "上一页",
                    ArrowDown: "下一页",
                    ArrowLeft: "上一章",
                    ArrowRight: "下一章",
                    Escape: "返回",
                    Space: "下半页",
                    PageUp: "下一页",
                    PageDown: "上一页",
                    Home: "首页",
                    End: "尾页"
                }
            },
            _ = {
                shelfConfig: {
                    showBookGroup: -1,
                    viewCate: "list",
                    bookOrder: "durChapterTime",
                    imageProxy: "noProxy",
                    virtualOptimize: "yes"
                },
                searchConfig: {
                    searchType: "multi",
                    bookSourceGroup: "",
                    bookSourceUrl: "",
                    concurrentCount: 4
                },
                customConfigList: [Object(n["a"])({}, M), Object(n["a"])({}, N)],
                config: Object(n["a"])(Object(n["a"])({}, M), {}, {
                    customConfig: "内置白天",
                    autoTheme: !0,
                    pageType: "正常"
                }),
                speechVoiceConfig: {
                    ttsType: "local",
                    cacheTTSAudio: !1,
                    voiceName: "",
                    speechRate: 1,
                    speechPitch: 1
                },
                defaultNightTheme: 6,
                themes: [{
                    body: "url(" + a.a + ") repeat",
                    content: "url(" + s.a + ") repeat",
                    popup: "url(" + l.a + ") repeat"
                }, {
                    body: "url(" + f.a + ") repeat",
                    content: "url(" + p.a + ") repeat",
                    popup: "url(" + m.a + ") repeat"
                }, {
                    body: "url(" + b.a + ") repeat",
                    content: "url(" + w.a + ") repeat",
                    popup: "url(" + S.a + ") repeat"
                }, {
                    body: "url(" + x.a + ") repeat",
                    content: "url(" + C.a + ") repeat",
                    popup: "url(" + O.a + ") repeat"
                }, {
                    body: "#ebcece repeat",
                    content: "#f5e4e4 repeat",
                    popup: "#faeceb repeat"
                }, {
                    body: "url(" + T.a + ") repeat",
                    content: "url(" + L.a + ") repeat",
                    popup: "url(" + R.a + ") repeat"
                }, {
                    body: "url(" + U.a + ") repeat",
                    content: "url(" + E.a + ") repeat",
                    popup: "#121212"
                }, {
                    body: "#f7f7f7 repeat",
                    content: "#fff repeat",
                    popup: "#f7f7f7 repeat"
                }],
                fonts: [{
                    fontFamily: "custom-system"
                }, {
                    fontFamily: "custom-ht, reader-ht"
                }, {
                    fontFamily: "custom-kt, reader-kt"
                }, {
                    fontFamily: "custom-st, reader-st"
                }, {
                    fontFamily: "custom-fs, reader-fs"
                }]
            },
            P = ["UnknownHostException", "ConnectException: Failed to connect", "SocketException: Connection reset", "SSLHandshakeException", "responseCode: 307", "responseCode: 400", "responseCode: 403", "responseCode: 404", "responseCode: 500", "responseCode: 502", "responseCode: 503", "responseCode: 504", "responseCode: 513"],
            G = {
                name: "",
                pattern: "",
                replacement: "",
                scope: "",
                isRegex: !1,
                isEnabled: !0
            },
            W = {
                bookName: "",
                bookAuthor: "",
                chapterIndex: 0,
                chapterPos: 0,
                chapterName: "",
                bookText: "",
                content: ""
            },
            z = Object.keys(M).concat(["contentBGImg"]),
            H = ["custom-system", "custom-ht", "custom-kt", "custom-st", "custom-fs"];
        t["b"] = _
    
