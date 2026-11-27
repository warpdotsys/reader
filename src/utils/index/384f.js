// 恢复自 reader-pro-3.2.14 前端产物 beauty/index.js 的 webpack 模块 384f（逐字切片，形参 t, e, o）
const t = module;
const e = module.exports;
const o = require;

            "use strict";
            var s = o("e330"),
                r = o("5388"),
                i = o("cb27"),
                a = i.Set,
                n = i.proto,
                c = s(n.forEach),
                l = s(n.keys),
                u = l(new a).next;
            t.exports = function(t, e, o) {
                return o ? r({
                    iterator: l(t),
                    next: u
                }, e) : c(t, e)
            }
        
