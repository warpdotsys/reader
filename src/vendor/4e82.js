// chunk-vendors webpack 模块 4e82（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./e330.js"),
                o = n("./59ed.js"),
                a = n("./7b0b.js"),
                s = n("./07fa.js"),
                u = n("./083a.js"),
                c = n("./577e.js"),
                l = n("./d039.js"),
                f = n("./addb.js"),
                d = n("./a640.js"),
                h = n("./3f7e.js"),
                p = n("./99f4.js"),
                v = n("./1212.js"),
                m = n("./ea83.js"),
                g = [],
                b = i(g.sort),
                y = i(g.push),
                _ = l((function() {
                    g.sort(void 0)
                })),
                w = l((function() {
                    g.sort(null)
                })),
                x = d("sort"),
                C = !l((function() {
                    if (v) return v < 70;
                    if (!(h && h > 3)) {
                        if (p) return !0;
                        if (m) return m < 603;
                        var e, t, n, r, i = "";
                        for (e = 65; e < 76; e++) {
                            switch (t = String.fromCharCode(e), e) {
                                case 66:
                                case 69:
                                case 70:
                                case 72:
                                    n = 3;
                                    break;
                                case 68:
                                case 71:
                                    n = 4;
                                    break;
                                default:
                                    n = 2
                            }
                            for (r = 0; r < 47; r++) g.push({
                                k: t + r,
                                v: n
                            })
                        }
                        for (g.sort((function(e, t) {
                                return t.v - e.v
                            })), r = 0; r < g.length; r++) t = g[r].k.charAt(0), i.charAt(i.length - 1) !== t && (i += t);
                        return "DGBEFHACIJK" !== i
                    }
                })),
                S = _ || !w || !x || !C,
                O = function(e) {
                    return function(t, n) {
                        return void 0 === n ? -1 : void 0 === t ? 1 : void 0 !== e ? +e(t, n) || 0 : c(t) > c(n) ? 1 : -1
                    }
                };
            r({
                target: "Array",
                proto: !0,
                forced: S
            }, {
                sort: function(e) {
                    void 0 !== e && o(e);
                    var t = a(this);
                    if (C) return void 0 === e ? b(t) : b(t, e);
                    var n, r, i = [],
                        c = s(t);
                    for (r = 0; r < c; r++) r in t && y(i, t[r]);
                    f(i, O(e)), n = s(i), r = 0;
                    while (r < n) t[r] = i[r++];
                    while (r < c) u(t, r++);
                    return t
                }
            })
        
