// chunk-vendors webpack 模块 857a（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./e330.js"),
                i = n("./1d80.js"),
                o = n("./577e.js"),
                a = /"/g,
                s = r("".replace);
            e.exports = function(e, t, n, r) {
                var u = o(i(e)),
                    c = "<" + t;
                return "" !== n && (c += " " + n + '="' + s(o(r), a, "&quot;") + '"'), c + ">" + u + "</" + t + ">"
            }
        
