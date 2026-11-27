// chunk-vendors webpack 模块 e01a（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./83ab.js"),
                o = n("./cfe9.js"),
                a = n("./e330.js"),
                s = n("./1a2d.js"),
                u = n("./1626.js"),
                c = n("./3a9b.js"),
                l = n("./577e.js"),
                f = n("./edd0.js"),
                d = n("./e893.js"),
                h = o.Symbol,
                p = h && h.prototype;
            if (i && u(h) && (!("description" in p) || void 0 !== h().description)) {
                var v = {},
                    m = function() {
                        var e = arguments.length < 1 || void 0 === arguments[0] ? void 0 : l(arguments[0]),
                            t = c(p, this) ? new h(e) : void 0 === e ? h() : h(e);
                        return "" === e && (v[t] = !0), t
                    };
                d(m, h), m.prototype = p, p.constructor = m;
                var g = "Symbol(description detection)" === String(h("description detection")),
                    b = a(p.valueOf),
                    y = a(p.toString),
                    _ = /^Symbol\((.*)\)[^)]+$/,
                    w = a("".replace),
                    x = a("".slice);
                f(p, "description", {
                    configurable: !0,
                    get: function() {
                        var e = b(this);
                        if (s(v, e)) return "";
                        var t = y(e),
                            n = g ? x(t, 7, -1) : w(t, _, "$1");
                        return "" === n ? void 0 : n
                    }
                }), r({
                    global: !0,
                    constructor: !0,
                    forced: !0
                }, {
                    Symbol: m
                })
            }
        
