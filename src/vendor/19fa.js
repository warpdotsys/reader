// chunk-vendors webpack 模块 19fa（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            var r = n("./fc5e.js"),
                i = n("./c901.js");
            e.exports = function(e) {
                return function(t, n) {
                    var o, a, s = String(i(t)),
                        u = r(n),
                        c = s.length;
                    return u < 0 || u >= c ? e ? "" : void 0 : (o = s.charCodeAt(u), o < 55296 || o > 56319 || u + 1 === c || (a = s.charCodeAt(u + 1)) < 56320 || a > 57343 ? e ? s.charAt(u) : o : e ? s.slice(u, u + 2) : a - 56320 + (o - 55296 << 10) + 65536)
                }
            }
        
