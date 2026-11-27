<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=reader scopeId=510c7aa8；无 sourcemap，模板为 render 推断还原 -->
<template>
<div class="popup-wrapper" :style="popupTheme"><div class="title-zone"><div class="title">{{ "书架(" + _s(shelfBooks.length) + ")" }}</div><div :class="{'title-btn': !0, loading: refreshLoading}"><span class="span-btn" @click="(t) => {
                                expand = !expand
                            }">{{ " " + _s(expand ? "收起" : "展开") + " " }}</span><span :class="{loading: refreshLoading}" @click="refreshShelf"><template v-if="refreshLoading"><i class="el-icon-loading" /></template>{{ " " + _s(refreshLoading ? "刷新中..." : "刷新") + " " }}</span></div></div><div ref='"bookList"' class="data-wrapper" :class="{night: $store.getters.isNight, day: !$store.getters.isNight, expand: expand}"><div class="shelfbook-list"><template v-if='shelfBooks.length && "yes" === $store.getters.shelfConfig.virtualOptimize'><BookShelfVirtualList :bookList="shelfBooks" :visible="visible" @changeBook="changeBook" /></template><template v-else><template v-for="n in shelfBooks"><div :key="n.bookUrl" ref='"book"' class="book-item" :class="{selected: isSelected(n)}" @click="(t) => {
                                    return changeBook(n)
                                }"><div class="book-title"><div class="book-name">{{ " " + _s(n.name) + " " }}</div><template v-if="n.totalChapterNum - 1 - n.durChapterIndex"><div class="book-progress">{{ " " + _s(n.totalChapterNum - 1 - n.durChapterIndex) + " " }}</div></template></div><div class="chapter-text">{{ " " + _s(n.durChapterTitle) + " " }}</div></div></template></template></div></div></div>
</template>

