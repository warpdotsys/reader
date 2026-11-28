<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=eecda6a0；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog v-if="$store.getters.isNormalPage" :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' :title="title" :visible="show" :width="dialogWidth" :top="dialogTop" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }"><div class="custom-dialog-title" slot="title"><span class="el-dialog__title">{{ _s(this.title) + " " }}<span class="float-right span-btn" @click="resolveAndImportBooks">一键导入</span><span class="float-right span-btn" @click="resolveBooks">解析书籍</span></span></div><div class="source-container table-container"><el-table ref='"fileTable"' :data="filteredFileList" :height="dialogContentHeight" @selection-change="(t) => {
                            fileSelection = t
                        }"><el-table-column type="selection" width="25" :fixed="$store.state.miniInterface" :selectable="function(e) {
                            return !toParent
                        }" /><el-table-column property="name" min-width="150px" :label='resolveMode ? "书名" : "文件名"' sortable="" :sort-method="sortMethod" :filters='[{
                            text: "已导入",
                            value: "imported"
                        }, {
                            text: "未导入",
                            value: "unImport"
                        }, {
                            text: "可导入",
                            value: "canImport"
                        }, {
                            text: "目录",
                            value: "directory"
                        }]' :filter-method="filterHandler" :fixed="$store.state.miniInterface"><template v-slot:default="o"><template v-if="o.row.isImported"><span class="imported-book-name">{{ o.row.name }}</span></template><template v-else><template v-if="!(o.row.isDirectory)"><span>{{ o.row.name }}</span></template></template><template v-if="o.row.isDirectory"><el-link type="primary" @click="(t) => {
                                        return showFileList(o.row.path)
                                    }">{{ o.row.name }}</el-link></template></template></el-table-column><template v-if="resolveMode"><el-table-column property="book.author" label="作者名" min-width="100px" sortable="" :filters="authorList" :filter-method="filterAuthor" /></template><el-table-column property="size" label="大小" sortable="" :formatter="formatTableField" min-width="100px" /><el-table-column property="lastModified" label="修改时间" sortable="" :formatter="formatTableField" width="120px" /><el-table-column align="right" width="140px"><template slot='"header"'><el-input size="mini" placeholder="搜索本页" v-model="keyword" /></template><template v-slot:default="o"><template v-if="!(o.row.isDirectory)"><el-dropdown :trigger='$store.state.touchable ? "click" : "hover"' @command="(t) => {
                                        return operateFile(o.row, t)
                                    }"><el-button class="text-button" type="text" size="medium"> 操作<i class="el-icon-arrow-down el-icon--right" /></el-button><el-dropdown-menu slot="dropdown"><template v-if="!(o.row.isDirectory)"><el-dropdown-item command="downloadFile">下载</el-dropdown-item></template><template v-if='!o.row.isDirectory && o.row.name.endsWith(".zip")'><el-dropdown-item command="restoreFromFile">还原</el-dropdown-item></template><template v-if='!o.row.isDirectory && o.row.name.endsWith(".json")'><el-dropdown-item command="editFile">编辑</el-dropdown-item></template><template v-if="o.row.canImport"><el-dropdown-item command="importFromFile">加入书架</el-dropdown-item></template></el-dropdown-menu></el-dropdown></template><template v-if="!(o.row.toParent)"><el-button style="color: #f56c6c" type="text" @click="(t) => {
                                        return deleteFile(o.row)
                                    }">删除</el-button></template></template></el-table-column></el-table></div><div class="dialog-footer" slot="footer"><el-dropdown class="float-left" :trigger='$store.state.touchable ? "click" : "hover"' @command="operate"><el-button type="primary" size="medium"> 操作<template v-if="fileSelection.length"><span>{{ " (" + _s(fileSelection.length) + ")" }}</span></template><i class="el-icon-arrow-down el-icon--right" /></el-button><el-dropdown-menu slot="dropdown"><el-dropdown-item command="deleteFileList">批量删除</el-dropdown-item><el-dropdown-item command="importFromFile">批量加入书架</el-dropdown-item><el-dropdown-item command="mkdir">新建目录</el-dropdown-item><el-dropdown-item command="upload">上传文件</el-dropdown-item></el-dropdown-menu></el-dropdown><input ref='"bookRef"' style="display: none" type="file" multiple="multiple" @change="onFileChange" /></div></el-dialog></el-dialog>
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
  name: 'FileManager',
  data: function() {
      return {
          currentPath: "/",
          fileList: [],
          keyword: "",
          searchWord: "",
          resolveMode: !1,
          fileSelection: []
      }
  },
  props: ["show", "home", "title"],
  computed: __merge({}, {
      localBook: function() {
          return this.$store.getters.shelfBooks.filter((function(e) {
              return "loc_book" === e.origin
          }))
      },
      filteredFileList: function() {
          var e = this;
          return this.fileList.filter((function(t) {
              return !e.keyword || t.name.toLowerCase().includes(e.keyword.toLowerCase())
          }))
      },
      authorList: function() {
          if (!this.resolveMode) return [];
          var e = {};
          return this.filteredFileList.map((function(e) {
              return e.book.author
          })).filter((function(t) {
              return !(!t || e[t]) && (e[t] = 1, !0)
          })).map((function(e) {
              return {
                  text: e,
                  value: e
              }
          }))
      }
  }),
  watch: {
      show: function(e) {
          var t = this;
          e && (this.resolveMode = !1, this.showFileList("/"), this.$nextTick((function() {
              t.$refs.fileTable.clearFilter(), t.$refs.fileTable.clearSelection(), t.$refs.fileTable.clearSort()
          })))
      }
  },
  methods: {
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
      canImport: function(e) {
          var t = e.path.toLowerCase();
          return t.endsWith(".txt") || t.endsWith(".epub") || t.endsWith(".umd") || t.endsWith(".cbz") || t.endsWith(".pdf")
      },
      cancel: function() {
          this.$emit("setShow", !1)
      },
      resolveBooks: function() {
          var e = this;
          this.$confirm("解析当前目录及子目录的所有书籍耗费资源较多，确认要继续?", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
              beforeClose: function(t, o, n) {
                  "confirm" === t ? (o.confirmButtonLoading = !0, o.confirmButtonText = "解析中...", axios.post(e.api + "/file/parse", {
                      path: e.currentPath,
                      home: e.home
                  }).then((function(t) {
                      t.data.isSuccess && (e.fileList = t.data.data.map((function(t) {
                          return __merge(__merge({}, t), {}, {
                              isImported: !!e.localBook.find((function(e) {
                                  return e.originName.indexOf(t.path) >= 0
                              })),
                              canImport: e.canImport(t)
                          })
                      })), e.resolveMode = !0, e.$nextTick((function() {
                          e.$refs.fileTable && e.$refs.fileTable.doLayout()
                      })))
                  }), (function(t) {
                      e.$message.error("解析书籍失败 " + (t && t.toString()))
                  })).then((function() {
                      n(), o.confirmButtonLoading = !1
                  }))) : n()
              }
          })["catch"]((function() {
              return !1
          }))
      },
      resolveAndImportBooks: function() {
          var e = this;
          this.$confirm("一键导入当前目录及子目录的所有书籍耗费资源较多，确认要继续?", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
              beforeClose: function(t, o, n) {
                  "confirm" === t ? (o.confirmButtonLoading = !0, o.confirmButtonText = "导入中...", axios.post(e.api + "/file/parse", {
                      path: e.currentPath,
                      home: e.home,
                      import: 1
                  }, {
                      timeout: 3e5
                  }).then((function(t) {
                      t.data.isSuccess && (e.$root.$children[0].loadBookShelf(), e.$alert("成功导入" + t.data.data.length + "本书", "导入结果"))
                  }), (function(t) {
                      e.$message.error("一键导入书籍失败 " + (t && t.toString()))
                  })).then((function() {
                      n(), o.confirmButtonLoading = !1
                  }))) : n()
              }
          })["catch"]((function() {
              return !1
          }))
      },
      showFileList: function(e) {
          var t = this;
          this.currentPath = e || "/", axios.get(this.api + "/file/list", {
              params: {
                  path: this.currentPath,
                  home: this.home
              }
          }).then((function(e) {
              if (e.data.isSuccess) {
                  if (e.data.data = e.data.data || [], "/" !== t.currentPath) {
                      var o = t.currentPath.split("/").filter((function(e) {
                          return e
                      }));
                      o.pop(), e.data.data.unshift({
                          name: "..",
                          isDirectory: !0,
                          toParent: !0,
                          path: "/" + o.join("/")
                      })
                  }
                  e.data.data.sort(t.sortMethod), t.fileList = e.data.data.map((function(e) {
                      return __merge(__merge({}, e), {}, {
                          isImported: !e.isDirectory && !!t.localBook.find((function(t) {
                              return t.originName.indexOf(e.path) >= 0
                          })),
                          canImport: t.canImport(e)
                      })
                  }))
              }
          }), (function(e) {
              t.$message.error("加载文件列表失败 " + (e && e.toString()))
          }))
      },
      sortMethod: function(e, t) {
          return ".." === e.name ? -1 : ".." === t.name ? 1 : e.isDirectory && !t.isDirectory ? -1 : !e.isDirectory && t.isDirectory || e.name > t.name ? 1 : -1
      },
      filterHandler: function(e, t) {
          switch (e) {
              case "imported":
                  return t.isImported;
              case "unImport":
                  return !t.isDirectory && !t.isImported;
              case "canImport":
                  return !t.isDirectory && t.canImport;
              case "directory":
                  return t.isDirectory;
              default:
                  return !0
          }
      },
      filterAuthor: function(e, t) {
          return t.book.author === e
      },
      operate: function(e) {
          if ("importFromFile" === e) return this.importFromFile(!0);
          this[e].call(this)
      },
      operateFile: function(e, t) {
          this[t].call(this, e)
      },
      deleteFileList: function() {
          var e = this;
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              var o;
              return regeneratorRuntime.wrap((function(t) {
                  while (1) switch (t.prev = t.next) {
                      case 0:
                          if (e.fileSelection.length) {
                              t.next = 3;
                              break
                          }
                          return e.$message.error("请选择需要删除的文件"), t.abrupt("return");
                      case 3:
                          return t.next = 5, e.$confirm("确认要删除所选择的文件吗?", "提示", {
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
                          axios.post(e.api + "/file/deleteMulti", {
                              path: e.fileSelection.map((function(e) {
                                  return e.path
                              })),
                              home: e.home
                          }).then((function(t) {
                              t.data.isSuccess && (e.fileSelection = [], e.$message.success("删除文件成功"), e.showFileList(e.currentPath))
                          }), (function(t) {
                              e.$message.error("删除文件失败 " + (t && t.toString()))
                          }));
                      case 9:
                      case "end":
                          return t.stop()
                  }
              }), t)
          })))()
      },
      deleteFile: function(e) {
          var t = this;
          return asyncToGenerator(regeneratorRuntime.mark((function o() {
              var n;
              return regeneratorRuntime.wrap((function(o) {
                  while (1) switch (o.prev = o.next) {
                      case 0:
                          return o.next = 2, t.$confirm("确认要删除该".concat(e.isDirectory ? "目录" : "文件", "吗?"), "提示", {
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
                          axios.post(t.api + "/file/delete", {
                              path: e.path,
                              home: t.home
                          }).then((function(e) {
                              e.data.isSuccess && (t.$message.success("删除成功"), t.showFileList(t.currentPath))
                          }), (function(e) {
                              t.$message.error("删除失败 " + (e && e.toString()))
                          }));
                      case 6:
                      case "end":
                          return o.stop()
                  }
              }), o)
          })))()
      },
      editFile: function(e) {
          var t = this;
          return asyncToGenerator(regeneratorRuntime.mark((function o() {
              return regeneratorRuntime.wrap((function(o) {
                  while (1) switch (o.prev = o.next) {
                      case 0:
                          axios.get(t.api + "/file/get", {
                              params: {
                                  path: e.path,
                                  home: t.home
                              }
                          }).then((function(o) {
                              if (o.data.isSuccess) {
                                  var n = o.data.data;
                                  try {
                                      n = JSON.stringify(JSON.parse(n), null, 4)
                                  } catch (r) {
                                      n = o.data.data
                                  }
                                  eventBus.$emit("showEditor", "编辑 " + e.name, n, function() {
                                      var o = asyncToGenerator(regeneratorRuntime.mark((function o(n, r) {
                                          var a;
                                          return regeneratorRuntime.wrap((function(o) {
                                              while (1) switch (o.prev = o.next) {
                                                  case 0:
                                                      return o.prev = 0, o.next = 3, t.$confirm("随意修改内容有可能会造成数据错乱，确认要保存编辑后的内容吗?", "提示", {
                                                          confirmButtonText: "确定",
                                                          cancelButtonText: "取消",
                                                          type: "warning"
                                                      })["catch"]((function() {
                                                          return !1
                                                      }));
                                                  case 3:
                                                      if (a = o.sent, a) {
                                                          o.next = 6;
                                                          break
                                                      }
                                                      return o.abrupt("return");
                                                  case 6:
                                                      axios.post(t.api + "/file/save", {
                                                          path: e.path,
                                                          home: t.home,
                                                          content: n
                                                      }).then((function(e) {
                                                          e.data.isSuccess && r()
                                                      }), (function(e) {
                                                          t.$message.error("保存文件内容失败 " + (e && e.toString()))
                                                      })), o.next = 12;
                                                      break;
                                                  case 9:
                                                      o.prev = 9, o.t0 = o["catch"](0), t.$message.error("内容必须是JSON格式");
                                                  case 12:
                                                  case "end":
                                                      return o.stop()
                                              }
                                          }), o, null, [
                                              [0, 9]
                                          ])
                                      })));
                                      return function(e, t) {
                                          return o.apply(this, arguments)
                                      }
                                  }())
                              }
                          }), (function(e) {
                              t.$message.error("获取文件内容失败 " + (e && e.toString()))
                          }));
                      case 1:
                      case "end":
                          return o.stop()
                  }
              }), o)
          })))()
      },
      mkdir: function() {
          var e = this;
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              var o;
              return regeneratorRuntime.wrap((function(t) {
                  while (1) switch (t.prev = t.next) {
                      case 0:
                          return t.next = 2, e.$prompt("", "新建目录", {
                              inputValue: "",
                              confirmButtonText: "确定",
                              cancelButtonText: "取消",
                              inputValidator: function(e) {
                                  return !!e || "目录名不能为空"
                              }
                          })["catch"]((function() {
                              return !1
                          }));
                      case 2:
                          if (o = t.sent, o) {
                              t.next = 5;
                              break
                          }
                          return t.abrupt("return");
                      case 5:
                          axios.post(e.api + "/file/mkdir", {
                              path: e.currentPath,
                              name: o.value,
                              home: e.home
                          }).then((function(t) {
                              t.data.isSuccess && (e.$message.success("操作成功"), e.showFileList(e.currentPath))
                          }), (function(t) {
                              e.$message.error("操作失败" + (t && t.toString()))
                          }));
                      case 6:
                      case "end":
                          return t.stop()
                  }
              }), t)
          })))()
      },
      importFromFile: function(e) {
          var t = this;
          return asyncToGenerator(regeneratorRuntime.mark((function o() {
              return regeneratorRuntime.wrap((function(o) {
                  while (1) switch (o.prev = o.next) {
                      case 0:
                          if (!0 !== e) {
                              o.next = 4;
                              break
                          }
                          if (t.fileSelection.length) {
                              o.next = 4;
                              break
                          }
                          return t.$message.error("请选择需要加入书架的书籍"), o.abrupt("return");
                      case 4:
                          axios.post(t.api + "/file/importPreview", {
                              path: !0 === e ? t.fileSelection.map((function(e) {
                                  return e.path
                              })) : [e.path],
                              home: t.home
                          }).then((function(e) {
                              if (e.data.isSuccess) {
                                  if (!e.data.data || !e.data.data.length) return void t.$message.error("没有选择可导入的书籍");
                                  setTimeout((function() {
                                      eventBus.$emit("importPreview", e.data.data)
                                  }), 0)
                              }
                          }), (function(e) {
                              t.$message.error("请求失败 " + (e && e.toString()))
                          }));
                      case 5:
                      case "end":
                          return o.stop()
                  }
              }), o)
          })))()
      },
      upload: function() {
          this.$refs.bookRef.dispatchEvent(new MouseEvent("click"))
      },
      downloadFile: function(e) {
          var t = tt(this.api + "/file/download", {
              home: this.home,
              path: e.path,
              accessToken: this.$store.state.token
          });
          window.open(t, "__blank")
      },
      onFileChange: function(e) {
          var t = this;
          if (e.target && e.target.files && e.target.files.length) {
              for (var o = new FormData, n = 0; n < e.target.files.length; n++) {
                  var r = e.target.files[n];
                  o.append("file" + n, r)
              }
              o.append("home", this.home), o.append("path", this.currentPath), axios.post(this.api + "/file/upload", o, {
                  headers: {
                      "Content-Type": "multipart/form-data"
                  }
              }).then((function(e) {
                  e.data.isSuccess && (t.$message.success("上传文件成功"), t.showFileList(t.currentPath))
              }), (function(e) {
                  t.$message.error("上传文件 " + (e && e.toString()))
              })), this.$refs.bookRef.value = null
          }
      },
      restoreFromFile: function(e) {
          var t = this;
          return asyncToGenerator(regeneratorRuntime.mark((function o() {
              var n;
              return regeneratorRuntime.wrap((function(o) {
                  while (1) switch (o.prev = o.next) {
                      case 0:
                          return o.next = 2, t.$confirm("确认要从该压缩文件恢复书源、书架、分组、RSS订阅数据、替换规则、书签、用户配置和Webdav书籍吗?", "提示", {
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
                          axios.post(t.api + "/file/restore", {
                              path: e.path,
                              home: t.home
                          }).then((function(e) {
                              e.data.isSuccess && (t.$message.success("恢复成功"), t.$root.$children[0].init(!0))
                          }), (function(e) {
                              t.$message.error("恢复失败 " + (e && e.toString()))
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
                        title: e.title,
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
                }, [e._v(e._s(this.title) + " "), t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.resolveAndImportBooks
                    }
                }, [e._v("一键导入")]), t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.resolveBooks
                    }
                }, [e._v("解析书籍")])])]), t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    ref: "fileTable",
                    attrs: {
                        data: e.filteredFileList,
                        height: e.dialogContentHeight
                    },
                    on: {
                        "selection-change": function(t) {
                            e.fileSelection = t
                        }
                    }
                }, [t("el-table-column", {
                    attrs: {
                        type: "selection",
                        width: "25",
                        fixed: e.$store.state.miniInterface,
                        selectable: function(e) {
                            return !e.toParent
                        }
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "name",
                        "min-width": "150px",
                        label: e.resolveMode ? "书名" : "文件名",
                        sortable: "",
                        "sort-method": e.sortMethod,
                        filters: [{
                            text: "已导入",
                            value: "imported"
                        }, {
                            text: "未导入",
                            value: "unImport"
                        }, {
                            text: "可导入",
                            value: "canImport"
                        }, {
                            text: "目录",
                            value: "directory"
                        }],
                        "filter-method": e.filterHandler,
                        fixed: e.$store.state.miniInterface
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [o.row.isImported ? t("span", {
                                staticClass: "imported-book-name"
                            }, [e._v(e._s(o.row.name))]) : o.row.isDirectory ? e._e() : t("span", [e._v(e._s(o.row.name))]), o.row.isDirectory ? t("el-link", {
                                attrs: {
                                    type: "primary"
                                },
                                on: {
                                    click: function(t) {
                                        return e.showFileList(o.row.path)
                                    }
                                }
                            }, [e._v(e._s(o.row.name))]) : e._e()]
                        }
                    }], null, !1, 3985659619)
                }), e.resolveMode ? t("el-table-column", {
                    attrs: {
                        property: "book.author",
                        label: "作者名",
                        "min-width": "100px",
                        sortable: "",
                        filters: e.authorList,
                        "filter-method": e.filterAuthor
                    }
                }) : e._e(), t("el-table-column", {
                    attrs: {
                        property: "size",
                        label: "大小",
                        sortable: "",
                        formatter: e.formatTableField,
                        "min-width": "100px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "lastModified",
                        label: "修改时间",
                        sortable: "",
                        formatter: e.formatTableField,
                        width: "120px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        align: "right",
                        width: "140px"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [o.row.isDirectory ? e._e() : t("el-dropdown", {
                                attrs: {
                                    trigger: e.$store.state.touchable ? "click" : "hover"
                                },
                                on: {
                                    command: function(t) {
                                        return e.operateFile(o.row, t)
                                    }
                                }
                            }, [t("el-button", {
                                staticClass: "text-button",
                                attrs: {
                                    type: "text",
                                    size: "medium"
                                }
                            }, [e._v(" 操作"), t("i", {
                                staticClass: "el-icon-arrow-down el-icon--right"
                            })]), t("el-dropdown-menu", {
                                attrs: {
                                    slot: "dropdown"
                                },
                                slot: "dropdown"
                            }, [o.row.isDirectory ? e._e() : t("el-dropdown-item", {
                                attrs: {
                                    command: "downloadFile"
                                }
                            }, [e._v("下载")]), !o.row.isDirectory && o.row.name.endsWith(".zip") ? t("el-dropdown-item", {
                                attrs: {
                                    command: "restoreFromFile"
                                }
                            }, [e._v("还原")]) : e._e(), !o.row.isDirectory && o.row.name.endsWith(".json") ? t("el-dropdown-item", {
                                attrs: {
                                    command: "editFile"
                                }
                            }, [e._v("编辑")]) : e._e(), o.row.canImport ? t("el-dropdown-item", {
                                attrs: {
                                    command: "importFromFile"
                                }
                            }, [e._v("加入书架")]) : e._e()], 1)], 1), o.row.toParent ? e._e() : t("el-button", {
                                staticStyle: {
                                    color: "#f56c6c"
                                },
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.deleteFile(o.row)
                                    }
                                }
                            }, [e._v("删除")])]
                        }
                    }], null, !1, 1541015572)
                }, [t("template", {
                    slot: "header"
                }, [t("el-input", {
                    attrs: {
                        size: "mini",
                        placeholder: "搜索本页"
                    },
                    model: {
                        value: e.keyword,
                        callback: function(t) {
                            e.keyword = t
                        },
                        expression: "keyword"
                    }
                })], 1)], 2)], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("el-dropdown", {
                    staticClass: "float-left",
                    attrs: {
                        trigger: e.$store.state.touchable ? "click" : "hover"
                    },
                    on: {
                        command: e.operate
                    }
                }, [t("el-button", {
                    attrs: {
                        type: "primary",
                        size: "medium"
                    }
                }, [e._v(" 操作"), e.fileSelection.length ? t("span", [e._v(" (" + e._s(e.fileSelection.length) + ")")]) : e._e(), t("i", {
                    staticClass: "el-icon-arrow-down el-icon--right"
                })]), t("el-dropdown-menu", {
                    attrs: {
                        slot: "dropdown"
                    },
                    slot: "dropdown"
                }, [t("el-dropdown-item", {
                    attrs: {
                        command: "deleteFileList"
                    }
                }, [e._v("批量删除")]), t("el-dropdown-item", {
                    attrs: {
                        command: "importFromFile"
                    }
                }, [e._v("批量加入书架")]), t("el-dropdown-item", {
                    attrs: {
                        command: "mkdir"
                    }
                }, [e._v("新建目录")]), t("el-dropdown-item", {
                    attrs: {
                        command: "upload"
                    }
                }, [e._v("上传文件")])], 1)], 1), t("input", {
                    ref: "bookRef",
                    staticStyle: {
                        display: "none"
                    },
                    attrs: {
                        type: "file",
                        multiple: "multiple"
                    },
                    on: {
                        change: e.onFileChange
                    }
                })], 1)]) : e._e()
            },
  staticRenderFns: []
}
</script>

<style scoped>
.float-left, .float-left { float:left }
.dialog-footer { display:-ms-flexbox;display:flex;-ms-flex-direction:row-reverse;flex-direction:row-reverse;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-pack:justify;justify-content:space-between }
.dialog-footer .float-left { margin-right:5px;margin-bottom:5px }
.source-container .text-button { padding:3px 5px }
.source-container .imported-book-name { color:#bbb }
</style>
