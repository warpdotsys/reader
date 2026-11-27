// chunk-vendors webpack 模块 bcbf（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./f5df.js");
            e.exports = function(e) {
                var t = r(e);
                return "BigInt64Array" === t || "BigUint64Array" === t
            }
        
