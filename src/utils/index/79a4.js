// 恢复自 reader-pro-3.2.14 前端产物 beauty/index.js 的 webpack 模块 79a4（逐字切片，形参 t, e, o）
const t = module;
const e = module.exports;
const o = require;

            "use strict";
            var s = o("23e7"),
                r = o("d039"),
                i = o("953b"),
                a = o("dad2"),
                n = !a("intersection", (function(t) {
                    return 2 === t.size && t.has(1) && t.has(2)
                })) || r((function() {
                    return "3,2" !== String(Array.from(new Set([1, 2, 3]).intersection(new Set([3, 2]))))
                }));
            s({
                target: "Set",
                proto: !0,
                real: !0,
                forced: n
            }, {
                intersection: i
            })
        
