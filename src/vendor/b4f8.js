// chunk-vendors webpack 模块 b4f8（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./d066.js"),
                o = n("./1a2d.js"),
                a = n("./577e.js"),
                s = n("./5692.js"),
                u = n("./0b43.js"),
                c = s("string-to-symbol-registry"),
                l = s("symbol-to-string-registry");
            r({
                target: "Symbol",
                stat: !0,
                forced: !u
            }, {
                for: function(e) {
                    var t = a(e);
                    if (o(c, t)) return c[t];
                    var n = i("Symbol")(t);
                    return c[t] = n, l[n] = t, n
                }
            })
        
