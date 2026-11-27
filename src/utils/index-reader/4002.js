// 恢复自 reader-pro-3.2.14 前端产物 beauty/index-reader.js 的 webpack 模块 4002（逐字切片，形参 e, t, r）
const e = module;
const t = module.exports;
const r = require;

            "use strict";
            r("3ca3");
            var n, i = r("23e7"),
                a = r("83ab"),
                o = r("f354"),
                s = r("cfe9"),
                l = r("0366"),
                u = r("e330"),
                c = r("cb2d"),
                f = r("edd0"),
                h = r("19aa"),
                d = r("1a2d"),
                p = r("60da"),
                v = r("4df4"),
                g = r("f36a"),
                m = r("6547").codeAt,
                b = r("5fb2"),
                y = r("577e"),
                S = r("d44e"),
                w = r("d6d6"),
                k = r("5352"),
                z = r("69f3"),
                B = z.set,
                R = z.getterFor("URL"),
                O = k.URLSearchParams,
                U = k.getState,
                P = s.URL,
                L = s.TypeError,
                j = s.parseInt,
                x = Math.floor,
                E = Math.pow,
                C = u("".charAt),
                H = u(/./.exec),
                A = u([].join),
                T = u(1..toString),
                q = u([].pop),
                F = u([].push),
                M = u("".replace),
                V = u([].shift),
                N = u("".split),
                $ = u("".slice),
                I = u("".toLowerCase),
                G = u([].unshift),
                K = "Invalid authority",
                D = "Invalid scheme",
                J = "Invalid host",
                Q = "Invalid port",
                _ = /[a-z]/i,
                Y = /[\d+-.a-z]/i,
                W = /\d/,
                X = /^0x/i,
                Z = /^[0-7]+$/,
                ee = /^\d+$/,
                te = /^[\da-f]+$/i,
                re = /[\0\t\n\r #%/:<>?@[\\\]^|]/,
                ne = /[\0\t\n\r #/:<>?@[\\\]^|]/,
                ie = /^[\u0000-\u0020]+/,
                ae = /(^|[^\u0000-\u0020])[\u0000-\u0020]+$/,
                oe = /[\t\n\r]/g,
                se = function(e) {
                    var t, r, n, i, a, o, s, l = N(e, ".");
                    if (l.length && "" === l[l.length - 1] && l.length--, t = l.length, t > 4) return e;
                    for (r = [], n = 0; n < t; n++) {
                        if (i = l[n], "" === i) return e;
                        if (a = 10, i.length > 1 && "0" === C(i, 0) && (a = H(X, i) ? 16 : 8, i = $(i, 8 === a ? 1 : 2)), "" === i) o = 0;
                        else {
                            if (!H(10 === a ? ee : 8 === a ? Z : te, i)) return e;
                            o = j(i, a)
                        }
                        F(r, o)
                    }
                    for (n = 0; n < t; n++)
                        if (o = r[n], n === t - 1) {
                            if (o >= E(256, 5 - t)) return null
                        } else if (o > 255) return null;
                    for (s = q(r), n = 0; n < r.length; n++) s += r[n] * E(256, 3 - n);
                    return s
                },
                le = function(e) {
                    var t, r, n, i, a, o, s, l = [0, 0, 0, 0, 0, 0, 0, 0],
                        u = 0,
                        c = null,
                        f = 0,
                        h = function() {
                            return C(e, f)
                        };
                    if (":" === h()) {
                        if (":" !== C(e, 1)) return;
                        f += 2, u++, c = u
                    }
                    while (h()) {
                        if (8 === u) return;
                        if (":" !== h()) {
                            t = r = 0;
                            while (r < 4 && H(te, h())) t = 16 * t + j(h(), 16), f++, r++;
                            if ("." === h()) {
                                if (0 === r) return;
                                if (f -= r, u > 6) return;
                                n = 0;
                                while (h()) {
                                    if (i = null, n > 0) {
                                        if (!("." === h() && n < 4)) return;
                                        f++
                                    }
                                    if (!H(W, h())) return;
                                    while (H(W, h())) {
                                        if (a = j(h(), 10), null === i) i = a;
                                        else {
                                            if (0 === i) return;
                                            i = 10 * i + a
                                        }
                                        if (i > 255) return;
                                        f++
                                    }
                                    l[u] = 256 * l[u] + i, n++, 2 !== n && 4 !== n || u++
                                }
                                if (4 !== n) return;
                                break
                            }
                            if (":" === h()) {
                                if (f++, !h()) return
                            } else if (h()) return;
                            l[u++] = t
                        } else {
                            if (null !== c) return;
                            f++, u++, c = u
                        }
                    }
                    if (null !== c) {
                        o = u - c, u = 7;
                        while (0 !== u && o > 0) s = l[u], l[u--] = l[c + o - 1], l[c + --o] = s
                    } else if (8 !== u) return;
                    return l
                },
                ue = function(e) {
                    for (var t = null, r = 1, n = null, i = 0, a = 0; a < 8; a++) 0 !== e[a] ? (i > r && (t = n, r = i), n = null, i = 0) : (null === n && (n = a), ++i);
                    return i > r ? n : t
                },
                ce = function(e) {
                    var t, r, n, i;
                    if ("number" == typeof e) {
                        for (t = [], r = 0; r < 4; r++) G(t, e % 256), e = x(e / 256);
                        return A(t, ".")
                    }
                    if ("object" == typeof e) {
                        for (t = "", n = ue(e), r = 0; r < 8; r++) i && 0 === e[r] || (i && (i = !1), n === r ? (t += r ? ":" : "::", i = !0) : (t += T(e[r], 16), r < 7 && (t += ":")));
                        return "[" + t + "]"
                    }
                    return e
                },
                fe = {},
                he = p({}, fe, {
                    " ": 1,
                    '"': 1,
                    "<": 1,
                    ">": 1,
                    "`": 1
                }),
                de = p({}, he, {
                    "#": 1,
                    "?": 1,
                    "{": 1,
                    "}": 1
                }),
                pe = p({}, de, {
                    "/": 1,
                    ":": 1,
                    ";": 1,
                    "=": 1,
                    "@": 1,
                    "[": 1,
                    "\\": 1,
                    "]": 1,
                    "^": 1,
                    "|": 1
                }),
                ve = function(e, t) {
                    var r = m(e, 0);
                    return r > 32 && r < 127 && !d(t, e) ? e : encodeURIComponent(e)
                },
                ge = {
                    ftp: 21,
                    file: null,
                    http: 80,
                    https: 443,
                    ws: 80,
                    wss: 443
                },
                me = function(e, t) {
                    var r;
                    return 2 === e.length && H(_, C(e, 0)) && (":" === (r = C(e, 1)) || !t && "|" === r)
                },
                be = function(e) {
                    var t;
                    return e.length > 1 && me($(e, 0, 2)) && (2 === e.length || "/" === (t = C(e, 2)) || "\\" === t || "?" === t || "#" === t)
                },
                ye = function(e) {
                    return "." === e || "%2e" === I(e)
                },
                Se = function(e) {
                    return e = I(e), ".." === e || "%2e." === e || ".%2e" === e || "%2e%2e" === e
                },
                we = {},
                ke = {},
                ze = {},
                Be = {},
                Re = {},
                Oe = {},
                Ue = {},
                Pe = {},
                Le = {},
                je = {},
                xe = {},
                Ee = {},
                Ce = {},
                He = {},
                Ae = {},
                Te = {},
                qe = {},
                Fe = {},
                Me = {},
                Ve = {},
                Ne = {},
                $e = function(e, t, r) {
                    var n, i, a, o = y(e);
                    if (t) {
                        if (i = this.parse(o), i) throw new L(i);
                        this.searchParams = null
                    } else {
                        if (void 0 !== r && (n = new $e(r, !0)), i = this.parse(o, null, n), i) throw new L(i);
                        a = U(new O), a.bindURL(this), this.searchParams = a
                    }
                };
            $e.prototype = {
                type: "URL",
                parse: function(e, t, r) {
                    var i, a, o, s, l = this,
                        u = t || we,
                        c = 0,
                        f = "",
                        h = !1,
                        p = !1,
                        m = !1;
                    e = y(e), t || (l.scheme = "", l.username = "", l.password = "", l.host = null, l.port = null, l.path = [], l.query = null, l.fragment = null, l.cannotBeABaseURL = !1, e = M(e, ie, ""), e = M(e, ae, "$1")), e = M(e, oe, ""), i = v(e);
                    while (c <= i.length) {
                        switch (a = i[c], u) {
                            case we:
                                if (!a || !H(_, a)) {
                                    if (t) return D;
                                    u = ze;
                                    continue
                                }
                                f += I(a), u = ke;
                                break;
                            case ke:
                                if (a && (H(Y, a) || "+" === a || "-" === a || "." === a)) f += I(a);
                                else {
                                    if (":" !== a) {
                                        if (t) return D;
                                        f = "", u = ze, c = 0;
                                        continue
                                    }
                                    if (t && (l.isSpecial() !== d(ge, f) || "file" === f && (l.includesCredentials() || null !== l.port) || "file" === l.scheme && !l.host)) return;
                                    if (l.scheme = f, t) return void(l.isSpecial() && ge[l.scheme] === l.port && (l.port = null));
                                    f = "", "file" === l.scheme ? u = He : l.isSpecial() && r && r.scheme === l.scheme ? u = Be : l.isSpecial() ? u = Pe : "/" === i[c + 1] ? (u = Re, c++) : (l.cannotBeABaseURL = !0, F(l.path, ""), u = Me)
                                }
                                break;
                            case ze:
                                if (!r || r.cannotBeABaseURL && "#" !== a) return D;
                                if (r.cannotBeABaseURL && "#" === a) {
                                    l.scheme = r.scheme, l.path = g(r.path), l.query = r.query, l.fragment = "", l.cannotBeABaseURL = !0, u = Ne;
                                    break
                                }
                                u = "file" === r.scheme ? He : Oe;
                                continue;
                            case Be:
                                if ("/" !== a || "/" !== i[c + 1]) {
                                    u = Oe;
                                    continue
                                }
                                u = Le, c++;
                                break;
                            case Re:
                                if ("/" === a) {
                                    u = je;
                                    break
                                }
                                u = Fe;
                                continue;
                            case Oe:
                                if (l.scheme = r.scheme, a === n) l.username = r.username, l.password = r.password, l.host = r.host, l.port = r.port, l.path = g(r.path), l.query = r.query;
                                else if ("/" === a || "\\" === a && l.isSpecial()) u = Ue;
                                else if ("?" === a) l.username = r.username, l.password = r.password, l.host = r.host, l.port = r.port, l.path = g(r.path), l.query = "", u = Ve;
                                else {
                                    if ("#" !== a) {
                                        l.username = r.username, l.password = r.password, l.host = r.host, l.port = r.port, l.path = g(r.path), l.path.length--, u = Fe;
                                        continue
                                    }
                                    l.username = r.username, l.password = r.password, l.host = r.host, l.port = r.port, l.path = g(r.path), l.query = r.query, l.fragment = "", u = Ne
                                }
                                break;
                            case Ue:
                                if (!l.isSpecial() || "/" !== a && "\\" !== a) {
                                    if ("/" !== a) {
                                        l.username = r.username, l.password = r.password, l.host = r.host, l.port = r.port, u = Fe;
                                        continue
                                    }
                                    u = je
                                } else u = Le;
                                break;
                            case Pe:
                                if (u = Le, "/" !== a || "/" !== C(f, c + 1)) continue;
                                c++;
                                break;
                            case Le:
                                if ("/" !== a && "\\" !== a) {
                                    u = je;
                                    continue
                                }
                                break;
                            case je:
                                if ("@" === a) {
                                    h && (f = "%40" + f), h = !0, o = v(f);
                                    for (var b = 0; b < o.length; b++) {
                                        var S = o[b];
                                        if (":" !== S || m) {
                                            var w = ve(S, pe);
                                            m ? l.password += w : l.username += w
                                        } else m = !0
                                    }
                                    f = ""
                                } else if (a === n || "/" === a || "?" === a || "#" === a || "\\" === a && l.isSpecial()) {
                                    if (h && "" === f) return K;
                                    c -= v(f).length + 1, f = "", u = xe
                                } else f += a;
                                break;
                            case xe:
                            case Ee:
                                if (t && "file" === l.scheme) {
                                    u = Te;
                                    continue
                                }
                                if (":" !== a || p) {
                                    if (a === n || "/" === a || "?" === a || "#" === a || "\\" === a && l.isSpecial()) {
                                        if (l.isSpecial() && "" === f) return J;
                                        if (t && "" === f && (l.includesCredentials() || null !== l.port)) return;
                                        if (s = l.parseHost(f), s) return s;
                                        if (f = "", u = qe, t) return;
                                        continue
                                    }
                                    "[" === a ? p = !0 : "]" === a && (p = !1), f += a
                                } else {
                                    if ("" === f) return J;
                                    if (s = l.parseHost(f), s) return s;
                                    if (f = "", u = Ce, t === Ee) return
                                }
                                break;
                            case Ce:
                                if (!H(W, a)) {
                                    if (a === n || "/" === a || "?" === a || "#" === a || "\\" === a && l.isSpecial() || t) {
                                        if ("" !== f) {
                                            var k = j(f, 10);
                                            if (k > 65535) return Q;
                                            l.port = l.isSpecial() && k === ge[l.scheme] ? null : k, f = ""
                                        }
                                        if (t) return;
                                        u = qe;
                                        continue
                                    }
                                    return Q
                                }
                                f += a;
                                break;
                            case He:
                                if (l.scheme = "file", "/" === a || "\\" === a) u = Ae;
                                else {
                                    if (!r || "file" !== r.scheme) {
                                        u = Fe;
                                        continue
                                    }
                                    switch (a) {
                                        case n:
                                            l.host = r.host, l.path = g(r.path), l.query = r.query;
                                            break;
                                        case "?":
                                            l.host = r.host, l.path = g(r.path), l.query = "", u = Ve;
                                            break;
                                        case "#":
                                            l.host = r.host, l.path = g(r.path), l.query = r.query, l.fragment = "", u = Ne;
                                            break;
                                        default:
                                            be(A(g(i, c), "")) || (l.host = r.host, l.path = g(r.path), l.shortenPath()), u = Fe;
                                            continue
                                    }
                                }
                                break;
                            case Ae:
                                if ("/" === a || "\\" === a) {
                                    u = Te;
                                    break
                                }
                                r && "file" === r.scheme && !be(A(g(i, c), "")) && (me(r.path[0], !0) ? F(l.path, r.path[0]) : l.host = r.host), u = Fe;
                                continue;
                            case Te:
                                if (a === n || "/" === a || "\\" === a || "?" === a || "#" === a) {
                                    if (!t && me(f)) u = Fe;
                                    else if ("" === f) {
                                        if (l.host = "", t) return;
                                        u = qe
                                    } else {
                                        if (s = l.parseHost(f), s) return s;
                                        if ("localhost" === l.host && (l.host = ""), t) return;
                                        f = "", u = qe
                                    }
                                    continue
                                }
                                f += a;
                                break;
                            case qe:
                                if (l.isSpecial()) {
                                    if (u = Fe, "/" !== a && "\\" !== a) continue
                                } else if (t || "?" !== a)
                                    if (t || "#" !== a) {
                                        if (a !== n && (u = Fe, "/" !== a)) continue
                                    } else l.fragment = "", u = Ne;
                                else l.query = "", u = Ve;
                                break;
                            case Fe:
                                if (a === n || "/" === a || "\\" === a && l.isSpecial() || !t && ("?" === a || "#" === a)) {
                                    if (Se(f) ? (l.shortenPath(), "/" === a || "\\" === a && l.isSpecial() || F(l.path, "")) : ye(f) ? "/" === a || "\\" === a && l.isSpecial() || F(l.path, "") : ("file" === l.scheme && !l.path.length && me(f) && (l.host && (l.host = ""), f = C(f, 0) + ":"), F(l.path, f)), f = "", "file" === l.scheme && (a === n || "?" === a || "#" === a))
                                        while (l.path.length > 1 && "" === l.path[0]) V(l.path);
                                    "?" === a ? (l.query = "", u = Ve) : "#" === a && (l.fragment = "", u = Ne)
                                } else f += ve(a, de);
                                break;
                            case Me:
                                "?" === a ? (l.query = "", u = Ve) : "#" === a ? (l.fragment = "", u = Ne) : a !== n && (l.path[0] += ve(a, fe));
                                break;
                            case Ve:
                                t || "#" !== a ? a !== n && ("'" === a && l.isSpecial() ? l.query += "%27" : l.query += "#" === a ? "%23" : ve(a, fe)) : (l.fragment = "", u = Ne);
                                break;
                            case Ne:
                                a !== n && (l.fragment += ve(a, he));
                                break
                        }
                        c++
                    }
                },
                parseHost: function(e) {
                    var t, r, n;
                    if ("[" === C(e, 0)) {
                        if ("]" !== C(e, e.length - 1)) return J;
                        if (t = le($(e, 1, -1)), !t) return J;
                        this.host = t
                    } else if (this.isSpecial()) {
                        if (e = b(e), H(re, e)) return J;
                        if (t = se(e), null === t) return J;
                        this.host = t
                    } else {
                        if (H(ne, e)) return J;
                        for (t = "", r = v(e), n = 0; n < r.length; n++) t += ve(r[n], fe);
                        this.host = t
                    }
                },
                cannotHaveUsernamePasswordPort: function() {
                    return !this.host || this.cannotBeABaseURL || "file" === this.scheme
                },
                includesCredentials: function() {
                    return "" !== this.username || "" !== this.password
                },
                isSpecial: function() {
                    return d(ge, this.scheme)
                },
                shortenPath: function() {
                    var e = this.path,
                        t = e.length;
                    !t || "file" === this.scheme && 1 === t && me(e[0], !0) || e.length--
                },
                serialize: function() {
                    var e = this,
                        t = e.scheme,
                        r = e.username,
                        n = e.password,
                        i = e.host,
                        a = e.port,
                        o = e.path,
                        s = e.query,
                        l = e.fragment,
                        u = t + ":";
                    return null !== i ? (u += "//", e.includesCredentials() && (u += r + (n ? ":" + n : "") + "@"), u += ce(i), null !== a && (u += ":" + a)) : "file" === t && (u += "//"), u += e.cannotBeABaseURL ? o[0] : o.length ? "/" + A(o, "/") : "", null !== s && (u += "?" + s), null !== l && (u += "#" + l), u
                },
                setHref: function(e) {
                    var t = this.parse(e);
                    if (t) throw new L(t);
                    this.searchParams.update()
                },
                getOrigin: function() {
                    var e = this.scheme,
                        t = this.port;
                    if ("blob" === e) try {
                        return new Ie(e.path[0]).origin
                    } catch (r) {
                        return "null"
                    }
                    return "file" !== e && this.isSpecial() ? e + "://" + ce(this.host) + (null !== t ? ":" + t : "") : "null"
                },
                getProtocol: function() {
                    return this.scheme + ":"
                },
                setProtocol: function(e) {
                    this.parse(y(e) + ":", we)
                },
                getUsername: function() {
                    return this.username
                },
                setUsername: function(e) {
                    var t = v(y(e));
                    if (!this.cannotHaveUsernamePasswordPort()) {
                        this.username = "";
                        for (var r = 0; r < t.length; r++) this.username += ve(t[r], pe)
                    }
                },
                getPassword: function() {
                    return this.password
                },
                setPassword: function(e) {
                    var t = v(y(e));
                    if (!this.cannotHaveUsernamePasswordPort()) {
                        this.password = "";
                        for (var r = 0; r < t.length; r++) this.password += ve(t[r], pe)
                    }
                },
                getHost: function() {
                    var e = this.host,
                        t = this.port;
                    return null === e ? "" : null === t ? ce(e) : ce(e) + ":" + t
                },
                setHost: function(e) {
                    this.cannotBeABaseURL || this.parse(e, xe)
                },
                getHostname: function() {
                    var e = this.host;
                    return null === e ? "" : ce(e)
                },
                setHostname: function(e) {
                    this.cannotBeABaseURL || this.parse(e, Ee)
                },
                getPort: function() {
                    var e = this.port;
                    return null === e ? "" : y(e)
                },
                setPort: function(e) {
                    this.cannotHaveUsernamePasswordPort() || (e = y(e), "" === e ? this.port = null : this.parse(e, Ce))
                },
                getPathname: function() {
                    var e = this.path;
                    return this.cannotBeABaseURL ? e[0] : e.length ? "/" + A(e, "/") : ""
                },
                setPathname: function(e) {
                    this.cannotBeABaseURL || (this.path = [], this.parse(e, qe))
                },
                getSearch: function() {
                    var e = this.query;
                    return e ? "?" + e : ""
                },
                setSearch: function(e) {
                    e = y(e), "" === e ? this.query = null : ("?" === C(e, 0) && (e = $(e, 1)), this.query = "", this.parse(e, Ve)), this.searchParams.update()
                },
                getSearchParams: function() {
                    return this.searchParams.facade
                },
                getHash: function() {
                    var e = this.fragment;
                    return e ? "#" + e : ""
                },
                setHash: function(e) {
                    e = y(e), "" !== e ? ("#" === C(e, 0) && (e = $(e, 1)), this.fragment = "", this.parse(e, Ne)) : this.fragment = null
                },
                update: function() {
                    this.query = this.searchParams.serialize() || null
                }
            };
            var Ie = function(e) {
                    var t = h(this, Ge),
                        r = w(arguments.length, 1) > 1 ? arguments[1] : void 0,
                        n = B(t, new $e(e, !1, r));
                    a || (t.href = n.serialize(), t.origin = n.getOrigin(), t.protocol = n.getProtocol(), t.username = n.getUsername(), t.password = n.getPassword(), t.host = n.getHost(), t.hostname = n.getHostname(), t.port = n.getPort(), t.pathname = n.getPathname(), t.search = n.getSearch(), t.searchParams = n.getSearchParams(), t.hash = n.getHash())
                },
                Ge = Ie.prototype,
                Ke = function(e, t) {
                    return {
                        get: function() {
                            return R(this)[e]()
                        },
                        set: t && function(e) {
                            return R(this)[t](e)
                        },
                        configurable: !0,
                        enumerable: !0
                    }
                };
            if (a && (f(Ge, "href", Ke("serialize", "setHref")), f(Ge, "origin", Ke("getOrigin")), f(Ge, "protocol", Ke("getProtocol", "setProtocol")), f(Ge, "username", Ke("getUsername", "setUsername")), f(Ge, "password", Ke("getPassword", "setPassword")), f(Ge, "host", Ke("getHost", "setHost")), f(Ge, "hostname", Ke("getHostname", "setHostname")), f(Ge, "port", Ke("getPort", "setPort")), f(Ge, "pathname", Ke("getPathname", "setPathname")), f(Ge, "search", Ke("getSearch", "setSearch")), f(Ge, "searchParams", Ke("getSearchParams")), f(Ge, "hash", Ke("getHash", "setHash"))), c(Ge, "toJSON", (function() {
                    return R(this).serialize()
                }), {
                    enumerable: !0
                }), c(Ge, "toString", (function() {
                    return R(this).serialize()
                }), {
                    enumerable: !0
                }), P) {
                var De = P.createObjectURL,
                    Je = P.revokeObjectURL;
                De && c(Ie, "createObjectURL", l(De, P)), Je && c(Ie, "revokeObjectURL", l(Je, P))
            }
            S(Ie, "URL"), i({
                global: !0,
                constructor: !0,
                forced: !o,
                sham: !a
            }, {
                URL: Ie
            })
        
