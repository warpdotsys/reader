// 恢复自 reader-pro-3.2.14 前端产物 beauty/index.js 的 webpack 模块 953b（逐字切片，形参 t, e, o）
const t = module;
const e = module.exports;
const o = require;

            "use strict";
            var s = o("dc19"),
                r = o("cb27"),
                i = o("8e16"),
                a = o("7f65"),
                n = o("384f"),
                c = o("5388"),
                l = r.Set,
                u = r.add,
                h = r.has;
            t.exports = function(t) {
                var e = s(this),
                    o = a(t),
                    r = new l;
                return i(e) > o.size ? c(o.getIterator(), (function(t) {
                    h(e, t) && u(r, t)
                })) : n(e, (function(t) {
                    o.includes(t) && u(r, t)
                })), r
            }
        
