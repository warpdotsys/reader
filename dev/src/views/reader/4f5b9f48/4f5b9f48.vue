<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=reader scopeId=4f5b9f48；无 sourcemap，模板为 render 推断还原 -->
<template>
<div class="popup-wrapper" :style="popupTheme"><div class="title-zone"><div class="title"> 目录 <template v-if="catalog.length"><span>{{ "(" + _s(catalog.length) + ")" }}</span></template></div><div :class="{'title-btn': !0}" v-show="!isSearching"><template v-if="catalog.length"><span class="span-btn" @click="(t) => {
                                expand = !expand
                            }">{{ " " + _s(expand ? "收起" : "展开") + " " }}</span></template><template v-if="catalog.length"><span class="span-btn" @click="(t) => {
                                asc = !asc
                            }">{{ asc ? "倒序" : "顺序" }}</span></template><template v-if="catalog.length"><span class="span-btn" @click="toTop">顶部</span></template><template v-if="catalog.length"><span class="span-btn" @click="toBottom">底部</span></template><template v-if='"loc_book" === book.origin'><span class="span-btn" @click="changeRule"> 修改规则 </span></template><span :class="{loading: refreshLoading, 'refresh-btn': !0}" @click="refreshChapter"><template v-if="refreshLoading"><i class="el-icon-loading" /></template>{{ " " + _s(refreshLoading ? "刷新中..." : "刷新") + " " }}</span><span class="span-btn" @click="(t) => {
                                isSearching = !isSearching
                            }">{{ " " + _s(isSearching ? "取消" : "搜索") + " " }}</span></div><div :class="{'title-btn': !0}" v-show="isSearching"><el-input class="booksource-filter-input" size="mini" placeholder="搜索目录" v-model="searchKeyword" /><span class="span-btn" @click="(t) => {
                                isSearching = !isSearching
                            }">{{ " " + _s(isSearching ? "取消" : "搜索") + " " }}</span></div></div><div ref='"cataData"' class="data-wrapper" :class="{night: $store.getters.isNight, day: !$store.getters.isNight, expand: expand}"><div class="cata"><template v-if='"yes" === $store.getters.shelfConfig.virtualOptimize'><PopCatalogVirtualList ref='"virtList"' :catalog="cataList" :cachedCataMap="cachedCataMap" :asc="asc" :visible="visible" @gotoChapter="gotoChapter" /></template><template v-else><template v-for="n in cataList"><div :key="n.index" ref='"cata"' class="log" :class="{selected: isSelected(n.index)}" @click="(t) => {
                                    return gotoChapter(n)
                                }"><div :class="{'log-text': !0, cached: cachedCataMap[n.index]}">{{ " " + _s(n.title) + " " }}</div></div></template></template></div></div></div>
</template>

<script>
import Vue from 'vue'
import axios from 'axios'
import { asyncToGenerator } from '@/utils/async'
import { regeneratorRuntime } from '@/utils/regenerator'
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

// PopCatalogVirtualList：产物里与 PopCata 同模块内联定义的虚拟目录列表（options F + render D）
const PopCatalogVirtualList = {
  name: "BookShelfVirtualList",
  components: {
      VirtList
  },
  props: ["catalog", "cachedCataMap", "asc", "visible"],
  watch: {
      visible: function() {
          var e = this;
          this.$nextTick((function() {
              var t = e.catalog.findIndex((function(t) {
                  return t.index === e.$store.getters.readingBook.index
              }));
              e.$refs.virtList && e.$refs.virtList.scrollToIndex(t >= 0 ? t : 0)
          }))
      }
  },
  methods: {
      isSelected: function(e) {
          return this.asc ? e === this.$store.getters.readingBook.index : this.catalog.length - 1 - e === this.$store.getters.readingBook.index
      },
      scrollToBottom: function() {
          var e = this;
          this.$nextTick((function() {
              e.$refs.virtList && e.$refs.virtList.scrollToBottom()
          }))
      },
      scrollToTop: function() {
          var e = this;
          this.$nextTick((function() {
              e.$refs.virtList && e.$refs.virtList.scrollToTop()
          }))
      }
  },
  render: function() {
  var e = this,
      t = e._self._c;
  return e.visible ? t("VirtList", {
      ref: "virtList",
      staticClass: "virtual-catalog",
      attrs: {
          itemKey: "index",
          list: e.catalog,
          minSize: 40
      },
      scopedSlots: e._u([{
          key: "default",
          fn: function(n) {
              var a = n.itemData;
              return [t("div", {
                  ref: "data",
                  staticClass: "log",
                  class: {
                      selected: e.isSelected(a.index)
                  },
                  on: {
                      click: function(t) {
                          return e.$emit("gotoChapter", a)
                      }
                  }
              }, [t("div", {
                  class: {
                      "log-text": !0,
                      "cached-text": e.cachedCataMap[a.index]
                  }
              }, [e._v(" " + e._s(a.title) + " ")])])]
          }
      }], null, !1, 650954991)
  }) : e._e()
  },
}

