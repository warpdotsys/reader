<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=1a462b56；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' title="书籍信息" :visible="show" :width="dialogSmallWidth" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }"><div class="custom-dialog-title" slot="title"><span class="el-dialog__title">书籍信息 <template v-if='isInShelf && "index" === $route.name'><span class="float-right span-btn" @click="editBook">编辑</span></template><template v-if="isInShelf"><span class="float-right span-btn" @click="showBookConfig">设置</span></template><template v-if='isInShelf && "index" === $route.name'><span class="float-right span-btn" @click="deleteBook">移出书架</span></template><template v-if="isInShelf"><span class="float-right span-btn" @click="triggerBookCoverRefClick">修改封面</span></template><template v-else><template v-if="!(isInShelf)"><span class="float-right span-btn" @click="(t) => {
                            return saveBook(showBookInfo, !0)
                        }">加入书架</span></template></template></span></div><template v-if="show"><div class="book-info-container"><div class="book-cover"><div class="book-cover-bg" :style="bookCoverBgStyle" /><div class="book-cover-bg-image"><img :key="showBookInfo.name" alt="" v-lazy="getCover(getBookCoverUrl(showBookInfo))" @click="triggerBookCoverRefClick" /><input ref='"bookCoverRef"' style="display: none" type="file" accept="image/jpg, image/png, image/jpeg" @change="onCoverFileChange" /></div></div><div class="book-name">{{ showBookInfo.name }}</div><BookKind class="text-center" :book="showBookInfo" /><div class="book-props"><div class="book-prop book-author">{{ " 作者： " + _s(showBookInfo.author || "未知") + " " }}</div><div class="book-prop book-origin">{{ " 来源： " + _s(displayOriginName(showBookInfo.origin)) + " " }}<template v-if='"loc_book" === showBookInfo.origin'><el-button class="book-prop-btn" type="text" @click="(t) => {
                            return refreshLocalBook(showBookInfo)
                        }">更新</el-button></template></div><div class="book-prop book-latest"><span class="latest-title">{{ "最新： " + _s(showBookInfo.latestChapterTitle) + " " }}</span><template v-if="isInShelf"><span class="book-prop-btn"> 追更 <el-switch active-color="#13ce66" inactive-color="#ff4949" :active-value="true" :inactive-value="false" v-model="showBookInfo.canUpdate" @change="(t) => {
                            return toggleBookCanUpdate(showBookInfo)
                        }" /></span></template></div><template v-if="showBookInfo.lastCheckError"><div class="book-prop last-check-error">{{ " 错误： " + _s(showBookInfo.lastCheckError) + " " }}</div></template><template v-if="isInShelf"><div class="book-prop book-group">{{ " 分组： " + _s(displayGroupName(showBookInfo.group)) + " " }}<el-button class="book-prop-btn" type="text" @click="(t) => {
                            return showSetBookGroup()
                        }">设置分组</el-button></div></template></div><div class="book-intro" /></div></template></el-dialog>
</template>

<script>import axios from 'axios'
import { merge as __merge } from '@/utils/merge'
import { regeneratorRuntime } from '@/utils/regenerator'
import { asyncToGenerator } from '@/utils/async'
import eventBus from '@/utils/event-bus'

/**
 * 64 位整数位运算替身（产物模块 f537 = goog.math.Long）。
 * 用途是书籍分组位掩码：group 每个 bit 代表一个分组。
 */
const LongBits = (function () {
  function toBig(v) {
    return typeof v === 'bigint' ? v : BigInt(Math.trunc(Number(v) || 0))
  }
  function Bits(v) {
    this.v = v
  }
  Bits.prototype.or = function (o) {
    return new Bits(this.v | toBig(o))
  }
  Bits.prototype.and = function (o) {
    return new Bits(this.v & toBig(o))
  }
  Bits.prototype.greaterThan = function (o) {
    return this.v > toBig(o)
  }
  Bits.prototype.toNumber = function () {
    return Number(this.v)
  }
  return {
    fromNumber: function (n) {
      return new Bits(BigInt.asIntN(64, toBig(n)))
    }
  }
})()

