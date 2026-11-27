// 恢复自 reader-pro-3.2.14 前端产物 beauty/index-reader.js 的 webpack 模块 271a（逐字切片，形参 e, t, r）
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
                u = i(l.getAll),
                c = i(l.has),
                f = new s("a=1");
            !f.has("a", 2) && f.has("a", void 0) || n(l, "has", (function(e) {
                var t = arguments.length,
                    r = t < 2 ? void 0 : arguments[1];
                if (t && void 0 === r) return c(this, e);
                var n = u(this, e);
                o(t, 1);
                var i = a(r),
                    s = 0;
                while (s < n.length)
                    if (n[s++] === i) return !0;
                return !1
            }), {
                enumerable: !0,
                unsafe: !0
            })
        
