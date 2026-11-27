// chunk-vendors webpack 模块 2c3e（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./83ab.js"),
                i = n("./9f7f.js").MISSED_STICKY,
                o = n("./c6b6.js"),
                a = n("./edd0.js"),
                s = n("./69f3.js").get,
                u = RegExp.prototype,
                c = TypeError;
            r && i && a(u, "sticky", {
                configurable: !0,
                get: function() {
                    if (this !== u) {
                        if ("RegExp" === o(this)) return !!s(this).sticky;
                        throw new c("Incompatible receiver, RegExp required")
                    }
                }
            })
        
