// chunk-vendors webpack 模块 5270（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./c532.js"),
                i = n("./c401.js"),
                o = n("./2e67.js"),
                a = n("./2444.js");

            function s(e) {
                e.cancelToken && e.cancelToken.throwIfRequested()
            }
            e.exports = function(e) {
                s(e), e.headers = e.headers || {}, e.data = i.call(e, e.data, e.headers, e.transformRequest), e.headers = r.merge(e.headers.common || {}, e.headers[e.method] || {}, e.headers), r.forEach(["delete", "get", "head", "post", "put", "patch", "common"], (function(t) {
                    delete e.headers[t]
                }));
                var t = e.adapter || a.adapter;
                return t(e).then((function(t) {
                    return s(e), t.data = i.call(e, t.data, t.headers, e.transformResponse), t
                }), (function(t) {
                    return o(t) || (s(e), t && t.response && (t.response.data = i.call(e, t.response.data, t.response.headers, e.transformResponse))), Promise.reject(t)
                }))
            }
        
