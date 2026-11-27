// 恢复自 reader-pro-3.2.14 前端产物 beauty/index-reader.js 的 webpack 模块 f6d6（逐字切片，形参 e, t, r）
const e = module;
const t = module.exports;
const r = require;

            "use strict";
            var n = r("23e7"),
                i = r("e330"),
                a = r("23cb"),
                o = RangeError,
                s = String.fromCharCode,
                l = String.fromCodePoint,
                u = i([].join),
                c = !!l && 1 !== l.length;
            n({
                target: "String",
                stat: !0,
                arity: 1,
                forced: c
            }, {
                fromCodePoint: function(e) {
                    var t, r = [],
                        n = arguments.length,
                        i = 0;
                    while (n > i) {
                        if (t = +arguments[i++], a(t, 1114111) !== t) throw new o(t + " is not a valid code point");
                        r[i] = t < 65536 ? s(t) : s(55296 + ((t -= 65536) >> 10), t % 1024 + 56320)
                    }
                    return u(r, "")
                }
            })
        
