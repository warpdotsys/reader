// chunk-vendors webpack 模块 ddb0（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./cfe9.js"),
                i = n("./fdbc.js"),
                o = n("./785a.js"),
                a = n("./e260.js"),
                s = n("./9112.js"),
                u = n("./d44e.js"),
                c = n("./b622.js"),
                l = c("iterator"),
                f = a.values,
                d = function(e, t) {
                    if (e) {
                        if (e[l] !== f) try {
                            s(e, l, f)
                        } catch (r) {
                            e[l] = f
                        }
                        if (u(e, t, !0), i[t])
                            for (var n in a)
                                if (e[n] !== a[n]) try {
                                    s(e, n, a[n])
                                } catch (r) {
                                    e[n] = a[n]
                                }
                    }
                };
            for (var h in i) d(r[h] && r[h].prototype, h);
            d(o, "DOMTokenList")
        
