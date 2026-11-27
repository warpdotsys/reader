// chunk-vendors webpack 模块 841c（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./c65b.js"),
                i = n("./d784.js"),
                o = n("./825a.js"),
                a = n("./861d.js"),
                s = n("./1d80.js"),
                u = n("./129f.js"),
                c = n("./577e.js"),
                l = n("./dc4a.js"),
                f = n("./14c3.js");
            i("search", (function(e, t, n) {
                return [function(t) {
                    var n = s(this),
                        i = a(t) ? l(t, e) : void 0;
                    return i ? r(i, t, n) : new RegExp(t)[e](c(n))
                }, function(e) {
                    var r = o(this),
                        i = c(e),
                        a = n(t, r, i);
                    if (a.done) return a.value;
                    var s = r.lastIndex;
                    u(s, 0) || (r.lastIndex = 0);
                    var l = f(r, i);
                    return u(r.lastIndex, s) || (r.lastIndex = s), null === l ? -1 : l.index
                }]
            }))
        
