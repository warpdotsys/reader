<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=3672581d；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' :title="rssSource.sourceName" :visible="show" :width="dialogSmallWidth" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }"><template v-if="sortUrls.length > 1"><el-tabs v-model="sortName" @tab-click="onTabClick"><template v-for="(e, o) in sortUrls"><el-tab-pane :key='"sort-" + o + e.name' :label="e.name" :name="e.name" /></template></el-tabs></template><div ref='"rssArticleListRef"' class="rss-article-list-container"><template v-for="(o, n) in rssArticleList"><div :key='"rss-article-" + n' class="rss-article" @click="(t) => {
                                return getRssArticleContent(o)
                            }"><div class="rss-article-info"><div class="rss-article-title">{{ o.title }}</div><div class="rss-article-date">{{ o.pubDate }}</div></div><template v-if="o.image"><div class="rss-article-image"><div class="image-wrapper"><el-image class="rss-article-img" :src="getCover(o.image, !0, !0)" :preview-src-list="rssArticleImageList" fit="cover" lazy="" @click="(t) => {
                                return t.stopPropagation(), noop.apply(null, arguments)
                            }" /></div></div></template></div></template><div class="load-more-rss" @click="(t) => {
                            hasMoreRssArticles && getRssArticles(page + 1)
                        }">{{ " " + _s(hasMoreRssArticles ? "加载更多" : "没有更多啦") + " " }}</div></div></el-dialog>
</template>

<script>import axios from 'axios'
import { merge as __merge } from '@/utils/merge'
import eventBus from '@/utils/event-bus'

