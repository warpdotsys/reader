<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=33306aac；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog v-if="$store.getters.isNormalPage" :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' title="用户管理" :visible="show" :width="dialogWidth" :top="dialogTop" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }"><div class="custom-dialog-title flex-title" slot="title"><span class="el-dialog__title">用户管理 </span><span class="title-center"><el-input class="search-input" size="mini" placeholder="输入关键字搜索" v-model="search" /></span></div><div class="source-container table-container"><el-table :data="showList" :height="dialogContentHeight - 42" @selection-change="(t) => {
                            manageUserSelection = t
                        }" @sort-change="sortChange"><el-table-column type="selection" width="25" :selectable="isUserSelectable" :fixed="$store.state.miniInterface" /><el-table-column property="username" label="用户名" min-width="100" sortable="custom" :fixed="$store.state.miniInterface" /><el-table-column property="lastLoginAt" label="上次登录" sortable="custom" :formatter="formatTableField" min-width="120" /><el-table-column property="createdAt" label="注册时间" sortable="custom" :formatter="formatTableField" min-width="120" /><el-table-column property="enableWebdav" label="WebDAV" min-width="80" :filters='[{
                            text: "开启",
                            value: !0
                        }, {
                            text: "关闭",
                            value: !1
                        }]' :filter-method="filterHandler"><template v-slot:default="o"><template v-if='"default" !== o.row.userNS'><el-switch active-color="#13ce66" inactive-color="#ff4949" :active-value="true" :inactive-value="false" v-model="o.row.enableWebdav" @change="(t) => {
                                        return toggleUserWebdav(o.row, t)
                                    }" /></template></template></el-table-column><el-table-column property="enableLocalStore" label="书仓" min-width="80" :filters='[{
                            text: "开启",
                            value: !0
                        }, {
                            text: "关闭",
                            value: !1
                        }]' :filter-method="filterHandler"><template v-slot:default="o"><template v-if='"default" !== o.row.userNS'><el-switch active-color="#13ce66" inactive-color="#ff4949" :active-value="true" :inactive-value="false" v-model="o.row.enableLocalStore" @change="(t) => {
                                        return toggleUserLocalStore(o.row, t)
                                    }" /></template></template></el-table-column><el-table-column label="操作" width="100px"><template v-slot:default="o"><template v-if='"default" != o.row.userNS'><el-button class="text-button" type="text" @click="(t) => {
                                        return editUser(o.row)
                                    }">修改</el-button></template><template v-if='"default" != o.row.userNS'><el-button class="text-button" type="text" @click="(t) => {
                                        return resetPassword(o.row)
                                    }">重置密码</el-button></template><template v-if='"default" != o.row.userNS'><el-button class="text-button" type="text" @click="(t) => {
                                        return setAsDefaultBookSources(o.row)
                                    }">设为默认书源</el-button></template></template></el-table-column></el-table></div><div class="dialog-footer" slot="footer"><div><el-button class="float-left" type="primary" size="medium" @click="deleteUserList">批量删除<template v-if="manageUserSelection.length"><span>{{ " (" + _s(manageUserSelection.length) + ")" }}</span></template></el-button><el-button class="float-left" type="primary" size="medium" @click="deleteUserBookSource">使用默认书源</el-button><el-button class="float-left" type="primary" size="medium" @click="(t) => {
                            return clearInactiveUser()
                        }">清理</el-button><el-button class="float-left" type="primary" size="medium" @click="(t) => {
                            return showAddUserDialog()
                        }">新增</el-button></div><div class="source-pagination"><el-pagination :current-page="pagination.page" :page-sizes="[25, 50, 100, 200, 300, 400, filterList.length]" :page-size="pagination.size" layout="total, sizes, prev, pager, next" :total="filterList.length" :pager-count="$store.state.miniInterface ? 5 : 7" @update:currentPage='(t) => {
                            return $set(pagination, "page", t)
                        }' @update:current-page='(t) => {
                            return $set(pagination, "page", t)
                        }' @update:pageSize='(t) => {
                            return $set(pagination, "size", t)
                        }' @update:page-size='(t) => {
                            return $set(pagination, "size", t)
                        }' /></div></div></el-dialog></el-dialog>
</template>

<script>import axios from 'axios'
import { merge as __merge } from '@/utils/merge'
import { regeneratorRuntime } from '@/utils/regenerator'
import { asyncToGenerator } from '@/utils/async'
import eventBus from '@/utils/event-bus'

/** 字节数格式化（产物模块 7513 的导出 d） */
function formatBytes(bytes, digits) {
  if (bytes == null || bytes === '') return '0 Bytes'
  var units = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB']
  var n = Math.floor(Math.log(bytes) / Math.log(1024))
  var r = bytes / Math.pow(1024, n)
  r = r.toFixed(digits || 2)
  return r + ' ' + units[n]
}

