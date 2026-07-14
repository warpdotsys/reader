<template>
  <div
    class="chapter-wrapper"
    :style="readerPageTheme"
    :class="{ night: isNight, day: !isNight }"
    @click="handlePageClick"
    @wheel="handleWheel"
    @mouseup="handleTextSelection"
    @touchstart.passive="rememberTouchStart"
    @touchend.passive="rememberTouchEnd"
  >
    <div class="tool-bar" :style="leftBarTheme">
      <div class="tools">
        <el-popover
          placement="right"
          :width="popupWidth"
          trigger="click"
          :show-arrow="false"
          v-model:visible="popCataVisible"
          popper-class="pop-cata"
        >
          <PopCatalog @getContent="getContent" class="popup" />
          <template #reference>
            <div class="tool-icon" :class="{ 'no-point': false }">
              <div class="iconfont">&#58905;</div>
              <div class="icon-text">目录</div>
            </div>
          </template>
        </el-popover>
        <el-popover
          placement="right"
          :width="popupWidth"
          trigger="click"
          :show-arrow="false"
          v-model:visible="readSettingsVisible"
          popper-class="pop-setting"
        >
          <read-settings class="popup" />
          <template #reference>
            <div class="tool-icon" :class="{ 'no-point': noPoint }">
              <div class="iconfont">&#58971;</div>
              <div class="icon-text">设置</div>
            </div>
          </template>
        </el-popover>
        <div class="tool-icon" @click="toShelf">
          <div class="iconfont">&#58892;</div>
          <div class="icon-text">书架</div>
        </div>
        <div class="tool-icon" :class="{ 'no-point': noPoint }" @click="toTop">
          <div class="iconfont">&#58914;</div>
          <div class="icon-text">顶部</div>
        </div>
        <button class="tool-icon auto-read-button" type="button" :title="autoReading ? '停止自动阅读' : '开始自动阅读'" @click="toggleAutoReading">
          <VideoPause v-if="autoReading" />
          <VideoPlay v-else />
          <div class="icon-text">自动</div>
        </button>
        <button
          class="tool-icon auto-read-button"
          type="button"
          :title="speechActive ? '停止朗读' : '朗读正文'"
          @click.stop="toggleSpeechReading"
        >
          <VideoPause v-if="speechActive" />
          <Headset v-else />
          <div class="icon-text">朗读</div>
        </button>
        <button
          class="tool-icon auto-read-button"
          type="button"
          title="导出书籍"
          @click.stop="downloadCurrentBook"
        >
          <Download />
          <div class="icon-text">导出</div>
        </button>
        <button
          class="tool-icon auto-read-button"
          type="button"
          title="分章导出 ZIP"
          @click.stop="downloadEpisodeArchive"
        >
          <FolderOpened />
          <div class="icon-text">分章</div>
        </button>
        <button
          class="tool-icon auto-read-button"
          type="button"
          title="直链上传当前书籍"
          @click.stop="uploadCurrentBook"
        >
          <Upload />
          <div class="icon-text">直链</div>
        </button>
        <div
          class="tool-icon"
          :class="{ 'no-point': noPoint }"
          @click="toBottom"
        >
          <div class="iconfont">&#58915;</div>
          <div class="icon-text">底部</div>
        </div>
      </div>
    </div>
    <div class="read-bar" :style="rightBarTheme">
      <div class="tools">
        <div
          class="tool-icon"
          :class="{ 'no-point': noPoint }"
          @click="toPreChapter"
        >
          <div class="iconfont">&#58920;</div>
          <span v-if="miniInterface">上一章</span>
        </div>
        <div
          class="tool-icon"
          :class="{ 'no-point': noPoint }"
          @click="toNextChapter"
        >
          <span v-if="miniInterface">下一章</span>
          <div class="iconfont">&#58913;</div>
        </div>
      </div>
    </div>
    <el-image-viewer
      v-if="imagePreviewUrl"
      :url-list="[imagePreviewUrl]"
      :initial-index="0"
      @close="imagePreviewUrl = ''"
    />
    <div class="chapter-bar"></div>
    <div class="chapter-progress" aria-hidden="true">
      <span :style="{ width: `${readingProgress}%` }"></span>
    </div>
    <div class="brightness-shade" :style="{ opacity: brightnessShadeOpacity }"></div>
    <div
      v-if="currentMangaChapter && !mangaFooterConfig.hideFooter"
      class="manga-footer"
      :class="{ 'manga-footer-center': mangaFooterConfig.footerOrientation === 1 }"
      role="status"
    >
      {{ mangaFooterText }}
    </div>
    <div
      v-if="selectionSpeech.visible"
      class="selection-speech-button"
      :class="{ 'selection-action-menu': readingPreferences.expandTextMenu || processTextEnabled }"
      :style="{ left: `${selectionSpeech.x}px`, top: `${selectionSpeech.y}px` }"
      @click.stop
    >
      <button
        v-if="readingPreferences.expandTextMenu"
        type="button"
        title="复制选中文本"
        aria-label="复制选中文本"
        @click.stop="copySelectedText"
      ><CopyDocument /></button>
      <button
        v-if="readingPreferences.expandTextMenu"
        type="button"
        title="搜索选中文本"
        aria-label="搜索选中文本"
        @click.stop="searchSelectedText"
      ><Search /></button>
      <button
        v-if="processTextEnabled"
        type="button"
        title="保存为书签摘录"
        aria-label="保存为书签摘录"
        @click.stop="processSelectedText"
      ><Document /></button>
      <button
        type="button"
        title="朗读选中文本"
        aria-label="朗读选中文本"
        @click.stop="speakSelectedText"
      ><Headset /></button>
    </div>
    <label
      v-if="readingPreferences.showBrightnessView"
      class="brightness-control"
      :class="{ 'brightness-right': readingPreferences.brightnessVwPos }"
    >
      <Sunny />
      <input
        v-model.number="readerBrightness"
        type="range"
        min="0"
        max="100"
        step="1"
        aria-label="阅读亮度"
        @change="saveBrightnessPreference"
      />
      <span>{{ readerBrightness }}%</span>
    </label>
    <div
      class="chapter"
      ref="content"
      :class="{
        'two-page': doublePageEnabled,
        'manga-horizontal': mangaHorizontalEnabled,
        'manga-snap-disabled': mangaPreferences.disableHorizontalPageSnap,
      }"
      :style="chapterTheme"
    >
      <div class="content">
        <div class="top-bar" ref="top"></div>
        <div
          v-for="data in chapterData"
          :key="data.index"
          :chapterIndex="data.index"
          :class="{ 'optimized-chapter': readingPreferences.optimizeRender }"
          ref="chapter"
        >
          <chapter-content
            ref="chapterRef"
            :chapterIndex="data.index"
            :contents="data.content"
            :title="data.title"
            :spacing="store.config.spacing"
            :fontSize="fontSize"
            :fontFamily="fontFamily"
            :justified="readingPreferences.textFullJustify"
            :bottom-justify="readingPreferences.textBottomJustify"
            :selectable="readingPreferences.selectText"
            :zh-layout="readingPreferences.useZhLayout"
            :adapt-special-style="readingPreferences.adaptSpecialStyle"
            :body-to-line-height="readingPreferences.readBodyToLh"
            :manga-ui="mangaPreferences.enabled"
            :hide-manga-title="mangaPreferences.hideTitle"
            :disable-manga-scale="mangaPreferences.disableScale"
            :manga-image-filter="mangaImageFilter"
            :title-addition="readingPreferences.showReadTitleAddition ? chapterAddition(data.index) : ''"
            :title-clickable="openBookInfoByClickTitle"
            :chinese-converter-type="readingPreferences.chineseConverterType"
            :image-click-way="readingPreferences.clickImgWay"
            :open-links-externally="readingPreferences.readUrlInBrowser"
            @titleClick="bookInfoVisible = true"
            @readedLengthChange="onReadedLengthChange"
            @imageClick="handleImageClick"
            @linkClick="openExternalLink"
            v-if="showContent"
          />
        </div>
        <div class="loading" ref="loading"></div>
        <div class="bottom-bar" ref="bottom"></div>
      </div>
    </div>
    <el-dialog v-model="bookInfoVisible" title="书籍信息" width="min(460px, calc(100vw - 32px))">
      <dl class="book-info-list">
        <div><dt>书名</dt><dd>{{ store.readingBook.name }}</dd></div>
        <div><dt>作者</dt><dd>{{ store.readingBook.author }}</dd></div>
        <div><dt>当前章节</dt><dd>{{ catalog[chapterIndex]?.title || '-' }}</dd></div>
        <div><dt>阅读进度</dt><dd>{{ chapterIndex + 1 }} / {{ catalog.length }}</dd></div>
      </dl>
      <div class="book-info-actions">
        <el-button :loading="sourceCandidatesLoading" @click="openSourceCandidates">更换书源</el-button>
      </div>
    </el-dialog>
    <el-dialog v-model="sourceSwitchVisible" title="更换书源" width="min(620px, calc(100vw - 24px))">
      <div v-if="sourceCandidates.length" class="source-candidate-list">
        <button
          v-for="candidate in sourceCandidates"
          :key="`${candidate.origin}-${candidate.bookUrl}`"
          class="source-candidate"
          type="button"
          :disabled="sourceSwitching"
          @click="switchBookSource(candidate)"
        >
          <strong>{{ candidate.originName }}</strong>
          <span>{{ candidate.name }}<template v-if="candidate.author"> · {{ candidate.author }}</template></span>
          <small>{{ candidate.latestChapterTitle || candidate.intro || candidate.bookUrl }}</small>
        </button>
      </div>
      <el-empty v-else description="没有找到可用的匹配书源" />
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import jump from '@/plugins/jump'
import settings from '@/config/themeConfig'
import API from '@api'
import type { AppDataItem, AppSettings } from '@api'
import type { SeachBook } from '@/book'
import { useLoading } from '@/hooks/loading'
import { useThrottleFn } from '@vueuse/shared'
import { isNullOrBlank } from '@/utils/utils'
import { CopyDocument, Document, Download, FolderOpened, Headset, Search, Sunny, Upload, VideoPause, VideoPlay } from '@element-plus/icons-vue'

