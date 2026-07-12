import type { AppSettings } from '@api'

function readString(settings: AppSettings, group: string, key: string, fallback: string) {
  const value = settings[group]?.[key]
  return typeof value === 'string' && value.trim() ? value.trim() : fallback
}

function readNumber(settings: AppSettings, group: string, key: string, fallback: number) {
  const value = Number(settings[group]?.[key])
  return Number.isFinite(value) ? value : fallback
}

function safeColor(value: string, fallback: string) {
  return CSS.supports('color', value) ? value : fallback
}

export function resolveAppLocale(settings?: AppSettings) {
  const configured = String(settings?.main?.language ?? 'auto')
  if (configured === 'zh') return 'zh-CN'
  if (configured === 'zh-rTW') return 'zh-TW'
  if (configured === 'en') return 'en'
  return navigator.language || 'zh-CN'
}

type VideoPreferences = {
  controls?: boolean
  autoplay?: boolean
  loop?: boolean
  muted?: boolean
  preload?: 'none' | 'metadata' | 'auto'
  playbackRate?: number
  volume?: number
}

let videoPreferences: VideoPreferences = {}
let videoObserver: MutationObserver | null = null
let ignoreAudioFocus = false
let audioPlayWakeLock = false
let mediaWakeLock: WakeLockSentinel | null = null
let mediaListenersInstalled = false
let webServiceWakeLock = false
let serviceWakeLock: WakeLockSentinel | null = null
let serviceWakeLockListenerInstalled = false

async function releaseServiceWakeLock() {
  const sentinel = serviceWakeLock
  serviceWakeLock = null
  if (!sentinel) return
  try {
    await sentinel.release()
  } catch {
    // Browsers can release the lock automatically when the page is hidden.
  }
}

async function syncServiceWakeLock() {
  if (!webServiceWakeLock || document.visibilityState !== 'visible') {
    await releaseServiceWakeLock()
    return
  }
  if (serviceWakeLock || !navigator.wakeLock) return
  try {
    serviceWakeLock = await navigator.wakeLock.request('screen')
  } catch {
    // Unsupported browsers continue without page-level wake lock.
  }
}

function installServiceWakeLockRuntime() {
  if (serviceWakeLockListenerInstalled) return
  serviceWakeLockListenerInstalled = true
  document.addEventListener('visibilitychange', () => void syncServiceWakeLock())
}

function playingMedia() {
  return Array.from(document.querySelectorAll<HTMLMediaElement>('audio, video'))
    .filter(media => !media.paused && !media.ended)
}

async function releaseMediaWakeLock() {
  const sentinel = mediaWakeLock
  mediaWakeLock = null
  if (!sentinel) return
  try {
    await sentinel.release()
  } catch {
    // Browsers may release the lock automatically when the document is hidden.
  }
}

async function syncMediaWakeLock() {
  if (!audioPlayWakeLock || document.visibilityState !== 'visible' || playingMedia().length === 0) {
    await releaseMediaWakeLock()
    return
  }
  if (mediaWakeLock || !navigator.wakeLock) return
  try {
    mediaWakeLock = await navigator.wakeLock.request('screen')
  } catch {
    // Wake Lock requires browser support, a secure context and a visible document.
  }
}

function installMediaRuntime() {
  if (mediaListenersInstalled) return
  mediaListenersInstalled = true
  document.addEventListener('play', event => {
    const current = event.target
    if (!(current instanceof HTMLMediaElement)) return
    if (!ignoreAudioFocus) {
      document.querySelectorAll<HTMLMediaElement>('audio, video').forEach(media => {
        if (media !== current && !media.paused) media.pause()
      })
      window.speechSynthesis?.cancel()
    }
    void syncMediaWakeLock()
  }, true)
  document.addEventListener('pause', () => void syncMediaWakeLock(), true)
  document.addEventListener('ended', () => void syncMediaWakeLock(), true)
  document.addEventListener('visibilitychange', () => void syncMediaWakeLock())
  document.addEventListener('legado:speech-start', () => {
    if (!ignoreAudioFocus) {
      document.querySelectorAll<HTMLMediaElement>('audio, video').forEach(media => media.pause())
    }
  })
}

function applyVideoElement(video: HTMLVideoElement) {
  if (typeof videoPreferences.controls === 'boolean') video.controls = videoPreferences.controls
  if (typeof videoPreferences.autoplay === 'boolean') video.autoplay = videoPreferences.autoplay
  if (typeof videoPreferences.loop === 'boolean') video.loop = videoPreferences.loop
  if (typeof videoPreferences.muted === 'boolean') video.muted = videoPreferences.muted
  if (videoPreferences.preload && ['none', 'metadata', 'auto'].includes(videoPreferences.preload)) {
    video.preload = videoPreferences.preload
  }
  const rate = Number(videoPreferences.playbackRate)
  if (Number.isFinite(rate)) video.playbackRate = Math.min(4, Math.max(0.25, rate))
  const volume = Number(videoPreferences.volume)
  if (Number.isFinite(volume)) video.volume = Math.min(1, Math.max(0, volume))
}

