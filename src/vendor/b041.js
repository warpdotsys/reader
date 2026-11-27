// chunk-vendors webpack 模块 b041（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./00ee.js"),
                i = n("./f5df.js");
            e.exports = r ? {}.toString : function() {
                return "[object " + i(this) + "]"
            }
        