const content = ref()
const imagePreviewUrl = ref('')
const bookInfoVisible = ref(false)
const sourceSwitchVisible = ref(false)
const sourceCandidatesLoading = ref(false)
const sourceSwitching = ref(false)
const sourceCandidates = ref<SeachBook[]>([])
const appSettings = ref<AppSettings>({})
const readStyles = ref<AppDataItem[]>([])
// loading spinner
const { isLoading, loadingWrapper } = useLoading(content, '正在获取信息')
const store = useBookStore()

const {
  catalog,
  popCataVisible,
  readSettingsVisible,
  miniInterface,
  showContent,
  bookProgress,
  theme,
  isNight,
} = storeToRefs(store)

const chapterPos = computed({
  get: () => store.readingBook.chapterPos,
  set: value => (store.readingBook.chapterPos = value),
})
const chapterIndex = computed({
  get: () => store.readingBook.chapterIndex,
  set: value => (store.readingBook.chapterIndex = value),
})
const isSeachBook = computed({
  get: () => store.readingBook.isSeachBook,
  set: value => (store.readingBook.isSeachBook = value),
})

// 当前阅读书籍readingBook持久化
watch(
  () => store.readingBook,
  book => {
    if (appSettings.value.main?.enableReadRecord === false) return
    // 保存localStorage
    // localStorage.setItem(book.bookUrl, JSON.stringify(book));
    // 最近阅读
    localStorage.setItem('readingRecent', JSON.stringify(book))
    //保存 sessionStorage
    sessionStorage.setItem('chapterIndex', book.chapterIndex.toString())
    sessionStorage.setItem('chapterPos', book.chapterPos.toString())
  },
  { deep: 1 },
)

// 无限滚动
const infiniteLoading = computed(() => store.config.infiniteLoading)
const readingPreferences = computed(() => {
  const read = appSettings.value.read || {}
  return {
    textFullJustify: read.textFullJustify !== false,
    textBottomJustify: read.textBottomJustify === true,
    adaptSpecialStyle: read.adaptSpecialStyle !== false,
    selectText: read.selectText !== false,
    useZhLayout: read.useZhLayout === true,
    noAnimScrollPage: read.noAnimScrollPage === true,
    mouseWheelPage: read.mouseWheelPage === true,
    volumeKeyPage: read.volumeKeyPage === true,
    volumeKeyPageOnPlay: read.volumeKeyPageOnPlay === true,
    expandTextMenu: read.expandTextMenu === true,
    optimizeRender: read.optimizeRender === true,
    pageTouchSlop: Math.max(0, Number(read.pageTouchSlop ?? 0)),
    doubleHorizontalPage: String(read.doubleHorizontalPage ?? '0'),
    progressBarBehavior: String(read.progressBarBehavior ?? 'page'),
    paddingDisplayCutouts: read.paddingDisplayCutouts === true,
    hideStatusBar: read.hideStatusBar === true,
    hideNavigationBar: read.hideNavigationBar === true,
    keyPageOnLongPress: read.keyPageOnLongPress === true,
    showBrightnessView: read.showBrightnessView !== false,
    brightnessVwPos: read.brightnessVwPos === true,
    readBodyToLh: read.readBodyToLh !== false,
    pageTouchClick: Math.max(0, Number(read.pageTouchClick ?? 0)),
    preDownloadNum: Math.min(100, Math.max(0, Number(read.preDownloadNum ?? 0))),
    autoReadSpeed: Math.min(60, Math.max(1, Number(read.autoReadSpeed ?? 10))),
    clickImgWay: String(read.clickImgWay ?? '0'),
    readUrlInBrowser: read.readUrlInBrowser === true,
    showReadTitleAddition: read.showReadTitleAddition === true,
    readBarStyleFollowPage: read.readBarStyleFollowPage === true,
    disableReturnKey: read.disableReturnKey === true,
    prevKeyCodes: String(read.prevKeyCodes ?? ''),
    nextKeyCodes: String(read.nextKeyCodes ?? ''),
    chineseConverterType: String(read.chineseConverterType ?? '0'),
    clickActions: {
      topLeft: String(read.clickActionTopLeft ?? 2),
      topCenter: String(read.clickActionTopCenter ?? 2),
      topRight: String(read.clickActionTopRight ?? 1),
      middleLeft: String(read.clickActionMiddleLeft ?? 2),
      middleCenter: String(read.clickActionMiddleCenter ?? 0),
      middleRight: String(read.clickActionMiddleRight ?? 1),
      bottomLeft: String(read.clickActionBottomLeft ?? 2),
      bottomCenter: String(read.clickActionBottomCenter ?? 1),
      bottomRight: String(read.clickActionBottomRight ?? 1),
    },
  }
})
const processTextEnabled = computed(
  () => appSettings.value.maintenance?.process_text !== false,
)
const mangaPreferences = computed(() => {
  const manga = appSettings.value.manga || {}
  return {
    enabled: manga.showMangaUi !== false,
    disableScale: manga.disableMangaScale === true,
    hideTitle: manga.hideMangaTitle === true,
    gray: manga.enableMangaGray === true,
    eInk: manga.enableMangaEInk === true,
    eInkThreshold: Math.min(255, Math.max(0, Number(manga.mangaEInkThreshold ?? 150))),
    colorFilter: String(manga.mangaColorFilter ?? '').trim(),
    disablePageAnim: manga.disableMangaPageAnim === true,
    preDownloadNum: Math.min(100, Math.max(0, Number(manga.mangaPreDownloadNum ?? 0))),
    disableClickScroll: manga.disableClickScroll === true,
    autoPageSpeed: Math.min(10, Math.max(1, Number(manga.mangaAutoPageSpeed ?? 3))),
    horizontalScroll: manga.enableMangaHorizontalScroll === true,
    disableHorizontalPageSnap: manga.disableHorizontalPageSnap === true,
  }
})
type MangaFooterConfig = {
  hideChapterLabel: boolean
  hideChapter: boolean
  hidePageNumberLabel: boolean
  hidePageNumber: boolean
  hideProgressRatioLabel: boolean
  hideProgressRatio: boolean
  footerOrientation: number
  hideFooter: boolean
  hideChapterName: boolean
}
const mangaFooterConfig = computed<MangaFooterConfig>(() => {
  const defaults: MangaFooterConfig = {
    hideChapterLabel: false,
    hideChapter: false,
    hidePageNumberLabel: false,
    hidePageNumber: false,
    hideProgressRatioLabel: false,
    hideProgressRatio: false,
    footerOrientation: 0,
    hideFooter: false,
    hideChapterName: false,
  }
  const raw = String(appSettings.value.manga?.mangaFooterConfig ?? '').trim()
  if (!raw) return defaults
  try {
    const value = JSON.parse(raw) as Partial<MangaFooterConfig>
    return { ...defaults, ...value }
  } catch {
    return defaults
  }
})
const isMangaContent = (contents: string[]) =>
  contents.filter(content => /^\s*<img[^>]*src/i.test(String(content))).length >=
  Math.max(1, contents.length / 2)
