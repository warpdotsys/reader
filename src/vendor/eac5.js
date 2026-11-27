// chunk-vendors webpack 模块 eac5（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./861d.js"),
                i = Math.floor;
            e.exports = Number.isInteger || function(e) {
                return !r(e) && isFinite(e) && i(e) === e
            }
        
