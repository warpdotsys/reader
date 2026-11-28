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
.wrapper { display:grid;-ms-flex-pack:distribute;justify-content:space-around;grid-gap:15px;padding:0 20px }
.wrapper .book { -webkit-user-select:none;-ms-user-select:none;user-select:none;display:-ms-flexbox;display:flex;cursor:pointer;padding:0;width:var(--book-item-width,100px);-ms-flex-direction:column;flex-direction:column;box-sizing:border-box;position:relative }
.wrapper .book .cover-img .cover, .wrapper .book .cover-img { width:100%;height:calc(var(--book-item-width, 100px)*1.5) }
.wrapper .book .name { width:100%;text-align:center;font-size:14px;font-weight:700;color:#33373d;max-height:40px;word-wrap:break-word;overflow:hidden;margin-top:5px;text-overflow:ellipsis;display:-webkit-box;-webkit-box-orient:vertical;-webkit-line-clamp:2 }
.wrapper .book .book-operation { position:absolute;right:0;top:0;font-size:24px;color:#969ba3 }
.wrapper .book .book-operation i { margin-left:10px;display:block;color:#8abcff }
</style>
