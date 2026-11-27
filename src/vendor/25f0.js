// chunk-vendors webpack 模块 25f0（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./5e77.js").PROPER,
                i = n("./cb2d.js"),
                o = n("./825a.js"),
                a = n("./577e.js"),
                s = n("./d039.js"),
                u = n("./90d8.js"),
                c = "toString",
                l = RegExp.prototype,
                f = l[c],
                d = s((function() {
                    return "/a/b" !== f.call({
                        source: "a",
                        flags: "b"
                    })
                })),
                h = r && f.name !== c;
            (d || h) && i(l, c, (function() {
                var e = o(this),
                    t = a(e.source),
                    n = a(u(e));
                return "/" + t + "/" + n
            }), {
                unsafe: !0
            })
        
