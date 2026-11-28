var render = function() {
                    var t = this,
                        e = t._self._c;
                    return t.$store.getters.isNormalPage ? e("el-dialog", {
                        class: t.isWebApp && !t.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                        attrs: {
                            title: "书架布局",
                            visible: t.show,
                            width: t.dialogSmallWidth,
                            top: t.dialogTop,
                            fullscreen: t.$store.state.miniInterface,
                            "before-close": t.cancel
                        },
                        on: {
                            "update:visible": function(e) {
                                t.show = e
                            }
                        }
                    }, [e("el-form", {
                        attrs: {
                            model: t.shelfConfig
                        }
                    }, [e("el-form-item", {
                        attrs: {
                            label: "显示分组"
                        }
                    }, [e("el-select", {
                        staticClass: "setting-select",
                        attrs: {
                            size: "mini",
                            filterable: "",
                            placeholder: "请选择默认显示分组"
                        },
                        model: {
                            value: t.shelfConfig.showBookGroup,
                            callback: function(e) {
                                t.$set(t.shelfConfig, "showBookGroup", e)
                            },
                            expression: "shelfConfig.showBookGroup"
                        }
                    }, t._l(t.bookGroupDisplayList, (function(t, o) {
                        return e("el-option", {
                            key: "book-group-" + o,
                            attrs: {
                                label: t.groupName,
                                value: t.groupId
                            }
                        })
                    })), 1)], 1), e("el-form-item", {
                        attrs: {
                            label: "显示视图"
                        }
                    }, [e("el-select", {
                        staticClass: "setting-select",
                        attrs: {
                            size: "mini",
                            filterable: "",
                            placeholder: "请选择视图"
                        },
                        model: {
                            value: t.shelfConfig.viewCate,
                            callback: function(e) {
                                t.$set(t.shelfConfig, "viewCate", e)
                            },
                            expression: "shelfConfig.viewCate"
                        }
                    }, t._l(t.viewCateList, (function(t, o) {
                        return e("el-option", {
                            key: "view-cate-" + o,
                            attrs: {
                                label: t.name,
                                value: t.value
                            }
                        })
                    })), 1)], 1), e("el-form-item", {
                        attrs: {
                            label: "书籍排序"
                        }
                    }, [e("el-select", {
                        staticClass: "setting-select",
                        attrs: {
                            size: "mini",
                            filterable: "",
                            placeholder: "请选择排序"
                        },
                        model: {
                            value: t.shelfConfig.bookOrder,
                            callback: function(e) {
                                t.$set(t.shelfConfig, "bookOrder", e)
                            },
                            expression: "shelfConfig.bookOrder"
                        }
                    }, t._l(t.bookOrderList, (function(t, o) {
                        return e("el-option", {
                            key: "book-order-" + o,
                            attrs: {
                                label: t.name,
                                value: t.value
                            }
                        })
                    })), 1)], 1), e("el-form-item", {
                        attrs: {
                            label: "图片代理"
                        }
                    }, [e("el-select", {
                        staticClass: "setting-select",
                        attrs: {
                            size: "mini",
                            filterable: "",
                            placeholder: "请选择图片代理"
                        },
                        model: {
                            value: t.shelfConfig.imageProxy,
                            callback: function(e) {
                                t.$set(t.shelfConfig, "imageProxy", e)
                            },
                            expression: "shelfConfig.imageProxy"
                        }
                    }, t._l(t.imageProxyList, (function(t, o) {
                        return e("el-option", {
                            key: "book-image-" + o,
                            attrs: {
                                label: t.name,
                                value: t.value
                            }
                        })
                    })), 1)], 1), e("el-form-item", {
                        attrs: {
                            label: "列表优化"
                        }
                    }, [e("el-select", {
                        staticClass: "setting-select",
                        attrs: {
                            size: "mini",
                            filterable: "",
                            placeholder: "请选择是否启用列表优化"
                        },
                        model: {
                            value: t.shelfConfig.virtualOptimize,
                            callback: function(e) {
                                t.$set(t.shelfConfig, "virtualOptimize", e)
                            },
                            expression: "shelfConfig.virtualOptimize"
                        }
                    }, t._l(t.virtualOptimizeList, (function(t, o) {
                        return e("el-option", {
                            key: "virtual-options-" + o,
                            attrs: {
                                label: t.name,
                                value: t.value
                            }
                        })
                    })), 1)], 1)], 1), e("div", {
                        staticClass: "dialog-footer",
                        attrs: {
                            slot: "footer"
                        },
                        slot: "footer"
                    }, [e("el-button", {
                        attrs: {
                            size: "medium",
                            type: "primary"
                        },
                        on: {
                            click: t.save
                        }
                    }, [t._v("保 存")]), e("el-button", {
                        attrs: {
                            size: "medium",
                            type: "primary"
                        },
                        on: {
                            click: t.cancel
                        }
                    }, [t._v("关 闭")])], 1)], 1) : t._e()
                };
