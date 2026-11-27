// chunk-vendors webpack 模块 fb6a（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./e8b5.js"),
                o = n("./68ee.js"),
                a = n("./861d.js"),
                s = n("./23cb.js"),
                u = n("./07fa.js"),
                c = n("./fc6a.js"),
                l = n("./8418.js"),
                f = n("./b622.js"),
                d = n("./1dde.js"),
                h = n("./f36a.js"),
                p = d("slice"),
                v = f("species"),
                m = Array,
                g = Math.max;
            r({
                target: "Array",
                proto: !0,
                forced: !p
            }, {
                slice: function(e, t) {
                    var n, r, f, d = c(this),
                        p = u(d),
                        b = s(e, p),
                        y = s(void 0 === t ? p : t, p);
                    if (i(d) && (n = d.constructor, o(n) && (n === m || i(n.prototype)) ? n = void 0 : a(n) && (n = n[v], null === n && (n = void 0)), n === m || void 0 === n)) return h(d, b, y);
                    for (r = new(void 0 === n ? m : n)(g(y - b, 0)), f = 0; b < y; b++, f++) b in d && l(r, f, d[b]);
                    return r.length = f, r
                }
            })
        
