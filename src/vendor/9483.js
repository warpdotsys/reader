// chunk-vendors webpack 模块 9483（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            n.d(t, "a", (function() {
                return o
            }));
            var r, i = function() {
                return Boolean("localhost" === window.location.hostname || "[::1]" === window.location.hostname || window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/))
            };

            function o(e, t) {
                void 0 === t && (t = {});
                var n = t.registrationOptions;
                void 0 === n && (n = {}), delete t.registrationOptions;
                var o = function(e) {
                    var n = [],
                        r = arguments.length - 1;
                    while (r-- > 0) n[r] = arguments[r + 1];
                    t && t[e] && t[e].apply(t, n)
                };
                "serviceWorker" in navigator && r.then((function() {
                    i() ? (u(e, o, n), navigator.serviceWorker.ready.then((function(e) {
                        o("ready", e)
                    })).catch((function(e) {
                        return a(o, e)
                    }))) : (s(e, o, n), navigator.serviceWorker.ready.then((function(e) {
                        o("ready", e)
                    })).catch((function(e) {
                        return a(o, e)
                    })))
                }))
            }

            function a(e, t) {
                navigator.onLine || e("offline"), e("error", t)
            }

            function s(e, t, n) {
                navigator.serviceWorker.register(e, n).then((function(e) {
                    t("registered", e), e.waiting ? t("updated", e) : e.onupdatefound = function() {
                        t("updatefound", e);
                        var n = e.installing;
                        n.onstatechange = function() {
                            "installed" === n.state && (navigator.serviceWorker.controller ? t("updated", e) : t("cached", e))
                        }
                    }
                })).catch((function(e) {
                    return a(t, e)
                }))
            }

            function u(e, t, n) {
                fetch(e).then((function(r) {
                    404 === r.status ? (t("error", new Error("Service worker not found at " + e)), c()) : -1 === r.headers.get("content-type").indexOf("javascript") ? (t("error", new Error("Expected " + e + " to have javascript content-type, but received " + r.headers.get("content-type"))), c()) : s(e, t, n)
                })).catch((function(e) {
                    return a(t, e)
                }))
            }

            function c() {
                "serviceWorker" in navigator && navigator.serviceWorker.ready.then((function(e) {
                    e.unregister()
                })).catch((function(e) {
                    return a(emit, e)
                }))
            }
            "undefined" !== typeof window && (r = "undefined" !== typeof Promise ? new Promise((function(e) {
                return window.addEventListener("load", e)
            })) : {
                then: function(e) {
                    return window.addEventListener("load", e)
                }
            })
        
