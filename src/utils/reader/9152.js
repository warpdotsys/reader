// 恢复自 reader-pro-3.2.14 前端产物 beauty/reader.js 的 webpack 模块 9152（逐字切片，形参 e, t）
const e = module;
const t = module.exports;

            /*! ieee754. BSD-3-Clause License. Feross Aboukhadijeh <https://feross.org/opensource> */
            t.read = function(e, t, n, a, u) {
                var o, r, i = 8 * u - a - 1,
                    s = (1 << i) - 1,
                    l = s >> 1,
                    c = -7,
                    p = n ? u - 1 : 0,
                    d = n ? -1 : 1,
                    D = e[t + p];
                for (p += d, o = D & (1 << -c) - 1, D >>= -c, c += i; c > 0; o = 256 * o + e[t + p], p += d, c -= 8);
                for (r = o & (1 << -c) - 1, o >>= -c, c += a; c > 0; r = 256 * r + e[t + p], p += d, c -= 8);
                if (0 === o) o = 1 - l;
                else {
                    if (o === s) return r ? NaN : 1 / 0 * (D ? -1 : 1);
                    r += Math.pow(2, a), o -= l
                }
                return (D ? -1 : 1) * r * Math.pow(2, o - a)
            }, t.write = function(e, t, n, a, u, o) {
                var r, i, s, l = 8 * o - u - 1,
                    c = (1 << l) - 1,
                    p = c >> 1,
                    d = 23 === u ? Math.pow(2, -24) - Math.pow(2, -77) : 0,
                    D = a ? 0 : o - 1,
                    h = a ? 1 : -1,
                    A = t < 0 || 0 === t && 1 / t < 0 ? 1 : 0;
                for (t = Math.abs(t), isNaN(t) || t === 1 / 0 ? (i = isNaN(t) ? 1 : 0, r = c) : (r = Math.floor(Math.log(t) / Math.LN2), t * (s = Math.pow(2, -r)) < 1 && (r--, s *= 2), t += r + p >= 1 ? d / s : d * Math.pow(2, 1 - p), t * s >= 2 && (r++, s /= 2), r + p >= c ? (i = 0, r = c) : r + p >= 1 ? (i = (t * s - 1) * Math.pow(2, u), r += p) : (i = t * Math.pow(2, p - 1) * Math.pow(2, u), r = 0)); u >= 8; e[n + D] = 255 & i, D += h, i /= 256, u -= 8);
                for (r = r << u | i, l += u; l > 0; e[n + D] = 255 & r, D += h, r /= 256, l -= 8);
                e[n + D - h] |= 128 * A
            }
        
