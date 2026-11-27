// chunk-vendors webpack 模块 b42e（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = Math.ceil,
                i = Math.floor;
            e.exports = Math.trunc || function(e) {
                var t = +e;
                return (t > 0 ? i : r)(t)
            }
        
