// chunk-vendors webpack 模块 2626（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./d066.js"),
                i = n("./edd0.js"),
                o = n("./b622.js"),
                a = n("./83ab.js"),
                s = o("species");
            e.exports = function(e) {
                var t = r(e);
                a && t && !t[s] && i(t, s, {
                    configurable: !0,
                    get: function() {
                        return this
                    }
                })
            }
        
