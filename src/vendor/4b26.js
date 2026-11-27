// chunk-vendors webpack 模块 4b26（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            n("./4160.js"), n("./14d9.js"), n("./a434.js"), n("./e9f5.js"), n("./7d54.js"), n("./7a82.js"), n("./d3b7.js"), n("./ac1f.js"), n("./1276.js"), n("./498a.js"), n("./159b.js"), n("./4795.js"), t.__esModule = !0;
            var r = n("./2b0e.js"),
                i = a(r),
                o = n("./5924.js");

            function a(e) {
                return e && e.__esModule ? e : {
                    default: e
                }
            }
            var s = !1,
                u = !1,
                c = void 0,
                l = function() {
                    if (!i["default"].prototype.$isServer) {
                        var e = d.modalDom;
                        return e ? s = !0 : (s = !1, e = document.createElement("div"), d.modalDom = e, e.addEventListener("touchmove", (function(e) {
                            e.preventDefault(), e.stopPropagation()
                        })), e.addEventListener("click", (function() {
                            d.doOnModalClick && d.doOnModalClick()
                        }))), e
                    }
                },
                f = {},
                d = {
                    modalFade: !0,
                    getInstance: function(e) {
                        return f[e]
                    },
                    register: function(e, t) {
                        e && t && (f[e] = t)
                    },
                    deregister: function(e) {
                        e && (f[e] = null, delete f[e])
                    },
                    nextZIndex: function() {
                        return d.zIndex++
                    },
                    modalStack: [],
                    doOnModalClick: function() {
                        var e = d.modalStack[d.modalStack.length - 1];
                        if (e) {
                            var t = d.getInstance(e.id);
                            t && t.closeOnClickModal && t.close()
                        }
                    },
                    openModal: function(e, t, n, r, a) {
                        if (!i["default"].prototype.$isServer && e && void 0 !== t) {
                            this.modalFade = a;
                            for (var u = this.modalStack, c = 0, f = u.length; c < f; c++) {
                                var d = u[c];
                                if (d.id === e) return
                            }
                            var h = l();
                            if ((0, o.addClass)(h, "v-modal"), this.modalFade && !s && (0, o.addClass)(h, "v-modal-enter"), r) {
                                var p = r.trim().split(/\s+/);
                                p.forEach((function(e) {
                                    return (0, o.addClass)(h, e)
                                }))
                            }
                            setTimeout((function() {
                                (0, o.removeClass)(h, "v-modal-enter")
                            }), 200), n && n.parentNode && 11 !== n.parentNode.nodeType ? n.parentNode.appendChild(h) : document.body.appendChild(h), t && (h.style.zIndex = t), h.tabIndex = 0, h.style.display = "", this.modalStack.push({
                                id: e,
                                zIndex: t,
                                modalClass: r
                            })
                        }
                    },
                    closeModal: function(e) {
                        var t = this.modalStack,
                            n = l();
                        if (t.length > 0) {
                            var r = t[t.length - 1];
                            if (r.id === e) {
                                if (r.modalClass) {
                                    var i = r.modalClass.trim().split(/\s+/);
                                    i.forEach((function(e) {
                                        return (0, o.removeClass)(n, e)
                                    }))
                                }
                                t.pop(), t.length > 0 && (n.style.zIndex = t[t.length - 1].zIndex)
                            } else
                                for (var a = t.length - 1; a >= 0; a--)
                                    if (t[a].id === e) {
                                        t.splice(a, 1);
                                        break
                                    }
                        }
                        0 === t.length && (this.modalFade && (0, o.addClass)(n, "v-modal-leave"), setTimeout((function() {
                            0 === t.length && (n.parentNode && n.parentNode.removeChild(n), n.style.display = "none", d.modalDom = void 0), (0, o.removeClass)(n, "v-modal-leave")
                        }), 200))
                    }
                };
            Object.defineProperty(d, "zIndex", {
                configurable: !0,
                get: function() {
                    return u || (c = c || (i["default"].prototype.$ELEMENT || {}).zIndex || 2e3, u = !0), c
                },
                set: function(e) {
                    c = e
                }
            });
            var h = function() {
                if (!i["default"].prototype.$isServer && d.modalStack.length > 0) {
                    var e = d.modalStack[d.modalStack.length - 1];
                    if (!e) return;
                    var t = d.getInstance(e.id);
                    return t
                }
            };
            i["default"].prototype.$isServer || window.addEventListener("keydown", (function(e) {
                if (27 === e.keyCode) {
                    var t = h();
                    t && t.closeOnPressEscape && (t.handleClose ? t.handleClose() : t.handleAction ? t.handleAction("cancel") : t.close())
                }
            })), t["default"] = d
        
