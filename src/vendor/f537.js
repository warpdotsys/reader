// chunk-vendors webpack 模块 f537（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            var r, i;
            (function(n, o) {
                function a(e) {
                    return e.default || e
                }
                r = [], i = function() {
                    var e = {};
                    return o(e), a(e)
                }.apply(t, r), void 0 === i || (e.exports = i)
            })("undefined" !== typeof globalThis ? globalThis : "undefined" !== typeof self && self, (function(e) {
                "use strict";
                Object.defineProperty(e, "__esModule", {
                    value: !0
                }), e.default = void 0;
                /**
                 * @license
                 * Copyright 2009 The Closure Library Authors
                 * Copyright 2020 Daniel Wirtz / The long.js Authors.
                 *
                 * Licensed under the Apache License, Version 2.0 (the "License");
                 * you may not use this file except in compliance with the License.
                 * You may obtain a copy of the License at
                 *
                 *     http://www.apache.org/licenses/LICENSE-2.0
                 *
                 * Unless required by applicable law or agreed to in writing, software
                 * distributed under the License is distributed on an "AS IS" BASIS,
                 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
                 * See the License for the specific language governing permissions and
                 * limitations under the License.
                 *
                 * SPDX-License-Identifier: Apache-2.0
                 */
                var t = null;
                try {
                    t = new WebAssembly.Instance(new WebAssembly.Module(new Uint8Array([0, 97, 115, 109, 1, 0, 0, 0, 1, 13, 2, 96, 0, 1, 127, 96, 4, 127, 127, 127, 127, 1, 127, 3, 7, 6, 0, 1, 1, 1, 1, 1, 6, 6, 1, 127, 1, 65, 0, 11, 7, 50, 6, 3, 109, 117, 108, 0, 1, 5, 100, 105, 118, 95, 115, 0, 2, 5, 100, 105, 118, 95, 117, 0, 3, 5, 114, 101, 109, 95, 115, 0, 4, 5, 114, 101, 109, 95, 117, 0, 5, 8, 103, 101, 116, 95, 104, 105, 103, 104, 0, 0, 10, 191, 1, 6, 4, 0, 35, 0, 11, 36, 1, 1, 126, 32, 0, 173, 32, 1, 173, 66, 32, 134, 132, 32, 2, 173, 32, 3, 173, 66, 32, 134, 132, 126, 34, 4, 66, 32, 135, 167, 36, 0, 32, 4, 167, 11, 36, 1, 1, 126, 32, 0, 173, 32, 1, 173, 66, 32, 134, 132, 32, 2, 173, 32, 3, 173, 66, 32, 134, 132, 127, 34, 4, 66, 32, 135, 167, 36, 0, 32, 4, 167, 11, 36, 1, 1, 126, 32, 0, 173, 32, 1, 173, 66, 32, 134, 132, 32, 2, 173, 32, 3, 173, 66, 32, 134, 132, 128, 34, 4, 66, 32, 135, 167, 36, 0, 32, 4, 167, 11, 36, 1, 1, 126, 32, 0, 173, 32, 1, 173, 66, 32, 134, 132, 32, 2, 173, 32, 3, 173, 66, 32, 134, 132, 129, 34, 4, 66, 32, 135, 167, 36, 0, 32, 4, 167, 11, 36, 1, 1, 126, 32, 0, 173, 32, 1, 173, 66, 32, 134, 132, 32, 2, 173, 32, 3, 173, 66, 32, 134, 132, 130, 34, 4, 66, 32, 135, 167, 36, 0, 32, 4, 167, 11])), {}).exports
                } catch {}

                function n(e, t, n) {
                    this.low = 0 | e, this.high = 0 | t, this.unsigned = !!n
                }

                function r(e) {
                    return !0 === (e && e["__isLong__"])
                }

                function i(e) {
                    var t = Math.clz32(e & -e);
                    return e ? 31 - t : t
                }
                n.prototype.__isLong__, Object.defineProperty(n.prototype, "__isLong__", {
                    value: !0
                }), n.isLong = r;
                var o = {},
                    a = {};

                function s(e, t) {
                    var n, r, i;
                    return t ? (e >>>= 0, (i = 0 <= e && e < 256) && (r = a[e], r) ? r : (n = c(e, 0, !0), i && (a[e] = n), n)) : (e |= 0, (i = -128 <= e && e < 128) && (r = o[e], r) ? r : (n = c(e, e < 0 ? -1 : 0, !1), i && (o[e] = n), n))
                }

                function u(e, t) {
                    if (isNaN(e)) return t ? _ : y;
                    if (t) {
                        if (e < 0) return _;
                        if (e >= m) return O
                    } else {
                        if (e <= -g) return E;
                        if (e + 1 >= g) return S
                    }
                    return e < 0 ? u(-e, t).neg() : c(e % v | 0, e / v | 0, t)
                }

                function c(e, t, r) {
                    return new n(e, t, r)
                }
                n.fromInt = s, n.fromNumber = u, n.fromBits = c;
                var l = Math.pow;

                function f(e, t, n) {
                    if (0 === e.length) throw Error("empty string");
                    if ("number" === typeof t ? (n = t, t = !1) : t = !!t, "NaN" === e || "Infinity" === e || "+Infinity" === e || "-Infinity" === e) return t ? _ : y;
                    if (n = n || 10, n < 2 || 36 < n) throw RangeError("radix");
                    var r;
                    if ((r = e.indexOf("-")) > 0) throw Error("interior hyphen");
                    if (0 === r) return f(e.substring(1), t, n).neg();
                    for (var i = u(l(n, 8)), o = y, a = 0; a < e.length; a += 8) {
                        var s = Math.min(8, e.length - a),
                            c = parseInt(e.substring(a, a + s), n);
                        if (s < 8) {
                            var d = u(l(n, s));
                            o = o.mul(d).add(u(c))
                        } else o = o.mul(i), o = o.add(u(c))
                    }
                    return o.unsigned = t, o
                }

                function d(e, t) {
                    return "number" === typeof e ? u(e, t) : "string" === typeof e ? f(e, t) : c(e.low, e.high, "boolean" === typeof t ? t : e.unsigned)
                }
                n.fromString = f, n.fromValue = d;
                var h = 65536,
                    p = 1 << 24,
                    v = h * h,
                    m = v * v,
                    g = m / 2,
                    b = s(p),
                    y = s(0);
                n.ZERO = y;
                var _ = s(0, !0);
                n.UZERO = _;
                var w = s(1);
                n.ONE = w;
                var x = s(1, !0);
                n.UONE = x;
                var C = s(-1);
                n.NEG_ONE = C;
                var S = c(-1, 2147483647, !1);
                n.MAX_VALUE = S;
                var O = c(-1, -1, !0);
                n.MAX_UNSIGNED_VALUE = O;
                var E = c(0, -2147483648, !1);
                n.MIN_VALUE = E;
                var k = n.prototype;
                k.toInt = function() {
                    return this.unsigned ? this.low >>> 0 : this.low
                }, k.toNumber = function() {
                    return this.unsigned ? (this.high >>> 0) * v + (this.low >>> 0) : this.high * v + (this.low >>> 0)
                }, k.toString = function(e) {
                    if (e = e || 10, e < 2 || 36 < e) throw RangeError("radix");
                    if (this.isZero()) return "0";
                    if (this.isNegative()) {
                        if (this.eq(E)) {
                            var t = u(e),
                                n = this.div(t),
                                r = n.mul(t).sub(this);
                            return n.toString(e) + r.toInt().toString(e)
                        }
                        return "-" + this.neg().toString(e)
                    }
                    var i = u(l(e, 6), this.unsigned),
                        o = this,
                        a = "";
                    while (1) {
                        var s = o.div(i),
                            c = o.sub(s.mul(i)).toInt() >>> 0,
                            f = c.toString(e);
                        if (o = s, o.isZero()) return f + a;
                        while (f.length < 6) f = "0" + f;
                        a = "" + f + a
                    }
                }, k.getHighBits = function() {
                    return this.high
                }, k.getHighBitsUnsigned = function() {
                    return this.high >>> 0
                }, k.getLowBits = function() {
                    return this.low
                }, k.getLowBitsUnsigned = function() {
                    return this.low >>> 0
                }, k.getNumBitsAbs = function() {
                    if (this.isNegative()) return this.eq(E) ? 64 : this.neg().getNumBitsAbs();
                    for (var e = 0 != this.high ? this.high : this.low, t = 31; t > 0; t--)
                        if (0 != (e & 1 << t)) break;
                    return 0 != this.high ? t + 33 : t + 1
                }, k.isSafeInteger = function() {
                    var e = this.high >> 21;
                    return !e || !this.unsigned && (-1 === e && !(0 === this.low && -2097152 === this.high))
                }, k.isZero = function() {
                    return 0 === this.high && 0 === this.low
                }, k.eqz = k.isZero, k.isNegative = function() {
                    return !this.unsigned && this.high < 0
                }, k.isPositive = function() {
                    return this.unsigned || this.high >= 0
                }, k.isOdd = function() {
                    return 1 === (1 & this.low)
                }, k.isEven = function() {
                    return 0 === (1 & this.low)
                }, k.equals = function(e) {
                    return r(e) || (e = d(e)), (this.unsigned === e.unsigned || this.high >>> 31 !== 1 || e.high >>> 31 !== 1) && (this.high === e.high && this.low === e.low)
                }, k.eq = k.equals, k.notEquals = function(e) {
                    return !this.eq(e)
                }, k.neq = k.notEquals, k.ne = k.notEquals, k.lessThan = function(e) {
                    return this.comp(e) < 0
                }, k.lt = k.lessThan, k.lessThanOrEqual = function(e) {
                    return this.comp(e) <= 0
                }, k.lte = k.lessThanOrEqual, k.le = k.lessThanOrEqual, k.greaterThan = function(e) {
                    return this.comp(e) > 0
                }, k.gt = k.greaterThan, k.greaterThanOrEqual = function(e) {
                    return this.comp(e) >= 0
                }, k.gte = k.greaterThanOrEqual, k.ge = k.greaterThanOrEqual, k.compare = function(e) {
                    if (r(e) || (e = d(e)), this.eq(e)) return 0;
                    var t = this.isNegative(),
                        n = e.isNegative();
                    return t && !n ? -1 : !t && n ? 1 : this.unsigned ? e.high >>> 0 > this.high >>> 0 || e.high === this.high && e.low >>> 0 > this.low >>> 0 ? -1 : 1 : this.sub(e).isNegative() ? -1 : 1
                }, k.comp = k.compare, k.negate = function() {
                    return !this.unsigned && this.eq(E) ? E : this.not().add(w)
                }, k.neg = k.negate, k.add = function(e) {
                    r(e) || (e = d(e));
                    var t = this.high >>> 16,
                        n = 65535 & this.high,
                        i = this.low >>> 16,
                        o = 65535 & this.low,
                        a = e.high >>> 16,
                        s = 65535 & e.high,
                        u = e.low >>> 16,
                        l = 65535 & e.low,
                        f = 0,
                        h = 0,
                        p = 0,
                        v = 0;
                    return v += o + l, p += v >>> 16, v &= 65535, p += i + u, h += p >>> 16, p &= 65535, h += n + s, f += h >>> 16, h &= 65535, f += t + a, f &= 65535, c(p << 16 | v, f << 16 | h, this.unsigned)
                }, k.subtract = function(e) {
                    return r(e) || (e = d(e)), this.add(e.neg())
                }, k.sub = k.subtract, k.multiply = function(e) {
                    if (this.isZero()) return this;
                    if (r(e) || (e = d(e)), t) {
                        var n = t["mul"](this.low, this.high, e.low, e.high);
                        return c(n, t["get_high"](), this.unsigned)
                    }
                    if (e.isZero()) return this.unsigned ? _ : y;
                    if (this.eq(E)) return e.isOdd() ? E : y;
                    if (e.eq(E)) return this.isOdd() ? E : y;
                    if (this.isNegative()) return e.isNegative() ? this.neg().mul(e.neg()) : this.neg().mul(e).neg();
                    if (e.isNegative()) return this.mul(e.neg()).neg();
                    if (this.lt(b) && e.lt(b)) return u(this.toNumber() * e.toNumber(), this.unsigned);
                    var i = this.high >>> 16,
                        o = 65535 & this.high,
                        a = this.low >>> 16,
                        s = 65535 & this.low,
                        l = e.high >>> 16,
                        f = 65535 & e.high,
                        h = e.low >>> 16,
                        p = 65535 & e.low,
                        v = 0,
                        m = 0,
                        g = 0,
                        w = 0;
                    return w += s * p, g += w >>> 16, w &= 65535, g += a * p, m += g >>> 16, g &= 65535, g += s * h, m += g >>> 16, g &= 65535, m += o * p, v += m >>> 16, m &= 65535, m += a * h, v += m >>> 16, m &= 65535, m += s * f, v += m >>> 16, m &= 65535, v += i * p + o * h + a * f + s * l, v &= 65535, c(g << 16 | w, v << 16 | m, this.unsigned)
                }, k.mul = k.multiply, k.divide = function(e) {
                    if (r(e) || (e = d(e)), e.isZero()) throw Error("division by zero");
                    if (t) {
                        if (!this.unsigned && -2147483648 === this.high && -1 === e.low && -1 === e.high) return this;
                        var n = (this.unsigned ? t["div_u"] : t["div_s"])(this.low, this.high, e.low, e.high);
                        return c(n, t["get_high"](), this.unsigned)
                    }
                    if (this.isZero()) return this.unsigned ? _ : y;
                    var i, o, a;
                    if (this.unsigned) {
                        if (e.unsigned || (e = e.toUnsigned()), e.gt(this)) return _;
                        if (e.gt(this.shru(1))) return x;
                        a = _
                    } else {
                        if (this.eq(E)) {
                            if (e.eq(w) || e.eq(C)) return E;
                            if (e.eq(E)) return w;
                            var s = this.shr(1);
                            return i = s.div(e).shl(1), i.eq(y) ? e.isNegative() ? w : C : (o = this.sub(e.mul(i)), a = i.add(o.div(e)), a)
                        }
                        if (e.eq(E)) return this.unsigned ? _ : y;
                        if (this.isNegative()) return e.isNegative() ? this.neg().div(e.neg()) : this.neg().div(e).neg();
                        if (e.isNegative()) return this.div(e.neg()).neg();
                        a = y
                    }
                    o = this;
                    while (o.gte(e)) {
                        i = Math.max(1, Math.floor(o.toNumber() / e.toNumber()));
                        var f = Math.ceil(Math.log(i) / Math.LN2),
                            h = f <= 48 ? 1 : l(2, f - 48),
                            p = u(i),
                            v = p.mul(e);
                        while (v.isNegative() || v.gt(o)) i -= h, p = u(i, this.unsigned), v = p.mul(e);
                        p.isZero() && (p = w), a = a.add(p), o = o.sub(v)
                    }
                    return a
                }, k.div = k.divide, k.modulo = function(e) {
                    if (r(e) || (e = d(e)), t) {
                        var n = (this.unsigned ? t["rem_u"] : t["rem_s"])(this.low, this.high, e.low, e.high);
                        return c(n, t["get_high"](), this.unsigned)
                    }
                    return this.sub(this.div(e).mul(e))
                }, k.mod = k.modulo, k.rem = k.modulo, k.not = function() {
                    return c(~this.low, ~this.high, this.unsigned)
                }, k.countLeadingZeros = function() {
                    return this.high ? Math.clz32(this.high) : Math.clz32(this.low) + 32
                }, k.clz = k.countLeadingZeros, k.countTrailingZeros = function() {
                    return this.low ? i(this.low) : i(this.high) + 32
                }, k.ctz = k.countTrailingZeros, k.and = function(e) {
                    return r(e) || (e = d(e)), c(this.low & e.low, this.high & e.high, this.unsigned)
                }, k.or = function(e) {
                    return r(e) || (e = d(e)), c(this.low | e.low, this.high | e.high, this.unsigned)
                }, k.xor = function(e) {
                    return r(e) || (e = d(e)), c(this.low ^ e.low, this.high ^ e.high, this.unsigned)
                }, k.shiftLeft = function(e) {
                    return r(e) && (e = e.toInt()), 0 === (e &= 63) ? this : e < 32 ? c(this.low << e, this.high << e | this.low >>> 32 - e, this.unsigned) : c(0, this.low << e - 32, this.unsigned)
                }, k.shl = k.shiftLeft, k.shiftRight = function(e) {
                    return r(e) && (e = e.toInt()), 0 === (e &= 63) ? this : e < 32 ? c(this.low >>> e | this.high << 32 - e, this.high >> e, this.unsigned) : c(this.high >> e - 32, this.high >= 0 ? 0 : -1, this.unsigned)
                }, k.shr = k.shiftRight, k.shiftRightUnsigned = function(e) {
                    return r(e) && (e = e.toInt()), 0 === (e &= 63) ? this : e < 32 ? c(this.low >>> e | this.high << 32 - e, this.high >>> e, this.unsigned) : c(32 === e ? this.high : this.high >>> e - 32, 0, this.unsigned)
                }, k.shru = k.shiftRightUnsigned, k.shr_u = k.shiftRightUnsigned, k.rotateLeft = function(e) {
                    var t;
                    return r(e) && (e = e.toInt()), 0 === (e &= 63) ? this : 32 === e ? c(this.high, this.low, this.unsigned) : e < 32 ? (t = 32 - e, c(this.low << e | this.high >>> t, this.high << e | this.low >>> t, this.unsigned)) : (e -= 32, t = 32 - e, c(this.high << e | this.low >>> t, this.low << e | this.high >>> t, this.unsigned))
                }, k.rotl = k.rotateLeft, k.rotateRight = function(e) {
                    var t;
                    return r(e) && (e = e.toInt()), 0 === (e &= 63) ? this : 32 === e ? c(this.high, this.low, this.unsigned) : e < 32 ? (t = 32 - e, c(this.high << t | this.low >>> e, this.low << t | this.high >>> e, this.unsigned)) : (e -= 32, t = 32 - e, c(this.low << t | this.high >>> e, this.high << t | this.low >>> e, this.unsigned))
                }, k.rotr = k.rotateRight, k.toSigned = function() {
                    return this.unsigned ? c(this.low, this.high, !1) : this
                }, k.toUnsigned = function() {
                    return this.unsigned ? this : c(this.low, this.high, !0)
                }, k.toBytes = function(e) {
                    return e ? this.toBytesLE() : this.toBytesBE()
                }, k.toBytesLE = function() {
                    var e = this.high,
                        t = this.low;
                    return [255 & t, t >>> 8 & 255, t >>> 16 & 255, t >>> 24, 255 & e, e >>> 8 & 255, e >>> 16 & 255, e >>> 24]
                }, k.toBytesBE = function() {
                    var e = this.high,
                        t = this.low;
                    return [e >>> 24, e >>> 16 & 255, e >>> 8 & 255, 255 & e, t >>> 24, t >>> 16 & 255, t >>> 8 & 255, 255 & t]
                }, n.fromBytes = function(e, t, r) {
                    return r ? n.fromBytesLE(e, t) : n.fromBytesBE(e, t)
                }, n.fromBytesLE = function(e, t) {
                    return new n(e[0] | e[1] << 8 | e[2] << 16 | e[3] << 24, e[4] | e[5] << 8 | e[6] << 16 | e[7] << 24, t)
                }, n.fromBytesBE = function(e, t) {
                    return new n(e[4] << 24 | e[5] << 16 | e[6] << 8 | e[7], e[0] << 24 | e[1] << 16 | e[2] << 8 | e[3], t)
                }, "function" === typeof BigInt && (n.fromBigInt = function(e, t) {
                    var n = Number(BigInt.asIntN(32, e)),
                        r = Number(BigInt.asIntN(32, e >> BigInt(32)));
                    return c(n, r, t)
                }, n.fromValue = function(e, t) {
                    return "bigint" === typeof e ? n.fromBigInt(e, t) : d(e, t)
                }, k.toBigInt = function() {
                    var e = BigInt(this.low >>> 0),
                        t = BigInt(this.unsigned ? this.high >>> 0 : this.high);
                    return t << BigInt(32) | e
                });
                e.default = n
            }))
        