const currentMangaChapter = computed(() =>
  mangaPreferences.value.enabled && chapterData.value.some(data => isMangaContent(data.content)),
)
const mangaHorizontalEnabled = computed(() =>
  currentMangaChapter.value && mangaPreferences.value.horizontalScroll,
)
const mangaImageFilter = computed(() => {
  if (!mangaPreferences.value.enabled) return 'none'
  const filters: string[] = []
  if (mangaPreferences.value.gray) filters.push('grayscale(1)')
  if (mangaPreferences.value.eInk) {
    const contrast = 1 + mangaPreferences.value.eInkThreshold / 128
    filters.push('grayscale(1)', `contrast(${contrast.toFixed(2)})`)
  }
  const configured = mangaPreferences.value.colorFilter
  if (configured) {
    if (CSS.supports('filter', configured)) {
      filters.push(configured)
    } else {
      try {
        const value = JSON.parse(configured) as Record<string, unknown>
        const mappings: Array<[string, string, string]> = [
          ['brightness', 'brightness', ''],
          ['contrast', 'contrast', ''],
          ['saturate', 'saturate', ''],
          ['sepia', 'sepia', ''],
          ['invert', 'invert', ''],
          ['hueRotate', 'hue-rotate', 'deg'],
        ]
        mappings.forEach(([key, cssName, unit]) => {
          const number = Number(value[key])
          if (Number.isFinite(number)) filters.push(`${cssName}(${number}${unit})`)
        })
      } catch {
        // Invalid filter JSON is ignored so image rendering remains available.
      }
    }
  }
  return filters.length ? filters.join(' ') : 'none'
})
const selectedReadStyle = computed<AppDataItem | undefined>(() => {
  if (readStyles.value.length === 0) return undefined
  const read = appSettings.value.read || {}
  const useComicStyle = currentMangaChapter.value && read.shareLayout !== true
  const index = Math.max(0, Number(useComicStyle ? read.comicStyleSelect : read.readStyleSelect) || 0)
  return readStyles.value[index]
})
const styleNumber = (key: string, fallback: number) => {
  const value = Number(selectedReadStyle.value?.[key])
  return Number.isFinite(value) ? value : fallback
}
const styleColor = (key: string, fallback: string) => {
  const value = String(selectedReadStyle.value?.[key] ?? '').trim()
  return value && CSS.supports('color', value) ? value : fallback
}
const openBookInfoByClickTitle = computed(
  () => appSettings.value.main?.openBookInfoByClickTitle === true,
)
const enableReadRecord = computed(
  () => appSettings.value.main?.enableReadRecord !== false,
)
const syncBookProgress = computed(
  () => appSettings.value.backup?.syncBookProgress !== false,
)
const enhancedProgressSync = computed(
  () => appSettings.value.backup?.syncBookProgressPlus === true,
)
const aloudPreferences = computed(() => {
  const aloud = appSettings.value.aloud || {}
  return {
    selectionMode: String(appSettings.value.read?.contentReadAloudMod ?? '0'),
    byPage: aloud.readAloudByPage === true,
    wakeLock: aloud.readAloudWakeLock === true,
    mediaButtonPerNext: aloud.mediaButtonPerNext === true,
    readAloudByMediaButton: aloud.readAloudByMediaButton === true,
    pauseOnInterruption: aloud.pauseReadAloudWhilePhoneCalls !== false,
    mediaButtonOnExit: aloud.mediaButtonOnExit === true,
    streamAudio: aloud.streamReadAloudAudio !== false,
    followSystemRate: aloud.ttsFollowSys !== false,
    speechRate: Math.min(10, Math.max(1, Number(aloud.ttsSpeechRate ?? 5))),
    timerMinutes: Math.min(240, Math.max(0, Number(aloud.ttsTimer ?? 0))),
    engine: String(aloud.ttsEngine ?? '').trim().toLowerCase(),
  }
})
const speechActive = ref(false)
const selectionSpeech = reactive({ visible: false, text: '', x: 0, y: 0 })
let speechTimer: ReturnType<typeof setTimeout> | null = null
let speechSegments: string[] = []
let speechSegmentIndex = 0
let speechGeneration = 0
let speechWakeLock: WakeLockSentinelLike | null = null
let speechPausedByVisibility = false

async function releaseSpeechWakeLock() {
  if (!speechWakeLock) return
  const sentinel = speechWakeLock
  speechWakeLock = null
  try {
    await sentinel.release()
  } catch {
    // The browser may release the lock when the document becomes hidden.
  }
}

async function acquireSpeechWakeLock() {
  if (!aloudPreferences.value.wakeLock || speechWakeLock || !('wakeLock' in navigator)) return
  try {
    speechWakeLock = await (navigator as WakeLockNavigator).wakeLock?.request('screen') ?? null
  } catch {
    // Wake Lock requires browser support, a secure context and a visible document.
  }
}

function setMediaPlaybackState(state: MediaSessionPlaybackState) {
  if (!('mediaSession' in navigator)) return
  try {
    navigator.mediaSession.playbackState = state
  } catch {
    // Some browsers expose Media Session without writable playback state.
  }
}

function stopSpeechReading() {
  speechGeneration += 1
  window.speechSynthesis?.cancel()
  speechActive.value = false
  speechSegments = []
  speechSegmentIndex = 0
  speechPausedByVisibility = false
  if (speechTimer) clearTimeout(speechTimer)
  speechTimer = null
  setMediaPlaybackState('none')
  void releaseSpeechWakeLock()
}

function speechVoice() {
  const configured = aloudPreferences.value.engine
  if (!configured || !window.speechSynthesis) return undefined
  return window.speechSynthesis.getVoices().find(voice =>
    [voice.name, voice.voiceURI, voice.lang].some(value =>
      value.toLowerCase().includes(configured),
    ),
  )
}

function speakCurrentSegment(generation: number) {
  const normalized = speechSegments[speechSegmentIndex]?.replace(/\s+/g, ' ').trim()
  if (!normalized || generation !== speechGeneration) {
    stopSpeechReading()
    return
  }
  const utterance = new SpeechSynthesisUtterance(normalized)
  utterance.lang = 'zh-CN'
  utterance.rate = aloudPreferences.value.followSystemRate
    ? 1
    : (aloudPreferences.value.speechRate + 5) / 10
  const voice = speechVoice()
  if (voice) utterance.voice = voice
  utterance.onstart = () => {
    if (generation !== speechGeneration) return
    speechActive.value = true
    setMediaPlaybackState('playing')
    document.dispatchEvent(new Event('legado:speech-start'))
    void acquireSpeechWakeLock()
  }
  utterance.onend = () => {
    if (generation !== speechGeneration) return
    speechSegmentIndex += 1
    if (speechSegmentIndex < speechSegments.length) speakCurrentSegment(generation)
    else stopSpeechReading()
  }
  utterance.onerror = () => {
    if (generation === speechGeneration) stopSpeechReading()
  }
  window.speechSynthesis.speak(utterance)
}

function speakSegments(segments: string[], startIndex = 0) {
  let normalized = segments.map(text => text.replace(/\s+/g, ' ').trim()).filter(Boolean)
  if (!aloudPreferences.value.streamAudio && normalized.length > 1) {
    normalized = [normalized.join('\n')]
    startIndex = 0
  }
  if (!normalized.length || !window.speechSynthesis || typeof SpeechSynthesisUtterance === 'undefined') {
    if (normalized.length) ElMessage.warning('当前浏览器不支持系统朗读')
    return
  }
  stopSpeechReading()
  speechSegments = normalized
  speechSegmentIndex = Math.min(normalized.length - 1, Math.max(0, startIndex))
  const generation = speechGeneration
  speakCurrentSegment(generation)
  const minutes = aloudPreferences.value.timerMinutes
  if (minutes > 0) speechTimer = setTimeout(stopSpeechReading, minutes * 60_000)
}

function speakText(text: string) {
  speakSegments([text])
}

function currentSpeechSegments() {
  const root = content.value as HTMLElement | undefined
  const paragraphs = Array.from(root?.querySelectorAll<HTMLElement>('.content p') || [])
  const selected = aloudPreferences.value.byPage
    ? paragraphs.filter(paragraph => {
        const rect = paragraph.getBoundingClientRect()
        return rect.bottom > 0 && rect.top < window.innerHeight
      })
    : paragraphs
  return selected.map(paragraph => paragraph.textContent || '').filter(Boolean)
}

function toggleSpeechReading() {
  if (speechActive.value || window.speechSynthesis?.speaking) stopSpeechReading()
  else speakSegments(currentSpeechSegments())
}

function handleTextSelection(event: MouseEvent) {
  const selection = window.getSelection()
  const text = selection?.toString().trim() || ''
  if (
    !text ||
    !selection?.rangeCount ||
    (aloudPreferences.value.selectionMode === '0' &&
      !readingPreferences.value.expandTextMenu &&
      !processTextEnabled.value)
  ) {
    selectionSpeech.visible = false
    return
  }
  const node = selection.getRangeAt(0).commonAncestorContainer
  const element = node.nodeType === Node.ELEMENT_NODE ? node : node.parentElement
  if (!content.value?.contains(element)) return
  if (aloudPreferences.value.selectionMode === '1') {
    selectionSpeech.visible = false
    speakText(text)
    return
  }
  selectionSpeech.text = text
  const menuWidth =
    (readingPreferences.value.expandTextMenu ? 88 : 0) +
    (processTextEnabled.value ? 44 : 0) +
    44
  selectionSpeech.x = Math.min(window.innerWidth - menuWidth - 8, Math.max(8, event.clientX + 8))
  selectionSpeech.y = Math.min(window.innerHeight - 48, Math.max(8, event.clientY + 8))
  selectionSpeech.visible = true
}

function speakSelectedText() {
  const text = selectionSpeech.text
  selectionSpeech.visible = false
  window.getSelection()?.removeAllRanges()
  speakText(text)
}

async function copySelectedText() {
  const text = selectionSpeech.text
  try {
    await navigator.clipboard.writeText(text)
    ElMessage.success('已复制选中文本')
  } catch {
    ElMessage.warning('浏览器未允许写入剪贴板')
  }
  selectionSpeech.visible = false
  window.getSelection()?.removeAllRanges()
}

function searchSelectedText() {
  const text = selectionSpeech.text
  selectionSpeech.visible = false
  window.getSelection()?.removeAllRanges()
  window.open(`https://www.google.com/search?q=${encodeURIComponent(text)}`, '_blank', 'noopener,noreferrer')
}