export default {

  model: {
      prop: "show",
      event: "setShow"
  },
  name: 'UserManage',
  data: function() {
      return {
          manageUserSelection: [],
          search: "",
          pagination: {
              page: 1,
              size: 25
          },
          sortable: {
              prop: "",
              order: null
          }
      }
  },
  props: ["show"],
  computed: __merge({}, {
      userList: {
          get: function() {
              return this.$store.state.userList
          },
          set: function(e) {
              this.$store.commit("setUserList", e)
          }
      },
      filterList: function() {
          var e = this;
          return this.userList.filter((function(t) {
              return "default" !== t.userNS && (!e.search || t.username.toLowerCase().includes(e.search.toLowerCase()))
          }))
      },
      sortList: function() {
          var e = this;
          if (!this.sortable.prop || !this.sortable.order) return this.filterList;
          var t = [].concat(this.filterList);
          return t.sort((function(t, o) {
              if ("ascending" !== e.sortable.order) {
                  var n = t;
                  t = o, o = n
              }
              return t[e.sortable.prop] > o[e.sortable.prop] ? 1 : t[e.sortable.prop] < o[e.sortable.prop] ? -1 : 0
          })), t
      },
      showList: function() {
          var e = (this.pagination.page - 1) * this.pagination.size;
          return e > this.sortList.length ? [] : this.sortList.slice(e, Math.min(e + this.pagination.size, this.sortList.length))
      }
  }),
  watch: {
      show: function(e) {
          e && (this.manageUserSelection = [])
      }
  },
  methods: {
      cancel: function() {
          this.$emit("setShow", !1)
      },
      sortChange: function(e) {
          var t = e.prop,
              o = e.order;
          this.sortable = {
              prop: t,
              order: o
          }
      },
      showAddUserDialog: function() {
          eventBus.$emit("showUserFormDialog")
      },
      clearInactiveUser: function() {
          var e = this;
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              var o;
              return regeneratorRuntime.wrap((function(t) {
                  while (1) switch (t.prev = t.next) {
                      case 0:
                          return t.next = 2, e.$prompt("请输入需要清理的未登录天数", "清理不活跃用户", {
                              inputValue: 31,
                              confirmButtonText: "确定",
                              cancelButtonText: "取消",
                              inputValidator: function(e) {
                                  return e ? !isNaN(parseInt(e)) || "天数必须是数字" : "天数不能为空"
                              }
                          })["catch"]((function() {
                              return !1
                          }));
                      case 2:
                          if (o = t.sent, o && o.value) {
                              t.next = 5;
                              break
                          }
                          return t.abrupt("return");
                      case 5:
                          axios.post(e.api + "/clearInactiveUsers", {
                              inactiveDay: parseInt(o.value)
                          }, {
                              timeout: 0
                          }).then((function(t) {
                              t.data.isSuccess && (e.$message.success("清理不活跃用户成功"), e.userList = t.data.data.map((function(e) {
                                  return __merge(__merge({}, e), {}, {
                                      userNS: e.username
                                  })
                              })))
                          }), (function(t) {
                              e.$message.error("清理不活跃用户失败 " + (t && t.toString()))
                          }));
                      case 6:
                      case "end":
                          return t.stop()
                  }
              }), t)
          })))()
      },
      formatTableField: function(e, t, o) {
          switch (t.property) {
              case "createdAt":
              case "lastLoginAt":
              case "lastModified":
                  return o ? new Date(o).format("yy-MM-dd hh:mm") : "";
              case "size":
                  return e.isDirectory ? "" : formatBytes(o);
              default:
                  return o
          }
      },
      isUserSelectable: function(e) {
          return "default" !== e.userNS
      },
      deleteUserList: function() {
          var e = this;
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              var o;
              return regeneratorRuntime.wrap((function(t) {
                  while (1) switch (t.prev = t.next) {
                      case 0:
                          if (e.manageUserSelection.length) {
                              t.next = 3;
                              break
                          }
                          return e.$message.error("请选择需要删除的用户"), t.abrupt("return");
                      case 3:
                          return t.next = 5, e.$confirm("确认要删除所选择的用户吗?", "提示", {
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
                          axios.post(e.api + "/deleteUsers", e.manageUserSelection.map((function(e) {
                              return e.username
                          })), {
                              timeout: 0
                          }).then((function(t) {
                              t.data.isSuccess && (e.manageUserSelection = [], e.$message.success("删除用户成功"), e.userList = t.data.data.map((function(e) {
                                  return __merge(__merge({}, e), {}, {
                                      userNS: e.username
                                  })
                              })))
                          }), (function(t) {
                              e.$message.error("删除用户失败 " + (t && t.toString()))
                          }));
                      case 9:
                      case "end":
                          return t.stop()
                  }
              }), t)
          })))()
      },
      deleteUserBookSource: function() {
          var e = this;
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              var o;
              return regeneratorRuntime.wrap((function(t) {
                  while (1) switch (t.prev = t.next) {
                      case 0:
                          if (e.manageUserSelection.length) {
                              t.next = 3;
                              break
                          }
                          return e.$message.error("请选择需要删除书源的用户"), t.abrupt("return");
                      case 3:
                          return t.next = 5, e.$confirm("确认要删除所选择的用户书源吗?", "提示", {
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
                          axios.post(e.api + "/deleteUserBookSource", e.manageUserSelection.map((function(e) {
                              return e.username
                          }))).then((function(t) {
                              t.data.isSuccess && (e.manageUserSelection = [], e.$message.success("操作成功"))
                          }), (function(t) {
                              e.$message.error("操作失败 " + (t && t.toString()))
                          }));
                      case 9:
                      case "end":
                          return t.stop()
                  }
              }), t)
          })))()
      },
      toggleUserWebdav: function(e, t) {
          var o = this;
          axios.post(this.api + "/updateUser", {
              username: e.username,
              enableWebdav: t
          }).then((function(e) {
              e.data.isSuccess && (o.$message.success("修改成功"), o.userList = e.data.data.map((function(e) {
                  return __merge(__merge({}, e), {}, {
                      userNS: e.username
                  })
              })))
          }), (function(e) {
              o.$message.error("修改失败 " + (e && e.toString()))
          }))
      },
      toggleUserLocalStore: function(e, t) {
          var o = this;
          axios.post(this.api + "/updateUser", {
              username: e.username,
              enableLocalStore: t
          }).then((function(e) {
              e.data.isSuccess && (o.$message.success("修改成功"), o.userList = e.data.data.map((function(e) {
                  return __merge(__merge({}, e), {}, {
                      userNS: e.username
                  })
              })))
          }), (function(e) {
              o.$message.error("修改失败 " + (e && e.toString()))
          }))
      },
      editUser: function(e) {
          eventBus.$emit("showUserFormDialog", e)
      },
      setAsDefaultBookSources: function(e) {
          var t = this;
          return asyncToGenerator(regeneratorRuntime.mark((function o() {
              var n;
              return regeneratorRuntime.wrap((function(o) {
                  while (1) switch (o.prev = o.next) {
                      case 0:
                          return o.next = 2, t.$confirm("确认要将用户".concat(e.username, "的书源设为默认书源（新用户有效）吗?"), "提示", {
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
                          return o.abrupt("return", axios.post(t.api + "/setAsDefaultBookSources", {
                              username: e.username
                          }).then((function(e) {
                              e.data.isSuccess && t.$message.success("设置成功")
                          }), (function(e) {
                              t.$message.error("设置失败 " + (e && e.toString()))
                          })));
                      case 6:
                      case "end":
                          return o.stop()
                  }
              }), o)
          })))()
      },
      resetPassword: function(e) {
          var t = this;
          return asyncToGenerator(regeneratorRuntime.mark((function o() {
              var n;
              return regeneratorRuntime.wrap((function(o) {
                  while (1) switch (o.prev = o.next) {
                      case 0:
                          return o.next = 2, t.$prompt("", "重置密码", {
                              inputValue: "",
                              confirmButtonText: "确定",
                              cancelButtonText: "取消",
                              inputValidator: function(e) {
                                  return !!e || "密码不能为空"
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
                          axios.post(t.api + "/resetPassword", {
                              username: e.username,
                              password: n.value
                          }).then((function(e) {
                              e.data.isSuccess && t.$message.success("重置密码成功")
                          }), (function(e) {
                              t.$message.error("重置密码失败 " + (e && e.toString()))
                          }));
                      case 6:
                      case "end":
                          return o.stop()
                  }
              }), o)
          })))()
      },
      filterHandler: function(e, t, o) {
          var n = o["property"];
          return t[n] === e
      }
  },
  render: function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "用户管理",
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
                    staticClass: "custom-dialog-title flex-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [e._v("用户管理 ")]), t("span", {
                    staticClass: "title-center"
                }, [t("el-input", {
                    staticClass: "search-input",
                    attrs: {
                        size: "mini",
                        placeholder: "输入关键字搜索"
                    },
                    model: {
                        value: e.search,
                        callback: function(t) {
                            e.search = t
                        },
                        expression: "search"
                    }
                })], 1)]), t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    attrs: {
                        data: e.showList,
                        height: e.dialogContentHeight - 42
                    },
                    on: {
                        "selection-change": function(t) {
                            e.manageUserSelection = t
                        },
                        "sort-change": e.sortChange
                    }
                }, [t("el-table-column", {
                    attrs: {
                        type: "selection",
                        width: "25",
                        selectable: e.isUserSelectable,
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "username",
                        label: "用户名",
                        "min-width": "100",
                        sortable: "custom",
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "lastLoginAt",
                        label: "上次登录",
                        sortable: "custom",
                        formatter: e.formatTableField,
                        "min-width": "120"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "createdAt",
                        label: "注册时间",
                        sortable: "custom",
                        formatter: e.formatTableField,
                        "min-width": "120"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "enableWebdav",
                        label: "WebDAV",
                        "min-width": "80",
                        filters: [{
                            text: "开启",
                            value: !0
                        }, {
                            text: "关闭",
                            value: !1
                        }],
                        "filter-method": e.filterHandler
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return ["default" !== o.row.userNS ? t("el-switch", {
                                attrs: {
                                    "active-color": "#13ce66",
                                    "inactive-color": "#ff4949",
                                    "active-value": !0,
                                    "inactive-value": !1
                                },
                                on: {
                                    change: function(t) {
                                        return e.toggleUserWebdav(o.row, t)
                                    }
                                },
                                model: {
                                    value: o.row.enableWebdav,
                                    callback: function(t) {
                                        e.$set(o.row, "enableWebdav", t)
                                    },
                                    expression: "scope.row.enableWebdav"
                                }
                            }) : e._e()]
                        }
                    }], null, !1, 2358517604)
                }), t("el-table-column", {
                    attrs: {
                        property: "enableLocalStore",
                        label: "书仓",
                        "min-width": "80",
                        filters: [{
                            text: "开启",
                            value: !0
                        }, {
                            text: "关闭",
                            value: !1
                        }],
                        "filter-method": e.filterHandler
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return ["default" !== o.row.userNS ? t("el-switch", {
                                attrs: {
                                    "active-color": "#13ce66",
                                    "inactive-color": "#ff4949",
                                    "active-value": !0,
                                    "inactive-value": !1
                                },
                                on: {
                                    change: function(t) {
                                        return e.toggleUserLocalStore(o.row, t)
                                    }
                                },
                                model: {
                                    value: o.row.enableLocalStore,
                                    callback: function(t) {
                                        e.$set(o.row, "enableLocalStore", t)
                                    },
                                    expression: "scope.row.enableLocalStore"
                                }
                            }) : e._e()]
                        }
                    }], null, !1, 4075233220)
                }), t("el-table-column", {
                    attrs: {
                        label: "操作",
                        width: "100px"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return ["default" != o.row.userNS ? t("el-button", {
                                staticClass: "text-button",
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.editUser(o.row)
                                    }
                                }
                            }, [e._v("修改")]) : e._e(), "default" != o.row.userNS ? t("el-button", {
                                staticClass: "text-button",
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.resetPassword(o.row)
                                    }
                                }
                            }, [e._v("重置密码")]) : e._e(), "default" != o.row.userNS ? t("el-button", {
                                staticClass: "text-button",
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.setAsDefaultBookSources(o.row)
                                    }
                                }
                            }, [e._v("设为默认书源")]) : e._e()]
                        }
                    }], null, !1, 1517798773)
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
                        click: e.deleteUserList
                    }
                }, [e._v("批量删除"), e.manageUserSelection.length ? t("span", [e._v(" (" + e._s(e.manageUserSelection.length) + ")")]) : e._e()]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.deleteUserBookSource
                    }
                }, [e._v("使用默认书源")]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: function(t) {
                            return e.clearInactiveUser()
                        }
                    }
                }, [e._v("清理")]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: function(t) {
                            return e.showAddUserDialog()
                        }
                    }
                }, [e._v("新增")])], 1), t("div", {
                    staticClass: "source-pagination"
                }, [t("el-pagination", {
                    attrs: {
                        "current-page": e.pagination.page,
                        "page-sizes": [25, 50, 100, 200, 300, 400, e.filterList.length],
                        "page-size": e.pagination.size,
                        layout: "total, sizes, prev, pager, next",
                        total: e.filterList.length,
                        "pager-count": e.$store.state.miniInterface ? 5 : 7
                    },
                    on: {
                        "update:currentPage": function(t) {
                            return e.$set(e.pagination, "page", t)
                        },
                        "update:current-page": function(t) {
                            return e.$set(e.pagination, "page", t)
                        },
                        "update:pageSize": function(t) {
                            return e.$set(e.pagination, "size", t)
                        },
                        "update:page-size": function(t) {
                            return e.$set(e.pagination, "size", t)
                        }
                    }
                })], 1)])]) : e._e()
            },
  staticRenderFns: []
}
</script>

<style scoped>
.float-right { float:right }
.dialog-footer { display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-pack:justify;justify-content:space-between }
.dialog-footer .float-left { margin-right:5px;margin-bottom:5px }
.source-container .text-button { padding:3px 5px }
.source-container .source-pagination { margin-top:5px;text-align:right }
</style>
