// 恢复自 reader-pro-3.2.14 前端产物 beauty/reader.js 的 webpack 模块 5b81（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = require;

            "use strict";
            var a = n("23e7"),
                u = n("c65b"),
                o = n("e330"),
                r = n("1d80"),
                i = n("1626"),
                s = n("861d"),
                l = n("44e7"),
                c = n("577e"),
                p = n("dc4a"),
                d = n("90d8"),
                D = n("0cb2"),
                h = n("b622"),
                A = n("c430"),
                F = h("replace"),
                f = TypeError,
                m = o("".indexOf),
                C = o("".replace),
                E = o("".slice),
                y = Math.max;
            a({
                target: "String",
                proto: !0
            }, {
                replaceAll: function(e, t) {
                    var n, a, o, h, g, b, v, B, k, w, x = r(this),
                        S = 0,
                        N = "";
                    if (s(e)) {
                        if (n = l(e), n && (a = c(r(d(e))), !~m(a, "g"))) throw new f("`.replaceAll` does not allow non-global regexes");
                        if (o = p(e, F), o) return u(o, e, x, t);
                        if (A && n) return C(c(x), e, t)
                    }
                    h = c(x), g = c(e), b = i(t), b || (t = c(t)), v = g.length, B = y(1, v), k = m(h, g);
                    while (-1 !== k) w = b ? c(t(g, k, h)) : D(g, h, k, [], void 0, t), N += E(h, S, k) + w, S = k + v, k = k + B > h.length ? -1 : m(h, g, k + B);
                    return S < h.length && (N += E(h, S)), N
                }
            })
        
