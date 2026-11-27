// chunk-vendors webpack 模块 b0c0（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./83ab.js"),
                i = n("./5e77.js").EXISTS,
                o = n("./e330.js"),
                a = n("./edd0.js"),
                s = Function.prototype,
                u = o(s.toString),
                c = /function\b(?:\s|\/\*[\S\s]*?\*\/|\/\/[^\n\r]*[\n\r]+)*([^\s(/]*)/,
                l = o(c.exec),
                f = "name";
            r && !i && a(s, f, {
                configurable: !0,
                get: function() {
                    try {
                        return l(c, u(this))[1]
                    } catch (e) {
                        return ""
                    }
                }
            })
        
