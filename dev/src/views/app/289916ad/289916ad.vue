<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=289916ad；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog v-if="$store.getters.isNormalPage" :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' :title='isShowBookGroupSettingDialog ? "设置分组" : "分组管理"' :visible="show" :width="dialogWidth" :top="dialogTop" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }" @opened="opened"><div class="source-container table-container"><el-table :key="isShowBookGroupSettingDialog" ref='"bookGroupTableRef"' :data="showBookGroupList" :height="dialogContentHeight" @selection-change="(t) => {
                            bookGroupSelection = t
                        }"><template v-if="isShowBookGroupSettingDialog"><el-table-column type="selection" width="25" /></template><el-table-column property="groupName" label="分组名" min-width="100"><template v-slot:default="o"><div class="drag-icon"><i class="el-icon-rank" /></div><span>{{ " " + _s(displayBookGroupName(o.row)) }}</span></template></el-table-column><template v-if="!(isShowBookGroupSettingDialog)"><el-table-column property="show" label="显示" min-width="80"><template v-slot:default="o"><el-switch active-color="#13ce66" inactive-color="#ff4949" :active-value="true" :inactive-value="false" v-model="o.row.show" @change="(t) => {
                                        return toggleBookGroupShow(o.row, t)
                                    }" /></template></el-table-column></template><el-table-column label="操作" width="100px"><template v-slot:default="o"><el-button type="text" @click="(t) => {
                                        return saveBookGroup(o.row)
                                    }">编辑</el-button><template v-if="!isShowBookGroupSettingDialog && o.row.groupId > 0 && !getShowShelfBooks(o.row.groupId).length"><el-button style="color: #f56c6c" type="text" @click="(t) => {
                                        return deleteBookGroup(o.row)
                                    }">删除</el-button></template></template></el-table-column></el-table></div><div class="dialog-footer" slot="footer"><el-button class="float-left" type="primary" size="medium" @click="(t) => {
                            return saveBookGroup()
                        }">添加分组</el-button><template v-if="isShowSaveOrderButton"><el-button class="float-left" type="primary" size="medium" @click="(t) => {
                            return saveOrder()
                        }">保存排序</el-button></template><template v-if="isShowBookGroupSettingDialog"><el-button type="primary" size="medium" @click="setBookGroup">确认</el-button></template><el-button size="medium" @click="cancel">取消</el-button></div></el-dialog></el-dialog>
</template>

<script>import axios from 'axios'
import { merge as __merge } from '@/utils/merge'
import { regeneratorRuntime } from '@/utils/regenerator'
import { asyncToGenerator } from '@/utils/async'

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
 * 拖动排序替身（产物用模块 aa47 = Sortable 1.15.6）。
 * 工程未引入 sortablejs，这里退化为安全空实现：不接管拖拽，
 * 但保留 create() / option() 两个被调用的接口，避免运行时报错。
 */
const SortableShim = {
  create: function (el, options) {
    var inst = {
      el: el,
      option: function () {},
      destroy: function () {}
    }
    if (window.Sortable && typeof window.Sortable.create === 'function') {
      try {
        return window.Sortable.create(el, options)
      } catch (e) {
        return inst
      }
    }
    return inst
  }
}

