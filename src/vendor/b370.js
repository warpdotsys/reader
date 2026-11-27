// chunk-vendors webpack 模块 b370（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            var r = n("./7037.js")["default"];
            n("./e01a.js"), n("./944a.js"), n("./99af.js"), n("./4160.js"), n("./c975.js"), n("./fb6a.js"), n("./c0b6.js"), n("./b0c0.js"), n("./e9f5.js"), n("./7d54.js"), n("./0c47.js"), n("./23dc.js"), n("./a9e3.js"), n("./b8bf.js"), n("./7a82.js"), n("./d3b7.js"), n("./ac1f.js"), n("./5319.js"), n("./159b.js"), n("./4795.js"), e.exports = function(e) {
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
                }, n.p = "/dist/", n(n.s = 130)
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
                11: function(e, t) {
                    e.exports = n("./2bb5.js")
                },
                12: function(e, t) {
                    e.exports = n("./417f.js")
                },
                130: function(e, t, n) {
                    "use strict";
                    n.r(t);
                    var r, i, o = n(12),
                        a = n.n(o),
                        s = n(4),
                        u = n.n(s),
                        c = n(11),
                        l = n.n(c),
                        f = n(14),
                        d = n.n(f),
                        h = n(36),
                        p = n.n(h),
                        v = n(3),
                        m = {
                            name: "ElDropdown",
                            componentName: "ElDropdown",
                            mixins: [u.a, l.a],
                            directives: {
                                Clickoutside: a.a
                            },
                            components: {
                                ElButton: d.a,
                                ElButtonGroup: p.a
                            },
                            provide: function() {
                                return {
                                    dropdown: this
                                }
                            },
                            props: {
                                trigger: {
                                    type: String,
                                    default: "hover"
                                },
                                type: String,
                                size: {
                                    type: String,
                                    default: ""
                                },
                                splitButton: Boolean,
                                hideOnClick: {
                                    type: Boolean,
                                    default: !0
                                },
                                placement: {
                                    type: String,
                                    default: "bottom-end"
                                },
                                visibleArrow: {
                                    default: !0
                                },
                                showTimeout: {
                                    type: Number,
                                    default: 250
                                },
                                hideTimeout: {
                                    type: Number,
                                    default: 150
                                },
                                tabindex: {
                                    type: Number,
                                    default: 0
                                },
                                disabled: {
                                    type: Boolean,
                                    default: !1
                                }
                            },
                            data: function() {
                                return {
                                    timeout: null,
                                    visible: !1,
                                    triggerElm: null,
                                    menuItems: null,
                                    menuItemsArray: null,
                                    dropdownElm: null,
                                    focusing: !1,
                                    listId: "dropdown-menu-" + Object(v["generateId"])()
                                }
                            },
                            computed: {
                                dropdownSize: function() {
                                    return this.size || (this.$ELEMENT || {}).size
                                }
                            },
                            mounted: function() {
                                this.$on("menu-item-click", this.handleMenuItemClick)
                            },
                            watch: {
                                visible: function(e) {
                                    this.broadcast("ElDropdownMenu", "visible", e), this.$emit("visible-change", e)
                                },
                                focusing: function(e) {
                                    var t = this.$el.querySelector(".el-dropdown-selfdefine");
                                    t && (e ? t.className += " focusing" : t.className = t.className.replace("focusing", ""))
                                }
                            },
                            methods: {
                                getMigratingConfig: function() {
                                    return {
                                        props: {
                                            "menu-align": "menu-align is renamed to placement."
                                        }
                                    }
                                },
                                show: function() {
                                    var e = this;
                                    this.disabled || (clearTimeout(this.timeout), this.timeout = setTimeout((function() {
                                        e.visible = !0
                                    }), "click" === this.trigger ? 0 : this.showTimeout))
                                },
                                hide: function() {
                                    var e = this;
                                    this.disabled || (this.removeTabindex(), this.tabindex >= 0 && this.resetTabindex(this.triggerElm), clearTimeout(this.timeout), this.timeout = setTimeout((function() {
                                        e.visible = !1
                                    }), "click" === this.trigger ? 0 : this.hideTimeout))
                                },
                                handleClick: function() {
                                    this.disabled || (this.visible ? this.hide() : this.show())
                                },
                                handleTriggerKeyDown: function(e) {
                                    var t = e.keyCode;
                                    [38, 40].indexOf(t) > -1 ? (this.removeTabindex(), this.resetTabindex(this.menuItems[0]), this.menuItems[0].focus(), e.preventDefault(), e.stopPropagation()) : 13 === t ? this.handleClick() : [9, 27].indexOf(t) > -1 && this.hide()
                                },
                                handleItemKeyDown: function(e) {
                                    var t = e.keyCode,
                                        n = e.target,
                                        r = this.menuItemsArray.indexOf(n),
                                        i = this.menuItemsArray.length - 1,
                                        o = void 0;
                                    [38, 40].indexOf(t) > -1 ? (o = 38 === t ? 0 !== r ? r - 1 : 0 : r < i ? r + 1 : i, this.removeTabindex(), this.resetTabindex(this.menuItems[o]), this.menuItems[o].focus(), e.preventDefault(), e.stopPropagation()) : 13 === t ? (this.triggerElmFocus(), n.click(), this.hideOnClick && (this.visible = !1)) : [9, 27].indexOf(t) > -1 && (this.hide(), this.triggerElmFocus())
                                },
                                resetTabindex: function(e) {
                                    this.removeTabindex(), e.setAttribute("tabindex", "0")
                                },
                                removeTabindex: function() {
                                    this.triggerElm.setAttribute("tabindex", "-1"), this.menuItemsArray.forEach((function(e) {
                                        e.setAttribute("tabindex", "-1")
                                    }))
                                },
                                initAria: function() {
                                    this.dropdownElm.setAttribute("id", this.listId), this.triggerElm.setAttribute("aria-haspopup", "list"), this.triggerElm.setAttribute("aria-controls", this.listId), this.splitButton || (this.triggerElm.setAttribute("role", "button"), this.triggerElm.setAttribute("tabindex", this.tabindex), this.triggerElm.setAttribute("class", (this.triggerElm.getAttribute("class") || "") + " el-dropdown-selfdefine"))
                                },
                                initEvent: function() {
                                    var e = this,
                                        t = this.trigger,
                                        n = this.show,
                                        r = this.hide,
                                        i = this.handleClick,
                                        o = this.splitButton,
                                        a = this.handleTriggerKeyDown,
                                        s = this.handleItemKeyDown;
                                    this.triggerElm = o ? this.$refs.trigger.$el : this.$slots["default"][0].elm;
                                    var u = this.dropdownElm;
                                    this.triggerElm.addEventListener("keydown", a), u.addEventListener("keydown", s, !0), o || (this.triggerElm.addEventListener("focus", (function() {
                                        e.focusing = !0
                                    })), this.triggerElm.addEventListener("blur", (function() {
                                        e.focusing = !1
                                    })), this.triggerElm.addEventListener("click", (function() {
                                        e.focusing = !1
                                    }))), "hover" === t ? (this.triggerElm.addEventListener("mouseenter", n), this.triggerElm.addEventListener("mouseleave", r), u.addEventListener("mouseenter", n), u.addEventListener("mouseleave", r)) : "click" === t && this.triggerElm.addEventListener("click", i)
                                },
                                handleMenuItemClick: function(e, t) {
                                    this.hideOnClick && (this.visible = !1), this.$emit("command", e, t)
                                },
                                triggerElmFocus: function() {
                                    this.triggerElm.focus && this.triggerElm.focus()
                                },
                                initDomOperation: function() {
                                    this.dropdownElm = this.popperElm, this.menuItems = this.dropdownElm.querySelectorAll("[tabindex='-1']"), this.menuItemsArray = [].slice.call(this.menuItems), this.initEvent(), this.initAria()
                                }
                            },
                            render: function(e) {
                                var t = this,
                                    n = this.hide,
                                    r = this.splitButton,
                                    i = this.type,
                                    o = this.dropdownSize,
                                    a = this.disabled,
                                    s = function(e) {
                                        t.$emit("click", e), n()
                                    },
                                    u = null;
                                if (r) u = e("el-button-group", [e("el-button", {
                                    attrs: {
                                        type: i,
                                        size: o,
                                        disabled: a
                                    },
                                    nativeOn: {
                                        click: s
                                    }
                                }, [this.$slots["default"]]), e("el-button", {
                                    ref: "trigger",
                                    attrs: {
                                        type: i,
                                        size: o,
                                        disabled: a
                                    },
                                    class: "el-dropdown__caret-button"
                                }, [e("i", {
                                    class: "el-dropdown__icon el-icon-arrow-down"
                                })])]);
                                else {
                                    u = this.$slots["default"];
                                    var c = u[0].data || {},
                                        l = c.attrs,
                                        f = void 0 === l ? {} : l;
                                    a && !f.disabled && (f.disabled = !0, c.attrs = f)
                                }
                                var d = a ? null : this.$slots.dropdown;
                                return e("div", {
                                    class: "el-dropdown",
                                    directives: [{
                                        name: "clickoutside",
                                        value: n
                                    }],
                                    attrs: {
                                        "aria-disabled": a
                                    }
                                }, [u, d])
                            }
                        },
                        g = m,
                        b = n(0),
                        y = Object(b["a"])(g, r, i, !1, null, null, null);
                    y.options.__file = "packages/dropdown/src/dropdown.vue";
                    var _ = y.exports;
                    _.install = function(e) {
                        e.component(_.name, _)
                    };
                    t["default"] = _
                },
                14: function(e, t) {
                    e.exports = n("./eedf.js")
                },
                3: function(e, t) {
                    e.exports = n("./8122.js")
                },
                36: function(e, t) {
                    e.exports = n("./845f.js")
                },
                4: function(e, t) {
                    e.exports = n("./d010.js")
                }
            })
        