export default {
  name: "PopCata",
  components: {
      PopCatalogVirtualList
  },
  data: function() {
      return {
          refreshLoading: !1,
          asc: !0,
          expand: !1,
          cachedCataMap: {},
          tocUrl: "",
          isSearching: !1,
          searchKeyword: ""
      }
  },
  props: ["visible"],
  computed: {
      book: function() {
          return this.$store.getters.readingBook
      },
      index: function() {
          return this.$store.getters.readingBook.index
      },
      catalog: function() {
          return this.$store.getters.readingBook.catalog || []
      },
      cataList: function() {
          var e = this;
          return this.isSearching && this.searchKeyword ? this.asc ? this.catalog.filter((function(t) {
              return (t.title || "").includes(e.searchKeyword)
          })) : [].concat(this.catalog).reverse().filter((function(t) {
              return (t.title || "").includes(e.searchKeyword)
          })) : this.asc ? this.catalog : [].concat(this.catalog).reverse()
      },
      theme: function() {
          return this.$store.getters.config.theme
      },
      popupTheme: function() {
          return {
              background: this.$store.getters.currentThemeConfig.popup
          }
      },
      tocRuleList: function() {
          return this.book && this.book.originName ? this.book.originName.toLowerCase().endsWith(".txt") ? this.$store.state.txtTocRules : [{
              name: "根据 Spin 获取章节，使用 Toc 补充章节名",
              rule: "spin+toc"
          }, {
              name: "根据 Spin 获取章节，强制使用 Toc 章节名",
              rule: "spin<toc"
          }, {
              name: "根据 Spin 获取章节",
              rule: "spin"
          }, {
              name: "根据 Toc 获取章节，使用 Spin 补充章节名",
              rule: "toc+spin"
          }, {
              name: "根据 Toc 获取章节，强制使用 Spin 章节名",
              rule: "toc<spin"
          }, {
              name: "根据 Toc 获取章节",
              rule: "toc"
          }] : []
      }
  },
  mounted: function() {
      window.popcatalogComp = this
  },
  watch: {
      visible: function(e) {
          var t = this;
          e && (this.computeCachedCata(), this.$nextTick((function() {
              t.jumpToCurrent()
          })))
      },
      catalog: function() {
          this.refreshLoading = !1, this.computeCachedCata()
      },
      index: function() {
          this.computeCachedCata()
      },
      asc: function() {
          this.jumpToCurrent()
      }
  },
  methods: {
      isSelected: function(e) {
          return this.asc ? e === this.$store.getters.readingBook.index : this.catalog.length - 1 - e === this.$store.getters.readingBook.index
      },
      gotoChapter: function(e) {
          var t = this.catalog.indexOf(e);
          this.$emit("close"), this.$emit("gotoChapter", t)
      },
      refreshChapter: function() {
          this.refreshLoading = !0, this.$emit("refresh")
      },
      changeRule: function() {
          var e = this;
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              var n, a;
              return regeneratorRuntime.wrap((function(t) {
                  while (1) switch (t.prev = t.next) {
                      case 0:
                          return t.next = 2, e.$msgbox({
                              title: "修改目录规则",
                              message: e.renderComp(),
                              showCancelButton: !0,
                              confirmButtonText: "确定",
                              cancelButtonText: "取消"
                          })["catch"]((function(e) {
                              return "close" === e && "close"
                          }));
                      case 2:
                          if (n = t.sent, "confirm" !== n) {
                              t.next = 12;
                              break
                          }
                          if (e.tocUrl !== e.book.tocUrl) {
                              t.next = 7;
                              break
                          }
                          return e.$message.error("未修改规则"), t.abrupt("return");
                      case 7:
                          return a = e.$store.getters.shelfBooks.find((function(t) {
                              return t.bookUrl === e.book.bookUrl
                          })), a.tocUrl = e.tocUrl, t.abrupt("return", axios.post(e.api + "/saveBook", a).then((function(t) {
                              t.data.isSuccess && (e.$message.success("操作成功"), e.$store.commit("updateShelfBook", t.data.data), e.refreshLoading = !0, e.$emit("refresh"))
                          }), (function(t) {
                              e.$message.error("操作失败" + (t && t.toString()))
                          })));
                      case 12:
                          return t.abrupt("return", !1);
                      case 13:
                      case "end":
                          return t.stop()
                  }
              }), t)
          })))()
      },
      renderComp: function() {
          this.$createElement;
          var e = this.tocRuleList;
          this.tocUrl = this.book.tocUrl;
          var t = this;
          Vue.component("custComp2", {
              render: function() {
                  var t = this,
                      n = arguments[0];
                  return window.custComp2 = this, n("div", {
                      style: {
                          textAlign: "center"
                      }
                  }, [n("span", ["请选择规则："]), n("el-select", {
                      attrs: {
                          size: "mini",
                          filterable: !0,
                          placeholder: "未分组"
                      },
                      model: {
                          value: t.selectedRule,
                          callback: function(e) {
                              t.selectedRule = e
                          }
                      },
                      on: {
                          change: this.change
                      }
                  }, [e.map((function(e, t) {
                      return n("el-option", {
                          key: "rule-" + t,
                          attrs: {
                              label: e.name,
                              value: e.rule
                          }
                      })
                  }))]), n("el-input", {
                      attrs: {
                          type: "textarea",
                          rows: 3,
                          size: "small"
                      },
                      style: {
                          marginTop: "10px"
                      },
                      model: {
                          value: t.selectedRule,
                          callback: function(e) {
                              t.selectedRule = e
                          }
                      },
                      on: {
                          change: this.change
                      }
                  })])
              },
              data: function() {
                  return {
                      selectedRule: t.tocUrl
                  }
              },
              methods: {
                  change: function() {
                      t.tocUrl = this.selectedRule
                  }
              }
          });
          var n = Vue.component("custComp2");
          return this.$createElement(n)
      },
      jumpToCurrent: function(e) {
          var t = this;
          if ("yes" !== this.$store.getters.shelfConfig.virtualOptimize)
              if ("undefined" === typeof e && (e = this.asc ? this.$store.getters.readingBook.index : this.catalog.length - 1 - this.$store.getters.readingBook.index), this.$refs.cata && this.$refs.cata[e]) {
                  var n = this.$refs.cataData;
                  scrollToElement(this.$refs.cata[e], {
                      container: n,
                      duration: 0
                  })
              } else setTimeout((function() {
                  t.jumpToCurrent(e)
              }), 10)
      },
      toTop: function() {
          this.$refs.virtList ? this.$refs.virtList.scrollToTop() : this.jumpToCurrent(0)
      },
      toBottom: function() {
          this.$refs.virtList ? this.$refs.virtList.scrollToBottom() : this.jumpToCurrent(this.catalog.length - 1)
      },
      computeCachedCata: function() {
          var e = this,
              t = {},
              n = "localCache@" + this.$store.getters.readingBook.name + "_" + this.$store.getters.readingBook.author + "@" + this.$store.getters.readingBook.bookUrl + "@chapterContent-";
          window.$cacheStorage.iterate((function(e, a) {
              if (a.startsWith(n)) try {
                  var u = parseInt(a.replace(n, ""));
                  t[u] = !0
              } catch (re) {}
          })).then((function() {
              e.cachedCataMap = t
          }))["catch"]((function() {}))
      }
  },
}
</script>

