// chunk-vendors webpack 模块 cc12（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./cfe9.js"),
                i = n("./861d.js"),
                o = r.document,
                a = i(o) && i(o.createElement);
            e.exports = function(e) {
                return a ? o.createElement(e) : {}
            }
        
