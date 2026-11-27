// chunk-vendors webpack 模块 f748（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            e.exports = Math.sign || function(e) {
                var t = +e;
                return 0 === t || t !== t ? t : t < 0 ? -1 : 1
            }
        
