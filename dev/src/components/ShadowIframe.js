// ShadowIframe 组件（从 webpack 模块 968f 内联组件提取）
//
// 原产物里它是 reader chunk 内部的一个具名组件（`name: "ShadowIframe"`），
// 不在单独的 .vue 目录里，所以单独提取成模块供其他组件 import。
//
// 用途：用 Shadow DOM 隔离第三方网页内容（阅读器里的网页模式）。
export default {
  name: 'ShadowIframe',
  props: {
    src: { type: String, default: '' },
    renderType: { type: String, default: 'iframe' },
    html: { type: String, default: '' },
    fontSize: { type: [Number, String], default: 18 },
    fontFamily: { type: String, default: '' },
    bgColor: { type: String, default: '#ffffff' },
    textColor: { type: String, default: '#000000' },
    lineHeight: { type: [Number, String], default: 1.8 },
    padding: { type: [Number, String], default: 16 },
    scrollTop: { type: Number, default: 0 },
  },
  data() {
    return {
      loading: true,
      initing: false,
      nowSrc: '',
    }
  },
  computed: {
    contentWindow() {
      return this.$refs.iframe && this.$refs.iframe.contentWindow
    },
    renderIframe() {
      return this.renderType === 'iframe'
    },
    completeSrc() {
      if (!this.nowSrc) return ''
      if (this.nowSrc.startsWith('//')) return window.location.protocol + this.nowSrc
      return this.nowSrc
    },
    chineseFont() {
      return this.fontFamily || '-apple-system, "PingFang SC", "Microsoft YaHei", sans-serif'
    },
  },
  watch: {
    src: {
      immediate: true,
      handler(v) {
        this.nowSrc = v || ''
      },
    },
    html() {
      this.renderContent()
    },
  },
  mounted() {
    this.renderContent()
  },
  methods: {
    /** 把 html 注入 Shadow DOM，样式与正文保持一致 */
    renderContent() {
      if (this.renderIframe) return
      const host = this.$refs.host
      if (!host) return
      this.initing = true
      try {
        let root = host.shadowRoot
        if (!root) root = host.attachShadow({ mode: 'open' })
        root.innerHTML = `
          <style>
            :host { display:block; }
            body, div { margin:0; padding:${this.padding}px; }
            body {
              font-family:${this.chineseFont};
              font-size:${this.fontSize}px;
              line-height:${this.lineHeight};
              color:${this.textColor};
              background:${this.bgColor};
              word-break:break-word;
            }
            img { max-width:100%; height:auto; }
            a { color:inherit; }
          </style>
          <body>${this.html || ''}</body>
        `
      } catch (e) {
        console.warn('ShadowIframe 渲染失败', e)
      }
      this.initing = false
      this.loading = false
    },
    onLoad() {
      this.loading = false
    },
    /** 滚动到指定位置（供父组件调用） */
    scrollTo(top) {
      if (this.contentWindow) this.contentWindow.scrollTo(0, top)
      else if (this.$refs.host) this.$refs.host.scrollTop = top
    },
  },
  render(h) {
    if (this.renderIframe) {
      return h('iframe', {
        ref: 'iframe',
        attrs: { src: this.completeSrc, frameborder: '0', sandbox: 'allow-scripts allow-same-origin' },
        style: { width: '100%', height: '100%', border: 'none' },
        on: { load: this.onLoad },
      })
    }
    return h('div', { ref: 'host', class: 'shadow-iframe-host' })
  },
}
