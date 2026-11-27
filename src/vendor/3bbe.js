// chunk-vendors webpack 模块 3bbe（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./1787.js"),
                i = String,
                o = TypeError;
            e.exports = function(e) {
                if (r(e)) return e;
                throw new o("Can't set " + i(e) + " as a prototype")
            }
        
