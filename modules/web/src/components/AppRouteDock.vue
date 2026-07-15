<template>
  <nav class="app-route-dock" aria-label="Legado 页面导航">
    <router-link
      v-for="item in navItems"
      :key="item.path"
      :to="item.path"
      class="route-link"
      :aria-label="item.label"
      :title="item.label"
    >
      <component :is="item.icon" />
      <span>{{ item.label }}</span>
    </router-link>
  </nav>
</template>

<script setup lang="ts">
import { computed, onMounted, ref, type Component } from 'vue'
import {
  Collection,
  Compass,
  DataAnalysis,
  Link,
  Reading,
  SetUp,
  Setting,
} from '@element-plus/icons-vue'
import API, { type AppSettings } from '@/api/api'

type NavItem = {
  label: string
  path: string
  icon: Component
}

const appSettings = ref<AppSettings>({})

const navigationLabels = computed(() => {
  const language = String(appSettings.value.main?.language ?? 'auto')
  if (language === 'en') {
    return { shelf: 'Bookshelf', sources: 'Sources', rss: 'Feeds', features: 'Tools', settings: 'Settings', server: 'Server' }
  }
  if (language === 'zh-rTW') {
    return { shelf: '書架', sources: '書源', rss: '訂閱源', features: '功能', settings: '設定', server: '控制台' }
  }
  return { shelf: '书架', sources: '书源', rss: '订阅源', features: '功能', settings: '设置', server: '控制台' }
})

const exploreLabel = computed(() => {
  const language = String(appSettings.value.main?.language ?? 'auto')
  if (language === 'en') return 'Discover'
  if (language === 'zh-rTW') return '發現'
  return '发现'
})

const navItems = computed<NavItem[]>(() => {
  const main = appSettings.value.main || {}
  const labels = navigationLabels.value
  return [
    { label: labels.shelf, path: '/', icon: Reading },
    { label: labels.sources, path: '/bookSource', icon: Collection },
    ...(main.showDiscovery !== false ? [{ label: exploreLabel.value, path: '/explore', icon: Compass }] : []),
    ...(main.showRss !== false ? [{ label: labels.rss, path: '/rss', icon: Link }] : []),
    { label: labels.features, path: '/features', icon: SetUp },
    { label: labels.settings, path: '/settings', icon: Setting },
    { label: labels.server, path: '/server', icon: DataAnalysis },
  ]
})

onMounted(async () => {
  try {
    const response = await API.getAppSettings()
    if (response.data.isSuccess) appSettings.value = response.data.data
  } catch {
    // Keep all navigation entries available if preferences cannot be loaded.
  }
})
</script>

<style scoped lang="scss">
.app-route-dock {
  position: fixed;
  top: 14px;
  right: 18px;
  z-index: 50;
  max-width: calc(100vw - 36px);
  padding: 8px;
  border: 1px solid var(--legado-nav-border);
  border-radius: 8px;
  background: var(--legado-nav-bg);
  box-shadow: var(--legado-bar-shadow);
  backdrop-filter: blur(14px);
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}

:global(.legado-transparent-status) .app-route-dock {
  top: max(0px, env(safe-area-inset-top));
}

:global(.legado-immersive-nav) .app-route-dock {
  backdrop-filter: none;
}

.route-link {
  min-height: 34px;
  padding: 0 10px;
  border-radius: 7px;
  color: #334155;
  text-decoration: none;
  display: inline-flex;
  align-items: center;
  gap: 7px;
  font-size: 13px;
  font-weight: 600;
  line-height: 1;
  white-space: nowrap;

  svg {
    width: 16px;
    height: 16px;
    color: #0f766e;
  }

  &:hover,
  &.router-link-active {
    color: #0f172a;
    background: #ecfdf5;
  }

  &.router-link-exact-active {
    color: #fff;
    background: #0f766e;

    svg {
      color: #fff;
    }
  }
}

@media (max-width: 760px) {
  .app-route-dock {
    top: 0;
    left: 0;
    right: 0;
    max-width: none;
    min-height: 44px;
    padding: max(5px, env(safe-area-inset-top)) 8px 5px;
    border-radius: 0;
    flex-wrap: nowrap;
    overflow-x: auto;
    overscroll-behavior-x: contain;
    justify-content: flex-start;
  }

  .route-link {
    flex: 0 0 34px;
    min-height: 34px;
    justify-content: center;
    padding: 0;

    span {
      position: absolute;
      width: 1px;
      height: 1px;
      padding: 0;
      margin: -1px;
      overflow: hidden;
      clip: rect(0, 0, 0, 0);
      white-space: nowrap;
      border: 0;
    }
  }
}
</style>