export default {

  model: {
      prop: "show",
      event: "setShow"
  },
  name: 'BookGroup',
  data: function() {
      return {
          isShowBookGroupSettingDialog: !1,
          showBookGroupList: [],
          sortedBookGroupList: [],
          refreshCounter: 0,
          bookGroupSelection: []
      }
  },
  props: ["show", "isSet"],
  computed: __merge({}, {
      bookGroupList: function() {
          return this.$store.state.bookGroupList
      },
      shelfBooks: function() {
          return this.$store.getters.shelfBooks
      },
      showBookInfo: {
          get: function() {
              return this.$store.state.showBookInfo
          },
          set: function(e) {
              this.$store.commit("setShowBookInfo", e)
          }
      },
      isShowSaveOrderButton: function() {
          return this.showBookGroupList.reduce((function(e, t) {
              return e + "" + t.groupId
          }), "") !== this.sortedBookGroupList.reduce((function(e, t) {
              return e + "" + t.groupId
          }), "")
      }
  }),
  watch: {
      show: function(e) {
          e && (this.isShowBookGroupSettingDialog = this.isSet, this.bookGroupSelection = [], this.computeBookGroupList())
      },
      isSet: function(e) {
          this.dragableElement && this.dragableElement.option("disabled", e)
      },
      bookGroupList: function() {
          this.computeBookGroupList()
      }
  },
  methods: {
      cancel: function() {
          this.isShowBookGroupSettingDialog = !1, this.$emit("setShow", !1)
      },
      computeBookGroupList: function() {
          var e = this;
          this.showBookGroupList = [], this.$nextTick((function() {
              e.showBookGroupList = e.isSet ? e.$store.state.bookGroupList.filter((function(e) {
                  return e.groupId > 0
              })) : e.$store.state.bookGroupList, e.sortedBookGroupList = e.showBookGroupList
          }))
      },
      opened: function() {
          var e = this;
          if (this.$refs.bookGroupTableRef && this.$refs.bookGroupTableRef.doLayout(), this.setDragable(), this.isSet) {
              var t = this.getBookGroupListForBook(this.showBookInfo.group);
              this.$nextTick((function() {
                  e.$refs.bookGroupTableRef && e.$refs.bookGroupTableRef.clearSelection(), t.forEach((function(t) {
                      e.$refs.bookGroupTableRef && e.$refs.bookGroupTableRef.toggleRowSelection(t, !0)
                  }))
              }))
          }
      },
      loadBookGroup: function(e) {
          return this.$root.$children[0].loadBookGroup(e)
      },
      getShowShelfBooks: function(e) {
          if (-1 === e) return this.shelfBooks;
          if (-2 === e) return this.shelfBooks.filter((function(e) {
              return "loc_book" === e.origin
          }));
          if (-3 === e) return this.shelfBooks.filter((function(e) {
              return 1 === e.type
          }));
          if (-4 === e) return this.shelfBooks.filter((function(e) {
              return 0 === e.group
          }));
          try {
              return this.shelfBooks.filter((function(t) {
                  return 0 === e || (LongBits.fromNumber(t.group).and(e).greaterThan(0))
              }))
          } catch (t) {}
      },
      toggleBookGroupShow: function(e, t) {
          var o = this;
          axios.post(this.api + "/saveBookGroup", __merge(__merge({}, e), {}, {
              show: t
          })).then((function(e) {
              e.data.isSuccess && (o.$message.success("修改成功"), o.loadBookGroup(!0))
          }), (function(e) {
              o.$message.error("修改失败 " + (e && e.toString()))
          }))
      },
      deleteBookGroup: function(e) {
          var t = this;
          return asyncToGenerator(regeneratorRuntime.mark((function o() {
              var n;
              return regeneratorRuntime.wrap((function(o) {
                  while (1) switch (o.prev = o.next) {
                      case 0:
                          return o.next = 2, t.$confirm("确认要删除该分组吗?", "提示", {
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
                          return o.abrupt("return");
                      case 5:
                          axios.post(t.api + "/deleteBookGroup", {
                              groupId: e.groupId
                          }).then((function(e) {
                              e.data.isSuccess && (t.$message.success("删除分组成功"), t.loadBookGroup(!0))
                          }), (function(e) {
                              t.$message.error("删除分组失败 " + (e && e.toString()))
                          }));
                      case 6:
                      case "end":
                          return o.stop()
                  }
              }), o)
          })))()
      },
      saveBookGroup: function(e) {
          var t = this;
          return asyncToGenerator(regeneratorRuntime.mark((function o() {
              var n;
              return regeneratorRuntime.wrap((function(o) {
                  while (1) switch (o.prev = o.next) {
                      case 0:
                          return o.next = 2, t.$prompt("", "".concat(e ? "编辑分组" : "添加分组"), {
                              inputValue: e ? e.groupName : "",
                              confirmButtonText: "确定",
                              cancelButtonText: "取消",
                              inputValidator: function(e) {
                                  return !!e || "分组名不能为空"
                              }
                          })["catch"]((function() {
                              return !1
                          }));
                      case 2:
                          if (n = o.sent, n) {
                              o.next = 5;
                              break
                          }
                          return o.abrupt("return");
                      case 5:
                          axios.post(t.api + "/saveBookGroup", __merge(__merge({}, e), {}, {
                              groupName: n.value
                          })).then((function(o) {
                              o.data.isSuccess && (t.$message.success(e ? "修改成功" : "添加成功"), t.loadBookGroup(!0))
                          }), (function(o) {
                              t.$message.error((e ? "修改失败" : "添加失败") + (o && o.toString()))
                          }));
                      case 6:
                      case "end":
                          return o.stop()
                  }
              }), o)
          })))()
      },
      displayBookGroupName: function(e) {
          return e.groupName + (e.groupId < 0 ? "(" + this.$store.getters.builtInBookGroupMap[e.groupId] + ")" : "")
      },
      setBookGroup: function() {
          var e = this;
          this.bookGroupSelection.length ? axios.post(this.api + "/saveBookGroupId", {
              bookUrl: this.showBookInfo.bookUrl,
              groupId: this.bookGroupSelection.reduce((function(e, t) {
                  return LongBits.fromNumber(e).or(t.groupId).toNumber()
              }), 0)
          }).then((function(t) {
              t.data.isSuccess && (e.$message.success("设置成功"), e.cancel(), e.showBookInfo = t.data.data, e.$store.commit("updateShelfBook", t.data.data))
          }), (function(t) {
              e.$message.error("设置失败" + (t && t.toString()))
          })) : this.$message.error("请选择书籍分组")
      },
      getBookGroupListForBook: function(e) {
          var t = [];
          return this.$store.state.bookGroupList.forEach((function(o) {
              o.groupId > 0 && (LongBits.fromNumber(e).and(o.groupId).greaterThan(0)) && t.push(o)
          })), t
      },
      setDragable: function() {
          var e = this,
              t = this.$el.querySelectorAll(".el-table__body-wrapper > table > tbody")[0];
          this.dragableElement = SortableShim.create(t, {
              handle: ".drag-icon",
              setData: function(e) {
                  e.setData("Text", "")
              },
              onEnd: function(t) {
                  if (t.oldIndex !== t.newIndex) {
                      var o = [].concat(e.showBookGroupList),
                          n = o.splice(t.oldIndex, 1)[0];
                      o.splice(t.newIndex, 0, n), e.sortedBookGroupList = o
                  }
              }
          }), window.bookGroupComp = this, this.dragableElement.option("disabled", this.isSet)
      },
      saveOrder: function() {
          var e = this;
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              return regeneratorRuntime.wrap((function(t) {
                  while (1) switch (t.prev = t.next) {
                      case 0:
                          axios.post(e.api + "/saveBookGroupOrder", {
                              order: e.sortedBookGroupList.map((function(e, t) {
                                  return {
                                      groupId: e.groupId,
                                      order: t
                                  }
                              }))
                          }).then((function(t) {
                              t.data.isSuccess && (e.$message.success("保存成功"), e.loadBookGroup(!0))
                          }), (function(t) {
                              e.$message.error("保存失败" + (t && t.toString()))
                          }));
                      case 1:
                      case "end":
                          return t.stop()
                  }
              }), t)
          })))()
      }
  },
  render: function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: e.isShowBookGroupSettingDialog ? "设置分组" : "分组管理",
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
                        opened: e.opened
                    }
                }, [t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    key: e.isShowBookGroupSettingDialog,
                    ref: "bookGroupTableRef",
                    attrs: {
                        data: e.showBookGroupList,
                        height: e.dialogContentHeight
                    },
                    on: {
                        "selection-change": function(t) {
                            e.bookGroupSelection = t
                        }
                    }
                }, [e.isShowBookGroupSettingDialog ? t("el-table-column", {
                    attrs: {
                        type: "selection",
                        width: "25"
                    }
                }) : e._e(), t("el-table-column", {
                    attrs: {
                        property: "groupName",
                        label: "分组名",
                        "min-width": "100"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("div", {
                                staticClass: "drag-icon"
                            }, [t("i", {
                                staticClass: "el-icon-rank"
                            })]), t("span", [e._v(" " + e._s(e.displayBookGroupName(o.row)))])]
                        }
                    }], null, !1, 4111896031)
                }), e.isShowBookGroupSettingDialog ? e._e() : t("el-table-column", {
                    attrs: {
                        property: "show",
                        label: "显示",
                        "min-width": "80"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("el-switch", {
                                attrs: {
                                    "active-color": "#13ce66",
                                    "inactive-color": "#ff4949",
                                    "active-value": !0,
                                    "inactive-value": !1
                                },
                                on: {
                                    change: function(t) {
                                        return e.toggleBookGroupShow(o.row, t)
                                    }
                                },
                                model: {
                                    value: o.row.show,
                                    callback: function(t) {
                                        e.$set(o.row, "show", t)
                                    },
                                    expression: "scope.row.show"
                                }
                            })]
                        }
                    }], null, !1, 3235955515)
                }), t("el-table-column", {
                    attrs: {
                        label: "操作",
                        width: "100px"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("el-button", {
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.saveBookGroup(o.row)
                                    }
                                }
                            }, [e._v("编辑")]), !e.isShowBookGroupSettingDialog && o.row.groupId > 0 && !e.getShowShelfBooks(o.row.groupId).length ? t("el-button", {
                                staticStyle: {
                                    color: "#f56c6c"
                                },
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.deleteBookGroup(o.row)
                                    }
                                }
                            }, [e._v("删除")]) : e._e()]
                        }
                    }], null, !1, 1414828911)
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
                        size: "medium"
                    },
                    on: {
                        click: function(t) {
                            return e.saveBookGroup()
                        }
                    }
                }, [e._v("添加分组")]), e.isShowSaveOrderButton ? t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: function(t) {
                            return e.saveOrder()
                        }
                    }
                }, [e._v("保存排序")]) : e._e(), e.isShowBookGroupSettingDialog ? t("el-button", {
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.setBookGroup
                    }
                }, [e._v("确认")]) : e._e(), t("el-button", {
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
.qrcode-img { display:block;margin:0 auto }
.drag-icon { cursor:move;display:inline-block;margin-right:5px;-webkit-user-select:none;-ms-user-select:none;user-select:none }
</style>
