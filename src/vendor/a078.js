// chunk-vendors webpack 模块 a078（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./0366.js"),
                i = n("./c65b.js"),
                o = n("./5087.js"),
                a = n("./7b0b.js"),
                s = n("./07fa.js"),
                u = n("./9a1f.js"),
                c = n("./35a1.js"),
                l = n("./e95a.js"),
                f = n("./bcbf.js"),
                d = n("./ebb5.js").aTypedArrayConstructor,
                h = n("./f495.js");
            e.exports = function(e) {
                var t, n, p, v, m, g, b, y, _ = o(this),
                    w = a(e),
                    x = arguments.length,
                    C = x > 1 ? arguments[1] : void 0,
                    S = void 0 !== C,
                    O = c(w);
                if (O && !l(O)) {
                    b = u(w, O), y = b.next, w = [];
                    while (!(g = i(y, b)).done) w.push(g.value)
                }
                for (S && x > 2 && (C = r(C, arguments[2])), n = s(w), p = new(d(_))(n), v = f(p), t = 0; n > t; t++) m = S ? C(w[t], t) : w[t], p[t] = v ? h(m) : +m;
                return p
            }
        
