// chunk-vendors webpack 模块 cb2d（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./1626.js"),
                i = n("./9bf2.js"),
                o = n("./13d2.js"),
                a = n("./6374.js");
            e.exports = function(e, t, n, s) {
                s || (s = {});
                var u = s.enumerable,
                    c = void 0 !== s.name ? s.name : t;
                if (r(n) && o(n, c, s), s.global) u ? e[t] = n : a(t, n);
                else {
                    try {
                        s.unsafe ? e[t] && (u = !0) : delete e[t]
                    } catch (l) {}
                    u ? e[t] = n : i.f(e, t, {
                        value: n,
                        enumerable: !1,
                        configurable: !s.nonConfigurable,
                        writable: !s.nonWritable
                    })
                }
                return e
            }
        
