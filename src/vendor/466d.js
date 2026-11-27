// chunk-vendors webpack 模块 466d（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./c65b.js"),
                i = n("./d784.js"),
                o = n("./825a.js"),
                a = n("./861d.js"),
                s = n("./50c4.js"),
                u = n("./577e.js"),
                c = n("./1d80.js"),
                l = n("./dc4a.js"),
                f = n("./8aa5.js"),
                d = n("./14c3.js");
            i("match", (function(e, t, n) {
                return [function(t) {
                    var n = c(this),
                        i = a(t) ? l(t, e) : void 0;
                    return i ? r(i, t, n) : new RegExp(t)[e](u(n))
                }, function(e) {
                    var r = o(this),
                        i = u(e),
                        a = n(t, r, i);
                    if (a.done) return a.value;
                    if (!r.global) return d(r, i);
                    var c = r.unicode;
                    r.lastIndex = 0;
                    var l, h = [],
                        p = 0;
                    while (null !== (l = d(r, i))) {
                        var v = u(l[0]);
                        h[p] = v, "" === v && (r.lastIndex = f(i, s(r.lastIndex), c)), p++
                    }
                    return 0 === p ? null : h
                }]
            }))
        
