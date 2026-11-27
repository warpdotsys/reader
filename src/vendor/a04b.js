// chunk-vendors webpack 模块 a04b（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./c04e.js"),
                i = n("./d9b5.js");
            e.exports = function(e) {
                var t = r(e, "string");
                return i(t) ? t : t + ""
            }
        
