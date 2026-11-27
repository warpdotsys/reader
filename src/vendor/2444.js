// chunk-vendors webpack 模块 2444（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            (function(t) {
                var r = n("./c532.js"),
                    i = n("./c8af.js"),
                    o = n("./387f.js"),
                    a = {
                        "Content-Type": "application/x-www-form-urlencoded"
                    };

                function s(e, t) {
                    !r.isUndefined(e) && r.isUndefined(e["Content-Type"]) && (e["Content-Type"] = t)
                }

                function u() {
                    var e;
                    return ("undefined" !== typeof XMLHttpRequest || "undefined" !== typeof t && "[object process]" === Object.prototype.toString.call(t)) && (e = n("./b50d.js")), e
                }

                function c(e, t, n) {
                    if (r.isString(e)) try {
                        return (t || JSON.parse)(e), r.trim(e)
                    } catch (i) {
                        if ("SyntaxError" !== i.name) throw i
                    }
                    return (n || JSON.stringify)(e)
                }
                var l = {
                    transitional: {
                        silentJSONParsing: !0,
                        forcedJSONParsing: !0,
                        clarifyTimeoutError: !1
                    },
                    adapter: u(),
                    transformRequest: [function(e, t) {
                        return i(t, "Accept"), i(t, "Content-Type"), r.isFormData(e) || r.isArrayBuffer(e) || r.isBuffer(e) || r.isStream(e) || r.isFile(e) || r.isBlob(e) ? e : r.isArrayBufferView(e) ? e.buffer : r.isURLSearchParams(e) ? (s(t, "application/x-www-form-urlencoded;charset=utf-8"), e.toString()) : r.isObject(e) || t && "application/json" === t["Content-Type"] ? (s(t, "application/json"), c(e)) : e
                    }],
                    transformResponse: [function(e) {
                        var t = this.transitional,
                            n = t && t.silentJSONParsing,
                            i = t && t.forcedJSONParsing,
                            a = !n && "json" === this.responseType;
                        if (a || i && r.isString(e) && e.length) try {
                            return JSON.parse(e)
                        } catch (s) {
                            if (a) {
                                if ("SyntaxError" === s.name) throw o(s, this, "E_JSON_PARSE");
                                throw s
                            }
                        }
                        return e
                    }],
                    timeout: 0,
                    xsrfCookieName: "XSRF-TOKEN",
                    xsrfHeaderName: "X-XSRF-TOKEN",
                    maxContentLength: -1,
                    maxBodyLength: -1,
                    validateStatus: function(e) {
                        return e >= 200 && e < 300
                    },
                    headers: {
                        common: {
                            Accept: "application/json, text/plain, */*"
                        }
                    }
                };
                r.forEach(["delete", "get", "head"], (function(e) {
                    l.headers[e] = {}
                })), r.forEach(["post", "put", "patch"], (function(e) {
                    l.headers[e] = r.merge(a)
                })), e.exports = l
            }).call(this, n("./4362.js"))
        
