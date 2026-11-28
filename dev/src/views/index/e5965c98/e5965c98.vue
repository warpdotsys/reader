<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=index scopeId=e5965c98；无 sourcemap，模板为 render 推断还原 -->
<template>
<div class="popup-wrapper" :style="popupTheme"><div class="title-zone"><div class="title">书海</div><div :class="{'title-btn': !0}"><span class="source-count">{{ " 共" + _s(bookSourceShowLength) + "个可用书源 " }}</span><template v-if="$store.state.miniInterface"><i class="el-icon-close close-btn" @click="(e) => {
                                return e.stopPropagation(), close.apply(null, arguments)
                            }" /></template></div></div><div class="source-group-wrapper"><el-tabs class="booksource-group-tabs" stretch="" v-model="sourceGroup"><template v-for="t in bookSourceGroup"><el-tab-pane :key='"sourceGroupTab-" + t' :label="t" :name="t" /></template></el-tabs></div><div ref='"sourceList"' class="data-wrapper" :class="{night: $store.getters.isNight, day: !$store.getters.isNight}" @scroll="scrollHandler"><div class="cata"><template v-if="visible"><el-collapse ref='"sourceList"' class="source-collapse" v-model="showCollapse" @change="onCollapseChange"><template v-for="(o, s) in bookSourceListNew"><el-collapse-item :key='"source-" + s' ref='"source"' :title="o.bookSourceName" :name="s" v-show='!sourceGroup || "全部" === sourceGroup || "未分组" === sourceGroup && "" === o.bookSourceGroup || (o.bookSourceGroup + ",").indexOf(sourceGroup + ",") >= 0'><template v-if="showCollapse.includes(s)"><template v-for="(s, r) in o.exploreGroup"><div :key='"group-" + r' class="explore-group"><template v-for="(s, r) in s"><el-tag :key='"group-" + r' class="explore-btn" type="info" :effect='$store.getters.isNight ? "dark" : "light"' @click="(e) => {
                                            return exploreBookSource(s.url, o.bookSourceUrl, 1, e)
                                        }">{{ " " + _s(s.name) + " " }}</el-tag></template></div></template></template></el-collapse-item></template></el-collapse></template></div></div></div>
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

