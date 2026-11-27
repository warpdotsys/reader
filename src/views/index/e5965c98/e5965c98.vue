<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=index scopeId=e5965c98；无 sourcemap，模板为 render 推断还原 -->
<template>
<div class="popup-wrapper" :style="popupTheme"><div class="title-zone"><div class="title">书海</div><div :class="{'title-btn': !0}"><span class="source-count">{{ " 共" + _s(bookSourceShowLength) + "个可用书源 " }}</span><template v-if="$store.state.miniInterface"><i class="el-icon-close close-btn" @click="(e) => {
                                return e.stopPropagation(), close.apply(null, arguments)
                            }" /></template></div></div><div class="source-group-wrapper"><el-tabs class="booksource-group-tabs" stretch="" v-model="sourceGroup"><template v-for="t in bookSourceGroup"><el-tab-pane :key='"sourceGroupTab-" + t' :label="t" :name="t" /></template></el-tabs></div><div ref='"sourceList"' class="data-wrapper" :class="{night: $store.getters.isNight, day: !$store.getters.isNight}" @scroll="scrollHandler"><div class="cata"><template v-if="visible"><el-collapse ref='"sourceList"' class="source-collapse" v-model="showCollapse" @change="onCollapseChange"><template v-for="(o, s) in bookSourceListNew"><el-collapse-item :key='"source-" + s' ref='"source"' :title="o.bookSourceName" :name="s" v-show='!sourceGroup || "全部" === sourceGroup || "未分组" === sourceGroup && "" === o.bookSourceGroup || (o.bookSourceGroup + ",").indexOf(sourceGroup + ",") >= 0'><template v-if="showCollapse.includes(s)"><template v-for="(s, r) in o.exploreGroup"><div :key='"group-" + r' class="explore-group"><template v-for="(s, r) in s"><el-tag :key='"group-" + r' class="explore-btn" type="info" :effect='$store.getters.isNight ? "dark" : "light"' @click="(e) => {
                                            return exploreBookSource(s.url, o.bookSourceUrl, 1, e)
                                        }">{{ " " + _s(s.name) + " " }}</el-tag></template></div></template></template></el-collapse-item></template></el-collapse></template></div></div></div>
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
                },
  staticRenderFns: []
}
</script>

<style scoped>
.popup-wrapper { margin:-16px;margin-bottom:-13px;padding:24px;padding-top:calc(24px + constant(safe-area-inset-top));padding-top:calc(24px + env(safe-area-inset-top)) }
.popup-wrapper .title-zone { margin:0 0 20px 0;width:100%;display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-pack:justify;justify-content:space-between }
.popup-wrapper .title { font-size:18px;font-weight:400;font-family:-apple-system,Noto Sans,Helvetica Neue,Helvetica,Nimbus Sans L,Arial,Liberation Sans,PingFang SC,Hiragino Sans GB,Noto Sans CJK SC,Source Han Sans SC,Source Han Sans CN,Microsoft YaHei,Wenquanyi Micro Hei,WenQuanYi Zen Hei,ST Heiti,SimHei,WenQuanYi Zen Hei Sharp,sans-serif;color:#ed4259;border-bottom:1px solid #ed4259;width:-moz-fit-content;width:fit-content }
.popup-wrapper .title-btn { font-size:14px;line-height:26px;color:#ed4259 }
.popup-wrapper .title-btn .source-count { display:inline-block;margin-right:25px;color:#606266 }
.popup-wrapper .title-btn .close-btn { font-size:20px;vertical-align:middle;cursor:pointer }
.popup-wrapper .title-btn.loading { color:#606266 }
.popup-wrapper .source-group-wrapper { display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;overflow-x:auto;padding:5px 0 }
.popup-wrapper .source-group-wrapper .source-group-btn { margin-right:10px;cursor:pointer }
.popup-wrapper .source-group-wrapper .booksource-group-tabs { width:100% }
.popup-wrapper .source-group-wrapper .source-group-btn.selected { color:#ed4259 }
.popup-wrapper .data-wrapper { height:300px;overflow:auto }
.popup-wrapper .data-wrapper .cata { display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-pack:justify;justify-content:space-between }
.popup-wrapper .data-wrapper .cata .source-collapse { width:100%;border:none }
.popup-wrapper .data-wrapper .cata .explore-group { display:-ms-flexbox;display:flex;-ms-flex-pack:justify;justify-content:space-between;margin-bottom:2px;padding-top:2px;overflow-x:auto }
.popup-wrapper .data-wrapper .cata .explore-btn { margin-right:15px;margin-bottom:5px;cursor:pointer }
.popup-wrapper .data-wrapper::-webkit-scrollbar { width:0!important }
.popup-wrapper .el-collapse-item__header, .popup-wrapper .el-collapse-item__wrap { background:rgba(0,0,0,0);color:#606266 }
.popup-wrapper .el-collapse-item__content { color:#606266;padding:10px }
.popup-wrapper .night .el-collapse-item__header, .popup-wrapper .night .el-collapse-item__wrap { border-bottom:1px solid #666 }
.popup-wrapper .night .explore-group { border-bottom:1px dashed #333 }
.popup-wrapper .day .el-collapse-item__header, .popup-wrapper .day .el-collapse-item__wrap { border-bottom:1px solid #ebeef5 }
.popup-wrapper .day .explore-group { border-bottom:1px dashed #efefef }
</style>
