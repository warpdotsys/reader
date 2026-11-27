// chunk-vendors webpack 模块 41f6（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./cfe9.js"),
                i = n("./e330.js"),
                o = n("./7282.js"),
                a = n("./0b25.js"),
                s = n("./2005.js"),
                u = n("./b620.js"),
                c = n("./36f2.js"),
                l = n("./dbe5.js"),
                f = r.structuredClone,
                d = r.ArrayBuffer,
                h = r.DataView,
                p = Math.min,
                v = d.prototype,
                m = h.prototype,
                g = i(v.slice),
                b = o(v, "resizable", "get"),
                y = o(v, "maxByteLength", "get"),
                _ = i(m.getInt8),
                w = i(m.setInt8);
            e.exports = (l || c) && function(e, t, n) {
                var r, i = u(e),
                    o = void 0 === t ? i : a(t),
                    v = !b || !b(e);
                if (s(e), l && (e = f(e, {
                        transfer: [e]
                    }), i === o && (n || v))) return e;
                if (i >= o && (!n || v)) r = g(e, 0, o);
                else {
                    var m = n && !v && y ? {
                        maxByteLength: y(e)
                    } : void 0;
                    r = new d(o, m);
                    for (var x = new h(e), C = new h(r), S = p(o, i), O = 0; O < S; O++) w(C, O, _(x, O))
                }
                return l || c(e), r
            }
        
