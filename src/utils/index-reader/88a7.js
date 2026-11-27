// 恢复自 reader-pro-3.2.14 前端产物 beauty/index-reader.js 的 webpack 模块 88a7（逐字切片，形参 e, t, r）
const e = module;
const t = module.exports;
const r = require;

            "use strict";
            var n = r("cb2d"),
                i = r("e330"),
                a = r("577e"),
                o = r("d6d6"),
                s = URLSearchParams,
                l = s.prototype,
                u = i(l.append),
                c = i(l["delete"]),
                f = i(l.forEach),
                h = i([].push),
                d = new s("a=1&a=2&b=3");
            d["delete"]("a", 1), d["delete"]("b", void 0), d + "" !== "a=2" && n(l, "delete", (function(e) {
                var t = arguments.length,
                    r = t < 2 ? void 0 : arguments[1];
                if (t && void 0 === r) return c(this, e);
                var n = [];
                f(this, (function(e, t) {
                    h(n, {
                        key: t,
                        value: e
                    })
                })), o(t, 1);
                var i, s = a(e),
                    l = a(r),
                    d = 0,
                    p = 0,
                    v = !1,
                    g = n.length;
                while (d < g) i = n[d++], v || i.key === s ? (v = !0, c(this, i.key)) : p++;
                while (p < g) i = n[p++], i.key === s && i.value === l || u(this, i.key, i.value)
            }), {
                enumerable: !0,
                unsafe: !0
            })
        
