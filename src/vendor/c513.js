// chunk-vendors webpack 模块 c513（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./1a2d.js"),
                o = n("./d9b5.js"),
                a = n("./0d51.js"),
                s = n("./5692.js"),
                u = n("./0b43.js"),
                c = s("symbol-to-string-registry");
            r({
                target: "Symbol",
                stat: !0,
                forced: !u
            }, {
                keyFor: function(e) {
                    if (!o(e)) throw new TypeError(a(e) + " is not a symbol");
                    if (i(c, e)) return c[e]
                }
            })
        
