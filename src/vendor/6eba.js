// chunk-vendors webpack 模块 6eba（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./e330.js"),
                o = Date,
                a = i(o.prototype.getTime);
            r({
                target: "Date",
                stat: !0
            }, {
                now: function() {
                    return a(new o)
                }
            })
        
