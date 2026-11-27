// chunk-vendors webpack 模块 b620（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./cfe9.js"),
                i = n("./7282.js"),
                o = n("./c6b6.js"),
                a = r.ArrayBuffer,
                s = r.TypeError;
            e.exports = a && i(a.prototype, "byteLength", "get") || function(e) {
                if ("ArrayBuffer" !== o(e)) throw new s("ArrayBuffer expected");
                return e.byteLength
            }
        
