<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=ef9d6efc；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' :visible="show" :width="dialogSmallWidth" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }"><div class="custom-dialog-title" slot="title"><span class="el-dialog__title">{{ "RSS订阅(" + _s(rssSourceList.length) + ") " }}<span class="float-right span-btn" @click="(t) => {
                            showRssSourceEditButton = !showRssSourceEditButton
                        }">{{ showRssSourceEditButton ? "取消" : "编辑" }}</span><span class="float-right span-btn" @click="uploadRssSource">导入</span><span class="float-right span-btn" @click="(t) => {
                            return editRssSource(!1)
                        }">新增</span></span><input ref='"rssInputRef"' style="display: none" type="file" @change="(t) => {
                            return onSourceFileChange(t, !0)
                        }" /></div><div class="rss-source-list-container"><template v-for="(o, n) in rssSourceList"><div :key='"rss-" + n' class="rss-source" @click="(t) => {
                                return showRssArticleListDialog(o)
                            }"><template v-if="showRssSourceEditButton"><i class="el-icon-close" @click="(t) => {
                                return t.stopPropagation(), deleteRssSource(o)
                            }" /></template><template v-if="showRssSourceEditButton"><i class="el-icon-edit" @click="(t) => {
                                return t.stopPropagation(), editRssSource(o)
                            }" /></template><el-image class="rss-icon" :src="getImage(o.sourceIcon, !0)" fit="cover" lazy="" /><div class="rss-title">{{ o.sourceName }}</div></div></template></div></el-dialog>
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
  name: 'RssSourceList',
  data: function() {
      return {
          showRssSourceEditButton: !1
      }
  },
  props: ["show", "rssSource"],
  computed: __merge({}, {
      rssSourceList: function() {
          return [].concat(this.$store.state.rssSourceList).sort((function(e, t) {
              return e.customOrder - t.customOrder
          }))
      }
  }),
  watch: {
      show: function(e) {}
  },
  methods: {
      cancel: function() {
          this.$emit("setShow", !1)
      },
      deleteRssSource: function(e) {
          var t = this;
          return asyncToGenerator(regeneratorRuntime.mark((function o() {
              var n;
              return regeneratorRuntime.wrap((function(o) {
                  while (1) switch (o.prev = o.next) {
                      case 0:
                          return o.next = 2, t.$confirm("确认要删除该RSS订阅源吗?", "提示", {
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
                          axios.post(t.api + "/deleteRssSource", e).then((function(e) {
                              e.data.isSuccess && (t.$message.success("删除成功"), t.loadRssSources(!0))
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
      uploadRssSource: function() {
          this.$refs.rssInputRef.dispatchEvent(new MouseEvent("click"))
      },
      onSourceFileChange: function(e, t) {
          eventBus.$emit("onSourceFileChange", e, t)
      },
      showRssArticleListDialog: function(e) {
          eventBus.$emit("showRssArticleListDialog", e)
      },
      editRssSource: function(e) {
          var t = this;
          e = e || {
              sourceName: "新增RSS源",
              sourceUrl: "",
              sourceIcon: "",
              sourceGroup: "",
              enabled: !0,
              singleUrl: !0,
              articleStyle: 0,
              ruleArticles: "",
              ruleTitle: "",
              rulePubDate: "",
              ruleImage: "",
              ruleLink: "",
              ruleContent: "",
              enableJs: !0
          }, eventBus.$emit("showEditor", "编辑RSS源", JSON.stringify(e, null, 4), (function(e, o) {
              try {
                  var n = JSON.parse(e);
                  if (!n.sourceName) return void t.$message.error("RSS源名称不能为空");
                  if (!n.sourceUrl) return void t.$message.error("RSS源链接不能为空");
                  axios.post(t.api + "/saveRssSource", n).then((function(e) {
                      e.data.isSuccess && (o(), t.$message.success("保存RSS源成功"), t.loadRssSources(!0))
                  }), (function(e) {
                      t.$message.error("保存RSS源失败 " + (e && e.toString()))
                  }))
              } catch (r) {
                  t.$message.error("RSS源必须是JSON格式")
              }
          }))
      },
      loadRssSources: function(e) {
          return this.$root.$children[0].loadRssSources(e)
      }
  },
  render: function() {
                var e = this,
                    t = e._self._c;
                return t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        visible: e.show,
                        width: e.dialogSmallWidth,
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
                }, [e._v("RSS订阅(" + e._s(e.rssSourceList.length) + ") "), t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: function(t) {
                            e.showRssSourceEditButton = !e.showRssSourceEditButton
                        }
                    }
                }, [e._v(e._s(e.showRssSourceEditButton ? "取消" : "编辑"))]), t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.uploadRssSource
                    }
                }, [e._v("导入")]), t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: function(t) {
                            return e.editRssSource(!1)
                        }
                    }
                }, [e._v("新增")])]), t("input", {
                    ref: "rssInputRef",
                    staticStyle: {
                        display: "none"
                    },
                    attrs: {
                        type: "file"
                    },
                    on: {
                        change: function(t) {
                            return e.onSourceFileChange(t, !0)
                        }
                    }
                })]), t("div", {
                    staticClass: "rss-source-list-container"
                }, e._l(e.rssSourceList, (function(o, n) {
                    return t("div", {
                        key: "rss-" + n,
                        staticClass: "rss-source",
                        on: {
                            click: function(t) {
                                return e.showRssArticleListDialog(o)
                            }
                        }
                    }, [e.showRssSourceEditButton ? t("i", {
                        staticClass: "el-icon-close",
                        on: {
                            click: function(t) {
                                return t.stopPropagation(), e.deleteRssSource(o)
                            }
                        }
                    }) : e._e(), e.showRssSourceEditButton ? t("i", {
                        staticClass: "el-icon-edit",
                        on: {
                            click: function(t) {
                                return t.stopPropagation(), e.editRssSource(o)
                            }
                        }
                    }) : e._e(), t("el-image", {
                        staticClass: "rss-icon",
                        attrs: {
                            src: e.getImage(o.sourceIcon, !0),
                            fit: "cover",
                            lazy: ""
                        }
                    }), t("div", {
                        staticClass: "rss-title"
                    }, [e._v(e._s(o.sourceName))])], 1)
                })), 0)])
            },
  staticRenderFns: []
}
</script>

<style scoped>
.rss-source-list-container { max-height:calc(var(--vh, 1vh)*70 - 114px);overflow-y:auto }
.rss-source-list-container .rss-source { display:inline-block;width:25%;box-sizing:border-box;padding:10px;text-align:center;vertical-align:top;margin-bottom:10px;cursor:pointer;position:relative }
.rss-source-list-container .rss-source .el-icon-close { position:absolute;right:6px;top:8px;font-size:18px }
.rss-source-list-container .rss-source .el-icon-edit { position:absolute;right:6px;top:42px;font-size:18px }
.rss-source-list-container .rss-source .rss-icon { display:inline-block;width:50px;height:50px;border-radius:5px }
.rss-source-list-container .rss-source .rss-title { margin-top:5px;text-align:center }
</style>
