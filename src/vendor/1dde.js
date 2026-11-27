// chunk-vendors webpack 模块 1dde（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./d039.js"),
                i = n("./b622.js"),
                o = n("./1212.js"),
                a = i("species");
            e.exports = function(e) {
                return o >= 51 || !r((function() {
                    var t = [],
                        n = t.constructor = {};
                    return n[a] = function() {
                        return {
                            foo: 1
                        }
                    }, 1 !== t[e](Boolean).foo
                }))
            }
        
