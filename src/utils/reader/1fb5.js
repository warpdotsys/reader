// 恢复自 reader-pro-3.2.14 前端产物 beauty/reader.js 的 webpack 模块 1fb5（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = require;

            "use strict";
            t.byteLength = c, t.toByteArray = d, t.fromByteArray = A;
            for (var a = [], u = [], o = "undefined" !== typeof Uint8Array ? Uint8Array : Array, r = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", i = 0, s = r.length; i < s; ++i) a[i] = r[i], u[r.charCodeAt(i)] = i;

            function l(e) {
                var t = e.length;
                if (t % 4 > 0) throw new Error("Invalid string. Length must be a multiple of 4");
                var n = e.indexOf("="); - 1 === n && (n = t);
                var a = n === t ? 0 : 4 - n % 4;
                return [n, a]
            }

            function c(e) {
                var t = l(e),
                    n = t[0],
                    a = t[1];
                return 3 * (n + a) / 4 - a
            }

            function p(e, t, n) {
                return 3 * (t + n) / 4 - n
            }

            function d(e) {
                var t, n, a = l(e),
                    r = a[0],
                    i = a[1],
                    s = new o(p(e, r, i)),
                    c = 0,
                    d = i > 0 ? r - 4 : r;
                for (n = 0; n < d; n += 4) t = u[e.charCodeAt(n)] << 18 | u[e.charCodeAt(n + 1)] << 12 | u[e.charCodeAt(n + 2)] << 6 | u[e.charCodeAt(n + 3)], s[c++] = t >> 16 & 255, s[c++] = t >> 8 & 255, s[c++] = 255 & t;
                return 2 === i && (t = u[e.charCodeAt(n)] << 2 | u[e.charCodeAt(n + 1)] >> 4, s[c++] = 255 & t), 1 === i && (t = u[e.charCodeAt(n)] << 10 | u[e.charCodeAt(n + 1)] << 4 | u[e.charCodeAt(n + 2)] >> 2, s[c++] = t >> 8 & 255, s[c++] = 255 & t), s
            }

            function D(e) {
                return a[e >> 18 & 63] + a[e >> 12 & 63] + a[e >> 6 & 63] + a[63 & e]
            }

            function h(e, t, n) {
                for (var a, u = [], o = t; o < n; o += 3) a = (e[o] << 16 & 16711680) + (e[o + 1] << 8 & 65280) + (255 & e[o + 2]), u.push(D(a));
                return u.join("")
            }

            function A(e) {
                for (var t, n = e.length, u = n % 3, o = [], r = 16383, i = 0, s = n - u; i < s; i += r) o.push(h(e, i, i + r > s ? s : i + r));
                return 1 === u ? (t = e[n - 1], o.push(a[t >> 2] + a[t << 4 & 63] + "==")) : 2 === u && (t = (e[n - 2] << 8) + e[n - 1], o.push(a[t >> 10] + a[t >> 4 & 63] + a[t << 2 & 63] + "=")), o.join("")
            }
            u["-".charCodeAt(0)] = 62, u["_".charCodeAt(0)] = 63
        
