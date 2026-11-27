// chunk-vendors webpack 模块 e267（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./e330.js"),
                i = n("./e8b5.js"),
                o = n("./1626.js"),
                a = n("./c6b6.js"),
                s = n("./577e.js"),
                u = r([].push);
            e.exports = function(e) {
                if (o(e)) return e;
                if (i(e)) {
                    for (var t = e.length, n = [], r = 0; r < t; r++) {
                        var c = e[r];
                        "string" == typeof c ? u(n, c) : "number" != typeof c && "Number" !== a(c) && "String" !== a(c) || u(n, s(c))
                    }
                    var l = n.length,
                        f = !0;
                    return function(e, t) {
                        if (f) return f = !1, t;
                        if (i(this)) return t;
                        for (var r = 0; r < l; r++)
                            if (n[r] === e) return t
                    }
                }
            }
        
