<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=b4d6c7ce；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog v-if="$store.getters.isNormalPage" :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' title="书签" :visible="show" :width="dialogWidth" :top="dialogTop" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }"><el-form :model="bookmarkForm"><el-form-item label="书名"><el-input readonly="" v-model="bookmarkForm.bookName" /></el-form-item><el-form-item label="作者"><el-input readonly="" v-model="bookmarkForm.bookAuthor" /></el-form-item><el-form-item label="章节"><el-input readonly="" v-model="bookmarkForm.chapterName" /></el-form-item><el-form-item label="内容"><el-input type="textarea" rows="5" readonly="" v-model="bookmarkForm.bookText" /></el-form-item><el-form-item label="备注"><el-input type="textarea" rows="3" v-model="bookmarkForm.content" /></el-form-item></el-form><div class="dialog-footer" slot="footer"><el-button size="medium" @click="cancel">取 消</el-button><el-button size="medium" type="primary" @click="save">确 定</el-button></div></el-dialog></el-dialog>
</template>

<script>import axios from 'axios'
import { merge as __merge } from '@/utils/merge'

/** 书签默认值（产物模块 bfff 的导出 c） */
const DEFAULT_BOOKMARK = {
  bookName: '',
  bookAuthor: '',
  chapterIndex: 0,
  chapterPos: 0,
  chapterName: '',
  bookText: '',
  content: ''
}

export default {

  model: {
      prop: "show",
      event: "setShow"
  },
  name: 'BookmarkForm',
  data: function() {
      return {
          bookmarkForm: __merge({}, DEFAULT_BOOKMARK)
      }
  },
  props: ["show", "bookmark", "isAdd"],
  computed: {},
  watch: {
      show: function(e) {
          e && (this.bookmarkForm = this.bookmark || __merge({}, DEFAULT_BOOKMARK))
      }
  },
  methods: {
      cancel: function() {
          this.$emit("setShow", !1)
      },
      save: function() {
          var e = this;
          if (this.bookmarkForm.bookName || this.bookmarkForm.bookAuthor)
              if (this.bookmarkForm.bookText) {
                  var t = __merge({}, this.bookmarkForm);
                  axios.post("/saveBookmark", t).then((function(t) {
                      t.data.isSuccess && (e.$message.success((e.isAdd ? "新增" : "编辑") + "书签成功"), e.$root.$children[0].loadBookmarks(!0), e.cancel())
                  }), (function(t) {
                      e.$message.error((e.isAdd ? "新增" : "编辑") + "书签失败 " + (t && t.toString()))
                  }))
              } else this.$message.error("书籍内容不能为空");
          else this.$message.error("书籍信息错误")
      }
  },
  render: function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "书签",
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
                }, [t("el-form", {
                    attrs: {
                        model: e.bookmarkForm
                    }
                }, [t("el-form-item", {
                    attrs: {
                        label: "书名"
                    }
                }, [t("el-input", {
                    attrs: {
                        readonly: ""
                    },
                    model: {
                        value: e.bookmarkForm.bookName,
                        callback: function(t) {
                            e.$set(e.bookmarkForm, "bookName", t)
                        },
                        expression: "bookmarkForm.bookName"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "作者"
                    }
                }, [t("el-input", {
                    attrs: {
                        readonly: ""
                    },
                    model: {
                        value: e.bookmarkForm.bookAuthor,
                        callback: function(t) {
                            e.$set(e.bookmarkForm, "bookAuthor", t)
                        },
                        expression: "bookmarkForm.bookAuthor"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "章节"
                    }
                }, [t("el-input", {
                    attrs: {
                        readonly: ""
                    },
                    model: {
                        value: e.bookmarkForm.chapterName,
                        callback: function(t) {
                            e.$set(e.bookmarkForm, "chapterName", t)
                        },
                        expression: "bookmarkForm.chapterName"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "内容"
                    }
                }, [t("el-input", {
                    attrs: {
                        type: "textarea",
                        rows: 5,
                        readonly: ""
                    },
                    model: {
                        value: e.bookmarkForm.bookText,
                        callback: function(t) {
                            e.$set(e.bookmarkForm, "bookText", t)
                        },
                        expression: "bookmarkForm.bookText"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "备注"
                    }
                }, [t("el-input", {
                    attrs: {
                        type: "textarea",
                        rows: 3
                    },
                    model: {
                        value: e.bookmarkForm.content,
                        callback: function(t) {
                            e.$set(e.bookmarkForm, "content", t)
                        },
                        expression: "bookmarkForm.content"
                    }
                })], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("el-button", {
                    attrs: {
                        size: "medium"
                    },
                    on: {
                        click: e.cancel
                    }
                }, [e._v("取 消")]), t("el-button", {
                    attrs: {
                        size: "medium",
                        type: "primary"
                    },
                    on: {
                        click: e.save
                    }
                }, [e._v("确 定")])], 1)], 1) : e._e()
            },
  staticRenderFns: []
}
</script>

<style scoped>
.float-left, .float-left { float:left }
</style>
