// 恢复自 reader-pro-3.2.14 前端产物 beauty/reader.js 的 webpack 模块 b639（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = require;

            "use strict";
            (function(e) {
                /*!
                 * The buffer module from node.js, for the browser.
                 *
                 * @author   Feross Aboukhadijeh <http://feross.org>
                 * @license  MIT
                 */
                var a = n("1fb5"),
                    u = n("9152"),
                    o = n("2335");

                function r() {
                    try {
                        var e = new Uint8Array(1);
                        return e.__proto__ = {
                            __proto__: Uint8Array.prototype,
                            foo: function() {
                                return 42
                            }
                        }, 42 === e.foo() && "function" === typeof e.subarray && 0 === e.subarray(1, 1).byteLength
                    } catch (t) {
                        return !1
                    }
                }

                function i() {
                    return l.TYPED_ARRAY_SUPPORT ? 2147483647 : 1073741823
                }

                function s(e, t) {
                    if (i() < t) throw new RangeError("Invalid typed array length");
                    return l.TYPED_ARRAY_SUPPORT ? (e = new Uint8Array(t), e.__proto__ = l.prototype) : (null === e && (e = new l(t)), e.length = t), e
                }

                function l(e, t, n) {
                    if (!l.TYPED_ARRAY_SUPPORT && !(this instanceof l)) return new l(e, t, n);
                    if ("number" === typeof e) {
                        if ("string" === typeof t) throw new Error("If encoding is specified then the first argument must be a string");
                        return D(this, e)
                    }
                    return c(this, e, t, n)
                }

                function c(e, t, n, a) {
                    if ("number" === typeof t) throw new TypeError('"value" argument must not be a number');
                    return "undefined" !== typeof ArrayBuffer && t instanceof ArrayBuffer ? F(e, t, n, a) : "string" === typeof t ? h(e, t, n) : f(e, t)
                }

                function p(e) {
                    if ("number" !== typeof e) throw new TypeError('"size" argument must be a number');
                    if (e < 0) throw new RangeError('"size" argument must not be negative')
                }

                function d(e, t, n, a) {
                    return p(t), t <= 0 ? s(e, t) : void 0 !== n ? "string" === typeof a ? s(e, t).fill(n, a) : s(e, t).fill(n) : s(e, t)
                }

                function D(e, t) {
                    if (p(t), e = s(e, t < 0 ? 0 : 0 | m(t)), !l.TYPED_ARRAY_SUPPORT)
                        for (var n = 0; n < t; ++n) e[n] = 0;
                    return e
                }

                function h(e, t, n) {
                    if ("string" === typeof n && "" !== n || (n = "utf8"), !l.isEncoding(n)) throw new TypeError('"encoding" must be a valid string encoding');
                    var a = 0 | E(t, n);
                    e = s(e, a);
                    var u = e.write(t, n);
                    return u !== a && (e = e.slice(0, u)), e
                }

                function A(e, t) {
                    var n = t.length < 0 ? 0 : 0 | m(t.length);
                    e = s(e, n);
                    for (var a = 0; a < n; a += 1) e[a] = 255 & t[a];
                    return e
                }

                function F(e, t, n, a) {
                    if (t.byteLength, n < 0 || t.byteLength < n) throw new RangeError("'offset' is out of bounds");
                    if (t.byteLength < n + (a || 0)) throw new RangeError("'length' is out of bounds");
                    return t = void 0 === n && void 0 === a ? new Uint8Array(t) : void 0 === a ? new Uint8Array(t, n) : new Uint8Array(t, n, a), l.TYPED_ARRAY_SUPPORT ? (e = t, e.__proto__ = l.prototype) : e = A(e, t), e
                }

                function f(e, t) {
                    if (l.isBuffer(t)) {
                        var n = 0 | m(t.length);
                        return e = s(e, n), 0 === e.length ? e : (t.copy(e, 0, 0, n), e)
                    }
                    if (t) {
                        if ("undefined" !== typeof ArrayBuffer && t.buffer instanceof ArrayBuffer || "length" in t) return "number" !== typeof t.length || te(t.length) ? s(e, 0) : A(e, t);
                        if ("Buffer" === t.type && o(t.data)) return A(e, t.data)
                    }
                    throw new TypeError("First argument must be a string, Buffer, ArrayBuffer, Array, or array-like object.")
                }

                function m(e) {
                    if (e >= i()) throw new RangeError("Attempt to allocate Buffer larger than maximum size: 0x" + i().toString(16) + " bytes");
                    return 0 | e
                }

                function C(e) {
                    return +e != e && (e = 0), l.alloc(+e)
                }

                function E(e, t) {
                    if (l.isBuffer(e)) return e.length;
                    if ("undefined" !== typeof ArrayBuffer && "function" === typeof ArrayBuffer.isView && (ArrayBuffer.isView(e) || e instanceof ArrayBuffer)) return e.byteLength;
                    "string" !== typeof e && (e = "" + e);
                    var n = e.length;
                    if (0 === n) return 0;
                    for (var a = !1;;) switch (t) {
                        case "ascii":
                        case "latin1":
                        case "binary":
                            return n;
                        case "utf8":
                        case "utf-8":
                        case void 0:
                            return G(e).length;
                        case "ucs2":
                        case "ucs-2":
                        case "utf16le":
                        case "utf-16le":
                            return 2 * n;
                        case "hex":
                            return n >>> 1;
                        case "base64":
                            return J(e).length;
                        default:
                            if (a) return G(e).length;
                            t = ("" + t).toLowerCase(), a = !0
                    }
                }

                function y(e, t, n) {
                    var a = !1;
                    if ((void 0 === t || t < 0) && (t = 0), t > this.length) return "";
                    if ((void 0 === n || n > this.length) && (n = this.length), n <= 0) return "";
                    if (n >>>= 0, t >>>= 0, n <= t) return "";
                    e || (e = "utf8");
                    while (1) switch (e) {
                        case "hex":
                            return M(this, t, n);
                        case "utf8":
                        case "utf-8":
                            return L(this, t, n);
                        case "ascii":
                            return P(this, t, n);
                        case "latin1":
                        case "binary":
                            return $(this, t, n);
                        case "base64":
                            return T(this, t, n);
                        case "ucs2":
                        case "ucs-2":
                        case "utf16le":
                        case "utf-16le":
                            return O(this, t, n);
                        default:
                            if (a) throw new TypeError("Unknown encoding: " + e);
                            e = (e + "").toLowerCase(), a = !0
                    }
                }

                function g(e, t, n) {
                    var a = e[t];
                    e[t] = e[n], e[n] = a
                }

                function b(e, t, n, a, u) {
                    if (0 === e.length) return -1;
                    if ("string" === typeof n ? (a = n, n = 0) : n > 2147483647 ? n = 2147483647 : n < -2147483648 && (n = -2147483648), n = +n, isNaN(n) && (n = u ? 0 : e.length - 1), n < 0 && (n = e.length + n), n >= e.length) {
                        if (u) return -1;
                        n = e.length - 1
                    } else if (n < 0) {
                        if (!u) return -1;
                        n = 0
                    }
                    if ("string" === typeof t && (t = l.from(t, a)), l.isBuffer(t)) return 0 === t.length ? -1 : v(e, t, n, a, u);
                    if ("number" === typeof t) return t &= 255, l.TYPED_ARRAY_SUPPORT && "function" === typeof Uint8Array.prototype.indexOf ? u ? Uint8Array.prototype.indexOf.call(e, t, n) : Uint8Array.prototype.lastIndexOf.call(e, t, n) : v(e, [t], n, a, u);
                    throw new TypeError("val must be string, number or Buffer")
                }

                function v(e, t, n, a, u) {
                    var o, r = 1,
                        i = e.length,
                        s = t.length;
                    if (void 0 !== a && (a = String(a).toLowerCase(), "ucs2" === a || "ucs-2" === a || "utf16le" === a || "utf-16le" === a)) {
                        if (e.length < 2 || t.length < 2) return -1;
                        r = 2, i /= 2, s /= 2, n /= 2
                    }

                    function l(e, t) {
                        return 1 === r ? e[t] : e.readUInt16BE(t * r)
                    }
                    if (u) {
                        var c = -1;
                        for (o = n; o < i; o++)
                            if (l(e, o) === l(t, -1 === c ? 0 : o - c)) {
                                if (-1 === c && (c = o), o - c + 1 === s) return c * r
                            } else - 1 !== c && (o -= o - c), c = -1
                    } else
                        for (n + s > i && (n = i - s), o = n; o >= 0; o--) {
                            for (var p = !0, d = 0; d < s; d++)
                                if (l(e, o + d) !== l(t, d)) {
                                    p = !1;
                                    break
                                } if (p) return o
                        }
                    return -1
                }

                function B(e, t, n, a) {
                    n = Number(n) || 0;
                    var u = e.length - n;
                    a ? (a = Number(a), a > u && (a = u)) : a = u;
                    var o = t.length;
                    if (o % 2 !== 0) throw new TypeError("Invalid hex string");
                    a > o / 2 && (a = o / 2);
                    for (var r = 0; r < a; ++r) {
                        var i = parseInt(t.substr(2 * r, 2), 16);
                        if (isNaN(i)) return r;
                        e[n + r] = i
                    }
                    return r
                }

                function k(e, t, n, a) {
                    return ee(G(t, e.length - n), e, n, a)
                }

                function w(e, t, n, a) {
                    return ee(X(t), e, n, a)
                }

                function x(e, t, n, a) {
                    return w(e, t, n, a)
                }

                function S(e, t, n, a) {
                    return ee(J(t), e, n, a)
                }

                function N(e, t, n, a) {
                    return ee(Z(t, e.length - n), e, n, a)
                }

                function T(e, t, n) {
                    return 0 === t && n === e.length ? a.fromByteArray(e) : a.fromByteArray(e.slice(t, n))
                }

                function L(e, t, n) {
                    n = Math.min(e.length, n);
                    var a = [],
                        u = t;
                    while (u < n) {
                        var o, r, i, s, l = e[u],
                            c = null,
                            p = l > 239 ? 4 : l > 223 ? 3 : l > 191 ? 2 : 1;
                        if (u + p <= n) switch (p) {
                            case 1:
                                l < 128 && (c = l);
                                break;
                            case 2:
                                o = e[u + 1], 128 === (192 & o) && (s = (31 & l) << 6 | 63 & o, s > 127 && (c = s));
                                break;
                            case 3:
                                o = e[u + 1], r = e[u + 2], 128 === (192 & o) && 128 === (192 & r) && (s = (15 & l) << 12 | (63 & o) << 6 | 63 & r, s > 2047 && (s < 55296 || s > 57343) && (c = s));
                                break;
                            case 4:
                                o = e[u + 1], r = e[u + 2], i = e[u + 3], 128 === (192 & o) && 128 === (192 & r) && 128 === (192 & i) && (s = (15 & l) << 18 | (63 & o) << 12 | (63 & r) << 6 | 63 & i, s > 65535 && s < 1114112 && (c = s))
                        }
                        null === c ? (c = 65533, p = 1) : c > 65535 && (c -= 65536, a.push(c >>> 10 & 1023 | 55296), c = 56320 | 1023 & c), a.push(c), u += p
                    }
                    return R(a)
                }
                t.Buffer = l, t.SlowBuffer = C, t.INSPECT_MAX_BYTES = 50, l.TYPED_ARRAY_SUPPORT = void 0 !== e.TYPED_ARRAY_SUPPORT ? e.TYPED_ARRAY_SUPPORT : r(), t.kMaxLength = i(), l.poolSize = 8192, l._augment = function(e) {
                    return e.__proto__ = l.prototype, e
                }, l.from = function(e, t, n) {
                    return c(null, e, t, n)
                }, l.TYPED_ARRAY_SUPPORT && (l.prototype.__proto__ = Uint8Array.prototype, l.__proto__ = Uint8Array, "undefined" !== typeof Symbol && Symbol.species && l[Symbol.species] === l && Object.defineProperty(l, Symbol.species, {
                    value: null,
                    configurable: !0
                })), l.alloc = function(e, t, n) {
                    return d(null, e, t, n)
                }, l.allocUnsafe = function(e) {
                    return D(null, e)
                }, l.allocUnsafeSlow = function(e) {
                    return D(null, e)
                }, l.isBuffer = function(e) {
                    return !(null == e || !e._isBuffer)
                }, l.compare = function(e, t) {
                    if (!l.isBuffer(e) || !l.isBuffer(t)) throw new TypeError("Arguments must be Buffers");
                    if (e === t) return 0;
                    for (var n = e.length, a = t.length, u = 0, o = Math.min(n, a); u < o; ++u)
                        if (e[u] !== t[u]) {
                            n = e[u], a = t[u];
                            break
                        } return n < a ? -1 : a < n ? 1 : 0
                }, l.isEncoding = function(e) {
                    switch (String(e).toLowerCase()) {
                        case "hex":
                        case "utf8":
                        case "utf-8":
                        case "ascii":
                        case "latin1":
                        case "binary":
                        case "base64":
                        case "ucs2":
                        case "ucs-2":
                        case "utf16le":
                        case "utf-16le":
                            return !0;
                        default:
                            return !1
                    }
                }, l.concat = function(e, t) {
                    if (!o(e)) throw new TypeError('"list" argument must be an Array of Buffers');
                    if (0 === e.length) return l.alloc(0);
                    var n;
                    if (void 0 === t)
                        for (t = 0, n = 0; n < e.length; ++n) t += e[n].length;
                    var a = l.allocUnsafe(t),
                        u = 0;
                    for (n = 0; n < e.length; ++n) {
                        var r = e[n];
                        if (!l.isBuffer(r)) throw new TypeError('"list" argument must be an Array of Buffers');
                        r.copy(a, u), u += r.length
                    }
                    return a
                }, l.byteLength = E, l.prototype._isBuffer = !0, l.prototype.swap16 = function() {
                    var e = this.length;
                    if (e % 2 !== 0) throw new RangeError("Buffer size must be a multiple of 16-bits");
                    for (var t = 0; t < e; t += 2) g(this, t, t + 1);
                    return this
                }, l.prototype.swap32 = function() {
                    var e = this.length;
                    if (e % 4 !== 0) throw new RangeError("Buffer size must be a multiple of 32-bits");
                    for (var t = 0; t < e; t += 4) g(this, t, t + 3), g(this, t + 1, t + 2);
                    return this
                }, l.prototype.swap64 = function() {
                    var e = this.length;
                    if (e % 8 !== 0) throw new RangeError("Buffer size must be a multiple of 64-bits");
                    for (var t = 0; t < e; t += 8) g(this, t, t + 7), g(this, t + 1, t + 6), g(this, t + 2, t + 5), g(this, t + 3, t + 4);
                    return this
                }, l.prototype.toString = function() {
                    var e = 0 | this.length;
                    return 0 === e ? "" : 0 === arguments.length ? L(this, 0, e) : y.apply(this, arguments)
                }, l.prototype.equals = function(e) {
                    if (!l.isBuffer(e)) throw new TypeError("Argument must be a Buffer");
                    return this === e || 0 === l.compare(this, e)
                }, l.prototype.inspect = function() {
                    var e = "",
                        n = t.INSPECT_MAX_BYTES;
                    return this.length > 0 && (e = this.toString("hex", 0, n).match(/.{2}/g).join(" "), this.length > n && (e += " ... ")), "<Buffer " + e + ">"
                }, l.prototype.compare = function(e, t, n, a, u) {
                    if (!l.isBuffer(e)) throw new TypeError("Argument must be a Buffer");
                    if (void 0 === t && (t = 0), void 0 === n && (n = e ? e.length : 0), void 0 === a && (a = 0), void 0 === u && (u = this.length), t < 0 || n > e.length || a < 0 || u > this.length) throw new RangeError("out of range index");
                    if (a >= u && t >= n) return 0;
                    if (a >= u) return -1;
                    if (t >= n) return 1;
                    if (t >>>= 0, n >>>= 0, a >>>= 0, u >>>= 0, this === e) return 0;
                    for (var o = u - a, r = n - t, i = Math.min(o, r), s = this.slice(a, u), c = e.slice(t, n), p = 0; p < i; ++p)
                        if (s[p] !== c[p]) {
                            o = s[p], r = c[p];
                            break
                        } return o < r ? -1 : r < o ? 1 : 0
                }, l.prototype.includes = function(e, t, n) {
                    return -1 !== this.indexOf(e, t, n)
                }, l.prototype.indexOf = function(e, t, n) {
                    return b(this, e, t, n, !0)
                }, l.prototype.lastIndexOf = function(e, t, n) {
                    return b(this, e, t, n, !1)
                }, l.prototype.write = function(e, t, n, a) {
                    if (void 0 === t) a = "utf8", n = this.length, t = 0;
                    else if (void 0 === n && "string" === typeof t) a = t, n = this.length, t = 0;
                    else {
                        if (!isFinite(t)) throw new Error("Buffer.write(string, encoding, offset[, length]) is no longer supported");
                        t |= 0, isFinite(n) ? (n |= 0, void 0 === a && (a = "utf8")) : (a = n, n = void 0)
                    }
                    var u = this.length - t;
                    if ((void 0 === n || n > u) && (n = u), e.length > 0 && (n < 0 || t < 0) || t > this.length) throw new RangeError("Attempt to write outside buffer bounds");
                    a || (a = "utf8");
                    for (var o = !1;;) switch (a) {
                        case "hex":
                            return B(this, e, t, n);
                        case "utf8":
                        case "utf-8":
                            return k(this, e, t, n);
                        case "ascii":
                            return w(this, e, t, n);
                        case "latin1":
                        case "binary":
                            return x(this, e, t, n);
                        case "base64":
                            return S(this, e, t, n);
                        case "ucs2":
                        case "ucs-2":
                        case "utf16le":
                        case "utf-16le":
                            return N(this, e, t, n);
                        default:
                            if (o) throw new TypeError("Unknown encoding: " + a);
                            a = ("" + a).toLowerCase(), o = !0
                    }
                }, l.prototype.toJSON = function() {
                    return {
                        type: "Buffer",
                        data: Array.prototype.slice.call(this._arr || this, 0)
                    }
                };
                var I = 4096;

                function R(e) {
                    var t = e.length;
                    if (t <= I) return String.fromCharCode.apply(String, e);
                    var n = "",
                        a = 0;
                    while (a < t) n += String.fromCharCode.apply(String, e.slice(a, a += I));
                    return n
                }

                function P(e, t, n) {
                    var a = "";
                    n = Math.min(e.length, n);
                    for (var u = t; u < n; ++u) a += String.fromCharCode(127 & e[u]);
                    return a
                }

                function $(e, t, n) {
                    var a = "";
                    n = Math.min(e.length, n);
                    for (var u = t; u < n; ++u) a += String.fromCharCode(e[u]);
                    return a
                }

                function M(e, t, n) {
                    var a = e.length;
                    (!t || t < 0) && (t = 0), (!n || n < 0 || n > a) && (n = a);
                    for (var u = "", o = t; o < n; ++o) u += Q(e[o]);
                    return u
                }

                function O(e, t, n) {
                    for (var a = e.slice(t, n), u = "", o = 0; o < a.length; o += 2) u += String.fromCharCode(a[o] + 256 * a[o + 1]);
                    return u
                }

                function z(e, t, n) {
                    if (e % 1 !== 0 || e < 0) throw new RangeError("offset is not uint");
                    if (e + t > n) throw new RangeError("Trying to access beyond buffer length")
                }

                function _(e, t, n, a, u, o) {
                    if (!l.isBuffer(e)) throw new TypeError('"buffer" argument must be a Buffer instance');
                    if (t > u || t < o) throw new RangeError('"value" argument is out of bounds');
                    if (n + a > e.length) throw new RangeError("Index out of range")
                }

                function j(e, t, n, a) {
                    t < 0 && (t = 65535 + t + 1);
                    for (var u = 0, o = Math.min(e.length - n, 2); u < o; ++u) e[n + u] = (t & 255 << 8 * (a ? u : 1 - u)) >>> 8 * (a ? u : 1 - u)
                }

                function U(e, t, n, a) {
                    t < 0 && (t = 4294967295 + t + 1);
                    for (var u = 0, o = Math.min(e.length - n, 4); u < o; ++u) e[n + u] = t >>> 8 * (a ? u : 3 - u) & 255
                }

                function q(e, t, n, a, u, o) {
                    if (n + a > e.length) throw new RangeError("Index out of range");
                    if (n < 0) throw new RangeError("Index out of range")
                }

                function Y(e, t, n, a, o) {
                    return o || q(e, t, n, 4, 34028234663852886e22, -34028234663852886e22), u.write(e, t, n, a, 23, 4), n + 4
                }

                function W(e, t, n, a, o) {
                    return o || q(e, t, n, 8, 17976931348623157e292, -17976931348623157e292), u.write(e, t, n, a, 52, 8), n + 8
                }
                l.prototype.slice = function(e, t) {
                    var n, a = this.length;
                    if (e = ~~e, t = void 0 === t ? a : ~~t, e < 0 ? (e += a, e < 0 && (e = 0)) : e > a && (e = a), t < 0 ? (t += a, t < 0 && (t = 0)) : t > a && (t = a), t < e && (t = e), l.TYPED_ARRAY_SUPPORT) n = this.subarray(e, t), n.__proto__ = l.prototype;
                    else {
                        var u = t - e;
                        n = new l(u, void 0);
                        for (var o = 0; o < u; ++o) n[o] = this[o + e]
                    }
                    return n
                }, l.prototype.readUIntLE = function(e, t, n) {
                    e |= 0, t |= 0, n || z(e, t, this.length);
                    var a = this[e],
                        u = 1,
                        o = 0;
                    while (++o < t && (u *= 256)) a += this[e + o] * u;
                    return a
                }, l.prototype.readUIntBE = function(e, t, n) {
                    e |= 0, t |= 0, n || z(e, t, this.length);
                    var a = this[e + --t],
                        u = 1;
                    while (t > 0 && (u *= 256)) a += this[e + --t] * u;
                    return a
                }, l.prototype.readUInt8 = function(e, t) {
                    return t || z(e, 1, this.length), this[e]
                }, l.prototype.readUInt16LE = function(e, t) {
                    return t || z(e, 2, this.length), this[e] | this[e + 1] << 8
                }, l.prototype.readUInt16BE = function(e, t) {
                    return t || z(e, 2, this.length), this[e] << 8 | this[e + 1]
                }, l.prototype.readUInt32LE = function(e, t) {
                    return t || z(e, 4, this.length), (this[e] | this[e + 1] << 8 | this[e + 2] << 16) + 16777216 * this[e + 3]
                }, l.prototype.readUInt32BE = function(e, t) {
                    return t || z(e, 4, this.length), 16777216 * this[e] + (this[e + 1] << 16 | this[e + 2] << 8 | this[e + 3])
                }, l.prototype.readIntLE = function(e, t, n) {
                    e |= 0, t |= 0, n || z(e, t, this.length);
                    var a = this[e],
                        u = 1,
                        o = 0;
                    while (++o < t && (u *= 256)) a += this[e + o] * u;
                    return u *= 128, a >= u && (a -= Math.pow(2, 8 * t)), a
                }, l.prototype.readIntBE = function(e, t, n) {
                    e |= 0, t |= 0, n || z(e, t, this.length);
                    var a = t,
                        u = 1,
                        o = this[e + --a];
                    while (a > 0 && (u *= 256)) o += this[e + --a] * u;
                    return u *= 128, o >= u && (o -= Math.pow(2, 8 * t)), o
                }, l.prototype.readInt8 = function(e, t) {
                    return t || z(e, 1, this.length), 128 & this[e] ? -1 * (255 - this[e] + 1) : this[e]
                }, l.prototype.readInt16LE = function(e, t) {
                    t || z(e, 2, this.length);
                    var n = this[e] | this[e + 1] << 8;
                    return 32768 & n ? 4294901760 | n : n
                }, l.prototype.readInt16BE = function(e, t) {
                    t || z(e, 2, this.length);
                    var n = this[e + 1] | this[e] << 8;
                    return 32768 & n ? 4294901760 | n : n
                }, l.prototype.readInt32LE = function(e, t) {
                    return t || z(e, 4, this.length), this[e] | this[e + 1] << 8 | this[e + 2] << 16 | this[e + 3] << 24
                }, l.prototype.readInt32BE = function(e, t) {
                    return t || z(e, 4, this.length), this[e] << 24 | this[e + 1] << 16 | this[e + 2] << 8 | this[e + 3]
                }, l.prototype.readFloatLE = function(e, t) {
                    return t || z(e, 4, this.length), u.read(this, e, !0, 23, 4)
                }, l.prototype.readFloatBE = function(e, t) {
                    return t || z(e, 4, this.length), u.read(this, e, !1, 23, 4)
                }, l.prototype.readDoubleLE = function(e, t) {
                    return t || z(e, 8, this.length), u.read(this, e, !0, 52, 8)
                }, l.prototype.readDoubleBE = function(e, t) {
                    return t || z(e, 8, this.length), u.read(this, e, !1, 52, 8)
                }, l.prototype.writeUIntLE = function(e, t, n, a) {
                    if (e = +e, t |= 0, n |= 0, !a) {
                        var u = Math.pow(2, 8 * n) - 1;
                        _(this, e, t, n, u, 0)
                    }
                    var o = 1,
                        r = 0;
                    this[t] = 255 & e;
                    while (++r < n && (o *= 256)) this[t + r] = e / o & 255;
                    return t + n
                }, l.prototype.writeUIntBE = function(e, t, n, a) {
                    if (e = +e, t |= 0, n |= 0, !a) {
                        var u = Math.pow(2, 8 * n) - 1;
                        _(this, e, t, n, u, 0)
                    }
                    var o = n - 1,
                        r = 1;
                    this[t + o] = 255 & e;
                    while (--o >= 0 && (r *= 256)) this[t + o] = e / r & 255;
                    return t + n
                }, l.prototype.writeUInt8 = function(e, t, n) {
                    return e = +e, t |= 0, n || _(this, e, t, 1, 255, 0), l.TYPED_ARRAY_SUPPORT || (e = Math.floor(e)), this[t] = 255 & e, t + 1
                }, l.prototype.writeUInt16LE = function(e, t, n) {
                    return e = +e, t |= 0, n || _(this, e, t, 2, 65535, 0), l.TYPED_ARRAY_SUPPORT ? (this[t] = 255 & e, this[t + 1] = e >>> 8) : j(this, e, t, !0), t + 2
                }, l.prototype.writeUInt16BE = function(e, t, n) {
                    return e = +e, t |= 0, n || _(this, e, t, 2, 65535, 0), l.TYPED_ARRAY_SUPPORT ? (this[t] = e >>> 8, this[t + 1] = 255 & e) : j(this, e, t, !1), t + 2
                }, l.prototype.writeUInt32LE = function(e, t, n) {
                    return e = +e, t |= 0, n || _(this, e, t, 4, 4294967295, 0), l.TYPED_ARRAY_SUPPORT ? (this[t + 3] = e >>> 24, this[t + 2] = e >>> 16, this[t + 1] = e >>> 8, this[t] = 255 & e) : U(this, e, t, !0), t + 4
                }, l.prototype.writeUInt32BE = function(e, t, n) {
                    return e = +e, t |= 0, n || _(this, e, t, 4, 4294967295, 0), l.TYPED_ARRAY_SUPPORT ? (this[t] = e >>> 24, this[t + 1] = e >>> 16, this[t + 2] = e >>> 8, this[t + 3] = 255 & e) : U(this, e, t, !1), t + 4
                }, l.prototype.writeIntLE = function(e, t, n, a) {
                    if (e = +e, t |= 0, !a) {
                        var u = Math.pow(2, 8 * n - 1);
                        _(this, e, t, n, u - 1, -u)
                    }
                    var o = 0,
                        r = 1,
                        i = 0;
                    this[t] = 255 & e;
                    while (++o < n && (r *= 256)) e < 0 && 0 === i && 0 !== this[t + o - 1] && (i = 1), this[t + o] = (e / r >> 0) - i & 255;
                    return t + n
                }, l.prototype.writeIntBE = function(e, t, n, a) {
                    if (e = +e, t |= 0, !a) {
                        var u = Math.pow(2, 8 * n - 1);
                        _(this, e, t, n, u - 1, -u)
                    }
                    var o = n - 1,
                        r = 1,
                        i = 0;
                    this[t + o] = 255 & e;
                    while (--o >= 0 && (r *= 256)) e < 0 && 0 === i && 0 !== this[t + o + 1] && (i = 1), this[t + o] = (e / r >> 0) - i & 255;
                    return t + n
                }, l.prototype.writeInt8 = function(e, t, n) {
                    return e = +e, t |= 0, n || _(this, e, t, 1, 127, -128), l.TYPED_ARRAY_SUPPORT || (e = Math.floor(e)), e < 0 && (e = 255 + e + 1), this[t] = 255 & e, t + 1
                }, l.prototype.writeInt16LE = function(e, t, n) {
                    return e = +e, t |= 0, n || _(this, e, t, 2, 32767, -32768), l.TYPED_ARRAY_SUPPORT ? (this[t] = 255 & e, this[t + 1] = e >>> 8) : j(this, e, t, !0), t + 2
                }, l.prototype.writeInt16BE = function(e, t, n) {
                    return e = +e, t |= 0, n || _(this, e, t, 2, 32767, -32768), l.TYPED_ARRAY_SUPPORT ? (this[t] = e >>> 8, this[t + 1] = 255 & e) : j(this, e, t, !1), t + 2
                }, l.prototype.writeInt32LE = function(e, t, n) {
                    return e = +e, t |= 0, n || _(this, e, t, 4, 2147483647, -2147483648), l.TYPED_ARRAY_SUPPORT ? (this[t] = 255 & e, this[t + 1] = e >>> 8, this[t + 2] = e >>> 16, this[t + 3] = e >>> 24) : U(this, e, t, !0), t + 4
                }, l.prototype.writeInt32BE = function(e, t, n) {
                    return e = +e, t |= 0, n || _(this, e, t, 4, 2147483647, -2147483648), e < 0 && (e = 4294967295 + e + 1), l.TYPED_ARRAY_SUPPORT ? (this[t] = e >>> 24, this[t + 1] = e >>> 16, this[t + 2] = e >>> 8, this[t + 3] = 255 & e) : U(this, e, t, !1), t + 4
                }, l.prototype.writeFloatLE = function(e, t, n) {
                    return Y(this, e, t, !0, n)
                }, l.prototype.writeFloatBE = function(e, t, n) {
                    return Y(this, e, t, !1, n)
                }, l.prototype.writeDoubleLE = function(e, t, n) {
                    return W(this, e, t, !0, n)
                }, l.prototype.writeDoubleBE = function(e, t, n) {
                    return W(this, e, t, !1, n)
                }, l.prototype.copy = function(e, t, n, a) {
                    if (n || (n = 0), a || 0 === a || (a = this.length), t >= e.length && (t = e.length), t || (t = 0), a > 0 && a < n && (a = n), a === n) return 0;
                    if (0 === e.length || 0 === this.length) return 0;
                    if (t < 0) throw new RangeError("targetStart out of bounds");
                    if (n < 0 || n >= this.length) throw new RangeError("sourceStart out of bounds");
                    if (a < 0) throw new RangeError("sourceEnd out of bounds");
                    a > this.length && (a = this.length), e.length - t < a - n && (a = e.length - t + n);
                    var u, o = a - n;
                    if (this === e && n < t && t < a)
                        for (u = o - 1; u >= 0; --u) e[u + t] = this[u + n];
                    else if (o < 1e3 || !l.TYPED_ARRAY_SUPPORT)
                        for (u = 0; u < o; ++u) e[u + t] = this[u + n];
                    else Uint8Array.prototype.set.call(e, this.subarray(n, n + o), t);
                    return o
                }, l.prototype.fill = function(e, t, n, a) {
                    if ("string" === typeof e) {
                        if ("string" === typeof t ? (a = t, t = 0, n = this.length) : "string" === typeof n && (a = n, n = this.length), 1 === e.length) {
                            var u = e.charCodeAt(0);
                            u < 256 && (e = u)
                        }
                        if (void 0 !== a && "string" !== typeof a) throw new TypeError("encoding must be a string");
                        if ("string" === typeof a && !l.isEncoding(a)) throw new TypeError("Unknown encoding: " + a)
                    } else "number" === typeof e && (e &= 255);
                    if (t < 0 || this.length < t || this.length < n) throw new RangeError("Out of range index");
                    if (n <= t) return this;
                    var o;
                    if (t >>>= 0, n = void 0 === n ? this.length : n >>> 0, e || (e = 0), "number" === typeof e)
                        for (o = t; o < n; ++o) this[o] = e;
                    else {
                        var r = l.isBuffer(e) ? e : G(new l(e, a).toString()),
                            i = r.length;
                        for (o = 0; o < n - t; ++o) this[o + t] = r[o % i]
                    }
                    return this
                };
                var H = /[^+\/0-9A-Za-z-_]/g;

                function K(e) {
                    if (e = V(e).replace(H, ""), e.length < 2) return "";
                    while (e.length % 4 !== 0) e += "=";
                    return e
                }

                function V(e) {
                    return e.trim ? e.trim() : e.replace(/^\s+|\s+$/g, "")
                }

                function Q(e) {
                    return e < 16 ? "0" + e.toString(16) : e.toString(16)
                }

                function G(e, t) {
                    var n;
                    t = t || 1 / 0;
                    for (var a = e.length, u = null, o = [], r = 0; r < a; ++r) {
                        if (n = e.charCodeAt(r), n > 55295 && n < 57344) {
                            if (!u) {
                                if (n > 56319) {
                                    (t -= 3) > -1 && o.push(239, 191, 189);
                                    continue
                                }
                                if (r + 1 === a) {
                                    (t -= 3) > -1 && o.push(239, 191, 189);
                                    continue
                                }
                                u = n;
                                continue
                            }
                            if (n < 56320) {
                                (t -= 3) > -1 && o.push(239, 191, 189), u = n;
                                continue
                            }
                            n = 65536 + (u - 55296 << 10 | n - 56320)
                        } else u && (t -= 3) > -1 && o.push(239, 191, 189);
                        if (u = null, n < 128) {
                            if ((t -= 1) < 0) break;
                            o.push(n)
                        } else if (n < 2048) {
                            if ((t -= 2) < 0) break;
                            o.push(n >> 6 | 192, 63 & n | 128)
                        } else if (n < 65536) {
                            if ((t -= 3) < 0) break;
                            o.push(n >> 12 | 224, n >> 6 & 63 | 128, 63 & n | 128)
                        } else {
                            if (!(n < 1114112)) throw new Error("Invalid code point");
                            if ((t -= 4) < 0) break;
                            o.push(n >> 18 | 240, n >> 12 & 63 | 128, n >> 6 & 63 | 128, 63 & n | 128)
                        }
                    }
                    return o
                }

                function X(e) {
                    for (var t = [], n = 0; n < e.length; ++n) t.push(255 & e.charCodeAt(n));
                    return t
                }

                function Z(e, t) {
                    for (var n, a, u, o = [], r = 0; r < e.length; ++r) {
                        if ((t -= 2) < 0) break;
                        n = e.charCodeAt(r), a = n >> 8, u = n % 256, o.push(u), o.push(a)
                    }
                    return o
                }

                function J(e) {
                    return a.toByteArray(K(e))
                }

                function ee(e, t, n, a) {
                    for (var u = 0; u < a; ++u) {
                        if (u + n >= t.length || u >= e.length) break;
                        t[u + n] = e[u]
                    }
                    return u
                }

                function te(e) {
                    return e !== e
                }
            }).call(this, n("c8ba"))
        
