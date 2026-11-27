var render = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: e.isAdd ? "新增用户" : "修改用户",
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
                        model: e.userForm
                    }
                }, [t("el-form-item", {
                    attrs: {
                        label: "用户名"
                    }
                }, [t("el-input", {
                    attrs: {
                        autocomplete: "off",
                        readonly: !e.isAdd
                    },
                    model: {
                        value: e.userForm.username,
                        callback: function(t) {
                            e.$set(e.userForm, "username", t)
                        },
                        expression: "userForm.username"
                    }
                })], 1), e.isAdd ? t("el-form-item", {
                    attrs: {
                        label: "密码"
                    }
                }, [t("el-input", {
                    attrs: {
                        type: "password",
                        autocomplete: "off",
                        "show-password": ""
                    },
                    model: {
                        value: e.userForm.password,
                        callback: function(t) {
                            e.$set(e.userForm, "password", t)
                        },
                        expression: "userForm.password"
                    }
                })], 1) : e._e(), t("el-form-item", {
                    attrs: {
                        label: "书籍上限"
                    }
                }, [t("el-input-number", {
                    attrs: {
                        min: 1
                    },
                    model: {
                        value: e.userForm.bookLimit,
                        callback: function(t) {
                            e.$set(e.userForm, "bookLimit", t)
                        },
                        expression: "userForm.bookLimit"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "书源上限"
                    }
                }, [t("el-input-number", {
                    attrs: {
                        min: 1
                    },
                    model: {
                        value: e.userForm.bookSourceLimit,
                        callback: function(t) {
                            e.$set(e.userForm, "bookSourceLimit", t)
                        },
                        expression: "userForm.bookSourceLimit"
                    }
                })], 1), t("el-checkbox", {
                    model: {
                        value: e.userForm.enableWebdav,
                        callback: function(t) {
                            e.$set(e.userForm, "enableWebdav", t)
                        },
                        expression: "userForm.enableWebdav"
                    }
                }, [e._v("启用webdav")]), t("el-checkbox", {
                    model: {
                        value: e.userForm.enableLocalStore,
                        callback: function(t) {
                            e.$set(e.userForm, "enableLocalStore", t)
                        },
                        expression: "userForm.enableLocalStore"
                    }
                }, [e._v("启用书仓")]), t("el-checkbox", {
                    model: {
                        value: e.userForm.enableBookSource,
                        callback: function(t) {
                            e.$set(e.userForm, "enableBookSource", t)
                        },
                        expression: "userForm.enableBookSource"
                    }
                }, [e._v("编辑书源")]), t("el-checkbox", {
                    model: {
                        value: e.userForm.enableRssSource,
                        callback: function(t) {
                            e.$set(e.userForm, "enableRssSource", t)
                        },
                        expression: "userForm.enableRssSource"
                    }
                }, [e._v("编辑RSS源")])], 1), t("div", {
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
