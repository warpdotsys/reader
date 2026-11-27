// chunk-vendors webpack 模块 cdf9（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./825a.js"),
                i = n("./861d.js"),
                o = n("./f069.js");
            e.exports = function(e, t) {
                if (r(e), i(t) && t.constructor === e) return t;
                var n = o.f(e),
                    a = n.resolve;
                return a(t), n.promise
            }
        
