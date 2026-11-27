// chunk-vendors webpack 模块 d9b5（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./d066.js"),
                i = n("./1626.js"),
                o = n("./3a9b.js"),
                a = n("./fdbf.js"),
                s = Object;
            e.exports = a ? function(e) {
                return "symbol" == typeof e
            } : function(e) {
                var t = r("Symbol");
                return i(t) && o(t.prototype, s(e))
            }
        