function processSelectedText() {
  const text = selectionSpeech.text
  selectionSpeech.visible = false
  window.getSelection()?.removeAllRanges()
  void router.push({
    path: '/features',
    query: {
      kind: 'bookmarks',
      draftText: text,
      bookName: store.readingBook.name || '',
      bookAuthor: store.readingBook.author || '',
      chapterName: catalog.value[store.readingBook.chapterIndex]?.title || '',
      chapterIndex: String(store.readingBook.chapterIndex || 0),
    },
  })
}

async function downloadCurrentBook() {
  try {
    const response = await API.exportBook(store.readingBook.bookUrl)
    if (!response.data.isSuccess) throw new Error(response.data.errorMsg || '导出失败')
    downloadExportResult(response.data.data)
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '导出失败')
  }
}

async function downloadEpisodeArchive() {
  try {
    const response = await API.exportBookEpisodes(store.readingBook.bookUrl)
    if (!response.data.isSuccess) throw new Error(response.data.errorMsg || '分章导出失败')
    downloadExportResult(response.data.data)
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '分章导出失败')
  }
}

async function uploadCurrentBook() {
  try {
    const response = await API.uploadBook(store.readingBook.bookUrl)
    if (!response.data.isSuccess) throw new Error(response.data.errorMsg || '直链上传失败')
    const result = response.data.data
    await navigator.clipboard?.writeText(result.downloadUrl)
    await ElMessageBox.alert(result.downloadUrl, result.summary || '直链上传完成', {
      confirmButtonText: '确定',
    })
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '直链上传失败')
  }
}

function downloadExportResult(result: import('@/api/api').BookExportResult) {
  const binary = atob(result.base64)
  const bytes = Uint8Array.from(binary, character => character.charCodeAt(0))
  const url = URL.createObjectURL(new Blob([bytes], { type: result.mime }))
  const anchor = document.createElement('a')
  anchor.href = url
  anchor.download = result.fileName
  anchor.click()
  URL.revokeObjectURL(url)
  ElMessage.success(`已导出 ${result.fileName}`)
}
const viewportWidth = ref(window.innerWidth)
const readingScrollProgress = ref(0)
const doublePageEnabled = computed(() => {
  const mode = readingPreferences.value.doubleHorizontalPage
  if (viewportWidth.value < 900 || mode === '0') return false
  return mode === 'always' || (mode === 'landscape' && window.innerWidth > window.innerHeight)
})
const readingProgress = computed(() => {
  const behavior = readingPreferences.value.progressBarBehavior
  const chapterCount = Math.max(1, catalog.value.length)
  const chapterPart = Math.min(1, Math.max(0, readingScrollProgress.value / 100))
  if (behavior === 'chapter') return chapterPart * 100
  if (behavior === 'book') return ((chapterIndex.value + chapterPart) / chapterCount) * 100
  return readingScrollProgress.value
})
const mangaPageCount = computed(() => {
  readingScrollProgress.value
  const root = content.value as HTMLElement | undefined
  if (!root) return 1
  return mangaHorizontalEnabled.value
    ? Math.max(1, Math.ceil(root.scrollWidth / Math.max(1, root.clientWidth)))
    : Math.max(1, Math.ceil(document.documentElement.scrollHeight / Math.max(1, window.innerHeight)))
})
const mangaPageNumber = computed(() =>
  Math.min(
    mangaPageCount.value,
    Math.max(1, Math.floor((readingScrollProgress.value / 100) * mangaPageCount.value) + 1),
  ),
)
const mangaFooterText = computed(() => {
  const config = mangaFooterConfig.value
  const parts: string[] = []
  if (!config.hideChapterName) parts.push(catalog.value[chapterIndex.value]?.title || '')
  if (!config.hidePageNumber) {
    parts.push(`${config.hidePageNumberLabel ? '' : '页码 '}${mangaPageNumber.value}/${mangaPageCount.value}`)
  }
  if (!config.hideChapter) {
    parts.push(`${config.hideChapterLabel ? '' : '章节 '}${chapterIndex.value + 1}/${Math.max(1, catalog.value.length)}`)
  }
  if (!config.hideProgressRatio) {
    parts.push(`${config.hideProgressRatioLabel ? '' : '进度 '}${readingProgress.value.toFixed(1)}%`)
  }
  return parts.filter(Boolean).join(' · ')
})
const readerBrightness = computed({
  get: () => {
    const key = isNight.value ? 'nightBrightness' : 'brightness'
    return Math.min(100, Math.max(0, Number(appSettings.value.read?.[key] ?? 100)))
  },
  set: value => {
    const key = isNight.value ? 'nightBrightness' : 'brightness'
    if (!appSettings.value.read) appSettings.value.read = {}
    appSettings.value.read[key] = Math.min(100, Math.max(0, Number(value)))
  },
})
const brightnessShadeOpacity = computed(() =>
  String(((100 - readerBrightness.value) / 100) * 0.82),
)

async function saveBrightnessPreference() {
  try {
    const response = await API.saveAppSettings(appSettings.value)
    if (response.data.isSuccess) appSettings.value = response.data.data
  } catch {
    ElMessage.warning('亮度偏好暂时无法保存')
  }
}
let touchStart: { x: number; y: number } | null = null
let touchMoved = false
let lastPageClickAt = 0

function rememberTouchStart(event: TouchEvent) {
  const touch = event.touches[0]
  if (!touch) return
  touchStart = { x: touch.clientX, y: touch.clientY }
  touchMoved = false
}

function rememberTouchEnd(event: TouchEvent) {
  const touch = event.changedTouches[0]
  if (!touch || !touchStart) return
  const distance = Math.hypot(touch.clientX - touchStart.x, touch.clientY - touchStart.y)
  touchMoved = distance > readingPreferences.value.pageTouchSlop
  touchStart = null
}

function updateReadingProgress() {
  if (mangaHorizontalEnabled.value && content.value) {
    const maxScroll = Math.max(1, content.value.scrollWidth - content.value.clientWidth)
    readingScrollProgress.value = Math.min(100, Math.max(0, (content.value.scrollLeft / maxScroll) * 100))
    return
  }
  const maxScroll = Math.max(1, document.documentElement.scrollHeight - window.innerHeight)
  readingScrollProgress.value = Math.min(100, Math.max(0, (window.scrollY / maxScroll) * 100))
}
const screenOrientation = computed(() => String(appSettings.value.read?.screenOrientation ?? '0'))
const keepLight = computed(() => String(appSettings.value.read?.keep_light ?? '0'))

type WakeLockSentinelLike = { release: () => Promise<void> }
type WakeLockNavigator = Navigator & {
  wakeLock?: { request: (type: 'screen') => Promise<WakeLockSentinelLike> }
}
let wakeLockSentinel: WakeLockSentinelLike | null = null
let wakeLockTimeout: ReturnType<typeof setTimeout> | null = null

async function releaseWakeLock() {
  if (wakeLockTimeout) clearTimeout(wakeLockTimeout)
  wakeLockTimeout = null
  if (!wakeLockSentinel) return
  const sentinel = wakeLockSentinel
  wakeLockSentinel = null
  try {
    await sentinel.release()
  } catch {
    // The browser may already have released the lock while backgrounded.
  }
}

async function applyReadingDevicePreferences() {
  const orientation = screen.orientation as ScreenOrientation & { lock?: (value: string) => Promise<void>; unlock?: () => void }
  if (screenOrientation.value === '0') {
    orientation.unlock?.()
  } else {
    const target = screenOrientation.value === '1' ? 'portrait' : 'landscape'
    try {
      await orientation.lock?.(target)
    } catch {
      // Orientation locking is only available in supported browser contexts.
    }
  }

  await releaseWakeLock()
  const seconds = Number(keepLight.value)
  if (!Number.isFinite(seconds) || seconds <= 0 || !('wakeLock' in navigator)) return
  try {
    wakeLockSentinel = await (navigator as WakeLockNavigator).wakeLock?.request('screen') ?? null
    if (wakeLockSentinel && seconds > 1) {
      wakeLockTimeout = setTimeout(() => void releaseWakeLock(), seconds * 1000)
    }
  } catch {
    // Wake Lock requires a supported, visible document and may be denied by the browser.
  }
}

watch([screenOrientation, keepLight], () => void applyReadingDevicePreferences())

watch(enableReadRecord, enabled => {
  if (!enabled) localStorage.removeItem('readingRecent')
})
let scrollObserver: IntersectionObserver | null
const loading = ref()
watchEffect(() => {
  if (!infiniteLoading.value) {
    scrollObserver?.disconnect()
  } else {
    scrollObserver?.observe(loading.value)
  }
})
const loadMore = () => {
  const index = chapterData.value.slice(-1)[0].index
  if (catalog.value.length - 1 > index) {
    getContent(index + 1, false)
    saveProgressIfEnabled() // 保存的是上一章的进度，不是预载的本章进度
  }
}
// IntersectionObserver回调 底部加载
const onReachBottom = (entries: IntersectionObserverEntry[]) => {
  if (isLoading.value) return
  for (const { isIntersecting } of entries) {
    if (!isIntersecting) return
    loadMore()
  }
}

