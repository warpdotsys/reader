// 恢复自 reader-pro-3.2.14 前端产物 beauty/reader.js 的 webpack 模块 0094（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = require;

            "use strict";
            var a, u = n("bb2f"),
                o = n("cfe9"),
                r = n("e330"),
                i = n("6964"),
                s = n("f183"),
                l = n("6d61"),
                c = n("acac"),
                p = n("861d"),
                d = n("69f3").enforce,
                D = n("d039"),
                h = n("cdce"),
                A = Object,
                F = Array.isArray,
                f = A.isExtensible,
                m = A.isFrozen,
                C = A.isSealed,
                E = A.freeze,
                y = A.seal,
                g = !o.ActiveXObject && "ActiveXObject" in o,
                b = function(e) {
                    return function() {
                        return e(this, arguments.length ? arguments[0] : void 0)
                    }
                },
                v = l("WeakMap", b, c),
                B = v.prototype,
                k = r(B.set),
                w = function() {
                    return u && D((function() {
                        var e = E([]);
                        return k(new v, e, 1), !m(e)
                    }))
                };
            if (h)
                if (g) {
                    a = c.getConstructor(b, "WeakMap", !0), s.enable();
                    var x = r(B["delete"]),
                        S = r(B.has),
                        N = r(B.get);
                    i(B, {
                        delete: function(e) {
                            if (p(e) && !f(e)) {
                                var t = d(this);
                                return t.frozen || (t.frozen = new a), x(this, e) || t.frozen["delete"](e)
                            }
                            return x(this, e)
                        },
                        has: function(e) {
                            if (p(e) && !f(e)) {
                                var t = d(this);
                                return t.frozen || (t.frozen = new a), S(this, e) || t.frozen.has(e)
                            }
                            return S(this, e)
                        },
                        get: function(e) {
                            if (p(e) && !f(e)) {
                                var t = d(this);
                                return t.frozen || (t.frozen = new a), S(this, e) ? N(this, e) : t.frozen.get(e)
                            }
                            return N(this, e)
                        },
                        set: function(e, t) {
                            if (p(e) && !f(e)) {
                                var n = d(this);
                                n.frozen || (n.frozen = new a), S(this, e) ? k(this, e, t) : n.frozen.set(e, t)
                            } else k(this, e, t);
                            return this
                        }
                    })
                } else w() && i(B, {
                    set: function(e, t) {
                        var n;
                        return F(e) && (m(e) ? n = E : C(e) && (n = y)), k(this, e, t), n && n(e), this
                    }
                })
        
