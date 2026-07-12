<template>
  <div
    class="editor"
    :class="editorClasses"
    :style="editorStyle"
  >
    <source-tab-form class="left" :config="config" />
    <tool-bar />
    <source-tab-tools class="right" />
  </div>
</template>

<script setup lang="ts">
import bookSourceConfig from '@/config/bookSourceEditConfig'
import rssSourceConfig from '@/config/rssSourceEditConfig'
import '@/assets/sourceeditor.css'
import { useDark } from '@vueuse/core'
import { useSourceStore } from '@/store'
import type { SourceConfig } from '@/config/sourceConfig'
import API, { type AppSettings } from '@/api/api'

useDark()

const route = useRoute()
const store = useSourceStore()
const appSettings = ref<AppSettings>({})
let editorObserver: MutationObserver | null = null
const editorFontSize = computed(() =>
  Math.min(32, Math.max(10, Number(appSettings.value.maintenance?.editFontScale ?? 16))),
)
const editorAutoWrap = computed(() => appSettings.value.maintenance?.editAutoWrap !== false)
const editorAutoComplete = computed(() => appSettings.value.maintenance?.editAutoComplete !== false)
const editorGuideMode = computed(() =>
  Math.min(3, Math.max(0, Number(appSettings.value.maintenance?.showBoardLine ?? 0))),
)
const editorMaxLines = computed(() =>
  Math.min(10000, Math.max(0, Number(appSettings.value.network?.sourceEditMaxLine ?? 0))),
)
const editorNonPrintableMode = computed(() =>
  Math.min(2, Math.max(0, Number(appSettings.value.maintenance?.editNonPrintable ?? 0))),
)
const editorStyle = computed(() => ({
  '--editor-font-size': `${editorFontSize.value}px`,
  '--editor-max-height': editorMaxLines.value > 0
    ? `${Math.ceil(editorMaxLines.value * editorFontSize.value * 1.6 + 24)}px`
    : 'none',
}))
const editorClasses = computed(() => ({
  'editor-nowrap': !editorAutoWrap.value,
  'editor-guide-vertical': editorGuideMode.value === 1 || editorGuideMode.value === 3,
  'editor-guide-horizontal': editorGuideMode.value === 2 || editorGuideMode.value === 3,
}))

function applyEditorAttributes() {
  const value = editorAutoComplete.value ? 'on' : 'off'
  document.querySelectorAll<HTMLInputElement | HTMLTextAreaElement>('.editor input, .editor textarea')
    .forEach(element => {
      element.autocomplete = value
      element.spellcheck = editorAutoComplete.value
      if (element instanceof HTMLTextAreaElement) {
        element.wrap = editorAutoWrap.value ? 'soft' : 'off'
        element.style.maxHeight = editorMaxLines.value > 0 ? 'var(--editor-max-height)' : ''
        element.style.overflowY = editorMaxLines.value > 0 ? 'auto' : ''
        bindWhitespacePreview(element)
      }
    })
}

function whitespaceText(value: string) {
  return value
    .replace(/ /g, '·')
    .replace(/\t/g, '→   ')
    .replace(/\r?\n/g, '↵\n')
}

function updateWhitespacePreview(textarea: HTMLTextAreaElement) {
  const host = textarea.parentElement
  if (!host) return
  let preview = host.querySelector<HTMLPreElement>('.non-printable-preview')
  if (editorNonPrintableMode.value === 0 || document.activeElement !== textarea) {
    preview?.remove()
    return
  }
  if (!preview) {
    preview = document.createElement('pre')
    host.appendChild(preview)
  }
  preview.className = `non-printable-preview mode-${editorNonPrintableMode.value}`
  preview.textContent = whitespaceText(textarea.value).slice(0, 20000)
}

function bindWhitespacePreview(textarea: HTMLTextAreaElement) {
  if (textarea.dataset.nonPrintableBound === 'true') {
    updateWhitespacePreview(textarea)
    return
  }
  textarea.dataset.nonPrintableBound = 'true'
  textarea.addEventListener('focus', () => updateWhitespacePreview(textarea))
  textarea.addEventListener('input', () => updateWhitespacePreview(textarea))
  textarea.addEventListener('blur', () => setTimeout(() => updateWhitespacePreview(textarea), 0))
}

