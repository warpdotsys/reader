// chunk-vendors webpack 模块 1148（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./5926.js"),
                i = n("./577e.js"),
                o = n("./1d80.js"),
                a = RangeError;
            e.exports = function(e) {
                var t = i(o(this)),
                    n = "",
                    s = r(e);
                if (s < 0 || s === 1 / 0) throw new a("Wrong number of repetitions");
                for (; s > 0;
                    (s >>>= 1) && (t += t)) 1 & s && (n += t);
                return n
            }
        
