// 占位组件：VirtList（虚拟列表）
//
// 说明：原产物里 VirtList 来自 webpack 模块 `index-reader/febf.js`（32 KB），
// 该模块**尚未被恢复成 .vue 源文件**（34 个 .vue 里没有它）。
//
// 这里先提供功能等价的简化实现，保证工程可编译、页面可用。
// 待后续从 febf.js 恢复成真正的 VirtList.vue 后替换。
//
// 原 VirtList 的能力：给定 items、每项高度，只渲染可视区内的项，支撑大列表滚动。
export default {
  name: 'VirtList',
  props: {
    items: { type: Array, default: () => [] },
    itemHeight: { type: Number, default: 60 },
    buffer: { type: Number, default: 6 },
  },
  data() {
    return { scrollTop: 0, viewportH: 600 }
  },
  computed: {
    totalH() {
      return this.items.length * this.itemHeight
    },
    startIndex() {
      return Math.max(0, Math.floor(this.scrollTop / this.itemHeight) - this.buffer)
    },
    endIndex() {
      return Math.min(this.items.length, Math.ceil((this.scrollTop + this.viewportH) / this.itemHeight) + this.buffer)
    },
    visible() {
      return this.items.slice(this.startIndex, this.endIndex)
    },
    offsetY() {
      return this.startIndex * this.itemHeight
    },
  },
  mounted() {
    this.measure()
    window.addEventListener('resize', this.measure)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.measure)
  },
  methods: {
    measure() {
      if (this.$el && this.$el.clientHeight) this.viewportH = this.$el.clientHeight
    },
    onScroll(e) {
      this.scrollTop = e.target.scrollTop
    },
  },
  render(h) {
    return h(
      'div',
      { class: 'virt-list', style: { height: '100%', overflowY: 'auto' }, on: { scroll: this.onScroll } },
      [
        h('div', { style: { height: this.totalH + 'px', position: 'relative' } }, [
          h(
            'div',
            { style: { transform: `translateY(${this.offsetY}px)` } },
            this.visible.map((item, i) =>
              h('div', { key: this.startIndex + i, style: { height: this.itemHeight + 'px' } }, [
                this.$scopedSlots.default
                  ? this.$scopedSlots.default({ item, index: this.startIndex + i })
                  : String(item && (item.name || item.title || '')),
              ])
            )
          ),
        ]),
      ]
    )
  },
}
