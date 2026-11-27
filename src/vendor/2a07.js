// chunk-vendors webpack 模块 2a07（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./cfe9.js"),
                i = n("./9adc.js");
            e.exports = function(e) {
                if (i) {
                    try {
                        return r.process.getBuiltinModule(e)
                    } catch (t) {}
                    try {
                        return Function('return require("' + e + '")')()
                    } catch (t) {}
                }
            }
        
