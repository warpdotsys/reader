<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=53b78df8；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog v-if="$store.getters.isNormalPage" :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' title="密钥管理" :visible="show" :width="dialogWidth" :top="dialogTop" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }"><div class="custom-dialog-title" slot="title"><span class="el-dialog__title">密钥管理 </span></div><div class="source-container table-container"><el-table :data="list" :height="dialogContentHeight" @selection-change="(t) => {
                            localSelection = t
                        }"><el-table-column type="selection" width="25" :fixed="$store.state.miniInterface" /><el-table-column property="host" min-width="150px" label="Host" :fixed="$store.state.miniInterface" /><el-table-column property="code" label="Code" min-width="100px" /><el-table-column property="type" label="类型" min-width="100px" /><el-table-column property="userMaxLimit" label="用户上限" min-width="100px" /><el-table-column property="expiredAt" label="过期时间" :formatter="formatTableField" min-width="100px" /><el-table-column property="simpleWebExpiredAt" :formatter="formatTableField" label="Kindle过期" min-width="150px" /><el-table-column property="activeTime" :formatter="formatTableField" label="激活时间" min-width="150px" /><el-table-column property="activeIp" label="激活IP" min-width="100px" /><el-table-column property="lastOnlineTime" :formatter="formatTableField" label="上次在线时间" min-width="150px" /><el-table-column property="lastOnlineIp" label="上次在线IP" min-width="100px" /></el-table></div><div class="dialog-footer" slot="footer"><div><el-button class="float-left" type="primary" size="medium" @click="deleteItems">批量删除<template v-if="localSelection.length"><span>{{ " (" + _s(localSelection.length) + ")" }}</span></template></el-button><el-button class="float-left" type="primary" size="medium" @click="showForm">生成密钥</el-button></div></div></el-dialog></el-dialog>
</template>

<script>import axios from 'axios'
import { merge as __merge } from '@/utils/merge'
import { regeneratorRuntime } from '@/utils/regenerator'
import { asyncToGenerator } from '@/utils/async'
import eventBus from '@/utils/event-bus'
import { getStorage, setStorage } from '@/utils/storage'

