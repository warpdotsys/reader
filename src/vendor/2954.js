// chunk-vendors webpack 模块 2954（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./ebb5.js"),
                i = n("./d039.js"),
                o = n("./f36a.js"),
                a = r.aTypedArray,
                s = r.getTypedArrayConstructor,
                u = r.exportTypedArrayMethod,
                c = i((function() {
                    new Int8Array(1).slice()
                }));
            u("slice", (function(e, t) {
                var n = o(a(this), e, t),
                    r = s(this),
                    i = 0,
                    u = n.length,
                    c = new r(u);
                while (u > i) c[i] = n[i++];
                return c
            }), c)
        
