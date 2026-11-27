// chunk-vendors webpack 模块 182d（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./f8cd.js"),
                i = RangeError;
            e.exports = function(e, t) {
                var n = r(e);
                if (n % t) throw new i("Wrong offset");
                return n
            }
        
