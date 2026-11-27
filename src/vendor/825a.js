// chunk-vendors webpack 模块 825a（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./861d.js"),
                i = String,
                o = TypeError;
            e.exports = function(e) {
                if (r(e)) return e;
                throw new o(i(e) + " is not an object")
            }
        
