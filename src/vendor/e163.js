// chunk-vendors webpack 模块 e163（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./1a2d.js"),
                i = n("./1626.js"),
                o = n("./7b0b.js"),
                a = n("./f772.js"),
                s = n("./e177.js"),
                u = a("IE_PROTO"),
                c = Object,
                l = c.prototype;
            e.exports = s ? c.getPrototypeOf : function(e) {
                var t = o(e);
                if (r(t, u)) return t[u];
                var n = t.constructor;
                return i(n) && t instanceof n ? n.prototype : t instanceof c ? l : null
            }
        
