<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=44eeac3f；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog v-if="$store.getters.isNormalPage" :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' title="替换规则管理" :visible="show" :width="dialogWidth" :top="dialogTop" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }"><div class="custom-dialog-title" slot="title"><span class="el-dialog__title">替换规则管理</span></div><div class="source-container table-container"><el-table :data="$store.state.filterRules" :height="dialogContentHeight" @selection-change="(t) => {
                            localSelection = t
                        }"><el-table-column type="selection" width="25" :fixed="$store.state.miniInterface" /><el-table-column property="name" min-width="150px" label="规则名称" :fixed="$store.state.miniInterface" /><el-table-column property="scope" label="替换范围" min-width="150px" /><el-table-column property="isEnabled" label="是否启用" min-width="80"><template v-slot:default="o"><el-switch active-color="#13ce66" inactive-color="#ff4949" :active-value="true" :inactive-value="false" v-model="o.row.isEnabled" @change="(t) => {
                                        return toggleRuleEnabled(o.row, t)
                                    }" /></template></el-table-column><el-table-column label="操作" width="100px"><template v-slot:default="o"><el-button type="text" @click="(t) => {
                                        return editReplaceRule(o.row)
                                    }">编辑</el-button></template></el-table-column></el-table></div><div class="dialog-footer" slot="footer"><div><el-button class="float-left" type="primary" size="medium" @click="deleteReplaceRules">批量删除<template v-if="localSelection.length"><span>{{ " (" + _s(localSelection.length) + ")" }}</span></template></el-button><el-button class="float-left" type="primary" size="medium" @click="uploadFile">导入</el-button><el-button class="float-left" type="primary" size="medium" @click="addNew">添加</el-button><input ref='"fileRef"' style="display: none" type="file" @change="(t) => {
                            return onFileChange(t)
                        }" /></div></div></el-dialog></el-dialog>
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
  name: 'ReplaceRule',
  data: function() {
      return {
          localSelection: []
      }
  },
  computed: {},
  props: ["show"],
  watch: {
      show: function(e) {}
  },
  methods: {
      formatTableField: function(e, t, o) {
          switch (t.property) {
              default:
                  return o
          }
      },
      cancel: function() {
          this.$emit("setShow", !1)
      },
      deleteReplaceRules: function() {
          var e = this;
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              var o;
              return regeneratorRuntime.wrap((function(t) {
                  while (1) switch (t.prev = t.next) {
                      case 0:
                          if (e.localSelection.length) {
                              t.next = 3;
                              break
                          }
                          return e.$message.error("请选择需要删除的替换规则"), t.abrupt("return");
                      case 3:
                          return t.next = 5, e.$confirm("确认要删除所选择的替换规则吗?", "提示", {
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
                          axios.post(e.api + "/deleteReplaceRules", e.localSelection).then((function(t) {
                              t.data.isSuccess && (e.localSelection = [], e.$message.success("删除替换规则成功"), e.$root.$children[0].loadReplaceRules(!0))
                          }), (function(t) {
                              e.$message.error("删除替换规则失败 " + (t && t.toString()))
                          }));
                      case 9:
                      case "end":
                          return t.stop()
                  }
              }), t)
          })))()
      },
      toggleRuleEnabled: function(e, t) {
          var o = this;
          axios.post("/saveReplaceRule", __merge(__merge({}, e), {}, {
              isEnabled: t
          })).then((function(e) {
              e.data.isSuccess && (o.$message.success("修改成功"), o.$root.$children[0].loadReplaceRules(!0))
          }), (function(e) {
              o.$message.error("修改失败 " + (e && e.toString()))
          }))
      },
      editReplaceRule: function(e) {
          eventBus.$emit("showReplaceRuleForm", __merge({}, e), !1)
      },
      addNew: function() {
          eventBus.$emit("showReplaceRuleForm", {
              name: "文本替换",
              pattern: "",
              replacement: "",
              isRegex: !1,
              isEnabled: !0,
              scope: this.$store.getters.readingBook ? this.$store.getters.readingBook.name + ";" + this.$store.getters.readingBook.bookUrl : "*"
          }, !1)
      },
      uploadFile: function() {
          this.$refs.fileRef.dispatchEvent(new MouseEvent("click"))
      },
      onFileChange: function(e) {
          var t = this,
              o = e.target.files && e.target.files[0],
              n = new FileReader;
          n.onload = function(e) {
              var o = e.target.result;
              try {
                  var n = JSON.parse(o);
                  Array.isArray(n) && n.length && t.comfirmImport(n)
              } catch (r) {
                  t.$message.error("替换规则文件错误")
              }
          }, n.onerror = function() {
              var e = new FormData;
              e.append("file", o), axios.post(t.api + "/readSourceFile", e, {
                  headers: {
                      "Content-Type": "multipart/form-data"
                  }
              }).then((function(e) {
                  if (e.data.isSuccess) {
                      var o = [];
                      e.data.data.forEach((function(e) {
                          try {
                              var t = JSON.parse(e);
                              Array.isArray(t) && (o = o.concat(t))
                          } catch (n) {}
                      })), o.length ? t.comfirmImport(o) : t.$message.error("替换规则文件错误")
                  }
              }), (function(e) {
                  t.$message.error("读取替换规则文件内容失败 " + (e && e.toString()))
              }))
          }, n.readAsText(o), this.$refs.fileRef.value = null
      },
      comfirmImport: function(e) {
          var t = this;
          return asyncToGenerator(regeneratorRuntime.mark((function o() {
              var n;
              return regeneratorRuntime.wrap((function(o) {
                  while (1) switch (o.prev = o.next) {
                      case 0:
                          return o.next = 2, t.$confirm("确认要导入文件中的".concat(e.length, "条替换规则吗?"), "提示", {
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
                          axios.post(t.api + "/saveReplaceRules", e).then((function(e) {
                              e.data.isSuccess && (t.$message.success("导入替换规则成功"), t.$root.$children[0].loadReplaceRules(!0))
                          }), (function(e) {
                              t.$message.error("导入替换规则失败 " + (e && e.toString()))
                          }));
                      case 6:
                      case "end":
                          return o.stop()
                  }
              }), o)
          })))()
      }
  },
  render: function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "替换规则管理",
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
                }, [e._v("替换规则管理")])]), t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    attrs: {
                        data: e.$store.state.filterRules,
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
                        label: "规则名称",
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "scope",
                        label: "替换范围",
                        "min-width": "150px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "isEnabled",
                        label: "是否启用",
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
                                        return e.toggleRuleEnabled(o.row, t)
                                    }
                                },
                                model: {
                                    value: o.row.isEnabled,
                                    callback: function(t) {
                                        e.$set(o.row, "isEnabled", t)
                                    },
                                    expression: "scope.row.isEnabled"
                                }
                            })]
                        }
                    }], null, !1, 2011687545)
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
                                        return e.editReplaceRule(o.row)
                                    }
                                }
                            }, [e._v("编辑")])]
                        }
                    }], null, !1, 1473522600)
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
                        click: e.deleteReplaceRules
                    }
                }, [e._v("批量删除"), e.localSelection.length ? t("span", [e._v(" (" + e._s(e.localSelection.length) + ")")]) : e._e()]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.uploadFile
                    }
                }, [e._v("导入")]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.addNew
                    }
                }, [e._v("添加")]), t("input", {
                    ref: "fileRef",
                    staticStyle: {
                        display: "none"
                    },
                    attrs: {
                        type: "file"
                    },
                    on: {
                        change: function(t) {
                            return e.onFileChange(t)
                        }
                    }
                })], 1)])]) : e._e()
            },
  staticRenderFns: []
}
</script>

<style scoped>
@charset "UTF-8";.float-left { float:left }
.dialog-footer { display:-ms-flexbox;display:flex;-ms-flex-direction:row-reverse;flex-direction:row-reverse;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-pack:justify;justify-content:space-between }
.dialog-footer .float-left { margin-right:5px;margin-bottom:5px }
</style>
