<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=index scopeId=2fbafdaa；无 sourcemap，模板为 render 推断还原 -->
<template>
<div class="wrapper"><template v-for="o in bookList"><div :key="o.bookUrl" class="book" :style='showNavigation ? {
                                minWidth: "360px !important"
                            } : {}'><div class="cover-img" @click='(e) => {
                                    return e.stopPropagation(), $emit("showBookInfoDialog", o)
                                }'><el-image ref='"bookCoverList"' class="cover" :src="getCover(getBookCoverUrl(o), !0)" fit="cover" lazy="" /></div><div class="info" @click='(e) => {
                                    return e.stopPropagation(), $emit("toDetail", o)
                                }'><div class="book-operation"><template v-if="!isSearchResult && showBookEditButton"><i class="el-icon-close" @click='(e) => {
                                    return e.stopPropagation(), $emit("deleteBook", o)
                                }' /></template><template v-if="!isSearchResult && showBookEditButton"><i class="el-icon-edit" @click='(e) => {
                                    return e.stopPropagation(), $emit("editBook", o)
                                }' /></template><template v-if="isSearchResult"><i class="el-icon-edit" @click='(e) => {
                                    return e.stopPropagation(), $emit("editBook", o, !0)
                                }' /></template><template v-if="!isSearchResult && !showBookEditButton && o.totalChapterNum - 1 - o.durChapterIndex > 0"><el-badge class="unread-num-badge" :max="99" :value="o.totalChapterNum - 1 - o.durChapterIndex" /></template></div><div class="name" :class='showBookEditButton ? "edit" : ""' slot="reference">{{ " " + _s(o.name) + " " }}</div><div class="sub"><div class="author">{{ " " + _s(o.author || "") + " " }}</div><template v-if="o.totalChapterNum"><div class="dot">•</div></template><template v-if="o.totalChapterNum"><div class="size">{{ " 共" + _s(o.totalChapterNum) + "章 " }}</div></template></div><template v-if="!isSearchResult && o.durChapterTitle"><div class="dur-chapter">{{ " 已读：" + _s(o.durChapterTitle) + " " }}</div></template><template v-if="isSearchResult"><BookKind :book="o" /></template><template v-if="o.latestChapterTitle"><div class="last-chapter">{{ " " + _s(o.lastCheckTime ? dateFormat(o.lastCheckTime) : "最新") + "：" + _s(o.latestChapterTitle) + " " }}</div></template><template v-if="isSearchResult"><div><el-tag class="setting-connect" type="success" :effect='$store.getters.isNight ? "dark" : "light"' @click='(e) => {
                                    return e.stopPropagation(), $emit("addBookToShelf", o)
                                }'> 加入书架 </el-tag><template v-if="showSourceCount"><span class="source-count" @click='(e) => {
                                    return e.stopPropagation(), $emit("showSearchBookSourceList", o)
                                }'>{{ _s(o.sourceCount || 1) + " 个书源" }}</span></template></div></template></div></div></template></div>
</template>

