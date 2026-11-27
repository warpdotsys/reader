// chunk-vendors webpack 模块 addb（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./f36a.js"),
                i = Math.floor,
                o = function(e, t) {
                    var n = e.length;
                    if (n < 8) {
                        var a, s, u = 1;
                        while (u < n) {
                            s = u, a = e[u];
                            while (s && t(e[s - 1], a) > 0) e[s] = e[--s];
                            s !== u++ && (e[s] = a)
                        }
                    } else {
                        var c = i(n / 2),
                            l = o(r(e, 0, c), t),
                            f = o(r(e, c), t),
                            d = l.length,
                            h = f.length,
                            p = 0,
                            v = 0;
                        while (p < d || v < h) e[p + v] = p < d && v < h ? t(l[p], f[v]) <= 0 ? l[p++] : f[v++] : p < d ? l[p++] : f[v++]
                    }
                    return e
                };
            e.exports = o
        
