var render = function() {
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
            };
