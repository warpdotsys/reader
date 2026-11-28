<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=2fdb5f62；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' title="书籍封面" :visible="show" :width="dialogSmallWidth" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }"><div class="custom-dialog-title" slot="title"><span class="el-dialog__title">书籍封面 <span class="float-right span-btn" :class="{loading: loadingMore}" @click="searchBookSourceByEventStream"><template v-if="loadingMore"><i class="el-icon-loading" /></template>{{ " " + _s(loadingMore ? "加载中..." : "加载更多") + " " }}</span></span></div><template v-if="show"><div class="book-cover-container"><template v-if="coverList.length"><div class="cover-list"><template v-for="(o, n) in coverList"><div :key='"cover-" + n' class="book-cover-wrapper" @click="(t) => {
                                return setCover(o)
                            }"><el-image class="book-cover" :src="o" fit="cover" lazy=""><img class="el-image__inner" style="object-fit: cover" slot="error" :src="noCover" alt="" /></el-image></div></template></div></template><template v-else><div class="empty-notice"> 暂无其它来源 </div></template></div></template></el-dialog>
</template>

<script>import axios from 'axios'
import { merge as __merge } from '@/utils/merge'
import { regeneratorRuntime } from '@/utils/regenerator'
import { asyncToGenerator } from '@/utils/async'

/** 无封面占位图（产物图片模块导出的 data URI，工程侧等价替换） */
const NO_COVER =
  'data:image/svg+xml;charset=utf-8,' +
  encodeURIComponent(
    '<svg xmlns="http://www.w3.org/2000/svg" width="120" height="160">' +
      '<rect width="120" height="160" fill="#e8e8e8"/>' +
      '<text x="60" y="84" font-size="13" fill="#aaa" text-anchor="middle">无封面</text>' +
      '</svg>'
  )

