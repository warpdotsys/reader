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
                }, [e._v(e._s(e.book ? e.book.name : "") + " 书签管理 ")]), t("span", {
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
                })], 1)]), t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    attrs: {
                        data: e.showList,
                        height: e.dialogContentHeight - 42
                    },
                    on: {
                        "selection-change": function(t) {
                            e.localSelection = t
                        },
                        "sort-change": e.sortChange
                    }
                }, [t("el-table-column", {
                    attrs: {
                        type: "selection",
                        width: "25",
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        "min-width": "150px",
                        label: "书籍",
                        sortable: "custom",
                        fixed: e.$store.state.miniInterface
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(t) {
                            return [e._v(" " + e._s(t.row.bookName) + " - " + e._s(t.row.bookAuthor) + " ")]
                        }
                    }], null, !1, 3213026799)
                }), t("el-table-column", {
                    attrs: {
                        property: "chapterName",
                        label: "章节",
                        "min-width": "150px",
                        sortable: "custom"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "bookText",
                        label: "内容",
                        "min-width": "150px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "content",
                        label: "备注",
                        "min-width": "150px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        label: "操作",
                        width: "100px"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [e.book ? t("el-button", {
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.showBookmark(o.row)
                                    }
                                }
                            }, [e._v("跳转")]) : e._e(), t("el-button", {
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.editBookmark(o.row)
                                    }
                                }
                            }, [e._v("编辑")])]
                        }
                    }], null, !1, 2296415621)
                })], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("div", [t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.deleteBookmarks
                    }
                }, [e._v("批量删除 "), e.localSelection.length ? t("span", [e._v(" (" + e._s(e.localSelection.length) + ")")]) : e._e()]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.uploadFile
                    }
                }, [e._v("导入")]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.addBookmark
                    }
                }, [e._v("添加")]), t("input", {
                    ref: "fileRef",
                    staticStyle: {
                        display: "none"
                    },
                    attrs: {
                        type: "file"
                    },
                    on: {
                        change: function(t) {
                            return e.onFileChange(t)
                        }
                    }
                })], 1), t("div", {
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
