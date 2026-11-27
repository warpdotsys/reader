// chunk-vendors webpack 模块 9d11（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            var r = n("./fc5e.js"),
                i = Math.max,
                o = Math.min;
            e.exports = function(e, t) {
                return e = r(e), e < 0 ? i(e + t, 0) : o(e, t)
            }
        
