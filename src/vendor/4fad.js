// chunk-vendors webpack 模块 4fad（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./d039.js"),
                i = n("./861d.js"),
                o = n("./c6b6.js"),
                a = n("./d86b.js"),
                s = Object.isExtensible,
                u = r((function() {
                    s(1)
                }));
            e.exports = u || a ? function(e) {
                return !!i(e) && ((!a || "ArrayBuffer" !== o(e)) && (!s || s(e)))
            } : s
        
