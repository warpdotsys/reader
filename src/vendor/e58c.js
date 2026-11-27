// chunk-vendors webpack 模块 e58c（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./2ba4.js"),
                i = n("./fc6a.js"),
                o = n("./5926.js"),
                a = n("./07fa.js"),
                s = n("./a640.js"),
                u = Math.min,
                c = [].lastIndexOf,
                l = !!c && 1 / [1].lastIndexOf(1, -0) < 0,
                f = s("lastIndexOf"),
                d = l || !f;
            e.exports = d ? function(e) {
                if (l) return r(c, this, arguments) || 0;
                var t = i(this),
                    n = a(t);
                if (0 === n) return -1;
                var s = n - 1;
                for (arguments.length > 1 && (s = u(s, o(arguments[1]))), s < 0 && (s = n + s); s >= 0; s--)
                    if (s in t && t[s] === e) return s || 0;
                return -1
            } : c
        
