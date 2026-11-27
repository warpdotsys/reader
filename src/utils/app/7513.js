// 恢复自 reader-pro-3.2.14 前端产物 beauty/app.js 的 webpack 模块 7513（逐字切片，形参 e, t, o）
const e = module;
const t = module.exports;
const o = require;

        "use strict";
        o.d(t, "d", (function() {
            return s
        })), o.d(t, "a", (function() {
            return c
        })), o.d(t, "g", (function() {
            return l
        })), o.d(t, "b", (function() {
            return u
        })), o.d(t, "i", (function() {
            return f
        })), o.d(t, "e", (function() {
            return d
        })), o.d(t, "c", (function() {
            return p
        })), o.d(t, "f", (function() {
            return h
        })), o.d(t, "h", (function() {
            return m
        }));
        var n = o("53ca"),
            r = o("c7eb"),
            a = o("1da1"),
            i = (o("d9e2"), o("d401"), o("99af"), o("14d9"), o("b680"), o("63f0")),
            s = function(e, t) {
                if (null == e || "" == e) return "0 Bytes";
                var o = new Array("Bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"),
                    n = 0;
                n = Math.floor(Math.log(e) / Math.log(1024));
                var r = e / Math.pow(1024, n);
                return r = r.toFixed(t || 2), r + " " + o[n]
            },
            c = function(e, t) {
                var o = 0,
                    n = [];

                function i() {
                    return s.apply(this, arguments)
                }

                function s() {
                    return s = Object(a["a"])(Object(r["a"])().mark((function e() {
                        var a, s, l;
                        return Object(r["a"])().wrap((function(e) {
                            while (1) switch (e.prev = e.next) {
                                case 0:
                                    return e.prev = 0, ++o, c.leftCount = n.length, l = n.shift(), e.next = 6, l();
                                case 6:
                                    s = e.sent, e.next = 13;
                                    break;
                                case 9:
                                    e.prev = 9, e.t0 = e["catch"](0), a = e.t0, c.errorCount++;
                                case 13:
                                    return e.prev = 13, --o, c.requestCount++, c.leftCount = n.length, t && t(c, s, a), n.length > 0 && i(), e.finish(13);
                                case 20:
                                case "end":
                                    return e.stop()
                            }
                        }), e, null, [
                            [0, 9, 13, 20]
                        ])
                    }))), s.apply(this, arguments)
                }
                var c = function(t) {
                    t && t instanceof Function && (n.push(t), c.leftCount = n.length, o < e && i())
                };
                return c.requestCount = 0, c.leftCount = 0, c.errorCount = 0, c.cancel = function() {
                    n = [], c.leftCount = 0
                }, c.isEnd = function() {
                    return !c.leftCount && !o
                }, c
            },
            l = function() {
                var e = Object(a["a"])(Object(r["a"])().mark((function e(t, o) {
                    var a;
                    return Object(r["a"])().wrap((function(e) {
                        while (1) switch (e.prev = e.next) {
                            case 0:
                                return o = "localCache@" + o, e.next = 3, t()["catch"]((function() {
                                    return window.$cacheStorage.getItem(o).then((function(e) {
                                        if (e) return "object" === Object(n["a"])(e) && (e.isCache = !0), {
                                            data: e
                                        }
                                    }))["catch"]((function(e) {
                                        var t = Object(i["b"])(o);
                                        if (t) return {
                                            data: t
                                        };
                                        throw e
                                    }))
                                }));
                            case 3:
                                return a = e.sent, a.data && a.data.isSuccess && window.$cacheStorage.setItem(o, a.data)["catch"]((function() {})), e.abrupt("return", a);
                            case 6:
                            case "end":
                                return e.stop()
                        }
                    }), e)
                })));
                return function(t, o) {
                    return e.apply(this, arguments)
                }
            }(),
            u = function() {
                var e = Object(a["a"])(Object(r["a"])().mark((function e(t, o, a) {
                    var s, c;
                    return Object(r["a"])().wrap((function(e) {
                        while (1) switch (e.prev = e.next) {
                            case 0:
                                if (o = "localCache@" + o, !0 === a) {
                                    e.next = 8;
                                    break
                                }
                                return e.next = 4, window.$cacheStorage.getItem(o).then((function(e) {
                                    if (e) return "object" === Object(n["a"])(e) && (e.isCache = !0), e;
                                    throw new Error("Cache not found")
                                }))["catch"]((function() {
                                    var e = Object(i["b"])(o);
                                    return e
                                }));
                            case 4:
                                if (s = e.sent, !s) {
                                    e.next = 8;
                                    break
                                }
                                if (!(!a || a && a(s))) {
                                    e.next = 8;
                                    break
                                }
                                return e.abrupt("return", {
                                    data: s
                                });
                            case 8:
                                return e.next = 10, t();
                            case 10:
                                return c = e.sent, c.data && c.data.isSuccess && window.$cacheStorage.setItem(o, c.data)["catch"]((function() {})), e.abrupt("return", c);
                            case 13:
                            case "end":
                                return e.stop()
                        }
                    }), e)
                })));
                return function(t, o, n) {
                    return e.apply(this, arguments)
                }
            }(),
            f = function() {
                var e = Object(a["a"])(Object(r["a"])().mark((function e(t, o, s, c) {
                    var l, u;
                    return Object(r["a"])().wrap((function(e) {
                        while (1) switch (e.prev = e.next) {
                            case 0:
                                if (o = "localCache@" + o, l = function() {
                                        var e = Object(a["a"])(Object(r["a"])().mark((function e() {
                                            var n;
                                            return Object(r["a"])().wrap((function(e) {
                                                while (1) switch (e.prev = e.next) {
                                                    case 0:
                                                        return e.next = 2, t();
                                                    case 2:
                                                        return n = e.sent, n.data && n.data.isSuccess && window.$cacheStorage.setItem(o, n.data)["catch"]((function() {})), e.abrupt("return", n);
                                                    case 5:
                                                    case "end":
                                                        return e.stop()
                                                }
                                            }), e)
                                        })));
                                        return function() {
                                            return e.apply(this, arguments)
                                        }
                                    }(), !0 === s) {
                                    e.next = 10;
                                    break
                                }
                                return e.next = 5, window.$cacheStorage.getItem(o).then((function(e) {
                                    if (e) return "object" === Object(n["a"])(e) && (e.isCache = !0), e;
                                    throw new Error("Cache not found")
                                }))["catch"]((function() {
                                    var e = Object(i["b"])(o);
                                    return e
                                }));
                            case 5:
                                if (u = e.sent, !u) {
                                    e.next = 10;
                                    break
                                }
                                if (!(!s || s && s(u))) {
                                    e.next = 10;
                                    break
                                }
                                return l().then(c || function() {}), e.abrupt("return", {
                                    data: u
                                });
                            case 10:
                                return e.next = 12, l();
                            case 12:
                                return e.abrupt("return", e.sent);
                            case 13:
                            case "end":
                                return e.stop()
                        }
                    }), e)
                })));
                return function(t, o, n, r) {
                    return e.apply(this, arguments)
                }
            }(),
            d = function() {
                return window.innerWidth <= 750
            },
            p = function(e, t) {
                if (e === t) return 1;
                if (!e || !t) return 0;
                for (var o = new Array(e.length + 1), n = 0; n <= e.length; n++) o[n] = new Array(t.length + 1);
                for (var r = 0; r <= e.length; r++)
                    for (var a = 0; a <= t.length; a++) 0 === r ? o[0][a] = a : 0 === a ? o[r][0] = r : e.charAt(r - 1) === t.charAt(a - 1) ? o[r][a] = o[r - 1][a - 1] : o[r][a] = 1 + Math.min(o[r - 1][a - 1], Math.min(o[r][a - 1], o[r - 1][a]));
                return 1 - o[e.length][t.length] / Math.max(e.length, t.length)
            },
            h = function(e, t) {
                if (window.customFonts = window.customFonts || {}, !window.customFonts[e] || window.customFonts[e] !== t) {
                    var o = document.createElement("style");
                    o.textContent = '\n    @font-face {\n      font-family: "'.concat(e, '";\n      src: url("').concat(t, '");\n    }'), o.id = "custom-font-" + e, document.head.appendChild(o), window.customFonts[e] = t
                }
            },
            m = function(e) {
                window.customFonts = window.customFonts || {}, delete window.customFonts[e];
                for (var t = document.querySelectorAll("#custom-font-" + e), o = 0; o < t.length; o++) {
                    var n = t[o];
                    n.remove()
                }
            }
    
