var render = function() {
                var e = this,
                    t = e._self._c;
                return t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "书籍信息",
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
                }, [e._v("书籍信息 "), e.isInShelf && "index" === e.$route.name ? t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.editBook
                    }
                }, [e._v("编辑")]) : e._e(), e.isInShelf ? t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.showBookConfig
                    }
                }, [e._v("设置")]) : e._e(), e.isInShelf && "index" === e.$route.name ? t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.deleteBook
                    }
                }, [e._v("移出书架")]) : e._e(), e.isInShelf ? t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.triggerBookCoverRefClick
                    }
                }, [e._v("修改封面")]) : e.isInShelf ? e._e() : t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: function(t) {
                            return e.saveBook(e.showBookInfo, !0)
                        }
                    }
                }, [e._v("加入书架")])])]), e.show ? t("div", {
                    staticClass: "book-info-container"
                }, [t("div", {
                    staticClass: "book-cover"
                }, [t("div", {
                    staticClass: "book-cover-bg",
                    style: e.bookCoverBgStyle
                }), t("div", {
                    staticClass: "book-cover-bg-image"
                }, [t("img", {
                    directives: [{
                        name: "lazy",
                        rawName: "v-lazy",
                        value: e.getCover(e.getBookCoverUrl(e.showBookInfo)),
                        expression: "getCover(getBookCoverUrl(showBookInfo))"
                    }],
                    key: e.showBookInfo.name,
                    attrs: {
                        alt: ""
                    },
                    on: {
                        click: e.triggerBookCoverRefClick
                    }
                }), t("input", {
                    ref: "bookCoverRef",
                    staticStyle: {
                        display: "none"
                    },
                    attrs: {
                        type: "file",
                        accept: "image/jpg, image/png, image/jpeg"
                    },
                    on: {
                        change: e.onCoverFileChange
                    }
                })])]), t("div", {
                    staticClass: "book-name"
                }, [e._v(e._s(e.showBookInfo.name))]), t("BookKind", {
                    staticClass: "text-center",
                    attrs: {
                        book: e.showBookInfo
                    }
                }), t("div", {
                    staticClass: "book-props"
                }, [t("div", {
                    staticClass: "book-prop book-author"
                }, [e._v(" 作者： " + e._s(e.showBookInfo.author || "未知") + " ")]), t("div", {
                    staticClass: "book-prop book-origin"
                }, [e._v(" 来源： " + e._s(e.displayOriginName(e.showBookInfo.origin)) + " "), "loc_book" === e.showBookInfo.origin ? t("el-button", {
                    staticClass: "book-prop-btn",
                    attrs: {
                        type: "text"
                    },
                    on: {
                        click: function(t) {
                            return e.refreshLocalBook(e.showBookInfo)
                        }
                    }
                }, [e._v("更新")]) : e._e()], 1), t("div", {
                    staticClass: "book-prop book-latest"
                }, [t("span", {
                    staticClass: "latest-title"
                }, [e._v("最新： " + e._s(e.showBookInfo.latestChapterTitle) + " ")]), e.isInShelf ? t("span", {
                    staticClass: "book-prop-btn"
                }, [e._v(" 追更 "), t("el-switch", {
                    attrs: {
                        "active-color": "#13ce66",
                        "inactive-color": "#ff4949",
                        "active-value": !0,
                        "inactive-value": !1
                    },
                    on: {
                        change: function(t) {
                            return e.toggleBookCanUpdate(e.showBookInfo)
                        }
                    },
                    model: {
                        value: e.showBookInfo.canUpdate,
                        callback: function(t) {
                            e.$set(e.showBookInfo, "canUpdate", t)
                        },
                        expression: "showBookInfo.canUpdate"
                    }
                })], 1) : e._e()]), e.showBookInfo.lastCheckError ? t("div", {
                    staticClass: "book-prop last-check-error"
                }, [e._v(" 错误： " + e._s(e.showBookInfo.lastCheckError) + " ")]) : e._e(), e.isInShelf ? t("div", {
                    staticClass: "book-prop book-group"
                }, [e._v(" 分组： " + e._s(e.displayGroupName(e.showBookInfo.group)) + " "), t("el-button", {
                    staticClass: "book-prop-btn",
                    attrs: {
                        type: "text"
                    },
                    on: {
                        click: function(t) {
                            return e.showSetBookGroup()
                        }
                    }
                }, [e._v("设置分组")])], 1) : e._e()]), t("div", {
                    staticClass: "book-intro",
                    domProps: {
                        innerHTML: e._s(e.renderBookIntro(e.showBookInfo))
                    }
                })], 1) : e._e()])
            };
