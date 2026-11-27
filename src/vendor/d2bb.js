// chunk-vendors webpack 模块 d2bb（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./7282.js"),
                i = n("./861d.js"),
                o = n("./1d80.js"),
                a = n("./3bbe.js");
            e.exports = Object.setPrototypeOf || ("__proto__" in {} ? function() {
                var e, t = !1,
                    n = {};
                try {
                    e = r(Object.prototype, "__proto__", "set"), e(n, []), t = n instanceof Array
                } catch (s) {}
                return function(n, r) {
                    return o(n), a(r), i(n) ? (t ? e(n, r) : n.__proto__ = r, n) : n
                }
            }() : void 0)
        
