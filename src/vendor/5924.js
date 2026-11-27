// chunk-vendors webpack 模块 5924（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./7037.js")["default"];
            n("./e01a.js"), n("./d28b.js"), n("./d9e2.js"), n("./d401.js"), n("./4de4.js"), n("./caad.js"), n("./c975.js"), n("./e260.js"), n("./e9f5.js"), n("./910d.js"), n("./a9e3.js"), n("./d3b7.js"), n("./ac1f.js"), n("./3ca3.js"), n("./466d.js"), n("./5319.js"), n("./ddb0.js"), t.__esModule = !0, t.isInContainer = t.getScrollContainer = t.isScroll = t.getStyle = t.once = t.off = t.on = void 0;
            var i = "function" === typeof Symbol && "symbol" === r(Symbol.iterator) ? function(e) {
                return r(e)
            } : function(e) {
                return e && "function" === typeof Symbol && e.constructor === Symbol && e !== Symbol.prototype ? "symbol" : r(e)
            };
            t.hasClass = m, t.addClass = g, t.removeClass = b, t.setStyle = _;
            var o = n("./2b0e.js"),
                a = s(o);

            function s(e) {
                return e && e.__esModule ? e : {
                    default: e
                }
            }
            var u = a["default"].prototype.$isServer,
                c = /([\:\-\_]+(.))/g,
                l = /^moz([A-Z])/,
                f = u ? 0 : Number(document.documentMode),
                d = function(e) {
                    return (e || "").replace(/^[\s\uFEFF]+|[\s\uFEFF]+$/g, "")
                },
                h = function(e) {
                    return e.replace(c, (function(e, t, n, r) {
                        return r ? n.toUpperCase() : n
                    })).replace(l, "Moz$1")
                },
                p = t.on = function() {
                    return !u && document.addEventListener ? function(e, t, n) {
                        e && t && n && e.addEventListener(t, n, !1)
                    } : function(e, t, n) {
                        e && t && n && e.attachEvent("on" + t, n)
                    }
                }(),
                v = t.off = function() {
                    return !u && document.removeEventListener ? function(e, t, n) {
                        e && t && e.removeEventListener(t, n, !1)
                    } : function(e, t, n) {
                        e && t && e.detachEvent("on" + t, n)
                    }
                }();
            t.once = function(e, t, n) {
                var r = function r() {
                    n && n.apply(this, arguments), v(e, t, r)
                };
                p(e, t, r)
            };

            function m(e, t) {
                if (!e || !t) return !1;
                if (-1 !== t.indexOf(" ")) throw new Error("className should not contain space.");
                return e.classList ? e.classList.contains(t) : (" " + e.className + " ").indexOf(" " + t + " ") > -1
            }

            function g(e, t) {
                if (e) {
                    for (var n = e.className, r = (t || "").split(" "), i = 0, o = r.length; i < o; i++) {
                        var a = r[i];
                        a && (e.classList ? e.classList.add(a) : m(e, a) || (n += " " + a))
                    }
                    e.classList || e.setAttribute("class", n)
                }
            }

            function b(e, t) {
                if (e && t) {
                    for (var n = t.split(" "), r = " " + e.className + " ", i = 0, o = n.length; i < o; i++) {
                        var a = n[i];
                        a && (e.classList ? e.classList.remove(a) : m(e, a) && (r = r.replace(" " + a + " ", " ")))
                    }
                    e.classList || e.setAttribute("class", d(r))
                }
            }
            var y = t.getStyle = f < 9 ? function(e, t) {
                if (!u) {
                    if (!e || !t) return null;
                    t = h(t), "float" === t && (t = "styleFloat");
                    try {
                        switch (t) {
                            case "opacity":
                                try {
                                    return e.filters.item("alpha").opacity / 100
                                } catch (n) {
                                    return 1
                                }
                            default:
                                return e.style[t] || e.currentStyle ? e.currentStyle[t] : null
                        }
                    } catch (n) {
                        return e.style[t]
                    }
                }
            } : function(e, t) {
                if (!u) {
                    if (!e || !t) return null;
                    t = h(t), "float" === t && (t = "cssFloat");
                    try {
                        var n = document.defaultView.getComputedStyle(e, "");
                        return e.style[t] || n ? n[t] : null
                    } catch (r) {
                        return e.style[t]
                    }
                }
            };

            function _(e, t, n) {
                if (e && t)
                    if ("object" === ("undefined" === typeof t ? "undefined" : i(t)))
                        for (var r in t) t.hasOwnProperty(r) && _(e, r, t[r]);
                    else t = h(t), "opacity" === t && f < 9 ? e.style.filter = isNaN(n) ? "" : "alpha(opacity=" + 100 * n + ")" : e.style[t] = n
            }
            var w = t.isScroll = function(e, t) {
                if (!u) {
                    var n = null !== t && void 0 !== t,
                        r = y(e, n ? t ? "overflow-y" : "overflow-x" : "overflow");
                    return r.match(/(scroll|auto|overlay)/)
                }
            };
            t.getScrollContainer = function(e, t) {
                if (!u) {
                    var n = e;
                    while (n) {
                        if ([window, document, document.documentElement].includes(n)) return window;
                        if (w(n, t)) return n;
                        n = n.parentNode
                    }
                    return n
                }
            }, t.isInContainer = function(e, t) {
                if (u || !e || !t) return !1;
                var n = e.getBoundingClientRect(),
                    r = void 0;
                return r = [window, document, document.documentElement, null, void 0].includes(t) ? {
                    top: 0,
                    right: window.innerWidth,
                    bottom: window.innerHeight,
                    left: 0
                } : t.getBoundingClientRect(), n.top < r.bottom && n.bottom > r.top && n.right > r.left && n.left < r.right
            }
        
