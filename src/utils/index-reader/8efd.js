// 恢复自 reader-pro-3.2.14 前端产物 beauty/index-reader.js 的 webpack 模块 8efd（逐字切片，形参 e, t, r）
const e = module;
const t = module.exports;
const r = require;

            "use strict";
            var n = r("53ca"),
                i = function(e, t, r, n) {
                    return e /= n / 2, e < 1 ? r / 2 * e * e + t : (e--, -r / 2 * (e * (e - 2) - 1) + t)
                },
                a = function() {
                    var e, t, r, a, o, s, l, u, c, f, h, d, p;

                    function v() {
                        var t = e.scrollTop || e.scrollY || e.pageYOffset;
                        return t = "undefined" === typeof t ? 0 : t, t
                    }

                    function g(t) {
                        var n = t.getBoundingClientRect().top,
                            i = e.getBoundingClientRect ? e.getBoundingClientRect().top : 0;
                        return n - i + r
                    }

                    function m(t) {
                        e.scrollTo ? e.scrollTo(0, t) : e.scrollTop = t
                    }

                    function b(e) {
                        f || (f = e), h = e - f, d = s(h, r, u, c), m(d), h < c ? requestAnimationFrame(b) : y()
                    }

                    function y() {
                        m(r + u), t && l && (t.setAttribute("tabindex", "-1"), t.focus()), "function" === typeof p && p(), f = !1
                    }

                    function S(f) {
                        var h = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : {};
                        switch (c = h.duration || 1e3, o = h.offset || 0, p = h.callback, s = h.easing || i, l = h.a11y || !1, Object(n["a"])(h.container)) {
                            case "object":
                                e = h.container;
                                break;
                            case "string":
                                e = document.querySelector(h.container);
                                break;
                            default:
                                e = window
                        }
                        switch (r = v(), Object(n["a"])(f)) {
                            case "number":
                                t = void 0, l = !1, a = r + f;
                                break;
                            case "object":
                                t = f, a = g(t);
                                break;
                            case "string":
                                t = document.querySelector(f), a = g(t);
                                break
                        }
                        switch (u = a - r + o, Object(n["a"])(h.duration)) {
                            case "number":
                                c = h.duration;
                                break;
                            case "function":
                                c = h.duration(u);
                                break
                        }
                        requestAnimationFrame(b)
                    }
                    return S
                },
                o = a();
            t["a"] = o
        
