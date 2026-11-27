// chunk-vendors webpack 模块 cfe9（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            (function(t) {
                var n = function(e) {
                    return e && e.Math === Math && e
                };
                e.exports = n("object" == typeof globalThis && globalThis) || n("object" == typeof window && window) || n("object" == typeof self && self) || n("object" == typeof t && t) || n("object" == typeof this && this) || function() {
                    return this
                }() || Function("return this")()
            }).call(this, n("./c8ba.js"))
        
