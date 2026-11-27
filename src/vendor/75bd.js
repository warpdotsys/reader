// chunk-vendors webpack 模块 75bd（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./cfe9.js"),
                i = n("./4b11.js"),
                o = n("./b620.js"),
                a = r.DataView;
            e.exports = function(e) {
                if (!i || 0 !== o(e)) return !1;
                try {
                    return new a(e), !1
                } catch (t) {
                    return !0
                }
            }
        
