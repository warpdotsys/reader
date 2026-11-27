// chunk-vendors webpack 模块 77a7（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = Array,
                i = Math.abs,
                o = Math.pow,
                a = Math.floor,
                s = Math.log,
                u = Math.LN2,
                c = function(e, t, n) {
                    var c, l, f, d = r(n),
                        h = 8 * n - t - 1,
                        p = (1 << h) - 1,
                        v = p >> 1,
                        m = 23 === t ? o(2, -24) - o(2, -77) : 0,
                        g = e < 0 || 0 === e && 1 / e < 0 ? 1 : 0,
                        b = 0;
                    e = i(e), e !== e || e === 1 / 0 ? (l = e !== e ? 1 : 0, c = p) : (c = a(s(e) / u), f = o(2, -c), e * f < 1 && (c--, f *= 2), e += c + v >= 1 ? m / f : m * o(2, 1 - v), e * f >= 2 && (c++, f /= 2), c + v >= p ? (l = 0, c = p) : c + v >= 1 ? (l = (e * f - 1) * o(2, t), c += v) : (l = e * o(2, v - 1) * o(2, t), c = 0));
                    while (t >= 8) d[b++] = 255 & l, l /= 256, t -= 8;
                    c = c << t | l, h += t;
                    while (h > 0) d[b++] = 255 & c, c /= 256, h -= 8;
                    return d[b - 1] |= 128 * g, d
                },
                l = function(e, t) {
                    var n, r = e.length,
                        i = 8 * r - t - 1,
                        a = (1 << i) - 1,
                        s = a >> 1,
                        u = i - 7,
                        c = r - 1,
                        l = e[c--],
                        f = 127 & l;
                    l >>= 7;
                    while (u > 0) f = 256 * f + e[c--], u -= 8;
                    n = f & (1 << -u) - 1, f >>= -u, u += t;
                    while (u > 0) n = 256 * n + e[c--], u -= 8;
                    if (0 === f) f = 1 - s;
                    else {
                        if (f === a) return n ? NaN : l ? -1 / 0 : 1 / 0;
                        n += o(2, t), f -= s
                    }
                    return (l ? -1 : 1) * n * o(2, f - t)
                };
            e.exports = {
                pack: c,
                unpack: l
            }
        
