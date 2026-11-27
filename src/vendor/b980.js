// chunk-vendors webpack 模块 b980（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./d039.js"),
                i = n("./5c6c.js");
            e.exports = !r((function() {
                var e = new Error("a");
                return !("stack" in e) || (Object.defineProperty(e, "stack", i(1, 7)), 7 !== e.stack)
            }))
        