<style scoped>
.popup-wrapper { margin:-16px;margin-bottom:-13px;padding:24px;padding-top:calc(24px + constant(safe-area-inset-top));padding-top:calc(24px + env(safe-area-inset-top)) }
.popup-wrapper .title-zone { margin:0 0 20px 0;width:100%;display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-pack:justify;justify-content:space-between }
.popup-wrapper .title { font-size:18px;font-weight:400;font-family:-apple-system,Noto Sans,Helvetica Neue,Helvetica,Nimbus Sans L,Arial,Liberation Sans,PingFang SC,Hiragino Sans GB,Noto Sans CJK SC,Source Han Sans SC,Source Han Sans CN,Microsoft YaHei,Wenquanyi Micro Hei,WenQuanYi Zen Hei,ST Heiti,SimHei,WenQuanYi Zen Hei Sharp,sans-serif;color:#ed4259;border-bottom:1px solid #ed4259;width:-moz-fit-content;width:fit-content }
.popup-wrapper .title-btn { font-size:14px;line-height:26px;color:#606266 }
.popup-wrapper .title-btn .progress-percent { display:inline-block;margin-right:25px }
.popup-wrapper .title-btn .span-btn { display:inline-block;color:#ed4259;margin-left:15px;cursor:pointer }
.popup-wrapper .title-btn .booksource-filter-input { width:200px }
.popup-wrapper .title-btn .refresh-btn { display:inline-block;margin-left:15px;color:#ed4259;cursor:pointer }
.popup-wrapper .title-btn .refresh-btn.loading { color:#606266 }
.popup-wrapper .data-wrapper { height:300px;overflow:auto }
.popup-wrapper .data-wrapper .cata { height:100% }
.popup-wrapper .data-wrapper .cata .cached { color:#444 }
.popup-wrapper .data-wrapper .cata .selected .log-text { color:#eb4259!important }
.popup-wrapper .data-wrapper .cata .log { width:100%;height:40px;cursor:pointer;font:16px/40px PingFangSC-Regular,HelveticaNeue-Light,Helvetica Neue Light,Microsoft YaHei,sans-serif }
.popup-wrapper .data-wrapper .cata .log .log-text { margin-right:26px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis }
.popup-wrapper .data-wrapper.expand { height:85vh }
.popup-wrapper .data-wrapper::-webkit-scrollbar { width:0!important }
.popup-wrapper .night .log { border-bottom:1px solid #333 }
.popup-wrapper .day .log { border-bottom:1px solid #eee }
.popup-wrapper .day .cached { color:#bbb!important }
.popup-wrapper .data-wrapper .cata .log { width:100% }
.popup-wrapper .data-wrapper .cata .log .log-text { margin-right:0 }

@media screen and (max-width:500px) {
  .popup-wrapper .data-wrapper .cata .log{width:100%}
  .popup-wrapper .data-wrapper .cata .log .log-text{margin-right:0}
}
</style>
