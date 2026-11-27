// chunk-vendors webpack 模块 c56a（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            n("./d9e2.js"), n("./d401.js"), n("./4795.js"), t.__esModule = !0, t["default"] = function(e, t) {
                var n = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : 300,
                    r = arguments.length > 3 && void 0 !== arguments[3] && arguments[3];
                if (!e || !t) throw new Error("instance & callback is required");
                var i = !1,
                    o = function() {
                        i || (i = !0, t && t.apply(null, arguments))
                    };
                r ? e.$once("after-leave", o) : e.$on("after-leave", o), setTimeout((function() {
                    o()
                }), n + 100)
            }
        