// 字体
const fontFamily = computed(() => {
  const configuredFont = String(selectedReadStyle.value?.fontPath ?? '').trim()
  if (configuredFont && !/^(https?:|data:|\/)/i.test(configuredFont)) return configuredFont
  const systemTypeface = Math.min(5, Math.max(0, Number(appSettings.value.read?.system_typefaces ?? 0)))
  const systemFonts = [
    '',
    'system-ui, sans-serif',
    'serif',
    'sans-serif',
    'monospace',
    'cursive',
  ]
  if (systemFonts[systemTypeface]) return systemFonts[systemTypeface]
  if (store.config.font >= 0) {
    return settings.fonts[store.config.font]
  }
  return store.config.customFontName
})
const fontSize = computed(() => {
  return `${Math.min(60, Math.max(10, styleNumber('textSize', store.config.fontSize)))}px`
})

// 主题部分
const bodyColor = computed(() => settings.themes[theme.value].body)
const chapterColor = computed(() => settings.themes[theme.value].content)
const popupColor = computed(() => settings.themes[theme.value].popup)

const readWidth = computed(() => {
  if (!miniInterface.value) {
    return store.config.readWidth - 130 + 'px'
  } else {
    return window.innerWidth + 'px'
  }
})
const popupWidth = computed(() => {
  if (!miniInterface.value) {
    return store.config.readWidth - 33
  } else {
    return window.innerWidth - 33
  }
})
const bodyTheme = computed(() => {
  return {
    background: bodyColor.value,
  }
})
const readerPageTheme = computed(() => ({
  ...bodyTheme.value,
  '--reader-safe-top': readingPreferences.value.paddingDisplayCutouts
    ? 'max(48px, env(safe-area-inset-top))'
    : '48px',
  '--reader-safe-inline': readingPreferences.value.paddingDisplayCutouts
    ? 'env(safe-area-inset-left)'
    : '0px',
}))
const chapterTheme = computed(() => {
  const style = selectedReadStyle.value
  return {
    background: style ? styleColor('bgStr', chapterColor.value) : chapterColor.value,
    color: style ? styleColor('textColor', 'inherit') : 'inherit',
    width: readWidth.value,
    paddingLeft: style ? `${Math.min(120, Math.max(0, styleNumber('paddingLeft', 65)))}px` : undefined,
    paddingRight: style ? `${Math.min(120, Math.max(0, styleNumber('paddingRight', 65)))}px` : undefined,
    '--read-line-height': style
      ? String(Math.max(1.2, (styleNumber('textSize', store.config.fontSize) + styleNumber('lineSpacingExtra', 0)) / Math.max(10, styleNumber('textSize', store.config.fontSize))))
      : '1.8',
    '--read-paragraph-spacing': style ? `${Math.max(0, styleNumber('paragraphSpacing', 0))}px` : '0px',
    '--read-font-weight': style?.bold === true ? '700' : '400',
  }
})
const readerBarColor = computed(() =>
  readingPreferences.value.readBarStyleFollowPage ? chapterColor.value : popupColor.value,
)
const showToolBar = ref(false)
const leftBarTheme = computed(() => {
  return {
    background: readerBarColor.value,
    marginLeft: miniInterface.value
      ? 0
      : -(store.config.readWidth / 2 + 68) + 'px',
    display: miniInterface.value && !showToolBar.value ? 'none' : 'block',
  }
})
const rightBarTheme = computed(() => {
  return {
    background: readerBarColor.value,
    marginRight: miniInterface.value
      ? 0
      : -(store.config.readWidth / 2 + 52) + 'px',
    display: miniInterface.value && !showToolBar.value ? 'none' : 'block',
  }
})

/**
 * pc移动端判断 最大阅读宽度修正
 * 阅读宽度最小为640px 加上工具栏 68px 52px 取较大值 为 776px
 */
const onResize = () => {
  viewportWidth.value = window.innerWidth
  store.setMiniInterface(window.innerWidth < 776)
  const width = store.config.readWidth /**包含padding */
  checkPageWidth(width)
}
/** 判断阅读宽度是否超出页面或者低于默认值640 */
const checkPageWidth = (readWidth: number) => {
  if (store.miniInterface) return
  if (readWidth < 640) store.config.readWidth = 640
  if (readWidth + 2 * 68 > window.innerWidth) store.config.readWidth -= 160
}
watch(
  () => store.config.readWidth,
  width => checkPageWidth(width),
)
// 顶部底部跳转
const top = ref()
const bottom = ref()
const toTop = () => {
  jump(top.value)
}
const toBottom = () => {
  jump(bottom.value)
}

// 书架路由切换
const router = useRouter()
const toShelf = () => {
  router.push('/')
}
async function openSourceCandidates() {
  sourceCandidatesLoading.value = true
  sourceCandidates.value = []
  try {
    const response = await API.findBookSourceCandidates(store.readingBook.bookUrl)
    if (!response.data.isSuccess) throw new Error(response.data.errorMsg || '无法查询书源')
    sourceCandidates.value = response.data.data
    sourceSwitchVisible.value = true
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '无法查询书源')
  } finally {
    sourceCandidatesLoading.value = false
  }
}
async function switchBookSource(candidate: SeachBook) {
  try {
    await ElMessageBox.confirm(
      `将《${store.readingBook.name}》切换到「${candidate.originName}」吗？`,
      '更换书源',
      { confirmButtonText: '切换', cancelButtonText: '取消', type: 'warning' },
    )
  } catch {
    return
  }
  sourceSwitching.value = true
  try {
    await store.saveBookProgress()
    const response = await API.changeBookSource(store.readingBook.bookUrl, candidate)
    if (!response.data.isSuccess) throw new Error(response.data.errorMsg || '换源失败')
    const book = response.data.data
    sessionStorage.setItem('bookUrl', book.bookUrl)
    sessionStorage.setItem('bookName', book.name)
    sessionStorage.setItem('bookAuthor', book.author)
    sessionStorage.setItem('chapterIndex', String(book.durChapterIndex || 0))
    sessionStorage.setItem('chapterPos', String(book.durChapterPos || 0))
    sessionStorage.setItem('isSeachBook', 'false')
    ElMessage.success(`已切换到 ${book.originName || candidate.originName}`)
    window.setTimeout(() => window.location.reload(), 300)
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '换源失败')
  } finally {
    sourceSwitching.value = false
  }
}
const chapterAddition = (index: number) => {
  const chapter = catalog.value.find(item => item.index === index)
  return chapter?.tag || ''
}
const openExternalLink = (url: string) => {
  if (!url) return
  window.open(url, '_blank', 'noopener,noreferrer')
}
const handleImageClick = (url: string, action: string) => {
  if (!url || action === '0') return
  if (action === 'browser') {
    openExternalLink(url)
    return
  }
  if (action === 'view') imagePreviewUrl.value = url
}

// 获取章节内容
const chapterData = ref<{ index: number; content: string[]; title: string }[]>(
  [],
)
const contentCache = new Map<number, Promise<string>>()
function fetchChapterContent(position: number) {
  const cached = contentCache.get(position)
  if (cached) return cached
  const chapter = catalog.value[position]
  const request = API.getBookContent(store.readingBook.bookUrl, chapter.index).then(response => {
    if (!response.data.isSuccess) throw new Error(response.data.errorMsg)
    return response.data.data
  })
  contentCache.set(position, request)
  request.catch(() => contentCache.delete(position))
  return request
}

async function prefetchChapters(position: number) {
  const requests: Promise<string>[] = []
  const count = currentMangaChapter.value
    ? Math.max(readingPreferences.value.preDownloadNum, mangaPreferences.value.preDownloadNum)
    : readingPreferences.value.preDownloadNum
  for (let offset = 1; offset <= count; offset += 1) {
    const next = position + offset
    if (!catalog.value[next]) break
    requests.push(fetchChapterContent(next))
  }
  await Promise.allSettled(requests)
}
const noPoint = ref(true)
const getContent = (index: number, reloadChapter = true, chapterPos = 0) => {
  if (reloadChapter) {
    //展示进度条
    store.setShowContent(false)
    //强制滚回顶层
    jump(top.value, { duration: 0 })
    //从目录，按钮切换章节时保存进度 预加载时不保存
    saveReadingBookProgressToBrowser(index, chapterPos)
    chapterData.value = []
  }
  const { title } = catalog.value[index]

  loadingWrapper(
    fetchChapterContent(index).then(
      data => {
          const content = data.split(/\n+/)
          chapterData.value.push({ index, content, title })
          if (reloadChapter) toChapterPos(chapterPos)
        store.setContentLoading(true)
        noPoint.value = false
        store.setShowContent(true)
        void prefetchChapters(index)
      },
      err => {
        const content = ['获取章节内容失败！']
        chapterData.value.push({ index, content, title })
        store.setShowContent(true)
        throw err
      },
    ),
  )
}

// 章节进度跳转和计算
const chapter = ref()
const chapterRef = ref()
const toChapterPos = (pos: number) => {
  nextTick(() => {
    if (chapterRef.value.length === 1)
      chapterRef.value[0].scrollToReadedLength(pos)
  })
}

// 普通同步每 60 秒，增强同步每 5 秒。
const saveProgressIfEnabled = () => {
  if (enableReadRecord.value && syncBookProgress.value) store.saveBookProgress()
}
const saveBookProgressRegular = useThrottleFn(saveProgressIfEnabled, 60000)
const saveBookProgressEnhanced = useThrottleFn(saveProgressIfEnabled, 5000)