export default {
                    name: "Explore",
                    data: function() {
                        return {
                            page: 1,
                            ruleFindUrl: "",
                            bookSourceUrl: "",
                            exploreList: [],
                            showCollapse: [],
                            sourceGroup: "全部",
                            lastCollapseLength: 0
                        }
                    },
                    props: ["visible", "bookSourceList"],
                    computed: {
                        theme: function() {
                            return this.$store.getters.config.theme
                        },
                        popupTheme: function() {
                            return {
                                background: this.$store.getters.currentThemeConfig.popup
                            }
                        },
                        bookSourceListNew: function() {
                            return this.bookSourceList.filter((function(t) {
                                return t.exploreUrl
                            }))
                        },
                        bookSourceGroup: function() {
                            var t = new Set;
                            return t.add("全部"), this.bookSourceListNew.forEach((function(e) {
                                e.bookSourceGroup && e.bookSourceGroup.split(",").forEach((function(e) {
                                    e && t.add(e)
                                }))
                            })), t.add("未分组"), Array.from(t)
                        },
                        bookSourceShowLength: function() {
                            var t = this;
                            return this.sourceGroup && "全部" !== this.sourceGroup ? this.bookSourceListNew.filter((function(e) {
                                return "未分组" === t.sourceGroup ? !e.bookSourceGroup : (e.bookSourceGroup + ",").indexOf(t.sourceGroup + ",") >= 0
                            })).length : this.bookSourceListNew.length
                        }
                    },
                    mounted: function() {
                        window.explorePop = this
                    },
                    watch: {
                        visible: function(t) {
                            var e = this;
                            t && this.$nextTick((function() {
                                e.lastScrollTop && (e.$refs.sourceList.scrollTop = e.lastScrollTop)
                            }))
                        }
                    },
                    methods: {
                        onCollapseChange: function(t) {
                            var e = this;
                            if (t.length > this.lastCollapseLength) {
                                var o = this.bookSourceList[t[t.length - 1]];
                                true === o.exploreUrl && f["a"].post(this.api + "/getBookSource", {
                                    bookSourceUrl: o.bookSourceUrl
                                }).then((function(t) {
                                    t.data.isSuccess && (o.exploreUrl = t.data.data.exploreUrl, e.$set(o, "exploreGroup", e.getExploreGroup(o)))
                                }), (function(t) {
                                    e.$message.error("加载书源信息失败 " + (t && t.toString()))
                                }))
                            }
                            this.lastCollapseLength = t.length
                        },
                        getExploreGroup: function(t) {
                            if (!t.exploreUrl) return [];
                            var e = [],
                                o = [],
                                s = [];
                            try {
                                s = JSON.parse(t.exploreUrl)
                            } catch (i) {
                                try {
                                    s = new Function("return " + t.exploreUrl)()
                                } catch (i) {}
                            }
                            if (Array.isArray(s) && s.length) {
                                var r = 0;
                                s.forEach((function(t) {
                                    var s = t.style && t.style.layout_flexBasisPercent && -1 !== t.style.layout_flexBasisPercent ? t.style.layout_flexBasisPercent : .25;
                                    o.push({
                                        name: t.title,
                                        url: t.url
                                    }), r += s, r >= 1 && (e.push(o), o = [], r = 0)
                                }))
                            } else t.exploreUrl.replace(/\r\n/g, "\n").split("\n").forEach((function(t) {
                                t ? (o.length >= 4 && (e.push(o), o = []), t = t.split("::"), o.push({
                                    name: t[0],
                                    url: t[1]
                                })) : o.length && (e.push(o), o = [])
                            }));
                            return o.length && e.push(o), e
                        },
                        exploreBookSource: function(t, e, o) {
                            var s = this;
                            this.page = o || 1, this.ruleFindUrl = t, this.bookSourceUrl = e, f["a"].post(this.api + "/exploreBook", {
                                ruleFindUrl: t,
                                bookSourceUrl: e,
                                page: o
                            }).then((function(t) {
                                if (t.data.isSuccess) {
                                    if (1 === o) s.exploreList = t.data.data;
                                    else {
                                        var e = [].concat(s.exploreList),
                                            r = e.reduce((function(t, e) {
                                                return t[e.bookUrl] = e, t
                                            }), {}),
                                            i = e.length;
                                        t.data.data.forEach((function(t) {
                                            r[t.bookUrl] || e.push(t)
                                        })), s.exploreList = e, e.length === i && s.$message.error("没有更多啦")
                                    }
                                    s.$emit("showSearchList", s.exploreList)
                                }
                            }), (function(t) {
                                throw s.$message.error("探索失败 " + (t && t.toString())), t
                            }))
                        },
                        loadMore: function() {
                            this.page = this.page + 1, this.exploreBookSource(this.ruleFindUrl, this.bookSourceUrl, this.page)
                        },
                        jumpToActive: function() {
                            var t = this;
                            this.$nextTick((function() {
                                var e = -1;
                                if (t.bookSourceListNew.some((function(o, s) {
                                        if (o.bookSourceUrl == t.bookSourceUrl) return e = s, true
                                    })), !(e < 0)) {
                                    var o = t.$refs.sourceList;
                                    __merge(t.$refs.source[e], {
                                        container: o,
                                        duration: 0
                                    })
                                }
                            }))
                        },
                        close: function() {
                            this.$emit("close")
                        },
                        scrollHandler: function() {
                            this.lastScrollTop = this.$refs.sourceList.scrollTop
                        },
                        setSourceGroup: function(t) {
                            this.sourceGroup === t ? this.sourceGroup = "" : this.sourceGroup = t
                        }
                    }
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
