var render = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "书架管理",
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
                }, [t("div", {
                    staticClass: "custom-dialog-title flex-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [e._v("书架管理 ")]), t("span", {
                    staticClass: "title-center"
                }, [t("el-input", {
                    staticClass: "search-input",
                    attrs: {
                        size: "mini",
                        placeholder: "输入关键字搜索"
                    },
                    model: {
                        value: e.search,
                        callback: function(t) {
                            e.search = t
                        },
                        expression: "search"
                    }
                })], 1), t("span", {
                    directives: [{
                        name: "show",
                        rawName: "v-show",
                        value: !e.$store.state.miniInterface,
                        expression: "!$store.state.miniInterface"
                    }],
                    staticClass: "action-zone"
                }, [t("span", {
                    staticClass: "float-right small-tip"
                }, [e._v("❗️只能缓存文本内容")])])]), t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    attrs: {
                        data: e.showList,
                        height: e.dialogContentHeight - 42
                    },
                    on: {
                        "selection-change": function(t) {
                            e.manageBookSelection = t
                        },
                        "sort-change": e.sortChange
                    }
                }, [t("el-table-column", {
                    attrs: {
                        type: "selection",
                        width: "25",
                        selectable: e.isBookSelectable,
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "name",
                        label: "书名名",
                        "min-width": "100",
                        sortable: "custom",
                        fixed: e.$store.state.miniInterface
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("el-button", {
                                staticClass: "text-button",
                                attrs: {
                                    size: "medium",
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.showBookInfo(o.row)
                                    }
                                }
                            }, [e._v(e._s(o.row.name))])]
                        }
                    }], null, !1, 4264428119)
                }), t("el-table-column", {
                    attrs: {
                        property: "author",
                        label: "作者",
                        sortable: "custom",
                        "min-width": "100"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "group",
                        label: "分组",
                        "min-width": "120",
                        filters: e.bookGroupFilters,
                        "filter-method": e.filterHandler
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(t) {
                            return [e._v(" " + e._s(e.renderBookGroup(t.row)) + " ")]
                        }
                    }], null, !1, 1549071982)
                }), t("el-table-column", {
                    attrs: {
                        label: "章节",
                        "min-width": "120"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("span", [e._v("共 " + e._s(o.row.totalChapterNum) + " 章")]), t("br"), "loc_book" !== o.row.origin ? t("span", [e._v(" 服务器缓存： " + e._s(o.row.cachedChapterCount || 0) + " 章 "), t("br")]) : e._e(), t("span", [e._v("浏览器缓存： " + e._s(o.row.localCacheCount) + " 章")])]
                        }
                    }], null, !1, 4230467245)
                }), t("el-table-column", {
                    attrs: {
                        label: "操作",
                        width: "100px"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("el-button", {
                                staticClass: "text-button",
                                attrs: {
                                    size: "medium",
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.editBook(o.row)
                                    }
                                }
                            }, [e._v("编辑")]), t("el-button", {
                                staticClass: "text-button",
                                staticStyle: {
                                    "margin-left": "0"
                                },
                                attrs: {
                                    size: "medium",
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.setBookGroup(o.row)
                                    }
                                }
                            }, [e._v("分组")]), t("el-dropdown", {
                                attrs: {
                                    trigger: e.$store.state.touchable ? "click" : "hover"
                                },
                                on: {
                                    command: function(t) {
                                        return e.cacheBook(o.row, t)
                                    }
                                }
                            }, [t("el-button", {
                                staticClass: "text-button",
                                attrs: {
                                    type: "text",
                                    size: "medium"
                                }
                            }, [e.isCaching(o.row) ? t("span", [t("i", {
                                staticClass: "el-icon-loading"
                            }), e._v(" 缓存中 ")]) : t("span", [e._v(" 缓存"), t("i", {
                                staticClass: "el-icon-arrow-down el-icon--right"
                            })])]), t("el-dropdown-menu", {
                                attrs: {
                                    slot: "dropdown"
                                },
                                slot: "dropdown"
                            }, ["loc_book" !== o.row.origin ? t("el-dropdown-item", {
                                attrs: {
                                    command: "cacheBookOnServer"
                                }
                            }, [e._v("服务器缓存")]) : e._e(), "loc_book" !== o.row.origin ? t("el-dropdown-item", {
                                attrs: {
                                    command: "cacheBookSSE"
                                }
                            }, [e._v("缓存到服务器")]) : e._e(), t("el-dropdown-item", {
                                attrs: {
                                    command: "cacheBookLocal"
                                }
                            }, [e._v("缓存到浏览器")]), "loc_book" !== o.row.origin ? t("el-dropdown-item", {
                                attrs: {
                                    command: "deleteBookCache"
                                }
                            }, [e._v("删除服务器缓存")]) : e._e(), t("el-dropdown-item", {
                                attrs: {
                                    command: "deleteBookLocalCache"
                                }
                            }, [e._v("删除浏览器缓存")])], 1)], 1), t("el-dropdown", {
                                attrs: {
                                    trigger: e.$store.state.touchable ? "click" : "hover"
                                },
                                on: {
                                    command: function(t) {
                                        return e.exportBook(o.row, t)
                                    }
                                }
                            }, [t("el-button", {
                                staticClass: "text-button",
                                attrs: {
                                    type: "text",
                                    size: "medium"
                                }
                            }, [e._v(" 导出"), t("i", {
                                staticClass: "el-icon-arrow-down el-icon--right"
                            })]), t("el-dropdown-menu", {
                                attrs: {
                                    slot: "dropdown"
                                },
                                slot: "dropdown"
                            }, [t("el-dropdown-item", {
                                attrs: {
                                    command: "txt"
                                }
                            }, [e._v("导出为TXT")]), t("el-dropdown-item", {
                                attrs: {
                                    command: "epub"
                                }
                            }, [e._v("导出为Epub")])], 1)], 1)]
                        }
                    }], null, !1, 675343596)
                })], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("div", [t("el-dropdown", {
                    staticClass: "float-left",
                    attrs: {
                        trigger: e.$store.state.touchable ? "click" : "hover"
                    },
                    on: {
                        command: e.multiOperate
                    }
                }, [t("el-button", {
                    attrs: {
                        type: "primary",
                        size: "medium"
                    }
                }, [e._v(" 操作 "), e.manageBookSelection.length ? t("span", [e._v(" (" + e._s(e.manageBookSelection.length) + ")")]) : e._e(), t("i", {
                    staticClass: "el-icon-arrow-down el-icon--right"
                })]), t("el-dropdown-menu", {
                    attrs: {
                        slot: "dropdown"
                    },
                    slot: "dropdown"
                }, [t("el-dropdown-item", {
                    attrs: {
                        command: "deleteBookList"
                    }
                }, [e._v("批量删除")]), t("el-dropdown-item", {
                    attrs: {
                        command: "addBookGroupMulti"
                    }
                }, [e._v("添加分组")]), t("el-dropdown-item", {
                    attrs: {
                        command: "removeBookGroupMulti"
                    }
                }, [e._v("删除分组")])], 1)], 1), t("el-dropdown", {
                    staticClass: "float-left",
                    attrs: {
                        trigger: e.$store.state.touchable ? "click" : "hover"
                    },
                    on: {
                        command: e.cacheBookMulti
                    }
                }, [t("el-button", {
                    attrs: {
                        type: "primary",
                        size: "medium"
                    }
                }, [e._v(" 缓存 "), e.manageBookSelection.length ? t("span", [e._v(" (" + e._s(e.manageBookSelection.length) + ")")]) : e._e(), t("i", {
                    staticClass: "el-icon-arrow-down el-icon--right"
                })]), t("el-dropdown-menu", {
                    attrs: {
                        slot: "dropdown"
                    },
                    slot: "dropdown"
                }, [t("el-dropdown-item", {
                    attrs: {
                        command: "cacheBookOnServer"
                    }
                }, [e._v("服务器缓存")]), t("el-dropdown-item", {
                    attrs: {
                        command: "cacheBookSSE"
                    }
                }, [e._v("缓存到服务器")]), t("el-dropdown-item", {
                    attrs: {
                        command: "cacheBookLocal"
                    }
                }, [e._v("缓存到浏览器")]), t("el-dropdown-item", {
                    attrs: {
                        command: "deleteBookCache"
                    }
                }, [e._v("删除服务器缓存")]), t("el-dropdown-item", {
                    attrs: {
                        command: "deleteBookLocalCache"
                    }
                }, [e._v("删除浏览器缓存")])], 1)], 1), e.multiOperating ? t("el-button", {
                    attrs: {
                        size: "medium"
                    },
                    on: {
                        click: e.cancelMulti
                    }
                }, [e._v("取消批量操作")]) : e._e()], 1), t("div", {
                    staticClass: "source-pagination"
                }, [t("el-pagination", {
                    attrs: {
                        "current-page": e.pagination.page,
                        "page-sizes": [25, 50, 100, 200, 300, 400, e.filterList.length],
                        "page-size": e.pagination.size,
                        layout: "total, sizes, prev, pager, next",
                        total: e.filterList.length,
                        "pager-count": e.$store.state.miniInterface ? 5 : 7
                    },
                    on: {
                        "update:currentPage": function(t) {
                            return e.$set(e.pagination, "page", t)
                        },
                        "update:current-page": function(t) {
                            return e.$set(e.pagination, "page", t)
                        },
                        "update:pageSize": function(t) {
                            return e.$set(e.pagination, "size", t)
                        },
                        "update:page-size": function(t) {
                            return e.$set(e.pagination, "size", t)
                        }
                    }
                })], 1)])]) : e._e()
            };
