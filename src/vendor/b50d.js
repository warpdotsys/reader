// chunk-vendors webpack 模块 b50d（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./c532.js"),
                i = n("./467f.js"),
                o = n("./7aac.js"),
                a = n("./30b5.js"),
                s = n("./83b9.js"),
                u = n("./c345.js"),
                c = n("./3934.js"),
                l = n("./2d83.js");
            e.exports = function(e) {
                return new Promise((function(t, n) {
                    var f = e.data,
                        d = e.headers,
                        h = e.responseType;
                    r.isFormData(f) && delete d["Content-Type"];
                    var p = new XMLHttpRequest;
                    if (e.auth) {
                        var v = e.auth.username || "",
                            m = e.auth.password ? unescape(encodeURIComponent(e.auth.password)) : "";
                        d.Authorization = "Basic " + btoa(v + ":" + m)
                    }
                    var g = s(e.baseURL, e.url);

                    function b() {
                        if (p) {
                            var r = "getAllResponseHeaders" in p ? u(p.getAllResponseHeaders()) : null,
                                o = h && "text" !== h && "json" !== h ? p.response : p.responseText,
                                a = {
                                    data: o,
                                    status: p.status,
                                    statusText: p.statusText,
                                    headers: r,
                                    config: e,
                                    request: p
                                };
                            i(t, n, a), p = null
                        }
                    }
                    if (p.open(e.method.toUpperCase(), a(g, e.params, e.paramsSerializer), !0), p.timeout = e.timeout, "onloadend" in p ? p.onloadend = b : p.onreadystatechange = function() {
                            p && 4 === p.readyState && (0 !== p.status || p.responseURL && 0 === p.responseURL.indexOf("file:")) && setTimeout(b)
                        }, p.onabort = function() {
                            p && (n(l("Request aborted", e, "ECONNABORTED", p)), p = null)
                        }, p.onerror = function() {
                            n(l("Network Error", e, null, p)), p = null
                        }, p.ontimeout = function() {
                            var t = "timeout of " + e.timeout + "ms exceeded";
                            e.timeoutErrorMessage && (t = e.timeoutErrorMessage), n(l(t, e, e.transitional && e.transitional.clarifyTimeoutError ? "ETIMEDOUT" : "ECONNABORTED", p)), p = null
                        }, r.isStandardBrowserEnv()) {
                        var y = (e.withCredentials || c(g)) && e.xsrfCookieName ? o.read(e.xsrfCookieName) : void 0;
                        y && (d[e.xsrfHeaderName] = y)
                    }
                    "setRequestHeader" in p && r.forEach(d, (function(e, t) {
                        "undefined" === typeof f && "content-type" === t.toLowerCase() ? delete d[t] : p.setRequestHeader(t, e)
                    })), r.isUndefined(e.withCredentials) || (p.withCredentials = !!e.withCredentials), h && "json" !== h && (p.responseType = e.responseType), "function" === typeof e.onDownloadProgress && p.addEventListener("progress", e.onDownloadProgress), "function" === typeof e.onUploadProgress && p.upload && p.upload.addEventListener("progress", e.onUploadProgress), e.cancelToken && e.cancelToken.promise.then((function(e) {
                        p && (p.abort(), n(e), p = null)
                    })), f || (f = null), p.send(f)
                }))
            }
        
