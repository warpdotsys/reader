// chunk-vendors webpack 模块 05f5（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            var r = n("./7a41.js"),
                i = n("./ef08.js").document,
                o = r(i) && r(i.createElement);
            e.exports = function(e) {
                return o ? i.createElement(e) : {}
            }
        
