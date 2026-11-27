// 恢复自 reader-pro-3.2.14 前端产物 beauty/index.js 的 webpack 模块 7f65（逐字切片，形参 t, e, o）
const t = module;
const e = module.exports;
const o = require;

            "use strict";
            var s = o("59ed"),
                r = o("825a"),
                i = o("c65b"),
                a = o("5926"),
                n = o("46c4"),
                c = "Invalid size",
                l = RangeError,
                u = TypeError,
                h = Math.max,
                f = function(t, e) {
                    this.set = t, this.size = h(e, 0), this.has = s(t.has), this.keys = s(t.keys)
                };
            f.prototype = {
                getIterator: function() {
                    return n(r(i(this.keys, this.set)))
                },
                includes: function(t) {
                    return i(this.has, this.set, t)
                }
            }, t.exports = function(t) {
                r(t);
                var e = +t.size;
                if (e !== e) throw new u(c);
                var o = a(e);
                if (o < 0) throw new l(c);
                return new f(t, o)
            }
        
