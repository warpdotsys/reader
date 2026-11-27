// chunk-vendors webpack 模块 6547（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./e330.js"),
                i = n("./5926.js"),
                o = n("./577e.js"),
                a = n("./1d80.js"),
                s = r("".charAt),
                u = r("".charCodeAt),
                c = r("".slice),
                l = function(e) {
                    return function(t, n) {
                        var r, l, f = o(a(t)),
                            d = i(n),
                            h = f.length;
                        return d < 0 || d >= h ? e ? "" : void 0 : (r = u(f, d), r < 55296 || r > 56319 || d + 1 === h || (l = u(f, d + 1)) < 56320 || l > 57343 ? e ? s(f, d) : r : e ? c(f, d, d + 2) : l - 56320 + (r - 55296 << 10) + 65536)
                    }
                };
            e.exports = {
                codeAt: l(!1),
                charAt: l(!0)
            }
        
