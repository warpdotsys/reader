<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=3ee772ca；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog v-if="$store.getters.isNormalPage" :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' title="授权管理" :visible="show" :width="dialogWidth" :top="dialogTop" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }"><div class="custom-dialog-title" slot="title"><span class="el-dialog__title">授权管理 <span class="float-right span-btn" @click="importLicense">更新密钥</span></span></div><div><div class="license-item"><span class="license-name">授权类型</span><span class="license-value">{{ license.type }}</span></div><div class="license-item"><span class="license-name">授权域名</span><span class="license-value">{{ license.host }}</span></div><div class="license-item"><span class="license-name">过期时间</span><span class="license-value">{{ 0 === license.expiredAt ? "永久有效" : new Date(license.expiredAt).format("yy-MM-dd hh:mm") }}</span></div><div class="license-item"><span class="license-name">用户上限</span><span class="license-value">{{ _s(license.userMaxLimit) + " 人" }}</span></div><div class="license-item"><span class="license-name">kindle端</span><span class="license-value">{{ _s(0 === license.simpleWebExpiredAt ? "永久有效" : new Date(license.simpleWebExpiredAt).format("yy-MM-dd hh:mm")) + " 过期" }}</span><span class="span-btn" @click="supplyLicense">申请7天试用</span></div></div></el-dialog></el-dialog>
</template>

<script>import axios from 'axios'
import { merge as __merge } from '@/utils/merge'
import { regeneratorRuntime } from '@/utils/regenerator'
import { asyncToGenerator } from '@/utils/async'

