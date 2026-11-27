var render = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "书籍设置",
                        visible: e.show,
                        width: e.dialogSmallWidth,
                        top: e.dialogTop,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("el-form", {
                    attrs: {
                        model: e.bookConfig
                    }
                }, [t("el-form-item", {
                    attrs: {
                        label: "PDF图片宽度"
                    }
                }, [t("el-select", {
                    staticClass: "setting-select",
                    attrs: {
                        size: "mini",
                        filterable: "",
                        placeholder: "请选择PDF图片宽度"
                    },
                    model: {
                        value: e.bookConfig.pdfImageWidth,
                        callback: function(t) {
                            e.$set(e.bookConfig, "pdfImageWidth", t)
                        },
                        expression: "bookConfig.pdfImageWidth"
                    }
                }, e._l(e.pdfImageWidthList, (function(e, o) {
                    return t("el-option", {
                        key: "pdf-image-width-" + o,
                        attrs: {
                            label: e.name,
                            value: e.value
                        }
                    })
                })), 1)], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("el-button", {
                    attrs: {
                        size: "medium",
                        type: "primary"
                    },
                    on: {
                        click: e.save
                    }
                }, [e._v("保 存")]), t("el-button", {
                    attrs: {
                        size: "medium",
                        type: "primary"
                    },
                    on: {
                        click: e.cancel
                    }
                }, [e._v("关 闭")])], 1)], 1) : e._e()
            };
