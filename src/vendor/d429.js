// chunk-vendors webpack 模块 d429（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./07fa.js"),
                i = n("./5926.js"),
                o = RangeError;
            e.exports = function(e, t, n, a) {
                var s = r(e),
                    u = i(n),
                    c = u < 0 ? s + u : u;
                if (c >= s || c < 0) throw new o("Incorrect index");
                for (var l = new t(s), f = 0; f < s; f++) l[f] = f === c ? a : e[f];
                return l
            }
        
