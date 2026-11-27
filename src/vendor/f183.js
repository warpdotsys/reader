// chunk-vendors webpack 模块 f183（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./e330.js"),
                o = n("./d012.js"),
                a = n("./861d.js"),
                s = n("./1a2d.js"),
                u = n("./9bf2.js").f,
                c = n("./241c.js"),
                l = n("./057f.js"),
                f = n("./4fad.js"),
                d = n("./90e3.js"),
                h = n("./bb2f.js"),
                p = !1,
                v = d("meta"),
                m = 0,
                g = function(e) {
                    u(e, v, {
                        value: {
                            objectID: "O" + m++,
                            weakData: {}
                        }
                    })
                },
                b = function(e, t) {
                    if (!a(e)) return "symbol" == typeof e ? e : ("string" == typeof e ? "S" : "P") + e;
                    if (!s(e, v)) {
                        if (!f(e)) return "F";
                        if (!t) return "E";
                        g(e)
                    }
                    return e[v].objectID
                },
                y = function(e, t) {
                    if (!s(e, v)) {
                        if (!f(e)) return !0;
                        if (!t) return !1;
                        g(e)
                    }
                    return e[v].weakData
                },
                _ = function(e) {
                    return h && p && f(e) && !s(e, v) && g(e), e
                },
                w = function() {
                    x.enable = function() {}, p = !0;
                    var e = c.f,
                        t = i([].splice),
                        n = {};
                    n[v] = 1, e(n).length && (c.f = function(n) {
                        for (var r = e(n), i = 0, o = r.length; i < o; i++)
                            if (r[i] === v) {
                                t(r, i, 1);
                                break
                            } return r
                    }, r({
                        target: "Object",
                        stat: !0,
                        forced: !0
                    }, {
                        getOwnPropertyNames: l.f
                    }))
                },
                x = e.exports = {
                    enable: w,
                    fastKey: b,
                    getWeakData: y,
                    onFreeze: _
                };
            o[v] = !0
        
