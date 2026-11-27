// chunk-vendors webpack 模块 2834（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./ebb5.js"),
                i = n("./e330.js"),
                o = n("./59ed.js"),
                a = n("./dfb9.js"),
                s = r.aTypedArray,
                u = r.getTypedArrayConstructor,
                c = r.exportTypedArrayMethod,
                l = i(r.TypedArrayPrototype.sort);
            c("toSorted", (function(e) {
                void 0 !== e && o(e);
                var t = s(this),
                    n = a(u(t), t);
                return l(n, e)
            }))
        
