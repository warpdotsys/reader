// chunk-vendors webpack 模块 58a8（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./e330.js"),
                i = n("./1d80.js"),
                o = n("./577e.js"),
                a = n("./5899.js"),
                s = r("".replace),
                u = RegExp("^[" + a + "]+"),
                c = RegExp("(^|[^" + a + "])[" + a + "]+$"),
                l = function(e) {
                    return function(t) {
                        var n = o(i(t));
                        return 1 & e && (n = s(n, u, "")), 2 & e && (n = s(n, c, "$1")), n
                    }
                };
            e.exports = {
                start: l(1),
                end: l(2),
                trim: l(3)
            }
        
