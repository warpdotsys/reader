// chunk-vendors webpack 模块 d58f（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./59ed.js"),
                i = n("./7b0b.js"),
                o = n("./44ad.js"),
                a = n("./07fa.js"),
                s = TypeError,
                u = "Reduce of empty array with no initial value",
                c = function(e) {
                    return function(t, n, c, l) {
                        var f = i(t),
                            d = o(f),
                            h = a(f);
                        if (r(n), 0 === h && c < 2) throw new s(u);
                        var p = e ? h - 1 : 0,
                            v = e ? -1 : 1;
                        if (c < 2)
                            while (1) {
                                if (p in d) {
                                    l = d[p], p += v;
                                    break
                                }
                                if (p += v, e ? p < 0 : h <= p) throw new s(u)
                            }
                        for (; e ? p >= 0 : h > p; p += v) p in d && (l = n(l, d[p], p, f));
                        return l
                    }
                };
            e.exports = {
                left: c(!1),
                right: c(!0)
            }
        
