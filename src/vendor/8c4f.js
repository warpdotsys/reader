// chunk-vendors webpack 模块 8c4f（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";

            function r(e, t) {
                for (var n in t) e[n] = t[n];
                return e
            }
            n.d(t, "a", (function() {
                return xt
            }));
            var i = /[!'()*]/g,
                o = function(e) {
                    return "%" + e.charCodeAt(0).toString(16)
                },
                a = /%2C/g,
                s = function(e) {
                    return encodeURIComponent(e).replace(i, o).replace(a, ",")
                };

            function u(e) {
                try {
                    return decodeURIComponent(e)
                } catch (t) {
                    0
                }
                return e
            }

            function c(e, t, n) {
                void 0 === t && (t = {});
                var r, i = n || f;
                try {
                    r = i(e || "")
                } catch (s) {
                    r = {}
                }
                for (var o in t) {
                    var a = t[o];
                    r[o] = Array.isArray(a) ? a.map(l) : l(a)
                }
                return r
            }
            var l = function(e) {
                return null == e || "object" === typeof e ? e : String(e)
            };

            function f(e) {
                var t = {};
                return e = e.trim().replace(/^(\?|#|&)/, ""), e ? (e.split("&").forEach((function(e) {
                    var n = e.replace(/\+/g, " ").split("="),
                        r = u(n.shift()),
                        i = n.length > 0 ? u(n.join("=")) : null;
                    void 0 === t[r] ? t[r] = i : Array.isArray(t[r]) ? t[r].push(i) : t[r] = [t[r], i]
                })), t) : t
            }

            function d(e) {
                var t = e ? Object.keys(e).map((function(t) {
                    var n = e[t];
                    if (void 0 === n) return "";
                    if (null === n) return s(t);
                    if (Array.isArray(n)) {
                        var r = [];
                        return n.forEach((function(e) {
                            void 0 !== e && (null === e ? r.push(s(t)) : r.push(s(t) + "=" + s(e)))
                        })), r.join("&")
                    }
                    return s(t) + "=" + s(n)
                })).filter((function(e) {
                    return e.length > 0
                })).join("&") : null;
                return t ? "?" + t : ""
            }
            var h = /\/?$/;

            function p(e, t, n, r) {
                var i = r && r.options.stringifyQuery,
                    o = t.query || {};
                try {
                    o = v(o)
                } catch (s) {}
                var a = {
                    name: t.name || e && e.name,
                    meta: e && e.meta || {},
                    path: t.path || "/",
                    hash: t.hash || "",
                    query: o,
                    params: t.params || {},
                    fullPath: b(t, i),
                    matched: e ? g(e) : []
                };
                return n && (a.redirectedFrom = b(n, i)), Object.freeze(a)
            }

            function v(e) {
                if (Array.isArray(e)) return e.map(v);
                if (e && "object" === typeof e) {
                    var t = {};
                    for (var n in e) t[n] = v(e[n]);
                    return t
                }
                return e
            }
            var m = p(null, {
                path: "/"
            });

            function g(e) {
                var t = [];
                while (e) t.unshift(e), e = e.parent;
                return t
            }

            function b(e, t) {
                var n = e.path,
                    r = e.query;
                void 0 === r && (r = {});
                var i = e.hash;
                void 0 === i && (i = "");
                var o = t || d;
                return (n || "/") + o(r) + i
            }

            function y(e, t, n) {
                return t === m ? e === t : !!t && (e.path && t.path ? e.path.replace(h, "") === t.path.replace(h, "") && (n || e.hash === t.hash && _(e.query, t.query)) : !(!e.name || !t.name) && (e.name === t.name && (n || e.hash === t.hash && _(e.query, t.query) && _(e.params, t.params))))
            }

            function _(e, t) {
                if (void 0 === e && (e = {}), void 0 === t && (t = {}), !e || !t) return e === t;
                var n = Object.keys(e).sort(),
                    r = Object.keys(t).sort();
                return n.length === r.length && n.every((function(n, i) {
                    var o = e[n],
                        a = r[i];
                    if (a !== n) return !1;
                    var s = t[n];
                    return null == o || null == s ? o === s : "object" === typeof o && "object" === typeof s ? _(o, s) : String(o) === String(s)
                }))
            }

            function w(e, t) {
                return 0 === e.path.replace(h, "/").indexOf(t.path.replace(h, "/")) && (!t.hash || e.hash === t.hash) && x(e.query, t.query)
            }

            function x(e, t) {
                for (var n in t)
                    if (!(n in e)) return !1;
                return !0
            }

            function C(e) {
                for (var t = 0; t < e.matched.length; t++) {
                    var n = e.matched[t];
                    for (var r in n.instances) {
                        var i = n.instances[r],
                            o = n.enteredCbs[r];
                        if (i && o) {
                            delete n.enteredCbs[r];
                            for (var a = 0; a < o.length; a++) i._isBeingDestroyed || o[a](i)
                        }
                    }
                }
            }
            var S = {
                name: "RouterView",
                functional: !0,
                props: {
                    name: {
                        type: String,
                        default: "default"
                    }
                },
                render: function(e, t) {
                    var n = t.props,
                        i = t.children,
                        o = t.parent,
                        a = t.data;
                    a.routerView = !0;
                    var s = o.$createElement,
                        u = n.name,
                        c = o.$route,
                        l = o._routerViewCache || (o._routerViewCache = {}),
                        f = 0,
                        d = !1;
                    while (o && o._routerRoot !== o) {
                        var h = o.$vnode ? o.$vnode.data : {};
                        h.routerView && f++, h.keepAlive && o._directInactive && o._inactive && (d = !0), o = o.$parent
                    }
                    if (a.routerViewDepth = f, d) {
                        var p = l[u],
                            v = p && p.component;
                        return v ? (p.configProps && O(v, a, p.route, p.configProps), s(v, a, i)) : s()
                    }
                    var m = c.matched[f],
                        g = m && m.components[u];
                    if (!m || !g) return l[u] = null, s();
                    l[u] = {
                        component: g
                    }, a.registerRouteInstance = function(e, t) {
                        var n = m.instances[u];
                        (t && n !== e || !t && n === e) && (m.instances[u] = t)
                    }, (a.hook || (a.hook = {})).prepatch = function(e, t) {
                        m.instances[u] = t.componentInstance
                    }, a.hook.init = function(e) {
                        e.data.keepAlive && e.componentInstance && e.componentInstance !== m.instances[u] && (m.instances[u] = e.componentInstance), C(c)
                    };
                    var b = m.props && m.props[u];
                    return b && (r(l[u], {
                        route: c,
                        configProps: b
                    }), O(g, a, c, b)), s(g, a, i)
                }
            };

            function O(e, t, n, i) {
                var o = t.props = E(n, i);
                if (o) {
                    o = t.props = r({}, o);
                    var a = t.attrs = t.attrs || {};
                    for (var s in o) e.props && s in e.props || (a[s] = o[s], delete o[s])
                }
            }

            function E(e, t) {
                switch (typeof t) {
                    case "undefined":
                        return;
                    case "object":
                        return t;
                    case "function":
                        return t(e);
                    case "boolean":
                        return t ? e.params : void 0;
                    default:
                        0
                }
            }

            function k(e, t, n) {
                var r = e.charAt(0);
                if ("/" === r) return e;
                if ("?" === r || "#" === r) return t + e;
                var i = t.split("/");
                n && i[i.length - 1] || i.pop();
                for (var o = e.replace(/^\//, "").split("/"), a = 0; a < o.length; a++) {
                    var s = o[a];
                    ".." === s ? i.pop() : "." !== s && i.push(s)
                }
                return "" !== i[0] && i.unshift(""), i.join("/")
            }

            function T(e) {
                var t = "",
                    n = "",
                    r = e.indexOf("#");
                r >= 0 && (t = e.slice(r), e = e.slice(0, r));
                var i = e.indexOf("?");
                return i >= 0 && (n = e.slice(i + 1), e = e.slice(0, i)), {
                    path: e,
                    query: n,
                    hash: t
                }
            }

            function j(e) {
                return e.replace(/\/(?:\s*\/)+/g, "/")
            }
            var $ = Array.isArray || function(e) {
                    return "[object Array]" == Object.prototype.toString.call(e)
                },
                A = K,
                P = R,
                I = F,
                N = z,
                M = Y,
                L = new RegExp(["(\\\\.)", "([\\/.])?(?:(?:\\:(\\w+)(?:\\(((?:\\\\.|[^\\\\()])+)\\))?|\\(((?:\\\\.|[^\\\\()])+)\\))([+*?])?|(\\*))"].join("|"), "g");

            function R(e, t) {
                var n, r = [],
                    i = 0,
                    o = 0,
                    a = "",
                    s = t && t.delimiter || "/";
                while (null != (n = L.exec(e))) {
                    var u = n[0],
                        c = n[1],
                        l = n.index;
                    if (a += e.slice(o, l), o = l + u.length, c) a += c[1];
                    else {
                        var f = e[o],
                            d = n[2],
                            h = n[3],
                            p = n[4],
                            v = n[5],
                            m = n[6],
                            g = n[7];
                        a && (r.push(a), a = "");
                        var b = null != d && null != f && f !== d,
                            y = "+" === m || "*" === m,
                            _ = "?" === m || "*" === m,
                            w = n[2] || s,
                            x = p || v;
                        r.push({
                            name: h || i++,
                            prefix: d || "",
                            delimiter: w,
                            optional: _,
                            repeat: y,
                            partial: b,
                            asterisk: !!g,
                            pattern: x ? H(x) : g ? ".*" : "[^" + V(w) + "]+?"
                        })
                    }
                }
                return o < e.length && (a += e.substr(o)), a && r.push(a), r
            }

            function F(e, t) {
                return z(R(e, t), t)
            }

            function D(e) {
                return encodeURI(e).replace(/[\/?#]/g, (function(e) {
                    return "%" + e.charCodeAt(0).toString(16).toUpperCase()
                }))
            }

            function B(e) {
                return encodeURI(e).replace(/[?#]/g, (function(e) {
                    return "%" + e.charCodeAt(0).toString(16).toUpperCase()
                }))
            }

            function z(e, t) {
                for (var n = new Array(e.length), r = 0; r < e.length; r++) "object" === typeof e[r] && (n[r] = new RegExp("^(?:" + e[r].pattern + ")$", q(t)));
                return function(t, r) {
                    for (var i = "", o = t || {}, a = r || {}, s = a.pretty ? D : encodeURIComponent, u = 0; u < e.length; u++) {
                        var c = e[u];
                        if ("string" !== typeof c) {
                            var l, f = o[c.name];
                            if (null == f) {
                                if (c.optional) {
                                    c.partial && (i += c.prefix);
                                    continue
                                }
                                throw new TypeError('Expected "' + c.name + '" to be defined')
                            }
                            if ($(f)) {
                                if (!c.repeat) throw new TypeError('Expected "' + c.name + '" to not repeat, but received `' + JSON.stringify(f) + "`");
                                if (0 === f.length) {
                                    if (c.optional) continue;
                                    throw new TypeError('Expected "' + c.name + '" to not be empty')
                                }
                                for (var d = 0; d < f.length; d++) {
                                    if (l = s(f[d]), !n[u].test(l)) throw new TypeError('Expected all "' + c.name + '" to match "' + c.pattern + '", but received `' + JSON.stringify(l) + "`");
                                    i += (0 === d ? c.prefix : c.delimiter) + l
                                }
                            } else {
                                if (l = c.asterisk ? B(f) : s(f), !n[u].test(l)) throw new TypeError('Expected "' + c.name + '" to match "' + c.pattern + '", but received "' + l + '"');
                                i += c.prefix + l
                            }
                        } else i += c
                    }
                    return i
                }
            }

            function V(e) {
                return e.replace(/([.+*?=^!:${}()[\]|\/\\])/g, "\\$1")
            }

            function H(e) {
                return e.replace(/([=!:$\/()])/g, "\\$1")
            }

            function W(e, t) {
                return e.keys = t, e
            }

            function q(e) {
                return e && e.sensitive ? "" : "i"
            }

            function U(e, t) {
                var n = e.source.match(/\((?!\?)/g);
                if (n)
                    for (var r = 0; r < n.length; r++) t.push({
                        name: r,
                        prefix: null,
                        delimiter: null,
                        optional: !1,
                        repeat: !1,
                        partial: !1,
                        asterisk: !1,
                        pattern: null
                    });
                return W(e, t)
            }

            function X(e, t, n) {
                for (var r = [], i = 0; i < e.length; i++) r.push(K(e[i], t, n).source);
                var o = new RegExp("(?:" + r.join("|") + ")", q(n));
                return W(o, t)
            }

            function G(e, t, n) {
                return Y(R(e, n), t, n)
            }

            function Y(e, t, n) {
                $(t) || (n = t || n, t = []), n = n || {};
                for (var r = n.strict, i = !1 !== n.end, o = "", a = 0; a < e.length; a++) {
                    var s = e[a];
                    if ("string" === typeof s) o += V(s);
                    else {
                        var u = V(s.prefix),
                            c = "(?:" + s.pattern + ")";
                        t.push(s), s.repeat && (c += "(?:" + u + c + ")*"), c = s.optional ? s.partial ? u + "(" + c + ")?" : "(?:" + u + "(" + c + "))?" : u + "(" + c + ")", o += c
                    }
                }
                var l = V(n.delimiter || "/"),
                    f = o.slice(-l.length) === l;
                return r || (o = (f ? o.slice(0, -l.length) : o) + "(?:" + l + "(?=$))?"), o += i ? "$" : r && f ? "" : "(?=" + l + "|$)", W(new RegExp("^" + o, q(n)), t)
            }

            function K(e, t, n) {
                return $(t) || (n = t || n, t = []), n = n || {}, e instanceof RegExp ? U(e, t) : $(e) ? X(e, t, n) : G(e, t, n)
            }
            A.parse = P, A.compile = I, A.tokensToFunction = N, A.tokensToRegExp = M;
            var J = Object.create(null);

            function Z(e, t, n) {
                t = t || {};
                try {
                    var r = J[e] || (J[e] = A.compile(e));
                    return "string" === typeof t.pathMatch && (t[0] = t.pathMatch), r(t, {
                        pretty: !0
                    })
                } catch (i) {
                    return ""
                } finally {
                    delete t[0]
                }
            }

            function Q(e, t, n, i) {
                var o = "string" === typeof e ? {
                    path: e
                } : e;
                if (o._normalized) return o;
                if (o.name) {
                    o = r({}, e);
                    var a = o.params;
                    return a && "object" === typeof a && (o.params = r({}, a)), o
                }
                if (!o.path && o.params && t) {
                    o = r({}, o), o._normalized = !0;
                    var s = r(r({}, t.params), o.params);
                    if (t.name) o.name = t.name, o.params = s;
                    else if (t.matched.length) {
                        var u = t.matched[t.matched.length - 1].path;
                        o.path = Z(u, s, "path " + t.path)
                    } else 0;
                    return o
                }
                var l = T(o.path || ""),
                    f = t && t.path || "/",
                    d = l.path ? k(l.path, f, n || o.append) : f,
                    h = c(l.query, o.query, i && i.options.parseQuery),
                    p = o.hash || l.hash;
                return p && "#" !== p.charAt(0) && (p = "#" + p), {
                    _normalized: !0,
                    path: d,
                    query: h,
                    hash: p
                }
            }
            var ee, te = [String, Object],
                ne = [String, Array],
                re = function() {},
                ie = {
                    name: "RouterLink",
                    props: {
                        to: {
                            type: te,
                            required: !0
                        },
                        tag: {
                            type: String,
                            default: "a"
                        },
                        custom: Boolean,
                        exact: Boolean,
                        exactPath: Boolean,
                        append: Boolean,
                        replace: Boolean,
                        activeClass: String,
                        exactActiveClass: String,
                        ariaCurrentValue: {
                            type: String,
                            default: "page"
                        },
                        event: {
                            type: ne,
                            default: "click"
                        }
                    },
                    render: function(e) {
                        var t = this,
                            n = this.$router,
                            i = this.$route,
                            o = n.resolve(this.to, i, this.append),
                            a = o.location,
                            s = o.route,
                            u = o.href,
                            c = {},
                            l = n.options.linkActiveClass,
                            f = n.options.linkExactActiveClass,
                            d = null == l ? "router-link-active" : l,
                            h = null == f ? "router-link-exact-active" : f,
                            v = null == this.activeClass ? d : this.activeClass,
                            m = null == this.exactActiveClass ? h : this.exactActiveClass,
                            g = s.redirectedFrom ? p(null, Q(s.redirectedFrom), null, n) : s;
                        c[m] = y(i, g, this.exactPath), c[v] = this.exact || this.exactPath ? c[m] : w(i, g);
                        var b = c[m] ? this.ariaCurrentValue : null,
                            _ = function(e) {
                                oe(e) && (t.replace ? n.replace(a, re) : n.push(a, re))
                            },
                            x = {
                                click: oe
                            };
                        Array.isArray(this.event) ? this.event.forEach((function(e) {
                            x[e] = _
                        })) : x[this.event] = _;
                        var C = {
                                class: c
                            },
                            S = !this.$scopedSlots.$hasNormal && this.$scopedSlots.default && this.$scopedSlots.default({
                                href: u,
                                route: s,
                                navigate: _,
                                isActive: c[v],
                                isExactActive: c[m]
                            });
                        if (S) {
                            if (1 === S.length) return S[0];
                            if (S.length > 1 || !S.length) return 0 === S.length ? e() : e("span", {}, S)
                        }
                        if ("a" === this.tag) C.on = x, C.attrs = {
                            href: u,
                            "aria-current": b
                        };
                        else {
                            var O = ae(this.$slots.default);
                            if (O) {
                                O.isStatic = !1;
                                var E = O.data = r({}, O.data);
                                for (var k in E.on = E.on || {}, E.on) {
                                    var T = E.on[k];
                                    k in x && (E.on[k] = Array.isArray(T) ? T : [T])
                                }
                                for (var j in x) j in E.on ? E.on[j].push(x[j]) : E.on[j] = _;
                                var $ = O.data.attrs = r({}, O.data.attrs);
                                $.href = u, $["aria-current"] = b
                            } else C.on = x
                        }
                        return e(this.tag, C, this.$slots.default)
                    }
                };

            function oe(e) {
                if (!(e.metaKey || e.altKey || e.ctrlKey || e.shiftKey) && !e.defaultPrevented && (void 0 === e.button || 0 === e.button)) {
                    if (e.currentTarget && e.currentTarget.getAttribute) {
                        var t = e.currentTarget.getAttribute("target");
                        if (/\b_blank\b/i.test(t)) return
                    }
                    return e.preventDefault && e.preventDefault(), !0
                }
            }

            function ae(e) {
                if (e)
                    for (var t, n = 0; n < e.length; n++) {
                        if (t = e[n], "a" === t.tag) return t;
                        if (t.children && (t = ae(t.children))) return t
                    }
            }

            function se(e) {
                if (!se.installed || ee !== e) {
                    se.installed = !0, ee = e;
                    var t = function(e) {
                            return void 0 !== e
                        },
                        n = function(e, n) {
                            var r = e.$options._parentVnode;
                            t(r) && t(r = r.data) && t(r = r.registerRouteInstance) && r(e, n)
                        };
                    e.mixin({
                        beforeCreate: function() {
                            t(this.$options.router) ? (this._routerRoot = this, this._router = this.$options.router, this._router.init(this), e.util.defineReactive(this, "_route", this._router.history.current)) : this._routerRoot = this.$parent && this.$parent._routerRoot || this, n(this, this)
                        },
                        destroyed: function() {
                            n(this)
                        }
                    }), Object.defineProperty(e.prototype, "$router", {
                        get: function() {
                            return this._routerRoot._router
                        }
                    }), Object.defineProperty(e.prototype, "$route", {
                        get: function() {
                            return this._routerRoot._route
                        }
                    }), e.component("RouterView", S), e.component("RouterLink", ie);
                    var r = e.config.optionMergeStrategies;
                    r.beforeRouteEnter = r.beforeRouteLeave = r.beforeRouteUpdate = r.created
                }
            }
            var ue = "undefined" !== typeof window;

            function ce(e, t, n, r, i) {
                var o = t || [],
                    a = n || Object.create(null),
                    s = r || Object.create(null);
                e.forEach((function(e) {
                    le(o, a, s, e, i)
                }));
                for (var u = 0, c = o.length; u < c; u++) "*" === o[u] && (o.push(o.splice(u, 1)[0]), c--, u--);
                return {
                    pathList: o,
                    pathMap: a,
                    nameMap: s
                }
            }

            function le(e, t, n, r, i, o) {
                var a = r.path,
                    s = r.name;
                var u = r.pathToRegexpOptions || {},
                    c = de(a, i, u.strict);
                "boolean" === typeof r.caseSensitive && (u.sensitive = r.caseSensitive);
                var l = {
                    path: c,
                    regex: fe(c, u),
                    components: r.components || {
                        default: r.component
                    },
                    alias: r.alias ? "string" === typeof r.alias ? [r.alias] : r.alias : [],
                    instances: {},
                    enteredCbs: {},
                    name: s,
                    parent: i,
                    matchAs: o,
                    redirect: r.redirect,
                    beforeEnter: r.beforeEnter,
                    meta: r.meta || {},
                    props: null == r.props ? {} : r.components ? r.props : {
                        default: r.props
                    }
                };
                if (r.children && r.children.forEach((function(r) {
                        var i = o ? j(o + "/" + r.path) : void 0;
                        le(e, t, n, r, l, i)
                    })), t[l.path] || (e.push(l.path), t[l.path] = l), void 0 !== r.alias)
                    for (var f = Array.isArray(r.alias) ? r.alias : [r.alias], d = 0; d < f.length; ++d) {
                        var h = f[d];
                        0;
                        var p = {
                            path: h,
                            children: r.children
                        };
                        le(e, t, n, p, i, l.path || "/")
                    }
                s && (n[s] || (n[s] = l))
            }

            function fe(e, t) {
                var n = A(e, [], t);
                return n
            }

            function de(e, t, n) {
                return n || (e = e.replace(/\/$/, "")), "/" === e[0] || null == t ? e : j(t.path + "/" + e)
            }

            function he(e, t) {
                var n = ce(e),
                    r = n.pathList,
                    i = n.pathMap,
                    o = n.nameMap;

                function a(e) {
                    ce(e, r, i, o)
                }

                function s(e, t) {
                    var n = "object" !== typeof e ? o[e] : void 0;
                    ce([t || e], r, i, o, n), n && n.alias.length && ce(n.alias.map((function(e) {
                        return {
                            path: e,
                            children: [t]
                        }
                    })), r, i, o, n)
                }

                function u() {
                    return r.map((function(e) {
                        return i[e]
                    }))
                }

                function c(e, n, a) {
                    var s = Q(e, n, !1, t),
                        u = s.name;
                    if (u) {
                        var c = o[u];
                        if (!c) return d(null, s);
                        var l = c.regex.keys.filter((function(e) {
                            return !e.optional
                        })).map((function(e) {
                            return e.name
                        }));
                        if ("object" !== typeof s.params && (s.params = {}), n && "object" === typeof n.params)
                            for (var f in n.params) !(f in s.params) && l.indexOf(f) > -1 && (s.params[f] = n.params[f]);
                        return s.path = Z(c.path, s.params, 'named route "' + u + '"'), d(c, s, a)
                    }
                    if (s.path) {
                        s.params = {};
                        for (var h = 0; h < r.length; h++) {
                            var p = r[h],
                                v = i[p];
                            if (pe(v.regex, s.path, s.params)) return d(v, s, a)
                        }
                    }
                    return d(null, s)
                }

                function l(e, n) {
                    var r = e.redirect,
                        i = "function" === typeof r ? r(p(e, n, null, t)) : r;
                    if ("string" === typeof i && (i = {
                            path: i
                        }), !i || "object" !== typeof i) return d(null, n);
                    var a = i,
                        s = a.name,
                        u = a.path,
                        l = n.query,
                        f = n.hash,
                        h = n.params;
                    if (l = a.hasOwnProperty("query") ? a.query : l, f = a.hasOwnProperty("hash") ? a.hash : f, h = a.hasOwnProperty("params") ? a.params : h, s) {
                        o[s];
                        return c({
                            _normalized: !0,
                            name: s,
                            query: l,
                            hash: f,
                            params: h
                        }, void 0, n)
                    }
                    if (u) {
                        var v = ve(u, e),
                            m = Z(v, h, 'redirect route with path "' + v + '"');
                        return c({
                            _normalized: !0,
                            path: m,
                            query: l,
                            hash: f
                        }, void 0, n)
                    }
                    return d(null, n)
                }

                function f(e, t, n) {
                    var r = Z(n, t.params, 'aliased route with path "' + n + '"'),
                        i = c({
                            _normalized: !0,
                            path: r
                        });
                    if (i) {
                        var o = i.matched,
                            a = o[o.length - 1];
                        return t.params = i.params, d(a, t)
                    }
                    return d(null, t)
                }

                function d(e, n, r) {
                    return e && e.redirect ? l(e, r || n) : e && e.matchAs ? f(e, n, e.matchAs) : p(e, n, r, t)
                }
                return {
                    match: c,
                    addRoute: s,
                    getRoutes: u,
                    addRoutes: a
                }
            }

            function pe(e, t, n) {
                var r = t.match(e);
                if (!r) return !1;
                if (!n) return !0;
                for (var i = 1, o = r.length; i < o; ++i) {
                    var a = e.keys[i - 1];
                    a && (n[a.name || "pathMatch"] = "string" === typeof r[i] ? u(r[i]) : r[i])
                }
                return !0
            }

            function ve(e, t) {
                return k(e, t.parent ? t.parent.path : "/", !0)
            }
            var me = ue && window.performance && window.performance.now ? window.performance : Date;

            function ge() {
                return me.now().toFixed(3)
            }
            var be = ge();

            function ye() {
                return be
            }

            function _e(e) {
                return be = e
            }
            var we = Object.create(null);

            function xe() {
                "scrollRestoration" in window.history && (window.history.scrollRestoration = "manual");
                var e = window.location.protocol + "//" + window.location.host,
                    t = window.location.href.replace(e, ""),
                    n = r({}, window.history.state);
                return n.key = ye(), window.history.replaceState(n, "", t), window.addEventListener("popstate", Oe),
                    function() {
                        window.removeEventListener("popstate", Oe)
                    }
            }

            function Ce(e, t, n, r) {
                if (e.app) {
                    var i = e.options.scrollBehavior;
                    i && e.app.$nextTick((function() {
                        var o = Ee(),
                            a = i.call(e, t, n, r ? o : null);
                        a && ("function" === typeof a.then ? a.then((function(e) {
                            Ie(e, o)
                        })).catch((function(e) {
                            0
                        })) : Ie(a, o))
                    }))
                }
            }

            function Se() {
                var e = ye();
                e && (we[e] = {
                    x: window.pageXOffset,
                    y: window.pageYOffset
                })
            }

            function Oe(e) {
                Se(), e.state && e.state.key && _e(e.state.key)
            }

            function Ee() {
                var e = ye();
                if (e) return we[e]
            }

            function ke(e, t) {
                var n = document.documentElement,
                    r = n.getBoundingClientRect(),
                    i = e.getBoundingClientRect();
                return {
                    x: i.left - r.left - t.x,
                    y: i.top - r.top - t.y
                }
            }

            function Te(e) {
                return Ae(e.x) || Ae(e.y)
            }

            function je(e) {
                return {
                    x: Ae(e.x) ? e.x : window.pageXOffset,
                    y: Ae(e.y) ? e.y : window.pageYOffset
                }
            }

            function $e(e) {
                return {
                    x: Ae(e.x) ? e.x : 0,
                    y: Ae(e.y) ? e.y : 0
                }
            }

            function Ae(e) {
                return "number" === typeof e
            }
            var Pe = /^#\d/;

            function Ie(e, t) {
                var n = "object" === typeof e;
                if (n && "string" === typeof e.selector) {
                    var r = Pe.test(e.selector) ? document.getElementById(e.selector.slice(1)) : document.querySelector(e.selector);
                    if (r) {
                        var i = e.offset && "object" === typeof e.offset ? e.offset : {};
                        i = $e(i), t = ke(r, i)
                    } else Te(e) && (t = je(e))
                } else n && Te(e) && (t = je(e));
                t && ("scrollBehavior" in document.documentElement.style ? window.scrollTo({
                    left: t.x,
                    top: t.y,
                    behavior: e.behavior
                }) : window.scrollTo(t.x, t.y))
            }
            var Ne = ue && function() {
                var e = window.navigator.userAgent;
                return (-1 === e.indexOf("Android 2.") && -1 === e.indexOf("Android 4.0") || -1 === e.indexOf("Mobile Safari") || -1 !== e.indexOf("Chrome") || -1 !== e.indexOf("Windows Phone")) && (window.history && "function" === typeof window.history.pushState)
            }();

            function Me(e, t) {
                Se();
                var n = window.history;
                try {
                    if (t) {
                        var i = r({}, n.state);
                        i.key = ye(), n.replaceState(i, "", e)
                    } else n.pushState({
                        key: _e(ge())
                    }, "", e)
                } catch (o) {
                    window.location[t ? "replace" : "assign"](e)
                }
            }

            function Le(e) {
                Me(e, !0)
            }
            var Re = {
                redirected: 2,
                aborted: 4,
                cancelled: 8,
                duplicated: 16
            };

            function Fe(e, t) {
                return Ve(e, t, Re.redirected, 'Redirected when going from "' + e.fullPath + '" to "' + We(t) + '" via a navigation guard.')
            }

            function De(e, t) {
                var n = Ve(e, t, Re.duplicated, 'Avoided redundant navigation to current location: "' + e.fullPath + '".');
                return n.name = "NavigationDuplicated", n
            }

            function Be(e, t) {
                return Ve(e, t, Re.cancelled, 'Navigation cancelled from "' + e.fullPath + '" to "' + t.fullPath + '" with a new navigation.')
            }

            function ze(e, t) {
                return Ve(e, t, Re.aborted, 'Navigation aborted from "' + e.fullPath + '" to "' + t.fullPath + '" via a navigation guard.')
            }

            function Ve(e, t, n, r) {
                var i = new Error(r);
                return i._isRouter = !0, i.from = e, i.to = t, i.type = n, i
            }
            var He = ["params", "query", "hash"];

            function We(e) {
                if ("string" === typeof e) return e;
                if ("path" in e) return e.path;
                var t = {};
                return He.forEach((function(n) {
                    n in e && (t[n] = e[n])
                })), JSON.stringify(t, null, 2)
            }

            function qe(e) {
                return Object.prototype.toString.call(e).indexOf("Error") > -1
            }

            function Ue(e, t) {
                return qe(e) && e._isRouter && (null == t || e.type === t)
            }

            function Xe(e, t, n) {
                var r = function(i) {
                    i >= e.length ? n() : e[i] ? t(e[i], (function() {
                        r(i + 1)
                    })) : r(i + 1)
                };
                r(0)
            }

            function Ge(e) {
                return function(t, n, r) {
                    var i = !1,
                        o = 0,
                        a = null;
                    Ye(e, (function(e, t, n, s) {
                        if ("function" === typeof e && void 0 === e.cid) {
                            i = !0, o++;
                            var u, c = Qe((function(t) {
                                    Ze(t) && (t = t.default), e.resolved = "function" === typeof t ? t : ee.extend(t), n.components[s] = t, o--, o <= 0 && r()
                                })),
                                l = Qe((function(e) {
                                    var t = "Failed to resolve async component " + s + ": " + e;
                                    a || (a = qe(e) ? e : new Error(t), r(a))
                                }));
                            try {
                                u = e(c, l)
                            } catch (d) {
                                l(d)
                            }
                            if (u)
                                if ("function" === typeof u.then) u.then(c, l);
                                else {
                                    var f = u.component;
                                    f && "function" === typeof f.then && f.then(c, l)
                                }
                        }
                    })), i || r()
                }
            }

            function Ye(e, t) {
                return Ke(e.map((function(e) {
                    return Object.keys(e.components).map((function(n) {
                        return t(e.components[n], e.instances[n], e, n)
                    }))
                })))
            }

            function Ke(e) {
                return Array.prototype.concat.apply([], e)
            }
            var Je = "function" === typeof Symbol && "symbol" === typeof Symbol.toStringTag;

            function Ze(e) {
                return e.__esModule || Je && "Module" === e[Symbol.toStringTag]
            }

            function Qe(e) {
                var t = !1;
                return function() {
                    var n = [],
                        r = arguments.length;
                    while (r--) n[r] = arguments[r];
                    if (!t) return t = !0, e.apply(this, n)
                }
            }
            var et = function(e, t) {
                this.router = e, this.base = tt(t), this.current = m, this.pending = null, this.ready = !1, this.readyCbs = [], this.readyErrorCbs = [], this.errorCbs = [], this.listeners = []
            };

            function tt(e) {
                if (!e)
                    if (ue) {
                        var t = document.querySelector("base");
                        e = t && t.getAttribute("href") || "/", e = e.replace(/^https?:\/\/[^\/]+/, "")
                    } else e = "/";
                return "/" !== e.charAt(0) && (e = "/" + e), e.replace(/\/$/, "")
            }

            function nt(e, t) {
                var n, r = Math.max(e.length, t.length);
                for (n = 0; n < r; n++)
                    if (e[n] !== t[n]) break;
                return {
                    updated: t.slice(0, n),
                    activated: t.slice(n),
                    deactivated: e.slice(n)
                }
            }

            function rt(e, t, n, r) {
                var i = Ye(e, (function(e, r, i, o) {
                    var a = it(e, t);
                    if (a) return Array.isArray(a) ? a.map((function(e) {
                        return n(e, r, i, o)
                    })) : n(a, r, i, o)
                }));
                return Ke(r ? i.reverse() : i)
            }

            function it(e, t) {
                return "function" !== typeof e && (e = ee.extend(e)), e.options[t]
            }

            function ot(e) {
                return rt(e, "beforeRouteLeave", st, !0)
            }

            function at(e) {
                return rt(e, "beforeRouteUpdate", st)
            }

            function st(e, t) {
                if (t) return function() {
                    return e.apply(t, arguments)
                }
            }

            function ut(e) {
                return rt(e, "beforeRouteEnter", (function(e, t, n, r) {
                    return ct(e, n, r)
                }))
            }

            function ct(e, t, n) {
                return function(r, i, o) {
                    return e(r, i, (function(e) {
                        "function" === typeof e && (t.enteredCbs[n] || (t.enteredCbs[n] = []), t.enteredCbs[n].push(e)), o(e)
                    }))
                }
            }
            et.prototype.listen = function(e) {
                this.cb = e
            }, et.prototype.onReady = function(e, t) {
                this.ready ? e() : (this.readyCbs.push(e), t && this.readyErrorCbs.push(t))
            }, et.prototype.onError = function(e) {
                this.errorCbs.push(e)
            }, et.prototype.transitionTo = function(e, t, n) {
                var r, i = this;
                try {
                    r = this.router.match(e, this.current)
                } catch (a) {
                    throw this.errorCbs.forEach((function(e) {
                        e(a)
                    })), a
                }
                var o = this.current;
                this.confirmTransition(r, (function() {
                    i.updateRoute(r), t && t(r), i.ensureURL(), i.router.afterHooks.forEach((function(e) {
                        e && e(r, o)
                    })), i.ready || (i.ready = !0, i.readyCbs.forEach((function(e) {
                        e(r)
                    })))
                }), (function(e) {
                    n && n(e), e && !i.ready && (Ue(e, Re.redirected) && o === m || (i.ready = !0, i.readyErrorCbs.forEach((function(t) {
                        t(e)
                    }))))
                }))
            }, et.prototype.confirmTransition = function(e, t, n) {
                var r = this,
                    i = this.current;
                this.pending = e;
                var o = function(e) {
                        !Ue(e) && qe(e) && (r.errorCbs.length ? r.errorCbs.forEach((function(t) {
                            t(e)
                        })) : console.error(e)), n && n(e)
                    },
                    a = e.matched.length - 1,
                    s = i.matched.length - 1;
                if (y(e, i) && a === s && e.matched[a] === i.matched[s]) return this.ensureURL(), e.hash && Ce(this.router, i, e, !1), o(De(i, e));
                var u = nt(this.current.matched, e.matched),
                    c = u.updated,
                    l = u.deactivated,
                    f = u.activated,
                    d = [].concat(ot(l), this.router.beforeHooks, at(c), f.map((function(e) {
                        return e.beforeEnter
                    })), Ge(f)),
                    h = function(t, n) {
                        if (r.pending !== e) return o(Be(i, e));
                        try {
                            t(e, i, (function(t) {
                                !1 === t ? (r.ensureURL(!0), o(ze(i, e))) : qe(t) ? (r.ensureURL(!0), o(t)) : "string" === typeof t || "object" === typeof t && ("string" === typeof t.path || "string" === typeof t.name) ? (o(Fe(i, e)), "object" === typeof t && t.replace ? r.replace(t) : r.push(t)) : n(t)
                            }))
                        } catch (a) {
                            o(a)
                        }
                    };
                Xe(d, h, (function() {
                    var n = ut(f),
                        a = n.concat(r.router.resolveHooks);
                    Xe(a, h, (function() {
                        if (r.pending !== e) return o(Be(i, e));
                        r.pending = null, t(e), r.router.app && r.router.app.$nextTick((function() {
                            C(e)
                        }))
                    }))
                }))
            }, et.prototype.updateRoute = function(e) {
                this.current = e, this.cb && this.cb(e)
            }, et.prototype.setupListeners = function() {}, et.prototype.teardown = function() {
                this.listeners.forEach((function(e) {
                    e()
                })), this.listeners = [], this.current = m, this.pending = null
            };
            var lt = function(e) {
                function t(t, n) {
                    e.call(this, t, n), this._startLocation = ft(this.base)
                }
                return e && (t.__proto__ = e), t.prototype = Object.create(e && e.prototype), t.prototype.constructor = t, t.prototype.setupListeners = function() {
                    var e = this;
                    if (!(this.listeners.length > 0)) {
                        var t = this.router,
                            n = t.options.scrollBehavior,
                            r = Ne && n;
                        r && this.listeners.push(xe());
                        var i = function() {
                            var n = e.current,
                                i = ft(e.base);
                            e.current === m && i === e._startLocation || e.transitionTo(i, (function(e) {
                                r && Ce(t, e, n, !0)
                            }))
                        };
                        window.addEventListener("popstate", i), this.listeners.push((function() {
                            window.removeEventListener("popstate", i)
                        }))
                    }
                }, t.prototype.go = function(e) {
                    window.history.go(e)
                }, t.prototype.push = function(e, t, n) {
                    var r = this,
                        i = this,
                        o = i.current;
                    this.transitionTo(e, (function(e) {
                        Me(j(r.base + e.fullPath)), Ce(r.router, e, o, !1), t && t(e)
                    }), n)
                }, t.prototype.replace = function(e, t, n) {
                    var r = this,
                        i = this,
                        o = i.current;
                    this.transitionTo(e, (function(e) {
                        Le(j(r.base + e.fullPath)), Ce(r.router, e, o, !1), t && t(e)
                    }), n)
                }, t.prototype.ensureURL = function(e) {
                    if (ft(this.base) !== this.current.fullPath) {
                        var t = j(this.base + this.current.fullPath);
                        e ? Me(t) : Le(t)
                    }
                }, t.prototype.getCurrentLocation = function() {
                    return ft(this.base)
                }, t
            }(et);

            function ft(e) {
                var t = window.location.pathname,
                    n = t.toLowerCase(),
                    r = e.toLowerCase();
                return !e || n !== r && 0 !== n.indexOf(j(r + "/")) || (t = t.slice(e.length)), (t || "/") + window.location.search + window.location.hash
            }
            var dt = function(e) {
                function t(t, n, r) {
                    e.call(this, t, n), r && ht(this.base) || pt()
                }
                return e && (t.__proto__ = e), t.prototype = Object.create(e && e.prototype), t.prototype.constructor = t, t.prototype.setupListeners = function() {
                    var e = this;
                    if (!(this.listeners.length > 0)) {
                        var t = this.router,
                            n = t.options.scrollBehavior,
                            r = Ne && n;
                        r && this.listeners.push(xe());
                        var i = function() {
                                var t = e.current;
                                pt() && e.transitionTo(vt(), (function(n) {
                                    r && Ce(e.router, n, t, !0), Ne || bt(n.fullPath)
                                }))
                            },
                            o = Ne ? "popstate" : "hashchange";
                        window.addEventListener(o, i), this.listeners.push((function() {
                            window.removeEventListener(o, i)
                        }))
                    }
                }, t.prototype.push = function(e, t, n) {
                    var r = this,
                        i = this,
                        o = i.current;
                    this.transitionTo(e, (function(e) {
                        gt(e.fullPath), Ce(r.router, e, o, !1), t && t(e)
                    }), n)
                }, t.prototype.replace = function(e, t, n) {
                    var r = this,
                        i = this,
                        o = i.current;
                    this.transitionTo(e, (function(e) {
                        bt(e.fullPath), Ce(r.router, e, o, !1), t && t(e)
                    }), n)
                }, t.prototype.go = function(e) {
                    window.history.go(e)
                }, t.prototype.ensureURL = function(e) {
                    var t = this.current.fullPath;
                    vt() !== t && (e ? gt(t) : bt(t))
                }, t.prototype.getCurrentLocation = function() {
                    return vt()
                }, t
            }(et);

            function ht(e) {
                var t = ft(e);
                if (!/^\/#/.test(t)) return window.location.replace(j(e + "/#" + t)), !0
            }

            function pt() {
                var e = vt();
                return "/" === e.charAt(0) || (bt("/" + e), !1)
            }

            function vt() {
                var e = window.location.href,
                    t = e.indexOf("#");
                return t < 0 ? "" : (e = e.slice(t + 1), e)
            }

            function mt(e) {
                var t = window.location.href,
                    n = t.indexOf("#"),
                    r = n >= 0 ? t.slice(0, n) : t;
                return r + "#" + e
            }

            function gt(e) {
                Ne ? Me(mt(e)) : window.location.hash = e
            }

            function bt(e) {
                Ne ? Le(mt(e)) : window.location.replace(mt(e))
            }
            var yt = function(e) {
                    function t(t, n) {
                        e.call(this, t, n), this.stack = [], this.index = -1
                    }
                    return e && (t.__proto__ = e), t.prototype = Object.create(e && e.prototype), t.prototype.constructor = t, t.prototype.push = function(e, t, n) {
                        var r = this;
                        this.transitionTo(e, (function(e) {
                            r.stack = r.stack.slice(0, r.index + 1).concat(e), r.index++, t && t(e)
                        }), n)
                    }, t.prototype.replace = function(e, t, n) {
                        var r = this;
                        this.transitionTo(e, (function(e) {
                            r.stack = r.stack.slice(0, r.index).concat(e), t && t(e)
                        }), n)
                    }, t.prototype.go = function(e) {
                        var t = this,
                            n = this.index + e;
                        if (!(n < 0 || n >= this.stack.length)) {
                            var r = this.stack[n];
                            this.confirmTransition(r, (function() {
                                var e = t.current;
                                t.index = n, t.updateRoute(r), t.router.afterHooks.forEach((function(t) {
                                    t && t(r, e)
                                }))
                            }), (function(e) {
                                Ue(e, Re.duplicated) && (t.index = n)
                            }))
                        }
                    }, t.prototype.getCurrentLocation = function() {
                        var e = this.stack[this.stack.length - 1];
                        return e ? e.fullPath : "/"
                    }, t.prototype.ensureURL = function() {}, t
                }(et),
                _t = function(e) {
                    void 0 === e && (e = {}), this.app = null, this.apps = [], this.options = e, this.beforeHooks = [], this.resolveHooks = [], this.afterHooks = [], this.matcher = he(e.routes || [], this);
                    var t = e.mode || "hash";
                    switch (this.fallback = "history" === t && !Ne && !1 !== e.fallback, this.fallback && (t = "hash"), ue || (t = "abstract"), this.mode = t, t) {
                        case "history":
                            this.history = new lt(this, e.base);
                            break;
                        case "hash":
                            this.history = new dt(this, e.base, this.fallback);
                            break;
                        case "abstract":
                            this.history = new yt(this, e.base);
                            break;
                        default:
                            0
                    }
                },
                wt = {
                    currentRoute: {
                        configurable: !0
                    }
                };
            _t.prototype.match = function(e, t, n) {
                return this.matcher.match(e, t, n)
            }, wt.currentRoute.get = function() {
                return this.history && this.history.current
            }, _t.prototype.init = function(e) {
                var t = this;
                if (this.apps.push(e), e.$once("hook:destroyed", (function() {
                        var n = t.apps.indexOf(e);
                        n > -1 && t.apps.splice(n, 1), t.app === e && (t.app = t.apps[0] || null), t.app || t.history.teardown()
                    })), !this.app) {
                    this.app = e;
                    var n = this.history;
                    if (n instanceof lt || n instanceof dt) {
                        var r = function(e) {
                                var r = n.current,
                                    i = t.options.scrollBehavior,
                                    o = Ne && i;
                                o && "fullPath" in e && Ce(t, e, r, !1)
                            },
                            i = function(e) {
                                n.setupListeners(), r(e)
                            };
                        n.transitionTo(n.getCurrentLocation(), i, i)
                    }
                    n.listen((function(e) {
                        t.apps.forEach((function(t) {
                            t._route = e
                        }))
                    }))
                }
            }, _t.prototype.beforeEach = function(e) {
                return Ct(this.beforeHooks, e)
            }, _t.prototype.beforeResolve = function(e) {
                return Ct(this.resolveHooks, e)
            }, _t.prototype.afterEach = function(e) {
                return Ct(this.afterHooks, e)
            }, _t.prototype.onReady = function(e, t) {
                this.history.onReady(e, t)
            }, _t.prototype.onError = function(e) {
                this.history.onError(e)
            }, _t.prototype.push = function(e, t, n) {
                var r = this;
                if (!t && !n && "undefined" !== typeof Promise) return new Promise((function(t, n) {
                    r.history.push(e, t, n)
                }));
                this.history.push(e, t, n)
            }, _t.prototype.replace = function(e, t, n) {
                var r = this;
                if (!t && !n && "undefined" !== typeof Promise) return new Promise((function(t, n) {
                    r.history.replace(e, t, n)
                }));
                this.history.replace(e, t, n)
            }, _t.prototype.go = function(e) {
                this.history.go(e)
            }, _t.prototype.back = function() {
                this.go(-1)
            }, _t.prototype.forward = function() {
                this.go(1)
            }, _t.prototype.getMatchedComponents = function(e) {
                var t = e ? e.matched ? e : this.resolve(e).route : this.currentRoute;
                return t ? [].concat.apply([], t.matched.map((function(e) {
                    return Object.keys(e.components).map((function(t) {
                        return e.components[t]
                    }))
                }))) : []
            }, _t.prototype.resolve = function(e, t, n) {
                t = t || this.history.current;
                var r = Q(e, t, n, this),
                    i = this.match(r, t),
                    o = i.redirectedFrom || i.fullPath,
                    a = this.history.base,
                    s = St(a, o, this.mode);
                return {
                    location: r,
                    route: i,
                    href: s,
                    normalizedTo: r,
                    resolved: i
                }
            }, _t.prototype.getRoutes = function() {
                return this.matcher.getRoutes()
            }, _t.prototype.addRoute = function(e, t) {
                this.matcher.addRoute(e, t), this.history.current !== m && this.history.transitionTo(this.history.getCurrentLocation())
            }, _t.prototype.addRoutes = function(e) {
                this.matcher.addRoutes(e), this.history.current !== m && this.history.transitionTo(this.history.getCurrentLocation())
            }, Object.defineProperties(_t.prototype, wt);
            var xt = _t;

            function Ct(e, t) {
                return e.push(t),
                    function() {
                        var n = e.indexOf(t);
                        n > -1 && e.splice(n, 1)
                    }
            }

            function St(e, t, n) {
                var r = "hash" === n ? "#" + t : t;
                return e ? j(e + "/" + r) : r
            }
            _t.install = se, _t.version = "3.6.5", _t.isNavigationFailure = Ue, _t.NavigationFailureType = Re, _t.START_LOCATION = m, ue && window.Vue && window.Vue.use(_t)
        
