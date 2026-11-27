// chunk-vendors webpack 模块 40e9（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./41f6.js");
            i && r({
                target: "ArrayBuffer",
                proto: !0
            }, {
                transferToFixedLength: function() {
                    return i(this, arguments.length ? arguments[0] : void 0, !1)
                }
            })
        
