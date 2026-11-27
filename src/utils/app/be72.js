// 恢复自 reader-pro-3.2.14 前端产物 beauty/app.js 的 webpack 模块 be72（逐字切片，形参 e, t, o）
const e = module;
const t = module.exports;
const o = require;

        o("d401"), o("0d03"), o("d3b7"), o("25f0"), o("e323"), String.prototype.MD5 = function(e) {
            var t = this;

            function o(e, t) {
                return e << t | e >>> 32 - t
            }

            function n(e, t) {
                var o, n, r, a, i;
                return r = 2147483648 & e, a = 2147483648 & t, o = 1073741824 & e, n = 1073741824 & t, i = (1073741823 & e) + (1073741823 & t), o & n ? 2147483648 ^ i ^ r ^ a : o | n ? 1073741824 & i ? 3221225472 ^ i ^ r ^ a : 1073741824 ^ i ^ r ^ a : i ^ r ^ a
            }

            function r(e, t, o) {
                return e & t | ~e & o
            }

            function a(e, t, o) {
                return e & o | t & ~o
            }

            function i(e, t, o) {
                return e ^ t ^ o
            }

            function s(e, t, o) {
                return t ^ (e | ~o)
            }

            function c(e, t, a, i, s, c, l) {
                return e = n(e, n(n(r(t, a, i), s), l)), n(o(e, c), t)
            }

            function l(e, t, r, i, s, c, l) {
                return e = n(e, n(n(a(t, r, i), s), l)), n(o(e, c), t)
            }

            function u(e, t, r, a, s, c, l) {
                return e = n(e, n(n(i(t, r, a), s), l)), n(o(e, c), t)
            }

            function f(e, t, r, a, i, c, l) {
                return e = n(e, n(n(s(t, r, a), i), l)), n(o(e, c), t)
            }

            function d(e) {
                var t, o = e.length,
                    n = o + 8,
                    r = (n - n % 64) / 64,
                    a = 16 * (r + 1),
                    i = Array(a - 1),
                    s = 0,
                    c = 0;
                while (c < o) t = (c - c % 4) / 4, s = c % 4 * 8, i[t] = i[t] | e.charCodeAt(c) << s, c++;
                return t = (c - c % 4) / 4, s = c % 4 * 8, i[t] = i[t] | 128 << s, i[a - 2] = o << 3, i[a - 1] = o >>> 29, i
            }

            function p(e) {
                var t, o, n = "",
                    r = "";
                for (o = 0; o <= 3; o++) t = e >>> 8 * o & 255, r = "0" + t.toString(16), n += r.substr(r.length - 2, 2);
                return n
            }
            var h, m, g, b, k, w, v, S, A, x = Array(),
                B = 7,
                C = 12,
                y = 17,
                O = 22,
                I = 5,
                T = 9,
                j = 14,
                L = 20,
                $ = 4,
                R = 11,
                F = 16,
                U = 23,
                D = 6,
                E = 10,
                M = 15,
                N = 21;
            for (x = d(t), w = 1732584193, v = 4023233417, S = 2562383102, A = 271733878, h = 0; h < x.length; h += 16) m = w, g = v, b = S, k = A, w = c(w, v, S, A, x[h + 0], B, 3614090360), A = c(A, w, v, S, x[h + 1], C, 3905402710), S = c(S, A, w, v, x[h + 2], y, 606105819), v = c(v, S, A, w, x[h + 3], O, 3250441966), w = c(w, v, S, A, x[h + 4], B, 4118548399), A = c(A, w, v, S, x[h + 5], C, 1200080426), S = c(S, A, w, v, x[h + 6], y, 2821735955), v = c(v, S, A, w, x[h + 7], O, 4249261313), w = c(w, v, S, A, x[h + 8], B, 1770035416), A = c(A, w, v, S, x[h + 9], C, 2336552879), S = c(S, A, w, v, x[h + 10], y, 4294925233), v = c(v, S, A, w, x[h + 11], O, 2304563134), w = c(w, v, S, A, x[h + 12], B, 1804603682), A = c(A, w, v, S, x[h + 13], C, 4254626195), S = c(S, A, w, v, x[h + 14], y, 2792965006), v = c(v, S, A, w, x[h + 15], O, 1236535329), w = l(w, v, S, A, x[h + 1], I, 4129170786), A = l(A, w, v, S, x[h + 6], T, 3225465664), S = l(S, A, w, v, x[h + 11], j, 643717713), v = l(v, S, A, w, x[h + 0], L, 3921069994), w = l(w, v, S, A, x[h + 5], I, 3593408605), A = l(A, w, v, S, x[h + 10], T, 38016083), S = l(S, A, w, v, x[h + 15], j, 3634488961), v = l(v, S, A, w, x[h + 4], L, 3889429448), w = l(w, v, S, A, x[h + 9], I, 568446438), A = l(A, w, v, S, x[h + 14], T, 3275163606), S = l(S, A, w, v, x[h + 3], j, 4107603335), v = l(v, S, A, w, x[h + 8], L, 1163531501), w = l(w, v, S, A, x[h + 13], I, 2850285829), A = l(A, w, v, S, x[h + 2], T, 4243563512), S = l(S, A, w, v, x[h + 7], j, 1735328473), v = l(v, S, A, w, x[h + 12], L, 2368359562), w = u(w, v, S, A, x[h + 5], $, 4294588738), A = u(A, w, v, S, x[h + 8], R, 2272392833), S = u(S, A, w, v, x[h + 11], F, 1839030562), v = u(v, S, A, w, x[h + 14], U, 4259657740), w = u(w, v, S, A, x[h + 1], $, 2763975236), A = u(A, w, v, S, x[h + 4], R, 1272893353), S = u(S, A, w, v, x[h + 7], F, 4139469664), v = u(v, S, A, w, x[h + 10], U, 3200236656), w = u(w, v, S, A, x[h + 13], $, 681279174), A = u(A, w, v, S, x[h + 0], R, 3936430074), S = u(S, A, w, v, x[h + 3], F, 3572445317), v = u(v, S, A, w, x[h + 6], U, 76029189), w = u(w, v, S, A, x[h + 9], $, 3654602809), A = u(A, w, v, S, x[h + 12], R, 3873151461), S = u(S, A, w, v, x[h + 15], F, 530742520), v = u(v, S, A, w, x[h + 2], U, 3299628645), w = f(w, v, S, A, x[h + 0], D, 4096336452), A = f(A, w, v, S, x[h + 7], E, 1126891415), S = f(S, A, w, v, x[h + 14], M, 2878612391), v = f(v, S, A, w, x[h + 5], N, 4237533241), w = f(w, v, S, A, x[h + 12], D, 1700485571), A = f(A, w, v, S, x[h + 3], E, 2399980690), S = f(S, A, w, v, x[h + 10], M, 4293915773), v = f(v, S, A, w, x[h + 1], N, 2240044497), w = f(w, v, S, A, x[h + 8], D, 1873313359), A = f(A, w, v, S, x[h + 15], E, 4264355552), S = f(S, A, w, v, x[h + 6], M, 2734768916), v = f(v, S, A, w, x[h + 13], N, 1309151649), w = f(w, v, S, A, x[h + 4], D, 4149444226), A = f(A, w, v, S, x[h + 11], E, 3174756917), S = f(S, A, w, v, x[h + 2], M, 718787259), v = f(v, S, A, w, x[h + 9], N, 3951481745), w = n(w, m), v = n(v, g), S = n(S, b), A = n(A, k);
            return 32 == e ? p(w) + p(v) + p(S) + p(A) : p(v) + p(S)
        }
    
