<template>
  <div id="app-root">
    <div class="dev-banner">
      <strong>reader-vue 工程骨架</strong>
      <span>路由: {{ $route.path }}</span>
      <span>mini: {{ $store.state.miniInterface ? '是' : '否' }}</span>
    </div>
    <router-view />

    <!-- 全局弹出层：22 个业务对话框，由 eventBus 驱动 -->
    <BookConfig v-model="dlg.showBookConfigDialog" />
    <BookInfo v-model="dlg.showBookInfoDialog" :book="ctx.book" />
    <BookGroup v-model="dlg.showBookGroupDialog" />
    <BookCover v-model="dlg.showBookCoverDialog" :book="ctx.book" />
    <BookManage v-model="dlg.showBookManageDialog" />
    <Bookmark v-model="dlg.showBookmarkDialog" />
    <BookmarkForm v-model="dlg.showBookmarkForm" :book="ctx.book" />
    <UserManage v-model="dlg.showUserManageDialog" />
    <UserForm v-model="dlg.showUserFormDialog" :user="ctx.user" />
    <ReplaceRule v-model="dlg.showReplaceRuleDialog" />
    <ReplaceRuleForm v-model="dlg.showReplaceRuleForm" :rule="ctx.rule" />
    <MPCode v-model="dlg.showMPCodeDialog" />
    <License v-model="dlg.showLicenseDialog" />
    <ActiveLicense v-model="dlg.showActiveLicenseDialog" />
    <RssSourceList v-model="dlg.showRssSourceListDialog" />
    <RssArticleList v-model="dlg.showRssArticleListDialog" :rssSource="ctx.rssSource" />
    <RssArticle v-model="dlg.showRssArticleDialog" :rssArticleInfo="ctx.rssArticleInfo" />
    <SearchBookContent v-model="dlg.showSearchBookContentDialog" :book="ctx.book" />
    <RemoteBookSourceSub v-model="dlg.showRemoteBookSourceSubDialog" />
    <HttpTTS v-model="dlg.showHttpTTSDialog" />
    <FileManager v-model="dlg.showFileManagerDialog" :home="''" title="文件管理" />
  </div>
</template>

<script>
import { isMiniInterface } from '@/utils/common'
import eventBus from '@/utils/event-bus'

// 22 个业务对话框
import BookConfig from '@/views/app/08713662/08713662.vue'
import BookInfo from '@/views/app/1a462b56/1a462b56.vue'
import BookGroup from '@/views/app/289916ad/289916ad.vue'
import BookCover from '@/views/app/2fdb5f62/2fdb5f62.vue'
import BookManage from '@/views/app/99ff4e1a/99ff4e1a.vue'
import Bookmark from '@/views/app/6574bc14/6574bc14.vue'
import BookmarkForm from '@/views/app/b4d6c7ce/b4d6c7ce.vue'
import UserManage from '@/views/app/33306aac/33306aac.vue'
import UserForm from '@/views/app/7e126aa8/7e126aa8.vue'
import ReplaceRule from '@/views/app/44eeac3f/44eeac3f.vue'
import ReplaceRuleForm from '@/views/app/f3156612/f3156612.vue'
import MPCode from '@/views/app/52ffc46f/52ffc46f.vue'
import License from '@/views/app/3ee772ca/3ee772ca.vue'
import ActiveLicense from '@/views/app/53b78df8/53b78df8.vue'
import RssSourceList from '@/views/app/ef9d6efc/ef9d6efc.vue'
import RssArticleList from '@/views/app/3672581d/3672581d.vue'
import RssArticle from '@/views/app/12878b5b/12878b5b.vue'
import SearchBookContent from '@/views/app/6e24e744/6e24e744.vue'
import RemoteBookSourceSub from '@/views/app/e4af2df0/e4af2df0.vue'
import HttpTTS from '@/views/app/75500020/75500020.vue'
import FileManager from '@/views/app/eecda6a0/eecda6a0.vue'

// eventBus 事件名 → dialog 开关字段
const EVENT_MAP = {
  showBookConfigDialog: 'showBookConfigDialog',
  showBookInfoDialog: 'showBookInfoDialog',
  showBookGroupDialog: 'showBookGroupDialog',
  showBookCoverDialog: 'showBookCoverDialog',
  showBookManageDialog: 'showBookManageDialog',
  showBookmarkDialog: 'showBookmarkDialog',
  showBookmarkForm: 'showBookmarkForm',
  showUserManageDialog: 'showUserManageDialog',
  showUserFormDialog: 'showUserFormDialog',
  showReplaceRuleDialog: 'showReplaceRuleDialog',
  showReplaceRuleForm: 'showReplaceRuleForm',
  showMPCodeDialog: 'showMPCodeDialog',
  showLicenseDialog: 'showLicenseDialog',
  showActiveLicenseDialog: 'showActiveLicenseDialog',
  showRssSourceListDialog: 'showRssSourceListDialog',
  showRssArticleListDialog: 'showRssArticleListDialog',
  showRssArticleDialog: 'showRssArticleDialog',
  showSearchBookContentDialog: 'showSearchBookContentDialog',
  showRemoteBookSourceSubDialog: 'showRemoteBookSourceSubDialog',
  showHttpTTSDialog: 'showHttpTTSDialog',
  showFileManagerDialog: 'showFileManagerDialog',
}

