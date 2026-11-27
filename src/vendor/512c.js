// chunk-vendors webpack 模块 512c（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            var r = n("./ef08.js"),
                i = n("./5524.js"),
                o = n("./9c0c.js"),
                a = n("./051b.js"),
                s = n("./9c0e.js"),
                u = "prototype",
                c = function(e, t, n) {
                    var l, f, d, h = e & c.F,
                        p = e & c.G,
                        v = e & c.S,
                        m = e & c.P,
                        g = e & c.B,
                        b = e & c.W,
                        y = p ? i : i[t] || (i[t] = {}),
                        _ = y[u],
                        w = p ? r : v ? r[t] : (r[t] || {})[u];
                    for (l in p && (n = t), n) f = !h && w && void 0 !== w[l], f && s(y, l) || (d = f ? w[l] : n[l], y[l] = p && "function" != typeof w[l] ? n[l] : g && f ? o(d, r) : b && w[l] == d ? function(e) {
                        var t = function(t, n, r) {
                            if (this instanceof e) {
                                switch (arguments.length) {
                                    case 0:
                                        return new e;
                                    case 1:
                                        return new e(t);
                                    case 2:
                                        return new e(t, n)
                                }
                                return new e(t, n, r)
                            }
                            return e.apply(this, arguments)
                        };
                        return t[u] = e[u], t
                    }(d) : m && "function" == typeof d ? o(Function.call, d) : d, m && ((y.virtual || (y.virtual = {}))[l] = d, e & c.R && _ && !_[l] && a(_, l, d)))
                };
            c.F = 1, c.G = 2, c.S = 4, c.P = 8, c.B = 16, c.W = 32, c.U = 64, c.R = 128, e.exports = c
        
