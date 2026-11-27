// chunk-vendors webpack 模块 157a（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./cfe9.js"),
                i = n("./83ab.js"),
                o = Object.getOwnPropertyDescriptor;
            e.exports = function(e) {
                if (!i) return r[e];
                var t = o(r, e);
                return t && t.value
            }
        