<script>
// 依赖的 webpack 模块 id 见同目录 _meta.json 与 docs/FRONTEND-RECONSTRUCTION.md
export default {
  name: "Content",
    data: function() {
    return {
    currentTime: 0,
    audioDuration: 0,
    playing: !1,
    currentSpeed: 1,
    audioVolume: 100,
    startTime: 0,
    iframeStyle: {},
    iframeSize: {
    scrollHeight: 0,
    scrollWidth: 0
    }
    }
    },
    components: {
    ShadowIframe: ft
    },
    props: ["content", "error", "title", "showContent", "isSlideRead", "isScrollRead", "showChapterList", "currentShowChapter", "isEditContent"],
    mounted: function() {
    this.isAudio && this.play(!0), window.contentCom = this, this.loadCustomFontFamil()
    },
    unmounted: function() {
    delete window.contentCom
    },
    computed: {
    readingBook: function() {
    return this.$store.getters.readingBook
    },
    chapter: function() {
    return this.$store.getters.readingBook.catalog[this.$store.getters.readingBook.index] || {}
    },
    show: function() {
    return this.$store.state.showContent
    },
    fontSize: function() {
    return this.$store.getters.config.fontSize + "px"
    },
    autoPlay: {
    get: function() {
    return this.$store.state.autoPlay
    },
    set: function(e) {
    this.$store.commit("setAutoPlay", e)
    }
    },
    isCarToon: function() {
    return !this.error && !this.isEpub && (this.content || "").indexOf("<img") >= 0
    },
    isAudio: function() {
    return !this.error && 1 === this.readingBook.type
    },
    isVideo: function() {
    return !this.error && 4 === this.readingBook.type
    },
    isEpub: function() {
    return !this.error && this.readingBook.bookUrl.toLowerCase().endsWith(".epub")
    },
    isEpubIframe: function() {
    return "iframe" === this.$store.getters.config.epubMode
    },
    isCbz: function() {
    return !this.error && this.readingBook.bookUrl.toLowerCase().endsWith(".cbz")
    },
    isPdf: function() {
    return !this.error && this.readingBook.bookUrl.toLowerCase().endsWith(".pdf")
    },
    containerStyle: function() {
    return Object(i["a"])(Object(i["a"])({
    fontSize: this.$store.getters.config.fontSize + "px",
    fontWeight: this.$store.getters.config.fontWeight || void 0,
    color: this.$store.getters.config.fontColor || (this.$store.getters.isNight ? "#666" : "#262626")
    }, this.$store.getters.currentFontFamily), this.$store.getters.config.contentCSS || {})
    },
    pStyle: function() {
    return {
    lineHeight: this.$store.getters.config.lineHeight,
    marginTop: "undefined" !== typeof this.$store.getters.config.paragraphSpace ? this.$store.getters.config.paragraphSpace + "em" : null,
    marginBottom: "undefined" !== typeof this.$store.getters.config.paragraphSpace ? this.$store.getters.config.paragraphSpace + "em" : null
    }
    },
    windowSize: function() {
    return this.$store.state.windowSize
    },
    currentCustomFontFamily: function() {
    return this.$store.getters.currentCustomFontFamily
    }
    },
    watch: {
    containerStyle: function() {
    this.isEpub && this.isEpubIframe && this.setIframeStyle()
    },
    pStyle: function() {
    this.isEpub && this.isEpubIframe && this.setIframeStyle()
    },
    windowSize: function() {
    this.isEpub
    },
    currentCustomFontFamily: function() {
    this.loadCustomFontFamil()
    },
    isSlideRead: function() {
    this.isEpub && this.isEpubIframe && (this.iframeStyle = {}, this.setIframeStyle(), this.isSlideRead ? this.syncIframeWidth() : this.syncIframeHeight(), this.sendToIframe("execute", {
    script: 'document.body.style.transform="translateX(0px)";'
    }))
    },
    showContent: {
    handler: function(e) {
    e && this.isVideo && this.initDplayer()
    },
    immediate: !0
    }
    },
    methods: {
    renderScrollChapterList: function() {
    var e = this,
    t = this.$createElement;
    return t("div", {
    class: "content-body",
    style: this.containerStyle,
    directives: [{
    name: "lazy-container",
    value: {
    selector: "img"
    }
    }]
    }, [this.showChapterList.map((function(n) {
    if (n.isVolume) return t("div", {
    class: "content-body chapter-content reading-chapter volume-chapter"
    }, [t("div", {
    class: "volume-content"
    }, [t("h3", {
    attrs: {
    "data-pos": 0
    }
    }, [n.title]), t("p", {
    class: "volume-tag"
    }, [n.content])])]);
    var a = n.title.length + 2;
    return t("div", {
    class: ["chapter-content", e.readingBook.index === n.index ? "reading-chapter" : ""],
    attrs: {
    "data-index": n.index
    }
    }, [e.isCbz || e.isPdf ? null : t("h3", {
    attrs: {
    "data-pos": 0
    }
    }, [n.title]), n.content.split(/\n+/).map((function(n) {
    if (n = n.replace(/^\s+/g, ""), !n) return null;
    var u = a;
    return a += n.length + 2, n.indexOf("<img") >= 0 ? (n = n.replace(/src=/g, "data-src=").replace("__API_ROOT__", e.$store.getters.apiRoot), t("div", {
    style: e.containerStyle,
    domProps: {
    innerHTML: n
    },
    attrs: {
    "data-pos": u
    }
    })) : t("p", {
    style: e.pStyle,
    domProps: {
    innerHTML: n
    },
    attrs: {
    "data-pos": u
    }
    })
    }))])
    }))])
    },
    renderAudio: function() {
    var e = this,
    t = this.$createElement;
    return t("div", {
    class: "content-audio"
    }, [t("audio", {
    ref: "audio",
    attrs: {
    preload: "preload",
    src: this.content
    },
    on: {
    loadMetaData: this.audioEvent,
    progress: this.onProgress,
    playing: this.onProgress,
    timeupdate: this.onTimeupdate,
    play: this.onPlay,
    pause: this.onPause,
    ended: this.onEnd,
    error: this.onError,
    seeked: this.onSeeked,
    seeking: this.onSeeking,
    stalled: this.audioEvent,
    suspend: this.onsuspend,
    loadeddata: this.audioEvent,
    loadedmetadata: this.computeDuration,
    durationchange: this.computeDuration,
    canplay: this.onCanPlay,
    canplaythrough: this.audioEvent,
    waiting: this.onWaiting
    }
    }), t("div", {
    class: "book-cover"
    }, [t("img", {
    directives: [{
    name: "lazy",
    value: this.getCover(this.readingBook.coverUrl)
    }]
    })]), t("div", {
    class: "book-progress"
    }, [t("div", {
    class: "progress-tip"
    }, [this.formatTime(this.currentTime)]), t("div", {
    class: "progress-container"
    }, [t("el-slider", {
    attrs: {
    min: 0,
    max: this.audioDuration,
    "show-tooltip": !1
    },
    model: {
    value: e.currentTime,
    callback: function(t) {
    e.currentTime = t
    }
    },
    on: {
    change: function(t) {
    e.seekTime(t)
    }
    }
    })]), t("div", {
    class: "progress-tip total-time"
    }, [this.formatTime(this.audioDuration)])]), t("div", {
    class: "book-operation"
    }, [t("i", {
    class: "reader-iconfont reader-icon-jian15s",
    on: {
    click: function(t) {
    return t.stopPropagation(), t.preventDefault(),
    function() {
    e.seekTime(e.$refs.audio.currentTime - 15)
    }()
    }
    }
    }), t("i", {
    class: "reader-iconfont reader-icon-player-backward-step",
    on: {
    click: function(t) {
    return t.stopPropagation(), t.preventDefault(), e.prevChapter(t)
    }
    }
    }), t("i", {
    class: ["reader-iconfont", this.playing ? "reader-icon-player-play" : "reader-icon-player-pause"],
    on: {
    click: function(t) {
    return t.stopPropagation(), t.preventDefault(), e.toggle(t)
    }
    }
    }), t("i", {
    class: "reader-iconfont reader-icon-player-forward-step",
    on: {
    click: function(t) {
    return t.stopPropagation(), t.preventDefault(), e.nextChapter(t)
    }
    }
    }), t("i", {
    class: "reader-iconfont reader-icon-15s",
    on: {
    click: function(t) {
    return t.stopPropagation(), t.preventDefault(),
    function() {
    e.seekTime(e.$refs.audio.currentTime + 15)
    }()
    }
    }
    })]), t("div", {
    class: "book-operation"
    }, [t("span", {
    style: {
    display: "flex",
    flexDirection: "row",
    alignItems: "center"
    }
    }, [t("i", {
    class: ["reader-iconfont", this.audioVolume > 0 ? "reader-icon-volume" : "reader-icon-volume-off"],
    style: {
    marginRight: this.audioVolume > 0 ? "15px" : "25px"
    },
    on: {
    click: function(t) {
    return t.stopPropagation(), t.preventDefault(),
    function() {
    e.setAudioVolume(e.audioVolume > 0 ? 0 : 100)
    }()
    }
    }
    }), t("el-slider", {
    attrs: {
    min: 0,
    max: 100,
    "show-tooltip": !1
    },
    style: {
    width: "180px"
    },
    model: {
    value: e.audioVolume,
    callback: function(t) {
    e.audioVolume = t
    }
    },
    on: {
    change: function(t) {
    e.setAudioVolume(t)
    }
    }
    })])]), t("div", {
    class: "book-info",
    style: {
    background: this.getCover(this.readingBook.coverUrl, !0)
    }
    }, [t("div", {
    class: "book-cover"
    }, [t("img", {
    directives: [{
    name: "lazy",
    value: this.getCover(this.readingBook.coverUrl)
    }]
    })]), t("div", {
    class: "book-intro"
    }, [t("div", {
    class: "title"
    }, [this.title]), t("div", {
    class: "subtitle"
    }, [this.readingBook.name, this.readingBook.author ? "•" : "", this.readingBook.author])])])])
    },
    renderVideo: function() {
    var e = this.$createElement;
    return e("div", {
    ref: "dplayer"
    })
    },
    initDplayer: function() {
    var e = this;
    if (this.$refs.dplayer) {
    this.dplayer && this.dplayer.destroy();
    var t = {
    video: {
    url: this.content
    }
    };
    try {
    t = JSON.parse(this.content), "string" === typeof t && (t = {
    video: {
    url: this.content
    }
    })
    } catch (n) {}
    this.dplayer = new Ct.a(Object(i["a"])(Object(i["a"])({
    container: this.$refs.dplayer
    }, t), {}, {
    preventClickToggle: !0,
    autoplay: !0
    })), this.dplayer.on("durationchange", (function() {
    e.startTime && (e.dplayer.seek(e.startTime), e.startTime = null)
    })), this.dplayer.on("timeupdate", (function() {
    e.currentTime = 0 | e.dplayer.video.currentTime, e.$emit("updateProgress")
    }))
    } else setTimeout((function() {
    e.initDplayer()
    }), 50)
    },
    renderEpub: function() {
    var e = this,
    t = this.$createElement;
    if (!this.content.startsWith("/book-assets")) return null;
    var n = this.content;
    return n.startsWith("/") || (n = "/" + n), t(ft, {
    class: "epub-iframe",
    ref: "iframe",
    style: this.iframeStyle,
    attrs: {
    src: this.$store.getters.apiRoot + n,
    renderType: this.isEpubIframe ? "iframe" : "shadowDom"
    },
    on: {
    iframeEvent: function(t, n) {
    e.iframeEvent(t, n)
    }
    }
    })
    },
    iframeEvent: function(e, t) {
    var n = this;
    switch (e) {
    case "inited":
    this.iframeStyle = {}, this.setIframeStyle(), this.isSlideRead ? this.syncIframeWidth() : this.syncIframeHeight();
    break;
    case "load":
    setTimeout((function() {
    n.$emit("iframeLoad"), n.$emit("epubLocationChange", t)
    }), 100);
    break;
    case "setHeight":
    this.iframeSize.scrollHeight = t, this.isSlideRead || (this.iframeStyle = Object(i["a"])(Object(i["a"])({}, this.iframeStyle), {}, {
    height: Math.max(t, .8 * this.windowSize.height) + "px"
    }), this.$emit("contentChange"));
    break;
    case "setWidth":
    this.iframeSize.scrollWidth = t, this.isSlideRead && this.$emit("contentChange");
    break;
    case "click":
    this.$emit("epubClick", t);
    break;
    case "clickHash":
    this.$emit("epubClickHash", t);
    break;
    case "keydown":
    this.$emit("epubKeydown", t);
    break;
    case "previewImageList":
    this.$store.commit("setPreviewImageIndex", t.imageIndex), this.$store.commit("setPreviewImgList", t.imageList);
    break;
    case "clickA":
    this.$emit("epubLocationChange", t);
    break;
    case "touchstart":
    case "touchmove":
    case "touchend":
    this.$emit("epubTouch", {
    event: e,
    data: t
    });
    break;
    default:
    break
    }
    },
    syncIframeHeight: function() {
    var e = this;
    this.ensure((function() {
    return e.$refs.iframe.syncIframeHeight()
    }), (function() {
    return e.$refs.iframe
    }))
    },
    syncIframeWidth: function() {
    var e = this;
    this.ensure((function() {
    return e.$refs.iframe.syncIframeWidth()
    }), (function() {
    return e.$refs.iframe
    }))
    },
    setIframeStyle: function() {
    var e = this;
    this.$root.$children[0].setDocumentProperty();
    var t = "";
    for (var n in this.containerStyle) Object.hasOwnProperty.call(this.containerStyle, n) && (t += n.replace(/([A-Z])/g, (function(e) {
    return "-" + e.toLowerCase()
    })) + ":" + this.containerStyle[n] + " !important;");
    var a = "";
    this.isSlideRead && (t += "\n          height: 100%;\n          ".concat(this.isEpubIframe ? "overflow: hidden;" : "", "\n          columns: calc(100vw - 16px - var(--horizontal-padding, 0px)) 1;\n          column-gap: calc(16px + var(--horizontal-padding, 0px));\n        "), a += "\n          break-inside: avoid;\n        ");
    var u = "";
    for (var o in this.pStyle) Object.hasOwnProperty.call(this.pStyle, o) && (u += o.replace(/([A-Z])/g, (function(e) {
    return "-" + e.toLowerCase()
    })) + ":" + this.pStyle[o] + " !important;");
    u += "font-family: " + this.containerStyle.fontFamily + " !important;", u += "font-size: " + this.containerStyle.fontSize + " !important;", u += "font-weight: " + this.containerStyle.fontWeight + " !important;", u += "color: " + this.containerStyle.color + " !important;";
    var r = "";
    r += "font-family: " + this.containerStyle.fontFamily + " !important;", r += "font-weight: " + this.containerStyle.fontWeight + " !important;", r += "color: " + this.containerStyle.color + " !important;";
    var i = "";
    this.currentCustomFontFamily && (i = '\n        @font-face {\n          font-family: "'.concat(this.currentCustomFontFamily.name, '";\n          src: url("').concat(this.currentCustomFontFamily.url, '");\n        }'));
    var s = "\n        ".concat(i, "\n        *::-webkit-scrollbar {\n          display: none;\n          width: 0 !important;\n          height: 0 !important;\n        }\n        *:focus {\n          outline: none !important;\n        }\n        html {\n          ").concat(this.isSlideRead ? "height: 100%;" : "", "\n          min-height: 100%;\n        }\n        body {\n          margin: 0 !important;\n          ").concat(t, "\n        }\n        .reading {\n          color: red !important;\n        }\n        body p {\n          ").concat(u, "\n        }\n        body h1, body h2, body h3, body h4 {\n          ").concat(r, "\n        }\n        img, body img {\n          display: block;\n          max-width: ").concat(this.isEpubIframe ? "100vw" : "100%", " !important;\n          height: auto !important;\n          ").concat(a, "\n        }");
    this.ensure((function() {
    return e.$refs.iframe.setIframeStyle(s)
    }), (function() {
    return e.$refs.iframe
    }))
    },
    ensure: function(e, t) {
    var n = this;
    t && t() ? e && e() : setTimeout((function() {
    n.ensure(e, t)
    }), 10)
    },
    transformX: function(e) {
    var t = this;
    this.ensure((function() {
    return t.$refs.iframe.transformX(e)
    }), (function() {
    return t.$refs.iframe
    }))
    },
    syncCSSProperty: function() {
    var e = this;
    this.ensure((function() {
    return e.$refs.iframe.syncCSSProperty()
    }), (function() {
    return e.$refs.iframe
    }))
    },
    formatTime: function(e) {
    if (!e) return "00:00";
    var t = function(e) {
    return e >= 10 ? "" + e : "0" + e
    };
    if (e < 60) return "00:" + t(e);
    if (e < 3600) {
    var n = Math.round(e / 60),
    a = e % 60;
    return t(n) + ":" + t(a)
    }
    var u = Math.round(e / 3600),
    o = Math.round(e / 3600 / 60),
    r = e % 60;
    return t(u) + ":" + t(o) + ":" + t(r)
    },
    seekTime: function(e) {
    isNaN(e) || e === 1 / 0 || this.$refs.audio && (this.$refs.audio.currentTime = e)
    },
    setAudioVolume: function(e) {
    isNaN(e) || e === 1 / 0 || (this.audioVolume = e, window.audioVolume = e, this.$refs.audio && (this.$refs.audio.volume = e / 100))
    },
    ensureSeekTime: function(e) {
    this.startTime = e
    },
    toggle: function() {
    this.playing ? this.$refs.audio && this.$refs.audio.pause() : this.play()
    },
    play: function(e) {
    var t = this;
    this.$refs.audio ? (e && (this.$refs.audio.load(), this.computeDuration()), e && !this.autoPlay || this.$refs.audio.play()) : setTimeout((function() {
    t.play(e)
    }), 10)
    },
    prevChapter: function() {
    this.autoPlay = !0, this.$emit("prevChapter")
    },
    nextChapter: function() {
    this.autoPlay = !0, this.$emit("nextChapter")
    },
    computeDuration: function() {
    var e = this;
    if (this.$refs.audio) {
    var t = this.$refs.audio.duration;
    this.$refs.audio.readyState >= 1 && !isNaN(t) && t !== 1 / 0 && t ? (this.audioDuration = parseInt(t), this.$refs.audio.playbackRate = this.currentSpeed, this.$refs.audio.currentTime = this.startTime, this.autoPlay && this.$refs.audio.play(), window.audioVolume && this.setAudioVolume(window.audioVolume)) : setTimeout((function() {
    e.computeDuration()
    }), 50)
    } else setTimeout((function() {
    e.computeDuration()
    }), 100)
    },
    onProgress: function() {},
    onTimeupdate: function() {
    this.$refs.audio && (this.currentTime = 0 | this.$refs.audio.currentTime), this.$emit("updateProgress")
    },
    onPlay: function() {
    this.playing = !0
    },
    onPause: function() {
    this.playing = !1
    },
    onEnd: function() {
    this.playing = !1, this.currentTime = 0, this.audioDuration = 0, this.autoPlay = !0, this.$emit("nextChapter")
    },
    onError: function(e) {
    this.$message.error(e.toString()), this.playing = !1
    },
    onSeeked: function() {},
    onSeeking: function() {},
    audioEvent: function() {},
    onsuspend: function() {},
    onCanPlay: function() {},
    onWaiting: function() {},
    loadCustomFontFamil: function() {
    this.currentCustomFontFamily && (Object(N["f"])(this.currentCustomFontFamily.name, this.currentCustomFontFamily.url), this.isEpub && this.isEpubIframe && this.setIframeStyle())
    }
    },
  render: function() {
                    var e = this,
                        t = e._self._c;
                    return t("div", {
                        staticClass: "popup-wrapper",
                        style: e.popupTheme
                    }, [t("div", {
                        staticClass: "title-zone"
                    }, [t("div", {
                        staticClass: "title"
                    }, [e._v("书架(" + e._s(e.shelfBooks.length) + ")")]), t("div", {
                        class: {
                            "title-btn": !0,
                            loading: e.refreshLoading
                        }
                    }, [t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: function(t) {
                                e.expand = !e.expand
                            }
                        }
                    }, [e._v(" " + e._s(e.expand ? "收起" : "展开") + " ")]), t("span", {
                        class: {
                            loading: e.refreshLoading
                        },
                        on: {
                            click: e.refreshShelf
                        }
                    }, [e.refreshLoading ? t("i", {
                        staticClass: "el-icon-loading"
                    }) : e._e(), e._v(" " + e._s(e.refreshLoading ? "刷新中..." : "刷新") + " ")])])]), t("div", {
                        ref: "bookList",
                        staticClass: "data-wrapper",
                        class: {
                            night: e.$store.getters.isNight,
                            day: !e.$store.getters.isNight,
                            expand: e.expand
                        }
                    }, [t("div", {
                        staticClass: "shelfbook-list"
                    }, [e.shelfBooks.length && "yes" === e.$store.getters.shelfConfig.virtualOptimize ? t("BookShelfVirtualList", {
                        attrs: {
                            bookList: e.shelfBooks,
                            visible: e.visible
                        },
                        on: {
                            changeBook: e.changeBook
                        }
                    }) : e._l(e.shelfBooks, (function(n) {
                        return t("div", {
                            key: n.bookUrl,
                            ref: "book",
                            refInFor: !0,
                            staticClass: "book-item",
                            class: {
                                selected: e.isSelected(n)
                            },
                            on: {
                                click: function(t) {
                                    return e.changeBook(n)
                                }
                            }
                        }, [t("div", {
                            staticClass: "book-title"
                        }, [t("div", {
                            staticClass: "book-name"
                        }, [e._v(" " + e._s(n.name) + " ")]), n.totalChapterNum - 1 - n.durChapterIndex ? t("div", {
                            staticClass: "book-progress"
                        }, [e._v(" " + e._s(n.totalChapterNum - 1 - n.durChapterIndex) + " ")]) : e._e()]), t("div", {
                            staticClass: "chapter-text"
                        }, [e._v(" " + e._s(n.durChapterTitle) + " ")])])
                    }))], 2)])])
                },
  staticRenderFns: []
}
</script>

