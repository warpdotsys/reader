// chunk-vendors webpack 模块 13d5（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./d58f.js").left,
                o = n("./a640.js"),
                a = n("./1212.js"),
                s = n("./9adc.js"),
                u = !s && a > 79 && a < 83,
                c = u || !o("reduce");
            r({
                target: "Array",
                proto: !0,
                forced: c
            }, {
                reduce: function(e) {
                    var t = arguments.length;
                    return i(this, e, t, t > 1 ? arguments[1] : void 0)
                }
            })
        
