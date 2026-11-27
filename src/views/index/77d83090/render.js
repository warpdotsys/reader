var render = function() {
                    var t = this,
                        e = t._self._c;
                    return e("div", {
                        ref: "wrapper",
                        staticClass: "wrapper",
                        style: {
                            "grid-template-columns": "repeat(auto-fill, " + t.bookItemWidth + ")"
                        }
                    }, t._l(t.bookList, (function(o) {
                        return e("div", {
                            key: o.bookUrl,
                            staticClass: "book",
                            style: {
                                "--book-item-width": t.bookItemWidth
                            },
                            on: {
                                click: function(e) {
                                    return t.$emit("toDetail", o)
                                }
                            }
                        }, [e("div", {
                            staticClass: "cover-img",
                            on: {
                                click: function(e) {
                                    return e.ctrlKey ? e.shiftKey || e.altKey || e.metaKey ? null : (e.stopPropagation(), t.$emit("showBookInfoDialog", o)) : null
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
                            staticClass: "name"
                        }, [t._v(" " + t._s(o.name) + " ")]), e("div", {
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
                        }) : t._e()], 1)])
                    })), 0)
                };
