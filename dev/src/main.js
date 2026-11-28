// 应用入口
import Vue from 'vue'
// Date.prototype.format 扩展（.vue 里大量使用，原版依赖）
import '@/utils/date-format'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

// ── 原版全局样式（从产物 web-dist/css/ 提取，含 @font-face 与图标字体）──
// 顺序：vendor（element-ui 覆盖）→ 各 chunk 的 scoped 样式
import '@/assets/css/chunk-vendors.css'
import '@/assets/css/app.css'
import '@/assets/css/index.css'
import '@/assets/css/reader.css'
import '@/assets/css/setting.css'

// Sortable：产物里 BookGroup 拖动排序用模块 aa47（Sortable 1.15.6）
// 对话框组件的降级桩检测 window.Sortable，存在时自动接管
import Sortable from 'sortablejs'
window.Sortable = Sortable

import App from './App.vue'
import router from './router'
import store from './store'
import globalMixin from './mixins/global'
import rootMethods from './mixins/root-methods'

Vue.use(ElementUI, { size: 'mini' })
Vue.config.productionTip = false

// 全局混入：api / isWebApp / isPWA / getImagePath / dateFormat / store 映射等
Vue.mixin(globalMixin)
// 数据加载方法（子组件用 this.$root.$children[0].loadBookShelf() 之类调用）
Vue.mixin(rootMethods)

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app')
