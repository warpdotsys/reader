<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=reader scopeId=abde66a6；无 sourcemap，模板为 render 推断还原 -->
<template>
<div class="popup-wrapper" :style="popupTheme"><div class="title-zone"><div class="title">{{ "来源(" + _s(showBookSourceList.length) + ")" }}</div><div :class="{'title-btn': !0, loading: loading}" v-show="!isSearching"><el-select class="booksource-group-select" size="mini" filterable="" placeholder="全部分组" v-model="bookSourceGroup"><template v-for="(e, n) in $store.getters.bookSourceGroupList"><el-option :key='"source-group-" + n' :label='e.name + " (" + e.count + ")"' :value="e.value" /></template></el-select><span class="span-btn" @click="(t) => {
                                expand = !expand
                            }">{{ " " + _s(expand ? "收起" : "展开") + " " }}</span><span :class="{loading: loading}" @click="refresh"><template v-if="loading"><i class="el-icon-loading" /></template>{{ " " + _s(loading ? "刷新中..." : "刷新") + " " }}</span><span :class="{loading: loadingMore}" @click="searchBookSourceByEventStream"><template v-if="loadingMore"><i class="el-icon-loading" /></template>{{ " " + _s(loadingMore ? "加载中..." : "加载更多") + " " }}</span><span class="span-btn" @click="(t) => {
                                isSearching = !isSearching
                            }">{{ " " + _s(isSearching ? "取消" : "搜索") + " " }}</span></div><div :class="{'title-btn': !0, loading: loadingMore}" v-show="isSearching"><el-input class="booksource-filter-input" size="mini" placeholder="搜索书源" v-model="searchKeyword" /><span class="span-btn" @click="(t) => {
                                isSearching = !isSearching
                            }">{{ " " + _s(isSearching ? "取消" : "搜索") + " " }}</span></div></div><div ref='"sourceList"' class="data-wrapper" :class="{night: $store.getters.isNight, day: !$store.getters.isNight, expand: expand}"><div class="source-list"><template v-for="(n, a) in showBookSourceList"><div :key="a" ref='"source"' class="source-item" :class="{selected: isSelected(n)}" @click="(t) => {
                                    return changeBookSource(n)
                                }"><div class="source-title"><div class="source-name">{{ " " + _s(n.originName) + " " }}</div><div class="source-time">{{ " " + _s(n.time ? "⏱ " + n.time + "ms" : "") + " " }}</div></div><div class="source-latest-chapter">{{ " " + _s(n.latestChapterTitle || "无最新章节") + " " }}</div></div></template></div></div></div>
</template>

<script>
import axios from 'axios'
import { asyncToGenerator } from '@/utils/async'
import { regeneratorRuntime } from '@/utils/regenerator'

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

/** 拼 query string（产物用的 axios buildURL，模块 30b5） */
function buildURL(url, params, serializer) {
  function encode(val) {
    return encodeURIComponent(val)
      .replace(/%3A/gi, ':')
      .replace(/%24/g, '$')
      .replace(/%2C/gi, ',')
      .replace(/%20/g, '+')
      .replace(/%5B/gi, '[')
      .replace(/%5D/gi, ']')
  }
  if (!params) return url
  var parts
  var isURLSearchParams = typeof URLSearchParams !== 'undefined' && params instanceof URLSearchParams
  var isArray = function (v) {
    return Object.prototype.toString.call(v) === '[object Array]'
  }
  var isDate = function (v) {
    return Object.prototype.toString.call(v) === '[object Date]'
  }
  var isObject = function (v) {
    return v !== null && typeof v === 'object'
  }
  if (serializer) {
    parts = serializer(params)
  } else if (isURLSearchParams) {
    parts = params.toString()
  } else {
    var arr = []
    Object.keys(params).forEach(function (key) {
      var val = params[key]
      if (val === null || typeof val === 'undefined') return
      var k = key
      if (isArray(val)) k += '[]'
      else val = [val]
      val.forEach(function (v) {
        if (isDate(v)) v = v.toISOString()
        else if (isObject(v)) v = JSON.stringify(v)
        arr.push(encode(k) + '=' + encode(v))
      })
    })
    parts = arr.join('&')
  }
  if (parts) {
    var hashmark = url.indexOf('#')
    if (hashmark !== -1) url = url.slice(0, hashmark)
    url += (url.indexOf('?') === -1 ? '?' : '&') + parts
  }
  return url
}

