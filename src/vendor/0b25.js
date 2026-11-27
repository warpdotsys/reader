// chunk-vendors webpack 模块 0b25（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./5926.js"),
                i = n("./50c4.js"),
                o = RangeError;
            e.exports = function(e) {
                if (void 0 === e) return 0;
                var t = r(e),
                    n = i(t);
                if (t !== n) throw new o("Wrong length or index");
                return n
            }
        
