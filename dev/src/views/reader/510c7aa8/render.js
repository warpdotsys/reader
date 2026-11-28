var render = function() {
                    var e = this,
                        t = e._self._c;
                    return t("div", {
                        staticClass: "popup-wrapper",
                        style: e.popupTheme
                    }, [t("div", {
                        staticClass: "title-zone"
                    }, [t("div", {
                        staticClass: "title"
                    }, [e._v("书架(" + e._s(e.shelfBooks.length) + ")")]), t("div", {
                        class: {
                            "title-btn": !0,
                            loading: e.refreshLoading
                        }
                    }, [t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: function(t) {
                                e.expand = !e.expand
                            }
                        }
                    }, [e._v(" " + e._s(e.expand ? "收起" : "展开") + " ")]), t("span", {
                        class: {
                            loading: e.refreshLoading
                        },
                        on: {
                            click: e.refreshShelf
                        }
                    }, [e.refreshLoading ? t("i", {
                        staticClass: "el-icon-loading"
                    }) : e._e(), e._v(" " + e._s(e.refreshLoading ? "刷新中..." : "刷新") + " ")])])]), t("div", {
                        ref: "bookList",
                        staticClass: "data-wrapper",
                        class: {
                            night: e.$store.getters.isNight,
                            day: !e.$store.getters.isNight,
                            expand: e.expand
                        }
                    }, [t("div", {
                        staticClass: "shelfbook-list"
                    }, [e.shelfBooks.length && "yes" === e.$store.getters.shelfConfig.virtualOptimize ? t("BookShelfVirtualList", {
                        attrs: {
                            bookList: e.shelfBooks,
                            visible: e.visible
                        },
                        on: {
                            changeBook: e.changeBook
                        }
                    }) : e._l(e.shelfBooks, (function(n) {
                        return t("div", {
                            key: n.bookUrl,
                            ref: "book",
                            refInFor: !0,
                            staticClass: "book-item",
                            class: {
                                selected: e.isSelected(n)
                            },
                            on: {
                                click: function(t) {
                                    return e.changeBook(n)
                                }
                            }
                        }, [t("div", {
                            staticClass: "book-title"
                        }, [t("div", {
                            staticClass: "book-name"
                        }, [e._v(" " + e._s(n.name) + " ")]), n.totalChapterNum - 1 - n.durChapterIndex ? t("div", {
                            staticClass: "book-progress"
                        }, [e._v(" " + e._s(n.totalChapterNum - 1 - n.durChapterIndex) + " ")]) : e._e()]), t("div", {
                            staticClass: "chapter-text"
                        }, [e._v(" " + e._s(n.durChapterTitle) + " ")])])
                    }))], 2)])])
                };
