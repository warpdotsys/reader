// Vuex store —— 从 webpack 模块 1a58 手工重写为可维护源码
//
// 原始产物是单文件压缩 + 模块引用（Object(f["b"])("k")、Object(s["a"])({}, A) 之类），
// 这里按语义还原：
//   Object(f["b"])(k)          -> getStorage(k)
//   Object(f["c"])(k, v)       -> setStorage(k, v)
//   Object(f["a"])(k)          -> removeStorage(k)
//   Object(s["a"])({}, A, B)   -> { ...A, ...B }   （浅拷贝合并）
//
// state / getters / mutations 的字段与产物逐一对齐，未增删业务字段。
import Vue from 'vue'
import Vuex from 'vuex'
import { getStorage, setStorage, removeStorage } from '@/utils/storage'
import defaults from '@/config/defaults'

Vue.use(Vuex)

// 默认用户（产物里是 [{username:"默认", userNS:"default"}]）
const DEFAULT_USER = { username: '默认', userNS: 'default' }

export const state = {
  connected: false,
  api:
    getStorage('api_prefix') ||
    location.host + location.pathname.replace('index.html', '').replace(/\/$/, '') + '/reader3',
  shelfBooks: [],
  readingBook: {},
  config: { ...defaults.config },
  miniInterface: false,
  windowSize: { width: window.innerWidth, height: window.innerHeight },
  touchable: 'ontouchstart' in document,
  showLogin: false,
  loginAuth: true,
  token: getStorage('api_token') || '',
  bookSourceList: [],
  isSecureMode: false,
  isManagerMode: false,
  secureKey: '',
  userInfo: {},
  userList: [DEFAULT_USER],
  userNS: 'default',
  showManagerMode: false,
  version: 'v3.2.14-05250935',
  filterRules: [],
  speechVoiceConfig: { ...defaults.speechVoiceConfig },
  safeArea: { top: 0, bottom: 0, left: 0, right: 0 },
  autoPlay: false,
  failureIncludeTimeout: false,
  failureBookSource: [],
  bookGroupList: [],
  rssSourceList: [],
  shelfConfig: { ...defaults.shelfConfig },
  searchConfig: { ...defaults.searchConfig },
  // 原版内置两套配置（产物 bfff: [M, N]）；阅读设置弹窗会读 configDefaultType
  customConfigList: [
    { ...defaults.config, name: '内置白天', customConfig: '内置白天', configDefaultType: 0 },
    { ...defaults.config, name: '内置黑夜', customConfig: '内置黑夜', configDefaultType: 1, theme: defaults.defaultNightTheme },
  ],
  currentUserName: '',
  windowSizeType: '',
  previewImgList: [],
  txtTocRules: [],
  httpTTS: [],
  rssArticle: {},
  showBookInfo: false,
  notAutoSync: false,
  showContent: false,
  // 由 app 对话框组件使用（BookManage / Bookmark）
  cachingBookList: [],
  bookmarks: [],
}

export const getters = {
  api: (s) => s.api,
  apiRoot: (s) => s.api.replace(/\/reader3$/, ''),
  isSlideRead: (s) => s.shelfConfig.viewCate === 'slide',
  isSystemNight: () => {
    try {
      return window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches
    } catch (e) {
      return false
    }
  },
  isNight: (s) => s.config.theme === defaults.defaultNightTheme,
  isKindlePage: () => /kindle/i.test(navigator.userAgent),
  isNormalPage: (s) => s.config.pageType === '正常',
  currentContentBGImg: (s) => (defaults.themes[s.config.theme] || {}).content || '',
  customCSSUrl: (s) => s.config.customCSSUrl || '',
  currentFontFamily: (s) => (defaults.fonts[s.config.font] || {}).font || '',
  currentCustomFontFamily: (s) => s.config.customFontFamily || '',
  currentThemeConfig: (s) => defaults.themes[s.config.theme] || {},
  body: (s) => (defaults.themes[s.config.theme] || {}).body || '',
  popup: (s) => (defaults.themes[s.config.theme] || {}).popup || '',
  shelfBooks: (s) => s.shelfBooks,
  bookSourceGroupList: (s) => {
    const set = new Set(s.bookSourceList.map((b) => b.bookSourceGroup).filter(Boolean))
    return [...set]
  },
  builtInBookGroupMap: (s) => {
    const m = {}
    s.bookGroupList.forEach((g) => { m[g.groupId] = g.groupName })
    return m
  },
  config: (s) => s.config,
  shelfConfig: (s) => s.shelfConfig,
  searchConfig: (s) => s.searchConfig,
  // 原版定义（产物 1a58）：非安全模式视为已登录；安全模式看 userInfo.username
  hasLogin: (s) => !s.isSecureMode || (s.isSecureMode && !!s.userInfo.username),
  readingBook: (s) => ({
    ...s.readingBook,
    ...(s.shelfBooks.find((b) => b.bookUrl === s.readingBook.bookUrl) || {}),
  }),
  isManagerMode: (s) => s.isManagerMode,
  userInfo: (s) => s.userInfo,
  bookGroupList: (s) => s.bookGroupList,
  rssSourceList: (s) => s.rssSourceList,
  dialogTop: (s) => (s.windowSize.height > 800 ? '15vh' : '8vh'),
  dialogSmallWidth: (s) => s.windowSize.width < 800,
  previewImgList: (s) => s.previewImgList,
}

