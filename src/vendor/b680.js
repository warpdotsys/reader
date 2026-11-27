// chunk-vendors webpack 模块 b680（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./e330.js"),
                o = n("./5926.js"),
                a = n("./408a.js"),
                s = n("./1148.js"),
                u = n("./d039.js"),
                c = RangeError,
                l = String,
                f = Math.floor,
                d = i(s),
                h = i("".slice),
                p = i(1..toFixed),
                v = function(e, t, n) {
                    return 0 === t ? n : t % 2 === 1 ? v(e, t - 1, n * e) : v(e * e, t / 2, n)
                },
                m = function(e) {
                    var t = 0,
                        n = e;
                    while (n >= 4096) t += 12, n /= 4096;
                    while (n >= 2) t += 1, n /= 2;
                    return t
                },
                g = function(e, t, n) {
                    var r = -1,
                        i = n;
                    while (++r < 6) i += t * e[r], e[r] = i % 1e7, i = f(i / 1e7)
                },
                b = function(e, t) {
                    var n = 6,
                        r = 0;
                    while (--n >= 0) r += e[n], e[n] = f(r / t), r = r % t * 1e7
                },
                y = function(e) {
                    var t = 6,
                        n = "";
                    while (--t >= 0)
                        if ("" !== n || 0 === t || 0 !== e[t]) {
                            var r = l(e[t]);
                            n = "" === n ? r : n + d("0", 7 - r.length) + r
                        } return n
                },
                _ = u((function() {
                    return "0.000" !== p(8e-5, 3) || "1" !== p(.9, 0) || "1.25" !== p(1.255, 2) || "1000000000000000128" !== p(0xde0b6b3a7640080, 0)
                })) || !u((function() {
                    p({})
                }));
            r({
                target: "Number",
                proto: !0,
                forced: _
            }, {
                toFixed: function(e) {
                    var t, n, r, i, s = a(this),
                        u = o(e),
                        f = [0, 0, 0, 0, 0, 0],
                        p = "",
                        _ = "0";
                    if (u < 0 || u > 20) throw new c("Incorrect fraction digits");
                    if (s !== s) return "NaN";
                    if (s <= -1e21 || s >= 1e21) return l(s);
                    if (s < 0 && (p = "-", s = -s), s > 1e-21)
                        if (t = m(s * v(2, 69, 1)) - 69, n = t < 0 ? s * v(2, -t, 1) : s / v(2, t, 1), n *= 4503599627370496, t = 52 - t, t > 0) {
                            g(f, 0, n), r = u;
                            while (r >= 7) g(f, 1e7, 0), r -= 7;
                            g(f, v(10, r, 1), 0), r = t - 1;
                            while (r >= 23) b(f, 1 << 23), r -= 23;
                            b(f, 1 << r), g(f, 1, 1), b(f, 2), _ = y(f)
                        } else g(f, 0, n), g(f, 1 << -t, 0), _ = y(f) + d("0", u);
                    return u > 0 ? (i = _.length, _ = p + (i <= u ? "0." + d("0", u - i) + _ : h(_, 0, i - u) + "." + h(_, i - u))) : _ = p + _, _
                }
            })
        
