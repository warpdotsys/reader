var render = function() {
                var e = this,
                    t = e._self._c;
                return t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
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
                }, [e._v("RSS订阅(" + e._s(e.rssSourceList.length) + ") "), t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: function(t) {
                            e.showRssSourceEditButton = !e.showRssSourceEditButton
                        }
                    }
                }, [e._v(e._s(e.showRssSourceEditButton ? "取消" : "编辑"))]), t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.uploadRssSource
                    }
                }, [e._v("导入")]), t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: function(t) {
                            return e.editRssSource(!1)
                        }
                    }
                }, [e._v("新增")])]), t("input", {
                    ref: "rssInputRef",
                    staticStyle: {
                        display: "none"
                    },
                    attrs: {
                        type: "file"
                    },
                    on: {
                        change: function(t) {
                            return e.onSourceFileChange(t, !0)
                        }
                    }
                })]), t("div", {
                    staticClass: "rss-source-list-container"
                }, e._l(e.rssSourceList, (function(o, n) {
                    return t("div", {
                        key: "rss-" + n,
                        staticClass: "rss-source",
                        on: {
                            click: function(t) {
                                return e.showRssArticleListDialog(o)
                            }
                        }
                    }, [e.showRssSourceEditButton ? t("i", {
                        staticClass: "el-icon-close",
                        on: {
                            click: function(t) {
                                return t.stopPropagation(), e.deleteRssSource(o)
                            }
                        }
                    }) : e._e(), e.showRssSourceEditButton ? t("i", {
                        staticClass: "el-icon-edit",
                        on: {
                            click: function(t) {
                                return t.stopPropagation(), e.editRssSource(o)
                            }
                        }
                    }) : e._e(), t("el-image", {
                        staticClass: "rss-icon",
                        attrs: {
                            src: e.getImage(o.sourceIcon, !0),
                            fit: "cover",
                            lazy: ""
                        }
                    }), t("div", {
                        staticClass: "rss-title"
                    }, [e._v(e._s(o.sourceName))])], 1)
                })), 0)])
            };
