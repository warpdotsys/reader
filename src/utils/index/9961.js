// 恢复自 reader-pro-3.2.14 前端产物 beauty/index.js 的 webpack 模块 9961（逐字切片，形参 t, e, o）
const t = module;
const e = module.exports;
const o = require;

            "use strict";
            var s = o("dc19"),
                r = o("cb27"),
                i = o("83b9e"),
                a = o("7f65"),
                n = o("5388"),
                c = r.add,
                l = r.has,
                u = r.remove;
            t.exports = function(t) {
                var e = s(this),
                    o = a(t).getIterator(),
                    r = i(e);
                return n(o, (function(t) {
                    l(e, t) ? u(r, t) : c(r, t)
                })), r
            }
        
