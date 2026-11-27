// chunk-vendors webpack 模块 cc98（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./c430.js"),
                o = n("./4738.js").CONSTRUCTOR,
                a = n("./d256.js"),
                s = n("./d066.js"),
                u = n("./1626.js"),
                c = n("./cb2d.js"),
                l = a && a.prototype;
            if (r({
                    target: "Promise",
                    proto: !0,
                    forced: o,
                    real: !0
                }, {
                    catch: function(e) {
                        return this.then(void 0, e)
                    }
                }), !i && u(a)) {
                var f = s("Promise").prototype["catch"];
                l["catch"] !== f && c(l, "catch", f, {
                    unsafe: !0
                })
            }
        
