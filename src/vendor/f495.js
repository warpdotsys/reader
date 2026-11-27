// chunk-vendors webpack 模块 f495（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./c04e.js"),
                i = TypeError;
            e.exports = function(e) {
                var t = r(e, "number");
                if ("number" == typeof t) throw new i("Can't convert number to bigint");
                return BigInt(t)
            }
        
