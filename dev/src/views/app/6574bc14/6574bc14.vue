<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=6574bc14；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog v-if="$store.getters.isNormalPage" :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' :visible="show" :width="dialogWidth" :top="dialogTop" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }"><div class="custom-dialog-title flex-title" slot="title"><span class="el-dialog__title">{{ _s(book ? book.name : "") + " 书签管理 " }}</span><span class="title-center"><el-input class="search-input" size="mini" placeholder="输入关键字搜索" v-model="search" /></span></div><div class="source-container table-container"><el-table :data="showList" :height="dialogContentHeight - 42" @selection-change="(t) => {
                            localSelection = t
                        }" @sort-change="sortChange"><el-table-column type="selection" width="25" :fixed="$store.state.miniInterface" /><el-table-column min-width="150px" label="书籍" sortable="custom" :fixed="$store.state.miniInterface"><template v-slot:default="t">{{ " " + _s(t.row.bookName) + " - " + _s(t.row.bookAuthor) + " " }}</template></el-table-column><el-table-column property="chapterName" label="章节" min-width="150px" sortable="custom" /><el-table-column property="bookText" label="内容" min-width="150px" /><el-table-column property="content" label="备注" min-width="150px" /><el-table-column label="操作" width="100px"><template v-slot:default="o"><template v-if="book"><el-button type="text" @click="(t) => {
                                        return showBookmark(o.row)
                                    }">跳转</el-button></template><el-button type="text" @click="(t) => {
                                        return editBookmark(o.row)
                                    }">编辑</el-button></template></el-table-column></el-table></div><div class="dialog-footer" slot="footer"><div><el-button class="float-left" type="primary" size="medium" @click="deleteBookmarks">批量删除 <template v-if="localSelection.length"><span>{{ " (" + _s(localSelection.length) + ")" }}</span></template></el-button><el-button class="float-left" type="primary" size="medium" @click="uploadFile">导入</el-button><el-button class="float-left" type="primary" size="medium" @click="addBookmark">添加</el-button><input ref='"fileRef"' style="display: none" type="file" @change="(t) => {
                            return onFileChange(t)
                        }" /></div><div class="source-pagination"><el-pagination :current-page="pagination.page" :page-sizes="[25, 50, 100, 200, 300, 400, filterList.length]" :page-size="pagination.size" layout="total, sizes, prev, pager, next" :total="filterList.length" :pager-count="$store.state.miniInterface ? 5 : 7" @update:currentPage='(t) => {
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

export default {

  model: {
      prop: "show",
      event: "setShow"
  },
  name: 'Bookmark',
  data: function() {
      return {
          localSelection: [],
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
  computed: __merge({}, {
      bookmarkList: function() {
          var e = this;
          return this.book && this.book.name ? this.$store.state.bookmarks.filter((function(t) {
              return t.bookName === e.book.name && t.bookAuthor === e.book.author
          })) : this.$store.state.bookmarks
      },
      filterList: function() {
          var e = this;
          return this.bookmarkList.filter((function(t) {
              return !e.search || t.bookName.toLowerCase().includes(e.search.toLowerCase()) || t.bookAuthor.toLowerCase().includes(e.search.toLowerCase()) || t.chapterName.toLowerCase().includes(e.search.toLowerCase()) || t.bookText.toLowerCase().includes(e.search.toLowerCase()) || t.content.toLowerCase().includes(e.search.toLowerCase())
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
          return e > this.filterList.length ? [] : this.filterList.slice(e, Math.min(e + this.pagination.size, this.filterList.length))
      }
  }),
  props: ["show", "book"],
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
      sortChange: function(e) {
          var t = e.prop,
              o = e.order;
          this.sortable = {
              prop: t,
              order: o
          }
      },
      cancel: function() {
          this.$emit("setShow", !1)
      },
      addBookmark: function() {
          eventBus.$emit("addBookmark")
      },
      deleteBookmarks: function() {
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
                          return e.$message.error("请选择需要删除的书签"), t.abrupt("return");
                      case 3:
                          return t.next = 5, e.$confirm("确认要删除所选择的书签吗?", "提示", {
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
                          axios.post(e.api + "/deleteBookmarks", e.localSelection).then((function(t) {
                              t.data.isSuccess && (e.localSelection = [], e.$message.success("删除书签成功"), e.$root.$children[0].loadBookmarks(!0))
                          }), (function(t) {
                              e.$message.error("删除书签失败 " + (t && t.toString()))
                          }));
                      case 9:
                      case "end":
                          return t.stop()
                  }
              }), t)
          })))()
      },
      editBookmark: function(e) {
          eventBus.$emit("showBookmarkForm", __merge({}, e), !1)
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
                  t.$message.error("书签文件错误")
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
                      })), o.length ? t.comfirmImport(o) : t.$message.error("书签文件错误")
                  }
              }), (function(e) {
                  t.$message.error("读取书签文件内容失败 " + (e && e.toString()))
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
                          return o.next = 2, t.$confirm("确认要导入文件中的".concat(e.length, "条书签吗?"), "提示", {
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
                          axios.post(t.api + "/saveBookmarks", e).then((function(e) {
                              e.data.isSuccess && (t.$message.success("导入书签成功"), t.$root.$children[0].loadBookmarks(!0))
                          }), (function(e) {
                              t.$message.error("导入书签失败 " + (e && e.toString()))
                          }));
                      case 6:
                      case "end":
                          return o.stop()
                  }
              }), o)
          })))()
      },
      showBookmark: function(e) {
          eventBus.$emit("showBookmark", e), this.cancel()
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
                }, [e._v(e._s(e.book ? e.book.name : "") + " 书签管理 ")]), t("span", {
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
                            e.localSelection = t
                        },
                        "sort-change": e.sortChange
                    }
                }, [t("el-table-column", {
                    attrs: {
                        type: "selection",
                        width: "25",
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        "min-width": "150px",
                        label: "书籍",
                        sortable: "custom",
                        fixed: e.$store.state.miniInterface
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(t) {
                            return [e._v(" " + e._s(t.row.bookName) + " - " + e._s(t.row.bookAuthor) + " ")]
                        }
                    }], null, !1, 3213026799)
                }), t("el-table-column", {
                    attrs: {
                        property: "chapterName",
                        label: "章节",
                        "min-width": "150px",
                        sortable: "custom"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "bookText",
                        label: "内容",
                        "min-width": "150px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "content",
                        label: "备注",
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
                            return [e.book ? t("el-button", {
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.showBookmark(o.row)
                                    }
                                }
                            }, [e._v("跳转")]) : e._e(), t("el-button", {
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.editBookmark(o.row)
                                    }
                                }
                            }, [e._v("编辑")])]
                        }
                    }], null, !1, 2296415621)
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
                        click: e.deleteBookmarks
                    }
                }, [e._v("批量删除 "), e.localSelection.length ? t("span", [e._v(" (" + e._s(e.localSelection.length) + ")")]) : e._e()]), t("el-button", {
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
                        click: e.addBookmark
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
                })], 1), t("div", {
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
.float-left { float:left }
.dialog-footer { display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-pack:justify;justify-content:space-between }
.dialog-footer .float-left { margin-right:5px;margin-bottom:5px }
.source-container .text-button { padding:3px 5px }
.source-container .source-pagination { margin-top:5px;text-align:right }
</style>
