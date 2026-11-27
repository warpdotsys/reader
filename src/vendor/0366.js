// chunk-vendors webpack 模块 0366（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./4625.js"),
                i = n("./59ed.js"),
                o = n("./40d5.js"),
                a = r(r.bind);
            e.exports = function(e, t) {
                return i(e), void 0 === t ? e : o ? a(e, t) : function() {
                    return e.apply(t, arguments)
                }
            }
        
