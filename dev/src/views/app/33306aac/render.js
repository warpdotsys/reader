var render = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "用户管理",
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
                }, [e._v("用户管理 ")]), t("span", {
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
                            e.manageUserSelection = t
                        },
                        "sort-change": e.sortChange
                    }
                }, [t("el-table-column", {
                    attrs: {
                        type: "selection",
                        width: "25",
                        selectable: e.isUserSelectable,
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "username",
                        label: "用户名",
                        "min-width": "100",
                        sortable: "custom",
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "lastLoginAt",
                        label: "上次登录",
                        sortable: "custom",
                        formatter: e.formatTableField,
                        "min-width": "120"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "createdAt",
                        label: "注册时间",
                        sortable: "custom",
                        formatter: e.formatTableField,
                        "min-width": "120"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "enableWebdav",
                        label: "WebDAV",
                        "min-width": "80",
                        filters: [{
                            text: "开启",
                            value: !0
                        }, {
                            text: "关闭",
                            value: !1
                        }],
                        "filter-method": e.filterHandler
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return ["default" !== o.row.userNS ? t("el-switch", {
                                attrs: {
                                    "active-color": "#13ce66",
                                    "inactive-color": "#ff4949",
                                    "active-value": !0,
                                    "inactive-value": !1
                                },
                                on: {
                                    change: function(t) {
                                        return e.toggleUserWebdav(o.row, t)
                                    }
                                },
                                model: {
                                    value: o.row.enableWebdav,
                                    callback: function(t) {
                                        e.$set(o.row, "enableWebdav", t)
                                    },
                                    expression: "scope.row.enableWebdav"
                                }
                            }) : e._e()]
                        }
                    }], null, !1, 2358517604)
                }), t("el-table-column", {
                    attrs: {
                        property: "enableLocalStore",
                        label: "书仓",
                        "min-width": "80",
                        filters: [{
                            text: "开启",
                            value: !0
                        }, {
                            text: "关闭",
                            value: !1
                        }],
                        "filter-method": e.filterHandler
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return ["default" !== o.row.userNS ? t("el-switch", {
                                attrs: {
                                    "active-color": "#13ce66",
                                    "inactive-color": "#ff4949",
                                    "active-value": !0,
                                    "inactive-value": !1
                                },
                                on: {
                                    change: function(t) {
                                        return e.toggleUserLocalStore(o.row, t)
                                    }
                                },
                                model: {
                                    value: o.row.enableLocalStore,
                                    callback: function(t) {
                                        e.$set(o.row, "enableLocalStore", t)
                                    },
                                    expression: "scope.row.enableLocalStore"
                                }
                            }) : e._e()]
                        }
                    }], null, !1, 4075233220)
                }), t("el-table-column", {
                    attrs: {
                        label: "操作",
                        width: "100px"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return ["default" != o.row.userNS ? t("el-button", {
                                staticClass: "text-button",
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.editUser(o.row)
                                    }
                                }
                            }, [e._v("修改")]) : e._e(), "default" != o.row.userNS ? t("el-button", {
                                staticClass: "text-button",
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.resetPassword(o.row)
                                    }
                                }
                            }, [e._v("重置密码")]) : e._e(), "default" != o.row.userNS ? t("el-button", {
                                staticClass: "text-button",
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.setAsDefaultBookSources(o.row)
                                    }
                                }
                            }, [e._v("设为默认书源")]) : e._e()]
                        }
                    }], null, !1, 1517798773)
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
                        click: e.deleteUserList
                    }
                }, [e._v("批量删除"), e.manageUserSelection.length ? t("span", [e._v(" (" + e._s(e.manageUserSelection.length) + ")")]) : e._e()]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.deleteUserBookSource
                    }
                }, [e._v("使用默认书源")]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: function(t) {
                            return e.clearInactiveUser()
                        }
                    }
                }, [e._v("清理")]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: function(t) {
                            return e.showAddUserDialog()
                        }
                    }
                }, [e._v("新增")])], 1), t("div", {
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
