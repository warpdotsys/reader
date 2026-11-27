// chunk-vendors webpack 模块 ab13（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./b622.js"),
                i = r("match");
            e.exports = function(e) {
                var t = /./;
                try {
                    "/./" [e](t)
                } catch (n) {
                    try {
                        return t[i] = !1, "/./" [e](t)
                    } catch (r) {}
                }
                return !1
            }
        
