// chunk-vendors webpack 模块 f4b3（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./d039.js"),
                o = n("./7b0b.js"),
                a = n("./c04e.js"),
                s = i((function() {
                    return null !== new Date(NaN).toJSON() || 1 !== Date.prototype.toJSON.call({
                        toISOString: function() {
                            return 1
                        }
                    })
                }));
            r({
                target: "Date",
                proto: !0,
                arity: 1,
                forced: s
            }, {
                toJSON: function(e) {
                    var t = o(this),
                        n = a(t, "number");
                    return "number" != typeof n || isFinite(n) ? t.toISOString() : null
                }
            })
        
