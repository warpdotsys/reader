var render = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        visible: e.show,
                        width: e.dialogWidth,
                        top: e.dialogTop,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        },
                        opened: e.opend
                    }
                }, [t("div", {
                    staticClass: "custom-dialog-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [t("span", {
                    staticClass: "title-input"
                }, [t("el-input", {
                    staticClass: "search-input",
                    attrs: {
                        size: "mini",
                        placeholder: "搜索书籍内容"
                    },
                    nativeOn: {
                        keyup: function(t) {
                            return !t.type.indexOf("key") && e._k(t.keyCode, "enter", 13, t.key, "Enter") ? null : e.searchBookContent(-1)
                        }
                    },
                    model: {
                        value: e.keyword,
                        callback: function(t) {
                            e.keyword = t
                        },
                        expression: "keyword"
                    }
                }, [t("i", {
                    staticClass: "el-input__icon el-icon-search",
                    attrs: {
                        slot: "prefix"
                    },
                    slot: "prefix"
                })])], 1)])]), t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    ref: "resultTable",
                    attrs: {
                        data: e.searchResultList,
                        height: e.dialogContentHeight
                    },
                    on: {
                        "row-click": e.clickRow
                    }
                }, [t("el-table-column", {
                    attrs: {
                        property: "chapterTitle",
                        "min-width": "100px",
                        label: "章节"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "resultText",
                        "min-width": "250px",
                        label: "搜索结果"
                    }
                })], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium",
                        disabled: e.loading
                    },
                    on: {
                        click: function(t) {
                            return e.searchBookContent(e.lastIndex)
                        }
                    }
                }, [e._v(e._s(e.loading ? "加载中" : "加载更多"))]), e.lastScrollTop > 0 ? t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.restoreScrollTop
                    }
                }, [e._v("跳转上次位置")]) : e._e(), t("el-button", {
                    attrs: {
                        size: "medium"
                    },
                    on: {
                        click: e.cancel
                    }
                }, [e._v("取消")])], 1)]) : e._e()
            };
