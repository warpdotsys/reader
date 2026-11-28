<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=reader scopeId=510c7aa8；无 sourcemap，模板为 render 推断还原 -->
<template>
<div class="popup-wrapper" :style="popupTheme"><div class="title-zone"><div class="title">{{ "书架(" + _s(shelfBooks.length) + ")" }}</div><div :class="{'title-btn': !0, loading: refreshLoading}"><span class="span-btn" @click="(t) => {
                                expand = !expand
                            }">{{ " " + _s(expand ? "收起" : "展开") + " " }}</span><span :class="{loading: refreshLoading}" @click="refreshShelf"><template v-if="refreshLoading"><i class="el-icon-loading" /></template>{{ " " + _s(refreshLoading ? "刷新中..." : "刷新") + " " }}</span></div></div><div ref='"bookList"' class="data-wrapper" :class="{night: $store.getters.isNight, day: !$store.getters.isNight, expand: expand}"><div class="shelfbook-list"><template v-if='shelfBooks.length && "yes" === $store.getters.shelfConfig.virtualOptimize'><BookShelfVirtualList :bookList="shelfBooks" :visible="visible" @changeBook="changeBook" /></template><template v-else><template v-for="n in shelfBooks"><div :key="n.bookUrl" ref='"book"' class="book-item" :class="{selected: isSelected(n)}" @click="(t) => {
                                    return changeBook(n)
                                }"><div class="book-title"><div class="book-name">{{ " " + _s(n.name) + " " }}</div><template v-if="n.totalChapterNum - 1 - n.durChapterIndex"><div class="book-progress">{{ " " + _s(n.totalChapterNum - 1 - n.durChapterIndex) + " " }}</div></template></div><div class="chapter-text">{{ " " + _s(n.durChapterTitle) + " " }}</div></div></template></template></div></div></div>
</template>

<script>
import axios from 'axios'
import { cachedFetch } from '@/utils/common'
import VirtList from '@/components/VirtList'

/**
 * 元素平滑滚动到视口（产物模块 8efd 的默认导出，调用点变量 c 的 a 导出）。
 * 支持传入数字 / 元素 / 选择器，以及 { duration, offset, easing, a11y, callback, container }。
 */
function scrollToElement(el, options) {
  var opts = options || {}
  var container = null
  var target = null
  var start = 0
  var to = 0
  var distance = 0
  var begin = false
  var duration = opts.duration || 1000
  var offset = opts.offset || 0
  var callback = opts.callback
  var a11y = opts.a11y || false
  var easing =
    opts.easing ||
    function (t, b, c, d) {
      t /= d / 2
      if (t < 1) return (c / 2) * t * t + b
      t--
      return (-c / 2) * (t * (t - 2) - 1) + b
    }

  var kind = opts.container
  if (kind && typeof kind === 'object') container = kind
  else if (typeof kind === 'string') container = document.querySelector(kind)
  else container = window

  function getScrollTop() {
    var t = container.scrollTop || container.scrollY || container.pageYOffset
    return typeof t === 'undefined' ? 0 : t
  }

  function getElementTop(node) {
    var rectTop = node.getBoundingClientRect().top
    var containerTop = container.getBoundingClientRect ? container.getBoundingClientRect().top : 0
    return rectTop - containerTop + start
  }

  function setScrollTop(top) {
    if (container.scrollTo) container.scrollTo(0, top)
    else container.scrollTop = top
  }

  function finish() {
    setScrollTop(start + to)
    if (target && a11y) {
      target.setAttribute('tabindex', '-1')
      target.focus()
    }
    if (typeof callback === 'function') callback()
    begin = false
  }

  function step(now) {
    if (!begin) begin = now
    distance = now - begin
    setScrollTop(easing(distance, start, to, duration))
    if (distance < duration) requestAnimationFrame(step)
    else finish()
  }

  start = getScrollTop()

  if (typeof el === 'number') {
    target = null
    a11y = false
    to = el
  } else if (el && typeof el === 'object') {
    target = el
    to = getElementTop(target)
  } else if (typeof el === 'string') {
    target = document.querySelector(el)
    to = getElementTop(target)
  }

  to = to - start + offset
  if (typeof opts.duration === 'function') duration = opts.duration(to)

  requestAnimationFrame(step)
}

