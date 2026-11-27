// chunk-vendors webpack 模块 4df4（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./0366.js"),
                i = n("./c65b.js"),
                o = n("./7b0b.js"),
                a = n("./9bdd.js"),
                s = n("./e95a.js"),
                u = n("./68ee.js"),
                c = n("./07fa.js"),
                l = n("./8418.js"),
                f = n("./9a1f.js"),
                d = n("./35a1.js"),
                h = Array;
            e.exports = function(e) {
                var t = o(e),
                    n = u(this),
                    p = arguments.length,
                    v = p > 1 ? arguments[1] : void 0,
                    m = void 0 !== v;
                m && (v = r(v, p > 2 ? arguments[2] : void 0));
                var g, b, y, _, w, x, C = d(t),
                    S = 0;
                if (!C || this === h && s(C))
                    for (g = c(t), b = n ? new this(g) : h(g); g > S; S++) x = m ? v(t[S], S) : t[S], l(b, S, x);
                else
                    for (b = n ? new this : [], _ = f(t, C), w = _.next; !(y = i(w, _)).done; S++) x = m ? a(_, v, [y.value, S], !0) : y.value, l(b, S, x);
                return b.length = S, b
            }
        
