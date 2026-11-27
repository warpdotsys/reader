// chunk-vendors webpack 模块 85e7（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            var r = n("./1a14.js"),
                i = n("./77e9.js"),
                o = n("./9876.js");
            e.exports = n("./0bad.js") ? Object.defineProperties : function(e, t) {
                i(e);
                var n, a = o(t),
                    s = a.length,
                    u = 0;
                while (s > u) r.f(e, n = a[u++], t[n]);
                return e
            }
        
