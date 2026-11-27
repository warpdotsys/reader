<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=index scopeId=70ce2ba2；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog v-if="$store.getters.isNormalPage" :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' title="书架布局" :visible="show" :width="dialogSmallWidth" :top="dialogTop" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(e) => {
                                show = e
                            }"><el-form :model="shelfConfig"><el-form-item label="显示分组"><el-select class="setting-select" size="mini" filterable="" placeholder="请选择默认显示分组" v-model="shelfConfig.showBookGroup"><template v-for="(t, o) in bookGroupDisplayList"><el-option :key='"book-group-" + o' :label="t.groupName" :value="t.groupId" /></template></el-select></el-form-item><el-form-item label="显示视图"><el-select class="setting-select" size="mini" filterable="" placeholder="请选择视图" v-model="shelfConfig.viewCate"><template v-for="(t, o) in viewCateList"><el-option :key='"view-cate-" + o' :label="t.name" :value="t.value" /></template></el-select></el-form-item><el-form-item label="书籍排序"><el-select class="setting-select" size="mini" filterable="" placeholder="请选择排序" v-model="shelfConfig.bookOrder"><template v-for="(t, o) in bookOrderList"><el-option :key='"book-order-" + o' :label="t.name" :value="t.value" /></template></el-select></el-form-item><el-form-item label="图片代理"><el-select class="setting-select" size="mini" filterable="" placeholder="请选择图片代理" v-model="shelfConfig.imageProxy"><template v-for="(t, o) in imageProxyList"><el-option :key='"book-image-" + o' :label="t.name" :value="t.value" /></template></el-select></el-form-item><el-form-item label="列表优化"><el-select class="setting-select" size="mini" filterable="" placeholder="请选择是否启用列表优化" v-model="shelfConfig.virtualOptimize"><template v-for="(t, o) in virtualOptimizeList"><el-option :key='"virtual-options-" + o' :label="t.name" :value="t.value" /></template></el-select></el-form-item></el-form><div class="dialog-footer" slot="footer"><el-button size="medium" type="primary" @click="save">保 存</el-button><el-button size="medium" type="primary" @click="cancel">关 闭</el-button></div></el-dialog></el-dialog>
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
                },
  staticRenderFns: []
}
</script>
