// chunk-vendors webpack 模块 e323（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./e330.js"),
                o = n("./1d80.js"),
                a = n("./5926.js"),
                s = n("./577e.js"),
                u = i("".slice),
                c = Math.max,
                l = Math.min,
                f = !"".substr || "b" !== "ab".substr(-1);
            r({
                target: "String",
                proto: !0,
                forced: f
            }, {
                substr: function(e, t) {
                    var n, r, i = s(o(this)),
                        f = i.length,
                        d = a(e);
                    return d === 1 / 0 && (d = 0), d < 0 && (d = c(f + d, 0)), n = void 0 === t ? f : a(t), n <= 0 || n === 1 / 0 ? "" : (r = l(d + n, f), d >= r ? "" : u(i, d, r))
                }
            })
        
