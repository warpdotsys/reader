// chunk-vendors webpack 模块 7149（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./d066.js"),
                o = n("./c430.js"),
                a = n("./d256.js"),
                s = n("./4738.js").CONSTRUCTOR,
                u = n("./cdf9.js"),
                c = i("Promise"),
                l = o && !s;
            r({
                target: "Promise",
                stat: !0,
                forced: o || s
            }, {
                resolve: function(e) {
                    return u(l && this === c ? a : this, e)
                }
            })
        
