<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=e4af2df0；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog v-if="$store.getters.isNormalPage" :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' title="书源订阅管理" :visible="show" :width="dialogWidth" :top="dialogTop" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }"><div class="custom-dialog-title" slot="title"><span class="el-dialog__title">书源订阅管理 </span></div><div class="source-container table-container"><el-table :data="remoteBookSourceList" :height="dialogContentHeight" @selection-change="(t) => {
                            localSelection = t
                        }"><el-table-column type="selection" width="25" :fixed="$store.state.miniInterface" /><el-table-column property="name" min-width="150px" label="名称" :fixed="$store.state.miniInterface" /><el-table-column property="link" label="链接" min-width="150px" /><el-table-column property="lastSyncTime" :formatter="formatTableField" label="上次同步" min-width="150px" /><el-table-column label="操作" width="120px"><template v-slot:default="o"><el-button type="text" @click="(t) => {
                                        return showForm(o.row, o.$index)
                                    }">修改</el-button><el-button type="text" :disabled="loadingIndex >= 0" @click="(t) => {
                                        return sync(o.row, o.$index)
                                    }"><template v-if="loadingIndex === o.$index"><i class="el-icon-loading" /></template>{{ loadingIndex === o.$index ? "同步中" : "同步" }}</el-button></template></el-table-column></el-table></div><div class="dialog-footer" slot="footer"><div><el-button class="float-left" type="primary" size="medium" @click="deleteRemoteBookSourceSub">批量删除<template v-if="localSelection.length"><span>{{ " (" + _s(localSelection.length) + ")" }}</span></template></el-button><el-button class="float-left" type="primary" size="medium" @click="(t) => {
                            return showForm(!1)
                        }">新增</el-button></div></div></el-dialog></el-dialog>
</template>

<script>import axios from 'axios'
import { merge as __merge } from '@/utils/merge'
import { regeneratorRuntime } from '@/utils/regenerator'
import { asyncToGenerator } from '@/utils/async'

