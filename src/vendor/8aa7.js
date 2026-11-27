// chunk-vendors webpack 模块 8aa7（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./cfe9.js"),
                i = n("./d039.js"),
                o = n("./1c7e.js"),
                a = n("./ebb5.js").NATIVE_ARRAY_BUFFER_VIEWS,
                s = r.ArrayBuffer,
                u = r.Int8Array;
            e.exports = !a || !i((function() {
                u(1)
            })) || !i((function() {
                new u(-1)
            })) || !o((function(e) {
                new u, new u(null), new u(1.5), new u(e)
            }), !0) || i((function() {
                return 1 !== new u(new s(2), 1, void 0).length
            }))
        
