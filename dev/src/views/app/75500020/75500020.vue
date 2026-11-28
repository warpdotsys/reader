<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=75500020；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog v-if="$store.getters.isNormalPage" :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' title="HttpTTS管理" :visible="show" :width="dialogWidth" :top="dialogTop" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }"><div class="custom-dialog-title" slot="title"><span class="el-dialog__title">HttpTTS管理</span></div><div class="source-container table-container"><el-table :data="$store.state.httpTTS" :height="dialogContentHeight" @selection-change="(t) => {
                            localSelection = t
                        }"><el-table-column type="selection" width="25" :fixed="$store.state.miniInterface" /><el-table-column property="name" min-width="150px" label="名称" :fixed="$store.state.miniInterface" /><el-table-column property="url" label="链接" min-width="150px" /><el-table-column label="操作" width="100px"><template v-slot:default="o"><el-button type="text" @click="(t) => {
                                        return editItem(o.row)
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
  name: 'HttpTTS',
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
                          return e.$message.error("请选择需要删除的HttpTTS"), t.abrupt("return");
                      case 3:
                          return t.next = 5, e.$confirm("确认要删除所选择的HttpTTS吗?", "提示", {
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
                          axios.post(e.api + "/httpTTS/deleteMulti", e.localSelection).then((function(t) {
                              t.data.isSuccess && (e.localSelection = [], e.$message.success("删除HttpTTS成功"), e.$root.$children[0].loadHttpTTS(!0))
                          }), (function(t) {
                              e.$message.error("删除HttpTTS失败 " + (t && t.toString()))
                          }));
                      case 9:
                      case "end":
                          return t.stop()
                  }
              }), t)
          })))()
      },
      editItem: function(e) {
          this.showEditor(__merge({}, e), "编辑HttpTTS")
      },
      addNew: function() {
          this.showEditor({
              name: "新增HttpTTS",
              url: "",
              contentType: "",
              header: ""
          }, "新增HttpTTS")
      },
      showEditor: function(e, t) {
          var o = this;
          eventBus.$emit("showEditor", t, JSON.stringify(e, null, 4), (function(e, t) {
              try {
                  var n = JSON.parse(e);
                  if (!n.name) return void o.$message.error("名称不能为空");
                  if (!n.url) return void o.$message.error("链接不能为空");
                  axios.post(o.api + "/httpTTS/save", n).then((function(e) {
                      e.data.isSuccess && (t(), o.$message.success("保存成功"), o.$root.$children[0].loadHttpTTS(!0))
                  }), (function(e) {
                      o.$message.error("保存失败 " + (e && e.toString()))
                  }))
              } catch (r) {
                  o.$message.error("必须是JSON格式")
              }
          }))
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
                  t.$message.error("HttpTTS文件错误")
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
                      })), o.length ? t.comfirmImport(o) : t.$message.error("HttpTTS文件错误")
                  }
              }), (function(e) {
                  t.$message.error("读取HttpTTS文件内容失败 " + (e && e.toString()))
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
                          return o.next = 2, t.$confirm("确认要导入文件中的".concat(e.length, "条HttpTTS吗?"), "提示", {
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
                          axios.post(t.api + "/httpTTS/saveMulti", e).then((function(e) {
                              e.data.isSuccess && (t.$message.success("导入HttpTTS成功"), t.$root.$children[0].loadHttpTTS(!0))
                          }), (function(e) {
                              t.$message.error("导入HttpTTS失败 " + (e && e.toString()))
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
                        title: "HttpTTS管理",
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
                }, [e._v("HttpTTS管理")])]), t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    attrs: {
                        data: e.$store.state.httpTTS,
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
                        property: "url",
                        label: "链接",
                        "min-width": "150px"
                    }
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
                                        return e.editItem(o.row)
                                    }
                                }
                            }, [e._v("编辑")])]
                        }
                    }], null, !1, 966691103)
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
.float-left { float:left }
.dialog-footer { display:-ms-flexbox;display:flex;-ms-flex-direction:row-reverse;flex-direction:row-reverse;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-pack:justify;justify-content:space-between }
.dialog-footer .float-left { margin-right:5px;margin-bottom:5px }
</style>
