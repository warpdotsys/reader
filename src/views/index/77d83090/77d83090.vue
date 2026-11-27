<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=index scopeId=77d83090；无 sourcemap，模板为 render 推断还原 -->
<template>
<div ref='"wrapper"' class="wrapper" :style="{'grid-template-columns': &quot;repeat(auto-fill, &quot; + bookItemWidth + &quot;)&quot;}"><template v-for="o in bookList"><div :key="o.bookUrl" class="book" :style="{'--book-item-width': bookItemWidth}" @click='(e) => {
                                    return $emit("toDetail", o)
                                }'><div class="cover-img" @click='(e) => {
                                    return e.ctrlKey ? e.shiftKey || e.altKey || e.metaKey ? null : (e.stopPropagation(), $emit("showBookInfoDialog", o)) : null
                                }'><el-image ref='"bookCoverList"' class="cover" :src="getCover(getBookCoverUrl(o), !0)" fit="cover" lazy="" /></div><div class="name">{{ " " + _s(o.name) + " " }}</div><div class="book-operation"><template v-if="!isSearchResult && showBookEditButton"><i class="el-icon-close" @click='(e) => {
                                    return e.stopPropagation(), $emit("deleteBook", o)
                                }' /></template><template v-if="!isSearchResult && showBookEditButton"><i class="el-icon-edit" @click='(e) => {
                                    return e.stopPropagation(), $emit("editBook", o)
                                }' /></template><template v-if="isSearchResult"><i class="el-icon-edit" @click='(e) => {
                                    return e.stopPropagation(), $emit("editBook", o, !0)
                                }' /></template><template v-if="!isSearchResult && !showBookEditButton && o.totalChapterNum - 1 - o.durChapterIndex > 0"><el-badge class="unread-num-badge" :max="99" :value="o.totalChapterNum - 1 - o.durChapterIndex" /></template></div></div></template></div>
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
                },
  staticRenderFns: []
}
</script>

<style scoped>
.wrapper { display:grid;-ms-flex-pack:distribute;justify-content:space-around;grid-gap:15px;padding:0 20px }
.wrapper .book { -webkit-user-select:none;-ms-user-select:none;user-select:none;display:-ms-flexbox;display:flex;cursor:pointer;padding:0;width:var(--book-item-width,100px);-ms-flex-direction:column;flex-direction:column;box-sizing:border-box;position:relative }
.wrapper .book .cover-img .cover, .wrapper .book .cover-img { width:100%;height:calc(var(--book-item-width, 100px)*1.5) }
.wrapper .book .name { width:100%;text-align:center;font-size:14px;font-weight:700;color:#33373d;max-height:40px;word-wrap:break-word;overflow:hidden;margin-top:5px;text-overflow:ellipsis;display:-webkit-box;-webkit-box-orient:vertical;-webkit-line-clamp:2 }
.wrapper .book .book-operation { position:absolute;right:0;top:0;font-size:24px;color:#969ba3 }
.wrapper .book .book-operation i { margin-left:10px;display:block;color:#8abcff }
</style>
