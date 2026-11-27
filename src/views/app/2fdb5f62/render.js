var render = function() {
                var e = this,
                    t = e._self._c;
                return t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "书籍封面",
                        visible: e.show,
                        width: e.dialogSmallWidth,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("div", {
                    staticClass: "custom-dialog-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [e._v("书籍封面 "), t("span", {
                    staticClass: "float-right span-btn",
                    class: {
                        loading: e.loadingMore
                    },
                    on: {
                        click: e.searchBookSourceByEventStream
                    }
                }, [e.loadingMore ? t("i", {
                    staticClass: "el-icon-loading"
                }) : e._e(), e._v(" " + e._s(e.loadingMore ? "加载中..." : "加载更多") + " ")])])]), e.show ? t("div", {
                    staticClass: "book-cover-container"
                }, [e.coverList.length ? t("div", {
                    staticClass: "cover-list"
                }, e._l(e.coverList, (function(o, n) {
                    return t("div", {
                        key: "cover-" + n,
                        staticClass: "book-cover-wrapper",
                        on: {
                            click: function(t) {
                                return e.setCover(o)
                            }
                        }
                    }, [t("el-image", {
                        staticClass: "book-cover",
                        attrs: {
                            src: o,
                            fit: "cover",
                            lazy: ""
                        }
                    }, [t("img", {
                        staticClass: "el-image__inner",
                        staticStyle: {
                            "object-fit": "cover"
                        },
                        attrs: {
                            slot: "error",
                            src: e.noCover,
                            alt: ""
                        },
                        slot: "error"
                    })])], 1)
                })), 0) : t("div", {
                    staticClass: "empty-notice"
                }, [e._v(" 暂无其它来源 ")])]) : e._e()])
            };