export const mutations = {
  setCachingBookList(state, list) {
    state.cachingBookList = [].concat(list || [])
  },
  setBookmarks(state, list) {
    state.bookmarks = list || []
  },
  setShelfBooks(state, payload) {
    window.shelfBooks = payload
    state.shelfBooks = (payload || []).map((b) => ({ ...b }))
  },
  updateShelfBook(state, book) {
    const i = state.shelfBooks.findIndex((b) => b.bookUrl === book.bookUrl)
    if (i >= 0) {
      state.shelfBooks[i] = {
        ...state.shelfBooks[i],
        author: book.author || state.shelfBooks[i].author,
        bookUrl: book.bookUrl || state.shelfBooks[i].bookUrl,
        coverUrl: book.coverUrl || state.shelfBooks[i].coverUrl,
        tocUrl: book.tocUrl || state.shelfBooks[i].tocUrl,
        ...book,
      }
    }
  },
  setReadingBook(state, payload) {
    state.readingBook = payload || {}
  },
  durChapterTime(state, payload) {
    if (state.readingBook) state.readingBook.durChapterTime = payload
  },
  clearReadingBook(state) {
    state.readingBook = {}
    removeStorage(state.currentUserName + '@readingRecent')
  },
  setConfig(state, payload) {
    state.config = payload
    setStorage('config', payload)
  },
  setMiniInterface(state, payload) {
    state.miniInterface = payload
  },
  setWindowSize(state, payload) {
    state.windowSize = payload
  },
  setTouchable(state, payload) {
    state.touchable = payload
  },
  setApi(state, payload) {
    state.api = payload
    setStorage('api_prefix', payload)
  },
  setConnected(state, payload) {
    state.connected = payload
  },
  setShowLogin(state, payload) {
    state.showLogin = payload
  },
  setLoginAuth(state, payload) {
    state.loginAuth = payload
  },
  setToken(state, payload) {
    state.token = payload
    setStorage('api_token', payload)
  },
  setBookSourceList(state, payload) {
    state.bookSourceList = payload || []
  },
  setUserNS(state, payload) {
    state.userNS = payload
  },
  setIsSecureMode(state, payload) {
    state.isSecureMode = payload
  },
  setSecureKey(state, payload) {
    state.secureKey = payload
  },
  setIsManagerMode(state, payload) {
    state.isManagerMode = payload
  },
  setShowManagerMode(state, payload) {
    state.showManagerMode = payload
  },
  setUserInfo(state, payload) {
    state.userInfo = payload
  },
  setUserList(state, payload) {
    state.userList = payload && payload.length ? [...payload] : [DEFAULT_USER]
  },
  setFilterRules(state, payload) {
    state.filterRules = payload
    setStorage('filterRules', payload)
  },
  setSpeechVoiceConfig(state, payload) {
    state.speechVoiceConfig = payload
    setStorage('speechVoiceConfig', payload)
  },
  setShelfConfig(state, payload) {
    state.shelfConfig = payload
    setStorage('shelfConfig', payload)
  },
  setSearchConfig(state, payload) {
    state.searchConfig = payload
    setStorage('searchConfig', payload)
  },
  setSafeArea(state, payload) {
    state.safeArea = payload
  },
  setAutoPlay(state, payload) {
    state.autoPlay = payload
  },
  setBookGroupList(state, payload) {
    state.bookGroupList = payload || []
  },
  setRssSourceList(state, payload) {
    state.rssSourceList = payload || []
  },
  setPreviewImgList(state, payload) {
    state.previewImgList = payload || []
  },
  setShowBookInfo(state, payload) {
    state.showBookInfo = payload
  },
  setNotAutoSync(state, payload) {
    state.notAutoSync = payload
  },
  setShowContent(state, payload) {
    state.showContent = payload
  },
  setCurrentUserName(state, payload) {
    state.currentUserName = payload
  },
  setCustomConfigList(state, payload) {
    state.customConfigList = payload || []
  },
  setTxtTocRules(state, payload) {
    state.txtTocRules = payload || []
  },
  setHttpTTS(state, payload) {
    state.httpTTS = payload || []
  },
  setRssArticle(state, payload) {
    state.rssArticle = payload || {}
  },
  setFailureBookSource(state, payload) {
    state.failureBookSource = payload || []
  },
}

export const actions = {
  // 从后端拉取并同步全部用户数据（对应产物里的 syncFromLocalStorage）
  async syncFromLocalStorage({ commit, state }) {
    if (!state.api) return
    try {
      const r = await fetch(state.api + '/getUserConfig?accessToken=' + encodeURIComponent(state.token))
      const j = await r.json()
      if (j && j.isSuccess && j.data) commit('setConfig', j.data)
    } catch (e) {
      console.warn('syncFromLocalStorage 失败', e)
    }
  },
}

export default new Vuex.Store({ state, getters, mutations, actions })
