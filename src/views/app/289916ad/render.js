var render = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: e.isShowBookGroupSettingDialog ? "设置分组" : "分组管理",
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
                        opened: e.opened
                    }
                }, [t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    key: e.isShowBookGroupSettingDialog,
                    ref: "bookGroupTableRef",
                    attrs: {
                        data: e.showBookGroupList,
                        height: e.dialogContentHeight
                    },
                    on: {
                        "selection-change": function(t) {
                            e.bookGroupSelection = t
                        }
                    }
                }, [e.isShowBookGroupSettingDialog ? t("el-table-column", {
                    attrs: {
                        type: "selection",
                        width: "25"
                    }
                }) : e._e(), t("el-table-column", {
                    attrs: {
                        property: "groupName",
                        label: "分组名",
                        "min-width": "100"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("div", {
                                staticClass: "drag-icon"
                            }, [t("i", {
                                staticClass: "el-icon-rank"
                            })]), t("span", [e._v(" " + e._s(e.displayBookGroupName(o.row)))])]
                        }
                    }], null, !1, 4111896031)
                }), e.isShowBookGroupSettingDialog ? e._e() : t("el-table-column", {
                    attrs: {
                        property: "show",
                        label: "显示",
                        "min-width": "80"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("el-switch", {
                                attrs: {
                                    "active-color": "#13ce66",
                                    "inactive-color": "#ff4949",
                                    "active-value": !0,
                                    "inactive-value": !1
                                },
                                on: {
                                    change: function(t) {
                                        return e.toggleBookGroupShow(o.row, t)
                                    }
                                },
                                model: {
                                    value: o.row.show,
                                    callback: function(t) {
                                        e.$set(o.row, "show", t)
                                    },
                                    expression: "scope.row.show"
                                }
                            })]
                        }
                    }], null, !1, 3235955515)
                }), t("el-table-column", {
                    attrs: {
                        label: "操作",
                        width: "100px"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("el-button", {
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.saveBookGroup(o.row)
                                    }
                                }
                            }, [e._v("编辑")]), !e.isShowBookGroupSettingDialog && o.row.groupId > 0 && !e.getShowShelfBooks(o.row.groupId).length ? t("el-button", {
                                staticStyle: {
                                    color: "#f56c6c"
                                },
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.deleteBookGroup(o.row)
                                    }
                                }
                            }, [e._v("删除")]) : e._e()]
                        }
                    }], null, !1, 1414828911)
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
                        size: "medium"
                    },
                    on: {
                        click: function(t) {
                            return e.saveBookGroup()
                        }
                    }
                }, [e._v("添加分组")]), e.isShowSaveOrderButton ? t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: function(t) {
                            return e.saveOrder()
                        }
                    }
                }, [e._v("保存排序")]) : e._e(), e.isShowBookGroupSettingDialog ? t("el-button", {
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.setBookGroup
                    }
                }, [e._v("确认")]) : e._e(), t("el-button", {
                    attrs: {
                        size: "medium"
                    },
                    on: {
                        click: e.cancel
                    }
                }, [e._v("取消")])], 1)]) : e._e()
            };
