var render = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "书签",
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
                        model: e.bookmarkForm
                    }
                }, [t("el-form-item", {
                    attrs: {
                        label: "书名"
                    }
                }, [t("el-input", {
                    attrs: {
                        readonly: ""
                    },
                    model: {
                        value: e.bookmarkForm.bookName,
                        callback: function(t) {
                            e.$set(e.bookmarkForm, "bookName", t)
                        },
                        expression: "bookmarkForm.bookName"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "作者"
                    }
                }, [t("el-input", {
                    attrs: {
                        readonly: ""
                    },
                    model: {
                        value: e.bookmarkForm.bookAuthor,
                        callback: function(t) {
                            e.$set(e.bookmarkForm, "bookAuthor", t)
                        },
                        expression: "bookmarkForm.bookAuthor"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "章节"
                    }
                }, [t("el-input", {
                    attrs: {
                        readonly: ""
                    },
                    model: {
                        value: e.bookmarkForm.chapterName,
                        callback: function(t) {
                            e.$set(e.bookmarkForm, "chapterName", t)
                        },
                        expression: "bookmarkForm.chapterName"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "内容"
                    }
                }, [t("el-input", {
                    attrs: {
                        type: "textarea",
                        rows: 5,
                        readonly: ""
                    },
                    model: {
                        value: e.bookmarkForm.bookText,
                        callback: function(t) {
                            e.$set(e.bookmarkForm, "bookText", t)
                        },
                        expression: "bookmarkForm.bookText"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "备注"
                    }
                }, [t("el-input", {
                    attrs: {
                        type: "textarea",
                        rows: 3
                    },
                    model: {
                        value: e.bookmarkForm.content,
                        callback: function(t) {
                            e.$set(e.bookmarkForm, "content", t)
                        },
                        expression: "bookmarkForm.content"
                    }
                })], 1)], 1), t("div", {
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
