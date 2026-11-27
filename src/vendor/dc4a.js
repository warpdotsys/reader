// chunk-vendors webpack 模块 dc4a（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./59ed.js"),
                i = n("./7234.js");
            e.exports = function(e, t) {
                var n = e[t];
                return i(n) ? void 0 : r(n)
            }
        
