// chunk-vendors webpack 模块 d066（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./cfe9.js"),
                i = n("./1626.js"),
                o = function(e) {
                    return i(e) ? e : void 0
                };
            e.exports = function(e, t) {
                return arguments.length < 2 ? o(r[e]) : r[e] && r[e][t]
            }
        
