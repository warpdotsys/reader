// chunk-vendors webpack 模块 92f0（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            var r = n("./1a14.js").f,
                i = n("./9c0e.js"),
                o = n("./cc15.js")("toStringTag");
            e.exports = function(e, t, n) {
                e && !i(e = n ? e : e.prototype, o) && r(e, o, {
                    configurable: !0,
                    value: t
                })
            }
        
