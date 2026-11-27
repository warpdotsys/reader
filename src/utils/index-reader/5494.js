// 恢复自 reader-pro-3.2.14 前端产物 beauty/index-reader.js 的 webpack 模块 5494（逐字切片，形参 e, t, r）
const e = module;
const t = module.exports;
const r = require;

            "use strict";
            var n = r("83ab"),
                i = r("e330"),
                a = r("edd0"),
                o = URLSearchParams.prototype,
                s = i(o.forEach);
            n && !("size" in o) && a(o, "size", {
                get: function() {
                    var e = 0;
                    return s(this, (function() {
                        e++
                    })), e
                },
                configurable: !0,
                enumerable: !0
            })
        
