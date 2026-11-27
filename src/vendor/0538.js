// chunk-vendors webpack 模块 0538（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./e330.js"),
                i = n("./59ed.js"),
                o = n("./861d.js"),
                a = n("./1a2d.js"),
                s = n("./f36a.js"),
                u = n("./40d5.js"),
                c = Function,
                l = r([].concat),
                f = r([].join),
                d = {},
                h = function(e, t, n) {
                    if (!a(d, t)) {
                        for (var r = [], i = 0; i < t; i++) r[i] = "a[" + i + "]";
                        d[t] = c("C,a", "return new C(" + f(r, ",") + ")")
                    }
                    return d[t](e, n)
                };
            e.exports = u ? c.bind : function(e) {
                var t = i(this),
                    n = t.prototype,
                    r = s(arguments, 1),
                    a = function() {
                        var n = l(r, s(arguments));
                        return this instanceof a ? h(t, n.length, n) : t.apply(e, n)
                    };
                return o(n) && (a.prototype = n), a
            }
        
