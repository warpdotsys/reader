// chunk-vendors webpack 模块 e34a（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            var r = n("./8b1a.js")("meta"),
                i = n("./7a41.js"),
                o = n("./9c0e.js"),
                a = n("./1a14.js").f,
                s = 0,
                u = Object.isExtensible || function() {
                    return !0
                },
                c = !n("./4b8b.js")((function() {
                    return u(Object.preventExtensions({}))
                })),
                l = function(e) {
                    a(e, r, {
                        value: {
                            i: "O" + ++s,
                            w: {}
                        }
                    })
                },
                f = function(e, t) {
                    if (!i(e)) return "symbol" == typeof e ? e : ("string" == typeof e ? "S" : "P") + e;
                    if (!o(e, r)) {
                        if (!u(e)) return "F";
                        if (!t) return "E";
                        l(e)
                    }
                    return e[r].i
                },
                d = function(e, t) {
                    if (!o(e, r)) {
                        if (!u(e)) return !0;
                        if (!t) return !1;
                        l(e)
                    }
                    return e[r].w
                },
                h = function(e) {
                    return c && p.NEED && u(e) && !o(e, r) && l(e), e
                },
                p = e.exports = {
                    KEY: r,
                    NEED: !1,
                    fastKey: f,
                    getWeak: d,
                    onFreeze: h
                }
        
