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
                    }, [e._v("来源(" + e._s(e.showBookSourceList.length) + ")")]), t("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: !e.isSearching,
                            expression: "!isSearching"
                        }],
                        class: {
                            "title-btn": !0,
                            loading: e.loading
                        }
                    }, [t("el-select", {
                        staticClass: "booksource-group-select",
                        attrs: {
                            size: "mini",
                            filterable: "",
                            placeholder: "全部分组"
                        },
                        model: {
                            value: e.bookSourceGroup,
                            callback: function(t) {
                                e.bookSourceGroup = t
                            },
                            expression: "bookSourceGroup"
                        }
                    }, e._l(e.$store.getters.bookSourceGroupList, (function(e, n) {
                        return t("el-option", {
                            key: "source-group-" + n,
                            attrs: {
                                label: e.name + " (" + e.count + ")",
                                value: e.value
                            }
                        })
                    })), 1), t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: function(t) {
                                e.expand = !e.expand
                            }
                        }
                    }, [e._v(" " + e._s(e.expand ? "收起" : "展开") + " ")]), t("span", {
                        class: {
                            loading: e.loading
                        },
                        on: {
                            click: e.refresh
                        }
                    }, [e.loading ? t("i", {
                        staticClass: "el-icon-loading"
                    }) : e._e(), e._v(" " + e._s(e.loading ? "刷新中..." : "刷新") + " ")]), t("span", {
                        class: {
                            loading: e.loadingMore
                        },
                        on: {
                            click: e.searchBookSourceByEventStream
                        }
                    }, [e.loadingMore ? t("i", {
                        staticClass: "el-icon-loading"
                    }) : e._e(), e._v(" " + e._s(e.loadingMore ? "加载中..." : "加载更多") + " ")]), t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: function(t) {
                                e.isSearching = !e.isSearching
                            }
                        }
                    }, [e._v(" " + e._s(e.isSearching ? "取消" : "搜索") + " ")])], 1), t("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: e.isSearching,
                            expression: "isSearching"
                        }],
                        class: {
                            "title-btn": !0,
                            loading: e.loadingMore
                        }
                    }, [t("el-input", {
                        staticClass: "booksource-filter-input",
                        attrs: {
                            size: "mini",
                            placeholder: "搜索书源"
                        },
                        model: {
                            value: e.searchKeyword,
                            callback: function(t) {
                                e.searchKeyword = t
                            },
                            expression: "searchKeyword"
                        }
                    }), t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: function(t) {
                                e.isSearching = !e.isSearching
                            }
                        }
                    }, [e._v(" " + e._s(e.isSearching ? "取消" : "搜索") + " ")])], 1)]), t("div", {
                        ref: "sourceList",
                        staticClass: "data-wrapper",
                        class: {
                            night: e.$store.getters.isNight,
                            day: !e.$store.getters.isNight,
                            expand: e.expand
                        }
                    }, [t("div", {
                        staticClass: "source-list"
                    }, e._l(e.showBookSourceList, (function(n, a) {
                        return t("div", {
                            key: a,
                            ref: "source",
                            refInFor: !0,
                            staticClass: "source-item",
                            class: {
                                selected: e.isSelected(n)
                            },
                            on: {
                                click: function(t) {
                                    return e.changeBookSource(n)
                                }
                            }
                        }, [t("div", {
                            staticClass: "source-title"
                        }, [t("div", {
                            staticClass: "source-name"
                        }, [e._v(" " + e._s(n.originName) + " ")]), t("div", {
                            staticClass: "source-time"
                        }, [e._v(" " + e._s(n.time ? "⏱ " + n.time + "ms" : "") + " ")])]), t("div", {
                            staticClass: "source-latest-chapter"
                        }, [e._v(" " + e._s(n.latestChapterTitle || "无最新章节") + " ")])])
                    })), 0)])])
                };
