// chunk-vendors webpack 模块 f8cd（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./5926.js"),
                i = RangeError;
            e.exports = function(e) {
                var t = r(e);
                if (t < 0) throw new i("The argument can't be less than 0");
                return t
            }
        
