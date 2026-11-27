// 恢复自 reader-pro-3.2.14 前端产物 beauty/app.js 的 webpack 模块 63f0（逐字切片，形参 e, t, o）
const e = module;
const t = module.exports;
const o = require;

        "use strict";
        o.d(t, "c", (function() {
            return n
        })), o.d(t, "a", (function() {
            return r
        })), o.d(t, "b", (function() {
            return a
        }));
        o("f4b3"), o("e9c4"), o("b64b");
        var n = function(e, t) {
                t = "string" === typeof t ? t : JSON.stringify(t), window.localStorage && window.localStorage.setItem(e, t)
            },
            r = function(e) {
                window.localStorage && window.localStorage.removeItem(e)
            },
            a = function(e) {
                var t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : null,
                    o = t;
                try {
                    if (o = window.localStorage && window.localStorage.getItem(e), null === o) return t;
                    if (o) {
                        var n = JSON.parse(o);
                        if (null !== n) return n
                    }
                    return o
                } catch (r) {
                    return o
                }
            }
    
