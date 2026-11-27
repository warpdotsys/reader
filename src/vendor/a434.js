// chunk-vendors webpack 模块 a434（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./7b0b.js"),
                o = n("./23cb.js"),
                a = n("./5926.js"),
                s = n("./07fa.js"),
                u = n("./3a34.js"),
                c = n("./3511.js"),
                l = n("./65f0.js"),
                f = n("./8418.js"),
                d = n("./083a.js"),
                h = n("./1dde.js"),
                p = h("splice"),
                v = Math.max,
                m = Math.min;
            r({
                target: "Array",
                proto: !0,
                forced: !p
            }, {
                splice: function(e, t) {
                    var n, r, h, p, g, b, y = i(this),
                        _ = s(y),
                        w = o(e, _),
                        x = arguments.length;
                    for (0 === x ? n = r = 0 : 1 === x ? (n = 0, r = _ - w) : (n = x - 2, r = m(v(a(t), 0), _ - w)), c(_ + n - r), h = l(y, r), p = 0; p < r; p++) g = w + p, g in y && f(h, p, y[g]);
                    if (h.length = r, n < r) {
                        for (p = w; p < _ - r; p++) g = p + r, b = p + n, g in y ? y[b] = y[g] : d(y, b);
                        for (p = _; p > _ - r + n; p--) d(y, p - 1)
                    } else if (n > r)
                        for (p = _ - r; p > w; p--) g = p + r - 1, b = p + n - 1, g in y ? y[b] = y[g] : d(y, b);
                    for (p = 0; p < n; p++) y[p + w] = arguments[p + 2];
                    return u(y, _ - r + n), h
                }
            })
        
