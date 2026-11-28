<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=6e24e744；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog v-if="$store.getters.isNormalPage" :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' :visible="show" :width="dialogWidth" :top="dialogTop" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }" @opened="opend"><div class="custom-dialog-title" slot="title"><span class="el-dialog__title"><span class="title-input"><el-input class="search-input" size="mini" placeholder="搜索书籍内容" v-model="keyword" @keyup.native='function(t) {
                            return !t.type.indexOf("key") && _k(t.keyCode, "enter", 13, t.key, "Enter") ? null : searchBookContent(-1)
                        }'><i class="el-input__icon el-icon-search" slot="prefix" /></el-input></span></span></div><div class="source-container table-container"><el-table ref='"resultTable"' :data="searchResultList" :height="dialogContentHeight" @row-click="clickRow"><el-table-column property="chapterTitle" min-width="100px" label="章节" /><el-table-column property="resultText" min-width="250px" label="搜索结果" /></el-table></div><div class="dialog-footer" slot="footer"><el-button class="float-left" type="primary" size="medium" :disabled="loading" @click="(t) => {
                            return searchBookContent(lastIndex)
                        }">{{ loading ? "加载中" : "加载更多" }}</el-button><template v-if="lastScrollTop > 0"><el-button class="float-left" type="primary" size="medium" @click="restoreScrollTop">跳转上次位置</el-button></template><el-button size="medium" @click="cancel">取消</el-button></div></el-dialog></el-dialog>
</template>

<script>import axios from 'axios'
import { merge as __merge } from '@/utils/merge'
import { regeneratorRuntime } from '@/utils/regenerator'
import { asyncToGenerator } from '@/utils/async'
import eventBus from '@/utils/event-bus'

export default {

  model: {
      prop: "show",
      event: "setShow"
  },
  name: 'SearchBookContent',
  data: function() {
      return {
          lastScrollTop: 0,
          keyword: "",
          lastIndex: 0,
          searchResultList: [],
          loading: !1
      }
  },
  computed: {},
  props: ["show", "book"],
  watch: {
      show: function(e) {},
      book: {
          deep: !0,
          handler: function(e, t) {
              e.bookUrl !== t.bookUrl && (this.keyword = "", this.lastIndex = -1, this.searchResultList = [])
          }
      }
  },
  created: function() {
      window.searchBookComp = this
  },
  methods: {
      formatTableField: function(e, t, o) {
          switch (t.property) {
              default:
                  return o
          }
      },
      opend: function() {
          var e = this;
          this.$nextTick((function() {
              e.restoreScrollTop()
          }))
      },
      restoreScrollTop: function() {
          var e = this;
          if (this.$refs.resultTable && this.$refs.resultTable.$ready) try {
              this.$refs.resultTable.bodyWrapper.scrollTop = this.lastScrollTop
          } catch (t) {
              setTimeout((function() {
                  e.restoreScrollTop()
              }), 10)
          } else this.$nextTick((function() {
              e.restoreScrollTop()
          }))
      },
      cancel: function() {
          this.$emit("setShow", !1)
      },
      searchBookContent: function(e) {
          var t = this;
          return asyncToGenerator(regeneratorRuntime.mark((function o() {
              return regeneratorRuntime.wrap((function(o) {
                  while (1) switch (o.prev = o.next) {
                      case 0:
                          if (!t.loading) {
                              o.next = 2;
                              break
                          }
                          return o.abrupt("return");
                      case 2:
                          t.loading = !0, axios.post(t.api + "/searchBookContent", {
                              url: t.book.bookUrl,
                              keyword: t.keyword,
                              lastIndex: e
                          }).then((function(o) {
                              t.loading = !1, o.data.isSuccess && (t.lastIndex = o.data.data.lastIndex, t.searchResultList = -1 === e ? o.data.data.list : [].concat(t.searchResultList).concat(o.data.data.list))
                          }), (function(e) {
                              t.loading = !1, t.$message.error("加载失败 " + (e && e.toString()))
                          }));
                      case 4:
                      case "end":
                          return o.stop()
                  }
              }), o)
          })))()
      },
      clickRow: function(e) {
          this.lastScrollTop = this.$refs.resultTable.bodyWrapper.scrollTop, eventBus.$emit("showSearchContent", e), this.cancel()
      }
  },
  render: function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        visible: e.show,
                        width: e.dialogWidth,
                        top: e.dialogTop,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        },
                        opened: e.opend
                    }
                }, [t("div", {
                    staticClass: "custom-dialog-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [t("span", {
                    staticClass: "title-input"
                }, [t("el-input", {
                    staticClass: "search-input",
                    attrs: {
                        size: "mini",
                        placeholder: "搜索书籍内容"
                    },
                    nativeOn: {
                        keyup: function(t) {
                            return !t.type.indexOf("key") && e._k(t.keyCode, "enter", 13, t.key, "Enter") ? null : e.searchBookContent(-1)
                        }
                    },
                    model: {
                        value: e.keyword,
                        callback: function(t) {
                            e.keyword = t
                        },
                        expression: "keyword"
                    }
                }, [t("i", {
                    staticClass: "el-input__icon el-icon-search",
                    attrs: {
                        slot: "prefix"
                    },
                    slot: "prefix"
                })])], 1)])]), t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    ref: "resultTable",
                    attrs: {
                        data: e.searchResultList,
                        height: e.dialogContentHeight
                    },
                    on: {
                        "row-click": e.clickRow
                    }
                }, [t("el-table-column", {
                    attrs: {
                        property: "chapterTitle",
                        "min-width": "100px",
                        label: "章节"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "resultText",
                        "min-width": "250px",
                        label: "搜索结果"
                    }
                })], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium",
                        disabled: e.loading
                    },
                    on: {
                        click: function(t) {
                            return e.searchBookContent(e.lastIndex)
                        }
                    }
                }, [e._v(e._s(e.loading ? "加载中" : "加载更多"))]), e.lastScrollTop > 0 ? t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.restoreScrollTop
                    }
                }, [e._v("跳转上次位置")]) : e._e(), t("el-button", {
                    attrs: {
                        size: "medium"
                    },
                    on: {
                        click: e.cancel
                    }
                }, [e._v("取消")])], 1)]) : e._e()
            },
  staticRenderFns: []
}
</script>

<style scoped>
.float-left { float:left }
.title-input { display:inline-block;width:75%;margin:0 auto;-ms-transform:translateX(20%);transform:translateX(20%) }
</style>
