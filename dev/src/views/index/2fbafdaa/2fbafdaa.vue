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
import { getStorage, setStorage } from '@/utils/storage'
import { asyncToGenerator } from '@/utils/async'
import { regeneratorRuntime } from '@/utils/regenerator'
import { merge as __merge } from '@/utils/merge'
import { clone } from '@/utils/clone'
import { cachedFetch } from '@/utils/common'
import eventBus from '@/utils/event-bus'
import axios from 'axios'

export default {
                    name: "BookList",
                    data: function() {
                        return {
                            containerWith: (this.$refs.wrapper || {}).clientWidth || 750
                        }
                    },
                    props: ["showNavigation", "bookList", "isSearchResult", "showBookEditButton"],
                    computed: __merge(__merge({}, Object(__merge)(["dialogSmallWidth", "dialogTop"])), {}, {
                        bookItemWidth: function() {
                            var t = parseInt(this.$store.state.shelfConfig.viewCate.replace("column-", ""));
                            return t < 10 ? (this.containerWith - 15 * (t - 1) - 40) / t + "px" : t + "px"
                        }
                    }),
                    mounted: function() {
                        var t = this;
                        this.setWidth(), this.resizeObserver = new ResizeObserver((function() {
                            t.setWidth()
                        })), this.resizeObserver.observe(this.$refs.wrapper)
                    },
                    destroyed: function() {
                        this.resizeObserver.disconnect(), this.resizeObserver = null
                    },
                    methods: {
                        getBookCoverUrl: function(t) {
                            return t.customCoverUrl || t.coverUrl
                        },
                        setWidth: function() {
                            this.containerWith = this.$refs.wrapper.clientWidth
                        }
                    }
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
