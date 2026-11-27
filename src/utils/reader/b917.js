// 恢复自 reader-pro-3.2.14 前端产物 beauty/reader.js 的 webpack 模块 b917（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = require;

            "use strict";
            var a = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789",
                u = a + "+/",
                o = a + "-_",
                r = function(e) {
                    for (var t = {}, n = 0; n < 64; n++) t[e.charAt(n)] = n;
                    return t
                };
            e.exports = {
                i2c: u,
                c2i: r(u),
                i2cUrl: o,
                c2iUrl: r(o)
            }
        
