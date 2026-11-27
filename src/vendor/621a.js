// chunk-vendors webpack 模块 621a（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./cfe9.js"),
                i = n("./e330.js"),
                o = n("./83ab.js"),
                a = n("./4b11.js"),
                s = n("./5e77.js"),
                u = n("./9112.js"),
                c = n("./edd0.js"),
                l = n("./6964.js"),
                f = n("./d039.js"),
                d = n("./19aa.js"),
                h = n("./5926.js"),
                p = n("./50c4.js"),
                v = n("./0b25.js"),
                m = n("./be8e.js"),
                g = n("./77a7.js"),
                b = n("./e163.js"),
                y = n("./d2bb.js"),
                _ = n("./81d5.js"),
                w = n("./f36a.js"),
                x = n("./7156.js"),
                C = n("./e893.js"),
                S = n("./d44e.js"),
                O = n("./69f3.js"),
                E = s.PROPER,
                k = s.CONFIGURABLE,
                T = "ArrayBuffer",
                j = "DataView",
                $ = "prototype",
                A = "Wrong length",
                P = "Wrong index",
                I = O.getterFor(T),
                N = O.getterFor(j),
                M = O.set,
                L = r[T],
                R = L,
                F = R && R[$],
                D = r[j],
                B = D && D[$],
                z = Object.prototype,
                V = r.Array,
                H = r.RangeError,
                W = i(_),
                q = i([].reverse),
                U = g.pack,
                X = g.unpack,
                G = function(e) {
                    return [255 & e]
                },
                Y = function(e) {
                    return [255 & e, e >> 8 & 255]
                },
                K = function(e) {
                    return [255 & e, e >> 8 & 255, e >> 16 & 255, e >> 24 & 255]
                },
                J = function(e) {
                    return e[3] << 24 | e[2] << 16 | e[1] << 8 | e[0]
                },
                Z = function(e) {
                    return U(m(e), 23, 4)
                },
                Q = function(e) {
                    return U(e, 52, 8)
                },
                ee = function(e, t, n) {
                    c(e[$], t, {
                        configurable: !0,
                        get: function() {
                            return n(this)[t]
                        }
                    })
                },
                te = function(e, t, n, r) {
                    var i = N(e),
                        o = v(n),
                        a = !!r;
                    if (o + t > i.byteLength) throw new H(P);
                    var s = i.bytes,
                        u = o + i.byteOffset,
                        c = w(s, u, u + t);
                    return a ? c : q(c)
                },
                ne = function(e, t, n, r, i, o) {
                    var a = N(e),
                        s = v(n),
                        u = r(+i),
                        c = !!o;
                    if (s + t > a.byteLength) throw new H(P);
                    for (var l = a.bytes, f = s + a.byteOffset, d = 0; d < t; d++) l[f + d] = u[c ? d : t - d - 1]
                };
            if (a) {
                var re = E && L.name !== T;
                f((function() {
                    L(1)
                })) && f((function() {
                    new L(-1)
                })) && !f((function() {
                    return new L, new L(1.5), new L(NaN), 1 !== L.length || re && !k
                })) ? re && k && u(L, "name", T) : (R = function(e) {
                    return d(this, F), x(new L(v(e)), this, R)
                }, R[$] = F, F.constructor = R, C(R, L)), y && b(B) !== z && y(B, z);
                var ie = new D(new R(2)),
                    oe = i(B.setInt8);
                ie.setInt8(0, 2147483648), ie.setInt8(1, 2147483649), !ie.getInt8(0) && ie.getInt8(1) || l(B, {
                    setInt8: function(e, t) {
                        oe(this, e, t << 24 >> 24)
                    },
                    setUint8: function(e, t) {
                        oe(this, e, t << 24 >> 24)
                    }
                }, {
                    unsafe: !0
                })
            } else R = function(e) {
                d(this, F);
                var t = v(e);
                M(this, {
                    type: T,
                    bytes: W(V(t), 0),
                    byteLength: t
                }), o || (this.byteLength = t, this.detached = !1)
            }, F = R[$], D = function(e, t, n) {
                d(this, B), d(e, F);
                var r = I(e),
                    i = r.byteLength,
                    a = h(t);
                if (a < 0 || a > i) throw new H("Wrong offset");
                if (n = void 0 === n ? i - a : p(n), a + n > i) throw new H(A);
                M(this, {
                    type: j,
                    buffer: e,
                    byteLength: n,
                    byteOffset: a,
                    bytes: r.bytes
                }), o || (this.buffer = e, this.byteLength = n, this.byteOffset = a)
            }, B = D[$], o && (ee(R, "byteLength", I), ee(D, "buffer", N), ee(D, "byteLength", N), ee(D, "byteOffset", N)), l(B, {
                getInt8: function(e) {
                    return te(this, 1, e)[0] << 24 >> 24
                },
                getUint8: function(e) {
                    return te(this, 1, e)[0]
                },
                getInt16: function(e) {
                    var t = te(this, 2, e, arguments.length > 1 && arguments[1]);
                    return (t[1] << 8 | t[0]) << 16 >> 16
                },
                getUint16: function(e) {
                    var t = te(this, 2, e, arguments.length > 1 && arguments[1]);
                    return t[1] << 8 | t[0]
                },
                getInt32: function(e) {
                    return J(te(this, 4, e, arguments.length > 1 && arguments[1]))
                },
                getUint32: function(e) {
                    return J(te(this, 4, e, arguments.length > 1 && arguments[1])) >>> 0
                },
                getFloat32: function(e) {
                    return X(te(this, 4, e, arguments.length > 1 && arguments[1]), 23)
                },
                getFloat64: function(e) {
                    return X(te(this, 8, e, arguments.length > 1 && arguments[1]), 52)
                },
                setInt8: function(e, t) {
                    ne(this, 1, e, G, t)
                },
                setUint8: function(e, t) {
                    ne(this, 1, e, G, t)
                },
                setInt16: function(e, t) {
                    ne(this, 2, e, Y, t, arguments.length > 2 && arguments[2])
                },
                setUint16: function(e, t) {
                    ne(this, 2, e, Y, t, arguments.length > 2 && arguments[2])
                },
                setInt32: function(e, t) {
                    ne(this, 4, e, K, t, arguments.length > 2 && arguments[2])
                },
                setUint32: function(e, t) {
                    ne(this, 4, e, K, t, arguments.length > 2 && arguments[2])
                },
                setFloat32: function(e, t) {
                    ne(this, 4, e, Z, t, arguments.length > 2 && arguments[2])
                },
                setFloat64: function(e, t) {
                    ne(this, 8, e, Q, t, arguments.length > 2 && arguments[2])
                }
            });
            S(R, T), S(D, j), e.exports = {
                ArrayBuffer: R,
                DataView: D
            }
        
