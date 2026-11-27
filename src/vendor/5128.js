// chunk-vendors webpack 模块 5128（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            n("./a9e3.js"), n("./e25e.js"), n("./4795.js"), t.__esModule = !0, t.PopupManager = void 0;
            var r = n("./2b0e.js"),
                i = d(r),
                o = n("./7f4d.js"),
                a = d(o),
                s = n("./4b26.js"),
                u = d(s),
                c = n("./e62d.js"),
                l = d(c),
                f = n("./5924.js");

            function d(e) {
                return e && e.__esModule ? e : {
                    default: e
                }
            }
            var h = 1,
                p = void 0;
            t["default"] = {
                props: {
                    visible: {
                        type: Boolean,
                        default: !1
                    },
                    openDelay: {},
                    closeDelay: {},
                    zIndex: {},
                    modal: {
                        type: Boolean,
                        default: !1
                    },
                    modalFade: {
                        type: Boolean,
                        default: !0
                    },
                    modalClass: {},
                    modalAppendToBody: {
                        type: Boolean,
                        default: !1
                    },
                    lockScroll: {
                        type: Boolean,
                        default: !0
                    },
                    closeOnPressEscape: {
                        type: Boolean,
                        default: !1
                    },
                    closeOnClickModal: {
                        type: Boolean,
                        default: !1
                    }
                },
                beforeMount: function() {
                    this._popupId = "popup-" + h++, u["default"].register(this._popupId, this)
                },
                beforeDestroy: function() {
                    u["default"].deregister(this._popupId), u["default"].closeModal(this._popupId), this.restoreBodyStyle()
                },
                data: function() {
                    return {
                        opened: !1,
                        bodyPaddingRight: null,
                        computedBodyPaddingRight: 0,
                        withoutHiddenClass: !0,
                        rendered: !1
                    }
                },
                watch: {
                    visible: function(e) {
                        var t = this;
                        if (e) {
                            if (this._opening) return;
                            this.rendered ? this.open() : (this.rendered = !0, i["default"].nextTick((function() {
                                t.open()
                            })))
                        } else this.close()
                    }
                },
                methods: {
                    open: function(e) {
                        var t = this;
                        this.rendered || (this.rendered = !0);
                        var n = (0, a["default"])({}, this.$props || this, e);
                        this._closeTimer && (clearTimeout(this._closeTimer), this._closeTimer = null), clearTimeout(this._openTimer);
                        var r = Number(n.openDelay);
                        r > 0 ? this._openTimer = setTimeout((function() {
                            t._openTimer = null, t.doOpen(n)
                        }), r) : this.doOpen(n)
                    },
                    doOpen: function(e) {
                        if (!this.$isServer && (!this.willOpen || this.willOpen()) && !this.opened) {
                            this._opening = !0;
                            var t = this.$el,
                                n = e.modal,
                                r = e.zIndex;
                            if (r && (u["default"].zIndex = r), n && (this._closing && (u["default"].closeModal(this._popupId), this._closing = !1), u["default"].openModal(this._popupId, u["default"].nextZIndex(), this.modalAppendToBody ? void 0 : t, e.modalClass, e.modalFade), e.lockScroll)) {
                                this.withoutHiddenClass = !(0, f.hasClass)(document.body, "el-popup-parent--hidden"), this.withoutHiddenClass && (this.bodyPaddingRight = document.body.style.paddingRight, this.computedBodyPaddingRight = parseInt((0, f.getStyle)(document.body, "paddingRight"), 10)), p = (0, l["default"])();
                                var i = document.documentElement.clientHeight < document.body.scrollHeight,
                                    o = (0, f.getStyle)(document.body, "overflowY");
                                p > 0 && (i || "scroll" === o) && this.withoutHiddenClass && (document.body.style.paddingRight = this.computedBodyPaddingRight + p + "px"), (0, f.addClass)(document.body, "el-popup-parent--hidden")
                            }
                            "static" === getComputedStyle(t).position && (t.style.position = "absolute"), t.style.zIndex = u["default"].nextZIndex(), this.opened = !0, this.onOpen && this.onOpen(), this.doAfterOpen()
                        }
                    },
                    doAfterOpen: function() {
                        this._opening = !1
                    },
                    close: function() {
                        var e = this;
                        if (!this.willClose || this.willClose()) {
                            null !== this._openTimer && (clearTimeout(this._openTimer), this._openTimer = null), clearTimeout(this._closeTimer);
                            var t = Number(this.closeDelay);
                            t > 0 ? this._closeTimer = setTimeout((function() {
                                e._closeTimer = null, e.doClose()
                            }), t) : this.doClose()
                        }
                    },
                    doClose: function() {
                        this._closing = !0, this.onClose && this.onClose(), this.lockScroll && setTimeout(this.restoreBodyStyle, 200), this.opened = !1, this.doAfterClose()
                    },
                    doAfterClose: function() {
                        u["default"].closeModal(this._popupId), this._closing = !1
                    },
                    restoreBodyStyle: function() {
                        this.modal && this.withoutHiddenClass && (document.body.style.paddingRight = this.bodyPaddingRight, (0, f.removeClass)(document.body, "el-popup-parent--hidden")), this.withoutHiddenClass = !0
                    }
                }
            }, t.PopupManager = u["default"]
        
