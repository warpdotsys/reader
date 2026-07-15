import { createApp } from 'vue'
import App from './App.vue'
import router from '@/router'
import store from '@/store'
import API from '@api'
import { setLocalServerToken } from '@/api/axios'
import { applyAppSettings } from '@/settings/runtime'
import { ElNotification } from 'element-plus'
import '@/assets/runtime-theme.css'
import 'element-plus/theme-chalk/dark/css-vars.css'

createApp(App).use(store).use(router).mount('#app')

const hasDefaultEntry = () => location.hash === '' || location.hash === '#/'
const resumeRecentReading = async () => {
  const recent = localStorage.getItem('readingRecent')
  if (!recent) return false
  try {
    const reading = JSON.parse(recent) as {
      bookUrl?: string
      name?: string
      author?: string
      chapterIndex?: number
      chapterPos?: number
      isSeachBook?: boolean
    }
    if (!reading.bookUrl || !reading.name || reading.author == null) return false
    sessionStorage.setItem('bookUrl', reading.bookUrl)
    sessionStorage.setItem('bookName', reading.name)
    sessionStorage.setItem('bookAuthor', reading.author)
    sessionStorage.setItem('chapterIndex', String(reading.chapterIndex || 0))
    sessionStorage.setItem('chapterPos', String(reading.chapterPos || 0))
    sessionStorage.setItem('isSeachBook', String(reading.isSeachBook === true))
    await router.replace('/chapter')
    return true
  } catch {
    return false
  }
}

const ensureLocalAuthentication = async () => {
  const state = await API.getAuthState()
  if (!state.data.isSuccess || !state.data.data.required) return true
  const password = window.prompt('此 Legado Server 已启用本地访问密码')
  if (!password) return false
  const session = await API.authenticate(password)
  if (!session.data.isSuccess) return false
  setLocalServerToken(session.data.data.token)
  return true
}

void ensureLocalAuthentication()
  .then(allowed => allowed ? API.getAppSettings() : undefined)
  .then(async response => {
    if (!response) return
    if (!response.data.isSuccess) return
    applyAppSettings(response.data.data)
    if (response.data.data.maintenance?.autoUpdateVariant !== false) {
      void API.checkForUpdates().then(updateResponse => {
        const update = updateResponse.data.data
        if (!updateResponse.data.isSuccess || !update?.newer || !update.latestVersion) return
        ElNotification({
          title: '发现服务端更新',
          message: `${update.releaseName || update.latestVersion} · ${update.latestVersion}`,
          type: 'info',
          duration: 10_000,
          onClick: () => void router.push('/server'),
        })
      }).catch(() => undefined)
    }
    if (response.data.data.backup?.autoCheckNewBackup !== false) {
      void API.checkNewBackup().then(checkResponse => {
        const check = checkResponse.data.data
        if (!checkResponse.data.isSuccess || !check?.newer || !check.remote) return
        ElNotification({
          title: '发现新的 WebDAV 备份',
          message: `${check.remote.fileName} · ${new Date(check.remote.modifiedTime).toLocaleString()}`,
          type: 'info',
          duration: 8000,
          onClick: () => void router.push('/server'),
        })
      }).catch(() => undefined)
    }
    if (!hasDefaultEntry()) return
    if (response.data.data.main?.defaultToRead === true && (await resumeRecentReading())) return
    const home = String(response.data.data.main?.defaultHomePage || 'bookshelf')
    const destination = {
      bookshelf: '/',
      explore: '/explore',
      rss: '/rss',
      my: '/settings',
    }[home]
    if (destination && destination !== '/') await router.replace(destination)
  })
  .catch(() => undefined)
// 书架 同步Element PLUS 夜间模式
watch(
  () => useBookStore().isNight,
  isNight => {
    if (isNight) {
      document.documentElement.classList.add('dark')
    } else {
      document.documentElement.classList.remove('dark')
    }
  },
)

window.addEventListener('vite:preloadError', event => {
  event.preventDefault()
})
