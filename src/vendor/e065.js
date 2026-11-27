// chunk-vendors webpack 模块 e065（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./428f.js"),
                i = n("./1a2d.js"),
                o = n("./e538.js"),
                a = n("./9bf2.js").f;
            e.exports = function(e) {
                var t = r.Symbol || (r.Symbol = {});
                i(t, e) || a(t, e, {
                    value: o.f(e)
                })
            }
        
