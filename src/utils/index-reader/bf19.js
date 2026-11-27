// 恢复自 reader-pro-3.2.14 前端产物 beauty/index-reader.js 的 webpack 模块 bf19（逐字切片，形参 e, t, r）
const e = module;
const t = module.exports;
const r = require;

            "use strict";
            var n = r("23e7"),
                i = r("c65b");
            n({
                target: "URL",
                proto: !0,
                enumerable: !0
            }, {
                toJSON: function() {
                    return i(URL.prototype.toString, this)
                }
            })
        
