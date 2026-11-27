// 恢复自 reader-pro-3.2.14 前端产物 beauty/index-reader.js 的 webpack 模块 5352（逐字切片，形参 e, t, r）
const e = module;
const t = module.exports;
const r = require;

            "use strict";
            r("e260"), r("f6d6");
            var n = r("23e7"),
                i = r("cfe9"),
                a = r("157a"),
                o = r("d066"),
                s = r("c65b"),
                l = r("e330"),
                u = r("83ab"),
                c = r("f354"),
                f = r("cb2d"),
                h = r("edd0"),
                d = r("6964"),
                p = r("d44e"),
                v = r("dcc3"),
                g = r("69f3"),
                m = r("19aa"),
                b = r("1626"),
                y = r("1a2d"),
                S = r("0366"),
                w = r("f5df"),
                k = r("825a"),
                z = r("861d"),
                B = r("577e"),
                R = r("7c73"),
                O = r("5c6c"),
                U = r("9a1f"),
                P = r("35a1"),
                L = r("4754"),
                j = r("d6d6"),
                x = r("b622"),
                E = r("addb"),
                C = x("iterator"),
                H = "URLSearchParams",
                A = H + "Iterator",
                T = g.set,
                q = g.getterFor(H),
                F = g.getterFor(A),
                M = a("fetch"),
                V = a("Request"),
                N = a("Headers"),
                $ = V && V.prototype,
                I = N && N.prototype,
                G = i.TypeError,
                K = i.encodeURIComponent,
                D = String.fromCharCode,
                J = o("String", "fromCodePoint"),
                Q = parseInt,
                _ = l("".charAt),
                Y = l([].join),
                W = l([].push),
                X = l("".replace),
                Z = l([].shift),
                ee = l([].splice),
                te = l("".split),
                re = l("".slice),
                ne = l(/./.exec),
                ie = /\+/g,
                ae = "�",
                oe = /^[0-9a-f]+$/i,
                se = function(e, t) {
                    var r = re(e, t, t + 2);
                    return ne(oe, r) ? Q(r, 16) : NaN
                },
                le = function(e) {
                    for (var t = 0, r = 128; r > 0 && 0 !== (e & r); r >>= 1) t++;
                    return t
                },
                ue = function(e) {
                    var t = null;
                    switch (e.length) {
                        case 1:
                            t = e[0];
                            break;
                        case 2:
                            t = (31 & e[0]) << 6 | 63 & e[1];
                            break;
                        case 3:
                            t = (15 & e[0]) << 12 | (63 & e[1]) << 6 | 63 & e[2];
                            break;
                        case 4:
                            t = (7 & e[0]) << 18 | (63 & e[1]) << 12 | (63 & e[2]) << 6 | 63 & e[3];
                            break
                    }
                    return t > 1114111 ? null : t
                },
                ce = function(e) {
                    e = X(e, ie, " ");
                    var t = e.length,
                        r = "",
                        n = 0;
                    while (n < t) {
                        var i = _(e, n);
                        if ("%" === i) {
                            if ("%" === _(e, n + 1) || n + 3 > t) {
                                r += "%", n++;
                                continue
                            }
                            var a = se(e, n + 1);
                            if (a !== a) {
                                r += i, n++;
                                continue
                            }
                            n += 2;
                            var o = le(a);
                            if (0 === o) i = D(a);
                            else {
                                if (1 === o || o > 4) {
                                    r += ae, n++;
                                    continue
                                }
                                var s = [a],
                                    l = 1;
                                while (l < o) {
                                    if (n++, n + 3 > t || "%" !== _(e, n)) break;
                                    var u = se(e, n + 1);
                                    if (u !== u) {
                                        n += 3;
                                        break
                                    }
                                    if (u > 191 || u < 128) break;
                                    W(s, u), n += 2, l++
                                }
                                if (s.length !== o) {
                                    r += ae;
                                    continue
                                }
                                var c = ue(s);
                                null === c ? r += ae : i = J(c)
                            }
                        }
                        r += i, n++
                    }
                    return r
                },
                fe = /[!'()~]|%20/g,
                he = {
                    "!": "%21",
                    "'": "%27",
                    "(": "%28",
                    ")": "%29",
                    "~": "%7E",
                    "%20": "+"
                },
                de = function(e) {
                    return he[e]
                },
                pe = function(e) {
                    return X(K(e), fe, de)
                },
                ve = v((function(e, t) {
                    T(this, {
                        type: A,
                        target: q(e).entries,
                        index: 0,
                        kind: t
                    })
                }), H, (function() {
                    var e = F(this),
                        t = e.target,
                        r = e.index++;
                    if (!t || r >= t.length) return e.target = null, L(void 0, !0);
                    var n = t[r];
                    switch (e.kind) {
                        case "keys":
                            return L(n.key, !1);
                        case "values":
                            return L(n.value, !1)
                    }
                    return L([n.key, n.value], !1)
                }), !0),
                ge = function(e) {
                    this.entries = [], this.url = null, void 0 !== e && (z(e) ? this.parseObject(e) : this.parseQuery("string" == typeof e ? "?" === _(e, 0) ? re(e, 1) : e : B(e)))
                };
            ge.prototype = {
                type: H,
                bindURL: function(e) {
                    this.url = e, this.update()
                },
                parseObject: function(e) {
                    var t, r, n, i, a, o, l, u = this.entries,
                        c = P(e);
                    if (c) {
                        t = U(e, c), r = t.next;
                        while (!(n = s(r, t)).done) {
                            if (i = U(k(n.value)), a = i.next, (o = s(a, i)).done || (l = s(a, i)).done || !s(a, i).done) throw new G("Expected sequence with length 2");
                            W(u, {
                                key: B(o.value),
                                value: B(l.value)
                            })
                        }
                    } else
                        for (var f in e) y(e, f) && W(u, {
                            key: f,
                            value: B(e[f])
                        })
                },
                parseQuery: function(e) {
                    if (e) {
                        var t, r, n = this.entries,
                            i = te(e, "&"),
                            a = 0;
                        while (a < i.length) t = i[a++], t.length && (r = te(t, "="), W(n, {
                            key: ce(Z(r)),
                            value: ce(Y(r, "="))
                        }))
                    }
                },
                serialize: function() {
                    var e, t = this.entries,
                        r = [],
                        n = 0;
                    while (n < t.length) e = t[n++], W(r, pe(e.key) + "=" + pe(e.value));
                    return Y(r, "&")
                },
                update: function() {
                    this.entries.length = 0, this.parseQuery(this.url.query)
                },
                updateURL: function() {
                    this.url && this.url.update()
                }
            };
            var me = function() {
                    m(this, be);
                    var e = arguments.length > 0 ? arguments[0] : void 0,
                        t = T(this, new ge(e));
                    u || (this.size = t.entries.length)
                },
                be = me.prototype;
            if (d(be, {
                    append: function(e, t) {
                        var r = q(this);
                        j(arguments.length, 2), W(r.entries, {
                            key: B(e),
                            value: B(t)
                        }), u || this.length++, r.updateURL()
                    },
                    delete: function(e) {
                        var t = q(this),
                            r = j(arguments.length, 1),
                            n = t.entries,
                            i = B(e),
                            a = r < 2 ? void 0 : arguments[1],
                            o = void 0 === a ? a : B(a),
                            s = 0;
                        while (s < n.length) {
                            var l = n[s];
                            if (l.key !== i || void 0 !== o && l.value !== o) s++;
                            else if (ee(n, s, 1), void 0 !== o) break
                        }
                        u || (this.size = n.length), t.updateURL()
                    },
                    get: function(e) {
                        var t = q(this).entries;
                        j(arguments.length, 1);
                        for (var r = B(e), n = 0; n < t.length; n++)
                            if (t[n].key === r) return t[n].value;
                        return null
                    },
                    getAll: function(e) {
                        var t = q(this).entries;
                        j(arguments.length, 1);
                        for (var r = B(e), n = [], i = 0; i < t.length; i++) t[i].key === r && W(n, t[i].value);
                        return n
                    },
                    has: function(e) {
                        var t = q(this).entries,
                            r = j(arguments.length, 1),
                            n = B(e),
                            i = r < 2 ? void 0 : arguments[1],
                            a = void 0 === i ? i : B(i),
                            o = 0;
                        while (o < t.length) {
                            var s = t[o++];
                            if (s.key === n && (void 0 === a || s.value === a)) return !0
                        }
                        return !1
                    },
                    set: function(e, t) {
                        var r = q(this);
                        j(arguments.length, 1);
                        for (var n, i = r.entries, a = !1, o = B(e), s = B(t), l = 0; l < i.length; l++) n = i[l], n.key === o && (a ? ee(i, l--, 1) : (a = !0, n.value = s));
                        a || W(i, {
                            key: o,
                            value: s
                        }), u || (this.size = i.length), r.updateURL()
                    },
                    sort: function() {
                        var e = q(this);
                        E(e.entries, (function(e, t) {
                            return e.key > t.key ? 1 : -1
                        })), e.updateURL()
                    },
                    forEach: function(e) {
                        var t, r = q(this).entries,
                            n = S(e, arguments.length > 1 ? arguments[1] : void 0),
                            i = 0;
                        while (i < r.length) t = r[i++], n(t.value, t.key, this)
                    },
                    keys: function() {
                        return new ve(this, "keys")
                    },
                    values: function() {
                        return new ve(this, "values")
                    },
                    entries: function() {
                        return new ve(this, "entries")
                    }
                }, {
                    enumerable: !0
                }), f(be, C, be.entries, {
                    name: "entries"
                }), f(be, "toString", (function() {
                    return q(this).serialize()
                }), {
                    enumerable: !0
                }), u && h(be, "size", {
                    get: function() {
                        return q(this).entries.length
                    },
                    configurable: !0,
                    enumerable: !0
                }), p(me, H), n({
                    global: !0,
                    constructor: !0,
                    forced: !c
                }, {
                    URLSearchParams: me
                }), !c && b(N)) {
                var ye = l(I.has),
                    Se = l(I.set),
                    we = function(e) {
                        if (z(e)) {
                            var t, r = e.body;
                            if (w(r) === H) return t = e.headers ? new N(e.headers) : new N, ye(t, "content-type") || Se(t, "content-type", "application/x-www-form-urlencoded;charset=UTF-8"), R(e, {
                                body: O(0, B(r)),
                                headers: O(0, t)
                            })
                        }
                        return e
                    };
                if (b(M) && n({
                        global: !0,
                        enumerable: !0,
                        dontCallGetSet: !0,
                        forced: !0
                    }, {
                        fetch: function(e) {
                            return M(e, arguments.length > 1 ? we(arguments[1]) : {})
                        }
                    }), b(V)) {
                    var ke = function(e) {
                        return m(this, $), new V(e, arguments.length > 1 ? we(arguments[1]) : {})
                    };
                    $.constructor = ke, ke.prototype = $, n({
                        global: !0,
                        constructor: !0,
                        dontCallGetSet: !0,
                        forced: !0
                    }, {
                        Request: ke
                    })
                }
            }
            e.exports = {
                URLSearchParams: me,
                getState: q
            }
        
