// chunk-vendors webpack 模块 083a（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./0d51.js"),
                i = TypeError;
            e.exports = function(e, t) {
                if (!delete e[t]) throw new i("Cannot delete property " + r(t) + " of " + r(e))
            }
        