<style scoped>
.popup-wrapper { margin:-16px;margin-bottom:-13px;padding:24px;padding-top:calc(24px + constant(safe-area-inset-top));padding-top:calc(24px + env(safe-area-inset-top)) }
.popup-wrapper .title-zone { margin:0 0 20px 0;width:100%;display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-pack:justify;justify-content:space-between }
.popup-wrapper .title { font-size:18px;font-weight:400;font-family:-apple-system,Noto Sans,Helvetica Neue,Helvetica,Nimbus Sans L,Arial,Liberation Sans,PingFang SC,Hiragino Sans GB,Noto Sans CJK SC,Source Han Sans SC,Source Han Sans CN,Microsoft YaHei,Wenquanyi Micro Hei,WenQuanYi Zen Hei,ST Heiti,SimHei,WenQuanYi Zen Hei Sharp,sans-serif;color:#ed4259;border-bottom:1px solid #ed4259;width:-moz-fit-content;width:fit-content }
.popup-wrapper .title-btn { font-size:14px;line-height:26px;color:#ed4259;cursor:pointer }
.popup-wrapper .title-btn .home-btn { display:inline-block;margin-right:25px }
.popup-wrapper .title-btn.loading { color:#606266 }
.popup-wrapper .data-wrapper { height:300px;overflow:auto }
.popup-wrapper .data-wrapper .shelfbook-list { height:100% }
.popup-wrapper .data-wrapper .shelfbook-list .book-item { width:100%;cursor:pointer;display:-ms-flexbox;display:flex;-ms-flex-direction:column;flex-direction:column;max-width:100%;overflow:hidden;padding:8px 0 }
.popup-wrapper .data-wrapper .shelfbook-list .book-item .book-title { display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-pack:justify;justify-content:space-between;-ms-flex-align:center;align-items:center }
.popup-wrapper .data-wrapper .shelfbook-list .book-item .book-title .book-name { font-size:16px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis }
.popup-wrapper .data-wrapper .shelfbook-list .book-item .book-title .book-progress { float:right;font-size:12px }
.popup-wrapper .data-wrapper .shelfbook-list .book-item .chapter-text { overflow:hidden;white-space:nowrap;text-overflow:ellipsis;color:#888;font-size:14px;margin-top:6px }
.popup-wrapper .data-wrapper .shelfbook-list .book-item.selected .book-name { color:#eb4259 }
.popup-wrapper .data-wrapper.expand { height:85vh }
.popup-wrapper .data-wrapper::-webkit-scrollbar { width:0!important }
.popup-wrapper .night .shelfbook-list .book-item { border-bottom:1px solid #333 }
.popup-wrapper .night .shelfbook-list .book-item .book-title { color:#888 }
.popup-wrapper .night .shelfbook-list .book-item .chapter-text { color:#555 }
.popup-wrapper .day .book-item { border-bottom:1px solid #eee }
</style>
