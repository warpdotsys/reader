<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=7e126aa8；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog v-if="$store.getters.isNormalPage" :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' :title='isAdd ? "新增用户" : "修改用户"' :visible="show" :width="dialogSmallWidth" :top="dialogTop" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }"><el-form :model="userForm"><el-form-item label="用户名"><el-input autocomplete="off" :readonly="!isAdd" v-model="userForm.username" /></el-form-item><template v-if="isAdd"><el-form-item label="密码"><el-input type="password" autocomplete="off" show-password="" v-model="userForm.password" /></el-form-item></template><el-form-item label="书籍上限"><el-input-number min="1" v-model="userForm.bookLimit" /></el-form-item><el-form-item label="书源上限"><el-input-number min="1" v-model="userForm.bookSourceLimit" /></el-form-item><el-checkbox v-model="userForm.enableWebdav">启用webdav</el-checkbox><el-checkbox v-model="userForm.enableLocalStore">启用书仓</el-checkbox><el-checkbox v-model="userForm.enableBookSource">编辑书源</el-checkbox><el-checkbox v-model="userForm.enableRssSource">编辑RSS源</el-checkbox></el-form><div class="dialog-footer" slot="footer"><el-button size="medium" @click="cancel">取 消</el-button><el-button size="medium" type="primary" @click="save">确 定</el-button></div></el-dialog></el-dialog>
</template>

<script>import axios from 'axios'
import { merge as __merge } from '@/utils/merge'

// 用户表单默认值（产物里是变量 re，模块内常量）
const USER_FORM_DEFAULT = {
  username: '',
  password: '',
  bookLimit: 200,
  bookSourceLimit: 100,
  enableWebdav: false,
  enableLocalStore: false,
  enableBookSource: true,
  enableRssSource: true,
}

export default {

  model: {
      prop: "show",
      event: "setShow"
  },
  name: 'UserForm',
  data: function() {
      return {
          isAdd: !0,
          userForm: __merge({}, USER_FORM_DEFAULT)
      }
  },
  props: ["show", "user"],
  computed: {},
  watch: {
      show: function(e) {
          e && (this.user && this.user.username ? (this.userForm = __merge(__merge({}, USER_FORM_DEFAULT), this.user), this.isAdd = !1) : (this.userForm = __merge({}, USER_FORM_DEFAULT), this.isAdd = !0))
      }
  },
  methods: {
      cancel: function() {
          this.$emit("setShow", !1)
      },
      save: function() {
          var e = this;
          this.userForm.username ? !this.isAdd || this.userForm.password ? axios.post(this.api + (this.isAdd ? "/addUser" : "/updateUser"), this.userForm).then((function(t) {
              if (t.data.isSuccess) {
                  e.$message.success("操作成功"), e.cancel();
                  var o = t.data.data.map((function(e) {
                      return __merge(__merge({}, e), {}, {
                          userNS: e.username
                      })
                  }));
                  e.$store.commit("setUserList", o)
              }
          }), (function(t) {
              e.$message.error("操作失败 " + (t && t.toString()))
          })) : this.$message.success("密码不能为空") : this.$message.success("用户名不能为空")
      }
  },
  render: function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: e.isAdd ? "新增用户" : "修改用户",
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
                        model: e.userForm
                    }
                }, [t("el-form-item", {
                    attrs: {
                        label: "用户名"
                    }
                }, [t("el-input", {
                    attrs: {
                        autocomplete: "off",
                        readonly: !e.isAdd
                    },
                    model: {
                        value: e.userForm.username,
                        callback: function(t) {
                            e.$set(e.userForm, "username", t)
                        },
                        expression: "userForm.username"
                    }
                })], 1), e.isAdd ? t("el-form-item", {
                    attrs: {
                        label: "密码"
                    }
                }, [t("el-input", {
                    attrs: {
                        type: "password",
                        autocomplete: "off",
                        "show-password": ""
                    },
                    model: {
                        value: e.userForm.password,
                        callback: function(t) {
                            e.$set(e.userForm, "password", t)
                        },
                        expression: "userForm.password"
                    }
                })], 1) : e._e(), t("el-form-item", {
                    attrs: {
                        label: "书籍上限"
                    }
                }, [t("el-input-number", {
                    attrs: {
                        min: 1
                    },
                    model: {
                        value: e.userForm.bookLimit,
                        callback: function(t) {
                            e.$set(e.userForm, "bookLimit", t)
                        },
                        expression: "userForm.bookLimit"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "书源上限"
                    }
                }, [t("el-input-number", {
                    attrs: {
                        min: 1
                    },
                    model: {
                        value: e.userForm.bookSourceLimit,
                        callback: function(t) {
                            e.$set(e.userForm, "bookSourceLimit", t)
                        },
                        expression: "userForm.bookSourceLimit"
                    }
                })], 1), t("el-checkbox", {
                    model: {
                        value: e.userForm.enableWebdav,
                        callback: function(t) {
                            e.$set(e.userForm, "enableWebdav", t)
                        },
                        expression: "userForm.enableWebdav"
                    }
                }, [e._v("启用webdav")]), t("el-checkbox", {
                    model: {
                        value: e.userForm.enableLocalStore,
                        callback: function(t) {
                            e.$set(e.userForm, "enableLocalStore", t)
                        },
                        expression: "userForm.enableLocalStore"
                    }
                }, [e._v("启用书仓")]), t("el-checkbox", {
                    model: {
                        value: e.userForm.enableBookSource,
                        callback: function(t) {
                            e.$set(e.userForm, "enableBookSource", t)
                        },
                        expression: "userForm.enableBookSource"
                    }
                }, [e._v("编辑书源")]), t("el-checkbox", {
                    model: {
                        value: e.userForm.enableRssSource,
                        callback: function(t) {
                            e.$set(e.userForm, "enableRssSource", t)
                        },
                        expression: "userForm.enableRssSource"
                    }
                }, [e._v("编辑RSS源")])], 1), t("div", {
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
