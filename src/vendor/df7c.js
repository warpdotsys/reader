// chunk-vendors webpack 模块 df7c（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            (function(e) {
                function n(e, t) {
                    for (var n = 0, r = e.length - 1; r >= 0; r--) {
                        var i = e[r];
                        "." === i ? e.splice(r, 1) : ".." === i ? (e.splice(r, 1), n++) : n && (e.splice(r, 1), n--)
                    }
                    if (t)
                        for (; n--; n) e.unshift("..");
                    return e
                }

                function r(e) {
                    "string" !== typeof e && (e += "");
                    var t, n = 0,
                        r = -1,
                        i = !0;
                    for (t = e.length - 1; t >= 0; --t)
                        if (47 === e.charCodeAt(t)) {
                            if (!i) {
                                n = t + 1;
                                break
                            }
                        } else - 1 === r && (i = !1, r = t + 1);
                    return -1 === r ? "" : e.slice(n, r)
                }

                function i(e, t) {
                    if (e.filter) return e.filter(t);
                    for (var n = [], r = 0; r < e.length; r++) t(e[r], r, e) && n.push(e[r]);
                    return n
                }
                t.resolve = function() {
                    for (var t = "", r = !1, o = arguments.length - 1; o >= -1 && !r; o--) {
                        var a = o >= 0 ? arguments[o] : e.cwd();
                        if ("string" !== typeof a) throw new TypeError("Arguments to path.resolve must be strings");
                        a && (t = a + "/" + t, r = "/" === a.charAt(0))
                    }
                    return t = n(i(t.split("/"), (function(e) {
                        return !!e
                    })), !r).join("/"), (r ? "/" : "") + t || "."
                }, t.normalize = function(e) {
                    var r = t.isAbsolute(e),
                        a = "/" === o(e, -1);
                    return e = n(i(e.split("/"), (function(e) {
                        return !!e
                    })), !r).join("/"), e || r || (e = "."), e && a && (e += "/"), (r ? "/" : "") + e
                }, t.isAbsolute = function(e) {
                    return "/" === e.charAt(0)
                }, t.join = function() {
                    var e = Array.prototype.slice.call(arguments, 0);
                    return t.normalize(i(e, (function(e, t) {
                        if ("string" !== typeof e) throw new TypeError("Arguments to path.join must be strings");
                        return e
                    })).join("/"))
                }, t.relative = function(e, n) {
                    function r(e) {
                        for (var t = 0; t < e.length; t++)
                            if ("" !== e[t]) break;
                        for (var n = e.length - 1; n >= 0; n--)
                            if ("" !== e[n]) break;
                        return t > n ? [] : e.slice(t, n - t + 1)
                    }
                    e = t.resolve(e).substr(1), n = t.resolve(n).substr(1);
                    for (var i = r(e.split("/")), o = r(n.split("/")), a = Math.min(i.length, o.length), s = a, u = 0; u < a; u++)
                        if (i[u] !== o[u]) {
                            s = u;
                            break
                        } var c = [];
                    for (u = s; u < i.length; u++) c.push("..");
                    return c = c.concat(o.slice(s)), c.join("/")
                }, t.sep = "/", t.delimiter = ":", t.dirname = function(e) {
                    if ("string" !== typeof e && (e += ""), 0 === e.length) return ".";
                    for (var t = e.charCodeAt(0), n = 47 === t, r = -1, i = !0, o = e.length - 1; o >= 1; --o)
                        if (t = e.charCodeAt(o), 47 === t) {
                            if (!i) {
                                r = o;
                                break
                            }
                        } else i = !1;
                    return -1 === r ? n ? "/" : "." : n && 1 === r ? "/" : e.slice(0, r)
                }, t.basename = function(e, t) {
                    var n = r(e);
                    return t && n.substr(-1 * t.length) === t && (n = n.substr(0, n.length - t.length)), n
                }, t.extname = function(e) {
                    "string" !== typeof e && (e += "");
                    for (var t = -1, n = 0, r = -1, i = !0, o = 0, a = e.length - 1; a >= 0; --a) {
                        var s = e.charCodeAt(a);
                        if (47 !== s) - 1 === r && (i = !1, r = a + 1), 46 === s ? -1 === t ? t = a : 1 !== o && (o = 1) : -1 !== t && (o = -1);
                        else if (!i) {
                            n = a + 1;
                            break
                        }
                    }
                    return -1 === t || -1 === r || 0 === o || 1 === o && t === r - 1 && t === n + 1 ? "" : e.slice(t, r)
                };
                var o = "b" === "ab".substr(-1) ? function(e, t, n) {
                    return e.substr(t, n)
                } : function(e, t, n) {
                    return t < 0 && (t = e.length + t), e.substr(t, n)
                }
            }).call(this, n("./4362.js"))
        
