// chunk-vendors webpack 模块 cd26（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./ebb5.js"),
                i = r.aTypedArray,
                o = r.exportTypedArrayMethod,
                a = Math.floor;
            o("reverse", (function() {
                var e, t = this,
                    n = i(t).length,
                    r = a(n / 2),
                    o = 0;
                while (o < r) e = t[o], t[o++] = t[--n], t[n] = e;
                return t
            }))
        
