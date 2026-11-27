// chunk-vendors webpack 模块 f99f（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./cfe9.js");
            e.exports = function(e, t) {
                var n = r.Iterator,
                    i = n && n.prototype,
                    o = i && i[e],
                    a = !1;
                if (o) try {
                    o.call({
                        next: function() {
                            return {
                                done: !0
                            }
                        },
                        return: function() {
                            a = !0
                        }
                    }, -1)
                } catch (s) {
                    s instanceof t || (a = !1)
                }
                if (!a) return o
            }
        
