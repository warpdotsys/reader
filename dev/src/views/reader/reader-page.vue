<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=reader / 模块 968f（scopeId 9c1aae92）
     主页面 options 来自 968f.js 的变量 Nt；模板来自同模块的 render 变量 a（staticRenderFns 为空，u = []）；
     样式来自同 scopeId 的 style.css。产物里形如「模块变量点 a」的 webpack 模块引用已全部改写为
     具名 import 或模块级常量，无残留；子组件按 scopeId 目录引用。 -->
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
                            }"> 后面全部 </div><div class="caching-tip" v-show="isCachingContent">{{ " " + str(cachingContentTip) + " " }}</div><div class="caching-cancel-btn" v-show="isCachingContent" @click="cancelCaching"><i class="el-icon-close" /></div></div></template><div class="tools"><div class="tool-icon progress-text" @click="showCacheContent"><template v-if="$store.state.miniInterface"><span>阅读进度: </span></template>{{ " " + str(readingProgress) + " " }}</div><div class="tool-icon" :style="$store.state.miniInterface ? {
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
                            }">重置</span></div><div class="progress"><span class="progress-tip">定时</span><div class="progress-bar"><el-slider min="0" max="180" step="1" :show-tooltip="false" v-model="speechMinutes" @change="changeSpeechMinutes" /></div><span class="setting-btn">{{ str(speechMinutes) + "分钟" }}</span></div></div></div></template></div></div><div ref='"content"' class="chapter" :class="chapterClass" :style="chapterTheme"><template v-if="showClickZone"><div class="click-zone" :style='isSlideRead ? {} : {
                            position: "fixed"
                        }'><div :style="showPrevPageStyle"><span>点击前一页</span></div><div :style="showMenuZoneStyle"><span>点击显示菜单</span></div><div :style="showNextPageStyle"><span>点击后一页</span></div><div class="close-btn" @click="(t) => {
                                showClickZone = !1
                            }">关闭</div><div :style="showPrevPageExtraStyle" /><div :style="showNextPageExtraStyle" /></div></template><div ref='"top"' class="top-bar">{{ " " + str($store.state.miniInterface ? title : "") + " " }}<template v-if="$store.state.miniInterface && !isSlideRead"><span class="right-timestr">{{ timeStr }}</span></template></div><div class="content" @touchstart="handleTouchStart" @touchmove="handleTouchMove" @touchend="handleTouchEnd" @click="handlerClick"><template v-if="show"><div class="content-inner" :style="contentInnerStyle"><Content ref='"bookContentRef"' class="book-content" :style="contentStyle" :title="title" :content="content" :showContent="show" :error="error" :showChapterList="showChapterList" :isScrollRead="isScrollRead" :isSlideRead="isSlideRead" :isEditContent="isEditContent" @prevChapter="toLastChapter" @nextChapter="toNextChapter" @updateProgress="saveReadingPosition" @iframeLoad='(t) => {
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
import axios from 'axios'
import { merge as __merge } from '@/utils/merge'
import { regeneratorRuntime } from '@/utils/regenerator'
import { asyncToGenerator } from '@/utils/async'
import { getStorage, setStorage } from '@/utils/storage'
import { cachedFetch } from '@/utils/common'
import eventBus from '@/utils/event-bus'
import PopCata from '@/views/reader/4f5b9f48/4f5b9f48.vue'
import ReadSettings from '@/views/reader/062ccc78/062ccc78.vue'
import BookSource from '@/views/reader/abde66a6/abde66a6.vue'
import BookShelf from '@/views/reader/510c7aa8/510c7aa8.vue'
import Content from '@/views/reader/59408c4f/59408c4f.vue'

// ---------------------------------------------------------------------------
// 产物里以 webpack 模块引用出现、工程侧没有同名文件的常量与工具。
// 语义按产物逐条对齐（注释标出出处模块），放在组件外，避免每次实例化重建。
// ---------------------------------------------------------------------------

/** Edge TTS 中文（普通话，简体）音色表（产物模块 968f 的 kt["中文（普通话，简体）"]，变量 wt） */
const EDGE_VOICE_LIST = [
  { LocalName: "晓晓（女 - 年轻人）", name: "zh-CN-XiaoxiaoNeural" },
  { LocalName: "云扬（男 - 年轻人）", name: "zh-CN-YunyangNeural" },
  { LocalName: "云希（男 - 年轻人 - 抖音热门）", name: "zh-CN-YunxiNeural" },
  { LocalName: "晓伊（女 - 儿童）", name: "zh-CN-XiaoyiNeural" },
  { LocalName: "云健（男 - 中年人）", name: "zh-CN-YunjianNeural" },
  { LocalName: "云夏（男 - 儿童）", name: "zh-CN-YunxiaNeural" }
]

