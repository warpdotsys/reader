// 恢复自 reader-pro-3.2.14 前端产物 beauty/index.js 的 webpack 模块 395e（逐字切片，形参 t, e, o）
const t = module;
const e = module.exports;
const o = require;

            "use strict";
            var s = o("dc19"),
                r = o("cb27").has,
                i = o("8e16"),
                a = o("7f65"),
                n = o("5388"),
                c = o("2a62");
            t.exports = function(t) {
                var e = s(this),
                    o = a(t);
                if (i(e) < o.size) return !1;
                var l = o.getIterator();
                return !1 !== n(l, (function(t) {
                    if (!r(e, t)) return c(l, "normal", !1)
                }))
            }
        
