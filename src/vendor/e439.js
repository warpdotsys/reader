// chunk-vendors webpack 模块 e439（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./d039.js"),
                o = n("./fc6a.js"),
                a = n("./06cf.js").f,
                s = n("./83ab.js"),
                u = !s || i((function() {
                    a(1)
                }));
            r({
                target: "Object",
                stat: !0,
                forced: u,
                sham: !s
            }, {
                getOwnPropertyDescriptor: function(e, t) {
                    return a(o(e), t)
                }
            })
        
