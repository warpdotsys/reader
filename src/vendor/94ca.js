// chunk-vendors webpack 模块 94ca（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./d039.js"),
                i = n("./1626.js"),
                o = /#|\.prototype\./,
                a = function(e, t) {
                    var n = u[s(e)];
                    return n === l || n !== c && (i(t) ? r(t) : !!t)
                },
                s = a.normalize = function(e) {
                    return String(e).replace(o, ".").toLowerCase()
                },
                u = a.data = {},
                c = a.NATIVE = "N",
                l = a.POLYFILL = "P";
            e.exports = a
        
