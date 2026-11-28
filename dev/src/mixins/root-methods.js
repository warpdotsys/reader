// 根组件方法（原版 App/index-page 上的一组数据加载方法）
//
// 子组件通过 `this.$root.$children[0].xxx()` 调用它们，所以必须挂在
// **会被 mixin 注入的组件实例上**。这里放进 global mixin，任何实例都能响应。
//
// 原产物里这些方法是 babel 编译后的 generator 状态机，这里用原生 async/await
// 写等价实现，功能一致：调后端 reader3 接口 → commit 到 store。
//
// 未登录时后端返回 { isSuccess:false, data:"NEED_LOGIN" }，按原版行为提示并不抛错。
import axios from 'axios'
import { cachedFetch } from '@/utils/common'

const TIMEOUT = 300000

export default {
  methods: {

    /** 规范化 API 地址：补齐协议，避免出现 `host/host/path` 这种重复拼接 */
    __normApi(api) {
      let a = api || this.$store.getters.api || ''
      if (!a) return ''
      if (/^https?:\/\//.test(a)) return a
      // 只有 host[:port]/path，补当前页面的协议
      return window.location.protocol + '//' + a.replace(/^\/+/, '')
    },

    /** 统一的 GET 封装：带上 accessToken，处理 NEED_LOGIN */
    __readerGet(path, params = {}, opts = {}) {
      const api = this.__normApi(opts.api)
      const token = this.$store.state.token || ''
      const qs = Object.entries({ ...params, accessToken: token })
        .map(([k, v]) => `${encodeURIComponent(k)}=${encodeURIComponent(v == null ? '' : v)}`)
        .join('&')
      return axios.get(`${api}${path}${qs ? '?' + qs : ''}`, { timeout: opts.timeout || TIMEOUT })
    },

    /** 统一的 POST 封装（后端多数写接口是 POST + JSON body） */
    __readerPost(path, body = {}, opts = {}) {
      const api = this.__normApi(opts.api)
      const token = this.$store.state.token || ''
      return axios.post(
        `${api}${path}?accessToken=${encodeURIComponent(token)}`,
        { ...body, accessToken: token },
        { timeout: opts.timeout || TIMEOUT }
      )
    },

    /** 判断后端是否要求登录 */
    __isNeedLogin(res) {
      return res && res.data && (res.data.isSuccess === false) && res.data.data === 'NEED_LOGIN'
    },

    /** 初始化：拉配置 + 判定连接状态 */
    async init() {
      try {
        await this.loadBookShelf(false)
      } catch (e) {
        // init 失败不阻断页面
      }
    },

    /** 拉书架（原版 loadBookShelf(refresh, api)） */
    async loadBookShelf(refresh, api) {
      const booksApi = api || this.$store.getters.api
      try {
        const res = await cachedFetch(
          () => this.__readerGet('/getBookshelf', { refresh: refresh ? 1 : 0 }, { api: booksApi }),
          'getBookshelf@' + this.currentUserName
        )
        if (this.__isNeedLogin(res)) {
          this.$store.commit('setConnected', true)
          this.$store.commit('setLoginAuth', false)
          return
        }
        this.$store.commit('setConnected', !(res.data && res.data.isCache))
        if (res.data && res.data.isSuccess && Array.isArray(res.data.data)) {
          this.$store.commit('setShelfBooks', res.data.data)
        }
      } catch (e) {
        this.$store.commit('setConnected', false)
        if (this.$message) this.$message.error('后端连接失败 ' + (e && e.toString ? e.toString() : e))
        throw e
      }
    },

    /** 拉书源 */
    async loadBookSource() {
      try {
        const res = await this.__readerGet('/getBookSources')
        if (res.data && res.data.isSuccess) {
          this.$store.commit('setBookSourceList', res.data.data || [])
        }
      } catch (e) {
        console.warn('loadBookSource 失败', e)
      }
    },

    /** 拉书籍分组 */
    async loadBookGroup() {
      try {
        const res = await this.__readerGet('/getBookGroups')
        if (res.data && res.data.isSuccess) {
          this.$store.commit('setBookGroupList', res.data.data || [])
        }
      } catch (e) {
        console.warn('loadBookGroup 失败', e)
      }
    },

    /** 拉替换规则 */
    async loadReplaceRules() {
      try {
        const res = await this.__readerGet('/getReplaceRules')
        if (res.data && res.data.isSuccess) {
          this.$store.commit('setFilterRules', res.data.data || [])
        }
      } catch (e) {
        console.warn('loadReplaceRules 失败', e)
      }
    },

    /** 拉 HttpTTS 配置 */
    async loadHttpTTS() {
      try {
        const res = await this.__readerGet('/getHttpTTS')
        if (res.data && res.data.isSuccess) {
          this.$store.commit('setHttpTTS', res.data.data || {})
        }
      } catch (e) {
        console.warn('loadHttpTTS 失败', e)
      }
    },

    /** 拉书签 */
    async loadBookmarks() {
      try {
        const res = await this.__readerGet('/getBookmarks')
        if (res.data && res.data.isSuccess) {
          this.$store.commit('setBookmarks', res.data.data || [])
        }
      } catch (e) {
        console.warn('loadBookmarks 失败', e)
      }
    },

    /** 拉 RSS 订阅源 */
    async loadRssSources() {
      try {
        const res = await this.__readerGet('/getRssSources')
        if (res.data && res.data.isSuccess) {
          this.$store.commit('setRssSourceList', res.data.data || [])
        }
      } catch (e) {
        console.warn('loadRssSources 失败', e)
      }
    },

    /** 保存用户配置到后端（原版 saveUserConfig） */
    async saveUserConfig() {
      if (!window.localStorage) return
      try {
        const cfg = this.$store.state.config
        const res = await this.__readerPost('/saveUserConfig', {
          config: JSON.stringify(cfg),
          userNS: this.$store.state.userNS,
        })
        if (res.data && res.data.isSuccess) {
          if (this.$message) this.$message.success('配置已保存')
        }
      } catch (e) {
        if (this.$message) this.$message.error('保存配置失败 ' + (e && e.toString ? e.toString() : e))
      }
    },

    /** 从后端恢复配置（原版 restoreUserConfig） */
    async restoreUserConfig() {
      try {
        const res = this.__readerGet
          ? await this.__readerGet('/getUserConfig')
          : null
        if (res && res.data && res.data.isSuccess && res.data.data) {
          const d = res.data.data
          if (typeof d.config === 'string') {
            try { this.$store.commit('setConfig', JSON.parse(d.config)) } catch (e) {}
          }
          if (this.$message) this.$message.success('配置已同步')
        }
      } catch (e) {
        if (this.$message) this.$message.error('同步配置失败 ' + (e && e.toString ? e.toString() : e))
      }
    },
  },
}
