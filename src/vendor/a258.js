// chunk-vendors webpack 模块 a258（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./0366.js"),
                i = n("./44ad.js"),
                o = n("./7b0b.js"),
                a = n("./07fa.js"),
                s = function(e) {
                    var t = 1 === e;
                    return function(n, s, u) {
                        var c, l, f = o(n),
                            d = i(f),
                            h = a(d),
                            p = r(s, u);
                        while (h-- > 0)
                            if (c = d[h], l = p(c, h, f), l) switch (e) {
                                case 0:
                                    return c;
                                case 1:
                                    return h
                            }
                        return t ? -1 : void 0
                    }
                };
            e.exports = {
                findLast: s(0),
                findLastIndex: s(1)
            }
        
