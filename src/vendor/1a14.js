// chunk-vendors webpack 模块 1a14（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            var r = n("./77e9.js"),
                i = n("./faf5.js"),
                o = n("./3397.js"),
                a = Object.defineProperty;
            t.f = n("./0bad.js") ? Object.defineProperty : function(e, t, n) {
                if (r(e), t = o(t, !0), r(n), i) try {
                    return a(e, t, n)
                } catch (s) {}
                if ("get" in n || "set" in n) throw TypeError("Accessors not supported!");
                return "value" in n && (e[t] = n.value), e
            }
        
