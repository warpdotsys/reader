// chunk-vendors webpack 模块 393a（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./e444.js"),
                i = n("./512c.js"),
                o = n("./ba01.js"),
                a = n("./051b.js"),
                s = n("./8a0d.js"),
                u = n("./26dd.js"),
                c = n("./92f0.js"),
                l = n("./ce7a.js"),
                f = n("./cc15.js")("iterator"),
                d = !([].keys && "next" in [].keys()),
                h = "@@iterator",
                p = "keys",
                v = "values",
                m = function() {
                    return this
                };
            e.exports = function(e, t, n, g, b, y, _) {
                u(n, t, g);
                var w, x, C, S = function(e) {
                        if (!d && e in T) return T[e];
                        switch (e) {
                            case p:
                                return function() {
                                    return new n(this, e)
                                };
                            case v:
                                return function() {
                                    return new n(this, e)
                                }
                        }
                        return function() {
                            return new n(this, e)
                        }
                    },
                    O = t + " Iterator",
                    E = b == v,
                    k = !1,
                    T = e.prototype,
                    j = T[f] || T[h] || b && T[b],
                    $ = j || S(b),
                    A = b ? E ? S("entries") : $ : void 0,
                    P = "Array" == t && T.entries || j;
                if (P && (C = l(P.call(new e)), C !== Object.prototype && C.next && (c(C, O, !0), r || "function" == typeof C[f] || a(C, f, m))), E && j && j.name !== v && (k = !0, $ = function() {
                        return j.call(this)
                    }), r && !_ || !d && !k && T[f] || a(T, f, $), s[t] = $, s[O] = m, b)
                    if (w = {
                            values: E ? $ : S(v),
                            keys: y ? $ : S(p),
                            entries: A
                        }, _)
                        for (x in w) x in T || o(T, x, w[x]);
                    else i(i.P + i.F * (d || k), t, w);
                return w
            }
        
