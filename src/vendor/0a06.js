// chunk-vendors webpack 模块 0a06（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./c532.js"),
                i = n("./30b5.js"),
                o = n("./f6b4.js"),
                a = n("./5270.js"),
                s = n("./4a7b.js"),
                u = n("./848b.js"),
                c = u.validators;

            function l(e) {
                this.defaults = e, this.interceptors = {
                    request: new o,
                    response: new o
                }
            }
            l.prototype.request = function(e) {
                "string" === typeof e ? (e = arguments[1] || {}, e.url = arguments[0]) : e = e || {}, e = s(this.defaults, e), e.method ? e.method = e.method.toLowerCase() : this.defaults.method ? e.method = this.defaults.method.toLowerCase() : e.method = "get";
                var t = e.transitional;
                void 0 !== t && u.assertOptions(t, {
                    silentJSONParsing: c.transitional(c.boolean, "1.0.0"),
                    forcedJSONParsing: c.transitional(c.boolean, "1.0.0"),
                    clarifyTimeoutError: c.transitional(c.boolean, "1.0.0")
                }, !1);
                var n = [],
                    r = !0;
                this.interceptors.request.forEach((function(t) {
                    "function" === typeof t.runWhen && !1 === t.runWhen(e) || (r = r && t.synchronous, n.unshift(t.fulfilled, t.rejected))
                }));
                var i, o = [];
                if (this.interceptors.response.forEach((function(e) {
                        o.push(e.fulfilled, e.rejected)
                    })), !r) {
                    var l = [a, void 0];
                    Array.prototype.unshift.apply(l, n), l = l.concat(o), i = Promise.resolve(e);
                    while (l.length) i = i.then(l.shift(), l.shift());
                    return i
                }
                var f = e;
                while (n.length) {
                    var d = n.shift(),
                        h = n.shift();
                    try {
                        f = d(f)
                    } catch (p) {
                        h(p);
                        break
                    }
                }
                try {
                    i = a(f)
                } catch (p) {
                    return Promise.reject(p)
                }
                while (o.length) i = i.then(o.shift(), o.shift());
                return i
            }, l.prototype.getUri = function(e) {
                return e = s(this.defaults, e), i(e.url, e.params, e.paramsSerializer).replace(/^\?/, "")
            }, r.forEach(["delete", "get", "head", "options"], (function(e) {
                l.prototype[e] = function(t, n) {
                    return this.request(s(n || {}, {
                        method: e,
                        url: t,
                        data: (n || {}).data
                    }))
                }
            })), r.forEach(["post", "put", "patch"], (function(e) {
                l.prototype[e] = function(t, n, r) {
                    return this.request(s(r || {}, {
                        method: e,
                        url: t,
                        data: n
                    }))
                }
            })), e.exports = l
        
