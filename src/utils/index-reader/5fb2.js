// 恢复自 reader-pro-3.2.14 前端产物 beauty/index-reader.js 的 webpack 模块 5fb2（逐字切片，形参 e, t, r）
const e = module;
const t = module.exports;
const r = require;

            "use strict";
            var n = r("e330"),
                i = 2147483647,
                a = 36,
                o = 1,
                s = 26,
                l = 38,
                u = 700,
                c = 72,
                f = 128,
                h = "-",
                d = /[^\0-\u007E]/,
                p = /[.\u3002\uFF0E\uFF61]/g,
                v = "Overflow: input needs wider integers to process",
                g = a - o,
                m = RangeError,
                b = n(p.exec),
                y = Math.floor,
                S = String.fromCharCode,
                w = n("".charCodeAt),
                k = n([].join),
                z = n([].push),
                B = n("".replace),
                R = n("".split),
                O = n("".toLowerCase),
                U = function(e) {
                    var t = [],
                        r = 0,
                        n = e.length;
                    while (r < n) {
                        var i = w(e, r++);
                        if (i >= 55296 && i <= 56319 && r < n) {
                            var a = w(e, r++);
                            56320 === (64512 & a) ? z(t, ((1023 & i) << 10) + (1023 & a) + 65536) : (z(t, i), r--)
                        } else z(t, i)
                    }
                    return t
                },
                P = function(e) {
                    return e + 22 + 75 * (e < 26)
                },
                L = function(e, t, r) {
                    var n = 0;
                    e = r ? y(e / u) : e >> 1, e += y(e / t);
                    while (e > g * s >> 1) e = y(e / g), n += a;
                    return y(n + (g + 1) * e / (e + l))
                },
                j = function(e) {
                    var t = [];
                    e = U(e);
                    var r, n, l = e.length,
                        u = f,
                        d = 0,
                        p = c;
                    for (r = 0; r < e.length; r++) n = e[r], n < 128 && z(t, S(n));
                    var g = t.length,
                        b = g;
                    g && z(t, h);
                    while (b < l) {
                        var w = i;
                        for (r = 0; r < e.length; r++) n = e[r], n >= u && n < w && (w = n);
                        var B = b + 1;
                        if (w - u > y((i - d) / B)) throw new m(v);
                        for (d += (w - u) * B, u = w, r = 0; r < e.length; r++) {
                            if (n = e[r], n < u && ++d > i) throw new m(v);
                            if (n === u) {
                                var R = d,
                                    O = a;
                                while (1) {
                                    var j = O <= p ? o : O >= p + s ? s : O - p;
                                    if (R < j) break;
                                    var x = R - j,
                                        E = a - j;
                                    z(t, S(P(j + x % E))), R = y(x / E), O += a
                                }
                                z(t, S(P(R))), p = L(d, B, b === g), d = 0, b++
                            }
                        }
                        d++, u++
                    }
                    return k(t, "")
                };
            e.exports = function(e) {
                var t, r, n = [],
                    i = R(B(O(e), p, "."), ".");
                for (t = 0; t < i.length; t++) r = i[t], z(n, b(d, r) ? "xn--" + j(r) : r);
                return k(n, ".")
            }
        
