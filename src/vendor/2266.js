// chunk-vendors webpack 模块 2266（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./0366.js"),
                i = n("./c65b.js"),
                o = n("./825a.js"),
                a = n("./0d51.js"),
                s = n("./e95a.js"),
                u = n("./07fa.js"),
                c = n("./3a9b.js"),
                l = n("./9a1f.js"),
                f = n("./35a1.js"),
                d = n("./2a62.js"),
                h = TypeError,
                p = function(e, t) {
                    this.stopped = e, this.result = t
                },
                v = p.prototype;
            e.exports = function(e, t, n) {
                var m, g, b, y, _, w, x, C = n && n.that,
                    S = !(!n || !n.AS_ENTRIES),
                    O = !(!n || !n.IS_RECORD),
                    E = !(!n || !n.IS_ITERATOR),
                    k = !(!n || !n.INTERRUPTED),
                    T = r(t, C),
                    j = function(e) {
                        return m && d(m, "normal", e), new p(!0, e)
                    },
                    $ = function(e) {
                        return S ? (o(e), k ? T(e[0], e[1], j) : T(e[0], e[1])) : k ? T(e, j) : T(e)
                    };
                if (O) m = e.iterator;
                else if (E) m = e;
                else {
                    if (g = f(e), !g) throw new h(a(e) + " is not iterable");
                    if (s(g)) {
                        for (b = 0, y = u(e); y > b; b++)
                            if (_ = $(e[b]), _ && c(v, _)) return _;
                        return new p(!1)
                    }
                    m = l(e, g)
                }
                w = O ? e.next : m.next;
                while (!(x = i(w, m)).done) {
                    try {
                        _ = $(x.value)
                    } catch (A) {
                        d(m, "throw", A)
                    }
                    if ("object" == typeof _ && _ && c(v, _)) return _
                }
                return new p(!1)
            }
        