/**
 * BookKind（产物模块 0fe7，无 scopeId，Render 与产物逐行一致）
 */
const BookKind = {
  name: 'BookKind',
  props: ['book'],
  methods: {
    renderBookKind: function () {
      if (!this.book) return ''
      var e = this.book.wordCount + ',' + this.book.kind
      if (!e) return ''
      var t = e.split(',')
      return t
        .filter(function (e) {
          return e && 'undefined' !== e
        })
        .map(function (e) {
          return '<span class="small-tag">'.concat(e, '</span>')
        })
        .join('')
    }
  },
  render: function () {
    var e = this,
      t = e._self._c
    return t('div', {
      staticClass: 'book-kind',
      domProps: { innerHTML: e._s(e.renderBookKind(e.book)) }
    })
  }
}

export default {

  components: {
      BookKind: BookKind
  },
  model: {
      prop: "show",
      event: "setShow"
  },
  name: 'BookInfo',
  data: function() {
      return {}
  },
  props: ["show"],
  computed: __merge({}, {
      bookCoverBgStyle: function() {
          return {
              backgroundImage: "url(".concat(this.getCover(this.getBookCoverUrl(this.showBookInfo), !0), ")")
          }
      },
      bookSourceList: function() {
          return this.$store.state.bookSourceList
      },
      showBookInfo: {
          get: function() {
              return this.$store.state.showBookInfo
          },
          set: function(e) {
              this.$store.commit("setShowBookInfo", e)
          }
      },
      isInShelf: function() {
          var e = this;
          return this.$store.getters.shelfBooks.find((function(t) {
              return t.bookUrl === e.showBookInfo.bookUrl
          }))
      }
  }),
  watch: {
      show: function(e) {}
  },
  methods: {
      cancel: function() {
          this.$emit("setShow", !1)
      },
      displayGroupName: function(e) {
          var t = [],
              o = "";
          return this.$store.state.bookGroupList.forEach((function(n) {
              n.groupId > 0 && (LongBits.fromNumber(n.groupId).and(e).greaterThan(0)) ? t.push(n.groupName) : -4 === n.groupId && (o = n.groupName)
          })), t.join(",") || o || "未分组"
      },
      displayOriginName: function(e) {
          return "loc_book" === e ? "本地" : (this.bookSourceList.find((function(t) {
              return t.bookSourceUrl === e
          })) || {}).bookSourceName || "未知书源"
      },
      renderBookIntro: function(e) {
          var t = (e.intro || "暂无简介").split("\n");
          return t.map((function(e) {
              return "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;".concat(e.replace(/^\s+/g, ""), "</p>")
          })).join("")
      },
      getBookCoverUrl: function(e) {
          return e.customCoverUrl || e.coverUrl
      },
      triggerBookCoverRefClick: function() {
          var e = this;
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              var o;
              return regeneratorRuntime.wrap((function(t) {
                  while (1) switch (t.prev = t.next) {
                      case 0:
                          if (!e.isInShelf) {
                              t.next = 7;
                              break
                          }
                          return t.next = 3, e.$confirm("请选择操作?", "提示", {
                              confirmButtonText: "上传封面",
                              cancelButtonText: "选择其它书源",
                              type: "warning",
                              closeOnClickModal: !1,
                              closeOnPressEscape: !1,
                              distinguishCancelAndClose: !0
                          })["catch"]((function(e) {
                              return "close" === e && "close"
                          }));
                      case 3:
                          if (o = t.sent, "close" !== o) {
                              t.next = 6;
                              break
                          }
                          return t.abrupt("return");
                      case 6:
                          o ? e.$refs.bookCoverRef.dispatchEvent(new MouseEvent("click")) : eventBus.$emit("showBookCoverDialog", e.showBookInfo);
                      case 7:
                      case "end":
                          return t.stop()
                  }
              }), t)
          })))()
      },
      onCoverFileChange: function(e) {
          var t = this;
          if (e.target && e.target.files && e.target.files.length) {
              var o = e.target.files && e.target.files[0],
                  n = new FormData;
              n.append("file", o), n.append("type", "covers"), axios.post(this.api + "/uploadFile", n, {
                  headers: {
                      "Content-Type": "multipart/form-data"
                  }
              }).then((function(e) {
                  if (e.data.isSuccess) {
                      if (!e.data.data.length) return void t.$message.error("上传文件失败");
                      t.showBookInfo.customCoverUrl = e.data.data[0], t.saveBook(t.showBookInfo)
                  }
              }), (function(e) {
                  t.$message.error("上传文件失败 " + (e && e.toString()))
              }))
          }
      },
      refreshLocalBook: function(e) {
          var t = this;
          axios.post(this.api + "/refreshLocalBook", {
              bookUrl: e.bookUrl
          }).then((function(e) {
              e.data.isSuccess && (t.$message.success("更新成功"), t.showBookInfo = e.data.data, t.$store.commit("updateShelfBook", e.data.data))
          }), (function(e) {
              t.$message.error("更新失败" + (e && e.toString()))
          }))
      },
      toggleBookCanUpdate: function(e) {
          return this.saveBook(e)
      },
      saveBook: function(e, t) {
          var o = this;
          return axios.post(this.api + "/saveBook", e).then((function(e) {
              e.data.isSuccess && (o.$message.success(t ? "加入书架成功" : "操作成功"), o.showBookInfo = e.data.data, t ? o.$root.$children[0].loadBookShelf(!0) : o.$store.commit("updateShelfBook", e.data.data))
          }), (function(e) {
              o.$message.error((t ? "加入书架失败" : "操作失败") + (e && e.toString()))
          }))
      },
      deleteBook: function() {
          this.$root.$children[0].deleteBook(this.showBookInfo)
      },
      editBook: function() {
          var e = this;
          eventBus.$emit("editBook", this.showBookInfo, !1, (function(t) {
              e.showBookInfo = t
          }))
      },
      showBookConfig: function() {
          eventBus.$emit("showBookConfigDialog")
      },
      showSetBookGroup: function() {
          eventBus.$emit("showBookGroupDialog", !0)
      }
  },
  render: function() {
                var e = this,
                    t = e._self._c;
                return t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "书籍信息",
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
                }, [e._v("书籍信息 "), e.isInShelf && "index" === e.$route.name ? t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.editBook
                    }
                }, [e._v("编辑")]) : e._e(), e.isInShelf ? t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.showBookConfig
                    }
                }, [e._v("设置")]) : e._e(), e.isInShelf && "index" === e.$route.name ? t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.deleteBook
                    }
                }, [e._v("移出书架")]) : e._e(), e.isInShelf ? t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.triggerBookCoverRefClick
                    }
                }, [e._v("修改封面")]) : e.isInShelf ? e._e() : t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: function(t) {
                            return e.saveBook(e.showBookInfo, !0)
                        }
                    }
                }, [e._v("加入书架")])])]), e.show ? t("div", {
                    staticClass: "book-info-container"
                }, [t("div", {
                    staticClass: "book-cover"
                }, [t("div", {
                    staticClass: "book-cover-bg",
                    style: e.bookCoverBgStyle
                }), t("div", {
                    staticClass: "book-cover-bg-image"
                }, [t("img", {
                    directives: [{
                        name: "lazy",
                        rawName: "v-lazy",
                        value: e.getCover(e.getBookCoverUrl(e.showBookInfo)),
                        expression: "getCover(getBookCoverUrl(showBookInfo))"
                    }],
                    key: e.showBookInfo.name,
                    attrs: {
                        alt: ""
                    },
                    on: {
                        click: e.triggerBookCoverRefClick
                    }
                }), t("input", {
                    ref: "bookCoverRef",
                    staticStyle: {
                        display: "none"
                    },
                    attrs: {
                        type: "file",
                        accept: "image/jpg, image/png, image/jpeg"
                    },
                    on: {
                        change: e.onCoverFileChange
                    }
                })])]), t("div", {
                    staticClass: "book-name"
                }, [e._v(e._s(e.showBookInfo.name))]), t("BookKind", {
                    staticClass: "text-center",
                    attrs: {
                        book: e.showBookInfo
                    }
                }), t("div", {
                    staticClass: "book-props"
                }, [t("div", {
                    staticClass: "book-prop book-author"
                }, [e._v(" 作者： " + e._s(e.showBookInfo.author || "未知") + " ")]), t("div", {
                    staticClass: "book-prop book-origin"
                }, [e._v(" 来源： " + e._s(e.displayOriginName(e.showBookInfo.origin)) + " "), "loc_book" === e.showBookInfo.origin ? t("el-button", {
                    staticClass: "book-prop-btn",
                    attrs: {
                        type: "text"
                    },
                    on: {
                        click: function(t) {
                            return e.refreshLocalBook(e.showBookInfo)
                        }
                    }
                }, [e._v("更新")]) : e._e()], 1), t("div", {
                    staticClass: "book-prop book-latest"
                }, [t("span", {
                    staticClass: "latest-title"
                }, [e._v("最新： " + e._s(e.showBookInfo.latestChapterTitle) + " ")]), e.isInShelf ? t("span", {
                    staticClass: "book-prop-btn"
                }, [e._v(" 追更 "), t("el-switch", {
                    attrs: {
                        "active-color": "#13ce66",
                        "inactive-color": "#ff4949",
                        "active-value": !0,
                        "inactive-value": !1
                    },
                    on: {
                        change: function(t) {
                            return e.toggleBookCanUpdate(e.showBookInfo)
                        }
                    },
                    model: {
                        value: e.showBookInfo.canUpdate,
                        callback: function(t) {
                            e.$set(e.showBookInfo, "canUpdate", t)
                        },
                        expression: "showBookInfo.canUpdate"
                    }
                })], 1) : e._e()]), e.showBookInfo.lastCheckError ? t("div", {
                    staticClass: "book-prop last-check-error"
                }, [e._v(" 错误： " + e._s(e.showBookInfo.lastCheckError) + " ")]) : e._e(), e.isInShelf ? t("div", {
                    staticClass: "book-prop book-group"
                }, [e._v(" 分组： " + e._s(e.displayGroupName(e.showBookInfo.group)) + " "), t("el-button", {
                    staticClass: "book-prop-btn",
                    attrs: {
                        type: "text"
                    },
                    on: {
                        click: function(t) {
                            return e.showSetBookGroup()
                        }
                    }
                }, [e._v("设置分组")])], 1) : e._e()]), t("div", {
                    staticClass: "book-intro",
                    domProps: {
                        innerHTML: e._s(e.renderBookIntro(e.showBookInfo))
                    }
                })], 1) : e._e()])
            },
  staticRenderFns: []
}
</script>