export default {

  model: {
      prop: "show",
      event: "setShow"
  },
  name: 'ActiveLicense',
  data: function() {
      return {
          localSelection: [],
          filePath: "data/activeLicense.json",
          list: [],
          loadingIndex: -1
      }
  },
  computed: {},
  props: ["show"],
  watch: {
      show: function(e) {
          e && this.loadList()
      }
  },
  methods: {
      formatTableField: function(e, t, o) {
          switch (t.property) {
              case "expiredAt":
              case "simpleWebExpiredAt":
              case "activeTime":
              case "lastOnlineTime":
                  return o ? new Date(o).format("yy-MM-dd hh:mm") : "";
              default:
                  return o
          }
      },
      cancel: function() {
          this.$emit("setShow", !1)
      },
      loadList: function() {
          var e = this;
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              var o, n;
              return regeneratorRuntime.wrap((function(t) {
                  while (1) switch (t.prev = t.next) {
                      case 0:
                          return t.next = 2, jt(e.filePath, "__STORAGE__");
                      case 2:
                          if (o = t.sent, o) try {
                              n = JSON.parse(o), Array.isArray(n) && (e.list = n)
                          } catch (r) {}
                          case 4: case "end": return t.stop()
                  }
              }), t)
          })))()
      },
      deleteItems: function() {
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
                          return e.$message.error("请选择需要删除的记录"), t.abrupt("return");
                      case 3:
                          return t.next = 5, e.$confirm("确认要删除所选择的记录吗?", "提示", {
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
                          n = e.list.filter((function(t) {
                              return !e.localSelection.includes(t)
                          })), e.saveData(n);
                      case 10:
                      case "end":
                          return t.stop()
                  }
              }), t)
          })))()
      },
      showForm: function() {
          var e = this;
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              var o, n, r;
              return regeneratorRuntime.wrap((function(t) {
                  while (1) switch (t.prev = t.next) {
                      case 0:
                          return o = getStorage("lastGenerateLicenseConfig", {
                              host: "*",
                              userMaxLimit: 15,
                              expiredAt: 0,
                              openApi: !1,
                              simpleWebExpiredAt: 1735660799e3,
                              key: "Pvkp7tMQJpi4kWBE",
                              type: "basic",
                              code: ""
                          }), n = [{
                              name: "host",
                              label: "Host",
                              type: "input"
                          }, {
                              name: "userMaxLimit",
                              label: "用户上限",
                              type: "input"
                          }, {
                              name: "expiredAt",
                              label: "过期时间",
                              type: "input"
                          }, {
                              name: "simpleWebExpiredAt",
                              label: "Kindle有效期",
                              type: "input"
                          }, {
                              name: "type",
                              label: "类型",
                              type: "input"
                          }, {
                              name: "code",
                              label: "Code",
                              type: "input"
                          }], t.next = 4, e.$msgbox({
                              title: "生成密钥",
                              message: e.renderForm(e.filePath, o, n, (function(e) {
                                  return o = e
                              })),
                              showCancelButton: !0,
                              confirmButtonText: "确定",
                              cancelButtonText: "取消"
                          })["catch"]((function(e) {
                              return "close" === e && "close"
                          }));
                      case 4:
                          if (r = t.sent, "confirm" !== r) {
                              t.next = 16;
                              break
                          }
                          return o.userMaxLimit = parseInt(o.userMaxLimit), o.expiredAt = "" + o.expiredAt, o.expiredAt && o.expiredAt.indexOf("-") > 0 ? o.expiredAt = new Date(o.expiredAt.replace(/-/g, "/") + " 23:59:59").getTime() : o.expiredAt = parseInt(o.expiredAt), o.simpleWebExpiredAt = "" + o.simpleWebExpiredAt, o.simpleWebExpiredAt && o.simpleWebExpiredAt.indexOf("-") > 0 ? o.simpleWebExpiredAt = new Date(o.simpleWebExpiredAt.replace(/-/g, "/") + " 23:59:59").getTime() : o.simpleWebExpiredAt = parseInt(o.simpleWebExpiredAt), setStorage("lastGenerateLicenseConfig", o), t.next = 14, axios.post("/generateLicense", o).then((function(e) {
                              e.data.isSuccess && eventBus.$emit("showEditor", "生成授权", JSON.stringify(e.data.data, null, 4))
                          }), (function(t) {
                              throw e.$message.error("生成密钥失败 " + (t && t.toString())), t
                          }));
                      case 14:
                          t.next = 17;
                          break;
                      case 16:
                          return t.abrupt("return", !1);
                      case 17:
                      case "end":
                          return t.stop()
                  }
              }), t)
          })))()
      },
      saveData: function(e, t) {
          var o = this;
          return asyncToGenerator(regeneratorRuntime.mark((function n() {
              var r;
              return regeneratorRuntime.wrap((function(n) {
                  while (1) switch (n.prev = n.next) {
                      case 0:
                          return n.next = 2, Lt(o.filePath, JSON.stringify(e), "__STORAGE__");
                      case 2:
                          r = n.sent, r && (o.localSelection = [], t || o.$message.success("操作成功"), o.loadList());
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
                        title: "密钥管理",
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
                }, [e._v("密钥管理 ")])]), t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    attrs: {
                        data: e.list,
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
                        property: "host",
                        "min-width": "150px",
                        label: "Host",
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "code",
                        label: "Code",
                        "min-width": "100px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "type",
                        label: "类型",
                        "min-width": "100px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "userMaxLimit",
                        label: "用户上限",
                        "min-width": "100px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "expiredAt",
                        label: "过期时间",
                        formatter: e.formatTableField,
                        "min-width": "100px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "simpleWebExpiredAt",
                        formatter: e.formatTableField,
                        label: "Kindle过期",
                        "min-width": "150px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "activeTime",
                        formatter: e.formatTableField,
                        label: "激活时间",
                        "min-width": "150px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "activeIp",
                        label: "激活IP",
                        "min-width": "100px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "lastOnlineTime",
                        formatter: e.formatTableField,
                        label: "上次在线时间",
                        "min-width": "150px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "lastOnlineIp",
                        label: "上次在线IP",
                        "min-width": "100px"
                    }
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
                        click: e.deleteItems
                    }
                }, [e._v("批量删除"), e.localSelection.length ? t("span", [e._v(" (" + e._s(e.localSelection.length) + ")")]) : e._e()]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.showForm
                    }
                }, [e._v("生成密钥")])], 1)])]) : e._e()
            },
  staticRenderFns: []
}
</script>

<style scoped>
.float-left { float:left }
.dialog-footer { display:-ms-flexbox;display:flex;-ms-flex-direction:row-reverse;flex-direction:row-reverse;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-pack:justify;justify-content:space-between }
.dialog-footer .float-left { margin-right:5px;margin-bottom:5px }
</style>
