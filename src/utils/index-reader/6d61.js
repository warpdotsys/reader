// 恢复自 reader-pro-3.2.14 前端产物 beauty/index-reader.js 的 webpack 模块 6d61（逐字切片，形参 e, t, r）
const e = module;
const t = module.exports;
const r = require;

            "use strict";
            var n = r("23e7"),
                i = r("cfe9"),
                a = r("e330"),
                o = r("94ca"),
                s = r("cb2d"),
                l = r("f183"),
                u = r("2266"),
                c = r("19aa"),
                f = r("1626"),
                h = r("7234"),
                d = r("861d"),
                p = r("d039"),
                v = r("1c7e"),
                g = r("d44e"),
                m = r("7156");
            e.exports = function(e, t, r) {
                var b = -1 !== e.indexOf("Map"),
                    y = -1 !== e.indexOf("Weak"),
                    S = b ? "set" : "add",
                    w = i[e],
                    k = w && w.prototype,
                    z = w,
                    B = {},
                    R = function(e) {
                        var t = a(k[e]);
                        s(k, e, "add" === e ? function(e) {
                            return t(this, 0 === e ? 0 : e), this
                        } : "delete" === e ? function(e) {
                            return !(y && !d(e)) && t(this, 0 === e ? 0 : e)
                        } : "get" === e ? function(e) {
                            return y && !d(e) ? void 0 : t(this, 0 === e ? 0 : e)
                        } : "has" === e ? function(e) {
                            return !(y && !d(e)) && t(this, 0 === e ? 0 : e)
                        } : function(e, r) {
                            return t(this, 0 === e ? 0 : e, r), this
                        })
                    },
                    O = o(e, !f(w) || !(y || k.forEach && !p((function() {
                        (new w).entries().next()
                    }))));
                if (O) z = r.getConstructor(t, e, b, S), l.enable();
                else if (o(e, !0)) {
                    var U = new z,
                        P = U[S](y ? {} : -0, 1) !== U,
                        L = p((function() {
                            U.has(1)
                        })),
                        j = v((function(e) {
                            new w(e)
                        })),
                        x = !y && p((function() {
                            var e = new w,
                                t = 5;
                            while (t--) e[S](t, t);
                            return !e.has(-0)
                        }));
                    j || (z = t((function(e, t) {
                        c(e, k);
                        var r = m(new w, e, z);
                        return h(t) || u(t, r[S], {
                            that: r,
                            AS_ENTRIES: b
                        }), r
                    })), z.prototype = k, k.constructor = z), (L || x) && (R("delete"), R("has"), b && R("get")), (x || P) && R(S), y && k.clear && delete k.clear
                }
                return B[e] = z, n({
                    global: !0,
                    constructor: !0,
                    forced: z !== w
                }, B), g(z, e), y || r.setStrong(z, e, b), z
            }
        
