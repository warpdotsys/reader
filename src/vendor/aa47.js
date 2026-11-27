// chunk-vendors webpack 模块 aa47（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            /**!
             * Sortable 1.15.6
             * @author	RubaXa   <trash@rubaxa.org>
             * @author	owenm    <owen23355@gmail.com>
             * @license MIT
             */
            function r(e, t) {
                var n = Object.keys(e);
                if (Object.getOwnPropertySymbols) {
                    var r = Object.getOwnPropertySymbols(e);
                    t && (r = r.filter((function(t) {
                        return Object.getOwnPropertyDescriptor(e, t).enumerable
                    }))), n.push.apply(n, r)
                }
                return n
            }

            function i(e) {
                for (var t = 1; t < arguments.length; t++) {
                    var n = null != arguments[t] ? arguments[t] : {};
                    t % 2 ? r(Object(n), !0).forEach((function(t) {
                        a(e, t, n[t])
                    })) : Object.getOwnPropertyDescriptors ? Object.defineProperties(e, Object.getOwnPropertyDescriptors(n)) : r(Object(n)).forEach((function(t) {
                        Object.defineProperty(e, t, Object.getOwnPropertyDescriptor(n, t))
                    }))
                }
                return e
            }

            function o(e) {
                return o = "function" === typeof Symbol && "symbol" === typeof Symbol.iterator ? function(e) {
                    return typeof e
                } : function(e) {
                    return e && "function" === typeof Symbol && e.constructor === Symbol && e !== Symbol.prototype ? "symbol" : typeof e
                }, o(e)
            }

            function a(e, t, n) {
                return t in e ? Object.defineProperty(e, t, {
                    value: n,
                    enumerable: !0,
                    configurable: !0,
                    writable: !0
                }) : e[t] = n, e
            }

            function s() {
                return s = Object.assign || function(e) {
                    for (var t = 1; t < arguments.length; t++) {
                        var n = arguments[t];
                        for (var r in n) Object.prototype.hasOwnProperty.call(n, r) && (e[r] = n[r])
                    }
                    return e
                }, s.apply(this, arguments)
            }

            function u(e, t) {
                if (null == e) return {};
                var n, r, i = {},
                    o = Object.keys(e);
                for (r = 0; r < o.length; r++) n = o[r], t.indexOf(n) >= 0 || (i[n] = e[n]);
                return i
            }

            function c(e, t) {
                if (null == e) return {};
                var n, r, i = u(e, t);
                if (Object.getOwnPropertySymbols) {
                    var o = Object.getOwnPropertySymbols(e);
                    for (r = 0; r < o.length; r++) n = o[r], t.indexOf(n) >= 0 || Object.prototype.propertyIsEnumerable.call(e, n) && (i[n] = e[n])
                }
                return i
            }
            var l = "1.15.6";

            function f(e) {
                if ("undefined" !== typeof window && window.navigator) return !!navigator.userAgent.match(e)
            }
            var d = f(/(?:Trident.*rv[ :]?11\.|msie|iemobile|Windows Phone)/i),
                h = f(/Edge/i),
                p = f(/firefox/i),
                v = f(/safari/i) && !f(/chrome/i) && !f(/android/i),
                m = f(/iP(ad|od|hone)/i),
                g = f(/chrome/i) && f(/android/i),
                b = {
                    capture: !1,
                    passive: !1
                };

            function y(e, t, n) {
                e.addEventListener(t, n, !d && b)
            }

            function _(e, t, n) {
                e.removeEventListener(t, n, !d && b)
            }

            function w(e, t) {
                if (t) {
                    if (">" === t[0] && (t = t.substring(1)), e) try {
                        if (e.matches) return e.matches(t);
                        if (e.msMatchesSelector) return e.msMatchesSelector(t);
                        if (e.webkitMatchesSelector) return e.webkitMatchesSelector(t)
                    } catch (n) {
                        return !1
                    }
                    return !1
                }
            }

            function x(e) {
                return e.host && e !== document && e.host.nodeType ? e.host : e.parentNode
            }

            function C(e, t, n, r) {
                if (e) {
                    n = n || document;
                    do {
                        if (null != t && (">" === t[0] ? e.parentNode === n && w(e, t) : w(e, t)) || r && e === n) return e;
                        if (e === n) break
                    } while (e = x(e))
                }
                return null
            }
            var S, O = /\s+/g;

            function E(e, t, n) {
                if (e && t)
                    if (e.classList) e.classList[n ? "add" : "remove"](t);
                    else {
                        var r = (" " + e.className + " ").replace(O, " ").replace(" " + t + " ", " ");
                        e.className = (r + (n ? " " + t : "")).replace(O, " ")
                    }
            }

            function k(e, t, n) {
                var r = e && e.style;
                if (r) {
                    if (void 0 === n) return document.defaultView && document.defaultView.getComputedStyle ? n = document.defaultView.getComputedStyle(e, "") : e.currentStyle && (n = e.currentStyle), void 0 === t ? n : n[t];
                    t in r || -1 !== t.indexOf("webkit") || (t = "-webkit-" + t), r[t] = n + ("string" === typeof n ? "" : "px")
                }
            }

            function T(e, t) {
                var n = "";
                if ("string" === typeof e) n = e;
                else
                    do {
                        var r = k(e, "transform");
                        r && "none" !== r && (n = r + " " + n)
                    } while (!t && (e = e.parentNode));
                var i = window.DOMMatrix || window.WebKitCSSMatrix || window.CSSMatrix || window.MSCSSMatrix;
                return i && new i(n)
            }

            function j(e, t, n) {
                if (e) {
                    var r = e.getElementsByTagName(t),
                        i = 0,
                        o = r.length;
                    if (n)
                        for (; i < o; i++) n(r[i], i);
                    return r
                }
                return []
            }

            function $() {
                var e = document.scrollingElement;
                return e || document.documentElement
            }

            function A(e, t, n, r, i) {
                if (e.getBoundingClientRect || e === window) {
                    var o, a, s, u, c, l, f;
                    if (e !== window && e.parentNode && e !== $() ? (o = e.getBoundingClientRect(), a = o.top, s = o.left, u = o.bottom, c = o.right, l = o.height, f = o.width) : (a = 0, s = 0, u = window.innerHeight, c = window.innerWidth, l = window.innerHeight, f = window.innerWidth), (t || n) && e !== window && (i = i || e.parentNode, !d))
                        do {
                            if (i && i.getBoundingClientRect && ("none" !== k(i, "transform") || n && "static" !== k(i, "position"))) {
                                var h = i.getBoundingClientRect();
                                a -= h.top + parseInt(k(i, "border-top-width")), s -= h.left + parseInt(k(i, "border-left-width")), u = a + o.height, c = s + o.width;
                                break
                            }
                        } while (i = i.parentNode);
                    if (r && e !== window) {
                        var p = T(i || e),
                            v = p && p.a,
                            m = p && p.d;
                        p && (a /= m, s /= v, f /= v, l /= m, u = a + l, c = s + f)
                    }
                    return {
                        top: a,
                        left: s,
                        bottom: u,
                        right: c,
                        width: f,
                        height: l
                    }
                }
            }

            function P(e, t, n) {
                var r = F(e, !0),
                    i = A(e)[t];
                while (r) {
                    var o = A(r)[n],
                        a = void 0;
                    if (a = "top" === n || "left" === n ? i >= o : i <= o, !a) return r;
                    if (r === $()) break;
                    r = F(r, !1)
                }
                return !1
            }

            function I(e, t, n, r) {
                var i = 0,
                    o = 0,
                    a = e.children;
                while (o < a.length) {
                    if ("none" !== a[o].style.display && a[o] !== Ye.ghost && (r || a[o] !== Ye.dragged) && C(a[o], n.draggable, e, !1)) {
                        if (i === t) return a[o];
                        i++
                    }
                    o++
                }
                return null
            }

            function N(e, t) {
                var n = e.lastElementChild;
                while (n && (n === Ye.ghost || "none" === k(n, "display") || t && !w(n, t))) n = n.previousElementSibling;
                return n || null
            }

            function M(e, t) {
                var n = 0;
                if (!e || !e.parentNode) return -1;
                while (e = e.previousElementSibling) "TEMPLATE" === e.nodeName.toUpperCase() || e === Ye.clone || t && !w(e, t) || n++;
                return n
            }

            function L(e) {
                var t = 0,
                    n = 0,
                    r = $();
                if (e)
                    do {
                        var i = T(e),
                            o = i.a,
                            a = i.d;
                        t += e.scrollLeft * o, n += e.scrollTop * a
                    } while (e !== r && (e = e.parentNode));
                return [t, n]
            }

            function R(e, t) {
                for (var n in e)
                    if (e.hasOwnProperty(n))
                        for (var r in t)
                            if (t.hasOwnProperty(r) && t[r] === e[n][r]) return Number(n);
                return -1
            }

            function F(e, t) {
                if (!e || !e.getBoundingClientRect) return $();
                var n = e,
                    r = !1;
                do {
                    if (n.clientWidth < n.scrollWidth || n.clientHeight < n.scrollHeight) {
                        var i = k(n);
                        if (n.clientWidth < n.scrollWidth && ("auto" == i.overflowX || "scroll" == i.overflowX) || n.clientHeight < n.scrollHeight && ("auto" == i.overflowY || "scroll" == i.overflowY)) {
                            if (!n.getBoundingClientRect || n === document.body) return $();
                            if (r || t) return n;
                            r = !0
                        }
                    }
                } while (n = n.parentNode);
                return $()
            }

            function D(e, t) {
                if (e && t)
                    for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n]);
                return e
            }

            function B(e, t) {
                return Math.round(e.top) === Math.round(t.top) && Math.round(e.left) === Math.round(t.left) && Math.round(e.height) === Math.round(t.height) && Math.round(e.width) === Math.round(t.width)
            }

            function z(e, t) {
                return function() {
                    if (!S) {
                        var n = arguments,
                            r = this;
                        1 === n.length ? e.call(r, n[0]) : e.apply(r, n), S = setTimeout((function() {
                            S = void 0
                        }), t)
                    }
                }
            }

            function V() {
                clearTimeout(S), S = void 0
            }

            function H(e, t, n) {
                e.scrollLeft += t, e.scrollTop += n
            }

            function W(e) {
                var t = window.Polymer,
                    n = window.jQuery || window.Zepto;
                return t && t.dom ? t.dom(e).cloneNode(!0) : n ? n(e).clone(!0)[0] : e.cloneNode(!0)
            }

            function q(e, t, n) {
                var r = {};
                return Array.from(e.children).forEach((function(i) {
                    var o, a, s, u;
                    if (C(i, t.draggable, e, !1) && !i.animated && i !== n) {
                        var c = A(i);
                        r.left = Math.min(null !== (o = r.left) && void 0 !== o ? o : 1 / 0, c.left), r.top = Math.min(null !== (a = r.top) && void 0 !== a ? a : 1 / 0, c.top), r.right = Math.max(null !== (s = r.right) && void 0 !== s ? s : -1 / 0, c.right), r.bottom = Math.max(null !== (u = r.bottom) && void 0 !== u ? u : -1 / 0, c.bottom)
                    }
                })), r.width = r.right - r.left, r.height = r.bottom - r.top, r.x = r.left, r.y = r.top, r
            }
            var U = "Sortable" + (new Date).getTime();

            function X() {
                var e, t = [];
                return {
                    captureAnimationState: function() {
                        if (t = [], this.options.animation) {
                            var e = [].slice.call(this.el.children);
                            e.forEach((function(e) {
                                if ("none" !== k(e, "display") && e !== Ye.ghost) {
                                    t.push({
                                        target: e,
                                        rect: A(e)
                                    });
                                    var n = i({}, t[t.length - 1].rect);
                                    if (e.thisAnimationDuration) {
                                        var r = T(e, !0);
                                        r && (n.top -= r.f, n.left -= r.e)
                                    }
                                    e.fromRect = n
                                }
                            }))
                        }
                    },
                    addAnimationState: function(e) {
                        t.push(e)
                    },
                    removeAnimationState: function(e) {
                        t.splice(R(t, {
                            target: e
                        }), 1)
                    },
                    animateAll: function(n) {
                        var r = this;
                        if (!this.options.animation) return clearTimeout(e), void("function" === typeof n && n());
                        var i = !1,
                            o = 0;
                        t.forEach((function(e) {
                            var t = 0,
                                n = e.target,
                                a = n.fromRect,
                                s = A(n),
                                u = n.prevFromRect,
                                c = n.prevToRect,
                                l = e.rect,
                                f = T(n, !0);
                            f && (s.top -= f.f, s.left -= f.e), n.toRect = s, n.thisAnimationDuration && B(u, s) && !B(a, s) && (l.top - s.top) / (l.left - s.left) === (a.top - s.top) / (a.left - s.left) && (t = Y(l, u, c, r.options)), B(s, a) || (n.prevFromRect = a, n.prevToRect = s, t || (t = r.options.animation), r.animate(n, l, s, t)), t && (i = !0, o = Math.max(o, t), clearTimeout(n.animationResetTimer), n.animationResetTimer = setTimeout((function() {
                                n.animationTime = 0, n.prevFromRect = null, n.fromRect = null, n.prevToRect = null, n.thisAnimationDuration = null
                            }), t), n.thisAnimationDuration = t)
                        })), clearTimeout(e), i ? e = setTimeout((function() {
                            "function" === typeof n && n()
                        }), o) : "function" === typeof n && n(), t = []
                    },
                    animate: function(e, t, n, r) {
                        if (r) {
                            k(e, "transition", ""), k(e, "transform", "");
                            var i = T(this.el),
                                o = i && i.a,
                                a = i && i.d,
                                s = (t.left - n.left) / (o || 1),
                                u = (t.top - n.top) / (a || 1);
                            e.animatingX = !!s, e.animatingY = !!u, k(e, "transform", "translate3d(" + s + "px," + u + "px,0)"), this.forRepaintDummy = G(e), k(e, "transition", "transform " + r + "ms" + (this.options.easing ? " " + this.options.easing : "")), k(e, "transform", "translate3d(0,0,0)"), "number" === typeof e.animated && clearTimeout(e.animated), e.animated = setTimeout((function() {
                                k(e, "transition", ""), k(e, "transform", ""), e.animated = !1, e.animatingX = !1, e.animatingY = !1
                            }), r)
                        }
                    }
                }
            }

            function G(e) {
                return e.offsetWidth
            }

            function Y(e, t, n, r) {
                return Math.sqrt(Math.pow(t.top - e.top, 2) + Math.pow(t.left - e.left, 2)) / Math.sqrt(Math.pow(t.top - n.top, 2) + Math.pow(t.left - n.left, 2)) * r.animation
            }
            var K = [],
                J = {
                    initializeByDefault: !0
                },
                Z = {
                    mount: function(e) {
                        for (var t in J) J.hasOwnProperty(t) && !(t in e) && (e[t] = J[t]);
                        K.forEach((function(t) {
                            if (t.pluginName === e.pluginName) throw "Sortable: Cannot mount plugin ".concat(e.pluginName, " more than once")
                        })), K.push(e)
                    },
                    pluginEvent: function(e, t, n) {
                        var r = this;
                        this.eventCanceled = !1, n.cancel = function() {
                            r.eventCanceled = !0
                        };
                        var o = e + "Global";
                        K.forEach((function(r) {
                            t[r.pluginName] && (t[r.pluginName][o] && t[r.pluginName][o](i({
                                sortable: t
                            }, n)), t.options[r.pluginName] && t[r.pluginName][e] && t[r.pluginName][e](i({
                                sortable: t
                            }, n)))
                        }))
                    },
                    initializePlugins: function(e, t, n, r) {
                        for (var i in K.forEach((function(r) {
                                var i = r.pluginName;
                                if (e.options[i] || r.initializeByDefault) {
                                    var o = new r(e, t, e.options);
                                    o.sortable = e, o.options = e.options, e[i] = o, s(n, o.defaults)
                                }
                            })), e.options)
                            if (e.options.hasOwnProperty(i)) {
                                var o = this.modifyOption(e, i, e.options[i]);
                                "undefined" !== typeof o && (e.options[i] = o)
                            }
                    },
                    getEventProperties: function(e, t) {
                        var n = {};
                        return K.forEach((function(r) {
                            "function" === typeof r.eventProperties && s(n, r.eventProperties.call(t[r.pluginName], e))
                        })), n
                    },
                    modifyOption: function(e, t, n) {
                        var r;
                        return K.forEach((function(i) {
                            e[i.pluginName] && i.optionListeners && "function" === typeof i.optionListeners[t] && (r = i.optionListeners[t].call(e[i.pluginName], n))
                        })), r
                    }
                };

            function Q(e) {
                var t = e.sortable,
                    n = e.rootEl,
                    r = e.name,
                    o = e.targetEl,
                    a = e.cloneEl,
                    s = e.toEl,
                    u = e.fromEl,
                    c = e.oldIndex,
                    l = e.newIndex,
                    f = e.oldDraggableIndex,
                    p = e.newDraggableIndex,
                    v = e.originalEvent,
                    m = e.putSortable,
                    g = e.extraEventProperties;
                if (t = t || n && n[U], t) {
                    var b, y = t.options,
                        _ = "on" + r.charAt(0).toUpperCase() + r.substr(1);
                    !window.CustomEvent || d || h ? (b = document.createEvent("Event"), b.initEvent(r, !0, !0)) : b = new CustomEvent(r, {
                        bubbles: !0,
                        cancelable: !0
                    }), b.to = s || n, b.from = u || n, b.item = o || n, b.clone = a, b.oldIndex = c, b.newIndex = l, b.oldDraggableIndex = f, b.newDraggableIndex = p, b.originalEvent = v, b.pullMode = m ? m.lastPutMode : void 0;
                    var w = i(i({}, g), Z.getEventProperties(r, t));
                    for (var x in w) b[x] = w[x];
                    n && n.dispatchEvent(b), y[_] && y[_].call(t, b)
                }
            }
            var ee = ["evt"],
                te = function(e, t) {
                    var n = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : {},
                        r = n.evt,
                        o = c(n, ee);
                    Z.pluginEvent.bind(Ye)(e, t, i({
                        dragEl: re,
                        parentEl: ie,
                        ghostEl: oe,
                        rootEl: ae,
                        nextEl: se,
                        lastDownEl: ue,
                        cloneEl: ce,
                        cloneHidden: le,
                        dragStarted: Ce,
                        putSortable: me,
                        activeSortable: Ye.active,
                        originalEvent: r,
                        oldIndex: fe,
                        oldDraggableIndex: he,
                        newIndex: de,
                        newDraggableIndex: pe,
                        hideGhostForTarget: qe,
                        unhideGhostForTarget: Ue,
                        cloneNowHidden: function() {
                            le = !0
                        },
                        cloneNowShown: function() {
                            le = !1
                        },
                        dispatchSortableEvent: function(e) {
                            ne({
                                sortable: t,
                                name: e,
                                originalEvent: r
                            })
                        }
                    }, o))
                };

            function ne(e) {
                Q(i({
                    putSortable: me,
                    cloneEl: ce,
                    targetEl: re,
                    rootEl: ae,
                    oldIndex: fe,
                    oldDraggableIndex: he,
                    newIndex: de,
                    newDraggableIndex: pe
                }, e))
            }
            var re, ie, oe, ae, se, ue, ce, le, fe, de, he, pe, ve, me, ge, be, ye, _e, we, xe, Ce, Se, Oe, Ee, ke, Te = !1,
                je = !1,
                $e = [],
                Ae = !1,
                Pe = !1,
                Ie = [],
                Ne = !1,
                Me = [],
                Le = "undefined" !== typeof document,
                Re = m,
                Fe = h || d ? "cssFloat" : "float",
                De = Le && !g && !m && "draggable" in document.createElement("div"),
                Be = function() {
                    if (Le) {
                        if (d) return !1;
                        var e = document.createElement("x");
                        return e.style.cssText = "pointer-events:auto", "auto" === e.style.pointerEvents
                    }
                }(),
                ze = function(e, t) {
                    var n = k(e),
                        r = parseInt(n.width) - parseInt(n.paddingLeft) - parseInt(n.paddingRight) - parseInt(n.borderLeftWidth) - parseInt(n.borderRightWidth),
                        i = I(e, 0, t),
                        o = I(e, 1, t),
                        a = i && k(i),
                        s = o && k(o),
                        u = a && parseInt(a.marginLeft) + parseInt(a.marginRight) + A(i).width,
                        c = s && parseInt(s.marginLeft) + parseInt(s.marginRight) + A(o).width;
                    if ("flex" === n.display) return "column" === n.flexDirection || "column-reverse" === n.flexDirection ? "vertical" : "horizontal";
                    if ("grid" === n.display) return n.gridTemplateColumns.split(" ").length <= 1 ? "vertical" : "horizontal";
                    if (i && a["float"] && "none" !== a["float"]) {
                        var l = "left" === a["float"] ? "left" : "right";
                        return !o || "both" !== s.clear && s.clear !== l ? "horizontal" : "vertical"
                    }
                    return i && ("block" === a.display || "flex" === a.display || "table" === a.display || "grid" === a.display || u >= r && "none" === n[Fe] || o && "none" === n[Fe] && u + c > r) ? "vertical" : "horizontal"
                },
                Ve = function(e, t, n) {
                    var r = n ? e.left : e.top,
                        i = n ? e.right : e.bottom,
                        o = n ? e.width : e.height,
                        a = n ? t.left : t.top,
                        s = n ? t.right : t.bottom,
                        u = n ? t.width : t.height;
                    return r === a || i === s || r + o / 2 === a + u / 2
                },
                He = function(e, t) {
                    var n;
                    return $e.some((function(r) {
                        var i = r[U].options.emptyInsertThreshold;
                        if (i && !N(r)) {
                            var o = A(r),
                                a = e >= o.left - i && e <= o.right + i,
                                s = t >= o.top - i && t <= o.bottom + i;
                            return a && s ? n = r : void 0
                        }
                    })), n
                },
                We = function(e) {
                    function t(e, n) {
                        return function(r, i, o, a) {
                            var s = r.options.group.name && i.options.group.name && r.options.group.name === i.options.group.name;
                            if (null == e && (n || s)) return !0;
                            if (null == e || !1 === e) return !1;
                            if (n && "clone" === e) return e;
                            if ("function" === typeof e) return t(e(r, i, o, a), n)(r, i, o, a);
                            var u = (n ? r : i).options.group.name;
                            return !0 === e || "string" === typeof e && e === u || e.join && e.indexOf(u) > -1
                        }
                    }
                    var n = {},
                        r = e.group;
                    r && "object" == o(r) || (r = {
                        name: r
                    }), n.name = r.name, n.checkPull = t(r.pull, !0), n.checkPut = t(r.put), n.revertClone = r.revertClone, e.group = n
                },
                qe = function() {
                    !Be && oe && k(oe, "display", "none")
                },
                Ue = function() {
                    !Be && oe && k(oe, "display", "")
                };
            Le && !g && document.addEventListener("click", (function(e) {
                if (je) return e.preventDefault(), e.stopPropagation && e.stopPropagation(), e.stopImmediatePropagation && e.stopImmediatePropagation(), je = !1, !1
            }), !0);
            var Xe = function(e) {
                    if (re) {
                        e = e.touches ? e.touches[0] : e;
                        var t = He(e.clientX, e.clientY);
                        if (t) {
                            var n = {};
                            for (var r in e) e.hasOwnProperty(r) && (n[r] = e[r]);
                            n.target = n.rootEl = t, n.preventDefault = void 0, n.stopPropagation = void 0, t[U]._onDragOver(n)
                        }
                    }
                },
                Ge = function(e) {
                    re && re.parentNode[U]._isOutsideThisEl(e.target)
                };

            function Ye(e, t) {
                if (!e || !e.nodeType || 1 !== e.nodeType) throw "Sortable: `el` must be an HTMLElement, not ".concat({}.toString.call(e));
                this.el = e, this.options = t = s({}, t), e[U] = this;
                var n = {
                    group: null,
                    sort: !0,
                    disabled: !1,
                    store: null,
                    handle: null,
                    draggable: /^[uo]l$/i.test(e.nodeName) ? ">li" : ">*",
                    swapThreshold: 1,
                    invertSwap: !1,
                    invertedSwapThreshold: null,
                    removeCloneOnHide: !0,
                    direction: function() {
                        return ze(e, this.options)
                    },
                    ghostClass: "sortable-ghost",
                    chosenClass: "sortable-chosen",
                    dragClass: "sortable-drag",
                    ignore: "a, img",
                    filter: null,
                    preventOnFilter: !0,
                    animation: 0,
                    easing: null,
                    setData: function(e, t) {
                        e.setData("Text", t.textContent)
                    },
                    dropBubble: !1,
                    dragoverBubble: !1,
                    dataIdAttr: "data-id",
                    delay: 0,
                    delayOnTouchOnly: !1,
                    touchStartThreshold: (Number.parseInt ? Number : window).parseInt(window.devicePixelRatio, 10) || 1,
                    forceFallback: !1,
                    fallbackClass: "sortable-fallback",
                    fallbackOnBody: !1,
                    fallbackTolerance: 0,
                    fallbackOffset: {
                        x: 0,
                        y: 0
                    },
                    supportPointer: !1 !== Ye.supportPointer && "PointerEvent" in window && (!v || m),
                    emptyInsertThreshold: 5
                };
                for (var r in Z.initializePlugins(this, e, n), n) !(r in t) && (t[r] = n[r]);
                for (var i in We(t), this) "_" === i.charAt(0) && "function" === typeof this[i] && (this[i] = this[i].bind(this));
                this.nativeDraggable = !t.forceFallback && De, this.nativeDraggable && (this.options.touchStartThreshold = 1), t.supportPointer ? y(e, "pointerdown", this._onTapStart) : (y(e, "mousedown", this._onTapStart), y(e, "touchstart", this._onTapStart)), this.nativeDraggable && (y(e, "dragover", this), y(e, "dragenter", this)), $e.push(this.el), t.store && t.store.get && this.sort(t.store.get(this) || []), s(this, X())
            }

            function Ke(e) {
                e.dataTransfer && (e.dataTransfer.dropEffect = "move"), e.cancelable && e.preventDefault()
            }

            function Je(e, t, n, r, i, o, a, s) {
                var u, c, l = e[U],
                    f = l.options.onMove;
                return !window.CustomEvent || d || h ? (u = document.createEvent("Event"), u.initEvent("move", !0, !0)) : u = new CustomEvent("move", {
                    bubbles: !0,
                    cancelable: !0
                }), u.to = t, u.from = e, u.dragged = n, u.draggedRect = r, u.related = i || t, u.relatedRect = o || A(t), u.willInsertAfter = s, u.originalEvent = a, e.dispatchEvent(u), f && (c = f.call(l, u, a)), c
            }

            function Ze(e) {
                e.draggable = !1
            }

            function Qe() {
                Ne = !1
            }

            function et(e, t, n) {
                var r = A(I(n.el, 0, n.options, !0)),
                    i = q(n.el, n.options, oe),
                    o = 10;
                return t ? e.clientX < i.left - o || e.clientY < r.top && e.clientX < r.right : e.clientY < i.top - o || e.clientY < r.bottom && e.clientX < r.left
            }

            function tt(e, t, n) {
                var r = A(N(n.el, n.options.draggable)),
                    i = q(n.el, n.options, oe),
                    o = 10;
                return t ? e.clientX > i.right + o || e.clientY > r.bottom && e.clientX > r.left : e.clientY > i.bottom + o || e.clientX > r.right && e.clientY > r.top
            }

            function nt(e, t, n, r, i, o, a, s) {
                var u = r ? e.clientY : e.clientX,
                    c = r ? n.height : n.width,
                    l = r ? n.top : n.left,
                    f = r ? n.bottom : n.right,
                    d = !1;
                if (!a)
                    if (s && Ee < c * i) {
                        if (!Ae && (1 === Oe ? u > l + c * o / 2 : u < f - c * o / 2) && (Ae = !0), Ae) d = !0;
                        else if (1 === Oe ? u < l + Ee : u > f - Ee) return -Oe
                    } else if (u > l + c * (1 - i) / 2 && u < f - c * (1 - i) / 2) return rt(t);
                return d = d || a, d && (u < l + c * o / 2 || u > f - c * o / 2) ? u > l + c / 2 ? 1 : -1 : 0
            }

            function rt(e) {
                return M(re) < M(e) ? 1 : -1
            }

            function it(e) {
                var t = e.tagName + e.className + e.src + e.href + e.textContent,
                    n = t.length,
                    r = 0;
                while (n--) r += t.charCodeAt(n);
                return r.toString(36)
            }

            function ot(e) {
                Me.length = 0;
                var t = e.getElementsByTagName("input"),
                    n = t.length;
                while (n--) {
                    var r = t[n];
                    r.checked && Me.push(r)
                }
            }

            function at(e) {
                return setTimeout(e, 0)
            }

            function st(e) {
                return clearTimeout(e)
            }
            Ye.prototype = {
                constructor: Ye,
                _isOutsideThisEl: function(e) {
                    this.el.contains(e) || e === this.el || (Se = null)
                },
                _getDirection: function(e, t) {
                    return "function" === typeof this.options.direction ? this.options.direction.call(this, e, t, re) : this.options.direction
                },
                _onTapStart: function(e) {
                    if (e.cancelable) {
                        var t = this,
                            n = this.el,
                            r = this.options,
                            i = r.preventOnFilter,
                            o = e.type,
                            a = e.touches && e.touches[0] || e.pointerType && "touch" === e.pointerType && e,
                            s = (a || e).target,
                            u = e.target.shadowRoot && (e.path && e.path[0] || e.composedPath && e.composedPath()[0]) || s,
                            c = r.filter;
                        if (ot(n), !re && !(/mousedown|pointerdown/.test(o) && 0 !== e.button || r.disabled) && !u.isContentEditable && (this.nativeDraggable || !v || !s || "SELECT" !== s.tagName.toUpperCase()) && (s = C(s, r.draggable, n, !1), (!s || !s.animated) && ue !== s)) {
                            if (fe = M(s), he = M(s, r.draggable), "function" === typeof c) {
                                if (c.call(this, e, s, this)) return ne({
                                    sortable: t,
                                    rootEl: u,
                                    name: "filter",
                                    targetEl: s,
                                    toEl: n,
                                    fromEl: n
                                }), te("filter", t, {
                                    evt: e
                                }), void(i && e.preventDefault())
                            } else if (c && (c = c.split(",").some((function(r) {
                                    if (r = C(u, r.trim(), n, !1), r) return ne({
                                        sortable: t,
                                        rootEl: r,
                                        name: "filter",
                                        targetEl: s,
                                        fromEl: n,
                                        toEl: n
                                    }), te("filter", t, {
                                        evt: e
                                    }), !0
                                })), c)) return void(i && e.preventDefault());
                            r.handle && !C(u, r.handle, n, !1) || this._prepareDragStart(e, a, s)
                        }
                    }
                },
                _prepareDragStart: function(e, t, n) {
                    var r, i = this,
                        o = i.el,
                        a = i.options,
                        s = o.ownerDocument;
                    if (n && !re && n.parentNode === o) {
                        var u = A(n);
                        if (ae = o, re = n, ie = re.parentNode, se = re.nextSibling, ue = n, ve = a.group, Ye.dragged = re, ge = {
                                target: re,
                                clientX: (t || e).clientX,
                                clientY: (t || e).clientY
                            }, we = ge.clientX - u.left, xe = ge.clientY - u.top, this._lastX = (t || e).clientX, this._lastY = (t || e).clientY, re.style["will-change"] = "all", r = function() {
                                te("delayEnded", i, {
                                    evt: e
                                }), Ye.eventCanceled ? i._onDrop() : (i._disableDelayedDragEvents(), !p && i.nativeDraggable && (re.draggable = !0), i._triggerDragStart(e, t), ne({
                                    sortable: i,
                                    name: "choose",
                                    originalEvent: e
                                }), E(re, a.chosenClass, !0))
                            }, a.ignore.split(",").forEach((function(e) {
                                j(re, e.trim(), Ze)
                            })), y(s, "dragover", Xe), y(s, "mousemove", Xe), y(s, "touchmove", Xe), a.supportPointer ? (y(s, "pointerup", i._onDrop), !this.nativeDraggable && y(s, "pointercancel", i._onDrop)) : (y(s, "mouseup", i._onDrop), y(s, "touchend", i._onDrop), y(s, "touchcancel", i._onDrop)), p && this.nativeDraggable && (this.options.touchStartThreshold = 4, re.draggable = !0), te("delayStart", this, {
                                evt: e
                            }), !a.delay || a.delayOnTouchOnly && !t || this.nativeDraggable && (h || d)) r();
                        else {
                            if (Ye.eventCanceled) return void this._onDrop();
                            a.supportPointer ? (y(s, "pointerup", i._disableDelayedDrag), y(s, "pointercancel", i._disableDelayedDrag)) : (y(s, "mouseup", i._disableDelayedDrag), y(s, "touchend", i._disableDelayedDrag), y(s, "touchcancel", i._disableDelayedDrag)), y(s, "mousemove", i._delayedDragTouchMoveHandler), y(s, "touchmove", i._delayedDragTouchMoveHandler), a.supportPointer && y(s, "pointermove", i._delayedDragTouchMoveHandler), i._dragStartTimer = setTimeout(r, a.delay)
                        }
                    }
                },
                _delayedDragTouchMoveHandler: function(e) {
                    var t = e.touches ? e.touches[0] : e;
                    Math.max(Math.abs(t.clientX - this._lastX), Math.abs(t.clientY - this._lastY)) >= Math.floor(this.options.touchStartThreshold / (this.nativeDraggable && window.devicePixelRatio || 1)) && this._disableDelayedDrag()
                },
                _disableDelayedDrag: function() {
                    re && Ze(re), clearTimeout(this._dragStartTimer), this._disableDelayedDragEvents()
                },
                _disableDelayedDragEvents: function() {
                    var e = this.el.ownerDocument;
                    _(e, "mouseup", this._disableDelayedDrag), _(e, "touchend", this._disableDelayedDrag), _(e, "touchcancel", this._disableDelayedDrag), _(e, "pointerup", this._disableDelayedDrag), _(e, "pointercancel", this._disableDelayedDrag), _(e, "mousemove", this._delayedDragTouchMoveHandler), _(e, "touchmove", this._delayedDragTouchMoveHandler), _(e, "pointermove", this._delayedDragTouchMoveHandler)
                },
                _triggerDragStart: function(e, t) {
                    t = t || "touch" == e.pointerType && e, !this.nativeDraggable || t ? this.options.supportPointer ? y(document, "pointermove", this._onTouchMove) : y(document, t ? "touchmove" : "mousemove", this._onTouchMove) : (y(re, "dragend", this), y(ae, "dragstart", this._onDragStart));
                    try {
                        document.selection ? at((function() {
                            document.selection.empty()
                        })) : window.getSelection().removeAllRanges()
                    } catch (n) {}
                },
                _dragStarted: function(e, t) {
                    if (Te = !1, ae && re) {
                        te("dragStarted", this, {
                            evt: t
                        }), this.nativeDraggable && y(document, "dragover", Ge);
                        var n = this.options;
                        !e && E(re, n.dragClass, !1), E(re, n.ghostClass, !0), Ye.active = this, e && this._appendGhost(), ne({
                            sortable: this,
                            name: "start",
                            originalEvent: t
                        })
                    } else this._nulling()
                },
                _emulateDragOver: function() {
                    if (be) {
                        this._lastX = be.clientX, this._lastY = be.clientY, qe();
                        var e = document.elementFromPoint(be.clientX, be.clientY),
                            t = e;
                        while (e && e.shadowRoot) {
                            if (e = e.shadowRoot.elementFromPoint(be.clientX, be.clientY), e === t) break;
                            t = e
                        }
                        if (re.parentNode[U]._isOutsideThisEl(e), t)
                            do {
                                if (t[U]) {
                                    var n = void 0;
                                    if (n = t[U]._onDragOver({
                                            clientX: be.clientX,
                                            clientY: be.clientY,
                                            target: e,
                                            rootEl: t
                                        }), n && !this.options.dragoverBubble) break
                                }
                                e = t
                            } while (t = x(t));
                        Ue()
                    }
                },
                _onTouchMove: function(e) {
                    if (ge) {
                        var t = this.options,
                            n = t.fallbackTolerance,
                            r = t.fallbackOffset,
                            i = e.touches ? e.touches[0] : e,
                            o = oe && T(oe, !0),
                            a = oe && o && o.a,
                            s = oe && o && o.d,
                            u = Re && ke && L(ke),
                            c = (i.clientX - ge.clientX + r.x) / (a || 1) + (u ? u[0] - Ie[0] : 0) / (a || 1),
                            l = (i.clientY - ge.clientY + r.y) / (s || 1) + (u ? u[1] - Ie[1] : 0) / (s || 1);
                        if (!Ye.active && !Te) {
                            if (n && Math.max(Math.abs(i.clientX - this._lastX), Math.abs(i.clientY - this._lastY)) < n) return;
                            this._onDragStart(e, !0)
                        }
                        if (oe) {
                            o ? (o.e += c - (ye || 0), o.f += l - (_e || 0)) : o = {
                                a: 1,
                                b: 0,
                                c: 0,
                                d: 1,
                                e: c,
                                f: l
                            };
                            var f = "matrix(".concat(o.a, ",").concat(o.b, ",").concat(o.c, ",").concat(o.d, ",").concat(o.e, ",").concat(o.f, ")");
                            k(oe, "webkitTransform", f), k(oe, "mozTransform", f), k(oe, "msTransform", f), k(oe, "transform", f), ye = c, _e = l, be = i
                        }
                        e.cancelable && e.preventDefault()
                    }
                },
                _appendGhost: function() {
                    if (!oe) {
                        var e = this.options.fallbackOnBody ? document.body : ae,
                            t = A(re, !0, Re, !0, e),
                            n = this.options;
                        if (Re) {
                            ke = e;
                            while ("static" === k(ke, "position") && "none" === k(ke, "transform") && ke !== document) ke = ke.parentNode;
                            ke !== document.body && ke !== document.documentElement ? (ke === document && (ke = $()), t.top += ke.scrollTop, t.left += ke.scrollLeft) : ke = $(), Ie = L(ke)
                        }
                        oe = re.cloneNode(!0), E(oe, n.ghostClass, !1), E(oe, n.fallbackClass, !0), E(oe, n.dragClass, !0), k(oe, "transition", ""), k(oe, "transform", ""), k(oe, "box-sizing", "border-box"), k(oe, "margin", 0), k(oe, "top", t.top), k(oe, "left", t.left), k(oe, "width", t.width), k(oe, "height", t.height), k(oe, "opacity", "0.8"), k(oe, "position", Re ? "absolute" : "fixed"), k(oe, "zIndex", "100000"), k(oe, "pointerEvents", "none"), Ye.ghost = oe, e.appendChild(oe), k(oe, "transform-origin", we / parseInt(oe.style.width) * 100 + "% " + xe / parseInt(oe.style.height) * 100 + "%")
                    }
                },
                _onDragStart: function(e, t) {
                    var n = this,
                        r = e.dataTransfer,
                        i = n.options;
                    te("dragStart", this, {
                        evt: e
                    }), Ye.eventCanceled ? this._onDrop() : (te("setupClone", this), Ye.eventCanceled || (ce = W(re), ce.removeAttribute("id"), ce.draggable = !1, ce.style["will-change"] = "", this._hideClone(), E(ce, this.options.chosenClass, !1), Ye.clone = ce), n.cloneId = at((function() {
                        te("clone", n), Ye.eventCanceled || (n.options.removeCloneOnHide || ae.insertBefore(ce, re), n._hideClone(), ne({
                            sortable: n,
                            name: "clone"
                        }))
                    })), !t && E(re, i.dragClass, !0), t ? (je = !0, n._loopId = setInterval(n._emulateDragOver, 50)) : (_(document, "mouseup", n._onDrop), _(document, "touchend", n._onDrop), _(document, "touchcancel", n._onDrop), r && (r.effectAllowed = "move", i.setData && i.setData.call(n, r, re)), y(document, "drop", n), k(re, "transform", "translateZ(0)")), Te = !0, n._dragStartId = at(n._dragStarted.bind(n, t, e)), y(document, "selectstart", n), Ce = !0, window.getSelection().removeAllRanges(), v && k(document.body, "user-select", "none"))
                },
                _onDragOver: function(e) {
                    var t, n, r, o, a = this.el,
                        s = e.target,
                        u = this.options,
                        c = u.group,
                        l = Ye.active,
                        f = ve === c,
                        d = u.sort,
                        h = me || l,
                        p = this,
                        v = !1;
                    if (!Ne) {
                        if (void 0 !== e.preventDefault && e.cancelable && e.preventDefault(), s = C(s, u.draggable, a, !0), F("dragOver"), Ye.eventCanceled) return v;
                        if (re.contains(e.target) || s.animated && s.animatingX && s.animatingY || p._ignoreWhileAnimating === s) return B(!1);
                        if (je = !1, l && !u.disabled && (f ? d || (r = ie !== ae) : me === this || (this.lastPutMode = ve.checkPull(this, l, re, e)) && c.checkPut(this, l, re, e))) {
                            if (o = "vertical" === this._getDirection(e, s), t = A(re), F("dragOverValid"), Ye.eventCanceled) return v;
                            if (r) return ie = ae, D(), this._hideClone(), F("revert"), Ye.eventCanceled || (se ? ae.insertBefore(re, se) : ae.appendChild(re)), B(!0);
                            var m = N(a, u.draggable);
                            if (!m || tt(e, o, this) && !m.animated) {
                                if (m === re) return B(!1);
                                if (m && a === e.target && (s = m), s && (n = A(s)), !1 !== Je(ae, a, re, t, s, n, e, !!s)) return D(), m && m.nextSibling ? a.insertBefore(re, m.nextSibling) : a.appendChild(re), ie = a, z(), B(!0)
                            } else if (m && et(e, o, this)) {
                                var g = I(a, 0, u, !0);
                                if (g === re) return B(!1);
                                if (s = g, n = A(s), !1 !== Je(ae, a, re, t, s, n, e, !1)) return D(), a.insertBefore(re, g), ie = a, z(), B(!0)
                            } else if (s.parentNode === a) {
                                n = A(s);
                                var b, y, _ = 0,
                                    w = re.parentNode !== a,
                                    x = !Ve(re.animated && re.toRect || t, s.animated && s.toRect || n, o),
                                    S = o ? "top" : "left",
                                    O = P(s, "top", "top") || P(re, "top", "top"),
                                    T = O ? O.scrollTop : void 0;
                                if (Se !== s && (b = n[S], Ae = !1, Pe = !x && u.invertSwap || w), _ = nt(e, s, n, o, x ? 1 : u.swapThreshold, null == u.invertedSwapThreshold ? u.swapThreshold : u.invertedSwapThreshold, Pe, Se === s), 0 !== _) {
                                    var j = M(re);
                                    do {
                                        j -= _, y = ie.children[j]
                                    } while (y && ("none" === k(y, "display") || y === oe))
                                }
                                if (0 === _ || y === s) return B(!1);
                                Se = s, Oe = _;
                                var $ = s.nextElementSibling,
                                    L = !1;
                                L = 1 === _;
                                var R = Je(ae, a, re, t, s, n, e, L);
                                if (!1 !== R) return 1 !== R && -1 !== R || (L = 1 === R), Ne = !0, setTimeout(Qe, 30), D(), L && !$ ? a.appendChild(re) : s.parentNode.insertBefore(re, L ? $ : s), O && H(O, 0, T - O.scrollTop), ie = re.parentNode, void 0 === b || Pe || (Ee = Math.abs(b - A(s)[S])), z(), B(!0)
                            }
                            if (a.contains(re)) return B(!1)
                        }
                        return !1
                    }

                    function F(u, c) {
                        te(u, p, i({
                            evt: e,
                            isOwner: f,
                            axis: o ? "vertical" : "horizontal",
                            revert: r,
                            dragRect: t,
                            targetRect: n,
                            canSort: d,
                            fromSortable: h,
                            target: s,
                            completed: B,
                            onMove: function(n, r) {
                                return Je(ae, a, re, t, n, A(n), e, r)
                            },
                            changed: z
                        }, c))
                    }

                    function D() {
                        F("dragOverAnimationCapture"), p.captureAnimationState(), p !== h && h.captureAnimationState()
                    }

                    function B(t) {
                        return F("dragOverCompleted", {
                            insertion: t
                        }), t && (f ? l._hideClone() : l._showClone(p), p !== h && (E(re, me ? me.options.ghostClass : l.options.ghostClass, !1), E(re, u.ghostClass, !0)), me !== p && p !== Ye.active ? me = p : p === Ye.active && me && (me = null), h === p && (p._ignoreWhileAnimating = s), p.animateAll((function() {
                            F("dragOverAnimationComplete"), p._ignoreWhileAnimating = null
                        })), p !== h && (h.animateAll(), h._ignoreWhileAnimating = null)), (s === re && !re.animated || s === a && !s.animated) && (Se = null), u.dragoverBubble || e.rootEl || s === document || (re.parentNode[U]._isOutsideThisEl(e.target), !t && Xe(e)), !u.dragoverBubble && e.stopPropagation && e.stopPropagation(), v = !0
                    }

                    function z() {
                        de = M(re), pe = M(re, u.draggable), ne({
                            sortable: p,
                            name: "change",
                            toEl: a,
                            newIndex: de,
                            newDraggableIndex: pe,
                            originalEvent: e
                        })
                    }
                },
                _ignoreWhileAnimating: null,
                _offMoveEvents: function() {
                    _(document, "mousemove", this._onTouchMove), _(document, "touchmove", this._onTouchMove), _(document, "pointermove", this._onTouchMove), _(document, "dragover", Xe), _(document, "mousemove", Xe), _(document, "touchmove", Xe)
                },
                _offUpEvents: function() {
                    var e = this.el.ownerDocument;
                    _(e, "mouseup", this._onDrop), _(e, "touchend", this._onDrop), _(e, "pointerup", this._onDrop), _(e, "pointercancel", this._onDrop), _(e, "touchcancel", this._onDrop), _(document, "selectstart", this)
                },
                _onDrop: function(e) {
                    var t = this.el,
                        n = this.options;
                    de = M(re), pe = M(re, n.draggable), te("drop", this, {
                        evt: e
                    }), ie = re && re.parentNode, de = M(re), pe = M(re, n.draggable), Ye.eventCanceled || (Te = !1, Pe = !1, Ae = !1, clearInterval(this._loopId), clearTimeout(this._dragStartTimer), st(this.cloneId), st(this._dragStartId), this.nativeDraggable && (_(document, "drop", this), _(t, "dragstart", this._onDragStart)), this._offMoveEvents(), this._offUpEvents(), v && k(document.body, "user-select", ""), k(re, "transform", ""), e && (Ce && (e.cancelable && e.preventDefault(), !n.dropBubble && e.stopPropagation()), oe && oe.parentNode && oe.parentNode.removeChild(oe), (ae === ie || me && "clone" !== me.lastPutMode) && ce && ce.parentNode && ce.parentNode.removeChild(ce), re && (this.nativeDraggable && _(re, "dragend", this), Ze(re), re.style["will-change"] = "", Ce && !Te && E(re, me ? me.options.ghostClass : this.options.ghostClass, !1), E(re, this.options.chosenClass, !1), ne({
                        sortable: this,
                        name: "unchoose",
                        toEl: ie,
                        newIndex: null,
                        newDraggableIndex: null,
                        originalEvent: e
                    }), ae !== ie ? (de >= 0 && (ne({
                        rootEl: ie,
                        name: "add",
                        toEl: ie,
                        fromEl: ae,
                        originalEvent: e
                    }), ne({
                        sortable: this,
                        name: "remove",
                        toEl: ie,
                        originalEvent: e
                    }), ne({
                        rootEl: ie,
                        name: "sort",
                        toEl: ie,
                        fromEl: ae,
                        originalEvent: e
                    }), ne({
                        sortable: this,
                        name: "sort",
                        toEl: ie,
                        originalEvent: e
                    })), me && me.save()) : de !== fe && de >= 0 && (ne({
                        sortable: this,
                        name: "update",
                        toEl: ie,
                        originalEvent: e
                    }), ne({
                        sortable: this,
                        name: "sort",
                        toEl: ie,
                        originalEvent: e
                    })), Ye.active && (null != de && -1 !== de || (de = fe, pe = he), ne({
                        sortable: this,
                        name: "end",
                        toEl: ie,
                        originalEvent: e
                    }), this.save())))), this._nulling()
                },
                _nulling: function() {
                    te("nulling", this), ae = re = ie = oe = se = ce = ue = le = ge = be = Ce = de = pe = fe = he = Se = Oe = me = ve = Ye.dragged = Ye.ghost = Ye.clone = Ye.active = null, Me.forEach((function(e) {
                        e.checked = !0
                    })), Me.length = ye = _e = 0
                },
                handleEvent: function(e) {
                    switch (e.type) {
                        case "drop":
                        case "dragend":
                            this._onDrop(e);
                            break;
                        case "dragenter":
                        case "dragover":
                            re && (this._onDragOver(e), Ke(e));
                            break;
                        case "selectstart":
                            e.preventDefault();
                            break
                    }
                },
                toArray: function() {
                    for (var e, t = [], n = this.el.children, r = 0, i = n.length, o = this.options; r < i; r++) e = n[r], C(e, o.draggable, this.el, !1) && t.push(e.getAttribute(o.dataIdAttr) || it(e));
                    return t
                },
                sort: function(e, t) {
                    var n = {},
                        r = this.el;
                    this.toArray().forEach((function(e, t) {
                        var i = r.children[t];
                        C(i, this.options.draggable, r, !1) && (n[e] = i)
                    }), this), t && this.captureAnimationState(), e.forEach((function(e) {
                        n[e] && (r.removeChild(n[e]), r.appendChild(n[e]))
                    })), t && this.animateAll()
                },
                save: function() {
                    var e = this.options.store;
                    e && e.set && e.set(this)
                },
                closest: function(e, t) {
                    return C(e, t || this.options.draggable, this.el, !1)
                },
                option: function(e, t) {
                    var n = this.options;
                    if (void 0 === t) return n[e];
                    var r = Z.modifyOption(this, e, t);
                    n[e] = "undefined" !== typeof r ? r : t, "group" === e && We(n)
                },
                destroy: function() {
                    te("destroy", this);
                    var e = this.el;
                    e[U] = null, _(e, "mousedown", this._onTapStart), _(e, "touchstart", this._onTapStart), _(e, "pointerdown", this._onTapStart), this.nativeDraggable && (_(e, "dragover", this), _(e, "dragenter", this)), Array.prototype.forEach.call(e.querySelectorAll("[draggable]"), (function(e) {
                        e.removeAttribute("draggable")
                    })), this._onDrop(), this._disableDelayedDragEvents(), $e.splice($e.indexOf(this.el), 1), this.el = e = null
                },
                _hideClone: function() {
                    if (!le) {
                        if (te("hideClone", this), Ye.eventCanceled) return;
                        k(ce, "display", "none"), this.options.removeCloneOnHide && ce.parentNode && ce.parentNode.removeChild(ce), le = !0
                    }
                },
                _showClone: function(e) {
                    if ("clone" === e.lastPutMode) {
                        if (le) {
                            if (te("showClone", this), Ye.eventCanceled) return;
                            re.parentNode != ae || this.options.group.revertClone ? se ? ae.insertBefore(ce, se) : ae.appendChild(ce) : ae.insertBefore(ce, re), this.options.group.revertClone && this.animate(re, ce), k(ce, "display", ""), le = !1
                        }
                    } else this._hideClone()
                }
            }, Le && y(document, "touchmove", (function(e) {
                (Ye.active || Te) && e.cancelable && e.preventDefault()
            })), Ye.utils = {
                on: y,
                off: _,
                css: k,
                find: j,
                is: function(e, t) {
                    return !!C(e, t, e, !1)
                },
                extend: D,
                throttle: z,
                closest: C,
                toggleClass: E,
                clone: W,
                index: M,
                nextTick: at,
                cancelNextTick: st,
                detectDirection: ze,
                getChild: I,
                expando: U
            }, Ye.get = function(e) {
                return e[U]
            }, Ye.mount = function() {
                for (var e = arguments.length, t = new Array(e), n = 0; n < e; n++) t[n] = arguments[n];
                t[0].constructor === Array && (t = t[0]), t.forEach((function(e) {
                    if (!e.prototype || !e.prototype.constructor) throw "Sortable: Mounted plugin must be a constructor function, not ".concat({}.toString.call(e));
                    e.utils && (Ye.utils = i(i({}, Ye.utils), e.utils)), Z.mount(e)
                }))
            }, Ye.create = function(e, t) {
                return new Ye(e, t)
            }, Ye.version = l;
            var ut, ct, lt, ft, dt, ht, pt = [],
                vt = !1;

            function mt() {
                function e() {
                    for (var e in this.defaults = {
                            scroll: !0,
                            forceAutoScrollFallback: !1,
                            scrollSensitivity: 30,
                            scrollSpeed: 10,
                            bubbleScroll: !0
                        }, this) "_" === e.charAt(0) && "function" === typeof this[e] && (this[e] = this[e].bind(this))
                }
                return e.prototype = {
                    dragStarted: function(e) {
                        var t = e.originalEvent;
                        this.sortable.nativeDraggable ? y(document, "dragover", this._handleAutoScroll) : this.options.supportPointer ? y(document, "pointermove", this._handleFallbackAutoScroll) : t.touches ? y(document, "touchmove", this._handleFallbackAutoScroll) : y(document, "mousemove", this._handleFallbackAutoScroll)
                    },
                    dragOverCompleted: function(e) {
                        var t = e.originalEvent;
                        this.options.dragOverBubble || t.rootEl || this._handleAutoScroll(t)
                    },
                    drop: function() {
                        this.sortable.nativeDraggable ? _(document, "dragover", this._handleAutoScroll) : (_(document, "pointermove", this._handleFallbackAutoScroll), _(document, "touchmove", this._handleFallbackAutoScroll), _(document, "mousemove", this._handleFallbackAutoScroll)), bt(), gt(), V()
                    },
                    nulling: function() {
                        dt = ct = ut = vt = ht = lt = ft = null, pt.length = 0
                    },
                    _handleFallbackAutoScroll: function(e) {
                        this._handleAutoScroll(e, !0)
                    },
                    _handleAutoScroll: function(e, t) {
                        var n = this,
                            r = (e.touches ? e.touches[0] : e).clientX,
                            i = (e.touches ? e.touches[0] : e).clientY,
                            o = document.elementFromPoint(r, i);
                        if (dt = e, t || this.options.forceAutoScrollFallback || h || d || v) {
                            yt(e, this.options, o, t);
                            var a = F(o, !0);
                            !vt || ht && r === lt && i === ft || (ht && bt(), ht = setInterval((function() {
                                var o = F(document.elementFromPoint(r, i), !0);
                                o !== a && (a = o, gt()), yt(e, n.options, o, t)
                            }), 10), lt = r, ft = i)
                        } else {
                            if (!this.options.bubbleScroll || F(o, !0) === $()) return void gt();
                            yt(e, this.options, F(o, !1), !1)
                        }
                    }
                }, s(e, {
                    pluginName: "scroll",
                    initializeByDefault: !0
                })
            }

            function gt() {
                pt.forEach((function(e) {
                    clearInterval(e.pid)
                })), pt = []
            }

            function bt() {
                clearInterval(ht)
            }
            var yt = z((function(e, t, n, r) {
                    if (t.scroll) {
                        var i, o = (e.touches ? e.touches[0] : e).clientX,
                            a = (e.touches ? e.touches[0] : e).clientY,
                            s = t.scrollSensitivity,
                            u = t.scrollSpeed,
                            c = $(),
                            l = !1;
                        ct !== n && (ct = n, gt(), ut = t.scroll, i = t.scrollFn, !0 === ut && (ut = F(n, !0)));
                        var f = 0,
                            d = ut;
                        do {
                            var h = d,
                                p = A(h),
                                v = p.top,
                                m = p.bottom,
                                g = p.left,
                                b = p.right,
                                y = p.width,
                                _ = p.height,
                                w = void 0,
                                x = void 0,
                                C = h.scrollWidth,
                                S = h.scrollHeight,
                                O = k(h),
                                E = h.scrollLeft,
                                T = h.scrollTop;
                            h === c ? (w = y < C && ("auto" === O.overflowX || "scroll" === O.overflowX || "visible" === O.overflowX), x = _ < S && ("auto" === O.overflowY || "scroll" === O.overflowY || "visible" === O.overflowY)) : (w = y < C && ("auto" === O.overflowX || "scroll" === O.overflowX), x = _ < S && ("auto" === O.overflowY || "scroll" === O.overflowY));
                            var j = w && (Math.abs(b - o) <= s && E + y < C) - (Math.abs(g - o) <= s && !!E),
                                P = x && (Math.abs(m - a) <= s && T + _ < S) - (Math.abs(v - a) <= s && !!T);
                            if (!pt[f])
                                for (var I = 0; I <= f; I++) pt[I] || (pt[I] = {});
                            pt[f].vx == j && pt[f].vy == P && pt[f].el === h || (pt[f].el = h, pt[f].vx = j, pt[f].vy = P, clearInterval(pt[f].pid), 0 == j && 0 == P || (l = !0, pt[f].pid = setInterval(function() {
                                r && 0 === this.layer && Ye.active._onTouchMove(dt);
                                var t = pt[this.layer].vy ? pt[this.layer].vy * u : 0,
                                    n = pt[this.layer].vx ? pt[this.layer].vx * u : 0;
                                "function" === typeof i && "continue" !== i.call(Ye.dragged.parentNode[U], n, t, e, dt, pt[this.layer].el) || H(pt[this.layer].el, n, t)
                            }.bind({
                                layer: f
                            }), 24))), f++
                        } while (t.bubbleScroll && d !== c && (d = F(d, !1)));
                        vt = l
                    }
                }), 30),
                _t = function(e) {
                    var t = e.originalEvent,
                        n = e.putSortable,
                        r = e.dragEl,
                        i = e.activeSortable,
                        o = e.dispatchSortableEvent,
                        a = e.hideGhostForTarget,
                        s = e.unhideGhostForTarget;
                    if (t) {
                        var u = n || i;
                        a();
                        var c = t.changedTouches && t.changedTouches.length ? t.changedTouches[0] : t,
                            l = document.elementFromPoint(c.clientX, c.clientY);
                        s(), u && !u.el.contains(l) && (o("spill"), this.onSpill({
                            dragEl: r,
                            putSortable: n
                        }))
                    }
                };

            function wt() {}

            function xt() {}
            wt.prototype = {
                startIndex: null,
                dragStart: function(e) {
                    var t = e.oldDraggableIndex;
                    this.startIndex = t
                },
                onSpill: function(e) {
                    var t = e.dragEl,
                        n = e.putSortable;
                    this.sortable.captureAnimationState(), n && n.captureAnimationState();
                    var r = I(this.sortable.el, this.startIndex, this.options);
                    r ? this.sortable.el.insertBefore(t, r) : this.sortable.el.appendChild(t), this.sortable.animateAll(), n && n.animateAll()
                },
                drop: _t
            }, s(wt, {
                pluginName: "revertOnSpill"
            }), xt.prototype = {
                onSpill: function(e) {
                    var t = e.dragEl,
                        n = e.putSortable,
                        r = n || this.sortable;
                    r.captureAnimationState(), t.parentNode && t.parentNode.removeChild(t), r.animateAll()
                },
                drop: _t
            }, s(xt, {
                pluginName: "removeOnSpill"
            });
            Ye.mount(new mt), Ye.mount(xt, wt), t["a"] = Ye
        
