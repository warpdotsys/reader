<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=08713662；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog v-if="$store.getters.isNormalPage" :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' title="书籍设置" :visible="show" :width="dialogSmallWidth" :top="dialogTop" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }"><el-form :model="bookConfig"><el-form-item label="PDF图片宽度"><el-select class="setting-select" size="mini" filterable="" placeholder="请选择PDF图片宽度" v-model="bookConfig.pdfImageWidth"><template v-for="(e, o) in pdfImageWidthList"><el-option :key='"pdf-image-width-" + o' :label="e.name" :value="e.value" /></template></el-select></el-form-item></el-form><div class="dialog-footer" slot="footer"><el-button size="medium" type="primary" @click="save">保 存</el-button><el-button size="medium" type="primary" @click="cancel">关 闭</el-button></div></el-dialog></el-dialog>
</template>

<script>import axios from 'axios'
import { merge as __merge } from '@/utils/merge'

export default {

  model: {
      prop: "show",
      event: "setShow"
  },
  name: 'BookConfig',
  data: function() {
      return {
          isAdd: !0,
          bookConfig: {
              pdfImageWidth: (this.$store.state.showBookInfo.readConfig || {}).pdfImageWidth || 800
          },
          pdfImageWidthList: [{
              name: "750px",
              value: 750
          }, {
              name: "800px",
              value: 800
          }, {
              name: "850px",
              value: 850
          }, {
              name: "900px",
              value: 900
          }, {
              name: "950px",
              value: 950
          }, {
              name: "1000px",
              value: 1e3
          }, {
              name: "1100px",
              value: 1100
          }, {
              name: "1200px",
              value: 1200
          }, {
              name: "1300px",
              value: 1300
          }, {
              name: "1400px",
              value: 1400
          }, {
              name: "1500px",
              value: 1500
          }, {
              name: "1600px",
              value: 1600
          }]
      }
  },
  props: ["show"],
  computed: {},
  watch: {
      show: function(e) {
          e && (this.bookConfig = {
              pdfImageWidth: (this.$store.state.showBookInfo.readConfig || {}).pdfImageWidth || 800
          })
      }
  },
  methods: {
      cancel: function() {
          this.$emit("setShow", !1)
      },
      save: function() {
          var e = this;
          axios.post("/book/saveBookConfig", __merge({
              bookUrl: this.$store.state.showBookInfo.bookUrl
          }, this.bookConfig)).then((function(t) {
              t.data.isSuccess && (e.$message.success("更新成功"), e.showBookInfo = t.data.data, e.$store.commit("updateShelfBook", t.data.data))
          }), (function(t) {
              e.$message.error("更新失败" + (t && t.toString()))
          }))
      }
  },
  render: function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "书籍设置",
                        visible: e.show,
                        width: e.dialogSmallWidth,
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
                        model: e.bookConfig
                    }
                }, [t("el-form-item", {
                    attrs: {
                        label: "PDF图片宽度"
                    }
                }, [t("el-select", {
                    staticClass: "setting-select",
                    attrs: {
                        size: "mini",
                        filterable: "",
                        placeholder: "请选择PDF图片宽度"
                    },
                    model: {
                        value: e.bookConfig.pdfImageWidth,
                        callback: function(t) {
                            e.$set(e.bookConfig, "pdfImageWidth", t)
                        },
                        expression: "bookConfig.pdfImageWidth"
                    }
                }, e._l(e.pdfImageWidthList, (function(e, o) {
                    return t("el-option", {
                        key: "pdf-image-width-" + o,
                        attrs: {
                            label: e.name,
                            value: e.value
                        }
                    })
                })), 1)], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("el-button", {
                    attrs: {
                        size: "medium",
                        type: "primary"
                    },
                    on: {
                        click: e.save
                    }
                }, [e._v("保 存")]), t("el-button", {
                    attrs: {
                        size: "medium",
                        type: "primary"
                    },
                    on: {
                        click: e.cancel
                    }
                }, [e._v("关 闭")])], 1)], 1) : e._e()
            },
  staticRenderFns: []
}
</script>