export default {

  model: {
      prop: "show",
      event: "setShow"
  },
  name: 'License',
  data: function() {
      return {
          license: {
              host: "",
              userMaxLimit: 50,
              expiredAt: 0,
              openApi: !1,
              simpleWebExpiredAt: (new Date).getTime()
          }
      }
  },
  computed: {},
  props: ["show"],
  watch: {
      show: function(e) {
          e && this.getLicense()
      }
  },
  methods: {
      cancel: function() {
          this.$emit("setShow", !1)
      },
      getLicense: function() {
          var e = this;
          axios.get(this.api + "/getLicense").then((function(t) {
              t.data.isSuccess && (e.license = t.data.data.license)
          }), (function(t) {
              e.$message.error("获取授权失败 " + (t && t.toString()))
          }))
      },
      supplyLicense: function() {
          var e = this;
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              var o, n;
              return regeneratorRuntime.wrap((function(t) {
                  while (1) switch (t.prev = t.next) {
                      case 0:
                          return t.next = 2, e.$prompt("请输入邮箱进行验证，每个邮箱仅限试用一次，有效期7天", "验证邮箱", {
                              inputValue: "",
                              confirmButtonText: "确定",
                              cancelButtonText: "取消",
                              inputValidator: function(e) {
                                  return !!e || "邮箱不能为空"
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
                          if (n = o.value, n.match(/@(163|126|qq|yahoo|sina|sohu|yeah|139|189|21cn|outlook|gmail|icloud).com$/)) {
                              t.next = 9;
                              break
                          }
                          return e.$message.error("仅支持163|126|qq|yahoo|sina|sohu|yeah|139|189|21cn|outlook|gmail|icloud等邮箱"), t.abrupt("return");
                      case 9:
                          axios.post(bt + "/reader3/sendCodeToEmail", {
                              email: n
                          }).then((function(t) {
                              t.data.isSuccess && e.showVerifyCodePrompt(n)
                          }), (function(t) {
                              e.$message.error("发送验证码失败 " + (t && t.toString()))
                          }));
                      case 10:
                      case "end":
                          return t.stop()
                  }
              }), t)
          })))()
      },
      showVerifyCodePrompt: function(e) {
          var t = this;
          return asyncToGenerator(regeneratorRuntime.mark((function o() {
              var n;
              return regeneratorRuntime.wrap((function(o) {
                  while (1) switch (o.prev = o.next) {
                      case 0:
                          return o.next = 2, t.$prompt("请输入验证码", "验证邮箱", {
                              inputValue: "",
                              confirmButtonText: "确定",
                              cancelButtonText: "取消",
                              inputValidator: function(e) {
                                  return !!e || "验证码不能为空"
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
                          axios.post(bt + "/reader3/supplyLicense", {
                              email: e,
                              code: n.value
                          }, {
                              alert: !1
                          }).then((function(e) {
                              e.data.isSuccess && (t.$message.success("申请试用密钥成功，有效期7天，请谨慎更新"), t.comfirmImport(e.data.data.key))
                          }), (function(e) {
                              t.$message.error("申请试用失败 " + (e && e.toString()))
                          }));
                      case 6:
                      case "end":
                          return o.stop()
                  }
              }), o)
          })))()
      },
      importLicense: function() {
          var e = this;
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              var o;
              return regeneratorRuntime.wrap((function(t) {
                  while (1) switch (t.prev = t.next) {
                      case 0:
                          return t.next = 2, e.$prompt("请输入密钥", "更新密钥", {
                              inputValue: "",
                              confirmButtonText: "确定",
                              cancelButtonText: "取消",
                              inputValidator: function(e) {
                                  return !!e || "密钥不能为空"
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
                          return t.abrupt("return", e.comfirmImport(o.value));
                      case 6:
                      case "end":
                          return t.stop()
                  }
              }), t)
          })))()
      },
      comfirmImport: function(e) {
          var t = this;
          return asyncToGenerator(regeneratorRuntime.mark((function o() {
              var n;
              return regeneratorRuntime.wrap((function(o) {
                  while (1) switch (o.prev = o.next) {
                      case 0:
                          return o.next = 2, t.$confirm("确认要更新密钥吗?", "提示", {
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
                          axios.post(t.api + "/importLicense", {
                              content: e
                          }).then((function(e) {
                              e.data.isSuccess && (t.$message.success("更新密钥成功"), t.license = e.data.data.license)
                          }), (function(e) {
                              t.$message.error("更新密钥失败 " + (e && e.toString()))
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
                        title: "授权管理",
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
                }, [e._v("授权管理 "), t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.importLicense
                    }
                }, [e._v("更新密钥")])])]), t("div", [t("div", {
                    staticClass: "license-item"
                }, [t("span", {
                    staticClass: "license-name"
                }, [e._v("授权类型")]), t("span", {
                    staticClass: "license-value"
                }, [e._v(e._s(e.license.type))])]), t("div", {
                    staticClass: "license-item"
                }, [t("span", {
                    staticClass: "license-name"
                }, [e._v("授权域名")]), t("span", {
                    staticClass: "license-value"
                }, [e._v(e._s(e.license.host))])]), t("div", {
                    staticClass: "license-item"
                }, [t("span", {
                    staticClass: "license-name"
                }, [e._v("过期时间")]), t("span", {
                    staticClass: "license-value"
                }, [e._v(e._s(0 === e.license.expiredAt ? "永久有效" : new Date(e.license.expiredAt).format("yy-MM-dd hh:mm")))])]), t("div", {
                    staticClass: "license-item"
                }, [t("span", {
                    staticClass: "license-name"
                }, [e._v("用户上限")]), t("span", {
                    staticClass: "license-value"
                }, [e._v(e._s(e.license.userMaxLimit) + " 人")])]), t("div", {
                    staticClass: "license-item"
                }, [t("span", {
                    staticClass: "license-name"
                }, [e._v("kindle端")]), t("span", {
                    staticClass: "license-value"
                }, [e._v(e._s(0 === e.license.simpleWebExpiredAt ? "永久有效" : new Date(e.license.simpleWebExpiredAt).format("yy-MM-dd hh:mm")) + " 过期")]), t("span", {
                    staticClass: "span-btn",
                    on: {
                        click: e.supplyLicense
                    }
                }, [e._v("申请7天试用")])])])]) : e._e()
            },
  staticRenderFns: []
}
</script>

<style scoped>
.float-left { float:left }
.license-item { font-size:14px;padding:10px 5px }
.license-item .license-name { display:inline-block;width:65px;margin-right:5px;font-weight:700 }
.license-item .span-btn { display:inline-block;cursor:pointer;font-size:15px;margin-left:10px }
.night-theme .license-item { color:#888 }
</style>
