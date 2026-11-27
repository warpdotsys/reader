// chunk-vendors webpack 模块 45fc（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./b727.js").some,
                o = n("./a640.js"),
                a = o("some");
            r({
                target: "Array",
                proto: !0,
                forced: !a
            }, {
                some: function(e) {
                    return i(this, e, arguments.length > 1 ? arguments[1] : void 0)
                }
            })
        