function applyVideoPreferences(raw: unknown) {
  try {
    videoPreferences = typeof raw === 'string' ? JSON.parse(raw || '{}') : (raw || {}) as VideoPreferences
  } catch {
    videoPreferences = {}
  }
  document.querySelectorAll('video').forEach(applyVideoElement)
  if (videoObserver) return
  videoObserver = new MutationObserver(records => {
    records.forEach(record => record.addedNodes.forEach(node => {
      if (!(node instanceof Element)) return
      if (node instanceof HTMLVideoElement) applyVideoElement(node)
      node.querySelectorAll('video').forEach(applyVideoElement)
    }))
  })
  videoObserver.observe(document.documentElement, { childList: true, subtree: true })
}

export function applyAppSettings(settings: AppSettings) {
  const mode = readString(settings, 'main', 'themeMode', '0')
  const systemDark = window.matchMedia?.('(prefers-color-scheme: dark)').matches ?? false
  const dark = mode === '2' || (mode === '0' && systemDark)
  const ink = mode === '3'
  const theme = settings.theme || {}
  const primary = safeColor(
    String(dark ? theme.colorPrimaryNight : theme.colorPrimary || ''),
    dark ? '#546e7a' : '#0f766e',
  )
  const accent = safeColor(
    String(dark ? theme.colorAccentNight : theme.colorAccent || ''),
    dark ? '#bf360c' : '#e53935',
  )
  const background = safeColor(
    String(dark ? theme.colorBackgroundNight : theme.colorBackground || ''),
    dark ? '#212121' : '#f5f7fa',
  )
  const bottom = safeColor(
    String(dark ? theme.colorBottomBackgroundNight : theme.colorBottomBackground || ''),
    dark ? '#303030' : '#ffffff',
  )
  const image = readString(
    settings,
    'theme',
    dark ? 'backgroundImageNight' : 'backgroundImage',
    '',
  )
  const blur = Math.min(
    40,
    Math.max(
      0,
      readNumber(settings, 'theme', dark ? 'backgroundImageNightBlurring' : 'backgroundImageBlurring', 0),
    ),
  )
  const scale = Math.min(1.4, Math.max(0.8, readNumber(settings, 'theme', 'fontScale', 100) / 100))
  const elevation = Math.min(24, Math.max(0, readNumber(settings, 'theme', 'barElevation', 4)))
  const transparentNav = dark
    ? theme.transparentNavBarNight === true
    : theme.transparentNavBar === true
  const root = document.documentElement
  const locale = resolveAppLocale(settings)
  ignoreAudioFocus = settings.aloud?.ignoreAudioFocus === true
  audioPlayWakeLock = settings.aloud?.audioPlayWakeLock === true
  webServiceWakeLock = settings.main?.webServiceWakeLock === true

  root.lang = locale
  root.dataset.legadoLocale = locale
  root.dataset.legadoAudioFocus = ignoreAudioFocus ? 'shared' : 'exclusive'
  root.dataset.legadoAudioWakeLock = audioPlayWakeLock ? 'enabled' : 'disabled'
  root.dataset.legadoServiceWakeLock = webServiceWakeLock ? 'enabled' : 'disabled'
  root.classList.toggle('dark', dark)
  root.classList.toggle('legado-ink', ink)
  root.classList.toggle('legado-transparent-status', theme.transparentStatusBar === true)
  root.classList.toggle('legado-immersive-nav', theme.immNavigationBar === true)
  root.classList.toggle('legado-antialias', settings.network?.antiAlias === true)
  root.style.setProperty('--el-color-primary', primary)
  root.style.setProperty('--legado-primary', primary)
  root.style.setProperty('--legado-accent', accent)
  root.style.setProperty('--legado-page-bg', background)
  root.style.setProperty('--legado-surface-bg', bottom)
  root.style.setProperty('--legado-font-scale', String(scale))
  root.style.setProperty('--legado-background-image', image ? `url("${image.replaceAll('"', '\\"')}")` : 'none')
  root.style.setProperty('--legado-background-blur', `${blur}px`)
  root.style.setProperty('--legado-nav-bg', transparentNav ? 'rgba(255, 255, 255, 0.18)' : bottom)
  root.style.setProperty('--legado-nav-border', transparentNav ? 'rgba(255, 255, 255, 0.3)' : 'rgba(203, 213, 225, 0.78)')
  root.style.setProperty(
    '--legado-bar-shadow',
    elevation === 0 ? 'none' : `0 ${Math.max(1, elevation / 2)}px ${elevation * 2}px rgba(15, 23, 42, 0.13)`,
  )
  applyVideoPreferences(settings.maintenance?.videoSetting)
  installServiceWakeLockRuntime()
  installMediaRuntime()
  void syncServiceWakeLock()
  void syncMediaWakeLock()
}
