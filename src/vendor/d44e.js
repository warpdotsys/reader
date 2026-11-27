// chunk-vendors webpack 模块 d44e（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./9bf2.js").f,
                i = n("./1a2d.js"),
                o = n("./b622.js"),
                a = o("toStringTag");
            e.exports = function(e, t, n) {
                e && !n && (e = e.prototype), e && !i(e, a) && r(e, a, {
                    configurable: !0,
                    value: t
                })
            }
        
