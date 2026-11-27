// chunk-vendors webpack 模块 3511（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = TypeError,
                i = 9007199254740991;
            e.exports = function(e) {
                if (e > i) throw r("Maximum allowed index exceeded");
                return e
            }
        
