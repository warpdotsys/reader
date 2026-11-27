// chunk-vendors webpack 模块 4010（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            n("./d28b.js"), n("./4160.js"), n("./c975.js"), n("./277d.js"), n("./e260.js"), n("./14d9.js"), n("./a434.js"), n("./e9f5.js"), n("./7d54.js"), n("./d3b7.js"), n("./3ca3.js"), n("./159b.js"), n("./ddb0.js"), t.__esModule = !0, t.removeResizeListener = t.addResizeListener = void 0;
            var r = n("./6dd8.js"),
                i = a(r),
                o = n("./9619.js");

            function a(e) {
                return e && e.__esModule ? e : {
                    default: e
                }
            }
            var s = "undefined" === typeof window,
                u = function(e) {
                    var t = e,
                        n = Array.isArray(t),
                        r = 0;
                    for (t = n ? t : t[Symbol.iterator]();;) {
                        var i;
                        if (n) {
                            if (r >= t.length) break;
                            i = t[r++]
                        } else {
                            if (r = t.next(), r.done) break;
                            i = r.value
                        }
                        var o = i,
                            a = o.target.__resizeListeners__ || [];
                        a.length && a.forEach((function(e) {
                            e()
                        }))
                    }
                };
            t.addResizeListener = function(e, t) {
                s || (e.__resizeListeners__ || (e.__resizeListeners__ = [], e.__ro__ = new i["default"]((0, o.debounce)(16, u)), e.__ro__.observe(e)), e.__resizeListeners__.push(t))
            }, t.removeResizeListener = function(e, t) {
                e && e.__resizeListeners__ && (e.__resizeListeners__.splice(e.__resizeListeners__.indexOf(t), 1), e.__resizeListeners__.length || e.__ro__.disconnect())
            }
        
