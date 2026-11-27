// chunk-vendors webpack 模块 dcc3（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./ae93.js").IteratorPrototype,
                i = n("./7c73.js"),
                o = n("./5c6c.js"),
                a = n("./d44e.js"),
                s = n("./3f8c.js"),
                u = function() {
                    return this
                };
            e.exports = function(e, t, n, c) {
                var l = t + " Iterator";
                return e.prototype = i(r, {
                    next: o(+!c, n)
                }), a(e, l, !1, !0), s[l] = u, e
            }
        
