// chunk-vendors webpack 模块 7f4d（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            t.__esModule = !0, t["default"] = function(e) {
                for (var t = 1, n = arguments.length; t < n; t++) {
                    var r = arguments[t] || {};
                    for (var i in r)
                        if (r.hasOwnProperty(i)) {
                            var o = r[i];
                            void 0 !== o && (e[i] = o)
                        }
                }
                return e
            }
        