// BookShelfVirtualList：产物里与 BookShelf 同模块内联定义的虚拟列表包装（options K + render W）
const BookShelfVirtualList = {
  name: "BookShelfVirtualList",
  components: {
      VirtList
  },
  props: ["bookList", "visible"],
  watch: {
      visible: function() {
          var e = this;
          this.$nextTick((function() {
              e.$refs.virtList.$forceUpdate();
              var t = e.bookList.findIndex((function(t) {
                  return t.bookUrl === e.$store.getters.readingBook.bookUrl
              }));
              e.$refs.virtList && e.$refs.virtList.scrollToIndex(t >= 0 ? t : 0)
          }))
      }
  },
  render: function() {
  var e = this,
      t = e._self._c;
  return e.visible ? t("VirtList", {
      ref: "virtList",
      attrs: {
          itemKey: "bookUrl",
          list: e.bookList,
          minSize: 40
      },
      scopedSlots: e._u([{
          key: "default",
          fn: function(n) {
              var a = n.itemData;
              return [t("div", {
                  ref: "book",
                  staticClass: "book-item",
                  class: {
                      selected: a.bookUrl === e.$store.getters.readingBook.bookUrl
                  },
                  on: {
                      click: function(t) {
                          return e.$emit("changeBook", a)
                      }
                  }
              }, [t("div", {
                  staticClass: "book-title"
              }, [t("div", {
                  staticClass: "book-name"
              }, [e._v(" " + e._s(a.name) + " ")]), a.totalChapterNum - 1 - a.durChapterIndex ? t("div", {
                  staticClass: "book-progress"
              }, [e._v(" " + e._s(a.totalChapterNum - 1 - a.durChapterIndex) + " ")]) : e._e()]), t("div", {
                  staticClass: "chapter-text"
              }, [e._v(" " + e._s(a.durChapterTitle) + " ")])])]
          }
      }], null, !1, 690613472)
  }) : e._e()
  },
}

export default {
  name: "BookShelf",
  data: function() {
      return {
          refreshLoading: !1,
          expand: !1
      }
  },
  components: {
      BookShelfVirtualList
  },
  props: ["visible"],
  computed: {
      theme: function() {
          return this.$store.getters.config.theme
      },
      popupTheme: function() {
          return {
              background: this.$store.getters.currentThemeConfig.popup
          }
      },
      shelfBooks: function() {
          return this.$store.getters.shelfBooks
      }
  },
  mounted: function() {},
  watch: {
      visible: function(e) {
          e && this.getBookshelf()
      }
  },
  methods: {
      isSelected: function(e) {
          return e.bookUrl == this.$store.getters.readingBook.bookUrl
      },
      getBookshelf: function(e) {
          var t = this;
          this.shelfBooks.length || cachedFetch((function() {
              return axios.get(t.api + "/getBookshelf", {
                  params: {
                      refresh: e ? 1 : 0
                  }
              })
          }), "getBookshelf" + ((this.$store.state.userInfo || {}).username || "default")).then((function(e) {
              t.refreshLoading = !1, e.data.isSuccess && (t.$store.commit("setShelfBooks", e.data.data), t.shelfBooks.length && t.jumpToActive())
          }), (function(e) {
              throw t.refreshLoading = !1, t.$message.error("获取书架书籍失败 " + (e && e.toString())), e
          }))
      },
      changeBook: function(e) {
          var t, n, a = {
              name: e.name,
              bookUrl: e.bookUrl,
              index: null !== (t = null !== (n = e.index) && void 0 !== n ? n : e.durChapterIndex) && void 0 !== t ? t : 0,
              type: e.type,
              coverUrl: e.customCoverUrl || e.coverUrl,
              tocUrl: e.tocUrl,
              author: e.author,
              origin: e.origin,
              originName: e.originName,
              latestChapterTitle: e.latestChapterTitle,
              intro: e.intro
          };
          this.$emit("changeBook", a)
      },
      refreshShelf: function() {
          this.refreshLoading || (this.refreshLoading = !0, this.getBookshelf(!0))
      },
      jumpToActive: function() {
          var e = this;
          "yes" !== this.$store.getters.shelfConfig.virtualOptimize && this.$nextTick((function() {
              var t = -1;
              if (e.shelfBooks.some((function(n, a) {
                      if (n.bookUrl == e.$store.getters.readingBook.bookUrl) return t = a, !0
                  })), !(t < 0))
                  if (e.$refs.book && e.$refs.book[t]) {
                      var n = e.$refs.bookList;
                      scrollToElement(e.$refs.book[t], {
                          container: n,
                          duration: 0
                      })
                  } else setTimeout((function() {
                      e.jumpToActive()
                  }), 10)
          }))
      },
      backToHome: function() {
          this.$emit("toShelf")
      }
  },
}
</script>

