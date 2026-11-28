<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=index scopeId=70ce2ba2；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog v-if="$store.getters.isNormalPage" :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' title="书架布局" :visible="show" :width="dialogSmallWidth" :top="dialogTop" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(e) => {
                                show = e
                            }"><el-form :model="shelfConfig"><el-form-item label="显示分组"><el-select class="setting-select" size="mini" filterable="" placeholder="请选择默认显示分组" v-model="shelfConfig.showBookGroup"><template v-for="(t, o) in bookGroupDisplayList"><el-option :key='"book-group-" + o' :label="t.groupName" :value="t.groupId" /></template></el-select></el-form-item><el-form-item label="显示视图"><el-select class="setting-select" size="mini" filterable="" placeholder="请选择视图" v-model="shelfConfig.viewCate"><template v-for="(t, o) in viewCateList"><el-option :key='"view-cate-" + o' :label="t.name" :value="t.value" /></template></el-select></el-form-item><el-form-item label="书籍排序"><el-select class="setting-select" size="mini" filterable="" placeholder="请选择排序" v-model="shelfConfig.bookOrder"><template v-for="(t, o) in bookOrderList"><el-option :key='"book-order-" + o' :label="t.name" :value="t.value" /></template></el-select></el-form-item><el-form-item label="图片代理"><el-select class="setting-select" size="mini" filterable="" placeholder="请选择图片代理" v-model="shelfConfig.imageProxy"><template v-for="(t, o) in imageProxyList"><el-option :key='"book-image-" + o' :label="t.name" :value="t.value" /></template></el-select></el-form-item><el-form-item label="列表优化"><el-select class="setting-select" size="mini" filterable="" placeholder="请选择是否启用列表优化" v-model="shelfConfig.virtualOptimize"><template v-for="(t, o) in virtualOptimizeList"><el-option :key='"virtual-options-" + o' :label="t.name" :value="t.value" /></template></el-select></el-form-item></el-form><div class="dialog-footer" slot="footer"><el-button size="medium" type="primary" @click="save">保 存</el-button><el-button size="medium" type="primary" @click="cancel">关 闭</el-button></div></el-dialog></el-dialog>
</template>

<script>
import { getStorage, setStorage } from '@/utils/storage'
import { asyncToGenerator } from '@/utils/async'
import { regeneratorRuntime } from '@/utils/regenerator'
import { merge as __merge } from '@/utils/merge'
import { clone } from '@/utils/clone'
import { cachedFetch } from '@/utils/common'
import eventBus from '@/utils/event-bus'
import axios from 'axios'
import defaults from '@/config/defaults'

export default {
                    model: {
                        prop: "show",
                        event: "setShow"
                    },
                    name: "ShelfSettings",
                    data: function() {
                        return {
                            isAdd: true,
                            shelfConfig: __merge(__merge({}, defaults.shelfConfig), this.$store.state.shelfConfig),
                            viewCateList: [{
                                name: "列表",
                                value: "list"
                            }, {
                                name: "网格3列",
                                value: "column-3"
                            }, {
                                name: "网格4列",
                                value: "column-4"
                            }, {
                                name: "网格5列",
                                value: "column-5"
                            }, {
                                name: "网格6列",
                                value: "column-6"
                            }, {
                                name: "网格(小)",
                                value: "column-100"
                            }, {
                                name: "网格(中)",
                                value: "column-150"
                            }, {
                                name: "网格(大)",
                                value: "column-200"
                            }],
                            bookOrderList: [{
                                name: "按阅读时间",
                                value: "durChapterTime"
                            }, {
                                name: "按更新时间",
                                value: "lastCheckTime"
                            }, {
                                name: "按书名",
                                value: "name"
                            }],
                            imageProxyList: [{
                                name: "不使用代理",
                                value: "noProxy"
                            }, {
                                name: "服务器代理",
                                value: "serverProxy"
                            }],
                            virtualOptimizeList: [{
                                name: "启用虚拟列表优化",
                                value: "yes"
                            }, {
                                name: "不启用优化",
                                value: "no"
                            }]
                        }
                    },
                    props: ["show"],
                    computed: __merge(__merge({}, Object(__merge)(["dialogSmallWidth", "dialogTop"])), {}, {
                        bookGroupDisplayList: function() {
                            var t = this;
                            return this.$store.state.bookGroupList.filter((function(e) {
                                return t.getShowShelfBooks(e.groupId).length && e.show
                            })).sort((function(t, e) {
                                return t.order - e.order
                            }))
                        },
                        shelfBooks: function() {
                            return this.$store.getters.shelfBooks
                        }
                    }),
                    watch: {
                        show: function(t) {
                            t && (this.shelfConfig = __merge(__merge({}, defaults.shelfConfig), this.$store.state.shelfConfig))
                        }
                    },
                    methods: {
                        cancel: function() {
                            this.$emit("setShow", false)
                        },
                        getShowShelfBooks: function(t) {
                            return -1 === t ? this.shelfBooks : -2 === t ? this.shelfBooks.filter((function(t) {
                                return "loc_book" === t.origin
                            })) : -3 === t ? this.shelfBooks.filter((function(t) {
                                return 1 === t.type
                            })) : -4 === t ? this.shelfBooks.filter((function(t) {
                                return 0 === t.group
                            })) : -5 === t ? this.shelfBooks.filter((function(t) {
                                return t.lastCheckError
                            })) : this.shelfBooks.filter((function(e) {
                                return 0 === t || z.a.fromNumber(e.group).and(t).greaterThan(0)
                            }))
                        },
                        save: function() {
                            this.$store.commit("setShelfConfig", __merge({}, this.shelfConfig))
                        }
                    }
                }
</script>
