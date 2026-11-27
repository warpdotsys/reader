// chunk-vendors webpack 模块 90d8（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./c65b.js"),
                i = n("./1a2d.js"),
                o = n("./3a9b.js"),
                a = n("./ad6d.js"),
                s = RegExp.prototype;
            e.exports = function(e) {
                var t = e.flags;
                return void 0 !== t || "flags" in s || i(e, "flags") || !o(s, e) ? t : r(a, e)
            }
        