export default {

  model: {
      prop: "show",
      event: "setShow"
  },
  name: 'BookCover',
  data: function() {
      return {
          coverList: [],
          lastIndex: -1,
          loadingMore: !1,
          noCover: NO_COVER
      }
  },
  props: ["show", "book"],
  computed: __merge({}, {
      showBookInfo: {
          get: function() {
              return this.$store.state.showBookInfo
          },
          set: function(e) {
              this.$store.commit("setShowBookInfo", e)
          }
      },
      coverListMap: function() {
          return this.coverList.reduce((function(e, t) {
              return e[t] = t, e
          }), {})
      }
  }),
  watch: {
      show: function(e) {
          e && (this.coverList = [], this.lastIndex = -1, this.loadingMore = !1, this.getBookSource())
      }
  },
  methods: {
      cancel: function() {
          this.$emit("setShow", !1)
      },
      getBookCoverUrl: function(e) {
          return e.customCoverUrl || e.coverUrl
      },
      getBookSource: function(e) {
          var t = this;
          axios.post(this.api + "/getAvailableBookSource", {
              url: this.book.bookUrl,
              refresh: e ? 1 : 0
          }).then((function(e) {
              t.loading = !1, e.data.isSuccess && (t.coverList = (e.data.data || []).map((function(e) {
                  return e.coverUrl
              })))
          }), (function(e) {
              throw t.loading = !1, t.$message.error("获取书籍来源信息失败 " + (e && e.toString())), e
          }))
      },
      searchBookSourceByEventStream: function() {
          var e = this,
              t = function() {
                  try {
                      e.searchEventSource && e.searchEventSource.readyState != e.searchEventSource.CLOSED && e.searchEventSource.close(), e.searchEventSource = null
                  } catch (t) {}
              };
          if (this.loadingMore) return t(), void(this.loadingMore = !1);
          var o = {
              accessToken: this.$store.state.token,
              concurrentCount: this.$store.state.searchConfig.concurrentCount,
              url: this.book.bookUrl,
              lastIndex: this.lastIndex
          };
          this.loadingMore = !0;
          var n = ut(this.api + "/searchBookSourceSSE", o);
          t(), this.searchEventSource = new EventSource(n, {
              withCredentials: !0
          }), this.searchEventSource.addEventListener("error", (function(o) {
              e.loadingMore = !1, t();
              try {
                  if (o.data) {
                      var n = JSON.parse(o.data);
                      n && n.errorMsg && e.$message.error(n.errorMsg)
                  }
              } catch (r) {}
          }));
          var r = this.coverList.length;
          this.searchEventSource.addEventListener("end", (function(o) {
              e.loadingMore = !1, t();
              try {
                  if (o.data) {
                      var n = JSON.parse(o.data);
                      n && n.lastIndex && (e.lastIndex = n.lastIndex)
                  }
                  e.coverList.length === r && e.$message.error("没有更多啦")
              } catch (a) {}
          })), this.searchEventSource.addEventListener("message", (function(t) {
              try {
                  if (t.data) {
                      var o = JSON.parse(t.data);
                      o && o.lastIndex && (e.lastIndex = o.lastIndex), o.data && (e.coverList = [].concat(e.coverList, o.data.filter((function(t) {
                          return !e.coverListMap[t.coverUrl]
                      })).map((function(e) {
                          return e.coverUrl
                      }))))
                  }
              } catch (n) {}
          }))
      },
      setCover: function(e) {
          var t = this;
          return asyncToGenerator(regeneratorRuntime.mark((function o() {
              var n, r;
              return regeneratorRuntime.wrap((function(o) {
                  while (1) switch (o.prev = o.next) {
                      case 0:
                          return o.next = 2, t.$confirm("确认要将封面设置为所选择的图片吗？", "提示", {
                              confirmButtonText: "确定",
                              cancelButtonText: "取消",
                              type: "warning"
                          })["catch"]((function() {
                              return !1
                          }));
                      case 2:
                          if (n = o.sent, n) {
                              o.next = 5;
                              break
                          }
                          return o.abrupt("return", !1);
                      case 5:
                          r = __merge(__merge({}, t.book), {}, {
                              coverUrl: e
                          }), t.saveBook(r);
                      case 7:
                      case "end":
                          return o.stop()
                  }
              }), o)
          })))()
      },
      saveBook: function(e) {
          var t = this;
          return axios.post(this.api + "/saveBook", e).then((function(o) {
              o.data.isSuccess && (t.$message.success("操作成功"), t.showBookInfo.bookUrl === e.bookUrl && (t.showBookInfo = o.data.data), t.$store.commit("updateShelfBook", o.data.data))
          }), (function(e) {
              t.$message.error("操作失败" + (e && e.toString()))
          }))
      }
  },
  render: function() {
                var e = this,
                    t = e._self._c;
                return t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "书籍封面",
                        visible: e.show,
                        width: e.dialogSmallWidth,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("div", {
                    staticClass: "custom-dialog-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [e._v("书籍封面 "), t("span", {
                    staticClass: "float-right span-btn",
                    class: {
                        loading: e.loadingMore
                    },
                    on: {
                        click: e.searchBookSourceByEventStream
                    }
                }, [e.loadingMore ? t("i", {
                    staticClass: "el-icon-loading"
                }) : e._e(), e._v(" " + e._s(e.loadingMore ? "加载中..." : "加载更多") + " ")])])]), e.show ? t("div", {
                    staticClass: "book-cover-container"
                }, [e.coverList.length ? t("div", {
                    staticClass: "cover-list"
                }, e._l(e.coverList, (function(o, n) {
                    return t("div", {
                        key: "cover-" + n,
                        staticClass: "book-cover-wrapper",
                        on: {
                            click: function(t) {
                                return e.setCover(o)
                            }
                        }
                    }, [t("el-image", {
                        staticClass: "book-cover",
                        attrs: {
                            src: o,
                            fit: "cover",
                            lazy: ""
                        }
                    }, [t("img", {
                        staticClass: "el-image__inner",
                        staticStyle: {
                            "object-fit": "cover"
                        },
                        attrs: {
                            slot: "error",
                            src: e.noCover,
                            alt: ""
                        },
                        slot: "error"
                    })])], 1)
                })), 0) : t("div", {
                    staticClass: "empty-notice"
                }, [e._v(" 暂无其它来源 ")])]) : e._e()])
            },
  staticRenderFns: []
}
</script>

<style scoped>
.book-cover-container { max-height:calc(var(--vh, 1vh)*70 - 114px);overflow-y:auto }
.book-cover-container .book-cover-wrapper { display:inline-block;width:25%;box-sizing:border-box;padding:3px;text-align:center;vertical-align:top;margin-bottom:10px;cursor:pointer;position:relative }
.book-cover-container .empty-notice { min-height:40px;text-align:center }
</style>
