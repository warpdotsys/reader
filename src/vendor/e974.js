// chunk-vendors webpack 模块 e974（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            n("./c975.js"), n("./b0c0.js"), n("./a9e3.js"), n("./ac1f.js"), n("./00b4.js"), t.__esModule = !0;
            var r = n("./2b0e.js"),
                i = a(r),
                o = n("./5128.js");

            function a(e) {
                return e && e.__esModule ? e : {
                    default: e
                }
            }
            var s = i["default"].prototype.$isServer ? function() {} : n("./6167.js"),
                u = function(e) {
                    return e.stopPropagation()
                };
            t["default"] = {
                props: {
                    transformOrigin: {
                        type: [Boolean, String],
                        default: !0
                    },
                    placement: {
                        type: String,
                        default: "bottom"
                    },
                    boundariesPadding: {
                        type: Number,
                        default: 5
                    },
                    reference: {},
                    popper: {},
                    offset: {
                        default: 0
                    },
                    value: Boolean,
                    visibleArrow: Boolean,
                    arrowOffset: {
                        type: Number,
                        default: 35
                    },
                    appendToBody: {
                        type: Boolean,
                        default: !0
                    },
                    popperOptions: {
                        type: Object,
                        default: function() {
                            return {
                                gpuAcceleration: !1
                            }
                        }
                    }
                },
                data: function() {
                    return {
                        showPopper: !1,
                        currentPlacement: ""
                    }
                },
                watch: {
                    value: {
                        immediate: !0,
                        handler: function(e) {
                            this.showPopper = e, this.$emit("input", e)
                        }
                    },
                    showPopper: function(e) {
                        this.disabled || (e ? this.updatePopper() : this.destroyPopper(), this.$emit("input", e))
                    }
                },
                methods: {
                    createPopper: function() {
                        var e = this;
                        if (!this.$isServer && (this.currentPlacement = this.currentPlacement || this.placement, /^(top|bottom|left|right)(-start|-end)?$/g.test(this.currentPlacement))) {
                            var t = this.popperOptions,
                                n = this.popperElm = this.popperElm || this.popper || this.$refs.popper,
                                r = this.referenceElm = this.referenceElm || this.reference || this.$refs.reference;
                            !r && this.$slots.reference && this.$slots.reference[0] && (r = this.referenceElm = this.$slots.reference[0].elm), n && r && (this.visibleArrow && this.appendArrow(n), this.appendToBody && document.body.appendChild(this.popperElm), this.popperJS && this.popperJS.destroy && this.popperJS.destroy(), t.placement = this.currentPlacement, t.offset = this.offset, t.arrowOffset = this.arrowOffset, this.popperJS = new s(r, n, t), this.popperJS.onCreate((function(t) {
                                e.$emit("created", e), e.resetTransformOrigin(), e.$nextTick(e.updatePopper)
                            })), "function" === typeof t.onUpdate && this.popperJS.onUpdate(t.onUpdate), this.popperJS._popper.style.zIndex = o.PopupManager.nextZIndex(), this.popperElm.addEventListener("click", u))
                        }
                    },
                    updatePopper: function() {
                        var e = this.popperJS;
                        e ? (e.update(), e._popper && (e._popper.style.zIndex = o.PopupManager.nextZIndex())) : this.createPopper()
                    },
                    doDestroy: function(e) {
                        !this.popperJS || this.showPopper && !e || (this.popperJS.destroy(), this.popperJS = null)
                    },
                    destroyPopper: function() {
                        this.popperJS && this.resetTransformOrigin()
                    },
                    resetTransformOrigin: function() {
                        if (this.transformOrigin) {
                            var e = {
                                    top: "bottom",
                                    bottom: "top",
                                    left: "right",
                                    right: "left"
                                },
                                t = this.popperJS._popper.getAttribute("x-placement").split("-")[0],
                                n = e[t];
                            this.popperJS._popper.style.transformOrigin = "string" === typeof this.transformOrigin ? this.transformOrigin : ["top", "bottom"].indexOf(t) > -1 ? "center " + n : n + " center"
                        }
                    },
                    appendArrow: function(e) {
                        var t = void 0;
                        if (!this.appended) {
                            for (var n in this.appended = !0, e.attributes)
                                if (/^_v-/.test(e.attributes[n].name)) {
                                    t = e.attributes[n].name;
                                    break
                                } var r = document.createElement("div");
                            t && r.setAttribute(t, ""), r.setAttribute("x-arrow", ""), r.className = "popper__arrow", e.appendChild(r)
                        }
                    }
                },
                beforeDestroy: function() {
                    this.doDestroy(!0), this.popperElm && this.popperElm.parentNode === document.body && (this.popperElm.removeEventListener("click", u), document.body.removeChild(this.popperElm))
                },
                deactivated: function() {
                    this.$options.beforeDestroy[0].call(this)
                }
            }
        
