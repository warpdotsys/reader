<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=reader scopeId=401db35b；无 sourcemap，模板为 render 推断还原 -->
<template>
<div class="settings-wrapper" :class="{night: $store.getters.isNight, day: !$store.getters.isNight}" :style="popupTheme"><div class="settings-title"> 设置 <div class="title-btn" @click="resetConfig">重置</div><div class="title-btn" @click="restoreConfig">同步</div><div class="title-btn" @click="backupConfig">保存</div><div class="title-btn" @click="toggleNotAutoSync">{{ " " + _s(notAutoSync ? "自动同步" : "取消自动同步") + " " }}</div></div><div class="setting-list"><ul><li><span class="setting-item-title">配置方案</span><div class="selection-zone"><template v-for="(n, a) in $store.state.customConfigList"><span :key="a" class="span-item" :class="{selected: $store.getters.config.customConfig === n.name}" @click="(t) => {
                                    return setCustomConfig(n)
                                }"><span>{{ n.name }}</span><template v-if="a > 1 && $store.getters.config.customConfig !== n.name"><i class="el-icon-close delete-custom-config-icon" @click="(t) => {
                                    return t.stopPropagation(), deleteCustomConfig(a, n.name)
                                }" /></template></span></template><span :key='"addNewCustomConfig"' class="span-item" @click="addNewCustomConfig">新增方案</span><span :key='"autoTheme"' ref='"themes"' class="span-item" :class="{selected: $store.getters.config.autoTheme}" @click="setAutoTheme">自动切换</span></div></li><li><span class="setting-item-title">方案类型</span><div class="selection-zone"><template v-for="(n, a) in configDefaultTypeList"><span :key="a" class="span-item" :class="{selected: currentCustomConfig.configDefaultType === n}" @click="(t) => {
                                    return setConfigDefaultType(n)
                                }">{{ n }}</span></template></div></li><li><span class="setting-item-title">阅读主题</span><div class="selection-zone"><template v-for="(n, a) in themeColors"><span :key="a" ref='"themes"' class="theme-item" :class="{selected: config.theme === a}" :style="n" @click='(t) => {
                                    return setConfig("theme", a)
                                }'><template v-if="6 != a"><em class="iconfont"></em></template><template v-else><em class="moon-icon">{{ moonIcon }}</em></template></span></template><span :key='"custom"' ref='"themes"' class="span-item" :class='{selected: "custom" === config.theme}' @click='(t) => {
                                return setConfig("theme", "custom")
                            }'>自定义</span></div></li><template v-if='"custom" === config.theme'><li><span class="setting-item-title">自定义</span><div class="custom-theme"><div class="custom-theme-title"><span class="custom-theme-title">主题模式</span><template v-for="(n, a) in themeTypes"><span :key="a" class="span-item" :class="{selected: themeType == n}" @click='(t) => {
                                    return setConfig("themeType", n)
                                }'>{{ "day" === n ? "白天" : "黑夜" }}</span></template></div><span class="custom-theme-title">页面背景颜色 <el-color-picker v-model="config.bodyColor" /></span><span class="custom-theme-title">浮窗背景颜色 <el-color-picker v-model="config.popupColor" /></span><span class="custom-theme-title">阅读背景颜色 <el-color-picker v-model="config.contentColor" /></span><span class="custom-theme-title">阅读背景图片 <template v-for="(n, a) in builtinBG"><img :key="a" class="content-bg-preview" :class="{selected: $store.getters.config.contentBGImg == n.src}" :src="n.src" alt="" @click="(t) => {
                                    return setBGImg(n.src)
                                }" /></template><template v-for="n in $store.getters.config.customBGImgList || []"><div :key="n" class="content-bg-preview" :class="{selected: $store.getters.config.contentBGImg == n}"><img :src="getCustomBGImgURL(n)" alt="" @click="(t) => {
                                    return setBGImg(n)
                                }" /><i class="el-icon-close delete-bg-icon" @click="(t) => {
                                    return t.stopPropagation(), deleteCustomBGImg(n)
                                }" /></div></template><span class="upload-bg-btn" @click="uploadBGFile">上传</span><input ref='"bgFileRef"' style="display: none" type="file" @change="onBGFileChange" /></span></div></li></template><li><span class="setting-item-title">正文字体</span><div class="selection-zone"><template v-for="(n, a) in fonts"><span :key="a" class="span-item" :class="{selected: config.font == a}" @click='(t) => {
                                    return setConfig("font", a)
                                }'>{{ _s(n) + " " }}<i :class="{'el-icon-upload': !0, 'upload-font-icon': !0, active: config.customFontsMap && config.customFontsMap[customFonts[a]]}" @click="(t) => {
                                    return t.stopPropagation(), uploadFontFile(customFonts[a], n)
                                }" /></span></template><input ref='"fontFileRef"' style="display: none" type="file" @change="onFontFileChange" /></div></li><li><span class="setting-item-title">简繁转换</span><div class="selection-zone"><template v-for="(n, a) in chineseFonts"><span :key="a" class="span-item" :class="{selected: config.chineseFont == n}" @click='(t) => {
                                    return setConfig("chineseFont", n)
                                }'>{{ n }}</span></template></div></li><li><span class="setting-item-title">字体大小</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("fontSize")
                            }'><em class="iconfont"></em></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.fontSize" /></span><b /><span class="more" @click='(t) => {
                                return incConfig("fontSize")
                            }'><em class="iconfont"></em></span></div></li><li><span class="setting-item-title">字体粗细</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("fontWeight")
                            }'><i class="el-icon-minus" /></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.fontWeight" /></span><b /><span class="less" @click='(t) => {
                                return incConfig("fontWeight")
                            }'><i class="el-icon-plus" /></span></div></li><li><span class="setting-item-title font-color-title">字体颜色</span><el-color-picker v-model="config.fontColor" /></li><el-divider /><li><span class="setting-item-title">段落行高</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("lineHeight")
                            }'><i class="el-icon-minus" /></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.lineHeight" /></span><b /><span class="less" @click='(t) => {
                                return incConfig("lineHeight")
                            }'><i class="el-icon-plus" /></span></div></li><li><span class="setting-item-title">段落间距</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("paragraphSpace")
                            }'><i class="el-icon-minus" /></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.paragraphSpace" /></span><b /><span class="less" @click='(t) => {
                                return incConfig("paragraphSpace")
                            }'><i class="el-icon-plus" /></span></div></li><template v-if="$store.state.miniInterface"><li><span class="setting-item-title">左右边距</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("horizontalPadding")
                            }'><i class="el-icon-minus" /></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.horizontalPadding" /></span><b /><span class="less" @click='(t) => {
                                return incConfig("horizontalPadding")
                            }'><i class="el-icon-plus" /></span></div></li></template><template v-if="$store.state.miniInterface"><li><span class="setting-item-title">顶部边距</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("topPadding")
                            }'><i class="el-icon-minus" /></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.topPadding" /></span><b /><span class="less" @click='(t) => {
                                return incConfig("topPadding")
                            }'><i class="el-icon-plus" /></span></div></li></template><template v-if="$store.state.miniInterface"><li><span class="setting-item-title">底部边距</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("bottomPadding")
                            }'><i class="el-icon-minus" /></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.bottomPadding" /></span><b /><span class="less" @click='(t) => {
                                return incConfig("bottomPadding")
                            }'><i class="el-icon-plus" /></span></div></li></template><el-divider /><li><span class="setting-item-title">页面模式</span><div class="selection-zone"><template v-for="(n, a) in pageModes"><span :key="a" class="span-item" :class="{selected: config.pageMode == n}" @click="(t) => {
                                    return setPageMode(n)
                                }">{{ n }}</span></template></div></li><template v-if="!($store.state.miniInterface)"><li><span class="setting-item-title">页面宽度</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("readWidth")
                            }'><em class="iconfont"></em></span><b /> <span class="lang">{{ config.readWidth }}</span><b /><span class="more" @click='(t) => {
                                return incConfig("readWidth")
                            }'><em class="iconfont"></em></span></div></li></template><el-divider /><li><span class="setting-item-title">翻页方式</span><div class="selection-zone"><template v-for="(n, a) in readMethods"><span :key="a" class="span-item" :class="{selected: config.readMethod == n}" v-show='!$store.state.miniInterface && "左右滑动" !== n || $store.state.miniInterface' @click="(t) => {
                                    return setReadMethod(n)
                                }">{{ n }}</span></template><span class="small-tip">❗️上下滚动2会自动隐藏看过的章节，但是可能会抖动</span></div></li><li><span class="setting-item-title">动画时长</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("animateMSTime")
                            }'><i class="el-icon-minus" /></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.animateMSTime" /></span><b /><span class="less" @click='(t) => {
                                return incConfig("animateMSTime")
                            }'><i class="el-icon-plus" /></span></div></li><li><span class="setting-item-title">自动翻页</span><div class="selection-zone"><template v-for="(n, a) in autoReadingMethods"><span :key="a" class="span-item" :class="{selected: config.autoReadingMethod === n}" @click='(t) => {
                                    return setConfig("autoReadingMethod", n)
                                }'>{{ n }}</span></template></div></li><template v-if='"像素滚动" === config.autoReadingMethod'><li><span class="setting-item-title">滚动像素</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("autoReadingPixel")
                            }'><i class="el-icon-minus" /></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.autoReadingPixel" /></span><b /><span class="less" @click='(t) => {
                                return incConfig("autoReadingPixel")
                            }'><i class="el-icon-plus" /></span></div></li></template><li><span class="setting-item-title">翻页速度</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("autoReadingLineTime")
                            }'><i class="el-icon-minus" /></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.autoReadingLineTime" /></span><b /><span class="less" @click='(t) => {
                                return incConfig("autoReadingLineTime")
                            }'><i class="el-icon-plus" /></span></div></li><li><span class="setting-item-title">全屏点击</span><div class="selection-zone"><template v-for="(n, a) in clickMethods"><span :key="a" class="span-item" :class="{selected: config.clickMethod == n}" @click='(t) => {
                                    return setConfig("clickMethod", n)
                                }'>{{ n }}</span></template></div></li><li><span class="setting-item-title">选择文字</span><div class="selection-zone"><template v-for="(n, a) in selectionActions"><span :key="a" class="span-item" :class="{selected: config.selectionAction == n}" @click='(t) => {
                                    return setConfig("selectionAction", n)
                                }'>{{ n }}</span></template></div></li><li><span class="setting-item-title">Epub解析</span><div class="selection-zone"><template v-for="(n, a) in epubModes"><span :key="a" class="span-item" :class="{selected: config.epubMode == n}" @click="(t) => {
                                    return setEpubMode(n)
                                }">{{ n }}</span></template><span class="small-tip">❗️解析可能导致显示问题，但是翻页性能较好，样式支持更多，也支持简繁切换。</span></div></li><li><span class="setting-item-title">请求超时</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("chapterRequestTimeout")
                            }'><i class="el-icon-minus" /></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.chapterRequestTimeout" /></span><b /><span class="less" @click='(t) => {
                                return incConfig("chapterRequestTimeout")
                            }'><i class="el-icon-plus" /></span></div></li><el-divider /><li><span class="setting-item-title">快捷键</span><div class="selection-zone"><template v-for="(n, a) in quickKeyModes"><span :key="a" class="span-item" :class="{selected: config.quickKeyMode === n}" @click="(t) => {
                                    return setQuickKeyMode(n)
                                }">{{ n }}</span></template></div></li><template v-if='"自定义" === config.quickKeyMode'><template v-for="(n, a) in quickKeyList"><li :key='"quickKey-" + a'><span class="setting-item-title">{{ n.label }}</span><div class="selection-zone"><template v-for="(a, u) in quickKeyOps"><span :key="u" class="span-item" :class="{selected: config.quickKey[n.key] === a}" @click="(t) => {
                                        return setQuickKeyOpt(n.key, a)
                                    }">{{ a }}</span></template></div></li></template></template><li class="operation-zone"><span class="span-btn" @click="showClickZone">显示翻页区域</span><span class="span-btn" @click="showRuleEditor">过滤规则管理</span></li></ul></div></div>
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
                        staticClass: "settings-wrapper",
                        class: {
                            night: e.$store.getters.isNight,
                            day: !e.$store.getters.isNight
                        },
                        style: e.popupTheme
                    }, [t("div", {
                        staticClass: "settings-title"
                    }, [e._v(" 设置 "), t("div", {
                        staticClass: "title-btn",
                        on: {
                            click: e.resetConfig
                        }
                    }, [e._v("重置")]), t("div", {
                        staticClass: "title-btn",
                        on: {
                            click: e.restoreConfig
                        }
                    }, [e._v("同步")]), t("div", {
                        staticClass: "title-btn",
                        on: {
                            click: e.backupConfig
                        }
                    }, [e._v("保存")]), t("div", {
                        staticClass: "title-btn",
                        on: {
                            click: e.toggleNotAutoSync
                        }
                    }, [e._v(" " + e._s(e.notAutoSync ? "自动同步" : "取消自动同步") + " ")])]), t("div", {
                        staticClass: "setting-list"
                    }, [t("ul", [t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("配置方案")]), t("div", {
                        staticClass: "selection-zone"
                    }, [e._l(e.$store.state.customConfigList, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.$store.getters.config.customConfig === n.name
                            },
                            on: {
                                click: function(t) {
                                    return e.setCustomConfig(n)
                                }
                            }
                        }, [t("span", [e._v(e._s(n.name))]), a > 1 && e.$store.getters.config.customConfig !== n.name ? t("i", {
                            staticClass: "el-icon-close delete-custom-config-icon",
                            on: {
                                click: function(t) {
                                    return t.stopPropagation(), e.deleteCustomConfig(a, n.name)
                                }
                            }
                        }) : e._e()])
                    })), t("span", {
                        key: "addNewCustomConfig",
                        staticClass: "span-item",
                        on: {
                            click: e.addNewCustomConfig
                        }
                    }, [e._v("新增方案")]), t("span", {
                        key: "autoTheme",
                        ref: "themes",
                        staticClass: "span-item",
                        class: {
                            selected: e.$store.getters.config.autoTheme
                        },
                        on: {
                            click: e.setAutoTheme
                        }
                    }, [e._v("自动切换")])], 2)]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("方案类型")]), t("div", {
                        staticClass: "selection-zone"
                    }, e._l(e.configDefaultTypeList, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.currentCustomConfig.configDefaultType === n
                            },
                            on: {
                                click: function(t) {
                                    return e.setConfigDefaultType(n)
                                }
                            }
                        }, [e._v(e._s(n))])
                    })), 0)]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("阅读主题")]), t("div", {
                        staticClass: "selection-zone"
                    }, [e._l(e.themeColors, (function(n, a) {
                        return t("span", {
                            key: a,
                            ref: "themes",
                            refInFor: !0,
                            staticClass: "theme-item",
                            class: {
                                selected: e.config.theme === a
                            },
                            style: n,
                            on: {
                                click: function(t) {
                                    return e.setConfig("theme", a)
                                }
                            }
                        }, [6 != a ? t("em", {
                            staticClass: "iconfont"
                        }, [e._v("")]) : t("em", {
                            staticClass: "moon-icon"
                        }, [e._v(e._s(e.moonIcon))])])
                    })), t("span", {
                        key: "custom",
                        ref: "themes",
                        staticClass: "span-item",
                        class: {
                            selected: "custom" === e.config.theme
                        },
                        on: {
                            click: function(t) {
                                return e.setConfig("theme", "custom")
                            }
                        }
                    }, [e._v("自定义")])], 2)]), "custom" === e.config.theme ? t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("自定义")]), t("div", {
                        staticClass: "custom-theme"
                    }, [t("div", {
                        staticClass: "custom-theme-title"
                    }, [t("span", {
                        staticClass: "custom-theme-title"
                    }, [e._v("主题模式")]), e._l(e.themeTypes, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.themeType == n
                            },
                            on: {
                                click: function(t) {
                                    return e.setConfig("themeType", n)
                                }
                            }
                        }, [e._v(e._s("day" === n ? "白天" : "黑夜"))])
                    }))], 2), t("span", {
                        staticClass: "custom-theme-title"
                    }, [e._v("页面背景颜色 "), t("el-color-picker", {
                        model: {
                            value: e.config.bodyColor,
                            callback: function(t) {
                                e.$set(e.config, "bodyColor", t)
                            },
                            expression: "config.bodyColor"
                        }
                    })], 1), t("span", {
                        staticClass: "custom-theme-title"
                    }, [e._v("浮窗背景颜色 "), t("el-color-picker", {
                        model: {
                            value: e.config.popupColor,
                            callback: function(t) {
                                e.$set(e.config, "popupColor", t)
                            },
                            expression: "config.popupColor"
                        }
                    })], 1), t("span", {
                        staticClass: "custom-theme-title"
                    }, [e._v("阅读背景颜色 "), t("el-color-picker", {
                        model: {
                            value: e.config.contentColor,
                            callback: function(t) {
                                e.$set(e.config, "contentColor", t)
                            },
                            expression: "config.contentColor"
                        }
                    })], 1), t("span", {
                        staticClass: "custom-theme-title"
                    }, [e._v("阅读背景图片 "), e._l(e.builtinBG, (function(n, a) {
                        return t("img", {
                            key: a,
                            staticClass: "content-bg-preview",
                            class: {
                                selected: e.$store.getters.config.contentBGImg == n.src
                            },
                            attrs: {
                                src: n.src,
                                alt: ""
                            },
                            on: {
                                click: function(t) {
                                    return e.setBGImg(n.src)
                                }
                            }
                        })
                    })), e._l(e.$store.getters.config.customBGImgList || [], (function(n) {
                        return t("div", {
                            key: n,
                            staticClass: "content-bg-preview",
                            class: {
                                selected: e.$store.getters.config.contentBGImg == n
                            }
                        }, [t("img", {
                            attrs: {
                                src: e.getCustomBGImgURL(n),
                                alt: ""
                            },
                            on: {
                                click: function(t) {
                                    return e.setBGImg(n)
                                }
                            }
                        }), t("i", {
                            staticClass: "el-icon-close delete-bg-icon",
                            on: {
                                click: function(t) {
                                    return t.stopPropagation(), e.deleteCustomBGImg(n)
                                }
                            }
                        })])
                    })), t("span", {
                        staticClass: "upload-bg-btn",
                        on: {
                            click: e.uploadBGFile
                        }
                    }, [e._v("上传")]), t("input", {
                        ref: "bgFileRef",
                        staticStyle: {
                            display: "none"
                        },
                        attrs: {
                            type: "file"
                        },
                        on: {
                            change: e.onBGFileChange
                        }
                    })], 2)])]) : e._e(), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("正文字体")]), t("div", {
                        staticClass: "selection-zone"
                    }, [e._l(e.fonts, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.config.font == a
                            },
                            on: {
                                click: function(t) {
                                    return e.setConfig("font", a)
                                }
                            }
                        }, [e._v(e._s(n) + " "), t("i", {
                            class: {
                                "el-icon-upload": !0,
                                "upload-font-icon": !0,
                                active: e.config.customFontsMap && e.config.customFontsMap[e.customFonts[a]]
                            },
                            on: {
                                click: function(t) {
                                    return t.stopPropagation(), e.uploadFontFile(e.customFonts[a], n)
                                }
                            }
                        })])
                    })), t("input", {
                        ref: "fontFileRef",
                        staticStyle: {
                            display: "none"
                        },
                        attrs: {
                            type: "file"
                        },
                        on: {
                            change: e.onFontFileChange
                        }
                    })], 2)]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("简繁转换")]), t("div", {
                        staticClass: "selection-zone"
                    }, e._l(e.chineseFonts, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.config.chineseFont == n
                            },
                            on: {
                                click: function(t) {
                                    return e.setConfig("chineseFont", n)
                                }
                            }
                        }, [e._v(e._s(n))])
                    })), 0)]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("字体大小")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("fontSize")
                            }
                        }
                    }, [t("em", {
                        staticClass: "iconfont"
                    }, [e._v("")])]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.fontSize,
                            callback: function(t) {
                                e.$set(e.config, "fontSize", t)
                            },
                            expression: "config.fontSize"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "more",
                        on: {
                            click: function(t) {
                                return e.incConfig("fontSize")
                            }
                        }
                    }, [t("em", {
                        staticClass: "iconfont"
                    }, [e._v("")])])])]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("字体粗细")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("fontWeight")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-minus"
                    })]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.fontWeight,
                            callback: function(t) {
                                e.$set(e.config, "fontWeight", t)
                            },
                            expression: "config.fontWeight"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.incConfig("fontWeight")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-plus"
                    })])])]), t("li", [t("span", {
                        staticClass: "setting-item-title font-color-title"
                    }, [e._v("字体颜色")]), t("el-color-picker", {
                        model: {
                            value: e.config.fontColor,
                            callback: function(t) {
                                e.$set(e.config, "fontColor", t)
                            },
                            expression: "config.fontColor"
                        }
                    })], 1), t("el-divider"), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("段落行高")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("lineHeight")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-minus"
                    })]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.lineHeight,
                            callback: function(t) {
                                e.$set(e.config, "lineHeight", t)
                            },
                            expression: "config.lineHeight"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.incConfig("lineHeight")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-plus"
                    })])])]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("段落间距")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("paragraphSpace")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-minus"
                    })]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.paragraphSpace,
                            callback: function(t) {
                                e.$set(e.config, "paragraphSpace", t)
                            },
                            expression: "config.paragraphSpace"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.incConfig("paragraphSpace")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-plus"
                    })])])]), e.$store.state.miniInterface ? t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("左右边距")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("horizontalPadding")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-minus"
                    })]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.horizontalPadding,
                            callback: function(t) {
                                e.$set(e.config, "horizontalPadding", t)
                            },
                            expression: "config.horizontalPadding"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.incConfig("horizontalPadding")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-plus"
                    })])])]) : e._e(), e.$store.state.miniInterface ? t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("顶部边距")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("topPadding")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-minus"
                    })]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.topPadding,
                            callback: function(t) {
                                e.$set(e.config, "topPadding", t)
                            },
                            expression: "config.topPadding"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.incConfig("topPadding")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-plus"
                    })])])]) : e._e(), e.$store.state.miniInterface ? t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("底部边距")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("bottomPadding")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-minus"
                    })]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.bottomPadding,
                            callback: function(t) {
                                e.$set(e.config, "bottomPadding", t)
                            },
                            expression: "config.bottomPadding"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.incConfig("bottomPadding")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-plus"
                    })])])]) : e._e(), t("el-divider"), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("页面模式")]), t("div", {
                        staticClass: "selection-zone"
                    }, e._l(e.pageModes, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.config.pageMode == n
                            },
                            on: {
                                click: function(t) {
                                    return e.setPageMode(n)
                                }
                            }
                        }, [e._v(e._s(n))])
                    })), 0)]), e.$store.state.miniInterface ? e._e() : t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("页面宽度")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("readWidth")
                            }
                        }
                    }, [t("em", {
                        staticClass: "iconfont"
                    }, [e._v("")])]), t("b"), e._v(" "), t("span", {
                        staticClass: "lang"
                    }, [e._v(e._s(e.config.readWidth))]), t("b"), t("span", {
                        staticClass: "more",
                        on: {
                            click: function(t) {
                                return e.incConfig("readWidth")
                            }
                        }
                    }, [t("em", {
                        staticClass: "iconfont"
                    }, [e._v("")])])])]), t("el-divider"), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("翻页方式")]), t("div", {
                        staticClass: "selection-zone"
                    }, [e._l(e.readMethods, (function(n, a) {
                        return t("span", {
                            directives: [{
                                name: "show",
                                rawName: "v-show",
                                value: !e.$store.state.miniInterface && "左右滑动" !== n || e.$store.state.miniInterface,
                                expression: "\n              (!$store.state.miniInterface && method !== '左右滑动') ||\n                $store.state.miniInterface\n            "
                            }],
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.config.readMethod == n
                            },
                            on: {
                                click: function(t) {
                                    return e.setReadMethod(n)
                                }
                            }
                        }, [e._v(e._s(n))])
                    })), t("span", {
                        staticClass: "small-tip"
                    }, [e._v("❗️上下滚动2会自动隐藏看过的章节，但是可能会抖动")])], 2)]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("动画时长")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("animateMSTime")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-minus"
                    })]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.animateMSTime,
                            callback: function(t) {
                                e.$set(e.config, "animateMSTime", t)
                            },
                            expression: "config.animateMSTime"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.incConfig("animateMSTime")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-plus"
                    })])])]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("自动翻页")]), t("div", {
                        staticClass: "selection-zone"
                    }, e._l(e.autoReadingMethods, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.config.autoReadingMethod === n
                            },
                            on: {
                                click: function(t) {
                                    return e.setConfig("autoReadingMethod", n)
                                }
                            }
                        }, [e._v(e._s(n))])
                    })), 0)]), "像素滚动" === e.config.autoReadingMethod ? t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("滚动像素")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("autoReadingPixel")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-minus"
                    })]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.autoReadingPixel,
                            callback: function(t) {
                                e.$set(e.config, "autoReadingPixel", t)
                            },
                            expression: "config.autoReadingPixel"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.incConfig("autoReadingPixel")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-plus"
                    })])])]) : e._e(), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("翻页速度")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("autoReadingLineTime")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-minus"
                    })]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.autoReadingLineTime,
                            callback: function(t) {
                                e.$set(e.config, "autoReadingLineTime", t)
                            },
                            expression: "config.autoReadingLineTime"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.incConfig("autoReadingLineTime")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-plus"
                    })])])]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("全屏点击")]), t("div", {
                        staticClass: "selection-zone"
                    }, e._l(e.clickMethods, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.config.clickMethod == n
                            },
                            on: {
                                click: function(t) {
                                    return e.setConfig("clickMethod", n)
                                }
                            }
                        }, [e._v(e._s(n))])
                    })), 0)]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("选择文字")]), t("div", {
                        staticClass: "selection-zone"
                    }, e._l(e.selectionActions, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.config.selectionAction == n
                            },
                            on: {
                                click: function(t) {
                                    return e.setConfig("selectionAction", n)
                                }
                            }
                        }, [e._v(e._s(n))])
                    })), 0)]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("Epub解析")]), t("div", {
                        staticClass: "selection-zone"
                    }, [e._l(e.epubModes, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.config.epubMode == n
                            },
                            on: {
                                click: function(t) {
                                    return e.setEpubMode(n)
                                }
                            }
                        }, [e._v(e._s(n))])
                    })), t("span", {
                        staticClass: "small-tip"
                    }, [e._v("❗️解析可能导致显示问题，但是翻页性能较好，样式支持更多，也支持简繁切换。")])], 2)]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("请求超时")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("chapterRequestTimeout")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-minus"
                    })]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.chapterRequestTimeout,
                            callback: function(t) {
                                e.$set(e.config, "chapterRequestTimeout", t)
                            },
                            expression: "config.chapterRequestTimeout"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.incConfig("chapterRequestTimeout")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-plus"
                    })])])]), t("el-divider"), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("快捷键")]), t("div", {
                        staticClass: "selection-zone"
                    }, e._l(e.quickKeyModes, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.config.quickKeyMode === n
                            },
                            on: {
                                click: function(t) {
                                    return e.setQuickKeyMode(n)
                                }
                            }
                        }, [e._v(e._s(n))])
                    })), 0)]), "自定义" === e.config.quickKeyMode ? e._l(e.quickKeyList, (function(n, a) {
                        return t("li", {
                            key: "quickKey-" + a
                        }, [t("span", {
                            staticClass: "setting-item-title"
                        }, [e._v(e._s(n.label))]), t("div", {
                            staticClass: "selection-zone"
                        }, e._l(e.quickKeyOps, (function(a, u) {
                            return t("span", {
                                key: u,
                                staticClass: "span-item",
                                class: {
                                    selected: e.config.quickKey[n.key] === a
                                },
                                on: {
                                    click: function(t) {
                                        return e.setQuickKeyOpt(n.key, a)
                                    }
                                }
                            }, [e._v(e._s(a))])
                        })), 0)])
                    })) : e._e(), t("li", {
                        staticClass: "operation-zone"
                    }, [t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: e.showClickZone
                        }
                    }, [e._v("显示翻页区域")]), t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: e.showRuleEditor
                        }
                    }, [e._v("过滤规则管理")])])], 2)])])
                },
  staticRenderFns: []
}
</script>
