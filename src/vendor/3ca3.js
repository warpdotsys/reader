// chunk-vendors webpack 模块 3ca3（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./6547.js").charAt,
                i = n("./577e.js"),
                o = n("./69f3.js"),
                a = n("./c6d2.js"),
                s = n("./4754.js"),
                u = "String Iterator",
                c = o.set,
                l = o.getterFor(u);
            a(String, "String", (function(e) {
                c(this, {
                    type: u,
                    string: i(e),
                    index: 0
                })
            }), (function() {
                var e, t = l(this),
                    n = t.string,
                    i = t.index;
                return i >= n.length ? s(void 0, !0) : (e = r(n, i), t.index += e.length, s(e, !1))
            }))
        
