var render = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "替换规则",
                        visible: e.show,
                        width: e.dialogWidth,
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
                        model: e.ruleForm
                    }
                }, [t("el-form-item", {
                    attrs: {
                        label: "名称"
                    }
                }, [t("el-input", {
                    model: {
                        value: e.ruleForm.name,
                        callback: function(t) {
                            e.$set(e.ruleForm, "name", t)
                        },
                        expression: "ruleForm.name"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "规则"
                    }
                }, [t("el-input", {
                    model: {
                        value: e.ruleForm.pattern,
                        callback: function(t) {
                            e.$set(e.ruleForm, "pattern", t)
                        },
                        expression: "ruleForm.pattern"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "替换为"
                    }
                }, [t("el-input", {
                    model: {
                        value: e.ruleForm.replacement,
                        callback: function(t) {
                            e.$set(e.ruleForm, "replacement", t)
                        },
                        expression: "ruleForm.replacement"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "替换范围"
                    }
                }, [t("el-input", {
                    model: {
                        value: e.ruleForm.scope,
                        callback: function(t) {
                            e.$set(e.ruleForm, "scope", t)
                        },
                        expression: "ruleForm.scope"
                    }
                })], 1), t("el-checkbox", {
                    model: {
                        value: e.ruleForm.isRegex,
                        callback: function(t) {
                            e.$set(e.ruleForm, "isRegex", t)
                        },
                        expression: "ruleForm.isRegex"
                    }
                }, [e._v("使用正则表达式")]), t("el-checkbox", {
                    model: {
                        value: e.ruleForm.isEnabled,
                        callback: function(t) {
                            e.$set(e.ruleForm, "isEnabled", t)
                        },
                        expression: "ruleForm.isEnabled"
                    }
                }, [e._v("是否启用")])], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("el-button", {
                    attrs: {
                        size: "medium"
                    },
                    on: {
                        click: e.cancel
                    }
                }, [e._v("取 消")]), t("el-button", {
                    attrs: {
                        size: "medium",
                        type: "primary"
                    },
                    on: {
                        click: e.save
                    }
                }, [e._v("确 定")])], 1)], 1) : e._e()
            };