<style scoped>
.book-info-container .book-cover { width:100%;position:relative;height:150px }
.book-info-container .book-cover .book-cover-bg { position:absolute;height:100%;width:100%;filter:blur(50px) }
.book-info-container .book-cover .book-cover-bg-image { position:absolute;height:100%;width:100% }
.book-info-container .book-cover .book-cover-bg-image img { margin:0 auto;display:block;height:150px }
.book-info-container .book-name { display:block;font-size:16px;font-weight:500;text-align:center;padding:10px 0 }
.book-info-container .text-center { text-align:center }
.book-info-container .book-props { padding:5px 0 }
.book-info-container .book-props .book-prop { padding:3px 0 }
.book-info-container .book-props .book-prop.book-latest { display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-pack:justify;justify-content:space-between }
.book-info-container .book-props .book-prop.book-latest .latest-title { overflow:hidden;text-overflow:ellipsis;white-space:nowrap }
.book-info-container .book-props .book-prop.book-latest .book-prop-btn { width:80px;text-align:right }
.book-info-container .book-props .book-prop.last-check-error { color:red }
.book-info-container .book-props .book-prop .book-prop-btn { float:right;height:19px;padding:0;cursor:pointer }
.book-info-container .book-props .book-prop.book-operate-zone { text-align:center }
.book-info-container .book-props .book-prop.book-operate-zone .book-operate-btn { cursor:pointer }
.book-info-container .book-intro { line-height:1.6;max-height:calc(var(--vh, 1vh)*70 - 399px);overflow-y:auto }
</style>
