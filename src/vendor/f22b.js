// chunk-vendors webpack 模块 f22b（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./f069.js"),
                o = n("./4738.js").CONSTRUCTOR;
            r({
                target: "Promise",
                stat: !0,
                forced: o
            }, {
                reject: function(e) {
                    var t = i.f(this),
                        n = t.reject;
                    return n(e), t.promise
                }
            })
        
