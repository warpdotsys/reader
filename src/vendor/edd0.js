// chunk-vendors webpack 模块 edd0（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./13d2.js"),
                i = n("./9bf2.js");
            e.exports = function(e, t, n) {
                return n.get && r(n.get, t, {
                    getter: !0
                }), n.set && r(n.set, t, {
                    setter: !0
                }), i.f(e, t, n)
            }
        
