// chunk-vendors webpack 模块 9bf2（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./83ab.js"),
                i = n("./0cfb.js"),
                o = n("./aed9.js"),
                a = n("./825a.js"),
                s = n("./a04b.js"),
                u = TypeError,
                c = Object.defineProperty,
                l = Object.getOwnPropertyDescriptor,
                f = "enumerable",
                d = "configurable",
                h = "writable";
            t.f = r ? o ? function(e, t, n) {
                if (a(e), t = s(t), a(n), "function" === typeof e && "prototype" === t && "value" in n && h in n && !n[h]) {
                    var r = l(e, t);
                    r && r[h] && (e[t] = n.value, n = {
                        configurable: d in n ? n[d] : r[d],
                        enumerable: f in n ? n[f] : r[f],
                        writable: !1
                    })
                }
                return c(e, t, n)
            } : c : function(e, t, n) {
                if (a(e), t = s(t), a(n), i) try {
                    return c(e, t, n)
                } catch (r) {}
                if ("get" in n || "set" in n) throw new u("Accessors not supported");
                return "value" in n && (e[t] = n.value), e
            }
        
