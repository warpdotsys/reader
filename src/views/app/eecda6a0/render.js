var render = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: e.title,
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
                    staticClass: "custom-dialog-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [e._v(e._s(this.title) + " "), t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.resolveAndImportBooks
                    }
                }, [e._v("一键导入")]), t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.resolveBooks
                    }
                }, [e._v("解析书籍")])])]), t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    ref: "fileTable",
                    attrs: {
                        data: e.filteredFileList,
                        height: e.dialogContentHeight
                    },
                    on: {
                        "selection-change": function(t) {
                            e.fileSelection = t
                        }
                    }
                }, [t("el-table-column", {
                    attrs: {
                        type: "selection",
                        width: "25",
                        fixed: e.$store.state.miniInterface,
                        selectable: function(e) {
                            return !e.toParent
                        }
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "name",
                        "min-width": "150px",
                        label: e.resolveMode ? "书名" : "文件名",
                        sortable: "",
                        "sort-method": e.sortMethod,
                        filters: [{
                            text: "已导入",
                            value: "imported"
                        }, {
                            text: "未导入",
                            value: "unImport"
                        }, {
                            text: "可导入",
                            value: "canImport"
                        }, {
                            text: "目录",
                            value: "directory"
                        }],
                        "filter-method": e.filterHandler,
                        fixed: e.$store.state.miniInterface
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [o.row.isImported ? t("span", {
                                staticClass: "imported-book-name"
                            }, [e._v(e._s(o.row.name))]) : o.row.isDirectory ? e._e() : t("span", [e._v(e._s(o.row.name))]), o.row.isDirectory ? t("el-link", {
                                attrs: {
                                    type: "primary"
                                },
                                on: {
                                    click: function(t) {
                                        return e.showFileList(o.row.path)
                                    }
                                }
                            }, [e._v(e._s(o.row.name))]) : e._e()]
                        }
                    }], null, !1, 3985659619)
                }), e.resolveMode ? t("el-table-column", {
                    attrs: {
                        property: "book.author",
                        label: "作者名",
                        "min-width": "100px",
                        sortable: "",
                        filters: e.authorList,
                        "filter-method": e.filterAuthor
                    }
                }) : e._e(), t("el-table-column", {
                    attrs: {
                        property: "size",
                        label: "大小",
                        sortable: "",
                        formatter: e.formatTableField,
                        "min-width": "100px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "lastModified",
                        label: "修改时间",
                        sortable: "",
                        formatter: e.formatTableField,
                        width: "120px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        align: "right",
                        width: "140px"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [o.row.isDirectory ? e._e() : t("el-dropdown", {
                                attrs: {
                                    trigger: e.$store.state.touchable ? "click" : "hover"
                                },
                                on: {
                                    command: function(t) {
                                        return e.operateFile(o.row, t)
                                    }
                                }
                            }, [t("el-button", {
                                staticClass: "text-button",
                                attrs: {
                                    type: "text",
                                    size: "medium"
                                }
                            }, [e._v(" 操作"), t("i", {
                                staticClass: "el-icon-arrow-down el-icon--right"
                            })]), t("el-dropdown-menu", {
                                attrs: {
                                    slot: "dropdown"
                                },
                                slot: "dropdown"
                            }, [o.row.isDirectory ? e._e() : t("el-dropdown-item", {
                                attrs: {
                                    command: "downloadFile"
                                }
                            }, [e._v("下载")]), !o.row.isDirectory && o.row.name.endsWith(".zip") ? t("el-dropdown-item", {
                                attrs: {
                                    command: "restoreFromFile"
                                }
                            }, [e._v("还原")]) : e._e(), !o.row.isDirectory && o.row.name.endsWith(".json") ? t("el-dropdown-item", {
                                attrs: {
                                    command: "editFile"
                                }
                            }, [e._v("编辑")]) : e._e(), o.row.canImport ? t("el-dropdown-item", {
                                attrs: {
                                    command: "importFromFile"
                                }
                            }, [e._v("加入书架")]) : e._e()], 1)], 1), o.row.toParent ? e._e() : t("el-button", {
                                staticStyle: {
                                    color: "#f56c6c"
                                },
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.deleteFile(o.row)
                                    }
                                }
                            }, [e._v("删除")])]
                        }
                    }], null, !1, 1541015572)
                }, [t("template", {
                    slot: "header"
                }, [t("el-input", {
                    attrs: {
                        size: "mini",
                        placeholder: "搜索本页"
                    },
                    model: {
                        value: e.keyword,
                        callback: function(t) {
                            e.keyword = t
                        },
                        expression: "keyword"
                    }
                })], 1)], 2)], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("el-dropdown", {
                    staticClass: "float-left",
                    attrs: {
                        trigger: e.$store.state.touchable ? "click" : "hover"
                    },
                    on: {
                        command: e.operate
                    }
                }, [t("el-button", {
                    attrs: {
                        type: "primary",
                        size: "medium"
                    }
                }, [e._v(" 操作"), e.fileSelection.length ? t("span", [e._v(" (" + e._s(e.fileSelection.length) + ")")]) : e._e(), t("i", {
                    staticClass: "el-icon-arrow-down el-icon--right"
                })]), t("el-dropdown-menu", {
                    attrs: {
                        slot: "dropdown"
                    },
                    slot: "dropdown"
                }, [t("el-dropdown-item", {
                    attrs: {
                        command: "deleteFileList"
                    }
                }, [e._v("批量删除")]), t("el-dropdown-item", {
                    attrs: {
                        command: "importFromFile"
                    }
                }, [e._v("批量加入书架")]), t("el-dropdown-item", {
                    attrs: {
                        command: "mkdir"
                    }
                }, [e._v("新建目录")]), t("el-dropdown-item", {
                    attrs: {
                        command: "upload"
                    }
                }, [e._v("上传文件")])], 1)], 1), t("input", {
                    ref: "bookRef",
                    staticStyle: {
                        display: "none"
                    },
                    attrs: {
                        type: "file",
                        multiple: "multiple"
                    },
                    on: {
                        change: e.onFileChange
                    }
                })], 1)]) : e._e()
            };
