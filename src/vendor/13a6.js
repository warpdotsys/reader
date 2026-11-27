// chunk-vendors webpack 模块 13a6（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = Math.round;
            e.exports = function(e) {
                var t = r(e);
                return t < 0 ? 0 : t > 255 ? 255 : 255 & t
            }
        
