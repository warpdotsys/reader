// chunk-vendors webpack 模块 fc1b（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./f748.js"),
                i = n("./82e3.js"),
                o = Math.abs,
                a = 2220446049250313e-31;
            e.exports = function(e, t, n, s) {
                var u = +e,
                    c = o(u),
                    l = r(u);
                if (c < s) return l * i(c / s / t) * s * t;
                var f = (1 + t / a) * c,
                    d = f - (f - c);
                return d > n || d !== d ? l * (1 / 0) : l * d
            }
        
