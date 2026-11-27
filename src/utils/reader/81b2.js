// 恢复自 reader-pro-3.2.14 前端产物 beauty/reader.js 的 webpack 模块 81b2（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = require;

            "use strict";
            var a = n("23e7"),
                u = n("cfe9"),
                o = n("d066"),
                r = n("e330"),
                i = n("c65b"),
                s = n("d039"),
                l = n("577e"),
                c = n("d6d6"),
                p = n("b917").c2i,
                d = /[^\d+/a-z]/i,
                D = /[\t\n\f\r ]+/g,
                h = /[=]{1,2}$/,
                A = o("atob"),
                F = String.fromCharCode,
                f = r("".charAt),
                m = r("".replace),
                C = r(d.exec),
                E = !!A && !s((function() {
                    return "hi" !== A("aGk=")
                })),
                y = E && s((function() {
                    return "" !== A(" ")
                })),
                g = E && !s((function() {
                    A("a")
                })),
                b = E && !s((function() {
                    A()
                })),
                v = E && 1 !== A.length,
                B = !E || y || g || b || v;
            a({
                global: !0,
                bind: !0,
                enumerable: !0,
                forced: B
            }, {
                atob: function(e) {
                    if (c(arguments.length, 1), E && !y && !g) return i(A, u, e);
                    var t, n, a, r = m(l(e), D, ""),
                        s = "",
                        b = 0,
                        v = 0;
                    if (r.length % 4 === 0 && (r = m(r, h, "")), t = r.length, t % 4 === 1 || C(d, r)) throw new(o("DOMException"))("The string is not correctly encoded", "InvalidCharacterError");
                    while (b < t) n = f(r, b++), a = v % 4 ? 64 * a + p[n] : p[n], v++ % 4 && (s += F(255 & a >> (-2 * v & 6)));
                    return s
                }
            })
        
