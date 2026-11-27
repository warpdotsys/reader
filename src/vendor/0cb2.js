// chunk-vendors webpack 模块 0cb2（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./e330.js"),
                i = n("./7b0b.js"),
                o = Math.floor,
                a = r("".charAt),
                s = r("".replace),
                u = r("".slice),
                c = /\$([$&'`]|\d{1,2}|<[^>]*>)/g,
                l = /\$([$&'`]|\d{1,2})/g;
            e.exports = function(e, t, n, r, f, d) {
                var h = n + e.length,
                    p = r.length,
                    v = l;
                return void 0 !== f && (f = i(f), v = c), s(d, v, (function(i, s) {
                    var c;
                    switch (a(s, 0)) {
                        case "$":
                            return "$";
                        case "&":
                            return e;
                        case "`":
                            return u(t, 0, n);
                        case "'":
                            return u(t, h);
                        case "<":
                            c = f[u(s, 1, -1)];
                            break;
                        default:
                            var l = +s;
                            if (0 === l) return i;
                            if (l > p) {
                                var d = o(l / 10);
                                return 0 === d ? i : d <= p ? void 0 === r[d - 1] ? a(s, 1) : r[d - 1] + a(s, 1) : i
                            }
                            c = r[l - 1]
                    }
                    return void 0 === c ? "" : c
                }))
            }
        
