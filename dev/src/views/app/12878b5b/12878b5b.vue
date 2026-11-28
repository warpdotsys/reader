<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=12878b5b；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' :title="rssArticleInfo.title" :visible="show" :width="dialogSmallWidth" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }"><template v-if="show"><div class="rss-article-info-container"><div ref='"rssArticleContentRef"' class="rss-article-content" @click="rssArticleClickHandler" /></div></template></el-dialog>
</template>

<script>import { merge as __merge } from '@/utils/merge'

export default {

  model: {
      prop: "show",
      event: "setShow"
  },
  name: 'RssArticle',
  data: function() {
      return {
          rssArticleList: [],
          sourceUrl: "",
          sortUrls: [],
          sortName: "",
          hasMoreRssArticles: !0,
          page: 1
      }
  },
  props: ["show", "rssArticleInfo"],
  computed: __merge({}, {
      rssArticleImageList: function() {
          var e = this;
          return this.rssArticleList.filter((function(e) {
              return e.image
          })).map((function(t) {
              return e.getImage(t.image, !0, !0)
          }))
      }
  }),
  watch: {
      show: function(e) {
          e && this.$nextTick(this.runScript)
      }
  },
  methods: {
      cancel: function() {
          this.$emit("setShow", !1)
      },
      rssArticleClickHandler: function(e) {
          if (e.target && e.target.nodeName && "img" === e.target.nodeName.toLowerCase()) {
              var t = this.$refs.rssArticleContentRef.querySelectorAll("img");
              if (t.length) {
                  for (var o = [], n = 0, r = 0; r < t.length; r++) o.push(t[r].src), t[r] === e.target && (n = r);
                  this.$store.commit("setPreviewImageIndex", n), this.$store.commit("setPreviewImgList", o)
              }
          }
      },
      runScript: function() {
          try {
              var e = this.$refs.rssArticleContentRef.querySelectorAll("script");
              if (e.length)
                  for (var t = 0; t < e.length; t++) {
                      var o = e[t].innerText;
                      new Function(o)()
                  }
          } catch (n) {
              this.$message.error("执行脚本出错: " + n)
          }
      }
  },
  render: function() {
                var e = this,
                    t = e._self._c;
                return t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: e.rssArticleInfo.title,
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
                }, [e.show ? t("div", {
                    staticClass: "rss-article-info-container"
                }, [t("div", {
                    ref: "rssArticleContentRef",
                    staticClass: "rss-article-content",
                    domProps: {
                        innerHTML: e._s(e.rssArticleInfo.content || e.rssArticleInfo.description)
                    },
                    on: {
                        click: e.rssArticleClickHandler
                    }
                })]) : e._e()])
            },
  staticRenderFns: []
}
</script>

<style scoped>
.rss-article-info-container { max-height:calc(var(--vh, 1vh)*70 - 114px);overflow-y:auto }
</style>
