// chunk-vendors webpack 模块 4a7b（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./c532.js");
            e.exports = function(e, t) {
                t = t || {};
                var n = {},
                    i = ["url", "method", "data"],
                    o = ["headers", "auth", "proxy", "params"],
                    a = ["baseURL", "transformRequest", "transformResponse", "paramsSerializer", "timeout", "timeoutMessage", "withCredentials", "adapter", "responseType", "xsrfCookieName", "xsrfHeaderName", "onUploadProgress", "onDownloadProgress", "decompress", "maxContentLength", "maxBodyLength", "maxRedirects", "transport", "httpAgent", "httpsAgent", "cancelToken", "socketPath", "responseEncoding"],
                    s = ["validateStatus"];

                function u(e, t) {
                    return r.isPlainObject(e) && r.isPlainObject(t) ? r.merge(e, t) : r.isPlainObject(t) ? r.merge({}, t) : r.isArray(t) ? t.slice() : t
                }

                function c(i) {
                    r.isUndefined(t[i]) ? r.isUndefined(e[i]) || (n[i] = u(void 0, e[i])) : n[i] = u(e[i], t[i])
                }
                r.forEach(i, (function(e) {
                    r.isUndefined(t[e]) || (n[e] = u(void 0, t[e]))
                })), r.forEach(o, c), r.forEach(a, (function(i) {
                    r.isUndefined(t[i]) ? r.isUndefined(e[i]) || (n[i] = u(void 0, e[i])) : n[i] = u(void 0, t[i])
                })), r.forEach(s, (function(r) {
                    r in t ? n[r] = u(e[r], t[r]) : r in e && (n[r] = u(void 0, e[r]))
                }));
                var l = i.concat(o).concat(a).concat(s),
                    f = Object.keys(e).concat(Object.keys(t)).filter((function(e) {
                        return -1 === l.indexOf(e)
                    }));
                return r.forEach(f, c), n
            }
        
