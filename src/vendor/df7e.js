// chunk-vendors webpack 模块 df7e（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./07fa.js");
            e.exports = function(e, t) {
                for (var n = r(e), i = new t(n), o = 0; o < n; o++) i[o] = e[n - o - 1];
                return i
            }
        