async function loadEditorPreferences() {
  try {
    const response = await API.getAppSettings()
    if (response.data.isSuccess) appSettings.value = response.data.data
  } finally {
    await nextTick()
    applyEditorAttributes()
  }
}
const isBookSource = ref<boolean>(/bookSource/i.test(route.path))
const config = computed<SourceConfig>(() =>
  isBookSource.value
    ? (bookSourceConfig as SourceConfig)
    : (rssSourceConfig as SourceConfig),
)

provide('isBookSource', isBookSource)

watch(
  () => route.path,
  path => {
    isBookSource.value = /bookSource/i.test(path)
    document.title = isBookSource.value ? '书源管理' : '订阅源管理'
    store.clearEdit()
  },
  { immediate: true },
)

watch([editorAutoWrap, editorAutoComplete, editorMaxLines, editorNonPrintableMode], async () => {
  await nextTick()
  applyEditorAttributes()
})

onMounted(() => {
  void loadEditorPreferences()
  editorObserver = new MutationObserver(applyEditorAttributes)
  editorObserver.observe(document.querySelector('.editor')!, { childList: true, subtree: true })
})

onUnmounted(() => {
  editorObserver?.disconnect()
  editorObserver = null
})
</script>

<style lang="scss" scoped>
.editor {
  display: flex;
  height: 100vh;
  padding-top: 68px;
  box-sizing: border-box;
  overflow: hidden;

  :deep(input),
  :deep(textarea) {
    font-size: var(--editor-font-size);
  }

  :deep(.non-printable-preview) {
    max-height: 180px;
    margin: 6px 0 0;
    padding: 8px 10px;
    overflow: auto;
    border: 1px solid rgba(148, 163, 184, 0.45);
    border-radius: 5px;
    background: rgba(248, 250, 252, 0.96);
    color: #64748b;
    font: 12px/1.55 ui-monospace, SFMono-Regular, Consolas, monospace;
    white-space: pre-wrap;
    overflow-wrap: anywhere;
  }

  :deep(.non-printable-preview.mode-2) {
    border-color: rgba(180, 83, 9, 0.42);
    background: #fffbeb;
    color: #92400e;
  }

  &.editor-nowrap :deep(textarea) {
    white-space: pre;
    overflow-x: auto;
  }

  &.editor-guide-vertical :deep(textarea) {
    background-image: linear-gradient(
      to right,
      transparent calc(80ch - 1px),
      rgba(148, 163, 184, 0.38) calc(80ch - 1px),
      rgba(148, 163, 184, 0.38) 80ch,
      transparent 80ch
    );
  }

  &.editor-guide-horizontal :deep(textarea) {
    background-image: repeating-linear-gradient(
      to bottom,
      transparent 0,
      transparent calc(1.6em - 1px),
      rgba(148, 163, 184, 0.22) calc(1.6em - 1px),
      rgba(148, 163, 184, 0.22) 1.6em
    );
  }

  &.editor-guide-vertical.editor-guide-horizontal :deep(textarea) {
    background-image:
      linear-gradient(
        to right,
        transparent calc(80ch - 1px),
        rgba(148, 163, 184, 0.38) calc(80ch - 1px),
        rgba(148, 163, 184, 0.38) 80ch,
        transparent 80ch
      ),
      repeating-linear-gradient(
        to bottom,
        transparent 0,
        transparent calc(1.6em - 1px),
        rgba(148, 163, 184, 0.22) calc(1.6em - 1px),
        rgba(148, 163, 184, 0.22) 1.6em
      );
  }

  .left {
    flex: 1;
    margin-left: 20px;
  }

  .right {
    flex: 1;
    width: 360px;
    margin-right: 20px;
  }
}

@media (max-width: 760px) {
  .editor {
    padding-top: 110px;
  }
}
</style>
