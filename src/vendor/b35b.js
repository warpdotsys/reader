// chunk-vendors webpack 模块 b35b（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            var r = n("./7037.js")["default"];
            n("./e01a.js"), n("./944a.js"), n("./99af.js"), n("./c975.js"), n("./c0b6.js"), n("./b0c0.js"), n("./0c47.js"), n("./23dc.js"), n("./a9e3.js"), n("./b8bf.js"), n("./7a82.js"), n("./d3b7.js"), e.exports = function(e) {
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
                }, n.p = "/dist/", n(n.s = 94)
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
                22: function(e, t) {
                    e.exports = n("./12f2.js")
                },
                4: function(e, t) {
                    e.exports = n("./d010.js")
                },
                94: function(e, t, n) {
                    "use strict";
                    n.r(t);
                    var r = function() {
                            var e = this,
                                t = e.$createElement,
                                n = e._self._c || t;
                            return n("div", {
                                staticClass: "el-switch",
                                class: {
                                    "is-disabled": e.switchDisabled,
                                    "is-checked": e.checked
                                },
                                attrs: {
                                    role: "switch",
                                    "aria-checked": e.checked,
                                    "aria-disabled": e.switchDisabled
                                },
                                on: {
                                    click: function(t) {
                                        return t.preventDefault(), e.switchValue(t)
                                    }
                                }
                            }, [n("input", {
                                ref: "input",
                                staticClass: "el-switch__input",
                                attrs: {
                                    type: "checkbox",
                                    id: e.id,
                                    name: e.name,
                                    "true-value": e.activeValue,
                                    "false-value": e.inactiveValue,
                                    disabled: e.switchDisabled
                                },
                                on: {
                                    change: e.handleChange,
                                    keydown: function(t) {
                                        return !("button" in t) && e._k(t.keyCode, "enter", 13, t.key, "Enter") ? null : e.switchValue(t)
                                    }
                                }
                            }), e.inactiveIconClass || e.inactiveText ? n("span", {
                                class: ["el-switch__label", "el-switch__label--left", e.checked ? "" : "is-active"]
                            }, [e.inactiveIconClass ? n("i", {
                                class: [e.inactiveIconClass]
                            }) : e._e(), !e.inactiveIconClass && e.inactiveText ? n("span", {
                                attrs: {
                                    "aria-hidden": e.checked
                                }
                            }, [e._v(e._s(e.inactiveText))]) : e._e()]) : e._e(), n("span", {
                                ref: "core",
                                staticClass: "el-switch__core",
                                style: {
                                    width: e.coreWidth + "px"
                                }
                            }), e.activeIconClass || e.activeText ? n("span", {
                                class: ["el-switch__label", "el-switch__label--right", e.checked ? "is-active" : ""]
                            }, [e.activeIconClass ? n("i", {
                                class: [e.activeIconClass]
                            }) : e._e(), !e.activeIconClass && e.activeText ? n("span", {
                                attrs: {
                                    "aria-hidden": !e.checked
                                }
                            }, [e._v(e._s(e.activeText))]) : e._e()]) : e._e()])
                        },
                        i = [];
                    r._withStripped = !0;
                    var o = n(4),
                        a = n.n(o),
                        s = n(22),
                        u = n.n(s),
                        c = n(11),
                        l = n.n(c),
                        f = {
                            name: "ElSwitch",
                            mixins: [u()("input"), l.a, a.a],
                            inject: {
                                elForm: {
                                    default: ""
                                }
                            },
                            props: {
                                value: {
                                    type: [Boolean, String, Number],
                                    default: !1
                                },
                                disabled: {
                                    type: Boolean,
                                    default: !1
                                },
                                width: {
                                    type: Number,
                                    default: 40
                                },
                                activeIconClass: {
                                    type: String,
                                    default: ""
                                },
                                inactiveIconClass: {
                                    type: String,
                                    default: ""
                                },
                                activeText: String,
                                inactiveText: String,
                                activeColor: {
                                    type: String,
                                    default: ""
                                },
                                inactiveColor: {
                                    type: String,
                                    default: ""
                                },
                                activeValue: {
                                    type: [Boolean, String, Number],
                                    default: !0
                                },
                                inactiveValue: {
                                    type: [Boolean, String, Number],
                                    default: !1
                                },
                                name: {
                                    type: String,
                                    default: ""
                                },
                                validateEvent: {
                                    type: Boolean,
                                    default: !0
                                },
                                id: String
                            },
                            data: function() {
                                return {
                                    coreWidth: this.width
                                }
                            },
                            created: function() {
                                ~[this.activeValue, this.inactiveValue].indexOf(this.value) || this.$emit("input", this.inactiveValue)
                            },
                            computed: {
                                checked: function() {
                                    return this.value === this.activeValue
                                },
                                switchDisabled: function() {
                                    return this.disabled || (this.elForm || {}).disabled
                                }
                            },
                            watch: {
                                checked: function() {
                                    this.$refs.input.checked = this.checked, (this.activeColor || this.inactiveColor) && this.setBackgroundColor(), this.validateEvent && this.dispatch("ElFormItem", "el.form.change", [this.value])
                                }
                            },
                            methods: {
                                handleChange: function(e) {
                                    var t = this,
                                        n = this.checked ? this.inactiveValue : this.activeValue;
                                    this.$emit("input", n), this.$emit("change", n), this.$nextTick((function() {
                                        t.$refs.input && (t.$refs.input.checked = t.checked)
                                    }))
                                },
                                setBackgroundColor: function() {
                                    var e = this.checked ? this.activeColor : this.inactiveColor;
                                    this.$refs.core.style.borderColor = e, this.$refs.core.style.backgroundColor = e
                                },
                                switchValue: function() {
                                    !this.switchDisabled && this.handleChange()
                                },
                                getMigratingConfig: function() {
                                    return {
                                        props: {
                                            "on-color": "on-color is renamed to active-color.",
                                            "off-color": "off-color is renamed to inactive-color.",
                                            "on-text": "on-text is renamed to active-text.",
                                            "off-text": "off-text is renamed to inactive-text.",
                                            "on-value": "on-value is renamed to active-value.",
                                            "off-value": "off-value is renamed to inactive-value.",
                                            "on-icon-class": "on-icon-class is renamed to active-icon-class.",
                                            "off-icon-class": "off-icon-class is renamed to inactive-icon-class."
                                        }
                                    }
                                }
                            },
                            mounted: function() {
                                this.coreWidth = this.width || 40, (this.activeColor || this.inactiveColor) && this.setBackgroundColor(), this.$refs.input.checked = this.checked
                            }
                        },
                        d = f,
                        h = n(0),
                        p = Object(h["a"])(d, r, i, !1, null, null, null);
                    p.options.__file = "packages/switch/src/component.vue";
                    var v = p.exports;
                    v.install = function(e) {
                        e.component(v.name, v)
                    };
                    t["default"] = v
                }
            })
        
