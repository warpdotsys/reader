// chunk-vendors webpack 模块 145e（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./7b0b.js"),
                i = n("./23cb.js"),
                o = n("./07fa.js"),
                a = n("./083a.js"),
                s = Math.min;
            e.exports = [].copyWithin || function(e, t) {
                var n = r(this),
                    u = o(n),
                    c = i(e, u),
                    l = i(t, u),
                    f = arguments.length > 2 ? arguments[2] : void 0,
                    d = s((void 0 === f ? u : i(f, u)) - l, u - c),
                    h = 1;
                l < c && c < l + d && (h = -1, l += d - 1, c += d - 1);
                while (d-- > 0) l in n ? n[c] = n[l] : a(n, c), c += h, l += h;
                return n
            }
        
