// chunk-vendors webpack 模块 485a（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./c65b.js"),
                i = n("./1626.js"),
                o = n("./861d.js"),
                a = TypeError;
            e.exports = function(e, t) {
                var n, s;
                if ("string" === t && i(n = e.toString) && !o(s = r(n, e))) return s;
                if (i(n = e.valueOf) && !o(s = r(n, e))) return s;
                if ("string" !== t && i(n = e.toString) && !o(s = r(n, e))) return s;
                throw new a("Can't convert object to primitive value")
            }
        
