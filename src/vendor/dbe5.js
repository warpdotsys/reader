// chunk-vendors webpack 模块 dbe5（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./cfe9.js"),
                i = n("./d039.js"),
                o = n("./1212.js"),
                a = n("./8558.js"),
                s = r.structuredClone;
            e.exports = !!s && !i((function() {
                if ("DENO" === a && o > 92 || "NODE" === a && o > 94 || "BROWSER" === a && o > 97) return !1;
                var e = new ArrayBuffer(8),
                    t = s(e, {
                        transfer: [e]
                    });
                return 0 !== e.byteLength || 8 !== t.byteLength
            }))
        
