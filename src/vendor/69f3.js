// chunk-vendors webpack 模块 69f3（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r, i, o, a = n("./cdce.js"),
                s = n("./cfe9.js"),
                u = n("./861d.js"),
                c = n("./9112.js"),
                l = n("./1a2d.js"),
                f = n("./c6cd.js"),
                d = n("./f772.js"),
                h = n("./d012.js"),
                p = "Object already initialized",
                v = s.TypeError,
                m = s.WeakMap,
                g = function(e) {
                    return o(e) ? i(e) : r(e, {})
                },
                b = function(e) {
                    return function(t) {
                        var n;
                        if (!u(t) || (n = i(t)).type !== e) throw new v("Incompatible receiver, " + e + " required");
                        return n
                    }
                };
            if (a || f.state) {
                var y = f.state || (f.state = new m);
                y.get = y.get, y.has = y.has, y.set = y.set, r = function(e, t) {
                    if (y.has(e)) throw new v(p);
                    return t.facade = e, y.set(e, t), t
                }, i = function(e) {
                    return y.get(e) || {}
                }, o = function(e) {
                    return y.has(e)
                }
            } else {
                var _ = d("state");
                h[_] = !0, r = function(e, t) {
                    if (l(e, _)) throw new v(p);
                    return t.facade = e, c(e, _, t), t
                }, i = function(e) {
                    return l(e, _) ? e[_] : {}
                }, o = function(e) {
                    return l(e, _)
                }
            }
            e.exports = {
                set: r,
                get: i,
                has: o,
                enforce: g,
                getterFor: b
            }
        
