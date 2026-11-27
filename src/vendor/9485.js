// chunk-vendors webpack 模块 9485（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./2266.js"),
                o = n("./59ed.js"),
                a = n("./825a.js"),
                s = n("./46c4.js"),
                u = n("./2a62.js"),
                c = n("./f99f.js"),
                l = n("./2ba4.js"),
                f = n("./d039.js"),
                d = TypeError,
                h = f((function() {
                    [].keys().reduce((function() {}), void 0)
                })),
                p = !h && c("reduce", d);
            r({
                target: "Iterator",
                proto: !0,
                real: !0,
                forced: h || p
            }, {
                reduce: function(e) {
                    a(this);
                    try {
                        o(e)
                    } catch (f) {
                        u(this, "throw", f)
                    }
                    var t = arguments.length < 2,
                        n = t ? void 0 : arguments[1];
                    if (p) return l(p, this, t ? [e] : [e, n]);
                    var r = s(this),
                        c = 0;
                    if (i(r, (function(r) {
                            t ? (t = !1, n = r) : n = e(n, r, c), c++
                        }), {
                            IS_RECORD: !0
                        }), t) throw new d("Reduce of empty iterator with no initial value");
                    return n
                }
            })
        
