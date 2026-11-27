// chunk-vendors webpack 模块 f5df（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./00ee.js"),
                i = n("./1626.js"),
                o = n("./c6b6.js"),
                a = n("./b622.js"),
                s = a("toStringTag"),
                u = Object,
                c = "Arguments" === o(function() {
                    return arguments
                }()),
                l = function(e, t) {
                    try {
                        return e[t]
                    } catch (n) {}
                };
            e.exports = r ? o : function(e) {
                var t, n, r;
                return void 0 === e ? "Undefined" : null === e ? "Null" : "string" == typeof(n = l(t = u(e), s)) ? n : c ? o(t) : "Object" === (r = o(t)) && i(t.callee) ? "Arguments" : r
            }
        
