import { createWebHashHistory, createRouter } from 'vue-router'
import { bookRoutes } from './bookRouter'
import { sourceRoutes } from './sourceRouter'

const router = createRouter({
  //   history: createWebHistory(process.env.BASE_URL),
  history: createWebHashHistory(),
  routes: [
    bookRoutes,
    sourceRoutes,
    {
      path: '/server',
      name: 'server-console',
      component: () => import('../views/ServerConsole.vue'),
    },
    {
      path: '/features',
      name: 'feature-workbench',
      component: () => import('../views/FeatureWorkbench.vue'),
    },
    {
      path: '/settings',
      name: 'settings-center',
      component: () => import('../views/SettingsCenter.vue'),
    },
  ].flat(),
})

router.afterEach(to => {
  if (to.name == 'server-console') document.title = '服务端控制台'
  if (to.name == 'feature-workbench') document.title = 'App 功能工作台'
  if (to.name == 'settings-center') document.title = '设置中心'
  if (to.name == 'shelf') document.title = '书架'
})

export default router
