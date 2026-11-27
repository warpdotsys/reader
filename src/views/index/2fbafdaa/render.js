var render = function() {
                    var t = this,
                        e = t._self._c;
                    return e("div", {
                        staticClass: "wrapper"
                    }, t._l(t.bookList, (function(o) {
                        return e("div", {
                            key: o.bookUrl,
                            staticClass: "book",
                            style: t.showNavigation ? {
                                minWidth: "360px !important"
                            } : {}
                        }, [e("div", {
                            staticClass: "cover-img",
                            on: {
                                click: function(e) {
                                    return e.stopPropagation(), t.$emit("showBookInfoDialog", o)
                                }
                            }
                        }, [e("el-image", {
                            ref: "bookCoverList",
                            refInFor: !0,
                            staticClass: "cover",
                            attrs: {
                                src: t.getCover(t.getBookCoverUrl(o), !0),
                                fit: "cover",
                                lazy: ""
                            }
                        })], 1), e("div", {
                            staticClass: "info",
                            on: {
                                click: function(e) {
                                    return e.stopPropagation(), t.$emit("toDetail", o)
                                }
                            }
                        }, [e("div", {
                            staticClass: "book-operation"
                        }, [!t.isSearchResult && t.showBookEditButton ? e("i", {
                            staticClass: "el-icon-close",
                            on: {
                                click: function(e) {
                                    return e.stopPropagation(), t.$emit("deleteBook", o)
                                }
                            }
                        }) : t._e(), !t.isSearchResult && t.showBookEditButton ? e("i", {
                            staticClass: "el-icon-edit",
                            on: {
                                click: function(e) {
                                    return e.stopPropagation(), t.$emit("editBook", o)
                                }
                            }
                        }) : t._e(), t.isSearchResult ? e("i", {
                            staticClass: "el-icon-edit",
                            on: {
                                click: function(e) {
                                    return e.stopPropagation(), t.$emit("editBook", o, !0)
                                }
                            }
                        }) : t._e(), !t.isSearchResult && !t.showBookEditButton && o.totalChapterNum - 1 - o.durChapterIndex > 0 ? e("el-badge", {
                            staticClass: "unread-num-badge",
                            attrs: {
                                max: 99,
                                value: o.totalChapterNum - 1 - o.durChapterIndex
                            }
                        }) : t._e()], 1), e("div", {
                            staticClass: "name",
                            class: t.showBookEditButton ? "edit" : "",
                            attrs: {
                                slot: "reference"
                            },
                            slot: "reference"
                        }, [t._v(" " + t._s(o.name) + " ")]), e("div", {
                            staticClass: "sub"
                        }, [e("div", {
                            staticClass: "author"
                        }, [t._v(" " + t._s(o.author || "") + " ")]), o.totalChapterNum ? e("div", {
                            staticClass: "dot"
                        }, [t._v("•")]) : t._e(), o.totalChapterNum ? e("div", {
                            staticClass: "size"
                        }, [t._v(" 共" + t._s(o.totalChapterNum) + "章 ")]) : t._e()]), !t.isSearchResult && o.durChapterTitle ? e("div", {
                            staticClass: "dur-chapter"
                        }, [t._v(" 已读：" + t._s(o.durChapterTitle) + " ")]) : t._e(), t.isSearchResult ? e("BookKind", {
                            attrs: {
                                book: o
                            }
                        }) : t._e(), o.latestChapterTitle ? e("div", {
                            staticClass: "last-chapter"
                        }, [t._v(" " + t._s(o.lastCheckTime ? t.dateFormat(o.lastCheckTime) : "最新") + "：" + t._s(o.latestChapterTitle) + " ")]) : t._e(), t.isSearchResult ? e("div", [e("el-tag", {
                            staticClass: "setting-connect",
                            attrs: {
                                type: "success",
                                effect: t.$store.getters.isNight ? "dark" : "light"
                            },
                            on: {
                                click: function(e) {
                                    return e.stopPropagation(), t.$emit("addBookToShelf", o)
                                }
                            }
                        }, [t._v(" 加入书架 ")]), t.showSourceCount ? e("span", {
                            staticClass: "source-count",
                            on: {
                                click: function(e) {
                                    return e.stopPropagation(), t.$emit("showSearchBookSourceList", o)
                                }
                            }
                        }, [t._v(t._s(o.sourceCount || 1) + " 个书源")]) : t._e()], 1) : t._e()], 1)])
                    })), 0)
                };
