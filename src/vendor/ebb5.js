// chunk-vendors webpack 模块 ebb5（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r, i, o, a = n("./4b11.js"),
                s = n("./83ab.js"),
                u = n("./cfe9.js"),
                c = n("./1626.js"),
                l = n("./861d.js"),
                f = n("./1a2d.js"),
                d = n("./f5df.js"),
                h = n("./0d51.js"),
                p = n("./9112.js"),
                v = n("./cb2d.js"),
                m = n("./edd0.js"),
                g = n("./3a9b.js"),
                b = n("./e163.js"),
                y = n("./d2bb.js"),
                _ = n("./b622.js"),
                w = n("./90e3.js"),
                x = n("./69f3.js"),
                C = x.enforce,
                S = x.get,
                O = u.Int8Array,
                E = O && O.prototype,
                k = u.Uint8ClampedArray,
                T = k && k.prototype,
                j = O && b(O),
                $ = E && b(E),
                A = Object.prototype,
                P = u.TypeError,
                I = _("toStringTag"),
                N = w("TYPED_ARRAY_TAG"),
                M = "TypedArrayConstructor",
                L = a && !!y && "Opera" !== d(u.opera),
                R = !1,
                F = {
                    Int8Array: 1,
                    Uint8Array: 1,
                    Uint8ClampedArray: 1,
                    Int16Array: 2,
                    Uint16Array: 2,
                    Int32Array: 4,
                    Uint32Array: 4,
                    Float32Array: 4,
                    Float64Array: 8
                },
                D = {
                    BigInt64Array: 8,
                    BigUint64Array: 8
                },
                B = function(e) {
                    if (!l(e)) return !1;
                    var t = d(e);
                    return "DataView" === t || f(F, t) || f(D, t)
                },
                z = function(e) {
                    var t = b(e);
                    if (l(t)) {
                        var n = S(t);
                        return n && f(n, M) ? n[M] : z(t)
                    }
                },
                V = function(e) {
                    if (!l(e)) return !1;
                    var t = d(e);
                    return f(F, t) || f(D, t)
                },
                H = function(e) {
                    if (V(e)) return e;
                    throw new P("Target is not a typed array")
                },
                W = function(e) {
                    if (c(e) && (!y || g(j, e))) return e;
                    throw new P(h(e) + " is not a typed array constructor")
                },
                q = function(e, t, n, r) {
                    if (s) {
                        if (n)
                            for (var i in F) {
                                var o = u[i];
                                if (o && f(o.prototype, e)) try {
                                    delete o.prototype[e]
                                } catch (a) {
                                    try {
                                        o.prototype[e] = t
                                    } catch (c) {}
                                }
                            }
                        $[e] && !n || v($, e, n ? t : L && E[e] || t, r)
                    }
                },
                U = function(e, t, n) {
                    var r, i;
                    if (s) {
                        if (y) {
                            if (n)
                                for (r in F)
                                    if (i = u[r], i && f(i, e)) try {
                                        delete i[e]
                                    } catch (o) {}
                            if (j[e] && !n) return;
                            try {
                                return v(j, e, n ? t : L && j[e] || t)
                            } catch (o) {}
                        }
                        for (r in F) i = u[r], !i || i[e] && !n || v(i, e, t)
                    }
                };
            for (r in F) i = u[r], o = i && i.prototype, o ? C(o)[M] = i : L = !1;
            for (r in D) i = u[r], o = i && i.prototype, o && (C(o)[M] = i);
            if ((!L || !c(j) || j === Function.prototype) && (j = function() {
                    throw new P("Incorrect invocation")
                }, L))
                for (r in F) u[r] && y(u[r], j);
            if ((!L || !$ || $ === A) && ($ = j.prototype, L))
                for (r in F) u[r] && y(u[r].prototype, $);
            if (L && b(T) !== $ && y(T, $), s && !f($, I))
                for (r in R = !0, m($, I, {
                        configurable: !0,
                        get: function() {
                            return l(this) ? this[N] : void 0
                        }
                    }), F) u[r] && p(u[r], N, r);
            e.exports = {
                NATIVE_ARRAY_BUFFER_VIEWS: L,
                TYPED_ARRAY_TAG: R && N,
                aTypedArray: H,
                aTypedArrayConstructor: W,
                exportTypedArrayMethod: q,
                exportTypedArrayStaticMethod: U,
                getTypedArrayConstructor: z,
                isView: B,
                isTypedArray: V,
                TypedArray: j,
                TypedArrayPrototype: $
            }
        