<script>
// 依赖的 webpack 模块 id 见同目录 _meta.json 与 docs/FRONTEND-RECONSTRUCTION.md
export default {
  name: "VirtualSelect",
    props: {
    width: {
    type: Number,
    default: 250
    },
    size: {
    type: String,
    default: "small"
    },
    placeholder: {
    type: String,
    default: "请选择"
    },
    dataList: {
    type: Array,
    default: function() {
    return []
    }
    },
    value: {
    type: [String, Number],
    default: ""
    }
    },
    components: {
    VirtList: _["a"]
    },
    watch: {
    visibleVirtualList: function(t) {
    var e = this;
    t ? (this.keyword = "", this.$nextTick((function() {
    var t = e.curIndex ? e.curIndex : 0;
    e.$refs.virtList.scrollToIndex(t - 1)
    }))) : this.keyword = this.curLabel
    },
    value: function(t) {
    var e;
    this.curValue = t, this.curLabel = (null === (e = this.dataList.find((function(e) {
    return e.bookSourceUrl === t
    }))) || void 0 === e ? void 0 : e.bookSourceName) || "", this.curIndex = this.dataList.findIndex((function(e) {
    return e.bookSourceUrl === t
    }))
    },
    dataList: function(t) {
    var e, o = this;
    this.curLabel = (null === (e = t.find((function(t) {
    return t.bookSourceUrl === o.value
    }))) || void 0 === e ? void 0 : e.bookSourceName) || "", this.curIndex = t.findIndex((function(t) {
    return t.bookSourceUrl === o.value
    }))
    }
    },
    data: function() {
    return {
    visibleVirtualList: !1,
    curValue: this.value,
    curLabel: "",
    curIndex: null,
    keyword: ""
    }
    },
    created: function() {
    var t = this;
    this.$on("clickVirtualItem", (function(e) {
    t.curIndex = e.index, t.curLabel = e.bookSourceName, t.visibleVirtualList = !1, t.$emit("input", e.bookSourceUrl)
    }))
    },
    computed: {
    list: function() {
    var t = this;
    return this.dataList.filter((function(e) {
    return !t.keyword || e.bookSourceName.includes(t.keyword)
    }))
    },
    tip: function() {
    return this.curLabel || this.placeholder || "请选择"
    }
    },
    mounted: function() {
    var t, e = this;
    this.curValue = this.value, this.curLabel = (null === (t = this.dataList.find((function(t) {
    return t.bookSourceUrl === e.value
    }))) || void 0 === t ? void 0 : t.bookSourceName) || "", this.curIndex = this.dataList.findIndex((function(t) {
    return t.bookSourceUrl === e.value
    })), this.keyword = this.curLabel
    },
    methods: {
    handleInput: function(t) {
    this.keyword = t, this.$refs.virtList.$forceUpdate()
    }
    },
  render: function() {
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
                },
  staticRenderFns: []
}
</script>

<style scoped>
.wrapper { display:grid;grid-template-columns:repeat(auto-fill,380px);-ms-flex-pack:distribute;justify-content:space-around;grid-gap:10px }
.wrapper .book { -webkit-user-select:none;-ms-user-select:none;user-select:none;display:-ms-flexbox;display:flex;cursor:pointer;margin-bottom:18px;padding:24px 24px;width:360px;-ms-flex-direction:row;flex-direction:row;-ms-flex-pack:distribute;justify-content:space-around }
.wrapper .book .cover-img .cover, .wrapper .book .cover-img { width:84px;height:112px }
.wrapper .book .info { position:relative;display:-ms-flexbox;display:flex;-ms-flex-direction:column;flex-direction:column;-ms-flex-pack:justify;justify-content:space-between;-ms-flex-align:left;align-items:left;height:112px;margin-left:20px;-ms-flex:1;flex:1 }
.wrapper .book .info .book-operation { position:absolute;right:5px;top:0;font-size:24px;color:#969ba3 }
.wrapper .book .info .book-operation i { margin-left:10px }
.wrapper .book .info .name { width:-moz-fit-content;width:fit-content;font-size:16px;font-weight:700;color:#33373d;margin-right:38px;max-height:45px;word-wrap:break-word;overflow:hidden;text-overflow:ellipsis;display:-webkit-box;-webkit-box-orient:vertical;-webkit-line-clamp:2 }
.wrapper .book .info .name.edit { margin-right:62px }
.wrapper .book .info .sub { display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;font-size:12px;font-weight:600;color:#969ba3 }
.wrapper .book .info .sub .dot { margin:0 7px }
.wrapper .book .info .dur-chapter, .wrapper .book .info .intro, .wrapper .book .info .last-chapter { color:#6b6b6b;font-size:13px;margin-top:3px;font-weight:500;word-wrap:break-word;overflow:hidden;text-overflow:ellipsis;display:-webkit-box;-webkit-box-orient:vertical;-webkit-line-clamp:1;text-align:left }
.wrapper .book .info .source-count { color:#6b6b6b;font-size:14px;display:inline-block;margin-left:10px;cursor:pointer }
.wrapper:last-child { margin-right:auto }
.wrapper { display:-ms-flexbox;display:flex;-ms-flex-direction:column;flex-direction:column }
.wrapper .book { box-sizing:border-box;width:100%;margin-bottom:0;padding:10px 20px }

@media screen and (max-width:750px) {
  .wrapper{display:-ms-flexbox;display:flex;-ms-flex-direction:column;flex-direction:column}
  .wrapper .book{box-sizing:border-box;width:100%;margin-bottom:0;padding:10px 20px}
}
</style>
