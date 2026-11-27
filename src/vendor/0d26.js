// chunk-vendors webpack 模块 0d26（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./e330.js"),
                i = Error,
                o = r("".replace),
                a = function(e) {
                    return String(new i(e).stack)
                }("zxcasd"),
                s = /\n\s*at [^:]*:[^\n]*/,
                u = s.test(a);
            e.exports = function(e, t) {
                if (u && "string" == typeof e && !i.prepareStackTrace)
                    while (t--) e = o(e, s, "");
                return e
            }
        
