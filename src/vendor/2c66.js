// chunk-vendors webpack 模块 2c66（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./83ab.js"),
                i = n("./edd0.js"),
                o = n("./75bd.js"),
                a = ArrayBuffer.prototype;
            r && !("detached" in a) && i(a, "detached", {
                configurable: !0,
                get: function() {
                    return o(this)
                }
            })
        
