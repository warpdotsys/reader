// chunk-vendors webpack 模块 3529（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./c65b.js"),
                o = n("./59ed.js"),
                a = n("./f069.js"),
                s = n("./e667.js"),
                u = n("./2266.js"),
                c = n("./5eed.js");
            r({
                target: "Promise",
                stat: !0,
                forced: c
            }, {
                race: function(e) {
                    var t = this,
                        n = a.f(t),
                        r = n.reject,
                        c = s((function() {
                            var a = o(t.resolve);
                            u(e, (function(e) {
                                i(a, t, e).then(n.resolve, r)
                            }))
                        }));
                    return c.error && r(c.value), n.promise
                }
            })
        