export default {

  model: {
      prop: "show",
      event: "setShow"
  },
  name: 'RssArticleList',
  data: function() {
      return {
          rssArticleList: [],
          sourceUrl: "",
          sortUrls: [],
          sortName: "",
          sortUrl: "",
          hasMoreRssArticles: !0,
          page: 1
      }
  },
  props: ["show", "rssSource"],
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
          if (e) try {
              this.parseSourceUrl(), this.getRssArticles()
          } catch (t) {
              this.$message.error("解析失败: " + t)
          } else this.sortUrls = [], this.rssArticleList = [], this.page = 1, this.sortName = "", this.sortUrl = "", this.hasMoreRssArticles = !0
      }
  },
  methods: {
      cancel: function() {
          this.$emit("setShow", !1)
      },
      parseSourceUrl: function() {
          if (this.sourceUrl = this.rssSource.sourceUrl, !this.rssSource.singleUrl && this.rssSource.sortUrl) {
              var e = [];
              this.rssSource.sortUrl.replace(/\r\n/g, "\n").split("\n").forEach((function(t) {
                  t && (t = t.split("::"), e.push({
                      name: t[0],
                      url: t[1]
                  }))
              })), this.sortUrls = e, this.sortName = e[0].name, this.sortUrl = e[0].url
          }
      },
      onTabClick: function(e) {
          this.sortUrl = this.sortUrls.find((function(t) {
              return t.name === e.name
          })).url, this.page = 1, this.hasMoreRssArticles = !0, this.getRssArticles()
      },
      getRssArticles: function(e) {
          var t = this;
          this.page = e || 1, axios.post(this.api + "/getRssArticles", {
              sourceUrl: this.sourceUrl,
              sortName: this.sortName,
              sortUrl: this.sortUrl,
              page: this.page
          }).then((function(e) {
              if (e.data.isSuccess) {
                  var o = e.data.data.first;
                  if (!o.length) return t.$message.error("没有数据"), void(t.hasMoreRssArticles = !1);
                  t.page > 1 ? t.rssArticleList = [].concat(t.rssArticleList).concat(o) : t.rssArticleList = o
              }
          }), (function(e) {
              t.$message.error("加载RSS文章列表失败 " + (e && e.toString()))
          }))
      },
      noop: function() {},
      showRssArticle: function() {},
      getRssArticleContent: function(e) {
          var t = this;
          axios.post(this.api + "/getRssContent", {
              sourceUrl: this.rssSource.sourceUrl,
              link: e.link,
              origin: e.origin
          }).then((function(t) {
              t.data.isSuccess && eventBus.$emit("showRssArticleDialog", __merge(__merge({}, e), {}, {
                  content: t.data.data
              }))
          }), (function(e) {
              t.$message.error("加载RSS文章内容失败 " + (e && e.toString()))
          }))
      }
  },
  render: function() {
                var e = this,
                    t = e._self._c;
                return t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: e.rssSource.sourceName,
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
                }, [e.sortUrls.length > 1 ? t("el-tabs", {
                    on: {
                        "tab-click": e.onTabClick
                    },
                    model: {
                        value: e.sortName,
                        callback: function(t) {
                            e.sortName = t
                        },
                        expression: "sortName"
                    }
                }, e._l(e.sortUrls, (function(e, o) {
                    return t("el-tab-pane", {
                        key: "sort-" + o + e.name,
                        attrs: {
                            label: e.name,
                            name: e.name
                        }
                    })
                })), 1) : e._e(), t("div", {
                    ref: "rssArticleListRef",
                    staticClass: "rss-article-list-container"
                }, [e._l(e.rssArticleList, (function(o, n) {
                    return t("div", {
                        key: "rss-article-" + n,
                        staticClass: "rss-article",
                        on: {
                            click: function(t) {
                                return e.getRssArticleContent(o)
                            }
                        }
                    }, [t("div", {
                        staticClass: "rss-article-info"
                    }, [t("div", {
                        staticClass: "rss-article-title"
                    }, [e._v(e._s(o.title))]), t("div", {
                        staticClass: "rss-article-date"
                    }, [e._v(e._s(o.pubDate))])]), o.image ? t("div", {
                        staticClass: "rss-article-image"
                    }, [t("div", {
                        staticClass: "image-wrapper"
                    }, [t("el-image", {
                        staticClass: "rss-article-img",
                        attrs: {
                            src: e.getCover(o.image, !0, !0),
                            "preview-src-list": e.rssArticleImageList,
                            fit: "cover",
                            lazy: ""
                        },
                        on: {
                            click: function(t) {
                                return t.stopPropagation(), e.noop.apply(null, arguments)
                            }
                        }
                    })], 1)]) : e._e()])
                })), t("div", {
                    staticClass: "load-more-rss",
                    on: {
                        click: function(t) {
                            e.hasMoreRssArticles && e.getRssArticles(e.page + 1)
                        }
                    }
                }, [e._v(" " + e._s(e.hasMoreRssArticles ? "加载更多" : "没有更多啦") + " ")])], 2)], 1)
            },
  staticRenderFns: []
}
</script>

<style scoped>
.rss-article-list-container { max-height:calc(var(--vh, 1vh)*70 - 114px);overflow-y:auto }
.rss-article-list-container .rss-article { display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;padding:15px 10px;border-bottom:1px solid #eee;cursor:pointer }
.rss-article-list-container .rss-article .rss-article-info { display:-ms-flexbox;display:flex;-ms-flex-direction:column;flex-direction:column;-ms-flex-pack:justify;justify-content:space-between;-ms-flex:1;flex:1;padding-right:5px }
.rss-article-list-container .rss-article .rss-article-info .rss-article-title { font-weight:600;font-size:14px }
.rss-article-list-container .rss-article .rss-article-info .rss-article-date { font-size:12px;margin-top:10px }
.rss-article-list-container .rss-article .rss-article-image { width:120px;display:-ms-flexbox;display:flex;-ms-flex-align:center;align-items:center }
.rss-article-list-container .rss-article .rss-article-image .image-wrapper { width:100%;height:0;padding-bottom:62.5%;overflow:hidden }
.rss-article-list-container .rss-article .rss-article-image .image-wrapper .rss-article-img { width:120px;height:75px }
.rss-article-list-container .load-more-rss { text-align:center;padding:10px;cursor:pointer }
</style>