/** 正文分段的标点/符号清洗正则（产物模块 968f 的变量 xt） */
const PUNCTUATION_RE = /[\u2000-\u206F\u2E00-\u2E7F\\'!"#$%&\(\)*+,-\./:;<=>?@\[\]^_`{\|}~，。？《》；：、«]/g

/** 书签默认表单（产物模块 bfff 的导出 c，变量 W） */
const BOOKMARK_FORM_DEFAULT = {
  bookName: '',
  bookAuthor: '',
  chapterIndex: 0,
  chapterPos: 0,
  chapterName: '',
  bookText: '',
  content: ''
}

/** 文本替换规则默认表单（产物模块 bfff 的导出 d，变量 G） */
const TEXT_REPLACE_RULE_DEFAULT = {
  name: '',
  pattern: '',
  replacement: '',
  scope: '',
  isRegex: false,
  isEnabled: true
}

/** 字符串相似度（产物模块 7513 的导出 c，变量 p）：1 - 编辑距离 / 较长串长度 */
function stringSimilarity(a, b) {
  if (a === b) return 1
  if (!a || !b) return 0
  var d = new Array(a.length + 1)
  var i
  var j
  for (i = 0; i <= a.length; i++) d[i] = new Array(b.length + 1)
  for (i = 0; i <= a.length; i++) {
    for (j = 0; j <= b.length; j++) {
      if (i === 0) d[0][j] = j
      else if (j === 0) d[i][0] = i
      else if (a.charAt(i - 1) === b.charAt(j - 1)) d[i][j] = d[i - 1][j - 1]
      else d[i][j] = 1 + Math.min(d[i - 1][j - 1], Math.min(d[i][j - 1], d[i - 1][j]))
    }
  }
  return 1 - d[a.length][b.length] / Math.max(a.length, b.length)
}

/**
 * 元素平滑滚动到视口（产物模块 8efd 的默认导出，调用点变量 c 的 a 导出）。
 * 支持传入数字 / 元素 / 选择器，以及 { duration, offset, easing, a11y, callback, container }。
 */
function scrollToElement(el, options) {
  var opts = options || {}
  var container = null
  var target = null
  var start = 0
  var to = 0
  var distance = 0
  var begin = false
  var duration = opts.duration || 1000
  var offset = opts.offset || 0
  var callback = opts.callback
  var a11y = opts.a11y || false
  var easing =
    opts.easing ||
    function (t, b, c, d) {
      t /= d / 2
      if (t < 1) return (c / 2) * t * t + b
      t--
      return (-c / 2) * (t * (t - 2) - 1) + b
    }

  var kind = opts.container
  if (kind && typeof kind === 'object') container = kind
  else if (typeof kind === 'string') container = document.querySelector(kind)
  else container = window

  function getScrollTop() {
    var t = container.scrollTop || container.scrollY || container.pageYOffset
    return typeof t === 'undefined' ? 0 : t
  }

  function getElementTop(node) {
    var rectTop = node.getBoundingClientRect().top
    var containerTop = container.getBoundingClientRect ? container.getBoundingClientRect().top : 0
    return rectTop - containerTop + start
  }

  function setScrollTop(top) {
    if (container.scrollTo) container.scrollTo(0, top)
    else container.scrollTop = top
  }

  function finish() {
    setScrollTop(start + to)
    if (target && a11y) {
      target.setAttribute('tabindex', '-1')
      target.focus()
    }
    if (typeof callback === 'function') callback()
    begin = false
  }

  function step(now) {
    if (!begin) begin = now
    distance = now - begin
    setScrollTop(easing(distance, start, to, duration))
    if (distance < duration) requestAnimationFrame(step)
    else finish()
  }

  start = getScrollTop()

  if (typeof el === 'number') {
    target = null
    a11y = false
    to = el
  } else if (el && typeof el === 'object') {
    target = el
    to = getElementTop(target)
  } else if (typeof el === 'string') {
    target = document.querySelector(el)
    to = getElementTop(target)
  }

  to = to - start + offset
  if (typeof opts.duration === 'function') duration = opts.duration(to)

  requestAnimationFrame(step)
}

/**
 * 并发受限任务队列（产物模块 7513 的导出 a，变量 c）
 * 返回 runner(task)：task 入队，未达并发上限就立刻起跑；
 * runner 上挂 requestCount / leftCount / errorCount / cancel() / isEnd()。
 */
function createConcurrentRunner(limit, onDone) {
  var running = 0
  var queue = []

  function next() {
    return worker()
  }

  async function worker() {
    ++running
    runner.leftCount = queue.length
    var task = queue.shift()
    var res
    var err
    try {
      res = await task()
    } catch (e) {
      err = e
      runner.errorCount++
    } finally {
      --running
      runner.requestCount++
      runner.leftCount = queue.length
      if (onDone) onDone(runner, res, err)
      if (queue.length > 0) next()
    }
  }

  var runner = function (task) {
    if (task && task instanceof Function) {
      queue.push(task)
      runner.leftCount = queue.length
      if (running < limit) next()
    }
  }
  runner.requestCount = 0
  runner.leftCount = 0
  runner.errorCount = 0
  runner.cancel = function () {
    queue.length = 0
    runner.leftCount = 0
  }
  runner.isEnd = function () {
    return !runner.leftCount && !running
  }
  return runner
}

/** 拼 query string（产物用的 axios buildURL，模块 30b5） */
function buildURL(url, params, serializer) {
  function encode(val) {
    return encodeURIComponent(val)
      .replace(/%3A/gi, ':')
      .replace(/%24/g, '$')
      .replace(/%2C/gi, ',')
      .replace(/%20/g, '+')
      .replace(/%5B/gi, '[')
      .replace(/%5D/gi, ']')
  }
  if (!params) return url
  var parts
  var isURLSearchParams = typeof URLSearchParams !== 'undefined' && params instanceof URLSearchParams
  var isArray = function (v) {
    return Object.prototype.toString.call(v) === '[object Array]'
  }
  var isDate = function (v) {
    return Object.prototype.toString.call(v) === '[object Date]'
  }
  var isObject = function (v) {
    return v !== null && typeof v === 'object'
  }
  if (serializer) {
    parts = serializer(params)
  } else if (isURLSearchParams) {
    parts = params.toString()
  } else {
    var arr = []
    Object.keys(params).forEach(function (key) {
      var val = params[key]
      if (val === null || typeof val === 'undefined') return
      var k = key
      if (isArray(val)) k += '[]'
      else val = [val]
      val.forEach(function (v) {
        if (isDate(v)) v = v.toISOString()
        else if (isObject(v)) v = JSON.stringify(v)
        arr.push(encode(k) + '=' + encode(v))
      })
    })
    parts = arr.join('&')
  }
  if (parts) {
    var hashmark = url.indexOf('#')
    if (hashmark !== -1) url = url.slice(0, hashmark)
    url += (url.indexOf('?') === -1 ? '?' : '&') + parts
  }
  return url
}

export default {
    name: 'ReaderPage',
    components: {
        PopCata,
        BookSource,
        BookShelf,
        Content,
        ReadSettings
    },
    mounted: function() {
        var e = this;
        window.readerPage = this, this.speechAvalable = window.speechSynthesis && window.speechSynthesis.getVoices, this.speechAvalable && (this.fetchVoiceList(), void 0 !== window.speechSynthesis.onvoiceschanged && (window.speechSynthesis.onvoiceschanged = this.fetchVoiceList)), window.addEventListener("unload", this.saveReadingPosition), eventBus.$on("showSearchContent", (function(t) {
            if (!e._inactive)
                if (e.chapterIndex !== t.chapterIndex) {
                    if (e.isScrollRead) return e.scrollStartChapterIndex = t.chapterIndex, void e.computeShowChapterList().then((function() {
                        e.showMatchKeyword(t)
                    }));
                    e.onNextContentRendered((function() {
                        e.$nextTick((function() {
                            e.showMatchKeyword(t)
                        }))
                    })), e.getContent(t.chapterIndex)
                } else e.showMatchKeyword(t)
        })), eventBus.$on("showBookmark", (function(t) {
            if (!e._inactive)
                if (e.chapterIndex !== t.chapterIndex) {
                    if (e.isScrollRead) return e.scrollStartChapterIndex = t.chapterIndex, void e.computeShowChapterList().then((function() {
                        e.showBookmark(t)
                    }));
                    e.onNextContentRendered((function() {
                        e.$nextTick((function() {
                            e.showBookmark(t)
                        }))
                    })), e.getContent(t.chapterIndex)
                } else e.showBookmark(t)
        })), eventBus.$on("addBookmark", (function() {
            e._inactive || e.addBookmark()
        }))
    },
    activated: function() {
        var e = this;
        this.init(), window.addEventListener("keydown", this.keydownHandler), this.title ? document.title = this.$store.getters.readingBook.name + " - " + this.title : document.title = this.$store.getters.readingBook.name, this.formatTime(), this.timer = setInterval((function() {
            e.formatTime()
        }), 5e3), this.unwatchFn = this.$store.watch((function(e) {
            return e.config
        }), (function() {
            e.$nextTick((function() {
                e.computePages((function() {
                    e.currentPage > e.totalPages && e.showPage(e.totalPages, 0)
                }))
            }))
        }), {
            deep: !0
        }), window.addEventListener("scroll", this.scrollHandler);
        try {
            this.releaseWakeLockFn = this.wakeLock()
        } catch (t) {}
        this.$Lazyload.$on("loaded", this.lazyloadHandler)
    },
    deactivated: function() {
        this.saveBookProgress(), this.startSavePosition = !1, this.lastReadingBook = this.$store.getters.readingBook, this.timer && clearInterval(this.timer), window.removeEventListener("keydown", this.keydownHandler), window.removeEventListener("scroll", this.scrollHandler), this.unwatchFn && this.unwatchFn(), this.releaseWakeLockFn && this.releaseWakeLockFn(), this.$Lazyload.$off("loaded", this.lazyloadHandler)
    },
    watch: {
        chapterName: function(e) {
            this.title = e
        },
        content: function() {
            var e = this;
            this.contentStyle = {}, this.transformX = 0, this.currentPage = 1, this.$nextTick((function() {
                e.computePages(), e.saveReadingPosition()
            })), this.isEpub && this.$once("iframeLoad", (function() {
                e.computePages()
            }))
        },
        readSettingsVisible: function(e) {},
        title: function(e) {
            document.title = e ? this.$store.getters.readingBook.name + " - " + e : this.$store.getters.readingBook.name
        },
        isSlideRead: function(e) {
            var t = this;
            e || (this.contentStyle = {}, this.transformX = 0, this.isEpub && this.$refs.bookContentRef.transformX(0)), this.$nextTick((function() {
                t.computePages((function() {
                    t.currentParagraph ? t.showParagraph(t.currentParagraph, !0) : t.showPage(t.currentPage, 0)
                }))
            }))
        },
        isScrollRead: function(e) {
            e && (this.scrollStartChapterIndex = this.chapterIndex, this.computeShowChapterList())
        },
        windowSize: function() {
            var e = this;
            this.$nextTick((function() {
                e.computePages((function() {
                    e.showPage(e.currentPage, 0)
                }))
            }))
        },
        loginAuth: function(e) {
            e && this.init(!0)
        },
        showReadBar: function(e) {
            e && (this.showToolBar = !1)
        },
        readingBook: function(e, t) {
            e.bookUrl !== t.bookUrl && (this.startSavePosition = !1, this.autoShowPosition())
        },
        currentPage: function(e, t) {
            var n = this;
            null != this.$store.getters.readingBook && e !== t && e >= this.totalPages - 2 && this.$store.getters.readingBook.index < this.$store.getters.readingBook.catalog.length - 1 && (this.isScrollRead || this.preCaching || (this.preCaching = !0, this.getBookContent(this.$store.getters.readingBook.index + 1, {
                timeout: 1e3 * this.$store.getters.config.chapterRequestTimeout,
                silent: !0
            }, !1, !0).then((function() {
                n.preCaching = !1
            }))))
        },
        filterRules: function() {
            this.isScrollRead ? this.computeShowChapterList() : this.content = this.filterContent(this.originalContent)
        },
        chineseFont: function() {
            this.title = this.title, this.content = this.content, this.computeShowChapterList()
        }
    },
    data: function() {
        return {
            title: "",
            originalContent: "",
            content: "",
            error: !1,
            popCataVisible: !1,
            readSettingsVisible: !1,
            popBookSourceVisible: !1,
            popBookShelfVisible: !1,
            showToolBar: !0,
            book: null,
            show: !1,
            contentStyle: {},
            currentPage: 1,
            totalPages: 1,
            transformX: 0,
            transforming: !1,
            showClickZone: !1,
            timeStr: "",
            progressValue: 1,
            speechAvalable: !1,
            showReadBar: !1,
            speechSpeaking: !1,
            localVoiceList: [],
            showSpeechConfig: !0,
            ttsTypeList: [{
                name: "浏览器",
                value: "local"
            }, {
                name: "大声朗读",
                value: "edge"
            }, {
                name: "HttpTTS",
                value: "httpTTS"
            }],
            currentTTSCacheKey: null,
            currentParagraph: null,
            startSavePosition: !1,
            showCacheContentZone: !1,
            isCachingContent: !1,
            cachingContentTip: "",
            autoReading: !1,
            showChapterList: [],
            scrollStartChapterIndex: 0,
            showNextChapterSize: 1,
            showPrevChapterSize: 0,
            speechMinutes: 0,
            speechEndTime: 0,
            isEditContent: !1,
            contentInnerStyle: {}
        }
    },
    computed: {
        readingBook: function() {
            return this.$store.getters.readingBook || {}
        },
        catalog: function() {
            return (this.$store.getters.readingBook || {}).catalog || []
        },
        chapterIndex: function() {
            return 0 | ((this.$store.getters.readingBook || {}).index || 0)
        },
        windowSize: function() {
            return this.$store.state.windowSize
        },
        config: function() {
            return this.$store.getters.config
        },
        theme: function() {
            return this.config.theme
        },
        animateMSTime: function() {
            return this.config.animateMSTime
        },
        isNight: function() {
            return this.$store.getters.isNight
        },
        bodyTheme: function() {
            return {
                background: this.$store.getters.currentThemeConfig.body
            }
        },
        isSlideRead: function() {
            return !(this.autoReading || this.showReadBar || this.isAudio || this.isVideo) && this.$store.getters.isSlideRead
        },
        isScrollRead: function() {
            return !this.isEpub && !this.isAudio && !this.isVideo && !this.isSlideRead && ("上下滚动" === this.config.readMethod || "上下滚动2" === this.config.readMethod)
        },
        chapterClass: function() {
            return (this.isSlideRead ? "slide-reader " : "") + (this.isEpub ? "iframe" === this.config.epubMode ? "epub-iframe" : "epub-dom" : this.isCarToon ? "cartoon" : this.isAudio ? "audio" : this.isVideo ? "video" : "")
        },
        chapterTheme: function() {
            var e = this.showReadBar ? {
                paddingBottom: (this.showSpeechConfig ? 280 : 80) + "px"
            } : {};
            return "string" === typeof this.$store.getters.currentThemeConfig.content ? __merge(__merge({}, e), {}, {
                background: this.$store.getters.currentThemeConfig.content,
                width: this.readWidth
            }) : __merge(__merge(__merge({}, e), this.$store.getters.currentThemeConfig.content), {}, {
                width: this.readWidth
            })
        },
        leftBarTheme: function() {
            return {
                background: this.$store.getters.currentThemeConfig.popup,
                marginLeft: this.$store.state.miniInterface ? 0 : -(this.readWidthConfig / 2 + 68) + "px",
                display: this.$store.state.miniInterface && !this.showToolBar ? "none" : "block"
            }
        },
        rightBarTheme: function() {
            return {
                background: this.$store.getters.currentThemeConfig.popupPure,
                marginRight: this.$store.state.miniInterface ? 0 : -(this.readWidthConfig / 2 + 52) + "px",
                display: this.$store.state.miniInterface && !this.showToolBar ? "none" : "block"
            }
        },
        readBarTheme: function() {
            return {
                background: this.$store.getters.currentThemeConfig.popupPure,
                marginRight: this.$store.state.miniInterface ? 0 : -this.readWidthConfig / 2 + "px",
                zIndex: 200,
                display: this.speechAvalable && this.showReadBar ? "block" : "none",
                width: this.$store.state.miniInterface ? "100vw" : "500px"
            }
        },
        readWidth: function() {
            return this.$store.state.miniInterface ? this.windowSize.width + "px" : this.readWidthConfig - 130 + "px"
        },
        readWidthConfig: function() {
            var e = this.$store.getters.config.readWidth;
            while (e > this.$store.state.windowSize.width - 140) e -= 20;
            return e
        },
        popperWidth: function() {
            return this.$store.state.miniInterface ? this.windowSize.width - 33 : this.readWidthConfig - 33
        },
        readingProgress: function() {
            return this.catalog && this.catalog.length ? parseInt(100 * (this.chapterIndex + 1) / this.catalog.length) + "%" : ""
        },
        showPrevPageStyle: function() {
            return this.isSlideRead && "固定模式" !== this.$store.getters.config.clickMethod ? {
                left: 0,
                top: 0,
                bottom: 0,
                right: this.windowSize.width / 2 + "px",
                background: "#43987324",
                paddingRight: .2 * this.windowSize.width + "px"
            } : {
                left: 0,
                top: 0,
                right: 0,
                bottom: "固定模式" === this.$store.getters.config.clickMethod ? .7 * this.windowSize.height + "px" : this.windowSize.height / 2 + "px",
                background: "#43987324"
            }
        },
        showMenuZoneStyle: function() {
            return {
                top: .3 * this.windowSize.height + "px",
                bottom: .3 * this.windowSize.height + "px",
                left: .3 * this.windowSize.width + "px",
                right: .3 * this.windowSize.width + "px",
                background: "#636060",
                zIndex: 10
            }
        },
        showNextPageStyle: function() {
            return this.isSlideRead && "固定模式" !== this.$store.getters.config.clickMethod ? {
                right: 0,
                top: 0,
                bottom: 0,
                left: this.windowSize.width / 2 + "px",
                background: "#6b1a7324",
                paddingLeft: .2 * this.windowSize.width + "px"
            } : {
                left: 0,
                bottom: 0,
                right: 0,
                top: "固定模式" === this.$store.getters.config.clickMethod ? .7 * this.windowSize.height + "px" : this.windowSize.height / 2 + "px",
                background: "#6b1a7324"
            }
        },
        showNextPageExtraStyle: function() {
            return "固定模式" === this.$store.getters.config.clickMethod ? {
                top: .3 * this.windowSize.height + "px",
                bottom: .3 * this.windowSize.height + "px",
                right: 0,
                left: .7 * this.windowSize.width + "px",
                background: "#6b1a7324",
                paddingRight: .2 * this.windowSize.width + "px"
            } : {
                display: "none"
            }
        },
        showPrevPageExtraStyle: function() {
            return "固定模式" === this.$store.getters.config.clickMethod ? {
                top: .3 * this.windowSize.height + "px",
                bottom: .3 * this.windowSize.height + "px",
                left: 0,
                right: .7 * this.windowSize.width + "px",
                background: "#43987324",
                paddingRight: .2 * this.windowSize.width + "px"
            } : {
                display: "none"
            }
        },
        loginAuth: function() {
            return this.$store.state.loginAuth
        },
        filterRules: function() {
            return this.$store.state.filterRules
        },
        httpTTSList: function() {
            return this.$store.state.httpTTS
        },
        themeBtnStyle: function() {
            return {
                background: this.$store.getters.currentThemeConfig.popupPure
            }
        },
        popupAbsoluteBtnStyle: function() {
            return {
                background: this.$store.getters.currentThemeConfig.popupPure
            }
        },
        voiceName: {
            get: function() {
                return this.$store.state.speechVoiceConfig.voiceName
            },
            set: function(e) {
                e !== this.$store.state.speechVoiceConfig.voiceName && this.speechSpeaking && this.restartSpeech(), this.$store.commit("setSpeechVoiceConfig", __merge(__merge({}, this.$store.state.speechVoiceConfig), {}, {
                    voiceName: e
                }))
            }
        },
        speechRate: {
            get: function() {
                return this.$store.state.speechVoiceConfig.speechRate
            },
            set: function(e) {
                e !== this.$store.state.speechVoiceConfig.speechRate && this.speechSpeaking && this.restartSpeech(), this.$store.commit("setSpeechVoiceConfig", __merge(__merge({}, this.$store.state.speechVoiceConfig), {}, {
                    speechRate: e
                }))
            }
        },
        speechPitch: {
            get: function() {
                return this.$store.state.speechVoiceConfig.speechPitch
            },
            set: function(e) {
                e !== this.$store.state.speechVoiceConfig.speechPitch && this.speechSpeaking && this.restartSpeech(), this.$store.commit("setSpeechVoiceConfig", __merge(__merge({}, this.$store.state.speechVoiceConfig), {}, {
                    speechPitch: e
                }))
            }
        },
        ttsType: {
            get: function() {
                return this.$store.state.speechVoiceConfig.ttsType
            },
            set: function(e) {
                e !== this.$store.state.speechVoiceConfig.ttsType && this.speechSpeaking && this.restartSpeech(), this.$store.commit("setSpeechVoiceConfig", __merge(__merge({}, this.$store.state.speechVoiceConfig), {}, {
                    ttsType: e
                }))
            }
        },
        cacheTTSAudio: {
            get: function() {
                return this.$store.state.speechVoiceConfig.cacheTTSAudio
            },
            set: function(e) {
                this.$store.commit("setSpeechVoiceConfig", __merge(__merge({}, this.$store.state.speechVoiceConfig), {}, {
                    cacheTTSAudio: e
                }))
            }
        },
        isCarToon: function() {
            return !this.error && !this.isEpub && !this.isCbz && !this.isPdf && (this.content || "").indexOf("<img") >= 0
        },
        isAudio: function() {
            return !this.error && 1 === this.$store.getters.readingBook.type
        },
        isVideo: function() {
            return !this.error && 4 === this.$store.getters.readingBook.type
        },
        isEpub: function() {
            return !this.error && this.$store.getters.readingBook.bookUrl && this.$store.getters.readingBook.bookUrl.toLowerCase().endsWith(".epub")
        },
        isEpubResolve: function() {
            return this.isEpub && "iframe" !== this.config.epubMode
        },
        isEpubIframe: function() {
            return this.isEpub && "iframe" === this.config.epubMode
        },
        isCbz: function() {
            return !this.error && this.$store.getters.readingBook.bookUrl && this.$store.getters.readingBook.bookUrl.toLowerCase().endsWith(".cbz")
        },
        isPdf: function() {
            return !this.error && this.$store.getters.readingBook.bookUrl && this.$store.getters.readingBook.bookUrl.toLowerCase().endsWith(".pdf")
        },
        scrollOffset: function() {
            return this.$store.getters.config.fontSize * this.$store.getters.config.lineHeight * 2 + this.$store.getters.config.fontSize * this.$store.getters.config.paragraphSpace * 2
        },
        formatedTitle: function() {
            return this.formatChinese(this.title)
        },
        chineseFont: function() {
            return this.config.chineseFont
        },
        slideDistance: function() {
            return 16 + this.config.horizontalPadding
        },
        voiceList: function() {
            return "local" === this.ttsType ? this.localVoiceList : "edge" === this.ttsType ? EDGE_VOICE_LIST : this.httpTTSList
        }
    },
    methods: {
        /** 模板插值里的 str(...)：等价于 Vue 内部 _s（null/undefined 渲染成空串） */
        str: function(val) {
            return val == null ? "" : "object" === typeof val ? JSON.stringify(val, null, 2) : String(val)
        },
        init: function(e) {
            var t = this;
            return asyncToGenerator(regeneratorRuntime.mark((function n() {
                return regeneratorRuntime.wrap((function(n) {
                    while (1) switch (n.prev = n.next) {
                        case 0:
                            t.$store.getters.readingBook && t.$store.getters.readingBook.bookUrl ? (console.log("init: readingBook already set, skipping init"), t.initBook(e)) : null != t.$route.query && null != t.$route.query.bookUrl ? (console.log("init: loading book from URL parameters", t.$route.query.bookUrl), t.$store.getters.shelfBooks.length ? t.initBookFromUrl(e) : eventBus.$once("shelfBooksLoaded", (function() {
                                t.initBookFromUrl(e)
                            }))) : (t.$message.error("缓存信息丢失,请在书架选择书籍！"), t.title = "", t.content = "缓存信息丢失,请在书架选择书籍！", t.error = !0, t.show = !0);
                        case 1:
                        case "end":
                            return n.stop()
                    }
                }), n)
            })))()
        },
        initBookFromUrl: function(e) {
            var t, n, a = this,
                u = this.$store.getters.shelfBooks.find((function(e) {
                    return e.bookUrl === a.$route.query.bookUrl
                }));
            this.$store.commit("setReadingBook", __merge(__merge({}, u), {}, {
                name: u.name,
                bookUrl: u.bookUrl,
                index: null !== (t = null !== (n = u.index) && void 0 !== n ? n : u.durChapterIndex) && void 0 !== t ? t : 0,
                type: u.type,
                coverUrl: u.customCoverUrl || u.coverUrl,
                tocUrl: u.tocUrl,
                author: u.author,
                origin: u.origin,
                originName: u.originName,
                latestChapterTitle: u.latestChapterTitle,
                intro: u.intro
            })), this.initBook(e)
        },
        initBook: function(e) {
            var t = this;
            e || !this.lastReadingBook || this.lastReadingBook.bookUrl !== this.$store.getters.readingBook.bookUrl || this.lastReadingBook.index !== this.$store.getters.readingBook.index ? (this.title = "", this.show = !1, this.loading = this.$loading({
                target: this.$refs.content,
                lock: !0,
                text: "正在获取内容",
                spinner: "el-icon-loading",
                background: "rgba(0,0,0,0)"
            }), this.lastReadingBook = this.$store.getters.readingBook, this.autoShowPosition(), this.loadCatalog(!1, !0)) : (this.isScrollRead ? (this.scrollStartChapterIndex = this.chapterIndex, this.showPrevChapterSize = 0, this.computeShowChapterList().then((function() {
                t.autoShowPosition(!0)
            }))) : this.isEpubIframe ? this.autoShowPosition() : this.$nextTick((function() {
                t.autoShowPosition(!0)
            })), setTimeout((function() {
                t.$store.commit("setReadingBook", t.lastReadingBook)
            }), 100))
        },
        changeBook: function(e) {
            this.$message.info("换书成功"), this.popBookShelfVisible = !1, this.show = !1, this.$store.commit("setReadingBook", e), this.$router.push({
                path: "/reader",
                query: {
                    bookUrl: e.bookUrl
                }
            }), this.loadCatalog(!0, !0)
        },
        changeBookSource: function() {
            this.popBookSourceVisible = !1, this.show = !1, this.tryRefresh = !1, this.loadCatalog(!0, !0)
        },
        loadCatalog: function(e, t) {
            var n = this;
            this.api ? this.getCatalog(e).then((function(e) {
                if (e.data.isSuccess) {
                    var a = Object.assign({}, n.$store.getters.readingBook);
                    a.catalog = e.data.data, n.$store.commit("setReadingBook", a), n.$emit("loadCatalog");
                    var u = a.index || 0;
                    n.getContent(u)
                } else t && (n.title = "", n.content = "获取章节目录失败！\n" + e.data.errorMsg, n.error = !0, n.show = !0, n.$emit("showContent")), n.loading.close()
            }), (function(e) {
                n.loading.close(), n.$message.error("获取书籍目录列表 " + (e && e.toString()))
            })) : setTimeout((function() {
                n.loadCatalog && n.loadCatalog(e)
            }), 1e3)
        },
        getCatalog: function(e) {
            var t = this,
                n = {
                    url: this.$store.getters.readingBook.bookUrl,
                    refresh: e ? 1 : 0
                };
            return this.$route.query.search && (n.bookSourceUrl = this.$store.getters.readingBook.origin), cachedFetch((function() {
                return axios.post(t.api + "/getChapterList", n)
            }), this.$store.getters.readingBook.name + "_" + this.$store.getters.readingBook.author + "@" + this.$store.getters.readingBook.bookUrl + "@chapterList")
        },
        refreshCatalog: function() {
            return this.loadCatalog(!0)
        },
        getBookContent: function(e, t, n, a) {
            return this.$root.$children[0].getBookContent(e, t, n, a)
        },
        refreshContent: function() {
            this.autoShowPosition(), this.getContent(this.$store.getters.readingBook.index, !0)
        },
        gotoChapter: function(e) {
            if ("undefined" !== typeof this.$store.getters.readingBook.catalog[e]) {
                var t = __merge({}, this.$store.getters.readingBook);
                if (t.index = e, this.$store.commit("setReadingBook", t), this.isScrollRead) return this.scrollStartChapterIndex = e, void this.computeShowChapterList(!0);
                this.getContent(e)
            } else this.$message.error("章节信息错误")
        },
        getContent: function(e, t) {
            var n = this;
            this.show = !1, this.contentInnerStyle = {}, this.loading && this.loading.visible || (this.loading = this.$loading({
                target: this.$refs.content,
                lock: !0,
                text: t ? "正在刷新内容" : "正在获取内容",
                spinner: "el-icon-loading",
                background: "rgba(0,0,0,0)"
            }));
            var a = this.$store.getters.readingBook.bookUrl;
            try {
                var u = __merge({}, this.$store.getters.readingBook);
                u.index = e, this.$store.commit("setReadingBook", u)
            } catch (re) {
                console.error(re)
            }
            if (this.toTop(0), this.$store.getters.readingBook.catalog[e]) {
                var o = this.$store.getters.readingBook.catalog[e].title,
                    r = this.$store.getters.readingBook.catalog[e].index;
                this.title = o, this.getBookContent(r, {}, t).then((function(t) {
                    if (a === n.$store.getters.readingBook.bookUrl && e === n.$store.getters.readingBook.index) {
                        if (t.data.isCache && n.saveBookProgress(), t.data.isSuccess) {
                            var u = t.data.data;
                            n.originalContent = u, n.content = n.filterContent(u), n.addChapterContentToCache({
                                bookUrl: a,
                                index: e,
                                title: o,
                                content: u,
                                error: !1
                            }), n.loading.close(), n.error = !1, n.show = !0, n.$emit("showContent")
                        } else n.content = "获取章节内容失败！\n" + t.data.errorMsg, n.addChapterContentToCache({
                            bookUrl: a,
                            index: e,
                            title: o,
                            content: "获取章节内容失败！\n" + t.data.errorMsg,
                            error: !0
                        }), n.error = !0, n.show = !0, n.$emit("showContent"), n.loading.close();
                        n.isScrollRead && n.computeShowChapterList()
                    }
                }), (function(t) {
                    if (a === n.$store.getters.readingBook.bookUrl && e === n.$store.getters.readingBook.index) throw n.content = "获取章节内容失败！\n" + (t && t.toString()), n.addChapterContentToCache({
                        bookUrl: a,
                        index: e,
                        title: o,
                        content: "获取章节内容失败！\n" + (t && t.toString()),
                        error: !0
                    }), n.error = !0, n.show = !0, n.$emit("showContent"), n.loading.close(), n.$message.error("获取章节内容失败 " + (t && t.toString())), n.isScrollRead && n.computeShowChapterList(), t
                }))
            } else this.tryRefresh ? (this.tryRefresh = !1, this.content = "获取章节内容失败，请更新目录！", this.error = !0, this.show = !0, this.$emit("showContent"), this.loading.close()) : (this.tryRefresh = !0, this.refreshCatalog())
        },
        toggleEditContent: function() {
            this.isEditContent ? (this.isEditContent = !1, this.saveContent()) : this.isEditContent = !0
        },
        saveContent: function() {
            var e = this;
            return asyncToGenerator(regeneratorRuntime.mark((function t() {
                var n, a, u;
                return regeneratorRuntime.wrap((function(t) {
                    while (1) switch (t.prev = t.next) {
                        case 0:
                            return t.next = 2, e.$confirm("是否保存章节内容?", "提示", {
                                confirmButtonText: "保存",
                                cancelButtonText: "取消",
                                type: "warning"
                            })["catch"]((function() {
                                return !1
                            }));
                        case 2:
                            if (n = t.sent, n) {
                                t.next = 7;
                                break
                            }
                            return e.autoShowPosition(), e.getContent(e.$store.getters.readingBook.index), t.abrupt("return");
                        case 7:
                            return a = e.getTextContent(e.getContentParagraphContainer()), u = "localCache@" + e.$store.getters.readingBook.name + "_" + e.$store.getters.readingBook.author + "@" + e.$store.getters.readingBook.bookUrl + "@chapterContent-" + e.chapterIndex, t.abrupt("return", axios.post(e.api + "/saveBookContent", {
                                url: e.$store.getters.readingBook.bookUrl,
                                index: e.chapterIndex,
                                content: a
                            }).then(function() {
                                var t = asyncToGenerator(regeneratorRuntime.mark((function t(n) {
                                    return regeneratorRuntime.wrap((function(t) {
                                        while (1) switch (t.prev = t.next) {
                                            case 0:
                                                if (!n.data.isSuccess) {
                                                    t.next = 4;
                                                    break
                                                }
                                                return t.next = 3, window.$cacheStorage.removeItem(u)["catch"]((function() {}));
                                            case 3:
                                                e.$message.success("保存成功！注意：刷新章节内容将会失效");
                                            case 4:
                                            case "end":
                                                return t.stop()
                                        }
                                    }), t)
                                })));
                                return function(e) {
                                    return t.apply(this, arguments)
                                }
                            }())["catch"]((function(t) {
                                e.$message.error("保存章节内容失败 " + (t && t.toString()))
                            })));
                        case 10:
                        case "end":
                            return t.stop()
                    }
                }), t)
            })))()
        },
        getTextContent: function(e) {
            if (!e) return "";
            if ("H3" === e.nodeName) return "";
            if ("P" === e.nodeName) return e.innerText;
            if ("IMG" === e.nodeName) {
                var t = e.cloneNode(!0),
                    n = t.getAttribute("data-error");
                return t.removeAttribute("data-error"), t.removeAttribute("data-src"), t.removeAttribute("lazy"), t.setAttribute("src", n), t.outerHTML
            }
            if ("#text" === e.nodeName) return e.nodeValue;
            for (var a = "", u = 0; u < e.childNodes.length; u++) u > 0 && "#text" !== e.childNodes[u].nodeName && (a += "\n"), a += this.getTextContent(e.childNodes[u]);
            return a.replace(/^\n*/, "").replace(/\n*$/, "")
        },
        filterContent: function(e) {
            var t = this;
            if (this.isEpub || this.isAudio || this.isVideo) return e;
            if (!e) return e;
            try {
                this.filterRules.forEach((function(n) {
                    if ("undefined" === typeof n.isEnabled || !1 !== n.isEnabled) {
                        var a = n.scope.split(";");
                        "*" !== a[0] && a[0] !== t.$store.getters.readingBook.name || (1 == a.length || a.length > 1 && a[1] === t.$store.getters.readingBook.bookUrl) && (e = n.isRegex ? e.replace(new RegExp(n.pattern, "ig"), n.replacement) : e.replaceAll(n.pattern, n.replacement))
                    }
                }))
            } catch (re) {}
            return e.replace(/\\n+/g, "\n"), e = this.formatChinese(e), e
        },
        loadShowChapter: function(e, t) {
            var n = this;
            if (!t && this.chapterContentCache && this.chapterContentCache.chapters[e] && !this.chapterContentCache.chapters[e].error) return e >= this.chapterIndex - this.showPrevChapterSize && e <= this.chapterIndex + this.showNextChapterSize && this.computeShowChapterList(), Promise.resolve();
            var a = this.$store.getters.readingBook.bookUrl;
            if (!this.$store.getters.readingBook.catalog) return new Promise((function(a) {
                n.$once("loadCatalog", (function() {
                    n.loadShowChapter(e, t).then(a)
                }))
            }));
            if (!this.$store.getters.readingBook.catalog[e]) return Promise.reject("章节不存在");
            var u = this.$store.getters.readingBook.catalog[e].title,
                o = this.$store.getters.readingBook.catalog[e].index;
            return this.getBookContent(o, {}, t, !0).then((function(t) {
                t.data.isSuccess ? n.addChapterContentToCache({
                    bookUrl: a,
                    index: e,
                    title: u,
                    content: t.data.data,
                    error: !1
                }) : n.addChapterContentToCache({
                    bookUrl: a,
                    index: e,
                    title: u,
                    content: "获取章节内容失败！\n" + t.data.errorMsg,
                    error: !0
                })
            }), (function(t) {
                throw n.addChapterContentToCache({
                    bookUrl: a,
                    index: e,
                    title: u,
                    content: "获取章节内容失败！\n" + (t && t.toString()),
                    error: !0
                }), t
            }))
        },
        addChapterContentToCache: function(e) {
            this.chapterContentCache && this.chapterContentCache.bookUrl === this.readingBook.bookUrl || (this.chapterContentCache = {
                bookUrl: this.readingBook.bookUrl,
                chapters: {}
            }), "undefined" !== typeof this.chapterContentCache.chapters[e.index] && e.error && !this.chapterContentCache.chapters[e.index].error || (e.isVolume = !!(this.readingBook.catalog[e.index] || {}).isVolume, this.chapterContentCache.chapters[e.index] = e)
        },
        computeShowChapterList: function(e) {
            var t = this;
            if (!this.chapterContentCache) return new Promise((function(n) {
                setTimeout((function() {
                    t.computeShowChapterList(e).then(n)
                }), 10)
            }));
            if (!this.isScrollRead) return Promise.resolve();
            var n = [],
                a = this.scrollStartChapterIndex || this.chapterIndex;
            "上下滚动2" === this.config.readMethod && (a = this.chapterIndex - this.showPrevChapterSize);
            for (var u = [], o = a; o <= this.chapterIndex + this.showNextChapterSize; o++) this.chapterContentCache.chapters[o] ? n.push(__merge(__merge({}, this.chapterContentCache.chapters[o]), {}, {
                content: this.filterContent(this.chapterContentCache.chapters[o].content)
            })) : u.push(this.loadShowChapter(o));
            if (u.length) return Promise.all(u).then((function() {
                t.computeShowChapterList(e)
            }));
            this.saveReadingPosition(), this.startSavePosition = !1, this.showChapterList = n, this.$nextTick((function() {
                t.computePages((function() {
                    e ? (t.toTop(0), t.startSavePosition = !0) : "上下滚动2" === t.config.readMethod ? t.autoShowPosition(!0) : t.startSavePosition = !0
                }))
            }))
        },
        saveBookProgress: function() {
            return axios.post(this.api + "/saveBookProgress", {
                url: this.$store.getters.readingBook.bookUrl,
                index: this.chapterIndex
            }, {
                silent: !0
            })["catch"]((function() {}))
        },
        toTop: function(e) {
            this.$store.state.miniInterface ? this.scrollContent(-(document.documentElement.scrollTop || document.body.scrollTop), e) : scrollToElement(this.$refs.top, {
                duration: e
            })
        },
        toBottom: function(e) {
            scrollToElement(this.$refs.bottom, {
                duration: e
            })
        },
        toNextChapter: function(e) {
            if (this.$store.getters.readingBook && this.$store.getters.readingBook.bookUrl && this.$store.getters.readingBook.catalog) {
                var t = this.$store.getters.readingBook.index;
                if (t++, "undefined" !== typeof this.$store.getters.readingBook.catalog[t]) {
                    if (this.isScrollRead) return this.scrollStartChapterIndex = t, void this.computeShowChapterList(!0);
                    this.getContent(t)
                } else e && e(), this.$message.error("本章是最后一章")
            } else e && e()
        },
        toLastChapter: function(e) {
            if (this.$store.getters.readingBook && this.$store.getters.readingBook.bookUrl && this.$store.getters.readingBook.catalog) {
                var t = this.$store.getters.readingBook.index;
                if (t--, "undefined" !== typeof this.$store.getters.readingBook.catalog[t]) {
                    if (this.isScrollRead) return this.scrollStartChapterIndex = t, void this.computeShowChapterList(!0);
                    this.getContent(t)
                } else this.$message.error("本章是第一章"), e && e()
            } else e && e()
        },
        toShelf: function() {
            this.$router.push("/")
        },
        onContentChange: function() {
            this.computePages()
        },
        computePages: function(e) {
            var t = this;
            if (this.$refs.bookContentRef && this.$refs.bookContentRef.$el) {
                if (this.isSlideRead) this.isEpub ? this.totalPages = Math.ceil(this.$refs.bookContentRef.iframeSize.scrollWidth / (this.windowSize.width - this.slideDistance)) : this.totalPages = Math.ceil(this.$refs.bookContentRef.$el.scrollWidth / (this.windowSize.width - this.slideDistance));
                else {
                    var n = this.windowSize.height - this.scrollOffset;
                    if (this.totalPages = Math.ceil(this.$refs.bookContentRef.$el.scrollHeight / n), !this.isScrollRead) {
                        var a = n * this.totalPages - this.$refs.bookContentRef.$el.scrollHeight - 68;
                        this.contentInnerStyle = {
                            "padding-bottom": a + "px"
                        }
                    }
                }
                this.$emit("afterComputePages"), e && e()
            } else setTimeout((function() {
                t.computePages(e)
            }), 30)
        },
        nextPage: function(e) {
            var t = this;
            if (this.show && !this.transforming)
                if (this.isSlideRead) this.currentPage < this.totalPages ? ("undefined" === typeof e && (this.transformX = -(this.windowSize.width - this.slideDistance) * (this.currentPage - 1)), this.currentPage += 1, this.transforming = !0, this.transform("undefined" === typeof e ? -(this.windowSize.width - this.slideDistance) : e, this.animateMSTime)) : this.toNextChapter((function() {
                    "undefined" !== typeof e && t.showPage(t.currentPage, 0)
                }));
                else if (this.nearBottom(10)) this.currentPage = 1, this.toNextChapter();
            else {
                this.currentPage += 1;
                var n = this.windowSize.height - this.scrollOffset;
                this.transforming = !0, this.scrollContent(n, this.animateMSTime)
            }
        },
        prevPage: function(e) {
            var t = this;
            if (this.show && !this.transforming)
                if (this.isSlideRead) this.currentPage > 1 ? ("undefined" === typeof e && (this.transformX = -(this.windowSize.width - this.slideDistance) * (this.currentPage - 1)), this.currentPage -= 1, this.transforming = !0, this.transform("undefined" === typeof e ? this.windowSize.width - this.slideDistance : e, this.animateMSTime)) : (this.onNextContentPagesInit((function() {
                    t.showPage(t.totalPages, 0)
                })), this.toLastChapter((function() {
                    "undefined" !== typeof e && t.showPage(t.currentPage, 0)
                })));
                else if ((document.documentElement.scrollTop || document.body.scrollTop) > 0) {
                this.currentPage -= 1;
                var n = -this.windowSize.height + this.scrollOffset;
                this.transforming = !0, this.scrollContent(n, this.animateMSTime)
            } else this.currentPage = 1, this.onNextContentPagesInit((function() {
                t.showPage(t.totalPages, 0)
            })), this.toLastChapter()
        },
        showPage: function(e, t) {
            if (this.show)
                if (this.currentPage = Math.min(e, this.totalPages), this.isSlideRead) {
                    var n = -(this.windowSize.width - this.slideDistance) * (this.currentPage - 1) - this.transformX;
                    this.transform(n, "undefined" === typeof t ? this.animateMSTime : t)
                } else {
                    var a = (this.windowSize.height - 10) * (this.currentPage - 1) - (document.documentElement.scrollTop || document.body.scrollTop);
                    this.scrollContent(a, "undefined" === typeof t ? this.animateMSTime : t)
                }
        },
        transform: function(e, t, n) {
            var a = this;
            n && (this.transformX = 0);
            var u = function() {
                a.isEpub ? a.$refs.bookContentRef && a.$refs.bookContentRef.transformX(a.transformX + e) : a.contentStyle = {
                    transform: "translateX(".concat(a.transformX + e, "px)")
                }, a.transformX += e, a.transforming = !1, setTimeout(a.saveReadingPosition, t)
            };
            if (t) {
                var o = Bt.Utils.makeEaseInOut(Bt.Timings.power.bind(null, 3));
                new Bt({
                    duration: t || 500,
                    timing: o,
                    draw: function(t) {
                        a.isEpub ? a.$refs.bookContentRef && a.$refs.bookContentRef.transformX(a.transformX + e * t) : a.contentStyle = {
                            transform: "translateX(".concat(a.transformX + e * t, "px)")
                        }
                    },
                    onEnd: u
                })
            } else u()
        },
        scrollContent: function(e, t, n) {
            var a = this,
                u = n ? 0 : document.documentElement.scrollTop || document.body.scrollTop,
                o = function() {
                    document.documentElement.scrollTop = u + e, document.body.scrollTop = u + e, a.transforming = !1, setTimeout(a.saveReadingPosition, t)
                };
            if (t) {
                var r = Bt.Utils.makeEaseInOut(Bt.Timings.power.bind(null, 3));
                new Bt({
                    duration: t || 500,
                    timing: r,
                    draw: function(t) {
                        document.documentElement.scrollTop = u + e * t, document.body.scrollTop = u + e * t
                    },
                    onEnd: o
                })
            } else o()
        },
        handlerClick: function(e) {
            this.isEpub || (this.lastTouch || this.ignoreNextClick || this.eventHandler(e), this.ignoreNextClick = !1)
        },
        handleTouchStart: function(e) {
            this.lastSelection = this.checkSelection(), this.lastSelection || this.isAudio || this.isVideo || (this.lastTouch = !1, this.lastMoveX = !1, e.touches && e.touches[0] && (this.lastTouch = e.touches[0]))
        },
        handleTouchMove: function(e) {
            if (!this.checkSelection() && e.touches && e.touches[0] && this.lastTouch && (this.lastMoveY = e.touches[0].clientY - this.lastTouch.clientY, this.isSlideRead)) {
                e.preventDefault && e.preventDefault(), e.stopPropagation && e.stopPropagation();
                var t = e.touches[0].clientX - this.lastTouch.clientX;
                this.isEpub ? this.$refs.bookContentRef && this.$refs.bookContentRef.transformX(this.transformX + t) : this.contentStyle = {
                    transform: "translateX(".concat(this.transformX + t, "px)")
                }, this.lastMoveX = t
            }
        },
        handleTouchEnd: function() {
            var e = this;
            this.checkSelection(!0) || (this.lastMoveX ? (this.transformX += this.lastMoveX, this.lastMoveX > 0 ? this.prevPage(this.windowSize.width - this.slideDistance - this.lastMoveX) : this.nextPage(-(this.windowSize.width - this.slideDistance) - this.lastMoveX)) : Math.abs(this.lastMoveY) <= 3 && this.lastTouch && (this.isEpub || this.eventHandler(this.lastTouch)), setTimeout((function() {
                e.lastTouch = !1, e.lastMoveX = !1, e.lastMoveY = !1
            }), 300))
        },
        epubTouch: function(e) {
            switch (e.event) {
                case "touchstart":
                    this.handleTouchStart(e.data);
                    break;
                case "touchmove":
                    this.handleTouchMove(e.data);
                    break;
                default:
                    this.handleTouchEnd()
            }
        },
        epubClickHash: function(e) {
            this.isSlideRead ? "undefined" !== typeof e.left && this.showPage(this.currentPage + Math.round(e.left / (this.windowSize.width - this.slideDistance)) + (e.left > 0 ? 1 : 0), 0) : "undefined" !== typeof e.top && this.scrollContent(e.top - (this.$store.state.miniInterface ? this.getFirstParagraphPos().bottom : 0) - (0 | window.webAppDistance) - (0 | this.$store.state.safeArea.top), 0, !0)
        },
        epubLocationChangeHandler: function(e) {
            function t(e) {
                var t = document.createElement("a");
                return t.href = e, decodeURIComponent(t.pathname)
            }
            e = t(e);
            var n = this.catalog[this.chapterIndex];
            if (n) {
                for (var a = this.content.replace(n.url, ""), u = e.replace(a, ""), o = -1, r = 0; r < this.catalog.length; r++)
                    if (this.catalog[r].url === u) {
                        o = r;
                        break
                    } if (o >= 0) {
                    var s = __merge({}, this.$store.getters.readingBook);
                    s.index = o, this.$store.commit("setReadingBook", s), this.title = this.$store.getters.readingBook.catalog[o].title
                }
            }
        },
        eventHandler: function(e) {
            if (this.checkSelection(!0)) this.ignoreNextClick = !0;
            else if (this.popBookSourceVisible || this.popBookShelfVisible || this.popCataVisible || this.readSettingsVisible) this.isEpub && (this.popBookSourceVisible = !1, this.popBookShelfVisible = !1, this.popCataVisible = !1, this.readSettingsVisible = !1);
            else if (this.isAudio) this.showReadBar || (this.showToolBar = !this.showToolBar);
            else if (this.autoReading) this.showToolBar = !this.showToolBar;
            else if (this.isEditContent) this.showToolBar = !this.showToolBar;
            else {
                var t = this.windowSize.width / 2,
                    n = this.windowSize.height / 2,
                    a = .2 * this.windowSize.width,
                    u = .2 * this.windowSize.height;
                if (this.isEpub && "iframe" === this.config.epubMode && (e.clientY = e.clientY + 45 - (document.documentElement.scrollTop || document.body.scrollTop)), Math.abs(e.clientY - n) <= u && Math.abs(e.clientX - t) <= a) this.showReadBar || (this.showToolBar = !this.showToolBar);
                else {
                    if (this.isVideo) return;
                    if ("下一页" === this.$store.getters.config.clickMethod) return this.showToolBar = !1, void this.nextPage();
                    if ("不翻页" === this.$store.getters.config.clickMethod) return void(this.showToolBar = !this.showToolBar);
                    "固定模式" === this.$store.getters.config.clickMethod ? e.clientY < n - u || e.clientY > n - u && e.clientX < t - a ? (this.showToolBar = !1, this.prevPage()) : (e.clientY > n + u || e.clientY < n + u && e.clientX > t + a) && (this.showToolBar = !1, this.nextPage()) : this.isSlideRead ? e.clientX > t ? (this.showToolBar = !1, this.nextPage()) : e.clientX < t && (this.showToolBar = !1, this.prevPage()) : e.clientY > n ? (this.showToolBar = !1, this.nextPage()) : e.clientY < n && (this.showToolBar = !1, this.prevPage())
                }
            }
        },
        keydownHandler: function(e, t) {
            if (!(this.popBookSourceVisible || this.popBookShelfVisible || this.popCataVisible || this.readSettingsVisible || this.showTextFilterPrompting) && (t || document.activeElement === document.body) && !this.isAudio && !this.isVideo) {
                var n = {
                        37: "ArrowLeft",
                        38: "ArrowUp",
                        39: "ArrowRight",
                        40: "ArrowDown",
                        27: "Escape",
                        32: "Space",
                        33: "PageUp",
                        34: "PageDown",
                        35: "End",
                        36: "Home"
                    },
                    a = "Space" === e.code ? "Space" : e.key || n[e.keyCode];
                if ("自定义" !== this.config.quickKeyMode) switch (a) {
                    case "ArrowLeft":
                        e.preventDefault && e.preventDefault(), e.stopPropagation && e.stopPropagation(), this.showToolBar = !1, this.isSlideRead ? this.prevPage() : this.toLastChapter();
                        break;
                    case "ArrowRight":
                        e.preventDefault && e.preventDefault(), e.stopPropagation && e.stopPropagation(), this.showToolBar = !1, this.isSlideRead ? this.nextPage() : this.toNextChapter();
                        break;
                    case "ArrowUp":
                    case "PageUp":
                        (this.isSlideRead || "PageUp" === a) && (e.preventDefault && e.preventDefault(), e.stopPropagation && e.stopPropagation(), this.showToolBar = !1, this.prevPage());
                        break;
                    case "ArrowDown":
                    case "PageDown":
                        this.isSlideRead || "PageDown" === a ? (e.preventDefault && e.preventDefault(), e.stopPropagation && e.stopPropagation(), this.showToolBar = !1, this.nextPage()) : !this.isSlideRead && this.nearBottom(10) && (this.currentPage = 1, this.toNextChapter());
                        break;
                    case "Escape":
                        this.toShelf();
                        break;
                    case "Home":
                        e.preventDefault && e.preventDefault(), e.stopPropagation && e.stopPropagation(), this.showToolBar = !1, this.showPage(0, 0);
                        break;
                    case "End":
                        e.preventDefault && e.preventDefault(), e.stopPropagation && e.stopPropagation(), this.showToolBar = !1, this.showPage(this.totalPages, 0);
                        break;
                    case "Space":
                        if (!this.isSlideRead)
                            if (e.preventDefault && e.preventDefault(), e.stopPropagation && e.stopPropagation(), this.showToolBar = !1, this.nearBottom(10)) this.currentPage = 1, this.toNextChapter();
                            else {
                                var u = this.windowSize.height / 2;
                                this.scrollContent(u, this.animateMSTime)
                            } break
                } else this.quickKeyHandle(a, e)
            }
        },
        quickKeyHandle: function(e, t) {
            if (this.config.quickKey[e]) {
                var n = this.config.quickKey[e];
                switch (n) {
                    case "上一页":
                        t.preventDefault && t.preventDefault(), t.stopPropagation && t.stopPropagation(), this.showToolBar = !1, this.prevPage();
                        break;
                    case "下一页":
                        t.preventDefault && t.preventDefault(), t.stopPropagation && t.stopPropagation(), this.showToolBar = !1, this.nextPage();
                        break;
                    case "上半页":
                        if (!this.isSlideRead)
                            if (t.preventDefault && t.preventDefault(), t.stopPropagation && t.stopPropagation(), this.showToolBar = !1, this.nearBottom(10)) this.toLastChapter();
                            else {
                                var a = -this.windowSize.height / 2;
                                this.scrollContent(a, this.animateMSTime)
                            } break;
                    case "下半页":
                        if (!this.isSlideRead)
                            if (t.preventDefault && t.preventDefault(), t.stopPropagation && t.stopPropagation(), this.showToolBar = !1, this.nearBottom(10)) this.currentPage = 1, this.toNextChapter();
                            else {
                                var u = this.windowSize.height / 2;
                                this.scrollContent(u, this.animateMSTime)
                            } break;
                    case "上一章":
                        t.preventDefault && t.preventDefault(), t.stopPropagation && t.stopPropagation(), this.toLastChapter();
                        break;
                    case "下一章":
                        t.preventDefault && t.preventDefault(), t.stopPropagation && t.stopPropagation(), this.currentPage = 1, this.toNextChapter();
                        break;
                    case "返回":
                        this.toShelf();
                        break;
                    case "首页":
                        t.preventDefault && t.preventDefault(), t.stopPropagation && t.stopPropagation(), this.showToolBar = !1, this.showPage(0, 0);
                        break;
                    case "尾页":
                        t.preventDefault && t.preventDefault(), t.stopPropagation && t.stopPropagation(), this.showToolBar = !1, this.showPage(this.totalPages, 0);
                        break;
                    default:
                        break
                }
            }
        },
        formatProgressTip: function(e) {
            return "第 ".concat(e || this.progressValue, "/").concat(this.totalPages, " 页")
        },
        formatTime: function() {
            var e = new Date,
                t = function(e) {
                    return e >= 10 ? "" + e : "0" + e
                };
            this.timeStr = t(e.getHours()) + ":" + t(e.getMinutes())
        },
        checkSelection: function(e) {
            var t = this,
                n = "";
            return this.isEpubIframe ? this.$refs.bookContentRef && this.$refs.bookContentRef.$el && this.$refs.bookContentRef.$el.contentWindow && this.$refs.bookContentRef.$el.contentWindow.getSelection ? n = this.$refs.bookContentRef.$el.contentWindow.getSelection().toString() : this.$refs.bookContentRef && this.$refs.bookContentRef.$el && this.$refs.bookContentRef.$el.contentWindow && this.$refs.bookContentRef.$el.contentWindow.document && this.$refs.bookContentRef.$el.contentWindow.document.selection && "Control" != this.$refs.bookContentRef.$el.contentWindow.document.selection.type && (n = this.$refs.bookContentRef.$el.contentWindow.document.selection.createRange().text) : window.getSelection ? n = window.getSelection().toString() : document.selection && "Control" != document.selection.type && (n = document.selection.createRange().text), n && e && setTimeout((function() {
                "过滤弹窗" !== t.$store.getters.config.selectionAction && "操作弹窗" !== t.$store.getters.config.selectionAction || t.showTextOperate(n)
            }), 200), n
        },
        showTextOperate: function(e) {
            var t = this;
            return asyncToGenerator(regeneratorRuntime.mark((function n() {
                var a;
                return regeneratorRuntime.wrap((function(n) {
                    while (1) switch (n.prev = n.next) {
                        case 0:
                            if (!t.isEditContent) {
                                n.next = 2;
                                break
                            }
                            return n.abrupt("return");
                        case 2:
                            return n.next = 4, t.$confirm("请选择操作?", "提示", {
                                confirmButtonText: "添加过滤规则",
                                cancelButtonText: "添加书签",
                                type: "warning",
                                closeOnClickModal: !1,
                                closeOnPressEscape: !1,
                                distinguishCancelAndClose: !0
                            })["catch"]((function(e) {
                                return "close" === e && "close"
                            }));
                        case 4:
                            if (a = n.sent, "close" !== a) {
                                n.next = 7;
                                break
                            }
                            return n.abrupt("return");
                        case 7:
                            if (!a) {
                                n.next = 11;
                                break
                            }
                            return n.abrupt("return", t.showTextFilterPrompt(e));
                        case 11:
                            return n.abrupt("return", t.showAddBookmark(e));
                        case 12:
                        case "end":
                            return n.stop()
                    }
                }), n)
            })))()
        },
        showTextFilterPrompt: function(e) {
            var t = this;
            return asyncToGenerator(regeneratorRuntime.mark((function n() {
                var a;
                return regeneratorRuntime.wrap((function(n) {
                    while (1) switch (n.prev = n.next) {
                        case 0:
                            if (!t.showTextFilterPrompting) {
                                n.next = 2;
                                break
                            }
                            return n.abrupt("return");
                        case 2:
                            if (e.replace(/^\s+/, "").replace(/\s+$/, "")) {
                                n.next = 4;
                                break
                            }
                            return n.abrupt("return");
                        case 4:
                            a = Object.assign({}, TEXT_REPLACE_RULE_DEFAULT, {
                                name: "文本替换",
                                pattern: e,
                                replacement: "",
                                isRegex: !1,
                                isEnabled: !0,
                                scope: t.$store.getters.readingBook.name + ";" + t.$store.getters.readingBook.bookUrl
                            }), t.showTextFilterPrompting = !0, eventBus.$emit("showReplaceRuleForm", a, !0, (function() {
                                t.showTextFilterPrompting = !1
                            }));
                        case 7:
                        case "end":
                            return n.stop()
                    }
                }), n)
            })))()
        },
        showAddBookmark: function(e) {
            var t = this;
            return asyncToGenerator(regeneratorRuntime.mark((function n() {
                var a, u, r, i, s, l, c, p, d;
                return regeneratorRuntime.wrap((function(n) {
                    while (1) switch (n.prev = n.next) {
                        case 0:
                            if (!t.showAddBookmarking) {
                                n.next = 2;
                                break
                            }
                            return n.abrupt("return");
                        case 2:
                            if (a = e.replace(/^\s+/, "").replace(/\s+$/, ""), u = t.getContentMatchParagraph(a, 1, .7), u) {
                                n.next = 7;
                                break
                            }
                            return t.$message.error("选择1-2段整段文字才能定位段落"), n.abrupt("return");
                        case 7:
                            if (r = 5, i = 150, s = [u], l = u.innerText + "\n", !(s.length < r && l.length < i)) {
                                n.next = 27;
                                break
                            }
                            c = -1, p = t.getContentParagraphList(), d = 0;
                        case 15:
                            if (!(d < p.length)) {
                                n.next = 27;
                                break
                            }
                            if (!(c > 0 && d > c)) {
                                n.next = 23;
                                break
                            }
                            if (s.push(p[d]), l += p[d].innerText + "\n", !(s.length >= r || l.length >= i)) {
                                n.next = 21;
                                break
                            }
                            return n.abrupt("break", 27);
                        case 21:
                            n.next = 24;
                            break;
                        case 23:
                            s[s.length - 1] === p[d] && (c = d);
                        case 24:
                            d++, n.next = 15;
                            break;
                        case 27:
                            t.showAddBookmarkForm(l);
                        case 28:
                        case "end":
                            return n.stop()
                    }
                }), n)
            })))()
        },
        addBookmark: function() {
            for (var e = this.getCurrentParagraph(), t = this.getContentParagraphList(), n = "", a = -1, u = 0; u < t.length; u++)
                if (e === t[u] && (a = u), a >= 0 && u >= a && (n += t[u].innerText + "\n", u >= a + 5)) break;
            this.showAddBookmarkForm(n)
        },
        showAddBookmarkForm: function(e) {
            var t = this;
            e = e.replace(/\\n*$/, "");
            var n = Object.assign({}, BOOKMARK_FORM_DEFAULT, {
                bookName: this.$store.getters.readingBook.name,
                bookAuthor: this.$store.getters.readingBook.author,
                chapterIndex: this.chapterIndex,
                chapterPos: this.currentPage,
                chapterName: this.title,
                bookText: e,
                content: ""
            });
            this.showAddBookmarking = !0, eventBus.$emit("showBookmarkForm", n, !0, (function() {
                t.showAddBookmarking = !1
            }))
        },
        toogleNight: function() {
            this.isNight ? this.$store.commit("setNightTheme", !1) : this.$store.commit("setNightTheme", !0)
        },
        fetchVoiceList: function() {
            this.localVoiceList = window.speechSynthesis.getVoices().sort((function(e, t) {
                return e.lang.startsWith("zh-") && t.lang.startsWith("zh-") ? e.lang > t.lang ? 1 : e.lang < t.lang ? -1 : 0 : e.lang.startsWith("zh-") ? -1 : t.lang.startsWith("zh-") || e.lang > t.lang ? 1 : e.lang < t.lang ? -1 : 0
            }))
        },
        changeSpeechRate: function(e) {
            this.speechRate = e
        },
        changeSpeechPitch: function(e) {
            this.speechPitch = e
        },
        changeSpeechMinutes: function(e) {
            this.speechMinutes = e, this.speechEndTime = e ? (new Date).getTime() + 60 * e * 1e3 : 0
        },
        showHttpTTSDialog: function() {
            eventBus.$emit("showHttpTTSDialog")
        },
        startSpeech: function() {
            if (!this.error && this.voiceName) {
                if (window.speechSynthesis.speaking) try {
                    window.speechSynthesis.cancel()
                } catch (e) {}
                if (this.speechEndTime > 0 && (new Date).getTime() > this.speechEndTime) return this.$message.info("定时关闭朗读"), void(this.speechEndTime = 0);
                "local" !== this.ttsType ? this.speechCurrentByTTS() : this.speechCurrent()
            }
        },
        speechCurrentByTTS: function() {
            var e = this;
            return asyncToGenerator(regeneratorRuntime.mark((function t() {
                var n, a, u, r, i, s, l;
                return regeneratorRuntime.wrap((function(t) {
                    while (1) switch (t.prev = t.next) {
                        case 0:
                            if (n = e.getCurrentParagraph(), n) {
                                t.next = 4;
                                break
                            }
                            return e.speechNext(), t.abrupt("return");
                        case 4:
                            if (a = n.innerText, !a.match(/^([\t-\r \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000\uFEFF]|(?:[\0-\x1F\x7F-\x9F\xAD\u0378\u0379\u0380-\u0383\u038B\u038D\u03A2\u0530\u0557\u0558\u058B\u058C\u0590\u05C8-\u05CF\u05EB-\u05EE\u05F5-\u0605\u061C\u06DD\u070E\u070F\u074B\u074C\u07B2-\u07BF\u07FB\u07FC\u082E\u082F\u083F\u085C\u085D\u085F\u086B-\u086F\u088F-\u0896\u08E2\u0984\u098D\u098E\u0991\u0992\u09A9\u09B1\u09B3-\u09B5\u09BA\u09BB\u09C5\u09C6\u09C9\u09CA\u09CF-\u09D6\u09D8-\u09DB\u09DE\u09E4\u09E5\u09FF\u0A00\u0A04\u0A0B-\u0A0E\u0A11\u0A12\u0A29\u0A31\u0A34\u0A37\u0A3A\u0A3B\u0A3D\u0A43-\u0A46\u0A49\u0A4A\u0A4E-\u0A50\u0A52-\u0A58\u0A5D\u0A5F-\u0A65\u0A77-\u0A80\u0A84\u0A8E\u0A92\u0AA9\u0AB1\u0AB4\u0ABA\u0ABB\u0AC6\u0ACA\u0ACE\u0ACF\u0AD1-\u0ADF\u0AE4\u0AE5\u0AF2-\u0AF8\u0B00\u0B04\u0B0D\u0B0E\u0B11\u0B12\u0B29\u0B31\u0B34\u0B3A\u0B3B\u0B45\u0B46\u0B49\u0B4A\u0B4E-\u0B54\u0B58-\u0B5B\u0B5E\u0B64\u0B65\u0B78-\u0B81\u0B84\u0B8B-\u0B8D\u0B91\u0B96-\u0B98\u0B9B\u0B9D\u0BA0-\u0BA2\u0BA5-\u0BA7\u0BAB-\u0BAD\u0BBA-\u0BBD\u0BC3-\u0BC5\u0BC9\u0BCE\u0BCF\u0BD1-\u0BD6\u0BD8-\u0BE5\u0BFB-\u0BFF\u0C0D\u0C11\u0C29\u0C3A\u0C3B\u0C45\u0C49\u0C4E-\u0C54\u0C57\u0C5B\u0C5C\u0C5E\u0C5F\u0C64\u0C65\u0C70-\u0C76\u0C8D\u0C91\u0CA9\u0CB4\u0CBA\u0CBB\u0CC5\u0CC9\u0CCE-\u0CD4\u0CD7-\u0CDC\u0CDF\u0CE4\u0CE5\u0CF0\u0CF4-\u0CFF\u0D0D\u0D11\u0D45\u0D49\u0D50-\u0D53\u0D64\u0D65\u0D80\u0D84\u0D97-\u0D99\u0DB2\u0DBC\u0DBE\u0DBF\u0DC7-\u0DC9\u0DCB-\u0DCE\u0DD5\u0DD7\u0DE0-\u0DE5\u0DF0\u0DF1\u0DF5-\u0E00\u0E3B-\u0E3E\u0E5C-\u0E80\u0E83\u0E85\u0E8B\u0EA4\u0EA6\u0EBE\u0EBF\u0EC5\u0EC7\u0ECF\u0EDA\u0EDB\u0EE0-\u0EFF\u0F48\u0F6D-\u0F70\u0F98\u0FBD\u0FCD\u0FDB-\u0FFF\u10C6\u10C8-\u10CC\u10CE\u10CF\u1249\u124E\u124F\u1257\u1259\u125E\u125F\u1289\u128E\u128F\u12B1\u12B6\u12B7\u12BF\u12C1\u12C6\u12C7\u12D7\u1311\u1316\u1317\u135B\u135C\u137D-\u137F\u139A-\u139F\u13F6\u13F7\u13FE\u13FF\u169D-\u169F\u16F9-\u16FF\u1716-\u171E\u1737-\u173F\u1754-\u175F\u176D\u1771\u1774-\u177F\u17DE\u17DF\u17EA-\u17EF\u17FA-\u17FF\u180E\u181A-\u181F\u1879-\u187F\u18AB-\u18AF\u18F6-\u18FF\u191F\u192C-\u192F\u193C-\u193F\u1941-\u1943\u196E\u196F\u1975-\u197F\u19AC-\u19AF\u19CA-\u19CF\u19DB-\u19DD\u1A1C\u1A1D\u1A5F\u1A7D\u1A7E\u1A8A-\u1A8F\u1A9A-\u1A9F\u1AAE\u1AAF\u1ACF-\u1AFF\u1B4D\u1BF4-\u1BFB\u1C38-\u1C3A\u1C4A-\u1C4C\u1C8B-\u1C8F\u1CBB\u1CBC\u1CC8-\u1CCF\u1CFB-\u1CFF\u1F16\u1F17\u1F1E\u1F1F\u1F46\u1F47\u1F4E\u1F4F\u1F58\u1F5A\u1F5C\u1F5E\u1F7E\u1F7F\u1FB5\u1FC5\u1FD4\u1FD5\u1FDC\u1FF0\u1FF1\u1FF5\u1FFF\u200B-\u200F\u202A-\u202E\u2060-\u206F\u2072\u2073\u208F\u209D-\u209F\u20C1-\u20CF\u20F1-\u20FF\u218C-\u218F\u242A-\u243F\u244B-\u245F\u2B74\u2B75\u2B96\u2CF4-\u2CF8\u2D26\u2D28-\u2D2C\u2D2E\u2D2F\u2D68-\u2D6E\u2D71-\u2D7E\u2D97-\u2D9F\u2DA7\u2DAF\u2DB7\u2DBF\u2DC7\u2DCF\u2DD7\u2DDF\u2E5E-\u2E7F\u2E9A\u2EF4-\u2EFF\u2FD6-\u2FEF\u3040\u3097\u3098\u3100-\u3104\u3130\u318F\u31E6-\u31EE\u321F\uA48D-\uA48F\uA4C7-\uA4CF\uA62C-\uA63F\uA6F8-\uA6FF\uA7CE\uA7CF\uA7D2\uA7D4\uA7DD-\uA7F1\uA82D-\uA82F\uA83A-\uA83F\uA878-\uA87F\uA8C6-\uA8CD\uA8DA-\uA8DF\uA954-\uA95E\uA97D-\uA97F\uA9CE\uA9DA-\uA9DD\uA9FF\uAA37-\uAA3F\uAA4E\uAA4F\uAA5A\uAA5B\uAAC3-\uAADA\uAAF7-\uAB00\uAB07\uAB08\uAB0F\uAB10\uAB17-\uAB1F\uAB27\uAB2F\uAB6C-\uAB6F\uABEE\uABEF\uABFA-\uABFF\uD7A4-\uD7AF\uD7C7-\uD7CA\uD7FC-\uD7FF\uE000-\uF8FF\uFA6E\uFA6F\uFADA-\uFAFF\uFB07-\uFB12\uFB18-\uFB1C\uFB37\uFB3D\uFB3F\uFB42\uFB45\uFBC3-\uFBD2\uFD90\uFD91\uFDC8-\uFDCE\uFDD0-\uFDEF\uFE1A-\uFE1F\uFE53\uFE67\uFE6C-\uFE6F\uFE75\uFEFD-\uFF00\uFFBF-\uFFC1\uFFC8\uFFC9\uFFD0\uFFD1\uFFD8\uFFD9\uFFDD-\uFFDF\uFFE7\uFFEF-\uFFFB\uFFFE\uFFFF]|\uD800[\uDC0C\uDC27\uDC3B\uDC3E\uDC4E\uDC4F\uDC5E-\uDC7F\uDCFB-\uDCFF\uDD03-\uDD06\uDD34-\uDD36\uDD8F\uDD9D-\uDD9F\uDDA1-\uDDCF\uDDFE-\uDE7F\uDE9D-\uDE9F\uDED1-\uDEDF\uDEFC-\uDEFF\uDF24-\uDF2C\uDF4B-\uDF4F\uDF7B-\uDF7F\uDF9E\uDFC4-\uDFC7\uDFD6-\uDFFF]|\uD801[\uDC9E\uDC9F\uDCAA-\uDCAF\uDCD4-\uDCD7\uDCFC-\uDCFF\uDD28-\uDD2F\uDD64-\uDD6E\uDD7B\uDD8B\uDD93\uDD96\uDDA2\uDDB2\uDDBA\uDDBD-\uDDBF\uDDF4-\uDDFF\uDF37-\uDF3F\uDF56-\uDF5F\uDF68-\uDF7F\uDF86\uDFB1\uDFBB-\uDFFF]|\uD802[\uDC06\uDC07\uDC09\uDC36\uDC39-\uDC3B\uDC3D\uDC3E\uDC56\uDC9F-\uDCA6\uDCB0-\uDCDF\uDCF3\uDCF6-\uDCFA\uDD1C-\uDD1E\uDD3A-\uDD3E\uDD40-\uDD7F\uDDB8-\uDDBB\uDDD0\uDDD1\uDE04\uDE07-\uDE0B\uDE14\uDE18\uDE36\uDE37\uDE3B-\uDE3E\uDE49-\uDE4F\uDE59-\uDE5F\uDEA0-\uDEBF\uDEE7-\uDEEA\uDEF7-\uDEFF\uDF36-\uDF38\uDF56\uDF57\uDF73-\uDF77\uDF92-\uDF98\uDF9D-\uDFA8\uDFB0-\uDFFF]|\uD803[\uDC49-\uDC7F\uDCB3-\uDCBF\uDCF3-\uDCF9\uDD28-\uDD2F\uDD3A-\uDD3F\uDD66-\uDD68\uDD86-\uDD8D\uDD90-\uDE5F\uDE7F\uDEAA\uDEAE\uDEAF\uDEB2-\uDEC1\uDEC5-\uDEFB\uDF28-\uDF2F\uDF5A-\uDF6F\uDF8A-\uDFAF\uDFCC-\uDFDF\uDFF7-\uDFFF]|\uD804[\uDC4E-\uDC51\uDC76-\uDC7E\uDCBD\uDCC3-\uDCCF\uDCE9-\uDCEF\uDCFA-\uDCFF\uDD35\uDD48-\uDD4F\uDD77-\uDD7F\uDDE0\uDDF5-\uDDFF\uDE12\uDE42-\uDE7F\uDE87\uDE89\uDE8E\uDE9E\uDEAA-\uDEAF\uDEEB-\uDEEF\uDEFA-\uDEFF\uDF04\uDF0D\uDF0E\uDF11\uDF12\uDF29\uDF31\uDF34\uDF3A\uDF45\uDF46\uDF49\uDF4A\uDF4E\uDF4F\uDF51-\uDF56\uDF58-\uDF5C\uDF64\uDF65\uDF6D-\uDF6F\uDF75-\uDF7F\uDF8A\uDF8C\uDF8D\uDF8F\uDFB6\uDFC1\uDFC3\uDFC4\uDFC6\uDFCB\uDFD6\uDFD9-\uDFE0\uDFE3-\uDFFF]|\uD805[\uDC5C\uDC62-\uDC7F\uDCC8-\uDCCF\uDCDA-\uDD7F\uDDB6\uDDB7\uDDDE-\uDDFF\uDE45-\uDE4F\uDE5A-\uDE5F\uDE6D-\uDE7F\uDEBA-\uDEBF\uDECA-\uDECF\uDEE4-\uDEFF\uDF1B\uDF1C\uDF2C-\uDF2F\uDF47-\uDFFF]|\uD806[\uDC3C-\uDC9F\uDCF3-\uDCFE\uDD07\uDD08\uDD0A\uDD0B\uDD14\uDD17\uDD36\uDD39\uDD3A\uDD47-\uDD4F\uDD5A-\uDD9F\uDDA8\uDDA9\uDDD8\uDDD9\uDDE5-\uDDFF\uDE48-\uDE4F\uDEA3-\uDEAF\uDEF9-\uDEFF\uDF0A-\uDFBF\uDFE2-\uDFEF\uDFFA-\uDFFF]|\uD807[\uDC09\uDC37\uDC46-\uDC4F\uDC6D-\uDC6F\uDC90\uDC91\uDCA8\uDCB7-\uDCFF\uDD07\uDD0A\uDD37-\uDD39\uDD3B\uDD3E\uDD48-\uDD4F\uDD5A-\uDD5F\uDD66\uDD69\uDD8F\uDD92\uDD99-\uDD9F\uDDAA-\uDEDF\uDEF9-\uDEFF\uDF11\uDF3B-\uDF3D\uDF5B-\uDFAF\uDFB1-\uDFBF\uDFF2-\uDFFE]|\uD808[\uDF9A-\uDFFF]|\uD809[\uDC6F\uDC75-\uDC7F\uDD44-\uDFFF]|[\uD80A\uD812-\uD817\uD819\uD824-\uD82A\uD82D\uD82E\uD830-\uD832\uD83F\uD87C\uD87D\uD87F\uD889-\uDB3F\uDB41-\uDBFF][\uDC00-\uDFFF]|\uD80B[\uDC00-\uDF8F\uDFF3-\uDFFF]|\uD80D[\uDC30-\uDC3F\uDC56-\uDC5F]|\uD810[\uDFFB-\uDFFF]|\uD811[\uDE47-\uDFFF]|\uD818[\uDC00-\uDCFF\uDD3A-\uDFFF]|\uD81A[\uDE39-\uDE3F\uDE5F\uDE6A-\uDE6D\uDEBF\uDECA-\uDECF\uDEEE\uDEEF\uDEF6-\uDEFF\uDF46-\uDF4F\uDF5A\uDF62\uDF78-\uDF7C\uDF90-\uDFFF]|\uD81B[\uDC00-\uDD3F\uDD7A-\uDE3F\uDE9B-\uDEFF\uDF4B-\uDF4E\uDF88-\uDF8E\uDFA0-\uDFDF\uDFE5-\uDFEF\uDFF2-\uDFFF]|\uD821[\uDFF8-\uDFFF]|\uD823[\uDCD6-\uDCFE\uDD09-\uDFFF]|\uD82B[\uDC00-\uDFEF\uDFF4\uDFFC\uDFFF]|\uD82C[\uDD23-\uDD31\uDD33-\uDD4F\uDD53\uDD54\uDD56-\uDD63\uDD68-\uDD6F\uDEFC-\uDFFF]|\uD82F[\uDC6B-\uDC6F\uDC7D-\uDC7F\uDC89-\uDC8F\uDC9A\uDC9B\uDCA0-\uDFFF]|\uD833[\uDCFA-\uDCFF\uDEB4-\uDEFF\uDF2E\uDF2F\uDF47-\uDF4F\uDFC4-\uDFFF]|\uD834[\uDCF6-\uDCFF\uDD27\uDD28\uDD73-\uDD7A\uDDEB-\uDDFF\uDE46-\uDEBF\uDED4-\uDEDF\uDEF4-\uDEFF\uDF57-\uDF5F\uDF79-\uDFFF]|\uD835[\uDC55\uDC9D\uDCA0\uDCA1\uDCA3\uDCA4\uDCA7\uDCA8\uDCAD\uDCBA\uDCBC\uDCC4\uDD06\uDD0B\uDD0C\uDD15\uDD1D\uDD3A\uDD3F\uDD45\uDD47-\uDD49\uDD51\uDEA6\uDEA7\uDFCC\uDFCD]|\uD836[\uDE8C-\uDE9A\uDEA0\uDEB0-\uDFFF]|\uD837[\uDC00-\uDEFF\uDF1F-\uDF24\uDF2B-\uDFFF]|\uD838[\uDC07\uDC19\uDC1A\uDC22\uDC25\uDC2B-\uDC2F\uDC6E-\uDC8E\uDC90-\uDCFF\uDD2D-\uDD2F\uDD3E\uDD3F\uDD4A-\uDD4D\uDD50-\uDE8F\uDEAF-\uDEBF\uDEFA-\uDEFE\uDF00-\uDFFF]|\uD839[\uDC00-\uDCCF\uDCFA-\uDDCF\uDDFB-\uDDFE\uDE00-\uDFDF\uDFE7\uDFEC\uDFEF\uDFFF]|\uD83A[\uDCC5\uDCC6\uDCD7-\uDCFF\uDD4C-\uDD4F\uDD5A-\uDD5D\uDD60-\uDFFF]|\uD83B[\uDC00-\uDC70\uDCB5-\uDD00\uDD3E-\uDDFF\uDE04\uDE20\uDE23\uDE25\uDE26\uDE28\uDE33\uDE38\uDE3A\uDE3C-\uDE41\uDE43-\uDE46\uDE48\uDE4A\uDE4C\uDE50\uDE53\uDE55\uDE56\uDE58\uDE5A\uDE5C\uDE5E\uDE60\uDE63\uDE65\uDE66\uDE6B\uDE73\uDE78\uDE7D\uDE7F\uDE8A\uDE9C-\uDEA0\uDEA4\uDEAA\uDEBC-\uDEEF\uDEF2-\uDFFF]|\uD83C[\uDC2C-\uDC2F\uDC94-\uDC9F\uDCAF\uDCB0\uDCC0\uDCD0\uDCF6-\uDCFF\uDDAE-\uDDE5\uDE03-\uDE0F\uDE3C-\uDE3F\uDE49-\uDE4F\uDE52-\uDE5F\uDE66-\uDEFF]|\uD83D[\uDED8-\uDEDB\uDEED-\uDEEF\uDEFD-\uDEFF\uDF77-\uDF7A\uDFDA-\uDFDF\uDFEC-\uDFEF\uDFF1-\uDFFF]|\uD83E[\uDC0C-\uDC0F\uDC48-\uDC4F\uDC5A-\uDC5F\uDC88-\uDC8F\uDCAE\uDCAF\uDCBC-\uDCBF\uDCC2-\uDCFF\uDE54-\uDE5F\uDE6E\uDE6F\uDE7D-\uDE7F\uDE8A-\uDE8E\uDEC7-\uDECD\uDEDD\uDEDE\uDEEA-\uDEEF\uDEF9-\uDEFF\uDF93\uDFFA-\uDFFF]|\uD869[\uDEE0-\uDEFF]|\uD86D[\uDF3A-\uDF3F]|\uD86E[\uDC1E\uDC1F]|\uD873[\uDEA2-\uDEAF]|\uD87A[\uDFE1-\uDFEF]|\uD87B[\uDE5E-\uDFFF]|\uD87E[\uDE1E-\uDFFF]|\uD884[\uDF4B-\uDF4F]|\uD888[\uDFB0-\uDFFF]|\uDB40[\uDC00-\uDCFF\uDDF0-\uDFFF]|[\uD800-\uDBFF](?![\uDC00-\uDFFF])|(?:[^\uD800-\uDBFF]|^)[\uDC00-\uDFFF])|(?:[!-#%-\*,-\/:;\?@\[-\]_\{\}\xA1\xA7\xAB\xB6\xB7\xBB\xBF\u037E\u0387\u055A-\u055F\u0589\u058A\u05BE\u05C0\u05C3\u05C6\u05F3\u05F4\u0609\u060A\u060C\u060D\u061B\u061D-\u061F\u066A-\u066D\u06D4\u0700-\u070D\u07F7-\u07F9\u0830-\u083E\u085E\u0964\u0965\u0970\u09FD\u0A76\u0AF0\u0C77\u0C84\u0DF4\u0E4F\u0E5A\u0E5B\u0F04-\u0F12\u0F14\u0F3A-\u0F3D\u0F85\u0FD0-\u0FD4\u0FD9\u0FDA\u104A-\u104F\u10FB\u1360-\u1368\u1400\u166E\u169B\u169C\u16EB-\u16ED\u1735\u1736\u17D4-\u17D6\u17D8-\u17DA\u1800-\u180A\u1944\u1945\u1A1E\u1A1F\u1AA0-\u1AA6\u1AA8-\u1AAD\u1B4E\u1B4F\u1B5A-\u1B60\u1B7D-\u1B7F\u1BFC-\u1BFF\u1C3B-\u1C3F\u1C7E\u1C7F\u1CC0-\u1CC7\u1CD3\u2010-\u2027\u2030-\u2043\u2045-\u2051\u2053-\u205E\u207D\u207E\u208D\u208E\u2308-\u230B\u2329\u232A\u2768-\u2775\u27C5\u27C6\u27E6-\u27EF\u2983-\u2998\u29D8-\u29DB\u29FC\u29FD\u2CF9-\u2CFC\u2CFE\u2CFF\u2D70\u2E00-\u2E2E\u2E30-\u2E4F\u2E52-\u2E5D\u3001-\u3003\u3008-\u3011\u3014-\u301F\u3030\u303D\u30A0\u30FB\uA4FE\uA4FF\uA60D-\uA60F\uA673\uA67E\uA6F2-\uA6F7\uA874-\uA877\uA8CE\uA8CF\uA8F8-\uA8FA\uA8FC\uA92E\uA92F\uA95F\uA9C1-\uA9CD\uA9DE\uA9DF\uAA5C-\uAA5F\uAADE\uAADF\uAAF0\uAAF1\uABEB\uFD3E\uFD3F\uFE10-\uFE19\uFE30-\uFE52\uFE54-\uFE61\uFE63\uFE68\uFE6A\uFE6B\uFF01-\uFF03\uFF05-\uFF0A\uFF0C-\uFF0F\uFF1A\uFF1B\uFF1F\uFF20\uFF3B-\uFF3D\uFF3F\uFF5B\uFF5D\uFF5F-\uFF65]|\uD800[\uDD00-\uDD02\uDF9F\uDFD0]|\uD801\uDD6F|\uD802[\uDC57\uDD1F\uDD3F\uDE50-\uDE58\uDE7F\uDEF0-\uDEF6\uDF39-\uDF3F\uDF99-\uDF9C]|\uD803[\uDD6E\uDEAD\uDF55-\uDF59\uDF86-\uDF89]|\uD804[\uDC47-\uDC4D\uDCBB\uDCBC\uDCBE-\uDCC1\uDD40-\uDD43\uDD74\uDD75\uDDC5-\uDDC8\uDDCD\uDDDB\uDDDD-\uDDDF\uDE38-\uDE3D\uDEA9\uDFD4\uDFD5\uDFD7\uDFD8]|\uD805[\uDC4B-\uDC4F\uDC5A\uDC5B\uDC5D\uDCC6\uDDC1-\uDDD7\uDE41-\uDE43\uDE60-\uDE6C\uDEB9\uDF3C-\uDF3E]|\uD806[\uDC3B\uDD44-\uDD46\uDDE2\uDE3F-\uDE46\uDE9A-\uDE9C\uDE9E-\uDEA2\uDF00-\uDF09\uDFE1]|\uD807[\uDC41-\uDC45\uDC70\uDC71\uDEF7\uDEF8\uDF43-\uDF4F\uDFFF]|\uD809[\uDC70-\uDC74]|\uD80B[\uDFF1\uDFF2]|\uD81A[\uDE6E\uDE6F\uDEF5\uDF37-\uDF3B\uDF44]|\uD81B[\uDD6D-\uDD6F\uDE97-\uDE9A\uDFE2]|\uD82F\uDC9F|\uD836[\uDE87-\uDE8B]|\uD839\uDDFF|\uD83A[\uDD5E\uDD5F])|[ \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000]|(?:[\$\+<->\^`\|~\xA2-\xA6\xA8\xA9\xAC\xAE-\xB1\xB4\xB8\xD7\xF7\u02C2-\u02C5\u02D2-\u02DF\u02E5-\u02EB\u02ED\u02EF-\u02FF\u0375\u0384\u0385\u03F6\u0482\u058D-\u058F\u0606-\u0608\u060B\u060E\u060F\u06DE\u06E9\u06FD\u06FE\u07F6\u07FE\u07FF\u0888\u09F2\u09F3\u09FA\u09FB\u0AF1\u0B70\u0BF3-\u0BFA\u0C7F\u0D4F\u0D79\u0E3F\u0F01-\u0F03\u0F13\u0F15-\u0F17\u0F1A-\u0F1F\u0F34\u0F36\u0F38\u0FBE-\u0FC5\u0FC7-\u0FCC\u0FCE\u0FCF\u0FD5-\u0FD8\u109E\u109F\u1390-\u1399\u166D\u17DB\u1940\u19DE-\u19FF\u1B61-\u1B6A\u1B74-\u1B7C\u1FBD\u1FBF-\u1FC1\u1FCD-\u1FCF\u1FDD-\u1FDF\u1FED-\u1FEF\u1FFD\u1FFE\u2044\u2052\u207A-\u207C\u208A-\u208C\u20A0-\u20C0\u2100\u2101\u2103-\u2106\u2108\u2109\u2114\u2116-\u2118\u211E-\u2123\u2125\u2127\u2129\u212E\u213A\u213B\u2140-\u2144\u214A-\u214D\u214F\u218A\u218B\u2190-\u2307\u230C-\u2328\u232B-\u2429\u2440-\u244A\u249C-\u24E9\u2500-\u2767\u2794-\u27C4\u27C7-\u27E5\u27F0-\u2982\u2999-\u29D7\u29DC-\u29FB\u29FE-\u2B73\u2B76-\u2B95\u2B97-\u2BFF\u2CE5-\u2CEA\u2E50\u2E51\u2E80-\u2E99\u2E9B-\u2EF3\u2F00-\u2FD5\u2FF0-\u2FFF\u3004\u3012\u3013\u3020\u3036\u3037\u303E\u303F\u309B\u309C\u3190\u3191\u3196-\u319F\u31C0-\u31E5\u31EF\u3200-\u321E\u322A-\u3247\u3250\u3260-\u327F\u328A-\u32B0\u32C0-\u33FF\u4DC0-\u4DFF\uA490-\uA4C6\uA700-\uA716\uA720\uA721\uA789\uA78A\uA828-\uA82B\uA836-\uA839\uAA77-\uAA79\uAB5B\uAB6A\uAB6B\uFB29\uFBB2-\uFBC2\uFD40-\uFD4F\uFDCF\uFDFC-\uFDFF\uFE62\uFE64-\uFE66\uFE69\uFF04\uFF0B\uFF1C-\uFF1E\uFF3E\uFF40\uFF5C\uFF5E\uFFE0-\uFFE6\uFFE8-\uFFEE\uFFFC\uFFFD]|\uD800[\uDD37-\uDD3F\uDD79-\uDD89\uDD8C-\uDD8E\uDD90-\uDD9C\uDDA0\uDDD0-\uDDFC]|\uD802[\uDC77\uDC78\uDEC8]|\uD803[\uDD8E\uDD8F]|\uD805\uDF3F|\uD807[\uDFD5-\uDFF1]|\uD81A[\uDF3C-\uDF3F\uDF45]|\uD82F\uDC9C|\uD833[\uDC00-\uDCEF\uDD00-\uDEB3\uDF50-\uDFC3]|\uD834[\uDC00-\uDCF5\uDD00-\uDD26\uDD29-\uDD64\uDD6A-\uDD6C\uDD83\uDD84\uDD8C-\uDDA9\uDDAE-\uDDEA\uDE00-\uDE41\uDE45\uDF00-\uDF56]|\uD835[\uDEC1\uDEDB\uDEFB\uDF15\uDF35\uDF4F\uDF6F\uDF89\uDFA9\uDFC3]|\uD836[\uDC00-\uDDFF\uDE37-\uDE3A\uDE6D-\uDE74\uDE76-\uDE83\uDE85\uDE86]|\uD838[\uDD4F\uDEFF]|\uD83B[\uDCAC\uDCB0\uDD2E\uDEF0\uDEF1]|\uD83C[\uDC00-\uDC2B\uDC30-\uDC93\uDCA0-\uDCAE\uDCB1-\uDCBF\uDCC1-\uDCCF\uDCD1-\uDCF5\uDD0D-\uDDAD\uDDE6-\uDE02\uDE10-\uDE3B\uDE40-\uDE48\uDE50\uDE51\uDE60-\uDE65\uDF00-\uDFFF]|\uD83D[\uDC00-\uDED7\uDEDC-\uDEEC\uDEF0-\uDEFC\uDF00-\uDF76\uDF7B-\uDFD9\uDFE0-\uDFEB\uDFF0]|\uD83E[\uDC00-\uDC0B\uDC10-\uDC47\uDC50-\uDC59\uDC60-\uDC87\uDC90-\uDCAD\uDCB0-\uDCBB\uDCC0\uDCC1\uDD00-\uDE53\uDE60-\uDE6D\uDE70-\uDE7C\uDE80-\uDE89\uDE8F-\uDEC6\uDECE-\uDEDC\uDEDF-\uDEE9\uDEF0-\uDEF8\uDF00-\uDF92\uDF94-\uDFEF]))+$/)) {
                                t.next = 8;
                                break
                            }
                            return setTimeout((function() {
                                e.speechNext()
                            }), 300), t.abrupt("return");
                        case 8:
                            if (a = a.replace(/([\t-\r \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000\uFEFF]|(?:[\0-\x1F\x7F-\x9F\xAD\u0378\u0379\u0380-\u0383\u038B\u038D\u03A2\u0530\u0557\u0558\u058B\u058C\u0590\u05C8-\u05CF\u05EB-\u05EE\u05F5-\u0605\u061C\u06DD\u070E\u070F\u074B\u074C\u07B2-\u07BF\u07FB\u07FC\u082E\u082F\u083F\u085C\u085D\u085F\u086B-\u086F\u088F-\u0896\u08E2\u0984\u098D\u098E\u0991\u0992\u09A9\u09B1\u09B3-\u09B5\u09BA\u09BB\u09C5\u09C6\u09C9\u09CA\u09CF-\u09D6\u09D8-\u09DB\u09DE\u09E4\u09E5\u09FF\u0A00\u0A04\u0A0B-\u0A0E\u0A11\u0A12\u0A29\u0A31\u0A34\u0A37\u0A3A\u0A3B\u0A3D\u0A43-\u0A46\u0A49\u0A4A\u0A4E-\u0A50\u0A52-\u0A58\u0A5D\u0A5F-\u0A65\u0A77-\u0A80\u0A84\u0A8E\u0A92\u0AA9\u0AB1\u0AB4\u0ABA\u0ABB\u0AC6\u0ACA\u0ACE\u0ACF\u0AD1-\u0ADF\u0AE4\u0AE5\u0AF2-\u0AF8\u0B00\u0B04\u0B0D\u0B0E\u0B11\u0B12\u0B29\u0B31\u0B34\u0B3A\u0B3B\u0B45\u0B46\u0B49\u0B4A\u0B4E-\u0B54\u0B58-\u0B5B\u0B5E\u0B64\u0B65\u0B78-\u0B81\u0B84\u0B8B-\u0B8D\u0B91\u0B96-\u0B98\u0B9B\u0B9D\u0BA0-\u0BA2\u0BA5-\u0BA7\u0BAB-\u0BAD\u0BBA-\u0BBD\u0BC3-\u0BC5\u0BC9\u0BCE\u0BCF\u0BD1-\u0BD6\u0BD8-\u0BE5\u0BFB-\u0BFF\u0C0D\u0C11\u0C29\u0C3A\u0C3B\u0C45\u0C49\u0C4E-\u0C54\u0C57\u0C5B\u0C5C\u0C5E\u0C5F\u0C64\u0C65\u0C70-\u0C76\u0C8D\u0C91\u0CA9\u0CB4\u0CBA\u0CBB\u0CC5\u0CC9\u0CCE-\u0CD4\u0CD7-\u0CDC\u0CDF\u0CE4\u0CE5\u0CF0\u0CF4-\u0CFF\u0D0D\u0D11\u0D45\u0D49\u0D50-\u0D53\u0D64\u0D65\u0D80\u0D84\u0D97-\u0D99\u0DB2\u0DBC\u0DBE\u0DBF\u0DC7-\u0DC9\u0DCB-\u0DCE\u0DD5\u0DD7\u0DE0-\u0DE5\u0DF0\u0DF1\u0DF5-\u0E00\u0E3B-\u0E3E\u0E5C-\u0E80\u0E83\u0E85\u0E8B\u0EA4\u0EA6\u0EBE\u0EBF\u0EC5\u0EC7\u0ECF\u0EDA\u0EDB\u0EE0-\u0EFF\u0F48\u0F6D-\u0F70\u0F98\u0FBD\u0FCD\u0FDB-\u0FFF\u10C6\u10C8-\u10CC\u10CE\u10CF\u1249\u124E\u124F\u1257\u1259\u125E\u125F\u1289\u128E\u128F\u12B1\u12B6\u12B7\u12BF\u12C1\u12C6\u12C7\u12D7\u1311\u1316\u1317\u135B\u135C\u137D-\u137F\u139A-\u139F\u13F6\u13F7\u13FE\u13FF\u169D-\u169F\u16F9-\u16FF\u1716-\u171E\u1737-\u173F\u1754-\u175F\u176D\u1771\u1774-\u177F\u17DE\u17DF\u17EA-\u17EF\u17FA-\u17FF\u180E\u181A-\u181F\u1879-\u187F\u18AB-\u18AF\u18F6-\u18FF\u191F\u192C-\u192F\u193C-\u193F\u1941-\u1943\u196E\u196F\u1975-\u197F\u19AC-\u19AF\u19CA-\u19CF\u19DB-\u19DD\u1A1C\u1A1D\u1A5F\u1A7D\u1A7E\u1A8A-\u1A8F\u1A9A-\u1A9F\u1AAE\u1AAF\u1ACF-\u1AFF\u1B4D\u1BF4-\u1BFB\u1C38-\u1C3A\u1C4A-\u1C4C\u1C8B-\u1C8F\u1CBB\u1CBC\u1CC8-\u1CCF\u1CFB-\u1CFF\u1F16\u1F17\u1F1E\u1F1F\u1F46\u1F47\u1F4E\u1F4F\u1F58\u1F5A\u1F5C\u1F5E\u1F7E\u1F7F\u1FB5\u1FC5\u1FD4\u1FD5\u1FDC\u1FF0\u1FF1\u1FF5\u1FFF\u200B-\u200F\u202A-\u202E\u2060-\u206F\u2072\u2073\u208F\u209D-\u209F\u20C1-\u20CF\u20F1-\u20FF\u218C-\u218F\u242A-\u243F\u244B-\u245F\u2B74\u2B75\u2B96\u2CF4-\u2CF8\u2D26\u2D28-\u2D2C\u2D2E\u2D2F\u2D68-\u2D6E\u2D71-\u2D7E\u2D97-\u2D9F\u2DA7\u2DAF\u2DB7\u2DBF\u2DC7\u2DCF\u2DD7\u2DDF\u2E5E-\u2E7F\u2E9A\u2EF4-\u2EFF\u2FD6-\u2FEF\u3040\u3097\u3098\u3100-\u3104\u3130\u318F\u31E6-\u31EE\u321F\uA48D-\uA48F\uA4C7-\uA4CF\uA62C-\uA63F\uA6F8-\uA6FF\uA7CE\uA7CF\uA7D2\uA7D4\uA7DD-\uA7F1\uA82D-\uA82F\uA83A-\uA83F\uA878-\uA87F\uA8C6-\uA8CD\uA8DA-\uA8DF\uA954-\uA95E\uA97D-\uA97F\uA9CE\uA9DA-\uA9DD\uA9FF\uAA37-\uAA3F\uAA4E\uAA4F\uAA5A\uAA5B\uAAC3-\uAADA\uAAF7-\uAB00\uAB07\uAB08\uAB0F\uAB10\uAB17-\uAB1F\uAB27\uAB2F\uAB6C-\uAB6F\uABEE\uABEF\uABFA-\uABFF\uD7A4-\uD7AF\uD7C7-\uD7CA\uD7FC-\uD7FF\uE000-\uF8FF\uFA6E\uFA6F\uFADA-\uFAFF\uFB07-\uFB12\uFB18-\uFB1C\uFB37\uFB3D\uFB3F\uFB42\uFB45\uFBC3-\uFBD2\uFD90\uFD91\uFDC8-\uFDCE\uFDD0-\uFDEF\uFE1A-\uFE1F\uFE53\uFE67\uFE6C-\uFE6F\uFE75\uFEFD-\uFF00\uFFBF-\uFFC1\uFFC8\uFFC9\uFFD0\uFFD1\uFFD8\uFFD9\uFFDD-\uFFDF\uFFE7\uFFEF-\uFFFB\uFFFE\uFFFF]|\uD800[\uDC0C\uDC27\uDC3B\uDC3E\uDC4E\uDC4F\uDC5E-\uDC7F\uDCFB-\uDCFF\uDD03-\uDD06\uDD34-\uDD36\uDD8F\uDD9D-\uDD9F\uDDA1-\uDDCF\uDDFE-\uDE7F\uDE9D-\uDE9F\uDED1-\uDEDF\uDEFC-\uDEFF\uDF24-\uDF2C\uDF4B-\uDF4F\uDF7B-\uDF7F\uDF9E\uDFC4-\uDFC7\uDFD6-\uDFFF]|\uD801[\uDC9E\uDC9F\uDCAA-\uDCAF\uDCD4-\uDCD7\uDCFC-\uDCFF\uDD28-\uDD2F\uDD64-\uDD6E\uDD7B\uDD8B\uDD93\uDD96\uDDA2\uDDB2\uDDBA\uDDBD-\uDDBF\uDDF4-\uDDFF\uDF37-\uDF3F\uDF56-\uDF5F\uDF68-\uDF7F\uDF86\uDFB1\uDFBB-\uDFFF]|\uD802[\uDC06\uDC07\uDC09\uDC36\uDC39-\uDC3B\uDC3D\uDC3E\uDC56\uDC9F-\uDCA6\uDCB0-\uDCDF\uDCF3\uDCF6-\uDCFA\uDD1C-\uDD1E\uDD3A-\uDD3E\uDD40-\uDD7F\uDDB8-\uDDBB\uDDD0\uDDD1\uDE04\uDE07-\uDE0B\uDE14\uDE18\uDE36\uDE37\uDE3B-\uDE3E\uDE49-\uDE4F\uDE59-\uDE5F\uDEA0-\uDEBF\uDEE7-\uDEEA\uDEF7-\uDEFF\uDF36-\uDF38\uDF56\uDF57\uDF73-\uDF77\uDF92-\uDF98\uDF9D-\uDFA8\uDFB0-\uDFFF]|\uD803[\uDC49-\uDC7F\uDCB3-\uDCBF\uDCF3-\uDCF9\uDD28-\uDD2F\uDD3A-\uDD3F\uDD66-\uDD68\uDD86-\uDD8D\uDD90-\uDE5F\uDE7F\uDEAA\uDEAE\uDEAF\uDEB2-\uDEC1\uDEC5-\uDEFB\uDF28-\uDF2F\uDF5A-\uDF6F\uDF8A-\uDFAF\uDFCC-\uDFDF\uDFF7-\uDFFF]|\uD804[\uDC4E-\uDC51\uDC76-\uDC7E\uDCBD\uDCC3-\uDCCF\uDCE9-\uDCEF\uDCFA-\uDCFF\uDD35\uDD48-\uDD4F\uDD77-\uDD7F\uDDE0\uDDF5-\uDDFF\uDE12\uDE42-\uDE7F\uDE87\uDE89\uDE8E\uDE9E\uDEAA-\uDEAF\uDEEB-\uDEEF\uDEFA-\uDEFF\uDF04\uDF0D\uDF0E\uDF11\uDF12\uDF29\uDF31\uDF34\uDF3A\uDF45\uDF46\uDF49\uDF4A\uDF4E\uDF4F\uDF51-\uDF56\uDF58-\uDF5C\uDF64\uDF65\uDF6D-\uDF6F\uDF75-\uDF7F\uDF8A\uDF8C\uDF8D\uDF8F\uDFB6\uDFC1\uDFC3\uDFC4\uDFC6\uDFCB\uDFD6\uDFD9-\uDFE0\uDFE3-\uDFFF]|\uD805[\uDC5C\uDC62-\uDC7F\uDCC8-\uDCCF\uDCDA-\uDD7F\uDDB6\uDDB7\uDDDE-\uDDFF\uDE45-\uDE4F\uDE5A-\uDE5F\uDE6D-\uDE7F\uDEBA-\uDEBF\uDECA-\uDECF\uDEE4-\uDEFF\uDF1B\uDF1C\uDF2C-\uDF2F\uDF47-\uDFFF]|\uD806[\uDC3C-\uDC9F\uDCF3-\uDCFE\uDD07\uDD08\uDD0A\uDD0B\uDD14\uDD17\uDD36\uDD39\uDD3A\uDD47-\uDD4F\uDD5A-\uDD9F\uDDA8\uDDA9\uDDD8\uDDD9\uDDE5-\uDDFF\uDE48-\uDE4F\uDEA3-\uDEAF\uDEF9-\uDEFF\uDF0A-\uDFBF\uDFE2-\uDFEF\uDFFA-\uDFFF]|\uD807[\uDC09\uDC37\uDC46-\uDC4F\uDC6D-\uDC6F\uDC90\uDC91\uDCA8\uDCB7-\uDCFF\uDD07\uDD0A\uDD37-\uDD39\uDD3B\uDD3E\uDD48-\uDD4F\uDD5A-\uDD5F\uDD66\uDD69\uDD8F\uDD92\uDD99-\uDD9F\uDDAA-\uDEDF\uDEF9-\uDEFF\uDF11\uDF3B-\uDF3D\uDF5B-\uDFAF\uDFB1-\uDFBF\uDFF2-\uDFFE]|\uD808[\uDF9A-\uDFFF]|\uD809[\uDC6F\uDC75-\uDC7F\uDD44-\uDFFF]|[\uD80A\uD812-\uD817\uD819\uD824-\uD82A\uD82D\uD82E\uD830-\uD832\uD83F\uD87C\uD87D\uD87F\uD889-\uDB3F\uDB41-\uDBFF][\uDC00-\uDFFF]|\uD80B[\uDC00-\uDF8F\uDFF3-\uDFFF]|\uD80D[\uDC30-\uDC3F\uDC56-\uDC5F]|\uD810[\uDFFB-\uDFFF]|\uD811[\uDE47-\uDFFF]|\uD818[\uDC00-\uDCFF\uDD3A-\uDFFF]|\uD81A[\uDE39-\uDE3F\uDE5F\uDE6A-\uDE6D\uDEBF\uDECA-\uDECF\uDEEE\uDEEF\uDEF6-\uDEFF\uDF46-\uDF4F\uDF5A\uDF62\uDF78-\uDF7C\uDF90-\uDFFF]|\uD81B[\uDC00-\uDD3F\uDD7A-\uDE3F\uDE9B-\uDEFF\uDF4B-\uDF4E\uDF88-\uDF8E\uDFA0-\uDFDF\uDFE5-\uDFEF\uDFF2-\uDFFF]|\uD821[\uDFF8-\uDFFF]|\uD823[\uDCD6-\uDCFE\uDD09-\uDFFF]|\uD82B[\uDC00-\uDFEF\uDFF4\uDFFC\uDFFF]|\uD82C[\uDD23-\uDD31\uDD33-\uDD4F\uDD53\uDD54\uDD56-\uDD63\uDD68-\uDD6F\uDEFC-\uDFFF]|\uD82F[\uDC6B-\uDC6F\uDC7D-\uDC7F\uDC89-\uDC8F\uDC9A\uDC9B\uDCA0-\uDFFF]|\uD833[\uDCFA-\uDCFF\uDEB4-\uDEFF\uDF2E\uDF2F\uDF47-\uDF4F\uDFC4-\uDFFF]|\uD834[\uDCF6-\uDCFF\uDD27\uDD28\uDD73-\uDD7A\uDDEB-\uDDFF\uDE46-\uDEBF\uDED4-\uDEDF\uDEF4-\uDEFF\uDF57-\uDF5F\uDF79-\uDFFF]|\uD835[\uDC55\uDC9D\uDCA0\uDCA1\uDCA3\uDCA4\uDCA7\uDCA8\uDCAD\uDCBA\uDCBC\uDCC4\uDD06\uDD0B\uDD0C\uDD15\uDD1D\uDD3A\uDD3F\uDD45\uDD47-\uDD49\uDD51\uDEA6\uDEA7\uDFCC\uDFCD]|\uD836[\uDE8C-\uDE9A\uDEA0\uDEB0-\uDFFF]|\uD837[\uDC00-\uDEFF\uDF1F-\uDF24\uDF2B-\uDFFF]|\uD838[\uDC07\uDC19\uDC1A\uDC22\uDC25\uDC2B-\uDC2F\uDC6E-\uDC8E\uDC90-\uDCFF\uDD2D-\uDD2F\uDD3E\uDD3F\uDD4A-\uDD4D\uDD50-\uDE8F\uDEAF-\uDEBF\uDEFA-\uDEFE\uDF00-\uDFFF]|\uD839[\uDC00-\uDCCF\uDCFA-\uDDCF\uDDFB-\uDDFE\uDE00-\uDFDF\uDFE7\uDFEC\uDFEF\uDFFF]|\uD83A[\uDCC5\uDCC6\uDCD7-\uDCFF\uDD4C-\uDD4F\uDD5A-\uDD5D\uDD60-\uDFFF]|\uD83B[\uDC00-\uDC70\uDCB5-\uDD00\uDD3E-\uDDFF\uDE04\uDE20\uDE23\uDE25\uDE26\uDE28\uDE33\uDE38\uDE3A\uDE3C-\uDE41\uDE43-\uDE46\uDE48\uDE4A\uDE4C\uDE50\uDE53\uDE55\uDE56\uDE58\uDE5A\uDE5C\uDE5E\uDE60\uDE63\uDE65\uDE66\uDE6B\uDE73\uDE78\uDE7D\uDE7F\uDE8A\uDE9C-\uDEA0\uDEA4\uDEAA\uDEBC-\uDEEF\uDEF2-\uDFFF]|\uD83C[\uDC2C-\uDC2F\uDC94-\uDC9F\uDCAF\uDCB0\uDCC0\uDCD0\uDCF6-\uDCFF\uDDAE-\uDDE5\uDE03-\uDE0F\uDE3C-\uDE3F\uDE49-\uDE4F\uDE52-\uDE5F\uDE66-\uDEFF]|\uD83D[\uDED8-\uDEDB\uDEED-\uDEEF\uDEFD-\uDEFF\uDF77-\uDF7A\uDFDA-\uDFDF\uDFEC-\uDFEF\uDFF1-\uDFFF]|\uD83E[\uDC0C-\uDC0F\uDC48-\uDC4F\uDC5A-\uDC5F\uDC88-\uDC8F\uDCAE\uDCAF\uDCBC-\uDCBF\uDCC2-\uDCFF\uDE54-\uDE5F\uDE6E\uDE6F\uDE7D-\uDE7F\uDE8A-\uDE8E\uDEC7-\uDECD\uDEDD\uDEDE\uDEEA-\uDEEF\uDEF9-\uDEFF\uDF93\uDFFA-\uDFFF]|\uD869[\uDEE0-\uDEFF]|\uD86D[\uDF3A-\uDF3F]|\uD86E[\uDC1E\uDC1F]|\uD873[\uDEA2-\uDEAF]|\uD87A[\uDFE1-\uDFEF]|\uD87B[\uDE5E-\uDFFF]|\uD87E[\uDE1E-\uDFFF]|\uD884[\uDF4B-\uDF4F]|\uD888[\uDFB0-\uDFFF]|\uDB40[\uDC00-\uDCFF\uDDF0-\uDFFF]|[\uD800-\uDBFF](?![\uDC00-\uDFFF])|(?:[^\uD800-\uDBFF]|^)[\uDC00-\uDFFF])|(?:[!-#%-\*,-\/:;\?@\[-\]_\{\}\xA1\xA7\xAB\xB6\xB7\xBB\xBF\u037E\u0387\u055A-\u055F\u0589\u058A\u05BE\u05C0\u05C3\u05C6\u05F3\u05F4\u0609\u060A\u060C\u060D\u061B\u061D-\u061F\u066A-\u066D\u06D4\u0700-\u070D\u07F7-\u07F9\u0830-\u083E\u085E\u0964\u0965\u0970\u09FD\u0A76\u0AF0\u0C77\u0C84\u0DF4\u0E4F\u0E5A\u0E5B\u0F04-\u0F12\u0F14\u0F3A-\u0F3D\u0F85\u0FD0-\u0FD4\u0FD9\u0FDA\u104A-\u104F\u10FB\u1360-\u1368\u1400\u166E\u169B\u169C\u16EB-\u16ED\u1735\u1736\u17D4-\u17D6\u17D8-\u17DA\u1800-\u180A\u1944\u1945\u1A1E\u1A1F\u1AA0-\u1AA6\u1AA8-\u1AAD\u1B4E\u1B4F\u1B5A-\u1B60\u1B7D-\u1B7F\u1BFC-\u1BFF\u1C3B-\u1C3F\u1C7E\u1C7F\u1CC0-\u1CC7\u1CD3\u2010-\u2027\u2030-\u2043\u2045-\u2051\u2053-\u205E\u207D\u207E\u208D\u208E\u2308-\u230B\u2329\u232A\u2768-\u2775\u27C5\u27C6\u27E6-\u27EF\u2983-\u2998\u29D8-\u29DB\u29FC\u29FD\u2CF9-\u2CFC\u2CFE\u2CFF\u2D70\u2E00-\u2E2E\u2E30-\u2E4F\u2E52-\u2E5D\u3001-\u3003\u3008-\u3011\u3014-\u301F\u3030\u303D\u30A0\u30FB\uA4FE\uA4FF\uA60D-\uA60F\uA673\uA67E\uA6F2-\uA6F7\uA874-\uA877\uA8CE\uA8CF\uA8F8-\uA8FA\uA8FC\uA92E\uA92F\uA95F\uA9C1-\uA9CD\uA9DE\uA9DF\uAA5C-\uAA5F\uAADE\uAADF\uAAF0\uAAF1\uABEB\uFD3E\uFD3F\uFE10-\uFE19\uFE30-\uFE52\uFE54-\uFE61\uFE63\uFE68\uFE6A\uFE6B\uFF01-\uFF03\uFF05-\uFF0A\uFF0C-\uFF0F\uFF1A\uFF1B\uFF1F\uFF20\uFF3B-\uFF3D\uFF3F\uFF5B\uFF5D\uFF5F-\uFF65]|\uD800[\uDD00-\uDD02\uDF9F\uDFD0]|\uD801\uDD6F|\uD802[\uDC57\uDD1F\uDD3F\uDE50-\uDE58\uDE7F\uDEF0-\uDEF6\uDF39-\uDF3F\uDF99-\uDF9C]|\uD803[\uDD6E\uDEAD\uDF55-\uDF59\uDF86-\uDF89]|\uD804[\uDC47-\uDC4D\uDCBB\uDCBC\uDCBE-\uDCC1\uDD40-\uDD43\uDD74\uDD75\uDDC5-\uDDC8\uDDCD\uDDDB\uDDDD-\uDDDF\uDE38-\uDE3D\uDEA9\uDFD4\uDFD5\uDFD7\uDFD8]|\uD805[\uDC4B-\uDC4F\uDC5A\uDC5B\uDC5D\uDCC6\uDDC1-\uDDD7\uDE41-\uDE43\uDE60-\uDE6C\uDEB9\uDF3C-\uDF3E]|\uD806[\uDC3B\uDD44-\uDD46\uDDE2\uDE3F-\uDE46\uDE9A-\uDE9C\uDE9E-\uDEA2\uDF00-\uDF09\uDFE1]|\uD807[\uDC41-\uDC45\uDC70\uDC71\uDEF7\uDEF8\uDF43-\uDF4F\uDFFF]|\uD809[\uDC70-\uDC74]|\uD80B[\uDFF1\uDFF2]|\uD81A[\uDE6E\uDE6F\uDEF5\uDF37-\uDF3B\uDF44]|\uD81B[\uDD6D-\uDD6F\uDE97-\uDE9A\uDFE2]|\uD82F\uDC9F|\uD836[\uDE87-\uDE8B]|\uD839\uDDFF|\uD83A[\uDD5E\uDD5F])|[ \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000]|(?:[\$\+<->\^`\|~\xA2-\xA6\xA8\xA9\xAC\xAE-\xB1\xB4\xB8\xD7\xF7\u02C2-\u02C5\u02D2-\u02DF\u02E5-\u02EB\u02ED\u02EF-\u02FF\u0375\u0384\u0385\u03F6\u0482\u058D-\u058F\u0606-\u0608\u060B\u060E\u060F\u06DE\u06E9\u06FD\u06FE\u07F6\u07FE\u07FF\u0888\u09F2\u09F3\u09FA\u09FB\u0AF1\u0B70\u0BF3-\u0BFA\u0C7F\u0D4F\u0D79\u0E3F\u0F01-\u0F03\u0F13\u0F15-\u0F17\u0F1A-\u0F1F\u0F34\u0F36\u0F38\u0FBE-\u0FC5\u0FC7-\u0FCC\u0FCE\u0FCF\u0FD5-\u0FD8\u109E\u109F\u1390-\u1399\u166D\u17DB\u1940\u19DE-\u19FF\u1B61-\u1B6A\u1B74-\u1B7C\u1FBD\u1FBF-\u1FC1\u1FCD-\u1FCF\u1FDD-\u1FDF\u1FED-\u1FEF\u1FFD\u1FFE\u2044\u2052\u207A-\u207C\u208A-\u208C\u20A0-\u20C0\u2100\u2101\u2103-\u2106\u2108\u2109\u2114\u2116-\u2118\u211E-\u2123\u2125\u2127\u2129\u212E\u213A\u213B\u2140-\u2144\u214A-\u214D\u214F\u218A\u218B\u2190-\u2307\u230C-\u2328\u232B-\u2429\u2440-\u244A\u249C-\u24E9\u2500-\u2767\u2794-\u27C4\u27C7-\u27E5\u27F0-\u2982\u2999-\u29D7\u29DC-\u29FB\u29FE-\u2B73\u2B76-\u2B95\u2B97-\u2BFF\u2CE5-\u2CEA\u2E50\u2E51\u2E80-\u2E99\u2E9B-\u2EF3\u2F00-\u2FD5\u2FF0-\u2FFF\u3004\u3012\u3013\u3020\u3036\u3037\u303E\u303F\u309B\u309C\u3190\u3191\u3196-\u319F\u31C0-\u31E5\u31EF\u3200-\u321E\u322A-\u3247\u3250\u3260-\u327F\u328A-\u32B0\u32C0-\u33FF\u4DC0-\u4DFF\uA490-\uA4C6\uA700-\uA716\uA720\uA721\uA789\uA78A\uA828-\uA82B\uA836-\uA839\uAA77-\uAA79\uAB5B\uAB6A\uAB6B\uFB29\uFBB2-\uFBC2\uFD40-\uFD4F\uFDCF\uFDFC-\uFDFF\uFE62\uFE64-\uFE66\uFE69\uFF04\uFF0B\uFF1C-\uFF1E\uFF3E\uFF40\uFF5C\uFF5E\uFFE0-\uFFE6\uFFE8-\uFFEE\uFFFC\uFFFD]|\uD800[\uDD37-\uDD3F\uDD79-\uDD89\uDD8C-\uDD8E\uDD90-\uDD9C\uDDA0\uDDD0-\uDDFC]|\uD802[\uDC77\uDC78\uDEC8]|\uD803[\uDD8E\uDD8F]|\uD805\uDF3F|\uD807[\uDFD5-\uDFF1]|\uD81A[\uDF3C-\uDF3F\uDF45]|\uD82F\uDC9C|\uD833[\uDC00-\uDCEF\uDD00-\uDEB3\uDF50-\uDFC3]|\uD834[\uDC00-\uDCF5\uDD00-\uDD26\uDD29-\uDD64\uDD6A-\uDD6C\uDD83\uDD84\uDD8C-\uDDA9\uDDAE-\uDDEA\uDE00-\uDE41\uDE45\uDF00-\uDF56]|\uD835[\uDEC1\uDEDB\uDEFB\uDF15\uDF35\uDF4F\uDF6F\uDF89\uDFA9\uDFC3]|\uD836[\uDC00-\uDDFF\uDE37-\uDE3A\uDE6D-\uDE74\uDE76-\uDE83\uDE85\uDE86]|\uD838[\uDD4F\uDEFF]|\uD83B[\uDCAC\uDCB0\uDD2E\uDEF0\uDEF1]|\uD83C[\uDC00-\uDC2B\uDC30-\uDC93\uDCA0-\uDCAE\uDCB1-\uDCBF\uDCC1-\uDCCF\uDCD1-\uDCF5\uDD0D-\uDDAD\uDDE6-\uDE02\uDE10-\uDE3B\uDE40-\uDE48\uDE50\uDE51\uDE60-\uDE65\uDF00-\uDFFF]|\uD83D[\uDC00-\uDED7\uDEDC-\uDEEC\uDEF0-\uDEFC\uDF00-\uDF76\uDF7B-\uDFD9\uDFE0-\uDFEB\uDFF0]|\uD83E[\uDC00-\uDC0B\uDC10-\uDC47\uDC50-\uDC59\uDC60-\uDC87\uDC90-\uDCAD\uDCB0-\uDCBB\uDCC0\uDCC1\uDD00-\uDE53\uDE60-\uDE6D\uDE70-\uDE7C\uDE80-\uDE89\uDE8F-\uDEC6\uDECE-\uDEDC\uDEDF-\uDEE9\uDEF0-\uDEF8\uDF00-\uDF92\uDF94-\uDFEF]))+$/, ""), a) {
                                t.next = 12;
                                break
                            }
                            return e.speechNext(), t.abrupt("return");
                        case 12:
                            if (u = e.getTTSConfig(a), r = u.config, i = u.cacheKey, e.currentTTSCacheKey = i, s = buildURL(e.api + "/book/tts", r), !e.cacheTTSAudio) {
                                t.next = 20;
                                break
                            }
                            return t.next = 18, e.getCachedTTSAudioURL(i);
                        case 18:
                            l = t.sent, s = l || s;
                        case 20:
                            e.$refs.ttsAudio.src = s, e.$refs.ttsAudio.load(), e.$refs.ttsAudio.play(), e.showParagraph(n, !0), n.className = "reading";
                        case 25:
                        case "end":
                            return t.stop()
                    }
                }), t)
            })))()
        },
        getTTSConfig: function(e) {
            var t = this.speechPitch,
                n = this.speechRate;
            "textToSpeechCn" === this.ttsType ? (t = parseInt(50 * (t - 1)), n = parseInt(200 * (n - 1))) : "edge" === this.ttsType && (t = parseInt(50 * (t - 1)));
            var a = {
                text: e,
                type: this.ttsType,
                voice: this.voiceName,
                pitch: "".concat(t),
                rate: "".concat(n),
                accessToken: this.$store.state.token
            };
            return {
                config: a,
                cacheKey: "localCache@ttsData@" + this.$store.getters.readingBook.name + "_" + this.$store.getters.readingBook.author + "@" + "".concat(a.text).concat(a.type).concat(a.voice).concat(a.pitch).concat(a.rate).MD5(32)
            }
        },
        onTTSAudioPlay: function() {
            this.speechSpeaking = !0, this.skipAutoNext = !1, this.cacheTTSAudio && (this.cacheNextParagraphTTSAudio(1), this.cacheNextParagraphTTSAudio(2), this.cacheNextParagraphTTSAudio(3))
        },
        onTTSAudioPause: function() {
            this.speechSpeaking = !1, this.skipAutoNext = !1
        },
        onTTSAudioEnded: function() {
            this.currentTTSCacheKey && window.$cacheStorage.removeItem(this.currentTTSCacheKey)["catch"]((function() {})), this.skipAutoNext ? (this.skipAutoNext = !1, this.speechSpeaking = !1) : this.speechNext()
        },
        onTTSAudioError: function(e) {
            this.speechSpeaking && ((e.error || e.name) && this.$message.error("朗读错误:  ".concat(e.type || "", "  ").concat(e.error || e.name || e.toString())), this.speechSpeaking = !1)
        },
        getCachedTTSAudioURL: function(e) {
            var t = this;
            return asyncToGenerator(regeneratorRuntime.mark((function n() {
                var a;
                return regeneratorRuntime.wrap((function(n) {
                    while (1) switch (n.prev = n.next) {
                        case 0:
                            return n.next = 2, window.$cacheStorage.getItem(e)["catch"]((function() {
                                return !1
                            }));
                        case 2:
                            if (a = n.sent, !a) {
                                n.next = 7;
                                break
                            }
                            return n.next = 6, t.base64ToBlob(a, "audio/mpeg");
                        case 6:
                            return n.abrupt("return", n.sent);
                        case 7:
                        case "end":
                            return n.stop()
                    }
                }), n)
            })))()
        },
        cacheNextParagraphTTSAudio: function(e) {
            var t = this;
            return asyncToGenerator(regeneratorRuntime.mark((function n() {
                var a, u, r, s, l, c;
                return regeneratorRuntime.wrap((function(n) {
                    while (1) switch (n.prev = n.next) {
                        case 0:
                            if (a = t.getNextParagraph(e), a) {
                                n.next = 3;
                                break
                            }
                            return n.abrupt("return");
                        case 3:
                            if (u = a.innerText, !u.match(/^([\t-\r \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000\uFEFF]|(?:[\0-\x1F\x7F-\x9F\xAD\u0378\u0379\u0380-\u0383\u038B\u038D\u03A2\u0530\u0557\u0558\u058B\u058C\u0590\u05C8-\u05CF\u05EB-\u05EE\u05F5-\u0605\u061C\u06DD\u070E\u070F\u074B\u074C\u07B2-\u07BF\u07FB\u07FC\u082E\u082F\u083F\u085C\u085D\u085F\u086B-\u086F\u088F-\u0896\u08E2\u0984\u098D\u098E\u0991\u0992\u09A9\u09B1\u09B3-\u09B5\u09BA\u09BB\u09C5\u09C6\u09C9\u09CA\u09CF-\u09D6\u09D8-\u09DB\u09DE\u09E4\u09E5\u09FF\u0A00\u0A04\u0A0B-\u0A0E\u0A11\u0A12\u0A29\u0A31\u0A34\u0A37\u0A3A\u0A3B\u0A3D\u0A43-\u0A46\u0A49\u0A4A\u0A4E-\u0A50\u0A52-\u0A58\u0A5D\u0A5F-\u0A65\u0A77-\u0A80\u0A84\u0A8E\u0A92\u0AA9\u0AB1\u0AB4\u0ABA\u0ABB\u0AC6\u0ACA\u0ACE\u0ACF\u0AD1-\u0ADF\u0AE4\u0AE5\u0AF2-\u0AF8\u0B00\u0B04\u0B0D\u0B0E\u0B11\u0B12\u0B29\u0B31\u0B34\u0B3A\u0B3B\u0B45\u0B46\u0B49\u0B4A\u0B4E-\u0B54\u0B58-\u0B5B\u0B5E\u0B64\u0B65\u0B78-\u0B81\u0B84\u0B8B-\u0B8D\u0B91\u0B96-\u0B98\u0B9B\u0B9D\u0BA0-\u0BA2\u0BA5-\u0BA7\u0BAB-\u0BAD\u0BBA-\u0BBD\u0BC3-\u0BC5\u0BC9\u0BCE\u0BCF\u0BD1-\u0BD6\u0BD8-\u0BE5\u0BFB-\u0BFF\u0C0D\u0C11\u0C29\u0C3A\u0C3B\u0C45\u0C49\u0C4E-\u0C54\u0C57\u0C5B\u0C5C\u0C5E\u0C5F\u0C64\u0C65\u0C70-\u0C76\u0C8D\u0C91\u0CA9\u0CB4\u0CBA\u0CBB\u0CC5\u0CC9\u0CCE-\u0CD4\u0CD7-\u0CDC\u0CDF\u0CE4\u0CE5\u0CF0\u0CF4-\u0CFF\u0D0D\u0D11\u0D45\u0D49\u0D50-\u0D53\u0D64\u0D65\u0D80\u0D84\u0D97-\u0D99\u0DB2\u0DBC\u0DBE\u0DBF\u0DC7-\u0DC9\u0DCB-\u0DCE\u0DD5\u0DD7\u0DE0-\u0DE5\u0DF0\u0DF1\u0DF5-\u0E00\u0E3B-\u0E3E\u0E5C-\u0E80\u0E83\u0E85\u0E8B\u0EA4\u0EA6\u0EBE\u0EBF\u0EC5\u0EC7\u0ECF\u0EDA\u0EDB\u0EE0-\u0EFF\u0F48\u0F6D-\u0F70\u0F98\u0FBD\u0FCD\u0FDB-\u0FFF\u10C6\u10C8-\u10CC\u10CE\u10CF\u1249\u124E\u124F\u1257\u1259\u125E\u125F\u1289\u128E\u128F\u12B1\u12B6\u12B7\u12BF\u12C1\u12C6\u12C7\u12D7\u1311\u1316\u1317\u135B\u135C\u137D-\u137F\u139A-\u139F\u13F6\u13F7\u13FE\u13FF\u169D-\u169F\u16F9-\u16FF\u1716-\u171E\u1737-\u173F\u1754-\u175F\u176D\u1771\u1774-\u177F\u17DE\u17DF\u17EA-\u17EF\u17FA-\u17FF\u180E\u181A-\u181F\u1879-\u187F\u18AB-\u18AF\u18F6-\u18FF\u191F\u192C-\u192F\u193C-\u193F\u1941-\u1943\u196E\u196F\u1975-\u197F\u19AC-\u19AF\u19CA-\u19CF\u19DB-\u19DD\u1A1C\u1A1D\u1A5F\u1A7D\u1A7E\u1A8A-\u1A8F\u1A9A-\u1A9F\u1AAE\u1AAF\u1ACF-\u1AFF\u1B4D\u1BF4-\u1BFB\u1C38-\u1C3A\u1C4A-\u1C4C\u1C8B-\u1C8F\u1CBB\u1CBC\u1CC8-\u1CCF\u1CFB-\u1CFF\u1F16\u1F17\u1F1E\u1F1F\u1F46\u1F47\u1F4E\u1F4F\u1F58\u1F5A\u1F5C\u1F5E\u1F7E\u1F7F\u1FB5\u1FC5\u1FD4\u1FD5\u1FDC\u1FF0\u1FF1\u1FF5\u1FFF\u200B-\u200F\u202A-\u202E\u2060-\u206F\u2072\u2073\u208F\u209D-\u209F\u20C1-\u20CF\u20F1-\u20FF\u218C-\u218F\u242A-\u243F\u244B-\u245F\u2B74\u2B75\u2B96\u2CF4-\u2CF8\u2D26\u2D28-\u2D2C\u2D2E\u2D2F\u2D68-\u2D6E\u2D71-\u2D7E\u2D97-\u2D9F\u2DA7\u2DAF\u2DB7\u2DBF\u2DC7\u2DCF\u2DD7\u2DDF\u2E5E-\u2E7F\u2E9A\u2EF4-\u2EFF\u2FD6-\u2FEF\u3040\u3097\u3098\u3100-\u3104\u3130\u318F\u31E6-\u31EE\u321F\uA48D-\uA48F\uA4C7-\uA4CF\uA62C-\uA63F\uA6F8-\uA6FF\uA7CE\uA7CF\uA7D2\uA7D4\uA7DD-\uA7F1\uA82D-\uA82F\uA83A-\uA83F\uA878-\uA87F\uA8C6-\uA8CD\uA8DA-\uA8DF\uA954-\uA95E\uA97D-\uA97F\uA9CE\uA9DA-\uA9DD\uA9FF\uAA37-\uAA3F\uAA4E\uAA4F\uAA5A\uAA5B\uAAC3-\uAADA\uAAF7-\uAB00\uAB07\uAB08\uAB0F\uAB10\uAB17-\uAB1F\uAB27\uAB2F\uAB6C-\uAB6F\uABEE\uABEF\uABFA-\uABFF\uD7A4-\uD7AF\uD7C7-\uD7CA\uD7FC-\uD7FF\uE000-\uF8FF\uFA6E\uFA6F\uFADA-\uFAFF\uFB07-\uFB12\uFB18-\uFB1C\uFB37\uFB3D\uFB3F\uFB42\uFB45\uFBC3-\uFBD2\uFD90\uFD91\uFDC8-\uFDCE\uFDD0-\uFDEF\uFE1A-\uFE1F\uFE53\uFE67\uFE6C-\uFE6F\uFE75\uFEFD-\uFF00\uFFBF-\uFFC1\uFFC8\uFFC9\uFFD0\uFFD1\uFFD8\uFFD9\uFFDD-\uFFDF\uFFE7\uFFEF-\uFFFB\uFFFE\uFFFF]|\uD800[\uDC0C\uDC27\uDC3B\uDC3E\uDC4E\uDC4F\uDC5E-\uDC7F\uDCFB-\uDCFF\uDD03-\uDD06\uDD34-\uDD36\uDD8F\uDD9D-\uDD9F\uDDA1-\uDDCF\uDDFE-\uDE7F\uDE9D-\uDE9F\uDED1-\uDEDF\uDEFC-\uDEFF\uDF24-\uDF2C\uDF4B-\uDF4F\uDF7B-\uDF7F\uDF9E\uDFC4-\uDFC7\uDFD6-\uDFFF]|\uD801[\uDC9E\uDC9F\uDCAA-\uDCAF\uDCD4-\uDCD7\uDCFC-\uDCFF\uDD28-\uDD2F\uDD64-\uDD6E\uDD7B\uDD8B\uDD93\uDD96\uDDA2\uDDB2\uDDBA\uDDBD-\uDDBF\uDDF4-\uDDFF\uDF37-\uDF3F\uDF56-\uDF5F\uDF68-\uDF7F\uDF86\uDFB1\uDFBB-\uDFFF]|\uD802[\uDC06\uDC07\uDC09\uDC36\uDC39-\uDC3B\uDC3D\uDC3E\uDC56\uDC9F-\uDCA6\uDCB0-\uDCDF\uDCF3\uDCF6-\uDCFA\uDD1C-\uDD1E\uDD3A-\uDD3E\uDD40-\uDD7F\uDDB8-\uDDBB\uDDD0\uDDD1\uDE04\uDE07-\uDE0B\uDE14\uDE18\uDE36\uDE37\uDE3B-\uDE3E\uDE49-\uDE4F\uDE59-\uDE5F\uDEA0-\uDEBF\uDEE7-\uDEEA\uDEF7-\uDEFF\uDF36-\uDF38\uDF56\uDF57\uDF73-\uDF77\uDF92-\uDF98\uDF9D-\uDFA8\uDFB0-\uDFFF]|\uD803[\uDC49-\uDC7F\uDCB3-\uDCBF\uDCF3-\uDCF9\uDD28-\uDD2F\uDD3A-\uDD3F\uDD66-\uDD68\uDD86-\uDD8D\uDD90-\uDE5F\uDE7F\uDEAA\uDEAE\uDEAF\uDEB2-\uDEC1\uDEC5-\uDEFB\uDF28-\uDF2F\uDF5A-\uDF6F\uDF8A-\uDFAF\uDFCC-\uDFDF\uDFF7-\uDFFF]|\uD804[\uDC4E-\uDC51\uDC76-\uDC7E\uDCBD\uDCC3-\uDCCF\uDCE9-\uDCEF\uDCFA-\uDCFF\uDD35\uDD48-\uDD4F\uDD77-\uDD7F\uDDE0\uDDF5-\uDDFF\uDE12\uDE42-\uDE7F\uDE87\uDE89\uDE8E\uDE9E\uDEAA-\uDEAF\uDEEB-\uDEEF\uDEFA-\uDEFF\uDF04\uDF0D\uDF0E\uDF11\uDF12\uDF29\uDF31\uDF34\uDF3A\uDF45\uDF46\uDF49\uDF4A\uDF4E\uDF4F\uDF51-\uDF56\uDF58-\uDF5C\uDF64\uDF65\uDF6D-\uDF6F\uDF75-\uDF7F\uDF8A\uDF8C\uDF8D\uDF8F\uDFB6\uDFC1\uDFC3\uDFC4\uDFC6\uDFCB\uDFD6\uDFD9-\uDFE0\uDFE3-\uDFFF]|\uD805[\uDC5C\uDC62-\uDC7F\uDCC8-\uDCCF\uDCDA-\uDD7F\uDDB6\uDDB7\uDDDE-\uDDFF\uDE45-\uDE4F\uDE5A-\uDE5F\uDE6D-\uDE7F\uDEBA-\uDEBF\uDECA-\uDECF\uDEE4-\uDEFF\uDF1B\uDF1C\uDF2C-\uDF2F\uDF47-\uDFFF]|\uD806[\uDC3C-\uDC9F\uDCF3-\uDCFE\uDD07\uDD08\uDD0A\uDD0B\uDD14\uDD17\uDD36\uDD39\uDD3A\uDD47-\uDD4F\uDD5A-\uDD9F\uDDA8\uDDA9\uDDD8\uDDD9\uDDE5-\uDDFF\uDE48-\uDE4F\uDEA3-\uDEAF\uDEF9-\uDEFF\uDF0A-\uDFBF\uDFE2-\uDFEF\uDFFA-\uDFFF]|\uD807[\uDC09\uDC37\uDC46-\uDC4F\uDC6D-\uDC6F\uDC90\uDC91\uDCA8\uDCB7-\uDCFF\uDD07\uDD0A\uDD37-\uDD39\uDD3B\uDD3E\uDD48-\uDD4F\uDD5A-\uDD5F\uDD66\uDD69\uDD8F\uDD92\uDD99-\uDD9F\uDDAA-\uDEDF\uDEF9-\uDEFF\uDF11\uDF3B-\uDF3D\uDF5B-\uDFAF\uDFB1-\uDFBF\uDFF2-\uDFFE]|\uD808[\uDF9A-\uDFFF]|\uD809[\uDC6F\uDC75-\uDC7F\uDD44-\uDFFF]|[\uD80A\uD812-\uD817\uD819\uD824-\uD82A\uD82D\uD82E\uD830-\uD832\uD83F\uD87C\uD87D\uD87F\uD889-\uDB3F\uDB41-\uDBFF][\uDC00-\uDFFF]|\uD80B[\uDC00-\uDF8F\uDFF3-\uDFFF]|\uD80D[\uDC30-\uDC3F\uDC56-\uDC5F]|\uD810[\uDFFB-\uDFFF]|\uD811[\uDE47-\uDFFF]|\uD818[\uDC00-\uDCFF\uDD3A-\uDFFF]|\uD81A[\uDE39-\uDE3F\uDE5F\uDE6A-\uDE6D\uDEBF\uDECA-\uDECF\uDEEE\uDEEF\uDEF6-\uDEFF\uDF46-\uDF4F\uDF5A\uDF62\uDF78-\uDF7C\uDF90-\uDFFF]|\uD81B[\uDC00-\uDD3F\uDD7A-\uDE3F\uDE9B-\uDEFF\uDF4B-\uDF4E\uDF88-\uDF8E\uDFA0-\uDFDF\uDFE5-\uDFEF\uDFF2-\uDFFF]|\uD821[\uDFF8-\uDFFF]|\uD823[\uDCD6-\uDCFE\uDD09-\uDFFF]|\uD82B[\uDC00-\uDFEF\uDFF4\uDFFC\uDFFF]|\uD82C[\uDD23-\uDD31\uDD33-\uDD4F\uDD53\uDD54\uDD56-\uDD63\uDD68-\uDD6F\uDEFC-\uDFFF]|\uD82F[\uDC6B-\uDC6F\uDC7D-\uDC7F\uDC89-\uDC8F\uDC9A\uDC9B\uDCA0-\uDFFF]|\uD833[\uDCFA-\uDCFF\uDEB4-\uDEFF\uDF2E\uDF2F\uDF47-\uDF4F\uDFC4-\uDFFF]|\uD834[\uDCF6-\uDCFF\uDD27\uDD28\uDD73-\uDD7A\uDDEB-\uDDFF\uDE46-\uDEBF\uDED4-\uDEDF\uDEF4-\uDEFF\uDF57-\uDF5F\uDF79-\uDFFF]|\uD835[\uDC55\uDC9D\uDCA0\uDCA1\uDCA3\uDCA4\uDCA7\uDCA8\uDCAD\uDCBA\uDCBC\uDCC4\uDD06\uDD0B\uDD0C\uDD15\uDD1D\uDD3A\uDD3F\uDD45\uDD47-\uDD49\uDD51\uDEA6\uDEA7\uDFCC\uDFCD]|\uD836[\uDE8C-\uDE9A\uDEA0\uDEB0-\uDFFF]|\uD837[\uDC00-\uDEFF\uDF1F-\uDF24\uDF2B-\uDFFF]|\uD838[\uDC07\uDC19\uDC1A\uDC22\uDC25\uDC2B-\uDC2F\uDC6E-\uDC8E\uDC90-\uDCFF\uDD2D-\uDD2F\uDD3E\uDD3F\uDD4A-\uDD4D\uDD50-\uDE8F\uDEAF-\uDEBF\uDEFA-\uDEFE\uDF00-\uDFFF]|\uD839[\uDC00-\uDCCF\uDCFA-\uDDCF\uDDFB-\uDDFE\uDE00-\uDFDF\uDFE7\uDFEC\uDFEF\uDFFF]|\uD83A[\uDCC5\uDCC6\uDCD7-\uDCFF\uDD4C-\uDD4F\uDD5A-\uDD5D\uDD60-\uDFFF]|\uD83B[\uDC00-\uDC70\uDCB5-\uDD00\uDD3E-\uDDFF\uDE04\uDE20\uDE23\uDE25\uDE26\uDE28\uDE33\uDE38\uDE3A\uDE3C-\uDE41\uDE43-\uDE46\uDE48\uDE4A\uDE4C\uDE50\uDE53\uDE55\uDE56\uDE58\uDE5A\uDE5C\uDE5E\uDE60\uDE63\uDE65\uDE66\uDE6B\uDE73\uDE78\uDE7D\uDE7F\uDE8A\uDE9C-\uDEA0\uDEA4\uDEAA\uDEBC-\uDEEF\uDEF2-\uDFFF]|\uD83C[\uDC2C-\uDC2F\uDC94-\uDC9F\uDCAF\uDCB0\uDCC0\uDCD0\uDCF6-\uDCFF\uDDAE-\uDDE5\uDE03-\uDE0F\uDE3C-\uDE3F\uDE49-\uDE4F\uDE52-\uDE5F\uDE66-\uDEFF]|\uD83D[\uDED8-\uDEDB\uDEED-\uDEEF\uDEFD-\uDEFF\uDF77-\uDF7A\uDFDA-\uDFDF\uDFEC-\uDFEF\uDFF1-\uDFFF]|\uD83E[\uDC0C-\uDC0F\uDC48-\uDC4F\uDC5A-\uDC5F\uDC88-\uDC8F\uDCAE\uDCAF\uDCBC-\uDCBF\uDCC2-\uDCFF\uDE54-\uDE5F\uDE6E\uDE6F\uDE7D-\uDE7F\uDE8A-\uDE8E\uDEC7-\uDECD\uDEDD\uDEDE\uDEEA-\uDEEF\uDEF9-\uDEFF\uDF93\uDFFA-\uDFFF]|\uD869[\uDEE0-\uDEFF]|\uD86D[\uDF3A-\uDF3F]|\uD86E[\uDC1E\uDC1F]|\uD873[\uDEA2-\uDEAF]|\uD87A[\uDFE1-\uDFEF]|\uD87B[\uDE5E-\uDFFF]|\uD87E[\uDE1E-\uDFFF]|\uD884[\uDF4B-\uDF4F]|\uD888[\uDFB0-\uDFFF]|\uDB40[\uDC00-\uDCFF\uDDF0-\uDFFF]|[\uD800-\uDBFF](?![\uDC00-\uDFFF])|(?:[^\uD800-\uDBFF]|^)[\uDC00-\uDFFF])|(?:[!-#%-\*,-\/:;\?@\[-\]_\{\}\xA1\xA7\xAB\xB6\xB7\xBB\xBF\u037E\u0387\u055A-\u055F\u0589\u058A\u05BE\u05C0\u05C3\u05C6\u05F3\u05F4\u0609\u060A\u060C\u060D\u061B\u061D-\u061F\u066A-\u066D\u06D4\u0700-\u070D\u07F7-\u07F9\u0830-\u083E\u085E\u0964\u0965\u0970\u09FD\u0A76\u0AF0\u0C77\u0C84\u0DF4\u0E4F\u0E5A\u0E5B\u0F04-\u0F12\u0F14\u0F3A-\u0F3D\u0F85\u0FD0-\u0FD4\u0FD9\u0FDA\u104A-\u104F\u10FB\u1360-\u1368\u1400\u166E\u169B\u169C\u16EB-\u16ED\u1735\u1736\u17D4-\u17D6\u17D8-\u17DA\u1800-\u180A\u1944\u1945\u1A1E\u1A1F\u1AA0-\u1AA6\u1AA8-\u1AAD\u1B4E\u1B4F\u1B5A-\u1B60\u1B7D-\u1B7F\u1BFC-\u1BFF\u1C3B-\u1C3F\u1C7E\u1C7F\u1CC0-\u1CC7\u1CD3\u2010-\u2027\u2030-\u2043\u2045-\u2051\u2053-\u205E\u207D\u207E\u208D\u208E\u2308-\u230B\u2329\u232A\u2768-\u2775\u27C5\u27C6\u27E6-\u27EF\u2983-\u2998\u29D8-\u29DB\u29FC\u29FD\u2CF9-\u2CFC\u2CFE\u2CFF\u2D70\u2E00-\u2E2E\u2E30-\u2E4F\u2E52-\u2E5D\u3001-\u3003\u3008-\u3011\u3014-\u301F\u3030\u303D\u30A0\u30FB\uA4FE\uA4FF\uA60D-\uA60F\uA673\uA67E\uA6F2-\uA6F7\uA874-\uA877\uA8CE\uA8CF\uA8F8-\uA8FA\uA8FC\uA92E\uA92F\uA95F\uA9C1-\uA9CD\uA9DE\uA9DF\uAA5C-\uAA5F\uAADE\uAADF\uAAF0\uAAF1\uABEB\uFD3E\uFD3F\uFE10-\uFE19\uFE30-\uFE52\uFE54-\uFE61\uFE63\uFE68\uFE6A\uFE6B\uFF01-\uFF03\uFF05-\uFF0A\uFF0C-\uFF0F\uFF1A\uFF1B\uFF1F\uFF20\uFF3B-\uFF3D\uFF3F\uFF5B\uFF5D\uFF5F-\uFF65]|\uD800[\uDD00-\uDD02\uDF9F\uDFD0]|\uD801\uDD6F|\uD802[\uDC57\uDD1F\uDD3F\uDE50-\uDE58\uDE7F\uDEF0-\uDEF6\uDF39-\uDF3F\uDF99-\uDF9C]|\uD803[\uDD6E\uDEAD\uDF55-\uDF59\uDF86-\uDF89]|\uD804[\uDC47-\uDC4D\uDCBB\uDCBC\uDCBE-\uDCC1\uDD40-\uDD43\uDD74\uDD75\uDDC5-\uDDC8\uDDCD\uDDDB\uDDDD-\uDDDF\uDE38-\uDE3D\uDEA9\uDFD4\uDFD5\uDFD7\uDFD8]|\uD805[\uDC4B-\uDC4F\uDC5A\uDC5B\uDC5D\uDCC6\uDDC1-\uDDD7\uDE41-\uDE43\uDE60-\uDE6C\uDEB9\uDF3C-\uDF3E]|\uD806[\uDC3B\uDD44-\uDD46\uDDE2\uDE3F-\uDE46\uDE9A-\uDE9C\uDE9E-\uDEA2\uDF00-\uDF09\uDFE1]|\uD807[\uDC41-\uDC45\uDC70\uDC71\uDEF7\uDEF8\uDF43-\uDF4F\uDFFF]|\uD809[\uDC70-\uDC74]|\uD80B[\uDFF1\uDFF2]|\uD81A[\uDE6E\uDE6F\uDEF5\uDF37-\uDF3B\uDF44]|\uD81B[\uDD6D-\uDD6F\uDE97-\uDE9A\uDFE2]|\uD82F\uDC9F|\uD836[\uDE87-\uDE8B]|\uD839\uDDFF|\uD83A[\uDD5E\uDD5F])|[ \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000]|(?:[\$\+<->\^`\|~\xA2-\xA6\xA8\xA9\xAC\xAE-\xB1\xB4\xB8\xD7\xF7\u02C2-\u02C5\u02D2-\u02DF\u02E5-\u02EB\u02ED\u02EF-\u02FF\u0375\u0384\u0385\u03F6\u0482\u058D-\u058F\u0606-\u0608\u060B\u060E\u060F\u06DE\u06E9\u06FD\u06FE\u07F6\u07FE\u07FF\u0888\u09F2\u09F3\u09FA\u09FB\u0AF1\u0B70\u0BF3-\u0BFA\u0C7F\u0D4F\u0D79\u0E3F\u0F01-\u0F03\u0F13\u0F15-\u0F17\u0F1A-\u0F1F\u0F34\u0F36\u0F38\u0FBE-\u0FC5\u0FC7-\u0FCC\u0FCE\u0FCF\u0FD5-\u0FD8\u109E\u109F\u1390-\u1399\u166D\u17DB\u1940\u19DE-\u19FF\u1B61-\u1B6A\u1B74-\u1B7C\u1FBD\u1FBF-\u1FC1\u1FCD-\u1FCF\u1FDD-\u1FDF\u1FED-\u1FEF\u1FFD\u1FFE\u2044\u2052\u207A-\u207C\u208A-\u208C\u20A0-\u20C0\u2100\u2101\u2103-\u2106\u2108\u2109\u2114\u2116-\u2118\u211E-\u2123\u2125\u2127\u2129\u212E\u213A\u213B\u2140-\u2144\u214A-\u214D\u214F\u218A\u218B\u2190-\u2307\u230C-\u2328\u232B-\u2429\u2440-\u244A\u249C-\u24E9\u2500-\u2767\u2794-\u27C4\u27C7-\u27E5\u27F0-\u2982\u2999-\u29D7\u29DC-\u29FB\u29FE-\u2B73\u2B76-\u2B95\u2B97-\u2BFF\u2CE5-\u2CEA\u2E50\u2E51\u2E80-\u2E99\u2E9B-\u2EF3\u2F00-\u2FD5\u2FF0-\u2FFF\u3004\u3012\u3013\u3020\u3036\u3037\u303E\u303F\u309B\u309C\u3190\u3191\u3196-\u319F\u31C0-\u31E5\u31EF\u3200-\u321E\u322A-\u3247\u3250\u3260-\u327F\u328A-\u32B0\u32C0-\u33FF\u4DC0-\u4DFF\uA490-\uA4C6\uA700-\uA716\uA720\uA721\uA789\uA78A\uA828-\uA82B\uA836-\uA839\uAA77-\uAA79\uAB5B\uAB6A\uAB6B\uFB29\uFBB2-\uFBC2\uFD40-\uFD4F\uFDCF\uFDFC-\uFDFF\uFE62\uFE64-\uFE66\uFE69\uFF04\uFF0B\uFF1C-\uFF1E\uFF3E\uFF40\uFF5C\uFF5E\uFFE0-\uFFE6\uFFE8-\uFFEE\uFFFC\uFFFD]|\uD800[\uDD37-\uDD3F\uDD79-\uDD89\uDD8C-\uDD8E\uDD90-\uDD9C\uDDA0\uDDD0-\uDDFC]|\uD802[\uDC77\uDC78\uDEC8]|\uD803[\uDD8E\uDD8F]|\uD805\uDF3F|\uD807[\uDFD5-\uDFF1]|\uD81A[\uDF3C-\uDF3F\uDF45]|\uD82F\uDC9C|\uD833[\uDC00-\uDCEF\uDD00-\uDEB3\uDF50-\uDFC3]|\uD834[\uDC00-\uDCF5\uDD00-\uDD26\uDD29-\uDD64\uDD6A-\uDD6C\uDD83\uDD84\uDD8C-\uDDA9\uDDAE-\uDDEA\uDE00-\uDE41\uDE45\uDF00-\uDF56]|\uD835[\uDEC1\uDEDB\uDEFB\uDF15\uDF35\uDF4F\uDF6F\uDF89\uDFA9\uDFC3]|\uD836[\uDC00-\uDDFF\uDE37-\uDE3A\uDE6D-\uDE74\uDE76-\uDE83\uDE85\uDE86]|\uD838[\uDD4F\uDEFF]|\uD83B[\uDCAC\uDCB0\uDD2E\uDEF0\uDEF1]|\uD83C[\uDC00-\uDC2B\uDC30-\uDC93\uDCA0-\uDCAE\uDCB1-\uDCBF\uDCC1-\uDCCF\uDCD1-\uDCF5\uDD0D-\uDDAD\uDDE6-\uDE02\uDE10-\uDE3B\uDE40-\uDE48\uDE50\uDE51\uDE60-\uDE65\uDF00-\uDFFF]|\uD83D[\uDC00-\uDED7\uDEDC-\uDEEC\uDEF0-\uDEFC\uDF00-\uDF76\uDF7B-\uDFD9\uDFE0-\uDFEB\uDFF0]|\uD83E[\uDC00-\uDC0B\uDC10-\uDC47\uDC50-\uDC59\uDC60-\uDC87\uDC90-\uDCAD\uDCB0-\uDCBB\uDCC0\uDCC1\uDD00-\uDE53\uDE60-\uDE6D\uDE70-\uDE7C\uDE80-\uDE89\uDE8F-\uDEC6\uDECE-\uDEDC\uDEDF-\uDEE9\uDEF0-\uDEF8\uDF00-\uDF92\uDF94-\uDFEF]))+$/)) {
                                n.next = 6;
                                break
                            }
                            return n.abrupt("return", t.cacheNextParagraphTTSAudio(e + 1));
                        case 6:
                            if (u = u.replace(/([\t-\r \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000\uFEFF]|(?:[\0-\x1F\x7F-\x9F\xAD\u0378\u0379\u0380-\u0383\u038B\u038D\u03A2\u0530\u0557\u0558\u058B\u058C\u0590\u05C8-\u05CF\u05EB-\u05EE\u05F5-\u0605\u061C\u06DD\u070E\u070F\u074B\u074C\u07B2-\u07BF\u07FB\u07FC\u082E\u082F\u083F\u085C\u085D\u085F\u086B-\u086F\u088F-\u0896\u08E2\u0984\u098D\u098E\u0991\u0992\u09A9\u09B1\u09B3-\u09B5\u09BA\u09BB\u09C5\u09C6\u09C9\u09CA\u09CF-\u09D6\u09D8-\u09DB\u09DE\u09E4\u09E5\u09FF\u0A00\u0A04\u0A0B-\u0A0E\u0A11\u0A12\u0A29\u0A31\u0A34\u0A37\u0A3A\u0A3B\u0A3D\u0A43-\u0A46\u0A49\u0A4A\u0A4E-\u0A50\u0A52-\u0A58\u0A5D\u0A5F-\u0A65\u0A77-\u0A80\u0A84\u0A8E\u0A92\u0AA9\u0AB1\u0AB4\u0ABA\u0ABB\u0AC6\u0ACA\u0ACE\u0ACF\u0AD1-\u0ADF\u0AE4\u0AE5\u0AF2-\u0AF8\u0B00\u0B04\u0B0D\u0B0E\u0B11\u0B12\u0B29\u0B31\u0B34\u0B3A\u0B3B\u0B45\u0B46\u0B49\u0B4A\u0B4E-\u0B54\u0B58-\u0B5B\u0B5E\u0B64\u0B65\u0B78-\u0B81\u0B84\u0B8B-\u0B8D\u0B91\u0B96-\u0B98\u0B9B\u0B9D\u0BA0-\u0BA2\u0BA5-\u0BA7\u0BAB-\u0BAD\u0BBA-\u0BBD\u0BC3-\u0BC5\u0BC9\u0BCE\u0BCF\u0BD1-\u0BD6\u0BD8-\u0BE5\u0BFB-\u0BFF\u0C0D\u0C11\u0C29\u0C3A\u0C3B\u0C45\u0C49\u0C4E-\u0C54\u0C57\u0C5B\u0C5C\u0C5E\u0C5F\u0C64\u0C65\u0C70-\u0C76\u0C8D\u0C91\u0CA9\u0CB4\u0CBA\u0CBB\u0CC5\u0CC9\u0CCE-\u0CD4\u0CD7-\u0CDC\u0CDF\u0CE4\u0CE5\u0CF0\u0CF4-\u0CFF\u0D0D\u0D11\u0D45\u0D49\u0D50-\u0D53\u0D64\u0D65\u0D80\u0D84\u0D97-\u0D99\u0DB2\u0DBC\u0DBE\u0DBF\u0DC7-\u0DC9\u0DCB-\u0DCE\u0DD5\u0DD7\u0DE0-\u0DE5\u0DF0\u0DF1\u0DF5-\u0E00\u0E3B-\u0E3E\u0E5C-\u0E80\u0E83\u0E85\u0E8B\u0EA4\u0EA6\u0EBE\u0EBF\u0EC5\u0EC7\u0ECF\u0EDA\u0EDB\u0EE0-\u0EFF\u0F48\u0F6D-\u0F70\u0F98\u0FBD\u0FCD\u0FDB-\u0FFF\u10C6\u10C8-\u10CC\u10CE\u10CF\u1249\u124E\u124F\u1257\u1259\u125E\u125F\u1289\u128E\u128F\u12B1\u12B6\u12B7\u12BF\u12C1\u12C6\u12C7\u12D7\u1311\u1316\u1317\u135B\u135C\u137D-\u137F\u139A-\u139F\u13F6\u13F7\u13FE\u13FF\u169D-\u169F\u16F9-\u16FF\u1716-\u171E\u1737-\u173F\u1754-\u175F\u176D\u1771\u1774-\u177F\u17DE\u17DF\u17EA-\u17EF\u17FA-\u17FF\u180E\u181A-\u181F\u1879-\u187F\u18AB-\u18AF\u18F6-\u18FF\u191F\u192C-\u192F\u193C-\u193F\u1941-\u1943\u196E\u196F\u1975-\u197F\u19AC-\u19AF\u19CA-\u19CF\u19DB-\u19DD\u1A1C\u1A1D\u1A5F\u1A7D\u1A7E\u1A8A-\u1A8F\u1A9A-\u1A9F\u1AAE\u1AAF\u1ACF-\u1AFF\u1B4D\u1BF4-\u1BFB\u1C38-\u1C3A\u1C4A-\u1C4C\u1C8B-\u1C8F\u1CBB\u1CBC\u1CC8-\u1CCF\u1CFB-\u1CFF\u1F16\u1F17\u1F1E\u1F1F\u1F46\u1F47\u1F4E\u1F4F\u1F58\u1F5A\u1F5C\u1F5E\u1F7E\u1F7F\u1FB5\u1FC5\u1FD4\u1FD5\u1FDC\u1FF0\u1FF1\u1FF5\u1FFF\u200B-\u200F\u202A-\u202E\u2060-\u206F\u2072\u2073\u208F\u209D-\u209F\u20C1-\u20CF\u20F1-\u20FF\u218C-\u218F\u242A-\u243F\u244B-\u245F\u2B74\u2B75\u2B96\u2CF4-\u2CF8\u2D26\u2D28-\u2D2C\u2D2E\u2D2F\u2D68-\u2D6E\u2D71-\u2D7E\u2D97-\u2D9F\u2DA7\u2DAF\u2DB7\u2DBF\u2DC7\u2DCF\u2DD7\u2DDF\u2E5E-\u2E7F\u2E9A\u2EF4-\u2EFF\u2FD6-\u2FEF\u3040\u3097\u3098\u3100-\u3104\u3130\u318F\u31E6-\u31EE\u321F\uA48D-\uA48F\uA4C7-\uA4CF\uA62C-\uA63F\uA6F8-\uA6FF\uA7CE\uA7CF\uA7D2\uA7D4\uA7DD-\uA7F1\uA82D-\uA82F\uA83A-\uA83F\uA878-\uA87F\uA8C6-\uA8CD\uA8DA-\uA8DF\uA954-\uA95E\uA97D-\uA97F\uA9CE\uA9DA-\uA9DD\uA9FF\uAA37-\uAA3F\uAA4E\uAA4F\uAA5A\uAA5B\uAAC3-\uAADA\uAAF7-\uAB00\uAB07\uAB08\uAB0F\uAB10\uAB17-\uAB1F\uAB27\uAB2F\uAB6C-\uAB6F\uABEE\uABEF\uABFA-\uABFF\uD7A4-\uD7AF\uD7C7-\uD7CA\uD7FC-\uD7FF\uE000-\uF8FF\uFA6E\uFA6F\uFADA-\uFAFF\uFB07-\uFB12\uFB18-\uFB1C\uFB37\uFB3D\uFB3F\uFB42\uFB45\uFBC3-\uFBD2\uFD90\uFD91\uFDC8-\uFDCE\uFDD0-\uFDEF\uFE1A-\uFE1F\uFE53\uFE67\uFE6C-\uFE6F\uFE75\uFEFD-\uFF00\uFFBF-\uFFC1\uFFC8\uFFC9\uFFD0\uFFD1\uFFD8\uFFD9\uFFDD-\uFFDF\uFFE7\uFFEF-\uFFFB\uFFFE\uFFFF]|\uD800[\uDC0C\uDC27\uDC3B\uDC3E\uDC4E\uDC4F\uDC5E-\uDC7F\uDCFB-\uDCFF\uDD03-\uDD06\uDD34-\uDD36\uDD8F\uDD9D-\uDD9F\uDDA1-\uDDCF\uDDFE-\uDE7F\uDE9D-\uDE9F\uDED1-\uDEDF\uDEFC-\uDEFF\uDF24-\uDF2C\uDF4B-\uDF4F\uDF7B-\uDF7F\uDF9E\uDFC4-\uDFC7\uDFD6-\uDFFF]|\uD801[\uDC9E\uDC9F\uDCAA-\uDCAF\uDCD4-\uDCD7\uDCFC-\uDCFF\uDD28-\uDD2F\uDD64-\uDD6E\uDD7B\uDD8B\uDD93\uDD96\uDDA2\uDDB2\uDDBA\uDDBD-\uDDBF\uDDF4-\uDDFF\uDF37-\uDF3F\uDF56-\uDF5F\uDF68-\uDF7F\uDF86\uDFB1\uDFBB-\uDFFF]|\uD802[\uDC06\uDC07\uDC09\uDC36\uDC39-\uDC3B\uDC3D\uDC3E\uDC56\uDC9F-\uDCA6\uDCB0-\uDCDF\uDCF3\uDCF6-\uDCFA\uDD1C-\uDD1E\uDD3A-\uDD3E\uDD40-\uDD7F\uDDB8-\uDDBB\uDDD0\uDDD1\uDE04\uDE07-\uDE0B\uDE14\uDE18\uDE36\uDE37\uDE3B-\uDE3E\uDE49-\uDE4F\uDE59-\uDE5F\uDEA0-\uDEBF\uDEE7-\uDEEA\uDEF7-\uDEFF\uDF36-\uDF38\uDF56\uDF57\uDF73-\uDF77\uDF92-\uDF98\uDF9D-\uDFA8\uDFB0-\uDFFF]|\uD803[\uDC49-\uDC7F\uDCB3-\uDCBF\uDCF3-\uDCF9\uDD28-\uDD2F\uDD3A-\uDD3F\uDD66-\uDD68\uDD86-\uDD8D\uDD90-\uDE5F\uDE7F\uDEAA\uDEAE\uDEAF\uDEB2-\uDEC1\uDEC5-\uDEFB\uDF28-\uDF2F\uDF5A-\uDF6F\uDF8A-\uDFAF\uDFCC-\uDFDF\uDFF7-\uDFFF]|\uD804[\uDC4E-\uDC51\uDC76-\uDC7E\uDCBD\uDCC3-\uDCCF\uDCE9-\uDCEF\uDCFA-\uDCFF\uDD35\uDD48-\uDD4F\uDD77-\uDD7F\uDDE0\uDDF5-\uDDFF\uDE12\uDE42-\uDE7F\uDE87\uDE89\uDE8E\uDE9E\uDEAA-\uDEAF\uDEEB-\uDEEF\uDEFA-\uDEFF\uDF04\uDF0D\uDF0E\uDF11\uDF12\uDF29\uDF31\uDF34\uDF3A\uDF45\uDF46\uDF49\uDF4A\uDF4E\uDF4F\uDF51-\uDF56\uDF58-\uDF5C\uDF64\uDF65\uDF6D-\uDF6F\uDF75-\uDF7F\uDF8A\uDF8C\uDF8D\uDF8F\uDFB6\uDFC1\uDFC3\uDFC4\uDFC6\uDFCB\uDFD6\uDFD9-\uDFE0\uDFE3-\uDFFF]|\uD805[\uDC5C\uDC62-\uDC7F\uDCC8-\uDCCF\uDCDA-\uDD7F\uDDB6\uDDB7\uDDDE-\uDDFF\uDE45-\uDE4F\uDE5A-\uDE5F\uDE6D-\uDE7F\uDEBA-\uDEBF\uDECA-\uDECF\uDEE4-\uDEFF\uDF1B\uDF1C\uDF2C-\uDF2F\uDF47-\uDFFF]|\uD806[\uDC3C-\uDC9F\uDCF3-\uDCFE\uDD07\uDD08\uDD0A\uDD0B\uDD14\uDD17\uDD36\uDD39\uDD3A\uDD47-\uDD4F\uDD5A-\uDD9F\uDDA8\uDDA9\uDDD8\uDDD9\uDDE5-\uDDFF\uDE48-\uDE4F\uDEA3-\uDEAF\uDEF9-\uDEFF\uDF0A-\uDFBF\uDFE2-\uDFEF\uDFFA-\uDFFF]|\uD807[\uDC09\uDC37\uDC46-\uDC4F\uDC6D-\uDC6F\uDC90\uDC91\uDCA8\uDCB7-\uDCFF\uDD07\uDD0A\uDD37-\uDD39\uDD3B\uDD3E\uDD48-\uDD4F\uDD5A-\uDD5F\uDD66\uDD69\uDD8F\uDD92\uDD99-\uDD9F\uDDAA-\uDEDF\uDEF9-\uDEFF\uDF11\uDF3B-\uDF3D\uDF5B-\uDFAF\uDFB1-\uDFBF\uDFF2-\uDFFE]|\uD808[\uDF9A-\uDFFF]|\uD809[\uDC6F\uDC75-\uDC7F\uDD44-\uDFFF]|[\uD80A\uD812-\uD817\uD819\uD824-\uD82A\uD82D\uD82E\uD830-\uD832\uD83F\uD87C\uD87D\uD87F\uD889-\uDB3F\uDB41-\uDBFF][\uDC00-\uDFFF]|\uD80B[\uDC00-\uDF8F\uDFF3-\uDFFF]|\uD80D[\uDC30-\uDC3F\uDC56-\uDC5F]|\uD810[\uDFFB-\uDFFF]|\uD811[\uDE47-\uDFFF]|\uD818[\uDC00-\uDCFF\uDD3A-\uDFFF]|\uD81A[\uDE39-\uDE3F\uDE5F\uDE6A-\uDE6D\uDEBF\uDECA-\uDECF\uDEEE\uDEEF\uDEF6-\uDEFF\uDF46-\uDF4F\uDF5A\uDF62\uDF78-\uDF7C\uDF90-\uDFFF]|\uD81B[\uDC00-\uDD3F\uDD7A-\uDE3F\uDE9B-\uDEFF\uDF4B-\uDF4E\uDF88-\uDF8E\uDFA0-\uDFDF\uDFE5-\uDFEF\uDFF2-\uDFFF]|\uD821[\uDFF8-\uDFFF]|\uD823[\uDCD6-\uDCFE\uDD09-\uDFFF]|\uD82B[\uDC00-\uDFEF\uDFF4\uDFFC\uDFFF]|\uD82C[\uDD23-\uDD31\uDD33-\uDD4F\uDD53\uDD54\uDD56-\uDD63\uDD68-\uDD6F\uDEFC-\uDFFF]|\uD82F[\uDC6B-\uDC6F\uDC7D-\uDC7F\uDC89-\uDC8F\uDC9A\uDC9B\uDCA0-\uDFFF]|\uD833[\uDCFA-\uDCFF\uDEB4-\uDEFF\uDF2E\uDF2F\uDF47-\uDF4F\uDFC4-\uDFFF]|\uD834[\uDCF6-\uDCFF\uDD27\uDD28\uDD73-\uDD7A\uDDEB-\uDDFF\uDE46-\uDEBF\uDED4-\uDEDF\uDEF4-\uDEFF\uDF57-\uDF5F\uDF79-\uDFFF]|\uD835[\uDC55\uDC9D\uDCA0\uDCA1\uDCA3\uDCA4\uDCA7\uDCA8\uDCAD\uDCBA\uDCBC\uDCC4\uDD06\uDD0B\uDD0C\uDD15\uDD1D\uDD3A\uDD3F\uDD45\uDD47-\uDD49\uDD51\uDEA6\uDEA7\uDFCC\uDFCD]|\uD836[\uDE8C-\uDE9A\uDEA0\uDEB0-\uDFFF]|\uD837[\uDC00-\uDEFF\uDF1F-\uDF24\uDF2B-\uDFFF]|\uD838[\uDC07\uDC19\uDC1A\uDC22\uDC25\uDC2B-\uDC2F\uDC6E-\uDC8E\uDC90-\uDCFF\uDD2D-\uDD2F\uDD3E\uDD3F\uDD4A-\uDD4D\uDD50-\uDE8F\uDEAF-\uDEBF\uDEFA-\uDEFE\uDF00-\uDFFF]|\uD839[\uDC00-\uDCCF\uDCFA-\uDDCF\uDDFB-\uDDFE\uDE00-\uDFDF\uDFE7\uDFEC\uDFEF\uDFFF]|\uD83A[\uDCC5\uDCC6\uDCD7-\uDCFF\uDD4C-\uDD4F\uDD5A-\uDD5D\uDD60-\uDFFF]|\uD83B[\uDC00-\uDC70\uDCB5-\uDD00\uDD3E-\uDDFF\uDE04\uDE20\uDE23\uDE25\uDE26\uDE28\uDE33\uDE38\uDE3A\uDE3C-\uDE41\uDE43-\uDE46\uDE48\uDE4A\uDE4C\uDE50\uDE53\uDE55\uDE56\uDE58\uDE5A\uDE5C\uDE5E\uDE60\uDE63\uDE65\uDE66\uDE6B\uDE73\uDE78\uDE7D\uDE7F\uDE8A\uDE9C-\uDEA0\uDEA4\uDEAA\uDEBC-\uDEEF\uDEF2-\uDFFF]|\uD83C[\uDC2C-\uDC2F\uDC94-\uDC9F\uDCAF\uDCB0\uDCC0\uDCD0\uDCF6-\uDCFF\uDDAE-\uDDE5\uDE03-\uDE0F\uDE3C-\uDE3F\uDE49-\uDE4F\uDE52-\uDE5F\uDE66-\uDEFF]|\uD83D[\uDED8-\uDEDB\uDEED-\uDEEF\uDEFD-\uDEFF\uDF77-\uDF7A\uDFDA-\uDFDF\uDFEC-\uDFEF\uDFF1-\uDFFF]|\uD83E[\uDC0C-\uDC0F\uDC48-\uDC4F\uDC5A-\uDC5F\uDC88-\uDC8F\uDCAE\uDCAF\uDCBC-\uDCBF\uDCC2-\uDCFF\uDE54-\uDE5F\uDE6E\uDE6F\uDE7D-\uDE7F\uDE8A-\uDE8E\uDEC7-\uDECD\uDEDD\uDEDE\uDEEA-\uDEEF\uDEF9-\uDEFF\uDF93\uDFFA-\uDFFF]|\uD869[\uDEE0-\uDEFF]|\uD86D[\uDF3A-\uDF3F]|\uD86E[\uDC1E\uDC1F]|\uD873[\uDEA2-\uDEAF]|\uD87A[\uDFE1-\uDFEF]|\uD87B[\uDE5E-\uDFFF]|\uD87E[\uDE1E-\uDFFF]|\uD884[\uDF4B-\uDF4F]|\uD888[\uDFB0-\uDFFF]|\uDB40[\uDC00-\uDCFF\uDDF0-\uDFFF]|[\uD800-\uDBFF](?![\uDC00-\uDFFF])|(?:[^\uD800-\uDBFF]|^)[\uDC00-\uDFFF])|(?:[!-#%-\*,-\/:;\?@\[-\]_\{\}\xA1\xA7\xAB\xB6\xB7\xBB\xBF\u037E\u0387\u055A-\u055F\u0589\u058A\u05BE\u05C0\u05C3\u05C6\u05F3\u05F4\u0609\u060A\u060C\u060D\u061B\u061D-\u061F\u066A-\u066D\u06D4\u0700-\u070D\u07F7-\u07F9\u0830-\u083E\u085E\u0964\u0965\u0970\u09FD\u0A76\u0AF0\u0C77\u0C84\u0DF4\u0E4F\u0E5A\u0E5B\u0F04-\u0F12\u0F14\u0F3A-\u0F3D\u0F85\u0FD0-\u0FD4\u0FD9\u0FDA\u104A-\u104F\u10FB\u1360-\u1368\u1400\u166E\u169B\u169C\u16EB-\u16ED\u1735\u1736\u17D4-\u17D6\u17D8-\u17DA\u1800-\u180A\u1944\u1945\u1A1E\u1A1F\u1AA0-\u1AA6\u1AA8-\u1AAD\u1B4E\u1B4F\u1B5A-\u1B60\u1B7D-\u1B7F\u1BFC-\u1BFF\u1C3B-\u1C3F\u1C7E\u1C7F\u1CC0-\u1CC7\u1CD3\u2010-\u2027\u2030-\u2043\u2045-\u2051\u2053-\u205E\u207D\u207E\u208D\u208E\u2308-\u230B\u2329\u232A\u2768-\u2775\u27C5\u27C6\u27E6-\u27EF\u2983-\u2998\u29D8-\u29DB\u29FC\u29FD\u2CF9-\u2CFC\u2CFE\u2CFF\u2D70\u2E00-\u2E2E\u2E30-\u2E4F\u2E52-\u2E5D\u3001-\u3003\u3008-\u3011\u3014-\u301F\u3030\u303D\u30A0\u30FB\uA4FE\uA4FF\uA60D-\uA60F\uA673\uA67E\uA6F2-\uA6F7\uA874-\uA877\uA8CE\uA8CF\uA8F8-\uA8FA\uA8FC\uA92E\uA92F\uA95F\uA9C1-\uA9CD\uA9DE\uA9DF\uAA5C-\uAA5F\uAADE\uAADF\uAAF0\uAAF1\uABEB\uFD3E\uFD3F\uFE10-\uFE19\uFE30-\uFE52\uFE54-\uFE61\uFE63\uFE68\uFE6A\uFE6B\uFF01-\uFF03\uFF05-\uFF0A\uFF0C-\uFF0F\uFF1A\uFF1B\uFF1F\uFF20\uFF3B-\uFF3D\uFF3F\uFF5B\uFF5D\uFF5F-\uFF65]|\uD800[\uDD00-\uDD02\uDF9F\uDFD0]|\uD801\uDD6F|\uD802[\uDC57\uDD1F\uDD3F\uDE50-\uDE58\uDE7F\uDEF0-\uDEF6\uDF39-\uDF3F\uDF99-\uDF9C]|\uD803[\uDD6E\uDEAD\uDF55-\uDF59\uDF86-\uDF89]|\uD804[\uDC47-\uDC4D\uDCBB\uDCBC\uDCBE-\uDCC1\uDD40-\uDD43\uDD74\uDD75\uDDC5-\uDDC8\uDDCD\uDDDB\uDDDD-\uDDDF\uDE38-\uDE3D\uDEA9\uDFD4\uDFD5\uDFD7\uDFD8]|\uD805[\uDC4B-\uDC4F\uDC5A\uDC5B\uDC5D\uDCC6\uDDC1-\uDDD7\uDE41-\uDE43\uDE60-\uDE6C\uDEB9\uDF3C-\uDF3E]|\uD806[\uDC3B\uDD44-\uDD46\uDDE2\uDE3F-\uDE46\uDE9A-\uDE9C\uDE9E-\uDEA2\uDF00-\uDF09\uDFE1]|\uD807[\uDC41-\uDC45\uDC70\uDC71\uDEF7\uDEF8\uDF43-\uDF4F\uDFFF]|\uD809[\uDC70-\uDC74]|\uD80B[\uDFF1\uDFF2]|\uD81A[\uDE6E\uDE6F\uDEF5\uDF37-\uDF3B\uDF44]|\uD81B[\uDD6D-\uDD6F\uDE97-\uDE9A\uDFE2]|\uD82F\uDC9F|\uD836[\uDE87-\uDE8B]|\uD839\uDDFF|\uD83A[\uDD5E\uDD5F])|[ \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000]|(?:[\$\+<->\^`\|~\xA2-\xA6\xA8\xA9\xAC\xAE-\xB1\xB4\xB8\xD7\xF7\u02C2-\u02C5\u02D2-\u02DF\u02E5-\u02EB\u02ED\u02EF-\u02FF\u0375\u0384\u0385\u03F6\u0482\u058D-\u058F\u0606-\u0608\u060B\u060E\u060F\u06DE\u06E9\u06FD\u06FE\u07F6\u07FE\u07FF\u0888\u09F2\u09F3\u09FA\u09FB\u0AF1\u0B70\u0BF3-\u0BFA\u0C7F\u0D4F\u0D79\u0E3F\u0F01-\u0F03\u0F13\u0F15-\u0F17\u0F1A-\u0F1F\u0F34\u0F36\u0F38\u0FBE-\u0FC5\u0FC7-\u0FCC\u0FCE\u0FCF\u0FD5-\u0FD8\u109E\u109F\u1390-\u1399\u166D\u17DB\u1940\u19DE-\u19FF\u1B61-\u1B6A\u1B74-\u1B7C\u1FBD\u1FBF-\u1FC1\u1FCD-\u1FCF\u1FDD-\u1FDF\u1FED-\u1FEF\u1FFD\u1FFE\u2044\u2052\u207A-\u207C\u208A-\u208C\u20A0-\u20C0\u2100\u2101\u2103-\u2106\u2108\u2109\u2114\u2116-\u2118\u211E-\u2123\u2125\u2127\u2129\u212E\u213A\u213B\u2140-\u2144\u214A-\u214D\u214F\u218A\u218B\u2190-\u2307\u230C-\u2328\u232B-\u2429\u2440-\u244A\u249C-\u24E9\u2500-\u2767\u2794-\u27C4\u27C7-\u27E5\u27F0-\u2982\u2999-\u29D7\u29DC-\u29FB\u29FE-\u2B73\u2B76-\u2B95\u2B97-\u2BFF\u2CE5-\u2CEA\u2E50\u2E51\u2E80-\u2E99\u2E9B-\u2EF3\u2F00-\u2FD5\u2FF0-\u2FFF\u3004\u3012\u3013\u3020\u3036\u3037\u303E\u303F\u309B\u309C\u3190\u3191\u3196-\u319F\u31C0-\u31E5\u31EF\u3200-\u321E\u322A-\u3247\u3250\u3260-\u327F\u328A-\u32B0\u32C0-\u33FF\u4DC0-\u4DFF\uA490-\uA4C6\uA700-\uA716\uA720\uA721\uA789\uA78A\uA828-\uA82B\uA836-\uA839\uAA77-\uAA79\uAB5B\uAB6A\uAB6B\uFB29\uFBB2-\uFBC2\uFD40-\uFD4F\uFDCF\uFDFC-\uFDFF\uFE62\uFE64-\uFE66\uFE69\uFF04\uFF0B\uFF1C-\uFF1E\uFF3E\uFF40\uFF5C\uFF5E\uFFE0-\uFFE6\uFFE8-\uFFEE\uFFFC\uFFFD]|\uD800[\uDD37-\uDD3F\uDD79-\uDD89\uDD8C-\uDD8E\uDD90-\uDD9C\uDDA0\uDDD0-\uDDFC]|\uD802[\uDC77\uDC78\uDEC8]|\uD803[\uDD8E\uDD8F]|\uD805\uDF3F|\uD807[\uDFD5-\uDFF1]|\uD81A[\uDF3C-\uDF3F\uDF45]|\uD82F\uDC9C|\uD833[\uDC00-\uDCEF\uDD00-\uDEB3\uDF50-\uDFC3]|\uD834[\uDC00-\uDCF5\uDD00-\uDD26\uDD29-\uDD64\uDD6A-\uDD6C\uDD83\uDD84\uDD8C-\uDDA9\uDDAE-\uDDEA\uDE00-\uDE41\uDE45\uDF00-\uDF56]|\uD835[\uDEC1\uDEDB\uDEFB\uDF15\uDF35\uDF4F\uDF6F\uDF89\uDFA9\uDFC3]|\uD836[\uDC00-\uDDFF\uDE37-\uDE3A\uDE6D-\uDE74\uDE76-\uDE83\uDE85\uDE86]|\uD838[\uDD4F\uDEFF]|\uD83B[\uDCAC\uDCB0\uDD2E\uDEF0\uDEF1]|\uD83C[\uDC00-\uDC2B\uDC30-\uDC93\uDCA0-\uDCAE\uDCB1-\uDCBF\uDCC1-\uDCCF\uDCD1-\uDCF5\uDD0D-\uDDAD\uDDE6-\uDE02\uDE10-\uDE3B\uDE40-\uDE48\uDE50\uDE51\uDE60-\uDE65\uDF00-\uDFFF]|\uD83D[\uDC00-\uDED7\uDEDC-\uDEEC\uDEF0-\uDEFC\uDF00-\uDF76\uDF7B-\uDFD9\uDFE0-\uDFEB\uDFF0]|\uD83E[\uDC00-\uDC0B\uDC10-\uDC47\uDC50-\uDC59\uDC60-\uDC87\uDC90-\uDCAD\uDCB0-\uDCBB\uDCC0\uDCC1\uDD00-\uDE53\uDE60-\uDE6D\uDE70-\uDE7C\uDE80-\uDE89\uDE8F-\uDEC6\uDECE-\uDEDC\uDEDF-\uDEE9\uDEF0-\uDEF8\uDF00-\uDF92\uDF94-\uDFEF]))+$/, ""), u) {
                                n.next = 9;
                                break
                            }
                            return n.abrupt("return", t.cacheNextParagraphTTSAudio(e + 1));
                        case 9:
                            return r = t.getTTSConfig(u), s = r.config, l = r.cacheKey, n.next = 12, window.$cacheStorage.getItem(l)["catch"]((function() {
                                return !1
                            }));
                        case 12:
                            if (c = n.sent, !c) {
                                n.next = 15;
                                break
                            }
                            return n.abrupt("return");
                        case 15:
                            axios.post("/book/tts", __merge(__merge({}, s), {}, {
                                base64: "1"
                            }), {
                                silent: !0
                            }).then((function(e) {
                                e.data.isSuccess && window.$cacheStorage.setItem(l, e.data.data)["catch"]((function() {}))
                            }), (function() {}));
                        case 16:
                        case "end":
                            return n.stop()
                    }
                }), n)
            })))()
        },
        base64ToBlob: function(e, t) {
            return asyncToGenerator(regeneratorRuntime.mark((function n() {
                var a, u, r, i;
                return regeneratorRuntime.wrap((function(n) {
                    while (1) switch (n.prev = n.next) {
                        case 0:
                            for (a = window.atob(e), u = new Uint8Array(a.length), r = 0; r < a.length; r++) u[r] = a.charCodeAt(r);
                            if (i = new Blob([u], {
                                    type: t
                                }), !(window.navigator.userAgent.indexOf("iPhone") >= 0 || window.navigator.userAgent.indexOf("iPad") >= 0)) {
                                n.next = 8;
                                break
                            }
                            return n.abrupt("return", new Promise((function(e, t) {
                                var n = new FileReader;
                                n.onload = function() {
                                    e(n.result)
                                }, n.onerror = t, n.readAsDataURL(i)
                            })));
                        case 8:
                            return n.abrupt("return", window.URL.createObjectURL(i));
                        case 9:
                        case "end":
                            return n.stop()
                    }
                }), n)
            })))()
        },
        speechCurrent: function() {
            var e = this,
                t = this.voiceList.find((function(t) {
                    return t.name === e.voiceName
                }));
            if (t) {
                var n = this.getCurrentParagraph();
                if (n) {
                    var a = n.innerText;
                    a.match(/^([\t-\r \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000\uFEFF]|(?:[\0-\x1F\x7F-\x9F\xAD\u0378\u0379\u0380-\u0383\u038B\u038D\u03A2\u0530\u0557\u0558\u058B\u058C\u0590\u05C8-\u05CF\u05EB-\u05EE\u05F5-\u0605\u061C\u06DD\u070E\u070F\u074B\u074C\u07B2-\u07BF\u07FB\u07FC\u082E\u082F\u083F\u085C\u085D\u085F\u086B-\u086F\u088F-\u0896\u08E2\u0984\u098D\u098E\u0991\u0992\u09A9\u09B1\u09B3-\u09B5\u09BA\u09BB\u09C5\u09C6\u09C9\u09CA\u09CF-\u09D6\u09D8-\u09DB\u09DE\u09E4\u09E5\u09FF\u0A00\u0A04\u0A0B-\u0A0E\u0A11\u0A12\u0A29\u0A31\u0A34\u0A37\u0A3A\u0A3B\u0A3D\u0A43-\u0A46\u0A49\u0A4A\u0A4E-\u0A50\u0A52-\u0A58\u0A5D\u0A5F-\u0A65\u0A77-\u0A80\u0A84\u0A8E\u0A92\u0AA9\u0AB1\u0AB4\u0ABA\u0ABB\u0AC6\u0ACA\u0ACE\u0ACF\u0AD1-\u0ADF\u0AE4\u0AE5\u0AF2-\u0AF8\u0B00\u0B04\u0B0D\u0B0E\u0B11\u0B12\u0B29\u0B31\u0B34\u0B3A\u0B3B\u0B45\u0B46\u0B49\u0B4A\u0B4E-\u0B54\u0B58-\u0B5B\u0B5E\u0B64\u0B65\u0B78-\u0B81\u0B84\u0B8B-\u0B8D\u0B91\u0B96-\u0B98\u0B9B\u0B9D\u0BA0-\u0BA2\u0BA5-\u0BA7\u0BAB-\u0BAD\u0BBA-\u0BBD\u0BC3-\u0BC5\u0BC9\u0BCE\u0BCF\u0BD1-\u0BD6\u0BD8-\u0BE5\u0BFB-\u0BFF\u0C0D\u0C11\u0C29\u0C3A\u0C3B\u0C45\u0C49\u0C4E-\u0C54\u0C57\u0C5B\u0C5C\u0C5E\u0C5F\u0C64\u0C65\u0C70-\u0C76\u0C8D\u0C91\u0CA9\u0CB4\u0CBA\u0CBB\u0CC5\u0CC9\u0CCE-\u0CD4\u0CD7-\u0CDC\u0CDF\u0CE4\u0CE5\u0CF0\u0CF4-\u0CFF\u0D0D\u0D11\u0D45\u0D49\u0D50-\u0D53\u0D64\u0D65\u0D80\u0D84\u0D97-\u0D99\u0DB2\u0DBC\u0DBE\u0DBF\u0DC7-\u0DC9\u0DCB-\u0DCE\u0DD5\u0DD7\u0DE0-\u0DE5\u0DF0\u0DF1\u0DF5-\u0E00\u0E3B-\u0E3E\u0E5C-\u0E80\u0E83\u0E85\u0E8B\u0EA4\u0EA6\u0EBE\u0EBF\u0EC5\u0EC7\u0ECF\u0EDA\u0EDB\u0EE0-\u0EFF\u0F48\u0F6D-\u0F70\u0F98\u0FBD\u0FCD\u0FDB-\u0FFF\u10C6\u10C8-\u10CC\u10CE\u10CF\u1249\u124E\u124F\u1257\u1259\u125E\u125F\u1289\u128E\u128F\u12B1\u12B6\u12B7\u12BF\u12C1\u12C6\u12C7\u12D7\u1311\u1316\u1317\u135B\u135C\u137D-\u137F\u139A-\u139F\u13F6\u13F7\u13FE\u13FF\u169D-\u169F\u16F9-\u16FF\u1716-\u171E\u1737-\u173F\u1754-\u175F\u176D\u1771\u1774-\u177F\u17DE\u17DF\u17EA-\u17EF\u17FA-\u17FF\u180E\u181A-\u181F\u1879-\u187F\u18AB-\u18AF\u18F6-\u18FF\u191F\u192C-\u192F\u193C-\u193F\u1941-\u1943\u196E\u196F\u1975-\u197F\u19AC-\u19AF\u19CA-\u19CF\u19DB-\u19DD\u1A1C\u1A1D\u1A5F\u1A7D\u1A7E\u1A8A-\u1A8F\u1A9A-\u1A9F\u1AAE\u1AAF\u1ACF-\u1AFF\u1B4D\u1BF4-\u1BFB\u1C38-\u1C3A\u1C4A-\u1C4C\u1C8B-\u1C8F\u1CBB\u1CBC\u1CC8-\u1CCF\u1CFB-\u1CFF\u1F16\u1F17\u1F1E\u1F1F\u1F46\u1F47\u1F4E\u1F4F\u1F58\u1F5A\u1F5C\u1F5E\u1F7E\u1F7F\u1FB5\u1FC5\u1FD4\u1FD5\u1FDC\u1FF0\u1FF1\u1FF5\u1FFF\u200B-\u200F\u202A-\u202E\u2060-\u206F\u2072\u2073\u208F\u209D-\u209F\u20C1-\u20CF\u20F1-\u20FF\u218C-\u218F\u242A-\u243F\u244B-\u245F\u2B74\u2B75\u2B96\u2CF4-\u2CF8\u2D26\u2D28-\u2D2C\u2D2E\u2D2F\u2D68-\u2D6E\u2D71-\u2D7E\u2D97-\u2D9F\u2DA7\u2DAF\u2DB7\u2DBF\u2DC7\u2DCF\u2DD7\u2DDF\u2E5E-\u2E7F\u2E9A\u2EF4-\u2EFF\u2FD6-\u2FEF\u3040\u3097\u3098\u3100-\u3104\u3130\u318F\u31E6-\u31EE\u321F\uA48D-\uA48F\uA4C7-\uA4CF\uA62C-\uA63F\uA6F8-\uA6FF\uA7CE\uA7CF\uA7D2\uA7D4\uA7DD-\uA7F1\uA82D-\uA82F\uA83A-\uA83F\uA878-\uA87F\uA8C6-\uA8CD\uA8DA-\uA8DF\uA954-\uA95E\uA97D-\uA97F\uA9CE\uA9DA-\uA9DD\uA9FF\uAA37-\uAA3F\uAA4E\uAA4F\uAA5A\uAA5B\uAAC3-\uAADA\uAAF7-\uAB00\uAB07\uAB08\uAB0F\uAB10\uAB17-\uAB1F\uAB27\uAB2F\uAB6C-\uAB6F\uABEE\uABEF\uABFA-\uABFF\uD7A4-\uD7AF\uD7C7-\uD7CA\uD7FC-\uD7FF\uE000-\uF8FF\uFA6E\uFA6F\uFADA-\uFAFF\uFB07-\uFB12\uFB18-\uFB1C\uFB37\uFB3D\uFB3F\uFB42\uFB45\uFBC3-\uFBD2\uFD90\uFD91\uFDC8-\uFDCE\uFDD0-\uFDEF\uFE1A-\uFE1F\uFE53\uFE67\uFE6C-\uFE6F\uFE75\uFEFD-\uFF00\uFFBF-\uFFC1\uFFC8\uFFC9\uFFD0\uFFD1\uFFD8\uFFD9\uFFDD-\uFFDF\uFFE7\uFFEF-\uFFFB\uFFFE\uFFFF]|\uD800[\uDC0C\uDC27\uDC3B\uDC3E\uDC4E\uDC4F\uDC5E-\uDC7F\uDCFB-\uDCFF\uDD03-\uDD06\uDD34-\uDD36\uDD8F\uDD9D-\uDD9F\uDDA1-\uDDCF\uDDFE-\uDE7F\uDE9D-\uDE9F\uDED1-\uDEDF\uDEFC-\uDEFF\uDF24-\uDF2C\uDF4B-\uDF4F\uDF7B-\uDF7F\uDF9E\uDFC4-\uDFC7\uDFD6-\uDFFF]|\uD801[\uDC9E\uDC9F\uDCAA-\uDCAF\uDCD4-\uDCD7\uDCFC-\uDCFF\uDD28-\uDD2F\uDD64-\uDD6E\uDD7B\uDD8B\uDD93\uDD96\uDDA2\uDDB2\uDDBA\uDDBD-\uDDBF\uDDF4-\uDDFF\uDF37-\uDF3F\uDF56-\uDF5F\uDF68-\uDF7F\uDF86\uDFB1\uDFBB-\uDFFF]|\uD802[\uDC06\uDC07\uDC09\uDC36\uDC39-\uDC3B\uDC3D\uDC3E\uDC56\uDC9F-\uDCA6\uDCB0-\uDCDF\uDCF3\uDCF6-\uDCFA\uDD1C-\uDD1E\uDD3A-\uDD3E\uDD40-\uDD7F\uDDB8-\uDDBB\uDDD0\uDDD1\uDE04\uDE07-\uDE0B\uDE14\uDE18\uDE36\uDE37\uDE3B-\uDE3E\uDE49-\uDE4F\uDE59-\uDE5F\uDEA0-\uDEBF\uDEE7-\uDEEA\uDEF7-\uDEFF\uDF36-\uDF38\uDF56\uDF57\uDF73-\uDF77\uDF92-\uDF98\uDF9D-\uDFA8\uDFB0-\uDFFF]|\uD803[\uDC49-\uDC7F\uDCB3-\uDCBF\uDCF3-\uDCF9\uDD28-\uDD2F\uDD3A-\uDD3F\uDD66-\uDD68\uDD86-\uDD8D\uDD90-\uDE5F\uDE7F\uDEAA\uDEAE\uDEAF\uDEB2-\uDEC1\uDEC5-\uDEFB\uDF28-\uDF2F\uDF5A-\uDF6F\uDF8A-\uDFAF\uDFCC-\uDFDF\uDFF7-\uDFFF]|\uD804[\uDC4E-\uDC51\uDC76-\uDC7E\uDCBD\uDCC3-\uDCCF\uDCE9-\uDCEF\uDCFA-\uDCFF\uDD35\uDD48-\uDD4F\uDD77-\uDD7F\uDDE0\uDDF5-\uDDFF\uDE12\uDE42-\uDE7F\uDE87\uDE89\uDE8E\uDE9E\uDEAA-\uDEAF\uDEEB-\uDEEF\uDEFA-\uDEFF\uDF04\uDF0D\uDF0E\uDF11\uDF12\uDF29\uDF31\uDF34\uDF3A\uDF45\uDF46\uDF49\uDF4A\uDF4E\uDF4F\uDF51-\uDF56\uDF58-\uDF5C\uDF64\uDF65\uDF6D-\uDF6F\uDF75-\uDF7F\uDF8A\uDF8C\uDF8D\uDF8F\uDFB6\uDFC1\uDFC3\uDFC4\uDFC6\uDFCB\uDFD6\uDFD9-\uDFE0\uDFE3-\uDFFF]|\uD805[\uDC5C\uDC62-\uDC7F\uDCC8-\uDCCF\uDCDA-\uDD7F\uDDB6\uDDB7\uDDDE-\uDDFF\uDE45-\uDE4F\uDE5A-\uDE5F\uDE6D-\uDE7F\uDEBA-\uDEBF\uDECA-\uDECF\uDEE4-\uDEFF\uDF1B\uDF1C\uDF2C-\uDF2F\uDF47-\uDFFF]|\uD806[\uDC3C-\uDC9F\uDCF3-\uDCFE\uDD07\uDD08\uDD0A\uDD0B\uDD14\uDD17\uDD36\uDD39\uDD3A\uDD47-\uDD4F\uDD5A-\uDD9F\uDDA8\uDDA9\uDDD8\uDDD9\uDDE5-\uDDFF\uDE48-\uDE4F\uDEA3-\uDEAF\uDEF9-\uDEFF\uDF0A-\uDFBF\uDFE2-\uDFEF\uDFFA-\uDFFF]|\uD807[\uDC09\uDC37\uDC46-\uDC4F\uDC6D-\uDC6F\uDC90\uDC91\uDCA8\uDCB7-\uDCFF\uDD07\uDD0A\uDD37-\uDD39\uDD3B\uDD3E\uDD48-\uDD4F\uDD5A-\uDD5F\uDD66\uDD69\uDD8F\uDD92\uDD99-\uDD9F\uDDAA-\uDEDF\uDEF9-\uDEFF\uDF11\uDF3B-\uDF3D\uDF5B-\uDFAF\uDFB1-\uDFBF\uDFF2-\uDFFE]|\uD808[\uDF9A-\uDFFF]|\uD809[\uDC6F\uDC75-\uDC7F\uDD44-\uDFFF]|[\uD80A\uD812-\uD817\uD819\uD824-\uD82A\uD82D\uD82E\uD830-\uD832\uD83F\uD87C\uD87D\uD87F\uD889-\uDB3F\uDB41-\uDBFF][\uDC00-\uDFFF]|\uD80B[\uDC00-\uDF8F\uDFF3-\uDFFF]|\uD80D[\uDC30-\uDC3F\uDC56-\uDC5F]|\uD810[\uDFFB-\uDFFF]|\uD811[\uDE47-\uDFFF]|\uD818[\uDC00-\uDCFF\uDD3A-\uDFFF]|\uD81A[\uDE39-\uDE3F\uDE5F\uDE6A-\uDE6D\uDEBF\uDECA-\uDECF\uDEEE\uDEEF\uDEF6-\uDEFF\uDF46-\uDF4F\uDF5A\uDF62\uDF78-\uDF7C\uDF90-\uDFFF]|\uD81B[\uDC00-\uDD3F\uDD7A-\uDE3F\uDE9B-\uDEFF\uDF4B-\uDF4E\uDF88-\uDF8E\uDFA0-\uDFDF\uDFE5-\uDFEF\uDFF2-\uDFFF]|\uD821[\uDFF8-\uDFFF]|\uD823[\uDCD6-\uDCFE\uDD09-\uDFFF]|\uD82B[\uDC00-\uDFEF\uDFF4\uDFFC\uDFFF]|\uD82C[\uDD23-\uDD31\uDD33-\uDD4F\uDD53\uDD54\uDD56-\uDD63\uDD68-\uDD6F\uDEFC-\uDFFF]|\uD82F[\uDC6B-\uDC6F\uDC7D-\uDC7F\uDC89-\uDC8F\uDC9A\uDC9B\uDCA0-\uDFFF]|\uD833[\uDCFA-\uDCFF\uDEB4-\uDEFF\uDF2E\uDF2F\uDF47-\uDF4F\uDFC4-\uDFFF]|\uD834[\uDCF6-\uDCFF\uDD27\uDD28\uDD73-\uDD7A\uDDEB-\uDDFF\uDE46-\uDEBF\uDED4-\uDEDF\uDEF4-\uDEFF\uDF57-\uDF5F\uDF79-\uDFFF]|\uD835[\uDC55\uDC9D\uDCA0\uDCA1\uDCA3\uDCA4\uDCA7\uDCA8\uDCAD\uDCBA\uDCBC\uDCC4\uDD06\uDD0B\uDD0C\uDD15\uDD1D\uDD3A\uDD3F\uDD45\uDD47-\uDD49\uDD51\uDEA6\uDEA7\uDFCC\uDFCD]|\uD836[\uDE8C-\uDE9A\uDEA0\uDEB0-\uDFFF]|\uD837[\uDC00-\uDEFF\uDF1F-\uDF24\uDF2B-\uDFFF]|\uD838[\uDC07\uDC19\uDC1A\uDC22\uDC25\uDC2B-\uDC2F\uDC6E-\uDC8E\uDC90-\uDCFF\uDD2D-\uDD2F\uDD3E\uDD3F\uDD4A-\uDD4D\uDD50-\uDE8F\uDEAF-\uDEBF\uDEFA-\uDEFE\uDF00-\uDFFF]|\uD839[\uDC00-\uDCCF\uDCFA-\uDDCF\uDDFB-\uDDFE\uDE00-\uDFDF\uDFE7\uDFEC\uDFEF\uDFFF]|\uD83A[\uDCC5\uDCC6\uDCD7-\uDCFF\uDD4C-\uDD4F\uDD5A-\uDD5D\uDD60-\uDFFF]|\uD83B[\uDC00-\uDC70\uDCB5-\uDD00\uDD3E-\uDDFF\uDE04\uDE20\uDE23\uDE25\uDE26\uDE28\uDE33\uDE38\uDE3A\uDE3C-\uDE41\uDE43-\uDE46\uDE48\uDE4A\uDE4C\uDE50\uDE53\uDE55\uDE56\uDE58\uDE5A\uDE5C\uDE5E\uDE60\uDE63\uDE65\uDE66\uDE6B\uDE73\uDE78\uDE7D\uDE7F\uDE8A\uDE9C-\uDEA0\uDEA4\uDEAA\uDEBC-\uDEEF\uDEF2-\uDFFF]|\uD83C[\uDC2C-\uDC2F\uDC94-\uDC9F\uDCAF\uDCB0\uDCC0\uDCD0\uDCF6-\uDCFF\uDDAE-\uDDE5\uDE03-\uDE0F\uDE3C-\uDE3F\uDE49-\uDE4F\uDE52-\uDE5F\uDE66-\uDEFF]|\uD83D[\uDED8-\uDEDB\uDEED-\uDEEF\uDEFD-\uDEFF\uDF77-\uDF7A\uDFDA-\uDFDF\uDFEC-\uDFEF\uDFF1-\uDFFF]|\uD83E[\uDC0C-\uDC0F\uDC48-\uDC4F\uDC5A-\uDC5F\uDC88-\uDC8F\uDCAE\uDCAF\uDCBC-\uDCBF\uDCC2-\uDCFF\uDE54-\uDE5F\uDE6E\uDE6F\uDE7D-\uDE7F\uDE8A-\uDE8E\uDEC7-\uDECD\uDEDD\uDEDE\uDEEA-\uDEEF\uDEF9-\uDEFF\uDF93\uDFFA-\uDFFF]|\uD869[\uDEE0-\uDEFF]|\uD86D[\uDF3A-\uDF3F]|\uD86E[\uDC1E\uDC1F]|\uD873[\uDEA2-\uDEAF]|\uD87A[\uDFE1-\uDFEF]|\uD87B[\uDE5E-\uDFFF]|\uD87E[\uDE1E-\uDFFF]|\uD884[\uDF4B-\uDF4F]|\uD888[\uDFB0-\uDFFF]|\uDB40[\uDC00-\uDCFF\uDDF0-\uDFFF]|[\uD800-\uDBFF](?![\uDC00-\uDFFF])|(?:[^\uD800-\uDBFF]|^)[\uDC00-\uDFFF])|(?:[!-#%-\*,-\/:;\?@\[-\]_\{\}\xA1\xA7\xAB\xB6\xB7\xBB\xBF\u037E\u0387\u055A-\u055F\u0589\u058A\u05BE\u05C0\u05C3\u05C6\u05F3\u05F4\u0609\u060A\u060C\u060D\u061B\u061D-\u061F\u066A-\u066D\u06D4\u0700-\u070D\u07F7-\u07F9\u0830-\u083E\u085E\u0964\u0965\u0970\u09FD\u0A76\u0AF0\u0C77\u0C84\u0DF4\u0E4F\u0E5A\u0E5B\u0F04-\u0F12\u0F14\u0F3A-\u0F3D\u0F85\u0FD0-\u0FD4\u0FD9\u0FDA\u104A-\u104F\u10FB\u1360-\u1368\u1400\u166E\u169B\u169C\u16EB-\u16ED\u1735\u1736\u17D4-\u17D6\u17D8-\u17DA\u1800-\u180A\u1944\u1945\u1A1E\u1A1F\u1AA0-\u1AA6\u1AA8-\u1AAD\u1B4E\u1B4F\u1B5A-\u1B60\u1B7D-\u1B7F\u1BFC-\u1BFF\u1C3B-\u1C3F\u1C7E\u1C7F\u1CC0-\u1CC7\u1CD3\u2010-\u2027\u2030-\u2043\u2045-\u2051\u2053-\u205E\u207D\u207E\u208D\u208E\u2308-\u230B\u2329\u232A\u2768-\u2775\u27C5\u27C6\u27E6-\u27EF\u2983-\u2998\u29D8-\u29DB\u29FC\u29FD\u2CF9-\u2CFC\u2CFE\u2CFF\u2D70\u2E00-\u2E2E\u2E30-\u2E4F\u2E52-\u2E5D\u3001-\u3003\u3008-\u3011\u3014-\u301F\u3030\u303D\u30A0\u30FB\uA4FE\uA4FF\uA60D-\uA60F\uA673\uA67E\uA6F2-\uA6F7\uA874-\uA877\uA8CE\uA8CF\uA8F8-\uA8FA\uA8FC\uA92E\uA92F\uA95F\uA9C1-\uA9CD\uA9DE\uA9DF\uAA5C-\uAA5F\uAADE\uAADF\uAAF0\uAAF1\uABEB\uFD3E\uFD3F\uFE10-\uFE19\uFE30-\uFE52\uFE54-\uFE61\uFE63\uFE68\uFE6A\uFE6B\uFF01-\uFF03\uFF05-\uFF0A\uFF0C-\uFF0F\uFF1A\uFF1B\uFF1F\uFF20\uFF3B-\uFF3D\uFF3F\uFF5B\uFF5D\uFF5F-\uFF65]|\uD800[\uDD00-\uDD02\uDF9F\uDFD0]|\uD801\uDD6F|\uD802[\uDC57\uDD1F\uDD3F\uDE50-\uDE58\uDE7F\uDEF0-\uDEF6\uDF39-\uDF3F\uDF99-\uDF9C]|\uD803[\uDD6E\uDEAD\uDF55-\uDF59\uDF86-\uDF89]|\uD804[\uDC47-\uDC4D\uDCBB\uDCBC\uDCBE-\uDCC1\uDD40-\uDD43\uDD74\uDD75\uDDC5-\uDDC8\uDDCD\uDDDB\uDDDD-\uDDDF\uDE38-\uDE3D\uDEA9\uDFD4\uDFD5\uDFD7\uDFD8]|\uD805[\uDC4B-\uDC4F\uDC5A\uDC5B\uDC5D\uDCC6\uDDC1-\uDDD7\uDE41-\uDE43\uDE60-\uDE6C\uDEB9\uDF3C-\uDF3E]|\uD806[\uDC3B\uDD44-\uDD46\uDDE2\uDE3F-\uDE46\uDE9A-\uDE9C\uDE9E-\uDEA2\uDF00-\uDF09\uDFE1]|\uD807[\uDC41-\uDC45\uDC70\uDC71\uDEF7\uDEF8\uDF43-\uDF4F\uDFFF]|\uD809[\uDC70-\uDC74]|\uD80B[\uDFF1\uDFF2]|\uD81A[\uDE6E\uDE6F\uDEF5\uDF37-\uDF3B\uDF44]|\uD81B[\uDD6D-\uDD6F\uDE97-\uDE9A\uDFE2]|\uD82F\uDC9F|\uD836[\uDE87-\uDE8B]|\uD839\uDDFF|\uD83A[\uDD5E\uDD5F])|[ \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000]|(?:[\$\+<->\^`\|~\xA2-\xA6\xA8\xA9\xAC\xAE-\xB1\xB4\xB8\xD7\xF7\u02C2-\u02C5\u02D2-\u02DF\u02E5-\u02EB\u02ED\u02EF-\u02FF\u0375\u0384\u0385\u03F6\u0482\u058D-\u058F\u0606-\u0608\u060B\u060E\u060F\u06DE\u06E9\u06FD\u06FE\u07F6\u07FE\u07FF\u0888\u09F2\u09F3\u09FA\u09FB\u0AF1\u0B70\u0BF3-\u0BFA\u0C7F\u0D4F\u0D79\u0E3F\u0F01-\u0F03\u0F13\u0F15-\u0F17\u0F1A-\u0F1F\u0F34\u0F36\u0F38\u0FBE-\u0FC5\u0FC7-\u0FCC\u0FCE\u0FCF\u0FD5-\u0FD8\u109E\u109F\u1390-\u1399\u166D\u17DB\u1940\u19DE-\u19FF\u1B61-\u1B6A\u1B74-\u1B7C\u1FBD\u1FBF-\u1FC1\u1FCD-\u1FCF\u1FDD-\u1FDF\u1FED-\u1FEF\u1FFD\u1FFE\u2044\u2052\u207A-\u207C\u208A-\u208C\u20A0-\u20C0\u2100\u2101\u2103-\u2106\u2108\u2109\u2114\u2116-\u2118\u211E-\u2123\u2125\u2127\u2129\u212E\u213A\u213B\u2140-\u2144\u214A-\u214D\u214F\u218A\u218B\u2190-\u2307\u230C-\u2328\u232B-\u2429\u2440-\u244A\u249C-\u24E9\u2500-\u2767\u2794-\u27C4\u27C7-\u27E5\u27F0-\u2982\u2999-\u29D7\u29DC-\u29FB\u29FE-\u2B73\u2B76-\u2B95\u2B97-\u2BFF\u2CE5-\u2CEA\u2E50\u2E51\u2E80-\u2E99\u2E9B-\u2EF3\u2F00-\u2FD5\u2FF0-\u2FFF\u3004\u3012\u3013\u3020\u3036\u3037\u303E\u303F\u309B\u309C\u3190\u3191\u3196-\u319F\u31C0-\u31E5\u31EF\u3200-\u321E\u322A-\u3247\u3250\u3260-\u327F\u328A-\u32B0\u32C0-\u33FF\u4DC0-\u4DFF\uA490-\uA4C6\uA700-\uA716\uA720\uA721\uA789\uA78A\uA828-\uA82B\uA836-\uA839\uAA77-\uAA79\uAB5B\uAB6A\uAB6B\uFB29\uFBB2-\uFBC2\uFD40-\uFD4F\uFDCF\uFDFC-\uFDFF\uFE62\uFE64-\uFE66\uFE69\uFF04\uFF0B\uFF1C-\uFF1E\uFF3E\uFF40\uFF5C\uFF5E\uFFE0-\uFFE6\uFFE8-\uFFEE\uFFFC\uFFFD]|\uD800[\uDD37-\uDD3F\uDD79-\uDD89\uDD8C-\uDD8E\uDD90-\uDD9C\uDDA0\uDDD0-\uDDFC]|\uD802[\uDC77\uDC78\uDEC8]|\uD803[\uDD8E\uDD8F]|\uD805\uDF3F|\uD807[\uDFD5-\uDFF1]|\uD81A[\uDF3C-\uDF3F\uDF45]|\uD82F\uDC9C|\uD833[\uDC00-\uDCEF\uDD00-\uDEB3\uDF50-\uDFC3]|\uD834[\uDC00-\uDCF5\uDD00-\uDD26\uDD29-\uDD64\uDD6A-\uDD6C\uDD83\uDD84\uDD8C-\uDDA9\uDDAE-\uDDEA\uDE00-\uDE41\uDE45\uDF00-\uDF56]|\uD835[\uDEC1\uDEDB\uDEFB\uDF15\uDF35\uDF4F\uDF6F\uDF89\uDFA9\uDFC3]|\uD836[\uDC00-\uDDFF\uDE37-\uDE3A\uDE6D-\uDE74\uDE76-\uDE83\uDE85\uDE86]|\uD838[\uDD4F\uDEFF]|\uD83B[\uDCAC\uDCB0\uDD2E\uDEF0\uDEF1]|\uD83C[\uDC00-\uDC2B\uDC30-\uDC93\uDCA0-\uDCAE\uDCB1-\uDCBF\uDCC1-\uDCCF\uDCD1-\uDCF5\uDD0D-\uDDAD\uDDE6-\uDE02\uDE10-\uDE3B\uDE40-\uDE48\uDE50\uDE51\uDE60-\uDE65\uDF00-\uDFFF]|\uD83D[\uDC00-\uDED7\uDEDC-\uDEEC\uDEF0-\uDEFC\uDF00-\uDF76\uDF7B-\uDFD9\uDFE0-\uDFEB\uDFF0]|\uD83E[\uDC00-\uDC0B\uDC10-\uDC47\uDC50-\uDC59\uDC60-\uDC87\uDC90-\uDCAD\uDCB0-\uDCBB\uDCC0\uDCC1\uDD00-\uDE53\uDE60-\uDE6D\uDE70-\uDE7C\uDE80-\uDE89\uDE8F-\uDEC6\uDECE-\uDEDC\uDEDF-\uDEE9\uDEF0-\uDEF8\uDF00-\uDF92\uDF94-\uDFEF]))+$/) ? setTimeout((function() {
                        e.speechNext()
                    }), 300) : (a = a.replace(/([\t-\r \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000\uFEFF]|(?:[\0-\x1F\x7F-\x9F\xAD\u0378\u0379\u0380-\u0383\u038B\u038D\u03A2\u0530\u0557\u0558\u058B\u058C\u0590\u05C8-\u05CF\u05EB-\u05EE\u05F5-\u0605\u061C\u06DD\u070E\u070F\u074B\u074C\u07B2-\u07BF\u07FB\u07FC\u082E\u082F\u083F\u085C\u085D\u085F\u086B-\u086F\u088F-\u0896\u08E2\u0984\u098D\u098E\u0991\u0992\u09A9\u09B1\u09B3-\u09B5\u09BA\u09BB\u09C5\u09C6\u09C9\u09CA\u09CF-\u09D6\u09D8-\u09DB\u09DE\u09E4\u09E5\u09FF\u0A00\u0A04\u0A0B-\u0A0E\u0A11\u0A12\u0A29\u0A31\u0A34\u0A37\u0A3A\u0A3B\u0A3D\u0A43-\u0A46\u0A49\u0A4A\u0A4E-\u0A50\u0A52-\u0A58\u0A5D\u0A5F-\u0A65\u0A77-\u0A80\u0A84\u0A8E\u0A92\u0AA9\u0AB1\u0AB4\u0ABA\u0ABB\u0AC6\u0ACA\u0ACE\u0ACF\u0AD1-\u0ADF\u0AE4\u0AE5\u0AF2-\u0AF8\u0B00\u0B04\u0B0D\u0B0E\u0B11\u0B12\u0B29\u0B31\u0B34\u0B3A\u0B3B\u0B45\u0B46\u0B49\u0B4A\u0B4E-\u0B54\u0B58-\u0B5B\u0B5E\u0B64\u0B65\u0B78-\u0B81\u0B84\u0B8B-\u0B8D\u0B91\u0B96-\u0B98\u0B9B\u0B9D\u0BA0-\u0BA2\u0BA5-\u0BA7\u0BAB-\u0BAD\u0BBA-\u0BBD\u0BC3-\u0BC5\u0BC9\u0BCE\u0BCF\u0BD1-\u0BD6\u0BD8-\u0BE5\u0BFB-\u0BFF\u0C0D\u0C11\u0C29\u0C3A\u0C3B\u0C45\u0C49\u0C4E-\u0C54\u0C57\u0C5B\u0C5C\u0C5E\u0C5F\u0C64\u0C65\u0C70-\u0C76\u0C8D\u0C91\u0CA9\u0CB4\u0CBA\u0CBB\u0CC5\u0CC9\u0CCE-\u0CD4\u0CD7-\u0CDC\u0CDF\u0CE4\u0CE5\u0CF0\u0CF4-\u0CFF\u0D0D\u0D11\u0D45\u0D49\u0D50-\u0D53\u0D64\u0D65\u0D80\u0D84\u0D97-\u0D99\u0DB2\u0DBC\u0DBE\u0DBF\u0DC7-\u0DC9\u0DCB-\u0DCE\u0DD5\u0DD7\u0DE0-\u0DE5\u0DF0\u0DF1\u0DF5-\u0E00\u0E3B-\u0E3E\u0E5C-\u0E80\u0E83\u0E85\u0E8B\u0EA4\u0EA6\u0EBE\u0EBF\u0EC5\u0EC7\u0ECF\u0EDA\u0EDB\u0EE0-\u0EFF\u0F48\u0F6D-\u0F70\u0F98\u0FBD\u0FCD\u0FDB-\u0FFF\u10C6\u10C8-\u10CC\u10CE\u10CF\u1249\u124E\u124F\u1257\u1259\u125E\u125F\u1289\u128E\u128F\u12B1\u12B6\u12B7\u12BF\u12C1\u12C6\u12C7\u12D7\u1311\u1316\u1317\u135B\u135C\u137D-\u137F\u139A-\u139F\u13F6\u13F7\u13FE\u13FF\u169D-\u169F\u16F9-\u16FF\u1716-\u171E\u1737-\u173F\u1754-\u175F\u176D\u1771\u1774-\u177F\u17DE\u17DF\u17EA-\u17EF\u17FA-\u17FF\u180E\u181A-\u181F\u1879-\u187F\u18AB-\u18AF\u18F6-\u18FF\u191F\u192C-\u192F\u193C-\u193F\u1941-\u1943\u196E\u196F\u1975-\u197F\u19AC-\u19AF\u19CA-\u19CF\u19DB-\u19DD\u1A1C\u1A1D\u1A5F\u1A7D\u1A7E\u1A8A-\u1A8F\u1A9A-\u1A9F\u1AAE\u1AAF\u1ACF-\u1AFF\u1B4D\u1BF4-\u1BFB\u1C38-\u1C3A\u1C4A-\u1C4C\u1C8B-\u1C8F\u1CBB\u1CBC\u1CC8-\u1CCF\u1CFB-\u1CFF\u1F16\u1F17\u1F1E\u1F1F\u1F46\u1F47\u1F4E\u1F4F\u1F58\u1F5A\u1F5C\u1F5E\u1F7E\u1F7F\u1FB5\u1FC5\u1FD4\u1FD5\u1FDC\u1FF0\u1FF1\u1FF5\u1FFF\u200B-\u200F\u202A-\u202E\u2060-\u206F\u2072\u2073\u208F\u209D-\u209F\u20C1-\u20CF\u20F1-\u20FF\u218C-\u218F\u242A-\u243F\u244B-\u245F\u2B74\u2B75\u2B96\u2CF4-\u2CF8\u2D26\u2D28-\u2D2C\u2D2E\u2D2F\u2D68-\u2D6E\u2D71-\u2D7E\u2D97-\u2D9F\u2DA7\u2DAF\u2DB7\u2DBF\u2DC7\u2DCF\u2DD7\u2DDF\u2E5E-\u2E7F\u2E9A\u2EF4-\u2EFF\u2FD6-\u2FEF\u3040\u3097\u3098\u3100-\u3104\u3130\u318F\u31E6-\u31EE\u321F\uA48D-\uA48F\uA4C7-\uA4CF\uA62C-\uA63F\uA6F8-\uA6FF\uA7CE\uA7CF\uA7D2\uA7D4\uA7DD-\uA7F1\uA82D-\uA82F\uA83A-\uA83F\uA878-\uA87F\uA8C6-\uA8CD\uA8DA-\uA8DF\uA954-\uA95E\uA97D-\uA97F\uA9CE\uA9DA-\uA9DD\uA9FF\uAA37-\uAA3F\uAA4E\uAA4F\uAA5A\uAA5B\uAAC3-\uAADA\uAAF7-\uAB00\uAB07\uAB08\uAB0F\uAB10\uAB17-\uAB1F\uAB27\uAB2F\uAB6C-\uAB6F\uABEE\uABEF\uABFA-\uABFF\uD7A4-\uD7AF\uD7C7-\uD7CA\uD7FC-\uD7FF\uE000-\uF8FF\uFA6E\uFA6F\uFADA-\uFAFF\uFB07-\uFB12\uFB18-\uFB1C\uFB37\uFB3D\uFB3F\uFB42\uFB45\uFBC3-\uFBD2\uFD90\uFD91\uFDC8-\uFDCE\uFDD0-\uFDEF\uFE1A-\uFE1F\uFE53\uFE67\uFE6C-\uFE6F\uFE75\uFEFD-\uFF00\uFFBF-\uFFC1\uFFC8\uFFC9\uFFD0\uFFD1\uFFD8\uFFD9\uFFDD-\uFFDF\uFFE7\uFFEF-\uFFFB\uFFFE\uFFFF]|\uD800[\uDC0C\uDC27\uDC3B\uDC3E\uDC4E\uDC4F\uDC5E-\uDC7F\uDCFB-\uDCFF\uDD03-\uDD06\uDD34-\uDD36\uDD8F\uDD9D-\uDD9F\uDDA1-\uDDCF\uDDFE-\uDE7F\uDE9D-\uDE9F\uDED1-\uDEDF\uDEFC-\uDEFF\uDF24-\uDF2C\uDF4B-\uDF4F\uDF7B-\uDF7F\uDF9E\uDFC4-\uDFC7\uDFD6-\uDFFF]|\uD801[\uDC9E\uDC9F\uDCAA-\uDCAF\uDCD4-\uDCD7\uDCFC-\uDCFF\uDD28-\uDD2F\uDD64-\uDD6E\uDD7B\uDD8B\uDD93\uDD96\uDDA2\uDDB2\uDDBA\uDDBD-\uDDBF\uDDF4-\uDDFF\uDF37-\uDF3F\uDF56-\uDF5F\uDF68-\uDF7F\uDF86\uDFB1\uDFBB-\uDFFF]|\uD802[\uDC06\uDC07\uDC09\uDC36\uDC39-\uDC3B\uDC3D\uDC3E\uDC56\uDC9F-\uDCA6\uDCB0-\uDCDF\uDCF3\uDCF6-\uDCFA\uDD1C-\uDD1E\uDD3A-\uDD3E\uDD40-\uDD7F\uDDB8-\uDDBB\uDDD0\uDDD1\uDE04\uDE07-\uDE0B\uDE14\uDE18\uDE36\uDE37\uDE3B-\uDE3E\uDE49-\uDE4F\uDE59-\uDE5F\uDEA0-\uDEBF\uDEE7-\uDEEA\uDEF7-\uDEFF\uDF36-\uDF38\uDF56\uDF57\uDF73-\uDF77\uDF92-\uDF98\uDF9D-\uDFA8\uDFB0-\uDFFF]|\uD803[\uDC49-\uDC7F\uDCB3-\uDCBF\uDCF3-\uDCF9\uDD28-\uDD2F\uDD3A-\uDD3F\uDD66-\uDD68\uDD86-\uDD8D\uDD90-\uDE5F\uDE7F\uDEAA\uDEAE\uDEAF\uDEB2-\uDEC1\uDEC5-\uDEFB\uDF28-\uDF2F\uDF5A-\uDF6F\uDF8A-\uDFAF\uDFCC-\uDFDF\uDFF7-\uDFFF]|\uD804[\uDC4E-\uDC51\uDC76-\uDC7E\uDCBD\uDCC3-\uDCCF\uDCE9-\uDCEF\uDCFA-\uDCFF\uDD35\uDD48-\uDD4F\uDD77-\uDD7F\uDDE0\uDDF5-\uDDFF\uDE12\uDE42-\uDE7F\uDE87\uDE89\uDE8E\uDE9E\uDEAA-\uDEAF\uDEEB-\uDEEF\uDEFA-\uDEFF\uDF04\uDF0D\uDF0E\uDF11\uDF12\uDF29\uDF31\uDF34\uDF3A\uDF45\uDF46\uDF49\uDF4A\uDF4E\uDF4F\uDF51-\uDF56\uDF58-\uDF5C\uDF64\uDF65\uDF6D-\uDF6F\uDF75-\uDF7F\uDF8A\uDF8C\uDF8D\uDF8F\uDFB6\uDFC1\uDFC3\uDFC4\uDFC6\uDFCB\uDFD6\uDFD9-\uDFE0\uDFE3-\uDFFF]|\uD805[\uDC5C\uDC62-\uDC7F\uDCC8-\uDCCF\uDCDA-\uDD7F\uDDB6\uDDB7\uDDDE-\uDDFF\uDE45-\uDE4F\uDE5A-\uDE5F\uDE6D-\uDE7F\uDEBA-\uDEBF\uDECA-\uDECF\uDEE4-\uDEFF\uDF1B\uDF1C\uDF2C-\uDF2F\uDF47-\uDFFF]|\uD806[\uDC3C-\uDC9F\uDCF3-\uDCFE\uDD07\uDD08\uDD0A\uDD0B\uDD14\uDD17\uDD36\uDD39\uDD3A\uDD47-\uDD4F\uDD5A-\uDD9F\uDDA8\uDDA9\uDDD8\uDDD9\uDDE5-\uDDFF\uDE48-\uDE4F\uDEA3-\uDEAF\uDEF9-\uDEFF\uDF0A-\uDFBF\uDFE2-\uDFEF\uDFFA-\uDFFF]|\uD807[\uDC09\uDC37\uDC46-\uDC4F\uDC6D-\uDC6F\uDC90\uDC91\uDCA8\uDCB7-\uDCFF\uDD07\uDD0A\uDD37-\uDD39\uDD3B\uDD3E\uDD48-\uDD4F\uDD5A-\uDD5F\uDD66\uDD69\uDD8F\uDD92\uDD99-\uDD9F\uDDAA-\uDEDF\uDEF9-\uDEFF\uDF11\uDF3B-\uDF3D\uDF5B-\uDFAF\uDFB1-\uDFBF\uDFF2-\uDFFE]|\uD808[\uDF9A-\uDFFF]|\uD809[\uDC6F\uDC75-\uDC7F\uDD44-\uDFFF]|[\uD80A\uD812-\uD817\uD819\uD824-\uD82A\uD82D\uD82E\uD830-\uD832\uD83F\uD87C\uD87D\uD87F\uD889-\uDB3F\uDB41-\uDBFF][\uDC00-\uDFFF]|\uD80B[\uDC00-\uDF8F\uDFF3-\uDFFF]|\uD80D[\uDC30-\uDC3F\uDC56-\uDC5F]|\uD810[\uDFFB-\uDFFF]|\uD811[\uDE47-\uDFFF]|\uD818[\uDC00-\uDCFF\uDD3A-\uDFFF]|\uD81A[\uDE39-\uDE3F\uDE5F\uDE6A-\uDE6D\uDEBF\uDECA-\uDECF\uDEEE\uDEEF\uDEF6-\uDEFF\uDF46-\uDF4F\uDF5A\uDF62\uDF78-\uDF7C\uDF90-\uDFFF]|\uD81B[\uDC00-\uDD3F\uDD7A-\uDE3F\uDE9B-\uDEFF\uDF4B-\uDF4E\uDF88-\uDF8E\uDFA0-\uDFDF\uDFE5-\uDFEF\uDFF2-\uDFFF]|\uD821[\uDFF8-\uDFFF]|\uD823[\uDCD6-\uDCFE\uDD09-\uDFFF]|\uD82B[\uDC00-\uDFEF\uDFF4\uDFFC\uDFFF]|\uD82C[\uDD23-\uDD31\uDD33-\uDD4F\uDD53\uDD54\uDD56-\uDD63\uDD68-\uDD6F\uDEFC-\uDFFF]|\uD82F[\uDC6B-\uDC6F\uDC7D-\uDC7F\uDC89-\uDC8F\uDC9A\uDC9B\uDCA0-\uDFFF]|\uD833[\uDCFA-\uDCFF\uDEB4-\uDEFF\uDF2E\uDF2F\uDF47-\uDF4F\uDFC4-\uDFFF]|\uD834[\uDCF6-\uDCFF\uDD27\uDD28\uDD73-\uDD7A\uDDEB-\uDDFF\uDE46-\uDEBF\uDED4-\uDEDF\uDEF4-\uDEFF\uDF57-\uDF5F\uDF79-\uDFFF]|\uD835[\uDC55\uDC9D\uDCA0\uDCA1\uDCA3\uDCA4\uDCA7\uDCA8\uDCAD\uDCBA\uDCBC\uDCC4\uDD06\uDD0B\uDD0C\uDD15\uDD1D\uDD3A\uDD3F\uDD45\uDD47-\uDD49\uDD51\uDEA6\uDEA7\uDFCC\uDFCD]|\uD836[\uDE8C-\uDE9A\uDEA0\uDEB0-\uDFFF]|\uD837[\uDC00-\uDEFF\uDF1F-\uDF24\uDF2B-\uDFFF]|\uD838[\uDC07\uDC19\uDC1A\uDC22\uDC25\uDC2B-\uDC2F\uDC6E-\uDC8E\uDC90-\uDCFF\uDD2D-\uDD2F\uDD3E\uDD3F\uDD4A-\uDD4D\uDD50-\uDE8F\uDEAF-\uDEBF\uDEFA-\uDEFE\uDF00-\uDFFF]|\uD839[\uDC00-\uDCCF\uDCFA-\uDDCF\uDDFB-\uDDFE\uDE00-\uDFDF\uDFE7\uDFEC\uDFEF\uDFFF]|\uD83A[\uDCC5\uDCC6\uDCD7-\uDCFF\uDD4C-\uDD4F\uDD5A-\uDD5D\uDD60-\uDFFF]|\uD83B[\uDC00-\uDC70\uDCB5-\uDD00\uDD3E-\uDDFF\uDE04\uDE20\uDE23\uDE25\uDE26\uDE28\uDE33\uDE38\uDE3A\uDE3C-\uDE41\uDE43-\uDE46\uDE48\uDE4A\uDE4C\uDE50\uDE53\uDE55\uDE56\uDE58\uDE5A\uDE5C\uDE5E\uDE60\uDE63\uDE65\uDE66\uDE6B\uDE73\uDE78\uDE7D\uDE7F\uDE8A\uDE9C-\uDEA0\uDEA4\uDEAA\uDEBC-\uDEEF\uDEF2-\uDFFF]|\uD83C[\uDC2C-\uDC2F\uDC94-\uDC9F\uDCAF\uDCB0\uDCC0\uDCD0\uDCF6-\uDCFF\uDDAE-\uDDE5\uDE03-\uDE0F\uDE3C-\uDE3F\uDE49-\uDE4F\uDE52-\uDE5F\uDE66-\uDEFF]|\uD83D[\uDED8-\uDEDB\uDEED-\uDEEF\uDEFD-\uDEFF\uDF77-\uDF7A\uDFDA-\uDFDF\uDFEC-\uDFEF\uDFF1-\uDFFF]|\uD83E[\uDC0C-\uDC0F\uDC48-\uDC4F\uDC5A-\uDC5F\uDC88-\uDC8F\uDCAE\uDCAF\uDCBC-\uDCBF\uDCC2-\uDCFF\uDE54-\uDE5F\uDE6E\uDE6F\uDE7D-\uDE7F\uDE8A-\uDE8E\uDEC7-\uDECD\uDEDD\uDEDE\uDEEA-\uDEEF\uDEF9-\uDEFF\uDF93\uDFFA-\uDFFF]|\uD869[\uDEE0-\uDEFF]|\uD86D[\uDF3A-\uDF3F]|\uD86E[\uDC1E\uDC1F]|\uD873[\uDEA2-\uDEAF]|\uD87A[\uDFE1-\uDFEF]|\uD87B[\uDE5E-\uDFFF]|\uD87E[\uDE1E-\uDFFF]|\uD884[\uDF4B-\uDF4F]|\uD888[\uDFB0-\uDFFF]|\uDB40[\uDC00-\uDCFF\uDDF0-\uDFFF]|[\uD800-\uDBFF](?![\uDC00-\uDFFF])|(?:[^\uD800-\uDBFF]|^)[\uDC00-\uDFFF])|(?:[!-#%-\*,-\/:;\?@\[-\]_\{\}\xA1\xA7\xAB\xB6\xB7\xBB\xBF\u037E\u0387\u055A-\u055F\u0589\u058A\u05BE\u05C0\u05C3\u05C6\u05F3\u05F4\u0609\u060A\u060C\u060D\u061B\u061D-\u061F\u066A-\u066D\u06D4\u0700-\u070D\u07F7-\u07F9\u0830-\u083E\u085E\u0964\u0965\u0970\u09FD\u0A76\u0AF0\u0C77\u0C84\u0DF4\u0E4F\u0E5A\u0E5B\u0F04-\u0F12\u0F14\u0F3A-\u0F3D\u0F85\u0FD0-\u0FD4\u0FD9\u0FDA\u104A-\u104F\u10FB\u1360-\u1368\u1400\u166E\u169B\u169C\u16EB-\u16ED\u1735\u1736\u17D4-\u17D6\u17D8-\u17DA\u1800-\u180A\u1944\u1945\u1A1E\u1A1F\u1AA0-\u1AA6\u1AA8-\u1AAD\u1B4E\u1B4F\u1B5A-\u1B60\u1B7D-\u1B7F\u1BFC-\u1BFF\u1C3B-\u1C3F\u1C7E\u1C7F\u1CC0-\u1CC7\u1CD3\u2010-\u2027\u2030-\u2043\u2045-\u2051\u2053-\u205E\u207D\u207E\u208D\u208E\u2308-\u230B\u2329\u232A\u2768-\u2775\u27C5\u27C6\u27E6-\u27EF\u2983-\u2998\u29D8-\u29DB\u29FC\u29FD\u2CF9-\u2CFC\u2CFE\u2CFF\u2D70\u2E00-\u2E2E\u2E30-\u2E4F\u2E52-\u2E5D\u3001-\u3003\u3008-\u3011\u3014-\u301F\u3030\u303D\u30A0\u30FB\uA4FE\uA4FF\uA60D-\uA60F\uA673\uA67E\uA6F2-\uA6F7\uA874-\uA877\uA8CE\uA8CF\uA8F8-\uA8FA\uA8FC\uA92E\uA92F\uA95F\uA9C1-\uA9CD\uA9DE\uA9DF\uAA5C-\uAA5F\uAADE\uAADF\uAAF0\uAAF1\uABEB\uFD3E\uFD3F\uFE10-\uFE19\uFE30-\uFE52\uFE54-\uFE61\uFE63\uFE68\uFE6A\uFE6B\uFF01-\uFF03\uFF05-\uFF0A\uFF0C-\uFF0F\uFF1A\uFF1B\uFF1F\uFF20\uFF3B-\uFF3D\uFF3F\uFF5B\uFF5D\uFF5F-\uFF65]|\uD800[\uDD00-\uDD02\uDF9F\uDFD0]|\uD801\uDD6F|\uD802[\uDC57\uDD1F\uDD3F\uDE50-\uDE58\uDE7F\uDEF0-\uDEF6\uDF39-\uDF3F\uDF99-\uDF9C]|\uD803[\uDD6E\uDEAD\uDF55-\uDF59\uDF86-\uDF89]|\uD804[\uDC47-\uDC4D\uDCBB\uDCBC\uDCBE-\uDCC1\uDD40-\uDD43\uDD74\uDD75\uDDC5-\uDDC8\uDDCD\uDDDB\uDDDD-\uDDDF\uDE38-\uDE3D\uDEA9\uDFD4\uDFD5\uDFD7\uDFD8]|\uD805[\uDC4B-\uDC4F\uDC5A\uDC5B\uDC5D\uDCC6\uDDC1-\uDDD7\uDE41-\uDE43\uDE60-\uDE6C\uDEB9\uDF3C-\uDF3E]|\uD806[\uDC3B\uDD44-\uDD46\uDDE2\uDE3F-\uDE46\uDE9A-\uDE9C\uDE9E-\uDEA2\uDF00-\uDF09\uDFE1]|\uD807[\uDC41-\uDC45\uDC70\uDC71\uDEF7\uDEF8\uDF43-\uDF4F\uDFFF]|\uD809[\uDC70-\uDC74]|\uD80B[\uDFF1\uDFF2]|\uD81A[\uDE6E\uDE6F\uDEF5\uDF37-\uDF3B\uDF44]|\uD81B[\uDD6D-\uDD6F\uDE97-\uDE9A\uDFE2]|\uD82F\uDC9F|\uD836[\uDE87-\uDE8B]|\uD839\uDDFF|\uD83A[\uDD5E\uDD5F])|[ \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000]|(?:[\$\+<->\^`\|~\xA2-\xA6\xA8\xA9\xAC\xAE-\xB1\xB4\xB8\xD7\xF7\u02C2-\u02C5\u02D2-\u02DF\u02E5-\u02EB\u02ED\u02EF-\u02FF\u0375\u0384\u0385\u03F6\u0482\u058D-\u058F\u0606-\u0608\u060B\u060E\u060F\u06DE\u06E9\u06FD\u06FE\u07F6\u07FE\u07FF\u0888\u09F2\u09F3\u09FA\u09FB\u0AF1\u0B70\u0BF3-\u0BFA\u0C7F\u0D4F\u0D79\u0E3F\u0F01-\u0F03\u0F13\u0F15-\u0F17\u0F1A-\u0F1F\u0F34\u0F36\u0F38\u0FBE-\u0FC5\u0FC7-\u0FCC\u0FCE\u0FCF\u0FD5-\u0FD8\u109E\u109F\u1390-\u1399\u166D\u17DB\u1940\u19DE-\u19FF\u1B61-\u1B6A\u1B74-\u1B7C\u1FBD\u1FBF-\u1FC1\u1FCD-\u1FCF\u1FDD-\u1FDF\u1FED-\u1FEF\u1FFD\u1FFE\u2044\u2052\u207A-\u207C\u208A-\u208C\u20A0-\u20C0\u2100\u2101\u2103-\u2106\u2108\u2109\u2114\u2116-\u2118\u211E-\u2123\u2125\u2127\u2129\u212E\u213A\u213B\u2140-\u2144\u214A-\u214D\u214F\u218A\u218B\u2190-\u2307\u230C-\u2328\u232B-\u2429\u2440-\u244A\u249C-\u24E9\u2500-\u2767\u2794-\u27C4\u27C7-\u27E5\u27F0-\u2982\u2999-\u29D7\u29DC-\u29FB\u29FE-\u2B73\u2B76-\u2B95\u2B97-\u2BFF\u2CE5-\u2CEA\u2E50\u2E51\u2E80-\u2E99\u2E9B-\u2EF3\u2F00-\u2FD5\u2FF0-\u2FFF\u3004\u3012\u3013\u3020\u3036\u3037\u303E\u303F\u309B\u309C\u3190\u3191\u3196-\u319F\u31C0-\u31E5\u31EF\u3200-\u321E\u322A-\u3247\u3250\u3260-\u327F\u328A-\u32B0\u32C0-\u33FF\u4DC0-\u4DFF\uA490-\uA4C6\uA700-\uA716\uA720\uA721\uA789\uA78A\uA828-\uA82B\uA836-\uA839\uAA77-\uAA79\uAB5B\uAB6A\uAB6B\uFB29\uFBB2-\uFBC2\uFD40-\uFD4F\uFDCF\uFDFC-\uFDFF\uFE62\uFE64-\uFE66\uFE69\uFF04\uFF0B\uFF1C-\uFF1E\uFF3E\uFF40\uFF5C\uFF5E\uFFE0-\uFFE6\uFFE8-\uFFEE\uFFFC\uFFFD]|\uD800[\uDD37-\uDD3F\uDD79-\uDD89\uDD8C-\uDD8E\uDD90-\uDD9C\uDDA0\uDDD0-\uDDFC]|\uD802[\uDC77\uDC78\uDEC8]|\uD803[\uDD8E\uDD8F]|\uD805\uDF3F|\uD807[\uDFD5-\uDFF1]|\uD81A[\uDF3C-\uDF3F\uDF45]|\uD82F\uDC9C|\uD833[\uDC00-\uDCEF\uDD00-\uDEB3\uDF50-\uDFC3]|\uD834[\uDC00-\uDCF5\uDD00-\uDD26\uDD29-\uDD64\uDD6A-\uDD6C\uDD83\uDD84\uDD8C-\uDDA9\uDDAE-\uDDEA\uDE00-\uDE41\uDE45\uDF00-\uDF56]|\uD835[\uDEC1\uDEDB\uDEFB\uDF15\uDF35\uDF4F\uDF6F\uDF89\uDFA9\uDFC3]|\uD836[\uDC00-\uDDFF\uDE37-\uDE3A\uDE6D-\uDE74\uDE76-\uDE83\uDE85\uDE86]|\uD838[\uDD4F\uDEFF]|\uD83B[\uDCAC\uDCB0\uDD2E\uDEF0\uDEF1]|\uD83C[\uDC00-\uDC2B\uDC30-\uDC93\uDCA0-\uDCAE\uDCB1-\uDCBF\uDCC1-\uDCCF\uDCD1-\uDCF5\uDD0D-\uDDAD\uDDE6-\uDE02\uDE10-\uDE3B\uDE40-\uDE48\uDE50\uDE51\uDE60-\uDE65\uDF00-\uDFFF]|\uD83D[\uDC00-\uDED7\uDEDC-\uDEEC\uDEF0-\uDEFC\uDF00-\uDF76\uDF7B-\uDFD9\uDFE0-\uDFEB\uDFF0]|\uD83E[\uDC00-\uDC0B\uDC10-\uDC47\uDC50-\uDC59\uDC60-\uDC87\uDC90-\uDCAD\uDCB0-\uDCBB\uDCC0\uDCC1\uDD00-\uDE53\uDE60-\uDE6D\uDE70-\uDE7C\uDE80-\uDE89\uDE8F-\uDEC6\uDECE-\uDEDC\uDEDF-\uDEE9\uDEF0-\uDEF8\uDF00-\uDF92\uDF94-\uDFEF]))+$/, ""), a ? (this.utterance = new SpeechSynthesisUtterance(a), this.utterance.onstart = function() {
                        e.speechSpeaking = !0, e.skipAutoNext = !1
                    }, this.utterance.onend = function() {
                        e.skipAutoNext ? (e.skipAutoNext = !1, e.speechSpeaking = !1) : e.speechNext()
                    }, this.utterance.onerror = function(t) {
                        "canceled" === t.error || "interrupted" === t.error || (t.error || t.name) && e.$message.error("朗读错误:  ".concat(t.type || "", "  ").concat(t.error || t.name || t.toString())), setTimeout((function() {
                            e.speechSpeaking = window.speechSynthesis.speaking || !1
                        }), 200)
                    }, this.utterance.voice = t, this.utterance.pitch = this.speechPitch, this.utterance.rate = this.speechRate, this.showParagraph(n, !0), n.className = "reading", this.speechSpeaking = !0, window.speechSynthesis.speak(this.utterance)) : this.speechNext())
                } else this.speechNext()
            }
        },
        stopSpeech: function(e) {
            try {
                if (this.skipAutoNext = !0, "local" === this.ttsType ? window.speechSynthesis.cancel() : this.$refs.ttsAudio.pause(), e) {
                    var t = this.getCurrentParagraph();
                    t && (t.className = "")
                }
            } catch (re) {}
        },
        restartSpeech: function() {
            var e = this;
            this.stopSpeech(), setTimeout((function() {
                e.startSpeech()
            }), 100)
        },
        toggleSpeech: function() {
            this.speechSpeaking ? this.stopSpeech(!0) : this.startSpeech()
        },
        speechPrev: function() {
            var e = this;
            window.speechSynthesis.speaking && this.stopSpeech();
            var t = this.getCurrentParagraph(),
                n = this.getPrevParagraph(1);
            n ? (this.showParagraph(n, !0), t.className = "", n.className = "reading", this.startSpeech()) : (this.onNextContentRendered((function() {
                setTimeout((function() {
                    e.startSpeech()
                }), 100)
            })), this.toLastChapter())
        },
        speechNext: function() {
            var e = this;
            window.speechSynthesis.speaking && this.stopSpeech();
            var t = this.getCurrentParagraph(),
                n = this.getNextParagraph(1);
            n ? (this.showParagraph(n, !0), t.className = "", n.className = "reading", this.startSpeech()) : (this.onNextContentRendered((function() {
                setTimeout((function() {
                    e.startSpeech()
                }), 100)
            })), this.toNextChapter())
        },
        getCurrentParagraph: function() {
            var e = this.getContentParagraphContainer().querySelectorAll(".reading"),
                t = null;
            if (e.length) t = e[0];
            else
                for (var n = this.getContentParagraphList(), a = 0; a < n.length; a++) {
                    var u = n[a].getBoundingClientRect();
                    if (this.isSlideRead) {
                        if (u.right > 0) {
                            t = n[a];
                            break
                        }
                    } else {
                        var o = u.bottom;
                        if (this.isEpubIframe && (o -= document.documentElement.scrollTop || document.body.scrollTop), o > 50 + (0 | window.webAppDistance) + (0 | this.$store.state.safeArea.top)) {
                            t = n[a];
                            break
                        }
                    }
                }
            return t
        },
        getPrevParagraph: function(e) {
            for (var t = this.getCurrentParagraph(), n = this.getContentParagraphList(), a = 0; a < n.length; a++)
                if (a > 0 && t === n[a]) return n[a - e];
            return null
        },
        getNextParagraph: function(e) {
            for (var t = this.getCurrentParagraph(), n = this.getContentParagraphList(), a = 0; a < n.length; a++)
                if (t === n[a]) return n[a + (e || 1)];
            return null
        },
        exitRead: function() {
            var e = this.getCurrentParagraph();
            this.stopSpeech(!0), this.showReadBar = !1, this.showParagraph(e)
        },
        showParagraph: function(e, t) {
            var n = this;
            e && (this.isSlideRead ? this.$nextTick((function() {
                var t = e.getBoundingClientRect();
                t.left > n.windowSize.width - n.slideDistance && n.showPage(Math.round(t.left / (n.windowSize.width - n.slideDistance)) + 1, 0)
            })) : t && this.$nextTick((function() {
                var t = e.getBoundingClientRect(),
                    a = t.top;
                n.isEpubIframe && (a -= document.documentElement.scrollTop || document.body.scrollTop), n.scrollContent(a - (n.$store.state.miniInterface ? n.getFirstParagraphPos().bottom : 0) - (0 | window.webAppDistance) - (0 | n.$store.state.safeArea.top), 0)
            })))
        },
        getFirstParagraphPos: function() {
            return this.$refs.top.getBoundingClientRect()
        },
        scrollHandler: function() {
            var e = this,
                t = document.documentElement.scrollTop || document.body.scrollTop;
            if (this.isSlideRead || (this.currentPage = Math.round((t + this.windowSize.height) / (this.windowSize.height - this.scrollOffset))), this.isScrollRead) {
                var n = this.lastScrollTop || 0;
                if (n > 0 && 0 === t);
                else if (t > document.documentElement.scrollHeight - 2 * this.windowSize.height && !this.preCaching && this.startSavePosition) {
                    this.preCaching = !0;
                    var a = this.chapterIndex + 1;
                    this.showChapterList.length && (a = this.showChapterList[this.showChapterList.length - 1].index + 1), this.showNextChapterSize = a - this.chapterIndex, this.loadShowChapter(a).then((function() {
                        e.computeShowChapterList(), e.preCaching = !1
                    }))["catch"]((function() {
                        e.preCaching = !1
                    }))
                }
            }
            this.lastScrollTop = t, this.scrollTimer && clearTimeout(this.scrollTimer), this.scrollTimer = setTimeout(this.saveReadingPosition, 100)
        },
        beforeReadMethodChange: function() {
            this.currentParagraph = this.getCurrentParagraph()
        },
        showPosition: function(e, t) {
            var n = this;
            if (this.$refs.bookContentRef)
                if (this.isAudio || this.isVideo) this.$refs.bookContentRef.ensureSeekTime(e);
                else if (this.isEpub || this.isCarToon) this.scrollContent(e, 0, !0), t && t();
            else {
                for (var a = this.getContentParagraphList(!0), u = 0; u < a.length; u++)
                    if (a[u].dataset && "undefined" !== typeof a[u].dataset.pos && +a[u].dataset.pos >= e) {
                        this.showParagraph(a[u], !0);
                        break
                    } t && t()
            } else setTimeout((function() {
                n.showPosition(e, t)
            }), 10)
        },
        saveReadingPosition: function() {
            try {
                if (this.error || !this.startSavePosition) return;
                setStorage("bookChapterPage@" + this.$store.getters.readingBook.name + "_" + this.$store.getters.readingBook.author, this.currentPage);
                var e = null;
                if (this.isAudio || this.isVideo) e = this.$refs.bookContentRef ? this.$refs.bookContentRef.currentTime : 0;
                else if (this.isEpub || this.isCarToon) e = document.documentElement.scrollTop || document.body.scrollTop;
                else {
                    if (this.preCaching) return;
                    if (this.currentParagraph = this.getCurrentParagraph(), this.currentParagraph) {
                        var t = this.currentParagraph;
                        while (t.className.indexOf("chapter-content") < 0)
                            if (t = t.parentNode, t === this.$refs.bookContentRef.$el) break;
                        if (t) {
                            if (t.dataset && "undefined" !== typeof t.dataset.index) {
                                var n = +t.dataset.index;
                                if (n != this.$store.getters.readingBook.index) {
                                    var a = __merge({}, this.$store.getters.readingBook);
                                    a.index = n, this.$store.commit("setReadingBook", a), this.saveBookProgress(), this.title = this.$store.getters.readingBook.catalog[n].title
                                }
                            }
                            e = t.innerText.indexOf(this.currentParagraph.innerText)
                        }
                    }
                }
                null !== e && setStorage("bookChapterProgress@" + this.$store.getters.readingBook.name + "_" + this.$store.getters.readingBook.author, e)
            } catch (re) {}
        },
        autoShowPosition: function(e) {
            var t = this,
                n = function() {
                    if (setTimeout((function() {
                            t.startSavePosition = !0
                        }), 2e3), !t.error) {
                        var e = getStorage("bookChapterProgress@" + t.$store.getters.readingBook.name + "_" + t.$store.getters.readingBook.author);
                        if (e && +e) t.$nextTick((function() {
                            t.showPosition(+e, (function() {
                                t.startSavePosition = !0
                            }))
                        }));
                        else {
                            var n = getStorage("bookChapterPage@" + t.$store.getters.readingBook.name + "_" + t.$store.getters.readingBook.author);
                            n && +n && t.$nextTick((function() {
                                t.computePages((function() {
                                    t.showPage(n, 0)
                                }))
                            }))
                        }
                    }
                };
            e ? n() : this.onNextContentRendered(n)
        },
        onNextContentRendered: function(e) {
            var t = this;
            this.isEpub ? this.$once("iframeLoad", (function() {
                t.$nextTick((function() {
                    e && e()
                }))
            })) : this.isCarToon ? this.$once("lazyload", (function() {
                t.$nextTick((function() {
                    e && e()
                }))
            })) : this.$once("showContent", (function() {
                t.$nextTick((function() {
                    e && e()
                }))
            }))
        },
        onNextContentPagesInit: function(e) {
            var t = this;
            if (this.isEpub) this.$once("iframeLoad", (function() {
                t.$nextTick((function() {
                    e && e()
                }))
            }));
            else {
                var n = !1;
                this.$once("showContent", (function() {
                    n = !0
                }));
                var a = function() {
                    n && (t.$off("afterComputePages", a), e && e())
                };
                this.$on("afterComputePages", a)
            }
        },
        wakeLock: function() {
            if ("WakeLock" in window && "request" in window.WakeLock) {
                var e = null,
                    t = function() {
                        var e = new AbortController,
                            t = e.signal;
                        return window.WakeLock.request("screen", {
                            signal: t
                        })["catch"]((function(e) {
                            e.name
                        })), e
                    };
                e = t();
                var n = function() {
                    null !== e && "visible" === document.visibilityState && (e = t())
                };
                return document.addEventListener("visibilitychange", n), document.addEventListener("fullscreenchange", n),
                    function() {
                        null != e && (e.abort(), e = null), document.removeEventListener("visibilitychange", n), document.removeEventListener("fullscreenchange", n)
                    }
            }
            if ("wakeLock" in navigator && "request" in navigator.wakeLock) {
                var a = null,
                    u = function() {
                        var e = asyncToGenerator(regeneratorRuntime.mark((function e() {
                            return regeneratorRuntime.wrap((function(e) {
                                while (1) switch (e.prev = e.next) {
                                    case 0:
                                        return e.prev = 0, e.next = 3, navigator.wakeLock.request("screen");
                                    case 3:
                                        a = e.sent, a.addEventListener("release", (function() {})), e.next = 9;
                                        break;
                                    case 7:
                                        e.prev = 7, e.t0 = e["catch"](0);
                                    case 9:
                                    case "end":
                                        return e.stop()
                                }
                            }), e, null, [
                                [0, 7]
                            ])
                        })));
                        return function() {
                            return e.apply(this, arguments)
                        }
                    }();
                u();
                var i = function() {
                    null !== a && "visible" === document.visibilityState && u()
                };
                return document.addEventListener("visibilitychange", i), document.addEventListener("fullscreenchange", i),
                    function() {
                        null != a && (a.release(), a = null), document.removeEventListener("visibilitychange", i), document.removeEventListener("fullscreenchange", i)
                    }
            }
        },
        lazyloadHandler: function() {
            this.isAudio || this.isVideo || (this.computePages(), this.$emit("lazyload"))
        },
        showCacheContent: function() {
            this.showCacheContentZone = !this.showCacheContentZone
        },
        cacheChapterContent: function(e) {
            var t = this,
                n = [];
            n = !0 === e ? n.concat(this.catalog.slice(this.chapterIndex + 1, this.catalog.length)) : n.concat(this.catalog.slice(this.chapterIndex + 1, Math.min(this.catalog.length, this.chapterIndex + 1 + e))), n.length ? (this.isCachingContent = !0, this.cachingContentTip = "正在缓存章节  0/" + n.length, this.cachingHandler = createConcurrentRunner(2, (function(e) {
                t.cachingContentTip = "正在缓存章节  " + e.requestCount + "/" + n.length, e.isEnd() && (t.$message.success("缓存完成"), t.isCachingContent = !1, t.cachingContentTip = "")
            })), n.forEach((function(e) {
                t.cachingHandler((function() {
                    return t.getBookContent(e.index, {
                        timeout: 1e3 * t.$store.getters.config.chapterRequestTimeout,
                        silent: !0
                    }, !1, !0)
                }))
            }))) : this.$message.error("不需要缓存")
        },
        cancelCaching: function() {
            this.cachingHandler && this.cachingHandler.cancel && (this.cachingHandler.cancel(), this.isCachingContent = !1, this.cachingContentTip = "")
        },
        startAutoReading: function() {
            var e = this;
            this.showToolBar = !1, this.autoReading = !0, this.$nextTick((function() {
                e.autoRead()
            }))
        },
        autoRead: function() {
            var e = this;
            if (this.autoReading)
                if (this.showToolBar) this.autoReadingTimer = setTimeout((function() {
                    e.autoRead()
                }), 300);
                else if ("像素滚动" !== this.config.autoReadingMethod) {
                var t = this.getCurrentParagraph(),
                    n = this.getNextParagraph();
                if (n) {
                    t.className = "reading", n.className = "";
                    var a = this.config.autoReadingLineTime;
                    try {
                        var u = t.getBoundingClientRect();
                        a *= Math.ceil(u.height / this.config.fontSize / this.config.lineHeight)
                    } catch (re) {}
                    this.autoReadingTimer = setTimeout((function() {
                        t.className = "", n.className = "reading", e.showParagraph(n, !0), setTimeout((function() {
                            e.autoRead()
                        }), 32)
                    }), a)
                } else this.onNextContentRendered((function() {
                    setTimeout((function() {
                        e.autoRead()
                    }), 100)
                })), this.toNextChapter((function() {
                    e.autoReading = !1
                }))
            } else this.autoReadByPixel()
        },
        autoReadByPixel: function() {
            var e = this;
            if (this.autoReading)
                if (this.showToolBar) this.autoReadingTimer = setTimeout((function() {
                    e.autoRead()
                }), 300);
                else if ("像素滚动" === this.config.autoReadingMethod) {
                var t = document.documentElement.scrollTop || document.body.scrollTop;
                t + this.windowSize.height < document.documentElement.scrollHeight ? this.autoReadingTimer = setTimeout((function() {
                    e.scrollContent(e.config.autoReadingPixel, 0), e.autoReadByPixel()
                }), this.config.autoReadingLineTime) : (this.onNextContentRendered((function() {
                    setTimeout((function() {
                        e.autoReadByPixel()
                    }), 100)
                })), this.toNextChapter((function() {
                    e.autoReading = !1
                })))
            } else this.autoRead()
        },
        stopAutoReading: function() {
            this.autoReadingTimer && clearInterval(this.autoReadingTimer), this.autoReading = !1;
            var e = this.getCurrentParagraph();
            e.className = ""
        },
        toggleAutoReading: function() {
            this.autoReading ? this.stopAutoReading() : this.startAutoReading()
        },
        showReadingBookInfo: function() {
            var e = __merge({}, this.$store.getters.readingBook),
                t = this.$store.getters.shelfBooks.find((function(t) {
                    return t.bookUrl === e.bookUrl
                }));
            e = Object.assign(e, t || {}), eventBus.$emit("showBookInfoDialog", e)
        },
        formatChinese: function(e) {
            return this.isEpub || this.isAudio || this.isVideo || this.isCbz || this.isCarToon ? e : "简体" === this.config.chineseFont ? Dt(e) : "繁体" === this.config.chineseFont ? dt(e) : e
        },
        showSearchBookContentDialog: function() {
            var e = __merge({}, this.$store.getters.readingBook),
                t = this.$store.getters.shelfBooks.find((function(t) {
                    return t.bookUrl === e.bookUrl
                }));
            e = Object.assign(e, t || {}), eventBus.$emit("showSearchBookContentDialog", e)
        },
        showMatchKeyword: function(e) {
            var t = this;
            if (!this._inactive)
                if (this.$refs.bookContentRef)
                    if (!this.isEpubResolve || this.$refs.bookContentRef.$el && this.$refs.bookContentRef.$el.shadowRoot) {
                        if (this.isEpubIframe) try {
                            if (!this.$refs.bookContentRef.$el || !this.$refs.bookContentRef.$el.contentWindow || !this.$refs.bookContentRef.$el.contentWindow.document || !this.$refs.bookContentRef.$el.contentWindow.document.querySelectorAll("body")) return void setTimeout((function() {
                                t.showMatchKeyword(e)
                            }), 10)
                        } catch (s) {
                            return
                        }
                        try {
                            for (var n = this.getContentParagraphList(!0), a = 0, u = 0; u < n.length; u++) {
                                var o = n[u].innerText,
                                    r = -1,
                                    i = !1;
                                while (1) {
                                    if (r = o.indexOf(e.query, r + 1), !(r >= 0)) break;
                                    if (a++, a === e.resultCountWithinChapter + 1) {
                                        i = !0, this.showParagraph(n[u], !0);
                                        break
                                    }
                                }
                                if (i) break
                            }
                        } catch (re) {}
                    } else setTimeout((function() {
                        t.showMatchKeyword(e)
                    }), 10);
            else setTimeout((function() {
                t.showMatchKeyword(e)
            }), 10)
        },
        getParagraphListInView: function() {
            for (var e = this.getContentParagraphList(), t = [], n = 0; n < e.length; n++) {
                var a = e[n].getBoundingClientRect();
                this.isSlideRead ? a.right > 0 && a.left > 0 && t.push(e[n]) : a.bottom > 50 + (0 | window.webAppDistance) + (0 | this.$store.state.safeArea.top) && a.bottom < this.windowSize.height && t.push(e[n])
            }
            return t
        },
        showBookmarkDialog: function() {
            var e = __merge({}, this.$store.getters.readingBook),
                t = this.$store.getters.shelfBooks.find((function(t) {
                    return t.bookUrl === e.bookUrl
                }));
            e = Object.assign(e, t || {}), eventBus.$emit("showBookmarkDialog", e)
        },
        getContentParagraphContainer: function() {
            if (!this.isEpub) return this.$refs.bookContentRef.$el;
            if (!this.isEpubIframe) return this.$refs.bookContentRef.$el.shadowRoot;
            try {
                return this.$refs.bookContentRef.$el.contentWindow.document.querySelectorAll("body"), this.$refs.bookContentRef.$el.contentWindow.document
            } catch (e) {
                return this.$refs.bookContentRef.$el
            }
        },
        getContentParagraphList: function(e) {
            var t = [];
            return t = this.isEpub ? (this.isEpubIframe, this.getContentParagraphContainer().querySelectorAll("h1,h2,h3,h4,p")) : this.getContentParagraphContainer().querySelectorAll(e ? ".reading-chapter h3,p" : "h3,p"), t
        },
        getContentMatchParagraph: function(e, t, n) {
            t = t || .7;
            var a = e.replace(/\\n+/g, "\n").split(/\n+/).map((function(e) {
                return e.replace(PUNCTUATION_RE, "")
            })).filter((function(e) {
                return e
            }));
            try {
                for (var u = this.getContentParagraphList(!0), o = null, r = 0; r < u.length; r++) {
                    for (var i = !0, s = 0, l = r, c = 0; c < a.length; c++) {
                        var p = null;
                        while (r + s < u.length) {
                            if (p = u[r + s].innerText.replace(PUNCTUATION_RE, ""), p.length) break;
                            s++, l++
                        }
                        if (!p) {
                            i = !1;
                            break
                        }
                        var d = stringSimilarity(p, a[c]);
                        if (d < t) {
                            i = !1;
                            break
                        }
                        s++
                    }
                    if (i) {
                        o = u[l];
                        break
                    }
                }
                if (o) return o;
                if (t - .1 >= n) return this.getContentMatchParagraph(e, t - .1, n)
            } catch (re) {
                console.error(re)
            }
            return null
        },
        showContentMatchParagraph: function(e) {
            if (!this._inactive) {
                var t = this.getContentMatchParagraph(e, 1, .6);
                t ? this.showParagraph(t, !0) : this.$message.error("无法定位内容所在段落")
            }
        },
        showBookmark: function(e) {
            var t = this;
            if (!this._inactive)
                if (this.$refs.bookContentRef)
                    if (!this.isEpubResolve || this.$refs.bookContentRef.$el && this.$refs.bookContentRef.$el.shadowRoot) {
                        if (this.isEpubIframe) try {
                            if (!this.$refs.bookContentRef.$el || !this.$refs.bookContentRef.$el.contentWindow || !this.$refs.bookContentRef.$el.contentWindow.document || !this.$refs.bookContentRef.$el.contentWindow.document.querySelectorAll("body")) return void setTimeout((function() {
                                t.showBookmark(e)
                            }), 10)
                        } catch (n) {
                            return
                        }
                        this.showContentMatchParagraph(e.bookText)
                    } else setTimeout((function() {
                        t.showBookmark(e)
                    }), 10);
            else setTimeout((function() {
                t.showBookmark(e)
            }), 10)
        },
        readOriginal: function() {
            if (this.isPdf && this.$store.getters.readingBook.originName)
                if (this.$store.getters.readingBook.originName.indexOf("localStore") >= 0 || this.$store.getters.readingBook.originName.indexOf("webdav") >= 0) {
                    var e = buildURL(this.api + "/file/download", {
                        home: this.$store.getters.readingBook.originName.indexOf("localStore") >= 0 ? "__LOCAL_STORE__" : "__WEBDAV__",
                        path: this.$store.getters.readingBook.originName.replace(/^.*(localStore|webdav)/g, ""),
                        stream: 1,
                        accessToken: this.$store.state.token
                    });
                    window.open(e, "__blank")
                } else {
                    var t = this.$store.getters.readingBook.bookUrl.replace("storage/data", "book-assets");
                    window.open(t, "__blank")
                }
        }
    }
}
</script>

<!-- 页面自身样式：产物 style.css（提取时去掉了 [data-v-*] 属性，故此处 scoped 重新生成） -->
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