export default {

  model: {
      prop: "show",
      event: "setShow"
  },
  name: 'RemoteBookSourceSub',
  data: function() {
      return {
          localSelection: [],
          filePath: "remoteBookSourceSub.json",
          remoteBookSourceList: [],
          loadingIndex: -1
      }
  },
  computed: {},
  props: ["show"],
  watch: {
      show: function(e) {
          e && this.loadRemoteBookSourceList()
      }
  },
  methods: {
      formatTableField: function(e, t, o) {
          switch (t.property) {
              case "lastSyncTime":
                  return o ? new Date(o).format("yy-MM-dd hh:mm") : "";
              default:
                  return o
          }
      },
      cancel: function() {
          this.$emit("setShow", !1)
      },
      loadRemoteBookSourceList: function() {
          var e = this;
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              var o, n;
              return regeneratorRuntime.wrap((function(t) {
                  while (1) switch (t.prev = t.next) {
                      case 0:
                          return t.next = 2, jt(e.filePath);
                      case 2:
                          if (o = t.sent, o) try {
                              n = JSON.parse(o), Array.isArray(n) && (e.remoteBookSourceList = n)
                          } catch (r) {}
                          case 4: case "end": return t.stop()
                  }
              }), t)
          })))()
      },
      deleteRemoteBookSourceSub: function() {
          var e = this;
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              var o, n;
              return regeneratorRuntime.wrap((function(t) {
                  while (1) switch (t.prev = t.next) {
                      case 0:
                          if (e.localSelection.length) {
                              t.next = 3;
                              break
                          }
                          return e.$message.error("请选择需要删除的书源订阅"), t.abrupt("return");
                      case 3:
                          return t.next = 5, e.$confirm("确认要删除所选择的书源订阅吗?", "提示", {
                              confirmButtonText: "确定",
                              cancelButtonText: "取消",
                              type: "warning"
                          })["catch"]((function() {
                              return !1
                          }));
                      case 5:
                          if (o = t.sent, o) {
                              t.next = 8;
                              break
                          }
                          return t.abrupt("return");
                      case 8:
                          n = e.remoteBookSourceList.filter((function(t) {
                              return !e.localSelection.includes(t)
                          })), e.saveData(n);
                      case 10:
                      case "end":
                          return t.stop()
                  }
              }), t)
          })))()
      },
      sync: function(e, t) {
          var o = this;
          this.loadingIndex = t, axios.post("/saveFromRemoteSource", {
              url: e.link
          }, {
              timeout: 6e5
          }).then((function(e) {
              o.loadingIndex = -1, o.$root.$children[0].loadBookSource(!0), e.data.isSuccess && (o.remoteBookSourceList[t].lastSyncTime = (new Date).getTime(), o.saveData(o.remoteBookSourceList, !0))
          }), (function(e) {
              o.$message.error("同步失败 " + (e && e.toString()))
          }))
      },
      showForm: function(e, t) {
          var o = this;
          return asyncToGenerator(regeneratorRuntime.mark((function n() {
              var r, a, c, l;
              return regeneratorRuntime.wrap((function(n) {
                  while (1) switch (n.prev = n.next) {
                      case 0:
                          return r = !e, e = __merge({}, e || {
                              name: "",
                              link: "",
                              lastSyncTime: null
                          }), a = [{
                              name: "name",
                              label: "名称",
                              type: "input"
                          }, {
                              name: "link",
                              label: "链接",
                              type: "input"
                          }], n.next = 5, o.$msgbox({
                              title: r ? "新增订阅" : "编辑订阅",
                              message: o.renderForm(o.filePath, e, a, (function(t) {
                                  return e = t
                              })),
                              showCancelButton: !0,
                              confirmButtonText: "确定",
                              cancelButtonText: "取消"
                          })["catch"]((function(e) {
                              return "close" === e && "close"
                          }));
                      case 5:
                          if (c = n.sent, "confirm" !== c) {
                              n.next = 12;
                              break
                          }
                          l = [].concat(o.remoteBookSourceList), r ? l = l.concat([e]) : l[t] = e, o.saveData(l), n.next = 13;
                          break;
                      case 12:
                          return n.abrupt("return", !1);
                      case 13:
                      case "end":
                          return n.stop()
                  }
              }), n)
          })))()
      },
      saveData: function(e, t) {
          var o = this;
          return asyncToGenerator(regeneratorRuntime.mark((function n() {
              var r;
              return regeneratorRuntime.wrap((function(n) {
                  while (1) switch (n.prev = n.next) {
                      case 0:
                          return n.next = 2, Lt(o.filePath, JSON.stringify(e));
                      case 2:
                          r = n.sent, r && (o.localSelection = [], t || o.$message.success("操作成功"), o.loadRemoteBookSourceList());
                      case 4:
                      case "end":
                          return n.stop()
                  }
              }), n)
          })))()
      }
  },
  render: function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "书源订阅管理",
                        visible: e.show,
                        width: e.dialogWidth,
                        top: e.dialogTop,
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
                }, [e._v("书源订阅管理 ")])]), t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    attrs: {
                        data: e.remoteBookSourceList,
                        height: e.dialogContentHeight
                    },
                    on: {
                        "selection-change": function(t) {
                            e.localSelection = t
                        }
                    }
                }, [t("el-table-column", {
                    attrs: {
                        type: "selection",
                        width: "25",
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "name",
                        "min-width": "150px",
                        label: "名称",
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "link",
                        label: "链接",
                        "min-width": "150px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "lastSyncTime",
                        formatter: e.formatTableField,
                        label: "上次同步",
                        "min-width": "150px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        label: "操作",
                        width: "120px"
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
                                        return e.showForm(o.row, o.$index)
                                    }
                                }
                            }, [e._v("修改")]), t("el-button", {
                                attrs: {
                                    type: "text",
                                    disabled: e.loadingIndex >= 0
                                },
                                on: {
                                    click: function(t) {
                                        return e.sync(o.row, o.$index)
                                    }
                                }
                            }, [e.loadingIndex === o.$index ? t("i", {
                                staticClass: "el-icon-loading"
                            }) : e._e(), e._v(e._s(e.loadingIndex === o.$index ? "同步中" : "同步"))])]
                        }
                    }], null, !1, 3286982204)
                })], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("div", [t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.deleteRemoteBookSourceSub
                    }
                }, [e._v("批量删除"), e.localSelection.length ? t("span", [e._v(" (" + e._s(e.localSelection.length) + ")")]) : e._e()]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: function(t) {
                            return e.showForm(!1)
                        }
                    }
                }, [e._v("新增")])], 1)])]) : e._e()
            },
  staticRenderFns: []
}
</script>

<style scoped>
.float-left { float:left }
.dialog-footer { display:-ms-flexbox;display:flex;-ms-flex-direction:row-reverse;flex-direction:row-reverse;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-pack:justify;justify-content:space-between }
.dialog-footer .float-left { margin-right:5px;margin-bottom:5px }
</style>
