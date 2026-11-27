// chunk-vendors webpack 模块 c975（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./4625.js"),
                o = n("./4d64.js").indexOf,
                a = n("./a640.js"),
                s = i([].indexOf),
                u = !!s && 1 / s([1], 1, -0) < 0,
                c = u || !a("indexOf");
            r({
                target: "Array",
                proto: !0,
                forced: c
            }, {
                indexOf: function(e) {
                    var t = arguments.length > 1 ? arguments[1] : void 0;
                    return u ? s(this, e, t) || 0 : o(this, e, t)
                }
            })
        
