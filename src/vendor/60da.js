// chunk-vendors webpack 模块 60da（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./83ab.js"),
                i = n("./e330.js"),
                o = n("./c65b.js"),
                a = n("./d039.js"),
                s = n("./df75.js"),
                u = n("./7418.js"),
                c = n("./d1e7.js"),
                l = n("./7b0b.js"),
                f = n("./44ad.js"),
                d = Object.assign,
                h = Object.defineProperty,
                p = i([].concat);
            e.exports = !d || a((function() {
                if (r && 1 !== d({
                        b: 1
                    }, d(h({}, "a", {
                        enumerable: !0,
                        get: function() {
                            h(this, "b", {
                                value: 3,
                                enumerable: !1
                            })
                        }
                    }), {
                        b: 2
                    })).b) return !0;
                var e = {},
                    t = {},
                    n = Symbol("assign detection"),
                    i = "abcdefghijklmnopqrst";
                return e[n] = 7, i.split("").forEach((function(e) {
                    t[e] = e
                })), 7 !== d({}, e)[n] || s(d({}, t)).join("") !== i
            })) ? function(e, t) {
                var n = l(e),
                    i = arguments.length,
                    a = 1,
                    d = u.f,
                    h = c.f;
                while (i > a) {
                    var v, m = f(arguments[a++]),
                        g = d ? p(s(m), d(m)) : s(m),
                        b = g.length,
                        y = 0;
                    while (b > y) v = g[y++], r && !o(h, m, v) || (n[v] = m[v])
                }
                return n
            } : d
        