<style scoped>
.popup-wrapper { margin:-16px;margin-bottom:-13px;padding:24px;padding-top:calc(24px + constant(safe-area-inset-top));padding-top:calc(24px + env(safe-area-inset-top)) }
.popup-wrapper .title-zone { margin:0 0 20px 0;width:100%;display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-pack:justify;justify-content:space-between }
.popup-wrapper .title { font-size:18px;font-weight:400;font-family:-apple-system,Noto Sans,Helvetica Neue,Helvetica,Nimbus Sans L,Arial,Liberation Sans,PingFang SC,Hiragino Sans GB,Noto Sans CJK SC,Source Han Sans SC,Source Han Sans CN,Microsoft YaHei,Wenquanyi Micro Hei,WenQuanYi Zen Hei,ST Heiti,SimHei,WenQuanYi Zen Hei Sharp,sans-serif;color:#ed4259;border-bottom:1px solid #ed4259;width:-moz-fit-content;width:fit-content }
.popup-wrapper .title-btn { font-size:14px;line-height:26px;color:#ed4259;cursor:pointer }
.popup-wrapper .title-btn .home-btn { display:inline-block;margin-right:25px }
.popup-wrapper .title-btn.loading { color:#606266 }
.popup-wrapper .data-wrapper { height:300px;overflow:auto }
.popup-wrapper .data-wrapper .shelfbook-list { height:100% }
.popup-wrapper .data-wrapper .shelfbook-list .book-item { width:100%;cursor:pointer;display:-ms-flexbox;display:flex;-ms-flex-direction:column;flex-direction:column;max-width:100%;overflow:hidden;padding:8px 0 }
.popup-wrapper .data-wrapper .shelfbook-list .book-item .book-title { display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-pack:justify;justify-content:space-between;-ms-flex-align:center;align-items:center }
.popup-wrapper .data-wrapper .shelfbook-list .book-item .book-title .book-name { font-size:16px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis }
.popup-wrapper .data-wrapper .shelfbook-list .book-item .book-title .book-progress { float:right;font-size:12px }
.popup-wrapper .data-wrapper .shelfbook-list .book-item .chapter-text { overflow:hidden;white-space:nowrap;text-overflow:ellipsis;color:#888;font-size:14px;margin-top:6px }
.popup-wrapper .data-wrapper .shelfbook-list .book-item.selected .book-name { color:#eb4259 }
.popup-wrapper .data-wrapper.expand { height:85vh }
.popup-wrapper .data-wrapper::-webkit-scrollbar { width:0!important }
.popup-wrapper .night .shelfbook-list .book-item { border-bottom:1px solid #333 }
.popup-wrapper .night .shelfbook-list .book-item .book-title { color:#888 }
.popup-wrapper .night .shelfbook-list .book-item .chapter-text { color:#555 }
.popup-wrapper .day .book-item { border-bottom:1px solid #eee }
</style>