export default {
  name: 'App',
  components: {
    BookConfig, BookInfo, BookGroup, BookCover, BookManage, Bookmark, BookmarkForm,
    UserManage, UserForm, ReplaceRule, ReplaceRuleForm, MPCode, License, ActiveLicense,
    RssSourceList, RssArticleList, RssArticle, SearchBookContent, RemoteBookSourceSub,
    HttpTTS, FileManager,
  },
  data() {
    return {
      // 每个 dialog 的显示开关（key 与 EVENT_MAP 的值一致）
      dlg: Object.fromEntries(Object.values(EVENT_MAP).map((k) => [k, false])),
      // 传给 dialog 的上下文对象（原版通过参数传递，这里先给空壳）
      ctx: {
        book: {},
        user: {},
        rule: {},
        rssSource: {},
        rssArticleInfo: {},
      },
    }
  },
  beforeCreate() {
    const store = this.$store
    if (!store) return

    // 1) 小屏/移动端判定
    store.commit('setMiniInterface', isMiniInterface())

    // 2) 视口高度写入 CSS 变量（移动端 100vh 问题的标准解）
    document.documentElement.style.setProperty('--vh', 0.01 * window.innerHeight + 'px')

    // 3) 窗口尺寸变化时同步
    this._onResize = () => {
      document.documentElement.style.setProperty('--vh', 0.01 * window.innerHeight + 'px')
      store.commit('setMiniInterface', isMiniInterface())
      store.commit('setWindowSize', {
        width: window.innerWidth,
        height: window.innerHeight,
      })
      store.commit('setTouchable', 'ontouchstart' in document)
    }
    window.addEventListener('resize', this._onResize)

    // 4) 初始窗口信息
    store.commit('setWindowSize', {
      width: window.innerWidth,
      height: window.innerHeight,
    })
    store.commit('setTouchable', 'ontouchstart' in document)

    // 5) URL 上的 ?api= 覆盖后端地址
    try {
      const api = window.getQueryString && window.getQueryString('api')
      if (api) store.commit('setApi', api)
    } catch (e) {}
  },
  async mounted() {
    // 启动时拉后端信息：判断是否安全模式（决定 hasLogin，进而决定侧栏是否显示）
    try {
      const api = this.$store.getters.api
      const url = /^https?:\/\//.test(api) ? api : window.location.protocol + '//' + api.replace(/^\/+/, '')
      const res = await fetch(url + '/getUserInfo')
      const j = await res.json()
      if (j && j.isSuccess && j.data) {
        this.$store.commit('setIsSecureMode', !!j.data.secure)
        if (j.data.secureKey !== undefined) this.$store.commit('setLoginAuth', !j.data.secure)
      }
    } catch (e) {
      console.warn('getUserInfo 失败', e)
    }

    // 拉首屏数据（书架/分组/书源等），与原版 App 的 init 行为一致
    try {
      if (typeof this.init === 'function') await this.init()
      if (typeof this.loadBookGroup === 'function') this.loadBookGroup()
      if (typeof this.loadBookSource === 'function') this.loadBookSource()
    } catch (e) {
      console.warn('init 失败', e)
    }

    // 6) 挂 eventBus 监听：把事件转成对应 dialog 的显示
    this._handlers = {}
    for (const evt of Object.keys(EVENT_MAP)) {
      const key = EVENT_MAP[evt]
      const h = (payload) => {
        // 事件可以带上下文（book/user/rule 等）
        if (payload && typeof payload === 'object') {
          if (payload.book) this.ctx.book = payload.book
          if (payload.user) this.ctx.user = payload.user
          if (payload.rule) this.ctx.rule = payload.rule
          if (payload.rssSource) this.ctx.rssSource = payload.rssSource
          if (payload.rssArticleInfo) this.ctx.rssArticleInfo = payload.rssArticleInfo
        }
        this.dlg[key] = true
      }
      this._handlers[evt] = h
      eventBus.$on(evt, h)
    }
  },
  beforeDestroy() {
    if (this._onResize) window.removeEventListener('resize', this._onResize)
    if (this._handlers) {
      for (const [evt, h] of Object.entries(this._handlers)) eventBus.$off(evt, h)
    }
  },
}
</script>

<style>
* { box-sizing: border-box; }
body { margin: 0; font-family: -apple-system, "PingFang SC", "Microsoft YaHei", sans-serif; }
.dev-banner {
  display: flex; gap: 16px; align-items: center;
  padding: 4px 12px; background: #f0f7ff; border-bottom: 1px solid #d0e3ff;
  font-size: 12px; color: #3060c0; height: 24px; overflow: hidden;
}
</style>
