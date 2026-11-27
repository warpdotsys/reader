// chunk-vendors webpack 模块 0b42（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./e8b5.js"),
                i = n("./68ee.js"),
                o = n("./861d.js"),
                a = n("./b622.js"),
                s = a("species"),
                u = Array;
            e.exports = function(e) {
                var t;
                return r(e) && (t = e.constructor, i(t) && (t === u || r(t.prototype)) ? t = void 0 : o(t) && (t = t[s], null === t && (t = void 0))), void 0 === t ? u : t
            }
        
