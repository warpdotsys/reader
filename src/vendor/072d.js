// chunk-vendors webpack 模块 072d（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./0bad.js"),
                i = n("./9876.js"),
                o = n("./fed5.js"),
                a = n("./1917.js"),
                s = n("./0983.js"),
                u = n("./9fbb.js"),
                c = Object.assign;
            e.exports = !c || n("./4b8b.js")((function() {
                var e = {},
                    t = {},
                    n = Symbol(),
                    r = "abcdefghijklmnopqrst";
                return e[n] = 7, r.split("").forEach((function(e) {
                    t[e] = e
                })), 7 != c({}, e)[n] || Object.keys(c({}, t)).join("") != r
            })) ? function(e, t) {
                var n = s(e),
                    c = arguments.length,
                    l = 1,
                    f = o.f,
                    d = a.f;
                while (c > l) {
                    var h, p = u(arguments[l++]),
                        v = f ? i(p).concat(f(p)) : i(p),
                        m = v.length,
                        g = 0;
                    while (m > g) h = v[g++], r && !d.call(p, h) || (n[h] = p[h])
                }
                return n
            } : c
        