export default {
  name: "BookSource",
  data: function() {
      return {
          index: this.$store.getters.readingBook.index,
          bookSource: [],
          bookSourceGroup: "",
          bookSourceGroupIndexMap: {},
          loading: !1,
          expand: !1,
          loadingMore: !1,
          isSearching: !1,
          searchKeyword: ""
      }
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
      bookSourceMap: function() {
          return this.bookSource.reduce((function(e, t) {
              return e[t.bookUrl] = t, e
          }), {})
      },
      readingBook: function() {
          return this.$store.getters.readingBook || {}
      },
      showBookSourceList: function() {
          var e = this;
          return this.isSearching && this.searchKeyword ? this.bookSource.filter((function(t) {
              return (t.latestChapterTitle || "").includes(e.searchKeyword)
          })) : this.bookSource
      }
  },
  mounted: function() {},
  watch: {
      visible: function(e) {
          e && this.getBookSource()
      },
      readingBook: function(e, t) {
          e.bookUrl !== t.bookUrl && (this.bookSourceGroupIndexMap = {})
      }
  },
  methods: {
      isSelected: function(e) {
          return e.bookUrl == this.$store.getters.readingBook.bookUrl
      },
      getBookSource: function(e) {
          var t = this;
          axios.post(this.api + "/getAvailableBookSource", {
              url: this.$store.getters.readingBook.bookUrl,
              refresh: e ? 1 : 0
          }).then((function(e) {
              var n;
              (t.loading = !1, e.data.isSuccess) && (t.bookSource = e.data.data || [], t.bookSource.length && (t.bookSourceGroupIndexMap[""] = Math.max(null !== (n = t.bookSourceGroupIndexMap[""]) && void 0 !== n ? n : 0, t.bookSource.length), t.jumpToActive()))
          }), (function(e) {
              throw t.loading = !1, t.$message.error("获取书籍来源信息失败 " + (e && e.toString())), e
          }))
      },
      changeBookSource: function(e) {
          var t = this;
          return asyncToGenerator(regeneratorRuntime.mark((function n() {
              var a;
              return regeneratorRuntime.wrap((function(n) {
                  while (1) switch (n.prev = n.next) {
                      case 0:
                          return n.next = 2, t.$root.$children[0].isInShelf(t.$store.getters.readingBook, "加入书架之后才能切换书源, 是否加入书架?");
                      case 2:
                          if (a = n.sent, a) {
                              n.next = 5;
                              break
                          }
                          return n.abrupt("return");
                      case 5:
                          axios.post(t.api + "/setBookSource", {
                              bookUrl: t.$store.getters.readingBook.bookUrl,
                              newUrl: e.bookUrl,
                              bookSourceUrl: e.origin
                          }).then((function(n) {
                              if (n.data.isSuccess) {
                                  t.$message.info("换源成功");
                                  var a = Object.assign({}, t.$store.getters.readingBook);
                                  a.bookUrl = e.bookUrl, a.type = "undefined" !== typeof e.type ? e.type : a.type, a.coverUrl = "undefined" !== typeof e.coverUrl ? e.coverUrl : a.coverUrl, t.$store.commit("setReadingBook", a), t.$emit("changeBookSource"), axios.get(t.api + "/getBookshelf", {}).then((function(e) {
                                      e.data.isSuccess && t.$store.commit("setShelfBooks", e.data.data)
                                  }), (function() {}))
                              }
                          }), (function(e) {
                              throw t.$message.error("换源失败 " + (e && e.toString())), e
                          }));
                      case 6:
                      case "end":
                          return n.stop()
                  }
              }), n)
          })))()
      },
      refresh: function() {
          this.loadingMore || (this.loading = !0, this.getBookSource(!0))
      },
      loadMoreSource: function() {
          var e = this;
          this.loadingMore || (this.loadingMore = !0, axios.post(this.api + "/searchBookSource", {
              url: this.$store.getters.readingBook.bookUrl,
              bookSourceGroup: this.bookSourceGroup,
              lastIndex: this.bookSourceGroupIndexMap[this.bookSourceGroup]
          }).then((function(t) {
              if (e.loadingMore = !1, t.data.isSuccess) {
                  var n = t.data.data.list || [];
                  e.bookSource = [].concat(e.bookSource, n.filter((function(t) {
                      return !e.bookSourceMap[t.bookUrl]
                  }))), t.data.data.lastIndex && (e.bookSourceGroupIndexMap[e.bookSourceGroup] = t.data.data.lastIndex)
              }
          }), (function(t) {
              throw e.loadingMore = !1, e.$message.error("加载更多书籍来源失败 " + (t && t.toString())), t
          })))
      },
      searchBookSourceByEventStream: function() {
          var e = this,
              t = function() {
                  try {
                      e.searchEventSource && e.searchEventSource.readyState != e.searchEventSource.CLOSED && e.searchEventSource.close(), e.searchEventSource = null
                  } catch (re) {}
              };
          if (this.loadingMore) return t(), void(this.loadingMore = !1);
          var n = {
              accessToken: this.$store.state.token,
              concurrentCount: this.$store.state.searchConfig.concurrentCount,
              url: this.$store.getters.readingBook.bookUrl,
              bookSourceGroup: this.bookSourceGroup,
              lastIndex: this.bookSourceGroupIndexMap[this.bookSourceGroup]
          };
          this.loadingMore = !0;
          var a = buildURL(this.api + "/searchBookSourceSSE", n);
          t(), this.searchEventSource = new EventSource(a, {
              withCredentials: !0
          }), this.searchEventSource.addEventListener("error", (function(n) {
              e.loadingMore = !1, t();
              try {
                  if (n.data) {
                      var a = JSON.parse(n.data);
                      a && a.errorMsg && e.$message.error(a.errorMsg)
                  }
              } catch (re) {}
          }));
          var u = this.bookSource.length;
          this.searchEventSource.addEventListener("end", (function(n) {
              e.loadingMore = !1, t();
              try {
                  if (n.data) {
                      var a = JSON.parse(n.data);
                      a && a.lastIndex && (e.bookSourceGroupIndexMap[e.bookSourceGroup] = a.lastIndex)
                  }
                  e.bookSource.length === u && e.$message.error("没有更多啦")
              } catch (re) {}
          })), this.searchEventSource.addEventListener("message", (function(t) {
              try {
                  if (t.data) {
                      var n = JSON.parse(t.data);
                      n && n.lastIndex && (e.bookSourceGroupIndexMap[e.bookSourceGroup] = n.lastIndex), n.data && (e.bookSource = [].concat(e.bookSource, n.data.filter((function(t) {
                          return !e.bookSourceMap[t.bookUrl]
                      }))))
                  }
              } catch (re) {}
          }))
      },
      jumpToActive: function() {
          var e = this;
          this.$nextTick((function() {
              var t = -1;
              if (e.bookSource.some((function(n, a) {
                      if (n.bookUrl == e.$store.getters.readingBook.bookUrl) return t = a, !0
                  })), !(t < 0)) {
                  var n = e.$refs.sourceList;
                  scrollToElement(e.$refs.source[t], {
                      container: n,
                      duration: 0
                  })
              }
          }))
      }
  },
}
</script>

