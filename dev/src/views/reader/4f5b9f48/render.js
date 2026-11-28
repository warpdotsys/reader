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
                    }, [e._v(" 目录 "), e.catalog.length ? t("span", [e._v("(" + e._s(e.catalog.length) + ")")]) : e._e()]), t("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: !e.isSearching,
                            expression: "!isSearching"
                        }],
                        class: {
                            "title-btn": !0
                        }
                    }, [e.catalog.length ? t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: function(t) {
                                e.expand = !e.expand
                            }
                        }
                    }, [e._v(" " + e._s(e.expand ? "收起" : "展开") + " ")]) : e._e(), e.catalog.length ? t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: function(t) {
                                e.asc = !e.asc
                            }
                        }
                    }, [e._v(e._s(e.asc ? "倒序" : "顺序"))]) : e._e(), e.catalog.length ? t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: e.toTop
                        }
                    }, [e._v("顶部")]) : e._e(), e.catalog.length ? t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: e.toBottom
                        }
                    }, [e._v("底部")]) : e._e(), "loc_book" === e.book.origin ? t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: e.changeRule
                        }
                    }, [e._v(" 修改规则 ")]) : e._e(), t("span", {
                        class: {
                            loading: e.refreshLoading,
                            "refresh-btn": !0
                        },
                        on: {
                            click: e.refreshChapter
                        }
                    }, [e.refreshLoading ? t("i", {
                        staticClass: "el-icon-loading"
                    }) : e._e(), e._v(" " + e._s(e.refreshLoading ? "刷新中..." : "刷新") + " ")]), t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: function(t) {
                                e.isSearching = !e.isSearching
                            }
                        }
                    }, [e._v(" " + e._s(e.isSearching ? "取消" : "搜索") + " ")])]), t("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: e.isSearching,
                            expression: "isSearching"
                        }],
                        class: {
                            "title-btn": !0
                        }
                    }, [t("el-input", {
                        staticClass: "booksource-filter-input",
                        attrs: {
                            size: "mini",
                            placeholder: "搜索目录"
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
                        ref: "cataData",
                        staticClass: "data-wrapper",
                        class: {
                            night: e.$store.getters.isNight,
                            day: !e.$store.getters.isNight,
                            expand: e.expand
                        }
                    }, [t("div", {
                        staticClass: "cata"
                    }, ["yes" === e.$store.getters.shelfConfig.virtualOptimize ? t("PopCatalogVirtualList", {
                        ref: "virtList",
                        attrs: {
                            catalog: e.cataList,
                            cachedCataMap: e.cachedCataMap,
                            asc: e.asc,
                            visible: e.visible
                        },
                        on: {
                            gotoChapter: e.gotoChapter
                        }
                    }) : e._l(e.cataList, (function(n) {
                        return t("div", {
                            key: n.index,
                            ref: "cata",
                            refInFor: !0,
                            staticClass: "log",
                            class: {
                                selected: e.isSelected(n.index)
                            },
                            on: {
                                click: function(t) {
                                    return e.gotoChapter(n)
                                }
                            }
                        }, [t("div", {
                            class: {
                                "log-text": !0,
                                cached: e.cachedCataMap[n.index]
                            }
                        }, [e._v(" " + e._s(n.title) + " ")])])
                    }))], 2)])])
                };
