// chunk-vendors webpack 模块 057f（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./c6b6.js"),
                i = n("./fc6a.js"),
                o = n("./241c.js").f,
                a = n("./f36a.js"),
                s = "object" == typeof window && window && Object.getOwnPropertyNames ? Object.getOwnPropertyNames(window) : [],
                u = function(e) {
                    try {
                        return o(e)
                    } catch (t) {
                        return a(s)
                    }
                };
            e.exports.f = function(e) {
                return s && "Window" === r(e) ? u(e) : o(i(e))
            }
        
