// chunk-vendors webpack 模块 ab36（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./861d.js"),
                i = n("./9112.js");
            e.exports = function(e, t) {
                r(t) && "cause" in t && i(e, "cause", t.cause)
            }
        