const onReadedLengthChange = (index: number, pos: number) => {
  saveReadingBookProgressToBrowser(index, pos)
  if (enhancedProgressSync.value) saveBookProgressEnhanced()
  else saveBookProgressRegular()
}

// 文档标题
watchEffect(() => {
  document.title = catalog.value[chapterIndex.value]?.title || document.title
})

// 阅读记录保存浏览器
const saveReadingBookProgressToBrowser = (index: number, pos: number) => {
  // 保存pinia
  chapterIndex.value = index
  chapterPos.value = pos
}

// 进度同步
// 返回导航变化 同步请求会在获取书架前完成

/**
 * VisibilityChange https://developer.mozilla.org/zh-CN/docs/Web/API/Document/visibilitychange_event
 * 监听关闭页面 切换tab 返回桌面 等操作
 * 注意不用监听点击链接导航变化 不对Safari<14.5兼容处理
 **/
const onVisibilityChange = () => {
  const _bookProgress = bookProgress.value
  if (document.visibilityState == 'hidden' && _bookProgress) {
    saveProgressIfEnabled()
  }
  if (document.visibilityState === 'visible') void applyReadingDevicePreferences()
  if (
    document.visibilityState === 'hidden' &&
    aloudPreferences.value.pauseOnInterruption &&
    window.speechSynthesis?.speaking &&
    !window.speechSynthesis.paused
  ) {
    window.speechSynthesis.pause()
    speechPausedByVisibility = true
    speechActive.value = false
    setMediaPlaybackState('paused')
    void releaseSpeechWakeLock()
  } else if (
    document.visibilityState === 'visible' &&
    speechPausedByVisibility &&
    window.speechSynthesis?.paused
  ) {
    speechPausedByVisibility = false
    window.speechSynthesis.resume()
    speechActive.value = true
    setMediaPlaybackState('playing')
    void acquireSpeechWakeLock()
  }
}
// 定时同步

// 章节切换
const toNextChapter = () => {
  store.setContentLoading(true)
  const index = chapterIndex.value + 1
  if (typeof catalog.value[index] !== 'undefined') {
    ElMessage({
      message: '下一章',
      type: 'info',
    })
    getContent(index)
    saveProgressIfEnabled()
  } else {
    ElMessage({
      message: '本章是最后一章',
      type: 'error',
    })
  }
}
const toPreChapter = () => {
  store.setContentLoading(true)
  const index = chapterIndex.value - 1
  if (typeof catalog.value[index] !== 'undefined') {
    ElMessage({
      message: '上一章',
      type: 'info',
    })
    getContent(index)
    saveProgressIfEnabled()
  } else {
    ElMessage({
      message: '本章是第一章',
      type: 'error',
    })
  }
}

let canJump = true
const pageStep = () => Math.max(120, window.innerHeight - 100)
const scrollTop = () => document.scrollingElement?.scrollTop ?? window.scrollY
const scrollHeight = () =>
  document.scrollingElement?.scrollHeight ?? document.documentElement.scrollHeight
const jumpPage = (direction: 1 | -1) => {
  if (!canJump) return
  if (mangaHorizontalEnabled.value && content.value) {
    content.value.scrollBy({
      left: direction * Math.max(120, content.value.clientWidth - 40),
      behavior: mangaPreferences.value.disablePageAnim ? 'auto' : 'smooth',
    })
    return
  }
  const atTop = scrollTop() <= 0
  const atBottom = scrollTop() + window.innerHeight >= scrollHeight() - 1
  if ((direction < 0 && atTop) || (direction > 0 && atBottom)) {
    ElMessage.warning(direction < 0 ? '已到达页面顶部' : '已到达页面底部')
    return
  }
  canJump = false
  jump(direction * pageStep(), {
    duration: readingPreferences.value.noAnimScrollPage ||
      (currentMangaChapter.value && mangaPreferences.value.disablePageAnim)
      ? 0
      : store.config.jumpDuration,
    callback: () => (canJump = true),
  })
}

function moveSpeechSegment(direction: 1 | -1) {
  const segments = speechSegments.length ? speechSegments : currentSpeechSegments()
  if (!segments.length) return
  const nextIndex = speechSegments.length
    ? Math.min(segments.length - 1, Math.max(0, speechSegmentIndex + direction))
    : direction > 0 ? 0 : segments.length - 1
  speakSegments(segments, nextIndex)
}

const mediaSessionActions: MediaSessionAction[] = [
  'play',
  'pause',
  'stop',
  'previoustrack',
  'nexttrack',
]

function setMediaSessionHandler(action: MediaSessionAction, handler: MediaSessionActionHandler | null) {
  if (!('mediaSession' in navigator)) return
  try {
    navigator.mediaSession.setActionHandler(action, handler)
  } catch {
    // Browsers may support only a subset of Media Session actions.
  }
}

function configureMediaSession() {
  if (!('mediaSession' in navigator)) return
  document.documentElement.dataset.legadoMediaSession = 'active'
  setMediaSessionHandler('play', () => {
    if (window.speechSynthesis?.paused) {
      window.speechSynthesis.resume()
      speechActive.value = true
      setMediaPlaybackState('playing')
      void acquireSpeechWakeLock()
    } else if (!speechActive.value && aloudPreferences.value.readAloudByMediaButton) {
      speakSegments(currentSpeechSegments())
    }
  })
  setMediaSessionHandler('pause', () => {
    window.speechSynthesis?.pause()
    speechActive.value = false
    setMediaPlaybackState('paused')
    void releaseSpeechWakeLock()
  })
  setMediaSessionHandler('stop', stopSpeechReading)
  setMediaSessionHandler('previoustrack', () => {
    if (aloudPreferences.value.mediaButtonPerNext) {
      stopSpeechReading()
      toPreChapter()
    } else moveSpeechSegment(-1)
  })
  setMediaSessionHandler('nexttrack', () => {
    if (aloudPreferences.value.mediaButtonPerNext) {
      stopSpeechReading()
      toNextChapter()
    } else moveSpeechSegment(1)
  })
}

function clearMediaSession() {
  for (const action of mediaSessionActions) setMediaSessionHandler(action, null)
  if ('mediaSession' in navigator) navigator.mediaSession.metadata = null
  delete document.documentElement.dataset.legadoMediaSession
}

function updateMediaMetadata(title?: string) {
  if (!('mediaSession' in navigator) || typeof MediaMetadata === 'undefined') return
  navigator.mediaSession.metadata = new MediaMetadata({
    title: title || catalog.value[chapterIndex.value]?.title || store.readingBook.name,
    artist: store.readingBook.author,
    album: store.readingBook.name,
  })
}

watch(
  () => [aloudPreferences.value.mediaButtonPerNext, aloudPreferences.value.readAloudByMediaButton],
  configureMediaSession,
)
const runPageAction = (action: string) => {
  if (action === '0') showToolBar.value = !showToolBar.value
  else if (action === '1') jumpPage(1)
  else if (action === '2') jumpPage(-1)
}
const isReaderControl = (target: EventTarget | null) =>
  target instanceof Element &&
  Boolean(
    target.closest(
      '.tool-bar, .read-bar, .el-popper, button, a, input, textarea, select',
    ),
  )
const handlePageClick = (event: MouseEvent) => {
  if (touchMoved || isReaderControl(event.target) || window.getSelection()?.toString()) {
    touchMoved = false
    return
  }
  if (currentMangaChapter.value && mangaPreferences.value.disableClickScroll) return
  const now = performance.now()
  const clickInterval = readingPreferences.value.pageTouchClick * 10
  if (clickInterval > 0 && now - lastPageClickAt < clickInterval) return
  lastPageClickAt = now
  if (
    (readingPreferences.value.hideStatusBar || readingPreferences.value.hideNavigationBar) &&
    !document.fullscreenElement
  ) {
    void document.documentElement.requestFullscreen?.().catch(() => undefined)
  }
  const column =
    event.clientX < window.innerWidth / 3
      ? 'Left'
      : event.clientX > (window.innerWidth * 2) / 3
        ? 'Right'
        : 'Center'
  const row =
    event.clientY < window.innerHeight / 3
      ? 'Top'
      : event.clientY > (window.innerHeight * 2) / 3
        ? 'Bottom'
        : 'Middle'
  const key = `${row.charAt(0).toLowerCase()}${row.slice(1)}${column}` as keyof typeof readingPreferences.value.clickActions
  runPageAction(readingPreferences.value.clickActions[key])
}
const autoReading = ref(false)
let autoReadFrame: number | null = null
let autoReadTimestamp = 0
const runAutoRead = (timestamp: number) => {
  if (!autoReading.value) return
  if (autoReadTimestamp > 0) {
    const speed = currentMangaChapter.value
      ? mangaPreferences.value.autoPageSpeed * 10
      : readingPreferences.value.autoReadSpeed
    const distance = speed * ((timestamp - autoReadTimestamp) / 1000)
    if (mangaHorizontalEnabled.value && content.value) content.value.scrollBy({ left: distance, behavior: 'auto' })
    else window.scrollBy({ top: distance, behavior: 'auto' })
  }
  autoReadTimestamp = timestamp
  const atEnd = mangaHorizontalEnabled.value && content.value
    ? content.value.scrollLeft + content.value.clientWidth >= content.value.scrollWidth - 2
    : window.scrollY + window.innerHeight >= document.documentElement.scrollHeight - 2
  if (atEnd) {
    autoReading.value = false
    autoReadFrame = null
    return
  }
  autoReadFrame = requestAnimationFrame(runAutoRead)
}
const toggleAutoReading = () => {
  autoReading.value = !autoReading.value
  autoReadTimestamp = 0
  if (autoReading.value) autoReadFrame = requestAnimationFrame(runAutoRead)
  else if (autoReadFrame !== null) cancelAnimationFrame(autoReadFrame)
}
const handleWheel = (event: WheelEvent) => {
  if (!readingPreferences.value.mouseWheelPage || event.ctrlKey || isReaderControl(event.target)) return
  if (Math.abs(event.deltaY) < 1) return
  event.preventDefault()
  jumpPage(event.deltaY > 0 ? 1 : -1)
}
const keyTokens = (value: string) =>
  new Set(
    value
      .split(/[\s,;|]+/)
      .map(token => token.trim().toLowerCase())
      .filter(Boolean),
  )
