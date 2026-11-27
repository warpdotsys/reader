// chunk-vendors webpack 模块 57b9（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./c65b.js"),
                i = n("./d066.js"),
                o = n("./b622.js"),
                a = n("./cb2d.js");
            e.exports = function() {
                var e = i("Symbol"),
                    t = e && e.prototype,
                    n = t && t.valueOf,
                    s = o("toPrimitive");
                t && !t[s] && a(t, s, (function(e) {
                    return r(n, this)
                }), {
                    arity: 1
                })
            }
        
