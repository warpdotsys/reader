// chunk-vendors webpack 模块 51eb（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./825a.js"),
                i = n("./485a.js"),
                o = TypeError;
            e.exports = function(e) {
                if (r(this), "string" === e || "default" === e) e = "string";
                else if ("number" !== e) throw new o("Incorrect hint");
                return i(this, e)
            }
        
