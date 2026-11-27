// chunk-vendors webpack 模块 99af（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./d039.js"),
                o = n("./e8b5.js"),
                a = n("./861d.js"),
                s = n("./7b0b.js"),
                u = n("./07fa.js"),
                c = n("./3511.js"),
                l = n("./8418.js"),
                f = n("./65f0.js"),
                d = n("./1dde.js"),
                h = n("./b622.js"),
                p = n("./1212.js"),
                v = h("isConcatSpreadable"),
                m = p >= 51 || !i((function() {
                    var e = [];
                    return e[v] = !1, e.concat()[0] !== e
                })),
                g = function(e) {
                    if (!a(e)) return !1;
                    var t = e[v];
                    return void 0 !== t ? !!t : o(e)
                },
                b = !m || !d("concat");
            r({
                target: "Array",
                proto: !0,
                arity: 1,
                forced: b
            }, {
                concat: function(e) {
                    var t, n, r, i, o, a = s(this),
                        d = f(a, 0),
                        h = 0;
                    for (t = -1, r = arguments.length; t < r; t++)
                        if (o = -1 === t ? a : arguments[t], g(o))
                            for (i = u(o), c(h + i), n = 0; n < i; n++, h++) n in o && l(d, h, o[n]);
                        else c(h + 1), l(d, h++, o);
                    return d.length = h, d
                }
            })
        
