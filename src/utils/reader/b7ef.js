// 恢复自 reader-pro-3.2.14 前端产物 beauty/reader.js 的 webpack 模块 b7ef（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = require;

            "use strict";
            var a = n("23e7"),
                u = n("cfe9"),
                o = n("d066"),
                r = n("5c6c"),
                i = n("9bf2").f,
                s = n("1a2d"),
                l = n("19aa"),
                c = n("7156"),
                p = n("e391"),
                d = n("cf98"),
                D = n("0d26"),
                h = n("83ab"),
                A = n("c430"),
                F = "DOMException",
                f = o("Error"),
                m = o(F),
                C = function() {
                    l(this, E);
                    var e = arguments.length,
                        t = p(e < 1 ? void 0 : arguments[0]),
                        n = p(e < 2 ? void 0 : arguments[1], "Error"),
                        a = new m(t, n),
                        u = new f(t);
                    return u.name = F, i(a, "stack", r(1, D(u.stack, 1))), c(a, this, C), a
                },
                E = C.prototype = m.prototype,
                y = "stack" in new f(F),
                g = "stack" in new m(1, 2),
                b = m && h && Object.getOwnPropertyDescriptor(u, F),
                v = !!b && !(b.writable && b.configurable),
                B = y && !v && !g;
            a({
                global: !0,
                constructor: !0,
                forced: A || B
            }, {
                DOMException: B ? C : m
            });
            var k = o(F),
                w = k.prototype;
            if (w.constructor !== k)
                for (var x in A || i(w, "constructor", r(1, k)), d)
                    if (s(d, x)) {
                        var S = d[x],
                            N = S.s;
                        s(k, N) || i(k, N, r(6, S.c))
                    }
        
