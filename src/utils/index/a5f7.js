// 恢复自 reader-pro-3.2.14 前端产物 beauty/index.js 的 webpack 模块 a5f7（逐字切片，形参 t, e, o）
const t = module;
const e = module.exports;
const o = require;

            "use strict";
            var s = o("dc19"),
                r = o("cb27"),
                i = o("83b9e"),
                a = o("8e16"),
                n = o("7f65"),
                c = o("384f"),
                l = o("5388"),
                u = r.has,
                h = r.remove;
            t.exports = function(t) {
                var e = s(this),
                    o = n(t),
                    r = i(e);
                return a(e) <= o.size ? c(e, (function(t) {
                    o.includes(t) && h(r, t)
                })) : l(o.getIterator(), (function(t) {
                    u(e, t) && h(r, t)
                })), r
            }
        
