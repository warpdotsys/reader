// chunk-vendors webpack 模块 dfb9（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./07fa.js");
            e.exports = function(e, t, n) {
                var i = 0,
                    o = arguments.length > 2 ? n : r(t),
                    a = new e(o);
                while (o > i) a[i] = t[i++];
                return a
            }
        
