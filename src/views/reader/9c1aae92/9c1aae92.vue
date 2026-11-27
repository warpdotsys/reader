<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=reader scopeId=9c1aae92；无 sourcemap，模板为 render 推断还原 -->
<template>
<div ref='"chapterWrapperRef"' class="chapter-wrapper" :class="{night: isNight, day: !isNight, 'mini-interface': $store.state.miniInterface}" :style="bodyTheme"><div class="tool-bar" :style="leftBarTheme"><div class="tools"><div class="tool-icon" @click="toShelf"><div class="iconfont">  </div><div class="icon-text">首页</div></div><el-popover placement="right" :width="popperWidth" trigger="click" :visible-arrow="false" popper-class="popper-component" v-model="popBookShelfVisible"><BookShelf ref='"popBookShelf"' class="popup" :visible="popBookShelfVisible" @changeBook="changeBook" @toShelf="toShelf" /><div class="tool-icon" slot="reference"><div class="iconfont">  </div><div class="icon-text">书架</div></div></el-popover><el-popover placement="right" :width="popperWidth" trigger="click" :visible-arrow="false" popper-class="popper-component" v-model="popBookSourceVisible"><BookSource ref='"popBookSource"' class="popup" :visible="popBookSourceVisible" @changeBookSource="(t) => {
                                return changeBookSource()
                            }" @close="(t) => {
                                popBookSourceVisible = !1
                            }" /><div class="tool-icon" slot="reference"><div class="tool-el-icon"><i class="el-icon-menu" /></div><div class="icon-text">书源</div></div></el-popover><el-popover placement="right" :width="popperWidth" trigger="click" :visible-arrow="false" popper-class="popper-component" v-model="popCataVisible"><PopCata ref='"popCata"' class="popup" :visible="popCataVisible" @gotoChapter="gotoChapter" @refresh="refreshCatalog" @close="(t) => {
                                popCataVisible = !1
                            }" /><div class="tool-icon" slot="reference"><div class="iconfont">  </div><div class="icon-text">目录</div></div></el-popover><el-popover placement="right" :width="popperWidth" trigger="click" :visible-arrow="false" popper-class="popper-component" v-model="readSettingsVisible"><ReadSettings class="popup" :visible="readSettingsVisible" @close="(t) => {
                                readSettingsVisible = !1
                            }" @showClickZone="(t) => {
                                showClickZone = !0
                            }" @readMethodChange="beforeReadMethodChange" /><div class="tool-icon" slot="reference"><div class="iconfont">  </div><div class="icon-text">设置</div></div></el-popover><template v-if="!($store.state.miniInterface)"><div class="tool-icon" @click="(t) => {
                                return toTop(0)
                            }"><div class="iconfont">  </div><div class="icon-text">顶部</div></div></template><template v-if="!($store.state.miniInterface)"><div class="tool-icon" @click="(t) => {
                                return toBottom(0)
                            }"><div class="iconfont">  </div><div class="icon-text">底部</div></div></template></div></div><div class="read-bar" :style="rightBarTheme"><div class="float-btn-zone"><div class="float-left-btn-zone"><template v-if="!(isAudio || isVideo)"><div class="float-btn" :style="popupAbsoluteBtnStyle" @click="showBookmarkDialog"><i class="el-icon-collection-tag" /></div></template><template v-if="!(isAudio || isVideo)"><div class="float-btn" :style="popupAbsoluteBtnStyle" @click="showSearchBookContentDialog"><i class="el-icon-search" /></div></template><div class="float-btn" :style="popupAbsoluteBtnStyle" @click="showReadingBookInfo"><i class="el-icon-info" /></div><template v-if="!(!$store.state.miniInterface || isSlideRead || isAudio || isVideo)"><div class="float-btn" :style="popupAbsoluteBtnStyle" @click="(t) => {
                                return toTop(0)
                            }"><i class="el-icon-top" /></div></template><template v-if="!(!$store.state.miniInterface || isSlideRead || isAudio || isVideo)"><div class="float-btn" :style="popupAbsoluteBtnStyle" @click="(t) => {
                                return toBottom(0)
                            }"><i class="el-icon-bottom" /></div></template></div><div class="float-right-btn-zone"><template v-if="!(isAudio || isVideo || isEpub || isCbz || isPdf)"><div class="float-btn" :class='isEditContent ? "editing" : ""' style="line-height: 36px" :style="popupAbsoluteBtnStyle" @click="(t) => {
                                return toggleEditContent()
                            }"><i class="el-icon-edit" /></div></template><template v-if="!(isAudio || isVideo || isEpub)"><div class="float-btn" style="line-height: 36px" :style="popupAbsoluteBtnStyle" @click="showCacheContent"><i class="el-icon-download" /></div></template><template v-if="isPdf"><div class="float-btn" style="line-height: 36px" :style="popupAbsoluteBtnStyle" @click="readOriginal"><i class="el-icon-reading" /></div></template><div class="float-btn" :style="popupAbsoluteBtnStyle" @click="refreshContent"><i class="el-icon-refresh-right" /></div><template v-if="!(isAudio || isVideo)"><div class="float-btn" :class='autoReading ? "auto-reading" : ""' :style="popupAbsoluteBtnStyle" @click="(t) => {
                                return toggleAutoReading()
                            }"><i class="el-icon-view" /></div></template><template v-if="!(isAudio || isVideo)"><div class="float-btn" :style="popupAbsoluteBtnStyle" @click="(t) => {
                                showReadBar = !showReadBar
                            }"><i class="el-icon-headset" /></div></template><div class="float-btn" :style="popupAbsoluteBtnStyle" @click="toogleNight"><template v-if="isNight"><i class="el-icon-sunny" /></template><template v-else><i class="el-icon-moon" /></template></div></div></div><template v-if="!(!$store.state.miniInterface || isAudio || isVideo)"><div class="progress"><div class="progress-bar"><el-slider min="1" :max="totalPages" :show-tooltip="false" v-model="currentPage" @change="showPage" @input="(t) => {
                                progressValue = t
                            }" /></div><span class="progress-tip">{{ formatProgressTip() }}</span></div></template><template v-if="showCacheContentZone"><div class="cache-content-zone"><div> 缓存章节 </div><div class="cache-content-btn" v-show="!isCachingContent" @click="(t) => {
                                return cacheChapterContent(50)
                            }"> 后面50章 </div><div class="cache-content-btn" v-show="!isCachingContent" @click="(t) => {
                                return cacheChapterContent(100)
                            }"> 后面100章 </div><div class="cache-content-btn" v-show="!isCachingContent" @click="(t) => {
                                return cacheChapterContent(!0)
                            }"> 后面全部 </div><div class="caching-tip" v-show="isCachingContent">{{ " " + _s(cachingContentTip) + " " }}</div><div class="caching-cancel-btn" v-show="isCachingContent" @click="cancelCaching"><i class="el-icon-close" /></div></div></template><div class="tools"><div class="tool-icon progress-text" @click="showCacheContent"><template v-if="$store.state.miniInterface"><span>阅读进度: </span></template>{{ " " + _s(readingProgress) + " " }}</div><div class="tool-icon" :style="$store.state.miniInterface ? {
                            order: -1
                        } : {}" @click="(t) => {
                                return toLastChapter()
                            }"><div class="iconfont">  </div><template v-if="$store.state.miniInterface"><span>上一章</span></template></div><div class="tool-icon" @click="(t) => {
                                return toNextChapter()
                            }"><template v-if="$store.state.miniInterface"><span>下一章</span></template><div class="iconfont">  </div></div></div></div><div class="read-bar" :style="readBarTheme"><div class="reader-bar-inner"><div class="operate-bar"><div class="close-btn" @click="exitRead"><i class="el-icon-close" /></div><div class="center"><span class="ctrl-btn" @click="speechPrev">上一段</span><span class="play-pause-btn" @click="toggleSpeech"><template v-if="speechSpeaking"><i class="el-icon-video-pause" :style="popupAbsoluteBtnStyle" /></template><template v-else><i class="el-icon-video-play" :style="popupAbsoluteBtnStyle" /></template></span><span class="ctrl-btn" @click="speechNext">下一段</span></div><div class="collapse-btn" @click="(t) => {
                                showSpeechConfig = !showSpeechConfig
                            }"><template v-if="showSpeechConfig"><i class="el-icon-bottom" /></template><template v-else><i class="el-icon-top" /></template></div></div><template v-if="showSpeechConfig"><div class="setting-item"><div class="setting-title"> 朗读方式 <span class="float-right" @click="showHttpTTSDialog">HttpTTS 管理</span></div><div class="setting-value"><div class="voice-list"><el-select class="voice-select" size="mini" filterable="" placeholder="请选择朗读方式" v-model="ttsType"><template v-for="(e, n) in ttsTypeList"><el-option :key='"tts-type-" + n' :label="e.name" :value="e.value" /></template></el-select></div></div><template v-if='"local" !== ttsType'><div class="setting-oneline"><span class="setting-title">连读优化：</span><el-switch active-color="#13ce66" inactive-color="#ff4949" :active-value="true" :inactive-value="false" v-model="cacheTTSAudio" /></div></template><div class="setting-title"> 语音库 </div><div class="setting-value"><div class="voice-list"><el-select class="voice-select" size="mini" filterable="" placeholder="请选择语音库" v-model="voiceName"><template v-for="(e, n) in voiceList"><el-option :key='"search-type-" + n' :label="e.LocalName || e.name" :value="e.name" /></template></el-select></div></div></div></template><template v-if="showSpeechConfig"><div class="setting-item"><div class="setting-title">语音设置</div><div class="setting-value"><div class="progress"><span class="progress-tip">语速</span><div class="progress-bar"><el-slider min="0.5" max="2" step="0.1" :show-tooltip="false" v-model="speechRate" @change="changeSpeechRate" /></div><span class="setting-btn" @click="(t) => {
                                return changeSpeechRate(1)
                            }">重置</span></div><div class="progress" v-show='"httpTTS" !== ttsType'><span class="progress-tip">语调</span><div class="progress-bar"><el-slider min="0" max="2" step="0.1" :show-tooltip="false" v-model="speechPitch" @change="changeSpeechPitch" /></div><span class="setting-btn" @click="(t) => {
                                return changeSpeechPitch(1)
                            }">重置</span></div><div class="progress"><span class="progress-tip">定时</span><div class="progress-bar"><el-slider min="0" max="180" step="1" :show-tooltip="false" v-model="speechMinutes" @change="changeSpeechMinutes" /></div><span class="setting-btn">{{ _s(speechMinutes) + "分钟" }}</span></div></div></div></template></div></div><div ref='"content"' class="chapter" :class="chapterClass" :style="chapterTheme"><template v-if="showClickZone"><div class="click-zone" :style='isSlideRead ? {} : {
                            position: "fixed"
                        }'><div :style="showPrevPageStyle"><span>点击前一页</span></div><div :style="showMenuZoneStyle"><span>点击显示菜单</span></div><div :style="showNextPageStyle"><span>点击后一页</span></div><div class="close-btn" @click="(t) => {
                                showClickZone = !1
                            }">关闭</div><div :style="showPrevPageExtraStyle" /><div :style="showNextPageExtraStyle" /></div></template><div ref='"top"' class="top-bar">{{ " " + _s($store.state.miniInterface ? title : "") + " " }}<template v-if="$store.state.miniInterface && !isSlideRead"><span class="right-timestr">{{ timeStr }}</span></template></div><div class="content" @touchstart="handleTouchStart" @touchmove="handleTouchMove" @touchend="handleTouchEnd" @click="handlerClick"><template v-if="show"><div class="content-inner" :style="contentInnerStyle"><Content ref='"bookContentRef"' class="book-content" :style="contentStyle" :title="title" :content="content" :showContent="show" :error="error" :showChapterList="showChapterList" :isScrollRead="isScrollRead" :isSlideRead="isSlideRead" :isEditContent="isEditContent" @prevChapter="toLastChapter" @nextChapter="toNextChapter" @updateProgress="saveReadingPosition" @iframeLoad='(t) => {
                                return $emit("iframeLoad")
                            }' @contentChange="(t) => {
                                return onContentChange()
                            }" @epubClick="eventHandler" @epubLocationChange="epubLocationChangeHandler" @epubClickHash="epubClickHash" @epubKeydown="(t) => {
                                return keydownHandler(t, !0)
                            }" @epubTouch="epubTouch" /></div></template></div><div ref='"bottom"' class="bottom-bar"><audio ref='"ttsAudio"' preload="preload" src="" autoplay="" @ended="onTTSAudioEnded" @error="onTTSAudioError" @play="onTTSAudioPlay" @pause="onTTSAudioPause" /><template v-if="isSlideRead"><span>{{ "第".concat(currentPage, "/").concat(totalPages, "页 ").concat(readingProgress) }}</span></template><template v-if="isSlideRead"><span>{{ timeStr }}</span></template><template v-if="!(!show || isSlideRead || error || isScrollRead)"><span class="bottom-btn" @click="(t) => {
                                return toNextChapter()
                            }">加载下一章</span></template></div></div></div>
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
                        ref: "chapterWrapperRef",
                        staticClass: "chapter-wrapper",
                        class: {
                            night: e.isNight,
                            day: !e.isNight,
                            "mini-interface": e.$store.state.miniInterface
                        },
                        style: e.bodyTheme
                    }, [t("div", {
                        staticClass: "tool-bar",
                        style: e.leftBarTheme
                    }, [t("div", {
                        staticClass: "tools"
                    }, [t("div", {
                        staticClass: "tool-icon",
                        on: {
                            click: e.toShelf
                        }
                    }, [t("div", {
                        staticClass: "iconfont"
                    }, [e._v("  ")]), t("div", {
                        staticClass: "icon-text"
                    }, [e._v("首页")])]), t("el-popover", {
                        attrs: {
                            placement: "right",
                            width: e.popperWidth,
                            trigger: "click",
                            "visible-arrow": !1,
                            "popper-class": "popper-component"
                        },
                        model: {
                            value: e.popBookShelfVisible,
                            callback: function(t) {
                                e.popBookShelfVisible = t
                            },
                            expression: "popBookShelfVisible"
                        }
                    }, [t("BookShelf", {
                        ref: "popBookShelf",
                        staticClass: "popup",
                        attrs: {
                            visible: e.popBookShelfVisible
                        },
                        on: {
                            changeBook: e.changeBook,
                            toShelf: e.toShelf
                        }
                    }), t("div", {
                        staticClass: "tool-icon",
                        attrs: {
                            slot: "reference"
                        },
                        slot: "reference"
                    }, [t("div", {
                        staticClass: "iconfont"
                    }, [e._v("  ")]), t("div", {
                        staticClass: "icon-text"
                    }, [e._v("书架")])])], 1), t("el-popover", {
                        attrs: {
                            placement: "right",
                            width: e.popperWidth,
                            trigger: "click",
                            "visible-arrow": !1,
                            "popper-class": "popper-component"
                        },
                        model: {
                            value: e.popBookSourceVisible,
                            callback: function(t) {
                                e.popBookSourceVisible = t
                            },
                            expression: "popBookSourceVisible"
                        }
                    }, [t("BookSource", {
                        ref: "popBookSource",
                        staticClass: "popup",
                        attrs: {
                            visible: e.popBookSourceVisible
                        },
                        on: {
                            changeBookSource: function(t) {
                                return e.changeBookSource()
                            },
                            close: function(t) {
                                e.popBookSourceVisible = !1
                            }
                        }
                    }), t("div", {
                        staticClass: "tool-icon",
                        attrs: {
                            slot: "reference"
                        },
                        slot: "reference"
                    }, [t("div", {
                        staticClass: "tool-el-icon"
                    }, [t("i", {
                        staticClass: "el-icon-menu"
                    })]), t("div", {
                        staticClass: "icon-text"
                    }, [e._v("书源")])])], 1), t("el-popover", {
                        attrs: {
                            placement: "right",
                            width: e.popperWidth,
                            trigger: "click",
                            "visible-arrow": !1,
                            "popper-class": "popper-component"
                        },
                        model: {
                            value: e.popCataVisible,
                            callback: function(t) {
                                e.popCataVisible = t
                            },
                            expression: "popCataVisible"
                        }
                    }, [t("PopCata", {
                        ref: "popCata",
                        staticClass: "popup",
                        attrs: {
                            visible: e.popCataVisible
                        },
                        on: {
                            gotoChapter: e.gotoChapter,
                            refresh: e.refreshCatalog,
                            close: function(t) {
                                e.popCataVisible = !1
                            }
                        }
                    }), t("div", {
                        staticClass: "tool-icon",
                        attrs: {
                            slot: "reference"
                        },
                        slot: "reference"
                    }, [t("div", {
                        staticClass: "iconfont"
                    }, [e._v("  ")]), t("div", {
                        staticClass: "icon-text"
                    }, [e._v("目录")])])], 1), t("el-popover", {
                        attrs: {
                            placement: "right",
                            width: e.popperWidth,
                            trigger: "click",
                            "visible-arrow": !1,
                            "popper-class": "popper-component"
                        },
                        model: {
                            value: e.readSettingsVisible,
                            callback: function(t) {
                                e.readSettingsVisible = t
                            },
                            expression: "readSettingsVisible"
                        }
                    }, [t("ReadSettings", {
                        staticClass: "popup",
                        attrs: {
                            visible: e.readSettingsVisible
                        },
                        on: {
                            close: function(t) {
                                e.readSettingsVisible = !1
                            },
                            showClickZone: function(t) {
                                e.showClickZone = !0
                            },
                            readMethodChange: e.beforeReadMethodChange
                        }
                    }), t("div", {
                        staticClass: "tool-icon",
                        attrs: {
                            slot: "reference"
                        },
                        slot: "reference"
                    }, [t("div", {
                        staticClass: "iconfont"
                    }, [e._v("  ")]), t("div", {
                        staticClass: "icon-text"
                    }, [e._v("设置")])])], 1), e.$store.state.miniInterface ? e._e() : t("div", {
                        staticClass: "tool-icon",
                        on: {
                            click: function(t) {
                                return e.toTop(0)
                            }
                        }
                    }, [t("div", {
                        staticClass: "iconfont"
                    }, [e._v("  ")]), t("div", {
                        staticClass: "icon-text"
                    }, [e._v("顶部")])]), e.$store.state.miniInterface ? e._e() : t("div", {
                        staticClass: "tool-icon",
                        on: {
                            click: function(t) {
                                return e.toBottom(0)
                            }
                        }
                    }, [t("div", {
                        staticClass: "iconfont"
                    }, [e._v("  ")]), t("div", {
                        staticClass: "icon-text"
                    }, [e._v("底部")])])], 1)]), t("div", {
                        staticClass: "read-bar",
                        style: e.rightBarTheme
                    }, [t("div", {
                        staticClass: "float-btn-zone"
                    }, [t("div", {
                        staticClass: "float-left-btn-zone"
                    }, [e.isAudio || e.isVideo ? e._e() : t("div", {
                        staticClass: "float-btn",
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: e.showBookmarkDialog
                        }
                    }, [t("i", {
                        staticClass: "el-icon-collection-tag"
                    })]), e.isAudio || e.isVideo ? e._e() : t("div", {
                        staticClass: "float-btn",
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: e.showSearchBookContentDialog
                        }
                    }, [t("i", {
                        staticClass: "el-icon-search"
                    })]), t("div", {
                        staticClass: "float-btn",
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: e.showReadingBookInfo
                        }
                    }, [t("i", {
                        staticClass: "el-icon-info"
                    })]), !e.$store.state.miniInterface || e.isSlideRead || e.isAudio || e.isVideo ? e._e() : t("div", {
                        staticClass: "float-btn",
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: function(t) {
                                return e.toTop(0)
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-top"
                    })]), !e.$store.state.miniInterface || e.isSlideRead || e.isAudio || e.isVideo ? e._e() : t("div", {
                        staticClass: "float-btn",
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: function(t) {
                                return e.toBottom(0)
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-bottom"
                    })])]), t("div", {
                        staticClass: "float-right-btn-zone"
                    }, [e.isAudio || e.isVideo || e.isEpub || e.isCbz || e.isPdf ? e._e() : t("div", {
                        staticClass: "float-btn",
                        class: e.isEditContent ? "editing" : "",
                        staticStyle: {
                            "line-height": "36px"
                        },
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: function(t) {
                                return e.toggleEditContent()
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-edit"
                    })]), e.isAudio || e.isVideo || e.isEpub ? e._e() : t("div", {
                        staticClass: "float-btn",
                        staticStyle: {
                            "line-height": "36px"
                        },
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: e.showCacheContent
                        }
                    }, [t("i", {
                        staticClass: "el-icon-download"
                    })]), e.isPdf ? t("div", {
                        staticClass: "float-btn",
                        staticStyle: {
                            "line-height": "36px"
                        },
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: e.readOriginal
                        }
                    }, [t("i", {
                        staticClass: "el-icon-reading"
                    })]) : e._e(), t("div", {
                        staticClass: "float-btn",
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: e.refreshContent
                        }
                    }, [t("i", {
                        staticClass: "el-icon-refresh-right"
                    })]), e.isAudio || e.isVideo ? e._e() : t("div", {
                        staticClass: "float-btn",
                        class: e.autoReading ? "auto-reading" : "",
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: function(t) {
                                return e.toggleAutoReading()
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-view"
                    })]), e.isAudio || e.isVideo ? e._e() : t("div", {
                        staticClass: "float-btn",
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: function(t) {
                                e.showReadBar = !e.showReadBar
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-headset"
                    })]), t("div", {
                        staticClass: "float-btn",
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: e.toogleNight
                        }
                    }, [e.isNight ? t("i", {
                        staticClass: "el-icon-sunny"
                    }) : t("i", {
                        staticClass: "el-icon-moon"
                    })])])]), !e.$store.state.miniInterface || e.isAudio || e.isVideo ? e._e() : t("div", {
                        staticClass: "progress"
                    }, [t("div", {
                        staticClass: "progress-bar"
                    }, [t("el-slider", {
                        attrs: {
                            min: 1,
                            max: e.totalPages,
                            "show-tooltip": !1
                        },
                        on: {
                            change: e.showPage,
                            input: function(t) {
                                e.progressValue = t
                            }
                        },
                        model: {
                            value: e.currentPage,
                            callback: function(t) {
                                e.currentPage = t
                            },
                            expression: "currentPage"
                        }
                    })], 1), t("span", {
                        staticClass: "progress-tip"
                    }, [e._v(e._s(e.formatProgressTip()))])]), e.showCacheContentZone ? t("div", {
                        staticClass: "cache-content-zone"
                    }, [t("div", [e._v(" 缓存章节 ")]), t("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: !e.isCachingContent,
                            expression: "!isCachingContent"
                        }],
                        staticClass: "cache-content-btn",
                        on: {
                            click: function(t) {
                                return e.cacheChapterContent(50)
                            }
                        }
                    }, [e._v(" 后面50章 ")]), t("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: !e.isCachingContent,
                            expression: "!isCachingContent"
                        }],
                        staticClass: "cache-content-btn",
                        on: {
                            click: function(t) {
                                return e.cacheChapterContent(100)
                            }
                        }
                    }, [e._v(" 后面100章 ")]), t("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: !e.isCachingContent,
                            expression: "!isCachingContent"
                        }],
                        staticClass: "cache-content-btn",
                        on: {
                            click: function(t) {
                                return e.cacheChapterContent(!0)
                            }
                        }
                    }, [e._v(" 后面全部 ")]), t("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: e.isCachingContent,
                            expression: "isCachingContent"
                        }],
                        staticClass: "caching-tip"
                    }, [e._v(" " + e._s(e.cachingContentTip) + " ")]), t("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: e.isCachingContent,
                            expression: "isCachingContent"
                        }],
                        staticClass: "caching-cancel-btn",
                        on: {
                            click: e.cancelCaching
                        }
                    }, [t("i", {
                        staticClass: "el-icon-close"
                    })])]) : e._e(), t("div", {
                        staticClass: "tools"
                    }, [t("div", {
                        staticClass: "tool-icon progress-text",
                        on: {
                            click: e.showCacheContent
                        }
                    }, [e.$store.state.miniInterface ? t("span", [e._v("阅读进度: ")]) : e._e(), e._v(" " + e._s(e.readingProgress) + " ")]), t("div", {
                        staticClass: "tool-icon",
                        style: e.$store.state.miniInterface ? {
                            order: -1
                        } : {},
                        on: {
                            click: function(t) {
                                return e.toLastChapter()
                            }
                        }
                    }, [t("div", {
                        staticClass: "iconfont"
                    }, [e._v("  ")]), e.$store.state.miniInterface ? t("span", [e._v("上一章")]) : e._e()]), t("div", {
                        staticClass: "tool-icon",
                        on: {
                            click: function(t) {
                                return e.toNextChapter()
                            }
                        }
                    }, [e.$store.state.miniInterface ? t("span", [e._v("下一章")]) : e._e(), t("div", {
                        staticClass: "iconfont"
                    }, [e._v("  ")])])])]), t("div", {
                        staticClass: "read-bar",
                        style: e.readBarTheme
                    }, [t("div", {
                        staticClass: "reader-bar-inner"
                    }, [t("div", {
                        staticClass: "operate-bar"
                    }, [t("div", {
                        staticClass: "close-btn",
                        on: {
                            click: e.exitRead
                        }
                    }, [t("i", {
                        staticClass: "el-icon-close"
                    })]), t("div", {
                        staticClass: "center"
                    }, [t("span", {
                        staticClass: "ctrl-btn",
                        on: {
                            click: e.speechPrev
                        }
                    }, [e._v("上一段")]), t("span", {
                        staticClass: "play-pause-btn",
                        on: {
                            click: e.toggleSpeech
                        }
                    }, [e.speechSpeaking ? t("i", {
                        staticClass: "el-icon-video-pause",
                        style: e.popupAbsoluteBtnStyle
                    }) : t("i", {
                        staticClass: "el-icon-video-play",
                        style: e.popupAbsoluteBtnStyle
                    })]), t("span", {
                        staticClass: "ctrl-btn",
                        on: {
                            click: e.speechNext
                        }
                    }, [e._v("下一段")])]), t("div", {
                        staticClass: "collapse-btn",
                        on: {
                            click: function(t) {
                                e.showSpeechConfig = !e.showSpeechConfig
                            }
                        }
                    }, [e.showSpeechConfig ? t("i", {
                        staticClass: "el-icon-bottom"
                    }) : t("i", {
                        staticClass: "el-icon-top"
                    })])]), e.showSpeechConfig ? t("div", {
                        staticClass: "setting-item"
                    }, [t("div", {
                        staticClass: "setting-title"
                    }, [e._v(" 朗读方式 "), t("span", {
                        staticClass: "float-right",
                        on: {
                            click: e.showHttpTTSDialog
                        }
                    }, [e._v("HttpTTS 管理")])]), t("div", {
                        staticClass: "setting-value"
                    }, [t("div", {
                        staticClass: "voice-list"
                    }, [t("el-select", {
                        staticClass: "voice-select",
                        attrs: {
                            size: "mini",
                            filterable: "",
                            placeholder: "请选择朗读方式"
                        },
                        model: {
                            value: e.ttsType,
                            callback: function(t) {
                                e.ttsType = t
                            },
                            expression: "ttsType"
                        }
                    }, e._l(e.ttsTypeList, (function(e, n) {
                        return t("el-option", {
                            key: "tts-type-" + n,
                            attrs: {
                                label: e.name,
                                value: e.value
                            }
                        })
                    })), 1)], 1)]), "local" !== e.ttsType ? t("div", {
                        staticClass: "setting-oneline"
                    }, [t("span", {
                        staticClass: "setting-title"
                    }, [e._v("连读优化：")]), t("el-switch", {
                        attrs: {
                            "active-color": "#13ce66",
                            "inactive-color": "#ff4949",
                            "active-value": !0,
                            "inactive-value": !1
                        },
                        model: {
                            value: e.cacheTTSAudio,
                            callback: function(t) {
                                e.cacheTTSAudio = t
                            },
                            expression: "cacheTTSAudio"
                        }
                    })], 1) : e._e(), t("div", {
                        staticClass: "setting-title"
                    }, [e._v(" 语音库 ")]), t("div", {
                        staticClass: "setting-value"
                    }, [t("div", {
                        staticClass: "voice-list"
                    }, [t("el-select", {
                        staticClass: "voice-select",
                        attrs: {
                            size: "mini",
                            filterable: "",
                            placeholder: "请选择语音库"
                        },
                        model: {
                            value: e.voiceName,
                            callback: function(t) {
                                e.voiceName = t
                            },
                            expression: "voiceName"
                        }
                    }, e._l(e.voiceList, (function(e, n) {
                        return t("el-option", {
                            key: "search-type-" + n,
                            attrs: {
                                label: e.LocalName || e.name,
                                value: e.name
                            }
                        })
                    })), 1)], 1)])]) : e._e(), e.showSpeechConfig ? t("div", {
                        staticClass: "setting-item"
                    }, [t("div", {
                        staticClass: "setting-title"
                    }, [e._v("语音设置")]), t("div", {
                        staticClass: "setting-value"
                    }, [t("div", {
                        staticClass: "progress"
                    }, [t("span", {
                        staticClass: "progress-tip"
                    }, [e._v("语速")]), t("div", {
                        staticClass: "progress-bar"
                    }, [t("el-slider", {
                        attrs: {
                            min: .5,
                            max: 2,
                            step: .1,
                            "show-tooltip": !1
                        },
                        on: {
                            change: e.changeSpeechRate
                        },
                        model: {
                            value: e.speechRate,
                            callback: function(t) {
                                e.speechRate = t
                            },
                            expression: "speechRate"
                        }
                    })], 1), t("span", {
                        staticClass: "setting-btn",
                        on: {
                            click: function(t) {
                                return e.changeSpeechRate(1)
                            }
                        }
                    }, [e._v("重置")])]), t("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: "httpTTS" !== e.ttsType,
                            expression: "ttsType !== 'httpTTS'"
                        }],
                        staticClass: "progress"
                    }, [t("span", {
                        staticClass: "progress-tip"
                    }, [e._v("语调")]), t("div", {
                        staticClass: "progress-bar"
                    }, [t("el-slider", {
                        attrs: {
                            min: 0,
                            max: 2,
                            step: .1,
                            "show-tooltip": !1
                        },
                        on: {
                            change: e.changeSpeechPitch
                        },
                        model: {
                            value: e.speechPitch,
                            callback: function(t) {
                                e.speechPitch = t
                            },
                            expression: "speechPitch"
                        }
                    })], 1), t("span", {
                        staticClass: "setting-btn",
                        on: {
                            click: function(t) {
                                return e.changeSpeechPitch(1)
                            }
                        }
                    }, [e._v("重置")])]), t("div", {
                        staticClass: "progress"
                    }, [t("span", {
                        staticClass: "progress-tip"
                    }, [e._v("定时")]), t("div", {
                        staticClass: "progress-bar"
                    }, [t("el-slider", {
                        attrs: {
                            min: 0,
                            max: 180,
                            step: 1,
                            "show-tooltip": !1
                        },
                        on: {
                            change: e.changeSpeechMinutes
                        },
                        model: {
                            value: e.speechMinutes,
                            callback: function(t) {
                                e.speechMinutes = t
                            },
                            expression: "speechMinutes"
                        }
                    })], 1), t("span", {
                        staticClass: "setting-btn"
                    }, [e._v(e._s(e.speechMinutes) + "分钟")])])])]) : e._e()])]), t("div", {
                        ref: "content",
                        staticClass: "chapter",
                        class: e.chapterClass,
                        style: e.chapterTheme
                    }, [e.showClickZone ? t("div", {
                        staticClass: "click-zone",
                        style: e.isSlideRead ? {} : {
                            position: "fixed"
                        }
                    }, [t("div", {
                        style: e.showPrevPageStyle
                    }, [t("span", [e._v("点击前一页")])]), t("div", {
                        style: e.showMenuZoneStyle
                    }, [t("span", [e._v("点击显示菜单")])]), t("div", {
                        style: e.showNextPageStyle
                    }, [t("span", [e._v("点击后一页")])]), t("div", {
                        staticClass: "close-btn",
                        on: {
                            click: function(t) {
                                e.showClickZone = !1
                            }
                        }
                    }, [e._v("关闭")]), t("div", {
                        style: e.showPrevPageExtraStyle
                    }), t("div", {
                        style: e.showNextPageExtraStyle
                    })]) : e._e(), t("div", {
                        ref: "top",
                        staticClass: "top-bar"
                    }, [e._v(" " + e._s(e.$store.state.miniInterface ? e.title : "") + " "), e.$store.state.miniInterface && !e.isSlideRead ? t("span", {
                        staticClass: "right-timestr"
                    }, [e._v(e._s(e.timeStr))]) : e._e()]), t("div", {
                        staticClass: "content",
                        on: {
                            touchstart: e.handleTouchStart,
                            touchmove: e.handleTouchMove,
                            touchend: e.handleTouchEnd,
                            click: e.handlerClick
                        }
                    }, [e.show ? t("div", {
                        staticClass: "content-inner",
                        style: e.contentInnerStyle
                    }, [t("Content", {
                        ref: "bookContentRef",
                        staticClass: "book-content",
                        style: e.contentStyle,
                        attrs: {
                            title: e.title,
                            content: e.content,
                            showContent: e.show,
                            error: e.error,
                            showChapterList: e.showChapterList,
                            isScrollRead: e.isScrollRead,
                            isSlideRead: e.isSlideRead,
                            isEditContent: e.isEditContent
                        },
                        on: {
                            prevChapter: e.toLastChapter,
                            nextChapter: e.toNextChapter,
                            updateProgress: e.saveReadingPosition,
                            iframeLoad: function(t) {
                                return e.$emit("iframeLoad")
                            },
                            contentChange: function(t) {
                                return e.onContentChange()
                            },
                            epubClick: e.eventHandler,
                            epubLocationChange: e.epubLocationChangeHandler,
                            epubClickHash: e.epubClickHash,
                            epubKeydown: function(t) {
                                return e.keydownHandler(t, !0)
                            },
                            epubTouch: e.epubTouch
                        }
                    })], 1) : e._e()]), t("div", {
                        ref: "bottom",
                        staticClass: "bottom-bar"
                    }, [t("audio", {
                        ref: "ttsAudio",
                        attrs: {
                            preload: "preload",
                            src: "",
                            autoplay: ""
                        },
                        on: {
                            ended: e.onTTSAudioEnded,
                            error: e.onTTSAudioError,
                            play: e.onTTSAudioPlay,
                            pause: e.onTTSAudioPause
                        }
                    }), e.isSlideRead ? t("span", [e._v(e._s("第".concat(e.currentPage, "/").concat(e.totalPages, "页 ").concat(e.readingProgress)))]) : e._e(), e.isSlideRead ? t("span", [e._v(e._s(e.timeStr))]) : e._e(), !e.show || e.isSlideRead || e.error || e.isScrollRead ? e._e() : t("span", {
                        staticClass: "bottom-btn",
                        on: {
                            click: function(t) {
                                return e.toNextChapter()
                            }
                        }
                    }, [e._v("加载下一章")])])])])
                },
  staticRenderFns: []
}
</script>

