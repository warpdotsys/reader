<template>
  <transition name="welcome-fade">
    <section
      v-if="welcomeVisible"
      class="welcome-screen"
      :style="welcomeBackground"
      aria-label="Legado"
    >
      <button class="welcome-close" type="button" title="关闭欢迎页" aria-label="关闭欢迎页" @click="closeWelcome">
        <Close />
      </button>
      <div class="welcome-brand">
        <div v-if="welcomeShowIcon" class="welcome-mark">L</div>
        <h1 v-if="welcomeShowText">Legado</h1>
      </div>
    </section>
  </transition>
  <app-route-dock v-if="showRouteDock" />
  <router-view
    class="route-content"
    :class="{ 'chapter-route-content': route.name === 'chapter' }"
  ></router-view>
</template>

<script setup lang="ts">
import AppRouteDock from '@/components/AppRouteDock.vue'
import type { AppSettings, LeagdoApiResponse } from '@/api/api'
import { Close } from '@element-plus/icons-vue'

const route = useRoute()
const welcomeVisible = ref(false)
const welcomeSettings = ref<AppSettings>({})
let welcomeTimer: ReturnType<typeof setTimeout> | null = null

const welcomeDark = computed(() => {
  const mode = String(welcomeSettings.value.main?.themeMode ?? '0')
  return mode === '2' || (mode === '0' && window.matchMedia?.('(prefers-color-scheme: dark)').matches)
})
const welcomeImage = computed(() => {
  const welcome = welcomeSettings.value.welcome || {}
  const value = welcomeDark.value ? welcome.welcomeImagePathDark : welcome.welcomeImagePath
  return typeof value === 'string' ? value.trim() : ''
})
const welcomeShowText = computed(() => {
  const welcome = welcomeSettings.value.welcome || {}
  return welcomeDark.value ? welcome.welcomeShowTextDark !== false : welcome.welcomeShowText !== false
})
const welcomeShowIcon = computed(() => {
  const welcome = welcomeSettings.value.welcome || {}
  return welcomeDark.value ? welcome.welcomeShowIconDark !== false : welcome.welcomeShowIcon !== false
})
const welcomeBackground = computed(() => ({
  backgroundColor: welcomeDark.value ? '#171717' : '#f8fafc',
  backgroundImage: welcomeImage.value ? `url("${welcomeImage.value.replaceAll('"', '\\"')}")` : 'none',
}))

function closeWelcome() {
  welcomeVisible.value = false
  if (welcomeTimer) clearTimeout(welcomeTimer)
  welcomeTimer = null
}

onMounted(async () => {
  try {
    const response = await fetch(new URL('/getAppSettings', location.origin))
    if (!response.ok) return
    const result = await response.json() as LeagdoApiResponse<AppSettings>
    if (!result.isSuccess) return
    welcomeSettings.value = result.data
    if (result.data.welcome?.customWelcome !== true) return
    welcomeVisible.value = true
    const duration = Math.min(10000, Math.max(0, Number(result.data.welcome?.welcomeShowTime ?? 500)))
    if (duration > 0) welcomeTimer = setTimeout(closeWelcome, duration)
  } catch {
    // A failed welcome preference request must never block the application.
  }
})

onUnmounted(() => {
  if (welcomeTimer) clearTimeout(welcomeTimer)
})
const showRouteDock = computed(() =>
  ['shelf', 'book-home', 'rss-home', 'rss-reader', 'chapter', 'feature-workbench', 'settings-center', 'server-console'].includes(String(route.name || '')),
)
</script>

<style lang="scss">
.welcome-screen {
  position: fixed;
  inset: 0;
  z-index: 3000;
  display: grid;
  place-items: center;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
}

.welcome-close {
  position: fixed;
  top: max(14px, env(safe-area-inset-top));
  right: max(14px, env(safe-area-inset-right));
  display: grid;
  place-items: center;
  width: 38px;
  height: 38px;
  padding: 0;
  border: 1px solid rgba(148, 163, 184, 0.58);
  border-radius: 7px;
  background: rgba(255, 255, 255, 0.88);
  color: #334155;
  cursor: pointer;

  svg {
    width: 19px;
    height: 19px;
  }
}

.welcome-brand {
  display: grid;
  justify-items: center;
  gap: 14px;
  color: var(--legado-primary, #0f766e);

  h1 {
    margin: 0;
    font-size: 32px;
    letter-spacing: 0;
  }
}

.welcome-mark {
  display: grid;
  place-items: center;
  width: 68px;
  height: 68px;
  border-radius: 8px;
  background: var(--legado-primary, #0f766e);
  color: #fff;
  font-size: 34px;
  font-weight: 800;
}

.welcome-fade-enter-active,
.welcome-fade-leave-active {
  transition: opacity 180ms ease;
}

.welcome-fade-enter-from,
.welcome-fade-leave-to {
  opacity: 0;
}

@media (max-width: 760px) {
  :global(:root) {
    --legado-route-dock-height: calc(48px + env(safe-area-inset-top));
  }

  .route-content:not(.chapter-route-content) {
    padding-top: var(--legado-route-dock-height);
  }
}
</style>
