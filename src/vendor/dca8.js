// chunk-vendors webpack 模块 dca8（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./bb2f.js"),
                o = n("./d039.js"),
                a = n("./861d.js"),
                s = n("./f183.js").onFreeze,
                u = Object.freeze,
                c = o((function() {
                    u(1)
                }));
            r({
                target: "Object",
                stat: !0,
                forced: c,
                sham: !i
            }, {
                freeze: function(e) {
                    return u && a(e) ? u(s(e)) : e
                }
            })
        
