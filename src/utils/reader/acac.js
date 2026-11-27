// 恢复自 reader-pro-3.2.14 前端产物 beauty/reader.js 的 webpack 模块 acac（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = require;

            "use strict";
            var a = n("e330"),
                u = n("6964"),
                o = n("f183").getWeakData,
                r = n("19aa"),
                i = n("825a"),
                s = n("7234"),
                l = n("861d"),
                c = n("2266"),
                p = n("b727"),
                d = n("1a2d"),
                D = n("69f3"),
                h = D.set,
                A = D.getterFor,
                F = p.find,
                f = p.findIndex,
                m = a([].splice),
                C = 0,
                E = function(e) {
                    return e.frozen || (e.frozen = new y)
                },
                y = function() {
                    this.entries = []
                },
                g = function(e, t) {
                    return F(e.entries, (function(e) {
                        return e[0] === t
                    }))
                };
            y.prototype = {
                get: function(e) {
                    var t = g(this, e);
                    if (t) return t[1]
                },
                has: function(e) {
                    return !!g(this, e)
                },
                set: function(e, t) {
                    var n = g(this, e);
                    n ? n[1] = t : this.entries.push([e, t])
                },
                delete: function(e) {
                    var t = f(this.entries, (function(t) {
                        return t[0] === e
                    }));
                    return ~t && m(this.entries, t, 1), !!~t
                }
            }, e.exports = {
                getConstructor: function(e, t, n, a) {
                    var p = e((function(e, u) {
                            r(e, D), h(e, {
                                type: t,
                                id: C++,
                                frozen: null
                            }), s(u) || c(u, e[a], {
                                that: e,
                                AS_ENTRIES: n
                            })
                        })),
                        D = p.prototype,
                        F = A(t),
                        f = function(e, t, n) {
                            var a = F(e),
                                u = o(i(t), !0);
                            return !0 === u ? E(a).set(t, n) : u[a.id] = n, e
                        };
                    return u(D, {
                        delete: function(e) {
                            var t = F(this);
                            if (!l(e)) return !1;
                            var n = o(e);
                            return !0 === n ? E(t)["delete"](e) : n && d(n, t.id) && delete n[t.id]
                        },
                        has: function(e) {
                            var t = F(this);
                            if (!l(e)) return !1;
                            var n = o(e);
                            return !0 === n ? E(t).has(e) : n && d(n, t.id)
                        }
                    }), u(D, n ? {
                        get: function(e) {
                            var t = F(this);
                            if (l(e)) {
                                var n = o(e);
                                if (!0 === n) return E(t).get(e);
                                if (n) return n[t.id]
                            }
                        },
                        set: function(e, t) {
                            return f(this, e, t)
                        }
                    } : {
                        add: function(e) {
                            return f(this, e, !0)
                        }
                    }), p
                }
            }
        
