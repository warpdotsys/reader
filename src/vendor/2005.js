// chunk-vendors webpack 模块 2005（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./75bd.js"),
                i = TypeError;
            e.exports = function(e) {
                if (r(e)) throw new i("ArrayBuffer is detached");
                return e
            }
        
