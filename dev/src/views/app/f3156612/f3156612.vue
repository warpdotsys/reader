<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=f3156612；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog v-if="$store.getters.isNormalPage" :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' title="替换规则" :visible="show" :width="dialogWidth" :top="dialogTop" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }"><el-form :model="ruleForm"><el-form-item label="名称"><el-input v-model="ruleForm.name" /></el-form-item><el-form-item label="规则"><el-input v-model="ruleForm.pattern" /></el-form-item><el-form-item label="替换为"><el-input v-model="ruleForm.replacement" /></el-form-item><el-form-item label="替换范围"><el-input v-model="ruleForm.scope" /></el-form-item><el-checkbox v-model="ruleForm.isRegex">使用正则表达式</el-checkbox><el-checkbox v-model="ruleForm.isEnabled">是否启用</el-checkbox></el-form><div class="dialog-footer" slot="footer"><el-button size="medium" @click="cancel">取 消</el-button><el-button size="medium" type="primary" @click="save">确 定</el-button></div></el-dialog></el-dialog>
</template>

<script>import axios from 'axios'
import { merge as __merge } from '@/utils/merge'

/** 替换规则默认值（产物模块 bfff 的导出 d） */
const DEFAULT_REPLACE_RULE = {
  name: '',
  pattern: '',
  replacement: '',
  scope: '',
  isRegex: !1,
  isEnabled: !0
}

export default {

  model: {
      prop: "show",
      event: "setShow"
  },
  name: 'ReplaceRuleForm',
  data: function() {
      return {
          ruleForm: __merge({}, DEFAULT_REPLACE_RULE)
      }
  },
  props: ["show", "rule", "isAdd"],
  computed: {},
  watch: {
      show: function(e) {
          e && (this.ruleForm = this.rule || __merge({}, DEFAULT_REPLACE_RULE))
      }
  },
  methods: {
      cancel: function() {
          this.$emit("setShow", !1)
      },
      save: function() {
          var e = this;
          if (this.ruleForm.name)
              if (this.ruleForm.pattern)
                  if (this.ruleForm.scope) {
                      if (this.isAdd) {
                          var t = this.$store.state.filterRules.find((function(t) {
                              return t.name === e.ruleForm.name
                          }));
                          if (t) return void this.$message.error("规则名不能重复")
                      }
                      var o = __merge({}, this.ruleForm);
                      axios.post("/saveReplaceRule", o).then((function(t) {
                          t.data.isSuccess && (e.$message.success((e.isAdd ? "新增" : "编辑") + "替换规则成功"), e.$root.$children[0].loadReplaceRules(!0), e.cancel())
                      }), (function(t) {
                          e.$message.error((e.isAdd ? "新增" : "编辑") + "替换规则失败 " + (t && t.toString()))
                      }))
                  } else this.$message.error("替换范围不能为空");
          else this.$message.error("规则不能为空");
          else this.$message.error("规则名不能为空")
      }
  },
  render: function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "替换规则",
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
                        model: e.ruleForm
                    }
                }, [t("el-form-item", {
                    attrs: {
                        label: "名称"
                    }
                }, [t("el-input", {
                    model: {
                        value: e.ruleForm.name,
                        callback: function(t) {
                            e.$set(e.ruleForm, "name", t)
                        },
                        expression: "ruleForm.name"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "规则"
                    }
                }, [t("el-input", {
                    model: {
                        value: e.ruleForm.pattern,
                        callback: function(t) {
                            e.$set(e.ruleForm, "pattern", t)
                        },
                        expression: "ruleForm.pattern"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "替换为"
                    }
                }, [t("el-input", {
                    model: {
                        value: e.ruleForm.replacement,
                        callback: function(t) {
                            e.$set(e.ruleForm, "replacement", t)
                        },
                        expression: "ruleForm.replacement"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "替换范围"
                    }
                }, [t("el-input", {
                    model: {
                        value: e.ruleForm.scope,
                        callback: function(t) {
                            e.$set(e.ruleForm, "scope", t)
                        },
                        expression: "ruleForm.scope"
                    }
                })], 1), t("el-checkbox", {
                    model: {
                        value: e.ruleForm.isRegex,
                        callback: function(t) {
                            e.$set(e.ruleForm, "isRegex", t)
                        },
                        expression: "ruleForm.isRegex"
                    }
                }, [e._v("使用正则表达式")]), t("el-checkbox", {
                    model: {
                        value: e.ruleForm.isEnabled,
                        callback: function(t) {
                            e.$set(e.ruleForm, "isEnabled", t)
                        },
                        expression: "ruleForm.isEnabled"
                    }
                }, [e._v("是否启用")])], 1), t("div", {
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
.float-left { float:left }
</style>
