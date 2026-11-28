// 全局 mixin（还原自 app chunk 主模块 56d7 的 Vue.mixin）
//
// 原产物里这段是全局混入，所有组件实例都能直接访问：
//   computed : api / isWebApp / isPWA / isNightTheme / currentUserName
//   methods  : getImagePath / getCover / getImage / currentDateTime / dateFormat / renderForm / nearBottom
//
// 这是整个前端的地基，改这里务必跑全量闸门。
import Vue from 'vue'

// 占位封面：原产物从图片模块取 base64，这里给一个内联的灰色方块，
// 后续可从原产物 lt/Ln 模块取真实值替换。
const DEFAULT_COVER =
  'data:image/svg+xml;charset=utf-8,' +
  encodeURIComponent(
    '<svg xmlns="http://www.w3.org/2000/svg" width="120" height="160">' +
      '<rect width="120" height="160" fill="#e8e8e8"/>' +
      '<text x="60" y="84" font-size="13" fill="#aaa" text-anchor="middle">无封面</text>' +
      '</svg>'
  )

export default {
  computed: {
    api() {
      return this.$store.getters.api
    },
    isWebApp() {
      return window.navigator.standalone
    },
    isPWA() {
      return ['fullscreen', 'standalone', 'minimal-ui'].some((m) =>
        window.matchMedia('(display-mode: ' + m + ')').matches
      )
    },
    isNightTheme() {
      return this.$store.getters.isNight
    },
    currentUserName() {
      return this.$store.getters.currentUserName
    },

    // ── store 映射（产物里各组件直接用 this.xxx，等价于 mapState/mapGetters）──
    shelfConfig() {
      return this.$store.state.shelfConfig
    },
    searchConfig() {
      return this.$store.state.searchConfig
    },
    config() {
      return this.$store.state.config
    },
    userInfo() {
      return this.$store.state.userInfo
    },
    userList() {
      return this.$store.state.userList
    },
    userNS() {
      return this.$store.state.userNS
    },
    isNight() {
      return this.$store.getters.isNight
    },
    isNormalPage() {
      return this.$store.getters.isNormalPage
    },
    isManagerMode() {
      return this.$store.state.isManagerMode
    },
    miniInterface() {
      return this.$store.state.miniInterface
    },
    hasLogin() {
      return this.$store.getters.hasLogin
    },
    apiRoot() {
      return this.$store.getters.apiRoot
    },
    shelfBooks() {
      return this.$store.state.shelfBooks
    },
    readingBook() {
      return this.$store.getters.readingBook
    },
    bookSourceList() {
      return this.$store.state.bookSourceList
    },
    bookGroupList() {
      return this.$store.state.bookGroupList
    },
    rssSourceList() {
      return this.$store.state.rssSourceList
    },
    popupTheme() {
      return this.$store.getters.popup
    },
    bodyTheme() {
      return this.$store.getters.body
    },
    dialogTop() {
      return this.$store.getters.dialogTop
    },
    dialogSmallWidth() {
      return this.$store.getters.dialogSmallWidth
    },

    // ── 以下 6 个 store 里没有，按产物语义补默认值（原版从窗口尺寸/UI 状态派生）──
    collapseMenu() {
      return this.$store.state.collapseMenu || false
    },
    dialogWidth() {
      return this.$store.getters.dialogSmallWidth ? '90%' : '640px'
    },
    dialogContentHeight() {
      const h = this.$store.state.windowSize.height || window.innerHeight
      return Math.max(h - 160, 200)
    },
    popupWidth() {
      const w = this.$store.state.windowSize.width || window.innerWidth
      return w < 720 ? w - 32 : 420
    },
  },

  methods: {
    /** 把后端返回的相对路径拼成可访问 URL；网络图走代理 */
    getImagePath(path, noProxy) {
      if (!path) return path
      const isRemote =
        path.startsWith('http://') || path.startsWith('https://') || path.startsWith('//')
      if (isRemote) {
        const useProxy =
          (noProxy && window.serviceWorkerReady) ||
          this.$store.state.shelfConfig.imageProxy === 'noProxy'
        return useProxy ? path : this.api + '/cover?path=' + path
      }
      return this.$store.getters.apiRoot + path
    },

    /** 取封面 URL；未指定 raw 时返回 el-image 需要的对象 */
    getCover(path, raw, noProxy) {
      const p = this.getImagePath(path, noProxy)
      if (!p) return DEFAULT_COVER
      return raw ? p : { src: p, error: DEFAULT_COVER }
    },

    /** 取图片；与 getCover 同形，默认图不同 */
    getImage(path, raw, noProxy) {
      const p = this.getImagePath(path, noProxy)
      if (!p) return DEFAULT_COVER
      return raw ? p : { src: p, error: DEFAULT_COVER }
    },

    /** 当前时间戳字符串：YYYYMMDD_HHmmss */
    currentDateTime() {
      const d = new Date()
      const pad = (n) => (n < 10 ? '0' + n : n)
      return (
        d.getFullYear() +
        pad(d.getMonth() + 1) +
        pad(d.getDate()) +
        '_' +
        pad(d.getHours()) +
        pad(d.getMinutes()) +
        pad(d.getSeconds())
      )
    },

    /** 相对时间：刚刚 / N秒前 / N分钟前 / N小时前 / N天前 / N月前 / N年前 */
    dateFormat(ts) {
      const diff = parseInt((Date.now() - ts) / 1000)
      if (diff <= 30) return '刚刚'
      if (diff < 60) return diff + '秒前'
      if (diff < 3600) return parseInt(diff / 60) + '分钟前'
      if (diff < 86400) return parseInt(diff / 3600) + '小时前'
      if (diff < 2592000) return parseInt(diff / 86400) + '天前'
      if (diff < 31536000) return parseInt(diff / 2592000) + '月前'
      return parseInt(diff / 31536000) + '年前'
    },

    /** 动态创建一个表单向导组件并打开，用于运行期生成简易输入框 */
    renderForm(name, initial, fields, onChange) {
      Vue.component(name, {
        render(h) {
          return h('div', { style: { textAlign: 'center' } }, [
            fields.map((f) => {
              if (f.type === 'input') {
                return h('div', { class: 'form-item' }, [
                  h('span', { style: { display: 'inline-block', width: '85px' } }, [f.label + '：']),
                  h('el-input', {
                    attrs: { size: 'mini' },
                    style: { width: '172px' },
                    model: {
                      value: this.info[f.name],
                      callback: (v) => this.$set(this.info, f.name, v),
                    },
                  }),
                ])
              }
              if (f.type === 'select') {
                return h('div', { class: 'form-item' }, [
                  h('span', { style: { display: 'inline-block', width: '85px' } }, [f.label + '：']),
                  h(
                    'el-select',
                    {
                      attrs: {
                        size: 'mini',
                        filterable: true,
                        multiple: typeof f.multiple === 'undefined' || f.multiple,
                        placeholder: f.placeholder || '',
                      },
                      model: {
                        value: this.info[f.name],
                        callback: (v) => this.$set(this.info, f.name, v),
                      },
                    },
                    (f.options || []).map((o, i) =>
                      h('el-option', { key: f.name + '-op-' + i, attrs: { label: o.label, value: o.value } })
                    )
                  ),
                ])
              }
              return null
            }),
          ])
        },
        data() {
          return { info: initial, items: fields }
        },
        watch: {
          info: {
            handler(v) {
              onChange(v)
            },
            deep: true,
          },
        },
      })
      const Ctor = Vue.component(name)
      return this.$createElement(Ctor)
    },

    /** 是否已滚动到距底部 gap 像素以内 */
    nearBottom(gap) {
      const doc = document.documentElement
      return (
        doc.clientHeight + (doc.scrollTop || document.body.scrollTop) >=
        doc.scrollHeight - gap
      )
    },
  },
}