const matchesConfiguredKey = (event: KeyboardEvent, value: string) => {
  const tokens = keyTokens(value)
  if (!tokens.size) return false
  return [event.code, event.key, String(event.keyCode), String(event.which)]
    .filter(Boolean)
    .some(candidate => tokens.has(candidate.toLowerCase()))
}
const isEditableTarget = (target: EventTarget | null) =>
  target instanceof HTMLElement &&
  Boolean(target.closest('input, textarea, select, [contenteditable="true"]'))
// 监听方向键和用户配置的翻页键
const longPressHandled = new Set<string>()
const handleKeyPress = (event: KeyboardEvent) => {
  if (!canJump || isEditableTarget(event.target)) return
  if (longPressHandled.delete(event.code)) return
  const preferences = readingPreferences.value
  const allowVolumePaging = preferences.volumeKeyPage && (
    preferences.volumeKeyPageOnPlay ||
    !speechActive.value ||
    window.speechSynthesis?.paused === true
  )
  if (allowVolumePaging && ['AudioVolumeUp', 'VolumeUp'].includes(event.code)) {
    event.preventDefault()
    jumpPage(-1)
    return
  }
  if (allowVolumePaging && ['AudioVolumeDown', 'VolumeDown'].includes(event.code)) {
    event.preventDefault()
    jumpPage(1)
    return
  }
  if (matchesConfiguredKey(event, preferences.prevKeyCodes)) {
    event.stopPropagation()
    event.preventDefault()
    jumpPage(-1)
    return
  }
  if (matchesConfiguredKey(event, preferences.nextKeyCodes)) {
    event.stopPropagation()
    event.preventDefault()
    jumpPage(1)
    return
  }
  switch (event.key) {
    case 'ArrowLeft':
      event.stopPropagation()
      event.preventDefault()
      toPreChapter()
      break
    case 'ArrowRight':
      event.stopPropagation()
      event.preventDefault()
      toNextChapter()
      break
    case 'ArrowUp':
      event.stopPropagation()
      event.preventDefault()
      jumpPage(-1)
      break
    case 'ArrowDown':
      event.stopPropagation()
      event.preventDefault()
      jumpPage(1)
      break
  }
}

// 阻止默认滚动和需要禁用的回车行为
const ignoreKeyPress = (event: KeyboardEvent) => {
  if (isEditableTarget(event.target)) return
  if (readingPreferences.value.keyPageOnLongPress && event.repeat) {
    const preferences = readingPreferences.value
    const allowVolumePaging = preferences.volumeKeyPage && (
      preferences.volumeKeyPageOnPlay ||
      !speechActive.value ||
      window.speechSynthesis?.paused === true
    )
    const previous = event.key === 'ArrowUp' ||
      matchesConfiguredKey(event, preferences.prevKeyCodes) ||
      (allowVolumePaging && ['AudioVolumeUp', 'VolumeUp'].includes(event.code))
    const next = event.key === 'ArrowDown' ||
      matchesConfiguredKey(event, preferences.nextKeyCodes) ||
      (allowVolumePaging && ['AudioVolumeDown', 'VolumeDown'].includes(event.code))
    if (previous || next) {
      event.preventDefault()
      longPressHandled.add(event.code)
      jumpPage(previous ? -1 : 1)
      return
    }
  }
  if (
    event.key === 'ArrowUp' ||
    event.key === 'ArrowDown' ||
    (event.key === 'Enter' && readingPreferences.value.disableReturnKey) ||
    matchesConfiguredKey(event, readingPreferences.value.prevKeyCodes) ||
    matchesConfiguredKey(event, readingPreferences.value.nextKeyCodes)
  ) {
    event.preventDefault()
    event.stopPropagation()
  }
}
async function loadAppPreferences() {
  try {
    const [response, stylesResponse] = await Promise.all([
      API.getAppSettings(),
      API.getAppData('readStyles'),
    ])
    if (response.data.isSuccess) appSettings.value = response.data.data
    if (stylesResponse.data.isSuccess) readStyles.value = stylesResponse.data.data
  } catch {
    // Reading remains functional with its existing local configuration.
  }
}

onMounted(async () => {
  await Promise.all([store.loadWebConfig(), loadAppPreferences()])
  await applyReadingDevicePreferences()
  configureMediaSession()
  //获取书籍数据
  const bookUrl = sessionStorage.getItem('bookUrl')
  const name = sessionStorage.getItem('bookName')
  const author = sessionStorage.getItem('bookAuthor')
  const chapterIndex = Number(sessionStorage.getItem('chapterIndex') || 0)
  const chapterPos = Number(sessionStorage.getItem('chapterPos') || 0)
  const isSeachBook = sessionStorage.getItem('isSeachBook') === 'true'
  if (isNullOrBlank(bookUrl) || isNullOrBlank(name) || author === null) {
    ElMessage.warning('书籍信息为空，即将自动返回书架页面...')
    return setTimeout(toShelf, 500)
  }
  const book: typeof store.readingBook = {
    // @ts-expect-error: bookUrl name author is NON_Blank string here
    bookUrl,
    // @ts-expect-error: bookUrl name author is NON_Blank string here
    name,
    author,
    chapterIndex,
    chapterPos,
    isSeachBook,
  }
  onResize()
      window.addEventListener('resize', onResize)
      window.addEventListener('scroll', updateReadingProgress, { passive: true })
      content.value?.addEventListener('scroll', updateReadingProgress, { passive: true })
      updateReadingProgress()
  loadingWrapper(
    store.loadWebCatalog(book).then(chapters => {
      store.setReadingBook(book)
      getContent(chapterIndex, true, chapterPos)
      window.addEventListener('keyup', handleKeyPress)
      window.addEventListener('keydown', ignoreKeyPress)
      // 兼容Safari < 14
      document.addEventListener('visibilitychange', onVisibilityChange)
      //监听底部加载
      scrollObserver = new IntersectionObserver(onReachBottom, {
        rootMargin: '-100% 0% 20% 0%',
      })
      if (infiniteLoading.value === true) scrollObserver.observe(loading.value)
      //第二次点击同一本书 页面标题不会变化
      document.title = '...'
      document.title = (name as string) + ' | ' + chapters[chapterIndex].title
      updateMediaMetadata(chapters[chapterIndex].title)
    }),
  )
})

onUnmounted(() => {
  autoReading.value = false
  if (autoReadFrame !== null) cancelAnimationFrame(autoReadFrame)
  autoReadFrame = null
  contentCache.clear()
  void releaseWakeLock()
  if (!aloudPreferences.value.mediaButtonOnExit) {
    stopSpeechReading()
    clearMediaSession()
  }
  if (document.fullscreenElement) void document.exitFullscreen?.()
  ;(screen.orientation as ScreenOrientation & { unlock?: () => void }).unlock?.()
  window.removeEventListener('keyup', handleKeyPress)
  window.removeEventListener('keydown', ignoreKeyPress)
  window.removeEventListener('resize', onResize)
  window.removeEventListener('scroll', updateReadingProgress)
  content.value?.removeEventListener('scroll', updateReadingProgress)
  // 兼容Safari < 14
  document.removeEventListener('visibilitychange', onVisibilityChange)
  readSettingsVisible.value = false
  popCataVisible.value = false
  scrollObserver?.disconnect()
  scrollObserver = null
})

const addToBookShelfConfirm = async () => {
  const book = store.readingBook
  // 阅读的是搜索的书籍 并未在书架
  if (book.isSeachBook === true) {
    if (appSettings.value.maintenance?.showAddToShelfAlert === false) {
      isSeachBook.value = false
      sessionStorage.removeItem('isSeachBook')
      return
    }
    await ElMessageBox.confirm(`是否将《${book.name}》放入书架？`, '放入书架', {
      confirmButtonText: '确认',
      cancelButtonText: '否',
      type: 'info',
      /*
        ElMessageBox.confirm默认在触发hashChange事件时自动关闭
        按下物理返回键时触发hashChange事件
        使用router.push("/")则不会触发hashChange事件
        */
      closeOnHashChange: false,
    })
      .then(() => {
        //选择是，无动作
        isSeachBook.value = false
      })
      .catch(async () => {
        //选择否，删除书籍
        await API.deleteBook(book)
      })
      .finally(() => sessionStorage.removeItem('isSeachBook'))
  }
}
onBeforeRouteLeave(async (to, from, next) => {
  console.log('onBeforeRouteLeave')
  // 弹窗时停止响应按键翻页
  window.removeEventListener('keyup', handleKeyPress)
  await addToBookShelfConfirm()
  next()
})
</script>

