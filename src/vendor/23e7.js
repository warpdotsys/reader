// chunk-vendors webpack 模块 23e7（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./cfe9.js"),
                i = n("./06cf.js").f,
                o = n("./9112.js"),
                a = n("./cb2d.js"),
                s = n("./6374.js"),
                u = n("./e893.js"),
                c = n("./94ca.js");
            e.exports = function(e, t) {
                var n, l, f, d, h, p, v = e.target,
                    m = e.global,
                    g = e.stat;
                if (l = m ? r : g ? r[v] || s(v, {}) : r[v] && r[v].prototype, l)
                    for (f in t) {
                        if (h = t[f], e.dontCallGetSet ? (p = i(l, f), d = p && p.value) : d = l[f], n = c(m ? f : v + (g ? "." : "#") + f, e.forced), !n && void 0 !== d) {
                            if (typeof h == typeof d) continue;
                            u(h, d)
                        }(e.sham || d && d.sham) && o(h, "sham", !0), a(l, f, h, e)
                    }
            }
        
