var render = function() {
                    var t = this,
                        e = t._self._c;
                    return e("div", {
                        staticClass: "popup-wrapper",
                        style: t.popupTheme
                    }, [e("div", {
                        staticClass: "title-zone"
                    }, [e("div", {
                        staticClass: "title"
                    }, [t._v("书海")]), e("div", {
                        class: {
                            "title-btn": !0
                        }
                    }, [e("span", {
                        staticClass: "source-count"
                    }, [t._v(" 共" + t._s(t.bookSourceShowLength) + "个可用书源 ")]), t.$store.state.miniInterface ? e("i", {
                        staticClass: "el-icon-close close-btn",
                        on: {
                            click: function(e) {
                                return e.stopPropagation(), t.close.apply(null, arguments)
                            }
                        }
                    }) : t._e()])]), e("div", {
                        staticClass: "source-group-wrapper"
                    }, [e("el-tabs", {
                        staticClass: "booksource-group-tabs",
                        attrs: {
                            stretch: ""
                        },
                        model: {
                            value: t.sourceGroup,
                            callback: function(e) {
                                t.sourceGroup = e
                            },
                            expression: "sourceGroup"
                        }
                    }, t._l(t.bookSourceGroup, (function(t) {
                        return e("el-tab-pane", {
                            key: "sourceGroupTab-" + t,
                            attrs: {
                                label: t,
                                name: t
                            }
                        })
                    })), 1)], 1), e("div", {
                        ref: "sourceList",
                        staticClass: "data-wrapper",
                        class: {
                            night: t.$store.getters.isNight,
                            day: !t.$store.getters.isNight
                        },
                        on: {
                            scroll: t.scrollHandler
                        }
                    }, [e("div", {
                        staticClass: "cata"
                    }, [t.visible ? e("el-collapse", {
                        ref: "sourceList",
                        staticClass: "source-collapse",
                        on: {
                            change: t.onCollapseChange
                        },
                        model: {
                            value: t.showCollapse,
                            callback: function(e) {
                                t.showCollapse = e
                            },
                            expression: "showCollapse"
                        }
                    }, t._l(t.bookSourceListNew, (function(o, s) {
                        return e("el-collapse-item", {
                            directives: [{
                                name: "show",
                                rawName: "v-show",
                                value: !t.sourceGroup || "全部" === t.sourceGroup || "未分组" === t.sourceGroup && "" === o.bookSourceGroup || (o.bookSourceGroup + ",").indexOf(t.sourceGroup + ",") >= 0,
                                expression: "\n            !sourceGroup ||\n              sourceGroup === '全部' ||\n              (sourceGroup === '未分组' && source.bookSourceGroup === '') ||\n              (source.bookSourceGroup + ',').indexOf(sourceGroup + ',') >= 0\n          "
                            }],
                            key: "source-" + s,
                            ref: "source",
                            refInFor: !0,
                            attrs: {
                                title: o.bookSourceName,
                                name: s
                            }
                        }, [t.showCollapse.includes(s) ? t._l(o.exploreGroup, (function(s, r) {
                            return e("div", {
                                key: "group-" + r,
                                staticClass: "explore-group"
                            }, t._l(s, (function(s, r) {
                                return e("el-tag", {
                                    key: "group-" + r,
                                    staticClass: "explore-btn",
                                    attrs: {
                                        type: "info",
                                        effect: t.$store.getters.isNight ? "dark" : "light"
                                    },
                                    on: {
                                        click: function(e) {
                                            return t.exploreBookSource(s.url, o.bookSourceUrl, 1, e)
                                        }
                                    }
                                }, [t._v(" " + t._s(s.name) + " ")])
                            })), 1)
                        })) : t._e()], 2)
                    })), 1) : t._e()], 1)])])
                };
