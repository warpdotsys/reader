// chunk-vendors webpack 模块 39ad（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            var r = n("./6ca1.js"),
                i = n("./d16a.js"),
                o = n("./9d11.js");
            e.exports = function(e) {
                return function(t, n, a) {
                    var s, u = r(t),
                        c = i(u.length),
                        l = o(a, c);
                    if (e && n != n) {
                        while (c > l)
                            if (s = u[l++], s != s) return !0
                    } else
                        for (; c > l; l++)
                            if ((e || l in u) && u[l] === n) return e || l || 0;
                    return !e && -1
                }
            }
        