<style scoped>
.popper-component { margin-left:10px }
.chapter-wrapper { padding:0;-ms-flex-direction:column;flex-direction:column;-ms-flex-align:center;align-items:center }
.chapter-wrapper .no-point { pointer-events:none }
.chapter-wrapper .tool-bar { position:fixed;top:0;padding-top:0;padding-top:constant(safe-area-inset-top)!important;padding-top:env(safe-area-inset-top)!important;left:50%;z-index:2001 }
.chapter-wrapper .tool-bar .tools { display:-ms-flexbox;display:flex;-ms-flex-direction:column;flex-direction:column }
.chapter-wrapper .tool-bar .tools .tool-icon { font-size:18px;width:58px;height:48px;text-align:center;padding-top:12px;cursor:pointer;outline:none }
.chapter-wrapper .tool-bar .tools .tool-icon .iconfont { font-family:iconfont;width:16px;font-size:16px;margin:0 auto;height:22px;line-height:22px;vertical-align:middle }
.chapter-wrapper .tool-bar .tools .tool-icon .tool-el-icon { font-size:18px;line-height:22px;height:22px }
.chapter-wrapper .tool-bar .tools .tool-icon .tool-el-icon i { line-height:22px }
.chapter-wrapper .tool-bar .tools .tool-icon .icon-text { font-size:12px }
.chapter-wrapper .read-bar { position:fixed;bottom:0;padding-bottom:0;padding-bottom:calc(constant(safe-area-inset-bottom)/2)!important;padding-bottom:calc(env(safe-area-inset-bottom)/2)!important;right:50%;z-index:100 }
.chapter-wrapper .read-bar .progress { padding:10px 36px;display:-ms-flexbox;display:flex;-ms-flex-pack:justify;justify-content:space-between;-ms-flex-align:center;align-items:center }
.chapter-wrapper .read-bar .progress .progress-bar { -ms-flex:1;flex:1;padding:0 10px }
.chapter-wrapper .read-bar .progress .progress-tip { font-size:14px;margin-left:5px }
.chapter-wrapper .read-bar .cache-content-zone { padding:10px 36px;display:-ms-flexbox;display:flex;-ms-flex-pack:justify;justify-content:space-between;-ms-flex-align:center;align-items:center;font-size:14px;position:absolute;right:55px;width:300px;background:inherit }
.chapter-wrapper .read-bar .cache-content-zone .cache-content-btn { cursor:pointer }
.chapter-wrapper .read-bar .float-left-btn-zone { position:absolute;bottom:155px;bottom:calc(155px + constant(safe-area-inset-bottom)/2);bottom:calc(155px + env(safe-area-inset-bottom)/2);left:4px;right:auto;display:-ms-flexbox;display:flex;-ms-flex-direction:column;flex-direction:column }
.chapter-wrapper .read-bar .float-left-btn-zone .float-btn { line-height:32px;width:36px;height:36px;border-radius:100%;display:block;cursor:pointer;text-align:center;vertical-align:middle;pointer-events:all;margin-top:20px }
.chapter-wrapper .read-bar .float-left-btn-zone .float-btn .el-icon-bottom, .chapter-wrapper .read-bar .float-left-btn-zone .float-btn .el-icon-collection-tag, .chapter-wrapper .read-bar .float-left-btn-zone .float-btn .el-icon-info, .chapter-wrapper .read-bar .float-left-btn-zone .float-btn .el-icon-search, .chapter-wrapper .read-bar .float-left-btn-zone .float-btn .el-icon-top { line-height:36px }
.chapter-wrapper .read-bar .float-right-btn-zone { position:absolute;bottom:155px;bottom:calc(155px + constant(safe-area-inset-bottom)/2);bottom:calc(155px + env(safe-area-inset-bottom)/2);left:4px;right:auto;display:-ms-flexbox;display:flex;-ms-flex-direction:column;flex-direction:column }
.chapter-wrapper .read-bar .float-right-btn-zone .float-btn { line-height:32px;width:36px;height:36px;border-radius:100%;display:block;cursor:pointer;text-align:center;vertical-align:middle;pointer-events:all;margin-top:20px }
.chapter-wrapper .read-bar .float-right-btn-zone .float-btn .el-icon-headset, .chapter-wrapper .read-bar .float-right-btn-zone .float-btn .el-icon-refresh-right, .chapter-wrapper .read-bar .float-right-btn-zone .float-btn .el-icon-view { line-height:36px }
.chapter-wrapper .read-bar .float-right-btn-zone .float-btn .el-icon-moon { color:#121212;line-height:34px }
.chapter-wrapper .read-bar .float-right-btn-zone .float-btn .el-icon-sunny { color:#666;line-height:34px }
.chapter-wrapper .read-bar .auto-reading, .chapter-wrapper .read-bar .editing { color:red }
.chapter-wrapper .read-bar .tools { display:-ms-flexbox;display:flex;-ms-flex-direction:column;flex-direction:column }
.chapter-wrapper .read-bar .tools .tool-icon { font-size:18px;width:42px;height:31px;padding-top:12px;text-align:center;-ms-flex-align:center;align-items:center;cursor:pointer;outline:none;margin-top:-1px }
.chapter-wrapper .read-bar .tools .tool-icon.progress-text { font-size:16px }
.chapter-wrapper .read-bar .tools .tool-icon .iconfont { font-family:iconfont;width:16px;height:16px;font-size:16px;margin:0 auto 6px }
.chapter-wrapper .read-bar .reader-bar-inner { display:-ms-flexbox;display:flex;-ms-flex-direction:column;flex-direction:column;padding-bottom:10px;padding-bottom:calc(10px + constant(safe-area-inset-top));padding-bottom:calc(10px + env(safe-area-inset-top));padding-left:5px;padding-right:5px }
.chapter-wrapper .read-bar .reader-bar-inner .operate-bar { display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-pack:justify;justify-content:space-between;padding:10px 10px 0 10px;-ms-flex-align:center;align-items:center }
.chapter-wrapper .read-bar .reader-bar-inner .operate-bar .close-btn, .chapter-wrapper .read-bar .reader-bar-inner .operate-bar .collapse-btn { font-size:22px;height:35px;cursor:pointer }
.chapter-wrapper .read-bar .reader-bar-inner .operate-bar .center span { display:inline-block;cursor:pointer }
.chapter-wrapper .read-bar .reader-bar-inner .operate-bar .center .play-pause-btn { font-size:50px;margin-top:-40px }
.chapter-wrapper .read-bar .reader-bar-inner .operate-bar .center .play-pause-btn i { border-radius:100% }
.chapter-wrapper .read-bar .reader-bar-inner .operate-bar .center .ctrl-btn { margin:0 15px }
.chapter-wrapper .read-bar .reader-bar-inner .setting-item { display:-ms-flexbox;display:flex;-ms-flex-direction:column;flex-direction:column;padding:5px 10px }
.chapter-wrapper .read-bar .reader-bar-inner .setting-item .setting-title { font-size:14px;padding:8px 0 }
.chapter-wrapper .read-bar .reader-bar-inner .setting-item .setting-oneline { padding:10px 0 }
.chapter-wrapper .read-bar .reader-bar-inner .setting-item .setting-btn { font-size:14px;cursor:pointer;display:inline-block;margin-left:5px }
.chapter-wrapper .read-bar .reader-bar-inner .setting-item .voice-list { display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;overflow-x:auto;padding:5px 10px }
.chapter-wrapper .read-bar .reader-bar-inner .setting-item .voice-list .radio-group { white-space:nowrap }
.chapter-wrapper .read-bar .reader-bar-inner .setting-item .voice-list .radio-group .radio-button { margin-right:10px }
.chapter-wrapper .read-bar .reader-bar-inner .setting-item .voice-list .radio-group .radio-button .el-radio-button__inner { border-radius:4px 4px 4px 4px }
.chapter-wrapper .read-bar .reader-bar-inner .setting-item .voice-list .voice-select { margin:0 auto;width:240px }
.chapter-wrapper .read-bar .reader-bar-inner .setting-item .progress { padding:5px 10px }
.chapter-wrapper .read-bar .reader-bar-inner .setting-item .progress .progress-tip { margin-left:0;margin-right:5px }
.chapter-wrapper .chapter-bar .el-breadcrumb .item { font-size:14px;color:#606266 }
.chapter-wrapper .chapter { font-family:Microsoft YaHei,PingFangSC-Regular,HelveticaNeue-Light,Helvetica Neue Light,sans-serif;text-align:left;padding:0 65px;min-height:100vh;min-height:calc(var(--vh, 1vh)*100);width:670px;margin:0 auto;background-size:cover;position:relative }
.chapter-wrapper .chapter .el-icon-loading { font-size:36px;color:#b5b5b5 }
.chapter-wrapper .chapter .el-loading-text { font-weight:500;color:#b5b5b5 }
.chapter-wrapper .chapter .click-zone { position:absolute;z-index:120;top:0;bottom:0;left:0;right:0;background:#333;opacity:.8;color:#fff;font-size:14px;pointer-events:none }
.chapter-wrapper .chapter .click-zone div { position:absolute;text-align:center;display:-ms-flexbox;display:flex;-ms-flex-align:center;align-items:center;-ms-flex-pack:center;justify-content:center }
.chapter-wrapper .chapter .click-zone .close-btn { left:0;right:0;bottom:20px;height:45px;line-height:45px;z-index:10;padding:0;cursor:pointer;pointer-events:all }
.chapter-wrapper .chapter .content { font-size:18px;line-height:1.8;overflow:hidden;font-family:Microsoft YaHei,PingFangSC-Regular,HelveticaNeue-Light,Helvetica Neue Light,sans-serif }
.chapter-wrapper .chapter .content .content-inner { min-height:calc(var(--vh, 1vh)*80);padding-bottom:25px;box-sizing:border-box }
.chapter-wrapper .chapter .bottom-bar, .chapter-wrapper .chapter .top-bar { box-sizing:border-box }
.chapter-wrapper .chapter .top-bar { height:44px;padding:10px }
.chapter-wrapper .chapter .top-bar .right-timestr { display:inline-block;float:right }
.chapter-wrapper .chapter .bottom-bar { width:100%;text-align:center;padding-bottom:30px }
.chapter-wrapper .chapter .bottom-bar .bottom-btn { font-size:14px;cursor:pointer;display:inline-block;margin:0 auto;padding:10px 40px;width:80%;box-sizing:border-box }
.chapter-wrapper .chapter.audio .bottom-bar, .chapter-wrapper .chapter.audio .top-bar { display:none }
.chapter-wrapper .chapter.audio .content-inner { height:calc(var(--vh, 1vh)*100);margin-top:0!important;padding-top:0!important;padding-bottom:0!important;display:-ms-flexbox;display:flex;-ms-flex-align:center;align-items:center }
.chapter-wrapper .chapter.video .bottom-bar, .chapter-wrapper .chapter.video .top-bar { display:none }
.chapter-wrapper .chapter.video .content-inner { height:calc(var(--vh, 1vh)*100);margin-top:0!important;padding-top:0!important;padding-bottom:0!important;display:-ms-flexbox;display:flex;-ms-flex-align:center;align-items:center }
.chapter-wrapper .chapter.video .content-inner .book-content { width:100%;height:100% }
.day .popup { box-shadow:0 2px 4px rgba(0,0,0,.12),0 0 6px rgba(0,0,0,.04) }
.day .tool-icon { border:1px solid rgba(0,0,0,.1);margin-top:-1px;color:#000 }
.day .cache-content-zone, .day .progress-tip, .day .tool-icon .icon-text { color:rgba(0,0,0,.4) }
.day .float-left-btn-zone, .day .float-right-btn-zone, .day .reader-bar-inner { color:#121212 }
.day .reader-bar-inner .setting-title { color:rgba(0,0,0,.8) }
.day .reader-bar-inner .setting-value { color:rgba(0,0,0,.4) }
.day .chapter { border:1px solid #d8d8d8;color:#262626 }
.day .bottom-bar, .day .top-bar { color:rgba(0,0,0,.4) }
.day .el-slider__runway { background-color:#fff }
.day .play-pause-btn { color:#409eff }
.night .popup { box-shadow:0 2px 4px rgba(0,0,0,.48),0 0 6px rgba(0,0,0,.16) }
.night .tool-icon { border:1px solid #444;margin-top:-1px;color:#666 }
.night .cache-content-zone, .night .float-left-btn-zone, .night .float-right-btn-zone, .night .progress-tip, .night .reader-bar-inner, .night .tool-icon .icon-text { color:#666 }
.night .chapter { border:1px solid #444;color:#666 }
.night .popper__arrow { background:#666 }
.night .bottom-bar, .night .top-bar { color:#666 }
.night .el-slider__runway { background-color:#282828 }
.night .el-slider__bar { background-color:#185798 }
.night .el-slider__button { border:2px solid #185798;background-color:#282828 }
.night .play-pause-btn { color:#185798 }
.chapter-wrapper .read-bar .float-btn-zone { position:absolute;bottom:135px;left:4px }
.chapter-wrapper .read-bar .float-btn-zone .float-left-btn-zone { position:relative;left:auto;bottom:auto }
.chapter-wrapper .read-bar .float-btn-zone .float-right-btn-zone { position:relative;left:auto;bottom:auto;margin-bottom:20px }
.chapter-wrapper.mini-interface { padding:0;position:relative;height:100% }
.chapter-wrapper.mini-interface .tool-bar { left:0;width:100vw;margin-left:0!important }
.chapter-wrapper.mini-interface .tool-bar .tools { -ms-flex-direction:row;flex-direction:row;-ms-flex-pack:distribute;justify-content:space-around }
.chapter-wrapper.mini-interface .tool-bar .tools .tool-icon { border:none }
.chapter-wrapper.mini-interface .read-bar { right:0;width:100vw;margin-right:0!important }
.chapter-wrapper.mini-interface .read-bar .cache-content-zone { position:relative;width:auto;right:0;background:inherit }
.chapter-wrapper.mini-interface .read-bar .float-btn-zone { position:static;bottom:0;left:0 }
.chapter-wrapper.mini-interface .read-bar .float-left-btn-zone { position:absolute;right:auto;left:20px;bottom:135px }
.chapter-wrapper.mini-interface .read-bar .float-right-btn-zone { position:absolute;left:auto;right:20px;bottom:135px;margin-bottom:0 }
.chapter-wrapper.mini-interface .read-bar .tools { -ms-flex-direction:row;flex-direction:row;-ms-flex-pack:distribute;justify-content:space-around;padding:0 15px;height:45px }
.chapter-wrapper.mini-interface .read-bar .tools .tool-icon { border:none;width:auto;padding:0;height:45px;line-height:45px }
.chapter-wrapper.mini-interface .read-bar .tools .tool-icon .iconfont { display:inline-block }
.chapter-wrapper.mini-interface .read-bar .tools .tool-icon span { vertical-align:middle }
.chapter-wrapper.mini-interface .chapter { width:100vw!important;padding:0 16px;padding-left:calc(16px + var(--horizontal-padding, 0px));padding-right:calc(16px + var(--horizontal-padding, 0px));box-sizing:border-box;border:none;text-align:justify;position:relative }
.chapter-wrapper.mini-interface .chapter .top-bar { position:fixed;top:0;left:0;width:100vw;z-index:50;background:inherit;height:calc(30px + var(--top-padding, 0px));height:calc(30px + constant(safe-area-inset-top) + var(--top-padding, 0px));height:calc(30px + env(safe-area-inset-top) + var(--top-padding, 0px));padding:6px 16px;padding-left:calc(16px + var(--horizontal-padding, 0px));padding-right:calc(16px + var(--horizontal-padding, 0px));padding-top:calc(6px + constant(safe-area-inset-top) + var(--top-padding, 0px));padding-top:calc(6px + env(safe-area-inset-top) + var(--top-padding, 0px));font-size:12px }
.chapter-wrapper.mini-interface .chapter .content-inner { margin-top:30px;margin-top:calc(30px + var(--top-padding, 0px));margin-top:calc(30px + constant(safe-area-inset-top) + var(--top-padding, 0px));margin-top:calc(30px + env(safe-area-inset-top) + var(--top-padding, 0px));padding-top:15px;padding-bottom:15px }
.chapter-wrapper.mini-interface .chapter.cartoon { padding:0 }
.chapter-wrapper.mini-interface .chapter.cartoon .content-inner { padding-top:1px }
.chapter-wrapper.mini-interface .chapter.slide-reader { padding:0;height:100% }
.chapter-wrapper.mini-interface .chapter.slide-reader .bottom-bar { height:calc(24px + var(--bottom-padding, 0px));position:absolute;bottom:0;padding:0 16px;padding-left:calc(16px + var(--horizontal-padding, 0px));padding-right:calc(16px + var(--horizontal-padding, 0px));padding-bottom:calc(6px + var(--bottom-padding, 0px));display:-ms-flexbox;display:flex;-ms-flex-pack:justify;justify-content:space-between;font-size:12px }
.chapter-wrapper.mini-interface .chapter.slide-reader .top-bar { position:relative }
.chapter-wrapper.mini-interface .chapter.slide-reader .content { position:absolute;overflow:visible;top:calc(30px + var(--top-padding, 0px));top:calc(30px + constant(safe-area-inset-top) + var(--top-padding, 0px));top:calc(30px + env(safe-area-inset-top) + var(--top-padding, 0px));bottom:calc(24px + var(--bottom-padding, 0px));left:0;right:0 }
.chapter-wrapper.mini-interface .chapter.slide-reader .content-inner { margin:0;margin-left:calc(16px + var(--horizontal-padding, 0px));margin-right:calc(16px + var(--horizontal-padding, 0px));overflow:hidden;text-align:justify;padding:0;height:100% }
.chapter-wrapper.mini-interface .chapter.slide-reader .book-content { height:100% }
.chapter-wrapper.mini-interface .chapter.slide-reader .book-content img { break-inside:avoid }
.chapter-wrapper.mini-interface .chapter.slide-reader:not(.epub-iframe) .book-content { columns:calc(100vw - 16px - var(--horizontal-padding, 0px)) 1;column-gap:calc(16px + var(--horizontal-padding, 0px)) }
.chapter-wrapper.mini-interface::-webkit-scrollbar { width:0!important }
</style>
