// chunk-vendors webpack 模块 a9e3（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./c430.js"),
                o = n("./83ab.js"),
                a = n("./cfe9.js"),
                s = n("./428f.js"),
                u = n("./e330.js"),
                c = n("./94ca.js"),
                l = n("./1a2d.js"),
                f = n("./7156.js"),
                d = n("./3a9b.js"),
                h = n("./d9b5.js"),
                p = n("./c04e.js"),
                v = n("./d039.js"),
                m = n("./241c.js").f,
                g = n("./06cf.js").f,
                b = n("./9bf2.js").f,
                y = n("./408a.js"),
                _ = n("./58a8.js").trim,
                w = "Number",
                x = a[w],
                C = s[w],
                S = x.prototype,
                O = a.TypeError,
                E = u("".slice),
                k = u("".charCodeAt),
                T = function(e) {
                    var t = p(e, "number");
                    return "bigint" == typeof t ? t : j(t)
                },
                j = function(e) {
                    var t, n, r, i, o, a, s, u, c = p(e, "number");
                    if (h(c)) throw new O("Cannot convert a Symbol value to a number");
                    if ("string" == typeof c && c.length > 2)
                        if (c = _(c), t = k(c, 0), 43 === t || 45 === t) {
                            if (n = k(c, 2), 88 === n || 120 === n) return NaN
                        } else if (48 === t) {
                        switch (k(c, 1)) {
                            case 66:
                            case 98:
                                r = 2, i = 49;
                                break;
                            case 79:
                            case 111:
                                r = 8, i = 55;
                                break;
                            default:
                                return +c
                        }
                        for (o = E(c, 2), a = o.length, s = 0; s < a; s++)
                            if (u = k(o, s), u < 48 || u > i) return NaN;
                        return parseInt(o, r)
                    }
                    return +c
                },
                $ = c(w, !x(" 0o1") || !x("0b1") || x("+0x1")),
                A = function(e) {
                    return d(S, e) && v((function() {
                        y(e)
                    }))
                },
                P = function(e) {
                    var t = arguments.length < 1 ? 0 : x(T(e));
                    return A(this) ? f(Object(t), this, P) : t
                };
            P.prototype = S, $ && !i && (S.constructor = P), r({
                global: !0,
                constructor: !0,
                wrap: !0,
                forced: $
            }, {
                Number: P
            });
            var I = function(e, t) {
                for (var n, r = o ? m(t) : "MAX_VALUE,MIN_VALUE,NaN,NEGATIVE_INFINITY,POSITIVE_INFINITY,EPSILON,MAX_SAFE_INTEGER,MIN_SAFE_INTEGER,isFinite,isInteger,isNaN,isSafeInteger,parseFloat,parseInt,fromString,range".split(","), i = 0; r.length > i; i++) l(t, n = r[i]) && !l(e, n) && b(e, n, g(t, n))
            };
            i && C && I(s[w], C), ($ || i) && I(s[w], x)
        
