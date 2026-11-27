// chunk-vendors webpack 模块 1836（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            var r = n("./6ca1.js"),
                i = n("./6438.js").f,
                o = {}.toString,
                a = "object" == typeof window && window && Object.getOwnPropertyNames ? Object.getOwnPropertyNames(window) : [],
                s = function(e) {
                    try {
                        return i(e)
                    } catch (t) {
                        return a.slice()
                    }
                };
            e.exports.f = function(e) {
                return a && "[object Window]" == o.call(e) ? s(e) : i(r(e))
            }
        
