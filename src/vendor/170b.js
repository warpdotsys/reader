// chunk-vendors webpack 模块 170b（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./ebb5.js"),
                i = n("./50c4.js"),
                o = n("./23cb.js"),
                a = r.aTypedArray,
                s = r.getTypedArrayConstructor,
                u = r.exportTypedArrayMethod;
            u("subarray", (function(e, t) {
                var n = a(this),
                    r = n.length,
                    u = o(e, r),
                    c = s(n);
                return new c(n.buffer, n.byteOffset + u * n.BYTES_PER_ELEMENT, i((void 0 === t ? r : o(t, r)) - u))
            }))
        