<style scoped>
.popup-wrapper { margin:-16px;margin-bottom:-13px;padding:24px;padding-top:calc(24px + constant(safe-area-inset-top));padding-top:calc(24px + env(safe-area-inset-top)) }
.popup-wrapper .title-zone { margin:0 0 20px 0;width:100%;display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-pack:justify;justify-content:space-between }
.popup-wrapper .title { font-size:18px;font-weight:400;font-family:-apple-system,Noto Sans,Helvetica Neue,Helvetica,Nimbus Sans L,Arial,Liberation Sans,PingFang SC,Hiragino Sans GB,Noto Sans CJK SC,Source Han Sans SC,Source Han Sans CN,Microsoft YaHei,Wenquanyi Micro Hei,WenQuanYi Zen Hei,ST Heiti,SimHei,WenQuanYi Zen Hei Sharp,sans-serif;color:#ed4259;border-bottom:1px solid #ed4259;width:-moz-fit-content;width:fit-content }
.popup-wrapper .title-btn { font-size:14px;line-height:26px;color:#ed4259;cursor:pointer }
.popup-wrapper .title-btn .booksource-group-select { width:140px }
.popup-wrapper .title-btn .booksource-filter-input { width:200px }
.popup-wrapper .title-btn .source-count { display:inline-block;color:#606266 }
.popup-wrapper .title-btn span { margin-left:15px }
.popup-wrapper .title-btn.loading { color:#606266 }
.popup-wrapper .data-wrapper { height:300px;overflow:auto }
.popup-wrapper .data-wrapper .source-list .source-item { width:100%;cursor:pointer;display:-ms-flexbox;display:flex;-ms-flex-direction:column;flex-direction:column;max-width:100%;overflow:hidden;padding:8px 0 }
.popup-wrapper .data-wrapper .source-list .source-item .source-title { display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-pack:justify;justify-content:space-between;-ms-flex-align:center;align-items:center }
.popup-wrapper .data-wrapper .source-list .source-item .source-title .source-name { font-size:16px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis }
.popup-wrapper .data-wrapper .source-list .source-item .source-title .source-time { float:right;font-size:12px }
.popup-wrapper .data-wrapper .source-list .source-item .source-latest-chapter { overflow:hidden;white-space:nowrap;text-overflow:ellipsis;color:#888;font-size:14px;margin-top:6px }
.popup-wrapper .data-wrapper .source-list .source-item.selected .source-name { color:#eb4259 }
.popup-wrapper .data-wrapper.expand { height:85vh }
.popup-wrapper .data-wrapper::-webkit-scrollbar { width:0!important }
.popup-wrapper .night .source-item { border-bottom:1px solid #333 }
.popup-wrapper .day .source-item { border-bottom:1px solid #eee }
</style>
