// 路由
import Vue from 'vue'
import Router from 'vue-router'
import Placeholder from '../Placeholder.vue'

Vue.use(Router)

// 真实页面（subagent 从产物完整重建）
const IndexPage = () => import('@/views/index/index-page.vue')
const Setting = () => import('@/views/setting/44d3706e/44d3706e.vue')
// reader-page.vue 是从产物 Nt（主页面）完整重建的，含全部方法并已注册 5 个子组件
const ReaderPage = () => import('@/views/reader/reader-page.vue')

// 探测组件：按 ?c=名字 动态渲染
const Probe = {
  name: 'Probe',
  data() {
    return { comp: null, err: '', props: {} }
  },
  watch: {
    '$route.query.c': {
      immediate: true,
      handler(name) {
        this.comp = null
        this.err = ''
        if (!name) { this.err = '未指定 ?c='; return }
        const map = {
          app_12878b5b: () => import('@/views/app/12878b5b/12878b5b.vue'),
          app_1a462b56: () => import('@/views/app/1a462b56/1a462b56.vue'),
          app_289916ad: () => import('@/views/app/289916ad/289916ad.vue'),
          app_2fdb5f62: () => import('@/views/app/2fdb5f62/2fdb5f62.vue'),
          app_3672581d: () => import('@/views/app/3672581d/3672581d.vue'),
          app_3ee772ca: () => import('@/views/app/3ee772ca/3ee772ca.vue'),
          app_44eeac3f: () => import('@/views/app/44eeac3f/44eeac3f.vue'),
          app_52ffc46f: () => import('@/views/app/52ffc46f/52ffc46f.vue'),
          app_53b78df8: () => import('@/views/app/53b78df8/53b78df8.vue'),
          app_6574bc14: () => import('@/views/app/6574bc14/6574bc14.vue'),
          app_6e24e744: () => import('@/views/app/6e24e744/6e24e744.vue'),
          app_75500020: () => import('@/views/app/75500020/75500020.vue'),
          app_7e126aa8: () => import('@/views/app/7e126aa8/7e126aa8.vue'),
          app_b4d6c7ce: () => import('@/views/app/b4d6c7ce/b4d6c7ce.vue'),
          app_e4af2df0: () => import('@/views/app/e4af2df0/e4af2df0.vue'),
          app_eecda6a0: () => import('@/views/app/eecda6a0/eecda6a0.vue'),
          app_ef9d6efc: () => import('@/views/app/ef9d6efc/ef9d6efc.vue'),
          app_f3156612: () => import('@/views/app/f3156612/f3156612.vue'),
          index_b817d39e: () => import('@/views/index/b817d39e/b817d39e.vue'),
          index_2fbafdaa: () => import('@/views/index/2fbafdaa/2fbafdaa.vue'),
          index_e5965c98: () => import('@/views/index/e5965c98/e5965c98.vue'),
          index_70ce2ba2: () => import('@/views/index/70ce2ba2/70ce2ba2.vue'),
          index_77d83090: () => import('@/views/index/77d83090/77d83090.vue'),
          reader_9c1aae92: () => import('@/views/reader/9c1aae92/9c1aae92.vue'),
          reader_062ccc78: () => import('@/views/reader/062ccc78/062ccc78.vue'),
          reader_59408c4f: () => import('@/views/reader/59408c4f/59408c4f.vue'),
          reader_401db35b: () => import('@/views/reader/401db35b/401db35b.vue'),
          reader_abde66a6: () => import('@/views/reader/abde66a6/abde66a6.vue'),
          reader_4f5b9f48: () => import('@/views/reader/4f5b9f48/4f5b9f48.vue'),
          reader_59408c4f_pop: () => import('@/views/reader/59408c4f/59408c4f.vue'),
          reader_401db35b_pop: () => import('@/views/reader/401db35b/401db35b.vue'),
          reader_4f5b9f48_pop: () => import('@/views/reader/4f5b9f48/4f5b9f48.vue'),
          reader_510c7aa8_pop: () => import('@/views/reader/510c7aa8/510c7aa8.vue'),
          reader_abde66a6_pop: () => import('@/views/reader/abde66a6/abde66a6.vue'),
          reader_510c7aa8: () => import('@/views/reader/510c7aa8/510c7aa8.vue'),
          app_08713662: () => import('@/views/app/08713662/08713662.vue'),
          app_99ff4e1a: () => import('@/views/app/99ff4e1a/99ff4e1a.vue'),
          app_33306aac: () => import('@/views/app/33306aac/33306aac.vue'),
        }
        const f = map[name]
        if (!f) { this.err = '未知组件 ' + name; return }
        this.comp = f
      },
    },
  },
  render(h) {
    if (this.err) return h('div', { style: 'padding:20px;color:#c00' }, this.err)
    if (!this.comp) return h('div', { style: 'padding:20px' }, '加载中…')
    let props = {}
    try {
      const raw = this.$route.query.p
      if (raw) props = JSON.parse(decodeURIComponent(raw))
    } catch (e) {}
    return h(this.comp, { props })
  },
}

export default new Router({
  base: '',
  routes: [
    { path: '/', name: 'index', component: IndexPage },
    { path: '/setting', name: 'Setting', component: Setting },
    { path: '/reader', name: 'Reader', component: ReaderPage, props: { content: '', title: '' } },
    { path: '/probe', name: 'Probe', component: Probe },
  ],
})