<style lang="scss" scoped>
.chapter-progress {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 110;
  width: 100%;
  height: 3px;
  background: rgba(15, 23, 42, 0.08);
  pointer-events: none;

  span {
    display: block;
    height: 100%;
    background: var(--legado-primary, #0f766e);
    transition: width 120ms linear;
  }
}

.brightness-shade {
  position: fixed;
  inset: 0;
  z-index: 90;
  background: #000;
  pointer-events: none;
}

.brightness-control {
  position: fixed;
  left: 14px;
  bottom: 82px;
  z-index: 120;
  display: flex;
  align-items: center;
  gap: 8px;
  min-height: 38px;
  padding: 7px 10px;
  border: 1px solid rgba(148, 163, 184, 0.52);
  border-radius: 7px;
  background: rgba(255, 255, 255, 0.94);
  color: #334155;
  box-shadow: 0 8px 20px rgba(15, 23, 42, 0.14);

  &.brightness-right {
    right: 14px;
    left: auto;
  }

  svg {
    width: 17px;
    height: 17px;
    color: #b45309;
  }

  input {
    width: 120px;
    accent-color: var(--legado-primary, #0f766e);
  }

  span {
    min-width: 34px;
    font-size: 12px;
    font-variant-numeric: tabular-nums;
  }
}
.selection-speech-button {
  position: fixed;
  z-index: 150;
  display: inline-flex;
  align-items: center;
  gap: 6px;
  min-height: 36px;
  padding: 7px 10px;
  border: 1px solid rgba(15, 118, 110, 0.35);
  border-radius: 7px;
  background: rgba(255, 255, 255, 0.98);
  color: #0f766e;
  box-shadow: 0 8px 24px rgba(15, 23, 42, 0.18);

  svg {
    width: 16px;
    height: 16px;
  }
}

.selection-action-menu {
  gap: 2px;
  padding: 4px;

  button {
    display: inline-grid;
    place-items: center;
    width: 36px;
    height: 36px;
    padding: 0;
    border: 0;
    border-radius: 5px;
    background: transparent;
    color: inherit;
    cursor: pointer;
  }

  button:hover,
  button:focus-visible {
    background: rgba(15, 118, 110, 0.1);
    outline: none;
  }
}

.manga-footer {
  position: fixed;
  right: max(12px, env(safe-area-inset-right));
  bottom: max(10px, env(safe-area-inset-bottom));
  left: max(12px, env(safe-area-inset-left));
  z-index: 82;
  padding: 7px 10px;
  color: rgba(51, 65, 85, 0.88);
  font-size: 12px;
  line-height: 1.4;
  text-align: left;
  pointer-events: none;
}

.manga-footer-center {
  text-align: center;
}

.book-info-list {
  margin: 0;

  > div {
    display: grid;
    grid-template-columns: 88px minmax(0, 1fr);
    gap: 14px;
    padding: 10px 0;
    border-bottom: 1px solid #e5e7eb;
  }

  > div:last-child {
    border-bottom: 0;
  }

  dt {
    color: #64748b;
    font-size: 13px;
  }

  dd {
    min-width: 0;
    margin: 0;
    color: #1e293b;
    overflow-wrap: anywhere;
  }
}

.book-info-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 16px;
}

.source-candidate-list {
  display: grid;
  gap: 8px;
  max-height: min(58vh, 520px);
  overflow: auto;
}

.source-candidate {
  display: grid;
  gap: 4px;
  width: 100%;
  padding: 12px;
  border: 1px solid #d7dee8;
  border-radius: 6px;
  background: #fff;
  color: #1e293b;
  cursor: pointer;
  text-align: left;

  strong {
    color: #0f766e;
    font-size: 14px;
  }

  span,
  small {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  small {
    color: #64748b;
  }

  &:hover:not(:disabled),
  &:focus-visible {
    border-color: #0f766e;
    background: #f0fdfa;
    outline: none;
  }

  &:disabled {
    cursor: wait;
    opacity: 0.6;
  }
}

:deep(.pop-setting) {
  margin-left: 68px;
  top: 0;
}

:deep(.pop-cata) {
  margin-left: 10px;
}

.chapter-wrapper {
  padding: 0 4%;

  overflow-x: hidden;

  :deep(.no-point) {
    pointer-events: none;
  }

  .tool-bar {
    position: fixed;
    top: 0;
    left: 50%;
    z-index: 100;

    .tools {
      display: flex;
      flex-direction: column;

      .tool-icon {
        font-size: 18px;
        width: 58px;
        height: 48px;
        text-align: center;
        padding-top: 12px;
        cursor: pointer;
        outline: none;

        .iconfont {
          font-family: iconfont;
          width: 16px;
          height: 16px;
          font-size: 16px;
          margin: 0 auto 6px;
        }

        .icon-text {
          font-size: 12px;
        }
      }
    }
  }

  .read-bar {
    position: fixed;
    bottom: 0;
    right: 50%;
    z-index: 100;

    .tools {
      display: flex;
      flex-direction: column;

      .tool-icon {
        font-size: 18px;
        width: 42px;
        height: 31px;
        padding-top: 12px;
        text-align: center;
        align-items: center;
        cursor: pointer;
        outline: none;
        margin-top: -1px;

        .iconfont {
          font-family: iconfont;
          width: 16px;
          height: 16px;
          font-size: 16px;
          margin: 0 auto 6px;
        }
      }
    }
  }

  .chapter {
    font-family: 'Microsoft YaHei', PingFangSC-Regular, HelveticaNeue-Light,
      'Helvetica Neue Light', sans-serif;
    text-align: left;
    padding: 0 65px;
    min-height: 100vh;
    width: 670px;
    margin: 0 auto;

    .content {
      font-size: 18px;
      line-height: var(--read-line-height, 1.8);
      font-family: 'Microsoft YaHei', PingFangSC-Regular, HelveticaNeue-Light,
        'Helvetica Neue Light', sans-serif;

      :deep(p) {
        margin-bottom: var(--read-paragraph-spacing, 0);
        font-weight: var(--read-font-weight, 400);
      }

      .optimized-chapter {
        content-visibility: auto;
        contain-intrinsic-size: auto 1000px;
      }

      .bottom-bar,
      .top-bar {
        height: 64px;
      }
    }
  }

  .chapter.two-page .content {
    column-count: 2;
    column-gap: 56px;
  }

  .chapter.manga-horizontal {
    overflow-x: auto;
    overflow-y: hidden;
    scroll-snap-type: x mandatory;

    &.manga-snap-disabled {
      scroll-snap-type: none;
    }

    .content > div[chapterIndex] {
      display: flex;
      align-items: center;
      width: max-content;
      min-width: 100%;

      > :deep(div) {
        flex: 0 0 100%;
        scroll-snap-align: start;
      }
    }
  }

  .auto-read-button {
    border: 0;
    background: transparent;

    svg {
      display: block;
      width: 18px;
      height: 18px;
      margin: 0 auto 5px;
    }
  }
}

.day {
  :deep(.popup) {
    box-shadow:
      0 2px 4px rgba(0, 0, 0, 0.12),
      0 0 6px rgba(0, 0, 0, 0.04);
  }

  :deep(.tool-icon) {
    border: 1px solid rgba(0, 0, 0, 0.1);
    margin-top: -1px;
    color: #000;

    .icon-text {
      color: rgba(0, 0, 0, 0.4);
    }
  }

  :deep(.chapter) {
    border: 1px solid #d8d8d8;
    color: #262626;
  }
}

.night {
  :deep(.popup) {
    box-shadow:
      0 2px 4px rgba(0, 0, 0, 0.48),
      0 0 6px rgba(0, 0, 0, 0.16);
  }

  :deep(.tool-icon) {
    border: 1px solid #444;
    margin-top: -1px;
    color: #666;

    .icon-text {
      color: #666;
    }
  }

  :deep(.chapter) {
    border: 1px solid #444;
    color: #666;
  }

  :deep(.popper__arrow) {
    background: #666;
  }
}

@media screen and (max-width: 776px) {
  .chapter-wrapper {
    padding: var(--reader-safe-top) var(--reader-safe-inline) 0;

    .tool-bar {
      top: 48px;
      left: 0;
      width: 100vw;
      margin-left: 0 !important;

      .tools {
        flex-direction: row;
        justify-content: space-between;

        .tool-icon {
          border: none;
        }
      }
    }

    .chapter-progress {
      top: 48px;
    }

    .brightness-control {
      bottom: 54px;

      input {
        width: min(32vw, 120px);
      }
    }

    .read-bar {
      right: 0;
      width: 100vw;
      margin-right: 0 !important;

      .tools {
        flex-direction: row;
        justify-content: space-between;
        padding: 0 15px;

        .tool-icon {
          border: none;
          width: auto;

          .iconfont {
            display: inline-block;
          }
        }
      }
    }

    .chapter {
      width: 100vw !important;
      padding: 0 20px;
      box-sizing: border-box;
    }
  }
}
</style>
