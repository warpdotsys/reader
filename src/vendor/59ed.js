// chunk-vendors webpack 模块 59ed（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./1626.js"),
                i = n("./0d51.js"),
                o = TypeError;
            e.exports = function(e) {
                if (r(e)) return e;
                throw new o(i(e) + " is not a function")
            }
        
