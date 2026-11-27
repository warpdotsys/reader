// chunk-vendors webpack 模块 6ac9（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            var r = n("./7037.js")["default"];
            n("./e01a.js"), n("./944a.js"), n("./99af.js"), n("./c975.js"), n("./277d.js"), n("./c0b6.js"), n("./b0c0.js"), n("./0c47.js"), n("./23dc.js"), n("./a9e3.js"), n("./b8bf.js"), n("./7a82.js"), n("./d3b7.js"), n("./4795.js"), e.exports = function(e) {
                var t = {};

                function n(r) {
                    if (t[r]) return t[r].exports;
                    var i = t[r] = {
                        i: r,
                        l: !1,
                        exports: {}
                    };
                    return e[r].call(i.exports, i, i.exports, n), i.l = !0, i.exports
                }
                return n.m = e, n.c = t, n.d = function(e, t, r) {
                    n.o(e, t) || Object.defineProperty(e, t, {
                        enumerable: !0,
                        get: r
                    })
                }, n.r = function(e) {
                    "undefined" !== typeof Symbol && Symbol.toStringTag && Object.defineProperty(e, Symbol.toStringTag, {
                        value: "Module"
                    }), Object.defineProperty(e, "__esModule", {
                        value: !0
                    })
                }, n.t = function(e, t) {
                    if (1 & t && (e = n(e)), 8 & t) return e;
                    if (4 & t && "object" === r(e) && e && e.__esModule) return e;
                    var i = Object.create(null);
                    if (n.r(i), Object.defineProperty(i, "default", {
                            enumerable: !0,
                            value: e
                        }), 2 & t && "string" != typeof e)
                        for (var o in e) n.d(i, o, function(t) {
                            return e[t]
                        }.bind(null, o));
                    return i
                }, n.n = function(e) {
                    var t = e && e.__esModule ? function() {
                        return e["default"]
                    } : function() {
                        return e
                    };
                    return n.d(t, "a", t), t
                }, n.o = function(e, t) {
                    return Object.prototype.hasOwnProperty.call(e, t)
                }, n.p = "/dist/", n(n.s = 78)
            }({
                0: function(e, t, n) {
                    "use strict";

                    function r(e, t, n, r, i, o, a, s) {
                        var u, c = "function" === typeof e ? e.options : e;
                        if (t && (c.render = t, c.staticRenderFns = n, c._compiled = !0), r && (c.functional = !0), o && (c._scopeId = "data-v-" + o), a ? (u = function(e) {
                                e = e || this.$vnode && this.$vnode.ssrContext || this.parent && this.parent.$vnode && this.parent.$vnode.ssrContext, e || "undefined" === typeof __VUE_SSR_CONTEXT__ || (e = __VUE_SSR_CONTEXT__), i && i.call(this, e), e && e._registeredComponents && e._registeredComponents.add(a)
                            }, c._ssrRegister = u) : i && (u = s ? function() {
                                i.call(this, this.$root.$options.shadowRoot)
                            } : i), u)
                            if (c.functional) {
                                c._injectStyles = u;
                                var l = c.render;
                                c.render = function(e, t) {
                                    return u.call(t), l(e, t)
                                }
                            } else {
                                var f = c.beforeCreate;
                                c.beforeCreate = f ? [].concat(f, u) : [u]
                            } return {
                            exports: e,
                            options: c
                        }
                    }
                    n.d(t, "a", (function() {
                        return r
                    }))
                },
                2: function(e, t) {
                    e.exports = n("./5924.js")
                },
                3: function(e, t) {
                    e.exports = n("./8122.js")
                },
                5: function(e, t) {
                    e.exports = n("./e974.js")
                },
                7: function(e, t) {
                    e.exports = n("./2b0e.js")
                },
                78: function(e, t, n) {
                    "use strict";
                    n.r(t);
                    var r = function() {
                            var e = this,
                                t = e.$createElement,
                                n = e._self._c || t;
                            return n("span", [n("transition", {
                                attrs: {
                                    name: e.transition
                                },
                                on: {
                                    "after-enter": e.handleAfterEnter,
                                    "after-leave": e.handleAfterLeave
                                }
                            }, [n("div", {
                                directives: [{
                                    name: "show",
                                    rawName: "v-show",
                                    value: !e.disabled && e.showPopper,
                                    expression: "!disabled && showPopper"
                                }],
                                ref: "popper",
                                staticClass: "el-popover el-popper",
                                class: [e.popperClass, e.content && "el-popover--plain"],
                                style: {
                                    width: e.width + "px"
                                },
                                attrs: {
                                    role: "tooltip",
                                    id: e.tooltipId,
                                    "aria-hidden": e.disabled || !e.showPopper ? "true" : "false"
                                }
                            }, [e.title ? n("div", {
                                staticClass: "el-popover__title",
                                domProps: {
                                    textContent: e._s(e.title)
                                }
                            }) : e._e(), e._t("default", [e._v(e._s(e.content))])], 2)]), n("span", {
                                ref: "wrapper",
                                staticClass: "el-popover__reference-wrapper"
                            }, [e._t("reference")], 2)], 1)
                        },
                        i = [];
                    r._withStripped = !0;
                    var o = n(5),
                        a = n.n(o),
                        s = n(2),
                        u = n(3),
                        c = {
                            name: "ElPopover",
                            mixins: [a.a],
                            props: {
                                trigger: {
                                    type: String,
                                    default: "click",
                                    validator: function(e) {
                                        return ["click", "focus", "hover", "manual"].indexOf(e) > -1
                                    }
                                },
                                openDelay: {
                                    type: Number,
                                    default: 0
                                },
                                closeDelay: {
                                    type: Number,
                                    default: 200
                                },
                                title: String,
                                disabled: Boolean,
                                content: String,
                                reference: {},
                                popperClass: String,
                                width: {},
                                visibleArrow: {
                                    default: !0
                                },
                                arrowOffset: {
                                    type: Number,
                                    default: 0
                                },
                                transition: {
                                    type: String,
                                    default: "fade-in-linear"
                                },
                                tabindex: {
                                    type: Number,
                                    default: 0
                                }
                            },
                            computed: {
                                tooltipId: function() {
                                    return "el-popover-" + Object(u["generateId"])()
                                }
                            },
                            watch: {
                                showPopper: function(e) {
                                    this.disabled || (e ? this.$emit("show") : this.$emit("hide"))
                                }
                            },
                            mounted: function() {
                                var e = this,
                                    t = this.referenceElm = this.reference || this.$refs.reference,
                                    n = this.popper || this.$refs.popper;
                                !t && this.$refs.wrapper.children && (t = this.referenceElm = this.$refs.wrapper.children[0]), t && (Object(s["addClass"])(t, "el-popover__reference"), t.setAttribute("aria-describedby", this.tooltipId), t.setAttribute("tabindex", this.tabindex), n.setAttribute("tabindex", 0), "click" !== this.trigger && (Object(s["on"])(t, "focusin", (function() {
                                    e.handleFocus();
                                    var n = t.__vue__;
                                    n && "function" === typeof n.focus && n.focus()
                                })), Object(s["on"])(n, "focusin", this.handleFocus), Object(s["on"])(t, "focusout", this.handleBlur), Object(s["on"])(n, "focusout", this.handleBlur)), Object(s["on"])(t, "keydown", this.handleKeydown), Object(s["on"])(t, "click", this.handleClick)), "click" === this.trigger ? (Object(s["on"])(t, "click", this.doToggle), Object(s["on"])(document, "click", this.handleDocumentClick)) : "hover" === this.trigger ? (Object(s["on"])(t, "mouseenter", this.handleMouseEnter), Object(s["on"])(n, "mouseenter", this.handleMouseEnter), Object(s["on"])(t, "mouseleave", this.handleMouseLeave), Object(s["on"])(n, "mouseleave", this.handleMouseLeave)) : "focus" === this.trigger && (this.tabindex < 0 && console.warn("[Element Warn][Popover]a negative taindex means that the element cannot be focused by tab key"), t.querySelector("input, textarea") ? (Object(s["on"])(t, "focusin", this.doShow), Object(s["on"])(t, "focusout", this.doClose)) : (Object(s["on"])(t, "mousedown", this.doShow), Object(s["on"])(t, "mouseup", this.doClose)))
                            },
                            beforeDestroy: function() {
                                this.cleanup()
                            },
                            deactivated: function() {
                                this.cleanup()
                            },
                            methods: {
                                doToggle: function() {
                                    this.showPopper = !this.showPopper
                                },
                                doShow: function() {
                                    this.showPopper = !0
                                },
                                doClose: function() {
                                    this.showPopper = !1
                                },
                                handleFocus: function() {
                                    Object(s["addClass"])(this.referenceElm, "focusing"), "click" !== this.trigger && "focus" !== this.trigger || (this.showPopper = !0)
                                },
                                handleClick: function() {
                                    Object(s["removeClass"])(this.referenceElm, "focusing")
                                },
                                handleBlur: function() {
                                    Object(s["removeClass"])(this.referenceElm, "focusing"), "click" !== this.trigger && "focus" !== this.trigger || (this.showPopper = !1)
                                },
                                handleMouseEnter: function() {
                                    var e = this;
                                    clearTimeout(this._timer), this.openDelay ? this._timer = setTimeout((function() {
                                        e.showPopper = !0
                                    }), this.openDelay) : this.showPopper = !0
                                },
                                handleKeydown: function(e) {
                                    27 === e.keyCode && "manual" !== this.trigger && this.doClose()
                                },
                                handleMouseLeave: function() {
                                    var e = this;
                                    clearTimeout(this._timer), this.closeDelay ? this._timer = setTimeout((function() {
                                        e.showPopper = !1
                                    }), this.closeDelay) : this.showPopper = !1
                                },
                                handleDocumentClick: function(e) {
                                    var t = this.reference || this.$refs.reference,
                                        n = this.popper || this.$refs.popper;
                                    !t && this.$refs.wrapper.children && (t = this.referenceElm = this.$refs.wrapper.children[0]), this.$el && t && !this.$el.contains(e.target) && !t.contains(e.target) && n && !n.contains(e.target) && (this.showPopper = !1)
                                },
                                handleAfterEnter: function() {
                                    this.$emit("after-enter")
                                },
                                handleAfterLeave: function() {
                                    this.$emit("after-leave"), this.doDestroy()
                                },
                                cleanup: function() {
                                    (this.openDelay || this.closeDelay) && clearTimeout(this._timer)
                                }
                            },
                            destroyed: function() {
                                var e = this.reference;
                                Object(s["off"])(e, "click", this.doToggle), Object(s["off"])(e, "mouseup", this.doClose), Object(s["off"])(e, "mousedown", this.doShow), Object(s["off"])(e, "focusin", this.doShow), Object(s["off"])(e, "focusout", this.doClose), Object(s["off"])(e, "mousedown", this.doShow), Object(s["off"])(e, "mouseup", this.doClose), Object(s["off"])(e, "mouseleave", this.handleMouseLeave), Object(s["off"])(e, "mouseenter", this.handleMouseEnter), Object(s["off"])(document, "click", this.handleDocumentClick)
                            }
                        },
                        l = c,
                        f = n(0),
                        d = Object(f["a"])(l, r, i, !1, null, null, null);
                    d.options.__file = "packages/popover/src/main.vue";
                    var h = d.exports,
                        p = function(e, t, n) {
                            var r = t.expression ? t.value : t.arg,
                                i = n.context.$refs[r];
                            i && (Array.isArray(i) ? i[0].$refs.reference = e : i.$refs.reference = e)
                        },
                        v = {
                            bind: function(e, t, n) {
                                p(e, t, n)
                            },
                            inserted: function(e, t, n) {
                                p(e, t, n)
                            }
                        },
                        m = n(7),
                        g = n.n(m);
                    g.a.directive("popover", v), h.install = function(e) {
                        e.directive("popover", v), e.component(h.name, h)
                    }, h.directive = v;
                    t["default"] = h
                }
            })
        
